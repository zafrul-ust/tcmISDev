package com.tcmis.client.common.factory;


import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.common.beans.RowDataBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;


/******************************************************************************
 * CLASSNAME: RowDataBeanFactory <br>
 * @version: 1.0, Nov 14, 2007 <br>
 *****************************************************************************/


public class RowDataBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_ROW_DATA = "ROW_DATA";

	//table name
	public String TABLE = "ROW_DATA";


	//constructor
	public RowDataBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if(attributeName.equals("rowData")) {
			return ATTRIBUTE_ROW_DATA;
		}
		else {
			return super.getColumnName(attributeName);
		}
	}

	//select
	public Collection select(String dataKey, RowDataBean configDataBean) throws BaseException,Exception {
		Connection connection = this.getDbManager().getConnection();

		Collection cin = new Vector(1);
		cin.add(dataKey);

		Collection cout = new Vector(11);
		cout.add(new Integer(java.sql.Types.VARCHAR));
		cout.add(new Integer(java.sql.Types.VARCHAR));
		cout.add(new Integer(java.sql.Types.VARCHAR));
		cout.add(new Integer(java.sql.Types.VARCHAR));
		cout.add(new Integer(java.sql.Types.VARCHAR));
		cout.add(new Integer(java.sql.Types.VARCHAR));
		cout.add(new Integer(java.sql.Types.VARCHAR));
		cout.add(new Integer(java.sql.Types.VARCHAR));
		cout.add(new Integer(java.sql.Types.VARCHAR));
		cout.add(new Integer(java.sql.Types.VARCHAR));
		cout.add(new Integer(java.sql.Types.VARCHAR));
		Collection result = null;
		SqlManager sqlManager = new SqlManager();
		try {
			result = sqlManager.doProcedure(connection, "pkg_ftp_outbound.P_FTP_OUTBOUND", cin, cout);
		} finally {
		}

		Iterator iterator = result.iterator();
		String searchQuery = "";
		int count = 0;
		while (iterator.hasNext()) {
			if (count == 0) {
				configDataBean.setFileName((String)iterator.next());
			}else if (count == 1) {
				configDataBean.setFtpServer((String)iterator.next());
			}else if (count == 2) {
				configDataBean.setFtpUserName((String)iterator.next());
			}else if (count == 3) {
				configDataBean.setFtpPassword((String)iterator.next());
			}else if (count == 4) {
				configDataBean.setPath((String)iterator.next());
			}else if (count == 5) {
				configDataBean.setEncryptionRequired((String)iterator.next());
			}else if (count == 6) {
				configDataBean.setEncryptionMethod((String)iterator.next());
			}else if (count == 7) {
				configDataBean.setEncryptionPublicKey((String)iterator.next());
			}else if (count == 8) {
				configDataBean.setFtpType((String)iterator.next());
			}else if (count == 9) {
				configDataBean.setDosFormat((String)iterator.next());
			}else if (count == 10) {
				searchQuery = (String) iterator.next();
			}
			count++;
		}

		if (log.isDebugEnabled()) {

			log.debug("file:"+configDataBean.getFileName());
			log.debug(configDataBean.getFtpServer());
			log.debug(configDataBean.getFtpUserName());
			//log.debug(configDataBean.getFtpPassword());
			log.debug(configDataBean.getPath());
			log.debug(configDataBean.getEncryptionMethod());
			log.debug(configDataBean.getEncryptionPublicKey());
			log.debug(configDataBean.getEncryptionRequired());
			log.debug(configDataBean.getFtpType());
			log.debug(configDataBean.getDosFormat());
			log.debug(searchQuery);
		}

		Collection c = select(searchQuery, connection);

		this.getDbManager().returnConnection(connection);
		return c;
	}

	public Collection select(String query, Connection conn)
	throws BaseException {

		Collection rowDataBeanColl = new Vector();

		DataSet dataSet = new SqlManager().select(conn, query);
		Iterator dataIter = dataSet.iterator();
		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			RowDataBean bean = new RowDataBean();
			load(dataSetRow, bean);
			rowDataBeanColl.add(bean);
		}

		return rowDataBeanColl;
	}
}