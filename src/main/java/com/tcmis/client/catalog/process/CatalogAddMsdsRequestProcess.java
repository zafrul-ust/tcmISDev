package com.tcmis.client.catalog.process;

import java.util.Collection;
import java.util.Vector;
import java.util.HashMap;
import java.util.Iterator;
import java.sql.Connection;
import java.math.BigDecimal;

import com.tcmis.client.common.beans.CatalogQueueBean;
import com.tcmis.common.db.CatalogVendorDbManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.catalog.beans.CatAddHeaderViewBean;
import com.tcmis.client.catalog.beans.CatalogAddItemBean;
import com.tcmis.client.catalog.beans.CatalogInputBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.*;
import com.tcmis.common.admin.beans.PersonnelBean;

/******************************************************************************
 * Process for CatalogAddMsdsRequestProcess
 * @version 1.0
 *****************************************************************************/
public class CatalogAddMsdsRequestProcess extends BaseProcess {
    Log log = LogFactory.getLog(this.getClass());
    private DbManager dbManager;
    private Connection connection = null;
    private GenericSqlFactory genericSqlFactory;
    private ResourceLibrary library = null;
    private String URL = "";

    public static final int LINE_ITEM = 1;
    public static final int NEW_MSDS = 6;
    public static final int NEW_APPROVAL_CODE = 7;

    public CatalogAddMsdsRequestProcess(String client) {
        super(client);
    }

    public CatalogAddMsdsRequestProcess(String client, String locale, String URL) {
        super(client, locale);
        this.URL = URL;
        library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
    }

    private void updateCatalogAddItemVocetData(BigDecimal requestId) {
        try {
            //update vocet status and coat category for components
            StringBuilder query = new StringBuilder("update catalog_add_item cai set (vocet_status_id,vocet_coat_category_id) = ");
            query.append("( select vocet_status,vocet_category_id from vocet_msds vm, catalog_add_request_new carn, catalog_company cc");
            query.append(" where carn.company_id = cc.company_id and carn.catalog_company_id = cc.catalog_company_id");
            query.append(" and carn.catalog_id = cc.catalog_id and carn.company_id = vm.company_id and carn.eng_eval_facility_id = vm.facility_id");
            query.append(" and cc.customer_msds_db = vm.customer_msds_db and carn.company_id = cai.company_id and carn.request_id = cai.request_id");
            query.append(" and vm.customer_msds_or_mixture_no = cai.customer_msds_number) where cai.request_id = ").append(requestId);
            genericSqlFactory.deleteInsertUpdate(query.toString(),connection);

            //update vocet status and coat category for kit/mixture
            query = new StringBuilder("update catalog_add_item cai set (vocet_status_id,vocet_coat_category_id) = ");
            query.append("( select vocet_status,vocet_category_id from vocet_msds vm, catalog_add_request_new carn, catalog_company cc");
            query.append(" where carn.company_id = cc.company_id and carn.catalog_company_id = cc.catalog_company_id");
            query.append(" and carn.catalog_id = cc.catalog_id and carn.company_id = vm.company_id and carn.eng_eval_facility_id = vm.facility_id");
            query.append(" and cc.customer_msds_db = vm.customer_msds_db and carn.company_id = cai.company_id and carn.request_id = cai.request_id");
            query.append(" and vm.customer_msds_or_mixture_no = cai.customer_mixture_number) where cai.request_id = ").append(requestId);
            genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
        }catch (Exception e) {
            e.printStackTrace();
        }
    } //end of method

    public void deleteLine(BigDecimal requestId, BigDecimal lineItem, BigDecimal partId) {
        try {
            DbManager dbManager = new DbManager(getClient(),this.getLocale());
            GenericSqlFactory factory = new GenericSqlFactory(dbManager);
            StringBuilder query = new StringBuilder("delete from catalog_add_item where request_id = ").append(requestId).append(" and line_item = ").append(lineItem);
            query.append(" and part_id = ").append(partId);
            factory.deleteInsertUpdate(query.toString());
        }catch (Exception e) {
            e.printStackTrace();
        }
    } //end of method

