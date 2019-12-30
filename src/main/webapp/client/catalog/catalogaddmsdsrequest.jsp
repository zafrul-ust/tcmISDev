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
<tcmis:gridFontSizeCss />
<!-- CSS for YUI -->
<%--
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>

<!-- Add any other stylesheets you need for the page here -->
<link rel="stylesheet" type="text/css" href="/css/tabs.css"/>
<link rel="stylesheet" type="text/css" href="/css/autocomplete.css" /> 

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
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- For Autocomplete support -->
<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script> 
<script type="text/javascript" src="/js/jquery/autocomplete.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/client/catalog/catalogaddmsdsrequest.js"></script>
<script type="text/javascript" src="/js/client/catalog/shownewchemappdetail.js"></script>
<script type="text/javascript" src="/js/client/catalog/chemicalapprovaldata.js"></script>
<script type="text/javascript" src="/js/client/catalog/approvalcomments.js"></script>    

<!-- These are for the Grid-->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srndRowSpan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>

<%--This has the custom cells we built,
    hcal - the internationalized calendar which we use
    hlink - this is for any links you want tp provide in the grid, the URL/function to call
           can be attached based on a event (rowselect etc)
    hed -editable sinlge line text
    hcoro -select drop down
    hchstatus -checkbox
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>	

<%--This file has our custom sorting and other utility methos for the grid.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
	<fmt:message key="label.catalogaddrequest"/>
</title>

</head>


<body bgcolor="#ffffff" onLoad="editOnLoad('${param.uAction}');closeAllchildren();" onunload="closeAllchildren();closeThisWindow();" onresize="resizeWindowSizes();">

<script language="JavaScript" type="text/javascript">
<!--

<c:set var="setUpdateExpirationPermission" value='N'/>
var setUpdateExpirationPermission = false;
<c:if test="${hasHmrbTab == 'Y'}">
	<tcmis:facilityPermission indicator="true" userGroupId="EditUseCodeExpiration" companyId="${personnelBean.companyId}" facilityId="${param.facilityId}">
		setUpdateExpirationPermission = true;
        <c:set var="setUpdateExpirationPermission" value='Y'/>
    </tcmis:facilityPermission>
</c:if>

with ( milonic=new menuname("hmrbRightClickMenu") ) {
 top="offset=2";
 style=submenuStyle;
 itemheight=17;
 aI( "text=<b>Item Id: $$ ;url=javascript:doNothing();" );
}

with ( milonic=new menuname("msdsRightClickMenu") ) {
 top="offset=2";
 style=submenuStyle;
 itemheight=17;
 aI( "text=<b>Item Id: $$ ;url=javascript:doNothing();" );
}

drawMenus();

var altApproversList = new Array (
	<c:forEach var="chemicalApproverBean" items="${catAddHeaderViewBean.approverColl}" varStatus="status">
		<c:choose>
       <c:when test="${status.index == 0}">
         "<c:out value="${status.current.personnelId}"/>"
       </c:when>
       <c:otherwise>
         ,"<c:out value="${status.current.personnelId}"/>"
       </c:otherwise>
     </c:choose>
	</c:forEach>
);

var altApproverRolesList = new Array (
	<c:forEach var="chemicalApproverBean" items="${catAddHeaderViewBean.approverColl}" varStatus="status">
		<c:choose>
       <c:when test="${status.index == 0}">
         '<tcmis:jsReplace value="${status.current.approvalRole}"/>'
       </c:when>
       <c:otherwise>
         ,'<tcmis:jsReplace value="${status.current.approvalRole}"/>'
       </c:otherwise>
     </c:choose>
	</c:forEach>
);

