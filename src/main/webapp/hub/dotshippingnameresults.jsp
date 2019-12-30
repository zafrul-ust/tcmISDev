
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
	
<!-- Add any other javascript you need for the page here --> 
<script type="text/javascript" src="/js/hub/dotshippingname.js"></script> 
	
<title><fmt:message key="label.dotshippingname" /></title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>"
,selectedRowMsg:"<fmt:message key="label.selectedshippingname"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>"
};

var config = [
{
	columnId:"permission"
},
{  columnId:"hazardousMaterialDescription",
   columnName:'<fmt:message key="label.description"/>',
   width:30
},
{
	columnId:"hazmatId",
	columnName:'<fmt:message key="label.hazmatid"/>',
	tooltip:"Y",
	width:5
},
{
	columnId:"symbol"
},
{
	columnId:"properShippingName"
},

{  columnId:"hazardClass",
   columnName:'<fmt:message key="label.hazardclass"/>',
   width:8
},
{
   columnId:"identificationNumber",
   columnName:'<fmt:message key="label.identificationnumber"/>',
   width:8
},
{
   columnId:"packing",
   columnName:'<fmt:message key="label.packinggroup"/>',
   width:10
},
{
	columnId:"shippingNameCount"
}
];
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoad()">

<tcmis:form action="/dotshippingnameresults.do"
	onsubmit="return submitFrameOnlyOnce();">
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

	<!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
     The default value of showUpdateLinks is false.
-->
	<tcmis:facilityPermission indicator="true"
		userGroupId="dotshippingnameUserGroup"
		facilityId="${param.testingField}">
		<script type="text/javascript">
 <!--
  showUpdateLinks = true;
 //-->
 </script>
	</tcmis:facilityPermission>

	<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
So this is just used to feed the YUI pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
	<!-- Error Messages Begins -->
	<div id="errorMessagesAreaBody" style="display: none;"><html:errors />
	</div>

	<script type="text/javascript">
<!--

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

	<div id="Cfr49HazardousMaterialViewBean"
		style="width: 100%; height: 400px;" style="display: none;"></div>

	<c:if test="${hazMatBeanCollection != null}">
		<!-- Search results start -->

		<c:set var="dataCount" value='${0}' />
<script type="text/javascript">

		<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
		<c:if test="${!empty hazMatBeanCollection}">
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="hazMatBean" items="${hazMatBeanCollection}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>
{ id:${status.index +1},
 <c:if test="${status.current.hazardClass eq 'Forbidden'}">'class':"grid_red",</c:if>
 <c:if test="${status.current.shippingNameCount eq '0'}">'class':"grid_black",</c:if>
 data:['${readonly}',
  '<tcmis:jsReplace value="${status.current.hazardousMaterialDescription}" processMultiLines="true"/>',
  '${status.current.hazmatId}',
  '${status.current.symbol}',
  '<tcmis:jsReplace value="${status.current.properShippingName}" processMultiLines="true"/>',
 '${status.current.hazardClass}',
 '${status.current.identificationNumber}',
 '<tcmis:jsReplace value="${status.current.packingGroup}" />',
 '${status.current.shippingNameCount}'
  ]}  
<c:set var="dataCount" value='${dataCount+1}' />
</c:forEach>
]};

   </c:if>
  </script>
		<!-- If the collection is empty say no data found -->
		<c:if test="${empty hazMatBeanCollection}">

			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="tableNoData" id="resultsPageTable">
				<tr>
					<td width="100%"><fmt:message key="main.nodatafound" /></td>
				</tr>
			</table>
		</c:if>

		<!-- Search results end -->
	</c:if> <!-- Hidden element start -->
	<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">
	    <input name="uAction" id="uAction" value="search" type="hidden">     
    <input name="minHeight" id="minHeight" type="hidden" value="100">
    
	<div id="hiddenElements" style="display: none;"><!-- Store all search criteria in hidden elements, need this to requery the database after updates -->
	<tcmis:saveRequestParameter /></div>
	<!-- Hidden elements end --></div>
	<!-- close of backGroundContent --></div>
	<!-- close of interface -->

</tcmis:form>
</body>
</html:html>