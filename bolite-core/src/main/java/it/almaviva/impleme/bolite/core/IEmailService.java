package it.almaviva.impleme.bolite.core;

import java.util.UUID;

public interface IEmailService {

    void sendEmail(UUID codice, String idDebt, String type, String emailTemplateType, String iub);
    
    void sendUserNotificatore(String cf, String email);

    void sendEmailWithSMTP(UUID codice, String idDebt, String template);

    void sendPraticaGenericaEmail(UUID fromString,  String notificationType, String ente);
}