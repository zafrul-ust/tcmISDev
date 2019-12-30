package com.tcmis.client.catalog.action;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.commons.beanutils.DynaBean;

import com.tcmis.client.catalog.beans.ScheduleDeliveryHeaderViewBean;
import com.tcmis.client.catalog.beans.DeliverySummaryBean;
import com.tcmis.client.catalog.process.DeliveryScheduleProcess;
import com.tcmis.client.catalog.process.OrderTrackingProcess;
import com.tcmis.client.catalog.process.MrScheduleFreqProcess;
import com.tcmis.common.exceptions.BaseException;
import com.tcmis.common.framework.TcmISBaseAction;
import com.tcmis.common.util.BeanHandler;
import com.tcmis.common.util.DateHandler;
import com.tcmis.common.util.BeanSorter;
import com.tcmis.common.admin.beans.PersonnelBean;
import com.tcmis.common.admin.beans.BlackOutDateInputBean;
import com.tcmis.common.admin.process.BlackoutDayProcess;
import com.tcmis.client.catalog.beans.DeliveryScheduleCalendarInputBean;
import com.tcmis.client.catalog.beans.MrScheduleFreqInputBean;
import com.tcmis.client.common.process.MaterialRequestProcess;

public class DeliveryScheduleAction extends TcmISBaseAction {
  public ActionForward executeAction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws BaseException, Exception {

    if (!this.isLoggedIn(request)) {
      request.setAttribute("requestedPage", "deliveryschedulemain");
      request.setAttribute("requestedURLWithParameters", this.getRequestedURLWithParameters(request));
      return (mapping.findForward("login"));
    }

    PersonnelBean personnelBean = (PersonnelBean)this.getSessionObject(request, "personnelBean");
    BigDecimal personnelId = new BigDecimal(personnelBean.getPersonnelId());
    
    if (request.getParameter("prNumber") == null) {
      return mapping.findForward("systemerrorpage");
    } else {
      //if form is not null we have to perform some action
      if (form != null) {
        this.saveTcmISToken(request);
        //copy inputBean from form
        DeliverySummaryBean myInputBean = new DeliverySummaryBean();
        BeanHandler.copyAttributes(form, myInputBean);
        //set global data for request
        request.setAttribute("companyId", myInputBean.getCompanyId());
        request.setAttribute("prNumber", myInputBean.getPrNumber());
        request.setAttribute("lineItem", myInputBean.getLineItem());
		  request.setAttribute("requestedQty", myInputBean.getRequestedQty());
		  request.setAttribute("source", myInputBean.getSource());
        request.setAttribute("personnelId",personnelId.toString());
        Locale locale = request.getLocale();
        if (((DynaBean) form).get("action") != null && !"search".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
          //this data is being past from search sql
          request.setAttribute("requestLineStatus", myInputBean.getRequestLineStatus());
          request.setAttribute("requestor", myInputBean.getRequestor());
          request.setAttribute("userViewType",myInputBean.getUserViewType());
          request.setAttribute("facilityId",myInputBean.getFacilityId());
        }

        //OrderTrackingProcess orderTrackingProcess = new OrderTrackingProcess(this.getDbUser(request));
        //check what button was pressed and determine where to go
        if (((DynaBean) form).get("action") != null && "search".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
          DeliveryScheduleProcess deliveryScheduleProcess = new DeliveryScheduleProcess(this.getDbUser(request));
          request.setAttribute("scheduleDeliveryHeaderViewBean", deliveryScheduleProcess.getScheduleDeliveryHeaderViewBean(myInputBean));
          return (mapping.findForward("showsearch"));
        } else if (((DynaBean) form).get("action") != null && "result".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
          DeliveryScheduleProcess deliveryScheduleProcess = new DeliveryScheduleProcess(this.getDbUser(request));
          //set facility Id
          myInputBean.setFacilityId(deliveryScheduleProcess.getFacilityIdForMr(myInputBean));
          request.setAttribute("facilityId",myInputBean.getFacilityId());
          //set user view type
          myInputBean.setUserViewType(deliveryScheduleProcess.getUserViewType(personnelBean,myInputBean));
          request.setAttribute("userViewType",myInputBean.getUserViewType());
          //set blackout date
          setBlackoutDate(request,locale,myInputBean,true);
          //get part lead time
          request.setAttribute("medianMrLeadTime", deliveryScheduleProcess.getMedianMrLeadTime(myInputBean));
          this.setSessionObject(request,deliveryScheduleProcess.getDeliverySummaryBeanBean(myInputBean),"deliverySummaryBeanCollection");
          request.setAttribute("userChangedData","n");
          request.setAttribute("showCalendar","y");
          return (mapping.findForward("showresults"));
        } else if (((DynaBean) form).get("action") != null && "update".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
          StringBuffer requestURL = request.getRequestURL();
          DeliveryScheduleProcess deliveryScheduleProcess = new DeliveryScheduleProcess(this.getDbUser(request),requestURL.substring(0,requestURL.lastIndexOf("/")));
          try {
            DynaBean dynaBean = (DynaBean) form;
            List list = (List) dynaBean.get("scheduleDeliveryInputBean");
            if (list != null) {
              Collection deliverySummaryBeanCollection = new Vector();
              Iterator iterator = list.iterator();
              while (iterator.hasNext()) {
                DeliverySummaryBean bean = new DeliverySummaryBean();
                org.apache.commons.beanutils.LazyDynaBean lazyBean = (org.apache.commons.beanutils.LazyDynaBean) iterator.next();
                BeanHandler.copyAttributes(lazyBean, bean,getTcmISLocale(request));
                bean.setCompanyId(myInputBean.getCompanyId());
                bean.setPrNumber(myInputBean.getPrNumber());
                bean.setLineItem(myInputBean.getLineItem());
                deliverySummaryBeanCollection.add(bean);
              }
              //update data
              String totalOrderedQuantity = "";
              if (((DynaBean) form).get("totalOrderedQuantity") != null) {
               totalOrderedQuantity = (String) ((DynaBean) form).get("totalOrderedQuantity");
              }
              deliveryScheduleProcess.updateSchedule(personnelId, deliverySummaryBeanCollection, totalOrderedQuantity,myInputBean);
            }
            //set blackout date
            setBlackoutDate(request,locale,myInputBean,true);
            //re-fill data and display to user
            this.setSessionObject(request,deliveryScheduleProcess.getDeliverySummaryBeanBean(myInputBean),"deliverySummaryBeanCollection");
            request.setAttribute("userChangedData","n");
            request.setAttribute("showCalendar","y");
            return (mapping.findForward("showresults"));
          }catch (Exception e) {
              e.printStackTrace();
            BaseException be = new BaseException();
            be.setMessageKey("error.db.update");
            //re-fill data and display to user
            this.setSessionObject(request,deliveryScheduleProcess.getDeliverySummaryBeanBean(myInputBean),"deliverySummaryBeanCollection");
            request.setAttribute("showCalendar","n");
            throw be;
          }
        }else if (((DynaBean) form).get("action") != null && "showCalendar".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
          //first save data to session
          DynaBean dynaBean = (DynaBean) form;
          List list = (List) dynaBean.get("scheduleDeliveryInputBean");
          Collection deliverySummaryBeanCollection = new Vector();
          if (list != null) {
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
              DeliverySummaryBean bean = new DeliverySummaryBean();
              org.apache.commons.beanutils.LazyDynaBean lazyBean = (org.apache.commons.beanutils.LazyDynaBean) iterator.next();
              BeanHandler.copyAttributes(lazyBean, bean,getTcmISLocale(request));
              if (bean.getRequestedDateToDeliver() == null) {
                continue;
              }
              bean.setCompanyId(myInputBean.getCompanyId());
              bean.setPrNumber(myInputBean.getPrNumber());
              bean.setLineItem(myInputBean.getLineItem());
              deliverySummaryBeanCollection.add(bean);
            }
          }
          //set data for each date
          setDateDisplayMap(request,deliverySummaryBeanCollection);
          //set current mm/yyyy to display
          setDisplayDate(request,locale);
          //get blackout date
          setBlackoutDate(request,locale,myInputBean,false);
          //updating session data because user has a chance to change data
          DeliveryScheduleProcess deliveryScheduleProcess = new DeliveryScheduleProcess(this.getDbUser(request));
          this.setSessionObject(request,deliveryScheduleProcess.getObjectViewFromData(deliverySummaryBeanCollection),"deliverySummaryBeanCollection");
          
  		  request.setAttribute("requestedDateToDeliver0",request.getParameter("scheduleDeliveryInputBean[0].requestedDateToDeliver"));
          request.setAttribute("userChangedData",myInputBean.getUserChangedData());
          request.setAttribute("showSummary","y");
          return (mapping.findForward("showcalendar"));
        }else if (((DynaBean) form).get("action") != null && "showCalendarNext".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
          Collection deliverySummaryBeanCollection = (Collection)this.getSessionObject(request, "deliverySummaryBeanCollection");
          DeliveryScheduleCalendarInputBean inputBean = new DeliveryScheduleCalendarInputBean();
          BeanHandler.copyAttributes(form, inputBean);
          //save data to session
          DeliveryScheduleProcess deliveryScheduleProcess = new DeliveryScheduleProcess(this.getDbUser(request));
          deliveryScheduleProcess.updateDataInSession(deliverySummaryBeanCollection,inputBean);
          //set data for each date
          setDateDisplayMap(request,deliverySummaryBeanCollection);
          //set current mm/yyyy to display
          setDisplayDate(request,locale);
          //get blackout date
          setBlackoutDate(request,locale,myInputBean,false);
          DeliverySummaryBean myBean = new DeliverySummaryBean();
          BeanSorter beanSorter = new BeanSorter(myBean);
          this.setSessionObject(request,beanSorter.sort(deliverySummaryBeanCollection, "getRequestedDateToDeliver"),"deliverySummaryBeanCollection");
          request.setAttribute("requestedDateToDeliver0",request.getParameter("scheduleDeliveryInputBean[0].requestedDateToDeliver"));
          request.setAttribute("userChangedData",myInputBean.getUserChangedData());
          request.setAttribute("showSummary","y");
          return (mapping.findForward("showcalendar"));
        }else if (((DynaBean) form).get("action") != null && "updateShowCalendar".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
          Collection deliverySummaryBeanCollection = (Collection)this.getSessionObject(request, "deliverySummaryBeanCollection");
          DeliveryScheduleCalendarInputBean inputBean = new DeliveryScheduleCalendarInputBean();
          BeanHandler.copyAttributes(form, inputBean);
          //save data to session
          DeliveryScheduleProcess deliveryScheduleProcess = new DeliveryScheduleProcess(this.getDbUser(request));
          deliveryScheduleProcess.updateDataInSession(deliverySummaryBeanCollection,inputBean);
          
          //        update data
          String totalOrderedQuantity = "";
          if (((DynaBean) form).get("totalOrderedQuantity") != null) {
           totalOrderedQuantity = (String) ((DynaBean) form).get("totalOrderedQuantity");
          }
          deliveryScheduleProcess.updateSchedule(personnelId, deliverySummaryBeanCollection, totalOrderedQuantity,myInputBean);
          //set data for each date
          setDateDisplayMap(request,deliverySummaryBeanCollection);
          //set current mm/yyyy to display
          setDisplayDate(request,locale);
          //get blackout date
          setBlackoutDate(request,locale,myInputBean,false);
          DeliverySummaryBean myBean = new DeliverySummaryBean();
          BeanSorter beanSorter = new BeanSorter(myBean);
          this.setSessionObject(request,deliveryScheduleProcess.getDeliverySummaryBeanBean(myInputBean),"deliverySummaryBeanCollection");
//          this.setSessionObject(request,beanSorter.sort(deliverySummaryBeanCollection, "getRequestedDateToDeliver"),"deliverySummaryBeanCollection");
          
          Vector<DeliverySummaryBean> c = (Vector<DeliverySummaryBean>) this.getSessionObject(request, "deliverySummaryBeanCollection");
         
          SimpleDateFormat informat = new SimpleDateFormat("dd-MMM-yyyy",getTcmISLocale());
          
          if(c.size() > 0)
        	  request.setAttribute("requestedDateToDeliver0",informat.format(c.get(0).getRequestedDateToDeliver()));
          request.setAttribute("userChangedData",myInputBean.getUserChangedData());
          request.setAttribute("showSummary","y");
          return (mapping.findForward("showcalendar"));
        }else if (((DynaBean) form).get("action") != null && "showCalendarPervious".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
          Collection deliverySummaryBeanCollection = (Collection)this.getSessionObject(request, "deliverySummaryBeanCollection");
          DeliveryScheduleCalendarInputBean inputBean = new DeliveryScheduleCalendarInputBean();
          BeanHandler.copyAttributes(form, inputBean);
          //save data to session
          DeliveryScheduleProcess deliveryScheduleProcess = new DeliveryScheduleProcess(this.getDbUser(request));
          deliveryScheduleProcess.updateDataInSession(deliverySummaryBeanCollection,inputBean);
          //set data for each date
          setDateDisplayMap(request,deliverySummaryBeanCollection);
          //set current mm/yyyy to display
          setDisplayDate(request,locale);
          //get blackout date
          setBlackoutDate(request,locale,myInputBean,false);
          DeliverySummaryBean myBean = new DeliverySummaryBean();
          BeanSorter beanSorter = new BeanSorter(myBean);
          this.setSessionObject(request,beanSorter.sort(deliverySummaryBeanCollection, "getRequestedDateToDeliver"),"deliverySummaryBeanCollection");
          
          request.setAttribute("requestedDateToDeliver0",request.getParameter("requestedDateToDeliver0"));
          request.setAttribute("userChangedData",myInputBean.getUserChangedData());
          request.setAttribute("showSummary","y");
          return (mapping.findForward("showcalendar"));
        }else if (((DynaBean) form).get("action") != null && "showSummary".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
          Collection deliverySummaryBeanCollection = (Collection)this.getSessionObject(request, "deliverySummaryBeanCollection");
          DeliveryScheduleCalendarInputBean inputBean = new DeliveryScheduleCalendarInputBean();
          BeanHandler.copyAttributes(form, inputBean);
          DeliveryScheduleProcess deliveryScheduleProcess = new DeliveryScheduleProcess(this.getDbUser(request));
          deliveryScheduleProcess.updateDataInSession(deliverySummaryBeanCollection,inputBean);
          //get blackout date
          setBlackoutDate(request,locale,myInputBean,true);
          DeliverySummaryBean myBean = new DeliverySummaryBean();
          BeanSorter beanSorter = new BeanSorter(myBean);
          this.setSessionObject(request,beanSorter.sort(deliverySummaryBeanCollection, "getRequestedDateToDeliver"),"deliverySummaryBeanCollection");
          request.setAttribute("userChangedData",myInputBean.getUserChangedData());
          request.setAttribute("showCalendar","y");
          return (mapping.findForward("showresults"));
        }
        else if (((DynaBean) form).get("action") != null && "freqUpdate".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
        MrScheduleFreqInputBean freqInputBean = new MrScheduleFreqInputBean();
        //first save data to session
          DynaBean dynaBean = (DynaBean) form;
          List list = (List) dynaBean.get("mrScheduleFreqInputBean");
          Collection mrScheduleFreqInputBeanCollection = new Vector();
          if (list != null) {
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {

              org.apache.commons.beanutils.LazyDynaBean lazyBean = (org.apache.commons.beanutils.LazyDynaBean) iterator.next();
              BeanHandler.copyAttributes(lazyBean, freqInputBean,getTcmISLocale(request));
              freqInputBean.setCompanyId(myInputBean.getCompanyId());
              freqInputBean.setPrNumber(myInputBean.getPrNumber());
              freqInputBean.setLineItem(myInputBean.getLineItem());
            }
          }
        
        MrScheduleFreqProcess process = new MrScheduleFreqProcess(this.getDbUser(request),this.getTcmISLocale(request));
        process.update(freqInputBean);
        /*request.setAttribute("bean",process.getSearchResult(freqInputBean).get(0));
        DeliveryScheduleProcess deliveryScheduleProcess = new DeliveryScheduleProcess(this.getDbUser(request));
        //set facility Id
        myInputBean.setFacilityId(deliveryScheduleProcess.getFacilityIdForMr(myInputBean));
        request.setAttribute("facilityId",myInputBean.getFacilityId());
        //set user view type
        myInputBean.setUserViewType(deliveryScheduleProcess.getUserViewType(personnelBean,myInputBean));
        request.setAttribute("userViewType",myInputBean.getUserViewType());
        //set blackout date
        setBlackoutDate(request,locale,myInputBean,true);
        //get part lead time
        BigDecimal medianMrLeadTime = new BigDecimal("0");
        medianMrLeadTime = deliveryScheduleProcess.getMedianMrLeadTime(myInputBean);
        request.setAttribute("medianMrLeadTime", medianMrLeadTime);
        request.setAttribute("medianMrLeadTimeDaysColl", deliveryScheduleProcess.getDaysTillLeadTime (medianMrLeadTime));*/
        request.setAttribute("medianMrLeadTime", new BigDecimal("0"));
        request.setAttribute("totalQty", freqInputBean.getTotal());
    }
    else if (((DynaBean) form).get("action") != null && "freqSearch".equalsIgnoreCase(((String) ((DynaBean) form).get("action")))) {
        MrScheduleFreqInputBean freqInputBean = new MrScheduleFreqInputBean();
        BeanHandler.copyAttributes(form, freqInputBean,this.getLocale(request));
        MrScheduleFreqProcess process = new MrScheduleFreqProcess(this.getDbUser(request),this.getTcmISLocale(request));
        request.setAttribute("bean",process.getSearchResult(freqInputBean).get(0));

        DeliveryScheduleProcess deliveryScheduleProcess = new DeliveryScheduleProcess(this.getDbUser(request));
        //set facility Id
        myInputBean.setFacilityId(deliveryScheduleProcess.getFacilityIdForMr(myInputBean));
        request.setAttribute("facilityId",myInputBean.getFacilityId());
        //set user view type
        myInputBean.setUserViewType(deliveryScheduleProcess.getUserViewType(personnelBean,myInputBean));
        request.setAttribute("userViewType",myInputBean.getUserViewType());
        //set blackout date
        setBlackoutDate(request,locale,myInputBean,true);
        //get part lead time
        BigDecimal medianMrLeadTime = new BigDecimal("0");
        medianMrLeadTime = deliveryScheduleProcess.getMedianMrLeadTime(myInputBean);
        request.setAttribute("medianMrLeadTime", medianMrLeadTime);
        request.setAttribute("medianMrLeadTimeDaysColl", deliveryScheduleProcess.getDaysTillLeadTime (medianMrLeadTime));
     }
    }
      return mapping.findForward("success");
    }
  } //end of method

  void setDateDisplayMap(HttpServletRequest request, Collection deliverySummaryBeanCollection) throws BaseException {
    try {
      if (deliverySummaryBeanCollection.size() > 0) {
        DeliverySummaryBean myBean = new DeliverySummaryBean();
        BeanSorter beanSorter = new BeanSorter(myBean);
        DeliveryScheduleProcess deliveryScheduleProcess = new DeliveryScheduleProcess(this.getDbUser(request));
        request.setAttribute("dateDisplayMap", deliveryScheduleProcess.createHashMapDataForCalendar(beanSorter.sort(deliverySummaryBeanCollection, "getRequestedDateToDeliver")));
      }
    }catch (Exception e) {
     throw new BaseException(e);
   }
  } //end of method

  void setDisplayDate(HttpServletRequest request, Locale locale) {
    String calendarDate = "";
    if (request.getParameter("calendarDate") == null) {
      Calendar cal = Calendar.getInstance(locale);
      calendarDate = DateHandler.formatDate(cal.getTime(),"MM/dd/yyyy");
    }else {
      calendarDate = request.getParameter("calendarDate").toString();
    }
    request.setAttribute("displayDate",calendarDate);
  } //end of method

  void setBlackoutDate(HttpServletRequest request, Locale locale, DeliverySummaryBean inputBean, boolean allBlackOutDays) throws BaseException {
    try {
      //if viewer than don't need to set blackout date since users can't
      //edit schedule
      if ("viewer".equalsIgnoreCase(inputBean.getUserViewType())) {
        request.setAttribute("blackOutDayColl",new Vector(0));
      }else {
        String calendarDate = "";
        if (request.getParameter("calendarDate") == null) {
          Calendar cal = Calendar.getInstance(locale);
          calendarDate = DateHandler.formatDate(cal.getTime(), "MM/dd/yyyy");
        } else {
          calendarDate = request.getParameter("calendarDate").toString();
        }
        request.setAttribute("displayDate", calendarDate);
        BlackOutDateInputBean blackOutDateInputBean = new BlackOutDateInputBean();
        BlackoutDayProcess blackoutDayProcess = new BlackoutDayProcess(this.getDbUser(request));
        blackOutDateInputBean.setCompanyId(inputBean.getCompanyId());
        blackOutDateInputBean.setPrNumber(inputBean.getPrNumber());
        blackOutDateInputBean.setLineItem(inputBean.getLineItem());
        if (!allBlackOutDays) {
          blackOutDateInputBean.setCalendarDate(DateHandler.getDateFromString("MM/dd/yyyy", calendarDate));
        }
        request.setAttribute("blackOutDayColl", blackoutDayProcess.getBlackoutDays(blackOutDateInputBean));
      }
    }catch (Exception e) {
     throw new BaseException(e);
   }
  } //end of method

} //end of class
