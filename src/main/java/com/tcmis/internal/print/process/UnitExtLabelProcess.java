package com.tcmis.internal.print.process;

import java.io.File;
import java.util.Collection;
import java.util.Optional;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.ZplDataProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.PrintHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SqlHandler;
import com.tcmis.common.util.StringHandler;
import com.tcmis.internal.hub.beans.LabelInputBean;
import com.tcmis.internal.hub.beans.PackShipConfirmInputBean;
import com.tcmis.internal.print.beans.UsgovLabelViewBean;
import com.tcmis.internal.print.factory.UsgovLabelViewBeanFactory;

/******************************************************************************
 * Process for receiving qc
 * @version 1.0
 *****************************************************************************/
public class UnitExtLabelProcess extends BaseProcess {
	
	private static final String DLA_GASES_FACILITY = "DLA Gases";
	private static final String PARCEL_MODE = "Parcel";
	private static final String DLA_GASES_CASE_RFID = "2F1203153505734";
	private static final String DLA_GASES_PALLET_RFID = "2F0203153505734";
	private static final String DEFAULT_CASE_RFID = "2F1203739333433";
	private static final String DEFAULT_PALLET_RFID = "2F0203739333433";
	
	Log log = LogFactory.getLog(this.getClass());
	private boolean printBoxLabel = true;

	public UnitExtLabelProcess(String client) {
		super(client);
	}

	public Object[] buildUnitLabels(String labelType,Collection locationLabelPrinterCollection,
			Collection labelDataCollection
	) throws BaseException
	{
		String labelUrl = "";
		String fileAbsolutePath = "";
		ResourceLibrary resource = new ResourceLibrary("label");
		ZplProcess zplProcess = new ZplProcess(this.getClient());

		File dir = new File(resource.getString("label.serverpath"));
		try {
			File file = File.createTempFile("labeljnlp", ".jnlp", dir);
			fileAbsolutePath = zplProcess.buildLabelZpl(labelDataCollection, "",
					locationLabelPrinterCollection,
					file.getAbsolutePath(),labelType);

			labelUrl = resource.getString("label.hosturl") +
			resource.getString("label.urlpath") +
			file.getName();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			labelUrl = "";
		}

		Object[] objs = {fileAbsolutePath,labelUrl};
		return objs;
	}
	
