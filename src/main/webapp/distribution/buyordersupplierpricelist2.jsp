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

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<script type="text/javascript" src="/js/common/iframeresizemain.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>

<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>
<script type="text/javascript" src="/js/calendar/calendarval.js"></script>
<script src="/js/catalog/qualitysummary.js" language="JavaScript"></script>

<title>
<fmt:message key="label.qualitysummary"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
all:"<fmt:message key="label.all"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"
};

 
 var paymentConfig = {
			divName:'SupplierAddPaymentTermsBean', // the div id to contain the grid of the data.
			beanData:'paymentData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
			beanGrid:'paymentGrid',     // the grid's name, as in beanGrid.attachEvent...
			config:'paymentconfig',	     // the column config var name, as in var config = { [ columnId:..,columnName...
			rowSpan:true,			 // this page has rowSpan > 1 or not.
			submitDefault:true,    // the fields in grid are defaulted to be submitted or not.
									// remember to call haasGrid.parentFormOnSubmit() before actual submit.
			onRowSelect:paymentSelectRow,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
			onRightClick:null,   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
		    singleClickEdit:false //This is for single click edidting. If it is set to true, txt column type will pop a txt editing box on sligne click. 
//			onBeforeSorting:_onBeforeSorting
		};

 
// -->
</script>

</head>

<body bgcolor="#ffffff" >
<div class="interface" id="mainPage" style="" onmousedown="toggleContextMenu('contextMenu');">
<div id="searchFrameDiv" class="contentArea">

<div class="roundcont filterContainer" style="width:100%;">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
			<b><fmt:message key="supplierPriceList"/>:</b>
		   <c:choose>
		   <c:when test="${empty dbuyViewBeanCollection}" >
			    <table width="95%" border="0" cellpadding="0" cellspacing="0" class="tableNoData">
			     <tr>
			      <td width="100%">
			       <fmt:message key="main.nodatafound"/>
			      </td>
			     </tr>
			    </table>
		   </c:when>
		   <c:otherwise>
			    <table width="95%" border="0" cellpadding="0" cellspacing="0" class="tableNoData">
			     <tr><td width="5%" nowrap><b><fmt:message key="label.catalogitem"/>:</b></td><td>${param.itemId}</td></tr>
			     <tr><td width="5%" nowrap><b><fmt:message key="label.description"/>:</b></td><td>${dbuyViewBeanCollection[0].itemDesc}</td></tr>
			     <tr><td width="5%" nowrap><b><fmt:message key="label.shipto"/>:</b></td><td>${param.shipToLocationId}</td></tr>
			     <tr><td width="5%" nowrap><b><fmt:message key="label.inventorygroup"/>:</b></td><td>${param.inventoryGroup}</td></tr>
			    </table>
		    <c:set var="colorClass" value='alt'/>
		   </c:otherwise>
		  </c:choose>
			
			 <c:if test="${empty dbuyViewBeanCollection}" >
			   </c:if>
		  <c:if test="${!empty dbuyViewBeanCollection}" >	  
          <table width="95%" border="0" cellpadding="0" cellspacing="0" class="tableResults">

          <c:set var="colorClass" value=''/>
          <c:set var="dataCount" value='${0}'/>
          
		   <tr>
		   <th width="5%"><fmt:message key="label.supplier"/></th>
		   <th width="5%"><fmt:message key="label.supplierpartnum"/></th>
		   <th width="5%"><fmt:message key="label.fromquantity"/></th>
		   <th width="10%"><fmt:message key="label.unitprice"/></th>
		   <th width="5%"><fmt:message key="label.multipleOf"/></th>
		   <th width="10%"><fmt:message key="label.minimumbuyqty"/></th>
		   <th width="10%"><fmt:message key="label.supplypath"/></th>
		   <th width="5%"><fmt:message key="label.leadtimeindays"/></th>
		   <th width="5%"><fmt:message key="label.shelflife"/></th>
		   </tr>
		   
		   <c:forEach var="dbuybean" items="${dbuyViewBeanCollection}" varStatus="status">
		   <c:choose>
		   <c:when test="${status.index % 2 == 0}" >
		    <c:set var="colorClass" value=''/>
		   </c:when>
		   <c:otherwise>
		    <c:set var="colorClass" value='alt'/>
		   </c:otherwise>
		  </c:choose>
<tr align="center" class="<c:out value="${colorClass}"/>" ID="rowId<c:out value="${status.index}"/>">
		    <td width="10%"><c:out value="${dbuybean.supplierName}"/></td>
		    <td width="5%"><c:out value="${dbuybean.supplierPartNo}"/></td>
		    <td width="10%"><c:out value="${dbuybean.breakQuantity}"/></td>
		    <td width="10%"><c:out value="${dbuybean.unitPrice}"/></td>
		    <td width="5%"><c:out value="${dbuybean.multipleOf}"/></td>
		    <td width="10%"><c:out value="${dbuybean.minBuyQuantity}"/></td>
		    <td width="5%"><c:out value="${dbuybean.supplyPath}"/></td>
		    <td width="10%"><c:out value="${dbuybean.leadTimeDays}"/></td>
		    <td width="5%"><c:out value="${dbuybean.remainingShelfLifePercent}"/></td>
		   </tr>
		 </c:forEach>
	      
		   </table>
		   </c:if>
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>


