<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
<tcmis:gridFontSizeCss overflow="notHidden"/>
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support for column type hcal-->

<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>


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
<script src="/js/report/adhocmaterialmatrixreport.js" language="JavaScript"></script>
<script src="/js/report/adhocsearchtemplate.js" language="JavaScript"></script>
<script src="/js/report/adhoclistfieldtemplate.js" language="JavaScript"></script>
<script src="/js/report/workareasearchadhoctemplate.js" language="JavaScript"></script>

<script language="JavaScript" type="text/javascript">
<!--
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
		myfacilitygroups:"<fmt:message key="label.myfacilitygroups"/>",
		myFacilities:"<fmt:message key="label.myfacilities"/>",
		myworkareas:"<fmt:message key="label.myworkareas"/>",
		pleaseselect:"<fmt:message key="errors.selecta"/>",
		facility:"<fmt:message key="label.facility"/>",
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
		enddaterequired:"<fmt:message key="label.enddaterequired"/>",
		pleasewait:"<fmt:message key="label.pleasewait"/>",
		other:"<fmt:message key="label.other"/>"
		}; 

	// -->
</script>
</head>

<body bgcolor="#ffffff" onload="loadFields();loadTemplate();initializeReportGrid();loadForm();" onunload="closeAllchildren();">
<c:if test="org.apache.struts.action.MESSAGE == null">
	<div class="errorMessages">
		ERROR: Application resources not loaded
	</div>
</c:if>

<tcmis:form action="/adhocmaterialmatrixreport.do" onsubmit="return submitOnlyOnce();">   

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
<table id="searchMaskTable" width="1400" border="0" cellpadding="0" cellspacing="0">
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
		      <tcmis:jsReplace var="userGroupDesc" value="${adHocMaterialMatrixReportForm.userGroupDesc}" processMultiLines="true" /> 
              <c:set var="tmpVersion" value=''/>
              <c:if test="${adHocMaterialMatrixReportForm.owner == 'PERSONNEL_ID'}">
                  <c:set var="tmpVersion" value='${adHocMaterialMatrixReportForm.createdByName}'/>
              </c:if>
              <c:if test="${adHocMaterialMatrixReportForm.owner == 'USER_GROUP_ID'}">
                  <c:set var="tmpVersion" value='${userGroupDesc}'/>
              </c:if>
              <c:if test="${adHocMaterialMatrixReportForm.owner == 'COMPANY_ID'}">
                  <c:set var="tmpVersion" value='${adHocMaterialMatrixReportForm.companyName}'/>
              </c:if>
			<c:set var="globalLabelLetter"><fmt:message key="${adHocMaterialMatrixReportForm.globalizationLabelLetter}"/></c:set>
			&nbsp;:&nbsp;<c:out value="${globalLabelLetter}"/><c:out value="${adHocMaterialMatrixReportForm.templateId}"/>-<c:out value="${adHocMaterialMatrixReportForm.templateName}"/>
			<c:if test="${!empty tmpVersion}">&nbsp;(<c:out value="${tmpVersion}"/>)</c:if>
		</c:if>
	</td>
</tr>
</table>

<fieldset><legend><fmt:message key="label.searchfields"/></legend>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
<input type="hidden" name="reportTypeForWorkAreaSearchAdHocTemplate" id="reportTypeForWorkAreaSearchAdHocTemplate" value="AdHocMaterialMatrix"/>    
<%@ include file="/report/workareasearchadhoctemplate.jsp" %>
<tr>
    <td class="optionTitleBoldLeft">
        <%-- todo for now set hidden field until Ron is ready 
        <input type="hidden" name="attachedtofacility" id="attachedtofacility" value=""/>--%>																														
       	<input name="approved" id="approved" type="radio" class="radioBtns" value="" onclick="showApprOrAttFac(this.id,this.checked);" <c:if test="${adHocMaterialMatrixReportForm.reportCriteria == null || adHocMaterialMatrixReportForm.reportCriteria == 'APPROVED'}">checked</c:if>>&nbsp;<fmt:message key="label.approved"/>
       	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 		<input name="attachedtofacility" id="attachedtofacility" type="radio" class="radioBtns" value="" onclick="showApprOrAttFac(this.id,this.checked);" <c:if test="${adHocMaterialMatrixReportForm.reportCriteria == 'FACILITY'}">checked</c:if>>&nbsp;<fmt:message key="label.associatewithfacility"/>

        <input type="hidden" name="usedOnly" id="usedOnly" value=""/>
        <input type="hidden" name="beginDateJsp" id="beginDateJsp" value=""/>
        <input type="hidden" name="endDateJsp" id="endDateJsp" value=""/>
    </td>

</tr>
</table>
	   <%-- the reason that I have the include here is because the data is set here as well as reloadqpldata.jsp --%>
	   <%@ include file="/report/adhocsearchtemplate.jsp" %>
       <%-- List and Cas grid for the inventory and material matrix grids goes here. --%>
       <%@ include file="/report/adhoclistfieldtemplate.jsp" %>
</fieldset>

