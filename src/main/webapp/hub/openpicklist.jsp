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

<script src="/js/common/formchek.js" language="JavaScript"></script>
<script src="/js/common/commonutil.js" language="JavaScript"></script>

<%-- For Calendar support --%>
<%--
<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>
--%>

<%-- Add any other javascript you need for the page here --%>
<script src="/js/hub/picklistpicking.js" language="JavaScript"></script>

<title>
<fmt:message key="openpicklist.title"/>
</title>

<script language="JavaScript" type="text/javascript">
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={     alert:"<fmt:message key="label.alert"/>",
                     and:"<fmt:message key="label.and"/>",
          submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
          pleaseSelectId:"<fmt:message key="openpicklist.alert"/>"};
</script>
</head>

<body bgcolor="#ffffff">


<tcmis:form action="/openpicklist.do" onsubmit="return submitOnlyOnce();">

 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;text-align:center;">
  <br><br><br><fmt:message key="label.pleasewait"/>
 </div>
 <div class="interface" id="mainPage">

<%-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure --%>
<div class="topNavigation" id="topNavigation">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr valign="top">
<td width="200">
<img src="/images/tcmtitlegif.gif" align="left">
</td>
<td align="right">
<img src="/images/tcmistcmis32.gif" align="right">
</td>
</tr>
</table>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td width="70%" class="headingl">
<fmt:message key="openpicklist.title"/>
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
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
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
        <td width="50%" class="optionTitleBoldCenter" colspan=2><fmt:message key="openpicklist.pleaseselect"/></td>
      </tr>
      <tr>
        <td width="50%" class="optionTitleBoldCenter" colspan=2>
           <select name="picklistId" class="selectBox" id="picklistId">
               <option value='0'><fmt:message key="openpicklist.idtime"/></option>
             <c:forEach var="picklist" items="${openPicklistColl}" varStatus="status">        
               <fmt:formatDate var="fmtTimeGenerated" value="${picklist.picklistPrintDate}" pattern="yyyy-MM-dd HH:mm:ss.S"/>                             
               <option value='<c:out value="${picklist.picklistId}"/>'><fmt:message key="openpicklist.picklist.prefix"/> <c:out value="${picklist.picklistId}"/> : <c:out value="${fmtTimeGenerated}"/></option>
             </c:forEach>
           </select>   
        </td>
      </tr>
      <tr>
        <td width="20%" class="optionTitleBoldRight">
           <input name="submitSearch" id="submitSearch" type="submit" class="inputBtns" value='<fmt:message key="label.ok"/>' onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="return validateOpenPicklist();">
        </td>
        <td width="20%" class="optionTitleBoldLeft">
           <input name="cancel" id="cancel" type="button" class="inputBtns" value='<fmt:message key="label.cancel"/>' onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="window.close();">
        </td>
      </tr>
    </table>    
    <input type="hidden" name='hub' id='hub' value='<c:out value="${param.hub}"/>'>
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
<c:if test="${! empty openPicklistError}">
<c:out value="${openPicklistError}"/>
</c:if>
</div>
</div>
<!-- Error Messages Ends -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;">
   <input name="action" id="action" type="hidden" value="createExcel">
 </div>
<!-- Hidden elements end -->

</div> <!-- close of content area -->
</div> <!-- close of interface -->
</tcmis:form>
</body>
</html:html>
