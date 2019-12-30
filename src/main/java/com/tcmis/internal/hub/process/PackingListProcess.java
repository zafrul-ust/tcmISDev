package com.tcmis.internal.hub.process;

/**
 * Title: Your Product Name Description: Your description Copyright: Copyright
 * (c) 1998 Company: Your Company
 * 
 * @author Your Name
 * @version 06-25-04 Adding log statements to trace a memory usage issue
 */

// ACJEngine
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import radian.tcmis.common.util.StringHandler;

import com.actuate.ereport.datasrcmgrs.appdatasrc.AppDataHandler;
import com.actuate.ereport.engine.ACJEngine;
import com.actuate.ereport.engine.TemplateManager;
import com.actuate.ereport.engine.lib.ACJConstants;
import com.actuate.ereport.output.ACJOutputProcessor;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericProcedureFactory;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.internal.hub.beans.FreightTlTrackingViewBean;
import com.tcmis.internal.hub.beans.HomeCompanyBean;
import com.tcmis.internal.hub.beans.InventoryGroupDefinitionBean;
import com.tcmis.internal.hub.beans.ShipmentBean;
import com.tcmis.internal.hub.erw.PackingListDetail;
import com.tcmis.internal.hub.erw.PackingListHeader;
import com.tcmis.internal.hub.erw.RegisterTable;
import com.tcmis.internal.hub.factory.HomeCompanyBeanFactory;
import com.tcmis.internal.hub.factory.InventoryGroupDefinitionBeanFactory;
import com.tcmis.internal.hub.factory.PackingListDetailViewBeanFactory;
import com.tcmis.internal.hub.factory.PackingListHeaderViewBeanFactory;

public class PackingListProcess extends BaseProcess
{

	// ACJEngine
	private ACJEngine erw = null;
	private OutputStream os = null;
	private TemplateManager tm = null;
	private ACJOutputProcessor ec = null;

	private String templateName = "";
	private String logoURL = "";
	private int numberOfCopies = 1;
	/**
	 * @return the templateName
	 */
	public String getTemplateName()
	{
		return this.templateName;
	}

	/**
	 * @param templateName
	 *            the templateName to set
	 */
	public void setTemplateName(String templateName)
	{
		this.templateName = templateName;
	}

	/**
	 * @return the logoURL
	 */
	public String getLogoURL()
	{
		return this.logoURL;
	}

	/**
	 * @param logoURL
	 *            the logoURL to set
	 */
	public void setLogoURL(String logoURL)
	{
		this.logoURL = logoURL;
	}
	
	/**
	 * @param numberOfCopies
	 *            the numberOfCopies to set
	 */
	public void setNumberOfCopies(int numberOfCopies)
	{
		this.numberOfCopies = numberOfCopies;
	}

	/**
	 * @return the numberOfCopies
	 */
	public int getNumberOfCopies()
	{
		return this.numberOfCopies;
	}


	

	Log log = LogFactory.getLog(this.getClass());

	public PackingListProcess(String client)
	{
		super(client);
	}

	/**
	 * Gets data to be used in the report request.
	 * 
	 * @param shipmentId
	 * @throws Exception 
	 */
	public void getReportParametersData(String[] shipmentId) throws Exception
	{
		Collection homeCompanyLogo = getHomeCompanyLogo(shipmentId);
		Collection inventoryGroupTemplate = getInventoryGroupTemplate(shipmentId);
		Collection transferRequestCollection = getTransferRequest(shipmentId);
		//HashMap transferRequestArr = getTransferRequest(shipmentId);
		//Iterator trIterator = transferRequestCollection.iterator();
		Iterator iterator = homeCompanyLogo.iterator();
		while (iterator.hasNext())
		{
			HomeCompanyBean homeCompanyBean = (HomeCompanyBean) iterator
					.next();
			logoURL = homeCompanyBean.getLogoImageUrl();
		}
		
		// get template type
		iterator = transferRequestCollection.iterator();
		String transferRequest = (String) iterator.next();
		
		while(iterator.hasNext()){
			transferRequest = (String) iterator.next();
		}
		
		// get template name and number of copies
		
		iterator = inventoryGroupTemplate.iterator();
		while (iterator.hasNext())
		{
			InventoryGroupDefinitionBean inventoryGroupDefinitionBean = (InventoryGroupDefinitionBean) iterator
					.next();
			//String transferRequest = (String) trIterator.next();
			//String transferRequest = (String) transferRequestArr.get(shipmentId);
			//if(!StringHandler.isBlankString(inventoryGroupDefinitionBean.getTransferPackingListTemplate()))
			if(StringHandler.isBlankString(inventoryGroupDefinitionBean.getTransferPackingListTemplate())
					|| StringHandler.isBlankString(transferRequest) || !transferRequest.equalsIgnoreCase("Y"))
				templateName = inventoryGroupDefinitionBean
				.getPackingListTemplate();
			else	
				templateName = inventoryGroupDefinitionBean.getTransferPackingListTemplate();
	
			numberOfCopies = ((BigDecimal) inventoryGroupDefinitionBean
					.getPackingListNumCopies()).intValue();
		}
		// make sure we print at least one copy
		if (numberOfCopies < 1)
		{
			numberOfCopies = 1;
		}
	}

