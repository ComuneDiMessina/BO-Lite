package it.almaviva.impleme.bolite.zeebe.inadapter;

import io.zeebe.client.api.response.ActivatedJob;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Slf4j
public abstract class AbstractInadapterClient{

    protected static void logJob(final ActivatedJob job) {
    	log.info(
    		      "complete job\n>>> [type: {}, key: {}, element: {}, workflow instance: {}]\n{deadline; {}]\n[headers: {}]\n[variables: {}]",
    		      job.getType(),
    		      job.getKey(),
    		      job.getElementId(),
    		      job.getWorkflowInstanceKey(),
    		      Instant.ofEpochMilli(job.getDeadline()),
    		      job.getCustomHeaders(),
    		      job.getVariables());
    		  }      
}