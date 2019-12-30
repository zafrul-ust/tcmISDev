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

<!--  These are for the autocomplete function -->
<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script> 
<script type="text/javascript" src="/js/jquery/autocomplete.js"></script> 
<link rel="stylesheet" type="text/css" href="/css/autocomplete.css" /> 

<script type='text/javascript' src='/js/jquery/ajaxQueue.js'></script>

	<%-- Add any other javascript you need for the page here --%>
<script src="/js/report/adhocinventoryreportoriginal.js" language="JavaScript"></script>
<script language="JavaScript" type="text/javascript">
<!--

var altFacilityId = new Array(
<c:forEach var="facBean" items="${facAppReportViewBeanCollection[0].facilityList}" varStatus="status">
	<c:if test="${status.index > 0}">,</c:if>
	'<tcmis:jsReplace value="${facBean.facilityId}"/>'
</c:forEach>
);

var altFacilityName = new Array(
<c:forEach var="facBean" items="${facAppReportViewBeanCollection[0].facilityList}" varStatus="status">
	<c:if test="${status.index > 0}">,</c:if>
	'<tcmis:jsReplace value="${facBean.facilityName}"/>'
</c:forEach>
);

var altAreaId = new Array();
<c:forEach var="facBean" items="${facAppReportViewBeanCollection[0].facilityList}" varStatus="status">
	<c:set var="currentFacility" value='${facBean.facilityId}'/>
	<c:set var="areaCollection" value='${facBean.areaList}'/>
	altAreaId['<tcmis:jsReplace value="${currentFacility}"/>'] = new Array(
		<c:forEach var="areaBean" items="${areaCollection}" varStatus="status1">
			<c:if test="${status1.index > 0}">,</c:if>
			'${areaBean.areaId}'
		</c:forEach>
);
</c:forEach>

var altAreaName = new Array();
<c:forEach var="facBean" items="${facAppReportViewBeanCollection[0].facilityList}" varStatus="status">
	<c:set var="currentFacility" value='${facBean.facilityId}'/>
	<c:set var="areaCollection" value='${facBean.areaList}'/>
	altAreaName['<tcmis:jsReplace value="${currentFacility}"/>'] = new Array(
		<c:forEach var="areaBean" items="${areaCollection}" varStatus="status1">
			<c:if test="${status1.index > 0}">,</c:if>
			'<tcmis:jsReplace value="${areaBean.areaName}"/>'
		</c:forEach>
);
</c:forEach>

var altBuildingId = new Array();
<c:forEach var="facBean" items="${facAppReportViewBeanCollection[0].facilityList}" varStatus="status">
	<c:set var="currentFacility" value='${facBean.facilityId}'/>
	<c:set var="areaCollection" value='${facBean.areaList}'/>
	<c:forEach var="areaBean" items="${areaCollection}" varStatus="status1">
        <c:set var="currentArea" value='${areaBean.areaId}'/>
        <c:set var="bldgCollection" value='${areaBean.buildingList}'/>
        altBuildingId['<tcmis:jsReplace value="${currentFacility}"/><c:out value="${currentArea}"/>'] = new Array(
            <c:forEach var="bldgBean" items="${bldgCollection}" varStatus="status2">
                <c:if test="${status2.index > 0}">,</c:if>
                '${bldgBean.buildingId}'
            </c:forEach>
        );
    </c:forEach>
</c:forEach>

var altBuildingName = new Array();
<c:forEach var="facBean" items="${facAppReportViewBeanCollection[0].facilityList}" varStatus="status">
	<c:set var="currentFacility" value='${facBean.facilityId}'/>
	<c:set var="areaCollection" value='${facBean.areaList}'/>
	<c:forEach var="areaBean" items="${areaCollection}" varStatus="status1">
        <c:set var="currentArea" value='${areaBean.areaId}'/>
        <c:set var="bldgCollection" value='${areaBean.buildingList}'/>
        altBuildingName['<tcmis:jsReplace value="${currentFacility}"/><c:out value="${currentArea}"/>'] = new Array(
            <c:forEach var="bldgBean" items="${bldgCollection}" varStatus="status2">
                <c:if test="${status2.index > 0}">,</c:if>
                '<tcmis:jsReplace value="${bldgBean.buildingName}"/>'
            </c:forEach>
        );
    </c:forEach>
</c:forEach>

