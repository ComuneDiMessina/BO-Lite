package it.almaviva.impleme.bolite.integration.model.documentale;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Document
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-08-09T11:41:08.520+02:00")

public class Document   {
  @JsonProperty("filename")
  private String filename = null;

  @JsonProperty("user")
  private String user = "l'utente a cui appartiene il token di autorizzazione";

  @JsonProperty("readgroups")
  @Valid
  private List<String> readgroups = null;

  @JsonProperty("writegroups")
  @Valid
  private List<String> writegroups = null;

  @JsonProperty("id_file")
  private String idFile = null;

  @JsonProperty("created_at")
  private String createdAt = null;

  @JsonProperty("version")
  private Integer version = null;

  @JsonProperty("content")
  private String content = null;

  @JsonProperty("ft")
  private String ft = null;

  public Document filename(String filename) {
    this.filename = filename;
    return this;
  }

  /**
   * nome del documento
   * @return filename
  **/
  @ApiModelProperty(value = "nome del documento")


  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  public Document user(String user) {
    this.user = user;
    return this;
  }

  /**
   * il nome dell'utente a cui appartiene il documento
   * @return user
  **/
  @ApiModelProperty(value = "il nome dell'utente a cui appartiene il documento")


  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public Document readgroups(List<String> readgroups) {
    this.readgroups = readgroups;
    return this;
  }

  public Document addReadgroupsItem(String readgroupsItem) {
    if (this.readgroups == null) {
      this.readgroups = new ArrayList<String>();
    }
    this.readgroups.add(readgroupsItem);
    return this;
  }

  /**
   * il nome del gruppo a cui appartiene il documento
   * @return readgroups
  **/
  @ApiModelProperty(value = "il nome del gruppo a cui appartiene il documento")


  public List<String> getReadgroups() {
    return readgroups;
  }

  public void setReadgroups(List<String> readgroups) {
    this.readgroups = readgroups;
  }

  public Document writegroups(List<String> writegroups) {
    this.writegroups = writegroups;
    return this;
  }

  public Document addWritegroupsItem(String writegroupsItem) {
    if (this.writegroups == null) {
      this.writegroups = new ArrayList<String>();
    }
    this.writegroups.add(writegroupsItem);
    return this;
  }

  /**
   * il nome del gruppo a cui appartiene il documento
   * @return writegroups
  **/
  @ApiModelProperty(value = "il nome del gruppo a cui appartiene il documento")


  public List<String> getWritegroups() {
    return writegroups;
  }

  public void setWritegroups(List<String> writegroups) {
    this.writegroups = writegroups;
  }

  public Document idFile(String idFile) {
    this.idFile = idFile;
    return this;
  }

  /**
   * identificativo univoco assegnato dal sistema
   * @return idFile
  **/
  @ApiModelProperty(value = "identificativo univoco assegnato dal sistema")


  public String getIdFile() {
    return idFile;
  }

  public void setIdFile(String idFile) {
    this.idFile = idFile;
  }

  public Document createdAt(String createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * momento in cui � stato creato il documento in formato ISO-8061
   * @return createdAt
  **/
  @ApiModelProperty(value = "momento in cui � stato creato il documento in formato ISO-8061")


  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public Document version(Integer version) {
    this.version = version;
    return this;
  }

  /**
   * versione del documento. La prima versione � la 1.
   * @return version
  **/
  @ApiModelProperty(value = "versione del documento. La prima versione � la 1.")


  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  public Document content(String content) {
    this.content = content;
    return this;
  }

  /**
   * il contenuto del documento codificato in base64
   * @return content
  **/
  @ApiModelProperty(value = "il contenuto del documento codificato in base64")


  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Document ft(String ft) {
    this.ft = ft;
    return this;
  }

  /**
   * elenco della parole usate per l'indicizzazione fulltext
   * @return ft
  **/
  @ApiModelProperty(value = "elenco della parole usate per l'indicizzazione fulltext")


  public String getFt() {
    return ft;
  }

  public void setFt(String ft) {
    this.ft = ft;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Document document = (Document) o;
    return Objects.equals(this.filename, document.filename) &&
        Objects.equals(this.user, document.user) &&
        Objects.equals(this.readgroups, document.readgroups) &&
        Objects.equals(this.writegroups, document.writegroups) &&
        Objects.equals(this.idFile, document.idFile) &&
        Objects.equals(this.createdAt, document.createdAt) &&
        Objects.equals(this.version, document.version) &&
        Objects.equals(this.content, document.content) &&
        Objects.equals(this.ft, document.ft);
  }

  @Override
  public int hashCode() {
    return Objects.hash(filename, user, readgroups, writegroups, idFile, createdAt, version, content, ft);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Document {\n");
    
    sb.append("    filename: ").append(toIndentedString(filename)).append("\n");
    sb.append("    user: ").append(toIndentedString(user)).append("\n");
    sb.append("    readgroups: ").append(toIndentedString(readgroups)).append("\n");
    sb.append("    writegroups: ").append(toIndentedString(writegroups)).append("\n");
    sb.append("    idFile: ").append(toIndentedString(idFile)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("    ft: ").append(toIndentedString(ft)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

