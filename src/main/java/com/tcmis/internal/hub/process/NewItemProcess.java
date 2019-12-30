package com.tcmis.internal.hub.process;

import com.tcmis.client.catalog.process.EngEvalProcess;
import com.tcmis.client.common.beans.ChemicalApproverBean;
import com.tcmis.client.common.beans.PartBean;
import com.tcmis.client.common.factory.ChemicalApproverBeanFactory;
import com.tcmis.client.common.factory.PartBeanFactory;
import com.tcmis.client.common.factory.SizeUnitConversionBeanFactory;
import com.tcmis.client.common.factory.SizeUnitViewBeanFactory;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.NewChemHeaderViewBean;
import com.tcmis.internal.hub.beans.NewItemInputBean;
import com.tcmis.internal.hub.factory.NewChemHeaderViewBeanFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import java.sql.Connection;

/******************************************************************************
 * Process for New Item
 * @version 1.0
 *****************************************************************************/

public class NewItemProcess extends BaseProcess {
    Log log = LogFactory.getLog(this.getClass());
    private GenericSqlFactory genericSqlFactory;
    private DbManager dbManager;
    private Connection connection = null;

    public NewItemProcess(String client) {
        super(client);
        dbManager = new DbManager(getClient());
        genericSqlFactory = new GenericSqlFactory(dbManager);
    }

    public Collection copyDetailInfo(Collection beans) throws Exception {
        Collection coll = new Vector(beans.size());
        Iterator iter = beans.iterator();
        int partCount = 1;
        while (iter.hasNext()) {
            NewItemInputBean bean = (NewItemInputBean) iter.next();
            if ("new".equalsIgnoreCase(bean.getNewComponent())) {
                PartBean partBean = new PartBean();
                partBean.setPartId(new BigDecimal(partCount++));
                if (!StringHandler.isBlankString(bean.getComponent())) {
                    partBean.setComponentsPerItem(new BigDecimal(bean.getComponent()));
                }
                partBean.setPartSize(new BigDecimal(bean.getSize()));
                partBean.setSizeUnit(bean.getUnit());
                partBean.setPkgStyle(bean.getPkgStyle());
                if (!StringHandler.isBlankString(bean.getNetWtVol())) {
                    partBean.setNetWt(new BigDecimal(bean.getNetWtVol()));
                }
                partBean.setNetWtUnit(bean.getNetWtVolUnit());
                partBean.setDimension(bean.getDimension());
                partBean.setMaterialDesc(bean.getMaterialDesc());
                partBean.setGrade(bean.getGrade());
                partBean.setManufacturer(bean.getManufacturer());
                partBean.setMfgPartNo(bean.getMfgPartNo());
                coll.add(partBean);
            }
        }
        return coll;
    }

    public NewChemHeaderViewBean copyHeaderInfo(NewItemInputBean bean) throws Exception {
        NewChemHeaderViewBean newChemHeaderViewBean = new NewChemHeaderViewBean();
        newChemHeaderViewBean.setCatalogId(bean.getCatalogId());
        newChemHeaderViewBean.setCatPartNo(bean.getCatPartNo());
        newChemHeaderViewBean.setCatalogCompanyId(bean.getCatalogCompanyId());
        newChemHeaderViewBean.setCatalogDesc(bean.getCatalogDesc());
        newChemHeaderViewBean.setPartDescription(bean.getPartDesc());
        newChemHeaderViewBean.setCompanyId(bean.getCompanyId());
        newChemHeaderViewBean.setFacilityId(bean.getFacilityId());
        newChemHeaderViewBean.setNewChemPackagingChange(bean.getNewChemPackagingChange());
        return newChemHeaderViewBean;
    }

