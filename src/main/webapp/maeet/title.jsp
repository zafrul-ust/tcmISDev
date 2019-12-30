<TABLE WIDTH="100%" BGCOLOR=#000066 BORDER="0">
<TR VALIGN="middle">
<TD WIDTH="60%" HEIGHT="20" BGCOLOR=#000066 VALIGN="MIDDLE">
  &nbsp;&nbsp;&nbsp;<B>
<FONT FACE="Arial" SIZE="2" COLOR="#FFFFFF">
  <A HREF="/tcmIS/maeet/Home"><fmt:message key="label.home"/></A>
  |
  <A HREF="/tcmIS/maeet/Msds?limit=yes&noncatalog=yes"><fmt:message key="label.msds"/></A>
  |
  <A HREF="/tcmIS/maeet/Register"><fmt:message key="label.tcmis"/></A>
  |
  <A HREF="/tcmIS/maeet/catalog.do"><fmt:message key="label.catalog"/></A>
  |
  <A HREF="/tcmIS/maeet/ordertracking.do"><fmt:message key="ordertracking.label.title"/></A>
  |
  <A HREF="/tcmIS/maeet/inventory.do"><fmt:message key="label.inventory"/></A>
  |
<c:if test="${sessionScope.FULLNAME != null}" >
  <a href="javascript:openwin();"><fmt:message key="label.help"/></a>
  |</b>
</TD>
<TD WIDTH="40%" HEIGHT="20" BGCOLOR=#000066 VALIGN="MIDDLE" ALIGN="RIGHT">
<FONT FACE="Arial" SIZE="2" COLOR="#FFFFFF">
<B>Logged in as:</B>&nbsp;<c:out value='${sessionScope.FULLNAME}'/>
</FONT>
</c:if>
</FONT>
</TD>
</TR>
</TABLE>
