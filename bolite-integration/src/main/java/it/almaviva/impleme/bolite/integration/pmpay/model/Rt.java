package it.almaviva.impleme.bolite.integration.pmpay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * Rt
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-06-26T13:45:33.348+02:00")

public class Rt   {
  @JsonProperty("@nil")
  private String nil = null;

  public Rt nil(String nil) {
    this.nil = nil;
    return this;
  }

  /**
   * Get nil
   * @return nil
  **/
  @ApiModelProperty(value = "")


  public String getNil() {
    return nil;
  }

  public void setNil(String nil) {
    this.nil = nil;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Rt rt = (Rt) o;
    return Objects.equals(this.nil, rt.nil);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nil);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Rt {\n");
    
    sb.append("    nil: ").append(toIndentedString(nil)).append("\n");
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

