package com.tcmis.internal.catalog.factory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.internal.catalog.beans.MsdsBean;

/******************************************************************************
 * CLASSNAME: MsdsBeanFactory <br>
 * @version: 1.0, Dec 10, 2010 <br>
 *****************************************************************************/

public class MsdsBeanFactory extends BaseBeanFactory {
	public String ATTRIBUTE_MATERIAL_ID = "MATERIAL_ID";
	public String ATTRIBUTE_REVISION_DATE = "REVISION_DATE";
	public String ATTRIBUTE_CONTENT = "CONTENT";
	public String ATTRIBUTE_EMERGENCY_PHONE = "EMERGENCY_PHONE";
	public String ATTRIBUTE_SPECIFIC_GRAVITY = "SPECIFIC_GRAVITY";
	public String ATTRIBUTE_HEALTH = "HEALTH";
	public String ATTRIBUTE_FLAMMABILITY = "FLAMMABILITY";
	public String ATTRIBUTE_REACTIVITY = "REACTIVITY";
	public String ATTRIBUTE_SPECIFIC_HAZARD = "SPECIFIC_HAZARD";
	public String ATTRIBUTE_COMPATIBILITY = "COMPATIBILITY";
	public String ATTRIBUTE_STORAGE_TEMP = "STORAGE_TEMP";
	public String ATTRIBUTE_PPE = "PPE";
	public String ATTRIBUTE_SIGNAL_WORD = "SIGNAL_WORD";
	public String ATTRIBUTE_TARGET_ORGAN = "TARGET_ORGAN";
	public String ATTRIBUTE_ROUTE_OF_ENTRY = "ROUTE_OF_ENTRY";
	public String ATTRIBUTE_SKIN = "SKIN";
	public String ATTRIBUTE_EYES = "EYES";
	public String ATTRIBUTE_INHALATION = "INHALATION";
	public String ATTRIBUTE_INJECTION = "INJECTION";
	public String ATTRIBUTE_BOILING_POINT = "BOILING_POINT";
	public String ATTRIBUTE_FLASH_POINT = "FLASH_POINT";
	public String ATTRIBUTE_PH = "PH";
	public String ATTRIBUTE_FREEZING_POINT = "FREEZING_POINT";
	public String ATTRIBUTE_DENSITY = "DENSITY";
	public String ATTRIBUTE_INGESTION = "INGESTION";
	public String ATTRIBUTE_ON_LINE = "ON_LINE";
	public String ATTRIBUTE_DENSITY_UNIT = "DENSITY_UNIT";
	public String ATTRIBUTE_PHYSICAL_STATE = "PHYSICAL_STATE";
	public String ATTRIBUTE_VOC_UNIT = "VOC_UNIT";
	public String ATTRIBUTE_TSCA_12B = "TSCA_12B";
	public String ATTRIBUTE_SARA_311_312_ACUTE = "SARA_311_312_ACUTE";
	public String ATTRIBUTE_SARA_311_312_CHRONIC = "SARA_311_312_CHRONIC";
	public String ATTRIBUTE_SARA_311_312_FIRE = "SARA_311_312_FIRE";
	public String ATTRIBUTE_SARA_311_312_PRESSURE = "SARA_311_312_PRESSURE";
	public String ATTRIBUTE_SARA_311_312_REACTIVITY = "SARA_311_312_REACTIVITY";
	public String ATTRIBUTE_OSHA_HAZARD = "OSHA_HAZARD";
	public String ATTRIBUTE_TSCA_LIST = "TSCA_LIST";
	public String ATTRIBUTE_MIXTURE = "MIXTURE";
	public String ATTRIBUTE_VOC = "VOC";
	public String ATTRIBUTE_VOC_LOWER = "VOC_LOWER";
	public String ATTRIBUTE_VOC_UPPER = "VOC_UPPER";
	public String ATTRIBUTE_REVIEWED_BY = "REVIEWED_BY";
	public String ATTRIBUTE_REVIEW_DATE = "REVIEW_DATE";
	public String ATTRIBUTE_REMARK = "REMARK";
	public String ATTRIBUTE_FLASH_POINT_UNIT = "FLASH_POINT_UNIT";
	public String ATTRIBUTE_SOLIDS = "SOLIDS";
	public String ATTRIBUTE_SOLIDS_LOWER = "SOLIDS_LOWER";
	public String ATTRIBUTE_SOLIDS_UPPER = "SOLIDS_UPPER";
	public String ATTRIBUTE_SOLIDS_UNIT = "SOLIDS_UNIT";
	public String ATTRIBUTE_SOLIDS_PERCENT = "SOLIDS_PERCENT";
	public String ATTRIBUTE_SOLIDS_LOWER_PERCENT = "SOLIDS_LOWER_PERCENT";
	public String ATTRIBUTE_SOLIDS_UPPER_PERCENT = "SOLIDS_UPPER_PERCENT";
	public String ATTRIBUTE_VOC_LOWER_PERCENT = "VOC_LOWER_PERCENT";
	public String ATTRIBUTE_VOC_UPPER_PERCENT = "VOC_UPPER_PERCENT";
	public String ATTRIBUTE_VOC_PERCENT = "VOC_PERCENT";
	public String ATTRIBUTE_HMIS_HEALTH = "HMIS_HEALTH";
	public String ATTRIBUTE_HMIS_FLAMMABILITY = "HMIS_FLAMMABILITY";
	public String ATTRIBUTE_HMIS_REACTIVITY = "HMIS_REACTIVITY";
	public String ATTRIBUTE_HMIS_CHRONIC = "HMIS_CHRONIC";
	public String ATTRIBUTE_HMIS_SOURCE = "HMIS_SOURCE";
	public String ATTRIBUTE_PERSONAL_PROTECTION = "PERSONAL_PROTECTION";
	public String ATTRIBUTE_ALT_DATA_SOURCE = "ALT_DATA_SOURCE";
	public String ATTRIBUTE_VAPOR_PRESSURE_DETECT = "VAPOR_PRESSURE_DETECT";
	public String ATTRIBUTE_VAPOR_PRESSURE = "VAPOR_PRESSURE";
	public String ATTRIBUTE_VAPOR_PRESSURE_UNIT = "VAPOR_PRESSURE_UNIT";
	public String ATTRIBUTE_VAPOR_PRESSURE_TEMP = "VAPOR_PRESSURE_TEMP";
	public String ATTRIBUTE_VAPOR_PRESSURE_TEMP_UNIT = "VAPOR_PRESSURE_TEMP_UNIT";
	public String ATTRIBUTE_INSERT_DATE = "INSERT_DATE";
	public String ATTRIBUTE_COMPOSITION_CHANGED = "COMPOSITION_CHANGED";
	public String ATTRIBUTE_VOC_LESS_H2O_EXEMPT = "VOC_LESS_H2O_EXEMPT";
	public String ATTRIBUTE_VOC_LESS_H2O_EXEMPT_UNIT = "VOC_LESS_H2O_EXEMPT_UNIT";
	public String ATTRIBUTE_VOC_LB_PER_SOLID_LB = "VOC_LB_PER_SOLID_LB";
	public String ATTRIBUTE_VOC_LESS_H2O_EXEMPT_LOWER = "VOC_LESS_H2O_EXEMPT_LOWER";
	public String ATTRIBUTE_VOC_LESS_H2O_EXEMPT_UPPER = "VOC_LESS_H2O_EXEMPT_UPPER";
	public String ATTRIBUTE_VOC_SOURCE = "VOC_SOURCE";
	public String ATTRIBUTE_VOC_LESS_H2O_EXEMPT_SOURCE = "VOC_LESS_H2O_EXEMPT_SOURCE";
	public String ATTRIBUTE_VOC_LB_PER_SOLID_LB_SOURCE = "VOC_LB_PER_SOLID_LB_SOURCE";
	public String ATTRIBUTE_SOLIDS_SOURCE = "SOLIDS_SOURCE";
	public String ATTRIBUTE_VAPOR_PRESSURE_SOURCE = "VAPOR_PRESSURE_SOURCE";
	public String ATTRIBUTE_VOC_LESS_H20_EXEMPT_SOURCE = "VOC_LESS_H20_EXEMPT_SOURCE";
	public String ATTRIBUTE_VOC_LB_PER_SOLID_LB_LOWER = "VOC_LB_PER_SOLID_LB_LOWER";
	public String ATTRIBUTE_VOC_LB_PER_SOLID_LB_UPPER = "VOC_LB_PER_SOLID_LB_UPPER";
	public String ATTRIBUTE_VOC_LB_PER_SOLID_LB_LOW_CALC = "VOC_LB_PER_SOLID_LB_LOW_CALC";
	public String ATTRIBUTE_VOC_LB_PER_SOLID_LB_CALC = "VOC_LB_PER_SOLID_LB_CALC";
	public String ATTRIBUTE_VOC_LB_PER_SOLID_LB_UP_CALC = "VOC_LB_PER_SOLID_LB_UP_CALC";
	public String ATTRIBUTE_VAPOR_PRESSURE_LOWER = "VAPOR_PRESSURE_LOWER";
	public String ATTRIBUTE_VAPOR_PRESSURE_UPPER = "VAPOR_PRESSURE_UPPER";
	public String ATTRIBUTE_VOC_LESS_H2O_EXEMPT_LOWER_CALC = "VOC_LESS_H2O_EXEMPT_LOWER_CALC";
	public String ATTRIBUTE_VOC_LESS_H2O_EXEMPT_CALC = "VOC_LESS_H2O_EXEMPT_CALC";
	public String ATTRIBUTE_VOC_LESS_H2O_EXEMPT_UPPER_CALC = "VOC_LESS_H2O_EXEMPT_UPPER_CALC";
	public String ATTRIBUTE_VOC_LESS_H2O_EXEMPT_UNIT_CALC = "VOC_LESS_H2O_EXEMPT_UNIT_CALC";
	public String ATTRIBUTE_FRENCH_CONTENT = "FRENCH_CONTENT";
	public String ATTRIBUTE_VOC_COMP_VAPOR_PRESSURE_MMHG = "VOC_COMP_VAPOR_PRESSURE_MMHG";
	public String ATTRIBUTE_CHRONIC = "CHRONIC";
	public String ATTRIBUTE_LFC_CODE = "LFC_CODE";
	public String ATTRIBUTE_POLYMERIZE = "POLYMERIZE";
	public String ATTRIBUTE_INCOMPATIBLE = "INCOMPATIBLE";
	public String ATTRIBUTE_CORROSIVE = "CORROSIVE";
	public String ATTRIBUTE_HEALTH_EFFECTS = "HEALTH_EFFECTS";
	public String ATTRIBUTE_STABLE = "STABLE";
	public String ATTRIBUTE_VAPOR_DENSITY = "VAPOR_DENSITY";
	public String ATTRIBUTE_EVAPORATION_RATE = "EVAPORATION_RATE";
	public String ATTRIBUTE_FIRE_CONDITIONS_TO_AVOID = "FIRE_CONDITIONS_TO_AVOID";
	public String ATTRIBUTE_SPECIFIC_GRAVITY_BASIS	= "SPECIFIC_GRAVITY_BASIS";
	public String ATTRIBUTE_SPECIFIC_GRAVITY_DETECT	= "SPECIFIC_GRAVITY_DETECT";
	public String ATTRIBUTE_SPECIFIC_GRAVITY_LOWER	= "SPECIFIC_GRAVITY_LOWER";
	public String ATTRIBUTE_SPECIFIC_GRAVITY_UPPER = "SPECIFIC_GRAVITY_UPPER";
	public String ATTRIBUTE_DENSITY_DETECT = "DENSITY_DETECT";
	public String ATTRIBUTE_DENSITY_UPPER = "DENSITY_UPPER";
	public String ATTRIBUTE_DENSITY_SOURCE = "DENSITY_SOURCE";
	public String ATTRIBUTE_FLASH_POINT_DETECT	= "FLASH_POINT_DETECT";
	public String ATTRIBUTE_FLASH_POINT_SOURCE= "FLASH_POINT_SOURCE";
	public String ATTRIBUTE_FLASH_POINT_LOWER = "FLASH_POINT_LOWER";
	public String ATTRIBUTE_FLASH_POINT_METHOD	= "FLASH_POINT_METHOD";
	public String ATTRIBUTE_FLASH_POINT_UPPER = "FLASH_POINT_UPPER";
	public String ATTRIBUTE_BOILING_POINT_DETECT = "BOILING_POINT_DETECT";
	public String ATTRIBUTE_BOILING_POINT_SOURCE = "BOILING_POINT_SOURCE"; 
	public String ATTRIBUTE_BOILING_POINT_UPPER = "BOILING_POINT_UPPER"; 
	public String ATTRIBUTE_BOILING_POINT_UNIT = "BOILING_POINT_UNIT";
	public String ATTRIBUTE_PH_DETAIL = "PH_DETAIL";
	public String ATTRIBUTE_PH_SOURCE = "PH_SOURCE";
	public String ATTRIBUTE_PH_DETECT = "PH_DETECT"; 
	public String ATTRIBUTE_PH_UPPER = "PH_UPPER";
	public String ATTRIBUTE_WATER_REACTIVE = "WATER_REACTIVE";
	public String ATTRIBUTE_OXIDIZER = "OXIDIZER";
	public String ATTRIBUTE_CARCINOGEN = "CARCINOGEN";
	public String ATTRIBUTE_PRODUCT_CODE = "PRODUCT_CODE";
	public String ATTRIBUTE_BOILING_POINT_LOWER = "BOILING_POINT_LOWER";
	public String ATTRIBUTE_BOILING_POINT_DETAIL = "BOILING_POINT_DETAIL";
	public String ATTRIBUTE_SPECIFIC_GRAVITY_SOURCE = "SPECIFIC_GRAVITY_SOURCE";
	public String ATTRIBUTE_NFPA_SOURCE = "NFPA_SOURCE";	
	public String ATTRIBUTE_ALLOY = "ALLOY";
    public String ATTRIBUTE_MSDS_INDEXING_PRIORITY_ID = "MSDS_INDEXING_PRIORITY_ID";
    public String ATTRIBUTE_LABEL_COMPANY_NAME = "LABEL_COMPANY_NAME";
    public String ATTRIBUTE_LABEL_ADDRESS_LINE_1 = "LABEL_ADDRESS_LINE_1";
    public String ATTRIBUTE_LABEL_ADDRESS_LINE_2 = "LABEL_ADDRESS_LINE_2";
    public String ATTRIBUTE_LABEL_ADDRESS_LINE_3 = "LABEL_ADDRESS_LINE_3";
    public String ATTRIBUTE_LABEL_ADDRESS_LINE_4 = "LABEL_ADDRESS_LINE_4";
    public String ATTRIBUTE_LABEL_CITY = "LABEL_CITY";
    public String ATTRIBUTE_LABEL_STATE_ABBREV = "LABEL_STATE_ABBREV";
    public String ATTRIBUTE_LABEL_COUNTRY_ABBREV = "LABEL_COUNTRY_ABBREV";
    public String ATTRIBUTE_LABEL_ZIP = "LABEL_ZIP";
    public String ATTRIBUTE_LABEL_PHONE = "LABEL_PHONE";
    public String ATTRIBUTE_MSDS_ID = "MSDS_ID";
    public String ATTRIBUTE_GHS_COMPLIANT_IMAGE = "GHS_COMPLIANT_IMAGE"; 

