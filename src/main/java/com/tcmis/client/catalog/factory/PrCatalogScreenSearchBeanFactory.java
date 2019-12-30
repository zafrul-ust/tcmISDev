package com.tcmis.client.catalog.factory;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.lang.reflect.Method;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import oracle.jdbc.OracleTypes;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.catalog.beans.CatalogInputBean;
import com.tcmis.client.catalog.beans.PrCatalogScreenSearchBean;
import com.tcmis.common.admin.beans.FacilityViewBean;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseBeanFactory;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.util.*;

/**
 * ***************************************************************************
 * CLASSNAME: PrCatalogScreenSearchViewBeanFactory <br>
 *
 * @version: 1.0, May 31, 2005 <br>
 * ***************************************************************************
 */

public class PrCatalogScreenSearchBeanFactory extends BaseBeanFactory {

	Log log = LogFactory.getLog(this.getClass());

	public PrCatalogScreenSearchBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	public Collection selectPos(CatalogInputBean bean, BigDecimal personnelId) throws BaseException {
		Connection connection = this.getDbManager().getConnection();
		try {
			connection.setAutoCommit(false);
		} catch (SQLException ex) {
		}

		Collection cin = new Vector();
		cin.add(new String("USER")); //company_id
		//Facility
		if ((bean.getFacilityId() != null && bean.getFacilityId().trim().length() > 0 && "My Facilities".equalsIgnoreCase(bean.getFacilityId()))) {
			cin.add(new String("All"));
		} else {
			cin.add(bean.getFacilityId());
		}
		//work area
		if ((bean.getApplicationId() != null && bean.getApplicationId().trim().length() > 0 && "My Work Areas".equalsIgnoreCase(bean.getApplicationId()))) {
			cin.add(new String("All"));
		} else {
			cin.add(new String(bean.getApplicationId()));
		}
		//Search text
		if (bean.getSearchText() != null && bean.getSearchText().trim().length() > 0) {
			cin.add(new String(bean.getSearchText()));
		} else {
			cin.add("");
		}
		//approved only
		if ("workAreaApprovedOnly".equalsIgnoreCase(bean.getWorkAreaApprovedOnlyPos())) {
			cin.add(new String("Y")); //approved
		} else {
			cin.add(new String("N"));
		}
		if (bean.getPosRequestorId() != null) {
			cin.add(bean.getPosRequestorId());
		}else {
			cin.add(personnelId);
		}
		//inventory group
		String catalogType = bean.getCatalog();
    	String inventoryGroup = catalogType.substring(catalogType.indexOf(": ") + 2);
   	    if (inventoryGroup.startsWith("All")) {
      	    inventoryGroup = "All";
    	}
		cin.add(inventoryGroup);

		Collection cout = new Vector();
		cout.add(new Integer(java.sql.Types.VARCHAR));
		Collection result = null;
		SqlManager sqlManager = new SqlManager();
		try {
            result = sqlManager.doProcedure(connection, "PR_CATALOG_SCREEN_POS", cin, cout);
        } catch (Exception ex) {
			return new Vector();
		} finally {
		}
		Iterator i11 = result.iterator();
		String searchQuery = "";
		int count = 1;
		while (i11.hasNext()) {
			if (count == 1) {
				searchQuery = (String) i11.next();
			}
			count++;
		}

		Collection c;
		//no need to get available qty for fee for searching point of sale catalog
		c = selectQuery(searchQuery,true,connection);

        try {
			connection.setAutoCommit(true);
		} catch (SQLException ex) {
		}

		//make sure to always return connection
		finally{
			this.getDbManager().returnConnection(connection);
		}
		return c;
	} //end of method

