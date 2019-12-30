package com.tcmis.client.common.factory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.common.beans.MsdsIndexingBean;
import com.tcmis.common.admin.beans.PersonnelBean;
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
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.catalog.beans.GHSStatementsViewBean;
import com.tcmis.internal.catalog.beans.MsdsBean;
import com.tcmis.internal.catalog.beans.PictogramBean;
import com.tcmis.internal.catalog.factory.MsdsBeanFactory;

public class MsdsIndexingBeanFactory extends BaseBeanFactory {

	// MSDS Fields
	public String ATTRIBUTE_MATERIAL_ID = "MATERIAL_ID";
	public String ATTRIBUTE_REVISION_DATE = "REVISION_DATE";
	public String ATTRIBUTE_MSDS_AUTHOR_ID = "MSDS_AUTHOR_ID";
	public String ATTRIBUTE_CONTENT = "CONTENT";
	public String ATTRIBUTE_ID_ONLY = "ID_ONLY";
	public String ATTRIBUTE_MSDS_INDEXING_PRIORITY_ID = "MSDS_INDEXING_PRIORITY_ID";
	public String ATTRIBUTE_DATA_ENTRY_COMPLETE = "DATA_ENTRY_COMPLETE";
	public String ATTRIBUTE_GHS_COMPLIANT_IMAGE = "GHS_COMPLIANT_IMAGE";
	
	//TODO: private String componentMsds;
	public String ATTRIBUTE_SIGNAL_WORD = "SIGNAL_WORD";
	public String ATTRIBUTE_LABEL_COMPANY_NAME = "LABEL_COMPANY_NAME";
	public String ATTRIBUTE_LABEL_ADDRESS_LINE_1 = "LABEL_ADDRESS_LINE_1";
	public String ATTRIBUTE_LABEL_ADDRESS_LINE_2 = "LABEL_ADDRESS_LINE_2";
	public String ATTRIBUTE_LABEL_ADDRESS_LINE_3 = "LABEL_ADDRESS_LINE_3";
	public String ATTRIBUTE_LABEL_ADDRESS_LINE_4 = "LABEL_ADDRESS_LINE_4";
	public String ATTRIBUTE_LABEL_CITY = "LABEL_CITY";
	public String ATTRIBUTE_LABEL_COUNTRY_ABBREV = "LABEL_COUNTRY_ABBREV";
	public String ATTRIBUTE_LABEL_STATE_ABBREV = "LABEL_STATE_ABBREV";
	public String ATTRIBUTE_LABEL_ZIP = "LABEL_ZIP";
	public String ATTRIBUTE_LABEL_PHONE = "LABEL_PHONE";
	public String ATTRIBUTE_MSDS_ID = "MSDS_ID";
	public String ATTRIBUTE_GHS_HAZARD = "GHS_HAZARD";
	
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
	
	public String ATTRIBUTE_PH_DETECT = "PH_DETECT";
	public String ATTRIBUTE_PH = "PH";
	public String ATTRIBUTE_PH_UPPER = "PH_UPPER";
	public String ATTRIBUTE_PH_DETAIL = "PH_DETAIL";
	public String ATTRIBUTE_PH_SOURCE = "PH_SOURCE";
	
	public String ATTRIBUTE_PHYSICAL_STATE = "PHYSICAL_STATE";
	public String ATTRIBUTE_PHYSICAL_STATE_SOURCE = "PHYSICAL_STATE_SOURCE";
	
	public String ATTRIBUTE_SARA_311_312_ACUTE = "SARA_311_312_ACUTE";
	public String ATTRIBUTE_SARA_311_312_CHRONIC = "SARA_311_312_CHRONIC";
	public String ATTRIBUTE_SARA_311_312_FIRE = "SARA_311_312_FIRE";
	public String ATTRIBUTE_SARA_311_312_PRESSURE = "SARA_311_312_PRESSURE";
	public String ATTRIBUTE_SARA_311_312_REACTIVITY = "SARA_311_312_REACTIVITY";
	public String ATTRIBUTE_VAPOR_DENSITY = "VAPOR_DENSITY";
	public String ATTRIBUTE_EVAPORATION_RATE = "EVAPORATION_RATE";
	
	public String ATTRIBUTE_DENSITY_DETECT = "DENSITY_DETECT";
	public String ATTRIBUTE_DENSITY = "DENSITY";
	public String ATTRIBUTE_DENSITY_UPPER = "DENSITY_UPPER";
	public String ATTRIBUTE_DENSITY_UNIT = "DENSITY_UNIT";
	public String ATTRIBUTE_DENSITY_SOURCE = "DENSITY_SOURCE";

