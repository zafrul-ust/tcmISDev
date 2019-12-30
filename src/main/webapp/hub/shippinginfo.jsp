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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html:html lang="true">

<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<!-- This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss overflow="notHidden"/>

<!-- Add any other stylesheets you need for the page here -->
<link rel="stylesheet" type="text/css" href="/css/tabs.css"></link>                

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
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<script type="text/javascript" src="/js/hub/shippinginfo.js"></script>

<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<!-- These are for the Grid and inner div popup windows-->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>	
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>

<%--This file has our custom sorting and other utility methos for the grid.--%>    
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
	<fmt:message key="label.shipinfo"/>
</title>

</head>

<c:choose>
	<c:when test="${blank !='Y' && !empty dotPartMaterialColl}">
	    <body bgcolor="#ffffff" onLoad="showTransitWin();editOnLoad('new');" onunload="closeAllchildren();" onresize="resizeResults();">
	</c:when>
	<c:otherwise>
	    <body bgcolor="#ffffff" onLoad="" onunload="closeAllchildren();">
	</c:otherwise>
</c:choose>


<script language="JavaScript" type="text/javascript">
<!--

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
itemid:"<fmt:message key="label.itemid"/>",
rqweight:"<fmt:message key="label.rqweight(lb)"/>",
erg:"<fmt:message key="label.erg"/>",
hazmatid:"<fmt:message key="label.hazmatid"/>",
dgid:"<fmt:message key="label.dgid"/>",
adrid:"<fmt:message key="label.adrid"/>",
infochangeddisplaylater:"<fmt:message key="label.infochangeddisplaylater"/>",
dottechnicalname:"<fmt:message key="label.dottechnicalname"/>",
iatatechnicalname:"<fmt:message key="label.iatatechnicalname"/>",
adrtechnicalname:"<fmt:message key="label.adrtechnicalname"/>",
imdgtechnicalname:"<fmt:message key="label.imdgtechnicalname"/>",
itemInteger:"<fmt:message key="error.item.integer"/>",
acidBase:"<fmt:message key="label.acidbase"/>",
corrosive:"<fmt:message key="label.corrosive"/>",
physicalState:"<fmt:message key="label.physicalstate"/>",
aerosol:"<fmt:message key="label.aerosol"/>",
flammable:"<fmt:message key="label.flammable"/>",
toxic:"<fmt:message key="label.toxic"/>",
oxidizer:"<fmt:message key="label.oxidizer"/>",
reactive:"<fmt:message key="label.reactive"/>",
waterReactive:"<fmt:message key="label.waterreactive"/>",
organicPeroxide:"<fmt:message key="label.organicperoxide"/>",
container:"<fmt:message key="label.container"/>",
containerPressure:"<fmt:message key="label.containerpressure"/>",
pyrophoric:"<fmt:message key="label.pyrophoric"/>",
waterMiscible:"<fmt:message key="label.watermiscible"/>"
};
// -->
</script>


<tcmis:form action="/shippinginfo.do" onsubmit="" target="_self">

<c:set var="pagePermission" value="N"/>
<c:set var="disabled" value="disabled"/>
<c:choose>
	<c:when test="${param.source eq 'JDE'}"></c:when>
	<c:otherwise>
		<tcmis:opsEntityPermission indicator="true" userGroupId="shippingInfo">   
		   <c:set var="pagePermission" value="Y"/>
		   <c:set var="disabled" value=""/>
		</tcmis:opsEntityPermission>
	</c:otherwise>
</c:choose>
<div class="interface" id="mainPage" style="">

<%-- transition --%>
<div id="transitDailogWin" class="errorMessages" style="display: none;overflow: auto;">
	<table width="100%" border="0" cellpadding="2" cellspacing="1">
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="center" id="transitLabel"><fmt:message key="label.pleasewait"/>
			</td>
		</tr>
		<tr>
			<td align="center">
				<img src="/images/rel_interstitial_loading.gif" align="middle">
			</td>
		</tr>
	</table>
</div>

<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/><br/>
    ${tcmISError}
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
    </c:forEach>        
</div>

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null and empty tcmISErrors and empty tcmISError}">
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

<c:set var="showExpertReview" value='N'/>
<tcmis:opsEntityPermission indicator="true" userGroupId="ExpertReviewAdmin" opsEntityId="">
<c:set var="showExpertReview" value='Y'/>
</tcmis:opsEntityPermission>
	<table class="tableSearch" border="0">
			<tr>
				<td class="optionTitleBoldRight" width="5%" nowrap>
					 <fmt:message key="label.itemid"/> :&nbsp;
				</td>
				<td class="optionTitleBoldLeft" width="25%">
					 <input class="inputBox" type="text" name="itemId" id="itemId" value="${param.itemId}" onkeypress="return onKeyPress(event);"  size="10" maxlength="10">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					 <input name="startNewCountBtn" id="startNewCountBtn" type="button" class="smallBtns" value="<fmt:message key="label.search"/>" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'"
          		 					onclick="submitSearchForm();"/>
				 </td>
				 <td class="optionTitleBoldLeft" width="25%">
					 <c:if test="${blank !='Y' && !empty dotPartMaterialColl && pagePermission == 'Y'}">
					 	<input name="startNewCountBtn" id="startNewCountBtn" type="button" class="smallBtns" value="<fmt:message key="label.update"/>" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'"
          		 					onclick="submitUpdate();"/>
          		 	 </c:if>
				 </td>
				 <td class="optionTitleBoldLeft" width="45%">
				 		<c:if test="${blank != 'Y' && !empty dotPartMaterialColl && showExpertReview == 'Y'}">
					 	<input name="expertRevBtn" id="expertRevBtn" type="button" class="smallBtns" value="<fmt:message key="label.expertreview"/>" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'"
          		 					onclick="expertReviewList();"/>
          		 		</c:if>
				 </td>
			 </tr>
			 <tr>
				<td class="optionTitleBoldRight" nowrap><fmt:message key="label.description"/> :&nbsp;
				</td>
				<td class="optionTitleLeft" colspan="3"  >
					<span id="descSpan" onmouseover="showTooltip('hiddenDesc', 'descSpan', 0, 15);" onmouseout="hideTooltip('hiddenDesc');">
						${fn:substring(dotPartMaterialColl[0].itemDesc, 0, 350)}
	   			    </span> 
	   			    <input type="hidden" id="descLength" value="${fn:length(dotPartMaterialColl[0].itemDesc)}" />
				</td>
			 </tr> 
			 <c:if test="${showExpertReview == 'Y'}">
			 <tr>
				<td class="optionTitleBoldRight" nowrap><fmt:message key="label.expertreview"/> :&nbsp;
				</td>
				<td class="optionTitleLeft" colspan="3"  >
					<c:forEach var="expertReviewItem" items="${expertReviewList}" varStatus="status">
					<span>${status.current.description};</span>
	   			    </c:forEach>
				</td>
			 </tr> 
			 </c:if>
		 </table>		 

<div id="resultDiv" >

<!-- No Data Found Div -->
<c:if test="${blank !='Y' && empty dotPartMaterialColl}">
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">    
	    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
	        <tr>
	            <td width="100%">
	                <fmt:message key="main.nodatafound"/>
	            </td>
	        </tr>
	    </table>
  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</c:if>


<c:if test="${!empty dotPartMaterialColl}">
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

<c:forEach var="dotPartMaterialBean" items="${dotPartMaterialColl}" varStatus="status">
 <c:if test="${dataCount == 0}">
  <c:set var="selectedTabId" value="${dataCount}"/>
 </c:if>
 openTab('<c:out value="${dataCount}"/>','','<fmt:message key="label.component"/>','','');
 <c:set var="dataCount" value='${dataCount+1}'/>
