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
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"> </link>

<%@ include file="/common/locale.jsp" %>
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />

<link rel="stylesheet" type="text/css" href="/css/clientpages.css"></link>
<style type="text/css">
html {
  height: 100%;
  max-height:100%;
  margin-bottom: 1px;
  overflow: hidden;
}
</style>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
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
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/js/common/msds/msdsviewermain.js"></script>


<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

    
<title>
<fmt:message key="title.msdsviewer"/>
</title>
<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={
    alert:"<fmt:message key="label.alert"/>",
    and:"<fmt:message key="label.and"/>",
    or:"<fmt:message key="label.or"/>",
    all:"<fmt:message key="label.all"/>",
    validvalues:"<fmt:message key="label.validvalues"/>",
    submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
    itemInteger:"<fmt:message key="error.item.integer"/>",
    errors:"<fmt:message key="label.errors"/>",
    pleasewait:"<fmt:message key="label.pleasewait"/>",
    searchText:"<fmt:message key="label.searchtext"/>",
    ph:"<fmt:message key="label.ph"/>",
    flashpoint:"<fmt:message key="label.flashpoint"/>",
    vaporpressure:"<fmt:message key="label.vaporpressure"/>",
    voc:"<fmt:message key="label.voc"/>",
    solids:"<fmt:message key="label.solids"/>",
    nfpa:"<fmt:message key="label.nfpa"/>",
    hmis:"<fmt:message key="label.hmis"/>",
    health:"<fmt:message key="label.health"/>",
    flammability:"<fmt:message key="label.flammability"/>",
    reactivity:"<fmt:message key="label.reactivity"/>",
    list:"<fmt:message key="label.list"/>",
    casnumber:"<fmt:message key="label.casnumber"/>",
    max:"<fmt:message key="label.max"/>",
    min:"<fmt:message key="label.min"/>",
    average:"<fmt:message key="label.average"/>",
    searchInput:"<fmt:message key="error.searchInput.integer"/>"
};

useLayout=false;

var defaults = {
	   fac: {id:'',name:'<fmt:message key="label.all"/>'},
   	   app: {id:'',name:'<fmt:message key="label.all"/>'}
}

<c:set var="currentFacilityId" value=''/>
var facApplicationArr = [
	<c:forEach var="facBean" items="${facilityWorkareaColl}" varStatus="status">
	  <c:if test="${currentFacilityId != facBean.facilityId && currentFacilityId != ''}">]},</c:if> 
	  <c:if test="${currentFacilityId != facBean.facilityId}"> 
	  { 	  id: '${facBean.facilityId}', 
		  name: '<tcmis:jsReplace value="${facBean.facilityName}"/>',
		  coll: [{ 
		  	  id: '${facBean.application}', 
		  	  name: '<tcmis:jsReplace value="${facBean.applicationDesc}"/>'}
	  </c:if> 
	  <c:if test="${currentFacilityId == facBean.facilityId}"> 
	  ,{	  	  id: '${facBean.application}', 
		  	  name: '<tcmis:jsReplace value="${facBean.applicationDesc}"/>'}
	  </c:if> 

	<c:set var="currentFacilityId" value='${facBean.facilityId}'/>
	</c:forEach>]}]; 

// -->
 </script>
</head>

<!--call the loadLayoutWin() to set the sizes of the search section and initiate the layout. 
If you dont want to use the layout set javascript variable useLayout=false;-->
<body bgcolor="#ffffff"  onload="setFacAppDropdowns();var listOrCasNos = document.getElementsByName('listOrCasNos');listOrCasNos[0].checked = true;$('casNumberAndOr').value = 'and';" onresize="resizeFrames();">

<div class="interface" id="mainPage" style="">
<!-- Search Frame Begins -->
<div id="searchFrameDiv">

