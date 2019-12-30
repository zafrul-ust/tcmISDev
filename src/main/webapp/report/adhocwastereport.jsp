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
<tcmis:fontSizeCss/>
	<%--
 <link rel="stylesheet" type="text/css" href="/css/haasGlobal.css"></link>
 --%>
	<%-- Add any other stylesheets you need for the page here --%>


	<%--
 <link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
 <link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
 --%>

<script src="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>

	<%-- For Calendar support --%>

<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>


	<%-- Add any other javascript you need for the page here --%>
<script src="/js/report/adhocwastereport.js" language="JavaScript"></script>
<script language="JavaScript" type="text/javascript">
<!--
var altFacilityId = new Array(<c:forEach var="wasteFacAppActVendorViewBean" items="${wasteFacAppActVendorViewBeanCollection}" varStatus="status">
	 <c:choose>
	 <c:when test="${status.index > 0}">, '<tcmis:jsReplace value="${status.current.facilityId}"/>'
</c:when>
<c:otherwise>
'<tcmis:jsReplace value="${status.current.facilityId}"/>'
</c:otherwise>
</c:choose>
</c:forEach>
)
;

var altFacilityName = new Array(<c:forEach var="wasteFacAppActVendorViewBean" items="${wasteFacAppActVendorViewBeanCollection}" varStatus="status">
	 <c:choose>
	 <c:when test="${status.index > 0}">, '<tcmis:jsReplace value="${status.current.facilityName}"/>'
</c:when>
<c:otherwise>
'<tcmis:jsReplace value="${status.current.facilityName}"/>'
</c:otherwise>
</c:choose>
</c:forEach>
)
;

var altApplication = new Array();
<c:forEach var="wasteFacAppActVendorViewBean" items="${wasteFacAppActVendorViewBeanCollection}" varStatus="status">
<c:set var="currentFacility" value='${status.current.facilityId}'/>
<c:set var="applicationBeanCollection" value='${status.current.applicationCollection}'/>

<c:set var="applicationCount" value='${0}'/>
altApplication['<tcmis:jsReplace value="${currentFacility}"/>'] = new Array(<c:forEach var="facLocAppBean" items="${applicationBeanCollection}" varStatus="status1">
	 <%--
			<c:if test="${status1.index == 0}">
	 "All","My Work Areas",
  </c:if>
 --%>
	 <c:set var="applicationCount" value='${applicationCount+1}'/>
	 <c:choose>
	 <c:when test="${applicationCount > 1}">, '<tcmis:jsReplace value="${status1.current.application}"/>'
</c:when>
<c:otherwise>
'<tcmis:jsReplace value="${status1.current.application}"/>'
</c:otherwise>
</c:choose>
</c:forEach>
)
;
</c:forEach>

var altApplicationDesc = new Array();
<c:forEach var="wasteFacAppActVendorViewBean" items="${wasteFacAppActVendorViewBeanCollection}" varStatus="status">
<c:set var="currentFacility" value='${status.current.facilityId}'/>
<c:set var="applicationBeanCollection" value='${status.current.applicationCollection}'/>

<c:set var="applicationCount" value='${0}'/>
altApplicationDesc['<tcmis:jsReplace value="${currentFacility}"/>'] = new Array(<c:forEach var="facLocAppBean" items="${applicationBeanCollection}" varStatus="status1">
	 <%--
  <c:if test="${status1.index == 0}">
	 "All","My Work Areas",
  </c:if>
 --%>
	 <c:set var="applicationCount" value='${applicationCount+1}'/>
	 <c:choose>
	 <c:when test="${applicationCount > 1}">, '<tcmis:jsReplace value="${status1.current.applicationDesc}"/>'
</c:when>
<c:otherwise>
'<tcmis:jsReplace value="${status1.current.applicationDesc}"/>'
</c:otherwise>
</c:choose>

</c:forEach>
)
;
</c:forEach>

