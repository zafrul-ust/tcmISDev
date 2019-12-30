<table width="100%" bgcolor=#000066 border="0" cellpadding="0" cellspacing="0">
<tr>
<td width="80%" height="20" border="0" cellpadding="0" cellspacing="0">
  &nbsp;&nbsp;&nbsp;
  <font face="Arial" size="2" color="#ffffff">
  <a href="/tcmIS/bae/Home"><fmt:message key="label.home"/></a>
  |
  <a href="/tcmIS/bae/Msds?limit=yes&noncatalog=yes"><fmt:message key="label.msds"/></a>
  |
  <a href="/tcmIS/bae/Register"><fmt:message key="label.tcmis"/></a>
  |
  <a href="/tcmIS/bae/Dropship"><fmt:message key="label.receiving"/></a>
  |
  <a href="/tcmIS/bae/receiptdocumentviewermain.do"><fmt:message key="receiptdocumentviewer.title"/></a>
  |
  <c:if test="${sessionScope.FULLNAME != null}" >
  <a href="javascript:opentcmISHelp();"><fmt:message key="label.help"/></a>
  |
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
