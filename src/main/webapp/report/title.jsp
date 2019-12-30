<TABLE WIDTH="100%" BGCOLOR=#000066 BORDER="0">
<TR VALIGN="middle">
<TD WIDTH="60%" HEIGHT="20" BGCOLOR=#000066 VALIGN="MIDDLE">
  &nbsp;&nbsp;&nbsp;<B>
<FONT FACE="Arial" SIZE="2" COLOR="#000000">
  <A HREF="Home">Home</A>
  |
  <A HREF="Register">tcmIS</A>
  |
  <A HREF="Dropship">Receiving</A>
  |
  <A HREF="Msds?limit=yes&noncatalog=yes">MSDS</A>
  |
  <A HREF="catalog.do">Catalog</A>
  |
  <A HREF="ordertracking.do">Order Tracking</A>
  |
  <A HREF="inventory.do">Inventory</A>
  |
  <A HREF="showreports.do">Reports</A>
  |
  <A HREF="reportlogout.do">Logout</A>
  |

<c:if test="${sessionScope.FULLNAME != null}" >
  <A HREF="javascript:openwin();">Help</A>
  |</B>
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
