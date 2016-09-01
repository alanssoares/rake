package com.bean;

import java.net.MalformedURLException;
//import java.net.URL;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.ManagedBean;



import javax.faces.context.FacesContext;




//import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

import com.dao.DAO;
import com.modelo.Artigo;
import com.modelo.Avaliacao;
import com.modelo.Participante;
import com.modelo.Submissao;

@ManagedBean
public class AvaliacaoBean {

	private Avaliacao avaliacao = new Avaliacao();

	private String comentario;
	
	public void addAvaliacao()
	{
		System.out.println("Gravando Avaliacao");
		new DAO<Avaliacao>(Avaliacao.class).adiciona(this.avaliacao);
	}
	
	public String cancelar()
	{
		return "index.xhtml";
	}
	
	public String confirma() throws MalformedURLException, EmailException
	{
		String nome = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("revisor");
		System.out.println("Nome "+nome);
		if((!avaliacao.getNota().isEmpty()) && (!comentario.isEmpty()))
		{
			addAvaliacao();
			enviaComentario();
			
			return "index.xhtml";
		}

		return "avaliacao.xhtml";
		
	}
	
	public List<String> buscaAutores()
	{
		List<Submissao> submissoes = new DAO<Submissao>(Submissao.class).listaTodos();
		
		List<String> emails = new ArrayList<String>();
		
		for(Submissao s : submissoes)
			{
				if(avaliacao.getId_avaliacao().getId_artigo_fk() == s.getId_submissao().getId_artigo_fk())
				{
					Participante p = new DAO<Participante>(Participante.class).buscaPorId(s.getId_submissao().getInscricao_fk());
					
					emails.add(p.getEmail());
				}

			}
		
		return emails;
	}
	
	public void enviaComentario() throws MalformedURLException, EmailException
	{ 
		
		Participante p = new DAO<Participante>(Participante.class).buscaPorId(avaliacao.getId_avaliacao().getInscricao_fk());
		
		// Cria a mensagem de e-mail 
		MultiPartEmail email = new MultiPartEmail(); 
		email.setHostName("mail.google.com"); 
		email.addTo(p.getEmail(), p.getNome()); 
		email.setFrom("teste@gmail.com", "");
		email.setAuthentication("teste@gmail.com", "");
		email.setSubject(p.getCongresso()+" - Avaliacao de Artigo"); 
		email.setMsg(comentario);   
   
		email.send();// envia o e-mail

	}
	
	public Avaliacao getAvaliacao()
	{
		return avaliacao;
	}
	
	public void setAvaliacao(Avaliacao avaliacao) {
		this.avaliacao = avaliacao;
	}
	
	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	
	public List<Avaliacao> getListaAvaliacao()
	{
		return new DAO<Avaliacao>(Avaliacao.class).listaTodos();

	}
	
}
