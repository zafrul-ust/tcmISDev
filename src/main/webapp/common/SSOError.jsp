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


<title><fmt:message key="label.haastcm"/></title>

</head>

<body bgcolor="#ffffff" onload="resetTransitPage()">

<div class="interface" id="mainPage">
<div class="contentArea">
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your mail table will be 100% -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"></div>
    <div class="dataContent">
     <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData">
     <tr>
      <td width="100%">
<H1>SSO Error</H1>
<h3>Environment</h3>
      <table border="1" cellpadding="4" cellspacing="0">
         <tr><td>Server</td><td><%= java.net.InetAddress.getLocalHost().getHostName() %></td></tr>
         <%java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); %>
		<tr><td>Current Date</td><td><%= df.format(new java.util.Date()) %></td></tr>
	</table>
<h3>HTTP Request Headers Received</h3>
      <table border="1" cellpadding="4" cellspacing="0">
<%
         java.util.Enumeration eNames = request.getHeaderNames();
         while (eNames.hasMoreElements()) {
            String name = "" + eNames.nextElement();
            String value = request.getHeader(name);
      %>
         <tr><td><%= name %></td><td><%= value %></td></tr>
      <%
         }
      %>  
	</table>
<h3>HTTP Request Attributes Received</h3>
      <table border="1" cellpadding="4" cellspacing="0">
<%
		java.util.Enumeration enAttr = request.getAttributeNames(); 
		while(enAttr.hasMoreElements()){
		 String name = "" + enAttr.nextElement();
		 String value = "" + request.getAttribute(name);
      %>
         <tr><td><%= name %></td><td><%= value %></td></tr>
      <%
         }
      %>  
	</table>
<h3>HTTP Request Parameters Received</h3>
      <table border="1" cellpadding="4" cellspacing="0">
<%
		java.util.Enumeration enParam = request.getParameterNames();
		while(enParam.hasMoreElements()){
		 String name = "" + enParam.nextElement();
		 String value = "" +request.getParameter(name);
      %>
         <tr><td><%= name %></td><td><%= value %></td></tr>
      <%
         }
      %>  
	</table>
    </td>
     </tr>
    </table>
  </div>
  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</div>
</td></tr>
</table>
<!-- Search results end -->

</div> <!-- close of contentArea -->
</div> <!-- close of interface -->

</body>
</html:html>
