<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>

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

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<!-- CSS for YUI -->
<%--
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>

<!-- Add any other stylesheets you need for the page here -->
<link rel="stylesheet" type="text/css" href="/css/tabs.css"></link>                

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<%@ include file="/common/locale.jsp" %>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>

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
<script type="text/javascript" src="/js/client/catalog/engeval.js"></script>
<script type="text/javascript" src="/js/client/catalog/shownewchemappdetail.js"></script>
<script type="text/javascript" src="/js/client/catalog/chemicalapprovaldata.js"></script>
<script type="text/javascript" src="/js/client/catalog/approvalcomments.js"></script>

<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<!-- These are for the Grid-->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<%--This is for HTML form integration.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<%--This is for smart rendering.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<%--This is to suppoert loading by JSON.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>    
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
-->
<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--This file has our custom sorting and other utility methos for the grid.--%>    
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
	<fmt:message key="buyorders.legend.engineeringevaluation"/>
</title>

<c:set var="workArea">
 <fmt:message key="label.workarea"/>
</c:set>

<script language="JavaScript" type="text/javascript">
<!--
var config = [
{ columnId:"facilityName",
  columnName:'<fmt:message key="label.facility"/>',
  sorting:'haasStr',
  width:15
},
{ columnId:"workArea",
  columnName:'<tcmis:jsReplace value="${workArea}"/>',
  sorting:'haasStr',
  width:15
},
{ columnId:"userGroupId",
  columnName:'<fmt:message key="label.usergroup"/>',
  sorting:'haasStr',
  width:8
},
{ columnId:"qty",
  columnName:'<fmt:message key="label.qty"/>',
  sorting:'int',
  width:5
},
{ columnId:"submitDate",
  columnName:'<fmt:message key="label.date"/>',
  sorting:'int',
  hiddenSortingColumn:"hiddenSubmitDateTime",
  width:8
},
{
  columnId:"hiddenSubmitDateTime",  	
  sorting:'int'
},
{ columnId:"requestorInfo",
  columnName:'<fmt:message key="label.requestor"/>',
  sorting:'haasStr',
  width:25
}
];

// -->
</script>

</head>


<body bgcolor="#ffffff" onLoad="editOnLoad('${param.uAction}');if('${tcmISError}' != null && '${tcmISError}'.length > 0)showErrorMessages();">

<script language="JavaScript" type="text/javascript">
<!--
with(milonic=new menuname("nchemcontextMenu")){
top="offset=2"
style = contextStyle;
margin=3
aI("text=<fmt:message key="label.addcomponent"/>;url=javascript:addNewTab();");
aI("text=<fmt:message key="label.removecomponent"/>;url=javascript:removeTab();");
}

with(milonic=new menuname("nchemcontextMenuWithoutDelete")){
top="offset=2"
style = contextStyle;
margin=3
aI("text=<fmt:message key="label.addcomponent"/>;url=javascript:addNewTab();");
}
// -->
</script>

<script language="JavaScript" type="text/javascript">
<!--

<c:set var="tmpSizeUnitSizeColl" value='${catAddHeaderViewBean.sizeUnitViewColl}'/>
<bean:size id="tmpSizeUnitSize" name="tmpSizeUnitSizeColl"/>
var altSizeUnitRequiredNetWt = new Array(
<c:forEach var="sizeUnitViewBean" items="${catAddHeaderViewBean.sizeUnitViewColl}" varStatus="status">
 <c:choose>
   <c:when test="${status.index == 0 && tmpSizeUnitSize > 1}">
     "N","<c:out value="${status.current.netWtRequired}"/>"
   </c:when>
   <c:otherwise>
     <c:choose>
       <c:when test="${status.index == 0}">
         "<c:out value="${status.current.netWtRequired}"/>"
       </c:when>
       <c:otherwise>
         ,"<c:out value="${status.current.netWtRequired}"/>"
       </c:otherwise>
     </c:choose>
   </c:otherwise>
  </c:choose>
</c:forEach>
);

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

var prRulesColl = new Array();
<c:forEach var="prRulesBean" items="${materialRequestData.prRulesColl}" varStatus="status">
	prRulesColl[${status.index}]={
		chargeType:'${prRulesBean.chargeType}',
		poRequired:'${prRulesBean.poRequired}',
	   prAccountRequired:'${prRulesBean.prAccountRequired}',
		chargeDisplay1:'${prRulesBean.chargeDisplay1}',
		chargeDisplay2:'${prRulesBean.chargeDisplay2}',
		chargeDisplay3:'${prRulesBean.chargeDisplay3}',
		chargeDisplay4:'${prRulesBean.chargeDisplay4}',
		chargeLabel1:'${prRulesBean.chargeLabel1}',
		chargeLabel2:'${prRulesBean.chargeLabel2}',
		chargeLabel3:'${prRulesBean.chargeLabel3}',
		chargeLabel4:'${prRulesBean.chargeLabel4}',
		chargeAllowNull1:'${prRulesBean.chargeAllowNull1}',
		chargeAllowNull2:'${prRulesBean.chargeAllowNull2}',
		chargeAllowNull3:'${prRulesBean.chargeAllowNull3}',
		chargeAllowNull4:'${prRulesBean.chargeAllowNull4}',
		unitPriceRequired:'${prRulesBean.unitPriceRequired}',
		poSeqRequired:'${prRulesBean.poSeqRequired}',
		customerRequisition:'${prRulesBean.customerRequisition}',
		chargeAllowEdit1:'${prRulesBean.chargeAllowEdit1}',
		chargeAllowEdit2:'${prRulesBean.chargeAllowEdit2}',
		chargeAllowEdit3:'${prRulesBean.chargeAllowEdit3}',
		chargeAllowEdit4:'${prRulesBean.chargeAllowEdit4}'
	};
</c:forEach>

var currencyColl = new Array();
<c:forEach var="tmpBean" items="${materialRequestData.currencyColl}" varStatus="status">
	currencyColl[${status.index}]={
		currencyId:'${tmpBean.currencyId}',
		currencyDescription:'${tmpBean.currencyDescription}',
	   exchangeRate:'${tmpBean.exchangeRate}'
	};
