package com.tcmis.internal.catalog.factory;

import java.math.BigDecimal;
import java.util.Collection;

import com.tcmis.client.common.beans.CatalogQueueBean;
import com.tcmis.common.admin.beans.VvLocaleBean;
import com.tcmis.common.exceptions.BaseException;

public interface CatalogQueueDataMapper {

	public Collection<VvLocaleBean> possibleAlternateLocales(CatalogQueueBean workQueueItem) throws BaseException;
	public void changeQueueLocale(BigDecimal qId, String task, String localeOverride) throws BaseException;
	public void changeItemLocaleByQId(BigDecimal qId, String localeOverride) throws BaseException;
}