	public String load(String[] shipmentId)
	{
		String url = "";
		Random rand = new Random();
		int tmpReq = rand.nextInt();
		Integer tmpReqNum = new Integer(tmpReq);

		try
		{
			// first get data
			Collection headerData = getHeaderData(shipmentId);
			Collection detailData = getDetailData(shipmentId);
			Collection homeCompanyLogo = getHomeCompanyLogo(shipmentId);
			Collection inventoryGroupTemplate = getInventoryGroupTemplate(shipmentId);
			Iterator iterator = homeCompanyLogo.iterator();
			while (iterator.hasNext())
			{
				HomeCompanyBean homeCompanyBean = (HomeCompanyBean) iterator
						.next();
				logoURL = homeCompanyBean.getLogoImageUrl();
			}
			// get template name and number of copies
			int numberOfCopies = 1;
			iterator = inventoryGroupTemplate.iterator();
			while (iterator.hasNext())
			{
				InventoryGroupDefinitionBean inventoryGroupDefinitionBean = (InventoryGroupDefinitionBean) iterator
						.next();
				templateName = inventoryGroupDefinitionBean
						.getPackingListTemplate();
				numberOfCopies = ((BigDecimal) inventoryGroupDefinitionBean
						.getPackingListNumCopies()).intValue();
			}
			// make sure we print at least one copy
			if (numberOfCopies < 1)
			{
				numberOfCopies = 1;
			}

			erw = new ACJEngine();
			erw.setDebugMode(true);
			erw.setX11GfxAvailibility(false);
			erw.setTargetOutputDevice(ACJConstants.PDF);
			ec = new ACJOutputProcessor();

			ResourceLibrary resource = new ResourceLibrary("report");
			String fontmappath = resource.getString("report.font.path");
			ec.setPathForFontMapFile(fontmappath);

			// loading template
			String templatpath = resource.getString("report.template.path");
			erw.readTemplate("" + templatpath + templateName);
			// modifying template
			tm = erw.getTemplateManager();
			tm.setImageURL("IMG000", logoURL);
			// register table...
			String tempwritefilepath = resource.getString("report.serverpath");
			// erw.setCacheOption(true,
			// ""+tempwritefilepath+"PackingList"+tmpReqNum.toString()+".joi");

			AppDataHandler ds = new AppDataHandler();
			RegisterTable[] rt = getData(headerData, detailData, numberOfCopies);
			for (int i = 0; i < rt.length; i++)
			{
				Vector v1 = rt[i].getData();
				Vector v2 = rt[i].getMethods();
				ds.RegisterTable(rt[i].getData(), rt[i].getName(),
						rt[i].getMethods(), rt[i].getWhere());
			}
			erw.setDataSource(ds);
			// generating report
			ec.setReportData(erw.generateReport());
			ec.setPDFProperty("FileName", "" + tempwritefilepath
					+ "PackingList" + tmpReqNum.toString() + ".pdf");
			ec.setPDFProperty("ZipCompressed", new Boolean(false));
			ec.generatePDF();

			// send url back to client to open
			String tempurlpath = resource.getString("report.hosturl")
					+ resource.getString("report.urlpath");
			url = tempurlpath + "PackingList" + tmpReqNum.toString() + ".pdf";
		}
		catch (Exception e)
		{
			e.printStackTrace();
			url = "";
		}
		return url;
	}