	public String ATTRIBUTE_FLASH_POINT_DETECT = "FLASH_POINT_DETECT";
	public String ATTRIBUTE_FLASH_POINT_LOWER = "FLASH_POINT_LOWER";
	public String ATTRIBUTE_FLASH_POINT_UPPER = "FLASH_POINT_UPPER";
	public String ATTRIBUTE_FLASH_POINT_UNIT = "FLASH_POINT_UNIT";
	public String ATTRIBUTE_FLASH_POINT_METHOD = "FLASH_POINT_METHOD";
	public String ATTRIBUTE_FLASH_POINT_SOURCE = "FLASH_POINT_SOURCE";
	
	public String ATTRIBUTE_BOILING_POINT_DETECT = "BOILING_POINT_DETECT";
	public String ATTRIBUTE_BOILING_POINT_LOWER = "BOILING_POINT_LOWER";
	public String ATTRIBUTE_BOILING_POINT_UPPER = "BOILING_POINT_UPPER";
	public String ATTRIBUTE_BOILING_POINT_UNIT = "BOILING_POINT_UNIT";
	public String ATTRIBUTE_BOILING_POINT_DETAIL = "BOILING_POINT_DETAIL";
	public String ATTRIBUTE_BOILING_POINT_SOURCE = "BOILING_POINT_SOURCE";
	
	public String ATTRIBUTE_SPECIFIC_GRAVITY_DETECT = "SPECIFIC_GRAVITY_DETECT";
	public String ATTRIBUTE_SPECIFIC_GRAVITY_LOWER = "SPECIFIC_GRAVITY_LOWER";
	public String ATTRIBUTE_SPECIFIC_GRAVITY_UPPER = "SPECIFIC_GRAVITY_UPPER";
	public String ATTRIBUTE_SPECIFIC_GRAVITY_BASIS = "SPECIFIC_GRAVITY_BASIS";
	public String ATTRIBUTE_SPECIFIC_GRAVITY_SOURCE = "SPECIFIC_GRAVITY_SOURCE";
	
	public String ATTRIBUTE_VAPOR_PRESSURE_DETECT = "VAPOR_PRESSURE_DETECT";
	public String ATTRIBUTE_VAPOR_PRESSURE = "VAPOR_PRESSURE";
	public String ATTRIBUTE_VAPOR_PRESSURE_UPPER = "VAPOR_PRESSURE_UPPER";
	public String ATTRIBUTE_VAPOR_PRESSURE_UNIT = "VAPOR_PRESSURE_UNIT";
	public String ATTRIBUTE_VAPOR_PRESSURE_TEMP = "VAPOR_PRESSURE_TEMP";
	public String ATTRIBUTE_VAPOR_PRESSURE_TEMP_UNIT = "VAPOR_PRESSURE_TEMP_UNIT";
	public String ATTRIBUTE_VAPOR_PRESSURE_SOURCE = "VAPOR_PRESSURE_SOURCE";
	
	public String ATTRIBUTE_SOLIDS = "SOLIDS";
	public String ATTRIBUTE_SOLIDS_LOWER = "SOLIDS_LOWER";
	public String ATTRIBUTE_SOLIDS_UPPER = "SOLIDS_UPPER";
	public String ATTRIBUTE_SOLIDS_UNIT = "SOLIDS_UNIT";
	public String ATTRIBUTE_SOLIDS_SOURCE = "SOLIDS_SOURCE";
	
	public String ATTRIBUTE_VOC = "VOC";
	public String ATTRIBUTE_VOC_LOWER = "VOC_LOWER";
	public String ATTRIBUTE_VOC_UPPER = "VOC_UPPER";
	public String ATTRIBUTE_VOC_UNIT = "VOC_UNIT";
	public String ATTRIBUTE_VOC_SOURCE = "VOC_SOURCE";
	
	public String ATTRIBUTE_VOC_LESS_H2O_EXEMPT = "VOC_LESS_H2O_EXEMPT";
	public String ATTRIBUTE_VOC_LESS_H2O_EXEMPT_LOWER = "VOC_LESS_H2O_EXEMPT_LOWER";
	public String ATTRIBUTE_VOC_LESS_H2O_EXEMPT_UPPER = "VOC_LESS_H2O_EXEMPT_UPPER";
	public String ATTRIBUTE_VOC_LESS_H2O_EXEMPT_UNIT = "VOC_LESS_H2O_EXEMPT_UNIT";
	public String ATTRIBUTE_VOC_LESS_H2O_EXEMPT_SOURCE = "VOC_LESS_H2O_EXEMPT_SOURCE";

	public String ATTRIBUTE_REMARK = "REMARK";
	public String ATTRIBUTE_ALT_DATA_SOURCE = "ALT_DATA_SOURCE";
	
