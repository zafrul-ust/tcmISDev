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
<%@ include file="/common/opshubig.jsp" %>
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss overflow="notHidden"/>
<!-- CSS for YUI -->
<%--
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>
<!-- Add any other stylesheets you need for the page here -->

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<script src="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js" language="JavaScript"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>

<%-- Add any other stylesheets you need for the page here --%>


<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<script src="/js/hub/receiving.js" language="JavaScript"></script>
<!-- For Calendar support -->
<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>

<%-- Add any other javascript you need for the page here --%>


<%-- These are for the Grid, uncomment if you are going to use the grid --%>
<%--<script src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>

<%-- This is for the YUI, uncomment if you will use this --%>
<%--<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>--%>

<title>
<fmt:message key="label.receiving"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
quantityreceived:"<fmt:message key="label.quantityreceived"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
dor:"<fmt:message key="receivedreceipts.label.dor"/>",
expiredate:"<fmt:message key="label.expiredate"/>",
all:"<fmt:message key="label.all"/>",
mfglot:"<fmt:message key="label.mfglot"/>",
forreceipt:"<fmt:message key="label.forreceipt"/>",
incoming:"<fmt:message key="label.incoming"/>",
transferreceiptid:"<fmt:message key="label.transferreceiptid"/>",
bin:"<fmt:message key="label.bin"/>",
qtybeingreceived:"<fmt:message key="label.qtybeingreceived"/>",
qtyreceivednotmatch:"<fmt:message key="label.qtyreceivednotmatch"/>",
packagedqtyreceived:"<fmt:message key="label.packagedqtyreceived"/>",
checkpackagedsize:"<fmt:message key="label.checkpackagedsize"/>",
lotstatus:"<fmt:message key="label.lotstatus"/>",
cannotbe:"<fmt:message key="label.cannotbe"/>",
incoming:"<fmt:message key="label.incoming"/>",
supplieref:"<fmt:message key="receiving.label.supplieref"/>",
nopermissionstoqcstatus:"<fmt:message key="label.nopermissionstoqcstatus"/>",
cannotselectreceiptwith:"<fmt:message key="label.cannotselectreceiptwith"/>",
differentmlitem:"<fmt:message key="label.differentmlitem"/>",
pendingnewchemrequest:"<fmt:message key="label.pendingnewchemrequest"/>",
nopermissiontochangestatus:"<fmt:message key="label.nopermissiontochangestatus"/>",
cannotselectreceiptwith:"<fmt:message key="label.cannotselectreceiptwith"/>",
differentmlitem:"<fmt:message key="label.differentmlitem"/>",
mustBeInteger:"<fmt:message key="label.errorinteger"/>",
mustbeanumberinthisfield:"<fmt:message key="label.mustbeanumberinthisfield"/>",
pendingnewchemrequest:"<fmt:message key="label.pendingnewchemrequest"/>",
showlegend:"<fmt:message key="label.showlegend"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
// -->
</script>

<script language="JavaScript" type="text/javascript">
<!--

defaults.ops.nodefault = true;
defaults.hub.nodefault = true;
/*
var althubid = new Array(
<c:forEach var="hubInventoryGroupOvBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,"<c:out value="${status.current.branchPlant}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status.current.branchPlant}"/>"
   </c:otherwise>
  </c:choose>
</c:forEach>
);
var altinvid = new Array();
var altinvName = new Array();
<c:forEach var="hubInventoryGroupOvBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
<c:set var="currentHub" value='${status.current.branchPlant}'/>
<c:set var="inventoryGroupDefinitions" value='${status.current.inventoryGroupCollection}'/>

altinvid["<c:out value="${currentHub}"/>"] = new Array(""
 <c:forEach var="inventoryGroupObjBean" items="${inventoryGroupDefinitions}" varStatus="status1">
,"<c:out value="${status1.current.inventoryGroup}"/>"
  </c:forEach>

  );
altinvName["<c:out value="${currentHub}"/>"] = new Array(messagesData.all
 <c:forEach var="inventoryGroupObjBean" items="${inventoryGroupDefinitions}" varStatus="status1">
,"<c:out value="${status1.current.inventoryGroupName}"/>"
  </c:forEach>

  );
 </c:forEach>
 */
