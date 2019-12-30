package com.tcmis.internal.catalog.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

import com.tcmis.client.common.beans.MaterialSearchViewBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.internal.catalog.beans.GHSFieldsInputBean;
import com.tcmis.internal.catalog.beans.GHSStatementsViewBean;
import com.tcmis.internal.catalog.beans.PictogramBean;
import com.tcmis.internal.catalog.beans.SdsSectionBean;
import com.tcmis.internal.catalog.factory.MsdsBeanFactory;

public class GHSFieldsProcess extends GenericProcess {
	private static String RESOURCE_BUNDLE = "com.tcmis.common.resources.CommonResources";
	private final ResourceLibrary library;
    private String ghsCompliant = "";

    public GHSFieldsProcess(String client, Locale locale) {
		super(client, locale);
		library = new ResourceLibrary(RESOURCE_BUNDLE, getLocaleObject());
	}
    
    public GHSFieldsProcess(String client, Locale locale, String URL) {
		super(client, locale);
		library = new ResourceLibrary(RESOURCE_BUNDLE, getLocaleObject());
	}

    public String ghsCompliant() {
        String result = "";
        if ("N".equals(ghsCompliant))
            result = library.getString("label.notghscompliant");
        return result;
    }

    public Collection<GHSStatementsViewBean> getStatements(GHSFieldsInputBean inputBean) throws BaseException {
    	DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new GHSStatementsViewBean());
		
		StringBuilder query = new StringBuilder();
		query.append("select gs.id, gs.code, gs.statement, nvl(mgs.is_from_msds,1) is_from_msds, mgs.msds_id ");
		query.append("from msds_admin.ghs_statement gs, msds_admin.msds_ghs_statement mgs ");
		query.append("where gs.id = mgs.ghs_statement_id(+) and mgs.msds_id(+) = ").append(inputBean.getMsdsId()).append(" and");
		if (inputBean.getCodeAbbrev().equals("H")) {
			query.append(" (gs.code like 'H%' or gs.code like 'EUH%') and gs.code not like 'HM%'");
    	}
		else {
			query.append(" gs.code like '").append(inputBean.getCodeAbbrev()).append("%'");
		}
		
		query.append(" order by gs.code");
        Collection<GHSStatementsViewBean> statements = factory.selectQuery(query.toString());
        
