package com.tcmis.client.common.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.util.StringHandler;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.client.catalog.beans.CatalogInputBean;
import com.tcmis.client.catalog.beans.CatAddHeaderViewBean;
import com.tcmis.client.catalog.process.EngEvalProcess;
import com.tcmis.client.catalog.process.CatalogAddRequestProcess;
import com.tcmis.client.order.beans.CustomerPoStageBean;
import com.tcmis.client.common.beans.CatalogAddTransactionBean;

import java.sql.Connection;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Vector;
import java.util.Collection;

/******************************************************************************
 * Process for CreateCatalogAddProcess
 * @version 1.0
 *****************************************************************************/
public class CreateCatalogAddProcess extends BaseProcess implements Runnable{

    Log log = LogFactory.getLog(this.getClass());
    private DbManager dbManager;
    private Connection connection = null;
    private GenericSqlFactory genericSqlFactory;
    private String URL = "";
    private String loadId = "";

    public CreateCatalogAddProcess(String client,String locale, String url)  {
        super(client,locale);
        this.URL = url;
    }

    public void setFactoryConnection(GenericSqlFactory genericSqlFactory, Connection connection) {
        this.connection = connection;
        this.genericSqlFactory = genericSqlFactory;
    }

    public void run() {
        try {
            //Thread.currentThread().sleep(10000);    //10 seconds
            CreateCatalogAddProcess process = new CreateCatalogAddProcess(client,locale,URL);
            process.createCatalogAdd(loadId);
        } catch(Exception e){
            StringBuilder esub = new StringBuilder("Error while creating new thread for new catalog add: ");
            esub.append(loadId);
            String emsg = e.getMessage();
            MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",esub.toString(),emsg);
        }
    }

    public void processData(String loadId) throws BaseException {
        try {
            this.loadId = loadId;
            Thread thread = new Thread(this);
            thread.start();
        }catch (Exception ee) {
            StringBuilder esub = new StringBuilder("Error while processing data for new catalog add: ");
            esub.append(loadId);
            String emsg = ee.getMessage();
            MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",esub.toString(),emsg);
        }
    }

    public void createCatalogAdd(String loadId) throws BaseException {
        try {
            dbManager = new DbManager(getClient(),this.getLocale());
            connection = dbManager.getConnection();
            genericSqlFactory = new GenericSqlFactory(dbManager);
            if ("-1".equals(loadId)) {
                //called from transaction process
                createCatalogAddFromTransaction();
            }else {
                //called from EDI process
                createCatalogAddFromEdi(loadId);
            }
        }catch (Exception ee) {
            StringBuilder esub = new StringBuilder("Error while processing data for new catalog add: ");
            esub.append(loadId);
            String emsg = ee.getMessage();
            MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com",esub.toString(),emsg);
        }finally {
            dbManager.returnConnection(connection);
            dbManager = null;
            connection = null;
            genericSqlFactory = null;
        }
    }

