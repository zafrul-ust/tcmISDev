<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

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
<%@ include file="/common/opshubig.jsp" %>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss overflow="notHidden"/>
<!-- CSS for YUI -->
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
<!-- Add any other stylesheets you need for the page here -->

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>



<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>

<%@ include file="/common/rightclickmenudata.jsp" %>

<script type="text/javascript" src="/js/hub/receivingqc.js"></script>
<script type="text/javascript" src="/js/hub/bindata.js"></script>
<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
<script type="text/javascript" src="/js/calendar/calendarval.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>

<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<title>
<fmt:message key="label.receivingqc"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
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
viewpurchaseorder:"<fmt:message key="label.viewpurchaseorder"/>", 
nopermissionstoqcstatus:"<fmt:message key="label.nopermissionstoqcstatus"/>",
cannotselectreceiptwith:"<fmt:message key="label.cannotselectreceiptwith"/>",
differentmlitem:"<fmt:message key="label.differentmlitem"/>",
pendingnewchemrequest:"<fmt:message key="label.pendingnewchemrequest"/>",
nopermissiontochangestatus:"<fmt:message key="label.nopermissiontochangestatus"/>",
cannotselectreceiptwith:"<fmt:message key="label.cannotselectreceiptwith"/>",
differentmlitem:"<fmt:message key="label.differentmlitem"/>",
pendingnewchemrequest:"<fmt:message key="label.pendingnewchemrequest"/>",
labelquantity:"<fmt:message key="label.labelquantity"/>",
mustBeInteger:"<fmt:message key="label.errorinteger"/>",
mustbeanumberinthisfield:"<fmt:message key="label.mustbeanumberinthisfield"/>",
actsupshpdate:"<fmt:message key="label.actsupshpdate"/>",
potitle:"<fmt:message key="receivinghistory.label.approved.potitle"/>",
itemtitle:"<fmt:message key="receivinghistory.label.approved.itemtitle"/>",
receiptspecs:"<fmt:message key="label.receiptspecs"/>",
viewaddreceipts:"<fmt:message key="pickingqc.viewaddreceipts"/>",
viewcustomerreturnrequest:"<fmt:message key="label.viewcustomerreturnrequest"/>",
viewrma:"<fmt:message key="label.viewrma"/>",
itemnotes:"<fmt:message key="itemnotes.title"/>",
receivingchecklist:"<fmt:message key="label.receivingchecklist"/>",
customerreturnrequest:"<fmt:message key="customerreturnrequest.title"/>",
indefinite:"<fmt:message key="label.indefinite"/>",
expdatelessthanminexpdate:"<fmt:message key="label.expdatelessthanminexpdate"/>",
qastatement:"<fmt:message key="label.qastatement"/>",
norowselected:"<fmt:message key="label.norowselected"/>",
sendinghubwillbealtered:"<fmt:message key="label.sendinghubwillbealtered"/>",
startmarstest:"<fmt:message key="label.startmarstest"/>",
marsdetail:"<fmt:message key="label.marsdetail"/>",
groupReceiptDoc:"<fmt:message key="label.groupReceiptDoc"/>",
shipbeforemanufacture:'<fmt:message key="label.shipbeforemanufacture"/>',
dom:"<fmt:message key="receivedreceipts.label.dom"/>",
dor:"<fmt:message key="receivedreceipts.label.dor"/>",
dos:"<fmt:message key="label.manufacturerdos"/>",
showlegend:"<fmt:message key="label.showlegend"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
newreceivingchecklist:"<fmt:message key="receivingQcCheckList"/>"
};

defaults.ops.nodefault = true;
defaults.hub.nodefault = true;

with(milonic=new menuname("receivingqcMenu")){
 top="offset=2"
 style = contextStyle;
 margin=3
 aI("text=<fmt:message key="receivinghistory.label.approved.potitle"/>;url=javascript:showrecforinvtransfrQc();");
 aI("text=<fmt:message key="receivinghistory.label.approved.itemtitle"/>;url=javascript:showPreviousReceivedQc();");
 aI("text=<fmt:message key="label.receiptspecs"/>;url=javascript:receiptSpecs();");
 aI("text=<fmt:message key="pickingqc.viewaddreceipts"/>;url=javascript:showProjectDocuments();");
}
/*
with(milonic=new menuname("docTypeNotIT")){
 top="offset=2"
 style = contextStyle;
 margin=3
 aI("text=<fmt:message key="receivinghistory.label.approved.itemtitle"/>;url=javascript:showPreviousReceivedQc();");
 aI("text=<fmt:message key="label.receiptspecs"/>;url=javascript:receiptSpecs();");
 aI("text=<fmt:message key="pickingqc.viewaddreceipts"/>;url=javascript:showProjectDocuments();");
}
*/
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

var searchMyArr = [                              
                   { value:'is', text:'<fmt:message key="label.is"/>'},
                   { value:'contains', text:'<fmt:message key="label.contains"/>'},
                   { value:'startsWith', text:'<fmt:message key="label.startswith"/>'},
                   { value:'endsWith', text:'<fmt:message key="label.endswith"/>'}
                  ];  

// -->
</script>

<script language="JavaScript" type="text/javascript">
<!--
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
 </c:forEach>  */
// -->
</script>
</head>


<c:set var="submitPrint" value='${param.submitPrint}'/>
<c:set var="submitDocumentLabelsPrint" value='${param.submitDocumentLabelsPrint}'/>
<c:set var="submitReceiptLabelsPrint" value='${param.submitReceiptLabelsPrint}'/>
<c:set var="submitSearchTransferRequest" value='${param.submitSearchTransferRequest}'/>
<c:set var="doexcelpopup" value='${doexcelpopup}'/>
<c:set var="selectedHub" value='${param.sourceHub}'/>
<c:choose>
  <c:when test="${!empty submitDocumentLabelsPrint}" >
   <body bgcolor="#ffffff" onLoad="printReceivingQcDocLabels(${selectedHub});setOps();setDefault();setSearchWhat(searchWhatChemArray);" onunload="closeAllchildren();">
  </c:when>
  <c:when test="${!empty submitReceiptLabelsPrint}" >
   <body bgcolor="#ffffff" onLoad="printReceivingQcReceiptLabels(${selectedHub});setOps();setDefault();setSearchWhat(searchWhatChemArray);" onunload="closeAllchildren();">
  </c:when>
  <c:when test="${!empty submitPrint}" >
   <body bgcolor="#ffffff" onLoad="printReceivingQcLabels(${selectedHub});setOps();setDefault();setSearchWhat(searchWhatChemArray);" onunload="closeAllchildren();">
  </c:when>
  <c:when test="${!empty submitSearchTransferRequest}" >
   <body bgcolor="#ffffff" onLoad="setOps();setDefault();setSearchWhat(searchWhatChemArray);searchWhat='${param.transferRequestId}';searchTransferRequestId();" >
  </c:when>
  <c:otherwise>
   <body bgcolor="#ffffff" onLoad="setOps();setDefault();setSearchWhat(searchWhatChemArray);" onunload="closeAllchildren();">
  </c:otherwise>
</c:choose>

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
<table id="searchMaskTable" width="650" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
<!-- code starts here -->
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
<tr>
  <td width="5%" class="optionTitleBoldRight">
      <fmt:message key="label.category"/>:
  </td>
  <td width="10%" class="optionTitleLeft">
     <html:select property="category" styleId="category" onchange="changeSearchCriterion();" styleClass="selectBox">
        <html:option value="Chemicals" key="label.chemicals"/>
        <html:option value="Non-Chemicals" key="label.non-chemicals"/>
     </html:select>
  </td>
  <td width="5%" class="optionTitleBoldRight">
     <fmt:message key="label.search"/>:
  </td>
  <td width="8%" class="optionTitleLeft" nowrap>
  	 <select name="searchWhat" id="searchWhat"  onchange="getSelectedSearchWhat(this.value);" class="selectBox">
	 </select>
	 <input type="hidden" name="selectedSearchWhat" id="selectedSearchWhat" value="${param.searchWhat}"/>
     <html:select property="searchType" styleId="searchType" styleClass="selectBox">
			<html:option value="is" ><fmt:message key="label.is"/></html:option>
			<html:option value="startsWith" key="label.startswith"/>
 	 </html:select>
 	 <html:text property="search" styleId="search" size="20" styleClass="inputBox"/>
  </td>
  <td width="5%" class="optionTitleBoldLeft" nowrap>
   &nbsp;
  </td>
</tr>

<tr>
 <td width="5%" class="optionTitleBoldRight" nowrap>
  	<fmt:message key="label.operatingentity"/>:
 </td>
 <td width="10%" class="optionTitleLeft">
 	<select name="opsEntityId" id="opsEntityId" class="selectBox">
	</select>
	<input type="hidden" name="selectedOpsEntityId" id="selectedOpsEntityId" value="${param.opsEntityId}"/>
   
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
 <tcmis:inventoryGroupPermission indicator="true" userGroupId="ReceivingQC" facilityId="${selectedHubName}">
  <c:set var="receivingQcPermission" value='Yes'/>
 </tcmis:inventoryGroupPermission>
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
--%>
 </td>
 <td width="5%" class="optionTitleBoldRight" nowrap>
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
 <td width="5%" class="optionTitleBoldRight" >
 	<fmt:message key="label.inventorygroup"/>:
 </td>
 <td width="10%" class="optionTitleBoldLeft">
 	<select name="inventoryGroup" id="inventoryGroup" class="selectBox">
    </select>
    <input type="hidden" name="selectedInventoryGroup" id="selectedInventoryGroup" value="${param.inventoryGroup}"/>
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
			<c:if test="${status.current.issueGeneration == 'Item Counting'}">
				<c:set var="displayPartNumber" value='Yes'/>
			</c:if>
			<c:if test="${status.current.partNoAssignedAtReceipt == 'Y'}">
				<c:set var="displayPartNumber" value='Yes'/>
			</c:if>
		</c:when>
      <c:otherwise>
        <option value="<c:out value="${currentIg}"/>"><c:out value="${status.current.inventoryGroupName}"/></option>
			<c:if test="${empty selectedIg || selectedIg == 'All'}">
				<c:if test="${status.current.issueGeneration == 'Item Counting'}">
					<c:set var="displayPartNumber" value='Yes'/>
				</c:if>
				<c:if test="${status.current.partNoAssignedAtReceipt == 'Y'}">
					<c:set var="displayPartNumber" value='Yes'/>
				</c:if>
			</c:if>
		</c:otherwise>
    </c:choose>
  </c:forEach>
  <c:if test="${invenGroupCount == 0}">
   <option value=""><fmt:message key="label.all"/></option>
  </c:if>
 </select>
--%>
 </td>
 <td width="5%" colspan="3" class="optionTitleBoldRight">
   &nbsp;
 </td>
 