</c:forEach>

var deliveryTypeColl = new Array();
<c:forEach var="tmpBean" items="${materialRequestData.deliveryTypeColl}" varStatus="status">
	deliveryTypeColl[${status.index}]={
		deliveryType:'${tmpBean.deliveryType}',
		description:'${tmpBean.description}'
	};
</c:forEach>

var dockDeliveryPointColl = new Array();
var facAccountSysPoForDirectColl = new Array();
var facAccountSysPoForIndirectColl = new Array();
var prAccountColl = new Array();
var chargeNumberForDirectColl = new Array();
var chargeNumberForIndirectColl = new Array();
<c:forEach var="lineItemBean" items="${materialRequestData.lineItemColl}" varStatus="status">
	dockDeliveryPointColl['${lineItemBean.lineItem}']={
		dockColl:[
			<c:forEach var="tmpBean" items="${lineItemBean.dockDeliveryPointColl}" varStatus="status2">
			 	<c:if test="${status2.index != 0 }">,</c:if>
				{
				dockLocationId:'${tmpBean.dockLocationId}',
				dockDesc:'${tmpBean.dockDesc}',
				deliveryPointColl:[
					 <c:forEach var="tmpBean2" items="${tmpBean.deliveryPointColl}" varStatus="status3">
						<c:if test="${status3.index != 0 }">,</c:if>
						{
							deliveryPoint: '<tcmis:jsReplace value="${tmpBean2.deliveryPoint}"/>',
							deliveryPointDesc: '<tcmis:jsReplace value="${tmpBean2.deliveryPointDesc}"/>'
						}
					 </c:forEach>
				]
				}
			</c:forEach>
		]
	};

	facAccountSysPoForDirectColl['${lineItemBean.lineItem}']=[
		<c:forEach var="tmpBean" items="${lineItemBean.facAccountSysPoForDirectColl}" varStatus="status2">
			<c:if test="${status2.index != 0 }">,</c:if>
			{
				poNumber:'${tmpBean.poNumber}'
			}
		</c:forEach>
	];

	facAccountSysPoForIndirectColl['${lineItemBean.lineItem}']=[
		<c:forEach var="tmpBean" items="${lineItemBean.facAccountSysPoForIndirectColl}" varStatus="status2">
			 <c:if test="${status2.index != 0 }">,</c:if>
			 {
			 	poNumber:'${tmpBean.poNumber}'
			 }
		</c:forEach>
	];

	prAccountColl['${lineItemBean.lineItem}']=[
		<c:forEach var="tmpBean" items="${lineItemBean.prAccountColl}" varStatus="status2">
		 	<c:if test="${status2.index != 0 }">,</c:if>
			{
				accountNumber:'${tmpBean.accountNumber}',
		   	accountNumber2:'${tmpBean.accountNumber2}',
				accountNumber3:'${tmpBean.accountNumber3}',
				accountNumber4:'${tmpBean.accountNumber4}',
				percentage:'${tmpBean.percentage}',
				chargeType:'${tmpBean.chargeType}'
			}
		</c:forEach>
	];

	chargeNumberForDirectColl['${lineItemBean.lineItem}']=[
		<c:forEach var="tmpBean" items="${lineItemBean.chargeNumberForDirectColl}" varStatus="status2">
		 	<c:if test="${status2.index != 0 }">,</c:if>
			{
				chargeNumber1:'${tmpBean.chargeNumber1}',
		   	chargeNumber2:'${tmpBean.chargeNumber2}',
				chargeNumber3:'${tmpBean.chargeNumber3}',
				chargeNumber4:'${tmpBean.chargeNumber4}',
				percent:'${tmpBean.percent}'
			}
		</c:forEach>
	];

	chargeNumberForIndirectColl['${lineItemBean.lineItem}']=[
		<c:forEach var="tmpBean" items="${lineItemBean.chargeNumberForIndirectColl}" varStatus="status2">
		 	<c:if test="${status2.index != 0 }">,</c:if>
			{
				chargeNumber1:'${tmpBean.chargeNumber1}',
		   	chargeNumber2:'${tmpBean.chargeNumber2}',
				chargeNumber3:'${tmpBean.chargeNumber3}',
				chargeNumber4:'${tmpBean.chargeNumber4}',
				percent:'${tmpBean.percent}'
			}
		</c:forEach>
	];

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
hline:"<fmt:message key="label.line"/>",
hworkarea:"<fmt:message key="label.workarea"/>",
hpart:"<fmt:message key="label.part"/>",
hdesc:"<fmt:message key="label.description"/>",
htype:"<fmt:message key="label.type"/>",
hqty:"<fmt:message key="label.qty"/>",
hcritical:"<fmt:message key="label.critical"/>",
notes:"<fmt:message key="label.notes"/>",
lineitemdetail:"<fmt:message key="label.lineitemdetail"/>",
missingdeliverto:"<fmt:message key="label.missingdeliverto"/>",
missingqty:"<fmt:message key="label.missingqty"/>",
invalidqtypart1:"<fmt:message key="label.invalidqtypart1"/>",
invalidqtypart2:"<fmt:message key="label.invalidqtypart2"/>",
missingdeliverdate:"<fmt:message key="label.missingdeliverdate"/>",
missingpo:"<fmt:message key="label.missingpo"/>",
missingpoqty:"<fmt:message key="label.missingpoqty"/>",
missingpouom:"<fmt:message key="label.missingpouom"/>",
missingpounitprice:"<fmt:message key="label.missingpounitprice"/>",
missingcurrency:"<fmt:message key="label.missingcurrency"/>",
missingchargenumber:"<fmt:message key="label.missingchargenumber"/>",
missingpercent:"<fmt:message key="label.missingpercent"/>",
invalidpercent:"<fmt:message key="label.invalidpercent"/>",
unitpricedisplayerror:"<fmt:message key="label.unitpricedisplayerror"/>",
missingcustreq:"<fmt:message key="label.missingcustreq"/>",
component:"<fmt:message key="label.component"/>",
lineapprovedby:"<fmt:message key="label.lineapprovedby"/>",
financeapproverinformation:"<fmt:message key="label.financeapproverinformation"/>",
pleasewait:"<fmt:message key="label.pleasewait"/>",
pleasewaitmenu:"<fmt:message key="label.pleasewaitmenu"/>",
approvaldetail:"<fmt:message key="label.approvaldetail"/>",
norowselected:"<fmt:message key="label.norowselected"/>",
manufacturer:"<fmt:message key="label.manufacturer"/>",
materialdescription:"<fmt:message key="label.materialdescription"/>",
manufacturertradename:"<fmt:message key="label.manufacturertradename"/>",
dimension:"<fmt:message key="label.dimension"/>",
netsize:"<fmt:message key="label.netsize"/>",
netunit:"<fmt:message key="label.netunit"/>",
numperpart:"<fmt:message key="catalog.label.numperpart"/>",
size:"<fmt:message key="label.size"/>",
unit:"<fmt:message key="label.unit"/>",
packagestyle:"<fmt:message key="label.packagestyle"/>",
suppliername:"<fmt:message key="label.suppliername"/>",
contactname:"<fmt:message key="label.contactname"/>",
contactphone:"<fmt:message key="label.contactphone"/>",
qty:"<fmt:message key="label.qty"/>",
deliveryby:"<fmt:message key="label.deliveryby"/>",
dock:"<fmt:message key="label.dock"/>",
deliverto:"<fmt:message key="label.deliverto"/>",
po:"<fmt:message key="label.po"/>",
line:"<fmt:message key="label.line"/>",
itemInteger:"<fmt:message key="error.item.integer"/>",
view:"<fmt:message key="label.view"/>",
viewEdit:"<fmt:message key="lavel.viewedit"/>",
returnSelectedData:"<fmt:message key="label.returnselecteddata"/>",
cancel:"<fmt:message key="label.cancel"/>",
comments:"<fmt:message key="label.comments"/>",
add:"<fmt:message key="label.add"/>"
};
// -->
 </script>


