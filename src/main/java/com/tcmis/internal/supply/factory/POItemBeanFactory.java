package com.tcmis.internal.supply.factory;

import oracle.jdbc.OracleTypes;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import java.lang.reflect.Method;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.ParseException;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.tcmis.internal.supply.beans.POItemSearchInputBean;
import com.tcmis.common.framework.BaseDataBean;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.DataBeanCreationException;
import com.tcmis.common.framework.BaseBeanFactory;
//import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.db.SqlManager;
import com.tcmis.common.util.*;
import com.tcmis.common.db.DbManager;
import com.tcmis.internal.supply.beans.POItemBean;

public class POItemBeanFactory extends BaseBeanFactory 
{

	Log log = LogFactory.getLog(this.getClass());

	//column names
	public String ATTRIBUTE_ITEM_TYPE = "ITEM_TYPE";
	public String ATTRIBUTE_ITEM_ID = "ITEM_ID";
	public String ATTRIBUTE_ITEM_DESC = "ITEM_DESC";
	public String ATTRIBUTE_COMPANY_ID = "COMPANY_ID";
	public String ATTRIBUTE_CATALOG_ID = "CATALOG_ID";
	public String ATTRIBUTE_PRIORITY = "PRIORITY";
	public String ATTRIBUTE_CAT_PART_NO = "CAT_PART_NO";
	public String ATTRIBUTE_PART_GROUP_NO = "PART_GROUP_NO";
	public String ATTRIBUTE_STATUS = "STATUS";
	public String ATTRIBUTE_PACKAGING = "PACKAGING";
	public String ATTRIBUTE_UNIT_OF_SALE = "UNIT_OF_SALE";

	public POItemBeanFactory(DbManager dbManager) {
		super(dbManager);
	}

	//get column names
	public String getColumnName(String attributeName) 
	{
		if(attributeName.equals("itemType")) {
			return ATTRIBUTE_ITEM_TYPE;
		}
		else if(attributeName.equals("itemId")) {
			return ATTRIBUTE_ITEM_ID;
		}
		else if(attributeName.equals("itemDesc")) {
			return ATTRIBUTE_ITEM_DESC;
		}
		else if(attributeName.equals("companyId")) {
			return ATTRIBUTE_COMPANY_ID;
		}		
		else if(attributeName.equals("catalogId")) {
			return ATTRIBUTE_CATALOG_ID;
		}
		else if(attributeName.equals("priority")) {
			return ATTRIBUTE_PRIORITY;
		}
		else if(attributeName.equals("catPartNo")) {
			return ATTRIBUTE_CAT_PART_NO;
		}
		else if(attributeName.equals("partGroupNo")) {
			return ATTRIBUTE_PART_GROUP_NO;
		}
		else if(attributeName.equals("status")) {
			return ATTRIBUTE_STATUS;
		}
		else if(attributeName.equals("packaging")) {
			return ATTRIBUTE_PACKAGING;
		}
		else if(attributeName.equals("unitOfSale")) {
			return ATTRIBUTE_UNIT_OF_SALE;
		}
		else 
		{
			return super.getColumnName(attributeName);
		}
	}


