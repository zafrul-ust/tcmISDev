<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html:html lang="true">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss />
<!-- CSS for YUI -->

<%@ include file="/common/locale.jsp" %>

<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
<!-- Add any other stylesheets you need for the page here -->

<script src="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>
<script src="/js/common/resultiframeresize.js" language="JavaScript"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js" language="JavaScript"></script>
<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<%-- For Calendar support --%>
<!--
<script type="text/javascript" src="/js/calendar/date.js"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>
<script src="/js/calendar/CalendarPopup.js" language="JavaScript"></script>
-->

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here -->
<script src="/js/common/ordertracking/deliveryschedule.js" language="JavaScript"></script>

<!-- This is for the YUI, uncomment if you will use this -->
<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>

<title>
<fmt:message key="deliveryschedule.label.title"/>
</title>

<%-- global variables --%>
<c:set var="showCalendar" value="${showCalendar}"/>

<script LANGUAGE="JavaScript" TYPE="text/javascript">
<!--
with(milonic=new menuname("showAll")){
 top="offset=2"
 style = contextStyle;
 margin=3
 aI("text=<fmt:message key="label.addrow"/>;url=javascript:showAddRow();");
 aI("text=<fmt:message key="label.remove"/>;url=javascript:showRemove();");
}

with(milonic=new menuname("showEmpty")){
 top="offset=2"
 style = contextStyle;
 margin=3
 aI("type=header;text=<fmt:message key="label.addrow"/>;offbgcolor=#e5e5e5;onbgcolor=#e5e5e5;rawcss=cursor:default;padding-left:5px;padding-right:5px;");
 aI("type=header;text=<fmt:message key="label.remove"/>;offbgcolor=#e5e5e5;onbgcolor=#e5e5e5;rawcss=cursor:default;padding-left:5px;padding-right:5px;");
}

with(milonic=new menuname("showAdd")){
 top="offset=2"
 style = contextStyle;
 margin=3
 aI("text=<fmt:message key="label.addrow"/>;url=javascript:showAddRow();");
 aI("type=header;text=<fmt:message key="label.remove"/>;offbgcolor=#e5e5e5;onbgcolor=#e5e5e5;rawcss=cursor:default;padding-left:5px;padding-right:5px;");
}

drawMenus();
// -->
</script>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
partialDeliveredQtyError:"<fmt:message key="label.partialDeliveredQtyError"/>",
increaseQtyError:"<fmt:message key="label.increaseQtyError"/>",
dataChanged:"<fmt:message key="label.dataChanged"/>",
frequencyschedulewarn:"<fmt:message key="label.frequencyschedulewarn"/>",    
itemInteger:"<fmt:message key="error.item.integer"/>"};

var windowCloseOnEsc = true;
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="myOnload('list')" onmouseup="toggleContextMenuToNormal()" >

<tcmis:form action="/deliveryscheduleresults.do" onsubmit="return submitFrameOnlyOnce();">

<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->

<c:set var="userViewType" value="${userViewType}"/>
<c:set var="requestLineStatus" value="${param.requestLineStatus}"/>

<script type="text/javascript">
<!--
  <c:choose>
   <c:when test="${userViewType == 'user' || userViewType == 'alternate' || userViewType == 'editMRQty' || userViewType == 'approverEditMRQty'}" >
       showUpdateLinks = true;
   </c:when>
   <c:otherwise>
       showUpdateLinks = false;
   </c:otherwise>
  </c:choose>
//-->
</script>

<!-- show calendar -->
<script type="text/javascript">
<!--
  <c:choose>
   <c:when test="${showCalendar == 'y'}" >
       showCalendarLink = true;
   </c:when>
   <c:otherwise>
       showCalendarLink = false;
   </c:otherwise>
  </c:choose>
//-->
</script>

<!-- show summary -->
<script type="text/javascript">
<!--
  showSummaryLink = false;
//-->
</script>

<script type="text/javascript">
<!--
// define __block_weekdays array to block out weekdays, 0 means sunday, 1 means monday....6 means saturday.
// e.g.block sunday and  monday.
/*TODO get these values from the database.*/
/*
 var __block_weekdays = new Array(
 0,
 6
 );
*/
// block June 16, 17 or year 2009
 var __block_dates = new Array(
 '20090703',
 '20090907',
 '20091126',
 '20091127',
 '20091224',
 '20091225'
 );

