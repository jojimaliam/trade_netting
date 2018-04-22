package com.jpmtrade.container;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.jpmtrade.model.Trade;
import com.jpmtrade.model.TradeAggregatedPositionKey;
import com.jpmtrade.model.TradeRealtimePosition;

public class TradeContainerImpl implements TradeContainer {

	private final Map<Integer, Trade> trades = new HashMap();
	private final Map<TradeAggregatedPositionKey, TradeRealtimePosition> tradePositions = new HashMap();

	@Override
	public void addTrade(Trade trade) {
		trades.put(trade.getTradeId(), trade);
	}

	@Override
	public Map<TradeAggregatedPositionKey, TradeRealtimePosition> getTradePositions() {
		return tradePositions;
	}

	@Override
	public TradeRealtimePosition getPositionByTrade(Trade trade) {
		return tradePositions.get(new TradeAggregatedPositionKey(trade.getAccountNumber(), trade.getSecurityId()));
	}

	@Override
	public TradeRealtimePosition addAndGetPosition(Trade trade) {
		TradeAggregatedPositionKey key = new TradeAggregatedPositionKey(trade.getAccountNumber(),
				trade.getSecurityId());
		TradeRealtimePosition tradePosition = new TradeRealtimePosition();
		tradePositions.put(key, tradePosition);

		return tradePosition;
	}

	@Override
	public Map<Integer, Trade> getTrades() {
		return trades;
	}

	@Override
	public List<TradeRealtimePosition> getPositionsList() {
		return new LinkedList<TradeRealtimePosition>(tradePositions.values());
	}

	@Override
	public TradeRealtimePosition getPositionByKey(TradeAggregatedPositionKey key) {
		return tradePositions.get(key);
	}

	@Override
	public List<Trade> getAllTrades() {
		return new LinkedList<Trade>(trades.values());
	}

}
