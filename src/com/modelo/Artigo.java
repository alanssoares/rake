package com.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotNull;

@Entity
public class Artigo {

	@Id
	@GeneratedValue
	private int id_artigo;
	
	@NotNull
	private String resumo;
	
	@NotNull
	private String titulo;
	
	@JoinColumn(name="id")
	private int id_congresso;

	@NotNull
	private String url;
	
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getId_congresso() {
		return id_congresso;
	}
	
	public void setId_congresso(int id_congresso) {
		this.id_congresso = id_congresso;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getResumo() {
		return resumo;
	}

	public void setResumo(String resumo) {
		this.resumo = resumo;
	}

	public int getId_artigo() {
		return id_artigo;
	}

	public void setId_artigo(int id_artigo) {
		this.id_artigo = id_artigo;
	}
	
}
