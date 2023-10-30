package it.almaviva.impleme.bolite.integration.enums;

public enum ECaseFileTypes{
    BOOKING_ROOM(1L),
    GENERIC(2L),
    AUTO(3L);
    
    
    private final Long value;

	ECaseFileTypes(final Long newValue) {
        value = newValue;
    }
	
	
	public static Long getValue(String inputType) {
		for (ECaseFileTypes type : ECaseFileTypes.values()) {
			if (type.toString().equals(inputType)) {
				return type.value;
			}
		}
		return 0L;
	}

}