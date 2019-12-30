<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

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
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"> </link>

<%@ include file="/common/locale.jsp" %>
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
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

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/js/client/catalog/catalogreportmain.js"></script>


<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<title>
   <fmt:message key="label.catalogreport"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--

<c:set var="selectedFacilityId" value='${personnelBean.myDefaultFacilityId}'/>

var altFacilityId = new Array(
<c:forEach var="userFacCatAppOvBean" items="${userFacCatAppOvBeanColl}" varStatus="status">
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
<c:forEach var="userFacCatAppOvBean" items="${userFacCatAppOvBeanColl}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,'<tcmis:jsReplace value="${status.current.facilityName}" />'
   </c:when>
   <c:otherwise>
    '<tcmis:jsReplace value="${status.current.facilityName}" />'
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altApplicationId = new Array();
var altApplicationDesc = new Array();
<c:forEach var="userFacCatAppOvBean" items="${userFacCatAppOvBeanColl}" varStatus="status">
<c:set var="currentFacilityId" value='${status.current.facilityId}'/>
<c:set var="applicationObjBeanCollection" value='${status.current.applicationList}'/>

<c:set var="applicationCount" value='${0}'/>
altApplicationId["<c:out value="${currentFacilityId}"/>"] = new Array(
 <c:forEach var="applicationObjBean" items="${applicationObjBeanCollection}" varStatus="status1">
  <c:choose>
   <c:when test="${applicationCount > 0}">
    ,"<c:out value="${status1.current.application}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status1.current.application}"/>"
   </c:otherwise>
  </c:choose>
  <c:set var="applicationCount" value='${applicationCount+1}'/>
  </c:forEach>
  );

<c:set var="applicationCount" value='${0}'/>
altApplicationDesc["<c:out value="${currentFacilityId}"/>"] = new Array(
 <c:forEach var="applicationObjBean" items="${applicationObjBeanCollection}" varStatus="status1">
  <c:choose>
   <c:when test="${applicationCount > 0}">
    ,'<tcmis:jsReplace value="${status1.current.applicationDesc}" processMultiLines="true" />'
   </c:when>
   <c:otherwise>
    '<tcmis:jsReplace value="${status1.current.applicationDesc}" processMultiLines="true" />'
   </c:otherwise>
  </c:choose>
  <c:set var="applicationCount" value='${applicationCount+1}'/>
  </c:forEach>
  );
 </c:forEach>

var altCatalogId = new Array();
var altCatalogDesc = new Array();
<c:forEach var="facAppUserGrpOvBean" items="${userFacCatAppOvBeanColl}" varStatus="status">
<c:set var="currentFacilityId" value='${status.current.facilityId}'/>
<c:set var="catalogObjBeanCollection" value='${status.current.catalogList}'/>

<c:set var="catalogCount" value='${0}'/>
altCatalogId["<c:out value="${currentFacilityId}"/>"] = new Array(
 <c:forEach var="catalogObjBean" items="${catalogObjBeanCollection}" varStatus="status1">
  <c:choose>
   <c:when test="${catalogCount > 0}">
    ,"<c:out value="${status1.current.catalogId}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status1.current.catalogId}"/>"
   </c:otherwise>
  </c:choose>
  <c:set var="catalogCount" value='${catalogCount+1}'/>
  </c:forEach>
  );

<c:set var="catalogCount" value='${0}'/>
altCatalogDesc["<c:out value="${currentFacilityId}"/>"] = new Array(
 <c:forEach var="catalogObjBean" items="${catalogObjBeanCollection}" varStatus="status1">
  <c:choose>
   <c:when test="${catalogCount > 0}">
    ,'<tcmis:jsReplace value="${status1.current.catalogDesc}" />'
   </c:when>
   <c:otherwise>
    '<tcmis:jsReplace value="${status1.current.catalogDesc}" />'
   </c:otherwise>
  </c:choose>
  <c:set var="catalogCount" value='${catalogCount+1}'/>
  </c:forEach>
  );
 </c:forEach>




