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

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/client/catalog/catalogaddrequest.js"></script>
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

<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script> 
<script type="text/javascript" src="/js/jquery/autocomplete.js"></script> 
<link rel="stylesheet" type="text/css" href="/css/autocomplete.css" /> 

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


<body bgcolor="#ffffff" onLoad="editOnLoad('<tcmis:jsReplace value="${param.uAction}"/>');" onunload="closeAllchildren();closeThisWindow();" onresize="resizeWindowSizes();">

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

with ( milonic=new menuname("qplRightClickMenu") ) {
 top="offset=2";
 style=submenuStyle;
 itemheight=17;
 aI( "text=<b>Item Id: $$ ;url=javascript:doNothing();" );
}

with ( milonic=new menuname("qplMsdsRightClickMenu") ) {
 top="offset=2";
 style=submenuStyle;
 itemheight=17;
 aI( "text=<b>Item Id: $$ ;url=javascript:doNothing();" );
}

with ( milonic=new menuname("fileAttachedMenu") ) {
 top="offset=2";
 style=submenuStyle;
 itemheight=17;
 aI( "text=<b>Item Id: $$ ;url=javascript:doNothing();" );
}

with ( milonic=new menuname("useApprovalRightClickMenu") ) {
 top="offset=2";
 style=submenuStyle;
 itemheight=17;
 aI( "text=<b>Item Id: $$ ;url=javascript:doNothing();" );
}

with ( milonic=new menuname("specRightClickMenu") ) {
 top="offset=2";
 style=submenuStyle;
 itemheight=17;
 aI( "text=<b>Item Id: $$ ;url=javascript:doNothing();" );
}

with ( milonic=new menuname("flowdownRightClickMenu") ) {
 top="offset=2";
 style=submenuStyle;
 itemheight=17;
 aI( "text=<b>Item Id: $$ ;url=javascript:doNothing();" );
}

with ( milonic=new menuname("hmrbRightClickMenu") ) {
 top="offset=2";
 style=submenuStyle;
 itemheight=17;
 aI( "text=<b>Item Id: $$ ;url=javascript:doNothing();" );
}

with ( milonic=new menuname("storageRightClickMenu") ) {
	 top="offset=2";
	 style=submenuStyle;
	 itemheight=17;
	 aI( "text=<fmt:message key="label.delete"/>;url=javascript:deleteStorage(null);" );
	}

drawMenus();

var altCatalogFacility = new Array(
<c:forEach var="catalogFacilityBean" items="${catalogFacilityCollection}" varStatus="status2">
   <c:if test="${status2.index > 0}">,</c:if>
   {
		catalogCompanyId:'<c:out value="${status2.current.catalogCompanyId}"/>',
		catalogId:'<c:out value="${status2.current.catalogId}"/>',
		catalogDesc:'<c:out value="${status2.current.catalogDesc}"/>',
		companyId:'<c:out value="${status2.current.companyId}"/>',
		displayFlowDown:'<c:out value="${status2.current.displayFlowDown}"/>',
		directedChargeByPart:'<c:out value="${status2.current.directedChargeByPart}"/>',
		consumableOption:'<c:out value="${status2.current.consumableOption}"/>',
		labelSpecRequired:'<c:out value="${status2.current.labelSpecRequired}"/>',
		facilityId:'<c:out value="${status2.current.facilityId}"/>',
		display:'<c:out value="${status2.current.display}"/>',
		mrCreateFromCatalog:'<c:out value="${status2.current.mrCreateFromCatalog}"/>',
		catalogAddAllowed:'<c:out value="${status2.current.catalogAddAllowed}"/>',
		catAddAllowAllForApps:'<c:out value="${status2.current.catAddAllowAllForApps}"/>',
		catPartAddAutoGenNullPn:'<c:out value="${status2.current.catPartAddAutoGenNullPn}"/>',
		customerCatAddProcess:'<c:out value="${status2.current.customerCatAddProcess}"/>',
		qualityIdLabel:'<c:out value="${status2.current.qualityIdLabel}"/>',
		catPartAttributeHeader:'<c:out value="${status2.current.catPartAttributeHeader}"/>',
		catPartAttributeLabel1:'<c:out value="${status2.current.catPartAttributeLabel1}"/>',
		catPartAttributeLabel2:'<c:out value="${status2.current.catPartAttributeLabel2}"/>',
		label1SetFiChargeTypeVal:'<c:out value="${status2.current.label1SetFiChargeTypeVal}"/>',
		label2SetFiChargeTypeVal:'<c:out value="${status2.current.label2SetFiChargeTypeVal}"/>',
		label1SetCarnProdMatlVal:'<c:out value="${status2.current.label1SetCarnProdMatlVal}"/>',
		label2SetCarnProdMatlVal:'<c:out value="${status2.current.label2SetCarnProdMatlVal}"/>'
    }
</c:forEach>
);

<c:set var="qualityIdLabel" value=''/>
<c:set var="catPartAttributeHeader" value=''/>
<c:set var="catPartAttributeLabel1" value=''/>
<c:set var="catPartAttributeLabel2" value=''/>
<c:set var="label1SetCarnProdMatlVal" value=''/>
<c:set var="label2SetCarnProdMatlVal" value=''/>
<c:forEach var="catalogFacilityBean" items="${catalogFacilityCollection}" varStatus="status2">
    <c:set var="qualityIdLabel" value="${status2.current.qualityIdLabel}"/>
    <c:set var="catPartAttributeHeader" value="${status2.current.catPartAttributeHeader}"/>
    <c:set var="catPartAttributeLabel1" value="${status2.current.catPartAttributeLabel1}"/>
    <c:set var="catPartAttributeLabel2" value="${status2.current.catPartAttributeLabel2}"/>
    <c:set var="label1SetCarnProdMatlVal" value="${status2.current.label1SetCarnProdMatlVal}"/>
    <c:set var="label2SetCarnProdMatlVal" value="${status2.current.label2SetCarnProdMatlVal}"/>
