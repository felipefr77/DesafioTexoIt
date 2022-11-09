package com.texoit.model;

import java.util.ArrayList;
import java.util.List;

public class Result {

	private List<DadosResult> min = new ArrayList<>();
	private List<DadosResult> max = new ArrayList<>();
	
	public void addMin(DadosResult dados) {
		this.min.add(dados);
	}
	public void addMax(DadosResult dados) {
		this.max.add(dados);
	}
	public List<DadosResult> getMin() {
		return min;
	}
	public void setMin(List<DadosResult> min) {
		this.min = min;
	}
	public List<DadosResult> getMax() {
		return max;
	}
	public void setMax(List<DadosResult> max) {
		this.max = max;
	}
}
