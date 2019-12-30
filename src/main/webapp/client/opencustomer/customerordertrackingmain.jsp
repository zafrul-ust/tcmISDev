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
<script type="text/javascript" src="/js/distribution/customerordertrackingmain.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<title>
    <fmt:message key="ordertracking.label.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",all:"<fmt:message key="label.all"/>",
showlegend:"<fmt:message key="label.showlegend"/>",errors:"<fmt:message key="label.errors"/>",  
validvalues:"<fmt:message key="label.validvalues"/>",mr:"<fmt:message key="label.mr"/>",
selectatleastonecustomer:"<fmt:message key="label.selectatleastonecustomer"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", itemInteger:"<fmt:message key="error.item.integer"/>"};

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="loadLayoutWin('','customerOrderTracking');getCustomerIdList();<c:if test="${sessionScope.personnelBean.openCustomer}">updateFacilities();</c:if>" onunload="try{closeAllchildren();}catch(ex){}" onresize="resizeFrames()">
<c:if test="${sessionScope.personnelBean.openCustomer}">
	<script>
		var customerFacilities = [
		<c:forEach var="customer" items="${userCustomerCollection}" varStatus="status1">
			{id: '${customer.customerId}',
			 facilities: [<c:forEach var="facility" items="${customer.userFacilityCollection}" varStatus="status2">
			 		{id: '${facility.facilityId}', name:'<tcmis:jsReplace value="${facility.facilityName}"/>'}<c:if test="${!status2.last}">,</c:if>
				     </c:forEach>]
			}<c:if test="${!status1.last}">,</c:if>
		</c:forEach>]
	</script>
</c:if>
<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<%--NEW - removed the search frame and copied the search section here--%>
<div class="contentArea">
<tcmis:form action="/customerordertrackingresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
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
    <tr>
    	<c:choose>
    		<c:when test="${sessionScope.personnelBean.openCustomer}">
		      <td class="optionTitleBoldRight" nowrap> <fmt:message key="label.customer"/>:</td>
		      <td class="optionTitleLeft" colspan="3">
		        <select name="customerId" id="customerId" class="selectBox" onchange="updateFacilities(); getCustomerIdList();" >
		          <c:forEach var="userCustomerBean" items="${userCustomerCollection}" varStatus="status">
		            <option value="${status.current.customerId}" >${status.current.customerName}</option>
		          </c:forEach>
		        </select>
		        <input name="customerIdList" id="customerIdList" type="hidden" value=""/>
		      </td>
		</c:when>
		<c:otherwise>
		      <td class="optionTitleBoldRight" rowspan="3"> <fmt:message key="label.customer"/>:</td>
		      <td class="optionTitleLeft"  rowspan="3" >
		        <select name="customerId"  class="selectBox" id="customerId"   onchange="getCustomerIdList();" multiple size="4">
		          <c:forEach var="userCustomerBean" items="${userCustomerCollection}" varStatus="status1">
		            <option value="${status1.current.customerId}" >${status1.current.customerName}</option>
		          </c:forEach>
		        </select>
		        <input name="customerIdList" id="customerIdList" type="hidden" value=""/>
		      </td>
		</c:otherwise>
	</c:choose>
    </tr>
    <c:if test="${sessionScope.personnelBean.openCustomer}">
    	<tr>
	      <td class="optionTitleBoldRight" nowrap> <fmt:message key="label.shipto"/>:</td>
	      <td class="optionTitleLeft" colspan="3" >
	        <select name="facilityId" id="facilityId" class="selectBox">
	        </select>
	      </td>
    	</tr>
    </c:if>
    <tr>
      <td rowspan="3" width="2%" class="optionTitleBoldRight" nowrap><fmt:message key="label.status"/>:</td>
      <td rowspan="3" width="10%" class="optionTitleBoldLeft">
        <select name="orderStatus"  class="selectBox" id="orderStatus" onchange="getOrderStatusListString();" multiple size="4" >
            <option value=""><fmt:message key="label.all"/></option>
