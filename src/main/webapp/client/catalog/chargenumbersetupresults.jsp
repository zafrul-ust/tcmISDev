<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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

<!-- For Calendar support 
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
-->
<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/client/catalog/chargenumbersetup.js"></script>

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
    <fmt:message key="label.chargenumbersetup"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData = {alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
chargenumberinuse1:"<fmt:message key="label.chargenumberinuse1"/>",
chargenumberinuse2:"<fmt:message key="label.chargenumberinuse2"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
notCorrectFormat:"<fmt:message key="error.notcorrectformat"/>"

};
showUpdateLinks = false;

<c:set var="showDesCol" value='N'/>

<c:if test="${fn:toLowerCase(param.showDesCol) == 'y'}">
	<c:set var="showDesCol" value='Y'/>
</c:if>

var config = [
{ 
  	columnId:"permission"
},
{
  	columnId:"status",
  	columnName:'<fmt:message key="label.active"/>',
//  	columnName:'<fmt:message key="label.active"/><br><input type="checkbox" value="" onClick="return checkAll(\'status\');" name="chkAll" id="chkAll">',
  	type:'hchstatus',  // checkbox:The value is string "true" if checked
  	onChange:changedOrNot,
    align:'center',
    width:5
},
{ 
  	columnId:"originalStatus"
},
<c:if test="${!empty param.chargeNumber1Label}">
	<c:if test="${fn:toLowerCase(param.printChargeDesc1) == 'y'}">
		<c:set var="showDesCol" value='Y'/>
	</c:if>
{ 	columnId:"chargeNumber1",
  	columnName:'${param.chargeNumber1Label}',
  	type:'hed',
  	onChange:changedOrNot, 
  	width:22, // the width of this cell
  	size:22,  // the size of the input field
    maxlength:30 // the maxlength of the input field  
},
{ 
  	columnId:"originalChargeNo1"
},
{ 
  	columnId:"chargeNo1Alias"
},
</c:if>
<c:if test="${!empty param.chargeNumber2Label}">
	<c:if test="${fn:toLowerCase(param.printChargeDesc2) == 'y'}">
		<c:set var="showDesCol" value='Y'/>
	</c:if>
{ 	columnId:"chargeNumber2",
  	columnName:'${param.chargeNumber2Label}',
  	type:'hed',
  	onChange:changedOrNot, 
  	width:22, // the width of this cell
  	size:22,  // the size of the input field
    maxlength:30 // the maxlength of the input field 
},
{ 
  	columnId:"originalChargeNo2"
},
{ 
  	columnId:"chargeNo2Alias"
},
</c:if>
<c:if test="${!empty param.chargeNumber3Label}">
	<c:if test="${fn:toLowerCase(param.printChargeDesc3) == 'y'}">
		<c:set var="showDesCol" value='Y'/>
	</c:if>
{ 	columnId:"chargeNumber3",
  	columnName:'${param.chargeNumber3Label}',
  	type:'hed', 
  	onChange:changedOrNot,
  	width:22, // the width of this cell
  	size:22,  // the size of the input field
    maxlength:30 // the maxlength of the input field 
},
{ 
  	columnId:"originalChargeNo3"
},
{ 
  	columnId:"chargeNo3Alias"
},
</c:if>
<c:if test="${!empty param.chargeNumber4Label}">
	<c:if test="${fn:toLowerCase(param.printChargeDesc4) == 'y'}">
		<c:set var="showDesCol" value='Y'/>
	</c:if>
{ 	columnId:"chargeNumber4",
  	columnName:'$param.chargeNumber4Label}',
  	type:'hed', 
  	onChange:changedOrNot,
  	width:22, // the width of this cell
  	size:22,  // the size of the input field
    maxlength:30 // the maxlength of the input field 
},
{ 
  	columnId:"originalChargeNo4"
},
{ 
  	columnId:"chargeNo4Alias"
},
</c:if>
{
  	columnId:"chargeId1"
},
{ 
  	columnId:"changed"

}
<c:if test="${showDesCol == 'Y'}">
,
{ 	columnId:"description",
  	columnName:'<fmt:message key="label.description"/>',
  	type:'hed', 
  	onChange:changedOrNot,
  	width:22, // the width of this cell
  	size:22,  // the size of the input field
    maxlength:30 // the maxlength of the input field 
},
{
	columnId:"originalDescription"	
}
</c:if>

];

