package graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import d3e.core.EmailService;
import d3e.core.ListExt;
import java.util.Random;
import models.EmailMessage;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import repository.jpa.AnonymousUserRepository;
import repository.jpa.CustomerRepository;
import repository.jpa.EmailMessageRepository;
import repository.jpa.OneTimePasswordRepository;
import repository.jpa.UserRepository;
import repository.jpa.UserSessionRepository;
import security.AppSessionProvider;
import store.EntityMutator;

@org.springframework.stereotype.Component
public class Mutation implements GraphQLMutationResolver {
  @Autowired private EntityMutator mutator;
  @Autowired private CustomerRepository customerRepository;
  @Autowired private AnonymousUserRepository anonymousUserRepository;
  @Autowired private EmailMessageRepository emailMessageRepository;
  @Autowired private OneTimePasswordRepository oneTimePasswordRepository;
  @Autowired private UserRepository userRepository;
  @Autowired private UserSessionRepository userSessionRepository;
  @Autowired private ObjectFactory<AppSessionProvider> provider;
  @Autowired private EmailService emailService;

  public Mutation() {}

  private void sendEmail(String email, String code) {
    EmailMessage msg = new EmailMessage();
    msg.setTo(ListExt.asList(email));
    msg.setSubject("One Time Password.");
    msg.setBody("Your OTP is: " + code + ". It is valid for 10 minutes.");
    this.mutator.save(msg);
    this.emailService.send(msg);
  }

  private String generateToken() {
    char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    return generateRandomString(chars, 32);
  }

  private String generateCode() {
    char[] digits = "1234567890".toCharArray();
    return generateRandomString(digits, 4);
  }

  private String generateRandomString(char[] array, int length) {
    StringBuilder sb = new StringBuilder(length);
    Random rnd = new Random();
    for (int i = 0; i < length; i++) {
      char c = array[rnd.nextInt(array.length)];
      sb.append(c);
    }
    return sb.toString();
  }
}
