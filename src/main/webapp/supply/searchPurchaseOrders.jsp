<!DOCtype html PUBLIC "-//W3C//Dtd XHTML 1.0 Transitional//EN"
	"http://www.w3.org/tr/xhtml1/Dtd/xhtml1-transitional.dtd">
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
<meta http-equiv="content-type" content="text/html; charset=iso-8859-1">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>
<tcmis:fontSizeCss />

<!-- This handles the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<!-- This handles which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js" language="JavaScript"></script>

<script SRC="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>
<%-- Add any other javascript you need for the page here --%>
<script src="/js/supply/searchposmain.js" language="JavaScript"></script>


<%-- These are for the Grid, uncomment if you are going to use the grid --%>
<%--<script src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridHaas.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGridCell.js"></script>
<script src="/js/dhtmlxGrid/dhtmlXGrid_excell_mro.js"></script>
--%>

<%-- This is for the YUI, uncomment if you will use this --%>
<%--<script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script>--%>

<title>
<fmt:message key="searchpurchaseorders.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
   //add all the javascript messages here, this for internationalization of client side javascript messages
   var messagesData = new Array();
   messagesData={alert:"<fmt:message key="label.alert"/>",
                   and:"<fmt:message key="label.and"/>",
                   all:"<fmt:message key="label.all"/>",
        submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
           validValues:"<fmt:message key="transactions.validvalues"/>",
                itemId:"<fmt:message key="label.itemid"/>"};

   var althubid = new Array(
   <c:forEach var="hubInventoryGroupOvBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
     <c:choose>
       <c:when test="${status.index > 0}">
        ,"<c:out value="${status.current.branchPlant}"/>"
       </c:when>
       <c:otherwise>
        "<c:out value="${status.current.branchPlant}"/>"
       </c:otherwise>
      </c:choose>
   </c:forEach>
   );

   var altinvid = new Array();
   var altinvName = new Array();
   <c:forEach var="hubInventoryGroupOvBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
     <c:set var="currentHub" value='${status.current.branchPlant}'/>
     <c:set var="inventoryGroupDefinitions" value='${status.current.inventoryGroupCollection}'/>

   altinvid["<c:out value="${currentHub}"/>"] = new Array(""
     <c:forEach var="inventoryGroupObjBean" items="${inventoryGroupDefinitions}" varStatus="status1">
    ,"<c:out value="${status1.current.inventoryGroup}"/>"
      </c:forEach>
   );

   altinvName["<c:out value="${currentHub}"/>"] = new Array(messagesData.all
     <c:forEach var="inventoryGroupObjBean" items="${inventoryGroupDefinitions}" varStatus="status1">
    ,"<c:out value="${status1.current.inventoryGroupName}"/>"
      </c:forEach>
   );
   </c:forEach>
// -->
</script>
</head>

<body bgcolor="#ffffff">


<tcmis:form action="/searchpos.do" onsubmit="if (checkSearchPOsInput()) return submitOnlyOnce(); else return false;">

 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;text-align:center;">
  <br><br><br><fmt:message key="label.pleasewait"/>
 </div>
 <div class="interface" id="mainPage">

<%-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure --%>
<div class="topNavigation" id="topNavigation">
<table width="100%">
<tr valign="top">
<td width="200">
<img src="/images/tcmtitlegif.gif" align="left">
</td>
<td align="right">
<img src="/images/tcmistcmis32.gif" align="right">
</td>
</tr>
</table>

<table width="100%">
<tr><td width="70%" class="headingl">
<fmt:message key="searchpurchaseorders.title"/>
</td>
<td width="30%" class="headingr">
<html:link style="color:#FFFFFF" forward="home">
 <fmt:message key="label.home"/>
</html:link>
</td>
</tr>
</table>
</div>

