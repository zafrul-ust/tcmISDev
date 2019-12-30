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

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

<!-- These are for the Grid, uncomment if you are going to use the grid -->

<title>
Check Log
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();

// -->
</script>
</head>

<body bgcolor="#ffffff" onload="document.genericForm.submit()">

<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<%--NEW - removed the search frame and copied the search section here--%>
<div class="contentArea">
<tcmis:form action="/checklogresults.do" target="resultFrame">
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
    <table width="400" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
    <tr>
      <td width="10%" class="optionTitleBoldLeft" nowrap>
      	<input type="radio" name="logname" value="tcmis.log" <c:if test="${ param.logname != 'catalina.out' && param.logname != 'docviewer.log' && param.logname != 'HaasReports.log'}">checked="checked"</c:if>>&nbsp;tcmis.log
    </BR>
      	<input type="radio" name="logname" value="catalina.out" <c:if test="${ param.logname == 'catalina.out' }">checked="checked"</c:if>/>&nbsp;catalina.out
    </BR>
      	<input type="radio" name="logname" value="docviewer.log" <c:if test="${ param.logname == 'docviewer.log' }">checked="checked"</c:if>/>&nbsp;docviewer.log
    </BR>
      	<input type="radio" name="logname" value="HaasReports.log" <c:if test="${ param.logname == 'HaasReports.log' }">checked="checked"</c:if>/>&nbsp;HaasReports.log
 
      </td>     
      <c:set var="lines" value="200"/>
      <c:if test="${!empty param.lines}">
      	<c:set var="lines" value="${param.lines}"/>
      </c:if>
      <td width="70%" class="optionTitleBoldLeft" nowrap>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="text" name="lines" value="${lines}"/>
      </td>
      <td width="10%" class="optionTitleBoldLeft" nowrap="nowrap">
<input type="checkbox" name="order" value="reverse" <c:if test="${ param.order == 'reverse' }">checked="checked"</c:if>/>Reverse</BR>
<input type="checkbox" name="wrap" value="wrap" <c:if test="${ param.wrap == 'wrap' }">checked="checked"</c:if>/>Wrap&nbsp;
      </td>
    </tr>
    <tr>
      <td width="10%" class="optionTitleBoldRight">
     </td>
      <td>
      	<button onclick="document.genericForm.submit();">go</button>
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

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input type="hidden" name="uAction" id="uAction" value=""/>
<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
<input name="searchHeight" id="searchHeight" type="hidden" value="133">
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

<div id="resultGridDiv" style="display:">
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
      </span> --%>
      <span id="updateResultLink" style="display: none">
      </span> 
     </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

    <div class="dataContent">
     <iframe scrolling="no" id="resultFrame" name="resultFrame" width="100%" height="600" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
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