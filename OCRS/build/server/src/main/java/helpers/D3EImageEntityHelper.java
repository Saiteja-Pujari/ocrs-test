package helpers;

import d3e.core.DFile;
import graphql.input.D3EImageEntityInput;
import models.D3EImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.jpa.AvatarRepository;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;
import store.InputHelper;

@Service("D3EImage")
public class D3EImageEntityHelper<T extends D3EImage, I extends D3EImageEntityInput>
    implements EntityHelper<T, I> {
  @Autowired protected EntityMutator mutator;
  @Autowired private AvatarRepository avatarRepository;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
  }

  public D3EImage newInstance() {
    return new D3EImage();
  }

  @Override
  public T fromInput(I input, InputHelper helper) {
    if (input == null) {
      return null;
    }
    T newD3EImage = ((T) new D3EImage());
    return fromInput(input, newD3EImage, helper);
  }

  @Override
  public T fromInput(I input, T entity, InputHelper helper) {
    if (helper.has("size")) {
      entity.setSize(input.getSize());
    }
    if (helper.has("width")) {
      entity.setWidth(input.getWidth());
    }
    if (helper.has("height")) {
      entity.setHeight(input.getHeight());
    }
    if (helper.has("file")) {
      entity.setFile(helper.readDFile(input.getFile(), "file"));
    }
    return entity;
  }

  public void validateFieldSize(T entity, EntityValidationContext validationContext) {
    try {
      long it = entity.getSize();
    } catch (RuntimeException e) {
    }
  }

  public void validateFieldWidth(T entity, EntityValidationContext validationContext) {
    try {
      long it = entity.getWidth();
    } catch (RuntimeException e) {
    }
  }

  public void validateFieldHeight(T entity, EntityValidationContext validationContext) {
    try {
      long it = entity.getHeight();
    } catch (RuntimeException e) {
    }
  }

  public void validateFieldFile(T entity, EntityValidationContext validationContext) {
    try {
      DFile it = entity.getFile();
      if (it == null) {
        validationContext.addFieldError("file", "file is required.");
        return;
      }
    } catch (RuntimeException e) {
    }
  }

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

  public void validate(T entity, EntityValidationContext validationContext) {
    referenceFromValidations(entity, validationContext);
    validateFieldSize(entity, validationContext);
    validateFieldWidth(entity, validationContext);
    validateFieldHeight(entity, validationContext);
    validateFieldFile(entity, validationContext);
  }

  @Override
  public T clone(T entity) {
    return null;
  }

  @Override
  public T getById(long id) {
    return null;
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
}
