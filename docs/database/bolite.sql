-- DROP SCHEMA bolite;

CREATE SCHEMA bolite AUTHORIZATION bolite;

-- DROP TYPE bolite."etipo_enum";

CREATE TYPE bolite."etipo_enum" AS ENUM (
	'ENTE',
	'PRIVATO');

DROP SEQUENCE bolite.booking_id_seq;
CREATE SEQUENCE bolite.booking_id_seq INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START WITH 1  NO CYCLE;
DROP SEQUENCE bolite.booking_service_id_seq;
CREATE SEQUENCE bolite.booking_service_id_seq INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START WITH 1  NO CYCLE;
DROP SEQUENCE bolite.case_file_id_seq;
CREATE SEQUENCE bolite.case_file_id_seq INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START WITH 1  NO CYCLE;
DROP SEQUENCE bolite.case_file_state_id_seq;
CREATE SEQUENCE bolite.case_file_state_id_seq INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START WITH 1  NO CYCLE;
DROP SEQUENCE bolite.case_file_type_id_seq;
CREATE SEQUENCE bolite.case_file_type_id_seq INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START WITH 1  NO CYCLE;
DROP SEQUENCE bolite.case_file_user_id_seq;
CREATE SEQUENCE bolite.case_file_user_id_seq INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START WITH 1  NO CYCLE;
DROP SEQUENCE bolite.room_category_id_seq;
CREATE SEQUENCE bolite.room_category_id_seq INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START WITH 1  NO CYCLE;
DROP SEQUENCE bolite.room_daily_tariff_id_seq;
CREATE SEQUENCE bolite.room_daily_tariff_id_seq INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START WITH 1  NO CYCLE;
DROP SEQUENCE bolite.room_daily_timelost_id_seq;
CREATE SEQUENCE bolite.room_daily_timelost_id_seq INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START WITH 1  NO CYCLE;
DROP SEQUENCE bolite.room_event_id_seq;
CREATE SEQUENCE bolite.room_event_id_seq INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START WITH 1  NO CYCLE;
DROP SEQUENCE bolite.room_id_seq;
CREATE SEQUENCE bolite.room_id_seq INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START WITH 1  NO CYCLE;
DROP SEQUENCE bolite.room_openings_id_seq;
CREATE SEQUENCE bolite.room_openings_id_seq INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START WITH 1  NO CYCLE;
DROP SEQUENCE bolite.room_reservation_id_seq;
CREATE SEQUENCE bolite.room_reservation_id_seq INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START WITH 1  NO CYCLE;
DROP SEQUENCE bolite.room_service_id_seq;
CREATE SEQUENCE bolite.room_service_id_seq INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START WITH 1  NO CYCLE;
DROP SEQUENCE bolite.room_structure_type_id_seq;
CREATE SEQUENCE bolite.room_structure_type_id_seq INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START WITH 1  NO CYCLE;
DROP SEQUENCE bolite.room_tariff_event_type_id_seq;
CREATE SEQUENCE bolite.room_tariff_event_type_id_seq INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START WITH 1  NO CYCLE;
DROP SEQUENCE bolite.room_tariff_id_seq;
CREATE SEQUENCE bolite.room_tariff_id_seq INCREMENT BY 1 MINVALUE 1 MAXVALUE 2147483647 START WITH 1  NO CYCLE;
DROP TABLE bolite.booking;
CREATE TABLE bolite.booking (id SERIAL NOT NULL, room_id INTEGER NOT NULL, amount NUMERIC(19,2) NOT NULL, note CHARACTER VARYING(255), booking_date TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL, protocol_number CHARACTER VARYING(255), protocol_date TIMESTAMP(6) WITHOUT TIME ZONE, booking_start_date DATE NOT NULL, booking_end_date DATE NOT NULL, booking_start_hour TIME(6) WITHOUT TIME ZONE NOT NULL, booking_end_hour TIME(6) WITHOUT TIME ZONE NOT NULL, event_type INTEGER, event_description CHARACTER VARYING(255), event_title CHARACTER VARYING(255) NOT NULL, amount_servizi NUMERIC(19,2), evento CHARACTER VARYING, PRIMARY KEY (id));
DROP TABLE bolite.booking_service;
CREATE TABLE bolite.booking_service (id SERIAL NOT NULL, code CHARACTER VARYING(255) NOT NULL, description CHARACTER VARYING(255) NOT NULL, note CHARACTER VARYING(255), amount NUMERIC(19,2) DEFAULT 0.00, booking_id INTEGER NOT NULL, PRIMARY KEY (id));
DROP TABLE bolite.case_file;
CREATE TABLE bolite.case_file (id SERIAL NOT NULL, codice UUID, data_creazione TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL, stato_id INTEGER NOT NULL, tipo_pratica INTEGER NOT NULL, booking_id INTEGER, ente_id INTEGER NOT NULL, numero_protocollo CHARACTER VARYING(255), data_protocollo TIMESTAMP(6) WITHOUT TIME ZONE, note TEXT, causale TEXT, importo NUMERIC NOT NULL, tributo JSONB, tributo_id CHARACTER VARYING(255), iuv CHARACTER VARYING(32), rata CHARACTER VARYING(2) DEFAULT '0', qrcode TEXT, PRIMARY KEY (id), UNIQUE (codice));
DROP TABLE bolite.case_file_document;
CREATE TABLE bolite.case_file_document (id_documento CHARACTER VARYING(255) NOT NULL, descrizione CHARACTER VARYING(255), nome CHARACTER VARYING(255) NOT NULL, id_pratica INTEGER NOT NULL, PRIMARY KEY (id_documento));
DROP TABLE bolite.case_file_outstanding_debt;
CREATE TABLE bolite.case_file_outstanding_debt (id_richiesta CHARACTER VARYING(255) NOT NULL, importo NUMERIC(19,2) NOT NULL, data_creazione TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL, data_scadenza DATE, iuv CHARACTER VARYING(255), stato CHARACTER VARYING(255) NOT NULL, id_pratica INTEGER NOT NULL, ente_id CHARACTER VARYING, tributo_id CHARACTER VARYING, causale TEXT, PRIMARY KEY (id_richiesta));
DROP TABLE bolite.case_file_state;
CREATE TABLE bolite.case_file_state (id SERIAL NOT NULL, label CHARACTER VARYING(255) NOT NULL, description CHARACTER VARYING(255), PRIMARY KEY (id));
DROP TABLE bolite.case_file_type;
CREATE TABLE bolite.case_file_type (id SERIAL NOT NULL, codice CHARACTER VARYING(255), descrizione CHARACTER VARYING(255), nome CHARACTER VARYING(255), PRIMARY KEY (id));
DROP TABLE bolite.case_file_user;
CREATE TABLE bolite.case_file_user (id SERIAL NOT NULL, ente_ragione_sociale CHARACTER VARYING(255), nome CHARACTER VARYING(255), cognome CHARACTER VARYING(255), cf CHARACTER VARYING(255), piva CHARACTER VARYING(255), telephone_number CHARACTER VARYING(255), birth_date DATE, birth_place CHARACTER VARYING(255), email CHARACTER VARYING(255), residenza_stato CHARACTER VARYING(255), residenza_provincia CHARACTER VARYING(255), residenza_comune CHARACTER VARYING(255), residenza_address CHARACTER VARYING(255), residenza_civico CHARACTER VARYING(255), residenza_cap CHARACTER VARYING(255), flag_presidente BOOLEAN DEFAULT false NOT NULL, flag_legale BOOLEAN DEFAULT false NOT NULL, flag_richiedente BOOLEAN DEFAULT false NOT NULL, flag_organizzatore BOOLEAN DEFAULT false NOT NULL, flag_ente BOOLEAN DEFAULT false NOT NULL, casefile_id INTEGER NOT NULL, PRIMARY KEY (id));
DROP TABLE bolite.room;
CREATE TABLE bolite.room (id SERIAL NOT NULL, codice UUID NOT NULL, ente_id INTEGER NOT NULL, category_id INTEGER NOT NULL, structure_id INTEGER NOT NULL, capacity INTEGER NOT NULL, use_conditions TEXT, flag_catering BOOLEAN, flag_terze_parti BOOLEAN, advance_days INTEGER DEFAULT 15, blocked BOOLEAN, name CHARACTER VARYING(255) NOT NULL, PRIMARY KEY (id), UNIQUE (codice));
DROP TABLE bolite.room_category;
CREATE TABLE bolite.room_category (id SERIAL NOT NULL, category CHARACTER VARYING(255) NOT NULL, description CHARACTER VARYING(255), PRIMARY KEY (id));
DROP TABLE bolite.room_daily_tariff;
CREATE TABLE bolite.room_daily_tariff (id SERIAL NOT NULL, day_of_week INTEGER NOT NULL, room_tariff_id INTEGER NOT NULL, PRIMARY KEY (id));
DROP TABLE bolite.room_daily_timelost;
CREATE TABLE bolite.room_daily_timelost (id SERIAL NOT NULL, start_hour TIME(6) WITHOUT TIME ZONE NOT NULL, end_hour TIME(6) WITHOUT TIME ZONE NOT NULL, timeslot_cost NUMERIC(10,2) NOT NULL, room_daily_tariff_id INTEGER NOT NULL, PRIMARY KEY (id));
DROP TABLE bolite.room_event;
CREATE TABLE bolite.room_event (id SERIAL NOT NULL, label CHARACTER VARYING(255) NOT NULL, description CHARACTER VARYING(255), room_id INTEGER NOT NULL, CONSTRAINT room_tariff_event_type_pkey PRIMARY KEY (id));
DROP TABLE bolite.room_openings;
CREATE TABLE bolite.room_openings (id SERIAL NOT NULL, day_of_week INTEGER NOT NULL, room_id INTEGER NOT NULL, start_hour TIME(6) WITHOUT TIME ZONE, end_hour TIME(6) WITHOUT TIME ZONE, PRIMARY KEY (id), CONSTRAINT roomopenings_ix1 UNIQUE (day_of_week, room_id));
DROP TABLE bolite.room_reservation;
CREATE TABLE bolite.room_reservation (id SERIAL NOT NULL, note CHARACTER VARYING(255), cause CHARACTER VARYING(255) NOT NULL, unavailability_start_date DATE NOT NULL, unavailability_end_date DATE NOT NULL, unavailability_start_hour TIME(6) WITHOUT TIME ZONE NOT NULL, unavailability_end_hour TIME(6) WITHOUT TIME ZONE NOT NULL, room_id INTEGER NOT NULL, PRIMARY KEY (id));
DROP TABLE bolite.room_service;
CREATE TABLE bolite.room_service (id SERIAL NOT NULL, code CHARACTER VARYING(255) NOT NULL, description CHARACTER VARYING(255) NOT NULL, note CHARACTER VARYING(255), amount NUMERIC(19,2) DEFAULT 0.00, room_id INTEGER NOT NULL, PRIMARY KEY (id), CONSTRAINT roomservice_ix1 UNIQUE (code, room_id));
DROP TABLE bolite.room_structure_type;
CREATE TABLE bolite.room_structure_type (id SERIAL NOT NULL, struttura CHARACTER VARYING(255) NOT NULL, description CHARACTER VARYING(255), PRIMARY KEY (id));
DROP TABLE bolite.room_tariff;
CREATE TABLE bolite.room_tariff (id SERIAL NOT NULL, hourly_cost DOUBLE PRECISION, full_day_cost DOUBLE PRECISION, weekly_cost DOUBLE PRECISION, event_type_id INTEGER, note CHARACTER VARYING(255), room_id INTEGER, flag_intera_giornata BOOLEAN NOT NULL, flag_intera_settimana BOOLEAN NOT NULL, evento CHARACTER VARYING, PRIMARY KEY (id));
ALTER TABLE "bolite"."booking" ADD CONSTRAINT booking_room_id_fkey FOREIGN KEY ("room_id") REFERENCES "impleme-bolite"."bolite"."room" ("id");
ALTER TABLE "bolite"."booking" ADD CONSTRAINT booking_event_type_fkey FOREIGN KEY ("event_type") REFERENCES "impleme-bolite"."bolite"."room_event" ("id");
ALTER TABLE "bolite"."booking" ADD CONSTRAINT booking_check CHECK (booking_start_date <= booking_end_date);
ALTER TABLE "bolite"."booking_service" ADD CONSTRAINT booking_service_booking_id_fkey FOREIGN KEY ("booking_id") REFERENCES "impleme-bolite"."bolite"."booking" ("id");
ALTER TABLE "bolite"."case_file" ADD CONSTRAINT case_file_stato_id_fkey FOREIGN KEY ("stato_id") REFERENCES "impleme-bolite"."bolite"."case_file_state" ("id");
ALTER TABLE "bolite"."case_file" ADD CONSTRAINT case_file_tipo_pratica_fkey FOREIGN KEY ("tipo_pratica") REFERENCES "impleme-bolite"."bolite"."case_file_type" ("id");
ALTER TABLE "bolite"."case_file" ADD CONSTRAINT case_file_booking_id_fkey FOREIGN KEY ("booking_id") REFERENCES "impleme-bolite"."bolite"."booking" ("id");
ALTER TABLE "bolite"."case_file" ADD CONSTRAINT case_file_ente_id_fkey FOREIGN KEY ("ente_id") REFERENCES "impleme-bolite"."pagopa"."enti" ("id");
ALTER TABLE "bolite"."case_file_document" ADD CONSTRAINT case_file_document_id_pratica_fkey FOREIGN KEY ("id_pratica") REFERENCES "impleme-bolite"."bolite"."case_file" ("id");
ALTER TABLE "bolite"."case_file_outstanding_debt" ADD CONSTRAINT case_file_outstanding_debt_id_pratica_fkey FOREIGN KEY ("id_pratica") REFERENCES "impleme-bolite"."bolite"."case_file" ("id");
ALTER TABLE "bolite"."case_file_outstanding_debt" ADD CONSTRAINT casefileoutstandingdebt_fk2 FOREIGN KEY ("ente_id", "tributo_id") REFERENCES "impleme-bolite"."pagopa"."tributi" ("ente", "id");
ALTER TABLE "bolite"."case_file_user" ADD CONSTRAINT case_file_user_casefile_id_fkey FOREIGN KEY ("casefile_id") REFERENCES "impleme-bolite"."bolite"."case_file" ("id");
ALTER TABLE "bolite"."room" ADD CONSTRAINT room_ente_id_fkey FOREIGN KEY ("ente_id") REFERENCES "impleme-bolite"."pagopa"."enti" ("id");
ALTER TABLE "bolite"."room" ADD CONSTRAINT room_category_id_fkey FOREIGN KEY ("category_id") REFERENCES "impleme-bolite"."bolite"."room_category" ("id");
ALTER TABLE "bolite"."room" ADD CONSTRAINT room_structure_id_fkey FOREIGN KEY ("structure_id") REFERENCES "impleme-bolite"."bolite"."room_structure_type" ("id");
ALTER TABLE "bolite"."room_daily_tariff" ADD CONSTRAINT room_daily_tariff_room_id_fkey FOREIGN KEY ("room_tariff_id") REFERENCES "impleme-bolite"."bolite"."room_tariff" ("id");
ALTER TABLE "bolite"."room_daily_tariff" ADD CONSTRAINT room_daily_tariff_day_of_week_check CHECK (day_of_week = ANY (ARRAY[1, 2, 3, 4, 5, 6, 7]));
ALTER TABLE "bolite"."room_daily_timelost" ADD CONSTRAINT room_daily_timelost_room_id_fkey FOREIGN KEY ("room_daily_tariff_id") REFERENCES "impleme-bolite"."bolite"."room_daily_tariff" ("id");
ALTER TABLE "bolite"."room_daily_timelost" ADD CONSTRAINT room_row_check CHECK (start_hour < end_hour);
ALTER TABLE "bolite"."room_event" ADD CONSTRAINT roomevent_fk1 FOREIGN KEY ("room_id") REFERENCES "impleme-bolite"."bolite"."room" ("id");
ALTER TABLE "bolite"."room_openings" ADD CONSTRAINT room_openings_room_id_fkey FOREIGN KEY ("room_id") REFERENCES "impleme-bolite"."bolite"."room" ("id");
ALTER TABLE "bolite"."room_openings" ADD CONSTRAINT roomopenings_ck2 CHECK (end_hour > start_hour);
ALTER TABLE "bolite"."room_openings" ADD CONSTRAINT room_openings_day_of_week_check CHECK (day_of_week = ANY (ARRAY[1, 2, 3, 4, 5, 6, 7]));
ALTER TABLE "bolite"."room_reservation" ADD CONSTRAINT room_reservation_room_id_fkey FOREIGN KEY ("room_id") REFERENCES "impleme-bolite"."bolite"."room" ("id");
ALTER TABLE "bolite"."room_reservation" ADD CONSTRAINT roomreservation_ck1 CHECK (unavailability_start_date <= unavailability_end_date);
ALTER TABLE "bolite"."room_reservation" ADD CONSTRAINT roomreservation_ck2 CHECK (unavailability_start_hour < unavailability_end_hour);
ALTER TABLE "bolite"."room_service" ADD CONSTRAINT room_service_room_id_fkey FOREIGN KEY ("room_id") REFERENCES "impleme-bolite"."bolite"."room" ("id");
ALTER TABLE "bolite"."room_tariff" ADD CONSTRAINT room_tariff_event_type_id_fkey FOREIGN KEY ("event_type_id") REFERENCES "impleme-bolite"."bolite"."room_event" ("id");
ALTER TABLE "bolite"."room_tariff" ADD CONSTRAINT room_tariff_room_id_fkey FOREIGN KEY ("room_id") REFERENCES "impleme-bolite"."bolite"."room" ("id");

