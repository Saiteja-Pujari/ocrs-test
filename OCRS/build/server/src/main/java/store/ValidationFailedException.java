package store;

import java.util.List;

@SuppressWarnings("serial")
public class ValidationFailedException extends RuntimeException {
  private List<String> errors;

  public ValidationFailedException(List<String> errors) {
    super();
    this.errors = errors;
  }

  public List<String> getErrors() {
    return errors;
  }
}