<tcmis:form action="/engeval.do" onsubmit="" target="_self">

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

<%-- the reason that I have the include here is because the data is set here as well as catalogaddrequest.jsp --%>
<%@ include file="/client/catalog/chemicalapprovaldata.jsp" %>
<%@ include file="/client/catalog/approvalcomments.jsp" %>

<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
  ${tcmISError}<br/>
  <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
  </c:forEach>
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

<!-- Transit Page Begins -->
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->

<!-- start of contentArea -->
<div class="contentArea">

<!-- Search Option Begins -->
<%--Change the width to what you want your page to span.--%>
<div id="searchMaskTable" style="width:980px">
<%-- Left --%>
<div style="width:140px;float:left">
<div class="roundcont filterContainer" >
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
    <table width="140px" height="525px" class="tableSearch">
		<tr>
        <td>
          <fieldset>
            <legend><fmt:message key="label.requestinfo"/></legend>
              <table>
					<tr><td class="optionTitleBoldCenter" nowrap><fmt:message key="label.requestor"/>:</td></tr>
            	<tr><td align="center">${catAddHeaderViewBean.requestorName}</td></tr>
					<tr><td class="optionTitleBoldCenter" nowrap><fmt:message key="label.startingpoint"/>:</td></tr>
					<c:set var="startingPoint" value='${catAddHeaderViewBean.evalType}'/>
					<c:choose>
						<c:when test="${startingPoint == 'new'}">
						 <tr><td align="center"><fmt:message key="label.newengeval"/></td></tr>
						</c:when>
						<c:otherwise>
						 <tr><td align="center"><fmt:message key="label.reorderengeval"/></td></tr>
						</c:otherwise>
					</c:choose>
            	<tr><td class="optionTitleBoldCenter" nowrap><fmt:message key="label.requestnumber"/>:</td></tr>
            	<tr><td align="center">${catAddHeaderViewBean.requestId}</td></tr>
            	<tr><td class="optionTitleBoldCenter" nowrap><fmt:message key="label.daterequested"/>:</td></tr>
            	<fmt:formatDate var="fmtRequestDate" value="${catAddHeaderViewBean.requestDate}" pattern="${dateFormatPattern}"/>
            	<tr><td align="center">${fmtRequestDate}</td></tr>
              </table>
          </fieldset>
			 <br/>
			 <fieldset>
            <legend><fmt:message key="label.sections"/></legend>
              <table>
             	<tr><td>
            		<input type="button" class="inputBtns" id="ssB1" value="<fmt:message key="label.section1"/>"  onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="if(!ssButton1){this.className='inputBtns';}"
          				 onclick= "showSection1(this); return false;"/>
          		</td></tr>
          		 <tr><td>
            		<input type="button" id="ssB2" class="inputBtns" value="<fmt:message key="label.section2"/>"  onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="if(!ssButton2){this.className='inputBtns';}"
          				 onclick= "showSection2(this); return false;"/>
          		</td></tr>
          		 <tr><td>
            		<input type="button" id="manageSDSBtn" class="inputBtns" value="<fmt:message key="label.managesds"/>"  onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="if(!ssButton2){this.className='inputBtns';}"
          				 onclick= "manageSDS(); return false;"/>
          		</td></tr>
               </table>
           </fieldset>
			  <br/>
           <fieldset>
             <legend><fmt:message key="label.actions"/></legend>
             <table>
             	<tr><td>
						 <span id="submitSpan">
						 	<input name="submitSubmit" type="button"  class="inputBtns" value="<fmt:message key="button.submit"/>" id="submitSubmit" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          					 onclick= "submitSubmitForm()"/>
						 </span>
					  </td></tr>
					 <tr><td>
						 <span id="saveSpan">
						 	<input name="submitSave" type="submit" class="inputBtns" value="<fmt:message key="label.save"/>" id="submitSave" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 			onclick= "return submitSaveForm()"/>
						 </span>
					  </td></tr>
					 <tr><td>
						 <span id="deleteSpan">
						 	<input name="submitDelete" type="submit"  class="inputBtns" value="<fmt:message key="label.delete"/>" id="submitDelete" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          					 onclick= "return submitDeleteForm()"/>
						</span>
					  </td></tr>
          		 <tr><td>
						 <span id="approveSpan">
						 	<input name="submitApprove" type="button" class="inputBtns" value="<fmt:message key="label.approve"/>" id="submitApprove" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          				 	onclick= "submitApproveForm()"/>
						 </span>
						 <span id="approvalDetailSpan">
						 	<input name="submitApprove" type="button" class="inputBtns" value="<fmt:message key="label.approvaldetail"/>" id="approvalDetail" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          					 onclick= "getApprovalDetail()"/>
						 </span>
					  </td></tr>
             </table>
           </fieldset>
        </td>
      </tr>
	 </table>
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</div>

