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
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"/>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss />

<%@ include file="/common/locale.jsp" %>
<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/client/catalog/catalogaddhmrb.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<title>
	<fmt:message key="label.addeditapprovalcode"/>
</title>

</head>


<body bgcolor="#ffffff" onLoad="editOnLoad('${param.uAction}');closeAllchildren();" onunload="checkClose('${param.uAction}');">

<script language="JavaScript" type="text/javascript">
<!--

var altUsageCategory = new Array(
<c:forEach var="usageCategorySubcatViewBean" items="${hmrbData.usageCategoryColl}" varStatus="status1">
   <c:if test="${status1.index > 0}">,</c:if>
   {
		usageCategoryId:'<tcmis:jsReplace value="${usageCategorySubcatViewBean.usageCategoryId}"/>',
		usageCategoryName:'<tcmis:jsReplace value="${usageCategorySubcatViewBean.usageCategoryName}"/>',
		showProgram:'${usageCategorySubcatViewBean.showProgram}'
	}
</c:forEach>
);

var altUsageSubCategory = new Array();
<c:forEach var="usageCategorySubcatViewBean" items="${hmrbData.usageCategoryColl}" varStatus="status">
    altUsageSubCategory['${usageCategorySubcatViewBean.usageCategoryId}'] = new Array(
        <c:forEach var="usageSubcatViewBean" items="${usageCategorySubcatViewBean.usageSubCategoryColl}" varStatus="status1">
           <c:if test="${status1.index > 0}">,</c:if>
           {
                usageSubcategoryId:'${usageSubcatViewBean.usageSubcategoryId}',
                usageSubcategoryName:'<tcmis:jsReplace value="${usageSubcatViewBean.usageSubcategoryName}"/>',
                production:'${usageSubcatViewBean.production}',
                approvalCodeId:'${usageSubcatViewBean.approvalCodeId}',
                approvalCodeName:'${usageSubcatViewBean.approvalCodeName}',
                approvalExpires:'${usageSubcatViewBean.approvalExpires}',
                includesSpecialCoatings:'${usageSubcatViewBean.includesSpecialCoatings}',
                approvalUseGroupId:'${usageSubcatViewBean.applicationUseGroupId}'
           }
        </c:forEach>
    );
</c:forEach>

var altMaterialCategory = new Array(
<c:forEach var="materialCategorySubcatViewBean" items="${hmrbData.materialCategoryColl}" varStatus="status1">
   <c:if test="${status1.index > 0}">,</c:if>
   {
		materialCategoryId:'<tcmis:jsReplace value="${materialCategorySubcatViewBean.materialCategoryId}"/>',
		materialCategoryName:'<tcmis:jsReplace value="${materialCategorySubcatViewBean.materialCategoryName}"/>',
        showForProd:'${materialCategorySubcatViewBean.showForProd}',
        showForNonProd:'${materialCategorySubcatViewBean.showForNonProd}'
    }
</c:forEach>
);

var altMaterialSubCategory = new Array();
<c:forEach var="materialCategorySubcatViewBean" items="${hmrbData.materialCategoryColl}" varStatus="status">
    altMaterialSubCategory['${materialCategorySubcatViewBean.materialCategoryId}'] = new Array(
        <c:forEach var="materialSubcatViewBean" items="${materialCategorySubcatViewBean.materialSubCategoryColl}" varStatus="status1">
           <c:if test="${status1.index > 0}">,</c:if>
           {
                materialSubcategoryId:'${materialSubcatViewBean.materialSubcategoryId}',
                materialSubcategoryName:'<tcmis:jsReplace value="${materialSubcatViewBean.materialSubcategoryName}"/>',
                coatCategory:'${materialSubcatViewBean.coatCategory}',
                showForProd:'${materialSubcatViewBean.showForProd}',
                showForNonProd:'${materialSubcatViewBean.showForNonProd}',
                toVocet:'${materialSubcatViewBean.toVocet}',
                specialtyCoating:'${materialSubcatViewBean.specialtyCoating}',
                hideQtyPerShift:'${materialSubcatViewBean.hideQtyPerShift}',
                qtyPerShiftOptional:'${materialSubcatViewBean.qtyPerShiftOptional}',
                hideAnnualUsage:'${materialSubcatViewBean.hideAnnualUsage}',
                annualUsageOptional:'${materialSubcatViewBean.annualUsageOptional}',
                hideMultiComponent:'${materialSubcatViewBean.hideMultiComponent}',
                hideProcessInfo:'${materialSubcatViewBean.hideProcessInfo}',
                hideFtwSpecific:'${materialSubcatViewBean.hideFtwSpecific}'
           }
        </c:forEach>
    );
</c:forEach>

