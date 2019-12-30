<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
<tcmis:gridFontSizeCss overflow="notHidden"/>
	<%--
 <link rel="stylesheet" type="text/css" href="/css/haasGlobal.css"></link>
 --%>
	<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

	<%-- For Calendar support --%>
<%--<script type="text/javascript" src="/js/calendar/date.js"></script>--%>
<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--NEW - dhtmlX grid files
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
--%>
<!-- These are for the Grid-->
    <!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_drag.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<%-- Add any other javascript you need for the page here --%>
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>
<script src="/js/report/adhocusagereport.js" language="JavaScript"></script>
<script src="/js/report/workareasearchadhoctemplate.js" language="JavaScript"></script>
<script src="/js/report/adhocdatefieldtemplate.js" language="JavaScript"></script>

<script language="JavaScript" type="text/javascript">
<!--
var module =  '<tcmis:module/>';

var altDock = new Array();
<c:forEach var="facAppDockDpViewBean" items="${facAppDockDpViewBeanCollection}" varStatus="status">
	<c:set var="currentFacility" value='${status.current.facilityId}'/>
	<c:set var="dockBeanCollection" value='${status.current.dockBeanCollection}'/>
	altDock['<tcmis:jsReplace value="${currentFacility}"/>'] = new Array(
		<c:forEach var="facLocAppBean" items="${dockBeanCollection}" varStatus="status1">
			<c:if test="${status1.index > 0}">,</c:if>
			'<tcmis:jsReplace value="${status1.current.dockLocationId}"/>'
		</c:forEach>
);
</c:forEach>

var altDockDesc = new Array();
<c:forEach var="facAppDockDpViewBean" items="${facAppDockDpViewBeanCollection}" varStatus="status">
	<c:set var="currentFacility" value='${status.current.facilityId}'/>
	<c:set var="dockBeanCollection" value='${status.current.dockBeanCollection}'/>
	altDockDesc['<tcmis:jsReplace value="${currentFacility}"/>'] = new Array(
		<c:forEach var="facLocAppBean" items="${dockBeanCollection}" varStatus="status1">
			<c:if test="${status1.index > 0}">,</c:if>
			'<tcmis:jsReplace value="${status1.current.dockDesc}"/>'
		</c:forEach>
);
</c:forEach>

var altDeliveryPoint = new Array();
<c:forEach var="facAppDockDpViewBean" items="${facAppDockDpViewBeanCollection}" varStatus="status">
	<c:set var="currentFacility" value='${status.current.facilityId}'/>
	<c:set var="dockBeanCollection" value='${status.current.dockBeanCollection}'/>
	<c:forEach var="facAppDockDpViewBean" items="${dockBeanCollection}" varStatus="status1">
		<c:set var="currentDock" value='${status1.current.dockLocationId}'/>
		<c:set var="deliveryPointBeanCollection" value='${status1.current.deliveryPointBeanCollection}'/>
		altDeliveryPoint['<tcmis:jsReplace value="${currentFacility}"/><tcmis:jsReplace value="${currentDock}"/>'] = new Array(
			<c:forEach var="facAppDockDpViewBean2" items="${deliveryPointBeanCollection}" varStatus="status2">
				<c:if test="${status2.index > 0}">,</c:if>
				'<tcmis:jsReplace value="${status2.current.deliveryPoint}"/>'
			</c:forEach>
		);
	</c:forEach>
</c:forEach>

var altDeliveryPointDesc = new Array();
<c:forEach var="facAppDockDpViewBean" items="${facAppDockDpViewBeanCollection}" varStatus="status">
	<c:set var="currentFacility" value='${status.current.facilityId}'/>
	<c:set var="dockBeanCollection" value='${status.current.dockBeanCollection}'/>
	<c:forEach var="facAppDockDpViewBean" items="${dockBeanCollection}" varStatus="status1">
		<c:set var="currentDock" value='${status1.current.dockLocationId}'/>
		<c:set var="deliveryPointBeanCollection" value='${status1.current.deliveryPointBeanCollection}'/>
		altDeliveryPointDesc['<tcmis:jsReplace value="${currentFacility}"/><tcmis:jsReplace value="${currentDock}"/>'] = new Array(
			<c:forEach var="facAppDockDpViewBean2" items="${deliveryPointBeanCollection}" varStatus="status2">
				<c:if test="${status2.index > 0}">,</c:if>
				'<tcmis:jsReplace value="${status2.current.deliveryPointDesc}"/>'
			</c:forEach>
		);
	</c:forEach>
