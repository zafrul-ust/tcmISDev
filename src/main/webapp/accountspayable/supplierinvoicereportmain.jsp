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
<%@ include file="/common/opshubig.jsp" %>
  
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<%--NEW--%>  
<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
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

<!--  These are for the autocomplete function -->
<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script> 
<script type="text/javascript" src="/js/jquery/autocomplete.js"></script> 
<link rel="stylesheet" type="text/css" href="/css/autocomplete.css" /> 

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/opshubig.js"></script>
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/js/accountspayable/supplierinvoicereportmain.js"></script>
  

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>


<title>
<fmt:message key="supplierinvoicereport.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
defaults = {
	ops: {id:'',name:'<fmt:message key="label.pleaseselect"/>'},
	hub: {id:'',name:'<fmt:message key="label.all"/>'},
	inv: {id:'',name:'<fmt:message key="label.all"/>'}
}

j$().ready(function() {
	// For Buyers autocomplete
	function log(event, data, formatted) {
		if( typeof(data) == 'undefined') return false;
		j$('#buyerId').val(formatted.split(":")[0]);
		$("buyerName").className = "inputBox"; 
	} 
	
	j$("#buyerName").autocomplete("/tcmIS/supply/findbuyer.do", {
		width: 300,
		max: 200,
		matchContains:true,
		cacheLength:0,
		//matchContains: "word",
		autoFill: false,
		formatItem: function(data, i, n, value) {
			var values = value.split(":");
			return  values[0]+"  "+values[1];
		},
		formatResult: function(data, value) {
			return value.split(":")[1];
		}
	});
	
	j$('#buyerName').bind('keyup',(function(e) {
		  var keyCode = (e.keyCode ? e.keyCode : e.which);

		  if( keyCode != 13 && keyCode != 9) // 13 is for Enter; 9 is for Tab
		  	invalidatePersonnelList();
	}));
	
	j$("#buyerName").result(log).next().click(function() {
		j$(this).prev().search();
	});

	// For Supplier complete
	function supplierLog(event, data, formatted) {
		j$('#supplier').val(formatted.split(":")[0]);
		$("supplierName").className = "inputBox"; 
	} 

	j$("#supplierName").autocomplete("/tcmIS/distribution/findsupplier.do",{
		extraParams: {
			activeOnly: function() { return j$('#activeOnly').is(':checked'); },
			opsEntityId: function() { return j$("#opsEntityId").val(); } }, 
		width: 550,
		max: 100,  // default value is 10
		cacheLength:0, // disable cache here because we put "rownum < max" for better performance. Cache will make data off.
		scrollHeight: 200,
		formatItem: function(data, i, n, value) {
			var values = value.split(":");
			return  values[0]+"  "+values[1].substring(0,45)+" - "+values[3];
		},
		formatResult: function(data, value) {
			return value.split(":")[1];
		}
	});
	
	j$('#supplierName').bind('keyup',(function(e) {
		  var keyCode = (e.keyCode ? e.keyCode : e.which);
	
		  if( keyCode != 13 && keyCode != 9) // 13 is for Enter; 9 is for Tab
		  	invalidateSupplier();
	}));
	
	
	j$("#supplierName").result(supplierLog).next().click(function() {
		j$(this).prev().search();
	});
});

function invalidatePersonnelList()
{
 var buyerName  =  document.getElementById("buyerName");
 var buyerId  =  document.getElementById("buyerId");
 if (buyerName.value.length == 0) {
	 buyerName.className = "inputBox";
 }else {
	 buyerName.className = "inputBox red";
 }
 //set to empty
 buyerId.value = "";
}

function invalidateSupplier()
{
 var supplierName  =  document.getElementById("supplierName");
 var supplier  =  document.getElementById("supplier");
 if (supplierName.value.length == 0) {
   supplierName.className = "inputBox";
 }else {
   supplierName.className = "inputBox red";
 }
 //set to empty
 supplier.value = "";
}

var messagesData = new Array();
messagesData={
	alert:"<fmt:message key="label.alert"/>",
	and:"<fmt:message key="label.and"/>",
	all:"<fmt:message key="label.all"/>",
	errors:"<fmt:message key="label.errors"/>",  
	validvalues:"<fmt:message key="label.validvalues"/>",
	submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
	pleaseinput:"<fmt:message key="label.pleaseinput"/>",
	invoiceAge:"<fmt:message key="label.invoiceage"/>",
	operatingentity:"<fmt:message key="label.operatingentity"/>",
	supplier:"<fmt:message key="label.supplier"/>"
};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="loadLayoutWin('','supplierInvoiceReport');setOps();" onresize="resizeFrames()">

