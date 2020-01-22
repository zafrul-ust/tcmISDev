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

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/js/common/opshubig.js"></script>
<script type="text/javascript" src="/js/distribution/printinvoicemain.js"></script>

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

<title>
<fmt:message key="printinvoice.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",all:"<fmt:message key="label.all"/>",
showlegend:"<fmt:message key="label.showlegend"/>",errors:"<fmt:message key="label.errors"/>",
waitingforinputfrom:"<fmt:message key="label.waitingforinputfrom"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>", 
pickcustomerid:"<fmt:message key="label.pickcustomerid"/>", 
shiptoaddress:"<fmt:message key="label.shiptoaddress"/>"};

j$().ready(function() {
	function log(event, data, formatted) {
		j$('#customerId').val(formatted.split(":")[0]);
		$("customerName").className = "inputBox"; 
	} 
	
	j$("#customerName").autocomplete("findcustomer.do",{
			width: 350,
			max: 100,  // default value is 10
			cacheLength:0, // disable cache here because we put "rownum < max" for better performance. Cache will make data off.
			scrollHeight: 200,
			formatItem: function(data, i, n, value) {
				return  value.split(":")[0]+" "+value.split(":")[1].substring(0,32);
			},
			formatResult: function(data, value) {
				return value.split(":")[1];
			}
	});
	
	j$('#customerName').bind('keyup',(function(e) {
		  var keyCode = (e.keyCode ? e.keyCode : e.which);

		  if( keyCode != 13 && keyCode != 9) // 13 is for Enter; 9 is for Tab
		  	invalidateCustomer();
	}));
	
	j$('#customerName').bind('blur',(function() {
		var customerName  =  document.getElementById("customerName");
		if (customerName.value.length == 0) {
			clearCustomerId();
		}
		
	}));
	
	j$("#customerName").result(log).next().click(function() {
		j$(this).prev().search();
	});
}); 

function invalidateCustomer()
{
 var customerName  =  document.getElementById("customerName");
 
 if (customerName.value.length == 0) {
   customerName.className = "inputBox";
 }else {
   customerName.className = "inputBox red";
 }
 clearCustomerId();
}

function clearCustomerId() {
 var customerId  =  document.getElementById("customerId");
 //set to empty
 customerId.value = "";
}
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="loadLayoutWin('','invoicePrint');setOps();" onresize="resizeFrames()" onunload="closeAllchildren();">

<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<%--NEW - removed the search frame and copied the search section here--%>
<div class="contentArea">
<tcmis:form action="/printinvoiceresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
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
    <table width="750" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
    <tr>
      <td width="20%" class="optionTitleBoldRight" nowrap><fmt:message key="label.operatingentity"/>:</td>
      <td width="30%">
         <select name="opsEntityId" id="opsEntityId" class="selectBox">
         </select>
      </td>
<td width="40%" class="optionTitleBoldLeft" colspan="4" nowrap="nowrap">
<fmt:message key="label.shipconfirmdate"/>:
<fmt:message key="label.from"/>
<input class="inputBox pointer" readonly type="text" name="shipConfirmDate" id="shipConfirmDate" value="<tcmis:getDateTag numberOfDaysFromToday="-30" datePattern="${dateFormatPattern}"/>" onClick="return getCalendar(document.genericForm.shipConfirmDate,null,null,null,document.genericForm.deliveredDate);" size="10"/>
<fmt:message key="label.to"/>
<input class="inputBox pointer" readonly type="text" name="deliveredDate" id="deliveredDate" value="<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>" onClick="return getCalendar(document.genericForm.deliveredDate,null,null,document.genericForm.shipConfirmDate);" size="10"/>
</td>
    </tr>
    <tr>
      <td width="10%" class="optionTitleBoldRight"><fmt:message key="label.hub"/>:</td>
      <td width="10%">
         <select name="hub" id="hub" class="selectBox">
         </select>
      </td>
      <td class="optionTitleBoldRight"> <fmt:message key="label.customer"/>:</td>
      <td class="optionTitleLeft" nowrap="nowrap">
      	<input class="inputBox" type="text" name="customerName" id="customerName" value="" size="30" />
      	<input name="customerId" id="customerId" type="hidden" value=""/>
      	<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="selectedCustomer" value="..." align="right" onclick="lookupCustomer()"/>
		<!-- <input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="None" value="<fmt:message key="label.clear"/>" onclick="clearCustomer()"/> -->
      </td>
    </tr>
    <tr>
      <td width="10%" class="optionTitleBoldRight" nowrap="nowrap"><fmt:message key="label.inventorygroup"/>:&nbsp;</td>
      <td width="10%">
       <select name="inventoryGroup" id="inventoryGroup" class="selectBox">
       </select>
      </td>
      <td class="optionTitleBoldRight" nowrap><fmt:message key="label.shipto"/>:</td>
      <td class="optionTitleLeft" nowrap="nowrap">
		<span id="shipToBtnDisplay" style="display:;">
	      	<input class="invGreyInputText" type="text" name="shipToName" id="shipToName" value="" size="50" readonly="readonly"/>
 	     	<input name="shipToLocationId" id="shipToLocationId" type="hidden" value=""/>
			<input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="getShipTo" value="..." align="right" onClick="changeShipTo();"/>
			<!-- <input type="button" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" name="None" value="<fmt:message key="label.clear"/>" onclick="clearShipTo()"/> -->  
      </span>
		
      </td>
