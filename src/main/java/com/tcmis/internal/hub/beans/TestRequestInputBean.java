package com.tcmis.internal.hub.beans;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;

import com.tcmis.common.framework.HubBaseInputBean;

@SuppressWarnings("serial")
public class TestRequestInputBean extends HubBaseInputBean implements SQLData {
	public static Log log = LogFactory.getLog(TestRequestInputBean.class);

	public static final String REQUEST_STATUS_NEW = "New";
	public static final String REQUEST_STATUS_LAB = "To Lab";
	public static final String REQUEST_STATUS_TESTING = "In Test";
	public static final String REQUEST_STATUS_QC = "QC";
	public static final String REQUEST_STATUS_CLOSED = "Closed";
    public static final String REQUEST_STATUS_COMPLETE = "Complete";

    public static final String SQLTypeName = "CUSTOMER.LAB_TEST_REQUEST_DATA_OBJ";
	
	// Read-only fields from Item, Part, Receipt tables.
	private String partDescription;
	private String itemDescription;
	private BigDecimal itemId;
	private Date dateOfManufacture;
	private Date dateOfReceipt;
	private Date dateOfShipment;
	private String mfgLot;
	private BigDecimal receiptId;
	private BigDecimal poNumber;
	
	// Read-Only fields from LAB_TEST
	private String toLabSignature;
	private BigDecimal toLabPersonnelId;
	private String receivedSignature;
	private BigDecimal samplesReceivedPersonnelId;
	private String testCompletedSignature;
	private BigDecimal testCompletedPersonnelId;
	private String qualificationSignature;
	private BigDecimal qualificationPersonnelId;
	private String closedSignature;
	private BigDecimal closedPersonnelId;
	private Date closedDate;
	private Date createDate;
	private String createSignature;
	private BigDecimal createPersonnelId;
	private String cancelledByName;
	private String reasonForCancellation;
	private BigDecimal cancelledBy;
	private String requestor;
	private String facility;
		
	// Fillable fields from LAB_TEST
	// These fields are the primary key for a lab test record.
	private String companyId;
	private String catalogCompanyId;
	private String catalogId;
	private String catPartNo;
	private String partGroupNo;
	private BigDecimal testRequestId;
	
	// These fields are filled by the user, except requestStatus which is system filled based on user actions.
	private String requestStatus;
	private String sampleSize;
	private String materialQualified;
	private Date dateToLab;
	private Date dateReceivedByLab;
	private Date dateTestComplete;
	private Date dateMaterialQualified;
	private Date dateQualificationExpires;
	private Date dateClosed;
	private Date dateCancelled;
	private Collection<TestResultBean> testResults;
	private Array testResultsArray;
	private String qualityTrackingNumber;
		
	// These fields are used to track search parameters when searching for existing Lab Test Requests.
	private String searchArgument;
	private String searchMode;
	private String searchField;
	private BigDecimal labAge;
	private String facilityId;
	private String hub;
	private String inventoryGroup;	
	private BigDecimal quantityReceived;
	private String lotStatus;
	private String opsEntityId;
	private String inventoryGroupName;
	private BigDecimal radianPo;
	private BigDecimal poLine;
	private Date expirationDate;
	private String specList;
    private String frequency;
    private Collection<LabTestReceiptBean> labTestReceiptColl;
	private Array labTestReceiptArray;



    public TestRequestInputBean(){
		super();
	}
	
	public TestRequestInputBean(ActionForm inputForm, Locale locale){
		super(inputForm, locale);
	}
		
