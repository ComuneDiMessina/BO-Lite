INSERT INTO tab_pm_micro_service (id, description, name) VALUES('6a13586a-3b04-4228-9a88-191619630060', 'ms profile manager', 'PROFILE_MANAGER');
INSERT INTO tab_pm_role (id, "name", wso2name) VALUES('9611ad1c-8819-4192-b6a6-6640b353b7e1', 'PM_ADMIN', 'PM_ADMIN');
INSERT INTO tab_pm_ms_role (ms_id, role_id) VALUES('6a13586a-3b04-4228-9a88-191619630060', '9611ad1c-8819-4192-b6a6-6640b353b7e1');


INSERT INTO tab_pm_micro_service (id, description, name) VALUES('0bffcb5a-6ffc-4dff-b51d-515de309b2e3', 'ms user manager', 'USER_MANAGER');
INSERT INTO tab_pm_role (id, "name", wso2name) VALUES('661660ca-6b3b-47a8-8a47-0bd7c99282c1', 'UM_ADMIN', 'UM_ADMIN');
INSERT INTO tab_pm_ms_role (ms_id, role_id) VALUES('0bffcb5a-6ffc-4dff-b51d-515de309b2e3', '661660ca-6b3b-47a8-8a47-0bd7c99282c1');

INSERT INTO tab_pm_micro_service (id, description, name) VALUES('e99cd85b-eaa3-4133-9c08-b49c369c7665', 'ms zeebe user task', 'ZEEBE_USER_TASK');
INSERT INTO tab_pm_ms_role (ms_id, role_id) VALUES('e99cd85b-eaa3-4133-9c08-b49c369c7665', '50323715-4813-4d42-bfcd-ebca8d4d83fe');
INSERT INTO tab_pm_ms_group (ms_id, group_id) VALUES('e99cd85b-eaa3-4133-9c08-b49c369c7665', '334549e0-1f06-4f92-b583-37e866db7349');



INSERT INTO tab_pm_micro_service (id, description, name) VALUES('daf86cc4-0127-4203-b78b-4734ec2eeee7', 'ms bolite', 'BOLITE');
INSERT INTO tab_pm_role (id, "name", wso2name) VALUES('50323715-4813-4d42-bfcd-ebca8d4d83fe', 'AMMINISTRATORE', 'AMMINISTRATORE');
INSERT INTO tab_pm_role (id, "name", wso2name) VALUES('22369933-519c-4549-9800-f253fadc97d1', 'OPERATORE', 'OPERATORE');
INSERT INTO tab_pm_role (id, "name", wso2name) VALUES('21445b5a-104b-41be-89b5-3583cec68163', 'CITTADINO', 'CITTADINO');
INSERT INTO tab_pm_ms_role (ms_id, role_id) VALUES('daf86cc4-0127-4203-b78b-4734ec2eeee7', '50323715-4813-4d42-bfcd-ebca8d4d83fe');
INSERT INTO tab_pm_ms_role (ms_id, role_id) VALUES('daf86cc4-0127-4203-b78b-4734ec2eeee7', '22369933-519c-4549-9800-f253fadc97d1');
INSERT INTO tab_pm_ms_role (ms_id, role_id) VALUES('daf86cc4-0127-4203-b78b-4734ec2eeee7', '21445b5a-104b-41be-89b5-3583cec68163');


INSERT INTO tab_pm_group (id, "name", wso2name) VALUES ('39bbe716-2b45-4ab5-b8ce-b49bea2ac74f', 'AMMINISTRATORE_SIF07', 'AMMINISTRATORE_SIF07');
INSERT INTO tab_pm_group (id, "name", wso2name) VALUES ('10d363cb-baba-431f-87f4-2d784442055e', 'OPERATORE_SIF07', 'OPERATORE_SIF07');
INSERT INTO tab_pm_ms_group (ms_id, group_id) VALUES ('daf86cc4-0127-4203-b78b-4734ec2eeee7', '39bbe716-2b45-4ab5-b8ce-b49bea2ac74f');
INSERT INTO tab_pm_ms_group (ms_id, group_id) VALUES ('daf86cc4-0127-4203-b78b-4734ec2eeee7', '10d363cb-baba-431f-87f4-2d784442055e');
INSERT INTO tab_pm_ms_group (ms_id, group_id) VALUES ('e99cd85b-eaa3-4133-9c08-b49c369c7665', '39bbe716-2b45-4ab5-b8ce-b49bea2ac74f');
INSERT INTO tab_pm_ms_group (ms_id, group_id) VALUES ('e99cd85b-eaa3-4133-9c08-b49c369c7665', '10d363cb-baba-431f-87f4-2d784442055e');

INSERT INTO tab_pm_domain (id, description, name, wso2name) VALUES ('60fa08ed-f81d-406f-84a4-655f73b4ac1b', 'COMUNE', 'COMUNE', 'COMUNE');
INSERT INTO tab_pm_domain_value (id, description, value, domain_id) VALUES ('65420767-2117-49e6-be81-f98549ee7fa5', 'COMUNE DI MESSINA', 'SIF07', '60fa08ed-f81d-406f-84a4-655f73b4ac1b');
INSERT INTO tab_pm_group_domain_value (group_id, domain_value_id) VALUES ('39bbe716-2b45-4ab5-b8ce-b49bea2ac74f', '65420767-2117-49e6-be81-f98549ee7fa5');
INSERT INTO tab_pm_group_domain_value (group_id, domain_value_id) VALUES ('10d363cb-baba-431f-87f4-2d784442055e', '65420767-2117-49e6-be81-f98549ee7fa5');



INSERT INTO tab_pm_micro_service (id, description, name) VALUES('f40ec9f1-8474-4b10-b033-d4e9d5ee16af', 'anagrafica tributi', 'IMPLEME_TRIBUTI');
INSERT INTO tab_pm_ms_role (ms_id, role_id) VALUES('f40ec9f1-8474-4b10-b033-d4e9d5ee16af', '50323715-4813-4d42-bfcd-ebca8d4d83fe');
INSERT INTO tab_pm_ms_role (ms_id, role_id) VALUES('f40ec9f1-8474-4b10-b033-d4e9d5ee16af', '21445b5a-104b-41be-89b5-3583cec68163');
INSERT INTO tab_pm_ms_group (ms_id, group_id) VALUES ('f40ec9f1-8474-4b10-b033-d4e9d5ee16af', '39bbe716-2b45-4ab5-b8ce-b49bea2ac74f');
INSERT INTO tab_pm_ms_group (ms_id, group_id) VALUES ('f40ec9f1-8474-4b10-b033-d4e9d5ee16af', '10d363cb-baba-431f-87f4-2d784442055e');