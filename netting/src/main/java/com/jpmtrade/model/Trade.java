package com.jpmtrade.model;

import static com.jpmtrade.constants.Constants.PIPE;

import com.jpmtrade.Exception.TradeValidationException;

public class Trade {
	private Integer tradeId;
	private Integer tradeVersion;
	private String securityId;
	private Integer quantity;
	private String accountNumber;
	private TradeDirection direction;
	private TradeOperation operation;

	public Trade(Integer tradeId, Integer tradeVersion) {
		this.tradeId = tradeId;
		this.tradeVersion = tradeVersion;
	}

	public Integer getTradeId() {
		return tradeId;
	}

	public Integer getTradeVersion() {
		return tradeVersion;
	}

	public String getSecurityId() {
		return securityId;
	}

	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public TradeDirection getDirection() {
		return direction;
	}

	public void setDirection(TradeDirection direction) {
		this.direction = direction;
	}

	public TradeOperation getOperation() {
		return operation;
	}

	public void setOperation(TradeOperation operation) {
		this.operation = operation;
	}
	
	public void validate() throws TradeValidationException {

		if (null == tradeId || null == tradeVersion || null == securityId || null == quantity || null == accountNumber
				|| null == direction || null == operation) {
			throw new TradeValidationException("Mandatory Values missing for the Trade");
		}

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tradeId == null) ? 0 : tradeId.hashCode());
		result = prime * result + ((tradeVersion == null) ? 0 : tradeVersion.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trade other = (Trade) obj;
	     if (!tradeId.equals(other.tradeId))
			return false;
	     if (!tradeVersion.equals(other.tradeVersion))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return new StringBuilder(accountNumber).append(PIPE).append(securityId).append(PIPE).append(quantity)
				.append(PIPE).append(operation).append(PIPE).append(direction).append(PIPE).append(tradeVersion)
				.append(PIPE).append(tradeId).toString();
	}
}
