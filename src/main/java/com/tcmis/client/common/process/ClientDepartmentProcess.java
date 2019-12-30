package com.tcmis.client.common.process;

//import java.io.*;
//import java.io.PrintWriter;

//import java.io.Writer;
//import java.math.BigDecimal;
//import java.text.DateFormat;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.common.beans.DeptBean;
import com.tcmis.client.common.factory.DeptBeanFactory;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;

/******************************************************************************
 * Process used by ManufacturerSearchAction
 * @version 1.0
 *****************************************************************************/

public class ClientDepartmentProcess extends BaseProcess {
	static Log log = LogFactory.getLog(ClientDepartmentProcess.class);
	protected DbManager dbManager = null;
	protected DeptBeanFactory factory = null;

	public ClientDepartmentProcess(String client) {
		super(client);
		try {
			dbManager = new DbManager(client, getLocale());
			factory = new DeptBeanFactory(dbManager);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public Collection<DeptBean> getDepartments(String companyId) throws BaseException, Exception {
		SearchCriteria criteria = new SearchCriteria("companyId", SearchCriterion.EQUALS, companyId);
		SortCriteria sort = new SortCriteria("deptName");
		return factory.select(criteria, sort);
	}

	public int insertDepartment(DeptBean bean) throws BaseException, Exception {
		return factory.insert(bean);
	}

	public int updateDepartment(DeptBean bean) throws BaseException, Exception {
		return factory.update(bean);
	}

}
