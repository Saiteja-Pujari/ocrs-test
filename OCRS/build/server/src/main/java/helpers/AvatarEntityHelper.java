package helpers;

import d3e.core.D3EResourceHandler;
import d3e.core.ListExt;
import graphql.input.AvatarEntityInput;
import models.Avatar;
import models.D3EImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.jpa.AvatarRepository;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;
import store.InputHelper;

@Service("Avatar")
public class AvatarEntityHelper<T extends Avatar, I extends AvatarEntityInput>
    implements EntityHelper<T, I> {
  @Autowired protected EntityMutator mutator;
  @Autowired private AvatarRepository avatarRepository;
  @Autowired private D3EResourceHandler resourceHandler;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
  }

  public Avatar newInstance() {
    return new Avatar();
  }

  @Override
  public T fromInput(I input, InputHelper helper) {
    if (input == null) {
      return null;
    }
    T newAvatar = ((T) new Avatar());
    newAvatar.setId(input.getId());
    return fromInput(input, newAvatar, helper);
  }

  @Override
  public T fromInput(I input, T entity, InputHelper helper) {
    if (helper.has("image")) {
      entity.setImage(helper.readEmbedded(input.getImage(), "image"));
    }
    if (helper.has("createFrom")) {
      entity.setCreateFrom(input.getCreateFrom());
    }
    return entity;
  }

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

  public void validate(T entity, EntityValidationContext validationContext) {
    referenceFromValidations(entity, validationContext);
    long imageIndex = 0l;
    if (entity.getImage() != null) {
      D3EImageEntityHelper helper = mutator.getHelperByInstance(entity.getImage());
      helper.validate(entity.getImage(), validationContext.child("image", null, 0l));
    }
  }

  @Override
  public T clone(T entity) {
    return null;
  }

  @Override
  public T getById(long id) {
    return id == 0l ? null : ((T) avatarRepository.getOne(id));
  }

  @Override
  public void setDefaults(T entity) {
    if (entity.getImage() != null) {
      D3EImageEntityHelper helper = mutator.getHelperByInstance(entity.getImage());
      helper.setDefaults(entity.getImage());
    }
  }

  @Override
  public void compute(T entity) {
    if (entity.getImage() != null) {
      D3EImageEntityHelper helper = mutator.getHelperByInstance(entity.getImage());
      helper.compute(entity.getImage());
    }
  }

  public Boolean onDelete(T entity, EntityValidationContext deletionContext) {
    return true;
  }

  public void validateOnDelete(T entity, EntityValidationContext deletionContext) {}

  public void performImageAction(T entity) {
    if (entity.getImage() != null && entity.getImage().getFile() != null) {
      entity
          .getImage()
          .setFile(resourceHandler.saveImage(entity.getImage().getFile(), ListExt.List()));
    }
  }

  @Override
  public Boolean onCreate(T entity) {
    performImageAction(entity);
    return true;
  }

  @Override
  public Boolean onUpdate(T entity, T old) {
    performImageAction(entity);
    return true;
  }
}
