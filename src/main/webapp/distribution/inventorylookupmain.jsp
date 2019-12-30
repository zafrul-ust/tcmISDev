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
<%@ include file="/common/opshubig.jsp" %>

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/js/common/opshubig.js"></script>
<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/distribution/inventorylookupmain.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<title>
    <fmt:message key="label.inventory"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",all:"<fmt:message key="label.all"/>",
showlegend:"<fmt:message key="label.showlegend"/>",errors:"<fmt:message key="label.errors"/>",  
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
pleaseselect:"<fmt:message key="label.pleaseselect"/>",
operatingentity:"<fmt:message key="label.operatingentity"/>",
mustbeaninteger:"<fmt:message key="errors.integer"/>",
returnreceiptid:"<fmt:message key="label.returnreceiptid"/>",
itemInteger:"<fmt:message key="error.item.integer"/>"};

<c:set var="myinventorygroups">
 <fmt:message key="label.myinventorygroups"/>
</c:set>
var defaults = {
   	   id:'',name:'<tcmis:jsReplace value="${myinventorygroups}"/>'
}

    <c:forEach var="nouse0" items="${personnelBean.opsHubIgOvBeanCollection}" varStatus="status">
    	<c:if test="${status.current.opsEntityId == param.opsEntityId }">
          <c:set var="operatingEntityName" value='${status.current.operatingEntityName}'/>
          <c:forEach var="nouse1" items="${status.current.hubIgCollection}" varStatus="status1">
    	     	<c:if test="${ status1.current.hub == param.hub }">
					<c:set var="hubName" value='${status1.current.hubName}'/>
        	 	    var inventoryArray = [
        	 	    	<c:forEach var="nouse2" items="${status1.current.inventoryGroupCollection}" varStatus="status2">
         				   <c:if test="${ status2.index !=0 }">,</c:if>
	    	    	 	   {
	    	    			id:'${status2.current.inventoryGroup}',
	    	    			name:'<tcmis:jsReplace value="${status2.current.inventoryGroupName}"/>'
	    	    		   }
         		   		</c:forEach>
        	 	    ]; 
        		 </c:if>
          </c:forEach>
        </c:if>
    </c:forEach>

// -->
</script>
</head>
<%-- displayOnly needs to match the pageData in application.jsp if the page will be on the menu --%>
<body bgcolor="#ffffff" onload="loadLayoutWin('','inventoryLookup');buildDropDown();" onresize="resizeFrames()">

<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<%--NEW - removed the search frame and copied the search section here--%>
<div class="contentArea">
<tcmis:form action="/inventorylookupresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
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
    <%--  <table width="700" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch"> --%>
    <table width="600" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
    <%-- Row 1 --%>
    <tr>
      <td nowrap  width="8%" class="optionTitleBoldRight" nowrap><fmt:message key="label.operatingentity"/>:</td>
      <td width="6%" class="optionTitleLeft">
         ${operatingEntityName}
      </td>
      <td  class="optionTitleBoldLeft" nowrap><fmt:message key="label.search"/>:&nbsp;
          <select name="searchField"  class="selectBox" id="searchField">
            <option value="bin"><fmt:message key="label.bin"/></option>
			<option value="catPartNo"><fmt:message key="label.partnumber"/></option>
			<option value="itemDesc"><fmt:message key="label.itemdesc"/></option>
			<option value="itemId"><fmt:message key="label.itemid"/></option>
			<option value="lotStatus"><fmt:message key="label.lotstatus"/></option>
			<option value="mfgLot"><fmt:message key="label.mfglot"/></option>
			<option value="originalReceiptId"><fmt:message key="label.originalreceiptid"/></option>
			<option value="ownerCompanyId"><fmt:message key="label.ownercompanyid"/></option>
			<option value="poNumber"><fmt:message key="label.customerpo"/></option>
			<option value="radianPo"><fmt:message key="label.po"/></option>
			<option selected value="receiptId"><fmt:message key="label.receiptid"/></option>                                                                                                                                                     
          </select>
          &nbsp;
          <select name="searchMode" class="selectBox" id="searchMode" >
               <option value="is"> <fmt:message key="label.is"/>  </option>
               <option value="contains"> <fmt:message key="label.contains"/>  </option>
               <option value="startsWith"><fmt:message key="label.startswith"/> </option>
               <option value="endsWith"><fmt:message key="label.endswith" /></option>
          </select>
          &nbsp;                                                            
          <input name="searchArgument" id="searchArgument" type="text" class="inputBox" size="15"/>
       </td>        
    </tr>
    <%-- Row 2 --%>
    <tr>
      <td nowrap width="6%"  class="optionTitleBoldRight"><fmt:message key="label.hub"/>:</td>
      <td  width="10%" class="optionTitleLeft">
         ${hubName}
      </td>
      <td  class="optionTitleBoldLeft" nowrap><fmt:message key="label.search"/>:&nbsp;
          <select name="searchField2"  class="selectBox" id="searchField2">
            <option value="bin"><fmt:message key="label.bin"/></option>
			<option value="catPartNo"><fmt:message key="label.partnumber"/></option>
			<option value="itemDesc"><fmt:message key="label.itemdesc"/></option>
			<option value="itemId"><fmt:message key="label.itemid"/></option>
			<option value="lotStatus"><fmt:message key="label.lotstatus"/></option>
			<option value="mfgLot"><fmt:message key="label.mfglot"/></option>
			<option value="originalReceiptId"><fmt:message key="label.originalreceiptid"/></option>
			<option value="ownerCompanyId"><fmt:message key="label.ownercompanyid"/></option>
			<option value="poNumber"><fmt:message key="label.customerpo"/></option>
			<option value="radianPo"><fmt:message key="label.po"/></option>
			<option selected value="receiptId"><fmt:message key="label.receiptid"/></option>                                                                                                                                                     
          </select>
          &nbsp;
          <select name="searchMode2" class="selectBox" id="searchMode2" >
               <option value="is"> <fmt:message key="label.is"/>  </option>
               <option value="contains"> <fmt:message key="label.contains"/>  </option>
               <option value="startsWith"><fmt:message key="label.startswith"/> </option>
               <option value="endsWith"><fmt:message key="label.endswith" /></option>
          </select>
          &nbsp;                                                            
          <input name="searchArgument2" id="searchArgument2" type="text" class="inputBox" size="15"/>
       </td>        	
