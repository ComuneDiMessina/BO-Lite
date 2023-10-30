package it.almaviva.impleme.bolite.integration.client.rest;

import it.almaviva.impleme.bolite.integration.notificatore.model.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name = "Notificatore", url = "${integrator.endpoint}")
public interface INotificatoreClient {
	
	@RequestMapping(value = "/notificatore/utente", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	ResponseEntity<UtenteNotificatoreResponse> utente(@RequestBody UtenteNotificatoreRequest utenteNotificatoreRequest );
	
	@RequestMapping(value = "/notificatore/invioNotifica", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	ResponseEntity<InvioNotificaResponse> invioNotifica(@RequestBody InvioNotificaRequest invioNotificaRequest );

	@RequestMapping(value = "/notificatore/attachment", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	ResponseEntity<InsertAttachmentResponse> insertAttachment(@RequestBody InsertAttachmentRequest insertAttachmentRequest );
}