        return statements;
	}
    
    public Collection<GHSStatementsViewBean> getStatementsByIds(String[] ids) throws BaseException {
    	DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new GHSStatementsViewBean());
		
		Collection<GHSStatementsViewBean> statements = null;
		if (ids.length > 0) {
			StringBuilder query = new StringBuilder("select id, code, statement from msds_admin.ghs_statement ");
			query.append("where id in (");
			StringBuilder idColl = new StringBuilder();
			for (int i = 0; i < ids.length;i++) {
				if (ids[i].length() > 0) {
					if (idColl.length() > 0) {
						idColl.append(",");
					}
					idColl.append(ids[i]);
				}
			}
			query.append(idColl);
			query.append(")");
	        statements = factory.selectQuery(query.toString());
		}
        
        return statements;
	}
    
    public Collection<String> getSignalWords() throws BaseException, Exception {
    	DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new MaterialSearchViewBean());
		
		StringBuilder query = new StringBuilder("select distinct signal_word from msds_admin.maxcom_sug");
        Object[] result = factory.selectIntoObjectArray(query.toString());
        
        Collection<Object[]> signalWordColl = (Collection)result[GenericSqlFactory.DATA_INDEX];
        Collection<String> signalWords = new Vector<String>();
        for (Iterator<Object[]> it = signalWordColl.iterator(); it.hasNext();) {
        	Object[] nxt = it.next();
        	if (nxt[0] != null && ! nxt[0].toString().equals("NONE")) {
       			signalWords.add(nxt[0].toString());
        	}
        }
        
        return signalWords;
    }
    
    public Collection<String> getIdOnlyColl() throws BaseException, Exception {
    	DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new MaterialSearchViewBean());
		
		StringBuilder query = new StringBuilder("select id_only from global.vv_id_only where set_by = 'tcmIS' order by id_only");
        Object[] result = factory.selectIntoObjectArray(query.toString());
        
        Collection<Object[]> idOnlyColl = (Collection)result[GenericSqlFactory.DATA_INDEX];
        Collection<String> idOnly = new Vector<String>();
        for (Iterator<Object[]> it = idOnlyColl.iterator(); it.hasNext();) {
        	Object[] nxt = it.next();
        	if (nxt[0] != null) {
        		idOnly.add(nxt[0].toString());
        	}
        }
        
        return idOnly;
    }
    
	public Collection<PictogramBean> getPictograms() throws Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());
	    GenericSqlFactory factory = new GenericSqlFactory(dbManager, new PictogramBean());
	    
	    StringBuilder query = new StringBuilder("select * from vv_ghs_pictogram order by default_order");
	    
	    Collection<PictogramBean> pictogramColl = factory.selectQuery(query.toString());
	    
	    return pictogramColl;
	}
	
	public void updateStatements(GHSFieldsInputBean input, PersonnelBean personnel, Collection<GHSStatementsViewBean> stmts) throws BaseException, Exception {
		Connection conn = dbManager.getConnection();
		try {
			if (input.getMsdsId() == null) {
				input.setMsdsId(nextMaxcomId(conn));
			}
			StringBuilder deleteStmt = new StringBuilder();
			deleteStmt.append("delete (select mgs.* from msds_admin.msds_ghs_statement mgs, msds_admin.ghs_statement gs ");
	    	deleteStmt.append("where mgs.msds_id=").append(input.getMsdsId());
	    	if (input.getCodeAbbrev().equals("H")) {
	    		deleteStmt.append(" and (gs.code like 'H%' OR gs.code like 'EUH%' OR gs.code = 'NRH')");
	    	}
	    	else if (input.getCodeAbbrev().equals("P")) {
	    		deleteStmt.append(" and (gs.code like 'P%' OR gs.code = 'NRP')");
	    	}
	    	deleteStmt.append(" and mgs.ghs_statement_id = gs.id)");
	    	if (stmts != null && ! stmts.isEmpty()) {
                boolean insertData = false;
                StringBuilder insertStmt = new StringBuilder("insert all ");
	        	for (GHSStatementsViewBean stmtBean : stmts) {
	        		if (stmtBean.isOk()) {
		                BigDecimal nextStmtId = nextMaxcomId(conn);
		        		insertStmt.append("into MSDS_ADMIN.MSDS_GHS_STATEMENT (ID, CREATED_BY, CREATED_DATE, MSDS_ID, GHS_STATEMENT_ID, IS_FROM_MSDS) VALUES(");
		        		insertStmt.append(nextStmtId+","+personnel.getPersonnelId()+",sysdate,"+input.getMsdsId()+","+stmtBean.getGhsStatementId()+","+stmtBean.getIsFromMsds()+")");
	        		    insertData = true;
                    }
	        	}
		    	//delete old statements
		    	factory.deleteInsertUpdate(deleteStmt.toString(), conn);
                //insert new statements
                if (insertData) {
                    insertStmt.append("select * from dual");
                    factory.deleteInsertUpdate(insertStmt.toString(), conn);
                }
	        	if (input.getMaterialId() != null && input.getRevisionDate() != null) {
		        	MsdsBeanFactory msdsFactory = new MsdsBeanFactory(dbManager);
		        	SearchCriteria criteria = new SearchCriteria("materialId", SearchCriterion.EQUALS, input.getMaterialId().toPlainString());
		        	criteria.addCriterion("revisionDate", SearchCriterion.EQUALS, DateHandler.getOracleToDateFunction(input.getRevisionDate()));
		        	msdsFactory.setMsdsId(criteria, input.getMsdsId(), conn);
	        	}
	    	}
	    	else if ("true".equals(input.getStatementNotRequired())) {
	    		BigDecimal nextStmtId = nextMaxcomId(conn);
	    		StringBuilder insertStmt = new StringBuilder("insert into MSDS_ADMIN.MSDS_GHS_STATEMENT ");
	    		insertStmt.append("(ID, CREATED_BY, CREATED_DATE, MSDS_ID, GHS_STATEMENT_ID, IS_FROM_MSDS) VALUES(");
	    		insertStmt.append(nextStmtId).append(",");
	    		insertStmt.append(personnel.getPersonnelId()).append(",");
	    		insertStmt.append("sysdate,");
	    		insertStmt.append(input.getMsdsId()).append(",");
	    		insertStmt.append(input.getStatementNotRequiredId()).append(",");
	    		insertStmt.append("0)");
	    		
	    		factory.deleteInsertUpdate(deleteStmt.toString(), conn);
	        	factory.deleteInsertUpdate(insertStmt.toString(), conn);
	        	
	        	if (input.getMaterialId() != null && input.getRevisionDate() != null) {
		        	MsdsBeanFactory msdsFactory = new MsdsBeanFactory(dbManager);
		        	SearchCriteria criteria = new SearchCriteria("materialId", SearchCriterion.EQUALS, input.getMaterialId().toPlainString());
		        	criteria.addCriterion("revisionDate", SearchCriterion.EQUALS, DateHandler.getOracleToDateFunction(input.getRevisionDate()));
		        	msdsFactory.setMsdsId(criteria, input.getMsdsId(), conn);
	        	}
	    	}
		}
		finally {
			dbManager.returnConnection(conn);
		}
	}
	
	private BigDecimal nextMaxcomId(Connection conn) throws SQLException, BaseException {
        BigDecimal nextMsdsId = null;

        try {
            String maxmsds = "MAXMSDS_DBLINK";
            StringBuilder query = new StringBuilder("select msdsadmin_seq.nextval@").append(maxmsds).append(" from dual");
            nextMsdsId = new BigDecimal(factory.selectSingleValue(query.toString(),conn));
        }catch (Exception e) {
            //this is to handle if system failed to access Maxcom
            StringBuilder query = new StringBuilder("select ghs_sequence.nextval").append(" from dual");
            nextMsdsId = new BigDecimal(factory.selectSingleValue(query.toString(),conn));
        }
        return nextMsdsId;
	}
	
	public Collection<SdsSectionBean> getSdsSectionColl() throws BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new SdsSectionBean());
		
		StringBuilder query = new StringBuilder();
		query.append("select * from vv_ghs_sds_section where NOT (composition_index_priority = 0) order by composition_index_priority");
        Collection<SdsSectionBean> statements = factory.selectQuery(query.toString());
        
        return statements;
	}
}
