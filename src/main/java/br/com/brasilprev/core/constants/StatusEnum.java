package br.com.brasilprev.core.constants;

public enum StatusEnum {
	OPENED("Opened"), CANCELED("Canceled"), FINISHED("Finished");

	private String descricao;

	StatusEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