</tr>

<tr>
<td width="5%" class="optionTitleLeft" colspan="5">
 <html:submit property="submitSearch" styleId="submitSearch" onclick="return searchForm()" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
   <fmt:message key="label.search"/>
 </html:submit>

 <html:submit property="submitCreateReport" styleId="submitCreateReport" onclick="return doexcelpopup()" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
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
      <a href="javascript:showLegend()"><fmt:message key="label.Legend"/></a> |
      <span id="updateMlItemLink" style="">
        <c:if test="${receivingQcPermission == 'Yes'}">
         <a href="#" onclick="changeMlItem(); return false;"><fmt:message key="label.changeitem"/></a> |
        </c:if>
      </span>
      <span id="receivingLabelsLink" style="">
        <a href="#" onclick="printReceivingBoxLabels(); return false;"><fmt:message key="receivedreceipts.button.receivinglabels"/></a> |
      </span>
      <span id="generateLabelsLink" style="">
        <a href="#" onclick="submitGenLabel('submitPrint'); return false;"><fmt:message key="labels.generatelabels"/></a> |
     </span>
     <span id="generateDocumentLabelsLink" style="">
        <a href="#" onclick="submitGenDocLabel('submitPrint'); return false;"><fmt:message key="label.documentlabels"/></a> |
     </span>
     <span id="generateDocumentLabelsLink" style="">
        <a href="#" onclick="submitGenReceiptLabel('submitPrint'); return false;"><fmt:message key="label.receiptdetaillables"/></a>
     </span>
     <c:if test="${receivingQcPermission == 'Yes'}">
     <span id="updateResultLink" style="">
      | <a href="#" onclick="submitConfirm('submitReceive'); return false;"><fmt:message key="button.confirm"/></a> |
     </span>
     </c:if>
     <html:checkbox property="skipKitLabels" styleId="skipKitLabels" value="Yes" styleClass="radioBtns"/><fmt:message key="receivedreceipts.label.skipkitlabels"/>
     </c:if>
    </div>
    <div class="dataContent">

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">
 <c:set var="colorClass" value=''/>
 <c:set var="dataCount" value='${0}'/>

 <c:forEach var="receiptDescriptionViewBean" items="${receivingQcViewRelationBeanCollection}" varStatus="status">
  <c:if test="${status.index % 10 == 0}">
   <tr align="center">
   <th width="5%"><fmt:message key="label.po"/><br>(<fmt:message key="label.customerpo"/>)</th>
   <th width="5%"><fmt:message key="label.poline"/></th>
   <th width="5%"><fmt:message key="label.item"/><br/>(<fmt:message key="label.inventorygroup"/>)</th>
   <th width="12%"><fmt:message key="label.description"/></th>
   <th width="5%"><fmt:message key="label.receiptspecs"/></th>
   <c:if test="${receivingQcPermission == 'Yes'}">
    <th width="2%"><fmt:message key="label.ok"/></th>
    <th width="8%"><fmt:message key="label.mfglot"/><br/>(<fmt:message key="receivingqc.label.origlot"/>)</th>
    <th width="5%"><fmt:message key="label.lotstatus"/></th>
    <th width="5%"><fmt:message key="label.actsupshpdate"/></th>
    <th width="5%"><fmt:message key="receivedreceipts.label.dor"/></th>
    <th width="5%"><fmt:message key="receivedreceipts.label.dom"/></th>
    <th width="5%"><fmt:message key="label.dateofrepackaging"/></th>
    <th width="5%"><fmt:message key="label.manufacturerdos"/></th>
    <th width="5%"><fmt:message key="label.minexpdate"/></th>
    <th width="5%"><fmt:message key="label.expdate"/></th>
    <th width="4%" colspan="3"><fmt:message key="label.bin"/></th>
    <th width="5%"><fmt:message key="receiving.label.qtyreceived"/></th>
    <th width="8%"><fmt:message key="label.packaging"/></th>
    <th width="2%"><fmt:message key="receiving.label.closepoline"/></th>
    <th width="4%"><fmt:message key="label.receiptid"/><BR>(<fmt:message key="receivingqc.label.transreceiptid"/>)</th>
    <th width="5%"><fmt:message key="receivingqc.label.nooflabels"/></th>
    <th width="5%" colspan="2"><fmt:message key="receiving.label.packagedqty"/>
     x <fmt:message key="receiving.label.packagedsize"/></th>
    <th width="5%"><fmt:message key="label.notes"/></th>
    <th width="5%"><fmt:message key="receiving.label.deliveryticket"/></th>
    <th width="5%"><fmt:message key="label.addqualitynote"/></th>
   <!-- <th width="5%"><fmt:message key="label.qastatement"/></th>--> 
    <th width="5%"><fmt:message key="label.unitlabeledper129p"/></th>
        <th width="2%"><fmt:message key="label.cagecode"/></th>
    <th width="2%"><fmt:message key="reversereceipt.title"/></th>
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

  <c:set var="critical" value='${status.current.critical}'/>
  <c:if test="${critical == 'Y' || critical == 'y'}">
   <c:set var="colorClass" value='red'/>
  </c:if>

  <c:if test="${critical == 'S' || critical == 's'}">
   <c:set var="colorClass" value='pink'/>
  </c:if>

  <c:set var="excess" value='${status.current.excess}'/>
  <c:if test="${excess == 'YES' || excess == 'Yes'}">
   <c:set var="colorClass" value='orange'/>
  </c:if>

  <c:if test="${status.current.itemType == 'ML'}">
   <c:set var="colorClass" value='green'/>
  </c:if>

  <c:set var="updateStatus" value='${status.current.updateStatus}'/>
  <c:if test="${updateStatus == 'NO' || updateStatus == 'Error'}">
   <c:set var="colorClass" value='error'/>
  </c:if>

  <tr class="<c:out value="${colorClass}"/>" id="rowId<c:out value="${status.index}"/>" onmouseup="selectRow('${status.index}')">
  <input type="hidden" id="colorClass<c:out value="${status.index}"/>" value="${colorClass}" />
  <input type="hidden" id="selectedDataCount<c:out value="${status.index}"/>" value="${dataCount}">
  <c:set var="kitCollection"  value='${status.current.kitCollection}'/>
  <bean:size id="listSize" name="kitCollection"/>
  <c:set var="mainRowSpan" value='${status.current.rowSpan}' />
  <c:set var="manageKitsAsSingleUnit" value='${status.current.manageKitsAsSingleUnit}'/>
  <c:set var="mvItem" value='${status.current.mvItem}'/>
  <c:set var="docType" value='${status.current.docType}'/>
  <c:set var="qualityControlItem" value='${status.current.qualityControlItem}'/>

  <c:set var="inventoryGroupPermission" value=''/>
  <tcmis:inventoryGroupPermission indicator="true" userGroupId="ReceivingQC" inventoryGroup="${status.current.inventoryGroup}">
   <c:set var="inventoryGroupPermission" value='Yes'/>
  </tcmis:inventoryGroupPermission>

  <c:set var="statusUpdatePermission" value=''/>
  <tcmis:inventoryGroupPermission indicator="true" userGroupId="PickStatusUpd" inventoryGroup="${status.current.inventoryGroup}">
   <c:set var="statusUpdatePermission" value='Yes'/>
  </tcmis:inventoryGroupPermission>

  <c:set var="onlynonPickableStatusPermission" value=''/>
  <tcmis:inventoryGroupPermission indicator="true" userGroupId="onlynonPickableStatus" inventoryGroup="${status.current.inventoryGroup}">
   <c:set var="onlynonPickableStatusPermission" value='Yes'/>
  </tcmis:inventoryGroupPermission>

  <%--<c:if test="${mvItem == 'Y'}">
   <c:set var="inventoryGroupPermission" value=''/>
  </c:if>--%>

  <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
  <span id="poLabel<c:out value="${dataCount}"/>">
  <c:choose>
   <c:when test="${docType == 'IT' && ! empty status.current.customerRmaId}" >
     RMA <c:out value="${status.current.customerRmaId}"/>
   </c:when>      
   <c:when test="${docType == 'IT'}" >
   	 <fmt:message key="label.tr"/> <c:out value="${status.current.transferRequestId}"/>
<%--      <a href=javascript:showrecforinvtransfrQc(<c:out value="${dataCount}"/>)><fmt:message key="label.tr"/> <c:out value="${status.current.transferRequestId}"/></a>--%>
   </c:when>
   <c:when test="${docType == 'IA'}" >
     <c:out value="${status.current.returnPrNumber}"/>-<c:out value="${status.current.returnLineItem}"/>
   </c:when>
   <c:when test="${!empty status.current.intercompanyPo && !empty status.current.intercompanyPoLine}" >
       <c:out value="${status.current.intercompanyPo}"/>
       <br>(<c:out value="${status.current.radianPo}"/>)
   </c:when>      
   <c:otherwise>
    <c:out value="${status.current.radianPo}"/>
   </c:otherwise>
  </c:choose>
  <c:if test="${!empty status.current.poNumber}">
   <br>(<c:out value="${status.current.poNumber}"/>)
  </c:if>
  </span>
  </td>

  <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
  <c:choose>
   <c:when test="${docType == 'IT' || docType == 'IA'}">
    &nbsp;
   </c:when>
   <c:when test="${!empty status.current.intercompanyPo && !empty status.current.intercompanyPoLine}" >
       <c:out value="${status.current.intercompanyPoLine}"/>
       <br>(<c:out value="${status.current.poLine}"/>)
   </c:when>
   <c:otherwise>
     <c:out value="${status.current.poLine}"/>
   </c:otherwise>
  </c:choose>
  </td>

  <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
   <c:out value="${status.current.itemId}"/>
