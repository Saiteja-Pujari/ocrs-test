package classes;

import java.util.List;
import models.User;

public class MutateUserResult extends MutateResult<User> {
  public MutateUserResult() {}

  public MutateUserResult(MutateResultStatus status, User value, List<String> errors) {
    this.status = status;
    this.value = value;
    this.errors = errors;
  }
}
