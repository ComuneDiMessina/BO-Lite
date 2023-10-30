package it.almaviva.impleme.bolite.integration.pmpay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PosizioneDebitoriaRequest {
	@JsonProperty("ente")
    private String ente;
	
	@JsonProperty("id_tributo")
    private String idTributo;
	
	@JsonProperty("descr_tributo")
    private String descrTributo;
	
	@JsonProperty("anno_tributo")
    private String annoTributo;
	
	@JsonProperty("numero_tributo")
    private String numeroTributo;
	
	@JsonProperty("numero_posizione")
    private String numeroPosizione;
	
	@JsonProperty("anagrafica_debitore")
    private String anagraficaDebitore;
	
	@JsonProperty("cf_piva_debitore")
    private String cfPivaDebitore;
	
	@JsonProperty("indirizzo_debitore")
    private String indirizzoDebitore;
	
	@JsonProperty("localita_debitore")
    private String localitaDebitore;
	
	@JsonProperty("provincia_localita")
    private String provinciaLocalita;
	
	@JsonProperty("data_emissione")
    private String dataEmissione;
	
	@JsonProperty("importo_debito")
    private String importoDebito;
	
	@JsonProperty("data_scadenza")
    private String dataScadenza;
	
	@JsonProperty("causale_debito")
    private String causaleDebito;
	
	@JsonProperty("rata")
    private String rata;
	
	@JsonProperty("cod_contabilita")
    private String codContabilita;
	
	@JsonProperty("data_notifica")
    private String dataNotifica;
	
	@JsonProperty("email_rt")
    private String emailRt;
	
	@JsonProperty("descrizione_rt")
    private String descrizioneRt;
	
	@JsonProperty("iuv_rata_unica")
    private String iuvRataUnica;
	
	@JsonProperty("servizio_pagamento")
    private String servizio_pagamento;

	public PosizioneDebitoriaRequest(String ente, String idTributo, String descrTributo, String annoTributo,
			String numeroTributo, String numeroPosizione, String anagraficaDebitore, String cfPivaDebitore,
			String indirizzoDebitore, String localitaDebitore, String provinciaLocalita, String dataEmissione,
			String importoDebito, String dataScadenza, String causaleDebito, String rata, String codContabilita,
			String dataNotifica, String emailRt, String descrizioneRt, String iuvRataUnica, String servizio_pagamento) {
		super();
		this.ente = ente;
		this.idTributo = idTributo;
		this.descrTributo = descrTributo;
		this.annoTributo = annoTributo;
		this.numeroTributo = numeroTributo;
		this.numeroPosizione = numeroPosizione;
		this.anagraficaDebitore = anagraficaDebitore;
		this.cfPivaDebitore = cfPivaDebitore;
		this.indirizzoDebitore = indirizzoDebitore;
		this.localitaDebitore = localitaDebitore;
		this.provinciaLocalita = provinciaLocalita;
		this.dataEmissione = dataEmissione;
		this.importoDebito = importoDebito;
		this.dataScadenza = dataScadenza;
		this.causaleDebito = causaleDebito;
		this.rata = rata;
		this.codContabilita = codContabilita;
		this.dataNotifica = dataNotifica;
		this.emailRt = emailRt;
		this.descrizioneRt = descrizioneRt;
		this.iuvRataUnica = iuvRataUnica;
		this.servizio_pagamento = servizio_pagamento;
	}
	
	
	
}