<%--  <a href="javascript:showPreviousReceivedQc(<c:out value="${dataCount}"/>)"><c:out value="${status.current.itemId}"/></a>--%>  
   <br>(<c:out value="${status.current.inventoryGroupName}"/>)
  </td>

  <c:forEach var="receiptDescriptionViewBean" items="${kitCollection}" varStatus="kitstatus">
   <c:set var="kitUpdateStatus" value='${kitstatus.current.updateStatus}'/>
   <c:if test="${kitUpdateStatus == 'NO' || kitUpdateStatus == 'Error'}">
    <c:set var="colorClass" value='error'/>
   </c:if>
   <c:if test="${kitstatus.index > 0 && listSize > 1 }">
     <tr class="<c:out value="${colorClass}"/>" id="childRowId<c:out value="${status.index}"/><c:out value="${kitstatus.index}"/>">
   </c:if>

    <c:choose>
    <c:when test="${listSize > 1 && manageKitsAsSingleUnit == 'N'}">
     <td width="12%" class="optionTitleLeft"><c:out value="${kitstatus.current.materialDesc}"/></td>
     <td width="5%"  class="optionTitleLeft"><c:out value="${status.current.specs}"/></td>
    </c:when>
    <c:when test="${manageKitsAsSingleUnit != 'N' && kitstatus.index == 0}">
     <td width="12%" class="optionTitleLeft" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.lineDesc}"/></td>
     <td width="5%"  class="optionTitleLeft" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.specs}"/></td>
    </c:when>
    <c:otherwise>
     <td width="12%" class="optionTitleLeft"></td>
     <td width="5%"  class="optionTitleLeft"></td>
    </c:otherwise>
   </c:choose>



   <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].radianPo" id="radianPo<c:out value="${dataCount}"/>" value="<c:out value="${status.current.radianPo}"/>">
   <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].poLine" id="poLine<c:out value="${dataCount}"/>" value="<c:out value="${status.current.poLine}"/>">
   <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].itemId" id="itemId<c:out value="${dataCount}"/>" value="<c:out value="${status.current.itemId}"/>">
   <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].inventoryGroup" id="inventoryGroup<c:out value="${dataCount}"/>" value="<c:out value="${status.current.inventoryGroup}"/>">
   <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].docType" id="docType<c:out value="${dataCount}"/>" value="<c:out value="${status.current.docType}"/>">
   <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].origMfgLot" id="origMfgLot<c:out value="${dataCount}"/>" value="<c:out value="${status.current.origMfgLot}"/>">
   <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].receiptId" id="receiptId<c:out value="${dataCount}"/>" value="<c:out value="${kitstatus.current.receiptId}"/>">
   <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].rowNumber" id="rowNumber<c:out value="${dataCount}"/>" value="<c:out value="${dataCount}"/>">
   <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].receiptGroup" id="receiptGroup<c:out value="${dataCount}"/>" value="<c:out value="${status.current.receiptGroup}"/>">
   <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].branchPlant" id="branchPlant<c:out value="${dataCount}"/>" value="<c:out value="${status.current.branchPlant}"/>">
   <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].newChemRequestId" id="newChemRequestId<c:out value="${dataCount}"/>" value="<c:out value="${status.current.newChemRequestId}"/>">
   <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].itemType" id="itemType<c:out value="${dataCount}"/>" value="<c:out value="${status.current.itemType}"/>">
   <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].catalogId" id="catalogId<c:out value="${dataCount}"/>" value="<c:out value="${status.current.catalogId}"/>">
   <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].catPartNo" id="catPartNo<c:out value="${dataCount}"/>" value="<c:out value="${status.current.catPartNo}"/>">
   <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].catalogCompanyId" id="catalogCompanyId<c:out value="${dataCount}"/>" value="<c:out value="${status.current.catalogCompanyId}"/>">
   <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].unitLabelCatPartNo" id="unitLabelCatPartNo<c:out value="${dataCount}"/>" value="<c:out value="${status.current.unitLabelCatPartNo}"/>">
   <input type="hidden" name="receivingQcViewBean[${dataCount}].customerRmaId" id="customerRmaId${dataCount}" value="${status.current.customerRmaId}">
   <input type="hidden" name="receivingQcViewBean[${dataCount}].qcOk" id="qcOk${dataCount}" value="${status.current.qcOk}">
   <input type="hidden" name="receivingQcViewBean[${dataCount}].transferReceiptId" id="transferReceiptId${dataCount}" value="${status.current.transferReceiptId}">
   <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].transferRequestId" id="transferRequestId<c:out value="${dataCount}"/>" value="<c:out value="${status.current.transferRequestId}"/>">
   <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].qualityControlItem" id="qualityControlItem<c:out value="${dataCount}"/>" value="<c:out value="${status.current.qualityControlItem}"/>">
   <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].intercompanyPo" id="intercompanyPo<c:out value="${dataCount}"/>" value="<c:out value="${status.current.intercompanyPo}"/>">
   <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].intercompanyPoLine" id="intercompanyPoLine<c:out value="${dataCount}"/>" value="<c:out value="${status.current.intercompanyPoLine}"/>">
   <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].incomingTesting" id="incomingTesting<c:out value="${dataCount}"/>" value="<c:out value="${status.current.incomingTesting}"/>">
   <%-- <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].lockExpireDate" id="lockExpireDate<c:out value="${dataCount}"/>" value="<c:out value="${status.current.lockExpireDate}"/>">
    --%>
   <c:choose>
      <c:when test="${inventoryGroupPermission == 'Yes'}">
        <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].updateStatus" id="receivingQcViewBean[<c:out value="${dataCount}"/>].updateStatus" value="<c:out value="${updateStatus}"/>">
        <c:set var="checkBoxChecked" value=''/>
        <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].origLotStatus" id="origLotStatus<c:out value="${dataCount}"/>" value="<c:out value="${status.current.lotStatus}"/>">
        <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].rowSpan" id="rowSpan<c:out value="${dataCount}"/>" value="<c:out value="${listSize}"/>">
        <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].lotStatusRootCause" id="lotStatusRootCause<c:out value="${dataCount}"/>" value="<c:out value="${status.current.lotStatusRootCause}"/>">
        <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].lotStatusRootCauseNotes" id="lotStatusRootCauseNotes<c:out value="${dataCount}"/>" value="<c:out value="${status.current.lotStatusRootCauseNotes}"/>">
        <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].responsibleCompanyId" id="responsibleCompanyId<c:out value="${dataCount}"/>" value="<c:out value="${status.current.responsibleCompanyId}"/>">

        <c:choose>
         <c:when test="${mvItem != 'Y'}">
          <c:if test="${kitstatus.current.ok != null}" >
           <c:set var="checkBoxChecked" value='checked'/>
          </c:if>

          <fmt:formatDate var="formattedShipDate" value="${kitstatus.current.vendorShipDate}" pattern="${dateFormatPattern}"/>
          <fmt:formatDate var="formattedDateOfReceipt" value="${kitstatus.current.dateOfReceipt}" pattern="${dateFormatPattern}"/>
          <fmt:formatDate var="formattedDateOfManufacture" value="${kitstatus.current.dateOfManufacture}" pattern="${dateFormatPattern}"/>
          <fmt:formatDate var="formattedDateOfRepackaging" value="${kitstatus.current.dateOfRepackaging}" pattern="${dateFormatPattern}"/>
          <fmt:formatDate var="formattedDateOfShipment" value="${kitstatus.current.dateOfShipment}" pattern="${dateFormatPattern}"/>
          <fmt:formatDate var="localeExpireDate" value="${kitstatus.current.expireDate}" pattern="${dateFormatPattern}"/>
          <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].receiptShelfLifeBasis" id="receiptShelfLifeBasis<c:out value="${dataCount}"/>" value="<c:out value="${kitstatus.current.receiptShelfLifeBasis}"/>">
          
          <fmt:formatDate var="fmtExpireDate" value="${kitstatus.current.expireDate}" pattern="MM/dd/yyyy"/>
        <c:choose>
          <c:when test="${fmtExpireDate == '01/01/3000'}" >
             <c:set var="formattedExpirationDate">
     	   			<fmt:message key="label.indefinite"/>
     	   	</c:set>
          </c:when>
          <c:otherwise>
             <c:set var="formattedExpirationDate" value="${localeExpireDate}"/>
          </c:otherwise>
        </c:choose>
        
<c:set var="checkBoxPermission" value='N'/>
<tcmis:inventoryGroupPermission indicator="true" userGroupId="transferReconciliation" inventoryGroup="${status.current.inventoryGroup}">
	<c:set var="checkBoxPermission" value='Y'/>
</tcmis:inventoryGroupPermission> 

          <td width="2%">
           <c:choose>
            <c:when test="${(docType == 'IT' && kitstatus.current.qcOk == 'N' && checkBoxPermission == 'Y')}" >
             <input type="checkbox" name="receivingQcViewBean[<c:out value="${dataCount}"/>].ok" id="ok<c:out value="${dataCount}"/>" value="<c:out value="${dataCount}"/>" <c:out value="${checkBoxChecked}"/> onclick="checkChemicalReceivingQcInput(<c:out value="${dataCount}"/>,'warning')" >
            </c:when>
            <c:when test="${(docType == 'IT' && kitstatus.current.qcOk == 'N')}" >
             &nbsp;
            </c:when>
            <c:when test="${!empty kitstatus.current.origMfgLot && !(kitstatus.current.mfgLot == kitstatus.current.origMfgLot)}" >
             <input type="checkbox" name="receivingQcViewBean[<c:out value="${dataCount}"/>].ok" id="ok<c:out value="${dataCount}"/>" value="<c:out value="${dataCount}"/>" <c:out value="${checkBoxChecked}"/> onclick="checkChemicalReceivingQcInput(<c:out value="${dataCount}"/>,'warning')" disabled>
            </c:when>
            <c:otherwise>
             <input type="checkbox" name="receivingQcViewBean[<c:out value="${dataCount}"/>].ok" id="ok<c:out value="${dataCount}"/>" value="<c:out value="${dataCount}"/>" <c:out value="${checkBoxChecked}"/> onclick="checkChemicalReceivingQcInput(<c:out value="${dataCount}"/>,'warning')" >
            </c:otherwise>
           </c:choose>
          </td>
