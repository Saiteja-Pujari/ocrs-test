package graphql;

import classes.LoginResult;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import java.util.Optional;
import java.util.UUID;
import models.AnonymousUser;
import models.Customer;
import models.EmailMessage;
import models.OneTimePassword;
import models.User;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import repository.jpa.AnonymousUserRepository;
import repository.jpa.CustomerRepository;
import repository.jpa.EmailMessageRepository;
import repository.jpa.OneTimePasswordRepository;
import repository.jpa.UserRepository;
import repository.jpa.UserSessionRepository;
import security.AppSessionProvider;
import security.JwtTokenUtil;
import security.UserProxy;

@org.springframework.stereotype.Component
public class Query implements GraphQLQueryResolver {
  @Autowired private JwtTokenUtil jwtTokenUtil;
  @Autowired private PasswordEncoder passwordEncoder;
  @Autowired private CustomerRepository customerRepository;
  @Autowired private AnonymousUserRepository anonymousUserRepository;
  @Autowired private EmailMessageRepository emailMessageRepository;
  @Autowired private OneTimePasswordRepository oneTimePasswordRepository;
  @Autowired private UserRepository userRepository;
  @Autowired private UserSessionRepository userSessionRepository;
  @Autowired private ObjectFactory<AppSessionProvider> provider;

  public Customer getCustomerById(long id, DataFetchingEnvironment env) {
    Optional<Customer> findById = customerRepository.findById(id);
    return findById.orElse(null);
  }

  public AnonymousUser getAnonymousUserById(long id, DataFetchingEnvironment env) {
    Optional<AnonymousUser> findById = anonymousUserRepository.findById(id);
    return findById.orElse(null);
  }

  public EmailMessage getEmailMessageById(long id, DataFetchingEnvironment env) {
    Optional<EmailMessage> findById = emailMessageRepository.findById(id);
    return findById.orElse(null);
  }

  public OneTimePassword getOneTimePasswordById(long id, DataFetchingEnvironment env) {
    Optional<OneTimePassword> findById = oneTimePasswordRepository.findById(id);
    return findById.orElse(null);
  }

  public Boolean checkOneTimePasswordTokenUnique(long id, String name) {
    return !(oneTimePasswordRepository.isUniqueToken(id, name));
  }

  public LoginResult loginWithOTP(String token, String code) {
    OneTimePassword otp = oneTimePasswordRepository.getByToken(token);
    LoginResult loginResult = new classes.LoginResult();
    if (otp == null) {
      loginResult.success = false;
      loginResult.failureMessage = "Invalid token.";
      return loginResult;
    }
    if (!(code.equals(otp.getCode()))) {
      loginResult.success = false;
      loginResult.failureMessage = "Invalid code.";
      return loginResult;
    }
    User user = otp.getUser();
    if (user == null) {
      loginResult.success = false;
      loginResult.failureMessage = "Invalid user.";
      return loginResult;
    }
    loginResult.success = true;
    loginResult.userObject = user;
    String type = null;
    String email = null;
    loginResult.token =
        jwtTokenUtil.generateToken(
            email, new UserProxy(type, user.getId(), UUID.randomUUID().toString()));
    return loginResult;
  }
}
