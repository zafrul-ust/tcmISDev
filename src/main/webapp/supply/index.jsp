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
<tcmis:fontSizeCss currentCss="global.css"/>
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
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>

<title><fmt:message key="supply.home.header"/></title>

</head>
<body BGCOLOR="#FFFFFF" TEXT="#000000">

<!-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure -->
<div class="topNavigation" id="topNavigation">
<TABLE BORDER=0 WIDTH=100% CLASS="moveupmore">
<TR VALIGN="TOP">
<TD WIDTH="200">
<img src="/images/tcmtitlegif.gif" border=0 align="left">
</TD>
<TD ALIGN="right">
<img src="/images/tcmistcmis32.gif" border=0 align="right">
</TD>
</TR>
</TABLE>
<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0" WIDTH="100%" CLASS="moveup">
<TR>
<TD WIDTH="70%" ALIGN="LEFT" HEIGHT="22" CLASS="heading">
 <fmt:message key="supply.home.header"/>
</TD>
<TD WIDTH="30%" ALIGN="RIGHT" HEIGHT="22" CLASS="headingr">
<html:link style="color:#FFFFFF" forward="home">
 <fmt:message key="label.home"/>
</html:link>
</TD>
</TR>
</TABLE>
</div>

<%--<html:form action="/login.do">--%>
<TABLE WIDTH="800" BORDER="0" CLASS="moveup">
<TR VALIGN="TOP">
 <TD BGCOLOR="#E6E8FA" HEIGHT="400">
  <form name="loginForm" method="post" action="/tcmIS/supply/Home?">
  <INPUT TYPE="hidden" NAME="UserAction" VALUE="LOGOUT">
  <TABLE WIDTH="150" BORDER="0" CLASS="moveup">
  <TR>
    <TD>&nbsp;</TD>
  </TR>
  <TR>
    <TD>&nbsp;</TD>
  </TR>
  <TR>
  <TD width="10%" align="CENTER">
   <html:submit property="logOut" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'">
    <fmt:message key="label.logout"/>
   </html:submit>
  </TD>
  </TR>
  <TR>
   <TD width="10%" HEIGHT="50" align="CENTER" valign="BOTTOM">
    <A HREF="/tcmIS/Invoice/ChangePassword?whichHome=supply" STYLE="color:#e86915"><fmt:message key="changepassword.link.message"/></A>
   </TD>
  </TR>
  <TR><TD width="10%" HEIGHT="50" align="CENTER" valign="BOTTOM"><A HREF="/tcmIS/haas/userprofilemain.do?homeLink=supply" STYLE="color:#e86915"><fmt:message key="label.userProfile"/></A></TD></TR>
  <TR><TD>&nbsp;</TD></TR>
  <TR><TD>&nbsp;</TD></TR>
  <TR><TD CLASS="bluel"><FONT SIZE="2"></FONT></TD></TR>
  </TABLE>
 </form>
 </TD>

<%--</html:form>--%>

<TD WIDTH="650" HEIGHT="400" VALIGN="TOP">
<TABLE  BORDER="0" CELLSPACING=1 CELLPADDING=0 WIDTH="100%" CLASS="moveup">
<TR>
<TD WIDTH="550" CLASS="announce" COLSPAN="3">
<html:errors/><BR>
<c:set var="firstname" value='${sessionScope.personnelBean.firstName}'/>
<c:set var="lastname" value='${sessionScope.personnelBean.lastName}'/>
<c:set var="companyid" value='${sessionScope.personnelBean.companyId}'/>

<fmt:message key="label.loggedinas"/>: <B><c:out value="${firstname}" />&nbsp;<c:out value="${lastname}" /></B>
<fmt:message key="hub.home.forcompany"/>
<B>
<c:choose>
  <c:when test="${companyid == 'Radian' || companyid == 'RADIAN'}" >
    Haas TCM
  </c:when>
  <c:otherwise>
    <c:out value="${companyid}" />
  </c:otherwise>