var altWasteLoc = new Array();
<c:forEach var="wasteFacAppActVendorViewBean" items="${wasteFacAppActVendorViewBeanCollection}" varStatus="status">
<c:set var="currentFacility" value='${status.current.facilityId}'/>
<c:set var="applicationBeanCollection" value='${status.current.applicationCollection}'/>

<c:forEach var="facLocAppBean" items="${applicationBeanCollection}" varStatus="status1">

<c:set var="currentApplication" value='${status1.current.application}'/>
<c:set var="wasteBeanCollection" value='${status1.current.wasteLocationIdCollection}'/>
<c:set var="wasteCount" value='${0}'/>

altWasteLoc['<tcmis:jsReplace value="${currentFacility}"/>-<tcmis:jsReplace value="${currentApplication}" />'] = new Array(

	 <c:forEach var="wasteLocBean" items="${wasteBeanCollection}" varStatus="status2">
	 <%--
  <c:if test="${status2.index == 0}">
	 "All","My Locations",
  </c:if>
 --%>
	 <c:set var="wasteCount" value='${wasteCount+1}'/>
	 <c:choose>
	 <c:when test="${wasteCount > 1}">, '<tcmis:jsReplace value="${status2.current.wasteLocationId}"/>'
</c:when>
<c:otherwise>
'<tcmis:jsReplace value="${status2.current.wasteLocationId}"/>'
</c:otherwise>
</c:choose>
</c:forEach>

)
;

</c:forEach>

</c:forEach>

var altWasteLocDesc = new Array();
<c:forEach var="wasteFacAppActVendorViewBean" items="${wasteFacAppActVendorViewBeanCollection}" varStatus="status">
<c:set var="currentFacility" value='${status.current.facilityId}'/>
<c:set var="applicationBeanCollection" value='${status.current.applicationCollection}'/>
<c:forEach var="facLocAppBean" items="${applicationBeanCollection}" varStatus="status1">
<c:set var="currentApplication" value='${status1.current.application}'/>
<c:set var="wasteBeanCollection" value='${status1.current.wasteLocationIdCollection}'/>
<c:set var="wasteCount" value='${0}'/>
altWasteLocDesc['<tcmis:jsReplace value="${currentFacility}"/>-<tcmis:jsReplace value="${currentApplication}" />'] = new Array(<c:forEach var="wasteLocBean" items="${wasteBeanCollection}" varStatus="status2">
	 <%--
  <c:if test="${status2.index == 0}">
	 "All","My Locations",
  </c:if>
 --%>
	 <c:set var="wasteCount" value='${wasteCount+1}'/>
	 <c:choose>
	 <c:when test="${wasteCount > 1}">, '<tcmis:jsReplace value="${status2.current.wasteLocationId}"/>'
</c:when>
<c:otherwise>
'<tcmis:jsReplace value="${status2.current.wasteLocationId}"/>'
</c:otherwise>
</c:choose>
</c:forEach>
)
;
</c:forEach>
</c:forEach>


var altVendor = new Array();
<c:forEach var="wasteFacAppActVendorViewBean" items="${wasteFacAppActVendorViewBeanCollection}" varStatus="status">
<c:set var="currentFacility" value='${status.current.facilityId}'/>
<c:set var="vendorBeanCollection" value='${status.current.vendorIdCollection}'/>

<c:set var="vendorCount" value='${0}'/>
altVendor['<tcmis:jsReplace value="${currentFacility}"/>'] = new Array(<c:forEach var="vendorBean" items="${vendorBeanCollection}" varStatus="status1">
	 <%--
  <c:if test="${status1.index == 0}">
	 "All","My Vendors",
  </c:if>
 --%>
	 <c:set var="vendorCount" value='${vendorCount+1}'/>
	 <c:choose>
	 <c:when test="${vendorCount > 1}">, '<tcmis:jsReplace value="${status1.current.vendorId}"/>'
</c:when>
<c:otherwise>
'<tcmis:jsReplace value="${status1.current.vendorId}"/>'
</c:otherwise>
</c:choose>
</c:forEach>
)
;
</c:forEach>