    //returns:
    // OK - if everything process as expected
    // ERROR - if unexpected error occurred
    // a message to show to user
    public String createNewChem(NewItemInputBean headerInfo, Collection beans) {
        String result = "OK";
        try {
            connection = dbManager.getConnection();
            //create new chem request
            BigDecimal requestId = createNewChemHeader(headerInfo);
            Iterator iter = beans.iterator();
            int partCount = 1;
            while (iter.hasNext()) {
                NewItemInputBean bean = (NewItemInputBean) iter.next();
                if ("new".equalsIgnoreCase(bean.getNewComponent())) {
                    createNewChemDetail(requestId, new BigDecimal(partCount++), headerInfo, bean);
                }
            }
            //update receipts with new chem request
            String message = "";
            String receiptIds = headerInfo.getReceiptId();
            if (!StringHandler.isBlankString(receiptIds)) {
                if (receiptIds.contains(",")) {
                    String[] receipt = receiptIds.split(",");
                    for (int i = 0; i < receipt.length; i++) {
                        message += updateNewChemReceipt(requestId, new BigDecimal(receipt[i]));
                    }
                } else {
                    message += updateNewChemReceipt(requestId, new BigDecimal(receiptIds));
                }
            } else {
                result = "ERROR";
            }
            if (!StringHandler.isBlankString(message)) {
                result = message;
            }

            //advance Catalog Add Request
            advanceCatalogAddRequest(requestId);
            //send email notification to approvers
            sendEmailToApprover(requestId, headerInfo);
        } catch (Exception e) {
            result = "ERROR";
        }finally {
            try {
                dbManager.returnConnection(connection);
            }catch (Exception ee) {log.error("Error while returning connection back to pool",ee);}
        }
        return result;
    }

    public void advanceCatalogAddRequest(BigDecimal requestId) throws Exception {
        try {
            EngEvalProcess engEvalProcess = new EngEvalProcess(this.getClient(), this.getLocale(), "");
            engEvalProcess.setFactoryConnection(genericSqlFactory, connection);
            engEvalProcess.setNextStatusAction("Submit");
            engEvalProcess.createCatalogAddItemQc(requestId);
            engEvalProcess.populateCatalogAddItemLocale(requestId);
            engEvalProcess.setNextStatus(requestId);
        } catch (Exception e) {
            log.error("Error while trying advance USGOV catalog add request", e);
        }
    } // end of method

    public void sendEmailToApprover(BigDecimal requestId, NewItemInputBean headerInfo) {
        try {
            ChemicalApproverBeanFactory factory = new ChemicalApproverBeanFactory(dbManager);
            Collection approverEmails = factory.selectApproverEmailForRequest(requestId, "Material QC",connection);
            Iterator iter = approverEmails.iterator();
            String[] to = new String[approverEmails.size()];
            int count = 0;
            while (iter.hasNext()) {
                ChemicalApproverBean bean = (ChemicalApproverBean) iter.next();
                to[count++] = bean.getEmail();
            }
            //send email
            if (to.length > 0) {
                String msg = "tcmIS New Chemical Request " + requestId.toString() + " is waiting for your approval(" + headerInfo.getFacilityId() + ")";
                String[] cc = {"deverror@tcmis.com"};
                MailProcess.sendEmail(to, cc, msg, msg, false);
            } else {
                MailProcess.sendEmail("deverror@tcmis.com", null, "deverror@tcmis.com", "ML New Chem Request error occurred while trying to send email to approver:" + requestId.toString(), "No approver");
            }
        } catch (Exception e) {
            log.error("ML New Chem Request", e);
            MailProcess.sendEmail("deverror@tcmis.com", null, "deverror@tcmis.com", "ML New Chem Request error occurred while trying to send email to approver:" + requestId.toString(), "See log file.");
        }
    }

    public String updateNewChemReceipt(BigDecimal requestId, BigDecimal receiptId) throws Exception {
        String result = "";

        Vector inArgs = new Vector(2);
        inArgs.add(receiptId);
        inArgs.add(requestId);

        Vector outArgs = new Vector(1);
        outArgs.add(new Integer(java.sql.Types.VARCHAR));

        log.info("p_new_chem_receipt_update " + inArgs);
        Collection coll = genericSqlFactory.doProcedure(connection,"p_new_chem_receipt_update", inArgs, outArgs);
        if (coll.size() == 1) {
            if (!"OK".equals(((Vector) coll).get(0).toString())) {
                result = ((Vector) coll).get(0).toString();
            }
        }

        log.info("p_new_chem_receipt result" + result);
        if (result.trim().length() > 0) {
            result += "<br>";
        }
        return result;
    }

    public void createNewChemDetail(BigDecimal requestId, BigDecimal partId, NewItemInputBean headerInfo, NewItemInputBean detailInfo) throws Exception {

        Vector inArgs = new Vector(14);
        inArgs.add(requestId);
        inArgs.add(headerInfo.getCompanyId());
        inArgs.add(partId);
        inArgs.add(detailInfo.getMaterialDesc());
        inArgs.add(detailInfo.getManufacturer());
        inArgs.add(detailInfo.getSize());
        inArgs.add(detailInfo.getUnit());
        inArgs.add(detailInfo.getPkgStyle());
        inArgs.add(detailInfo.getComponent());
        if ("Yes".equals(detailInfo.getNetWtRequired())) {
            inArgs.add(detailInfo.getNetWtVol());
            inArgs.add(detailInfo.getNetWtVolUnit());
            inArgs.add(detailInfo.getDimension());
        } else {
            inArgs.add("");
            inArgs.add("");
            inArgs.add("");
        }

        inArgs.add(detailInfo.getGrade());
        inArgs.add(detailInfo.getMfgPartNo());

        log.info("p_new_chem_detail " + inArgs);
        genericSqlFactory.doProcedure(connection,"p_new_chem_detail", inArgs);
    }

