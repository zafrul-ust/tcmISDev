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
<fmt:message key="updatereceiptnotes.title"/>
</title>

</head>

<body bgcolor="#ffffff">


<tcmis:form action="/updatereceiptnotes.do" onsubmit="return submitOnlyOnce();">

 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
  <br><br><br><fmt:message key="label.pleasewait"/>
 </div>

<div class="contentArea" id="mainPage">

<!-- Error Messages Begins -->
<div class="spacerY">&nbsp;
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
<!-- Error Messages Ends -->

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
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">

    <c:if test="${! empty receipt}">
    <tr>
      <td width="5%" class="optionTitleBoldRight" nowrap><fmt:message key="label.receiptid"/>:</td>
      <td width="20%">
        <c:out value="${param.receiptId}"/>
        <input type="hidden" name="receiptId" id="receiptId" value='<c:out value="${param.receiptId}"/>'>
      </td>
    </tr>
    <tr>
      <td width="5%" class="optionTitleBoldRight" nowrap><fmt:message key="label.notes"/>:&nbsp;</td>
      <td width="20%">
        <textarea name="notes" id="notes" rows="5" cols="70" class="inputBox"><c:out value="${receipt.notes}"/></textarea>
      </td>
    </tr>
    <tr>
      <td width="5%" class="optionTitleBoldRight" nowrap><fmt:message key="label.internalreceiptnotes"/>:&nbsp;</td>
      <td width="20%">
        <textarea name="internalReceiptNotes" id="internalReceiptNotes" rows="5" cols="70" class="inputBox"><c:out value="${receipt.internalReceiptNotes}"/></textarea>
      </td>
    </tr>
    <tr><td colspan=6>&nbsp;</td></tr>
    <tr>
      <td width="5%" class="optionTitleBoldRight">&nbsp;</td>
      <td width="20%">
        <input name="addNotes" type="submit" class="inputBtns" value="add" id="addNotes" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'">
        &nbsp;&nbsp;&nbsp;
        <input name="cancel" type="button" class="inputBtns" value="cancel" id="cancel" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'" onclick='closeWindow();'>
      </td>
    </tr>
    </c:if>

    <c:if test="${! empty receiptMessage}">
    <tr>
      <td width="100%" class="optionTitleLeft">
        <fmt:message key="updatereceiptnotes.notesforreceipt"/>&nbsp;<c:out value="${receiptMessage}"/>&nbsp;<fmt:message key="updatereceiptnotes.addedsuccessfully"/><br><br>
        <fmt:message key="updatereceiptnotes.refreshscreen"/>
      </td>
    </tr>
    <tr>
     <td width="100%" class="optionTitleCenter">
        <input name="ok" type="button" class="inputBtns" value="ok" id="ok" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'" onclick='closeWindow();'>
     </td>
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

</tcmis:form>

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;"></div>
<!-- Hidden elements end -->

</div> <!-- close of mainpage -->

</body>
</html:html>

