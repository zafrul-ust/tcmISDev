package com.tcmis.supplier.shipping.process;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.ZplDataProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.PrintHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.internal.hub.beans.LabelInputBean;
import com.tcmis.supplier.shipping.beans.MslPalletViewBean;
import com.tcmis.supplier.shipping.beans.SupplierPackingSummaryViewBean;
import com.tcmis.supplier.shipping.beans.SupplierPackingViewBean;
import com.tcmis.supplier.shipping.beans.SupplierUsgovLabelViewBean;
import com.tcmis.supplier.shipping.factory.MslBoxViewBeanFactory;
import com.tcmis.supplier.shipping.factory.MslPalletViewBeanFactory;
import com.tcmis.supplier.shipping.factory.SupplierUsgovLabelViewBeanFactory;
import com.tcmis.supplier.shipping.factory.UsgovDd250ViewBeanFactory;

import radian.tcmis.common.util.StringHandler;

/******************************************************************************
 * Process for receiving qc
 * 
 * @version 1.0
 *****************************************************************************/
public class LabelProcess extends BaseProcess
{
	Log log = LogFactory.getLog(this.getClass());
	
	private static final long TOTAL_SECONDS_TO_PAUSE_PRINT_JOB = 10;

	public LabelProcess(String client)
	{
		super(client);
	}

	public void print(String printerName, String fileName)
			throws BaseException, Exception
	{
		PrintHandler.print(printerName, fileName);
	}

	public String buildUnitBoxLabels(PersonnelBean personnelBean,
			LabelInputBean labelInputBean,
			Collection locationLabelPrinterCollection,
			Collection suppPackSummaryViewBeanCollection) throws BaseException
	{
		String labelUrl = "";
		ResourceLibrary resource = new ResourceLibrary("label");
		if (personnelBean.getPrinterLocation() != null
				&& personnelBean.getPrinterLocation().length() > 0
				&& !"811".equalsIgnoreCase(labelInputBean.getPaperSize()))
		{
			ZplProcess zplProcess = new ZplProcess(this.getClient());
			Collection labelDataCollection = getLabelData(suppPackSummaryViewBeanCollection);
			File dir = new File(resource.getString("label.serverpath"));
			try
			{
				File file = File.createTempFile("labeljnlp", ".jnlp", dir);

				zplProcess.buildLabelZpl(labelDataCollection, "",
						locationLabelPrinterCollection, file.getAbsolutePath());

				labelUrl = resource.getString("label.hosturl")
						+ resource.getString("label.urlpath") + file.getName();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				labelUrl = "";
			}
		}
		return labelUrl;
	}

	public String buildPrintTable(PersonnelBean personnelBean,
			LabelInputBean labelInputBean,
			Collection locationLabelPrinterCollection,
			Collection suppPackSummaryViewBeanCollection) throws BaseException
	{
		String labelUrl = "";
		ResourceLibrary resource = new ResourceLibrary("label");
		if (personnelBean.getPrinterLocation() != null
				&& personnelBean.getPrinterLocation().length() > 0
				&& !"811".equalsIgnoreCase(labelInputBean.getPaperSize()))
		{
			ZplProcess zplProcess = new ZplProcess(this.getClient());
			Collection labelDataCollection = getLabelData(suppPackSummaryViewBeanCollection);
			File dir = new File(resource.getString("label.serverpath"));
			try
			{
				File file = File.createTempFile("labeljnlp", ".jnlp", dir);

				zplProcess.buildLabelZpl(labelDataCollection, "",
						locationLabelPrinterCollection, file.getAbsolutePath());

				labelUrl = resource.getString("label.hosturl")
						+ resource.getString("label.urlpath") + file.getName();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				labelUrl = "";
			}
		}
		return labelUrl;
	}

