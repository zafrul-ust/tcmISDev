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

<%@ include file="/common/locale.jsp" %> 
<tcmis:fontSizeCss />

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<script type="text/javascript" src="/js/supply/itemnotes.js"></script>

<title>
<fmt:message key="itemnotes.title"/>
</title>

<script language="JavaScript" type="text/javascript">
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
                and:"<fmt:message key="label.and"/>",
     submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
        itemInteger:"<fmt:message key="error.item.integer"/>"};
</script>

<!-- This handles the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

</head>
 
<body bgcolor="#ffffff">

<tcmis:form action="/edititemnotes.do" onsubmit="return submitOnlyOnce();">

 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
 </div>

 <div class="interface" id="mainPage">
<!-- Search Option Begins -->
<div class="contentArea">
<table id="searchMaskTable" width="400" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td width="25%" class="optionTitleBoldRight"><fmt:message key="label.itemid"/>:</td>
      <td width="20%">
        <input class="inputBox" type="text" name="itemId" id="itemId" value='<c:out value="${param.itemId}"/>' size="8">
      </td>
      <td><input name="submitSearch" id="submitSearch" type="submit" class="inputBtns" value='<fmt:message key="label.search"/>' onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return validateSearchForm();"></td>
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
<c:if test="${! empty errorMessage}">
  <c:out value="${errorMessage}"/>
</c:if>
</div>
</div>
<!-- Error Messages Ends -->

