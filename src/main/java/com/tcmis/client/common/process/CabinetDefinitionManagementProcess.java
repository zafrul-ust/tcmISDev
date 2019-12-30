package com.tcmis.client.common.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.catalog.beans.CatalogFacilityBean;
import com.tcmis.client.catalog.beans.CatalogInputBean;
import com.tcmis.client.catalog.beans.FacLocAppBean;
import com.tcmis.client.catalog.beans.PointOfSaleAccountSysInfoBean;
import com.tcmis.client.catalog.process.CatalogProcess;
import com.tcmis.client.common.beans.*;
import com.tcmis.client.het.process.PermitManagementProcess;
import com.tcmis.client.het.process.UsageLoggingProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.catalog.beans.ReportingEntityBean;
import com.tcmis.internal.hub.beans.CabinetBinInputBean;
import com.tcmis.internal.hub.beans.CabinetLevelInputBean;
import com.tcmis.internal.hub.beans.CabinetManagementInputBean;
import com.tcmis.internal.hub.beans.CabinetPartLevelLogViewBean;
import com.tcmis.internal.hub.process.CabinetLevelProcess;

/******************************************************************************
 * Process for CabinetDefinitionManagementProcess
 * @version 1.0
 *
 *****************************************************************************/
public class CabinetDefinitionManagementProcess extends GenericProcess {

	Log log = LogFactory.getLog(this.getClass());


	public CabinetDefinitionManagementProcess(String client) {
		super(client);
	}

	public CabinetDefinitionManagementProcess(String client,String locale)  {
		super(client,locale);
	}

	public String checkUserPermissionByWorkArea(WorkAreaSearchTemplateInputBean inputBean, PersonnelBean personnelBean, String userGroupId) throws BaseException {
		String result = "N";
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);
		try {
				StringBuilder applicationIdString = new StringBuilder("");
				String[] cabinetIdArray =  inputBean.getApplicationId().split("\\|");
				for (int i = 0; i < cabinetIdArray.length; i++) {
					if (cabinetIdArray[i].length() > 0) {
						//don't add the commas at the end if this is the last element
						if (i == cabinetIdArray.length -1) {
							applicationIdString.append(cabinetIdArray[i]);
						} else {
							applicationIdString.append(cabinetIdArray[i]).append(",");
						}
					}
				}
			StringBuilder query = new StringBuilder("select count(*) from (");
			if (applicationIdString.length() > 0) {
				query.append("select ugma.application from user_group_member_application ugma, fac_loc_app fla");
				query.append(" where ugma.company_id = fla.company_id and ugma.facility_id = fla.facility_id");
				query.append(" and ugma.application = fla.application and ugma.user_group_id = '").append(userGroupId).append("'");
				query.append(" and ugma.company_id = '").append(inputBean.getCompanyId()).append("' and ugma.facility_id = '").append(inputBean.getFacilityId()).append("'");
				query.append(" and ugma.personnel_id = ").append(personnelBean.getPersonnelId()).append(" and ugma.user_group_id = '").append(userGroupId).append("'");
				query.append(" and fla.application_id in (").append(applicationIdString).append(")");
				query.append(" union all");
			}
			query.append(" select application from user_group_member_application");
			query.append(" where company_id = '").append(inputBean.getCompanyId()).append("' and facility_id = '").append(inputBean.getFacilityId()).append("' and application = 'All'");
			query.append(" and personnel_id = ").append(personnelBean.getPersonnelId()).append(" and user_group_id = '").append(userGroupId).append("'");
			query.append(")");
			if (!"0".equals(genericSqlFactory.selectSingleValue(query.toString()))) {
				result = "Y";
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "N";
		}
		return result;
	} //end of method

