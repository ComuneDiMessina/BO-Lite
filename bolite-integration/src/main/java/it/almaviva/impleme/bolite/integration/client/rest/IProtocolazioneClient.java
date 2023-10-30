package it.almaviva.impleme.bolite.integration.client.rest;

import it.almaviva.impleme.bolite.integration.protocollazione.model.AllegaDocumentoRequest;
import it.almaviva.impleme.bolite.integration.protocollazione.model.RichiestaProtocollazioneRequest;
import it.almaviva.impleme.bolite.integration.protocollazione.model.RispostaAllegaDocumentoWrapper;
import it.almaviva.impleme.bolite.integration.protocollazione.model.RispostaProtocollazioneWrapper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "Protocollazione", url = "${integrator.endpoint}")
public interface IProtocolazioneClient {

	@RequestMapping(value = "/protocollazione/inserisciArrivo", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	ResponseEntity<RispostaProtocollazioneWrapper> inserisciArrivo(@RequestBody RichiestaProtocollazioneRequest richiestaProtocollazioneRequest);

	@RequestMapping(value = "/protocollazione/allegaDocumento", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	ResponseEntity<RispostaAllegaDocumentoWrapper> allegaDocumento(@RequestBody AllegaDocumentoRequest allegaDocumentoRequest);

}
