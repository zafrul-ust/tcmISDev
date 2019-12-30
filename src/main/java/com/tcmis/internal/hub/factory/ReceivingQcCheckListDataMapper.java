package com.tcmis.internal.hub.factory;

import java.math.BigDecimal;

import com.tcmis.common.exceptions.BaseException;

public interface ReceivingQcCheckListDataMapper {

	public String definedShelfLifeItem(BigDecimal receiptId) throws BaseException;
	public String definedShelfLifeItem(BigDecimal po, BigDecimal poLine) throws BaseException;
}
