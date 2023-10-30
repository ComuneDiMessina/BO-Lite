package it.almaviva.impleme.bolite.integration.enums;

public enum EOutstandingDebtTypes {
	DIRITTI_SEGRETERIA(1),
	ALTRO(2);

	private final int value;

	EOutstandingDebtTypes(final int newValue) {
		value = newValue;
	}

	public int getValue() {
		return value;
	}
}