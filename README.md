# Rilascio di una nuova release
```bash
mvn --batch-mode release:prepare release:perform -Darguments="-Dmaven.deploy.skip=true"
git push --all && git push --tags
```

# Rilascio di una nuova immagine Docker
## Build Dev
```bash
docker image build -t registry-implemedev.almaviva.it/impleme/bolite-boot:<VERSION> .
```


## Prerequisiti
* Setup iniziale di Postgres
* Setup Profile Manager
* Setup Notificatore
* Setup Zeebe
* WSO2 ESB
* Documentale

### Configurazione notificatore
Le notifiche via email utilizzano template html caricati sul notificatore
* [template html](docs/templates)


### Configurazione database
I servizi si appoggiano su un DB postgres , precedentemente configurato con il seguente script sql:
* [Init DB](docs/database/bolite.sql)

### Configurazione zeebe
I flussi di lavorazione Pratica del BOLite utilizzano 3 wf zeebe:

* [Pagamento spontaneo](docs/zeebe/auto_casefile_flow.bpmn)
* [Prenotazione sale](docs/zeebe/booking_room_casefile_flow.bpmn)
* [Pratica generica con lavorazione Operatore](docs/zeebe/generic_casefile_flow.bpmn)