</c:forEach>

var facilityPermArr = new Array();

<c:forEach var="facilityPermission" items="${authorizedFacilityUsersForUg}" varStatus="gateKeepingStatus">
     facilityPermArr[<c:out value="${gateKeepingStatus.index}"/>] = '<tcmis:jsReplace value="${facilityPermission.facilityId}"/>';
</c:forEach>

var hideDockDeliveryPointJsp = 'N'; 
<c:set var="hideDockDeliveryPoint" value='N'/>
<tcmis:featureReleased feature="HideDockDeliveryPoint" scope="ALL">  
    hideDockDeliveryPointJsp = 'Y'; 
    <c:set var="hideDockDeliveryPoint" value='Y'/>  
</tcmis:featureReleased>    
  
// -->    
</script>

<title>
	<fmt:message key="adhocusagereport.title"/>
</title>

<script language="JavaScript" type="text/javascript">
	<!--
	//add all the javascript messages here, this for internationalization of client side javascript messages
	var messagesData = new Array();
	messagesData = {alert:"<fmt:message key="label.alert"/>",
		and:"<fmt:message key="label.and"/>",
		submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
		all:"<fmt:message key="label.all"/>",
		myFacilities:"<fmt:message key="label.myfacilities"/>",
		myworkareas:"<fmt:message key="label.myworkareas"/>",
		thereisnoitemtomove:"<fmt:message key="label.thereisnoitemtomove"/>",
		selectanitemthatyouwanttomove:"<fmt:message key="label.selectanitemthatyouwanttomove"/>",
		youmustfirstselectitemtoreorder:"<fmt:message key="label.youmustfirstselectitemtoreorder"/>",
		youmustselectreportfield:"<fmt:message key="label.youmustselectreportfield"/>",
		youmustentercasnumber:"<fmt:message key="label.youmustentercasnumber"/>",
		pleaseenterreportname:"<fmt:message key="label.pleaseenterreportname"/>",
		begindaterequired:"<fmt:message key="label.begindaterequired"/>",
		enddaterequired:"<fmt:message key="label.enddaterequired"/>",
		invaliddateformat:"<fmt:message key="label.invaliddateformat"/>",
	    adhocusagecompatibilityerror:"<fmt:message key="label.adhocusagecompatibilityerror"/>",
	    pleasewait:"<fmt:message key="label.pleasewait"/>",
		dateformat:"<fmt:message key="javascript.dateformat"/>",
		other:"<fmt:message key="label.other"/>",
		entervalidinteger:"<fmt:message key="label.entervalidinteger"/>"};
	// -->
</script>
</head>

<body bgcolor="#ffffff" onload="loadDateFields();loadTemplate();initializeReportGrid();loadForm();" onunload="closeAllchildren();" onresize="resizeFrames()">
<c:if test="org.apache.struts.action.MESSAGE == null">
	<div class="errorMessages">
		ERROR: Application resources not loaded.
	</div>
</c:if>

<tcmis:form action="/adhocusagereport.do" onsubmit="return submitOnlyOnce();">

<div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
	<br><br><br><fmt:message key="label.pleasewait"/>
</div>
<div class="interface" id="mainPage">

	<%-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure --%>
<div class="topNavigation" id="topNavigation">
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td width="200">
				<img src="/images/tcmtitlegif.gif" align="left">
			</td>
			<td>
				<img src="/images/tcmistcmis32.gif" align="right">
			</td>
		</tr>
	</table>

	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td width="70%" class="headingl">
				<fmt:message key="adhocusagereport.title"/>
			</td>
			<td width="30%" class="headingr">
				<html:link style="color:#FFFFFF" forward="home">
					<fmt:message key="label.home"/>
				</html:link>
			</td>
		</tr>
	</table>
</div>

<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td>
<div class="roundcont filterContainer">
<div class="roundright">
<div class="roundtop">
	<div class="roundtopright"><img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10"
											  class="corner_filter" style="display: none"/></div>