var altApproverRolesResubmitRequest = new Array (
	<c:forEach var="chemicalApproverBean" items="${catAddHeaderViewBean.approverColl}" varStatus="status">
		<c:choose>
       <c:when test="${status.index == 0}">
         '<tcmis:jsReplace value="${status.current.resubmitRequest}"/>'
       </c:when>
       <c:otherwise>
         ,'<tcmis:jsReplace value="${status.current.resubmitRequest}"/>'
       </c:otherwise>
     </c:choose>
	</c:forEach>
);

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
errors:"<fmt:message key="label.errors"/>",
genericError:"<fmt:message key="generic.error"/>",
submit:"<fmt:message key="button.submit"/>",
save:"<fmt:message key="button.save"/>",
deleteRequest:"<fmt:message key="label.delete"/>",
approve:"<fmt:message key="label.approve"/>",
reject:"<fmt:message key="label.reject"/>",
approvaldetail:"<fmt:message key="label.approvaldetail"/>",
approvalRulesResult:"<fmt:message key="label.approvalrulesresult"/>",    
pleasewait:"<fmt:message key="label.pleasewait"/>",
editCopy:"<fmt:message key="label.editcopy"/>",
deleteLine:"<fmt:message key="label.deleteline"/>",
view:"<fmt:message key="label.view"/>",
addApprovalCode:"<fmt:message key="label.addapprovalcode"/>",
lmMsds:"<fmt:message key="label.lmmsds"/>",
materialDescription:"<fmt:message key="label.materialdescription"/>",
manufacturer:"<fmt:message key="label.manufacturer"/>",
mfgTradeName:"<fmt:message key="label.manufacturertradename"/>",
size:"<fmt:message key="label.size"/>",
unit:"<fmt:message key="label.unit"/>",
requestMissingApprovalCode:"<fmt:message key="label.requestmissingapprovalcode"/>",
editMaterialInfo:"<fmt:message key="label.editmaterialinfo"/>",
rejectLine:"<fmt:message key="label.rejectline"/>",
uploadMsds:"<fmt:message key="label.uploadmsds"/>",
viewComponentMsds:"<fmt:message key="label.viewcomponentmsds"/>",
addMsds:"<fmt:message key="label.addmsds"/>",
uom:"<fmt:message key="label.uom"/>",
poundsPerUnit:"<fmt:message key="label.poundsperunit"/>",
requestMissingMsds:"<fmt:message key="label.requestmissingmsds"/>",
fileAttached:"<fmt:message key="label.viewattachedfile"/>",
newMsds:"<fmt:message key="label.newmsds"/>",
newMaterial:"<fmt:message key="label.newmaterial"/>",
addMaterial:"<fmt:message key="label.addmaterial"/>",
uploadMsdsImage:"<fmt:message key="label.uploadmsdsimage"/>",
viewKitMsds:"<fmt:message key="label.viewkitmsds"/>",
setUpdateExpiration:"<fmt:message key="label.setupdateexpiration"/>",
editAndResubmit:"<fmt:message key="label.editandresubmit"/>",
cataddreq:"<fmt:message key="label.cataddreq"/>",
resubmitWarning:"<fmt:message key="label.resubmitwarning"/>",
results:"<fmt:message key="label.results"/>",
allTests:"<fmt:message key="label.alltests"/>",
approvalRules:"<fmt:message key="label.approvalrules"/>",
customerMsdsNumber:"<fmt:message key="label.msds"/>",    
customerMixtureNumber:"<fmt:message key="label.kitmsds"/>",
percentVolWeight:"<fmt:message key="error.percentvolweight"/>",
percentVolWeightUnitCount:"<fmt:message key="error.percentvolweightunitcount"/>",
kitname:"<fmt:message key="report.label.kitDescription"/>",
cancel:"<fmt:message key="label.cancel"/>",
returnSelectedData:"<fmt:message key="label.returnselecteddata"/>",
comments:"<fmt:message key="label.comments"/>",
norowselected:"<fmt:message key="label.norowselected"/>",    
itemInteger:"<fmt:message key="error.item.integer"/>",
uploadKitMsdsImage:"<fmt:message key="label.uploadkitmsdsimage"/>",
viewKitSummary:"<fmt:message key="label.viewkitsummary"/>",
uploadSdsImageRequiredData:"<fmt:message key="label.uploadsdsimagerequireddata"/>",
customerMfgId:"<fmt:message key="label.customermfgid"/>",
invalidReplaceMsds:"<fmt:message key="error.invalidreplacemsds"/>"};

var errorMessage = messagesData.genericError;
// -->
 </script>


<tcmis:form action="/catalogaddmsdsrequest.do" onsubmit="" target="_self">

<div class="interface" id="mainPage" style="">

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
    errorMessage = '${tcmISError}';
   </c:otherwise>
</c:choose>
//-->
</script>
<!-- Error Messages Ends -->

<!-- start of contentArea -->
<div class="contentArea">

