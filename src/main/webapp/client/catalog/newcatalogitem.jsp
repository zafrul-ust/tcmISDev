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

<html:html lang="true">

<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<!-- CSS for YUI -->
<%--
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>

<!-- Add any other stylesheets you need for the page here -->
<link rel="stylesheet" type="text/css" href="/css/tabs.css"></link>                

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<%@ include file="/common/locale.jsp" %>

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

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/client/catalog/newcatalogitem.js"></script>

<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<!-- These are for the Grid and inner div popup windows-->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>	
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<%--
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<%--This is for HTML form integration.
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<%--This is for smart rendering.
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<%--This is to suppoert loading by JSON.
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
--%>

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
<%--
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
--%>

<%--This file has our custom sorting and other utility methos for the grid.--%>    
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
	<fmt:message key="label.newcatalogitem"/>
</title>

</head>


<body bgcolor="#ffffff" onLoad="editOnLoad('${uAction}');closeAllchildren();" onunload="closeAllchildren();closeThisWindow();">

<script language="JavaScript" type="text/javascript">
<!--
with(milonic=new menuname("newCatalogItemContextMenu")){
top="offset=2"
style = contextStyle;
margin=3
aI("text=<fmt:message key="label.addcomponent"/>;url=javascript:addNewTab();");
aI("text=<fmt:message key="label.removecomponent"/>;url=javascript:removeTab();");
}

with(milonic=new menuname("newCatalogItemContextMenuWithoutDelete")){
top="offset=2"
style = contextStyle;
margin=3
aI("text=<fmt:message key="label.addcomponent"/>;url=javascript:addNewTab();");
}
// -->
</script>

<script language="JavaScript" type="text/javascript">
<!--

<c:set var="tmpSizeUnitSizeColl" value='${newCatalogItemData.sizeUnitViewColl}'/>
<bean:size id="tmpSizeUnitSize" name="tmpSizeUnitSizeColl"/>
var altSizeUnitRequiredNetWt = new Array(
<c:forEach var="sizeUnitViewBean" items="${newCatalogItemData.sizeUnitViewColl}" varStatus="status">
 <c:choose>
   <c:when test="${status.index == 0 && tmpSizeUnitSize > 1}">
     "N","<c:out value="${status.current.netWtRequired}"/>"
   </c:when>
   <c:otherwise>
     <c:choose>
       <c:when test="${status.index == 0}">
         "<c:out value="${status.current.netWtRequired}"/>"
       </c:when>
       <c:otherwise>
         ,"<c:out value="${status.current.netWtRequired}"/>"
       </c:otherwise>
     </c:choose>
   </c:otherwise>
  </c:choose>
</c:forEach>
);

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
errors:"<fmt:message key="label.errors"/>",
component:"<fmt:message key="label.component"/>",
pleasewait:"<fmt:message key="label.pleasewait"/>",
pleasewaitmenu:"<fmt:message key="label.pleasewaitmenu"/>",
manufacturer:"<fmt:message key="label.manufacturer"/>",
manufacturerpartnumber:"<fmt:message key="label.manufacturerpartnumber"/>",
charsmax:"<fmt:message key="label.30charsmax"/>",	
materialdescription:"<fmt:message key="label.materialdescription"/>",
mfgtradename:"<fmt:message key="label.manufacturertradename"/>",
dimension:"<fmt:message key="label.dimension"/>",
netsize:"<fmt:message key="label.netsize"/>",
netunit:"<fmt:message key="label.netunit"/>",
numperpart:"<fmt:message key="catalog.label.numperpart"/>",
size:"<fmt:message key="label.size"/>",
unit:"<fmt:message key="label.unit"/>",
packagestyle:"<fmt:message key="label.packagestyle"/>",
shippingweight:"<fmt:message key="label.shippingweight"/>",
itemInteger:"<fmt:message key="error.item.integer"/>"};
// -->
 </script>


<tcmis:form action="/newcatalogitem.do" onsubmit="" target="_self">

<div class="interface" id="mainPage" style="">

<%-- transition --%>
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

	 <%-- message --%>
<div id="messageDailogWin" class="errorMessages" style="display: none;overflow: auto;">
	<table width="100%" border="0" cellpadding="2" cellspacing="1">
		<tr>
			<td align="center" width="100%">
				<textarea cols="70" rows="5" class="inputBox" readonly="true" name="messageText" id="messageText">${tcmISError}</textarea>
			</td>
		</tr>
		<tr>
			<td align="center" width="100%">
			<input name="closeMessageB"  id="closeMessageB"  type="button" value="<fmt:message key="label.ok"/>" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onClick="closeMessageWin();"/>
			</td>
		</tr>
	</table>
</div>

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${empty tcmISErrors and empty tcmISError}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
//-->
</script>
<!-- Error Messages Ends -->

<!-- start of contentArea -->
<div class="contentArea">

