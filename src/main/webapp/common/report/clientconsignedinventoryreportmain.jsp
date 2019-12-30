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

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/report/clientconsignedinventoryreportmain.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<title>
    <fmt:message key="consignedinventoryreport.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",all:"<fmt:message key="label.all"/>",
showlegend:"<fmt:message key="label.showlegend"/>",errors:"<fmt:message key="label.errors"/>",  
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
pleaseselect:"<fmt:message key="label.pleaseselect"/>",
entervalidintegerforexpireswithin:"<fmt:message key="label.entervalidintegerforexpireswithin"/>",
entervalidintegerforexpiresafter:"<fmt:message key="label.entervalidintegerforexpiresafter"/>"};

var altFacilityId = new Array(
<c:forEach var="facilityIgViewOvBean" items="${facilityIgViewOvBeanCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,"<c:out value="${status.current.facilityId}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status.current.facilityId}"/>"
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altFacilityName = new Array(
<c:forEach var="facilityIgViewOvBean" items="${facilityIgViewOvBeanCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,"<c:out value="${status.current.facilityName}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status.current.facilityName}"/>"
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altInventoryGroup = new Array();
var altInventoryGroupName = new Array();
var altInventoryGroupCollection = new Array();
<c:forEach var="facilityIgViewOvBean" items="${facilityIgViewOvBeanCollection}" varStatus="status">
  <c:set var="currentFacility" value='${status.current.facilityId}'/>
  <c:set var="inventoryGroups" value='${status.current.inventoryGroups}'/>

	<bean:size id="inventoryGroupSize" name="inventoryGroups"/>

  altInventoryGroup["<c:out value="${currentFacility}" escapeXml="false"/>"] = new Array(
  <c:forEach var="inventoryGroupDefinitionOvBean" items="${inventoryGroups}" varStatus="status1">
		<c:choose>
	    <c:when test="${status1.index == 0 && inventoryGroupSize > 1}">
        "All","<c:out value="${status1.current.inventoryGroup}"/>"
      </c:when>
      <c:otherwise>
        <c:choose>
          <c:when test="${status1.index == 0}">
            "<c:out value="${status1.current.inventoryGroup}"/>"
          </c:when>
          <c:otherwise>
            ,"<c:out value="${status1.current.inventoryGroup}"/>"
          </c:otherwise>
        </c:choose>
			</c:otherwise>
    </c:choose>
  </c:forEach>
  );

  altInventoryGroupName["<c:out value="${currentFacility}" escapeXml="false"/>"] = new Array(
  <c:forEach var="inventoryGroupDefinitionOvBean" items="${inventoryGroups}" varStatus="status1">
		<c:choose>
	    <c:when test="${status1.index == 0 && inventoryGroupSize > 1}">
        "All","<c:out value="${status1.current.inventoryGroup}"/>"
      </c:when>
      <c:otherwise>
        <c:choose>
          <c:when test="${status1.index == 0}">
            "<c:out value="${status1.current.inventoryGroupName}"/>"
          </c:when>
          <c:otherwise>
            ,"<c:out value="${status1.current.inventoryGroupName}"/>"
          </c:otherwise>
        </c:choose>
			</c:otherwise>
    </c:choose>
  </c:forEach>
  );

  altInventoryGroupCollection["<c:out value="${currentFacility}" escapeXml="false"/>"] = new Array(
  <c:forEach var="inventoryGroupDefinitionOvBean" items="${inventoryGroups}" varStatus="status1">
   <c:choose>
   <c:when test="${status1.index > 0}">
    ,"<c:out value="${status1.current.collection}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status1.current.collection}"/>"
   </c:otherwise>
  </c:choose>
  </c:forEach>
  );
</c:forEach>

// -->
</script>
</head>
<%-- displayOnly needs to match the pageData in application.jsp if the page will be on the menu --%>
<body bgcolor="#ffffff" onload="loadLayoutWin('','clientConsignedInventoryReport');facilityChanged();" onresize="resizeFrames()">

<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<%--NEW - removed the search frame and copied the search section here--%>
<div class="contentArea">
<tcmis:form action="/clientconsignedinventoryreportresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
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
    <table width="500" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
    <%-- row 1 --%>
    <tr>
		<td width="15%" class="optionTitleBoldRight">
				<fmt:message key="label.facility"/>:&nbsp;
		</td>
		<td width="40%" class="optionTitleBoldLeft">
		  <c:set var="selectedFacility" value='${param.facilityId}'/>
		  <c:if test="${empty selectedFacility}">
			  <c:set var="selectedFacility" value='${personnelBean.myDefaultFacilityId}'/>
		  </c:if>
		  <select name="facilityId" id="facilityId" onchange="facilityChanged()" class="selectBox">
	        <c:forEach var="facilityIgViewOvBean" items="${facilityIgViewOvBeanCollection}" varStatus="status">
	          <c:set var="currentFacility" value='${status.current.facilityId}'/>
	          <c:choose>
	          <c:when test="${empty selectedFacility}" >
	            <c:set var="selectedFacility" value=""/>
	            <c:set var="inventoryGroupNameOvBeanCollection" value='${status.current.inventoryGroups}'/>
	          </c:when>
	          <c:when test="${currentFacility == selectedFacility}" >
	            <c:set var="inventoryGroupNameOvBeanCollection" value='${status.current.inventoryGroups}'/>
	          </c:when>
	          </c:choose>
	
	          <c:choose>
	          <c:when test="${currentFacility == selectedFacility}">
	            <option value="<c:out value="${currentFacility}"/>" selected><c:out value="${status.current.facilityName}"/></option>
	          </c:when>
	          <c:otherwise>
	            <option value="<c:out value="${currentFacility}"/>"><c:out value="${status.current.facilityName}"/></option>
	          </c:otherwise>
	          </c:choose>
	      </c:forEach>
	      </select>
	   </td>

       <td width="45%" class="optionTitleBoldRight" nowrap>
         <fmt:message key="inventory.label.expireswithin"/>:&nbsp;
         <input class="inputBox" type="text" name="expiresWithin" id="expiresWithin" value="<c:out value="${param.expiresWithin}"/>" onChange="checkexpiresWithin()" maxlength="10" size="3">
         <fmt:message key="label.days"/>
       </td>
    </tr>

	  <%-- row 2 --%>
	  <tr>
		<td width="15%" class="optionTitleBoldRight" nowrap>
				<fmt:message key="label.inventorygroup"/>:&nbsp;
		</td>
		<td width="40%" class="optionTitleBoldLeft">

	      <c:choose>
	        <c:when test="${inventoryGroupNameOvBeanCollection == null}">
	          <c:set var="inventoryGroupSize" value="0"/>
	        </c:when>
	        <c:otherwise>
	          <bean:size id="inventoryGroupSize" name="inventoryGroupNameOvBeanCollection"/>
	        </c:otherwise>
	      </c:choose>
			<select name="inventoryGroup" id="inventoryGroup"  class="selectBox">
				<c:choose>
					<c:when test="${inventoryGroupSize == 0}">
						<option value="All"><fmt:message key="label.all"/></option>
					</c:when>
					<c:otherwise>
						<c:if test="${inventoryGroupSize > 1}">
			              <option value="All"><fmt:message key="label.all"/></option>
			            </c:if>
						<c:set var="selectedInventoryGroup" value='${param.inventoryGroup}'/>
			            <c:forEach var="inventoryGroupNameOvBean" items="${inventoryGroupNameOvBeanCollection}" varStatus="status">
			              <c:set var="currentSelectedInventory" value='${status.current.inventoryGroup}'/>
			              <c:choose>
			              <c:when test="${selectedInventoryGroup == currentSelectedInventoryGroup}">
			                <option value="<c:out value="${currentSelectedInventoryGroup}"/>" selected><c:out value="${status.current.inventoryGroupName}"/></option>
			              </c:when>
			              <c:otherwise>
			                <option value="<c:out value="${currentSelectedInventoryGroup}"/>"><c:out value="${status.current.inventoryGroupName}"/></option>
			              </c:otherwise>
			             </c:choose>
			            </c:forEach>
					</c:otherwise>
				</c:choose>
			</select>
		</td>

		<td width="45%" class="optionTitleBoldRight" nowrap>
				<fmt:message key="inventory.label.expiresafter"/>:&nbsp;
				<input class="inputBox" type="text" name="expiresAfter" id="expiresAfter" value="<c:out value="${param.expiresAfter}"/>" onChange="checkexpiresAfter()" maxlength="10" size="3">
				<fmt:message key="label.days"/>
		</td>
	</tr>
	<%-- Row 3 --%>
    <!--<tr>
      <td>
          &nbsp;
      </td>
      <td class="optionTitleBoldLeft" nowrap>
          <input type="checkbox" class="radioBtns" name="showCustomerReturn" id="showCustomerReturn" value="Y" />&nbsp;<fmt:message key="label.showcustomerreturn"/>
      </td>
      <td colspan="2">
          &nbsp;
      </td>
    </tr>-->

    <%-- Row 4 --%>
    <tr>
     <td colspan="3"  class="optionTitleBoldLeft" nowrap>
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

  <input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
 <!-- needed if this page will show on application.do -->
 <!-- To get the correct height value to insert, insert showSearchHeight = true; anywhere in the JavaScript array section in main.jsp.  -->
  <input name="searchHeight" id="searchHeight" type="hidden" value="133"/>
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
		&nbsp;
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