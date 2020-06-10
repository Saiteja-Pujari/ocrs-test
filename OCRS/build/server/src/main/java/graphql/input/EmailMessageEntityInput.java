package graphql.input;

import classes.EmailMessageStatus;
import d3e.core.DFileEntityInput;
import java.time.LocalDateTime;
import java.util.List;

public class EmailMessageEntityInput implements store.IEntityInput {
  private long id;
  private String from;
  private List<String> to;
  private List<String> bcc;
  private List<String> cc;
  private String subject;
  private boolean html;
  private String body;
  private List<DFileEntityInput> inlineAttachments;
  private List<DFileEntityInput> attachments;
  private LocalDateTime createdOn;
  private EmailMessageStatus status;

  public String _type() {
    return "EmailMessage";
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
    this.to = to;
  }

  public List<String> getBcc() {
    return this.bcc;
  }

  public void setBcc(List<String> bcc) {
    this.bcc = bcc;
  }

  public List<String> getCc() {
    return this.cc;
  }

  public void setCc(List<String> cc) {
    this.cc = cc;
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

  public List<DFileEntityInput> getInlineAttachments() {
    return this.inlineAttachments;
  }

  public void setInlineAttachments(List<DFileEntityInput> inlineAttachments) {
    this.inlineAttachments = inlineAttachments;
  }

  public List<DFileEntityInput> getAttachments() {
    return this.attachments;
  }

  public void setAttachments(List<DFileEntityInput> attachments) {
    this.attachments = attachments;
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

  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
  }
}
