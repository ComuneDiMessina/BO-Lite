package it.almaviva.impleme.bolite.integration.pmpay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
@ToString
public class PagamentoPosizioneDebitoria {
    @JsonProperty("ID_DEBITO")
    private String idDebito;    

    @JsonProperty("CAUSALE_PAGAMENTO")
    private String causalePagamento;
    
    @JsonProperty("SERVIZIO_PAGAMENTO")
    private String servizioPagamento;
    
    @JsonProperty("IMPORTO_TOTALE")
    private String importoTotale;
    
    @JsonProperty("DATA_SCADENZA")
    private String dataScadenza;
    
//    @Autowired
//    @JsonProperty("DATI_BOLLO")
//    private DatiBollo datiBollo;
}