var altFloorId = new Array();
<c:forEach var="facBean" items="${facAppReportViewBeanCollection[0].facilityList}" varStatus="status">
	<c:set var="currentFacility" value='${facBean.facilityId}'/>
	<c:set var="areaCollection" value='${facBean.areaList}'/>
	<c:forEach var="areaBean" items="${areaCollection}" varStatus="status1">
        <c:set var="currentArea" value='${areaBean.areaId}'/>
        <c:set var="bldgCollection" value='${areaBean.buildingList}'/>
        <c:forEach var="bldgBean" items="${bldgCollection}" varStatus="status2">
            <c:set var="currentBldg" value='${bldgBean.buildingId}'/>
            <c:set var="floorCollection" value='${bldgBean.floorList}'/>
            altFloorId['<tcmis:jsReplace value="${currentFacility}"/><c:out value="${currentArea}"/><c:out value="${currentBldg}"/>'] = new Array(
            <c:forEach var="floorBean" items="${floorCollection}" varStatus="status3">
                <c:if test="${status3.index > 0}">,</c:if>
                '${floorBean.floorId}'
            </c:forEach>
            );
        </c:forEach>
    </c:forEach>
</c:forEach>

var altFloorName = new Array();
<c:forEach var="facBean" items="${facAppReportViewBeanCollection[0].facilityList}" varStatus="status">
	<c:set var="currentFacility" value='${facBean.facilityId}'/>
	<c:set var="areaCollection" value='${facBean.areaList}'/>
	<c:forEach var="areaBean" items="${areaCollection}" varStatus="status1">
        <c:set var="currentArea" value='${areaBean.areaId}'/>
        <c:set var="bldgCollection" value='${areaBean.buildingList}'/>
        <c:forEach var="bldgBean" items="${bldgCollection}" varStatus="status2">
            <c:set var="currentBldg" value='${bldgBean.buildingId}'/>
            <c:set var="floorCollection" value='${bldgBean.floorList}'/>
            altFloorName['<tcmis:jsReplace value="${currentFacility}"/><c:out value="${currentArea}"/><c:out value="${currentBldg}"/>'] = new Array(
            <c:forEach var="floorBean" items="${floorCollection}" varStatus="status3">
                <c:if test="${status3.index > 0}">,</c:if>
                '<tcmis:jsReplace value="${floorBean.description}"/>'
            </c:forEach>
            );
        </c:forEach>
    </c:forEach>
</c:forEach>

var altRoomId = new Array();
<c:forEach var="facBean" items="${facAppReportViewBeanCollection[0].facilityList}" varStatus="status">
	<c:set var="currentFacility" value='${facBean.facilityId}'/>
	<c:set var="areaCollection" value='${facBean.areaList}'/>
	<c:forEach var="areaBean" items="${areaCollection}" varStatus="status1">
        <c:set var="currentArea" value='${areaBean.areaId}'/>
        <c:set var="bldgCollection" value='${areaBean.buildingList}'/>
        <c:forEach var="bldgBean" items="${bldgCollection}" varStatus="status2">
            <c:set var="currentBldg" value='${bldgBean.buildingId}'/>
            <c:set var="floorCollection" value='${bldgBean.floorList}'/>
            <c:forEach var="floorBean" items="${floorCollection}" varStatus="status3">
	            <c:set var="currentFloor" value='${floorBean.floorId}'/>
	            <c:set var="roomCollection" value='${floorBean.roomList}'/>
		        altRoomId['<tcmis:jsReplace value="${currentFacility}"/><c:out value="${currentArea}"/><c:out value="${currentBldg}"/><c:out value="${currentFloor}"/>'] = new Array(
		            <c:forEach var="roomBean" items="${roomCollection}" varStatus="status4">
		                <c:if test="${status4.index > 0}">,</c:if>
		                '${roomBean.roomId}'
		            </c:forEach>
	            );
            </c:forEach>
        </c:forEach>
    </c:forEach>
</c:forEach>

var altRoomName = new Array();
<c:forEach var="facBean" items="${facAppReportViewBeanCollection[0].facilityList}" varStatus="status">
	<c:set var="currentFacility" value='${facBean.facilityId}'/>
	<c:set var="areaCollection" value='${facBean.areaList}'/>
	<c:forEach var="areaBean" items="${areaCollection}" varStatus="status1">
        <c:set var="currentArea" value='${areaBean.areaId}'/>
        <c:set var="bldgCollection" value='${areaBean.buildingList}'/>
        <c:forEach var="bldgBean" items="${bldgCollection}" varStatus="status2">
            <c:set var="currentBldg" value='${bldgBean.buildingId}'/>
            <c:set var="floorCollection" value='${bldgBean.floorList}'/>
            <c:forEach var="floorBean" items="${floorCollection}" varStatus="status3">
	            <c:set var="currentFloor" value='${floorBean.floorId}'/>
	            <c:set var="roomCollection" value='${floorBean.roomList}'/>
	            altRoomName['<tcmis:jsReplace value="${currentFacility}"/><c:out value="${currentArea}"/><c:out value="${currentBldg}"/><c:out value="${currentFloor}"/>'] = new Array(
		            <c:forEach var="roomBean" items="${roomCollection}" varStatus="status4">
		                <c:if test="${status4.index > 0}">,</c:if>
		                '<tcmis:jsReplace value="${roomBean.roomName}"/>'
		            </c:forEach>
	            );
	        </c:forEach>
        </c:forEach>
    </c:forEach>
