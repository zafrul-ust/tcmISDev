package com.tcmis.client.report.process;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Locale;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tcmis.client.report.beans.FX2731ADataBean;
import com.tcmis.client.report.beans.FX2731DataBean;
import com.tcmis.client.report.beans.InventoryPlanningInputBean;
import com.tcmis.client.report.beans.MaterialCompositionDetailBean;
import com.tcmis.client.report.beans.OwnerOperatorBean;
import com.tcmis.client.report.factory.FacilityPlanningReportOvBeanFactory;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.exceptions.NoDataException;
import com.tcmis.common.framework.BaseExcelReportProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.ExcelHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;


public class InventoryPlanningReportProcess extends BaseExcelReportProcess {
	Log log = LogFactory.getLog(this.getClass());
	
	InventoryPlanningInputBean bean;
	PersonnelBean personnelBean;
	//Writer writer;
	OutputStream os;
	OutputStreamWriter writer;
	ResourceLibrary library;
	Locale locale;


	public InventoryPlanningReportProcess(String client) {
		super(client);
	}

	public InventoryPlanningReportProcess(String client,Locale locale) {
		super(client);
		this.locale = locale;
		library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", locale);
	}
	public Collection getSearchDropDown(String companyId) throws BaseException {

		DbManager dbManager = new DbManager(getClient(),getLocale());
		FacilityPlanningReportOvBeanFactory factory = new
		FacilityPlanningReportOvBeanFactory(dbManager);
		
		SearchCriteria criteria = new SearchCriteria();
		
		if (null != companyId) {
			criteria.addCriterion("companyId", SearchCriterion.EQUALS, companyId);
		}
        criteria.addCriterion("facilityId", SearchCriterion.EQUALS,"Palmdale");
        return factory.selectObject(criteria);
	}
	
	public void runReport(InventoryPlanningInputBean bean, PersonnelBean personnelBean, OutputStream os) throws BaseException, Exception {
		this.bean = bean;
		this.personnelBean = personnelBean;
		this.os = os;
		writer = new OutputStreamWriter(os);
		if ("BATCH".equalsIgnoreCase(bean.getReportGenerationType())) {
			BatchReport bp = new BatchReport();
			new Thread(bp).start();
			writer.write("<html>");
			writer.write(library.getString("label.batchreportmessage") + " " + personnelBean.getEmail());
			writer.write("</html>");
			writer.close();
		} else {
			createReport();
		}
	}
	
	public void createReport() throws BaseException, Exception {
		try
		{
			/*if("ascii".equalsIgnoreCase(bean.getFileType()))
			{
				writeToFileOES2730(bean, personnelBean.getPersonnelIdBigDecimal());
				writeToFileOES2731A(bean, personnelBean.getPersonnelIdBigDecimal());
			
			}
			else
			{	*/
			    ExcelHandler pw = getExcelReport(bean, personnelBean.getPersonnelIdBigDecimal());
				if ("INTERACTIVE".equalsIgnoreCase(bean.getReportGenerationType())) {
					pw.write(os);
				} 
				else {
					
						File tempFile = File.createTempFile("InventoryPlanning", ".xls");
						FileOutputStream fos = new FileOutputStream(tempFile);
						pw.write(fos);
						//now email the file
						MailProcess.sendEmail(personnelBean.getEmail(), "", MailProcess.DEFAULT_FROM_EMAIL, library.getString("label.inventoryplanning"), library.getString("label.hereisyourreport"), bean.getReportName() + ".xls", tempFile.getAbsolutePath());
					
					
				}
			//}
	} catch (Exception e) {
		System.out.println("Error:" + e.getMessage());
		e.printStackTrace(System.out);
		
		throw new BaseException(e);
	}
	log.debug("done with Business Plan report");
		
	}
	