<%-- 
		   <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.search"/>:</td>
            <td colspan="2" class="optionTitleBoldLeft" nowrap="nowrap">
                <select name="searchField"  class="selectBox" id="searchField" >
                    <option value="prNumber"> <fmt:message key="label.mr"/>  </option>
                </select>
                &nbsp;
                <select name="searchMode" class="selectBox" id="searchMode" >
                
                       <option value="is"> <fmt:message key="label.is"/>  </option>
                 <!--
                       <option value="like"> <fmt:message key="label.contains"/>  </option>
                       <option value="startsWith"><fmt:message key="label.startswith"/> </option>
                       <option value="endsWith"><fmt:message key="label.endswith" /></option>
                -->        
                </select>
             &nbsp;
                <input name="searchArgument" id="searchArgument" type="text" class="inputBox" size="15"/>
            </td>
--%>                 
    </tr>
    <tr>
	  <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.search"/>:</td>
	  <td width="10%">
    	<select name="documentType" id="documentType" class="selectBox">
  			<option value="shipmentId"><fmt:message key="label.shipment"/></option>
  			<option value="mr"><fmt:message key="label.mr"/></option>
            <option value="invoice"><fmt:message key="label.invoice"/></option>
            <option value="poNumber"><fmt:message key="label.customerpo"/></option>
       	</select>
    	<input class="inputBox" type="text" name="idField" id="idField" value="" size="13" maxlength="12">
      </td>
      <td width="10%" class="optionTitleBoldRight" nowrap="nowrap"><fmt:message key="label.currency"/>:
	  </td>
      <td width="10%">
		<select name="currencyId" id="currencyId" class="selectBox">
	 	   	<option value=""><fmt:message key="label.all"/></option>
    	<c:forEach var="cbean" items="${vvCurrencyBeanCollection}" varStatus="status">
	 	   	<option value="${cbean.currencyId}" <c:if test="${currencyId eq cbean.currencyId}">selected</c:if>>${cbean.currencyId}</option>
    	</c:forEach>
    	</select>
	  </td>
    </tr>
    <tr>
      <td width="10%" colspan="4" class="optionTitleBoldCenter" nowrap="nowrap">
      	<input name="showNotReprintedOnly" id="showNotReprintedOnly" type="checkbox" class="radioBtns" value="Y" checked><fmt:message key="label.shownotreprintedonly"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    	<input name="showNeverPrinted" id="showNeverPrinted" type="checkbox" class="radioBtns" value="Y"><fmt:message key="label.showneverprinted"/>
    	<input name="showEInvoices" id="showEInvoices" type="checkbox" class="radioBtns" value="Y" onClick="uncheckOtherCheckBox(this.id,this.checked);"><fmt:message key="label.showeinvoices"/>
    	<input name="showEInvoicesNotSent" id="showEInvoicesNotSent" type="checkbox" class="radioBtns" value="Y" onClick="uncheckOtherCheckBox(this.id,this.checked);"><fmt:message key="label.einvoicesnotsent"/>
	  </td>
    </tr>
    <tr>
     <td colspan="4" width="100%" class="optionTitleBoldLeft" nowrap>
          <input name="submitSearch" type="button" class="inputBtns" value="<fmt:message key="label.search"/>" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "submitValidateSearchForm();">
          <input name="xslButton" type="button" class="inputBtns" value="<fmt:message key="label.createExcel"/>" id="xslButton" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "return createXSL()"/>
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
<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
<div class="spacerY">&nbsp;
<div id="searchErrorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
</c:if>

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input type="hidden" name="uAction" id="uAction" value="createXSL"/>
<input type="hidden" name="hubName" id="hubName" value=""/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
<input name="searchHeight" id="searchHeight" type="hidden" value="150">
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

<div id="resultGridDiv" style="display: none;">
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
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
      <div id="mainUpdateLinks" style="display: none"><%-- mainUpdateLinks Begins --%> 
			<span id="updateResultLink" style="display: none">
				<a href="#" onclick="resultFrame.printSelectedInvoices(); return false;"><fmt:message key="label.printselectedinvoices"/></a>&nbsp;
				<input name="reportType" id="active" type="radio" class="radioBtns" value="Active" checked>
        		<fmt:message key="label.active"/>
        		<input name="reportType" id="batch" type="radio" class="radioBtns" value="Batch" onclick="showName();">
        		<fmt:message key="label.batch"/>&nbsp;
        		<span id="printBatch" style="display: none">
        		<fmt:message key="label.name"/>&nbsp;
				<input class="inputBox" type="text" name="reportName" id="reportName" value="" size="12" >
			</span>&nbsp;
			<a href="#" onclick="resultFrame.regenEInvoice();"><fmt:message key="label.regenerateeinvoice"/></a>&nbsp;
		</span>
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
</div>  
</div><!-- Result Frame Ends -->

</div> <!-- close of interface -->

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>


	 <%-- freeze --%>
<div id="transitDailogWin" class="errorMessages" style="display:none;left:20%;top:20%;z-index:5;">
</div>
<div id="transitDailogWinBody" class="errorMessages" style="display: none;">
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


</body>
</html:html>