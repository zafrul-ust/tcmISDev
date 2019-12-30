package com.tcmis.client.common.process;

import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.StringHandler;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.db.DbManager;
import com.tcmis.client.catalog.beans.MyWorkareaFacilityViewBean;
import com.tcmis.client.catalog.process.CatalogProcess;
import com.tcmis.client.common.beans.VvPersonalProtectionBean;
import com.tcmis.client.report.beans.ListBean;
import com.tcmis.client.report.beans.VvMaterialSubcategoryBean;
import com.tcmis.client.report.beans.FacilityCatalogBean;
import com.tcmis.common.admin.beans.*;
import com.tcmis.client.common.beans.MsdsViewerInputBean;
import com.tcmis.client.common.beans.MsdsSearchDataTblBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

/******************************************************************************
 * Process for MsdsViewerProcess
 * @version 1.0
 *****************************************************************************/
public class MsdsViewerProcess extends GenericProcess {

	Log log = LogFactory.getLog(this.getClass());

	public MsdsViewerProcess(String client,String locale)  {
		super(client,locale);
	}

    public String showFacilityUseCode(String facilityId) throws BaseException, Exception {
        String result = "N";
        if (!StringHandler.isBlankString(facilityId)) {
            DbManager dbManager = new DbManager(getClient(),getLocale());
	        GenericSqlFactory factory = new GenericSqlFactory(dbManager);
		    String query = "select use_code_required from facility where facility_id = '"+facilityId+"'";
            if ("Y".equals(factory.selectSingleValue(query.toString()))) {
                result = "Y";
            }
        }
        return result;
	}

    public Collection getFacilityWorkareaColl() throws BaseException, Exception {
	    factory.setBean(new MyWorkareaFacilityViewBean()); 
		String query = "select distinct company_id, facility_id, facility_name from FACILITY_WORKAREA_VIEW order by facility_name";
	    return factory.selectQuery(query);
	}
	
	public Collection getMyWorkareaFacilityViewBean(BigDecimal personnelId) throws BaseException, Exception {
		factory.setBean(new MyWorkareaFacilityViewBean());  //second argument is the bean that you are working with
		String query = "select distinct * from my_workarea_facility_view where personnel_id = "+personnelId.toString()+
			             " order by facility_name,application_desc";
	    return factory.selectQuery(query);
	}

	public Collection getPhysicalStateColl() throws BaseException {
		factory.setBean(new VvPhysicalStateBean());
		StringBuilder query = new StringBuilder("select physical_state, NVL(physical_state_desc, physical_state) physical_state_desc from vv_physical_state");
        query.append(" order by physical_state_desc");
        return factory.selectQuery(query.toString());
	}
	
	public Collection getPPColl() throws BaseException {
		factory.setBean(new VvPersonalProtectionBean());
		StringBuilder query = new StringBuilder("select personal_protect_code,personal_protect_desc from vv_personal_protection");
        query.append(" order by personal_protect_code");
        return factory.selectQuery(query.toString());
	}
	
	public Collection getListColl() throws BaseException {
		factory.setBean(new ListBean());
		StringBuilder query = new StringBuilder("select * from list");
        query.append(" order by list_name");
        return factory.selectQuery(query.toString());
	}
	
	public Collection getSpecificHazardColl() throws BaseException {
		factory.setBean(new VvSpecificHazardBean());
		StringBuilder query = new StringBuilder("select * from vv_specific_hazard");
        query.append(" order by specific_hazard_description");
        return factory.selectQuery(query.toString());
	}
	
	public Collection getVaporPressureUnitColl() throws BaseException {
		factory.setBean(new VvVaporPressureUnitBean());
		StringBuilder query = new StringBuilder("select * from vv_vapor_pressure_unit");
        query.append(" order by vapor_pressure_unit");
        return factory.selectQuery(query.toString());
	}
	
	public String allowMixture( String facilityId ) {
		String multiRow = null;
		try {
			if( "ALL".equals( facilityId ) || this.isBlank( facilityId ) )
				multiRow = factory.selectSingleValue("select count(*) from facility where allow_mixture = 'Y'");
			else 
				multiRow = factory.selectSingleValue("select count(*) from facility where allow_mixture = 'Y' and facility_id = "
						+this.getSqlString( facilityId ) +"");
		}catch(Exception ex){}
		
		if( this.isBlank(multiRow) || "0".equals(multiRow) )
			return "N";
		else 
			return "Y";
	}

