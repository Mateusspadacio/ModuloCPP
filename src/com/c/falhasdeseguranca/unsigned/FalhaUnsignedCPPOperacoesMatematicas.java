package com.c.falhasdeseguranca.unsigned;

import java.util.ArrayList;
import java.util.List;

import com.constants.Constants;

public class FalhaUnsignedCPPOperacoesMatematicas {

	private List<String> variaveisCorrigidas;
	private ArrayList<String> variaveisNaoTratadas;
	
	private boolean isIf = false;
	private boolean isElse = false;
	private int contarChaves = 0;
	

	protected FalhaUnsignedCPPOperacoesMatematicas() {
		variaveisCorrigidas = new ArrayList<>();
		variaveisNaoTratadas = new ArrayList<>();
	}

	public void detectaAdicao(String linha, int num) {
		linha = linha.replace(" ", "");
		detectaAdicaoNaoTratada(linha, num);
		detectaAdicaoComplexa(linha, num);
		detectaSubtracaoSimples(linha, num);
		
		detectaVariavel(linha);
		
	}

	private void detectaAdicaoNaoTratada(String linha, int num) {
		for (String variavel : variaveisNaoTratadas) {
			
			if (!startWith(variavel, Constants.CARACTER_MAIS)) {
				continue;
			}
			
			variavel = variavel.replace(Constants.CARACTER_MAIS, "");
			
			if (linha.contains(Constants.PALAVRA_IF) && (linha.contains(variavel + Constants.CARACTER_MENOR) 
					|| linha.contains(Constants.CARACTER_MAIOR + variavel))) {
				removeVariavel(Constants.CARACTER_MAIS + variavel);
				break;
			}
		}

	}
	
	private void detectaAdicaoComplexa(String linha, int num) {
		if (linha.contains(Constants.PALAVRA_IF) && linha.contains("UINT_MAX" + Constants.CARACTER_MENOS)) {
			isIf = true;
		}
		
		if (isIf && linha.contains(Constants.CARACTER_CHAVE_ABERTA)) {
			contarChaves++;
		}
		
		if (isIf && linha.contains(Constants.CARACTER_CHAVE_FECHADA)) {
			contarChaves--;
		}
		
		if (linha.contains(Constants.PALAVRA_ELSE)) {
			isElse = true;
		}
		
		if (isElse && contarChaves > 0) {
			removeVariavelTratadaAdicaoComplexa(linha);
		}
		
		if (contarChaves == 0) {
			isElse = false;
			isIf = false;
		}
	}
	
	private void removeVariavelTratadaAdicaoComplexa(String linha) {
		for (String variavel : variaveisCorrigidas) {
			
			if (linha.contains(variavel + Constants.CARACTER_IGUAL) && linha.contains(Constants.CARACTER_MAIS)) {
				removeVariavel(Constants.CARACTER_MAIS + variavel);
				break;
			}
		}
		
	}
	
	private void detectaVariavel(String linha) {
		for (String variavel : variaveisCorrigidas) {
			if (linha.contains(variavel + Constants.CARACTER_IGUAL) && linha.contains(Constants.CARACTER_MAIS)) {
				extrairVariavelQueRecebeResultado(Constants.CARACTER_MAIS + variavel);
				continue;
			}
			
			if (linha.contains(variavel + Constants.CARACTER_IGUAL) && linha.contains(Constants.CARACTER_MENOS)) {
				extrairVariavelQueRecebeResultado(Constants.CARACTER_MENOS + variavel);
			}
		}
	}
	
	private void detectaSubtracaoSimples(String linha, int num) {
		for (String variavel : variaveisNaoTratadas) {
			
			if (!startWith(variavel, Constants.CARACTER_MENOS)) {
				continue;
			}
			
			variavel = variavel.replace(Constants.CARACTER_MENOS, "");
			
			if (linha.contains(Constants.PALAVRA_IF) && (linha.contains(variavel + Constants.CARACTER_MAIOR)
					|| linha.contains(Constants.CARACTER_MENOR + variavel))) {
				removeVariavel(Constants.CARACTER_MENOS + variavel);
				break;
			}
		}
	}
	
	private void removeVariavel(String variavel) {
		variaveisNaoTratadas.remove(variavel);
		variaveisCorrigidas.remove(variavel.replaceFirst("(\\+*)(\\-*)", ""));
	}
	
	private void extrairVariavelQueRecebeResultado(String variavel) {
		variaveisNaoTratadas.add(variavel);
	}

	public void setVariaveisCorrigidas(List<String> variaveisCorrigidas) {
		this.variaveisCorrigidas = variaveisCorrigidas;
	}

	public ArrayList<String> getVariaveisNaoTratadas() {
		return variaveisNaoTratadas;
	}
	
	private boolean startWith(String variavel, String value) {
		return variavel.startsWith(value);
	}
	
}