	Collection getHeaderData(String[] shipmentId) throws Exception
	{
		Collection col = null;
		try
		{
			DbManager dbManager = new DbManager(getClient());
			PackingListHeaderViewBeanFactory factory = new PackingListHeaderViewBeanFactory(
					dbManager);
			col = factory.selectForPackingList(shipmentId);
		}
		catch (Exception e)
		{
			throw e;
		}
		return col;
	} // end of method

	Collection getDetailData(String[] shipmentId) throws Exception
	{
		Collection col = null;
		try
		{
			DbManager dbManager = new DbManager(getClient());
			PackingListDetailViewBeanFactory factory = new PackingListDetailViewBeanFactory(
					dbManager);
			col = factory.selectForPackingList(shipmentId);
		}
		catch (Exception e)
		{
			throw e;
		}
		return col;
	} // end of method

	Collection getHomeCompanyLogo(String[] shipmentId) throws Exception
	{
		Collection col = null;
		try
		{
			DbManager dbManager = new DbManager(getClient());
			HomeCompanyBeanFactory factory = new HomeCompanyBeanFactory(
					dbManager);
			col = factory.selectLogoForPackingList(shipmentId);
		}
		catch (Exception e)
		{
			throw e;
		}
		return col;
	} // end of method

	Collection getInventoryGroupTemplate(String[] shipmentId) throws Exception
	{
		Collection col = null;
		try
		{
			DbManager dbManager = new DbManager(getClient());
			InventoryGroupDefinitionBeanFactory factory = new InventoryGroupDefinitionBeanFactory(
					dbManager);
			col = factory.selectForTemplateNameNCopies(shipmentId);
		}
		catch (Exception e)
		{
			throw e;
		}
		return col;
	} // end of method

	public RegisterTable[] getData(Collection headerData,
			Collection detailData, int numberOfCopies) throws Exception
	{
		boolean result = true;
		RegisterTable[] rt = new RegisterTable[2];
		try
		{
			rt[0] = new RegisterTable(PackingListHeader.getVector(headerData,
					numberOfCopies), "PACKINGLISTHEADER",
					PackingListHeader.getFieldVector(), null);
			rt[1] = new RegisterTable(PackingListDetail.getVector(detailData),
					"PACKINGLISTDETAIL", PackingListDetail.getFieldVector(),
					null);
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
			throw e1;
		}
		return rt;
	}
	
	public void setWarrantyDate(String[] shipmentId)
	{
		try{
		     DbManager dbManager = new DbManager(getClient(),getLocale());
		     GenericProcedureFactory procFactory = new GenericProcedureFactory(dbManager);
		     
		     for (int i = 0; i < shipmentId.length; i++)
		     {
		    	Collection cin  = new Vector(1);
			 	cin.add(shipmentId[i]);
			 	procFactory.doProcedure("pkg_defined_shelf_life.p_shipment_cust_warranty_date", cin);
		     }
		 }
		 catch (Exception e) {
			  e.printStackTrace();
			  
			  }

     }
	
	Collection<String> getTransferRequest(String[] shipmentId) throws Exception
	{
		Collection ret = null;
		//HashMap ret = null;
		try
		{
			DbManager dbManager = new DbManager(getClient());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager);
			
			StringBuilder query = new StringBuilder("SELECT NVL2 (itr.transfer_request_id, 'Y', 'N') transfer_request  ");
			query.append("FROM shipment s, issue i,  inventory_transfer_request itr ");
			query.append("WHERE s.shipment_id = i.shipment_id ");
			query.append("AND i.pr_number = itr.transfer_request_id(+) ");
			query.append("AND s.shipment_id in (");
			
			for (int i = 0; i < shipmentId.length; i++) {
				if(i != 0)
					query.append(",");
				 query.append(shipmentId[i]);
			}
			
			query.append(")");
			
			Object[] result = factory.selectIntoObjectArray(query.toString());
	        
	        Collection<Object[]> transferRequestColl = (Collection)result[GenericSqlFactory.DATA_INDEX];
	        ret = new Vector<String>();
	        //ret = new HashMap();
	        for (Iterator<Object[]> it = transferRequestColl.iterator(); it.hasNext();) {
	        	Object[] nxt = it.next();
	        	if (nxt[0] != null) {
	        		ret.add(nxt[0].toString());
	        	}
	        /*	if(nxt[0] != null){
	        		ret.put(nxt[0], nxt[1]);
	        	}*/
	        }
		}
		catch (Exception e)
		{
			throw e;
		}
		
		return ret;
	}
}
