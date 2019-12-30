package com.tcmis.internal.hub.process;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;
import java.util.Collections;
import java.util.Comparator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.factory.PersonnelBeanFactory;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericProcess;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.ShipConfirmInputBean;
import com.tcmis.internal.hub.beans.ShipConfirmViewBean;
import com.tcmis.internal.hub.beans.ShipmentBean;
import com.tcmis.internal.hub.beans.ShipmentCreationStageBean;
import com.tcmis.internal.hub.beans.SplitReceiptBean;
import com.tcmis.internal.hub.factory.HubBeanFactory;
import com.tcmis.internal.hub.factory.ShipConfirmViewBeanFactory;
import com.tcmis.internal.hub.factory.ShipmentBeanFactory;
import com.tcmis.internal.hub.factory.ShipmentCreationStageBeanFactory;

/******************************************************************************
 * Process for ship confirm
 * 
 * @version 1.0
 *****************************************************************************/
public class SplitReceiptProcess extends GenericProcess {
	Log log = LogFactory.getLog(this.getClass());

	public SplitReceiptProcess(TcmISBaseAction act) throws BaseException{
		    super(act);
	}
	
	public SplitReceiptProcess(String client, String locale) {
		super(client, locale);
	}

	public Collection getHubs() throws BaseException, Exception {
		DbManager dbManager = new DbManager(getClient(), getLocale());
		HubBeanFactory factory = new HubBeanFactory(dbManager);
		return factory.selectHubs();
	}

	public Collection search(SplitReceiptBean in) throws BaseException, Exception {
		String query = "select x.*, fx_quality_control_item_id (x.item_id,x.inventory_group) QUALITY_CONTROL_ITEM from receipt x where receipt_id = "+ in.getReceiptId();
		return factory.setBean(in).selectQuery(query);
	}
	public Collection searchbins(SplitReceiptBean in) throws BaseException, Exception {
		String query = "select BIN from receipt_item_prior_bin_view where status = 'A' and item_id = "+this.getSqlString(in.getItemId()) + " and hub = "+this.getSqlString(in.getHub());
		return factory.setBean(in).selectQuery(query);
	}

