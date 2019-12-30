package com.tcmis.client.common.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.common.beans.*;
import com.tcmis.client.order.beans.RequestLineItemBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * Process for MaterialRequestProcess
 * @version 1.0
 *****************************************************************************/
public class MaterialRequestDataProcess extends BaseProcess {


	Log log = LogFactory.getLog(this.getClass());

	public MaterialRequestDataProcess(String client,String locale)  {
		super(client,locale);
	}

	public final int FINANCE_DATA = 0;
	public final int RELEASER_DATA = 1;
	public final int USE_DATA = 2;
	public final int LIST_DATA = 3;
    public final int APPROVAL_DATA = 4;
    private final int NUMBER_OF_ELEMENTS = 5;
	private PurchaseRequestBean prBean = null;

    public Collection getApproverDetails(LineItemViewBean inputBean) throws BaseException {
        Collection result = new Vector();
        try {
            DbManager dbManager = new DbManager(getClient(),getLocale());
            GenericSqlFactory factory = new GenericSqlFactory(dbManager,new ChargeNumberApprovalDetailBean());
            StringBuilder query = new StringBuilder("select * from table(pkg_chg_num_approval_search.fx_approval_search_mgr(");
            query.append(inputBean.getPrNumber());
            if (!StringHandler.isBlankString(inputBean.getLineItem())) {
                query.append(",'").append(inputBean.getLineItem()).append("'");
            }
            query.append(")) order by line_item,account_number,account_number2,account_number3,account_number4");
            Iterator iter = factory.selectQuery(query.toString()).iterator();
            Vector processedData = new Vector();
            Vector processedAccountData = new Vector();
            while(iter.hasNext()) {
                ChargeNumberApprovalDetailBean bean = (ChargeNumberApprovalDetailBean)iter.next();
                if (!StringHandler.isBlankString(bean.getAccountApproverName1())) {
                    //account approver
                    if (!processedAccountData.contains(bean.getPrNumber()+"-"+bean.getLineItem()+bean.getAccountApproverName1())) {
                        MrApproverDetailBean accountDataBean = new MrApproverDetailBean();
                        accountDataBean.setMrLine(bean.getPrNumber()+"-"+bean.getLineItem());
                        accountDataBean.setApprovalType(bean.getAccountApproverLabel());
                        accountDataBean.setApprovalStatus(bean.getAccountApprovalStatus());
                        if ("Pending".equals(bean.getAccountApprovalStatus())) {
                            StringBuilder tmpName = new StringBuilder(StringHandler.emptyIfNull(bean.getAccountApproverName1()));
                            StringBuilder tmpEmail = new StringBuilder(StringHandler.emptyIfNull(bean.getAccountApproverEmail1()));
                            StringBuilder tmpPhone = new StringBuilder(StringHandler.emptyIfNull(bean.getAccountApproverPhone1()));
                            if (!StringHandler.isBlankString(bean.getAccountApproverName2())) {
                                tmpName.append("; ").append(StringHandler.emptyIfNull(bean.getAccountApproverName2()));
                                tmpEmail.append("; ").append(StringHandler.emptyIfNull(bean.getAccountApproverEmail2()));
                                tmpPhone.append("; ").append(StringHandler.emptyIfNull(bean.getAccountApproverPhone2()));
                            }
                            accountDataBean.setApproverName(tmpName.toString());
                            accountDataBean.setApprovalEmail(tmpEmail.toString());
                            accountDataBean.setApprovalPhone(tmpPhone.toString());
                        }else {
                            accountDataBean.setApproverName(bean.getAccountApproverName1());
                            accountDataBean.setApprovalEmail(bean.getAccountApproverEmail1());
                            accountDataBean.setApprovalPhone(bean.getAccountApproverPhone1());
                        }
                        accountDataBean.setApprovalDate(bean.getAccountApprovalDate());
                        result.add(accountDataBean);
                        processedAccountData.addElement(accountDataBean.getMrLine()+bean.getAccountApproverName1());
                    }
                }

                //determine which charge number to show
                StringBuilder tmpChargeNumber = new StringBuilder("Charge number: ");
                boolean hasChargeNumber = false;
                if ("Y".equals(bean.getChargeField1Control())) {
                    if (hasChargeNumber) {
                        tmpChargeNumber.append("; ").append(bean.getAccountNumber());
                    }else {
                        tmpChargeNumber.append(bean.getAccountNumber());
                        hasChargeNumber = true;
                    }
                }
                if ("Y".equals(bean.getChargeField2Control())) {
                    if (hasChargeNumber) {
                        tmpChargeNumber.append("; ").append(bean.getAccountNumber2());
                    }else {
                        tmpChargeNumber.append(bean.getAccountNumber2());
                        hasChargeNumber = true;
                    }
                }
                if ("Y".equals(bean.getChargeField3Control())) {
                    if (hasChargeNumber) {
                        tmpChargeNumber.append("; ").append(bean.getAccountNumber3());
                    }else {
                        tmpChargeNumber.append(bean.getAccountNumber3());
                        hasChargeNumber = true;
                    }
                }
                if ("Y".equals(bean.getChargeField4Control())) {
                    if (hasChargeNumber) {
                        tmpChargeNumber.append("; ").append(bean.getAccountNumber4());
                    }else {
                        tmpChargeNumber.append(bean.getAccountNumber4());
                        hasChargeNumber = true;
                    }
                }

                //check to see if charge number already processed
                //the reason that I need this check is because a given charge number can fail for multiple rules
                //because charge number will evaluate the same for charge number regardless of rule
                if (processedData.contains(bean.getLineItem()+tmpChargeNumber) || "TCM, Data Center".equals(bean.getApproverLevel1Name1())) {
                    //already processed go to next record
                    continue;
                }else {
                    processedData.addElement(bean.getLineItem()+tmpChargeNumber);
                }

                //level 1
                if (new BigDecimal(2).equals(bean.getNoLevel1ChgAcctApprover())) {
                    if (!StringHandler.isBlankString(bean.getApproverLevel1Name1()) &&
                        !StringHandler.isBlankString(bean.getApproverLevel1Name2()) ) {
                        //approver 1
                        MrApproverDetailBean dataBean = new MrApproverDetailBean();
                        dataBean.setMrLine(bean.getPrNumber()+"-"+bean.getLineItem());
                        dataBean.setApprovalType(tmpChargeNumber.toString());
                        StringBuilder tmpStatus = new StringBuilder("Level 1.1 ");
                        tmpStatus.append(bean.getLevel1ApprovalStatus1());
                        if("Y".equals(bean.getApproveByPrice()) && bean.getApprovalLevel1Limit() != null) {
                            tmpStatus.append(" (").append(bean.getApprovalLevel1Limit()).append(" ").append(bean.getApprovalLimitCurrencyId()).append(")");
                        }
                        dataBean.setApprovalStatus(tmpStatus.toString());
                        dataBean.setApproverName(bean.getApproverLevel1Name1());
                        dataBean.setApprovalEmail(bean.getApproverLevel1Email1());
                        dataBean.setApprovalPhone(bean.getApproverLevel1Phone1());
                        dataBean.setApprovalDate(bean.getApprovalLevel1ApprDate1());
                        result.add(dataBean);
                        //approver 2
                        MrApproverDetailBean dataBean2 = new MrApproverDetailBean();
                        dataBean2.setMrLine(bean.getPrNumber()+"-"+bean.getLineItem());
                        dataBean2.setApprovalType(tmpChargeNumber.toString());
                        tmpStatus = new StringBuilder("Level 1.2 ");
                        tmpStatus.append(bean.getLevel1ApprovalStatus2());
                        if("Y".equals(bean.getApproveByPrice()) && bean.getApprovalLevel1Limit() != null) {
                            tmpStatus.append(" (").append(bean.getApprovalLevel1Limit()).append(" ").append(bean.getApprovalLimitCurrencyId()).append(")");
                        }
                        dataBean2.setApprovalStatus(tmpStatus.toString());
                        dataBean2.setApproverName(bean.getApproverLevel1Name2());
                        dataBean2.setApprovalEmail(bean.getApproverLevel1Email2());
                        dataBean2.setApprovalPhone(bean.getApproverLevel1Phone2());
                        dataBean2.setApprovalDate(bean.getApprovalLevel1ApprDate2());
                        result.add(dataBean2);
                    }
                }else if (new BigDecimal(1).equals(bean.getNoLevel1ChgAcctApprover()) && !StringHandler.isBlankString(bean.getApproverLevel1Name1())) {
                    //approver 1
                    MrApproverDetailBean dataBean = new MrApproverDetailBean();
                    dataBean.setMrLine(bean.getPrNumber()+"-"+bean.getLineItem());
                    dataBean.setApprovalType(tmpChargeNumber.toString());
                    StringBuilder tmpStatus = new StringBuilder("Level 1 ");
                    tmpStatus.append(bean.getLevel1ApprovalStatus1());
                    if("Y".equals(bean.getApproveByPrice()) && bean.getApprovalLevel1Limit() != null) {
                        tmpStatus.append(" (").append(bean.getApprovalLevel1Limit()).append(" ").append(bean.getApprovalLimitCurrencyId()).append(")");
                    }
                    dataBean.setApprovalStatus(tmpStatus.toString());
                    dataBean.setApproverName(bean.getApproverLevel1Name1());
                    dataBean.setApprovalEmail(bean.getApproverLevel1Email1());
                    dataBean.setApprovalPhone(bean.getApproverLevel1Phone1());
                    dataBean.setApprovalDate(bean.getApprovalLevel1ApprDate1());
                    result.add(dataBean);
                }
                //level 2
                if (new BigDecimal(2).equals(bean.getNoLevel2ChgAcctApprover())) {
                    if (!StringHandler.isBlankString(bean.getApproverLevel2Name1()) &&
                        !StringHandler.isBlankString(bean.getApproverLevel2Name2()) ) {
                        //approver 1
                        MrApproverDetailBean dataBean = new MrApproverDetailBean();
                        dataBean.setMrLine(bean.getPrNumber()+"-"+bean.getLineItem());
                        dataBean.setApprovalType(tmpChargeNumber.toString());
                        StringBuilder tmpStatus = new StringBuilder("Level 2.1 ");
                        tmpStatus.append(bean.getLevel2ApprovalStatus1());
                        if("Y".equals(bean.getApproveByPrice()) && bean.getApprovalLevel2Limit() != null) {
                            tmpStatus.append(" (").append(bean.getApprovalLevel2Limit()).append(" ").append(bean.getApprovalLimitCurrencyId()).append(")");
                        }
                        dataBean.setApprovalStatus(tmpStatus.toString());
                        dataBean.setApproverName(bean.getApproverLevel2Name1());
                        dataBean.setApprovalEmail(bean.getApproverLevel2Email1());
                        dataBean.setApprovalPhone(bean.getApproverLevel2Phone1());
                        dataBean.setApprovalDate(bean.getApprovalLevel2ApprDate1());
                        result.add(dataBean);
                        //approver 2
                        MrApproverDetailBean dataBean2 = new MrApproverDetailBean();
                        dataBean2.setMrLine(bean.getPrNumber()+"-"+bean.getLineItem());
                        dataBean2.setApprovalType(tmpChargeNumber.toString());
                        tmpStatus = new StringBuilder("Level 2.2 ");
                        tmpStatus.append(bean.getLevel2ApprovalStatus2());
                        if("Y".equals(bean.getApproveByPrice()) && bean.getApprovalLevel2Limit() != null) {
                            tmpStatus.append(" (").append(bean.getApprovalLevel2Limit()).append(" ").append(bean.getApprovalLimitCurrencyId()).append(")");
                        }
                        dataBean2.setApprovalStatus(tmpStatus.toString());
                        dataBean2.setApproverName(bean.getApproverLevel2Name2());
                        dataBean2.setApprovalEmail(bean.getApproverLevel2Email2());
                        dataBean2.setApprovalPhone(bean.getApproverLevel2Phone2());
                        dataBean2.setApprovalDate(bean.getApprovalLevel2ApprDate2());
                        result.add(dataBean2);
                    }
                }else if (new BigDecimal(1).equals(bean.getNoLevel2ChgAcctApprover()) && !StringHandler.isBlankString(bean.getApproverLevel2Name1())) {
                    //approver 1
                    MrApproverDetailBean dataBean = new MrApproverDetailBean();
                    dataBean.setMrLine(bean.getPrNumber()+"-"+bean.getLineItem());
                    dataBean.setApprovalType(tmpChargeNumber.toString());
                    StringBuilder tmpStatus = new StringBuilder("Level 2 ");
                    tmpStatus.append(bean.getLevel2ApprovalStatus1());
                    if("Y".equals(bean.getApproveByPrice()) && bean.getApprovalLevel2Limit() != null) {
                        tmpStatus.append(" (").append(bean.getApprovalLevel2Limit()).append(" ").append(bean.getApprovalLimitCurrencyId()).append(")");
                    }
                    dataBean.setApprovalStatus(tmpStatus.toString());
                    dataBean.setApproverName(bean.getApproverLevel2Name1());
                    dataBean.setApprovalEmail(bean.getApproverLevel2Email1());
                    dataBean.setApprovalPhone(bean.getApproverLevel2Phone1());
                    dataBean.setApprovalDate(bean.getApprovalLevel2ApprDate1());
                    result.add(dataBean);
                }
                //level 3
                if (new BigDecimal(2).equals(bean.getNoLevel3ChgAcctApprover())) {
                    if (!StringHandler.isBlankString(bean.getApproverLevel3Name1()) &&
                        !StringHandler.isBlankString(bean.getApproverLevel3Name2()) ) {
                        //approver 1
                        MrApproverDetailBean dataBean = new MrApproverDetailBean();
                        dataBean.setMrLine(bean.getPrNumber()+"-"+bean.getLineItem());
                        dataBean.setApprovalType(tmpChargeNumber.toString());
                        StringBuilder tmpStatus = new StringBuilder("Level 3.1 ");
                        tmpStatus.append(bean.getLevel3ApprovalStatus1());
                        if("Y".equals(bean.getApproveByPrice()) && bean.getApprovalLevel3Limit() != null) {
                            tmpStatus.append(" (").append(bean.getApprovalLevel3Limit()).append(" ").append(bean.getApprovalLimitCurrencyId()).append(")");
                        }
                        dataBean.setApprovalStatus(tmpStatus.toString());
                        dataBean.setApproverName(bean.getApproverLevel3Name1());
                        dataBean.setApprovalEmail(bean.getApproverLevel3Email1());
                        dataBean.setApprovalPhone(bean.getApproverLevel3Phone1());
                        dataBean.setApprovalDate(bean.getApprovalLevel3ApprDate1());
                        result.add(dataBean);
                        //approver 2
                        MrApproverDetailBean dataBean2 = new MrApproverDetailBean();
                        dataBean2.setMrLine(bean.getPrNumber()+"-"+bean.getLineItem());
                        dataBean2.setApprovalType(tmpChargeNumber.toString());
                        tmpStatus = new StringBuilder("Level 3.2 ");
                        tmpStatus.append(bean.getLevel3ApprovalStatus2());
                        if("Y".equals(bean.getApproveByPrice()) && bean.getApprovalLevel3Limit() != null) {
                            tmpStatus.append(" (").append(bean.getApprovalLevel3Limit()).append(" ").append(bean.getApprovalLimitCurrencyId()).append(")");
                        }
                        dataBean2.setApprovalStatus(tmpStatus.toString());
                        dataBean2.setApproverName(bean.getApproverLevel3Name2());
                        dataBean2.setApprovalEmail(bean.getApproverLevel3Email2());
                        dataBean2.setApprovalPhone(bean.getApproverLevel3Phone2());
                        dataBean2.setApprovalDate(bean.getApprovalLevel3ApprDate2());
                        result.add(dataBean2);
                    }
                }else if (new BigDecimal(1).equals(bean.getNoLevel3ChgAcctApprover()) && !StringHandler.isBlankString(bean.getApproverLevel3Name1())) {
                    //approver 1
                    MrApproverDetailBean dataBean = new MrApproverDetailBean();
                    dataBean.setMrLine(bean.getPrNumber()+"-"+bean.getLineItem());
                    dataBean.setApprovalType(tmpChargeNumber.toString());
                    StringBuilder tmpStatus = new StringBuilder("Level 3 ");
                    tmpStatus.append(bean.getLevel3ApprovalStatus1());
                    if("Y".equals(bean.getApproveByPrice()) && bean.getApprovalLevel3Limit() != null) {
                        tmpStatus.append(" (").append(bean.getApprovalLevel3Limit()).append(" ").append(bean.getApprovalLimitCurrencyId()).append(")");
                    }
                    dataBean.setApprovalStatus(tmpStatus.toString());
                    dataBean.setApproverName(bean.getApproverLevel3Name1());
                    dataBean.setApprovalEmail(bean.getApproverLevel3Email1());
                    dataBean.setApprovalPhone(bean.getApproverLevel3Phone1());
                    dataBean.setApprovalDate(bean.getApprovalLevel3ApprDate1());
                    result.add(dataBean);
                }
                //level 4
                if (new BigDecimal(2).equals(bean.getNoLevel4ChgAcctApprover())) {
                    if (!StringHandler.isBlankString(bean.getApproverLevel4Name1()) &&
                        !StringHandler.isBlankString(bean.getApproverLevel4Name2()) ) {
                        //approver 1
                        MrApproverDetailBean dataBean = new MrApproverDetailBean();
                        dataBean.setMrLine(bean.getPrNumber()+"-"+bean.getLineItem());
                        dataBean.setApprovalType(tmpChargeNumber.toString());
                        StringBuilder tmpStatus = new StringBuilder("Level 4.1 ");
                        tmpStatus.append(bean.getLevel4ApprovalStatus1());
                        if("Y".equals(bean.getApproveByPrice()) && bean.getApprovalLevel4Limit() != null) {
                            tmpStatus.append(" (").append(bean.getApprovalLevel4Limit()).append(" ").append(bean.getApprovalLimitCurrencyId()).append(")");
                        }
                        dataBean.setApprovalStatus(tmpStatus.toString());
                        dataBean.setApproverName(bean.getApproverLevel4Name1());
                        dataBean.setApprovalEmail(bean.getApproverLevel4Email1());
                        dataBean.setApprovalPhone(bean.getApproverLevel4Phone1());
                        dataBean.setApprovalDate(bean.getApprovalLevel4ApprDate1());
                        result.add(dataBean);
                        //approver 2
                        MrApproverDetailBean dataBean2 = new MrApproverDetailBean();
                        dataBean2.setMrLine(bean.getPrNumber()+"-"+bean.getLineItem());
                        dataBean2.setApprovalType(tmpChargeNumber.toString());
                        tmpStatus = new StringBuilder("Level 4.2 ");
                        tmpStatus.append(bean.getLevel4ApprovalStatus2());
                        if("Y".equals(bean.getApproveByPrice()) && bean.getApprovalLevel4Limit() != null) {
                            tmpStatus.append(" (").append(bean.getApprovalLevel4Limit()).append(" ").append(bean.getApprovalLimitCurrencyId()).append(")");
                        }
                        dataBean2.setApprovalStatus(tmpStatus.toString());
                        dataBean2.setApproverName(bean.getApproverLevel4Name2());
                        dataBean2.setApprovalEmail(bean.getApproverLevel4Email2());
                        dataBean2.setApprovalPhone(bean.getApproverLevel4Phone2());
                        dataBean2.setApprovalDate(bean.getApprovalLevel4ApprDate2());
                        result.add(dataBean2);
                    }
                }else if (new BigDecimal(1).equals(bean.getNoLevel4ChgAcctApprover()) && !StringHandler.isBlankString(bean.getApproverLevel4Name1())) {
                    //approver 1
                    MrApproverDetailBean dataBean = new MrApproverDetailBean();
                    dataBean.setMrLine(bean.getPrNumber()+"-"+bean.getLineItem());
                    dataBean.setApprovalType(tmpChargeNumber.toString());
                    StringBuilder tmpStatus = new StringBuilder("Level 4 ");
                    tmpStatus.append(bean.getLevel4ApprovalStatus1());
                    if("Y".equals(bean.getApproveByPrice()) && bean.getApprovalLevel4Limit() != null) {
                        tmpStatus.append(" (").append(bean.getApprovalLevel4Limit()).append(" ").append(bean.getApprovalLimitCurrencyId()).append(")");
                    }
                    dataBean.setApprovalStatus(tmpStatus.toString());
                    dataBean.setApproverName(bean.getApproverLevel4Name1());
                    dataBean.setApprovalEmail(bean.getApproverLevel4Email1());
                    dataBean.setApprovalPhone(bean.getApproverLevel4Phone1());
                    dataBean.setApprovalDate(bean.getApprovalLevel4ApprDate1());
                    result.add(dataBean);
                }
            } //end of while loop
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    } //end of method

    public Object[] getMrApprovalDetailData(LineItemViewBean inputBean) throws BaseException {
		Object[] result = new Object[NUMBER_OF_ELEMENTS];
		result[FINANCE_DATA] = new Vector(0);
		result[RELEASER_DATA] = new Vector(0);
		result[USE_DATA] = new Vector(0);
		result[LIST_DATA] = new Vector(0);
        result[APPROVAL_DATA] = new Vector(0);
        
        try {
			if (prBean == null) {
				DbManager dbManager = new DbManager(getClient(),getLocale());
				GenericSqlFactory factory = new GenericSqlFactory(dbManager,new PurchaseRequestBean());
				String query = "select pr.*,prr.approver_required,prr.releaser_required,f.list_approval,fla.use_approval_limits_option,rli.scrap";
                //if looking up data for historical data then use pr_rules_view$
                if (this.getClient().equals(inputBean.getCompanyId()) || StringHandler.isBlankString(inputBean.getCompanyId()))
                    query += " from purchase_request pr, request_line_item rli, facility f,fac_loc_app fla,pr_rules prr";
                else if("Radian".equals(inputBean.getCompanyId()))
                    query += " from purchase_request pr, request_line_item rli, customer.facility f,fac_loc_app fla,pr_rules prr";
                else
                    query += " from purchase_request pr, request_line_item rli, facility$ f,fac_loc_app$ fla,pr_rules$ prr";

                query +=       " where pr.company_id = rli.company_id and pr.pr_number = rli.pr_number"+
                               " and pr.company_id = f.company_id and pr.facility_id = f.facility_id"+
                               " and pr.company_id = fla.company_id and pr.facility_id = fla.facility_id"+
                               " and rli.application = fla.application and pr.company_id = prr.company_id"+
                               " and pr.facility_id = prr.facility_id and pr.account_sys_id = prr.account_sys_id"+
                               " and rli.charge_type = prr.charge_type and rli.pr_number = "+inputBean.getPrNumber()+
                               " and rli.line_item = '"+inputBean.getLineItem()+"'";
                Collection prColl = factory.selectQuery(query);
				Iterator iter = prColl.iterator();
				while (iter.hasNext()) {
					prBean = (PurchaseRequestBean)iter.next();
					break;
				}
			}

			Object[] dataObj = null;
			if ("posubmit".equalsIgnoreCase(prBean.getPrStatus()) ||
                "rejected".equalsIgnoreCase(prBean.getPrStatus()) ||
				"cancel".equalsIgnoreCase(prBean.getPrStatus())) {
                if ("Y".equalsIgnoreCase(prBean.getApproverRequired()) && !"y".equalsIgnoreCase(prBean.getScrap())) {
                    dataObj = getFinanceApproverData(inputBean);
				    result[FINANCE_DATA] = dataObj[0];
                }
                if ("Y".equalsIgnoreCase(prBean.getReleaserRequired())) {
                    dataObj = getReleaserData(inputBean);
				    result[RELEASER_DATA] = dataObj[0];
                }
                if ("Y".equalsIgnoreCase(prBean.getUseApprovalLimitsOption())) {
                    dataObj = getApproverData(inputBean);
				    result[USE_DATA] = dataObj[0];
                }
                if ("Y".equalsIgnoreCase(prBean.getListApproval())) {
                    result[LIST_DATA] = getApproverListDetails(inputBean);
                }
                result[APPROVAL_DATA] = getApproverDetails(inputBean);
            }else if ("compchk2".equalsIgnoreCase(prBean.getPrStatus())) {
				dataObj = getApproverData(inputBean);
				result[USE_DATA] = dataObj[0];
                result[LIST_DATA] = getApproverListDetails(inputBean);
            }else if ("compchk4".equalsIgnoreCase(prBean.getPrStatus())) {
				result[APPROVAL_DATA] = getApproverDetails(inputBean);
            }else if ("compchk".equalsIgnoreCase(prBean.getPrStatus())) {
                if ("Y".equalsIgnoreCase(prBean.getApproverRequired()) && !"y".equalsIgnoreCase(prBean.getScrap())) {
                    dataObj = getFinanceApproverData(inputBean);
				    result[FINANCE_DATA] = dataObj[0];
                }
            }else if ("approved".equalsIgnoreCase(prBean.getPrStatus())) {
                if ("Y".equalsIgnoreCase(prBean.getApproverRequired()) && !"y".equalsIgnoreCase(prBean.getScrap())) {
                    dataObj = getFinanceApproverData(inputBean);
                    result[FINANCE_DATA] = dataObj[0];
                }
                if ("Y".equalsIgnoreCase(prBean.getReleaserRequired())) {
                    dataObj = getReleaserData(inputBean);
				    result[RELEASER_DATA] = dataObj[0];
                }
            }

		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Collection getCartData (LineItemViewBean inputBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory lineItemFac = new GenericSqlFactory(dbManager,new LineItemViewBean());
		SearchCriteria searchCriteria = new SearchCriteria();
		searchCriteria.addCriterion("prNumber", SearchCriterion.EQUALS, inputBean.getPrNumber().toString());
		return lineItemFac.select(searchCriteria, null, "line_item_ii_view");
	}

	public  Object[] getApproverData(	LineItemViewBean inputBean) throws BaseException {
		Collection c = null;
		boolean hasAdministrator = false;

		if(null!=inputBean.getPrNumber() &&  null!=inputBean.getLineItem()) {
			DbManager dbManager = new DbManager(getClient(),getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager,new RequestLineItemApproverBean());
			String query = "select fx_personnel_id_to_phone(a.use_approver) phone,fx_personnel_id_to_email(a.use_approver) email,fx_personnel_id_to_name(a.use_approver) full_name" +
			" from request_line_item a" +
			" where a.use_approver is not null and a.pr_number = " + inputBean.getPrNumber() + " and a.line_item = '" + inputBean.getLineItem() + "'" +
			" order by fx_personnel_id_to_name(a.use_approver)";
			c = factory.selectQuery(query);
			if( c == null || c.size() == 0) {
				query = "select fx_personnel_id_to_email(a.personnel_id) email," +
				"fx_personnel_id_to_name(a.personnel_id) full_name," +
				"fx_personnel_id_to_phone(a.personnel_id) phone" +
				" from over_limit_use_approver a,request_line_item r, purchase_request p"+
				" where p.pr_number = r.pr_number and a.facility_id = p.facility_id"+
				" and decode(a.application,'All',r.application,a.application) = r.application "+
				" and r.pr_number = "+inputBean.getPrNumber()+" and r.line_item = "+inputBean.getLineItem()+
				" order by fx_personnel_id_to_name(a.personnel_id)";
				c = factory.selectQuery(query);
			}

			if( c == null || c.size() == 0 ) {
				c = approverDataFetchCommonQuery(inputBean.getPrNumber());
				hasAdministrator = true;
			}
		}
		Object[] objs = {c,hasAdministrator};
		return objs;

	}

	public Object[] getFinanceApproverData(	LineItemViewBean inputBean) throws BaseException {
		Collection c = null;
		boolean hasAdministrator = false;
		if(null!=inputBean.getPrNumber()) {
			DbManager dbManager = new DbManager(getClient(),getLocale());
			/*
			GenericSqlFactory factory = new GenericSqlFactory(dbManager,new RequestLineItemApproverBean());
			StringBuilder queryBuff = new StringBuilder("select fx_personnel_id_to_name(a.personnel_id) full_name, " +
					" fx_personnel_id_to_phone(a.personnel_id) phone,fx_personnel_id_to_email(a.personnel_id) email from personnel a");
			queryBuff.append(" where a.personnel_id = fx_mr_finance_approver("+inputBean.getPrNumber()+")");
			c = factory.selectQuery(queryBuff.toString());
			 */
			GenericSqlFactory factory = new GenericSqlFactory(dbManager,new PurchaseRequestBean());
			String query = "";
			if (prBean == null) {
				query = "select * from purchase_request where pr_number = "+inputBean.getPrNumber();
				Collection prColl = factory.selectQuery(query);
				Iterator iter = prColl.iterator();
				while (iter.hasNext()) {
					prBean = (PurchaseRequestBean)iter.next();
					break;
				}
			}

			String approvalStatus = "Pending";
			factory.setBeanObject(new RequestLineItemApproverBean());
			if ("posubmit".equalsIgnoreCase(prBean.getPrStatus())) {
				approvalStatus = "Approved";
			}
			BigDecimal userId = prBean.getRequestor();
			if (prBean.getRequestedFinanceApprover() != null) {
				userId = prBean.getRequestedFinanceApprover();
			}
			query = "select '"+approvalStatus+"' approval_status,"+ DateHandler.getOracleToDateFunction(prBean.getFinanceApprovedDate())+" approval_date,"+
			"fx_personnel_id_to_phone("+userId+") phone,fx_personnel_id_to_email("+userId+") email,"+
			"fx_personnel_id_to_name("+userId+") full_name"+
			" from dual";
			c = factory.selectQuery(query);

			if( c == null || c.size() == 0 ) {
				c = approverDataFetchCommonQuery(inputBean.getPrNumber());
				hasAdministrator = true;
			}
		}
		Object[] objs = {c,hasAdministrator};
		return objs;
	}


	public Collection approverDataFetchCommonQuery(	BigDecimal  prNumber) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new RequestLineItemApproverBean());
		StringBuilder queryBuff = new StringBuilder("select fx_personnel_id_to_email(a.personnel_id) email , " ).append(
		" fx_personnel_id_to_name(a.personnel_id) full_name, fx_personnel_id_to_phone(a.personnel_id) phone " ).append(
		" from user_group_member a, request_line_item r where a.user_group_id = 'Administrator' " ).append(
		" and a.facility_id = r.facility_id  ");
		queryBuff.append(" and r.pr_number =").append(prNumber);
		queryBuff.append(" order by fx_personnel_id_to_name(a.personnel_id)");

		return factory.selectQuery(queryBuff.toString());
	}

	public int deleteFromDeliverySchedule(MaterialRequestInputBean inputBean) throws
	BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new RequestLineItemBean());

