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
<script type="text/javascript" src="/js/pagename.js"></script>

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
<fmt:message key="label.item"/> <fmt:message key="label.management"/>
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
<%--<tcmis:form action="/pagenameresults.do" onsubmit="return submitFrameOnlyOnce();">--%>

<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->
<!--Uncomment for production-->
<%--
<tcmis:permission indicator="true" userGroupId="pageNameUserGroup" facilityId="${param.testingField}">
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
<%--<c:if test="${pageNameViewBeanCollection != null}" >--%>
<!-- Search results start -->

<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>

 <!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
 <!--Uncomment for production-->
 <%--<c:if test="${!empty pageNameViewBeanCollection}" >--%>

    <table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">
    <!--Uncomment for production-->
    <%--<c:forEach var="pageNameViewBean" items="${pageNameViewBeanCollection}" varStatus="status">--%>

    <!--Uncomment for production-->
    <%--<c:if test="${status.index % 10 == 0}">--%>
    <!-- Need to print the header every 10 rows-->
    <tr>
<th width="2%"><fmt:message key="label.ok"/></th>
<th width="5%"><fmt:message key="label.inventorygroup"/></th>
<th width="5%" ><fmt:message key="label.catalogpartnumber"/></th>
<th width="5%" ><fmt:message key="label.setpointsrp/sl/rq"/></th>
<th width="5%" ><fmt:message key="label.receiptprocessingmethod"/></th>
<th width="5%" ><fmt:message key="label.billingmethod"/></th>
<th width="5%" ><fmt:message key="label.stockingmethod"/></th>
<th width="5%" ><fmt:message key="label.usagefacility"/></th>
<th width="5%" ><fmt:message key="label.usageworkarea"/></th>
<th width="5%" ><fmt:message key="label.countuom"/></th>
<th width="5%" ><fmt:message key="label.inventoryuom"/></th>
<th width="5%" ><fmt:message key="label.onhand(lastcounted)"/></th>
<th width="5%" ><fmt:message key="label.inpurchasing"/></th>
<th width="5%" ><fmt:message key="label.item"/></th>
<th width="20%"><fmt:message key="label.itemdescription"/></th>
<th width="5%" ><fmt:message key="label.replenishqty"/></th>
<th width="5%" ><fmt:message key="label.needdatemm/dd/yyyy"/></th>
<th width="5%" ><fmt:message key="label.status"/></th>
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
<td width="2%">
	<input type="checkbox" value="Yes" name="ok<c:out value="${status.index}"/>" id="ok<c:out value="${status.index}"/>">
</td>
<td width="5%"><c:out value="${status.current.inventoryGroup}"/></td>
<td width="5%">
	<a href="javascript:showMinMax('05DANA-10838','Cape Girardeau','1','Tote')">
		<c:out value="${status.current.catalogpartnumber}"/>
	</a>
</td>
<td width="5%" ><c:out value="${status.current.setPointsRpSlRq}"/></td>
<td width="5%" ><c:out value="${status.current.receiptProcessingMethod}"/></td>
<td width="5%" ><c:out value="${status.current.billingMethod}"/></td>
<td width="5%" ><c:out value="${status.current.stockingMethod}"/></td>
<td width="5%" ><c:out value="${status.current.usageFacility}"/></td>
<td width="5%" ><c:out value="${status.current.usageWorkarea}"/></td>
<td width="5%" ><c:out value="${status.current.countUom}"/></td>
<td width="5%" ><c:out value="${status.current.inventoryUom}"/></td>
<td width="5%" ><c:out value="${status.current.onhandLastcounted}"/></td>
<td width="5%" ><c:out value="${status.current.inpurchasing}"/></td>
<td width="5%" ><c:out value="${status.current.item}"/></td>
<td width="20%"><c:out value="${status.current.itemDescription}"/></td>
<td width="5%" >
	<input type="text" class="inputBox" onChange="CheckQtyValue('1')" name="replenishQty<c:out value="${status.index}"/>" id="replenishQty<c:out value="${status.index}"/>" value="<c:out value="${status.current.replenishQty}"/>" maxLength="10" size="3"/>
</td>
<td width="5%" >
	<input type="text" class="inputBox" onChange="CheckQtyValue('1')" name="needDate<c:out value="${status.index}"/>" id="needDate<c:out value="${status.index}"/>" value="<c:out value="${status.current.needDate}"/>" maxLength="10" size="6"/>
	<a href="javascript: void(0);" id="linkneedDate<c:out value="${status.index}"/>" onClick="return getCalendar(document.iteminventory.needDate<c:out value="${status.index}"/>);" class="datePopUpLink">
		&diams;
	</a>
</td>
<td width="5%" >
<!-- keep the java script onChange for now.. -->
<select class="selectBox" name="status<c:out value="${status.index}"/>" id="status<c:out value="${status.index}"/>" onChange= "checkactivestatus('1')">
    <c:choose>
     <c:when test="${status.current.status == 'A' }" >
		<option selected="selected" value="A">
			<fmt:message key="label.active"/>
		</option>
		<option value="I">
			<fmt:message key="label.inactive"/>
		</option>
     </c:when>
     <c:otherwise>
		<option value="A">
			<fmt:message key="label.active"/>
		</option>
		<option selected="selected" value="I">
			<fmt:message key="label.inactive"/>
		</option>
     </c:otherwise>
    </c:choose>
</select>
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
   <%--<c:if test="${empty pageNameViewBeanCollection}" >--%>

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