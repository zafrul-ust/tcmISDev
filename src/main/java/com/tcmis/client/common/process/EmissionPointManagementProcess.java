package com.tcmis.client.common.process;

import java.util.Collection;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.common.beans.EmissionPointManagementBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.util.ResourceLibrary;

import radian.tcmis.common.util.SqlHandler;

/*******************************************************************
 * 
 * @version 1.0
 * 
 * 
 ********************************************************************/

public class EmissionPointManagementProcess extends GenericProcess {
	Log log = LogFactory.getLog(this.getClass());

	public EmissionPointManagementProcess(String client, Locale locale) {
		super(client, locale);
	}
	
	@SuppressWarnings("unchecked")
	public Vector<EmissionPointManagementBean> getPageSearchResult(EmissionPointManagementBean inputBean) {
		try {
			StringBuilder query = new StringBuilder("select * from fac_app_emission_point");
			query.append(" where facility_id = ").append(SqlHandler.delimitString(inputBean.getFacilityId()));
			query.append(" and application = ").append(SqlHandler.delimitString(inputBean.getApplication()));
			query.append(" order by fac_emission_point, app_emission_point, app_emission_point_desc");
			
			factory.setBean(new EmissionPointManagementBean());
			return (Vector<EmissionPointManagementBean>) factory.selectQuery(query.toString());
		} catch (BaseException e) {
			log.error(e);
			return null;
		}
	}
	
	public String updateFacAppEmissionPoint(EmissionPointManagementBean inputBean, Collection<EmissionPointManagementBean> beanColl) {
		try {
			StringBuilder query = null;
			for (EmissionPointManagementBean bean : beanColl) {
				if (Boolean.valueOf(bean.getIsNew())) {
					query = new StringBuilder("insert into fac_app_emission_point (active, application, app_emission_point, app_emission_point_desc, facility_id, fac_emission_point) values (");
					query.append(SqlHandler.delimitString(bean.getActive()));
					query.append(",").append(SqlHandler.delimitString(inputBean.getApplication()));
					query.append(",").append(SqlHandler.delimitString(bean.getAppEmissionPoint()));
					query.append(",").append(SqlHandler.delimitString(bean.getAppEmissionPointDesc()));
					query.append(",").append(SqlHandler.delimitString(inputBean.getFacilityId()));
					query.append(",").append(SqlHandler.delimitString(bean.getFacEmissionPoint())).append(")");
				} else {
					query = new StringBuilder("update fac_app_emission_point set");
					query.append(" active = ").append(SqlHandler.delimitString(bean.getActive()));
					query.append(", app_emission_point_desc = ").append(SqlHandler.delimitString(bean.getAppEmissionPointDesc()));
					query.append(" where facility_id = ").append(SqlHandler.delimitString(inputBean.getFacilityId()));
					query.append(" and application = ").append(SqlHandler.delimitString(inputBean.getApplication()));
					query.append(" and app_emission_point = ").append(SqlHandler.delimitString(bean.getAppEmissionPoint()));
					query.append(" and fac_emission_point = ").append(SqlHandler.delimitString(bean.getFacEmissionPoint()));
				}
				
				factory.deleteInsertUpdate(query.toString());
			}
		} catch (BaseException e) {
			log.error(e);
			return new ResourceLibrary("com.tcmis.common.resources.CommonResources", getLocaleObject()).getString("error.db.select");
		}
		
		return "";
	}
}