    public Collection getSearchMsdsOnlyResults(MsdsViewerInputBean inputBean) throws BaseException {
		factory.setBean(new MsdsSearchDataTblBean());
        StringBuilder query = new StringBuilder("select * from table (pkg_msds_search.fx_search_material_catalog(");
        if (!StringHandler.isBlankString(inputBean.getCompanyId())) {
            query.append("'").append(inputBean.getCompanyId()).append("'");
        }else {
            query.append("''");
        }
        query.append(",").append(this.getSqlString(inputBean.getFacilityId()));
        if (!StringHandler.isBlankString(inputBean.getSimpleSearchText())) {
            query.append(",").append(this.getSqlString(inputBean.getSimpleSearchText()));
        }else {
            query.append(",''");
        }
        if("Y".equals(inputBean.getFullDatabase())) {
        	query.append(",'Y'");
        }else {
            query.append(",'N'");
        }
        //kit only
        query.append(",'N'");

        query.append("))");
        if ("catalogAddHmrb".equalsIgnoreCase(inputBean.getSourcePage()))
            query.append(" where customer_mixture_number is null");
        query.append(" order by customer_mixture_number,customer_msds_number,material_id");
        return factory.selectQuery(query.toString());
	}


    public Collection getSearchResults(MsdsViewerInputBean inputBean, PersonnelBean personnelBean) throws BaseException {
		factory.setBean(new MsdsSearchDataTblBean());
		if( "hide".equals( inputBean.getHideOrShowDiv() ) ) {
           
            StringBuilder query1 = new StringBuilder("select * from table (pkg_msds_search.fx_msds_simple_search_data(");
            query1.append(this.getSqlString(inputBean.getCompanyId())).append(",");
            query1.append(this.getSqlString(inputBean.getFacilityId())).append(",");
            query1.append(this.getSqlString(inputBean.getSimpleSearchText())).append("))");
            //order by customer_msds_db,material_id,customer_msds_number,customer_mixture_number,cat_part_no");

            return factory.selectQuery(query1.toString());    
		}

		StringBuilder query = new StringBuilder("select * from table (PKG_MSDS_SEARCH.FX_MSDS_SEARCH_DATA('',");
        query.append(personnelBean.getPersonnelId()).append(",'");
        query.append(StringHandler.emptyIfNull(inputBean.getFacilityId()));
		query.append("','");
		query.append(inputBean.getApplicationList());
		query.append("','");
		query.append(inputBean.getSearchField());
		query.append("','");
		query.append(StringHandler.emptyIfNull(inputBean.getSearchText()));
		query.append("','");
		query.append(StringHandler.emptyIfNull(inputBean.getMatchType()));
		query.append("','");
		query.append(StringHandler.emptyIfNull(inputBean.getMfgId()));
		query.append("','");
		query.append(StringHandler.emptyIfNull(inputBean.getPhysicalState()));
		query.append("','");
		query.append(StringHandler.emptyIfNull(inputBean.getPh()));
		query.append("','");
		query.append(StringHandler.emptyIfNull(inputBean.getPhCompare()));
		query.append("','");
		query.append(StringHandler.emptyIfNull(inputBean.getFlashPoint()));
		query.append("','");
		query.append(StringHandler.emptyIfNull(inputBean.getFlashPointCompare()));
		query.append("','");
		query.append(StringHandler.emptyIfNull(inputBean.getTemperatureUnit()));
		query.append("','");
		query.append(StringHandler.emptyIfNull(inputBean.getVaporPressure()));
		query.append("','");
		query.append(StringHandler.emptyIfNull(inputBean.getVaporPressureCompare()));
		query.append("','");
		query.append(StringHandler.emptyIfNull(inputBean.getVaporPressureUnit()));
		query.append("','");
        if(!StringHandler.isBlankString(inputBean.getVocSearchSelect()) && inputBean.getVocPercent() != null)
		{
            query.append(StringHandler.emptyIfNull(inputBean.getVocPercent()));
            query.append("','");
            query.append(StringHandler.emptyIfNull(inputBean.getVocPercentCompare()));
            query.append("','");
            query.append(StringHandler.emptyIfNull(inputBean.getVocSearchSelect()));
			query.append("','");
		}else{
			query.append("','','','");
		}
        query.append(StringHandler.emptyIfNull(inputBean.getSolidsPercent()));
		query.append("','");
		query.append(StringHandler.emptyIfNull(inputBean.getSolidsPercentCompare()));
		query.append("','");
		query.append(StringHandler.emptyIfNull(inputBean.getHealth()));
		query.append("','");
		query.append(StringHandler.emptyIfNull(inputBean.getHealthCompare()));
		query.append("','");
		query.append(StringHandler.emptyIfNull(inputBean.getFlammability()));
		query.append("','");
		query.append(StringHandler.emptyIfNull(inputBean.getFlammabilityCompare()));
		query.append("','");
		query.append(StringHandler.emptyIfNull(inputBean.getReactivity()));
		query.append("','");
		query.append(StringHandler.emptyIfNull(inputBean.getReactivityCompare()));
		query.append("','");
		query.append(StringHandler.emptyIfNull(inputBean.getSpecificHazard()));
		query.append("','");
		query.append(StringHandler.emptyIfNull(inputBean.getHmisHealth()));
		query.append("','");
		query.append(StringHandler.emptyIfNull(inputBean.getHmisHealthCompare()));
		query.append("','");
		query.append(StringHandler.emptyIfNull(inputBean.getHmisFlammability()));
		query.append("','");
		query.append(StringHandler.emptyIfNull(inputBean.getHmisFlammabilityCompare()));
		query.append("','");
		query.append(StringHandler.emptyIfNull(inputBean.getHmisReactivity()));
		query.append("','");
		query.append(StringHandler.emptyIfNull(inputBean.getHmisReactivityCompare()));
		query.append("','");
		query.append(StringHandler.emptyIfNull(inputBean.getPersonalProtection()));
		query.append("','");
		if(inputBean.getGridSubmit() != null && !StringHandler.isBlankString(inputBean.getGridSubmit()))
		{
			if(inputBean.getGridType().equalsIgnoreCase("list"))
			{
				query.append(StringHandler.emptyIfNull(inputBean.getGridSubmit()));
				query.append("','");
				query.append(StringHandler.emptyIfNull(inputBean.getCasNumberString()));
				query.append("','");
			}
			else
			{
				query.append(StringHandler.emptyIfNull(inputBean.getListString()));
				query.append("','");
				query.append(StringHandler.emptyIfNull(inputBean.getGridSubmit()));
				query.append("','");
			}
		}
		else
		{
			query.append(StringHandler.emptyIfNull(inputBean.getListString()));
			query.append("','");
			query.append(StringHandler.emptyIfNull(inputBean.getCasNumberString()));
			query.append("','");
		}
		query.append(StringHandler.emptyIfNull(inputBean.getCasNumberAndOrString()));
		query.append("',';','");
		query.append(inputBean.getFullDatabase() == null?"N":"Y");
		query.append("','");
		query.append("Y".equals(inputBean.getApprovedOnly())?"Y":"N");
		query.append("','");
		query.append("Y".equals(inputBean.getKitOnly())?"Y":"N");
		query.append("','");
		query.append("Y".equals(inputBean.getStocked())?"Y":"N");
        query.append("','");
        if(!StringHandler.isBlankString(inputBean.getVocLwesSearchSelect()) && inputBean.getVocLwesPercent() != null)
		{
			query.append(StringHandler.emptyIfNull(inputBean.getVocLwesPercent()));
			query.append("','");
			query.append(StringHandler.emptyIfNull(inputBean.getVocLwesPercentCompare()));
			query.append("','");
            query.append(StringHandler.emptyIfNull(inputBean.getVocLwesSearchSelect()));
            query.append("'");
		}else{
			query.append("','',''");
		}
        query.append("))");
        
		if ("Y".equals(inputBean.getKitOnly()))
			query.append(" order by customer_mixture_number,customer_msds_db,item_id,customer_msds_number,material_id");
		else
			query.append(" order by material_id,customer_msds_db,customer_msds_number,customer_mixture_number,cat_part_no");
	
        return factory.selectQuery(query.toString());
	}
	