var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
all:"<fmt:message key="label.all"/>",
countData:"<fmt:message key="label.itemcountmsg"/>",
errors:"<fmt:message key="label.errors"/>",
pleaseselect:"<fmt:message key="label.pleaseselect"/>",
partCatalog:"<fmt:message key="label.partcatalog"/>",
allCatalogs:"<fmt:message key="label.allcatalogs"/>",
allworkareas:"<fmt:message key="label.allWorkAreas"/>",
waitingforinputfrom:"<fmt:message key="label.waitingforinputfrom"/>"
};


// -->
</script>
</head>

<body bgcolor="#ffffff" onresize="resizeFrames()" onload="loadLayoutWin('','catalogReport');loadFacility();">
<div class="interface" id="mainPage" style="">
<div id="searchFrameDiv">
<div class="contentArea">
<tcmis:form action="/catalogreportresults.do"  onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent" id="searchTableDiv" >
    <!-- Insert all the search option within this div -->
    <table id="searchTable" width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
<tr>
<td width="10%" class="optionTitleBoldRight">
<fmt:message key="label.facility"/>:
</td>

<td width="40%" class="optionTitleBoldLeft" nowrap>
<select name="facilityId" id="facilityId" class="selectBox" onchange="facilityChanged()">
</select>
</td>
<td width="40%" class="optionTitleBoldLeft" nowrap>
<input type="checkbox" name="workAreaApprovedOnly" id="workAreaApprovedOnly" value="Y" checked="checked" class="radioBtns"><fmt:message key="label.facilityapprovedonly"/>
</td>

</tr>
<tr>
<td width="10%" class="optionTitleBoldRight" nowrap>
<fmt:message key="label.catalog"/>:
</td>

<td width="40%" class="optionTitleBoldLeft">
<select name="catalogId" id="catalogId" class="selectBox" onchange="catalogChanged()">
</select>
</td>

</tr>

<tr>

<td width="10%" class="optionTitleBoldRight" nowrap>
<fmt:message key="label.workarea"/>:
</td>

<td width="40%" class="optionTitleBoldLeft">
<select name="applicationId" id="applicationId" class="selectBox" onchange="applicationChanged()">
</select>
</td>

</tr>
<tr>
<td width="10%"  class="optionTitleBoldRight">
<fmt:message key="label.search"/>:
</td>

<td width="40%" class="optionTitleBoldLeft" nowrap>
<input name="searchText" id="searchText" type="text" class="inputBox" value="${param.searchText}" size="30" onkeypress="return onKeyPress()"/>
<c:if test="${minimumCharLength > 25}">
	<img src="/images/minwidth.gif" width="${(minimumCharLength-25)*7}" height="0">
</c:if>
</td>
</tr>

<tr>
<td  class="optionTitleBoldLeft" colspan="2">
    <input name="submitSearch" id="submitSearch" type="submit" value="<fmt:message key="label.search"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'"  onclick="return submitSearchForm();">
    <input name="createexcel"  id="createexcel"  type="button" value="<fmt:message key="label.createexcel"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="createXSL()"/>
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

<div id="hiddenElements" style="display: none;">
<input name="uAction" id="uAction" value="" type="hidden" />
<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
<input name="facilityName" id="facilityName" type="hidden" value="" />
<input name="applicationDesc" id="applicationDesc" type="hidden" value="" />
<input name="searchHeight" id="searchHeight" type="hidden" value="193">
<input name="companyId" id="companyId" type="hidden" value="${userFacCatAppOvBeanColl[0].companyId}" />

<%-- the following fields are for catalog adds --%>
<input type="hidden" name="myDefaultFacilityId" id="myDefaultFacilityId" value="${personnelBean.myDefaultFacilityId}"/>
</div>

</tcmis:form>
</div>
</div>
<!-- Search Frame Ends -->


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
    <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
      --%>
      <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
      
        
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
      		
    		
