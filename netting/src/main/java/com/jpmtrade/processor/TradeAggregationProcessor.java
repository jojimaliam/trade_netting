package com.jpmtrade.processor;

import static com.jpmtrade.constants.Constants.TRADE_EXISTS;
import static com.jpmtrade.constants.Constants.TRADE_NEW;

import org.apache.log4j.Logger;

import com.jpmtrade.container.TradeContainer;
import com.jpmtrade.container.TradeContainerImpl;
import com.jpmtrade.model.Trade;
import com.jpmtrade.model.TradeRealtimePosition;
import com.jpmtrade.util.TradeCalculatorFactory;

public class TradeAggregationProcessor {
	private TradeContainer container;
	final static Logger logger = Logger.getLogger(TradeAggregationProcessor.class);

	public void processTrade(Trade newTrade) {
		try {
			container =TradeContainerImpl.getInstance();
			newTrade.validate();
			TradeRealtimePosition position = container.getPositionByTrade(newTrade);
			if (null == position) {
				position = container.addAndGetPosition(newTrade);
			}
			Trade existingTrade = container.getTrades().get(newTrade.getTradeId());
			if (null != existingTrade) {
				if (newTrade.getTradeVersion() >= existingTrade.getTradeVersion()) {
					TradeRealtimePosition existingPosition = container.getPositionByTrade(existingTrade);
					existingPosition.setQuantity(TradeCalculatorFactory.getCalculator(existingTrade, TRADE_EXISTS)
							.calculate(existingTrade, existingPosition));
				} else {
					return;
				}
			}

			position.setQuantity(
					TradeCalculatorFactory.getCalculator(newTrade, TRADE_NEW).calculate(newTrade, position));

			position.addTrade(newTrade);
			container.addTrade(newTrade);

		} catch (Exception ex) {
         
			logger.error(ex.getMessage(), ex);
		}
	}

	public TradeContainer getContainer() {
		return container;
	}
}
