package com.tcmis.internal.supply.process;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.common.admin.process.HubInventoryGroupProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.SortCriteria;
import com.tcmis.common.util.SortCriterion;
import com.tcmis.internal.hub.beans.ReceiptItemPriorBinViewBean;
import com.tcmis.internal.hub.beans.ReceivingInputBean;
import com.tcmis.internal.hub.beans.ReceivingKitBean;
import com.tcmis.internal.hub.beans.ReceivingViewBean;
import com.tcmis.internal.hub.factory.NoBuyOrderPoReceivingViewBeanFactory;
import com.tcmis.internal.hub.factory.ReceivingViewBeanFactory;
import com.tcmis.internal.supply.beans.BuyPageInputBean;
import com.tcmis.internal.supply.beans.*;
import com.tcmis.internal.supply.factory.*;
import com.tcmis.common.admin.factory.PersonnelNameUserGroupViewBeanFactory;

/******************************************************************************
 * Process for receiving
 * @version 1.0
 *****************************************************************************/
public class BuyPageProcess
 extends BaseProcess {
 Log log = LogFactory.getLog(this.getClass());

 public BuyPageProcess(String client) {
	super(client);
 }

 public Collection getItemBuyerComments() throws
	BaseException {
	DbManager dbManager = new DbManager(getClient());
	ItemBuyerCommentsBeanFactory factory =
	 new ItemBuyerCommentsBeanFactory(dbManager);
	String sortBy = null;
	SearchCriteria criteria = new SearchCriteria();

	SortCriteria sortCriteria = new SortCriteria();
	sortCriteria.addCriterion("transactionDate");

	return factory.select(criteria, sortCriteria);
 }

 public Collection getBuyOrderSort() throws
	BaseException {
	DbManager dbManager = new DbManager(getClient());
	VvBuypageSortBeanFactory factory =
	 new VvBuypageSortBeanFactory(dbManager);
	String sortBy = null;
	SearchCriteria criteria = new SearchCriteria();

	SortCriteria sortCriteria = new SortCriteria();
	sortCriteria.addCriterion("sortDesc");

	return factory.select(criteria, sortCriteria);
 }

 public Collection getBuyOrderStatus() throws
	BaseException {
	DbManager dbManager = new DbManager(getClient());
	VvBuyOrderStatusBeanFactory factory =
	 new VvBuyOrderStatusBeanFactory(dbManager);
	String sortBy = null;
	SearchCriteria criteria = new SearchCriteria();

	SortCriteria sortCriteria = new SortCriteria();
	sortCriteria.addCriterion("displaySort");

	return factory.select(criteria, sortCriteria);
 }

 public Collection getSearchResult(BuyPageInputBean bean,
	boolean hasUpdatePermission,
	Collection hubInventoryGroupOvBeanColl) throws BaseException {
	DbManager dbManager = new DbManager(getClient());
	AssociatePrViewBeanFactory factory = new AssociatePrViewBeanFactory(dbManager);
	String sortBy = null;
	SearchCriteria criteria = new SearchCriteria();

	if (bean.getBranchPlant() != null &&
	 !bean.getInventoryGroup().equalsIgnoreCase("ALL")) {
	 criteria.addCriterion("branchPlant", SearchCriterion.EQUALS,
		bean.getBranchPlant());
}

	HubInventoryGroupProcess hubInventoryGroupProcess = new
	 HubInventoryGroupProcess(this.getClient());
	boolean iscollection = hubInventoryGroupProcess.isCollection(
	 hubInventoryGroupOvBeanColl,
	 bean.getBranchPlant(), bean.getInventoryGroup());

	if (bean.getInventoryGroup() != null &&
	 !bean.getInventoryGroup().equalsIgnoreCase("ALL")) {
	 if (iscollection) {

}
	 else if (bean.getInventoryGroup().length() > 0) {
		criteria.addCriterion("inventoryGroup", SearchCriterion.EQUALS,
		 bean.getInventoryGroup());
}
}

	String[] selectCollectionSelect = bean.getStatusCollectionSelect();
	if (selectCollectionSelect != null && selectCollectionSelect.length > 0) {
	 Collection statusCollection = new Vector();
	 for (int loop = 0; loop < bean.getStatusCollectionSelect().length; loop++) {
		statusCollection.add(selectCollectionSelect[loop]);
}
	 if (statusCollection.size() == 1 &&
		statusCollection.contains(new String("All Statuses"))) {

}
	 else if (statusCollection.size() == 1 &&
		statusCollection.contains(new String("All Except Closed"))) {
		criteria.addCriterion("status", SearchCriterion.NOT_EQUAL, "Closed");
}
	 else if (statusCollection.size() == 1 &&
		statusCollection.contains(new String("All DBuy Statuses"))) {
		criteria.addCriterion("status", SearchCriterion.LIKE, "DBuy");
}
	 else {
		criteria.addCriterion("status", SearchCriterion.EQUALS, statusCollection,
		 SearchCriterion.IGNORE_CASE);
}
}

	if (bean.getShowOnlyOpenBuys() != null &&
	 bean.getShowOnlyOpenBuys().length() > 0) {
	 criteria.addCriterion("status", SearchCriterion.NOT_EQUAL, "Closed");
	 criteria.addValueToCriterion("status", "Confirmed");

	 criteria.addCriterion("frozen", SearchCriterion.NOT_EQUAL, "Y");
	 criteria.addCriterion("radianPo", SearchCriterion.IS,null);
}

	if (bean.getBuyerId() != null && (bean.getBuyerId().floatValue() == -1)) {
	 criteria.addCriterion("buyerId", SearchCriterion.IS, null);
}
	else if (bean.getBuyerId() != null) {
	 criteria.addCriterion("buyerId", SearchCriterion.EQUALS,
		"" + bean.getBuyerId() + "");
}

	if (bean.getSupplyPath() != null &&
	 !bean.getSupplyPath().equalsIgnoreCase("ALL")) {
	 criteria.addCriterion("supplyPath", SearchCriterion.EQUALS,
		bean.getSupplyPath());
}

//add description search if it's not null
	if (bean.getSearchWhat() != null && bean.getSearchText() != null &&
	 bean.getSearchText().trim().length() > 0) {
//check search criterion
	 String criterion = null;
	 if (bean.getSearchType() != null &&
		bean.getSearchType().equalsIgnoreCase("IS")) {
		criterion = SearchCriterion.EQUALS;
}
	 else if (bean.getSearchType() != null &&
		bean.getSearchType().equalsIgnoreCase("CONTAINS")) {
		criterion = SearchCriterion.LIKE;
}

	 String searchWhat = bean.getSearchWhat();
//check what to search by
	 if (bean.getSearchWhat() != null &&
		bean.getSearchWhat().equalsIgnoreCase("MFG_ID")) {
		criteria.addCriterion("mfgId", criterion, bean.getSearchText(),
		 SearchCriterion.IGNORE_CASE);
}
	 else if (bean.getSearchWhat() != null &&
		bean.getSearchWhat().equalsIgnoreCase("RADIAN_PO")) {
		criteria.addCriterion("radianPo", criterion, bean.getSearchText());
}
	 else if (bean.getSearchWhat() != null &&
		bean.getSearchWhat().equalsIgnoreCase("PART_ID")) {
		criteria.addCriterion("partId", criterion, bean.getSearchText(),
		 SearchCriterion.IGNORE_CASE);
}
	 else if (bean.getSearchWhat() != null &&
		bean.getSearchWhat().equalsIgnoreCase("ITEM ID")) {
		criteria.addCriterion("itemId", criterion, bean.getSearchText());
}
	 else if (bean.getSearchWhat() != null &&
		bean.getSearchWhat().equalsIgnoreCase("RAYTHEON_PO")) {
		criteria.addCriterion("raytheonPo", criterion, bean.getSearchText());
}
	 else if (bean.getSearchWhat() != null &&
		bean.getSearchWhat().equalsIgnoreCase("MR_NUMBER")) {
		criteria.addCriterion("mrNumber", criterion, bean.getSearchText());
}
	 else if (bean.getSearchWhat() != null &&
		bean.getSearchWhat().equalsIgnoreCase("ITEM_TYPE")) {
		criteria.addCriterion("itemType", criterion, bean.getSearchText());
}
	 else if (bean.getSearchWhat() != null &&
		bean.getSearchWhat().equalsIgnoreCase("PR_NUMBER")) {
		criteria.addCriterion("prNumber", criterion, bean.getSearchText());
	 }
	}

	SortCriteria sortCriteria = new SortCriteria();
	String sortString = bean.getSort();
	StringTokenizer st = new StringTokenizer(sortString,",");
	while (st.hasMoreTokens()) {
	 sortCriteria.addCriterion(st.nextToken().toString());
	}

	return factory.select(criteria, sortCriteria);
 }

 public Collection getCustomerBuyerNames() throws
	BaseException {
	DbManager dbManager = new DbManager(getClient());
	PersonnelNameUserGroupViewBeanFactory factory =
	 new PersonnelNameUserGroupViewBeanFactory(dbManager);
	String sortBy = null;
	SearchCriteria criteria = new SearchCriteria();

	criteria.addCriterion("userGroupId",
	 SearchCriterion.EQUALS,
	 "customerPurchasing");

	/*criteria.addCriterion("companyId",
	 SearchCriterion.EQUALS,
	 "Radian");*/

	SortCriteria sortCriteria = new SortCriteria();
	sortCriteria.addCriterion("name");

	return factory.selectMemberNames(criteria, sortCriteria);
 }

 /*****************************************************************************
	* Gives a collection of PersonnelNameUserGroupViewBean beans for which a user has
	* customer buying privillages
*
	* @param personnelId  The personnel Id of the person whose privilages are needed.
	****************************************************************************/
 public Collection getMyInventoryGroups(BigDecimal personnelId) throws
	BaseException {
	DbManager dbManager = new DbManager(getClient());
	PersonnelNameUserGroupViewBeanFactory factory =
	 new PersonnelNameUserGroupViewBeanFactory(dbManager);
	String sortBy = null;
	SearchCriteria criteria = new SearchCriteria();

	criteria.addCriterion("userGroupId",
	 SearchCriterion.EQUALS,
	 "customerPurchasing");

	criteria.addCriterion("personnelId",
	 SearchCriterion.EQUALS,
	 "" + personnelId + "");

	SortCriteria sortCriteria = new SortCriteria();
	sortCriteria.addCriterion("facilityId");
	sortCriteria.addCriterion("inventoryGroup");

	return factory.selectUserPrivilages(criteria, sortCriteria);
 }

 public HashMap updateSelected(Collection
	associatePrViewBeanCollection, BigDecimal personnelId) throws
	BaseException {
	DbManager dbManager = new DbManager(getClient());
	String createdPos = "";
	String errorMessage = "";

	Iterator mainIterator = associatePrViewBeanCollection.iterator();
	while (mainIterator.hasNext()) {
	 AssociatePrViewBean currentAssociatePrViewBean = (
		AssociatePrViewBean) mainIterator.next(); ;

	 String currentOk = currentAssociatePrViewBean.getOk();
	 if (currentOk != null && currentOk.length() > 0) {
		try {
		 log.info("Creating PO for PR " + currentAssociatePrViewBean.getPrNumber() +
			"  Line  " + currentAssociatePrViewBean.getOk() + "");
		 Collection result = null;
		 AssociatePrViewBeanFactory associatePrViewBeanFactory = new
			AssociatePrViewBeanFactory(dbManager);
		 //associatePrViewBeanFactory.updateBuyerCompanyId(currentAssociatePrViewBean,personnelId);
		 result = associatePrViewBeanFactory.createCustomerPo(currentAssociatePrViewBean,personnelId);
		 if (result != null) {
			Iterator resultIterator = result.iterator();
			int resultCount = 0;
			while (resultIterator.hasNext()) {
			 if (resultCount == 0) {
				BigDecimal radianPo = (BigDecimal) resultIterator.next(); ;
				if (radianPo != null) {
				 log.info("CreatedPo:  " + radianPo + "");
				 createdPos += "" + radianPo + ",";
				}
			 }
			 resultCount++;
			}
		 }
		}
		catch (Exception ex) {
		 errorMessage += ex.getMessage();
		}
}
}

	if (createdPos.length() > 1) {
	 createdPos = createdPos.substring(0,
		(createdPos.length() - 1));
	 com.tcmis.common.util.StringHandler.replace(createdPos, ",", "%44");
  }

	HashMap result = new HashMap();
	result.put("ERRORMESSAGE", errorMessage);
	result.put("CREATEDPOS", createdPos);
	return result;
 }

 /*public void writeExcelFile(ReceivingInputBean headerBean,
	Collection searchCollection,String filePath) throws
	BaseException, Exception {
	PrintWriter pw = new PrintWriter(new FileOutputStream(filePath));
	pw.println("<html>");
	pw.println("<TABLE BORDER=\"1\">");
	pw.println("<TR>");
	pw.println("<TD>");
	pw.println("<B>Hub:</B>");
	pw.println("</TD>");
	pw.println("<TD>");
	pw.println("" + headerBean.getSourceHubName() + "");
	pw.println("</TD>");
	pw.println("<TD>");
	pw.println("<B>Category:</B>");
	pw.println("</TD>");
	pw.println("<TD>");
	pw.println("" + headerBean.getCategory() + "");
	pw.println("</TD>");
	pw.println("<TD>");
	pw.println("<B>Search:</B>");
	pw.println("</TD>");
	pw.println("<TD>");
	 pw.println("" + headerBean.getSearchWhat() + " " + headerBean.getSearchType() +
	"  " + headerBean.getSearch() + "");
	pw.println("</TD>");
	pw.println("</TR>");
	pw.println("<TR>");
	pw.println("<TD>");
	pw.println("<B>Inven Grp:</B>");
	pw.println("</TD>");
	pw.println("<TD>");
	pw.println("" + headerBean.getInventoryGroup() + "");
	pw.println("</TD>");
	pw.println("<TD>");
	pw.println("<B>Expected Within:</B>");
	pw.println("</TD>");
	pw.println("<TD>" + headerBean.getExpected() + " Days");
	pw.println("</TD>");
	pw.println("<TD>");
	pw.println("<B>&nbsp;</B>");
	pw.println("</TD>");
	pw.println("<TD>&nbsp;");
	pw.println("</TD>");
	pw.println("</TR>");
	pw.println("<TR>");
	pw.println("</TR>");
	pw.println("<TR>");
	pw.println("</TR>");
	pw.println("</TABLE>");
	pw.println("<table border=\"1\">");
	pw.println("<TR>");
	pw.println("<TH width=\"2%\">PO</TH>");
	pw.println("<TH width=\"5%\">PO Line</TH>");
	pw.println("<TH width=\"2%\">Customer PO</TH>");
	pw.println("<TH width=\"5%\">Date Exptd</TH>");
	pw.println("<TH width=\"5%\">Supplier</TH>");
	pw.println("<TH width=\"5%\">Open Amt</TH>");
	pw.println("<TH width=\"5%\">Inventory Group</TH>");
	pw.println("<TH width=\"5%\">Item</TH>");
	pw.println("<TH width=\"5%\">Packaging</TH>");
	pw.println("<TH width=\"7%\">Description</TH>");
	pw.println("</TR>");
//print rows
	Iterator mainIterator = searchCollection.iterator();
	while (mainIterator.hasNext()) {
	pw.println("<TR>");
	ReceivingViewBean
	receivingViewBean = (
	ReceivingViewBean) mainIterator.next(); ;
	int mainRowSpan = receivingViewBean.getRowSpan().
	intValue();
	Collection receivingKitBeanCollection = receivingViewBean.getKitCollection();
	if ("IT".equalsIgnoreCase(receivingViewBean.getTransType())) {
	pw.println("<TD ROWSPAN=" + mainRowSpan + ">TR " +
	receivingViewBean.getTransferRequestId() + "</TD>");
}
	else {
	pw.println("<TD ROWSPAN=" + mainRowSpan + ">" +
	receivingViewBean.getRadianPo() + "</TD>");
}
	pw.println("<TD ROWSPAN=" + mainRowSpan + ">" +
	receivingViewBean.getLineItem() + "</TD>");
	pw.println("<TD ROWSPAN=" + mainRowSpan + ">" +
	receivingViewBean.getCustomerPo() + "</TD>");
	pw.println("<TD ROWSPAN=" + mainRowSpan + ">" +
	com.tcmis.common.util.StringHandler.emptyIfNull(receivingViewBean.getExpected()) +
	"</TD>");
	pw.println("<TD ROWSPAN=" + mainRowSpan + ">" +
	receivingViewBean.getLastSupplier() +
	"</TD>");
	pw.println("<TD ROWSPAN=" + mainRowSpan + ">" +
	receivingViewBean.getQtyOpen() +
	"</TD>");
	pw.println("<TD ROWSPAN=" + mainRowSpan + ">" +
	receivingViewBean.getInventoryGroup() +
	"</TD>");
	pw.println("<TD ROWSPAN=" + mainRowSpan + ">" +
	receivingViewBean.getItemId() +
	"</TD>");
	int kitCount = 0;
	Iterator kitIterator = receivingKitBeanCollection.iterator();
	while (kitIterator.hasNext()) {
	kitCount++;
	ReceivingKitBean receivingKitBean = (
	ReceivingKitBean) kitIterator.next(); ;
	if (kitCount > 1 && receivingKitBeanCollection.size() > 1) {
	pw.println("<TR>");
}
	if (receivingKitBeanCollection.size() > 1 &&
	"N".equalsIgnoreCase(receivingViewBean.getManageKitsAsSingleUnit())) {
	pw.println("<TD>" +
	receivingKitBean.getPackaging() +
	"</TD>");
	pw.println("<TD>" +
	receivingKitBean.getMaterialDesc() +
	"</TD>");
}
	 else if (!"N".equalsIgnoreCase(receivingViewBean.getManageKitsAsSingleUnit()) &&
	kitCount == 1) {
	pw.println("<TD ROWSPAN=" + mainRowSpan + ">" +
	receivingViewBean.getPackaging() + "</TD>");
	if (headerBean.getCategory().equalsIgnoreCase("chemicals")) {
	pw.println("<TD ROWSPAN=" + mainRowSpan + ">" +
	 receivingViewBean.getItemDescription() + "</TD>");
}
	else {
	pw.println("<TD ROWSPAN=" + mainRowSpan + ">" +
	 receivingViewBean.getPartDescription() + "</TD>");
}
}
	if (kitCount > 1 || receivingKitBeanCollection.size() == 1) {
	pw.println("</TR>");
}
}
}
	pw.println("</html>");
	pw.close();
	}*/
}