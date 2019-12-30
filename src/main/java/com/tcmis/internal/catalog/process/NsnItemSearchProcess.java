package com.tcmis.internal.catalog.process;

import java.util.Collection;

import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.internal.catalog.beans.NsnItemSearchViewBean;

import radian.tcmis.common.util.SqlHandler;

/******************************************************************************
 * Process used by MaterialSearchAction
 * @version 1.0
 *****************************************************************************/

public class NsnItemSearchProcess extends GenericProcess {

	public NsnItemSearchProcess(String client) {
		super(client);
	}

	@SuppressWarnings("unchecked")
	public Collection<NsnItemSearchViewBean> searchNsnItems(String searchArgument) throws BaseException, Exception {
		String query = "select i.item_id, i.item_type, i.item_desc, cpig.status "
				+ "from item i, catalog_part_item_group cpig where "
				+ "cpig.company_id = 'USGOV' and cpig.catalog_id = 'NSN' and "
				+ "cpig.cat_part_no = " + SqlHandler.delimitString(searchArgument)
				+ " and cpig.item_id = i.item_id order by i.item_id";
		return factory.setBean(new NsnItemSearchViewBean()).selectQuery(query);
	}

}
