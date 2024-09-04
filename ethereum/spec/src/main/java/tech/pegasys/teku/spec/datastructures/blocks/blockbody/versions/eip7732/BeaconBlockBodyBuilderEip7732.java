/*
 * Copyright Consensys Software Inc., 2022
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package tech.pegasys.teku.spec.datastructures.blocks.blockbody.versions.eip7732;

import static com.google.common.base.Preconditions.checkNotNull;

import tech.pegasys.teku.infrastructure.ssz.SszList;
import tech.pegasys.teku.infrastructure.ssz.primitive.SszBytes32;
import tech.pegasys.teku.spec.datastructures.blocks.blockbody.BeaconBlockBody;
import tech.pegasys.teku.spec.datastructures.blocks.blockbody.BeaconBlockBodyBuilder;
import tech.pegasys.teku.spec.datastructures.blocks.blockbody.BeaconBlockBodySchema;
import tech.pegasys.teku.spec.datastructures.blocks.blockbody.versions.electra.BeaconBlockBodyBuilderElectra;
import tech.pegasys.teku.spec.datastructures.execution.SignedExecutionPayloadHeader;
import tech.pegasys.teku.spec.datastructures.operations.PayloadAttestation;
import tech.pegasys.teku.spec.datastructures.type.SszSignature;

public class BeaconBlockBodyBuilderEip7732 extends BeaconBlockBodyBuilderElectra {

  private SignedExecutionPayloadHeader signedExecutionPayloadHeader;
  private SszList<PayloadAttestation> payloadAttestations;

  public BeaconBlockBodyBuilderEip7732(
      final BeaconBlockBodySchema<? extends BeaconBlockBodyEip7732> schema) {
    super(schema, null);
  }

  @Override
  public BeaconBlockBodyBuilder signedExecutionPayloadHeader(
      final SignedExecutionPayloadHeader signedExecutionPayloadHeader) {
    this.signedExecutionPayloadHeader = signedExecutionPayloadHeader;
    return this;
  }

  @Override
  public BeaconBlockBodyBuilder payloadAttestations(
      final SszList<PayloadAttestation> payloadAttestations) {
    this.payloadAttestations = payloadAttestations;
    return this;
  }

  @Override
  protected void validate() {
    // EIP7732 TODO: hacky skip of super.validate() to avoid blobKzgCommitments and executionPayload
    // checks
    // super.validate();
    checkNotNull(signedExecutionPayloadHeader, "signedExecutionPayloadHeader must be specified");
    checkNotNull(payloadAttestations, "payloadAttestations must be specified");
  }

  @Override
  public BeaconBlockBody build() {
    validate();

    final BeaconBlockBodySchemaEip7732Impl schema =
        getAndValidateSchema(false, BeaconBlockBodySchemaEip7732Impl.class);
    return new BeaconBlockBodyEip7732Impl(
        schema,
        new SszSignature(randaoReveal),
        eth1Data,
        SszBytes32.of(graffiti),
        proposerSlashings,
        attesterSlashings,
        attestations,
        deposits,
        voluntaryExits,
        syncAggregate,
        getBlsToExecutionChanges(),
        signedExecutionPayloadHeader,
        payloadAttestations);
  }
}
