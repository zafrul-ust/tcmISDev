<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html lang="true">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="expires" content="-1"/>
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<!-- Add any other stylesheets you need for the page here -->

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<%--NEW - grid resize--%>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- Add any other javascript you need for the page here -->
<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>





<title>
    <fmt:message key="label.needbytolerance"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={needByTolerance:"<fmt:message key="label.needbytolerance"/>",
errors:"<fmt:message key="label.errorupdating"/>",
integerError:"<fmt:message key="error.integerdaysbetween"/>"};

// Can be removed to show My Entities, My hubs and My Inventory Group
//defaults.ops.nodefault = true;
//defaults.hub.nodefault = true;
// defaults.inv.nodefault = true;

//if hub is changed, call igchanged function
//defaults.hub.callback = igchanged;

// -->

function submitUpdate()
{
 /*Make sure to not set the target of the form to anything other than resultFrame*/	
 var days = $("needByTolerance").value.trim();
 var daysAsInt = parseInt(days);
  if(isInteger(days) && (daysAsInt > -1 && daysAsInt < 31)) 
 {
	try {
	   document.getElementById('uAction').value = 'update';
   	   return true;
  	} catch(exx) {}

  }
  else
  {
	var error = messagesData.integerError;
	error = error.replace(/[{]0[}]/g,messagesData.needByTolerance);
	error = error.replace(/[{]1[}]/g,"0");
	error = error.replace(/[{]2[}]/g,"30");
	alert(error);
   	return false;
  }
}

function onLoad()
{
	if(showErrorMessage)
	{
	  window.opener.parent.showTransitWin(messagesData.needByTolerance);
	  var resulterrorMessages = document.getElementById("errorMessagesAreaBody");
	  var errorMessagesArea = document.getElementById("errorMessagesArea");
	  errorMessagesArea.innerHTML = resulterrorMessages.innerHTML;

	  var errorMessagesArea = document.getElementById("errorMessagesArea");
	  errorMessagesArea.style.display="";

	  var dhxWins = new dhtmlXWindows();
	  dhxWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	  if (!dhxWins.window("errorWin")) {
	  // create window first time
	  var errorWin = dhxWins.createWindow("errorWin", 0, 0, 300, 150);
	  errorWin.setText(messagesData.errors);
	  errorWin.clearIcon();  // hide icon
	  errorWin.denyResize(); // deny resizing
	  errorWin.denyPark();   // deny parking
	  errorWin.attachObject("errorMessagesArea");
	  errorWin.attachEvent("onClose", function(errorWin){errorWin.hide();});
	  errorWin.center();
	  }
	  else
	  {
	    // just show
	    dhxWins.window("errorWin").show();
	  }
	}
	else if (document.getElementById("wAction").value.trim() == "updateSuccess")
	{
	   	window.opener.updateNeedByTolerance($("inventoryGroupName").value, $("needByToleranceSave").value);
		window.close();
	}
}


</script>
</head>
<%-- displayOnly needs to match the pageData in application.jsp if the page will be on the menu --%>
<body bgcolor="#ffffff" onload = "onLoad()" onunload ="window.opener.parent.closeTransitWin();">


<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<%--NEW - removed the search frame and copied the search section here--%>
<div class="contentArea">
<tcmis:form action="/editneedbytolerance.jsp">
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
    <%--  <table width="700" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch"> --%>
    <table width="340" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
     <tr> 
         <td  class="optionTitleBoldLeft" width="125"><fmt:message key="label.inventorygroup"/>:</td>
               <td nowrap class="optionTitleBoldLeft">
          &nbsp;                                                            
          ${param.inventoryGroupName}
       </td>  
    </tr>
    <tr>    
	  <td  class="optionTitleBoldLeft" width="125"><fmt:message key="label.needbytolerance"/>:</td>
      <td nowrap class="optionTitleBoldLeft">
          &nbsp;                                                            
          <input name="needByTolerance" id="needByTolerance" type="text" class="inputBox" size="7" onkeypress="return onKeyPress()"/>
          &nbsp; 
          <fmt:message key="label.dayordays"/>
       </td>        
    </tr>
    <tr>
     <td colspan="2"  class="optionTitleBoldLeft" nowrap>
          <input name="submit" type="submit" class="inputBtns" value="<fmt:message key="label.update"/>" id="submit" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "return submitUpdate()"/>
          <input name="cancel" type="button" class="inputBtns" value="<fmt:message key="label.cancel"/>" id="cancel" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick= "javascript:window.close()"/>
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
<div id="searchErrorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
</c:if>

<!-- Error Messages Ends -->



<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
  <input type="hidden" name="uAction" id="uAction" value=""/>
   <input type="hidden" name="wAction" id="wAction" value="${wAction}"/>
  <input type="hidden" name="inventoryGroupName" id="inventoryGroupName" value="${param.inventoryGroupName}"/>
  <input type="hidden" name="inventoryGroup" id="inventoryGroup" value="${param.inventoryGroup}"/>
  <input type="hidden" name="needByToleranceSave" id="needByToleranceSave" value="${needByToleranceSave}"/>
  <input name="opsEntityId" id="opsEntityId" value="${param.opsEntityId}" type="hidden"/>

 <!-- needed if this page will show on application.do -->
 <!-- To get the correct height value to insert, insert showSearchHeight = true; anywhere in the JavaScript array section in main.jsp.  -->
</div>
<!-- Hidden elements end -->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages">
	<html:errors/>
</div>
<!-- Error Messages Ends -->
<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/>
    ${tcmISError}
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
    </c:forEach>        
</div>

<script type="text/javascript">
   <c:choose>
    <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISError && empty tcmISErrors }">
     showErrorMessage = false;
    </c:when>
    <c:otherwise>
     showErrorMessage = true;
    </c:otherwise>
   </c:choose>   
    //-->       
</script>

<!-- Error Messages Ends -->
</tcmis:form>
</div> <!-- close of contentArea -->

</div>
</div>
</body>
</html>