package com.tcmis.client.common.process;

import java.io.File;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tcmis.client.common.beans.MaterialCompositionViewBean;
import com.tcmis.client.common.beans.MaterialShipDeclarationViewBean;
import com.tcmis.client.common.beans.MsdsLocaleViewBean;
import com.tcmis.client.common.beans.MsdsReportListViewBean;
import com.tcmis.client.common.beans.MsdsViewerListViewBean;
import com.tcmis.client.common.beans.ViewMsdsInputBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.FileHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * Process for MsdsViewerProcess
 * @version 1.0
 *****************************************************************************/
public class ViewMsdsProcess extends BaseProcess {

	Log log = LogFactory.getLog(this.getClass());
	String browserLocaleCode = "";
	ResourceLibrary resource = new ResourceLibrary("tcmISWebResource");

    public ViewMsdsProcess(String client,String locale)  {
		super(client,locale);
	}

    public void calculateLatestRevisionDateAndLocale(ViewMsdsInputBean inputBean) throws Exception {
        try {

            //first get alternate locale code
            String tmpLocaleCode = "";
            String alternateLocaleCode = "";
            String browserAlternateLocaleCode = "";
            if (!StringHandler.isBlankString(inputBean.getLocaleCode())) {
                alternateLocaleCode = getAlternateMsdsLocale(inputBean.getLocaleCode());
                tmpLocaleCode = inputBean.getLocaleCode();
            }
            if (!StringHandler.isBlankString(browserLocaleCode)) {
                browserAlternateLocaleCode = getAlternateMsdsLocale(browserLocaleCode);
            }
            //the reason I use revision_date_format is because we need to keep revision_date alone for sorting to work correctly
            DbManager dbManager = new DbManager(getClient(), getLocale());
            GenericSqlFactory factory = new GenericSqlFactory(dbManager,new ViewMsdsInputBean());
            StringBuilder query = new StringBuilder("select to_char(revision_date,'dd/Mon/yyyy') revision_date_format, concat(to_char(revision_date, 'yyyy-mm-dd HH24:MI:SS'),'.0') revision_date_time_stamp, locale_code");
            query.append(" from msds_locale_view where material_id = ").append(inputBean.getMaterialId()).append(" and on_line = 'Y'");
            query.append(" order by revision_date desc, locale_code");
            Iterator iter = factory.selectQuery(query.toString()).iterator();
            boolean firstTime = true;
            boolean foundAlternateData = false;
            while (iter.hasNext()) {
                ViewMsdsInputBean bean = (ViewMsdsInputBean)iter.next();
                if (!StringHandler.isBlankString(inputBean.getFacility())) {
                    //if locale code is the same as the passed in locale code then we got the latest data
                    //the reason that I am setting revision_date to revision_date_display is because of sorting purpose
                    if (bean.getLocaleCode().equals(tmpLocaleCode)) {
                        inputBean.setLocaleCode(bean.getLocaleCode());
                        inputBean.setRevisionDate(bean.getRevisionDateFormat());
                        inputBean.setRevisionDateTimeStamp(bean.getRevisionDateTimeStamp());
                        break;
                    }
                    //default to the first record matches the alternate locale code
                    if (bean.getLocaleCode().equals(alternateLocaleCode) && !foundAlternateData) {
                        inputBean.setLocaleCode(bean.getLocaleCode());
                        inputBean.setRevisionDate(bean.getRevisionDateFormat());
                        inputBean.setRevisionDateTimeStamp(bean.getRevisionDateTimeStamp());
                        foundAlternateData = true;
                    }
                    //default latest data to the first record found
                    if (firstTime && !foundAlternateData) {
                        inputBean.setLocaleCode(bean.getLocaleCode());
                        inputBean.setRevisionDate(bean.getRevisionDateFormat());
                        inputBean.setRevisionDateTimeStamp(bean.getRevisionDateTimeStamp());
                        firstTime = false;
                    }
                }else {
                    //if locale code is the same as browser locale then we got the latest data
                    if (bean.getLocaleCode().equals(browserLocaleCode)) {
                        inputBean.setLocaleCode(bean.getLocaleCode());
                        inputBean.setRevisionDate(bean.getRevisionDateFormat());
                        inputBean.setRevisionDateTimeStamp(bean.getRevisionDateTimeStamp());
                        break;
                    }
                    //default to the first record matches the alternate locale code
                    if (bean.getLocaleCode().equals(browserAlternateLocaleCode) && !foundAlternateData) {
                        inputBean.setLocaleCode(bean.getLocaleCode());
                        inputBean.setRevisionDate(bean.getRevisionDateFormat());
                        inputBean.setRevisionDateTimeStamp(bean.getRevisionDateTimeStamp());
                        foundAlternateData = true;
                    }
                    //default latest data to the first record found
                    if (firstTime && !foundAlternateData) {
                        inputBean.setLocaleCode(bean.getLocaleCode());
                        inputBean.setRevisionDate(bean.getRevisionDateFormat());
                        inputBean.setRevisionDateTimeStamp(bean.getRevisionDateTimeStamp());
                        firstTime = false;
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    } //end of method

    public String getLocaleCode(HttpSession session, HttpServletRequest request, String facilityId) {
		  String localeCode = "";
	        try
	        {
	            PersonnelBean personnelBean = (PersonnelBean) session.getAttribute("personnelBean");
	            if (personnelBean != null)
	            {
	            	browserLocaleCode = (String) session.getAttribute("langSetting");
	            	localeCode = (String) session.getAttribute("langSetting");
	            }else {
                    String tmpBrowserLocaleCode = request.getHeader("Accept-Language");
                    //because browser locale has slightly different format (xx-yy i.e en_us) I'm going to convert it to xx_YY i.e en_US
                    //here are other examples:
                    //browser locale:en-us
                    //browser locale:zh-tw,en-us;q=0.7,en;q=0.3
                    //browser locale:en-gb
                    if (tmpBrowserLocaleCode != null) {
                        tmpBrowserLocaleCode = tmpBrowserLocaleCode.substring(0,5);
                        String[] tmpBl = tmpBrowserLocaleCode.split("-");
                        if (tmpBl.length == 2) {
                            tmpBrowserLocaleCode = tmpBl[0]+"_"+tmpBl[1].toUpperCase();
                        }else {
                            tmpBrowserLocaleCode = "en_US";
                        }
                    }else {
                        tmpBrowserLocaleCode = "en_US";
                    }
                    browserLocaleCode = tmpBrowserLocaleCode;
                }

                if(facilityId != null && facilityId.length() > 0) {
		        	// Get locale from facility
		        	String defaultLocale = getDefaultLocaleForFacility(facilityId);
		        	if(defaultLocale != null && defaultLocale.length() > 0)
		        		localeCode = defaultLocale;
		        }
	        } catch (Exception ex) {
	        	ex.printStackTrace();
                browserLocaleCode = "en_US";
            }
	        if(localeCode == null || localeCode.length() == 0)
	    		localeCode = "en_US";
	        return localeCode;
	 }

	 private String getDefaultLocaleForFacility(String facilityId) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager);
		StringBuilder query = new StringBuilder("select locale_code from facility_locale f where facility_id = '");
		query.append(facilityId).append("'");
		query.append(" order by display_order, locale_code");
		return factory.selectSingleValue(query.toString());
	 }

	 public String getAlternateMsdsLocale(String tmpLocaleCode) throws BaseException {
		 DbManager dbManager = new DbManager(getClient(), getLocale());
		 GenericSqlFactory factory = new GenericSqlFactory(dbManager);
		 StringBuilder query = new StringBuilder("select a.alternate_msds_locale from vv_locale a where a.locale_code = '").append(tmpLocaleCode).append("'");
		 return factory.selectSingleValue(query.toString());
	 }

     public Collection getTitleInfo(ViewMsdsInputBean inputBean) throws Exception {
        DbManager dbManager = new DbManager(getClient(), getLocale());
        GenericSqlFactory factory = new GenericSqlFactory(dbManager,new MsdsLocaleViewBean());
        StringBuilder query = new StringBuilder("select nvl(content,' ') content,nvl(trade_name,' ') trade_name, revision_date, nvl(locale_display,' ') locale_display,nvl(mfg_desc,' ') mfg_desc");
        query.append(" from msds_locale_view m where material_id = ").append(inputBean.getMaterialId()).append(" and on_line = 'Y'");
        query.append(" and trunc(revision_date) = '").append(inputBean.getRevisionDate()).append("'");
        query.append(" and locale_code = '").append(inputBean.getLocaleCode()).append("'");
        //	add order by
		query.append(" order by revision_date desc, locale_code");
		return factory.selectQuery(query.toString());
    } //end of method

	 public String getNotice(ViewMsdsInputBean inputBean) throws Exception, BaseException {
		 DbManager dbManager = new DbManager(getClient(), getLocale());
		 GenericSqlFactory factory = new GenericSqlFactory(dbManager);
		 String result ="N";
		 //DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		 DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
         try {
        	 Date d = df.parse(inputBean.getRevisionDateTimeStamp());
			 StringBuilder query = new StringBuilder("select fx_material_on_list('CA Prop65',");
	         query.append(inputBean.getMaterialId()).append(",").append(DateHandler.getOracleToDateFunction(d)).append(") from dual");
	         if("Y".equals(factory.selectSingleValue(query.toString())))
	        	result = "Y";
         }
         catch(ParseException e) {
        	 e.printStackTrace();
        	 throw e;
         }
         return result;
	 }

     public String getRevisionMeetsCompanyStandard(ViewMsdsInputBean inputBean) throws Exception, BaseException {
         DbManager dbManager = new DbManager(getClient(), getLocale());
		 GenericSqlFactory factory = new GenericSqlFactory(dbManager);
         String result = "Y";
         StringBuilder query = new StringBuilder("select count(*) from company where company_msds = 'Y'");
         DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
         try {
        	 Date d = df.parse(inputBean.getRevisionDateTimeStamp());
	         if("0".equals(factory.selectSingleValue(query.toString()))) {
	            query = new StringBuilder("select count(*) from msds");
	            query.append(" where material_id = ").append(inputBean.getMaterialId());
	            query.append(" and revision_date = ").append(DateHandler.getOracleToDateFunction(d));
	            query.append(" and nvl(data_entry_complete, 'N') = 'Y'");
	            if("0".equals(factory.selectSingleValue(query.toString())))
	               result = "N";
	         }else {
	             query = new StringBuilder("select count(*) from company_msds cm");
	             query.append(" where cm.material_id = ").append(inputBean.getMaterialId());
                 query.append(" and cm.revision_date = ").append(DateHandler.getOracleToDateFunction(d));
                 query.append(" and nvl(cm.data_entry_complete, 'N') = 'Y'");
                 if("0".equals(factory.selectSingleValue(query.toString())))
                    result = "N";
             }
         }
         catch(ParseException e) {
        	 e.printStackTrace();
        	 throw e;
         }
         return result;
     }

//		get the lastest revision date
	public Collection getRevisionDateDropdownColl(BigDecimal materialId, String facility, Boolean isModuleHaas) throws Exception {
			DbManager dbManager = new DbManager(getClient(), getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager,new MsdsLocaleViewBean());

            String query = "";
            String facilityDefaultLocale = "";
            String facilityAlternateMsdsLocale = "";
            String browserAlternateMsdsLocale = "";
            if(isModuleHaas) {
            	query = new StringBuilder("select to_char(revision_date,'dd/Mon/yyyy') revision_date_with_format, revision_date revision_date, locale_code, locale_display from msds_locale_view m where material_id=")
            			.append(materialId.toString()).append(" and ON_LINE = 'Y' order by revision_date desc,locale_code").toString();
            } else {
            	StringBuilder queryBuilder = new StringBuilder();
            	queryBuilder.append("select to_char(revision_date,'dd/Mon/yyyy') revision_date_with_format, revision_date revision_date, locale_code, locale_display");
            	queryBuilder.append(" from msds_locale_view m where material_id=").append(materialId.toString());
            	queryBuilder.append(" and ON_LINE = 'Y' and locale_code in (select locale_code from facility_locale");
            	if ( ! StringHandler.isBlankString(facility)) {
            		queryBuilder.append(" where facility_id = ").append(SqlHandler.delimitString(facility));
                	queryBuilder.append(" union select a.alternate_msds_locale from vv_locale a, facility_locale fl");
                	queryBuilder.append(" where a.locale_code = fl.locale_code and fl.facility_id = ").append(SqlHandler.delimitString(facility));
            	}
            	else {
            		queryBuilder.append(" union select a.alternate_msds_locale from vv_locale a, facility_locale fl");
            		queryBuilder.append(" where a.locale_code = fl.locale_code");
            		if (browserLocaleCode.length() > 0) {
                        query += " union select '"+browserLocaleCode+"' from dual";
                        browserAlternateMsdsLocale = getAlternateMsdsLocale(browserLocaleCode);
                        if (!StringHandler.isBlankString(browserAlternateMsdsLocale) && !browserAlternateMsdsLocale.equalsIgnoreCase(browserLocaleCode)) {
                            query += " union select '"+browserAlternateMsdsLocale+"' from dual";
                        }
                    }
            	}
            	queryBuilder.append(") order by revision_date desc,locale_code");
            	query = queryBuilder.toString();
            }

	    return factory.selectQuery(query);
	} //end of method


	public String getMsdsUrl(BigDecimal materialId, String localeCode, String revisionDate) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new MsdsLocaleViewBean());
		StringBuilder query = new StringBuilder("select nvl(content,' ') content,nvl(on_line,' ') on_line from msds_locale_view m where ON_LINE = 'Y' and material_id = ");
		query.append(materialId.toString()).append(" and m.locale_code = '").append(localeCode).append("' and trunc(revision_date) = '");
		query.append(revisionDate).append("' order by revision_date desc");
		Vector<MsdsLocaleViewBean> v = (Vector<MsdsLocaleViewBean>)factory.selectQuery(query.toString());

		String msdsUrl = "";
		if(v.size() == 0) {
          msdsUrl=resource.getString("hosturl")+"gen/msds_not_found.html";
	    } else {
	      msdsUrl = v.get(0).getContent();
	    }
        return msdsUrl;
	}

