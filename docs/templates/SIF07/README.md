# Script template

## insert schema pagopa tabella enti_templates
```sql
INSERT INTO enti_templates (id, nome, template, ente_id, sub) VALUES (3, 'PS_ATTESA_PAGAMENTO', 'PS_ATTESA_PAGAMENTO_SIF07', 8, 'Avviso di Pagamento');
INSERT INTO enti_templates (id, nome, template, ente_id, sub) VALUES (5, 'PS_PROTOCOLLATA', 'PS_PROTOCOLLATA_SIF07', 8, 'Esito invio domanda di Pagamento Spontaneo');
INSERT INTO enti_templates (id, nome, template, ente_id, sub) VALUES (2, 'INTEGRAZIONE_DOCUMENTALE', 'INTEGRAZIONE_DOCUMENTALE_SIF07', 8, 'Domanda di Prenotazione spazio prot. n° ${numero_protocollo} del ${data_prenotazione} - Richiesta integrazione documentale');
INSERT INTO enti_templates (id, nome, template, ente_id, sub) VALUES (6, 'PS_ANNULLATA', 'PS_ANNULLATA_SIF07', 8, 'Domanda di Pagamento Spontaneo n° ${numero_protocollo} del ${data_prenotazione} - Annullamento pratica');
INSERT INTO enti_templates (id, nome, template, ente_id, sub) VALUES (7, 'PS_CHIUSA', 'PS_CHIUSA_SIF07', 8, 'Domanda di Pagamento Spontaneo n° ${numero_protocollo} del ${data_prenotazione} - Chiusura pratica');
INSERT INTO enti_templates (id, nome, template, ente_id, sub) VALUES (8, 'PS_RICHIESTA_INTEGRAZIONE', 'PS_RICHIESTA_INTEGRAZIONE_SIF07', 8, 'Domanda di Pagamento Spontaneo n° ${numero_protocollo} del ${data_prenotazione} - Richiesta integrazione documentale');
INSERT INTO enti_templates (id, nome, template, ente_id, sub) VALUES (1, 'ESITO_INVIO', 'ESITO_INVIO_SIF07', 8, 'Acquisizione Richiesta di Prenotazione Spazio');
INSERT INTO enti_templates (id, nome, template, ente_id, sub) VALUES (10, 'CHIUSA', 'CHIUSA_SIF07', 8, 'Esito Richiesta di Prenotazione Spazio (prot. numero ${numero_protocollo})');
INSERT INTO enti_templates (id, nome, template, ente_id, sub) VALUES (13, 'ANNULLATA', 'ANNULLATA_SIF07', 8, 'Domanda di Prenotazione Spazio prot. n° ${numero_protocollo} del ${data_prenotazione} - Annullamento pratica');
INSERT INTO enti_templates (id, nome, template, ente_id, sub) VALUES (11, 'RIFIUTATA', 'RIFIUTATA_SIF07', 8, 'Domanda di Prenotazione Spazio prot. n° ${numero_protocollo} del ${data_prenotazione} - Chiusura pratica');
INSERT INTO enti_templates (id, nome, template, ente_id, sub) VALUES (12, 'DISCARD', 'DISCARD_SIF07', 8, 'Domanda di Prenotazione Spazio prot. n° ${numero_protocollo} del ${data_prenotazione} - Revoca pratica');
INSERT INTO enti_templates (id, nome, template, ente_id, sub) VALUES (9, 'ATTESA_PAGAMENTO', 'ATTESA_PAGAMENTO_SIF07', 8, 'Domanda di Prenotazione Spazio prot. n° ${numero_protocollo} del ${data_prenotazione} - Richiesta Pagamento documentale');
```