with(milonic=new menuname("rightClickMenu")){
    top="offset=2"
    style = contextStyle;
    margin=3
    	aI("text=<fmt:message key="label.delete"/>;url=javascript:deleteRow();");
}

drawMenus();

//-->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoad();">
<tcmis:form action="/chargenumbersetupresults.do" onsubmit="return submitFrameOnlyOnce();">

<c:set var="readonly" value="N" />
<tcmis:facilityPermission indicator="true" userGroupId="ChargeNumberSetup" companyId="${headerColl.companyId}" facilityId="${param.facilityId}">
<c:set var="readonly" value="Y" />
<script type="text/javascript">
<!--
	showUpdateLinks = true;
//-->
</script>
</tcmis:facilityPermission>


<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
     ${tcmISError}<br/>
    <c:forEach var="tcmisError" items="${tcmISErrors}">
      ${tcmisError}<br/>
    </c:forEach>
    <c:if test="${param.maxData == fn:length(chargeNoColl)}">
     <fmt:message key="label.maxdata">
      <fmt:param value="${param.maxData}"/>
     </fmt:message>
    </c:if>
</div>
<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
<c:when test="${empty tcmISErrors and empty tcmISError}">
 <c:choose>
  <c:when test="${param.maxData == fn:length(chargeNoColl)}">
    showErrorMessage = true;
    parent.messagesData.errors = "<fmt:message key="label.noticewindowtitle"/>";
  </c:when>
  <c:otherwise>
    showErrorMessage = false;
  </c:otherwise>
 </c:choose>
</c:when>
<c:otherwise>
  parent.messagesData.errors = "<fmt:message key="label.errors"/>";
  showErrorMessage = true;
  </c:otherwise>
</c:choose>
//-->
</script>
<!-- Error Messages Ends -->
<div class="interface" id="resultsPage">
<div class="backGroundContent">

<div id="chargeNumberBean" style="width:100%;height:400px;" style="display: none;"></div>
<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty chargeNoColl}">
<input name="dependent" id="dependent" type="hidden" value="Y"/>
<script type="text/javascript">

<!--

var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="chargeNoBean" items="${chargeNoColl}" varStatus="status">
<c:set var="showUpdateLink" value='N'/>
 
<c:if test="${status.index > 0}">,</c:if>

<c:set var="chargeIdStatus" value="false"/>
<c:if test="${status.current.status == 'A'}"><c:set var="chargeIdStatus" value="true"/></c:if>
<c:set var="desc"> <tcmis:jsReplace value="${status.current.description}" processMultiLines="true" /></c:set>
/*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},
/* Set the color to the rows according to their releaseStatus*/
<c:if test="${status.current.status != 'A'}">'class':"grid_black",</c:if>
	 data:['${readonly}',${chargeIdStatus},'${status.current.status}',
	 <c:if test="${!empty param.chargeNumber1Label}">'${status.current.chargeNumber1}','${status.current.chargeNumber1}','${param.chargeLabel1Alias}',</c:if>
	 <c:if test="${!empty param.chargeNumber2Label}">'${status.current.chargeNumber2}','${status.current.chargeNumber2}','${param.chargeLabel2Alias}',</c:if>
	 <c:if test="${!empty param.chargeNumber3Label}">'${status.current.chargeNumber3}','${status.current.chargeNumber3}','${param.chargeLabel3Alias}',</c:if>
	 <c:if test="${!empty param.chargeNumber4Label}">'${status.current.chargeNumber4}','${status.current.chargeNumber4}','${param.chargeLabel4Alias}',</c:if>
	 '${status.current.chargeId1}','N'
	 <c:if test="${showDesCol == 'Y'}">, '${desc}', '${desc}' </c:if>
	 ]}
 <c:set var="dataCount" value='${dataCount+1}'/> 
 </c:forEach>
]};

