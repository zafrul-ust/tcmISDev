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

<script src="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js" language="JavaScript"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>

<!-- For Calendar support -->
<%--
<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>
--%>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/hub/addhubbin.js" language="JavaScript"></script>
<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
pleaseselect:"<fmt:message key="label.pleaseselect"/>",
none:"<fmt:message key="label.none"/>",    
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
required:"<fmt:message key="errors.required"/>",
binType:"<fmt:message key="label.bintype"/>",
room:"<fmt:message key="label.room"/>"};
// -->
</script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>

<!-- This is for the YUI, uncomment if you will use this -->
<%--<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>--%>
<script type="text/javascript">
<!--
windowCloseOnEsc = true;

var altHubId = new Array(
		<c:forEach var="hubRoomOvBean" items="${hubRoomOvBeanCollection}" varStatus="status">
		 <c:choose>
		   <c:when test="${status.index > 0}">
		    ,"<c:out value="${status.current.hub}"/>"
		   </c:when>
		   <c:otherwise>
		    "<c:out value="${status.current.hub}"/>"
		   </c:otherwise>
		  </c:choose>
		</c:forEach>
		);

		var altRoomId = new Array();
		var altRoomName = new Array();
		<c:forEach var="hubRoomOvBean" items="${hubRoomOvBeanCollection}" varStatus="status">
		  <c:set var="currentHub" value='${status.current.hub}'/>
		  <c:set var="roomCollection" value='${status.current.roomVar}'/>

		  altRoomId["<c:out value="${currentHub}"/>"] = new Array(""
		  <c:forEach var="vvHubRoomBean" items="${roomCollection}" varStatus="status1">
		    ,"<c:out value="${status1.current.room}"/>"
		  </c:forEach>
		  );

		  altRoomName["<c:out value="${currentHub}"/>"] = new Array("<fmt:message key="label.all"/>"
		  <c:forEach var="vvHubRoomBean" items="${roomCollection}" varStatus="status2">
		    ,"<c:out value="${status2.current.roomDescription}"/>"
		  </c:forEach>
		  );
		</c:forEach>


		var fromBinManagement = false;
		<c:set var="dontShowBody" value='N'/>
		<c:choose>
		<c:when test="${(fromBinManagement eq 'Yes')  and (!empty successMessage)}">
		fromBinManagement = true;
		<c:set var="dontShowBody" value='Y'/>
		</c:when>
		<c:otherwise>
		</c:otherwise>
		</c:choose>
// -->
</script>

<title>
 <fmt:message key="receiving.button.newbin"/>
</title>
</head>

<body onload="showRoomOptions();newBinsWinlose();">
  <div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
 </div>
 <div class="interface" id="mainPage">

<tcmis:form action="/showaddhubbin.do" onsubmit="return submitFrameOnlyOnce();">
<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">

<c:if test="${dontShowBody != 'Y'}">


<c:choose>
<c:when test="${!empty successMessage || !empty errorMessage}" >
  <c:if test="${!empty successMessage}">
   <fmt:message key="hubbin.successmessage"/>
  </c:if>

  <c:if test="${!empty errorMessage}">
   <fmt:message key="hubbin.errormessage"/>
  </c:if>
<br/><br/>
 <html:button property="containerlabels"  styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "cancel()">
   <fmt:message key="label.ok(done)"/>
 </html:button>
 
   <html:errors/>
    ${tcmISError}
 </c:when>
<c:otherwise>
<tr>
<td width="2%" class="optionTitleBoldRight">
<fmt:message key="label.hub"/>:
</td>
<td>
<c:out value="${param.sourceHubName}"/>
<input type="hidden" name="branchPlant" id="branchPlant" value="${branchPlant}"/>
</td>
</tr>
<tr>
<td class="optionTitleBoldRight">
<fmt:message key="label.room"/>:
</td>
     <td class="optionTitleLeft">
          <select class="selectBox"  name="room" id="room">
          </select>
        
&nbsp;
         
<tcmis:inventoryGroupPermission indicator="true" userGroupId="addNewBin" facilityId="${param.sourceHubName}">
    <html:button property="addNewRoom"  styleClass="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onclick= "createnewroom();">
    <fmt:message key="label.addnewroom"/>
 </html:button>
</tcmis:inventoryGroupPermission>  

   </td>  

</tr>
<tr>
<td width="15%" class="optionTitleBoldRight">
<fmt:message key="label.bin"/>:
</td>

<td colspan="2"  class="optionTitleBoldLeft">
<input class="inputBox" type="text" name="bin" id="bin" value="" size="18" maxlength="25"/>
</td>
</tr>

<tr>
<td width="15%" class="optionTitleBoldRight" nowrap>
<fmt:message key="hubbin.label.onsite"/>:
</td>
<td colspan="4" class="optionTitleBoldLeft">

<input name="onSite" id="onSite" type="checkbox" class="radioBtns" value="Y"  checked/>
</td>
</tr>
<tr>
<td width="15%" class="optionTitleBoldRight" nowrap>
<fmt:message key="label.bintype"/>:
</td>
<td colspan="4" class="optionTitleBoldLeft">
<select name="binType" id="binType" class="selectBox">
	<option value=""><fmt:message key="label.pleaseselect"/></option>
	<c:forEach var="bean" items="${vvBinTypes}" varStatus="status2">
	<option value="${status2.current}">${status2.current}</option>
	</c:forEach>
</select>
</td>
</tr>




<tr>
<td colspan="4" class="optionTitleBoldLeft">
  <html:submit property="submitAdd"  styleClass="inputBtns" onclick="return validateForm();" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
     <fmt:message key="label.ok(done)"/>
  </html:submit>
<input name="uploadTemplateBtn" type="button" class="inputBtns" value="<fmt:message key="label.uploadbins"/>" id="uploadTemplateBtn" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "return doUploadTemplate()"/>  	
  <html:submit property="containerlabels"  styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "cancel();return false;">
     <fmt:message key="label.cancel"/>
  </html:submit>
</td>
</tr>
</c:otherwise>
</c:choose>

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
</c:if>
<!-- Search Option Ends -->

<input name="sourceHubName" id="sourceHubName" type="hidden" value="${param.sourceHubName}"/>
<input name="selectedRoom" id="selectedRoom" type="hidden" value="${param.selectedRoom}"/>
<input name="action" id="action" type="hidden" value=""/>
<input name="userAction" id="userAction" type="hidden" value=""/>
<input name="fromBinManagement" id="fromBinManagement" type="hidden" value="${param.fromBinManagement}"/>
<input type="hidden" name="personnelCompanyId"  id="personnelCompanyId" value="${personnelBean.companyId}"/>
</tcmis:form>
</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->

</body>
</html:html>