<input type="hidden"  id="mainRowSpan<c:out value="${dataCount}"/>" value="${mainRowSpan}" >
	       <td width="8%">
           <input type="text" name="receivingQcViewBean[<c:out value="${dataCount}"/>].mfgLot" id="mfgLot<c:out value="${dataCount}"/>" value="<c:out value="${kitstatus.current.mfgLot}"/>" size="20" maxlength="36" class="inputBox" onchange="checkOriginalLot(<c:out value="${dataCount}"/>)">
           <%--<c:if test="${docType == 'IT'}">--%>
                <br/><c:out value="${status.current.origMfgLot}"/>
           <%--</c:if>--%>
          </td>

          <c:if test="${kitstatus.index == 0}">
           <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
             <select name="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].lotStatus" id="lotStatus<c:out value="${dataCount}"/>" class="selectBox" onchange="checkReceiptStatus(<c:out value="${dataCount}"/>)">
               <c:forEach var="vvLotStatusBean" items="${vvLotStatusBeanCollection}" varStatus="vvlotstatus">
                <c:set var="lotStatusValue" value='${vvlotstatus.current.lotStatus}'/>
                <c:choose>
                 <c:when test="${(statusUpdatePermission == 'Yes'  || qualityControlItem !='Y' ) && onlynonPickableStatusPermission != 'Yes'}">
                 </c:when>
                <c:otherwise>
                  <c:if test="${vvlotstatus.current.certified == 'Y' || (vvlotstatus.current.pickable == 'Y' && vvlotstatus.current.allocationOrder !=0)}">
                     <c:set var="lotStatusValue" value=''/>
                  </c:if>
                 </c:otherwise>
                </c:choose>
                <c:set var="jspLabelEsc" value=""/>
   				<c:if test="${fn:length(vvlotstatus.current.jspLabel) > 0}"><c:set var="jspLabel">${vvlotstatus.current.jspLabel}</c:set>
   				<c:set var="jspLabelEsc"><fmt:message key="${jspLabel}"/></c:set>					
   				</c:if>
                <c:choose>
       				<c:when test="${status.current.lotStatus == vvlotstatus.current.lotStatus}" >
						<option value="${lotStatusValue}" selected><tcmis:jsReplace value="${jspLabelEsc}" processMultiLines="false" /></option>
					</c:when>
					<c:otherwise>
						<option value="${lotStatusValue}"><tcmis:jsReplace value="${jspLabelEsc}" processMultiLines="false" /></option>
					</c:otherwise>
				</c:choose>
               </c:forEach>
              </select>
           </td>
          </c:if>
          
          <td width="5%" nowrap>
	         <input type="text" readonly name="receivingQcViewBean[<c:out value="${dataCount}"/>].vendorShipDate" id="vendorShipDate${dataCount}"
	           onClick="return getCalendar(document.genericForm.vendorShipDate${dataCount},null,null,null,(document.genericForm.dateOfReceipt${dataCount}.value=='')?document.genericForm.today:document.genericForm.dateOfReceipt${dataCount});"
               value="${formattedShipDate}" size="8" maxlength="10" class="inputBox pointer">
	       </td>
	       <td width="5%" nowrap>
	         <input name='receivingQcViewBean[${dataCount}].beforeSystemDate'
							    id='beforeSystemDate${dataCount}' type="hidden"
							    value='<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>'  /> 
	         <input type="text" readonly name="receivingQcViewBean[<c:out value="${dataCount}"/>].dateOfReceipt" id="dateOfReceipt${dataCount}"
	           onClick="return getCalendar(document.genericForm.dateOfReceipt${dataCount},document.genericForm.date60,null,null,document.genericForm.today);"
	           value="${formattedDateOfReceipt}" size="8" maxlength="10" class="inputBox pointer">
	       </td>
	       <td width="5%" nowrap>
	         <input type="text" readonly name="receivingQcViewBean[<c:out value="${dataCount}"/>].dateOfManufacture" id="dateOfManufacture${dataCount}" value="${formattedDateOfManufacture}" 
	            onclick="return getCalendar(document.genericForm.dateOfManufacture<c:out value="${dataCount}"/>,null,null,null,(document.genericForm.dateOfShipment${dataCount}.value=='')?document.genericForm.today:document.genericForm.dateOfShipment${dataCount});" size="8" maxlength="10" class="inputBox pointer">	         
	       </td> 
	       <td width="5%" nowrap>
	         <input type="text" readonly name="receivingQcViewBean[<c:out value="${dataCount}"/>].dateOfRepackaging" id="dateOfRepackaging${dataCount}" value="${formattedDateOfRepackaging}" 
	            onclick="return getCalendar(document.genericForm.dateOfRepackaging<c:out value="${dataCount}"/>,document.genericForm.dateOfManufacture${dataCount},null,null,document.genericForm.dateOfReceipt${dataCount});" size="8" maxlength="10" class="inputBox pointer">  
	       </td>
	       <td width="5%" nowrap>
	         <input type="text" readonly name="receivingQcViewBean[<c:out value="${dataCount}"/>].dateOfShipment" id="dateOfShipment${dataCount}" value="${formattedDateOfShipment}"
	           onClick="return getCalendar(document.genericForm.dateOfShipment${dataCount},null,null,document.genericForm.dateOfManufacture${dataCount},document.genericForm.today);" size="8" maxlength="10" class="inputBox pointer">
	       </td>
	       <td width="5%" nowrap>
	         <fmt:formatDate var="minimumExpireDate" value="${kitstatus.current.minimumExpireDate}" pattern="${dateFormatPattern}"/>  
	         ${minimumExpireDate}
	         <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].minimumExpireDate" id="minimumExpireDate${dataCount}" value="${minimumExpireDate}"/>        	       
	       </td>
	       <td width="5%" nowrap>
	         <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].expireDate" id="expireDate${dataCount}" value="${localeExpireDate}"/>        	 
	         <c:choose>  
	         	<c:when test="${kitstatus.current.lockExpireDate != null && kitstatus.current.lockExpireDate != 'Y'}">    
		         	<input type="text" readonly name="receivingQcViewBean[<c:out value="${dataCount}"/>].expireDateString" id="expireDateString${dataCount}" value="${formattedExpirationDate}"
		          	 onClick="return getCalendar(document.genericForm.expireDateString${dataCount},null,null,document.genericForm.todayoneyear,null,'Y');"
		          	 onchange="expiredDateChanged(${dataCount});"  size="8" maxlength="10" class="inputBox pointer">
		        </c:when>
		        <c:otherwise>
		         	<input type="hidden" readonly name="receivingQcViewBean[<c:out value="${dataCount}"/>].expireDateString" id="expireDateString${dataCount}" value="${formattedExpirationDate}">
		        	${formattedExpirationDate}
		        </c:otherwise>
	          </c:choose>
	       </td>

         <td width="1%">
           <input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="addBin<c:out value="${dataCount}"/>" value="+" OnClick="showVvHubBins(<c:out value="${dataCount}"/>)" >
         </td>
	       <td width="4%" class="optionTitleLeft">
	        <select name="receivingQcViewBean[<c:out value="${dataCount}"/>].bin" id="bin<c:out value="${dataCount}"/>" class="selectBox" >
              <option value="${kitstatus.current.bin}">${kitstatus.current.bin}</option>
            </select>
<%-- 
	        <c:set var="binCollection"  value='${status.current.receiptItemPriorBinViewBeanCollection}'/>
          <c:set var="selectedBin" value='${kitstatus.current.bin}'/>
	        <bean:size id="binSize" name="binCollection"/>
	        <select name="receivingQcViewBean[<c:out value="${dataCount}"/>].bin" id="bin<c:out value="${dataCount}"/>" class="selectBox">
	         <c:forEach var="receiptItemPriorBinViewBean" items="${binCollection}" varStatus="binstatus">
            <c:set var="currentBin" value='${binstatus.current.bin}'/>
            <c:choose>
             <c:when test="${currentBin == selectedBin}">
              <option value="<c:out value="${currentBin}"/>" selected><c:out value="${currentBin}"/></option>
             </c:when>
             <c:otherwise>
              <option value="<c:out value="${currentBin}"/>"><c:out value="${currentBin}"/></option>
             </c:otherwise>
            </c:choose>
	         </c:forEach>
	         <c:if test="${binSize == 0}">
	          <option value="NONE"><fmt:message key="label.none"/></option>
	         </c:if>
	        </select>