</c:choose>
</B>
</TD>

<TR>

<TD ALIGN="CENTER" BGCOLOR="#E6E8FA" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="33%" height="30">

<A HREF="/tcmIS/supply/purchorder" STYLE="color:#e86915">Purchase Order</A></TD>

<TD ALIGN="CENTER" BGCOLOR="#E6E8FA" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="33%" height="30">

<A HREF="/tcmIS/supply/buyordersmain.do" STYLE="color:#e86915">Buy Orders</A></TD>

<TD ALIGN="CENTER" BGCOLOR="#E6E8FA" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="33%" height="30">

<A HREF="/tcmIS/supply/searchposmain.do" STYLE="color:#e86915">Search POs</A></TD>

</TR>

<TR>

<TD ALIGN="CENTER" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="33%" height="30">

<A HREF="/tcmIS/supply/printpolist.do" STYLE="color:#e86915">Printed PO List</A>

&nbsp;</TD>

<TD ALIGN="CENTER" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="33%" height="30">

<A HREF="/tcmIS/supply/supplierrequest.do" STYLE="color:#e86915">New Supplier Request</A>

&nbsp;</TD>

<TD ALIGN="CENTER" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="33%" height="30">

&nbsp;</TD>

</TR>

<TR><TH WIDTH="550" COLSPAN="4" ALIGN="CENTER" height="30">Accounts Payable Pages</TH></TR>

<TR>

<TD ALIGN="CENTER" BGCOLOR="#E6E8FA" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="33%" height="30">

<A HREF="/tcmIS/Invoice/AccountsPayable" STYLE="color:#e86915" TARGET="accopayab">Accounts Payable</A></TD>

<TD ALIGN="CENTER" BGCOLOR="#E6E8FA" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="33%" height="30">

<A HREF="/tcmIS/accountspayable/supplierinvoicereport.do" STYLE="color:#e86915" TARGET="invoicereport">Invoice Report</A></TD>

<TD ALIGN="CENTER" BGCOLOR="#E6E8FA" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="33%" height="30">

&nbsp;</TD>

</TR>

