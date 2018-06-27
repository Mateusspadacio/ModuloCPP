package com.c.falhasdeseguranca.getsvspsp;

import java.util.ArrayList;

import com.metodos.padrao.MetodosAdapter;
import com.metodos.padrao.MetodosPadrao;

public class FalhaGetsVSPSPCPP implements MetodosPadrao, Cloneable{

	private ArrayList<String> resultadoAnalise;
	private MetodosAdapter adapter;
	
	public FalhaGetsVSPSPCPP() {
		resultadoAnalise = new ArrayList<>();
		adapter = new FalhaGetsVSPSPCPPAdapter();
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
		FalhaGetsVSPSPCPP falhaGetsCPP = (FalhaGetsVSPSPCPP) super.clone();
		ArrayList<String> listaClone = (ArrayList<String>) falhaGetsCPP.getResultadoAnalise().clone();
		falhaGetsCPP.setResultadoanalise(listaClone);
		return falhaGetsCPP;
	}

}