	public String ATTRIBUTE_ON_LINE = "ON_LINE";
	public String ATTRIBUTE_REVIEWED_BY = "REVIEWED_BY";
	public String ATTRIBUTE_VERIFIED_BY = "VERIFIED_BY";
	public String ATTRIBUTE_VERIFIED_ON = "VERIFIED_ON";
	public String ATTRIBUTE_REVIEW_DATE = "REVIEW_DATE";
	public String ATTRIBUTE_INSERT_DATE = "INSERT_DATE";
	public String ATTRIBUTE_HEALTH_EFFECTS = "HEALTH_EFFECTS";

	public String ATTRIBUTE_LOCALE_CODE = "LOCALE_CODE";
	
	public String ATTRIBUTE_SDS_REQUIRED = "SDS_REQUIRED";



    //table name
	public String MSDS_TABLE = "GLOBAL.MSDS";
	public String CO_TABLE = "customer.company_msds";
	public String LOCALE_TABLE = "GLOBAL.MSDS_LOCALE";
	public String MATERIAL_TABLE = "material";
	public String PICTOGRAM_TABLE = "global.msds_ghs_pictogram";
	public String GHS_STMT_TABLE = "msds_admin.msds_ghs_statement";
	
	Log log = LogFactory.getLog(getClass());

	//constructor
	public MsdsIndexingBeanFactory(DbManager dbManager) {
		super(dbManager);
	}
	
