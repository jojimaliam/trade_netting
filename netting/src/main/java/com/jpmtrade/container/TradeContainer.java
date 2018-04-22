package com.jpmtrade.container;

import java.util.List;
import java.util.Map;

import com.jpmtrade.model.TradeAggregatedPositionKey;
import com.jpmtrade.model.Trade;
import com.jpmtrade.model.TradeRealtimePosition;

public interface TradeContainer {

	public void addTrade(Trade trade);

	public TradeRealtimePosition addAndGetPosition(Trade trade);

	public TradeRealtimePosition getPositionByTrade(Trade trade);

	public Map<Integer, Trade> getTrades();

	public List<TradeRealtimePosition> getPositionsList();

	public TradeRealtimePosition getPositionByKey(TradeAggregatedPositionKey key);

	public List<Trade> getAllTrades();

	public Map<TradeAggregatedPositionKey, TradeRealtimePosition> getTradePositions();

}