    private void createCatalogAddFromTransaction() throws Exception {
        try {
            //get data that need new catalog adds
            StringBuilder query = new StringBuilder("select * from transaction_cat_add_view tcav where not exists (select request_id from catalog_add_request_new carn");
            query.append(" where tcav.company_id = carn.company_id and tcav.cat_add_request_id = carn.request_id)");
            genericSqlFactory.setBeanObject(new CatalogAddTransactionBean());
            Iterator iter = genericSqlFactory.selectQuery(query.toString(),connection).iterator();
            while (iter.hasNext()) {
                CatalogAddTransactionBean bean = (CatalogAddTransactionBean)iter.next();
                CatalogInputBean catalogInputBean = new CatalogInputBean();
                BeanHandler.copyAttributes(bean,catalogInputBean);
                if (bean.getCatAddRequestId() == null) continue;

                catalogInputBean.setRequestId(bean.getCatAddRequestId());
                catalogInputBean.setApprovalPath(bean.getApprovalPath());
                //figuring out cat_part_no
                String tmpCatPartNo = "";
                if (!StringHandler.isBlankString(bean.getActivityPartNumber()))
                    tmpCatPartNo = bean.getActivityPartNumber();
                else if (!StringHandler.isBlankString(bean.getActivityPartNumber2()))
                    tmpCatPartNo = bean.getActivityPartNumber2();
                else if (!StringHandler.isBlankString(bean.getActivityPartNumber3()))
                    tmpCatPartNo = bean.getActivityPartNumber3();
                //currently our part number is only 30 characters
                if (tmpCatPartNo.length() > 30)
                    tmpCatPartNo = tmpCatPartNo.substring(0,29);
                catalogInputBean.setCatalogAddCatPartNo(tmpCatPartNo);

                if(StringHandler.isBlankString(catalogInputBean.getCatalogAddPartDesc()))
                {
                    StringBuilder tmpMaterialDesc = new StringBuilder("");
                    if (!StringHandler.isBlankString(bean.getActivityPartDescription()))
                        tmpMaterialDesc.append(bean.getActivityPartDescription());
                    if (!StringHandler.isBlankString(bean.getActivityPartDescription2()))
                        tmpMaterialDesc.append(" ").append(bean.getActivityPartDescription2());
                    if (!StringHandler.isBlankString(bean.getActivityPartDescription3()))
                        tmpMaterialDesc.append(" ").append(bean.getActivityPartDescription3());

                    catalogInputBean.setCatalogAddPartDesc(tmpMaterialDesc.toString());
                }

                createNewCatalogAddRequestNew(catalogInputBean,bean.getCatAddRequestId(),"new");
                createCatalogAddItemTransaction(catalogInputBean,bean);

                //call procedure to set status
                setTransactionStatus(bean.getCatAddRequestId(),"Pending Catalog Add");

                //set next status
                CatalogAddRequestProcess catalogAddRequestProcess = new CatalogAddRequestProcess(this.getClient(),this.getLocale(),URL);
                catalogAddRequestProcess.setFactoryConnection(genericSqlFactory,connection);
                EngEvalProcess engEvalProcess = new EngEvalProcess(this.getClient(),this.getLocale(),URL);
                engEvalProcess.setFactoryConnection(genericSqlFactory,connection);
                //populate catalog_add_item_locale for request
                engEvalProcess.populateCatalogAddItemLocale(catalogInputBean.getRequestId());
                //update catalog_add_item_qc and catalog_add_item_orig
                engEvalProcess.updateCatalogAddItemQcNOrig(catalogInputBean.getRequestId());
                //update catalog_add_spec_qc
                engEvalProcess.updateCatalogAddSpecQc(catalogInputBean.getRequestId());
                engEvalProcess.setNextStatusAction("Submit");
                CatAddHeaderViewBean headerViewBean = new CatAddHeaderViewBean();
                BeanHandler.copyAttributes(catalogInputBean,headerViewBean);
                catalogAddRequestProcess.setNextStatus(headerViewBean,engEvalProcess,-1);
                //update catalog_add_item_qc.comments
                updateCatalogAddItemQc(catalogInputBean,bean);
            }
        }catch(Exception e) {
            throw e;
        }
    } //end of method

    private void setProcessTransaction(BigDecimal requestId) throws Exception {
        try {
            Vector cin = new Vector(1);
            cin.addElement(requestId.toString());
            Collection cout = new Vector();
            cout.add(new Integer(java.sql.Types.VARCHAR));
            Collection resultData = genericSqlFactory.doProcedure("pkg_transaction_upload.p_process_approved_cat_add", cin, cout);
            Iterator i11 = resultData.iterator();
            String val = "";
            while (i11.hasNext()) {
                val = (String) i11.next();
            }
            if (!"OK".equalsIgnoreCase(val)) {
                if (!StringHandler.isBlankString(val)) {
                    if (!val.startsWith("No parts with status"))
                        MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Continue to process transaction data failed","p_process_approved_cat_add - Request - "+requestId.toString()+"\n"+val);
                }
            }
        }catch(Exception e) {
            throw e;
        }
    } //end of method