	public Vector getPickableLotStatus() {
		Vector ResultV = new Vector();
		Connection conn = null;
		String query = "select lot_status,PICKABLE,CERTIFIED,ALLOCATION_ORDER from vv_lot_status where ACTIVE='Y' and LOT_STATUS <> 'Incoming' order by lot_status ";
		Hashtable result = new Hashtable();
		try {
			//		debug(query);
			conn = this.dbManager.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				result = new Hashtable();
				String faci = (rs.getString("lot_status") == null ? "" : rs.getString("lot_status"));
				String pickable = (rs.getString("PICKABLE") == null ? "" : rs.getString("PICKABLE"));
				String certified = (rs.getString("CERTIFIED") == null ? "" : rs.getString("CERTIFIED"));
				String allocorder = (rs.getString("ALLOCATION_ORDER") == null ? "" : rs.getString("ALLOCATION_ORDER"));

				if ("Y".equalsIgnoreCase(pickable) && "0".equalsIgnoreCase(allocorder)) {
					pickable = "N";
				}

				if ("Y".equalsIgnoreCase(certified)) {
					pickable = "Y";
				}

				result.put(faci, pickable);
				ResultV.addElement(result);
			}

		}
		catch (Exception e) {
			//e.printStackTrace();
		}
		finally {
			if (conn != null) {
				try {conn.close(); } catch(Exception eee){};
			}
		}
		return ResultV;
	}

	public boolean getCertUpdate(String lotstatus,Vector all_status_set_d1) {// pickable lot status
//		Vector all_status_set_d1 = this.getPickableLotStatus();
		//if ( !origlotstatus.equalsIgnoreCase( lotstatus ) )
		{
			for ( int h=0; h < all_status_set_d1.size(); h++ )
			{
				Hashtable data1= ( Hashtable ) all_status_set_d1.elementAt( h );
				Enumeration E;
				for ( E=data1.keys(); E.hasMoreElements(); )
				{
					String key= ( String ) E.nextElement();
					String keyvalue=data1.get( key ).toString();

					if ( lotstatus.equalsIgnoreCase( key ) && "Y".equalsIgnoreCase( keyvalue ) )
					{
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public Object[] splitReceipt(SplitReceiptBean in, PersonnelBean pbean) throws BaseException, Exception {
		//System.out.println("compare "+splitQuantity.compareTo(maxSplitQuantity)+"");
		BigDecimal maxSplitQuantity = new BigDecimal(in.getQuantity());
		BigDecimal splitQuantity  = in.getSplitQuantity();
		Integer receiptId = null;
		String errmsg = "";
		if ((splitQuantity.compareTo(maxSplitQuantity) == -1 || splitQuantity.compareTo(maxSplitQuantity) ==0))
		{
			try {
				//String query="select lot_status from vv_lot_status order by lot_status ";
				String personnelId = ""+pbean.getPersonnelId();
				String receiptid = ""+in.getReceiptId();
				String invengrp = in.getInventoryGroup();
				String movePendingAdjustment = in.getMovePendingAdjustment();
				boolean certupdate = false;
				String lotstatus = in.getLotStatus();
				String certUpdatePersonnelId = "";
				if ( "Y".equalsIgnoreCase( in.getQualityControlItem() ) )
				{
					Vector all_status_set_d1 = this.getPickableLotStatus();
					certupdate = this.getCertUpdate(lotstatus, all_status_set_d1);
					if( certupdate)
						certUpdatePersonnelId += pbean.getPersonnelId();
				}

				//		 Vector outArgs = new Vector();
				//		 outArgs.add( new Integer(java.sql.Types.VARCHAR) );
				//		 outArgs.add( new Integer(java.sql.Types.VARCHAR) );
				////			   "select lot_status,PICKABLE,CERTIFIED,ALLOCATION_ORDER from vv_lot_status where ACTIVE='Y' and LOT_STATUS <> 'Incoming' order by lot_status ";
				//			factory.doProcedure("p_receipt_split", 
				//					buildProcedureInput(
				//							in.getHub(),
				//							in.getBin(),
				//							in.getQcDate(),
				//							in.getSplitQuantity(),
				//							in.getLotStatus(),
				//							pbean.getPersonnelId(),
				//							in.getReceiptId(),
				//							in.getInventoryGroup(),
				//							certUpdatePersonnelId // "" or personnelId depends on certupdate
				//					)
				//					, outArgs) ;
				Connection connect1 = dbManager.getConnection();
				CallableStatement cstmt = connect1.prepareCall("{call p_receipt_split(?,?,?,?,?,?,?,?,?,?,?,?)}");
				String queryS = "call p_receipt_split(";
				GregorianCalendar gcDate_mfgd = new GregorianCalendar();
				Timestamp CountDateTimeStamp = new Timestamp(gcDate_mfgd.getTime().getTime());

				cstmt.setString(1, in.getHub()); queryS += this.getSqlString(in.getHub());
				cstmt.setString(2, in.getBin()); queryS += ","+this.getSqlString(in.getBin());
				cstmt.setTimestamp(3, CountDateTimeStamp); queryS += ","+this.getSqlString(CountDateTimeStamp);
				cstmt.setBigDecimal(4, in.getSplitQuantity()); queryS += ","+this.getSqlString(in.getSplitQuantity());
				cstmt.setString(5, lotstatus); queryS += ","+this.getSqlString(lotstatus);
				cstmt.setString(6, personnelId); queryS += ","+this.getSqlString(personnelId);
				cstmt.setInt(7, Integer.parseInt(receiptid)); queryS += ","+this.getSqlString(receiptid);
				cstmt.setString(8, invengrp); queryS += ","+this.getSqlString(invengrp);
				if (certupdate){cstmt.setString(9,personnelId);}else{cstmt.setNull(9,java.sql.Types.VARCHAR);} queryS += ",??certUpdatePersonnelId??";

				cstmt.registerOutParameter(10, Types.INTEGER); queryS += ",<receiptId>";
				cstmt.registerOutParameter(11, Types.VARCHAR); queryS += ",<errmsg>";

				if ("Y".equalsIgnoreCase(movePendingAdjustment))
				{
					cstmt.setString(12, "Y"); queryS += ",Y";
				}
				else
				{
					cstmt.setString(12, "N"); queryS += ",N";
				}

				log.debug("query:"+queryS);
				cstmt.executeQuery();

				receiptId = cstmt.getInt(10);
				errmsg = cstmt.getString(11);

				log.debug("New Split Receipt Id: "+receiptId);
				log.debug("Error Message: "+errmsg);

				connect1.commit();
				cstmt.close();

			}
			catch (Exception e)
			{
				e.printStackTrace();
				errmsg = e.getMessage();
			}
		}
		Object[] objs = { receiptId, errmsg };

		return objs;
	}
}