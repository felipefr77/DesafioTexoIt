package com.texoit.model;

public class DadosResult {
	
	private String producer;
	private Integer interval;
	private Integer previousWin;
	private Integer followingWin;

	public DadosResult(String producer, Integer interval, Integer previousWin, Integer followingWin) {
		this.producer = producer;
		this.interval = interval;
		this.previousWin = previousWin;
		this.followingWin = followingWin;
	}
	
	public DadosResult() {
		
	}
	public void setProducer(String producer) {
		this.producer = producer;
	}
	public void setInterval(Integer interval) {
		this.interval = interval;
	}
	public void setPreviousWin(Integer previousWin) {
		this.previousWin = previousWin;
	}
	public void setFollowingWin(Integer followingWin) {
		this.followingWin = followingWin;
	}
	public String getProducer() {
		return producer;
	}
	public Integer getInterval() {
		return interval;
	}
	public Integer getPreviousWin() {
		return previousWin;
	}
	public Integer getFollowingWin() {
		return followingWin;
	}
}
