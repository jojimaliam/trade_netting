package com.jpmtrade.model;

import static com.jpmtrade.constants.Constants.PIPE;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class TradeRealtimePosition {

	private Integer quantity = 0;
	private Set<Integer> trades = new HashSet();

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public void addTrade(Trade trade) {
		trades.add(trade.getTradeId());
	}

	public Set<Integer> getTrades() {
		return trades;
	}

	@Override
	public String toString() {

		String tradeIds = trades.stream().map(tradeId -> tradeId.toString()).collect(Collectors.joining(","));
		return new StringBuilder(quantity.toString()).append(PIPE).append(tradeIds).toString();
	}

}
