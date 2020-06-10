package graphql.input;

public class CustomerEntityInput implements store.IEntityInput {
  private long id;
  private String phonenumber;
  private String password;

  public String _type() {
    return "Customer";
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

  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
  }
}
