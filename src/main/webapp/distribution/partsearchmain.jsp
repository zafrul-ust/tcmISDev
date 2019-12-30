<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

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
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<%--NEW--%>
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
<script type="text/javascript" src="/js/distribution/partsearchmain.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<title>
${param.curpath} &gt; <fmt:message key="label.addline"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",all:"<fmt:message key="label.all"/>",
showlegend:"<fmt:message key="label.showlegend"/>",
errors:"<fmt:message key="label.errors"/>",  
validvalues:"<fmt:message key="label.validvalues"/>",
searchInputInteger:"<fmt:message key="error.searchInput.integer"/>", 
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
pleasewait:"<fmt:message key="label.pleasewait"/>",	
itemInteger:"<fmt:message key="error.item.integer"/>",
pleaseSelect:"<fmt:message key="error.norowselected"/>"};



var returnSelectedValue = null;
var returnSelectedDesc  = null;
var returnSelectedPrice  = null;

var preinv = '${param.inventoryGroup}';

<c:set var="igCount" value="0"/>
var count = 0 ;
<c:forEach var="nouse0" items="${personnelBean.opsHubIgOvBeanCollection}" varStatus="status">
<%--<tcmis:opsEntityPermission opsEntityId="${status.current.opsEntityId}" indicator="true" userGroupId="GenerateOrders">--%>
<c:forEach var="nouse1" items="${status.current.hubIgCollection}" varStatus="status1">
  <c:forEach var="nouse2" items="${status1.current.inventoryGroupCollection}" varStatus="status2">
    //alert('${status2.current.inventoryGroup}'+":"+'${status2.current.inventoryGroupName}');
   <tcmis:inventoryGroupPermission indicator="true" userGroupId="GenerateOrders" inventoryGroup="${status2.current.inventoryGroup}">
 	count++;
   </tcmis:inventoryGroupPermission>
  </c:forEach>
</c:forEach>
<%--</tcmis:opsEntityPermission>--%>
</c:forEach>

var igarr = 
new Array(
<c:forEach var="nouse0" items="${personnelBean.opsHubIgOvBeanCollection}" varStatus="status">
<%--<tcmis:opsEntityPermission opsEntityId="${status.current.opsEntityId}" indicator="true" userGroupId="GenerateOrders">--%>
  <c:if  test="${ status.current.opsEntityId == param.opsEntityId }"> 
	 <c:forEach var="nouse1" items="${status.current.hubIgCollection}" varStatus="status1">
	  <c:forEach var="nouse2" items="${status1.current.inventoryGroupCollection}" varStatus="status2">
		<tcmis:inventoryGroupPermission indicator="true" userGroupId="GenerateOrders" inventoryGroup="${status2.current.inventoryGroup}">
		<c:if test="${ igCount !=0 }">,</c:if>
	   {
		value:'${status2.current.inventoryGroup}',
		text:'<tcmis:jsReplace value="${status2.current.inventoryGroupName}"/>'
	   }
	   <c:set var="igCount" value="${igCount+1}"/>
	   </tcmis:inventoryGroupPermission>
	 </c:forEach>
	</c:forEach>
  </c:if>
<%--</tcmis:opsEntityPermission>--%>
</c:forEach>
);

function setup() {
	var eleName = 'inventoryGroup';
	   var obj = $(eleName);
	   for (var i = obj.length; i-- > 0;)
	     obj[i] = null;
	   
	   var selectedIndex =0;
	   for(i=0;i< igarr.length;i++) {
				setOption(i,igarr[i].text,igarr[i].value, eleName);
				if( igarr[i].value == preinv )
					selectedIndex = i;
	   } 
   	   obj.selectedIndex = selectedIndex;
}

function doF6() {
	if(resultFrame.selectedRowId != null)
		resultFrame.doF6();
}

function doF10() {
	if(resultFrame.selectedRowId != null)
		resultFrame.doF10();
}

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="setup();loadLayoutWin('','partSearch');closeAllchildren();document.genericForm.partNumber.focus();" onresize="resizeFrames()" onunload="closeAllchildren();try{opener.closeTransitWin();}catch(ex){}" >

