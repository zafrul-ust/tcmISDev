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
<script type="text/javascript" src="/js/suppliershipping.js"></script>

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
<fmt:message key="label.suppliershipping"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="myOnload()">

<!--Uncomment for production-->
<%--<tcmis:form action="/suppliershipping.do" onsubmit="return submitFrameOnlyOnce();">--%>

<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->
<!--Uncomment for production-->
<%--
<tcmis:permission indicator="true" userGroupId="suppliershippingUserGroup" facilityId="${param.testingField}">
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
 //-->
 </script>
</tcmis:permission>
--%>
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

<div class="interface" id="resultsPage"> <!-- start of interface -->
<div class="backGroundContent"> <!-- start of backGroundContent -->

<!--Uncomment for production-->
<%--<c:if test="${suppliershippingViewBeanCollection != null}" >--%>
<!-- Search results start -->

<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>

 <!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
 <!--Uncomment for production-->
 <%--<c:if test="${!empty suppliershippingViewBeanCollection}" >--%>

    <table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">
    <!--Uncomment for production-->
    <%--<c:forEach var="suppliershippingViewBean" items="${suppliershippingViewBeanCollection}" varStatus="status">--%>

    <!--Uncomment for production-->
    <%--<c:if test="${status.index % 10 == 0}">--%>
    <!-- Need to print the header every 10 rows-->

    <%--Place the table header here --%>

		<tr>
 			<th width="5%"><fmt:message key="label.haaspoline"/></th>
    		<th width="5%"><fmt:message key="label.promiseddate"/></th>
    		<th width="7%"><fmt:message key="label.openqty"/></th>
    		<th width="5%"><fmt:message key="label.packaging"/></th>
    		<th width="12%"><fmt:message key="label.description"/></th>
    		<th width="12%"><fmt:message key="label.requestorwadt"/></th>
    		<th width="12%"><fmt:message key="label.mrlineuserpo"/></th>
    		<th width="12%"><fmt:message key="label.shipto"/></th>
    		<th width="2%"><fmt:message key="label.nrlabels"/></th>
    		<th width="2%"><fmt:message key="label.ok"/></th>
    		<th width="5%"><fmt:message key="label.shipdatemmddyyyy"/></th>
    		<th width="5%"><fmt:message key="label.receiptdatemmddyyyy"/></th>
    		<th width="8%"><fmt:message key="label.lot"/>
    		<th width="5%"><fmt:message key="label.expdatemmddyyyy"/></th>
    		<th width="5%"><fmt:message key="label.qtyshipped"/></th>
    		<th width="5%"><fmt:message key="label.duplicate"/></th>
		</tr>
    

    <!--Uncomment for production-->
    <%--</c:if>--%>

    <!--Uncomment for production-->
    <%--
    <c:choose>
     <c:when test="${status.index % 2 == 0}" >
      <c:set var="colorClass" value=''/>
     </c:when>
     <c:otherwise>
      <c:set var="colorClass" value='alt'/>
     </c:otherwise>
    </c:choose>
    --%>

    <tr class="<c:out value="${colorClass}"/>" id="rowId<c:out value="${status.index}"/>">
     	
     	<td width="5%"><c:out value="${status.current.haasPOLine}"/></td>
     	<td width="5%"><c:out value="${status.current.promisedDate}"/></td>
     	<td width="7%"><c:out value="${status.current.openQty}"/></td>
     	<td width="5%"><c:out value="${status.current.packaging}"/></td>
     	<td width="12%"><c:out value="${status.current.description}"/></td>
     	<td width="12%"><c:out value="${status.current.requestorWadt}"/></td>
     	<td width="12%"><c:out value="${status.current.mrLineUserPO}"/></td>
     	<td width="12%"><c:out value="${status.current.shipTo}"/></td>
     	<td width="2%"><input class="inputBox" type="text" name="nrLabels" id="nrLabels" value="<c:out value="${status.current.nrLabels}"/>"></td>
     	<td width="2%"><input class="radioBtns" type="checkBox" name="ok" id="ok" ><c:out value="${status.current.ok}"/></td>
     	<td width="5%">
   			<input class="invisibleClass" type="text" name="shipDateMmddyyyy" id="shipDateMmddyyyy" value="" size="10" readonly>
   			<a href="javascript: void(0);" class="datePopUpLink" name="shipDateDateLink" id="shipDateDateLink" &diams;</a> 
     	<td width="5%">
   			<input class="invisibleClass" type="text" name="receiptDateMmddyyyy" id="receiptDateMmddyyyy" value="" size="10" readonly>
   			<a href="javascript: void(0);" class="datePopUpLink" name="receiptDateLink" id="receiptDateLink" &diams;</a> 
     	<td width="8%"><c:out value="${status.current.lot}"/></td>
     	<td width="5%"><c:out value="${status.current.expDateMmddyyyy}"/></td>
     	<td width="5%"><input class="inputBox" type="text" name="qtyShipped" id="qtyShipped" value="<c:out value="${status.current.qtyShipped}"/>"></td>
     	<td width="5%"><input name="duplicateButton" id="duplicateButton" type="button" value="<fmt:message key="label.dupl"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
		</td>
     	
    </tr>

   <c:set var="dataCount" value='${dataCount+1}'/>

   <!--Uncomment for production-->
   <%--</c:forEach>--%>
   </table>
   <!--Uncomment for production-->
   <%--</c:if>--%>
   <!-- If the collection is empty say no data found -->

   <!--Uncomment for production-->
   <%--<c:if test="${empty suppliershippingViewBeanCollection}" >--%>

    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
     <tr>
      <td width="100%">
       <fmt:message key="main.nodatafound"/>
      </td>
     </tr>
    </table>
   <!--Uncomment for production-->
   <%--</c:if>--%>

<!-- Search results end -->
<!--Uncomment for production-->
<%--</c:if>--%>

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">

<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
<tcmis:saveRequestParameter/>

 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

<!--Uncomment for production-->
<%--</tcmis:form>--%>
</body>
</html:html>