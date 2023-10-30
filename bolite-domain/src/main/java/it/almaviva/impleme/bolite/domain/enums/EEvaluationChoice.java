package it.almaviva.impleme.bolite.domain.enums;

public enum EEvaluationChoice {

    closeable("CLOSEABLE"),
    integrable("INTEGRABLE"),
    payable("PAYABLE");

    private String choice;

    EEvaluationChoice(String choice){
		this.choice = choice;
	}
	
	public String choice() {
        return choice;
    }
}