<%--// block June 16, 17 or year 2009
 var __block_dates = new Array(
<c:forEach var="blackOutDaybean" items="${blackOutDayColl}" varStatus="status">
    <c:choose>
      <c:when test="${status.index == 0}">
        <fmt:formatDate var="formattedRequestedDate" value="${blackOutDaybean}" pattern="yyyyddMM"/>
        "<c:out value="${formattedRequestedDate}" />"
      </c:when>
      <c:otherwise>
        <fmt:formatDate var="formattedRequestedDate" value="${blackOutDaybean}" pattern="yyyyddMM"/>
        ,"<c:out value="${formattedRequestedDate}" />"
      </c:otherwise>
    </c:choose>
  </c:forEach>
 );--%>


  <%--var blackOutDate = new Array(
  <c:forEach var="blackOutDaybean" items="${blackOutDayColl}" varStatus="status">
    <c:choose>
      <c:when test="${status.index == 0}">
        <fmt:formatDate var="formattedRequestedDate" value="${blackOutDaybean}" pattern="MMM dd, yyyy"/>
        "<c:out value="${formattedRequestedDate}" />"
      </c:when>
      <c:otherwise>
        <fmt:formatDate var="formattedRequestedDate" value="${blackOutDaybean}" pattern="MMM dd, yyyy"/>
        ,"<c:out value="${formattedRequestedDate}" />"
      </c:otherwise>
    </c:choose>
  </c:forEach>
  );--%>
//-->
</script>

<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
So this is just used to feed the YUI pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
 <html:errors/>
</div>

<script type="text/javascript">
<!--
/*YAHOO.namespace("example.aqua");
YAHOO.util.Event.addListener(window, "load", init);*/

/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
//-->
</script>
<!-- Error Messages Ends -->

<div class="interface" id="resultsPage">
<div class="backGroundContent">

<c:if test="${deliverySummaryBeanCollection != null}" >
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>

