package com.tcmis.client.common.process;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.util.Collection;
import java.util.Iterator;

import com.tcmis.client.common.beans.PurchaseRequestBean;
import com.tcmis.common.admin.process.MailProcess;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.catalog.beans.CatalogInputBean;
import com.tcmis.client.catalog.beans.CatalogPartUnitOfSaleBean;
import com.tcmis.client.common.beans.ShoppingCartBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.net.TcmisHttpURLConnection;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * Process for ShoppingCartProcess
 * @version 1.0
 *****************************************************************************/
public class ShoppingCartProcess extends BaseProcess {

	Log log = LogFactory.getLog(this.getClass());
	DbManager dbManager = null;
	Connection connection = null;
	GenericSqlFactory genericSqlFactory = null;
	private boolean returnConnection = true;

	public ShoppingCartProcess(String client,String locale) {
		super(client,locale);
		dbManager = new DbManager(getClient(),this.getLocale());
	}

	public void setFactoryConnection(GenericSqlFactory genericSqlFactory, Connection connection) {
		this.connection = connection;
		this.genericSqlFactory = genericSqlFactory;
	}

	public void setReturnConnection(boolean val) {
		this.returnConnection = val;
	}

	public BigDecimal buildRequest(CatalogInputBean inputBean,Collection beans,BigDecimal userId) throws BaseException {
		BigDecimal prNumber = new BigDecimal(-1);
		try {
			BigDecimal existingPrNumber = getExistingPrNumber(beans);
			if (!existingPrNumber.equals(prNumber)) {
				prNumber = buildUpdateRequest(existingPrNumber,inputBean,beans,userId);
			}else {
				prNumber = buildNewRequest(inputBean,beans,userId);
			}
		}catch (Exception e) {
			e.printStackTrace();
			BaseException be = new BaseException(e);
			be.setRootCause(e);
			throw be;
		}
		return prNumber;
	}

	public BigDecimal buildUpdateRequest(BigDecimal prNumber,CatalogInputBean inputBean,Collection beans,BigDecimal userId) throws BaseException {
		if (log.isDebugEnabled()) {
			log.debug(inputBean.getFacilityId()+"-"+inputBean.getAccountSysId());
		}
		try {
			if (connection == null)
				connection = dbManager.getConnection();
			if (genericSqlFactory == null)
				genericSqlFactory = new GenericSqlFactory(dbManager);
			StringBuilder query;
			String requiredMrScreen = "Y";
			String unitPriceRequired = "N/A";

			Iterator iter = beans.iterator();
			query = new StringBuilder("select nvl(max(to_number(line_item+1)),1) from request_line_item where pr_number = ").append(prNumber.toString());
			BigDecimal lineItem = new BigDecimal(genericSqlFactory.selectSingleValue(query.toString(),connection));
			boolean firstTime = true;
			while(iter.hasNext()) {
				ShoppingCartBean bean = (ShoppingCartBean)iter.next();
				if (firstTime) {
					if ("Y".equalsIgnoreCase(requiredMrScreen)) {
						query = new StringBuilder("select unit_price_required from pr_rules where status = 'A' and facility_id = '").append(bean.getFacilityId());
						query.append("' and account_sys_id = '").append(inputBean.getAccountSysId()).append("'");
						unitPriceRequired = genericSqlFactory.selectSingleValue(query.toString(),connection);
					}
					firstTime = false;
				}
				//determine whether to update line data or create new one
				if (StringHandler.isBlankString(bean.getLineItem())) {
					//create new line item
					createNewRequestLineItem(bean,prNumber,lineItem.intValue(),requiredMrScreen,unitPriceRequired);
					lineItem = new BigDecimal(lineItem.intValue()+1);
				}else {
					//update line item
					query = new StringBuilder("update request_line_item set quantity  = ").append(bean.getQuantity().toString());
					//required date
					if (bean.getDateNeeded() != null) {
						query.append(",required_datetime = ").append(DateHandler.getOracleToDateFunction(bean.getDateNeeded()));
					}
					//notes
					if (!StringHandler.isBlankString(bean.getNotes())) {
						query.append(",notes = ").append(SqlHandler.delimitString(bean.getNotes()));
					}
					//critical
					if ("true".equalsIgnoreCase(bean.getCritical())) {
						query.append(",critical = 'y'");
					}else {
						query.append(",critical = null");
					}
					query.append(" where pr_number = ").append(prNumber.toString()).append(" and line_item = '").append(bean.getLineItem()).append("'");
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
				} //end of updating line item
			}
		}catch (Exception e) {
			e.printStackTrace();
			BaseException be = new BaseException(e);
			be.setRootCause(e);
			throw be;
		}finally {
			if (returnConnection)
				dbManager.returnConnection(connection);
		}
		return prNumber;
	} //end of method

