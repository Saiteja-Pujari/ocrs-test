package models;

import java.util.function.Consumer;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.ChildDocument;
import org.springframework.data.solr.core.mapping.Indexed;
import store.DatabaseObject;

@Entity
public class Avatar extends DatabaseObject {
  @Field @Indexed @ChildDocument @Embedded private D3EImage image;
  @Field @Indexed private String createFrom;

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
    if (image != null) {
      image.setMasterAvatar(this);
      image.updateMasters(visitor);
    }
  }

  public D3EImage getImage() {
    return this.image;
  }

  public void setImage(D3EImage image) {
    this.image = image;
  }

  public String getCreateFrom() {
    return this.createFrom;
  }

  public void setCreateFrom(String createFrom) {
    this.createFrom = createFrom;
  }

  public String displayName() {
    return "Avatar";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof Avatar && super.equals(a);
  }

  public Avatar cloneInstance(Avatar cloneObj) {
    if (cloneObj == null) {
      cloneObj = new Avatar();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setImage(this.getImage() == null ? null : this.getImage().cloneInstance(null));
    cloneObj.setCreateFrom(this.getCreateFrom());
    return cloneObj;
  }
}
