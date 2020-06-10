package graphql.input;

public class ReportConfigOptionEntityInput implements store.IEntityInput {
  private long id;
  private String identity;
  private String value;

  public String _type() {
    return "ReportConfigOption";
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

  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
  }
}
