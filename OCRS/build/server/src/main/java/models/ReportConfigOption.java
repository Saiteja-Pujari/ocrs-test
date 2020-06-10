package models;

import java.util.function.Consumer;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.Indexed;
import store.DatabaseObject;

@Entity
public class ReportConfigOption extends DatabaseObject {
  @Field @Indexed @NotNull private String identity;
  @Field @Indexed @NotNull private String value;
  @Field @ManyToOne private ReportConfig masterReportConfig;

  public Object _masterObject() {
    if (null != masterReportConfig) {
      return masterReportConfig;
    }
    return null;
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
  }

  public void updateFlat(DatabaseObject obj) {
    super.updateFlat(obj);
    if (masterReportConfig != null) {
      masterReportConfig.updateFlat(obj);
    }
  }

  public String getIdentity() {
    return this.identity;
  }

  public void setIdentity(String identity) {
    this.identity = identity;
  }

  public String getValue() {
    return this.value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public ReportConfig getMasterReportConfig() {
    return this.masterReportConfig;
  }

  public void setMasterReportConfig(ReportConfig masterReportConfig) {
    this.masterReportConfig = masterReportConfig;
  }

  public String displayName() {
    return "ReportConfigOption";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof ReportConfigOption && super.equals(a);
  }

  public ReportConfigOption cloneInstance(ReportConfigOption cloneObj) {
    if (cloneObj == null) {
      cloneObj = new ReportConfigOption();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setIdentity(this.getIdentity());
    cloneObj.setValue(this.getValue());
    return cloneObj;
  }
}