</div>
<div class="roundContent">
<!-- Insert all the search option within this div -->

<c:set var="globalLabelLetter" value=''/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
	<tr>
		<td class="optionTitleBoldCenter" colspan="2">
			<fmt:message key="adhocusagereport.title"/>
				<c:if test = "${adHocUsageReportForm.templateId != null}">
				<tcmis:jsReplace var="userGroupDesc" value="${adHocUsageReportForm.userGroupDesc}" processMultiLines="true" /> 
				<c:set var="tmpVersion" value=''/>
		        <c:if test="${adHocUsageReportForm.owner == 'PERSONNEL_ID'}">
		            <c:set var="tmpVersion" value='${adHocUsageReportForm.createdByName}'/>
		        </c:if>
		        <c:if test="${adHocUsageReportForm.owner == 'USER_GROUP_ID'}">
		            <c:set var="tmpVersion" value='${userGroupDesc}'/>
		        </c:if>
		        <c:if test="${adHocUsageReportForm.owner == 'COMPANY_ID'}">
		            <c:set var="tmpVersion" value='${adHocUsageReportForm.companyName}'/>
		        </c:if>
				<c:set var="globalLabelLetter"><fmt:message key="${adHocUsageReportForm.globalizationLabelLetter}"/></c:set>
				&nbsp;:&nbsp;<c:out value="${globalLabelLetter}"/><c:out value="${adHocUsageReportForm.templateId}"/>-<c:out value="${adHocUsageReportForm.templateName}"/>
				<c:if test="${!empty tmpVersion}">&nbsp;(<c:out value="${tmpVersion}"/>)</c:if>
			</c:if>
		</td>
	</tr>
</table>

<fieldset><legend><fmt:message key="label.searchfields"/></legend>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
<input type="hidden" name="showFlammabilityVocZone" id="showFlammabilityVocZone" value="${showFlammabilityVocZone}"/>
<input type="hidden" name="reportTypeForWorkAreaSearchAdHocTemplate" id="reportTypeForWorkAreaSearchAdHocTemplate" value="AdHocUsage"/> 
<%@ include file="/report/workareasearchadhoctemplate.jsp" %>
<c:if test="${hideDockDeliveryPoint == 'N'}">
<tr>
    <%--
    <c:if test="${sessionScope.personnelBean.client == 'SWA'}">
		<td class="optionTitleBoldRight"><fmt:message key="label.location"/>:</td>
		<td class="optionTitleBoldLeft">
			<select class="selectBox" name="location" id="location">
			</select>
		</td>
	</c:if>
	<c:if test="${sessionScope.personnelBean.client != 'SWA'}">
		<td class="optionTitleBoldRight">&nbsp;</td>
		<td>&nbsp;</td>
	</c:if>
    --%>
    <td class="optionTitleBoldLeft"><fmt:message key="label.dock"/>:&nbsp;
		<select class="selectBox" name="dockId" id="dockId" onchange="dockChanged()">
		</select>
        &nbsp;&nbsp;&nbsp;
        <fmt:message key="label.deliverypoint"/>:&nbsp;
		<select class="selectBox" name="deliveryPoint" id="deliveryPoint">
		</select>
    </td>
</tr>
</c:if>
</table>
<%@ include file="/report/adhocdatefieldtemplate.jsp" %>
<table width="100%" border="0" cellpadding="0" class="tableSearch">   
<%--
<tr>
	<c:if test="${sessionScope.personnelBean.client == 'SWA'}">
		<td class="optionTitleBoldRight">
			<fmt:message key="adhocusagereport.label.reportingcategory"/>:
		</td>
		<td class="optionTitleLeft">
			<select class="selectBox" name="reportCategory" id="reportCategory">
			</select>
		</td>
	</c:if>
	<c:if test="${sessionScope.personnelBean.client != 'SWA'}">
		<td class="optionTitleBoldRight">&nbsp;</td>
		<td>&nbsp;</td>
	</c:if>

    <td class="optionTitleBoldRight">&nbsp;</td>
    <td>&nbsp;</td>
    <html:hidden property="materialCategory" styleId="materialCategory" value=""/>

    <td class="optionTitleBoldRight">
		<fmt:message key="adhocusagereport.label.materialcategory"/>:
	</td>
	<td colspan="3" class="optionTitleBoldLeft">
		<html:select property="materialCategory" styleClass="selectBox" styleId="materialCategory">
			<html:option value=""><fmt:message key="label.selectOne"/></html:option>
			<html:options collection="vvCategoryBeanCollection" name="VvCategoryBean" labelProperty="categoryDesc" property="categoryId"/>
		</html:select>
	</td>