var altVendorDesc = new Array();
<c:forEach var="wasteFacAppActVendorViewBean" items="${wasteFacAppActVendorViewBeanCollection}" varStatus="status">
<c:set var="currentFacility" value='${status.current.facilityId}'/>
<c:set var="vendorBeanCollection" value='${status.current.vendorIdCollection}'/>

<c:set var="vendorCount" value='${0}'/>
altVendorDesc['<tcmis:jsReplace value="${currentFacility}"/>'] = new Array(<c:forEach var="vendorBean" items="${vendorBeanCollection}" varStatus="status1">
	 <%--
  <c:if test="${status1.index == 0}">
	 "All","My Vendors",
  </c:if>
 --%>
	 <c:set var="vendorCount" value='${vendorCount+1}'/>
	 <c:choose>
	 <c:when test="${vendorCount > 1}">, '<tcmis:jsReplace value="${status1.current.companyName}"/>'
</c:when>
<c:otherwise>
'<tcmis:jsReplace value="${status1.current.companyName}"/>'
</c:otherwise>
</c:choose>

</c:forEach>
)
;
</c:forEach>

var altBaseFieldId = new Array(<c:forEach var="baseFieldViewBean" items="${baseFieldBeanCollection}" varStatus="status">
	 <c:choose>
	 <c:when test="${status.index > 0}">, "<c:out value="${status.current.baseFieldId}"/>"
</c:when>
<c:otherwise>
"<c:out value="${status.current.baseFieldId}"/>"
</c:otherwise>
</c:choose>
</c:forEach>
);

var altBaseFieldIdCompatibility = new Array(<c:forEach var="baseFieldViewBean" items="${baseFieldBeanCollection}" varStatus="status">
	 <c:choose>
	 <c:when test="${status.index > 0}">, "<c:out value="${status.current.compatibility}"/>"
</c:when>
<c:otherwise>
"<c:out value="${status.current.compatibility}"/>"
</c:otherwise>
</c:choose>
</c:forEach>
);
// -->
</script>

	<%-- These are for the Grid, uncomment if you are going to use the grid --%>
	<%--<script src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
 <script src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
 <script src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
 <script src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
 --%>

	<%-- This is for the YUI, uncomment if you will use this --%>
	<%--<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
	<script type="text/javascript" src="/yui/build/event/event.js" ></script>
	<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
	<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
	<script type="text/javascript" src="/yui/build/container/container.js"></script>
	<script type="text/javascript" src="/js/common/waitDialog.js"></script>
	<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>--%>

<title>
	<fmt:message key="adhocwastereport.title"/>
</title>

<script language="JavaScript" type="text/javascript">
	<!--
	//add all the javascript messages here, this for internationalization of client side javascript messages
	var messagesData = new Array();
	messagesData = {alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
		submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
		all:"<fmt:message key="label.all"/>",
		myworkareas:"<fmt:message key="label.myworkareas"/>",
		thereisnoitemtomove:"<fmt:message key="label.thereisnoitemtomove"/>",
		selectanitemthatyouwanttomove:"<fmt:message key="label.selectanitemthatyouwanttomove"/>",
		youmustfirstselectitemtoreorder:"<fmt:message key="label.youmustfirstselectitemtoreorder"/>",
		youmustselectreportfield:"<fmt:message key="label.youmustselectreportfield"/>",
		youmustentercasnumber:"<fmt:message key="label.youmustentercasnumber"/>",
		pleaseenterreportname:"<fmt:message key="label.pleaseenterreportname"/>",
		pleaseselectvendor:"<fmt:message key="label.pleaseselectvendor"/>",
		begindaterequired:"<fmt:message key="label.begindaterequired"/>",
		adhocwastecompatibilityerror:"<fmt:message key="label.adhocwastecompatibilityerror"/>",
		enddaterequired:"<fmt:message key="label.enddaterequired"/>"};
	// -->
</script>
</head>

<body bgcolor="#ffffff" onload="loadForm();">
<c:if test="org.apache.struts.action.MESSAGE == null">
	<div class="errorMessages">
		ERROR: Application resources not loaded
	</div>
