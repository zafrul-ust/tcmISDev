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
<script src="/js/report/adhocmaterialmatrixreportoriginal.js" language="JavaScript"></script>
<script language="JavaScript" type="text/javascript">
<!--

var altFacilityId = new Array(
<c:forEach var="facAppReportViewBean" items="${facAppReportViewBeanCollection}" varStatus="status">
	<c:if test="${status.index > 0}">,</c:if>
	'<tcmis:jsReplace value="${status.current.facilityId}"/>'
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

<%--
var altReportList = new Array(<c:forEach var="chemicalListBean" items="${chemicalListBeanCollection}" varStatus="status">
	 <c:choose>
	 <c:when test="${status.index > 0}">, "<c:out value="${status.current.chemicalList}"/>"
</c:when>
<c:otherwise>
"<c:out value="${status.current.chemicalList}"/>"
</c:otherwise>
</c:choose>
</c:forEach>
)
;
--%>

// -->
</script>

<title>
	<fmt:message key="adhocmaterialmatrixreport.title"/>
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
</head>

<body bgcolor="#ffffff" onload="loadForm()">
<c:if test="org.apache.struts.action.MESSAGE == null">
	<div class="errorMessages">
		ERROR: Application resources not loaded
	</div>
</c:if>

<tcmis:form action="/adhocmaterialmatrixoriginal.do" onsubmit="return submitOnlyOnce();">

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
				<fmt:message key="adhocmaterialmatrixreport.title"/>
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
	<td class="optionTitleBoldCenter" colspan="5">
		<fmt:message key="adhocmaterialmatrixreport.title"/>
		<c:if test = "${adHocMaterialMatrixReportForm.templateName != null}">
			<c:set var="globalLabelLetter"><fmt:message key="${adHocMaterialMatrixReportForm.globalizationLabelLetter}"/></c:set>
			&nbsp;:&nbsp;<c:out value="${globalLabelLetter}"/><c:out value="${adHocMaterialMatrixReportForm.templateId}"/>-<c:out value="${adHocMaterialMatrixReportForm.templateName}"/>
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
	<td colspan="2">
		<select class="selectBox" id="facilityId" name="facilityId" onchange="facilityChanged()">
		</select>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<span id="reportingEntityLabelSpan" style="display: none">
			<b><fmt:message key="label.workareagroup"/>:</b>
		</span>
		&nbsp;&nbsp;
		<span id="reportingEntityDropdownSpan" style="display: none">
			<select class="selectBox" id="reportingEntityId" name="reportingEntityId" onchange="reportingEntityChanged()">
			</select>
		</span>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<b><fmt:message key="label.workarea"/>:</b>
		&nbsp;&nbsp;
		<select class="selectBox" name="application" id="application">
		</select>
	</td>
</tr>

<tr>
	<td colspan="3" class="optionTitleBoldLeft">
		<html:radio property="reportCriteria" value="PART" styleClass="radioBtns" styleId="reportCriteria"
						onclick="reportCriteriaClicked();"/> <fmt:message key="label.partnumber"/>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<html:select styleClass="selectBox" styleId="partNumberCriteria" property="partNumberCriteria">
			<html:option value="contains"><fmt:message key="label.contains"/></html:option>
			<html:option value="is"><fmt:message key="label.is"/></html:option>
		</html:select>
		&nbsp;&nbsp;
		<html:text property="partNumber" styleId="partNumber" styleClass="inputBox"/>
	</td>

</tr>

<input type="hidden" name="reportCriteria" id="reportCriteria" value=""/>
<input type="hidden" name="beginDateJsp" id="beginDateJsp" value=""/>
<input type="hidden" name="endDateJsp" id="endDateJsp" value=""/>

<tr>
	<td class="optionTitleBoldLeft">
		<html:radio property="reportCriteria" value="APPROVED" styleClass="radioBtns" styleId="reportCriteria"
						onclick="reportCriteriaClicked();"/> <fmt:message
		 key="adhocmaterialmatrixreport.label.allpartsapproved"/>
	</td>

	<td class="optionTitleBoldRight">
		<fmt:message key="adhocmaterialmatrixreport.label.listformat"/>:
	</td>

	<td >
		<html:select styleClass="selectBox" styleId="listFormat" property="listFormat">
			<html:option value="YES">Yes</html:option>
			<html:option value="xx.x %">xx.x %</html:option>
			<html:option value="xx.xE-y %">xx.x E -y%</html:option>
			<html:option value="lbs">lbs</html:option>
		</html:select>
	</td>

</tr>
<c:if test="${displayPartsInInventory == 'true'}">
	<tr>
		<td colspan="3" class="optionTitleBoldLeft">
			<html:radio property="reportCriteria" value="INVENTORY" styleClass="radioBtns" styleId="reportCriteria" onclick="reportCriteriaClicked();"/>
			<fmt:message key="adhocmaterialmatrixreport.label.partsininventoryon"/>:
		   &nbsp;&nbsp;
			<html:text size="10" property="inventoryDate" styleId="inventoryDate" styleClass="inputBox" onfocus="blur();"
					  onclick="return getCalendar(document.adHocMaterialMatrixReportForm.inventoryDate,null,todaysDate);"/>
		    <a href="javascript: void(0);" ID="linkinventoryDate"
			STYLE="color:#0000ff;cursor:pointer;text-decoration:underline"></a>
		</td>
	</tr>
</c:if>
</table>
</fieldset>

<fieldset><legend><fmt:message key="label.reportfields"/></legend>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
<tr>
	<td width="47%" class="optionTitleBoldCenter" nowrap>
		<fmt:message key="adhocmaterialmatrixreport.label.chemicallist"/>
		<input type="hidden" id="searchListId" name="searchListId"/>
		<input type="text" id="listDesc" name="listDesc" size="60" class="inputBox"   />
		<button class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'" name="None" value=""  OnClick="clearList();return false;"><fmt:message key="label.clear"/> </button>
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
			<html:options collection="listOptionBeanCollection" name="ListBean" labelProperty="listName" property="listId"/>
		</html:select>
	</td>
	<td width="6%" class="optionTitleBoldCenter">
		<img src="/images/rightarrow.png" onmouseover=style.cursor="hand" onclick="transferItem($('bar'),$('chemicalFieldList'));"/>
		<br>
		<img src="/images/leftarrow.png" onmouseover=style.cursor="hand" onclick="transferItem($('chemicalFieldList'),$('bar'));"/>
	</td>
	<td width="47%" class="optionTitleBoldLeft" colspan="2">
		<html:select styleClass="selectBox" styleId="chemicalFieldList" size="13" multiple="multiple" property="chemicalFieldList" name="chemicalFieldList" value="">
			
			<html:option value="xxblankxx">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</html:option>
			<html:options collection="chemicalListBeanCollection" name="ListBean" labelProperty="listName" property="listId"/>
		</html:select>
	</td>
</tr>


<tr>
	<td width="47%" class="optionTitleBoldRight">
		<fmt:message key="adhocmaterialmatrixreport.label.basefields"/> <a href="#" onclick="showBaseFields('MaterialMatrix');">
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
		<c:set var="reportFieldCollection" value='${adHocMaterialMatrixReportForm.reportFieldCollection}'/>
		<html:select styleClass="selectBox" styleId="foo" style="width=120px" size="8" multiple="multiple" property="foo">
			<html:options collection="baseFieldBeanCollection" name="BaseFieldViewBean" labelProperty="name"
							  property="baseFieldId"/>
		</html:select>
	</td>
	<td width="6%" class="optionTitleBoldCenter">
		<img src="/images/rightarrow.png" onmouseover=style.cursor="hand" onclick="transferItem($('foo'),$('reportFieldList'));"/>
		<br>
		<img src="/images/leftarrow.png" onmouseover=style.cursor="hand" onclick="transferItem($('reportFieldList'),$('foo'));"/>
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
	<html:hidden property="id" styleId="id" value="${adHocMaterialMatrixReportForm.id}"/>
	<html:hidden property="templateName" styleId="templateName" value="${adHocMaterialMatrixReportForm.templateName}"/>
	<html:hidden property="templateDescription" styleId="templateDescription" value="${adHocMaterialMatrixReportForm.templateDescription}"/>
	<html:hidden property="templateId" styleId="templateId" value="${adHocMaterialMatrixReportForm.templateId}"/>
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

		<c:if test="${adHocMaterialMatrixReportForm.publishTemplate == 'Y'}">
			<html:button property="publishTemplate" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'"
						 onmouseout="this.className='inputBtns'" onclick="publishTemplateAudit()">
				<fmt:message key="label.publishtemplate"/>
			</html:button>
		</c:if>

		<c:if test="${adHocMaterialMatrixReportForm.unpublishTemplate == 'Y'}">
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
<input type="hidden" name="showPartsInInventory" id="showPartsInInventory" value="${displayPartsInInventory}"/>
<input type="hidden" name="templateFacilityId" id="templateFacilityId" value="${adHocMaterialMatrixReportForm.facilityId}"/>
<input type="hidden" name="templateReportingEntityId" id="templateReportingEntityId" value="${adHocMaterialMatrixReportForm.reportingEntityId}"/>
<input type="hidden" name="templateApplication" id="templateApplication" value="${adHocMaterialMatrixReportForm.application}"/>
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