//-->   
</script>
</c:if>

<!-- If the collection is empty say no data found -->
<c:if test="${empty chargeNoColl}">
    <input name="dependent" id="dependent" type="hidden" value="N"/>
</c:if>
<!-- Search results end -->

<!-- Hidden element start --> 
<div id="hiddenElements" style="display: none;">    			
	<input name="totalLines" id="totalLines" value="1" type="hidden"/>
	<input name="totalLinesNum" id="totalLinesNum" value="${dataCount}" type="hidden"/>
	<input name="uAction" id="uAction" type="hidden" value="${param.uAction}" />

<!-- Hidden elements for re-search-after-update purpose -->
	<input name="companyId" id="companyId" type="hidden" value="${headerColl.companyId}"/>
	<input name="facilityId" id="facilityId" type="hidden" value="${param.facilityId}"/>
	<input name="accountSysId" id="accountSysId" type="hidden" value="${param.accountSysId}"/>
	<input name="chargeType" id="chargeType" type="hidden" value="${param.chargeType}"/>
	<input name="chargeNumber1" id="chargeNumber1" type="hidden" value="${param.chargeNumber1}"/>
	<input name="chargeNumber2" id="chargeNumber2" type="hidden" value="${param.chargeNumber2}"/>
	<input name="chargeNumber3" id="chargeNumber3" type="hidden" value="${param.chargeNumber3}"/>
	<input name="chargeNumber4" id="chargeNumber4" type="hidden" value="${param.chargeNumber4}"/>
	<input type="hidden" name="chargeNumber1Label" id="chargeNumber1Label" value="${param.chargeNumber1Label}"/>
	<input type="hidden" name="chargeNumber2Label" id="chargeNumber2Label" value="${param.chargeNumber2Label}"/>
	<input type="hidden" name="chargeNumber3Label" id="chargeNumber3Label" value="${param.chargeNumber3Label}"/>
	<input type="hidden" name="chargeNumber4Label" id="chargeNumber4Label" value="${param.chargeNumber4Label}"/>
	<input type="hidden" name="chargeLabel1Alias" id="chargeLabel1Alias" value="${param.chargeLabel1Alias}"/>
	<input type="hidden" name="chargeLabel2Alias" id="chargeLabel2Alias" value="${param.chargeLabel2Alias}"/>
	<input type="hidden" name="chargeLabel3Alias" id="chargeLabel3Alias" value="${param.chargeLabel3Alias}"/>
	<input type="hidden" name="chargeLabel4Alias" id="chargeLabel4Alias" value="${param.chargeLabel4Alias}"/>
	<input name="chargeValidation1" id="chargeValidation1" type="hidden" value="${param.chargeValidation1}"/>
	<input name="chargeValidation2" id="chargeValidation2" type="hidden" value="${param.chargeValidation2}"/>
	<input name="chargeValidation3" id="chargeValidation3" type="hidden" value="${param.chargeValidation3}"/>
	<input name="chargeValidation4" id="chargeValidation4" type="hidden" value="${param.chargeValidation4}"/>
	<input name="minHeight" id="minHeight" type="hidden" value="100"/>
    <input name="maxData" id="maxData" type="hidden" value="${param.maxData}"/>
    <input name="printChargeDesc1" id="printChargeDesc1" type="hidden" value="${param.printChargeDesc1}"/>
    <input name="printChargeDesc2" id="printChargeDesc2" type="hidden" value="${param.printChargeDesc2}"/>
    <input name="printChargeDesc3" id="printChargeDesc3" type="hidden" value="${param.printChargeDesc3}"/>
    <input name="printChargeDesc4" id="printChargeDesc4" type="hidden" value="${param.printChargeDesc4}"/>
    
    
</div>
<!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->

</tcmis:form>
</body>
</html:html>