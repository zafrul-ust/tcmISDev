package com.tcmis.client.common.process;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;





import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.*;
import com.tcmis.client.common.beans.MsdsDocumentInputBean;
import com.tcmis.client.common.beans.MsdsDocumentViewBean;
import com.tcmis.client.common.factory.MsdsDocumentViewBeanFactory;

public class MsdsDocumentProcess extends BaseProcess {
    Log log = LogFactory.getLog(this.getClass());

    public MsdsDocumentProcess(String client) {
        super(client);
    }

    public MsdsDocumentProcess(String client, String locale) {
        super(client, locale);
    }

    public Collection getSearchResult(MsdsDocumentInputBean
            msdsDocumentInputBean) throws BaseException {
        DbManager dbManager = new DbManager(getClient(), getLocale());
        MsdsDocumentViewBeanFactory factory = new MsdsDocumentViewBeanFactory(
                dbManager);

        SearchCriteria criteria = new SearchCriteria();
        if (msdsDocumentInputBean.getMaterialId() != null) {
            criteria.addCriterion("materialId", SearchCriterion.EQUALS, msdsDocumentInputBean.getMaterialId().toString());
        }
        if (!StringHandler.isBlankString(msdsDocumentInputBean.getCompanyId())) {
            criteria.addCriterion("companyId", SearchCriterion.EQUALS, msdsDocumentInputBean.getCompanyId());
        }
        if (!msdsDocumentInputBean.getShowDocuments().equalsIgnoreCase("includeDeleted")) {
            criteria.addCriterion("deletedBy", SearchCriterion.IS, "null");
        }

        return factory.select(criteria);
    }
    
    public Collection getSearchKitResult(MsdsDocumentInputBean
            msdsDocumentInputBean) throws BaseException {
        DbManager dbManager = new DbManager(getClient(), getLocale());
        GenericSqlFactory factory = new GenericSqlFactory(
                dbManager);
        factory.setBean(new MsdsDocumentViewBean());
        StringBuilder query = new StringBuilder("select md.*,md.document_type document_type_name,fx_personnel_id_to_name(md.entered_by) entered_by_name from mixture_document md where mixture_id = ");
        query.append(msdsDocumentInputBean.getMixtureId()).append(" and mixture_revision_date = '").append(msdsDocumentInputBean.getMixtureRevisionDate()).append("'");
        if (!msdsDocumentInputBean.getShowDocuments().equalsIgnoreCase("includeDeleted"))
        	query.append(" and deleted_on is null");

        return factory.selectQuery(query.toString());
    }

    public String addNewDocument(MsdsDocumentInputBean bean, boolean fromKitManagement) throws BaseException {
        DbManager dbManager = new DbManager(getClient(), getLocale());

        if (bean.getTheFile() != null && bean.getTheFile().length() > (long) 0) {
            //copy file to server
            File f = bean.getTheFile();
            String fileType = f.getName().substring(f.getName().lastIndexOf("."));
            bean.setFileName(fileType);
            ResourceLibrary resource = new ResourceLibrary("msdsdocument");
            String date = new SimpleDateFormat("yyyy/MM/").format(new Date());
            String docPath = (fromKitManagement?resource.getString("kit.documentfile.path"):resource.getString("msds.documentfile.path"))+date;
            try {
                File dir = new File(docPath);
                if ( ! dir.exists()) {
                    dir.mkdirs();
                }
                File file = File.createTempFile(""+(fromKitManagement?bean.getCustomerMixtureNumber():bean.getMaterialId()) + "-",""+fileType+"", dir);
                FileHandler.copy(bean.getTheFile(), file);
                bean.setFileName(file.getName());
            }
            catch (Exception e) {
            	StringBuilder errMessage = new StringBuilder("Error saving file for ");
            	
            	if(fromKitManagement)
            		errMessage.append("mixture " + bean.getCustomerMixtureNumber());
        		else
        			errMessage.append("material " + bean.getMaterialId());	
            	
            	log.error(errMessage.toString(), e);
                return null;
            }
            String documentFileUrl = resource.getString("msds.documentfile.hosturl") + (fromKitManagement?resource.getString("kit.documentfile.urlpath"):resource.getString("msds.documentfile.urlpath"))+date + bean.getFileName();
            bean.setDocumentUrl(documentFileUrl);
            try {
            	if(fromKitManagement)
            	{
        			SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy");
                    GenericSqlFactory factory = new GenericSqlFactory(dbManager);
                    BigDecimal documentId = factory.getSequence("document_seq");
                    StringBuilder mixtureDocInsert = new StringBuilder("insert into mixture_document (COMPANY_ID,DOCUMENT_DATE,DOCUMENT_ID,DOCUMENT_NAME,DOCUMENT_SOURCE,DOCUMENT_TYPE,DOCUMENT_URL,ENTERED_BY,ENTERED_ON,MIXTURE_ID,MIXTURE_REVISION_DATE,STATUS) values(");
                    mixtureDocInsert.append("'").append(bean.getCompanyId()).append("','").append(dateFormatter.format(bean.getDocumentDate())).append("',").append(documentId).append(",'").append(bean.getDocumentName()).append("','Mixture','").append(bean.getDocumentType()).append("','");
                    mixtureDocInsert.append(bean.getDocumentUrl()).append("',").append(bean.getEnteredBy()).append(",sysdate,").append(bean.getMixtureId()).append(",'").append(bean.getMixtureRevisionDate()).append("','A')");
                    factory.deleteInsertUpdate(mixtureDocInsert.toString());
            	}
            	else
            	{
	                MsdsDocumentViewBeanFactory factory = new MsdsDocumentViewBeanFactory(dbManager);
                    bean.setDocumentSource("Catalog");
	                factory.insert(bean);
            	}
            }catch (Exception e) {
            	StringBuilder errMessage = new StringBuilder("Error saving document data for ");
            	
            	if(fromKitManagement)
            		errMessage.append("mixture " + bean.getCustomerMixtureNumber());
        		else
        			errMessage.append("material " + bean.getMaterialId());	
            	
            	log.error(errMessage.toString(), e);
                return null;
            }

            return documentFileUrl;

        } else {
            return null;
        }
    }

