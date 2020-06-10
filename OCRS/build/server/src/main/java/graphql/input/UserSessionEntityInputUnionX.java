package graphql.input;

public class UserSessionEntityInputUnionX implements store.IEntityInput {
  private String type;

  public String _type() {
    return type;
  }

  public long getId() {
    return 0l;
  }

  public void setType(String type) {
    this.type = type;
  }

  public UserSessionEntityInput getValue() {
    switch (type) {
      default:
        {
          return null;
        }
    }
  }
}