<c:if test="${!empty deliverySummaryBeanCollection}" >
 <table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">
  <c:forEach var="deliverySummaryBean" items="${deliverySummaryBeanCollection}" varStatus="status">
    <c:if test="${status.index % 10 == 0}">
      <tr>
        <th width="20%"><fmt:message key="deliveryschedule.label.scheduled"/></th>
        <th width="20%"><fmt:message key="deliveryschedule.label.currentqty"/></th>
        <c:if test="${requestLineStatus != 'Draft'}" >
        <th width="20%"><fmt:message key="deliveryschedule.label.revisedqty"/></th>
        <th width="20%"><fmt:message key="label.delivered"/></th>
        <th width="20%"><fmt:message key="deliveryschedule.label.deliveredqty"/></th>
        </c:if>
      </tr>
    </c:if>

    <c:choose>
      <c:when test="${status.index % 2 == 0}" >
        <c:set var="colorClass" value=''/>
      </c:when>
      <c:otherwise>
        <c:set var="colorClass" value='alt'/>
      </c:otherwise>
    </c:choose>

    <%-- hide deleted row  --%>
    <c:set var="tmpRowDeleted" value='${status.current.rowDeleted}'/>
    <c:choose>
      <c:when test="${tmpRowDeleted == 'y'}">
        <tr class="<c:out value="${colorClass}"/>" id="rowId<c:out value="${status.index}"/>" style="display: none">
      </c:when>
      <c:otherwise>
        <tr class="<c:out value="${colorClass}"/>" id="rowId<c:out value="${status.index}"/>" onmouseup="selectRow('<c:out value="${status.index}"/>','<c:out value="${dataCount}"/>')">
      </c:otherwise>
    </c:choose>
      <%-- rest of row data --%>
      <c:choose>
        <%-- editable view --%>
        <c:when test="${userViewType == 'user' || userViewType == 'alternate' || userViewType == 'editMRQty' || userViewType == 'approverEditMRQty'}" >
          <c:set var="deliveredInfoColl"  value='${status.current.deliveredInfoColl}'/>
          <c:set var="numberOfChild" value="${1}"/>
          <bean:size id="numberOfChild" name="deliveredInfoColl"/>
            <c:if test='${numberOfChild == 0}'>
                <c:set var="numberOfChild" value="${1}"/>
            </c:if>
          <c:set var="childRowCount" value="${0}"/>
          <c:set var="rowStatus" value='${status.current.status}'/>
          <c:choose>
            <c:when test="${rowStatus == 'Delivered'}">
              <fmt:formatDate var="formattedRequestedDate" value="${status.current.requestedDateToDeliver}" pattern="${dateFormatPattern}"/>
              <td width="20%" rowspan='<c:out value="${numberOfChild}"/>'><c:out value="${formattedRequestedDate}"/></td>
              <td width="20%" rowspan='<c:out value="${numberOfChild}"/>'><c:out value="${status.current.requestedQty}"/></td>
              <c:if test="${requestLineStatus != 'Draft'}" >
              <td width="20%" rowspan='${numberOfChild}'>&nbsp;</td>
              </c:if>
              <%-- building javascript array --%>
              <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].requestedDateToDeliver" id="requestedDateToDeliver<c:out value="${dataCount}"/>" type="hidden" value="<c:out value="${formattedRequestedDate}"/>">
              <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].requestedQty" id="requestedQty<c:out value="${dataCount}"/>" type="hidden" value="<c:out value="${status.current.requestedQty}"/>">
              <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].revisedQuantity" id="revisedQuantity<c:out value="${dataCount}"/>" type="hidden" value="<c:out value=""/>">
            </c:when>
            <c:otherwise>
              <fmt:formatDate var="formattedRequestedDate" value="${status.current.requestedDateToDeliver}" pattern="${dateFormatPattern}"/>
              <td width="20%" rowspan='<c:out value="${numberOfChild}"/>'><c:out value="${formattedRequestedDate}"/></td>
              <%-- building javascript array --%>
              <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].requestedDateToDeliver" id="requestedDateToDeliver<c:out value="${dataCount}"/>" type="hidden" value="<c:out value="${formattedRequestedDate}"/>">
              <c:set var="tmpRowType" value='${status.current.rowType}'/>
              <c:choose>
                <c:when test="${tmpRowType == 'new'}">
                  <td width="20%" rowspan='<c:out value="${numberOfChild}"/>'><input type="text" name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].requestedQty" id="requestedQty<c:out value="${dataCount}"/>" value="<c:out value="${status.current.requestedQty}"/>" size="8" maxlength="10" class="inputBox" onchange="scheduledQtyChanged('y')"></td>
                  <c:if test="${requestLineStatus != 'Draft'}">
                      <td width="20%" rowspan='<c:out value="${numberOfChild}"/>'>&nbsp;</td>
                   </c:if>
                  <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].revisedQuantity" id="revisedQuantity<c:out value="${dataCount}"/>" type="hidden" value="<c:out value=""/>" size="8" maxlength="10" class="inputBox">
                </c:when>
                <c:when test="${tmpRowType == 'revised'}">
                  <td width="20%" rowspan='<c:out value="${numberOfChild}"/>'><input type="text" name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].revisedQuantity" id="revisedQuantity<c:out value="${dataCount}"/>" value="<c:out value="${status.current.revisedQuantity}"/>" size="8" maxlength="10" class="inputBox" onchange="draftRevisedQtyChanged('${dataCount}','y')"></td>
                  <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].requestedQty" id="requestedQty<c:out value="${dataCount}"/>" type="hidden" value="<c:out value="${status.current.requestedQty}"/>" size="8" maxlength="10" class="inputBox">
                  <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].requestedDateToDeliverForAudit" id="requestedDateToDeliverForAudit<c:out value="${dataCount}"/>" value="<c:out value="${status.current.requestedDateToDeliver}"/>" type="hidden">
                </c:when>
                <c:when test="${requestLineStatus == 'Draft'}">
                  <td width="20%" rowspan='<c:out value="${numberOfChild}"/>'><input type="text" name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].revisedQuantity" id="revisedQuantity<c:out value="${dataCount}"/>" value="<c:out value="${status.current.requestedQty}"/>" size="8" maxlength="10" class="inputBox" onchange="draftRevisedQtyChanged('${dataCount}','y')"></td>
                  <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].requestedQty" id="requestedQty<c:out value="${dataCount}"/>" type="hidden" value="<c:out value="${status.current.requestedQty}"/>" size="8" maxlength="10" class="inputBox">
                  <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].requestedDateToDeliverForAudit" id="requestedDateToDeliverForAudit<c:out value="${dataCount}"/>" value="<c:out value="${status.current.requestedDateToDeliver}"/>" type="hidden">
                </c:when>
                <c:otherwise>
                  <td width="20%" rowspan='<c:out value="${numberOfChild}"/>'><c:out value="${status.current.requestedQty}"/></td>
                  <td width="20%" rowspan='<c:out value="${numberOfChild}"/>'><input type="text" name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].revisedQuantity" id="revisedQuantity<c:out value="${dataCount}"/>" value="<c:out value="${status.current.revisedQuantity}"/>" size="8" maxlength="10" class="inputBox" onchange="revisedQtyChanged('<c:out value="${dataCount}"/>','y')"></td>
                  <%-- building javascript array --%>
                  <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].requestedDateToDeliverForAudit" id="requestedDateToDeliverForAudit<c:out value="${dataCount}"/>" value="<c:out value="${status.current.requestedDateToDeliver}"/>" type="hidden">
                  <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].requestedQty" id="requestedQty<c:out value="${dataCount}"/>" type="hidden" value="<c:out value="${status.current.requestedQty}"/>">
                </c:otherwise>
              </c:choose>
            </c:otherwise>
          </c:choose>  <%-- end of first 3 columns  --%>
          <%-- building javascript array --%>
          <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].status" id="status<c:out value="${dataCount}"/>" type="hidden" value="<c:out value="${status.current.status}"/>">
          <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].rowType" id="rowType<c:out value="${dataCount}"/>" type="hidden" value="<c:out value="${status.current.rowType}"/>">
          <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].rowDeleted" id="rowDeleted<c:out value="${dataCount}"/>" type="hidden" value="<c:out value="${status.current.rowDeleted}"/>">
          <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].openQty" id="openQty<c:out value="${dataCount}"/>" type="hidden" value="<c:out value="${status.current.openQty}"/>">
          <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].displayRowId" id="displayRowId<c:out value="${dataCount}"/>" type="hidden" value="<c:out value="${status.index}"/>">
          <input name="scheduleDeliveryInputBean[<c:out value="${status.index}"/>].maxRowIdForDisplayRowId" id="maxRowIdForDisplayRowId<c:out value="${status.index}"/><c:out value="${childRowCount}"/>" type="hidden" value="<c:out value="${dataCount}"/>">
          <c:choose>
            <c:when test="${status.current.status == 'Not Issued'}">
              <c:if test="${requestLineStatus != 'Draft'}" >
              <td width="20%"></td>
              <td width="20%"></td>
              </c:if>
              </tr>
              <%-- building javascript array --%>
              <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].refDate" id="refDate<c:out value="${dataCount}"/>" type="hidden" value="<c:out value=""/>">
              <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].refQuantity" id="refQuantity<c:out value="${dataCount}"/>" type="hidden" value="<c:out value=""/>">
              <c:set var="childRowCount" value='${childRowCount+1}'/>
              <c:set var="dataCount" value='${dataCount+1}'/>
            </c:when>
            <c:otherwise>
              <c:set var="firstRow" value="${true}"/>
              <c:set var="childRowCount" value="${0}"/>
              <c:forEach var="innerDeliverySummaryBean" items="${deliveredInfoColl}" varStatus="status2">
                <c:if test='${! firstRow}'>
                  <tr class="<c:out value="${colorClass}"/>" id="childRowId<c:out value="${status.index}"/><c:out value="${childRowCount}"/>" onmouseup="selectRow('<c:out value="${status.index}"/>','<c:out value="${dataCount}"/>')">
                  <%-- building javascript array --%>
                  <%-- keeping data in a flat view: repeat for child record--%>
                  <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].requestedDateToDeliver" id="requestedDateToDeliver<c:out value="${dataCount}"/>" type="hidden" value="<c:out value="${formattedRequestedDate}"/>">
                  <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].requestedQty" id="requestedQty<c:out value="${dataCount}"/>" type="hidden" value="<c:out value="${status.current.requestedQty}"/>">
                  <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].revisedQuantity" id="revisedQuantity<c:out value="${dataCount}"/>" type="hidden" value="<c:out value=""/>">
                  <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].status" id="status<c:out value="${dataCount}"/>" type="hidden" value="<c:out value="${status.current.status}"/>">
                  <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].rowType" id="rowType<c:out value="${dataCount}"/>" type="hidden" value="duplicated">
                  <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].rowDeleted" id="rowDeleted<c:out value="${dataCount}"/>" type="hidden" value="<c:out value="${status.current.rowDeleted}"/>">
                  <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].openQty" id="openQty<c:out value="${dataCount}"/>" type="hidden" value="<c:out value="${status.current.openQty}"/>">
                  <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].displayRowId" id="displayRowId<c:out value="${dataCount}"/>" type="hidden" value="<c:out value="${status.index}"/>">
                  <input name="scheduleDeliveryInputBean[<c:out value="${status.index}"/>].maxRowIdForDisplayRowId" id="maxRowIdForDisplayRowId<c:out value="${status.index}"/><c:out value="${childRowCount}"/>" type="hidden" value="<c:out value="${dataCount}"/>">
                </c:if>
                <c:if test="${requestLineStatus != 'Draft'}" >         
                <c:choose>
                  <c:when test="${status2.current.refDate == null}">
                    <td width="20%"><c:out value="${status.current.status}"/></td>
                  </c:when>
                  <c:otherwise>
                    <fmt:formatDate var="formatteddateDelivered" value="${status2.current.refDate}" pattern="${dateFormatPattern}"/>
                    <td width="20%"><c:out value="${formatteddateDelivered}"/></td>
                    <%-- building javascript array --%>
                    <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].refDate" id="refDate<c:out value="${dataCount}"/>" type="hidden" value="<c:out value="${formatteddateDelivered}"/>">
                  </c:otherwise>
                </c:choose>
                <td width="20%"><c:out value="${status2.current.refQuantity}"/></td>
                </c:if>
                </tr>
                <%-- building javascript array --%>
                <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].refQuantity" id="refQuantity<c:out value="${dataCount}"/>" type="hidden" value="<c:out value="${status2.current.refQuantity}"/>">
                <c:set var="childRowCount" value='${childRowCount+1}'/>
                <c:set var="dataCount" value='${dataCount+1}'/>
                <c:set var="firstRow" value="${false}"/>
              </c:forEach>
            </c:otherwise>
          </c:choose>

        </c:when>
        <%-- non-editable view --%>
        <c:otherwise>
          <c:set var="deliveredInfoColl"  value='${status.current.deliveredInfoColl}'/>
          <c:set var="numberOfChild" value="${1}"/>
          <bean:size id="numberOfChild" name="deliveredInfoColl"/>
            <c:if test='${numberOfChild == 0}'>
                <c:set var="numberOfChild" value="${1}"/>
            </c:if>
          <c:set var="childRowCount" value="${0}"/>
          <fmt:formatDate var="formattedRequestedDate" value="${status.current.requestedDateToDeliver}" pattern="${dateFormatPattern}"/>
          <td width="20%" rowspan='<c:out value="${numberOfChild}"/>'><c:out value="${formattedRequestedDate}"/></td>
          <td width="20%" rowspan='<c:out value="${numberOfChild}"/>'><c:out value="${status.current.requestedQty}"/></td>
          <c:if test="${requestLineStatus != 'Draft'}" >
          <td width="20%" rowspan='<c:out value="${numberOfChild}"/>'></td>
          </c:if>
          <%-- building javascript array --%>
          <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].requestedDateToDeliver" id="requestedDateToDeliver<c:out value="${dataCount}"/>" type="hidden" value="<c:out value="${formattedRequestedDate}"/>">
          <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].requestedQty" id="requestedQty<c:out value="${dataCount}"/>" type="hidden" value="<c:out value="${status.current.requestedQty}"/>">
          <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].revisedQuantity" id="revisedQuantity<c:out value="${dataCount}"/>" type="hidden" value="<c:out value=""/>">
          <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].status" id="status<c:out value="${dataCount}"/>" type="hidden" value="<c:out value="${status.current.status}"/>">
          <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].rowType" id="rowType<c:out value="${dataCount}"/>" type="hidden" value="<c:out value="${status.current.rowType}"/>">
          <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].rowDeleted" id="rowDeleted<c:out value="${dataCount}"/>" type="hidden" value="<c:out value="${status.current.rowDeleted}"/>">
          <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].openQty" id="openQty<c:out value="${dataCount}"/>" type="hidden" value="<c:out value="${status.current.openQty}"/>">
          <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].displayRowId" id="displayRowId<c:out value="${dataCount}"/>" type="hidden" value="<c:out value="${status.index}"/>">
          <input name="scheduleDeliveryInputBean[<c:out value="${status.index}"/>].maxRowIdForDisplayRowId" id="maxRowIdForDisplayRowId<c:out value="${status.index}"/><c:out value="${childRowCount}"/>" type="hidden" value="<c:out value="${dataCount}"/>">
          <c:choose>
            <c:when test="${status.current.status == 'Not Issued'}">
              <c:if test="${requestLineStatus != 'Draft'}" >
              <td width="20%">&nbsp;</td>
              <td width="20%">&nbsp;</td>
              </c:if>
              </tr>
              <%-- building javascript array --%>
              <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].refDate" id="refDate<c:out value="${dataCount}"/>" type="hidden" value="<c:out value=""/>">
              <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].refQuantity" id="refQuantity<c:out value="${dataCount}"/>" type="hidden" value="<c:out value=""/>">
              <c:set var="childRowCount" value='${childRowCount+1}'/>
              <c:set var="dataCount" value='${dataCount+1}'/>
            </c:when>
            <c:otherwise>
              <c:set var="firstRow" value="${true}"/>
              <c:forEach var="innerDeliverySummaryBean" items="${deliveredInfoColl}" varStatus="status2">
                <c:if test='${! firstRow}'>
                  <tr class="<c:out value="${colorClass}"/>" id="childRowId<c:out value="${status.index}"/><c:out value="${childRowCount}"/>" onmouseup="selectRow('<c:out value="${status.index}"/>','<c:out value="${dataCount}"/>')">
                  <%-- building javascript array --%>
                  <%-- keeping data in a flat view: repeat for child record--%>
                  <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].requestedDateToDeliver" id="requestedDateToDeliver<c:out value="${dataCount}"/>" type="hidden" value="<c:out value="${formattedRequestedDate}"/>">
                  <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].requestedQty" id="requestedQty<c:out value="${dataCount}"/>" type="hidden" value="<c:out value="${status.current.requestedQty}"/>">
                  <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].revisedQuantity" id="revisedQuantity<c:out value="${dataCount}"/>" type="hidden" value="<c:out value=""/>">
                  <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].status" id="status<c:out value="${dataCount}"/>" type="hidden" value="<c:out value="${status.current.status}"/>">
                  <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].rowType" id="rowType<c:out value="${dataCount}"/>" type="hidden" value="duplicated">
                  <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].rowDeleted" id="rowDeleted<c:out value="${dataCount}"/>" type="hidden" value="<c:out value="${status.current.rowDeleted}"/>">
                  <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].openQty" id="openQty<c:out value="${dataCount}"/>" type="hidden" value="<c:out value="${status.current.openQty}"/>">
                  <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].displayRowId" id="displayRowId<c:out value="${dataCount}"/>" type="hidden" value="<c:out value="${status.index}"/>">
                  <input name="scheduleDeliveryInputBean[<c:out value="${status.index}"/>].maxRowIdForDisplayRowId" id="maxRowIdForDisplayRowId<c:out value="${status.index}"/><c:out value="${childRowCount}"/>" type="hidden" value="<c:out value="${dataCount}"/>">
                </c:if>
                <c:if test="${requestLineStatus != 'Draft'}" > 
                <c:choose>
                  <c:when test="${status2.current.refDate == null}">
                    <td width="20%">&nbsp;</td>
                    <td width="20%">&nbsp;</td>
                  </c:when>
                  <c:otherwise>
                    <fmt:formatDate var="formatteddateDelivered" value="${status2.current.refDate}" pattern="${dateFormatPattern}"/>
                    <td width="20%"><c:out value="${formatteddateDelivered}"/></td>
                    <%-- building javascript array --%>
                    <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].refDate" id="refDate<c:out value="${dataCount}"/>" type="hidden" value="<c:out value="${formatteddateDelivered}"/>">
                    <td width="20%"><c:out value="${status2.current.refQuantity}"/></td>
                  </c:otherwise>
                </c:choose>

                </c:if>
                </tr>
                <%-- building javascript array --%>
                <input name="scheduleDeliveryInputBean[<c:out value="${dataCount}"/>].refQuantity" id="refQuantity<c:out value="${dataCount}"/>" type="hidden" value="<c:out value="${status2.current.refQuantity}"/>">
                <c:set var="childRowCount" value='${childRowCount+1}'/>
                <c:set var="dataCount" value='${dataCount+1}'/>
                <c:set var="firstRow" value="${false}"/>
              </c:forEach>
            </c:otherwise>
          </c:choose>
        </c:otherwise>   <%-- end of non-editable view --%>
      </c:choose>

   <!-- hidden columns -->
   <input type="hidden" name="colorClass<c:out value="${status.index}"/>" id="colorClass<c:out value="${status.index}"/>" value="<c:out value="${colorClass}"/>" >
   <input type="hidden" name="numberOfChild<c:out value="${status.index}"/>" id="numberOfChild<c:out value="${status.index}"/>" value="<c:out value="${childRowCount}"/>" >
   <!-- end of hidden columns -->
  </c:forEach>
 </table>
