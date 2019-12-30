<TABLE WIDTH="100%" BGCOLOR=#000066 BORDER="0">
<TR VALIGN="middle">
<TD WIDTH="60%" HEIGHT="20" BGCOLOR=#000066 VALIGN="MIDDLE">
  &nbsp;&nbsp;&nbsp;<B><FONT FACE="Arial" SIZE="2" COLOR="#FFFFFF">
  <A HREF="/tcmIS/dana/Home">Home</A>
  |
  <A HREF="/tcmIS/dana/Msds?limit=yes&noncatalog=yes">MSDS</A>
  |
  <A HREF="/tcmIS/dana/Register">tcmIS</A>
  |
  <A HREF="/tcmIS/dana/Dropship">Receiving</A>
  |
  <A HREF="/tcmIS/dana/showmonthlyinventorydetail.do">Monthly Inventory Detail</A>
  |
  <A HREF="/tcmIS/dana/showpeiprojectlist.do">Projects</A>
  |
  <A HREF="javascript:openwin();">Help</A>
  |</FONT></B>
</TD>
<TD WIDTH="40%" HEIGHT="20" BGCOLOR=#000066 VALIGN="MIDDLE" ALIGN="RIGHT">
<FONT FACE="Arial" SIZE="2" COLOR="#FFFFFF">
<c:if test="${sessionScope.FULLNAME != null}" >
<B>Logged in as:</B>&nbsp;<c:out value='${sessionScope.FULLNAME}'/>
</c:if>
</FONT>
</TD>
</TR>
</TABLE>
