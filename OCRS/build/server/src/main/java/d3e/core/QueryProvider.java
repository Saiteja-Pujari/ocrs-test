package d3e.core;

import classes.LoginResult;
import java.util.Optional;
import javax.annotation.PostConstruct;
import models.AnonymousUser;
import models.Customer;
import models.EmailMessage;
import models.OneTimePassword;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.jpa.AnonymousUserRepository;
import repository.jpa.AvatarRepository;
import repository.jpa.CustomerRepository;
import repository.jpa.EmailMessageRepository;
import repository.jpa.OneTimePasswordRepository;
import repository.jpa.ReportConfigOptionRepository;
import repository.jpa.ReportConfigRepository;
import repository.jpa.UserRepository;
import repository.jpa.UserSessionRepository;
import security.AppSessionProvider;
import security.JwtTokenUtil;

@Service
public class QueryProvider {
  public static QueryProvider instance;
  @Autowired private JwtTokenUtil jwtTokenUtil;
  @Autowired private CustomerRepository customerRepository;
  @Autowired private AnonymousUserRepository anonymousUserRepository;
  @Autowired private AvatarRepository avatarRepository;
  @Autowired private EmailMessageRepository emailMessageRepository;
  @Autowired private OneTimePasswordRepository oneTimePasswordRepository;
  @Autowired private ReportConfigRepository reportConfigRepository;
  @Autowired private ReportConfigOptionRepository reportConfigOptionRepository;
  @Autowired private UserRepository userRepository;
  @Autowired private UserSessionRepository userSessionRepository;
  @Autowired private ObjectFactory<AppSessionProvider> provider;

  @PostConstruct
  public void init() {
    instance = this;
  }

  public static QueryProvider get() {
    return instance;
  }

  public Customer getCustomerById(long id) {
    Optional<Customer> findById = customerRepository.findById(id);
    return findById.orElse(null);
  }

  public AnonymousUser getAnonymousUserById(long id) {
    Optional<AnonymousUser> findById = anonymousUserRepository.findById(id);
    return findById.orElse(null);
  }

  public EmailMessage getEmailMessageById(long id) {
    Optional<EmailMessage> findById = emailMessageRepository.findById(id);
    return findById.orElse(null);
  }

  public OneTimePassword getOneTimePasswordById(long id) {
    Optional<OneTimePassword> findById = oneTimePasswordRepository.findById(id);
    return findById.orElse(null);
  }

  public Boolean checkOneTimePasswordTokenUnique(long id, String name) {
    return !(oneTimePasswordRepository.isUniqueToken(id, name));
  }

  public LoginResult loginWithOTP(String token, String code) {
    OneTimePassword otp = oneTimePasswordRepository.getByToken(token);
    LoginResult loginResult = new LoginResult();
    loginResult.success = true;
    loginResult.userObject = otp.getUser();
    loginResult.token = token;
    return loginResult;
  }
}