<%--      <td width="2%" class="optionTitleBoldLeft" nowrap><fmt:message key="label.expwithin"/>:</td>
      <td width="2%" class="optionTitleBoldLeft">
       	 <input name="noDaysOld" id="noDaysOld" type="text" class="inputBox" size="5"/>  
      </td>     --%>     
    </tr>
    <%-- Row 3 --%>
    <tr>
      <td nowrap  width="8%" class="optionTitleBoldRight"><fmt:message key="label.inventorygroup"/>:</td>
      <td width="14%" class="optionTitleBoldLeft">
       <select name="inventoryGroup" id="inventoryGroup" class="selectBox">
       </select>
      </td>	  
      <td class="optionTitleBoldLeft"  nowrap="nowrap">
			<input type="checkbox" name="showIssuedReceipts" id="showIssuedReceipts" value="Yes" class="radioBtns" /> <fmt:message key="label.includehistory"/>
	  </td>           
    </tr>
    
    <tr>
      <td colspan="2">&nbsp;</td>
      <td nowrap class="optionTitleBoldLeft" >
      	 <select name="searchField3" class="selectBox" id="searchField3" >
               <option value="dateOfReceipt"> <fmt:message key="receivedreceipts.label.dor"/>  </option>
               <option value="expireDate"> <fmt:message key="label.expiredate"/>  </option>
         </select>
		 <fmt:message key="label.from"/>
		 <input class="inputBox pointer" readonly type="text" name="dorfrom" id="dorfrom" value="<tcmis:getDateTag numberOfDaysFromToday="-365" datePattern="${dateFormatPattern}"/>" 
		 	onClick="return getCalendar(document.genericForm.dorfrom,null,document.genericForm.dorto);" maxlength="10" size="9"/>
		 <fmt:message key="label.to"/>&nbsp;
		 <input class="inputBox pointer" readonly type="text" name="dorto" id="dorto" value="" onClick="return getCalendar(document.genericForm.dorto,document.genericForm.dorfrom);"
		        maxlength="10" size="9"/>
	  </td>   
    </tr>
    
    <%-- Row 5 --%>
    <tr>
     <td colspan="2"  class="optionTitleBoldLeft" nowrap>
          <input name="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "return submitSearchForm()"/>
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

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
  <input type="hidden" name="uAction" id="uAction" value=""/>
  <input type="hidden" name="opsEntityId" id="opsEntityId" value="${param.opsEntityId}"/>
  <input type="hidden" name="hub" id="hub" value="${param.hub}"/>
  <input type="hidden" name="defaultInventoryGroup" id="defaultInventoryGroup" value="${param.inventoryGroup}"/>
  <input type="hidden" name="fromReconciliation" id="fromReconciliation" value=""/>
  <input name="countId" id="countId" type="hidden" value="${param.countId}" />
  <input name="callAddReceipt" id="callAddReceipt" type="hidden" value="${param.fromReconciliation}" />
  <input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
 <!-- needed if this page will show on application.do -->
 <!-- To get the correct height value to insert, insert showSearchHeight = true; anywhere in the JavaScript array section in main.jsp.  -->
  <input name="searchHeight" id="searchHeight" type="hidden" value="214"/>
</div>
<!-- Hidden elements end -->

<!-- Error Messages Ends -->
</tcmis:form>
</div> <!-- close of contentArea -->

</div>
<!-- Search Frame Ends -->

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
<!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">    
    <div class="boxhead"> 
    <%-- boxhead Begins --%>
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
          Please keep the <span></span> on the same line this will avoid extra virtical space.
      --%>     
		<%-- Use this case if you only have one update link to minimize extra line.  Notice this uses "div" instead of "span" --%>
	  <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
	    <span id="selectedRow"><a href="#" onclick='resultFrame.returnReceipt(); return false;'><fmt:message key="label.returnreceipts"/></a></span> |
		<span id="showlegendLink"><a href="javascript:window.close();"><fmt:message key="label.close"/></a></span>
      </div> <%-- mainUpdateLinks Ends --%>
     <%-- END OF OR --%>
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

<%-- show legend Begins --%>
<div id="showLegendArea" style="display: none;overflow: auto;">
  <table width=100% class="tableResults" border="0" cellpadding="0" cellspacing="0">
    <tr><td width="100px" class="yellow legendText">&nbsp;</td><td class="legendText"><fmt:message key="label.pendingccauth"/></td></tr>
    <tr><td width="100px" class="red legendText">&nbsp;</td><td class="legendText"><fmt:message key="label.qualityhold"/></td></tr>
  </table>
</div>
<%-- show legend Ends --%>
</body>
</html:html>