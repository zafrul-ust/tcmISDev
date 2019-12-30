
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

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
<script type="text/javascript" src="/js/client/opencustomer/shiptopermission.js"></script>

<!-- These are for the Grid-->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<%--This is for HTML form integration.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<%--This is for smart rendering.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<%--This is to suppoert loading by JSON.--%>
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
<%--This file has our custom sorting and other utility methos for the grid.--%>    
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
    <fmt:message key="label.shiptopermission"/> (${fullName})
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData = {alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
            validvalues:"<fmt:message key="label.validvalues"/>",
            submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
            recordFound:"<fmt:message key="label.recordFound"/>",
            searchDuration:"<fmt:message key="label.searchDuration"/>",
            minutes:"<fmt:message key="label.minutes"/>",
            seconds:"<fmt:message key="label.seconds"/>",
            itemInteger:"<fmt:message key="error.item.integer"/>",
            ok:"<fmt:message key="label.ok"/>"};

var config = [
  {
  	columnId:"permission"
  },
  {
  	columnId:"facilityName",
  	columnName:'<fmt:message key="label.shipto"/>',
  	tooltip:"Y",
    width:45
  },
  {
	columnId:"facilityAccess",
	columnName:'<fmt:message key="label.access"/><br> <input type="checkbox" value="" onClick="return checkAll();" name="ok" id="ok" >',
	type:'hchstatus',  // checkbox:The value is string "true" if checked
	align:'center',
	onChange: facilityChanged, 
	width:8
  },
  {columnId:"invoiceGroupMember", align:'center', width:8, type:'hchstatus',
	  columnName:'<fmt:message key="label.displayinvoice"/><br> <input type="checkbox" value="" onClick="return checkAllInvoice();" name="okInvoice" id="okInvoice" >'},
  {
    columnId:"userId"
  },
  {
    columnId:"userAccess"
  },
  {
    columnId:"personnelId"
  },
  {
    columnId:"customerId"
  },
  {
    columnId:"facilityId"
  },
  {
    columnId:"oldFacilityAccess"
  },
  {columnId:"oldInvoiceGroupMember"}
];

// -->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoad()">
<tcmis:form action="/shiptopermissionresults.do" onsubmit="return submitFrameOnlyOnce();">
<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/>
    ${tcmISError}
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
    </c:forEach>        
</div>

<script type="text/javascript">
   <c:choose>
    <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISError && empty tcmISErrors }">
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

<div id="UserShiptoAdminBean" style="width:100%;height:400px;" style="display: none;"></div>

<script type="text/javascript">
<!--
<c:set var="showUpdateLink" value='N'/>
<c:set var="dataCount" value='${0}'/>

<c:if test="${!empty userShiptoAdminBeanColl}" >  
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="shipToBean" items="${userShiptoAdminBeanColl}" varStatus="status">
	<c:set var="checkPermission" value='N'/>
	<c:if test="${shipToBean.userAccess == 'Admin' || shipToBean.userAccess == 'Grant Admin'}">
		<c:set var="checkPermission" value="Y"/>
		<c:set var="showUpdateLink" value='Y'/>
	</c:if>
	<%--The row ID needs to start with 1 per their design.--%>
	{ id:${status.count},
	 data:['${checkPermission}',
	  '<tcmis:jsReplace value='${shipToBean.facilityName}'/>', 
	  ${shipToBean.facilityAccess},
	  ${shipToBean.invoiceGroupMember},
	  '${shipToBean.userId}',
	  '${shipToBean.userAccess}',
	  '${shipToBean.personnelId}',
	  '${shipToBean.customerId}',
	  '${shipToBean.facilityId}',
	  ${shipToBean.facilityAccess},
	  ${shipToBean.invoiceGroupMember}
	  ]}<c:if test="${!status.last}">,</c:if>  
	 <c:set var="dataCount" value='${dataCount+1}'/> 
 </c:forEach>
]};
</c:if>
//-->  
</script>

<c:if test="${showUpdateLink == 'Y'}">
	 <script type="text/javascript">
	 <!--
		  showUpdateLinks = true;
	 //-->
	 </script>
</c:if>

<!-- If the collection is empty say no data found -->
<c:if test="${empty userShiptoAdminBeanColl}">
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
    <input name="uAction" id="uAction" value="" type="hidden">     
    <input name="minHeight" id="minHeight" type="hidden" value="100">
      
  <!-- Put all the Search Criteria here for re-search after update-->
    <input name="customerId" id="customerId" type="hidden" value="${param.customerId}">
    <input name="facilityId" id="facilityId" type="hidden" value="${param.facilityId}">
    <input name="hide" id="hide" type="hidden" value="${param.hide}">
    <input name="personnelId" id="personnelId" type="hidden" value="${param.personnelId}" />
	<input name="userId" id="userId" type="hidden" value="${param.userId}" />
</div>
<!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->

<c:if test="${showUpdateLink == 'Y'}">
    <script type="text/javascript">
        <!--
        showUpdateLinks = true;
        //-->
    </script>
</c:if>

</tcmis:form>
</body>
</html:html>