</c:forEach>


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
         '<c:out value="${status.current.approvalRole}"/>'
       </c:when>
       <c:otherwise>
         ,'<c:out value="${status.current.approvalRole}"/>'
       </c:otherwise>
     </c:choose>
	</c:forEach>
);

var altApproverRolesResubmitRequest = new Array (
	<c:forEach var="chemicalApproverBean" items="${catAddHeaderViewBean.approverColl}" varStatus="status">
		<c:choose>
       <c:when test="${status.index == 0}">
         '<c:out value="${status.current.resubmitRequest}"/>'
       </c:when>
       <c:otherwise>
         ,'<c:out value="${status.current.resubmitRequest}"/>'
       </c:otherwise>
     </c:choose>
	</c:forEach>
);

var altApproverRoleBeforeTcmQc = new Array (
	<c:forEach var="chemicalApproverBean" items="${catAddHeaderViewBean.approverColl}" varStatus="status">
		<c:choose>
       <c:when test="${status.index == 0}">
         '<c:out value="${status.current.beforeTcmQc}"/>'
       </c:when>
       <c:otherwise>
         ,'<c:out value="${status.current.beforeTcmQc}"/>'
       </c:otherwise>
     </c:choose>
	</c:forEach>
);

<c:set var="showItarControl" value="${tcmis:isFeatureReleased(personnelBean,'ItarControl',catAddHeaderViewBean.engEvalFacilityId)}"/>
var specConfig = [
{ columnId:"permission",
  columnName:''
},
{ columnId:"specId",
  columnName:'',
  submit:true
},
{ columnId:"status",
  columnName:'<fmt:message key="label.status"/>',
  width: 12,	
  submit:true
},		 
{ columnId:"specName",
  columnName:'<fmt:message key="label.name"/>',
  width: 15,	
  submit:true
},
{ columnId:"specTitle",
  columnName:'<fmt:message key="label.title"/>',
  width: 20,
  submit:true
},
{ columnId:"specVersion",
  columnName:'<fmt:message key="label.revision"/>',
  submit:true
},
{ columnId:"specAmendment",
  columnName:'<fmt:message key="label.amendment"/>',
  submit:true
},
{ columnId:"specDate",
  columnName:'<fmt:message key="label.date"/>',
  submit:true
},
{ columnId:"coc",
  columnName:'<fmt:message key="label.coc"/>',
  submit:true
},
{ columnId:"coa",
  columnName:'<fmt:message key="label.coa"/>',
  submit:true
},
{ columnId:"itarPermission"
},
{ columnId:"itar",
  <c:if test="${showItarControl == 'true'}">
    columnName:'<fmt:message key="label.itar"/>',
    type:'hcoro',
  </c:if>
  permission:true,  
  submit:true
},
{ columnId:"content",
  columnName:'',
  submit:true
},
{ columnId:"onLine",
  columnName:'',
  submit:true
},
{ columnId:"dataSource",
  columnName:'',
  submit:true
}
];

var itar = new Array(
	{text:'N',value:'N'},
	{text:'Y',value:'Y'}
);

var flowdownConfig = [
{ columnId:"permission",
  columnName:''
},
{ columnId:"flowDown",
  columnName:'',
  submit:true
},
{ columnId:"status",
  columnName:'<fmt:message key="label.status"/>',
  width: 12,
  submit:true
},
{ columnId:"flowDownDesc",
  columnName:'<fmt:message key="label.flowdown"/>',
  width: 25,	
  submit:true
},
{ columnId:"flowDownType",
  columnName:'<fmt:message key="label.type"/>',
  submit:true
},	 
{ columnId:"revisionDate",
  columnName:'<fmt:message key="label.date"/>',
  submit:true
},
{ columnId:"currentVersion",
  columnName:'<fmt:message key="label.version"/>',
  submit:true
},
{ columnId:"content",
  columnName:'',
  submit:true
},
{ columnId:"dataSource",
  columnName:'',
  submit:true
}
];

var altMaterialCategory = new Array(
<c:forEach var="materialCategorySubcatViewBean" items="${catAddHeaderViewBean.materialCategoryColl}" varStatus="status1">
   <c:if test="${status1.index > 0}">,</c:if>
   {
		materialCategoryId:'<c:out value="${materialCategorySubcatViewBean.materialCategoryId}"/>',
		materialCategoryName:'<c:out value="${materialCategorySubcatViewBean.materialCategoryName}"/>'
    }
</c:forEach>
);

var altMaterialSubCategory = new Array();
<c:forEach var="materialCategorySubcatViewBean" items="${catAddHeaderViewBean.materialCategoryColl}" varStatus="status">
    altMaterialSubCategory['${materialCategorySubcatViewBean.materialCategoryId}'] = new Array(
        <c:forEach var="materialSubcatViewBean" items="${materialCategorySubcatViewBean.materialSubCategoryColl}" varStatus="status1">
           <c:if test="${status1.index > 0}">,</c:if>
           {
                materialSubcategoryId:'${materialSubcatViewBean.materialSubcategoryId}',
                materialSubcategoryName:'<c:out value="${materialSubcatViewBean.materialSubcategoryName}"/>'
           }
        </c:forEach>
    );
