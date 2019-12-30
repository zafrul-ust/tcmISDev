<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html:html lang="true">
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=iso-8859-1">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="-1">
<LINK REL="SHORTCUT ICON" HREF="https://www.tcmis.com/images/buttons/tcmIS.ico"></LINK>
<LINK REL="stylesheet" TYPE="text/css" HREF="/css/clientpages.css"></LINK>
<SCRIPT SRC="/js/catalog.js" LANGUAGE="JavaScript"></SCRIPT>
<SCRIPT SRC="/js/cal/catalog.js" LANGUAGE="JavaScript"></SCRIPT>
<LINK REL="stylesheet" TYPE="text/css" HREF="/css/rightclick.css"></LINK>
<SCRIPT SRC="/js/rightclick.js" LANGUAGE="JavaScript"></SCRIPT>

<title>
<fmt:message key="label.catalog"/>
</title>

<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">
<!--
var altFacilityId = new Array(
<c:forEach var="myWorkareaFacilityViewBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,"<c:out value="${status.current.facilityId}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status.current.facilityId}"/>"
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altFacilityName = new Array(
<c:forEach var="myWorkareaFacilityViewBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status">
 <c:choose>
   <c:when test="${status.index > 0}">
    ,"<c:out value="${status.current.facilityName}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status.current.facilityName}"/>"
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altApplication = new Array();
<c:forEach var="myWorkareaFacilityViewBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status">
<c:set var="currentFacility" value='${status.current.facilityId}'/>
<c:set var="applicationBeanCollection" value='${status.current.applicationBeanCollection}'/>

<c:set var="applicationCount" value='${0}'/>
altApplication["<c:out value="${currentFacility}"/>"] = new Array(
 <c:forEach var="facLocAppBean" items="${applicationBeanCollection}" varStatus="status1">
<c:set var="currentStatus"  value='${status1.current.status}'/>
<c:if test="${currentStatus == 'A'}">
<c:set var="applicationCount" value='${applicationCount+1}'/>
 <c:choose>
   <c:when test="${applicationCount > 1}">
    ,"<c:out value="${status1.current.application}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status1.current.application}"/>"
   </c:otherwise>
  </c:choose>
</c:if>
 </c:forEach>
);
 </c:forEach>

<%--,"<c:out value="${status1.current.application}"/>"
  </c:forEach>
  );
 </c:forEach> --%>

var altApplicationDesc = new Array();
<c:forEach var="myWorkareaFacilityViewBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status">
<c:set var="currentFacility" value='${status.current.facilityId}'/>
<c:set var="applicationBeanCollection" value='${status.current.applicationBeanCollection}'/>

<c:set var="applicationCount" value='${0}'/>
altApplicationDesc["<c:out value="${currentFacility}"/>"] = new Array(
 <c:forEach var="facLocAppBean" items="${applicationBeanCollection}" varStatus="status1">
 <c:set var="currentStatus"  value='${status1.current.status}'/>
 <c:if test="${currentStatus == 'A'}">
 <c:set var="applicationCount" value='${applicationCount+1}'/>
 <c:choose>
   <c:when test="${applicationCount > 1}">
    ,"<c:out value="${status1.current.applicationDesc}"/>"
   </c:when>
   <c:otherwise>
    "<c:out value="${status1.current.applicationDesc}"/>"
   </c:otherwise>
  </c:choose>
 </c:if>
 </c:forEach>
);
 </c:forEach>

<%--,"<c:out value="${status1.current.applicationDesc}"/>"
  </c:forEach>
  );
 </c:forEach>--%>
var menuskin = "skin200"; // skin0, or skin1
var display_url = 0; // Show URLs in status bar?
// -->
</SCRIPT>

</HEAD>

<c:set var="doexcelpopup" value='${doexcelpopup}'/>
<c:set var="facilityOrAllCatalog" value='${param.facilityOrAllCatalog}'/>
<c:set var="buttonCreateExcel" value='${param.buttonCreateExcel}'/>
<c:set var="buttonPrint" value='${param.buttonPrint}'/>
<c:set var="filePath" value='${sessionScope.filePath}'/>