	public Collection select(CatalogInputBean bean, PersonnelBean user) throws BaseException {
		Connection connection = this.getDbManager().getConnection();
		try {
			connection.setAutoCommit(false);
		} catch (SQLException ex) {
		}

        boolean containsApproved = false;
        Collection cin = new Vector();
       
        cin.add(new String("USER")); //company_id
		//check to see user selected specific catalog or all catalog
        if ("All Catalogs".equals(bean.getFacilityOrAllCatalog())) {
            //facility and work area and search type
			cin.add(new String("My Facilities")); //facility_id
			cin.add(new String("My Applications")); //work area
			cin.add(new String("All Catalog")); //search_type
			//Search text
			if (bean.getSearchText() != null && bean.getSearchText().trim().length() > 0) {
				cin.add(new String(bean.getSearchText()));
			} else {
				cin.add("");
			}

			cin.add(new String("N")); //approved		
			cin.add(user != null ? user.getPersonnelId():null); //logged in personnel
		    cin.add(new String("Y"));//active
        } else {
			//Facility
			if ((bean.getFacilityId() != null && bean.getFacilityId().trim().length() > 0 && "My Facilities".equalsIgnoreCase(bean.getFacilityId()))) {
				cin.add(new String("My Facilities"));
			} else {
				cin.add(bean.getFacilityId());
			}
            //work area and search type
            if ("Full Facility Catalogs".equals(bean.getFacilityOrAllCatalog())) {
                cin.add(new String("My Applications"));
                cin.add(new String("Full Facility Catalog")); //search_type    
            }else {
                if ((bean.getApplicationId() != null && bean.getApplicationId().trim().length() > 0 && !"My Work Areas".equalsIgnoreCase(bean.getApplicationId()))) {
                    cin.add(new String(bean.getApplicationId()));
                    cin.add(new String("Active For Workarea")); //search_type
                    containsApproved = true;
                } else {
                    cin.add(new String("My Applications"));
                    cin.add(new String("Active For Facility")); //search_type
                }
            }
            //Search text
			if (bean.getSearchText() != null && bean.getSearchText().trim().length() > 0) {
				cin.add(new String(bean.getSearchText()));
			} else {
				cin.add("");
			}
            //approved only
			if ("Work Area Approved".equalsIgnoreCase(bean.getFacilityOrAllCatalog())) {
				cin.add(new String("Y")); //approved
			} else {
				cin.add(new String("N"));
			}
            //logged in personnel
			cin.add(user != null ? user.getPersonnelId():null);
			//Active only
            if ("Full Facility Catalogs".equals(bean.getFacilityOrAllCatalog())) {
                cin.add(new String("Y"));
            }else {
                cin.add(new String("N"));
            }
        } //end of not All Catalog

        Collection cinOptional = new Vector();
        //obsolete parts
		if (bean.getIncludeObsoleteParts() != null && bean.getIncludeObsoleteParts().trim().length() > 0) {
			cinOptional.add("Y");
		} else {
			cinOptional.add("N");
		}

		Collection cout = new Vector();
		cout.add(new Integer(java.sql.Types.VARCHAR));
		cout.add(new Integer(java.sql.Types.VARCHAR));
		log.debug(cin);

        Collection result = null;
		SqlManager sqlManager = new SqlManager();
		try {
			result = sqlManager.doProcedure(connection, "PR_CATALOG_SCREEN_PART", cin, cout, cinOptional);
        } catch (Exception ex) {
			return new Vector();
		} finally {
		}
		Iterator i11 = result.iterator();
		String searchQuery = "";
		String printScreenSearchQuery = "";
		int count = 1;
		while (i11.hasNext()) {
			if (count == 1) {
				printScreenSearchQuery = (String) i11.next();
			} else if (count == 2) {
				searchQuery = (String) i11.next();
			}
			count++;
		}

		Collection c;
		if (bean.isPrintScreen()) {
			//no need to get available qty for fee if creating excel
			c = selectQuery(printScreenSearchQuery,true,connection,bean);
		}else {
			c = selectQuery(searchQuery,containsApproved,connection,bean);
		}
		
		try {
			connection.setAutoCommit(true);
		} catch (SQLException ex) {
		}

		//make sure to always return connection
		finally{
			this.getDbManager().returnConnection(connection);
		}
		return c;
	} //end of method
	
	public Collection selectQuery(String query, boolean containsApproved, Connection conn, CatalogInputBean input) throws BaseException {
		if(input.isFacilityOrAllShelflife())
			query = facilityOrAllShelflifeQuery(query, input);
		
		return selectQuery(query, containsApproved, conn);
	}
	
