package classes;

import java.util.List;
import models.UserSession;

public class MutateUserSessionResult extends MutateResult<UserSession> {
  public MutateUserSessionResult() {}

  public MutateUserSessionResult(
      MutateResultStatus status, UserSession value, List<String> errors) {
    this.status = status;
    this.value = value;
    this.errors = errors;
  }
}
