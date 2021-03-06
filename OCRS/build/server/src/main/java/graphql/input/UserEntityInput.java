package graphql.input;

public abstract class UserEntityInput implements store.IEntityInput {
  private long id;
  private boolean isActive;

  public String _type() {
    return "User";
  }

  public boolean isIsActive() {
    return this.isActive;
  }

  public void setIsActive(boolean isActive) {
    this.isActive = isActive;
  }

  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
  }
}