with(milonic=new menuname("showPreviousReceipt")){
 top="offset=2"
 style = contextStyle;
 margin=3
 aI("text=<fmt:message key="receivinghistory.label.potitle"/>;url=javascript:showPreviousPoNonChemReceipts();");
}

drawMenus();

<c:set var="itemid"><fmt:message key="label.itemid"/></c:set>
var searchWhatChemArray = [
    { id:'radianPo', text:'<fmt:message key="label.po"/>'},
    { id:'customerPo', text:'<fmt:message key="label.customerpo"/>'},
    { id:'itemId', text:'<tcmis:jsReplace value="${itemid}"/>'},
    { id:'itemDescription', text:'<fmt:message key="label.itemdesc"/>'},
    { id:'lastSupplier', text:'<fmt:message key="label.supplier"/>'},
    { id:'transferRequestId', text:'<fmt:message key="transferrequest.title"/>'},
    { id:'customerRmaId', text:'<fmt:message key="label.rma"/>'}
];  
 
var searchWhatNonChemArray = [
    { id:'radianPo', text:'<fmt:message key="label.po"/>'},
    { id:'customerPo', text:'<fmt:message key="label.customerpo"/>'},
    { id:'itemId', text:'<tcmis:jsReplace value="${itemid}"/>'},
    { id:'itemDescription', text:'<fmt:message key="label.itemdesc"/>'},
    { id:'lastSupplier', text:'<fmt:message key="label.supplier"/>'}
];    

// -->
</script>
</head>

<c:set var="doexcelpopup" value='${doexcelpopup}'/>
<c:choose>
  <c:when test="${!empty doexcelpopup}" >
   <body onLoad="doexcelpopup();setOps();setDefault();setSearchWhat(searchWhatNonChemArray);" onunload="closeAllchildren();">
  </c:when>
  <c:when test="${!empty receivedReceipts}" >
   <body onLoad="showNonChemicalReceivedReceipts('<c:out value="${receivedReceipts}"/>');setOps();setDefault();setSearchWhat(searchWhatNonChemArray);" onunload="closeAllchildren();">
  </c:when>
  <c:otherwise>
   <body onload="setOps();setDefault();setSearchWhat(searchWhatNonChemArray);" onunload="closeAllchildren();">
  </c:otherwise>
</c:choose>

<c:set var="duplicateLine" value='${duplicateLine}'/>
<c:set var="submitSearch" value='${param.submitSearch}'/>
<c:set var="submitReceive" value='${param.submitReceive}'/>
<c:set var="submitCreateReport" value='${param.submitCreateReport}'/>

<tcmis:form action="/receiving.do" onsubmit="return SubmitOnlyOnce();">

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
<tr><td width="70%" class="headingl">
<fmt:message key="label.receiving"/>
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
<table id="searchMaskTable" width="880" border="0" cellpadding="0" cellspacing="0">
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
  <td width="5%" class="optionTitleBoldRight">
      <fmt:message key="label.category"/>:
  </td>
  <td width="10%" class="optionTitleBoldLeft">
     <html:select property="category" styleId="category" onchange="checkDropShip();" styleClass="selectBox">
        <html:option value="Chemicals"/>
        <html:option value="Non-Chemicals"/>
     </html:select>
  </td>
  <td width="5%" class="optionTitleBoldRight">
     <fmt:message key="label.search"/>:
  </td>
  <td width="10%" class="optionTitleBoldLeft" nowrap>
  	 <select name="searchWhat" id="searchWhat" onchange="getSelectedSearchWhat();" onchange="getSelectedSearchWhat();" class="selectBox">
	 </select>
	 <input type="hidden" name="selectedSearchWhat" id="selectedSearchWhat" value="${param.searchWhat}"/>
     <html:select property="searchType" styleId="searchType" styleClass="selectBox">
        <html:option value="is"/>
        <html:option value="contains"/>
        <html:option value="startsWith" key="label.startswith"/>
        <html:option value="endsWith" key="label.endswith"/>
      </html:select>
      <input class="inputBox" type="text" name="search" id="search"  value="${param.search}" size="20" maxlength="50">
  </td>  
