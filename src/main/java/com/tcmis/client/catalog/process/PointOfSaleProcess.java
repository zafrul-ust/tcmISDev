package com.tcmis.client.catalog.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.common.beans.*;
import com.tcmis.client.common.process.ShoppingCartProcess;
import com.tcmis.client.common.process.MaterialRequestProcess;
import com.tcmis.client.catalog.beans.CatalogInputBean;
import com.tcmis.client.catalog.beans.PointOfSaleBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.VvCurrencyBean;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;

/**
 * ***************************************************************************
 * Process for PointOfSaleProcess
 *
 * @version 1.0
 *          ***************************************************************************
 */
public class PointOfSaleProcess extends BaseProcess {

	Log log = LogFactory.getLog(this.getClass());
	DbManager dbManager;
	Connection connection = null;
	GenericSqlFactory genericSqlFactory;
	private ResourceLibrary library = null;

	public PointOfSaleProcess(String client) {
		super(client);
	}

	public PointOfSaleProcess(String client, String locale) {
		super(client, locale);
		library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
	}

	public BigDecimal buildPosRequest(CatalogInputBean inputBean,Collection beans,BigDecimal userId) throws BaseException {
		BigDecimal prNumber = new BigDecimal(0);
	   try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager,new FinanceApproverListBean());
			//log.debug(userId+"-"+inputBean);
			//build MR
			prNumber = buildRequest(inputBean,beans,userId);
			//update unit of sale
			updateUosAndCostCenter(prNumber);
			//submit MR
			StringBuilder query = new StringBuilder("update request_line_item set release_date = sysdate,last_updated = sysdate,last_updated_by = ").append(userId).append(",delivery_type = 'Deliver by'");
			query.append(",use_approval_status = 'approved',use_approver = -1").append(",use_approval_date = sysdate,list_approval_status = 'Approved',list_approval_date = sysdate");
            query.append(",charge_approval_status = 'Approved',charge_approval_date = sysdate");
            query.append(",relax_shelf_life = 'y',request_line_status = 'In Progress',category_status = 'Open'");
			query.append(" where pr_number = ").append(prNumber);
            genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
            //update purchase request
            query = new StringBuilder("update purchase_request set pr_status = 'posubmit',submitted_date = sysdate,last_updated = sysdate,");
			query.append("requested_finance_approver = requestor,");
			query.append("last_updated_by = ").append(userId).append(",pos_user = ").append(userId).append(" where pr_number = ").append(prNumber);
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
			//finally allocate MR
            reAllocateMr(prNumber);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		return prNumber;
	}

	String getUosQtyPerEach(PointOfSaleBean bean) {
		 String result = "";
		 try {
      	StringBuilder query = new StringBuilder("select unit_of_sale_quantity_per_each from catalog_part_unit_of_sale");
			query.append(" where company_id = '").append(bean.getCompanyId()).append("' and catalog_id = '").append(bean.getCatalogId()).append("'");
		   query.append(" and cat_part_no = '").append(bean.getCatPartNo()).append("' and unit_of_sale = '").append(bean.getUnitOfSale()).append("'");
			result = genericSqlFactory.selectSingleValue(query.toString(),connection);
		 }catch (Exception e) {
			e.printStackTrace();
		 }
		 return result;
	}

	void updateUosAndCostCenter(BigDecimal prNumber) {
		 try {
      	StringBuilder query = new StringBuilder("select cpi.company_id,cpi.catalog_id,cpi.cat_part_no,cpi.cost_center,cpi.unit_of_sale,rli.catalog_price,rli.line_item");
			query.append(" from catalog_part_inventory cpi,request_line_item rli");
			query.append(" where cpi.company_id = rli.company_id and cpi.catalog_id = rli.catalog_id");
			query.append(" and cpi.cat_part_no = rli.fac_part_no and cpi.part_group_no = rli.part_group_no");
			query.append(" and cpi.inventory_group = rli.inventory_group and rli.pr_number = ").append(prNumber);
			query.append(" order by to_number(line_item)");
			genericSqlFactory.setBeanObject(new PointOfSaleBean());
			Iterator iter = genericSqlFactory.selectQuery(query.toString(),connection).iterator();
			while (iter.hasNext()) {
				PointOfSaleBean bean = (PointOfSaleBean)iter.next();
				StringBuilder updateString = new StringBuilder("");
				if (!StringHandler.isBlankString(bean.getCostCenter())) {
					updateString.append("cost_center = '").append(bean.getCostCenter()).append("',");
				}
				//unit of sale
				String uos = bean.getUnitOfSale();
				if(!StringHandler.isBlankString(uos)) {
					updateString.append("unit_of_sale = '").append(uos).append("',");
					String uosqpe = getUosQtyPerEach(bean);
					if (!StringHandler.isBlankString(uosqpe)) {
						updateString.append("unit_of_sale_quantity_per_each = '").append(uosqpe).append("',");
						BigDecimal catalogPrice = bean.getCatalogPrice();
						if (catalogPrice != null) {
							float catPrice = catalogPrice.floatValue();
                    	float uosQtyPerEach = new Float(uosqpe).floatValue();
                     BigDecimal tmp = new BigDecimal(catPrice / uosQtyPerEach);
                     tmp = tmp.setScale(4,tmp.ROUND_HALF_UP);
							updateString.append("unit_of_sale_price = ").append(tmp.floatValue()).append(",");
						}
					}
				}
				//update rli
				if (updateString.length() > 1) {
					//removing the last commas
					query = new StringBuilder("update request_line_item set ").append(updateString.substring(0,updateString.length()-1));
					query.append(" where pr_number = ").append(prNumber).append(" and line_item = ").append(bean.getLineItem());
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	} //end of method

	private BigDecimal buildRequest(CatalogInputBean inputBean,Collection beans,BigDecimal userId) throws Exception {
		BigDecimal prNumber;
	   try {
			ShoppingCartProcess shoppingCartProcess = new ShoppingCartProcess(this.getClient(),this.getLocale());
			shoppingCartProcess.setFactoryConnection(genericSqlFactory,connection);
			MaterialRequestProcess mrProcess = new MaterialRequestProcess(this.getClient(),this.getLocale(),"");
			mrProcess.setFactoryConnection(genericSqlFactory,connection);
			
			prNumber = new BigDecimal(genericSqlFactory.selectSingleValue("select pr_seq.nextval from dual",connection));
			//purchase request
			shoppingCartProcess.createPurchaseRequest(prNumber,inputBean.getFacilityId(),inputBean.getAccountSysId(),inputBean.getPosRequestorId());

            StringBuilder query = new StringBuilder("select fac_item_charge_type_override from vv_account_sys where company_id = '").append(inputBean.getCompanyId());
            query.append("' and account_sys_id = '").append(inputBean.getAccountSysId()).append("'");
            String facItemChargeTypeOverride =  genericSqlFactory.selectSingleValue(query.toString(),connection);

            Iterator iter = beans.iterator();
			int lineItem = 1;
			Collection prAccountColl = new Vector(0);
			while(iter.hasNext()) {
				ShoppingCartBean bean = (ShoppingCartBean)iter.next();
				//log.debug(bean);
				//request line item
				//if point of sale clerk did not enter a date then set it today
				if (bean.getDateNeeded() == null) {
					Calendar cal = Calendar.getInstance();
					bean.setDateNeeded(cal.getTime());
				}
				shoppingCartProcess.createNewRequestLineItem(bean,prNumber,lineItem,"N/A","N/A");

				//if MD or MB then convert qty and insert UOM, otherwise leave qty the way it is
                if ("MD".equalsIgnoreCase(bean.getItemType()) || "MB".equalsIgnoreCase(bean.getItemType())) {
					query = new StringBuilder("update request_line_item set requested_dispensed_size_unit = '").append(bean.getExamplePackaging()).append("'");
				  	query.append(",quantity = ").append("fx_item_convert_from_unit(fx_best_item(catalog_id,fac_part_no,catalog_company_id,part_group_no),'");
					query.append(bean.getExamplePackaging()).append("',").append(bean.getQuantity()).append(")");
				   query.append(" where pr_number = ").append(prNumber).append(" and line_item = ").append(lineItem);
					genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
				}

                //pr_account
				if ("Directed".equalsIgnoreCase(bean.getPosWorkAreaOption())) {
                    String defaultChargeType = "";
                    //get default charge type
                    query = new StringBuilder("select fla.charge_type_default,fi.part_charge_type from request_line_item rli, fac_loc_app fla, fac_item fi");
                    query.append(" where rli.company_id = fla.company_id and rli.facility_id = fla.facility_id and rli.application = fla.application");
                    query.append(" and rli.catalog_company_id = fi.company_id and rli.catalog_id = fi.facility_id and rli.fac_part_no = fi.fac_part_no");
                    query.append(" and rli.pr_number = ").append(prNumber).append(" and rli.line_item = '").append(lineItem).append("'");
                    String flaChargeTypeDefault = "";
                    String partChargeType = "";
                    genericSqlFactory.setBeanObject(new LineItemViewBean());
                    Iterator iterC = genericSqlFactory.selectQuery(query.toString(),connection).iterator();
                    while (iterC.hasNext()) {
                        LineItemViewBean livBean = (LineItemViewBean)iterC.next();
                        flaChargeTypeDefault = livBean.getChargeTypeDefault();
                        partChargeType = livBean.getPartChargeType();
                        break;
                    }
                    //determining the default charge type
                    //USE fac_loc_app.charge_type default first unless it's overrides by vv_account_sys.fac_item_charge_type_override
                    //here's the logic for overriding fac_loc_app.charge_type_default
                    //vv_account_sys.fac_item_charge_type_override == fac_item.part_charge_type OR vv_accoount_sys.fac_item_charge_type_override = a
                    //then USE fac_item.part_charge_type
                    //vv_account_sys.fac_item_charge_type_override:
                    // d - and fac_item.part_charge_type = d then USE fac_item.part_charge_type (in this case it's d)
                    // i - and fac_item.part_charge_type = i then USE fac_item.part_charge_type (in this case it's i)
                    // a - always USE fac_item.part_charge_type
                    // n - never override => USE fla.charge_type_default
                    if (!StringHandler.isBlankString(flaChargeTypeDefault)) {
                        defaultChargeType = flaChargeTypeDefault;
                    }
                    //override fac_loc_app.charge_type_default if set
                    if (!StringHandler.isBlankString(facItemChargeTypeOverride)) {
                        if (facItemChargeTypeOverride.equals(partChargeType) || "a".equals(facItemChargeTypeOverride)) {
                            if (!StringHandler.isBlankString(partChargeType)) {
                                defaultChargeType = partChargeType;
                            }
                        }
                    }

                    String useApplication = "";
					//because directed_charge for Point of sale MRs: part -> work area
					//get charge number directed_charge
					genericSqlFactory.setBeanObject(new DirectedChargeBean());
					//first check to see if direct is directed
					query = new StringBuilder("select * from table (pkg_directed_charge_util.fx_get_directed_charges('").append(inputBean.getCompanyId()).append("','");
					query.append(inputBean.getFacilityId()).append("','").append(bean.getApplication()).append("','").append(useApplication).append("','");
					query.append(inputBean.getAccountSysId());
					query.append("','','Material','").append(bean.getCatalogCompanyId()).append("','").append(bean.getCatalogId());
					query.append("','").append(bean.getCatPartNo()).append("',").append(bean.getPartGroupNo()).append(")) order by charge_type");
					Collection chargeNumberColl = genericSqlFactory.selectQuery(query.toString(),connection);

                    //check to see if defaultChargeType has data
                    //if it has data then the defaultChargeType is valid otherwise set it to empty
                    if (!StringHandler.isBlankString(defaultChargeType)) {
                        boolean chargeTypeHasData = false;
                        Iterator iterTest = chargeNumberColl.iterator();
                        while(iterTest.hasNext()) {
                            DirectedChargeBean dcBean = (DirectedChargeBean)iterTest.next();
                            if (defaultChargeType.equals(dcBean.getChargeType())) {
                                chargeTypeHasData = true;
                                break;
                            }
                        }
                        if (!chargeTypeHasData) {
                            defaultChargeType = "";
                        }
                    }  //end of testing for data

                    String directedChargeTypeUsed = "";
					String lastChargeType = "";
					String poNumber = "";
					String poLine = "";
					String chargeNumber1 = "''";
					String chargeNumber2 = "''";
					String chargeNumber3 = "''";
					String chargeNumber4 = "''";
					Iterator iter2 = chargeNumberColl.iterator();
					while(iter2.hasNext()) {
						DirectedChargeBean dcBean = (DirectedChargeBean)iter2.next();
                        String currentChargeType = dcBean.getChargeType();
                        if (!StringHandler.isBlankString(defaultChargeType) && !currentChargeType.equals(defaultChargeType)) {
                            continue;
                        }

						if (poNumber.length() == 0 && !StringHandler.isBlankString(dcBean.getPoNumber())) {
							poNumber = dcBean.getPoNumber();
							poLine = dcBean.getPoLine();
						}
						if (lastChargeType.length() == 0 || lastChargeType.equals(currentChargeType)) {
							//replaced this with above because directed_charge now hold charge number both by part -> work area
							if (!StringHandler.isBlankString(dcBean.getChargeNumber1())) {
								chargeNumber1 = SqlHandler.delimitString(dcBean.getChargeNumber1());
							}
							if (!StringHandler.isBlankString(dcBean.getChargeNumber2())) {
								chargeNumber2 = SqlHandler.delimitString(dcBean.getChargeNumber2());
							}
							if (!StringHandler.isBlankString(dcBean.getChargeNumber3())) {
								chargeNumber3 = SqlHandler.delimitString(dcBean.getChargeNumber3());
							}
							if (!StringHandler.isBlankString(dcBean.getChargeNumber4())) {
								chargeNumber4 = SqlHandler.delimitString(dcBean.getChargeNumber4());
							}
							//end of new

							directedChargeTypeUsed = currentChargeType;
							if (dcBean.getPercent() != null) {
								shoppingCartProcess.createPrAccount(prNumber,lineItem,chargeNumber1,chargeNumber2,chargeNumber3,chargeNumber4,dcBean.getPercent());
							}
						}
						lastChargeType = currentChargeType;
					} //end of while loop
					//update charge type and po number
					if (directedChargeTypeUsed.length() > 0) {
						query = new StringBuilder("update request_line_item set charge_type = '").append(directedChargeTypeUsed).append("'");
						if (!StringHandler.isBlankString(poNumber)) {
							query.append(",po_number = ").append(SqlHandler.delimitString(poNumber));
						}
						if (!StringHandler.isBlankString(poLine)) {
							query.append(",release_number = ").append(SqlHandler.delimitString(poLine));
						}
						query.append(" where pr_number = ").append(prNumber).append(" and line_item = ").append(lineItem);
						genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
					}else {
						MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Point of sale directed charge error","Directed charge expected but empty - pr_number:"+prNumber);
					}
				}else {
					//user entered charge number
					if (lineItem == 1) {
						//since I am only allow one set of charge numbers per MR then the charge numbers is only store on the first record
						prAccountColl = mrProcess.getChargeNumberFromString(bean.getChargeNumber());
					}
					Iterator iter2 = prAccountColl.iterator();
					while (iter2.hasNext()) {
						PrAccountBean prAccountBean = (PrAccountBean)iter2.next();
						String chargeNumber1 = "''";
						String chargeNumber2 = "''";
						String chargeNumber3 = "''";
						String chargeNumber4 = "''";
						if (!StringHandler.isBlankString(prAccountBean.getAccountNumber())) {
							chargeNumber1 = SqlHandler.delimitString(prAccountBean.getAccountNumber());
						}
						if (!StringHandler.isBlankString(prAccountBean.getAccountNumber2())) {
							chargeNumber2 = SqlHandler.delimitString(prAccountBean.getAccountNumber2());
						}
						if (!StringHandler.isBlankString(prAccountBean.getAccountNumber3())) {
							chargeNumber3 = SqlHandler.delimitString(prAccountBean.getAccountNumber3());
						}
						if (!StringHandler.isBlankString(prAccountBean.getAccountNumber4())) {
							chargeNumber4 = SqlHandler.delimitString(prAccountBean.getAccountNumber4());
						}
						shoppingCartProcess.createPrAccount(prNumber,lineItem,chargeNumber1,chargeNumber2,chargeNumber3,chargeNumber4,prAccountBean.getPercentage());
					}
				}
				lineItem++;
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return prNumber;
	}

	public String getUserName(PointOfSaleInventoryViewBean inputBean) throws BaseException {
		dbManager = new DbManager(getClient(),this.getLocale());
		genericSqlFactory = new GenericSqlFactory(dbManager,new PointOfSaleInventoryViewBean());
		String query = "select fx_personnel_id_to_name(requestor) from purchase_request where pr_number = "+inputBean.getPrNumber();
		return genericSqlFactory.selectSingleValue(query);
	} //end of method

	public FinanceApproverListBean getUserOrderingLimit(PointOfSaleInventoryViewBean inputBean) throws BaseException {
		FinanceApproverListBean result = new FinanceApproverListBean();
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager,new FinanceApproverListBean());
			String query = "select count(*) from purchase_request pr, pr_rules p, request_line_item rli where pr.pr_number = "+inputBean.getPrNumber()+
			" and pr.pr_number = rli.pr_number and pr.company_id = rli.company_id and rli.charge_type = p.charge_type"+
			" and pr.company_id = p.company_id and pr.facility_id = p.facility_id and pr.account_sys_id = p.account_sys_id"+
			" and p.approver_required = 'y' and p.status = 'A'";
			if (!"0".equalsIgnoreCase(genericSqlFactory.selectSingleValue(query,connection))) {
				query = "select fal.*,pg.currency_id from purchase_request pr, finance_approver_list fal, facility f, price_group pg"+
				" where pr.pr_number = "+inputBean.getPrNumber()+
				" and pr.company_id = fal.company_id and pr.facility_id = fal.facility_id and pr.requestor = fal.personnel_id"+
				" and pr.company_id = f.company_id and pr.facility_id = f.facility_id and f.company_id = pg.company_id and f.price_group_id = pg.price_group_id";
				Collection dataColl = genericSqlFactory.selectQuery(query,connection);
				Iterator iter = dataColl.iterator();
				while (iter.hasNext()) {
					//load exchange rate
					result = (FinanceApproverListBean)iter.next();
					if (!StringHandler.isBlankString(result.getCurrencyId())) {
						genericSqlFactory.setBeanObject(new VvCurrencyBean());
						query = "select currency_id,currency_description,fx_conversion_rate(currency_id,'"+result.getCurrencyId()+"',sysdate, nvl((select ops_entity_id from purchase_request where PR_NUMBER = "+inputBean.getPrNumber()+"), 'HAASTCMUSA')) exchange_rate"+
						" from vv_currency order by currency_description";
						result.setCurrencyColl(genericSqlFactory.selectQuery(query,connection));
					}
					//throw a break here since I am only expecting 1 record from finance_approver_list sql
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

	public Collection getValidBinData(PointOfSaleInventoryViewBean inputBean) throws BaseException {
		dbManager = new DbManager(getClient(),this.getLocale());
		genericSqlFactory = new GenericSqlFactory(dbManager,new PointOfSaleInventoryViewBean());
		String query = "select distinct bin from facility_ig_bin  where status = 'A' and inventory_group = '"+inputBean.getInventoryGroup()+"' order by bin";
		return genericSqlFactory.selectQuery(query);
	} //end of method

	public Collection getTappableData(PointOfSaleInventoryViewBean inputBean) throws BaseException {
		dbManager = new DbManager(getClient(),this.getLocale());
		genericSqlFactory = new GenericSqlFactory(dbManager,new PointOfSaleInventoryViewBean());
		String query = "select receipt_id,bin from tappable_inventory_view where item_id = "+inputBean.getItemId()+" and inventory_group = '"+inputBean.getInventoryGroup()+"' order by receipt_id,bin";
		return genericSqlFactory.selectQuery(query);
	} //end of method

   public Collection getPOSReceiptData(BigDecimal prNumber) throws Exception {
		dbManager = new DbManager(getClient(),this.getLocale());
		genericSqlFactory = new GenericSqlFactory(dbManager,new PointOfSaleBean());
		StringBuilder query = new StringBuilder("select * from point_of_sale_receipt_view where pr_number = ").append(prNumber);
      query.append(" order by to_number(line_item)");
		return genericSqlFactory.selectQuery(query.toString());
   }

	public String submit(PersonnelBean personnelBean, PointOfSaleInventoryViewBean inputBean, Collection beans) throws BaseException {
		String result = "OK";
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager,new PointOfSaleInventoryViewBean());
			//log. debug("here in submit:"+inputBean.toString());
			String lastLineItem = "";
			boolean qtyChanged = false;
			Calendar cal = Calendar.getInstance();
			Iterator iter = beans.iterator();
			while(iter.hasNext()) {
				PointOfSaleInventoryViewBean bean = (PointOfSaleInventoryViewBean)iter.next();
				if (log.isDebugEnabled()) {
					//log.debug(bean.toString());
				}
				if (!lastLineItem.equalsIgnoreCase(bean.getLineItem())) {
					if (!bean.getMrQuantity().equals(bean.getSumOfQuantityPicked())) {
						qtyChanged = true;
						updateRliQty(personnelBean.getPersonnelId(),inputBean.getPrNumber(),bean.getLineItem(),bean.getSumOfQuantityPicked());
					}
					lastLineItem = bean.getLineItem();
				}
				if (bean.getQuantityAllocated() == null || (new BigDecimal(0)).equals(bean.getQuantityAllocated())) continue;
				bean.setPrNumber(inputBean.getPrNumber());
				bean.setReleaseDate(cal.getTime());
				String[] tmpResult = createIssue(personnelBean,bean);
				result = tmpResult[1];
				log.debug("*"+tmpResult[0]+"-"+tmpResult[1]+"*");
				if (StringHandler.isBlankString(result)) {
					result = confirmIssue(personnelBean,new BigDecimal(tmpResult[0]),bean.getReleaseDate());
					//log.debug(result);
				}
				//log.debug(result);
				pLineItemAllocate(inputBean.getPrNumber(),bean.getLineItem(),personnelBean.getCompanyId());
				allocateReceipt(bean.getReceiptId());
			} //end of loop

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		return result;
	}

	private void allocateReceipt(BigDecimal receiptId) {
		try {
			//call store procedure to reallocate MR
			Collection cin = new Vector(1);
			cin.add(receiptId);
			genericSqlFactory.doProcedure(connection,"p_receipt_allocate", cin);
		}catch (Exception e) {
			e.printStackTrace();
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","p_receipt_allocate error (pr_number: "+receiptId+")","POS Error occured while trying to call procedure");
		}
	}
	
	
	private void pLineItemAllocate(BigDecimal prNumber,String lineItem,String companyId)
	{
		try {
			//call store procedure to reallocate MR
			Collection cin = new Vector(1);
			cin.add(prNumber);
			cin.add(lineItem);
			cin.add("Y");
			cin.add(companyId);
			genericSqlFactory.doProcedure(connection,"p_line_item_allocate", cin);
		}catch (Exception e) {
			e.printStackTrace();
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","p_line_item_allocate error (pr_number: "+prNumber+",line_item:"+lineItem+")","POS Error occured while trying to call procedure");
		}
	}

	private void reAllocateMr(BigDecimal prNumber) {
		try {
			//call store procedure to reallocate MR
			Collection cin = new Vector(1);
			cin.add(prNumber);
			genericSqlFactory.doProcedure(connection,"p_mr_allocate", cin);
		}catch (Exception e) {
			e.printStackTrace();
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","p_mr_allocate error (pr_number: "+prNumber+")","POS Error occured while trying to call procedure");
		}
	}

	void updateRliQty(int clerkId, BigDecimal prNumber, String lineItem, BigDecimal pickedQty) {
		try {
			String query = "";
			//first check to see if picked qty is zero - cancel
			if (pickedQty.intValue() == 0) {
				query = "update request_line_item set quantity = 0,last_updated = sysdate,last_updated_by = "+clerkId+",cancel_status = 'canceled',"+
				"cancel_authorizer = "+clerkId+",cancel_action_date = sysdate,cancel_comment = decode(cancel_comment,null,'',cancel_comment||'; ')||'Set to canceled because picked qty is zero',"+
				"request_line_status = 'Cancelled', category_status = 'Cancelled' where pr_number = "+prNumber+" and line_item = '"+lineItem+"'";
			}else {
				query = "update request_line_item set quantity = fx_item_convert_from_unit(fx_best_item(catalog_id,fac_part_no,catalog_company_id,part_group_no),requested_dispensed_size_unit,"+pickedQty+"),last_updated = sysdate,last_updated_by = "+clerkId+
				" where pr_number = "+prNumber+" and line_item = '"+lineItem+"'";
			}
			genericSqlFactory.deleteInsertUpdate(query,connection);
		}catch (Exception e) {
			e.printStackTrace();
		}
	} //end of method

	private String[] createIssue(PersonnelBean personnelBean, PointOfSaleInventoryViewBean inputBean) throws BaseException {
		String[] result = new String[2];
		//call store procedure to add part to catalog
		Collection cin = new Vector();
		cin.add(inputBean.getPrNumber());
		cin.add(inputBean.getLineItem());
		cin.add(inputBean.getHub());
		cin.add(inputBean.getReceiptId());
		cin.add(inputBean.getItemId());
		cin.add(inputBean.getReleaseDate());
		cin.add(new BigDecimal(getUOMQtyForReceipt(inputBean)));
		cin.add(inputBean.getReleaseDate());
		cin.add(new BigDecimal(getBatchNo()));
		cin.add("");
		cin.add("");
		cin.add(new BigDecimal(personnelBean.getPersonnelId()));
		Collection cout = new Vector(2);
		cout.add(new Integer(java.sql.Types.NUMERIC));		//issue_id
		cout.add(new Integer(java.sql.Types.VARCHAR));     //error value
		log.debug(cin.toString());
		Vector error = (Vector)genericSqlFactory.doProcedure(connection,"p_issue_insert", cin, cout);
		if (log.isDebugEnabled()) {
			log.debug(error.toString());
		}
		result[0] = error.get(0).toString();
		result[1] = (String)error.get(1);

		if (!StringHandler.isBlankString(result[1])) {
			String esub = "Error while calling procedure to p_issue_insert (receiptId: "+inputBean.getReceiptId()+")";
			String emsg = "Procedure: p_issue_insert has an error.\n";
			emsg += result;
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",esub,emsg);
			result[1] = library.getString("generic.error");
		}
		return result;
	}

	private String confirmIssue(PersonnelBean personnelBean, BigDecimal issueId, Date deliverDate) throws BaseException {
		String result = "OK";
		//call store procedure to add part to catalog
		Collection cin = new Vector(3);
		cin.add(issueId);
		cin.add(deliverDate);
		cin.add(new BigDecimal(personnelBean.getPersonnelId()));
		Collection cout = new Vector(1);
		cout.add(new Integer(java.sql.Types.VARCHAR));     //error value
		log.debug(cin.toString());
		Collection procedureData = genericSqlFactory.doProcedure(connection,"p_issue_ship_confirm", cin, cout);
		Iterator i11 = procedureData.iterator();
		while (i11.hasNext()) {
			Object tmp = i11.next();
			if (tmp != null) {
				result = tmp.toString();
			}
		}
		if (!"OK".equalsIgnoreCase(result)) {
			String esub = "Error while calling procedure to p_issue_ship_confirm (issueId: "+issueId+")";
			String emsg = "Procedure: p_issue_ship_confirm has an error.\n";
			emsg += result;
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",esub,emsg);
			result = library.getString("generic.error");
		}
		return result;
	}

	private String getUOMQtyForReceipt(PointOfSaleInventoryViewBean bean) throws BaseException {
		String query = "select fx_item_convert_from_unit("+bean.getItemId()+",requested_dispensed_size_unit,"+bean.getQuantityAllocated()+") qty from request_line_item where pr_number = "+bean.getPrNumber()+" and line_item = '"+bean.getLineItem()+"'";
		return genericSqlFactory.selectSingleValue(query,connection);
	} //end of method

	private String getBatchNo() throws BaseException {
		String query = "select print_batch_seq.nextval batch_no from dual";
		return genericSqlFactory.selectSingleValue(query,connection);
	} //end of method

	public String openTap(PointOfSaleInventoryViewBean inputBean) throws BaseException {
		String result = "OK";
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager,new PointOfSaleInventoryViewBean());
			//first check to see if bin is valid
			String query = "select bin from facility_ig_bin where inventory_group = '"+inputBean.getInventoryGroup()+"'"+
			" and lower(bin) = lower('"+inputBean.getBin().trim()+"')";
			String tmpVal = genericSqlFactory.selectSingleValue(query,connection);
			if (StringHandler.isBlankString(tmpVal)) {
				genericSqlFactory.deleteInsertUpdate("insert into vv_hub_bins(branch_plant,bin)"+
						" select b.hub,"+SqlHandler.delimitString(inputBean.getBin().trim())+" bin from inventory_group_definition b"+
						" where b.inventory_group = '"+inputBean.getInventoryGroup()+"'",connection);
			}else {
				inputBean.setBin(tmpVal);
			}

			//call store procedure to add part to catalog
			Collection cin = new Vector(3);
			cin.add(inputBean.getReceiptId());
			cin.add(inputBean.getItemId());
			cin.add(inputBean.getBin());
			Collection cout = new Vector(2);
			cout.add(new Integer(java.sql.Types.NUMERIC));		//receipt_id
			cout.add(new Integer(java.sql.Types.VARCHAR));     //error value

			Collection procedureData = genericSqlFactory.doProcedure(connection,"p_tap", cin, cout);
			Iterator i11 = procedureData.iterator();
			int count = 0;
			while (i11.hasNext()) {
				Object tmp = i11.next();
				if (count == 1) {
					if (tmp != null) {
						result = tmp.toString();
					}
				}
				count++;
			}
			if (!"OK".equalsIgnoreCase(result)) {
				String esub = "Error while calling procedure to tap (receiptId: "+inputBean.getReceiptId()+")";
				String emsg = "Procedure: p_tap has an error.\n";
				emsg += result;
				MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",esub,emsg);
				result = library.getString("generic.error");
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
	}

	public String closeTap(PointOfSaleInventoryViewBean inputBean) throws BaseException {
		String result = "OK";
		//call store procedure to add part to catalog
		Collection cin = new Vector(1);
		cin.add(inputBean.getReceiptId());
		Collection cout = new Vector(2);
		cout.add(new Integer(java.sql.Types.NUMERIC));		//issue_id
		cout.add(new Integer(java.sql.Types.VARCHAR));     //error value
		dbManager = new DbManager(getClient(),this.getLocale());
		genericSqlFactory = new GenericSqlFactory(dbManager);
		Collection procedureData = genericSqlFactory.doProcedure("p_tap_close", cin, cout);
		log.debug(cin+"-"+procedureData);
		Iterator i11 = procedureData.iterator();
		int count = 0;
		while (i11.hasNext()) {
			Object tmp = i11.next();
			if (count == 1) {
				if (tmp != null) {
					result = tmp.toString();
				}
			}
			count++;
		}
		log.debug("*"+result+"*");
		if (!"OK".equalsIgnoreCase(result)) {
			String esub = "Error while calling procedure to close tap (receiptId: "+inputBean.getReceiptId()+")";
			String emsg = "Procedure: p_tap_close has an error.\n";
			emsg += result;
			MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",esub,emsg);
			result = library.getString("generic.error");
		}
		return result;
	}


	private Collection getPointOfSaleInventoryView(BigDecimal prNumber, String facilityId) throws BaseException {
		SearchCriteria criteria = new SearchCriteria();
		if (prNumber != null) {
			criteria.addCriterion("prNumber", SearchCriterion.EQUALS, prNumber.toString());
		}
		if (!StringHandler.isBlankString(facilityId)) {
			criteria.addCriterion("facilityId", SearchCriterion.EQUALS, facilityId);
		}
		SortCriteria scriteria = new SortCriteria();
		scriteria.addCriterion("prNumber");
		scriteria.addCriterion("lineItem");
		scriteria.addCriterion("itemId");
		scriteria.addCriterion("expireDate");
		scriteria.addCriterion("receiptId");
		scriteria.addCriterion("bin");

		return genericSqlFactory.select(criteria, scriteria,connection,"point_of_sale_inventory_view ");
	}

	public Object[] showResult(PointOfSaleInventoryViewBean inputBean) throws BaseException {
		Vector bv = new Vector();
		HashMap m1 = new HashMap();
		HashMap m2 = new HashMap();
		Integer i1 = null;
		HashMap map = null;
		Integer i2 = null;
		String lineItem = null;
		try {
			dbManager = new DbManager(getClient(),this.getLocale());
			connection = dbManager.getConnection();
			genericSqlFactory = new GenericSqlFactory(dbManager,new PointOfSaleInventoryViewBean());

			bv = (Vector)getPointOfSaleInventoryView(inputBean.getPrNumber(), inputBean.getFacilityId());
			PointOfSaleInventoryViewBean bean = null;
			String query = "";
			for (int i = 0; i < bv.size(); i++) {
				bean = (PointOfSaleInventoryViewBean) bv.get(i);
				lineItem = bean.getLineItem();

				//calculating number of rows within lineItem
				if (m1.get(lineItem) == null) {
					i1 = new Integer(0);
					m1.put(lineItem, i1);
					map = new HashMap();
					m2.put(lineItem, map);
				}
				i1 = (Integer) m1.get(lineItem);
				i1 = new Integer(i1.intValue() + 1);
				m1.put(lineItem, i1);

				StringBuilder receipt = new StringBuilder("receipt");
				if (bean.getReceiptId() == null) {
					receipt.append(lineItem);
					//since there is no inventory, use lineItem as key
					bean.setReceiptId(new BigDecimal(lineItem));
				}else {
					receipt.append(bean.getReceiptId().toString());
				}

				if (map.get(receipt.toString()) == null) {
					i2 = new Integer(0);
					map.put(receipt.toString(), i2);
				}
				i2 = (Integer) map.get(receipt.toString());
				i2 = new Integer(i2.intValue() + 1);
				map.put(receipt.toString(), i2);

				//calculate ordered and avaliable qty
				if ("MD".equalsIgnoreCase(bean.getItemType()) || "MB".equalsIgnoreCase(bean.getItemType())) {
					//first convert ordered qty from dispensed UOM to requested dispensed UOM
					if (bean.getItemId() != null && !StringHandler.isBlankString(bean.getRequestedDispensedSizeUnit()) &&
							bean.getMrQuantity() != null) {
						query = "select to_number(fx_sig_fig(fx_item_convert_to_unit("+bean.getItemId()+",'"+bean.getRequestedDispensedSizeUnit()+"',"+bean.getMrQuantity()+"),4)) ordered_qty from dual";
						bean.setMrQuantity(new BigDecimal(genericSqlFactory.selectSingleValue(query,connection)));
					}
					//convert inventory on hand to requested dispensed UOM
					if (bean.getItemId() != null && !StringHandler.isBlankString(bean.getRequestedDispensedSizeUnit()) &&
							bean.getQuantityOnHand() != null) {
						query = "select round(fx_item_convert_to_unit("+bean.getItemId()+",'"+bean.getRequestedDispensedSizeUnit()+"',"+bean.getQuantityOnHand()+"),2) quantity_available from dual";
						bean.setQuantityOnHand(new BigDecimal(genericSqlFactory.selectSingleValue(query,connection)));
					}
					//convert allocated qty from dispensed UOM to requested dispensed UOM
					if (bean.getItemId() != null && !StringHandler.isBlankString(bean.getRequestedDispensedSizeUnit()) &&
							bean.getQuantityAllocated() != null && !(new BigDecimal(0)).equals(bean.getQuantityAllocated())) {
						query = "select round(fx_item_convert_to_unit("+bean.getItemId()+",'"+bean.getRequestedDispensedSizeUnit()+"',"+bean.getQuantityAllocated()+"),4) allocated_qty from dual";
						bean.setQuantityAllocated(new BigDecimal(genericSqlFactory.selectSingleValue(query,connection)));
					}
					bean.setPackaging(bean.getRequestedDispensedSizeUnit());
				}
			}
			//cancel MR is no pick data
			if (bv.size() == 0) {
				String esub = "tcmIS encountered an error while getting pick info: "+inputBean.getPrNumber();
				MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",esub,"POS no data while trying to call point_of_sale_inventory_view");
				cancelPOSRequest(inputBean.getPrNumber(),true);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			genericSqlFactory = null;
		}
		Object[] objs = {bv, m1,m2};
		return objs;
	} //end of method

	public void cancelPOSRequest(BigDecimal prNumber, boolean callWithConnection) {
		try {
			String query = "update request_line_item set release_date = null,cancel_status = 'canceled',cancel_authorizer=-1,"+
			"cancel_action_date = sysdate,cancel_comment='point_of_sale_inventory_view failed to return data',"+
			"request_line_status='Cancelled',category_status='Cancelled' where pr_number = "+prNumber;
			if (callWithConnection) {
				genericSqlFactory.deleteInsertUpdate(query,connection);
				reAllocateMr(prNumber);
			}else {
				try {
					dbManager = new DbManager(getClient(),this.getLocale());
					connection = dbManager.getConnection();
					genericSqlFactory = new GenericSqlFactory(dbManager);
					genericSqlFactory.deleteInsertUpdate(query,connection);
					reAllocateMr(prNumber);
				}catch (Exception ee) {
					ee.printStackTrace();
				}finally {
					dbManager.returnConnection(connection);
					dbManager = null;
					connection = null;
					genericSqlFactory = null;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

} //end of class