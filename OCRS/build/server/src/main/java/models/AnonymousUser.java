package models;

import java.util.function.Consumer;
import javax.persistence.Entity;
import org.springframework.data.solr.core.mapping.SolrDocument;
import store.DatabaseObject;

@SolrDocument(collection = "AnonymousUser")
@Entity
public class AnonymousUser extends User {
  private transient AnonymousUser old;

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
  }

  public AnonymousUser getOld() {
    return this.old;
  }

  public void setOld(AnonymousUser old) {
    this.old = old;
  }

  public String displayName() {
    return "AnonymousUser";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof AnonymousUser && super.equals(a);
  }

  public AnonymousUser cloneInstance(AnonymousUser cloneObj) {
    if (cloneObj == null) {
      cloneObj = new AnonymousUser();
    }
    super.cloneInstance(cloneObj);
    return cloneObj;
  }
}
