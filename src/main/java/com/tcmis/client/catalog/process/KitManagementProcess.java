package com.tcmis.client.catalog.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.catalog.beans.KitManagementBean;
import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.distribution.beans.SalesQuoteViewBean;

	/**
	 * ******************************************************************
	 * Process for the PoExpediting Section
	 * 
	 * @version 1.0
	 * *****************************************************************
	 */

	public class KitManagementProcess extends BaseProcess {
		Log log = LogFactory.getLog(this.getClass());

		public KitManagementProcess(String client, Locale locale) {
			super(client, locale);
		}

		public KitManagementProcess(String client) {
			super(client);
		}

		@SuppressWarnings("unchecked")
		public Collection<KitManagementBean> getSearchResult(KitManagementBean input, PersonnelBean user) throws BaseException {
			DbManager dbManager = new DbManager(getClient(), getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager,new KitManagementBean());

			if(StringHandler.isBlankString(input.getSearchArgument()))
				return factory.selectQuery("select * from table (pkg_mixture_management.fx_mixture_search_data('"+user.getCompanyId()+"','"+input.getFacilityId()+"','MSDS','','like',"+("Y".equalsIgnoreCase(input.getShowActive())?"'Y'":"'N'")+")) order by mixture_id");

			else
				return factory.selectQuery("select * from table (pkg_mixture_management.fx_mixture_search_data('"+user.getCompanyId()+"','"+input.getFacilityId()+"','"+input.getSearchField()+"','"+input.getSearchArgument()+"','"+input.getSearchMode()+"',"+("Y".equalsIgnoreCase(input.getShowActive())?"'Y'":"'N'")+")) order by mixture_id");
		}

		public Collection update(Collection<KitManagementBean> inputLines, PersonnelBean user) throws BaseException {
			Vector errorMessages = new Vector();
			Collection inArgs = null;
			Collection outArgs = null;
			String errorMsg = "";
			DbManager dbManager = new DbManager(getClient(), getLocale());
			
			GenericSqlFactory mixFactory = new GenericSqlFactory(dbManager);
			GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);
			
			BigDecimal currentMixId = null;

			for (KitManagementBean bean : inputLines) {
				
				if("Y".equalsIgnoreCase(bean.getAmountPermission()) && bean.getAmount() != null)
				{
					StringBuilder mixupdateQuery = new StringBuilder("update mixture_material set amount = ");
					mixupdateQuery.append(bean.getAmount());
					mixupdateQuery.append(", size_unit = '");
					mixupdateQuery.append(bean.getSizeUnit());
					mixupdateQuery.append("' where mixture_id = ");
					mixupdateQuery.append(bean.getMixtureId());
					mixupdateQuery.append(" and material_id = ");
					mixupdateQuery.append(bean.getMaterialId());
					mixupdateQuery.append(" and company_id = '");
					mixupdateQuery.append(user.getCompanyId());
					mixupdateQuery.append("'");
					mixFactory.deleteInsertUpdate(mixupdateQuery.toString());
				}

				if (currentMixId == null || !bean.getMixtureId().equals(currentMixId)) 
				{ // Check the OK box was/was not checked value in the bean

					/*
					 * Check to see if the user has the permissions to do the
					 * update, for the inventory group of each line.
					 */
					//if (permissionBeanuser.hasFacilityPermission("KitManagement", input.getFacilityId(), user.getCompanyId())) 
					{
						try {
							inArgs = new Vector(16);
							inArgs.add(bean.getMixtureId());
							inArgs.add(bean.getMixtureDesc());
							if(bean.getVoc() != null)
							{
								inArgs.add(bean.getVoc());
								inArgs.add(bean.getVocDetect());
								inArgs.add(bean.getVocUpper());
								inArgs.add(bean.getVocUnit());
								inArgs.add(bean.getVocSource());
							}
							else
							{
								inArgs.add(null);
								inArgs.add(null);
								inArgs.add(null);
								inArgs.add(null);
								inArgs.add(null);
							}
							if(bean.getVocLwes() != null)
							{
								inArgs.add(bean.getVocLwes());
								inArgs.add(bean.getVocLwesDetect());
								inArgs.add(bean.getVocLwesUpper());
								inArgs.add(bean.getVocLwesUnit());
								inArgs.add(bean.getVocLwesSource());
							}
							else
							{
								inArgs.add(null);
								inArgs.add(null);
								inArgs.add(null);
								inArgs.add(null);
								inArgs.add(null);
							}
							inArgs.add(bean.getPhysicalState());
							inArgs.add(user.getPersonnelIdBigDecimal());
							inArgs.add("true".equalsIgnoreCase(bean.getStatus())?"A":"I");
							inArgs.add(bean.getMixtureProductCode());
							inArgs.add(bean.getRevisionComments());
							inArgs.add(bean.getPhysicalStateSource());
								
							if(bean.getVoc() == null)	
								inArgs.add("Missing value");
							else
								inArgs.add(null);
							
							if(bean.getVocLwes() == null)	
								inArgs.add("Missing value");
							else
								inArgs.add(null);
							
							outArgs = new Vector(1);
							outArgs.add(new Integer(java.sql.Types.VARCHAR));
							log.debug("Update arguments: " + inArgs);		
							
							currentMixId = bean.getMixtureId();

							// uncomment and modify the following codes to call a specific procedure
							factory.doProcedure("pkg_mixture_management.p_update", inArgs);
							      
							/*      if(error.size()>0 && error.get(0) != null)
							      {
							     	 String errorCode = (String) error.get(0);
							     	 log.info("Error in Procedure P_PO_LINE_EXPEDITE_NOTES: "+bean.getRadianPo()+"-"+bean.getPoLine()+" Error Code "+errorCode+" ");
							     	 errorMessages.add(errorCode);
							      }  */
		
						}
						
						
						
						catch (Exception e) {
							errorMsg = "Error updating: " + bean.getMixtureDesc();
							errorMessages.add(errorMsg);
						}
					}
				}
			}
			factory = null;
			dbManager = null;

			return (errorMessages.size() > 0 ? errorMessages : null);
		}
		
		public ExcelHandler getExcelReport(Collection<KitManagementBean> data, Locale locale) throws NoDataException, BaseException, Exception {

			ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
			ExcelHandler pw = new ExcelHandler(library);

			
			pw.addTable();
			//write column headers
			pw.addRow();
			/*Pass the header keys for the Excel.*/
			String[] headerkeys = { "label.kitmsds", "report.label.kitDescription", "label.status","label.kitrevisiondate", "report.label.productCode" , "label.vocdetect", "label.voc", "label.vocupper", "label.vocunit", "label.vocsource", 
					"label.voclwesdetect", "label.voclwes", "label.voclwesupper", "label.voclwesunit", "label.voclwessource","label.specificgravity","label.density","label.physicalstate","label.physicalstatesource", "label.comments",
					"label.componentmsds", "label.componentrevisiondate", "label.mixratioamount", "label.mixratiounit", "label.materialdescription"
					};
			

			/*This array defines the type of the excel column.
			0 means default depending on the data type.
			pw.TYPE_PARAGRAPH defaults to 40 characters.
			pw.TYPE_CALENDAR set up the date with no time.
			pw.TYPE_DATE set up the date with time.*/
			int[] types = {
						   0, pw.TYPE_PARAGRAPH, 0, pw.TYPE_CALENDAR, 0, 0, 0, 0, 0, 0,
						  0, 0, 0, 0, 0, 0, 0, 0, 0, pw.TYPE_PARAGRAPH,
					       0, pw.TYPE_CALENDAR, 0, 0, pw.TYPE_PARAGRAPH
					      };

			/*This array defines the default width of the column when the Excel file opens.
			0 means the width will be default depending on the data type.*/
			int[] widths = { 10, 15, 10, 10, 10 , 10, 10, 10, 10, 10,
							 10, 10, 10, 10, 10, 10, 10, 10, 10, 15, 
							 10, 10,10, 10 , 35
						   };
			/*This array defines the horizontal alignment of the data in a cell.
			0 means excel defaults the horizontal alignemnt by the data type.*/
			int[] horizAligns = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
								  0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
								  0, 0, 0, 0, 0
								};

			pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

			// format the numbers to the special columns
			/*pw.setColumnDigit(5, 2);
			pw.setColumnDigit(6, 2);*/

			for (KitManagementBean member : data) {

				pw.addRow();
				pw.addCell(member.getCustomerMixtureNumber());
				pw.addCell(member.getMixtureDesc());
				pw.addCell("A".equalsIgnoreCase(member.getStatus())?"Active":"Inactive");
				pw.addCell(member.getMixtureRevisionDate());
				pw.addCell(member.getMixtureProductCode());
				pw.addCell(member.getVocDetect());
				pw.addCell(member.getVoc());
				pw.addCell(member.getVocUpper());
				pw.addCell(member.getVocUnit());
			    pw.addCell(member.getVocSource());
			    pw.addCell(member.getVocLwesDetect());
			    pw.addCell(member.getVocLwes());
			    pw.addCell(member.getVocLwesUpper());
			    pw.addCell(member.getVocLwesUnit());
			    pw.addCell(member.getVocLwesSource());
			    pw.addCell(member.getSpecificGravityDisplay());
			    pw.addCell(member.getDensityDisplay());
			    pw.addCell(member.getPhysicalState());
			    pw.addCell(member.getPhysicalStateSource());
			    pw.addCell(member.getRevisionComments());
				pw.addCell(member.getCustomerMsdsNumber());
				pw.addCell(member.getComponentRevisionDate());
				pw.addCell(member.getAmount());
				pw.addCell(member.getSizeUnit());
				pw.addCell(member.getMaterialDesc());

		}
		return pw;
	}


	/*@SuppressWarnings("unchecked")
	public Collection<KitManagementBean> getMfg(KitManagementBean input) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new KitManagementBean());
		return factory.selectQuery("select MFG_DESC as mixture_mfg from manufacturer where (lower(MFG_DESC) like lower('%"+input.getMixtureMfg()+"%') or lower(mfg_short_name) like lower('%"+input.getMixtureMfg()+"%')) and rownum <= 50 order by mfg_desc, mfg_short_name");
	}*/
		
	@SuppressWarnings("unchecked")
	public Collection<KitManagementBean> getKitRevs(KitManagementBean input) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new KitManagementBean());
		return factory.selectQuery(new StringBuilder("select * from (select distinct to_char(mixture_revision_date, 'dd-Mon-YYYY') mixture_revision_date_display, mixture_revision_date, 'history' data_source from mixture_revision_audit where mixture_id = ").append(input.getMixtureId()).append(" union select distinct TO_CHAR (mixture_revision_date, 'dd-Mon-YYYY') mixture_revision_date_display, mixture_revision_date, 'summary' data_source from mixture_revision where mixture_id = ").append(input.getMixtureId()).append(") order by mixture_revision_date desc").toString());
	}
	
	@SuppressWarnings("unchecked")
	public Collection<KitManagementBean> getRevHist(KitManagementBean input) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new KitManagementBean());
		StringBuilder query = new StringBuilder("select mix.*,man.mfg_short_name,");
		query.append("pkg_data.fx_measurement_value_concat(mix.specific_gravity_detect,mix.specific_gravity_lower,mix.specific_gravity_upper,NULL,NULL,mix.specific_gravity_basis, NULL,NULL,NULL,NULL,DECODE(mix.specific_gravity_source, 'calculation', '*', NULL),4) specific_gravity_display,");
		query.append("pkg_data.fx_measurement_value_concat(mix.density_detect,mix.density,mix.density_upper,NULL,mix.density_unit,NULL, NULL,NULL,NULL,NULL,DECODE(mix.density_source, 'calculation', '*', NULL),4) density_display,");
		query.append("pkg_data.fx_measurement_value_concat(mix.voc_detect,mix.voc,mix.voc_upper,NULL,mix.voc_unit,NULL, NULL,NULL,NULL,NULL,DECODE(mix.voc_source, 'calculation', '*', NULL),4) voc_display,");
		query.append("pkg_data.fx_measurement_value_concat(mix.voc_less_h2o_exempt_detect,mix.voc_less_h2o_exempt,mix.voc_less_h2o_exempt_upper,NULL,mix.voc_less_h2o_exempt_unit,NULL, NULL,NULL,NULL,NULL,DECODE(mix.voc_less_h2o_exempt_source, 'calculation', '*', NULL),4) voc_lwes_display ");
		query.append("from mixture_revision mix,manufacturer man where mixture_id = ").append(input.getMixtureId()).append(" and MIX.MIXTURE_MFG_ID = MAN.MFG_ID (+) order by mixture_revision_date desc");
		return factory.selectQuery(query.toString());
	}
	
	@SuppressWarnings("unchecked")
	public Collection<KitManagementBean> getEditHist(KitManagementBean input) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new KitManagementBean());
		StringBuilder query = new StringBuilder("select * from (select mra.last_updated,mra.mixture_desc,MRA.MIXTURE_PRODUCT_CODE,MRA.PHYSICAL_STATE,MRA.PHYSICAL_STATE_SOURCE,fx_personnel_id_to_name(mra.last_updated_by) updated_by_name,man.mfg_short_name, ");
		query.append("pkg_data.fx_measurement_value_concat(mra.specific_gravity_detect,mra.specific_gravity_lower,mra.specific_gravity_upper,NULL,NULL,mra.specific_gravity_basis, NULL,NULL,NULL,NULL,DECODE(mra.specific_gravity_source, 'calculation', '*', NULL),4) specific_gravity_display,");
		query.append("pkg_data.fx_measurement_value_concat(mra.density_detect,mra.density,mra.density_upper,NULL,mra.density_unit,NULL, NULL,NULL,NULL,NULL,DECODE(mra.density_source, 'calculation', '*', NULL),4) density_display,");
		query.append("pkg_data.fx_measurement_value_concat(mra.voc_detect,mra.voc,mra.voc_upper,NULL,mra.voc_unit,NULL, NULL,NULL,NULL,NULL,DECODE(mra.voc_source, 'calculation', '*', NULL),4) voc_display,");
		query.append("pkg_data.fx_measurement_value_concat(mra.voc_less_h2o_exempt_detect,mra.voc_less_h2o_exempt,mra.voc_less_h2o_exempt_upper,NULL,mra.voc_less_h2o_exempt_unit,NULL, NULL,NULL,NULL,NULL,DECODE(mra.voc_less_h2o_exempt_source, 'calculation', '*', NULL),4) voc_lwes_display ");
		query.append("from mixture mra,manufacturer man where mra.mixture_id = ").append(input.getMixtureId()).append(" and mra.MIXTURE_MFG_ID = MAN.MFG_ID (+)");
		query.append(" union select mra.last_updated,mra.mixture_desc,MRA.MIXTURE_PRODUCT_CODE,MRA.PHYSICAL_STATE,MRA.PHYSICAL_STATE_SOURCE,fx_personnel_id_to_name(mra.last_updated_by) updated_by_name,man.mfg_short_name, ");
		query.append("pkg_data.fx_measurement_value_concat(mra.specific_gravity_detect,mra.specific_gravity_lower,mra.specific_gravity_upper,NULL,NULL,mra.specific_gravity_basis, NULL,NULL,NULL,NULL,DECODE(mra.specific_gravity_source, 'calculation', '*', NULL),4) specific_gravity_display,");
		query.append("pkg_data.fx_measurement_value_concat(mra.density_detect,mra.density,mra.density_upper,NULL,mra.density_unit,NULL, NULL,NULL,NULL,NULL,DECODE(mra.density_source, 'calculation', '*', NULL),4) density_display,");
		query.append("pkg_data.fx_measurement_value_concat(mra.voc_detect,mra.voc,mra.voc_upper,NULL,mra.voc_unit,NULL, NULL,NULL,NULL,NULL,DECODE(mra.voc_source, 'calculation', '*', NULL),4) voc_display,");
		query.append("pkg_data.fx_measurement_value_concat(mra.voc_less_h2o_exempt_detect,mra.voc_less_h2o_exempt,mra.voc_less_h2o_exempt_upper,NULL,mra.voc_less_h2o_exempt_unit,NULL, NULL,NULL,NULL,NULL,DECODE(mra.voc_less_h2o_exempt_source, 'calculation', '*', NULL),4) voc_lwes_display ");
		query.append("from mixture_revision_audit mra,manufacturer man where mra.mixture_id = ").append(input.getMixtureId()).append(" and mra.mixture_revision_date  = to_date('").append(new SimpleDateFormat("MM/dd/yyyy").format(input.getMixtureRevisionDate())).append("', 'MM/dd/yyyy')").append(" and mra.MIXTURE_MFG_ID = MAN.MFG_ID (+)) order by last_updated desc");
		return factory.selectQuery(query.toString());
	}
}
	