	public String addChargeNumberFromSetup(CatalogInputBean inputBean, PersonnelBean personnelBean) throws BaseException {
		String result = "OK";
		DbManager dbManager = new DbManager(getClient(), getLocale());
		Connection connection = dbManager.getConnection();
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);
		try {
			if (!StringHandler.isBlankString(inputBean.getChargeNumbers())) {
				AccountSysBean accountSysBean = new AccountSysBean();
				accountSysBean.setCompanyId(inputBean.getCompanyId());
                accountSysBean.setDescription(inputBean.getDescription());

                MaterialRequestProcess mrProcess = new MaterialRequestProcess(this.getClient(),this.getLocale(),"");
				Collection prAccountColl = mrProcess.getChargeNumberFromString(inputBean.getChargeNumbers());
				Iterator iter = prAccountColl.iterator();
				while (iter.hasNext()) {
					PrAccountBean prAccountBean = (PrAccountBean)iter.next();
					Hashtable dataH = new Hashtable(4);
					Hashtable innerH = new Hashtable(4);
					innerH.put(0,(prAccountBean.getAccountNumber() == null ?"":prAccountBean.getAccountNumber()));
					innerH.put(1,(prAccountBean.getAccountNumber2() == null ?"":prAccountBean.getAccountNumber2()));
					innerH.put(2,(prAccountBean.getAccountNumber3() == null ?"":prAccountBean.getAccountNumber3()));
					innerH.put(3,(prAccountBean.getAccountNumber4() == null ?"":prAccountBean.getAccountNumber4()));
					dataH.put(inputBean.getChargeId(),innerH);
					result = insertChargeNumber(personnelBean,genericSqlFactory,connection,dataH,accountSysBean);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "failed";
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		return result;
	} //end of method

	public String addNewChargeNumber(CatalogInputBean inputBean, PersonnelBean personnelBean) throws BaseException {
		String result = "OK";
		DbManager dbManager = new DbManager(getClient(), getLocale());
		Connection connection = dbManager.getConnection();
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);
		try {
			if (!StringHandler.isBlankString(inputBean.getChargeNumbers())) {
				StringBuilder query = new StringBuilder("select * from account_sys where account_sys_id = '").append(inputBean.getAccountSysId());
				query.append("' and charge_type = '").append(inputBean.getChargeType()).append("' and company_id = '").append(inputBean.getCompanyId()).append("'");
				genericSqlFactory.setBeanObject(new AccountSysBean());
				Collection accountSysColl = genericSqlFactory.selectQuery(query.toString(),connection);
				Iterator iter = accountSysColl.iterator();
				AccountSysBean accountSysBean = new AccountSysBean();
				while (iter.hasNext()) {
					accountSysBean = (AccountSysBean) iter.next();
					break;
				}

				String[] chargeIdArray = new String[4];
				chargeIdArray[0] = accountSysBean.getChargeId1();
				chargeIdArray[1] = accountSysBean.getChargeId2();
				chargeIdArray[2] = accountSysBean.getChargeId3();
				chargeIdArray[3] = accountSysBean.getChargeId4();

				MaterialRequestProcess mrProcess = new MaterialRequestProcess(this.getClient(),this.getLocale(),"");
				Collection prAccountColl = mrProcess.getChargeNumberFromString(inputBean.getChargeNumbers());
				iter = prAccountColl.iterator();
				while (iter.hasNext()) {
					PrAccountBean prAccountBean = (PrAccountBean)iter.next();
					Hashtable dataH = new Hashtable(4);
					for (int i = 0; i < chargeIdArray.length; i++) {
						String tmpChargeId = chargeIdArray[i];
						if (!StringHandler.isBlankString(tmpChargeId)) {
							if (dataH.containsKey(tmpChargeId)) {
								Hashtable innerH = (Hashtable)dataH.get(tmpChargeId);
								String tmpChargeNumber = "";
								if (i == 0) {
									tmpChargeNumber = prAccountBean.getAccountNumber();
								}else if (i == 1) {
									tmpChargeNumber = prAccountBean.getAccountNumber2();
								}else if (i == 2) {
									tmpChargeNumber = prAccountBean.getAccountNumber3();
								}else if (i == 3) {
									tmpChargeNumber = prAccountBean.getAccountNumber4();
								}
								innerH.put(innerH.size(),(tmpChargeNumber == null ? "":tmpChargeNumber));
							}else {
								Hashtable innerH = new Hashtable(4);
								String tmpChargeNumber = "";
								if (i == 0) {
									tmpChargeNumber = prAccountBean.getAccountNumber();
								}else if (i == 1) {
									tmpChargeNumber = prAccountBean.getAccountNumber2();
								}else if (i == 2) {
									tmpChargeNumber = prAccountBean.getAccountNumber3();
								}else if (i == 3) {
									tmpChargeNumber = prAccountBean.getAccountNumber4();
								}
								//since this gets call only if charge id change
								innerH.put(0,(tmpChargeNumber == null ? "":tmpChargeNumber));
								dataH.put(tmpChargeId,innerH);
							}
						}
					} //end of chargeId loop
					insertChargeNumber(personnelBean,genericSqlFactory,connection,dataH,accountSysBean);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "failed";
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		return result;
	} //end of method


	private String insertChargeNumber(PersonnelBean personnelBean,GenericSqlFactory factory, Connection connection, Hashtable dataH, AccountSysBean accountSysBean) {
		String result = "Failed";
		try {
			StringBuilder headerQ = new StringBuilder("insert into charge_number (charge_id,charge_number_1,charge_number_2,charge_number_3");
			headerQ.append(",charge_number_4,status,date_inserted,description,sales_tax_id,company_id,last_updated_by,last_updated_on,update_insert_comment)");
		    Enumeration enumer = dataH.keys();
			while(enumer.hasMoreElements()) {
				String key = (String)enumer.nextElement();
				Hashtable innerH = (Hashtable)dataH.get(key);
				StringBuilder value = new StringBuilder("'").append(key).append("'");
				StringBuilder existingQ = new StringBuilder("select count(*) from charge_number where charge_id = '").append(key);
				existingQ.append("' and company_id = '").append(accountSysBean.getCompanyId()).append("'");
				boolean hasChargeNumber = false;
				for (int i = 0; i < 4; i++) {
					String tmpChargeNumber = (String)innerH.get(i);
					if (!StringHandler.isBlankString(tmpChargeNumber)) {
						value.append(",").append(SqlHandler.delimitString(tmpChargeNumber.trim()));
						hasChargeNumber = true;
					}else {
						value.append(",''");
					}
					//logic for existing data
					if (i == 0) {
						if (!StringHandler.isBlankString(tmpChargeNumber)) {
							existingQ.append(" and lower(charge_number_1) = ").append(SqlHandler.delimitString(tmpChargeNumber.toLowerCase()));
						}else {
							existingQ.append(" and charge_number_1 is null");
						}
					}else if (i == 1) {
						if (!StringHandler.isBlankString(tmpChargeNumber)) {
							existingQ.append(" and lower(charge_number_2) = ").append(SqlHandler.delimitString(tmpChargeNumber.toLowerCase()));
						}else {
							existingQ.append(" and charge_number_2 is null");
						}
					}else if (i == 2) {
						if (!StringHandler.isBlankString(tmpChargeNumber)) {
							existingQ.append(" and lower(charge_number_3) = ").append(SqlHandler.delimitString(tmpChargeNumber.toLowerCase()));
						}else {
							existingQ.append(" and charge_number_3 is null");
						}
					}else if (i == 3) {
						if (!StringHandler.isBlankString(tmpChargeNumber)) {
							existingQ.append(" and lower(charge_number_4) = ").append(SqlHandler.delimitString(tmpChargeNumber.toLowerCase()));
						}else {
							existingQ.append(" and charge_number_4 is null");
						}
					} //end of existing logic
				} //end of for loop

				//only insert data if does not exist
				if ("0".equalsIgnoreCase(factory.selectSingleValue(existingQ.toString(),connection))) {
					value.append(",'A',sysdate,").append((StringHandler.isBlankString(accountSysBean.getDescription())?"null":"'" + accountSysBean.getDescription() + "'")).append(", null,'").append(accountSysBean.getCompanyId()).append("'");
					value.append(",").append(personnelBean.getPersonnelId()).append(",sysdate,null");
					if (hasChargeNumber) {
						factory.deleteInsertUpdate(headerQ.toString()+" values("+value.toString()+")",connection);
					}
					result = "OK";
				}
				else
					result = "DUP";
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	} //end of method

	public String workAreaHasDirectedCharge(CatalogInputBean inputBean) throws BaseException {
		String result = "Directed charge is empty";
		DbManager dbManager = new DbManager(getClient(), getLocale());
		Connection connection = dbManager.getConnection();
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);
		try {
			StringBuilder query = new StringBuilder("select distinct account_sys_id from pr_rules where company_id = '").append(inputBean.getCompanyId()).append("'");
			query.append(" and facility_id = '").append(inputBean.getFacilityId()).append("' and status = 'A'");
			genericSqlFactory.setBeanObject(new PrRulesViewBean());
			Iterator iter = genericSqlFactory.selectQuery(query.toString(),connection).iterator();
			while (iter.hasNext()) {
				PrRulesViewBean bean = (PrRulesViewBean)iter.next();
				query = new StringBuilder("select count(*) from table (pkg_directed_charge_util.fx_get_directed_charges('").append(inputBean.getCompanyId()).append("','");
				query.append(inputBean.getFacilityId()).append("','").append(inputBean.getApplicationId()).append("','");
				if (!StringHandler.isBlankString(inputBean.getUseApplication())) {
					query.append(inputBean.getUseApplication()).append("','");
				}else {
					query.append("','");
				}
				query.append(bean.getAccountSysId());
				query.append("','','Material'");
				query.append(",''");
				query.append(",''");
				query.append(",''");
				query.append(",''");
				query.append("))");
				if (!"0".equals(genericSqlFactory.selectSingleValue(query.toString(),connection))) {
					result = "Directed Charge found";
					break;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		return result;
	} //end of method

	public void getDirectedChargeInfo(PointOfSaleAccountSysInfoBean posBean, CatalogInputBean inputBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		Connection connection = dbManager.getConnection();
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);
		try {
			genericSqlFactory.setBeanObject(new DirectedChargeBean());
			StringBuilder query = new StringBuilder("select * from table (pkg_directed_charge_util.fx_get_directed_charges('").append(inputBean.getCompanyId()).append("','");
			query.append(inputBean.getFacilityId()).append("','").append(inputBean.getApplicationId()).append("','");
			if (!StringHandler.isBlankString(inputBean.getUseApplication())) {
				query.append(inputBean.getUseApplication()).append("','");
			}else {
				query.append("','");
			}
			query.append(inputBean.getAccountSysId());
			query.append("','','Material'");
			if (!StringHandler.isBlankString(inputBean.getCatalogCompanyId())) {
				query.append(",'").append(inputBean.getCatalogCompanyId()).append("'");
			}else {
				query.append(",''");
			}
			if (!StringHandler.isBlankString(inputBean.getCatalogId())) {
				query.append(",'").append(inputBean.getCatalogId()).append("'");
			}else {
				query.append(",''");
			}
			if (!StringHandler.isBlankString(inputBean.getCatalogAddCatPartNo())) {
				query.append(",'").append(inputBean.getCatalogAddCatPartNo()).append("'");
			}else {
				query.append(",''");
			}
			if (!StringHandler.isBlankString(inputBean.getCatalogAddPartGroupNo())) {
				query.append(",").append(inputBean.getCatalogAddPartGroupNo());
			}else {
				query.append(",''");
			}

			query.append(")) order by charge_type");       //the reason I am using this is because cat_part_no is an array
			Collection directedChargeNumberColl = genericSqlFactory.selectQuery(query.toString(),connection);
			Iterator iter = directedChargeNumberColl.iterator();
			Collection directedChargeNumberDColl = new Vector(0);
			Collection directedChargeNumberIColl = new Vector(0);
			String poNumberD = "";
			String poLineD = "";
			String poNumberI = "";
			String poLineI = "";
			while (iter.hasNext()) {
				DirectedChargeBean bean = (DirectedChargeBean)iter.next();
				DirectedChargeBean newBean = new DirectedChargeBean();
				BeanHandler.copyAttributes(bean,newBean);
				if ("d".equals(bean.getChargeType())) {
					if (StringHandler.isBlankString(poNumberD)) {
						poNumberD = bean.getPoNumber();
						poLineD = bean.getPoLine();
					}
					directedChargeNumberDColl.add(newBean);
				}else {
					if (StringHandler.isBlankString(poNumberI)) {
						poNumberI = bean.getPoNumber();
						poLineI = bean.getPoLine();
					}
					directedChargeNumberIColl.add(newBean);
				}
			}
			posBean.setDirectedChargeForDirectColl(directedChargeNumberDColl);
			posBean.setDirectedChargeForIndirectColl(directedChargeNumberIColl);
			if (directedChargeNumberDColl.size() > 0 || directedChargeNumberIColl.size() > 0) {
				if (directedChargeNumberDColl.size() > 0) {
					posBean.setSelectedChargeType("d");
				}else {
					posBean.setSelectedChargeType("i");
				}
			}

			//dealing with po number with charge_type = d
			if (posBean.getFacAccountSysPoForDirectColl() != null && !StringHandler.isBlankString(poNumberD)) {
				iter = posBean.getFacAccountSysPoForDirectColl().iterator();
				boolean poFound = false;
				while (iter.hasNext()) {
					FacAccountSysPoBean poBean = (FacAccountSysPoBean)iter.next();
					if (poNumberD.equalsIgnoreCase(poBean.getPoNumber())) {
						poFound = true;
						break;
					}
				}
				//add poNumberD to list if not in list
				if (!poFound) {
					FacAccountSysPoBean poBean = new FacAccountSysPoBean();
					poBean.setPoNumber(poNumberD);
					posBean.getFacAccountSysPoForDirectColl().add(poBean);
				}
			}
			if (!StringHandler.isBlankString(poNumberD)) {
				posBean.setPoNumberForDirect(poNumberD);
			}
			posBean.setPoLineForDirect(poLineD);

			//dealing with po number with charge_type = i
			if (posBean.getFacAccountSysPoForIndirectColl() != null && !StringHandler.isBlankString(poNumberI)) {
				iter = posBean.getFacAccountSysPoForIndirectColl().iterator();
				boolean poFound = false;
				while (iter.hasNext()) {
					FacAccountSysPoBean poBean = (FacAccountSysPoBean)iter.next();
					if (poNumberI.equalsIgnoreCase(poBean.getPoNumber())) {
						poFound = true;
						break;
					}
				}
				//add poNumberD to list if not in list
				if (!poFound) {
					FacAccountSysPoBean poBean = new FacAccountSysPoBean();
					poBean.setPoNumber(poNumberI);
					posBean.getFacAccountSysPoForIndirectColl().add(poBean);
				}
			}
			if (!StringHandler.isBlankString(poNumberI)) {
				posBean.setPoNumberForIndirect(poNumberI);
			}
			posBean.setPoLineForIndirect(poLineI);

			//getting fac_loc_app data
			String facLocAppDirectedChargeAllowNull = "N";
			String chargeTypeDefault = "";
			query = new StringBuilder("select * from fac_loc_app where company_id = '").append(inputBean.getCompanyId()).append("'");
			query.append(" and facility_id = '").append(inputBean.getFacilityId()).append("' and application = '").append(inputBean.getApplicationId()).append("'");
			genericSqlFactory.setBeanObject(new FacLocAppBean());
			Iterator flaIter = genericSqlFactory.selectQuery(query.toString(),connection).iterator();
			while (flaIter.hasNext()) {
				FacLocAppBean flaBean = (FacLocAppBean)flaIter.next();
				chargeTypeDefault = flaBean.getChargeTypeDefault();
				facLocAppDirectedChargeAllowNull = flaBean.getDirectedChargeAllowNull();
				break;
			}
			//check to see if user can enter data for both charge type or not
			if("part".equalsIgnoreCase(inputBean.getSearchText())) {
				posBean.setAllowBothChargeType("N");
			}else {
				if (!StringHandler.isBlankString(chargeTypeDefault)) {
					posBean.setAllowBothChargeType("Y");
				}else {
					posBean.setAllowBothChargeType("N");
				}
			}
			posBean.setFacLocAppDirectedChargeAllowNull(facLocAppDirectedChargeAllowNull);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
	} //end of method

	public String deleteDirectedCharge(CatalogInputBean inputBean) throws BaseException {
		String result = "OK";
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);
		try {
			StringBuilder query = getDeleteDirectedChargeString(inputBean);
			genericSqlFactory.deleteInsertUpdate(query.toString());
		} catch (Exception e) {
			e.printStackTrace();
			result = "failed";
		}
		return result;
	}

	private StringBuilder getDeleteDirectedChargeString(CatalogInputBean inputBean) {
		StringBuilder query = new StringBuilder("delete from directed_charge where company_id = '").append(inputBean.getCompanyId());
		query.append("' and facility_id = '").append(inputBean.getFacilityId()).append("' and application = '").append(inputBean.getApplicationId()).append("'");
		if ("part".equalsIgnoreCase(inputBean.getSearchText())) {
			//directed by part
			query.append(" and catalog_company_id = '").append(inputBean.getCatalogCompanyId()).append("' and catalog_id = '").append(inputBean.getCatalogId());
			query.append("' and cat_part_no = '").append(inputBean.getCatalogAddCatPartNo()).append("'");  //the reason I am using this is because cat_part_no is an array
		}else {
			query.append(" and charge_type = '").append(inputBean.getChargeType()).append("'");
			query.append(" and catalog_company_id is null and catalog_id is null and cat_part_no is null");
		}
		return query;
	}

	public String setWorkAreaCabinetPartDirectedCharge(CatalogInputBean inputBean) throws BaseException {
		String result = "OK";
		DbManager dbManager = new DbManager(getClient(), getLocale());
		Connection connection = dbManager.getConnection();
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);
		try {
			//first delete up existing data
			StringBuilder query = getDeleteDirectedChargeString(inputBean);
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);

			//insert new data
			query = new StringBuilder("insert into directed_charge (company_id,facility_id,application,account_sys_id,charge_type");
			StringBuilder value = new StringBuilder(" values('").append(inputBean.getCompanyId()).append("','").append(inputBean.getFacilityId());
			value.append("','").append(inputBean.getApplicationId());
			value.append("','").append(inputBean.getAccountSysId()).append("','").append(inputBean.getChargeType()).append("'");
			if (!StringHandler.isBlankString(inputBean.getPoNumber())) {
				query.append(",po_number");
				value.append(",").append(SqlHandler.delimitString(inputBean.getPoNumber()));
			}
			if (!StringHandler.isBlankString(inputBean.getPoLine())) {
				query.append(",po_line");
				value.append(",").append(SqlHandler.delimitString(inputBean.getPoLine()));
			}
			if (!StringHandler.isBlankString(inputBean.getCatalogCompanyId())) {
				query.append(",catalog_company_id");
				value.append(",'").append(inputBean.getCatalogCompanyId()).append("'");
			}
			if (!StringHandler.isBlankString(inputBean.getCatalogId())) {
				query.append(",catalog_id");
				value.append(",'").append(inputBean.getCatalogId()).append("'");
			}
			//the reason I am using this is because cat_part_no is an array
			if (!StringHandler.isBlankString(inputBean.getCatalogAddCatPartNo())) {
				query.append(",cat_part_no");
				value.append(",'").append(inputBean.getCatalogAddCatPartNo()).append("'");
				if (inputBean.getCatalogAddPartGroupNo() != null) {
					query.append(",part_group_no");
					value.append(",").append(inputBean.getCatalogAddPartGroupNo());
				}else {
					query.append(",part_group_no");
					value.append(",").append("1");
				}
			}


			if (!StringHandler.isBlankString(inputBean.getChargeNumbers())) {
				MaterialRequestProcess mrProcess = new MaterialRequestProcess(this.getClient(),this.getLocale(),"");
				Collection prAccountColl = mrProcess.getChargeNumberFromString(inputBean.getChargeNumbers());
				Iterator iter = prAccountColl.iterator();
				while (iter.hasNext()) {
					StringBuilder innerQuery = new StringBuilder("");
					innerQuery.append(query);
					StringBuilder innerValue = new StringBuilder("");
					innerValue.append(value);
					PrAccountBean prAccountBean = (PrAccountBean)iter.next();
					if (!StringHandler.isBlankString(prAccountBean.getAccountNumber())) {
						innerQuery.append(",charge_number_1");
						innerValue.append(",").append(SqlHandler.delimitString(prAccountBean.getAccountNumber()));
					}
					if (!StringHandler.isBlankString(prAccountBean.getAccountNumber2())) {
						innerQuery.append(",charge_number_2");
						innerValue.append(",").append(SqlHandler.delimitString(prAccountBean.getAccountNumber2()));
					}
					if (!StringHandler.isBlankString(prAccountBean.getAccountNumber3())) {
						innerQuery.append(",charge_number_3");
						innerValue.append(",").append(SqlHandler.delimitString(prAccountBean.getAccountNumber3()));
					}
					if (!StringHandler.isBlankString(prAccountBean.getAccountNumber4())) {
						innerQuery.append(",charge_number_4");
						innerValue.append(",").append(SqlHandler.delimitString(prAccountBean.getAccountNumber4()));
					}
					if (prAccountBean.getPercentage() != null) {
						innerQuery.append(",percent");
						innerValue.append(",").append(prAccountBean.getPercentage());
					}
					innerQuery.append(")").append(innerValue.append(")"));
					genericSqlFactory.deleteInsertUpdate(innerQuery.toString(),connection);
				}
			}else {
				query.append(")").append(value.append(")"));
				genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
			}

		} catch (Exception e) {
			e.printStackTrace();
			result = "failed";
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		return result;
	}

	public Collection getCatalogs(CabinetBinInputBean inputBean) throws BaseException, Exception {
		CatalogProcess catalogProcess = new CatalogProcess(getClient(), getLocale());
		CatalogFacilityBean tmpBean = new CatalogFacilityBean();
		tmpBean.setCompanyId(inputBean.getCompanyId());
		tmpBean.setCatalogCompanyId(inputBean.getCatalogCompanyId());
		tmpBean.setCatalogId(inputBean.getCatalogId());
		tmpBean.setFacilityId(inputBean.getFacilityId());
		return catalogProcess.getCatalogFacility(tmpBean);
	}

	public String updateCabinetBinPartInventory(Collection<CabinetManagementBean> updateBeanColl, PersonnelBean personnelBean) throws BaseException, Exception {
		String errMsg = null;
        ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());

        DbManager dbManager = new DbManager(getClient(), getLocale());
		Connection connection = dbManager.getConnection();
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);
		GenericProcedureFactory gpFactory = new GenericProcedureFactory(dbManager);
		CabinetLevelProcess cabinetLevelProcess = new CabinetLevelProcess(getClient());
		try {
			Vector cabinetManagementBeanUpdateV = new Vector(updateBeanColl.size());
			String lastKey = "";
			for (CabinetManagementBean updateBean : updateBeanColl) {
				//if not nonmanaged material and some data changed
				if (!"Y".equals(updateBean.getNonManaged()) &&  (!updateBean.getBinPartStatus().equalsIgnoreCase(updateBean.getOldBinPartStatus()) ||
						"Y".equals(updateBean.getChanged()) ||
						(updateBean.getCountType() != null && !updateBean.getCountType().equalsIgnoreCase(updateBean.getOldCountType())))) {
					//handling cabinet_part_inventory_item update/insert
					updateInsertCabinetPartInventoryItem(updateBean,genericSqlFactory,connection,personnelBean);
					//update cabinet_part_inventory - status and other fields
					//so that we don't have to update the same bin multiple times because of merge rows.
					StringBuilder key = new StringBuilder(updateBean.getCompanyId()).append(updateBean.getFacilityId()).append(updateBean.getCabinetId()).append(updateBean.getBinId()).append(updateBean.getCatPartNo());
					if (!lastKey.equals(key)) {
						cabinetManagementBeanUpdateV.add(updateBean);
					}
					lastKey = key.toString();
				}else if ("Y".equals(updateBean.getNonManaged()) && "Y".equals(updateBean.getChanged())) {
					//errMsg = deleteNonManagedMaterial(updateBean);
					errMsg = insertNonManagedMaterial(updateBean, personnelBean, gpFactory, connection);
				}
			} //end of loop

			//now update parts status
			for (int i = 0 ; i < cabinetManagementBeanUpdateV.size(); i++) {
				CabinetManagementBean curBean = (CabinetManagementBean)cabinetManagementBeanUpdateV.elementAt(i);
				String tmpErrorVal = updateCabinetPartInventory(curBean,genericSqlFactory,connection,personnelBean,library,"");				
                if (!StringHandler.isBlankString(tmpErrorVal)) {
                	if (tmpErrorVal.equalsIgnoreCase("ERROR")){
                		tmpErrorVal = library.getString("label.updatebinerror");
                	}
                	if (StringHandler.isBlankString(errMsg)) {
                		errMsg = "";
                	}
                	errMsg += tmpErrorVal+"(:"+curBean.getCatPartNo()+")\n";                	
                }else {
                    if(!curBean.getBinPartStatus().equalsIgnoreCase(curBean.getOldBinPartStatus()))
                    {
                        CabinetLevelInputBean cabinetLevelInputBean = new CabinetLevelInputBean();
                        BeanHandler.copyAttributes(curBean, cabinetLevelInputBean);
                        cabinetLevelProcess.setUserGroupNotification(cabinetLevelInputBean, personnelBean, "Status Update");
                    }
                }
            }
		} catch (Exception e) {
			e.printStackTrace();
			errMsg = library.getString("msg.tcmiserror");
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
			gpFactory = null;
		}
		return errMsg;
	}  //end of method

	private void updateInsertCabinetPartInventoryItem(CabinetManagementBean updateBean, GenericSqlFactory genericSqlFactory, Connection connection, PersonnelBean personnelBean) throws Exception{
		try {
			if ("NotCounted".equals(updateBean.getCountType())) {
				Collection inArgs = new Vector(10);
				inArgs.add(updateBean.getCompanyId());
				inArgs.add(updateBean.getCatalogCompanyId());
				inArgs.add(updateBean.getCatalogId());
				inArgs.add(updateBean.getCatPartNo());
				inArgs.add(updateBean.getPartGroupNo());
				inArgs.add(updateBean.getFacilityId());
				inArgs.add(updateBean.getApplication());
				inArgs.add(updateBean.getItemId());
				inArgs.add(updateBean.getAvgAmount());
				inArgs.add(updateBean.getMaxAmount());
				Collection outArgs = new Vector(1);
				outArgs.add(new Integer(java.sql.Types.VARCHAR));
                Collection opArgs = new Vector(2);
                opArgs.add(personnelBean.getPersonnelId());
                opArgs.add(updateBean.getBinPartStatus());
                Collection data = genericSqlFactory.doProcedure(connection,"PKG_STOCK_MGMT.P_UPSERT_CABINET_PART_INV_ITEM",inArgs,outArgs,opArgs);
				Iterator i11 = data.iterator();
				String val = "";
				while (i11.hasNext()) {
					val = (String) i11.next();
				}
				if (!StringHandler.isBlankString(val)) {
					if (!"OK".equalsIgnoreCase(val)) {
						MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","PKG_STOCK_MGMT.P_UPSERT_CABINET_PART_INV_ITEM","Error while executing PKG_STOCK_MGMT.P_UPSERT_CABINET_PART_INV_ITEM: "+val);
					}
				}
			}else if ("NotCounted".equals(updateBean.getOldCountType())) {
                //update status to inactive
                StringBuilder query = new StringBuilder("update cabinet_part_inventory_item set status = 'I' where company_id = '").append(updateBean.getCompanyId()).append("'");
				query.append(" and catalog_company_id = '").append(updateBean.getCatalogCompanyId()).append("' and catalog_id = '").append(updateBean.getCatalogId()).append("'");
				query.append(" and cat_part_no = '").append(updateBean.getCatPartNo()).append("' and part_group_no = ").append(updateBean.getPartGroupNo());
				query.append(" and facility_id = '").append(updateBean.getFacilityId()).append("' and application = '").append(updateBean.getApplication()).append("'");
				query.append(" and item_id = ").append(updateBean.getItemId());
				genericSqlFactory.deleteInsertUpdate(query.toString(),connection);

            }
		}catch(Exception e) {
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","PKG_STOCK_MGMT.P_UPSERT_CABINET_PART_INV_ITEM error","Error while executing PKG_STOCK_MGMT.P_UPSERT_CABINET_PART_INV_ITEM: "+updateBean.toString());
			throw e;
		}
	}   //end of method

	public String updateCabinetPartInventory(CabinetManagementBean bean, PersonnelBean personnelBean) throws BaseException, Exception {
		String errMsg = null;
        ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());

        DbManager dbManager = new DbManager(getClient(), getLocale());
		Connection connection = dbManager.getConnection();
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);
		GenericProcedureFactory gpFactory = new GenericProcedureFactory(dbManager);
		try {
			//now update parts status
			String tmpErrorVal = updateCabinetPartInventory(bean,genericSqlFactory,connection,personnelBean,library,"");
            if (!StringHandler.isBlankString(tmpErrorVal)) {
                errMsg += tmpErrorVal+" ("+library.getString("binid")+": "+bean.getBinId()+")\n";
            }
		} catch (Exception e) {
			e.printStackTrace();
			errMsg = library.getString("msg.tcmiserror");
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
			gpFactory = null;
		}
		return errMsg;
	}  //end of method

    private String updateCabinetPartInventory(CabinetManagementBean bean, GenericSqlFactory genericSqlFactory, Connection connection, PersonnelBean personnelBean, ResourceLibrary library, String homeCompanyOwned) {
		String errorCode = "";
        try {
            Collection inArgs = buildProcedureInput(bean.getBinId(),personnelBean.getPersonnelId(),bean.getReorderPoint(),bean.getStockingLevel(),bean.getReorderQuantity(),
                                                    bean.getKanbanReorderQuantity(),bean.getLeadTimeDays(),bean.getBinPartStatus(),bean.getCountType(),
                                                    bean.getDefaultPartType(),bean.getDefaultPermitId(),bean.getDefaultApplicationMethodCod(),
                                                    bean.getDefaultSubstrateCode(),bean.getTierIITemperature(),"",bean.getBinName(),"N",homeCompanyOwned);
            Vector outArgs = new Vector(1);
            outArgs.add(new Integer(java.sql.Types.VARCHAR));
            Vector optArgs = new Vector(4);
            optArgs.add(bean.getLevelHoldEndDate());        
            optArgs.add(bean.getPutAwayMethodOverride());
            String dropShipOverride = "N";
            if ("true".equals(bean.getDropShipOverride()) || "Y".equals(bean.getDropShipOverride()))
                dropShipOverride = "Y";
            optArgs.add(dropShipOverride);              //drop_ship_override
            optArgs.add(bean.getBinSizeUnit());
            Vector error = (Vector) genericSqlFactory.doProcedure(connection,"PKG_WORK_AREA_MANAGEMENT.P_UPDATE_CABINET", inArgs, outArgs, optArgs);
            log.debug(error.toString()+"-"+error.get(0));
            if (error.size() > 0 && error.get(0) != null && !"ok".equalsIgnoreCase((String) error.get(0))) {
                errorCode = library.getString((String) error.get(0));
            }
		}catch(Exception e) {
            errorCode = library.getString("generic.error");
            log.error(e);
        }
        return errorCode;
    } //end of method

	public String insertNonManagedMaterial(CabinetManagementBean updateBean, PersonnelBean personnelBean) throws Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		Connection connection = dbManager.getConnection();
		GenericProcedureFactory gpFactory = new GenericProcedureFactory(dbManager);
		String errorCode = null;
		try {
			errorCode = insertNonManagedMaterial(updateBean,personnelBean,gpFactory,connection);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			gpFactory = null;
		}
		return errorCode;
	}

	public String insertNonManagedMaterial(CabinetManagementBean updateBean, PersonnelBean personnelBean, GenericProcedureFactory gpFactory, Connection connection) throws Exception {
		Collection outArgs = null;
		String errorCode = null;
		try {
            log.debug(updateBean.toString());
            String tmpMsdsNumber = updateBean.getMsdsNumber();
			if (StringHandler.isBlankString(tmpMsdsNumber)) {
                if (!StringHandler.isBlankString(updateBean.getMsdsString())) {
                    String[] tmpVal = updateBean.getMsdsString().split(";");
                    tmpMsdsNumber = tmpVal[0].trim();
                }
			}
            if (StringHandler.isBlankString(tmpMsdsNumber) && updateBean.getMaterialId() != null) {
                tmpMsdsNumber = updateBean.getMaterialId().toString();
			}
            if (StringHandler.isBlankString(tmpMsdsNumber)) {
                 if (!StringHandler.isBlankString(updateBean.getMaterialIdString())) {
                    String[] tmpVal = updateBean.getMaterialIdString().split(";");
                    tmpMsdsNumber = tmpVal[0].trim();
                }
			}

            if("SELECT".equals(updateBean.getDefaultPartType()))  updateBean.setDefaultPartType("");
			if("SELECT".equals(updateBean.getDefaultPermitId()))  updateBean.setDefaultPermitId("");
			if("SELECT".equals(updateBean.getDefaultApplicationMethodCod()))  updateBean.setDefaultApplicationMethodCod("");
			if("SELECT".equals(updateBean.getDefaultSubstrateCode()))  updateBean.setDefaultSubstrateCode("");

			Collection inArgs = buildProcedureInput(updateBean.getCompanyId(), updateBean.getFacilityId(), updateBean.getCabinetId(),
					updateBean.getMaterialId(),updateBean.getAvgAmount(),updateBean.getMaxAmount(), updateBean.getSizeUnit(),
					updateBean.getBinPartStatus(), updateBean.getCatalogCompanyId(), updateBean.getCatalogId(),
					personnelBean.getPersonnelId(), tmpMsdsNumber, (updateBean.getDefaultPartType() != null ? updateBean.getDefaultPartType() :""),
					(updateBean.getDefaultPermitId()!= null ? updateBean.getDefaultPermitId() :""), (updateBean.getDefaultApplicationMethodCod()!= null ? updateBean.getDefaultApplicationMethodCod() :""),(updateBean.getDefaultSubstrateCode()!= null ? updateBean.getDefaultSubstrateCode() :""),
					StringHandler.emptyIfNull(updateBean.getTierIIStorage()),StringHandler.emptyIfNull(updateBean.getTierIITemperature()),updateBean.getLargestContainerSize());

			outArgs = new Vector(1);
			outArgs.add(new Integer(java.sql.Types.VARCHAR));

			if (log.isDebugEnabled()) {
				log.debug("Input Args for PKG_STOCK_MGMT.P_NONMANAGED_FLA_MATL_INSERT:" + inArgs);
			}
			Vector error = (Vector) gpFactory.doProcedure(connection,"PKG_STOCK_MGMT.P_NONMANAGED_FLA_MATL_INSERT", inArgs, outArgs);
			if (error.size() > 0 && error.get(0) != null && !"ok".equalsIgnoreCase((String) error.get(0))) {
				errorCode = (String) error.get(0);
				log.info("error inserting: " + updateBean.getMaterialId());
			}
		} catch (Exception ex) {
			throw ex;
		}

		return errorCode;
	}

	public String deleteNonManagedMaterial(CabinetManagementBean updateBean) throws Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericProcedureFactory gpFactory = new GenericProcedureFactory(dbManager);
		Collection outArgs = null;
		String errorCode = null;

		try {
			Collection inArgs = buildProcedureInput(updateBean.getCompanyId(), updateBean.getFacilityId(), updateBean.getCabinetId(), updateBean.getMaterialId());

			outArgs = new Vector(1);
			outArgs.add(new Integer(java.sql.Types.VARCHAR));

			if (log.isDebugEnabled()) {
				log.debug("Input Args for P_NONMANAGED_FLA_MATL_DELETE  :" + inArgs);
			}
			Vector error = (Vector) gpFactory.doProcedure("P_NONMANAGED_FLA_MATL_DELETE ", inArgs, outArgs);
			if (error.size() > 0 && error.get(0) != null && !"ok".equalsIgnoreCase((String) error.get(0))) {
				errorCode = (String) error.get(0);
				log.info("error P_NONMANAGED_FLA_MATL_DELETE : " + updateBean.getMaterialId());
			}

		} catch (Exception ex) {
			throw ex;
		}

		return errorCode;
	}