<div style="width:820px;float:right">
<%-- Right Section 1 --%>
<!-- Initialize Menus -->
<script>
 drawMenus();
</script>

<!-- CSS Tabs -->
<div id="newChemTabs">
 <ul id="mainTabList">
  <%--<li id="homeli"><a href="#" id="homeLink" class="selectedTab" onclick="togglePage('home'); return false;"><span id="homeLinkSpan" class="selectedSpanTab"><img src="/images/home.gif" class="iconImage">Home<br class="brNoHeight"><img src="/images/minwidth.gif" width="8" height="0"></span></a></li>--%>
 </ul>
</div>
<!-- CSS Tabs End -->

<script language="JavaScript" type="text/javascript">
<!--
function startOnload()
{

<c:set var="dataCount" value='${0}'/>
<c:set var="selectedTabId" value=''/>

<c:forEach var="catalogAddItemBean" items="${catAddHeaderViewBean.catalogAddItemColl}" varStatus="status">
 <c:if test="${dataCount == 0}">
  <c:set var="selectedTabId" value="${dataCount}"/>
 </c:if>
 openTab('<c:out value="${dataCount}"/>','','<fmt:message key="label.component"/>','','');
 <c:set var="dataCount" value='${dataCount+1}'/>
</c:forEach>

}

var newMsdsViewer = false;

<tcmis:featureReleased feature="NewMsdsViewer" scope="ALL">
   newMsdsViewer = true;
</tcmis:featureReleased>
// -->
</script>

