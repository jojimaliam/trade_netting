package com.jpmtrade.processor;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.jpmtrade.model.Trade;
import com.jpmtrade.model.TradeAggregatedPositionKey;
import com.jpmtrade.model.TradeDirection;
import com.jpmtrade.model.TradeOperation;
import com.jpmtrade.model.TradeRealtimePosition;

import junit.framework.TestCase;

public class TradeAggregationProcessorTest {
	TradeAggregationProcessor tradeAggPrcr;

	@Before
	public void setup() {
		tradeAggPrcr = new TradeAggregationProcessor();
	}

	@Test
	public void testXYZ() {
		tradeAggPrcr.processTrade(createTrade("1234|1|XYZ|100|BUY|ACC-1234|NEW"));
		tradeAggPrcr.processTrade(createTrade("1234|2|XYZ|150|BUY|ACC-1234|AMEND"));
		printTrade(tradeAggPrcr);
		printPosition(tradeAggPrcr);
		verifyPosition(tradeAggPrcr, "ACC-1234|XYZ|150|1234");
	}

	@Test
	public void testQED() {
		tradeAggPrcr.processTrade(createTrade("5678|1|QED|200|BUY|ACC-2345|NEW"));
		tradeAggPrcr.processTrade(createTrade("5678|2|QED|0|BUY|ACC-2345|CANCEL"));
		printTrade(tradeAggPrcr);
		printPosition(tradeAggPrcr);
		verifyPosition(tradeAggPrcr, "ACC-2345|QED|0|5678");
	}

	@Test
	public void testRET() {
		tradeAggPrcr.processTrade(createTrade("2233|1|RET|100|SELL|ACC-3456|NEW"));
		tradeAggPrcr.processTrade(createTrade("2233|2|RET|400|SELL|ACC-3456|AMEND"));
		tradeAggPrcr.processTrade(createTrade("2233|3|RET|0|SELL|ACC-3456|CANCEL"));
		printTrade(tradeAggPrcr);
		printPosition(tradeAggPrcr);
		verifyPosition(tradeAggPrcr, "ACC-3456|RET|0|2233");
	}

	@Test
	public void testYUI() {
		tradeAggPrcr.processTrade(createTrade("8896|1|YUI|300|BUY|ACC-4567|NEW"));
		tradeAggPrcr.processTrade(createTrade("6638|1|YUI|100|SELL|ACC-4567|NEW"));
		printTrade(tradeAggPrcr);
		printPosition(tradeAggPrcr);
		verifyPosition(tradeAggPrcr, "ACC-4567|YUI|200|8896,6638");
	}

	@Test
	public void testHJK() {
		tradeAggPrcr.processTrade(createTrade("6363|1|HJK|200|BUY|ACC-5678|NEW"));
		tradeAggPrcr.processTrade(createTrade("7666|1|HJK|200|BUY|ACC-5678|NEW"));
		tradeAggPrcr.processTrade(createTrade("6363|2|HJK|100|BUY|ACC-5678|AMEND"));
		tradeAggPrcr.processTrade(createTrade("7666|2|HJK|50|SELL|ACC-5678|AMEND"));
		printTrade(tradeAggPrcr);
		printPosition(tradeAggPrcr);
		verifyPosition(tradeAggPrcr, "ACC-5678|HJK|50|6363,7666");
	}

	@Test
	public void testFVBnGBN() {
		tradeAggPrcr.processTrade(createTrade("8686|1|FVB|100|BUY|ACC-6789|NEW"));
		tradeAggPrcr.processTrade(createTrade("8686|2|GBN|100|BUY|ACC-6789|AMEND"));
		tradeAggPrcr.processTrade(createTrade("9654|1|FVB|200|BUY|ACC-6789|NEW"));
		printTrade(tradeAggPrcr);
		printPosition(tradeAggPrcr);
		verifyPosition(tradeAggPrcr, "ACC-6789|GBN|100|8686");
		verifyPosition(tradeAggPrcr, "ACC-6789|FVB|200|9654,8686");
	}