<!-- Search Option Begins -->
<%--Change the width to what you want your page to span.--%>
<div id="searchMaskTable"  style="width:1010px;height:393px;">
	<table class="tableSearch" border="1">
		<tr>
		 <td class="optionTitleBoldLeft" colspan="2">
			 <span id="requestActionSpan" style="display: none">
			 </span>
		 </td>
		</tr>
		<tr>
            <td>
            <table class="tableSearchHeader" border="0">
                <tr>
                    <td class="optionTitleBoldRight" nowrap>
                        <fmt:message key="label.requestor"/>:
                    </td>
                    <td class="optionTitleLeft" title="${catAddHeaderViewBean.requestorPhone} - ${catAddHeaderViewBean.requestorEmail}" nowrap>
                        ${catAddHeaderViewBean.requestorName}
                    </td>
                    <td class="optionTitleBoldRight" nowrap>
                        <fmt:message key="label.request"/>:
                    </td>
                    <td class="optionTitleLeft" nowrap>
                        ${catAddHeaderViewBean.requestId} (${catAddHeaderViewBean.startingViewDesc})
                    </td>
                    <td class="optionTitleBoldRight" nowrap>
                        <fmt:message key="label.submitdate"/>:
                    </td>
                    <td class="optionTitleLeft" nowrap>
                        <fmt:formatDate var="fmtSubmitDate" value="${catAddHeaderViewBean.submitDate}" pattern="${dateFormatPattern}"/>
                        ${fmtSubmitDate}
                    </td>
                </tr>
                <c:if test="${not empty catAddHeaderViewBean.originalRequestId}">
                    <tr>
                        <td class="optionTitleBoldRight">
                            <fmt:message key="label.resubmitrequestor"/>:
                        </td>
                        <td class="optionTitleLeft" title="${catAddHeaderViewBean.resubmitRequestorPhone} - ${catAddHeaderViewBean.resubmitRequestorEmail}">
                            ${catAddHeaderViewBean.resubmitRequestorName}
                        </td>
                        <td class="optionTitleBoldRight">
                            <fmt:message key="label.originalrequest"/>:
                        </td>
                        <td class="optionTitleLeft">
                            ${catAddHeaderViewBean.originalRequestId} (${catAddHeaderViewBean.numberOfResubmittal} <fmt:message key="label.resubmission"/>)
                        </td>
                        <%--
                        <td class="optionTitleBoldRight">
                            <fmt:message key="label.resubmitdate"/>:
                        </td>
                        <td class="optionTitleLeft">
                            <fmt:formatDate var="fmtResubmitRequestedDate" value="${catAddHeaderViewBean.resubmitRequestedDate}" pattern="${dateFormatPattern}"/>
                            ${fmtResubmitRequestedDate}
                        </td>
                        --%>
                    </tr>
                </c:if>
                <tr>
                    <td class="optionTitleBoldRight" nowrap>
                        <fmt:message key="label.catalog"/>:
                    </td>
                    <td class="optionTitleLeft" nowrap>
                        ${catAddHeaderViewBean.catalogDesc}
                    </td>
                    <td class="optionTitleBoldRight" nowrap>
                        <fmt:message key="label.facility"/>:
                    </td>
                    <td class="optionTitleLeft"  nowrap>
                        ${catAddHeaderViewBean.facilityName}
                    </td>
                    <td class="optionTitleBoldRight">
                        <fmt:message key="label.requeststatus"/>:
                    </td>
                    <td class="optionTitleLeft">
                        ${catAddHeaderViewBean.requestStatusDesc}
                    </td>
                </tr>
                <tr>
                    <td class="optionTitleBoldRight" colspan="2">
                        <fmt:message key="label.additionalcomments"/>:
                    </td>
                    <td class="optionTitleBoldLeft" colspan="4">
                        <textarea cols="100" rows="3" class="inputBox" name="messageToApprovers" id="messageToApprovers">${catAddHeaderViewBean.messageToApprovers}</textarea>
                    </td>
                </tr>

                <tr id="kitMsdsUomSpan" style="display: none">
                    <td class="optionTitleBoldRight"  nowrap>
                        <fmt:message key="label.uom"/>*:
                    </td>
                    <td class="optionTitleLeft"  nowrap>
                        <input type="text" class="inputBox" name="unitOfMeasure" id="unitOfMeasure" value="${catAddHeaderViewBean.unitOfMeasure}" size="5" maxLength="20"/>
                    </td>
                    <td class="optionTitleBoldRight"  nowrap>
                        <fmt:message key="label.poundsperunit"/>*:
                    </td>
                    <td class="optionTitleLeft"  nowrap>
                        <input type="text" class="inputBox" name="poundsPerUnit" id="poundsPerUnit" value="${catAddHeaderViewBean.poundsPerUnit}" size="5" />
                    </td>
                </tr>

             </table>
             </td>
        </tr>
    </table>