    private void setTransactionStatus(BigDecimal requestId, String status) throws Exception {
        try {
            Vector cin = new Vector(2);
            cin.addElement(status);
            cin.addElement(requestId.toString());
            Collection cout = new Vector();
            cout.add(new Integer(java.sql.Types.VARCHAR));
            Collection resultData = genericSqlFactory.doProcedure("pkg_transaction_upload.p_set_cat_add_status", cin, cout);
            Iterator i11 = resultData.iterator();
            String val = "";
            while (i11.hasNext()) {
                val = (String) i11.next();
            }
            if (!"OK".equalsIgnoreCase(val) && !"No records were updated".equalsIgnoreCase(val)) {
                MailProcess.sendEmail("deverror@tcmis.com", null,"deverror@tcmis.com","Updating catalog add request status for transaction data failed","p_set_cat_add_status - Request - "+requestId.toString()+"\n"+val);
            }
        }catch(Exception e) {
            throw e;
        }
    } //end of method

    private void createCatalogAddFromEdi(String loadId) throws Exception {
        try {
            //get data that need new catalog adds
            StringBuilder query = new StringBuilder("select cps.*,sc.supplier_name from customer_po_stage cps, supplier_catalog sc where cps.load_id = ").append(loadId);
            query.append(" and cps.status = 'ERROR' and cps.status_detail = 'New Catalog Add'");
            query.append(" and cps.company_id = sc.company_id(+) and cps.supplier_code = sc.supplier_code(+)");
            genericSqlFactory.setBeanObject(new CustomerPoStageBean());
            Iterator iter = genericSqlFactory.selectQuery(query.toString(),connection).iterator();
            while (iter.hasNext()) {
                CustomerPoStageBean customerPoStageBean = (CustomerPoStageBean)iter.next();
                CatalogInputBean catalogInputBean = new CatalogInputBean();
                BeanHandler.copyAttributes(customerPoStageBean,catalogInputBean);
                query = new StringBuilder("select request_seq.nextval from dual");
                BigDecimal requestId = new BigDecimal(genericSqlFactory.selectSingleValue(query.toString(),connection));
                catalogInputBean.setRequestId(requestId);
                //cat_part_no
                String tmpCatPartNo = customerPoStageBean.getCatPartNo();
                //edi_vn
                if (StringHandler.isBlankString(tmpCatPartNo) && !StringHandler.isBlankString(customerPoStageBean.getEdiVn()))
                    tmpCatPartNo = customerPoStageBean.getEdiVn();
                if (StringHandler.isBlankString(tmpCatPartNo) && !StringHandler.isBlankString(customerPoStageBean.getEdiF7()))
                    tmpCatPartNo = customerPoStageBean.getEdiF7();
                if (StringHandler.isBlankString(tmpCatPartNo) && !StringHandler.isBlankString(customerPoStageBean.getManufacturerPartNum()))
                    tmpCatPartNo = customerPoStageBean.getManufacturerPartNum();
                catalogInputBean.setCatalogAddCatPartNo(tmpCatPartNo);
                //part_description
                //currently our part description is only 500 characters
                String tmpPartDesc = StringHandler.emptyIfNull(customerPoStageBean.getPartShortDesc());
                if (tmpPartDesc.length() > 500)
                    tmpPartDesc = tmpPartDesc.substring(0,499);
                catalogInputBean.setCatalogAddPartDesc(tmpPartDesc);
                catalogInputBean.setFacilityId(customerPoStageBean.getShiptoPartyId());
                createNewCatalogAddRequestNew(catalogInputBean,requestId,"new");
                createCatalogAddItemEdi(catalogInputBean,customerPoStageBean);

                //customer.customer_po_stage
                //status_detail [New Catalog Add, Pending Catalog Add, Approved Catalog add, Rejected Catalog Add]
                //catalog_add_request_id (fk catalog_add_request_new)
                updateCustomerPoStageNewCatalogAdd(catalogInputBean,customerPoStageBean);

                //set next status
                CatalogAddRequestProcess catalogAddRequestProcess = new CatalogAddRequestProcess(this.getClient(),this.getLocale(),URL);
                catalogAddRequestProcess.setFactoryConnection(genericSqlFactory,connection);
                EngEvalProcess engEvalProcess = new EngEvalProcess(this.getClient(),this.getLocale(),URL);
                engEvalProcess.setFactoryConnection(genericSqlFactory,connection);
                //populate catalog_add_item_locale for request
                engEvalProcess.populateCatalogAddItemLocale(catalogInputBean.getRequestId());
                //update catalog_add_item_qc and catalog_add_item_orig
                engEvalProcess.updateCatalogAddItemQcNOrig(catalogInputBean.getRequestId());
                //update catalog_add_spec_qc
                engEvalProcess.updateCatalogAddSpecQc(catalogInputBean.getRequestId());
                engEvalProcess.setNextStatusAction("Submit");
                CatAddHeaderViewBean headerViewBean = new CatAddHeaderViewBean();
                BeanHandler.copyAttributes(catalogInputBean,headerViewBean);
                catalogAddRequestProcess.setNextStatus(headerViewBean,engEvalProcess,-1);
            }
        }catch(Exception e) {
            throw e;
        }
    } //end of method

