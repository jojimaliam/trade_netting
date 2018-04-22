package com.jpmtrade.calculator;

import com.jpmtrade.model.Trade;
import com.jpmtrade.model.TradeRealtimePosition;

public class TradeIncrementalCalculator implements TradePositionCalculator {

	@Override
	public Integer calculate(Trade trade, TradeRealtimePosition position) {

		return position.getQuantity() + trade.getQuantity();
	}

}
