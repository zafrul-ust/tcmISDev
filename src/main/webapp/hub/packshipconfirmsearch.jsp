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

<%@ include file="/common/locale.jsp" %>
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss />
<!-- CSS for YUI -->
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />

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
<script type="text/javascript" src="/js/hub/packshipconfirmsearch.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>

	<!-- This is for the YUI, uncomment if you will use this -->
	<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
	<script type="text/javascript" src="/yui/build/event/event.js" ></script>
	<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
	<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
	<script type="text/javascript" src="/yui/build/container/container.js"></script>
	<script type="text/javascript" src="/js/common/waitDialog.js"></script>
	<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>

<title>
<fmt:message key="shipconfirm.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
var althubid = new Array(
<c:forEach var="hubInventoryGroupOvBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,"<c:out value="${status.current.branchPlant}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status.current.branchPlant}"/>"
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altinvid = new Array();
var altinvName = new Array();
<c:forEach var="hubInventoryGroupOvBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
<c:set var="currentHub" value='${status.current.branchPlant}'/>
<c:set var="inventoryGroupDefinitions" value='${status.current.inventoryGroupCollection}'/>

altinvid["<c:out value="${currentHub}"/>"] = new Array(""
 <c:forEach var="inventoryGroupObjBean" items="${inventoryGroupDefinitions}" varStatus="status1">
,"<c:out value="${status1.current.inventoryGroup}"/>"
  </c:forEach>

  );
altinvName["<c:out value="${currentHub}"/>"] = new Array("All"
 <c:forEach var="inventoryGroupObjBean" items="${inventoryGroupDefinitions}" varStatus="status1">
,"<c:out value="${status1.current.inventoryGroupName}"/>"
  </c:forEach>

  );
 </c:forEach>
// -->
</script>	

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
	messagesData={alert:"<fmt:message key="label.alert"/>",
	and:"<fmt:message key="label.and"/>",
	validvalues:"<fmt:message key="label.validvalues"/>",
	submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="setSearchFrameSize();">

<tcmis:form action="/packshipconfirmresults.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">

<div class="interface" id="searchMainPage">

<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="450" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
	    <tr>
			 <td width="5%" class="optionTitleBoldRight">
				<fmt:message key="label.hub"/>:
			 </td>
			 <td width="10%" class="optionTitleBoldLeft">
			 <c:set var="selectedHub" value='${param.sourceHub}'/>
			 <c:set var="selectedHubName" value=''/>
			 <%--
			 <select name="sourceHub" id="sourceHub" onchange="hubchanged()" class="selectBox">
			 --%>
			 <select name="sourceHub" id="sourceHub" class="selectBox">
				 <c:forEach var="hubInventoryGroupOvBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
				<c:set var="currentHub" value='${status.current.branchPlant}'/>

				<c:choose>
				 <c:when test="${empty selectedHub}" >
					<c:set var="selectedHub" value='${status.current.branchPlant}'/>
					<c:set var="selectedHubName" value="${status.current.hubName}"/>
					<c:set var="inventoryGroupDefinitions" value='${status.current.inventoryGroupCollection}'/>
				 </c:when>
				 <c:when test="${currentHub == selectedHub}" >
					<c:set var="inventoryGroupDefinitions" value='${status.current.inventoryGroupCollection}'/>
				 </c:when>
				</c:choose>

				<c:choose>
				 <c:when test="${currentHub == selectedHub}">
					<option value="<c:out value="${currentHub}"/>" selected><c:out value="${status.current.hubName}"/></option>
					<c:set var="selectedHubName" value="${status.current.hubName}"/>
				 </c:when>
				 <c:otherwise>
					<option value="<c:out value="${currentHub}"/>"><c:out value="${status.current.hubName}"/></option>
				 </c:otherwise>
				</c:choose>
				</c:forEach>
			 </select>
			</tr>

			<%--
			<tr>
			 <td class="optionTitleBoldRight">
			 <fmt:message key="label.inventorygroup"/>:
			 </td>
			 <td class="optionTitleBoldLeft">
			 <c:set var="selectedIg" value='${param.inventoryGroup}'/>
			 <c:set var="invenGroupCount" value='${0}'/>
			 <select name="inventoryGroup" id="inventoryGroup" class="selectBox">
				<option value=""><fmt:message key="label.all"/></option>
				<c:forEach var="inventoryGroupObjBean" items="${inventoryGroupDefinitions}" varStatus="status">
					<c:set var="invenGroupCount" value='${invenGroupCount+1}'/>
					<c:set var="currentIg" value='${status.current.inventoryGroup}'/>
					<c:choose>
						<c:when test="${empty selectedIg}" >
							<c:set var="selectedIg" value=""/>
						</c:when>
						<c:when test="${currentIg == selectedIg}" >
						</c:when>
					</c:choose>

					<c:choose>
						<c:when test="${currentIg == selectedIg}">
							<option value="<c:out value="${currentIg}"/>" selected><c:out value="${status.current.inventoryGroupName}"/></option>
						</c:when>
						<c:otherwise>
							<option value="<c:out value="${currentIg}"/>"><c:out value="${status.current.inventoryGroupName}"/></option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:if test="${invenGroupCount == 0}">
				 <option value=""><fmt:message key="label.all"/></option>
				</c:if>
			 </select>
			 </td>
			</tr>
			--%>

      <tr>
        <td class="optionTitleBoldRight"><fmt:message key="label.transportationmode"/>:</td>
        <td class="optionTitleBoldLeft">
          <html:select property="transportationMode" styleClass="selectBox" styleId="transportationMode">
          <html:option value="ltltl" key="label.ltltl"/>
          <html:option value="parcel" key="label.parcel"/>
        </html:select>
       </td>
      </tr>

	    <tr>
	    <td class="optionTitleBoldRight">
				<input name="Submit" id="Submit" type="submit" value="<fmt:message key="label.search"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="submitSearchForm();">
			</td>
		 
      	    <td class="optionTitleBoldLeft">
				<input name="endOfDayFedexClose" id="endOfDayFedexClose" type="button" value="<fmt:message key="label.endofdayclose"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="submitEndOfDayClose()">
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

	<div id="showStatusArea" style="display:none;z-index:5;">
		<div id="showStatusAreaTitle" class="hd"><fmt:message key="label.endofdayclose"/></div>
		<div id="showStatusAreaBody" class="bd">
		status text
		</div>
	</div>	
	<script type="text/javascript">
		YAHOO.namespace("example.aqua");
		YAHOO.util.Event.addListener(window, "load", init);
	</script>
</div> <!-- close of contentArea -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="action" id="action" value="" type="hidden">
<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>