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
<script type="text/javascript" src="/js/calendar/date.js"></script>
<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>


<!--  These are for the autocomplete function -->
<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script> 
<script type="text/javascript" src="/js/jquery/autocomplete.js"></script> 
<link rel="stylesheet" type="text/css" href="/css/autocomplete.css" /> 

<script type='text/javascript' src='/js/jquery/ajaxQueue.js'></script>

	<%-- Add any other javascript you need for the page here --%>
<script src="/js/report/adhocusagereportoriginal.js" language="JavaScript"></script>
<script language="JavaScript" type="text/javascript">
<!--
var module =  '<tcmis:module/>';

var altFacilityId = new Array(
<c:forEach var="facAppReportViewBean" items="${facAppReportViewBeanCollection}" varStatus="status">
	<c:if test="${status.index > 0}">,</c:if>
	'<tcmis:jsReplace value="${status.current.facilityId}" processMultiLines="true"/>'
</c:forEach>
);

var altFacilityName = new Array(
<c:forEach var="facAppReportViewBean" items="${facAppReportViewBeanCollection}" varStatus="status">
	<c:if test="${status.index > 0}">,</c:if>
	'<tcmis:jsReplace value="${status.current.facilityName}" processMultiLines="true"/>'
</c:forEach>
);

var altReportingEntityLabel = new Array(
<c:forEach var="facAppReportViewBean" items="${facAppReportViewBeanCollection}" varStatus="status">
	<c:if test="${status.index > 0}">,</c:if>
	'<tcmis:jsReplace value="${status.current.reportingEntityLabel}" processMultiLines="true"/>'
</c:forEach>
);

var altReportingEntity = new Array();
<c:forEach var="facAppReportViewBean" items="${facAppReportViewBeanCollection}" varStatus="status">
	<c:set var="currentFacility" value='${status.current.facilityId}'/>
	<c:set var="reportingEntityBeanCollection" value='${status.current.reportingEntityBeanCollection}'/>
	altReportingEntity["<c:out value="${currentFacility}"/>"] = new Array(
		<c:forEach var="facLocAppBean" items="${reportingEntityBeanCollection}" varStatus="status1">
			<c:if test="${status1.index > 0}">,</c:if>
			'<tcmis:jsReplace value="${status1.current.reportingEntityId}" processMultiLines="true"/>'
		</c:forEach>
);
</c:forEach>

var altReportingEntityDesc = new Array();
<c:forEach var="facAppReportViewBean" items="${facAppReportViewBeanCollection}" varStatus="status">
	<c:set var="currentFacility" value='${status.current.facilityId}'/>
	<c:set var="reportingEntityBeanCollection" value='${status.current.reportingEntityBeanCollection}'/>
	altReportingEntityDesc["<c:out value="${currentFacility}"/>"] = new Array(
		<c:forEach var="facLocAppBean" items="${reportingEntityBeanCollection}" varStatus="status1">
			<c:if test="${status1.index > 0}">,</c:if>
			'<tcmis:jsReplace value="${status1.current.reportingEntityDescription}" processMultiLines="true"/>'
		</c:forEach>
);
</c:forEach>

//now do applications
var altApplication = new Array();
<c:forEach var="facAppReportViewBean" items="${facAppReportViewBeanCollection}" varStatus="status">
	<c:set var="currentFacility" value='${status.current.facilityId}'/>
   <c:set var="reportingEntityColl" value='${status.current.reportingEntityBeanCollection}'/>
	<c:forEach var="facAppReportViewBean2" items="${reportingEntityColl}" varStatus="status1">
		<c:set var="currentEntity" value='${status1.current.reportingEntityId}'/>
		<c:set var="applicationBeanCollection" value='${status1.current.applicationBeanCollection}'/>
		altApplication['<tcmis:jsReplace value="${currentFacility}"/><tcmis:jsReplace value="${currentEntity}"/>'] = new Array(
			<c:forEach var="facLocAppBean" items="${applicationBeanCollection}" varStatus="status2">
				<c:if test="${status2.index > 0}">,</c:if>
				'<tcmis:jsReplace value="${status2.current.application}" processMultiLines="true"/>'
			</c:forEach>
		);
	</c:forEach>