	private BigDecimal getExistingPrNumber(Collection beans) {
		BigDecimal result = new BigDecimal(-1);
		Iterator iter = beans.iterator();
		while(iter.hasNext()) {
			ShoppingCartBean bean = (ShoppingCartBean)iter.next();
			if (bean.getPrNumber() != null) {
				result = bean.getPrNumber();
				break;
			}
		}
		return result;
	}

	public BigDecimal buildNewRequest(CatalogInputBean inputBean,Collection beans,BigDecimal userId) throws BaseException {
		BigDecimal prNumber = new BigDecimal(-1);
		try {
			if (connection == null)
				connection = dbManager.getConnection();
			if (genericSqlFactory == null)
				genericSqlFactory = new GenericSqlFactory(dbManager);
			StringBuilder query;
			String requiredMrScreen = "N/A";
			String unitPriceRequired = "N/A";

			Iterator iter = beans.iterator();
			int lineItem = 1;
			while(iter.hasNext()) {
				ShoppingCartBean bean = (ShoppingCartBean)iter.next();
				if (lineItem == 1) {
					query = new StringBuilder("select pr_seq.nextval from dual");
					prNumber = new BigDecimal(genericSqlFactory.selectSingleValue(query.toString(),connection));
					createPurchaseRequest(prNumber,bean.getFacilityId(),inputBean.getAccountSysId(),userId);
					query = new StringBuilder("select mr_screen_required from pr_rules where status = 'A' and facility_id = '").append(bean.getFacilityId());
					query.append("' and account_sys_id = '").append(inputBean.getAccountSysId()).append("'");
					requiredMrScreen = genericSqlFactory.selectSingleValue(query.toString(),connection);

					if ("Y".equalsIgnoreCase(requiredMrScreen)) {
						query = new StringBuilder("select unit_price_required from pr_rules where status = 'A' and facility_id = '").append(bean.getFacilityId());
						query.append("' and account_sys_id = '").append(inputBean.getAccountSysId()).append("'");
						unitPriceRequired = genericSqlFactory.selectSingleValue(query.toString(),connection);
					}
				}
				if( GenericProcess.isBlank(inputBean.getPayloadId())) 
					createNewRequestLineItem(bean,prNumber,lineItem,requiredMrScreen,unitPriceRequired);
				else 
					createNewRequestLineItem(bean,prNumber,lineItem,requiredMrScreen,unitPriceRequired,inputBean.getPayloadId());
				lineItem++;
			}
		}catch (Exception e) {
			e.printStackTrace();
			BaseException be = new BaseException(e);
			be.setRootCause(e);
			throw be;
		}finally {
			if (returnConnection)
				dbManager.returnConnection(connection);
		}
		return prNumber;
	} //end of method

	public void createNewRequestLineItem(ShoppingCartBean bean,BigDecimal prNumber, int lineItem, String requiredMrScreen, String unitPriceRequired) throws Exception{
		createNewRequestLineItem(bean,prNumber,lineItem,requiredMrScreen,unitPriceRequired,null);
	}