</c:forEach>
startSubOnload();
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
    <table id="resultTable" class="tableSearch" border="0" height="" width="100%" >
	 <tr>
    <td>
    	<div id="tabsdiv">
			<c:set var="partCount" value='0'/>
			<c:forEach var="dotPartMaterialBean" items="${dotPartMaterialColl}" varStatus="status">
			  <div id="newItem${partCount}" style="display:none;">
				<input type="hidden" name="dotPartMaterialBean[${partCount}].itemId" id="itemId${partCount}" value="${dotPartMaterialBean.itemId}">
				<input type="hidden" name="dotPartMaterialBean[${partCount}].partId" id="partId${partCount}" value="${dotPartMaterialBean.partId}">
					  <table width="100%" class="tableSearch" border="0">
						 <tr>
							<td class="optionTitleBoldRight" width="5%" nowrap>
								<fmt:message key="label.materialid"/> :
							</td>
							<td class="optionTitleBoldLeft"  width="25%" nowrap>
								<input name="dotPartMaterialBean[${partCount}].materialId" id="materialId${partCount}" type="text" maxlength="50" size="30" 
  									value="${dotPartMaterialBean.materialId}" class="invGreyInputText" readonly/>
  								<input name="startNewCountBtn" id="startNewCountBtn" type="button" class="smallBtns" value="<fmt:message key="label.msds"/>" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'"
          		 					onclick= "popMSDSup();"/>
							</td>
							<td class="optionTitleBoldRight" width="5%" nowrap>
								<fmt:message key="label.materialdesc"/> :
							</td>
							<td class="optionTitleLeft"  width="65%" >
								${dotPartMaterialBean.materialDesc}
							</td>
						 </tr>
						 <tr>
							<td class="optionTitleBoldRight" width="5%" nowrap>
								<fmt:message key="label.packaging"/> :
							</td>
							<td class="optionTitleLeft" width="25%" >
								${dotPartMaterialBean.packaging}
							</td>
							<td class="optionTitleBoldRight" width="5%" nowrap>
								<fmt:message key="label.weight(lb)"/> :
							</td>
							<td class="optionTitleLeft"  width="65%" >
								${dotPartMaterialBean.weightLb}
							</td>
						</tr>
					    <tr>
							<td class="optionTitleBoldRight" width="5%" nowrap>
								<fmt:message key="label.scheduleB"/> :
							</td>
							<td class="optionTitleLeft" >
								${dotPartMaterialBean.scheduleB}
							</td>
							<td class="optionTitleBoldRight" width="5%" nowrap>
								<fmt:message key="label.schedulebdesc"/> :
							</td>
							<td class="optionTitleLeft" width="65%" >
								${dotPartMaterialBean.scheduleBDesc}
							</td>
						</tr>
						<tr>
							<td class="optionTitleBoldRight" width="5%" nowrap>
								<fmt:message key="label.rqweight(lb)"/> :
							</td>
							<td class="optionTitleBoldLeft" width="25%" nowrap>
								<c:choose>
								   <c:when test="${pagePermission == 'Y'}"> 
								    <input class="inputBox" type="text" name="dotPartMaterialBean[${partCount}].reportableQuantityLb" id="reportableQuantityLb${partCount}" value="${dotPartMaterialBean.reportableQuantityLb}" size="10" >
								   </c:when>
								   <c:otherwise>
								    ${dotPartMaterialBean.reportableQuantityLb}
								   </c:otherwise>
								</c:choose>  
							</td>
							<td class="optionTitleBoldLeft" nowrap>
								&nbsp;
							</td>
							<td class="optionTitleBoldLeft">
								<input type="checkbox" class="radioBtns" name="dotPartMaterialBean[${partCount}].pkgGtRq" id="pkgGtRq${partCount}" value="Y" <c:if test="${dotPartMaterialBean.pkgGtRq == 'Y'}">checked</c:if> ${disabled} />
								<fmt:message key="label.componentNetWeightRQ"/>
							</td>
						</tr>
						<tr>
							<td class="optionTitleBoldRight" width="5%" nowrap>
								<fmt:message key="label.erg"/> :
							</td>
							<td class="optionTitleLeft" width="25%" nowrap>
								<c:choose>
								   <c:when test="${pagePermission == 'Y'}"> 
								    <input class="inputBox" type="text" name="dotPartMaterialBean[${partCount}].erg" id="erg${partCount}" value="${dotPartMaterialBean.erg}" size="10" >
								   </c:when>
								   <c:otherwise>
								    ${dotPartMaterialBean.erg}
								   </c:otherwise>
								</c:choose>   
							</td>
							<td class="optionTitleBoldLeft" nowrap>
								&nbsp;
							</td>
							<td class="optionTitleBoldLeft">
								<input type="checkbox" class="radioBtns" name="dotPartMaterialBean[${partCount}].ormD" id="ormD${partCount}" value="Y" <c:if test="${dotPartMaterialBean.ormD == 'Y'}">checked</c:if> ${disabled} />
								<fmt:message key="label.limitedquantitymaterial"/>
							</td>
						</tr>
					    <tr>
					    	<td class="optionTitleBoldRight" width="5%" nowrap>
								<fmt:message key="label.eccn"/> :
							</td>
							<td class="optionTitleLeft" width="25%" nowrap>
								<c:choose>
								   <c:when test="${pagePermission == 'Y'}"> 
								    <input class="inputBox" type="text" name="dotPartMaterialBean[${partCount}].eccn" id="eccn${partCount}" value="${dotPartMaterialBean.eccn}" size="12"  maxlength="12" >
								   </c:when>
								   <c:otherwise>
								    ${dotPartMaterialBean.eccn}
								   </c:otherwise>
								</c:choose>   
							</td>
							<td class="optionTitleBoldLeft" nowrap>
								&nbsp;
							</td>
							<td class="optionTitleBoldLeft" >
								<input type="checkbox" class="radioBtns" name="dotPartMaterialBean[${partCount}].marinePollutant" id="marinePollutant${partCount}" value="Y" <c:if test="${dotPartMaterialBean.marinePollutant == 'Y'}">checked</c:if> ${disabled} />
								<fmt:message key="label.marinePollutant"/>
							</td>
						</tr>
						<tr>
					    	<td class="optionTitleBoldRight" width="5%" nowrap>
								<fmt:message key="label.lastUpdatedBy"/> :
							</td>
							<td class="optionTitleLeft" width="25%" nowrap>
								${dotPartMaterialBean.lastChangedBy}
							</td>
							<td class="optionTitleBoldLeft" nowrap>
								&nbsp;
							</td>
							<td class="optionTitleBoldLeft" >
								<input type="checkbox" class="radioBtns" name="dotPartMaterialBean[${partCount}].bulkPkgMarinePollutant" id="bulkPkgMarinePollutant${partCount}" value="Y" <c:if test="${dotPartMaterialBean.bulkPkgMarinePollutant == 'Y'}">checked</c:if> ${disabled} />
								<fmt:message key="label.bulkpkgmarinepollutant"/>
							</td>
						</tr>
						<tr>
							<td class="optionTitleBoldRight" width="5%" nowrap>
								<fmt:message key="label.lastupdatedat"/> :
							</td>
							<td class="optionTitleLeft"  width="25%" nowrap>
								${dotPartMaterialBean.lastUpdated}
							</td>
							<td class="optionTitleBoldRight" width="5%" nowrap>
								&nbsp;
							</td>
							<td class="optionTitleBoldLeft"  width="65%" >
								<input type="checkbox" class="radioBtns" name="dotPartMaterialBean[${partCount}].dryIce" id="dryIce${partCount}" value="Y" <c:if test="${dotPartMaterialBean.dryIce == 'Y'}">checked</c:if> ${disabled} />
								<fmt:message key="label.dryice"/>
							</td>
						</tr>
				</table>
