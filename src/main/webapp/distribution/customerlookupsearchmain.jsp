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
<script type="text/javascript" src="/js/distribution/customerlookupsearchmain.js"></script>
  

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>


<title>
<fmt:message key="label.customerlookup"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages

var valueElementId="${valueElementId}";
var displayElementId="${displayElementId}";

var messagesData = new Array();
messagesData={
alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
all:"<fmt:message key="label.all"/>",
errors:"<fmt:message key="label.errors"/>",  
customerid:"<fmt:message key="label.customerid"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
newcustomerrequest:"<fmt:message key="customerrequestdetail.title"/>",
customerclass:"<fmt:message key="label.customerclass"/>",
na:"<fmt:message key="label.na"/>",
showlegend:"<fmt:message key="label.showlegend"/>",
itemInteger:"<fmt:message key="error.item.integer"/>"};
// -->
</script>
</head>

<c:choose>
      <c:when test="${param.uAction == 'searchCustomer'}">
        <c:set var="srcAction" value="customersearchresults.do?uAction=${param.uAction}&searchNameArgument=${param.searchNameArgument}&searchNameMode=${param.searchNameMode}"/>
     </c:when>      
    </c:choose> 
<body bgcolor="#ffffff" onload="loadLayoutWin('','customerSearch');mainOnload();" onresize="resizeFrames()">

<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<!-- action=start is there to decide in the action to query the database for the drop down in the search section.
     This is being done to avoid quering the databsae for drop down when I don't need them
 -->
<%-- <iframe id="searchFrame" name="searchFrame" width="100%" height="150" frameborder="0" marginwidth="0" style="" src="transactionssearch.do"></iframe>--%>
<%--NEW - removed the search frame and copied the search section here--%>
<div class="contentArea">
<tcmis:form action="/customerlookupsearchresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
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
                <td width="5%" class="optionTitleBoldRight" nowrap><fmt:message key="label.customerid"/>:</td> 
        <td width="45%" class="optionTitleLeft" nowrap>
           <select name="searchCustomerIdMode" id="searchCustomerIdMode" class="selectBox">
            <option value="is" selected="selected"><fmt:message key="label.is"/></option>           
          </select>
          <input class="inputBox" type="text" name="searchCustomerIdArgument" id="searchCustomerIdArgument" value="${param.searchCustomerIdArgument}" size="5" onkeypress="return onKeyPress()"/>
        </td> 
        <td width="5%" class="optionTitleBoldRight" nowrap><fmt:message key="label.billto" />:</td> 
        <td width="45%" class="optionTitleLeft" nowrap>
           <select name="searchBillToMode" id="searchBillToMode" class="selectBox">
            <option value="is"><fmt:message key="label.is"/></option>
            <option value="contains" selected="selected"><fmt:message key="label.contains"/></option>
            <option value="startsWith"><fmt:message key="label.startswith"/></option>
            <option value="endsWith"><fmt:message key="label.endswith"/></option>
          </select>
          <input class="inputBox" type="text" name="searchBillToArgument" id="searchBillToArgument" size="20" onkeypress="return onKeyPress()"></input></td>
        </td>
       </tr>
       
    <%--     Row 2    --%>
      <tr>
         <td width="5%" class="optionTitleBoldRight" nowrap><fmt:message key="label.customer" />&nbsp;<fmt:message key="label.name" />:</td> 
        <td width="45%" class="optionTitleLeft" nowrap>
           <select name="searchNameMode" id="searchNameMode" class="selectBox">
            <option value="is"><fmt:message key="label.is"/></option>
            <option value="contains" selected><fmt:message key="label.contains"/></option>
            <option value="startsWith"><fmt:message key="label.startswith"/></option>
            <option value="endsWith"><fmt:message key="label.endswith"/></option>
          </select>
          <input class="inputBox" type="text" name="searchNameArgument" id="searchNameArgument" value="${param.searchNameArgument}" size="20" onkeypress="return onKeyPress()"></input>
          </td>
        <td width="5%" class="optionTitleBoldRight" nowrap><fmt:message key="label.shipto" />:</td> 
        <td width="45%" class="optionTitleLeft" nowrap>
           <select name="searchShipToMode" id="searchShipToMode" class="selectBox">
            <option value="is"><fmt:message key="label.is"/></option>
            <option value="contains" selected="selected"><fmt:message key="label.contains"/></option>
            <option value="startsWith"><fmt:message key="label.startswith"/></option>
            <option value="endsWith"><fmt:message key="label.endswith"/></option>
          </select>
          <input class="inputBox" type="text" name="searchShipToArgument" id="searchShipToArgument" size="20" onkeypress="return onKeyPress()">
        </td>
      </tr>
    
    <%--     Row 3    --%>
          <tr>
        <td width="5%" class="optionTitleBoldRight" nowrap><fmt:message key="label.synonym" />:</td> 
        <td width="45%" class="optionTitleLeft" nowrap>
           <select name="searchSynonymMode" id="searchSynonymMode" class="selectBox">
            <option value="is"><fmt:message key="label.is"/></option>
            <option value="contains" selected><fmt:message key="label.contains"/></option>
            <option value="startsWith"><fmt:message key="label.startswith"/></option>
            <option value="endsWith"><fmt:message key="label.endswith"/></option>
          </select>
          <input class="inputBox" type="text" name="searchSynonymArgument" id="searchSynonymArgument" value="${param.searchSynonymArgument}" size="20" onkeypress="return onKeyPress()"></input>
          </td>       
      </tr>
   <%--     Row 4    --%>
    <tr>
     <td colspan="8" class="optionTitleBoldLeft">
          <input name="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="label.search"/>" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "return submitSearchForm()">
          		 <input name="xslButton" type="button" class="inputBtns" value="<fmt:message key="label.createExcel"/>" id="xslButton" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "return createXSL()"/>