    public int deleteDocument(Collection msdsDocumentInputBeanCollection, BigDecimal personnelId, String companyId) throws BaseException {
        DbManager dbManager = new DbManager(getClient(), getLocale());
        MsdsDocumentViewBeanFactory factory = new MsdsDocumentViewBeanFactory(dbManager);
        int documentDeleteCount = 0;

        Iterator documentIterator = msdsDocumentInputBeanCollection.iterator();
        while (documentIterator.hasNext()) {
            MsdsDocumentInputBean msdsDocumentInputBean = (MsdsDocumentInputBean) documentIterator.next();

            SearchCriteria criteria = new SearchCriteria();
            criteria.addCriterion("documentId",SearchCriterion.EQUALS,"" + msdsDocumentInputBean.getDocumentId() + "");
            /*
            if (!StringHandler.isBlankString(companyId))
                criteria.addCriterion("companyId", SearchCriterion.EQUALS,msdsDocumentInputBean.getCompanyId());
            else
            	criteria.addCriterion("companyId", SearchCriterion.EQUALS, "GLOBAL");
            */
            //mark record as deleted
            try {
            	documentDeleteCount = factory.delete(criteria, personnelId);
            } catch (Exception e) {
            	e.printStackTrace();
            	return -1;
            }

            /*leave physical file alone, incase user want to bring it back
            String documentUrl = msdsDocumentInputBean.getDocumentUrl();
            String documentFilePath = documentUrl.substring(documentUrl.lastIndexOf("/"),documentUrl.length());
            if (documentFilePath.length() > 0) {
                ResourceLibrary resource = new ResourceLibrary("msdsdocument");
                File documentFile = new File("" + resource.getString("msds.documentfile.path") + "" + documentFilePath + "");
                if (documentFile.isFile()) {
                    documentFile.delete();
                }
            }
            */
        }

        return documentDeleteCount;
    }
    
    public int deleteKitDocument(Collection msdsDocumentInputBeanCollection, BigDecimal personnelId, String companyId) throws BaseException {
        DbManager dbManager = new DbManager(getClient(), getLocale());
        GenericSqlFactory factory = new GenericSqlFactory(
                dbManager);
        int documentDeleteCount = 0;

        Iterator documentIterator = msdsDocumentInputBeanCollection.iterator();
        while (documentIterator.hasNext()) {
            MsdsDocumentInputBean msdsDocumentInputBean = (MsdsDocumentInputBean) documentIterator.next();

            StringBuilder mixtureDocDelete = new StringBuilder("update mixture_document set status= 'I', DELETED_ON = sysdate, DELETED_BY = ").append(personnelId).append(" where document_id = ").append(msdsDocumentInputBean.getDocumentId());
            factory.deleteInsertUpdate(mixtureDocDelete.toString());
        }

        return documentDeleteCount;
    }
}