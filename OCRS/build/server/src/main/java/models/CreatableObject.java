package models;

import java.time.LocalDateTime;

import store.DBSaveStatus;
import store.DatabaseObject;

public abstract class CreatableObject extends DatabaseObject {
  private LocalDateTime createdDate;
  private LocalDateTime lastModifiedDate;
  private User createdBy;
  private User lastModifiedBy;
  private int version;

  public LocalDateTime getCreatedDate() {
    return this.createdDate;
  }

  public void setCreatedDate(LocalDateTime cd) {
    this.createdDate = cd;
  }

  public LocalDateTime getLastModifiedDate() {
    return this.lastModifiedDate;
  }

  public void setLastModifiedDate(LocalDateTime lmd) {
    this.lastModifiedDate = lmd;
  }

  public User getCreatedBy() {
    return this.createdBy;
  }

  public void setCreatedBy(User cb) {
    this.createdBy = cb;
  }

  public User getLastModifiedBy() {
    return this.lastModifiedBy;
  }

  public void setLastModifiedBy(User lmb) {
    this.lastModifiedBy = lmb;
  }

  public DBSaveStatus getSaveStatus() {
    return this.saveStatus;
  }

  public void setSaveStatus(DBSaveStatus ss) {
    this.saveStatus = ss;
  }

  public int getVersion() {
    return this.version;
  }

  public void setVersion(int v) {
    this.version = v;
  }

  public void cloneInstance(CreatableObject cloneObj) {
    super.cloneInstance(cloneObj);
    cloneObj.setVersion(this.getVersion());
    cloneObj.setCreatedDate(this.getCreatedDate());
    cloneObj.setLastModifiedDate(this.getLastModifiedDate());
    cloneObj.setCreatedBy(this.getCreatedBy());
    cloneObj.setLastModifiedBy(this.getLastModifiedBy());
    cloneObj.setSaveStatus(this.getSaveStatus());
  }

  public CreatableObject getOld() {
    return null;
  }

  public void setOld(CreatableObject old) {
  }
}
