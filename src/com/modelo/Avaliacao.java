package com.modelo;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;


@Entity
public class Avaliacao{


	@EmbeddedId
	private ChaveComposta id_avaliacao = new ChaveComposta();
	

	@NotNull
	private String nota;

	public ChaveComposta getId_avaliacao() {
		return id_avaliacao;
	}

	public void setId_avaliacao(ChaveComposta id_avaliacao) {
		this.id_avaliacao = id_avaliacao;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}


	
	
}
