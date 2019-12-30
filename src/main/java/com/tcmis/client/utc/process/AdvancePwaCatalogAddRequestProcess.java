package com.tcmis.client.utc.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Vector;

import com.tcmis.client.catalog.process.EngEvalProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;

public class AdvancePwaCatalogAddRequestProcess extends GenericProcess {

	private GenericSqlFactory 	genericSqlFactory;
	private DbManager 			dbManager;
	private Connection			connection = null;
	private String 				URL;

	public AdvancePwaCatalogAddRequestProcess(String client, String URL) {
		super(client);
		this.URL = URL;
		dbManager = new DbManager(getClient());
		genericSqlFactory = new GenericSqlFactory(dbManager);
	}

	public String resetAndCreateNewCatalogAddRequest() throws Exception {
		try {
			connection = dbManager.getConnection();
			//reset catalog_part_item_group
			StringBuilder query = new StringBuilder("update catalog_part_item_group set status = 'H' where company_id = 'UTC'");
			query.append("and catalog_id = 'PWA' and catalog_item_id in ('AMS 3117-1S/1','MSM 6289-1/1')");
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
			//reset catalog_add_request_new
			query = new StringBuilder("update catalog_add_request_new set request_status = 7 where cat_part_no in ('AMS 3117-1S/1','MSM 6289-1/1')");
			query.append(" and catalog_id = 'PWA'");
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
			//reset material_definition_snap
			query = new StringBuilder("update material_definition_snap set updated_new_chem = 'Y' where new_material_add = 'Yes'");
			query.append(" and (material_id = 'AMS 3117-1S/1' or parent_material_id = 'MSM 6289-1/1')");
			genericSqlFactory.deleteInsertUpdate(query.toString(),connection);
			//execute store procedure to create new catalog add request
			genericSqlFactory.doProcedure("pkg_pwa_feed.p_pwa_new_chem_add", new Vector(0));
			//get newly created catalog add request id
			query = new StringBuilder("select max(request_id) from catalog_add_request_new where cat_part_no = 'AMS 3117-1S/1' and catalog_id = 'PWA'");
			String newRequestIds = genericSqlFactory.selectSingleValue(query.toString(),connection);
			query = new StringBuilder("select max(request_id) from catalog_add_request_new where cat_part_no = 'MSM 6289-1/1' and catalog_id = 'PWA'");
			newRequestIds += "; "+genericSqlFactory.selectSingleValue(query.toString(),connection);
			return (newRequestIds);
		}catch (Exception e) {
			log.error("Error while running daily inventory check", e);
			throw e;
		}
		finally {
			dbManager.returnConnection(connection);
		}
	} // end of method

	public void advanceCatalogAddRequest() throws Exception {
		try {
			connection = dbManager.getConnection();
			EngEvalProcess engEvalProcess = new EngEvalProcess(this.getClient(),this.getLocale(),URL);
			engEvalProcess.setFactoryConnection(genericSqlFactory,connection);
			engEvalProcess.setNextStatusAction("Submit");
			engEvalProcess.advancePwaCatalogAddRequest();
		}catch (Exception e) {
			log.error("Error while running daily inventory check", e);
			throw e;
		} finally {
			dbManager.returnConnection(connection);
		}
	} // end of method

} // end of class