    private void updateCustomerPoStageNewCatalogAdd(CatalogInputBean inputBean, CustomerPoStageBean customerPoStageBean) {
        try {
            StringBuilder query = new StringBuilder("update customer_po_stage set catalog_add_request_id = ");
            query.append(inputBean.getRequestId()).append(",status_detail = 'Pending Catalog Add' where load_id = ").append(customerPoStageBean.getLoadId());
            query.append(" and load_line = ").append(customerPoStageBean.getLoadLine());
            query.append(" and company_id = '").append(inputBean.getCompanyId()).append("' and customer_po_no = '").append(customerPoStageBean.getCustomerPoNo()).append("'");
            query.append(" and customer_po_line_no = '").append(customerPoStageBean.getCustomerPoLineNo()).append("'");
            genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCustomerPoStageApprovedCatalogAdd(CatAddHeaderViewBean inputBean) {
        try {
            //do catalog add from EDI
            StringBuilder query = new StringBuilder("update customer_po_stage set status = 'NEW'");
            query.append(",status_detail = 'Approved Catalog Add'");
            query.append(",cat_part_no = ").append(SqlHandler.delimitString(inputBean.getCatPartNo())).append(",part_group_no = ").append(inputBean.getPartGroupNo());
            query.append(" where status = 'ERROR' and status_detail = 'Pending Catalog Add' and catalog_add_request_id = ").append(inputBean.getRequestId());
            query.append(" and company_id = '").append(inputBean.getCompanyId()).append("'");
            genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
            //do catalog add from Transaction
            setTransactionStatus(inputBean.getRequestId(),"Approved Catalog Add");
            setProcessTransaction(inputBean.getRequestId());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCustomerPoStageRejectedCatalogAdd(CatAddHeaderViewBean inputBean) {
        try {
            StringBuilder query = new StringBuilder("update customer_po_stage set status_detail = 'Rejected Catalog Add'");
            query.append(" where status = 'ERROR' and status_detail = 'Pending Catalog Add' and catalog_add_request_id = ").append(inputBean.getRequestId());
            query.append(" and company_id = '").append(inputBean.getCompanyId()).append("'");
            genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
            //do catalog add from Transaction
            setTransactionStatus(inputBean.getRequestId(),"Rejected Catalog Add");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateCatalogAddItemQc(CatalogInputBean inputBean, CatalogAddTransactionBean bean) {
        try {
            StringBuilder tmpComments = new StringBuilder("");
            if (!StringHandler.isBlankString(bean.getCasNumber()))
                tmpComments.append("CAS Number: ").append(bean.getCasNumber());
            if (!StringHandler.isBlankString(bean.getSubstanceName())) {
                if (tmpComments.length() > 1)
                    tmpComments.append("; Substance Name: ").append(bean.getSubstanceName());
                else
                    tmpComments.append("Substance Name: ").append(bean.getSubstanceName());
            }
            if (!StringHandler.isBlankString(bean.getActivityVendorName())) {
                if (tmpComments.length() > 1)
                    tmpComments.append("; Vendor Name: ").append(bean.getActivityVendorName());
                else
                    tmpComments.append("Vendor Name: ").append(bean.getActivityVendorName());

            }
            if (tmpComments.length() > 1) {
                StringBuilder query = new StringBuilder("update catalog_add_item_qc set comments = ").append(SqlHandler.delimitString(tmpComments.toString()));
                query.append(" where request_id = ").append(inputBean.getRequestId());
                genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    } //end of method

    private void createCatalogAddItemTransaction(CatalogInputBean inputBean, CatalogAddTransactionBean bean) {
        try {
            StringBuilder query = new StringBuilder("insert into catalog_add_item (company_id,request_id,part_id,line_item,starting_view");
            query.append(",material_desc,manufacturer,mfg_trade_name");
            query.append(",components_per_item,part_size,size_unit,pkg_style");
            query.append(")");
            query.append(" values ('").append(inputBean.getCompanyId()).append("',").append(inputBean.getRequestId()).append(",1,1,0");

            StringBuilder tmpMaterialDesc = new StringBuilder("");
            if (!StringHandler.isBlankString(bean.getActivityPartNumber()))
                tmpMaterialDesc.append(bean.getActivityPartNumber()).append(" ");
            if (!StringHandler.isBlankString(bean.getActivityPartNumber2()))
                tmpMaterialDesc.append(bean.getActivityPartNumber2()).append(" ");
            if (!StringHandler.isBlankString(bean.getActivityPartNumber3()))
                tmpMaterialDesc.append(bean.getActivityPartNumber3()).append(" ");
            if (!StringHandler.isBlankString(bean.getActivityPartDescription()))
                tmpMaterialDesc.append(bean.getActivityPartDescription()).append(" ");
            if (!StringHandler.isBlankString(bean.getActivityPartDescription2()))
                tmpMaterialDesc.append(bean.getActivityPartDescription2()).append(" ");
            if (!StringHandler.isBlankString(bean.getActivityPartDescription3()))
                tmpMaterialDesc.append(bean.getActivityPartDescription3()).append(" ");

            query.append(",").append(SqlHandler.delimitString(tmpMaterialDesc.toString().trim()));
            String tmpMfg = bean.getManufacturerName();
            if (StringHandler.isBlankString(tmpMfg))
                tmpMfg = bean.getActivityVendorName();
            query.append(",").append(SqlHandler.delimitString(tmpMfg));
            query.append(",").append(SqlHandler.delimitString(""));
            BigDecimal activityQuantity = bean.getActivityQuantity();
            query.append(",1,").append(activityQuantity!= null?activityQuantity:"1").append(",");
            String unitOfMeasure = bean.getUnitOfMeasure();
            query.append(!StringHandler.isBlankString(unitOfMeasure)?SqlHandler.delimitString(unitOfMeasure):"'each'").append(",'piece'");
            query.append(")");
            genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
        }catch (Exception e) {
            e.printStackTrace();
        }
    } //end of method

    private void createCatalogAddItemEdi(CatalogInputBean inputBean, CustomerPoStageBean customerPoStageBean) {
        try {
            StringBuilder query = new StringBuilder("insert into catalog_add_item (company_id,request_id,part_id,line_item,starting_view");
            query.append(",material_desc,manufacturer,mfg_trade_name");
            query.append(",components_per_item,part_size,size_unit,pkg_style");
            query.append(")");
            query.append(" values ('").append(inputBean.getCompanyId()).append("',").append(inputBean.getRequestId()).append(",1,1,0");

            StringBuilder tmpMaterialDesc = new StringBuilder("");
            if (!StringHandler.isBlankString(customerPoStageBean.getCustomerPartNo()))
                tmpMaterialDesc.append(customerPoStageBean.getCustomerPartNo()).append(" ");
            if (!StringHandler.isBlankString(customerPoStageBean.getPartShortDesc()))
                tmpMaterialDesc.append(customerPoStageBean.getPartShortDesc()).append(" ");
            if (customerPoStageBean.getUnspsc() != null)
                tmpMaterialDesc.append(customerPoStageBean.getUnspsc()).append(" ");
            if (!StringHandler.isBlankString(customerPoStageBean.getEdiVn()))
                tmpMaterialDesc.append(customerPoStageBean.getEdiVn()).append(" ");
            if (!StringHandler.isBlankString(customerPoStageBean.getEdiF7()))
                tmpMaterialDesc.append(customerPoStageBean.getEdiF7()).append(" ");

            query.append(",").append(SqlHandler.delimitString(tmpMaterialDesc.toString().trim()));
            query.append(",").append(SqlHandler.delimitString(!StringHandler.isBlankString(customerPoStageBean.getSupplierName())?customerPoStageBean.getSupplierName():customerPoStageBean.getSupplierCode()));
            query.append(",").append(SqlHandler.delimitString(customerPoStageBean.getManufacturerPartNum()));
            query.append(",1,1,'each','piece'");
            query.append(")");
            genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
        }catch (Exception e) {
            e.printStackTrace();
        }
    } //end of method

    private void createNewCatalogAddRequestNew(CatalogInputBean inputBean, BigDecimal requestId, String startingPoint) {
        try {
            StringBuilder query = new StringBuilder("insert into catalog_add_request_new (request_id,requestor,request_date,cat_part_no,part_description,catalog_id,");
            query.append("eng_eval_facility_id,catalog_company_id,eng_eval_work_area,account_sys_id,engineering_evaluation,company_id,");
            query.append("request_status,starting_view,submit_date,approval_path)");
            query.append(" values (");
            query.append(requestId);
            query.append(",");
            query.append(-1);
            query.append(",sysdate");
            //cat_part_no
            if (!StringHandler.isBlankString(inputBean.getCatalogAddCatPartNo()))
                query.append(",").append(SqlHandler.delimitString(inputBean.getCatalogAddCatPartNo()));
            else
                query.append(",null");
            //part_description
            if (!StringHandler.isBlankString(inputBean.getCatalogAddPartDesc()))
                query.append(",").append(SqlHandler.delimitString(inputBean.getCatalogAddPartDesc()));
            else
                query.append(",null");
            //catalog_id
            query.append(",'").append(inputBean.getCatalogId());
            query.append("','");
            query.append(inputBean.getFacilityId());
            query.append("','");
            query.append(inputBean.getCatalogCompanyId());
            query.append("',null,");
            query.append("null,'N','");
            query.append(inputBean.getCompanyId());
            query.append("',");
            if ("new".equalsIgnoreCase(startingPoint)) {
                if ("MSDS".equals(inputBean.getApprovalPath()))
                    query.append("14,6");
                else
                    query.append("0,0");
            }else if ("newSize".equalsIgnoreCase(startingPoint)) {
                query.append("1,1");
            }else {
                query.append("2,2");
            }
            query.append(",sysdate");
            if (!StringHandler.isBlankString(inputBean.getApprovalPath()))
                query.append(",'").append(inputBean.getApprovalPath()).append("'");
            else
                query.append(",'Part'");

            query.append(")");
            genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
        }catch (Exception e) {
            e.printStackTrace();
        }
    } //end of method

    private boolean dataCountIsZero(String query) {
        boolean result = false;
        try {
            String tmpVal = genericSqlFactory.selectSingleValue(query,connection);
            if ("0".equalsIgnoreCase(tmpVal)) {
                result = true;
            }
        } catch (Exception e) {
            log.error(e);
        }
        return result;
    }

    private String calculateClientUrl(String companyId) {
        String result = URL;
        try {
            StringBuilder query = new StringBuilder("select web_application_path from");
            if ("TCM_OPS".equals(getClient()))
                query.append(" customer.connection_pool_company");
            else
                query.append(" connection_pool_company");
            query.append(" where company_id = '").append(companyId).append("'");

            String tmpVal = genericSqlFactory.selectSingleValue(query.toString(),connection);
            if (!StringHandler.isBlankString(tmpVal)) {
                //removing current module and replace it with web_application_path from database
                result = URL.substring(0,URL.indexOf("/tcmIS"))+tmpVal;
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }  //end of method

} //end of class