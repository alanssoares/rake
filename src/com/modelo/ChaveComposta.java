package com.modelo;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;

@Embeddable
public class ChaveComposta implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JoinColumn(name = "inscricao",nullable=false)
	private int inscricao_fk;
	
	@JoinColumn(name = "id_artigo",nullable=false)
	private int id_artigo_fk;

	public int getInscricao_fk() {
		return inscricao_fk;
	}

	public void setInscricao_fk(int inscricao_fk) {
		this.inscricao_fk = inscricao_fk;
	}

	public int getId_artigo_fk() {
		return id_artigo_fk;
	}

	public void setId_artigo_fk(int id_artigo_fk) {
		this.id_artigo_fk = id_artigo_fk;
	}
	
	
}
