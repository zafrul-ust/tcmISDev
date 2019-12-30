package com.tcmis.client.common.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.common.beans.CountInterpolationBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ResourceLibrary;

public class ClientCabinetCountInterpolationProcess extends GenericProcess {
	
	Log log = LogFactory.getLog(this.getClass());
	ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", getLocaleObject());
	public ClientCabinetCountInterpolationProcess(String client, Locale locale) {
		super(client, locale);
		// TODO Auto-generated constructor stub
	}
	
	public ClientCabinetCountInterpolationProcess(String client) {
		super(client);
	}

	public HashMap<String, Object> upsertInterpolationCount(Collection<CountInterpolationBean> countInterpolationBeanCol, PersonnelBean pbean) throws BaseException,Exception {
		DbManager dbManager = new DbManager(getClient(),locale.toString());
	    Connection connection = dbManager.getConnection();
	    HashMap<String, Object> returnMessage = new HashMap<String, Object>();
		StringBuilder query = new StringBuilder();
		try {
			for (CountInterpolationBean countInterpolationBean : countInterpolationBeanCol) {
				query = new StringBuilder();
				if (countInterpolationBean.isNewCountInterpolation()) {
					query.append("Insert into cabinet_count_interpolation(BIN_ID, COMPANY_ID, COUNT_QUANTITY, INVENTORY_QUANTITY) ");
					query.append("values (").append(countInterpolationBean.getBinId()).append(",'").append(pbean.getCompanyId()).append( "'," );
					query.append( countInterpolationBean.getCountQuantity() ).append( "," ).append( countInterpolationBean.getInventoryQuantity() ).append( ")");
					factory.deleteInsertUpdate(query.toString(),connection);
				} else if( countInterpolationBean.getUpdated().equalsIgnoreCase("Y")) {
					if ( countInterpolationBean.isDeleteCountInterpolation()) {
						query.append(" delete cabinet_count_interpolation ");
						query.append(" where COUNT_QUANTITY = " ).append( countInterpolationBean.getOldCountQuantity() );
						query.append(" and BIN_ID = " ).append( countInterpolationBean.getBinId());
						query.append(" and COMPANY_ID = '" ).append( pbean.getCompanyId() ).append( "'");					
						factory.deleteInsertUpdate(query.toString(),connection);	
					} else {
						query.append(" Update cabinet_count_interpolation ");
						query.append(" set COUNT_QUANTITY = " ).append( countInterpolationBean.getCountQuantity() ).append( ", " );
						query.append(" INVENTORY_QUANTITY = " ).append( countInterpolationBean.getInventoryQuantity());					
						query.append(" where BIN_ID = " ).append( countInterpolationBean.getBinId());
						query.append(" and COMPANY_ID = '" ).append( pbean.getCompanyId() ).append( "'");
						query.append(" and COUNT_QUANTITY = '" ).append( countInterpolationBean.getOldCountQuantity() ).append( "'");
						factory.deleteInsertUpdate(query.toString(),connection);
					}
				}
			}
		}
		catch (Exception e) {
			log.error("Error updating/inserting data:" + e.getMessage());	
			returnMessage.put("tcmISError",library.getString("error.updateerror"));
		} finally {
			dbManager.returnConnection(connection);
		}
		return returnMessage;
	}
   
    public Collection<CountInterpolationBean> getCountInterpolation(CountInterpolationBean inputBean) throws BaseException {
    	Collection<CountInterpolationBean> colInterpolation = new Vector(0);;
		Connection connection = null;
		DbManager dbManager = new DbManager(getClient(), this.getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager);
		try {
			connection = dbManager.getConnection();
			StringBuilder query = new StringBuilder("select * from cabinet_count_interpolation where ");
			query.append(" bin_id = ").append(inputBean.getBinId());
			factory.setBean(new CountInterpolationBean());
			colInterpolation = factory.selectQuery(query.toString(), connection);
		}
		finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
		}
		return colInterpolation;
    }
	
    public Collection<CountInterpolationBean> getCountInterpolation(String binId) throws BaseException {
    	CountInterpolationBean bean = new CountInterpolationBean();
    	bean.setBinId(new BigDecimal(binId));
		return getCountInterpolation(bean);
    }
    
}
