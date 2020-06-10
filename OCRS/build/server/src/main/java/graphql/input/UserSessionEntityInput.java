package graphql.input;

public abstract class UserSessionEntityInput implements store.IEntityInput {
  private long id;
  private String userSessionId;

  public String _type() {
    return "UserSession";
  }

  public String getUserSessionId() {
    return this.userSessionId;
  }

  public void setUserSessionId(String userSessionId) {
    this.userSessionId = userSessionId;
  }

  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
  }
}
