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
<tcmis:gridFontSizeCss />
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
<script type="text/javascript" src="/js/common/resultiframeresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<%--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>


<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>


<!--  These are for the autocomplete function -->
<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script> 
<script type="text/javascript" src="/js/jquery/autocomplete.js"></script> 
<link rel="stylesheet" type="text/css" href="/css/autocomplete.css" />  

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/admin/poapprover.js"></script>

<title>
<fmt:message key="label.poApproverTitle"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = {
	alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
	validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
	myfacilities:"<fmt:message key="label.myfacilities"/>",success:"<fmt:message key="label.success"/>"};

var expandedNode = '${personnelId}';
var originalPersonnel = '${personnelId}';

var altCompany = [
	{id:"", name:"<fmt:message key="label.mycompanies"/>"}
<c:forEach var="hubvar" items="${UserFacilitySelectOvCollection}" varStatus="status">
    ,{id:"${status.current.companyId}", name:"<tcmis:jsReplace value="${status.current.companyName}"/>"}
</c:forEach>
];

<c:set var="hasOpsEntityPermission" value="N "/>
var opsEntityPerms = {
<c:forEach var="opsEntity" items="${personnelBean.opsHubIgOvBeanCollection}" varStatus="status">
<c:set var="hasPerm" value="N"/>
<tcmis:opsEntityPermission indicator="true" opsEntityId="${status.current.opsEntityId}" userGroupId="POApproverAdmin">
<c:set var="hasPerm" value="Y"/>
<c:if test="${status.current.opsEntityId eq givenOpsEntityId}">
<c:set var="hasOpsEntityPermission" value="Y"/>
</c:if>
</tcmis:opsEntityPermission>
<c:if test="${status.index gt 0}">,</c:if>
'${status.current.opsEntityId}':'${hasPerm}'
</c:forEach>
};

function myOnload() {
	if (approverListEntityId.length > 0) {
		$("opsEntityId").value = approverListEntityId;
	}
	if (opsEntityPerms[$("opsEntityId").value] == "N") {
		$("updateBuyerLimit").style["display"] = "none";
	}
	//$("companyId").value = approverListCompanyId;
	populateGrids();
	
	if ($("unlimited").checked) {
		j$("label:has(#unlimited)").css("color","black");
		$("buyerLimit").style.display = "none";
	}
}

function rightClickRow() {
	
}

var approvalChainData = {};
var approverListData = {};
var approvalChainGrid = null;
var approverListGrid = null;

var approvalChainConfig = {
		divName:'poApprovalChainData',
		beanData:'approvalChainData',
		beanGrid:'approvalChainGrid',
		config:'approvalChainColumnConfig',
		rowSpan:false,
		noSmartRender: true,
		submitDefault:false,
		onRightClick:rightClickRow
};

var approverListConfig = {
		divName:'poApproverListData',
		beanData:'approverListData',
		beanGrid:'approverListGrid',
		config:'approverListColumnConfig',
		rowSpan:false,
		noSmartRender: true,
		submitDefault:false,
		onRightClick:rightClickRow
};

var approvalChainColumnConfig = [
{ columnId:"permission" },
{ columnId:"grab", columnName:'<fmt:message key="label.grab"/>', type:"hchstatus", align:"center", width:4, submit:true },
{ columnId:"approverPersonnelId", submit:true },
{ columnId:"approverName", columnName:'<fmt:message key="label.approvername"/>', width:10, align:"center" },
{ columnId:"approverLimit", columnName:'<fmt:message key="label.approverlimit"/>', width:7 }
];

var approverListColumnConfig = [
{ columnId:"permission" },
{ columnId:"grab", columnName:'<fmt:message key="label.grab"/>', type:"hchstatus", align:"center", width:4, submit:true },
{ columnId:"approverPersonnelId", submit:true },
{ columnId:"approverName", columnName:'<fmt:message key="label.approvername"/>', width:10, align:"center" },
{ columnId:"approverLimit", columnName:'<fmt:message key="label.approverlimit"/>', width:7 }
];

<c:set var="givenOpsEntityId" value="${empty param.opsEntityId?'':buyer.opsEntityId}"/>
<c:set var="givenCompanyId" value="${empty param.companyId?'':buyer.companyId}"/>

var approverListEntityId = "<tcmis:jsReplace value="${givenOpsEntityId}" processMultiLines="true"/>";
var approverListCompanyId = "<tcmis:jsReplace value="${givenCompanyId}" processMultiLines="true"/>";
// -->
</script>
<%-- set height dynamically END --%>

<fmt:message var="poFinApprUnlimited" key="label.unlimited"/>
<c:set var="unlimitedValue" value="1000000000000000"/>
</head>