	public String getPartDescription(){
		return partDescription;		
	}
	public String getItemDescription(){
		return itemDescription;		
	}
	public BigDecimal getItemId(){
		return itemId;		
	}
	public Date getDateOfManufacture(){
		return dateOfManufacture;		
	}
	public Date getDateOfReceipt(){
		return dateOfReceipt;		
	}
	public Date getDateOfShipment(){
		return dateOfShipment;		
	}
	public String getMfgLot(){
		return mfgLot;		
	}
	public BigDecimal getReceiptId(){
		return receiptId;		
	}
	public String getToLabSignature(){
		return toLabSignature;		
	}
	public BigDecimal getToLabPersonnelId() {
		return toLabPersonnelId;
	}
	public String getReceivedSignature(){
		return receivedSignature;		
	}
	public BigDecimal getSamplesReceivedPersonnelId() {
		return samplesReceivedPersonnelId;
	}
	public String getTestCompletedSignature(){
		return testCompletedSignature;
	}
	public BigDecimal getTestCompletedPersonnelId() {
		return testCompletedPersonnelId;
	}
	public String getQualificationSignature(){
		return qualificationSignature;
	}
	public BigDecimal getQualificationPersonnelId() {
		return qualificationPersonnelId;
	}
	public String getClosedSignature(){
		return closedSignature;
	}
	public BigDecimal getClosedPersonnelId() {
		return closedPersonnelId;
	}
	public Date getClosedDate(){
		return closedDate;
	}
	public String getCompanyId(){
		return companyId;
	}
	public String getCatalogCompanyId(){
		return catalogCompanyId;
	}
	public String getCatalogId(){
		return catalogId;		
	}
	public String getCatPartNo(){
		return catPartNo;		
	}
	public String getPartGroupNo(){
		return partGroupNo;
	}
	public BigDecimal getTestRequestId(){
		return testRequestId;
	}
	public String getRequestStatus(){
		return requestStatus;
	}
	public String getSampleSize(){
		return sampleSize;
	}
	public String getMaterialQualified(){
		return materialQualified;
	}
	public Date getDateToLab(){
		return dateToLab;
	}
	public Date getDateReceivedByLab(){
		return dateReceivedByLab;
	}
	public Date getDateTestComplete(){
		return dateTestComplete;
	}
	public Date getQualificationDate(){
		return dateMaterialQualified;
	}
	public Date getDateQualificationExpires(){
		return dateQualificationExpires;
	}
	public Date getDateClosed(){
		return dateClosed;
	}
	public Array getTestResultsArray(){
		return testResultsArray;
	}
	public Collection<TestResultBean> getTestResults(){
		return testResults;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public String getCreateSignature() {
		return createSignature;
	}
	public BigDecimal getCreatePersonnelId() {
		return createPersonnelId;
	}
	public BigDecimal getPoNumber() {
		return poNumber;
	}
	
	public String getHub()
	{
		return hub;
	}
	public String getInventoryGroup()
	{
		return inventoryGroup;
	}
	
	public  BigDecimal getQuantityReceived()
	{
		return quantityReceived;
	}
	public String getLotStatus()
	{
		return lotStatus;
	}
	public String getOpsEntityId()
	{
		return opsEntityId;
	}
	public String getInventoryGroupName()
	{
		return inventoryGroupName;
	}
	public BigDecimal getRadianPo()
	{
		return radianPo;
	}
	public BigDecimal getPoLine()
	{
		return poLine;
	}
	
	public Date getExpirationDate()
	{
		return expirationDate;
	}
	
	public String getQualityTrackingNumber(){
		return qualityTrackingNumber;
	}
		
	public void setExpirationDate(Date expirationDate)
	{
		this.expirationDate = expirationDate;
	}
	
	public void setHub(String hub)
	{
		this.hub = hub;
	}
	public void setInventoryGroup(String inventoryGroup)
	{
		this.inventoryGroup = inventoryGroup;
	}
	
	public void setQuantityReceived(BigDecimal quantityReceived)
	{
		this.quantityReceived = quantityReceived;
	}
	public void setLotStatus(String lotStatus)
	{
		this.lotStatus = lotStatus;
	}
	public void setOpsEntityId(String opsEntityId)
	{
		this.opsEntityId = opsEntityId;
	}
	public void setInventoryGroupName(String inventoryGroupName)
	{
		this.inventoryGroupName = inventoryGroupName;
	}
	public void setRadianPo(BigDecimal radianPo)
	{
		this.radianPo = radianPo;
	}
	public void setPoLine(BigDecimal poLine)
	{
		this.poLine = poLine;
	}

	public void setPoNumber(BigDecimal poNumber) {
		this.poNumber = poNumber;
	}
	
	
	
	public Date getDateMaterialQualified() {
		return dateMaterialQualified;
	}

	public void setDateMaterialQualified(Date dateMaterialQualified) {
		this.dateMaterialQualified = dateMaterialQualified;
	}

	public void setDateClosed(Date dateClosed) {
		this.dateClosed = dateClosed;
	}

	public void setPartDescription(String partDescription){
		this.partDescription = partDescription;
	}
	public void setItemDescription(String itemDescription){
		this.itemDescription = itemDescription;	
	}
	public void setItemId(BigDecimal itemId){
		this.itemId = itemId;
	}
	public void setDateOfManufacture(Date dateOfManufacture){
		this.dateOfManufacture = dateOfManufacture;
	}
	public void setDateOfReceipt(Date dateOfReceipt){
		this.dateOfReceipt = dateOfReceipt;
	}
	public void setDateOfShipment(Date dateOfShipment){
		this.dateOfShipment = dateOfShipment;
	}
	public void setMfgLot(String mfgLot){
		this.mfgLot = mfgLot;
	}	
	public void setReceiptId(BigDecimal receiptId){
		this.receiptId = receiptId;
	}
	public boolean hasReceiptId(){
		return getReceiptId() != null;
	}
	public void setToLabSignature(String toLabSignature){
		this.toLabSignature = toLabSignature;
	}
	public void setToLabPersonnelId(BigDecimal toLabPersonnelId) {
		this.toLabPersonnelId = toLabPersonnelId;
	}
	public void setReceivedSignature(String receivedSignature){
		this.receivedSignature = receivedSignature;
	}
	public void setSamplesReceivedPersonnelId(BigDecimal samplesReceivedPersonnelId) {
		this.samplesReceivedPersonnelId = samplesReceivedPersonnelId;
	}
	public void setTestCompletedSignature(String testCompletedSignature){
		this.testCompletedSignature = testCompletedSignature;
	}
	public void setTestCompletedPersonnelId(BigDecimal testCompletedPersonnelId) {
		this.testCompletedPersonnelId = testCompletedPersonnelId;
	}
	public void setQualificationSignature(String qualificationSignature){
		this.qualificationSignature = qualificationSignature;
	}
	public void setQualificationPersonnelId(BigDecimal qualificationPersonnelId) {
		this.qualificationPersonnelId = qualificationPersonnelId;
	}
	public void setClosedSignature(String closedSignature){
		this.closedSignature = closedSignature;
	}
	public void setClosedPersonnelId(BigDecimal closedPersonnelId) {
		this.closedPersonnelId = closedPersonnelId;
	}
	public void setCompanyId(String companyId){
		this.companyId = companyId;		
	}
	public void setCatalogCompanyId(String catalogCompanyId){
		this.catalogCompanyId = catalogCompanyId;
	}
	public void setCatalogId(String catalogId){
		this.catalogId = catalogId;
	}
	public void setCatPartNo(String catPartNo){
		this.catPartNo = catPartNo;
	}
	public void setPartGroupNo(String partGroupNo){
		this.partGroupNo = partGroupNo;
	}
	public void setTestRequestId(BigDecimal testRequestId){
		this.testRequestId = testRequestId;
	}
	public boolean hasTestRequestId(){
		return getTestRequestId() != null;
	}
	public void setRequestStatus(String requestStatus){
		this.requestStatus = requestStatus;
	}
	public void setSampleSize(String sampleSize){
		this.sampleSize = sampleSize;
	}
	public void setMaterialQualified(String materialQualified){
		this.materialQualified = materialQualified;
	}
	public void setDateToLab(Date dateToLab){
		this.dateToLab = dateToLab;
	}
	public void setDateReceivedByLab(Date dateReceivedByLab){
		this.dateReceivedByLab = dateReceivedByLab;
	}
	public void setDateTestComplete(Date dateTestComplete){
		this.dateTestComplete = dateTestComplete;
	}
	public void setQualificationDate(Date qualificationDate){
		this.dateMaterialQualified = qualificationDate;		
	}
	public void setDateQualificationExpires(Date expirationDate){
		this.dateQualificationExpires = expirationDate;
	}
	public void setClosedDate(Date closedDate){
		this.closedDate = closedDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public void setCreateSignature(String createSignature) {
		this.createSignature = createSignature;
	}
	public void setCreatePersonnelId(BigDecimal createPersonnelId) {
		this.createPersonnelId = createPersonnelId;
	}
	public void setTestResults(Collection<TestResultBean> testResults){
		this.testResults = testResults;
	}
	@SuppressWarnings("unchecked")
	public void setTestResultsArray(Array testResults){
		List list = null;
		try {
			list = Arrays.asList( (Object[]) testResults.getArray());
		}
		catch (SQLException sqle) {
			System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
		}
		this.setTestResults(list);
	}
	
	// The following methods are used by the Test Request Search page hub/testrequestsearchmain.jsp.
	@Override
	public void setHiddenFormFields() {
		// Fields opsEntityId, hub, and inventoryGroup are added in the HubBaseInputBean constructor.
		hiddenFormFieldList.add("searchArgument");
		hiddenFormFieldList.add("searchMode");
		hiddenFormFieldList.add("searchField");
		hiddenFormFieldList.add("labAge");
		hiddenFormFieldList.add("facilityId");
	}

	public String getSearchArgument() {
		return searchArgument;
	}
	
	public void setSearchArgument(String searchArgument) {
		this.searchArgument = searchArgument;
	}

	public String getSearchMode() {
		return searchMode;
	}
	
	public void setSearchMode(String searchMode) {
		this.searchMode = searchMode;
	}

	public String getSearchField() {
		return searchField;
	}
	
	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public void setLabAge(BigDecimal labAge) {
		this.labAge = labAge;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getFacilityId() {
		return facilityId;
	}
	public BigDecimal getLabAge() {
		return labAge;
	}
	
	public String getSpecList() {
		return specList;
	}

	public void setSpecList(String specList) {
		this.specList = specList;
	}
	
	
	public String getCancelledByName() {
		return cancelledByName;
	}

	public void setCancelledByName(String cancelledByName) {
		this.cancelledByName = cancelledByName;
	}

	public String getReasonForCancellation() {
		return reasonForCancellation;
	}

	public void setReasonForCancellation(String reasonForCancellation) {
		this.reasonForCancellation = reasonForCancellation;
	}

	public Date getDateCancelled() {
		return dateCancelled;
	}

	public void setDateCancelled(Date dateCancelled) {
		this.dateCancelled = dateCancelled;
	}
	
	

	public BigDecimal getCancelledBy() {
		return cancelledBy;
	}

	public void setCancelledBy(BigDecimal cancelledBy) {
		this.cancelledBy = cancelledBy;
	}
	
	public void setQualityTrackingNumber(String qualityTrackingNumber) {
		this.qualityTrackingNumber = qualityTrackingNumber;
	}

	public String getSQLTypeName() throws SQLException {
		return SQLTypeName;
	}

	public void readSQL(SQLInput stream, String typeName) throws SQLException {
		try{
			setTestRequestId(stream.readBigDecimal());
			setCatPartNo(stream.readString());
			setRequestStatus(stream.readString());
			setPartDescription(stream.readString());
			setItemId(stream.readBigDecimal());
			setItemDescription(stream.readString());
			setReceiptId(stream.readBigDecimal());
			setMfgLot(stream.readString());
			setDateOfManufacture(stream.readDate());
			setDateOfShipment(stream.readDate());
			setDateOfReceipt(stream.readDate());
			setSampleSize(stream.readString());
			setDateToLab(stream.readDate());
			setToLabSignature(stream.readString());
			setDateReceivedByLab(stream.readDate());
			setReceivedSignature(stream.readString());		
			setDateTestComplete(stream.readDate());
			setTestCompletedSignature(stream.readString());
			setMaterialQualified(stream.readString());
			setQualificationDate(stream.readDate());
			setExpirationDate(stream.readDate());
			setQualificationSignature(stream.readString());
			setClosedDate(stream.readDate());
			setClosedSignature(stream.readString());
			setCompanyId(stream.readString());
			setCreateDate(stream.readDate());
			setCreateSignature(stream.readString());
			setHub(stream.readString());
			setInventoryGroup(stream.readString());
			setInventoryGroupName(stream.readString());
			setRadianPo(stream.readBigDecimal());
			setPoLine(stream.readBigDecimal());
			setQuantityReceived(stream.readBigDecimal());
			setLotStatus(stream.readString());
			setOpsEntityId(stream.readString());
			setSpecList(stream.readString());
			setCatalogId(stream.readString());
			setDateCancelled(stream.readDate());
			setCancelledByName(stream.readString());
			setReasonForCancellation(stream.readString());
			setQualityTrackingNumber(stream.readString());
			setFacility(stream.readString());
            setFrequency(stream.readString());
            setTestResultsArray(stream.readArray());
            setLabTestReceiptArray(stream.readArray());
        }
		catch (SQLException e) {
			throw e;
		}
		catch (Exception e) {
			log.error("readSQL method failed",e);
			throw new SQLException(getClass().getName() + ".readSQL method failed");
		}
	}

	public void writeSQL(SQLOutput stream) throws SQLException {
		try{
			stream.writeBigDecimal(getTestRequestId());
			stream.writeString(getCatPartNo());
			stream.writeString(getRequestStatus());
			stream.writeString(getPartDescription());
			stream.writeBigDecimal(getItemId());
			stream.writeString(getItemDescription());
			stream.writeBigDecimal(getPoNumber());
			stream.writeBigDecimal(getReceiptId());
			stream.writeString(getMfgLot());
			stream.writeDate(getDateOfManufacture() == null ? null : new java.sql.Date(getDateOfManufacture().getTime()));
			stream.writeDate(getDateOfShipment() == null ? null : new java.sql.Date(getDateOfShipment().getTime()));
			stream.writeDate(getDateOfReceipt() == null ? null : new java.sql.Date(getDateOfReceipt().getTime()));
			stream.writeString(getSampleSize());
			stream.writeDate(getDateToLab() == null ? null : new java.sql.Date(getDateToLab().getTime()));
			stream.writeString(getToLabSignature());
			stream.writeDate(getDateReceivedByLab() == null ? null : new java.sql.Date(getDateReceivedByLab().getTime()));
			stream.writeString(getReceivedSignature());		
			stream.writeDate(getDateTestComplete() == null ? null : new java.sql.Date(getDateTestComplete().getTime()));
			stream.writeString(getTestCompletedSignature());
			stream.writeString(getMaterialQualified());
			stream.writeDate(getQualificationDate() == null ? null : new java.sql.Date(getQualificationDate().getTime()));
			stream.writeDate(getExpirationDate() == null ? null : new java.sql.Date(getExpirationDate().getTime()));
			stream.writeString(getQualificationSignature());
			stream.writeDate(getClosedDate() == null ? null : new java.sql.Date(getClosedDate().getTime()));
			stream.writeString(getClosedSignature());
			stream.writeString(getCompanyId());
			stream.writeDate(getCreateDate() == null ? null : new java.sql.Date(getCreateDate().getTime()));
			stream.writeString(getCreateSignature());
			stream.writeString(getHub());
			stream.writeString(getInventoryGroup());
			stream.writeString(getInventoryGroupName());
			stream.writeBigDecimal(getRadianPo());
			stream.writeBigDecimal(getPoLine());
			stream.writeBigDecimal(getQuantityReceived());
			stream.writeString(getLotStatus());
			stream.writeString(getOpsEntityId());
			stream.writeString(getSpecList());
			stream.writeString(getCatalogId());
			stream.writeDate(getDateCancelled() == null ? null : new java.sql.Date(getDateCancelled().getTime()));
			stream.writeString(getCancelledByName());
			stream.writeString(getReasonForCancellation());
			stream.writeString(getQualityTrackingNumber());
            stream.writeString(getFrequency());
            stream.writeArray(getTestResultsArray());
            stream.writeArray(getLabTestReceiptArray());
        }
		catch (SQLException e) {
			throw e;
		}
		catch (Exception e) {
			log.error("readSQL method failed",e);
			throw new SQLException(getClass().getName() + ".writeSQL method failed");
		}
	}

	public String getRequestor() {
		return requestor;
	}

	public void setRequestor(String requestor) {
		this.requestor = requestor;
	}

	public String getFacility() {
		return facility;
	}

	public void setFacility(String facility) {
		this.facility = facility;
	}

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public Collection<LabTestReceiptBean> getLabTestReceiptColl() {
        return labTestReceiptColl;
    }

    public void setLabTestReceiptColl(Collection<LabTestReceiptBean> labTestReceiptColl) {
        this.labTestReceiptColl = labTestReceiptColl;
    }

    public Array getLabTestReceiptArray() {
        return labTestReceiptArray;
    }

    public void setLabTestReceiptArray(Array labTestReceiptArray) {
        List list = null;
		try {
			list = Arrays.asList( (Object[]) labTestReceiptArray.getArray());
		}
		catch (SQLException sqle) {
			System.out.println("ERROR SETTING CHILDREN:" + sqle.getMessage());
		}
		this.setLabTestReceiptColl(list);
    }
}