<!-- CSS Tabs -->
<div id="newChemTabs" style="background-color: #E5E5E5;">
 <ul id="subTabList${partCount}">
 </ul>
</div>
<!-- CSS Tabs End -->
<table border="1" width="100%">
<tr><td>
  <div id="newType0${partCount}" style="display:none;" class="roundContent">    
	    		<table class="tableSearch" border="0" width="100%">				 
				 	<tr>
						<td class="optionTitleBoldRight" width="5%" nowrap>
							<fmt:message key="label.SHIP"/> :
						</td>
						<td class="optionTitleLeft" colspan="3" readonly nowrap>
							<span name="dotPartMaterialBean[${partCount}].dotDeclaration" id="dotDeclaration${partCount}"> 
									<c:set var="dotDeclaration" value='${dotPartMaterialBean.dotDeclaration}'/>
  									<c:if test="${dotPartMaterialBean.dotDeclaration =='ORM-D'}"><c:set var="dotDeclaration"><fmt:message key="label.limitedquantitymaterial"/></c:set></c:if>
  									${dotDeclaration}
  							</span>
						</td>
					</tr>
				 	<tr>
						<td class="optionTitleBoldRight" width="5%" nowrap>
							<fmt:message key="label.hazmatid"/> :
						</td>
						<td class="optionTitleLeft" nowrap>
							<input type="text" name="dotPartMaterialBean[${partCount}].hazmatId" id="hazmatId${partCount}" 
								value="${dotPartMaterialBean.hazmatId}" size="10"  class="invGreyInputText" readonly>
							<c:if test="${pagePermission == 'Y'}"><input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="dotBtn" id="dotBtn" value="..." align="right" onClick="getDOTInfo('${partCount}');"/></c:if>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
						<td class="optionTitleBoldRight" width="1%" nowrap>
							<fmt:message key="label.identificationnumber"/> :
						</td>
						<td class="optionTitleLeft" width="90%" nowrap>
							<input type="text" name="dotPartMaterialBean[${partCount}].identificationNumber" id="identificationNumber${partCount}" 
								value="${dotPartMaterialBean.identificationNumber}" size="6" maxlength="6" class="invGreyInputText" readonly>
						</td>
					</tr>
				 	<tr>
						<td class="optionTitleBoldRight" nowrap>
							<fmt:message key="label.properShippingName"/> :
						</td>
						<td class="optionTitleLeft" colspan="3" readonly nowrap>
							<input name="dotPartMaterialBean[${partCount}].properShippingName" id="properShippingName${partCount}" type="text" maxlength="200" size="150" 
  									value="${dotPartMaterialBean.properShippingName}" class="invGreyInputText" readonly/>
						</td>
					</tr>
					<tr>
						<td class="optionTitleBoldRight" nowrap>
							<fmt:message key="label.technicalName"/> :
						</td>
						<td class="optionTitleLeft" colspan="3" nowrap>
							<c:choose>
							  <c:when test="${pagePermission == 'Y' && (dotPartMaterialBean.symbol == 'G' || dotPartMaterialBean.symbol == 'A G' || dotPartMaterialBean.symbol == 'D G' || dotPartMaterialBean.symbol == 'G I')}"> 
								 <input name="dotPartMaterialBean[${partCount}].hazmatTechnicalName" id="hazmatTechnicalName${partCount}" type="text" maxlength="200" size="120" 
  									value="${dotPartMaterialBean.hazmatTechnicalName}" onkeyup="showTempInfo('dotDeclaration${partCount}');" class="inputBox" />
							  </c:when>
							  <c:otherwise>
								 <input name="dotPartMaterialBean[${partCount}].hazmatTechnicalName" id="hazmatTechnicalName${partCount}" type="text" maxlength="200" size="120" 
  									value="${dotPartMaterialBean.hazmatTechnicalName}" onkeyup="showTempInfo('dotDeclaration${partCount}');" class="invGreyInputText" readonly />
							  </c:otherwise>
							</c:choose> 
							<input type="hidden" name="dotPartMaterialBean[${partCount}].symbol" id="symbol${partCount}" value="${dotPartMaterialBean.symbol}">
						</td>
					</tr>
					<tr>
						<td class="optionTitleBoldRight" nowrap>
							<fmt:message key="label.hazardclass"/> :
						</td>
						<td class="optionTitleLeft" nowrap>
							<input name="dotPartMaterialBean[${partCount}].hazardClass" id="hazardClass${partCount}" type="text" maxlength="11" size="11" 
  									value="${dotPartMaterialBean.hazardClass}" class="invGreyInputText" readonly/>
						</td>
						<td class="optionTitleBoldRight" width="5%" nowrap>
							<fmt:message key="label.packinggroup"/> :
						</td>
						<td class="optionTitleLeft" nowrap>
							<input name="dotPartMaterialBean[${partCount}].packingGroup" id="packingGroup${partCount}" type="text" maxlength="3" size="3" 
  									value="${dotPartMaterialBean.packingGroup}" class="invGreyInputText" readonly/>
						</td>
					</tr>
					<tr>
						<td class="optionTitleBoldRight" nowrap>
							<fmt:message key="label.subsidaryRisk"/> :
						</td>
						<td class="optionTitleLeft" nowrap>
							<input type="text" name="dotPartMaterialBean[${partCount}].subsidiaryHazardClass" id="subsidiaryHazardClass${partCount}" 
								value="${dotPartMaterialBean.subsidiaryHazardClass}" size="20" maxlength="30" class="invGreyInputText" readonly>
							<c:if test="${pagePermission == 'Y'}">
								<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="dotBtn" id="dotBtn" value="..." align="right" 
									onClick="getSubRisk('subsidiaryHazardClass'+'${partCount}', 'DOT');"/>
								<input name="startNewCountBtn" id="startNewCountBtn" type="button" class="smallBtns" value="<fmt:message key="label.clear"/>" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'"
          		 					onclick= "clearField('subsidiaryHazardClass'+'${partCount}');"/>
							</c:if>
						</td>
						<td class="optionTitleBoldRight" nowrap>
							&nbsp;
						</td>
						<td class="optionTitleLeft" nowrap>
							&nbsp;
						</td>
					</tr>
					<tr>
						<td class="optionTitleBoldRight" nowrap>
							<fmt:message key="label.shippingremarks"/> :
						</td>
						<td class="optionTitleBoldLeft" colspan="3">
							<c:choose>
							  <c:when test="${pagePermission == 'Y'}"> 
								 <TEXTAREA name="dotPartMaterialBean[${partCount}].shippingInfoRemarks" id="shippingInfoRemarks${partCount}" onKeyDown='limitText("shippingInfoRemarks${partCount}", "<fmt:message key="label.shippingremarks"/>", 200);' onKeyUp='limitText("shippingInfoRemarks${partCount}", "<fmt:message key="label.shippingremarks"/>", 200);' class="inputBox" COLS=150 ROWS=2>${dotPartMaterialBean.shippingInfoRemarks}</TEXTAREA>
							  </c:when>
							  <c:otherwise>
								 ${dotPartMaterialBean.shippingInfoRemarks}
							  </c:otherwise>
							</c:choose>  
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					</tr>
					<tr><td>&nbsp;</td></tr>
					<tr><td>&nbsp;</td></tr>
				</table>
  </div>
  <div id="newType1${partCount}" style="display:none;" class="roundContent">    
	    		<table class="tableSearch" border="0" width="100%">
	    		    <tr>
						<td class="optionTitleBoldRight" width="5%" nowrap>
							<fmt:message key="label.declaration"/> :
						</td>
						<td class="optionTitleLeft" colspan="3" readonly nowrap>
							<span name="dotPartMaterialBean[${partCount}].iataDeclaration" id="iataDeclaration${partCount}">
  									${dotPartMaterialBean.iataDeclaration}
  							</span>
						</td>
					</tr>				 
				 	<tr>
						<td class="optionTitleBoldRight" width="5%" nowrap>
							<fmt:message key="label.dgid"/> :
						</td>
						<td class="optionTitleLeft" width="25%" nowrap>
							<input name="dotPartMaterialBean[${partCount}].iataDgId" id="iataDgId${partCount}" type="text" maxlength="10" size="10" 
  									value="${dotPartMaterialBean.iataDgId}" class="invGreyInputText" readonly/>
  							<c:if test="${pagePermission == 'Y'}"><input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="iataBtn" id="iataBtn" value="..." align="right" onClick="getIATAInfo('${partCount}');"/></c:if>
  							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
						<td class="optionTitleBoldRight" width="5%" nowrap>
							<fmt:message key="label.identificationnumber"/> :
						</td>
						<td class="optionTitleLeft" width="65%" nowrap>
							<input type="text" name="dotPartMaterialBean[${partCount}].iataIdentificationNumber" id="iataIdentificationNumber${partCount}" 
								value="${dotPartMaterialBean.iataIdentificationNumber}" size="10" class="invGreyInputText" readonly>
						</td>
					</tr>
				 	<tr>
						<td class="optionTitleBoldRight" width="5%" nowrap>
							<fmt:message key="label.properShippingName"/> :
						</td>
						<td class="optionTitleLeft"  colspan="3" nowrap>
							<input name="dotPartMaterialBean[${partCount}].iataProperShippingName" id="iataProperShippingName${partCount}" type="text" maxlength="200" size="150" 
  									value="${dotPartMaterialBean.iataProperShippingName}" class="invGreyInputText" readonly/>
						</td>
					</tr>
					<tr>
						<td class="optionTitleBoldRight" nowrap>
							<fmt:message key="label.technicalName"/> :
						</td>
						<td class="optionTitleLeft" colspan="3" nowrap>
							<c:choose>
								<c:when test="${dotPartMaterialBean.iataTechnicalNameRequired == 'Y' && pagePermission == 'Y'}">
							        <input name="dotPartMaterialBean[${partCount}].iataTechnicalName" id="iataTechnicalName${partCount}" type="text" maxlength="200" size="150" 
  										value="${dotPartMaterialBean.iataTechnicalName}" onkeyup="showTempInfo('iataDeclaration${partCount}');"  class="inputBox"/>
								</c:when>
								<c:otherwise>
							       	<input name="dotPartMaterialBean[${partCount}].iataTechnicalName" id="iataTechnicalName${partCount}" type="text" maxlength="200" size="60" 
  										value="${dotPartMaterialBean.iataTechnicalName}" onkeyup="showTempInfo('iataDeclaration${partCount}');"class="invGreyInputText" readonly/>
								</c:otherwise>
							</c:choose>
							<input type="hidden" name="dotPartMaterialBean[${partCount}].iataTechnicalNameRequired" id="iataTechnicalNameRequired${partCount}" value="${dotPartMaterialBean.iataTechnicalNameRequired}">
						</td>
					</tr>
					<tr>
						<td class="optionTitleBoldRight" nowrap>
							<fmt:message key="label.classordivision"/> :
						</td>
						<td class="optionTitleBoldLeft" nowrap>
							<input type="text" name="dotPartMaterialBean[${partCount}].iataClassOrDivision" id="iataClassOrDivision${partCount}" value="${dotPartMaterialBean.iataClassOrDivision}"  class="invGreyInputText" readonly size="4" >
						</td>
						<td class="optionTitleBoldRight" nowrap>
							<fmt:message key="label.packinggroup"/> :
						</td>
						<td class="optionTitleLeft" nowrap>
							<input name="dotPartMaterialBean[${partCount}].iataPackingGroup" id="iataPackingGroup${partCount}" type="text" maxlength="3" size="3" 
  									value="${dotPartMaterialBean.iataPackingGroup}" class="invGreyInputText" readonly/>
						</td>
					</tr>
					<tr>
						<td class="optionTitleBoldRight" nowrap>
							<fmt:message key="label.subsidiaryrisk"/> :
						</td>
						<td class="optionTitleLeft" nowrap>
							<input type="text" name="dotPartMaterialBean[${partCount}].iataSubrisk" id="iataSubrisk${partCount}" 
								value="${dotPartMaterialBean.iataSubrisk}" size="10" maxlength="10" class="invGreyInputText" readonly>
							<c:if test="${pagePermission == 'Y'}">
								<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="dotBtn" id="dotBtn" value="..." align="right" 
									onClick="getSubRisk('iataSubrisk'+'${partCount}', 'IATA');"/>
								<input name="startNewCountBtn" id="startNewCountBtn" type="button" class="smallBtns" value="<fmt:message key="label.clear"/>" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'"
          		 					onclick= "clearField('iataSubrisk'+'${partCount}');"/>
							</c:if>
						</td>
						<td class="optionTitleBoldRight" nowrap>
							<fmt:message key="label.regsubrisk"/> :
						</td>
						<td class="optionTitleLeft" nowrap>
							<input name="dotPartMaterialBean[${partCount}].iataRegSubrisk" id="iataRegSubrisk${partCount}" type="text" maxlength="20" size="20" 
  										value="${dotPartMaterialBean.iataRegSubrisk}" class="invGreyInputText" readonly/>
						</td>
					</tr>
					<tr>
						<td class="optionTitleBoldRight" nowrap>
							<fmt:message key="label.hazardlabel"/> :
						</td>
						<td class="optionTitleLeft" nowrap>
							<input name="dotPartMaterialBean[${partCount}].iataHazardLabel" id="iataHazardLabel${partCount}" type="text" maxlength="44" size="44" 
  										value="${dotPartMaterialBean.iataHazardLabel}" class="invGreyInputText" readonly/>
						</td>
						<td class="optionTitleBoldLeft" colspan="2" nowrap>
							<input type="radio" name="dotPartMaterialBean[${partCount}].iataMixtureSolution" id="iataMixtureSolution${partCount}" value="" <c:if test="${empty dotPartMaterialBean.iataMixtureSolution}">checked</c:if>  ${disabled} /><fmt:message key="label.na" />&nbsp;
							<input type="radio" name="dotPartMaterialBean[${partCount}].iataMixtureSolution" id="iataMixtureSolution${partCount}" value="M" <c:if test="${dotPartMaterialBean.iataMixtureSolution eq 'M'}">checked</c:if> ${disabled} /><fmt:message key="label.mixture" />&nbsp;
							<input type="radio" name="dotPartMaterialBean[${partCount}].iataMixtureSolution" id="iataMixtureSolution${partCount}" value="S" <c:if test="${dotPartMaterialBean.iataMixtureSolution eq 'S'}">checked</c:if>  ${disabled} /><fmt:message key="label.solution" />
						</td>
					</tr>
					<tr>
						<td class="optionTitleBoldRight" nowrap>
							<fmt:message key="label.specialprovision"/> :
						</td>
						<td class="optionTitleBoldLeft" nowrap>
							<input name="dotPartMaterialBean[${partCount}].iataSpecialProvision" id="iataSpecialProvision${partCount}" type="text" maxlength="26" size="26" 
  									value="${dotPartMaterialBean.iataSpecialProvision}" class="invGreyInputText" readonly/>
						</td>
						<td class="optionTitleBoldRight" nowrap>
							<fmt:message key="label.ergcode"/> :
						</td>
						<td class="optionTitleLeft" nowrap>
							<input name="dotPartMaterialBean[${partCount}].iataErgCode" id="iataErgCode${partCount}" type="text" maxlength="4" size="4" 
  										value="${dotPartMaterialBean.iataErgCode}" class="invGreyInputText" readonly/>
						</td>
					</tr>
					<tr>
						<td class="optionTitleBoldRight" width="5%" nowrap>
							<fmt:message key="label.shippingremarks"/> :
						</td>
						<td class="optionTitleBoldLeft" colspan="3">
							<c:choose>
							  <c:when test="${pagePermission == 'Y'}"> 
								 <TEXTAREA name="dotPartMaterialBean[${partCount}].iataShippingRemark" id="iataShippingRemark${partCount}" onKeyDown='limitText("iataShippingRemark${partCount}", "<fmt:message key="label.shippingremarks"/>", 400);' onKeyUp='limitText("iataShippingRemark${partCount}", "<fmt:message key="label.shippingremarks"/>", 400);' class="inputBox" COLS=150 ROWS=2>${dotPartMaterialBean.iataShippingRemark}</TEXTAREA>
							  </c:when>
							  <c:otherwise>
								 ${dotPartMaterialBean.iataShippingRemark}
							  </c:otherwise>
							</c:choose>  
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					</tr>
				</table>
  </div>
  <div id="newType2${partCount}" style="display:none;" class="roundContent">    
 				 	<table class="tableSearch" border="0" width="100%">				 
				 	<tr>
						<td class="optionTitleBoldRight" nowrap>
							<fmt:message key="label.declaration"/> :
						</td>
						<td class="optionTitleLeft" colspan="3" readonly nowrap>
							<span name="dotPartMaterialBean[${partCount}].adrDeclaration" id="adrDeclaration${partCount}">
  									${dotPartMaterialBean.adrDeclaration}
  							</span>
						</td>
					</tr>		
				 	<tr>
						<td class="optionTitleBoldRight" width="5%" nowrap>
							<fmt:message key="label.adrid"/> :
						</td>
						<td class="optionTitleLeft" width="25%" nowrap>
							<input type="text" name="dotPartMaterialBean[${partCount}].adrId" id="adrId${partCount}" 
								value="${dotPartMaterialBean.adrId}" size="10" class="invGreyInputText" readonly>
							<c:if test="${pagePermission == 'Y'}"><input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="adrBtn" id="adrBtn" value="..." align="right" onClick="getADRInfo('${partCount}');"/></c:if>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
						<td class="optionTitleBoldRight" width="5%" nowrap>
							<fmt:message key="label.identificationnumber"/> :
						</td>
						<td class="optionTitleLeft" width="65%" nowrap>
							<input name="dotPartMaterialBean[${partCount}].adrUnNo" id="adrUnNo${partCount}" type="text" maxlength="6" size="6" 
  									value="${dotPartMaterialBean.adrUnNo}" class="invGreyInputText" readonly/>
						</td>
					</tr>
				 	<tr>
						<td class="optionTitleBoldRight" nowrap>
							<fmt:message key="label.properShippingName"/> :
						</td>
						<td class="optionTitleLeft" colspan="3" nowrap>
							<input name="dotPartMaterialBean[${partCount}].adrProperShippingName" id="adrProperShippingName${partCount}" type="text" maxlength="120" size="120" 
  									value="${dotPartMaterialBean.adrProperShippingName}" class="invGreyInputText" readonly/>
						</td>
					</tr>
					<tr>
						<td class="optionTitleBoldRight" nowrap>
							<fmt:message key="label.technicalName"/> :
						</td>
						<td class="optionTitleLeft" colspan="3" nowrap>
							<c:choose>
								<c:when test="${dotPartMaterialBean.adrTechnicalNameRequired == 'Y' && pagePermission == 'Y'}">
							        <input name="dotPartMaterialBean[${partCount}].adrTechnicalName" id="adrTechnicalName${partCount}" type="text" maxlength="200" size="150" 
  										value="${dotPartMaterialBean.adrTechnicalName}" onkeyup="showTempInfo('adrDeclaration${partCount}');"  class="inputBox"/>
								</c:when>
								<c:otherwise>
							       	<input name="dotPartMaterialBean[${partCount}].adrTechnicalName" id="adrTechnicalName${partCount}" type="text" maxlength="200" size="60" 
  										value="${dotPartMaterialBean.adrTechnicalName}" onkeyup="showTempInfo('adrDeclaration${partCount}');"class="invGreyInputText" readonly/>
								</c:otherwise>
							</c:choose>
							<input type="hidden" name="dotPartMaterialBean[${partCount}].adrTechnicalNameRequired" id="adrTechnicalNameRequired${partCount}" value="${dotPartMaterialBean.adrTechnicalNameRequired}"/>
						</td>
					</tr>
					<tr>
						<td class="optionTitleBoldRight" nowrap>
							<fmt:message key="label.class"/> :
						</td>
						<td class="optionTitleLeft" nowrap>
							<input name="dotPartMaterialBean[${partCount}].adrClass" id="adrClass${partCount}" type="text" maxlength="6" size="6" 
  									value="${dotPartMaterialBean.adrClass}" class="invGreyInputText" readonly />
						</td>
						<td class="optionTitleBoldRight" nowrap>
							<fmt:message key="label.classificationcode"/> :
						</td>
						<td class="optionTitleLeft" nowrap>
							<input name="dotPartMaterialBean[${partCount}].adrClassificationCode" id="adrClassificationCode${partCount}" type="text" maxlength="20" size="20" 
  									value="${dotPartMaterialBean.adrClassificationCode}" class="invGreyInputText" readonly />
						</td>
					</tr>
					<tr>
						<td class="optionTitleBoldRight" nowrap>
							<fmt:message key="label.subsidiaryrisk"/> :
						</td>
						<td class="optionTitleLeft" nowrap>
							<input type="text" name="dotPartMaterialBean[${partCount}].adrSubrisk" id="adrSubrisk${partCount}" 
								value="${dotPartMaterialBean.adrSubrisk}" size="10" maxlength="10" class="invGreyInputText" readonly>
							<c:if test="${pagePermission == 'Y'}">
								<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="dotBtn" id="dotBtn" value="..." align="right" 
									onClick="getSubRisk('adrSubrisk'+'${partCount}', 'ADR');"/>
								<input name="startNewCountBtn" id="startNewCountBtn" type="button" class="smallBtns" value="<fmt:message key="label.clear"/>" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'"
          		 					onclick= "clearField('adrSubrisk'+'${partCount}');"/>
							</c:if>
						</td>
						<td class="optionTitleBoldRight" nowrap>
