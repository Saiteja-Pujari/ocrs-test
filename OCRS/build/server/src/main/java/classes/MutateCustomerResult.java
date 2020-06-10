package classes;

import java.util.List;
import models.Customer;

public class MutateCustomerResult extends MutateResult<Customer> {
  public MutateCustomerResult() {}

  public MutateCustomerResult(MutateResultStatus status, Customer value, List<String> errors) {
    this.status = status;
    this.value = value;
    this.errors = errors;
  }
}