<div class="contentArea">
<!-- Search Option Begins -->
<table id="searchMaskTable" width="700" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
    <tr>
      <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.hub"/>:</td>
      <td width="10%">
         <c:set var="selectedHub" value='${param.branchPlant}'/>
         <c:set var="selectedHubName" value=''/>
         <c:set var="inventoryGroupDefinitions" value='${null}'/>
         <select name="branchPlant" onchange="hubChanged()" class="selectBox" id="branchPlant">
            <option value=""><fmt:message key="label.all"/></option>
           <c:forEach var="hubInventoryGroupOvBean" items="${personnelBean.hubInventoryGroupOvBeanCollection}" varStatus="status">
            <c:set var="currentHub" value='${status.current.branchPlant}'/>
            <c:if test="${currentHub == selectedHub}" >
              <c:set var="inventoryGroupDefinitions" value='${status.current.inventoryGroupCollection}'/>
            </c:if>
            <c:choose>
              <c:when test="${currentHub == selectedHub}">
                <option value='<c:out value="${currentHub}"/>' selected><c:out value="${status.current.hubName}"/></option>
                <c:set var="selectedHubName" value="${status.current.hubName}"/>
              </c:when>
              <c:otherwise>
                <option value='<c:out value="${currentHub}"/>'><c:out value="${status.current.hubName}"/></option>
              </c:otherwise>
            </c:choose>
          </c:forEach>
         </select>
      </td>
      <td width="5%" class="optionTitleBoldRight" nowrap>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <fmt:message key="label.search"/>:&nbsp;
        <html:select property="searchField1" styleClass="selectBox" styleId="searchField1">
         <html:option value="ITEM_DESC" key="label.itemdesc"/>
         <html:option value="ITEM_ID" key="label.itemid"/>
         <html:option value="SUPPLIER" key="label.supplier"/>
        </html:select>
 <%--      </td>
      <td width="15%" class="optionTitleBoldLeft">  --%>
        &nbsp;<fmt:message key="label.for"/>&nbsp;
        <input class="inputBox" type="text" name="searchString1" id="searchString1" value='<c:out value="${param.searchString1}"/>' size="25">
      </td>
    </tr>
    <tr>
      <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.inventorygroup"/>:&nbsp;</td>
      <td width="10%">
       <c:set var="selectedIg" value='${param.inventoryGroup}'/>
       <c:set var="invenGroupCount" value='${0}'/>
       <select name="inventoryGroup" id="inventoryGroup" class="selectBox" size="1">
        <option value=""><fmt:message key="label.all"/></option>
        <c:forEach var="inventoryGroupObjBean" items="${inventoryGroupDefinitions}" varStatus="status">
          <c:set var="invenGroupCount" value='${invenGroupCount+1}'/>
          <c:set var="currentIg" value='${status.current.inventoryGroup}'/>
          <c:if test="${empty selectedIg}" >
              <c:set var="selectedIg" value=""/>
          </c:if>
          <c:choose>
            <c:when test="${currentIg == selectedIg}">
              <option value='<c:out value="${currentIg}"/>' selected><c:out value="${status.current.inventoryGroupName}"/></option>
            </c:when>
            <c:otherwise>
              <option value='<c:out value="${currentIg}"/>'><c:out value="${status.current.inventoryGroupName}"/></option>
            </c:otherwise>
          </c:choose>
        </c:forEach>
       </select>

      </td>
      <td width="5%" class="optionTitleBoldRight" nowrap>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <fmt:message key="label.search"/>:&nbsp;
        <html:select property="searchField2" styleClass="selectBox" styleId="searchField2">
         <html:option value="ITEM_DESC" key="label.itemdesc"/>
         <html:option value="ITEM_ID" key="label.itemid"/>
         <html:option value="SUPPLIER" key="label.supplier"/>
        </html:select>
       &nbsp;<fmt:message key="label.for"/>&nbsp;
        <input class="inputBox" type="text" name="searchString2" id="searchString2" value='<c:out value="${param.searchString2}"/>' size="25">
      </td>
    </tr>
    <tr>
      <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.buyer"/>:&nbsp;</td>
      <td width="10%">
        <select class="inputBox" name="buyer" id="buyer" size="1">
          <option value=""><fmt:message key="label.anybuyer"/></option>
         <c:choose>
           <c:when test="${param.buyer == null}">
             <c:set var="currentBuyer" value="${personnelBean.personnelId}"/>
           </c:when>
           <c:otherwise>
              <c:set var="currentBuyer" value="${param.buyer}"/>
           </c:otherwise>
         </c:choose>
         <c:forEach items="${buyerColl}" var="buyerBean">
          <c:choose>
            <c:when test="${currentBuyer == buyerBean.personnelId}">
             <option value='<c:out value="${buyerBean.personnelId}"/>' selected><c:out value="${buyerBean.name}"/></option>
            </c:when>
            <c:otherwise>
             <option value='<c:out value="${buyerBean.personnelId}"/>'><c:out value="${buyerBean.name}"/></option>
            </c:otherwise>
           </c:choose>
          </c:forEach>
        </select>
      </td>
      <td width="5%" class="optionTitleBoldLeft" nowrap>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <c:choose>
         <c:when test='${param.submitSearch == null || param.openOrders == "Yes"}'>
           <input type="checkbox" value="Yes" id="openOrders" name="openOrders" class="radioBtns" checked>
         </c:when>
         <c:otherwise>
           <input type="checkbox" value="Yes" id="openOrders" name="openOrders" class="radioBtns">
         </c:otherwise>
        </c:choose>
