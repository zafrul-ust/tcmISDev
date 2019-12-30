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

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss />
<!-- CSS for YUI -->
<%--
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>
<!-- Add any other stylesheets you need for the page here -->

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<script type="text/javascript" src="/js/common/searchiframeresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<%--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/clientinventory/inventory.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>

<!-- This is for the YUI, uncomment if you will use this -->
<%--<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>--%>

<title>
<fmt:message key="label.inventory"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
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
<c:forEach var="facilityIgViewOvBean" items="${facilityIgViewOvBeanCollection}" varStatus="status">
  <c:set var="currentFacility" value='${status.current.facilityId}'/>
  <c:set var="inventoryGroups" value='${status.current.inventoryGroups}'/>

	<bean:size id="inventoryGroupSize" name="inventoryGroups"/>

  altInventoryGroup["<c:out value="${currentFacility}" escapeXml="false"/>"] = new Array(
  <c:forEach var="inventoryGroupDefinitionOvBean" items="${inventoryGroups}" varStatus="status1">
		<c:choose>
	    <c:when test="${status1.index == 0 && inventoryGroupSize > 1}">
        "ALL","<c:out value="${status1.current.inventoryGroup}"/>"
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
            "<fmt:message key="label.allinventorygroups"/>","<c:out value="${status1.current.inventoryGroupName}"/>"
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
</c:forEach>

var menuskin = "skin1"; // skin0, or skin1
var display_url = 0; // Show URLs in status bar?
// -->
</script>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
all:"<fmt:message key="label.all"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
entervalidintegerforexpireswithin:"<fmt:message key="label.entervalidintegerforexpireswithin"/>",
entervalidintegerforexpiresafter:"<fmt:message key="label.entervalidintegerforexpiresafter"/>",
entervalidintegerforarriveswithin:"<fmt:message key="label.entervalidintegerforarriveswithin"/>",
novalidlineselected:"<fmt:message key="label.novalidlineselected"/>",
searchMessage:"<fmt:message key="receiptdocumentviewer.searchmessage"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="mySearchOnload();">

<tcmis:form action="/inventoryresults.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">

<div class="interface" id="searchMainPage">

<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="500" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
    <%-- row 1 --%>
    <tr>
			<td width="15%" class="optionTitleBoldRight">
				<fmt:message key="label.facility"/>:&nbsp;
			</td>
			<td width="40%" class="optionTitleBoldLeft">
                <select name="facilityId" id="facilityId" onchange="facilityChanged()" class="selectBox">
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
			<td width="15%" class="optionTitleBoldRight">
				<fmt:message key="label.inventory"/>:&nbsp;
			</td>
			<td width="40%" class="optionTitleBoldLeft">
			    <select name="inventory" id="inventory"  class="selectBox">
			    </select>
			</td>

			<td width="45%" class="optionTitleBoldRight" nowrap>
				<fmt:message key="inventory.label.expiresafter"/>:&nbsp;
				<input class="inputBox" type="text" name="expiresAfter" id="expiresAfter" value="<c:out value="${param.expiresAfter}"/>" onChange="checkexpiresAfter()" maxlength="10" size="3">
				<fmt:message key="label.days"/>
			</td>
	  </tr>

	  <%-- row 3 --%>
		<tr>
       <td width="15%" class="optionTitleBoldRight" nowrap>
         <fmt:message key="label.searchtext"/>:&nbsp;
       </td>
       <td width="40%" class="optionTitleBoldLeft">
          <input class="inputBox" type="text" name="searchText" id="searchText" value="<c:out value='${param.searchText}'/>" size="20">
       </td>

			<td width="45%" class="optionTitleBoldRight" nowrap>
			    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<fmt:message key="inventory.label.arriveswithin"/>:&nbsp;
				<input class="inputBox" type="text" name="arrivesWithin" id="arrivesWithin" value="<c:out value="${param.arrivesWithin}"/>" onChange="checkarrivesWithin()" maxlength="10" size="3">
				<fmt:message key="label.days"/>
			</td>
		</tr>

    <%-- buttons --%>
    <tr>
      <td width="10%" colspan="2" class="optionTitleBoldLeft">
        <input name="submitSearch" id="submitSearch" type="submit" class="inputBtns" value="<fmt:message key="button.search"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'" onclick="return submitSearchForm();">
        <input name="buttonCreateExcel" id="buttonCreateExcel" type="submit" class="inputBtns" value="<fmt:message key="label.createexcelfile"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="generateExcel(); return false;">
      </td>
    </tr>

    </table>
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

</div> <!-- close of contentArea -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="action" id="action" type="hidden" value="">
<input name="companyId" id="companyId" type="hidden" value="${personnelBean.companyId}">
<input name="myDefaultFacilityId" id="myDefaultFacilityId" type="hidden" value="${personnelBean.myDefaultFacilityId}">
<input name="startSearchTime" id="startSearchTime" type="hidden"value="">
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>