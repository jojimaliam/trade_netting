package com.jpmtrade.util;

import static com.jpmtrade.constants.Constants.SEPERATOR;
import static com.jpmtrade.constants.Constants.TRADE_EXISTS;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import com.jpmtrade.calculator.TradeDecrementalCalculator;
import com.jpmtrade.calculator.TradeIncrementalCalculator;
import com.jpmtrade.calculator.TradePositionCalculator;
import com.jpmtrade.model.Trade;
import com.jpmtrade.model.TradeDirection;
import com.jpmtrade.model.TradeOperation;

public class TradeCalculatorFactory {
	private static final Map<String, TradePositionCalculator> calcualatorcache = new HashMap();

	public static TradePositionCalculator getCalculator(Trade trade, String type) {

		Predicate<Trade> operatorPredicate = Trade -> (TradeOperation.CANCEL == trade.getOperation());

		String key = new StringBuilder(trade.getDirection().name()).append(SEPERATOR)
				.append(trade.getOperation().name() + SEPERATOR + type).toString();
		if (calcualatorcache.containsKey(key)) {
			return calcualatorcache.get(key);
		}
		TradePositionCalculator calculator;
		if (TradeDirection.BUY == trade.getDirection()) {

			if (operatorPredicate.test(trade)) {
				calculator = new TradeDecrementalCalculator();

			} else {
				calculator = new TradeIncrementalCalculator();
			}

		} else {

			if (operatorPredicate.test(trade)) {
				calculator = new TradeIncrementalCalculator();

			} else {
				calculator = new TradeDecrementalCalculator();
			}

		}
		if (TRADE_EXISTS.equals(type)) {
			if (calculator instanceof TradeIncrementalCalculator) {
				calculator = new TradeDecrementalCalculator();
			} else {
				calculator = new TradeIncrementalCalculator();
			}
		}
		calcualatorcache.put(key, calculator);
		return calculator;
	}

}
