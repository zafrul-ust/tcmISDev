package com.tcmis.client.catalog.factory;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.catalog.beans.ApprovalRequestOvBean;
import com.tcmis.client.catalog.beans.CaiLineObjBean;
import com.tcmis.client.catalog.beans.FacAppUserGrpOvBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DbSelectException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.util.DataSet;
import com.tcmis.common.util.DataSetRow;
import com.tcmis.common.util.SearchCriteria;

/******************************************************************************
 * CLASSNAME: ApprovalRequestOvFactory <br>
 * @version: 1.0, Feb 13, 2006 <br>
 *****************************************************************************/

public class ApprovalRequestOvFactory extends BaseBeanFactory {

 Log log = LogFactory.getLog(this.getClass());

 //constructor
 public ApprovalRequestOvFactory(DbManager dbManager) {
	super(dbManager);
 }

 public Collection selectObject(String query, String showPassAndFailReviewRules) throws BaseException {
    Connection connection = null;
    Collection c = null;
    try {
        connection = this.getDbManager().getConnection();
        c = selectObject(query, connection, showPassAndFailReviewRules);
    } catch (Exception e) {
        e.printStackTrace();
        DbSelectException ex = new DbSelectException("error.db.select");
        ex.setRootCause(e);
        throw ex;
    } finally {
        this.getDbManager().returnConnection(connection);
    }
    return c;
 }

 public Collection selectObject(String query,Connection conn, String showPassAndFailReviewRules) throws BaseException {
	Collection<ApprovalRequestOvBean> beanColl = new Vector();

	try {
     if( log.isDebugEnabled() ) {
        log.debug(query);   
     }
     Statement st = conn.createStatement();
	 ResultSet rs = st.executeQuery(query);
	 while (rs.next()) {
		 ApprovalRequestOvBean b = new ApprovalRequestOvBean();

         int k = 1;
		 b.setReviewId(rs.getInt(k++));
		 b.setProcessingOrder(rs.getInt(k++));
		 b.setFacilityId(rs.getString(k++));
		 b.setApprovalRole(rs.getString(k++));
         b.setCompanyId(rs.getString(k++));
		 b.setCatalogId(rs.getString(k++));
		 b.setApplication(rs.getString(k++));
		 b.setCatalogCompanyId(rs.getString(k++));
		 b.setRuleId(rs.getString(k++));
         b.setArgument1(rs.getString(k++));
		 b.setArgument2(rs.getString(k++));
         b.setStopOnFailure(rs.getString(k++));
		 b.setNotifyOnly(rs.getString(k++));
		 b.setFailStatement(rs.getString(k++));
		 b.setPassStatement(rs.getString(k++));
		 b.setAddlNotifyApprovalRole1(rs.getString(k++));
		 b.setAddlNotifyApprovalMsg1(rs.getString(k++));
		 b.setAddlNotifyApprovalRole2(rs.getString(k++));
		 b.setAddlNotifyApprovalMsg2(rs.getString(k++));
		 b.setRejectOnFailure(rs.getString(k++));
         b.setApproveOnPass(rs.getString(k++));
         b.setRequestorMessage(rs.getString(k++));
         b.setLineItem(rs.getString(k++));
         b.setItemId(rs.getString(k++));
         b.setFail(rs.getString(k++));
		 b.setOutputStatement(rs.getString(k++));
         b.setReviewRequired(rs.getString(k++));
         b.setRestrictedApproval(rs.getString(k++));
         b.setOnFailGotoReviewId(rs.getString(k++));
         b.setSmeNotificationType(rs.getString(k++));

         //the reason that I am doing it here is because data is loaded by index
         //if user is asking for results then only return data the do not matches below condition
         if ("N".equals(showPassAndFailReviewRules)) {
             if ("Y".equals(b.getFail()) && "Y".equals(b.getApproveOnPass()) && "N".equals(b.getStopOnFailure())) {
                 continue;
             }
         }

         Vector v = new Vector();
         Array arr1 = rs.getArray(k);
         java.util.List list = Arrays.asList( (Object[]) (arr1).getArray());
         for(int j=0;j<list.size();j++) {
            oracle.sql.STRUCT fac = (oracle.sql.STRUCT) list.get(j);
            Object[] attrs2 = fac.getAttributes();
            int m = 0 ;
            CaiLineObjBean ooo = new CaiLineObjBean();
            ooo.setLineItem((BigDecimal)attrs2[m++]);
            ooo.setPartId((BigDecimal)attrs2[m++]);
            ooo.setItemId((BigDecimal)attrs2[m++]);
            ooo.setMaterialId((BigDecimal)attrs2[m++]);
            ooo.setMaterialDesc((String)attrs2[m++]);
            ooo.setTradeName((String)attrs2[m++]);
            ooo.setCasNumber((String)attrs2[m++]);
            ooo.setChemicalName((String)attrs2[m++]);
            ooo.setPercentage((BigDecimal)attrs2[m++]);
            ooo.setManufacturerDesc((String)attrs2[m++]);
            v.add(ooo);
        }
        b.setListCas(v);

        beanColl.add(b);
	 }
	 rs.close();
	 st.close();
	}
	catch (SQLException e) {
	 e.printStackTrace();
	 DbSelectException ex = new DbSelectException("error.db.select");
    ex.setRootCause(e);
	 throw ex;
	}
	return beanColl;
 }
}