<table width="100%" class="clientnavigation" border="0" cellpadding="0" cellspacing="0">
<tr>
<td width="80%" height="20">
  <a href="Home"><fmt:message key="label.home"/></a>
  |
  <a href="Msds?limit=yes&noncatalog=yes"><fmt:message key="label.msds"/></a>
  |
  <a href="Register"><fmt:message key="label.tcmis"/></a>

  <c:set var="module">
   <tcmis:module/>
  </c:set>

  <c:if test='${module!="seagate" || module!="swa" || module!="sd" || module!="ael" ||
                module!="am" || module!="gema" || module!="pge" || module!="dcx" || module!="algat" ||
                module!="baz" || module!="cmm" || module!="fedco" || module!="imco" || module!="kanfit" ||
                module!="verasun"}'>
   |
   <a href="dropshipreceivingmain.do"><fmt:message key="label.receiving"/></a>
  </c:if>

  <c:if test='${module=="bae"}'>
  |
  <a href="receiptdocumentviewermain.do"><fmt:message key="receiptdocumentviewer.title"/></a>
  </c:if>

  <c:if test='${module=="ula"}'>
  |
  <a href="cabinventory?"><fmt:message key="label.cabinetinventory"/></a>
  </c:if>

  <c:if test='${module=="cal"}'>
  |
  <a href="formattedscaqmdreport.do"><fmt:message key="formattedscaqmdreport"/></a>
  </c:if>

  <c:if test='${module=="dana"}'>
  |
  <a href="showmonthlyinventorydetail.do"><fmt:message key="monthlyinventorydetail.title"/></a>
  |
  <a href="showpeiprojectlist.do"><fmt:message key="peiProjects"/></a>
  </c:if>

  <c:if test='${module=="iai" || module=="ray" || module=="pge" || module=="dcx" || module=="doe" ||
                module=="algat" || module=="baz" || module=="cmm" || module=="fedco" ||
                module=="imco" || module=="kanfit" || module=="nalco" || module=="pepsi"}'>
   |
   <a href="catalog.do"><fmt:message key="label.catalog"/></a>
  </c:if>

   |
   <a href="ordertrackingmain.do"><fmt:message key="ordertracking.label.title"/></a>

  <c:if test='${module=="iai" || module=="pge" || module=="dcx" || module=="algat" ||
                module=="baz" || module=="cmm" || module=="fedco" || module=="imco" ||
                module=="kanfit" || module=="verasun" || module=="nalco" || module=="pepsi"}'>
   |
   <a href="inventorymain.do"><fmt:message key="label.inventory"/></a>
  </c:if>

  <c:if test='${module=="ray" || module=="doe"}'>
   |
   <a href="showuseapprovalstatus.do"><fmt:message key="useapprovalstatus.title"/></a>
  </c:if>

  <c:if test='${module=="ray"}'>
   |
   <a href="workareausagemain.do"><fmt:message key="materialsused.title"/></a>
   |
   <a href="tsdfwastereceivingmain.do"><fmt:message key="tsdfwastereceiving.title"/></a>
   |
   <a href="tsdfcontainerreportmain.do"><fmt:message key="tsdfcontainerreport.title"/></a>
   |
   <a href="manifestreportmain.do"><fmt:message key="manifestreport.title"/></a>
   |
   <a href="catalogspecmain.do"><fmt:message key="catalogspec.title"/></a>
  </c:if>

  <c:if test="${sessionScope.personnelBean != null}" >
   |
   <a href="javascript:opentcmISHelp();"><fmt:message key="label.help"/></a>
  </c:if>
</td>

<td width="20%" height="20" class="alignRight">
<c:if test="${sessionScope.personnelBean != null}" >
 <span class="optionTitleBoldRight"><fmt:message key="label.loggedinas"/>:&nbsp;</span>
 <span class="optionTitleRight"><c:out value='${sessionScope.personnelBean.firstName}'/>&nbsp;<c:out value='${sessionScope.personnelBean.lastName}'/></span>
</c:if>
</td>
</tr>
</table>
