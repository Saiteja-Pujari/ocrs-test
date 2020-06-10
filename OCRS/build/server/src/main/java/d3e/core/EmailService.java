package d3e.core;

import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import models.EmailMessage;

@Service
public class EmailService implements Runnable {
  @Autowired
  private Environment env;

  @Autowired
  private EmailHelper helper;

  private LinkedBlockingQueue<EmailMessage> emails = new LinkedBlockingQueue<>();

  private static final Pattern variables = Pattern.compile("\\{(.*?)\\}");

  private final long SLEEP_TIME = 1_000L;

  @PostConstruct
  public void init() {
    new Thread(this).start();
  }

  public void send(EmailMessage mail) {
    if (mail == null) {
      return;
    }
    helper.markScheduled(mail);
    pushEmail(mail);
  }

  @Override
  public void run() {
    while (true) {
      EmailMessage mail = null;
      try {
        mail = emails.take();
        sendEmail(mail);
        helper.markSent(mail);
      } catch (MessagingException e) {
        if (mail != null) {
          helper.markSendingFailed(mail);
        }
      } catch (InterruptedException e) {
      }
    }
  }

  private synchronized void pushEmail(EmailMessage mail) {
    emails.add(mail);
  }

  private String getEnvString(String str) {
    return EnvironmentHelper.getEnvString(env, str);
  }

  private void sendEmail(EmailMessage email) throws AddressException, MessagingException {
    Properties prop = new Properties();

    prop.put("mail.smtp.auth", true);
    prop.put("mail.smtp.starttls.enable", true);
    prop.put("mail.smtp.host", getEnvString("{env.mail.smtp.host}"));
    prop.put("mail.smtp.port", getEnvString("{env.mail.smtp.port}"));

    String username = getEnvString("{env.mail.uname}");
    String password = getEnvString("{env.mail.pwd}");
    email.setFrom(getEnvString("{env.mail.sender}"));

    Session session = Session.getInstance(prop, new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
      }
    });
    Message message = new MimeMessage(session);
    message.setFrom(new InternetAddress(email.getFrom()));
    message.setRecipients(RecipientType.TO, InternetAddress.parse(String.join(",", email.getTo())));
    message.setRecipients(RecipientType.CC, InternetAddress.parse(String.join(",", email.getCc())));
    message.setRecipients(RecipientType.BCC, InternetAddress.parse(String.join(",", email.getBcc())));
    message.setSubject(email.getSubject());
    message.setText(email.getBody());
    Transport.send(message);
  }
}