</tr>

<tr>
 <td width="5%" class="optionTitleBoldRight">
  <fmt:message key="label.operatingentity"/>:
 </td>
 <td width="10%" class="optionTitleBoldLeft">
   	 <select name="opsEntityId" id="opsEntityId" class="selectBox">
	 </select>
	 <input type="hidden" name="selectedOpsEntityId" id="selectedOpsEntityId" value="${param.opsEntityId}"/>
 
<%-- 
 <c:set var="selectedHub" value='${param.sourceHub}'/>
 <c:set var="selectedHubName" value=''/>
 <select name="sourceHub" id="sourceHub" onchange="hubchanged()" class="selectBox">
   <c:forEach var="hubInventoryGroupOvBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
  <c:set var="currentHub" value='${status.current.branchPlant}'/>

  <c:choose>
   <c:when test="${empty selectedHub}" >
    <c:set var="selectedHub" value='${status.current.branchPlant}'/>
    <c:set var="selectedHubName" value="${status.current.hubName}"/>
    <c:set var="inventoryGroupDefinitions" value='${status.current.inventoryGroupCollection}'/>
   </c:when>
   <c:when test="${currentHub == selectedHub}" >
    <c:set var="inventoryGroupDefinitions" value='${status.current.inventoryGroupCollection}'/>
   </c:when>
  </c:choose>

  <c:choose>
   <c:when test="${currentHub == selectedHub}">
    <option value="<c:out value="${currentHub}"/>" selected><c:out value="${status.current.hubName}"/></option>
    <c:set var="selectedHubName" value="${status.current.hubName}"/>
   </c:when>
   <c:otherwise>
    <option value="<c:out value="${currentHub}"/>"><c:out value="${status.current.hubName}"/></option>
   </c:otherwise>
  </c:choose>
  </c:forEach>
 </select>

<c:set var="receivingPermission" value=''/>
 <tcmis:inventoryGroupPermission indicator="true" userGroupId="Receiving" facilityId="${selectedHubName}" inventoryGroup="${param.inventoryGroup}">
  <c:set var="receivingPermission" value='Yes'/>
 </tcmis:inventoryGroupPermission>
--%>
 </td>
 <td width="5%" class="optionTitleBoldRight">
   <fmt:message key="receiving.label.expected"/>
 </td>
 <td width="10%" class="optionTitleBoldLeft">

   <c:set var="expected" value='${param.expected}'/>
   <c:if test="${empty expected && (empty submitSearch && empty duplicateLine && empty submitCreateReport && empty submitReceive)}">
    <c:set var="expected" value='${30}'/>
   </c:if>
   <input class="inputBox" type="text" name="expected" value="<c:out value="${expected}"/>" size="1" maxlength="4">
   <!--<html:text property="expected" size="1" styleClass="inputBox"/>-->
   <fmt:message key="label.days"/>
 </td>

</tr>

<tr>
 <td width="5%" class="optionTitleBoldRight">
 	<fmt:message key="label.hub"/>:
 </td>
 <td width="10%" class="optionTitleBoldLeft">
 	<select name="sourceHub" id="sourceHub" class="selectBox">
    </select>
    <input type="hidden" name="selectedHub" id="selectedHub" value="${param.sourceHub}"/>
    <c:set var="selectedHubName" value='${param.sourceHubName}'/>
    <c:set var="receivingPermission" value=''/>
	 <tcmis:inventoryGroupPermission indicator="true" userGroupId="Receiving" facilityId="${selectedHubName}">
	  <c:set var="receivingPermission" value='Yes'/>
	 </tcmis:inventoryGroupPermission>