<c:choose>
  <c:when test="${!empty doexcelpopup && !empty buttonCreateExcel}" >
    <BODY BGCOLOR="#FFFFFF" TEXT="#000000" LINK="#FFFFFF" ALINK="" VLINK="#FFFF66" onLoad="doexcelpopup()">
  </c:when>
  <c:when test="${!empty doexcelpopup && !empty buttonPrint}" >
    <BODY BGCOLOR="#FFFFFF" TEXT="#000000" LINK="#FFFFFF" ALINK="" VLINK="#FFFF66" onLoad="doprintpopup('<c:out value="${filePath}" />')">
  </c:when>
  <c:otherwise>
   <BODY BGCOLOR="#FFFFFF" TEXT="#000000" LINK="#FFFFFF" ALINK="" VLINK="#FFFF66">
  </c:otherwise>
</c:choose>

<TABLE BORDER=0 WIDTH=100% >
<TR VALIGN="TOP">
<TD WIDTH="200">
<img src="/images/tcmtitlegif.gif" border=0 align="left">
</TD>
<TD ALIGN="right">
<img src="/images/catalog.gif" border=0 align="right">
</TD>
</TR>
</Table>

<%@ include file="title.jsp" %>

<%--
<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0" WIDTH="100%" CLASS="moveup">
<TR><TD WIDTH="70%" ALIGN="LEFT" HEIGHT="22" CLASS="heading">
<B><fmt:message key="label.catalog"/></B>
</TD>
<TD WIDTH="30%" ALIGN="RIGHT" HEIGHT="22" CLASS="headingr">
<A HREF="/tcmIS/Hub/Home" STYLE="color:#FFFFFF"><fmt:message key="label.home"/></A>
</TD>
</TR>
</TABLE>
--%>

<DIV ID="TRANSITPAGE" STYLE="display: none;">

<P><BR><BR><BR></P><center><font face=Arial SIZE=2><B><fmt:message key="label.pleasewait"/></B></FONT></center>

</DIV>

<DIV ID="MAINPAGE" STYLE="">
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">
<!--
var rowId  =  document.getElementById("MAINPAGE");
rowId.attachEvent('onmouseup',function () {
        makeRightClickNormal();});
// -->
</SCRIPT>

<tcmis:form action="/catalog.do" onsubmit="return SubmitOnlyOnce();">

<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="3" WIDTH="100%" CLASS="moveup">

<TR>
<TD WIDTH="5%" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="label.facility"/>:</B>
</TD>

<TD WIDTH="15%" CLASS="announce">
<c:set var="selectedFacilityId" value='${param.facilityId}'/>
<select name="facilityId" onchange="facilityChanged()">

  <c:forEach var="myWorkareaFacilityViewBean" items="${myWorkareaFacilityViewBeanCollection}" varStatus="status">
    <c:set var="currentFacilityId" value='${status.current.facilityId}'/>
    <c:choose>
      <c:when test="${empty selectedFacilityId}" >
        <c:set var="selectedFacilityId" value='${currentFacilityId}' />
        <c:set var="applicationSelectBeanCollection" value='${status.current.applicationBeanCollection}'/>
      </c:when>
      <c:when test="${currentFacilityId == selectedFacilityId}" >
        <c:set var="applicationSelectBeanCollection" value='${status.current.applicationBeanCollection}'/>
      </c:when>
    </c:choose>

    <c:choose>
      <c:when test="${currentFacilityId == selectedFacilityId}">
        <option value="<c:out value="${currentFacilityId}"/>" selected><c:out value="${status.current.facilityId}"/></option>
      </c:when>
      <c:otherwise>
        <option value="<c:out value="${currentFacilityId}"/>"><c:out value="${status.current.facilityId}"/></option>
      </c:otherwise>
    </c:choose>
  </c:forEach>
</select>
</TD>

<INPUT TYPE="hidden" NAME="selectedFacilityId" value="<c:out value="${selectedFacilityId}"/>" >

<c:if test="${empty facilityOrAllCatalog}">
<c:set var="selectedActiveForFacility" value='${"checked"}' />
</c:if>
<%--<c:out value="${selectedActiveForFacility}"/>--%>

