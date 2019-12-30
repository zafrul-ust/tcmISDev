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
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
<!-- Add any other stylesheets you need for the page here -->

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<script type="text/javascript" src="/js/common/resultiframeresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<script type="text/javascript" src="/js/hub/receivingqc.js"></script>

<script language="JavaScript" type="text/javascript">
<%-- add all the javascript messages here, this for internationalization of client side javascript messages --%>
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};

var valueSent = false;
function resetBack() {
	try {
		if( !valueSent ) {
			opener.cancelRootCauseCallback(${param.rowNumber},'${param.prevLotStatus}');
		}
	}catch(ex){}
}

</script>

<title>
 <fmt:message key="terminalstatusrootcause.title"/>
</title>

</head>

<body bgcolor="#ffffff" onunload="resetBack()">

<tcmis:form action="/terminalstatusrootcause.do" onsubmit="return submitOnlyOnce();" >	

 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
 </div>
 <div class="interface" id="mainPage">

<%-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure --%>
<div class="topNavigation" id="topNavigation">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td width="70%" class="headingl">
<fmt:message key="terminalstatusrootcause.title"/>:</td>
<td width="30%" class="headingr">&nbsp;
</td>
</tr>
</table>
</div>

<div class="contentArea">

<%-- Search Option Begins --%>
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <%-- Insert all the search option within this div --%>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
    <tr>
    <td width="15%" class="optionTitleBoldRight">
    <fmt:message key="terminalstatusrootcause.label.rootcause"/>:
    </td>
    <td width="30%" class="optionTitleLeft">
     <select name="rootCause" id="rootCause" class="selectBox">
       <c:forEach var="vvLotStatusRootCauseBean" items="${rootCauseCollection}" varStatus="status">
        <option value="<c:out value="${status.current.rootCause}"/>" selected><c:out value="${status.current.rootCauseTitle}"/></option>
      </c:forEach>
     </select>
    </td>
    </tr>

    <tr>
    <td width="15%" class="optionTitleBoldRight">
    <fmt:message key="terminalstatusrootcause.label.responsiblecompany"/>:
    </td>

    <td width="30%" class="optionTitleLeft">
     <select name="rootCauseCompany" id="rootCauseCompany" class="selectBox">
       <c:forEach var="companyBean" items="${companyIdsCollection}" varStatus="status">
        <option value="<c:out value="${status.current.companyId}"/>" selected><c:out value="${status.current.companyName}"/></option>
      </c:forEach>
     </select>
    </td>
    </tr>

    <tr>
    <td width="15%" class="optionTitleBoldRight">
    <fmt:message key="terminalstatusrootcause.label.justification"/>:
    </td>
    <td width="30%" class="optionTitleLeft">
    <textarea name="rootCauseNotes" id="rootCauseNotes" rows="4" cols="50" class="inputBox"></textarea>
    </td>
    </tr>

    <tr>
    <td width="15%" class="optionTitleBoldCenter">
       <html:button property="submitReverse" styleId="submitReverse" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="sendTerminalRootCauseValues()">
         <fmt:message key="label.ok"/>
       </html:button>
    </td>

    <td width="30%" class="optionTitleBoldCenter">
     <html:button property="containerlabels" styleId="containerlabels" styleClass="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="cancel()">
       <fmt:message key="label.cancel"/>
     </html:button>
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
<%-- Search Option Ends --%>

<div class="spacerY">&nbsp;</div>

<%-- Error Messages Begins --%>
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
<%-- Error Messages Ends --%>

<%-- Hidden element start --%>
 <div id="hiddenElements" style="display: none;">
   <input type="hidden" name="rowNumber" id="rowNumber" value="<c:out value="${rowNumber}"/>">
   <input type="hidden" name="lotStatus" id="lotStatus" value="<c:out value="${lotStatus}"/>">
 </div>
<%-- Hidden elements end --%>

</div> <%-- close of contentArea --%>

<%-- Footer message start --%>
 <div class="messageBar">&nbsp;</div>
<%-- Footer message end --%>

</div> <%-- close of interface --%>

</tcmis:form>
</body>
</html:html>