<%-- Spec div start --%>
<div class="roundcont filterContainer" style="width:100%;">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
	<div class="roundContent">
			<b><fmt:message key="buyOrders"/>:</b>
		   <c:choose>
		   <c:when test="${empty buyPageViewBeanCollection}" >
			    <table width="95%" border="0" cellpadding="0" cellspacing="0" class="tableNoData">
			     <tr>
			      <td width="100%">
			       <fmt:message key="main.nodatafound"/>
			      </td>
			     </tr>
			    </table>
		   </c:when>
		   <c:otherwise>
			    <table width="95%" border="0" cellpadding="0" cellspacing="0" class="tableNoData">
			     <tr>
			      <td width="100%"><%-- companyId for input ... shipToCompanyId not displayed now ${dbuyViewBeanCollection[0].shipToLocationId}--%>
			      
		   <th width="5%"><fmt:message key="label.addbuyorders"/></th>

			      </td>
			     </tr>
			    </table>
		   
		    <c:set var="colorClass" value='alt'/>
		   </c:otherwise>
		  </c:choose>
			
		  <c:if test="${!empty buyPageViewBeanCollection}" >	  
          <table width="95%" border="0" cellpadding="0" cellspacing="0" class="tableResults">

          <c:set var="colorClass" value=''/>
          <c:set var="dataCount" value='${0}'/>
<%-- para for ??customerPo preferredSupplier --%>          
		   <tr>
		   <th width="5%"><fmt:message key="label.prnumber"/></th>
		   <th width="5%"><fmt:message key="label.partnumber"/></th>
		   <th width="5%"><fmt:message key="label.type"/></th>
		   <th width="10%"><fmt:message key="label.buyquantity"/></th>
		   <th width="5%"><fmt:message key="label.needed"/></th>
		   <th width="10%"><fmt:message key="label.preferredsupplier"/><br>(<fmt:message key="label.selectsupplier"/>)</th>
		   <th width="10%"><fmt:message key="label.company"/></th>
		   <th width="10%"><fmt:message key="label.mrquantity"/></th>
		   <th width="5%"><fmt:message key="label.requestor"/></th>
		   <th width="5%"><fmt:message key="label.externallinenote"/></th>
		   <th width="5%"><fmt:message key="label.internallinenote"/></th>
		   <th width="5%"><fmt:message key="label.shiptonote"/></th>
		   </tr>
		   
		   <c:forEach var="buyorderbean" items="${dbuyViewBeanCollection}" varStatus="status">
		   <c:choose>
		   <c:when test="${status.index % 2 == 0}" >
		    <c:set var="colorClass" value=''/>
		   </c:when>
		   <c:otherwise>
		    <c:set var="colorClass" value='alt'/>
		   </c:otherwise>
		  </c:choose>
<tr align="center" class="<c:out value="${colorClass}"/>" ID="rowId<c:out value="${status.index}"/>">
		   <th width="10%"><fmt:message key="label.mrquantity"/></th>
		   <th width="5%"><fmt:message key="label.requestor"/></th>
		   <th width="5%"><fmt:message key="label.externallinenote"/></th>
		   <th width="5%"><fmt:message key="label.internallinenote"/></th>
		   <th width="5%"><fmt:message key="label.shiptonote"/></th>
		   <td width="2%">
		   	<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].prNumber" id="prNumber<c:out value="${status.index}"/>" value="<c:out value="${status.current.prNumber}"/>" readonly/>
		   	<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].radianPo" id="radianPo<c:out value="${status.index}"/>" type="hidden" value="${status.current.radianPo}"/>
		   </td>
		   <td width="2%">
				<c:out value="${status.current.partId}"/>		   
		   </td>
		   <td width="2%">
				<c:out value="${status.current.itemType}"/>		   
		   </td>
		   <td width="2%">
		   	<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].orderQuantity" id="orderQuantity<c:out value="${status.index}"/>" value="<c:out value="${status.current.orderQuantity}"/>" readonly/>
		   </td>
		   <td width="2%">
			<fmt:formatDate var="formattedNeedDate" value="${status.current.needDate}" pattern="${dateFormatPattern}"/>
		   	<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].needDate" id="needDate<c:out value="${status.index}"/>" value="<c:out value="${formattedNeedDate}"/>" readonly/>
		   </td>
		   <td width="2%">
			<fmt:formatDate var="formattedNeedDate" value="${status.current.needDate}" pattern="${dateFormatPattern}"/>
		   	<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].needDate" id="needDate<c:out value="${status.index}"/>" value="<c:out value="${formattedNeedDate}"/>" readonly/>
		   </td>
		   <td width="2%">
<%-- para for ??customerPo preferredSupplier, lastSupplierName not used. --%>		   lastSupplierName
								<c:out value="${status.current.preferredSupplierName}"/>${status.current.preferredSupplierName}
								<input name="buyOrdersInputBean[<c:out value="${status.index}"/>].poSupplierId" id="buyOrdersInputBean[<c:out value="${status.index}"/>].poSupplierId" value="${status.current.preferredSupplier}" type="hidden"/>
		   </td>
		   <td width="5%">
		   		<c:out value="${status.current.companyId}"/>
		   </td>
		   <td width="5%">
		   		<c:out value="${status.current.mrQuantity}"/>
		   </td>
		   <td width="8%">
		   	<c:out value="${status.current.requestorFirstName}"/> <c:out value="${status.current.requestorLastName}"/><br><c:out value="${status.current.phone}"/><br><c:out value="${status.current.email}"/>
		   </td>
		   <td width="3%"><c:out value="${status.current.notes}"/></td>
		   <td width="3%"><c:out value="${status.current.lineInternalNote}"/></td>
		   <td width="3%"><c:out value="${status.current.shiptoNote}"/></td>
		   </tr>
		 </c:forEach>
	      
		   </table>
		   </c:if>
	 </div>
  	<div class="roundbottom">
	 <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
	</div>
</div>
</div>

<%-- Spec div end --%>


</table>
</div>
</div>
</body>
</html:html>