<%--       </td>
      <td width="10%" class="optionTitleBoldLeft"> --%>
        <fmt:message key="searchpurchaseorders.openposonly"/>
<%--      </td>
      <td width="15%" class="optionTitleBoldLeft">  --%>
        <c:choose>
         <c:when test='${param.unconfirmedOrders == "Yes"}'>
          <input type="checkbox" value="Yes" id="unconfirmedOrders" name="unconfirmedOrders" class="radioBtns" checked>
         </c:when>
         <c:otherwise>
          <input type="checkbox" value="Yes" id="unconfirmedOrders" name="unconfirmedOrders" class="radioBtns">
         </c:otherwise>
        </c:choose>
         <fmt:message key="label.unconfirmed"/>
<%--         <c:choose>
         <c:when test='${param.brokenPrLink == "Yes"}'>
          <input type="checkbox" value="Yes" id="brokenPrLink" name="brokenPrLink" class="radioBtns" checked>
         </c:when>
         <c:otherwise>
          <input type="checkbox" value="Yes" id="brokenPrLink" name="brokenPrLink" class="radioBtns">
         </c:otherwise>
        </c:choose>
      <fmt:message key="searchpurchaseorders.brokenprlink"/> --%>
      </td>
    </tr>
    <tr>
      <td width="5%">&nbsp;</td>
      <td width="10%">&nbsp;</td>
      <td width="5%" class="optionTitleBoldLeft">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <c:choose>
         <c:when test='${param.brokenPrLink == "Yes"}'>
          <input type="checkbox" value="Yes" id="brokenPrLink" name="brokenPrLink" class="radioBtns" checked>
         </c:when>
         <c:otherwise>
          <input type="checkbox" value="Yes" id="brokenPrLink" name="brokenPrLink" class="radioBtns">
         </c:otherwise>
        </c:choose>
      <fmt:message key="searchpurchaseorders.brokenprlink"/></td>
      <td>&nbsp;</td>
    </tr> 
    <tr>
      <td width="5%" class="optionTitleBoldRight">
          <input name="submitSearch" type="submit" class="inputBtns" value="Search" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'">
      </td>
      <td>
		<input type="hidden" name="uAction" id="uAction" value="search"/>
		<input type="button" name="createXSL" id="createXSL" value="<fmt:message key="label.createExcel"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onClick="createExcel()"/>
      </td>
      <td colspan="3">&nbsp;</td>
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
<div class="spacerY">&nbsp;
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
<!-- Error Messages Ends -->

