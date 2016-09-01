package com.bean;


import java.util.List;

import javax.annotation.ManagedBean;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import com.dao.DAO;
import com.modelo.Endereco;
import com.modelo.Participante;

@ManagedBean
public class ParticipanteBean {

	private Participante participante = new Participante();

	private Endereco endereco = new Endereco();

	
	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Participante getParticipante() 
	{
		return participante;
	}

	public void setParticipante(Participante participante) 
	{
		this.participante = participante;
	}
	
	public void enviaEmail() throws EmailException
	{
		
		int inscricao  = new DAO<Participante>(Participante.class).contaTodos("Participante");
		Participante p = new DAO<Participante>(Participante.class).buscaPorId(inscricao);
		
		// Cria a mensagem de e-mail 
		Email email = new SimpleEmail(); 
		System.out.println(p.getEmail());
		email.setHostName("smtp.googlemail.com"); 
		email.setSmtpPort(465);
		email.setSSLOnConnect(true);
		email.addTo(p.getEmail(), p.getNome()); 
		email.setFrom("teste@gmail.com", "");
		email.setAuthentication("teste@gmail.com", "");
		email.setSubject(p.getCongresso()+ " - Confirmacao de Inscricao"); 
		email.setMsg(" Ola, "+p.getNome()+ ".\n Sua inscricao foi realizada com sucesso!\n Numero de inscricao: "
		+ inscricao+" \n Congresso: "+p.getCongresso());   
		
		// adiciona o anexo   
		email.send();// envia o e-mail
	}
	
	public String cancelar()
	{
		return "index.xhtml";
	}
	
	public String grava() throws EmailException
	{ 		
		
		if(!((participante.getNome().equals("")) && (participante.getEmail().equals("")) && 
				(participante.getTelefone_fixo().equals("")) && (participante.getCelular().equals("")) && 
				(participante.getLocal_de_emprego().equals("")) && (endereco.getRua().equals("")) && 
				(endereco.getBairro().equals("")) && (endereco.getNumero().equals("")) && 
				(endereco.getCep().equals("")) && (participante.getNumero_cartao().equals("")) && 
				(participante.getVencimento_cartao().equals("")) && 
				(participante.getMarca_cartao().equals("")) && 
				(participante.getCongresso().equals("")))){
		
		System.out.println("Gravando Participante");

		new DAO<Endereco>(Endereco.class).adiciona(endereco);
		
		int idEndereco = new DAO<Endereco>(Endereco.class).contaTodos("Endereco");
		
		participante.setId_endereco_fk(idEndereco);
		
		new DAO<Participante>(Participante.class).adiciona(this.participante);		
		
		//int idInscricao = new DAO<Participante>(Participante.class).contaTodos("Participante");
		
		enviaEmail();  

		return "index.xhtml";

		}
			
		return "index.xhtml";
	}
	
	public List<Participante> getParticipantes() {
		return  new DAO<Participante>(Participante.class).listaTodos();
	}
	
	
}
