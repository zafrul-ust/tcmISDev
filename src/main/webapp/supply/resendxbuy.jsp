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
<script type="text/javascript" src="/js/supply/resendxbuy.js"></script>
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
<fmt:message key="resendxbuy.title"/>
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
itemInteger:"<fmt:message key="error.item.integer"/>",
haaspoline:"<fmt:message key="label.haaspoline"/>",
partnumber:"<fmt:message key="label.partnumber"/>",
itemdescription:"<fmt:message key="label.itemdescription"/>",
itemid:"<fmt:message key="label.itemid"/>",
needdate:"<fmt:message key="label.needdate"/>",
qty:"<fmt:message key="label.quantity"/>",
ok:"<fmt:message key="label.ok"/>",
errors:"<fmt:message key="label.errors"/>",      
accountnumber:"<fmt:message key="label.accountnumber"/>",
shipfrom:"<fmt:message key="label.shipfrom"/>",
laastupdateddate:"<fmt:message key="label.laastUpdatedDate"/>",
shipto:"<fmt:message key="label.shipto"/>",  
lastupdatedby:"<fmt:message key="label.lastUpdatedBy"/>",
salesorderno:"<fmt:message key="label.salesorderno"/>"
};

with(milonic=new menuname("confirm")){
    top="offset=2"
    style = contextWideStyle;
    margin=3
    aI("text=<fmt:message key="label.confirm"/>;url=javascript:confirm();");
}

drawMenus();

function confirm() {
	    /*Set any variables you want to send to the server*/
		document.genericForm.target='';
		document.getElementById('action').value = 'confirm';
		showPleaseWait();
	    mygrid.parentFormOnSubmit(); //prepare grid for data sending	 
	    document.genericForm.submit(); 
}

// -->
</script>
</head>

<body bgcolor="#ffffff"  onload="resultOnLoad();" onresize="resizeFrames()">

<tcmis:form action="/resendxbuy.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">
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
       <span id="updateResultLink"><a href="#" onclick="update();"><fmt:message key="label.update"/></a></span>         
     </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

<div class="dataContent">
 <!--  result page section start -->
<div id="resendxBuyViewBean" style="height:400px;" style="display: none;"></div>
<c:if test="${resendxBuyViewBeanCollection != null}" >

<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty resendxBuyViewBeanCollection}" >
<script type="text/javascript">
<!--
var shipFromLocationId= new Array();
<c:forEach var="resendxBuy" items="${resendxBuyViewBeanCollection}" varStatus="status">
	shipFromLocationId[${status.index +1}] = new Array(
	{text:'<fmt:message key="label.pleaseselect"/>',value:''}
	<c:forEach var="SupplierLocationOvBean" items="${supplierLocationOvBeanCollection}" varStatus="status">
	 <c:if test="${SupplierLocationOvBean.supplier eq resendxBuy.supplier }">
		<c:forEach var="LocationObjBean" items="${SupplierLocationOvBean.supplierLocations}" varStatus="childstatus"> 
		 ,{text:'${childstatus.current.locationShortName}',value:'${childstatus.current.locationId}'}
	 	</c:forEach>  	
	</c:if>
	</c:forEach>
	); 
</c:forEach>		

var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="resendxBuy" items="${resendxBuyViewBeanCollection}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>

<fmt:formatDate var="fmtNeedDate" value="${resendxBuy.needDate}" pattern="${dateFormatPattern}"/>
<tcmis:jsReplace var="itemDesc" value="${resendxBuy.itemDesc}" processMultiLines="true" />  
<tcmis:jsReplace var="shipTo" value="${resendxBuy.shipToAddress}" processMultiLines="true" />

<c:set var="needDateSortable" value="${resendxBuy.needDate.time}"/>
<c:set var="supplierAccountNumber" value="${status.current.supplierAccountNumber}"/>

<c:set var="readonly" value='N'/> 
<tcmis:inventoryGroupPermission indicator="true" userGroupId="BuyOrder" inventoryGroup="${status.current.inventoryGroup}">
 <c:set var="readonly" value='Y'/>
<c:set var="showUpdateLink" value='Y'/>
</tcmis:inventoryGroupPermission>

<tcmis:inventoryGroupPermission indicator="true" userGroupId="BuyOrder" inventoryGroup="${status.current.inventoryGroup}">
<c:set var="hasConfirmPerm" value='Y'/>
</tcmis:inventoryGroupPermission>


/*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},	
 data:[
  '${readonly}',     
  '${resendxBuy.radianPo}-${resendxBuy.poLine}', '${resendxBuy.itemId}',
  '${resendxBuy.supplierPartNo}','${itemDesc}', '${shipTo}',
  '${fmtNeedDate}','${resendxBuy.quantity}','',  '${resendxBuy.shipFromLocationId}',
  '${supplierAccountNumber}',
  '${needDateSortable}','${resendxBuy.radianPo}','${resendxBuy.poLine}',
  '${resendxBuy.supplierSalesOrderNo}'
  ]}  
 <c:set var="dataCount" value='${dataCount+1}'/> 
 </c:forEach> 
]};
//-->  
</script>
</c:if>

<!-- If the collection is empty say no data found -->
   <c:if test="${empty resendxBuyViewBeanCollection}" >
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
<input name="radianPo" id="radianPo" value="" type="hidden"/>
<input name="airgasCu" id="airgasCu" value="" type="hidden"/>
<!--This sets the start time for time elapesed.-->
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}">
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}">
<input name="minHeight" id="minHeight" type="hidden" value="100">

</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

    <script type="text/javascript">
        <!--

<c:if test="${showUpdateLink == 'Y'}">
        showUpdateLinks = true;
</c:if>
	var hasConfirm = false;
<c:if test="${hasConfirmPerm == 'Y'}">
	hasConfirm = true;
</c:if>

	//-->
    </script>

</tcmis:form>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>

</body>
</html:html>