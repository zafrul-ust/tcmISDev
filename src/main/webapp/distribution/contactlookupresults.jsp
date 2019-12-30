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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html:html lang="true">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta http-equiv="expires" content="-1">
    <link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
    <%@ include file="/common/locale.jsp" %>
    <!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<!-- Add any other stylesheets you need for the page here -->

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<%--NEW - grid resize--%>
<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
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
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/distribution/contactlookup.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
-->
<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
    <fmt:message key="contactsearch.title"/>
</title>

<script type="text/javascript">
    <!--
   	 showUpdateLinks = false;
    
    //-->
</script>

<script language="JavaScript" type="text/javascript">
<!--

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData = {alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
selectedcontact:"<fmt:message key="label.selectedcontact"/>", 
selectedcustomercontact:"<fmt:message key="label.selectedcustomercontact"/>", 
newcontact:"<fmt:message key="label.newcontact"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};

var config = [
{ columnId:"customerName",
  columnName:'<fmt:message key="label.customername"/>',
  sorting:"haasStr",
  tooltip:true,
  width:12
},
{ columnId:"customerId",
  columnName:'<fmt:message key="label.id"/>',
  sorting:"haasStr",
  width:4
},
{ columnId:"abcClassification",
  columnName:'<fmt:message key="label.abcclassification"/>',
  width:8
},
{
    columnId:"fullName",
    columnName:'<fmt:message key="label.name"/>'
},
{ columnId:"phone",
  columnName:'<fmt:message key="label.phone"/>',
  sorting:"haasStr",
  width:8
},
{
  	columnId:"email",
   columnName:'<fmt:message key="label.email"/>',
  	width:10
},

{ columnId:"contactType",
  columnName:'<fmt:message key="label.jobfunction"/>',
  sorting:"haasStr",
  tooltip:true,
  width:8
},
{
  	columnId:"otherJobFunctions",
  	columnName:'<fmt:message key="label.otherjobfunctions"/>',
  	tooltip:true,
  	width:15
},
{ columnId:"address",
  columnName:'<fmt:message key="label.billtoaddress"/>',
  sorting:"haasStr",
  tooltip:true,
  width:15
},
{ columnId:"synonym",
  columnName:'<fmt:message key="label.synonym"/>'
	  
},
{ columnId:"addressLine1Display"
},
{ columnId:"addressLine2Display"
},
{ columnId:"addressLine3Display"
},
{ columnId:"addressLine4Display"
},
{ columnId:"addressLine5Display"
},
{ columnId:"fax"
},
{ columnId:"billToCompanyId"
},
{ columnId:"billToLocationId"
},
{ columnId:"nickName"
},
{ columnId:"contactId"
},
{ 
  columnId:"status"
},
{ 
  columnId:"creditStatus"
}
];

//-->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoad()">
<tcmis:form action="/contactlookupresults.do" onsubmit="return submitFrameOnlyOnce();">
<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/>
    ${tcmISError}
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
    </c:forEach>        
</div>

<script type="text/javascript">
   <c:choose>
    <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISErrors }">
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

<div id="customerContactSearchViewBean" style="width:100%;%;height:400px;" style="display: none;"></div>
<c:set var="dataCount" value='${0}'/>
<c:if test="${CustomerContactSearchViewCollection != null}" >
<script type="text/javascript">
<!--
<%--NEW - storing the data to be displayed in the grid in a JSON. notice the ID, this will be the id of the cell in the grid.--%>
<%--TODO - Right click to show links for receipt labels, print BOL, transactions history.--%>
<c:set var="preCustomerId" value=''/>
<bean:size id="listSize" name="CustomerContactSearchViewCollection"/>

var lineMap = new Array();
<%--
<c:forEach var="contactBean" items="${CustomerContactSearchViewCollection}" varStatus="status">
 <c:set var="currentCustomerId" value='${status.current.customerId}'/>
 <c:if test="${ currentCustomerId != preCustomerId }">
  lineMap[${status.index}] = ${rowCountPart[currentCustomerId]};
 </c:if>
 <c:set var="preCustomer" value='${status.current.customerId}'/>
</c:forEach>  --%>