<%-- 
 <c:set var="selectedIg" value='${param.inventoryGroup}'/>
 <c:set var="invenGroupCount" value='${0}'/>
 <select name="inventoryGroup" id="inventoryGroup" class="selectBox">
  <option value=""><fmt:message key="label.all"/></option>
  <c:forEach var="inventoryGroupObjBean" items="${inventoryGroupDefinitions}" varStatus="status">
    <c:set var="invenGroupCount" value='${invenGroupCount+1}'/>
    <c:set var="currentIg" value='${status.current.inventoryGroup}'/>
    <c:choose>
      <c:when test="${empty selectedIg}" >
        <c:set var="selectedIg" value=""/>
      </c:when>
      <c:when test="${currentIg == selectedIg}" >
      </c:when>
    </c:choose>

    <c:choose>
      <c:when test="${currentIg == selectedIg}">
        <option value="<c:out value="${currentIg}"/>" selected><c:out value="${status.current.inventoryGroupName}"/></option>
      </c:when>
      <c:otherwise>
        <option value="<c:out value="${currentIg}"/>"><c:out value="${status.current.inventoryGroupName}"/></option>
      </c:otherwise>
    </c:choose>
  </c:forEach>
  <c:if test="${invenGroupCount == 0}">
   <option value=""><fmt:message key="label.all"/></option>
  </c:if>
 </select>
--%>
 </td>
 <td width="5%" class="optionTitleBoldRight">
   &nbsp;
 </td>
 <td width="10%" class="optionTitleBoldLeft">
   <div id="showdropshiponly" style="display:none"><html:checkbox property="dropship" styleId="dropship" value="Y" styleClass="radioBtns"/><fmt:message key="receiving.label.showdropshiponly"/></div>
 </td>
</tr>

<tr>
 <td width="5%" class="optionTitleBoldRight">
 	<fmt:message key="label.inventorygroup"/>:
 </td>
 <td width="10%" class="optionTitleBoldLeft">
 	<select name="inventoryGroup" id="inventoryGroup" class="selectBox">
    </select>
    <input type="hidden" name="selectedInventoryGroup" id="selectedInventoryGroup" value="${param.inventoryGroup}"/>
 </td>
 <td width="5%" class="optionTitleBoldRight">
   <fmt:message key="label.sortby"/>:
 </td>
 <td width="10%" class="optionTitleBoldLeft">
   <html:select property="sort" styleId="sort" styleClass="selectBox">
     <html:option value="PO/POLine"/>
     <html:option value="Date Exptd/Supplier"/>
     <html:option value="Supplier/Date Exptd"/>
     <html:option value="Item Id/Date Exptd"/>
     </html:select>
 </td>
</tr>

<tr>
  <td width="5%" colspan="4" class="optionTitleBoldLeft">
   <html:submit property="submitSearch" onclick="return validateForm();" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
     <fmt:message key="label.search"/>
   </html:submit>

  <html:submit property="submitCreateReport" onclick="return doexcelpopup()" styleId="submitCreateReport" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
     <fmt:message key="label.createexcelfile"/>
  </html:submit>
   <html:button property="submitReprint" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "reprint()">
     <fmt:message key="label.reprint"/>
   </html:button>
  <tcmis:inventoryGroupPermission indicator="true" userGroupId="addNewBin" facilityId="${selectedHubName}">
   <html:button property="submitNewBin" onclick="addnewBin()" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
     <fmt:message key="receiving.button.newbin"/>
   </html:button>
  </tcmis:inventoryGroupPermission>
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
<div id="errorMessagesArea" class="errorMessages">
<c:if test="${!empty errorMessage}">
 <c:out value="${errorMessage}"/>
</c:if>
<html:errors/>
</div>
<!-- Error Messages Ends -->

<c:if test="${empty errorMessage && !empty submitReceive}">
 <div class="optionTitleBoldCenter"><fmt:message key="receiving.successmessage"/></div>
</c:if>

