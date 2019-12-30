<TABLE WIDTH="100%" BGCOLOR=#000066 BORDER="0">
<TR VALIGN="middle">
<TD WIDTH="60%" HEIGHT="20" BGCOLOR=#000066 VALIGN="MIDDLE">
  &nbsp;&nbsp;&nbsp;<B>
<FONT FACE="Arial" SIZE="2" COLOR="#FFFFFF">
  <A HREF="/tcmIS/dcx/Home">Home</A>
  |
  <A HREF="/tcmIS/dcx/Msds?limit=yes&noncatalog=yes">MSDS</A>
  |
  <A HREF="/tcmIS/dcx/Register">tcmIS</A>
  |
  <A HREF="/tcmIS/dcx/catalog.do">Catalog</A>
  |
  <A HREF="/tcmIS/dcx/ordertracking.do">Order Tracking</A>
  |
  <A HREF="/tcmIS/dcx/inventory.do">Inventory</A>
  |
<c:if test="${sessionScope.FULLNAME != null}" >
  <A HREF="javascript:openwin();">Help</A>
  |
</TD>
<TD WIDTH="40%" HEIGHT="20" BGCOLOR=#000066 VALIGN="MIDDLE" ALIGN="RIGHT">
<FONT FACE="Arial" SIZE="2" COLOR="#FFFFFF">
<B>Logged in as:</B>&nbsp;<c:out value='${sessionScope.FULLNAME}'/>
</FONT>
</c:if>
</FONT>
</B>
</TD>
</TR>
</TABLE>