</c:if>
<c:set var="facilityCollection" value='${requestScope.wasteFacAppActVendorViewBeanCollection}'/>
<tcmis:form action="/adhocwastereport.do" onsubmit="return submitOnlyOnce();">

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
				<fmt:message key="adhocwastereport.title"/>
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
	<td class="optionTitleBoldCenter" colspan="4">
		<fmt:message key="adhocwastereport.title"/>
		<c:if test = "${adHocWasteReportForm.templateName != null}">
			<c:set var="globalLabelLetter"><fmt:message key="${adHocWasteReportForm.globalizationLabelLetter}"/></c:set>
			&nbsp;:&nbsp;<c:out value="${globalLabelLetter}"/><c:out value="${adHocWasteReportForm.templateId}"/>-<c:out value="${adHocWasteReportForm.templateName}"/>
		</c:if>
	</td>
</tr>
</table>

<fieldset><legend><fmt:message key="label.searchfields"/></legend>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
<tr>
	<td class="optionTitleBoldRight">
		<fmt:message key="label.facility"/>:
	</td>
	<td class="optionTitleLeft">
		<c:if test="${adHocWasteReportForm.facilityId == null || adHocWasteReportForm.facilityId == ''}">
			<html:select styleClass="selectBox" styleId="facilityId" property="facilityId" onchange="facilityChanged()"
							 value="${personnelBean.facilityId}">
				<html:option value=""><fmt:message key="label.all"/></html:option>
				<html:options collection="wasteFacAppActVendorViewBeanCollection" name="WasteFacAppActVendorViewBean"
								  labelProperty="facilityName" property="facilityId"/>
			</html:select>
		</c:if>
		<c:if test="${adHocWasteReportForm.facilityId != null && adHocWasteReportForm.facilityId != ''}">
			<html:select styleClass="selectBox" styleId="facilityId" property="facilityId" onchange="facilityChanged()">
				<html:option value=""><fmt:message key="label.all"/></html:option>
				<html:options collection="wasteFacAppActVendorViewBeanCollection" name="WasteFacAppActVendorViewBean"
								  labelProperty="facilityName" property="facilityId"/>
			</html:select>
		</c:if>
	</td>

	<td class="optionTitleBoldRight"><fmt:message key="label.begindate"/>:</td>
	<td>
		<html:text size="10" property="beginDateJsp" styleClass="inputBox"
					  onclick="return getCalendar(document.adHocWasteReportForm.beginDateJsp,null,null,null,document.adHocWasteReportForm.endDateJsp);"
					  styleId="beginDateJsp" onfocus="blur();"/> <a href="javascript: void(0);" ID="linkbeginDateJsp"
																				STYLE="color:#0000ff;cursor:pointer;text-decoration:underline"></a>

	</td>

</tr>

<tr>
	<td class="optionTitleBoldRight">
		<fmt:message key="label.workarea"/>:
	</td>
	<td class="optionTitleLeft">

		<html:select styleClass="selectBox" styleId="application" property="application" onchange="applicationChanged()">
			<%--
			<html:option value="All"><fmt:message key="label.all"/></html:option>
         --%>

			<c:if test="${applicationCollection == null}">
				<c:set var="appColl" value='${wasteFacAppActVendorViewBeanCollection[0].applicationCollection}'/>
			</c:if>
			<c:if test="${!empty applicationCollection}">
				<c:set var="appColl" value='${applicationCollection}'/>
			</c:if>
			<html:options collection="appColl" labelProperty="applicationDesc" property="application"/>
			<%--
		 <c:if test="${!empty applicationCollection}">
	<html:options collection="applicationCollection" labelProperty="applicationDesc" property="application"/>
	</c:if>
	--%>
		</html:select>

	</td>
	<td class="optionTitleBoldRight"><fmt:message key="label.enddate"/>:</td>
	<td class="optionTitleLeft">
		<html:text size="10" property="endDateJsp" styleClass="inputBox" styleId="endDateJsp"
					  onclick="return getCalendar(document.adHocWasteReportForm.endDateJsp,null,null,document.adHocWasteReportForm.beginDateJsp);"
					  onfocus="blur();"/> <a href="javascript: void(0);" ID="linkendDateJsp"
													 STYLE="color:#0000ff;cursor:pointer;text-decoration:underline"></a>
	</td>