<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<%--NEW - removed the search frame and copied the search section here--%>
<div class="contentArea">
<tcmis:form action="/partsearchresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
    <table width="800" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
     <%--     Row 1    --%>
      <tr>
        <td width="2%" class="optionTitleBoldRight" ><fmt:message key="label.partnumber" />:</td> 
        <td width="45%" class="optionTitleLeft" nowrap> 
          <select name="searchPartNumberMode" id="searchPartNumberMode" class="selectBox">
            <option value="is"><fmt:message key="label.is"/></option>
            <option value="like" selected="selected"><fmt:message key="label.contains"/></option>
            <option value="startsWith"><fmt:message key="label.startswith"/></option>
            <option value="endsWith"><fmt:message key="label.endswith"/></option>
          </select>         
          <input class="inputBox" type="text" name="partNumber" id="partNumber" size="15" onkeypress="return onKeyPress()">
        <td width="5%" class="optionTitleBoldRight" nowrap><fmt:message key="label.customerpartnumber" />:</td> 
        <td width="45%" class="optionTitleLeft" nowrap>
          <select name="searchCustomerPartNumberMode" id="searchCustomerPartNumberMode" class="selectBox">
            <option value="is"><fmt:message key="label.is"/></option>
            <option value="like" selected="selected"><fmt:message key="label.contains"/></option>
            <option value="startsWith"><fmt:message key="label.startswith"/></option>
            <option value="endsWith"><fmt:message key="label.endswith"/></option>
          </select>
          <input class="inputBox" type="text" name="customerPartNumber" id="customerPartNumber" size="15" onkeypress="return onKeyPress()">
        </td>
      </tr>
      
      <%--     Row 2    --%>
      <tr>
        <td width="2%" class="optionTitleBoldRight" nowrap><fmt:message key="label.partdescription"/>:</td> 
        <td width="45%" class="optionTitleLeft" nowrap>
          <select name="searchPartDescMode" id="searchPartDescMode" class="selectBox">
            <option value="is"><fmt:message key="label.is"/></option>
            <option value="like" selected="selected"><fmt:message key="label.contains"/></option>
            <option value="startsWith"><fmt:message key="label.startswith"/></option>
            <option value="endsWith"><fmt:message key="label.endswith"/></option>
          </select>           
          <input class="inputBox" type="text" name="partDesc" id="partDesc" size="15" onkeypress="return onKeyPress()"></input>
        </td>
        <td width="5%" class="optionTitleBoldRight" ><fmt:message key="label.text" />:</td> 
        <td width="45%" class="optionTitleLeft" nowrap>
          <select name="searchTextMode" id="searchTextMode" class="selectBox">
            <option value="is"><fmt:message key="label.is"/></option>
            <option value="like" selected="selected"><fmt:message key="label.contains"/></option>
            <option value="startsWith"><fmt:message key="label.startswith"/></option>
            <option value="endsWith"><fmt:message key="label.endswith"/></option>
          </select>
          <input class="inputBox" type="text" name="text" id="text" size="15" onkeypress="return onKeyPress()">
        </td>
      </tr>
      
      <%--     Row 3    --%>
      <tr>
        <td width="2%" class="optionTitleBoldRight" ><fmt:message key="label.specification"/>:</td> 
        <td width="45%" class="optionTitleLeft" nowrap>
          <select name="searchSpecificationMode" id="searchSpecificationMode" class="selectBox">
            <option value="is"><fmt:message key="label.is"/></option>
            <option value="like" selected="selected"><fmt:message key="label.contains"/></option>
            <option value="startsWith"><fmt:message key="label.startswith"/></option>
            <option value="endsWith"><fmt:message key="label.endswith"/></option>
          </select>           
          <input class="inputBox" type="text" name="specification" id="specification" size="15" onkeypress="return onKeyPress()"></input>
        </td>
        <td width="5%" class="optionTitleBoldRight" ><fmt:message key="label.synonym" />:</td> 
        <td width="45%" class="optionTitleLeft" nowrap>
          <select name="searchAlternateMode" id="searchAlternateMode" class="selectBox">
            <option value="is"><fmt:message key="label.is"/></option>
            <option value="like" selected="selected"><fmt:message key="label.contains"/></option>
            <option value="startsWith"><fmt:message key="label.startswith"/></option>
            <option value="endsWith"><fmt:message key="label.endswith"/></option>
          </select>
          <input class="inputBox" type="text" name="alternate" id="alternate" size="15" onkeypress="return onKeyPress()">
        </td>
      </tr>
       <tr>
         <td width="5%" class="optionTitleBoldRight" ><fmt:message key="label.inventorygroup" />:</td>
        <td width="45%" class="optionTitleLeft" nowrap>
      <%--     Row 4
          <select name="autoAllocationIgMode" id="autoAllocationIgMode" class="selectBox">
            <option value="is"><fmt:message key="label.is"/></option>
            <option value="like" selected="selected"><fmt:message key="label.contains"/></option>
            <option value="startsWith"><fmt:message key="label.startswith"/></option>
            <option value="endsWith"><fmt:message key="label.endswith"/></option>
          </select>
      --%>
          <select name="inventoryGroup" id="inventoryGroup" class="selectBox">
          </select>
      
        </td>
          </tr>
      <%--     Row 4    --%>
      <tr>       
        <td class="optionTitleLeft" colspan="6" nowrap>				
			<input type="submit" name="submitSearch" id="submitSearch" value="<fmt:message key="label.search"/>" class="inputBtns"
				onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" 
				onclick="return submitSearchForm();" />
			<input name="close" type="button" class="inputBtns" value="<fmt:message key="label.close"/>" id="close" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "javascript:closeAllchildren();window.close();"/>
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
<div id="hiddenElements" style="display:;">
<input name="uAction" id="uAction" type="hidden" value="${param.uAction}"/>
<input name="customerId" id="customerId" type="hidden" value="${param.customerId}"/>
<input name="companyId" id="companyId" type="hidden" value="${param.companyId}"/>
<input name="shipToLocationId" id="shipToLocationId" type="hidden" value="${param.shipToLocationId}"/>
<input name="priceGroupId" id="priceGroupId" type="hidden" value="${param.priceGroupId}"/>
<input name="currencyId" id="currencyId" type="hidden" value="${param.currencyId}"/>
<input name="selectedInventoryGroup" id="selectedInventoryGroup" type="hidden" value=""/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
<input name="curpath" id="curpath" type="hidden" value="${param.curpath} > <fmt:message key="label.addline"/>"/>
<input name="fromCustomerReturn" id="fromCustomerReturn" type="hidden" value="${param.fromCustomerReturn}"/>
<input name="valElementId" id="valElementId" type="hidden" value="${param.valElementId}"/>
<input name="valElement2Id" id="valElement2Id" type="hidden" value="${param.valElement2Id}"/>
<input name="valElement3Id" id="valElement3Id" type="hidden" value="${param.valElement3Id}"/>

