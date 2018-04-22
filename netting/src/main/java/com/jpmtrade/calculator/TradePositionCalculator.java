package com.jpmtrade.calculator;

import com.jpmtrade.model.Trade;
import com.jpmtrade.model.TradeRealtimePosition;

public interface TradePositionCalculator {

	public Integer calculate(Trade trade, TradeRealtimePosition position);

}
