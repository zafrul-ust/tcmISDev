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
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
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
<script type="text/javascript" src="/js/distribution/quotationhistory.js"></script>
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>    

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

<c:if test="${!empty quoteHistoryViewBeanCollection}">
	<c:set var="regionName" value="${quoteHistoryViewBeanCollection[0].opsRegionName}"/>
    	<c:set var="inventoryGroup" value="${quoteHistoryViewBeanCollection[0].inventoryGroup}"/>
</c:if>

<title>
   ${param.curpath} &gt;
<c:choose>
   <c:when test="${!empty param.customerId}"> 
    <fmt:message key="showquotationallhistoryforcustomer.title"/> ${param.customerName}
    <c:set var="showCustomer" value='Y'/>
   </c:when>
   <c:when test="${param.searchKey eq 'INVENTORY GROUP'}"> 
    	<fmt:message key="label.showquotationhistoryig"/>: ${inventoryGroup}
    	<c:set var="showCustomer" value='N'/>
   </c:when>
   <c:when test="${param.searchKey eq 'REGION'}"> 
    	<fmt:message key="label.showquotationhistoryregion"/>: ${regionName}
    	<c:set var="showCustomer" value='N'/>
   </c:when>
   <c:when test="${param.searchKey eq 'GLOBAL'}"> 
    	<fmt:message key="label.showquotationhistoryglobal"/>
    	<c:set var="showCustomer" value='N'/>
   </c:when>
   <c:otherwise>
    <fmt:message key="showquotationallhistory.title"/>
    <c:set var="showCustomer" value='N'/>
   </c:otherwise>
</c:choose>
 - <fmt:message key="label.past1year"/>
</title>
  
<script language="JavaScript" type="text/javascript">
<!--

with(milonic=new menuname("openQuoteMenu")){
    top="offset=2"
    style = contextStyle;
    margin=3
       aI("text=<fmt:message key="label.open"/>;url=javascript:openQuoteTab();");
      
}

drawMenus();

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
itemInteger:"<fmt:message key="error.item.integer"/>",
scratchpad:"<fmt:message key="label.scratchPad"/>",
norow:"<fmt:message key="error.norowselected"/>",
lookup:"<fmt:message key="label.lookup"/>",
errors:"<fmt:message key="label.errors"/>"};

var windowCloseOnEsc = true;

var config = [
/*{ columnId:"itemId",
  columnName:'<fmt:message key="label.catalogitem"/>',
  width:16,
  tooltip:"Y"
},
{ columnId:"partDescription",
  columnName:'<fmt:message key="label.description"/>',
  width:16,
  tooltip:"Y"
},
*/
<c:if test="${showCustomer eq 'N'}" >
{ columnId:"customerName",
  columnName:'<fmt:message key="label.customer"/>',
  width:15,
  tooltip:"Y"
},
</c:if>
{ columnId:"catPartNo",
  columnName:'<fmt:message key="label.customerpart"/>'
},
<c:if test="${param.searchKey eq 'GLOBAL'}">
	{columnId:"region",
	columnName:'<fmt:message key="label.region"/>',
	tooltip:"Y",
	width:6},
</c:if>
<c:if test="${param.searchKey eq 'REGION' or param.searchKey eq 'GLOBAL'}"> 
	{columnId:"iinventoryGroup",
	columnName:'<fmt:message key="label.inventorygroup"/>',
	tooltip:"Y"},
</c:if>

{ columnId:"specList",
  columnName:'<fmt:message key="label.specs"/>',
  width:10,
  tooltip:"Y"
},
{ columnId:"prNumber",
  columnName:'<fmt:message key="label.quote"/>',
  width:6,
  sorting:'int',
  hiddenSortingColumn:"hprNumber"
},
{
 columnId:"hprNumber",
 sorting:'int'
},
{ columnId:"quantity",
  columnName:'<fmt:message key="label.qty"/>',
  align:"right",
  sorting:"int",
  width:4
},
{ columnId:"displayUnitCost",
  columnName:'<fmt:message key="label.quotedprice"/>',
  align:"right",
  hiddenSortingColumn:"expectedUnitCost",
  sorting:"int",
  width:6
},
{ columnId:"expectedUnitCost",
  sorting:"int"
},  
{ columnId:"submittedDate",
  columnName:'<fmt:message key="label.quotedate"/>',
  sorting:'int',
  width:7,
  hiddenSortingColumn:"hSubmittedDate"
},
{
 columnId:"hSubmittedDate",
 sorting:'int'
},
{ columnId:"requiredShelfLife",
  columnName:'<fmt:message key="label.requiredshelflife"/>',
  align:"right",
  width:5
},
{ columnId:"quoteExpireDate",
  columnName:'<fmt:message key="label.expiredate"/>',
  sorting:'int',
  hiddenSortingColumn:"hQuoteExpireDate"
},
{
 columnId:"hQuoteExpireDate",
 sorting:'int'
}
];
// -->
</script>
</head>