INSERT INTO bolite.case_file_state (id, label, description) VALUES (1, 'Richiesta Pagamento', '');
INSERT INTO bolite.case_file_state (id, label, description) VALUES (2, 'Richiesta Documentazione', '');
INSERT INTO bolite.case_file_state (id, label, description) VALUES (3, 'Inserita', '');
INSERT INTO bolite.case_file_state (id, label, description) VALUES (4, 'In lavorazione', '');
INSERT INTO bolite.case_file_state (id, label, description) VALUES (5, 'Validata', '');
INSERT INTO bolite.case_file_state (id, label, description) VALUES (7, 'Annullata', '');
INSERT INTO bolite.case_file_state (id, label, description) VALUES (8, 'Revocata', '');
INSERT INTO bolite.case_file_state (id, label, description) VALUES (6, 'Respinta', '');

INSERT INTO bolite.case_file_type (id, codice, descrizione, nome) VALUES (1, '01', 'pratica per la prenotazione delle stanze', 'BOOKING_ROOM');
INSERT INTO bolite.case_file_type (id, codice, descrizione, nome) VALUES (2, '02', 'pratica generica', 'GENERIC');
INSERT INTO bolite.case_file_type (id, codice, descrizione, nome) VALUES (3, '03', 'pratica automatica', 'AUTO');