	@Test
	public void testJKL() {
		tradeAggPrcr.processTrade(createTrade("1025|1|JKL|100|BUY|ACC-7789|NEW"));
		tradeAggPrcr.processTrade(createTrade("1036|1|JKL|100|BUY|ACC-7789|NEW"));
		tradeAggPrcr.processTrade(createTrade("1025|2|JKL|100|SELL|ACC-8877|AMEND"));
		printTrade(tradeAggPrcr);
		printPosition(tradeAggPrcr);
		verifyPosition(tradeAggPrcr, "ACC-7789|JKL|100|1036,1025");
		verifyPosition(tradeAggPrcr, "ACC-8877|JKL|-100|1025");
	}

	@Test
	public void testKLOHJK() {
		tradeAggPrcr.processTrade(createTrade("1122|1|KLO|100|BUY|ACC-9045|NEW"));
		tradeAggPrcr.processTrade(createTrade("1122|2|HJK|100|SELL|ACC-9045|AMEND"));
		tradeAggPrcr.processTrade(createTrade("1122|3|KLO|100|SELL|ACC-9045|AMEND"));
		tradeAggPrcr.processTrade(createTrade("1144|1|KLO|300|BUY|ACC-9045|NEW"));
		tradeAggPrcr.processTrade(createTrade("1144|2|KLO|400|BUY|ACC-9045|AMEND"));
		tradeAggPrcr.processTrade(createTrade("1155|1|KLO|600|SELL|ACC-9045|NEW"));
		tradeAggPrcr.processTrade(createTrade("1155|2|KLO|0|BUY|ACC-9045|CANCEL"));
		printTrade(tradeAggPrcr);
		printPosition(tradeAggPrcr);
		verifyPosition(tradeAggPrcr, "ACC-9045|KLO|300|1122,1144,1155");
		verifyPosition(tradeAggPrcr, "ACC-9045|HJK|0|1122");
	}

