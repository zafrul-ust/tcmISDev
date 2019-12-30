package com.tcmis.client.catalog.process;

import java.util.Collection;
import java.util.Vector;
import java.util.Date;
import java.util.Iterator;
import java.sql.Connection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.catalog.factory.ApprovalRequestOvFactory;
import com.tcmis.client.catalog.beans.CatalogAddApprovalBean;
import com.tcmis.client.catalog.beans.ApprovalReviewMessageViewBean;
import com.tcmis.client.catalog.beans.ApprovalRequestOvBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.StringHandler;

/******************************************************************************
 * Process for ChemicalApprovalReviewProcess
 * @version 1.0
 *****************************************************************************/
public class ChemicalApprovalReviewProcess extends BaseProcess {
    Log log = LogFactory.getLog(this.getClass());

    public ChemicalApprovalReviewProcess(String client, String locale) {
        super(client, locale);
    }

    public Object[] getReviewResult(String companyId, String requestId, String showPassAndFailReviewRules) throws BaseException {
        Object objs[] = new Object[2];
        DbManager dbManager = new DbManager(getClient());
        Connection connection = dbManager.getConnection();
        try {
            ApprovalRequestOvFactory factory = new ApprovalRequestOvFactory(dbManager);
            StringBuilder query = new StringBuilder("");
            //if carn.request_status = 7 or 9 or 12 then show freezed results
            GenericSqlFactory genericSqlFactory = new GenericSqlFactory(dbManager);
            //first delete previous messages
            genericSqlFactory.deleteInsertUpdate("delete from gt_approval_review_message",connection);
            //now do rest of logic
            Collection resultColl = null;
            Date archivedDate = null;
            String tmpVal = genericSqlFactory.selectSingleValue("select count(*) from catalog_add_request_new where request_status in (7,9,12) and request_id = "+requestId,connection);
            if (!"0".equalsIgnoreCase(tmpVal)) {
                genericSqlFactory.setBean(new CatalogAddApprovalBean());
                query = new StringBuilder("select max(approval_date) approvalDate from catalog_add_approval where request_id = ").append(requestId);
                Collection tmpColl = genericSqlFactory.selectQuery(query.toString());
                Iterator iter = tmpColl.iterator();
                while (iter.hasNext()) {
                    CatalogAddApprovalBean bean = (CatalogAddApprovalBean)iter.next();
                    archivedDate = bean.getApprovalDate();
                    break;
                }
                resultColl = getArchivedReviewResult(connection,companyId,requestId,showPassAndFailReviewRules,factory);
                //if there no data in archive then get current result review
                //the reason for this is that requests that were previously approved did not have archived data
                if (resultColl.size() == 0) {
                    archivedDate = null;
                    resultColl = getCurrentReviewResult(connection,companyId,requestId,showPassAndFailReviewRules,factory);
                }

            }else {
                archivedDate = null;
                resultColl = getCurrentReviewResult(connection,companyId,requestId,showPassAndFailReviewRules,factory);
            }

            //add additional message to approval roles if existed
            Iterator iter = resultColl.iterator();
            String currentApprovalRole = "";
            while(iter.hasNext()) {
                ApprovalRequestOvBean approvalRequestOvBean = (ApprovalRequestOvBean)iter.next();
                if (!currentApprovalRole.equals(approvalRequestOvBean.getApprovalRole())) {
                    //if request still pending approval then query from stage table
                    if (archivedDate == null) {
                        query = new StringBuilder("select car.approval_role,a.* from gt_approval_review_message a, chemical_approval_review car where a.review_id = car.review_id");
                        query.append(" and car.approval_role = '").append(approvalRequestOvBean.getApprovalRole()).append("'");
                    }else { //otherwise query from archive table
                        query = new StringBuilder("select * from catalog_add_chem_apprv_msg where request_id = ").append(requestId);
                        query.append(" and approval_role = '").append(approvalRequestOvBean.getApprovalRole()).append("'");
                    }
                    query.append(" order by processing_order,display_order");
                    genericSqlFactory.setBeanObject(new ApprovalReviewMessageViewBean());
                    Collection<ApprovalReviewMessageViewBean> msgTxtLines = genericSqlFactory.selectQuery(query.toString(),connection);
                    for(ApprovalReviewMessageViewBean bean:msgTxtLines) {
                        if (!StringHandler.isBlankString(bean.getMessageText()))
                            bean.setMessageText(bean.getMessageText().replaceAll("\n\n", "<br/>"));
                    }
                    approvalRequestOvBean.setMessageTextColl(msgTxtLines);
                }
                currentApprovalRole = approvalRequestOvBean.getApprovalRole();
            }

            objs[0] = archivedDate;
            objs[1] = resultColl;
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
		}
        return objs;
    } //end of method

    private Collection getArchivedReviewResult(Connection connection, String companyId, String requestId, String showPassAndFailReviewRules, ApprovalRequestOvFactory factory) {
        Collection result = new Vector(0);
        try {
            StringBuilder query = new StringBuilder("select * from table (PKG_APPROVAL_REVIEW.fx_tbl_request_review_archive('");
            query.append(companyId).append("',").append(requestId).append("))");
            if ("N".equals(showPassAndFailReviewRules)) {
                query.append(" where fail = 'Y'");
            }
            query.append(" order by line_item,approval_role,processing_order");
            result = factory.selectObject(query.toString(),connection,showPassAndFailReviewRules);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private Collection getCurrentReviewResult(Connection connection, String companyId, String requestId, String showPassAndFailReviewRules, ApprovalRequestOvFactory factory) {
        Collection result = new Vector(0);
        try {
            StringBuilder query = new StringBuilder("select * from table (PKG_APPROVAL_REVIEW.fx_tbl_request_review('");
            query.append(companyId).append("',").append(requestId).append(",'','Y','','','','Y'))");
            if ("N".equals(showPassAndFailReviewRules)) {
                query.append(" where fail = 'Y' and stop_on_failure = 'Y' and reject_on_failure = 'N'");
            }
            query.append(" order by line_item,approval_role,processing_order");
            result = factory.selectObject(query.toString(),connection,showPassAndFailReviewRules);
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }  //end of method

} //end of class