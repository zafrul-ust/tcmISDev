package com.tcmis.client.common.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericSqlFactory;

/******************************************************************************
 * Process for AddEditSynonymProcess
 * @version 1.0
 *****************************************************************************/
public class AddEditSynonymProcess extends BaseProcess {

	Log log = LogFactory.getLog(this.getClass());

	public AddEditSynonymProcess(String client,String locale)  {
		super(client,locale);
	}
	
	public String updateSynonymText(String facilityId, String materialId, String originalSynonymText, String synonymText, PersonnelBean personnelBean) throws BaseException {
		Vector errorMessages = new Vector();
		Collection inArgs = null;
		Collection outArgs = null;
		String errorMsg = "";
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericProcedureFactory factory = new GenericProcedureFactory(dbManager);
		BigDecimal personnelId = personnelBean.getPersonnelIdBigDecimal();

		try {
			inArgs = new Vector(5);
			inArgs.add(facilityId);
			inArgs.add(materialId);
			inArgs.add(originalSynonymText);
			inArgs.add(synonymText);
			inArgs.add(personnelId);
			outArgs = new Vector(1);
			outArgs.add(new Integer(java.sql.Types.VARCHAR));
			log.debug("Update arguments: " + inArgs);

			Vector error = (Vector) factory.doProcedure("P_FAC_MATERIAL_SYNONYM_MGR", inArgs, outArgs);

	        if(error.size()>0 && error.get(0) != null)
			{
				errorMsg = (String) error.get(0);
				log.info("Error updating Synonym for material_id: " + materialId + " from "+ originalSynonymText + " to " + synonymText);
			}  
		} catch (Exception e) {
			errorMsg = "Error updating Synonym for material_id: " + materialId + " from "+ originalSynonymText + " to " + synonymText;
		}

		factory = null;
		dbManager = null;

		return errorMsg;
	}
	
	public String getSynonymText(String facilityId, String materialId) throws BaseException {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager);
		StringBuilder query = new StringBuilder("select synonym_text from facility_material_synonym where facility_id = '");
        query.append(facilityId).append("' and material_id = ").append(materialId);
        return factory.selectSingleValue(query.toString());
	}

} //end of class