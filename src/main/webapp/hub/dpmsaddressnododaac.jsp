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
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/date.js"></script>
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
<script type="text/javascript" src="/js/calendar/CalendarPopup.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/hub/dpmsaddress.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script type="text/javascript" src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
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
<fmt:message key="dpmsaddress.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
invaliddateformat:"<fmt:message key="label.invaliddateformat"/>", daterequired:"<fmt:message key="label.daterequired"/>",
futuredatenotallowed:"<fmt:message key="label.futuredatenotallowed"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff">

<tcmis:form action="/dpmsaddress.do" onsubmit="return submitOnlyOnce();">

<div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
 <br><br><br><fmt:message key="label.pleasewait"/>
 <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>

<div class="interface" id="mainPage">

<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
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
        <td class="optionTitleBoldLeft"><fmt:message key="label.companyid"/>:</td>
        <td>
<c:out value="${custPoAddressChangeViewBeanCollection[0].companyId}"/>
       </td>
        <td class="optionTitleBoldLeft"><fmt:message key="label.addresschangerequestid"/>:</td>
        <td>
         <c:out value="${addressChangeRequestId}"/>
       </td>
      </tr>

              <tr>
        <td class="optionTitleBoldLeft"><fmt:message key="label.originaltransactiontype"/>:</td>
        <td>
<c:out value="${custPoAddressChangeViewBeanCollection[0].originalTransactionType}"/>
       </td>
        <td class="optionTitleBoldLeft"><fmt:message key="label.milstripcode"/>:</td>
        <td>
            <c:if test="${custPoAddressChangeViewBeanCollection[0].milstripCode == null}">
                <input name="milstripCode" id="milstripCode" type="text" class="inputBox" size="14" >
            </c:if>
            <c:if test="${custPoAddressChangeViewBeanCollection[0].milstripCode != null}">
                <input name="milstripCode" id="milstripCode" type="hidden" value="<c:out value="${custPoAddressChangeViewBeanCollection[0].milstripCode}"/>">
                <c:out value="${custPoAddressChangeViewBeanCollection[0].milstripCode}"/>
            </c:if>
       </td>
      </tr>
              <tr>
        <td class="optionTitleBoldLeft"><fmt:message key="label.transactionreferencenumber"/>:</td>
        <td>
<c:out value="${custPoAddressChangeViewBeanCollection[0].transactionRefNum}"/>
       </td>
        <td class="optionTitleBoldLeft"><fmt:message key="label.transportationcontrolnumber"/>:</td>
        <td>
         <c:out value="${custPoAddressChangeViewBeanCollection[0].transportationControlNum}"/>
       </td>
      </tr>
              <tr>
        <td class="optionTitleBoldLeft"><fmt:message key="label.nsn"/>:</td>
        <td>
<c:out value="${custPoAddressChangeViewBeanCollection[0].nsn}"/>
       </td>
        <td class="optionTitleBoldLeft"><fmt:message key="label.requisitionnumber"/>:</td>
        <td>
         <c:out value="${custPoAddressChangeViewBeanCollection[0].requisitionNumber}"/>
       </td>
      </tr>
             <tr>
        <td class="optionTitleBoldLeft"><fmt:message key="label.callnumber"/>:</td>
        <td>
<c:out value="${custPoAddressChangeViewBeanCollection[0].callNumber}"/>
       </td>
        <td class="optionTitleBoldLeft"><fmt:message key="label.projectcode"/>:</td>
        <td>
         <c:out value="${custPoAddressChangeViewBeanCollection[0].projectCode}"/>
       </td>
      </tr>
             <tr>
        <td class="optionTitleBoldLeft"><fmt:message key="label.priorityrating"/>:</td>
        <td>
<c:out value="${custPoAddressChangeViewBeanCollection[0].priorityRating}"/>
       </td>
        <td class="optionTitleBoldLeft"><fmt:message key="label.fmscasenumber"/>:</td>
        <td>
         <c:out value="${custPoAddressChangeViewBeanCollection[0].fmsCaseNum}"/>
       </td>
      </tr>
             <tr>
        <td class="optionTitleBoldLeft"><fmt:message key="label.portofembarkation"/>:</td>
        <td>
