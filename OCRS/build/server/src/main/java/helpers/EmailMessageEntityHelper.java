package helpers;

import d3e.core.D3EResourceHandler;
import graphql.input.EmailMessageEntityInput;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import models.EmailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.jpa.EmailMessageRepository;
import store.EntityHelper;
import store.EntityMutator;
import store.EntityValidationContext;
import store.InputHelper;

@Service("EmailMessage")
public class EmailMessageEntityHelper<T extends EmailMessage, I extends EmailMessageEntityInput>
    implements EntityHelper<T, I> {
  @Autowired protected EntityMutator mutator;
  @Autowired private EmailMessageRepository emailMessageRepository;
  @Autowired private D3EResourceHandler resourceHandler;

  public void setMutator(EntityMutator obj) {
    mutator = obj;
  }

  public EmailMessage newInstance() {
    return new EmailMessage();
  }

  @Override
  public T fromInput(I input, InputHelper helper) {
    if (input == null) {
      return null;
    }
    T newEmailMessage = ((T) new EmailMessage());
    newEmailMessage.setId(input.getId());
    return fromInput(input, newEmailMessage, helper);
  }

  @Override
  public T fromInput(I input, T entity, InputHelper helper) {
    if (helper.has("from")) {
      entity.setFrom(input.getFrom());
    }
    if (helper.has("to")) {
      entity.setTo(input.getTo().stream().collect(Collectors.toList()));
    }
    if (helper.has("bcc")) {
      entity.setBcc(input.getBcc().stream().collect(Collectors.toList()));
    }
    if (helper.has("cc")) {
      entity.setCc(input.getCc().stream().collect(Collectors.toList()));
    }
    if (helper.has("subject")) {
      entity.setSubject(input.getSubject());
    }
    if (helper.has("html")) {
      entity.setHtml(input.isHtml());
    }
    if (helper.has("body")) {
      entity.setBody(input.getBody());
    }
    if (helper.has("inlineAttachments")) {
      entity.setInlineAttachments(
          input.getInlineAttachments().stream()
              .map((one) -> helper.readDFile(one, "inlineAttachments"))
              .collect(Collectors.toList()));
    }
    if (helper.has("attachments")) {
      entity.setAttachments(
          input.getAttachments().stream()
              .map((one) -> helper.readDFile(one, "attachments"))
              .collect(Collectors.toList()));
    }
    if (helper.has("createdOn")) {
      entity.setCreatedOn(input.getCreatedOn());
    }
    if (helper.has("status")) {
      entity.setStatus(input.getStatus());
    }
    entity.updateMasters((o) -> {});
    return entity;
  }

  public void referenceFromValidations(T entity, EntityValidationContext validationContext) {}

  public void validate(T entity, EntityValidationContext validationContext) {
    referenceFromValidations(entity, validationContext);
  }

  public void setDefaultCreatedOn(T entity) {
    if (entity.getCreatedOn() != null) {
      return;
    }
    entity.setCreatedOn(LocalDateTime.now());
  }

  @Override
  public T clone(T entity) {
    return null;
  }

  @Override
  public T getById(long id) {
    return id == 0l ? null : ((T) emailMessageRepository.getOne(id));
  }

  @Override
  public void setDefaults(T entity) {
    this.setDefaultCreatedOn(entity);
  }

  @Override
  public void compute(T entity) {}

  public Boolean onDelete(T entity, EntityValidationContext deletionContext) {
    return true;
  }

  public void validateOnDelete(T entity, EntityValidationContext deletionContext) {}

  public void performFileAction(T entity) {
    entity.setInlineAttachments(
        entity.getInlineAttachments().stream()
            .filter((one) -> one != null)
            .map((one) -> resourceHandler.save(one))
            .collect(Collectors.toList()));
    entity.setAttachments(
        entity.getAttachments().stream()
            .filter((one) -> one != null)
            .map((one) -> resourceHandler.save(one))
            .collect(Collectors.toList()));
  }

  @Override
  public Boolean onCreate(T entity) {
    performFileAction(entity);
    return true;
  }

  @Override
  public Boolean onUpdate(T entity, T old) {
    performFileAction(entity);
    return true;
  }

  public T getOld(long id) {
    return ((T) getById(id).clone());
  }
}
