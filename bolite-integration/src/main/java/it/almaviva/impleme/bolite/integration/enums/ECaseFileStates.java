package it.almaviva.impleme.bolite.integration.enums;

public enum ECaseFileStates{
    CREATA(1),
    AGGIORNATA(2),
    PROTOCOLLATA(3),
    RICHIESTA_INTEGRAZIONE(4),
    ANNULLATA(5),
    ASSEGNATA(6),
    CHIUDIBILE(7),
    CHIUSA(8),
    ATTESA_PAGAMENTO(9);

    private final int value;

    ECaseFileStates(final int newValue) {
        value = newValue;
    }

    public Integer getValue() { return value; }

}