</c:forEach>

var altApplicationDesc = new Array();
<c:forEach var="facAppReportViewBean" items="${facAppReportViewBeanCollection}" varStatus="status">
	<c:set var="currentFacility" value='${status.current.facilityId}'/>
   <c:set var="reportingEntityColl" value='${status.current.reportingEntityBeanCollection}'/>
	<c:forEach var="facAppReportViewBean2" items="${reportingEntityColl}" varStatus="status1">
		<c:set var="currentEntity" value='${status1.current.reportingEntityId}'/>
		<c:set var="applicationBeanCollection" value='${status1.current.applicationBeanCollection}'/>
		altApplicationDesc['<tcmis:jsReplace value="${currentFacility}"/><tcmis:jsReplace value="${currentEntity}"/>'] = new Array(
			<c:forEach var="facLocAppBean" items="${applicationBeanCollection}" varStatus="status2">
				<c:if test="${status2.index > 0}">,</c:if>
				'<tcmis:jsReplace value="${status2.current.applicationDesc}" processMultiLines="true"/>'
			</c:forEach>
		);
	</c:forEach>
</c:forEach>

var altDock = new Array();
<c:forEach var="facAppDockDpViewBean" items="${facAppDockDpViewBeanCollection}" varStatus="status">
	<c:set var="currentFacility" value='${status.current.facilityId}'/>
	<c:set var="dockBeanCollection" value='${status.current.dockBeanCollection}'/>
	altDock['<tcmis:jsReplace value="${currentFacility}"/>'] = new Array(
		<c:forEach var="facLocAppBean" items="${dockBeanCollection}" varStatus="status1">
			<c:if test="${status1.index > 0}">,</c:if>
			'<tcmis:jsReplace value="${status1.current.dockLocationId}" processMultiLines="true"/>'
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
			'<tcmis:jsReplace value="${status1.current.dockDesc}" processMultiLines="true"/>'
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
				'<tcmis:jsReplace value="${status2.current.deliveryPoint}" processMultiLines="true"/>'
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
				'<tcmis:jsReplace value="${status2.current.deliveryPointDesc}" processMultiLines="true"/>'
			</c:forEach>
		);
	</c:forEach>
</c:forEach>

var altBaseFieldId = new Array(
<c:forEach var="baseFieldViewBean" items="${baseFieldBeanCollection}" varStatus="status">
	<c:if test="${status.index > 0}">,</c:if>
	'<tcmis:jsReplace value="${status.current.baseFieldId}" processMultiLines="true"/>'
</c:forEach>
);

var altBaseFieldIdCompatibility = new Array(
<c:forEach var="baseFieldViewBean" items="${baseFieldBeanCollection}" varStatus="status">
	<c:if test="${status.index > 0}">,</c:if>
	'<tcmis:jsReplace value="${status.current.compatibility}" processMultiLines="true"/>'
</c:forEach>
);

var listOptionArray = [
    <c:forEach var="listBean" items="${listOptionBeanCollection}" varStatus="status">
    <c:if test="${adHocUsageReportForm.chemicalListName  == status.current.listId}"><c:set var='savedListName'><tcmis:jsReplace value="${status.current.listName}"/></c:set></c:if>
    <c:if test="${ status.index !=0 }">,</c:if>
        {
          id:   '${status.current.listId}',
          name: '<tcmis:jsReplace value="${status.current.listName}" processMultiLines="true"/>'
        }
    </c:forEach>
]; 