</tr>
--%>

<tr>
	<td class="optionTitleBoldLeft"><fmt:message key="label.partnumber"/>:&nbsp;
		<html:select property="partNumberCriteria" styleClass="selectBox" styleId="partNumberCriteria">
			<html:option value="contains"><fmt:message key="label.contains"/></html:option>
			<html:option value="is"><fmt:message key="label.is"/></html:option>
		</html:select>
		&nbsp;&nbsp;&nbsp;
		<html:text property="partNumber" styleClass="inputBox" styleId="partNumber"/>
		&nbsp;&nbsp;&nbsp;
        <fmt:message key="label.manufacturer"/>:&nbsp;
		<html:select property="manufacturerCriteria" styleClass="selectBox" styleId="manufacturerCriteria">
			<html:option value="contains"><fmt:message key="label.contains"/></html:option>
			<html:option value="is"><fmt:message key="label.is"/></html:option>
		</html:select>
		&nbsp;&nbsp;&nbsp;
		<html:text property="manufacturer" styleId="manufacturer" styleClass="inputBox"/>
	</td>
</tr>
</table>


<script src="/js/report/adhoclistfieldtemplate.js" language="JavaScript"></script>
<%-- the reason that I have the include here is because the data is set here as well as reloadqpldata.jsp --%>
    <%@ include file="/report/adhoclistfieldtemplate.jsp" %>

</fieldset>

<fieldset><legend><fmt:message key="label.reportfields"/></legend>
<%--<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
<tr>
	<td class="optionTitleBoldLeft" colspan="4">&nbsp;
		&nbsp;&nbsp;
		<img src="/images/uparrow.png" onmouseover=style.cursor="hand" onclick="move(true);"/>
		<img src="/images/downarrow.png" onmouseover=style.cursor="hand" onclick="move(false);"/>
	</td> --%>
<%--
	<td width="6%">
		&nbsp; 
	</td>
 
	<td width="10%" class="optionTitleBoldCenter">
		<fmt:message key="adhocusagereport.label.reportfields"/>
	</td> 
	<td width="37%" class="optionTitleLeft">
		<img src="/images/uparrow.png" onmouseover=style.cursor="hand" onclick="move('reportFieldList',true);"/>
		<img src="/images/downarrow.png" onmouseover=style.cursor="hand" onclick="move('reportFieldList',false);"/>
	</td>
	 
</tr>

<tr>
	<td class="optionTitleLeft" width="1%">--%>
<%--		<span style="display:none;">
		<div class="dataContent">
		<div id="fieldBean" style="height:200px;width:235px;display:;"></div>
		<!-- Search results start -->
			<script type="text/javascript">
			<!--
				<c:set var="dataCount" value='${0}'/>
					/*Storing the data to be displayed in a JSON object array.*/
				  var jsonMainData = {
			      rows:[
					  <c:forEach var="fieldBean" items="${reportFieldBeanCollection}" varStatus="status">
						  <c:if test="${status.index != 0 }">,</c:if>
						  { id:${status.index +1},
							  data:[
									'<tcmis:jsReplace value="${fieldBean.name}"/>','${fieldBean.baseFieldId}'
                         ]}
					   <c:set var="dataCount" value='${dataCount+1}'/>
					  </c:forEach>
					]};
			//-->
			</script>
		</div>
		</span> --%>
		
    <%-- move the following into right positions once test is completed --%>
    <script src="/js/report/adhocreportfieldtemplate.js" language="JavaScript"></script>
    <input type="hidden" name="reportType" id="reportType" value="AdHocUsage"/>
    <%-- end move the following into right positions once test is completed --%>

    <%-- the reason that I have the include here is because the data is set here as well as reloadqpldata.jsp --%>
    <%@ include file="/report/adhocreportfieldtemplate.jsp" %>