<c:if test="${receivingViewRelationBeanCollection != null}" >
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your mail table will be 100% -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead">
    <c:if test="${!empty receivingViewRelationBeanCollection}" >
      <a href="javascript:showLegend()"><fmt:message key="label.Legend"/></a>
      <c:if test="${receivingPermission == 'Yes'}">
      <span id="receivingLink" style="">
       | <a href="#" onclick="submitNonChemReceiveAction('submitReceive'); return false;"><fmt:message key="receiving.button.receive"/></a>
      </span>
      </c:if> 
    </c:if>
    </div>
    </div>
    <div class="dataContent">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">

 <c:set var="colorClass" value=''/>
 <c:set var="dataCount" value='${0}'/>

 <c:forEach var="receivingViewBean" items="${receivingViewRelationBeanCollection}" varStatus="status">
  <c:if test="${status.index % 10 == 0}">
   <tr align="center">
   <th width="5%"><fmt:message key="label.po"/><br>(<fmt:message key="label.inventorygroup"/>)</th>
   <th width="3%"><fmt:message key="label.poline"/></th>
   <th width="7%"><fmt:message key="label.supplier"/></th>
   <th width="12%"><fmt:message key="label.description"/></th>
   <th width="5%"><fmt:message key="label.packaging"/></th>

   <c:if test="${receivingPermission == 'Yes'}">
    <th width="2%"><fmt:message key="label.ok"/></th>
    <th width="5%"><fmt:message key="receiving.label.supplieref"/></th>
    <th width="5%"><fmt:message key="receivedreceipts.label.dor"/></th>
    <th width="5%"><fmt:message key="receiving.label.qtyreceived"/></th>
    <th width="5%"><fmt:message key="label.notes"/></th>
    <th width="2%"><fmt:message key="receiving.label.duplicateline"/></th>
   </c:if>
   </tr>
  </c:if>

  <c:choose>
   <c:when test="${status.index % 2 == 0}" >
    <c:set var="colorClass" value=''/>
   </c:when>
   <c:otherwise>
    <c:set var="colorClass" value='alt'/>
   </c:otherwise>
  </c:choose>

  <tr align="center" CLASS="<c:out value="${colorClass}"/>" id="rowId<c:out value="${status.index}"/>" onmouseup="selectRowNonChem('${status.index}')">
  <input type="hidden" name="colorClass${status.index}" id="colorClass<c:out value="${status.index}"/>" value="${colorClass}" />
  <input type="hidden" id="selectedDataCount<c:out value="${status.index}"/>" value="${dataCount}">
  <input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].qtyOpen" id="qtyOpen<c:out value="${dataCount}"/>" value="<c:out value="${status.current.qtyOpen}"/>">
  <c:set var="kitCollection"  value='${status.current.kitCollection}'/>
  <bean:size id="listSize" name="kitCollection"/>
  <c:set var="mainRowSpan" value='${status.current.rowSpan}' />

  <c:set var="inventoryGroupPermission" value=''/>
  <tcmis:inventoryGroupPermission indicator="true" userGroupId="Receiving" inventoryGroup="${status.current.inventoryGroup}">
   <c:set var="inventoryGroupPermission" value='Yes'/>
  </tcmis:inventoryGroupPermission>

  <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
   <c:out value="${status.current.radianPo}"/>
   <%--<a href=javascript:showPreviousPoNonChemReceipts(<c:out value="${dataCount}"/>)><c:out value="${status.current.radianPo}"/></a>--%>
   <br>(<c:out value="${status.current.inventoryGroupName}"/>)
