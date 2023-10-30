package it.almaviva.impleme.bolite.integration.pmpay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * RispostaErrorRegistraPagamento
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-06-26T13:45:33.348+02:00")

public class RispostaErrorRegistraPagamento   {
  @JsonProperty("DATA_RICHIESTA")
  private String DATA_RICHIESTA = null;

  @JsonProperty("ID_RICHIESTA")
  private String ID_RICHIESTA = null;

  @JsonProperty("CODICE_AZIENDA")
  private String CODICE_AZIENDA = null;

  @JsonProperty("ESITO")
  private String ESITO = null;

  @JsonProperty("CODICE_ERRORE")
  private Integer CODICE_ERRORE = null;

  @JsonProperty("DESCRIZIONE_ERRORE")
  private String DESCRIZIONE_ERRORE = null;

  public RispostaErrorRegistraPagamento DATA_RICHIESTA(String DATA_RICHIESTA) {
    this.DATA_RICHIESTA = DATA_RICHIESTA;
    return this;
  }

  /**
   * Get DATA_RICHIESTA
   * @return DATA_RICHIESTA
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getDATARICHIESTA() {
    return DATA_RICHIESTA;
  }

  public void setDATARICHIESTA(String DATA_RICHIESTA) {
    this.DATA_RICHIESTA = DATA_RICHIESTA;
  }

  public RispostaErrorRegistraPagamento ID_RICHIESTA(String ID_RICHIESTA) {
    this.ID_RICHIESTA = ID_RICHIESTA;
    return this;
  }

  /**
   * Get ID_RICHIESTA
   * @return ID_RICHIESTA
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getIDRICHIESTA() {
    return ID_RICHIESTA;
  }

  public void setIDRICHIESTA(String ID_RICHIESTA) {
    this.ID_RICHIESTA = ID_RICHIESTA;
  }

  public RispostaErrorRegistraPagamento CODICE_AZIENDA(String CODICE_AZIENDA) {
    this.CODICE_AZIENDA = CODICE_AZIENDA;
    return this;
  }

  /**
   * Get CODICE_AZIENDA
   * @return CODICE_AZIENDA
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getCODICEAZIENDA() {
    return CODICE_AZIENDA;
  }

  public void setCODICEAZIENDA(String CODICE_AZIENDA) {
    this.CODICE_AZIENDA = CODICE_AZIENDA;
  }

  public RispostaErrorRegistraPagamento ESITO(String ESITO) {
    this.ESITO = ESITO;
    return this;
  }

  /**
   * Get ESITO
   * @return ESITO
  **/
  @ApiModelProperty(value = "")


  public String getESITO() {
    return ESITO;
  }

  public void setESITO(String ESITO) {
    this.ESITO = ESITO;
  }

  public RispostaErrorRegistraPagamento CODICE_ERRORE(Integer CODICE_ERRORE) {
    this.CODICE_ERRORE = CODICE_ERRORE;
    return this;
  }

  /**
   * Get CODICE_ERRORE
   * @return CODICE_ERRORE
  **/
  @ApiModelProperty(value = "")


  public Integer getCODICEERRORE() {
    return CODICE_ERRORE;
  }

  public void setCODICEERRORE(Integer CODICE_ERRORE) {
    this.CODICE_ERRORE = CODICE_ERRORE;
  }

  public RispostaErrorRegistraPagamento DESCRIZIONE_ERRORE(String DESCRIZIONE_ERRORE) {
    this.DESCRIZIONE_ERRORE = DESCRIZIONE_ERRORE;
    return this;
  }

  /**
   * Get DESCRIZIONE_ERRORE
   * @return DESCRIZIONE_ERRORE
  **/
  @ApiModelProperty(value = "")


  public String getDESCRIZIONEERRORE() {
    return DESCRIZIONE_ERRORE;
  }

  public void setDESCRIZIONEERRORE(String DESCRIZIONE_ERRORE) {
    this.DESCRIZIONE_ERRORE = DESCRIZIONE_ERRORE;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RispostaErrorRegistraPagamento rispostaErrorRegistraPagamento = (RispostaErrorRegistraPagamento) o;
    return Objects.equals(this.DATA_RICHIESTA, rispostaErrorRegistraPagamento.DATA_RICHIESTA) &&
        Objects.equals(this.ID_RICHIESTA, rispostaErrorRegistraPagamento.ID_RICHIESTA) &&
        Objects.equals(this.CODICE_AZIENDA, rispostaErrorRegistraPagamento.CODICE_AZIENDA) &&
        Objects.equals(this.ESITO, rispostaErrorRegistraPagamento.ESITO) &&
        Objects.equals(this.CODICE_ERRORE, rispostaErrorRegistraPagamento.CODICE_ERRORE) &&
        Objects.equals(this.DESCRIZIONE_ERRORE, rispostaErrorRegistraPagamento.DESCRIZIONE_ERRORE);
  }

  @Override
  public int hashCode() {
    return Objects.hash(DATA_RICHIESTA, ID_RICHIESTA, CODICE_AZIENDA, ESITO, CODICE_ERRORE, DESCRIZIONE_ERRORE);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RispostaErrorRegistraPagamento {\n");
    
    sb.append("    DATA_RICHIESTA: ").append(toIndentedString(DATA_RICHIESTA)).append("\n");
    sb.append("    ID_RICHIESTA: ").append(toIndentedString(ID_RICHIESTA)).append("\n");
    sb.append("    CODICE_AZIENDA: ").append(toIndentedString(CODICE_AZIENDA)).append("\n");
    sb.append("    ESITO: ").append(toIndentedString(ESITO)).append("\n");
    sb.append("    CODICE_ERRORE: ").append(toIndentedString(CODICE_ERRORE)).append("\n");
    sb.append("    DESCRIZIONE_ERRORE: ").append(toIndentedString(DESCRIZIONE_ERRORE)).append("\n");
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