var altBooth = new Array(
<c:forEach var="boothBean" items="${hmrbData.boothColl}" varStatus="status1">
   <c:if test="${status1.index > 0}">,</c:if>
   {
		boothId:'<tcmis:jsReplace value="${boothBean.boothId}"/>',
		boothName:'<tcmis:jsReplace value="${boothBean.boothName}"/>',
        production:'${boothBean.production}',
        nonProduction:'${boothBean.nonProduction}',
        boothType:'${boothBean.boothType}'
    }
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
pleasewait:"<fmt:message key="label.pleasewait"/>",
pleasewaitmenu:"<fmt:message key="label.pleasewaitmenu"/>",
all:"<fmt:message key="label.all"/>",
maxQtyUsePerShift:"<fmt:message key="label.maxqtyusepershift"/>",
matlFlyAwayWithAircraft:"<fmt:message key="label.matlflyawaywithaircraft"/>",
building:"<fmt:message key="label.building"/>",
processLocation:"<fmt:message key="label.processlocation"/>",
selectOne:"<fmt:message key="label.selectOne"/>",
usageCategory:"<fmt:message key="label.usagecategory"/>",
usageSubcategory:"<fmt:message key="label.usagesubcategory"/>",
materialCategory:"<fmt:message key="label.materialcategory"/>",
materialSubcategory:"<fmt:message key="label.materialsubcategory"/>",
purchasing:"<fmt:message key="label.purchasing"/>",
useProcesses:"<fmt:message key="label.useprocesses"/>",
substrateAppliedOn:"<fmt:message key="label.substrateappliedon"/>",    
saveNewAndReturn:"<fmt:message key="label.savenewandreturn"/>",
updateAndReturn:"<fmt:message key="label.updateandreturn"/>",
cancel:"<fmt:message key="label.cancel"/>",
gt54Gal:"<fmt:message key="label.largestcontainersizeonhandgt54gal"/>",
intendedProductFormulation:"<fmt:message key="label.intendedforproductformulation"/>",
withMsds:"<fmt:message key="label.withmsds"/>",
partsMaterialTo:"<fmt:message key="label.partsmaterial"/>",
partsThinned:"<fmt:message key="label.partsthinnerto"/>",
by:"<fmt:message key="label.lowercaseby"/>",
otherProccessLocation:"<fmt:message key="error.otherproccesslocation"/>",
missingAdditionalDescription:"<fmt:message key="label.missingadditionaldescription"/>",
foreignSupplier:"<fmt:message key="label.foreignsupplier"/>",
program:"<fmt:message key="label.programname"/>",
estimatedAnnualUsage:"<fmt:message key="label.estimateannualusage"/>",
estimatedAnnualUsageUnit:"<fmt:message key="label.estimateannualusageunit"/>",
dischargeSinkDrain:"<fmt:message key="label.dischargesinkdrain"/>",
yes:"<fmt:message key="label.yes"/>",
thinnerRatioNumber:"<fmt:message key="label.thinnerrationumber"/>",
materialRatioNumber:"<fmt:message key="label.materialrationumber"/>",
thinningRatioUnit:"<fmt:message key="label.thinningratiounit"/>",
itemInteger:"<fmt:message key="error.item.integer"/>"};
// -->
 </script>


<tcmis:form action="/catalogaddrequest.do" onsubmit="" target="_self">

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
				<td>
                    <span id="actionSpan" style="display: none"></span>
				</td>
			 </tr>
   </table>

