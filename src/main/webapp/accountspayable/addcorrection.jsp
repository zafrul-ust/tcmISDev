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

<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />

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

<!-- For Calendar support -->
<%--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>

<!-- Add any other javascript you need for the page here -->


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
<fmt:message key="label.addcreditcorrectionline"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};
// -->
</script>
</head>

<body bgcolor="#ffffff">

<!--Uncomment for production-->
<%--<tcmis:form action="/addcorrectionline.do" onsubmit="return submitOnlyOnce();">--%>

<!-- Transit Page Begins -->
<div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
 <br><br><br><fmt:message key="label.pleasewait"/>
 <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->

<div class="interface" id="mainPage"> <!-- start of interface-->

<div class="contentArea">  <!-- start of contentArea-->

<!-- Search Option Begins -->
<table id="searchMaskTable" width="400" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->
    
    
    <!-- Search Option Table Begins -->
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
      <tr>
        <td width="30%" class="optionTitleBoldRight" colspan="2"><fmt:message key="label.invoice"/>:</td>
        <td width="70%" class="optionTitleBoldLeft">
          <!-- Use this for dropdowns you build with collections from the database -->
          <select name="invoice" id="invoice" class="selectBox">
            <option value="1316481" selected>06080335</option>
          </select>
       </td>
      </tr>
      <tr>
        <td width="30%" class="optionTitleBoldRight" colspan="2"><fmt:message key="label.reference"/>:</td>
        <td width="70%" ><input name="reference" id="reference" type="text" class="inputBox"></td>
      </tr>

      <tr>
        <td width="10%" ></td>
      
        <td width="20%" class="optionTitleBoldCenter"><fmt:message key="label.qty"/>:</td>
        <td width="70%" class="optionTitleBoldCenter"><fmt:message key="label.unitprice"/>:</td>
      </tr>
      
      <tr>
        <td width="10%" class="optionTitleBoldCenter"><fmt:message key="label.credit"/>:</td>
        <td width="20%" class="optionTitleBoldCenter"><input name="creditQty" id="creditQty" type="text" class="inputBox" value="<c:out value="${status.current.creditQty}"/>"></td>
        <td width="70%" class="optionTitleBoldCenter"><input name="creditUnitPrice" id="creditUnitPrice" type="text" class="inputBox" value="<c:out value="${status.current.creditUnitPrice}"/>"></td>        
      </tr>

      <tr>
        <td width="10%" class="optionTitleBoldCenter"><fmt:message key="label.debit"/>:</td>
        <td width="20%" class="optionTitleBoldCenter"><input name="debitQty" id="debitQty" type="text" class="inputBox" value="<c:out value="${status.current.debitQty}"/>"></td>
        <td width="70%" class="optionTitleBoldCenter"><input name="debitUnitPrice" id="debitUnitPrice" type="text" class="inputBox" value="<c:out value="${status.current.debitUnitPrice}"/>"></td>        
      </tr>
           
      <tr>
      <td width="10%" >
      </td>
      <td width="90%" class="optionTitleBoldLeft">
       <input name="submit" id="submit" type="submit" value="<fmt:message key="label.ok"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
      
       <input name="cancel" id="cancel" type="button" value="<fmt:message key="label.cancel"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'">
      </td>
      </tr>
      
    </table>
    <!-- Search Option Table Ends -->
    
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

<!--Uncomment for production-->
<%--<c:if test="${addcorrectionlineViewBeanCollection != null}" >--%>

<!-- Search results start -->

<!-- Search results end -->
<!--Uncomment for production-->
<%--</c:if>--%>

<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;"></div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->

<%--</tcmis:form>--%>
</body>
</html:html>