<div id="section1" class="roundcont filterContainer" style="width:840px;">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
    <table height="500px" class="tableSearch">
    <tr>
    <td>
    <div id="tabsdiv">
      <c:set var="partCount" value='0'/>
      <c:forEach var="catalogAddItem" items="${catAddHeaderViewBean.catalogAddItemColl}" varStatus="status">
        <div id="newItem${partCount}">
            <fieldset>
              <legend><fmt:message key="label.newmaterials"/></legend>
              <table class="tableSearch">
                <tr><td class="optionTitleBoldLeft" colspan="2"><fmt:message key="label.manufacturer"/> - <fmt:message key="label.240charsmax"/>:*</td></tr>
           	    <tr>
					 	<td width="80%" colspan="2">
					  		<input class="inputBox" type="hidden" name="catalogAddItemBean[${partCount}].manufacturerId" id="manufacturerId${partCount}" value="" >
	  						<input class="inputBox" type="text" name="catalogAddItemBean[${partCount}].manufacturer" id="manufacturer${partCount}" value="${catalogAddItem.manufacturer}" size="110" maxlength="240">
      					<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="selectedManufacturer" id="selectedManufacturer" value="..." align="right" onClick="lookupManufacturer();"/>
						</td>
					</tr>
               <tr>
						<td class="optionTitleBoldLeft" width="80%" nowrap>
							<fmt:message key="label.materialdescription"/> - <fmt:message key="label.240charsmax"/>:* (<fmt:message key="label.includeallinformation"/>)
						</td>
                  <td class="optionTitleBoldLeft" width="20%">
							<fmt:message key="label.grade"/>:
						</td>
               </tr>
               <tr>
	    				<td width="80%">
							 <input type="text" class="inputBox" name="catalogAddItemBean[${partCount}].materialDesc" id="materialDesc${partCount}" value="${catalogAddItem.materialDesc}" size="110" maxLength="240"/>
						</td>
        				<td width="20%">
							  <input class="inputBox" type="text" name="catalogAddItemBean[${partCount}].grade" id="grade${partCount}" value="${catalogAddItem.grade}" size="20"/>
						</td>
               </tr>
               <tr>
                  <td width="80%" class="optionTitleBoldLeft">
                    <fmt:message key="label.manufacturertradename"/> - <fmt:message key="label.100charsmax"/>:* (<fmt:message key="label.frommsds"/>)<br>
            	  </td>
                  <td width="20%">
                      <c:if test="${catalogAddItem.msdsOnLine == 'Y'}">
                        <input name="viewMsds[${partCount}]" type="button" class="inputBtns" value="<fmt:message key="label.viewmsds"/>" id="viewMsds[${partCount}]" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'" onclick= "viewMsds(${catalogAddItem.materialId})">
                      </c:if>
                  </td>
               </tr>
           	   <tr>
               	<td colspan="2">
	  						<input class="inputBox" type="text" name="catalogAddItemBean[${partCount}].mfgTradeName" id="mfgTradeName${partCount}" value="${catalogAddItem.mfgTradeName}" size="100" />
            	   </td>
           	   </tr>
           	   <tr>
                  <td class="optionTitleBoldLeft" colspan="2">
							<fmt:message key="label.manufacturerrecommendedshelflifeatstorage"/>: ${catalogAddItem.shelfLifeDays} ${catalogAddItem.shelfLifeBasis}
						</td>
           	   </tr>
              </table>
            </fieldset>

            <input name="catalogAddItemBean[${partCount}].newTabComponent" id="newTabComponent${partCount}" type="hidden" value="New">
            <input name="catalogAddItemBean[${partCount}].netWtRequired" id="netWtRequired${partCount}" type="hidden" value="">

				<fieldset>
              <legend><fmt:message key="label.newsizepacking"/></legend>
              <table>
           	    <tr>
            	 	<td class="optionTitleBoldLeft" width="50%" colspan="4">
            			<fmt:message key="label.manufacturerpartnumber"/>:
            	  	</td>
            	  	<td class="optionTitleBoldLeft" width="20%">
							<span id="dimensionLabelSpan${partCount}" name="dimensionLabelSpan${partCount}">
								<fmt:message key="label.dimension"/>:*
							</span>
						</td>
                  <td class="optionTitleBoldLeft" width="15%">
							<span id="netWtLabelSpan${partCount}" name="netWtLabelSpan${partCount}">
								<fmt:message key="label.netsize"/>:*
							</span>
						  </td>
            	  	<td class="optionTitleBoldLeft" width="15%">
							<span id="netWtUnitLabelSpan${partCount}" name="netWtUnitLabelSpan${partCount}">
								<fmt:message key="label.netunit"/>:*
							</span>
						  </td>
                </tr>
                <tr>
	  					<td width="50%" colspan="4">
            			<input class="inputBox" type="text" name="catalogAddItemBean[${partCount}].mfgCatalogId" id="mfgCatalogId${partCount}" value="${catalogAddItem.mfgCatalogId}"  size="50" maxlength="100">
                  </td>
                  <td width="20%">
							<span id="dimensionSpan${partCount}" name="dimensionSpan${partCount}">
								<input class="inputBox" type="text" name="catalogAddItemBean[${partCount}].dimension" id="dimension${partCount}" value="${catalogAddItem.dimension}"  size="30" maxlength="30">
							</span>
						  </td>
            	  	<td width="15%">
							<span id="netWtSpan${partCount}" name="netWtSpan${partCount}">
								<input class="inputBox" type="text" name="catalogAddItemBean[${partCount}].netwt" id="netwt${partCount}" value="${catalogAddItem.netwt}"  size="10" maxlength="10">
							</span>
						</td>
                  <td width="15%">
							<span id="netWtUnitSpan${partCount}" name="netWtUnitSpan${partCount}">
								<select class="selectBox" name="catalogAddItemBean[${partCount}].netwtUnit" id="netwtUnit${partCount}" >
            						<c:set var="selectedNetwtUnit" value='${catalogAddItem.netwtUnit}'/>
									<c:set var="sizeUnitViewColl" value='${catAddHeaderViewBean.netWtSizeUnitColl}'/>
									<bean:size id="sizeUnitViewSize" name="sizeUnitViewColl"/>
									<c:if test="${sizeUnitViewSize > 1}">
										<option value="Select One" selected><fmt:message key="label.selectOne"/></option>
									</c:if>
									<c:forEach var="sizeUnitView" items="${catAddHeaderViewBean.netWtSizeUnitColl}" varStatus="status3">
										<c:set var="currentNetwtUnit" value='${status3.current.sizeUnit}'/>
										<c:choose>
											<c:when test="${currentNetwtUnit == selectedNetwtUnit}">
												<option value="<c:out value="${status3.current.sizeUnit}"/>" selected><c:out value="${status3.current.sizeUnit}" escapeXml="false"/></option>
											</c:when>
											<c:otherwise>
												<option value="<c:out value="${status3.current.sizeUnit}"/>"><c:out value="${status3.current.sizeUnit}" escapeXml="false"/></option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
				    			</select>
							</span>
						</td>
                </tr>
                <tr>
                  <td class="optionTitleBoldLeft" width="15%">
            		<fmt:message key="catalog.label.numperpart"/>:*
            	  </td>
            	  <td width="1%"> &nbsp; </td>
            	  <td class="optionTitleBoldLeft" width="15%">
            		<fmt:message key="label.size"/>:*
            	  </td>
            	  <td class="optionTitleBoldLeft" width="20%">
            		<fmt:message key="label.unit"/>:*
            	  </td>
            	  <td class="optionTitleBoldLeft" width="50%" colspan="3">
            		<fmt:message key="label.packagestyle"/>:*
                  </td>
                </tr>
                <tr>
	    		  <td>
            		<input class="inputBox" type="text" name="catalogAddItemBean[${partCount}].componentsPerItem" id="componentsPerItem${partCount}" value="${catalogAddItem.componentsPerItem}" size="12" maxlength="12">
                  </td>
                  <td width="1%"> X </td>
                  <td width="15%" nowrap>
            		<input class="inputBox" type="text" name="catalogAddItemBean[${partCount}].partSize" id="partSize${partCount}" value="${catalogAddItem.partSize}" size="10" maxlength="10">
            	  </td>
            	  <td width="5%" class="optionTitleLeft">
	                		<select class="selectBox" name="catalogAddItemBean[${partCount}].sizeUnit" id="sizeUnit${partCount}" onchange="unitChanged()" >
	                				<c:set var="selectedSizeUnit" value='${catalogAddItem.sizeUnit}'/>
									<c:set var="sizeUnitSizeColl" value='${catAddHeaderViewBean.sizeUnitViewColl}'/>
									<bean:size id="sizeUnitSize" name="sizeUnitSizeColl"/>
									<c:if test="${sizeUnitSize > 1}">
										<option value="Select One" selected><fmt:message key="label.selectOne"/></option>
									</c:if>
									<c:forEach var="vvSizeUnitBean" items="${catAddHeaderViewBean.sizeUnitViewColl}" varStatus="status1">
										<c:set var="currentSizeUnit" value='${status1.current.sizeUnit}'/>
										<c:choose>
											<c:when test="${currentSizeUnit == selectedSizeUnit}">
												<option value="<c:out value="${status1.current.sizeUnit}"/>" selected><c:out value="${status1.current.sizeUnit}" escapeXml="false"/></option>
											</c:when>
											<c:otherwise>
												<option value="<c:out value="${status1.current.sizeUnit}"/>"><c:out value="${status1.current.sizeUnit}" escapeXml="false"/></option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
				    			</select>
                  </td>
				  <td width="10%" colspan="2" class="optionTitleLeft">
			                		<select class="selectBox" name="catalogAddItemBean[${partCount}].pkgStyle" id="pkgStyle${partCount}">
			                			<c:set var="selectedPkgStyle" value='${catalogAddItem.pkgStyle}'/>
										<c:set var="pkgStyleSizeColl" value='${vvPkgStyleBeanCollection}'/>
										<bean:size id="pkgStyleSize" name="pkgStyleSizeColl"/>
										<c:if test="${pkgStyleSize > 1}">
											<option value="Select One" selected><fmt:message key="label.selectOne"/></option>
										</c:if>
										<c:forEach var="vvPkgStyleBean" items="${vvPkgStyleBeanCollection}" varStatus="status2">
											<c:set var="currentPkgStyle" value='${status2.current.pkgStyle}'/>
											<c:choose>
												<c:when test="${currentPkgStyle == selectedPkgStyle}">
													<option value="<c:out value="${status2.current.pkgStyle}"/>" selected><c:out value="${status2.current.pkgStyle}" escapeXml="false"/></option>
												</c:when>
												<c:otherwise>
													<option value="<c:out value="${status2.current.pkgStyle}"/>"><c:out value="${status2.current.pkgStyle}" escapeXml="false"/></option>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
              	  </td>
            	  <td class="optionTitleBoldLeft" nowrap>
							<c:choose>
							<c:when test="${catalogAddItem.sampleOnly == 'Y'}">
								<input type="checkbox" value="Y" name="catalogAddItemBean[${partCount}].sampleOnly" id="sampleOnly${partCount}" checked class="radioBtns">&nbsp;
							</c:when>
							<c:otherwise>
								 <input type="checkbox" value="" name="catalogAddItemBean[${partCount}].sampleOnly" id="sampleOnly${partCount}" class="radioBtns">&nbsp;
							</c:otherwise>
							</c:choose>
						 <fmt:message key="label.samplesizing"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  </td>
                </tr>
              </table>
            </fieldset>
             </div>
      <c:set var="partCount" value='${partCount+1}'/>
    </c:forEach>
    </div>
	       
          <fieldset>
            <legend><fmt:message key="label.suggestSupplier"/></legend>
            <table>
           	  <tr>
            	<td class="optionTitleBoldLeft" width="33%">
            		<fmt:message key="label.suppliername"/>:*
            	</td>
            	<td class="optionTitleBoldLeft" width="67%" colspan="2">
            		<fmt:message key="label.contactname"/>:*
                </td>
              </tr>
              <tr>

	    <td width="33%"><input class="inputBox" type="text" name="suggestedVendor" id="suggestedVendor" value="${catAddHeaderViewBean.suggestedVendor}" size="33" maxlength="60"></td>
        <td width="33%"><input class="inputBox" type="text" name="vendorContactName" id="vendorContactName" value="${catAddHeaderViewBean.vendorContactName}" size="33" maxlength="60"></td>
            	<td class="optionTitleBoldLeft" width="34%" nowrap>
            		<c:choose>
						<c:when test="${catAddHeaderViewBean.freeSample == 'Y'}">
					    	<input type="checkbox" value="Y" id="freeSample" name="freeSample" checked class="radioBtns">&nbsp;
    					</c:when>
						<c:otherwise>
						    <input type="checkbox" value="" id="freeSample" name="freeSample" class="radioBtns">&nbsp;
						</c:otherwise>
					</c:choose>
                	<fmt:message key="label.supplieroffersasfreesample"/></td>
              </tr>
              <tr>
                <td class="optionTitleBoldLeft" width="33%">
            		<fmt:message key="label.contactemail"/>:
            	</td>
            	<td class="optionTitleBoldLeft" width="33%">
            		<fmt:message key="label.contactphone"/>:*
            	</td>
            	<td class="optionTitleBoldLeft" width="34%">
            		<fmt:message key="label.contactfax"/>:
            	</td>
              </tr>
              <tr>

	    <td width="33%"><input class="inputBox" type="text" name="vendorContactEmail" id="vendorContactEmail" value="${catAddHeaderViewBean.vendorContactEmail}" size="20" maxlength="20"></td>
        <td width="33%"><input class="inputBox" type="text" name="vendorContactPhone" id="vendorContactPhone" value="${catAddHeaderViewBean.vendorContactPhone}" size="20" maxlength="20"></td>
        <td width="34%"><input class="inputBox" type="text" name="vendorContactFax" id="vendorContactFax" value="${catAddHeaderViewBean.vendorContactFax}" size="20" maxlength="20"></td>

              </tr>
            </table>
          </fieldset>
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