<div class="contentArea">
<tcmis:form action="/msdsviewerresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
<!-- Search Option Begins -->
<table id="searchMaskTable" width="1000" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td>
            <div class="roundcont filterContainer">
                <div class="roundright">
                    <div class="roundtop">
                        <div class="roundtopright"><img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none"/></div>
                    </div>
                    <div class="roundContent">
                     <!-- Insert all the search option within this div -->
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
        <tr>
            <td width="10%" class="optionTitleBoldLeft"nowrap>&nbsp;
                <fmt:message key="label.facility"/>:
                &nbsp;
                <c:set var="selectedFacilityId" value="${personnelBean.myDefaultFacilityId}"/>
                <select name="facilityId" id="facilityId" class="selectBox" >
                </select>
            </td>

            <td width="30%" class="optionTitleBoldLeft">
                &nbsp;
            </td>
            <!-- search by -->
            <td width="10%" >
	            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	        </td>
	        <td width="10%" id="simpleSearchTd1" class="optionTitleBoldRight" nowrap>
	            <fmt:message key="label.search"/>:&nbsp;
	        </td>
	        <td width="50%" id="simpleSearchTd2" class="optionTitleBoldLeft" nowrap>
		        &nbsp;         
	            <input name="simpleSearchText" id="simpleSearchText" type="text" class="inputBox" onkeypress="onKeySearch(event,submitSearchForm);" size="45" value="">&nbsp;
	        </td>
	        <td width="10%">
	            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	        </td>
        </tr>

       <tr>  
         <td colspan="7">   
            <!-- search button -->
            &nbsp;
            <input name="submitSearch" id="submitSearch" type="button" class="inputBtns" value="<fmt:message key="label.search"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "return submitSearchForm()">
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input name="createExcel" id="createExcel" type="button"
				   value="<fmt:message key="label.createexcelfile"/>" onclick="generateExcel();" class="inputBtns"
				   onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" />
			 <input name="clearallfields" id="clearallfields" type="button"
				   value="<fmt:message key="label.clearallfields"/>" onclick="clearAll();" class="inputBtns"
				   onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" />
          </td>
        </tr>
        
    </table>
    
    <div class="boxhead">&nbsp;<a href="#" onclick="showAdvancedSearch();"><fmt:message key="label.advancedsearchoptions" /><span id="showSpan">&nbsp;>></span></a></div>
    
    <table id="advancedSearchTable" width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch" style="display:none;">
        <tr>
            <td width="10%" class="optionTitleBoldRight" nowrap>&nbsp;
                <fmt:message key="label.workarea"/>:
            </td>

            <td width="30%" class="optionTitleBoldLeft">
            	<table>
            		<tr>
            			<td>
			                <select name="application" id="application" multiple="multiple" size="4" onchange="workAreaChanged();" class="selectBox" >
			                </select>
                		</td>
            			<td nowrap>
			                <input name="approvedOnlyDisplay" id="approvedOnlyDisplay" type="checkbox" class="radioBtns" value="Y" >
			                <fmt:message key="label.approvedonly"/>&nbsp;&nbsp;&nbsp;&nbsp;
			                <input name="approvedOnly" id="approvedOnly" type="hidden" value="" >
                		</td>
                	</tr>
                </table>
            </td>
           <td width="10%" class="optionTitleBoldRight" nowrap>
	            <fmt:message key="label.search"/>:&nbsp;
	        </td>
	        <td width="50%" class="optionTitleBoldLeft" nowrap colspan="2">
	        	<select name="searchField" id="searchField" class="selectBox" onchange="showOrHideMsdsNoSpan();">
     <%--          	 <option value=""><fmt:message key="label.all"/></option>   --%> 
                	 <option value="item_id"><fmt:message key="label.itemid"/></option>
                	 <option value="material_id"><fmt:message key="label.materialid"/></option>
                	 <option value="material_desc" selected><fmt:message key="label.materialdesc"/></option>
                	 <option value="cust_msds_no"><fmt:message key="label.msds"/></option>
                	 <option value="cat_part_no"><fmt:message key="label.partnumber"/></option>
                	 <%--<option value="product_code"><fmt:message key="report.label.productCode"/></option> --%>
                	 <option value="Synonym"><fmt:message key="label.synonym"/></option>
                	 <option value="trade_name"><fmt:message key="label.tradename"/></option>
                </select>
  <%--            &nbsp;
		        <select name="searchMode" class="selectBox" id="searchMode" >
		               <option value="is"> <fmt:message key="label.is"/>  </option>
		               <option value="contains" selected> <fmt:message key="label.contains"/>  </option>
		               <option value="startsWith"><fmt:message key="label.startswith"/> </option>
		               <option value="endsWith"><fmt:message key="label.endswith" /></option>
		        </select>  --%>   
		        &nbsp;         
	            <input name="searchText" id="searchText" type="text" class="inputBox" onkeypress="onKeySearch(event,submitSearchForm);" size="45" value="">&nbsp;
	            <span id="msdsNoSpan" style="display:none;font-size:160%;vertical-align:bottom;color:blue;"  onclick="showTooltip('hiddenDesc', 'searchText', -50, 23);"> 
					*
	   			</span> &nbsp;&nbsp;&nbsp;
	        </td>
        </tr>
        <tr>
            <td width="10%" class="optionTitleBoldRight" nowrap>
                <fmt:message key="label.manufacturer"/>:
            </td>

            <td width="30%" class="optionTitleBoldLeft" nowrap>
				<input class="inputBox" type="hidden" name="mfgId" id="mfgId" value="" >
				<input class="invGreyInputText" type="text" name="manufacturer" id="manufacturer" value="" size="40" readonly>
				<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="selectedManufacturer" id="selectedManufacturer" value="..." align="right" onClick="lookupManufacturer();"/>
				<button class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'"
                 	name="None" value=""  OnClick="clearManufacturer();return false;"><fmt:message key="label.clear"/> </button>
            </td>
            <td width="10%" class="optionTitleBoldRight" nowrap>
                &nbsp;
            </td>
            <!-- search by -->
            <td width="10%" class="optionTitleBoldLeft"  >
            	<input name="fullDatabase" id="fullDatabase" type="checkbox" class="radioBtns" value="Y" >&nbsp;
                <fmt:message key="label.fulldatabase"/>
            </td>
	        <td width="10%" class="optionTitleBoldLeft" nowrap>
	            <fmt:message key="label.physicalstate"/>:&nbsp;
	        	<select name="physicalState" id="physicalState" class="selectBox">
                	 <option value=""><fmt:message key="label.all"/></option>
                     <c:forEach var="physicalStateBean" items="${physicalStateColl}" varStatus="status">
                            <option value="${physicalStateBean.physicalState}"><c:out value="${physicalStateBean.physicalStateDesc}"/></option>
                     </c:forEach>
                </select>
	        </td>
        </tr>
		
		<tr>
			<td width="10%" class="optionTitleBoldRight"nowrap>
                <table width="100%" class="tableSearch"  border="0" cellpadding="0" cellspacing="0">
						<tr>
							<TD width="40%" class="optionTitleBoldRight">
								<fmt:message key="label.ph"/>&nbsp;:
							</TD>
						</tr>
						<tr>
							<TD width="40%" class="optionTitleBoldRight">
								<fmt:message key="label.flashpoint"/>&nbsp;:
							</TD>
						</tr>
						<tr>
							<TD width="40%" class="optionTitleBoldRight">
								<fmt:message key="label.vaporpressure"/>&nbsp;:
							</TD>
						</tr>
						<tr>
							<TD width="40%" class="optionTitleBoldRight">
								%<fmt:message key="label.voc"/>&nbsp;:
							</TD>
						</tr>
						<tr>
							<TD width="40%" class="optionTitleBoldRight">
								%<fmt:message key="label.solids"/>&nbsp;:
							</TD>
						</tr>
					</table>
            </td>
            
            <td width="10%" class="optionTitleBoldLeft"nowrap>
                <table width="100%" class="tableSearch"  border="0" cellpadding="0" cellspacing="0">
						<tr>
							<TD width="40%" class="optionTitleBoldLeft">
								<select name="phCompare" id="phCompare" class="selectBox">    			
									<option value=">">></option>
									<option value=">=">>=</option>
									<option value="<="><=</option>
									<option value="<"><</option>
								</select>
								<input class="inputBox" type="text" name="ph" id="ph" size="5" maxlength="10" value="">
							</TD>
						</tr>
						<tr>
							<TD width="40%" class="optionTitleBoldLeft">
								<select name="flashPointCompare" id="flashPointCompare" class="selectBox"> 
									<option value="<"><</option>   			
									<option value=">">></option>
									<option value=">=">>=</option>
									<option value="<="><=</option>
								</select>
								<input class="inputBox" type="text" name="flashPoint" id="flashPoint" size="5" maxlength="10" value="">
								<select name="temperatureUnit" id="temperatureUnit" class="selectBox">    			
									<option value="C">C</option>
									<option value="F" selected>F</option>
								</select>
							</TD>
						</tr>
						<tr>
							<TD width="40%" class="optionTitleBoldLeft">
								<select name="vaporPressureCompare" id="vaporPressureCompare" class="selectBox">    			
									<option value=">">></option>
									<option value=">=">>=</option>
									<option value="<="><=</option>
									<option value="<"><</option>
								</select>
								<input class="inputBox" type="text" name="vaporPressure" id="vaporPressure" size="5" maxlength="10" value="">
								<select name="vaporPressureUnit" id="vaporPressureUnit" class="selectBox">    			
									<option value=""><fmt:message key="label.pleaseselect"/></option>
                     				<c:forEach var="vaporPressureUnitBean" items="${vaporPressureUnitColl}" varStatus="status">
										<option value="${vaporPressureUnitBean.vaporPressureUnit}">${vaporPressureUnitBean.vaporPressureUnit}</option>
									</c:forEach>
								</select>
							</TD>
						</tr>
						<tr>
							<TD width="40%" class="optionTitleBoldLeft">
								<select name="vocPercentCompare" id="vocPercentCompare" class="selectBox">    			
									<option value=">">></option>
									<option value=">=">>=</option>
									<option value="<="><=</option>
									<option value="<"><</option>
								</select>
								<input class="inputBox" type="text" name="vocPercent" id="vocPercent" size="5" maxlength="10" value="">
							</TD>
						</tr>
						<tr>
							<TD width="40%" class="optionTitleBoldLeft">
								<select name="solidsPercentCompare" id="solidsPercentCompare" class="selectBox">    			
									<option value=">">></option>
									<option value=">=">>=</option>
									<option value="<="><=</option>
									<option value="<"><</option>
								</select>
								<input class="inputBox" type="text" name="solidsPercent" id="solidsPercent" size="5" maxlength="10" value="">
							</TD>
						</tr>
					</table>
            </td>
            
            <td width="10%" class="optionTitleBoldRight" nowrap>
                &nbsp;
            </td>

			<td width="25%" class="optionTitleLeft" valign="top">
				<FIELDSET>
					<LEGEND class="optionTitleBold"><fmt:message key="label.nfpa"/></LEGEND>
					<table width="100%" class="tableSearch" id="msdsDataTable" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<TD width="40%" class="optionTitleBoldRight">
								<fmt:message key="label.health"/>&nbsp;:
							</TD>
							<TD width="60%" class="optionTitleLeft" >
								<select name="healthCompare" id="healthCompare" class="selectBox"> 
									<option value="=">=</option>   			
									<option value=">">></option>
									<option value=">=">>=</option>
									<option value="<="><=</option>
									<option value="<"><</option>
								</select>
								<input class="inputBox" type="text" name="health" id="health" size="5" maxlength="10" value="">
							</TD>
						</tr>
						<tr>
							<TD width="40%" class="optionTitleBoldRight">
								<fmt:message key="label.flammability"/>&nbsp;:
							</TD>
							<TD width="60%" class="optionTitleLeft" >
								<select name="flammabilityCompare" id="flammabilityCompare" class="selectBox">    			
									<option value="=">=</option> 
									<option value=">">></option>
									<option value=">=">>=</option>
									<option value="<="><=</option>
									<option value="<"><</option>
								</select>
								<input class="inputBox" type="text" name="flammability" id="flammability" size="5" maxlength="10" value="">
							</TD>
						</tr>
						<tr>
							<TD width="40%" class="optionTitleBoldRight">
								<fmt:message key="label.reactivity"/>&nbsp;:
							</TD>
							<TD width="60%" class="optionTitleLeft" >
								<select name="reactivityCompare" id="reactivityCompare" class="selectBox">    			
									<option value="=">=</option> 
									<option value=">">></option>
									<option value=">=">>=</option>
									<option value="<="><=</option>
									<option value="<"><</option>
								</select>
								<input class="inputBox" type="text" name="reactivity" id="reactivity" size="5" maxlength="10" value="">
							</TD>
						</tr>
						<tr>
							<TD width="40%" class="optionTitleBoldRight">
								<fmt:message key="label.hazard"/>&nbsp;:
							</TD>
							<TD width="60%" class="optionTitleLeft" >
								<select name="specificHazard" id="specificHazard" class="selectBox">    			
									<option value=""><fmt:message key="label.all"/></option>
                     				<c:forEach var="specificHazardBean" items="${specificHazardColl}" varStatus="status">
										<option value="${specificHazardBean.specificHazard}">${specificHazardBean.specificHazardDescription}</option>
									</c:forEach>
								</select>
							</TD>
						</tr>
					</table>
				</FIELDSET>
			</td>
			<td width="25%" class="optionTitleLeft" valign="top">
				<FIELDSET>
					<LEGEND class="optionTitleBold"><fmt:message key="label.hmis"/></LEGEND>
					<table width="100%" class="tableSearch"  border="0" cellpadding="0" cellspacing="0">
						<tr>
							<TD width="40%" class="optionTitleBoldRight">
								<fmt:message key="label.health"/>&nbsp;:
							</TD>
							<TD width="60%" class="optionTitleLeft" >
								<select name="hmisHealthCompare" id="hmisHealthCompare" class="selectBox">    			
									<option value="=">=</option> 
									<option value=">">></option>
									<option value=">=">>=</option>
									<option value="<="><=</option>
									<option value="<"><</option>
								</select>
								<input class="inputBox" type="text" name="hmisHealth" id="hmisHealth" size="5" maxlength="10" value="">
							</TD>
						</tr>
						<tr>
							<TD width="40%" class="optionTitleBoldRight">
								<fmt:message key="label.flammability"/>&nbsp;:
							</TD>
							<TD width="60%" class="optionTitleLeft" >
								<select name="hmisFlammabilityCompare" id="hmisFlammabilityCompare" class="selectBox">    			
									<option value="=">=</option> 
									<option value=">">></option>
									<option value=">=">>=</option>
									<option value="<="><=</option>
									<option value="<"><</option>
								</select>
								<input class="inputBox" type="text" name="hmisFlammability" id="hmisFlammability" size="5" maxlength="10" value="">
							</TD>
						</tr>
						<tr>
							<TD width="40%" class="optionTitleBoldRight">
								<fmt:message key="label.reactivity"/>&nbsp;:
							</TD>
							<TD width="60%" class="optionTitleLeft" >
								<select name="hmisReactivityCompare" id="hmisReactivityCompare" class="selectBox">    			
									<option value="=">=</option> 
									<option value=">">></option>
									<option value=">=">>=</option>
									<option value="<="><=</option>
									<option value="<"><</option>
								</select>
								<input class="inputBox" type="text" name="hmisReactivity" id="hmisReactivity" size="5" maxlength="10" value="">
							</TD>
						</tr>
						<tr>
							<TD width="40%" class="optionTitleBoldRight">
								<fmt:message key="label.personalProtection"/>&nbsp;:
							</TD>
							<TD width="60%" class="optionTitleLeft" >
								<select name="personalProtection" id="personalProtection" class="selectBox">    			
					                <option value=""><fmt:message key="label.all"/></option>
					                <c:forEach var="ppBean" items="${ppColl}" varStatus="status">
					                      <option value="${ppBean.personalProtectCode}"><c:out value="${ppBean.personalProtectDesc}"/></option>
					                </c:forEach>
								</select>
							</TD>
						</tr>
					</table>
				</FIELDSET>
			</td>
		</tr>