<%--
							<fmt:message key="label.regsubrisk"/> :
 --%>							
						</td>
						<td class="optionTitleLeft" nowrap>
<%--							problem..<input name="dotPartMaterialBean[${partCount}].iataRegSubrisk" id="iataRegSubrisk${partCount}" type="text" maxlength="20" size="20" 
  										value="${dotPartMaterialBean.iataRegSubrisk}" class="invGreyInputText" readonly/>
--%>  										
						</td>
					</tr>
					<tr>
						<td class="optionTitleBoldRight" nowrap>
							<fmt:message key="label.transportcatalog"/> :
						</td>
						<td class="optionTitleLeft" nowrap>
							<input name="dotPartMaterialBean[${partCount}].adrTransportCategory" id="adrTransportCategory${partCount}" type="text" maxlength="22" size="22" 
  									value="${dotPartMaterialBean.adrTransportCategory}" class="invGreyInputText" readonly />
						</td>
						<td class="optionTitleBoldRight" nowrap>
							<fmt:message key="label.packinggroup"/> :
						</td>
						<td class="optionTitleLeft" nowrap>
							<input name="dotPartMaterialBean[${partCount}].adrPackingGroup" id="adrPackingGroup${partCount}" type="text" maxlength="82" size="30" 
  									value="${dotPartMaterialBean.adrPackingGroup}" class="invGreyInputText" readonly />
						</td>
					</tr>
					<tr>
						<td class="optionTitleBoldRight" nowrap>
							<fmt:message key="label.limitedquantity"/> :
						</td>
						<td class="optionTitleLeft" nowrap>
							<input name="dotPartMaterialBean[${partCount}].adrLimitedQuanity" id="adrLimitedQuanity${partCount}" type="text" maxlength="18" size="18" 
  									value="${dotPartMaterialBean.adrLimitedQuanity}" class="invGreyInputText" readonly />
						</td>
						<td class="optionTitleBoldRight" nowrap>
							<fmt:message key="label.tunnelcode"/> :
						</td>
						<td class="optionTitleLeft" nowrap>
							<input name="dotPartMaterialBean[${partCount}].adrTunnelCode" id="adrTunnelCode${partCount}" type="text" maxlength="6" size="6" 
  									value="${dotPartMaterialBean.adrTunnelCode}" class="invGreyInputText" readonly />
						</td>
					</tr>
					<tr>
						<td class="optionTitleBoldRight" width="5%" nowrap>
							<fmt:message key="label.shippingremarks"/> :
						</td>
						<td class="optionTitleBoldLeft" colspan="3">
							<c:choose>
							  <c:when test="${pagePermission == 'Y'}"> 
								 <TEXTAREA name="dotPartMaterialBean[${partCount}].adrShippingRemark" id="adrShippingRemark${partCount}" onKeyDown='limitText("adrShippingRemark${partCount}", "<fmt:message key="label.shippingremarks"/>", 400);' onKeyUp='limitText("adrShippingRemark${partCount}", "<fmt:message key="label.shippingremarks"/>", 400);' class="inputBox" COLS=150 ROWS=2>${dotPartMaterialBean.adrShippingRemark}</TEXTAREA>
							  </c:when>
							  <c:otherwise>
								 ${dotPartMaterialBean.adrShippingRemark}
							  </c:otherwise>
							</c:choose>  
						</td>
					</tr>
					<tr><td>&nbsp;</td></tr>
					<tr><td>&nbsp;</td></tr>
					<tr><td>&nbsp;</td></tr>
				</table>
  </div>
  <div id="newType3${partCount}" style="display:none;" class="roundContent">    
	    		<table class="tableSearch" border="0" width="100%">				 
				 	<tr>
						<td class="optionTitleBoldRight" width="5%" nowrap>
							<fmt:message key="label.declaration"/> :
						</td>
						<td class="optionTitleLeft" colspan="3" readonly nowrap>
							<span name="dotPartMaterialBean[${partCount}].imdgDeclaration" id="imdgDeclaration${partCount}">
  									${dotPartMaterialBean.imdgDeclaration}
  							</span>
						</td>
					</tr>
				 	<tr>
						<td class="optionTitleBoldRight" width="5%" nowrap>
							<fmt:message key="label.dgid"/> :
						</td>
						<td class="optionTitleLeft" width="25%" nowrap>
							<input name="dotPartMaterialBean[${partCount}].imdgId" id="imdgId${partCount}" type="text" maxlength="5" size="10" 
  									value="${dotPartMaterialBean.imdgId}" class="invGreyInputText" readonly/>
  							<c:if test="${pagePermission == 'Y'}"><input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="imdgBtn" id="imdgBtn" value="..." align="right" onClick="getIMDGInfo('${partCount}');"/></c:if>
  							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
						<td class="optionTitleBoldRight" width="5%" nowrap>
							<fmt:message key="label.identificationnumber"/> :
						</td>
						<td class="optionTitleLeft" width="65%" nowrap>
							<input name="dotPartMaterialBean[${partCount}].imdgUnNumber" id="imdgUnNumber${partCount}" type="text" maxlength="6" size="6" 
  									value="${dotPartMaterialBean.imdgUnNumber}" class="invGreyInputText" readonly/>
						</td>
					</tr>
				 	<tr>
						<td class="optionTitleBoldRight" nowrap>
							<fmt:message key="label.properShippingName"/> :
						</td>
						<td class="optionTitleLeft" colspan="3" nowrap>
							<input name="dotPartMaterialBean[${partCount}].imdgProperShippingName" id="imdgProperShippingName${partCount}" type="text" maxlength="400" size="150" 
  									value="${dotPartMaterialBean.imdgProperShippingName}" onkeyup="showTempInfo('imdgDeclaration${partCount}');" class="invGreyInputText" readonly/>
						</td>
					</tr>
					<tr>
						<td class="optionTitleBoldRight" nowrap>
							<fmt:message key="label.technicalName"/> :
						</td>
						<td class="optionTitleLeft" colspan="3" nowrap>
							<c:choose>
								<c:when test="${dotPartMaterialBean.imdgTechnicalNameRequired == 'Y' && pagePermission == 'Y'}">
							       <input name="dotPartMaterialBean[${partCount}].imdgTechnicalName" id="imdgTechnicalName${partCount}" type="text" maxlength="200" size="150" 
  										value="${dotPartMaterialBean.imdgTechnicalName}" class="inputBox"/>
								</c:when>
								<c:otherwise>
							       	<input name="dotPartMaterialBean[${partCount}].imdgTechnicalName" id="imdgTechnicalName${partCount}" type="text" maxlength="200" size="150" 
  										value="${dotPartMaterialBean.imdgTechnicalName}" class="invGreyInputText" readonly/>
								</c:otherwise>
							</c:choose>
							<input type="hidden" name="dotPartMaterialBean[${partCount}].imdgTechnicalNameRequired" id="imdgTechnicalNameRequired${partCount}" value="${dotPartMaterialBean.imdgTechnicalNameRequired}">
						</td>
					</tr>
					<tr>
						<td class="optionTitleBoldRight" nowrap>
							<fmt:message key="label.classordivision"/> :
						</td>
						<td class="optionTitleLeft" nowrap>
							<input type="text" name="dotPartMaterialBean[${partCount}].imdgClassOrDivision" id="imdgClassOrDivision${partCount}" 
								value="${dotPartMaterialBean.imdgClassOrDivision}"  class="invGreyInputText" readonly size="10" >
						</td>
						<td class="optionTitleBoldRight" nowrap>
							<fmt:message key="label.packinggroup"/> :
						</td>
						<td class="optionTitleLeft" nowrap>
							<input type="text" name="dotPartMaterialBean[${partCount}].imdgPackingGroup" id="imdgPackingGroup${partCount}" 
								value="${dotPartMaterialBean.imdgPackingGroup}"  class="invGreyInputText" readonly size="3"  maxlength="3">
						</td>
					</tr>
					<tr>
						<td class="optionTitleBoldRight" nowrap>
							<fmt:message key="label.subsidiaryrisk"/> :
						</td>
						<td class="optionTitleLeft" nowrap>
							<input type="text" name="dotPartMaterialBean[${partCount}].imdgSubsidiaryRisk" id="imdgSubsidiaryRisk${partCount}" 
								value="${dotPartMaterialBean.imdgSubsidiaryRisk}" size="10" maxlength="10" class="invGreyInputText" readonly>
							<c:if test="${pagePermission == 'Y'}">
								<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="dotBtn" id="dotBtn" value="..." align="right" 
									onClick="getSubRisk('imdgSubsidiaryRisk'+'${partCount}', 'IMDG');"/>
								<input name="startNewCountBtn" id="startNewCountBtn" type="button" class="smallBtns" value="<fmt:message key="label.clear"/>" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'"
          		 					onclick= "clearField('imdgSubsidiaryRisk'+'${partCount}');"/>
							</c:if>
						</td>
						<td class="optionTitleBoldRight" nowrap>
							<fmt:message key="label.regsubrisk"/> :
						</td>
						<td class="optionTitleLeft" nowrap>
							<input name="dotPartMaterialBean[${partCount}].imdgSubrisk" id="imdgSubrisk${partCount}" type="text" maxlength="10" size="10" 
  									value="${dotPartMaterialBean.imdgSubrisk}" class="invGreyInputText" readonly/>
						</td>
					</tr>
					<tr>
						<td class="optionTitleBoldRight" nowrap>
							<fmt:message key="label.shippingremarks"/> :
						</td>
						<td class="optionTitleBoldLeft" colspan="3">
							<c:choose>
							  <c:when test="${pagePermission == 'Y'}"> 
								 <TEXTAREA name="dotPartMaterialBean[${partCount}].imdgShippingRemark" id="imdgShippingRemark${partCount}" onKeyDown='limitText("imdgShippingRemark${partCount}", "<fmt:message key="label.shippingremarks"/>", 400);' onKeyUp='limitText("imdgShippingRemark${partCount}", "<fmt:message key="label.shippingremarks"/>", 400);' class="inputBox" COLS=150 ROWS=2>${dotPartMaterialBean.imdgShippingRemark}</TEXTAREA>
							  </c:when>
							  <c:otherwise>
								 ${dotPartMaterialBean.imdgShippingRemark}
							  </c:otherwise>
							</c:choose>  
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					</tr>
					<tr><td>&nbsp;</td></tr>
					<tr><td>&nbsp;</td></tr>
				</table>
  </div>
  <div id="newType4${partCount}" style="display:none" class="roundContent">
    <table class="tableSearch" border="0" width="100%">				 
	 	<tr>
			<td class="optionTitleBoldRight" width="5%" nowrap>
				<label for="acidBase${partCount}"><fmt:message key="label.acidbase"/>:</label>
			</td>
			<td class="optionTitleLeft" nowrap>
				<select id="acidBase${partCount}" name="dotPartMaterialBean[${partCount}].wmsAcidBase" class="selectBox">
					<option value=""><fmt:message key="label.pleaseselect"/></option>
					<c:forEach var="vv_acidbase" items="${acidBaseChoices}" varStatus="acidBaseStatus">
					<option value="${vv_acidbase}" ${dotPartMaterialBean.wmsAcidBase eq vv_acidbase?'selected':''}>${vv_acidbase}</option>
					</c:forEach>
				</select>
			</td>
			<td class="optionTitleBoldRight" width="5%" nowrap>
				<label for="reactive${partCount}"><fmt:message key="label.reactive"/>:</label>
			</td>
			<td class="optionTitleLeft" nowrap>
				<select id="reactive${partCount}" name="dotPartMaterialBean[${partCount}].wmsReactive" class="selectBox">
					<option value=""><fmt:message key="label.pleaseselect"/></option>
					<c:forEach var="vv_reactive" items="${reactiveChoices}" varStatus="reactiveStatus">
					<option value="${vv_reactive}" ${dotPartMaterialBean.wmsReactive eq vv_reactive?'selected':''}>${vv_reactive}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td class="optionTitleBoldRight" width="5%" nowrap>
				<label for="corrosive${partCount}"><fmt:message key="label.corrosive"/>:</label>
			</td>
			<td class="optionTitleLeft" nowrap>
				<select id="corrosive${partCount}" name="dotPartMaterialBean[${partCount}].wmsCorrosive" class="selectBox">
					<option value=""><fmt:message key="label.pleaseselect"/></option>
					<c:forEach var="vv_corrosive" items="${corrosiveChoices}" varStatus="corrosiveStatus">
					<option value="${vv_corrosive}" ${dotPartMaterialBean.wmsCorrosive eq vv_corrosive?'selected':''}>${vv_corrosive}</option>
					</c:forEach>
				</select>
			</td>
			<td class="optionTitleBoldRight" width="5%" nowrap>
				<label for="waterReactive${partCount}"><fmt:message key="label.waterreactive"/>:</label>
			</td>
			<td class="optionTitleLeft" nowrap>
				<select id="waterReactive${partCount}" name="dotPartMaterialBean[${partCount}].wmsWaterReactive" class="selectBox">
					<option value=""><fmt:message key="label.pleaseselect"/></option>
					<c:forEach var="vv_waterreactive" items="${waterReactiveChoices}" varStatus="waterReactiveStatus">
					<option value="${vv_waterreactive}" ${dotPartMaterialBean.wmsWaterReactive eq vv_waterreactive?'selected':''}>${vv_waterreactive}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td class="optionTitleBoldRight" width="5%" nowrap>
				<label><fmt:message key="label.physicalstate"/>:</label>
			</td>
			<td class="optionTitleLeft" nowrap>
				<span id="physicalState${partCount}">
					<c:out value="${dotPartMaterialBean.physicalState}"/>
				</span>
			</td>
			<td class="optionTitleBoldRight" width="5%" nowrap>
				<label><fmt:message key="label.pyrophoric"/>:</label>
			</td>
			<td class="optionTitleLeft" nowrap>
				<fieldset id="pyrophoric${partCount}" style="border:none">
					<label for="pyrophoricYes${partCount}" style="margin-left:10">
						<input type="radio" id="pyrophoricYes${partCount}" name="dotPartMaterialBean[${partCount}].wmsPyrophoric" 
							class="radioBtns" value="Y" ${dotPartMaterialBean.wmsPyrophoric eq 'Y'?'checked':''}/>
						<fmt:message key="label.yes"/>
					</label>
					<label for="pyrophoricNo${partCount}" style="margin-left:10">
						<input type="radio" id="pyrophoricNo${partCount}" name="dotPartMaterialBean[${partCount}].wmsPyrophoric" 
							class="radioBtns" value="N" ${dotPartMaterialBean.wmsPyrophoric eq 'N'?'checked':''}/>
						<fmt:message key="label.no"/>
					</label>
				</fieldset>
			</td>
		</tr>
		<tr>
			<td class="optionTitleBoldRight" width="5%" nowrap>
				<label for="aerosol${partCount}"><fmt:message key="label.aerosol"/>:</label>
			</td>
			<td class="optionTitleLeft" nowrap>
				<select id="aerosol${partCount}" name="dotPartMaterialBean[${partCount}].wmsAerosol" class="selectBox">
					<option value=""><fmt:message key="label.pleaseselect"/></option>
					<c:forEach var="vv_aerosol" items="${aerosolChoices}" varStatus="aerosolStatus">
					<option value="${vv_aerosol}" ${dotPartMaterialBean.wmsAerosol eq vv_aerosol?'selected':''}>${vv_aerosol}</option>
					</c:forEach>
				</select>
			</td>
			<td class="optionTitleBoldRight" width="5%" nowrap>
				<label for="organicPeroxide${partCount}"><fmt:message key="label.organicperoxide"/>:</label>
			</td>
			<td class="optionTitleLeft" nowrap>
				<select id="organicPeroxide${partCount}" name="dotPartMaterialBean[${partCount}].wmsOrganicPeroxide" class="selectBox">
					<option value=""><fmt:message key="label.pleaseselect"/></option>
					<c:forEach var="vv_organicperoxide" items="${organicPeroxideChoices}" varStatus="organicPeroxideStatus">
					<option value="${vv_organicperoxide}" ${dotPartMaterialBean.wmsOrganicPeroxide eq vv_organicperoxide?'selected':''}>${vv_organicperoxide}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td class="optionTitleBoldRight" width="5%" nowrap>
				<label for="flammable${partCount}"><fmt:message key="label.flammable"/>:</label>
			</td>
			<td class="optionTitleLeft" nowrap>
				<select id="flammable${partCount}" name="dotPartMaterialBean[${partCount}].wmsFlammable" class="selectBox">
					<option value=""><fmt:message key="label.pleaseselect"/></option>
					<c:forEach var="vv_flammable" items="${flammableChoices}" varStatus="flammableStatus">
					<option value="${vv_flammable}" ${dotPartMaterialBean.wmsFlammable eq vv_flammable?'selected':''}>${vv_flammable}</option>
					</c:forEach>
				</select>
			</td>
			<td class="optionTitleBoldRight" width="5%" nowrap>
				<label for="containerMaterial${partCount}"><fmt:message key="label.containermaterial"/>:</label>
			</td>
			<td class="optionTitleLeft" nowrap>
				<select id="containerMaterial${partCount}" name="dotPartMaterialBean[${partCount}].wmsContainer" class="selectBox">
					<option value=""><fmt:message key="label.pleaseselect"/></option>
					<c:forEach var="vv_containermaterial" items="${containerMaterialChoices}" varStatus="containerMaterialStatus">
					<option value="${vv_containermaterial}" ${dotPartMaterialBean.wmsContainer eq vv_containermaterial?'selected':''}>${vv_containermaterial}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td class="optionTitleBoldRight" width="5%" nowrap>
				<label><fmt:message key="label.watermiscible"/>:</label>
			</td>
			<td class="optionTitleLeft" nowrap>
				<fieldset id="waterMiscible${partCount}" style="border:none">
					<label for="waterMiscibleYes${partCount}" style="margin-left:10">
						<input type="radio" id="waterMiscibleYes${partCount}" name="dotPartMaterialBean[${partCount}].wmsWaterMiscible" 
						class="radioBtns" value="Y" ${dotPartMaterialBean.wmsWaterMiscible eq 'Y'?'checked':''}/>
						<fmt:message key="label.yes"/>
					</label>
					<label for="waterMiscibleNo${partCount}" style="margin-left:10">
						<input type="radio" id="waterMiscibleNo${partCount}" name="dotPartMaterialBean[${partCount}].wmsWaterMiscible" 
						class="radioBtns" value="N" ${dotPartMaterialBean.wmsWaterMiscible eq 'N'?'checked':''}/>
						<fmt:message key="label.no"/>
					</label>
				</fieldset>
			</td>
			<td class="optionTitleBoldRight" width="5%" nowrap>
				<label><fmt:message key="label.containerpressure"/>:</label>
			</td>
			<td class="optionTitleLeft" nowrap>
				<fieldset id="containerPressure${partCount}" style="border:none">
					<label for="containerPressureYes${partCount}" style="margin-left:10">
						<input type="radio" id="containerPressureYes${partCount}" name="dotPartMaterialBean[${partCount}].wmsContPressureRelieving" 
						class="radioBtns" value="Y" ${dotPartMaterialBean.wmsContPressureRelieving eq 'Y'?'checked':''}/>
						<fmt:message key="label.yes"/>
					</label>
					<label for="containerPressureNo${partCount}" style="margin-left:10">
						<input type="radio" id="containerPressureNo${partCount}" name="dotPartMaterialBean[${partCount}].wmsContPressureRelieving" 
						class="radioBtns" value="N" ${dotPartMaterialBean.wmsContPressureRelieving eq 'N'?'checked':''}/>
						<fmt:message key="label.no"/>
					</label>
				</fieldset>
			</td>
		</tr>
		<tr>
			<td class="optionTitleBoldRight" width="5%" nowrap>
				<label for="toxic${partCount}"><fmt:message key="label.toxic"/>:</label>
			</td>
			<td class="optionTitleLeft" nowrap>
				<select id="toxic${partCount}" name="dotPartMaterialBean[${partCount}].wmsToxic" class="selectBox">
					<option value=""><fmt:message key="label.pleaseselect"/></option>
					<c:forEach var="vv_toxic" items="${toxicChoices}" varStatus="toxicStatus">
					<option value="${vv_toxic}" ${dotPartMaterialBean.wmsToxic eq vv_toxic?'selected':''}>${vv_toxic}</option>
					</c:forEach>
				</select>
			</td>
			<td class="optionTitleBoldRight" width="5%" nowrap>
				<label for="oxidizer${partCount}"><fmt:message key="label.oxidizer"/>:</label>
			</td>
			<td class="optionTitleLeft" nowrap>
				<select id="oxidizer${partCount}" name="dotPartMaterialBean[${partCount}].wmsOxidizer" class="selectBox">
					<option value=""><fmt:message key="label.pleaseselect"/></option>
					<c:forEach var="vv_oxidizer" items="${oxidizerChoices}" varStatus="oxidizerStatus">
					<option value="${vv_oxidizer}" ${dotPartMaterialBean.wmsOxidizer eq vv_oxidizer?'selected':''}>${vv_oxidizer}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
	</table>
  </div>
