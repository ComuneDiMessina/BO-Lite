package it.almaviva.impleme.bolite.integration.client.rest.documentale;

import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name="documentaleClient", url="${documentale.endpoint}")
public interface DocumentsApiClient extends DocumentsApi {
}