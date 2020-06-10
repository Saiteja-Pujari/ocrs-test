package models;

import d3e.core.DFile;
import java.util.function.Consumer;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import org.apache.solr.client.solrj.beans.Field;
import org.hibernate.annotations.Cascade;
import org.springframework.data.solr.core.mapping.Indexed;
import store.DatabaseObject;

@Embeddable
public class D3EImage {
  @Field @Indexed private long size = 0l;
  @Field @Indexed private long width = 0l;
  @Field @Indexed private long height = 0l;

  @Field
  @Indexed
  @ManyToOne(fetch = FetchType.LAZY)
  @Cascade(value = {org.hibernate.annotations.CascadeType.PERSIST})
  private DFile file;

  private transient Avatar masterAvatar;

  public Object _masterObject() {
    if (null != masterAvatar) {
      return masterAvatar;
    }
    return null;
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {}

  public long getSize() {
    return this.size;
  }

  public void setSize(long size) {
    this.size = size;
  }

  public long getWidth() {
    return this.width;
  }

  public void setWidth(long width) {
    this.width = width;
  }

  public long getHeight() {
    return this.height;
  }

  public void setHeight(long height) {
    this.height = height;
  }

  public DFile getFile() {
    return this.file;
  }

  public void setFile(DFile file) {
    this.file = file;
  }

  public Avatar getMasterAvatar() {
    return this.masterAvatar;
  }

  public void setMasterAvatar(Avatar masterAvatar) {
    this.masterAvatar = masterAvatar;
  }

  public String displayName() {
    return "D3EImage";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof D3EImage && super.equals(a);
  }

  public D3EImage cloneInstance(D3EImage cloneObj) {
    if (cloneObj == null) {
      cloneObj = new D3EImage();
    }
    cloneObj.setSize(this.getSize());
    cloneObj.setWidth(this.getWidth());
    cloneObj.setHeight(this.getHeight());
    cloneObj.setFile(this.getFile());
    return cloneObj;
  }
}