	/*public void writeToFileOES2730(InventoryPlanningInputBean bean,BigDecimal personnelId) throws BaseException{
		Collection<MaterialCompositionDetailBean> compositiondata =  getCompositionDetail(bean, personnelBean.getPersonnelIdBigDecimal());
		Collection<OwnerOperatorAsciiValueBean> ownerAsciidata = getOwnerOperatorAscii();
		Iterator iter = ownerAsciidata.iterator();
		try {
			//File dir = new File("c:\\temp");
		   // File file = File.createTempFile("OES2730", ".dat",dir);
			
			File tempFile = File.createTempFile("OES2730", ".dat");			
		    FileWriter fw = new FileWriter(tempFile);
		  
		  for(OwnerOperatorAsciiValueBean data : ownerAsciidata) {
	            fw.write(data.getAsciiValue().toString().trim()+System.getProperty( "line.separator" ));   
	            
	        }
		    fw.close();
		    MailProcess.sendEmail(personnelBean.getEmail(), "", MailProcess.DEFAULT_FROM_EMAIL, library.getString("label.inventoryplanning"), library.getString("label.hereisyourreport"),"OES2730.dat", tempFile.getAbsolutePath());
		 }
		catch(Exception e) {
	        log.fatal("Error creating OES2730 file:" + e.getMessage(), e);
	        throw new BaseException(e);
	    }
    }*/
	
	
	/*public void writeToFileOES2731A(InventoryPlanningInputBean bean,BigDecimal personnelId) throws BaseException{
		Collection<MaterialCompositionDetailBean> compositiondata =  getCompositionDetail(bean, personnelBean.getPersonnelIdBigDecimal());
		try {
			File dir = new File("c:\\temp");
		    File tempFile = File.createTempFile("OES2731A", ".dat",dir);
			
			//File tempFile = File.createTempFile("OES2731A", ".dat");			
		    FileWriter fw = new FileWriter(tempFile);
		  
		  for(MaterialCompositionDetailBean data : compositiondata) {
	            fw.write(data.getAreaName().toString().trim()+System.getProperty( "line.separator" ));
	            fw.write(data.getAreaName().toString().trim()+System.getProperty( "line.separator" ));
	            fw.write(data.getAreaName().toString().trim()+System.getProperty( "line.separator" ));
	            fw.write(data.getAreaName().toString().trim()+System.getProperty( "line.separator" ));
	            fw.write(data.getAreaName().toString().trim()+System.getProperty( "line.separator" ));
	            fw.write(data.getAreaName().toString().trim()+System.getProperty( "line.separator" ));
	            
	        }
		    fw.close();
		   // MailProcess.sendEmail(personnelBean.getEmail(), "", MailProcess.DEFAULT_FROM_EMAIL, library.getString("label.inventoryplanning"), library.getString("label.hereisyourreport"),"OES2730.dat", tempFile.getAbsolutePath());
		 }
		catch(Exception e) {
	        log.fatal("Error creating OES2730 file:" + e.getMessage(), e);
	        throw new BaseException(e);
	    }
    }*/
	
	
	
	public Collection getCompositionDetail(InventoryPlanningInputBean bean,BigDecimal personnelId) throws BaseException {
		
		DbManager dbManager = new DbManager(getClient(),getLocale());
		 GenericSqlFactory factory = new GenericSqlFactory(dbManager,new MaterialCompositionDetailBean());
		Collection<MaterialCompositionDetailBean> c = null;
		String areaList = "";
		String trialRun="";
		for(int i=0;i< bean.getAreaListArray().length;i++)
		{
			if(bean.getAreaListArray()[i] != "")
			areaList = areaList + bean.getAreaListArray()[i] + "|";
		}
		
		if(bean.getTrialRun().equalsIgnoreCase("Y"))
		{
			trialRun ="Y";
		}
		else{
			trialRun ="N";
		}
				
		StringBuilder query= new StringBuilder("select * from table (pkg_planning_report.FX_MATERIAL_COMPOSITION(");

		query.append( "'" ).append(bean.getCompanyId()).append("'" )
		.append(",'").append(bean.getFacilityId()).append("'" )
		.append(",'").append(bean.getCounty()).append("'" )
		.append(",'").append(areaList).append("'" )
		.append(",'" ).append(bean.getReportType()).append("'" )
		.append(",'" ).append(bean.getReportDate()).append("'" )
	    .append(",'").append(trialRun).append("'" )
		.append(",").append(personnelId)
        .append(",'").append("|'")
				.append( "))");

		c = factory.selectQuery(query.toString());
		
		return c;
 }
	
