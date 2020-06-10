package models;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.ChildDocument;
import org.springframework.data.solr.core.mapping.Indexed;
import store.DatabaseObject;

@Entity
public class ReportConfig extends DatabaseObject {
  @Field @Indexed @NotNull private String identity;

  @Field
  @Indexed
  @ChildDocument
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ReportConfigOption> values = new ArrayList<>();

  public void addToValues(ReportConfigOption val, long index) {
    val.setMasterReportConfig(this);
    if (index == -1) {
      this.values.add(val);
    } else {
      this.values.add(((int) index), val);
    }
  }

  public void removeFromValues(ReportConfigOption val) {
    this.values.remove(val);
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
    for (ReportConfigOption obj : this.getValues()) {
      visitor.accept(obj);
      obj.setMasterReportConfig(this);
      obj.updateMasters(visitor);
    }
  }

  public String getIdentity() {
    return this.identity;
  }

  public void setIdentity(String identity) {
    this.identity = identity;
  }

  public List<ReportConfigOption> getValues() {
    return this.values;
  }

  public void setValues(List<ReportConfigOption> values) {
    this.values.clear();
    this.values.addAll(values);
  }

  public String displayName() {
    return "ReportConfig";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof ReportConfig && super.equals(a);
  }

  public ReportConfig cloneInstance(ReportConfig cloneObj) {
    if (cloneObj == null) {
      cloneObj = new ReportConfig();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setIdentity(this.getIdentity());
    cloneObj.setValues(
        this.getValues().stream()
            .map((ReportConfigOption colObj) -> colObj.cloneInstance(null))
            .collect(Collectors.toList()));
    return cloneObj;
  }
}
