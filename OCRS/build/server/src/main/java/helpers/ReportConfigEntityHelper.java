package helpers;

import d3e.core.IterableExt;
import d3e.core.ListExt;
import graphql.input.ReportConfigEntityInput;
import models.ReportConfig;
import models.ReportConfigOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.jpa.ReportConfigRepository;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;
import store.InputHelper;

@Service("ReportConfig")
public class ReportConfigEntityHelper<T extends ReportConfig, I extends ReportConfigEntityInput>
    implements EntityHelper<T, I> {
  @Autowired protected EntityMutator mutator;
  @Autowired private ReportConfigRepository reportConfigRepository;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
  }

  public ReportConfig newInstance() {
    return new ReportConfig();
  }

  @Override
  public T fromInput(I input, InputHelper helper) {
    if (input == null) {
      return null;
    }
    T newReportConfig = ((T) new ReportConfig());
    newReportConfig.setId(input.getId());
    return fromInput(input, newReportConfig, helper);
  }

  @Override
  public T fromInput(I input, T entity, InputHelper helper) {
    if (helper.has("identity")) {
      entity.setIdentity(input.getIdentity());
    }
    if (helper.has("values")) {
      entity.setValues(
          IterableExt.toList(
              ListExt.map(
                  input.getValues(),
                  (objId) -> {
                    ReportConfigOptionEntityHelper valuesHelper =
                        this.mutator.getHelper(objId._type());
                    return ((ReportConfigOption) helper.readChild(objId, "values"));
                  })));
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

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

  public void validate(T entity, EntityValidationContext validationContext) {
    referenceFromValidations(entity, validationContext);
    validateFieldIdentity(entity, validationContext);
    long valuesIndex = 0l;
    for (ReportConfigOption obj : entity.getValues()) {
      ReportConfigOptionEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.validate(obj, validationContext.child("values", obj.getIdentity(), valuesIndex++));
    }
  }

  @Override
  public T clone(T entity) {
    return null;
  }

  @Override
  public T getById(long id) {
    return id == 0l ? null : ((T) reportConfigRepository.getOne(id));
  }

  @Override
  public void setDefaults(T entity) {
    for (ReportConfigOption obj : entity.getValues()) {
      ReportConfigOptionEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.setDefaults(obj);
    }
  }

  @Override
  public void compute(T entity) {
    for (ReportConfigOption obj : entity.getValues()) {
      ReportConfigOptionEntityHelper helper = mutator.getHelperByInstance(obj);
      helper.compute(obj);
    }
  }

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