<div>

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
				<span id="headerSpan" style="display: none">
                    <fieldset>
					    <legend><fmt:message key="label.generalinformation"/></legend>
                        <table class="tableSearch" border="0" cellpadding="0" cellspacing="0">
						 <tr>
							 <td class="optionTitleBoldRight" nowrap="true">
								 <fmt:message key="label.usagecategory"/>:*
							 </td>
							 <td class="optionTitleBoldLeft">
								 <select name="usageCategoryId" id="usageCategoryId" class="selectBox" onchange="usageCategoryChanged()">
				                 </select>
							 </td>
                             <td class="optionTitleBoldRight" nowrap="true">
								 <fmt:message key="label.usagesubcategory"/>:*
							 </td>
							 <td class="optionTitleBoldLeft">
								 <select name="usageSubcategoryId" id="usageSubcategoryId" class="selectBox" onchange="usageSubcategoryChanged()">
				                 </select>
							 </td>
                         </tr>
						 <tr>
                            <td class="optionTitleBoldRight" nowrap="true">
								 <fmt:message key="label.materialcategory"/>:*
							 </td>
							 <td class="optionTitleBoldLeft">
								 <select name="materialCategoryId" id="materialCategoryId" class="selectBox" onchange="materialCategoryChanged()">
				                 </select>
							 </td>
                             <td class="optionTitleBoldRight" nowrap="true">
								 <fmt:message key="label.materialsubcategory"/>:*
							 </td>
							 <td class="optionTitleBoldLeft">
								 <select name="materialSubcategoryId" id="materialSubcategoryId" class="selectBox" onchange="materialSubcategoryChanged()">
				                 </select>
							 </td>
                         </tr>
                         <tr>
                            <td class="optionTitleBoldRight">
                                <fmt:message key="label.begindate"/>:&nbsp;
                            </td>
                            <td class="optionTitleBoldLeft" colspan="3" nowrap>
                            <c:set var="beginDateFmt"><fmt:formatDate value='${hmrbData.beginDateJsp}' pattern='${dateFormatPattern}'/></c:set>
                            <c:set var="endDateFmt"><fmt:formatDate value='${hmrbData.endDateJsp}' pattern='${dateFormatPattern}'/></c:set>
                                <html:text size="10" property="beginDateJsp" styleClass="inputBox" styleId="beginDateJsp" value="${beginDateFmt}"
                                    onclick="return getCalendar(document.genericForm.beginDateJsp,null,null,null,document.genericForm.endDateJsp);"
                                    onfocus="blur();"/> <a href="javascript: void(0);" ID="linkbeginDateJsp" STYLE="color:#0000ff;cursor:pointer;text-decoration:underline"></a>
                                &nbsp;&nbsp;
                                <fmt:message key="label.enddate"/>:&nbsp;
                                <html:text size="10" property="endDateJsp" styleClass="inputBox" styleId="endDateJsp" value="${endDateFmt}"
                                    onclick="return getCalendar(document.genericForm.endDateJsp,null,null,document.genericForm.beginDateJsp);"
                                    onfocus="blur();"/><a href="javascript: void(0);" ID="linkendDateJsp" STYLE="color:#0000ff;cursor:pointer;text-decoration:underline"></a>&nbsp;&nbsp;
                                 <span id="programDiv" style="visibility:hidden;">
	                                 <fmt:message key="label.programname"/>:*&nbsp;
	                                  <c:set var="programPreSel" value='${hmrbData.programId}'/>
	                                  <c:if test="${programPreSel == ''}">
	                                  	   <c:set var="programPreSel" value='None'/>
	                                  </c:if>              
	                             	 <select class="selectBox" name="programId" id="programId">
		                             	   <option value=""><fmt:message key="label.selectOne"/></option>
		                             	   	<c:forEach var="programBean" items="${hmrbData.programColl}" varStatus="programStatus">
		                             	   	 	<option value="${programBean.programId}" <c:if test="${programPreSel == programBean.programId}">selected</c:if> >${programBean.programName}</option>
											</c:forEach>
		                             </select>
		                             &nbsp;&nbsp;
	                             </span>
                                 <fmt:message key="label.matfromforeign"/>:*&nbsp;
                             	  <select class="selectBox" name="importFlag" id="importFlag">
                             	      <option value=""><fmt:message key="label.selectOne"/></option>
                             	      <c:set var="tmpVal" value='${hmrbData.importFlag}'/>
	                             	  <option value="Y" <c:if test="${tmpVal == 'Y'}">selected</c:if> ><fmt:message key="label.yes"/></option>
	                             	  <option value="N" <c:if test="${tmpVal == 'N'}">selected</c:if> ><fmt:message key="label.no"/></option>
	                             	  <option value="U" <c:if test="${tmpVal == 'U'}">selected</c:if> ><fmt:message key="label.unknown"/></option>
	                             </select>                          
                             </td>
                         </tr>

                        <c:set var="purchasingMethodCount" value='0'/>
                        <tr>
                            <td class="optionTitleBoldRight">
                                <fmt:message key="label.purchasing"/>:*
                            </td>
                            <td class="optionTitleBoldLeft" colspan="3">
                                <c:forEach var="purchasingMethodBean" items="${hmrbData.purchasingMethodColl}" varStatus="status">
                                    <input type="checkbox" class="radioBtns" name="purchasingMethod${purchasingMethodCount}" id="purchasingMethod${purchasingMethodCount}" value="${purchasingMethodBean.purchasingMethodId}">
                                        ${purchasingMethodBean.purchasingMethodName}
                                    <c:set var="purchasingMethodCount" value='${purchasingMethodCount+1}'/>
                                </c:forEach>
                            </td>
                        </tr>       
                        <tr id="maxQtyOnHandSpan" style="display: none">
                            <td class="optionTitleBoldLeft" colspan="4" nowrap="true">
                                <fmt:message key="label.largestcontainersizeonhandgt54gal"/>?:*&nbsp;
                                <c:set var="selectedVal" value='${hmrbData.gt54Gal}'/>
                                <c:set var="selectedYes" value=''/>
                                <c:set var="selectedNo" value=''/>
                                <c:if test="${selectedVal == 'Y'}">
                                    <c:set var="selectedYes" value='selected'/>
                                    <c:set var="selectedNo" value=''/>
                                </c:if>
                                <c:if test="${selectedVal == 'N'}">
                                    <c:set var="selectedYes" value=''/>
                                    <c:set var="selectedNo" value='selected'/>
                                </c:if>
                                <select name="gt54Gal" id="gt54Gal" class="selectBox">
                                    <option value=""> </option>
                                    <option value="Y" ${selectedYes}>
                                        <fmt:message key="label.yes"/>
                                    </option>
                                    <option value="N" ${selectedNo}>
                                        <fmt:message key="label.no"/>
                                    </option>
                                </select>
                            </td>
                        </tr>
                <%--
                        <tr id="intendedProductionFormulationSpan" style="display: none">
                            <td class="optionTitleBoldLeft" colspan="4" nowrap="true">
                                <fmt:message key="label.intendedforproductformulation"/>*&nbsp;
                                <c:set var="selectedVal" value='${hmrbData.intendedProductFormulation}'/>
                                <c:set var="selectedYes" value=''/>
                                <c:set var="selectedNo" value=''/>
                                <c:if test="${selectedVal == 'Y'}">
                                    <c:set var="selectedYes" value='selected'/>
                                    <c:set var="selectedNo" value=''/>
                                </c:if>
                                <c:if test="${selectedVal == 'N'}">
                                    <c:set var="selectedYes" value=''/>
                                    <c:set var="selectedNo" value='selected'/>
                                </c:if>
                                <select name="intendedProductFormulation" id="intendedProductFormulation" class="selectBox">
                                    <option value=""> </option>
                                    <option value="Y" ${selectedYes}>
                                        <fmt:message key="label.yes"/>
                                    </option>
                                    <option value="N" ${selectedNo}>
                                        <fmt:message key="label.no"/>
                                    </option>
                                </select>
                            </td>
                        </tr>
 				--%>
                        <tr>
                            <td class="optionTitleBoldRight">
                                <fmt:message key="label.pointofcontact"/>:
                            </td>
                            <td class="optionTitleBoldLeft" colspan="3">
					 		<textarea cols="110" rows="2" class="inputBox" name="pointOfContact" id="pointOfContact">${hmrbData.pointOfContact}</textarea>
				 		</td>
                        </tr>

                    </table>
                   </fieldset>     
                </span>
			</td>
		</tr>

		 <tr>
			 <td>
				 <span id="processSpan" style="display: none">
                     <fieldset>
					     <legend><fmt:message key="label.processinformation"/></legend>
                         <table class="tableSearch" border="0">
                            <c:set var="useDescriptionCount" value='0'/>
                            <c:set var="hasEndOfRowTag" value='N'/>
                            <c:forEach var="vvUseDescriptionBean" items="${hmrbData.useDescriptionColl}" varStatus="status">
                                 <c:if test="${useDescriptionCount % 5 == 0}">
                                     <tr>
                                     <c:if test="${useDescriptionCount == 0}">
                                         <td class="optionTitleBoldRight">
                                            <fmt:message key="label.useprocesses"/>:*
                                         </td>
                                         <td class="optionTitleBoldLeft" colspan="2">
				                            	<select name="dischargeSinkDrainId" id="dischargeSinkDrainId" class="selectBox" >
				                            	<c:if test="${fn:length(hmrbData.selectedUseDescriptionIds) == 0}">
				                                    <option value="-9229068743"><fmt:message key="label.selectOne"/></option>
				                                </c:if>
				                                    <option value="">
				                                        <fmt:message key="label.no"/>
				                                    </option>
				                                </select>
			                            		<fmt:message key="label.dischargesinkdrain"/>
			                            	</td>
			                            </tr>
			                            <tr>
				                             <td class="optionTitleBoldLeft">
	                                            &nbsp;
	                                         </td>
                                     </c:if>
                                     <c:if test="${useDescriptionCount != 0}">
                                         <td class="optionTitleBoldLeft">
                                            &nbsp;
                                         </td>
                                     </c:if>
                                </c:if>
                                <c:choose>
                                	<c:when test="${vvUseDescriptionBean.useName == 'Discharge to Sink/Drain'}">
                                	  	  <c:set var="useDescriptionCount" value='${useDescriptionCount-1}'/>
                                	  	  <c:set var="dischargeSinkDrainId" value='${vvUseDescriptionBean.useId}'/>
                                	</c:when>
                                	<c:otherwise>
                                		<td class="optionTitleBoldLeft">
	                                	<input type="checkbox" class="radioBtns" name="useDescription${useDescriptionCount}" id="useDescription${useDescriptionCount}" value="${vvUseDescriptionBean.useId}">
	                                    ${vvUseDescriptionBean.useName}
	                                    <c:set var="otherUseDescriptionId" value='-1'/>
	                                    <c:if test="${vvUseDescriptionBean.useName == 'Other'}">
	                                            <c:set var="otherUseDescriptionId" value='${vvUseDescriptionBean.useId}'/>
	                                     </c:if>
	                                     </td>
                                	</c:otherwise>
                                </c:choose>

                                <c:if test="${useDescriptionCount == 4 || useDescriptionCount+5 % 4 == 0}">
                                    </tr>
                                    <c:set var="hasEndOfRowTag" value='Y'/>
                                </c:if>
                                <c:set var="useDescriptionCount" value='${useDescriptionCount+1}'/>
                            </c:forEach>
                            <%-- if data is not multiple of 4 then add end of row tag --%>
                            <c:if test="${hasEndOfRowTag == 'N'}">
                                </tr>
                            </c:if>
                            <tr>
                                 <td class="optionTitleBoldRight">
                                    <fmt:message key="label.additionaldescription"/>:
                                </td>
                                <td class="optionTitleBoldLeft" colspan="6">
                                    <textarea cols="110" rows="2" class="inputBox" name="additionalDescription" id="additionalDescription">${hmrbData.additionalDescription}</textarea>
                                </td>
                            </tr>
                             <%-- <tr>
                            	<td class="optionTitleBoldRight">
                            		  <fmt:message key="label.dischargesinkdrain"/>:*&nbsp;
                            	</td>
                            	<td>
	                            	<select name="dischargeSinkDrainId" id="dischargeSinkDrainId" class="selectBox" >
	                            	<c:if test="${fn:length(hmrbData.selectedUseDescriptionIds) == 0}">
	                                    <option value="pleaseSelect"><fmt:message key="label.selectOne"/></option>
	                                </c:if>
	                                    <option value="">
	                                        <fmt:message key="label.no"/>
	                                    </option>
	                                    <option value="${dischargeSinkDrainId}">
	                                        <fmt:message key="label.yes"/>
	                                    </option>
	                                </select>
                            	</td>
                            </tr>--%>

                            <%--
                            <tr>
                                <td class="optionTitleBoldLeft" colspan="7">
                                    <fmt:message key="label.matlflyawaywithaircraft"/>*&nbsp;
                                    <c:set var="selectedVal" value='${hmrbData.matlFlyAwayWithAircraft}'/>
                                    <c:set var="selectedYes" value=''/>
                                    <c:set var="selectedNo" value=''/>
                                    <c:if test="${selectedVal == 'Y'}">
                                        <c:set var="selectedYes" value='selected'/>
                                        <c:set var="selectedNo" value=''/>
                                    </c:if>
                                    <c:if test="${selectedVal == 'N'}">
                                        <c:set var="selectedYes" value=''/>
                                        <c:set var="selectedNo" value='selected'/>
                                    </c:if>
                                    <select name="matlFlyAwayWithAircraft" id="matlFlyAwayWithAircraft" class="selectBox">
                                        <option value=""> </option>
                                        <option value="Y" ${selectedYes}>
                                            <fmt:message key="label.yes"/>
                                        </option>
                                        <option value="N" ${selectedNo}>
                                            <fmt:message key="label.no"/>
                                        </option>
                                    </select>
                                </td>
                            </tr>
                            --%>

                            <c:set var="substrateCount" value='0'/>
                            <c:set var="hasEndOfRowTag" value='N'/>
                            <tr>
                                <td class="optionTitleBoldRight">
                                    <fmt:message key="label.substrateappliedon"/>*
                                </td>
                                <td class="optionTitleBoldLeft" colspan="6">
                                    <c:forEach var="vvSubstrateBean" items="${hmrbData.substrateColl}" varStatus="status">
                                        <input type="checkbox" class="radioBtns" name="substrate${substrateCount}" id="substrate${substrateCount}" value="${vvSubstrateBean.substrateId}">
                                        ${vvSubstrateBean.substrateName}
                                        <c:set var="substrateCount" value='${substrateCount+1}'/>
                                    </c:forEach>
                                </td>
                            </tr>   
		                   <tr>
		                        <td class="optionTitleBoldLeft" colspan="6">
		                            <fmt:message key="label.estimateannualusage"/>:*&nbsp;
		                            <input class="inputBox" type="text" name="estimatedAnnualUsage" id="estimatedAnnualUsage" value="${hmrbData.estimatedAnnualUsage}" size="10">
		                            <select class="selectBox" name="estimatedAnnualUsageUnit" id="estimatedAnnualUsageUnit">
		                                <c:set var="selectedUnit" value='${hmrbData.estimatedAnnualUsageUnit}'/>
		                                <c:set var="unitColl" value='${hmrbData.maxQtyUsePerShiftUnitColl}'/>
		                                <bean:size id="unitSize" name="unitColl"/>
		                                <c:if test="${unitSize > 1}">
		                                    <option value="" selected><fmt:message key="label.selectOne"/></option>
		                                </c:if>
		                                <c:forEach var="vvSizeUnitBean" items="${hmrbData.maxQtyUsePerShiftUnitColl}" varStatus="status1">
		                                    <c:set var="currentUnit" value='${status1.current.sizeUnit}'/>
		                                    <c:choose>
		                                        <c:when test="${currentUnit == selectedUnit}">
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
                            <tr id="thinnedRow1" <c:if test="${param.isKit == true}">style="display:none"</c:if> >
                                <td class="optionTitleBoldLeft" colspan="6">
                                    <fmt:message key="label.matlthinnedwhenused"/>&nbsp;
                                    <c:set var="selectedVal" value='${hmrbData.matlThinnedWhenUsed}'/>
                                    <c:set var="selectedYes" value=''/>
                                    <c:set var="selectedNo" value='selected'/>
                                    <c:if test="${selectedVal == 'Y'}">
                                        <c:set var="selectedYes" value='selected'/>
                                        <c:set var="selectedNo" value=''/>
                                    </c:if>
                                    <c:if test="${selectedVal == 'N'}">
                                        <c:set var="selectedYes" value=''/>
                                        <c:set var="selectedNo" value='selected'/>
                                    </c:if>
                                    <select name="matlThinnedWhenUsed" id="matlThinnedWhenUsed" class="selectBox" onchange="matlThinnedChanged()">
                                        <option value="Y" ${selectedYes}>
                                            <fmt:message key="label.yes"/>
                                        </option>
                                        <option value="N" ${selectedNo}>
                                            <fmt:message key="label.no"/>
                                        </option>
                                    </select>
                                </td>
                            </tr>
                            <tr id="thinnedRow2" <c:if test="${param.isKit == true}">style="display:none"</c:if> >
                                <td class="optionTitleBoldLeft" colspan="6">
                                    <span id="thinnedSpan" style="display: none">
                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="label.withmsds"/>:* <input class="inputBox" type="text" name="thinnedCustomerMsdsNumber" id="thinnedCustomerMsdsNumber" value="${hmrbData.thinnedCustomerMsdsNumber}" readonly="true" size="20">
					 		            <input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="selectedThinnedCustomerMsdsNumber" id="selectedThinnedCustomerMsdsNumber" value="..." align="right" onClick="lookupCustomerMsdsNumber();"/>
                                        &nbsp;&nbsp;<fmt:message key="label.thinningratio"/>:* <input class="inputBox" type="text" name="thinnerAmountInRatio" id="thinnerAmountInRatio" value="${hmrbData.thinnerAmountInRatio}" size="3">&nbsp;<fmt:message key="label.partsthinnerto"/>
                                        &nbsp;<input class="inputBox" type="text" name="thinnedMatlAmountInRatio" id="thinnedMatlAmountInRatio" value="${hmrbData.thinnedMatlAmountInRatio}" size="3">&nbsp;<fmt:message key="label.partsmaterial"/>
                                        &nbsp;<fmt:message key="label.lowercaseby"/>&nbsp;
                                        <c:set var="selectedVal" value='${hmrbData.thinningUnit}'/>
                                        <c:set var="selectedWt" value=''/>
                                        <c:set var="selectedVol" value=''/>
                                        <c:if test="${selectedVal == 'Wt'}">
                                            <c:set var="selectedWt" value='selected'/>
                                            <c:set var="selectedVol" value=''/>
                                        </c:if>
                                        <c:if test="${selectedVal == 'Vol'}">
                                            <c:set var="selectedWt" value=''/>
                                            <c:set var="selectedVol" value='selected'/>
                                        </c:if>
                                        <select class="selectBox" name="thinningUnit" id="thinningUnit">
                                            <option value="" >
                                                <fmt:message key="label.selectOne"/>
                                            </option>
                                            <option value="Wt" ${selectedWt}>
                                                <fmt:message key="label.weight"/>
                                            </option>
                                            <option value="Vol" ${selectedVol}>
                                                <fmt:message key="label.volume"/>
                                            </option>
                                         </select>
                                    </span>
                                </td>
                            </tr>
                        </table>
                      </fieldset>
                 </span>
			 </td>
		 </tr>
         <tr id="maxShiftUsageSpan" style="display: none">
                        <td class="optionTitleBoldLeft" colspan="6">
                            <fmt:message key="label.maxqtyusepershift"/><span id="maxShiftUsageRequiredSpan" style="display: none">*</span>:&nbsp;
                            <input class="inputBox" type="text" name="maxQtyUsePerShift" id="maxQtyUsePerShift" value="${hmrbData.maxQtyUsePerShift}" size="10">
                            <select class="selectBox" name="maxQtyUsePerShiftUnit" id="maxQtyUsePerShiftUnit">
                                <c:set var="selectedUnit" value='${hmrbData.maxQtyUsePerShiftUnit}'/>
                                <c:set var="unitColl" value='${hmrbData.maxQtyUsePerShiftUnitColl}'/>
                                <bean:size id="unitSize" name="unitColl"/>
                                <c:if test="${unitSize > 1}">
                                    <option value="" selected><fmt:message key="label.selectOne"/></option>
                                </c:if>
                                <c:forEach var="vvSizeUnitBean" items="${hmrbData.maxQtyUsePerShiftUnitColl}" varStatus="status1">
                                    <c:set var="currentUnit" value='${status1.current.sizeUnit}'/>
                                    <c:choose>
                                        <c:when test="${currentUnit == selectedUnit}">
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
         <tr>
			 <td>
				 <span id="ftwSpecificSpan" style="display: none">
					    <legend></legend>
                         <table class="tableSearch" border="0">
                            <tr>
                                <td class="optionTitleBoldRight">
                                     <fmt:message key="label.shift"/>:
                                </td>
                                <c:set var="tmpVal" value='${hmrbData.shift1}'/>
                                 <c:set var="tmpValChecked" value=''/>
                                 <c:if test="${tmpVal == 'Y'}">
                                    <c:set var="tmpValChecked" value='checked="checked"'/>
                                 </c:if>
                                <td class="optionTitleBoldLeft">
                                      <input type="checkbox" name="shift1" id="shift1" value="Y" class="radioBtns" ${tmpValChecked}>1
                                </td>
                                <c:set var="tmpVal" value='${hmrbData.shift2}'/>
                                 <c:set var="tmpValChecked" value=''/>
                                 <c:if test="${tmpVal == 'Y'}">
                                    <c:set var="tmpValChecked" value='checked="checked"'/>
                                 </c:if>
                                <td class="optionTitleBoldLeft">
                                      <input type="checkbox" name="shift2" id="shift2" value="Y" class="radioBtns" ${tmpValChecked}>2
                                </td>
                                <c:set var="tmpVal" value='${hmrbData.shift3}'/>
                                 <c:set var="tmpValChecked" value=''/>
                                 <c:if test="${tmpVal == 'Y'}">
                                    <c:set var="tmpValChecked" value='checked="checked"'/>
                                 </c:if>
                                <td class="optionTitleBoldLeft">
                                      <input type="checkbox" name="shift3" id="shift3" value="Y" class="radioBtns" ${tmpValChecked}>3
                                </td>
                            </tr>
                            <tr>
                                <td class="optionTitleBoldLeft">
                                    &nbsp;
                                </td>
                                <c:set var="tmpVal" value='${hmrbData.saturday}'/>
                                 <c:set var="tmpValChecked" value=''/>
                                 <c:if test="${tmpVal == 'Y'}">
                                    <c:set var="tmpValChecked" value='checked="checked"'/>
                                 </c:if>
                                <td class="optionTitleBoldLeft">
                                      <input type="checkbox" name="saturday" id="saturday" value="Y" class="radioBtns" ${tmpValChecked}><fmt:message key="label.saturday"/>
                                </td>
                                <c:set var="tmpVal" value='${hmrbData.sunday}'/>
                                 <c:set var="tmpValChecked" value=''/>
                                 <c:if test="${tmpVal == 'Y'}">
                                    <c:set var="tmpValChecked" value='checked="checked"'/>
                                 </c:if>
                                <td class="optionTitleBoldLeft">
                                      <input type="checkbox" name="sunday" id="sunday" value="Y" class="radioBtns" ${tmpValChecked}><fmt:message key="label.sunday"/>
                                </td>
                            </tr>
                            <tr>
                                <td class="optionTitleBoldRight">
                                     <fmt:message key="label.building"/>:*
                                </td>
                                <td class="optionTitleBoldLeft" colspan="4">
                                    <div style="border : solid 1px; width : 950px; height : 110px; overflow : auto; ">
                                        <table class="tableSearch" border="0" width="100%">
                                             <c:set var="buildingCount" value='0'/>
                                             <c:set var="hasEndOfRowTag" value='N'/>
                                             <c:forEach var="buildingBean" items="${hmrbData.buildingColl}" varStatus="status">
                                                 <c:if test="${buildingCount % 4 == 0}">
                                                     <tr>
                                                </c:if>
                                                <td width="25%"  class="optionTitleBoldLeft" nowrap>
                                                    <input type="checkbox" class="radioBtns" name="building${buildingCount}" id="building${buildingCount}" value="${buildingBean.buildingId}">
                                                    ${buildingBean.buildingDescription}
                                                </td>

                                                <c:if test="${buildingCount == 3 || buildingCount+4 % 3 == 0}"> 
                                                    </tr>
                                                    <c:set var="hasEndOfRowTag" value='Y'/>
                                                </c:if>
                                                <c:set var="buildingCount" value='${buildingCount+1}'/>
                                             </c:forEach>
                                             <%-- if data is not multiple of 4 then add end of row tag --%>
                                             <c:if test="${hasEndOfRowTag == 'N'}">
                                                </tr>
                                             </c:if>
                                        
                                        </table>
                                    </div>
                                </td>
                            </tr>

                            <c:set var="useLocationCount" value='0'/>
                            <c:set var="hasEndOfRowTag" value='N'/>
                            <c:set var="useLocationBoothIndex" value='0'/>
                            <c:forEach var="vvUseLocationBean" items="${hmrbData.useLocationColl}" varStatus="status">
                                 <c:if test="${useLocationCount % 4 == 0}">
                                     <tr>
                                     <c:if test="${useLocationCount == 0}">
                                         <td class="optionTitleBoldRight" nowrap="true">
                                            <fmt:message key="label.processlocation"/>:*
                                         </td>
                                     </c:if>
                                     <c:if test="${useLocationCount != 0}">
                                         <td class="optionTitleBoldLeft">
                                            &nbsp;
                                         </td>
                                     </c:if>
                                </c:if>

                                <c:set var="otherProcessLocationId" value='-1'/>
                                <c:if test="${vvUseLocationBean.useLocationName == 'Booth'}">
                                    <td class="optionTitleBoldLeft">
                                    <input type="checkbox" class="radioBtns" name="useLocation${useLocationCount}" id="useLocation${useLocationCount}" value="${vvUseLocationBean.useLocationId}" onclick="boothClicked(${useLocationCount})">
                                    ${vvUseLocationBean.useLocationName}
                                    <c:set var="useLocationBoothIndex" value='${useLocationCount}'/>
                                </c:if>
                                <c:if test="${vvUseLocationBean.useLocationName != 'Booth'}">
                                    <c:if test="${vvUseLocationBean.useLocationName == 'Other'}">
                                        <c:set var="otherProcessLocationId" value='${vvUseLocationBean.useLocationId}'/>
                                        <td class="optionTitleBoldLeft" colspan="2">
                                         <input type="checkbox" class="radioBtns" name="useLocation${useLocationCount}" id="useLocation${useLocationCount}" value="${vvUseLocationBean.useLocationId}" onclick="useLocationOtherClicked(${useLocationCount})">
                                         ${vvUseLocationBean.useLocationName}
                                          <input class="inputBox" type="text" name="processLocationOtherText" id="processLocationOtherText" value="${hmrbData.processLocationOtherText}" size="30">
                                    </c:if>
                                    <c:if test="${vvUseLocationBean.useLocationName != 'Other'}">
                                        <td class="optionTitleBoldLeft">
                                        <input type="checkbox" class="radioBtns" name="useLocation${useLocationCount}" id="useLocation${useLocationCount}" value="${vvUseLocationBean.useLocationId}">
                                        ${vvUseLocationBean.useLocationName}
                                    </c:if>
                                </c:if>
                                </td>

                                <c:if test="${useLocationCount == 3 || useLocationCount+4 % 3 == 0}">
                                    </tr>
                                    <c:set var="hasEndOfRowTag" value='Y'/>
                                </c:if>
                                <c:set var="useLocationCount" value='${useLocationCount+1}'/>
                            </c:forEach>
                            <%-- if data is not multiple of 4 then add end of row tag --%>
                            <c:if test="${hasEndOfRowTag == 'N'}">
                                </tr>
                            </c:if>

                            <tr>
                                <td class="optionTitleBoldRight">
                                    <div id="boothLabelDiv" style="display: none">
                                        <fmt:message key="label.booth"/>:
                                    </div>
                                </td>
                                <td class="optionTitleBoldLeft" colspan="4">
                                    <%-- production --%>
                                    <div id="boothProductionDiv" style="border : solid 1px; width : 950px; height : 110px; overflow : auto; display: none">
                                        <table class="tableSearch" border="0" width="100%">
                                             <c:set var="boothCountProd" value='0'/>
                                             <c:set var="hasEndOfRowTag" value='N'/>
                                             <c:forEach var="boothBean" items="${hmrbData.boothColl}" varStatus="status">
                                                 <c:if test="${boothBean.production == 'Y'}">
                                                     <c:if test="${boothCountProd % 4 == 0}">
                                                         <tr>
                                                    </c:if>
                                                    <td width="25%"  class="optionTitleBoldLeft" nowrap>
                                                        <input type="checkbox" class="radioBtns" name="boothProduction${boothCountProd}" id="boothProduction${boothCountProd}" value="${boothBean.boothId}">
                                                        ${boothBean.boothName}
                                                     
                                                    </td>

                                                    <c:if test="${boothCountProd == 3 || boothCountProd+4 % 3 == 0}">
                                                        </tr>
                                                        <c:set var="hasEndOfRowTag" value='Y'/>
                                                    </c:if>
                                                    <c:set var="boothCountProd" value='${boothCountProd+1}'/>
                                                 </c:if>
                                             </c:forEach>
                                             <%-- if data is not multiple of 4 then add end of row tag --%>
                                             <c:if test="${hasEndOfRowTag == 'N'}">
                                                </tr>
                                             </c:if>
                                        </table>
                                    </div>
                                    <%-- non production --%>
                                    <div id="boothNonProductionDiv" style="border : solid 1px; width : 950px; height : 110px; overflow : auto; display: none">
                                        <table class="tableSearch" border="0" width="100%">
                                             <c:set var="hasEndOfRowTag" value='N'/>
                                              <c:set var="boothCountNonProd" value='0'/>
                                             <c:forEach var="boothBean" items="${hmrbData.boothColl}" varStatus="status">
                                                 <c:if test="${boothBean.nonProduction == 'Y'}">
                                                     <c:if test="${boothCountNonProd % 4 == 0}">
                                                         <tr>
                                                    </c:if>
                                                    <td width="25%"  class="optionTitleBoldLeft" nowrap>
                                                        <input type="checkbox" class="radioBtns" name="boothNonProduction${boothCountNonProd}" id="boothNonProduction${boothCountNonProd}" value="${boothBean.boothId}">
                                                        ${boothBean.boothName}
                                                       
                                                    </td>
                                                    <c:if test="${boothCountNonProd == 3 || boothCountNonProd+4 % 3 == 0}">
                                                        </tr>
                                                        <c:set var="hasEndOfRowTag" value='Y'/>
                                                    </c:if>
                                                      <c:set var="boothCountNonProd" value='${boothCountNonProd+1}'/>
                                                 </c:if>
                                             </c:forEach>
                                             <%-- if data is not multiple of 4 then add end of row tag --%>
                                             <c:if test="${hasEndOfRowTag == 'N'}">
                                                </tr>
                                             </c:if>
                                        </table>
                                    </div>
                                </td>
                            </tr>
                        </table>
                 </span>
                 <span id="eshContactInfoSpan" style="display: none">
                    <fieldset>
					     <legend><fmt:message key="label.eshcontactinformation"/></legend>
                         <table class="tableSearch" border="0">
                             <tr>
                                 <td class="optionTitleBoldLeft">
                                     ${hmrbData.eshContact}
                                 </td>
                             </tr>
                         </table>
                    </fieldset>
                 </span>
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