    public String ATTRIBUTE_LOCKHEED_MSDS = "LOCKHEED_MSDS";
	public String ATTRIBUTE_CO_BOILING_POINT_DETAIL = "LM_BOILING_POINT_DETAIL";
	public String ATTRIBUTE_CO_ALLOY = "LM_ALLOY";
	public String ATTRIBUTE_CO_ALT_DATA_SOURCE = "LM_ALT_DATA_SOURCE";
	public String ATTRIBUTE_CO_BOILING_POINT = "LM_BOILING_POINT";
	public String ATTRIBUTE_CO_BOILING_POINT_LOWER = "LM_BOILING_POINT_LOWER";
	public String ATTRIBUTE_CO_BOILING_POINT_DETECT = "LM_BOILING_POINT_DETECT";
	public String ATTRIBUTE_CO_BOILING_POINT_SOURCE = "LM_BOILING_POINT_SOURCE";
	public String ATTRIBUTE_CO_BOILING_POINT_UNIT = "LM_BOILING_POINT_UNIT";
	public String ATTRIBUTE_CO_BOILING_POINT_UPPER = "LM_BOILING_POINT_UPPER";
	public String ATTRIBUTE_CO_CARCINOGEN = "LM_CARCINOGEN";
	public String ATTRIBUTE_CO_CHRONIC = "LM_CHRONIC";
	public String ATTRIBUTE_CO_COMPATIBILITY = "LM_COMPATIBILITY";
	public String ATTRIBUTE_CO_COMPOSITION_CHANGED = "LM_COMPOSITION_CHANGED";
	public String ATTRIBUTE_CO_CONTENT = "LM_CONTENT";
	public String ATTRIBUTE_CO_CORROSIVE = "LM_CORROSIVE";
	public String ATTRIBUTE_CO_DENSITY = "LM_DENSITY";
	public String ATTRIBUTE_CO_DENSITY_DETECT = "LM_DENSITY_DETECT";
	public String ATTRIBUTE_CO_DENSITY_UNIT = "LM_DENSITY_UNIT";
	public String ATTRIBUTE_CO_DENSITY_UPPER = "LM_DENSITY_UPPER";
	public String ATTRIBUTE_CO_EMERGENCY_PHONE = "LM_EMERGENCY_PHONE";
	public String ATTRIBUTE_CO_EVAPORATION_RATE = "LM_EVAPORATION_RATE";
	public String ATTRIBUTE_CO_EYES = "LM_EYES";
	public String ATTRIBUTE_CO_FIRE_CONDITIONS_TO_AVOID = "LM_FIRE_CONDITIONS_TO_AVOID";
	public String ATTRIBUTE_CO_FLAMMABILITY = "LM_FLAMMABILITY";
	public String ATTRIBUTE_CO_FLASH_POINT = "LM_FLASH_POINT";
	public String ATTRIBUTE_CO_FLASH_POINT_DETECT = "LM_FLASH_POINT_DETECT";
	public String ATTRIBUTE_CO_FLASH_POINT_LOWER = "LM_FLASH_POINT_LOWER";
	public String ATTRIBUTE_CO_FLASH_POINT_METHOD = "LM_FLASH_POINT_METHOD";
	public String ATTRIBUTE_CO_FLASH_POINT_SOURCE = "LM_FLASH_POINT_SOURCE";
	public String ATTRIBUTE_CO_FLASH_POINT_UNIT = "LM_FLASH_POINT_UNIT";
	public String ATTRIBUTE_CO_FLASH_POINT_UPPER = "LM_FLASH_POINT_UPPER";
	public String ATTRIBUTE_CO_FREEZING_POINT = "LM_FREEZING_POINT";
	public String ATTRIBUTE_CO_FRENCH_CONTENT = "LM_FRENCH_CONTENT";
	public String ATTRIBUTE_CO_HEALTH = "LM_HEALTH";
	public String ATTRIBUTE_CO_HEALTH_EFFECTS = "LM_HEALTH_EFFECTS";
	public String ATTRIBUTE_CO_HMIS_CHRONIC = "LM_HMIS_CHRONIC";
	public String ATTRIBUTE_CO_HMIS_FLAMMABILITY = "LM_HMIS_FLAMMABILITY";
	public String ATTRIBUTE_CO_HMIS_HEALTH = "LM_HMIS_HEALTH";
	public String ATTRIBUTE_CO_HMIS_REACTIVITY = "LM_HMIS_REACTIVITY";
	public String ATTRIBUTE_CO_HMIS_SOURCE = "LM_HMIS_SOURCE";
	public String ATTRIBUTE_CO_INCOMPATIBLE = "LM_INCOMPATIBLE";
	public String ATTRIBUTE_CO_INGESTION = "LM_INGESTION";
	public String ATTRIBUTE_CO_INHALATION = "LM_INHALATION";
	public String ATTRIBUTE_CO_INJECTION = "LM_INJECTION";
	public String ATTRIBUTE_CO_INSERT_DATE = "LM_INSERT_DATE";
	public String ATTRIBUTE_CO_LFC_CODE = "LM_LFC_CODE";
	public String ATTRIBUTE_CO_MATERIAL_ID = "LM_MATERIAL_ID";
	public String ATTRIBUTE_CO_MIXTURE = "LM_MIXTURE";
	public String ATTRIBUTE_CO_NFPA_SOURCE = "LM_NFPA_SOURCE";
	public String ATTRIBUTE_CO_ON_LINE = "LM_ON_LINE";
	public String ATTRIBUTE_CO_OSHA_HAZARD = "LM_OSHA_HAZARD";
	public String ATTRIBUTE_CO_OXIDIZER = "LM_OXIDIZER";
	public String ATTRIBUTE_CO_PERSONAL_PROTECTION = "LM_PERSONAL_PROTECTION";
	public String ATTRIBUTE_CO_PH = "LM_PH";
	public String ATTRIBUTE_CO_PHYSICAL_STATE = "LM_PHYSICAL_STATE";
	public String ATTRIBUTE_CO_PH_DETAIL = "LM_PH_DETAIL";
	public String ATTRIBUTE_CO_PH_DETECT = "LM_PH_DETECT";
	public String ATTRIBUTE_CO_PH_SOURCE = "LM_PH_SOURCE";
	public String ATTRIBUTE_CO_PH_UPPER = "LM_PH_UPPER";
	public String ATTRIBUTE_CO_POLYMERIZE = "LM_POLYMERIZE";
	public String ATTRIBUTE_CO_PPE = "LM_PPE";
	public String ATTRIBUTE_CO_REACTIVITY = "LM_REACTIVITY";
	public String ATTRIBUTE_CO_REMARK = "LM_REMARK";
	public String ATTRIBUTE_CO_REVIEWED_BY = "LM_REVIEWED_BY";
	public String ATTRIBUTE_CO_REVIEW_DATE = "LM_REVIEW_DATE";
	public String ATTRIBUTE_CO_ROUTE_OF_ENTRY = "LM_ROUTE_OF_ENTRY";
	public String ATTRIBUTE_CO_SARA_311_312_ACUTE = "LM_SARA_311_312_ACUTE";
	public String ATTRIBUTE_CO_SARA_311_312_CHRONIC = "LM_SARA_311_312_CHRONIC";
	public String ATTRIBUTE_CO_SARA_311_312_FIRE = "LM_SARA_311_312_FIRE";
	public String ATTRIBUTE_CO_SARA_311_312_PRESSURE = "LM_SARA_311_312_PRESSURE";
	public String ATTRIBUTE_CO_SARA_311_312_REACTIVITY = "LM_SARA_311_312_REACTIVITY";
	public String ATTRIBUTE_CO_SG_DENSITY_SOURCE = "LM_SG_DENSITY_SOURCE";
	public String ATTRIBUTE_CO_SIGNAL_WORD = "LM_SIGNAL_WORD";
	public String ATTRIBUTE_CO_SKIN = "LM_SKIN";
	public String ATTRIBUTE_CO_SOLIDS = "LM_SOLIDS";
	public String ATTRIBUTE_CO_SOLIDS_LOWER = "LM_SOLIDS_LOWER";
	public String ATTRIBUTE_CO_SOLIDS_LOWER_PERCENT = "LM_SOLIDS_LOWER_PERCENT";
	public String ATTRIBUTE_CO_SOLIDS_PERCENT = "LM_SOLIDS_PERCENT";
	public String ATTRIBUTE_CO_SOLIDS_SOURCE = "LM_SOLIDS_SOURCE";
	public String ATTRIBUTE_CO_SOLIDS_UNIT = "LM_SOLIDS_UNIT";
	public String ATTRIBUTE_CO_SOLIDS_UPPER = "LM_SOLIDS_UPPER";
	public String ATTRIBUTE_CO_SOLIDS_UPPER_PERCENT = "LM_SOLIDS_UPPER_PERCENT";
	public String ATTRIBUTE_CO_SPECIFIC_GRAVITY = "LM_SPECIFIC_GRAVITY";
	public String ATTRIBUTE_CO_SPECIFIC_GRAVITY_BASIS = "LM_SPECIFIC_GRAVITY_BASIS";
	public String ATTRIBUTE_CO_SPECIFIC_GRAVITY_DETECT = "LM_SPECIFIC_GRAVITY_DETECT";
	public String ATTRIBUTE_CO_SPECIFIC_GRAVITY_LOWER = "LM_SPECIFIC_GRAVITY_LOWER";
	public String ATTRIBUTE_CO_SPECIFIC_GRAVITY_UPPER = "LM_SPECIFIC_GRAVITY_UPPER";
	public String ATTRIBUTE_CO_SPECIFIC_HAZARD = "LM_SPECIFIC_HAZARD";
	public String ATTRIBUTE_CO_STABLE = "LM_STABLE";
	public String ATTRIBUTE_CO_STORAGE_TEMP = "LM_STORAGE_TEMP";
	public String ATTRIBUTE_CO_TARGET_ORGAN = "LM_TARGET_ORGAN";
	public String ATTRIBUTE_CO_TSCA_12B = "LM_TSCA_12B";
	public String ATTRIBUTE_CO_TSCA_LIST = "LM_TSCA_LIST";
	public String ATTRIBUTE_CO_VAPOR_DENSITY = "LM_VAPOR_DENSITY";
	public String ATTRIBUTE_CO_VAPOR_PRESSURE = "LM_VAPOR_PRESSURE";
	public String ATTRIBUTE_CO_VAPOR_PRESSURE_DETECT = "LM_VAPOR_PRESSURE_DETECT";
	public String ATTRIBUTE_CO_VAPOR_PRESSURE_LOWER = "LM_VAPOR_PRESSURE_LOWER";
	public String ATTRIBUTE_CO_VAPOR_PRESSURE_SOURCE = "LM_VAPOR_PRESSURE_SOURCE";
	public String ATTRIBUTE_CO_VAPOR_PRESSURE_TEMP = "LM_VAPOR_PRESSURE_TEMP";
	public String ATTRIBUTE_CO_VAPOR_PRESSURE_TEMP_UNIT = "LM_VAPOR_PRESSURE_TEMP_UNIT";
	public String ATTRIBUTE_CO_VAPOR_PRESSURE_UNIT = "LM_VAPOR_PRESSURE_UNIT";
	public String ATTRIBUTE_CO_VAPOR_PRESSURE_UPPER = "LM_VAPOR_PRESSURE_UPPER";
	public String ATTRIBUTE_CO_VOC = "LM_VOC";
	public String ATTRIBUTE_CO_VOC_LB_PER_SOLID_LB = "LM_VOC_LB_PER_SOLID_LB";
	public String ATTRIBUTE_CO_VOC_LB_PER_SOLID_LB_LOWER = "LM_VOC_LB_PER_SOLID_LB_LOWER";
	public String ATTRIBUTE_CO_VOC_LB_PER_SOLID_LB_SOURCE = "LM_VOC_LB_PER_SOLID_LB_SOURCE";
	public String ATTRIBUTE_CO_VOC_LB_PER_SOLID_LB_UPPER = "LM_VOC_LB_PER_SOLID_LB_UPPER";
	public String ATTRIBUTE_CO_VOC_LESS_H2O_EXEMPT_SOURCE = "LM_VOC_LESS_H20_EXEMPT_SOURCE";
	public String ATTRIBUTE_CO_VOC_LESS_H2O_EXEMPT = "LM_VOC_LESS_H2O_EXEMPT";
	public String ATTRIBUTE_CO_VOC_LESS_H2O_EXEMPT_LOWER = "LM_VOC_LESS_H2O_EXEMPT_LOWER";
	public String ATTRIBUTE_CO_VOC_LESS_H2O_EXEMPT_UNIT = "LM_VOC_LESS_H2O_EXEMPT_UNIT";
	public String ATTRIBUTE_CO_VOC_LESS_H2O_EXEMPT_UPPER = "LM_VOC_LESS_H2O_EXEMPT_UPPER";
	public String ATTRIBUTE_CO_VOC_LOWER = "LM_VOC_LOWER";
	public String ATTRIBUTE_CO_VOC_LOWER_PERCENT = "LM_VOC_LOWER_PERCENT";
	public String ATTRIBUTE_CO_VOC_PERCENT = "LM_VOC_PERCENT";
	public String ATTRIBUTE_CO_VOC_SOURCE = "LM_VOC_SOURCE";
	public String ATTRIBUTE_CO_VOC_UNIT = "LM_VOC_UNIT";
	public String ATTRIBUTE_CO_VOC_UPPER = "LM_VOC_UPPER";
	public String ATTRIBUTE_CO_VOC_UPPER_PERCENT = "LM_VOC_UPPER_PERCENT";
	public String ATTRIBUTE_CO_WATER_REACTIVE = "LM_WATER_REACTIVE";
	public String ATTRIBUTE_CO_SPECIFIC_GRAVITY_SOURCE = "LM_SPECIFIC_GRAVITY_SOURCE";
	public String ATTRIBUTE_CO_DENSITY_SOURCE = "LM_DENSITY_SOURCE";
    public String ATTRIBUTE_VERIFIED_BY = "VERIFIED_BY";
    public String ATTRIBUTE_VERIFIED_BY_NAME = "VERIFIED_BY_NAME";
    public String ATTRIBUTE_VERIFIED_ON = "VERIFIED_ON";
    public String ATTRIBUTE_ID_ONLY = "ID_ONLY";
    public String ATTRIBUTE_MSDS_AUTHOR_ID = "MSDS_AUTHOR_ID";
    public String ATTRIBUTE_OZONE_DEPLETING_COMPOUND = "OZONE_DEPLETING_COMPOUND";
    public String ATTRIBUTE_LOW_VOLUME_EXEMPT = "LOW_VOLUME_EXEMPT";
    public String ATTRIBUTE_DATA_ENTRY_COMPLETE = "DATA_ENTRY_COMPLETE";
    public String ATTRIBUTE_CO_DATA_ENTRY_COMPLETE = "CO_DATA_ENTRY_COMPLETE";
    //public String ATTRIBUTE_COMPANY_MSDS = "COMPANY_MSDS";
    public String ATTRIBUTE_DETONABLE = "DETONABLE";
    public String ATTRIBUTE_MISCIBLE = "MISCIBLE";
    public String ATTRIBUTE_PYROPHORIC = "PYROPHORIC";
    public String ATTRIBUTE_SPONTANEOUSLY_COMBUSTIBLE = "SPONTANEOUSLY_COMBUSTIBLE";
    public String ATTRIBUTE_TSCA_STATEMENT = "TSCA_STATEMENT";
    public String ATTRIBUTE_PHYSICAL_STATE_SOURCE = "PHYSICAL_STATE_SOURCE";
    public String ATTRIBUTE_AS_MIXED = "AS_MIXED";
    public String ATTRIBUTE_GHS_HAZARD = "GHS_HAZARD";

    //table name
	public String TABLE = "GLOBAL.MSDS";
	public String TABLE_CO = "customer.company_msds";
    public String TABLE_LOCALE = "GLOBAL.MSDS_LOCALE";
    public String TABLE_MAT = "material";
    public String TABLE_SUG = "msds_admin.maxcom_sug";


    Log log = LogFactory.getLog(getClass());


