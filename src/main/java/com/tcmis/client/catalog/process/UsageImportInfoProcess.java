package com.tcmis.client.catalog.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.catalog.beans.CompanyIdCustomerMsdsDBBean;
import com.tcmis.client.catalog.beans.ImportUomBean;
import com.tcmis.client.catalog.beans.UsageImportConversionViewBean;
import com.tcmis.client.catalog.beans.UsageImportInputBean;
import com.tcmis.client.catalog.beans.VocetFugitiveCategoryBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.BulkMailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * Process for UsageImportInfoProcess
 * @version 1.0
 *****************************************************************************/
public class UsageImportInfoProcess extends GenericProcess {

	Log log = LogFactory.getLog(this.getClass());

	public UsageImportInfoProcess(String client,String locale)  {
		super(client,locale);
	}
	
	public Collection getSearchResults(UsageImportInputBean inputBean) throws BaseException, Exception {
		
		factory.setBean(new UsageImportConversionViewBean());
		SearchCriteria criteria = new SearchCriteria();

		criteria.addCriterion("facilityId", SearchCriterion.EQUALS, inputBean.getFacilityId());
		
		if (null != inputBean.getCreatedBy()) {
			criteria.addCriterion("createdBy", SearchCriterion.EQUALS, inputBean.getCreatedBy().toString());
		}

		if (null != inputBean.getCreatedFromDate()) {
			criteria.addCriterion("createdDate", SearchCriterion.FROM_DATE, inputBean.getCreatedFromDate());
		}
		
		if (null != inputBean.getCreatedToDate()) {
			criteria.addCriterion("createdDate", SearchCriterion.TO_DATE, inputBean.getCreatedToDate());
		}

		if (!StringHandler.isBlankString(inputBean.getSearchArgument())) {
			criteria.addCriterionWithMode(inputBean.getSearchField(), inputBean.getSearchMode(), inputBean.getSearchArgument());
		}
		
		if (null != inputBean.getLastModifiedBy()) {
			criteria.addCriterion("lastModifiedBy", SearchCriterion.EQUALS, inputBean.getLastModifiedBy().toString());
		}

		SortCriteria sort = new SortCriteria();
		sort.setSortAscending(true);
		sort.addCriterion("companyId, customerMsdsDb, facilityId, partNumber");
		Collection results = factory.select(criteria, sort, "usage_import_conversion_view");
		
		// This is to get the results for TCMIS catalog parts.
		if("Y".equals(inputBean.getIncludeTcmisCatalogParts())) {
			criteria = new SearchCriteria();
			criteria.addCriterion("facilityId", SearchCriterion.EQUALS, inputBean.getFacilityId());
			
			if (!StringHandler.isBlankString(inputBean.getSearchArgument())) {
				criteria.addCriterionWithMode(inputBean.getSearchField(), inputBean.getSearchMode(), inputBean.getSearchArgument());
			}
			
			sort = new SortCriteria();
			sort.addCriterion("facilityId, partNumber");
			results.addAll(factory.select(criteria, sort, "USAGE_CONVERSION_VIEW"));
		}
		
		return results;
	}
	
	public Collection getUOMColl(String facilityId) throws BaseException, Exception {
	    DbManager dbManager = new DbManager(getClient(),getLocale());
	    GenericSqlFactory factory = new GenericSqlFactory(dbManager,new ImportUomBean());  //second argument is the bean that you are working with
		StringBuilder query = new StringBuilder("select * from facility_usage_import_uom where facility_id = '").append(facilityId).append("'");
		
		query.append(" order by unit_of_measure");
	    return factory.selectQuery(query.toString());
	}
	
	public Collection getVocetFugitiveCategoryColl(String facilityId) throws BaseException, Exception {
	    DbManager dbManager = new DbManager(getClient(),getLocale());
	    GenericSqlFactory factory = new GenericSqlFactory(dbManager,new VocetFugitiveCategoryBean());  //second argument is the bean that you are working with
		StringBuilder query = new StringBuilder("select * from vocet_fugitive_category where facility_id = '").append(facilityId).append("'");
		
		query.append(" order by VOCET_FUGITIVE_CAT_NAME");
	    return factory.selectQuery(query.toString());
	}
	
	
	public Collection getCompanyIdandCustomerMsdsDb(String facilityId) throws BaseException, Exception {
	    DbManager dbManager = new DbManager(getClient(),getLocale());
	    GenericSqlFactory factory = new GenericSqlFactory(dbManager,new CompanyIdCustomerMsdsDBBean());  //second argument is the bean that you are working with
		StringBuilder query = new StringBuilder("select cp.company_id,cp.customer_msds_db,cp.catalog_id, c.catalog_desc,cf.facility_id"); 
		query.append(" from catalog_company cp, catalog c, catalog_facility cf");
		query.append(" where cp.catalog_company_id = c.company_id and cp.catalog_id = c.catalog_id");
		query.append(" and cp.company_id = cf.company_id and cp.catalog_company_id = cf.catalog_company_id"); 
		query.append(" and cp.catalog_id = cf.catalog_id and cf.display = 'Y' and cf.facility_id = '");
		query.append(facilityId).append("'");
		
	    return factory.selectQuery(query.toString());
	}
	
