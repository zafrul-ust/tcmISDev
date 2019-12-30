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
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />

<%-- Add any other stylesheets you need for the page here --%>

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<script type="text/javascript" src="/js/common/iframeresizemain.js"></script>
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

<script type="text/javascript" src="/js/hub/receivingqc.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>

<!-- This is for the YUI, uncomment if you will use this -->
<%--<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>--%>

<title>
<fmt:message key="label.receivingqc"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
dor:"<fmt:message key="receivedreceipts.label.dor"/>",
expiredate:"<fmt:message key="label.expiredate"/>",
all:"<fmt:message key="label.all"/>",
mfglot:"<fmt:message key="label.mfglot"/>",
showlegend:"<fmt:message key="label.showlegend"/>",
mustbeanumberinthisfield:"<fmt:message key="label.mustbeanumberinthisfield"/>",
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
altinvName["<c:out value="${currentHub}"/>"] = new Array("All"
 <c:forEach var="inventoryGroupObjBean" items="${inventoryGroupDefinitions}" varStatus="status1">
,"<c:out value="${status1.current.inventoryGroupName}"/>"
  </c:forEach>

  );
 </c:forEach>
 */
<c:choose>
   <c:when test="${docType == 'IT'}">
    with(milonic=new menuname("nonChemQc")){
	 top="offset=2"
	 style = contextStyle;
	 margin=3
	
	 aI("text=<fmt:message key="receivinghistory.label.approved.potitle"/>;url=javascript:showrecforinvtransfrQc();");
	 aI("text=<fmt:message key="pickingqc.viewaddreceipts"/>;url=javascript:showProjectDocuments();");
	}
   </c:when>
   <c:when test="${docType != 'IT' && docType != 'IA'}">
    with(milonic=new menuname("nonChemQc")){
	 top="offset=2"
	 style = contextStyle;
	 margin=3
	
	 aI("text=<fmt:message key="receivinghistory.label.potitle"/>;url=javascript:showPreviousPoLineQc();");
	 aI("text=<fmt:message key="pickingqc.viewaddreceipts"/>;url=javascript:showProjectDocuments();");
	}
   </c:when>
   <c:otherwise>
    with(milonic=new menuname("nonChemQc")){
	 top="offset=2"
	 style = contextStyle;
	 margin=3
	
	 aI("text=<fmt:message key="pickingqc.viewaddreceipts"/>;url=javascript:showProjectDocuments();");
	}
   </c:otherwise>
  </c:choose>

drawMenus();

<c:set var="itemid"><fmt:message key="label.itemid"/></c:set>
var searchWhatChemArray = [
    { id:'PO', text:'<fmt:message key="label.po"/>'},
    { id:'Receipt Id', text:'<fmt:message key="label.receiptid"/>'},
    { id:'Item ID', text:'<tcmis:jsReplace value="${itemid}"/>'},
    { id:'Item Desc', text:'<fmt:message key="label.itemdesc"/>'},
    { id:'transferRequestId', text:'<fmt:message key="transferrequest.title"/>'},
    { id:'customerRmaId', text:'<fmt:message key="label.rma"/>'}
];  
 
var searchWhatNonChemArray = [
    { id:'PO', text:'<fmt:message key="label.po"/>'},
    { id:'Receipt Id', text:'<fmt:message key="label.receiptid"/>'},
    { id:'Item ID', text:'<tcmis:jsReplace value="${itemid}"/>'},
    { id:'Item Desc', text:'<fmt:message key="label.itemdesc"/>'}
]; 
   
// -->
</script>

</head>

<body bgcolor="#ffffff" onload="setOps();setDefault();setSearchWhat(searchWhatNonChemArray);" onunload="closeAllchildren();">

<tcmis:form action="/receivingqc.do" onsubmit="return submitOnlyOnce();">

 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
 </div>
 <div class="interface" id="mainPage">

