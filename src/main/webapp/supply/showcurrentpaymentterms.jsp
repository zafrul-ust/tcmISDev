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

<tcmis:gridFontSizeCss overflow="notHidden"/>
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
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
<script type="text/javascript" src="/js/supply/showcurrentpaymentterms.js"></script>
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>    
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
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
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
${param.supplierName}&nbsp;-&nbsp;<fmt:message key="label.currentpaymentterms"/>
</title>
  
<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages



var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>"};

var cptConfig = [
{ columnId:"operatingEntityName",
  columnName:'<fmt:message key="label.operatingentity"/>',
  width:16
},
{ columnId:"paymentTerms",
  columnName:'<fmt:message key="label.paymentterms"/>'
},
{ columnId:"approvedByName",
  columnName:'<fmt:message key="label.approvedby"/>',
  width:16
},
{ columnId:"approvedOn",
  columnName:'<fmt:message key="label.approvedon"/>',
  hiddenSortingColumn:"hCPTApprovedOn",
  sorting:"int"
},
{ columnId:"hCPTApprovedOn",
  sorting:"int"
},
{  columnId:"status",
   columnName:'<fmt:message key="label.status"/>'
}

];


var cptExpConfig = [
{ columnId:"operatingEntityName",
  columnName:'<fmt:message key="label.operatingentity"/>',
  width:16
},
{ columnId:"inventoryGroupName",
  columnName:'<fmt:message key="label.inventorygroup"/>'
},
{ columnId:"defaultPaymentTerms",
  columnName:'<fmt:message key="label.paymentterms"/>'
},
{ columnId:"approvedByName",
  columnName:'<fmt:message key="label.approvedby"/>',
  width:16
},
{ columnId:"approvedOn",
  columnName:'<fmt:message key="label.approvedon"/>',
  hiddenSortingColumn:"happrovedOn",
  sorting:"int"
},
{ columnId:"happrovedOn",
  sorting:"int"
}
];

// -->
</script>
</head>

<body bgcolor="#ffffff"  onload="resultOnLoad();"  onresize="resizeWin()">

<tcmis:form action="/showcurrentpaymentterms.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">
<div class="interface" id="mainPage" style="">

<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/>
    ${tcmISError}
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
    </c:forEach>        
</div>
<!-- Error Messages Ends -->

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISErrors}"> 
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>   
//-->
</script>
<!-- Result Frame Begins -->


<%--NEw -Transit Page Starts --%>
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
<div id="resultGridDiv" style="display: none;">

<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">

<table  width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
<tr id="showCPTDivRow" style="display: none;">

<td class="optionTitleBoldLeft"> <fmt:message key="label.paymentterms"/></td>
<td colspan="6"> &nbsp;</td>

<td class="optionTitleBoldRight"  onclick="showCPTDiv();">&nbsp;&nbsp;&nbsp;&nbsp;
<img src="/dhtmlxLayout/codebase/imgs/dhxlayout_dhx_blue/dhxlayout_ver1b.gif" />
</td>

</tr>
<tr id="hideCPTDivRow">
<td colspan="7"> &nbsp;</td>

<td class="optionTitleBoldRight"  onclick="hideCPTDiv();">&nbsp;&nbsp;&nbsp;&nbsp;
 <img src="/dhtmlxLayout/codebase/imgs/dhxlayout_dhx_blue/dhxlayout_ver1t.gif" />
</td>

</tr>
</table>

<div id="currentPaymentTermsDiv"> <%-- style="display: none;"> --%>
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
    <div class="boxhead"><fmt:message key="label.paymentterms"/></div> 
<div id="currentPaymentTermsViewBean"></div>