<%--        <option value="Draft"> <fmt:message key="label.draft"/>  </option>   --%>      
            <option value="Open" selected> <fmt:message key="label.open"/>  </option>
            <option value="Complete"> <fmt:message key="label.complete"/>  </option>
        </select>
        <input name="orderStatusList" id="orderStatusList" type="hidden" value="Open"/>  
      </td>   
      <td class="optionTitleBoldRight"><fmt:message key="label.orderdate"/>:</td>
	  <td nowrap class="optionTitleBoldLeft">
		 <fmt:message key="label.from"/>
		 <input class="inputBox pointer" readonly type="text" name="orderFromDate" id="orderFromDate" value="<tcmis:getDateTag numberOfDaysFromToday="-30" datePattern="${dateFormatPattern}"/>" 
		 	onClick="return getCalendar(document.customerordertracking.orderFromDate,null,document.customerordertracking.orderToDate);" maxlength="10" size="9"/>
		 <fmt:message key="label.to"/>&nbsp;
		 <input class="inputBox pointer" readonly type="text" name="orderToDate" id="orderToDate" value="" onClick="return getCalendar(document.customerordertracking.orderToDate,document.customerordertracking.orderFromDate);"
		        maxlength="10" size="9"/>
	  </td>
    </tr>
    <tr>
       <td nowrap  class="optionTitleBoldRight"><fmt:message key="label.promised.ship.date"/>:</td>
	   <td nowrap class="optionTitleBoldLeft">
		 <fmt:message key="label.from"/>
		 <input class="inputBox pointer" readonly type="text" name="promiseShipFromDate" id="promiseShipFromDate" value="" onClick="return getCalendar(document.customerordertracking.promiseShipFromDate,null,document.customerordertracking.promiseShipToDate);"
		        maxlength="10" size="9"/>
		 <fmt:message key="label.to"/>&nbsp;
		 <input class="inputBox pointer" readonly type="text" name="promiseShipToDate" id="promiseShipToDate" value="" onClick="return getCalendar(document.customerordertracking.promiseShipToDate,document.customerordertracking.promiseShipFromDate);"
		        maxlength="10" size="9"/>
	   </td> 
    </tr>
    <tr>
      <td class="optionTitleBoldRight" nowrap> <fmt:message key="label.search"/>:</td>
      <td nowrap class="optionTitleBoldLeft">
            <select name="searchField"  class="selectBox" id="searchField">                                
                <option value="pr_number"><fmt:message key="label.mr"/></option>
                <option value="fac_part_no"><fmt:message key="label.catalogitem"/></option>
                <option value="po_number" selected> <fmt:message key="label.customerpo"/></option>
                <option value="customer_part_no"> <fmt:message key="label.customerpartnumber"/></option>
                <option value="part_description"> <fmt:message key="label.description"/></option>
            </select>
            &nbsp;
             <select name="searchMode" class="selectBox" id="searchMode" >
                <option value="equals" selected><fmt:message key="label.is"/></option>
	            <option value="like"><fmt:message key="label.contains"/></option>
	            <option value="begins with"><fmt:message key="label.startswith"/></option>
	            <option value="ends with"><fmt:message key="label.endswith"/></option>
             </select>
            <input name="searchArgument" id="searchArgument" type="text" class="inputBox" size="15"/>
      </td>    
    </tr>
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
<input type="hidden" name="hubName" id="hubName" value=""/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
<input name="searchHeight" id="searchHeight" type="hidden" value="214">
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
    <div class="boxhead"> 
    <%-- boxhead Begins --%>
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
          Please keep the <span></span> on the same line this will avoid extra virtical space.
      --%>     
		<%-- Use this case if you only have one update link to minimize extra line.  Notice this uses "div" instead of "span" --%>
	  <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
	   <span id="showlegendLink"><a href="javascript:showLegend()"><fmt:message key="label.showlegend"/></a></span>
      <div id="updateResultLink" style="display: none">
         <a href="#" onclick="call('updateSalesOrder'); return false;"><fmt:message key="label.update"/></a>
      </div>
     </div> <%-- mainUpdateLinks Ends --%>
     <%-- END OF OR --%>
	</div> <%-- boxhead Ends --%>

    <div class="dataContent">
     <iframe scrolling="no" id="resultFrame" name="resultFrame" width="100%" height="300" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
   </div>

  	  <%-- result count and time --%>
    <div id="footerHolder" class="messageBar"><span id="footer" style="float: left"></span><span style="float: right"><fmt:message key="label.rightclickformoreoptions"/></span></div>


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
   <tr><td width="100px" class="pink legendText">&nbsp;</td><td class="legendText"><fmt:message key="label.aog"/></td></tr>
    <tr><td width="100px" class="red legendText">&nbsp;</td><td class="legendText"><fmt:message key="label.criticalorders"/></td></tr>
    <tr><td width="100px" class="yellow legendText">&nbsp;</td><td class="legendText"><fmt:message key="label.pendingcancellation"/></td></tr>
    <tr><td width="100px" class="black legendText">&nbsp;</td><td class="legendText"><fmt:message key="ordertracking.label.cancelled"/></td></tr>
  </table>
</div>
<%-- show legend Ends --%>
</body>
</html:html>