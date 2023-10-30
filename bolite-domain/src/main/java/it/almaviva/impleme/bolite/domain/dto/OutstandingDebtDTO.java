package it.almaviva.impleme.bolite.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "OutstandingDebt")

public class OutstandingDebtDTO {
	private String ente;
	private String dataRichiesta;
	private String idRichiesta;
	private String nome;
	private String cognome;
	private String codiceFiscale;
	private String indirizzo;
	private String citta;
	private String cap;
	private String provincia;
	private String mail;	
    private String idDebito;    
    private String causalePagamento;    
    private String servizioPagamento;      
    private BigDecimal importoTotale;    
    private String dataScadenza; 
    private String provinciaResidenza;
	private String iuv;
	private String stato;
	private String idTributo;
}