</c:if>

   <!-- If the collection is empty say no data found -->
   <c:choose>
    <c:when test="${empty deliverySummaryBeanCollection  && requestLineStatus == 'Draft'}" >
    <table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <th width="20%"><fmt:message key="deliveryschedule.label.scheduled"/></th>
        <th width="20%"><fmt:message key="deliveryschedule.label.currentqty"/></th>
      </tr>
    </table>
    </c:when>
    <c:when test="${empty deliverySummaryBeanCollection}" >
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
     <tr>
      <td width="100%">
       <fmt:message key="main.nodatafound"/>
      </td>
     </tr>
    </table>
    </c:when>
   </c:choose>
<!-- Search results end -->
</c:if>

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="action" id="action" type="hidden" value="">
<input name="companyId" id="companyId" type="hidden"value="<c:out value="${companyId}"/>">
<input name="prNumber" id="prNumber" type="hidden" value="<c:out value="${prNumber}"/>">
<input name="lineItem" id="lineItem" type="hidden"value="<c:out value="${lineItem}"/>">
<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">
<input name="maxRowId" id="maxRowId" value="<c:out value="${dataCount}"/>" type="hidden">
<input name="totalOrderedQuantity" id="totalOrderedQuantity" value="" type="hidden">
<input name="requestLineStatus" id="requestLineStatus" value="<c:out value="${requestLineStatus}"/>" type="hidden">
<input name="requestor" id="requestor" value="<c:out value="${requestor}"/>" type="hidden">
<input name="medianMrLeadTime" id="medianMrLeadTime" value="<c:out value="${medianMrLeadTime}"/>" type="hidden">
<input name="userChangedData" id="userChangedData" value="<c:out value="${userChangedData}"/>" type="hidden">
<input name="facilityId" id="facilityId" value="<c:out value="${facilityId}"/>" type="hidden">
<input name="userViewType" id="userViewType" value="<c:out value="${userViewType}"/>" type="hidden">
<input name="source" id="source" type="hidden"value="<c:out value="${param.source}"/>">
<input name="requestLineStatus" id="requestLineStatus" type="hidden" value="${param.requestLineStatus}"/>
<input name="requestor" id="requestor" type="hidden" value="${param.requestor}"/>
<input name="lastAction" id="lastAction" type="hidden" value="${param.action}"/>
<input type="hidden" name="todaysDate" id="todaysDate" value="<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>"/>    
<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
<%--
<tcmis:saveRequestParameter/>
--%>

<input name="calendarAction" id="calendarAction" type="hidden" value=""/>

<%--This is the minimum height of the result section you want to display--%>
<input name="minHeight" id="minHeight" type="hidden" value="200">

 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>

</body>
</html:html>