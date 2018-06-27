package com.c.falhasdeseguranca.getsvspsp;

import java.util.ArrayList;

import com.constants.Constants;
import com.metodos.padrao.MetodosAdapter;

public class FalhaGetsVSPSPCPPAdapter implements MetodosAdapter{

	private ArrayList<String> resultadoAnalise;
	
	protected FalhaGetsVSPSPCPPAdapter() {
		resultadoAnalise = new ArrayList<>();
	}
	
	@Override
	public void analisa(String linha, int num) {
		analisaGetsVSPSP(linha, num);
	}
	
	private void analisaGetsVSPSP(String linha, int num) {
		if (linha.contains(Constants.METODO_GETS) || 
				linha.contains(Constants.METODO_VSPRINTF) ||
				linha.contains(Constants.METODO_SPRINTF)) {
			
			addResultado(linha, num);
			return;
		}
	}

	
	private void addResultado(String linha, int num) {
		resultadoAnalise.add(num + Constants.CARACTER_TRACO + linha);
	}
	
	@Override
	public ArrayList<String> getResultado() {
		return resultadoAnalise;
	}

}