	public void calculatingNumberOfKitsPerItem(Collection dataColl) {
		try {
			if (dataColl.size() > 1) {
				Object[] arrayObj = dataColl.toArray();
				int count = 1;
				int firstMatchedItemIndex = 0;
				//go ahead and get the first row and declare it as our current
				MsdsSearchDataTblBean firstBean = (MsdsSearchDataTblBean)arrayObj[0];

                String firstMsds = firstBean.getCustomerMixtureNumber()+"|"+firstBean.getItemId();
                if (StringHandler.isBlankString(firstMsds)) {
                    firstMsds = firstBean.getCustomerMixtureNumber()+"|"+firstBean.getItemId();
                }

                for (int i = 1; i < arrayObj.length; i++) {
					MsdsSearchDataTblBean bean = (MsdsSearchDataTblBean)arrayObj[i];
                    String currentMsds = bean.getCustomerMixtureNumber()+"|"+bean.getItemId();
                    if (StringHandler.isBlankString(currentMsds)) {
                        currentMsds = bean.getCustomerMixtureNumber()+"|"+bean.getItemId();
                    }

                    if (firstMsds != null) {
                        if (bean.getCustomerMixtureNumber() != null && bean.getCustomerMixtureNumber().length() > 0 && firstMsds.equals(currentMsds) ) {
                            count++;
                            //setting number of kits per item to -1 because it's kit
                            bean.setNumberOfKitPerMsds(new BigDecimal(-1));
                        }else {
                            MsdsSearchDataTblBean matchBean = (MsdsSearchDataTblBean)arrayObj[firstMatchedItemIndex];
                            matchBean.setNumberOfKitPerMsds(new BigDecimal(count));
                            //reset count
                            count = 1;
                            //reset first matched
                            firstMatchedItemIndex = i;
                        }
                    }else {
                        MsdsSearchDataTblBean matchBean = (MsdsSearchDataTblBean)arrayObj[firstMatchedItemIndex];
                        matchBean.setNumberOfKitPerMsds(new BigDecimal(count));
                        //reset count
                        count = 1;
                        //reset first matched
                        firstMatchedItemIndex = i;
                    }
                    firstMsds = currentMsds;
				}
				//taking care of the last record
				MsdsSearchDataTblBean lastBean = (MsdsSearchDataTblBean)arrayObj[arrayObj.length-1];
				//this the last record is not a kit
				if (lastBean.getNumberOfKitPerMsds() == null) {
					lastBean.setNumberOfKitPerMsds(new BigDecimal(1));
				}else {
					//otherwise it's a kit
					MsdsSearchDataTblBean matchBean = (MsdsSearchDataTblBean)arrayObj[firstMatchedItemIndex];
					matchBean.setNumberOfKitPerMsds(new BigDecimal(count));
				}
			}else {
				//since only one record then no need to merge cell
				Object[] arrayObj = dataColl.toArray();
				MsdsSearchDataTblBean firstBean = (MsdsSearchDataTblBean)arrayObj[0];
				firstBean.setNumberOfKitPerMsds(new BigDecimal(1));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void calculatingNumberOfKitsPerMsds(Collection dataColl) {
		try {
			if (dataColl.size() > 1) {
				Object[] arrayObj = dataColl.toArray();
				int count = 1;
				int firstMatchedItemIndex = 0;
				//go ahead and get the first row and declare it as our current
				MsdsSearchDataTblBean firstBean = (MsdsSearchDataTblBean)arrayObj[0];

                String firstMsds = firstBean.getCustomerMixtureNumber();
                if (StringHandler.isBlankString(firstMsds)) {
                    firstMsds = firstBean.getCustomerMixtureNumber();
                }
                
                String firstCustMsdsDb = firstBean.getCustomerMsdsDb();
                if (StringHandler.isBlankString(firstCustMsdsDb)) {
                	firstCustMsdsDb = firstBean.getCustomerMsdsDb();
                }

                for (int i = 1; i < arrayObj.length; i++) {
					MsdsSearchDataTblBean bean = (MsdsSearchDataTblBean)arrayObj[i];
                    String currentMsds = bean.getCustomerMixtureNumber();
                    if (StringHandler.isBlankString(currentMsds)) {
                        currentMsds = bean.getCustomerMixtureNumber();
                    }
                    
                    String currentCustMsdsDb = bean.getCustomerMsdsDb();
                    if (StringHandler.isBlankString(currentCustMsdsDb)) {
                    	currentCustMsdsDb = bean.getCustomerMsdsDb();
                    }

                    if (firstMsds != null && firstCustMsdsDb != null) {
                        if (firstMsds.equals(currentMsds) && firstCustMsdsDb.equals(currentCustMsdsDb)) {
                            count++;
                            //setting number of kits per item to -1 because it's kit
                            bean.setNumberOfKitPerMsds(new BigDecimal(-1));
                        }else {
                            MsdsSearchDataTblBean matchBean = (MsdsSearchDataTblBean)arrayObj[firstMatchedItemIndex];
                            matchBean.setNumberOfKitPerMsds(new BigDecimal(count));
                            //reset count
                            count = 1;
                            //reset first matched
                            firstMatchedItemIndex = i;
                        }
                    }else {
                        MsdsSearchDataTblBean matchBean = (MsdsSearchDataTblBean)arrayObj[firstMatchedItemIndex];
                        matchBean.setNumberOfKitPerMsds(new BigDecimal(count));
                        //reset count
                        count = 1;
                        //reset first matched
                        firstMatchedItemIndex = i;
                    }
                    firstMsds = currentMsds;
                    firstCustMsdsDb = currentCustMsdsDb;
				}
				//taking care of the last record
				MsdsSearchDataTblBean lastBean = (MsdsSearchDataTblBean)arrayObj[arrayObj.length-1];
				//this the last record is not a kit
				if (lastBean.getNumberOfKitPerMsds() == null) {
					lastBean.setNumberOfKitPerMsds(new BigDecimal(1));
				}else {
					//otherwise it's a kit
					MsdsSearchDataTblBean matchBean = (MsdsSearchDataTblBean)arrayObj[firstMatchedItemIndex];
					matchBean.setNumberOfKitPerMsds(new BigDecimal(count));
				}
			}else if (dataColl.size() == 1) {
				//since only one record then no need to merge cell
				Object[] arrayObj = dataColl.toArray();
				MsdsSearchDataTblBean firstBean = (MsdsSearchDataTblBean)arrayObj[0];
				firstBean.setNumberOfKitPerMsds(new BigDecimal(1));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}


    public ExcelHandler getExcelReport(MsdsViewerInputBean inputBean, Collection<MsdsSearchDataTblBean> data, Locale locale) throws
	    NoDataException, BaseException, Exception {

		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);
	
		pw.addTable(library.getString("label.data"));
	    //write column headers
		pw.addRow();

        String allowMixture = allowMixture(inputBean.getFacilityId());
        String showFacilityUseCode = showFacilityUseCode(inputBean.getFacilityId());
        CatalogProcess catalogProcess = new CatalogProcess(this.getClient(),this.getLocale());
        boolean showMsds = false;
        boolean showCASNumber = false;
        if (inputBean.getShowMsds() == null)
            showMsds =  "Y".equals(catalogProcess.isFeatureReleased("ALL","ShowKitMaterialMsds"));
        else
            showMsds =  "Y".equals(inputBean.getShowMsds());
        if (inputBean.getShowCASNumber() == null)
            showCASNumber = "Y".equals(catalogProcess.isFeatureReleased("ALL","ShowCASNumber"));
        else
            showCASNumber = "Y".equals(inputBean.getShowCASNumber());

        int baseCount = 8;
        if(showMsds)
            baseCount += 2;     //msds database and msds number

        if("Y".equals(allowMixture) && showMsds)
            baseCount += 3;     //kit msds and mix ratio and size unit

        if("Y".equals(showFacilityUseCode))
            baseCount += 1;     //part use code
        if("Y".equals(showFacilityUseCode) && showMsds)
            baseCount += 1;     //msds use code
        if("Y".equals(showFacilityUseCode) && "Y".equals(allowMixture) && showMsds)
            baseCount += 1;     //kit msds use code

        if(showCASNumber)
            baseCount += 1;     //cas number

        String[] headerkeys = new String[baseCount];
        int[] types = new int[baseCount];
        int[] widths = new int[baseCount];
        int[] horizAligns = new int[baseCount];
        int headerCount = 0;
        int typeCount = 0;
        int widthCount = 0;
        int horizAlignsCount = 0;
        if (showMsds) {
            headerkeys[headerCount++] = "label.msdsdatabase";
            types[typeCount++] = 0;
            widths[widthCount++] = 15;
            horizAligns[horizAlignsCount++] = 0;
        }
        headerkeys[headerCount++] = "label.materialid";
        types[typeCount++] = 0;
        widths[widthCount++] = 12;
        horizAligns[horizAlignsCount++] = 0;
        headerkeys[headerCount++] = "label.description";
        types[typeCount++] = 0;
        widths[widthCount++] = 40;
        horizAligns[horizAlignsCount++] = 0;
        if (showCASNumber) {
            headerkeys[headerCount++] = "label.casnumber";
            types[typeCount++] = 0;
            widths[widthCount++] = 15;
            horizAligns[horizAlignsCount++] = 0;
        }
        headerkeys[headerCount++] = "label.tradename";
        types[typeCount++] = 0;
        widths[widthCount++] = 40;
        horizAligns[horizAlignsCount++] = 0;
        headerkeys[headerCount++] = "label.manufacturer";
        types[typeCount++] = 0;
        widths[widthCount++] = 30;
        horizAligns[horizAlignsCount++] = 0;
        headerkeys[headerCount++] = "label.msdsurl";
        types[typeCount++] = 0;
        widths[widthCount++] = 28;
        horizAligns[horizAlignsCount++] = 0;
        if (showMsds) {
            headerkeys[headerCount++] = "label.msds";
            types[typeCount++] = 0;
            widths[widthCount++] = 12;
            horizAligns[horizAlignsCount++] = 0;
        }
        headerkeys[headerCount++] = "label.latestrevisiondate";
        types[typeCount++] = pw.TYPE_CALENDAR;
        widths[widthCount++] = 10;
        horizAligns[horizAlignsCount++] = 0;
        if("Y".equals(showFacilityUseCode) && showMsds) {
            headerkeys[headerCount++] = "label.msdsapprovalcode";
            types[typeCount++] = 0;
            widths[widthCount++] = 12;
            horizAligns[horizAlignsCount++] = 0;
        }
        if("Y".equals(allowMixture) && showMsds) {
            headerkeys[headerCount++] = "label.kitmsds";
            types[typeCount++] = 0;
            widths[widthCount++] = 10;
            horizAligns[horizAlignsCount++] = 0;
            headerkeys[headerCount++] = "label.kitmixratio";
            types[typeCount++] = 0;
            widths[widthCount++] = 7;
            horizAligns[horizAlignsCount++] = 0;
            headerkeys[headerCount++] = "label.kitmixratiouom";
            types[typeCount++] = 0;
            widths[widthCount++] = 12;
            horizAligns[horizAlignsCount++] = 0;
        }
        if("Y".equals(showFacilityUseCode) && "Y".equals(allowMixture) && showMsds) {
            headerkeys[headerCount++] = "label.kitapprovalcodes";
            types[typeCount++] = 0;
            widths[widthCount++] = 15;
            horizAligns[horizAlignsCount++] = 0;
        }
        headerkeys[headerCount++] = "label.item";
        types[typeCount++] = 0;
        widths[widthCount++] = 5;
        horizAligns[horizAlignsCount++] = 0;
        headerkeys[headerCount++] = "label.partno";
        types[typeCount++] = 0;
        widths[widthCount++] = 12;
        horizAligns[horizAlignsCount++] = 0;
        if("Y".equals(showFacilityUseCode)) {
            headerkeys[headerCount++] = "label.partapprovalcodes";
            types[typeCount++] = 0;
            widths[widthCount++] = 15;
            horizAligns[horizAlignsCount++] = 0;
        }
        pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
        //mix ratio - pw.setColumnDigit(10, 2);

        for (MsdsSearchDataTblBean member : data) {
            pw.addRow();
            if (showMsds)
                pw.addCell(member.getCustomerMsdsDb());
            pw.addCell(member.getMaterialId());
            pw.addCell(member.getMaterialDesc());
            if (showCASNumber)
                 pw.addCell(member.getCasNumberList());
            pw.addCell(member.getTradeName());
            pw.addCell(member.getMfgDesc());
            pw.addCell(member.getMsdsUrl());
            if (showMsds)
                pw.addCell(member.getCustomerMsdsNumber());
            pw.addCell(member.getLastMsdsRevisionDate());
            if ("Y".equals(showFacilityUseCode) && showMsds) {
                pw.addCell(member.getMsdsApprovalCode());
            }
            if("Y".equals(allowMixture) && showMsds) {
                pw.addCell(member.getCustomerMixtureNumber());
                pw.addCell(member.getMixRatioAmount());
                String tmpMixRatioUnit = member.getMixRatioSizeUnit();
                if ("%(v/v)".equals(tmpMixRatioUnit))
                    tmpMixRatioUnit = library.getString("report.label.percentByVolume");
                if ("%(w/w)".equals(tmpMixRatioUnit))
                    tmpMixRatioUnit = library.getString("report.label.percentByWeight");
                pw.addCell(tmpMixRatioUnit);
                if ("Y".equals(showFacilityUseCode)) {
                    pw.addCell(member.getKitApprovalCode());
                }
            }
            pw.addCell(member.getItemId());
            pw.addCell(member.getCatPartNo());
            if ("Y".equals(showFacilityUseCode)) {
                pw.addCell(member.getPartApprovalCode());
            }
        }
        this.writeReportHeader(pw,library,inputBean);
        return pw;
	}
    
    public ExcelHandler getKitExcelReport(MsdsViewerInputBean inputBean, Collection<MsdsSearchDataTblBean> data, PersonnelBean personnelBean, Locale locale, String module) throws
    NoDataException, BaseException, Exception {
	
	ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
	ExcelHandler pw = new ExcelHandler(library);

	pw.addTable(library.getString("label.data"));
//write column headers
	pw.addRow();
	
	int[] types = {0,0,0,0,0,0,0,0,0,0};
			
	int[] horizAligns = {0,0,0,0,0,0,0,0,0,0};

    String showFacilityUseCode = showFacilityUseCode(inputBean.getFacilityId());
    
    if("Y".equals(showFacilityUseCode)) {
            String[] headerkeys = {"label.msdsdatabase","label.kitmsds","label.kitdesc","label.approvalcode","label.msds","label.materialdesc","label.kitmixratio","label.kitmixratiouom","label.materialid","label.manufacturer","label.tradename"};
		    int[] widths = {12,12,20,12,8,40,15,15,8,20,30};
		    pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
    }else {
        	String[] headerkeys = {"label.msdsdatabase","label.kitmsds","label.kitdesc","label.msds","label.materialdesc","label.kitmixratio","label.kitmixratiouom","label.materialid","label.manufacturer","label.tradename"};
		    int[] widths = {12,12,20,8,40,15,15,8,20,30};
		    pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
    }
	
    if("Y".equals(showFacilityUseCode))
    	pw.setColumnDigit(6, 2);
    else
    	pw.setColumnDigit(5, 2);
    
	for (MsdsSearchDataTblBean member : data) {
	  
    pw.addRow();
    
    pw.addCell(member.getCustomerMsdsDb());
    pw.addCell(member.getCustomerMixtureNumber());
    pw.addCell(member.getMixtureDesc());
    
    if ("Y".equals(showFacilityUseCode)) 
    	pw.addCell(member.getKitApprovalCode());
    
    pw.addCell(member.getCustomerMsdsNumber());
    pw.addCell(member.getMaterialDesc());
    pw.addCell(member.getMixRatioAmount());
    String tmpMixRatioUnit = member.getMixRatioSizeUnit();
    if ("%(v/v)".equals(tmpMixRatioUnit))
        tmpMixRatioUnit = library.getString("report.label.percentByVolume");
    if ("%(w/w)".equals(tmpMixRatioUnit))
        tmpMixRatioUnit = library.getString("report.label.percentByWeight");
    pw.addCell(tmpMixRatioUnit);
    pw.addCell(member.getMaterialId());
    pw.addCell(member.getMfgDesc());
    pw.addCell(member.getTradeName());
  }

  this.writeReportHeader(pw,library,inputBean);
	
  return pw;
}
    
	private void writeReportHeader(ExcelHandler eh, ResourceLibrary library,MsdsViewerInputBean bean) throws BaseException,Exception {
		
		eh.addTable(library.getString("label.selectioncriteria"));
		eh.addRow();
		eh.addCell(library.getString("label.facility"));
		eh.addCell(bean.getFacilityName());
		eh.setColumnWidthNow(0,20);
		eh.setColumnWidthNow(1,20);
		eh.addRow();
		eh.addCell(library.getString("label.workarea"));
		String[] waDesc = bean.getApplicationDesc().split("&@#");
		for(int j = 0; j < waDesc.length; j++)
		{
			if(waDesc[j].length() > 0)
			{
				eh.addCell("");
				eh.addCell(waDesc[j]);
				eh.addRow();
			}
		}
		eh.addRow();
		eh.addCell(library.getString("label.approved"));
		eh.addCell(bean.getApprovedOnly());
		eh.addRow();
		eh.addCell(library.getString("label.stocked"));
		eh.addCell(bean.getStocked());
		eh.addRow();
		eh.addCell(library.getString("label.fulldatabase"));
		eh.addCell(bean.getFullDatabase());
		eh.addRow();
		eh.addCell(library.getString("label.kitmsds"));
		eh.addCell(bean.getKitOnly());
		eh.addRow();
		eh.addCell(library.getString("label.search"));
		eh.addCell(bean.getSearchFieldDesc() + " " + bean.getMatchTypeDesc() + " " + bean.getSearchText());
		eh.addRow();
		eh.addCell(library.getString("label.manufacturer"));
		eh.addCell(bean.getManufacturer());
		eh.addRow();
		eh.addCell(library.getString("label.physicalstate"));
		eh.addCell(bean.getPhysicalState());
		eh.addRow();
		eh.addCell(library.getString("label.ph"));
		eh.addCell(bean.getPh() == null ? "" :bean.getPhCompare() + " " + bean.getPh());
		eh.addRow();
		eh.addCell(library.getString("label.flashpoint"));
		eh.addCell(bean.getFlashPoint() == null ? "" :bean.getFlashPointCompare() + " " + bean.getFlashPoint() + " " + bean.getTemperatureUnit());
		eh.addRow();
		eh.addCell(library.getString("label.vaporpressure"));
		eh.addCell(bean.getVaporPressure() == null ? "" :bean.getVaporPressureCompare() + " " + bean.getVaporPressure() + " " + bean.getVaporPressureUnit());
		eh.addRow();
		eh.addCell(library.getString("label.voc"));
		eh.addCell(bean.getVocPercent() == null ? "" :bean.getVocPercentCompare() + " " + bean.getVocPercent() + " " + bean.getVocSearchSelect());
		eh.addRow();
		eh.addCell(library.getString("label.voclwes"));
		eh.addCell(bean.getVocLwesPercent() == null ? "" :bean.getVocLwesPercentCompare() + " " + bean.getVocLwesPercent() + " " + bean.getVocLwesSearchSelect());	
		eh.addRow();
		eh.addCell("%" + library.getString("label.solids"));		
		eh.addCell(bean.getSolidsPercent() == null ? "" :bean.getSolidsPercentCompare() + " " +bean.getSolidsPercent());	
		eh.addRow();
		String nfpa = library.getString("label.nfpa");
		eh.addCell(nfpa+ " " + library.getString("label.health"));		
		eh.addCell(bean.getHealth() == null ? "" :bean.getHealthCompare() + " " +bean.getHealth());
		eh.addRow();
		eh.addCell(nfpa+ " " + library.getString("label.flammability"));
		eh.addCell(bean.getFlammability() == null ? "" :bean.getFlammabilityCompare() + " " +bean.getFlammability());
		eh.addRow();
		eh.addCell(nfpa+ " " + library.getString("label.reactivity"));
		eh.addCell(bean.getReactivity() == null ? "" :bean.getReactivityCompare() + " " +bean.getReactivity());
		eh.addRow();
		eh.addCell(nfpa+ " " + library.getString("label.hazard"));
		eh.addCell(bean.getSpecificHazardDesc() == null ? "" :bean.getSpecificHazardDesc());
		eh.addRow();
		String hmis = library.getString("label.hmis");
		eh.addCell(hmis+ " " + library.getString("label.health"));
		eh.addCell(bean.getHmisHealth() == null ? "" :bean.getHmisHealthCompare() + " " +bean.getHmisHealth());
		eh.addRow();
		eh.addCell(hmis+ " " + library.getString("label.flammability"));
		eh.addCell(bean.getHmisFlammability() == null ? "" :bean.getHmisFlammabilityCompare() + " " +bean.getHmisFlammability());
		eh.addRow();
		eh.addCell(hmis+ " " + library.getString("label.reactivity"));
		eh.addCell(bean.getHmisReactivity() == null ? "" :bean.getHmisReactivityCompare() + " " +bean.getHmisReactivity());
		eh.addRow();
		eh.addCell(hmis+ " " + library.getString("label.personalProtection"));
		eh.addCell(bean.getPersonalProtectionDesc());
		eh.addRow();
		boolean isCas = bean.getGridType().equalsIgnoreCase("cas") ? true:false;
		if(isCas)
		{
			eh.addCell(library.getString("label.cas"));
			eh.addCell("");
			eh.addCell(library.getString("label.chemicalname"));
		}
		else
		{
			eh.addCell(library.getString("label.list"));
			eh.addCell("");
		}
		eh.addCell(library.getString("label.constraint"));
		eh.addCell(library.getString("label.operator"));
		eh.addCell( library.getString("label.value"));
		eh.addRow();
	
		String[] gridData = null;
		String[] gridRow = null;
		String[] gridRowVals = null;
		String[] gridRowDesc = null;
		
		
		if(bean.getGridSubmit() != null && !StringHandler.isBlankString(bean.getGridSubmit()))
		{
			gridData = bean.getGridSubmit().split(";");
			gridRowDesc = bean.getGridDesc().split("&@#");
	
			for(int i = 0; i < gridData.length; i++)
			{			
				eh.addCell("");
				int index = 0;
				if(isCas)
				{
					String[] casChem = gridRowDesc[i].split("#@&");
					eh.addCell(casChem[0]);
					eh.addCell(casChem[1]);	
					index = 1;
					gridRowVals = gridData[i].split(" ");

				}
				else
				{
					eh.addCell(gridRowDesc[i]);
					gridRow = gridData[i].split("\\|");
					if(gridRow.length > 1)
						gridRowVals = gridRow[1].split(" ");
				}
				if(gridRowVals != null && gridRowVals.length > 1)
				{

					String constraint = "";
					if(gridRowVals[index].equalsIgnoreCase("percent_upper"))
							constraint = "Max";
					else if(gridRowVals[index].equalsIgnoreCase("percent_lower"))
							constraint = "Min";
					else 
						constraint = "Average";
					index++;
					eh.addCell(constraint);
					if(gridRowVals[index+1].equalsIgnoreCase("Threshold") || gridRowVals[index+1].equalsIgnoreCase("null"))
					{
						eh.addCell(gridRowVals[index++] + " " + gridRowVals[index]);
						eh.addCell("");
						
					}
					else
					{
						eh.addCell(gridRowVals[index++]);
						eh.addCell(gridRowVals[index]);
					}
				}
				else
				{
					eh.addCell("");
					eh.addCell("");
					eh.addCell("");
				}
				gridRowVals = null;	
				eh.addRow();
			}
			
		}
	}

	public Collection getStorageLocations(String materialId, String facilityId, String application) throws BaseException {
		factory.setBean(new MyWorkareaFacilityViewBean());
		StringBuilder query = new StringBuilder("select * from table(pkg_msds_search.fx_mat_id_in_cabinet('', '").append(StringHandler.emptyIfNull(facilityId));
		query.append("', '").append(StringHandler.emptyIfNull(application)).append("', '");
		query.append(StringHandler.emptyIfNull(materialId)).append("'))");
        query.append(" order by application_desc");
        return factory.selectQuery(query.toString());
	}
	
	public Collection getVvVocUnit()  throws BaseException {
		factory.setBean(new VvVocUnitBean());
		String query = "select * from vv_voc_unit";
		return factory.selectQuery(query);
	}
	
	public Collection getFacilityCatalogColl()  throws BaseException {
		factory.setBean(new FacilityCatalogBean());
		String query = "select cf.facility_id,c.catalog_id,c.catalog_desc,c.catalog_company_id from catalog_facility cf, catalog c where cf.company_id = c.company_id and cf.catalog_company_id = c.catalog_company_id and cf.catalog_id = c.catalog_id and cf.display ='Y' order by cf.facility_id,c.catalog_desc";
		return factory.selectQuery(query);
	}
	
	public Collection getVvMaterialSubcategory()  throws BaseException {
		factory.setBean(new VvMaterialSubcategoryBean());
		String query = "select a.*,b.material_category_name from vv_material_subcategory a, vv_material_category b where a.company_id = b.company_id and a.catalog_company_id = b.catalog_company_id and a.catalog_id = b.catalog_id and a.material_category_id = b.material_category_id order by a.catalog_id,a.material_subcategory_name";
		return factory.selectQuery(query);
	}
	
} //end of class