</TABLE>
<TABLE  BORDER="0" CELLSPACING=1 CELLPADDING=0 WIDTH="100%" CLASS="moveup">
<TR><TH WIDTH="550" COLSPAN="4" ALIGN="CENTER" height="30">Customer Home Pages</TH></TR>
<TR>
<TD ALIGN="CENTER" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="25%"  height="30">
<A HREF="/tcmIS/drs/Register" STYLE="color:#e86915" TARGET="drs">DRS</A></TD>
<TD ALIGN="CENTER" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="25%" height="30">
<A HREF="/tcmIS/ray/Register" STYLE="color:#e86915" TARGET="ray">Raytheon</A></TD>
<TD ALIGN="CENTER" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="25%" height="30">
<A HREF="/tcmIS/seagate/Register" STYLE="color:#e86915" TARGET="seagate">Seagate</A></TD>
<TD ALIGN="CENTER" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="25%" height="30">
<A HREF="/tcmIS/swa/Register" STYLE="color:#e86915" TARGET="swa">Southwest</A></TD>
</TR>
<TR>
<TD ALIGN="CENTER" BGCOLOR="#E6E8FA" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="25%"  height="30">
<A HREF="/tcmIS/bae/Register" STYLE="color:#e86915" TARGET="bae">BAE</A></TD>
<TD ALIGN="CENTER" BGCOLOR="#E6E8FA" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="25%" height="30">
<A HREF="/tcmIS/utc/Register" STYLE="color:#e86915" TARGET="utc">UTC</A></TD>
<TD ALIGN="CENTER" BGCOLOR="#E6E8FA" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="25%" height="30">
<A HREF="/tcmIS/lmco/Register" STYLE="color:#e86915" TARGET="lmco">LMCO</A></TD>
<TD ALIGN="CENTER" BGCOLOR="#E6E8FA" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="25%" height="30">
<A HREF="/tcmIS/sd/Register" STYLE="color:#e86915" TARGET="sd">SD</A></TD>
</TR>
<TR>
<TD ALIGN="CENTER" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="25%"  height="30">
<A HREF="/tcmIS/miller/Register" STYLE="color:#e86915" TARGET="drs">Miller</A></TD>
<TD ALIGN="CENTER" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="25%" height="30">
<A HREF="/tcmIS/ula/Register" STYLE="color:#e86915" TARGET="ula">ULA</A></TD>
<TD ALIGN="CENTER" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="25%" height="30">
<A HREF="/tcmIS/cal/Register" STYLE="color:#e86915" TARGET="cal">CAL</A></TD>
<TD ALIGN="CENTER" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="25%" height="30">
<A HREF="/tcmIS/ael/Register" STYLE="color:#e86915" TARGET="aec">Aero Echo</A></TD>
</TR>
<TR>
<TD ALIGN="CENTER" BGCOLOR="#E6E8FA" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="25%"  height="30">
<A HREF="/tcmIS/gm/Register" STYLE="color:#e86915" TARGET="gm">GM</A></TD>
<TD ALIGN="CENTER" BGCOLOR="#E6E8FA" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="25%" height="30">
<A HREF="/tcmIS/dana/Register" STYLE="color:#e86915" TARGET="dana">Dana</A></TD>
<TD ALIGN="CENTER" BGCOLOR="#E6E8FA" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="25%" height="30">
<A HREF="/tcmIS/doe/Register" STYLE="color:#e86915" TARGET="doe">DOE</A></TD>
<TD ALIGN="CENTER" BGCOLOR="#E6E8FA" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="25%" height="30">
<A HREF="/tcmIS/fec/Register" STYLE="color:#e86915" TARGET="fec">FEC</A></TD>
</TR>

<TR>
<TD ALIGN="CENTER" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="25%" height="30">
<A HREF="/tcmIS/iai/Register" STYLE="color:#e86915" TARGET="iai">IAI</A></TD>
<TD ALIGN="CENTER" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="25%" height="30">
<A HREF="/tcmIS/am/Register" STYLE="color:#e86915" TARGET="am">AM</A></TD>
<TD ALIGN="CENTER" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="25%" height="30">
<A HREF="/tcmIS/gema/Register" STYLE="color:#e86915" TARGET="gema">GEMA</A></TD>
<TD ALIGN="CENTER" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="25%" height="30">
<A HREF="/tcmIS/l3/Register" STYLE="color:#e86915" TARGET="l3">L3</A></TD>
</TR>

<TR>
<TD ALIGN="CENTER" BGCOLOR="#E6E8FA" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="25%"  height="30">
<A HREF="/tcmIS/pge/Register" STYLE="color:#e86915" TARGET="pge">PG & E</A></TD>
<TD ALIGN="CENTER" BGCOLOR="#E6E8FA" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="25%" height="30">
<A HREF="/tcmIS/dcx/Register" STYLE="color:#e86915" TARGET="dcx">DCX</A></TD>
<TD ALIGN="CENTER" BGCOLOR="#E6E8FA" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="25%" height="30">
<A HREF="/tcmIS/gd/Register" STYLE="color:#e86915" TARGET="gd">GD</A></TD>
<TD ALIGN="CENTER" BGCOLOR="#E6E8FA" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="25%" height="30">
<A HREF="/tcmIS/algat/Register" STYLE="color:#e86915" TARGET="algat">ALGAT</A></TD>
</TR>

