package com.tcmis.internal.catalog.factory;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Collection;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.internal.catalog.beans.ItemStorageBean;

public interface IItemStorageDataMapper {
	
	Collection<ItemStorageBean> getSearchData(ItemStorageBean inputBean)
			throws BaseException, SQLException;

	Collection<BigDecimal> findItemsWithIncompleteStorageData() throws BaseException;

	Collection<PersonnelBean> listEmailRecipients() throws BaseException;
}
