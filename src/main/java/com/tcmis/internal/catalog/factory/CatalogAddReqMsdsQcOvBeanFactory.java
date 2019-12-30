package com.tcmis.internal.catalog.factory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbSelectException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.catalog.beans.CatalogAddReqCompQcObjBean;
import com.tcmis.internal.catalog.beans.CatalogAddReqMsdsQcOvBean;
import com.tcmis.internal.catalog.beans.CatalogAddRequestOvBean;

/******************************************************************************
 * CLASSNAME: CatalogAddRequestOvBeanFactory <br>
 * @version: 1.0, Aug 20, 2010 <br>
 *****************************************************************************/

public class CatalogAddReqMsdsQcOvBeanFactory extends BaseBeanFactory {

	static Hashtable<String,String> tmpHash = new Hashtable();
	
	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_REQUEST_ID = "REQUEST_ID";
	public String ATTRIBUTE_LINE_ITEM = "LINE_ITEM";
	public String ATTRIBUTE_PART_ID = "PART_ID";
	public String ATTRIBUTE_MATERIAL_ID = "MATERIAL_ID";
	public String ATTRIBUTE_REVISION_DATE = "REVISION_DATE";
	public String ATTRIBUTE_MATERIAL_DESC = "MATERIAL_DESC";
	public String ATTRIBUTE_MANUFACTURER = "MANUFACTURER";
	public String ATTRIBUTE_MFG_ID = "MFG_ID";
	public String ATTRIBUTE_MFG_TRADE_NAME = "MFG_TRADE_NAME";
	public String ATTRIBUTE_COMMENTS = "COMMENTS";
	public String ATTRIBUTE_MFG_URL = "MFG_URL";
	public String ATTRIBUTE_CONTACT = "CONTACT";
	public String ATTRIBUTE_PHONE = "PHONE";
	public String ATTRIBUTE_EMAIL = "EMAIL";
	public String ATTRIBUTE_NOTES = "NOTES";
	public String ATTRIBUTE_CONTENT = "CONTENT";
	public String ATTRIBUTE_SPECIFIC_GRAVITY_DETECT = "SPECIFIC_GRAVITY_DETECT";
	public String ATTRIBUTE_SPECIFIC_GRAVITY_LOWER = "SPECIFIC_GRAVITY_LOWER";
	public String ATTRIBUTE_SPECIFIC_GRAVITY_UPPER = "SPECIFIC_GRAVITY_UPPER";
	public String ATTRIBUTE_SPECIFIC_GRAVITY_BASIS = "SPECIFIC_GRAVITY_BASIS";
	public String ATTRIBUTE_SPECIFIC_GRAVITY_SOURCE = "SPECIFIC_GRAVITY_SOURCE";
	public String ATTRIBUTE_HEALTH = "HEALTH";
	public String ATTRIBUTE_FLAMMABILITY = "FLAMMABILITY";
	public String ATTRIBUTE_REACTIVITY = "REACTIVITY";
	public String ATTRIBUTE_SPECIFIC_HAZARD = "SPECIFIC_HAZARD";
	public String ATTRIBUTE_NFPA_SOURCE = "NFPA_SOURCE";
	public String ATTRIBUTE_HMIS_HEALTH = "HMIS_HEALTH";
	public String ATTRIBUTE_HMIS_FLAMMABILITY = "HMIS_FLAMMABILITY";
	public String ATTRIBUTE_HMIS_REACTIVITY = "HMIS_REACTIVITY";
	public String ATTRIBUTE_PERSONAL_PROTECTION = "PERSONAL_PROTECTION";
	public String ATTRIBUTE_HMIS_CHRONIC = "HMIS_CHRONIC";
	public String ATTRIBUTE_HMIS_SOURCE = "HMIS_SOURCE";
	public String ATTRIBUTE_FLASH_POINT_DETECT = "FLASH_POINT_DETECT";
	public String ATTRIBUTE_FLASH_POINT_LOWER = "FLASH_POINT_LOWER";
	public String ATTRIBUTE_FLASH_POINT_UPPER = "FLASH_POINT_UPPER";
	public String ATTRIBUTE_FLASH_POINT_UNIT = "FLASH_POINT_UNIT";
	public String ATTRIBUTE_FLASH_POINT_METHOD = "FLASH_POINT_METHOD";
	public String ATTRIBUTE_FLASH_POINT_SOURCE = "FLASH_POINT_SOURCE";
	public String ATTRIBUTE_DENSITY_DETECT = "DENSITY_DETECT";
	public String ATTRIBUTE_DENSITY = "DENSITY";
	public String ATTRIBUTE_DENSITY_UPPER = "DENSITY_UPPER";
	public String ATTRIBUTE_DENSITY_UNIT = "DENSITY_UNIT";
	public String ATTRIBUTE_DENSITY_SOURCE = "DENSITY_SOURCE";
	public String ATTRIBUTE_PHYSICAL_STATE = "PHYSICAL_STATE";
	public String ATTRIBUTE_VOC_UNIT = "VOC_UNIT";
	public String ATTRIBUTE_VOC = "VOC";
	public String ATTRIBUTE_VOC_LOWER = "VOC_LOWER";
	public String ATTRIBUTE_VOC_UPPER = "VOC_UPPER";
	public String ATTRIBUTE_REMARK = "REMARK";
	public String ATTRIBUTE_SOLIDS = "SOLIDS";
	public String ATTRIBUTE_SOLIDS_LOWER = "SOLIDS_LOWER";
	public String ATTRIBUTE_SOLIDS_UPPER = "SOLIDS_UPPER";
	public String ATTRIBUTE_SOLIDS_UNIT = "SOLIDS_UNIT";
	public String ATTRIBUTE_SOLIDS_SOURCE = "SOLIDS_SOURCE";
	public String ATTRIBUTE_VOC_LOWER_PERCENT = "VOC_LOWER_PERCENT";
	public String ATTRIBUTE_VOC_UPPER_PERCENT = "VOC_UPPER_PERCENT";
	public String ATTRIBUTE_VOC_PERCENT = "VOC_PERCENT";
	public String ATTRIBUTE_VOC_SOURCE = "VOC_SOURCE";
	public String ATTRIBUTE_ALT_DATA_SOURCE = "ALT_DATA_SOURCE";
	public String ATTRIBUTE_VAPOR_PRESSURE_DETECT = "VAPOR_PRESSURE_DETECT";
	public String ATTRIBUTE_VAPOR_PRESSURE = "VAPOR_PRESSURE";
	public String ATTRIBUTE_VAPOR_PRESSURE_UPPER = "VAPOR_PRESSURE_UPPER";
	public String ATTRIBUTE_VAPOR_PRESSURE_UNIT = "VAPOR_PRESSURE_UNIT";
	public String ATTRIBUTE_VAPOR_PRESSURE_TEMP = "VAPOR_PRESSURE_TEMP";
	public String ATTRIBUTE_VAPOR_PRESSURE_TEMP_UNIT = "VAPOR_PRESSURE_TEMP_UNIT";
	public String ATTRIBUTE_VAPOR_PRESSURE_SOURCE = "VAPOR_PRESSURE_SOURCE";
	public String ATTRIBUTE_VOC_LESS_H2O_EXEMPT = "VOC_LESS_H2O_EXEMPT";
	public String ATTRIBUTE_VOC_LESS_H2O_EXEMPT_UNIT = "VOC_LESS_H2O_EXEMPT_UNIT";
	public String ATTRIBUTE_VOC_LESS_H2O_EXEMPT_LOWER = "VOC_LESS_H2O_EXEMPT_LOWER";
	public String ATTRIBUTE_VOC_LESS_H2O_EXEMPT_UPPER = "VOC_LESS_H2O_EXEMPT_UPPER";
	public String ATTRIBUTE_VOC_LESS_H2O_EXEMPT_SOURCE = "VOC_LESS_H2O_EXEMPT_SOURCE";
	public String ATTRIBUTE_VOC_LB_PER_SOLID_LB = "VOC_LB_PER_SOLID_LB";
	public String ATTRIBUTE_VOC_LB_PER_SOLID_LB_SOURCE = "VOC_LB_PER_SOLID_LB_SOURCE";
	public String ATTRIBUTE_VOC_LB_PER_SOLID_LB_LOWER = "VOC_LB_PER_SOLID_LB_LOWER";
	public String ATTRIBUTE_VOC_LB_PER_SOLID_LB_UPPER = "VOC_LB_PER_SOLID_LB_UPPER";
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
	public String ATTRIBUTE_WATER_REACTIVE = "WATER_REACTIVE";
	public String ATTRIBUTE_OXIDIZER = "OXIDIZER";
	public String ATTRIBUTE_CARCINOGEN = "CARCINOGEN";
	public String ATTRIBUTE_ALLOY = "ALLOY";
	public String ATTRIBUTE_BOILING_POINT_DETECT = "BOILING_POINT_DETECT";
	public String ATTRIBUTE_BOILING_POINT_LOWER = "BOILING_POINT_LOWER";
	public String ATTRIBUTE_BOILING_POINT_UPPER = "BOILING_POINT_UPPER";
	public String ATTRIBUTE_BOILING_POINT_UNIT = "BOILING_POINT_UNIT";
	public String ATTRIBUTE_BOILING_POINT_SOURCE = "BOILING_POINT_SOURCE";
	public String ATTRIBUTE_BOILING_POINT_DETAIL = "BOILING_POINT_DETAIL";
	public String ATTRIBUTE_PH = "PH";
	public String ATTRIBUTE_PH_DETAIL = "PH_DETAIL";
	public String ATTRIBUTE_PH_DETECT = "PH_DETECT";
	public String ATTRIBUTE_PH_SOURCE = "PH_SOURCE";
	public String ATTRIBUTE_PH_UPPER = "PH_UPPER";
	public String ATTRIBUTE_LM_ALLOY = "LM_ALLOY";
	public String ATTRIBUTE_LM_ALT_DATA_SOURCE = "LM_ALT_DATA_SOURCE";
	public String ATTRIBUTE_LM_BOILING_POINT_DETECT = "LM_BOILING_POINT_DETECT";
	public String ATTRIBUTE_LM_BOILING_POINT_LOWER = "LM_BOILING_POINT_LOWER";
	public String ATTRIBUTE_LM_BOILING_POINT_UPPER = "LM_BOILING_POINT_UPPER";
	public String ATTRIBUTE_LM_BOILING_POINT_UNIT = "LM_BOILING_POINT_UNIT";
	public String ATTRIBUTE_LM_BOILING_POINT_SOURCE = "LM_BOILING_POINT_SOURCE";
	public String ATTRIBUTE_LM_BOILING_POINT_DETAIL = "LM_BOILING_POINT_DETAIL";
	public String ATTRIBUTE_LM_CARCINOGEN = "LM_CARCINOGEN";
	public String ATTRIBUTE_LM_CHRONIC = "LM_CHRONIC";
	public String ATTRIBUTE_LM_COMPATIBILITY = "LM_COMPATIBILITY";
	public String ATTRIBUTE_LM_COMPOSITION_CHANGED = "LM_COMPOSITION_CHANGED";
	public String ATTRIBUTE_LM_CONTENT = "LM_CONTENT";
	public String ATTRIBUTE_LM_CORROSIVE = "LM_CORROSIVE";
	public String ATTRIBUTE_LM_DENSITY = "LM_DENSITY";
	public String ATTRIBUTE_LM_DENSITY_DETECT = "LM_DENSITY_DETECT";
	public String ATTRIBUTE_LM_DENSITY_UNIT = "LM_DENSITY_UNIT";
	public String ATTRIBUTE_LM_DENSITY_UPPER = "LM_DENSITY_UPPER";
	public String ATTRIBUTE_LM_EMERGENCY_PHONE = "LM_EMERGENCY_PHONE";
	public String ATTRIBUTE_LM_EVAPORATION_RATE = "LM_EVAPORATION_RATE";
	public String ATTRIBUTE_LM_EYES = "LM_EYES";
	public String ATTRIBUTE_LM_FIRE_CONDITIONS_TO_AVOID = "LM_FIRE_CONDITIONS_TO_AVOID";
	public String ATTRIBUTE_LM_FLAMMABILITY = "LM_FLAMMABILITY";
	public String ATTRIBUTE_LM_FLASH_POINT = "LM_FLASH_POINT";
	public String ATTRIBUTE_LM_FLASH_POINT_DETECT = "LM_FLASH_POINT_DETECT";
	public String ATTRIBUTE_LM_FLASH_POINT_LOWER = "LM_FLASH_POINT_LOWER";
	public String ATTRIBUTE_LM_FLASH_POINT_METHOD = "LM_FLASH_POINT_METHOD";
	public String ATTRIBUTE_LM_FLASH_POINT_SOURCE = "LM_FLASH_POINT_SOURCE";
	public String ATTRIBUTE_LM_FLASH_POINT_UNIT = "LM_FLASH_POINT_UNIT";
	public String ATTRIBUTE_LM_FLASH_POINT_UPPER = "LM_FLASH_POINT_UPPER";
	public String ATTRIBUTE_LM_FREEZING_POINT = "LM_FREEZING_POINT";
	public String ATTRIBUTE_LM_FRENCH_CONTENT = "LM_FRENCH_CONTENT";
	public String ATTRIBUTE_LM_HEALTH = "LM_HEALTH";
	public String ATTRIBUTE_LM_HEALTH_EFFECTS = "LM_HEALTH_EFFECTS";
	public String ATTRIBUTE_LM_HMIS_CHRONIC = "LM_HMIS_CHRONIC";
	public String ATTRIBUTE_LM_HMIS_FLAMMABILITY = "LM_HMIS_FLAMMABILITY";
	public String ATTRIBUTE_LM_HMIS_HEALTH = "LM_HMIS_HEALTH";
	public String ATTRIBUTE_LM_HMIS_REACTIVITY = "LM_HMIS_REACTIVITY";
	public String ATTRIBUTE_LM_HMIS_SOURCE = "LM_HMIS_SOURCE";
	public String ATTRIBUTE_LM_INCOMPATIBLE = "LM_INCOMPATIBLE";
	public String ATTRIBUTE_LM_INGESTION = "LM_INGESTION";
	public String ATTRIBUTE_LM_INHALATION = "LM_INHALATION";
	public String ATTRIBUTE_LM_INJECTION = "LM_INJECTION";
	public String ATTRIBUTE_LM_INSERT_DATE = "LM_INSERT_DATE";
	public String ATTRIBUTE_LM_LFC_CODE = "LM_LFC_CODE";
	public String ATTRIBUTE_LM_MATERIAL_ID = "LM_MATERIAL_ID";
	public String ATTRIBUTE_LM_MIXTURE = "LM_MIXTURE";
	public String ATTRIBUTE_LM_NFPA_SOURCE = "LM_NFPA_SOURCE";
	public String ATTRIBUTE_LM_ON_LINE = "LM_ON_LINE";
	public String ATTRIBUTE_LM_OSHA_HAZARD = "LM_OSHA_HAZARD";
	public String ATTRIBUTE_LM_OXIDIZER = "LM_OXIDIZER";
	public String ATTRIBUTE_LM_PERSONAL_PROTECTION = "LM_PERSONAL_PROTECTION";
	public String ATTRIBUTE_LM_PH = "LM_PH";
	public String ATTRIBUTE_LM_PHYSICAL_STATE = "LM_PHYSICAL_STATE";
	public String ATTRIBUTE_LM_PH_DETAIL = "LM_PH_DETAIL";
	public String ATTRIBUTE_LM_PH_DETECT = "LM_PH_DETECT";
	public String ATTRIBUTE_LM_PH_SOURCE = "LM_PH_SOURCE";
	public String ATTRIBUTE_LM_PH_UPPER = "LM_PH_UPPER";
	public String ATTRIBUTE_LM_POLYMERIZE = "LM_POLYMERIZE";
	public String ATTRIBUTE_LM_PPE = "LM_PPE";
	public String ATTRIBUTE_LM_REACTIVITY = "LM_REACTIVITY";
	public String ATTRIBUTE_LM_REMARK = "LM_REMARK";
	public String ATTRIBUTE_LM_REVIEWED_BY = "LM_REVIEWED_BY";
	public String ATTRIBUTE_LM_REVIEW_DATE = "LM_REVIEW_DATE";
	public String ATTRIBUTE_LM_ROUTE_OF_ENTRY = "LM_ROUTE_OF_ENTRY";
	public String ATTRIBUTE_LM_SARA_311_312_ACUTE = "LM_SARA_311_312_ACUTE";
	public String ATTRIBUTE_LM_SARA_311_312_CHRONIC = "LM_SARA_311_312_CHRONIC";
	public String ATTRIBUTE_LM_SARA_311_312_FIRE = "LM_SARA_311_312_FIRE";
	public String ATTRIBUTE_LM_SARA_311_312_PRESSURE = "LM_SARA_311_312_PRESSURE";
	public String ATTRIBUTE_LM_SARA_311_312_REACTIVITY = "LM_SARA_311_312_REACTIVITY";
	public String ATTRIBUTE_LM_SIGNAL_WORD = "LM_SIGNAL_WORD";
	public String ATTRIBUTE_LM_SKIN = "LM_SKIN";
	public String ATTRIBUTE_LM_SOLIDS = "LM_SOLIDS";
	public String ATTRIBUTE_LM_SOLIDS_LOWER = "LM_SOLIDS_LOWER";
	public String ATTRIBUTE_LM_SOLIDS_LOWER_PERCENT = "LM_SOLIDS_LOWER_PERCENT";
	public String ATTRIBUTE_LM_SOLIDS_PERCENT = "LM_SOLIDS_PERCENT";
	public String ATTRIBUTE_LM_SOLIDS_SOURCE = "LM_SOLIDS_SOURCE";
	public String ATTRIBUTE_LM_SOLIDS_UNIT = "LM_SOLIDS_UNIT";
	public String ATTRIBUTE_LM_SOLIDS_UPPER = "LM_SOLIDS_UPPER";
	public String ATTRIBUTE_LM_SOLIDS_UPPER_PERCENT = "LM_SOLIDS_UPPER_PERCENT";
	public String ATTRIBUTE_LM_SPECIFIC_GRAVITY = "LM_SPECIFIC_GRAVITY";
	public String ATTRIBUTE_LM_SPECIFIC_GRAVITY_BASIS = "LM_SPECIFIC_GRAVITY_BASIS";
	public String ATTRIBUTE_LM_SPECIFIC_GRAVITY_DETECT = "LM_SPECIFIC_GRAVITY_DETECT";
	public String ATTRIBUTE_LM_SPECIFIC_GRAVITY_LOWER = "LM_SPECIFIC_GRAVITY_LOWER";
	public String ATTRIBUTE_LM_SPECIFIC_GRAVITY_UPPER = "LM_SPECIFIC_GRAVITY_UPPER";
	public String ATTRIBUTE_LM_SPECIFIC_HAZARD = "LM_SPECIFIC_HAZARD";
	public String ATTRIBUTE_LM_STABLE = "LM_STABLE";
	public String ATTRIBUTE_LM_STORAGE_TEMP = "LM_STORAGE_TEMP";
	public String ATTRIBUTE_LM_TARGET_ORGAN = "LM_TARGET_ORGAN";
	public String ATTRIBUTE_LM_TSCA_12B = "LM_TSCA_12B";
	public String ATTRIBUTE_LM_TSCA_LIST = "LM_TSCA_LIST";
	public String ATTRIBUTE_LM_VAPOR_DENSITY = "LM_VAPOR_DENSITY";
	public String ATTRIBUTE_LM_VAPOR_PRESSURE = "LM_VAPOR_PRESSURE";
	public String ATTRIBUTE_LM_VAPOR_PRESSURE_DETECT = "LM_VAPOR_PRESSURE_DETECT";
	public String ATTRIBUTE_LM_VAPOR_PRESSURE_SOURCE = "LM_VAPOR_PRESSURE_SOURCE";
	public String ATTRIBUTE_LM_VAPOR_PRESSURE_TEMP = "LM_VAPOR_PRESSURE_TEMP";
	public String ATTRIBUTE_LM_VAPOR_PRESSURE_TEMP_UNIT = "LM_VAPOR_PRESSURE_TEMP_UNIT";
	public String ATTRIBUTE_LM_VAPOR_PRESSURE_UNIT = "LM_VAPOR_PRESSURE_UNIT";
	public String ATTRIBUTE_LM_VAPOR_PRESSURE_UPPER = "LM_VAPOR_PRESSURE_UPPER";
	public String ATTRIBUTE_LM_VOC = "LM_VOC";
	public String ATTRIBUTE_LM_VOC_LB_PER_SOLID_LB = "LM_VOC_LB_PER_SOLID_LB";
	public String ATTRIBUTE_LM_VOC_LB_PER_SOLID_LB_LOWER = "LM_VOC_LB_PER_SOLID_LB_LOWER";
	public String ATTRIBUTE_LM_VOC_LB_PER_SOLID_LB_SOURCE = "LM_VOC_LB_PER_SOLID_LB_SOURCE";
	public String ATTRIBUTE_LM_VOC_LB_PER_SOLID_LB_UPPER = "LM_VOC_LB_PER_SOLID_LB_UPPER";
	public String ATTRIBUTE_LM_VOC_LESS_H20_EXEMPT_SOURCE = "LM_VOC_LESS_H20_EXEMPT_SOURCE";
	public String ATTRIBUTE_LM_VOC_LESS_H2O_EXEMPT = "LM_VOC_LESS_H2O_EXEMPT";
	public String ATTRIBUTE_LM_VOC_LESS_H2O_EXEMPT_LOWER = "LM_VOC_LESS_H2O_EXEMPT_LOWER";
	public String ATTRIBUTE_LM_VOC_LESS_H2O_EXEMPT_SOURCE = "LM_VOC_LESS_H2O_EXEMPT_SOURCE";
	public String ATTRIBUTE_LM_VOC_LESS_H2O_EXEMPT_UNIT = "LM_VOC_LESS_H2O_EXEMPT_UNIT";
	public String ATTRIBUTE_LM_VOC_LESS_H2O_EXEMPT_UPPER = "LM_VOC_LESS_H2O_EXEMPT_UPPER";
	public String ATTRIBUTE_LM_VOC_LOWER = "LM_VOC_LOWER";
	public String ATTRIBUTE_LM_VOC_LOWER_PERCENT = "LM_VOC_LOWER_PERCENT";
	public String ATTRIBUTE_LM_VOC_PERCENT = "LM_VOC_PERCENT";
	public String ATTRIBUTE_LM_VOC_SOURCE = "LM_VOC_SOURCE";
	public String ATTRIBUTE_LM_VOC_UNIT = "LM_VOC_UNIT";
	public String ATTRIBUTE_LM_VOC_UPPER = "LM_VOC_UPPER";
	public String ATTRIBUTE_LM_VOC_UPPER_PERCENT = "LM_VOC_UPPER_PERCENT";
	public String ATTRIBUTE_LM_WATER_REACTIVE = "LM_WATER_REACTIVE";
	public String ATTRIBUTE_LOCKHEED_MSDS = "LOCKHEED_MSDS";
	public String ATTRIBUTE_LM_SPECIFIC_GRAVITY_SOURCE = "LM_SPECIFIC_GRAVITY_SOURCE";
	public String ATTRIBUTE_LM_DENSITY_SOURCE = "LM_DENSITY_SOURCE";
	public String ATTRIBUTE_PRODUCT_CODE = "PRODUCT_CODE";
	public String ATTRIBUTE_COMPOSITION_VAR = "COMPOSITION_VAR";
    public String ATTRIBUTE_MFG_SHORT_NAME = "MFG_SHORT_NAME";
    public String ATTRIBUTE_MSDS_VERIFIED = "MSDS_VERIFIED";
    public String ATTRIBUTE_VERIFIED_BY = "VERIFIED_BY";
    public String ATTRIBUTE_VERIFIED_ON = "VERIFIED_DATE";
    public String ATTRIBUTE_ID_ONLY = "ID_ONLY";
    public String ATTRIBUTE_VERIFIED_BY_NAME = "VERIFIED_BY_NAME";
    public String ATTRIBUTE_ON_LINE = "ON_LINE";
    public String ATTRIBUTE_MSDS_AUTHOR_ID = "MSDS_AUTHOR_ID";
    public String ATTRIBUTE_MSDS_AUTHOR_DESC = "MSDS_AUTHOR_DESC";
    public String ATTRIBUTE_DETONABLE = "DETONABLE";
    public String ATTRIBUTE_MISCIBLE = "MISCIBLE";
    public String ATTRIBUTE_PYROPHORIC = "PYROPHORIC";
    public String ATTRIBUTE_SPONTANEOUSLY_COMBUSTIBLE = "SPONTANEOUSLY_COMBUSTIBLE";
    public String ATTRIBUTE_TSCA_STATEMENT = "TSCA_STATEMENT";
    
