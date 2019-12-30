<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

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

<%@ include file="/common/locale.jsp" %>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss />
<!-- CSS for YUI -->
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
<!-- Add any other stylesheets you need for the page here -->

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<script type="text/javascript" src="/js/common/resultiframeresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<%--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/ordertracking/polchemordertracking.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>

<!-- This is for the YUI, uncomment if you will use this -->
<%--<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>--%>

<title>
<fmt:message key="polchemordertracking.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages

with(milonic=new menuname("showDetails")){
 top="offset=2"
 style = contextStyle;
 margin=3
 aI("text=<fmt:message key="label.detail"/>;url=javascript:showDetailsReport();");
}

drawMenus();

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
itemInteger:"<fmt:message key="error.item.integer"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="myOnload()">

<tcmis:form action="/polchemordertrackingresults.do" onsubmit="return submitFrameOnlyOnce();">

<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
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

<c:if test="${polchemOrderStatusViewBeanCollection != null}" >
<!-- Search results start -->

<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>
<c:set var="poCount" value='0'/>
<c:set var="pre" value=''/>
<c:set var="curr" value=''/>
 <!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
 <c:if test="${!empty polchemOrderStatusViewBeanCollection}" >

    <table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">
    <c:forEach var="polchemOrderStatusViewBean" items="${polchemOrderStatusViewBeanCollection}" varStatus="status">
    <c:set var="curr" value='${status.current.poNumber}'/>
    <c:if test="${(poCount % 10 == 0) &&(pre ne curr)}">
    <!-- Need to print the header every 10 rows-->
    <tr>
    <th width="5%"><fmt:message key="label.milstrip"/></th>    
    <th width="5%"><fmt:message key="label.nsn"/></th>       
    <th width="5%"><fmt:message key="label.ownercompanyid"/></th>
    <th width="5%"><fmt:message key="label.orderreceived"/></th>
    <th width="5%"><fmt:message key="label.haasordercreated"/></th>
    <th width="3%"><fmt:message key="label.dpmsstatus"/></th>
    <th width="10%"><fmt:message key="label.dpmsreleasedate"/></th>    
    <th width="5%"><fmt:message key="label.inventorygroup"/></th>
    <th width="5%"><fmt:message key="label.haasordernumber"/></th>
    <th width="5%"><fmt:message key="label.status"/></th>
    <th width="5%"><fmt:message key="label.qty"/></th>
    <th width="15%"><fmt:message key="label.allocateddate"/></th>
    <th width="15%"><fmt:message key="label.backordereddate"/></th>
    <th width="5%"><fmt:message key="label.freightadvicesent"/></th>
    <th width="5%"><fmt:message key="label.freightadvicereceived"/></th>
    <th width="5%"><fmt:message key="label.picklistprintdate"/></th>
    <th width="10%"><fmt:message key="label.packeddate"/></th>
    <th width="5%"><fmt:message key="label.dateshipped"/></th>
    <th width="5%"><fmt:message key="label.shipconfirmsent"/></th>
    </tr>
    </c:if>

    <c:if test="${pre ne curr}">
	 <c:set var="poCount" value='${poCount+1}'/>
	 <script type="text/javascript">
 <!--
