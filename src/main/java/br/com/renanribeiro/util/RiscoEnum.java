package br.com.renanribeiro.util;

public enum RiscoEnum {

	Baixo ("baixo"),
	Medio ("medio"),
	Alto ("alto");
		
	private String risco;
	
	private RiscoEnum(String _risco) {
		this.risco = _risco;
	}
	
	public String getRisco() {
		return this.risco;
	}
	
	public static RiscoEnum getRisco(String risco) {
		RiscoEnum _risco = null;
		for(RiscoEnum tempState : RiscoEnum.values()) {
			if(tempState.getRisco().equalsIgnoreCase(risco)) {
				_risco = tempState;
				break;
			}
		}
		return _risco;
	}
}