</c:forEach>

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
genericError:"<fmt:message key="generic.error"/>",   
information:"<fmt:message key="label.information"/>",
pleasewait:"<fmt:message key="label.pleasewait"/>",
pleasewaitmenu:"<fmt:message key="label.pleasewaitmenu"/>",
addSuggestedSupplier:"<fmt:message key="label.addsuggestedsupplier"/>",
editItemInfo:"<fmt:message key="label.edititeminfo"/>",
uploadMsds:"<fmt:message key="label.uploadmsds"/>",
viewMsds:"<fmt:message key="label.viewmsds"/>",
deleteLine:"<fmt:message key="label.deleteline"/>",
edit:"<fmt:message key="label.edit"/>",
removeSelectedLine:"<fmt:message key="label.removeselectedline"/>",
submit:"<fmt:message key="button.submit"/>",
save:"<fmt:message key="button.save"/>",
deleteRequest:"<fmt:message key="label.delete"/>",
approve:"<fmt:message key="label.approve"/>",
reject:"<fmt:message key="label.reject"/>",
approvaldetail:"<fmt:message key="label.approvaldetail"/>",
newItem:"<fmt:message key="label.newitem"/>",
addItem:"<fmt:message key="label.additem"/>",    
showObsoleteItems:"<fmt:message key="label.showobsoleteitems"/>",
addWorkArea:"<fmt:message key="label.addworkarea"/>",
addSpec:"<fmt:message key="label.addspec"/>",
addNoSpec:"<fmt:message key="label.addnospec"/>",
addStandardMfgCert:"<fmt:message key="label.addstandardmfgcert"/>",	
addFlowdown:"<fmt:message key="label.addflowdown"/>",	
norowselected:"<fmt:message key="label.norowselected"/>",
fadeOutFromQpl:"<fmt:message key="label.fadeoutfromqpl"/>",
viewSpec:"<fmt:message key="label.viewspecification"/>",
uploadSpec:"<fmt:message key="label.uploadspecification"/>",
viewFlowdown:"<fmt:message key="label.viewflowdown"/>",
uploadFlowdown:"<fmt:message key="label.uploadflowdown"/>",
itemAlreadyInQpl:"<fmt:message key="label.itemalreadyinqpl"/>",
rejectLine:"<fmt:message key="label.rejectline"/>",
comments:"<fmt:message key="label.comments"/>",
requestMissingItemInQpl:"<fmt:message key="label.requestmissingiteminqpl"/>",
requestMissingWorkArea:"<fmt:message key="label.requestmissingworkarea"/>",
shelfLife:"<fmt:message key="label.shelflife"/>",
storageTemp:"<fmt:message key="label.storagetemp"/>", 	 
roomTempOutTime:"<fmt:message key="label.roomtempouttime"/>",
numberOfRecertAllowed:"<fmt:message key="label.numberofrecertallowed"/>",
productionMaterial:"<fmt:message key="label.productionmaterial"/>",
viewComponentMsds:"<fmt:message key="label.viewcomponentmsds"/>",
uploadAllMsdsForItem:"<fmt:message key="label.uploadallmsdsforitem"/>",
fileAttached:"<fmt:message key="label.viewattachedfile"/>",
customerMsdsNumber:"<fmt:message key="label.msds"/>",
approvalRulesResult:"<fmt:message key="label.approvalrulesresult"/>",
addApprovalCode:"<fmt:message key="label.addapprovalcode"/>",
reApprove:"<fmt:message key="label.reapprove"/>",
uploadApprovedLetter:"<fmt:message key="label.uploadletter"/>",
viewApprovedLetter:"<fmt:message key="label.viewletter"/>",
editCopy:"<fmt:message key="label.editcopy"/>",
view:"<fmt:message key="label.view"/>",
requestMissingApprovalCode:"<fmt:message key="label.requestmissingapprovalcode"/>",
cancel:"<fmt:message key="label.cancel"/>",
returnSelectedData:"<fmt:message key="label.returnselecteddata"/>",
removeFadeoutRequest:"<fmt:message key="label.removefadeoutrequest"/>",
labelColor:"<fmt:message key="label.labelcolor"/>",
hmrbUseApprovalApprovalCodeMismatch:"<fmt:message key="label.hmrbuseapprovalapprovalcodemismatch"/>",        
packaging:"<fmt:message key="label.packaging"/>",
setUpdateExpiration:"<fmt:message key="label.setupdateexpiration"/>",
editAndResubmit:"<fmt:message key="label.editandresubmit"/>",
cataddreq:"<fmt:message key="label.cataddreq"/>",
resubmitWarning:"<fmt:message key="label.resubmitwarning"/>",        
itemInteger:"<fmt:message key="error.item.integer"/>",
add:"<fmt:message key="label.add"/>",
averageQuantityStored:"<fmt:message key="label.averagequantitystored"/>",
maximumQuantityStored:"<fmt:message key="label.maximumquantitystored"/>",
results:"<fmt:message key="label.results"/>",
allTests:"<fmt:message key="label.alltests"/>",
approvalRules:"<fmt:message key="label.approvalrules"/>",
customerMixtureNumber:"<fmt:message key="label.kitmsds"/>",
storageWorkArea:"<fmt:message key="label.workarea"/>",
selectOne:"<fmt:message key="label.selectOne"/>",
materialCategory:"<fmt:message key="label.materialcategory"/>",
materialSubcategory:"<fmt:message key="label.materialsubcategory"/>",
hmrbEmapRequired:"<fmt:message key="error.hmrbemaprequired"/>",     
partRevision:"<fmt:message key="label.partrevision"/>",
percentVolWeight:"<fmt:message key="error.percentvolweight"/>",
kitDescription:"<fmt:message key="report.label.kitDescription"/>",
mixRatioAmount:"<fmt:message key="label.mixratioamount"/>",
deleteComponent:"<fmt:message key="label.deletecomponent"/>",
percentVolWeightUnitCount:"<fmt:message key="error.percentvolweightunitcount"/>",
uploadKitMsdsForItem:"<fmt:message key="label.uploadkitmsdsforitem"/>",
viewKitSummary:"<fmt:message key="label.viewkitsummary"/>",
missingMixRatioAmount:"<fmt:message key="error.missingmixratioamount"/>",
incomingTestingRequired:"<fmt:message key="error.incomingtestingrequired"/>",
customerMfgId:"<fmt:message key="label.customermfgid"/>",
invalidReplaceMsds:"<fmt:message key="error.invalidreplacemsds"/>"
};
// -->
 </script>