--- comune messina 1
INSERT INTO pagopa.enti_templates (id, nome, template, ente_id, sub) VALUES (1, 'PS_ATTESA_PAGAMENTO', 'PS_ATTESA_PAGAMENTO_SIF07', 1, 'Avviso di Pagamento');
INSERT INTO pagopa.enti_templates (id, nome, template, ente_id, sub) VALUES (2, 'PS_PROTOCOLLATA', 'PS_PROTOCOLLATA_SIF07', 1, 'Esito invio domanda di Pagamento Spontaneo');
INSERT INTO pagopa.enti_templates (id, nome, template, ente_id, sub) VALUES (3, 'INTEGRAZIONE_DOCUMENTALE', 'INTEGRAZIONE_DOCUMENTALE_SIF07', 1, 'Domanda di Prenotazione spazio prot. n° ${numero_protocollo} del ${data_prenotazione} - Richiesta integrazione documentale');
INSERT INTO pagopa.enti_templates (id, nome, template, ente_id, sub) VALUES (4, 'PS_ANNULLATA', 'PS_ANNULLATA_SIF07', 1, 'Domanda di Pagamento Spontaneo n° ${numero_protocollo} del ${data_prenotazione} - Annullamento pratica');
INSERT INTO pagopa.enti_templates (id, nome, template, ente_id, sub) VALUES (5, 'PS_CHIUSA', 'PS_CHIUSA_SIF07', 1, 'Domanda di Pagamento Spontaneo n° ${numero_protocollo} del ${data_prenotazione} - Chiusura pratica');
INSERT INTO pagopa.enti_templates (id, nome, template, ente_id, sub) VALUES (6, 'PS_RICHIESTA_INTEGRAZIONE', 'PS_RICHIESTA_INTEGRAZIONE_SIF07', 1, 'Domanda di Pagamento Spontaneo n° ${numero_protocollo} del ${data_prenotazione} - Richiesta integrazione documentale');
INSERT INTO pagopa.enti_templates (id, nome, template, ente_id, sub) VALUES (7, 'ESITO_INVIO', 'ESITO_INVIO_SIF07', 1, 'Acquisizione Richiesta di Prenotazione Spazio');
INSERT INTO pagopa.enti_templates (id, nome, template, ente_id, sub) VALUES (8, 'CHIUSA', 'CHIUSA_SIF07', 1, 'Esito Richiesta di Prenotazione Spazio (prot. numero ${numero_protocollo})');
INSERT INTO pagopa.enti_templates (id, nome, template, ente_id, sub) VALUES (9, 'ANNULLATA', 'ANNULLATA_SIF07', 1, 'Domanda di Prenotazione Spazio prot. n° ${numero_protocollo} del ${data_prenotazione} - Annullamento pratica');
INSERT INTO pagopa.enti_templates (id, nome, template, ente_id, sub) VALUES (10, 'RIFIUTATA', 'RIFIUTATA_SIF07', 1, 'Domanda di Prenotazione Spazio prot. n° ${numero_protocollo} del ${data_prenotazione} - Chiusura pratica');
INSERT INTO pagopa.enti_templates (id, nome, template, ente_id, sub) VALUES (11, 'DISCARD', 'DISCARD_SIF07', 1, 'Domanda di Prenotazione Spazio prot. n° ${numero_protocollo} del ${data_prenotazione} - Revoca pratica');
INSERT INTO pagopa.enti_templates (id, nome, template, ente_id, sub) VALUES (12, 'ATTESA_PAGAMENTO', 'ATTESA_PAGAMENTO_SIF07', 1, 'Domanda di Prenotazione Spazio prot. n° ${numero_protocollo} del ${data_prenotazione} - Richiesta Pagamento documentale');

INSERT INTO pagopa.tributi (id, ente, descrizione_tributo, descrizione_rt, descrizione_tributo_estesa, configparam, tipo, data_creazione, sottotipo, validazione, giorni_scadenza, diritti_segreteria, paga_diritti_segreteria, anno, prenota_spazio, spontaneo, data_attivazione) VALUES ('01', 'SIF07', 'Prenota uno Spazio', 'Prenota uno Spazio', 'Prenota uno Spazio', null, null, '2020-12-01 15:19:58', null, false, 5, false, false, '2020', true, false, '2020-12-15 00:00:00');
CREATE SEQUENCE pagopa."01_sif07_seq" INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START WITH 1  NO CYCLE;