<script language="JavaScript" type="text/javascript">

var otherUseDescriptionId="${otherUseDescriptionId};";
var theDischargeSinkDrainId = "${dischargeSinkDrainId}";
var otherProcessLocationId="${otherProcessLocationId};";
                                    
</script>

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
<input type="hidden" name="uAction" id="uAction" value="${param.uAction}"/>
<input type="hidden" name="personnelId" id="personnelId" value='${personnelBean.personnelId}'/>
<input type="hidden" name="requestId" id="requestId" value='${hmrbData.requestId}'/>
<input type="hidden" name="hmrbLineItem" id="hmrbLineItem" value='${hmrbData.hmrbLineItem}'/>
<input type="hidden" name="companyId" id="companyId" value='${hmrbData.companyId}'/>
<input type="hidden" name="facilityId" id="facilityId" value='${hmrbData.facilityId}'/>    
<input type="hidden" name="calledFrom" id="calledFrom" value='${hmrbData.calledFrom}'/>
<input type="hidden" name="selectedUsageCategoryId" id="selectedUsageCategoryId" value='${hmrbData.usageCategoryId}'/>
<input type="hidden" name="selectedUsageSubcategoryId" id="selectedUsageSubcategoryId" value='${hmrbData.usageSubcategoryId}'/>
<input type="hidden" name="selectedMaterialCategoryId" id="selectedMaterialCategoryId" value='${hmrbData.materialCategoryId}'/>
<input type="hidden" name="selectedMaterialSubcategoryId" id="selectedMaterialSubcategoryId" value='${hmrbData.materialSubcategoryId}'/>    
<input type="hidden" name="useDescriptionCount" id="useDescriptionCount" value="${useDescriptionCount}" />
<input type="hidden" name="selectedUseDescriptionIds" id="selectedUseDescriptionIds" value='${hmrbData.selectedUseDescriptionIds}'/>
<input type="hidden" name="substrateCount" id="substrateCount" value="${substrateCount}" />
<input type="hidden" name="selectedSubstrateIds" id="selectedSubstrateIds" value='${hmrbData.selectedSubstrateIds}'/>
<input type="hidden" name="useLocationCount" id="useLocationCount" value="${useLocationCount}" />
<input type="hidden" name="selectedUseLocationIds" id="selectedUseLocationIds" value='${hmrbData.selectedUseLocationIds}'/>
<input type="hidden" name="buildingCount" id="buildingCount" value="${buildingCount}" />    
<input type="hidden" name="selectedBuildingIds" id="selectedBuildingIds" value='${hmrbData.selectedBuildingIds}'/>
<input type="hidden" name="boothCountNonProd" id="boothCountNonProd" value="${boothCountNonProd}" />
<input type="hidden" name="boothCountProd" id="boothCountProd" value="${boothCountProd}" />
<input type="hidden" name="selectedBoothIds" id="selectedBoothIds" value="${hmrbData.selectedBoothIds}" />
<input type="hidden" name="useLocationBoothIndex" id="useLocationBoothIndex" value="${useLocationBoothIndex}" />
<input type="hidden" name="purchasingMethodCount" id="purchasingMethodCount" value="${purchasingMethodCount}" />
<input type="hidden" name="selectedPurchasingMethodIds" id="selectedPurchasingMethodIds" value="${hmrbData.selectedPurchasingMethodIds}" />
<input type="hidden" name="exportFlag" id="exportFlag" value="N"/>
<input type="hidden" name="showProgram" id="showProgram" value="${hmrbData.showProgram}"/>
<input type="hidden" name="programId" id="programId" value="${hmrbData.programId}"/>
	      
</div>
<!-- Hidden elements end -->
</tcmis:form>
</div>
<!-- close of contentArea -->

</div> <!-- close of interface -->

</body>
</html:html>