package com.stocks.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.log4j.Logger;

import com.stocks.model.StockTradeVO;
import com.stocks.model.StockVO;

public class StockUtil {
	
	
private HashMap<String, StockVO> stocks = null;

	
	private ArrayList<StockTradeVO> trades = null;

	
	public StockUtil(){
		trades = new ArrayList<StockTradeVO>();
		stocks = new HashMap<String, StockVO>();
	}

	
	public HashMap<String, StockVO> getStocks() {
		return stocks;
	}


	public void setStocks(HashMap<String, StockVO> stocks) {
		this.stocks = stocks;
	}

	
	public ArrayList<StockTradeVO> getTrades() {
		return trades;
	}

	
	public void setTrades(ArrayList<StockTradeVO> trades) {
		this.trades = trades;
	}

	// Method implementation to record Trade
	public boolean recordTrade(StockTradeVO trade) throws Exception{
		boolean result = false;
		try{
			result = trades.add(trade);
		}catch(Exception exception){
			throw new Exception("An error has occurred recording a trade in the system's backend.", exception);
		}
		return result;
	}

	
	public int getTradesNumber() {
		return trades.size();
	}

	
	public StockVO getStockBySymbol(String stockSymbol){
		StockVO stock = null;
		try{
			if(stockSymbol!=null){
				stock = stocks.get(stockSymbol);
			}
		}catch(Exception exception){
			
		}
		return stock;
	}
            // Method implementation to calculate Dividend Yield 
	public double getDividendYieldByStock(StockVO stackVO) {
		double dividendYieldStock = -1.0;
		if(stackVO.getTickerPrice() > 0.0){
			if( stackVO.getStockType()==StockConstants.COMMON){
				dividendYieldStock = stackVO.getLsttDiv() / stackVO.getTickerPrice();
			}else{
				dividendYieldStock = (stackVO.getFixDiv() * stackVO.getParValue() ) / stackVO.getTickerPrice();
			}
		}
		return dividendYieldStock;
	}


	/**
	 * Method to calculate P/E Ratio
	 * @return
	 */
	public double getPeRatio(StockVO stackVO) {
		double peRatio = -1.0;
		
		if(stackVO.getTickerPrice() > 0.0){
			peRatio = stackVO.getTickerPrice() / getDividendYieldByStock(stackVO);	
		}
		
		return peRatio;
	}
	
	/**
	 * Method to calculate Dividend Yield
	 * @return
	 */
	
	public double calculateDividendYield(String stockSymbol) throws Exception{
		double dividendYield = -1.0;

		try{
			StockVO stock = getStockBySymbol(stockSymbol);
	
			dividendYield = getDividendYieldByStock(stock);

		}catch(Exception exception){
			throw new Exception("Error Occure"+stockSymbol+".", exception);
		}
		return dividendYield;
	}

	/*
	 * (non-Javadoc)
	 * @see com.jpmorgan.stocks.simple.services.SimpleStockService#calculatePERatio(java.lang.String)
	 */
	public double calculatePERatio(String stockSymbol) throws Exception{
		double peRatio = -1.0;
		try{
			StockVO stock = getStockBySymbol(stockSymbol);


			peRatio = getPeRatio(stock);


		}catch(Exception exception){
			throw new Exception("Error Occured: ", exception);
		}
		return peRatio;
	}

	

	
	private class StockPredicate implements Predicate{
		/**
		 *
		 */
		private Logger logger = Logger.getLogger(StockPredicate.class);

		/**
		 * 
		 */
		private String stockSymbol = "";

		/**
		 * 
		 */
		private Calendar dateRange = null;

		/**
		 * 
		 * @param stockSymbol
		 * @param minutesRange
		 */
		public StockPredicate(String stockSymbol, int minutesRange){
			this.stockSymbol = stockSymbol;
			if( minutesRange > 0 ){
				dateRange = Calendar.getInstance();
				dateRange.add(Calendar.MINUTE, -minutesRange);
				logger.debug(String.format("Filter date pivot: %tF %tT", dateRange.getTime(), dateRange.getTime()));
			}

		}

		/**
		 * 
		 */
		public boolean evaluate(Object tradeObject) {
			StockTradeVO trade = (StockTradeVO) tradeObject;
			boolean shouldBeInclude = trade.getStock().getStockSymbol().equals(stockSymbol);
			if(shouldBeInclude && dateRange != null){
				shouldBeInclude = dateRange.getTime().compareTo(trade.getTimeStamp())<=0;
			}
			return shouldBeInclude;
		}

	}

	
	private double calculateStockPriceinRange(String stockSymbol, int minutesRange) throws Exception{
		double stockPrice = 0.0;


		
		Collection<StockTradeVO> tradesList = CollectionUtils.select(getTrades(), new StockPredicate(stockSymbol, minutesRange));


		// Calculate the summation
		double shareQuantityAcum = 0.0;
		double tradePriceAcum = 0.0;
		for(StockTradeVO trade : tradesList){
			// Calculate the summation of Trade Price x Quantity
			tradePriceAcum += (trade.getPrice() * trade.getSharesQuantity());
			// Acumulate Quantity
			shareQuantityAcum += trade.getSharesQuantity();
		}

		// calculate the stock price
		if(shareQuantityAcum > 0.0){
			stockPrice = tradePriceAcum / shareQuantityAcum;	
		}


		return stockPrice;
	}


	/*
	 * (non-Javadoc)
	 * @see com.jpmorgan.stocks.simple.services.SimpleStockService#calculateStockPrice()
	 */
	public double calculateStockPrice(String stockSymbol) throws Exception{
		double stockPrice = 0.0;

		try{
			StockVO stock = getStockBySymbol(stockSymbol);

			if(stock!=null){

			stockPrice = calculateStockPriceinRange(stockSymbol, 15);
			}


		}catch(Exception exception){
			throw new Exception("Error Occurred", exception);

		}


		return stockPrice;
	}

	
	public double calculateGBCEAllShareIndex() throws Exception{
		double allShareIndex = 0.0;
		
		// Calculate stock price for all stock in the system
		HashMap<String, StockVO> stocks = getStocks();
		ArrayList<Double> stockPrices = new ArrayList<Double>();
		for(String stockSymbol: stocks.keySet() ){
			double stockPrice = calculateStockPriceinRange(stockSymbol, 0);
			if(stockPrice > 0){
				stockPrices.add(stockPrice);
			}
		}
		
		if(stockPrices.size()>=1){
			double[] stockPricesArray = new double[stockPrices.size()];
			
			for(int i=0; i<=(stockPrices.size()-1); i++){
				stockPricesArray[i] = stockPrices.get(i).doubleValue();
			}
			// Calculates the GBCE All Share Index
			allShareIndex = StatUtils.geometricMean(stockPricesArray);
		}
		return allShareIndex;
	}

	
}
