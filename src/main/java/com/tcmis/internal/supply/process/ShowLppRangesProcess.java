package com.tcmis.internal.supply.process;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import oracle.jdbc.OracleTypes;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.internal.supply.beans.LppRangeBean;
import com.tcmis.internal.supply.beans.POItemBean;

/******************************************************************************
* Process used by ShowScheduleTcmBuysAction
* @version 1.0
*****************************************************************************/

public class ShowLppRangesProcess  extends BaseProcess 
{
	Log log = LogFactory.getLog(this.getClass());
	
	public ShowLppRangesProcess(String client) 
	{
		super(client);
	}  

	public Collection<LppRangeBean> getLPPRangesCollection (LppRangeBean inputBean) throws BaseException, Exception 
	{
		String resultQuery = null;
		Connection conn = null;
		CallableStatement cs = null;
		DbManager dbManager = new DbManager(this.getClient());
		conn = dbManager.getConnection();
		GenericSqlFactory genericSqlFactory;
		Collection<LppRangeBean> lppRangeBeansCol = null;
		try 
		{
			String query0 =  "{call pkg_po.pr_bo_baseline_query(?,?,?,?,?,?,?,?,?)}" ;
			cs = conn.prepareCall(query0);
			
			cs.setString( 1,inputBean.getCompanyId() );
			if ( inputBean.getMrNumber() != null) {
				cs.setBigDecimal( 2, inputBean.getMrNumber());
			} else {
				cs.setNull( 2, OracleTypes.INTEGER );
			}
			cs.setString( 3, inputBean.getMrLineItem() );
			if (inputBean.getItemId() != null) {
				cs.setInt( 4, inputBean.getItemId().intValue() );
			} else {
				cs.setNull( 4,OracleTypes.INTEGER );
			}
			cs.setString( 5,inputBean.getInventoryGroup() );
			cs.setString( 6,inputBean.getCurrency() );
			cs.setString( 7,inputBean.getOpsEntityId() );
			cs.registerOutParameter(8,OracleTypes.CURSOR);
			cs.registerOutParameter(9,OracleTypes.VARCHAR);
			
			cs.execute();
			resultQuery = (String)cs.getObject(9); 
			log.debug(resultQuery);
			
			if (resultQuery != null) {
				try {
					genericSqlFactory = new GenericSqlFactory(dbManager,new LppRangeBean());
					lppRangeBeansCol = genericSqlFactory.selectQuery(resultQuery, conn);
					return lppRangeBeansCol;
				}
				catch (BaseException e) {
					log.error("Exception getting data for associated Prs: " + e);
				}
			}	
		}
		catch (Exception sqle) {
			log.error("SQL Exception in getFlowdownSQL: " + sqle);
		}
		finally {
			dbManager.returnConnection(conn);
		}
		return lppRangeBeansCol;		
	
	}
	
}