</tr>


<tr>
	<td class="optionTitleBoldRight">
		<fmt:message key="adhocwastereport.label.accumulationpoint"/>:
	</td>
	<td class="optionTitleLeft">
		<html:select styleClass="selectBox" styleId="accumulationPoint" property="accumulationPoint">

			<html:option value="All"><fmt:message key="label.all"/></html:option>
			<%--
	  <c:if test="${accumulationPointCollection == null}">
		 <c:set var="accumulationPointCollection" value='${wasteFacAppActVendorViewBeanCollection[0].applicationCollection[0].wasteLocationIdCollection}'/>
	  </c:if>
	  <c:if test="${!empty accumulationPointCollection}">
		 <c:set var="accumulationPointCollection" value='${accumulationPointCollection}'/>
	  </c:if>
	<html:options collection="accumulationPointCollection" labelProperty="wasteLocationId" property="wasteLocationId"/>

	--%>
			<c:if test="${!empty accumulationPointCollection}">
				<html:options collection="accumulationPointCollection" labelProperty="wasteLocationId"
								  property="wasteLocationId"/>
			</c:if>

		</html:select>
	</td>
		<td class="optionTitleBoldRight">
		<fmt:message key="adhocwastereport.label.managementoption"/>:
	</td>
	<td class="optionTitleLeft" rowspan="2">
		<html:hidden property="managementOption" value="" styleId="managementOption"/>
		<html:textarea styleClass="inputBox" property="managementOptionDesc" rows="4" cols="20"/>
		<html:button property="search" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'"
						 onmouseout="this.className='inputBtns'" onclick="getManagementOptionSearch()">
			<fmt:message key="button.search"/>
		</html:button>
	</td>

</tr>

<tr>
	<td class="optionTitleBoldRight">
		<fmt:message key="label.vendor"/>:
	</td>
	<td class="optionTitleLeft">
		<html:select styleClass="selectBox" styleId="vendor" property="vendor">
			<html:option value="All"><fmt:message key="label.all"/></html:option>
			<c:if test="${!empty vendorCollection}">
				<html:options collection="vendorCollection" labelProperty="companyName" property="vendorId"/>
			</c:if>

		</html:select>
	</td>
	<td>&nbsp;</td>

</tr>

<tr>
	<td class="optionTitleBoldRight">
		<fmt:message key="adhocwastereport.label.profile"/>:
	</td>
	<td class="optionTitleLeft">
		<html:hidden property="profileId" value="" styleId="profileId"/>
		<html:text property="profileDesc" styleClass="inputBox" styleId="profileDesc"/>
		<html:button property="search" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'"
						 onmouseout="this.className='inputBtns'" onclick="getProfileSearch(this.form.vendor)">
			<fmt:message key="button.search"/>
		</html:button>
	</td>
	<td class="optionTitleBoldLeft">
		&nbsp;
	</td>

	<td class="optionTitleBoldLeft">
		&nbsp;
	</td>
</tr>

<tr>
	<td class="optionTitleBoldRight">
		&nbsp;
	</td>
	<td class="optionTitleBoldLeft">
		<html:checkbox styleClass="radioBtns" styleId="excludeWaste" property="excludeWaste" value="Y"/><fmt:message
		 key="adhocwastereport.label.excludehubwaste"/>
	</td>
	<td class="optionTitleBoldLeft">
		&nbsp;
	</td>

	<td class="optionTitleBoldLeft">
		&nbsp;
	</td>
</tr>
</table>
</fieldset>

