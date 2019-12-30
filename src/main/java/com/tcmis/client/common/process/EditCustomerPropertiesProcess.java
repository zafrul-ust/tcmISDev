package com.tcmis.client.common.process;

import java.util.Collection;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.StringHandler;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.internal.catalog.beans.MsdsBean;

/******************************************************************************
 * Process for EditCustomerPropertiesProcess
 * @version 1.0
 *****************************************************************************/
public class EditCustomerPropertiesProcess extends BaseProcess {

	Log log = LogFactory.getLog(this.getClass());

	public EditCustomerPropertiesProcess(String client,String locale)  {
		super(client,locale);
	}
	
	public MsdsBean getCompanyMsdsInfo(String materialId, String revisionDate) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new MsdsBean());
		StringBuilder query = new StringBuilder("select * from company_msds_view where material_id = ");
        query.append(materialId).append(" and revision_date = '").append(revisionDate).append("'");
        Vector<MsdsBean> c = (Vector<MsdsBean>) factory.selectQuery(query.toString());
        if(c.size() > 0)
        	return c.get(0);
        else
        	return null;
	}
	
	public Collection updateCOForCatalog(MsdsBean m, String companyId, int personnelId) throws BaseException {
        Collection result = new Vector();
        DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);
        Vector inArgs = new Vector();
		inArgs.add(companyId);// request company id
		inArgs.add(m.getMaterialId());//using global
		inArgs.add(m.getRevisionDate());//using global
		inArgs.add(m.getContent());
		inArgs.add(m.getCoHealth());
		inArgs.add(m.getCoFlammability());
		inArgs.add(m.getCoReactivity());
		inArgs.add(m.getCoSpecificHazard());
        if (!StringHandler.isBlankString(m.getCoHealth()) || !StringHandler.isBlankString(m.getCoFlammability()) ||
            !StringHandler.isBlankString(m.getCoReactivity()) || !StringHandler.isBlankString(m.getCoSpecificHazard())) {
            inArgs.add(m.getCoNfpaSource());
        }else {
            inArgs.add("");
        }
		inArgs.add(m.getCoHmisHealth());
		inArgs.add(m.getCoHmisFlammability());
		inArgs.add(m.getCoHmisReactivity());
		inArgs.add(m.getCoPersonalProtection());
		inArgs.add(m.getCoHmisChronic());
        if (!StringHandler.isBlankString(m.getCoHmisHealth()) || !StringHandler.isBlankString(m.getCoHmisFlammability()) ||
            !StringHandler.isBlankString(m.getCoHmisReactivity()) || !StringHandler.isBlankString(m.getCoPersonalProtection()) ||
            !StringHandler.isBlankString(m.getCoHmisChronic())) {
            inArgs.add(m.getCoHmisSource());
        }else {
            inArgs.add("");
        }
        //specific gravity
        if (m.getCoSpecificGravityLower() != null || m.getCoSpecificGravityUpper() != null ) {
            inArgs.add(m.getCoSpecificGravityDetect());
        }else {
            inArgs.add("");
        }
		inArgs.add(m.getCoSpecificGravityLower());
		inArgs.add(m.getCoSpecificGravityUpper());
        if (m.getCoSpecificGravityLower() != null || m.getCoSpecificGravityUpper() != null ) {
            inArgs.add(m.getCoSpecificGravityBasis());
        }else {
            inArgs.add("");
        }
        if (m.getCoSpecificGravityLower() != null || m.getCoSpecificGravityUpper() != null ) {
            inArgs.add(m.getCoSpecificGravitySource());
        }else {
            inArgs.add("");
        }
        //density
        if (m.getCoDensity() != null || m.getCoDensityUpper() != null ) {
            inArgs.add(m.getCoDensityDetect());
        }else {
            inArgs.add("");
        }
		inArgs.add(m.getCoDensity());
		inArgs.add(m.getCoDensityUpper());
        if (m.getCoDensity() != null || m.getCoDensityUpper() != null ) {
            inArgs.add(m.getCoDensityUnit());
            inArgs.add(m.getCoDensitySource());
        }else {
            inArgs.add("");
            inArgs.add("");
        }
        //flash point
        if (m.getCoFlashPointLower() != null || m.getCoFlashPointUpper() != null ) {
            inArgs.add(m.getCoFlashPointDetect());
        }else {
            inArgs.add("");
        }
		inArgs.add(m.getCoFlashPointLower());
		inArgs.add(m.getCoFlashPointUpper());
        if (m.getCoFlashPointLower() != null || m.getCoFlashPointUpper() != null ) {
            inArgs.add(m.getCoFlashPointUnit());
            inArgs.add(m.getCoFlashPointMethod());
            inArgs.add(m.getCoFlashPointSource());
        }else {
            inArgs.add("");
            inArgs.add("");
            inArgs.add("");
        }
		//boing point
        if (m.getCoBoilingPointLower() != null || m.getCoBoilingPointUpper() != null ) {
            inArgs.add(m.getCoBoilingPointDetect());
        }else {
            inArgs.add("");
        }
		inArgs.add(m.getCoBoilingPointLower());
		inArgs.add(m.getCoBoilingPointUpper());
        if (m.getCoBoilingPointLower() != null || m.getCoBoilingPointUpper() != null ) {
            inArgs.add(m.getCoBoilingPointUnit());
            inArgs.add(m.getCoBoilingPointSource());
		    inArgs.add(m.getCoBoilingPointDetail());
        }else {
            inArgs.add("");
            inArgs.add("");
            inArgs.add("");
        }
		//ph
        if (m.getCoPh() != null || m.getCoPhUpper() != null ) {
            inArgs.add(m.getCoPhDetect());
        }else {
            inArgs.add("");
        }
		inArgs.add(m.getCoPh());
		inArgs.add(m.getCoPhUpper());
        if (m.getCoPh() != null || m.getCoPhUpper() != null ) {
            inArgs.add(m.getCoPhDetail());
            inArgs.add(m.getCoPhSource());
        }else {
            inArgs.add("");
            inArgs.add("");
        }
        //voc
        inArgs.add(m.getCoVocLower());
		inArgs.add(m.getCoVoc());
		inArgs.add(m.getCoVocUpper());
        if (m.getCoVocLower() != null || m.getCoVoc() != null || m.getCoVocUpper() != null ) {
            inArgs.add(m.getCoVocUnit());
            inArgs.add(m.getCoVocSource());
        }else {
            inArgs.add("");
            inArgs.add("");
        }
        //voc less water exempt
        inArgs.add(m.getCoVocLessH2oExemptLower());
		inArgs.add(m.getCoVocLessH2oExempt());
		inArgs.add(m.getCoVocLessH2oExemptUpper());
        if (m.getCoVocLessH2oExemptLower() != null || m.getCoVocLessH2oExempt() != null || m.getCoVocLessH2oExemptUpper() != null ) {
            inArgs.add(m.getCoVocLessH2oExemptUnit());
            inArgs.add(m.getCoVocLessH2oExemptSource());
        }else {
            inArgs.add("");
            inArgs.add("");
        }
        //solid
        inArgs.add(m.getCoSolidsLower());
		inArgs.add(m.getCoSolids());
		inArgs.add(m.getCoSolidsUpper());
        if (m.getCoSolidsLower() != null || m.getCoSolids() != null || m.getCoSolidsUpper() != null ) {
            inArgs.add(m.getCoSolidsUnit());
            inArgs.add(m.getCoSolidsSource());
        }else {
            inArgs.add("");
            inArgs.add("");
        }
        //vapor pressure
        if (m.getCoVaporPressureLower() != null || m.getCoVaporPressure() != null || m.getCoVaporPressureUpper() != null ) {
            inArgs.add(m.getCoVaporPressureDetect());
        }else {
            inArgs.add("");
        }
		inArgs.add(m.getCoVaporPressureLower());
		inArgs.add(m.getCoVaporPressure());
		inArgs.add(m.getCoVaporPressureUpper());
        if (m.getCoVaporPressureLower() != null || m.getCoVaporPressure() != null || m.getCoVaporPressureUpper() != null ) {
            inArgs.add(m.getCoVaporPressureUnit());
            inArgs.add(m.getCoVaporPressureTemp());
		    inArgs.add(m.getCoVaporPressureTempUnit());
		    inArgs.add(m.getCoVaporPressureSource());
        }else {
            inArgs.add("");
            inArgs.add("");
            inArgs.add("");
            inArgs.add("");
        }
        //others
        inArgs.add(m.getCoCarcinogen());
		inArgs.add(m.getCoChronic());
		inArgs.add(m.getCoCorrosive());
		inArgs.add(m.getCoEvaporationRate());
		inArgs.add(m.getCoFireConditionsToAvoid());
		inArgs.add(m.getCoIncompatible());
		inArgs.add(m.getCoOxidizer());
		inArgs.add(m.getCoPolymerize());
		inArgs.add(m.getCoStable());
		inArgs.add(m.getCoVaporDensity());
		inArgs.add(m.getCoWaterReactive());
		inArgs.add(m.getOzoneDepletingCompound());
		inArgs.add(m.getLowVolumeExempt());
        inArgs.add(m.getCoPhysicalState());
        inArgs.add(personnelId);
        inArgs.add(m.isCoDataEntryComplete() ? "Y": "N");
        result = factory.doProcedure("pkg_company_msds.p_upsert_company_msds", inArgs, new Vector());

        //do company material here
        inArgs = new Vector();
        inArgs.add(companyId);// request company id
		inArgs.add(m.getMaterialId());//using global
		inArgs.add(m.getRevisionDate());//using global
        inArgs.add(m.getCoProductCode());
        inArgs.add(personnelId);
        factory.doProcedure("pkg_company_msds.p_upsert_company_material", inArgs);

        return result;
    }  //end of method

} //end of class