<!-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure -->
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
<fmt:message key="label.receivingqc"/>
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
<table id="searchMaskTable" width="800" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
<tr>
  <td width="5%" class="optionTitleBoldRight">
      <fmt:message key="label.category"/>:
  </td>
  <td width="10%" class="optionTitleLeft">
     <html:select property="category" styleId="category"  onchange="changeSearchCriterion();"  styleClass="selectBox">
        <html:option value="Chemicals" key="label.chemicals"/>
        <html:option value="Non-Chemicals" key="label.non-chemicals"/>
     </html:select>
  </td>
  <td width="5%" class="optionTitleBoldRight">
     <fmt:message key="label.search"/>:
  </td>
  <td width="8%" class="optionTitleLeft" nowrap>
  	 <select name="searchWhat" id="searchWhat"  onchange="getSelectedSearchWhat();"  class="selectBox">
	 </select>
	 <input type="hidden" name="selectedSearchWhat" id="selectedSearchWhat" value="${param.searchWhat}"/>
     <html:select property="searchType" styleId="searchType" styleClass="selectBox">
        <html:option value="is" key="label.is"/>
        <html:option value="contains" key="label.contain"/>
        <html:option value="startsWith" key="label.startswith"/>
        <html:option value="endsWith" key="label.endswith"/>
      </html:select>
      <html:text property="search" styleId="search" size="20" styleClass="inputBox"/>
  </td>
  <td width="5%" class="optionTitleBoldLeft">
   &nbsp;
  </td>
</tr>

<tr>
 <td width="5%" class="optionTitleBoldRight">
  <fmt:message key="label.operatingentity"/>:
 </td>
 <td width="10%" class="optionTitleLeft">
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

<c:set var="receivingQcPermission" value=''/>
 <tcmis:inventoryGroupPermission indicator="true" userGroupId="ReceivingQC" facilityId="${selectedHubName}" inventoryGroup="${param.inventoryGroup}">
  <c:set var="receivingQcPermission" value='Yes'/>
 </tcmis:inventoryGroupPermission>
--%>
 </td>

<td width="5%">&nbsp;</td>
<td width="5%">&nbsp;</td>
<td width="5%">&nbsp;</td>

<%--<td width="10%" class="optionTitleLeft">
<c:set var="paperSize" value='${param.paperSize}'/>
<c:if test="${empty paperSize || paperSize == ''}" >
 <c:set var="paperSize" value='31'/>
</c:if>

<c:choose>
  <c:when test="${paperSize == '811'}" >
   <input id="option1" type="radio" name="paperSizeRadio" onClick="assignpaperSize('31')" value="31" class="radioBtns">
   <fmt:message key="labels.label.size35"/>&nbsp;&nbsp;
   <input id="option2" type="radio" name="paperSizeRadio" onClick="assignpaperSize('811')" value="811" class="radioBtns" checked="checked">

   <fmt:message key="labels.label.size811"/>
  </c:when>
  <c:otherwise>
   <input id="option1" type="radio" name="paperSizeRadio" onClick="assignpaperSize('31')" value="31" class="radioBtns" checked="checked">
   <fmt:message key="labels.label.size35"/>&nbsp;&nbsp;
   <input id="option2" type="radio" name="paperSizeRadio" onClick="assignpaperSize('811')" value="811"  class="radioBtns">
   <fmt:message key="labels.label.size811"/>
  </c:otherwise>
</c:choose>
</td>--%>
</tr>

<tr>
 <td width="5%" class="optionTitleBoldRight">
 	<fmt:message key="label.hub"/>:
 </td>
 <td width="10%" class="optionTitleLeft">
 	<select name="sourceHub" id="sourceHub" class="selectBox">
    </select>
    <input type="hidden" name="selectedHub" id="selectedHub" value="${param.sourceHub}"/>
    
 <c:set var="selectedHubName" value='${param.sourceHubName}'/>
 <c:set var="receivingQcPermission" value=''/>
 <tcmis:inventoryGroupPermission indicator="true" userGroupId="ReceivingQC" facilityId="${selectedHubName}" inventoryGroup="${param.inventoryGroup}">
  <c:set var="receivingQcPermission" value='Yes'/>
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
        <option value="<c:out value="${currentIg}"/>" selected="selected"><c:out value="${status.current.inventoryGroupName}"/></option>
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
   <fmt:message key="label.sortby"/>:
 </td>
 <td width="8%" class="optionTitleLeft">
   <html:select property="sort" styleId="sort" styleClass="selectBox">
    <html:option value="Bin" key="label.bin"/>
    <html:option value="Mfg Lot" key="label.mfglot"/>
    <html:option value="Item Id" key="label.itemid"/>
    <html:option value="PO" key="label.po"/>
    <html:option value="Receipt ID" key="label.receiptid"/>
   </html:select>
 </td>