//  map will change per 'PO'
	map = new Array();
 //-->
 </script>
	</c:if>
	<script type="text/javascript">
   <!--
    lineMap[${status.index}] = map;
      map[map.length] = ${status.index};
   //-->
   </script>


    <c:choose>
     <c:when test="${poCount % 2 == 0}" >
      <c:set var="colorClass" value=''/>
     </c:when>
     <c:otherwise>
      <c:set var="colorClass" value='alt'/>
     </c:otherwise>
    </c:choose>

    <tr class="${colorClass}" id="rowId${status.index}" onmouseup="selectRow('${status.index}')">
     <c:if test="${pre ne curr}">
     <td width="5%" rowspan="${rowCountPart[curr]}">${status.current.poNumber}</td>     
     <td width="5%" rowspan="${rowCountPart[curr]}">${status.current.facPartNo}</td>          
     <td width="5%" rowspan="${rowCountPart[curr]}">${status.current.ownerCompanyId}</td>
      <td width="5%" rowspan="${rowCountPart[curr]}">
        <fmt:formatDate var="fmtorderReceivedDate" value="${status.current.orderReceivedDate}" pattern="${dateTimeFormatPattern}"/>
        <c:out value="${fmtorderReceivedDate}"/>&nbsp;
     </td>
      <td width="5%" rowspan="${rowCountPart[curr]}">
        <fmt:formatDate var="fmtorderCreatedDate" value="${status.current.orderCreatedDate}" pattern="${dateTimeFormatPattern}"/>
        <c:out value="${fmtorderCreatedDate}"/>&nbsp;
     </td>
     <td width="3%" rowspan="${rowCountPart[curr]}">${status.current.orderStatus}</td>
      <td width="5%" rowspan="${rowCountPart[curr]}">
        <fmt:formatDate var="fmtreleasedDate" value="${status.current.releasedDate}" pattern="${dateTimeFormatPattern}"/>
        ${fmtreleasedDate}
     </td>
     </c:if>
     <td width="5%">${status.current.inventoryGroup}</td>
     <td width="5%"><c:out value="${status.current.prNumber}"/>-<c:out value="${status.current.lineItem}"/></td>     
     <td width="5%">${status.current.currentStatus}</td>
     <td width="5%">${status.current.quanty}</td>
      <td width="5%">
        <fmt:formatDate var="fmtallocatedDate" value="${status.current.allocatedDate}" pattern="${dateTimeFormatPattern}"/>
        ${fmtallocatedDate}
     </td>
      <td width="5%">
        <fmt:formatDate var="fmtbackorderedDate" value="${status.current.backorderedDate}" pattern="${dateTimeFormatPattern}"/>
        ${fmtbackorderedDate}
     </td>
      <td width="5%">
        <fmt:formatDate var="fmtfaSentDate" value="${status.current.faSentDate}" pattern="${dateTimeFormatPattern}"/>
       ${fmtfaSentDate}
     </td>
      <td width="5%">
        <fmt:formatDate var="fmtfaReceivedDate" value="${status.current.faReceivedDate}" pattern="${dateTimeFormatPattern}"/>
        ${fmtfaReceivedDate}
     </td>
      <td width="5%">
        <fmt:formatDate var="fmtpicklistPrintDate" value="${status.current.picklistPrintDate}" pattern="${dateTimeFormatPattern}"/>
        ${fmtpicklistPrintDate}
     </td>
      <td width="5%">
        <fmt:formatDate var="fmtpackedDate" value="${status.current.packedDate}" pattern="${dateTimeFormatPattern}"/>
        ${fmtpackedDate}
     </td>
     <td width="5%">
        <fmt:formatDate var="fmtshippedDate" value="${status.current.shippedDate}" pattern="${dateFormatPattern}"/>
        ${fmtshippedDate}
     </td>
     <td width="5%">
        <fmt:formatDate var="fmtscSentDatee" value="${status.current.scSentDate}" pattern="${dateTimeFormatPattern}"/>
        ${fmtscSentDate}
     </td>
   </tr>
   <input type="hidden" name="colorClass<c:out value="${status.index}"/>" id="colorClass<c:out value="${status.index}"/>" value="${colorClass}" />
   
   <c:set var="dataCount" value='${dataCount+1}'/>
   <c:set var="pre" value='${status.current.poNumber}'/>
   </c:forEach>
   </table>
   </c:if>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty polchemOrderStatusViewBeanCollection}" >

    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
     <tr>
      <td width="100%">
       <fmt:message key="main.nodatafound"/>
      </td>
     </tr>
    </table>
   </c:if>

<!-- Search results end -->
</c:if>

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">
<input name="action" id="action" type="hidden">
<input name="packingGroupId" id="packingGroupId" type="hidden">  
<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
<!--<tcmis:saveRequestParameter/>

 --></div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>