<c:out value="${custPoAddressChangeViewBeanCollection[0].portOfEmbarkation}"/>
<input name="portOfEmbarkation" id="portOfEmbarkation" type="hidden" value="<c:out value="${custPoAddressChangeViewBeanCollection[0].portOfEmbarkation}"/>">
       </td>
        <td class="optionTitleBoldLeft"><fmt:message key="label.portofdebarkation"/>:</td>
        <td>
         <c:out value="${custPoAddressChangeViewBeanCollection[0].portOfDebarkation}"/>
<input name="portOfDebarkation" id="portOfDebarkation" type="hidden" value="<c:out value="${custPoAddressChangeViewBeanCollection[0].portOfDebarkation}"/>">

       </td>
      </tr>
             <tr>
        <td class="optionTitleBoldLeft"><fmt:message key="label.clin"/>:</td>
        <td>
<c:out value="${custPoAddressChangeViewBeanCollection[0].clin}"/>
       </td>
        <td class="optionTitleBoldLeft"><fmt:message key="label.indexingtype"/>:</td>
        <td>
         <c:out value="${custPoAddressChangeViewBeanCollection[0].indexingType}"/>
       </td>
      </tr>
             <tr>
        <td class="optionTitleBoldLeft"><fmt:message key="label.shippedassingle"/>:</td>
        <td>
<c:out value="${custPoAddressChangeViewBeanCollection[0].shippedAsSingle}"/>
       </td>
        <td class="optionTitleBoldLeft"><fmt:message key="label.requiresoverpack"/>:</td>
        <td>
         <c:out value="${custPoAddressChangeViewBeanCollection[0].requiresOverpack}"/>
       </td>
      </tr>
             <tr>
        <td class="optionTitleBoldLeft"><fmt:message key="label.grossweightlbs"/>:</td>
        <td>
<c:out value="${custPoAddressChangeViewBeanCollection[0].grossWeightLbs}"/>
       </td>
        <td class="optionTitleBoldLeft"><fmt:message key="label.cubicfeet"/>:</td>
        <td>
         <c:out value="${custPoAddressChangeViewBeanCollection[0].cubicFeet}"/>
       </td>
      </tr>
             <tr>
        <td class="optionTitleBoldLeft"><fmt:message key="label.signalcode"/>:</td>
        <td>
<c:out value="${custPoAddressChangeViewBeanCollection[0].signalCode}"/>
       </td>
        <td class="optionTitleBoldLeft"><fmt:message key="label.supplementaryaddresscode"/>:</td>
        <td>
         <c:out value="${custPoAddressChangeViewBeanCollection[0].supplementaryAddrCode}"/>
       </td>
      </tr>
             <tr>
        <td class="optionTitleBoldLeft"><fmt:message key="label.docidcode"/>:</td>
        <td>
<c:out value="${custPoAddressChangeViewBeanCollection[0].docIdCode}"/>
       </td>
        <td class="optionTitleBoldLeft"><fmt:message key="label.rdd"/>:</td>
        <td>
         <c:out value="${custPoAddressChangeViewBeanCollection[0].rdd}"/>
       </td>
      </tr>
             <tr>
        <td class="optionTitleBoldLeft"><fmt:message key="label.transportationpriority"/>:</td>
        <td>
         <c:out value="${custPoAddressChangeViewBeanCollection[0].transportationPriority}"/>
       </td>
       <td class="optionTitleBoldLeft"><fmt:message key="label.quantity"/>:</td>
        <td>
<c:out value="${custPoAddressChangeViewBeanCollection[0].quantity}"/>&nbsp;&nbsp;<fmt:message key="label.uom"/>:&nbsp;<c:out value="${custPoAddressChangeViewBeanCollection[0].uom}"/>
       </td>

      </tr>
       
       <tr>
        <td class="optionTitleBoldLeft"><fmt:message key="label.original"/>&nbsp;<fmt:message key="label.shiptoaddress"/>:</td>
        <td>
            ${custPoAddressChangeViewBeanCollection[0].shiptoAddress1}<br/>
            ${custPoAddressChangeViewBeanCollection[0].shiptoAddress2}<br/>
            ${custPoAddressChangeViewBeanCollection[0].shiptoAddress3}<br/>
            ${custPoAddressChangeViewBeanCollection[0].shiptoAddress4}
        </td>
        <td class="optionTitleBoldLeft"><fmt:message key="label.original"/>&nbsp;<fmt:message key="label.markforaddress"/>:</td>
        <td>
            ${custPoAddressChangeViewBeanCollection[0].markForAddress1}<br/>
            ${custPoAddressChangeViewBeanCollection[0].markForAddress2}<br/>
            ${custPoAddressChangeViewBeanCollection[0].markForAddress3}<br/>
            ${custPoAddressChangeViewBeanCollection[0].markForAddress4}
         </td>
      </tr>