<body bgcolor="#ffffff" onload="setDropDowns();myOnload();">

<tcmis:form action="/poapprovermain.do" onsubmit="return submitFrameOnlyOnce();" target="_self">

<div class="interface" id="searchMainPage"> <!-- Start of interface-->

<div class="contentArea"> <!-- Start of contentArea-->

<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <div class="boxhead"> <%-- boxhead Begins --%>
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
      --%>
	   <a id="updateBuyerLimit" href="#" onclick="updateBuyerLimit()"><fmt:message key="label.update"/></a>
	 </div> <%-- boxhead Ends --%>
    <!-- Insert all the search option within this div -->
    <!-- Search Option Table Start -->
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
      <tr>
        <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.operatingentity"/>:</td>
        <td width="30%" class="optionTitleLeft">
          <!-- Use this for dropdowns you build with collections from the database -->
          <input type="hidden" name="oldOpsEntityId" id="oldOpsEntityId" value=""/>
          <select name="opsEntityId" id="opsEntityId" class="selectBox" tabindex="1">
          </select>
         </td>
		<td width="10%" class="optionTitleBoldRight"><fmt:message key="label.buyername"/>:</td>
		<td width="30%" class="optionTitleLeft">
		  <!-- Use this for dropdowns you build with collections from the database -->
          <input type="text" name="buyerName" id="buyerName" class="inputBox" tabindex="3" value="<tcmis:jsReplace value="${buyer.buyerName}"/>"/>
          <input type="hidden" name="buyerPersonnelId" id="buyerPersonnelId" value="<tcmis:jsReplace value="${buyer.buyerPersonnelId}"/>"/>
		</td>
      </tr>
      <tr>
			 <%-- determine whether to display company dropdown --%>
			<c:set var="companySize" value="${fn:length(UserFacilitySelectOvCollection)}"/>
			<c:choose>
				<c:when test="${companySize == 1}">
					<td class="optionTitleLeft" nowrap="nowrap">
					 <c:forEach var="companyFacilityViewBean" items="${UserFacilitySelectOvCollection}" varStatus="status">
						 <input type="hidden" name="companyId" id="companyId" value="${status.current.companyId}"/>
						 <input type="hidden" name="oldcompanyId" id="oldcompanyId" value=""/>
					 </c:forEach>
					 </td><td class="optionTitleLeft" nowrap="nowrap"></td>
				</c:when>
				<c:otherwise>
						<td width="10%" class="optionTitleBoldRight"><fmt:message key="label.company"/>:</td>
						  <td width="30%" class="optionTitleLeft">
							 <!-- Use this for dropdowns you build with collections from the database -->
							 <select name="companyId" id="companyId" class="selectBox" tabindex="2">
							 	<option value="HAAS" selected="selected"><fmt:message key="label.haas"/></option>
							 </select>
							 <input type="hidden" name="oldcompanyId" id="oldcompanyId" value=""/>
						 </td>
				</c:otherwise>
			 </c:choose>
			 <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.approvallimit"/>:</td>
			 <td width="30%" class="optionTitleLeft">
	           <label for="unlimited" style="color:darkgray">
	             <input type="checkbox" id="unlimited" name="unlimited" class="radioBtns" onselect="unlimitBuyer()" tabindex="5" ${buyer.buyerLimit eq unlimitedValue?'checked':''}/>
	             <fmt:message key="label.unlimited"/>
	           </label>
	           <input type="text" id="buyerLimit" name="buyerLimit" class="inputBox" tabindex="4" value="<tcmis:jsReplace value="${buyer.buyerLimit}"/>"/>
	           <input type="hidden" id="savedBuyerLimit" name="savedBuyerLimit" value="<tcmis:jsReplace value="${buyer.buyerLimit}"/>"/>
	           <span id="homeCurrencyDisp"><c:out value="${buyer.homeCurrencyId}"/></span>
	           <input id="homeCurrencyId" type="hidden" name="homeCurrencyId" value="<tcmis:jsReplace value="${buyer.homeCurrencyId}"/>"/>
			 </td>
		</tr>
      <tr>
      	<td width="10%" class="optionTitleRight">
			<input name="Search" id="Search" type="button" value="<fmt:message key="label.search"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'"/>
		</td>
	   	<td colspan="2" >
 			<input name="createexcel" id="createexcel" type="submit" value="<fmt:message key="label.createexcel"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="createXSL()"/>
			<input type="button" name="cancel" id="cancel" value="<fmt:message key="label.cancel"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="parent.window.close()"/>
		</td>
      	<td width="30%"></td>
      </tr>
      
    </table>
    <!-- Search Option Table end -->
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table>
<!-- Search Option Ends -->