    public CatAddHeaderViewBean getNewCatalogItemData(BigDecimal requestId, BigDecimal partId) throws BaseException {
        CatAddHeaderViewBean catAddHeaderViewBean = null;
        try {
            dbManager = new DbManager(getClient(),this.getLocale());
            connection = dbManager.getConnection();
            genericSqlFactory = new GenericSqlFactory(dbManager);

            CatalogAddRequestProcess process = new CatalogAddRequestProcess(this.getClient(),this.getLocale(),URL);
            process.setFactoryConnection(genericSqlFactory,connection);
            //get catalog_add_request_new data
            Vector<CatAddHeaderViewBean> headerColl = (Vector)process.getCatAddHeaderView(requestId);
            catAddHeaderViewBean = headerColl.get(0);
            catAddHeaderViewBean.setRequestId(requestId);

            //get catalog_add_item data
            StringBuilder query = new StringBuilder("select * from catalog_add_item where request_id = ").append(requestId);
            query.append(" and line_item = ").append(LINE_ITEM).append(" and part_id = ").append(partId);
            genericSqlFactory.setBeanObject(new CatalogAddItemBean());
            Collection catalogAddItemColl = genericSqlFactory.selectQuery(query.toString(),connection);
            if (catalogAddItemColl.size() == 0) {
                CatalogAddItemBean catalogAddItemBean = new CatalogAddItemBean();
                catalogAddItemBean.setRequestId(requestId);
                catalogAddItemBean.setLineItem(new BigDecimal(LINE_ITEM));
                catalogAddItemColl.add(catalogAddItemBean);
            }
            catAddHeaderViewBean.setCatalogAddItemColl(catalogAddItemColl);

            //get shipping weight units
            EngEvalProcess engEvalProcess = new EngEvalProcess(this.getClient(),this.getLocale(),URL);
            engEvalProcess.setFactoryConnection(genericSqlFactory,connection);
            catAddHeaderViewBean.setShippingWeightUnitColl(engEvalProcess.getShippingWeightSizeUnit(false));
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            dbManager.returnConnection(connection);
            dbManager = null;
            connection = null;
            genericSqlFactory = null;
        }
        return catAddHeaderViewBean;
    }

    private StringBuilder copyCatalogAddItemByMaterial(CatalogAddItemBean inputBean) {
        StringBuilder query = new StringBuilder("insert into catalog_add_item (company_id,request_id,part_id,line_item,material_id,material_desc,mfg_id,manufacturer,");
        query.append("mfg_trade_name,starting_view,customer_msds_number,approved_cust_msds_number,part_size,MIX_RATIO_AMOUNT,size_unit,MIX_RATIO_SIZE_UNIT,customer_mixture_number,approved_cust_mixture_number,mixture_desc,item_id");
        query.append(",nanomaterial,aerosol_container,radioactive)");
        query.append(" (select carn.company_id,carn.request_id,").append(inputBean.getPartId()).append(",1,m.material_id,m.material_desc,mfg.mfg_id,mfg.mfg_desc,");
        query.append("m.trade_name,").append(inputBean.getStartingView()).append(",'").append(StringHandler.emptyIfNull(inputBean.getCustomerMsdsNumber())).append("','").append(StringHandler.emptyIfNull(inputBean.getCustomerMsdsNumber())).append("'");
        query.append(",").append(inputBean.getPartSize()).append(",").append(inputBean.getPartSize());
        if (!StringHandler.isBlankString(inputBean.getSizeUnit())) {
            query.append(",'").append(inputBean.getSizeUnit()).append("'").append(",'").append(inputBean.getSizeUnit()).append("'");
        }else {
            query.append(",null").append(",null");
        }
        if (!StringHandler.isBlankString(inputBean.getCustomerMixtureNumber())) {
            query.append(",").append(SqlHandler.delimitString(inputBean.getCustomerMixtureNumber()));
            query.append(",").append(SqlHandler.delimitString(inputBean.getCustomerMixtureNumber()));
        }else {
            query.append(",null,null");
        }
        if (!StringHandler.isBlankString(inputBean.getMixtureDesc())) {
            query.append(",").append(SqlHandler.delimitString(inputBean.getMixtureDesc()));
        }else {
            query.append(",null");
        }
        if (inputBean.getItemId() != null) {
            query.append(",").append(inputBean.getItemId());
        }else {
            query.append(",null");
        }
        query.append(",coalesce(sc.nanomaterial, s.nanomaterial) nanomaterial,decode(coalesce(sc.physical_state, s.physical_state), 'liquid/aerosol', 'Y', 'N') aerosol_container,coalesce(sc.radioactive, s.radioactive) radioactive");
        query.append(" from manufacturer mfg,company_msds sc,global.msds s,global.material m,catalog_add_request_new carn ");
        query.append(" where mfg.mfg_id = m.mfg_id and sc.company_id (+) = user and sc.revision_date(+) = s.revision_date and sc.material_id (+) = s.material_id and");
        query.append(" s.revision_date (+) = m.last_msds_revision_date and s.material_id (+) = m.material_id");
        query.append(" and carn.request_id = ").append(inputBean.getRequestId()).append(" and m.material_id = ").append(inputBean.getMaterialId()).append(")");
        return query;
    }