<fieldset><legend><fmt:message key="label.reportfields"/></legend>
    <%-- move the following into right positions once test is completed --%>
    <script src="/js/report/adhocreportfieldtemplate.js" language="JavaScript"></script>
    <input type="hidden" name="reportType" id="reportType" value="MaterialMatrix"/>
    <%-- end move the following into right positions once test is completed --%>

    <%-- the reason that I have the include here is because the data is set here as well as reloadqpldata.jsp --%>
    <%@ include file="/report/adhocreportfieldtemplate.jsp" %>
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
<input type="hidden" name="facilityName" id="facilityName" value=""/>
<c:choose>
	<c:when test="${adHocMaterialMatrixReportForm.facilityGroupId == null || adHocMaterialMatrixReportForm.facilityGroupId == ''}">
			<input type="hidden" name="templateFacilityGroupId" id="templateFacilityGroupId" value=""/>
	</c:when>
	<c:otherwise>
			<input type="hidden" name="templateFacilityGroupId" id="templateFacilityGroupId" value="${adHocMaterialMatrixReportForm.facilityGroupId}"/>
	</c:otherwise>
</c:choose>
<input type="hidden" name="preSelectDropsDowns" id="preSelectDropsDowns" value="${preSelectDropsDowns}"/>
<input type="hidden" name="templateFacilityId" id="templateFacilityId" value="${adHocMaterialMatrixReportForm.facilityId}"/>
<!-- Hidden element start -->
<input type="hidden" name="templateReportingEntityId" id="templateReportingEntityId" value="${adHocMaterialMatrixReportForm.reportingEntityId}"/>
<input type="hidden" name="facilityGroupName" id="facilityGroupName" value=""/>
<input type="hidden" name="reportingEntityName" id="reportingEntityName" value=""/>
<input type="hidden" name="uAction" id="uAction" value="${uAction}"/>
<input type="hidden" name="companyId" id="companyId" value="${personnelBean.companyId}"/>
<!--This sets the start time for time elapesed from the action.-->
<!--This sets the end time for time elapesed from the action.-->
<input type="hidden" name="ownerCompanyId" id="ownerCompanyId" value="${param.ownerCompanyId}"/>
<input type="hidden" name="reportCriteria" id="reportCriteria" value="${adHocMaterialMatrixReportForm.reportCriteria}"/>
<input type="hidden" name="roomName" id="roomName" value=""/>
<input type="hidden" name="materialCategoryId" id="materialCategoryId" value="${adHocMaterialMatrixReportForm.materialCategoryId}"/>
<input type="hidden" name="templateAreaId" id="templateAreaId" value="${adHocMaterialMatrixReportForm.areaId}"/>
<input type="hidden" name="templateAreaName" id="templateAreaName" value="${adHocMaterialMatrixReportForm.areaName}"/>
<input type="hidden" name="templateBuildingId" id="templateBuildingId" value="${adHocMaterialMatrixReportForm.buildingId}"/>
<input type="hidden" name="templateBuildingName" id="templateBuildingName" value="${adHocMaterialMatrixReportForm.buildingName}"/>
<input type="hidden" name="templateFloorId" id="templateFloorId" value="${adHocMaterialMatrixReportForm.floorId}"/>
<input type="hidden" name="floorName" id="floorName" value=""/>
<input type="hidden" name="templateRoomId" id="templateRoomId" value="${adHocMaterialMatrixReportForm.roomId}"/>
<input type="hidden" name="templateDeptId" id="templateDeptId" value="${adHocMaterialMatrixReportForm.deptId}"/>
<input type="hidden" name="templateDeptName" id="templateDeptName" value="${adHocMaterialMatrixReportForm.deptName}"/>
<input type="hidden" name="templateApplication" id="templateApplication" value="${adHocMaterialMatrixReportForm.application}"/>
<input type="hidden" name="templateApplicationDesc" id="templateApplicationDesc" value="${adHocMaterialMatrixReportForm.applicationDesc}"/>
<input type="hidden" name="thisshoudlbnevepoup" id="thisshoudlbnevepoup" value="${adHocMaterialMatrixReportForm.applicationDesc}"/>

<input type="hidden" name="templateMaterialSubcategoryId" id="templateMaterialSubcategoryId" value="${adHocMaterialMatrixReportForm.materialSubcategoryId}"/>
<input type="hidden" name="templateMaterialSubcategoryDesc" id="templateMaterialSubcategoryName" value="${adHocMaterialMatrixReportForm.materialSubcategoryName}"/>
<input type="hidden" name="templateMaterialCategoryId" id="templateMaterialCategoryId" value="${adHocMaterialMatrixReportForm.materialCategoryId}"/>
<input type="hidden" name="templateMaterialSubcategoryId" id="templateMaterialCategoryName" value="${adHocMaterialMatrixReportForm.materialCategoryName}"/>
<input type="hidden" name="templateCatalogId" id="templateCatalogId" value="${adHocMaterialMatrixReportForm.catalogId}"/>
<input type="hidden" name="catalogCompanyId" id="catalogCompanyId" value="${adHocMaterialMatrixReportForm.catalogCompanyId}"/>
<input type="hidden" name="isMatCatFX" id="isMatCatFX" value=""/>
<input type="hidden" name="supportTriggersThreshold" id="supportTriggersThreshold" value="N"/>
<input type="hidden" name="templateFlammable" id="templateFlammable" value="${adHocMaterialMatrixReportForm.flammable}"/>
<input type="hidden" name="showFlammabilityVocZone" id="showFlammabilityVocZone" value="${showFlammabilityVocZone}"/>
<input type="hidden" name="pageID" id="pageID" value="adHocMatMx"/>

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