<TD WIDTH="10%" ALIGN="LEFT" CLASS="announce">
<%--
<html:radio property="facilityOrAllCatalog" onchange="facilityOrAllCatalogChanged()" value=""/>
<B><fmt:message key="label.activeforFacility"/></B>
--%>
<html:hidden property="facilityOrAllCatalog" value="true"/>
&nbsp;
</TD>

<TD WIDTH="5%" ALIGN="RIGHT" CLASS="announce">
<%--
<html:checkbox property="workAreaApprovedOnly" onchange="workAreaApprovedChanged()" value="Y"/>
--%>
<html:hidden property="workAreaApprovedOnly" value=""/>
&nbsp;
</TD>

<TD ALIGN="LEFT" WIDTH="15%">
<%--
<B><fmt:message key="catalog.label.workAreaApprovedOnly"/></B>
--%>
&nbsp;
</TD>

</TR>
<TR>

<TD WIDTH="5%" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="label.workarea"/>:</B>
</TD>

<TD  ALIGN="LEFT" WIDTH="15%">
<c:set var="selectedApplicationId" value='${param.applicationId}'/>
<c:if test="${empty selectedApplicationId}" >
  <c:set var="selectedApplicationId" value="All"/>
</c:if>
<select name="applicationId" onchange="workAreaChanged()">
 <c:choose>
    <c:when test="${selectedApplicationId == 'All'}">
      <option value="All" selected>Please Select</option>
    </c:when>
    <c:otherwise>
      <option value="All">Please Select</option>
    </c:otherwise>
  </c:choose>

  <c:set var="applicationCount" value='${0}'/>
  <c:forEach var="facLocAppBean" items="${applicationSelectBeanCollection}" varStatus="status">
    <c:set var="currentApplicationId" value='${status.current.application}'/>
    <c:set var="currentStatus"  value='${status.current.status}'/>
    <c:if test="${currentStatus == 'A'}">
    <c:set var="applicationCount" value='${applicationCount+1}'/>
    <c:choose>
      <c:when test="${currentApplicationId == selectedApplicationId}">
        <option value="<c:out value="${currentApplicationId}"/>" selected><c:out value="${status.current.applicationDesc}"/></option>
      </c:when>
      <c:otherwise>
        <option value="<c:out value="${currentApplicationId}"/>"><c:out value="${status.current.applicationDesc}"/></option>
      </c:otherwise>
    </c:choose>
   </c:if>
  </c:forEach>
</select>
</TD>

<TD WIDTH="10%" ALIGN="LEFT" CLASS="announce">
<%--<html:radio property="facilityOrAllCatalog" onchange="facilityOrAllCatalogChanged()" value="All Catalogs"/>
<B><fmt:message key="label.allcatalogs"/></B>--%>
</TD>

<TD WIDTH="5%" ALIGN="RIGHT" CLASS="announce">
<%--<html:checkbox property="activeOnly" value="Y"/>--%>
</TD>

<TD WIDTH="15%" ALIGN="LEFT" CLASS="announce">
<%--<B><fmt:message key="label.activeOnly"/></B>--%>
</TD>

</TR>

<TR>
<TD WIDTH="5%" ALIGN="RIGHT" CLASS="announce">
<B><fmt:message key="label.search"/>:</B>
</TD>

<TD WIDTH="15%" ALIGN="LEFT" CLASS="announce">
<INPUT CLASS="HEADER" TYPE="text" NAME="searchText" value="<c:out value="${param.searchText}"/>" size="30">
</TD>

<TD WIDTH="10%" CLASS="announce">
<html:submit property="submitSearch" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'">
     <fmt:message key="label.search"/>
</html:submit>
</TD>

<TD WIDTH="10%" ALIGN="LEFT" CLASS="announce">
<html:submit property="buttonCreateExcel" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'">
     <fmt:message key="label.createexcelfile"/>
</html:submit>
</TD>

<TD WIDTH="5%" CLASS="announce">
<html:submit property="buttonPrint" styleClass="SUBMIT" onmouseover="className='SUBMITHOVER'" onmouseout="className='SUBMIT'">
     <fmt:message key="label.printlabels"/>
</html:submit>
</TD>

</TR>
</TABLE>