    private StringBuilder createNewCatalogAddItem(BigDecimal requestId, BigDecimal partId, BigDecimal startingView) {
        StringBuilder query = new StringBuilder("insert into catalog_add_item (company_id,request_id,line_item,part_id,starting_view) (select company_id,request_id,1,").append(partId).append(",").append(startingView);
        query.append(" from catalog_add_request_new where request_id = "+requestId+")");
        return query;
    }

    public BigDecimal buildNewMaterial(BigDecimal requestId) throws Exception{
        BigDecimal partId = null;
        try {
            BigDecimal startingView = new BigDecimal(CatalogAddMsdsRequestProcess.NEW_MSDS);
            dbManager = new DbManager(getClient(),this.getLocale());
            connection = dbManager.getConnection();
            genericSqlFactory = new GenericSqlFactory(dbManager);

            StringBuilder query = new StringBuilder("select nvl(max(part_id)+1,1) from catalog_add_item where request_id = ").append(requestId);
            partId = new BigDecimal(genericSqlFactory.selectSingleValue(query.toString(),connection));
            query = createNewCatalogAddItem(requestId,partId,startingView);
            genericSqlFactory.deleteInsertUpdate(query.toString(),connection);

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            dbManager.returnConnection(connection);
            dbManager = null;
            connection = null;
            genericSqlFactory = null;
        }
        return partId;
    } //end of method

    public void buildNewComponent(PersonnelBean personnelBean, CatAddHeaderViewBean inputBean, CatalogAddItemBean catalogAddItemBean) throws Exception{
        try {
            //if catalog vendor then need to use CatalogVendorDBManager so it can set security permission
            if ("CATALOG_VENDOR".equalsIgnoreCase(getClient()))
                dbManager = new CatalogVendorDbManager(personnelBean.getPersonnelIdBigDecimal(),getClient());
            else
                dbManager = new DbManager(getClient(),this.getLocale());
            connection = dbManager.getConnection();
            genericSqlFactory = new GenericSqlFactory(dbManager);

            //creating new catalog_add_item
            StringBuilder query = new StringBuilder("select nvl(max(part_id)+1,1) from catalog_add_item where request_id = ").append(inputBean.getRequestId());
            BigDecimal partId = new BigDecimal(genericSqlFactory.selectSingleValue(query.toString(),connection));
            query = createNewCatalogAddItem(inputBean.getRequestId(),partId,new BigDecimal(0));
            genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
            catalogAddItemBean.setPartId(partId);
            //updating catalog_add_item data
            EngEvalProcess engEvalProcess = new EngEvalProcess(this.getClient(),this.getLocale(),URL);
            engEvalProcess.setFactoryConnection(genericSqlFactory,connection);
            engEvalProcess.updateCatalogAddItemWithScreenData(inputBean.getRequestId(),catalogAddItemBean);
            //updating catalog_add_item_qc and catalog_add_item_orig
            engEvalProcess.updateCatalogAddItemQcNOrig(inputBean.getRequestId());
            //populate catalog_add_item_locale for request
            engEvalProcess.populateCatalogAddItemLocale(inputBean.getRequestId());
            //setup new component data
            engEvalProcess.isSdsExists(inputBean.getRequestId());
            //create work queue items for new component
            //get new component catalog_add_item_id
            query = new StringBuilder("select company_id from catalog_add_request_new where request_id = ").append(inputBean.getRequestId());
            catalogAddItemBean.setCompanyId(genericSqlFactory.selectSingleValue(query.toString(),connection));
            //get new component catalog_vendor_assignment_id
            boolean supplierIsWesco = false;
            String tmpCatalogVendorAssignmentId = "";
            query = new StringBuilder("select max(nvl(alt_supplier,supplier)) from catalog_queue where catalog_add_request_id = ").append(inputBean.getRequestId());
            query.append(" and line_item = ").append(inputBean.getLineItem());
            if (CatalogQueueBean.WESCO_SUPPLIER_ID.equals(genericSqlFactory.selectSingleValue(query.toString(),connection)))
                supplierIsWesco = true;
            else {
                query = new StringBuilder("select max(catalog_vendor_assignment_id) from catalog_queue where catalog_add_request_id = ").append(inputBean.getRequestId());
                query.append(" and line_item = ").append(inputBean.getLineItem());
                tmpCatalogVendorAssignmentId = genericSqlFactory.selectSingleValue(query.toString(),connection);
            }
            engEvalProcess.createWorkQueueItem(catalogAddItemBean.getCompanyId(),inputBean.getRequestId(),"SDS Sourcing", "material_qc_status","0",supplierIsWesco,tmpCatalogVendorAssignmentId);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            dbManager.returnConnection(connection);
            dbManager = null;
            connection = null;
            genericSqlFactory = null;
        }
    } //end of method