<%-- 			<select name="reportFieldList" id="reportFieldList" class="selectBox" size="15" multiple>
				<option value="xxblankxx">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</option>
				<c:forEach var="fieldBean" items="${reportFieldBeanCollection}" varStatus="status">
					<option value="${fieldBean.baseFieldId}">${fieldBean.name}</option>
				</c:forEach>
			</select>
	</td>
	<td class="optionTitleLeft">
	<button id="reportFieldsEditBtn" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'"
        	name="None" value=""  OnClick="popReportBaseFields('AdHocUsage');return false;"><fmt:message key="label.editlist"/> </button>
	</td>--%>
<%--
	<td width="47%" class="optionTitleRight">
		<html:select styleClass="selectBox" styleId="foo" size="17" multiple="multiple" property="foo">
			<html:options collection="baseFieldBeanCollection" name="BaseFieldViewBean" labelProperty="name"
							  property="baseFieldId"/>
		</html:select>

	</td>
	<td width="6%" class="optionTitleBoldCenter">
		<img src="/images/rightarrow.png" onmouseover=style.cursor="hand" onclick="transferItem(foo,reportFieldList,false);"/>
		<br>
		<img src="/images/leftarrow.png" onmouseover=style.cursor="hand" onclick="transferItem(reportFieldList,foo,false);"/>
	</td>
	<c:set var="reportFieldCollection" value='${adHocUsageReportForm.reportFieldCollection}'/>
	<td width="47%" class="optionTitleLeft" colspan="2">
		<html:select styleClass="selectBox" styleId="reportFieldList" size="17" multiple="multiple" property="reportFieldList">

			<html:option value="xxblankxx">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</html:option>

			<html:options collection="reportFieldBeanCollection" name="BaseFieldViewBean" labelProperty="name"
							  property="baseFieldId"/>
		</html:select>
	</td>

</tr>
</table> --%>
</fieldset>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
<tr>
	<td class="optionTitleBoldCenter" colspan="4">
		<html:radio styleClass="radioBtns" styleId="reportGenerationType" property="reportGenerationType"
						value="INTERACTIVE"/>
		<fmt:message key="adhocusagereport.label.interactive"/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<html:radio styleClass="radioBtns" styleId="reportGenerationType" property="reportGenerationType" value="BATCH"/>
		<fmt:message key="label.batch"/>
		&nbsp;&nbsp;&nbsp;
		<fmt:message key="adhocusagereport.label.reportname"/>:&nbsp;<html:text property="reportName" styleId="reportName"
																										styleClass="inputBox"/>
        <span id="showHideOpt">
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input name="gateKeeping" id="gateKeeping" type="checkbox" class="radioBtns" <c:if test="${templateBean.gateKeeping == 'Y'}">checked</c:if> value="Y" />
            <fmt:message key="adhocinventoryreport.label.gatekeeping"/>
       </span>
	</td>
</tr>