	//insert
	public int insertMsds(MsdsIndexingBean msdsBean) throws BaseException {

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
	
	public int source(MsdsIndexingBean msdsBean, Connection conn) throws BaseException {
		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + MSDS_TABLE + " (" +
			ATTRIBUTE_MATERIAL_ID + "," +
			ATTRIBUTE_REVISION_DATE + "," +
			ATTRIBUTE_CONTENT +
			ATTRIBUTE_SDS_REQUIRED;
			if ( ! StringHandler.isBlankString(msdsBean.getImageLocaleCode())) {
				query += ","+ ATTRIBUTE_LOCALE_CODE;
			}
            query += ")" +
			" values (" +
			msdsBean.getMaterialId() + "," +
			DateHandler.getOracleToDateFunction(msdsBean.getRevisionDate()) + "," +
			SqlHandler.delimitString(msdsBean.getContent()) + "," +
			SqlHandler.delimitString(msdsBean.getSdsRequired());
            
            if ( ! StringHandler.isBlankString(msdsBean.getImageLocaleCode())) {
				query += ","+ SqlHandler.delimitString(msdsBean.getImageLocaleCode());
			}
            query += ")";
            
       return sqlManager.update(conn, query);
	}

	public int insert(MsdsIndexingBean msdsBean, Connection conn)
	throws BaseException {

		SqlManager sqlManager = new SqlManager();

		String query  =
			"insert into " + MSDS_TABLE + " (" +
			ATTRIBUTE_MATERIAL_ID + "," +
			ATTRIBUTE_REVISION_DATE + "," +
			ATTRIBUTE_CONTENT + "," +
			ATTRIBUTE_SDS_REQUIRED + "," +
			ATTRIBUTE_HEALTH + "," +
			ATTRIBUTE_FLAMMABILITY + "," +
			ATTRIBUTE_REACTIVITY + "," +
			ATTRIBUTE_SPECIFIC_HAZARD + "," +
			ATTRIBUTE_SIGNAL_WORD + "," +
			ATTRIBUTE_PH + "," +
			ATTRIBUTE_DENSITY + "," +
			ATTRIBUTE_ON_LINE + "," +
			ATTRIBUTE_DENSITY_UNIT + "," +
			ATTRIBUTE_PHYSICAL_STATE + "," +
			ATTRIBUTE_VOC_UNIT + "," +
			ATTRIBUTE_SARA_311_312_ACUTE + "," +
			ATTRIBUTE_SARA_311_312_CHRONIC + "," +
			ATTRIBUTE_SARA_311_312_FIRE + "," +
			ATTRIBUTE_SARA_311_312_PRESSURE + "," +
			ATTRIBUTE_SARA_311_312_REACTIVITY + "," +
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
			ATTRIBUTE_VOC_LESS_H2O_EXEMPT + "," +
			ATTRIBUTE_VOC_LESS_H2O_EXEMPT_UNIT + "," +
			ATTRIBUTE_VOC_LESS_H2O_EXEMPT_LOWER + "," +
			ATTRIBUTE_VOC_LESS_H2O_EXEMPT_UPPER + "," +
			ATTRIBUTE_VOC_SOURCE + "," +
			ATTRIBUTE_VOC_LESS_H2O_EXEMPT_SOURCE + "," +
			ATTRIBUTE_SOLIDS_SOURCE + "," +
			ATTRIBUTE_VAPOR_PRESSURE_SOURCE + "," +
			ATTRIBUTE_VAPOR_PRESSURE_UPPER + "," +
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
			ATTRIBUTE_GHS_HAZARD; 
			if ( ! StringHandler.isBlankString(msdsBean.getImageLocaleCode())) {
				query += ","+ ATTRIBUTE_LOCALE_CODE;
			}
            query += ")" +
			" values (" +
			msdsBean.getMaterialId() + "," +
			DateHandler.getOracleToDateFunction(msdsBean.getRevisionDate()) + "," +
			SqlHandler.delimitString(msdsBean.getContent()) + "," +
			SqlHandler.delimitString(msdsBean.getSdsRequired()) + "," +
			SqlHandler.delimitString(msdsBean.getHealth()) + "," +
			SqlHandler.delimitString(msdsBean.getFlammability()) + "," +
			SqlHandler.delimitString(msdsBean.getReactivity()) + "," +
			SqlHandler.delimitString(msdsBean.getSpecificHazard()) + "," +
			SqlHandler.delimitString(msdsBean.getSignalWord()) + "," +
			msdsBean.getPh() + "," +
			msdsBean.getDensity() + "," +
			SqlHandler.delimitString(msdsBean.getOnLine()) + "," +
			SqlHandler.delimitString(msdsBean.getDensityUnit()) + "," +
			SqlHandler.delimitString(msdsBean.getPhysicalState()) + "," +
			SqlHandler.delimitString(msdsBean.getVocUnit()) + "," +
			SqlHandler.delimitString(msdsBean.getSara311312Acute()) + "," +
			SqlHandler.delimitString(msdsBean.getSara311312Chronic()) + "," +
			SqlHandler.delimitString(msdsBean.getSara311312Fire()) + "," +
			SqlHandler.delimitString(msdsBean.getSara311312Pressure()) + "," +
			SqlHandler.delimitString(msdsBean.getSara311312Reactivity()) + "," +
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
			msdsBean.getVocLessH2oExempt() + "," +
			SqlHandler.delimitString(msdsBean.getVocLessH2oExemptUnit()) + "," +
			msdsBean.getVocLessH2oExemptLower() + "," +
			msdsBean.getVocLessH2oExemptUpper() + "," +
			SqlHandler.delimitString(msdsBean.getVocSource()) + "," +
			SqlHandler.delimitString(msdsBean.getVocLessH2oExemptSource()) + "," +
			SqlHandler.delimitString(msdsBean.getSolidsSource()) + "," +
			SqlHandler.delimitString(msdsBean.getVaporPressureSource()) + "," +
			msdsBean.getVaporPressureUpper() + "," +
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
            SqlHandler.delimitString(msdsBean.isGhsHazard() ? "N" : "Y"); // reversed; web-page says non-hazardous, DB says hazardous
            if ( ! StringHandler.isBlankString(msdsBean.getImageLocaleCode())) {
            	query += "," + SqlHandler.delimitString(msdsBean.getImageLocaleCode());
            }
            query += ")";

		//System.out.println(query);
		return sqlManager.update(conn, query);
	}

	//update
	public int update(MsdsIndexingBean msdsBean) throws BaseException {

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

	public int update(MsdsIndexingBean msdsBean, Connection conn) throws BaseException {
		return update(msdsBean, false, conn);
	}
	
	public int update(MsdsIndexingBean msdsBean, boolean sourcingOnly, Connection conn) throws BaseException {
		StringBuilder query = new StringBuilder("update ").append(MSDS_TABLE).append(" set ");
		query.append(ATTRIBUTE_CONTENT).append("=").append(SqlHandler.delimitString(msdsBean.getContent())).append(",");
		query.append(ATTRIBUTE_SDS_REQUIRED).append("=").append(SqlHandler.delimitString(msdsBean.getSdsRequired())).append(",");
		query.append(ATTRIBUTE_ON_LINE).append("=").append(SqlHandler.delimitString(msdsBean.getOnLine())).append(",");
        if ( ! StringHandler.isBlankString(msdsBean.getIdOnly())) {
        	query.append(ATTRIBUTE_ID_ONLY).append("=").append(SqlHandler.delimitString(msdsBean.getIdOnly())).append(",");
        }
        query.append(ATTRIBUTE_MSDS_AUTHOR_ID).append("=").append(msdsBean.getMsdsAuthorId());
		if ( ! sourcingOnly) {
			query.append(",").append(ATTRIBUTE_HEALTH).append("=").append(SqlHandler.delimitString(msdsBean.getHealth())).append(",");
			query.append(ATTRIBUTE_FLAMMABILITY).append("=").append(SqlHandler.delimitString(msdsBean.getFlammability())).append(",");
			query.append(ATTRIBUTE_REACTIVITY).append("=").append(SqlHandler.delimitString(msdsBean.getReactivity())).append(",");
			query.append(ATTRIBUTE_SPECIFIC_HAZARD).append("=").append(SqlHandler.delimitString(msdsBean.getSpecificHazard())).append(",");
			query.append(ATTRIBUTE_SIGNAL_WORD).append("=").append(SqlHandler.delimitString(msdsBean.getSignalWord())).append(",");
			query.append(ATTRIBUTE_PH).append("=").append(msdsBean.getPh()).append(",");
			query.append(ATTRIBUTE_DENSITY).append("=").append(msdsBean.getDensity()).append(",");
			query.append(ATTRIBUTE_DENSITY_UNIT).append("=").append(SqlHandler.delimitString(msdsBean.getDensityUnit())).append(",");
			query.append(ATTRIBUTE_PHYSICAL_STATE).append("=").append(SqlHandler.delimitString(msdsBean.getPhysicalState())).append(",");
			query.append(ATTRIBUTE_VOC_UNIT).append("=").append(SqlHandler.delimitString(msdsBean.getVocUnit())).append(",");
			query.append(ATTRIBUTE_SARA_311_312_ACUTE).append("=").append(SqlHandler.delimitString(msdsBean.getSara311312Acute())).append(",");
			query.append(ATTRIBUTE_SARA_311_312_CHRONIC).append("=").append(SqlHandler.delimitString(msdsBean.getSara311312Chronic())).append(",");
			query.append(ATTRIBUTE_SARA_311_312_FIRE).append("=").append(SqlHandler.delimitString(msdsBean.getSara311312Fire())).append(",");
			query.append(ATTRIBUTE_SARA_311_312_PRESSURE).append("=").append(SqlHandler.delimitString(msdsBean.getSara311312Pressure())).append(",");
			query.append(ATTRIBUTE_SARA_311_312_REACTIVITY).append("=").append(SqlHandler.delimitString(msdsBean.getSara311312Reactivity())).append(",");
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
			query.append(ATTRIBUTE_VOC_LESS_H2O_EXEMPT).append("=").append(msdsBean.getVocLessH2oExempt()).append(",");
			query.append(ATTRIBUTE_VOC_LESS_H2O_EXEMPT_UNIT).append("=").append(SqlHandler.delimitString(msdsBean.getVocLessH2oExemptUnit())).append(",");
			query.append(ATTRIBUTE_VOC_LESS_H2O_EXEMPT_LOWER).append("=").append(msdsBean.getVocLessH2oExemptLower()).append(",");
			query.append(ATTRIBUTE_VOC_LESS_H2O_EXEMPT_UPPER).append("=").append(msdsBean.getVocLessH2oExemptUpper()).append(",");
			query.append(ATTRIBUTE_VOC_SOURCE).append("=").append(SqlHandler.delimitString(msdsBean.getVocSource())).append(",");
			query.append(ATTRIBUTE_VOC_LESS_H2O_EXEMPT_SOURCE).append("=").append(SqlHandler.delimitString(msdsBean.getVocLessH2oExemptSource())).append(",");
			query.append(ATTRIBUTE_SOLIDS_SOURCE).append("=").append(SqlHandler.delimitString(msdsBean.getSolidsSource())).append(",");
			query.append(ATTRIBUTE_VAPOR_PRESSURE_SOURCE).append("=").append(SqlHandler.delimitString(msdsBean.getVaporPressureSource())).append(",");
			query.append(ATTRIBUTE_VAPOR_PRESSURE_UPPER).append("=").append(msdsBean.getVaporPressureUpper()).append(",");
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
			query.append(ATTRIBUTE_FLASH_POINT_UPPER).append("=").append(msdsBean.getFlashPointUpper()).append(",");
			query.append(ATTRIBUTE_FLASH_POINT_METHOD).append("=").append(SqlHandler.delimitString(msdsBean.getFlashPointMethod())).append(",");
			query.append(ATTRIBUTE_BOILING_POINT_DETECT).append("=").append(SqlHandler.delimitString(msdsBean.getBoilingPointDetect())).append(",");
			query.append(ATTRIBUTE_BOILING_POINT_SOURCE).append("=").append(SqlHandler.delimitString(msdsBean.getBoilingPointSource())).append(",");
			query.append(ATTRIBUTE_BOILING_POINT_LOWER).append("=").append(msdsBean.getBoilingPointLower()).append(",");
			query.append(ATTRIBUTE_BOILING_POINT_UPPER).append("=").append(msdsBean.getBoilingPointUpper()).append(","); 
			query.append(ATTRIBUTE_BOILING_POINT_UNIT).append("=").append(SqlHandler.delimitString(msdsBean.getBoilingPointUnit())).append(",");
			query.append(ATTRIBUTE_BOILING_POINT_DETAIL).append("=").append(SqlHandler.delimitString(msdsBean.getBoilingPointDetail())).append(",");
			query.append(ATTRIBUTE_PH_DETAIL).append("=").append(SqlHandler.delimitString(msdsBean.getPhDetail())).append(",");
			query.append(ATTRIBUTE_PH_SOURCE).append("=").append(SqlHandler.delimitString(msdsBean.getPhSource())).append(",");
			query.append(ATTRIBUTE_PH_DETECT).append("=").append(SqlHandler.delimitString(msdsBean.getPhDetect())).append(","); 
			query.append(ATTRIBUTE_PH_UPPER).append("=").append(msdsBean.getPhUpper()).append(",");
			query.append(ATTRIBUTE_VERIFIED_BY).append("=").append(msdsBean.getVerifiedBy()).append(",");
	        query.append(ATTRIBUTE_VERIFIED_ON).append("=").append(DateHandler.getOracleToDateFunction(msdsBean.getVerifiedOn())).append(",");
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
	        query.append(ATTRIBUTE_GHS_HAZARD).append("=").append(SqlHandler.delimitString(msdsBean.isGhsHazard() ? "N" : "Y"));
		}
        if (!StringHandler.isBlankString(msdsBean.getImageLocaleCode()))
            query.append(",").append(ATTRIBUTE_LOCALE_CODE).append("=").append(SqlHandler.delimitString(msdsBean.getImageLocaleCode()));
        query.append(" WHERE MATERIAL_ID = ").append(msdsBean.getMaterialId());
        query.append(" AND REVISION_DATE = ").append(DateHandler.getOracleToDateFunction(msdsBean.getRevisionDate()));

        //System.out.println(query.toString());
		return new SqlManager().update(conn, query.toString());
	}
	
	public Collection<MsdsIndexingBean> select(SearchCriteria criteria, SortCriteria sortCriteria, Connection conn) throws BaseException {

		Collection<MsdsIndexingBean> msdsBeanColl = new Vector<MsdsIndexingBean>();

		String query = "select * from " + MSDS_TABLE + " " + getWhereClause(criteria) + getOrderByClause(sortCriteria);

		DataSet dataSet = new SqlManager().select(conn, query);

		Iterator dataIter = dataSet.iterator();

		while (dataIter.hasNext()) {
			DataSetRow dataSetRow = (DataSetRow) dataIter.next();
			MsdsIndexingBean msdsBean = new MsdsIndexingBean();
			load(dataSetRow, msdsBean);
			msdsBeanColl.add(msdsBean);
		}

		return msdsBeanColl;
	}

	//get column names
	public String getColumnName(String attributeName) {
		if (attributeName.equals("materialId")) {
			return ATTRIBUTE_MATERIAL_ID;
		}else if (attributeName.equals("revisionDate")) {
			return ATTRIBUTE_REVISION_DATE;
		}else if (attributeName.equals("onLine")) {
			return ATTRIBUTE_ON_LINE;
		} else {
			return super.getColumnName(attributeName);
		}
	}
	//get column types
	@Override
	public int getType(String attributeName) {
		return super.getType(attributeName, MsdsIndexingBean.class);
	}
	
	public MsdsIndexingBean select(BigDecimal materialId, Date revisionDate, Connection conn) throws BaseException {
		Collection<MsdsIndexingBean> results = select(getKeyCriteria(materialId, revisionDate), null, conn);
		return results.isEmpty() ? null : results.iterator().next();
	}
	
	private SearchCriteria getKeyCriteria(MsdsIndexingBean msdsBean) {
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
	
	public void removeMsdsLocale(MsdsIndexingBean msdsBean, Connection conn) throws BaseException {
		StringBuilder query = new StringBuilder("delete from ").append(LOCALE_TABLE).append(" ");
		query.append(getWhereClause(getKeyCriteria(msdsBean)));
		//System.out.println(query.toString());
		new SqlManager().update(conn, query.toString());
	}
	
	public void setMsdsLocale(MsdsIndexingBean msdsBean, Connection conn) throws BaseException {
		StringBuilder query = new StringBuilder("select count(*) from  ").append(LOCALE_TABLE).append(" ").append(getWhereClause(getKeyCriteria(msdsBean)));
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
				//System.out.println(query.toString());
				new SqlManager().update(conn, query.toString());
			}
			//msds_locale
			query = new StringBuilder("insert into ").append(LOCALE_TABLE).append(" (").append(ATTRIBUTE_MATERIAL_ID).append(",");
			query.append(ATTRIBUTE_REVISION_DATE).append(",").append(ATTRIBUTE_CONTENT).append(",").append(ATTRIBUTE_ON_LINE).append(",");
			query.append(ATTRIBUTE_INSERT_DATE).append(",locale_code,").append(ATTRIBUTE_MSDS_AUTHOR_ID).append(")");
			query.append(" values (").append(msdsBean.getMaterialId()).append(",");
			query.append(DateHandler.getOracleToDateFunction(msdsBean.getRevisionDate())).append(",");
			query.append(SqlHandler.delimitString(msdsBean.getContent())).append(",");
			query.append("'Y',sysdate,").append(SqlHandler.delimitString(msdsBean.getImageLocaleCode())).append(",");
			query.append(msdsBean.getMsdsAuthorId()).append(")");
			//System.out.println(query.toString());
			new SqlManager().update(conn, query.toString());
		}else {
			query = new StringBuilder("update ").append(LOCALE_TABLE).append(" set ");
			query.append(ATTRIBUTE_CONTENT).append("=").append(SqlHandler.delimitString(msdsBean.getContent()));
			query.append(getWhereClause(getKeyCriteria(msdsBean))).append(" ");
			query.append(" and locale_code = ").append(SqlHandler.delimitString(msdsBean.getImageLocaleCode()));
			//System.out.println(query.toString());
			new SqlManager().update(conn, query.toString());
		}
	}
	
	public void updateStatements(MsdsIndexingBean msds, PersonnelBean personnel, Connection conn) throws BaseException {
		SqlManager sqlManager = new SqlManager();
		StringBuilder ghsStmtIds = new StringBuilder();
		StringBuilder updatesFromMsds = new StringBuilder();
		for (Iterator<GHSStatementsViewBean> i = msds.getHazardStmts().iterator();i.hasNext();) {
			GHSStatementsViewBean hStmt = i.next();
			if (hStmt.getGhsStatementId() != null) {
				if (ghsStmtIds.length() > 0) {
					ghsStmtIds.append(",");
				}
				ghsStmtIds.append(hStmt.getGhsStatementId());
				if (hStmt.getIsFromMsds().intValue() == 1) {
					if (updatesFromMsds.length() > 0) {
						updatesFromMsds.append(",");
					}
					updatesFromMsds.append(hStmt.getGhsStatementId());
				}
			}
		}
		for (Iterator<GHSStatementsViewBean> i = msds.getPrecautionaryStmts().iterator();i.hasNext();) {
			GHSStatementsViewBean pStmt = i.next();
			if (pStmt.getGhsStatementId() != null) {
				if (ghsStmtIds.length() > 0) {
					ghsStmtIds.append(",");
				}
				ghsStmtIds.append(pStmt.getGhsStatementId());
				if (pStmt.getIsFromMsds().intValue() == 1) {
					if (updatesFromMsds.length() > 0) {
						updatesFromMsds.append(",");
					}
					updatesFromMsds.append(pStmt.getGhsStatementId());
				}
			}
		}
		
		StringBuilder deleteStmt = new StringBuilder();
		StringBuilder ghsIdQuery = new StringBuilder();
		deleteStmt.append("delete (select mgs.* from msds_admin.msds_ghs_statement mgs, msds_admin.ghs_statement gs ");
		deleteStmt.append("where mgs.msds_id=").append(msds.getMsdsId());
		if (ghsStmtIds.length() > 0) {
			deleteStmt.append(" and mgs.ghs_statement_id not in(").append(ghsStmtIds).append(")");
			
			ghsIdQuery.append("select gs.id from msds_admin.ghs_statement gs where gs.id not in");
			ghsIdQuery.append("(select mgs.ghs_statement_id from msds_admin.msds_ghs_statement mgs where mgs.msds_id = ").append(msds.getMsdsId());
			ghsIdQuery.append(") and gs.id in(").append(ghsStmtIds).append(")");
		}
		//delete old statements
		//System.out.println(deleteStmt.toString());
		deleteStmt.append(" and mgs.ghs_statement_id = gs.id)");
		sqlManager.update(conn, deleteStmt.toString());
		
		DataSet newGhsStmtIds = null;
		if (ghsIdQuery.length() > 0) {
			newGhsStmtIds = sqlManager.select(conn, ghsIdQuery.toString());
		}
		
		if (newGhsStmtIds != null && ! newGhsStmtIds.isEmpty()) {
			boolean insertData = false;
			StringBuilder insertStmt = new StringBuilder("insert all ");
			
			for (Iterator<DataSetRow> it = newGhsStmtIds.iterator();it.hasNext();) {
				DataSetRow dsr = it.next();
				BigDecimal ghsStatementId = dsr.getBigDecimal("ID");
				if (ghsStatementId != null) {
					BigDecimal nextStmtId = nextMaxcomId(conn);
					// it is impossible to insert a Maxcom estimated statement using this function so always use 1 for is_from_sds 
					insertStmt.append("into MSDS_ADMIN.MSDS_GHS_STATEMENT (ID, CREATED_BY, CREATED_DATE, MSDS_ID, GHS_STATEMENT_ID, IS_FROM_MSDS) VALUES(");
					insertStmt.append(nextStmtId+","+personnel.getPersonnelId()+",sysdate,"+msds.getMsdsId()+","+ghsStatementId+",1)");
					insertData = true;
				}
			}
			
			//insert new statements
			if (insertData) {
				insertStmt.append("select * from dual");
				//System.out.println(insertStmt.toString());
				sqlManager.update(conn, insertStmt.toString());
			}
		}
		
		if (updatesFromMsds.length() > 0) {
			StringBuilder updateQuery = new StringBuilder("update MSDS_ADMIN.MSDS_GHS_STATEMENT set IS_FROM_MSDS = 1, ");
			updateQuery.append("UPDATED_BY = ").append(personnel.getPersonnelId()).append(", UPDATED_DATE = sysdate ");
			updateQuery.append("where MSDS_ID = ").append(msds.getMsdsId()).append(" and GHS_STATEMENT_ID in (");
			updateQuery.append(updatesFromMsds).append(") and IS_FROM_MSDS = 0");
			sqlManager.update(conn, updateQuery.toString());
		}
	}
	
	public void updatePictograms(MsdsIndexingBean msds, Connection conn) throws BaseException {
		SqlManager sqlManager = new SqlManager();
		StringBuilder deletePicStmt = new StringBuilder("delete from global.msds_ghs_pictogram ");
    	deletePicStmt.append("where material_id = "+msds.getMaterialId()+" and revision_date = "+DateHandler.getOracleToDateFunction(msds.getRevisionDate())); 
    	
    	StringBuilder insertPicStmt = new StringBuilder("insert all ");
    	boolean insertPics = false;
    	for (Iterator<PictogramBean> pictogramIt = msds.getPictograms().iterator(); pictogramIt.hasNext();) {
    		PictogramBean pic = pictogramIt.next();
    		if (pic != null && ! StringHandler.isBlankString(pic.getPictogramId())) {
	    		insertPicStmt.append("into global.msds_ghs_pictogram (material_id, revision_date, pictogram_id, on_sds) ");
	    		insertPicStmt.append("values ("+msds.getMaterialId()+","+DateHandler.getOracleToDateFunction(msds.getRevisionDate())+",'"+pic.getPictogramId()+"','"+(pic.getOnSds()?"Y":"N")+"') ");
	    		insertPics = true;
    		}
    	}
    	insertPicStmt.append("select * from dual");
    	
    	//System.out.println(deletePicStmt.toString());
		sqlManager.update(conn, deletePicStmt.toString());
		if (insertPics) {
			//System.out.println(insertPicStmt.toString());
			sqlManager.update(conn, insertPicStmt.toString());
		}
		else {
			sqlManager.update(conn, "insert into global.msds_ghs_pictogram (material_id, revision_date, pictogram_id, on_sds) "
					+ "values("+msds.getMaterialId()+","+DateHandler.getOracleToDateFunction(msds.getRevisionDate())+",'not required','Y')");
		}
	}
	
	public BigDecimal nextMaxcomId(Connection conn) throws BaseException {
		SqlManager sqlManager = new SqlManager();
		BigDecimal nextMsdsId = null;

		try {
			String maxmsds = "MAXMSDS_DBLINK";
			StringBuilder query = new StringBuilder("select msdsadmin_seq.nextval@").append(maxmsds).append(" from dual");
			DataSet maxMsdsData = sqlManager.select(conn, query.toString());
			nextMsdsId = maxMsdsData.getDataSetRow(1).getBigDecimal("NEXTVAL");
		}catch (Exception e) {
			//this is to handle if system failed to access Maxcom
			StringBuilder query = new StringBuilder("select ghs_sequence.nextval").append(" from dual");
			DataSet maxMsdsData = sqlManager.select(conn, query.toString());
			nextMsdsId = maxMsdsData.getDataSetRow(1).getBigDecimal("NEXTVAL");
		}
		return nextMsdsId;
	}
	
	public boolean isOnLine(MsdsIndexingBean msdsBean, Connection conn) throws BaseException {
		String query = "select count(*) from  " + MSDS_TABLE + " " + getWhereClause(getKeyCriteria(msdsBean))+
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
	
	public int setIdOnlyToProcessed(MsdsIndexingBean msdsBean, Connection conn) throws BaseException {
		StringBuilder query = new StringBuilder("update ").append(MSDS_TABLE).append(" set ");
		query.append(ATTRIBUTE_ID_ONLY).append(" = 'C' ");
        query.append(getWhereClause(getKeyCriteria(msdsBean))).append(" ");
		return new SqlManager().update(conn, query.toString());
    }
}