<input name="addressChangeRequestId" id="addressChangeRequestId" value="<c:out value="${addressChangeRequestId}"/>" type="hidden" >
<input name="dodaacType" id="dodaacType" value="${custPoAddressChangeViewBeanCollection[0].shipToDodaacType}" type="hidden" >
<input name="mfDodaacType" id="mfDodaacType" value="${custPoAddressChangeViewBeanCollection[0].markForDodaacType}" type="hidden" >
<input name="type" id="type" value="${type}" type="hidden" >
      <tr>
        <td class="optionTitleBoldLeft"><fmt:message key="label.shiptododaac"/>:</td>
        <td><input maxlength="6" size="6" name="dodaac" id="dodaac" type="text" class="invGreyInputText" value="<c:out value="${custPoAddressChangeViewBeanCollection[0].origShipToDodaac}"/>" onfocus="blur();">
            <button name="submitSearch" id="submitSearch" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" value="..." onclick="submitSearchForm('nododaac1','${custPoAddressChangeViewBeanCollection[0].shipToDodaacType}'); return false;"><img src="/images/search_small.gif" alt="Search"></button>
<%--
            <input name="submitSearch" id="submitSearch" type="submit" value="<fmt:message key="label.search"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="submitSearchForm('nododaac1'); return false;">
--%>
        </td>
          <td class="optionTitleBoldLeft"><fmt:message key="label.markfordodaac"/>:</td>
          <td><input maxlength="6" size="6" name="mfDodaac" id="mfDodaac" type="text" class="invGreyInputText" value="<c:out value="${custPoAddressChangeViewBeanCollection[0].origMarkForDodaac}"/>" onfocus="blur();">
<button name="submitSearch" id="submitSearch" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" value="..." onclick="submitSearchForm('nododaac2','${custPoAddressChangeViewBeanCollection[0].markForDodaacType}'); return false;"><img src="/images/search_small.gif" alt="Search"></button>

<%--
              <input name="submitSearch" id="submitSearch" type="submit" value="<fmt:message key="label.search"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="submitSearchForm('nododaac2'); return false;">
--%>
          </td>

      </tr>
<%--
      <tr>
        <td class="optionTitleBoldLeft"><fmt:message key="label.shiptolocationid"/>:</td>
        <td><input name="locationId" id="locationId" type="text" class="inputBox" value="<c:out value="${custPoAddressChangeViewBeanCollection[0].origShipToLocationId}"/>" onfocus="blur();">

        </td>
          <td class="optionTitleBoldLeft"><fmt:message key="label.markforlocationid"/>:</td>
          <td><input name="mfLocationId" id="mfLocationId" type="text" class="inputBox" value="<c:out value="${custPoAddressChangeViewBeanCollection[0].origMarkForLocationId}"/>" onfocus="blur();">
              

          </td>

      </tr>
--%>
     <tr>
        <td class="optionTitleBoldLeft"><fmt:message key="label.shiptoaddress"/>:</td>
        <td>
<input name="locationId" id="locationId" type="hidden" value="<c:out value="${custPoAddressChangeViewBeanCollection[0].origShipToLocationId}"/>">
<input name="mfLocationId" id="mfLocationId" type="hidden" value="<c:out value="${custPoAddressChangeViewBeanCollection[0].origMarkForLocationId}"/>">
          <textarea rows="5" cols="40" name="address" id="address" class="inputBox"  onfocus="blur();">