--%>	        
	       </td>
         <td width="1%">
           <input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" id="moreBin<c:out value="${dataCount}"/>" value="more" onclick="loadBins(${status.current.itemId},'${param.sourceHub}',${dataCount})">
         </td>
         <%--<td width="12%" align="LEFT"><c:out value="${kitstatus.current.bin}"/></td>--%>

	       <c:choose>
	       <c:when test="${kitstatus.index == 0 && manageKitsAsSingleUnit == 'N'}">
           <td width="5%" align="right" rowspan="<c:out value="${mainRowSpan}"/>">
             <c:choose>
             <c:when test="${status.current.ownerCompanyId != null && status.current.docType == 'IA' && status.current.qcOk == 'Y'}">
              <input type="text" name="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].newQuantityReceived" id="newQuantityReceived<c:out value="${dataCount}"/>" value="${kitstatus.current.quantityReceived}" size="4" maxlength="10" class="inputBox">
             </c:when>
             <c:otherwise>
              <c:out value="${kitstatus.current.quantityReceived}"/>
             </c:otherwise>
            </c:choose>
           </td>
           <td width="8%"><c:out value="${kitstatus.current.packaging}"/></td>
           <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
            <c:choose>
             <c:when test="${(status.current.orderQtyUpdateOnReceipt == 'y' || status.current.orderQtyUpdateOnReceipt == 'Y')}" >
              <c:if test="${status.current.closePoLine != null}" >
              <c:set var="checkCloseBoxChecked" value='checked'/>
              </c:if>
              <input type="checkbox" name="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].closePoLine" id="closePoLine<c:out value="${dataCount}"/>" value="<c:out value="${status.current.orderQtyUpdateOnReceipt}"/>" <c:out value="${checkCloseBoxChecked}"/> onclick="checkClosePoLine(<c:out value="${dataCount}"/>)">
             </c:when>
             <c:otherwise>
              &nbsp;
             </c:otherwise>
            </c:choose>
           </td>
           <td width="4%" rowspan="<c:out value="${mainRowSpan}"/>">
            <c:out value="${kitstatus.current.receiptId}"/>
            <%--<c:if test="${docType == 'IT'}">--%>
             <br/><c:out value="${status.current.transferReceiptId}"/>
            <%--</c:if>--%>
            <br/><c:choose>
             <c:when test="${(kitstatus.current.receiptDocumentAvailable == 'y' || kitstatus.current.receiptDocumentAvailable == 'Y')}" >
              <img src="/images/buttons/document.gif" alt="<fmt:message key="pickingqc.viewaddreceipts"/>" title="<fmt:message key="pickingqc.viewaddreceipts"/>" onMouseOver="style.cursor='hand'" onclick="javascript:showProjectDocuments('<c:out value="${kitstatus.current.receiptId}"/>','<c:out value="${dataCount}"/>')">
             </c:when>
             <c:otherwise>
               <img src="/images/buttons/plus.gif" alt="<fmt:message key="pickingqc.addreceipts"/>" title="<fmt:message key="pickingqc.addreceipts"/>" onMouseOver="style.cursor='hand'" onclick="javascript:showProjectDocuments('<c:out value="${kitstatus.current.receiptId}"/>','<c:out value="${dataCount}"/>')">
             </c:otherwise>
            </c:choose>
          </td>

	        <td width="5%"  align="right" rowspan="<c:out value="${mainRowSpan}"/>">
            <input type="text" name="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].labelQuantity" id="labelQuantity<c:out value="${dataCount}"/>" value="${kitstatus.current.quantityReceived}" size="4" maxlength="10" class="inputBox">
          </td>
          <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>" colspan="2">&nbsp;</td>

          <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
            <textarea name="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].notes" id="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].notes" cols="25" rows="3" class="inputBox"><c:out value="${status.current.notes}"/></textarea>
          </td>
          <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
            <input type="text" name="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].deliveryTicket" id="deliveryTicket<c:out value="${dataCount}"/>" value="<c:out value="${status.current.deliveryTicket}"/>" size="6" maxlength="30" class="inputBox">
          </td>
          <td width="5%"  align="center" rowspan="<c:out value="${mainRowSpan}"/>">
            <input type="text" name="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].qualityTrackingNumber" id="qualityTrackingNumber<c:out value="${dataCount+listSize-1}"/>" value="<c:out value="${status.current.qualityTrackingNumber}"/>" size="20" maxlength="100" class="inputBox">
          </td>
         <!-- <td width="5%" align="center" rowspan="<c:out value="${mainRowSpan}"/>">
            <input type="text" name="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].qaStatement" id="qaStatement<c:out value="${dataCount}"/>" value="<c:out value="${status.current.qaStatement}"/>" size="1" maxlength="1" class="inputBox">
          </td>-->
          <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
           <c:choose>
            <c:when test="${kitstatus.current.polchemIg == 'Y' && kitstatus.current.doNumberRequired=='N'}" >
             <input type="checkbox" name="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].unitLabelPrinted" id="unitLabelPrinted<c:out value="${dataCount+listSize-1}"/>" value="<c:out value="${dataCount+listSize-1}"/>" onclick="unitLabelPartNumber(<c:out value="${dataCount+listSize-1}"/>)">
            </c:when>
            <c:otherwise>
             &nbsp;
            </c:otherwise>
           </c:choose>
          </td>
          <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
             <input type="text" name="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].supplierCageCode" id="supplierCageCode<c:out value="${dataCount+listSize-1}"/>" value="<c:out value="${status.current.supplierCageCode}"/>" size="8" maxlength="100" class="inputBox"/>
          </td>
          <td width="2%" rowspan="<c:out value="${mainRowSpan}"/>">
             <input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onclick= "reverseReceipt(<c:out value="${dataCount+listSize-1}"/>)" value="<fmt:message key="label.reverse"/>" name="reverseButton" id="reverseButton">
          </td>
	       </c:when>
         <c:when test="${kitstatus.index > 0 && manageKitsAsSingleUnit == 'N'}">
           <td width="8%"><c:out value="${kitstatus.current.packaging}"/></td>
         </c:when>
         <c:when test="${manageKitsAsSingleUnit != 'N'}">
          <td align="right" width="5%">
            <c:choose>
             <c:when test="${status.current.ownerCompanyId != null && status.current.docType == 'IA' && status.current.qcOk == 'Y'}">
              <input type="text" name="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].newQuantityReceived" id="newQuantityReceived<c:out value="${dataCount}"/>" value="${kitstatus.current.quantityReceived}" size="4" maxlength="10" class="inputBox">
             </c:when>
             <c:otherwise>
              <c:out value="${kitstatus.current.quantityReceived}"/>
             </c:otherwise>
            </c:choose>
          </td>
          <td width="8%"><c:out value="${kitstatus.current.packaging}"/></td>
          <td width="5%">
            <c:choose>
             <c:when test="${(status.current.orderQtyUpdateOnReceipt == 'y' || status.current.orderQtyUpdateOnReceipt == 'Y')}" >
              <c:if test="${status.current.closePoLine != null}" >
              <c:set var="checkCloseBoxChecked" value='checked'/>
              </c:if>
              <input type="checkbox" name="receivingQcViewBean[<c:out value="${dataCount}"/>].closePoLine" id="closePoLine<c:out value="${dataCount}"/>" value="<c:out value="${status.current.orderQtyUpdateOnReceipt}"/>" <c:out value="${checkCloseBoxChecked}"/> onclick="checkClosePoLine(<c:out value="${dataCount}"/>)">
             </c:when>
             <c:otherwise>
              &nbsp;
             </c:otherwise>
            </c:choose>
           </td>
          <td width="4%">
           <c:out value="${kitstatus.current.receiptId}"/>
           <%--<c:if test="${docType == 'IT'}">--%>
            <br/><c:out value="${status.current.transferReceiptId}"/>
           <%--</c:if>--%>
            <br/><c:choose>
             <c:when test="${(kitstatus.current.receiptDocumentAvailable == 'y' || kitstatus.current.receiptDocumentAvailable == 'Y')}" >
              <img src="/images/buttons/document.gif" alt="<fmt:message key="pickingqc.viewaddreceipts"/>" title="<fmt:message key="pickingqc.viewaddreceipts"/>" onMouseOver="style.cursor='hand'" onclick="javascript:showProjectDocuments('<c:out value="${kitstatus.current.receiptId}"/>','<c:out value="${dataCount}"/>')">
             </c:when>
             <c:otherwise>
               <img src="/images/buttons/plus.gif" alt="<fmt:message key="pickingqc.addreceipts"/>" title="<fmt:message key="pickingqc.addreceipts"/>" onMouseOver="style.cursor='hand'" onclick="javascript:showProjectDocuments('<c:out value="${kitstatus.current.receiptId}"/>','<c:out value="${dataCount}"/>')">
             </c:otherwise>
            </c:choose>
          </td>

	        <td align="right" width="5%">
            <input type="text" name="receivingQcViewBean[<c:out value="${dataCount}"/>].labelQuantity" id="labelQuantity<c:out value="${dataCount}"/>" value="${kitstatus.current.quantityReceived}" size="4" maxlength="10" class="inputBox">
          </td>
          <c:if test="${kitstatus.index == 0}">
           <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>" colspan="2">&nbsp;</td>
          </c:if>
          <td width="5%">
            <textarea name="receivingQcViewBean[<c:out value="${dataCount}"/>].notes" id="receivingQcViewBean[<c:out value="${dataCount}"/>].notes" cols="25" rows="3" class="inputBox"><c:out value="${status.current.notes}"/></textarea>
          </td>
          <td width="5%">
            <input type="text" name="receivingQcViewBean[<c:out value="${dataCount}"/>].deliveryTicket" id="deliveryTicket<c:out value="${dataCount}"/>" value="<c:out value="${status.current.deliveryTicket}"/>" size="6" maxlength="30" class="inputBox">
          </td>
          <td width="5%"  align="center" rowspan="<c:out value="${mainRowSpan}"/>">
            <input type="text" name="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].qualityTrackingNumber" id="qualityTrackingNumber<c:out value="${dataCount+listSize-1}"/>" value="<c:out value="${status.current.qualityTrackingNumber}"/>" size="20" maxlength="100" class="inputBox">
          </td>
         <!-- <td width="5%" align="center">
            <input type="text" name="receivingQcViewBean[<c:out value="${dataCount}"/>].qaStatement" id="qaStatement<c:out value="${dataCount}"/>" value="<c:out value="${status.current.qaStatement}"/>" size="1" maxlength="1" class="inputBox">
          </td> -->
          <td width="5%">
           <c:choose>
            <c:when test="${status.current.polchemIg == 'Y' && status.current.doNumberRequired=='N'}" >
             <input type="checkbox" name="receivingQcViewBean[<c:out value="${dataCount}"/>].unitLabelPrinted" id="unitLabelPrinted<c:out value="${dataCount}"/>" value="<c:out value="${dataCount}"/>" onclick="unitLabelPartNumber(<c:out value="${dataCount}"/>)">
            </c:when>
            <c:otherwise>
             &nbsp;
            </c:otherwise>
           </c:choose>
          </td>
          <td width="5%"  rowspan="<c:out value="${mainRowSpan}"/>">
          	<input type="text" name="receivingQcViewBean[<c:out value="${dataCount}"/>].supplierCageCode" id="supplierCageCode<c:out value="${dataCount}"/>" value="<c:out value="${status.current.supplierCageCode}"/>" size="8" maxlength="100" class="inputBox">
          </td>
          <td width="2%" rowspan="<c:out value="${mainRowSpan}"/>">
             <input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onclick= "reverseReceipt(<c:out value="${dataCount}"/>)" value="<fmt:message key="label.reverse"/>" name="reverseButton">
          </td>
          </c:when>
          </c:choose>
	      </c:when>
        <c:otherwise>   <%--Here starts the variable packaging stuff--%>
         <c:if test="${status.current.ok != null}" >
          <c:set var="checkBoxChecked" value='checked'/>
         </c:if>
         <c:if test="${kitstatus.index == 0}">
         
          <fmt:formatDate var="formattedShipDate" value="${status.current.vendorShipDate}" pattern="${dateFormatPattern}"/>
          <fmt:formatDate var="formattedDateOfReceipt" value="${status.current.dateOfReceipt}" pattern="${dateFormatPattern}"/>
          <fmt:formatDate var="formattedDateOfManufacture" value="${status.current.dateOfManufacture}" pattern="${dateFormatPattern}"/>
          <fmt:formatDate var="formattedDateOfRepackaging" value="${status.current.dateOfRepackaging}" pattern="${dateFormatPattern}"/>
          <fmt:formatDate var="formattedDateOfShipment" value="${status.current.dateOfShipment}" pattern="${dateFormatPattern}"/>
          <fmt:formatDate var="localeExpireDate" value="${status.current.expireDate}" pattern="${dateFormatPattern}"/>
          <fmt:formatDate var="fmtExpireDate" value="${status.current.expireDate}" pattern="MM/dd/yyyy"/>
        <c:choose>
          <c:when test="${fmtExpireDate == '01/01/3000'}" >
             <c:set var="formattedExpirationDate">
     	   			<fmt:message key="label.indefinite"/>
     	   	</c:set>
          </c:when>
          <c:otherwise>
             <c:set var="formattedExpirationDate" value="${localeExpireDate}"/>
          </c:otherwise>
        </c:choose>

          <td width="2%" rowspan="<c:out value="${mainRowSpan}"/>">
          <c:choose>
           <c:when test="${(docType == 'IT' && kitstatus.current.qcOk == 'N' && checkBoxPermission == 'Y')}" >
             <input type="checkbox" name="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].ok" id="ok<c:out value="${dataCount+listSize-1}"/>" value="<c:out value="${dataCount+listSize-1}"/>" <c:out value="${checkBoxChecked}"/> onclick="checkChemicalReceivingQcInput(<c:out value="${dataCount+listSize-1}"/>,'warning')">
           </c:when>
           <c:when test="${(docType == 'IT' && kitstatus.current.qcOk == 'N')}" >
            &nbsp;
           </c:when>
           <c:when test="${!empty kitstatus.current.origMfgLot && !(kitstatus.current.mfgLot == kitstatus.current.origMfgLot)}" >
             	<input type="checkbox" name="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].ok" id="ok<c:out value="${dataCount+listSize-1}"/>" value="<c:out value="${dataCount+listSize-1}"/>" <c:out value="${checkBoxChecked}"/> onclick="checkChemicalReceivingQcInput(<c:out value="${dataCount+listSize-1}"/>,'warning')" disabled>           
           </c:when>
           <c:otherwise>
           		<input type="checkbox" name="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].ok" id="ok<c:out value="${dataCount+listSize-1}"/>" value="<c:out value="${dataCount+listSize-1}"/>" <c:out value="${checkBoxChecked}"/> onclick="checkChemicalReceivingQcInput(<c:out value="${dataCount+listSize-1}"/>,'warning')">
           </c:otherwise>
          </c:choose>
          </td>

          <td width="8%" rowspan="<c:out value="${mainRowSpan}"/>">
           <input type="text" name="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].mfgLot" id="mfgLot<c:out value="${dataCount+listSize-1}"/>" value="<c:out value="${status.current.mfgLot}"/>" size="20" maxlength="36" class="inputBox" onchange="checkOriginalLot(<c:out value="${dataCount+listSize-1}"/>)">
           <%--<c:if test="${docType == 'IT'}">--%>
                <br/><c:out value="${status.current.origMfgLot}"/>
           <%--</c:if>--%>
          </td>

          <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
            <select name="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].lotStatus" id="lotStatus<c:out value="${dataCount+listSize-1}"/>" class="selectBox" onchange="checkReceiptStatus(<c:out value="${dataCount+listSize-1}"/>)">
              <c:forEach var="vvLotStatusBean" items="${vvLotStatusBeanCollection}" varStatus="vvlotstatus">
               <c:set var="lotStatusValue" value='${vvlotstatus.current.lotStatus}'/>
                <c:choose>
                 <c:when test="${(statusUpdatePermission == 'Yes'  || qualityControlItem !='Y' )&& onlynonPickableStatusPermission != 'Yes'}">
                 </c:when>
                 <c:otherwise>
                  <c:if test="${vvlotstatus.current.certified == 'Y' || (vvlotstatus.current.pickable == 'Y' && vvlotstatus.current.allocationOrder !=0)}">
                     <c:set var="lotStatusValue" value=''/>
                  </c:if>
                 </c:otherwise>
                </c:choose>
                <c:set var="jspLabel" value=""/>
   				<c:if test="${fn:length(vvlotstatus.current.jspLabel) > 0}"><c:set var="jspLabel">${vvlotstatus.current.jspLabel}</c:set>
   				<c:set var="jspLabelEsc"><fmt:message key="${jspLabel}"/></c:set>
   				</c:if>
                <c:choose>
                <c:when test="${status.current.lotStatus == vvlotstatus.current.lotStatus}" >
                 <c:if test="${vvlotstatus.current.lotStatus != 'Write Off Approved' && vvlotstatus.current.lotStatus != 'Write Off Rejected' }">
                    <option value="<c:out value="${vvlotstatus.current.lotStatus}"/>" selected="selected"><tcmis:jsReplace value="${jspLabelEsc}" processMultiLines="false" /></option>
                   </c:if>
                </c:when>
                <c:otherwise>
                 <c:if test="${vvlotstatus.current.lotStatus != 'Write Off Approved' && vvlotstatus.current.lotStatus != 'Write Off Rejected' }">
                    <option value="<c:out value="${vvlotstatus.current.lotStatus}"/>"><tcmis:jsReplace value="${jspLabelEsc}" processMultiLines="false" /></option>
                 </c:if>
                </c:otherwise>
               </c:choose>
              </c:forEach>
             </select>
          </td>

     
               <td width="5%" nowrap rowspan="<c:out value="${mainRowSpan}"/>">
                 <input type="text" name="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].vendorShipDate" id="vendorShipDate<c:out value="${dataCount+listSize-1}"/>" 
                   onClick="return getCalendar(document.genericForm.vendorShipDate${dataCount+listSize-1},null,null,null,(document.genericForm.dateOfReceipt${dataCount+listSize-1}.value=='')?document.genericForm.today:document.genericForm.dateOfReceipt${dataCount+listSize-1});" value="${formattedShipDate}" size="8" maxlength="10" class="inputBox pointer">
                  </td>
               <td width="5%" nowrap rowspan="<c:out value="${mainRowSpan}"/>">
                 <input name='receivingQcViewBean[${dataCount}].beforeSystemDate'
							    id='beforeSystemDate${dataCount}' type="hidden"
							    value='<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>'  /> 
                 <input type="text" name="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].dateOfReceipt" id="dateOfReceipt<c:out value="${dataCount+listSize-1}"/>" 
                   onClick="return getCalendar(document.genericForm.dateOfReceipt${dataCount+listSize-1},document.genericForm.date60,null,null,document.genericForm.today);" value="${formattedDateOfReceipt}" size="8" maxlength="10" class="inputBox pointer">
                  </td>
               <td width="5%" nowrap rowspan="<c:out value="${mainRowSpan}"/>">
                 <input type="text" name="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].dateOfManufacture" id="dateOfManufacture<c:out value="${dataCount+listSize-1}"/>" 
                   onclick="return getCalendar(document.genericForm.dateOfManufacture${dataCount+listSize-1},null,null,null,(document.genericForm.dateOfShipment${dataCount+listSize-1}.value=='')?document.genericForm.today:document.genericForm.dateOfShipment${dataCount+listSize-1});" value="<c:out value="${formattedDateOfManufacture}"/>" size="8" maxlength="10" class="inputBox pointer">
                  </td>
               <td width="5%" nowrap rowspan="<c:out value="${mainRowSpan}"/>"> 	
		         <input type="text" readonly name="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].dateOfRepackaging" id="dateOfRepackaging${dataCount+listSize-1}" value="${formattedDateOfRepackaging}" 
		            onclick="return getCalendar(document.genericForm.dateOfRepackaging<c:out value="${dataCount+listSize-1}"/>,document.genericForm.dateOfManufacture${dataCount+listSize-1},null,null,document.genericForm.dateOfReceipt${dataCount+listSize-1});" size="8" maxlength="10" class="inputBox pointer">  
		       </td>
               <td width="5%" nowrap rowspan="<c:out value="${mainRowSpan}"/>">
                 <input type="text" name="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].dateOfShipment" id="dateOfShipment<c:out value="${dataCount+listSize-1}"/>" 
                   onClick="return getCalendar(document.genericForm.dateOfShipment${dataCount+listSize-1},null,null,document.genericForm.dateOfManufacture${dataCount+listSize-1},document.genericForm.today);" value="${formattedDateOfShipment}" size="8" maxlength="10" class="inputBox pointer">
                  </td>
               <td nowrap rowspan="<c:out value="${mainRowSpan}"/>">
                 <fmt:formatDate var="minimumExpireDate" value="${status.current.minimumExpireDate}" pattern="${dateFormatPattern}"/>
                 ${minimumExpireDate}
                 <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].minimumExpireDate" id="minimumExpireDate${dataCount+listSize-1}" value="${minimumExpireDate}"/>        
               </td>
               <td width="5%" nowrap rowspan="<c:out value="${mainRowSpan}"/>">
                 <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].expireDate" id="expireDate${dataCount+listSize-1}" value="${localeExpireDate}"/> 
                  <c:choose>
                    <c:when test="${status.current.lockExpireDate != null && status.current.lockExpireDate != 'Y'}">       
                		 <input type="text" name="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].expireDateString" id="expireDateString<c:out value="${dataCount+listSize-1}"/>" 
                   			onClick="return getCalendar(document.genericForm.expireDateString${dataCount+listSize-1},null,null,(document.genericForm.dateOfReceipt${dataCount+listSize-1}.value=='')?document.genericForm.todayoneyear:document.genericForm.dateOfReceipt${dataCount+listSize-1},null,'Y');" 
                    		onchange="expiredDateChanged(${dataCount+listSize-1});" value="${formattedExpirationDate}" size="8" maxlength="10" class="inputBox pointer">
                   </c:when>
                   <c:otherwise>
                   		${formattedExpirationDate}
                   </c:otherwise> 
                   </c:choose>
               </td>
         
         <td width="1%" rowspan="<c:out value="${mainRowSpan}"/>">
           <input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="addBin<c:out value="${dataCount}"/>" id="addBin<c:out value="${dataCount}"/>" value="+" OnClick="showVvHubBins(<c:out value="${dataCount}"/>)" >
         </td>
	       <td width="4%" align="optionTitleLeft" rowspan="<c:out value="${mainRowSpan}"/>">
	        <select name="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].bin" id="bin<c:out value="${dataCount}"/>" class="selectBox">
	         <option value="${status.current.bin}">${status.current.bin}</option>
			</select>
