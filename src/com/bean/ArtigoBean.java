package com.bean;

//import javax.faces.application.FacesMessage;
//import javax.faces.context.FacesContext;
//import org.primefaces.event.FileUploadEvent;  
//import org.primefaces.model.UploadedFile; 
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.dao.DAO;
import com.modelo.Artigo;
import com.modelo.Congresso;
import com.modelo.Participante;
import com.modelo.Submissao;

@ManagedBean
@ViewScoped
public class ArtigoBean implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Artigo artigo = new Artigo();
			
	private int inscricao1;
	
	private int inscricao2;
	
	private int inscricao3;
	
	private String congresso;
	
	private UploadedFile arquivo;
	
	public UploadedFile getArquivo() {
		return arquivo;
	}
	public void setArquivo(UploadedFile arquivo) {
		this.arquivo = arquivo;
	}
	public String getCongresso() {
		return congresso;
	}
	public void setCongresso(String congresso) {
		this.congresso = congresso;
	}

	public int getInscricao1() {
		return inscricao1;
	}
	public void setInscricao1(int inscricao1) {
		this.inscricao1 = inscricao1;
	}
	public int getInscricao2() {
		return inscricao2;
	}
	public void setInscricao2(int inscricao2) {
		this.inscricao2 = inscricao2;
	}
	public int getInscricao3() {
		return inscricao3;
	}
	public void setInscricao3(int inscricao3) {
		this.inscricao3 = inscricao3;
	}
	public void setArtigo(Artigo artigo) {
		this.artigo = artigo;
	}

	public Artigo getArtigo()
	{
		return artigo;
		
	}
	
	public List<Integer> validaAutores()
	{
		List<Integer> inscricoes = new ArrayList<Integer>();
		
		Participante p1 = new DAO<Participante>(Participante.class).buscaPorId(inscricao1);
		Participante p2 = new DAO<Participante>(Participante.class).buscaPorId(inscricao2);
		Participante p3 = new DAO<Participante>(Participante.class).buscaPorId(inscricao3);

		if(p1 != null) inscricoes.add(inscricao1);
		if(p2 != null) inscricoes.add(inscricao2);
		if(p3 != null) inscricoes.add(inscricao3);
		
		return inscricoes;
		
	}
	
	public String confirmar()
	{
		List<Integer> inscricoes = validaAutores();
		
		if(!inscricoes.isEmpty())
		{
			
			System.out.println("Autores validados!");

			salvaArquivo();
			
			System.out.println("Salvando no banco!");

			buscaCongresso();
			
			new DAO<Artigo>(Artigo.class).adiciona(this.artigo);
			
			int id = new DAO<Artigo>(Artigo.class).contaTodos("Artigo");

			Submissao submissao = new Submissao();
				
			for(Integer object : inscricoes)
			{
				submissao.getId_submissao().setInscricao_fk(object);
				submissao.getId_submissao().setId_artigo_fk(id);
				
				new DAO<Submissao>(Submissao.class).adiciona(submissao);
			}

			
			return "index.xhtml";
				
		}
		
		
		return "submissao.xhtml";
	}
	
	public String cancelar()
	{
		return "menu.xhtml";
	}
	
	
	public void doUpload(FileUploadEvent fileUploadEvent)
	{ 
        this.arquivo = fileUploadEvent.getFile();  
        
        FacesContext ctx = FacesContext.getCurrentInstance();  
        FacesMessage msg = new FacesMessage();  
  
        msg.setSummary("Arquivo anexado com sucesso.");  
        msg.setSeverity(FacesMessage.SEVERITY_INFO);  
  
        ctx.addMessage("mensagens", msg);  
        
    }
	
	public void salvaArquivo()
	{
		FacesContext ctx = FacesContext.getCurrentInstance(); 
        System.out.println("1");

        FacesMessage msg = new FacesMessage();  
        System.out.println("2");

        String nomeArquivo = arquivo.getFileName(); //Nome do arquivo enviado 
        
        System.out.println("Salvando arquivo anexado - "+ nomeArquivo);
        
        String url="//Users//alan_curtindoafesta//Desktop//"+nomeArquivo;

        artigo.setUrl(url);//Salvando a url do arquivo
        
        byte[] conteudo = arquivo.getContents(); //Conteudo a ser gravado no arquivo  
  
        File file = new File("//Users//alan_curtindoafesta//Desktop//" + nomeArquivo); //Cria uma referencia para arquivo no caminho passado  
  
        try {  
  
            //Escreve o arquivo e salva  
            FileOutputStream fos = new FileOutputStream(file);  
            fos.write(conteudo);  
            fos.flush();  
            fos.close();  
  
            msg.setSummary("Arquivo salvo com sucesso!");  
            msg.setSeverity(FacesMessage.SEVERITY_INFO);  
  
        } catch (IOException ex) {  
  
            msg.setSummary(ex.getMessage());  
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);  
  
        } finally {  
            ctx.addMessage("mensagens", msg);    
        }  
	}
 
	public List<Artigo> getListaArtigos()
	{
		return new DAO<Artigo>(Artigo.class).listaTodos();
		
	}
	
	
	public void buscaCongresso()
	{
		List<Congresso> congressos = new DAO<Congresso>(Congresso.class).listaTodos();
		
		for(Congresso c : congressos)
		{
			if(c.getNome().equals(congresso))
			{
				artigo.setId_congresso(c.getId());
			}
		}
	}

}
