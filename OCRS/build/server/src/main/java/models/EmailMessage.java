package models;

import classes.EmailMessageStatus;
import d3e.core.DFile;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import org.apache.solr.client.solrj.beans.Field;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Type;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;
import store.DatabaseObject;

@SolrDocument(collection = "EmailMessage")
@Entity
public class EmailMessage extends CreatableObject {
  @Field @Indexed private String from;
  @Field @Indexed @ElementCollection private List<String> to = new ArrayList<>();
  @Field @Indexed @ElementCollection private List<String> bcc = new ArrayList<>();
  @Field @Indexed @ElementCollection private List<String> cc = new ArrayList<>();

  @Field
  @Indexed
  @Type(type = "text")
  private String subject;

  @Field @Indexed private boolean html = false;

  @Field
  @Indexed
  @Type(type = "text")
  private String body;

  @Field
  @Indexed
  @ManyToMany
  @Cascade(value = {org.hibernate.annotations.CascadeType.PERSIST})
  private List<DFile> inlineAttachments = new ArrayList<>();

  @Field
  @Indexed
  @ManyToMany
  @Cascade(value = {org.hibernate.annotations.CascadeType.PERSIST})
  private List<DFile> attachments = new ArrayList<>();

  @Field @Indexed private LocalDateTime createdOn;

  @Field
  @Indexed
  @Enumerated(javax.persistence.EnumType.STRING)
  private EmailMessageStatus status = EmailMessageStatus.Scheduled;

  private transient EmailMessage old;

  public void addToTo(String val, long index) {
    if (index == -1) {
      this.to.add(val);
    } else {
      this.to.add(((int) index), val);
    }
  }

  public void removeFromTo(String val) {
    this.to.remove(val);
  }

  public void addToBcc(String val, long index) {
    if (index == -1) {
      this.bcc.add(val);
    } else {
      this.bcc.add(((int) index), val);
    }
  }

  public void removeFromBcc(String val) {
    this.bcc.remove(val);
  }

  public void addToCc(String val, long index) {
    if (index == -1) {
      this.cc.add(val);
    } else {
      this.cc.add(((int) index), val);
    }
  }

  public void removeFromCc(String val) {
    this.cc.remove(val);
  }

  public void addToInlineAttachments(DFile val, long index) {
    if (index == -1) {
      this.inlineAttachments.add(val);
    } else {
      this.inlineAttachments.add(((int) index), val);
    }
  }

  public void removeFromInlineAttachments(DFile val) {
    this.inlineAttachments.remove(val);
  }

  public void addToAttachments(DFile val, long index) {
    if (index == -1) {
      this.attachments.add(val);
    } else {
      this.attachments.add(((int) index), val);
    }
  }

  public void removeFromAttachments(DFile val) {
    this.attachments.remove(val);
  }

  public void updateMasters(Consumer<DatabaseObject> visitor) {
    super.updateMasters(visitor);
  }

  public String getFrom() {
    return this.from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public List<String> getTo() {
    return this.to;
  }

  public void setTo(List<String> to) {
    this.to.clear();
    this.to.addAll(to);
  }

  public List<String> getBcc() {
    return this.bcc;
  }

  public void setBcc(List<String> bcc) {
    this.bcc.clear();
    this.bcc.addAll(bcc);
  }

  public List<String> getCc() {
    return this.cc;
  }

  public void setCc(List<String> cc) {
    this.cc.clear();
    this.cc.addAll(cc);
  }

  public String getSubject() {
    return this.subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public boolean isHtml() {
    return this.html;
  }

  public void setHtml(boolean html) {
    this.html = html;
  }

  public String getBody() {
    return this.body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public List<DFile> getInlineAttachments() {
    return this.inlineAttachments;
  }

  public void setInlineAttachments(List<DFile> inlineAttachments) {
    this.inlineAttachments.clear();
    this.inlineAttachments.addAll(inlineAttachments);
  }

  public List<DFile> getAttachments() {
    return this.attachments;
  }

  public void setAttachments(List<DFile> attachments) {
    this.attachments.clear();
    this.attachments.addAll(attachments);
  }

  public LocalDateTime getCreatedOn() {
    return this.createdOn;
  }

  public void setCreatedOn(LocalDateTime createdOn) {
    this.createdOn = createdOn;
  }

  public EmailMessageStatus getStatus() {
    return this.status;
  }

  public void setStatus(EmailMessageStatus status) {
    this.status = status;
  }

  public EmailMessage getOld() {
    return this.old;
  }

  public void setOld(EmailMessage old) {
    this.old = old;
  }

  public String displayName() {
    return "EmailMessage";
  }

  @Override
  public boolean equals(Object a) {
    return a instanceof EmailMessage && super.equals(a);
  }

  public EmailMessage cloneInstance(EmailMessage cloneObj) {
    if (cloneObj == null) {
      cloneObj = new EmailMessage();
    }
    super.cloneInstance(cloneObj);
    cloneObj.setFrom(this.getFrom());
    cloneObj.setTo(new ArrayList<>(this.getTo()));
    cloneObj.setBcc(new ArrayList<>(this.getBcc()));
    cloneObj.setCc(new ArrayList<>(this.getCc()));
    cloneObj.setSubject(this.getSubject());
    cloneObj.setHtml(this.isHtml());
    cloneObj.setBody(this.getBody());
    cloneObj.setInlineAttachments(new ArrayList<>(this.getInlineAttachments()));
    cloneObj.setAttachments(new ArrayList<>(this.getAttachments()));
    cloneObj.setCreatedOn(this.getCreatedOn());
    cloneObj.setStatus(this.getStatus());
    return cloneObj;
  }
}
