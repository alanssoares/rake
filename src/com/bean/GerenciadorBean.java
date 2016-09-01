package com.bean;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import javax.annotation.ManagedBean;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

import com.dao.DAO;
import com.modelo.Artigo;
import com.modelo.Avaliacao;
import com.modelo.Congresso;
import com.modelo.Participante;
import com.utils.MapValueComparator;

@ManagedBean
public class GerenciadorBean {

    public static final long TEMPO_RESULTADO = (1000 * 86400); // 24 HORAS
    public static final long TEMPO_AVALIACAO = (1000 * 432000); // 5 DIAS
    public static final int MAX_ACEITOS = 20;
    
	public void geraCertificado()
	{
		
	}
	
	public void geraCracha()
	{
		
	}
	
	public void enviaEmailRevisor(int id, Artigo a) throws EmailException, MalformedURLException
	{
		Participante p = new DAO<Participante>(Participante.class).buscaPorId(id);
		String nome = p.getNome().replace(" ", "%20");
		String titulo = a.getTitulo().replace(" ", "%20");

		String link= "http://localhost:8080/RAKe/avaliacao.xhtml?revisor="+nome+"&inscricao="+p.getInscricao()
				+"&titulo="+titulo+"&artigo="+a.getId_artigo();
		
		// Cria a mensagem de e-mail 
		EmailAttachment attachment = new EmailAttachment(); 
		attachment.setPath(a.getUrl());
		attachment.setDisposition(EmailAttachment.ATTACHMENT); 
		attachment.setDescription("Artigo"); 
		attachment.setName(a.getTitulo());   
		
		MultiPartEmail email = new MultiPartEmail(); 
		
		email.setHostName("smtp.googlemail.com"); 
		email.setSmtpPort(465);
		email.setSSLOnConnect(true);
		email.addTo(p.getEmail(), p.getNome()); 
		email.setFrom("teste@gmail.com", "");
		email.setAuthentication("teste@gmail.com", "");
		email.setSubject("eCongress - Avaliacao de Artigo"); 
		email.setMsg("Ola, "+p.getNome()+ ".\nSegue em anexo o artigo para avaliacao.\n"
				+ "A avaliacao deve ser feita atravŽs do link abaixo:\n"
		+ link+"\nOs campos nota e coment‡rio s‹o obrigat—rios.\nNota entre 0-10.\n"
				+ "Coment‡rio de no m‡ximo 250 caracteres.\nAtenciosamente, eCongress");   
		
		// adiciona o anexo
		email.attach(attachment);
   
		// envia o e-mail
		email.send();
		
		
		System.out.println("Email enviado com sucesso!");
	}
	
	public void enviaArtigo() throws EmailException, MalformedURLException
	{
		List<Artigo> artigos = new ArtigoBean().getListaArtigos();
		
		for(Artigo a : artigos)
		{
			List<Integer> revisores = randomRevisores();

			for(Integer id : revisores)
			{
				enviaEmailRevisor(id,a);
			}
		}
	}
	
	public List<Integer> randomRevisores()
	{
		ParticipanteBean bean = new ParticipanteBean();
		List<Participante> revisores =  new ArrayList<Participante>();
		int count = 0;
		List<Integer> r =  new ArrayList<Integer>();
		
		boolean randomOK = false;
		
		for(Participante p : bean.getParticipantes())
			if(p.isRevisor()) revisores.add(p);
		
		while(!randomOK)
		{
			r.clear();
			
			for(int i=0;i<5;i++)
				r.add(1+(int)(revisores.size()*Math.random()));
			
		    for(int i=0;i<5;i++)
			for(int j=0;j<5;j++)
				if(!r.get(i).equals(r.get(j))) count++;
		    
		    System.out.println(count);
		    if(count == 20) randomOK = true;
		    
		    count = 0;
		}
	    
		return r;
		
	}
	
	public void verificaAvaliacoes() throws MalformedURLException, EmailException
	{
		List<Avaliacao> avaliacoes = new AvaliacaoBean().getListaAvaliacao();

		for(Avaliacao a : avaliacoes)
		{
			if(a.getNota().isEmpty())
			{
				 List<Integer> revisores = randomRevisores();
				 
				 for(Integer id : revisores)
					 if(!id.equals(a.getId_avaliacao().getInscricao_fk()))
					 {
						 Artigo artigo = new DAO<Artigo>(Artigo.class).buscaPorId(a.getId_avaliacao().getId_artigo_fk());
						 
						 a.getId_avaliacao().setInscricao_fk(id);
						 
						 new DAO<Avaliacao>(Avaliacao.class).atualiza(a);//Atualizando avaliacao com novo avaliador
						 
						 enviaEmailRevisor(id, artigo);
						 break;
					 }	 
			}
		}
	}
	
