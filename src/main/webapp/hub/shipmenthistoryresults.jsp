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
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->

<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/hub/shipmenthistory.js"></script>

<script language="JavaScript" type="text/javascript">
<!--
var altMethod = new Array();
var altOwner = new Array();
var altNumber = new Array();
var altModeOfTransport = new Array();
var altIncoterm = new Array();
var altIncotermDesc = new Array();

<c:forEach var="invKey" items="${carrierInfoCollection}" varStatus="status3">
	altMethod["${invKey.key}"] = new Array();
	altOwner["${invKey.key}"] = new Array();
	altNumber["${invKey.key}"] = new Array();
	altMethod["${invKey.key}"][""] = "";
	altOwner["${invKey.key}"][""] = "";
	altNumber["${invKey.key}"][""] = "";
		
	<c:forEach var="carrierInfoBean" items="${invKey.value}" varStatus="status4">
		altMethod["${invKey.key}"]["${status4.current.carrierCode}"] = "${status4.current.carrierMethod}";
	</c:forEach>
	<c:forEach var="carrierInfoBean" items="${invKey.value}" varStatus="status4">
		altOwner["${invKey.key}"]["${status4.current.carrierCode}"] = "${status4.current.companyId}";
	</c:forEach>
	<c:forEach var="carrierInfoBean" items="${invKey.value}" varStatus="status4">
		altNumber["${invKey.key}"]["${status4.current.carrierCode}"] = '<tcmis:jsReplace value="${status4.current.account}"/>';
	</c:forEach>
	<c:forEach var="carrierInfoBean" items="${carrierInfoALL}" varStatus="status4">
		altMethod["${invKey.key}"]["${status4.current.carrierCode}"] = "${status4.current.carrierMethod}";
		altOwner["${invKey.key}"]["${status4.current.carrierCode}"] = "${status4.current.companyId}";
		altNumber["${invKey.key}"]["${status4.current.carrierCode}"] = '<tcmis:jsReplace value="${status4.current.account}"/>';
	</c:forEach>

</c:forEach>

<c:forEach var="motKey" items="${motIncotermCollection}" varStatus="motStatus">		 	
	altModeOfTransport[${motStatus.index}] = '<tcmis:jsReplace value="${motKey.modeOfTransport}"/>';   //add mode of transport	
				
	altIncoterm['<tcmis:jsReplace value="${motKey.modeOfTransport}"/>'] = new Array();     // add incoterms for this mode of transport
	altIncotermDesc['<tcmis:jsReplace value="${motKey.modeOfTransport}"/>'] = new Array();	   // add incoterms for this mode of transport
	<c:forEach var="incKey" items="${motKey.vvIncotermList}" varStatus="incStatus">
		altIncoterm['<tcmis:jsReplace value="${motKey.modeOfTransport}"/>'][${incStatus.index}]='<tcmis:jsReplace value="${incKey.incoterm}"/>';
		altIncotermDesc['<tcmis:jsReplace value="${motKey.modeOfTransport}"/>'][${incStatus.index}]='<tcmis:jsReplace value="${incKey.incotermShortDesc}"/>';
	</c:forEach>
</c:forEach>

function setValues(invGrp,rowNum) {
	var val = document.getElementById("carrierCode"+rowNum).value ;
	document.getElementById("carrierMethod"+rowNum).value = altMethod[invGrp][val];
	document.getElementById("accountOwner"+rowNum).value  = altOwner[invGrp][val];
	document.getElementById("accountNumber"+rowNum).value = altNumber[invGrp][val];
	document.getElementById("carrierMethod"+rowNum+"Span").innerHTML = altMethod[invGrp][val];
	document.getElementById("accountOwner"+rowNum+"Span").innerHTML  = altOwner[invGrp][val];
	document.getElementById("accountNumber"+rowNum+"Span").innerHTML = altNumber[invGrp][val];

}

