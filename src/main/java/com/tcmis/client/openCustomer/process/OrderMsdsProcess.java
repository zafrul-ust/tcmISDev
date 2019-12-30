package com.tcmis.client.openCustomer.process;

import java.util.Collection;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.openCustomer.beans.OrderMsdsBean;
import com.tcmis.client.openCustomer.beans.OrderMsdsInputBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;

/**
 * ***************************************************************************
 * Process for Personnel
 *
 * @version 1.0
 *          ***************************************************************************
 */
public class OrderMsdsProcess extends GenericProcess {
	Log log = LogFactory.getLog(this.getClass());

	public OrderMsdsProcess(String client) {
		this(client, defaultLocale);
	}

	public OrderMsdsProcess(String client, Locale locale) {
		super(client, locale, new OrderMsdsBean());
	}

	@SuppressWarnings("unchecked")
	public Collection<OrderMsdsBean> getSearchResult(PersonnelBean user, OrderMsdsInputBean input) throws BaseException {
		StringBuilder query = new StringBuilder("SELECT * FROM TABLE (PKG_OPEN_CUSTOMER.FX_CUST_MR_LINE_MSDS_SEARCH(");
		query.append(user.getPersonnelIdBigDecimal()).append(", ");
		query.append(input.getCustomerId()).append(", ");
		query.append("'").append(input.getSearchArgument()).append("', ");
		query.append(input.getMonthsBack()).append("))");

		return factory.selectQuery(query.toString());
	}
}
