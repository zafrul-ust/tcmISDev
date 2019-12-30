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

<script src="/js/hub/itemscanupload.js" language="JavaScript"></script>

<%-- For Calendar support --%>
<%--
<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>
--%>

<%-- Add any other javascript you need for the page here --%>


<%-- These are for the Grid, uncomment if you are going to use the grid --%>
<%--<script src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>

<%-- This is for the YUI, uncomment if you will use this --%>
<%--<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>--%>

<title>
<fmt:message key="itemcountsacnupload.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff">

<tcmis:form action="/itemcountscanupload.do" onsubmit="return submitOnlyOnce();" enctype="multipart/form-data">

<div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
 <br><br><br><fmt:message key="label.pleasewait"/>
</div>
<div class="interface" id="mainPage">

<%-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure --%>
<div class="topNavigation" id="topNavigation">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td width="200">
<img src="/images/tcmtitlegif.gif" align="left">
</td>
<td>
<img src="/images/tcmistcmis32.gif" align="right">
</td>
</tr>
</table>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td width="70%" class="headingl">
<fmt:message key="itemcountsacnupload.title"/>
</td>
<td width="30%" class="headingr">
<html:link style="color:#FFFFFF" forward="home">
 <fmt:message key="label.home"/>
</html:link>
</td>
</tr>
</table>
</div>

<div class="contentArea">

<c:set var="errorMessage" value='${errorMessage}'/>
<c:set var="uploadSucess" value='${uploadSucess}'/>

<c:choose>
<c:when test="${!empty uploadSucess}" >
<br><br><fmt:message key="inventorycountupload.successmessage"/>
</c:when>
<c:otherwise>

<c:if test="${!empty errorMessage}" >
<fmt:message key="inventorycountupload.errormessage"/><br>
<c:out value="${errorMessage}"/>
</c:if>

<table id="searchMaskTable" width="800" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
   <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">
    <tr>
      <th width="50%"><fmt:message key="scannerupload.onyourpc"/></th>
      <th width="50%"><fmt:message key="scannerupload.onyourscanner"/></th>
    </tr>
    <tr>
      <td class="lightblue"><fmt:message key="scannerupload.startmcllink"/><img src="/images/mcllink.gif"></td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td class="lightblue"><fmt:message key="scannerupload.turnonscanner"/></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td class="lightblue"><fmt:message key="scannerupload.logtoscanner"/></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td class="lightblue"><fmt:message key="scannerupload.pressesc"/></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td class="lightblue"><fmt:message key="scannerupload.placescannerincradle"/></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td class="lightblue"><fmt:message key="scannerupload.selectfile"/></td>
    </tr>
    <tr>
      <td class="lightblue"><fmt:message key="scannerupload.browsetofile"/></td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td class="lightblue"><fmt:message key="scannerupload.selectitemcountfile"/></td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td class="lightblue"><fmt:message key="scannerupload.openbutton"/></td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td class="lightblue"><fmt:message key="scannerupload.processbutton"/></td>
      <td>&nbsp;</td>
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

<!-- Error Messages Begins -->
<div class="spacerY">&nbsp;
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
<!-- Error Messages Ends -->

<table id="searchMaskTable" width="700" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
   <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
    <tr>
     <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.file"/>:</td>
     <td width="85%" class="optionTitleBoldLeft">
      <html:file property="theFile" value="c:\scannerdownload\itemcount.csv" size="50" styleClass="HEADER"/>
     </td>
    </tr>
    <tr>
     <td width="5%" colspan="2" class="optionTitleBoldLeft">C:\scannerdownload\itemcount.csv</td>
    </tr>
    <tr>
     <td width="5%" colspan="2" class="optionTitleBoldLeft">
       <input name="submitSave" id="submitSave" type="submit" class="inputBtns" value="<fmt:message key="label.process"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="">
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

</c:otherwise>
</c:choose>

</div> <!-- close of contentArea -->
</div> <!-- close of interface -->

</tcmis:form>
</DIV>
</BODY>
</html:html>