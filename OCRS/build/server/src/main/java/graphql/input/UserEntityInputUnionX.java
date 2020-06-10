package graphql.input;

public class UserEntityInputUnionX implements store.IEntityInput {
  private String type;
  private AnonymousUserEntityInput valueAnonymousUser;

  public String _type() {
    return type;
  }

  public long getId() {
    return 0l;
  }

  public void setType(String type) {
    this.type = type;
  }

  public AnonymousUserEntityInput getValueAnonymousUser() {
    return this.valueAnonymousUser;
  }

  public void setValueAnonymousUser(AnonymousUserEntityInput valueAnonymousUser) {
    this.valueAnonymousUser = valueAnonymousUser;
  }

  public UserEntityInput getValue() {
    switch (type) {
      case "AnonymousUser":
        {
          return this.valueAnonymousUser;
        }
      default:
        {
          return null;
        }
    }
  }
}
