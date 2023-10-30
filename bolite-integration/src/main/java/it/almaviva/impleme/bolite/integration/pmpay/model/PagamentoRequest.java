package it.almaviva.impleme.bolite.integration.pmpay.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@Setter
@Getter
@ToString

public class PagamentoRequest {
	@Autowired
    @JsonProperty("headerPagamento")    
    private HeaderPagamento headerPagamento;
	@Autowired
    @JsonProperty("anagraficaPagamento")    
    private AnagraficaPagamento anagraficaPagamento;
	@Autowired
    @JsonProperty("pagamento")
    private List<Pagamento> pagamento;
	
	
}