<!-- Search results start -->
<c:if test="${poSearchColl != null}">
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="dataContent">
    <table width="100%" class="tableResults">
    <c:set var="colorClass" value=''/>
    <c:set var="dataCount" value='${0}'/>

    <c:forEach var="po" items="${poSearchColl}" varStatus="status">
    <!-- Need to print the header every 10 rows-->
    <c:if test="${status.index % 10 == 0}">
    <tr>
      <th width="5%"><fmt:message key="label.po"/></th>
      <th width="15%"><fmt:message key="label.supplier"/></th>
      <th width="5%"><fmt:message key="label.buyer"/></th>
      <th width="5%"><fmt:message key="label.hub"/></th>
      <th width="5%"><fmt:message key="label.inventorygroup"/></th>
      <th width="5%"><fmt:message key="searchpurchaseorders.datecreatedconfirmed"/></th>
      <th width="5%"><fmt:message key="label.itemid"/></th>
      <th width="15%"><fmt:message key="label.itemdescription"/></th>
      <th width="5%"><fmt:message key="label.needdate"/></th>
      <th width="5%"><fmt:message key="label.promiseddate"/></th>
      <th width="5%"><fmt:message key="label.vendorshipdate"/></th>
      <th width="5%"><fmt:message key="label.revisedpromisedate"/></th>
      <th width="5%"><fmt:message key="label.unitprice"/></th>
      <th width="5%"><fmt:message key="label.quantity"/></th>
      <th width="5%"><fmt:message key="label.extprice"/></th>
      <th width="5%"><fmt:message key="label.paymentterms"/></th>
    </tr>
    </c:if>

    <c:choose>
     <c:when test="${status.index % 2 == 0}" >
      <c:set var="colorClass" value=''/>
     </c:when>
     <c:otherwise>
      <c:set var="colorClass" value='alt'/>
     </c:otherwise>
    </c:choose>

   <tr class='<c:out value="${colorClass}"/>' id='rowId<c:out value="${status.index}"/>'>
     <td width="5%">
       <a href='/tcmIS/supply/purchorder?po=<c:out value="${po.radianPo}"/>&Action=searchlike&subUserAction=po' target="_blank<c:out value="${po.radianPo}"/>">
         <c:out value="${po.radianPo}"/>
       </a>
     </td>
     <td width="15%"><c:out value="${po.supplierName}"/></td>
     <td width="5%"><c:out value="${po.buyerName}"/></td>
     <td width="5%"><c:out value="${po.hubName}"/></td>
     <td width="5%"><c:out value="${po.inventoryGroupName}"/></td>
     <fmt:formatDate var="fmtDateCreated" value="${po.dateCreated}" pattern="${dateTimeFormatPattern}"/>
     <td width="5%">${fmtDateCreated}</td>
     <td width="5%"><c:out value="${po.itemId}"/></td>
     <td width="15%"><c:out value="${po.itemDesc}"/></td>
     <fmt:formatDate var="fmtNeedDate" value="${po.needDate}" pattern="${dateFormatPattern}"/>
     <td width="5%">${fmtNeedDate}</td>
     <fmt:formatDate var="fmtPromisedDate" value="${po.promisedDate}" pattern="${dateFormatPattern}"/>
     <td width="5%">${fmtPromisedDate}</td>
     <fmt:formatDate var="fmtVendorShipDate" value="${po.vendorShipDate}" pattern="${dateFormatPattern}"/>
     <td width="5%">${fmtVendorShipDate}</td>
     <fmt:formatDate var="fmtRevisedPromisedDate" value="${po.revisedPromisedDate}" pattern="${dateFormatPattern}"/>
     <td width="5%">${fmtRevisedPromisedDate}</td>
     <td width="5%" nowrap>${po.currencyId} ${po.unitPrice} </td>
     <td width="5%"><c:out value="${po.quantity}"/></td>
     <td width="5%"nowrap>${po.currencyId} ${po.extPrice}</td>
     <td width="5%"><c:out value="${po.paymentTerms}"/></td>
   </tr>
   <c:set var="dataCount" value='${dataCount+1}'/>
   </c:forEach>
   </table>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty poSearchColl}" >
   <c:if test="${poSearchColl != null}" >
   <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData">
     <tr>
      <td width="100%">
       <fmt:message key="main.nodatafound"/>
      </td>
     </tr>
    </table>
   </c:if>
   </c:if>

  </div>
  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</div>
</c:if>
</td></tr>
</table>
<!-- Search results end -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;"></div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->
</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>