	public Object[] getCabinetManagementRowSpan(Collection dataColl) throws Exception {
		HashMap m1 = new HashMap();
		HashMap m2 = new HashMap();
		Integer i1 = null;
		HashMap map = null;
		Integer i2 = null;
		StringBuilder firstLevelKey;
		//looping thru line data
		Iterator iter = dataColl.iterator();
		while(iter.hasNext()) {
			CabinetManagementBean tmpBean = (CabinetManagementBean) iter.next();
			firstLevelKey = new StringBuilder(tmpBean.getCabinetId().toString());
            StringBuilder secondLevelKey = new StringBuilder("secondLevel");
            if (!StringHandler.isBlankString(tmpBean.getCatPartNo())) {
                if (tmpBean.getBinId() != null) {
                    firstLevelKey.append(tmpBean.getBinId().toString());
                }
                if (!StringHandler.isBlankString(tmpBean.getCatPartNo())) {
                    firstLevelKey.append(tmpBean.getCatPartNo());
                }
                secondLevelKey.append(firstLevelKey);
                if (tmpBean.getItemId() != null)
                    secondLevelKey.append(tmpBean.getItemId());
            }else {
                //handle non-managed materials - no merging cells
                if (tmpBean.getMaterialId() != null)
                    firstLevelKey.append(tmpBean.getMaterialId());
                //second level is the same as first
                secondLevelKey.append(firstLevelKey);
            }

            if (m1.get(firstLevelKey.toString()) == null) {
				i1 = new Integer(0);
				m1.put(firstLevelKey.toString(), i1);
				map = new HashMap();
				m2.put(firstLevelKey.toString(), map);
			}
			i1 = (Integer) m1.get(firstLevelKey.toString());
			i1 = new Integer(i1.intValue() + 1);
			m1.put(firstLevelKey.toString(), i1);

            //second level
            if (map.get(secondLevelKey.toString()) == null) {
				i2 = new Integer(0);
				map.put(secondLevelKey.toString(), i2);
			}
			i2 = (Integer) map.get(secondLevelKey.toString());
			i2 = new Integer(i2.intValue() + 1);
			map.put(secondLevelKey.toString(), i2);

        }
		Object[] objs = {m1,m2};
		return objs;
	}