<TABLE BORDER="0" CELLSPACING=1 CELLPADDING=0 WIDTH="100%" CLASS="moveup">
<TR>
<TD width="10%" CLASS="announce">
</TD>
<TD width="30%" CLASS="announce">
<html:errors/>
<c:set var="submitSearch" value='${param.submitSearch}'/>
 <c:if test="${empty submitSearch && empty buttonCreateExcel && empty buttonPrint}">
  <I><fmt:message key="hub.proceed"/></I>
 </c:if>
<BR>
</TD>
</TR>
</TABLE>

</DIV>
<DIV ID="RESULTSPAGE" STYLE="">

<TABLE BORDER="0" BGCOLOR="#a2a2a2" CELLSPACING="1" CELLPADDING="2" WIDTH="100%">
<c:set var="colorClass" value=''/>
<c:set var="dataCount" value='${0}'/>

<c:forEach var="prCatalogScreenSearchBean" items="${prCatalogScreenSearchBeanCollection}" varStatus="status">
<c:set var="dataCount" value='${dataCount+1}'/>

<c:if test="${status.index % 10 == 0}">
<tr align="center">
<TH width="2%"  height="38"><fmt:message key="label.print"/><BR><INPUT TYPE="checkbox" value="" onClick="checkall('printCheckBox')" NAME="chkAll"></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.catalog"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.part"/></TH>
<TH width="15%" CLASS="results" height="38"><fmt:message key="label.description"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.type"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.price"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="catalog.label.shelflife"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="catalog.label.unitOfSaleQuantityPerEach"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.partuom"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.status"/></TH>
<%--<TH width="5%" CLASS="results" height="38"><fmt:message key="catalog.label.numperpart"/></TH>--%>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.item"/></TH>
<TH width="15%" CLASS="results" height="38"><fmt:message key="inventory.label.componentdescription"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="inventory.label.packaging"/></TH>
<TH width="5%" CLASS="results" height="38"><fmt:message key="label.grade"/></TH>
<TH width="10%" CLASS="results" height="38"><fmt:message key="label.manufacturer"/></TH>
<TH width="10%" CLASS="results" height="38"><fmt:message key="label.mfgpartno"/></TH>
</tr>
</c:if>

<c:choose>
  <c:when test="${status.index % 2 == 0}" >
   <c:set var="colorClass" value='blue'/>
  </c:when>
  <c:otherwise>
   <c:set var="colorClass" value='white'/>
  </c:otherwise>
</c:choose>

<TR align="center" CLASS="<c:out value="${colorClass}"/>" ID="rowId<c:out value="${status.index}"/>">
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">
<!--
var rowId  =  document.getElementById("rowId<c:out value="${status.index}"/>");
rowId.attachEvent('onmouseup',function () {
        catchevent('<c:out value="${status.index}"/>');});
// -->
</SCRIPT>

<c:set var="itemCollection"  value='${status.current.itemCollection}'/>
<bean:size id="listSize" name="itemCollection"/>
<c:set var="mainRowSpan" value='${status.current.rowSpan}' />

<INPUT TYPE="hidden" NAME="colorClass<c:out value="${status.index}"/>" value="<c:out value="${colorClass}"/>" >
<INPUT TYPE="hidden" NAME="childRowsSize<c:out value="${status.index}"/>" value="<c:out value="${listSize}"/>" >

<INPUT TYPE="hidden" NAME="catPartNo" value="<c:out value="${status.current.catPartNo}"/>" >
<INPUT TYPE="hidden" NAME="partDescription" value="<c:out value="${status.current.partDescription}"/>" >


<INPUT TYPE="hidden" NAME="partGroupNo<c:out value="${status.index}"/>" value="<c:out value="${status.current.partGroupNo}"/>" >
<INPUT TYPE="hidden" NAME="catalogId<c:out value="${status.index}"/>" value="<c:out value="${status.current.catalogId}"/>" >


<TD width="2%" rowspan="<c:out value="${mainRowSpan}"/>"><INPUT TYPE="checkbox" NAME="printCheckBox" ID="printCheckBox<c:out value="${status.index}"/>" value="<c:out value="${status.current.catPartNo}"/>"></TD>
<TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.catalogId}"/></TD>
<TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.catPartNo}"/></TD>
<TD width="15%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.partDescription}"/></TD>
<TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.stockingMethod}"/></TD>

