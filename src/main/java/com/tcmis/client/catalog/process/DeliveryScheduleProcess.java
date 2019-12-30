package com.tcmis.client.catalog.process;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;
import java.text.ParseException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.tcmis.client.catalog.factory.ExistingCatalogViewBeanFactory;
import com.tcmis.common.admin.process.MailProcess;
import com.tcmis.common.db.DbManager;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.BaseProcess;
import com.tcmis.common.framework.GenericSqlFactory;
import com.tcmis.common.util.BeanSorter;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.ResourceLibrary;
import com.tcmis.common.util.SearchCriteria;
import com.tcmis.common.util.SearchCriterion;
import com.tcmis.common.util.StringHandler;
import com.tcmis.client.catalog.beans.DeliveryScheduleBean;
import com.tcmis.client.catalog.beans.DeliveryScheduleChangeBean;
import com.tcmis.client.catalog.beans.DeliverySummaryBean;
import com.tcmis.client.catalog.factory.DeliveryScheduleBeanFactory;
import com.tcmis.client.catalog.factory.DeliveryScheduleChangeBeanFactory;
import com.tcmis.client.order.factory.RequestLineItemBeanFactory;
import com.tcmis.client.catalog.beans.DeliveryScheduleCalendarInputBean;
import com.tcmis.client.catalog.factory.ScheduleDeliveryHeaderViewBeanFactory;
import com.tcmis.client.catalog.beans.ScheduleDeliveryHeaderViewBean;
import com.tcmis.client.catalog.beans.DeliverySummaryBean;
import com.tcmis.client.catalog.factory.DeliverySummaryBeanFactory;
import com.tcmis.client.catalog.beans.BuyerMaterialRequestViewBean;
import com.tcmis.client.catalog.factory.BuyerMaterialRequestViewBeanFactory;
import com.tcmis.client.catalog.factory.CatalogPartInventoryBeanFactory;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.PermissionBean;
import com.tcmis.common.admin.beans.BlackOutDateViewBean;
import com.tcmis.client.order.beans.PurchaseRequestBean;
import com.tcmis.client.order.factory.PurchaseRequestBeanFactory;
/******************************************************************************
 * Process for receiving
 * @version 1.0
 *****************************************************************************/
