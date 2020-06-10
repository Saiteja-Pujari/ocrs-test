package models;

import java.util.function.Consumer;
import javax.persistence.Entity;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;
import store.DatabaseObject;

@SolrDocument(collection = "User")
@Entity
public abstract class User extends CreatableObject {
  @Field @Indexed private boolean isActive = false;
  private transient User old;

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
  }

  public boolean isIsActive() {
    return this.isActive;
  }

  public void setIsActive(boolean isActive) {
    this.isActive = isActive;
  }

  public User getOld() {
    return this.old;
  }

  public void setOld(User old) {
    this.old = old;
  }

  public String displayName() {
    return "";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof User && super.equals(a);
  }

  public User cloneInstance(User cloneObj) {
    super.cloneInstance(cloneObj);
    cloneObj.setIsActive(this.isIsActive());
    return cloneObj;
  }

  @Override
  public String toString() {
    return displayName();
  }
}
