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
<script type="text/javascript" src="/js/calendar/date.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/js/hub/transactionsmain.js"></script>
<script type="text/javascript" src="/js/common/opshubig.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<title>
<fmt:message key="transactions.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",all:"<fmt:message key="label.all"/>",
showlegend:"<fmt:message key="label.showlegend"/>",errors:"<fmt:message key="label.errors"/>",  
validvalues:"<fmt:message key="label.validvalues"/>",
receiptid:"<fmt:message key="label.receiptid"/>",
mr:"<fmt:message key="label.mr"/>",
po:"<fmt:message key="label.po"/>",
days:"<fmt:message key="label.days"/>",
itemid:"<fmt:message key="label.itemid"/>",
issueid:"<fmt:message key="label.issueid"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>",
to:"<fmt:message key="label.to"/>"
};

defaults.ops.nodefault = true;
defaults.hub.nodefault = true;  
  
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="loadLayoutWin('','transactions');setOps();" onresize="resizeFrames()">

<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<%--NEW - removed the search frame and copied the search section here--%>
<div class="contentArea">
<tcmis:form action="/transactionsresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
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
    <table width="800" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
    <tr>
      <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.operatingentity"/>:</td>
      <td width="10%">
         <select name="opsEntityId" id="opsEntityId" class="selectBox"></select>
      </td>
      <td width="5%" class="optionTitleBoldRight" nowrap><fmt:message key="label.receiptid"/>:</td>
      <td width="10%" class="optionTitleLeftt">
         <input class="inputBox" type="text" name="receiptId" id="receiptId" value='<c:out value="${param.receiptId}"/>' size="15">
      </td>
      <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.mr"/>:</td>
      <td width="10%" class="optionTitleLeftt">
         <input class="inputBox" type="text" name="mrNumber" id="mrNumber" value='<c:out value="${param.mrNumber}"/>' size="15">
      </td>
      <td width="5%" class="optionTitleBoldRight" nowrap><fmt:message key="label.trackingnumber"/>:</td>
      <td width="10%" class="optionTitleBoldLeft">
         <input class="inputBox" type="text" name="trackingNumber" id="trackingNumber"/>
      </td>
    </tr>
    <tr>
      <td width="5%" class="optionTitleBoldRight" nowrap><fmt:message key="label.hub"/>:&nbsp;</td>
      <td width="10%">
         <select name="hub" id="hub" class="selectBox"></select>
      </td>
      <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.itemid"/>:</td>
      <td width="10%" class="optionTitleLeft">
         <input class="inputBox" type="text" name="itemId" id="itemId" value='<c:out value="${param.itemId}"/>' size="15">
      </td>
      <td width="5%" class="optionTitleBoldRight" nowrap><fmt:message key="label.mfglot"/>:</td>
      <td width="10%" class="optionTitleLeft">
         <input class="inputBox" type="text" name="mfgLot" id="mfgLot" value='<c:out value="${param.mfgLot}"/>' size="25">
      </td>
      <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.po"/>:</td>
      <td width="10%" class="optionTitleLeft">
         <input class="inputBox" type="text" name="radianPo" id="radianPo" value='<c:out value="${param.radianPo}"/>' size="15">
      </td>
    </tr>
    <tr>
      <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.inventorygroup"/>:&nbsp;</td>
	  <td width="10%" class="optionTitleBoldLeft" >
			<select name="inventoryGroup" id="inventoryGroup" class="selectBox"></select>
	  </td>
	  <td width="5%" class="optionTitleBoldRight"><fmt:message key="transactions.transactiondate"/>:</td>
	  
      <td width="10%" class="optionTitleBoldLeft" nowrap>
      <html:select property="transactionDate" styleClass="selectBox" styleId="transactionDate" onchange="return transDateChange(value);">
          <html:option value="within" key="label.within"/>
          <html:option value="onDate" key="transactions.ondate"/>
          <html:option value="fromDate" key="label.from"/>
     </html:select>
         <c:choose>
          <c:when test="${param.daysOld == null}" >
              <c:set var="numDaysOld" value="60"/>
          </c:when>
          <c:otherwise>
              <c:set var="numDaysOld" value="${param.daysOld}"/>
          </c:otherwise>
         </c:choose>
		 <span id="withinSpan" style="">
         <input class="inputBox"  type="text" name="daysOld" id="daysOld" value='<c:out value="${numDaysOld}"/>' size="5"/>
         <fmt:message key="label.days"/>
         </span>
         <span id="txnSpan" style="display:none">
      	 <input class="inputBox pointer"  readonly type="text" name="txnOnDate" id="txnOnDate" value="${param.txnOnDate}" onClick="return getCalendar(document.genericForm.txnOnDate);" size="10"/>
      	 </span>
      </td>
      <td class="optionTitleBoldRight"> <span id="fromSpan" style="display:none"><fmt:message key="label.from"/>:</span></td>   
      <td width="10%" class="optionTitleBoldRight" nowrap>
      	 <span id="toSpan" style="display:none">
         <input class="inputBox pointer"  type="text" name="transactionFromDate" id="transactionFromDate" value="" onClick="return getCalendar(document.genericForm.transactionFromDate,null,document.genericForm.transactionToDate);"
		        maxlength="10" size="9"/>
		 <fmt:message key="label.to"/>:
		 <input class="inputBox pointer"  type="text" name="transactionToDate" id="transactionToDate" value="" onClick="return getCalendar(document.genericForm.transactionToDate,document.genericForm.transactionFromDate);"
		        maxlength="10" size="9"/>
		 </span>     
	 </td>
           <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.issueid"/>:</td>
      <td width="10%" class="optionTitleBoldLeft">
         <input class="inputBox" type="text" name="issueId" id="issueId" value='<c:out value="${param.issueId}"/>' size="10"/>
      </td>

    </tr>
    <tr>
      <td width="5%" class="optionTitleBoldRight" nowrap><fmt:message key="transactions.transtype"/>:&nbsp;</td>
      <td width="10%">
        <html:select property="transType" styleClass="selectBox" styleId="transType" value="XLC">
          <html:option value="ALL" key="label.all"/>
          <html:option value="XLC" key="label.allexceptlc"/>
          <html:option value="OV" key="graphs.label.receipts"/>
          <html:option value="IT" key="label.transfers"/>
          <html:option value="RI" key="graphs.label.issues"/>
        </html:select>
      </td>  
       <td width="5%" class="optionTitleBoldRight" nowrap><fmt:message key="label.sortby"/>:&nbsp;</td>
      <td width="10%">
        <html:select property="sortBy" styleClass="selectBox" styleId="sortBy">
          <html:option value="dateOfReceipt" key="inventorydetail.label.dor"/>
          <html:option value="expireDate" key="transactions.expiredate"/>
          <html:option value="itemId" key="label.itemid"/>
          <html:option value="radianPo" key="label.po"/>
          <html:option value="receiptId" key="label.receiptid"/>
          <html:option value="transactionDate" key="transactions.transactiondate"/>
        </html:select>
      </td>
    </tr>
    <tr>
    </tr>
    <tr>
     <td colspan="8" class="optionTitleBoldLeft">
          <input name="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "return submitSearchForm()">
          <%--<input name="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/> JSON Multi Line" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "return submitSearchFormMulti()">--%>
          <input name="createXSL" type="button" class="inputBtns" value="<fmt:message key="label.createExcel"/>" id="createXSL" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "return validateSearchFormXSL()"/>
       <%--<input name="submitSearchXml" type="submit" class="inputBtns" value="<fmt:message key="label.search"/> XML" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "return submitSearchXmlForm()">--%>
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
<input type="hidden" name="uAction" id="uAction" value="createXSL"/>
<input type="hidden" name="hubName" id="hubName" value=""/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
<input name="searchHeight" id="searchHeight" type="hidden" value="148">
<input name="maxData" id="maxData" type="hidden" value="2000"/>
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
      <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
        <%--<a href="#" onclick="call('printGrid'); return false;"><fmt:message key="label.print"/></a>--%>
      <%-- <span id="showlegendLink">
        <a href="javascript:showreceivingpagelegend()"><fmt:message key="label.showlegend"/></a>
      </span>
      <span id="updateResultLink" style="display: none">
        <tcmis:permission indicator="true" userGroupId="transactions">
         <a href="#" onclick="submitUpdate(); return false;"><fmt:message key="label.update"/></a>
        </tcmis:permission>
      </span> --%>
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

</body>
</html:html>