<tr>
	<html:hidden property="id" styleId="id" value="${adHocUsageReportForm.id}"/>
	<html:hidden property="templateName" styleId="templateName" value="${adHocUsageReportForm.templateName}"/>
	<html:hidden property="templateDescription" styleId="templateDescription" value="${adHocUsageReportForm.templateDescription}"/>
	<html:hidden property="templateId" styleId="templateId" value="${adHocUsageReportForm.templateId}"/>
	<html:hidden property="emailMessage" styleId="emailMessage" value="${adHocUsageReportForm.emailMessage}"/>
    <html:hidden property="emailSubject" styleId="emailSubject" value="${adHocUsageReportForm.emailSubject}"/>
    <html:hidden property="emailUserGroupId" styleId="emailUserGroupId" value="${adHocUsageReportForm.emailUserGroupId}"/>    
    <html:hidden property="gateKeeping" styleId="gateKeeping" value="${adHocUsageReportForm.gateKeeping}"/>    
    <html:hidden property="emailMessageNeg" styleId="emailMessageNeg" value="${adHocUsageReportForm.emailMessageNeg}"/>
    <html:hidden property="emailSubjectNeg" styleId="emailSubjectNeg" value="${adHocUsageReportForm.emailSubjectNeg}"/>    
    <html:hidden property="emailUserGroupIdNeg" styleId="emailUserGroupIdNeg" value="${adHocUsageReportForm.emailUserGroupIdNeg}"/>    
    <html:hidden property="includeOpenOrders" styleId="includeOpenOrders" value="${adHocUsageReportForm.includeOpenOrders}"/>
	<html:hidden property="globalizationLabelLetter" styleId="globalizationLabelLetter" value="${globalLabelLetter}"/>
	<html:hidden property="submitValue" styleId="submitValue"/>  

	<td class="optionTitleCenter" colspan="4">

		<input name="clearTemplateB" id="clearTemplateB" type="submit" class="inputBtns" value="<fmt:message key="label.cleartemplate"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'" onclick="return clearTemplate();">

		<html:button property="openTemplate" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'"
						 onmouseout="this.className='inputBtns'" onclick="openTemplateAudit()">
			<fmt:message key="button.opentemplate"/>
		</html:button>

		<html:button property="saveTemplate" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'"
						 onmouseout="this.className='inputBtns'" onclick="saveTemplateAudit()">
			<fmt:message key="button.savetemplate"/>
		</html:button>

		<c:if test="${adHocUsageReportForm.publishTemplate == 'Y'}">
			<html:button property="publishTemplate" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'"
						 onmouseout="this.className='inputBtns'" onclick="publishTemplateAudit()">
				<fmt:message key="label.publishtemplate"/>
			</html:button>
		</c:if>

		<c:if test="${adHocUsageReportForm.unpublishTemplate == 'Y'}">
			<html:button property="unpublishTemplate" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'"
							 onmouseout="this.className='inputBtns'" onclick="unpublishTemplateAudit()">
				<fmt:message key="label.unpublishtemplate"/>
			</html:button>
		</c:if>

		<html:button property="submitReport" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'"
						 onmouseout="this.className='inputBtns'" onclick="generateAdHocReportAudit()">
			<fmt:message key="button.generatereport"/>
		</html:button>
	</td>
</tr>

</table>
<!-- End search options -->
</div>
<div class="roundbottom">
	<div class="roundbottomright"><img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner"
												  style="display: none"/></div>
</div>
</div>
</div>
</td>
</tr>
</table>
<!-- Search Option Ends -->

<div class="spacerY">&nbsp;</div>

<div id="transitDailogWin" class="errorMessages" style="display: none;overflow: auto;">
    <table width="100%" border="0" cellpadding="2" cellspacing="1">
        <tr><td>&nbsp;</td></tr>
        <tr><td>&nbsp;</td></tr>
        <tr><td>&nbsp;</td></tr>
        <tr>
            <td align="center" id="transitLabel">
            </td>
        </tr>
        <tr>
            <td align="center">
                <img src="/images/rel_interstitial_loading.gif" align="middle">
            </td>
        </tr>
    </table>
</div>


<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages">
	<html:errors/>
</div>
<!-- Error Messages Ends -->


<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;"></div>
<input type="hidden" name="templateFacilityGroupId" id="templateFacilityGroupId" value="${empty adHocUsageReportForm.facilityGroupId?'':adHocUsageReportForm.facilityGroupId}"/>
<input type="hidden" name="facilityName" id="facilityName" value=""/>
<input type="hidden" name="applicationDesc" id="applicationDesc" value=""/>
<input type="hidden" name="dockDesc" id="dockDesc" value=""/>
<input type="hidden" name="deliveryPointDesc" id="deliveryPointDesc" value=""/>
<input type="hidden" name="categoryDesc" id="categoryDesc" value=""/>
<input type="hidden" name="reportingEntity" id="reportingEntity" value=""/>
<input type="hidden" name="templateFacilityId" id="templateFacilityId" value="${adHocUsageReportForm.facilityId}"/>
<input type="hidden" name="templateReportingEntityId" id="templateReportingEntityId" value="${adHocUsageReportForm.reportingEntityId}"/>
<input type="hidden" name="templateDockId" id="templateDockId" value="${adHocUsageReportForm.dockId}"/>
<input type="hidden" name="templateDeliveryPoint" id="templateDeliveryPoint" value="${adHocUsageReportForm.deliveryPoint}"/>
<input type="hidden" name="preSelectDropsDowns" id="preSelectDropsDowns" value="${preSelectDropsDowns}"/>
<input type="hidden" name="uAction" id="uAction" value="${uAction}"/>
<input type="hidden" name="companyId" id="companyId" value="${personnelBean.companyId}"/>
<input type="hidden" name="isMatCatFX" id="isMatCatFX" value=""/>
<input type="hidden" name="supportTriggersThreshold" id="supportTriggersThreshold" value="Y"/>
<input type="hidden" name="catalogCompanyId" id="catalogCompanyId" value="${adHocUsageReportForm.catalogCompanyId}"/>