<!-- Search results start -->
<c:if test="${itemNotesColl != null}">
<table id="resultsMaskTable" width="780" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <input name="update" type="hidden" value="" id="update">
    <div class="boxhead">
    <c:set var="notesPermission" value=''/>
    <tcmis:facilityPermission indicator="true" userGroupId="catalogAddItemQc">
     <c:set var="notesPermission" value='Yes'/>
    </tcmis:facilityPermission>

    <tcmis:facilityPermission indicator="true" userGroupId="BuyOrder">
     <c:set var="notesPermission" value='Yes'/>
    </tcmis:facilityPermission>

    <tcmis:facilityPermission indicator="true" userGroupId="Purchasing">
     <c:set var="notesPermission" value='Yes'/>
    </tcmis:facilityPermission>

    <tcmis:inventoryGroupPermission indicator="true" userGroupId="GenerateOrders">
       <c:set var="notesPermission" value='Yes'/>
    </tcmis:inventoryGroupPermission>

    <c:if test="${notesPermission == 'Yes'}">
     <a href="#" onclick='submitUpdate(); return false;'><fmt:message key="label.update"/></a>
    </c:if>
    </div>

    <div class="dataContent">
    <table width="100%"  border="0" cellpadding="0" cellspacing="0" class="tableResults">
    <c:set var="colorClass" value=''/>
    <c:set var="dataCount" value='${0}'/>

    <tr>
      <th width="2%"><fmt:message key="label.delete"/></th>
      <th width="10%"><fmt:message key="label.date"/></th>
      <th width="10%"><fmt:message key="label.enteredby"/></th>
      <th width="25%"><fmt:message key="label.comments"/></th>
    </tr>

    <input type="hidden" name='itemNotesBean[<c:out value="${dataCount}"/>].changed' id='changed<c:out value="${dataCount}"/>' value='no'>
    <input type="hidden" name='itemNotesBean[<c:out value="${dataCount}"/>].buyerId' id='buyerId<c:out value="${dataCount}"/>' value='<c:out value="${personnelBean.personnelId}"/>'>
    <input type="hidden" name='itemNotesBean[<c:out value="${dataCount}"/>].itemId' id='itemId<c:out value="${dataCount}"/>' value='<c:out value="${param.itemId}"/>'>
    <input type="hidden" name='itemNotesBean[<c:out value="${dataCount}"/>].transactionDate' id='transactionDate<c:out value="${dataCount}"/>' value='<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateTimeFormatPattern}"/>'>
    <input type="hidden" name='itemNotesBean[<c:out value="${dataCount}"/>].recordNo' id='recordNo<c:out value="${dataCount}"/>' value='0'>
    <input type="hidden" name='itemNotesBean[<c:out value="${dataCount}"/>].enteredByName' id='enteredByName<c:out value="${dataCount}"/>' value='<c:out value="${personnelBean.lastName}"/>,<c:out value="${personnelBean.firstName}"/>'>

    <c:if test="${notesPermission == 'Yes'}">
    <tr class='<c:out value="${colorClass}"/>' id='rowId<c:out value="${dataCount}"/>'>
     <td width="2%">
        <input name='itemNotesBean[<c:out value="${dataCount}"/>].delete' id='delete<c:out value="${dataCount}"/>' value='<c:out value="${dataCount}"/>' type="checkbox" disabled style="display: none">
        <fmt:message key="label.new"/>
     </td>
     <td width="10%" nowrap><tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/></td>
     <td width="10%"><c:out value="${personnelBean.firstName}"/>&nbsp;<c:out value="${personnelBean.lastName}"/></td>
     <td width="25%" class="optionTitleBoldLeft"><textarea name='itemNotesBean[<c:out value="${dataCount}"/>].comments' id='comments<c:out value="${dataCount}"/>' onchange='commentsChanged(<c:out value="${dataCount}"/>);' cols=40 rows=4></textarea></td>
    </tr>
    </c:if>

  <c:set var="dataCount" value='${dataCount+1}'/>

  <c:forEach var="note" items="${itemNotesColl}" varStatus="status">

    <c:choose>
     <c:when test="${dataCount % 2 == 0}" >
      <c:set var="colorClass" value=''/>
     </c:when>
     <c:otherwise>
      <c:set var="colorClass" value='alt'/>
     </c:otherwise>
    </c:choose>

    <input type="hidden" name='itemNotesBean[<c:out value="${dataCount}"/>].changed' id='changed<c:out value="${dataCount}"/>' value='no'>
    <input type="hidden" name='itemNotesBean[<c:out value="${dataCount}"/>].buyerId' id='buyerId<c:out value="${dataCount}"/>' value='<c:out value="${note.buyerId}"/>'>
    <input type="hidden" name='itemNotesBean[<c:out value="${dataCount}"/>].itemId' id='itemId<c:out value="${dataCount}"/>' value='<c:out value="${note.itemId}"/>'>
    <input type="hidden" name='itemNotesBean[<c:out value="${dataCount}"/>].transactionDate' id='transactionDate<c:out value="${dataCount}"/>' value='<c:out value="${fmtTxnDate}"/>'>
    <input type="hidden" name='itemNotesBean[<c:out value="${dataCount}"/>].recordNo' id='recordNo<c:out value="${dataCount}"/>' value='<c:out value="${note.recordNo}"/>'>
    <input type="hidden" name='itemNotesBean[<c:out value="${dataCount}"/>].enteredByName' id='enteredByName<c:out value="${dataCount}"/>' value='<c:out value="${note.enteredByName}"/>'>

   <tr class='<c:out value="${colorClass}"/>' id='rowId<c:out value="${dataCount}"/>'>
     <td width="2%">
      <c:if test="${notesPermission == 'Yes'}">
       <input name='itemNotesBean[<c:out value="${dataCount}"/>].delete' id='delete<c:out value="${dataCount}"/>' value='<c:out value="${dataCount}"/>' type="checkbox">
      </c:if>
     </td>
     <td width="10%" nowrap>
        <fmt:formatDate var="fmtTxnDate" value="${note.transactionDate}" pattern="${dateTimeFormatPattern}"/>
        <c:out value="${fmtTxnDate}"/>
     </td>
     <td width="10%"><c:out value="${note.enteredByName}"/></td>
     <td width="25%" class="optionTitleLeft">
      <c:choose>
       <c:when test="${notesPermission == 'Yes'}" >
        <textarea name='itemNotesBean[<c:out value="${dataCount}"/>].comments' id='comments<c:out value="${dataCount}"/>' onchange='commentsChanged(<c:out value="${dataCount}"/>);' cols=40 rows=4><c:out value="${note.comments}"/></textarea>
       </c:when>
       <c:otherwise>
        <c:out value="${note.comments}"/>
       </c:otherwise>
      </c:choose>
     </td>
   </tr>
   <c:set var="dataCount" value='${dataCount+1}'/>
   </c:forEach>
   </table>

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

</tcmis:form>

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;"></div>
<!-- Hidden elements end -->

</div> <!-- close of contentarea -->
</div> <!-- close of interface -->

</body>
</html:html>