<!-- Search Option Begins -->
<%--Change the width to what you want your page to span.--%>
<div id="searchMaskTable">

	<table class="tableSearch" border="0">
			 <tr>
				<td><a href="#" onclick="submitUpdate(); return false;"><fmt:message key="button.submit"/></a>
					&nbsp;|&nbsp;<a href="#" onclick="cancel(); return false;"><fmt:message key="label.cancel"/></a>
				</td>
			 </tr>
		    <%--
			 <tr>
				 <td class="optionTitleBoldLeft">
					 <input type="radio" name="englishUnits" id="englishUnits" value="English Units" onclick="englishUnitChecked()"/><fmt:message key="label.englishunits"/>
					 &nbsp;
					<input type="radio" name="metricUnits" id="metricUnits" value="Metric Units" onclick="metricUnitChecked()"/><fmt:message key="label.metricunits"/>
				 </td>
			 </tr>
			 --%>
			 <tr>
				 <td class="optionTitleBoldLeft" colspan="5">
					 <fmt:message key="label.shippingweight"/>:&nbsp;
					 <input class="inputBox" type="text" name="shippingWeight" id="shippingWeight" value="${newCatalogItemData.shippingWeight}"  size="10" maxlength="10">&nbsp;
					 <fmt:message key="label.unit"/>:&nbsp;
					 <select class="selectBox" name="shippingWeightUnit" id="shippingWeightUnit">
						<c:set var="selectedShippingWeightUnit" value='${newCatalogItemData.shippingWeightUnit}'/>
						<c:set var="shippingWeightUnitColl" value='${newCatalogItemData.shippingWeightUnitColl}'/>
						<bean:size id="shippingWeightUnitSize" name="shippingWeightUnitColl"/>
						<c:if test="${shippingWeightUnitSize > 1}">
							<option value="Select One" selected><fmt:message key="label.selectOne"/></option>
						</c:if>
						<c:forEach var="vvSizeUnitBean" items="${newCatalogItemData.shippingWeightUnitColl}" varStatus="status1">
							<c:set var="currentShippingWeightUnit" value='${status1.current.sizeUnit}'/>
							<c:choose>
								<c:when test="${currentShippingWeightUnit == selectedShippingWeightUnit}">
									<option value="<c:out value="${status1.current.sizeUnit}"/>" selected><c:out value="${status1.current.sizeUnit}" escapeXml="false"/></option>
								</c:when>
								<c:otherwise>
									<option value="<c:out value="${status1.current.sizeUnit}"/>"><c:out value="${status1.current.sizeUnit}" escapeXml="false"/></option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					 </select>&nbsp;
					 (<fmt:message key="label.ifknown"/>)
				 </td>
				 <td class="optionTitleBoldLeft">
					 <input name="noSpec" type="checkbox"  value="Y" id="noSpec">
					 <fmt:message key="label.manufacturercertnotavailable"/>
				 </td>
			 </tr>
<%--			 <tr>
				<td class="optionTitleBoldLeft">
					 <input name="mvItem" type="checkbox"  value="Y" id="mvItem">
					 <fmt:message key="label.mvitem"/>
				</td>
				<td width="1%">&nbsp;</td>
				<td class="optionTitleBoldRight" nowrap>
					 <fmt:message key="label.nominalqty"/>:&nbsp;
					 <input class="inputBox" type="text" name="nominalQty" id="nominalQty" value=""  size="10" maxlength="10">&nbsp;
				</td>
				<td width="1%">&nbsp;</td>
				<td class="optionTitleBoldRight" nowrap>
					 <fmt:message key="receiving.label.nominalpkg"/>:&nbsp;
					 <input class="inputBox" type="text" name="nominalPkg" id="nominalPkg" value=""  size="10" maxlength="10">&nbsp;
				</td>
				
			 </tr>   --%>
		 </table>

<div>
<%-- Right Section 1 --%>
<!-- Initialize Menus -->
<script>
 drawMenus();
</script>

<!-- CSS Tabs -->
<div id="newChemTabs">
 <ul id="mainTabList">
 </ul>
</div>
<!-- CSS Tabs End -->

<script language="JavaScript" type="text/javascript">
<!--
function startOnload()
{

<c:set var="dataCount" value='${0}'/>
<c:set var="selectedTabId" value=''/>

<c:forEach var="catalogAddItemBean" items="${newCatalogItemData.catalogAddItemColl}" varStatus="status">
 <c:if test="${dataCount == 0}">
  <c:set var="selectedTabId" value="${dataCount}"/>
 </c:if>
 openTab('<c:out value="${dataCount}"/>','','<fmt:message key="label.component"/>','','');
 <c:set var="dataCount" value='${dataCount+1}'/>
</c:forEach>

}
// -->
</script>

