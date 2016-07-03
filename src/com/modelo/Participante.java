package com.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotNull;

@Entity
public class Participante{

	@Id
	@GeneratedValue
	private int inscricao;
	
	@JoinColumn(name="id_endereco")
	private int id_endereco_fk;
	
	@NotNull
	private String nome;
	
	@NotNull
	private String telefone_fixo;
	
	@NotNull
	private String celular;
	
	@NotNull
	private String email;
	
	@NotNull
	private String local_de_emprego;
	
	@NotNull
	private boolean revisor = false;
	
	@NotNull
	private String congresso;

	@NotNull
	private String numero_cartao;
	
	@NotNull
	private String vencimento_cartao;
	
	@NotNull
	private String marca_cartao;
	

	public String getNumero_cartao() {
		return numero_cartao;
	}
	public void setNumero_cartao(String numero_cartao) {
		this.numero_cartao = numero_cartao;
	}
	public String getVencimento_cartao() {
		return vencimento_cartao;
	}
	public void setVencimento_cartao(String vencimento_cartao) {
		this.vencimento_cartao = vencimento_cartao;
	}
	public String getMarca_cartao() {
		return marca_cartao;
	}
	public void setMarca_cartao(String marca_cartao) {
		this.marca_cartao = marca_cartao;
	}
	public boolean isRevisor() {
		return revisor;
	}
	public void setRevisor(boolean revisor) {
		this.revisor = revisor;
	}
	public String getCongresso() {
		return congresso;
	}
	public void setCongresso(String congresso) {
		this.congresso = congresso;
	}
	public String getLocal_de_emprego() {
		return local_de_emprego;
	}
	public void setLocal_de_emprego(String local_de_emprego) {
		this.local_de_emprego = local_de_emprego;
	}
	public int getInscricao() {
		return inscricao;
	}
	public void setInscricao(int inscricao) {
		this.inscricao = inscricao;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefone_fixo() {
		return telefone_fixo;
	}
	public void setTelefone_fixo(String telefone_fixo) {
		this.telefone_fixo = telefone_fixo;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public int getId_endereco_fk() {
		return id_endereco_fk;
	}
	public void setId_endereco_fk(int id_endereco_fk) {
		this.id_endereco_fk = id_endereco_fk;
	}
	
	
}