</td>
  <td width="3%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.lineItem}"/></td>
  <td width="7%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.lastSupplier}"/></td>
  <td width="12%" class="alignLeft" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.partDescription}"/></td>
  <td width="5%" class="alignLeft" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.packaging}"/></td>

  <c:forEach var="ReceivingKitBean" items="${kitCollection}" varStatus="kitstatus">
   <c:if test="${kitstatus.index > 0 && listSize > 1 }">
     <tr align="center" CLASS="<c:out value="${colorClass}"/>" id="childRowId<c:out value="${status.index}"/><c:out value="${kitstatus.index}"/>">
	  <%--<c:if test="${manageKitsAsSingleUnit != 'N'}">
      <input type="hidden" name="ktiComponent<c:out value="${dataCount}"/>" value="Yes">
     </c:if>--%>
   </c:if>

   <input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].rowNumber" id="rowNumber<c:out value="${dataCount}"/>" value="<c:out value="${dataCount}"/>">
   <input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].radianPo" id="radianPo<c:out value="${dataCount}"/>" value="<c:out value="${status.current.radianPo}"/>">
   <input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].lineItem" id="lineItem<c:out value="${dataCount}"/>" value="<c:out value="${status.current.lineItem}"/>">
   <input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].itemId" id="itemId<c:out value="${dataCount}"/>" value="<c:out value="${status.current.itemId}"/>">
   <input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].inventoryGroup" id="inventoryGroup<c:out value="${dataCount}"/>" value="<c:out value="${status.current.inventoryGroup}"/>">
   <input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].transType" id="transType<c:out value="${dataCount}"/>" value="<c:out value="${status.current.transType}"/>">
   <input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].rowSpan" id="rowSpan<c:out value="${dataCount}"/>" value="<c:out value="${listSize}"/>">
   <input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].branchPlant" id="branchPlant<c:out value="${dataCount}"/>" value="<c:out value="${status.current.branchPlant}"/>">
   <input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].nonintegerReceiving" id="nonintegerReceiving<c:out value="${dataCount}"/>" value="<c:out value="${status.current.nonintegerReceiving}"/>">

   <c:choose>
      <c:when test="${inventoryGroupPermission == 'Yes'}">
        <fmt:formatDate var="formattedDateOfReceipt" value="${kitstatus.current.dateOfReceipt}" pattern="${dateFormatPattern}"/>

        <td width="2%">
         <c:if test="${inventoryGroupPermission == 'Yes'}">
           <c:if test="${kitstatus.current.ok != null}" >
            <input type="checkbox" name="receivingViewBean[<c:out value="${dataCount}"/>].ok" id="ok<c:out value="${dataCount}"/>" value="<c:out value="${dataCount}"/>" checked onclick="checkNonChemicalReceivingInput(<c:out value="${dataCount}"/>)">
           </c:if>
           <c:if test="${kitstatus.current.ok == null}" >
            <input type="checkbox" name="receivingViewBean[<c:out value="${dataCount}"/>].ok" id="ok<c:out value="${dataCount}"/>" value="<c:out value="${dataCount}"/>" onclick="checkNonChemicalReceivingInput(<c:out value="${dataCount}"/>)">
           </c:if>
         </c:if>
        </td>
        <td width="8%">
         <input type="text" name="receivingViewBean[<c:out value="${dataCount}"/>].mfgLot" id="mfgLot<c:out value="${dataCount}"/>" value="<c:out value="${kitstatus.current.mfgLot}"/>" size="20" maxlength="36" class="inputBox">
        </td>
	    <td width="5%" nowrap>
	      <input name='receivingViewBean[${dataCount}].beforeSystemDate'
							    id='beforeSystemDate${dataCount}' type="hidden"
							    value='<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>'  /> 
		  <input type="text" name="receivingViewBean[<c:out value="${dataCount}"/>].dateOfReceipt" id="dateOfReceipt<c:out value="${dataCount}"/>" 
                  readonly  onClick="return getCalendar(document.genericForm.dateOfReceipt${dataCount},document.genericForm.date360,null,null,document.genericForm.todayDate);" value="${formattedDateOfReceipt}" size="8" maxlength="10" class="inputBox pointer">