<td width="5%" class="optionTitleLeft">
&nbsp;
 </td>
<%--
<td width="5%" class="optionTitleLeft">
&nbsp;
  </td>

<td width="5%" class="optionTitleLeft">
 </td>--%>
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
 <td width="5%" colspan="3" class="optionTitleBoldRight">
   &nbsp;
 </td>
</tr>

<tr>
<td width="5%" class="optionTitleLeft" colspan="5">
 <html:submit property="submitSearch" onclick="return searchForm()" styleId="submitSearch" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
   <fmt:message key="label.search"/>
 </html:submit>

 <html:submit property="submitCreateReport" onclick="return doexcelpopup()" styleId="submitCreateReport" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
     <fmt:message key="label.createexcelfile"/>
 </html:submit>

<tcmis:inventoryGroupPermission indicator="true" userGroupId="addNewBin" facilityId="${selectedHubName}">
<html:button property="submitNewBin" styleClass="inputBtns" onclick="addnewBin()" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
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

<c:if test="${receivingQcViewRelationBeanCollection != null}" >
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead">
      <c:if test="${!empty receivingQcViewRelationBeanCollection}" >
      <a href="javascript:showLegend()"><fmt:message key="label.Legend"/></a>
<%--      <span id="updateMlItemLink" style="">
        <c:if test="${receivingQcPermission == 'Yes'}">
         <a href="#" onclick="changeMlItem(); return false;"><fmt:message key="label.changeitem"/></a> |
        </c:if>
      </span>--%>
<%--      <span id="receivingLabelsLink" style="">
        <a href="#" onclick="printReceivingBoxLabels(); return false;"><fmt:message key="receivedreceipts.button.receivinglabels"/></a> |
      </span>--%>
      <%--<span id="generateLabelsLink" style="">
        <a href="#" onclick="submitActionForm('submitPrint'); return false;"><fmt:message key="labels.generatelabels"/></a> |
     </span>--%>
      <c:if test="${receivingQcPermission == 'Yes'}">
      <span id="updateResultLink" style="">
       | <a href="#" onclick="submitNonChemConfirm('submitReceive'); return false;"><fmt:message key="button.confirm"/></a>
      </span>
      </c:if>
      <%--<html:checkbox property="skipKitLabels" styleId="skipKitLabels" value="Yes" styleClass="radioBtns"/><fmt:message key="receivedreceipts.label.skipkitlabels"/>--%>
      </c:if>
    </div>
    </div>
    <div class="dataContent">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">
 <c:set var="colorClass" value=''/>
 <c:set var="dataCount" value='${0}'/>

 <c:forEach var="receiptDescriptionViewBean" items="${receivingQcViewRelationBeanCollection}" varStatus="status">
  <c:if test="${status.index % 10 == 0}">
   <tr align="center">
   <th width="5%"><fmt:message key="label.po"/><br/>(<fmt:message key="label.inventorygroup"/>)</th>
   <th width="5%"><fmt:message key="label.poline"/></th>
   <th width="12%"><fmt:message key="label.description"/></th>
   <c:if test="${receivingQcPermission == 'Yes'}">
    <th width="2%"><fmt:message key="label.ok"/><br/><input type="checkbox" value="" onClick="checkAllNonChemicalReceipts()" name="checkNonChemicalReceipts" id="checkNonChemicalReceipts"></th>
   </c:if>
    <th width="5%"><fmt:message key="receiving.label.supplieref"/></th>
    <th width="5%"><fmt:message key="receivingqc.label.enddate"/></th>
    <th width="5%"><fmt:message key="receivedreceipts.label.dor"/></th>
    <th width="5%"><fmt:message key="label.bin"/></th>
    <th width="5%"><fmt:message key="label.receiptid"/><br/>(<fmt:message key="receivingqc.label.transreceiptid"/>)</th>
    <th width="5%"><fmt:message key="receiving.label.qtyreceived"/></th>
    <th width="5%"><fmt:message key="label.packaging"/></th>
    <th width="5%"><fmt:message key="label.notes"/></th>
    <th width="5%"><fmt:message key="receiving.label.deliveryticket"/></th>
    <th width="2%"><fmt:message key="reversereceipt.title"/></th>
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

  <c:set var="critical" value='${status.current.critical}'/>
  <c:if test="${critical == 'Y' || critical == 'y'}">
   <c:set var="colorClass" value='red'/>
  </c:if>

  <c:if test="${critical == 'S' || critical == 's'}">
   <c:set var="colorClass" value='pink'/>
  </c:if>

  <c:set var="excess" value='${status.current.excess}'/>
  <c:if test="${excess == 'Yes' || excess == 'yes'}">
   <c:set var="colorClass" value='orange'/>
  </c:if>

  <c:set var="updateStatus" value='${status.current.updateStatus}'/>
  <c:if test="${updateStatus == 'NO' || updateStatus == 'Error'}">
   <c:set var="colorClass" value='error'/>
  </c:if>

  <tr class="<c:out value="${colorClass}"/>" id="rowId<c:out value="${status.index}"/>" onmouseup="selectRowNonChem('${status.index}')"> 
  <input type="hidden" name="colorClass${status.index}" id="colorClass<c:out value="${status.index}"/>" value="${colorClass}" />
  <input type="hidden" id="selectedDataCount<c:out value="${status.index}"/>" value="${dataCount}">
  <c:set var="kitCollection"  value='${status.current.kitCollection}'/>
  <bean:size id="listSize" name="kitCollection"/>
  <c:set var="mainRowSpan" value='${status.current.rowSpan}' />
  <c:set var="manageKitsAsSingleUnit" value='${status.current.manageKitsAsSingleUnit}'/>
  <c:set var="mvItem" value='${status.current.mvItem}'/>
  <c:set var="docType" value='${status.current.docType}'/>

  <c:set var="inventoryGroupPermission" value=''/>
  <tcmis:inventoryGroupPermission indicator="true" userGroupId="ReceivingQC" inventoryGroup="${status.current.inventoryGroup}">
   <c:set var="inventoryGroupPermission" value='Yes'/>
  </tcmis:inventoryGroupPermission>

  <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
  <c:choose>
   <c:when test="${docType == 'IT'}" >
     <fmt:message key="label.tr"/> <c:out value="${status.current.transferRequestId}"/>
