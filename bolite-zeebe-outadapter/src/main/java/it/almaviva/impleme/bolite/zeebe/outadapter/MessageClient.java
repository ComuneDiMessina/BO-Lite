package it.almaviva.impleme.bolite.zeebe.outadapter;

import io.zeebe.spring.client.EnableZeebeClient;
import io.zeebe.spring.client.ZeebeClientLifecycle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
@EnableZeebeClient
public class MessageClient {

	private final ZeebeClientLifecycle clientLifeCycle;

	private final CharSequence TTL = "PT1S";

	public MessageClient(ZeebeClientLifecycle clientLifeCycle) {
		this.clientLifeCycle = clientLifeCycle;
	}

	public void sendOrderCancel(UUID idCaseFile) {
		log.debug("Sending message order canceled idCaseFile: " + idCaseFile);
		clientLifeCycle.newPublishMessageCommand().messageName("CancellationReceived").correlationKey(idCaseFile.toString())
				.timeToLive(Duration.parse(TTL)).send().join();


	}

	public void sendPaymentRcv(UUID idCaseFile, String idDebt) {
		log.info("Sending message payment recived idCaseFile: " + idCaseFile);
		Map<String, Object> var = new HashMap<>();
		var.put("idDebt",idDebt);
		clientLifeCycle.newPublishMessageCommand().messageName("PaymentReceived").correlationKey(idCaseFile.toString()).variables(var)
				.timeToLive(Duration.parse(TTL)).send().join();

	}

	public void sendIntegrationRcv(UUID idCaseFile, String path) {
		log.debug("Sending message integration recived idCaseFile: " + idCaseFile);

		Map<String, Object> var = new HashMap<>();
		var.put("idCaseFile",idCaseFile.toString());
		var.put("dirPath",path);


		clientLifeCycle.newPublishMessageCommand().messageName("IntegrationReceived").correlationKey(idCaseFile.toString()).variables(var)
				.timeToLive(Duration.parse(TTL)).send().join();

	}
}