	public String update(Collection<UsageImportConversionViewBean> beans, PersonnelBean personnelBean) throws BaseException, Exception {
		
		BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
		
		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);
		String errMsg = "";
		String preRecord = ""; 
		
		StringBuilder completePartsMessage = new StringBuilder();	
		StringBuilder newPartMessage = null;
		String currentKitInfo = null;
		String newPartFacility = null;
		String newPartCompanyId = null;
		
		for (UsageImportConversionViewBean bean : beans) {
			StringBuilder query = new StringBuilder();
			boolean setUsageImportConversionUpdatedBy = true;
			if("changed".equals(bean.getUpdateStatus())) {
				try {
					query.append("update usage_import_conversion set last_modified_date = sysdate, last_modified_by = ").append(personnelId);
					query.append(", unit_of_measure = ").append(getSqlString(bean.getUnitOfMeasure()));
					query.append(", pounds_per_unit = ").append(getSqlString(bean.getPoundsPerUnit()));
					query.append(", comments = ").append(getSqlString(bean.getComments()));
					query.append(", TIER_II_STORAGE_CODE = ").append(getSqlString(bean.getTierIIStorage()));
					query.append(", TIER_II_TEMPERATURE_CODE = ").append(getSqlString(bean.getTierIITemperature()));
					query.append(", PURCHASING_METHOD_ID = ").append(getSqlString(bean.getPurchasingMethodId()));
					query.append(" where facility_id = '").append(bean.getFacilityId()); 
					query.append("' and customer_msds_db = '").append(bean.getCustomerMsdsDb());
					query.append("' and part_number = '").append(bean.getOriginalPartNumber());
					query.append("' and customer_msds_or_mixture_no = '").append(bean.getOriginalCustomerMsdsOrMixtureNo());
					query.append("' and customer_msds_number = '").append(bean.getCustomerMsdsNumber()).append("'");
					
					
					genericSqlFactory.deleteInsertUpdate(query.toString());
				}catch (Exception e) {
					e.printStackTrace();
					errMsg += "error updating part_number:" + bean.getOriginalPartNumber() + "   MSDS:" +bean.getOriginalCustomerMsdsOrMixtureNo() + "\n";
				}
				setUsageImportConversionUpdatedBy = false;
			} else if(bean.getUpdateStatus() != null && ("new".equals(bean.getUpdateStatus()) || "newPart".equals(bean.getUpdateStatus()))) {
				try {
					query.append("insert into usage_import_conversion (company_id, facility_id, customer_msds_db, customer_msds_or_mixture_no, part_number, unit_of_measure, pounds_per_unit, created_by, created_date, comments, tier_ii_storage_code, tier_ii_temperature_code,customer_msds_number,purchasing_method_id) values ('");
					//query.append("insert into usage_import_conversion (company_id, facility_id, customer_msds_db, customer_msds_or_mixture_no, part_number, unit_of_measure, pounds_per_unit, created_by, created_date, comments, tier_ii_storage_code, tier_ii_temperature_code) values ('");
					query.append(bean.getCompanyId()).append("', '").append(bean.getFacilityId()).append("', '");
					query.append(bean.getCustomerMsdsDb()).append("', '").append(bean.getCustomerMsdsOrMixtureNo()).append("', '");
					query.append(bean.getPartNumber()).append("', ").append(getSqlString(bean.getUnitOfMeasure())).append(", ");
					query.append(getSqlString(bean.getPoundsPerUnit())).append(", ").append(personnelId).append(", sysdate,");
					query.append(getSqlString(bean.getComments())).append(",").append(getSqlString(bean.getTierIIStorage())).append(",").append(getSqlString(bean.getTierIITemperature())).append(",").append(getSqlString(bean.getCustomerMsdsNumber())).append(",").append(getSqlString(bean.getPurchasingMethodId())).append(")");
					//query.append(getSqlString(bean.getComments())).append(",").append(getSqlString(bean.getTierIIStorage())).append(",").append(getSqlString(bean.getTierIITemperature())).append(")");
					
					
					genericSqlFactory.deleteInsertUpdate(query.toString());
					
					// Send out email if a new part is added.
	
					if(newPartFacility == null)
						newPartFacility = bean.getFacilityId();
					if(newPartCompanyId == null)
						newPartCompanyId = bean.getCompanyId();
				
					if(currentKitInfo == null || !currentKitInfo.equalsIgnoreCase(bean.getCustomerMsdsOrMixtureNo()  +  bean.getPartNumber()))
					{
						currentKitInfo = bean.getCustomerMsdsOrMixtureNo() +  bean.getPartNumber();
						if(newPartMessage != null)
							completePartsMessage.append(newPartMessage);

						newPartMessage = new StringBuilder();
						newPartMessage.append("New Part, ").append(StringHandler.emptyIfNull(bean.getPartNumber()));
						newPartMessage.append(", was added by ").append(StringHandler.emptyIfNull(personnelBean.getFirstName())).append(" ").append(StringHandler.emptyIfNull(personnelBean.getLastName())).append("\r\n");
						newPartMessage.append("MSDS: ").append(StringHandler.emptyIfNull(bean.getCustomerMsdsOrMixtureNo())).append("\r\n");
						newPartMessage.append("UOM: ").append(StringHandler.emptyIfNull(bean.getUnitOfMeasure())).append("\r\n");
						newPartMessage.append("lb/ UOM: ").append(StringHandler.emptyIfNull(bean.getPoundsPerUnit())).append("\r\n\r\n");
						newPartMessage.append("Components: \r\n\r\n");
						newPartMessage.append("Component MSDS: ").append(StringHandler.emptyIfNull(bean.getCustomerMsdsNumber())).append("\r\n");
						newPartMessage.append("Material ID: ").append(StringHandler.emptyIfNull(bean.getMaterialId())).append("\r\n");
						newPartMessage.append("Trade Name: ").append(StringHandler.emptyIfNull(bean.getMaterialDesc())).append("\r\n\r\n\r\n");
					}
					else
					{
						newPartMessage.append("Components MSDS: ").append(StringHandler.emptyIfNull(bean.getCustomerMsdsNumber())).append("\r\n");
						newPartMessage.append("Material ID: ").append(StringHandler.emptyIfNull(bean.getMaterialId())).append("\r\n");
						newPartMessage.append("Trade Name: ").append(StringHandler.emptyIfNull(bean.getMaterialDesc())).append("\r\n\r\n\r\n");
					}
	//		log.debug("message:"+message.toString());			

				}catch (Exception e) {
						e.printStackTrace();
						errMsg += "error adding part_number:" + bean.getOriginalPartNumber() + "   MSDS:" +bean.getOriginalCustomerMsdsOrMixtureNo() + "\n";
						errMsg += "The Part number and MSDS may already exist\n\n\n";
				}
			} else if("deleteByMsds".equals(bean.getUpdateStatus()) && !StringHandler.isBlankString(bean.getOriginalCustomerMsdsOrMixtureNo())) {
				try {
					delete(bean.getCompanyId(), bean.getFacilityId(), bean.getCustomerMsdsDb(), bean.getPartNumber(), bean.getCustomerMsdsOrMixtureNo());
				}catch (Exception e) {
					e.printStackTrace();
					errMsg += "error deleting part_number:" + bean.getOriginalPartNumber() + "   MSDS:" +bean.getOriginalCustomerMsdsOrMixtureNo() + "\n";
				}
			} else if("deleteByPart".equals(bean.getUpdateStatus()) && !StringHandler.isBlankString(bean.getOriginalPartNumber())) {
				String currentRecord = bean.getCompanyId()+bean.getFacilityId()+bean.getCustomerMsdsDb()+bean.getPartNumber();
				try {
				//	log.debug("currentRecord:"+currentRecord+"    preRecord:"+preRecord);
					if(!currentRecord.equals(preRecord)) {
						delete(bean.getCompanyId(), bean.getFacilityId(), bean.getCustomerMsdsDb(), bean.getPartNumber(), null);
						preRecord = currentRecord;
					}	
				}catch (Exception e) {
					e.printStackTrace();
					errMsg += "error deleting part_number:" + bean.getOriginalPartNumber();
				}
			} else if(StringHandler.isBlankString(bean.getUpdateStatus()) && !bean.getComments().equals(bean.getOriginalComments())) {
				try {
					query.append("update usage_import_conversion set last_modified_date = sysdate, last_modified_by = ").append(personnelId);
					query.append(", comments = ").append(getSqlString(bean.getComments()));
					query.append(" where facility_id = '").append(bean.getFacilityId()); 
					query.append("' and customer_msds_db = '").append(bean.getCustomerMsdsDb());
					query.append("' and part_number = '").append(bean.getOriginalPartNumber());
					query.append("' and customer_msds_number = '").append(bean.getCustomerMsdsNumber());
					query.append("' and customer_msds_or_mixture_no = '").append(bean.getOriginalCustomerMsdsOrMixtureNo()).append("'");
					
					genericSqlFactory.deleteInsertUpdate(query.toString());
				}catch (Exception e) {
					e.printStackTrace();
					errMsg += "error updating part_number:" + bean.getOriginalPartNumber() + "   MSDS:" +bean.getOriginalCustomerMsdsOrMixtureNo() + "\n";
				}
			} 
			
			if("changed".equals(bean.getFugitiveCategoryStatus()) || "new".equals(bean.getFugitiveCategoryStatus())) {
				try {
					if("TCMIS".equals(bean.getStatus())) {
						Collection inArgs = new Vector(7);
						inArgs.add(bean.getCompanyId());
						inArgs.add(bean.getFacilityId());
						inArgs.add(bean.getCatalogCompanyId());
						inArgs.add(bean.getCatalogId());
						inArgs.add(bean.getPartNumber());
						inArgs.add(bean.getVocetFugitiveCatId());
						inArgs.add(personnelId);
						
						if (log.isDebugEnabled()) {
							log.debug("pkg_vocet_fugitive.p_upsert_vocet_cat_part"+inArgs);
						}
						genericSqlFactory.doProcedure("pkg_vocet_fugitive.p_upsert_vocet_cat_part", inArgs, new Vector());
					}
					else {
						if(setUsageImportConversionUpdatedBy)
						{
							query = new StringBuilder();
							query.append("update usage_import_conversion set last_modified_date = sysdate, last_modified_by = ").append(personnelId);
							query.append(" where facility_id = '").append(bean.getFacilityId()); 
							query.append("' and customer_msds_db = '").append(bean.getCustomerMsdsDb());
							query.append("' and part_number = '").append(bean.getOriginalPartNumber());
							query.append("' and customer_msds_or_mixture_no = '").append(bean.getOriginalCustomerMsdsOrMixtureNo()).append("'");
							genericSqlFactory.deleteInsertUpdate(query.toString());
						}
						Collection inArgs = new Vector(5);
						inArgs.add(bean.getCompanyId());
						inArgs.add(bean.getFacilityId());
						inArgs.add(bean.getPartNumber());
						inArgs.add(bean.getVocetFugitiveCatId());
						inArgs.add(personnelId);
						
						if (log.isDebugEnabled()) {
							log.debug("pkg_vocet_fugitive.p_upsert_vocet_noncat_part"+inArgs);
						}
						genericSqlFactory.doProcedure("pkg_vocet_fugitive.p_upsert_vocet_noncat_part", inArgs, new Vector());
					}
				}catch (Exception e) {
					e.printStackTrace();
					errMsg += "error updating part_number:" + bean.getOriginalPartNumber() + "   MSDS:" +bean.getOriginalCustomerMsdsOrMixtureNo() + "\n";
				}
				
			}
			else if("deleted".equals(bean.getFugitiveCategoryStatus())) {
				try {
					Collection inArgs = new Vector(3);
					inArgs.add(bean.getCompanyId());
					inArgs.add(bean.getFacilityId());
					inArgs.add(bean.getPartNumber());
					
					if (log.isDebugEnabled()) {
						log.debug("pkg_vocet_fugitive.p_delete_vocet_noncat_part"+inArgs);
					}
					genericSqlFactory.doProcedure("pkg_vocet_fugitive.p_delete_vocet_noncat_part", inArgs, new Vector());
				}catch (Exception e) {
					e.printStackTrace();
					errMsg += "error deleting part_number:" + bean.getOriginalPartNumber() + "   MSDS:" +bean.getOriginalCustomerMsdsOrMixtureNo() + "\n";
				}
			}
		}
		