function setAllValues() {
	var total = document.getElementById('totalLines').value;
	for(var i = 0; i < total; i++ 	) {
		var incReq = document.getElementById('incotermRequired'+i).value;
		if (incReq != null && incReq == "Y") 
			showMotOptions(i);
		setValues( document.getElementById('inventoryGroup'+i).value,i);
	} 
		

    var printInvoiceCount = document.getElementById('printInvoiceCount').value;
    if (printInvoiceCount*1 > 0)
    {
        parent.document.getElementById("updateInvoiceLink").style["display"] = "";
    }
    else
    {
        parent.document.getElementById("updateInvoiceLink").style["display"] = "none";
    }
}

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",all:"<fmt:message key="label.all"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
itemInteger:"<fmt:message key="error.item.integer"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
selectashipment:"<fmt:message key="label.selectashipment"/>",
oneshipmentcanbeprinted:"<fmt:message key="label.oneshipmentcanbeprinted"/>",
selectOnlyOneShipment:"<fmt:message key="label.selectoneshipmentonly"/>",
pleaseselect:"<fmt:message key="label.pleaseselect"/>",
selectatleastoneshipment:"<fmt:message key="label.selectatleastoneshipment"/>",
selectmotandincoterms:"<fmt:message key="label.selectmotandincoterms"/>",
showdetails:"<fmt:message key="label.showdetails"/>",
addviewshipmentdocs:"<fmt:message key="label.addviewshipmentdocs"/>",
printinvoice:"<fmt:message key="label.printinvoice"/>",
selectOneShipment:"<fmt:message key="label.selectoneshipment"/>",
printproforma:"<fmt:message key="label.printproforma"/>"};

with(milonic=new menuname("showMenu")){
    top="offset=2"
    style = contextStyle;
    margin=3
    aI("text=<fmt:message key="label.showdetails"/>;url=javascript:showDetails();");
	aI("text=<fmt:message key="label.addviewshipmentdocs"/>;url=javascript:addViewShipmentDocs();");
	aI("text=<fmt:message key="label.viewpickingdocuments"/>;url=javascript:showPickingDocuments();");
}

drawMenus();

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="setAllValues();setResultFrameSize();displaySearchDuration();">
<tcmis:form action="shipmenthistoryresults.do" onsubmit="return submitFrameOnlyOnce();">

<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
So this is just used to feed the YUI pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
  <html:errors/>
	<logic:present name="errorBean" scope="request">
		<bean:write name="errorBean" property="cause"/>
	</logic:present>
</div>

<script type="text/javascript">
<!--
/*YAHOO.namespace("example.aqua");
YAHOO.util.Event.addListener(window, "load", init);*/

/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null and empty requestScope['errorBean']}">
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
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
 //-->
 </script>

<c:if test="${shipmentHistoryViewBeanCollection != null}" >
<!-- Search results start -->

<c:set var="colorClass" value=''/>
<c:set var="invColorClass" value=''/>
<c:set var="dataCount" value='${0}'/>
<c:set var="printInvoiceCount" value='${0}'/>
 <!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
 <c:if test="${!empty shipmentHistoryViewBeanCollection}" >

    <table width="100%" class="tableResults" id="resultsPageTable" border="0" cellpadding="0" cellspacing="0">
    <c:set var="colorClass" value=''/>
    <c:set var="dataCount" value='${0}'/>
												<%-- * * * * * * START OF RESULTS FOR-EACH BLOCK * * * * * * --%>

    <c:forEach var="shipmentHistoryViewBean"  items="${shipmentHistoryViewBeanCollection}" varStatus="status">

    <c:if test="${status.index % 10 == 0}">
    <%-- Need to print the header every 10 rows - So here's the COLUMN HEADER row: --%>

	<tr> <%-- was align="center" --%>

		<th width="2%"><fmt:message key="label.ok"/><BR>
					   <input type="checkbox" value=""	onClick="checkall('rowChk')" name="chkAll" id="chkAll"></th>
		<th width="2%"><fmt:message key="label.shipmentid"/></th>
		<th width="5%" nowrap="nowrap"><fmt:message key="label.datedelivered"/></th>
		<th width="10%"><fmt:message key="label.inventorygroup"/></th>
		<th width="10%"><fmt:message key="label.shipto"/></th>
		<th width="5%"><fmt:message key="label.customer"/></th>
		<th width="5%"><fmt:message key="label.carrier"/></th>
		<th width="2%"><fmt:message key="label.accountowner"/></th>
		<th width="2%"><fmt:message key="label.accountnumber"/></th>
		<th width="5%"><fmt:message key="label.trackingnumber"/></th>
		<th width="5%"><fmt:message key="label.modeoftransport"/></th>
		<th width="5%"><fmt:message key="label.incoterms"/></th>
		
		<c:if test="${showProForma == 'Y'}">
			<th width="5%" nowrap="nowrap"><fmt:message key="label.lastproformaprintdate"/></th>
		</c:if>
			
		<th width="5%" nowrap="nowrap"><fmt:message key="label.proposedexportdate"/></th>	
	</tr>

    </c:if>

    <c:choose>
     <c:when test="${status.index % 2 == 0}" >
      <c:set var="colorClass" value=''/>
      <c:set var="invColorClass" value='invInputText'/>      
     </c:when>
     <c:otherwise>
      <c:set var="colorClass" value='alt'/>
      <c:set var="invColorClass" value='invAltInputText'/>      
     </c:otherwise>
    </c:choose>
  <input type="hidden" name="shipmentBean[${status.index}].shipmentId" value="${status.current.shipmentId}" id="shipmentId<c:out value="${status.index}"/>">
  <input type="hidden" name="shipmentBean[${status.index}].inventoryGroup" id="inventoryGroup${status.index}" value="${status.current.inventoryGroup}">
  <input type="hidden" name="shipmentBean[${status.index}].companyId" id="companyId${status.index}" value="${status.current.companyId}">
  <input type="hidden" name="shipmentBean[${status.index}].materialRequestOrigin" id="materialRequestOrigin${status.index}" value="${status.current.materialRequestOrigin}">
  <input type="hidden" name="shipmentBean[${status.index}].distribution" id="distribution${status.index}" value="${status.current.distribution}">
  <input type="hidden" name="shipmentBean[${status.index}].printInvoice" id="printInvoice${status.index}" value="${status.current.printInvoice}">
  <input type="hidden" name="shipmentBean[${status.index}].invoiceAtShipping" id="invoiceAtShipping${status.index}" value="${status.current.invoiceAtShipping}">
  <input type="hidden" name="shipmentBean[${status.index}].incotermRequired" id="incotermRequired${status.index}" value="${status.current.incotermRequired}">
  <input type="hidden" name="shipmentBean[${status.index}].invoiceBy" id="invoiceBy${status.index}" value="${status.current.invoiceBy}">
  <input type="hidden" name="shipmentBean[${status.index}].proFormaRequired" id="proFormaRequired${status.index}" value="${status.current.proFormaRequired}">
  
  <c:if test="${status.current.printInvoice == 'Y'}">
    <c:set var="printInvoiceCount" value='${printInvoiceCount+1}'/>
  </c:if>
        
