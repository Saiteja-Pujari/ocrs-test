package graphql.input;

import java.time.LocalDateTime;

public class OneTimePasswordEntityInput implements store.IEntityInput {
  private long id;
  private boolean success;
  private String errorMsg;
  private String token;
  private String code;
  private ObjectRef user;
  private LocalDateTime expiry;

  public String _type() {
    return "OneTimePassword";
  }

  public boolean isSuccess() {
    return this.success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getErrorMsg() {
    return this.errorMsg;
  }

  public void setErrorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
  }

  public String getToken() {
    return this.token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getCode() {
    return this.code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public ObjectRef getUser() {
    return this.user;
  }

  public void setUser(ObjectRef user) {
    this.user = user;
  }

  public LocalDateTime getExpiry() {
    return this.expiry;
  }

  public void setExpiry(LocalDateTime expiry) {
    this.expiry = expiry;
  }

  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
  }
}