<c:set var="finalPrice" value='${0}'/>
<c:set var="facilityOrAllCatalog" value='${param.facilityOrAllCatalog}'/>
<c:set var="minCatalogPrice" value='${status.current.minCatalogPrice}'/>
<c:set var="maxCatalogPrice" value='${status.current.maxCatalogPrice}'/>
<c:choose>
 <c:when test="${facilityOrAllCatalog == 'All Catalog'}" >
  <c:choose>
   <c:when test="${!empty minCatalogPrice && !empty maxCatalogPrice}">
     <c:set var="finalPrice" value='${maxCatalogPrice}'/>
   </c:when>
   <c:otherwise>
     <c:if test="${!empty minCatalogPrice}">
        <c:set var="finalPrice" value='${minCatalogPrice}'/>
     </c:if>
     <c:if test="${!empty maxCatalogPrice}">
        <c:set var="finalPrice" value='${maxCatalogPrice}'/>
     </c:if>
   </c:otherwise>
  </c:choose>
 </c:when>
 <c:otherwise>
  <c:set var="finalPrice" value='${minCatalogPrice}'/>
 </c:otherwise>
</c:choose>

<TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
<c:if test="${!empty finalPrice}">
<tcmis:emptyIfZero value="${finalPrice}"><fmt:formatNumber maxFractionDigits="4" minFractionDigits="4"><c:out value="${finalPrice}"/></fmt:formatNumber></tcmis:emptyIfZero>
<c:out value="${status.current.currencyId}"/>
</c:if>
</TD>

<TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
<c:set var="storageTemp" value='${status.current.storageTemp}'/>
<c:set var="shelfLife" value='${status.current.shelfLife}'/>

<c:choose>
 <c:when test="${empty storageTemp || storageTemp == ' '}" >
 &nbsp;
 <INPUT TYPE="hidden" NAME="shelfLife" value="" >
 </c:when>
 <c:otherwise>
   <c:choose>
    <c:when test="${shelfLife != 'Indefinite'}" >
       <c:set var="shelfLifeBasis" value='${status.current.shelfLifeBasis}'/>
       <c:if test="${!empty shelfLifeBasis}">
         <c:out value="${status.current.shelfLife}"/> <c:out value="${shelfLifeBasis}"/> @ <c:out value="${storageTemp}"/>
         <INPUT TYPE="hidden" NAME="shelfLife" value="<c:out value="${status.current.shelfLife}"/> <c:out value="${shelfLifeBasis}"/> @ <c:out value="${storageTemp}"/>" >
       </c:if>
      <c:if test="${empty shelfLifeBasis}">
         <c:out value="${status.current.shelfLife}"/> @ <c:out value="${storageTemp}"/>
         <INPUT TYPE="hidden" NAME="shelfLife" value="<c:out value="${status.current.shelfLife}"/> @ <c:out value="${storageTemp}"/>" >
       </c:if>
    </c:when>
    <c:otherwise>
     <c:out value="${status.current.shelfLife}"/> @ <c:out value="${storageTemp}"/>
     <INPUT TYPE="hidden" NAME="shelfLife" value="<c:out value="${status.current.shelfLife}"/> @ <c:out value="${storageTemp}"/>" >
    </c:otherwise>
   </c:choose>
 </c:otherwise>
</c:choose>
</TD>

<TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
<c:if test="${!empty unitOfSaleQuantityPerEach}">
<tcmis:emptyIfZero value="${status.current.unitOfSaleQuantityPerEach}"><fmt:formatNumber maxFractionDigits="4" minFractionDigits="4"><c:out value="${status.current.unitOfSaleQuantityPerEach}"/></fmt:formatNumber></tcmis:emptyIfZero>
</c:if>
</TD>
<TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>"><c:out value="${status.current.unitOfSale}"/></TD>

<TD width="5%" rowspan="<c:out value="${mainRowSpan}"/>">
<c:choose>
 <c:when test="${empty status.current.approvalStatus}" >
  &nbsp;
 </c:when>
 <c:otherwise>
  <c:out value="${status.current.approvalStatus}"/>
 </c:otherwise>
