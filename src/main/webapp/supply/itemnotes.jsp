<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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

<tcmis:fontSizeCss />

<!-- This handles the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<!-- This handles which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js" language="JavaScript"></script>

<script SRC="/js/common/formchek.js" language="JavaScript"></script>
<script SRC="/js/common/commonutil.js" language="JavaScript"></script>

<title>
<fmt:message key="itemnotes.title"/>
</title>

</head>

<body bgcolor="#ffffff">


<tcmis:form action="/itemnotes.do" onsubmit="return submitOnlyOnce();">

 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;text-align:center;">
  <br><br><br><fmt:message key="label.pleasewait"/>
 </div>

 <div id="mainPage">
 <div class="contentArea">

<c:set var="updatePermission" value=""/>
<tcmis:facilityPermission indicator="true" userGroupId="catalogAddItemQc">
  <c:set var="updatePermission" value="Yes"/>
</tcmis:facilityPermission>

<tcmis:facilityPermission indicator="true" userGroupId="Purchasing">
  <c:set var="updatePermission" value="Yes"/>
</tcmis:facilityPermission>

<tcmis:facilityPermission indicator="true" userGroupId="BuyOrder">
  <c:set var="updatePermission" value="Yes"/>
</tcmis:facilityPermission>

<tcmis:inventoryGroupPermission indicator="true" userGroupId="GenerateOrders">
  <c:set var="updatePermission" value="Yes"/>
</tcmis:inventoryGroupPermission>

<c:if test="${updatePermission == 'Yes'}">
<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>

<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
    <table width="100%" border="0" cellpadding="0" cellspacing="0">

    <c:if test="${empty notesMessage}">
    <tr>
      <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.itemid"/>:</td>
      <td width="20%">
        <c:out value="${param.itemId}"/>
        <input type="hidden" name="itemId" id="itemId" value='<c:out value="${param.itemId}"/>'>
      </td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr><td colspan=4>&nbsp;</td></tr>
    <tr>
      <td width="5%" class="optionTitleBoldRight"><fmt:message key="label.notes"/>:&nbsp;</td>
      <td width="20%">
        <textarea name="comments" id="comments" rows="5" cols="70" class="inputBox"></textarea>
      </td>
      <td>&nbsp;</td>
      <td>
        <input name="addNotes" type="submit" class="inputBtns" value="add" id="addNotes" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'">
        <br>&nbsp;<br>
        <input name="cancel" type="button" class="inputBtns" value="cancel" id="cancel" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'" onclick='closeWindow();'>
      </td>
    </tr>
    <tr><td colspan=4>&nbsp;</td></tr>
    </c:if>

    <c:if test="${! empty notesMessage}">
    <tr>
      <td colspan=4><fmt:message key="itemnotes.addnotemessage"/><c:out value="${notesMessage}"/></td>
    </tr>
    <tr><td colspan=4>&nbsp;</td></tr>
    <tr>
      <td colspan=4><input name="ok" type="button" class="inputBtns" value="ok" id="ok" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'" onclick='closeWindow();'></td>
    </tr>

    </c:if>

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
</c:if>

<!-- Error Messages Begins -->
<div class="spacerY">&nbsp;
<div id="errorMessagesArea" class="errorMessages" style="display:none;">
<html:errors/>
</div>
</div>
<!-- Error Messages Ends -->

<!-- Search results start -->
<c:if test="${itemNotesColl != null}">
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

    <c:forEach var="note" items="${itemNotesColl}" varStatus="status">
    <!-- Need to print the header every 10 rows-->
    <c:if test="${status.index % 10 == 0}">
    <tr align="center">
      <th width="5%"><fmt:message key="label.date"/></th>
      <th width="5%"><fmt:message key="label.buyer"/></th>
      <th width="25%"><fmt:message key="label.comments"/></th>
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

   <tr align="center" class='<c:out value="${colorClass}"/>' id='rowId<c:out value="${status.index}"/>'>
     <td width="5%">
        <fmt:formatDate var="fmtTxnDate" value="${note.transactionDate}" pattern="MM/dd/yyyy kk:mm"/>
        <c:out value="${fmtTxnDate}"/>
     </td>
     <td width="5%"><c:out value="${note.enteredByName}"/></td>
     <td width="25%" class="alignLeft"><c:out value="${note.comments}"/></td>
   </tr>
   <c:set var="dataCount" value='${dataCount+1}'/>
   </c:forEach>
   </table>

   <!-- no data found -->
   <c:if test="${empty itemNotesColl}" >
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData">
      <tr>
      <TD width="100%">
      <fmt:message key="main.nodatafound"/>
      </td>
     </tr>
     </table>
   </c:if>
  </div>
  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</div>
</td></tr>
</table>
</c:if>
<!-- Search results end -->

</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;"></div>
<!-- Hidden elements end -->

</div> <!-- close of main -->
</tcmis:form>
</body>
</html:html>