<%--
        <c:set var="binCollection"  value='${status.current.receiptItemPriorBinViewBeanCollection}'/>
          <c:set var="selectedBin" value='${status.current.bin}'/>
	        <bean:size id="binSize" name="binCollection"/>
	        <select name="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].bin" id="bin<c:out value="${dataCount}"/>" class="selectBox">
	         <c:forEach var="receiptItemPriorBinViewBean" items="${binCollection}" varStatus="binstatus">
            <c:set var="currentBin" value='${binstatus.current.bin}'/>
            <c:choose>
             <c:when test="${currentBin == selectedBin}">
              <option value="<c:out value="${currentBin}"/>" selected="selected"><c:out value="${currentBin}"/></option>
             </c:when>
             <c:otherwise>
              <option value="<c:out value="${currentBin}"/>"><c:out value="${currentBin}"/></option>
             </c:otherwise>
            </c:choose>
	         </c:forEach>
	         <c:if test="${binSize == 0}">
	          <option value="NONE"><fmt:message key="label.none"/></option>
	         </c:if>
	        </select>
--%>	       
	       </td>
	       
         <%--<td width="12%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.bin}"/></td>--%>
         <td width="1%" rowspan="<c:out value="${mainRowSpan}"/>">
           <input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="moreBin<c:out value="${dataCount}"/>" id="moreBin<c:out value="${dataCount}"/>" value="more" onclick="loadBins(${status.current.itemId},'${param.sourceHub}',${dataCount})"/>
         </td>

         <td width="5%" align="right" rowspan="<c:out value="${mainRowSpan}"/>">
            <c:choose>
             <c:when test="${status.current.ownerCompanyId != null && status.current.docType == 'IA' && status.current.qcOk == 'Y'}">
              <input type="text" name="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].newQuantityReceived" id="newQuantityReceived<c:out value="${dataCount}"/>" value="${status.current.totalMvQuantityReceived}" size="4" maxlength="10" class="inputBox">
             </c:when>
             <c:otherwise>
              <c:out value="${status.current.totalMvQuantityReceived}"/>
             </c:otherwise>
            </c:choose>
         </td>
         <td width="8%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.packaging}"/></td>
         <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
          <c:choose>
           <c:when test="${(status.current.orderQtyUpdateOnReceipt == 'y' || status.current.orderQtyUpdateOnReceipt == 'Y')}" >
            <c:if test="${status.current.closePoLine != null}" >
             <c:set var="checkCloseBoxChecked" value='checked'/>
            </c:if>
             <input type="checkbox" name="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].closePoLine" id="closePoLine<c:out value="${dataCount+listSize-1}"/>" value="<c:out value="${status.current.orderQtyUpdateOnReceipt}"/>" <c:out value="${checkCloseBoxChecked}"/> onclick="checkClosePoLine(<c:out value="${dataCount+listSize-1}"/>)">
            </c:when>
            <c:otherwise>
             &nbsp;
            </c:otherwise>
          </c:choose>
         </td>
        </c:if>
        <td width="4%">
         <c:out value="${kitstatus.current.receiptId}"/>
         <c:if test="${docType == 'IT'}">
          <br/><c:out value="${status.current.transferReceiptId}"/>
         </c:if>
         <br/><c:choose>
          <c:when test="${(kitstatus.current.receiptDocumentAvailable == 'y' || kitstatus.current.receiptDocumentAvailable == 'Y')}" >
           <img src="/images/buttons/document.gif" alt="<fmt:message key="pickingqc.viewaddreceipts"/>" title="<fmt:message key="pickingqc.viewaddreceipts"/>"  onMouseOver="style.cursor='hand'" onclick="javascript:showProjectDocuments('<c:out value="${kitstatus.current.receiptId}"/>','<c:out value="${dataCount}"/>')">
          </c:when>
          <c:otherwise>
            <img src="/images/buttons/plus.gif" alt="<fmt:message key="pickingqc.addreceipts"/>" title="<fmt:message key="pickingqc.addreceipts"/>" onMouseOver="style.cursor='hand'" onclick="javascript:showProjectDocuments('<c:out value="${kitstatus.current.receiptId}"/>','<c:out value="${dataCount}"/>')">
          </c:otherwise>
         </c:choose>
        </td>

	      <td width="5%">
          <input type="text" name="receivingQcViewBean[<c:out value="${dataCount}"/>].labelQuantity" id="labelQuantity<c:out value="${dataCount}"/>" value="<c:out value="${kitstatus.current.labelQuantity}"/>" size="4" maxlength="10" class="inputBox">
        </td>
        <td width="5%" align="right" class="optionTitleLeft" colspan="2"><c:out value="${kitstatus.current.quantityReceived}"/> X <c:out value="${kitstatus.current.costFactor}"/>
        <c:out value="${kitstatus.current.purchasingUnitOfMeasure}"/> <c:out value="${kitstatus.current.displayPkgStyle}"/>
        <br/><font class="invisible<c:out value="${colorClass}"/>">_____________</font></td>

        <c:if test="${kitstatus.index == 0}">
          <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
            <textarea name="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].notes" id="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].notes" cols="25" rows="3" class="inputBox"><c:out value="${status.current.notes}"/></textarea>
          </td>
          <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
            <input type="text" name="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].deliveryTicket" id="deliveryTicket<c:out value="${dataCount}"/>" value="<c:out value="${status.current.deliveryTicket}"/>" size="6" maxlength="30" class="inputBox">
          </td>
          <td width="5%"  align="center" rowspan="<c:out value="${mainRowSpan}"/>">
          	<input type="text" name="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].qualityTrackingNumber" id="qualityTrackingNumber<c:out value="${dataCount+listSize-1}"/>" value="<c:out value="${status.current.qualityTrackingNumber}"/>" size="20" maxlength="100" class="inputBox">
          </td>
         <!--  <td width="5%"  align="center" rowspan="<c:out value="${mainRowSpan}"/>">
            <input type="text" name="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].qaStatement" id="qaStatement<c:out value="${dataCount+listSize-1}"/>" value="<c:out value="${status.current.qaStatement}"/>" size="1" maxlength="1" class="inputBox">
          </td>--> 
          <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
           <c:choose>
            <c:when test="${status.current.polchemIg == 'Y' && status.current.doNumberRequired=='N'}" >
             <input type="checkbox" name="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].unitLabelPrinted" id="unitLabelPrinted<c:out value="${dataCount+listSize-1}"/>" value="<c:out value="${dataCount+listSize-1}"/>" onclick="unitLabelPartNumber(<c:out value="${dataCount+listSize-1}"/>)">
            </c:when>
            <c:otherwise>
             &nbsp;
            </c:otherwise>
           </c:choose>
          </td>  
          <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
              <input type="text" name="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].supplierCageCode" id="supplierCageCode<c:out value="${dataCount+listSize-1}"/>" value="<c:out value="${status.current.supplierCageCode}"/>" size="8" maxlength="100" class="inputBox">
          </td>
          <td width="2%" rowspan="<c:out value="${mainRowSpan}"/>">
             <input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onclick= "reverseReceipt(<c:out value="${dataCount+listSize-1}"/>)" value="<fmt:message key="label.reverse"/>" name="reverseButton">
          </td>

         </c:if>
        </c:otherwise>
	    </c:choose>
	   </c:when>
	   <c:when test="${receivingQcPermission == 'Yes'}">
      <%--Readonly version--%>
      <c:set var="checkBoxChecked" value=''/>
      <input type="hidden" name="receivingQcViewBean[<c:out value="${dataCount}"/>].updateStatus" id="receivingQcViewBean[<c:out value="${dataCount}"/>].updateStatus" value="readOnly">
      <c:choose>
       <c:when test="${mvItem != 'Y'}">
        <c:if test="${kitstatus.current.ok != null}" >
         <c:set var="checkBoxChecked" value='checked'/>
        </c:if>
        <fmt:formatDate var="formattedShipDate" value="${kitstatus.current.vendorShipDate}" pattern="${dateFormatPattern}"/>
        <fmt:formatDate var="formattedDateOfReceipt" value="${kitstatus.current.dateOfReceipt}" pattern="${dateFormatPattern}"/>
        <fmt:formatDate var="formattedDateOfManufacture" value="${kitstatus.current.dateOfManufacture}" pattern="${dateFormatPattern}"/>
        <fmt:formatDate var="formattedDateOfRepackaging" value="${kitstatus.current.dateOfRepackaging}" pattern="${dateFormatPattern}"/>
        <fmt:formatDate var="formattedDateOfShipment" value="${kitstatus.current.dateOfShipment}" pattern="${dateFormatPattern}"/>
        
        <fmt:formatDate var="fmtExpireDate" value="${kitstatus.current.expireDate}" pattern="MM/dd/yyyy"/>
        <c:choose>
          <c:when test="${fmtExpireDate == '01/01/3000'}" >
             <c:set var="formattedExpirationDate">
     	   			<fmt:message key="label.indefinite"/>
     	   	</c:set>
          </c:when>
          <c:otherwise>
            <fmt:formatDate var="localeExpireDate" value="${kitstatus.current.expireDate}" pattern="${dateFormatPattern}"/>
             <c:set var="formattedExpirationDate" value="${localeExpireDate}"/>
          </c:otherwise>
        </c:choose>

        <td width="2%"><input type="checkbox" name="receivingQcViewBean[<c:out value="${dataCount}"/>].ok" id="ok<c:out value="${dataCount}"/>" value="<c:out value="${dataCount}"/>" <c:out value="${checkBoxChecked}"/> onclick="checkLabelQuantity(<c:out value="${dataCount}"/>,'warning')"></td>
        <td width="8%"><c:out value="${kitstatus.current.mfgLot}"/>
          <%--<c:if test="${docType == 'IT'}">--%>
           <br/><c:out value="${status.current.origMfgLot}"/>
          <%--</c:if>--%>
        </td>
        <c:if test="${kitstatus.index == 0}">
         <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
               <c:forEach var="vvLotStatusBean" items="${vvLotStatusBeanCollection}" varStatus="vvlotstatus">
                <c:choose>
                 <c:when test="${kitstatus.current.lotStatus == vvlotstatus.current.lotStatus}" >
                 <c:set var="jspLabel">${vvlotstatus.current.jspLabel}</c:set>
   				 <c:set var="jspLabelEsc"><fmt:message key="${jspLabel}"/></c:set>	
				 <tcmis:jsReplace value="${jspLabelEsc}" processMultiLines="false" />
                 </c:when>
                </c:choose>
               </c:forEach>
           </td>
          </c:if>

         <td width="5%"><c:out value="${formattedShipDate}"/></td>
	       <td width="5%"><c:out value="${formattedDateOfReceipt}"/>
	       <td width="5%"><c:out value="${formattedDateOfManufacture}"/>
	       <td width="5%"><c:out value="${formattedDateOfShipment}"/>
	       <td width="5%"><c:out value="${formattedExpirationDate}"/>
          <td width="1%">&nbsp;</td>
		  <td width="4%" colspan="3" class="alignLeft"><c:out value="${kitstatus.current.bin}"/></td>

	       <c:choose>
	       <c:when test="${kitstatus.index == 0 && manageKitsAsSingleUnit == 'N'}">
           <td width="5%" align="right" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${kitstatus.current.quantityReceived}"/></td>
           <td width="8%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${kitstatus.current.packaging}"/></td>
           <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">&nbsp;</td>
           <td width="4%" rowspan="<c:out value="${mainRowSpan}"/>">
            <c:out value="${kitstatus.current.receiptId}"/>
            <%--<c:if test="${docType == 'IT'}">--%>
             <br/><c:out value="${status.current.transferReceiptId}"/>
            <%--</c:if>--%>
            <br/><c:choose>
             <c:when test="${(kitstatus.current.receiptDocumentAvailable == 'y' || kitstatus.current.receiptDocumentAvailable == 'Y')}" >
              <img src="/images/buttons/document.gif" alt="<fmt:message key="pickingqc.viewaddreceipts"/>" title="<fmt:message key="pickingqc.viewaddreceipts"/>" onMouseOver="style.cursor='hand'" onclick="javascript:showProjectDocuments('<c:out value="${kitstatus.current.receiptId}"/>','<c:out value="${dataCount}"/>')">
             </c:when>
             <c:otherwise>
               <img src="/images/buttons/plus.gif" alt="<fmt:message key="pickingqc.addreceipts"/>" title="<fmt:message key="pickingqc.addreceipts"/>" onMouseOver="style.cursor='hand'" onclick="javascript:showProjectDocuments('<c:out value="${kitstatus.current.receiptId}"/>','<c:out value="${dataCount}"/>')">
             </c:otherwise>
            </c:choose>
          </td>

	        <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
            <input type="text" name="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].labelQuantity" id="labelQuantity<c:out value="${dataCount}"/>" value="<c:out value="${status.current.labelQuantity}"/>" size="4" maxlength="10" class="inputBox">
          </td>
          <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>" colspan="2">&nbsp;</td>
          <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.notes}"/></td>
          <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.deliveryTicket}"/></td>
          <!--  <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.qaStatement}"/></td>-->
          <td width="8%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.qualityTrackingNumber}"/></td>
          <td width="2%" rowspan="<c:out value="${mainRowSpan}"/>">&nbsp;</td>
          <td width="2%" rowspan="<c:out value="${mainRowSpan}"/>">&nbsp;</td>
	       </c:when>
         <c:when test="${manageKitsAsSingleUnit != 'N'}">
          <td align="right" width="5%"><c:out value="${kitstatus.current.quantityReceived}"/></td>
          <td width="8%"><c:out value="${kitstatus.current.packaging}"/></td>

          <td width="5%">&nbsp;</td>
          <td width="4%">
           <c:out value="${kitstatus.current.receiptId}"/>
           <%--<c:if test="${docType == 'IT'}">--%>
            <br/><c:out value="${status.current.transferReceiptId}"/>
           <%--</c:if>--%>
            <br/><c:choose>
             <c:when test="${(kitstatus.current.receiptDocumentAvailable == 'y' || kitstatus.current.receiptDocumentAvailable == 'Y')}" >
              <img src="/images/buttons/document.gif" alt="<fmt:message key="pickingqc.viewaddreceipts"/>" title="<fmt:message key="pickingqc.viewaddreceipts"/>" onMouseOver="style.cursor='hand'" onclick="javascript:showProjectDocuments('<c:out value="${kitstatus.current.receiptId}"/>','<c:out value="${dataCount}"/>')">
             </c:when>
             <c:otherwise>
               <img src="/images/buttons/plus.gif" alt="<fmt:message key="pickingqc.addreceipts"/>" title="<fmt:message key="pickingqc.addreceipts"/>"  onMouseOver="style.cursor='hand'" onclick="javascript:showProjectDocuments('<c:out value="${kitstatus.current.receiptId}"/>','<c:out value="${dataCount}"/>')">
             </c:otherwise>
            </c:choose>
          </td>

	        <td width="5%">
            <input type="text" name="receivingQcViewBean[<c:out value="${dataCount}"/>].labelQuantity" id="labelQuantity<c:out value="${dataCount}"/>" value="<c:out value="${status.current.labelQuantity}"/>" size="4" maxlength="10" Class="DETAILS">
          </td>
          <c:if test="${kitstatus.index == 0}">
           <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>" colspan="2">&nbsp;</td>
          </c:if>
          <td width="5%"><c:out value="${status.current.notes}"/></td>
          <td width="5%" align="center"><c:out value="${status.current.deliveryTicket}"/></td>
          <td width="8%" align="center"><c:out value="${status.current.qualityTrackingNumber}"/></td>
          <!-- <td width="5%"><c:out value="${status.current.qaStatement}"/></td>-->
          <td width="2%">&nbsp;</td>
          <td width="2%" rowspan="<c:out value="${mainRowSpan}"/>">&nbsp;</td>
          </c:when>
          </c:choose>
	      </c:when>
        <c:otherwise>   <%--Here starts the variable packaging stuff--%>
         <c:if test="${status.current.ok != null}" >
          <c:set var="checkBoxChecked" value='checked'/>
         </c:if>
         <fmt:formatDate var="formattedShipDate" value="${status.current.vendorShipDate}" pattern="${dateFormatPattern}"/>
         <fmt:formatDate var="formattedDateOfReceipt" value="${status.current.dateOfReceipt}" pattern="${dateFormatPattern}"/>
         <fmt:formatDate var="formattedDateOfManufacture" value="${status.current.dateOfManufacture}" pattern="${dateFormatPattern}"/>
         <fmt:formatDate var="formattedDateOfRepackaging" value="${status.current.dateOfRepackaging}" pattern="${dateFormatPattern}"/>
         <fmt:formatDate var="formattedDateOfShipment" value="${status.current.dateOfShipment}" pattern="${dateFormatPattern}"/>
         
         <fmt:formatDate var="fmtExpireDate" value="${status.current.expireDate}" pattern="MM/dd/yyyy"/>
        <c:choose>
          <c:when test="${fmtExpireDate == '01/01/3000'}" >
             <c:set var="formattedExpirationDate">
     	   			<fmt:message key="label.indefinite"/>
     	   	</c:set>
          </c:when>
          <c:otherwise>
            <fmt:formatDate var="localeExpireDate" value="${status.current.expireDate}" pattern="${dateFormatPattern}"/>
             <c:set var="formattedExpirationDate" value="${localeExpireDate}"/>
          </c:otherwise>
        </c:choose>
         
         <c:if test="${kitstatus.index == 0}">
          <td width="2%" rowspan="<c:out value="${mainRowSpan}"/>"><input type="checkbox" name="receivingQcViewBean[<c:out value="${dataCount+listSize-1}"/>].ok" id="ok<c:out value="${dataCount}"/>" value="<c:out value="${dataCount}"/>" <c:out value="${checkBoxChecked}"/> onclick="checkLabelQuantity(<c:out value="${dataCount}"/>,'warning')"></td>
          <td width="8%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.mfgLot}"/>
           <%--<c:if test="${docType == 'IT'}">--%>
                <br/><c:out value="${status.current.origMfgLot}"/>
           <%--</c:if>--%>
          </td>

          <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
              <c:forEach var="vvLotStatusBean" items="${vvLotStatusBeanCollection}" varStatus="vvlotstatus">
               <c:choose>
                <c:when test="${status.current.lotStatus == vvlotstatus.current.lotStatus}" >
                 <c:set var="jspLabel">${vvlotstatus.current.jspLabel}</c:set>
   				 <c:set var="jspLabelEsc"><fmt:message key="${jspLabel}"/></c:set>	
                 <tcmis:jsReplace value="${jspLabelEsc}" processMultiLines="false" />
                </c:when>
               </c:choose>
              </c:forEach>
          </td>

         <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${formattedShipDate}"/></td>
	       <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${formattedDateOfReceipt}"/>
	       <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${formattedDateOfManufacture}"/>
	       <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${formattedDateOfShipment}"/>
	       <fmt:formatDate var="minimumExpireDate" value="${kitstatus.current.minimumExpireDate}" pattern="${dateFormatPattern}"/>
	       <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${minimumExpireDate}"/>
 
	       <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${formattedExpirationDate}"/>
         <td width="12%" colspan = "3" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.bin}"/></td>
         <td width="5%" align="right" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.totalMvQuantityReceived}"/></td>
         <td width="8%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.packaging}"/></td>
         <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>">&nbsp;</td>
        </c:if>
        <td width="4%">
         <c:out value="${kitstatus.current.receiptId}"/>
         <%--<c:if test="${docType == 'IT'}">--%>
          <br/><c:out value="${status.current.transferReceiptId}"/>
         <%--</c:if>--%>
         <br/><c:choose>
          <c:when test="${(kitstatus.current.receiptDocumentAvailable == 'y' || kitstatus.current.receiptDocumentAvailable == 'Y')}" >
           <img src="/images/buttons/document.gif" alt="<fmt:message key="pickingqc.viewaddreceipts"/>" title="<fmt:message key="pickingqc.viewaddreceipts"/>" onMouseOver="style.cursor='hand'" onclick="javascript:showProjectDocuments('<c:out value="${kitstatus.current.receiptId}"/>','<c:out value="${dataCount}"/>')">
          </c:when>
          <c:otherwise>
            <img src="/images/buttons/plus.gif" alt="<fmt:message key="pickingqc.addreceipts"/>" title="<fmt:message key="pickingqc.addreceipts"/>"  onMouseOver="style.cursor='hand'" onclick="javascript:showProjectDocuments('<c:out value="${kitstatus.current.receiptId}"/>','<c:out value="${dataCount}"/>')">
          </c:otherwise>
         </c:choose>
        </td>

	      <td width="5%">
          <input type="text" name="receivingQcViewBean[<c:out value="${dataCount}"/>].labelQuantity" id="labelQuantity<c:out value="${dataCount}"/>" value="<c:out value="${kitstatus.current.labelQuantity}"/>" size="4" maxlength="10" Class="DETAILS">
        </td>
        <td width="5%" align="right" class="optionTitleLeft" colspan="2"><c:out value="${kitstatus.current.quantityReceived}"/> X <c:out value="${kitstatus.current.costFactor}"/>
        <c:out value="${kitstatus.current.purchasingUnitOfMeasure}"/> <c:out value="${kitstatus.current.displayPkgStyle}"/>
        <br/><font class="invisible<c:out value="${colorClass}"/>">_____________</font></td>

        <c:if test="${kitstatus.index == 0}">
          <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.notes}"/></td>
          <td width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.deliveryTicket}"/></td>
          <td width="8%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.qualityTrackingNumber}"/></td>
          <!-- <td width="5%" align="center" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.qaStatement}"/></td>-->
          <td width="2%" rowspan="<c:out value="${mainRowSpan}"/>">&nbsp;</td>
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

   <!-- If the collection is empty say no data found -->
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
</c:if>