    public BigDecimal buildAddMaterial(BigDecimal requestId, String custMsdsNo, BigDecimal materialId) throws Exception{
        BigDecimal partId = null;
        try {
            BigDecimal startingView = new BigDecimal(CatalogAddMsdsRequestProcess.NEW_APPROVAL_CODE);
            dbManager = new DbManager(getClient(),this.getLocale());
            connection = dbManager.getConnection();
            genericSqlFactory = new GenericSqlFactory(dbManager);

            StringBuilder query = new StringBuilder("select nvl(max(part_id)+1,1) from catalog_add_item where request_id = ").append(requestId);
            partId = new BigDecimal(genericSqlFactory.selectSingleValue(query.toString(),connection));

            copyFromExistingMsds(requestId,partId,custMsdsNo,materialId,startingView,true);

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            dbManager.returnConnection(connection);
            dbManager = null;
            connection = null;
            genericSqlFactory = null;
        }
        return partId;
    } //end of method

    public BigDecimal newMsds(CatalogInputBean bean, PersonnelBean personnelBean) throws Exception{
        BigDecimal requestId = null;
        try {
            dbManager = new DbManager(getClient(),this.getLocale());
            connection = dbManager.getConnection();
            genericSqlFactory = new GenericSqlFactory(dbManager);
            StringBuilder query = new StringBuilder("select request_seq.nextval from dual");
            requestId = new BigDecimal(genericSqlFactory.selectSingleValue(query.toString(),connection));
            String tmpStartingPoint = "newMsds";
            if (!StringHandler.isBlankString(bean.getCustMsdsNo()) && !"0".equals(bean.getCustMsdsNo())) {
                tmpStartingPoint = "newApprovalCode";
            }
            //create new catalog_add_request_new
            CatalogAddRequestProcess process = new CatalogAddRequestProcess(getClient(),getLocale(),URL);
            process.setFactoryConnection(genericSqlFactory,connection);
            process.createNewCatalogAddRequestNew(bean,personnelBean,requestId,tmpStartingPoint);
            //copy material data
            if ("newApprovalCode".equals(tmpStartingPoint) || !StringHandler.isBlankString(bean.getMaterialId())) {
                BigDecimal startingView = new BigDecimal(CatalogAddMsdsRequestProcess.NEW_APPROVAL_CODE);
                BigDecimal partId = new BigDecimal(1);
                CatalogAddItemBean caiBean = copyFromExistingMsds(requestId,partId,bean.getCustMsdsNo(),new BigDecimal(bean.getMaterialId()),startingView,false);
                if (!StringHandler.isBlankString(caiBean.getUnitOfMeasure()) && caiBean.getPoundsPerUnit() != null) {
                    query = new StringBuilder("update catalog_add_request_new set unit_of_measure = '").append(caiBean.getUnitOfMeasure());
                    query.append("', pounds_per_unit = ").append(caiBean.getPoundsPerUnit()).append(" where request_id = ").append(requestId);
                    genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
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
        return requestId;
    } //end of method

    private CatalogAddItemBean copyFromExistingMsds(BigDecimal requestId, BigDecimal partId, String custMsdsNo, BigDecimal materialId, BigDecimal startingView, boolean clearMixture) throws Exception {
        CatalogAddItemBean resultBean = new CatalogAddItemBean();
        try {
            StringBuilder query = new StringBuilder("select * from table (pkg_catalog_add.fx_msds_mixture_data(").append(requestId).append(",");
            query.append(SqlHandler.delimitString(custMsdsNo)).append("))");

            genericSqlFactory.setBeanObject(new CatalogAddItemBean());
            Collection dataC = genericSqlFactory.selectQuery(query.toString(),connection);
            Iterator iter = dataC.iterator();
            int count = 0;
            String lastDataKey = "";
            while(iter.hasNext()) {
                CatalogAddItemBean bean = (CatalogAddItemBean)iter.next();
                if (count == 0) {
                    resultBean = bean;
                }
                //handling customer msds number with multiple materials
                if (lastDataKey.equals(bean.getCustomerMsdsNumber())) continue;

                bean.setLineItem(new BigDecimal(1));
                bean.setPartId(partId.add(new BigDecimal(count++)));
                bean.setStartingView(startingView);
                if (clearMixture) {
                    bean.setCustomerMixtureNumber("");
                    bean.setMixtureDesc("");
                    bean.setItemId(null);
                }
                query = copyCatalogAddItemByMaterial(bean);
                genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
                //set last data key equals to current data key
                lastDataKey = bean.getCustomerMsdsNumber();
            }
            //if custMsdsNo is not in customer_msds_xref then it's just material_id
            if (count == 0) {
                CatalogAddItemBean bean = new CatalogAddItemBean();
                bean.setRequestId(requestId);
                bean.setStartingView(new BigDecimal(6));
                bean.setLineItem(new BigDecimal(1));
                bean.setPartId(partId);
                bean.setMaterialId(materialId);
                query = copyCatalogAddItemByMaterial(bean);
                genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
            }
            //update vocet data
            updateCatalogAddItemVocetData(requestId);
        }catch (Exception e) {
            throw e;
        }
        return resultBean;
    }

    private void saveHeaderData(CatAddHeaderViewBean inputBean) {
        try {
            StringBuilder query = new StringBuilder("update catalog_add_request_new set");
            if (!StringHandler.isBlankString(inputBean.getUnitOfMeasure())) {
                query.append(" unit_of_measure = ").append(SqlHandler.delimitString(inputBean.getUnitOfMeasure()));
            }else {
                query.append(" unit_of_measure = null");
            }
            if (inputBean.getPoundsPerUnit() != null) {
                query.append(",pounds_per_unit = ").append(inputBean.getPoundsPerUnit());
            }else {
                query.append(",pounds_per_unit = null");
            }
            if (!StringHandler.isBlankString(inputBean.getMessageToApprovers())) {
                query.append(",message_to_approvers = ").append(SqlHandler.delimitString(inputBean.getMessageToApprovers()));
            }else {
                query.append(",message_to_approvers = null");
            }
            query.append(" where request_id = ").append(inputBean.getRequestId());
            genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String saveRequestData(CatAddHeaderViewBean inputBean, Collection beans) throws BaseException {
        String result = "OK";
        try {
            dbManager = new DbManager(getClient(),this.getLocale());
            connection = dbManager.getConnection();
            genericSqlFactory = new GenericSqlFactory(dbManager);

            boolean isMixture = false;
            if (beans.size() > 1) {
                isMixture = true;
            }else {
                //if this request is not Kit then set uom and pounds per unit to null
                inputBean.setUnitOfMeasure("");
                inputBean.setPoundsPerUnit(null);
            }
            //save header data
            saveHeaderData(inputBean);

            //catalog_add_item data
            EngEvalProcess engEvalProcess = new EngEvalProcess(this.getClient(),this.getLocale(),URL);
            engEvalProcess.setFactoryConnection(genericSqlFactory,connection);
            Iterator iter = beans.iterator();
            while(iter.hasNext()) {
                CatalogAddItemBean bean = (CatalogAddItemBean)iter.next();
                if (!isMixture) {
                    bean.setCustomerMixtureNumber("");
                }
                bean.setMixRatioAmount(bean.getPartSize());
                bean.setMixRatioSizeUnit(bean.getSizeUnit());
                engEvalProcess.updateCatalogAddItemWithScreenData(inputBean.getRequestId(),bean);
            }
        }catch (Exception e) {
            e.printStackTrace();
            result = library.getString("msg.tcmiserror");
        }finally {
            dbManager.returnConnection(connection);
            dbManager = null;
            connection = null;
            genericSqlFactory = null;
        }
        return result;
    }

    public String saveMaterialData(CatAddHeaderViewBean inputBean, CatalogAddItemBean catalogAddItemBean) throws BaseException {
        String result = "OK";
        try {
            dbManager = new DbManager(getClient(),this.getLocale());
            connection = dbManager.getConnection();
            genericSqlFactory = new GenericSqlFactory(dbManager);
            EngEvalProcess engEvalProcess = new EngEvalProcess(this.getClient(),this.getLocale(),URL);
            engEvalProcess.setFactoryConnection(genericSqlFactory,connection);
            catalogAddItemBean.setMixRatioAmount(catalogAddItemBean.getPartSize());
            catalogAddItemBean.setMixRatioSizeUnit(catalogAddItemBean.getSizeUnit());
            engEvalProcess.updateCatalogAddItemWithScreenData(inputBean.getRequestId(),catalogAddItemBean);
        }catch (Exception e) {
            e.printStackTrace();
            result = library.getString("msg.tcmiserror");
        }finally {
            dbManager.returnConnection(connection);
            dbManager = null;
            connection = null;
            genericSqlFactory = null;
        }
        return result;
    }

    public CatAddHeaderViewBean getMsdsData(BigDecimal requestId, PersonnelBean personnelBean) throws BaseException {
        CatAddHeaderViewBean catAddHeaderViewBean = null;
        try {
            dbManager = new DbManager(getClient(),this.getLocale());
            connection = dbManager.getConnection();
            genericSqlFactory = new GenericSqlFactory(dbManager);
            CatalogAddRequestProcess process = new CatalogAddRequestProcess(this.getClient(),this.getLocale(),URL);
            process.setFactoryConnection(genericSqlFactory,connection);
            //get catalog_add_request_new data
            Vector<CatAddHeaderViewBean> headerColl = (Vector)process.getCatAddHeaderView(requestId);
            catAddHeaderViewBean = headerColl.get(0);
            process.canEditMixtureData(catAddHeaderViewBean, personnelBean);
            catAddHeaderViewBean.setRequestId(requestId);


            //if request is pending approval then get all approvers who can approve this request
            process.getApproverForRequest(catAddHeaderViewBean,requestId,personnelBean);
            //can edit mixture data
            process.canEditMixtureData(catAddHeaderViewBean,personnelBean);
            //set cat add approval detail needed flag
            process.catAddApprovalDetailNeeded(catAddHeaderViewBean);
            //set view level
            process.calculateViewLevel(catAddHeaderViewBean,personnelBean);

            //required customer mfg id
            process.customerMfgIdRequired(catAddHeaderViewBean,personnelBean);
            //get MSDS/Material for request
            StringBuilder query = new StringBuilder("select cai.*,decode(carn.request_status,'12','Approved','7','Rejected','Pending Approval') display_status");
            query.append(",fx_msds_material_online(cai.material_id) msds_on_line");
            query.append(",pkg_msds_search.fx_msds_comp_wt_fraction(cai.company_id,'','','',cai.item_id,cai.material_id)*100 percent_by_weight");
            query.append(",pkg_msds_search.fx_approval_code(carn.company_id,carn.eng_eval_facility_id,cai.material_id,cc.customer_msds_db,nvl(cai.approved_cust_mixture_number,cai.customer_mixture_number),null,'Y') kit_approval_code");
            query.append(",pkg_msds_search.fx_approval_code(carn.company_id,carn.eng_eval_facility_id,cai.material_id,cc.customer_msds_db,nvl(cai.approved_cust_msds_number,cai.customer_msds_number),null,'Y') msds_approval_code");
            query.append(",cai.customer_mfg_id");
            query.append(" from catalog_add_request_new carn, catalog_add_item cai, catalog_company cc where carn.company_id = cai.company_id and carn.request_id = cai.request_id AND carn.company_id = cc.company_id and carn.catalog_id = cc.catalog_id");
            query.append(" and carn.request_id = ").append(requestId);
            query.append(" order by line_item,part_id");
            genericSqlFactory.setBeanObject(new CatalogAddItemBean());
            Collection catalogAddItemColl = genericSqlFactory.selectQuery(query.toString(),connection);

            Collection msdsDataColl = new Vector(catalogAddItemColl.size());
            HashMap m1 = new HashMap();
            Integer i1 = null;
            //looping thru line data
            Iterator iter = catalogAddItemColl.iterator();
            int i = 0;
            while(iter.hasNext()) {
                CatalogAddItemBean tmpBean = (CatalogAddItemBean) iter.next();
                String tmpLineItem = "1";
                if (m1.get(tmpLineItem) == null) {
                    i1 = new Integer(0);
                    m1.put(tmpLineItem, i1);
                }
                i1 = (Integer) m1.get(tmpLineItem);
                i1 = new Integer(i1.intValue() + 1);
                m1.put(tmpLineItem, i1);

                msdsDataColl.add(tmpBean);
                i++;
                //get customer manufacturer ID for our manufacturer
                if (catAddHeaderViewBean.requireCustomerMfgId()) {
                    tmpBean.setCustomerMfgIdDisplay(process.getCustomerMfg(catAddHeaderViewBean.getCompanyId(),tmpBean.getMfgId()));
                }
            } //end of loop
            catAddHeaderViewBean.setMsdsDataColl(msdsDataColl);
            catAddHeaderViewBean.setMsdsRowSpan(m1);
            if (i > 1) {
                catAddHeaderViewBean.setAllowMixture("Y");
            }else {
                catAddHeaderViewBean.setAllowMixture("N");
            }

            //HMRB
            process.getHmrbData(catAddHeaderViewBean,"MSDS");
            //required customer msds
            process.requireCustomerMsds(catAddHeaderViewBean,personnelBean);
            process.getCatAddRequestorEditMsdsId(catAddHeaderViewBean);

            if (personnelBean.isFeatureReleased("HmrbTab",catAddHeaderViewBean.getEngEvalFacilityId(),catAddHeaderViewBean.getCompanyId())) {
                //get vocet status and coat category
                catAddHeaderViewBean.setVocetStatusColl(process.getVocetStatus(catAddHeaderViewBean));
                catAddHeaderViewBean.setVocetCoatCategoryColl(process.getVocetCoatCategory(catAddHeaderViewBean));
                //can edit vocet properties
                process.canEditVocetProperties(catAddHeaderViewBean,personnelBean);
                //can edit nano material
                process.canEditNanomaterial(catAddHeaderViewBean,personnelBean);
                //can edit radioactive
                process.canEditRadioactive(catAddHeaderViewBean,personnelBean);
            }else {
                catAddHeaderViewBean.setVocetStatusColl(new Vector(0));
                catAddHeaderViewBean.setVocetCoatCategoryColl(new Vector(0));
            }

            //has keyword and/or list approval
            catAddHeaderViewBean.setHasKeywordListApproval(process.hasKeywordListApproval(requestId));

            //get shipping weight units
            EngEvalProcess engEvalProcess = new EngEvalProcess(this.getClient(),this.getLocale(),URL);
            engEvalProcess.setFactoryConnection(genericSqlFactory,connection);
            catAddHeaderViewBean.setShippingWeightUnitColl(engEvalProcess.getShippingWeightSizeUnit(true));

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            dbManager.returnConnection(connection);
            dbManager = null;
            connection = null;
            genericSqlFactory = null;
        }
        return catAddHeaderViewBean;
    }

} //end of class