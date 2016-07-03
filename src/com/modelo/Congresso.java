package com.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;


@Entity
public class Congresso {

	@Id
	@GeneratedValue
	private int id;
	
	@NotNull
	private String nome;

	@NotNull
	private String dataInscricao;
	
	@NotNull
	private String dataSubmissao;
	
	public String getDataInscricao() {
		return dataInscricao;
	}

	public void setDataInscricao(String dataInscricao) {
		this.dataInscricao = dataInscricao;
	}

	public String getDataSubmissao() {
		return dataSubmissao;
	}

	public void setDataSubmissao(String dataSubmissao) {
		this.dataSubmissao = dataSubmissao;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
