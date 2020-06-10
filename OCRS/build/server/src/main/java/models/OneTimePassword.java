package models;

import java.time.LocalDateTime;
import java.util.function.Consumer;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;
import store.DatabaseObject;

@SolrDocument(collection = "OneTimePassword")
@Entity
public class OneTimePassword extends CreatableObject {
  @Field @Indexed @NotNull private boolean success = false;
  @Field @Indexed private String errorMsg;
  @Field @Indexed private String token;
  @Field @Indexed private String code;

  @Field
  @Indexed
  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  @Field @Indexed private LocalDateTime expiry;
  private transient OneTimePassword old;

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
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

  public User getUser() {
    return this.user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public LocalDateTime getExpiry() {
    return this.expiry;
  }

  public void setExpiry(LocalDateTime expiry) {
    this.expiry = expiry;
  }

  public OneTimePassword getOld() {
    return this.old;
  }

  public void setOld(OneTimePassword old) {
    this.old = old;
  }

  public String displayName() {
    return "OneTimePassword";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof OneTimePassword && super.equals(a);
  }

  public OneTimePassword cloneInstance(OneTimePassword cloneObj) {
    if (cloneObj == null) {
      cloneObj = new OneTimePassword();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setSuccess(this.isSuccess());
    cloneObj.setErrorMsg(this.getErrorMsg());
    cloneObj.setToken(this.getToken());
    cloneObj.setCode(this.getCode());
    cloneObj.setUser(this.getUser());
    cloneObj.setExpiry(this.getExpiry());
    return cloneObj;
  }
}