	public void createNewRequestLineItem(ShoppingCartBean bean,BigDecimal prNumber, int lineItem, String requiredMrScreen, String unitPriceRequired,String payloadId) throws Exception{
		try {
			StringBuilder column = new StringBuilder("(pr_number,line_item,application,ship_to_location_id,");
			column.append("fac_part_no,item_type,requested_item_type,catalog_id,catalog_company_id,");
			column.append("example_packaging,part_group_no,inventory_group,");
			column.append("application_desc,delivery_point");
			StringBuilder values = new StringBuilder(" select ").append(prNumber.toString()).append(",").append(lineItem).append(",").append(SqlHandler.delimitString(bean.getApplication())).append(",decode(fla.dock_selection_rule,'FIXED',fla.location_id,'')");
			values.append(",").append(SqlHandler.delimitString(bean.getCatPartNo())).append(",'").append(bean.getStockingMethod()).append("','").append(bean.getStockingMethod()).append("',").append(SqlHandler.delimitString(bean.getCatalogId())).append(",'").append(bean.getCatalogCompanyId()).append("',");
			values.append(SqlHandler.delimitString(bean.getExamplePackaging())).append(",").append(bean.getPartGroupNo().toString()).append(",").append(SqlHandler.delimitString(bean.getInventoryGroup())).append(",");
			values.append("fla.application_desc,decode(fla.delivery_point_selection_rule,'FIXED',fla.delivery_point,'')");
			if ("Various".equalsIgnoreCase(bean.getItemId())) {
				column.append(",example_item_id");
				values.append(",").append(bean.getExampleItemId());
			} else {
				column.append(",item_id");
				values.append(",").append(bean.getItemId());
			}
			BigDecimal tmpVal = bean.getCatalogPrice();
			if (!"Y".equalsIgnoreCase(requiredMrScreen)) {
				if (tmpVal != null) {
					column.append(",quoted_price,catalog_price");
					values.append(",").append(tmpVal.toString()).append(",").append(tmpVal.toString());
				}
			}else {
				//don't insert data for catalog_price if pr_rules.unit_price_required is Required
				if (!"Required".equalsIgnoreCase(unitPriceRequired)) {
					if (tmpVal != null) {
						column.append(",quoted_price,catalog_price");
						values.append(",").append(tmpVal.toString()).append(",").append(tmpVal.toString());
						//if pr_rules.unit_price_required = 'Display' || 'ByFacility' then set unit_of_sale_price = catalog_price
						if ("Display".equalsIgnoreCase(unitPriceRequired) || "ByFacility".equalsIgnoreCase(unitPriceRequired)) {
							//get unit_of_sale and unit_of_sale_quantity_per_each from catalog_part_unit_of_sale
							CatalogPartUnitOfSaleBean cpuosBean = getUnitOfSaleAndUnitOfSaleQuantityPerEach(bean.getCatalogId(),bean.getCatalogCompanyId(),bean.getCatPartNo(),bean.getInventoryGroup());							 //0 - unit_of_sale
							if (cpuosBean != null) {
								if (!StringHandler.isBlankString(cpuosBean.getUnitOfSale())) {
									column.append(",unit_of_sale");
									values.append(",'").append(cpuosBean.getUnitOfSale()).append("'");
								}
								if (cpuosBean.getUnitOfSaleQuantityPerEach() != null) {
									try {
										BigDecimal tmp = tmpVal.divide(cpuosBean.getUnitOfSaleQuantityPerEach(),8,BigDecimal.ROUND_HALF_UP);
										column.append(",unit_of_sale_quantity_per_each,unit_of_sale_price");
										values.append(",'").append(cpuosBean.getUnitOfSaleQuantityPerEach()).append("',").append(tmp.floatValue());
									}catch(Exception e) {
										//do nothing
									}
								}
							}
						} //end of unit price is not display
					} //end of catalog_price is not empty
				} //end of pr.unit_price_required <> 'Required'
			} //end of MR screen is required

			//baseline_price
			if (bean.getBaselinePrice() != null) {
				column.append(",baseline_price");
				values.append(",").append(bean.getBaselinePrice().toString());
			}
			//required date
			if (bean.getDateNeeded() != null) {
				column.append(",required_datetime");
				values.append(",").append(DateHandler.getOracleToDateFunction(bean.getDateNeeded()));
			}
			//notes
			if (!StringHandler.isBlankString(bean.getNotes())) {
				column.append(",notes");
				values.append(",").append(SqlHandler.delimitString(bean.getNotes()));
			}
			//critical
			if ("true".equalsIgnoreCase(bean.getCritical())) {
				column.append(",critical");
				values.append(",'y'");
			}
			//quantity
			column.append(",quantity");
			values.append(",").append(bean.getQuantity().toString());
			if( !GenericProcess.isBlank(payloadId) ) {
				// payloadId
				column.append(",payload_id");
				values.append(","+GenericProcess.getSqlString(payloadId));
			}
			//currency
			if (!StringHandler.isBlankString(bean.getCurrencyId())) {
				column.append(",currency_id");
				values.append(",'").append(bean.getCurrencyId()).append("'");
			}
			//order quantity rule
			if(!StringHandler.isBlankString(bean.getOrderQuantityRule())) {
				column.append(",order_quantity_rule");
				values.append(",'").append(bean.getOrderQuantityRule()).append("'");
			}
			//allocate by distance
			column.append(",allocate_by_distance");
			values.append(",fx_allocate_by_distance(fla.company_id,fla.facility_id,fla.application,'").append(bean.getCatalogCompanyId()).append("','");
			values.append(bean.getCatalogId()).append("','").append(bean.getCatPartNo()).append("',").append(bean.getPartGroupNo()).append(")");
			//charge type
			if(!StringHandler.isBlankString(bean.getChargeType())) {
				column.append(",charge_type");
				values.append(",'").append(bean.getChargeType()).append("'");
			}
			//po number
			if(!StringHandler.isBlankString(bean.getPoNumber())) {
				column.append(",po_number");
				values.append(",").append(SqlHandler.delimitString(bean.getPoNumber()));
			}
			//release number
			if(!StringHandler.isBlankString(bean.getReleaseNumber())) {
				column.append(",release_number");
				values.append(",").append(SqlHandler.delimitString(bean.getReleaseNumber()));
			}
			//all_or_none
			if (!StringHandler.isBlankString(bean.getAllOrNone())) {
				column.append(",all_or_none");
				values.append(",").append(SqlHandler.delimitString(bean.getAllOrNone()));
			}
			//customer requisition number
			if (!StringHandler.isBlankString(bean.getCustomerRequisitionNumber())) {
				column.append(",customer_requisition_number");
				values.append(",").append(SqlHandler.delimitString(bean.getCustomerRequisitionNumber()));
			}
			//allocate_by_mfg_lot
			if (!StringHandler.isBlankString(bean.getAllocateByMfgLot())) {
				column.append(",allocate_by_mfg_lot");
				values.append(",").append(SqlHandler.delimitString(bean.getAllocateByMfgLot()));
			}
			//allocate_after
			if (bean.getAllocateAfter() != null) {
				column.append(",allocate_after");
				values.append(",").append(DateHandler.getOracleToDateFunction(bean.getAllocateAfter()));
			}
			//source_file_name
			if (!StringHandler.isBlankString(bean.getSourceFileName())) {
				column.append(",source_file_name");
				values.append(",").append(SqlHandler.delimitString(bean.getSourceFileName()));
			}
			//edit_charge_number
			column.append(",edit_charge_number");
			values.append(",fla.edit_charge_number");
            //hold_for_program
            column.append(",hold_as_customer_owned");
			values.append(",fla.hold_as_customer_owned");

            //cabinet_replenishment
			column.append(",cabinet_replenishment");
			values.append(",pkg_create_mr.fx_customer_owned_cabinet(fla.company_id,fla.facility_id,fla.application,'").append(bean.getCatalogCompanyId()).append("','");
			values.append(bean.getCatalogId()).append("','").append(bean.getCatPartNo()).append("',").append(bean.getPartGroupNo()).append(",'").append(bean.getInventoryGroup()).append("')");

            //Finally put everything together
			values.append(" from fac_loc_app fla where application = ").append(SqlHandler.delimitString(bean.getApplication())).append(" and facility_id = '").append(bean.getFacilityId()).append("'");

			StringBuilder query = new StringBuilder("insert into request_line_item ");
			query.append(column.toString()).append(")").append(values.toString());
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
		}catch (Exception e) {
			throw e;
		}
	}

