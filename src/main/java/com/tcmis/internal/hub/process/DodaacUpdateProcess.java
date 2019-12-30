package com.tcmis.internal.hub.process;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.DodaacLocationBean;
import com.tcmis.internal.hub.beans.DpmsAddressInputBean;
import com.tcmis.internal.hub.beans.LocationSearchViewBean;
import com.tcmis.internal.hub.beans.VvDodaacTypeBean;
import com.tcmis.internal.hub.factory.DodaacLocationBeanFactory;
import com.tcmis.common.framework.GenericSqlFactory;
//import com.tcmis.internal.hub.factory.LocationSearchViewBeanFactory;
import com.tcmis.internal.hub.factory.VvDodaacTypeBeanFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

public class DodaacUpdateProcess
        extends BaseProcess {
    Log log = LogFactory.getLog(this.getClass());

    public DodaacUpdateProcess(String client) {
        super(client);
    }
    
    public DodaacUpdateProcess(String client,String locale) {
	    super(client,locale);
    }
    
    public Collection<LocationSearchViewBean> getAddressList(DpmsAddressInputBean bean) throws BaseException {

      	DbManager dbManager = new DbManager(getClient(),getLocale());
      	GenericSqlFactory factory = new GenericSqlFactory(dbManager,new LocationSearchViewBean());
      	
      	SearchCriteria criteria = new SearchCriteria();
      	
      	if (!StringHandler.isBlankString(bean.getDodaacType())) {
    	  criteria.addCriterion("dodaacType", SearchCriterion.EQUALS, bean.getDodaacType());
      	}
    	if (!StringHandler.isBlankString(bean.getDodaac())) {
          criteria.addCriterion("dodaac", SearchCriterion.EQUALS,  bean.getDodaac(), SearchCriterion.IGNORE_CASE);
    	}
    	if (!StringHandler.isBlankString(bean.getSearchText())) { 
          criteria.addCriterion("search", SearchCriterion.LIKE, bean.getSearchText(), SearchCriterion.IGNORE_CASE);
    	}
      	SortCriteria sort = new SortCriteria("city");
      	return factory.select(criteria,sort, "DODAAC_UPDATE_SEARCH_VIEW");
    }

    public Collection<VvDodaacTypeBean> getLocationTypeList() throws
            BaseException {
        DbManager dbManager = new DbManager(getClient(),getLocale());
        VvDodaacTypeBeanFactory factory = new VvDodaacTypeBeanFactory(dbManager);
        SearchCriteria searchCriteria = new SearchCriteria();
        SortCriteria sortCriteria = new SortCriteria("dodaacType");
        return factory.select(searchCriteria, sortCriteria);

    }

    public void updateLocation(Collection<LocationSearchViewBean> beans) throws BaseException {
        DodaacLocationBean outputbean = new DodaacLocationBean();
        DbManager dbManager = new DbManager(this.getClient(),getLocale());
        DodaacLocationBeanFactory factory = new DodaacLocationBeanFactory(dbManager);

        for (LocationSearchViewBean bean : beans) {
            if (bean.getOk() != null && bean.getDodaacType() != null && !bean.getDodaacType().equalsIgnoreCase(bean.getOldDodaacType())) {
                outputbean.setLocationId(bean.getLocationId());
                outputbean.setDodaacType(bean.getDodaacType());
                factory.update(outputbean);
                factory.updateLocation(bean.getLocationId(),bean.getCountryAbbrev(),bean.getStateAbbrev());
            }
        }

        factory = null;
        dbManager = null;
    }

    public ExcelHandler getExcelReport(DpmsAddressInputBean bean, Locale locale) throws
            BaseException, Exception {
        ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
        Collection data = getAddressList(bean);
        Iterator iterator = data.iterator();
        ExcelHandler pw = new ExcelHandler(library);
        pw.addTable();
        pw.addRow();
        pw.addCellKeyBold("label.dodaactype");
        if(bean.getDodaacType().equals(""))
            pw.addCellKeyBold("label.all") ;
        else
            pw.addCell(bean.getDodaacType());
        pw.addRow();
        pw.addCellKeyBold("label.dodaac");
        pw.addCell(bean.getDodaac());
        pw.addRow();
        pw.addCellKeyBold("label.searchtext");
        pw.addCell(bean.getSearchText());
        pw.addRow();

        String[] headerkeys = { "label.dodaactype","label.dodaac","label.location","label.address","label.city" };
        int[] widths = {14,12,0,0,22} ;

        int [] types = {
                0,0,0, ExcelHandler.TYPE_PARAGRAPH,0};
        int[] aligns = {0,0,0,0,0};
        pw.applyColumnHeader(headerkeys, types, widths, aligns);
        while (iterator.hasNext()) {
            LocationSearchViewBean recBean = (LocationSearchViewBean) iterator.next();
            pw.addRow();
            pw.addCell(recBean.getDodaacType());
            pw.addCell(recBean.getDodaac());
            pw.addCell(recBean.getLocationId());
            StringBuilder address = new StringBuilder(recBean.getAddressLine1Display());
            address.append(" ");
            address.append(recBean.getAddressLine2Display());
            address.append(" ");
            address.append(recBean.getAddressLine3Display());
            address.append(" ");
            address.append(recBean.getAddressLine4Display());
            pw.addCell(address.toString());
         pw.addCell(recBean.getCity());
        }
        return pw;
    }
}