</c:forEach>

	var listOptionArray = [
    <c:forEach var="listBean" items="${listOptionBeanCollection}" varStatus="status">
    <c:if test="${ status.index !=0 }">,</c:if>
        {
          id:   '${status.current.listId}',
          name: '<tcmis:jsReplace value="${status.current.listName}"/>'
        }
    </c:forEach>
    ]; 

j$().ready(function() {
	function log(event, data, formatted) {
		if( typeof(data) == 'undefined') return false;
		j$('#searchListId').val(data.id);
		$("listDesc").className = "inputBox"; 

		for (j=0;j<$("bar").length;j++) {
           if(data.id == $("bar").options[j].value){
              $("bar").options[j].selected = true;
     //         move(chemicalFieldList,true);
              break;
           }
        }
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
			return row.name;
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
 var listId  =  document.getElementById("searchListId");
 if (listDesc.value.length == 0) {
   listDesc.className = "inputBox";
 }else {
   listDesc.className = "inputBox red";
 }
 //set to empty
 listId.value = "";
}

function clearList() {
	$("searchListId").value = "";
	$("listDesc").value = "";
	$("listDesc").className = "inputBox";
}

// -->
</script>

<title>
	<fmt:message key="adhocinventoryreport.title"/>
</title>

<script language="JavaScript" type="text/javascript">
	<!--
	//add all the javascript messages here, this for internationalization of client side javascript messages
	var messagesData = new Array();
	messagesData = {alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
		submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
		all:"<fmt:message key="label.all"/>",
		myFacilities:"<fmt:message key="label.myfacilities"/>",
		myworkareas:"<fmt:message key="label.myworkareas"/>",
		thereisnoitemtomove:"<fmt:message key="label.thereisnoitemtomove"/>",
		selectanitemthatyouwanttomove:"<fmt:message key="label.selectanitemthatyouwanttomove"/>",
		youmustfirstselectitemtoreorder:"<fmt:message key="label.youmustfirstselectitemtoreorder"/>",
		youmustselectreportfield:"<fmt:message key="label.youmustselectreportfield"/>",
		youmustselectchemicalfield:"<fmt:message key="label.youmustselectchemicalfield"/>",
		youmustentercasnumber:"<fmt:message key="label.youmustentercasnumber"/>",
		pleaseenterreportname:"<fmt:message key="label.pleaseenterreportname"/>",
		partnumberrequired:"<fmt:message key="label.partnumberrequired"/>",
		inventorydaterequired:"<fmt:message key="label.inventorydaterequired"/>",
		begindaterequired:"<fmt:message key="label.begindaterequired"/>",
		enddaterequired:"<fmt:message key="label.enddaterequired"/>"};
	// -->
</script>
</head>

<body bgcolor="#ffffff" onload="loadForm()">
<c:if test="org.apache.struts.action.MESSAGE == null">
	<div class="errorMessages">
		ERROR: Application resources not loaded
	</div>
</c:if>

<tcmis:form action="/adhocinventoryoriginal.do" onsubmit="return submitOnlyOnce();">

<div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
	<br><br><br><fmt:message key="label.pleasewait"/>
</div>
<div class="interface" id="mainPage">

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
	<td class="optionTitleBoldCenter" colspan="8">
		<fmt:message key="adhocinventoryreport.title"/>
		<c:if test = "${adHocInventoryReportForm.templateName != null}">
			<c:set var="globalLabelLetter"><fmt:message key="${adHocInventoryReportForm.globalizationLabelLetter}"/></c:set>
			&nbsp;:&nbsp;<c:out value="${globalLabelLetter}"/><c:out value="${adHocInventoryReportForm.templateId}"/>-<c:out value="${adHocInventoryReportForm.templateName}"/>
		</c:if>
	</td>
</tr>
</table>

<fieldset><legend><fmt:message key="label.searchfields"/></legend>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
<tr>
	<td width="5%" class="optionTitleBoldRight">
		<fmt:message key="label.facility"/>:
	</td>
	<td width="20%" class="optionTitleBoldLeft">
		<select class="selectBox" id="facilityId" name="facilityId" onchange="facilityChanged()">
		</select>
    </td>
	<td width="5%" class="optionTitleBoldRight">
		<fmt:message key="label.area"/>:
	</td>
	<td width="20%" class="optionTitleBoldLeft">
		<select class="selectBox" id="areaId" name="areaId" onchange="areaChanged()">
		</select>
    </td>
	<td width="5%" class="optionTitleBoldRight">
		<fmt:message key="label.building"/>:
	</td>
	<td width="20%" class="optionTitleBoldLeft">
		<select class="selectBox" id="buildingId" name="buildingId" onchange="buildingChanged()">
		</select>
    </td>
    <td width="5%" class="optionTitleBoldRight">
		<fmt:message key="label.floor"/>:
	</td>
	<td width="20%" class="optionTitleBoldLeft">
		<select class="selectBox" id="floorId" name="floorId" onchange="floorChanged()">
		</select>
    </td>
	<td width="5%" class="optionTitleBoldRight">
		<fmt:message key="label.room"/>:
	</td>
	<td width="20%" class="optionTitleBoldLeft">
		<select class="selectBox" id="roomId" name="roomId">
		</select>
    </td>
</tr>
<tr>
    <td>&nbsp;</td>
    <td class="optionTitleBoldLeft" colspan="9">
        <fmt:message key="label.begindate"/>:&nbsp;
        <input type="hidden" name="today" id="today" value='<tcmis:getDateTag numberOfDaysFromToday="1" datePattern="${dateFormatPattern}"/>' />
        <html:text size="10" property="beginDateJsp" styleClass="inputBox" styleId="beginDateJsp"
					  onclick="return getCalendar(document.adHocInventoryReportForm.beginDateJsp,null,$('today'),null,document.adHocInventoryReportForm.endDateJsp);"
					  onfocus="blur();"/> <a href="javascript: void(0);" ID="linkbeginDateJsp"
					  STYLE="color:#0000ff;cursor:pointer;text-decoration:underline"></a>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <fmt:message key="label.enddate"/>:&nbsp;
        <input type="hidden" name="todayEnd" id="todayEnd" value='<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>' />
        <html:text size="10" property="endDateJsp" styleClass="inputBox" styleId="endDateJsp"
					  onclick="return getCalendar(document.adHocInventoryReportForm.endDateJsp,null,null,document.adHocInventoryReportForm.beginDateJsp,$('todayEnd'));"
					  onfocus="blur();"/><a href="javascript: void(0);" ID="linkendDateJsp"
					  STYLE="color:#0000ff;cursor:pointer;text-decoration:underline"></a>
    </td>

</tr>
</table>
</fieldset>

<fieldset><legend><fmt:message key="label.reportfields"/></legend>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
<tr>
	<td width="47%" class="optionTitleBoldCenter" nowrap>
		<fmt:message key="adhocmaterialmatrixreport.label.chemicallist"/>
		<input type="hidden" id="searchListId" name="searchListId"/>
		<input type="text" id="listDesc" name="listDesc" size="60" class="inputBox"   />
		<button class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'"
                                             name="None" value=""  OnClick="clearList();return false;"><fmt:message key="label.clear"/> </button>
	</td>
	<td width="6%">
		&nbsp;
	</td>
	<td width="10%" class="optionTitleBoldCenter">
		<fmt:message key="adhocmaterialmatrixreport.label.reportlist"/>
	</td>
	<td width="37%" class="optionTitleBoldLeft">
		<img src="/images/uparrow.png" onmouseover=style.cursor="hand" onclick="move(chemicalFieldList,true);"/>
		<img src="/images/downarrow.png" onmouseover=style.cursor="hand" onclick="move(chemicalFieldList,false);"/>
	</td>
</tr>

<tr>
	<td width="47%" class="optionTitleBoldRight">
		<html:select styleClass="selectBox" styleId="bar" size="13" multiple="multiple" property="bar">
			<html:options collection="listOptionBeanCollection" name="ListBean" labelProperty="listName"
							  property="listId"/>
		</html:select>
	</td>
	<td width="6%" class="optionTitleBoldCenter">
		<img src="/images/rightarrow.png" onmouseover=style.cursor="hand" onclick="transferItem(bar,chemicalFieldList);"/>
		<br>
		<img src="/images/leftarrow.png" onmouseover=style.cursor="hand" onclick="transferItem(chemicalFieldList,bar);"/>
	</td>
	<td width="47%" class="optionTitleBoldLeft" colspan="2">
		<html:select styleClass="selectBox" styleId="chemicalFieldList" size="13" multiple="multiple"
						 property="chemicalFieldList" name="chemicalFieldList" value="">
			
			<html:option value="xxblankxx">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</html:option>
			<html:options collection="chemicalListBeanCollection" name="ListBean" labelProperty="listName"
							  property="listId"/>
		</html:select>
	</td>
</tr>


<tr>
	<td width="47%" class="optionTitleBoldRight">
		<fmt:message key="adhocmaterialmatrixreport.label.basefields"/> <a href="#"
																								 onclick="showBaseFields('Inventory');">
		(<fmt:message key="label.description"/>)</a>
	</td>
	<td width="6%" class="optionTitleBoldLeft">
		&nbsp;
	</td>
	<td width="10%" class="optionTitleBoldCenter">
		<fmt:message key="adhocmaterialmatrixreport.label.reportfields"/>
	</td>
	<td width="37%" align="left">
		<img src="/images/uparrow.png" onmouseover=style.cursor="hand" onclick="move(reportFieldList,true);"/>
		<img src="/images/downarrow.png" onmouseover=style.cursor="hand" onclick="move(reportFieldList,false);"/>
	</td>
</tr>
<tr>
	<td width="47%" class="optionTitleBoldRight">
		<c:set var="reportFieldCollection" value='${adHocInventoryReportForm.reportFieldCollection}'/>
		<html:select styleClass="selectBox" styleId="foo" style="width=120px" size="8" multiple="multiple" property="foo">
			<html:options collection="baseFieldBeanCollection" name="BaseFieldViewBean" labelProperty="name"
							  property="baseFieldId"/>
		</html:select>
	</td>
	<td width="6%" class="optionTitleBoldCenter">
		<img src="/images/rightarrow.png" onmouseover=style.cursor="hand" onclick="transferItem(foo,reportFieldList);"/>
		<br>
		<img src="/images/leftarrow.png" onmouseover=style.cursor="hand" onclick="transferItem(reportFieldList,foo);"/>
	</td>
	<td width="47%" class="optionTitleBoldLeft" colspan="2">
		<html:select styleClass="selectBox" styleId="reportFieldList" size="8" multiple="multiple" property="reportFieldList">
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
	<td colspan="4" class="optionTitleBoldCenter">
		<html:radio property="reportGenerationType" value="INTERACTIVE" styleClass="radioBtns"
						styleId="reportGenerationType"/>
		<fmt:message key="adhocmaterialmatrixreport.label.interactive"/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<html:radio property="reportGenerationType" value="BATCH" styleClass="radioBtns" styleId="reportGenerationType"/>
		<fmt:message key="label.batch"/>
		&nbsp;
		<fmt:message key="adhocmaterialmatrixreport.label.reportname"/>:
		<html:text property="reportName" styleId="reportName" styleClass="inputBox"/>
	</td>
</tr>

<tr>
	<html:hidden property="id" styleId="id" value="${adHocInventoryReportForm.id}"/>
	<html:hidden property="templateName" styleId="templateName" value="${adHocInventoryReportForm.templateName}"/>
	<html:hidden property="templateDescription" styleId="templateDescription" value="${adHocInvetnoryReportForm.templateDescription}"/>
	<html:hidden property="templateId" styleId="templateId" value="${adHocInventoryReportForm.templateId}"/>
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

		<c:if test="${adHocInventoryReportForm.publishTemplate == 'Y'}">
			<html:button property="publishTemplate" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'"
						 onmouseout="this.className='inputBtns'" onclick="publishTemplateAudit()">
				<fmt:message key="label.publishtemplate"/>
			</html:button>
		</c:if>

		<c:if test="${adHocInventoryReportForm.unpublishTemplate == 'Y'}">
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
<input type="hidden" name="templateFacilityId" id="templateFacilityId" value="${adHocInventoryReportForm.facilityId}"/>
<input type="hidden" name="templateAreaId" id="templateAreaId" value="${adHocInventoryReportForm.areaId}"/>
<input type="hidden" name="templateBuildingId" id="templateBuildingId" value="${adHocInventoryReportForm.buildingId}"/>
<input type="hidden" name="templateFloorId" id="templateFloorId" value="${adHocInventoryReportForm.floorId}"/>
<input type="hidden" name="templateRoomId" id="templateRoomId" value="${adHocInventoryReportForm.roomId}"/>
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