	public Collection getCompositionInfo(String revisionDate, BigDecimal materialId, String facility) throws Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new MaterialCompositionViewBean());
		StringBuilder query = new StringBuilder("select cas_number, CHEMICAL_ID, percent, PERCENT_LOWER, PERCENT_UPPER, company_chemical_id, trace");

		query.append(" from COMPOSITION_COMPANY_GLOBAL ");
		query.append(" where material_id = ").append(materialId);
//		revision date
		if (!revisionDate.equalsIgnoreCase("null")) {
			query.append(" and REVISION_DATE=to_timestamp('").append(revisionDate).append(revisionDate.indexOf("-") != -1?"','yyyy-mm-dd HH24:MI:SS.FF9')":"','mm/dd/yyyy HH:MI:SS AM')");
		}
		query.append(" order by percent desc");

		return factory.selectQuery(query.toString());
	} //end of method


	public Collection getMaterialProperties(BigDecimal materialId, String localeCode, String revisionDate) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new MsdsLocaleViewBean());
		StringBuilder query = new StringBuilder("select alloy,boiling_point,carcinogen,chronic,company_id,compatibility,content,corrosive,density,evaporation_rate,eyes,fire_conditions_to_avoid,flammability,flash_point,freezing_point,health,health_effects,hmis_flammability,hmis_from_customer,hmis_health,hmis_reactivity,incompatible,ingestion,injection,inhalation,");
		query.append("mixture,nfpa_from_customer,osha_hazard,oxidizer,personal_protection,ph,physical_state,polymerize,ppe,product_code,reactivity,route_Of_Entry,sara_311_312_acute,sara_311_312_chronic,sara_311_312_fire,sara_311_312_pressure,sara_311_312_reactivity,signal_word,skin,solids,specific_gravity,specific_hazard,stable,target_organ,tsca_12b,tsca_list,");
		query.append("vapor_density,vapor_pressure,voc_comp_vapor_pressure_mmhg,round(voc_lb_per_solid_lb_calc, 4) voc_lb_per_solid_lb,round(voc_lb_per_solid_lb_low_calc, 4) voc_lb_per_solid_lb_low,round(voc_lb_per_solid_lb_up_calc, 4) voc_lb_per_solid_lb_up,voc_less_h2o_exempt,voc,voc_unit,round(voc_percent, 4) voc_percent,round(voc_lower_percent,4) voc_lower_percent,round(voc_upper_percent,4) voc_upper_percent,water_reactive,");
		query.append("federal_hazard_class,nanomaterial,radioactive,radioactivity_curies,miscible,pyrophoric,detonable,photoreactive,spontaneously_combustible,data_entry_complete,date_entered ");
		query.append(" from msds_locale_view where material_id = ");
		//query.append(materialId.toString()).append(" and locale_code = fx_session_locale_code and revision_date = '");
        query.append(materialId.toString()).append(" and locale_code = '").append(localeCode).append("' and trunc(revision_date) = '");
        query.append(revisionDate).append("'");
		return factory.selectQuery(query.toString());
	}

	public Collection getShippingInfo(BigDecimal materialId) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new MaterialShipDeclarationViewBean());
		StringBuilder query = new StringBuilder("select * from MATERIAL_SHIP_DECLARATION_VIEW where material_id = ");
		query.append(materialId.toString());

		return factory.selectQuery(query.toString());
	}

	public Collection getListInfo(String revisionDate, String id, String facility, String listType, String listId) throws Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new MsdsViewerListViewBean());
		String query = "select list_id, percent, display_level, list_name," +
		" on_list, sublist, lookup_list_id from msds_viewer_list_view where material_id=" + id + "";
		String queryForSubList = "";