<%--      <a href=javascript:showrecforinvtransfrQc(<c:out value="${dataCount}"/>)><fmt:message key="label.tr"/> <c:out value="${status.current.transferRequestId}"/></a> --%>
   </c:when>
   <c:when test="${docType == 'IA'}" >
     <c:out value="${status.current.returnPrNumber}"/>-<c:out value="${status.current.returnLineItem}"/>
   </c:when>
   <c:otherwise>
    <c:out value="${status.current.radianPo}"/>
    <br/>(<c:out value="${status.current.inventoryGroupName}"/>)
   </c:otherwise>
  </c:choose>
  </td>

  <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
  <c:choose>
   <c:when test="${docType == 'IT' || docType == 'IA'}">
    &nbsp;
   </c:when>
   <c:otherwise>
    <c:out value="${status.current.poLine}"/>
<%--     <a href=javascript:showPreviousPoLineQc(<c:out value="${dataCount}"/>)><c:out value="${status.current.poLine}"/></a> --%>
   </c:otherwise>
  </c:choose>
  </td>

  <c:forEach var="receiptDescriptionViewBean" items="${kitCollection}" varStatus="kitstatus">
   <c:if test="${kitstatus.index > 0 && listSize > 1 }">
     <tr class="<c:out value="${colorClass}"/>" id="childRowId<c:out value="${status.index}"/><c:out value="${kitstatus.index}"/>">
   </c:if>

    <c:choose>
    <c:when test="${listSize > 1 && manageKitsAsSingleUnit == 'N'}">
     <td width="12%" class="alignLeft"><c:out value="${kitstatus.current.materialDesc}"/></td>
    </c:when>
    <c:when test="${manageKitsAsSingleUnit != 'N' && kitstatus.index == 0}">
     <td width="12%" class="alignLeft" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.lineDesc}"/></td>
    </c:when>
   </c:choose>

   <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].radianPo" id="radianPo<c:out value="${dataCount}"/>" value="<c:out value="${status.current.radianPo}"/>">
   <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].poLine" id="poLine<c:out value="${dataCount}"/>" value="<c:out value="${status.current.poLine}"/>">
   <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].itemId" id="itemId<c:out value="${dataCount}"/>" value="<c:out value="${status.current.itemId}"/>">
   <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].inventoryGroup" id="inventoryGroup<c:out value="${dataCount}"/>" value="<c:out value="${status.current.inventoryGroup}"/>">
   <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].docType" id="docType<c:out value="${dataCount}"/>" value="<c:out value="${status.current.docType}"/>">
   <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].origMfgLot" id="origMfgLot<c:out value="${dataCount}"/>" value="<c:out value="${status.current.origMfgLot}"/>">
   <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].receiptId" id="receiptId<c:out value="${dataCount}"/>" value="<c:out value="${status.current.receiptId}"/>">
   <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].rowNumber" id="rowNumber<c:out value="${dataCount}"/>" value="<c:out value="${dataCount}"/>">
   <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].branchPlant" id="branchPlant<c:out value="${dataCount}"/>" value="<c:out value="${status.current.branchPlant}"/>">
   <fmt:formatDate var="formattedEndDate" value="${kitstatus.current.endDate}" pattern="${dateFormatPattern}"/>
   <fmt:formatDate var="formattedDateOfReceipt" value="${kitstatus.current.dateOfReceipt}" pattern="${dateFormatPattern}"/>
   <%--<fmt:formatDate var="formattedDateOfManufacture" value="${kitstatus.current.dateOfManufacture}" pattern="MM/dd/yyyy"/>
   <fmt:formatDate var="formattedDateOfShipment" value="${kitstatus.current.dateOfShipment}" pattern="MM/dd/yyyy"/>
   <fmt:formatDate var="formattedExpirationDate" value="${kitstatus.current.expireDate}" pattern="MM/dd/yyyy"/>
   <c:if test="${(status.current.indefiniteShelfLife == 'y') || (formattedExpirationDate == '01/01/3000')}" >
     <c:set var="formattedExpirationDate" value='Indefinite'/>
   </c:if>--%>

   <c:choose>
      <c:when test="${inventoryGroupPermission == 'Yes'}">
        <c:set var="checkBoxChecked" value=''/>
        <c:if test="${kitstatus.current.ok != null}" >
          <c:set var="checkBoxChecked" value='checked'/>
        </c:if>

        <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].rowSpan" id="rowSpan<c:out value="${dataCount}"/>" value="<c:out value="${listSize}"/>">
        <%--<input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].origLotStatus" ID="origLotStatus<c:out value="${dataCount}"/>" value="<c:out value="${status.current.lotStatus}"/>">
        <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].transferRequestId" ID="transferRequestId<c:out value="${dataCount}"/>" value="<c:out value="${status.current.transferRequestId}"/>">
        <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].lotStatusRootCause" ID="lotStatusRootCause<c:out value="${dataCount}"/>" value="<c:out value="${status.current.lotStatusRootCause}"/>">
        <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].lotStatusRootCauseNotes" ID="lotStatusRootCauseNotes<c:out value="${dataCount}"/>" value="<c:out value="${status.current.lotStatusRootCauseNotes}"/>">
        <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].responsibleCompanyId" ID="responsibleCompanyId<c:out value="${dataCount}"/>" value="<c:out value="${status.current.responsibleCompanyId}"/>">
        --%>
        <c:choose>
         <c:when test="${mvItem != 'Y'}">
          <td width="2%">
           <c:choose>
            <c:when test="${(docType == 'IT' && kitstatus.current.qcOk == 'N') || (docType == 'IT' && !(kitstatus.current.mfgLot == kitstatus.current.origMfgLot))}" >
             &nbsp;
            </c:when>
            <c:otherwise>
             <input type="checkbox" name="receivingQcViewBean[<c:out value="${dataCount}"/>].ok" id="ok<c:out value="${dataCount}"/>" value="<c:out value="${dataCount}"/>" <c:out value="${checkBoxChecked}"/> onclick="checkNonChemicalReceivingQcInput(<c:out value="${dataCount}"/>)">
            </c:otherwise>
           </c:choose>
          </td>

	       <td width="8%">
           <c:out value="${kitstatus.current.mfgLot}"/>
           <c:if test="${docType == 'IT'}">
                <br/><c:out value="${status.current.origMfgLot}"/>
           </c:if>
          </td>

         <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${formattedEndDate}"/></td>
	       <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${formattedDateOfReceipt}"/>
         <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${kitstatus.current.bin}"/></td>

	       <c:choose>
	       <c:when test="${kitstatus.index == 0 && manageKitsAsSingleUnit == 'N'}">
           <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
            <c:out value="${kitstatus.current.receiptId}"/>
            <c:if test="${docType == 'IT'}">
             <br/><c:out value="${status.current.transferReceiptId}"/>
            </c:if>
         <br/><c:choose>
          <c:when test="${(kitstatus.current.receiptDocumentAvailable == 'y' || kitstatus.current.receiptDocumentAvailable == 'Y')}" >
           <img src="/images/buttons/document.gif" alt="View/Add Receipt Documents" onMouseOver=style.cursor="hand" onclick="javascript:showProjectDocuments('<c:out value="${kitstatus.current.receiptId}"/>','<c:out value="${dataCount}"/>')">
          </c:when>
          <c:otherwise>
            <img src="/images/buttons/plus.gif" alt="Add Receipt Documents" onMouseOver=style.cursor="hand" onclick="javascript:showProjectDocuments('<c:out value="${kitstatus.current.receiptId}"/>','<c:out value="${dataCount}"/>')">
          </c:otherwise>
         </c:choose>
          </td>
          <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${kitstatus.current.quantityReceived}"/></td>
          <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${kitstatus.current.packaging}"/></td>
          <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
            <textarea name="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].notes" id="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].notes" cols="25" rows="3" class="inputBox"><c:out value="${status.current.notes}"/></textarea>
          </td>
          <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
            <input type="text" name="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].deliveryTicket" id="deliveryTicket<c:out value="${dataCount}"/>" value="<c:out value="${status.current.deliveryTicket}"/>" size="6" maxlength="30" class="inputBox">
          </td>
          <td width="2%" rowspan="<c:out value="${mainRowSpan}"/>">
             <input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onclick= "reverseReceipt(<c:out value="${dataCount+listSize-1}"/>)" value="<fmt:message key="label.reverse"/>" name="reverseButton">
          </td>
	       </c:when>
         <c:when test="${manageKitsAsSingleUnit != 'N'}">
          <td width="5%">
           <c:out value="${kitstatus.current.receiptId}"/>
           <c:if test="${docType == 'IT'}">
            <br/><c:out value="${status.current.transferReceiptId}"/>
           </c:if>
          <br/><c:choose>
          <c:when test="${(kitstatus.current.receiptDocumentAvailable == 'y' || kitstatus.current.receiptDocumentAvailable == 'Y')}" >
           <img src="/images/buttons/document.gif" alt="View/Add Receipt Documents" onMouseOver=style.cursor="hand" onclick="javascript:showProjectDocuments('<c:out value="${kitstatus.current.receiptId}"/>','<c:out value="${dataCount}"/>')">
          </c:when>
          <c:otherwise>
            <img src="/images/buttons/plus.gif" alt="Add Receipt Documents" onMouseOver=style.cursor="hand" onclick="javascript:showProjectDocuments('<c:out value="${kitstatus.current.receiptId}"/>','<c:out value="${dataCount}"/>')">
          </c:otherwise>
         </c:choose>
          </td>
          <td width="5%"><c:out value="${kitstatus.current.quantityReceived}"/></td>
	        <td width="5%"><c:out value="${kitstatus.current.packaging}"/></td>
          <td width="5%">
            <textarea name="receivingQcViewBean[<c:out value="${dataCount}"/>].notes" id="receivingQcViewBean[<c:out value="${dataCount}"/>].notes" cols="25" rows="3" class="inputBox"><c:out value="${status.current.notes}"/></textarea>
          </td>
          <td width="5%">
            <input type="text" name="receivingQcViewBean[<c:out value="${dataCount}"/>].deliveryTicket" id="deliveryTicket<c:out value="${dataCount}"/>" value="<c:out value="${status.current.deliveryTicket}"/>" size="6" maxlength="30" class="inputBox">
          </td>
          <td width="2%" rowspan="<c:out value="${mainRowSpan}"/>">
             <input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onclick= "reverseReceipt(<c:out value="${dataCount}"/>)" value="<fmt:message key="label.reverse"/>" name="reverseButton" id="reverseButton">
          </td>
          </c:when>
          </c:choose>
	      </c:when>
        <c:otherwise>   <%--Here starts the variable packaging stuff--%>
         <c:if test="${kitstatus.index == 0}">
          <td width="2%" rowspan="<c:out value="${mainRowSpan}"/>">
          <c:choose>
           <c:when test="${(docType == 'IT' && kitstatus.current.qcOk == 'N') || (docType == 'IT' && !(kitstatus.current.mfgLot == kitstatus.current.origMfgLot))}" >
            &nbsp;
          </c:when>
          <c:otherwise>
           <input type="checkbox" name="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].ok" id="ok<c:out value="${dataCount}"/>" value="<c:out value="${dataCount}"/>" <c:out value="${checkBoxChecked}"/> onclick="checkNonChemicalReceivingQcInput(<c:out value="${dataCount}"/>)">
          </c:otherwise>
          </c:choose>
          </td>

          <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
           <c:out value="${kitstatus.current.mfgLot}"/>
           <c:if test="${docType == 'IT'}">
                <br/><c:out value="${status.current.origMfgLot}"/>
           </c:if>
          </td>

         <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${formattedEndDate}"/></td>
	       <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${formattedDateOfReceipt}"/>
         <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${kitstatus.current.bin}"/></td>
        </c:if>

        <td width="5%">
         <c:out value="${kitstatus.current.pendingReceiptId}"/>
         <c:if test="${docType == 'IT'}">
          <br/><c:out value="${status.current.transferReceiptId}"/>
         </c:if>
         <br/><c:choose>
          <c:when test="${(kitstatus.current.receiptDocumentAvailable == 'y' || kitstatus.current.receiptDocumentAvailable == 'Y')}" >
           <img src="/images/buttons/document.gif" alt="View/Add Receipt Documents" onMouseOver=style.cursor="hand" onclick="javascript:showProjectDocuments('<c:out value="${kitstatus.current.receiptId}"/>','<c:out value="${dataCount}"/>')">
          </c:when>
          <c:otherwise>
            <img src="/images/buttons/plus.gif" alt="Add Receipt Documents" onMouseOver=style.cursor="hand" onclick="javascript:showProjectDocuments('<c:out value="${kitstatus.current.receiptId}"/>','<c:out value="${dataCount}"/>')">
          </c:otherwise>
         </c:choose>
        </td>
        <td width="5%"><c:out value="${kitstatus.current.quantityReceived}"/></td>
	      <td width="5%"><c:out value="${kitstatus.current.packaging}"/></td>
        <c:if test="${kitstatus.index == 0}">
        <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
          <textarea name="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].notes" id="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].notes" cols="25" rows="3" class="inputBox"><c:out value="${status.current.notes}"/></textarea>
        </td>
        <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
          <input type="text" name="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].deliveryTicket" ID="deliveryTicket<c:out value="${dataCount}"/>" value="<c:out value="${status.current.deliveryTicket}"/>" size="6" maxlength="30" Class="DETAILS">
        </td>
        <td width="2%" rowspan="<c:out value="${mainRowSpan}"/>">
           <input type="button"  class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onclick= "reverseReceipt(<c:out value="${dataCount+listSize-1}"/>)" value="<fmt:message key="label.reverse"/>" name="reverseButton">
        </td>
        </c:if>
        </c:otherwise>
	    </c:choose>
	   </c:when>
	   <c:when test="${receivingQcPermission == 'Yes'}">
      <%--Readonly version--%>
      <input type="hidden" name="receivingViewBean[<c:out value="${dataCount}"/>].updateStatus" id="receivingViewBean[<c:out value="${dataCount}"/>].updateStatus" value="readOnly">
      <c:choose>
       <c:when test="${mvItem != 'Y'}">
        <td width="2%"><input type="checkbox" name="receivingQcViewBean[<c:out value="${dataCount}"/>].ok" ID="ok<c:out value="${dataCount}"/>" value="<c:out value="${dataCount}"/>" <c:out value="${checkBoxChecked}"/> onclick="checkLabelQty(<c:out value="${dataCount}"/>)"></td>
        <td width="5%"><c:out value="${kitstatus.current.mfgLot}"/>
          <c:if test="${docType == 'IT'}">
           <br/><c:out value="${status.current.origMfgLot}"/>
          </c:if>
        </td>

         <td width="5%"><c:out value="${formattedEndDate}"/></td>
	       <td width="5%"><c:out value="${formattedDateOfReceipt}"/>
         <td width="5%" class="alignLeft"><c:out value="${kitstatus.current.bin}"/></td>

	       <c:choose>
	       <c:when test="${kitstatus.index == 0 && manageKitsAsSingleUnit == 'N'}">
           <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${kitstatus.current.receiptId}"/>
            <c:if test="${docType == 'IT'}">
             <br/><c:out value="${status.current.transferReceiptId}"/>
            </c:if>
          </td>

          <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${kitstatus.current.quantityReceived}"/></td>
          <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${kitstatus.current.packaging}"/></td>
          <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.notes}"/></td>
          <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.deliveryTicket}"/></td>
          <td width="2%" rowspan="<c:out value="${mainRowSpan}"/>">&nbsp;
          </td>
	       </c:when>
         <c:when test="${manageKitsAsSingleUnit != 'N'}">
          <td width="5%"><c:out value="${kitstatus.current.receiptId}"/>
           <c:if test="${docType == 'IT'}">
            <BR><c:out value="${status.current.transferReceiptId}"/>
           </c:if>
          </td>

          <td width="5%"><c:out value="${kitstatus.current.quantityReceived}"/></td>
	        <td width="5%"><c:out value="${kitstatus.current.packaging}"/></td>
          <td width="5%"><c:out value="${status.current.notes}"/></td>
          <td width="5%"><c:out value="${status.current.deliveryTicket}"/></td>
          <td width="2%" rowspan="<c:out value="${mainRowSpan}"/>">&nbsp;</td>
          </c:when>
          </c:choose>
	      </c:when>
        <c:otherwise>   <%--Here starts the variable packaging stuff--%>
         <c:if test="${kitstatus.index == 0}">
          <td width="2%"><input type="checkbox" name="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].ok" ID="ok<c:out value="${dataCount}"/>" value="<c:out value="${dataCount}"/>" <c:out value="${checkBoxChecked}"/> onclick="checkLabelQty(<c:out value="${dataCount}"/>)"></td>
          <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${kitstatus.current.mfgLot}"/>
           <c:if test="${docType == 'IT'}">
                <br/><c:out value="${status.current.origMfgLot}"/>
           </c:if>
          </td>

         <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${formattedEndDate}"/></td>
	       <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${formattedDateOfReceipt}"/>
        </c:if>
        <td width="5%"><c:out value="${kitstatus.current.pendingReceiptId}"/>
         <c:if test="${docType == 'IT'}">
          <br/><c:out value="${status.current.transferReceiptId}"/>
         </c:if>
        </td>
        <td width="5%"><c:out value="${kitstatus.current.quantityReceived}"/></td>
	      <td width="5%"><c:out value="${kitstatus.current.packaging}"/></td>
        <c:if test="${kitstatus.index == 0}">
          <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.pendingNominalPackaging}"/></td>
          <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.notes}"/></td>
          <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.deliveryTicket}"/></td>
          <td width="2%" rowspan="<c:out value="${mainRowSpan}"/>">&nbsp;</td>
         </c:if>
        </c:otherwise>
	    </c:choose>
	  </c:when>
	  </c:choose>
     <c:set var="dataCount" value='${dataCount+1}'/>
	</c:forEach>
 </tr>
</c:forEach>
</table>


<c:if test="${empty receivingQcViewRelationBeanCollection}" >
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
</td></tr>
</table>
<!-- Search results end -->
</c:if>

<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;">
   <input type="hidden" name="sourceHubName" id="sourceHubName" value="<c:out value="${selectedHubName}"/>">
   <input type="hidden" name="labelReceipts" id="labelReceipts" value="<c:out value="${labelReceipts}"/>">
   <input type="hidden" name="totallines" id="totallines" value="<c:out value="${dataCount}"/>">

   <input type="hidden" name="submitPrint" id="submitPrint" value="">
   <input type="hidden" name="submitReceive" id="submitReceive" value="">
   <input type="hidden" name="userAction" id="userAction" value="">
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
</body>
</html:html>