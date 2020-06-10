package helpers;

import graphql.input.ReportConfigOptionEntityInput;
import models.ReportConfigOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.jpa.ReportConfigOptionRepository;
import repository.jpa.ReportConfigRepository;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;
import store.InputHelper;

@Service("ReportConfigOption")
public class ReportConfigOptionEntityHelper<
        T extends ReportConfigOption, I extends ReportConfigOptionEntityInput>
    implements EntityHelper<T, I> {
  @Autowired protected EntityMutator mutator;
  @Autowired private ReportConfigOptionRepository reportConfigOptionRepository;
  @Autowired private ReportConfigRepository reportConfigRepository;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
  }

  public ReportConfigOption newInstance() {
    return new ReportConfigOption();
  }

  @Override
  public T fromInput(I input, InputHelper helper) {
    if (input == null) {
      return null;
    }
    T newReportConfigOption = ((T) new ReportConfigOption());
    newReportConfigOption.setId(input.getId());
    return fromInput(input, newReportConfigOption, helper);
  }

  @Override
  public T fromInput(I input, T entity, InputHelper helper) {
    if (helper.has("identity")) {
      entity.setIdentity(input.getIdentity());
    }
    if (helper.has("value")) {
      entity.setValue(input.getValue());
    }
    return entity;
  }

  public void validateFieldIdentity(T entity, EntityValidationContext validationContext) {
    try {
      String it = entity.getIdentity();
      if (it == null) {
        validationContext.addFieldError("identity", "identity is required.");
        return;
      }
    } catch (RuntimeException e) {
    }
  }

  public void validateFieldValue(T entity, EntityValidationContext validationContext) {
    try {
      String it = entity.getValue();
      if (it == null) {
        validationContext.addFieldError("value", "value is required.");
        return;
      }
    } catch (RuntimeException e) {
    }
  }

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

  public void validate(T entity, EntityValidationContext validationContext) {
    referenceFromValidations(entity, validationContext);
    validateFieldIdentity(entity, validationContext);
    validateFieldValue(entity, validationContext);
  }

  @Override
  public T clone(T entity) {
    return null;
  }

  @Override
  public T getById(long id) {
    return id == 0l ? null : ((T) reportConfigOptionRepository.getOne(id));
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
