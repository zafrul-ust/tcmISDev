<table width="100%" bgcolor=#000066 border="0" cellpadding="0" cellspacing="0">
<tr>
<td width="80%" height="20" border="0" cellpadding="0" cellspacing="0">
  &nbsp;&nbsp;&nbsp;
  <font face="Arial" size="2" color="#ffffff">
  <a href="/tcmIS/ray/Home"><fmt:message key="label.home"/></a>
  |
  <a href="/tcmIS/ray/Msds?limit=yes&noncatalog=yes"><fmt:message key="label.msds"/></a>
  |
  <a href="/tcmIS/ray/Register"><fmt:message key="label.tcmis"/></a>
  |
  <a href="/tcmIS/ray/Dropship"><fmt:message key="label.receiving"/></a>
  |
  <a href="/tcmIS/ray/catalog.do"><fmt:message key="label.catalog"/></a>
  |
  <a href="/tcmIS/ray/ordertrackingmain.do"><fmt:message key="ordertracking.label.title"/></a>    
  |
  <a href="/tcmIS/ray/showuseapprovalstatus.do"><fmt:message key="useapprovalstatus.title"/></a>
  |
  <a href="/tcmIS/ray/workareausagemain.do"><fmt:message key="materialsused.title"/></a>
  |
  <a href="/tcmIS/ray/tsdfwastereceivingmain.do"><fmt:message key="tsdfwastereceiving.title"/></a>
  |
  <a href="/tcmIS/ray/tsdfcontainerreportmain.do"><fmt:message key="tsdfcontainerreport.title"/></a>
  |
  <a href="/tcmIS/ray/manifestreportmain.do"><fmt:message key="manifestreport.title"/></a>
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