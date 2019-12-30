<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<HTML><HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=UTF-8">
<LINK REL="SHORTCUT ICON" HREF="https://www.tcmis.com/images/buttons/tcmIS.ico"></LINK>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="-1">
<TITLE>Submit Punchout</TITLE>
<LINK REL="stylesheet" TYPE="text/css" HREF="/stylesheets/global.css"></LINK>
<SCRIPT SRC="/clientscripts/iprocurement.js" LANGUAGE="JavaScript">
</SCRIPT>
<script src="/js/common/disableKeys.js" language="JavaScript"></script>
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>

</HEAD>  

<BODY onLoad="submitheform()">

<TABLE BORDER="0" CELLSPACING=1 CELLPADDING=0 WIDTH="100%" CLASS="moveup"><TR>
<TD width="10%" CLASS="announce">
</TD>
<TD width="30%" CLASS="announce"><FONT SIZE="4" FACE="Arial" COLOR="#fc0303"><B>Please wait Checkout in Progress..</B></FONT><BR>
</TD>
</TR></TABLE>

<form  NAME="puchoutiproc" action="${browserPost}" method="post">

<input type="hidden" name="cxml-urlencoded" value="${postBodyUrlUtf8}"></form>
</BODY>

</HTML>