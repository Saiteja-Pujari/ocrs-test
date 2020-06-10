package graphql.input;

import d3e.core.DFileEntityInput;

public class D3EImageEntityInput implements store.IEntityInput {
  private long id;
  private long size;
  private long width;
  private long height;
  private DFileEntityInput file;

  public String _type() {
    return "D3EImage";
  }

  public long getSize() {
    return this.size;
  }

  public void setSize(long size) {
    this.size = size;
  }

  public long getWidth() {
    return this.width;
  }

  public void setWidth(long width) {
    this.width = width;
  }

  public long getHeight() {
    return this.height;
  }

  public void setHeight(long height) {
    this.height = height;
  }

  public DFileEntityInput getFile() {
    return this.file;
  }

  public void setFile(DFileEntityInput file) {
    this.file = file;
  }

  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
  }
}
