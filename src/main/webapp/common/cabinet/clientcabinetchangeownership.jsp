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

<%@ include file="/common/locale.jsp" %>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss />
<!-- CSS for YUI -->
<%--
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>
<!-- Add any other stylesheets you need for the page here -->

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

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
<script type="text/javascript" src="/js/calendar/calendarval.js"></script>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/cabinet/clientcabinetchangeownership.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<%--<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<!-- This is for the YUI, uncomment if you will use this -->
<%-- <script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script> --%>

<!--  These are for the autocomplete function -->
<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script> 
<script type="text/javascript" src="/js/jquery/autocomplete.js"></script> 
<link rel="stylesheet" type="text/css" href="/css/autocomplete.css" />  
  
  
<title>
<fmt:message key="label.changeownership"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
var showErrorMessage = false;
<c:if test="${not empty tcmisError}">
    showErrorMessage = true;
</c:if>

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={
		alert:"<fmt:message key="label.alert"/>",
		and:"<fmt:message key="label.and"/>",
		part:"<fmt:message key="label.part"/>",
		For:"<fmt:message key="label.For"/>",
		changeownership:"<fmt:message key="label.changeownership"/>",
		saving:"<fmt:message key="label.saving"/>",
		integerError:"<fmt:message key="errors.integer"/>",
		reorderPoint:"<fmt:message key="label.reorderpoint"/>",
		stockingLevel:"<fmt:message key="label.stockinglevel"/>",
		kanbanReorderQuantity:"<fmt:message key="label.kanbanreorderqty"/>",
		reorderQuantity:"<fmt:message key="label.reorderquantity"/>",
		leadTimeInDays:"<fmt:message key="label.leadtimeindays"/>",
		reorderPointLessThanStockingLevel:"<fmt:message key="error.reorderpoint.lessthanstockinglevel"/>",
		reorderPointGreaterThanZero:"<fmt:message key="label.reorderpointgreaterthanzero"/>",
		submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"
		};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="onLoad();" onunload="opener.parent.closeTransitWin();">
<tcmis:form action="/clientcabinetmanagementmain.do" onsubmit="return submitOnlyOnce();">
 <div id="transitDialogWin" class="optionTitleBoldCenter" style="display: none;">
  <br><br><br><fmt:message key="label.pleasewait"/>
 </div>
 