</td></tr>
</table>

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
</c:if>
</div>
<!-- resultDiv Ends -->
</div>
<!-- Whole page Ends -->
</div>
<!-- content Area Ends -->

<!-- Error Messages Begins -->
<!-- Build this section only if there is an error message to display.
     For the search section, we show the error messages within its frame
-->
<div id="errorMessagesArea" class="errorMessages" style="display:none;z-index:10;">
</div>
<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input type="hidden" name="uAction" id="uAction" value=""/>
<input type="hidden" name="totalLines" id="totalLines" value="${dataCount}" >
<input type="hidden" name="partCount" id="partCount" value='${partCount}'/>
<input type="hidden" name="displayElement" id="displayElement" value=''/>
</div>
<!-- Hidden elements end -->

<div id="hiddenDesc" style="visibility: hidden;position: absolute;z-index: 2;font:8pt sans-serif;border: solid 1px;background-color:white;">
${dotPartMaterialColl[0].itemDesc}
</div> 

<script language="JavaScript" type="text/javascript">
<!--
function startSubOnload()
{
  if($v("partCount") > 0*1) {
	for(var i=0 ; i < $v("partCount"); i++) {
	 openSubTab(0,'','<fmt:message key="label.dot"/>','','',i);
	 openSubTab(1,'','<fmt:message key="label.iata"/>','','',i);
	 openSubTab(2,'','<fmt:message key="label.adr"/>','','',i);
	 openSubTab(3,'','<fmt:message key="label.imdg"/>','','',i);
	 openSubTab(4,'','<fmt:message key="label.storage"/>','','',i);
	}
  }  
}
// -->
</script>
</div>	
</tcmis:form>

<!-- close of contentArea -->



</body>
</html:html>