  public Collection getFX2731Detail(InventoryPlanningInputBean bean,BigDecimal personnelId) throws BaseException {
		
		DbManager dbManager = new DbManager(getClient(),getLocale());
		 GenericSqlFactory factory = new GenericSqlFactory(dbManager,new FX2731DataBean());
		Collection<FX2731DataBean> c = null;
		String areaList = "";
		String trialRun="";
		for(int i=0;i< bean.getAreaListArray().length;i++)
		{
			if(bean.getAreaListArray()[i].length() > 0 && bean.getAreaListArray()[i] != "")
			areaList = areaList + bean.getAreaListArray()[i] + "|";
		}
		
		if(bean.getTrialRun().equalsIgnoreCase("Y"))
		{
			trialRun ="Y";
		}
		else{
			trialRun ="N";
		}
			
				
		//StringBuilder query= new StringBuilder("select * from table (pkg_planning_report.FX_2731(");
        StringBuilder query= new StringBuilder("select * from table (pkg_planning_report.FX_CERS(");

        query.append( "'" ).append(bean.getCompanyId()).append("'" )
		.append(",'").append(bean.getFacilityId()).append("'" )
		.append(",'").append(bean.getCounty()).append("'" )
		.append(",'").append(areaList).append("'" )
		.append(",'" ).append(bean.getReportType()).append("'" )
		.append(",'" ).append(bean.getReportDate()).append("'" )
        .append(",'").append(trialRun).append("'" )
        .append(",").append(personnelId)
        .append(",'").append("|'))");

		c = factory.selectQuery(query.toString());
		
		return c;
 }
	
	public Collection getOwnerOperator() throws BaseException {
			
			DbManager dbManager = new DbManager(getClient(),getLocale());
			 GenericSqlFactory factory = new GenericSqlFactory(dbManager,new OwnerOperatorBean());
			Collection<OwnerOperatorBean> c = null;
					
			//StringBuilder query= new StringBuilder("select * from table (pkg_planning_report.FX_OWNER_OPERATOR)");
            StringBuilder query= new StringBuilder("select * from table (pkg_planning_report.FX_CERS_FACILITY)");


            c = factory.selectQuery(query.toString());
			
			return c;
	 }
	
	/*public Collection getOwnerOperatorAscii() throws BaseException {
		
		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager, new OwnerOperatorAsciiValueBean());
		Collection<OwnerOperatorAsciiValueBean> c = null;
				
		StringBuilder query= new StringBuilder("select * from table (pkg_planning_report.FX_OWNER_OPERATOR_ASCII)");

		
		c = factory.selectQuery(query.toString());
		
		return c;
 }*/
	
	public Collection getFX2731ADetail() throws BaseException {
			
			DbManager dbManager = new DbManager(getClient(),getLocale());
			GenericSqlFactory factory = new GenericSqlFactory(dbManager, new FX2731ADataBean());
			Collection<FX2731ADataBean> c = null;
					
			StringBuilder query= new StringBuilder("select * from table (pkg_planning_report.FX_2731a)");
	
			
			c = factory.selectQuery(query.toString());
			
			return c;
	 }
	