j$().ready(function() {
	function log(event, data, formatted) {
		if( typeof(data) == 'undefined') return false;
		j$('#chemicalListName').val(data.id);
		$("listDesc").className = "inputBox"; 
	} 
	
	j$("#listDesc").autocomplete(listOptionArray, {
		minChars: 1,
		width: 700,
		max: 300,
		matchContains:true,
		cacheLength:0,
		//matchContains: "word",
		autoFill: false,
		formatItem: function(row, i, max) {
			return row.id + ": " + row.name;
		},
		formatMatch: function(row, i, max) {
			return row.id + "||"+row.name;
		},
		formatResult: function(row) {
			return row.name;
		}
		
	});
	
	j$('#listDesc').bind('keyup',(function(e) {
		  var keyCode = (e.keyCode ? e.keyCode : e.which);

		  if( keyCode != 13 && keyCode != 9) // 13 is for Enter; 9 is for Tab
		  	invalidateList();
	}));
	
	j$("#listDesc").result(log).next().click(function() {
		j$(this).prev().search();
	});
});

function invalidateList()
{
 var listDesc  =  document.getElementById("listDesc");
 var listId  =  document.getElementById("chemicalListName");
 if (listDesc.value.length == 0) {
   listDesc.className = "inputBox";
 }else {
   listDesc.className = "inputBox red";
 }
 //set to empty
 listId.value = "";
}

function clearList() {
	$("chemicalListName").value = "";
	$("listDesc").value = "";
	$("listDesc").className = "inputBox";
}

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
		dateformat:"<fmt:message key="javascript.dateformat"/>"};
	// -->
</script>
</head>

<body bgcolor="#ffffff" onload="loadForm();">
<c:if test="org.apache.struts.action.MESSAGE == null">
	<div class="errorMessages">
		ERROR: Application resources not loaded.
	</div>
</c:if>

<tcmis:form action="/adhocusageoriginal.do" onsubmit="return submitOnlyOnce();">

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
				<c:set var="globalLabelLetter"><fmt:message key="${adHocUsageReportForm.globalizationLabelLetter}"/></c:set>
				&nbsp;:&nbsp;<c:out value="${globalLabelLetter}"/><c:out value="${adHocUsageReportForm.templateId}"/>-<c:out value="${adHocUsageReportForm.templateName}"/>
			</c:if>
		</td>
	</tr>
</table>

<fieldset><legend><fmt:message key="label.searchfields"/></legend>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
    <tr>
		<td class="optionTitleBoldLeft">
			<html:radio styleClass="radioBtns" styleId="reportType" property="reportType" value="list" onclick="reportTypeClicked()"/>
			<fmt:message key="label.list"/>
		</td>

		<td class="optionTitleLeft">
			<input type="hidden" id="chemicalListName" name="chemicalListName" value="${adHocUsageReportForm.chemicalListName}" />
			<input type="text" id="listDesc" size="80" class="inputBox"  value="${savedListName}" />
			<button class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'"
                                             name="None" value=""  OnClick="clearList();return false;"><fmt:message key="label.clear"/> </button>
<%--			<html:select property="chemicalListName" styleClass="selectBox" styleId="chemicalListName">
				<html:options collection="listOptionBeanCollection" name="ListBean" labelProperty="listName"
								  property="listId"/>
			</html:select>   --%>
			<html:button
				 property="listSearch" styleId="listSearch" styleClass="inputBtns"
				 onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'"
				 onclick="getListSearch()">
				<fmt:message key="adhocusagereport.button.viewlist"/>
			</html:button>
		</td>
	</tr>

	<tr>
		<td class="optionTitleBoldLeft">
			<html:radio styleClass="radioBtns" styleId="reportType" property="reportType" value="singleChemical" onclick="reportTypeClicked()"/>
			<fmt:message key="adhocusagereport.radio.singleChemical"/>
		</td>
		<td class="optionTitleBoldLeft">
			<fmt:message key="adhocusagereport.label.casNumber"/>
			<html:text property="casNumber" styleId="casNumber" styleClass="inputBox"/>
			<html:button
				 property="casSearch" styleId="casSearch" styleClass="inputBtns"
				 onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'"
				 onclick="getCasSearch()">
				<fmt:message key="button.search"/>
			</html:button>
		</td>
	</tr>
	<tr>
		<td class="optionTitleBoldLeft" colspan="2">
			<html:radio styleClass="radioBtns" styleId="reportType" property="reportType" value="all" onclick="reportTypeClicked()"/>
			<fmt:message key="label.all"/>
		</td>
	</tr>

