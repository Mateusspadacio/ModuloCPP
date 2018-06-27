package com.c.falhasdeseguranca.unsigned;

import java.util.ArrayList;

import com.metodos.padrao.MetodosAdapter;
import com.metodos.padrao.MetodosPadrao;

public class FalhaUnsignedCPP implements MetodosPadrao, Cloneable{

	private ArrayList<String> resultadoAnalise;
	private MetodosAdapter adapter;
	
	public FalhaUnsignedCPP() {
		resultadoAnalise = new ArrayList<>();
		adapter = new FalhaUnsignedCPPAdapter();
	}
	
	@Override
	public void analisando(String linha, int num) {
		adapter.analisa(linha, num);
		resultadoAnalise = adapter.getResultado();
		
	}

	@Override
	public ArrayList<String> getResultadoAnalise() {
		return resultadoAnalise;
	}
	
	private void setResultadoanalise(ArrayList<String> resultadoAnalise) {
		this.resultadoAnalise = resultadoAnalise;
	}

	@Override
	public void clearSession() {
		resultadoAnalise.clear();
	}
	
	@Override
	public Object clonar() {
		try {
			return clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		FalhaUnsignedCPP falhaUnsignedCPP = (FalhaUnsignedCPP) super.clone();
		ArrayList<String> listaClone = (ArrayList<String>) falhaUnsignedCPP.getResultadoAnalise().clone();
		falhaUnsignedCPP.setResultadoanalise(listaClone);
		return falhaUnsignedCPP;
	}

}