	public Object[] buildPrintCaseMSL(PersonnelBean personnelBean,
			LabelInputBean labelInputBean,
			Collection locationLabelPrinterCollection,
			Collection suppPackSummaryViewBeanCollection) throws BaseException
	{
		String labelUrl = "";
		String fileAbsolutePath = "";
		ResourceLibrary resource = new ResourceLibrary("label");
		{
			MslZplProcess mslZplProcess = new MslZplProcess(this.getClient());
			Collection labelDataCollection = getCaseMSLData(suppPackSummaryViewBeanCollection);
			File dir = new File(resource.getString("label.serverpath"));
			try
			{
				File file = File.createTempFile("labeljnlp", ".jnlp", dir);
				fileAbsolutePath = mslZplProcess.buildCaseMslZpl(
						labelDataCollection, labelInputBean.getLabelType(),
						labelInputBean.getSourcePage(), "",
						locationLabelPrinterCollection, !"Yes"
								.equalsIgnoreCase(labelInputBean
										.getSkipKitLabels()), file
								.getAbsolutePath());

				labelUrl = resource.getString("label.hosturl")
						+ resource.getString("label.urlpath") + file.getName();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				labelUrl = "";
			}
		}
		Object[] objs = { fileAbsolutePath, labelUrl };
		return objs;
	}

	public Object[] buildPrintCaseMSL(LabelInputBean labelInputBean,
			Collection locationLabelPrinterCollection,
			Collection labelDataCollection) throws BaseException
	{
		String labelUrl = "";
		String fileAbsolutePath = "";
		ResourceLibrary resource = new ResourceLibrary("label");
		MslZplProcess mslZplProcess = new MslZplProcess(this.getClient());
		File dir = new File(resource.getString("label.serverpath"));
		try
		{
			File file = File.createTempFile("labeljnlp", ".jnlp", dir);
			fileAbsolutePath = mslZplProcess.buildCaseMslZpl(
					labelDataCollection, labelInputBean.getLabelType(),
					labelInputBean.getSourcePage(), "",
					locationLabelPrinterCollection,
					!"Yes".equalsIgnoreCase(labelInputBean.getSkipKitLabels()),
					file.getAbsolutePath());

			labelUrl = resource.getString("label.hosturl")
					+ resource.getString("label.urlpath") + file.getName();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			labelUrl = "";
		}
		Object[] objs = { fileAbsolutePath, labelUrl };
		return objs;
	}
	

	public Object[] buildPrintCaseMSLByBatch(LabelInputBean labelInputBean,
			Collection locationLabelPrinterCollection,
			Collection labelDataCollection) throws BaseException
	{
		String labelUrl = "";
		String fileAbsolutePath = "";
		ResourceLibrary resource = new ResourceLibrary("label");
		MslZplProcess mslZplProcess = new MslZplProcess(this.getClient());
		File dir = new File(resource.getString("label.serverpath"));
		try
		{
			File file = File.createTempFile("labeljnlp", ".jnlp", dir);
			fileAbsolutePath = mslZplProcess.buildCaseMslZpl(
					labelDataCollection, labelInputBean.getLabelType(),
					labelInputBean.getSourcePage(), "",
					locationLabelPrinterCollection,
					!"Yes".equalsIgnoreCase(labelInputBean.getSkipKitLabels()),
					file.getAbsolutePath());

			labelUrl = resource.getString("label.hosturl")
					+ resource.getString("label.urlpath") + file.getName();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			labelUrl = "";
		}
		Object[] objs = { fileAbsolutePath, labelUrl };
		return objs;
	}

	public Object[] buildPrintPalletMSL(PersonnelBean personnelBean,
			LabelInputBean labelInputBean,
			Collection locationLabelPrinterCollection,
			Collection suppPackSummaryViewBeanCollection) throws BaseException
	{
		String labelUrl = "";
		String fileAbsolutePath = "";
		ResourceLibrary resource = new ResourceLibrary("label");
		{
			MslZplProcess mslZplProcess = new MslZplProcess(this.getClient());
			Collection labelDataCollection = getPalletMSLData(suppPackSummaryViewBeanCollection);
			File dir = new File(resource.getString("label.serverpath"));
			try
			{
				File file = File.createTempFile("labeljnlp", ".jnlp", dir);
				fileAbsolutePath = mslZplProcess.buildPalletMslZpl(
						labelDataCollection, labelInputBean.getLabelType(),
						"usgovpalletmsl", labelInputBean.getSourcePage(), "",
						locationLabelPrinterCollection, file.getAbsolutePath());

				labelUrl = resource.getString("label.hosturl")
						+ resource.getString("label.urlpath") + file.getName();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				labelUrl = "";
			}
		}
		Object[] objs = { fileAbsolutePath, labelUrl };
		return objs;
	}