</table>


<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">

<tr>
	<td class="optionTitleBoldRight">
		<fmt:message key="label.facility"/>:
	</td>

	<td class="optionTitleLeft">
		<select class="selectBox" id="facilityId" name="facilityId" onchange="facilityChanged()">
		</select>
	</td>


		<td class="optionTitleBoldRight">&nbsp;</td>
		<td>&nbsp;</td>

	<td class="optionTitleBoldRight"><fmt:message key="label.begindate"/>:</td>
	<td class="optionTitleLeft">
		<html:text size="10" property="beginDateJsp" styleClass="inputBox" styleId="beginDateJsp"
					  onclick="return getCalendar(document.adHocUsageReportForm.beginDateJsp,null,null,null,document.adHocUsageReportForm.endDateJsp);"
					  onfocus="blur();"/> <a href="javascript: void(0);" ID="linkbeginDateJsp"
													 STYLE="color:#0000ff;cursor:pointer;text-decoration:underline"></a>
	</td>

</tr>

<tr>
	<td class="optionTitleBoldRight">
		<span id="reportingEntityLabelSpan" style="display: none">
			<fmt:message key="label.workareagroup"/>:
		</span>
	</td>
	<td class="optionTitleLeft">
		<span id="reportingEntityDropdownSpan" style="display: none">
			<select class="selectBox" id="reportingEntityId" name="reportingEntityId" onchange="reportingEntityChanged()">
			</select>
		</span>
	</td>

	<td class="optionTitleBoldRight"><fmt:message key="label.dock"/>:</td>
	<td class="optionTitleLeft">
		<select class="selectBox" name="dockId" id="dockId" onchange="dockChanged()">
		</select>
	</td>
	<td class="optionTitleBoldRight"><fmt:message key="label.enddate"/>:</td>
	<td>
		<html:text size="10" property="endDateJsp" styleClass="inputBox" styleId="endDateJsp"
					  onclick="return getCalendar(document.adHocUsageReportForm.endDateJsp,null,null,document.adHocUsageReportForm.beginDateJsp);"
					  onfocus="blur();"/><a href="javascript: void(0);" ID="linkendDateJsp"
													STYLE="color:#0000ff;cursor:pointer;text-decoration:underline"></a>
	</td>
</tr>

<tr>
	<td class="optionTitleBoldRight">
		<fmt:message key="label.workarea"/>:
	</td>
	<td class="optionTitleLeft">
		<select class="selectBox" name="application" id="application">
		</select>
	</td>
	<td class="optionTitleBoldRight"><fmt:message key="label.deliverypoint"/>:</td>
	<td>
		<select class="selectBox" name="deliveryPoint" id="deliveryPoint">
		</select>
	</td>
	<td colspan="2">&nbsp;</td>
</tr>


<tr>

		<td class="optionTitleBoldRight">&nbsp;</td>
		<td>&nbsp;</td>

    <td class="optionTitleBoldRight">&nbsp;</td>
    <td>&nbsp;</td>
    <html:hidden property="materialCategory" styleId="materialCategory" value=""/>
    <%--
    <td class="optionTitleBoldRight">
		<fmt:message key="adhocusagereport.label.materialcategory"/>:
	</td>
	<td colspan="3" class="optionTitleBoldLeft">
		<html:select property="materialCategory" styleClass="selectBox" styleId="materialCategory">
			<html:option value=""><fmt:message key="label.selectOne"/></html:option>
			<html:options collection="vvCategoryBeanCollection" name="VvCategoryBean" labelProperty="categoryDesc" property="categoryId"/>
		</html:select>
	</td>
	--%>
</tr>