<div id="section1" class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
    <table class="tableSearch" border="0">
	 <tr>
		 <td>

		 </td>
	 </tr>
	 <tr>
    <td>
    	<div id="tabsdiv">
			<c:set var="partCount" value='0'/>
			<c:forEach var="catalogAddItem" items="${newCatalogItemData.catalogAddItemColl}" varStatus="status">
			  <div id="newItem${partCount}">
			  		<%--
					<table width="100%" class="tableSearch" border="1">
						  <tr>
							  <td class="optionTitleBoldLeft">
								<input type="radio" name="catalogAddItemBean[${partCount}].materialCriteria" id="materialCriteria${partCount}" value="MSDS Required" class="radioBtns" onclick="msdsRequiredChecked()" checked="checked"/><fmt:message key="label.msdsrequired"/>
								&nbsp;
								<input type="radio" name="catalogAddItemBean[${partCount}].materialCriteria" id="materialCriteria${partCount}" value="No MSDS Required" class="radioBtns" onclick="noMsdsRequiredChecked()"/><fmt:message key="label.nomsdsrequired"/>
							  </td>
						  </tr>
						  <tr>
							  <td class="optionTitleBoldLeft">
								  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								  <span id="cylinderTypeSpan${partCount}">
										<input type="radio" name="catalogAddItemBean[${partCount}].cylinderType" id="cylinderType${partCount}" value="Non Cylinder" class="radioBtns" onclick="nonCylinderChecked()" checked="checked"/><fmt:message key="label.nonCylinder"/>
										&nbsp;
										<input type="radio" name="catalogAddItemBean[${partCount}].cylinderType" id="cylinderType${partCount}" value="Cylinder" class="radioBtns" onclick="cylinderChecked()"/><fmt:message key="label.cylinder"/>
									</span>
								 </td>
						  </tr>
					  </table>
                --%>

					<fieldset>
					  <legend><fmt:message key="label.materialinfo"/></legend>
					  <table width="825px" class="tableSearch" border="0">
						 <tr>
							<td class="optionTitleBoldLeft" colspan="2" nowrap>
								<fmt:message key="label.materialdescription"/> - <fmt:message key="label.240charsmax"/>:<span style='font-size:12.0pt;color:red'>*</span> (<fmt:message key="label.includeallinformation"/>)
							</td>
						 </tr>
						 <tr>
							<td class="optionTitleBoldLeft" colspan="2">
								 <input type="text" class="inputBox" name="catalogAddItemBean[${partCount}].materialDesc" id="materialDesc${partCount}" value="${catalogAddItem.materialDesc}" size="110" maxLength="240"/>
							</td>
						 </tr>
						 <tr>
							<td class="optionTitleBoldLeft">
								<fmt:message key="label.grade"/>:
							</td>
							<td class="optionTitleBoldLeft">
								<fmt:message key="label.dimension"/>:
							</td>
						</tr>
						<tr>
							<td class="optionTitleBoldLeft">
								<input class="inputBox" type="text" name="catalogAddItemBean[${partCount}].grade" id="grade${partCount}" value="${catalogAddItem.grade}" size="50" maxLength="60"/>
							</td>
							<td class="optionTitleBoldLeft">
								<input class="inputBox" type="text" name="catalogAddItemBean[${partCount}].dimension" id="dimension${partCount}" value="${catalogAddItem.dimension}" size="50" maxlength="200">
							</td>
						</tr>

						 <tr><td class="optionTitleBoldLeft" colspan="2"><fmt:message key="label.manufacturer"/> - <fmt:message key="label.240charsmax"/>:<span style='font-size:12.0pt;color:red'>*</span></td></tr>
						 <tr>
							<td class="optionTitleBoldLeft" colspan="2">
								<input class="inputBox" type="hidden" name="catalogAddItemBean[${partCount}].mfgId" id="mfgId${partCount}" value="${catalogAddItem.mfgId}" >
								<input class="inputBox" type="text" name="catalogAddItemBean[${partCount}].manufacturer" id="manufacturer${partCount}" value="${catalogAddItem.manufacturer}" size="110" maxlength="240">
								<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="selectedManufacturer" id="selectedManufacturer" value="..." align="right" onClick="lookupManufacturer();"/>
							</td>
						</tr>
					<tr>
							<td class="optionTitleBoldLeft" colspan="2">
							  <fmt:message key="label.manufacturercontact"/><br>
						  </td>
						</tr>
						<tr>
							<td class="optionTitleBoldLeft" colspan="2">
								<input class="inputBox" type="text" name="catalogAddItemBean[${partCount}].manufacturerContact" id="manufacturerContact${partCount}" value="${catalogAddItem.manufacturerContact}" size="50" maxLength="50"/>
							</td>
						</tr>
						<tr>
							<td class="optionTitleBoldLeft" colspan="2">
							  <fmt:message key="label.manufacturernotes"/><br>
						  </td>
						</tr>
						<tr>
							<td class="optionTitleBoldLeft" colspan="2">
								<input class="inputBox" type="text" name="catalogAddItemBean[${partCount}].manufacturerNotes" id="manufacturerNotes${partCount}" value="${catalogAddItem.manufacturerNotes}" size="110" maxLength="4000"/>
							</td>
						</tr>
						<tr>
							<td class="optionTitleBoldLeft" colspan="2">
							  <fmt:message key="label.manufacturerrurl"/><br>
						  </td>
						</tr>
						<tr>
							<td class="optionTitleBoldLeft" colspan="2">
								<input class="inputBox" type="text" name="catalogAddItemBean[${partCount}].manufacturerUrl" id="manufacturerUrl${partCount}" value="${catalogAddItem.manufacturerUrl}" size="110" maxLength="200"/>
							</td>
						</tr>   	
						<tr>
							<td class="optionTitleBoldLeft" colspan="2">
							  <fmt:message key="label.manufacturertradename"/> - <fmt:message key="label.100charsmax"/>: (<fmt:message key="label.frommsds"/>)<br>
						  </td>
						</tr>
						<tr>
							<td class="optionTitleBoldLeft" colspan="2">
								<input class="inputBox" type="text" name="catalogAddItemBean[${partCount}].mfgTradeName" id="mfgTradeName${partCount}" value="${catalogAddItem.mfgTradeName}" size="110" maxLength="100"/>
							</td>
						</tr>
						<tr>
							<td class="optionTitleBoldLeft" colspan="2">
								<fmt:message key="label.manufacturerpartnumber"/> - <fmt:message key="label.30charsmax"/>:
							</td>
						</tr>
						<tr>
							<td class="optionTitleBoldLeft" colspan="2">
								<input class="inputBox" type="text" name="catalogAddItemBean[${partCount}].mfgCatalogId" id="mfgCatalogId${partCount}" value="${catalogAddItem.mfgCatalogId}"  size="110" maxlength="30">
							</td>
						</tr>

					  </table>
					</fieldset>

					<input name="catalogAddItemBean[${partCount}].newTabComponent" id="newTabComponent${partCount}" type="hidden" value="New">
					<input name="catalogAddItemBean[${partCount}].netWtRequired" id="netWtRequired${partCount}" type="hidden" value="">
					<input name="catalogAddItemBean[${partCount}].materialId" id="materialId${partCount}" value="${catalogAddItem.materialId}" type="hidden"/>
					<input name="catalogAddItemBean[${partCount}].companyId" id="companyId${partCount}" type="hidden" value="${newCatalogItemData.companyId}">  

					<fieldset>
					  <legend><fmt:message key="label.sizepackinginfo"/></legend>
					  <span id="nonCylinderPackagingSpan${partCount}">
						<table width="825px" border="0">
							<tr>
								<td class="optionTitleBoldLeft" width="15%">
									<fmt:message key="catalog.label.numperpart"/>:<span style='font-size:12.0pt;color:red'>*</span>
								</td>
								<td width="1%"> &nbsp; </td>
								<td class="optionTitleBoldLeft" width="15%">
									<fmt:message key="label.size"/>:<span style='font-size:12.0pt;color:red'>*</span>
								</td>
								<td class="optionTitleBoldLeft" width="20%">
									<fmt:message key="label.unit"/>:<span style='font-size:12.0pt;color:red'>*</span>
								</td>
								<%--
								<td class="optionTitleBoldLeft" width="50%">
									<fmt:message key="label.prepackagestyle"/>:
								</td>
								<td class="optionTitleBoldLeft" width="50%">
									<fmt:message key="label.packagesize"/>:
								</td>
								<td class="optionTitleBoldLeft" width="50%">
									<fmt:message key="label.packageunits"/>:
								</td>
                        --%>
								<td class="optionTitleBoldLeft" width="50%">
									<fmt:message key="label.packagestyle"/>:<span style='font-size:12.0pt;color:red'>*</span>
								</td>
							 </tr>
							<tr>
								<td>
									<input class="inputBox" type="text" name="catalogAddItemBean[${partCount}].componentsPerItem" id="componentsPerItem${partCount}" value="${catalogAddItem.componentsPerItem}" size="3" maxlength="12">
								</td>
								<td width="1%"> X </td>
								<td width="15%" nowrap>
									<input class="inputBox" type="text" name="catalogAddItemBean[${partCount}].partSize" id="partSize${partCount}" value="${catalogAddItem.partSize}" size="3" maxlength="10">
								</td>
								<td width="5%" class="optionTitleLeft">
									<select class="selectBox" name="catalogAddItemBean[${partCount}].sizeUnit" id="sizeUnit${partCount}" onchange="unitChanged()" >
										<c:set var="selectedSizeUnit" value='${catalogAddItem.sizeUnit}'/>
										<c:set var="sizeUnitSizeColl" value='${newCatalogItemData.sizeUnitViewColl}'/>
										<bean:size id="sizeUnitSize" name="sizeUnitSizeColl"/>
										<c:if test="${sizeUnitSize > 1}">
											<option value="Select One" selected><fmt:message key="label.selectOne"/></option>
										</c:if>
										<c:forEach var="vvSizeUnitBean" items="${newCatalogItemData.sizeUnitViewColl}" varStatus="status1">
											<c:set var="currentSizeUnit" value='${status1.current.sizeUnit}'/>
											<c:choose>
												<c:when test="${currentSizeUnit == selectedSizeUnit}">
													<option value="<c:out value="${status1.current.sizeUnit}"/>" selected><c:out value="${status1.current.sizeUnit}" escapeXml="false"/></option>
												</c:when>
												<c:otherwise>
													<option value="<c:out value="${status1.current.sizeUnit}"/>"><c:out value="${status1.current.sizeUnit}" escapeXml="false"/></option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</td>
								<%--
								<td width="10%" class="optionTitleLeft">
											<select class="selectBox" name="catalogAddItemBean[${partCount}].prePkgStyle" id="prePkgStyle${partCount}">
												<c:set var="selectedPkgStyle" value='${catalogAddItem.pkgStyle}'/>
											<c:set var="pkgStyleSizeColl" value='${vvPkgStyleBeanCollection}'/>
											<bean:size id="pkgStyleSize" name="pkgStyleSizeColl"/>
											<c:if test="${pkgStyleSize > 1}">
												<option value="Select One" selected><fmt:message key="label.selectOne"/></option>
											</c:if>
											<c:forEach var="vvPkgStyleBean" items="${vvPkgStyleBeanCollection}" varStatus="status2">
												<c:set var="currentPkgStyle" value='${status2.current.pkgStyle}'/>
												<c:choose>
													<c:when test="${currentPkgStyle == selectedPkgStyle}">
														<option value="<c:out value="${status2.current.pkgStyle}"/>" selected><c:out value="${status2.current.pkgStyle}" escapeXml="false"/></option>
													</c:when>
													<c:otherwise>
														<option value="<c:out value="${status2.current.pkgStyle}"/>"><c:out value="${status2.current.pkgStyle}" escapeXml="false"/></option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
								</td>
								<td>
									<fmt:message key="label.ina"/>&nbsp;
									<input class="inputBox" type="text" name="catalogAddItemBean[${partCount}].pkgSize" id="pkgSize${partCount}" value="${catalogAddItem.componentsPerItem}" size="3" maxlength="12">
								</td>
								<td width="10%" class="optionTitleLeft">
											<select class="selectBox" name="catalogAddItemBean[${partCount}].pkgSizeUnit" id="pkgSizeUnit${partCount}">
												<c:set var="selectedPkgStyle" value='${catalogAddItem.pkgStyle}'/>
											<c:set var="pkgStyleSizeColl" value='${vvPkgStyleBeanCollection}'/>
											<bean:size id="pkgStyleSize" name="pkgStyleSizeColl"/>
											<c:if test="${pkgStyleSize > 1}">
												<option value="Select One" selected><fmt:message key="label.selectOne"/></option>
											</c:if>
											<c:forEach var="vvPkgStyleBean" items="${vvPkgStyleBeanCollection}" varStatus="status2">
												<c:set var="currentPkgStyle" value='${status2.current.pkgStyle}'/>
												<c:choose>
													<c:when test="${currentPkgStyle == selectedPkgStyle}">
														<option value="<c:out value="${status2.current.pkgStyle}"/>" selected><c:out value="${status2.current.pkgStyle}" escapeXml="false"/></option>
													</c:when>
													<c:otherwise>
														<option value="<c:out value="${status2.current.pkgStyle}"/>"><c:out value="${status2.current.pkgStyle}" escapeXml="false"/></option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
								</td>
                        --%>

								<td width="10%" class="optionTitleLeft">
									<input class="inputBox" type="text" name="catalogAddItemBean[${partCount}].pkgStyle" id="pkgStyle${partCount}" value="${catalogAddItem.pkgStyle}" readonly="true" size="40">
									<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="selectedPkgStyle" id="selectedPkgStyle" value="..." align="right" onClick="lookupPkgStyle();"/>
									<%--
											<select class="selectBox" name="catalogAddItemBean[${partCount}].pkgStyle" id="pkgStyle${partCount}">
												<c:set var="selectedPkgStyle" value='${catalogAddItem.pkgStyle}'/>
											<c:set var="pkgStyleSizeColl" value='${vvPkgStyleBeanCollection}'/>
											<bean:size id="pkgStyleSize" name="pkgStyleSizeColl"/>
											<c:if test="${pkgStyleSize > 1}">
												<option value="Select One" selected><fmt:message key="label.selectOne"/></option>
											</c:if>
											<c:forEach var="vvPkgStyleBean" items="${vvPkgStyleBeanCollection}" varStatus="status2">
												<c:set var="currentPkgStyle" value='${status2.current.pkgStyle}'/>
												<c:choose>
													<c:when test="${currentPkgStyle == selectedPkgStyle}">
														<option value="<c:out value="${status2.current.pkgStyle}"/>" selected><c:out value="${status2.current.pkgStyle}" escapeXml="false"/></option>
													</c:when>
													<c:otherwise>
														<option value="<c:out value="${status2.current.pkgStyle}"/>"><c:out value="${status2.current.pkgStyle}" escapeXml="false"/></option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
									--%>
								</td>
							</tr>
							<tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td class="optionTitleBoldLeft" width="15%">
								<span id="netWtLabelSpan${partCount}" name="netWtLabelSpan${partCount}">
									<fmt:message key="label.netsize"/>:<span style='font-size:12.0pt;color:red'>*</span>
								</span>
							  </td>
							<td class="optionTitleBoldLeft" width="15%">
								<span id="netWtUnitLabelSpan${partCount}" name="netWtUnitLabelSpan${partCount}">
									<fmt:message key="label.netunit"/>:<span style='font-size:12.0pt;color:red'>*</span>
								</span>
							  </td>
						 </tr>
						 <tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td width="15%">
								<span id="netWtSpan${partCount}" name="netWtSpan${partCount}">
									<input class="inputBox" type="text" name="catalogAddItemBean[${partCount}].netwt" id="netwt${partCount}" value="${catalogAddItem.netwt}"  size="3" maxlength="10">
								</span>
							</td>
							<td width="15%">
								<span id="netWtUnitSpan${partCount}" name="netWtUnitSpan${partCount}">
									<select class="selectBox" name="catalogAddItemBean[${partCount}].netwtUnit" id="netwtUnit${partCount}" >
											<c:set var="selectedNetwtUnit" value='${catalogAddItem.netwtUnit}'/>
										<c:set var="sizeUnitViewColl" value='${newCatalogItemData.netWtSizeUnitColl}'/>
										<bean:size id="sizeUnitViewSize" name="sizeUnitViewColl"/>
										<c:if test="${sizeUnitViewSize > 1}">
											<option value="Select One" selected><fmt:message key="label.selectOne"/></option>
										</c:if>
										<c:forEach var="sizeUnitView" items="${newCatalogItemData.netWtSizeUnitColl}" varStatus="status3">
											<c:set var="currentNetwtUnit" value='${status3.current.sizeUnit}'/>
											<c:choose>
												<c:when test="${currentNetwtUnit == selectedNetwtUnit}">
													<option value="<c:out value="${status3.current.sizeUnit}"/>" selected><c:out value="${status3.current.sizeUnit}" escapeXml="false"/></option>
												</c:when>
												<c:otherwise>
													<option value="<c:out value="${status3.current.sizeUnit}"/>"><c:out value="${status3.current.sizeUnit}" escapeXml="false"/></option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</span>
							</td>
						 </tr>
					  </table>
					  </span>

					  <span id="cylinderPackagingSpan${partCount}" style="display:none">
						<table width="100%" border="0">
							<tr>
								<td class="optionTitleBoldLeft" width="15%">
									<fmt:message key="catalog.label.numperpart"/>:<span style='font-size:12.0pt;color:red'>*</span>
								</td>
								<td width="1%"> &nbsp; </td>
								<td class="optionTitleBoldLeft" width="15%">
									<fmt:message key="label.size"/>:<span style='font-size:12.0pt;color:red'>*</span>
								</td>
								<td class="optionTitleBoldLeft" width="20%">
									<fmt:message key="label.unit"/>:<span style='font-size:12.0pt;color:red'>*</span>
								</td>
								<td class="optionTitleBoldLeft" width="50%">
									<fmt:message key="label.cylindersize"/>:<span style='font-size:12.0pt;color:red'>*</span>
								</td>
								<td class="optionTitleBoldLeft" width="50%">
									<fmt:message key="label.cylindermatl"/>:<span style='font-size:12.0pt;color:red'>*</span>
								</td>
								<td class="optionTitleBoldLeft" width="50%">
									<fmt:message key="label.valuetype"/>:<span style='font-size:12.0pt;color:red'>*</span>
								</td>
							 </tr>

							<tr>
								<td>
									<input class="inputBox" type="text" name="catalogAddItemBean[${partCount}].cylinderComponentsPerItem" id="cylinderComponentsPerItem${partCount}" value="${catalogAddItem.componentsPerItem}" size="12" maxlength="12">
								</td>
								<td width="1%"> X </td>
								<td width="15%" nowrap>
									<input class="inputBox" type="text" name="catalogAddItemBean[${partCount}].cylinderSize" id="cylinderSize${partCount}" value="${catalogAddItem.partSize}" size="10" maxlength="10">
								</td>
								<td width="5%" class="optionTitleLeft">
									<select class="selectBox" name="catalogAddItemBean[${partCount}].cylinderSizeUnit" id="cylinderSizeUnit${partCount}" >
											<c:set var="selectedSizeUnit" value='${catalogAddItem.sizeUnit}'/>
										<c:set var="sizeUnitSizeColl" value='${newCatalogItemData.sizeUnitViewColl}'/>
										<bean:size id="sizeUnitSize" name="sizeUnitSizeColl"/>
										<c:if test="${sizeUnitSize > 1}">
											<option value="Select One" selected><fmt:message key="label.selectOne"/></option>
										</c:if>
										<c:forEach var="vvSizeUnitBean" items="${newCatalogItemData.sizeUnitViewColl}" varStatus="status1">
											<c:set var="currentSizeUnit" value='${status1.current.sizeUnit}'/>
											<c:choose>
												<c:when test="${currentSizeUnit == selectedSizeUnit}">
													<option value="<c:out value="${status1.current.sizeUnit}"/>" selected><c:out value="${status1.current.sizeUnit}" escapeXml="false"/></option>
												</c:when>
												<c:otherwise>
													<option value="<c:out value="${status1.current.sizeUnit}"/>"><c:out value="${status1.current.sizeUnit}" escapeXml="false"/></option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</td>
								<td width="10%" class="optionTitleLeft">
											<select class="selectBox" name="catalogAddItemBean[${partCount}].cylinderPkgStyle" id="cylinderPkgStyle${partCount}">
												<c:set var="selectedPkgStyle" value='${catalogAddItem.pkgStyle}'/>
											<c:set var="pkgStyleSizeColl" value='${vvPkgStyleBeanCollection}'/>
											<bean:size id="pkgStyleSize" name="pkgStyleSizeColl"/>
											<c:if test="${pkgStyleSize > 1}">
												<option value="Select One" selected><fmt:message key="label.selectOne"/></option>
											</c:if>
											<c:forEach var="vvPkgStyleBean" items="${vvPkgStyleBeanCollection}" varStatus="status2">
												<c:set var="currentPkgStyle" value='${status2.current.pkgStyle}'/>
												<c:choose>
													<c:when test="${currentPkgStyle == selectedPkgStyle}">
														<option value="<c:out value="${status2.current.pkgStyle}"/>" selected><c:out value="${status2.current.pkgStyle}" escapeXml="false"/></option>
													</c:when>
													<c:otherwise>
														<option value="<c:out value="${status2.current.pkgStyle}"/>"><c:out value="${status2.current.pkgStyle}" escapeXml="false"/></option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
								</td>
								<td width="50%" class="optionTitleLeft">
									<select class="selectBox" name="catalogAddItemBean[${partCount}].cylinderMaterial" id="cylinderMaterial${partCount}" >
											<c:set var="selectedSizeUnit" value='${catalogAddItem.sizeUnit}'/>
										<c:set var="sizeUnitSizeColl" value='${newCatalogItemData.sizeUnitViewColl}'/>
										<bean:size id="sizeUnitSize" name="sizeUnitSizeColl"/>
										<c:if test="${sizeUnitSize > 1}">
											<option value="Select One" selected><fmt:message key="label.selectOne"/></option>
										</c:if>
										<c:forEach var="vvSizeUnitBean" items="${newCatalogItemData.sizeUnitViewColl}" varStatus="status1">
											<c:set var="currentSizeUnit" value='${status1.current.sizeUnit}'/>
											<c:choose>
												<c:when test="${currentSizeUnit == selectedSizeUnit}">
													<option value="<c:out value="${status1.current.sizeUnit}"/>" selected><c:out value="${status1.current.sizeUnit}" escapeXml="false"/></option>
												</c:when>
												<c:otherwise>
													<option value="<c:out value="${status1.current.sizeUnit}"/>"><c:out value="${status1.current.sizeUnit}" escapeXml="false"/></option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</td>
								<td width="50%" class="optionTitleLeft">
									<select class="selectBox" name="catalogAddItemBean[${partCount}].valueType" id="valueType${partCount}" >
											<c:set var="selectedSizeUnit" value='${catalogAddItem.sizeUnit}'/>
										<c:set var="sizeUnitSizeColl" value='${newCatalogItemData.sizeUnitViewColl}'/>
										<bean:size id="sizeUnitSize" name="sizeUnitSizeColl"/>
										<c:if test="${sizeUnitSize > 1}">
											<option value="Select One" selected><fmt:message key="label.selectOne"/></option>
										</c:if>
										<c:forEach var="vvSizeUnitBean" items="${newCatalogItemData.sizeUnitViewColl}" varStatus="status1">
											<c:set var="currentSizeUnit" value='${status1.current.sizeUnit}'/>
											<c:choose>
												<c:when test="${currentSizeUnit == selectedSizeUnit}">
													<option value="<c:out value="${status1.current.sizeUnit}"/>" selected><c:out value="${status1.current.sizeUnit}" escapeXml="false"/></option>
												</c:when>
												<c:otherwise>
													<option value="<c:out value="${status1.current.sizeUnit}"/>"><c:out value="${status1.current.sizeUnit}" escapeXml="false"/></option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</td>
							 </tr>
					  </table>
					  </span>

					  <span id="noMsdsSpan${partCount}" style="display:none">
						<table width="100%" border="0">
							<tr>
								<td class="optionTitleBoldLeft" width="15%">
									<fmt:message key="catalog.label.numperpart"/>:<span style='font-size:12.0pt;color:red'>*</span>
								</td>
								<td class="optionTitleBoldLeft" width="50%">
									<fmt:message key="label.packagestyle"/>:<span style='font-size:12.0pt;color:red'>*</span>
								</td>
							</tr>
							<tr>
								<td>
									<input class="inputBox" type="text" name="catalogAddItemBean[${partCount}].noMsdsComponentsPerItem" id="noMsdsComponentsPerItem${partCount}" value="${catalogAddItem.componentsPerItem}" size="12" maxlength="12">
								</td>
								<td width="10%" colspan="2" class="optionTitleLeft">
									  <fmt:message key="label.per"/>&nbsp;
											 <select class="selectBox" name="catalogAddItemBean[${partCount}].noMsdsPkgStyle" id="noMsdsPkgStyle${partCount}">
												<c:set var="selectedPkgStyle" value='${catalogAddItem.pkgStyle}'/>
											<c:set var="pkgStyleSizeColl" value='${vvPkgStyleBeanCollection}'/>
											<bean:size id="pkgStyleSize" name="pkgStyleSizeColl"/>
											<c:if test="${pkgStyleSize > 1}">
												<option value="Select One" selected><fmt:message key="label.selectOne"/></option>
											</c:if>
											<c:forEach var="vvPkgStyleBean" items="${vvPkgStyleBeanCollection}" varStatus="status2">
												<c:set var="currentPkgStyle" value='${status2.current.pkgStyle}'/>
												<c:choose>
													<c:when test="${currentPkgStyle == selectedPkgStyle}">
														<option value="<c:out value="${status2.current.pkgStyle}"/>" selected><c:out value="${status2.current.pkgStyle}" escapeXml="false"/></option>
													</c:when>
													<c:otherwise>
														<option value="<c:out value="${status2.current.pkgStyle}"/>"><c:out value="${status2.current.pkgStyle}" escapeXml="false"/></option>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</select>
								</td>
							</tr>
					  </table>
					  </span>
					</fieldset>
				</div>
				<c:set var="partCount" value='${partCount+1}'/>
			</c:forEach>
		 </div>

   	</td>
   	</tr>
   </table>
   <!-- End search options -->
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>