<tr class="<c:out value="${colorClass}"/>"  id="rowId<c:out value="${status.index}"/>" onmouseup="selectRow(event, '${status.index}')" >
<input type="hidden" name="colorClass${status.index}" id="colorClass<c:out value="${status.index}"/>" value="${colorClass}" />
<td  width="2%" >
  <input type="checkbox" value="no" name="shipmentBean[${status.index}].ok" id="rowChk${status.index}" />
</td>
<td width="2%">
 <tcmis:emptyIfZero value="${status.current.shipmentId}">${status.current.shipmentId}</tcmis:emptyIfZero>
</td>
<td width="5%">
  <fmt:formatDate pattern="${dateFormatPattern}" value="${status.current.dateDelivered}"/>
</td>
<td width="10%">
  <c:out value="${status.current.inventoryGroup}"/>
</td>
<td width="10%">
  <c:out value="${status.current.addressLine1}"/> <c:out value="${status.current.addressLine2}"/> <c:out value="${status.current.addressLine3}"/> <c:out value="${status.current.addressLine4}"/> <c:out value="${status.current.addressLine5}"/>
</td>
<td width="5%">
  <c:out value="${status.current.customerName}"/>
</td>
<td width="5%">
<c:set var="carrierMethod" value='${status.current.carrierMethod}'/>
<c:if test="${carrierMethod != null}">
<c:set var="carrierMethod" value='(${status.current.carrierMethod})'/>
</c:if>
 <select name="shipmentBean[${status.index}].carrierCode" id="carrierCode${status.index}" value="${status.current.carrierCode}" onChange="setValues('${status.current.inventoryGroup}',${status.index})">
	<c:if test="${empty status.current.carrierCode}"><option value="" selected="selected"></option></c:if>
 	<c:forEach var="carrierInfoBean" items="${status.current.carrierInfoBeanCollection}" varStatus="status2">
 		<option value="${status2.current.carrierCode}" <c:if test="${status.current.carrierCode == status2.current.carrierCode }">selected="selected"</c:if> >${status2.current.carrierName}(${status2.current.carrierCode})</option>
 	</c:forEach>
 	<c:forEach var="carrierInfoBean" items="${carrierInfoALL}" varStatus="status2">
 		<option value="${status2.current.carrierCode}" <c:if test="${status.current.carrierCode == status2.current.carrierCode }">selected="selected"</c:if> >${status2.current.carrierName}(${status2.current.carrierCode})</option>
 	</c:forEach>
 </select>&nbsp;
 <span id="carrierMethod${status.index}Span"><c:out value="${status.current.carrierMethod}"/></span>
 <input type="hidden" name="carrierMethod" id="carrierMethod${status.index}" value="${status.current.carrierMethod}" />
</td>
<td width="2%">
	<span id="accountOwner${status.index}Span"><c:out value="${status.current.carrierOwner}"/></span>
    <input type="hidden" name="accountOwner" id="accountOwner${status.index}" value="${status.current.carrierOwner}" />
