package models;

import java.util.function.Consumer;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import org.apache.solr.client.solrj.beans.Field;
import org.hibernate.annotations.Type;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;
import store.DatabaseObject;

@SolrDocument(collection = "UserSession")
@Entity
public abstract class UserSession extends CreatableObject {
  @Field
  @Indexed
  @NotNull
  @Type(type = "text")
  private String userSessionId;

  private transient UserSession old;

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
  }

  public String getUserSessionId() {
    return this.userSessionId;
  }

  public void setUserSessionId(String userSessionId) {
    this.userSessionId = userSessionId;
  }

  public UserSession getOld() {
    return this.old;
  }

  public void setOld(UserSession old) {
    this.old = old;
  }

  public String displayName() {
    return "UserSession";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof UserSession && super.equals(a);
  }

  public UserSession cloneInstance(UserSession cloneObj) {
    super.cloneInstance(cloneObj);
    cloneObj.setUserSessionId(this.getUserSessionId());
    return cloneObj;
  }
}