	public ExcelHandler  getExcelReport(InventoryPlanningInputBean inputbean, BigDecimal personnelId) throws
	NoDataException, BaseException, Exception {
	 
	 //Vector<MaterialCompositionDetailBean> compositiondata = (Vector<MaterialCompositionDetailBean>) getCompositionDetail(inputbean, personnelId);
	 Vector<FX2731DataBean> fx2731data = (Vector<FX2731DataBean>) getFX2731Detail(inputbean, personnelId);
	 Vector<OwnerOperatorBean> ownerdata = (Vector<OwnerOperatorBean>) getOwnerOperator();
	 //Vector<FX2731ADataBean> fx2731Adata = (Vector<FX2731ADataBean>) getFX2731ADetail();
	 SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy"); 
	 ResourceLibrary library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
	 ExcelHandler pw = new ExcelHandler(library);

	 pw.addTable();
	 
	 /*Pass the header keys for the Excel.*/
	 String[] header = {
			   "FacilityID","CERSID","BusinessName","BeginningDate","EndingDate",
			   "Phone","Fax","CountyID","DunAndBradstreet","SICCode",
			   "NAICSCode","MailingAddress","MailingAddressCity","MailingAddressState","MailingAddressZipCode",
			   "OperatorName","OperatorPhone","OwnerName","OwnerPhone","OwnerMailAddress",
			   "OwnerCity","OwnerState","OwnerZipCode","OwnerCountry","EContactName",
			   "EContactPhone","EContactMailingAddress","EContactEmailAddress","EContactCity","EContactState",
			   "EContactZipCode","EContactCountry","PECName","PECTitle","PECBusinessPhone",
			   "PEC24HrPhone","PECPager","SECName","SECTitle","SECBusinessPhone",
			   "SEC24HrPhone","SECPager","ALCollectedInformation","IdentificationSignedDate","DocumentPreparerName",
			   "IdentificationSignerName","IdentificationSignerTitle","BillingContactName","BillingContactPhone","BillingContactEmail",
               "BillingAddress","BillingAddressCity","BillingAddressState","BillingAddressZipCode","BillingAddressCountry",
			   "AssessorParcelNumber","NumberOfEmployees","PropertyOwnerName","PropertyOwnerPhone","PropertyOwnerMailingAddress",
               "PropertyOwnerCity","PropertyOwnerState","PropertyOwnerZipCode","PropertyOwnerCountry"
             };
/*This array defines the type of the excel column.
 0 means default depending on the data type. */
	 int[] types = {
			 0,0,0,0,0,
			 0,0,0,0,0,
			 0,0,0,0,0,
			 0,0,0,0,0,
			 0,0,0,0,0,
			 0,0,0,0,0,
			 0,0,0,0,0,
			 0,0,0,0,0,
			 0,0,0,0,0,
			 0,0,0,0,0,
             0,0,0,0,0,
             0,0,0,0,0,
             0,0,0,0};
	 /*This array defines the default width of the column when the Excel file opens.
 0 means the width will be default depending on the data type.*/
	 int[] widths = {
			 0,0,0,0,0,
			 0,0,0,0,0,
			 0,0,0,0,0,
			 0,0,0,0,0,
			 0,0,0,0,0,
			 0,0,0,0,0,
			 0,0,0,0,0,
			 0,0,0,0,0,
			 0,0,0,0,0,
			 0,0,0,0,0,
             0,0,0,0,0,
             0,0,0,0,0,
             0,0,0,0};
	 	 /*This array defines the horizontal alignment of the data in a cell.
 0 means excel defaults the horizontal alignemnt by the data type.*/
	 int[] horizAligns = null;
	// pw.setColumnDigit(4,4); // final price 4 digit
	// pw.setColumnDigit(7,4); // unitOfSaleQuantityPerEach 4 digit
	 pw.applyColumnHeader(null, types, widths, horizAligns);

//	 now write data
//	 DateFormat longDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
//	 DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
//	 DateFormat shortDateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
	 pw.addRow();
		
	 for( String hstr: header)
		 pw.addCellBold(hstr);
	
	 for(OwnerOperatorBean bean: ownerdata) {
		 pw.addRow();

         pw.addCell(bean.getFacilityid());
         pw.addCell(bean.getCersid());
         pw.addCell(bean.getBusinessname());
         pw.addCell(bean.getBeginningdate());
         pw.addCell(bean.getEndingdate());
         pw.addCell(bean.getPhone());
         pw.addCell(bean.getFax());
         pw.addCell(bean.getCountyid());
         pw.addCell(bean.getDunandbradstreet());
         pw.addCell(bean.getSiccode());
         pw.addCell(bean.getNaicscode());
         pw.addCell(bean.getMailingaddress());
         pw.addCell(bean.getMailingaddresscity());
         pw.addCell(bean.getMailingaddressstate());
         pw.addCell(bean.getMailingaddresszipcode());
         pw.addCell(bean.getOperatorname());
         pw.addCell(bean.getOperatorphone());
         pw.addCell(bean.getOwnername());
         pw.addCell(bean.getOwnerphone());
         pw.addCell(bean.getOwnermailaddress());
         pw.addCell(bean.getOwnercity());
         pw.addCell(bean.getOwnerstate());
         pw.addCell(bean.getOwnerzipcode());
         pw.addCell(bean.getOwnercountry());
         pw.addCell(bean.getEcontactname());
         pw.addCell(bean.getEcontactphone());
         pw.addCell(bean.getEcontactmailingaddress());
         pw.addCell(bean.getEcontactemailaddress());
         pw.addCell(bean.getEcontactcity());
         pw.addCell(bean.getEcontactstate());
         pw.addCell(bean.getEcontactzipcode());
         pw.addCell(bean.getEcontactcountry());
         pw.addCell(bean.getPecname());
         pw.addCell(bean.getPectitle());
         pw.addCell(bean.getPecbusinessphone());
         pw.addCell(bean.getPec24hrphone());
         pw.addCell(bean.getPecpager());
         pw.addCell(bean.getSecname());
         pw.addCell(bean.getSectitle());
         pw.addCell(bean.getSecbusinessphone());
         pw.addCell(bean.getSec24hrphone());
         pw.addCell(bean.getSecpager());
         pw.addCell(bean.getAlcollectedinformation());
         pw.addCell(bean.getIdentificationsigned());
         pw.addCell(bean.getDocumentpreparername());
         pw.addCell(bean.getIdentificationsignername());
         pw.addCell(bean.getIdentificationsignertitle());
         pw.addCell(bean.getBillingcontactname());
         pw.addCell(bean.getBillingcontactphone());
         pw.addCell(bean.getBillingcontactemail());
         pw.addCell(bean.getBillingaddress());
         pw.addCell(bean.getBillingaddresscity());
         pw.addCell(bean.getBillingaddressstate());
         pw.addCell(bean.getBillingaddresszipcode());
         pw.addCell(bean.getBillingaddresscountry());
         pw.addCell(bean.getAssessorparcelnumber());
         pw.addCell(bean.getNumberofemployees());
         pw.addCell(bean.getPropertyownername());
         pw.addCell(bean.getPropertownerphone());
         pw.addCell(bean.getPropertownermailingaddress());
         pw.addCell(bean.getPropertownercity());
         pw.addCell(bean.getPropertownerstate());
         pw.addCell(bean.getPropertownerzipcode());
         pw.addCell(bean.getPropertownercountry());
     } //end of first tab

     //adding second tab
     pw.addTable();
	 /*Pass the header  for the Excel.*/
	 String[] header1 = {
			   "Add/Delete","CERSID","ChemicalLocation","WorkAreaGroup","CLConfidential","MapNumber","GridNumber",
               "ChemicalName","TradeSecret","ComponentMsdsNumber","CommonName","EHS","CASNumber",
               "PFCodeHazardClass","SFCodeHazardClass","TFCodeHazardClass","FFCodeHazardClass","FifthFireCodeHazardClass",
               "SixthFireCodeHazardClass","SeventhFireCodeHazardClass","EighthFireCodeHazardClass","HMType","RadioActive",
               "Curies","PhysicalState","LargestContainer","FHCFire","FHCReactive",
               "FHCPressureRelease","FHCAcuteHealth","FHCChronicHealth","AverageDailyAmount","MaximumDailyAmount",
               "AnnualWasteAmount","StateWasteCode","Units","DaysOnSite","SCAboveGroundTank",
               "SCUnderGroundTank","SCTankInsideBuilding","SCSteelDrum","SCPlasticNonMetallicDrum","SCCan",
               "SCCarboy","SCSilo","SCFiberDrum","SCBag","SCBox",
               "SCCylinder","SCGlassBottle","SCPlasticBottle","SCToteBin","SCTankTruckTankWagon",
               "SCTankCarRailCar","SCOther","OtherStorageContainer","StoragePressure","StorageTemperature",
               "HC1PercentByWeight","HC1Name","HC1EHS","HC1CAS","HC2PercentByWeight",
               "HC2Name","HC2EHS","HC2CAS","HC3PercentByWeight","HC3Name",
               "HC3EHS","HC3CAS","HC4PercentByWeight","HC4Name","HC4EHS",
               "HC4CAS","HC5PercentByWeight","HC5Name","HC5EHS","HC5CAS",
               "ChemicalDescriptionComment","AdditionalMixtureComponents","CCLID","USEPASRSNumber","DOTHazClassID"
            };
/*This array defines the type of the excel column.
 0 means default depending on the data type. */
	 int[] types1 = {
			 0,0,0,0,0,0,0,
			 0,0,0,0,0,0,
			 0,0,0,0,0,
			 0,0,0,0,0,
			 0,0,0,0,0,
			 0,0,0,0,0,
			 0,0,0,0,0,
			 0,0,0,0,0,
			 0,0,0,0,0,
			 0,0,0,0,0,
			 0,0,0,0,0,
			 0,0,0,0,0,
			 0,0,0,0,0,
			 0,0,0,0,0,
			 0,0,0,0,0,
			 0,0,0,0,0
			 };
	 /*This array defines the default width of the column when the Excel file opens.
 0 means the width will be default depending on the data type.*/
	 int[] widths1 = {
			 0,0,0,0,0,0,0,
			 0,0,0,0,0,0,
			 0,0,0,0,0,
			 0,0,0,0,0,
			 0,0,0,0,0,
			 0,0,0,0,0,
			 0,0,0,0,0,
			 0,0,0,0,0,
			 0,0,0,0,0,
			 0,0,0,0,0,
			 0,0,0,0,0,
			 0,0,0,0,0,
			 0,0,0,0,0,
			 0,0,0,0,0,
			 0,0,0,0,0,
			 0,0,0,0,0
			 };
	 	 /*This array defines the horizontal alignment of the data in a cell.
 0 means excel defaults the horizontal alignemnt by the data type.*/
	 int[] horizAligns1 = null;
	// pw.setColumnDigit(4,4); // final price 4 digit
	// pw.setColumnDigit(7,4); // unitOfSaleQuantityPerEach 4 digit
	 pw.applyColumnHeader(null, types1, widths1, horizAligns1);


	 pw.addRow();
		
	 for( String hstr1: header1)
		 pw.addCellBold(hstr1);
	 
	 for(FX2731DataBean bean: fx2731data) {
		 pw.addRow();

         pw.addCell(bean.getAddDeleteRecord());
         pw.addCell(bean.getCersid());
         pw.addCell(bean.getChemicallocation());
         pw.addCell(bean.getWorkAreaGroup());
         pw.addCell(bean.getClconfidential());
         pw.addCell(bean.getMapnumber());
         pw.addCell(bean.getGridnumber());
         pw.addCell(bean.getChemicalname());
         pw.addCell(bean.getTradesecret());
         pw.addCell(bean.getComponentMsdsNumber());
         pw.addCell(bean.getCommonname());
         pw.addCell(bean.getEhs());
         pw.addCell(bean.getCasnumber());
         pw.addCell(bean.getPfcodehazardclass());
         pw.addCell(bean.getSfcodehazardclass());
         pw.addCell(bean.getTfcodehazardclass());
         pw.addCell(bean.getFfcodehazardclass());
         pw.addCell(bean.getFifthfirecodehazardclass());
         pw.addCell(bean.getSixthfirecodehazardclass());
         pw.addCell(bean.getSeventhfirecodehazardclass());
         pw.addCell(bean.getEighthfirecodehazardclass());
         pw.addCell(bean.getHmtype());
         pw.addCell(bean.getRadioactive());
         pw.addCell(bean.getCuries());
         pw.addCell(bean.getPhysicalstate());
         pw.addCell(bean.getLargestcontainer());
         pw.addCell(bean.getFhcfire());
         pw.addCell(bean.getFhcreactive());
         pw.addCell(bean.getFhcpressurerelease());
         pw.addCell(bean.getFhcacutehealth());
         pw.addCell(bean.getFhcchronichealth());
         pw.addCell(bean.getAveragedailyamount());
         pw.addCell(bean.getMaximumdailyamount());
         pw.addCell(bean.getAnnualwasteamount());
         pw.addCell(bean.getStatewastecode());
         pw.addCell(bean.getUnits());
         pw.addCell(bean.getDaysonsite());
         pw.addCell(bean.getScabovegroundtank());
         pw.addCell(bean.getScundergroundtank());
         pw.addCell(bean.getSctankinsidebuilding());
         pw.addCell(bean.getScsteeldrum());
         pw.addCell(bean.getScplasticnonmetallicdrum());
         pw.addCell(bean.getSccan());
         pw.addCell(bean.getSccarboy());
         pw.addCell(bean.getScsilo());
         pw.addCell(bean.getScfiberdrum());
         pw.addCell(bean.getScbag());
         pw.addCell(bean.getScbox());
         pw.addCell(bean.getSccylinder());
         pw.addCell(bean.getScglassbottle());
         pw.addCell(bean.getScplasticbottle());
         pw.addCell(bean.getSctotebin());
         pw.addCell(bean.getSctanktrucktankwagon());
         pw.addCell(bean.getSctankcarrailcar());
         pw.addCell(bean.getScother());
         pw.addCell(bean.getOtherstoragecontainer());
         pw.addCell(bean.getStoragepressure());
         pw.addCell(bean.getStoragetemperature());
         pw.addCell(bean.getHc1percentbyweight());
         pw.addCell(bean.getHc1name());
         pw.addCell(bean.getHc1ehs());
         pw.addCell(bean.getHc1cas());
         pw.addCell(bean.getHc2percentbyweight());
         pw.addCell(bean.getHc2name());
         pw.addCell(bean.getHc2ehs());
         pw.addCell(bean.getHc2cas());
         pw.addCell(bean.getHc3percentbyweight());
         pw.addCell(bean.getHc3name());
         pw.addCell(bean.getHc3ehs());
         pw.addCell(bean.getHc3cas());
         pw.addCell(bean.getHc4percentbyweight());
         pw.addCell(bean.getHc4name());
         pw.addCell(bean.getHc4ehs());
         pw.addCell(bean.getHc4cas());
         pw.addCell(bean.getHc5percentbyweight());
         pw.addCell(bean.getHc5name());
         pw.addCell(bean.getHc5ehs());
         pw.addCell(bean.getHc5cas());
         pw.addCell(bean.getChemicaldescriptioncomment());
         pw.addCell(bean.getAdditionalmixturecomponents());
         pw.addCell(bean.getCclid());
         pw.addCell(bean.getUsepasrsnumber());
         pw.addCell(bean.getDothazclass());
     }

        /*
         pw.addTable();
		 
		 String[] header2 = {
				   "Page","MSDS Number","Facility ID","Area ID",
				   "Area Name","Customer Cabinet ID","Application","Compass Point",
				   "Loc Column","Loc Section","Location Detail","Floor Name",
				   "Interior","Room Name","Map ID","Map Grid","Building Name",
				   "Building Description","Material ID","Trade Name","Material Desc","Last MSDS Revision Date",
				   "CAS Number","Row Order","Percent","Chemical Name","EHS",
				   "RS","Carcinogen","Trade Secret"
		        };
		 
		 int[] types2 = {
				 0,0,0,0,0,0,0,0,0,0,
				 0,0,0,0,0,0,0,0,0,0,
				 0,0,0,0,0,0,0,0,0,0
		      };
		 
		 int[] widths2 = {
				 8,8,10,0,0,12,12,10,0,
				 0,10,0,0,0,0,0,0,
				 12,0,0,0,0,0,12,0,12,0,
				 0,12,0
		 };
		 
		 int[] horizAligns2 = null;
		 
		 pw.applyColumnHeader(null, types2, widths2, horizAligns2);


		 pw.addRow();
			
		 for( String hstr2: header2)
			 pw.addCellBold(hstr2);
		 
		 for(FX2731ADataBean abean: fx2731Adata) {
			 pw.addRow();
			
			
			 pw.addCell(abean.getPage());
			 pw.addCell(abean.getMsdsNumber());
			 pw.addCell(abean.getFacilityId());
			 pw.addCell(abean.getAreaId());
			 pw.addCell(abean.getAreaName());
			 pw.addCell(abean.getCustomerCabinetId());
			 pw.addCell(abean.getApplication());
			 pw.addCell(abean.getCompassPoint());
			 pw.addCell(abean.getLocColumn());
			 pw.addCell(abean.getLocSection());
			 pw.addCell(abean.getLocationDetail());
			 pw.addCell(abean.getFloorName());
			 pw.addCell(abean.getInterior());
			 pw.addCell(abean.getRoomName());
			 pw.addCell(abean.getMapId());
			 pw.addCell(abean.getMapGrid());
			 pw.addCell(abean.getBuildingName());
			 pw.addCell(abean.getBuildingDescription());
			 pw.addCell(abean.getMaterialId());
			 pw.addCell(abean.getTradeName());
			 pw.addCell(abean.getMaterialDesc());
			 if(abean.getLastMsdsRevisionDate() != null)
			 {
			   pw.addCell(sdf.format(abean.getLastMsdsRevisionDate()));
			 }
			 else
			 {
				 pw.addCell(abean.getLastMsdsRevisionDate());	 
			 }
			 pw.addCell(abean.getCasNumber());
			 pw.addCell(abean.getRowOrder());
			 pw.addCell(abean.getPercent());
			 pw.addCell(abean.getChemicalName());
			 pw.addCell(abean.getEhs());
			 pw.addCell(abean.getRs());
			 pw.addCell(abean.getCarcinogen());
			 pw.addCell(abean.getTradeSecret());
		 }
		 */
	 
	 return pw;
   }
	
	class BatchReport extends Thread {
		public BatchReport() {
			super("BusinessPlanning");
		}

		@Override
		public void run() {
			try {
				createReport();
			} catch (Exception e) {
				log.fatal("error in thread:" + e.getMessage(), e);
			}
		}
	}
} //end of class