	public Collection getSearchData(CabinetManagementInputBean bean) throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new CabinetManagementBean());
		StringBuilder query = new StringBuilder("select * from cabinet_part_level_view where facility_id = '").append(bean.getFacilityId()).append("'");
		if (!StringHandler.isBlankString(bean.getUseApplication())) {
			query.append(" and use_application = '").append(bean.getUseApplication()).append("'");
		}
		if (bean.getCabinetIdArray() != null && bean.getCabinetIdArray().length > 0) {
			if (bean.getCabinetIdArray().length == 1) {
				if (bean.getCabinetIdArray()[0].length() > 0) {
					query.append(" and cabinet_id = ").append(bean.getCabinetIdArray()[0]);
				}
			}else {
				query.append(" and cabinet_id in (");
				for (int i = 0; i < bean.getCabinetIdArray().length; i++) {
					if (bean.getCabinetIdArray()[i].length() > 0) {
						//don't add the commas at the end if this is the last element
						if (i == bean.getCabinetIdArray().length -1) {
							query.append(bean.getCabinetIdArray()[i]);
						} else {
							query.append(bean.getCabinetIdArray()[i]).append(",");
						}
					}
				}
				query.append(")");
			}
		}
		if (!StringHandler.isBlankString(bean.getCriteria())) {
			if ("part".equalsIgnoreCase(bean.getItemOrPart())) {
				if ("is".equalsIgnoreCase(bean.getCriterion())) {
					query.append(" and cat_part_no = ").append(SqlHandler.delimitString(bean.getCriteria()));
				} else if ("contains".equalsIgnoreCase(bean.getCriterion())) {
					query.append(" and (").append(doSearchLogic(StringHandler.replace(bean.getCriteria(),"'","''"),"catPartNo")).append(")");
				}
			} else if ("item".equalsIgnoreCase(bean.getItemOrPart())) {
				if ("is".equalsIgnoreCase(bean.getCriterion())) {
					query.append(" and item_id = ").append(bean.getCriteria());
				} else if ("contains".equalsIgnoreCase(bean.getCriterion())) {
					query.append(" and (").append(doSearchLogic(StringHandler.replace(bean.getCriteria(),"'","''"),"itemId")).append(")");
				}
			}
		}
		if (StringHandler.isBlankString(bean.getInactive())) {
			query.append(" and bin_part_status = 'A'");
		}

		if (!StringHandler.isBlankString(bean.getBranchPlant())) {
			query.append(" and source_hub = ").append(bean.getBranchPlant());
		}
		query.append(" and status in ('A','D')");

		query.append(" order by use_application_desc,cabinet_name,bin_name,cat_part_no,item_id");
		/*
		if ("clientCabinetManagement".equalsIgnoreCase(bean.getSourcePage()) || "cabinetManagement".equalsIgnoreCase(bean.getSourcePage())) {
			query.append(" order by use_application_desc,cabinet_name,bin_name,cat_part_no,item_id");
		}else if ("clientCabinetDefinition".equalsIgnoreCase(bean.getSourcePage())) {
			query.append(" order by use_application_desc,cat_part_no,item_id");
		} */
		return factory.selectQuery(query.toString());
	}

	public Collection getClientSearchData(WorkAreaSearchTemplateInputBean bean) throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new CabinetManagementBean());

        StringBuilder areaIdString = new StringBuilder("");
        if (!StringHandler.isBlankString(bean.getAreaId())) {
            areaIdString.append(bean.getAreaId().replace("|",","));
        }
        StringBuilder buildingIdString = new StringBuilder("");
        if (!StringHandler.isBlankString(bean.getBuildingId())) {
            buildingIdString.append(bean.getBuildingId().replace("|",","));
        }
        StringBuilder deptIdString = new StringBuilder("");
        if (!StringHandler.isBlankString(bean.getDeptId())) {
            String[] deptIdArray =  bean.getDeptId().split("\\|");
            for (int i = 0; i < deptIdArray.length; i++) {
                if (deptIdArray[i].length() > 0) {
                    //don't add the commas at the end if this is the last element
                    if (i == deptIdArray.length -1) {
                        deptIdString.append(getSqlString(deptIdArray[i]));
                    } else {
                        deptIdString.append(getSqlString(deptIdArray[i])).append(",");
                    }
                }
            }
        }
        StringBuilder cabinetIdString = new StringBuilder("");
        if (!StringHandler.isBlankString(bean.getApplicationId())) {
            cabinetIdString.append(bean.getApplicationId().replace("|",","));
        }
        StringBuilder countTypeString = new StringBuilder("");
        if (!StringHandler.isBlankString(bean.getCountTypeArray())) {
            String[] countTypeArray =  bean.getCountTypeArray().split("\\|");
            for (int i = 0; i < countTypeArray.length; i++) {
                if (countTypeArray[i].length() > 0) {
                    //don't add the commas at the end if this is the last element
                    if (i == countTypeArray.length -1) {
                        countTypeString.append(getSqlString(countTypeArray[i]));
                    } else {
                        countTypeString.append(getSqlString(countTypeArray[i])).append(",");
                    }
                }
            }
        }

        StringBuilder query = new StringBuilder("select * from table (PKG_STOCK_MGMT.fx_get_nonmanaged_data('");
		query.append(bean.getCompanyId()).append("', '");
		query.append(StringHandler.emptyIfNull(bean.getFacilityId())).append("','");
        query.append(StringHandler.emptyIfNull(cabinetIdString)).append("','");
		query.append(StringHandler.emptyIfNull(bean.getReportingEntityId())).append("','");
        query.append(StringHandler.emptyIfNull(areaIdString)).append("','");
        query.append(StringHandler.emptyIfNull(buildingIdString)).append("',");
        query.append(StringHandler.emptyIfNull(getSqlString(deptIdString))).append(",'");
        query.append(StringHandler.emptyIfNull(bean.getItemOrPart())).append("','");
		query.append(StringHandler.emptyIfNull(bean.getCriterion())).append("','");
		query.append(StringHandler.emptyIfNull(bean.getCriteria())).append("','");
		query.append(StringHandler.emptyIfNull(StringHandler.isBlankString(bean.getInactive())?"A":null)).append("',");
		query.append(StringHandler.emptyIfNull(getSqlString(countTypeString)));
		query.append("))");
		return factory.selectQuery(query.toString());
	}



	public String getShowDefault(PersonnelBean personnelBean, WorkAreaSearchTemplateInputBean bean) throws BaseException, Exception {
		return personnelBean.isFeatureReleased("ShowDefaultColsInWAStockMgmt", bean.getFacilityId(),bean.getCompanyId())?"Y":"N";
	}

	public void setDropdownsforGrid(String dbUser, Locale locale, Collection<CabinetManagementBean> results) throws BaseException, Exception {

		BigDecimal currentCabinetId = null;
		Collection currentPermits = null;
		Collection currentSubstrates = null;
		Collection currentApplicationMethods = null;
		for(CabinetManagementBean bean: results){

			if(currentCabinetId == null ||  bean.getCabinetId().compareTo(currentCabinetId) != 0) {
				currentPermits = new PermitManagementProcess(dbUser).getActivePermits(bean.getCompanyId(), bean.getFacilityId(), bean.getCabinetId());
				currentSubstrates = new UsageLoggingProcess(dbUser,locale).getSubstrates(bean.getCompanyId(), bean.getFacilityId(), bean.getCabinetId());
				currentApplicationMethods = new UsageLoggingProcess(dbUser,locale).getApplicationMethods(bean.getCompanyId(), bean.getFacilityId(), bean.getCabinetId());

				currentCabinetId = bean.getCabinetId();
			}

			bean.setPermits(currentPermits);
			bean.setSubstrates(currentSubstrates);
			bean.setApplicationMethods(currentApplicationMethods);
		}
	}

	public String doSearchLogic(String search, String field) {
		Hashtable r = StringHandler.getLogicalHashFromString(search);
		Vector opers = (Vector) r.get("OPERATORS");
		Vector likes = (Vector) r.get("LIKES");
		String result = "";
		//no operator
		if (opers.size() < 1) {
			if ("itemId".equals(field)) {
				result = " (item_id like lower('%" + search + "%'))";
			}else {
				result = " (lower(cat_part_no) like lower('%" + search + "%'))";
			}
			return result;
		}

		//contains operation in search text
		if ("itemId".equals(field)) {
			result += " ( (item_id like lower('%" + likes.elementAt(0).toString().trim() + "%')) ";
		}else {
			result += " ( (lower(cat_part_no) like lower('%" + likes.elementAt(0).toString().trim() + "%')) ";
		}
		boolean butNot = false;
		for (int i = 0; i < opers.size(); i++) {
			String op = opers.elementAt(i).toString();
			String lk = "like";
			if (op.equalsIgnoreCase("but not")) {
				op = "and";
				lk = "not like";
				butNot = true;
			}
			String searchS = likes.elementAt(i + 1).toString().trim();
			if ("itemId".equals(field)) {
				if (butNot) {
					result += " " + op + " (item_id " + lk + " lower('%" + searchS + "%')) ";
				}else {
					result += " " + op + " (item_id " + lk + " lower('%" + searchS + "%')) ";
				}
			}else {
				if (butNot) {
					result += " " + op + " (lower(cat_part_no) " + lk + " lower('%" + searchS + "%')) ";
				}else {
					result += " " + op + " (lower(cat_part_no) " + lk + " lower('%" + searchS + "%')) ";
				}
			}
		}
		result += " ) ";

		return result;
	}

	public Collection getUserFacilityWorkAreaGroupWorkAreaData(int personnelId) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new UserFacWagWaViewBean());
		StringBuilder query = new StringBuilder("select distinct * from user_fac_wag_wa_view where personnel_id = ").append(personnelId);
		query.append(" and reporting_entity_status = 'A' and status = 'A' and allow_stocking = 'Y'");
		query.append(" order by facility_name,reporting_entity_desc,application_desc");
		return factory.selectQuery(query.toString());
	}

	public Collection createRelationalObject(Collection userFacilityWorkAreaGroupWorkAreaColl) {
		Vector result = new Vector();
		Iterator iter = userFacilityWorkAreaGroupWorkAreaColl.iterator();
		String lastFacilityId = "";
		String lastWorkAreaGroup = "";
		while(iter.hasNext()) {
			UserFacWagWaViewBean bean = (UserFacWagWaViewBean)iter.next();
			String currentFacilityId = bean.getFacilityId();
			String currentWorkAreaGroup = bean.getReportingEntityId();
			if (currentFacilityId.equals(lastFacilityId)) {
				UserFacWagWaViewBean facilityBean = (UserFacWagWaViewBean)result.lastElement();
				Collection facilityWorkAreaColl = facilityBean.getFacilityWorkAreaColl();
				if (currentWorkAreaGroup.equals(lastWorkAreaGroup)) {
					Vector workAreaGroupColl = (Vector)facilityBean.getWorkAreaGroupColl();
					UserFacWagWaViewBean workAreaGroupBean = (UserFacWagWaViewBean)workAreaGroupColl.lastElement();
					Collection workAreaColl = workAreaGroupBean.getWorkAreaColl();
					UserFacWagWaViewBean workAreaBean = new UserFacWagWaViewBean();
					copyWorkAreaData(bean,workAreaBean);
					workAreaColl.add(workAreaBean);
					facilityWorkAreaColl.add(workAreaBean);
				}else {
					Collection workAreaGroupColl = facilityBean.getWorkAreaGroupColl();
					Collection workAreaColl = new Vector();
					UserFacWagWaViewBean workAreaGroupBean = new UserFacWagWaViewBean();
					UserFacWagWaViewBean workAreaBean = new UserFacWagWaViewBean();
					copyWorkAreaData(bean,workAreaBean);
					workAreaColl.add(workAreaBean);
					copyWorkAreaGroupData(bean,workAreaGroupBean);
					workAreaGroupBean.setWorkAreaColl(workAreaColl);
					workAreaGroupColl.add(workAreaGroupBean);
					facilityWorkAreaColl.add(workAreaBean);
				}
			}else {
				Collection workAreaGroupColl = new Vector();
				Collection workAreaColl = new Vector();
				UserFacWagWaViewBean workAreaGroupBean = new UserFacWagWaViewBean();
				UserFacWagWaViewBean workAreaBean = new UserFacWagWaViewBean();
				copyWorkAreaData(bean,workAreaBean);
				workAreaColl.add(workAreaBean);
				copyWorkAreaGroupData(bean,workAreaGroupBean);
				workAreaGroupBean.setWorkAreaColl(workAreaColl);
				workAreaGroupColl.add(workAreaGroupBean);
				bean.setWorkAreaGroupColl(workAreaGroupColl);
				//set workArea for facility here
				Collection facilityWorkAreaColl = new Vector();
				facilityWorkAreaColl.add(workAreaBean);
				bean.setFacilityWorkAreaColl(facilityWorkAreaColl);
				result.addElement(bean);
			}
			lastFacilityId = currentFacilityId;
			lastWorkAreaGroup = currentWorkAreaGroup;
		}
		return result;
	}

	private void copyWorkAreaData(UserFacWagWaViewBean fromBean, UserFacWagWaViewBean toBean) {
		toBean.setApplication(fromBean.getApplication());
		toBean.setApplicationDesc(fromBean.getApplicationDesc());
		toBean.setStatus(fromBean.getStatus());
		toBean.setManualMrCreation(fromBean.getManualMrCreation());
		toBean.setAllowStocking(fromBean.getAllowStocking());
		toBean.setApplicationId(fromBean.getApplicationId());
	}

	private void copyWorkAreaGroupData(UserFacWagWaViewBean fromBean, UserFacWagWaViewBean toBean) {
		toBean.setReportingEntityId(fromBean.getReportingEntityId());
		toBean.setReportingEntityDesc(fromBean.getReportingEntityDesc());
		toBean.setReportingEntityStatus(fromBean.getReportingEntityStatus());
	}

	public String getSizeUnitString(String materialId) throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new CabinetManagementBean());

		StringBuilder cabinetIdString = new StringBuilder("");

		StringBuilder query = new StringBuilder("select FX_SIZE_UNIT_OPTION(");
		query.append(materialId).append(") from dual");
		return factory.selectSingleValue(query.toString());
	}


	public ExcelHandler getExcelReport(CabinetManagementInputBean inputBean, Locale locale) throws Exception {
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);

		if ("cabinetManagement".equalsIgnoreCase(inputBean.getSourcePage())) {
			getCabinetManagementExcelReport(pw,inputBean);
		}else if ("clientCabinetDefinition".equalsIgnoreCase(inputBean.getSourcePage())) {
			getCabinetDefinitionExcelReport(pw,inputBean);
		}
		return pw;
	}

	public ExcelHandler getClientCabinetManagementExcelReport(WorkAreaSearchTemplateInputBean inputBean, PersonnelBean personnelBean, Locale locale) throws Exception {
		ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
		ExcelHandler pw = new ExcelHandler(library);
		getClientCabinetManagementExcelReport(pw,inputBean, personnelBean);
		return pw;
	}

	private void getCabinetManagementExcelReport(ExcelHandler pw, CabinetManagementInputBean inputBean) throws Exception {
		Collection<CabinetManagementBean> data = this.getSearchData(inputBean);
		pw.addTable();
		pw.addRow();
		String[] headerkeys = {"label.workarea","label.cabinet","label.bin","label.counttype","label.catalog", "label.active", "label.partnumber", "label.reorderpoint", "label.stockinglevel", "label.reorderquantity", "label.kanbanreorderqty", "label.leadtimeindays", "label.item", "label.description", "label.containersize", "label.msdsnumber"};
		int[] widths = {15, 15, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0};
		int[] types = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, pw.TYPE_PARAGRAPH, 0,0};
		int[] aligns = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		pw.applyColumnHeader(headerkeys, types, widths, aligns);

		// now write data
		for (CabinetManagementBean member : data) {
			pw.addRow();

			pw.addCell(member.getUseApplicationDesc());
			pw.addCell(member.getCabinetName());
			pw.addCell(member.getBinName());
			pw.addCell(member.getCountType());
			pw.addCell(member.getCatalogDesc());
			pw.addCell(member.getBinPartStatus());
			pw.addCell(member.getCatPartNo());
			pw.addCell(member.getReorderPoint());
			pw.addCell(member.getStockingLevel());
			pw.addCell(member.getReorderQuantity());
			pw.addCell(member.getKanbanReorderQuantity());
			pw.addCell(member.getLeadTimeDays());
			pw.addCell(member.getItemId());
			pw.addCell(member.getMaterialDesc());
			pw.addCell(member.getPackaging());
			pw.addCell(member.getMsdsString());
		}
	}

	private void getClientCabinetManagementExcelReport(ExcelHandler pw, WorkAreaSearchTemplateInputBean inputBean, PersonnelBean personnelBean) throws Exception {
		Collection<CabinetManagementBean> data = this.getClientSearchData(inputBean);
		pw.addTable();
		pw.addRow();

		int[] widths = {0, 0, 0, 0, 0, 30, 0, 0, 0, 0, 0,
				 0, 30, 30, 0, 0, 0, 0, 20, 20, 20, 0, 0, 0, 0, 0};
		int[] types =  {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				 0, pw.TYPE_PARAGRAPH,  pw.TYPE_PARAGRAPH,  0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int[] aligns = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				 0 ,0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};


		if("Y".equals(inputBean.getShowDefault()) && !personnelBean.isFeatureReleased("HomeCompanyOwned",inputBean.getFacilityId(),inputBean.getCompanyId())){
			String[] headerkeys = {"label.workarea","label.counttype","label.catalog", "label.active", "label.partnumber", "label.partdescription",
					"label.reorderpoint", "label.stockinglevel", "label.reorderquantity", "label.kanbanreorderqty", "label.leadtimeindays",
					"label.item", "label.description", "label.containersize", "label.msdsnumber", "label.averageamount", "label.maxamount",
					"label.unit","label.tieriistoragecontainer","label.tieriistoragepressure","label.tieriistoragetemperature","label.defaultparttype",
                    "label.defaultpermit", "label.defaultapplicationmethod", "label.defaultsubstrate", "label.dropship"};
			pw.applyColumnHeader(headerkeys, types, widths, aligns);
		} else if("Y".equals(inputBean.getShowDefault()) && personnelBean.isFeatureReleased("HomeCompanyOwned",inputBean.getFacilityId(),inputBean.getCompanyId())){
			String[] headerkeys = {"label.workarea","label.counttype","label.catalog", "label.active", "label.partnumber", "label.partdescription",
					"label.reorderpoint", "label.stockinglevel", "label.reorderquantity", "label.kanbanreorderqty", "label.leadtimeindays",
					"label.item", "label.description", "label.containersize", "label.msdsnumber", "label.averageamount", "label.maxamount",
					"label.unit","label.tieriistoragecontainer","label.tieriistoragepressure","label.tieriistoragetemperature","label.defaultparttype",
                    "label.defaultpermit", "label.defaultapplicationmethod", "label.defaultsubstrate", "label.ownership", "label.dropship"};
			pw.applyColumnHeader(headerkeys, types, widths, aligns);
		} else if(!"Y".equals(inputBean.getShowDefault()) && !personnelBean.isFeatureReleased("HomeCompanyOwned",inputBean.getFacilityId(),inputBean.getCompanyId())){
			String[] headerkeys = {"label.workarea","label.counttype","label.catalog", "label.active", "label.partnumber", "label.partdescription",
                    "label.reorderpoint", "label.stockinglevel", "label.reorderquantity", "label.kanbanreorderqty", "label.leadtimeindays", "label.item",
                    "label.description", "label.containersize", "label.msdsnumber", "label.averageamount", "label.maxamount", "label.unit",
                    "label.tieriistoragecontainer","label.tieriistoragepressure","label.tieriistoragetemperature","label.dropship"};
			pw.applyColumnHeader(headerkeys, types, widths, aligns);
		} else {
			String[] headerkeys = {"label.workarea","label.counttype","label.catalog", "label.active", "label.partnumber", "label.partdescription",
                    "label.reorderpoint", "label.stockinglevel", "label.reorderquantity", "label.kanbanreorderqty", "label.leadtimeindays", "label.item",
                    "label.description", "label.containersize", "label.msdsnumber", "label.averageamount", "label.maxamount", "label.unit",
                    "label.tieriistoragecontainer","label.tieriistoragepressure","label.tieriistoragetemperature","label.ownership","label.dropship"};
			pw.applyColumnHeader(headerkeys, types, widths, aligns);
		}
		
		// now write data
		for (CabinetManagementBean member : data) {
			pw.addRow();

			pw.addCell(member.getCabinetName());
			pw.addCell(member.getCountType());
			pw.addCell(member.getCatalogDesc());
			pw.addCell(member.getBinPartStatus());
			pw.addCell(member.getCatPartNo());
			pw.addCell(member.getPartDescription());
			pw.addCell(member.getReorderPoint());
			pw.addCell(member.getStockingLevel());
			pw.addCell(member.getReorderQuantity());
			pw.addCell(member.getKanbanReorderQuantity());
			pw.addCell(member.getLeadTimeDays());
			pw.addCell(member.getItemId());
			pw.addCell(member.getMaterialDesc());
			pw.addCell(member.getPackaging());
			pw.addCell(member.getMsdsString());
			pw.addCell(member.getAvgAmount());
			pw.addCell(member.getMaxAmount());
			pw.addCell(member.getSizeUnit());
			pw.addCell(member.getTierIIStorage() != null ? member.getTierIIStorage().substring(2):"");
			pw.addCell(member.getTierIIPressure() != null ? member.getTierIIPressure().substring(2):"");
			pw.addCell(member.getTierIITemperature() != null ? member.getTierIITemperature().substring(2):"");
			if("Y".equals(inputBean.getShowDefault())){
				ResourceLibrary res = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
				if("F".equals(member.getDefaultPartType()))
					pw.addCell(res.getString("label.aerospace"));
				else if("N".equals(member.getDefaultPartType()))
					pw.addCell(res.getString("label.nonaerospace"));
				else
					pw.addCell("");
				pw.addCell(member.getPermitName());
				pw.addCell(member.getMethod());
				pw.addCell(member.getSubstrate());
			}
			if (personnelBean.isFeatureReleased("HomeCompanyOwned",inputBean.getFacilityId(),inputBean.getCompanyId())) 
				pw.addCell(member.getOwnershipName());
            pw.addCell(member.getDropShipOverride());
		}
	}

	private void getCabinetDefinitionExcelReport(ExcelHandler pw, CabinetManagementInputBean inputBean) throws Exception {
		Collection<CabinetManagementBean> data = this.getSearchData(inputBean);
		pw.addTable();
		pw.addRow();
		String[] headerkeys = {"label.workarea", "label.catalog", "label.partnumber", "label.reorderpoint", "label.stockinglevel", "label.reorderquantity", "label.kanbanreorderqty", "label.leadtimeindays", "label.item", "label.description", "label.msdsnumber"};
		int[] widths = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int[] types = {0, 0, pw.TYPE_STRING, 0, 0, 0, 0, 0, 0, pw.TYPE_PARAGRAPH, 0};
		int[] aligns = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		pw.applyColumnHeader(headerkeys, types, widths, aligns);

		// now write data
		for (CabinetManagementBean member : data) {
			pw.addRow();

			pw.addCell(member.getCabinetName());
			pw.addCell(member.getCatalogDesc());
			pw.addCell(member.getCatPartNo());
			pw.addCell(member.getReorderPoint());
			pw.addCell(member.getStockingLevel());
			pw.addCell(member.getReorderQuantity());
			pw.addCell(member.getKanbanReorderQuantity());
			pw.addCell(member.getLeadTimeDays());
			pw.addCell(member.getItemId());
			pw.addCell(member.getMaterialDesc());
			pw.addCell(member.getMsdsString());
		}
	}  //end of method

	public Collection<VvUnidocsStorageContainerBean> getVvUnidocsStorageContainerBeanColl() throws BaseException, Exception
	{
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager,new VvUnidocsStorageContainerBean());

		return genericSqlFactory.selectQuery("select * from vv_unidocs_storage_container order by DESCRIPTION");
	}

	public Collection<VvTierIITemperatureCodeBean> getVvTierIITemperatureCodeBeanColl() throws BaseException, Exception
	{
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager,new VvTierIITemperatureCodeBean());

		return genericSqlFactory.selectQuery("select * from VV_TIER_II_TEMPERATURE_CODE order by TIER_II_TEMPERATURE");

	}

	public String getNonManagedMatCount(String companyId, String facilityId, String application, String materialId) throws BaseException, Exception
	{
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager,new VvTierIITemperatureCodeBean());
		return genericSqlFactory.selectSingleValue("select count(*) from NONMANAGED_FLA_MATL where COMPANY_ID = '" + companyId + "' and FACILITY_ID = '" + facilityId + "' and APPLICATION = '" + application + "' and MATERIAL_ID = " + materialId);
	}

	public boolean checkPermissions(String userGroupId, PersonnelBean personnelBean,  CabinetManagementInputBean bean) throws BaseException, Exception  {
		if(bean.getCabinetIdArray()[0].length() == 0)
			return true;
		else
		{
			StringBuilder cabinetIdString = new StringBuilder("");
			for (int i = 0; i < bean.getCabinetIdArray().length; i++) {
				if (bean.getCabinetIdArray()[i].length() > 0) {
					//don't add the commas at the end if this is the last element
					if (i == bean.getCabinetIdArray().length -1) {
						cabinetIdString.append("'").append(bean.getCabinetIdArray()[i]).append("'");
					} else {
						cabinetIdString.append("'").append(bean.getCabinetIdArray()[i]).append("',");
					}
				}
			}
			DbManager dbManager = new DbManager(getClient(), getLocale());
			GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);
			String count = genericSqlFactory.selectSingleValue("select count(*) from USER_GROUP_MEMBER_APPLICATION where company_id = '"
					+personnelBean.getCompanyId()+"' and facility_id = '"+bean.getFacilityId()
					+"' and user_group_id = '"+userGroupId+"' and personnel_id = "+personnelBean.getPersonnelId()
					+" and application in (select application from fac_loc_app where company_id = '"+personnelBean.getCompanyId()
					+"' and facility_id = '"+bean.getFacilityId()+"' and (application = 'All' or application_id in("+cabinetIdString+")))");
			if(Integer.parseInt(count) > 0)
				return true;
			else
				return false;
		}
	}

	public Collection getAuthorizedUserWorkAreasForNonManangedMatl(PersonnelBean personnelBean,  String facilityId) throws BaseException, Exception  {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager,new CabinetManagementBean());
		StringBuilder query = new StringBuilder("select fla.* from fac_loc_app fla, user_group_member_application ugma");
		query.append(" where fla.company_id = ugma.company_id and fla.status = 'A' and fla.facility_id = ugma.facility_id");
		query.append(" and fla.application = decode(ugma.application,'All',fla.application,ugma.application)");
		query.append(" and fla.company_id = '").append(personnelBean.getCompanyId()).append("' and fla.facility_id = '").append(facilityId).append("'");
		query.append(" and ugma.user_group_id = 'NonStockedPartMgmt' and ugma.personnel_id = ").append(personnelBean.getPersonnelId());
		return genericSqlFactory.selectQuery(query.toString());
	}

	public Collection getAuthorizedUserWorkAreasForManangedMatl(PersonnelBean personnelBean,  String facilityId, String companyId) throws BaseException, Exception  {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager,new CabinetManagementBean());
		StringBuilder query = new StringBuilder("select fla.* from fac_loc_app fla, user_group_member_application ugma");
		query.append(" where fla.company_id = ugma.company_id and fla.facility_id = ugma.facility_id");
		query.append(" and fla.application = decode(ugma.application,'All',fla.application,ugma.application)");
		query.append(" and fla.company_id = '").append(companyId).append("' and fla.facility_id = '").append(facilityId).append("'");
		query.append(" and ugma.user_group_id = 'StockedPartMgmt' and ugma.personnel_id = ").append(personnelBean.getPersonnelId());
        query.append(" order by fla.application_desc");
        return genericSqlFactory.selectQuery(query.toString());
	}

	public Collection getNonManMatlHist(String companyId, String facilityId, String application, String materialId, String catalogCompanyId, String catalogId) throws BaseException, Exception
	{
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager,new CabinetManagementBean());
		StringBuilder query = new StringBuilder();
		query.append("select fx_personnel_id_to_name(nfm.inserted_by) inserted_name,nfm.AVG_AMOUNT,nfm.SIZE_UNIT,nfm.STATUS,nfm.INSERTED_ON,nfm.WT_LB,nfm.MAX_AMOUNT");
        query.append(",vvusc.description tier_II_storage,vvttc.tier_II_temperature,nfm.MAX_WT_LB,nfm.LARGEST_CONTAINER_SIZE,nfm.WT_LB,nfm.MAX_WT_LB from");
		query.append(" NONMANAGED_FLA_MATL nfm, fac_loc_app fla, vv_unidocs_storage_container vvusc, VV_TIER_II_TEMPERATURE_CODE vvttc where nfm.COMPANY_ID = '");
		query.append(companyId).append("' and nfm.FACILITY_ID = '").append(facilityId).append("' and fla.APPLICATION_ID = '").append(application);
        query.append("' and nfm.company_id = fla.company_id and nfm.facility_id = fla.facility_id and nfm.application = fla.application");
        query.append(" and nfm.MATERIAL_ID = '").append(materialId).append("' and nfm.CATALOG_COMPANY_ID = '").append(catalogCompanyId);
		query.append("' and nfm.CATALOG_ID = '").append(catalogId);
		query.append("' and nfm.TIER_II_TEMPERATURE_CODE = vvttc.TIER_II_TEMPERATURE_CODE and nfm.TIER_II_STORAGE_CODE = vvusc.UNIDOCS_STORAGE_CONTAINER");
		return genericSqlFactory.selectQuery(query.toString());
    }

    public Collection getPartHistory(CabinetManagementBean inputBean) throws BaseException, Exception
	{
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager,new CabinetPartLevelLogViewBean());
		StringBuilder query = new StringBuilder();
		query.append("select cpll.*,fx_personnel_id_to_name(cpll.modified_by) modified_by_name,");
        query.append("pkg_work_area_management.fx_get_ownership_name(cpll.bin_id,cpll.date_modified,'OLD') old_ownership_name,");
        query.append("pkg_work_area_management.fx_get_ownership_name(cpll.bin_id,cpll.date_modified,'NEW') new_ownership_name,");
        query.append("ovvttc.tier_II_temperature old_tier_ii_temperature,nvvttc.tier_II_temperature new_tier_ii_temperature");
		query.append(" from cabinet_part_level_log cpll, VV_TIER_II_TEMPERATURE_CODE ovvttc, VV_TIER_II_TEMPERATURE_CODE nvvttc");
        query.append(" where cpll.modified_by <> -1 and cpll.COMPANY_ID = '");
		query.append(inputBean.getCompanyId()).append("' and cpll.FACILITY_ID = '").append(inputBean.getFacilityId()).append("' and cpll.APPLICATION = '").append(inputBean.getApplication());
        query.append("' and cpll.CATALOG_COMPANY_ID = '").append(inputBean.getCatalogCompanyId());
		query.append("' and cpll.CATALOG_ID = '").append(inputBean.getCatalogId()).append("' and cpll.cat_part_no = '").append(inputBean.getCatPartNo());
        query.append("' and cpll.part_group_no = ").append(inputBean.getPartGroupNo());
        query.append(" and (");
            query.append(" cpll.old_status <> cpll.new_status");
            query.append(" or cpll.old_count_type <> cpll.new_count_type");
            query.append(" or cpll.old_receipt_processing_method <> cpll.new_receipt_processing_method");
            query.append(" or cpll.old_tier_ii_temperature_code <> cpll.new_tier_ii_temperature_code");
            query.append(" or cpll.old_bin_name <> cpll.new_bin_name");
            query.append(" or cpll.old_drop_ship_override <> cpll.new_drop_ship_override");
        query.append(")");
        query.append(" and cpll.old_TIER_II_TEMPERATURE_CODE = ovvttc.TIER_II_TEMPERATURE_CODE(+)");
        query.append(" and cpll.new_TIER_II_TEMPERATURE_CODE = nvvttc.TIER_II_TEMPERATURE_CODE(+)");
        query.append(" order by cpll.date_modified desc");
        return genericSqlFactory.selectQuery(query.toString());
    }


    public String changeOwnership(CabinetManagementBean updateBean, PersonnelBean personnelBean) throws BaseException, Exception {
		String errMsg = null;

		DbManager dbManager = new DbManager(getClient(), getLocale());
		Connection connection = dbManager.getConnection();
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);
        ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());

        try {
            //first update cabinet_part_inventory levels if user want to change when converting ownership
            String convertToCompanyOwned = "";
            if(updateBean.getHcoFlag().equals("Y")){
                convertToCompanyOwned = "N"; //convert to customer owned
            }else {
                convertToCompanyOwned = "Y"; //convert to company owned
            }
            String errorMsg = updateCabinetPartInventory(updateBean,genericSqlFactory,connection,personnelBean,library,convertToCompanyOwned);
            if (!StringHandler.isBlankString(errorMsg)){
    			errMsg = errorMsg;
                //the reason for this is that if there is an error converting then send back what it was selected
                if(updateBean.getHcoFlag().equals("Y"))
                    updateBean.setHcoFlag("N");
                 else
                    updateBean.setHcoFlag("Y");
            }else {
                // return new owner
                String query = "select pkg_work_area_management.fx_get_ownership_name(" +  updateBean.getBinId() + ") from dual";
                updateBean.setOwnershipName(genericSqlFactory.selectSingleValue(query));
                //notify user of ownership change
                CabinetLevelInputBean cabinetLevelInputBean = new CabinetLevelInputBean();
                BeanHandler.copyAttributes(updateBean, cabinetLevelInputBean);
                CabinetLevelProcess cabinetLevelProcess = new CabinetLevelProcess(getClient());
                cabinetLevelProcess.setUserGroupNotification(cabinetLevelInputBean, personnelBean, "Change Ownership");
            }
        } catch (Exception e) {
			errMsg = "Error converting bin: " + updateBean.getBinId();
			log.error(errMsg + ". " + e.getMessage());
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
		}
        return errMsg;
	}

	public Collection getFacilityAreaBldgRm(PersonnelBean personnelBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),locale.toString());
        Connection conn = dbManager.getConnection();
		GenericSqlFactory factory = new GenericSqlFactory(dbManager);

        StringBuilder query = new StringBuilder("select * from personnel_fac_area_bldg_rm_vw pfabrv where pfabrv.PERSONNEL_ID = ").append(personnelBean.getPersonnelId());
        query.append(" and pfabrv.active = 'y' ");
        if ("Radian".equalsIgnoreCase(personnelBean.getCompanyId()) || "Haas".equalsIgnoreCase(personnelBean.getCompanyId())) {
            query.append(" and exists (select cug.company_id from customer.company_user_group cug");
            query.append(" where cug.user_group_id in ('NonStockedPartMgmt','StockedPartMgmt') and pfabrv.company_id = cug.company_id)");
        }
        query.append(" order by COMPANY_NAME,FACILITY_NAME,AREA_NAME,BUILDING_NAME,FLOOR_DESCRIPTION,ROOM_NAME");
        PersonnelFacAreaBldgRmVwProcess process = new PersonnelFacAreaBldgRmVwProcess(getClient(),locale.toString());
        factory.setBeanObject(new PersonnelFacAreaBldgRmVwBean());
        Vector<PersonnelFacAreaBldgRmOvBean> personnelFacAreaBldgRmOvBeanColl = (Vector<PersonnelFacAreaBldgRmOvBean>)process.createRelationalObjects(factory.selectQuery(query.toString(),conn));

        try{
            //looping thru user list of facilities
			for(PersonnelFacAreaBldgRmOvBean personnelFacAreaBldgRmOvBean: personnelFacAreaBldgRmOvBeanColl) {
				if(personnelFacAreaBldgRmOvBean.getFacilityList() != null) {
					Collection<FacNameAreaBldgRmObjBean> c = personnelFacAreaBldgRmOvBean.getFacilityList();
					for(FacNameAreaBldgRmObjBean facNameAreaBldgRmObjBean: c) {
						factory.setBeanObject(new ReportingEntityBean());
						facNameAreaBldgRmObjBean.setReportingEntityList(factory.selectQuery("select re.reporting_entity_id, re.description" 
								+ " from reporting_entity re "
		                        + " where re.company_id = '" +personnelFacAreaBldgRmOvBean.getCompanyId()+"'"
		                        + " and re.facility_id = '"+facNameAreaBldgRmObjBean.getFacilityId()+"' order by re.description",conn));
						factory.setBeanObject(new DeptBean());
						facNameAreaBldgRmObjBean.setDeptList(factory.selectQuery("select distinct fla.dept_id, d.dept_name"
								+ " from fac_loc_app fla, dept d, user_application ua"
								+ "	where fla.company_id = d.company_id"
								+ " and fla.dept_id = d.dept_id"
		                        + " and fla.company_id = ua.company_id and fla.facility_id = ua.facility_id"
		                        + " and fla.application = decode(ua.application,'All',fla.application,ua.application) and ua.personnel_id = "+personnelBean.getPersonnelId()
		                        + " and fla.company_id = '"+personnelFacAreaBldgRmOvBean.getCompanyId()+"'"
								+ " and fla.facility_id = '"+facNameAreaBldgRmObjBean.getFacilityId()+"' order by d.dept_name",conn));
						//getting account sys
					    factory.setBeanObject(new AccountSysBean());
						facNameAreaBldgRmObjBean.setAccountSysList(factory.selectQuery("select ac.account_sys_id,ac.account_sys_desc from vv_account_sys ac"
								+" where exists (select pr.account_sys_id from pr_rules pr"
                                +" where ac.company_id = pr.company_id and ac.account_sys_id = pr.account_sys_id and pr.status = 'A'"
                                + " and pr.company_id = '"+personnelFacAreaBldgRmOvBean.getCompanyId()+"' and pr.facility_id = '"+facNameAreaBldgRmObjBean.getFacilityId()+"') order by account_sys_desc",conn));
		            }
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(conn);
			dbManager = null;
			conn = null;
			factory = null;
		}
        return personnelFacAreaBldgRmOvBeanColl;
    }

	public Collection getAuthorizedUsersForUsergroup(PersonnelBean personnelBean, String userGroupId) throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager,new CabinetManagementBean());
		StringBuilder query = new StringBuilder("SELECT DISTINCT COMPANY_ID FROM USER_GROUP_MEMBER WHERE PERSONNEL_ID = ").append(personnelBean.getPersonnelId());
		query.append(" AND USER_GROUP_ID = '").append(userGroupId).append("'");
        return genericSqlFactory.selectQuery(query.toString());        
	}

	public ExcelHandler createTemplateData(PersonnelBean personnelBean, WorkAreaSearchTemplateInputBean inputBean, Locale locale) throws Exception {
        ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
        ExcelHandler pw = new ExcelHandler(library);
        pw.addTable();
        pw.addRow();

        String[] headerkeys = {
            "excel.company_id","excel.catalog_company_id","excel.catalog_id","excel.cat_part_no","excel.part_group_no",
            "excel.facility_id","excel.application","excel.bin_id","excel.reorder_point","excel.stocking_level","excel.level_hold_end_date"
        };
        /*This array defines the type of the excel column.
        0 means default depending on the data type. */
        int[] types = {0,0,0,0,0,0,0,0,0,0,0};
        /*This array defines the default width of the column when the Excel file opens.
        0 means the width will be default depending on the data type.*/
        int[] widths = {0,0,0,0,0,0,0,0,0,0,0};
        /*This array defines the horizontal alignment of the data in a cell.
        0 means excel defaults the horizontal alignemnt by the data type.*/
        int[] horizAligns = {0,0,0,0,0,0,0,0,0,0,0};

        pw.applyColumnHeader(headerkeys, types, widths, horizAligns);

        // now write data
        Collection<CabinetManagementBean> data = this.getClientSearchData(inputBean);
        for (CabinetManagementBean bean : data) {
            if ("Y".equals(bean.getNonManaged())) continue;
            pw.addRow();
            pw.addCell(bean.getCompanyId());
            pw.addCell(bean.getCatalogCompanyId());
            pw.addCell(bean.getCatalogId());
            pw.addCell(bean.getCatPartNo());
            pw.addCell(bean.getPartGroupNo());
            pw.addCell(bean.getFacilityId());
            pw.addCell(bean.getOrderingApplication());
            pw.addCell(bean.getBinId());
            pw.addCell(bean.getReorderPoint());
            pw.addCell(bean.getStockingLevel());
            pw.addCell(bean.getLevelHoldEndDate());
        }
        return pw;
    }
	
} //end of class