	public Object[] buildPrintPalletMSL(LabelInputBean labelInputBean,
			Collection locationLabelPrinterCollection,
			Collection labelDataCollection) throws BaseException
	{
		String labelUrl = "";
		String fileAbsolutePath = "";
		ResourceLibrary resource = new ResourceLibrary("label");
		MslZplProcess mslZplProcess = new MslZplProcess(this.getClient());

		File dir = new File(resource.getString("label.serverpath"));
		try
		{
			File file = File.createTempFile("labeljnlp", ".jnlp", dir);
			fileAbsolutePath = mslZplProcess.buildPalletMslZpl(
					labelDataCollection, labelInputBean.getLabelType(),
					"usgovpalletmsl", labelInputBean.getSourcePage(), "",
					locationLabelPrinterCollection, file.getAbsolutePath());

			labelUrl = resource.getString("label.hosturl")
					+ resource.getString("label.urlpath") + file.getName();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			labelUrl = "";
		}
		Object[] objs = { fileAbsolutePath, labelUrl };
		return objs;
	}

	public Object[] printPalletMSL(PersonnelBean personnelBean,LabelInputBean labelInputBean,
			Collection locationLabelPrinterCollection,
			Collection suppPackSummaryViewBeanCollection) throws BaseException
	{
		MslZplProcess mslZplProcess = new MslZplProcess(this.getClient());
		Collection labelDataCollection = getPalletMSLData(suppPackSummaryViewBeanCollection);
		List<String> fileAbsolutePath = new ArrayList<String>();
		String filePath="";
		try
		{
			fileAbsolutePath = mslZplProcess.buildPalletMslZpl(
					labelDataCollection, labelInputBean.getLabelType(),
					"usgovpalletmsl", labelInputBean.getSourcePage(), "",
					locationLabelPrinterCollection);
			
			for(String pathUrl : fileAbsolutePath) {
				printLabel(personnelBean,labelInputBean, pathUrl);
				filePath = pathUrl;
				//making the process to delay for some seconds. 
				//But it may not needed as each batch is going to be an each job for printer.
				//Will be removed after code review
				if (fileAbsolutePath.indexOf(pathUrl) != fileAbsolutePath.size() - 1) {
					TimeUnit.SECONDS.sleep(TOTAL_SECONDS_TO_PAUSE_PRINT_JOB);
			    }
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		Object[] objs = { fileAbsolutePath, filePath };
		return objs;
	}
	
	public void printLabel(PersonnelBean personnelBean,LabelInputBean labelInputBean, String filePath) throws BaseException,Exception {
		ZplDataProcess zplDataProcess = new ZplDataProcess(this.getClient());
		Collection locationLabelPrinterCollection = new Vector();
		locationLabelPrinterCollection = zplDataProcess.getLocationLabelPrinter(personnelBean,labelInputBean);
		String printerPath = zplDataProcess.getPrinterPath(locationLabelPrinterCollection);
		//PrintHandler.print(printerPath, ""+filePath+""); /*Uncomment after test*/
	}

	public Object[] buildPrintPlacardLabels(PersonnelBean personnelBean,
			LabelInputBean labelInputBean,
			Collection locationLabelPrinterCollection,
			Collection suppPackSummaryViewBeanCollection) throws BaseException
	{
		String labelUrl = "";
		String fileAbsolutePath = "";
		ResourceLibrary resource = new ResourceLibrary("label");
		ZplProcess zplProcess = new ZplProcess(this.getClient());
		Collection finalLabelDataCollection = new Vector();
		MslPalletViewBean palletBean = null;
		SupplierPackingViewBean mrBean = null;
		for (Object obj : suppPackSummaryViewBeanCollection)
		{
			if (obj == null)
				continue;
			mrBean = (SupplierPackingViewBean) obj;
			Collection mrBeanCollection = new Vector(1);
			mrBeanCollection.add(mrBean);
			Collection palletDataCollection = getPalletMSLData(mrBeanCollection);
			for (Object palletObj : palletDataCollection)
			{
				if (obj == null)
					continue;
				palletBean = (MslPalletViewBean) palletObj;
				Collection labelDataCollection = getPlacardLabelData(mrBean,
						palletBean, labelInputBean);
				finalLabelDataCollection.addAll(labelDataCollection);
			}
		}
		File dir = new File(resource.getString("label.serverpath"));
		try
		{
			File file = File.createTempFile("labeljnlp", ".jnlp", dir);

			zplProcess.buildPlacardLabelZpl(finalLabelDataCollection, "",
					locationLabelPrinterCollection, file.getAbsolutePath());

			labelUrl = resource.getString("label.hosturl")
					+ resource.getString("label.urlpath") + file.getName();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			labelUrl = "";
		}
		Object[] objs = { fileAbsolutePath, labelUrl };
		return objs;
	}

	public Object[] buildCommercialLabel(LabelInputBean labelInputBean,
			Collection locationLabelPrinterCollection,
			Collection labelDataCollection, String labelType)
			throws BaseException
	{
		String labelUrl = "";
		String fileAbsolutePath = "";
		ResourceLibrary resource = new ResourceLibrary("label");
		MslZplProcess mslZplProcess = new MslZplProcess(this.getClient());
		File dir = new File(resource.getString("label.serverpath"));
		try
		{
			File file = File.createTempFile("labeljnlp", ".jnlp", dir);
			fileAbsolutePath = mslZplProcess.buildPalletMslZpl(
					labelDataCollection, labelInputBean.getLabelType(),
					labelType, labelInputBean.getSourcePage(), "",
					locationLabelPrinterCollection, file.getAbsolutePath());

			labelUrl = resource.getString("label.hosturl")
					+ resource.getString("label.urlpath") + file.getName();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			labelUrl = "";
		}
		Object[] objs = { fileAbsolutePath, labelUrl };
		return objs;
	}

	// TCMISDEV-260: Convert the Material Inspection
	// & Receiving Report (DD250)
	/**
	 * Generates the list of pr Numbers of the selected line items
	 * 
	 * @param suppPackViewBeanCollection
	 * @return String
	 */
	public String getPrNumbers(Collection suppPackViewBeanCollection)
	{
		StringBuilder prNumbersStr = new StringBuilder();
		Iterator iterator = suppPackViewBeanCollection.iterator();
		while (iterator.hasNext())
		{
			SupplierPackingViewBean suppPackViewBean = (SupplierPackingViewBean) iterator
					.next();
			if (suppPackViewBean.getOk() != null
					&& suppPackViewBean.getOk().length() > 0)
			{
				prNumbersStr.append(suppPackViewBean.getPrNumber());
				prNumbersStr.append(",");
			}
		}
		return prNumbersStr.toString();
	}

	public Object[] buildPrintPackingList(
			Collection suppPackSummaryViewBeanCollection) throws BaseException
	{
		Collection labelDataCollection = getDd250Data(suppPackSummaryViewBeanCollection);
		String pdfUrl = "";
		String fileAbsolutePath = "";
		Object[] resultObjs = null;

		if (labelDataCollection.size() == 0)
		{
			String url = "";
			Object[] objs1 = { url, url };
			return objs1;
		}
		PrintDd250Process printPdfProcess = new PrintDd250Process(
				this.getClient());
		try
		{
			resultObjs = printPdfProcess.buildDd250Pdf(labelDataCollection);
		}
		catch (Exception ex1)
		{
			ex1.printStackTrace();
			pdfUrl = "";
		}
		return resultObjs;
	}
	
	/**
	 * TCMISDEV-3646
	 * Changes due to Serial Number requirements for External and Unit Labels can't be implemented in one view or procedure 
	 * If the part does not require Serial Number Tracking, return just a unitData map from SUPPLIER_USGOV_LABEL_VIEW that will print just like it did before these changes
	 * Else for Serial Number Tracking, return a boxData map from supplier_usgov_extlabel_view plus Serial Numbers from fx_part_serial_number_list per pallet ID or case ID
	 * @param  Collection of user selection of what to print
	 * @return Main collection of maps per user selection
	 **/
	public Collection getLabelData(Collection suppPackSummaryViewBeanCollection)
			throws BaseException
	{
		DbManager dbManager = new DbManager(getClient());
		Connection connection = dbManager.getConnection();
		GenericSqlFactory sqlFactory = new GenericSqlFactory(dbManager,new SupplierUsgovLabelViewBean());
		SupplierUsgovLabelViewBeanFactory factory = new SupplierUsgovLabelViewBeanFactory(
				dbManager);
		Collection finalLabelDataCollection = new Vector();
		HashMap rowLabelDataMap = null;
		Iterator iterator = suppPackSummaryViewBeanCollection.iterator();
		try
		{
			while (iterator.hasNext())
			{
				SupplierPackingSummaryViewBean suppPackSummaryViewBean = (SupplierPackingSummaryViewBean) iterator
						.next();
				if (suppPackSummaryViewBean.getOk() != null
						&& suppPackSummaryViewBean.getOk().length() > 0)
				{
					Vector<SupplierUsgovLabelViewBean> boxLabelDataColl = null;
					Vector<SupplierUsgovLabelViewBean> serialNumberDataColl = null;
					boolean isTrackSN = "Y".equalsIgnoreCase(suppPackSummaryViewBean.getTrackSerialNumber());
					SearchCriteria criteria = new SearchCriteria();
					criteria.addCriterion("prNumber", SearchCriterion.EQUALS, ""
							+ suppPackSummaryViewBean.getPrNumber() + "");
					criteria.addCriterion("lineItem", SearchCriterion.EQUALS, ""
							+ suppPackSummaryViewBean.getLineItem() + "");
					criteria.addCriterion("picklistId", SearchCriterion.EQUALS, ""
							+ suppPackSummaryViewBean.getPicklistId() + "");
					/*criteria.addCriterion("receiptId", SearchCriterion.EQUALS, ""
							+ suppPackSummaryViewBean.getReceiptId() + "");*/
					
					Collection receiptLabelDataColl = new Vector();
					receiptLabelDataColl = factory.select(criteria, null, connection);
					rowLabelDataMap = new HashMap();
					if(isTrackSN)
					{
					    boxLabelDataColl = (Vector<SupplierUsgovLabelViewBean>)sqlFactory.select(criteria, null,connection, "supplier_usgov_extlabel_view");				   
					    String delTick = suppPackSummaryViewBean.getSupplierSalesOrderNo();				   
	
						SupplierUsgovLabelViewBean supplierUsgovLabelViewBean =  ((Vector<SupplierUsgovLabelViewBean>)receiptLabelDataColl).firstElement();
						receiptLabelDataColl = new Vector();
						for(int i = 0;i < boxLabelDataColl.size();i++)
						{
							SupplierUsgovLabelViewBean boxBean = boxLabelDataColl.get(i);
							String skipUnitLabels = suppPackSummaryViewBean.getSkipUnitLabels();
							boxBean.setSkipUnitLabels(skipUnitLabels);					
							
							if(StringHandler.isBlankString(skipUnitLabels))
							{
								int snCount = boxBean.getNumOfSerialNumber().intValue();
								StringBuilder query = new StringBuilder("SELECT SERIAL_NUMBER from table(fx_part_serial_number_list('").append(supplierUsgovLabelViewBean.getSupplier()).append("','").append(delTick);
								query.append("','").append(supplierUsgovLabelViewBean.getShipFromLocationId()).append("','");
		
								if(!StringHandler.isBlankString(boxBean.getPalletId()))
										query.append(boxBean.getPalletId()).append("','')) SERIAL_NUMBER_LIST order by SERIAL_NUMBER asc"); 
								else
									query.append("','").append(boxBean.getBoxId()).append("')) SERIAL_NUMBER_LIST order by SERIAL_NUMBER asc"); 
							    serialNumberDataColl = (Vector<SupplierUsgovLabelViewBean>)sqlFactory.selectQuery(query.toString(),connection);						
								for(int j = 0;j < snCount;++j)
								{
									SupplierUsgovLabelViewBean nextSupplierUsgovLabelViewBean = new SupplierUsgovLabelViewBean();
									BeanHandler.copyAttributes(supplierUsgovLabelViewBean, nextSupplierUsgovLabelViewBean);
									nextSupplierUsgovLabelViewBean.setSerialNo1(serialNumberDataColl.get(j).getSerialNumber());
									nextSupplierUsgovLabelViewBean.setPalletId(boxBean.getPalletId());
									nextSupplierUsgovLabelViewBean.setBoxId(boxBean.getBoxId());
									receiptLabelDataColl.add(nextSupplierUsgovLabelViewBean);
								}
							}
							else
								receiptLabelDataColl.add(supplierUsgovLabelViewBean);								
							
							String[] boxLabeSNs = boxBean.getSerialNumber().split(",");
							
							for(int k = 0;k < boxLabeSNs.length;k++)
							{
								switch(k)
								{
									case 0:
										boxBean.setSerialNo1(boxLabeSNs[k]);
									break;
									case 1:
										boxBean.setSerialNo2(boxLabeSNs[k]);
									break;
									case 2:
										boxBean.setSerialNo3(boxLabeSNs[k]);
									break;
									case 3:
										boxBean.setSerialNo4(boxLabeSNs[k]);
									break;
									case 4:
										boxBean.setSerialNo5(boxLabeSNs[k]);
									break;
									case 5:
										boxBean.setSerialNoComment(boxLabeSNs[k]);
									break;
								}
							}
						}
						 rowLabelDataMap.put("boxData",boxLabelDataColl);
					}		
					else
					{
						Iterator labelDataiterator = receiptLabelDataColl.iterator();
						while (labelDataiterator.hasNext())
						{
		
								SupplierUsgovLabelViewBean supplierUsgovLabelViewBean = (SupplierUsgovLabelViewBean) labelDataiterator
								.next();
								supplierUsgovLabelViewBean
								.setSkipUnitLabels(suppPackSummaryViewBean
										.getSkipUnitLabels());
						}	
					}
					rowLabelDataMap.put("unitData",receiptLabelDataColl);
					finalLabelDataCollection.add(rowLabelDataMap);
				}
			}
        }
		finally {
			dbManager.returnConnection(connection);
			dbManager = null;
			connection = null;
			sqlFactory = null;
			factory = null;
		}
		return finalLabelDataCollection;
	}

	public Collection getPlacardLabelData(
			SupplierPackingViewBean suppPackSummaryViewBean,
			MslPalletViewBean palletBean, LabelInputBean labelInputBean)
			throws BaseException
	{
		DbManager dbManager = new DbManager(getClient());
		SupplierUsgovLabelViewBeanFactory factory = new SupplierUsgovLabelViewBeanFactory(
				dbManager);
		Collection receiptLabelDataColl = new Vector();
		if (suppPackSummaryViewBean.getOk() != null
				&& suppPackSummaryViewBean.getOk().length() > 0)
		{
			SearchCriteria criteria = new SearchCriteria();
			criteria.addCriterion("prNumber", SearchCriterion.EQUALS, ""
					+ suppPackSummaryViewBean.getPrNumber() + "");
			criteria.addCriterion("lineItem", SearchCriterion.EQUALS, ""
					+ suppPackSummaryViewBean.getLineItem() + "");
			labelInputBean.setPalletId(suppPackSummaryViewBean.getPalletId());
			receiptLabelDataColl = factory.selectPlacardData(criteria, null,
					labelInputBean);
			Iterator labelDataiterator = receiptLabelDataColl.iterator();
			while (labelDataiterator.hasNext())
			{
				SupplierUsgovLabelViewBean supplierUsgovLabelViewBean = (SupplierUsgovLabelViewBean) labelDataiterator
						.next();
				supplierUsgovLabelViewBean.setGrossWeight(""
						+ palletBean.getGrossWeight() + "");
			}
		}
		return receiptLabelDataColl;
	}

	public Collection getCaseMSLData(Collection suppPackViewBeanCollection)
			throws BaseException
	{
		DbManager dbManager = new DbManager(getClient());
		MslBoxViewBeanFactory factory = new MslBoxViewBeanFactory(dbManager);
		Collection finalLabelDataCollection = new Vector();
		Iterator iterator = suppPackViewBeanCollection.iterator();
		while (iterator.hasNext())
		{
			SupplierPackingViewBean suppPackViewBean = (SupplierPackingViewBean) iterator
					.next();
			if (suppPackViewBean.getOk() != null
					&& suppPackViewBean.getOk().length() > 0)
			{
				SearchCriteria criteria = new SearchCriteria();
				if (suppPackViewBean.getPackingGroupId() != null)
				{
					criteria.addCriterion("packingGroupId",
							SearchCriterion.EQUALS,
							"" + suppPackViewBean.getPackingGroupId() + "");
				}
				else
				{
					criteria.addCriterion("boxId", SearchCriterion.EQUALS, ""
							+ suppPackViewBean.getBoxId() + "");
				}
				SortCriteria scriteria = new SortCriteria();
				scriteria.addCriterion("boxNumber");
				Collection receiptLabelDataColl = new Vector();
				receiptLabelDataColl = factory.select(criteria, scriteria);
				finalLabelDataCollection.addAll(receiptLabelDataColl);
			}
		}
		return finalLabelDataCollection;
	}

	public Collection getPalletMSLData(Collection suppPackViewBeanCollection)
			throws BaseException
	{
		DbManager dbManager = new DbManager(getClient());
		MslPalletViewBeanFactory factory = new MslPalletViewBeanFactory(
				dbManager);
		Collection finalLabelDataCollection = new Vector();
		Iterator iterator = suppPackViewBeanCollection.iterator();
		while (iterator.hasNext())
		{
			SupplierPackingViewBean suppPackViewBean = (SupplierPackingViewBean) iterator
					.next();
			if (suppPackViewBean.getOk() != null
					&& suppPackViewBean.getOk().length() > 0)
			{
				SearchCriteria criteria = new SearchCriteria();
				if (suppPackViewBean.getPalletId() != null)
				{
					criteria.addCriterion("palletId", SearchCriterion.EQUALS,
							"" + suppPackViewBean.getPalletId() + "");
				}
				if (suppPackViewBean.getCarrierCode() != null)
				{
					criteria.addCriterion("carrierCode",
							SearchCriterion.EQUALS,
							"" + suppPackViewBean.getCarrierCode() + "");
				}
				if (suppPackViewBean.getConsolidationNumber() != null)
				{
					criteria.addCriterion("consolidationNumber",
							SearchCriterion.EQUALS,
							"" + suppPackViewBean.getConsolidationNumber() + "");
				}
				SortCriteria scriteria = new SortCriteria();
				scriteria.addCriterion("palletNumber");
				Collection receiptLabelDataColl = new Vector();
				receiptLabelDataColl = factory.select(criteria, scriteria);
				finalLabelDataCollection.addAll(receiptLabelDataColl);
			}
		}
		return finalLabelDataCollection;
	}

	public Collection getDd250Data(Collection suppPackViewBeanCollection)
			throws BaseException
	{
		DbManager dbManager = new DbManager(getClient());
		UsgovDd250ViewBeanFactory factory = new UsgovDd250ViewBeanFactory(
				dbManager);
		Collection finalLabelDataCollection = new Vector();
		Iterator iterator = suppPackViewBeanCollection.iterator();
		while (iterator.hasNext())
		{
			SupplierPackingViewBean suppPackViewBean = (SupplierPackingViewBean) iterator
					.next();
			if (suppPackViewBean.getOk() != null
					&& suppPackViewBean.getOk().length() > 0)
			{
				SearchCriteria criteria = new SearchCriteria();
				if (suppPackViewBean.getPrNumber() != null)
				{
					criteria.addCriterion("prNumber", SearchCriterion.EQUALS,
							"" + suppPackViewBean.getPrNumber() + "");
				}
				if (suppPackViewBean.getLineItem() != null)
				{
					criteria.addCriterion("lineItem", SearchCriterion.EQUALS,
							"" + suppPackViewBean.getLineItem() + "");
				}
				if (suppPackViewBean.getPackingGroupId() != null)
				{
					criteria.addCriterion("packingGroupId",
							SearchCriterion.EQUALS,
							"" + suppPackViewBean.getPackingGroupId() + "");
				}
				Collection receiptLabelDataColl = new Vector();
				receiptLabelDataColl = factory.select(criteria, null);
				finalLabelDataCollection.addAll(receiptLabelDataColl);
			}
		}
		return finalLabelDataCollection;
	}
}
