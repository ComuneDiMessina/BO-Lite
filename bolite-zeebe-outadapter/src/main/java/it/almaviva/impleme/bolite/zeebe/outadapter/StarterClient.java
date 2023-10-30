package it.almaviva.impleme.bolite.zeebe.outadapter;

import io.zeebe.client.api.response.WorkflowInstanceEvent;
import io.zeebe.spring.client.EnableZeebeClient;
import io.zeebe.spring.client.ZeebeClientLifecycle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
@EnableZeebeClient
public class StarterClient {

    @Value("${zeebe.workflow_roombooking}")
    private String workflow_roombooking;
    @Value("${zeebe.workflow_auto}")
    private String workflow_auto;
    @Value("${zeebe.workflow_generic}")
    private String workflow_generic;
    @Autowired
    ZeebeClientLifecycle clientLifeCycle;

    public void startBookingWf(UUID idCaseFile, String dirPath, BigDecimal amount, String gruppo) {

        Map<String, Object> var = new HashMap<>();
        var.put("idCaseFile", idCaseFile.toString());
        var.put("dirPath", dirPath);
        var.put("CG_GRUPPO", gruppo);
        var.put("idDebt", null);

        final WorkflowInstanceEvent event = clientLifeCycle.newCreateInstanceCommand().bpmnProcessId(workflow_roombooking).latestVersion().variables(var).send().join();

    }

    public void startPraticaAutomatica(UUID idCaseFile, String period, String idDebt) {

        Map<String, Object> var = new HashMap<>();
        var.put("idCaseFile", idCaseFile.toString());
        var.put("remainingTime", period);
        var.put("idDebt", idDebt);

        final WorkflowInstanceEvent event = clientLifeCycle.newCreateInstanceCommand().bpmnProcessId(workflow_auto).latestVersion().variables(var).send().join();
    }

    public void startPraticaGenerica(UUID idCaseFile, String dirPath, String period, String gruppo, String idDebt) {

        Map<String, Object> var = new HashMap<>();
        var.put("idCaseFile", idCaseFile.toString());
        var.put("dirPath", dirPath);
        var.put("CG_GRUPPO", gruppo);
        var.put("remainingTime", period);
        var.put("idDebt", idDebt);

        final WorkflowInstanceEvent event = clientLifeCycle.newCreateInstanceCommand().bpmnProcessId(workflow_generic).latestVersion().variables(var).send().join();

    }

}
