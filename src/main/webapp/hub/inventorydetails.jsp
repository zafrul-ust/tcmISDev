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
<script type="text/javascript" src="/js/hub/inventorydetails.js"></script>
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

<title>
 <fmt:message key="label.inventory"/> <fmt:message key="label.detail"/>  <fmt:message key="label.foritem"/>: <c:out value="${param.itemId}"/>
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
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
itemInteger:"<fmt:message key="error.item.integer"/>"};

<c:set var="inventorygroup"><fmt:message key="label.inventorygroup"/></c:set>
<c:set var="expirationdate"><fmt:message key="label.expirationdate"/></c:set> 
var config = [
{ columnId:"inventoryGroupName",
  columnName:'<tcmis:jsReplace value="${inventorygroup}"/>'
},
{ columnId:"receiptId",
  width:5,
  columnName:'<fmt:message key="label.receiptid"/>'
},
{ columnId:"originalReceiptId",
  width:6,
  columnName:'<fmt:message key="label.originalreceiptid"/>'
},
{ columnId:"ownerCompanyId",
  width:6,
  columnName:'<fmt:message key="label.owner"/>'
},
{ columnId:"quantity",
  width:6,
  columnName:'<fmt:message key="label.qtyonhand"/>',
  sorting:"int"  
},
{ columnId:"quantityReceived",
  columnName:'<fmt:message key="label.recvqty"/>',
  sorting:"int" 
},
{ columnId:"lotStatus",
  width:6,
  columnName:'<fmt:message key="label.status"/>'
},
{ columnId:"mfgLot",
  width:5,
  columnName:'<fmt:message key="label.mfglot"/>'
},
{ columnId:"expireDate1",
  columnName:'<tcmis:jsReplace value="${expirationdate}"/>',
  sorting:"int",
  width:7,
  hiddenSortingColumn:"hExpireDate1"
},
{ columnId:"hExpireDate1",
  sorting:"int"
},
{
  columnId:"bin",
  width:6,
  columnName:'<fmt:message key="label.bin"/>'
},
{
  columnId:"qualityTrackingNumber",
  width:6,
  columnName:'<fmt:message key="label.qualitynote"/>'
}
];


// -->
</script>
</head>
<body bgcolor="#ffffff" onload="resultOnLoad();" onresize="resizeFrames()"> 

<tcmis:form action="/inventorydetails.do" onsubmit="return submitFrameOnlyOnce();">

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
   <c:choose>
    <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISErrors }">
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
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
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
      <div id="mainUpdateLinks" style="display:none"> <%-- mainUpdateLinks Begins --%>
       <span id="updateResultLink" style="display:none"><a href="#" onclick="update();"><fmt:message key="label.update"/></a></span>         
     </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

<div class="dataContent">
 <!--  result page section start -->
<div id="logisticsViewBean" style="height:400px;" style="display: none;"></div>

<c:if test="${logisticsViewBeanCollection != null}">
<script type="text/javascript">
<!--
<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty logisticsViewBeanCollection}">
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="logisticsViewBeanCollection" items="${logisticsViewBeanCollection}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>
<c:set var="expireDate1Sortable" value="${status.current.expireDate1.time}"/>

<fmt:formatDate var="expireDate" value="${status.current.expireDate1}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="expireYear" value="${status.current.expireDate1}" pattern="yyyy"/>
<c:if test="${expireYear == '3000'}">
	<c:set var="expireDate"><fmt:message key="label.indefinite"/></c:set>
	<c:set var="expireDate1Sortable" value=''/>
</c:if>


<c:set var="ownerCompanyId" value='${status.current.ownerCompanyId}'/>
<c:if test="${status.current.ownerCompanyId == 'NULL' or  status.current.ownerCompanyId == 'Null'}">
<c:set var="ownerCompanyId" value=''/>
</c:if>




 /*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},
 data:['${status.current.inventoryGroupName}','${status.current.receiptId}',
 '${status.current.originalReceiptId}',       
 '${ownerCompanyId}','${status.current.quantity}',
 '${status.current.quantityReceived}','${status.current.lotStatus}',
 '${status.current.mfgLot}','${expireDate}',
 '${expireDate1Sortable}','${status.current.bin}','${status.current.qualityTrackingNumber}']}
 <c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
]};
</c:if>
//-->
</script>

<!-- If the collection is empty say no data found -->

<c:if test="${empty logisticsViewBeanCollection}">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td>
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
<!--This sets the start time for time elapesed.-->
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}">
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}">

</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>

</body>
</html:html>