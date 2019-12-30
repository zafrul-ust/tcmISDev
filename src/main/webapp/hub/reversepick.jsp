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

<script SRC="/js/common/formchek.js" language="JavaScript"></script>
<script SRC="/js/common/commonutil.js" language="JavaScript"></script>

<!-- This handles the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<!-- This handles which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js" language="JavaScript"></script>

<title>
<fmt:message key="reversepick.title"/>
</title>
</head>

<body bgcolor="#ffffff">


<tcmis:form action="/reversepick.do" onsubmit="return submitOnlyOnce();">

<div class="width">
 <div class="minwidth">
 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;text-align:center;">
  <br><br><br><fmt:message key="label.pleasewait"/>
 </div>
 <div class="interface" id="mainPage">

<!-- Search Option Begins -->
<div class="contentArea">
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->    
    <table width="255" border="0" cellpadding="0" cellspacing="0">
    
    <c:if test="${empty successBean}">
    <tr>
      <td width="85%" class="optionTitleBoldCenter"><fmt:message key="reversepick.reversemrline"/>: <c:out value="${reverseBean.prNumber}"/>-<c:out value="${reverseBean.lineItem}"/></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td width="85%" class="optionTitleCenter">
       <button class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'" name="reverseSmall" id="reverseSmall" value=""  onClick="javascript:document.getElementById('reverse').value = 'reverse';document.genericForm.submit();"><fmt:message key="button.yes"/> </button>         
        &nbsp;&nbsp;&nbsp;
        <button class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'" name="cancel" id="cancel" value=""  onClick='closeWindow();'><fmt:message key="button.cancel"/> </button>         
      </td>
       <input type="hidden" name="reverse" id ="reverse" value=''/>
      <input type="hidden" name="prNumber" value='<c:out value="${reverseBean.prNumber}"/>'>
      <input type="hidden" name="lineItem" value='<c:out value="${reverseBean.lineItem}"/>'>
      <input type="hidden" name="picklistId" value='<c:out value="${reverseBean.picklistId}"/>'>
    </tr>
    </c:if>    
    <c:if test="${! empty successBean}">
    <tr>
      <td width="85%" class="optionTitleBoldCenter">
        <fmt:message key="label.success"/><br>
      </td>
    </tr>
    <tr>
      <td width="85% class="optionTitleCenter">       
        <fmt:message key="label.mrline"/>: <c:out value="${reverseBean.prNumber}"/>-<c:out value="${reverseBean.lineItem}"/> <fmt:message key="reversepick.reversesuccessfull"/>
      	  <br/>
      	  <br/>
      	  <fmt:message key="label.refreshmainpage"/>
      </td>
    </tr>
    <tr>
      <td>&nbsp;</td>
    </tr>
    <tr>
       <td class="optionTitleCenter">
       <button class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onMouseout="this.className='smallBtns'" name="ok" id="ok" value=""  onClick='javascript:opener.parent.submitSearchForm();window.close()'><fmt:message key="label.ok"/> </button>         
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
<!-- Search Option Ends --> 

</tcmis:form>
 
<!-- Error Messages Begins -->
<div class="spacerY">&nbsp;
<div id="errorMessagesArea" class="errorMessages" style="display:none;">
<html:errors/>
</div>
</div>
<!-- Error Messages Ends -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;"></div>
<!-- Hidden elements end -->

</div> <!-- close of width -->
</div> <!-- close of minwidth -->
</div> <!-- close of interface -->

</body>
</html:html>

