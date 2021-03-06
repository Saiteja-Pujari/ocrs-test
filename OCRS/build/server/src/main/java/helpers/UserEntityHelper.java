package helpers;

import graphql.input.UserEntityInput;
import models.OneTimePassword;
import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.jpa.OneTimePasswordRepository;
import repository.jpa.UserRepository;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;
import store.InputHelper;

@Service("User")
public class UserEntityHelper<T extends User, I extends UserEntityInput>
    implements EntityHelper<T, I> {
  @Autowired protected EntityMutator mutator;
  @Autowired private UserRepository userRepository;
  @Autowired private OneTimePasswordRepository oneTimePasswordRepository;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
  }

  @Override
  public T fromInput(I input, InputHelper helper) {
    return null;
  }

  @Override
  public T fromInput(I input, T entity, InputHelper helper) {
    if (helper.has("isActive")) {
      entity.setIsActive(input.isIsActive());
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
    return id == 0l ? null : ((T) userRepository.getOne(id));
  }

  @Override
  public void setDefaults(T entity) {}

  @Override
  public void compute(T entity) {}

  private void deleteUserInOneTimePassword(T entity, EntityValidationContext deletionContext) {
    if (this.oneTimePasswordRepository.getByUser(entity).size() > 0) {
      deletionContext.addEntityError(
          "This cannot be deleted as it is being referred to by OneTimePassword.");
    }
  }

  public Boolean onDelete(T entity, EntityValidationContext deletionContext) {
    return true;
  }

  public void validateOnDelete(T entity, EntityValidationContext deletionContext) {
    this.deleteUserInOneTimePassword(entity, deletionContext);
  }

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