<TR>
<TD ALIGN="CENTER" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="25%" height="30">
<A HREF="/tcmIS/baz/Register" STYLE="color:#e86915" TARGET="baz">BAZ</A></TD>
<TD ALIGN="CENTER" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="25%" height="30">
<A HREF="/tcmIS/cmm/Register" STYLE="color:#e86915" TARGET="cmm">CMM</A></TD>
<TD ALIGN="CENTER" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="25%" height="30">
<A HREF="/tcmIS/fedco/Register" STYLE="color:#e86915" TARGET="fedco">FEDCO</A></TD>
<TD ALIGN="CENTER" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="25%" height="30">
<A HREF="/tcmIS/imco/Register" STYLE="color:#e86915" TARGET="imco">IMCO</A></TD>
</TR>

<TR>
<TD ALIGN="CENTER" BGCOLOR="#E6E8FA" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="25%"  height="30">
<A HREF="/tcmIS/kanfit/Register" STYLE="color:#e86915" TARGET="kanfit">Kanfit</A></TD>
<TD ALIGN="CENTER" BGCOLOR="#E6E8FA" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="25%" height="30">
<A HREF="/tcmIS/verasun/Register" STYLE="color:#e86915" TARGET="verasun">Verasun</A></TD>
<TD ALIGN="CENTER" BGCOLOR="#E6E8FA" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="25%" height="30">
<A HREF="/tcmIS/nalco/Register" STYLE="color:#e86915" TARGET="nalco">NALCO</A></TD>
<TD ALIGN="CENTER" BGCOLOR="#E6E8FA" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="25%" height="30">
<A HREF="/tcmIS/pepsi/Register" STYLE="color:#e86915" TARGET="pepsi">Pepsi</A></TD>
</TR>

<TR>
<TD ALIGN="CENTER" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="25%" height="30">
<A HREF="/tcmIS/hal/Register" STYLE="color:#e86915" TARGET="hal">HAL</A></TD>
<TD ALIGN="CENTER" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="25%" height="30">
<A HREF="/tcmIS/timken/Register" STYLE="color:#e86915" TARGET="timken">Timken</A></TD>
<TD ALIGN="CENTER" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="25%" height="30">
<A HREF="/tcmIS/maeet/Register" STYLE="color:#e86915" TARGET="maeet">Maeet</A></TD>
<TD ALIGN="CENTER" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="25%" height="30">
<A HREF="/tcmIS/cat/Register" STYLE="color:#e86915" TARGET="cat">Caterpillar</A></TD>
</TR>

<TR>
<TD ALIGN="CENTER" BGCOLOR="#E6E8FA" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="25%"  height="30">
<A HREF="/tcmIS/nrg/Register" STYLE="color:#e86915" TARGET="nrg">NRG</A></TD>
<TD ALIGN="CENTER" BGCOLOR="#E6E8FA" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="25%" height="30">
&nbsp;</TD>
<TD ALIGN="CENTER" BGCOLOR="#E6E8FA" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="25%" height="30">
&nbsp;</TD>
<TD ALIGN="CENTER" BGCOLOR="#E6E8FA" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="25%" height="30">
&nbsp;</TD>
</TR>

</TABLE>

<TABLE  BORDER="0" CELLSPACING=1 CELLPADDING=0 WIDTH="100%" CLASS="moveup">

<TR><TH WIDTH="550" COLSPAN="4" ALIGN="CENTER" height="30">Other Links</TH></TR>

<TR>

<TD ALIGN="CENTER" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="25%" height="30">

<A HREF="/tcmIS/cgihome" STYLE="color:#e86915" TARGET="cgilinks">Miscellaneous links</A></TD>

<TD ALIGN="CENTER" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="25%" height="30">

<A HREF="/tcmIS/hub/minmaxlevelmain.do" STYLE="color:#e86915" TARGET="minmaxlevels">Minmax Levels</A></TD>

<TD ALIGN="CENTER" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="25%" height="30">

&nbsp;</TD>

<TD ALIGN="CENTER" onmouseover="className='menuh1'" onMouseout="className='menu1'" width="25%" height="30">

&nbsp;</TD>

</TR>

</TABLE>


</TD>
</TR>
</TABLE>
</body>
</html:html>