<%-- 
		<tr>
            <td width="10%" class="optionTitleBoldRight">
                 <input name="fullDatabase" id="fullDatabase" type="checkbox" class="radioBtns" value="Y" >
            </td>
            <td width="10%" class="optionTitleBoldLeft" colspan="4" >
                <fmt:message key="label.fulldatabase"/>
            </td>
        </tr>
--%>		
		<tr>  
			<td>&nbsp;
			</td>
         	<td class="optionTitleBoldLeft" colspan="4" nowrap>
         		<input name="listOrCasNos" id="lists" type="radio" class="radioBtns" value="list" onclick="showListOrCasNos();" checked>&nbsp; 
            	<fmt:message key="label.list"/>&nbsp;
            	<button id="clearBtn" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'"
                 	name="None" value=""  OnClick="clearList();return false;"><fmt:message key="label.clear"/> </button>&nbsp;&nbsp; &nbsp; 
            	<input name="listOrCasNos" id="casNos" type="radio" class="radioBtns" value="casNos" onclick="showListOrCasNos();" >&nbsp; 
            	<fmt:message key="label.casnumber"/>&nbsp;
            	<span id="casDropDown" style="display:none;">
            	(
            	<select name="casNumberAndOr" id="casNumberAndOr" onchange="setAndOr();"  class="selectBox">
					<option value="and" selected><fmt:message key="label.and"/></option>
				    <option value="or"><fmt:message key="label.or"/></option>
				</select>
            	)&nbsp; 
            	<span class="boxhead"><a href="#" onclick="addMoreCasNo();"><fmt:message key="label.addanothercasnumber" /></a></span>
            	</span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; 
            	<input name="kitMsdsOnly" id="kitMsdsOnly" type="checkbox" class="radioBtns" value="Y" >&nbsp;<fmt:message key="label.showonlykitmsds"/>
            </td>
        </tr>
             
        <tr>
        	<td>&nbsp;
			</td>
            <td id="listCol" width="10%" class="optionTitleBoldLeft" colspan="4" nowrap>
              <table id="listTable">
              <tr>
              <td>
				<select name="list" id="list" class="selectBox" multiple size="6">
                     <c:forEach var="listBean" items="${listColl}" varStatus="status">
                            <option value="${listBean.listId}"><c:out value="${listBean.listName}"/></option>
                     </c:forEach>
                </select>
              </td>
              <td>
                <table id="listTable2" width="100%" class="tableSearch"  border="0" cellpadding="0" cellspacing="0">
	        	<%--		
	        			<tr><td>&nbsp;</td>
	        				<td align="center">
	        					<fmt:message key="label.threshold"/>: 
	        				</td>
	        				<td align="center">
	        					<fmt:message key="label.limit"/>:
	        				</td>
	        				<td>
	        					&nbsp;
	        				</td>
	        			</tr>
	        	 --%>
	        			<tr>
	        	<%--		
	        				<td>&nbsp;</td>
	        				<td align="center">
	        					<input name="threshold" id="threshold" type="checkbox" class="radioBtns" value="" >
	        				</td>
	        				<td align="center">
	        					<input name="limit" id="limit" type="checkbox" class="radioBtns" value="" >
	        				</td>
	        	 --%>
	        				<td>&nbsp;&nbsp;&nbsp;
	        					<select name="listPercent" id="listPercent" class="selectBox">    			
									<option value="percent_upper"><fmt:message key="label.max"/></option>
									<option value="percent_lower"><fmt:message key="label.min"/></option>
									<option value="percent"><fmt:message key="label.average"/></option>
								</select>&nbsp;&nbsp;
	        					<select name="listOperator" id="listOperator" onchange="disableInputBox('listOperator','listArgument');" class="selectBox">    			
									<option value="=" selected>=</option> 
									<option value=">">></option>
									<option value=">=" selected>>=</option>
									<option value="<="><=</option>
									<option value="<"><</option>
									<option value="is null">null</option>
								</select>&nbsp;&nbsp;
								<input type="text" class="inputBox"  name="listArgument" id="listArgument" size="5"/>
								<input type="hidden" name="listString" id="listString" value=""/>
	        				</td>
	        			</tr>
	        	</table>
              </td>
              </tr>
              </table>
            </td>
       
            <td id="casNosCol" width="10%" class="optionTitleBoldLeft" colspan="4" nowrap style="display:none;">
              <table>
              <tr><td>
	        	<div style="width: 520px; height: 100px; overflow: auto">
	        		<table id="casNumberTable" width="100%" class="tableSearch"  border="0" cellpadding="0" cellspacing="0">
	        			<tr><td>&nbsp;</td>
	        				<td>
	        					<fmt:message key="label.casnumber"/> 1: 
	        				</td>
	        				<td>
	        					<input class="inputBox" type="text" name="casNumber1" id="casNumber1" value="" maxlength="12" size="12"/>
	        				</td>
	        				<td>	
	        					<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="selectedCas" id="selectedCas" value="..." align="right" onClick="lookupCasNumber('1');"/>
	        				</td>
	        				<td>
	        					<select name="casNumberPercent1" id="casNumberPercent1" class="selectBox">    			
									<option value="percent_upper"><fmt:message key="label.max"/></option>
									<option value="percent_lower"><fmt:message key="label.min"/></option>
									<option value="percent"><fmt:message key="label.average"/></option>
								</select>
							</td>
							<td>
	        					<select name="casNumberOperator1" id="casNumberOperator1" onchange="disableInputBox('casNumberOperator1','casNumberArgument1');"  class="selectBox">    			
									<option value=">=" selected>>=</option> 
									<option value=">">></option>
									<option value="=">=</option>
									<option value="<="><=</option>
									<option value="<"><</option>
									<option value="is null">null</option>
								</select>
							</td>
							<td>
								<input type="text" class="inputBox"  name="casNumberArgument1" id="casNumberArgument1" size="5"/>
	        				</td>
	        			</tr>
	        			<tr><td id="casNumberAndOr2">
				                <fmt:message key="label.and"/>
	        				</td>
	        				<td>
	        					<fmt:message key="label.casnumber"/> 2: 
	        				</td>
	        				<td>
	        					<input class="inputBox" type="text" name="casNumber2" id="casNumber2" value="" maxlength="12" size="12"/>
	        				</td>
	        				<td>	
	        					<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="selectedCas" id="selectedCas" value="..." align="right" onClick="lookupCasNumber('2');"/>
	        				</td>
	        				<td>
	        					<select name="casNumberPercent2" id="casNumberPercent2" class="selectBox">    			
									<option value="percent_upper"><fmt:message key="label.max"/></option>
									<option value="percent_lower"><fmt:message key="label.min"/></option>
									<option value="percent"><fmt:message key="label.average"/></option>
								</select>
							</td>
							<td>
	        					<select name="casNumberOperator2" id="casNumberOperator2" onchange="disableInputBox('casNumberOperator2','casNumberArgument2');" class="selectBox">    			
									<option value=">=" selected>>=</option> 
									<option value=">">></option>
									<option value="=">=</option>
									<option value="<="><=</option>
									<option value="<"><</option>
									<option value="is null">null</option>
								</select>
							</td>
							<td>
								<input type="text" class="inputBox"  name="casNumberArgument2" id="casNumberArgument2" size="5"/>
	        				</td>
	        			</tr>
	        			<tr><td id="casNumberAndOr3">
				                <fmt:message key="label.and"/>
	        				</td>
	        				<td>
	        					<fmt:message key="label.casnumber"/> 3: 
	        				</td>
	        				<td>
	        					<input class="inputBox" type="text" name="casNumber3" id="casNumber3" value="" maxlength="12" size="12"/>
	        				</td>
	        				<td>	
	        					<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="selectedCas" id="selectedCas" value="..." align="right" onClick="lookupCasNumber('3');"/>
	        				</td>
	        				<td>
	        					<select name="casNumberPercent3" id="casNumberPercent3" class="selectBox">    			
									<option value="percent_upper"><fmt:message key="label.max"/></option>
									<option value="percent_lower"><fmt:message key="label.min"/></option>
									<option value="percent"><fmt:message key="label.average"/></option>
								</select>
							</td>
							<td>
	        					<select name="casNumberOperator3" id="casNumberOperator3" onchange="disableInputBox('casNumberOperator3','casNumberArgument3');" class="selectBox">    			
									<option value=">=" selected>>=</option> 
									<option value=">">></option>
									<option value="=">=</option>
									<option value="<="><=</option>
									<option value="<"><</option>
									<option value="is null">null</option>
								</select>
							</td>
							<td>
								<input type="text" class="inputBox"  name="casNumberArgument3" id="casNumberArgument3" size="5"/>
	        				</td>
	        			</tr>
	        			<tr><td id="casNumberAndOr4">
				                <fmt:message key="label.and"/>
	        				</td>
	        				<td>
	        					<fmt:message key="label.casnumber"/> 4: 
	        				</td>
	        				<td>
	        					<input class="inputBox" type="text" name="casNumber4" id="casNumber4" value="" maxlength="12" size="12"/>
	        				</td>
	        				<td>	
	        					<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="selectedCas" id="selectedCas" value="..." align="right" onClick="lookupCasNumber('4');"/>
	        				</td>
	        				<td>
	        					<select name="casNumberPercent4" id="casNumberPercent4" class="selectBox">    			
									<option value="percent_upper"><fmt:message key="label.max"/></option>
									<option value="percent_lower"><fmt:message key="label.min"/></option>
									<option value="percent"><fmt:message key="label.average"/></option>
								</select>
							</td>
							<td>
	        					<select name="casNumberOperator4" id="casNumberOperator4" onchange="disableInputBox('casNumberOperator4','casNumberArgument4');" class="selectBox">    			
									<option value=">=" selected>>=</option> 
									<option value=">">></option>
									<option value="=">=</option>
									<option value="<="><=</option>
									<option value="<"><</option>
									<option value="is null">null</option>
								</select>
							</td>
							<td>
								<input type="text" class="inputBox"  name="casNumberArgument4" id="casNumberArgument4" size="5"/>
	        				</td>
	        			</tr>
	        		</table>
	        	</div>
	        </td>
	        </tr>
	        </table>
	        </td>
        </tr>
    </table>

   <!-- End search options -->
                    </div>
                    <div class="roundbottom">
                        <div class="roundbottomright"><img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none"/></div>
                    </div>
                </div>
            </div>
        </td>
    </tr>
