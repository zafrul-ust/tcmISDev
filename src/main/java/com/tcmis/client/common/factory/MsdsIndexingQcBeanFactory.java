package com.tcmis.client.common.factory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.tcmis.client.common.beans.MsdsIndexingBean;
import com.tcmis.client.common.beans.MsdsIndexingQcBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.StringHandler;

public class MsdsIndexingQcBeanFactory extends BaseBeanFactory {

	public String ATTRIBUTE_MATERIAL_ID = "MATERIAL_ID";
	public String ATTRIBUTE_REVISION_DATE = "REVISION_DATE";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_INSERT_DATE = "INSERT_DATE";
	public String ATTRIBUTE_SUBMIT_DATE = "SUBMIT_DATE";
	public String ATTRIBUTE_SUBMIT_BY = "SUBMIT_BY";
	public String ATTRIBUTE_APPROVE_DATE = "APPROVE_DATE";
	public String ATTRIBUTE_APPROVE_BY = "APPROVE_BY";
	
	public String MSDS_QC_TABLE = "MSDS_QC";
	public String CO_MSDS_QC_TABLE = "COMPANY_MSDS_QC";
	public String MSDS_QC_DETAILS = "MSDS_QC_DETAIL";
	public String CO_MSDS_QC_DETAILS = "COMPANY_MSDS_QC_DETAIL";
	
	public static final String MSDS_QC_DETAIL_ALLOY = "ALLOY";
	public static final String MSDS_QC_DETAIL_AUTHOR = "AUTHOR";
	public static final String MSDS_QC_DETAIL_BOILING_POINT = "BOILING POINT";
	public static final String MSDS_QC_DETAIL_CARCINOGEN = "CARCINOGEN";
	public static final String MSDS_QC_DETAIL_CHRONIC = "CHRONIC";
	public static final String MSDS_QC_DETAIL_COMPOSITION = "COMPOSITION";
	public static final String MSDS_QC_DETAIL_CORROSIVE = "CORROSIVE";
	public static final String MSDS_QC_DETAIL_DENSITY = "DENSITY";
	public static final String MSDS_QC_DETAIL_DESCRIPTION = "DESCRIPTION";
	public static final String MSDS_QC_DETAIL_DETONABLE = "DETONABLE";
	public static final String MSDS_QC_DETAIL_EVAPORATION_RATE = "EVAPORATION RATE";
	public static final String MSDS_QC_DETAIL_FIRE = "FIRE";
	public static final String MSDS_QC_DETAIL_FLASHPOINT = "FLASHPOINT";
	public static final String MSDS_QC_DETAIL_GHS_ADDRESS = "GHS ADDRESS";
	public static final String MSDS_QC_DETAIL_HAZARD = "HAZARD";
	public static final String MSDS_QC_DETAIL_HEALTH_EFFECTS = "HEALTH EFFECTS";
	public static final String MSDS_QC_DETAIL_HMIS = "HMIS";
	public static final String MSDS_QC_DETAIL_IMAGE = "IMAGE";
	public static final String MSDS_QC_DETAIL_INCOMPATIBLE = "INCOMPATIBLE";
	public static final String MSDS_QC_DETAIL_LOCALIZED_MATERIAL_DESC = "LOCALIZED MATERIAL DESC";
	public static final String MSDS_QC_DETAIL_LOCALIZED_TRADE_NAME= "LOCALIZED TRADE NAME";
	public static final String MSDS_QC_DETAIL_MANUFACTURER = "MANUFACTURER";
	public static final String MSDS_QC_DETAIL_NFPA = "NFPA";
	public static final String MSDS_QC_DETAIL_OXIDIZER = "OXIDIZER";
	public static final String MSDS_QC_DETAIL_PH = "PH";
	public static final String MSDS_QC_DETAIL_PHYSICAL_STATE = "PHYSICAL STATE";
	public static final String MSDS_QC_DETAIL_PICTOGRAMS = "PICTOGRAMS";
	public static final String MSDS_QC_DETAIL_POLYMERIZE = "POLYMERIZE";
	public static final String MSDS_QC_DETAIL_PRECAUTIONARY= "PRECAUTIONARY";
	public static final String MSDS_QC_DETAIL_PRODUCT_CODE = "PRODUCT CODE";
	public static final String MSDS_QC_DETAIL_REVISION_DATE = "REVISION DATE";
	public static final String MSDS_QC_DETAIL_SARA = "SARA";
	public static final String MSDS_QC_DETAIL_SIGNAL_WORD = "SIGNAL WORD";
	public static final String MSDS_QC_DETAIL_SOLIDS = "SOLIDS";
	public static final String MSDS_QC_DETAIL_SPECIFIC_GRAVITY = "SPECIFIC GRAVITY";
	public static final String MSDS_QC_DETAIL_STABLE = "STABLE";
	public static final String MSDS_QC_DETAIL_TRADE_NAME = "TRADE NAME";
	public static final String MSDS_QC_DETAIL_TSCA = "TSCA";
	public static final String MSDS_QC_DETAIL_VAPOR_DENSITY = "VAPOR DENSITY";
	public static final String MSDS_QC_DETAIL_VAPOR_PRESSURE = "VAPOR PRESSURE";
	public static final String MSDS_QC_DETAIL_VOC = "VOC";
	public static final String MSDS_QC_DETAIL_VOC_LWES = "VOCLWES";
	public static final String MSDS_QC_DETAIL_WATER = "WATER";
	
