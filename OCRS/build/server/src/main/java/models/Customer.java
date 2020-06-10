package models;

import java.util.function.Consumer;
import javax.persistence.Entity;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;
import store.DatabaseObject;

@SolrDocument(collection = "Customer")
@Entity
public class Customer extends CreatableObject {
  @Field @Indexed private String phonenumber;
  @Field @Indexed private String password;
  private transient Customer old;

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
  }

  public String getPhonenumber() {
    return this.phonenumber;
  }

  public void setPhonenumber(String phonenumber) {
    this.phonenumber = phonenumber;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Customer getOld() {
    return this.old;
  }

  public void setOld(Customer old) {
    this.old = old;
  }

  public String displayName() {
    return "Customer";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof Customer && super.equals(a);
  }

  public Customer cloneInstance(Customer cloneObj) {
    if (cloneObj == null) {
      cloneObj = new Customer();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setPhonenumber(this.getPhonenumber());
    cloneObj.setPassword(this.getPassword());
    return cloneObj;
  }
}