<fieldset><legend><fmt:message key="label.reportfields"/></legend>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
<tr>
	<td width="47%" class="optionTitleBoldRight">
		<fmt:message key="adhocwastereport.label.basefields"/> <a href="#" onclick="showBaseFields('AdHocWaste');">
		(<fmt:message key="label.description"/>)</a>
	</td>
	<td width="6%" class="optionTitleBoldLeft">
		&nbsp;
	</td>
	<td width="10%" class="optionTitleBoldCenter">
		<fmt:message key="adhocwastereport.label.reportfields"/>
	</td>
	<td width="37%" class="optionTitleLeft">
		<img src="/images/uparrow.png" onmouseover=style.cursor="hand" onclick="move(reportFieldList,true);"/>
		<img src="/images/downarrow.png" onmouseover=style.cursor="hand" onclick="move(reportFieldList,false);"/>
	</td>
</tr>

<tr>
	<td width="47%" class="optionTitleBoldRight">
		<html:select styleClass="selectBox" styleId="foo" size="17" multiple="multiple" property="foo">
			<html:options collection="baseFieldBeanCollection" name="BaseFieldViewBean" labelProperty="name"
							  property="baseFieldId"/>
		</html:select>
	</td>
	<td width="6%" class="optionTitleBoldCenter">
		<img src="/images/rightarrow.png" onmouseover=style.cursor="hand" onclick="transferItem(foo,reportFieldList);"/>
		<br>
		<img src="/images/leftarrow.png" onmouseover=style.cursor="hand" onclick="transferItem(reportFieldList,foo);"/>
	</td>
	<c:set var="reportFieldCollection" value='${adHocWasteReportForm.reportFieldCollection}'/>
	<td width="47%" class="optionTitleLeft" colspan="2">
		<html:select styleClass="selectBox" styleId="reportFieldList" size="17" multiple="multiple" property="reportFieldList">
			<html:option value="xxblankxx">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</html:option>
			
			<html:options collection="reportFieldBeanCollection" name="ReportTemplateFieldBean" labelProperty="name"
							  property="baseFieldId"/>
		</html:select>
	</td>
</tr>
</table>
</fieldset>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
<tr>
	<td class="optionTitleBoldCenter" colspan="4">
		<html:radio styleClass="radioBtns" styleId="reportGenerationType" property="reportGenerationType"
						value="INTERACTIVE"/>
		<fmt:message key="adhocwastereport.label.interactive"/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<html:radio styleClass="radioBtns" styleId="reportGenerationType" property="reportGenerationType" value="BATCH"/>
		<fmt:message key="label.batch"/>
		&nbsp;&nbsp;&nbsp;
		<fmt:message key="adhocwastereport.label.reportname"/>:&nbsp; <html:text property="reportName"
																										 styleClass="inputBox"
																										 styleId="reportName"/>
	</td>
</tr>
<tr>
	<html:hidden property="id" styleId="id" value="${adHocWasteReportForm.id}"/>
	<html:hidden property="templateName" styleId="templateName" value="${adHocWasteReportForm.templateName}"/>
	<html:hidden property="templateDescription" styleId="templateDescription" value="${adHocWasteReportForm.templateDescription}"/>
	<html:hidden property="templateId" styleId="templateId" value="${adHocWasteReportForm.templateId}"/>
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

		<c:if test="${adHocWasteReportForm.publishTemplate == 'Y'}">
			<html:button property="publishTemplate" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'"
						 onmouseout="this.className='inputBtns'" onclick="publishTemplateAudit()">
				<fmt:message key="label.publishtemplate"/>
			</html:button>
		</c:if>

		<c:if test="${adHocWasteReportForm.unpublishTemplate == 'Y'}">
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

<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages">
	<html:errors/>
</div>
<!-- Error Messages Ends -->


<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;"></div>
<input type="hidden" name="facilityName" id="facilityName" value=""/>
<input type="hidden" name="applicationDesc" id="applicationDesc" value=""/>
<input type="hidden" name="accumulationPointDesc" id="accumulationPointDesc" value=""/>
<input type="hidden" name="vendorDesc" id="vendorDesc" value=""/>
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
