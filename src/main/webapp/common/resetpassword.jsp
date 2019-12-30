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
<meta http-equiv="content-type" content="text/html; charset=iso-8859-1">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss />
<!-- CSS for YUI -->

	<%-- Add any other stylesheets you need for the page here --%>


<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>

<script SRC="/js/common/formchek.js" language="JavaScript"></script>
<script SRC="/js/common/commonutil.js" language="JavaScript"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js" language="JavaScript"></script>
<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<script SRC="/js/common/resetpassword.js" language="JavaScript"></script>
<%-- Add any other javascript you need for the page here --%>

<title>
<fmt:message key="label.resetpassword"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
passwordMatchError:"<fmt:message key="error.password.change"/>",
passwordReuseError:"<fmt:message key="error.password.reuse"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff">

<tcmis:form action="/resetpassword.do" onsubmit="return submitOnlyOnce();">

 <div id="transitPage" style="display: none;text-align:center;">
  <p><br><br><br><fmt:message key="label.pleasewait"/></p>
 </div>
 <div class="interface" id="mainPage">

<%-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure --%>
<div class="topNavigation" id="topNavigation">

</div>

<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="300" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
	    <c:set var="updateStatus" value='${updateStatus}'/>
	    <c:choose>
		    <c:when test="${updateStatus == 'success'}">
          <tr>
            <td width="100%" class="optionTitleBoldCenter">
              <fmt:message key="label.passwordresetsuccess"/>&nbsp;
            </td>
          </tr>
			    <tr>
            <td width="100%" class="optionTitleBoldCenter">
	            <input name="close" id="close" type="button" value="<fmt:message key="label.close"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="closeWindow();">
            </td>
			    </tr>
		    </c:when>
		    <c:otherwise>
          <!-- new password -->
          <tr>
            <td width="20%" class="optionTitleBoldRight">
              <fmt:message key="password.label.passwordnew"/>:&nbsp;
            </td>
            <td width="80%" class="optionTitleBoldLeft">
              <input name="newPassword" id="newPassword" type="password" class="inputBox" value="<c:out value="${param.newPassword}"/>">
            </td>
          </tr>
          <!-- retype new password -->
          <tr>
            <td width="20%" class="optionTitleBoldRight">
              <fmt:message key="password.label.passwordcopy"/>:&nbsp;
            </td>
            <td width="80%" class="optionTitleBoldLeft">
              <input name="copyPassword" id="copyPassword" type="password" class="inputBox" value="<c:out value="${param.copyPassword}"/>">
            </td>
          </tr>
          <!-- search button -->
          <tr>
            <td width="100%" class="optionTitleBoldRight">
              <input name="resetPassword" id="resetPassword" type="submit" class="inputBtns" value="<fmt:message key="label.resetpassword"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "return checkPassword()">
            </td>
            <td width="100%" class="optionTitleBoldLeft">
              <input name="cancel" id="cancel" type="button" value="<fmt:message key="label.cancel"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="closeWindow();">
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
<!-- Search Option Ends -->

<!-- Error Messages Begins -->
<div class="spacerY">&nbsp;
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
<logic:present name="errorBean" scope="request">
   <c:if test="${!empty errorBean.header}" >
      <c:out value="${errorBean.header}"/><br>
   </c:if>
   <c:if test="${!empty errorBean.message1}" >
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="${errorBean.message1}"/><br>
   </c:if>
   <c:if test="${!empty errorBean.message2}" >
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="${errorBean.message2}"/><br>
   </c:if>
   <c:if test="${!empty errorBean.message3}" >
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="${errorBean.message3}"/><br>
   </c:if>
   <c:if test="${!empty errorBean.message4}" >
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="${errorBean.message4}"/><br>
   </c:if>
   <c:if test="${!empty errorBean.message5}" >
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="${errorBean.message5}"/><br>
   </c:if>
    <c:if test="${!empty errorBean.message6}" >
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="${errorBean.message6}"/><br>
   </c:if>
   <c:if test="${!empty errorBean.message7}" >
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="${errorBean.message7}"/><br>
   </c:if>
   <c:if test="${!empty errorBean.message8}" >
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="${errorBean.message8}"/>
   </c:if> 
</logic:present>
</div>
</div>
<!-- Error Messages Ends -->


<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;">
<input name="action" id="action" value="" type="hidden">
<input name="personnelId" id="personnelId" value="<c:out value="${personnelId}"/>" type="hidden">
<html:hidden property="requestedPage" value="${requestScope.requestedPage}"/>
<html:hidden property="requestedURLWithParameters" value="${requestScope.requestedURLWithParameters}"/>
</div>
<!-- Hidden elements end -->

</div> <!-- close of content area -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>