<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;">
   <input type="hidden" name="sourceHubName" id="sourceHubName" value="<c:out value="${selectedHubName}"/>">
   <input type="hidden" name="labelReceipts" id="labelReceipts" value="<c:out value="${labelReceipts}"/>">
   <input type="hidden" name="paperSize" id="paperSize" value="31">
   <input type="hidden" name="totallines" id="totallines" value="<c:out value="${dataCount}"/>">
   <input type="hidden" name="searchTypeSelected" id="searchTypeSelected" value="${param.searchType}">
   <input type="hidden" name="submitPrint" id="submitPrint" value="">
   <input type="hidden" name="submitDocumentLabelsPrint" id="submitDocumentLabelsPrint" value="">
   <input type="hidden" name="submitReceiptLabelsPrint" id="submitReceiptLabelsPrint" value="">
   <input type="hidden" name="submitReceive" id="submitReceive" value="">

   <input type="hidden" name="selectedItem" id="selectedItem" value="">
   <!--<input type="hidden" name="selectedInventoryGroup" id="selectedInventoryGroup" value="">-->
   <input type="hidden" name="selectedCatalogId" id="selectedCatalogId" value="">
   <input type="hidden" name="selectedCatPartNo" id="selectedCatPartNo" value="">
   <input type="hidden" name="selectedCatalogCompanyId" id="selectedCatalogCompanyId" value="">
   <input type="hidden" name="userAction" id="userAction" value="">
<input name='date60' id='date60' type="hidden" value='<tcmis:getDateTag numberOfDaysFromToday="-60" datePattern="${dateFormatPattern}"/>'  /> 
<input name='today' id='today' type="hidden" value='<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>'  />
<input name='todayoneyear' id='todayoneyear' type="hidden" value='<tcmis:getDateTag numberOfDaysFromToday="-365" datePattern="${dateFormatPattern}"/>'  />
<input name='indefiniteDate' id='indefiniteDate' type="hidden" value=''  />
<input name='groupReceiptDoc' id='groupReceiptDoc' type="hidden" value='${param.groupReceiptDoc}'/>
   
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