<%--	      <input type="text" readonly name="receivingViewBean[<c:out value="${dataCount}"/>].dateOfReceipt" id="dateOfReceipt${dataCount}"
	           onClick="return getCalendar(document.genericForm.dateOfReceipt${dataCount},null,null,null,document.genericForm.beforeSystemDate${dataCount});"
	           value="${formattedDateOfReceipt}" size="8" maxlength="10" class="inputBox pointer">
 	      <a href="javascript: void(0);" class="optionTitleBold" id="linkdateOfReceipt<c:out value="${dataCount}"/>" onclick="return getCalendar(document.genericForm.dateOfReceipt<c:out value="${dataCount}"/>);">&diams;</a> --%>
	    </td>
      <td width="5%">
         <input type="text" name="receivingViewBean[<c:out value="${dataCount}"/>].quantityReceived" id="quantityReceived<c:out value="${dataCount}"/>" value="<c:out value="${kitstatus.current.quantityReceived}"/>" size="4" maxlength="15" class="inputBox">
        </td>
        <td width="5%">
         <textarea name="receivingViewBean[<c:out value="${dataCount}"/>].receiptNotes" cols="25" rows="3" class="inputBox"><c:out value="${status.current.receiptNotes}"/></textarea>
        </td>
        <c:if test="${kitstatus.index == 0}">
         <td width="2%" rowspan="<c:out value="${mainRowSpan}"/>">
          <input type="submit" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onclick= " return duplLine(<c:out value="${dataCount}"/>)" value="Dupl" name="duplicateButton">
         </td>
        </c:if>
	   </c:when>
	   <c:when test="${receivingPermission == 'Yes'}">
        <input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].updateStatus" value="readOnly">
        <td width="2%">&nbsp;</td>
        <td width="5%">&nbsp;</td>
        <td width="5%">&nbsp;</td>
        <td width="5%">&nbsp;</td>
        <td width="2%">&nbsp;</td>
        <td width="2%">&nbsp;</td>
	  </c:when>
   </c:choose>
     <c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
 </tr>
</c:forEach>
</table>

  <input type="hidden" name="totallines" value="<c:out value="${dataCount}"/>">
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty receivingViewRelationBeanCollection}" >
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData">
     <tr>
      <td width="100%">
       <fmt:message key="main.nodatafound"/>
      </td>
     </tr>
    </table>
   </c:if>

  </div>
  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</div>
</td></tr>
</table>
<!-- Search results end -->
</c:if>

<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;">
<input type="hidden" name="userAction" id="userAction" value="">
<input type="hidden" name="duplicateLine" id="duplicateLine"  value="">
<input type="hidden" name="duplicatePkgLine" id="duplicatePkgLine" value="">
<input type="hidden" name="duplicateKitLine" id="duplicateKitLine" value="">
<input type="hidden" name="sourceHubName" id="sourceHubName" value="<c:out value="${selectedHubName}"/>">
<input name='date60' id='date60' type="hidden" value='<tcmis:getDateTag numberOfDaysFromToday="-60" datePattern="${dateFormatPattern}"/>'  />
<input name='date360' id='date360' type="hidden" value='<tcmis:getDateTag numberOfDaysFromToday="-360" datePattern="${dateFormatPattern}"/>'  />
<input name="todayDate" id="todayDate" type="hidden"
	   value='<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>'  /> 
<input type="hidden" name="submitPrint" id="submitPrint" value="">
<input type="hidden" name="submitReceive" id="submitReceive" value="">
<input type="hidden" name="isAutoPutHub"  id="isAutoPutHub" value=""/>
 </div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

<%-- show legend Begins --%>
<div id="showLegendArea" style="display: none;overflow: auto;">
  <table width=100% class="tableResults" border="0" cellpadding="0" cellspacing="0">
    <tr><td width="100px" class="pink">&nbsp;</td><td class="legendText"><fmt:message key="label.aogsupercriticalpo"/></td></tr>
    <tr><td width="100px" class="red">&nbsp;</td><td class="legendText"><fmt:message key="label.criticalpo"/></td></tr>
    <tr><td width="100px" class="orange">&nbsp;</td><td class="legendText"><fmt:message key="label.receiptforexcessmaterialreceivedonpo"/></td></tr>
    <tr><td width="100px" class="green">&nbsp;</td><td class="legendText"><fmt:message key="label.receiptformlitem"/></td></tr>
    <tr><td width="100px" class="error">&nbsp;</td><td class="legendText"><fmt:message key="label.errorprocessingreceipt"/></td></tr>
  </table>
</div>
<%-- show legend Ends --%>

</div> <!-- close of interface -->
</tcmis:form>
</DIV>

</body>
</html:html>