		if(completePartsMessage.length() > 0 || newPartMessage != null)
		{
			completePartsMessage.append(newPartMessage);
			BulkMailProcess bulkMailProcess = new BulkMailProcess(this.getClient());
			bulkMailProcess.sendBulkEmail("UsageImportNotification", newPartFacility, null, newPartCompanyId ,"New Part(s) added from Usage Import Info Page", completePartsMessage.toString(), false);
		}
		return errMsg;
	}
	
	private void delete(String companyId, String facilityId, String customerMsdsDb, String partNumber, String customerMsdsOrMixtureNo) throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);
		
		StringBuilder query = new StringBuilder();
		
		query.append("delete from usage_import_conversion where facility_id = '").append(facilityId); 
		query.append("' and company_id = '").append(companyId);
		query.append("' and customer_msds_db = '").append(customerMsdsDb);
		query.append("' and part_number = '").append(partNumber).append("'");
		
		if(customerMsdsOrMixtureNo != null && customerMsdsOrMixtureNo.length() > 0)
			query.append(" and customer_msds_or_mixture_no = '").append(customerMsdsOrMixtureNo).append("'");;
					
		genericSqlFactory.deleteInsertUpdate(query.toString());
		
	}

	public String getDupCount(UsageImportConversionViewBean validateBean) throws BaseException, Exception {
	    DbManager dbManager = new DbManager(getClient(),getLocale());
	    GenericSqlFactory factory = new GenericSqlFactory(dbManager,new ImportUomBean());  //second argument is the bean that you are working with
		StringBuilder query = new StringBuilder("select count(*) from usage_import_conversion where facility_id = '").append(validateBean.getFacilityId()).append("'");
		query.append(" and customer_msds_db = '").append(validateBean.getCustomerMsdsDb());
		query.append("' and customer_msds_or_mixture_no = '").append(validateBean.getCustomerMsdsOrMixtureNo());
		query.append("' and part_number = '").append(validateBean.getPartNumber()).append("'");
		
	    return factory.selectSingleValue(query.toString());
	}

    public ExcelHandler getExcelReport(PersonnelBean personnelBean,UsageImportInputBean inputBean, Locale locale, String module) throws
	    NoDataException, BaseException, Exception {
		
        boolean showFugitiveCategory = personnelBean.isFeatureReleased("ShowFugitiveCategory",inputBean.getFacilityId(),personnelBean.getCompanyId());
		Collection<UsageImportConversionViewBean> data = this.getSearchResults(inputBean);
		
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);
	
		pw.addTable();
	//write column headers
		pw.addRow();
		
		int[] types = {
				  0,0,0,0,0,
				  0,0,0,0,0,
				  0,0,0};
				
		int[] horizAligns = {
				  0,0,0,0,0,
				  0,0,0,0,0,
				  0,0,0};
		
		if(showFugitiveCategory) {		
			String[] headerkeys = {
				  "label.partno","label.fugitivecategory", "label.msds","label.uom","label.lbsuom","label.compmsds","label.materialid","label.tradename",
				  "label.approvalcode","label.tieriistoragecontainer","label.tieriistoragepressure","label.tieriistoragetemperature","label.comments","label.lastUpdatedBy","label.createdby",
				  };
			
			int[] widths = {
					  10,10,10,10,10,10,8,20,
					  10,15,15,15,20,20,20
					  };
			
			pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
		}
		else {
			String[] headerkeys = {
					  "label.partno", "label.msds","label.uom","label.lbsuom","label.compmsds","label.materialid","label.tradename",
					  "label.approvalcode","label.tieriistoragecontainer","label.tieriistoragepressure","label.tieriistoragetemperature","label.comments","label.lastUpdatedBy","label.createdby",
					  };
				
				int[] widths = {
						  10,10,10,10,10,8,20,
						  10,15,15,15,20,20,20
						  };
			
			pw.applyColumnHeader(headerkeys, types, widths, horizAligns);
		} 
					
					
		
	  for (UsageImportConversionViewBean member : data) {
		  
	    pw.addRow();
	    
	    pw.addCell(member.getPartNumber());
	    if(showFugitiveCategory)
	    	pw.addCell(member.getVocetFugitiveCatName());
	    pw.addCell(member.getCustomerMsdsOrMixtureNo());
	    pw.addCell(member.getUnitOfMeasure());
	    pw.addCell(member.getPoundsPerUnit());
	    pw.addCell(member.getCustomerMsdsNumber());
	    pw.addCell(member.getMaterialId());
	    pw.addCell(member.getTradeName());
	    pw.addCell(member.getApprovalCode());
	    pw.addCell((member.getTierIIStorage() != null && member.getTierIIStorage().length() > 3 ) ? member.getTierIIStorage().substring(2):"");
		pw.addCell((member.getTierIIPressure() != null &&  member.getTierIIPressure().length() > 3) ? member.getTierIIPressure().substring(2):"");
		pw.addCell((member.getTierIITemperature() != null &&  member.getTierIITemperature().length() > 3) ? member.getTierIITemperature().substring(2):"");
	    pw.addCell(member.getComments());
	    pw.addCell(member.getLastModifiedByName());
	    pw.addCell(member.getCreatedByName());


	  }
	  return pw;
	}

} //end of class