<c:if test="${currentPaymentTermsCollection != null}">
<script type="text/javascript">
<!--
<c:set var="paymentTermsDataCount" value='${0}'/>
<c:if test="${!empty currentPaymentTermsCollection}">
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="currentPaymentTermsCollection" items="${currentPaymentTermsCollection}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>
<fmt:formatDate var="fmtApprovedOn" value="${status.current.approvedOn}" pattern="${dateFormatPattern}"/>
<c:set var="approvedOnSortable" value="${status.current.approvedOn.time}"/>
 /*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},
 data:['${status.current.operatingEntityName}','${status.current.paymentTerms}','<tcmis:jsReplace value="${status.current.approvedByName}" />',
 '${fmtApprovedOn}','${approvedOnSortable}','${status.current.paymentTermStatus}']}
 <c:set var="paymentTermsDataCount" value='${paymentTermsDataCount+1}'/>
 </c:forEach>
]};
</c:if>
//-->
</script>

<!-- If the collection is empty say no data found -->

<c:if test="${empty currentPaymentTermsCollection}">
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
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
   <div id="paymentTermsFooter"></div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</td></tr>
</table>
</div> 
</div>

<div id="currentPaymentTermsExpFrameDiv" style="margin: 4px 4px 0px 4px;">

<table  width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
<tr id="showCPTExpDivRow" style="display: none;">

<td class="optionTitleBoldLeft"> <fmt:message key="label.paymenttermsexceptions"/></td>
<td colspan="6"> &nbsp;</td>

<td class="optionTitleBoldRight"  onclick="showCPTExpDiv();">&nbsp;&nbsp;&nbsp;&nbsp;
<img src="/dhtmlxLayout/codebase/imgs/dhxlayout_dhx_blue/dhxlayout_ver1b.gif" />
</td>

</tr>
<tr id="hideCPTExpDivRow">
<td colspan="7"> &nbsp;</td>

<td class="optionTitleBoldRight"  onclick="hideCPTExpDiv();">&nbsp;&nbsp;&nbsp;&nbsp;
 <img src="/dhtmlxLayout/codebase/imgs/dhxlayout_dhx_blue/dhxlayout_ver1t.gif" />
</td>

</tr>
</table>

<div id="currentPaymentTermsExceptionDiv" > <%-- style="display: none;"> --%>
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
    <div class="boxhead"><fmt:message key="label.paymenttermsexceptions"/></div> 
	<div id="paymentTermIgExceptionViewBean"></div>

<c:if test="${currentPaymentTermsExceptionsCollection != null}">
<script type="text/javascript">
<!--
<c:set var="paymentTermsExcepDataCount" value='${0}'/>
<c:if test="${!empty currentPaymentTermsExceptionsCollection}">
var jsonCPTExpMainData = new Array();
var jsonCPTExpMainData = {
rows:[
<c:forEach var="currentPaymentTermsExceptionsCollection" items="${currentPaymentTermsExceptionsCollection}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>
<fmt:formatDate var="fmtapprovedOn" value="${status.current.approvedOn}" pattern="${dateFormatPattern}"/>
<c:set var="approvedOnSortable" value="${status.current.approvedOn.time}"/>
 /*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},
 data:['${status.current.operatingEntityName}','${status.current.inventoryGroupName}','${status.current.defaultPaymentTerms}',
 '${status.current.approvedByName}','${fmtapprovedOn}','${approvedOnSortable}']}
 <c:set var="paymentTermsExcepDataCount" value='${paymentTermsExcepDataCount+1}'/>
 </c:forEach>
]};
</c:if>
//-->
</script>

<!-- If the collection is empty say no data found -->

<c:if test="${empty currentPaymentTermsExceptionsCollection}">
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
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
   <div id="paymentTermsExcepFooter"></div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</td></tr>
</table>
</div> 
 </div>
 <%-- result count and time --%>
 <div id="footer" class="messageBar"></div>

</div>
</div>
</td></tr>
</table>
</div>
</div><!-- Result Frame Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
<input name="action" id="action" value="" type="hidden"/>
<!--This sets the start time for time elapesed.-->
<input name="minHeight" id="minHeight" type="hidden" value="100"/>
<input name="paymentTermsTotalLines" id="paymentTermsTotalLines" value="${paymentTermsDataCount}" type="hidden"/>
<input name="paymentTermsExcepTotalLines" id="paymentTermsExcepTotalLines" value="${paymentTermsExcepDataCount}" type="hidden"/>

</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

<c:if test="${showUpdateLink == 'Y'}">
    <script type="text/javascript">
        <!--
        showUpdateLinks = true;
        //-->
    </script>
</c:if>
</tcmis:form>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>

</body>
</html:html>