    //table name
	public String TABLE = "CATALOG_ADD_REQ_MSDS_QC_OV";


	//constructor
	public CatalogAddReqMsdsQcOvBeanFactory(DbManager dbManager) {
		super(dbManager);
	}


	//get column names
	public String getColumnName(String attributeName) {
		if(attributeName.equals("requestId")) {
			return ATTRIBUTE_REQUEST_ID;
		}
		else if(attributeName.equals("lineItem")) {
			return ATTRIBUTE_LINE_ITEM;
		}
		else if(attributeName.equals("partId")) {
			return ATTRIBUTE_PART_ID;
		}
		else if(attributeName.equals("materialId")) {
			return ATTRIBUTE_MATERIAL_ID;
		}
		else if(attributeName.equals("revisionDate")) {
			return ATTRIBUTE_REVISION_DATE;
		}
		else if(attributeName.equals("materialDesc")) {
			return ATTRIBUTE_MATERIAL_DESC;
		}
		else if(attributeName.equals("manufacturer")) {
			return ATTRIBUTE_MANUFACTURER;
		}
		else if(attributeName.equals("mfgId")) {
			return ATTRIBUTE_MFG_ID;
		}
		else if(attributeName.equals("mfgTradeName")) {
			return ATTRIBUTE_MFG_TRADE_NAME;
		}
		else if(attributeName.equals("comments")) {
			return ATTRIBUTE_COMMENTS;
		}
		else if(attributeName.equals("mfgUrl")) {
			return ATTRIBUTE_MFG_URL;
		}
		else if(attributeName.equals("contact")) {
			return ATTRIBUTE_CONTACT;
		}
		else if(attributeName.equals("phone")) {
			return ATTRIBUTE_PHONE;
		}
		else if(attributeName.equals("email")) {
			return ATTRIBUTE_EMAIL;
		}
		else if(attributeName.equals("notes")) {
			return ATTRIBUTE_NOTES;
		}
		else if(attributeName.equals("content")) {
			return ATTRIBUTE_CONTENT;
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
		else if(attributeName.equals("specificGravityBasis")) {
			return ATTRIBUTE_SPECIFIC_GRAVITY_BASIS;
		}
		else if(attributeName.equals("specificGravitySource")) {
			return ATTRIBUTE_SPECIFIC_GRAVITY_SOURCE;
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
		else if(attributeName.equals("nfpaSource")) {
			return ATTRIBUTE_NFPA_SOURCE;
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
		else if(attributeName.equals("personalProtection")) {
			return ATTRIBUTE_PERSONAL_PROTECTION;
		}
		else if(attributeName.equals("hmisChronic")) {
			return ATTRIBUTE_HMIS_CHRONIC;
		}
		else if(attributeName.equals("hmisSource")) {
			return ATTRIBUTE_HMIS_SOURCE;
		}
		else if(attributeName.equals("flashPointDetect")) {
			return ATTRIBUTE_FLASH_POINT_DETECT;
		}
		else if(attributeName.equals("flashPointLower")) {
			return ATTRIBUTE_FLASH_POINT_LOWER;
		}
		else if(attributeName.equals("flashPointUpper")) {
			return ATTRIBUTE_FLASH_POINT_UPPER;
		}
		else if(attributeName.equals("flashPointUnit")) {
			return ATTRIBUTE_FLASH_POINT_UNIT;
		}
		else if(attributeName.equals("flashPointMethod")) {
			return ATTRIBUTE_FLASH_POINT_METHOD;
		}
		else if(attributeName.equals("flashPointSource")) {
			return ATTRIBUTE_FLASH_POINT_SOURCE;
		}
		else if(attributeName.equals("densityDetect")) {
			return ATTRIBUTE_DENSITY_DETECT;
		}
		else if(attributeName.equals("density")) {
			return ATTRIBUTE_DENSITY;
		}
		else if(attributeName.equals("densityUpper")) {
			return ATTRIBUTE_DENSITY_UPPER;
		}
		else if(attributeName.equals("densityUnit")) {
			return ATTRIBUTE_DENSITY_UNIT;
		}
		else if(attributeName.equals("densitySource")) {
			return ATTRIBUTE_DENSITY_SOURCE;
		}
		else if(attributeName.equals("physicalState")) {
			return ATTRIBUTE_PHYSICAL_STATE;
		}
		else if(attributeName.equals("vocUnit")) {
			return ATTRIBUTE_VOC_UNIT;
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
		else if(attributeName.equals("remark")) {
			return ATTRIBUTE_REMARK;
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
		else if(attributeName.equals("solidsSource")) {
			return ATTRIBUTE_SOLIDS_SOURCE;
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
		else if(attributeName.equals("vocSource")) {
			return ATTRIBUTE_VOC_SOURCE;
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
		else if(attributeName.equals("vaporPressureUpper")) {
			return ATTRIBUTE_VAPOR_PRESSURE_UPPER;
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
		else if(attributeName.equals("vaporPressureSource")) {
			return ATTRIBUTE_VAPOR_PRESSURE_SOURCE;
		}
		else if(attributeName.equals("vocLessH2oExempt")) {
			return ATTRIBUTE_VOC_LESS_H2O_EXEMPT;
		}
		else if(attributeName.equals("vocLessH2oExemptUnit")) {
			return ATTRIBUTE_VOC_LESS_H2O_EXEMPT_UNIT;
		}
		else if(attributeName.equals("vocLessH2oExemptLower")) {
			return ATTRIBUTE_VOC_LESS_H2O_EXEMPT_LOWER;
		}
		else if(attributeName.equals("vocLessH2oExemptUpper")) {
			return ATTRIBUTE_VOC_LESS_H2O_EXEMPT_UPPER;
		}
		else if(attributeName.equals("vocLessH2oExemptSource")) {
			return ATTRIBUTE_VOC_LESS_H2O_EXEMPT_SOURCE;
		}
		else if(attributeName.equals("vocLbPerSolidLb")) {
			return ATTRIBUTE_VOC_LB_PER_SOLID_LB;
		}
		else if(attributeName.equals("vocLbPerSolidLbSource")) {
			return ATTRIBUTE_VOC_LB_PER_SOLID_LB_SOURCE;
		}
		else if(attributeName.equals("vocLbPerSolidLbLower")) {
			return ATTRIBUTE_VOC_LB_PER_SOLID_LB_LOWER;
		}
		else if(attributeName.equals("vocLbPerSolidLbUpper")) {
			return ATTRIBUTE_VOC_LB_PER_SOLID_LB_UPPER;
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
		else if(attributeName.equals("waterReactive")) {
			return ATTRIBUTE_WATER_REACTIVE;
		}
		else if(attributeName.equals("oxidizer")) {
			return ATTRIBUTE_OXIDIZER;
		}
		else if(attributeName.equals("carcinogen")) {
			return ATTRIBUTE_CARCINOGEN;
		}
		else if(attributeName.equals("alloy")) {
			return ATTRIBUTE_ALLOY;
		}
		else if(attributeName.equals("boilingPointDetect")) {
			return ATTRIBUTE_BOILING_POINT_DETECT;
		}
		else if(attributeName.equals("boilingPointLower")) {
			return ATTRIBUTE_BOILING_POINT_LOWER;
		}
		else if(attributeName.equals("boilingPointUpper")) {
			return ATTRIBUTE_BOILING_POINT_UPPER;
		}
		else if(attributeName.equals("boilingPointUnit")) {
			return ATTRIBUTE_BOILING_POINT_UNIT;
		}
		else if(attributeName.equals("boilingPointSource")) {
			return ATTRIBUTE_BOILING_POINT_SOURCE;
		}
		else if(attributeName.equals("boilingPointDetail")) {
			return ATTRIBUTE_BOILING_POINT_DETAIL;
		}
		else if(attributeName.equals("ph")) {
			return ATTRIBUTE_PH;
		}
		else if(attributeName.equals("phDetail")) {
			return ATTRIBUTE_PH_DETAIL;
		}
		else if(attributeName.equals("phDetect")) {
			return ATTRIBUTE_PH_DETECT;
		}
		else if(attributeName.equals("phSource")) {
			return ATTRIBUTE_PH_SOURCE;
		}
		else if(attributeName.equals("phUpper")) {
			return ATTRIBUTE_PH_UPPER;
		}
		else if(attributeName.equals("lmAlloy")) {
			return ATTRIBUTE_LM_ALLOY;
		}
		else if(attributeName.equals("lmAltDataSource")) {
			return ATTRIBUTE_LM_ALT_DATA_SOURCE;
		}
		else if(attributeName.equals("lmBoilingPointDetect")) {
			return ATTRIBUTE_LM_BOILING_POINT_DETECT;
		}
		else if(attributeName.equals("lmBoilingPointLower")) {
			return ATTRIBUTE_LM_BOILING_POINT_LOWER;
		}
		else if(attributeName.equals("lmBoilingPointUpper")) {
			return ATTRIBUTE_LM_BOILING_POINT_UPPER;
		}
		else if(attributeName.equals("lmBoilingPointUnit")) {
			return ATTRIBUTE_LM_BOILING_POINT_UNIT;
		}
		else if(attributeName.equals("lmBoilingPointSource")) {
			return ATTRIBUTE_LM_BOILING_POINT_SOURCE;
		}
		else if(attributeName.equals("lmBoilingPointDetail")) {
			return ATTRIBUTE_LM_BOILING_POINT_DETAIL;
		}
		else if(attributeName.equals("lmCarcinogen")) {
			return ATTRIBUTE_LM_CARCINOGEN;
		}
		else if(attributeName.equals("lmChronic")) {
			return ATTRIBUTE_LM_CHRONIC;
		}
		else if(attributeName.equals("lmCompatibility")) {
			return ATTRIBUTE_LM_COMPATIBILITY;
		}
		else if(attributeName.equals("lmCompositionChanged")) {
			return ATTRIBUTE_LM_COMPOSITION_CHANGED;
		}
		else if(attributeName.equals("lmContent")) {
			return ATTRIBUTE_LM_CONTENT;
		}
		else if(attributeName.equals("lmCorrosive")) {
			return ATTRIBUTE_LM_CORROSIVE;
		}
		else if(attributeName.equals("lmDensity")) {
			return ATTRIBUTE_LM_DENSITY;
		}
		else if(attributeName.equals("lmDensityDetect")) {
			return ATTRIBUTE_LM_DENSITY_DETECT;
		}
		else if(attributeName.equals("lmDensityUnit")) {
			return ATTRIBUTE_LM_DENSITY_UNIT;
		}
		else if(attributeName.equals("lmDensityUpper")) {
			return ATTRIBUTE_LM_DENSITY_UPPER;
		}
		else if(attributeName.equals("lmEmergencyPhone")) {
			return ATTRIBUTE_LM_EMERGENCY_PHONE;
		}
		else if(attributeName.equals("lmEvaporationRate")) {
			return ATTRIBUTE_LM_EVAPORATION_RATE;
		}
		else if(attributeName.equals("lmEyes")) {
			return ATTRIBUTE_LM_EYES;
		}
		else if(attributeName.equals("lmFireConditionsToAvoid")) {
			return ATTRIBUTE_LM_FIRE_CONDITIONS_TO_AVOID;
		}
		else if(attributeName.equals("lmFlammability")) {
			return ATTRIBUTE_LM_FLAMMABILITY;
		}
		else if(attributeName.equals("lmFlashPoint")) {
			return ATTRIBUTE_LM_FLASH_POINT;
		}
		else if(attributeName.equals("lmFlashPointDetect")) {
			return ATTRIBUTE_LM_FLASH_POINT_DETECT;
		}
		else if(attributeName.equals("lmFlashPointLower")) {
			return ATTRIBUTE_LM_FLASH_POINT_LOWER;
		}
		else if(attributeName.equals("lmFlashPointMethod")) {
			return ATTRIBUTE_LM_FLASH_POINT_METHOD;
		}
		else if(attributeName.equals("lmFlashPointSource")) {
			return ATTRIBUTE_LM_FLASH_POINT_SOURCE;
		}
		else if(attributeName.equals("lmFlashPointUnit")) {
			return ATTRIBUTE_LM_FLASH_POINT_UNIT;
		}
		else if(attributeName.equals("lmFlashPointUpper")) {
			return ATTRIBUTE_LM_FLASH_POINT_UPPER;
		}
		else if(attributeName.equals("lmFreezingPoint")) {
			return ATTRIBUTE_LM_FREEZING_POINT;
		}
		else if(attributeName.equals("lmFrenchContent")) {
			return ATTRIBUTE_LM_FRENCH_CONTENT;
		}
		else if(attributeName.equals("lmHealth")) {
			return ATTRIBUTE_LM_HEALTH;
		}
		else if(attributeName.equals("lmHealthEffects")) {
			return ATTRIBUTE_LM_HEALTH_EFFECTS;
		}
		else if(attributeName.equals("lmHmisChronic")) {
			return ATTRIBUTE_LM_HMIS_CHRONIC;
		}
		else if(attributeName.equals("lmHmisFlammability")) {
			return ATTRIBUTE_LM_HMIS_FLAMMABILITY;
		}
		else if(attributeName.equals("lmHmisHealth")) {
			return ATTRIBUTE_LM_HMIS_HEALTH;
		}
		else if(attributeName.equals("lmHmisReactivity")) {
			return ATTRIBUTE_LM_HMIS_REACTIVITY;
		}
		else if(attributeName.equals("lmHmisSource")) {
			return ATTRIBUTE_LM_HMIS_SOURCE;
		}
		else if(attributeName.equals("lmIncompatible")) {
			return ATTRIBUTE_LM_INCOMPATIBLE;
		}
		else if(attributeName.equals("lmIngestion")) {
			return ATTRIBUTE_LM_INGESTION;
		}
		else if(attributeName.equals("lmInhalation")) {
			return ATTRIBUTE_LM_INHALATION;
		}
		else if(attributeName.equals("lmInjection")) {
			return ATTRIBUTE_LM_INJECTION;
		}
		else if(attributeName.equals("lmInsertDate")) {
			return ATTRIBUTE_LM_INSERT_DATE;
		}
		else if(attributeName.equals("lmLfcCode")) {
			return ATTRIBUTE_LM_LFC_CODE;
		}
		else if(attributeName.equals("lmMaterialId")) {
			return ATTRIBUTE_LM_MATERIAL_ID;
		}
		else if(attributeName.equals("lmMixture")) {
			return ATTRIBUTE_LM_MIXTURE;
		}
		else if(attributeName.equals("lmNfpaSource")) {
			return ATTRIBUTE_LM_NFPA_SOURCE;
		}
		else if(attributeName.equals("lmOnLine")) {
			return ATTRIBUTE_LM_ON_LINE;
		}
		else if(attributeName.equals("lmOshaHazard")) {
			return ATTRIBUTE_LM_OSHA_HAZARD;
		}
		else if(attributeName.equals("lmOxidizer")) {
			return ATTRIBUTE_LM_OXIDIZER;
		}
		else if(attributeName.equals("lmPersonalProtection")) {
			return ATTRIBUTE_LM_PERSONAL_PROTECTION;
		}
		else if(attributeName.equals("lmPh")) {
			return ATTRIBUTE_LM_PH;
		}
		else if(attributeName.equals("lmPhysicalState")) {
			return ATTRIBUTE_LM_PHYSICAL_STATE;
		}
		else if(attributeName.equals("lmPhDetail")) {
			return ATTRIBUTE_LM_PH_DETAIL;
		}
		else if(attributeName.equals("lmPhDetect")) {
			return ATTRIBUTE_LM_PH_DETECT;
		}
		else if(attributeName.equals("lmPhSource")) {
			return ATTRIBUTE_LM_PH_SOURCE;
		}
		else if(attributeName.equals("lmPhUpper")) {
			return ATTRIBUTE_LM_PH_UPPER;
		}
		else if(attributeName.equals("lmPolymerize")) {
			return ATTRIBUTE_LM_POLYMERIZE;
		}
		else if(attributeName.equals("lmPpe")) {
			return ATTRIBUTE_LM_PPE;
		}
		else if(attributeName.equals("lmReactivity")) {
			return ATTRIBUTE_LM_REACTIVITY;
		}
		else if(attributeName.equals("lmRemark")) {
			return ATTRIBUTE_LM_REMARK;
		}
		else if(attributeName.equals("lmReviewedBy")) {
			return ATTRIBUTE_LM_REVIEWED_BY;
		}
		else if(attributeName.equals("lmReviewDate")) {
			return ATTRIBUTE_LM_REVIEW_DATE;
		}
		else if(attributeName.equals("lmRouteOfEntry")) {
			return ATTRIBUTE_LM_ROUTE_OF_ENTRY;
		}
		else if(attributeName.equals("lmSara311312Acute")) {
			return ATTRIBUTE_LM_SARA_311_312_ACUTE;
		}
		else if(attributeName.equals("lmSara311312Chronic")) {
			return ATTRIBUTE_LM_SARA_311_312_CHRONIC;
		}
		else if(attributeName.equals("lmSara311312Fire")) {
			return ATTRIBUTE_LM_SARA_311_312_FIRE;
		}
		else if(attributeName.equals("lmSara311312Pressure")) {
			return ATTRIBUTE_LM_SARA_311_312_PRESSURE;
		}
		else if(attributeName.equals("lmSara311312Reactivity")) {
			return ATTRIBUTE_LM_SARA_311_312_REACTIVITY;
		}
		else if(attributeName.equals("lmSignalWord")) {
			return ATTRIBUTE_LM_SIGNAL_WORD;
		}
		else if(attributeName.equals("lmSkin")) {
			return ATTRIBUTE_LM_SKIN;
		}
		else if(attributeName.equals("lmSolids")) {
			return ATTRIBUTE_LM_SOLIDS;
		}
		else if(attributeName.equals("lmSolidsLower")) {
			return ATTRIBUTE_LM_SOLIDS_LOWER;
		}
		else if(attributeName.equals("lmSolidsLowerPercent")) {
			return ATTRIBUTE_LM_SOLIDS_LOWER_PERCENT;
		}
		else if(attributeName.equals("lmSolidsPercent")) {
			return ATTRIBUTE_LM_SOLIDS_PERCENT;
		}
		else if(attributeName.equals("lmSolidsSource")) {
			return ATTRIBUTE_LM_SOLIDS_SOURCE;
		}
		else if(attributeName.equals("lmSolidsUnit")) {
			return ATTRIBUTE_LM_SOLIDS_UNIT;
		}
		else if(attributeName.equals("lmSolidsUpper")) {
			return ATTRIBUTE_LM_SOLIDS_UPPER;
		}
		else if(attributeName.equals("lmSolidsUpperPercent")) {
			return ATTRIBUTE_LM_SOLIDS_UPPER_PERCENT;
		}
		else if(attributeName.equals("lmSpecificGravity")) {
			return ATTRIBUTE_LM_SPECIFIC_GRAVITY;
		}
		else if(attributeName.equals("lmSpecificGravityBasis")) {
			return ATTRIBUTE_LM_SPECIFIC_GRAVITY_BASIS;
		}
		else if(attributeName.equals("lmSpecificGravityDetect")) {
			return ATTRIBUTE_LM_SPECIFIC_GRAVITY_DETECT;
		}
		else if(attributeName.equals("lmSpecificGravityLower")) {
			return ATTRIBUTE_LM_SPECIFIC_GRAVITY_LOWER;
		}
		else if(attributeName.equals("lmSpecificGravityUpper")) {
			return ATTRIBUTE_LM_SPECIFIC_GRAVITY_UPPER;
		}
		else if(attributeName.equals("lmSpecificHazard")) {
			return ATTRIBUTE_LM_SPECIFIC_HAZARD;
		}
		else if(attributeName.equals("lmStable")) {
			return ATTRIBUTE_LM_STABLE;
		}
		else if(attributeName.equals("lmStorageTemp")) {
			return ATTRIBUTE_LM_STORAGE_TEMP;
		}
		else if(attributeName.equals("lmTargetOrgan")) {
			return ATTRIBUTE_LM_TARGET_ORGAN;
		}
		else if(attributeName.equals("lmTsca12b")) {
			return ATTRIBUTE_LM_TSCA_12B;
		}
		else if(attributeName.equals("lmTscaList")) {
			return ATTRIBUTE_LM_TSCA_LIST;
		}
		else if(attributeName.equals("lmVaporDensity")) {
			return ATTRIBUTE_LM_VAPOR_DENSITY;
		}
		else if(attributeName.equals("lmVaporPressure")) {
			return ATTRIBUTE_LM_VAPOR_PRESSURE;
		}
		else if(attributeName.equals("lmVaporPressureDetect")) {
			return ATTRIBUTE_LM_VAPOR_PRESSURE_DETECT;
		}
		else if(attributeName.equals("lmVaporPressureSource")) {
			return ATTRIBUTE_LM_VAPOR_PRESSURE_SOURCE;
		}
		else if(attributeName.equals("lmVaporPressureTemp")) {
			return ATTRIBUTE_LM_VAPOR_PRESSURE_TEMP;
		}
		else if(attributeName.equals("lmVaporPressureTempUnit")) {
			return ATTRIBUTE_LM_VAPOR_PRESSURE_TEMP_UNIT;
		}
		else if(attributeName.equals("lmVaporPressureUnit")) {
			return ATTRIBUTE_LM_VAPOR_PRESSURE_UNIT;
		}
		else if(attributeName.equals("lmVaporPressureUpper")) {
			return ATTRIBUTE_LM_VAPOR_PRESSURE_UPPER;
		}
		else if(attributeName.equals("lmVoc")) {
			return ATTRIBUTE_LM_VOC;
		}
		else if(attributeName.equals("lmVocLbPerSolidLb")) {
			return ATTRIBUTE_LM_VOC_LB_PER_SOLID_LB;
		}
		else if(attributeName.equals("lmVocLbPerSolidLbLower")) {
			return ATTRIBUTE_LM_VOC_LB_PER_SOLID_LB_LOWER;
		}
		else if(attributeName.equals("lmVocLbPerSolidLbSource")) {
			return ATTRIBUTE_LM_VOC_LB_PER_SOLID_LB_SOURCE;
		}
		else if(attributeName.equals("lmVocLbPerSolidLbUpper")) {
			return ATTRIBUTE_LM_VOC_LB_PER_SOLID_LB_UPPER;
		}
		else if(attributeName.equals("lmVocLessH20ExemptSource")) {
			return ATTRIBUTE_LM_VOC_LESS_H20_EXEMPT_SOURCE;
		}
		else if(attributeName.equals("lmVocLessH2oExempt")) {
			return ATTRIBUTE_LM_VOC_LESS_H2O_EXEMPT;
		}
		else if(attributeName.equals("lmVocLessH2oExemptLower")) {
			return ATTRIBUTE_LM_VOC_LESS_H2O_EXEMPT_LOWER;
		}
		else if(attributeName.equals("lmVocLessH2oExemptSource")) {
			return ATTRIBUTE_LM_VOC_LESS_H2O_EXEMPT_SOURCE;
		}
		else if(attributeName.equals("lmVocLessH2oExemptUnit")) {
			return ATTRIBUTE_LM_VOC_LESS_H2O_EXEMPT_UNIT;
		}
		else if(attributeName.equals("lmVocLessH2oExemptUpper")) {
			return ATTRIBUTE_LM_VOC_LESS_H2O_EXEMPT_UPPER;
		}
		else if(attributeName.equals("lmVocLower")) {
			return ATTRIBUTE_LM_VOC_LOWER;
		}
		else if(attributeName.equals("lmVocLowerPercent")) {
			return ATTRIBUTE_LM_VOC_LOWER_PERCENT;
		}
		else if(attributeName.equals("lmVocPercent")) {
			return ATTRIBUTE_LM_VOC_PERCENT;
		}
		else if(attributeName.equals("lmVocSource")) {
			return ATTRIBUTE_LM_VOC_SOURCE;
		}
		else if(attributeName.equals("lmVocUnit")) {
			return ATTRIBUTE_LM_VOC_UNIT;
		}
		else if(attributeName.equals("lmVocUpper")) {
			return ATTRIBUTE_LM_VOC_UPPER;
		}
		else if(attributeName.equals("lmVocUpperPercent")) {
			return ATTRIBUTE_LM_VOC_UPPER_PERCENT;
		}
		else if(attributeName.equals("lmWaterReactive")) {
			return ATTRIBUTE_LM_WATER_REACTIVE;
		}
		else if(attributeName.equals("lockheedMsds")) {
			return ATTRIBUTE_LOCKHEED_MSDS;
		}
		else if(attributeName.equals("lmSpecificGravitySource")) {
			return ATTRIBUTE_LM_SPECIFIC_GRAVITY_SOURCE;
		}
		else if(attributeName.equals("lmDensitySource")) {
			return ATTRIBUTE_LM_DENSITY_SOURCE;
		}
		else if(attributeName.equals("productCode")) {
			return ATTRIBUTE_PRODUCT_CODE;
		}
		else if(attributeName.equals("compositionVar")) {
			return ATTRIBUTE_COMPOSITION_VAR;
		}
        else if(attributeName.equals("mfgShortName")) {
			return ATTRIBUTE_MFG_SHORT_NAME;
		}
        else if(attributeName.equals("msdsVerified")) {
			return ATTRIBUTE_MSDS_VERIFIED;
		}
        else if(attributeName.equals("verifiedBy")) {
			return ATTRIBUTE_VERIFIED_BY;
		}
        else if(attributeName.equals("verifiedOn")) {
			return ATTRIBUTE_VERIFIED_ON;
		}
        else if(attributeName.equals("verifiedDate")) {
			return ATTRIBUTE_VERIFIED_ON;
		}
        else if(attributeName.equals("idOnly")) {
			return ATTRIBUTE_ID_ONLY;
		}
        else if(attributeName.equals("verifiedByName")) {
			return ATTRIBUTE_VERIFIED_BY_NAME;
		}
        else if(attributeName.equals("onLine")) {
        	return ATTRIBUTE_ON_LINE;
        }
        else if(attributeName.equals("msdsAuthorId")) {
        	return ATTRIBUTE_MSDS_AUTHOR_ID;
        }
        else if(attributeName.equals("msdsAuthorDesc")) {
        	return ATTRIBUTE_MSDS_AUTHOR_DESC;
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
        else {
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) {
		return super.getType(attributeName, CatalogAddReqMsdsQcOvBean.class);
	}

	public int delete(SearchCriteria criteria)
		throws BaseException {

		Connection connection = null;
		int result = 0;
		try {
			connection = getDbManager().getConnection();
			result = delete(criteria, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return result;
	}


	public int delete(SearchCriteria criteria, Connection conn)
		throws BaseException {

		String sqlQuery = " delete from " + TABLE + " " +
			getWhereClause(criteria);

		return new SqlManager().update(conn, sqlQuery);
	}


	//select
	public Collection select(SearchCriteria criteria)
		throws BaseException {

		return select(criteria,null);

		}
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria)
		throws BaseException {

		Connection connection = null;
		Collection c = null;
		try {
			connection = this.getDbManager().getConnection();
			c = select(criteria,sortCriteria, connection);
		}
		finally {
			this.getDbManager().returnConnection(connection);
		}
		return c;
	}
	public Collection select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn)
		throws BaseException {

		Collection catalogAddReqMsdsQcOvBeanColl = new Vector();

		String query = "select * from " + TABLE + " " +
			getWhereClause(criteria)+ getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow)dataIter.next();
			CatalogAddReqMsdsQcOvBean catalogAddReqMsdsQcOvBean = new CatalogAddReqMsdsQcOvBean();
			load(dataSetRow, catalogAddReqMsdsQcOvBean);
			catalogAddReqMsdsQcOvBeanColl.add(catalogAddReqMsdsQcOvBean);
		}

		return catalogAddReqMsdsQcOvBeanColl;
	}

	public Collection selectObject(SearchCriteria criteria) throws BaseException {
	 return selectObject(criteria,null);
	 }

	public Collection selectObject(SearchCriteria criteria, SortCriteria sortCriteria) throws BaseException {
		Connection connection = null;
		Collection c = null;
		try {
		 connection = this.getDbManager().getConnection();
		 java.util.Map map = connection.getTypeMap();
//		 map.put("TCM_OPS.CATALOG_ADD_REQ_MSDS_QC_OBJ",
//			Class.forName(
//			"com.tcmis.client.openCustomer.beans.CatalogAddReqMsdsQcOvBean"));

		 c = selectObject(criteria, sortCriteria,connection);
		}
		catch (Exception e) {
		 log.error("selectObject error:" + e.getMessage());
		 DbSelectException ex = new DbSelectException("error.db.select");
		 ex.setRootCause(e);
		 throw ex;
		}
		finally {
		 this.getDbManager().returnConnection(connection);
		}
		return c;
	 }

	 public Collection selectObject(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws
		BaseException {
		Collection catalogAddReqMsdsQcOvBeanColl = new Vector();

		String query = "select value(p) from " + TABLE + " p " +
		 getWhereClause(criteria) + getOrderByClause(sortCriteria);
		 log.debug("QUERY:" + query);
			try {
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(query);
				while (rs.next()) {
					CatalogAddReqMsdsQcOvBean b = new CatalogAddReqMsdsQcOvBean();
					b.readObject(rs.getObject(1));
                    catalogAddReqMsdsQcOvBeanColl.add(b);
				}
				rs.close();
				st.close();
			}
			catch (SQLException e) {
				log.error("selectObject error:" + e.getMessage());
				DbSelectException ex = new DbSelectException("error.db.select");
				ex.setRootCause(e);
				throw ex;
			}
			return catalogAddReqMsdsQcOvBeanColl;
		}


	/*//get column name
	@Override
	public String getColumnName(String attributeName) {
		String columnName = tmpHash.get(attributeName);
		if (columnName == null) {
			columnName = StringHandler.convertBeanPropertyToDatabaseColumn(attributeName);
			tmpHash.put(attributeName,columnName);
		}
		return columnName;
	}

	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, CatalogAddRequestOvBean.class);
	}

	public Collection<com.tcmis.internal.distribution.beans.CatalogAddReqMsdsQcOvBean> select(SearchCriteria criteria, SortCriteria sort) throws BaseException {
		Connection connection = null;
		Collection<com.tcmis.internal.distribution.beans.CatalogAddReqMsdsQcOvBean> results = new Vector<com.tcmis.internal.distribution.beans.CatalogAddReqMsdsQcOvBean>();
		try {
			connection = getDbManager().getConnection();

			Map<String, Class<?>>  connTypeMap = connection.getTypeMap();
			connTypeMap.put(CatalogAddReqMsdsQcOvBean.SQLTypeName, Class.forName("com.tcmis.internal.catalog.beans.CatalogAddReqMsdsQcOvBean"));
			connTypeMap.put(CatalogAddReqCompQcObjBean.SQLTypeName, Class.forName("com.tcmis.internal.catalog.beans.CatalogAddReqCompQcObjBean"));

			String query = new StringBuilder("select value(p) from ").append(TABLE).append(" p ").append(getWhereClause(criteria)).append(getOrderByClause(sort)).toString();
			log.debug(query);
			try {
				Statement st = connection.createStatement();
				ResultSet rs = st.executeQuery(query);
				while (rs.next()) {
					Object o = rs.getObject(1);
					results.add((com.tcmis.internal.distribution.beans.CatalogAddReqMsdsQcOvBean) rs.getObject(1));
				}
				rs.close();
				st.close();
			}
			catch (SQLException e) {
				log.error("selectObject error:" + e.getMessage());
				DbSelectException ex = new DbSelectException("error.db.select");
				ex.setRootCause(e);
				throw ex;
			}
			MsdsBeanFactory mbFactory = new MsdsBeanFactory(getDbManager());
			for (com.tcmis.internal.distribution.beans.CatalogAddReqMsdsQcOvBean bean : results) {
				if (bean.getMaterialId() != null) {
					bean.setAvailableRevisionDates(mbFactory.selectAllRevisionDates(bean.getMaterialId(), connection));
				}
			}
		}
		catch (Exception e) {
			log.error("select error:" + e.getMessage(), e);
			DbSelectException ex = new DbSelectException("error.db.select");

			ex.setRootCause(e);
			throw ex;
		}
		finally {
			getDbManager().returnConnection(connection);
		}

		return results;
	}*/

}