//		revision date
		if (!revisionDate.equalsIgnoreCase("null")) {
			query += " and trunc(REVISION_DATE)='" + revisionDate + "'";
		}

		queryForSubList = query;
		if ("lists".equalsIgnoreCase(listType)) {
			query += " and display_level = 0";
		} else {
			query += " and display_level = 0";
			queryForSubList += " and display_level = 1 and list_id='" + listId + "'";
		}
//		add order by
		query += " order by list_name";
		queryForSubList += " order by list_name";

		return factory.selectQuery(query.toString());
	} //end of method

	public Collection getSideViewInfo(ViewMsdsInputBean inputBean, Boolean IsModuleHaas) throws Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new MsdsLocaleViewBean());

		StringBuilder query = new StringBuilder("");
		if (!IsModuleHaas && !StringHandler.isBlankString(inputBean.getFacility())) {
			query.append("select fx_company_catalog_msds_id (").append(inputBean.getMaterialId()).append(",'").append(inputBean.getFacility());
			query.append("') msds_no,");
			query.append("m.* from msds_locale_view m where material_id = ").append(inputBean.getMaterialId());
	        query.append(" and trunc(revision_date) = '").append(inputBean.getRevisionDate()).append("'");
	        query.append(" and on_line = 'Y' and locale_code = '").append(inputBean.getLocaleCode()).append("'");
		} else {
			query.append("select fx_company_catalog_msds_id (").append(inputBean.getMaterialId());
			query.append(",'All') msds_no,");
			query.append("m.* from msds_locale_view m where material_id = ").append(inputBean.getMaterialId());
	        query.append(" and trunc(revision_date) = '").append(inputBean.getRevisionDate()).append("'");
	        query.append(" and on_line = 'Y' and locale_code = '").append(inputBean.getLocaleCode()).append("'");
		}
        query.append(" order by revision_date desc,locale_code");
        return factory.selectQuery(query.toString());
	}

	public String sendNewRevisionRequest(ViewMsdsInputBean inputBean) throws BaseException {
		String errMsg = "";

		StringBuilder subject = new StringBuilder("Request New Revision for Material Id: ").append(inputBean.getMaterialId().toString()).append(", client: ").append(inputBean.getClient());
		StringBuilder msg = new StringBuilder("Name:").append(inputBean.getName());
		if(inputBean.getRevisionDate() != null) msg.append("\nRevisionDate:").append(inputBean.getRevisionDate());
		if(inputBean.getPhone() != null) msg.append("\nPhone:").append(inputBean.getPhone());
		if(inputBean.getEmail() != null) msg.append("\nemail:").append(inputBean.getEmail());
		if(inputBean.getComments() != null) msg.append("\ncomments:").append(inputBean.getComments());

		if (inputBean.getTheFile() != null)  {
			if(log.isDebugEnabled()) {
				log.debug("Request New Revision for Material Id: "+inputBean.getMaterialId());
			}
			try {
				String dirname = resource.getString("saveltempreportpath");
				File dir = new File(dirname);

				String fileName = inputBean.getFileName();

				String[] fileNames = fileName.split("[.]");
				File f = File.createTempFile("tmp", "."+fileNames[fileNames.length-1], dir);
				FileHandler.copy(inputBean.getTheFile(),f);

				MailProcess.sendEmail("msds.queue@haasgroupintl.com","","msds.queue@haasgroupintl.com",
					subject.toString(),
					msg.toString(),
					fileName,
					dirname+f.getName()
					);
			}catch(Exception ex){
				ex.printStackTrace();
				errMsg = "Error sending email.";
			};

		}
		else {
			try {
				MailProcess.sendEmail("msds.queue@haasgroupintl.com","","msds.queue@haasgroupintl.com",
					"sendNewRevisionRequest",
					"Name:"+inputBean.getName()+"  Phone:"+inputBean.getPhone()+"  email:"+inputBean.getEmail()+"  comments:"+inputBean.getComments(),
					null,
					null);
			}catch(Exception ex){
				errMsg = "Error sending email.";
			};
		}

		return errMsg;
	}

	public Collection getMaterialLists(ViewMsdsInputBean inputBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
	    GenericSqlFactory factory = new GenericSqlFactory(dbManager, new MsdsViewerListViewBean());

		StringBuilder query = new StringBuilder("select /*+ RULE */ m.list_id, m.percent, m.display_level, m.list_name, m.on_list, m.sublist, m.lookup_list_id, m.material_id, m.revision_date");
		query.append(" from msds_viewer_list_view m where m.material_id=").append(inputBean.getMaterialId());
		query.append(" and trunc(m.revision_date)='").append(inputBean.getRevisionDate());
		query.append("' and m.display_level=0 order by m.list_name");

		Collection<MsdsViewerListViewBean> listColl = factory.selectQuery(query.toString());

		StringBuilder sublistQuery = new StringBuilder("select mvlv.list_id, mvlv.percent, mvlv.display_level, mvlv.list_name,mvlv.on_list,mvlv.sublist,mvlv.lookup_list_id, mvlv.material_id, mvlv.revision_date");
		sublistQuery.append(" from msds_viewer_list_view mvlv where mvlv.material_id=").append(inputBean.getMaterialId());
		sublistQuery.append(" and trunc(mvlv.revision_date)='").append(inputBean.getRevisionDate());
		sublistQuery.append("' and mvlv.display_level=1");
		sublistQuery.append(" and mvlv.list_id in (").append("select m.list_id from msds_viewer_list_view m where m.material_id=");
		sublistQuery.append(inputBean.getMaterialId()).append(" and trunc(m.revision_date)='");
		sublistQuery.append(inputBean.getRevisionDate()).append("' and m.display_level=0").append(")");
		sublistQuery.append(" order by mvlv.list_id, mvlv.display_level, mvlv.list_name");

		Collection<MsdsViewerListViewBean> sublistColl = factory.selectQuery(sublistQuery.toString());

		for (Iterator<MsdsViewerListViewBean> listIt = listColl.iterator();listIt.hasNext();) {
			MsdsViewerListViewBean listBean = listIt.next();
			if ("Yes".equals(listBean.getSublist())) {
				for (Iterator<MsdsViewerListViewBean> sublistIt = sublistColl.iterator();sublistIt.hasNext();) {
					MsdsViewerListViewBean sublistBean = sublistIt.next();
					if (listBean.getListId().equals(sublistBean.getListId())) {
						if (listBean.getSublistColl() == null) {
							listBean.setSublistColl(new Vector<MsdsViewerListViewBean>());
						}
						listBean.getSublistColl().add(sublistBean);
					}
				}
			}
		}

		return listColl;
	}

	public Collection<MsdsReportListViewBean> getReportListCollection(HttpServletRequest request, ViewMsdsInputBean inputBean, String lookupListId) throws BaseException, JSONException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
	    GenericSqlFactory factory = new GenericSqlFactory(dbManager, new MsdsReportListViewBean());

	    StringBuilder query = new StringBuilder();
	    query.append("select m.percent,rls.cas_number, replace(rls.rpt_chemical,chr(13) || chr(10),'') rpt_chemical ");
	    query.append("from REPORT_LIST_DISTINCT_MV rls, msds_viewer_list_comp_view m ");
	    query.append("where rls.list_id='").append(lookupListId).append("' ");
	    query.append("and m.list_id(+)=rls.list_id ");
	    query.append("and m.cas_number(+)=rls.cas_number ");
	    query.append("and m.material_id(+)=").append(inputBean.getMaterialId());
	    query.append(" and trunc(m.revision_date(+))='").append(inputBean.getRevisionDate()).append("'");
	    query.append(" order by cas_number");

		Collection<MsdsReportListViewBean> beanColl = factory.selectQuery(query.toString());
		
		JSONObject jsonMainData = new JSONObject();
		JSONArray rows = new JSONArray();
		int pctIdx = 0;
		int idx = 1;
		for (Iterator<MsdsReportListViewBean> it = beanColl.iterator();it.hasNext();) {
			MsdsReportListViewBean bean = it.next();
			JSONObject rowObj = new JSONObject();
			rowObj.put("id", idx++);

			JSONArray rowData = new JSONArray();
			rowData.put("N");
			rowData.put(StringHandler.emptyIfNull(bean.getPercent()));
			rowData.put(bean.getCasNumber());
			rowData.put(bean.getRptChemical());
			rowObj.put("data", rowData);
			if (bean.getPercent() != null) {
				if (rows.length() > pctIdx) {
					rows.put(rows.get(pctIdx));
				}
				rows.put(pctIdx++, rowObj);
			}
			else {
				rows.put(rowObj);
			}
		}

		jsonMainData.put("rows",rows);
		request.setAttribute("jsonMainData", jsonMainData);

		return beanColl;
	}
	
	public Collection getMaterialsByItem(ViewMsdsInputBean inputBean) throws BaseException {
	    DbManager dbManager = new DbManager(getClient(), getLocale());
	    GenericSqlFactory factory = new GenericSqlFactory(dbManager, new MsdsLocaleViewBean());

	    StringBuilder query = new StringBuilder("select m.material_id, m.material_desc from part p, material m where p.item_id = ");
	    query.append(inputBean.getItemId()).append(" and m.material_id = p.material_id order by m.material_desc");

	    Collection materialColl = factory.selectQuery(query.toString());

	    if ((inputBean.getMaterialId() == null) && (materialColl.iterator().hasNext())) {
	      inputBean.setMaterialId(((MsdsLocaleViewBean)materialColl.iterator().next()).getMaterialId());
	    }

	    return materialColl;
	}

} //end of class