package graphql.input;

import java.util.List;

public class ReportConfigEntityInput implements store.IEntityInput {
  private long id;
  private String identity;
  private List<ReportConfigOptionEntityInput> values;

  public String _type() {
    return "ReportConfig";
  }

  public String getIdentity() {
    return this.identity;
  }

  public void setIdentity(String identity) {
    this.identity = identity;
  }

  public List<ReportConfigOptionEntityInput> getValues() {
    return this.values;
  }

  public void setValues(List<ReportConfigOptionEntityInput> values) {
    this.values = values;
  }

  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
  }
}