    public BigDecimal createNewChemHeader(NewItemInputBean headerInfo) throws Exception {
        BigDecimal result = new BigDecimal(0);

        Vector inArgs = new Vector(7);
        inArgs.add(headerInfo.getRequestorId());
        inArgs.add(headerInfo.getCatalogCompanyId());
        inArgs.add(headerInfo.getCatalogId());
        inArgs.add(headerInfo.getCatPartNo());
        inArgs.add(headerInfo.getCompanyId());
        inArgs.add(headerInfo.getFacilityId());
        inArgs.add(headerInfo.getPartDesc());

        Vector outArgs = new Vector(1);
        outArgs.add(new Integer(java.sql.Types.VARCHAR));

        Vector optArgs = new Vector(1);
        optArgs.add(headerInfo.getInventoryGroup());

        log.info("p_new_chem_header " + inArgs);
        Collection coll = genericSqlFactory.doProcedure(connection,"p_new_chem_header", inArgs, outArgs, optArgs);
        if (coll.size() == 1) {
            result = new BigDecimal(((Vector) coll).get(0).toString());
        }
        return result;
    }

    public NewChemHeaderViewBean getHeaderInfo(NewItemInputBean bean) throws BaseException {
        NewChemHeaderViewBeanFactory factory = new NewChemHeaderViewBeanFactory(dbManager);
        SearchCriteria criteria = new SearchCriteria();
        criteria.addCriterion("catalogCompanyId", SearchCriterion.EQUALS, bean.getCatalogCompanyId());
        criteria.addCriterion("catalogId", SearchCriterion.EQUALS, bean.getCatalogId());
        criteria.addCriterion("catPartNo", SearchCriterion.EQUALS, bean.getCatPartNo());
        Collection headerInfo = factory.select(criteria, null);
        //
        NewChemHeaderViewBean newChemHeaderViewBean = new NewChemHeaderViewBean();
        if (headerInfo.size() > 0) {
            Iterator iter = headerInfo.iterator();
            while (iter.hasNext()) {
                newChemHeaderViewBean = (NewChemHeaderViewBean) iter.next();
                break;
            }
        }
        return newChemHeaderViewBean;
    }

    public Collection getDetailInfo(NewChemHeaderViewBean newChemHeaderViewBean) throws BaseException {
        Collection detailInfo = new Vector(0);
        if (newChemHeaderViewBean.getListItemId() != null) {
            if (!StringHandler.isBlankString(newChemHeaderViewBean.getListItemId().toString())) {
                PartBeanFactory factory = new PartBeanFactory(dbManager);
                SearchCriteria criteria = new SearchCriteria();
                criteria.addCriterion("itemId", SearchCriterion.EQUALS, newChemHeaderViewBean.getListItemId().toString());
                SortCriteria sortCriteria = new SortCriteria();
                sortCriteria.addCriterion("partId");
                detailInfo = factory.selectMlItem(criteria, sortCriteria);
            }
        }
        return detailInfo;
    }

    public Collection getSizeUnitNetWtRequired() throws BaseException, Exception {
        SizeUnitViewBeanFactory factory = new SizeUnitViewBeanFactory(dbManager);
        SearchCriteria criteria = new SearchCriteria();
        criteria.addCriterion("netWtRequired", SearchCriterion.EQUALS, "Y");
        SortCriteria sortCriteria = new SortCriteria();
        sortCriteria.addCriterion("sizeUnit");
        return factory.select(criteria, sortCriteria);
    }

    public Collection getFromUnit() throws BaseException, Exception {
        SizeUnitConversionBeanFactory factory = new SizeUnitConversionBeanFactory(dbManager);
        return factory.selectFromUnit();
    }

    public Collection getSeagateFromUnit() throws BaseException, Exception {
        SizeUnitConversionBeanFactory factory = new SizeUnitConversionBeanFactory(dbManager);
        return factory.selectSeagateFromUnit();
    }
}
