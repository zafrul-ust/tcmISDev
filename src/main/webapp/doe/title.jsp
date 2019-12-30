<table width="100%" bgcolor=#000066 border="0" cellpadding="0" cellspacing="0">
<tr>
<td width="80%" height="20" border="0" cellpadding="0" cellspacing="0">
  &nbsp;&nbsp;&nbsp;
  <font face="Arial" size="2" color="#ffffff">
  <a href="/tcmIS/doe/Home"><fmt:message key="label.home"/></a>
  |
  <a href="/tcmIS/doe/Msds?limit=yes&noncatalog=yes"><fmt:message key="label.msds"/></a>
  |
  <a href="/tcmIS/doe/Register"><fmt:message key="label.tcmis"/></a>
  |
  <a href="/tcmIS/doe/dropshipreceivingmain.do"><fmt:message key="label.receiving"/></a>
  |
  <a href="/tcmIS/doe/catalog.do"><fmt:message key="label.catalog"/></a>
  |
  <a href="/tcmIS/doe/ordertrackingmain.do"><fmt:message key="ordertracking.label.title"/></a>
  |
  <a href="/tcmIS/doe/showuseapprovalstatus.do"><fmt:message key="useapprovalstatus.title"/></a>
  |
  <c:if test="${sessionScope.FULLNAME != null}" >
  <a href="javascript:opentcmISHelp();"><fmt:message key="label.help"/></a>
</td>
<td width="20%" height="20" align="right">
<font face="Arial" size="2" color="#ffffff">
 <fmt:message key="label.loggedinas"/>:&nbsp;<c:out value='${sessionScope.FULLNAME}'/>
</font>
</c:if>
</font>
</td>
</tr>
</table>