</table>
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
    <input name="uAction" id="uAction" type="hidden" value="">
    <input name="startSearchTime" id="startSearchTime" type="hidden" value="">
    <input name="hideOrShowDiv" id="hideOrShowDiv" type="hidden" value="hide">
    <input name="selectedCasId" id="selectedCasId" type="hidden" value=""> 
    <input name="casNumberAndOrString" id="casNumberAndOrString" type="hidden" value="">
    <input name="casNumberString" id="casNumberString" type="hidden" value="">
    <input name="applicationList" id="applicationList" type="hidden" value="">
    <input name="oldApplicationList" id="oldApplicationList" type="hidden" value="">
    <input name="defaultFacilityId" id="defaultFacilityId" type="hidden" value="${personnelBean.myDefaultFacilityId}">
    <input name="searchHeight" id="searchHeight" type="hidden" value="">
</div>
<!-- Hidden elements end -->
</tcmis:form>
</div>
<!-- close of contentArea -->
</div>
<!-- Search Frame Ends -->

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
<!-- Transit Page Begins -->
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<br/>
<!-- Transit Page Ends -->
<div id="resultGridDiv" style="display: none;"> 
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr> 
    <td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <%-- boxhead Begins --%>
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
      --%>
     <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
     </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

    <div class="dataContent"> 
     <iframe  scrolling="no"  id="resultFrame" name="resultFrame" width="100%" height="" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
   </div>
   <%-- result count and time --%>
   <div id="footer" class="messageBar"></div>
  </div>

  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</td></tr>
</table>
</div>  
</div><!-- Result Frame Ends -->

</div> <!-- close of interface -->
    <!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a yui pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">
</div>

<div id="hiddenDesc" style="visibility: hidden;position: absolute;z-index: 2;font:8pt sans-serif;border: solid 1px grey;background-color:white;">
<TEXTAREA id="msdsNos" name="msdsNos" rows="6" cols="35" style="background-color: #ffffff;border: 1px solid #cccccc;"></TEXTAREA><br/><br/>
&nbsp;&nbsp;
<select name="matchType" id="matchType" class="selectBox">
    <option value="all"><fmt:message key="label.matchall"/></option>
    <option value="any" selected ><fmt:message key="label.matchany"/></option>
</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input name="okInput" id="okInput" type="button" value="<fmt:message key="label.ok"/>" onclick="okInput();" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="#" onclick="hideTooltip('hiddenDesc');"><fmt:message key="label.cancel" /></a>
</div> 

</body>
</html:html>