	public CatalogPartUnitOfSaleBean getUnitOfSaleAndUnitOfSaleQuantityPerEach(String catalogId, String catalogCompanyId, String catPartNo, String inventoryGroup) throws Exception {
		CatalogPartUnitOfSaleBean result = null;
		try {
			StringBuilder query = new StringBuilder("select cpuos.unit_of_sale,cpuos.unit_of_sale_quantity_per_each,cpi.unit_of_sale cpi_unit_of_sale");
			query.append(" from catalog_part_unit_of_sale cpuos, catalog_part_inventory cpi");
			query.append(" where cpuos.cat_part_no = '").append(catPartNo).append("' and cpuos.catalog_id = '").append(catalogId).append("' and cpuos.catalog_company_id = '").append(catalogCompanyId).append("'");
			query.append(" and cpuos.catalog_company_id = cpi.catalog_company_id(+) and cpuos.company_id = cpi.company_id(+) and cpuos.catalog_id = cpi.catalog_id(+)");
			query.append(" and cpuos.cat_part_no = cpi.cat_part_no(+) and cpuos.unit_of_sale = cpi.unit_of_sale(+)");
			query.append(" and cpi.inventory_group(+) = '").append(inventoryGroup).append("' order by cpi.unit_of_sale");
			genericSqlFactory.setBeanObject(new CatalogPartUnitOfSaleBean());
			Collection data = genericSqlFactory.selectQuery(query.toString(),connection);
			Iterator iter = data.iterator();
			while (iter.hasNext()) {
				CatalogPartUnitOfSaleBean bean = (CatalogPartUnitOfSaleBean)iter.next();
				//if unit_of_sale is defined in cpi then use that one
				if (!StringHandler.isBlankString(bean.getCpiUnitOfSale())) {
					return bean;
				}else {
					//otherwise use the first record found in catalog_part_unit_of_sale
					return bean;
				}
			}
		}catch (Exception e) {
			throw e;
		}
		return result;
	} //end of method

