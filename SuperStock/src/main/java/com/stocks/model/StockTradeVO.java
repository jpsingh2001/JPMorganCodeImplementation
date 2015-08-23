package com.stocks.model;

import java.util.Date;

/**
 * 
 * @author Jaidermes Nebrijo Duarte
 *
 */
public class StockTradeVO {
	
	private Date timeStamp = null;
	private StockVO stock = null;
	private boolean buyorsell;
	
	private int sharesQuantity = 0;
	
	private double price = 0.0;
	
	
	
	
	
	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public boolean isBuyorsell() {
		return buyorsell;
	}

	public void setBuyorsell(boolean buyorsell) {
		this.buyorsell = buyorsell;
	}

	public int getSharesQuantity() {
		return sharesQuantity;
	}

	public void setSharesQuantity(int sharesQuantity) {
		this.sharesQuantity = sharesQuantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public StockVO getStock() {
		return stock;
	}
	
	public void setStock(StockVO stock) {
		this.stock = stock;
	}

	
	
	
}
