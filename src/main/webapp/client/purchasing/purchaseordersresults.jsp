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
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>
<script src="/js/calendar/CalendarPopup.js" language="JavaScript"></script>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/client/purchasing/purchaseordersresults.js"></script>

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
<fmt:message key="purchaseorders.label.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
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

<body bgcolor="#ffffff" onload="myResultOnload()">

<tcmis:form action="/purchaseordersresults.do" onsubmit="return submitFrameOnlyOnce();">

<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->
 <script type="text/javascript">
 <!--
  showUpdateLinks = ${canEditPurchaseOrder};
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

<c:if test="${poHeaderViewBeanCollection != null}" >
<!-- Search results start -->

<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>

 <!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
 <c:if test="${!empty poHeaderViewBeanCollection}" >

		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults" id="resultsPageTable">
		 <c:set var="colorClass" value=''/>
		 <c:set var="dataCount" value='${0}'/>

		 <c:forEach var="poHeaderViewBean" items="${poHeaderViewBeanCollection}" varStatus="status">
			<c:if test="${status.index % 10 == 0}">
			 <tr class="">
				 <th width="2%" ><fmt:message key="label.ok"/></th>
				 <th width="2%"><fmt:message key="label.inventorygroup"/></th>
				 <th width="2%"><fmt:message key="label.buyer"/></th>
				 <th width="2%"><fmt:message key="label.catpartno"/></th>
				 <th width="5%"><fmt:message key="label.pkg"/></th>
				 <th width="5%"><fmt:message key="label.itemid"/></th>
				 <th width="15%"><fmt:message key="label.itemdesc"/></th>
				 <th width="2%"><fmt:message key="label.po"/></th>
				 <th width="5%"><fmt:message key="label.qty"/></th>
				 <th width="5%" ><fmt:message key="label.promiseddate"/></th>
				 <th width="5%" ><fmt:message key="label.supplier"/></th>
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

<%--
			<c:set var="critical" value='${status.current.critical}'/>
			<c:if test="${critical == 'Y' || critical == 'y'}">
			 <c:set var="colorClass" value='red'/>
			</c:if>

			<c:if test="${critical == 'S' || critical == 's'}">
			 <c:set var="colorClass" value='pink'/>
			</c:if>
--%>
				<c:set var="readOnly" value='${status.current.readOnly}'/>
				<tr class="${colorClass}" id="rowId${status.index}">
					<%-- hidden values --%>
					<input type="hidden" name="BuyOrdersInputBean[${dataCount}].docNum" id="docNum${dataCount}" value="${status.current.docNum}">
					<input type="hidden" name="BuyOrdersInputBean[${dataCount}].customerPoNumber" id="customerPoNumber${dataCount}" value="${status.current.poNumber}">

					<c:choose>
						<c:when test="${readOnly == 'N'}">
							<td width="2%">
								<input type="checkbox" name="BuyOrdersInputBean[${dataCount}].ok" id="ok${dataCount}" value="${dataCount}" onclick="checkBuyorder(${dataCount})" onchange="checkBuyorder(${dataCount})" >
							</td>
						</c:when>
						<c:otherwise>
							<td width="2%">&nbsp;</td>
						</c:otherwise>
					</c:choose>
					<td width="5%">${status.current.inventoryGroupName}</td>
					<td width="5%">${status.current.buyerName}</td>
					<td width="5%">${status.current.catPartNo}</td>
					<td width="5%">${status.current.packaging}</td>
					<td width="5%">${status.current.itemId}</td>
					<td width="5%">${status.current.itemDesc}</td>
					<td width="5%">${status.current.poNumber}</td>
					<c:choose>
						<c:when test="${readOnly == 'N'}">
							<td width="5%">
							 <input type="text" name="BuyOrdersInputBean[${dataCount}].orderQuantity" id="orderQuantity${dataCount}" value="${status.current.quantityToReceive}" size="4" maxlength="10" class="inputBox">
							</td>
							<td width="5%">
								<fmt:formatDate var="formattedPromisedDate" value="${status.current.expectedDeliveryDate}" pattern="MM/dd/yyyy"/>
								<input type="text" name="BuyOrdersInputBean[<c:out value="${status.index}"/>].promisedDate" id="promisedDate<c:out value="${status.index}"/>" value="${formattedPromisedDate}" size="8" maxlength="10" class="inputBox" readonly><a href="javascript: void(0);" class="optionTitleBold" id="linkpromisedDate<c:out value="${status.index}"/>" onclick="return getCalendar(document.genericForm.promisedDate<c:out value="${status.index}"/>);">&diams;</a>
							</td>
							<td width="5%">
							 <input type="text" name="BuyOrdersInputBean[${dataCount}].lastSupplier" id="lastSupplier${dataCount}" value="${status.current.supplierName}" size="15" maxlength="60" class="inputBox">
							</td>
						</c:when>
						<c:otherwise>
							<td width="5%">${status.current.quantityToReceive}</td>
							<fmt:formatDate var="formattedPromisedDate" value="${status.current.expectedDeliveryDate}" pattern="MM/dd/yyyy"/>
							<td width="5%">${formattedPromisedDate}</td>
							<td width="5%">${status.current.supplierName}</td>
						</c:otherwise>
					</c:choose>
				</tr>
			  <c:set var="dataCount" value='${dataCount+1}'/>
			</c:forEach>
    </table>
   </c:if>

   <!-- If the collection is empty say no data found -->
   <c:if test="${empty poHeaderViewBeanCollection}" >
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

<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
<tcmis:saveRequestParameter/>

 </div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>