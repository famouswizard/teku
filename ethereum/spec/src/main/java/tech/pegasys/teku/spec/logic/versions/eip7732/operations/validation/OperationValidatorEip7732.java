/*
 * Copyright Consensys Software Inc., 2024
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

package tech.pegasys.teku.spec.logic.versions.eip7732.operations.validation;

import java.util.Optional;
import tech.pegasys.teku.spec.datastructures.execution.SignedExecutionPayloadHeader;
import tech.pegasys.teku.spec.datastructures.state.Fork;
import tech.pegasys.teku.spec.datastructures.state.beaconstate.BeaconState;
import tech.pegasys.teku.spec.logic.common.helpers.BeaconStateAccessors;
import tech.pegasys.teku.spec.logic.common.helpers.Predicates;
import tech.pegasys.teku.spec.logic.common.operations.validation.AttestationDataValidator;
import tech.pegasys.teku.spec.logic.common.operations.validation.OperationInvalidReason;
import tech.pegasys.teku.spec.logic.common.util.AttestationUtil;
import tech.pegasys.teku.spec.logic.versions.capella.operations.validation.OperationValidatorCapella;
import tech.pegasys.teku.spec.logic.versions.phase0.operations.validation.VoluntaryExitValidator;

public class OperationValidatorEip7732 extends OperationValidatorCapella {

  private final ExecutionPayloadHeaderValidator executionPayloadHeaderValidator =
      new ExecutionPayloadHeaderValidator();

  public OperationValidatorEip7732(
      final Predicates predicates,
      final BeaconStateAccessors beaconStateAccessors,
      final AttestationDataValidator attestationDataValidator,
      final AttestationUtil attestationUtil,
      final VoluntaryExitValidator voluntaryExitValidator) {
    super(
        predicates,
        beaconStateAccessors,
        attestationDataValidator,
        attestationUtil,
        voluntaryExitValidator);
  }

  @Override
  public Optional<OperationInvalidReason> validateExecutionPayloadHeader(
      final Fork fork,
      final BeaconState state,
      final SignedExecutionPayloadHeader executionPayloadHeader) {
    return executionPayloadHeaderValidator.validate(fork, state, executionPayloadHeader);
  }
}