	//constructor
	public MsdsIndexingQcBeanFactory(DbManager dbManager) {
		super(dbManager);
	}
	
	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if (attributeName.equals("materialId")) {
        	return ATTRIBUTE_MATERIAL_ID;
        }
		else if (attributeName.equals("revisionDate")) {
			return ATTRIBUTE_REVISION_DATE;
		}
		else if (attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}
		else if (attributeName.equals("insertDate")) {
			return ATTRIBUTE_INSERT_DATE;
		}
		else if (attributeName.equals("submitDate")) {
			return ATTRIBUTE_SUBMIT_DATE;
		}
		else if (attributeName.equals("submitBy")) {
			return ATTRIBUTE_SUBMIT_BY;
		}
		else if (attributeName.equals("approveDate")) {
			return ATTRIBUTE_APPROVE_DATE;
		}
		else if (attributeName.equals("approveBy")) {
			return ATTRIBUTE_APPROVE_BY;
		}
		
        else {
			return super.getColumnName(attributeName);
		}
	}
	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, MsdsIndexingQcBean.class);
	}
	
	public MsdsIndexingQcBean getMsdsIndexingQcBean(BigDecimal materialId, Date revisionDate, String companyId, Connection conn) throws BaseException {
		StringBuilder query = new StringBuilder("");
		query.append(" where material_id = ").append(materialId);
		query.append(" and revision_date = ").append(DateHandler.getOracleToDateFunction(revisionDate));
		if (companyId == null) {
			query.insert(0, MSDS_QC_TABLE).insert(0, "select * from ");
		}
		else {
			query.insert(0, CO_MSDS_QC_TABLE).insert(0, "select * from ");
			query.append(" and company_id = '").append(companyId).append("'");
		}
		
		DataSet dataSet = new SqlManager().select(conn, query.toString());
		Iterator dataIter = dataSet.iterator();

		MsdsIndexingQcBean msdsQcBean = null;
		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			msdsQcBean = new MsdsIndexingQcBean();
			load(dataSetRow, msdsQcBean);
			break;
		}
		
		return msdsQcBean;
	}
	
	public void updateQcDetails(MsdsIndexingBean msds, PersonnelBean user, String companyId, Connection conn) throws BaseException {
		SqlManager sqlManager = new SqlManager();
		StringBuilder deleteDetailStmt = new StringBuilder("");
		MsdsIndexingBean item = msds;
		if (StringHandler.isBlankString(companyId)) {
			deleteDetailStmt.append("delete from global.msds_qc_detail where ");
		}
		else {
			deleteDetailStmt.append("delete from customer.company_msds_qc_detail where company_id = '").append(companyId).append("' and ");
			item = msds.getCoData();
		}
		deleteDetailStmt.append("material_id = "+msds.getMaterialId()+" and revision_date = "+DateHandler.getOracleToDateFunction(msds.getRevisionDate())); 
    	
		// if this is for global, then go through the whole process to see if there are any details to save, even if no QC data yet
		if (companyId == null || item.getQcData() != null) {
			Map<String,BigDecimal> columnErrorMap = new HashMap<String,BigDecimal>();
			
			//columnErrorMap.put(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_ALLOY, item.getQcData().getAlloyQcErrorType());
			columnErrorMap.put(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_AUTHOR, item.getQcData().getMsdsAuthorQcErrorType());
			columnErrorMap.put(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_BOILING_POINT, item.getQcData().getBoilingPointQcErrorType());
			//columnErrorMap.put(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_CARCINOGEN, item.getQcData().getCarcinogenQcErrorType());
			//columnErrorMap.put(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_CHRONIC, item.getQcData().getChronicQcErrorType());
			columnErrorMap.put(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_COMPOSITION, item.getQcData().getCompositionQcErrorType());
			columnErrorMap.put(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_CORROSIVE, item.getQcData().getCorrosiveQcErrorType());
			columnErrorMap.put(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_DENSITY, item.getQcData().getDensityQcErrorType());
			columnErrorMap.put(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_DESCRIPTION, item.getQcData().getMaterialDescriptionQcErrorType());
			columnErrorMap.put(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_DETONABLE, item.getQcData().getDetonableQcErrorType());
			columnErrorMap.put(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_EVAPORATION_RATE, item.getQcData().getEvaporationRateQcErrorType());
			columnErrorMap.put(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_FIRE, item.getQcData().getFireConditionsToAvoidQcErrorType());
			columnErrorMap.put(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_FLASHPOINT, item.getQcData().getFlashPointQcErrorType());
			columnErrorMap.put(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_GHS_ADDRESS, item.getQcData().getLabelAddressQcErrorType());
			columnErrorMap.put(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_HAZARD, item.getQcData().getHazardStatementsQcErrorType());
			columnErrorMap.put(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_HEALTH_EFFECTS, item.getQcData().getHealthEffectsQcErrorType());
			columnErrorMap.put(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_HMIS, item.getQcData().getHmisQcErrorType());
			columnErrorMap.put(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_IMAGE, item.getQcData().getContentQcErrorType());
			columnErrorMap.put(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_INCOMPATIBLE, item.getQcData().getIncompatibleQcErrorType());
			columnErrorMap.put(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_LOCALIZED_MATERIAL_DESC, item.getQcData().getLocalizedMaterialDescQcErrorType());
			columnErrorMap.put(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_LOCALIZED_TRADE_NAME, item.getQcData().getLocalizedTradeNameQcErrorType());
			columnErrorMap.put(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_MANUFACTURER, item.getQcData().getManufacturerQcErrorType());
			columnErrorMap.put(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_NFPA, item.getQcData().getNfpaQcErrorType());
			columnErrorMap.put(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_OXIDIZER, item.getQcData().getOxidizerQcErrorType());
			columnErrorMap.put(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_PH, item.getQcData().getPhQcErrorType());
			columnErrorMap.put(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_PHYSICAL_STATE, item.getQcData().getPhysicalStateQcErrorType());
			columnErrorMap.put(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_PICTOGRAMS, item.getQcData().getGhsPictogramsQcErrorType());
			columnErrorMap.put(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_POLYMERIZE, item.getQcData().getPolymerizeQcErrorType());
			columnErrorMap.put(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_PRECAUTIONARY, item.getQcData().getPrecautionaryStatementsQcErrorType());
			columnErrorMap.put(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_PRODUCT_CODE, item.getQcData().getProductCodeQcErrorType());
			columnErrorMap.put(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_REVISION_DATE, item.getQcData().getRevisionDateQcErrorType());
			columnErrorMap.put(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_SARA, item.getQcData().getSaraQcErrorType());
			columnErrorMap.put(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_SIGNAL_WORD, item.getQcData().getSignalWordQcErrorType());
			columnErrorMap.put(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_SOLIDS, item.getQcData().getSolidsQcErrorType());
			columnErrorMap.put(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_SPECIFIC_GRAVITY, item.getQcData().getSpecificGravityQcErrorType());
			columnErrorMap.put(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_STABLE, item.getQcData().getStableQcErrorType());
			columnErrorMap.put(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_TRADE_NAME, item.getQcData().getTradeNameQcErrorType());
			//columnErrorMap.put(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_TSCA, item.getQcData().getTscaQcErrorType());
			columnErrorMap.put(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_VAPOR_DENSITY, item.getQcData().getVaporDensityQcErrorType());
			columnErrorMap.put(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_VAPOR_PRESSURE, item.getQcData().getVaporPressureQcErrorType());
			columnErrorMap.put(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_VOC, item.getQcData().getVocQcErrorType());
			columnErrorMap.put(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_VOC_LWES, item.getQcData().getVocLessH2oExemptQcErrorType());
			columnErrorMap.put(MsdsIndexingQcBeanFactory.MSDS_QC_DETAIL_WATER, item.getQcData().getWaterReactiveQcErrorType());
			
			boolean insertDetails = false;
			StringBuilder insertDetailStmt = new StringBuilder("insert all ");
			if (StringHandler.isBlankString(companyId)) {
				for (Iterator<String> it = columnErrorMap.keySet().iterator();it.hasNext();) {
					String columnName = it.next();
					BigDecimal errorType = columnErrorMap.get(columnName);
					if (errorType != null) {
						insertDetailStmt.append("into global.msds_qc_detail (material_id, revision_date, column_name, qc_error_type_id, review_date, review_by) ");
						insertDetailStmt.append("values (").append(msds.getMaterialId()).append(",");
						insertDetailStmt.append(DateHandler.getOracleToDateFunction(msds.getRevisionDate())).append(",");
						insertDetailStmt.append("'").append(columnName).append("',");
						insertDetailStmt.append(errorType).append(",");
						insertDetailStmt.append("sysdate,");
						insertDetailStmt.append(user.getPersonnelIdBigDecimal()).append(") ");
						insertDetails = true;
					}
				}
			}
			else {
				for (Iterator<String> it = columnErrorMap.keySet().iterator();it.hasNext();) {
					String columnName = it.next();
					BigDecimal errorType = columnErrorMap.get(columnName);
					if (errorType != null) {
						insertDetailStmt.append("into customer.company_msds_qc_detail (material_id, revision_date, company_id, column_name, qc_error_type_id, review_date, review_by) ");
						insertDetailStmt.append("values (").append(msds.getMaterialId()).append(",");
						insertDetailStmt.append(DateHandler.getOracleToDateFunction(msds.getRevisionDate())).append(",");
						insertDetailStmt.append("'").append(companyId).append("', ");
						insertDetailStmt.append("'").append(columnName).append("',");
						insertDetailStmt.append(errorType).append(",");
						insertDetailStmt.append("sysdate,");
						insertDetailStmt.append(user.getPersonnelIdBigDecimal()).append(") ");
						insertDetails = true;
					}
				}
			}
			insertDetailStmt.append("select * from dual");
			
			sqlManager.update(conn, deleteDetailStmt.toString());
			if (insertDetails) {
				// if there is no global QC record, insert one before attempting to insert into the details table
				if (StringHandler.isBlankString(companyId)) {
					StringBuilder query = new StringBuilder("select * from global.msds_qc");
					query.append(" where material_id = ").append(msds.getMaterialId());
					query.append(" and revision_date = ").append(DateHandler.getOracleToDateFunction(msds.getRevisionDate()));
					
					DataSet dataSet = new SqlManager().select(conn, query.toString());
					Iterator dataIter = dataSet.iterator();

					if ( ! dataIter.hasNext()) {
						StringBuilder insertStmt = new StringBuilder("insert into global.msds_qc (material_id, revision_date, insert_date, submit_date, submit_by, source) ");
						insertStmt.append("values(").append(msds.getMaterialId()).append(", ");
						insertStmt.append(DateHandler.getOracleToDateFunction(msds.getRevisionDate())).append(",");
						insertStmt.append("sysdate,");
						insertStmt.append("sysdate,");
						insertStmt.append("-1,");
						insertStmt.append("'Customer QC')");
						
						sqlManager.update(conn, insertStmt.toString());
					}
				}
				
				sqlManager.update(conn, insertDetailStmt.toString());
			}
		}
	}
}
