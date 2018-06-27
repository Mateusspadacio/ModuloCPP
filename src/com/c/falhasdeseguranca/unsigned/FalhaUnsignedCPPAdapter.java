package com.c.falhasdeseguranca.unsigned;

import java.util.ArrayList;

import com.constants.Constants;
import com.metodos.padrao.MetodosAdapter;

public class FalhaUnsignedCPPAdapter implements MetodosAdapter{

	private ArrayList<String> variaveisCorrigidas;
	private FalhaUnsignedCPPOperacoesMatematicas op;
	
	protected FalhaUnsignedCPPAdapter() {
		variaveisCorrigidas = new ArrayList<>();
		op = new FalhaUnsignedCPPOperacoesMatematicas();
	}
	
	@Override
	public void analisa(String linha, int num) {
		getVariaveisUnsigned(linha, num);
		
		if (!variaveisCorrigidas.isEmpty()) {
			op.setVariaveisCorrigidas(variaveisCorrigidas);
			op.detectaAdicao(linha, num);
		}
		
	}
	
	private void getVariaveisUnsigned(String linha, int num) {
		if (linha.contains(Constants.PALAVRA_UNSIGNED) && linha.contains(Constants.CARACTER_PARENTES_ABERTO)) {
			getVariaveisLinha(replaceValuesFromParameters(linha, num), num);
		} else if (linha.contains(Constants.PALAVRA_UNSIGNED)) {
			addVariaveisCorrigidas(getPureVariable(linha));
		}
	}
	
	private String replaceValuesFromParameters(String linha, int num) {
		return linha.replace(linha.substring(0, linha.indexOf(Constants.CARACTER_PARENTES_ABERTO) + 1), "")
				.replace(linha.substring(linha.lastIndexOf(Constants.CARACTER_PARENTES_FECHADO), linha.length()), "").trim();
	}
	
	private void getVariaveisLinha(String linha, int num) {
		String[] variaveis = linha.split(Constants.CARACTER_VIRGULA);
		
		for (String variavel : variaveis) {
			addVariaveisCorrigidas(getPureVariable(variavel));
		}
	}
	
	private String getPureVariable(String variavel) {
		if (variavel.contains(Constants.CARACTER_IGUAL)) {
			variavel = variavel.replace(variavel.substring(variavel.indexOf(Constants.CARACTER_IGUAL), variavel.length()), "");
		}
		
		return variavel.replaceAll(Constants.PALAVRA_UNSIGNED + "|" + Constants.PRIMITIVO_INT + "|" + Constants.PRIMITIVO_DOUBLE
									+ "|" + Constants.PRIMITIVO_FLOAT + "|" + Constants.PRIMITIVO_CHAR + "|" + Constants.PRIMITIVO_BYTE
									+ "|" + Constants.PRIMITIVO_LONG + "|" + Constants.PRIMITIVO_SHORT + "|" + Constants.CARACTER_IGUAL
									+ "|" + Constants.CARACTER_PONTO_VIRGULA, "").trim();
	}
	
	private void addVariaveisCorrigidas(String variavel) {
		variaveisCorrigidas.add(variavel);
	}

	@Override
	public ArrayList<String> getResultado() {
		return op.getVariaveisNaoTratadas();
	}

}