<c:set var="newPermission" value="false"/>
<tcmis:opsEntityPermission indicator="true" userGroupId="CustomerDetailAdmin">
  <c:set var="newPermission" value="true"/>
</tcmis:opsEntityPermission>

<tcmis:opsEntityPermission indicator="true" userGroupId="CustomerDetailUpdate">
  <c:set var="newPermission" value="true"/>
</tcmis:opsEntityPermission>

<tcmis:opsEntityPermission indicator="true" userGroupId="GenerateOrders">
  <c:set var="newPermission" value="true"/>
</tcmis:opsEntityPermission>

<tcmis:inventoryGroupPermission indicator="true" userGroupId="GenerateOrders">
  <c:set var="newPermission" value="true"/>
</tcmis:inventoryGroupPermission>

<c:if test="${ empty displayElementId  &&  empty valueElementId}">     

<input name="submitNew" id="submitNew" type="button" value="<fmt:message key="label.newcustomer"/>" class="inputBtns"  onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'"
 onclick="subnew()">
</c:if>

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
<input type="hidden" name="uAction" id="uAction" value=""/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
<input type="hidden" name="currentCustomerId" id="currentCustomerId" value="${currentCustomerId}"/>
<input type="hidden" name="selectedCustomerId" id="selectedCustomerId" value="${param.selectedCustomerId}"/>
<input type="hidden" name="popup" id="popup" value="${param.popup}"/>
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
        <span id="showlegendLink"><a href="javascript:showLegend()"><fmt:message key="label.showlegend"/></a></span>
        <span id="selectedRow">&nbsp;</span> 
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
<%-- show legend Begins --%>
<div id="showLegendArea" style="display: none;overflow: auto;">
  <table width=100% class="tableResults" border="0" cellpadding="0" cellspacing="0">
    <tr><td width="100px" class="black legendText">&nbsp;</td><td class="legendText"><fmt:message key="label.creditstatusstop"/></td></tr>
  </table>
</div>
<%-- show legend Ends --%>
</body>
</html:html>