<input type="hidden" name="roomName" id="roomName" value=""/>
<input type="hidden" name="materialCategoryId" id="materialCategoryId" value="${adHocUsageReportForm.materialCategoryId}"/>
<input type="hidden" name="templateAreaId" id="templateAreaId" value="${adHocUsageReportForm.areaId}"/>
<input type="hidden" name="templateAreaName" id="templateAreaName" value="${adHocUsageReportForm.areaName}"/>
<input type="hidden" name="templateBuildingId" id="templateBuildingId" value="${adHocUsageReportForm.buildingId}"/>
<input type="hidden" name="templateBuildingName" id="templateBuildingName" value="${adHocUsageReportForm.buildingName}"/>
<input type="hidden" name="templateFloorId" id="templateFloorId" value="${adHocUsageReportForm.floorId}"/>
<input type="hidden" name="floorName" id="floorName" value=""/>
<input type="hidden" name="templateRoomId" id="templateRoomId" value="${adHocUsageReportForm.roomId}"/>
<input type="hidden" name="templateDeptId" id="templateDeptId" value="${adHocUsageReportForm.deptId}"/>
<input type="hidden" name="templateDeptName" id="templateDeptName" value="${adHocUsageReportForm.deptName}"/>
<input type="hidden" name="templateApplication" id="templateApplication" value="${adHocUsageReportForm.application}"/>
<input type="hidden" name="templateApplicationDesc" id="templateApplicationDesc" value="${adHocUsageReportForm.applicationDesc}"/>
<input type="hidden" name="thisshoudlbnevepoup" id="thisshoudlbnevepoup" value="${adHocUsageReportForm.applicationDesc}"/>

<input type="hidden" name="templateMaterialSubcategoryId" id="templateMaterialSubcategoryId" value="${adHocUsageReportForm.materialSubcategoryId}"/>
<input type="hidden" name="templateMaterialSubcategoryDesc" id="templateMaterialSubcategoryName" value="${adHocUsageReportForm.materialSubcategoryName}"/>
<input type="hidden" name="templateMaterialCategoryId" id="templateMaterialCategoryId" value="${adHocUsageReportForm.materialCategoryId}"/>
<input type="hidden" name="templateMaterialSubcategoryId" id="templateMaterialCategoryName" value="${adHocUsageReportForm.materialCategoryName}"/>
<input type="hidden" name="templateCatalogId" id="templateCatalogId" value="${adHocUsageReportForm.catalogId}"/>

<input type="hidden" name="templateVocZoneId" id="templateVocZoneId" value="${adHocUsageReportForm.vocZoneId}"/>
<input type="hidden" name="templateVocZoneDescription" id="templateVocZoneDescription" value="${adHocUsageReportForm.vocZoneDescription}"/>
<input type="hidden" name="templateFlammabilityControlZoneId" id="templateFlammabilityControlZoneId" value="${adHocUsageReportForm.flammabilityControlZoneId}"/>
<input type="hidden" name="templateFlammabilityControlZoneDesc" id="templateFlammabilityControlZoneDesc" value="${adHocUsageReportForm.flammabilityControlZoneDesc}"/>
<input type="hidden" name="templateOverFlamCtrlZnLimit" id="templateOverFlamCtrlZnLimit" value="${adHocUsageReportForm.overFlamCtrlZnLimit}"/>
<input type="hidden" name="templateOverFlamCtrlZnLmtPercent" id="templateOverFlamCtrlZnLmtPercent" value="${adHocUsageReportForm.overFlamCtrlZnLmtPercent}"/>
<input type="hidden" name="templateFlammable" id="templateFlammable" value="${adHocUsageReportForm.flammable}"/>

<!-- Hidden elements end -->

</div>
<!-- close of contentArea -->

<!-- Footer message start -->
<div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div>
<!-- close of interface -->

</tcmis:form>
</body>
</html:html>