<!-- Transit Page Begins -->
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br/><br/><br/><fmt:message key="label.pleasewait"/>  
  <br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/>
</div>
<!-- Transit Page Ends -->

<div id="resultGridsDiv">
<!-- Approval Chain Begins -->
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
	   <a href="#" onclick="removeApprover()" style="${hasOpsEntityPermission eq 'Y'?'':'display:none'}"><fmt:message key="label.remove"/></a>
	 </div> <%-- boxhead Ends --%>

    <div class="dataContent">
      <div id="poApprovalChainData" style="width:100%;height:130px;"></div>
      <c:if test="${poApprovalChain != null}">
			<script type="text/javascript">
				<!--
				<c:set var="poChainDataCount" value="${0}"/>
					<c:if test="${!empty poApprovalChain}" >
						var approvalChainData = {
							rows:[<c:forEach var="bean" items="${poApprovalChain}" varStatus="status">
								<c:set var="dataCount" value="${dataCount + 1}"/>
								{ 	id:${status.count},
									data:[	'${hasOpsEntityPermission eq 'Y'?'Y':'N'}',
									      	'',
									      	'${bean.approverPersonnelId}',
									      	'<tcmis:jsReplace value="${bean.approverName}" processMultiLines="false"/>',
									      	'${bean.approverLimit eq unlimitedValue?poFinApprUnlimited:bean.approverLimit}'
									]
								}<c:if test="${!status.last}">,</c:if>
							</c:forEach>
							]};
					</c:if>
				//-->
			</script>
			<!-- If the collection is empty say no data found -->
			<c:if test="${empty poApprovalChain}">
				<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="poApprovalChainTable">
					<tr>
						<td width="100%">
							<fmt:message key="main.nodatafound"/>
						</td>
					</tr>
				</table>
			</c:if>
			<!-- Search results end -->
		</c:if>
      
    </div>

  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
<!-- Approval Chain Ends -->

<!-- Approver List Begins -->
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
	   <a href="#" onclick="addApprover()" style="${hasOpsEntityPermission eq 'Y'?'':'display:none'}"><fmt:message key="label.add"/></a>
	 </div> <%-- boxhead Ends --%>

    <div class="dataContent">
      <div id="poApproverListData" style="width:100%;height:200px;"></div>
      <c:if test="${poApproverList != null}">
			<script type="text/javascript">
				<!--
				<c:set var="dataCount" value="${0}"/>
					<c:if test="${!empty poApproverList}" >
						var approverListData = {
							rows:[<c:forEach var="bean" items="${poApproverList}" varStatus="status">
								<c:set var="dataCount" value="${dataCount + 1}"/>
								{ 	id:${status.count},
									data:[	"${(buyer.buyerLimit lt bean.approverLimit && hasOpsEntityPermission eq 'Y')?'Y':'N'}",
									      	'',
									      	'${bean.approverPersonnelId}',
									      	'<tcmis:jsReplace value="${bean.approverName}" processMultiLines="false"/>',
									      	'${bean.approverLimit eq unlimitedValue?poFinApprUnlimited:bean.approverLimit}'
									]
								}<c:if test="${!status.last}">,</c:if>
							</c:forEach>
							]};
					</c:if>
				//-->
			</script>
			<!-- If the collection is empty say no data found -->
			<c:if test="${empty poApproverList}">
				<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="poApproverListTable">
					<tr>
						<td width="100%">
							<fmt:message key="main.nodatafound"/>
						</td>
					</tr>
				</table>
			</c:if>
			<!-- Search results end -->
		</c:if>
    </div>

  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
<!-- Approver List Ends -->
</div>

<!-- Error Messages Begins -->
<!-- Build this section only if there is an error message to display.
     For the search section, we show the error messages within its frame
-->
<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
<div class="spacerY">&nbsp;
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
</c:if>
<!-- Error Messages Ends -->

</div> <!-- close of contentArea -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input type="hidden" name="uAction" id="uAction" value="search"/>
<input type="hidden" name="hide" id="hide" value="show"/>
<input type="hidden" name="personnelId" id="personnelId" value="${personnelId}" />
<input type="hidden" name="userId" id="userId" value="${userId}" />
<input type="hidden" name="opsEntityName" id="opsEntityName" value="" />
<input type="hidden" name="opsEntityPerms" id="opsEntityPerms" value="${hasPerm}"/>
<input type="hidden" name="fullName" id="fullName" value="<tcmis:jsReplace value="${param.fullName}" processMultiLines='true'/>" />
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->
</tcmis:form>
</body>
</html:html>