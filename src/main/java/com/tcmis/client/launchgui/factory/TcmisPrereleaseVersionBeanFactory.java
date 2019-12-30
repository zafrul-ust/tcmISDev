package com.tcmis.client.launchgui.factory;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.reflect.Method;

import java.sql.Connection;

import java.text.ParseException;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DataBeanCreationException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.util.*;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.client.launchgui.beans.TcmisPrereleaseVersionBean;


/******************************************************************************
 * CLASSNAME: TcmisPrereleaseVersionBeanFactory <br>
 * @version: 1.0, Apr 2, 2007 <br>
 *****************************************************************************/


public class TcmisPrereleaseVersionBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_IP_ADDRESS = "IP_ADDRESS";
	public String ATTRIBUTE_USERNAME = "USERNAME";
	public String ATTRIBUTE_VERSION = "VERSION";
	public String ATTRIBUTE_RESTART = "RESTART";
	public String ATTRIBUTE_HOST = "HOST";
	public String ATTRIBUTE_PATH = "PATH";
        public String ATTRIBUTE_JAR_FILENAME = "JAR_FILENAME";
        public String ATTRIBUTE_CLASS_PATH = "CLASS_PATH";
        public String ATTRIBUTE_SOURCE_DATABASE_PROFILE = "SOURCE_DATABASE_PROFILE";

	//table name
	public String TABLE = "TCMIS_PRERELEASE_VERSION";


	//constructor
	public TcmisPrereleaseVersionBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("ipAddress")) {
			return ATTRIBUTE_IP_ADDRESS;
		}
		else if(attributeName.equals("username")) {
			return ATTRIBUTE_USERNAME;
		}
		else if(attributeName.equals("version")) {
			return ATTRIBUTE_VERSION;
		}
		else if(attributeName.equals("restart")) {
			return ATTRIBUTE_RESTART;
		}
		else if(attributeName.equals("host")) {
			return ATTRIBUTE_HOST;
		}
		else if(attributeName.equals("path")) {
			return ATTRIBUTE_PATH;
		}
                else if(attributeName.equals("jarFilename")) {
                        return ATTRIBUTE_JAR_FILENAME;
                }
                else if(attributeName.equals("classPath")) {
                  return ATTRIBUTE_CLASS_PATH;
                }
                else if(attributeName.equals("sourceDatabaseProfile")) {
                  return ATTRIBUTE_SOURCE_DATABASE_PROFILE;
                }
                else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, TcmisPrereleaseVersionBean.class);
	}


	//select
	public Collection select(SearchCriteria criteria)
		throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = select(criteria, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
	public Collection select(SearchCriteria criteria, Connection conn)
		throws BaseException {

		Collection tcmisPrereleaseVersionBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			TcmisPrereleaseVersionBean tcmisPrereleaseVersionBean = new TcmisPrereleaseVersionBean();
			load(dataSetRow, tcmisPrereleaseVersionBean);
			tcmisPrereleaseVersionBeanColl.add(tcmisPrereleaseVersionBean);
		}

		return tcmisPrereleaseVersionBeanColl;
	}
}