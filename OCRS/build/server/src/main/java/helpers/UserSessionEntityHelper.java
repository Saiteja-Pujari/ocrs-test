package helpers;

import graphql.input.UserSessionEntityInput;
import models.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.jpa.UserSessionRepository;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;
import store.InputHelper;

@Service("UserSession")
public class UserSessionEntityHelper<T extends UserSession, I extends UserSessionEntityInput>
    implements EntityHelper<T, I> {
  @Autowired protected EntityMutator mutator;
  @Autowired private UserSessionRepository userSessionRepository;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
  }

  @Override
  public T fromInput(I input, InputHelper helper) {
    return null;
  }

  @Override
  public T fromInput(I input, T entity, InputHelper helper) {
    if (helper.has("userSessionId")) {
      entity.setUserSessionId(input.getUserSessionId());
    }
    entity.updateMasters((o) -> {});
    return entity;
  }

  public void validateFieldUserSessionId(T entity, EntityValidationContext validationContext) {
    try {
      String it = entity.getUserSessionId();
      if (it == null) {
        validationContext.addFieldError("userSessionId", "userSessionId is required.");
        return;
      }
    } catch (RuntimeException e) {
    }
  }

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

  public void validate(T entity, EntityValidationContext validationContext) {
    referenceFromValidations(entity, validationContext);
    validateFieldUserSessionId(entity, validationContext);
  }

  @Override
  public T clone(T entity) {
    return null;
  }

  @Override
  public T getById(long id) {
    return id == 0l ? null : ((T) userSessionRepository.getOne(id));
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