		String query = "delete from delivery_schedule  " +
		"where company_id = '"+inputBean.getCompanyId()+"' and pr_number = " + inputBean.getPrNumber().toString() + " and line_item = '" + inputBean.getLineItem() + "'";

		return factory.deleteInsertUpdate(query);
	}

	public Collection getApproverListDetails(	LineItemViewBean inputBean) throws BaseException {
        DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new RliFacListRelDetailViewBean());
		String query = "select * from rli_fac_list_rel_detail_view"+
		" where pr_number = "+inputBean.getPrNumber()+" and line_item = '"+inputBean.getLineItem()+"'"+
		" order by list_name";
		return factory.selectQuery(query);
    }


	public Object[] getReleaserData(LineItemViewBean inputBean) throws BaseException {
		Collection c = null;
		boolean hasAdministrator = false;
		if(null!=inputBean.getPrNumber()) {
			DbManager dbManager = new DbManager(getClient(),getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager,new PurchaseRequestBean());
			String query = "";
			if (prBean == null) {
				query = "select * from purchase_request where pr_number = "+inputBean.getPrNumber();
				Collection prColl = factory.selectQuery(query);
				Iterator iter = prColl.iterator();
				while (iter.hasNext()) {
					prBean = (PurchaseRequestBean)iter.next();
					break;
				}
			}

			factory.setBeanObject(new RequestLineItemApproverBean());
			if ("posubmit".equalsIgnoreCase(prBean.getPrStatus())) {
				BigDecimal userId = prBean.getRequestor();
				if (prBean.getRequestedReleaser() != null) {
					userId = prBean.getRequestedReleaser();
				}
				query = "select 'Approved' approval_status,"+ DateHandler.getOracleToDateFunction(prBean.getMrReleasedDate())+" approval_date,"+
				"fx_personnel_id_to_phone("+userId+") phone,fx_personnel_id_to_email("+userId+") email,"+
				"fx_personnel_id_to_name("+userId+") full_name"+
				" from dual";
				c = factory.selectQuery(query);
			}else {
				query = "select 'Pending' approval_status,null approval_date,fx_personnel_id_to_email(a.personnel_id) email," +
				"fx_personnel_id_to_name(a.personnel_id) full_name, fx_personnel_id_to_phone(a.personnel_id) phone"+
				" from user_group_member a, purchase_request r  where a.user_group_id = 'Releaser'  and a.facility_id = r.facility_id"+
				" and r.pr_number = "+inputBean.getPrNumber()+
				" order by fx_personnel_id_to_name(a.personnel_id)";
				c = factory.selectQuery(query);
			}
			/*
			if( (c.isEmpty()) &&  (c.size()==0)) {
			 c = approverDataFetchCommonQuery(inputBean.getPrNumber());
			 hasAdministrator = true;
			}
			 */
		}
		Object[] objs = {c,hasAdministrator};
		return objs;
	}

} //end of class