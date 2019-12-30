<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<%--NEW--%>
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>

<script type="text/javascript">
<!--

/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${ empty tcmISError }">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
<c:choose>
   <c:when test="${done == 'Y'}">
    done = true;
   </c:when>
   <c:otherwise>
    done = false;
   </c:otherwise>
</c:choose>

//-->
</script>

<title>
<fmt:message key="label.contactus"/>
</title>


<script language="JavaScript" type="text/javascript">
<!--
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
all:"<fmt:message key="label.all"/>",
maxlength:"<fmt:message key="errors.maxlength"/>",
nodatafound:"<fmt:message key="main.nodatafound"/>"
};

function limitText(id, label, limitNum) {
    var limitCount;
    var limitField  =  document.getElementById(id);
// alert(limitField.value.length);
	if (limitField.value.length > limitNum) {
		limitField.value = limitField.value.substring(0, limitNum);
		var lengthMsg = messagesData.maxlength;
		lengthMsg = lengthMsg.replace(/[{]0[}]/g,label);
		lengthMsg = lengthMsg.replace(/[{]1[}]/g,limitNum);
		alert(lengthMsg);
	} 
}

function submitRequest() {
//	showPageWait();
	$("uAction").value = 'sendEmail';
	document.genericForm.submit();
	window.close();
}

// -->
</script>
</head>

<body bgcolor="#ffffff"  onload="document.genericForm.msg.focus();" onunload="try{opener.closeTransitWin();}catch(ex){}" onresize="resizeFrames()">

<tcmis:form action="/contactus.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="mainPage" style="">

<!-- Error Messages Begins -->

<div id="errorMessagesAreaBody" style="display:none;">
${tcmISError}
</div>
<!-- Error Messages Ends -->

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${ empty tcmISError }">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
//-->
</script>

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">

<%--NEw -Transit Page Starts --%>
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->

<div id="resultGridDiv" style="display:">
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

<div class="dataContent">

<fieldset>
<legend><fmt:message key="label.contactus"/></legend>

<table cellspacing="10">
<tr>
	<td width="2%" class="optionTitleBoldRight" nowrap><fmt:message key="label.from"/>:</td>
	<td width="15%" class="optionTitle">
		${fullName}
	</td>
</tr>
<tr>
	<td width="2%" class="optionTitleBoldRight"><fmt:message key="label.subject"/>:</td>
	<td width="15%" class="optionTitle">
		<select name="subject" id="subject" class="selectBox">
  			<option value="requestConfirmation"><fmt:message key="label.requestconfirmation"/></option>
  			<option value="expediteOrder"><fmt:message key="label.expediteorder"/></option>
  			<option value="other"><fmt:message key="label.other"/></option>
    	</select>
	</td>
</tr>
<tr>
	<td width="2%" class="optionTitleBoldRight">&nbsp;</td>
	<td width="15%" class="optionTitle">
		<TEXTAREA name="msg" id="msg" onKeyDown='limitText("msg", "<fmt:message key="label."/>", 1000);' onKeyUp='limitText("msg", "<fmt:message key="label.content"/>", 1000);' class="inputBox" COLS=60 ROWS=12></TEXTAREA>
	</td>
</tr>
<tr>
	<td colspan="2" width="100%" align="center">
          <input name="okBtn" type="button" class="inputBtns" value="<fmt:message key="button.submit"/>" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
          		 onclick="submitRequest();">
    </td>
</tr>
</table>
<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
<div class="spacerY">&nbsp;
<div id="searchErrorMessagesArea" class="errorMessages">
<html:errors/>
</div>
</div>
</c:if>  

</fieldset>

<!--  result page section start -->
<div id="beanCollDiv" style="height:400px;display: none;" ></div>
</div>

 <%-- result count and time --%>
 <div id="footer" class="messageBar"></div>

  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</td></tr>
</table>
</div>

</div><!-- Result Frame Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input type="hidden" name="uAction" id="uAction" value="${param.uAction}">
<input type="hidden" name="mrNumber" id="mrNumber" value="${param.mrNumber}">
<input type="hidden" name="customerServiceRepEmail" id="customerServiceRepEmail" value="${param.customerServiceRepEmail}">
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>

</body>
</html>