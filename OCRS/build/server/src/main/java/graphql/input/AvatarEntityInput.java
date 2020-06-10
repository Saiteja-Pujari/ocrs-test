package graphql.input;

public class AvatarEntityInput implements store.IEntityInput {
  private long id;
  private D3EImageEntityInput image;
  private String createFrom;

  public String _type() {
    return "Avatar";
  }

  public D3EImageEntityInput getImage() {
    return this.image;
  }

  public void setImage(D3EImageEntityInput image) {
    this.image = image;
  }

  public String getCreateFrom() {
    return this.createFrom;
  }

  public void setCreateFrom(String createFrom) {
    this.createFrom = createFrom;
  }

  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
  }
}
