package helpers;

import graphql.input.OneTimePasswordEntityInput;
import models.OneTimePassword;
import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.jpa.OneTimePasswordRepository;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;
import store.InputHelper;

@Service("OneTimePassword")
public class OneTimePasswordEntityHelper<
        T extends OneTimePassword, I extends OneTimePasswordEntityInput>
    implements EntityHelper<T, I> {
  @Autowired protected EntityMutator mutator;
  @Autowired private OneTimePasswordRepository oneTimePasswordRepository;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
  }

  public OneTimePassword newInstance() {
    return new OneTimePassword();
  }

  @Override
  public T fromInput(I input, InputHelper helper) {
    if (input == null) {
      return null;
    }
    T newOneTimePassword = ((T) new OneTimePassword());
    newOneTimePassword.setId(input.getId());
    return fromInput(input, newOneTimePassword, helper);
  }

  @Override
  public T fromInput(I input, T entity, InputHelper helper) {
    if (helper.has("success")) {
      entity.setSuccess(input.isSuccess());
    }
    if (helper.has("errorMsg")) {
      entity.setErrorMsg(input.getErrorMsg());
    }
    if (helper.has("token")) {
      entity.setToken(input.getToken());
    }
    if (helper.has("code")) {
      entity.setCode(input.getCode());
    }
    if (helper.has("user")) {
      if (input.getUser() != null) {
        entity.setUser(helper.readRef(input.getUser()));
      } else {
        entity.setUser(null);
      }
    }
    if (helper.has("expiry")) {
      entity.setExpiry(input.getExpiry());
    }
    entity.updateMasters((o) -> {});
    return entity;
  }

  public void validateFieldSuccess(T entity, EntityValidationContext validationContext) {
    try {
      boolean it = entity.isSuccess();
    } catch (RuntimeException e) {
    }
  }

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

  public void validate(T entity, EntityValidationContext validationContext) {
    referenceFromValidations(entity, validationContext);
    validateFieldSuccess(entity, validationContext);
    isErrorMsgExists(entity);
  }

  public boolean isErrorMsgExists(T entity) {
    if (!entity.isSuccess()) {
      return true;
    } else {
      entity.setErrorMsg("");
      return false;
    }
  }

  @Override
  public T clone(T entity) {
    return null;
  }

  @Override
  public T getById(long id) {
    return id == 0l ? null : ((T) oneTimePasswordRepository.getOne(id));
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