<tcmis:form action="/catalogaddrequest.do" onsubmit="" target="_self">

<div class="interface" id="mainPage" style="">

	 <%-- message --%>
<div id="messageDailogWin" class="errorMessages" style="display: none;overflow: auto;">
	<table width="100%" border="0" cellpadding="2" cellspacing="1">
		<tr>
			<td align="center" width="100%">
				<textarea cols="70" rows="5" class="inputBox" readonly="true" name="messageText" id="messageText"><c:out value="${tcmISError}"/></textarea>
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
<div id="searchMaskTable" border="0" cellpadding="0"	cellspacing="0">
	<table class="tableSearch" border="1" width="100%" >
		<tr>
		 <td class="optionTitleBoldLeft" colspan="2">
			 <span id="requestActionSpan" style="display: none">
			 </span>
		 </td>
		</tr>
		<tr>
			<td width="70%" class="optionTitleBoldRight">
				<table class="tableSearchRight" border="0">
					<tr>
				 		<td class="optionTitleBoldRight" nowrap>
					 		<fmt:message key="label.requestor"/>:
				 		</td>
				 		<td class="optionTitleLeft" title="${catAddHeaderViewBean.requestorPhone} - ${catAddHeaderViewBean.requestorEmail}">
					 		${catAddHeaderViewBean.requestorName}
				 		</td>
						<td class="optionTitleBoldRight">
							<fmt:message key="label.request"/>:
						</td>
						<td class="optionTitleLeft">
							${catAddHeaderViewBean.requestId} (${catAddHeaderViewBean.startingViewDesc})
						</td>
						<td class="optionTitleBoldRight">
							<fmt:message key="label.submitdate"/>:
						</td>
						<td class="optionTitleLeft">
							<fmt:formatDate var="fmtSubmitDate" value="${catAddHeaderViewBean.submitDate}" pattern="${dateFormatPattern}"/>
						 	${fmtSubmitDate}
						</td>
					</tr>
                    <c:if test="${not empty catAddHeaderViewBean.originalRequestId}">
                        <tr>
                            <td class="optionTitleBoldRight" nowrap>
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
				 		<td class="optionTitleLeft">
					 		${catAddHeaderViewBean.catalogDesc}
				 		</td>
				 		<td class="optionTitleBoldRight">
					 		<fmt:message key="label.facility"/>:
				 		</td>
				 		<td class="optionTitleLeft">
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
				 		<td class="optionTitleBoldRight" nowrap>
					 		<fmt:message key="label.partno"/>:
				 		</td>
						<c:choose>
							<c:when test="${catAddHeaderViewBean.startingView == '3'}">
								<td class="optionTitleBoldLeft">
					 				${catAddHeaderViewBean.catPartNo}
				 				</td>
								<input class="inputBox" type="hidden" name="catPartNo" id="catPartNo" value='<c:out value="${catAddHeaderViewBean.catPartNo}"/>'/>
							</c:when>
							<c:otherwise>
								<td class="optionTitleBoldLeft">
									<input class="inputBox" type="text" name="catPartNo" id="catPartNo" value="<c:out value="${catAddHeaderViewBean.catPartNo}"/>" size="15">
								</td>
							</c:otherwise>
						</c:choose>

						<c:choose>
							<c:when test="${catAddHeaderViewBean.startingView == '3'}">
								<td class="optionTitleBoldLeft">
									<fmt:message key="label.stocked"/>:&nbsp;${catAddHeaderViewBean.stocked}
								</td>
								<input class="inputBox" type="hidden" name="stocked" id="stocked" value='${catAddHeaderViewBean.stocked}'/>
							</c:when>
							<c:otherwise>
								<td class="optionTitleBoldLeft">
									<fmt:message key="label.stocked"/>:&nbsp;
									<c:set var="selectedStocked" value='${catAddHeaderViewBean.stocked}'/>
									<c:set var="selectedMM" value='selected'/>
									<c:set var="selectedOOR" value=''/>
									<c:if test="${selectedStocked == 'OOR'}">
										<c:set var="selectedMM" value=''/>
										<c:set var="selectedOOR" value='selected'/>
									</c:if>
									<select name="stocked" id="stocked" class="selectBox">
										<option value="MM" ${selectedMM}><fmt:message key="label.mm"/></option>
										<option value="OOR" ${selectedOOR}><fmt:message key="label.oor"/></option>
									</select>
								</td>
							</c:otherwise>
						</c:choose> 

						 <td class="optionTitleBoldLeft" colspan="3">
							 <fmt:message key="label.replacespartno"/>:&nbsp;
							 <input class="inputBox" type="text" name="replacesPartNo" id="replacesPartNo" value="<c:out value="${catAddHeaderViewBean.replacesPartNo}"/>" readonly="true" size="30"/>
					 		<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="selectedReplacesPartNo" id="selectedReplacesPartNo" value="..." align="right" onClick="lookupReplacementPart();"/>
				 		</td>
			 		</tr>
		    		<tr>
				 		<td class="optionTitleBoldRight" colspan="2">
					 		<fmt:message key="label.description"/>:
				 		</td>
				 		<td class="optionTitleBoldLeft" colspan="4">
					 		<textarea cols="100" rows="2" class="inputBox" name="partDescription" id="partDescription"><c:out value="${catAddHeaderViewBean.partDescription}"/></textarea>
				 		</td>
			 		</tr>
		    		<tr>
				 		<td class="optionTitleBoldRight" colspan="2">
					 		<fmt:message key="label.recertinstructions"/>:
				 		</td>
				 		<td class="optionTitleBoldLeft" colspan="4">
					 		<textarea cols="100" rows="2" class="inputBox" name="recertInstructions" id="recertInstructions"><c:out value="${catAddHeaderViewBean.recertInstructions}"/></textarea>
				 		</td>
			 		</tr>
				   <tr>
				 		<td class="optionTitleBoldRight" colspan="2">
					 		<fmt:message key="label.numberofrecertallowed"/>:
				 		</td>
				 		<td class="optionTitleBoldLeft" colspan="4">
							<input class="inputBox" type="text" name="maxRecertNumber" id="maxRecertNumber" value="${catAddHeaderViewBean.maxRecertNumber}" size="5">

                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<c:set var="selectedIncomingTesting" value=''/>
							<c:if test="${catAddHeaderViewBean.incomingTesting == 'Y'}">
								<c:set var="selectedIncomingTesting" value='checked="checked"'/>
							</c:if>
                            <input type="checkbox" name="incomingTesting" id="incomingTesting" value="Y" class="radio" ${selectedIncomingTesting}><fmt:message key="label.incomingtesting"/>

                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <fmt:message key="label.partrevision"/>:
						    <input class="inputBox" type="text" name="customerPartRevision" id="customerPartRevision" value="${catAddHeaderViewBean.customerPartRevision}" size="20">
						 </td>
                     </tr>
                     <tr>
                        <td class="optionTitleBoldRight" colspan="2">
                            <fmt:message key="label.additionalcomments"/>:
                        </td>
                        <td class="optionTitleBoldLeft" colspan="4">
                            <textarea cols="100" rows="2" class="inputBox" name="messageToApprovers" id="messageToApprovers"><c:out value="${catAddHeaderViewBean.messageToApprovers}"/></textarea>
                        </td>
                    </tr>
                </table>
			</td>
			<td width="30%" class="optionTitleBoldLeft">
				<table class="tableSearchCenter" border="0">
					<tr>
						<c:set var="catPartAttribute1Title" value=''/>
						<c:set var="catPartAttribute2Title" value=''/>
						<c:if test="${catPartAttributeHeader == 'Production Material'}">
							<c:set var="catPartAttribute1Title"><fmt:message key="label.productionmaterialdefinition"/></c:set>
                            <c:set var="catPartAttribute2Title"><fmt:message key="label.nonproductionmaterialdefinition"/></c:set>
						</c:if>

						<c:set var="catPartAttributeChecked" value=''/>
                        <c:set var="catPartAttribute2Checked" value=''/>
                        <c:if test="${catAddHeaderViewBean.catPartAttribute == catPartAttributeLabel1}">
                            <c:set var="catPartAttributeChecked" value='checked="checked"'/>
                        </c:if>
                        <c:if test="${catAddHeaderViewBean.catPartAttribute == catPartAttributeLabel2}">
                            <c:set var="catPartAttribute2Checked" value='checked="checked"'/>
                        </c:if>

						<td nowrap="true">
							<span id="catPartAttributeSpan" style="display: none">
                                ${catPartAttributeHeader}:*<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <input title='${catPartAttribute1Title}' type="radio" name="catPartAttribute1" id="catPartAttribute1" onClick="catPartAttribute1Clicked();" value="${catPartAttributeLabel1}" class="radio" ${catPartAttributeChecked}/>${catPartAttributeLabel1}
                                &nbsp;
                                <input title='${catPartAttribute2Title}' type="radio" name="catPartAttribute2" id="catPartAttribute2" onClick="catPartAttribute2Clicked();" value="${catPartAttributeLabel2}" class="radio" ${catPartAttribute2Checked}/>${catPartAttributeLabel2}
                            </span>

                            <span id="materialCategorySpan" style="display: none">
								<fmt:message key="label.materialcategory"/>:*<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                 <select name="materialCategoryId" id="materialCategoryId" class="selectBox" onchange="materialCategoryChanged()">
				                 </select>
                            </span>
                        </td>
					</tr>
					<tr>
						<td nowrap="true">
							<span id="qualityIdSpan" style="display: none">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${qualityIdLabel}:&nbsp;
								<input class="inputBox" type="text" name="qualityId" id="qualityId" value="${catAddHeaderViewBean.qualityId}" size="15">
							</span>

                            <span id="materialSubCategorySpan" style="display: none">
								<fmt:message key="label.materialsubcategory"/>:*<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                 <select name="materialSubcategoryId" id="materialSubcategoryId" class="selectBox">
				                 </select>
                            </span>
                        </td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<c:set var="selectedYes" value='checked="checked"'/>
						<c:set var="selectedNo" value=''/>
						<c:if test="${catAddHeaderViewBean.timeTempSensitive == 'N'}">
							<c:set var="selectedYes" value=''/>
							<c:set var="selectedNo" value='checked="checked"'/>
						</c:if>
						<td nowrap="true">
							<span id="timeTempSensitiveSpan">
								<fmt:message key="label.timetempsensitive"/>:<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="timeTempSensitiveYes" id="timeTempSensitiveYes" onclick="timeTempClickedYes();" value="Y" class="radio" ${selectedYes}/><fmt:message key="label.yes"/>
								&nbsp;<input type="radio" name="timeTempSensitiveNo" id="timeTempSensitiveNo" onclick="timeTempClickedNo();" value="N" class="radio" ${selectedNo}/><fmt:message key="label.no"/>
							</span>
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<c:set var="selectedYes" value=''/>
						<c:set var="selectedNo" value='checked="checked"'/>
						<c:if test="${catAddHeaderViewBean.roomTempOutTime == 'Y'}">
							<c:set var="selectedYes" value='checked="checked"'/>
							<c:set var="selectedNo" value=''/>
						</c:if>
						<td nowrap="true">
							<span id="roomTempOutTimeSpan" style="display: none">
								<fmt:message key="label.roomtempouttimerequired"/>:<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="roomTempOutTimeYes" id="roomTempOutTimeYes" onClick="outTimeClickedYes();" value="Y" class="radio" ${selectedYes}/><fmt:message key="label.yes"/>
								&nbsp;<input type="radio" name="roomTempOutTimeNo" id="roomTempOutTimeNo" onClick="outTimeClickedNo();" value="N" class="radio" ${selectedNo}/><fmt:message key="label.no"/>
							</span>
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

    openTab('<c:out value="${dataCount}"/>','','<fmt:message key="label.qpl"/>','','');
	<c:set var="dataCount" value='${dataCount+1}'/>
    if ($v("hasHmrbTab") == 'Y') {
        openTab('<c:out value="${dataCount}"/>','','<fmt:message key="label.hmrb"/>','','');
         <c:if test="${hasHmrbTab == 'Y'}">
            <c:set var="dataCount" value='${dataCount+1}'/>
        </c:if>
        hasHmrb = true;
	}
	
    openTab('<c:out value="${dataCount}"/>','','<fmt:message key="label.useapproval"/>','','');
	<c:set var="dataCount" value='${dataCount+1}'/>
	
    if ($v("hasCatalogAddStorageTab") == 'Y') {
    	openTab('<c:out value="${dataCount}"/>','','<fmt:message key="label.storage"/>','','');
         <c:if test="${hasCatalogAddStorageTab == 'Y'}">
     		<c:set var="dataCount" value='${dataCount+1}'/>
        </c:if>
        hasHmrb = true;
	}
	
	openTab('<c:out value="${dataCount}"/>','','<fmt:message key="label.spec"/>','','');
	<c:set var="dataCount" value='${dataCount+1}'/>
	openTab('<c:out value="${dataCount}"/>','','<fmt:message key="label.flowdown"/>','','');
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

                <%-- data for QPL tab here --%>
                <div id="newItem${partCount}">
					<span id="qplActionSpan" style="display: none">
					</span>

				  <div id="qplDataDiv" style="width:986px;height:293px;" style="display: none;"></div>
				  <c:if test="${catAddHeaderViewBean.qplDataColl != null}" >
					<script type="text/javascript">
					<!--
						<%-- the reason that I have the include here is because the data is set here as well as reloadqpldata.jsp --%>
						<%@ include file="/client/catalog/catalogaddrequestqpldata.jsp" %>
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

                      <div id="hmrbDataDiv" style="width:986px;height:293px;" style="display: none;"></div>
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

				<%-- data for Use Approval tab here --%>
				<c:set var="partCount" value='${partCount+1}'/>
				<div id="newItem${partCount}">
					<span id="useApprovalActionSpan"  style="display: none">
					</span>

				  <div id="useApprovalDataDiv" style="width:986px;height:293px;" style="display: none;"></div>
				  <c:if test="${catAddHeaderViewBean.useApprovalDataColl != null}" >
					<script type="text/javascript">
					<!--
						<%-- the reason that I have the include here is because the data is set here as well as reloaduseapprovaldata.jsp --%>
						<%@ include file="/client/catalog/catalogaddrequestuseapprovaldata.jsp" %>
					// -->
					</script>
				  </c:if>
				</div>
				
				 <%-- data for HMRB tab here --%>
                <c:if test="${hasCatalogAddStorageTab == 'Y'}">
					<%-- data for Storage tab here --%>
	                <c:set var="partCount" value='${partCount+1}'/>
	                <div id="newItem${partCount}">
	                    <span id="storageActionSpan" style="display: none">
	                    </span>
	
	                <div id="storageDataDiv" style="width:986px;height:293px;" style="display: none;"></div>
	                  <c:if test="${catAddHeaderViewBean.hmrbDataColl != null}" >
	                    <script type="text/javascript">
	                    <!--
	                        <%-- the reason that I have the include here is because the data is set here as well as reloadqpldata.jsp --%>
	                        <%@ include file="/client/catalog/catalogaddrequeststoragedata.jsp" %>
	                    // -->
	                    </script>
	                    </c:if>
	                </div>
                 </c:if>
                
				<%-- data for SPEC tab here --%>
				<c:set var="partCount" value='${partCount+1}'/>
				<div id="newItem${partCount}">
					<span id="specActionSpan" style="display: none">
					</span>

				  <div id="specDataDiv" style="width:986px;height:293px;" style="display: none;"></div>
				  <c:if test="${catAddHeaderViewBean.specDataColl != null}" >
					<script type="text/javascript">
					<!--
						<%-- the reason that I have the include here is because the data is set here as well as reloadspecdata.jsp --%>
						<%@ include file="/client/catalog/catalogaddrequestspecdata.jsp" %>
					// -->
					</script>
				  </c:if>
				</div>

				<%-- data for SPEC tab here --%>
				<c:set var="partCount" value='${partCount+1}'/>
				<div id="newItem${partCount}">
					<span id="flowdownActionSpan" style="display: none">
					</span>

				  <div id="flowdownDataDiv" style="width:986px;height:293px;" style="display: none;"></div>
				  <c:if test="${catAddHeaderViewBean.flowdownDataColl != null}" >
					<script type="text/javascript">
					<!--
						<%-- the reason that I have the include here is because the data is set here as well as reloadflowdowndata.jsp --%>
						<%@ include file="/client/catalog/catalogaddrequestflowdowndata.jsp" %>
					// -->
					</script>
				  </c:if>
				</div>
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
<input type="hidden" name="facilityId" id="facilityId" value='${catAddHeaderViewBean.engEvalFacilityId}'/>
<input type="hidden" name="facilityName" id="facilityName" value='${catAddHeaderViewBean.facilityName}'/>
<input type="hidden" name="catalogCompanyId" id="catalogCompanyId" value='${catAddHeaderViewBean.catalogCompanyId}'/>
<input type="hidden" name="catalogId" id="catalogId" value='${catAddHeaderViewBean.catalogId}'/>
<input type="hidden" name="partGroupNo" id="partGroupNo" value='${catAddHeaderViewBean.partGroupNo}'/>
<input type="hidden" name="companyId" id="companyId" value='${catAddHeaderViewBean.companyId}'/>
<input type="hidden" name="requestor" id="requestor" value='${catAddHeaderViewBean.requestor}'/>
<input type="hidden" name="requestStatus" id="requestStatus" value='${catAddHeaderViewBean.requestStatus}'/>
<input type="hidden" name="startingView" id="startingView" value='${catAddHeaderViewBean.startingView}'/>	
<input type="hidden" name="catAddApprovalDetailNeeded" id="catAddApprovalDetailNeeded" value='${catAddHeaderViewBean.catAddApprovalDetailNeeded}'/>
<input type="hidden" name="allowMixture" id="allowMixture" value='${catAddHeaderViewBean.allowMixture}'/>
<input type="hidden" name="allowRoomTempOutTime" id="allowRoomTempOutTime" value='${catAddHeaderViewBean.allowRoomTempOutTime}'/>
<input type="hidden" name="roomTempOutTime" id="roomTempOutTime" value='${catAddHeaderViewBean.roomTempOutTime}'/>
<input type="hidden" name="closeTransitWinflag" id="closeTransitWinflag" value='N'/>
<input type="hidden" name="hasKeywordListApproval" id="hasKeywordListApproval" value='${catAddHeaderViewBean.hasKeywordListApproval}'/>
<input type="hidden" name="hasHmrbTab" id="hasHmrbTab" value='${hasHmrbTab}'/>
<input type="hidden" name="approvedWithRestriction" id="approvedWithRestriction" value='N'/>
<input type="hidden" name="allowEditUseApproval" id="allowEditUseApproval" value='${catAddHeaderViewBean.allowEditUseApproval}'/>    
<input type="hidden" name="allowEditHetUsageRecording" id="allowEditHetUsageRecording" value='${catAddHeaderViewBean.allowEditHetUsageRecording}'/>
<input type="hidden" name="viewLevel" id="viewLevel" value='${catAddHeaderViewBean.viewLevel}'/>
<input type="hidden" name="labelColorRequired" id="labelColorRequired" value='${catAddHeaderViewBean.labelColorRequired}'/>
<input type="hidden" name="hasApplicationUseGroup" id="hasApplicationUseGroup" value='${catAddHeaderViewBean.hasApplicationUseGroup}'/>
<input type="hidden" name="newPartFromExistingItemModifyPkg" id="newPartFromExistingItemModifyPkg" value="<tcmis:jsReplace value="${param.newPartFromExistingItemModifyPkg}"/>"/>
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
<input type="hidden" name="engEvalFacilityId" id="engEvalFacilityId" value='${catAddHeaderViewBean.engEvalFacilityId}'/>
<input type="hidden" name="numberOfResubmittal" id="numberOfResubmittal" value='${catAddHeaderViewBean.numberOfResubmittal}'/>
<input type="hidden" name="facilityMaxResubmittal" id="facilityMaxResubmittal" value='${catAddHeaderViewBean.facilityMaxResubmittal}'/>
<input type="hidden" name="application" id="application" value=""/>
<input type="hidden" name="storageAction" id="storageAction" value=""/>
<input type="hidden" name="hasCatalogAddStorageTab" id="hasCatalogAddStorageTab" value='${hasCatalogAddStorageTab}'/>
<input type="hidden" name="userErrorMsg" id="userErrorMsg" value='${tcmISError}'/>
<input type="hidden" name="showReplacesMsds" id="showReplacesMsds" value='${showReplacesMsds}'/>
<input type="hidden" name="requireCustomerMsds" id="requireCustomerMsds" value='${catAddHeaderViewBean.requireCustomerMsds}'/>
<input type="hidden" name="allowEditSpec" id="allowEditSpec" value='${catAddHeaderViewBean.allowEditSpec}'/>
<input type="hidden" name="allowEditMatlCategorySubcategory" id="allowEditMatlCategorySubcategory" value='${catAddHeaderViewBean.allowEditMatlCategorySubcategory}'/>    
<input type="hidden" name="hasMaterialCategoryOption" id="hasMaterialCategoryOption" value='${catAddHeaderViewBean.hasMaterialCategoryOption}'/>    
<input type="hidden" name="selectedMaterialCategoryId" id="selectedMaterialCategoryId" value='${catAddHeaderViewBean.selectedMaterialCategoryId}'/>
<input type="hidden" name="selectedMaterialSubcategoryId" id="selectedMaterialSubcategoryId" value='${catAddHeaderViewBean.selectedMaterialSubcategoryId}'/>
<input type="hidden" name="seaGatePopUp" id="seaGatePopUp" value='${seaGatePopUp}'/>
<input type="hidden" name="catAddEmapAuditOption" id="catAddEmapAuditOption" value='${catAddHeaderViewBean.catAddEmapAuditOption}'/>
<input type="hidden" name="updateMixRatios" id="updateMixRatios" value='Y'/>
<input type="hidden" name="timeTempSensitive" id="timeTempSensitive" value='${catAddHeaderViewBean.timeTempSensitive}'/>
<input type="hidden" name="catAddRequestorEditMsdsId" id="catAddRequestorEditMsdsId" value='${catAddHeaderViewBean.catAddRequestorEditMsdsId}'/>
<input type="hidden" name="customerMsdsDb" id="customerMsdsDb" value='${catAddHeaderViewBean.customerMsdsDb}'/>
<input type="hidden" name="allowEditMixtureData" id="allowEditMixtureData" value='${catAddHeaderViewBean.allowEditMixtureData}'/>
<input type="hidden" name="showItarControl" id="showItarControl" value='${showItarControl}'/>
<input type="hidden" name="disabledSpecUpload" id="disabledSpecUpload" value='${tcmis:isFeatureReleased(personnelBean,'DisabledSpecUploadInCatAdd',catAddHeaderViewBean.engEvalFacilityId)}'/>
<input type="hidden" name="secureDocViewer" id="secureDocViewer" value='${tcmis:isCompanyFeatureReleased(personnelBean,'SecureDocViewer','', catAddHeaderViewBean.companyId)}'/>
<input type="hidden" name="qplContainsKit" id="qplContainsKit" value="<tcmis:jsReplace value="${param.qplContainsKit}"/>"/>
<input type="hidden" name="mixRatioRequired" id="mixRatioRequired" value='${catAddHeaderViewBean.mixRatioRequired}'/>
<input type="hidden" name="allowMultipleHmrb" id="allowMultipleHmrb" value='${catAddHeaderViewBean.catAddAllowMultipleHmrb}'/>
<input type="hidden" name="catalogHasDefaultTest" id="catalogHasDefaultTest" value='${catAddHeaderViewBean.catalogHasDefaultTest}'/>
<input type="hidden" name="newMaterial" id="newMaterial" value='${catAddHeaderViewBean.newMaterial}'/>
<input type="hidden" name="newPart" id="newPart" value='${catAddHeaderViewBean.newPart}'/>
<input type="hidden" name="label1SetCarnProdMatlVal" id="label1SetCarnProdMatlVal" value='${label1SetCarnProdMatlVal}'/>
<input type="hidden" name="label2SetCarnProdMatlVal" id="label2SetCarnProdMatlVal" value='${label2SetCarnProdMatlVal}'/>
<input type="hidden" name="qualityIdLabel" id="qualityIdLabel" value='${qualityIdLabel}'/>
</div>
<!-- Hidden elements end -->
</tcmis:form>
</div>
<!-- close of contentArea -->

</div> <!-- close of interface -->

<div id="hiddenFrame" style="display: none;">
	<iframe scrolling="no" id="catalogAddRequestFrame" name="catalogAddRequestFrame" width="100%" height="300" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
</div>
<div id="hiddenFrame" style="display: none;">
	<iframe scrolling="no" id="catalogAddRequestQplFrame" name="catalogAddRequestQplFrame" width="100%" height="300" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
</div>
<div id="hiddenFrame" style="display: none;">
	<iframe scrolling="no" id="catalogAddRequestUseApprovalFrame" name="catalogAddRequestUseApprovalFrame" width="100%" height="300" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
</div>
<div id="hiddenFrame" style="display: none;">
	<iframe scrolling="no" id="catalogAddRequestSpecFrame" name="catalogAddRequestSpecFrame" width="100%" height="300" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
</div>

</body>
</html:html>