	//constructor
	public MsdsBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	public int delete(SearchCriteria criteria) throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = delete(criteria, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int delete(SearchCriteria criteria, Connection conn) throws BaseException {
		return new SqlManager().update(conn, " delete from " + TABLE + " " + getWhereClause(criteria));
	}

	//delete
	public int delete(MsdsBean msdsBean) throws BaseException {
		return delete(getKeyCriteria(msdsBean));
	}

	public int delete(MsdsBean msdsBean, Connection conn) throws BaseException {
		return delete(getKeyCriteria(msdsBean), conn);
	}

	private SearchCriteria getKeyCriteria(MsdsBean msdsBean) {
		return getKeyCriteria(msdsBean.getMaterialId(), msdsBean.getRevisionDate());
	}

	private SearchCriteria getKeyCriteria(BigDecimal materialId, Date revisionDate) {
		SearchCriteria criteria = new SearchCriteria("materialId", SearchCriterion.EQUALS, "" + materialId);
		if(revisionDate != null)
			criteria.addCriterion("revisionDate", SearchCriterion.EQUALS, DateHandler.getOracleToDateFunction(revisionDate));
		return criteria;
	}
	
	private SearchCriteria getKeyCriteria(BigDecimal materialId, Date revisionDate, String companyId) {
		SearchCriteria criteria = new SearchCriteria("materialId", SearchCriterion.EQUALS, "" + materialId);
		criteria.addCriterion("revisionDate", SearchCriterion.EQUALS, DateHandler.getOracleToDateFunction(revisionDate));
		criteria.addCriterion("companyId", SearchCriterion.EQUALS, "" + companyId);
		return criteria;
	}

	//get column names
	@Override
	public String getColumnName(String attributeName) {
		if(attributeName.equals("materialId")) {
			return ATTRIBUTE_MATERIAL_ID;
		}
		else if(attributeName.equals("revisionDate")) {
			return ATTRIBUTE_REVISION_DATE;
		}
		else if(attributeName.equals("content")) {
			return ATTRIBUTE_CONTENT;
		}
		else if(attributeName.equals("emergencyPhone")) {
			return ATTRIBUTE_EMERGENCY_PHONE;
		}
		else if(attributeName.equals("specificGravity")) {
			return ATTRIBUTE_SPECIFIC_GRAVITY;
		}
		else if(attributeName.equals("health")) {
			return ATTRIBUTE_HEALTH;
		}
		else if(attributeName.equals("flammability")) {
			return ATTRIBUTE_FLAMMABILITY;
		}
		else if(attributeName.equals("reactivity")) {
			return ATTRIBUTE_REACTIVITY;
		}
		else if(attributeName.equals("specificHazard")) {
			return ATTRIBUTE_SPECIFIC_HAZARD;
		}
		else if(attributeName.equals("compatibility")) {
			return ATTRIBUTE_COMPATIBILITY;
		}
		else if(attributeName.equals("storageTemp")) {
			return ATTRIBUTE_STORAGE_TEMP;
		}
		else if(attributeName.equals("ppe")) {
			return ATTRIBUTE_PPE;
		}
		else if(attributeName.equals("signalWord")) {
			return ATTRIBUTE_SIGNAL_WORD;
		}
		else if(attributeName.equals("targetOrgan")) {
			return ATTRIBUTE_TARGET_ORGAN;
		}
		else if(attributeName.equals("routeOfEntry")) {
			return ATTRIBUTE_ROUTE_OF_ENTRY;
		}
		else if(attributeName.equals("skin")) {
			return ATTRIBUTE_SKIN;
		}
		else if(attributeName.equals("eyes")) {
			return ATTRIBUTE_EYES;
		}
		else if(attributeName.equals("inhalation")) {
			return ATTRIBUTE_INHALATION;
		}
		else if(attributeName.equals("injection")) {
			return ATTRIBUTE_INJECTION;
		}
		else if(attributeName.equals("boilingPoint")) {
			return ATTRIBUTE_BOILING_POINT;
		}
		else if(attributeName.equals("flashPoint")) {
			return ATTRIBUTE_FLASH_POINT;
		}
		else if(attributeName.equals("ph")) {
			return ATTRIBUTE_PH;
		}
		else if(attributeName.equals("freezingPoint")) {
			return ATTRIBUTE_FREEZING_POINT;
		}
		else if(attributeName.equals("density")) {
			return ATTRIBUTE_DENSITY;
		}
		else if(attributeName.equals("ingestion")) {
			return ATTRIBUTE_INGESTION;
		}
		else if(attributeName.equals("onLine")) {
			return ATTRIBUTE_ON_LINE;
		}
		else if(attributeName.equals("densityUnit")) {
			return ATTRIBUTE_DENSITY_UNIT;
		}
		else if(attributeName.equals("physicalState")) {
			return ATTRIBUTE_PHYSICAL_STATE;
		}
		else if(attributeName.equals("vocUnit")) {
			return ATTRIBUTE_VOC_UNIT;
		}
		else if(attributeName.equals("tsca12b")) {
			return ATTRIBUTE_TSCA_12B;
		}
		else if(attributeName.equals("sara311312Acute")) {
			return ATTRIBUTE_SARA_311_312_ACUTE;
		}
		else if(attributeName.equals("sara311312Chronic")) {
			return ATTRIBUTE_SARA_311_312_CHRONIC;
		}
		else if(attributeName.equals("sara311312Fire")) {
			return ATTRIBUTE_SARA_311_312_FIRE;
		}
		else if(attributeName.equals("sara311312Pressure")) {
			return ATTRIBUTE_SARA_311_312_PRESSURE;
		}
		else if(attributeName.equals("sara311312Reactivity")) {
			return ATTRIBUTE_SARA_311_312_REACTIVITY;
		}
		else if(attributeName.equals("oshaHazard")) {
			return ATTRIBUTE_OSHA_HAZARD;
		}
		else if(attributeName.equals("tscaList")) {
			return ATTRIBUTE_TSCA_LIST;
		}
		else if(attributeName.equals("mixture")) {
			return ATTRIBUTE_MIXTURE;
		}
		else if(attributeName.equals("voc")) {
			return ATTRIBUTE_VOC;
		}
		else if(attributeName.equals("vocLower")) {
			return ATTRIBUTE_VOC_LOWER;
		}
		else if(attributeName.equals("vocUpper")) {
			return ATTRIBUTE_VOC_UPPER;
		}
		else if(attributeName.equals("reviewedBy")) {
			return ATTRIBUTE_REVIEWED_BY;
		}
		else if(attributeName.equals("reviewDate")) {
			return ATTRIBUTE_REVIEW_DATE;
		}
		else if(attributeName.equals("remark")) {
			return ATTRIBUTE_REMARK;
		}
		else if(attributeName.equals("flashPointUnit")) {
			return ATTRIBUTE_FLASH_POINT_UNIT;
		}
		else if(attributeName.equals("solids")) {
			return ATTRIBUTE_SOLIDS;
		}
		else if(attributeName.equals("solidsLower")) {
			return ATTRIBUTE_SOLIDS_LOWER;
		}
		else if(attributeName.equals("solidsUpper")) {
			return ATTRIBUTE_SOLIDS_UPPER;
		}
		else if(attributeName.equals("solidsUnit")) {
			return ATTRIBUTE_SOLIDS_UNIT;
		}
		else if(attributeName.equals("solidsPercent")) {
			return ATTRIBUTE_SOLIDS_PERCENT;
		}
		else if(attributeName.equals("solidsLowerPercent")) {
			return ATTRIBUTE_SOLIDS_LOWER_PERCENT;
		}
		else if(attributeName.equals("solidsUpperPercent")) {
			return ATTRIBUTE_SOLIDS_UPPER_PERCENT;
		}
		else if(attributeName.equals("vocLowerPercent")) {
			return ATTRIBUTE_VOC_LOWER_PERCENT;
		}
		else if(attributeName.equals("vocUpperPercent")) {
			return ATTRIBUTE_VOC_UPPER_PERCENT;
		}
		else if(attributeName.equals("vocPercent")) {
			return ATTRIBUTE_VOC_PERCENT;
		}
		else if(attributeName.equals("hmisHealth")) {
			return ATTRIBUTE_HMIS_HEALTH;
		}
		else if(attributeName.equals("hmisFlammability")) {
			return ATTRIBUTE_HMIS_FLAMMABILITY;
		}
		else if(attributeName.equals("hmisReactivity")) {
			return ATTRIBUTE_HMIS_REACTIVITY;
		}
		else if(attributeName.equals("hmisChronic")) {
			return ATTRIBUTE_HMIS_CHRONIC;
		}
		else if(attributeName.equals("hmisSource")) {
			return ATTRIBUTE_HMIS_SOURCE;
		}
		else if(attributeName.equals("personalProtection")) {
			return ATTRIBUTE_PERSONAL_PROTECTION;
		}
		else if(attributeName.equals("altDataSource")) {
			return ATTRIBUTE_ALT_DATA_SOURCE;
		}
		else if(attributeName.equals("vaporPressureDetect")) {
			return ATTRIBUTE_VAPOR_PRESSURE_DETECT;
		}
		else if(attributeName.equals("vaporPressure")) {
			return ATTRIBUTE_VAPOR_PRESSURE;
		}
		else if(attributeName.equals("vaporPressureUnit")) {
			return ATTRIBUTE_VAPOR_PRESSURE_UNIT;
		}
		else if(attributeName.equals("vaporPressureTemp")) {
			return ATTRIBUTE_VAPOR_PRESSURE_TEMP;
		}
		else if(attributeName.equals("vaporPressureTempUnit")) {
			return ATTRIBUTE_VAPOR_PRESSURE_TEMP_UNIT;
		}
		else if(attributeName.equals("insertDate")) {
			return ATTRIBUTE_INSERT_DATE;
		}
		else if(attributeName.equals("compositionChanged")) {
			return ATTRIBUTE_COMPOSITION_CHANGED;
		}
		else if(attributeName.equals("vocLessH2oExempt")) {
			return ATTRIBUTE_VOC_LESS_H2O_EXEMPT;
		}
		else if(attributeName.equals("vocLessH2oExemptUnit")) {
			return ATTRIBUTE_VOC_LESS_H2O_EXEMPT_UNIT;
		}
		else if(attributeName.equals("vocLbPerSolidLb")) {
			return ATTRIBUTE_VOC_LB_PER_SOLID_LB;
		}
		else if(attributeName.equals("vocLessH2oExemptLower")) {
			return ATTRIBUTE_VOC_LESS_H2O_EXEMPT_LOWER;
		}
		else if(attributeName.equals("vocLessH2oExemptUpper")) {
			return ATTRIBUTE_VOC_LESS_H2O_EXEMPT_UPPER;
		}
		else if(attributeName.equals("vocSource")) {
			return ATTRIBUTE_VOC_SOURCE;
		}
		else if(attributeName.equals("vocLessH2oExemptSource")) {
			return ATTRIBUTE_VOC_LESS_H2O_EXEMPT_SOURCE;
		}
		else if(attributeName.equals("vocLbPerSolidLbSource")) {
			return ATTRIBUTE_VOC_LB_PER_SOLID_LB_SOURCE;
		}
		else if(attributeName.equals("solidsSource")) {
			return ATTRIBUTE_SOLIDS_SOURCE;
		}
		else if(attributeName.equals("vaporPressureSource")) {
			return ATTRIBUTE_VAPOR_PRESSURE_SOURCE;
		}
		else if(attributeName.equals("vocLessH20ExemptSource")) {
			return ATTRIBUTE_VOC_LESS_H20_EXEMPT_SOURCE;
		}
		else if(attributeName.equals("vocLbPerSolidLbLower")) {
			return ATTRIBUTE_VOC_LB_PER_SOLID_LB_LOWER;
		}
		else if(attributeName.equals("vocLbPerSolidLbUpper")) {
			return ATTRIBUTE_VOC_LB_PER_SOLID_LB_UPPER;
		}
		else if(attributeName.equals("vocLbPerSolidLbLowCalc")) {
			return ATTRIBUTE_VOC_LB_PER_SOLID_LB_LOW_CALC;
		}
		else if(attributeName.equals("vocLbPerSolidLbCalc")) {
			return ATTRIBUTE_VOC_LB_PER_SOLID_LB_CALC;
		}
		else if(attributeName.equals("vocLbPerSolidLbUpCalc")) {
			return ATTRIBUTE_VOC_LB_PER_SOLID_LB_UP_CALC;
		}
		else if(attributeName.equals("vaporPressureLower")) {
			return ATTRIBUTE_VAPOR_PRESSURE_LOWER;
		}
		else if(attributeName.equals("vaporPressureUpper")) {
			return ATTRIBUTE_VAPOR_PRESSURE_UPPER;
		}
		else if(attributeName.equals("vocLessH2oExemptLowerCalc")) {
			return ATTRIBUTE_VOC_LESS_H2O_EXEMPT_LOWER_CALC;
		}
		else if(attributeName.equals("vocLessH2oExemptCalc")) {
			return ATTRIBUTE_VOC_LESS_H2O_EXEMPT_CALC;
		}
		else if(attributeName.equals("vocLessH2oExemptUpperCalc")) {
			return ATTRIBUTE_VOC_LESS_H2O_EXEMPT_UPPER_CALC;
		}
		else if(attributeName.equals("vocLessH2oExemptUnitCalc")) {
			return ATTRIBUTE_VOC_LESS_H2O_EXEMPT_UNIT_CALC;
		}
		else if(attributeName.equals("frenchContent")) {
			return ATTRIBUTE_FRENCH_CONTENT;
		}
		else if(attributeName.equals("vocCompVaporPressureMmhg")) {
			return ATTRIBUTE_VOC_COMP_VAPOR_PRESSURE_MMHG;
		}
		else if(attributeName.equals("chronic")) {
			return ATTRIBUTE_CHRONIC;
		}
		else if(attributeName.equals("lfcCode")) {
			return ATTRIBUTE_LFC_CODE;
		}
		else if(attributeName.equals("polymerize")) {
			return ATTRIBUTE_POLYMERIZE;
		}
		else if(attributeName.equals("incompatible")) {
			return ATTRIBUTE_INCOMPATIBLE;
		}
		else if(attributeName.equals("corrosive")) {
			return ATTRIBUTE_CORROSIVE;
		}
		else if(attributeName.equals("healthEffects")) {
			return ATTRIBUTE_HEALTH_EFFECTS;
		}
		else if(attributeName.equals("stable")) {
			return ATTRIBUTE_STABLE;
		}
		else if(attributeName.equals("vaporDensity")) {
			return ATTRIBUTE_VAPOR_DENSITY;
		}
		else if(attributeName.equals("evaporationRate")) {
			return ATTRIBUTE_EVAPORATION_RATE;
		}
		else if(attributeName.equals("fireConditionsToAvoid")) {
			return ATTRIBUTE_FIRE_CONDITIONS_TO_AVOID;
		}		
		else if(attributeName.equals("specificGravityBasis")) {
			return ATTRIBUTE_SPECIFIC_GRAVITY_BASIS;
		}
		else if(attributeName.equals("specificGravitySource")) {
			return ATTRIBUTE_SPECIFIC_GRAVITY_SOURCE;
		}
		else if(attributeName.equals("nfpaSource")) {
			return ATTRIBUTE_NFPA_SOURCE;
		}
		
		else if(attributeName.equals("specificGravityDetect")) {
			return ATTRIBUTE_SPECIFIC_GRAVITY_DETECT;
		}
		else if(attributeName.equals("specificGravityLower")) {
			return ATTRIBUTE_SPECIFIC_GRAVITY_LOWER;
		}
		else if(attributeName.equals("specificGravityUpper")) {
			return ATTRIBUTE_SPECIFIC_GRAVITY_UPPER;
		}
		else if(attributeName.equals("densitySource")) {
			return ATTRIBUTE_DENSITY_SOURCE;
		}
		else if(attributeName.equals("densityDetect")) {
			return ATTRIBUTE_DENSITY_DETECT;
		}
		else if(attributeName.equals("densityUpper")) {
			return ATTRIBUTE_DENSITY_UPPER;
		}
		else if(attributeName.equals("flashPointDetect")) {
			return ATTRIBUTE_FLASH_POINT_DETECT;
		}
		else if(attributeName.equals("flashPointSource")) {
			return ATTRIBUTE_FLASH_POINT_SOURCE;
		}
		else if(attributeName.equals("flashPointLower")) {
			return ATTRIBUTE_FLASH_POINT_LOWER;
		}
		else if(attributeName.equals("flashPointMethod")) {
			return ATTRIBUTE_FLASH_POINT_METHOD;
		}
		else if(attributeName.equals("flashPointUpper")) {
			return ATTRIBUTE_FLASH_POINT_UPPER;
		}
		else if(attributeName.equals("boilingPointDetect")) {
			return ATTRIBUTE_BOILING_POINT_DETECT; 
		}
		else if(attributeName.equals("boilingPointSource")) {
			return ATTRIBUTE_BOILING_POINT_SOURCE; 
		}
		else if(attributeName.equals("boilingPointUpper")) {
			return ATTRIBUTE_BOILING_POINT_UPPER; 
		}
		else if(attributeName.equals("boilingPointUnit")) {
			return ATTRIBUTE_BOILING_POINT_UNIT;
		}
		else if(attributeName.equals("phDetail")) {
			return ATTRIBUTE_PH_DETAIL;
		}
		else if(attributeName.equals("phSource")) {
			return 	ATTRIBUTE_PH_SOURCE;
		}
		else if(attributeName.equals("boilingPointDetail")) {
			return ATTRIBUTE_BOILING_POINT_DETAIL;
		}
		else if(attributeName.equals("phDetect")) {
			return ATTRIBUTE_PH_DETECT; 
		}
		else if(attributeName.equals("phUpper")) {
			return ATTRIBUTE_PH_UPPER;
		}
		else if(attributeName.equals("waterReactive")) {
			return ATTRIBUTE_WATER_REACTIVE;
		}
		else if(attributeName.equals("oxidizer")) {
			return ATTRIBUTE_OXIDIZER;
		}
		else if(attributeName.equals("carcinogen")) {
			return ATTRIBUTE_CARCINOGEN;
		}
		else if(attributeName.equals("productCode")) {
			return ATTRIBUTE_PRODUCT_CODE;
		}
		else if(attributeName.equals("alloy")) {
			return ATTRIBUTE_ALLOY;
		}
        else if(attributeName.equals("msdsIndexingPriorityId")) {
			return ATTRIBUTE_MSDS_INDEXING_PRIORITY_ID;
		}
        else if(attributeName.equals("coHealth")) {
			return ATTRIBUTE_CO_HEALTH;
		}
        else if (attributeName.equals("coHealthEffects")) {
        	return ATTRIBUTE_CO_HEALTH_EFFECTS;
        }
		else if(attributeName.equals("coFlammability")) {
			return ATTRIBUTE_CO_FLAMMABILITY;
		}
		else if(attributeName.equals("coReactivity")) {
			return ATTRIBUTE_CO_REACTIVITY;
		}
		else if(attributeName.equals("coNfpaSource")) {
			return ATTRIBUTE_CO_NFPA_SOURCE;
		}
		else if(attributeName.equals("coSpecificHazard")) {
			return ATTRIBUTE_CO_SPECIFIC_HAZARD;
		}
		else if(attributeName.equals("coHmisFlammability")) {
			return ATTRIBUTE_CO_HMIS_FLAMMABILITY;
		}
		else if(attributeName.equals("coHmisHealth")) {
			return ATTRIBUTE_CO_HMIS_HEALTH;
		}
		else if(attributeName.equals("coHmisSource")) {
			return ATTRIBUTE_CO_HMIS_SOURCE;
		}
		else if(attributeName.equals("coHmisReactivity")) {
			return ATTRIBUTE_CO_HMIS_REACTIVITY;
		}
		else if(attributeName.equals("coHmisChronic")) {
			return ATTRIBUTE_CO_HMIS_CHRONIC;
		}
		else if(attributeName.equals("coSpecificGravityBasis")) {
			return ATTRIBUTE_CO_SPECIFIC_GRAVITY_BASIS; 
		}
		else if(attributeName.equals("coSpecificGravityDetect")) {
			return ATTRIBUTE_CO_SPECIFIC_GRAVITY_DETECT;
		}
		else if(attributeName.equals("coSpecificGravitySource")) {
			return ATTRIBUTE_CO_SPECIFIC_GRAVITY_SOURCE; 
		}
		else if(attributeName.equals("coSpecificGravityLower")) {
			return ATTRIBUTE_CO_SPECIFIC_GRAVITY_LOWER; 
		}
		else if(attributeName.equals("coSpecificGravityUpper")) {
			return ATTRIBUTE_CO_SPECIFIC_GRAVITY_UPPER;
		}
		else if(attributeName.equals("coSpecificGravity")) {
			return ATTRIBUTE_CO_SPECIFIC_GRAVITY;
		}
		else if(attributeName.equals("coDensityDetect")) {
			return ATTRIBUTE_CO_DENSITY_DETECT;
		}
		else if(attributeName.equals("coDensitySource")) {
			return ATTRIBUTE_CO_DENSITY_SOURCE;
		}
		else if(attributeName.equals("coDensityUpper")) {
			return ATTRIBUTE_CO_DENSITY_UPPER;
		}
		else if(attributeName.equals("coDensity")) {
			return ATTRIBUTE_CO_DENSITY;
		}
		else if(attributeName.equals("coDensityUnit")) {
			return ATTRIBUTE_CO_DENSITY_UNIT;
		}
		else if(attributeName.equals("coFlashPointDetect")) {
			return ATTRIBUTE_CO_FLASH_POINT_DETECT; 
		}
		else if(attributeName.equals("coFlashPointSource")) {
			return ATTRIBUTE_CO_FLASH_POINT_SOURCE;
		}
		else if(attributeName.equals("coFlashPointLower")) {
			return ATTRIBUTE_CO_FLASH_POINT_LOWER;
		}
		else if(attributeName.equals("coFlashPointMethod")) {
			return ATTRIBUTE_CO_FLASH_POINT_METHOD;
		}
		else if(attributeName.equals("coFlashPoint")) {
			return ATTRIBUTE_CO_FLASH_POINT;
		}
		else if(attributeName.equals("coFlashPointUnit")) {
			return ATTRIBUTE_CO_FLASH_POINT_UNIT;
		}
		else if(attributeName.equals("coFlashPointUpper")) {
			return ATTRIBUTE_CO_FLASH_POINT_UPPER;
		}
		else if(attributeName.equals("coBoilingPointDetect")) {
			return ATTRIBUTE_CO_BOILING_POINT_DETECT;
		}
		else if(attributeName.equals("coBoilingPointSource")) {
			return ATTRIBUTE_CO_BOILING_POINT_SOURCE;
		}
		else if(attributeName.equals("coBoilingPointUpper")) {
			return ATTRIBUTE_CO_BOILING_POINT_UPPER;
		}
		else if(attributeName.equals("coBoilingPointUnit")) {
			return ATTRIBUTE_CO_BOILING_POINT_UNIT;
		}
		else if(attributeName.equals("coBoilingPoint")) {
			return ATTRIBUTE_CO_BOILING_POINT;
		}
		else if(attributeName.equals("coPhDetail")) {
			return ATTRIBUTE_CO_PH_DETAIL;
		}
		else if(attributeName.equals("coBoilingPointDetail")) {
			return ATTRIBUTE_CO_BOILING_POINT_DETAIL;
		}
		else if(attributeName.equals("coPhDetect")) {
			return ATTRIBUTE_CO_PH_DETECT;
		}
		else if(attributeName.equals("coPhSource")) {
			return ATTRIBUTE_CO_PH_SOURCE;
		}
		else if(attributeName.equals("coPh")) {
			return ATTRIBUTE_CO_PH;
		}
		else if(attributeName.equals("coVocLower")) {
			return ATTRIBUTE_CO_VOC_LOWER;
		}
		else if(attributeName.equals("coVoc")) {
			return ATTRIBUTE_CO_VOC;
		}
		else if(attributeName.equals("coVocSource")) {
			return ATTRIBUTE_CO_VOC_SOURCE;
		}
		else if(attributeName.equals("coVocUnit")) {
			return ATTRIBUTE_CO_VOC_UNIT;
		}
		else if(attributeName.equals("coVocLessH2oExemptUpper")) {
			return ATTRIBUTE_CO_VOC_LESS_H2O_EXEMPT_UPPER;
		}
		else if(attributeName.equals("coVocLessH2oExempt")) {
			return ATTRIBUTE_CO_VOC_LESS_H2O_EXEMPT; 
		}
		else if(attributeName.equals("coVocLessH2oExemptLower")) {
			return ATTRIBUTE_CO_VOC_LESS_H2O_EXEMPT_LOWER;
		}
		else if(attributeName.equals("coVocLessH2oExemptSource")) {
			return ATTRIBUTE_CO_VOC_LESS_H2O_EXEMPT_SOURCE;
		}
		else if(attributeName.equals("coVocLessH2oExemptUnit")) {	
			return ATTRIBUTE_CO_VOC_LESS_H2O_EXEMPT_UNIT;
		}
		else if(attributeName.equals("coSolids")) {
			return ATTRIBUTE_CO_SOLIDS;
		}
		else if(attributeName.equals("coSolidsLower")) {
			return ATTRIBUTE_CO_SOLIDS_LOWER;
		}
		else if(attributeName.equals("coSolidsUpper")) {
			return ATTRIBUTE_CO_SOLIDS_UPPER;
		}
		else if(attributeName.equals("coSolidsSource")) {
			return ATTRIBUTE_CO_SOLIDS_SOURCE; 
		}
		else if(attributeName.equals("coSolidsUnit")) {
			return ATTRIBUTE_CO_SOLIDS_UNIT;
		}
		else if(attributeName.equals("coVaporPressure")) {
			return ATTRIBUTE_CO_VAPOR_PRESSURE; 
		}
		else if(attributeName.equals("coVaporPressureDetect")) {
			return ATTRIBUTE_CO_VAPOR_PRESSURE_DETECT; 
		}
		else if(attributeName.equals("coVaporPressureLower")) {
			return ATTRIBUTE_CO_VAPOR_PRESSURE_LOWER; 
		}
		else if(attributeName.equals("coVaporPressureSource")) {
			return  ATTRIBUTE_CO_VAPOR_PRESSURE_SOURCE;
		}
		else if(attributeName.equals("coVaporPressureTemp")) {
			return ATTRIBUTE_CO_VAPOR_PRESSURE_TEMP;
		}
		else if(attributeName.equals("coVaporPressureTempUnit")) {
			return ATTRIBUTE_CO_VAPOR_PRESSURE_TEMP_UNIT;
		}
		else if(attributeName.equals("coVaporPressureUnit")) {
			return ATTRIBUTE_CO_VAPOR_PRESSURE_UNIT;
		}
		else if(attributeName.equals("coVaporPressureUpper")) {
			return 	ATTRIBUTE_CO_VAPOR_PRESSURE_UPPER;
		}
		else if(attributeName.equals("boilingPointLower")) {
			return 	ATTRIBUTE_BOILING_POINT_LOWER;
		}
		else if(attributeName.equals("coBoilingPointLower")) {
			return 	ATTRIBUTE_CO_BOILING_POINT_LOWER;
		}
		else if(attributeName.equals("coPersonalProtection")) {
			return ATTRIBUTE_CO_PERSONAL_PROTECTION;
		}
		else if(attributeName.equals("lockheedMsds")) {
			return 	ATTRIBUTE_LOCKHEED_MSDS;
		}
		else if(attributeName.equals("coPhUpper")) {
			return 	ATTRIBUTE_CO_PH_UPPER;
		}
		else if(attributeName.equals("coVocUpper")) {
			return 	ATTRIBUTE_CO_VOC_UPPER;
		}
        else if(attributeName.equals("verifiedBy")) {
			return 	ATTRIBUTE_VERIFIED_BY;
		}
        else if(attributeName.equals("verifiedByName")) {
        	return ATTRIBUTE_VERIFIED_BY_NAME;
        }
        else if(attributeName.equals("verifiedOn")) {
			return 	ATTRIBUTE_VERIFIED_ON;
		}
        else if(attributeName.equals("verifiedDate")) {
			return 	ATTRIBUTE_VERIFIED_ON;
		}
        else if(attributeName.equals("idOnly")) {
        	return ATTRIBUTE_ID_ONLY;
        }
        else if(attributeName.equals("msdsAuthorId")) {
        	return ATTRIBUTE_MSDS_AUTHOR_ID;
        }
        else if(attributeName.equals("ozoneDepletingCompound")) {
			return 	ATTRIBUTE_OZONE_DEPLETING_COMPOUND;
		}
        else if(attributeName.equals("lowVolumeExempt")) {
        	return ATTRIBUTE_LOW_VOLUME_EXEMPT;
        }
        else if(attributeName.equals("dataEntryComplete")) {
        	return ATTRIBUTE_DATA_ENTRY_COMPLETE;
        }
        else if(attributeName.equals("coDataEntryComplete")) {
        	return ATTRIBUTE_CO_DATA_ENTRY_COMPLETE;
        }
        else if(attributeName.equals("detonable")) {
        	return ATTRIBUTE_DETONABLE;
        }
        else if(attributeName.equals("miscible")) {
        	return ATTRIBUTE_MISCIBLE;
        }
        else if(attributeName.equals("pyrophoric")) {
        	return ATTRIBUTE_PYROPHORIC;
        }
        else if(attributeName.equals("spontaneouslyCombustible")) {
        	return ATTRIBUTE_SPONTANEOUSLY_COMBUSTIBLE;
        }
        else if(attributeName.equals("tscaStatement")) {
        	return ATTRIBUTE_TSCA_STATEMENT;
        }
        else if(attributeName.equals("physicalStateSource")) {
        	return ATTRIBUTE_PHYSICAL_STATE_SOURCE;
        }
        else if(attributeName.equals("labelCompanyName")) {
        	return ATTRIBUTE_LABEL_COMPANY_NAME;
        }
        else if(attributeName.equals("labelAddressLine1")) {
        	return ATTRIBUTE_LABEL_ADDRESS_LINE_1;
        }
        else if(attributeName.equals("labelAddressLine2")) {
        	return ATTRIBUTE_LABEL_ADDRESS_LINE_2;
        }
        else if(attributeName.equals("labelAddressLine3")) {
        	return ATTRIBUTE_LABEL_ADDRESS_LINE_3;
        }
        else if(attributeName.equals("labelAddressLine4")) {
        	return ATTRIBUTE_LABEL_ADDRESS_LINE_4;
        }
        else if(attributeName.equals("labelCity")) {
        	return ATTRIBUTE_LABEL_CITY;
        }
        else if(attributeName.equals("labelCountryAbbrev")) {
        	return ATTRIBUTE_LABEL_COUNTRY_ABBREV;
        }
        else if(attributeName.equals("labelStateAbbrev")) {
        	return ATTRIBUTE_LABEL_STATE_ABBREV;
        }
        else if(attributeName.equals("labelZip")) {
        	return ATTRIBUTE_LABEL_ZIP;
        }
        else if(attributeName.equals("labelPhone")) {
        	return ATTRIBUTE_LABEL_PHONE;
        }
        else if(attributeName.equals("msdsId")) {
        	return ATTRIBUTE_MSDS_ID;
        }
        else if(attributeName.equals("ghsCompliantImage")) {
        	return ATTRIBUTE_GHS_COMPLIANT_IMAGE;
        }
        else if(attributeName.equals("asMixed")) {
        	return ATTRIBUTE_AS_MIXED;
        }
        else if (attributeName.equals("ghsHazard")) {
        	return ATTRIBUTE_GHS_HAZARD;
        }
		
        else {
			return super.getColumnName(attributeName);
		}
	}
	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, MsdsBean.class);
	}

	//insert
	public int insert(MsdsBean msdsBean) throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = insert(msdsBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int insert(MsdsBean msdsBean, Connection conn)
	throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + TABLE + " (" +
			ATTRIBUTE_MATERIAL_ID + "," +
			ATTRIBUTE_REVISION_DATE + "," +
			ATTRIBUTE_CONTENT + "," +
			ATTRIBUTE_EMERGENCY_PHONE + "," +
			ATTRIBUTE_SPECIFIC_GRAVITY + "," +
			ATTRIBUTE_HEALTH + "," +
			ATTRIBUTE_FLAMMABILITY + "," +
			ATTRIBUTE_REACTIVITY + "," +
			ATTRIBUTE_SPECIFIC_HAZARD + "," +
			ATTRIBUTE_COMPATIBILITY + "," +
			ATTRIBUTE_STORAGE_TEMP + "," +
			ATTRIBUTE_PPE + "," +
			ATTRIBUTE_SIGNAL_WORD + "," +
			ATTRIBUTE_TARGET_ORGAN + "," +
			ATTRIBUTE_ROUTE_OF_ENTRY + "," +
			ATTRIBUTE_SKIN + "," +
			ATTRIBUTE_EYES + "," +
			ATTRIBUTE_INHALATION + "," +
			ATTRIBUTE_INJECTION + "," +
			ATTRIBUTE_PH + "," +
			ATTRIBUTE_FREEZING_POINT + "," +
			ATTRIBUTE_DENSITY + "," +
			ATTRIBUTE_INGESTION + "," +
			ATTRIBUTE_ON_LINE + "," +
			ATTRIBUTE_DENSITY_UNIT + "," +
			ATTRIBUTE_PHYSICAL_STATE + "," +
			ATTRIBUTE_VOC_UNIT + "," +
			ATTRIBUTE_TSCA_12B + "," +
			ATTRIBUTE_SARA_311_312_ACUTE + "," +
			ATTRIBUTE_SARA_311_312_CHRONIC + "," +
			ATTRIBUTE_SARA_311_312_FIRE + "," +
			ATTRIBUTE_SARA_311_312_PRESSURE + "," +
			ATTRIBUTE_SARA_311_312_REACTIVITY + "," +
			ATTRIBUTE_OSHA_HAZARD + "," +
			ATTRIBUTE_TSCA_LIST + "," +
			ATTRIBUTE_MIXTURE + "," +
			ATTRIBUTE_VOC + "," +
			ATTRIBUTE_VOC_LOWER + "," +
			ATTRIBUTE_VOC_UPPER + "," +
			ATTRIBUTE_REVIEWED_BY + "," +
			ATTRIBUTE_REVIEW_DATE + "," +
			ATTRIBUTE_REMARK + "," +
			ATTRIBUTE_FLASH_POINT_UNIT + "," +
			ATTRIBUTE_SOLIDS + "," +
			ATTRIBUTE_SOLIDS_LOWER + "," +
			ATTRIBUTE_SOLIDS_UPPER + "," +
			ATTRIBUTE_SOLIDS_UNIT + "," +
			ATTRIBUTE_SOLIDS_PERCENT + "," +
			ATTRIBUTE_SOLIDS_LOWER_PERCENT + "," +
			ATTRIBUTE_SOLIDS_UPPER_PERCENT + "," +
			ATTRIBUTE_VOC_LOWER_PERCENT + "," +
			ATTRIBUTE_VOC_UPPER_PERCENT + "," +
			ATTRIBUTE_VOC_PERCENT + "," +
			ATTRIBUTE_HMIS_HEALTH + "," +
			ATTRIBUTE_HMIS_FLAMMABILITY + "," +
			ATTRIBUTE_HMIS_REACTIVITY + "," +
			ATTRIBUTE_HMIS_CHRONIC  + "," +
			ATTRIBUTE_HMIS_SOURCE  + "," +
			ATTRIBUTE_PERSONAL_PROTECTION + "," +
			ATTRIBUTE_ALT_DATA_SOURCE + "," +
			ATTRIBUTE_VAPOR_PRESSURE_DETECT + "," +
			ATTRIBUTE_VAPOR_PRESSURE + "," +
			ATTRIBUTE_VAPOR_PRESSURE_UNIT + "," +
			ATTRIBUTE_VAPOR_PRESSURE_TEMP + "," +
			ATTRIBUTE_VAPOR_PRESSURE_TEMP_UNIT + "," +
			ATTRIBUTE_INSERT_DATE + "," +
			ATTRIBUTE_COMPOSITION_CHANGED + "," +
			ATTRIBUTE_VOC_LESS_H2O_EXEMPT + "," +
			ATTRIBUTE_VOC_LESS_H2O_EXEMPT_UNIT + "," +
			ATTRIBUTE_VOC_LB_PER_SOLID_LB + "," +
			ATTRIBUTE_VOC_LESS_H2O_EXEMPT_LOWER + "," +
			ATTRIBUTE_VOC_LESS_H2O_EXEMPT_UPPER + "," +
			ATTRIBUTE_VOC_SOURCE + "," +
			ATTRIBUTE_VOC_LESS_H2O_EXEMPT_SOURCE + "," +
			ATTRIBUTE_VOC_LB_PER_SOLID_LB_SOURCE + "," +
			ATTRIBUTE_SOLIDS_SOURCE + "," +
			ATTRIBUTE_VAPOR_PRESSURE_SOURCE + "," +
			ATTRIBUTE_VOC_LESS_H20_EXEMPT_SOURCE + "," +
			ATTRIBUTE_VOC_LB_PER_SOLID_LB_LOWER + "," +
			ATTRIBUTE_VOC_LB_PER_SOLID_LB_UPPER + "," +
			ATTRIBUTE_VOC_LB_PER_SOLID_LB_LOW_CALC + "," +
			ATTRIBUTE_VOC_LB_PER_SOLID_LB_CALC + "," +
			ATTRIBUTE_VOC_LB_PER_SOLID_LB_UP_CALC + "," +
			ATTRIBUTE_VAPOR_PRESSURE_LOWER + "," +
			ATTRIBUTE_VAPOR_PRESSURE_UPPER + "," +
			ATTRIBUTE_VOC_LESS_H2O_EXEMPT_LOWER_CALC + "," +
			ATTRIBUTE_VOC_LESS_H2O_EXEMPT_CALC + "," +
			ATTRIBUTE_VOC_LESS_H2O_EXEMPT_UPPER_CALC + "," +
			ATTRIBUTE_VOC_LESS_H2O_EXEMPT_UNIT_CALC + "," +
			ATTRIBUTE_FRENCH_CONTENT + "," +
			ATTRIBUTE_VOC_COMP_VAPOR_PRESSURE_MMHG + "," +
			ATTRIBUTE_LFC_CODE + "," +
			ATTRIBUTE_HEALTH_EFFECTS + "," +
			ATTRIBUTE_VAPOR_DENSITY + "," +
			ATTRIBUTE_NFPA_SOURCE + "," +
			ATTRIBUTE_EVAPORATION_RATE + "," +
			ATTRIBUTE_SPECIFIC_GRAVITY_BASIS + "," +
			ATTRIBUTE_SPECIFIC_GRAVITY_SOURCE + "," +
			ATTRIBUTE_SPECIFIC_GRAVITY_DETECT + "," +
			ATTRIBUTE_SPECIFIC_GRAVITY_LOWER + "," +
			ATTRIBUTE_SPECIFIC_GRAVITY_UPPER + "," +
			ATTRIBUTE_DENSITY_DETECT + "," +
			ATTRIBUTE_DENSITY_SOURCE + "," +
			ATTRIBUTE_DENSITY_UPPER + "," +
			ATTRIBUTE_FLASH_POINT_DETECT + "," +
			ATTRIBUTE_FLASH_POINT_SOURCE + "," +
			ATTRIBUTE_FLASH_POINT_LOWER + "," +
			ATTRIBUTE_FLASH_POINT_METHOD + "," +
			ATTRIBUTE_FLASH_POINT_UPPER + "," +
			ATTRIBUTE_BOILING_POINT_DETECT + "," + 
			ATTRIBUTE_BOILING_POINT_SOURCE + "," +
			ATTRIBUTE_BOILING_POINT_LOWER + "," +
			ATTRIBUTE_BOILING_POINT_UPPER + "," + 
			ATTRIBUTE_BOILING_POINT_UNIT + "," +
			ATTRIBUTE_BOILING_POINT_DETAIL + "," +
			ATTRIBUTE_PH_DETAIL + "," +
			ATTRIBUTE_PH_SOURCE + "," +
			ATTRIBUTE_PH_DETECT + "," + 
			ATTRIBUTE_PH_UPPER + "," +
			ATTRIBUTE_VERIFIED_BY + "," +
			ATTRIBUTE_VERIFIED_ON + "," +
			ATTRIBUTE_ID_ONLY + "," +
			ATTRIBUTE_MSDS_AUTHOR_ID + "," +
            ATTRIBUTE_MSDS_INDEXING_PRIORITY_ID + "," +
			ATTRIBUTE_PHYSICAL_STATE_SOURCE + "," +
			ATTRIBUTE_LABEL_COMPANY_NAME + "," +
			ATTRIBUTE_LABEL_ADDRESS_LINE_1 + "," +
			ATTRIBUTE_LABEL_ADDRESS_LINE_2 + "," +
			ATTRIBUTE_LABEL_ADDRESS_LINE_3 + "," +
			ATTRIBUTE_LABEL_ADDRESS_LINE_4 + "," +
			ATTRIBUTE_LABEL_CITY + "," +
			ATTRIBUTE_LABEL_COUNTRY_ABBREV + "," +
			ATTRIBUTE_LABEL_STATE_ABBREV + "," +
			ATTRIBUTE_LABEL_ZIP + "," +
			ATTRIBUTE_LABEL_PHONE + "," +
			ATTRIBUTE_MSDS_ID + "," +
			//ATTRIBUTE_GHS_COMPLIANT_IMAGE + "," +
			ATTRIBUTE_GHS_HAZARD +
			")" +
			" values (" +
			msdsBean.getMaterialId() + "," +
			DateHandler.getOracleToDateFunction(msdsBean.getRevisionDate()) + "," +
			SqlHandler.delimitString(msdsBean.getContent()) + "," +
			SqlHandler.delimitString(msdsBean.getEmergencyPhone()) + "," +
			msdsBean.getSpecificGravity() + "," +
			SqlHandler.delimitString(msdsBean.getHealth()) + "," +
			SqlHandler.delimitString(msdsBean.getFlammability()) + "," +
			SqlHandler.delimitString(msdsBean.getReactivity()) + "," +
			SqlHandler.delimitString(msdsBean.getSpecificHazard()) + "," +
			SqlHandler.delimitString(msdsBean.getCompatibility()) + "," +
			SqlHandler.delimitString(msdsBean.getStorageTemp()) + "," +
			SqlHandler.delimitString(msdsBean.getPpe()) + "," +
			SqlHandler.delimitString(msdsBean.getSignalWord()) + "," +
			SqlHandler.delimitString(msdsBean.getTargetOrgan()) + "," +
			SqlHandler.delimitString(msdsBean.getRouteOfEntry()) + "," +
			SqlHandler.delimitString(msdsBean.getSkin()) + "," +
			SqlHandler.delimitString(msdsBean.getEyes()) + "," +
			SqlHandler.delimitString(msdsBean.getInhalation()) + "," +
			SqlHandler.delimitString(msdsBean.getInjection()) + "," +
			msdsBean.getPh() + "," +
			SqlHandler.delimitString(msdsBean.getFreezingPoint()) + "," +
			msdsBean.getDensity() + "," +
			SqlHandler.delimitString(msdsBean.getIngestion()) + "," +
			SqlHandler.delimitString(msdsBean.isOnLine() ? "Y" : "N") + "," +
			SqlHandler.delimitString(msdsBean.getDensityUnit()) + "," +
			SqlHandler.delimitString(msdsBean.getPhysicalState()) + "," +
			SqlHandler.delimitString(msdsBean.getVocUnit()) + "," +
			SqlHandler.delimitString(msdsBean.getTsca12b()) + "," +
			SqlHandler.delimitString(msdsBean.getSara311312Acute()) + "," +
			SqlHandler.delimitString(msdsBean.getSara311312Chronic()) + "," +
			SqlHandler.delimitString(msdsBean.getSara311312Fire()) + "," +
			SqlHandler.delimitString(msdsBean.getSara311312Pressure()) + "," +
			SqlHandler.delimitString(msdsBean.getSara311312Reactivity()) + "," +
			SqlHandler.delimitString(msdsBean.getOshaHazard()) + "," +
			SqlHandler.delimitString(msdsBean.getTscaList()) + "," +
			SqlHandler.delimitString(msdsBean.getMixture()) + "," +
			msdsBean.getVoc() + "," +
			msdsBean.getVocLower() + "," +
			msdsBean.getVocUpper() + "," +
			SqlHandler.delimitString(msdsBean.getReviewedBy()) + "," +
			"sysdate," +
			SqlHandler.delimitString(msdsBean.getRemark()) + "," +
			SqlHandler.delimitString(msdsBean.getFlashPointUnit()) + "," +
			msdsBean.getSolids() + "," +
			msdsBean.getSolidsLower() + "," +
			msdsBean.getSolidsUpper() + "," +
			SqlHandler.delimitString(msdsBean.getSolidsUnit()) + "," +
			msdsBean.getSolidsPercent() + "," +
			msdsBean.getSolidsLowerPercent() + "," +
			msdsBean.getSolidsUpperPercent() + "," +
			msdsBean.getVocLowerPercent() + "," +
			msdsBean.getVocUpperPercent() + "," +
			msdsBean.getVocPercent() + "," +
			SqlHandler.delimitString(msdsBean.getHmisHealth()) + "," +
			SqlHandler.delimitString(msdsBean.getHmisFlammability()) + "," +
			SqlHandler.delimitString(msdsBean.getHmisReactivity()) + "," +
			SqlHandler.delimitString(msdsBean.getHmisChronic()) + "," +
			SqlHandler.delimitString(msdsBean.getHmisSource()) + "," +
			SqlHandler.delimitString(msdsBean.getPersonalProtection()) + "," +
			SqlHandler.delimitString(msdsBean.getAltDataSource()) + "," +
			SqlHandler.delimitString(msdsBean.getVaporPressureDetect()) + "," +
			msdsBean.getVaporPressure() + "," +
			SqlHandler.delimitString(msdsBean.getVaporPressureUnit()) + "," +
			msdsBean.getVaporPressureTemp() + "," +
			SqlHandler.delimitString(msdsBean.getVaporPressureTempUnit()) + "," +
			"sysdate," +
			SqlHandler.delimitString(msdsBean.getCompositionChanged()) + "," +
			msdsBean.getVocLessH2oExempt() + "," +
			SqlHandler.delimitString(msdsBean.getVocLessH2oExemptUnit()) + "," +
			msdsBean.getVocLbPerSolidLb() + "," +
			msdsBean.getVocLessH2oExemptLower() + "," +
			msdsBean.getVocLessH2oExemptUpper() + "," +
			SqlHandler.delimitString(msdsBean.getVocSource()) + "," +
			SqlHandler.delimitString(msdsBean.getVocLessH2oExemptSource()) + "," +
			SqlHandler.delimitString(msdsBean.getVocLbPerSolidLbSource()) + "," +
			SqlHandler.delimitString(msdsBean.getSolidsSource()) + "," +
			SqlHandler.delimitString(msdsBean.getVaporPressureSource()) + "," +
			SqlHandler.delimitString(msdsBean.getVocLessH20ExemptSource()) + "," +
			msdsBean.getVocLbPerSolidLbLower() + "," +
			msdsBean.getVocLbPerSolidLbUpper() + "," +
			msdsBean.getVocLbPerSolidLbLowCalc() + "," +
			msdsBean.getVocLbPerSolidLbCalc() + "," +
			msdsBean.getVocLbPerSolidLbUpCalc() + "," +
			msdsBean.getVaporPressureLower() + "," +
			msdsBean.getVaporPressureUpper() + "," +
			msdsBean.getVocLessH2oExemptLowerCalc() + "," +
			msdsBean.getVocLessH2oExemptCalc() + "," +
			msdsBean.getVocLessH2oExemptUpperCalc() + "," +
			SqlHandler.delimitString(msdsBean.getVocLessH2oExemptUnitCalc()) + "," +
			SqlHandler.delimitString(msdsBean.getFrenchContent()) + "," +
			msdsBean.getVocCompVaporPressureMmhg() + "," +
			SqlHandler.delimitString(msdsBean.getLfcCode()) + "," +
			SqlHandler.delimitString(msdsBean.getHealthEffects()) + "," +
			SqlHandler.delimitString(msdsBean.getVaporDensity()) + "," +
			SqlHandler.delimitString(msdsBean.getNfpaSource()) + "," +
			SqlHandler.delimitString(msdsBean.getEvaporationRate()) + "," +
			SqlHandler.delimitString(msdsBean.getSpecificGravityBasis())  + "," +
			SqlHandler.delimitString(msdsBean.getSpecificGravitySource())  + "," +
			SqlHandler.delimitString(msdsBean.getSpecificGravityDetect())  + "," +
			msdsBean.getSpecificGravityLower() + "," +
			msdsBean.getSpecificGravityUpper() + "," +
			SqlHandler.delimitString(msdsBean.getDensityDetect())  + "," +
			SqlHandler.delimitString(msdsBean.getDensitySource())  + "," +
			msdsBean.getDensityUpper() + "," +
			SqlHandler.delimitString(msdsBean.getFlashPointDetect())  + "," +
			SqlHandler.delimitString(msdsBean.getFlashPointSource())  + "," +
			msdsBean.getFlashPointLower()  + "," +
			SqlHandler.delimitString(msdsBean.getFlashPointMethod())  + "," +
			msdsBean.getFlashPointUpper()  + "," +
			SqlHandler.delimitString(msdsBean.getBoilingPointDetect())  + "," +
			SqlHandler.delimitString(msdsBean.getBoilingPointSource())  + "," +
			msdsBean.getBoilingPointLower()  + "," +
			msdsBean.getBoilingPointUpper()  + "," +
			SqlHandler.delimitString(msdsBean.getBoilingPointUnit())  + "," +
			SqlHandler.delimitString(msdsBean.getBoilingPointDetail())  + "," +
			SqlHandler.delimitString(msdsBean.getPhDetail())  + "," +
			SqlHandler.delimitString(msdsBean.getPhSource())  + "," +
			SqlHandler.delimitString(msdsBean.getPhDetect())  + "," +
			msdsBean.getPhUpper()  + "," +
            msdsBean.getVerifiedBy() + "," +
            DateHandler.getOracleToDateFunction(msdsBean.getVerifiedOn()) + "," +
            SqlHandler.delimitString(msdsBean.getIdOnly()) + "," +
            msdsBean.getMsdsAuthorId() + "," +
            msdsBean.getMsdsIndexingPriorityId() + "," +
            SqlHandler.delimitString(msdsBean.getPhysicalStateSource()) + "," +
            SqlHandler.delimitString(msdsBean.getLabelCompanyName()) + "," +
            SqlHandler.delimitString(msdsBean.getLabelAddressLine1()) + "," +
            SqlHandler.delimitString(msdsBean.getLabelAddressLine2()) + "," +
            SqlHandler.delimitString(msdsBean.getLabelAddressLine3()) + "," +
            SqlHandler.delimitString(msdsBean.getLabelAddressLine4()) + "," +
            SqlHandler.delimitString(msdsBean.getLabelCity()) + "," +
            SqlHandler.delimitString(msdsBean.getLabelCountryAbbrev()) + "," +
            SqlHandler.delimitString(msdsBean.getLabelStateAbbrev()) + "," +
            SqlHandler.delimitString(msdsBean.getLabelZip()) + "," +
            SqlHandler.delimitString(msdsBean.getLabelPhone()) + "," +
            msdsBean.getMsdsId() + "," +
            //SqlHandler.delimitString(msdsBean.isGhsCompliantImage() ? "Y" : "N") + "," +
            SqlHandler.delimitString(msdsBean.isGhsHazard() ? "N" : "Y") + // reversed; web-page says non-hazardous, DB says hazardous
            ")";

		return sqlManager.update(conn, query);
	}

	//update
	public int update(MsdsBean msdsBean) throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = update(msdsBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int update(MsdsBean msdsBean, Connection conn) throws BaseException {
		StringBuilder query = new StringBuilder("update ").append(TABLE).append(" set ");
		query.append(ATTRIBUTE_CONTENT).append("=").append(SqlHandler.delimitString(msdsBean.getContent())).append(",");
		query.append(ATTRIBUTE_EMERGENCY_PHONE).append("=").append(SqlHandler.delimitString(msdsBean.getEmergencyPhone())).append(",");
		query.append(ATTRIBUTE_SPECIFIC_GRAVITY).append("=").append(msdsBean.getSpecificGravity()).append(",");
		query.append(ATTRIBUTE_HEALTH).append("=").append(SqlHandler.delimitString(msdsBean.getHealth())).append(",");
		query.append(ATTRIBUTE_FLAMMABILITY).append("=").append(SqlHandler.delimitString(msdsBean.getFlammability())).append(",");
		query.append(ATTRIBUTE_REACTIVITY).append("=").append(SqlHandler.delimitString(msdsBean.getReactivity())).append(",");
		query.append(ATTRIBUTE_SPECIFIC_HAZARD).append("=").append(SqlHandler.delimitString(msdsBean.getSpecificHazard())).append(",");
		query.append(ATTRIBUTE_COMPATIBILITY).append("=").append(SqlHandler.delimitString(msdsBean.getCompatibility())).append(",");
		query.append(ATTRIBUTE_STORAGE_TEMP).append("=").append(SqlHandler.delimitString(msdsBean.getStorageTemp())).append(",");
		query.append(ATTRIBUTE_PPE).append("=").append(SqlHandler.delimitString(msdsBean.getPpe())).append(",");
		query.append(ATTRIBUTE_SIGNAL_WORD).append("=").append(SqlHandler.delimitString(msdsBean.getSignalWord())).append(",");
		query.append(ATTRIBUTE_TARGET_ORGAN).append("=").append(SqlHandler.delimitString(msdsBean.getTargetOrgan())).append(",");
		query.append(ATTRIBUTE_ROUTE_OF_ENTRY).append("=").append(SqlHandler.delimitString(msdsBean.getRouteOfEntry())).append(",");
		query.append(ATTRIBUTE_SKIN).append("=").append(SqlHandler.delimitString(msdsBean.getSkin())).append(",");
		query.append(ATTRIBUTE_EYES).append("=").append(SqlHandler.delimitString(msdsBean.getEyes())).append(",");
		query.append(ATTRIBUTE_INHALATION).append("=").append(SqlHandler.delimitString(msdsBean.getInhalation())).append(",");
		query.append(ATTRIBUTE_INJECTION).append("=").append(SqlHandler.delimitString(msdsBean.getInjection())).append(",");
		query.append(ATTRIBUTE_BOILING_POINT_LOWER).append("=").append(msdsBean.getBoilingPointLower()).append(",");
		query.append(ATTRIBUTE_FLASH_POINT_LOWER).append("=").append(msdsBean.getFlashPointLower()).append(",");
		query.append(ATTRIBUTE_PH).append("=").append(msdsBean.getPh()).append(",");
		query.append(ATTRIBUTE_FREEZING_POINT).append("=").append(SqlHandler.delimitString(msdsBean.getFreezingPoint())).append(",");
		query.append(ATTRIBUTE_DENSITY).append("=").append(msdsBean.getDensity()).append(",");
		query.append(ATTRIBUTE_INGESTION).append("=").append(SqlHandler.delimitString(msdsBean.getIngestion())).append(",");
		query.append(ATTRIBUTE_ON_LINE).append("=").append(SqlHandler.delimitString(msdsBean.isOnLine() ? "Y" : "N")).append(",");
		query.append(ATTRIBUTE_DENSITY_UNIT).append("=").append(SqlHandler.delimitString(msdsBean.getDensityUnit())).append(",");
		query.append(ATTRIBUTE_PHYSICAL_STATE).append("=").append(SqlHandler.delimitString(msdsBean.getPhysicalState())).append(",");
		query.append(ATTRIBUTE_VOC_UNIT).append("=").append(SqlHandler.delimitString(msdsBean.getVocUnit())).append(",");
		query.append(ATTRIBUTE_TSCA_12B).append("=").append(SqlHandler.delimitString(msdsBean.getTsca12b())).append(",");
		query.append(ATTRIBUTE_SARA_311_312_ACUTE).append("=").append(SqlHandler.delimitString(msdsBean.getSara311312Acute())).append(",");
		query.append(ATTRIBUTE_SARA_311_312_CHRONIC).append("=").append(SqlHandler.delimitString(msdsBean.getSara311312Chronic())).append(",");
		query.append(ATTRIBUTE_SARA_311_312_FIRE).append("=").append(SqlHandler.delimitString(msdsBean.getSara311312Fire())).append(",");
		query.append(ATTRIBUTE_SARA_311_312_PRESSURE).append("=").append(SqlHandler.delimitString(msdsBean.getSara311312Pressure())).append(",");
		query.append(ATTRIBUTE_SARA_311_312_REACTIVITY).append("=").append(SqlHandler.delimitString(msdsBean.getSara311312Reactivity())).append(",");
		query.append(ATTRIBUTE_OSHA_HAZARD).append("=").append(SqlHandler.delimitString(msdsBean.getOshaHazard())).append(",");
		query.append(ATTRIBUTE_TSCA_LIST).append("=").append(SqlHandler.delimitString(msdsBean.getTscaList())).append(",");
		query.append(ATTRIBUTE_MIXTURE).append("=").append(SqlHandler.delimitString(msdsBean.getMixture())).append(",");
		query.append(ATTRIBUTE_VOC).append("=").append(msdsBean.getVoc()).append(",");
		query.append(ATTRIBUTE_VOC_LOWER).append("=").append(msdsBean.getVocLower()).append(",");
		query.append(ATTRIBUTE_VOC_UPPER).append("=").append(msdsBean.getVocUpper()).append(",");
		query.append(ATTRIBUTE_REVIEWED_BY).append("=").append(SqlHandler.delimitString(msdsBean.getReviewedBy())).append(",");
		query.append(ATTRIBUTE_REVIEW_DATE).append("=sysdate,");
		query.append(ATTRIBUTE_REMARK).append("=").append(SqlHandler.delimitString(msdsBean.getRemark())).append(",");
		query.append(ATTRIBUTE_FLASH_POINT_UNIT).append("=").append(SqlHandler.delimitString(msdsBean.getFlashPointUnit())).append(",");
		query.append(ATTRIBUTE_SOLIDS).append("=").append(msdsBean.getSolids()).append(",");
		query.append(ATTRIBUTE_SOLIDS_LOWER).append("=").append(msdsBean.getSolidsLower()).append(",");
		query.append(ATTRIBUTE_SOLIDS_UPPER).append("=").append(msdsBean.getSolidsUpper()).append(",");
		query.append(ATTRIBUTE_SOLIDS_UNIT).append("=").append(SqlHandler.delimitString(msdsBean.getSolidsUnit())).append(",");
		query.append(ATTRIBUTE_SOLIDS_PERCENT).append("=").append(msdsBean.getSolidsPercent()).append(",");
		query.append(ATTRIBUTE_SOLIDS_LOWER_PERCENT).append("=").append(msdsBean.getSolidsLowerPercent()).append(",");
		query.append(ATTRIBUTE_SOLIDS_UPPER_PERCENT).append("=").append(msdsBean.getSolidsUpperPercent()).append(",");
		query.append(ATTRIBUTE_VOC_LOWER_PERCENT).append("=").append(msdsBean.getVocLowerPercent()).append(",");
		query.append(ATTRIBUTE_VOC_UPPER_PERCENT).append("=").append(msdsBean.getVocUpperPercent()).append(",");
		query.append(ATTRIBUTE_VOC_PERCENT).append("=").append(msdsBean.getVocPercent()).append(",");
		query.append(ATTRIBUTE_HMIS_HEALTH).append("=").append(SqlHandler.delimitString(msdsBean.getHmisHealth())).append(",");
		query.append(ATTRIBUTE_HMIS_FLAMMABILITY).append("=").append(SqlHandler.delimitString(msdsBean.getHmisFlammability())).append(",");
		query.append(ATTRIBUTE_HMIS_REACTIVITY).append("=").append(SqlHandler.delimitString(msdsBean.getHmisReactivity())).append(",");
		query.append(ATTRIBUTE_HMIS_CHRONIC).append("=").append(SqlHandler.delimitString(msdsBean.getHmisChronic())).append(",");
		query.append(ATTRIBUTE_HMIS_SOURCE).append("=").append(SqlHandler.delimitString(msdsBean.getHmisSource())).append(",");
		query.append(ATTRIBUTE_PERSONAL_PROTECTION).append("=").append(SqlHandler.delimitString(msdsBean.getPersonalProtection())).append(",");
		query.append(ATTRIBUTE_ALT_DATA_SOURCE).append("=").append(SqlHandler.delimitString(msdsBean.getAltDataSource())).append(",");
		query.append(ATTRIBUTE_VAPOR_PRESSURE_DETECT).append("=").append(SqlHandler.delimitString(msdsBean.getVaporPressureDetect())).append(",");
		query.append(ATTRIBUTE_VAPOR_PRESSURE).append("=").append(msdsBean.getVaporPressure()).append(",");
		query.append(ATTRIBUTE_VAPOR_PRESSURE_UNIT).append("=").append(SqlHandler.delimitString(msdsBean.getVaporPressureUnit())).append(",");
		query.append(ATTRIBUTE_VAPOR_PRESSURE_TEMP).append("=").append(msdsBean.getVaporPressureTemp()).append(",");
		query.append(ATTRIBUTE_VAPOR_PRESSURE_TEMP_UNIT).append("=").append(SqlHandler.delimitString(msdsBean.getVaporPressureTempUnit())).append(",");
		query.append(ATTRIBUTE_INSERT_DATE).append("=").append(DateHandler.getOracleToDateFunction(msdsBean.getInsertDate())).append(",");
		query.append(ATTRIBUTE_COMPOSITION_CHANGED).append("=").append(SqlHandler.delimitString(msdsBean.getCompositionChanged())).append(",");
		query.append(ATTRIBUTE_VOC_LESS_H2O_EXEMPT).append("=").append(msdsBean.getVocLessH2oExempt()).append(",");
		query.append(ATTRIBUTE_VOC_LESS_H2O_EXEMPT_UNIT).append("=").append(SqlHandler.delimitString(msdsBean.getVocLessH2oExemptUnit())).append(",");
		query.append(ATTRIBUTE_VOC_LB_PER_SOLID_LB).append("=").append(msdsBean.getVocLbPerSolidLb()).append(",");
		query.append(ATTRIBUTE_VOC_LESS_H2O_EXEMPT_LOWER).append("=").append(msdsBean.getVocLessH2oExemptLower()).append(",");
		query.append(ATTRIBUTE_VOC_LESS_H2O_EXEMPT_UPPER).append("=").append(msdsBean.getVocLessH2oExemptUpper()).append(",");
		query.append(ATTRIBUTE_VOC_SOURCE).append("=").append(SqlHandler.delimitString(msdsBean.getVocSource())).append(",");
		query.append(ATTRIBUTE_VOC_LESS_H2O_EXEMPT_SOURCE).append("=").append(SqlHandler.delimitString(msdsBean.getVocLessH2oExemptSource())).append(",");
		query.append(ATTRIBUTE_VOC_LB_PER_SOLID_LB_SOURCE).append("=").append(SqlHandler.delimitString(msdsBean.getVocLbPerSolidLbSource())).append(",");
		query.append(ATTRIBUTE_SOLIDS_SOURCE).append("=").append(SqlHandler.delimitString(msdsBean.getSolidsSource())).append(",");
		query.append(ATTRIBUTE_VAPOR_PRESSURE_SOURCE).append("=").append(SqlHandler.delimitString(msdsBean.getVaporPressureSource())).append(",");
		query.append(ATTRIBUTE_VOC_LESS_H20_EXEMPT_SOURCE).append("=").append(SqlHandler.delimitString(msdsBean.getVocLessH20ExemptSource())).append(",");
		query.append(ATTRIBUTE_VOC_LB_PER_SOLID_LB_LOWER).append("=").append(msdsBean.getVocLbPerSolidLbLower()).append(",");
		query.append(ATTRIBUTE_VOC_LB_PER_SOLID_LB_UPPER).append("=").append(msdsBean.getVocLbPerSolidLbUpper()).append(",");
		query.append(ATTRIBUTE_VOC_LB_PER_SOLID_LB_LOW_CALC).append("=").append(msdsBean.getVocLbPerSolidLbLowCalc()).append(",");
		query.append(ATTRIBUTE_VOC_LB_PER_SOLID_LB_CALC).append("=").append(msdsBean.getVocLbPerSolidLbCalc()).append(",");
		query.append(ATTRIBUTE_VOC_LB_PER_SOLID_LB_UP_CALC).append("=").append(msdsBean.getVocLbPerSolidLbUpCalc()).append(",");
		query.append(ATTRIBUTE_VAPOR_PRESSURE_LOWER).append("=").append(msdsBean.getVaporPressureLower()).append(",");
		query.append(ATTRIBUTE_VAPOR_PRESSURE_UPPER).append("=").append(msdsBean.getVaporPressureUpper()).append(",");
		query.append(ATTRIBUTE_VOC_LESS_H2O_EXEMPT_LOWER_CALC).append("=").append(msdsBean.getVocLessH2oExemptLowerCalc()).append(",");
		query.append(ATTRIBUTE_VOC_LESS_H2O_EXEMPT_CALC).append("=").append(msdsBean.getVocLessH2oExemptCalc()).append(",");
		query.append(ATTRIBUTE_VOC_LESS_H2O_EXEMPT_UPPER_CALC).append("=").append(msdsBean.getVocLessH2oExemptUpperCalc()).append(",");
		query.append(ATTRIBUTE_VOC_LESS_H2O_EXEMPT_UNIT_CALC).append("=").append(SqlHandler.delimitString(msdsBean.getVocLessH2oExemptUnitCalc())).append(",");
		query.append(ATTRIBUTE_FRENCH_CONTENT).append("=").append(SqlHandler.delimitString(msdsBean.getFrenchContent())).append(",");
		query.append(ATTRIBUTE_VOC_COMP_VAPOR_PRESSURE_MMHG).append("=").append(msdsBean.getVocCompVaporPressureMmhg());
		query.append(ATTRIBUTE_CHRONIC).append("=").append(SqlHandler.delimitString(msdsBean.getChronic())).append(",");
		query.append(ATTRIBUTE_LFC_CODE).append("=").append(SqlHandler.delimitString(msdsBean.getLfcCode())).append(",");
		query.append(ATTRIBUTE_POLYMERIZE).append("=").append(SqlHandler.delimitString(msdsBean.getPolymerize())).append(",");
		query.append(ATTRIBUTE_INCOMPATIBLE).append("=").append(SqlHandler.delimitString(msdsBean.getIncompatible())).append(",");
		query.append(ATTRIBUTE_CORROSIVE).append("=").append(SqlHandler.delimitString(msdsBean.getCorrosive())).append(",");
		query.append(ATTRIBUTE_HEALTH_EFFECTS).append("=").append(SqlHandler.delimitString(msdsBean.getHealthEffects())).append(",");
		query.append(ATTRIBUTE_STABLE).append("=").append(SqlHandler.delimitString(msdsBean.getStable())).append(",");
		query.append(ATTRIBUTE_VAPOR_DENSITY).append("=").append(SqlHandler.delimitString(msdsBean.getVaporDensity())).append(",");
		query.append(ATTRIBUTE_NFPA_SOURCE).append("=").append(SqlHandler.delimitString(msdsBean.getNfpaSource())).append(",");
		query.append(ATTRIBUTE_EVAPORATION_RATE).append("=").append(SqlHandler.delimitString(msdsBean.getEvaporationRate())).append(",");
		query.append(ATTRIBUTE_SPECIFIC_GRAVITY_BASIS).append("=").append(SqlHandler.delimitString(msdsBean.getSpecificGravityBasis())).append(",");
		query.append(ATTRIBUTE_SPECIFIC_GRAVITY_SOURCE).append("=").append(SqlHandler.delimitString(msdsBean.getSpecificGravitySource())).append(",");
		query.append(ATTRIBUTE_SPECIFIC_GRAVITY_DETECT).append("=").append(SqlHandler.delimitString(msdsBean.getSpecificGravityDetect())).append(",");
		query.append(ATTRIBUTE_SPECIFIC_GRAVITY_LOWER).append("=").append(msdsBean.getSpecificGravityLower()).append(",");
		query.append(ATTRIBUTE_SPECIFIC_GRAVITY_UPPER).append("=").append(msdsBean.getSpecificGravityUpper()).append(",");
		query.append(ATTRIBUTE_DENSITY_DETECT).append("=").append(SqlHandler.delimitString(msdsBean.getDensityDetect())).append(",");
		query.append(ATTRIBUTE_DENSITY_SOURCE).append("=").append(SqlHandler.delimitString(msdsBean.getDensitySource())).append(",");
		query.append(ATTRIBUTE_DENSITY_UPPER).append("=").append(msdsBean.getDensityUpper()).append(",");
		query.append(ATTRIBUTE_FLASH_POINT_DETECT).append("=").append(SqlHandler.delimitString(msdsBean.getFlashPointDetect())).append(",");
		query.append(ATTRIBUTE_FLASH_POINT_SOURCE).append("=").append(SqlHandler.delimitString(msdsBean.getFlashPointSource())).append(",");	
		query.append(ATTRIBUTE_FLASH_POINT_LOWER).append("=").append(msdsBean.getFlashPointLower()).append(",");
		query.append(ATTRIBUTE_FLASH_POINT_METHOD).append("=").append(SqlHandler.delimitString(msdsBean.getFlashPointMethod())).append(",");
		query.append(ATTRIBUTE_FLASH_POINT_UPPER).append("=").append(msdsBean.getFlashPointUpper()).append(",");
		query.append(ATTRIBUTE_BOILING_POINT_DETECT).append("=").append(SqlHandler.delimitString(msdsBean.getBoilingPointDetect())).append(",");
		query.append(ATTRIBUTE_BOILING_POINT_SOURCE).append("=").append(SqlHandler.delimitString(msdsBean.getBoilingPointSource())).append(",");
		query.append(ATTRIBUTE_BOILING_POINT_UPPER).append("=").append(msdsBean.getBoilingPointUpper()).append(","); 
		query.append(ATTRIBUTE_BOILING_POINT_UNIT).append("=").append(SqlHandler.delimitString(msdsBean.getBoilingPointUnit())).append(",");
		query.append(ATTRIBUTE_BOILING_POINT_DETAIL).append("=").append(SqlHandler.delimitString(msdsBean.getBoilingPointDetail())).append(",");
		query.append(ATTRIBUTE_PH_DETAIL).append("=").append(SqlHandler.delimitString(msdsBean.getPhDetail())).append(",");
		query.append(ATTRIBUTE_PH_SOURCE).append("=").append(SqlHandler.delimitString(msdsBean.getPhSource())).append(",");
		query.append(ATTRIBUTE_PH_DETECT).append("=").append(SqlHandler.delimitString(msdsBean.getPhDetect())).append(","); 
		query.append(ATTRIBUTE_PH_UPPER).append("=").append(msdsBean.getPhUpper()).append(",");
		query.append(ATTRIBUTE_OXIDIZER).append("=").append(SqlHandler.delimitString(msdsBean.getOxidizer())).append(",");
		query.append(ATTRIBUTE_VERIFIED_BY).append("=").append(msdsBean.getVerifiedBy()).append(",");
        query.append(ATTRIBUTE_VERIFIED_ON).append("=").append(DateHandler.getOracleToDateFunction(msdsBean.getVerifiedOn())).append(",");
        query.append(ATTRIBUTE_ID_ONLY).append("=").append(SqlHandler.delimitString(msdsBean.getIdOnly())).append(",");
        query.append(ATTRIBUTE_MSDS_AUTHOR_ID).append("=").append(msdsBean.getMsdsAuthorId()).append(",");
        query.append(ATTRIBUTE_MSDS_INDEXING_PRIORITY_ID).append("=").append(msdsBean.getMsdsIndexingPriorityId()).append(",");
        query.append(ATTRIBUTE_PHYSICAL_STATE_SOURCE).append("=").append(SqlHandler.delimitString(msdsBean.getPhysicalStateSource())).append(",");
        query.append(ATTRIBUTE_LABEL_COMPANY_NAME).append("=").append(SqlHandler.delimitString(msdsBean.getLabelCompanyName())).append(",");
        query.append(ATTRIBUTE_LABEL_ADDRESS_LINE_1).append("=").append(SqlHandler.delimitString(msdsBean.getLabelAddressLine1())).append(",");
        query.append(ATTRIBUTE_LABEL_ADDRESS_LINE_2).append("=").append(SqlHandler.delimitString(msdsBean.getLabelAddressLine2())).append(",");
        query.append(ATTRIBUTE_LABEL_ADDRESS_LINE_3).append("=").append(SqlHandler.delimitString(msdsBean.getLabelAddressLine3())).append(",");
        query.append(ATTRIBUTE_LABEL_ADDRESS_LINE_4).append("=").append(SqlHandler.delimitString(msdsBean.getLabelAddressLine4())).append(",");
        query.append(ATTRIBUTE_LABEL_CITY).append("=").append(SqlHandler.delimitString(msdsBean.getLabelCity())).append(",");
        query.append(ATTRIBUTE_LABEL_COUNTRY_ABBREV).append("=").append(SqlHandler.delimitString(msdsBean.getLabelCountryAbbrev())).append(",");
        query.append(ATTRIBUTE_LABEL_STATE_ABBREV).append("=").append(SqlHandler.delimitString(msdsBean.getLabelStateAbbrev())).append(",");
        query.append(ATTRIBUTE_LABEL_ZIP).append("=").append(SqlHandler.delimitString(msdsBean.getLabelZip())).append(",");
        query.append(ATTRIBUTE_LABEL_PHONE).append("=").append(SqlHandler.delimitString(msdsBean.getLabelPhone())).append(",");
        query.append(ATTRIBUTE_MSDS_ID).append("=").append(msdsBean.getMsdsId()).append(",");
        //query.append(ATTRIBUTE_GHS_COMPLIANT_IMAGE).append("=").append(SqlHandler.delimitString(msdsBean.isGhsCompliantImage() ? "Y" : "N")).append(",");
        query.append(ATTRIBUTE_GHS_HAZARD).append("=").append(SqlHandler.delimitString(msdsBean.isGhsHazard() ? "N" : "Y"));
        query.append(" ").append(getWhereClause(getKeyCriteria(msdsBean)));

		return new SqlManager().update(conn, query.toString());
	}

	public int updateForCatalogAdd(MsdsBean msdsBean) throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = updateForCatalogAdd(msdsBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
		return result;
	}

	public int updateForCatalogAdd(MsdsBean msdsBean, Connection conn) throws BaseException {
		StringBuilder query = new StringBuilder("update ").append(TABLE).append(" set ");
		query.append(ATTRIBUTE_CONTENT).append("=").append(SqlHandler.delimitString(msdsBean.getContent())).append(",");
		//query.append(ATTRIBUTE_EMERGENCY_PHONE).append("=").append(SqlHandler.delimitString(msdsBean.getEmergencyPhone())).append(",");
		query.append(ATTRIBUTE_SPECIFIC_GRAVITY).append("=").append(msdsBean.getSpecificGravity()).append(",");
		query.append(ATTRIBUTE_HEALTH).append("=").append(SqlHandler.delimitString(msdsBean.getHealth())).append(",");
		query.append(ATTRIBUTE_FLAMMABILITY).append("=").append(SqlHandler.delimitString(msdsBean.getFlammability())).append(",");
		query.append(ATTRIBUTE_REACTIVITY).append("=").append(SqlHandler.delimitString(msdsBean.getReactivity())).append(",");
		query.append(ATTRIBUTE_SPECIFIC_HAZARD).append("=").append(SqlHandler.delimitString(msdsBean.getSpecificHazard())).append(",");
		//query.append(ATTRIBUTE_COMPATIBILITY).append("=").append(SqlHandler.delimitString(msdsBean.getCompatibility())).append(",");
		//query.append(ATTRIBUTE_STORAGE_TEMP).append("=").append(SqlHandler.delimitString(msdsBean.getStorageTemp())).append(",");
		query.append(ATTRIBUTE_PPE).append("=").append(SqlHandler.delimitString(msdsBean.getPpe())).append(",");
		query.append(ATTRIBUTE_SIGNAL_WORD).append("=").append(SqlHandler.delimitString(msdsBean.getSignalWord())).append(",");
		//query.append(ATTRIBUTE_TARGET_ORGAN).append("=").append(SqlHandler.delimitString(msdsBean.getTargetOrgan())).append(",");
		//query.append(ATTRIBUTE_ROUTE_OF_ENTRY).append("=").append(SqlHandler.delimitString(msdsBean.getRouteOfEntry())).append(",");
		//query.append(ATTRIBUTE_SKIN).append("=").append(SqlHandler.delimitString(msdsBean.getSkin())).append(",");
		//query.append(ATTRIBUTE_EYES).append("=").append(SqlHandler.delimitString(msdsBean.getEyes())).append(",");
		//query.append(ATTRIBUTE_INHALATION).append("=").append(SqlHandler.delimitString(msdsBean.getInhalation())).append(",");
		//query.append(ATTRIBUTE_INJECTION).append("=").append(SqlHandler.delimitString(msdsBean.getInjection())).append(",");
		//query.append(ATTRIBUTE_BOILING_POINT).append("=").append(SqlHandler.delimitString(msdsBean.getBoilingPoint())).append(",");
		query.append(ATTRIBUTE_PH).append("=").append(msdsBean.getPh()).append(",");
		//query.append(ATTRIBUTE_FREEZING_POINT).append("=").append(SqlHandler.delimitString(msdsBean.getFreezingPoint())).append(",");
		query.append(ATTRIBUTE_DENSITY).append("=").append(msdsBean.getDensity()).append(",");
		//query.append(ATTRIBUTE_INGESTION).append("=").append(SqlHandler.delimitString(msdsBean.getIngestion())).append(",");
		query.append(ATTRIBUTE_ON_LINE).append("=").append(SqlHandler.delimitString(msdsBean.isOnLine()?"Y":"N")).append(",");
		query.append(ATTRIBUTE_DENSITY_UNIT).append("=").append(SqlHandler.delimitString(msdsBean.getDensityUnit())).append(",");
		query.append(ATTRIBUTE_PHYSICAL_STATE).append("=").append(SqlHandler.delimitString(msdsBean.getPhysicalState())).append(",");
		query.append(ATTRIBUTE_VOC_UNIT).append("=").append(SqlHandler.delimitString(msdsBean.getVocUnit())).append(",");
		//query.append(ATTRIBUTE_TSCA_12B).append("=").append(SqlHandler.delimitString(msdsBean.getTsca12b())).append(",");
		query.append(ATTRIBUTE_SARA_311_312_ACUTE).append("=").append(SqlHandler.delimitString(msdsBean.getSara311312Acute())).append(",");
		query.append(ATTRIBUTE_SARA_311_312_CHRONIC).append("=").append(SqlHandler.delimitString(msdsBean.getSara311312Chronic())).append(",");
		query.append(ATTRIBUTE_SARA_311_312_FIRE).append("=").append(SqlHandler.delimitString(msdsBean.getSara311312Fire())).append(",");
		query.append(ATTRIBUTE_SARA_311_312_PRESSURE).append("=").append(SqlHandler.delimitString(msdsBean.getSara311312Pressure())).append(",");
		query.append(ATTRIBUTE_SARA_311_312_REACTIVITY).append("=").append(SqlHandler.delimitString(msdsBean.getSara311312Reactivity())).append(",");
		//query.append(ATTRIBUTE_OSHA_HAZARD).append("=").append(SqlHandler.delimitString(msdsBean.getOshaHazard())).append(",");
		//query.append(ATTRIBUTE_TSCA_LIST).append("=").append(SqlHandler.delimitString(msdsBean.getTscaList())).append(",");
		//query.append(ATTRIBUTE_MIXTURE).append("=").append(SqlHandler.delimitString(msdsBean.getMixture())).append(",");
		query.append(ATTRIBUTE_VOC).append("=").append(msdsBean.getVoc()).append(",");
		query.append(ATTRIBUTE_VOC_LOWER).append("=").append(msdsBean.getVocLower()).append(",");
		query.append(ATTRIBUTE_VOC_UPPER).append("=").append(msdsBean.getVocUpper()).append(",");
		query.append(ATTRIBUTE_REVIEWED_BY).append("=").append(SqlHandler.delimitString(msdsBean.getReviewedBy())).append(",");
		query.append(ATTRIBUTE_REVIEW_DATE).append("=sysdate,");
		query.append(ATTRIBUTE_REMARK).append("=").append(SqlHandler.delimitString(msdsBean.getRemark())).append(",");
		query.append(ATTRIBUTE_FLASH_POINT_UNIT).append("=").append(SqlHandler.delimitString(msdsBean.getFlashPointUnit())).append(",");
		query.append(ATTRIBUTE_SOLIDS).append("=").append(msdsBean.getSolids()).append(",");
		query.append(ATTRIBUTE_SOLIDS_LOWER).append("=").append(msdsBean.getSolidsLower()).append(",");
		query.append(ATTRIBUTE_SOLIDS_UPPER).append("=").append(msdsBean.getSolidsUpper()).append(",");
		query.append(ATTRIBUTE_SOLIDS_UNIT).append("=").append(SqlHandler.delimitString(msdsBean.getSolidsUnit())).append(",");
		//query.append(ATTRIBUTE_SOLIDS_PERCENT).append("=").append(msdsBean.getSolidsPercent()).append(",");
		//query.append(ATTRIBUTE_SOLIDS_LOWER_PERCENT).append("=").append(msdsBean.getSolidsLowerPercent()).append(",");
		//query.append(ATTRIBUTE_SOLIDS_UPPER_PERCENT).append("=").append(msdsBean.getSolidsUpperPercent()).append(",");
		//query.append(ATTRIBUTE_VOC_LOWER_PERCENT).append("=").append(msdsBean.getVocLowerPercent()).append(",");
		//query.append(ATTRIBUTE_VOC_UPPER_PERCENT).append("=").append(msdsBean.getVocUpperPercent()).append(",");
		//query.append(ATTRIBUTE_VOC_PERCENT).append("=").append(msdsBean.getVocPercent()).append(",");
		query.append(ATTRIBUTE_HMIS_HEALTH).append("=").append(SqlHandler.delimitString(msdsBean.getHmisHealth())).append(",");
		query.append(ATTRIBUTE_HMIS_FLAMMABILITY).append("=").append(SqlHandler.delimitString(msdsBean.getHmisFlammability())).append(",");
		query.append(ATTRIBUTE_HMIS_REACTIVITY).append("=").append(SqlHandler.delimitString(msdsBean.getHmisReactivity())).append(",");
		query.append(ATTRIBUTE_HMIS_CHRONIC).append("=").append(SqlHandler.delimitString(msdsBean.getHmisChronic())).append(",");
		query.append(ATTRIBUTE_HMIS_SOURCE).append("=").append(SqlHandler.delimitString(msdsBean.getHmisSource())).append(",");
		query.append(ATTRIBUTE_PERSONAL_PROTECTION).append("=").append(SqlHandler.delimitString(msdsBean.getPersonalProtection())).append(",");
		query.append(ATTRIBUTE_ALT_DATA_SOURCE).append("=").append(SqlHandler.delimitString(msdsBean.getAltDataSource())).append(",");
		query.append(ATTRIBUTE_VAPOR_PRESSURE_DETECT).append("=").append(SqlHandler.delimitString(msdsBean.getVaporPressureDetect())).append(",");
		query.append(ATTRIBUTE_VAPOR_PRESSURE).append("=").append(msdsBean.getVaporPressure()).append(",");
		query.append(ATTRIBUTE_VAPOR_PRESSURE_UNIT).append("=").append(SqlHandler.delimitString(msdsBean.getVaporPressureUnit())).append(",");
		query.append(ATTRIBUTE_VAPOR_PRESSURE_TEMP).append("=").append(msdsBean.getVaporPressureTemp()).append(",");
		query.append(ATTRIBUTE_VAPOR_PRESSURE_TEMP_UNIT).append("=").append(SqlHandler.delimitString(msdsBean.getVaporPressureTempUnit())).append(",");
		//query.append(ATTRIBUTE_INSERT_DATE).append("=").append(DateHandler.getOracleToDateFunction(msdsBean.getInsertDate())).append(",");
		//query.append(ATTRIBUTE_COMPOSITION_CHANGED).append("=").append(SqlHandler.delimitString(msdsBean.getCompositionChanged())).append(",");
		query.append(ATTRIBUTE_VOC_LESS_H2O_EXEMPT).append("=").append(msdsBean.getVocLessH2oExempt()).append(",");
		query.append(ATTRIBUTE_VOC_LESS_H2O_EXEMPT_UNIT).append("=").append(SqlHandler.delimitString(msdsBean.getVocLessH2oExemptUnit())).append(",");
		//query.append(ATTRIBUTE_VOC_LB_PER_SOLID_LB).append("=").append(msdsBean.getVocLbPerSolidLb()).append(",");
		query.append(ATTRIBUTE_VOC_LESS_H2O_EXEMPT_LOWER).append("=").append(msdsBean.getVocLessH2oExemptLower()).append(",");
		query.append(ATTRIBUTE_VOC_LESS_H2O_EXEMPT_UPPER).append("=").append(msdsBean.getVocLessH2oExemptUpper()).append(",");
		query.append(ATTRIBUTE_VOC_SOURCE).append("=").append(SqlHandler.delimitString(msdsBean.getVocSource())).append(",");
		query.append(ATTRIBUTE_VOC_LESS_H2O_EXEMPT_SOURCE).append("=").append(SqlHandler.delimitString(msdsBean.getVocLessH2oExemptSource())).append(",");
		//query.append(ATTRIBUTE_VOC_LB_PER_SOLID_LB_SOURCE).append("=").append(SqlHandler.delimitString(msdsBean.getVocLbPerSolidLbSource())).append(",");
		query.append(ATTRIBUTE_SOLIDS_SOURCE).append("=").append(SqlHandler.delimitString(msdsBean.getSolidsSource())).append(",");
		query.append(ATTRIBUTE_VAPOR_PRESSURE_SOURCE).append("=").append(SqlHandler.delimitString(msdsBean.getVaporPressureSource())).append(",");
		query.append(ATTRIBUTE_VOC_LESS_H20_EXEMPT_SOURCE).append("=").append(SqlHandler.delimitString(msdsBean.getVocLessH20ExemptSource())).append(",");
		//query.append(ATTRIBUTE_VOC_LB_PER_SOLID_LB_LOWER).append("=").append(msdsBean.getVocLbPerSolidLbLower()).append(",");
		//query.append(ATTRIBUTE_VOC_LB_PER_SOLID_LB_UPPER).append("=").append(msdsBean.getVocLbPerSolidLbUpper()).append(",");
		//query.append(ATTRIBUTE_VOC_LB_PER_SOLID_LB_LOW_CALC).append("=").append(msdsBean.getVocLbPerSolidLbLowCalc()).append(",");
		//query.append(ATTRIBUTE_VOC_LB_PER_SOLID_LB_CALC).append("=").append(msdsBean.getVocLbPerSolidLbCalc()).append(",");
		//query.append(ATTRIBUTE_VOC_LB_PER_SOLID_LB_UP_CALC).append("=").append(msdsBean.getVocLbPerSolidLbUpCalc()).append(",");
		query.append(ATTRIBUTE_VAPOR_PRESSURE_LOWER).append("=").append(msdsBean.getVaporPressureLower()).append(",");
		query.append(ATTRIBUTE_VAPOR_PRESSURE_UPPER).append("=").append(msdsBean.getVaporPressureUpper()).append(",");
		//query.append(ATTRIBUTE_VOC_LESS_H2O_EXEMPT_LOWER_CALC).append("=").append(msdsBean.getVocLessH2oExemptLowerCalc()).append(",");
		//query.append(ATTRIBUTE_VOC_LESS_H2O_EXEMPT_CALC).append("=").append(msdsBean.getVocLessH2oExemptCalc()).append(",");
		//query.append(ATTRIBUTE_VOC_LESS_H2O_EXEMPT_UPPER_CALC).append("=").append(msdsBean.getVocLessH2oExemptUpperCalc()).append(",");
		//query.append(ATTRIBUTE_VOC_LESS_H2O_EXEMPT_UNIT_CALC).append("=").append(SqlHandler.delimitString(msdsBean.getVocLessH2oExemptUnitCalc())).append(",");
		//query.append(ATTRIBUTE_FRENCH_CONTENT).append("=").append(SqlHandler.delimitString(msdsBean.getFrenchContent())).append(",");
		//query.append(ATTRIBUTE_VOC_COMP_VAPOR_PRESSURE_MMHG).append("=").append(msdsBean.getVocCompVaporPressureMmhg());
		query.append(ATTRIBUTE_LFC_CODE).append("=").append(SqlHandler.delimitString(msdsBean.getLfcCode())).append(",");
		query.append(ATTRIBUTE_HEALTH_EFFECTS).append("=").append(SqlHandler.delimitString(msdsBean.getHealthEffects())).append(",");
		query.append(ATTRIBUTE_VAPOR_DENSITY).append("=").append(SqlHandler.delimitString(msdsBean.getVaporDensity())).append(",");
		query.append(ATTRIBUTE_NFPA_SOURCE).append("=").append(SqlHandler.delimitString(msdsBean.getNfpaSource())).append(",");
		query.append(ATTRIBUTE_EVAPORATION_RATE).append("=").append(SqlHandler.delimitString(msdsBean.getEvaporationRate())).append(",");
		query.append(ATTRIBUTE_SPECIFIC_GRAVITY_BASIS).append("=").append(SqlHandler.delimitString(msdsBean.getSpecificGravityBasis())).append(",");
		query.append(ATTRIBUTE_SPECIFIC_GRAVITY_SOURCE).append("=").append(SqlHandler.delimitString(msdsBean.getSpecificGravitySource())).append(",");
		query.append(ATTRIBUTE_SPECIFIC_GRAVITY_DETECT).append("=").append(SqlHandler.delimitString(msdsBean.getSpecificGravityDetect())).append(",");
		query.append(ATTRIBUTE_SPECIFIC_GRAVITY_LOWER).append("=").append(msdsBean.getSpecificGravityLower()).append(",");
		query.append(ATTRIBUTE_SPECIFIC_GRAVITY_UPPER).append("=").append(msdsBean.getSpecificGravityUpper()).append(",");
		query.append(ATTRIBUTE_DENSITY_DETECT).append("=").append(SqlHandler.delimitString(msdsBean.getDensityDetect())).append(",");
		query.append(ATTRIBUTE_DENSITY_SOURCE).append("=").append(SqlHandler.delimitString(msdsBean.getDensitySource())).append(",");
		query.append(ATTRIBUTE_DENSITY_UPPER).append("=").append(msdsBean.getDensityUpper()).append(",");
		query.append(ATTRIBUTE_FLASH_POINT_DETECT).append("=").append(SqlHandler.delimitString(msdsBean.getFlashPointDetect())).append(",");
		query.append(ATTRIBUTE_FLASH_POINT_SOURCE).append("=").append(SqlHandler.delimitString(msdsBean.getFlashPointSource())).append(",");
		query.append(ATTRIBUTE_FLASH_POINT_LOWER).append("=").append(msdsBean.getFlashPointLower()).append(",");
		query.append(ATTRIBUTE_FLASH_POINT_METHOD).append("=").append(SqlHandler.delimitString(msdsBean.getFlashPointMethod())).append(",");
		query.append(ATTRIBUTE_FLASH_POINT_UPPER).append("=").append(msdsBean.getFlashPointUpper()).append(",");
		query.append(ATTRIBUTE_BOILING_POINT_DETECT).append("=").append(SqlHandler.delimitString(msdsBean.getBoilingPointDetect())).append(",");
		query.append(ATTRIBUTE_BOILING_POINT_SOURCE).append("=").append(SqlHandler.delimitString(msdsBean.getBoilingPointSource())).append(","); 
		query.append(ATTRIBUTE_BOILING_POINT_UPPER).append("=").append(msdsBean.getBoilingPointUpper()).append(","); 
		query.append(ATTRIBUTE_BOILING_POINT_UNIT).append("=").append(SqlHandler.delimitString(msdsBean.getBoilingPointUnit())).append(",");
		query.append(ATTRIBUTE_BOILING_POINT_DETAIL).append("=").append(SqlHandler.delimitString(msdsBean.getBoilingPointDetail())).append(",");
		query.append(ATTRIBUTE_BOILING_POINT_LOWER).append("=").append(msdsBean.getBoilingPointLower()).append(",");
		query.append(ATTRIBUTE_PH_DETAIL).append("=").append(SqlHandler.delimitString(msdsBean.getPhDetail())).append(",");
		query.append(ATTRIBUTE_PH_SOURCE).append("=").append(SqlHandler.delimitString(msdsBean.getPhSource())).append(",");
		query.append(ATTRIBUTE_PH_DETECT).append("=").append(SqlHandler.delimitString(msdsBean.getPhDetect())).append(","); 
		query.append(ATTRIBUTE_PH_UPPER).append("=").append(msdsBean.getPhUpper()).append(",");
		query.append(ATTRIBUTE_VERIFIED_BY).append("=").append(msdsBean.getVerifiedBy()).append(",");
        query.append(ATTRIBUTE_VERIFIED_ON).append("=").append(DateHandler.getOracleToDateFunction(msdsBean.getVerifiedOn())).append(",");
        query.append(ATTRIBUTE_ID_ONLY).append("=").append(SqlHandler.delimitString(msdsBean.getIdOnly())).append(",");
       // query.append(ATTRIBUTE_MSDS_AUTHOR_ID).append("=").append(msdsBean.getMsdsAuthorId()).append(",");
        query.append(ATTRIBUTE_MSDS_INDEXING_PRIORITY_ID).append("=").append(msdsBean.getMsdsIndexingPriorityId()).append(",");
        query.append(ATTRIBUTE_PHYSICAL_STATE_SOURCE).append("=").append(SqlHandler.delimitString(msdsBean.getPhysicalStateSource())).append(",");
        query.append(ATTRIBUTE_LABEL_COMPANY_NAME).append("=").append(SqlHandler.delimitString(msdsBean.getLabelCompanyName())).append(",");
        query.append(ATTRIBUTE_LABEL_ADDRESS_LINE_1).append("=").append(SqlHandler.delimitString(msdsBean.getLabelAddressLine1())).append(",");
        query.append(ATTRIBUTE_LABEL_ADDRESS_LINE_2).append("=").append(SqlHandler.delimitString(msdsBean.getLabelAddressLine2())).append(",");
        query.append(ATTRIBUTE_LABEL_ADDRESS_LINE_3).append("=").append(SqlHandler.delimitString(msdsBean.getLabelAddressLine3())).append(",");
        query.append(ATTRIBUTE_LABEL_ADDRESS_LINE_4).append("=").append(SqlHandler.delimitString(msdsBean.getLabelAddressLine4())).append(",");
        query.append(ATTRIBUTE_LABEL_CITY).append("=").append(SqlHandler.delimitString(msdsBean.getLabelCity())).append(",");
        query.append(ATTRIBUTE_LABEL_COUNTRY_ABBREV).append("=").append(SqlHandler.delimitString(msdsBean.getLabelCountryAbbrev())).append(",");
        query.append(ATTRIBUTE_LABEL_STATE_ABBREV).append("=").append(SqlHandler.delimitString(msdsBean.getLabelStateAbbrev())).append(",");
        query.append(ATTRIBUTE_LABEL_ZIP).append("=").append(SqlHandler.delimitString(msdsBean.getLabelZip())).append(",");
        query.append(ATTRIBUTE_LABEL_PHONE).append("=").append(SqlHandler.delimitString(msdsBean.getLabelPhone())).append(",");
        query.append(ATTRIBUTE_MSDS_ID).append("=").append(msdsBean.getMsdsId()).append(",");
        //query.append(ATTRIBUTE_GHS_COMPLIANT_IMAGE).append("=").append(SqlHandler.delimitString(msdsBean.isGhsCompliantImage() ? "Y" : "N")).append(",");
        query.append(ATTRIBUTE_GHS_HAZARD).append("=").append(SqlHandler.delimitString(msdsBean.isGhsHazard() ? "N" : "Y")).append(" ");
        query.append(getWhereClause(getKeyCriteria(msdsBean))).append(" ");

		return new SqlManager().update(conn, query.toString());
	}

	public int setIdOnlyToProcessed(MsdsBean msdsBean, Connection conn) throws BaseException {
		StringBuilder query = new StringBuilder("update ").append(TABLE).append(" set ");
		query.append(ATTRIBUTE_ID_ONLY).append(" = 'C' ");
        query.append(getWhereClause(getKeyCriteria(msdsBean))).append(" ");
		return new SqlManager().update(conn, query.toString());
    }

	public void setMsdsLocale(MsdsBean msdsBean, Connection conn) throws BaseException {
        StringBuilder query = new StringBuilder("select count(*) from  ").append(TABLE_LOCALE).append(" ").append(getWhereClause(getKeyCriteria(msdsBean)));
        query.append(" and locale_code = ").append(SqlHandler.delimitString(msdsBean.getImageLocaleCode()));
        DataSet dataSet = new SqlManager().select(conn, query.toString());
		Iterator dataIter = dataSet.iterator();
		int count = 0;
		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			count = dataSetRow.getInt("COUNT(*)");
		}
        //insert
        if (count == 0) {
            //material_locale
            query = new StringBuilder("select count(*) from global.material_locale where material_id = ").append(msdsBean.getMaterialId());
            query.append(" and locale_code = ").append(SqlHandler.delimitString(msdsBean.getImageLocaleCode()));
            dataSet = new SqlManager().select(conn, query.toString());
            dataIter = dataSet.iterator();
            count = 0;
            while (dataIter.hasNext()) {
                DataSetRow dataSetRow = (DataSetRow) dataIter.next();
                count = dataSetRow.getInt("COUNT(*)");
            }
            if (count == 0) {
                query = new StringBuilder("insert into global.material_locale (material_id,locale_code) values (").append(msdsBean.getMaterialId()).append(",");
                query.append(SqlHandler.delimitString(msdsBean.getImageLocaleCode())).append(")");
                new SqlManager().update(conn, query.toString());
            }
            //msds_locale
            query = new StringBuilder("insert into ").append(TABLE_LOCALE).append(" (").append(ATTRIBUTE_MATERIAL_ID).append(",");
			query.append(ATTRIBUTE_REVISION_DATE).append(",").append(ATTRIBUTE_CONTENT).append(",").append(ATTRIBUTE_ON_LINE).append(",");
            query.append(ATTRIBUTE_INSERT_DATE).append(",locale_code,").append(ATTRIBUTE_MSDS_AUTHOR_ID).append(")");
			query.append(" values (").append(msdsBean.getMaterialId()).append(",");
			query.append(DateHandler.getOracleToDateFunction(msdsBean.getRevisionDate())).append(",");
			query.append(SqlHandler.delimitString(msdsBean.getContent())).append(",");
            query.append("'Y',sysdate,").append(SqlHandler.delimitString(msdsBean.getImageLocaleCode())).append(",");
            query.append(msdsBean.getMsdsAuthorId()).append(")");
            new SqlManager().update(conn, query.toString());
        }else {
            query = new StringBuilder("update ").append(TABLE_LOCALE).append(" set ");
		    query.append(ATTRIBUTE_CONTENT).append("=").append(SqlHandler.delimitString(msdsBean.getContent()));
            query.append(getWhereClause(getKeyCriteria(msdsBean))).append(" ");
            query.append(" and locale_code = ").append(SqlHandler.delimitString(msdsBean.getImageLocaleCode()));
            new SqlManager().update(conn, query.toString());
        }
    }

    //select
	public Collection<MsdsBean> select(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {

		Connection connection = null;
		Collection<MsdsBean> c = null;
		try {
			connection = getDbManager().getConnection();
			c = select(criteria, sortCriteria, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
		return c;
	}

	public Collection<MsdsBean> select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws BaseException {

		Collection<MsdsBean> msdsBeanColl = new Vector<MsdsBean>();

		String query = "select * from " + TABLE + " " + getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			MsdsBean msdsBean = new MsdsBean();
			load(dataSetRow, msdsBean);
			msdsBeanColl.add(msdsBean);
		}

		return msdsBeanColl;
	}
	
	public MsdsBean select(BigDecimal materialId, Date revisionDate) throws BaseException {
		MsdsBean msdsBean = null;
		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			msdsBean = select(materialId, revisionDate, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
		return msdsBean;
	}

	public MsdsBean select(BigDecimal materialId, Date revisionDate, Connection conn) throws BaseException {
		Collection<MsdsBean> results = select(getKeyCriteria(materialId, revisionDate), null, conn);
		return results.isEmpty() ? null : results.iterator().next();
	}
	
	//select
/*	public MsdsBean selectWLockheed(BigDecimal materialId, Date revisionDate) throws BaseException {
		MsdsBean msdsBean = null;
		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			msdsBean = (MsdsBean) selectWLockheed(materialId, revisionDate, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
		return msdsBean;
	}
	
	public MsdsBean selectWLockheed(BigDecimal materialId, Date revisionDate, Connection conn) throws BaseException {
		Collection<MsdsBean> results = selectWLockheed(getKeyCriteria(materialId, revisionDate), null, conn);
		return results.isEmpty() ? null : results.iterator().next();
	}

	public Collection<MsdsBean> selectWLockheed(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws BaseException {
		Collection<MsdsBean> msdsBeanColl = new Vector<MsdsBean>();

		String query = "select * from " + TABLE + " " + getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			MsdsBean msdsBean = new MsdsBean();
			load(dataSetRow, msdsBean);
			msdsBeanColl.add(msdsBean);
		}

		return msdsBeanColl;
	}
*/
	public Collection<MsdsBean> selectAllRevisionDates(BigDecimal materialId) throws BaseException {

		Connection connection = null;
		Collection<MsdsBean> c = null;
		try {
			connection = getDbManager().getConnection();
			c = selectAllRevisionDates(materialId, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
		return c;
	}

	public Collection<MsdsBean> selectAllRevisionDates(BigDecimal materialId,  Connection conn) throws BaseException {

		Collection<MsdsBean> msdsBeanColl = new Vector<MsdsBean>();
		SearchCriteria criteria = new SearchCriteria("materialId", SearchCriterion.EQUALS, "" + materialId);

		String query = "select revision_date, content from " + TABLE + " " + getWhereClause(criteria) + " order by revision_date desc";

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			MsdsBean msdsBean = new MsdsBean();
			load(dataSetRow, msdsBean);
			msdsBeanColl.add(msdsBean);
		}

		return msdsBeanColl;
	}
	
	public Collection<MsdsBean> selectAllSignalWords() throws BaseException {
		Connection connection = null;
		Collection<MsdsBean> msdsBeanColl = new Vector<MsdsBean>();
		try {
			connection = getDbManager().getConnection();
		
			String query = "select distinct signal_word from " + TABLE_SUG + " order by signal_word desc";
	
			DataSet dataSet = new SqlManager().select(connection, query);
	
			Iterator dataIter = dataSet.iterator();
	
			while (dataIter.hasNext()) {
				DataSetRow dataSetRow = (DataSetRow) dataIter.next();
				MsdsBean msdsBean = new MsdsBean();
				load(dataSetRow, msdsBean);
				msdsBeanColl.add(msdsBean);
			}
		}
		finally {
			getDbManager().returnConnection(connection);
		}

		return msdsBeanColl;
	}
	
	public Collection<MsdsBean> selRevDatesCustGlobMsdsUnion(BigDecimal materialId) throws BaseException {

		Connection connection = null;
		Collection<MsdsBean> c = null;
		try {
			connection = getDbManager().getConnection();
			c = selRevDatesCustGlobMsdsUnion(materialId, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
		return c;
	}
	
	public Collection<MsdsBean> selRevDatesCustGlobMsdsUnion(BigDecimal materialId, Connection conn) throws BaseException {

		Collection<MsdsBean> msdsBeanColl = new Vector<MsdsBean>();
		SearchCriteria criteria = new SearchCriteria("materialId", SearchCriterion.EQUALS, "" + materialId);

		StringBuilder query =  new StringBuilder("select revision_date, content from ");
		query.append(TABLE).append(" ").append(getWhereClause(criteria)).append(" union ");
		query.append("select revision_date, content from ").append(TABLE_CO).append(" cm ").append(getWhereClause(criteria));
		query.append(" and material_id in (select material_id from material m where customer_only_msds = 'Y' and cm.material_id = m.material_id) ").append(" order by revision_date desc"); 

		DataSet dataSet = new SqlManager().select(conn, query.toString());

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			MsdsBean msdsBean = new MsdsBean();
			load(dataSetRow, msdsBean);
			msdsBeanColl.add(msdsBean);
		}

		return msdsBeanColl;
	}
		
	public boolean isOnLine(MsdsBean msdsBean, Connection conn) throws BaseException {
		String query = "select count(*) from  " + TABLE + " " + getWhereClause(getKeyCriteria(msdsBean))+
                       " and on_line = 'Y'";
        DataSet dataSet = new SqlManager().select(conn, query);
		Iterator dataIter = dataSet.iterator();
		int count = 0;
		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			count = dataSetRow.getInt("COUNT(*)");
		}
		return count > 0;
	}

	public boolean isOnLine(MsdsBean msdsBean) throws BaseException {
		Connection connection = null;
		;
		boolean online = true;
		try {
			connection = getDbManager().getConnection();
			online = isOnLine(msdsBean, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
		return online;
	}
	
	public MsdsBean selectCustomerOverride(BigDecimal materialId, Date revisionDate, String companyId) throws BaseException {
		MsdsBean msdsBean = null;
		Connection connection = null;
		try {
			connection = getDbManager().getConnection();
			msdsBean = selectCustomerOverride(materialId, revisionDate, companyId, connection);
		}
		finally {
			getDbManager().returnConnection(connection);
		}
		return msdsBean;
	}
	
	public MsdsBean selectCustomerOverride(BigDecimal materialId, Date revisionDate, String companyId, Connection conn) throws BaseException {
		Collection<MsdsBean> results = selectCustomerOverride(getKeyCriteria(materialId, revisionDate, companyId), null, conn);
		return results.isEmpty() ? null : results.iterator().next();
	}

	public Collection<MsdsBean> selectCustomerOverride(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws BaseException {
		Collection<MsdsBean> msdsBeanColl = new Vector<MsdsBean>();

		String query = "select * from " + TABLE_CO + " " + getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			MsdsBean msdsBean = new MsdsBean();
			load(dataSetRow, msdsBean);
			msdsBeanColl.add(msdsBean);
		}

		return msdsBeanColl;
	}
	
	public void setMsdsId(SearchCriteria criteria, BigDecimal msdsId, Connection conn) throws BaseException {
		String query = "update " + TABLE + " set msds_id = " + msdsId + " " + getWhereClause(criteria);
		new SqlManager().update(conn, query.toString());
	}
}