<%-- Right Section 2--%>
<div id="section2" class="roundcont filterContainer" style="display: none;" style="width:828px;">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
    <table height="500px" class="tableSearch">
      <tr>
        <td>
          <fieldset>
            <legend><fmt:message key="label.headerinfomaterialrequest"/>&nbsp;${materialRequestData.prNumber}</legend>
            <table>
           	  <tr>
            	<td class="optionTitleBoldRight" width="120"><fmt:message key="label.enduser"/>:</td>
				  <td class="optionTitleBoldLeft" width="20%"><input class="inputBox" type="text" name="endUser" id="endUser" value="${materialRequestData.endUser}" size="30" maxlength="30"></td>
                <td class="optionTitleBoldRight" width="15%"><fmt:message key="label.facility"/>:</td>
            	<td width="20%" nowrap>${materialRequestData.facilityId}</td>
                <td class="optionTitleBoldRight" width="8%"><fmt:message key="label.item"/>:</td>
            	<td width="8%" nowrap>${catAddHeaderViewBean.itemId}</td>
              </tr>
              <tr>
                <tr>
            	<td class="optionTitleBoldRight" width="15%"><fmt:message key="processmsds.label.department"/>:</td>
		   	    <td class="optionTitleBoldLeft" width="20%"><input class="inputBox" type="text" name="department" id="department" value="${materialRequestData.department}" size="30" maxlength="30"></td>
                <td class="optionTitleBoldRight" width="15%" nowrap><fmt:message key="label.workarea"/>:</td>
            	<td width="20%" nowrap>${materialRequestData.lineItemColl[0].applicationDisplay}</td>
                <td class="optionTitleBoldRight" width="8%"><fmt:message key="label.notes"/>:</td>
            	<td class="optionTitleBoldLeft" width="8%" nowrap>
            		<a href="#" onclick="showNotes(); return false;" id="showNotesLink"></a><br> </td>
                <td class="optionTitleBoldRight" width="8%"><fmt:message key="label.critical"/>:</td>
					 <td width="6%">
						 <c:choose>
								<c:when test="${materialRequestData.lineItemColl[0].critical == 'y'}">
									<input type="checkbox" value="y" id="critical" name="critical" checked class="radioBtns">
								</c:when>
								<c:otherwise>
									<input type="checkbox" value="" id="critical" name="critical" class="radioBtns">
								</c:otherwise>
						  </c:choose>

					 </td>
              </tr>
              <tr>
                <td  class="optionTitleBoldRight" width="15%"><fmt:message key="label.desc"/>:</td>
                <td width="85%" colspan="7"><span id="descPipeline" name="descPipeline"></span></td>
              </tr>
            </table>
          </fieldset>

          <fieldset>
            <legend><fmt:message key="label.chargesanddeliveryinfo"/></legend>
            <table>
           	  <tr>
				  	<td width="50%" valign="top" class="optionTitleBoldLeft">
						<table id="chargeNumberTable"  width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="optionTitleBoldRight" width="50%" nowrap>
									<fmt:message key="label.accountsysid"/>:&nbsp;
								</td>
            				<td class="optionTitleLeft" width="50%">
            					${materialRequestData.accountSysId}
								</td>
							</tr>
							<tr>
								<td class="optionTitleBoldCenter" colspan="2" width="100%" >
									<span id="chargeTypeSpan" name="chargeTypeSpan">
										<input type="radio" name="chargeType" id="chargeTypeD" onClick="directedCheck();" value="d" class="radioBtns" checked="checked"/><fmt:message key="label.direct"/>
								 		&nbsp;&nbsp;
										<input type="radio" name="chargeType" id="chargeTypeI" onClick="inDirectedCheck();" value="i" class="radioBtns" /><fmt:message key="label.indirect"/>
									</span>
							 	</td>
							</tr>
							<tr>
							 <td width="100%" rowspan="4" colspan="2">
								<div id="chargeNumber2ColumnsDivId" style="display: none;height: 170px;width: 450px;" ></div>
								<div id="chargeNumber3ColumnsDivId" style="display: none;height: 170px;width: 450px;" ></div>
								<div id="chargeNumber4ColumnsDivId" style="display: none;height: 170px;width: 450px;" ></div>
								<div id="chargeNumber5ColumnsDivId" style="display: none;height: 170px;width: 450px;" ></div> 
							 </td>
						  </tr>
						</table>
					</td>

					<td width="50%" valign="top" class="optionTitleBoldLeft">
						<table  width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="optionTitleBoldRight" width="10%">
									<fmt:message key="label.qty"/>:&nbsp;
								</td>
	    						<td width="90%">
									 <input class="inputBox" type="text" name="qty" id="qty" value="${materialRequestData.lineItemColl[0].quantity}" size="5" maxlength="6">
								</td>
							</tr>
							<tr>
                			<td class="optionTitleBoldRight" width="10%" nowrap>
            					<fmt:message key="label.deliveryby"/>:&nbsp;
            				</td>
            				<td width="90%" nowrap>
					 				<fmt:formatDate var="fmtRequiredDatetime" value="${materialRequestData.lineItemColl[0].defaultNeedByDate}" pattern="${dateFormatPattern}"/>
					 				<input type="hidden" name="today" id="today" value='<tcmis:getDateTag numberOfDaysFromToday="-1" datePattern="${dateFormatPattern}"/>' />
					 				<input class="inputBox pointer" type="text" name="deliveryBy" id="deliveryBy" readonly onClick="return getCalendar(document.genericForm.deliveryBy,$('today'));"
	      							value="${fmtRequiredDatetime}" size="10" maxlength="10">
                			</td>
							</tr>
							<tr>
								<td class="optionTitleBoldRight" width="10%">
									<fmt:message key="label.dock"/>:&nbsp;
								</td>
                			<td width="90%">
	    							<select name="dock" id="dock" class="selectBox" onchange="dockChanged()"></select>
                			</td>
							</tr>
							<tr>
							 <td class="optionTitleBoldRight" width="10%" nowrap>
								<fmt:message key="label.deliverto"/>:&nbsp;
							 </td>
							 <td width="90%">
							 	<select name="deliverTo" id="deliverTo" class="selectBox"></select>
							 </td>
							</tr>
							<tr>
							 <td width="100%" class="optionTitleBoldCenter" colspan="2">
								<hr width="200px">
							 </td>
						  </tr>
						  <tr>
							<td width="10%" class="optionTitleBoldRight">
								<span id="poLabelSpan" name="poLabelSpan">
									<fmt:message key="label.po"/>:&nbsp;
								</span>
							</td>
							<td width="90%" class="optionTitleBoldLeft">
								<span id="poComboSpan" name="poComboSpan">
									<select name="poCombo" id="poCombo" class="selectBox">
									</select>
								</span>
								<span id="poInputSpan" name="poInputSpan">
									<input name="poInput" id="poInput" type="text" class="inputBox" value="" size="10"/>
								</span>
							 </td>
						 	</tr>
							<tr>
							<td width="10%" class="optionTitleBoldRight">
								<span id="poLineLabelSpan" name="poLineSpan">
									<fmt:message key="label.line"/>:&nbsp;
								</span>
							</td>
							<td width="90%" class="optionTitleBoldLeft">
								<span id="poLineSpan" name="poLineSpan">
									<input name="poLineInput" id="poLineInput" type="text" class="inputBox" value="" size="10"/>
								</span>
							 </td>
						 	</tr>
						</table>
					</td>
				  </tr>
            </table>
          </fieldset>
			 
          <fieldset>
            <legend><fmt:message key="label.previousevaluationorders"/></legend>
            <table><tr><td>
            <div id="engEvalHistoryBean" style="width:760px;height:140px;">&nbsp;</div>
