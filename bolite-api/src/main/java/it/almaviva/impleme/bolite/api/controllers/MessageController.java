package it.almaviva.impleme.bolite.api.controllers;

import it.almaviva.impleme.bolite.core.IJobMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.util.UUID;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
public class MessageController implements IMessageController{

	private  final IJobMessageService jobMessageService;

	public MessageController(IJobMessageService jobMessageService) {
		this.jobMessageService = jobMessageService;
	}


	@Override
	@RolesAllowed({"CITTADINO"})
	public void cancelOrder(UUID idCaseFile){
		jobMessageService.sendOrderCancel(idCaseFile);
	}

	@Override
	@PermitAll
	@Deprecated
	public void paymentRcv(UUID idCaseFile){
		jobMessageService.sendPaymentRcv(idCaseFile, "");
	}



}
