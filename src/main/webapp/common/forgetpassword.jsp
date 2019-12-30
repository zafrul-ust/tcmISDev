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
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="content-type" content="text/html; charset=iso-8859-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss overflow="auto" />

<script type="text/javascript" src="/js/common/commonutil.js"></script>

<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/forgetpassword.js"></script>
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<c:set var="browserLocale" value="<%=request.getLocale()%>"/>
<c:set var="defaultLocale" value="en_US"/>
	<c:forEach var="localeBean" items="${vvLocaleBeanCollection}"  varStatus="status">
 		<c:if test="${status.current.localeCode == browserLocale }">
		<c:set var="defaultLocale" value="${browserLocale}"/>     
 		</c:if>
	</c:forEach>
<c:if test="${!empty param.langSetting}">
	<c:set var="defaultLocale" value="${param.langSetting}"/>
</c:if>	
		
<script language="JavaScript" type="text/javascript">
<!--
<fmt:setLocale value="en_US" scope="page"/> 
var i18nresetpasswordlabel = new Array();
i18nresetpasswordlabel["en_US"] = {
			label_login:"<fmt:message key="label.logonid"/>",
			btn_newpassword:"<fmt:message key="password.label.passwordnew"/>"
};
<fmt:setLocale value="zh_TW" scope="page"/> 
i18nresetpasswordlabel["zh_TW"] = {
			label_login:"<fmt:message key="label.logonid"/>",
			btn_newpassword:"<fmt:message key="password.label.passwordnew"/>"
};

<fmt:setLocale value="de_DE" scope="page"/>
i18nresetpasswordlabel["de_DE"] = {
			label_login:"<fmt:message key="label.logonid"/>",
			btn_newpassword:"<fmt:message key="password.label.passwordnew"/>"
};

<fmt:setLocale value="pl_PL" scope="page"/>
i18nresetpasswordlabel["pl_PL"] = {
			label_login:"<fmt:message key="label.logonid"/>",
			btn_newpassword:"<fmt:message key="password.label.passwordnew"/>"
};

<fmt:setLocale value="fr_FR" scope="page"/>
i18nresetpasswordlabel["fr_FR"] = {
			label_login:"<fmt:message key="label.logonid"/>",
			btn_newpassword:"<fmt:message key="password.label.passwordnew"/>"
};

<fmt:setLocale value="tr_TR" scope="page"/>
i18nresetpasswordlabel["tr_TR"] = {
			label_login:"<fmt:message key="label.logonid"/>",
			btn_newpassword:"<fmt:message key="password.label.passwordnew"/>"
};

<fmt:setLocale value="zh_CN" scope="page"/>
i18nresetpasswordlabel["zh_CN"] = {
			label_login:"<fmt:message key="label.logonid"/>",
			btn_newpassword:"<fmt:message key="password.label.passwordnew"/>"
};

<fmt:setLocale value="ar_AE" scope="page"/>
i18nresetpasswordlabel["ar_AE"] = {
			label_login:"<fmt:message key="label.logonid"/>",
			btn_newpassword:"<fmt:message key="password.label.passwordnew"/>"
};

<fmt:setLocale value="${defaultLocale}" scope="page"/>

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={
	alert:"<fmt:message key="label.alert"/>",
	and:"<fmt:message key="label.and"/>",
	passwordMatchError:"<fmt:message key="error.password.change"/>",
	passwordReuseError:"<fmt:message key="error.password.reuse"/>",
	errors:"<fmt:message key="label.errors"/>", 
	logonidandcaptcahneeded:"<fmt:message key="label.logonidandcaptcahneeded"/>", 
	submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};

var showErrorMessage = false;

function requestNewPassword() {
	try {
		if($v("logonId").length > 0 && $v("g-recaptcha-response").length > 0) {
			$("uAction").value = "newPassword";
			return true;
		} 
	}
	catch () {}
	
	alert(messagesData.logonidandcaptcahneeded);
	return false;
}