</c:choose>
</TD>

<c:forEach var="prCatalogScreenSearchItemBean" items="${itemCollection}" varStatus="status1">
  <c:if test="${status1.index > 0 && listSize > 1 }">
   <TR align="center" CLASS="<c:out value="${colorClass}"/>" ID="childRowId<c:out value="${status.index}"/><c:out value="${status1.index}"/>">
   <SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">
   <!--
    var childRowId  =  document.getElementById("childRowId<c:out value="${status.index}"/><c:out value="${status1.index}"/>");
     childRowId.attachEvent('onmouseup',function () {
      catchevent('<c:out value="${status.index}"/>');});
    // -->
    </SCRIPT>
  </c:if>

  <c:set var="componentCollection"  value='${status1.current.componentCollection}'/>
  <bean:size id="componentSize" name="componentCollection"/>
  <INPUT TYPE="hidden" NAME="grandChildRowsSize<c:out value="${status.index}"/><c:out value="${status1.index}"/>" value="<c:out value="${componentSize}"/>" >

  <%--<TD width="5%" rowspan="<c:out value="${componentSize}"/>"><c:out value="${status1.current.bundle}"/></TD>--%>
  <TD width="5%" rowspan="<c:out value="${componentSize}"/>"><c:out value="${status1.current.itemId}"/></TD>

  <c:forEach var="prCatalogScreenSearchComponentBean" items="${componentCollection}" varStatus="status2">
    <c:if test="${status2.index > 0 && componentSize > 1 }">
     <TR align="center" CLASS="<c:out value="${colorClass}"/>" ID="grnadChildRowId<c:out value="${status.index}"/><c:out value="${status1.index}"/><c:out value="${status2.index}"/>">
     <SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">
     <!--
     var childRowId  =  document.getElementById("grnadChildRowId<c:out value="${status.index}"/><c:out value="${status1.index}"/><c:out value="${status2.index}"/>");
     childRowId.attachEvent('onmouseup',function () {
      catchevent('<c:out value="${status.index}"/>');});
     // -->
     </SCRIPT>
    </c:if>

    <TD width="15%"><c:out value="${status2.current.materialDesc}"/></TD>
    <TD width="5%"><c:out value="${status2.current.packaging}"/></TD>
    <TD width="5%"><c:out value="${status2.current.grade}"/></TD>
    <TD width="10%"><c:out value="${status2.current.mfgDesc}"/></TD>
    <TD width="10%"><c:out value="${status2.current.mfgPartNo}"/></TD>

    <c:if test="${status2.index > 0 || componentSize ==1 }">
    </TR>
    </c:if>
  </c:forEach>

  <c:if test="${status1.index > 0 || listSize ==1 }">
   </TR>
  </c:if>
 </c:forEach>

</c:forEach>
</TABLE>

<INPUT TYPE="hidden" name="totallines" value="<c:out value="${dataCount}"/>">

<c:if test="${empty prCatalogScreenSearchBeanCollection}" >
<c:if test="${prCatalogScreenSearchBeanCollection != null}" >
<TABLE  BORDER="0" CELLSPACING=0 CELLPADDING=0 WIDTH="100%" CLASS="moveup">
<tr>
<TD HEIGHT="25" WIDTH="100%" VALIGN="MIDDLE" BGCOLOR="#a2a2a2">
<fmt:message key="main.nodatafound"/>
</TD>
</tr>
</TABLE>
</c:if>
</c:if>

</tcmis:form>
</DIV>

<DIV id="ie5menu" class="skin200" onMouseover="highlightie5()" onMouseout="lowlightie5()" onClick="jumptoie5();">
<DIV class="menuitems" url="javascript:showApprovedWorkAreas();">Approved Work Areas</DIV>
<c:if test="${selectedApplicationId != 'All'}">
  <DIV class="menuitems" url="javascript:showWorkAreaComments();">Work Area Comments</DIV>
</c:if>
<DIV class="menuitems" url="javascript:showPartNumberComments();">Part Number Comments</DIV>
</DIV>

</body>
</html:html>