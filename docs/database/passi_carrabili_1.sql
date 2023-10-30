ALTER TABLE "impleme-bolite"."bolite"."case_file_type" ADD COLUMN note character varying(255);
ALTER TABLE "impleme-bolite"."bolite"."case_file_type" ADD COLUMN testo_libero text;
ALTER TABLE "impleme-bolite"."bolite"."case_file_user" ADD COLUMN flag_fruitore boolean;
ALTER TABLE "impleme-bolite"."bolite"."case_file_user" ALTER COLUMN flag_fruitore SET NOT NULL;
ALTER TABLE "impleme-bolite"."bolite"."case_file_user" ALTER COLUMN flag_fruitore SET DEFAULT false;
ALTER TABLE "impleme-bolite"."bolite"."case_file_type" ADD COLUMN img text;
ALTER TABLE "impleme-bolite"."bolite"."case_file_type" ADD CONSTRAINT casefiletype_ix1 UNIQUE ("codice");


INSERT INTO pagopa.enti_templates (id, nome, "template", ente_id, sub) VALUES (18, 'PG_ANNULLATA', 'PG_ANNULLATA_SIF07', 8, '');
INSERT INTO pagopa.enti_templates (id, nome, "template", ente_id, sub) VALUES (19, 'PG_PROTOCOLLATA', 'PG_PROTOCOLLATA_SIF07', 8, '');
INSERT INTO pagopa.enti_templates (id, nome, "template", ente_id, sub) VALUES (15, 'PG_CONFERMATA', 'PG_CONFERMATA_SIF07', 8, '');
INSERT INTO pagopa.enti_templates (id, nome, "template", ente_id, sub) VALUES (16, 'PG_RESPINTA', 'PG_RESPINTA_SIF07', 8, '');
INSERT INTO pagopa.enti_templates (id, nome, "template", ente_id, sub) VALUES (17, 'PG_INTEGRAZIONE', 'PG_INTEGRAZIONE_SIF07', 8, '');
INSERT INTO pagopa.tributi (id, ente, descrizione_tributo, descrizione_rt, descrizione_tributo_estesa, configparam, tipo, data_creazione, sottotipo, validazione, giorni_scadenza, diritti_segreteria, paga_diritti_segreteria, anno, prenota_spazio, spontaneo, data_attivazione) VALUES ('01', 'SIF07', 'Spese di Istruttoria', null, null, null, null, '2020-12-10 14:42:21', null, false, 5, false, false, '2020', false, true, '2020-12-15 00:00:00');

ALTER TABLE "impleme-bolite"."bolite"."case_file_type" ADD COLUMN hidden BOOLEAN;
INSERT INTO case_file_type (id, codice, descrizione, nome, note, testo_libero, img, hidden) VALUES (2, 'P03', 'pratica passi carrabili', 'Passi Carrabili', 'Note', 'Testo Libero', null, false);

ALTER TABLE "impleme-bolite"."bolite"."case_file_type" ADD COLUMN importo numeric(19,2)