	//get column types
	public int getType(String attributeName) 
	{
		return super.getType(attributeName, POItemBean.class);
	}
	
	
	 public Collection getPOItemBeanCollection (POItemSearchInputBean inputBean) 
	 		throws BaseException 
	 {
		Collection pOItemBeanCollection = new Vector();
		 
		Connection connection = this.getDbManager().getConnection();
		
		//log.debug("inputBean.toString() == [" + inputBean.toString() + "]; ");
		String mode = inputBean.getMode();
		
		String searchArgument_1 = inputBean.getSearchArgument_1();
		String searchArgument_2 = inputBean.getSearchArgument_2();
		String companyId = inputBean.getCompanyId();
		
		String shipToId = inputBean.getShipToId();
		String sortBy = inputBean.getSortBy();				// 6		
		String validBPO = inputBean.getValidBPO(); // 7		
		String inventoryGroup = inputBean.getInventoryGroup();			// 8
		
		Vector cin = new Vector();
		for (int i = 0; i < 8; i++ )
		{
			cin.add("");  // element values will be set below...
		}
		if (mode != null && mode.equals("lineCharge") )
		{
			cin.setElementAt("Y", 4);				// position 5
		}
		else
		{
			if ( companyId != null && companyId.length() > 0 )
			{
				cin.setElementAt(companyId, 0);		// position 1 - CompanyId1 
			}

			if ( shipToId != null && shipToId.length() > 0 )
			{
				//log.debug("shipToId = [" + shipToId + "]; ");
				String shipToIdOriginal = shipToId.replace("~", "#" );
				//log.debug("shipToIdOriginal = [" + shipToIdOriginal + "]; ");
				// cin.setElementAt("TANK#200905-004", 1);		// position 2 - shipToId
				cin.setElementAt(shipToIdOriginal, 1);		// position 2 - shipToId 
			}
		}
		cin.setElementAt(searchArgument_1, 2);		// position 3
		cin.setElementAt(searchArgument_2, 3);		// position 4
		cin.setElementAt(sortBy, 5);				// position 6
		
		if (validBPO != null && mode.equals("Yes") )
		{
			cin.setElementAt("Y", 6);				// position 7 - validBPO == "Yes"
		}
		
		if ( inventoryGroup != null && inventoryGroup.length() > 0 )
		{
			cin.setElementAt(inventoryGroup, 7);		// position 8 - inventoryGroup 
		}

		// only for debugging... :
		/*  
		for (int i = 0; i < cin.size(); i++ )
		{
			String valueString;
			if ( ( (Vector) cin).elementAt( i ) == null)
			{
				valueString = "(null)";
			} 
			else
			{
				valueString = ( (Vector) cin).elementAt( i ).toString();
			} 
				
			log.debug("cin." + (i + 1) + ".toString() = [" + valueString + "];");
		}
		*/
		// ...end temporary block
		
		Collection cout = new Vector();
		
		cout.add(new Integer(OracleTypes.CURSOR)); // this indicates a ResultSet
		
		cout.add(new Integer(java.sql.Types.VARCHAR));
		
		Collection resultCollection = null;
		ResultSet resultSet = null;
		SqlManager sqlManager = new SqlManager();
		String procedureName = "pkg_po.item_search";
	    log.debug("calling pkg_po.item_search "+cin);
		try 
		{
			CallableStatement cs = null;
			String query0 = "{call pkg_po.item_search(?,?,?,?,?,?,?,?,?,?)}";
			cs = connection.prepareCall(query0);
			for (int i = 0; i < 8; i++ )
			{
		        cs.setObject(i+1, cin.elementAt(i));
			}
	        cs.registerOutParameter(9,OracleTypes.CURSOR);
	        cs.registerOutParameter(10,OracleTypes.VARCHAR);
	        cs.execute();
//			resultCollection = sqlManager.doProcedure(connection, procedureName, cin, cout);
//			String query = (String)( (Vector) resultCollection).elementAt(1);
	        String query = (String)cs.getObject(10); 
			log.debug(query);
		
//			resultSet = (ResultSet)( (Vector) resultCollection).elementAt(0);
			resultSet = (ResultSet) cs.getObject(9) ;
			DataSet dataSet = new DataSet();
	    	new SqlManager().populateDataSet(dataSet, resultSet);
	    	Iterator dataIter = dataSet.iterator();
	    	while (dataIter.hasNext()) 
	    	{
	    		DataSetRow dataSetRow = (DataSetRow) dataIter.next();
	    		POItemBean pOItemBean = new POItemBean();
	    		load(dataSetRow, pOItemBean);
	    		pOItemBeanCollection.add(pOItemBean);
	    	//log.debug("pOItemBean.toString() = [" + pOItemBean.toString() + "]; ");
	    	}
	    	cs.close();
	    	this.getDbManager().returnConnection(connection);
	    }
	    catch(Exception e) 
	    {
	    	log.error("Exception in SqlManager().populateDataSet(dataSet, resultSet)", e);
	    	throw new BaseException(e);
	    }
		return pOItemBeanCollection;
	 }
}