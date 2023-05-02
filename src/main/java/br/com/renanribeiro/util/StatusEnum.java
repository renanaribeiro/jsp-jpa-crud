package br.com.renanribeiro.util;

public enum StatusEnum {

	EmAnalise ("Em Análise"),
	AnaliseRealizada ("Análise Realizada"),
	AnaliseAprovada ("Análise Aprovada"),
	Iniciado ("Iniciado"),
	Planejado ("Planejado"),
	EmAndamento ("Em Andamento"),
	Encerrado ("Encerrado"),
	Cancelado ("Cancelado");
		
	private String status;
	
	private StatusEnum(String _status) {
		this.status = _status;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public static StatusEnum getState(String status) {
		StatusEnum _status = null;
		for(StatusEnum tempState : StatusEnum.values()) {
			if(tempState.getStatus().equalsIgnoreCase(status)) {
				_status = tempState;
				break;
			}
		}
		return _status;
	}
}