<div>
<%-- Right Section 1 --%>
<!-- Initialize Menus -->

<!-- CSS Tabs -->
<div id="newChemTabs">
 <ul id="mainTabList">
 </ul>
</div>
<!-- CSS Tabs End -->

<script language="JavaScript" type="text/javascript">
<!--
var hasHmrb = false;
function startOnload() {
	<c:set var="dataCount" value='${0}'/>
	<c:set var="selectedTabId" value='${0}'/>

    openTab('<c:out value="${dataCount}"/>','','<fmt:message key="label.material"/>','','');
	<c:set var="dataCount" value='${dataCount+1}'/>
    if ($v("hasHmrbTab") == 'Y') {
        openTab('<c:out value="${dataCount}"/>','','<fmt:message key="label.hmrb"/>','','');
         <c:if test="${hasHmrbTab == 'Y'}">
            <c:set var="dataCount" value='${dataCount+1}'/>
        </c:if>
        hasHmrb = true;
	}
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
    		<div id="tabsdiv">
				<c:set var="partCount" value='0'/>

                <%-- data for MSDS tab here --%>
                <div id="newItem${partCount}">
					<span id="msdsActionSpan" style="display: none">
					</span>

                    <div id="msdsDataDiv" style="width:986px;height:370px;" style="display: none;"></div>
				    <c:if test="${catAddHeaderViewBean.msdsDataColl != null}" >
                        <script type="text/javascript">
                        <!--
                            <%-- the reason that I have the include here is because the data is set here as well as reloadmsdsdata.jsp --%>
                            <%@ include file="/client/catalog/catalogaddrequestmsdsdata.jsp" %>
                        // -->
                        </script>
					</c:if>
				</div>

                 <%-- data for HMRB tab here --%>
                <c:if test="${hasHmrbTab == 'Y'}">
                    <c:set var="partCount" value='${partCount+1}'/>
                    <div id="newItem${partCount}">
                        <span id="hmrbActionSpan" style="display: none">
                        </span>

                      <div id="hmrbDataDiv" style="width:986px;height:400px;" style="display: none;"></div>
                      <c:if test="${catAddHeaderViewBean.hmrbDataColl != null}" >
                        <script type="text/javascript">
                        <!--
                            <%-- the reason that I have the include here is because the data is set here as well as reloadqpldata.jsp --%>
                            <%@ include file="/client/catalog/catalogaddrequesthmrbdata.jsp" %>
                        // -->
                        </script>
                        </c:if>
                    </div>
                </c:if>
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

<%-- the reason that I have the include here is because the data is set here as well as engeval.jsp --%>
<%@ include file="/client/catalog/chemicalapprovaldata.jsp" %>
<%@ include file="/client/catalog/approvalcomments.jsp" %>

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input type="hidden" name="uAction" id="uAction" value=""/>
<input type="hidden" name="totalLines" id="totalLines" value="${dataCount}" >
<input type="hidden" name="partCount" id="partCount" value='${partCount}'/>
<input type="hidden" name="personnelId" id="personnelId" value='${personnelBean.personnelId}'/>
<input type="hidden" name="requestId" id="requestId" value='${catAddHeaderViewBean.requestId}'/>
<input type="hidden" name="engEvalFacilityId" id="engEvalFacilityId" value='${catAddHeaderViewBean.engEvalFacilityId}'/>
<input type="hidden" name="facilityName" id="facilityName" value='${catAddHeaderViewBean.facilityName}'/>	
<input type="hidden" name="catalogCompanyId" id="catalogCompanyId" value='${catAddHeaderViewBean.catalogCompanyId}'/>
<input type="hidden" name="catalogId" id="catalogId" value='${catAddHeaderViewBean.catalogId}'/>
<input type="hidden" name="companyId" id="companyId" value='${catAddHeaderViewBean.companyId}'/>
<input type="hidden" name="requestor" id="requestor" value='${catAddHeaderViewBean.requestor}'/>
<input type="hidden" name="requestStatus" id="requestStatus" value='${catAddHeaderViewBean.requestStatus}'/>
<input type="hidden" name="requestStartingView" id="requestStartingView" value='${catAddHeaderViewBean.startingView}'/>	
<input type="hidden" name="catAddApprovalDetailNeeded" id="catAddApprovalDetailNeeded" value='${catAddHeaderViewBean.catAddApprovalDetailNeeded}'/>
<input type="hidden" name="closeTransitWinflag" id="closeTransitWinflag" value='N'/>
<input type="hidden" name="hasKeywordListApproval" id="hasKeywordListApproval" value='${catAddHeaderViewBean.hasKeywordListApproval}'/>
<input type="hidden" name="hasHmrbTab" id="hasHmrbTab" value='${hasHmrbTab}'/>
<input type="hidden" name="approvedWithRestriction" id="approvedWithRestriction" value='N'/>
<!-- Popup Calendar input options for use code approval expiration -->
<input type="hidden" name="blockBefore_beginDateJsp" id="blockBefore_beginDateJsp" value=""/>
<input type="hidden" name="blockAfter_beginDateJsp" id="blockAfter_beginDateJsp" value=""/>
<input type="hidden" name="blockBeforeExclude_beginDateJsp" id="blockBeforeExclude_beginDateJsp" value=""/>
<input type="hidden" name="blockAfterExclude_beginDateJsp" id="blockAfterExclude_beginDateJsp" value=""/>
<input type="hidden" name="inDefinite_beginDateJsp" id="inDefinite_beginDateJsp" value=""/>
<input type="hidden" name="blockBefore_endDateJsp" id="blockBefore_endDateJsp" value=""/>
<input type="hidden" name="blockAfter_endDateJsp" id="blockAfter_endDateJsp" value=""/>
<input type="hidden" name="blockBeforeExclude_endDateJsp" id="blockBeforeExclude_endDateJsp" value=""/>
<input type="hidden" name="blockAfterExclude_endDateJsp" id="blockAfterExclude_endDateJsp" value=""/>
<input type="hidden" name="inDefinite_endDateJsp" id="inDefinite_endDateJsp" value=""/>
<input type="hidden" name="setUpdateExpirationPermission" id="setUpdateExpirationPermission" value="${setUpdateExpirationPermission}"/>
<input type="hidden" name="originalRequestId" id="originalRequestId" value='${catAddHeaderViewBean.originalRequestId}'/>
<input type="hidden" name="resubmitRequestor" id="resubmitRequestor" value='${catAddHeaderViewBean.resubmitRequestor}'/>
<input type="hidden" name="numberOfResubmittal" id="numberOfResubmittal" value='${catAddHeaderViewBean.numberOfResubmittal}'/>
<input type="hidden" name="facilityMaxResubmittal" id="facilityMaxResubmittal" value='${catAddHeaderViewBean.facilityMaxResubmittal}'/>
<input type="hidden" name="requireCustomerMsds" id="requireCustomerMsds" value='${catAddHeaderViewBean.requireCustomerMsds}'/>
<input type="hidden" name="showReplacesMsds" id="showReplacesMsds" value='${showReplacesMsds}'/>
<input type="hidden" name="catAddRequestorEditMsdsId" id="catAddRequestorEditMsdsId" value='${catAddHeaderViewBean.catAddRequestorEditMsdsId}'/>
<input type="hidden" name="customerMsdsDb" id="customerMsdsDb" value='${catAddHeaderViewBean.customerMsdsDb}'/>
<input type="hidden" name="allowEditMixtureData" id="allowEditMixtureData" value='${catAddHeaderViewBean.allowEditMixtureData}'/>
<input type="hidden" name="mixRatioRequired" id="mixRatioRequired" value='${catAddHeaderViewBean.mixRatioRequired}'/>
<input type="hidden" name="allowMultipleHmrb" id="allowMultipleHmrb" value='${catAddHeaderViewBean.catAddAllowMultipleHmrb}'/>
<input type="hidden" name="newMaterial" id="newMaterial" value='${catAddHeaderViewBean.newMaterial}'/>
<input type="hidden" name="newPart" id="newPart" value='${catAddHeaderViewBean.newPart}'/>
</div>
<!-- Hidden elements end -->
</tcmis:form>
</div>
<!-- close of contentArea -->

</div> <!-- close of interface -->

<div id="hiddenFrame" style="display: none;">
	<iframe scrolling="no" id="catalogAddMsdsRequestFrame" name="catalogAddMsdsRequestFrame" width="100%" height="300" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
</div>

</body>
</html:html>