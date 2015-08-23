package com.stocks.model;




public class StockVO {
	

	
	private String stockSymbol;
	private double lsttDiv = 0.0;
	private double fixDiv = 0.0;
	private double parValue = 0.0;
	private double tickerPrice = 0.0; 
	private String stockType;


	
	public String getStockType() {
		return stockType;
	}


	public void setStockType(String stockType) {
		this.stockType = stockType;
	}


	public String getStockSymbol() {
		return stockSymbol;
	}


	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}


	public double getLsttDiv() {
		return lsttDiv;
	}


	public void setLsttDiv(double lsttDiv) {
		this.lsttDiv = lsttDiv;
	}


	public double getFixDiv() {
		return fixDiv;
	}


	public void setFixDiv(double fixDiv) {
		this.fixDiv = fixDiv;
	}


	public double getParValue() {
		return parValue;
	}


	public void setParValue(double parValue) {
		this.parValue = parValue;
	}


	public double getTickerPrice() {
		return tickerPrice;
	}


	public void setTickerPrice(double tickerPrice) {
		this.tickerPrice = tickerPrice;
	}


	

	
}