public class DeliveryScheduleProcess
 extends BaseProcess {
 Log log = LogFactory.getLog(this.getClass());
 private ResourceLibrary library = null;
 
 private String URL;

 public DeliveryScheduleProcess(String client) {
	super(client);
	library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
 }
 
 public DeliveryScheduleProcess(String client, String url) {
	super(client);
	library = new ResourceLibrary("com.tcmis.common.resources.CommonResources", this.getLocaleObject());
	this.URL = url;	
 }

 public String getUserViewType(PersonnelBean personnelBean,DeliverySummaryBean myInputBean)  throws BaseException {
   String userViewType = "viewer";
   PermissionBean permissionBean = personnelBean.getPermissionBean();
   if (personnelBean.getPersonnelId() == myInputBean.getRequestor().intValue()) {
     userViewType = "user";
   }
   if (permissionBean.hasFacilityPermission("EditMRQty",myInputBean.getFacilityId(),myInputBean.getCompanyId())) {
     userViewType = "editMRQty";
   }
   if (permissionBean.hasFacilityPermission("ApproverEditMRQty",myInputBean.getFacilityId(),myInputBean.getCompanyId())) {
     userViewType = "approverEditMRQty";
   }
   if (userViewType.equalsIgnoreCase("viewer")) {
     if (permissionBean.isAlternateRequestor(myInputBean.getCompanyId(),myInputBean.getFacilityId(),myInputBean.getRequestor())) {
       userViewType = "alternate";
     }
   }
   //make sure the right person is able to edit MR at the right status
   if (userViewType.equalsIgnoreCase("user") || userViewType.equalsIgnoreCase("alternate")) {
     if (!myInputBean.getRequestLineStatus().equalsIgnoreCase("Draft") && !myInputBean.getRequestLineStatus().equalsIgnoreCase("In Progress") &&
         !myInputBean.getRequestLineStatus().equalsIgnoreCase("Partial Del.")) {
       userViewType = "viewer";
     }
   }
   if (userViewType.equalsIgnoreCase("approverEditMRQty")) {
     if (!myInputBean.getRequestLineStatus().equalsIgnoreCase("Pending Finance Approval") &&
         !myInputBean.getRequestLineStatus().equalsIgnoreCase("Pending Use Approval")) {
       userViewType = "viewer";
     }
   }
   return userViewType;
 } //end of method

 public String getFacilityIdForMr(DeliverySummaryBean inputBean) throws BaseException {
   String facilityId = "";
   DbManager dbManager = new DbManager(getClient());
   PurchaseRequestBeanFactory factory = new PurchaseRequestBeanFactory(dbManager);
   SearchCriteria criteria = new SearchCriteria();
   criteria.addCriterion("companyId", SearchCriterion.EQUALS, inputBean.getCompanyId());
   criteria.addCriterion("prNumber", SearchCriterion.EQUALS, inputBean.getPrNumber().toString());
   Collection col =  factory.select(criteria);
   Iterator iterator = col.iterator();
   while(iterator.hasNext()) {
     PurchaseRequestBean bean = (PurchaseRequestBean)iterator.next();
     facilityId = bean.getFacilityId();
   }
   return facilityId;
 } //end of method


 public BigDecimal getMedianMrLeadTime(DeliverySummaryBean inputBean) throws BaseException {
   if ("viewer".equalsIgnoreCase(inputBean.getUserViewType())) {
    return new BigDecimal(0);
   }else {
     DbManager dbManager = new DbManager(getClient());
     CatalogPartInventoryBeanFactory factory = new CatalogPartInventoryBeanFactory(dbManager);
     return factory.selectMedianMrLeadTime(inputBean.getCompanyId(), inputBean.getPrNumber(), inputBean.getLineItem());
   }
 }

public Collection getDaysTillLeadTime(	BigDecimal medianMrLeadTime) throws BaseException {
		DbManager dbManager = new DbManager(getClient(),getLocale());
		GenericSqlFactory factory = new GenericSqlFactory(dbManager,new BlackOutDateViewBean());
		String query = "select DAILY_DATE CALENDAR_DATE from all_daily_date where DAILY_DATE > sysdate  and DAILY_DATE < sysdate +"+medianMrLeadTime.intValue()+" order by DAILY_DATE asc";
		return factory.selectQuery(query);
	}


 public Collection getObjectViewFromData(Collection summaryColl) throws BaseException,Exception {
   Vector resultColl = new Vector(summaryColl.size());
   //sort data to make sure that all the requested dates are together
   DeliverySummaryBean myBean = new DeliverySummaryBean();
   BeanSorter beanSorter = new BeanSorter(myBean);
   Collection sortedColl = beanSorter.sort(summaryColl,"getRequestedDateToDeliver");
   Iterator iterator = sortedColl.iterator();
   String lastRequestedDate = "";
   while(iterator.hasNext()) {
     DeliverySummaryBean bean = (DeliverySummaryBean)iterator.next();
     if (lastRequestedDate.equals(bean.getRequestedDateToDeliver().toString())) {
       DeliverySummaryBean newBean = (DeliverySummaryBean)resultColl.lastElement();
       //since status is not in order and we always want to show
       //'Pick in Progress' for line with both 'Pick in Progress' and something else
       if ("Pick in Progress".equals(bean.getStatus())) {
         newBean.setStatus(bean.getStatus());
       }
       //don't add 'Not Issued' row to child data
       if (!"Not Issued".equals(bean.getStatus())) {
         Collection innerColl = newBean.getDeliveredInfoColl();
         DeliverySummaryBean innerBean = new DeliverySummaryBean();
         innerBean.setRefDate(bean.getRefDate());
         innerBean.setRefQuantity(bean.getRefQuantity());
         innerColl.add(innerBean);
         newBean.setOpenQty(new BigDecimal(newBean.getOpenQty().intValue() - bean.getRefQuantity().intValue()));
       }
     }else {
       DeliverySummaryBean newBean = new DeliverySummaryBean();
       newBean.setCompanyId(bean.getCompanyId());
       newBean.setPrNumber(bean.getPrNumber());
       newBean.setLineItem(bean.getLineItem());
       newBean.setRequestedDateToDeliver(bean.getRequestedDateToDeliver());
       newBean.setRequestedQty(bean.getRequestedQty());
       newBean.setRevisedQuantity(bean.getRevisedQuantity());
       newBean.setStatus(bean.getStatus());
       newBean.setRowType(bean.getRowType());
       newBean.setRowDeleted(bean.getRowDeleted());
       newBean.setRequestor(bean.getRequestor());
       newBean.setRequestLineStatus(bean.getRequestLineStatus());
       newBean.setOpenQty(bean.getRequestedQty());
       //child data
       Collection innerColl = new Vector(11);
       //don't add 'Not Issued' row to child data
       if (!"Not Issued".equals(bean.getStatus())) {
         DeliverySummaryBean innerBean = new DeliverySummaryBean();
         innerBean.setRefDate(bean.getRefDate());
         innerBean.setRefQuantity(bean.getRefQuantity());
         innerColl.add(innerBean);
         newBean.setOpenQty(new BigDecimal(newBean.getOpenQty().intValue() - bean.getRefQuantity().intValue()));
       }
       newBean.setDeliveredInfoColl(innerColl);
       resultColl.addElement(newBean);
     }
     lastRequestedDate = bean.getRequestedDateToDeliver().toString();
   }

   return resultColl;
 } //end of method

 public Collection getDeliverySummaryBeanBean(DeliverySummaryBean inputBean) throws BaseException,Exception {
   DbManager dbManager = new DbManager(getClient());

   DeliverySummaryBeanFactory factory = new DeliverySummaryBeanFactory(dbManager);
   return (getObjectViewFromData(factory.select(inputBean)));
 }

 public ScheduleDeliveryHeaderViewBean getScheduleDeliveryHeaderViewBean(DeliverySummaryBean inputBean) throws BaseException {
   DbManager dbManager = new DbManager(getClient());
   Collection scheduleDeliveryHeaderViewBeanColl = new Vector();
   ScheduleDeliveryHeaderViewBean scheduleDeliveryHeaderViewBean = new ScheduleDeliveryHeaderViewBean();

   ScheduleDeliveryHeaderViewBeanFactory factory = new ScheduleDeliveryHeaderViewBeanFactory(dbManager);

   SearchCriteria criteria = new SearchCriteria();
   criteria.addCriterion("companyId", SearchCriterion.EQUALS, inputBean.getCompanyId());
   criteria.addCriterion("prNumber", SearchCriterion.EQUALS, inputBean.getPrNumber().toString());
   criteria.addCriterion("lineItem", SearchCriterion.EQUALS, inputBean.getLineItem());

   scheduleDeliveryHeaderViewBeanColl = factory.select(criteria);

   Iterator i11 = scheduleDeliveryHeaderViewBeanColl.iterator();
   while (i11.hasNext()) {
     scheduleDeliveryHeaderViewBean = (ScheduleDeliveryHeaderViewBean) i11.next(); 
   }
	//if coming from MR screen with a qty then use it
	//because users might change qty without updating data to database
	if (inputBean.getRequestedQty() != null) {
		scheduleDeliveryHeaderViewBean.setQuantity(inputBean.getRequestedQty());
	}

	return scheduleDeliveryHeaderViewBean;
 }


 public void updateDataInSession(Collection deliverySummaryBeanCollection, DeliveryScheduleCalendarInputBean inputBean) throws BaseException {
   try {
     String[] tmpCalendarDate = inputBean.getLastDisplayCalendarDate().split("/");
     if (tmpCalendarDate.length == 3) {
       //loop to 31 days of the month
       for (int i = 1; i < 32; i++) {
         int currentQty = 4;
         String currentDate = "";
         if (i < 10)  {
           currentDate = tmpCalendarDate[0] + "/0" + i + "/" + tmpCalendarDate[2];
         }else {
           currentDate = tmpCalendarDate[0] + "/" + i + "/" + tmpCalendarDate[2];
         }
         if (i == 1) {
           if (!isDataInSession(deliverySummaryBeanCollection,currentDate,inputBean.getCalendarInput1())) {
             if (inputBean.getCalendarInput1() != null) {
               createNewBeanForSession(deliverySummaryBeanCollection, currentDate, inputBean.getCalendarInput1());
             }
           }
         }
         if (i == 2) {
           if (!isDataInSession(deliverySummaryBeanCollection,currentDate,inputBean.getCalendarInput2())) {
             if (inputBean.getCalendarInput2() != null) {
               createNewBeanForSession(deliverySummaryBeanCollection, currentDate, inputBean.getCalendarInput2());
             }
           }
         }
         if (i == 3) {
           if (!isDataInSession(deliverySummaryBeanCollection,currentDate,inputBean.getCalendarInput3())) {
             if (inputBean.getCalendarInput3() != null) {
               createNewBeanForSession(deliverySummaryBeanCollection, currentDate, inputBean.getCalendarInput3());
             }
           }
         }
         if (i == 4) {
           if (!isDataInSession(deliverySummaryBeanCollection,currentDate,inputBean.getCalendarInput4())) {
             if (inputBean.getCalendarInput4() != null) {
               createNewBeanForSession(deliverySummaryBeanCollection, currentDate, inputBean.getCalendarInput4());
             }
           }
         }
         if (i == 5) {
           if (!isDataInSession(deliverySummaryBeanCollection,currentDate,inputBean.getCalendarInput5())) {
             if (inputBean.getCalendarInput5() != null) {
               createNewBeanForSession(deliverySummaryBeanCollection, currentDate, inputBean.getCalendarInput5());
             }
           }
         }
         if (i == 6) {
           if (!isDataInSession(deliverySummaryBeanCollection,currentDate,inputBean.getCalendarInput6())) {
             if (inputBean.getCalendarInput6() != null) {
               createNewBeanForSession(deliverySummaryBeanCollection, currentDate, inputBean.getCalendarInput6());
             }
           }
         }
         if (i == 7) {
           if (!isDataInSession(deliverySummaryBeanCollection,currentDate,inputBean.getCalendarInput7())) {
             if (inputBean.getCalendarInput7() != null) {
               createNewBeanForSession(deliverySummaryBeanCollection, currentDate, inputBean.getCalendarInput7());
             }
           }
         }
         if (i == 8) {
           if (!isDataInSession(deliverySummaryBeanCollection,currentDate,inputBean.getCalendarInput8())) {
             if (inputBean.getCalendarInput8() != null) {
               createNewBeanForSession(deliverySummaryBeanCollection, currentDate, inputBean.getCalendarInput8());
             }
           }
         }
         if (i == 9) {
           if (!isDataInSession(deliverySummaryBeanCollection,currentDate,inputBean.getCalendarInput9())) {
             if (inputBean.getCalendarInput9() != null) {
               createNewBeanForSession(deliverySummaryBeanCollection, currentDate, inputBean.getCalendarInput9());
             }
           }
         }
         if (i == 10) {
           if (!isDataInSession(deliverySummaryBeanCollection,currentDate,inputBean.getCalendarInput10())) {
             if (inputBean.getCalendarInput10() != null) {
               createNewBeanForSession(deliverySummaryBeanCollection, currentDate, inputBean.getCalendarInput10());
             }
           }
         }
         if (i == 11) {
           if (!isDataInSession(deliverySummaryBeanCollection,currentDate,inputBean.getCalendarInput11())) {
             if (inputBean.getCalendarInput11() != null) {
               createNewBeanForSession(deliverySummaryBeanCollection, currentDate, inputBean.getCalendarInput11());
             }
           }
         }
         if (i == 12) {
           if (!isDataInSession(deliverySummaryBeanCollection,currentDate,inputBean.getCalendarInput12())) {
             if (inputBean.getCalendarInput12() != null) {
               createNewBeanForSession(deliverySummaryBeanCollection, currentDate, inputBean.getCalendarInput12());
             }
           }
         }
         if (i == 13) {
           if (!isDataInSession(deliverySummaryBeanCollection,currentDate,inputBean.getCalendarInput13())) {
             if (inputBean.getCalendarInput13() != null) {
               createNewBeanForSession(deliverySummaryBeanCollection, currentDate, inputBean.getCalendarInput13());
             }
           }
         }
         if (i == 14) {
           if (!isDataInSession(deliverySummaryBeanCollection,currentDate,inputBean.getCalendarInput14())) {
             if (inputBean.getCalendarInput14() != null) {
               createNewBeanForSession(deliverySummaryBeanCollection, currentDate, inputBean.getCalendarInput14());
             }
           }
         }
         if (i == 15) {
           if (!isDataInSession(deliverySummaryBeanCollection,currentDate,inputBean.getCalendarInput15())) {
             if (inputBean.getCalendarInput15() != null) {
               createNewBeanForSession(deliverySummaryBeanCollection, currentDate, inputBean.getCalendarInput15());
             }
           }
         }
         if (i == 16) {
           if (!isDataInSession(deliverySummaryBeanCollection,currentDate,inputBean.getCalendarInput16())) {
             if (inputBean.getCalendarInput16() != null) {
               createNewBeanForSession(deliverySummaryBeanCollection, currentDate, inputBean.getCalendarInput16());
             }
           }
         }
         if (i == 17) {
           if (!isDataInSession(deliverySummaryBeanCollection,currentDate,inputBean.getCalendarInput17())) {
             if (inputBean.getCalendarInput17() != null) {
               createNewBeanForSession(deliverySummaryBeanCollection, currentDate, inputBean.getCalendarInput17());
             }
           }
         }
         if (i == 18) {
           if (!isDataInSession(deliverySummaryBeanCollection,currentDate,inputBean.getCalendarInput18())) {
             if (inputBean.getCalendarInput18() != null) {
               createNewBeanForSession(deliverySummaryBeanCollection, currentDate, inputBean.getCalendarInput18());
             }
           }
         }
         if (i == 19) {
           if (!isDataInSession(deliverySummaryBeanCollection,currentDate,inputBean.getCalendarInput19())) {
             if (inputBean.getCalendarInput19() != null) {
               createNewBeanForSession(deliverySummaryBeanCollection, currentDate, inputBean.getCalendarInput19());
             }
           }
         }
         if (i == 20) {
           if (!isDataInSession(deliverySummaryBeanCollection,currentDate,inputBean.getCalendarInput20())) {
             if (inputBean.getCalendarInput20() != null) {
               createNewBeanForSession(deliverySummaryBeanCollection, currentDate, inputBean.getCalendarInput20());
             }
           }
         }
         if (i == 21) {
           if (!isDataInSession(deliverySummaryBeanCollection,currentDate,inputBean.getCalendarInput21())) {
             if (inputBean.getCalendarInput21() != null) {
               createNewBeanForSession(deliverySummaryBeanCollection, currentDate, inputBean.getCalendarInput21());
             }
           }
         }
         if (i == 22) {
           if (!isDataInSession(deliverySummaryBeanCollection,currentDate,inputBean.getCalendarInput22())) {
             if (inputBean.getCalendarInput22() != null) {
               createNewBeanForSession(deliverySummaryBeanCollection, currentDate, inputBean.getCalendarInput22());
             }
           }
         }
         if (i == 23) {
           if (!isDataInSession(deliverySummaryBeanCollection,currentDate,inputBean.getCalendarInput23())) {
             if (inputBean.getCalendarInput23() != null) {
               createNewBeanForSession(deliverySummaryBeanCollection, currentDate, inputBean.getCalendarInput23());
             }
           }
         }
         if (i == 24) {
           if (!isDataInSession(deliverySummaryBeanCollection,currentDate,inputBean.getCalendarInput24())) {
             if (inputBean.getCalendarInput24() != null) {
               createNewBeanForSession(deliverySummaryBeanCollection, currentDate, inputBean.getCalendarInput24());
             }
           }
         }
         if (i == 25) {
           if (!isDataInSession(deliverySummaryBeanCollection,currentDate,inputBean.getCalendarInput25())) {
             if (inputBean.getCalendarInput25() != null) {
               createNewBeanForSession(deliverySummaryBeanCollection, currentDate, inputBean.getCalendarInput25());
             }
           }
         }
         if (i == 26) {
           if (!isDataInSession(deliverySummaryBeanCollection,currentDate,inputBean.getCalendarInput26())) {
             if (inputBean.getCalendarInput26() != null) {
               createNewBeanForSession(deliverySummaryBeanCollection, currentDate, inputBean.getCalendarInput26());
             }
           }
         }
         if (i == 27) {
           if (!isDataInSession(deliverySummaryBeanCollection,currentDate,inputBean.getCalendarInput27())) {
             if (inputBean.getCalendarInput27() != null) {
               createNewBeanForSession(deliverySummaryBeanCollection, currentDate, inputBean.getCalendarInput27());
             }
           }
         }
         if (i == 28) {
           if (!isDataInSession(deliverySummaryBeanCollection,currentDate,inputBean.getCalendarInput28())) {
             if (inputBean.getCalendarInput28() != null) {
               createNewBeanForSession(deliverySummaryBeanCollection, currentDate, inputBean.getCalendarInput28());
             }
           }
         }
         if (i == 29) {
           if (!isDataInSession(deliverySummaryBeanCollection,currentDate,inputBean.getCalendarInput29())) {
             if (inputBean.getCalendarInput29() != null) {
               createNewBeanForSession(deliverySummaryBeanCollection, currentDate, inputBean.getCalendarInput29());
             }
           }
         }
         if (i == 30) {
           if (!isDataInSession(deliverySummaryBeanCollection,currentDate,inputBean.getCalendarInput30())) {
             if (inputBean.getCalendarInput30() != null) {
               createNewBeanForSession(deliverySummaryBeanCollection, currentDate, inputBean.getCalendarInput30());
             }
           }
         }
         if (i == 31) {
           if (!isDataInSession(deliverySummaryBeanCollection,currentDate,inputBean.getCalendarInput31())) {
             if (inputBean.getCalendarInput31() != null) {
               createNewBeanForSession(deliverySummaryBeanCollection, currentDate, inputBean.getCalendarInput31());
             }
           }
         }
       }
     }
   }catch (Exception e) {
     throw new BaseException(e);
   }
 } //end of method

 public void createNewBeanForSession(Collection deliverySummaryBeanCollection,String currentDate, BigDecimal qty) throws BaseException {
   try {
     //set data in new bean
     DeliverySummaryBean bean = new DeliverySummaryBean();
     bean.setRequestedDateToDeliver(DateHandler.getDateFromString("MM/dd/yyyy", currentDate));
     bean.setRequestedQty(qty);
     bean.setRowType("new");
     bean.setStatus("Not Issued");
     bean.setOpenQty(qty);
     Collection col = new Vector(0);
     bean.setDeliveredInfoColl(col);
     deliverySummaryBeanCollection.add(bean);
   }catch (Exception e) {
     throw new BaseException(e);
   }
 } //end of method

 public boolean isDataInSession(Collection deliverySummaryBeanCollection, String currentDate, BigDecimal qty) {
   boolean result = false;
   if (deliverySummaryBeanCollection != null) {
     Iterator iterator = deliverySummaryBeanCollection.iterator();
     while (iterator.hasNext()) {
       DeliverySummaryBean bean = (DeliverySummaryBean) iterator.next();
       if (currentDate.equals(DateHandler.formatDate(bean.getRequestedDateToDeliver(), "MM/dd/yyyy"))) {
         if (qty != null) {
           if (!bean.getRequestedQty().equals(qty)) {
             bean.setRevisedQuantity(qty);
             if (StringHandler.isBlankString(bean.getRowType())) {
               bean.setRowType("revised");
             }
           }
         }else {
           bean.setRevisedQuantity(new BigDecimal(0));
           bean.setRowDeleted("y");
         }
         result = true;
         break;
       }
     }
   }
   return result;
 } //end of method

 public HashMap createHashMapDataForCalendar(Collection deliverySummaryBeanCollection) {
   HashMap myMapp = new HashMap();
   Iterator iterator = deliverySummaryBeanCollection.iterator();
   while (iterator.hasNext()) {
     DeliverySummaryBean bean = (DeliverySummaryBean)iterator.next();
     //don't add row to display data if it is marked as "deleted"
     if ("y".equals(bean.getRowDeleted()) || "duplicated".equals(bean.getRowType())) {
       continue;
     }
     if (bean.getRevisedQuantity() == null) {
       if (bean.getRequestedQty() != null) {
         myMapp.put(DateHandler.formatDate(bean.getRequestedDateToDeliver(), "MM/dd/yyyy"), bean.getRequestedQty().toString());
       }
     }else {
       myMapp.put(DateHandler.formatDate(bean.getRequestedDateToDeliver(), "MM/dd/yyyy"), bean.getRevisedQuantity().toString());
     }
   }

   return myMapp;
 } //end of method

 public void updateSchedule(BigDecimal userId, Collection inputBeanCollection, String totalOrderedQuantity,DeliverySummaryBean headerInputBean) throws BaseException {
   Iterator iterator = inputBeanCollection.iterator();
   DbManager dbManager = new DbManager(getClient());
   boolean scheduleChanged = false;
   String companyId = "";
   BigDecimal prNumber = new BigDecimal(0);
   String lineItem = "1";
   while (iterator.hasNext()) {
     DeliverySummaryBean inputBean = (DeliverySummaryBean) iterator.next();
     companyId = inputBean.getCompanyId();
     prNumber = inputBean.getPrNumber();
     lineItem = inputBean.getLineItem();
     //skip if missing data
     if (inputBean.getRequestedDateToDeliver() == null) {
       continue;
     }
     if (inputBean.getRequestedDateToDeliver().toString().length() == 0) {
       continue;
     }
     if (inputBean.getRequestedQty() == null) {
       continue;
     }

     if ("y".equalsIgnoreCase(inputBean.getRowDeleted())) {
       //insert into delivery_schedule_change for deleted row
       DeliveryScheduleChangeBeanFactory factory = new DeliveryScheduleChangeBeanFactory(dbManager);
       DeliveryScheduleChangeBean deliveryChangeBean = new DeliveryScheduleChangeBean();
       deliveryChangeBean.setCompanyId(inputBean.getCompanyId());
       deliveryChangeBean.setPrNumber(inputBean.getPrNumber());
       deliveryChangeBean.setLineItem(inputBean.getLineItem());
       deliveryChangeBean.setDateToDeliver(inputBean.getRequestedDateToDeliver());
       deliveryChangeBean.setOriginalQty(inputBean.getRequestedQty());
       deliveryChangeBean.setRevisedQty(new BigDecimal(0));
       deliveryChangeBean.setUpdatedDate(new Date());
       deliveryChangeBean.setUpdatedBy(userId);
       if (headerInputBean.getRequestLineStatus() !=null
           && !headerInputBean.getRequestLineStatus().equalsIgnoreCase("Draft"))
       {
         factory.insert(deliveryChangeBean);
       }
       //delete data from delivery_schedule for deleted row
       DeliveryScheduleBeanFactory dsFactory = new DeliveryScheduleBeanFactory(dbManager);
       SearchCriteria criteria = new SearchCriteria();
       criteria.addCriterion("prNumber", SearchCriterion.EQUALS,inputBean.getPrNumber().toString());
       criteria.addCriterion("lineItem", SearchCriterion.EQUALS,inputBean.getLineItem());
       criteria.addCriterion("dateToDeliver", SearchCriterion.EQUALS,DateHandler.formatDate(inputBean.getRequestedDateToDeliver(),"MM/dd/yyyy"));
       dsFactory.delete(criteria);
       if (headerInputBean.getRequestLineStatus() !=null
           && !headerInputBean.getRequestLineStatus().equalsIgnoreCase("Draft"))
       {
           scheduleChanged = true;
       }
     }else if ("revised".equalsIgnoreCase(inputBean.getRowType())) {
       //insert revised qty into delivery_schedule_change
       DeliveryScheduleChangeBeanFactory factory = new DeliveryScheduleChangeBeanFactory(dbManager);
       DeliveryScheduleChangeBean deliveryChangeBean = new DeliveryScheduleChangeBean();
       deliveryChangeBean.setCompanyId(inputBean.getCompanyId());
       deliveryChangeBean.setPrNumber(inputBean.getPrNumber());
       deliveryChangeBean.setLineItem(inputBean.getLineItem());
       deliveryChangeBean.setDateToDeliver(inputBean.getRequestedDateToDeliver());
       deliveryChangeBean.setOriginalQty(inputBean.getRequestedQty());
       deliveryChangeBean.setRevisedQty(inputBean.getRevisedQuantity());
       deliveryChangeBean.setUpdatedDate(new Date());
       deliveryChangeBean.setUpdatedBy(userId);
       if (headerInputBean.getRequestLineStatus() !=null
           && !headerInputBean.getRequestLineStatus().equalsIgnoreCase("Draft"))
       {
       factory.insert(deliveryChangeBean);
       }
       //update qty in delivery_schedule
       DeliveryScheduleBeanFactory dsFactory = new DeliveryScheduleBeanFactory(dbManager);
       SearchCriteria criteria = new SearchCriteria();
       criteria.addCriterion("prNumber", SearchCriterion.EQUALS,inputBean.getPrNumber().toString());
       criteria.addCriterion("lineItem", SearchCriterion.EQUALS,inputBean.getLineItem());
       criteria.addCriterion("dateToDeliver", SearchCriterion.EQUALS,DateHandler.formatDate(inputBean.getRequestedDateToDeliver(),"MM/dd/yyyy"));
          //delete data if revised qty is zero 0
       if ("0".equals(inputBean.getRevisedQuantity().toString())) {
         dsFactory.delete(criteria);
       }else {
         dsFactory.updateQuantity(criteria, inputBean.getRevisedQuantity());
       }
       if (headerInputBean.getRequestLineStatus() !=null
           && !headerInputBean.getRequestLineStatus().equalsIgnoreCase("Draft"))
       {
           scheduleChanged = true;
       }
     }else if ("new".equalsIgnoreCase(inputBean.getRowType())) {
       //insert new qty into delivery_schedule_change
       DeliveryScheduleChangeBeanFactory factory = new DeliveryScheduleChangeBeanFactory(dbManager);
       DeliveryScheduleChangeBean deliveryChangeBean = new DeliveryScheduleChangeBean();
       deliveryChangeBean.setCompanyId(headerInputBean.getCompanyId());
       deliveryChangeBean.setPrNumber(headerInputBean.getPrNumber());
       deliveryChangeBean.setLineItem(headerInputBean.getLineItem());
       deliveryChangeBean.setDateToDeliver(inputBean.getRequestedDateToDeliver());
       //for new row set original qty to 0 and set revised qty to user input
       deliveryChangeBean.setOriginalQty(new BigDecimal(0));
       deliveryChangeBean.setRevisedQty(inputBean.getRequestedQty());
       deliveryChangeBean.setUpdatedDate(new Date());
       deliveryChangeBean.setUpdatedBy(userId);
       if (headerInputBean.getRequestLineStatus() !=null
           && !headerInputBean.getRequestLineStatus().equalsIgnoreCase("Draft"))
       {
       factory.insert(deliveryChangeBean);
       }
       //insert new data in delivery_schedule
       DeliveryScheduleBeanFactory dsFactory = new DeliveryScheduleBeanFactory(dbManager);
       DeliveryScheduleBean deliveryBean = new DeliveryScheduleBean();
       deliveryBean.setCompanyId(headerInputBean.getCompanyId());
       deliveryBean.setPrNumber(headerInputBean.getPrNumber());
       deliveryBean.setLineItem(headerInputBean.getLineItem());
       deliveryBean.setDateToDeliver(inputBean.getRequestedDateToDeliver());
       deliveryBean.setQty(inputBean.getRequestedQty());
       dsFactory.insert(deliveryBean);
       if (headerInputBean.getRequestLineStatus() !=null
           && !headerInputBean.getRequestLineStatus().equalsIgnoreCase("Draft"))
       {
           scheduleChanged = true;
       }
     } //end of if "new"
   } //end of while loop

   //if totalOrderedQuantity is not null, then that means that user updated the quantity
   //and we need to update it in rli.quantity
   if (totalOrderedQuantity != null) {
     if (totalOrderedQuantity.length() > 0) {
       RequestLineItemBeanFactory rlibf = new RequestLineItemBeanFactory(dbManager);
       rlibf.updateQuantity(new BigDecimal(totalOrderedQuantity), companyId, prNumber, lineItem);
     }
   }

   if (scheduleChanged) {
     //first re-allocate
     DeliveryScheduleChangeBeanFactory factory = new DeliveryScheduleChangeBeanFactory(dbManager);
     Collection inArgs = new Vector(4);
     inArgs.add(prNumber);
     inArgs.add(lineItem);
     inArgs.add("Y");
     inArgs.add(companyId);
     factory.lineItemAllocate(inArgs);
     //notify Expediter and CSR
     notifyExpediterCSR(companyId,prNumber,lineItem);
   }

 } //end of method

 private void notifyExpediterCSR(String companyId, BigDecimal prNumber, String lineItem) {
   try{
     // send a message to the expediter and CustomerServiceRep
     // about the schedule change
     Vector emailAddress = new Vector(20);
     boolean hasPR = false;
     String tmpBuyer = "";
     String tmpBuyerEmail = "";
     String tmpBuyerName = "";
     String tmpBranchPlant = "";
     boolean firstTime = true;
     Vector radianPO = new Vector(11);
     Vector receipt = new Vector(11);
     DbManager dbManager = new DbManager(getClient());
     BuyerMaterialRequestViewBeanFactory factory = new BuyerMaterialRequestViewBeanFactory(dbManager);
     Collection col = factory.select(companyId,prNumber,lineItem);
     Iterator iterator = col.iterator();
     while(iterator.hasNext()) {
       BuyerMaterialRequestViewBean bean = (BuyerMaterialRequestViewBean)iterator.next();
       hasPR = true;
       if (firstTime) {
         tmpBuyer = bean.getBuyerId().toString();
         tmpBuyerName = bean.getFullName();
         tmpBuyerEmail = bean.getEmail();
         tmpBranchPlant = bean.getBranchPlant();
         firstTime = false;
       }
       String tmpPO = bean.getRadianPo().toString();
       String tmpReceipt = bean.getReceiptId().toString();
       if (!radianPO.contains(tmpPO)) {
         radianPO.addElement(tmpPO);
       }
       if (!receipt.contains(tmpReceipt)) {
         receipt.addElement(tmpReceipt);
       }
     }

     String subject = library.getString("label.deliveryschedulechange")+": "+tmpBranchPlant;
     String msg = "";
     if (hasPR) {
       String tmpPO = "";
       String tmpReceipt = "";
       for (int i = 0; i < radianPO.size(); i++) {
         tmpPO += (String) radianPO.elementAt(i)+",";
       }
       //removing the last commas ","
       if (tmpPO.length() > 1) {
         tmpPO = tmpPO.substring(0,tmpPO.length()-1);
       }
       for (int ii = 0; ii < receipt.size(); ii++) {
         tmpReceipt += (String)receipt.elementAt(ii)+",";
       }
       //removing the last commas ","
       if (tmpReceipt.length() > 1) {
         tmpReceipt = tmpReceipt.substring(0,tmpReceipt.length()-1);
       }

       //buyer is assigned then get buyer name from personnel_id
       if (tmpBuyer.length() > 1) {
         if (!StringHandler.isBlankString(tmpBuyerEmail)) {
           if (!emailAddress.contains(tmpBuyerEmail)) {
             emailAddress.add(tmpBuyerEmail);
           }
         }

         if (tmpPO.length() > 0 || tmpReceipt.length() > 0) {
           subject += " ("+tmpBuyerName;
           if (tmpPO.length() > 0) {
             subject += " - "+library.getString("label.po")+": "+tmpPO;
           }
           if (tmpReceipt.length() > 0) {
             subject += " - "+library.getString("label.receipt")+": "+tmpReceipt;
           }
           subject += ")";
         }else {
           subject += " ("+tmpBuyerName+")";
         }
       }else {
         if (tmpPO.length() > 0 || tmpReceipt.length() > 0) {
           subject += " ("+library.getString("label.unassigned");
           if (tmpPO.length() > 0) {
             subject += " - "+library.getString("label.po")+": "+tmpPO;
           }
           if (tmpReceipt.length() > 0) {
             subject += " - "+library.getString("label.receipt")+": "+tmpReceipt;
           }
           subject += ")";
         }else {
           subject += " ("+library.getString("label.unassigned")+")";
         }
       }
       //get CRS and Expediter for hub
       Collection csrNExpiderForHubColl = factory.selectCsrNExpiderForHub(companyId,tmpBranchPlant);
       Iterator iter = csrNExpiderForHubColl.iterator();
       while(iter.hasNext()) {
         emailAddress.add((String)iter.next());
       }
     }else {
       msg = library.getString("label.likemmnobuyernotified")+"\n\n";
       //serving this schedule delivery from MM, notify CSR only
       Collection csrForMRColl = factory.selectCsrForMr(companyId,prNumber);
       Iterator iter = csrForMRColl.iterator();
       while(iter.hasNext()) {
         emailAddress.add((String)iter.next());
       }
     }

     msg += library.getString("label.reviewdeliveryschedulechange")+":\n";
     // build the URL
     String url = this.URL+"/home.do";

     msg = msg + url+" \n\n";
     msg = msg + library.getString("label.reviewschedulemsg");
     String[] toAddress = new String[emailAddress.size()];
     for(int i=0;i<emailAddress.size();i++) {
       toAddress[i] = (String)emailAddress.elementAt(i);
     }
     //sending email
     if (toAddress.length > 0) {
       MailProcess.sendEmail(toAddress, new String[0], subject, msg, false);
     }else {
       String[] toDev = {"deverror@tcmis.com"};
       MailProcess.sendEmail(toDev, new String[0], subject,library.getString("label.nobuyerexpediter")+"\n"+msg, false);
     }
   }catch(Exception e){
     e.printStackTrace();
   }

 } //end of method

} //end of class