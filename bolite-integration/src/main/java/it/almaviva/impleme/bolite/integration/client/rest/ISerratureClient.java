package it.almaviva.impleme.bolite.integration.client.rest;

import it.almaviva.impleme.bolite.integration.serrature.model.RequestOpenDoor;
import it.almaviva.impleme.bolite.integration.serrature.model.ResponseOpenDoor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "Serrature", url = "${porta.url}")
public interface ISerratureClient {
	
	@RequestMapping(value = "/openDoor", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	ResponseEntity<ResponseOpenDoor> openDoor(@RequestHeader(value = "Authorization", required = true) String authorizationHeader, @RequestBody RequestOpenDoor requestOpenDoor);

}