<body bgcolor="#ffffff"  onload="resultOnLoad();" onresize="resizeFrames()">

<tcmis:form action="/quotationhistory.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">
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
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">

<%--NEw -Transit Page Starts --%>
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br/><br/><br/><fmt:message key="label.pleasewait"/>
  <br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/>
</div>
<!-- Transit Page Ends -->

<div id="resultGridDiv" style="display: none;">
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
    <div class="boxhead"> <%-- boxhead Begins --%>
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
          Since this page is read-only I don't need to hide the mainUpdateLinks div, I am showing the link Close to all.
      --%>      
      <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
      <span id="updateResultLink" style="display: none">
      <fmt:message key="label.catalogitem"/>: <span id="spanid1"></span><br/>
      <fmt:message key="label.description"/>: <span id="spanid2"></span>
	  </span>
       &nbsp;
      </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

<div class="dataContent">
 <!--  result page section start -->
<div id="quoteHistoryViewBean" style="height:400px;" style="display: none;"></div>

<c:if test="${quoteHistoryViewBeanCollection != null}">
<script type="text/javascript">
<!--
<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty quoteHistoryViewBeanCollection}">
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="quoteHistoryViewBeanCollection" items="${quoteHistoryViewBeanCollection}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>

<c:if test="${status.index eq 0}">
<c:set var="alternateName" value="${bean.alternateName}"/>
<c:set var="partDescription" value="${bean.partDescription}"/>
</c:if>

<fmt:formatDate var="fmtSubmittedDate" value="${status.current.submittedDate}" pattern="${dateFormatPattern}"/>
<c:set var="submittedDateSortable" value="${status.current.submittedDate.time}"/>

<fmt:formatDate var="fmtQuoteExpireDate" value="${status.current.quoteExpireDate}" pattern="${dateFormatPattern}"/>
<c:set var="quoteExpireDateSortable" value="${status.current.quoteExpireDate.time}"/>

<tcmis:jsReplace var="partDescription" value="${status.current.partDescription}"  processMultiLines="true"/>
<tcmis:jsReplace var="customerName" value="${status.current.customerName}"  processMultiLines="true"/>

<tcmis:jsReplace var="alternateName" value="${status.current.alternateName}"  processMultiLines="true"/>
<fmt:formatNumber var="unitPriceFinal" value="${status.current.unitPrice}"  pattern="${totalcurrencyformat}"></fmt:formatNumber>

/*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},
 data:[//'${alternateName}','${partDescription}',
       <c:if test="${showCustomer eq 'N'}" >
       '${customerName}',
       </c:if>
 '<tcmis:jsReplace value="${status.current.catPartNo}" />',
 <c:if test="${param.searchKey eq 'GLOBAL'}"> 
	'<tcmis:jsReplace value="${status.current.opsRegionName}" />',
</c:if>
<c:if test="${param.searchKey eq 'REGION' or param.searchKey eq 'GLOBAL'}"> 
	'<tcmis:jsReplace value="${status.current.inventoryGroup}" />',
</c:if>
 '${status.current.specList}','${status.current.prNumber}-${status.current.lineItem}','${status.current.prNumber}',
 '${status.current.quantity}','${status.current.currencyId} ${unitPriceFinal}','${status.current.unitPrice}','${fmtSubmittedDate}','${submittedDateSortable}',
 <c:choose>
 <c:when test="${(status.current.requiredShelfLife eq null) || (empty status.current.requiredShelfLife)}">
 '${status.current.requiredShelfLife}',
 </c:when>
 <c:otherwise>
 '${status.current.requiredShelfLife}%',
 </c:otherwise>
</c:choose>
 
 '${fmtQuoteExpireDate}','${quoteExpireDateSortable}']}
 <c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
]};
</c:if>
//-->
</script>

<!-- If the collection is empty say no data found -->

<c:if test="${empty quoteHistoryViewBeanCollection}">
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

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
<input name="action" id="action" value="" type="hidden"/>
<input type="hidden" name="alternateName" id="alternateName" value="${alternateName}"/>  
<input type="hidden" name="partDescription" id="partDescription" value="${partDescription}"/>  

<!--This sets the start time for time elapesed.-->
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}"/>
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>
<input name="status" id="status" type="hidden" value="${param.status}"/>
<input name="minHeight" id="minHeight" type="hidden" value="100"/>

</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

<c:if test="${ dataCunt != 0 }"> 
    <script type="text/javascript">
        <!--
        showUpdateLinks = true;
        if( $v('totalLines') != 0 ) { 
	        $("mainUpdateLinks").style.display = "";//style="display:none"> <%-- mainUpdateLinks Begins --%>
	        $("updateResultLink").style.display = "";//<span id="updateResultLink" style="display:none">
	        $('spanid1').innerHTML = $('alternateName').value;
	        $('spanid2').innerHTML = $('partDescription').value;
        }
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