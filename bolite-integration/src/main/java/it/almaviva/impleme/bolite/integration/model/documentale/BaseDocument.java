package it.almaviva.impleme.bolite.integration.model.documentale;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * BaseDocument
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-08-09T11:41:08.520+02:00")

public class BaseDocument   {
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

  public BaseDocument filename(String filename) {
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

  public BaseDocument user(String user) {
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

  public BaseDocument readgroups(List<String> readgroups) {
    this.readgroups = readgroups;
    return this;
  }

  public BaseDocument addReadgroupsItem(String readgroupsItem) {
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

  public BaseDocument writegroups(List<String> writegroups) {
    this.writegroups = writegroups;
    return this;
  }

  public BaseDocument addWritegroupsItem(String writegroupsItem) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BaseDocument baseDocument = (BaseDocument) o;
    return Objects.equals(this.filename, baseDocument.filename) &&
        Objects.equals(this.user, baseDocument.user) &&
        Objects.equals(this.readgroups, baseDocument.readgroups) &&
        Objects.equals(this.writegroups, baseDocument.writegroups);
  }

  @Override
  public int hashCode() {
    return Objects.hash(filename, user, readgroups, writegroups);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BaseDocument {\n");
    
    sb.append("    filename: ").append(toIndentedString(filename)).append("\n");
    sb.append("    user: ").append(toIndentedString(user)).append("\n");
    sb.append("    readgroups: ").append(toIndentedString(readgroups)).append("\n");
    sb.append("    writegroups: ").append(toIndentedString(writegroups)).append("\n");
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