	public Collection selectQuery(String query, boolean containsApproved, Connection conn) throws BaseException {
		Collection dataBeanColl = new Vector();
		try {
			log.debug(query);
			Statement sqlStatement = conn.createStatement();
      	    ResultSet sqlResult = sqlStatement.executeQuery(query);
			//get resultset metadata
      	    ResultSetMetaData rsmd = sqlResult.getMetaData();
			int[] columnType = new int[rsmd.getColumnCount()];
			Method[] writerMethodArray = new Method[rsmd.getColumnCount()];
			PrCatalogScreenSearchBean bean = new PrCatalogScreenSearchBean();
			Object mybean = bean.getClass().newInstance();
			BeanInfo beanInfo = Introspector.getBeanInfo(mybean.getClass());
      	    PropertyDescriptor[] pdesc = beanInfo.getPropertyDescriptors();
			int count = 0;
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				String currentDatabaseColumnName = rsmd.getColumnName(i).replaceAll("_","");
				columnType[count] = rsmd.getColumnType(i);
				int beanColumnIndex = -1;
				for (int j = 0; j < pdesc.length; j++) {
					if (currentDatabaseColumnName.equalsIgnoreCase(pdesc[j].getName())) {
						beanColumnIndex = j;
						break;
					}
				}
				if (beanColumnIndex == -1) {
					//column in view but not in bean
					writerMethodArray[count] = null;
				}else {
					writerMethodArray[count] = pdesc[beanColumnIndex].getWriteMethod();
				}
				count++;
			}
			while (sqlResult.next()) {
				PrCatalogScreenSearchBean myDataBean = new PrCatalogScreenSearchBean();
				loadNew(sqlResult,myDataBean,columnType,writerMethodArray);
				String partType = myDataBean.getStockingMethod();
				if (containsApproved && ("BG".equalsIgnoreCase(partType) || "SF".equalsIgnoreCase(partType) ||
					 "TC".equalsIgnoreCase(partType) || "SP".equalsIgnoreCase(partType))) {
					myDataBean.setServiceFeeRow("Y");
					getAvailableQtyForNoFee(myDataBean);
				}
                dataBeanColl.add(myDataBean);
			}
		}catch (Exception e) {
			e.printStackTrace();
			BaseException be = new BaseException(e);
      	be.setRootCause(e);
      	throw be;
		}
		return dataBeanColl;
	}

	public void getAvailableQtyForNoFee(PrCatalogScreenSearchBean myDataBean) throws BaseException {
		Connection connection = this.getDbManager().getConnection();
		try {
			String query = "select sum(quantity_available) quantity_available from no_buy_order_inventory_view where"+
								  " cat_part_no = '" + myDataBean.getCatPartNo() + "' and catalog_id = '" + myDataBean.getCatalogId() + "'"+
				              " and catalog_company_id = '"+myDataBean.getCatalogCompanyId()+"' and part_group_no = " +myDataBean.getPartGroupNo().toString()+
				 				  " group by catalog_id,catalog_company_id,cat_part_no,part_group_no";
			Statement sqlStatement = connection.createStatement();
      	ResultSet sqlResult = sqlStatement.executeQuery(query);
			while (sqlResult.next()) {
				myDataBean.setAvailableQtyForFee(sqlResult.getBigDecimal("quantity_available"));
			}
		}catch (Exception e) {
			e.printStackTrace();
			BaseException be = new BaseException(e);
      	be.setRootCause(e);
      	throw be;
		}finally {
			this.getDbManager().returnConnection(connection);
		}
	}

	// menu data queries
	// I am just lazy
	// return two hashmaps for part and item inventory
	public Object selectInventory(PrCatalogScreenSearchBean bean) throws BaseException {
		HashMap result = new HashMap();
		Vector itemInv = new Vector();
		Vector itemID = new Vector();
		CallableStatement cs = null;
		ResultSet rs = null;
		Connection connect1 = null;
		try {
			connect1 = this.getDbManager().getConnection();
			cs = connect1.prepareCall("{call PKG_INVENTORY_DETAIL.PR_PART_INVENTORY(?,?,?,?,?,?,?)}");
			cs.setString(1, bean.getCatalogId());
			cs.setString(2, bean.getCatPartNo());
			cs.setString(3, bean.getFacilityId());
			cs.setString(4, bean.getApplicationId());
			cs.setString(5, null);
			cs.registerOutParameter(6, OracleTypes.CURSOR);
			cs.setString(7, bean.getCatalogCompanyId());
			cs.execute();
			rs = (ResultSet) cs.getObject(6);
			while (rs.next()) {
				itemID.addElement(rs.getString("item_id"));
				itemInv.addElement(rs.getString("available_qty"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseException(e.getMessage());
		} finally {
			this.getDbManager().returnConnection(connect1);
		}

		BigDecimal partInv = new BigDecimal(0);
		String[] itemText = new String[itemID.size()];
		for (int j = 0; j < itemID.size(); j++) {
			String qty = itemInv.elementAt(j).toString();
			itemText[j] = itemID.elementAt(j).toString() + ": " + qty;
			partInv = partInv.add(new BigDecimal(qty));
			System.out.println(itemText[j]);
		}
		result.put("partInventory", partInv);
		result.put("itemId", itemID);
		result.put("itemQty", itemInv);
		return result;
	}
	///////////////////

	//	get flow down, reorder point and stocking level
	public Object selectStockingReorder(PrCatalogScreenSearchBean bean) throws BaseException {
		HashMap result = new HashMap();
		PreparedStatement cs = null;
		ResultSet rs = null;
		Connection connect1 = null;
		try {
			connect1 = this.getDbManager().getConnection();

			//first get quality info if catalog set to display it
			String query = "select reorder_point,stocking_level from catalog_part_inventory where catalog_id = '" + bean.getCatalogId() + "' and cat_part_no = '" + bean.getCatPartNo() + "' and " + " catalog_company_id = '" + bean.getCatalogCompanyId() + "' and part_group_no = " + bean.getPartGroupNo();
			if (bean.getInventoryGroup() != null && bean.getInventoryGroup().length() > 0)
				query += " and inventory_group = '" + bean.getInventoryGroup() + "'";
			cs = connect1.prepareStatement(query);
			cs.executeQuery();
			rs = cs.getResultSet();
			int countcpi = 0;
			while (rs.next()) {
				countcpi++;
				result.put("reorderPoint", rs.getString("reorder_point"));
				result.put("stockingLevel", rs.getString("stocking_level"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseException(e.getMessage());
		} finally {
			this.getDbManager().returnConnection(connect1);
		}
		return result;
	} //end of method

	public Object selectImgLit(PrCatalogScreenSearchBean bean) throws BaseException {
		HashMap result = new HashMap();
		PreparedStatement cs = null;
		ResultSet rs = null;
		Connection connect1 = null;
		try {
			connect1 = this.getDbManager().getConnection();

			String query = "select image_content, mfg_literature_content from item where item_id = " + bean.getItemId();
			cs = connect1.prepareStatement(query);
			cs.executeQuery();
			rs = cs.getResultSet();
			int countcpi = 0;
			while (rs.next()) {
				countcpi++;
				result.put("Img", rs.getString("image_content"));
				result.put("Lit", rs.getString("mfg_literature_content"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseException(e.getMessage());
		} finally {
			this.getDbManager().returnConnection(connect1);
		}
		return result;
	} //end of method
	
	public String facilityOrAllShelflifeQuery(String query, CatalogInputBean input) throws BaseException {
		StringBuilder modifiedQuery = new StringBuilder("");
		
		/* modify query with additional call to customer.fx_cat_part_shelf_life_list 
		 * in order to use a concatenated list of shelf life/storage temp for either
		 * all facilities or all work areas in a facility
		 */
		modifiedQuery.append("select customer.fx_cat_part_shelf_life_list(");
		modifiedQuery.append("USER, ");
		modifiedQuery.append("x.catalog_company_id, ");
		modifiedQuery.append("x.catalog_id,");
		modifiedQuery.append("x.cat_part_no,");
		modifiedQuery.append("x.part_group_no");
		
		if("Full Facility Catalogs".equals(input.getFacilityOrAllCatalog())) 			
			modifiedQuery.append(", '" + input.getFacilityId() + "'");
				
		modifiedQuery.append(") shelf_life_list, ");

		// include rest of the columns in the original query
		modifiedQuery.append("x.*");
		
		modifiedQuery.append(" from (");
		modifiedQuery.append(query);
		modifiedQuery.append(") x");
		
		return modifiedQuery.toString();
	}
} // end of class