	private PurchaseRequestBean getInvoiceAtShippingData (String facilityId, String accountSysId) {
		PurchaseRequestBean resultBean = new PurchaseRequestBean();
		try {
			StringBuilder query = new StringBuilder("select oef.ops_company_id,oef.ops_entity_id,f.def_customer_service_rep_id customer_service_rep_id, igf.customer_id");
			query.append(" from ops_entity_facility oef, facility f, invoice_group_facility igf");
			query.append(" where oef.facility_id = '").append(facilityId).append("'");
			query.append(" and oef.company_id = f.company_id and oef.facility_id = f.facility_id and oef.status = 'ACTIVE'");
			query.append(" and f.company_id = igf.company_id and f.facility_id = igf.facility_id");
			query.append(" and igf.account_sys_id = '").append(accountSysId).append("'");
			genericSqlFactory.setBeanObject(new PurchaseRequestBean());
			Collection<PurchaseRequestBean> beans= genericSqlFactory.selectQuery(query.toString(),connection);
			for (PurchaseRequestBean bean : beans) {
				resultBean.setOpsCompanyId(bean.getOpsCompanyId());
				resultBean.setOpsEntityId(bean.getOpsEntityId());
				resultBean.setCustomerId(bean.getCustomerId());
				resultBean.setCustomerServiceRepId(bean.getCustomerServiceRepId());
				break;
			}
			if (beans.size() > 1)
				MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Facility is served by multiples operating entity: "+facilityId,"Facility is served by multiples operating entity");
		}catch (Exception e) {
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Failed to get operating entity for facility: "+facilityId,"Failed to get operating entity for facility");
		}
		return resultBean;
	} //end of method

	public void createPurchaseRequest(BigDecimal prNumber, String facilityId, String accountSysId, BigDecimal userId) throws Exception{
		try {
			PurchaseRequestBean dataBean = getInvoiceAtShippingData(facilityId,accountSysId);
			StringBuilder query = new StringBuilder("insert into purchase_request (pr_number,requestor,facility_id,account_sys_id,pr_status,request_date");
			query.append(",ops_company_id,ops_entity_id,customer_id,customer_service_rep_id)");
			query.append(" values (").append(prNumber.toString()).append(",").append(userId.toString()).append(",'").append(facilityId).append("','").append(accountSysId).append("','entered',sysdate");
			//ops company id
			if (!StringHandler.isBlankString(dataBean.getOpsCompanyId()))
				query.append(",").append(GenericProcess.getSqlString(dataBean.getOpsCompanyId()));
			else
				query.append(",''");
			//ops entity id
			if (!StringHandler.isBlankString(dataBean.getOpsEntityId()))
				query.append(",").append(GenericProcess.getSqlString(dataBean.getOpsEntityId()));
			else
				query.append(",''");
			//customer id
			if (dataBean.getCustomerId() != null)
				query.append(",").append(dataBean.getCustomerId());
			else
				query.append(",''");
			//CSR
			if (dataBean.getCustomerServiceRepId() != null)
				query.append(",").append(dataBean.getCustomerServiceRepId());
			else
				query.append(",''");

			query.append(")");
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
		}catch (Exception e) {
			throw e;
		}
	} //end of method

	public void createPrAccount(BigDecimal prNumber,int lineItem,String accountNumber,String accountNumber2,String accountNumber3,String accountNumber4,BigDecimal percentage) throws Exception{
		try {
			StringBuilder query = new StringBuilder("insert into pr_account (pr_number,line_item,account_number,account_number2,account_number3,account_number4,percentage)");
			query.append(" values (").append(prNumber).append(",").append(lineItem).append(",").append(accountNumber).append(",").append(accountNumber2).append(",").append(accountNumber3);
			query.append(",").append(accountNumber4).append(",").append(percentage).append(")");
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
		}catch (Exception e) {
			throw e;
		}
	}

	// Larry note;
	// When copy this code, make sure you know what mime type text/html or text/xml you are doing.
   public String postHTMLShoppingCart(String posturl,String postStr) throws BaseException {
	  try {
		 log.debug("posting:"+posturl+"\n\ncontent:"+postStr);
		 URL cartResponseURL = new URL(posturl);
		 TcmisHttpURLConnection httpURLConn = new TcmisHttpURLConnection(cartResponseURL);
		 httpURLConn.connect();
		 httpURLConn.sendRequest(postStr,"text/html; charset=UTF-8");
		 Object o = httpURLConn.getResponse();
		 log.debug("Response:"+o);
		 int responseCode = httpURLConn.getResponseCode();
		 return ""+responseCode;
	  } catch (Exception mfe) {
	  }
	  return "No Response";
   }

} //end of class