</td>
<td width="2%">
	<span id="accountNumber${status.index}Span"><c:out value="${status.current.account}"/></span>
    <input type="hidden" name="accountNumber" id="accountNumber${status.index}" value="${status.current.account}" />
</td>
<td width="5%">
  <input type="text" class="inputBox"  ${readOnly} name="shipmentBean[${status.index}].trackingNumber" id="trackingNumber_${status.index}" value="${status.current.trackingNumber}"/>
</td>

<td width="5%">
	<c:set var="modeOfTransport" value='${status.current.modeOfTransport}'/>
	<c:if test="${modeOfTransport != null}">
		<c:set var="modeOfTransport" value='(${status.current.modeOfTransport})'/>
	</c:if>	
	<c:if test="${status.current.incotermRequired != null && status.current.incotermRequired == 'Y'}">
		<input type="hidden" name="hmodeOfTransport${status.index}" id="hmodeOfTransport${status.index}" value="${status.current.modeOfTransport}" />
		<select name="shipmentBean[${status.index}].modeOfTransport" id="modeOfTransport${status.index}" onChange="modeOfTransportChanged(${status.index});">		 	
		</select>&nbsp;	
	</c:if>	
</td>
<td width="5%">
	<c:set var="incoterm" value='${status.current.incoterm}'/>
	<c:if test="${incoterm != null}">
		<c:set var="incoterm" value='(${status.current.incoterm})'/>
	</c:if>
	<c:if test="${status.current.incotermRequired != null && status.current.incotermRequired == 'Y'}">
		<input type="hidden" name="hincoterm${status.index}" id="hincoterm${status.index}" value="${status.current.incoterm}" />
		<select name="shipmentBean[${status.index}].incoterm" id="incoterm${status.index}" >			 	
		</select>&nbsp;	
	</c:if>
</td>

<c:if test="${showProForma == 'Y'}">
<td width="5%" nowrap="nowrap">
	<c:choose>
		<c:when test="${status.current.proFormaRequired != null && status.current.proFormaRequired == 'Y'}">
  			<fmt:formatDate pattern="${dateFormatPattern}" value="${status.current.lastProFormaPrintDate}"/>
  		</c:when>
  		<c:otherwise>
  		N/A
  		</c:otherwise>
  	</c:choose>
</td>
</c:if>

<td width="5%" nowrap="nowrap">
  <fmt:formatDate pattern="${dateFormatPattern}" value="${status.current.proposedExportDate}"/>
</td>

</tr>

   <c:set var="dataCount" value='${dataCount+1}'/>

   </c:forEach>  <%-- * * * * * * END OF FOR-EACH BLOCK * * * * * * --%>
<input type="hidden" name="totallines" id="totallines" value="${dataCount}"/>

   </table>
   </c:if>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty shipmentHistoryViewBeanCollection}" >
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
    <input name="printInvoiceCount" id="printInvoiceCount" value="<c:out value="${printInvoiceCount}"/>" type="hidden">    
    <input name="uAction" id="uAction" value="update"/>
	<input type="hidden" name="sourceHub" id="sourceHub" value='<tcmis:jsReplace value="${param.sourceHub}"/>'/>
	<input type="hidden" name="shipmentId" id="shipmentId" value='<tcmis:jsReplace value="${param.shipmentId}"/>'/>
	<input type="hidden" name="inventoryGroup" id="inventoryGroup" value='<tcmis:jsReplace value="${param.inventoryGroup}"/>'/>
	<input type="hidden" name="dock" id="dock" value='<tcmis:jsReplace value="${param.dock}"/>'/>
	<input type="hidden" name="toDate" id="toDate" value='<tcmis:jsReplace value="${param.toDate}"/>'/>
	<input type="hidden" name="fromDate" id="fromDate" value='<tcmis:jsReplace value="${param.fromDate}"/>'/>
	<input type="hidden" name="shipmentOrTracking" value='<tcmis:jsReplace value="${param.shipmentOrTracking}"/>'>
    <input type="hidden" name="hub" id="hub" value='<tcmis:jsReplace value="${param.hub}"/>'/>
    <input type="hidden" name="prNumber" id="prNumber" value='<tcmis:jsReplace value="${param.prNumber}"/>'/>
    <input type="hidden" name="personnelId" id="personnelId" value="${personnelBean.personnelId}"/>
    <input type="hidden" name="pageid"  id="pageid" value='<tcmis:jsReplace value="${param.pageId}"/>'/>
</div>
<!-- Hidden elements end -->

</div> <!-- close of backGroundContent -->
</div> <!-- close of interface -->
</tcmis:form>
</body>
</html:html>