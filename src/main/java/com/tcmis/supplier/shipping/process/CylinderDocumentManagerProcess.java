package com.tcmis.supplier.shipping.process;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.VvDocumentTypeBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.*;
import com.tcmis.supplier.shipping.beans.CylinderDocumentManagerBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.framework.BaseProcess;

import java.io.File;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

/******************************************************************************
 * Process used by CylinderDocumentManagerProcess
 * @version 1.0
 *****************************************************************************/

public class CylinderDocumentManagerProcess  extends BaseProcess {
	Log log = LogFactory.getLog(this.getClass());
	protected DbManager dbManager = null;
	protected GenericSqlFactory factory = null;

	public CylinderDocumentManagerProcess(String client, String locale) {
		super(client, locale);
		init(new CylinderDocumentManagerBean());
	}

	private void init(CylinderDocumentManagerBean bean) {
		try {
			dbManager = new DbManager(client, this.getLocale());
			factory = new GenericSqlFactory(dbManager, bean);
		}
		catch (Exception ex) {
			log.error("Error initializing CylinderDocumentManagerProcess", ex);
		}
	}

	public Collection getCylinderDocumentTypes (CylinderDocumentManagerBean inputBean) throws Exception {
		Collection result = new Vector();
		try {
			SearchCriteria criteria = new SearchCriteria();
			criteria.addCriterion("documentSource", SearchCriterion.EQUALS, inputBean.getDocumentSource());
			criteria.addCriterion("status", SearchCriterion.EQUALS, "A");
			SortCriteria sortCriteria = new SortCriteria();
			sortCriteria.addCriterion("displayOrder");
			factory.setBeanObject(new VvDocumentTypeBean());
			result = factory.select(criteria, sortCriteria, "vv_document_type");
		}catch (Exception e) {
			log.error(e);
			throw e;
		}
		return result;
	}  //end of method

	public Collection getCylinderDocuments (CylinderDocumentManagerBean inputBean) throws Exception {
		Collection result = new Vector();
		try {
			StringBuilder query = new StringBuilder("select cd.*,fx_personnel_id_to_name(inserted_by) inserted_by_name,fx_personnel_id_to_name(last_updated_by) last_updated_by_name");
			query.append(" from cylinder_document cd where cylinder_tracking_id = ").append(inputBean.getCylinderTrackingId());
			if (!inputBean.isIncludeDeted())
				query.append(" and last_updated_by is null");
			query.append(" order by document_name");
			result = factory.selectQuery(query.toString());
		}catch (Exception e) {
			log.error(e);
			throw e;
		}
		return result;
	}  //end of method

	public String deleteDocument (Collection<CylinderDocumentManagerBean> data, PersonnelBean personnelBean) throws Exception {
		String result = "";
		Connection connection = dbManager.getConnection();
		try {
			for (CylinderDocumentManagerBean bean : data) {
				log.debug(bean.toString());
				if (bean.isDeleteRow()) {
					StringBuilder query = new StringBuilder("update cylinder_document set last_updated_date = sysdate,last_updated_by = ").append(personnelBean.getPersonnelId());
					query.append(" where cylinder_document_id = ").append(bean.getCylinderDocumentId()).append(" and cylinder_tracking_id = ").append(bean.getCylinderTrackingId());
					factory.deleteInsertUpdate(query.toString(),connection);
				}
			}
		}catch (Exception e) {
			log.error(e);
			throw e;
		}finally {
			dbManager.returnConnection(connection);
		}
		return result;
	}  //end of method

	public void addNewDocument(CylinderDocumentManagerBean bean, PersonnelBean personnelBean) throws BaseException {
		if (bean.getTheFile() != null && bean.getTheFile().length() > (long) 0) {
			try {
				//copy file to server
				File f = bean.getTheFile();
				String fileType = f.getName().substring(f.getName().lastIndexOf("."));
				ResourceLibrary library = new ResourceLibrary("tabletConfig");
				String cylinderDocDirectory = library.getString("cylinderDocDirectory");
				if (!cylinderDocDirectory.endsWith(File.separator)) {
					cylinderDocDirectory += File.separator;
				}
				ResourceLibrary library2 = new ResourceLibrary("tcmISWebResource");
				String webroot = library2.getString("serverWebRoot");
				if (!webroot.endsWith(File.separator)) {
					webroot += File.separator;
				}

				String saveDirectory = cylinderDocDirectory + new SimpleDateFormat("yyyy/MM/").format(new Date());
				File dir = new File(saveDirectory);
				if ( ! dir.exists()) {
					dir.mkdirs();
				}
				String tmpTrackingId = bean.getCylinderTrackingId().toString();
				if (bean.getCylinderTrackingId().intValue() < 10)
					tmpTrackingId = "0"+tmpTrackingId;
				File file = File.createTempFile(tmpTrackingId+"_",fileType, dir);
				FileHandler.copy(bean.getTheFile(), file);

				//insert data into database
				StringBuilder query = new StringBuilder("insert into cylinder_document (cylinder_document_id,cylinder_tracking_id,supplier,");
				query.append("document_name,document_type,date_inserted,document_date,document_url,inserted_by)");
				query.append(" values (null,").append(bean.getCylinderTrackingId()).append(",'").append(bean.getSupplier()).append("'");
				query.append(",").append(SqlHandler.delimitString(bean.getDocumentName())).append(",'").append(bean.getDocumentType()).append("'");
				query.append(",sysdate,null,'").append(FileHandler.getRelativeUrlFromFilename(saveDirectory+file.getName(),webroot)).append("',").append(personnelBean.getPersonnelId()).append(")");
				factory.deleteInsertUpdate(query.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} //end of if file uploaded
	} //end of method


} //end of class