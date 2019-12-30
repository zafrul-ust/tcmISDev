<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>
<html>
<HEAD>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<LINK REL="SHORTCUT ICON" HREF="http://test.tcmis.com/images/buttons/tcmIS.ico"></LINK>
<META HTTP-EQUIV="Expires" CONTENT="-1">
<TITLE>Reports</TITLE>
<LINK REL="stylesheet" TYPE="text/css" HREF="/stylesheets/global.css"></LINK>

</HEAD>  
<BODY BGCOLOR="#FFFFFF" TEXT="#000000" LINK="#FFFFFF">
<TABLE BORDER=0 WIDTH=100% >
<TR VALIGN="TOP">
<TD WIDTH="200">
<img src="/images/tcmtitlegif.gif" border=0 align="left">
</TD>
<TD ALIGN="right">
<img src="/images/tcmistcmis32.gif" border=0 align="right">
</TD>
</TR>
</Table>

<%@ include file="title.jsp" %>


<TABLE  BORDER="0" CELLSPACING=1 CELLPADDING=0 WIDTH="100%" CLASS="moveup">

<tr align="center">

<TH height="38">Reports</TH>

</tr>

<TR>
<TD ALIGN="CENTER" onmouseover="className='menuh1'" onMouseout="className='menu1'" height="30">
<A HREF="showadhocmaterialmatrixreport.do" STYLE="color:#e86915">Ad Hoc Material Matrix</A>
</TD>
</TR>
<TR>
<TD ALIGN="CENTER" onmouseover="className='menuh1'" onMouseout="className='menu1'" height="30">
<A HREF="showadhocusagereport.do" STYLE="color:#e86915">Ad Hoc Usage</A>
</TD>
</TR>
<TR>
<TD ALIGN="CENTER" onmouseover="className='menuh1'" onMouseout="className='menu1'" height="30">
<A HREF="showadhocwastereport.do" STYLE="color:#e86915">Ad Hoc Waste</A>
</TD>
</TR>
</TABLE>
</html>