	public void enviaArtigosAceitos() throws EmailException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		List<Congresso> congressos = new DAO<Congresso>(Congresso.class).listaTodos();
		String dataAtual = sdf.format(new Date());//Data atual
		
		for(Congresso c : congressos)
		{
			if(c.getDataSubmissao().equals(dataAtual))
			{
				enviaLista(c);
			}
		}
		
	}
	
	public void enviaLista(Congresso congresso) throws EmailException
	{
		List<Participante> participantes = new DAO<Participante>(Participante.class).listaTodos();
		String lista = getArtigosAceitos(congresso);
		
		for(Participante p : participantes)
		{
			if(p.getCongresso().equals(congresso.getNome()))
			{
				
				MultiPartEmail email = new MultiPartEmail(); 
				
				email.setHostName("smtp.googlemail.com"); 
				email.setSmtpPort(465);
				email.setSSLOnConnect(true);
				email.addTo(p.getEmail(), p.getNome()); 
				email.setFrom("teste@gmail.com", "");
				email.setAuthentication("teste@gmail.com", "");
				email.setSubject("eCongress - Lista de Artigos Aprovados"); 
				email.setMsg("LISTA DE ARTIGOS APROVADOS NO CONGRESSO - "+ congresso.getNome()+"\n"+lista);   
		   
				// envia o e-mail
				email.send();
			}
		}
	}
	
	
	public String getArtigosAceitos(Congresso c)
	{
		List<Avaliacao> avaliacoes = new AvaliacaoBean().getListaAvaliacao();
		List<Artigo> artigos = new ArtigoBean().getListaArtigos();
		Map<Integer,Float> artigosAceitos = new HashMap<Integer, Float>();
		MapValueComparator comparator = new MapValueComparator(artigosAceitos);
		Map<Integer,Float> artigosOrdenados = new TreeMap<Integer, Float>(comparator);
		List<Integer> id = new ArrayList<Integer>();
		String cmd="";
		
		for(Artigo a : artigos)
		{
			int cont = 0;
			float nota = 0;
			float media = 0;
			
			if(a.getId_congresso() == c.getId())
			{
				for(Avaliacao ava : avaliacoes)
					if(ava.getId_avaliacao().getId_artigo_fk() == a.getId_artigo())
					{
						nota = nota + Float.parseFloat(ava.getNota());
						cont++;
					}
							
				if(cont >= 3)
				{
					media = nota/cont;
					artigosAceitos.put(a.getId_artigo(),media);
				}
			}

		}

		artigosOrdenados.putAll(artigosAceitos);
		
		Iterator<Integer> it = artigosOrdenados.keySet().iterator();
		
		while(it.hasNext())	id.add(it.next());
		
		for(int i = id.size()-1; i >= id.size()-MAX_ACEITOS; i--) 
		{
			int chave = id.get(i);
			Artigo a = new DAO<Artigo>(Artigo.class).buscaPorId(chave);
			cmd+="Artigo - "+ a.getResumo()+" | Nota : "+ artigosOrdenados.get(chave)+"\n";
		}
		
		System.out.println(cmd);
		
		return cmd;
	}
	
	public String getMenu()
	{
		return "index.xhtml";
	}
	
	
	public static void main(String[] args) {
        Timer timer = null;
        Timer timerResultado = null;

        final GerenciadorBean g = new GerenciadorBean();

        System.out.println("Iniciou...");
        if (timer == null) {  
            timer = new Timer();  
            timerResultado = new Timer();
            
            TimerTask tarefa = new TimerTask() {
            	public void teste() throws MalformedURLException, EmailException {
                	        
            		g.verificaAvaliacoes();

            		System.out.println("Verificando...");
                }
                public void run() {
                    try {
                        this.teste();
                        //timer.cancel();
                    } catch (Exception e) {
                        e.printStackTrace();  
                    }  
                }  
            };  
            
            TimerTask tarefaResultado = new TimerTask() {
            	public void teste() throws MalformedURLException, EmailException {
                	
            		g.enviaArtigosAceitos();

            		System.out.println("Enviando Artigos Aceitos...");
                }
                public void run() {
                    try {
                        this.teste();
                        //timer.cancel();
                    } catch (Exception e) {
                        e.printStackTrace();  
                    }  
                }  
            };  
            
            timer.scheduleAtFixedRate(tarefa, TEMPO_AVALIACAO, TEMPO_AVALIACAO);
            timerResultado.scheduleAtFixedRate(tarefaResultado, TEMPO_RESULTADO, TEMPO_RESULTADO);
        }  
    }  
}
