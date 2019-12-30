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
<tcmis:fontSizeCss />
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
<script type="text/javascript" src="/js/common/searchiframeresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<%--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/admin/supplierpermissionsearch.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>

<!-- This is for the YUI, uncomment if you will use this -->
<%--<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>--%>

<title>
<fmt:message key="supplierpermission.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
<c:set var="companySupplierColl" value='${dropdownColl}'/>
<bean:size id="companySize" name="companySupplierColl"/>
var altCompanyId = new Array(
<c:forEach var="myCompanySupplierBean" items="${dropdownColl}" varStatus="status">
 <c:choose>
   <c:when test="${status.index == 0 && companySize > 1}">
     "My Companies","<c:out value="${status.current.companyId}" escapeXml="false"/>"
   </c:when>
   <c:otherwise>
     <c:choose>
       <c:when test="${status.index == 0}">
         "<c:out value="${status.current.companyId}" escapeXml="false"/>"
       </c:when>
       <c:otherwise>
         ,"<c:out value="${status.current.companyId}" escapeXml="false"/>"
       </c:otherwise>
     </c:choose>
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altCompanyName = new Array(
<c:forEach var="myCompanySupplierBean" items="${dropdownColl}" varStatus="status">
 <c:choose>
   <c:when test="${status.index == 0 && companySize > 1}">
     "<fmt:message key="label.mycompanies"/>","<c:out value="${status.current.companyName}" escapeXml="false"/>"
   </c:when>
   <c:otherwise>
     <c:choose>
       <c:when test="${status.index == 0}">
         "<c:out value="${status.current.companyName}" escapeXml="false"/>"
       </c:when>
       <c:otherwise>
         ,"<c:out value="${status.current.companyName}" escapeXml="false"/>"
       </c:otherwise>
     </c:choose>
   </c:otherwise>
  </c:choose>
</c:forEach>
);

var altSupplierId = new Array();
var altSupplierName = new Array();
  <c:if test="${companySize > 1}">
    altSupplierId["My Companies"] = new Array("My Suppliers");
    altSupplierName["My Companies"] = new Array("My Suppliers");
  </c:if>

  <c:forEach var="myCompanySupplierBean" items="${dropdownColl}" varStatus="status">
    <c:set var="currentCompanyId" value='${status.current.companyId}'/>
    <c:set var="supplierListCollection" value='${status.current.supplierList}'/>

    <bean:size id="supplierSize" name="supplierListCollection"/>

    altSupplierId["<c:out value="${currentCompanyId}" escapeXml="false"/>"] = new Array(
    <c:forEach var="supplierObjBean" items="${supplierListCollection}" varStatus="status1">
        <c:choose>
          <c:when test="${status1.index == 0 && supplierSize > 1}">
            "My Suppliers","<c:out value="${status1.current.supplier}" escapeXml="false"/>"
          </c:when>
          <c:otherwise>
            <c:choose>
              <c:when test="${status1.index == 0}">
                "<c:out value="${status1.current.supplier}" escapeXml="false"/>"
              </c:when>
              <c:otherwise>
                ,"<c:out value="${status1.current.supplier}" escapeXml="false"/>"
              </c:otherwise>
            </c:choose>
          </c:otherwise>
        </c:choose>
    </c:forEach>
    );

    altSupplierName["<c:out value="${currentCompanyId}" escapeXml="false"/>"] = new Array(
    <c:forEach var="supplierObjBean" items="${supplierListCollection}" varStatus="status1">
        <c:choose>
          <c:when test="${status1.index == 0 && supplierSize > 1}">
            "<fmt:message key="label.mysuppliers"/>","<c:out value="${status1.current.supplierName}" escapeXml="false"/>"
          </c:when>
          <c:otherwise>
            <c:choose>
              <c:when test="${status1.index == 0}">
                "<c:out value="${status1.current.supplierName}" escapeXml="false"/>"
              </c:when>
              <c:otherwise>
                ,"<c:out value="${status1.current.supplierName}" escapeXml="false"/>"
              </c:otherwise>
            </c:choose>
          </c:otherwise>
        </c:choose>
    </c:forEach>
    );
  </c:forEach>
// -->
</script>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="setSearchFrameSize();">

<tcmis:form action="/supplierpermissionresults.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">

<div class="interface" id="searchMainPage">

<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="500" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
      <tr>
        <td class="optionTitleBoldRight"><fmt:message key="label.company"/>:</td>
        <td>
          <!-- Use this for dropdowns you build with collections from the database -->
          <c:set var="companySupplierColl" value='${dropdownColl}'/>
          <bean:size id="companySize" name="companySupplierColl"/>
          <c:set var="selectedCompanyId" value='${param.companyId}'/>
          <select name="companyId" id="companyId" onchange="companyChanged()" class="selectBox">
            <c:choose>
              <c:when test="${companySize == 0}">
                <option value="My Companies"><fmt:message key="label.mycompanies"/></option>
              </c:when>
              <c:otherwise>
                <c:if test="${companySize > 1}">
                  <option value="My Companies" selected><fmt:message key="label.mycompanies"/></option>
                  <c:set var="selectedCompanyId" value="mycompanies"/>  <%-- set it to something so I can test it selectedCompanyId is empty below --%>
                </c:if>

                <c:forEach var="myCompanySupplierBean" items="${dropdownColl}" varStatus="status">
                  <c:set var="currentCompanyId" value='${status.current.companyId}'/>
                  <c:if test="${empty selectedCompanyId}" >
                    <c:set var="selectedCompanyId" value='${status.current.companyId}'/>
                    <c:set var="supplierCollection" value='${status.current.supplierList}'/>
                  </c:if>
                  <option value="<c:out value="${currentCompanyId}"/>"><c:out value="${status.current.companyName}" escapeXml="false"/></option>
                </c:forEach>
              </c:otherwise>
            </c:choose>
          </select>
       </td>
      </tr>
      <tr>
        <td class="optionTitleBoldRight"><fmt:message key="label.supplier"/>:</td>
        <td width="15%">
          <c:choose>
            <c:when test="${supplierCollection == null}">
              <c:set var="supplierSize" value="0"/>
            </c:when>
            <c:otherwise>
              <bean:size id="supplierSize" name="supplierCollection"/>
            </c:otherwise>
          </c:choose>
          <c:set var="selectedSupplierId" value='${param.supplier}'/>
          <select name="supplier" id="supplier" class="selectBox">
            <c:choose>
              <c:when test="${supplierSize == 0}">
                <option value="My Suppliers"><fmt:message key="label.mysuppliers"/></option>
              </c:when>
            <c:otherwise>
              <c:if test="${supplierSize > 1}">
                <option value="My Suppliers" selected><fmt:message key="label.mysuppliers"/></option>
               <c:set var="selectedSupplierId" value="mysuppliers"/>  <%-- set it to something so I can test it selectedFacilityId is empty below --%>
             </c:if>
             <c:forEach var="supplierOvBean" items="${supplierCollection}" varStatus="status">
               <c:set var="currentSupplierId" value='${status.current.supplier}'/>
                 <option value="<c:out value="${currentSupplierId}"/>"><c:out value="${status.current.supplierName}" escapeXml="false"/></option>
             </c:forEach>
           </c:otherwise>
         </c:choose>
         </select>
       </td>
	     <td class="optionTitleBoldLeft">
				<input name="editUserSupplier" id="editUserSupplier" type="button" class="smallBtns" value="<fmt:message key="label.editlist"/>" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onclick="showUserSupplier(); return false;">
			</td>
      </tr>
      <tr>
      <td colspan="3">
				<input name="Submit" id="Submit" type="submit" value="<fmt:message key="label.search"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="submitSearchForm();">
				<%--
	      <input name="buttonCreateExcel" id="buttonCreateExcel" type="submit" class="inputBtns" value="<fmt:message key="label.createexcelfile"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="generateExcel(); return false;">
	      --%>
				<input name="Cancel" id="Cancel" type="button" value="<fmt:message key="label.cancel"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="closeWindow();">
			</td>
      </tr>

    </table>
   <!-- End search options -->
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
</td></tr>
</table>
<!-- Search Option Ends -->


<!-- Error Messages Begins -->
<!-- Build this section only if there is an error message to display.
     For the search section, we show the error messages within its frame
-->
<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
<div class="spacerY">&nbsp;
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
</c:if>
<!-- Error Messages Ends -->

</div> <!-- close of contentArea -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="action" id="action" type="hidden" value="">
<input name="personnelId" id="personnelId" type="hidden" value="<c:out value="${personnelId}"/>">
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>