function showErrorMessages() {
  var resulterrorMessages = document.getElementById("errorMessagesAreaBody");
  var errorMessagesArea = document.getElementById("errorMessagesArea");

  errorMessagesArea.style.display="";

  errorMessagesArea.innerHTML = resulterrorMessages.innerHTML;

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

function setLocale() {
	var localarr = i18nresetpasswordlabel[ $v('langSetting') ] ;
	if( localarr == null ) return;
	document.getElementById('label_login').innerHTML = localarr.label_login;
	document.getElementById('newPassword').value = localarr.btn_newpassword;
}

// -->
</script>

<script type="text/javascript">
    // this defines these functions if they don't exist. Needed for: IE7- or compatibility view
if (!document.querySelectorAll) {
    document.querySelectorAll = function(selectors) {
      var style = document.createElement('style'), elements = [], element;
      document.documentElement.firstChild.appendChild(style);
      document._qsa = [];

      style.styleSheet.cssText = selectors + '{x-qsa:expression(document._qsa && document._qsa.push(this))}';
      window.scrollBy(0, 0);
      style.parentNode.removeChild(style);

      while (document._qsa.length) {
        element = document._qsa.shift();
        element.style.removeAttribute('x-qsa');
        elements.push(element);
      }
      document._qsa = null;
      return elements;
    };
  }

  // Document.querySelector method
  // Needed for: IE7- or compatibility view
  if (!document.querySelector) {
    document.querySelector = function(selectors) {
      var elements = document.querySelectorAll(selectors);
      return (elements.length) ? elements[0] : null;
    };
  }
</script>

<title>
<fmt:message key="label.resetpassword"/>
</title>
<c:choose>
	<c:when test="${defaultLocale == 'de_DE'}">
		 <script src='https://www.google.com/recaptcha/api.js?hl=de'></script>
	</c:when>	
	<c:when test="${defaultLocale == 'it_IT'}">
		 <script src='https://www.google.com/recaptcha/api.js?hl=it'></script>
	</c:when>
	<c:when test="${defaultLocale == 'pl_PL'}">
		 <script src='https://www.google.com/recaptcha/api.js?hl=pl'></script>
	</c:when>
	<c:when test="${defaultLocale == 'fr_FR'}">
		 <script src='https://www.google.com/recaptcha/api.js?hl=fr'></script>
	</c:when>
	<c:when test="${defaultLocale == 'zh_CN'}">
		 <script src='https://www.google.com/recaptcha/api.js?hl=zh_CN'></script>
	</c:when>
	<c:when test="${defaultLocale == 'tr_TR'}">
		 <script src='https://www.google.com/recaptcha/api.js?hl=tr'></script>
	</c:when>
	<c:otherwise>
		<script src='https://www.google.com/recaptcha/api.js'></script>
	</c:otherwise>
</c:choose>

</head>

<body bgcolor="#ffffff" onload="setLocale();if(showErrorMessage) setTimeout('showErrorMessages()',50);">

<tcmis:form action="/forgetpassword.do" onsubmit="return requestNewPassword() && submitOnlyOnce();">

<!-- Transit Page Begins -->
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
 	<br/><fmt:message key="label.pleasewait"/>
  <br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/>
</div>
<!-- Transit Page Ends -->

<div class="interface" id="mainPage">

<%-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure --%>
<div class="topNavigation" id="topNavigation">

</div>

<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="300" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
   <c:choose>
		    <c:when test="${updateStatus == 'success'}">
    <!-- Insert all the search option within this div -->
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
          <tr>
            <td width="100%" class="optionTitleBoldCenter">
              <fmt:message key="label.newpasswordsent"/>&nbsp;
            </td>
          </tr>
			    <tr>
            <td width="100%" class="optionTitleBoldCenter">
	            <input name="close" id="close" type="button" value="<fmt:message key="label.close"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick="window.close();">
            </td>
		  </tr>
    </table>
    </c:when>
	<c:otherwise>
		<div class="optionTitleBoldLeft">&nbsp;&nbsp;&nbsp;
			<span id="label_login"><fmt:message key="label.logonid"/></span>:
			<input class="inputBox" type="text" name="logonId" id="logonId" value='<tcmis:inputTextReplace value="${param.logonId}" processMultiLines="true" />' size="10" size="15">
		</div><br/>
		<div class="g-recaptcha" data-sitekey="6Lf7k1EUAAAAAFn9IJQtw6lLUB_Qv-f_oN2chj1q"></div><br/>
		<div class="optionTitleBoldLeft">&nbsp;&nbsp;&nbsp;
			<input name="newPassword" id="newPassword" type="submit" class="inputBtns" value="<fmt:message key="password.label.passwordnew"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" >
		</div>
		
	</c:otherwise>
</c:choose>
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

<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/>
    ${tcmISError}<br/>
    ${requestScope['org.apache.struts.action.ERROR']}    
</div>


<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISError }"> 
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
//-->
</script>
<!-- Error Messages Ends -->

<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;">
<input name="uAction" id="uAction" value="newPassword" type="hidden">
<input name="langSetting" id="langSetting" value="${param.langSetting}" type="hidden">
</div>
<!-- Hidden elements end -->

</div> <!-- close of content area -->
</div> <!-- close of interface -->

</tcmis:form>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>
</body>
</html:html>

