package it.almaviva.impleme.bolite.integration.client.rest;

import it.almaviva.impleme.bolite.integration.pmpay.model.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name = "PmPay", url = "${integrator.endpoint}")
public interface IPmPayClient {

	@RequestMapping(value = "/pagopa/autenticazione", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	ResponseEntity<LoginResponseWrapper> login(@RequestBody LoginRequest loginRequest);
	
	@RequestMapping(value = "/pagopa/iuv", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	ResponseEntity<IuvResponseWrapper> iuv(@RequestBody IuvRequest iuvRequest );
	
	@RequestMapping(value = "/partite_debitorie/insertAndSend", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	ResponseEntity<PosizioneDebitoriaResponse> insertAndSend(@RequestBody PosizioneDebitoriaRequest posizioneDebitoriaRequest);

	@RequestMapping(value = "/partite_debitorie/delete", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	ResponseEntity<AnnullaPosizioneDebitoriaResponse> delete(@RequestBody AnnullaPosizioneDebitoriaRequest annullaPosizioneDebitoriaRequest);

}