<div class="contentArea">
<!-- Search Option Begins -->
<table id="searchMaskTable" width="300" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
	 
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
        <tr>
	    	<td width="100%" colspan="2" class="optionTitleBoldLeft" nowrap><fmt:message key="label.changeownership"/></td>
        </tr>
        <tr>
	    	<td width="20%" class="optionTitleBoldRight" nowrap><fmt:message key="label.facility"/>:&nbsp;</td>
	        <td width="80%" class="optionTitleLeft"><c:out value="${cabinetPartLevelViewBean.facilityName}" escapeXml="false"/></td>	        
        </tr>
      	<tr>
        	<td width="20%" class="optionTitleBoldRight" nowrap><fmt:message key="label.workarea"/>:&nbsp;</td>
        	<td width="80%" class="optionTitleLeft"><c:out value="${cabinetPartLevelViewBean.applicationDesc}"/></td>
      	</tr>
      	<tr>
        	<td width="20%" class="optionTitleBoldRight" nowrap><fmt:message key="label.partnumber"/>:&nbsp;</td>
        	<td width="80%" class="optionTitleLeft"><c:out value="${cabinetPartLevelViewBean.catPartNo}"/></td>
      	</tr>
        <tr>
        	<td width="20%" class="optionTitleBoldRight" nowrap><fmt:message key="label.ownership"/>:</td>
        	<td width="80%" class="optionTitleLeft">
        		<select name="hcoFlag" id="hcoFlag" class="selectBox" onchange="displayDropShipOverride()">
                    <option value="N" <c:if test='${"Y" eq cabinetPartLevelViewBean.hcoFlag}'> selected</c:if>><fmt:message key="label.haas"/></option>
					<option value="Y" <c:if test='${"N" eq cabinetPartLevelViewBean.hcoFlag}'> selected</c:if>>${cabinetPartLevelViewBean.companyId}</option>
				</select>
        	</td>
      	</tr>
        <tr>
        	<td width="20%" class="optionTitleBoldRight" nowrap><fmt:message key="label.reorderpoint"/>:</td>
        	<td width="80%" class="optionTitleLeft">
        		<input name="reorderPoint" id="reorderPoint" type="text" class="inputBox" size="20" maxlength="30"  value="${cabinetPartLevelViewBean.reorderPoint}"/>
        	</td>
      	</tr>
      		<tr>
        	<td width="20%" class="optionTitleBoldRight" nowrap><fmt:message key="label.stockinglevel"/>:</td>
        	<td width="80%" class="optionTitleLeft">
        		<input name="stockingLevel" id="stockingLevel" type="text" class="inputBox" size="20" maxlength="30"  value="${cabinetPartLevelViewBean.stockingLevel}"/>
        	</td>
      	</tr>
      		<tr>
        	<td width="20%" class="optionTitleBoldRight" nowrap><fmt:message key="label.kanbanreorderqty"/>:</td>
        	<td width="80%" class="optionTitleLeft">
        		<input name="kanbanReorderQuantity" id="kanbanReorderQuantity" type="text" class="inputBox" size="20" maxlength="30"  value="${cabinetPartLevelViewBean.kanbanReorderQuantity}"/>
        	</td>
      	</tr>
      		<tr>
        	<td width="20%" class="optionTitleBoldRight" nowrap><fmt:message key="label.reorderquantity"/>:</td>
        	<td width="80%" class="optionTitleLeft">
        		<input name="reorderQuantity" id="reorderQuantity" type="text" class="inputBox" size="20" maxlength="30"  value="${cabinetPartLevelViewBean.reorderQuantity}"/>
        	</td>
      	</tr>
      	<tr>
        	<td width="20%" class="optionTitleBoldRight" nowrap><fmt:message key="label.leadtimeindays"/>:</td>
        	<td width="80%" class="optionTitleLeft">
        		<input name="leadTimeDays" id="leadTimeDays" type="text" class="inputBox" size="20" maxlength="30"  value="${cabinetPartLevelViewBean.leadTimeDays}"/>
        	</td>
      	</tr>
    	<tr>
        	<td width="20%" class="optionTitleBoldRight" nowrap><fmt:message key="label.counttype"/>:</td>
        	<td width="80%" class="optionTitleLeft">
        		<select name="countType" id="countType" class="selectBox" onchange="displayDropShipOverride()">
                    <c:if test="${cabinetPartLevelViewBean.countType == 'RECEIPT_ID'}">
                        <option value="ITEM_ID" <c:if test='${"ITEM_ID" eq cabinetPartLevelViewBean.countType}'> selected</c:if>><fmt:message key="label.itemid"/></option>
                        <c:if test='${"Y" eq cabinetPartLevelViewBean.hcoFlag}'>
                            <option value="KanBan" <c:if test='${"KanBan" eq cabinetPartLevelViewBean.countType}'> selected</c:if>><fmt:message key="label.kanban"/></option>
                        </c:if>
                        <option value="RECEIPT_ID" <c:if test='${"RECEIPT_ID" eq cabinetPartLevelViewBean.countType}'> selected</c:if>><fmt:message key="label.receiptid"/></option>
                    </c:if>
                    <c:if test="${cabinetPartLevelViewBean.countType != 'RECEIPT_ID'}">
                         <c:if test="${cabinetPartLevelViewBean.facilityId == 'STL Prod'}">
                            <option value="CONTAINER" <c:if test='${"CONTAINER" eq cabinetPartLevelViewBean.countType}'> selected</c:if>><fmt:message key="label.containerid"/></option> --%>
                        </c:if>
                        <option value="ITEM_ID" <c:if test='${"ITEM_ID" eq cabinetPartLevelViewBean.countType}'> selected</c:if>><fmt:message key="label.itemid"/></option>
                        <c:if test='${"Y" eq cabinetPartLevelViewBean.hcoFlag}'>
                            <option value="KanBan" <c:if test='${"KanBan" eq cabinetPartLevelViewBean.countType}'> selected</c:if>><fmt:message key="label.kanban"/></option>
                        </c:if>
                    </c:if>
                </select>
        	</td>
      	</tr>
        <tr id="dropShipOverrideTr" style="display:none">
            <td class="optionTitleBoldRight">&nbsp;</td>
            <td class="optionTitleBoldLeft">
                <input name="dropShipOverride" id="dropShipOverride" type="checkbox" class="radioBtns" value="Y">
                <fmt:message key="label.dropship"/>
            </td>
        </tr>
        <tr>
			<td width="15%" colspan="2">
				<br/>
				<input name="submitBtn" type="button" class="inputBtns" value="<fmt:message key="button.submit"/>" id="submitBtn" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
		          		  onclick= "submitSave()">
		        <input name="cancelBtn" type="button" class="inputBtns" value="<fmt:message key="label.cancel"/>" id="cancelBtn" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
		          		 onclick= "cancel()"/>
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
</td></tr>
</table>
<!-- Search Option Ends -->

<div class="spacerY">&nbsp;</div>

<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none; ">
	${tcmisError}
</div>
<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;">
 <input name="uAction" id="uAction" type="hidden" value="${param.uAction}" />
 <input name="ownershipName" id="ownershipName" type="hidden" value="${cabinetPartLevelViewBean.ownershipName}"/>
 <input name="binId" id="binId" type="hidden" value="${cabinetPartLevelViewBean.binId}"/>
 <input name="companyId" id="companyId" type="hidden" value="${cabinetPartLevelViewBean.companyId}"/>
 <input name="facilityId" id="facilityId" type="hidden" value="${cabinetPartLevelViewBean.facilityId}"/>
 <input name="catalogId" id="catalogId" type="hidden" value="${cabinetPartLevelViewBean.catalogId}"/>
 <input name="catalogCompanyId" id="catalogCompanyId" type="hidden" value="${cabinetPartLevelViewBean.catalogCompanyId}"/>
 <input name="application" id="application" type="hidden" value="${cabinetPartLevelViewBean.application}"/>
 <input name="catPartNo" id="catPartNo" type="hidden" value="${cabinetPartLevelViewBean.catPartNo}"/>
 <input name="partGroupNo" id="partGroupNo" type="hidden" value="${cabinetPartLevelViewBean.partGroupNo}"/>
 <input name="facilityName" id="facilityName" type="hidden" value="${cabinetPartLevelViewBean.facilityName}"/>
 <input name="cabinetName" id="cabinetName" type="hidden" value="${cabinetPartLevelViewBean.cabinetName}"/>
 <input name="applicationDesc" id="applicationDesc" type="hidden" value="${cabinetPartLevelViewBean.applicationDesc}"/>
 </div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->
</tcmis:form>
</body>
</html:html>