package d3e.core;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import classes.EmailMessageStatus;
import models.EmailMessage;
import repository.jpa.EmailMessageRepository;

@Service
public class EmailHelper {
  @Autowired
  private EmailMessageRepository emailMessageRepo;

  @Transactional
  public void markSent(EmailMessage mail) {
    markInternal(mail, EmailMessageStatus.Sent);
  }

  @Transactional
  public void markSendingFailed(EmailMessage mail) {
    markInternal(mail, EmailMessageStatus.Failed);
  }

  @Transactional
  public void markScheduled(EmailMessage mail) {
    markInternal(mail, EmailMessageStatus.Scheduled);
  }

  private void markInternal(EmailMessage mail, EmailMessageStatus status) {
    EmailMessage fromDb = emailMessageRepo.getOne(mail.getId());
    if (fromDb == null) {
      return;
    }
    fromDb.setStatus(status);
  }
}