	public void completeInput(LabelInputBean labelInputBean, PersonnelBean user) throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager);
		StringBuilder query = new StringBuilder()
				.append("SELECT facility_id, transportation_mode from packing_group_view where packing_group_id = ")
				.append(SqlHandler.delimitString(labelInputBean.getPackingGroupId()));
		
		StringBuilder palletQuery = new StringBuilder()
				.append("select pallet_id, pallet_closed closed, ")
				.append("(select count(distinct packing_group_id) from box where packing_group_id <> ")
				.append(labelInputBean.getPackingGroupId()).append(" and pallet_id = p.pallet_id) other_pac_ct")
				.append(" from pallet p where pallet_id = ");
		
		@SuppressWarnings("unchecked")
		Collection<PackShipConfirmInputBean> labelData = factory.setBean(new PackShipConfirmInputBean()).selectQuery(query.toString());
		Optional<PackShipConfirmInputBean> labelDataBean = labelData.stream().findFirst();
		
		if (labelDataBean.isPresent()) {
			PackShipConfirmInputBean bean = labelDataBean.get();
			if (StringHandler.isBlankString(labelInputBean.getFacilityId())) {
				labelInputBean.setFacilityId(bean.getFacilityId());
			}
			
			if (DLA_GASES_FACILITY.equals(bean.getFacilityId())) {
				if ( ! PARCEL_MODE.equals(bean.getTransportationMode())) {
					palletQuery.append(SqlHandler.delimitString(DLA_GASES_PALLET_RFID));
				}
				else {
					palletQuery.append(SqlHandler.delimitString(DEFAULT_PALLET_RFID));
				}
			}
			else {
				palletQuery.append(SqlHandler.delimitString(DEFAULT_PALLET_RFID));
			}
		}
		else {
			palletQuery.append(SqlHandler.delimitString(DEFAULT_PALLET_RFID));
		}
		
		@SuppressWarnings("unchecked")
		Collection<LabelInputBean> palletData = factory.setBean(new LabelInputBean()).selectQuery(palletQuery.toString());
		Optional<LabelInputBean> palletDataBean = palletData.stream().findFirst();
		if (palletDataBean.isPresent()) {
			LabelInputBean bean = palletDataBean.get();
			labelInputBean.setPalletId(bean.getPalletId());
			labelInputBean.setOtherPacCt(bean.getOtherPacCt());
			labelInputBean.setClosed(bean.getClosed());
		}
		else {
			labelInputBean.setClosed("N");
		}
		
		StringBuilder facItemQuery = new StringBuilder()
				.append("select coalesce(fi.shipped_as_single, 'N') sas, ")
				.append("coalesce(fi.requires_overpack, 'N') req_over_pk")
				.append(" from customer.request_line_item rli, customer.fac_item fi, freight_advice fa")
				.append(" where fa.packing_group_id = ").append(SqlHandler.delimitString(labelInputBean.getPackingGroupId()))
				.append(" and fa.freight_advice_number = rli.pr_number")
				.append(" and fa.freight_advice_line = rli.line_item")
				.append(" and rli.catalog_company_id = fi.company_id")
				.append(" and rli.catalog_id = fi.facility_id")
				.append(" and rli.fac_part_no = fi.fac_part_no");
				
		@SuppressWarnings("unchecked")
		Collection<LabelInputBean> facItemData = factory.selectQuery(facItemQuery.toString());
		Optional<LabelInputBean> facItemDataBean = facItemData.stream().findFirst();
		if (facItemDataBean.isPresent()) {
			LabelInputBean bean = facItemDataBean.get();
			labelInputBean.setSas(bean.getSas());
			labelInputBean.setReqOverPk(bean.getReqOverPk());
		}
		else {
			labelInputBean.setSas("N");
			labelInputBean.setReqOverPk("N");
		}
		
		if (StringHandler.isBlankString(labelInputBean.getPrinterLocation())) {
			labelInputBean.setPrinterLocation(user.getPrinterLocation());
		}
	}

	public String buildUnitExtLabels(LabelInputBean labelInputBean) throws BaseException,Exception
	{
		String result = "OK";
		ZplDataProcess zplDataProcess = new ZplDataProcess(this.getClient());
		PersonnelBean personnelBean = new PersonnelBean();
		Object[] resultsObj = null;

		labelInputBean.setPaperSize("33");
		//labelInputBean.setPrinterPath(inputBean.getPrinterName());
		labelInputBean.setSkipKitLabels("");

		Collection locationLabelPrinterCollection = new Vector();
		locationLabelPrinterCollection = zplDataProcess.getLocationLabelPrinter(personnelBean,labelInputBean);

		String printerPath = zplDataProcess.getPrinterPath(locationLabelPrinterCollection);
		//inputBean.setPrinterName(printerPath);

		Collection<UsgovLabelViewBean> unitLabelDataColl = getUnitLabelData(labelInputBean,"Unit");
		Collection<UsgovLabelViewBean> extLabelDataColl = getUnitLabelData(labelInputBean,"External");
		
		attachSerialNumbers(unitLabelDataColl, extLabelDataColl);
		
		labelInputBean.setNumBoxLabels(unitLabelDataColl.size());

		/*UINT LABELS*******
		 *Need to print unit labels only if the owner_company_id is <> "USGOV" - we are assuming all VMI will be pre-labeled.
		 * Also if receipt.unit_label_printed = 'Y' we don't need to print a unit label.
		 * */
		if (unitLabelDataColl !=null && unitLabelDataColl.size() >0)
		{
			if (labelInputBean.getSas().equalsIgnoreCase("Y"))
			{
				/*No need unit labels if it is a parcel, meaning there is no pallet.*/
				if (labelInputBean.getPalletId() != null && labelInputBean.getPalletId().length() > 0)
				{
					Object[] resultsObj1 = this.buildUnitLabels("Unit",locationLabelPrinterCollection,unitLabelDataColl);
					if(log.isDebugEnabled()) {
						log.debug(""+resultsObj1[0]+"");
					}
					PrintHandler.print(printerPath, ""+resultsObj1[0]+""); /*Uncomment after test*/
				}
			}
			else
			{
				/*Nedd to print unit labels if they are not pre-printed or it is not VMI*/
				Object[] resultsObj1 = this.buildUnitLabels("Unit",locationLabelPrinterCollection,unitLabelDataColl);
				if(log.isDebugEnabled()) {
					log.debug(""+resultsObj1[0]+"");
				}
				PrintHandler.print(printerPath, ""+resultsObj1[0]+""); /*Uncomment after test*/
			}
		}

		if (extLabelDataColl.isEmpty()) {
			result = "NOEXTDATA";
		}
		else {
			/*EXTERNAL LABELS*******
			 * */
			/*Need N external labels if it is a parcel.*/
			if (labelInputBean.getPalletId() == null || labelInputBean.getPalletId().length() == 0)
			{
				Object[] resultsObj1 = this.buildUnitLabels("External",locationLabelPrinterCollection,extLabelDataColl);
				if(log.isDebugEnabled()) {
					log.debug(""+resultsObj1[0]+"");
				}
				PrintHandler.print(printerPath, ""+resultsObj1[0]+""); /*Uncomment after test*/
			}
			else if ((labelInputBean.getPalletId() != null && labelInputBean.getPalletId().length() > 0) &&
					(labelInputBean.getClosed() != null && labelInputBean.getClosed().equalsIgnoreCase("N")))
			{
				Object[] resultsObj1 = this.buildUnitLabels("External",locationLabelPrinterCollection,extLabelDataColl);
				if(log.isDebugEnabled()) {
					log.debug(""+resultsObj1[0]+"");
				}
				PrintHandler.print(printerPath, ""+resultsObj1[0]+""); /*Uncomment after test*/
			}
			else if ((labelInputBean.getPalletId() != null && labelInputBean.getPalletId().length() > 0) &&
					(labelInputBean.getClosed() != null && labelInputBean.getClosed().equalsIgnoreCase("Y")))
			{
				/*Print these Pallet External labels only when the pallet is closed.
	     Need a pallet External label for each uniform pallet.
	     Need a pallet external label for each order on a heterogeneous pallet.*/
				if (labelInputBean.getOtherPacCt() !=null && labelInputBean.getOtherPacCt().intValue() ==0)
				{
					/*Heterogenous pallet*/
					/*Print External labels since it is overpack*/
					if (labelInputBean.getReqOverPk().equalsIgnoreCase("Y"))
					{
						Object[] resultsObj1 = this.buildUnitLabels("External",locationLabelPrinterCollection,extLabelDataColl);
						if(log.isDebugEnabled()) {
							log.debug(""+resultsObj1[0]+"");
						}
						PrintHandler.print(printerPath, ""+resultsObj1[0]+""); /*Uncomment after test*/
					}
					//Print pallet External labels for the whole pallet
	                if (printBoxLabel) {
	                    Collection<UsgovLabelViewBean> palletExtLabelDataCollection = getPalletExternalLabelData(labelInputBean);
	                    attachSerialNumbers(unitLabelDataColl, palletExtLabelDataCollection);
                    
	                    Object[] resultsObj1 = this.buildUnitLabels("External", locationLabelPrinterCollection, palletExtLabelDataCollection);
	                    if (log.isDebugEnabled()) {
	                        log.debug("" + resultsObj1[0] + "");
	                    }
	                    PrintHandler.print(printerPath, "" + resultsObj1[0] + ""); /*Uncomment after test*/
	                }
				}
				else
				{
					/*Uniform Pallet*/
					/*Print External labels since it is overpack*/
					if (labelInputBean.getReqOverPk().equalsIgnoreCase("Y") ||
							labelInputBean.getSas().equalsIgnoreCase("N"))
					{
						Object[] resultsObj1 = this.buildUnitLabels("External",locationLabelPrinterCollection,extLabelDataColl);
						if(log.isDebugEnabled()) {
							log.debug(""+resultsObj1[0]+"");
						}
						PrintHandler.print(printerPath, ""+resultsObj1[0]+""); /*Uncomment after test*/
					}
					//Print pallet External label for the whole pallet
	                if (printBoxLabel) {
	                    Collection<UsgovLabelViewBean> palletExtLabelDataCollection = getPalletExternalLabelData(labelInputBean);
	                    attachSerialNumbers(unitLabelDataColl, palletExtLabelDataCollection);
				
	                    Object[] resultsObj1 = this.buildUnitLabels("External", locationLabelPrinterCollection, palletExtLabelDataCollection);
	                    if (log.isDebugEnabled()) {
	                        log.debug("" + resultsObj1[0] + "");
	                    }
	                    PrintHandler.print(printerPath, "" + resultsObj1[0] + ""); /*Uncomment after test*/
	                }
				}			
			}
		}
		return result;
	}

	public Collection getUnitLabelData(LabelInputBean labelInputBean,String labelType) throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new UsgovLabelViewBean());

		Collection finalLabelDataCollection = new Vector();
		SearchCriteria criteria = new SearchCriteria();

		StringBuilder countMissingSerialNumber = new StringBuilder(" where missing_serial_number = 'Y'");
		if (!StringHandler.isBlankString(labelInputBean.getPackingGroupId()))
		{
			criteria.addCriterion("packingGroupId", SearchCriterion.EQUALS, "" + labelInputBean.getPackingGroupId() + "");
			countMissingSerialNumber.append(" and packing_group_id = ").append(labelInputBean.getPackingGroupId());
		}

		if (labelType.equalsIgnoreCase("Unit"))
		{
			criteria.addCriterion("unitLabelPrinted", SearchCriterion.EQUALS,"N");
			countMissingSerialNumber.append(" and unit_label_printed = 'N'");
		}

		if (labelInputBean.getPalletId() != null && labelInputBean.getPalletId().length() > 0)
		{
			criteria.addCriterion("palletId", SearchCriterion.EQUALS,labelInputBean.getPalletId());
			countMissingSerialNumber.append(" and pallet_id = '").append(SqlHandler.validString(labelInputBean.getPalletId())).append("'");
		}

		Collection<UsgovLabelViewBean> receiptLabelDataColl = new Vector();
		SortCriteria sort = new SortCriteria();
		if (labelInputBean.getFacilityId() != null && labelInputBean.getFacilityId().equalsIgnoreCase("DLA Gases"))
		{
			String dataView = "USGOV_HAAS_GASES_LABEL_VIEW";
			String countData = factory.selectSingleValue("select count(*) from "+dataView+countMissingSerialNumber.toString());
			if (countData.equals("0")) {
				labelInputBean.setPrintBoxLabel(true);
				receiptLabelDataColl = factory.setBean(new UsgovLabelViewBean()).
				select(criteria, sort, dataView);
			}else
				printBoxLabel = false;
		}
		else
		{
			receiptLabelDataColl =  factory.setBean(new UsgovLabelViewBean()).
			select(criteria,sort, "USGOV_LABEL_VIEW");
		}
		
		for (UsgovLabelViewBean labelData : receiptLabelDataColl) {
	        if(labelData.isSerialNumberTracked())
	        	labelData.setSerialNo1(labelData.getSerialNumber());
        }

		return receiptLabelDataColl;
	}

	public Collection getPalletExternalLabelData(LabelInputBean labelInputBean) throws BaseException {
		DbManager dbManager = new DbManager(getClient());
		UsgovLabelViewBeanFactory factory = new
		UsgovLabelViewBeanFactory(dbManager);

		Collection finalLabelDataCollection = new Vector();
		SearchCriteria criteria = new SearchCriteria();
		if (!StringHandler.isBlankString(labelInputBean.getPackingGroupId()))
			criteria.addCriterion("palletId", SearchCriterion.EQUALS, "" + labelInputBean.getPalletId() + "");

		Collection receiptLabelDataColl = new Vector();
		receiptLabelDataColl = factory.selectPalletExt(criteria, null, labelInputBean);
		return receiptLabelDataColl;
	}
	
	public void attachSerialNumbers(Collection<UsgovLabelViewBean> unitLabelDataColl, Collection<UsgovLabelViewBean> extLabelDataColl) {
		
		for (UsgovLabelViewBean extlabelData : extLabelDataColl) {
			
	        if(extlabelData.isSerialNumberTracked()) {
    			int serialNumberCount = 0;
        		
        		for (UsgovLabelViewBean unitlabelData : unitLabelDataColl) {
	        		if(serialNumberCount > 5)
	        			break;
	        		
	        		switch(serialNumberCount) {
						case 0:
							extlabelData.setSerialNo1(unitlabelData.getSerialNumber());
						break;
						case 1:
							extlabelData.setSerialNo2(unitlabelData.getSerialNumber());
						break;
						case 2:
							extlabelData.setSerialNo3(unitlabelData.getSerialNumber());
						break;
						case 3:
							extlabelData.setSerialNo4(unitlabelData.getSerialNumber());
						break;
						case 4:
							extlabelData.setSerialNo5(unitlabelData.getSerialNumber());
						break;
						case 5:
							extlabelData.setSerialNoComment("Check Serial Number List");
						break;
	        		}
	        		
	        		serialNumberCount++;
	        	}       		
	        }
        }
	}

}

