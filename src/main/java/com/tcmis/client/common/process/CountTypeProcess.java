package com.tcmis.client.common.process;

import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.DropDownListBean;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.db.DbManager;
import com.tcmis.internal.hub.beans.CabinetBinInputBean;
import com.tcmis.client.common.beans.UserFacWagWaViewBean;
import com.tcmis.client.common.beans.WorkAreaCountBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collection;
import java.util.Iterator;

/******************************************************************************
 * Process for CountTypeProcess
 * @version 1.0
 *****************************************************************************/
public class CountTypeProcess extends BaseProcess {

	Log log = LogFactory.getLog(this.getClass());

	public CountTypeProcess(String client,String locale)  {
		super(client,locale);
	}

    //the purpose of this method is to return appopriate count_type for page
    public Object[] getCountType(String calledFrom) {
        DropDownListBean[] dropDownList;
        try {
            ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
            DbManager dbManager = new DbManager(getClient(),getLocale());
            GenericSqlFactory factory = new GenericSqlFactory(dbManager,new WorkAreaCountBean());
            
            StringBuilder queryFeature = new StringBuilder("Select count(*) from FEATURE_RELEASE where feature = 'ShowCountTypeLevel'");
            String showCountTypeLevel = factory.selectSingleValue(queryFeature.toString());
            Integer count = new Integer(showCountTypeLevel);
            
            StringBuilder query = new StringBuilder("select * from vv_work_area_count_type");
            if ("CabinetBinAction".equals(calledFrom)) {
                query.append(" where count_type in ('ITEM_ID','RECEIPT_ID')");
            }
            query.append(" order by count_type");
            Collection dataC = factory.selectQuery(query.toString());
            int dropdownSize = 0;
            if (count != null && count.intValue() > 0){
            	dropdownSize = dataC.size();
            } else {
            	dropdownSize = dataC.size()-1;
            }            
            dropDownList = new DropDownListBean[dropdownSize];
            int i = 0;
            Iterator iter = dataC.iterator();
            while (iter.hasNext()) {
                WorkAreaCountBean bean = (WorkAreaCountBean)iter.next();
                if (!bean.getCountType().equalsIgnoreCase("LEVEL")) {                	
                	dropDownList[i++] = new DropDownListBean(library.getString(bean.getJspLabel()),bean.getCountType());                	
                	dropDownList[i-1].setCabinetStartPredateBlock(bean.getCabinetStartPredateBlock());
                } else if (bean.getCountType().equalsIgnoreCase("LEVEL") && count != null && count.intValue() > 0) {
                    dropDownList[i++] = new DropDownListBean(library.getString(bean.getJspLabel()),bean.getCountType());	
                    dropDownList[i-1].setCabinetStartPredateBlock(bean.getCabinetStartPredateBlock());
                }
                
            }
        }catch (Exception e) {
            dropDownList = new DropDownListBean[0];
        }
        return dropDownList;
    } //end of method

    public String getFacilityDefaultCountType(CabinetBinInputBean inputBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager);
		StringBuilder query = new StringBuilder("select default_count_type from facility where company_id = ").append(SqlHandler.delimitString(inputBean.getCompanyId()));
		query.append(" and facility_id = ").append(SqlHandler.delimitString(inputBean.getFacilityId()));
		return factory.selectSingleValue(query.toString());
	} //end of method

} //end of class