<!-- The following hidden inputs are for adding a line to Scratch Pad --> 
<input name="orderType" id="orderType" type="hidden" value="${param.orderType}"/>
<input name="prNumber" id="prNumber" type="hidden" value="${param.prNumber}"/>
<input name="lineItem" id="lineItem" type="hidden" value="${param.lineItem}"/>
<input name="facilityId" id="facilityId" type="hidden" value="${param.facilityId}"/>
<input name="shipToCompanyId" id="shipToCompanyId" type="hidden" value="${param.shipToCompanyId}"/>
<input name="shipToLocationId" id="shipToLocationId" type="hidden" value="${param.shipToLocationId}"/>
<input name="opsEntityId" id="opsEntityId" type="hidden" value="${param.opsEntityId}"/>
<input name="poNumber" id="poNumber" type="hidden" value="${param.poNumber}"/>
<input name="opsCompanyId" id="opsCompanyId" type="hidden" value="${param.opsCompanyId}"/>
<input name="requiredShelfLife" id="requiredShelfLife" type="hidden" value="${param.requiredShelfLife}"/>
<input name="shipComplete" id="shipComplete" type="hidden" value="${param.shipComplete}"/>
<input name="mvItem" id="mvItem" type="hidden" value="${param.mvItem}"/>
</div>
<!-- Hidden elements end -->

<!-- Error Messages Ends -->
</tcmis:form>
</div> <!-- close of contentArea -->

</div>
<!-- Search Frame Ends -->

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">

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

<%--NEw -Transit Page Starts --%>
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->

<div id="resultGridDiv" style="display: none;">
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
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
        <span id="selectedPart" style="display:none">&nbsp;</span>
        <span id="addAndContinue" style="display:none"><a href="javascript:add('continue')"><fmt:message key="label.addAndContinue"/></a></span>
        <span id="addAndClose" style="display:none">&nbsp;|&nbsp;<a href="javascript:add('close')"><fmt:message key="label.addAndClose"/></a>&nbsp;|&nbsp;</span>
       <c:if test="${param.fromCustomerReturn ne 'Yes' }"> 
		  <span id="searchGlobalItemSpan" style="display:none"><a href="javascript:searchGlobalCatalog()"><fmt:message key="label.searchglobalitem"/></a></span>
	  	</c:if>
	  </div>
    </div> <%-- boxhead Ends --%>

    <div class="dataContent">
     <iframe scrolling="no" id="resultFrame" name="resultFrame" width="100%" height="300" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
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

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>

</body>
</html:html>