<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<!-- action=start is there to decide in the action to query the database for the drop down in the search section.
     This is being done to avoid quering the databsae for drop down when I don't need them
 -->
<%-- <iframe id="searchFrame" name="searchFrame" width="100%" height="150" frameborder="0" marginwidth="0" style="" src="transactionssearch.do"></iframe>--%>
<%--NEW - removed the search frame and copied the search section here--%>
<div class="contentArea">
<tcmis:form action="/supplierinvoicereportresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
    <table id="searchTable" width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
    <%--     Row 1    --%>
      <tr>
        <td class="optionTitleBoldRight" width="1%">
        	<fmt:message key="label.operatingentity"/>:
        </td>
        <td width="10%">
        	<select name="opsEntityId" id="opsEntityId" class="selectBox"></select>
        </td>
        <td class="optionTitleBoldRight" width="5%">
        	<fmt:message key="label.hub"/>:
        </td>
        <td width="10%">
            <select name="hub" id="hub" class="selectBox"></select>
        </td>
        <td class="optionTitleBoldRight" width="1%" nowrap>
        	<fmt:message key="label.inventorygroup"/>:
        </td>
        <td width="10%">
        	<select name="inventoryGroup" id="inventoryGroup" class="selectBox"></select>
        </td>
      </tr>
      <tr>
        <td class="optionTitleBoldRight" width="1%"><fmt:message key="label.invoice"/>:</td>
        <td width="10%"><input name="supplierInvoiceId" type="text" class="inputBox" id="supplierInvoiceId" size="25" value='${param.supplierInvoiceId}'></td>
        <td class="optionTitleBoldRight" width="5%"><fmt:message key="label.status"/>:</td>
        <td width="10%">
          <select name="status" class="selectBox" id="status">
             <option value="All"><fmt:message key="label.all"/></option>
           <c:forEach items="${voucherStatusColl}" var="vstatus">
            <c:set var="currentStatus" value='${vstatus.voucherStatus}'/>
            <c:choose>
             <c:when test="${'In Progress' == currentStatus}">
              <option value='${currentStatus}' selected>${currentStatus}</option>
             </c:when>
             <c:otherwise>
              <option value='${currentStatus}'>${currentStatus}</option>
             </c:otherwise>
            </c:choose>
           </c:forEach>
          </select>
        </td>
        
        <td colspan="2">
        	&nbsp;
        </td>
      </tr>
      <tr>
        <td class="optionTitleBoldRight" width="1%"><fmt:message key="label.supplier"/>:</td>
        <td width="10%" nowrap>
        	<input type="text" id="supplierName" name="supplierName" onclick="if(!validateOps()) return false;" value="" class="inputBox" size="35" /> 
 		 	<input name="supplier" id="supplier" type="hidden" value="">
 		 	<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="selectedsupplier" value="..." align="right" onClick="lookupSupplier()"/>
 		 	<input name="activeOnly" id="activeOnly" onclick="" type="checkbox" checked class="radioBtns" value="Y">
	        	<fmt:message key="label.activeOnly"/>
        </td>
        <td class="optionTitleBoldRight" width="5%" nowrap>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <fmt:message key="label.invoiceage"/>:</td>
        <td width="10%" class="optionTitleBoldLeft">
          <c:choose>
           <c:when test="${param.voucherAge != null}">
            <input name="voucherAge" class="inputBox" type="text" value='${param.voucherAge}' id="voucherAg" size="5">Days
           </c:when>
           <c:otherwise>
            <input name="voucherAge" class="inputBox" type="text" value="100" id="voucherAge" size="5">Days
           </c:otherwise>
          </c:choose>
        </td>
        <td width="20%" class="optionTitleBoldLeft" nowrap colspan="2">
          <c:choose>
           <c:when test='${param.showOnlyWithReceipts == "Yes"}'>
            <input name="showOnlyWithReceipts" type="checkbox" class="radioBtns" value="Yes" id="showOnlyWithReceipts" checked>
           </c:when>
           <c:otherwise>
            <input name="showOnlyWithReceipts" type="checkbox" class="radioBtns" value="Yes" id="showOnlyWithReceipts">
           </c:otherwise>
          </c:choose>
          &nbsp;<fmt:message key="supplierinvoicereport.showonlywithreceipts"/>
        </td>
      </tr>
      <tr>
        <td class="optionTitleBoldRight" width="1%"><fmt:message key="label.buyer"/>:</td>
        <td width="10%">
          <input type="text" id="buyerName" name="buyerName" value="" class="inputBox" size="50" /> 
 		  <input name="buyerId" id="buyerId" type="hidden" value="">
        </td>
        <td class="optionTitleBoldRight" width="5%" nowrap><fmt:message key="label.sortby"/>:</td>
        <td width="10%">
          <select id="sortBy" name="sortBy" class="selectBox">
           <option value="radianPo,supplierInvoiceId"><fmt:message key="supplierinvoicereport.sortby.poinvoice"/></option>
           <option value="supplierInvoiceId"><fmt:message key="label.invoice"/></option>
           <option value="supplierName,supplierInvoiceId"><fmt:message key="supplierinvoicereport.sortby.supplierinvoice"/></option>
           <option value="invoiceDate"><fmt:message key="label.invoicedate"/></option>
           <option value="approvedDate"><fmt:message key="label.approveddate"/></option>
          </select>
        </td>
        <td class="optionTitleBoldLeft" width="20%" colspan="2">
          <c:choose>
           <c:when test='${param.showOnlyToBeQced == "Yes"}'>
            <input type="checkbox" name="showOnlyToBeQced" value="Yes"  onclick="freezeInvoiceStatus()" id="showOnlyToBeQced" checked>
           </c:when>
           <c:otherwise>
            <input type="checkbox" name="showOnlyToBeQced" value="Yes"  onclick="freezeInvoiceStatus()" id="showOnlyToBeQced">
           </c:otherwise>
          </c:choose>
          &nbsp;<fmt:message key="supplierinvoicereport.showonlyinvoicesqced"/>
        </td>
      </tr>
      <tr>
        <td width="1%">&nbsp;</td>
        <td width="10%">&nbsp;</td>
        <td width="5%">&nbsp;</td>
        <td width="10%">&nbsp;</td>
        <td width="20%" class="optionTitleLeft" nowrap colspan="2">
          <div id="approvedDates" style="display: none;">
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <fmt:message key="label.approvedfrom"/>:&nbsp;
            <input type="text" readonly name="approvedDateBegin" id="approvedDateBegin" onclick="return getCalendar(document.genericForm.approvedDateBegin,null,document.genericForm.approvedDateEnd);" class="inputBox pointer" value='${param.approvedDateBegin}' maxlength="10" size="8">
            &nbsp;<fmt:message key="label.to"/>:&nbsp;
            <input type="text" readonly name="approvedDateEnd" id="approvedDateEnd" onclick="return getCalendar(document.genericForm.approvedDateEnd,document.genericForm.approvedDateBegin);" class="inputBox pointer" value='${param.approvedDateEnd}' maxlength="10" size="8">
          </div>
        </td>
      </tr>
      <tr>
      	<td class="optionTitleBoldLeft">
			<input name="submitSearch" id="submitSearch" type="submit" value="<fmt:message key="label.search"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="return submitSearchForm()"/>
	 	</td>
      	<td>
			<input name="submitCreateexcel" id="submitCreateexcel" type="submit" value="<fmt:message key="label.createexcel"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="return createXSL()"/>
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
<input type="hidden" name="userAction" id="userAction" value=""/>
<input type="hidden" name="branchPlant" id="branchPlant" value=""/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
<input name="maxData" id="maxData" type="hidden" value="1500"/>
</div>
<!-- Hidden elements end -->

</tcmis:form>
</div> <!-- close of contentArea -->

</div>
<!-- Search Frame Ends -->

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">

<%--NEw -Transit Page Starts --%>
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->

<div id="resultGridDiv" name="resultGridDiv" style="display: none;">
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
      --%>
      <div id="mainUpdateLinks" style="display: none"> 
        &nbsp;<span id="updateResultLink" style="display: none"></span>
      </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

    <div class="dataContent">
    <iframe scrolling="no" id="resultFrame" name="resultFrame" width="100%" height="300" frameborder="0" marginwidth="0" src="/blank.html"></iframe> 
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
</div><!-- Result Frame Ends -->

<!-- close of interface -->

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>
</body>
</html:html>