var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="contactBean" items="${CustomerContactSearchViewCollection}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>
<c:set var="other" value=""/>
<c:if test="${contactBean.purchasing == 'Y'}">
	<c:set var="other">${other},<fmt:message key="label.purchasing"/></c:set>
</c:if>
<c:if test="${contactBean.accountsPayable == 'Y'}">
	<c:set var="other">${other},<fmt:message key="label.accountspayable"/></c:set>
</c:if>
<c:if test="${contactBean.receiving == 'Y'}">
	<c:set var="other">${other},<fmt:message key="label.receiving"/></c:set>
</c:if>
<c:if test="${contactBean.qualityAssurance == 'Y'}">
	<c:set var="other">${other},<fmt:message key="label.qualityassurance"/></c:set>
</c:if>
<c:if test="${contactBean.management == 'Y'}">
	<c:set var="other">${other},<fmt:message key="label.management"/></c:set>
</c:if>
<c:if test="${other != ''}">
	<c:set var="other">${fn:substringAfter(other,",")}</c:set>
</c:if>

<tcmis:jsReplace var="addressLine1Display" value='${contactBean.addressLine1Display}' processMultiLines="true"/>
<tcmis:jsReplace var="addressLine2Display" value='${contactBean.addressLine2Display}' processMultiLines="true"/>
<tcmis:jsReplace var="addressLine3Display" value='${contactBean.addressLine3Display}' processMultiLines="true"/>
<tcmis:jsReplace var="addressLine4Display" value='${contactBean.addressLine4Display}' processMultiLines="true"/>
<tcmis:jsReplace var="addressLine5Display" value='${contactBean.addressLine5Display}' processMultiLines="true"/>

{ id:${status.index +1},
<c:if test="${fn:toUpperCase(contactBean.status) eq 'INACTIVE'}">'class':"grid_black",</c:if>
<c:if test="${fn:toUpperCase(contactBean.creditStatus) eq 'STOP'}">'class':"grid_black",</c:if>
 data:[
  '<tcmis:jsReplace value="${contactBean.customerName}"/>','${contactBean.customerId}','${contactBean.abcClassification}',
  '<tcmis:jsReplace value="${contactBean.lastName}"/>, <tcmis:jsReplace value="${contactBean.firstName}"/>',
  '<tcmis:jsReplace value="${contactBean.phone}"/>','<tcmis:jsReplace value="${contactBean.email}"/>',
  '${contactBean.contactType}','${other}',
  '${addressLine1Display} ${addressLine2Display} ${addressLine3Display} ${addressLine4Display} ${addressLine5Display}',
  '<tcmis:jsReplace value="${contactBean.phone}"/>',
  '${addressLine1Display}', '${addressLine2Display}', '${addressLine3Display}', '${addressLine4Display}', '${addressLine5Display}',
  '<tcmis:jsReplace value="${contactBean.fax}"/>','${contactBean.billToCompanyId}','${contactBean.billToLocationId}',
  '<tcmis:jsReplace value="${contactBean.nickname}"/>','${contactBean.contactId}','${contactBean.status}','${contactBean.creditStatus}']}

<c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
]};
//-->
</script>
</c:if>

<!-- If the collection is empty say no data found -->
<c:if test="${empty CustomerContactSearchViewCollection}">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
        <tr>
            <td width="100%">
                <fmt:message key="main.nodatafound"/>
            </td>
        </tr>
    </table>
</c:if>
<!-- Search results end -->

<!-- Hidden element start --> 
<div id="hiddenElements" style="display: none;">    			
	<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden">
	<input name="displayElementId" id="displayElementId" type="hidden" value="<c:out value="${param.displayElementId}"/>">
	<input name="displayArea" id="displayArea" type="hidden" value="<c:out value="${param.displayArea}"/>">
	<input name="valueElementId" id="valueElementId" type="hidden" value="<c:out value="${param.valueElementId}"/>">
	<input name="searchCustomerIdArgument" id="searchCustomerIdArgument" type="hidden" value="${param.searchCustomerIdArgument}"></input>
	<input name="minHeight" id="minHeight" type="hidden" value="100"> 
</div>
<!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->
</tcmis:form>
</body>
</html:html>