<c:if test="${catAddHeaderViewBean.engEvalHistoryColl != null}" >
<script type="text/javascript">
<!--
<c:set var="dataCount" value='${0}'/>
var jsonMainData = {
rows:[
<c:forEach var="engEvalHistory" items="${catAddHeaderViewBean.engEvalHistoryColl}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>

<fmt:formatDate var="fmtSubmitDate" value="${engEvalHistory.submitDate}" pattern="${dateFormatPattern}"/>

  <c:set var="hiddenHubmitDateime" value="${engEvalHistory.submitDate.time}"/>
  
  <tcmis:jsReplace var="facilityName" value="${engEvalHistory.facilityName}" processMultiLines="false" />
  <tcmis:jsReplace var="applicationDisplay" value="${engEvalHistory.applicationDisplay}" processMultiLines="false" />
  <tcmis:jsReplace var="requestorInfo" value="${engEvalHistory.requestor}" processMultiLines="false" />
        
/*The row ID needs to start with 1 per their design. Use sinlge quotes for column data seperators.*/
{ id:${status.index +1},
 data:[
  '${facilityName}','${applicationDisplay}','${engEvalHistory.userGroupId}',
  '${engEvalHistory.qty}','${fmtSubmitDate}','${hiddenSubmitDateime}',
  '${requestorInfo}']}
 <c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
]};
// -->
</script>  
</c:if>   
          </td></tr></table>
          </fieldset> 
            
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
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
</c:if>
<!-- Error Messages Ends -->
<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input type="hidden" name="uAction" id="uAction" value=""/>
<input type="hidden" name="totalLines" id="totalLines" value="${dataCount}" >
<input type="hidden" name="partCount" id="partCount" value='${partCount}'/>
<input type="hidden" name="personnelId" id="personnelId" value='${personnelBean.personnelId}'/>
<input type="hidden" name="requestor" id="requestor" value='${catAddHeaderViewBean.requestor}'/>
<input type="hidden" name="requestStatus" id="requestStatus" value='${catAddHeaderViewBean.requestStatus}'/>
<input type="hidden" name="evalType" id="evalType" value='${catAddHeaderViewBean.evalType}'/>
<input type="hidden" name="facilityId" id="facilityId" value='${catAddHeaderViewBean.engEvalFacilityId}'/>
<input type="hidden" name="application" id="application" value='${catAddHeaderViewBean.engEvalWorkArea}'/>
<input type="hidden" name="itemId" id="itemId" value='${catAddHeaderViewBean.itemId}'/>
<input type="hidden" name="accountSysId" id="accountSysId" value='${catAddHeaderViewBean.accountSysId}'/>
<input type="hidden" name="requestId" id="requestId" value='${catAddHeaderViewBean.requestId}'/>
<input name="companyId" id="companyId" type="hidden" value="${materialRequestData.companyId}">
<input name="prNumber" id="prNumber" type="hidden" value="${materialRequestData.prNumber}">
<input name="lineItem" id="lineItem" type="hidden" value="${materialRequestData.lineItemColl[0].lineItem}">
<input name="canEditMr" id="canEditMr" type="hidden" value="${materialRequestData.canEditMr}">
<input name="quantity" id="quantity" type="hidden" value="${materialRequestData.lineItemColl[0].quantity}">
<input name="releaseNumber" id="releaseNumber" type="hidden" value="${materialRequestData.lineItemColl[0].releaseNumber}">
<input name="poNumber" id="poNumber" type="hidden" value="${materialRequestData.lineItemColl[0].poNumber}">
<input name="chargeNumbers" id="chargeNumbers" type="hidden" value="">	
<input name="currencyId" id="currencyId" type="hidden" value="${materialRequestData.lineItemColl[0].currencyId}">
<input name="currentChargeType" id="currentChargeType" type="hidden" value="${materialRequestData.lineItemColl[0].chargeType}">
<input name="shipToLocationId" id="shipToLocationId" type="hidden" value="${materialRequestData.lineItemColl[0].shipToLocationId}">
<input name="deliveryPoint" id="deliveryPoint" type="hidden" value="${materialRequestData.lineItemColl[0].deliveryPoint}"> 
<input name="directedChargeForDirect" id="directedChargeForDirect" type="hidden" value="${materialRequestData.lineItemColl[0].directedChargeForDirect}">
<input name="directedChargeForIndirect" id="directedChargeForIndirect" type="hidden" value="${materialRequestData.lineItemColl[0].directedChargeForIndirect}">
<input name="chargeNumbersFromDirectedChargeD" id="chargeNumbersFromDirectedChargeD" type="hidden" value="${materialRequestData.lineItemColl[0].chargeNumbersFromDirectedChargeD}">
<input name="chargeNumbersFromDirectedChargeI" id="chargeNumbersFromDirectedChargeI" type="hidden" value="${materialRequestData.lineItemColl[0].chargeNumbersFromDirectedChargeI}">	
<input name="notes" id="notes" type="hidden" value="${materialRequestData.lineItemColl[0].notes}">
<input type="hidden" name="approvedWithRestriction" id="approvedWithRestriction" value='N'/>
<input name="canEditLineChargeData" id="canEditLineChargeData" type="hidden" value="${materialRequestData.lineItemColl[0].canEditLineChargeData}">
</div>
<!-- Hidden elements end -->
</tcmis:form>
</div>
<!-- close of contentArea -->

</div> <!-- close of interface -->

<!-- You can build your error messages below.
     Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;"></div>

<span id="notesAreaBody" style="display:none;">
	<table  border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td align="center"  width="100%">
				<textarea cols="70" rows="5" class="inputBox" name="mrNotes" id="mrNotes">${materialRequestData.lineItemColl[0].notes}</textarea>
			</td>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="center"  width="100%">
				<input name="continue1"  id="continue1"  type="button" value="<fmt:message key="label.ok"/>" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onClick="closeNoteWin();"/>
			</td>
		</tr>
	</table>


</span>

</body>
</html:html>