<tr>
	<td class="optionTitleBoldRight">
		<fmt:message key="label.partnumber"/>:
	</td>
	<td class="optionTitleLeft">
		<html:select property="partNumberCriteria" styleClass="selectBox" styleId="partNumberCriteria">
			<html:option value="contains"><fmt:message key="label.contains"/></html:option>
			<html:option value="is"><fmt:message key="label.is"/></html:option>
		</html:select>
		&nbsp;
		<html:text property="partNumber" styleClass="inputBox" styleId="partNumber"/>
	</td>
	<td class="optionTitleBoldRight">
		<fmt:message key="label.manufacturer"/>:
	</td>
	<td colspan="3" class="optionTitleBoldLeft">
		<html:select property="manufacturerCriteria" styleClass="selectBox" styleId="manufacturerCriteria">
			<html:option value="contains"><fmt:message key="label.contains"/></html:option>
			<html:option value="is"><fmt:message key="label.is"/></html:option>
		</html:select>
		&nbsp;
		<html:text property="manufacturer" styleId="manufacturer" styleClass="inputBox"/>
	</td>
</tr>
</table>
</fieldset>

<fieldset><legend><fmt:message key="label.reportfields"/></legend>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
<tr>
	<td width="47%" class="optionTitleBoldRight">
		<fmt:message key="adhocusagereport.label.basefields"/>
		<a href="#" onclick="showBaseFields('AdHocUsage');">(<fmt:message key="label.description"/>)</a>   
	</td>
	<td width="6%">
		&nbsp;
	</td>

	<td width="10%" class="optionTitleBoldCenter">
		<fmt:message key="adhocusagereport.label.reportfields"/>
	</td>
	<td width="37%" class="optionTitleLeft">
		<img src="/images/uparrow.png" onmouseover=style.cursor="hand" onclick="move(reportFieldList,true);"/>
		<img src="/images/downarrow.png" onmouseover=style.cursor="hand" onclick="move(reportFieldList,false);"/>
	</td>
</tr>

<tr>
	<td width="47%" class="optionTitleRight">
		<html:select styleClass="selectBox" styleId="foo" size="17" multiple="multiple" property="foo">
			<html:options collection="baseFieldBeanCollection" name="BaseFieldViewBean" labelProperty="name"
							  property="baseFieldId"/>
		</html:select>

	</td>
	<td width="6%" class="optionTitleBoldCenter">
		<img src="/images/rightarrow.png" onmouseover=style.cursor="hand" onclick="transferItem($('foo'),$('reportFieldList'),false);"/>
		<br>
		<img src="/images/leftarrow.png" onmouseover=style.cursor="hand" onclick="transferItem($('reportFieldList'),$('foo'),false);"/>
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
</table>
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
	</td>
</tr>

<tr>
	<html:hidden property="id" styleId="id" value="${adHocUsageReportForm.id}"/>
	<html:hidden property="templateName" styleId="templateName" value="${adHocUsageReportForm.templateName}"/>
	<html:hidden property="templateDescription" styleId="templateDescription" value="${adHocUsageReportForm.templateDescription}"/>
	<html:hidden property="templateId" styleId="templateId" value="${adHocUsageReportForm.templateId}"/>
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

<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages">
	<html:errors/>
</div>
<!-- Error Messages Ends -->


<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;"></div>
<input type="hidden" name="facilityName" id="facilityName" value=""/>
<input type="hidden" name="applicationDesc" id="applicationDesc" value=""/>
<input type="hidden" name="dockDesc" id="dockDesc" value=""/>
<input type="hidden" name="deliveryPointDesc" id="deliveryPointDesc" value=""/>
<input type="hidden" name="categoryDesc" id="categoryDesc" value=""/>
<input type="hidden" name="reportingEntity" id="reportingEntity" value=""/>
<input type="hidden" name="templateFacilityId" id="templateFacilityId" value="${adHocUsageReportForm.facilityId}"/>
<input type="hidden" name="templateReportingEntityId" id="templateReportingEntityId" value="${adHocUsageReportForm.reportingEntityId}"/>
<input type="hidden" name="templateApplication" id="templateApplication" value="${adHocUsageReportForm.application}"/>
<input type="hidden" name="templateDockId" id="templateDockId" value="${adHocUsageReportForm.dockId}"/>
<input type="hidden" name="templateDeliveryPoint" id="templateDeliveryPoint" value="${adHocUsageReportForm.deliveryPoint}"/>
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