	@Test
	public void testAllTrades() {
		tradeAggPrcr.processTrade(createTrade("1234|1|XYZ|100|BUY|ACC-1234|NEW"));
		tradeAggPrcr.processTrade(createTrade("1234|2|XYZ|150|BUY|ACC-1234|AMEND"));
		tradeAggPrcr.processTrade(createTrade("5678|1|QED|200|BUY|ACC-2345|NEW"));
		tradeAggPrcr.processTrade(createTrade("5678|2|QED|0|BUY|ACC-2345|CANCEL"));
		tradeAggPrcr.processTrade(createTrade("2233|1|RET|100|SELL|ACC-3456|NEW"));
		tradeAggPrcr.processTrade(createTrade("2233|2|RET|400|SELL|ACC-3456|AMEND"));
		tradeAggPrcr.processTrade(createTrade("2233|3|RET|0|SELL|ACC-3456|CANCEL"));
		tradeAggPrcr.processTrade(createTrade("8896|1|YUI|300|BUY|ACC-4567|NEW"));
		tradeAggPrcr.processTrade(createTrade("6638|1|YUI|100|SELL|ACC-4567|NEW"));
		tradeAggPrcr.processTrade(createTrade("6363|1|HJK|200|BUY|ACC-5678|NEW"));
		tradeAggPrcr.processTrade(createTrade("7666|1|HJK|200|BUY|ACC-5678|NEW"));
		tradeAggPrcr.processTrade(createTrade("6363|2|HJK|100|BUY|ACC-5678|AMEND"));
		tradeAggPrcr.processTrade(createTrade("7666|2|HJK|50|SELL|ACC-5678|AMEND"));
		tradeAggPrcr.processTrade(createTrade("8686|1|FVB|100|BUY|ACC-6789|NEW"));
		tradeAggPrcr.processTrade(createTrade("8686|2|GBN|100|BUY|ACC-6789|AMEND"));
		tradeAggPrcr.processTrade(createTrade("9654|1|FVB|200|BUY|ACC-6789|NEW"));
		tradeAggPrcr.processTrade(createTrade("1025|1|JKL|100|BUY|ACC-7789|NEW"));
		tradeAggPrcr.processTrade(createTrade("1036|1|JKL|100|BUY|ACC-7789|NEW"));
		tradeAggPrcr.processTrade(createTrade("1025|2|JKL|100|SELL|ACC-8877|AMEND"));
		tradeAggPrcr.processTrade(createTrade("1122|1|KLO|100|BUY|ACC-9045|NEW"));
		tradeAggPrcr.processTrade(createTrade("1122|2|HJK|100|SELL|ACC-9045|AMEND"));
		tradeAggPrcr.processTrade(createTrade("1122|3|KLO|100|SELL|ACC-9045|AMEND"));
		tradeAggPrcr.processTrade(createTrade("1144|1|KLO|300|BUY|ACC-9045|NEW"));
		tradeAggPrcr.processTrade(createTrade("1144|2|KLO|400|BUY|ACC-9045|AMEND"));
		tradeAggPrcr.processTrade(createTrade("1155|1|KLO|600|SELL|ACC-9045|NEW"));
		tradeAggPrcr.processTrade(createTrade("1155|2|KLO|0|BUY|ACC-9045|CANCEL"));
		printTrade(tradeAggPrcr);
		printPosition(tradeAggPrcr);
		verifyPosition(tradeAggPrcr, "ACC-1234|XYZ|150|1234");
		verifyPosition(tradeAggPrcr, "ACC-2345|QED|0|5678");
		verifyPosition(tradeAggPrcr, "ACC-3456|RET|0|2233");
		verifyPosition(tradeAggPrcr, "ACC-4567|YUI|200|8896,6638");
		verifyPosition(tradeAggPrcr, "ACC-5678|HJK|50|6363,7666");
		verifyPosition(tradeAggPrcr, "ACC-6789|GBN|100|8686");
		verifyPosition(tradeAggPrcr, "ACC-6789|FVB|200|9654,8686");
		verifyPosition(tradeAggPrcr, "ACC-7789|JKL|100|1036,1025");
		verifyPosition(tradeAggPrcr, "ACC-8877|JKL|-100|1025");

	}
	
	

	private void verifyPosition(TradeAggregationProcessor builder, String string) {
		String[] split = string.split("\\|");
		TradeAggregatedPositionKey id = new TradeAggregatedPositionKey(split[0], split[1]);
		TradeRealtimePosition position = builder.getContainer().getPositionByKey(id);
		TestCase.assertEquals(position.getQuantity().toString(), split[2]);
	}

	private Trade createTrade(String string) {
		String[] split = string.split("\\|");
		Trade trade = new Trade(new Integer(split[0]), new Integer(split[1]));
		trade.setSecurityId(split[2]);
		trade.setQuantity(new Integer(split[3]));
		trade.setDirection(TradeDirection.valueOf(split[4]));
		trade.setAccountNumber(split[5]);
		trade.setOperation(TradeOperation.valueOf(split[6]));
		return trade;
	}

	private void printTrade(TradeAggregationProcessor tradeAggPrcr) {
		List<Trade> tradeList = tradeAggPrcr.getContainer().getAllTrades();
		System.out.println("----------------Trades---------------");
		System.out.println("Account |SYM|QUAN|OPR|DIR|VER|TradeId");
		tradeList.forEach(System.out::println);
	}

	private void printPosition(TradeAggregationProcessor tradeAggPrcr) {
		Map<TradeAggregatedPositionKey, TradeRealtimePosition>  positionMap= tradeAggPrcr.getContainer().getTradePositions();
		System.out.println("----------------positions---------------");
		System.out.println("Account |SYM|QUAN|TradeIds");
		System.out.println("------------------------------------");
		positionMap.forEach((k,v)-> {
			System.out.print(k)	;System.out.println(v);	
		});

	}
}
