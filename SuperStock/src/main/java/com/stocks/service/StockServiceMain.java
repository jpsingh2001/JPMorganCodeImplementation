package com.stocks.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.stocks.model.StockTradeVO;
import com.stocks.model.StockVO;

public class StockServiceMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		StockServiceMain stpckServiceObj = StockServiceMain();
		stpckServiceObj.storeStockTrade();
		System.out.print(stpckServiceObj.calculateDividendYieldTest());
		System.out.print(stpckServiceObj.calculatePERatioTest());
		System.out.print(stpckServiceObj.calculateStockPriceTest());
		System.out.print(stpckServiceObj.calculateGBCEAllShareIndexTest());
	}
	

	public void storeStockTrade(){


StockUtil stockUtil = new StockUtil();
		try{
			List<StockTradeVO> tradeStockList = getTradeStockListVO();

			// Insert many trades in the stock system
			for(StockTradeVO trade: tradeStockList){
				boolean result = stockUtil.recordTrade(trade);
			}


		}catch(Exception exception){
		}


	}	

	private List<StockTradeVO> getTradeStockListVO() {
		StockVO stockVO = new StockVO();
		stockVO.setFixDiv(10);
		stockVO.setLsttDiv(2);
		stockVO.setParValue(100);
		stockVO.setStockSymbol("TEA");
		stockVO.setStockType("common");
		stockVO.setTickerPrice(50);
		StockTradeVO stockTradeVO= new StockTradeVO();
		stockTradeVO.setBuyorsell(true); //True for Buy and false for sell
		stockTradeVO.setPrice(60);
		stockTradeVO.setSharesQuantity(5);
		stockTradeVO.setTimeStamp(new Date());
		stockTradeVO.setStock(stockVO);
		List<StockTradeVO> lst = new ArrayList<StockTradeVO>();
		lst.add(stockTradeVO);
		
		
		return lst;
	}


	public void calculateDividendYieldTest(){
		StockUtil stockUtil = new StockUtil();
		try{

			String[] stockSymbols = {"TEA", "POP", "ALE", "GIN", "JOE"};
			for(String stockSymbol: stockSymbols){
				double dividendYield = stockUtil.calculateDividendYield(stockSymbol);
			}

		}catch(Exception exception){
		}

	}

	public void calculatePERatioTest(){
		StockUtil stockUtil = new StockUtil();
		try{


			String[] stockSymbols = {"TEA", "POP", "ALE", "GIN", "JOE"};
			//String[] stockSymbols = {"TEA"};
			for(String stockSymbol: stockSymbols){
				double peRatio = stockUtil.calculatePERatio(stockSymbol);
			}
		}catch(Exception exception){
		}

	}

	public void calculateStockPriceTest(){
		StockUtil stockUtil = new StockUtil();
		try{

			
			String[] stockSymbols = {"TEA", "POP", "ALE", "GIN", "JOE"};
			//String[] stockSymbols = {"TEA"};
			for(String stockSymbol: stockSymbols){
				double stockPrice = stockUtil.calculateStockPrice(stockSymbol);
			}

			
		}catch(Exception exception){
		}

	}

	public void calculateGBCEAllShareIndexTest(){
		StockUtil stockUtil = new StockUtil();
		try{

			
			double GBCEAllShareIndex = stockUtil.calculateGBCEAllShareIndex();
			
		}catch(Exception exception){
		}

	}


}
