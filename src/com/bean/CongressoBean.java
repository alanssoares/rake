package com.bean;

import java.util.List;

import javax.annotation.ManagedBean;

import com.dao.DAO;
import com.modelo.Congresso;

@ManagedBean
public class CongressoBean{
	
	private Congresso congresso = new Congresso();

	public Congresso getCongresso() {
		
		return congresso;
	}

	public void setCongresso(Congresso congresso) {
		this.congresso = congresso;
	}
	
	public List<Congresso> getCongressos() {
		return  new DAO<Congresso>(Congresso.class).listaTodos();
	}

}
