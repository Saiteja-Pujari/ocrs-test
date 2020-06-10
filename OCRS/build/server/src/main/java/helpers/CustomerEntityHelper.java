package helpers;

import graphql.input.CustomerEntityInput;
import models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import repository.jpa.CustomerRepository;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;
import store.InputHelper;

@Service("Customer")
public class CustomerEntityHelper<T extends Customer, I extends CustomerEntityInput>
    implements EntityHelper<T, I> {
  @Autowired protected EntityMutator mutator;
  @org.springframework.beans.factory.annotation.Autowired private PasswordEncoder passwordEncoder;
  @Autowired private CustomerRepository customerRepository;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
  }

  public Customer newInstance() {
    return new Customer();
  }

  @Override
  public T fromInput(I input, InputHelper helper) {
    if (input == null) {
      return null;
    }
    T newCustomer = ((T) new Customer());
    newCustomer.setId(input.getId());
    return fromInput(input, newCustomer, helper);
  }

  @Override
  public T fromInput(I input, T entity, InputHelper helper) {
    if (helper.has("phonenumber")) {
      entity.setPhonenumber(input.getPhonenumber());
    }
    if (helper.has("password")) {
      entity.setPassword(passwordEncoder.encode(input.getPassword()));
    }
    entity.updateMasters((o) -> {});
    return entity;
  }

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

  public void validate(T entity, EntityValidationContext validationContext) {
    referenceFromValidations(entity, validationContext);
  }

  @Override
  public T clone(T entity) {
    return null;
  }

  @Override
  public T getById(long id) {
    return id == 0l ? null : ((T) customerRepository.getOne(id));
  }

  @Override
  public void setDefaults(T entity) {}

  @Override
  public void compute(T entity) {}

  public Boolean onDelete(T entity, EntityValidationContext deletionContext) {
    return true;
  }

  public void validateOnDelete(T entity, EntityValidationContext deletionContext) {}

  @Override
  public Boolean onCreate(T entity) {
    return true;
  }

  @Override
  public Boolean onUpdate(T entity, T old) {
    return true;
  }

  public T getOld(long id) {
    return ((T) getById(id).clone());
  }
}
