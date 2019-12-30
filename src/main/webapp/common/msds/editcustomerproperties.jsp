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
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<!--Use this tag to get the correct CSS class.
This looks at what the user's prefered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<script type="text/javascript" src="/js/common/searchiframeresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/catalog/msdsshareauditdata.js"></script>

<!-- These are for the Transit Window -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<title>
<fmt:message key="label.editcustomerproperties"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
waitingforinputfrom:"<fmt:message key="label.waitingforinputfrom"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
floatError:"<fmt:message key="errors.float"/>",
rangeError:"<fmt:message key="errors.range"/>",
integerError:"<fmt:message key="errors.integer"/>",
percent:"<fmt:message key="label.percent"/>",
percentUpper:"<fmt:message key="label.upperpercent"/>",
percentLower:"<fmt:message key="label.lowerpercent"/>",
nfpaHealth:"<fmt:message key="label.nfpahealth"/>",
nfpaFlammability:"<fmt:message key="label.nfpaflammability"/>",
nfpaReactivity:"<fmt:message key="label.nfpareactivity"/>",
hmisHealth:"<fmt:message key="label.hmishealth"/>",
hmisFlammability:"<fmt:message key="label.hmisflammability"/>",
hmisReactivity:"<fmt:message key="label.hmisreactivity"/>",
specificGravity:"<fmt:message key="label.specificgravity"/>",
density:"<fmt:message key="label.density"/>",
flashPoint:"<fmt:message key="label.flashpoint"/>",
voc:"<fmt:message key="label.voc"/>",
vocLower:"<fmt:message key="label.voclower"/>",
vocUpper:"<fmt:message key="label.vocupper"/>",
vocLessH2oExempt:"<fmt:message key="label.voclesswaterandexempt"/>",
vocLessH2oExemptLower:"<fmt:message key="label.voclesswaterandexemptlower"/>",
vocLessH2oExemptUpper:"<fmt:message key="label.voclesswaterandexemptupper"/>",
solids:"<fmt:message key="label.solids"/>",
solidsLower:"<fmt:message key="label.solidslower"/>",
solidsUpper:"<fmt:message key="label.solidsupper"/>",
vaporPressure:"<fmt:message key="label.vaporpressure"/>",
vaporPressureLower:"<fmt:message key="label.vaporpressurelower"/>",
vaporPressureUpper:"<fmt:message key="label.vaporpressureupper"/>",
vaporPressureTemp:"<fmt:message key="label.vaporpressuretemp"/>",
vaporDensity:"<fmt:message key="label.vapordensity"/>",
ph:"<fmt:message key="label.ph"/>",
boilingPoint:"<fmt:message key="label.boilingpoint"/>",
itemInteger:"<fmt:message key="error.item.integer"/>",
phRange:"<fmt:message key="errors.range"/>",
range:"<fmt:message key="label.wrongrange"/>",
nounit:"<fmt:message key="label.selectunit"/>"
};

var windowCloseOnEsc = true;

function myOnload() {
	updateCustomerOverride();
	if( '${done}' == 'Y' ) {
		openWinGeneric('viewmsds.do?act=msds'+
			'&materialId='+$v("materialId") +
			'&spec=' + // do we need to know spec id?
			'&currentView=properties' +
			'&facility=' + encodeURIComponent($v('facility')) +
			'&catpartno='
			,"ViewMSDS","1024","720",'yes' );
			
		window.close();
	}
}

function updateByCustomer() {
    if (auditCustomerData()) {
        showTransitWin('<fmt:message key="label.update"/>');
        $("uAction").value = "update";
        document.genericForm.submit();
    }
}

var dhxFreezeWins = null;
function initializeDhxWins() {
	if (dhxFreezeWins == null) {
		dhxFreezeWins = new dhtmlXWindows();
		dhxFreezeWins.setImagePath("/dhtmlxWindows/codebase/imgs/");
	}
}

function showTransitWin(messageType)
{
	var waitingMsg = messagesData.waitingforinputfrom+"...";
	$("transitLabel").innerHTML = waitingMsg.replace(/[{]0[}]/g,messageType);

	var transitDailogWin = document.getElementById("transitDailogWin");
	transitDailogWin.innerHTML = document.getElementById("transitDailogWinBody").innerHTML;
	transitDailogWin.style.display="";

	initializeDhxWins();
	if (!dhxFreezeWins.window("transitDailogWin")) {
		// create window first time
		transitWin = dhxFreezeWins.createWindow("transitDailogWin",0,0, 400, 200);
		transitWin.setText("");
		transitWin.clearIcon();  // hide icon
		transitWin.denyResize(); // deny resizing
		transitWin.denyPark();   // deny parking

		transitWin.attachObject("transitDailogWin");
		//transitWin.attachEvent("onClose", function(inputWin){inputWin.hide();});
		transitWin.center();
		// setting window position as default x,y position doesn't seem to work, also hidding buttons.
		transitWin.setPosition(328, 131);
		transitWin.button("minmax1").hide();
		transitWin.button("park").hide();
		transitWin.button("close").hide();
		transitWin.setModal(true);
	}else{
		// just show
		transitWin.setModal(true);
		dhxFreezeWins.window("transitDailogWin").show();
	}
}


// -->
</script>


</head>
<body bgcolor="#ffffff" onload="myOnload();" onunload="try{opener.parent.closeTransitWin();}catch(ex){}">

<!--Uncomment for production-->

<html:form action="/editcustomerproperties.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="searchMainPage"> <!-- Start of interface-->

<div class="contentArea"> <!-- Start of contentArea-->

<div id="transitDailogWin" class="errorMessages" style="display:none;left:20%;top:20%;z-index:5;">
</div>
	 <%-- freeze --%>
<div id="transitDailogWinBody" class="errorMessages" style="display: none;">
	<table width="100%" border="0" cellpadding="2" cellspacing="1">
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td align="center" id="transitLabel">
			</td>
		</tr>
		<tr>
			<td align="center">
				<img src="/images/rel_interstitial_loading.gif" align="middle">
			</td>
		</tr>
	</table>
</div>
<table id="searchMaskTable" width="420" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<c:set var="index" value="0"/>
<%@ include file="/catalog/customerOverride.jsp" %>
</td></tr>
</table>
 
<!-- Search Option Ends -->

<!-- Error Messages Begins -->
<!-- Build this section only if there is an error message to display.
     For the search section, we show the error messages within its frame
-->
<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null || !empty errorMsg}">
<div class="spacerY">&nbsp;
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
${errorMsg}
</div>
</div>
</c:if>
<!-- Error Messages Ends -->

</div> <!-- close of contentArea -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="uAction" id="uAction" type="hidden" value=""/>	
<input name="materialId" id="materialId" type="hidden" value="${param.materialId}"/>
<input name="revisionDate" id="revisionDate" type="hidden" value="${param.revisionDate}"/>
<input name="facility" id="facility" type="hidden" value="${param.facility}"/>
<input name="catpartno" id="catpartno" type="hidden" value="${param.catpartno}"/>	
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

<!--Uncomment for production-->
</html:form>
</body>
</html:html>