<c:out value="${custPoAddressChangeViewBeanCollection[0].stAddressLine1Display}"/>
<c:out value="${custPoAddressChangeViewBeanCollection[0].stAddressLine2Display}"/>
<c:out value="${custPoAddressChangeViewBeanCollection[0].stAddressLine3Display}"/>
<c:out value="${custPoAddressChangeViewBeanCollection[0].stAddressLine4Display}"/>
          </textarea></td>
          <td class="optionTitleBoldLeft"><fmt:message key="label.markforaddress"/>:</td>
          <td>
          <textarea rows="5" cols="40" name="mfAddress" id="mfAddress" class="inputBox"  onfocus="blur();">
<c:out value="${custPoAddressChangeViewBeanCollection[0].mfAddressLine1Display}"/>
<c:out value="${custPoAddressChangeViewBeanCollection[0].mfAddressLine2Display}"/>
<c:out value="${custPoAddressChangeViewBeanCollection[0].mfAddressLine3Display}"/>
<c:out value="${custPoAddressChangeViewBeanCollection[0].mfAddressLine4Display}"/>
          </textarea>
           </td>

      </tr>

     <tr>
        <td class="optionTitleBoldLeft"><fmt:message key="label.dpmsreplydate"/> (dd-MMM-yyyy HH:mm am/pm):</td>
        <td>
            <input maxlength="20" name="dpmsReplyDate" id="dpmsReplyDate" type="text" class="inputBox">
        </td>

        <td class="optionTitleBoldRight"><fmt:message key="label.notes"/>:</td>
        <td>
          <textarea rows="5" cols="40" name="notes" id="notes" class="inputBox">
              <c:out value="${custPoAddressChangeViewBeanCollection[0].shippingNotes}"/>
          </textarea>
       </td>
      </tr>

<c:choose>
<c:when test="${custPoAddressChangeViewBeanCollection[0].dateAddressOk == null}">
 <tr>
  <td>
    &nbsp;
  </td>
  <td>
   <input name="submitUpdate" id="submitUpdate" type="submit" value="<fmt:message key="label.update"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return submitUpdateForm();">
  </td>
  <td>
   <input name="submitNew" id="submitNew" type="submit" value="<fmt:message key="label.newaddress"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="submitNewForm('nododaac'); return false;">
  </td>
 </tr>
</c:when>
 <c:otherwise>
 <tr>
   <td class="optionTitleBoldLeft">
      <fmt:message key="label.verifiedby"/>:
    </td>
    <td>
      ${custPoAddressChangeViewBeanCollection[0].verifiedByName}
    </td>

    <td class="optionTitleBoldLeft">&nbsp;</td>
    <td class="optionTitleBoldLeft">&nbsp;</td>
  </tr>
</c:otherwise>
</c:choose>
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

<div class="spacerY">&nbsp;</div>

<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
<!-- Error Messages Ends -->
<%--
<c:if test="${custPoAddressChangeViewBeanCollection != null}" >
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <a href="#" onclick="doSomeThing(); return false;"><fmt:message key="label.createexcelfile"/></a></div>
    <div class="dataContent">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableResults">
    <c:set var="colorClass" value=''/>
    <c:set var="dataCount" value='${0}'/>

    <c:forEach var="custPoAddressChangeViewBean" items="${custPoAddressChangeViewBeanCollection}" varStatus="status">

    <c:if test="${status.index % 10 == 0}">
    <!-- Need to print the header every 10 rows-->
    <tr>
    <th width="5%"><fmt:message key="label.example1"/></th>
    <th width="5%"><fmt:message key="label.example2"/></th>
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

    <tr class="<c:out value="${colorClass}"/>" id="rowId<c:out value="${status.index}"/>">
     <td width="5%"><c:out value="${status.current.addressChangeReuqestId}"/></td>
     <td width="5%"><c:out value="${status.current.origShiptToDodaac}"/></td>
   </tr>
   <c:set var="dataCount" value='${dataCount+1}'/>
   </c:forEach>
   </table>
   <!-- If the collection is empty say no data found -->
   <c:if test="${empty custPoAddressChangeViewBeanCollection}" >

    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData">
     <tr>
      <td width="100%">
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
</td></tr>
</table>
<!-- Search results end -->
</c:if>

<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;"></div>
<!-- Hidden elements end -->
--%>
</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>