</div>

</div>
<!-- Search Option Ends -->

<!-- Error Messages Begins -->
<!-- Build this section only if there is an error message to display.
     For the search section, we show the error messages within its frame
-->
<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
<div class="spacerY">&nbsp;
<div id="searchErrorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
</c:if>
<!-- Error Messages Ends -->
<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input type="hidden" name="uAction" id="uAction" value=""/>
<input type="hidden" name="totalLines" id="totalLines" value="${dataCount}" >
<input type="hidden" name="partCount" id="partCount" value='${partCount}'/>
<input type="hidden" name="personnelId" id="personnelId" value='${personnelBean.personnelId}'/>
<input type="hidden" name="requestId" id="requestId" value='${requestId}'/>
<input type="hidden" name="companyId" id="companyId" value='${newCatalogItemData.companyId}'/>
<input type="hidden" name="startingView" id="startingView" value='${newCatalogItemData.startingView}'/>
<input type="hidden" name="inventoryGroup" id="inventoryGroup" value='${newCatalogItemData.inventoryGroup}'/>
<input type="hidden" name="alternateName" id="alternateName" value='${newCatalogItemData.alternateName}'/>
<input type="hidden" name="itemId" id="itemId" value='${newCatalogItemData.itemId}'/>
<input type="hidden" name="temporaryItemId" id="temporaryItemId" value='${newCatalogItemData.temporaryItemId}'/>
<input type="hidden" name="catalogCompanyId" id="catalogCompanyId" value='${newCatalogItemData.catalogCompanyId}'/>
<input type="hidden" name="sourcePage" id="sourcePage" value="${sourcePage}" >

</div>
<!-- Hidden elements end -->
</tcmis:form>
</div>
<!-- close of contentArea -->

</div> <!-- close of interface -->

</body>
</html:html>