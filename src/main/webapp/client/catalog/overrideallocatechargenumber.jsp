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

<link rel="stylesheet" type="text/css" href="/css/haasGlobal.css"></link>
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<title>
<fmt:message key="label.transferfromprogram"/>
</title>

</head>

<body bgcolor="#ffffff">
<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};

var windowCloseOnEsc = true;
function closeWindow() {
    window.close();
}

function submitScreenData() {
    var chargeNumber1 = "";
    var chargeNumber2 = "";
    var chargeNumber3 = "";
    var chargeNumber4 = "";
    try {
        if ($v("chargeNumber1").length > 0)
            chargeNumber1 = $v("chargeNumber1");
    }catch (e){}
    try {
        if ($v("chargeNumber2").length > 0)
            chargeNumber2 = $v("chargeNumber2");
    }catch (e){}
    try {
        if ($v("chargeNumber3").length > 0)
            chargeNumber3 = $v("chargeNumber3");
    }catch (e){}
    try {
        if ($v("chargeNumber4").length > 0)
            chargeNumber4 = $v("chargeNumber4");
    }catch (e){}

    opener.overrideAllocateChargeNumberData(chargeNumber1,chargeNumber2,chargeNumber3,chargeNumber4);
    closeWindow();
}

// -->
</script>

<tcmis:form action="/materialrequest.do" onsubmit="return SubmitOnlyOnce();">

 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
  <br><br><br><fmt:message key="label.pleasewait"/>
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
			 <td class="optionTitleBoldLeft">
				<a href="javascript:submitScreenData()"><fmt:message key="label.update"/></a> |
				<a href="javascript:closeWindow()"><fmt:message key="label.cancel"/></a>
			 </td>
		 </tr>

		<c:if test="${param.allocateByChargeId1 == 'Y'}" >
            <tr>
                <td class="optionTitleBoldRight">
                    ${param.chargeLabel1} :
                </td>
                <td class="optionTitleBoldLeft">
                  <input class="inputBox" id="chargeNumber1" type="text" name="chargeNumber1" value="<c:out value="${param.allocateByChargeNumber1}"/>" size="25">
                </td>
            </tr>
        </c:if>
        <c:if test="${param.allocateByChargeId2 == 'Y'}" >
            <tr>
                <td class="optionTitleBoldRight">
                    ${param.chargeLabel2} :
                </td>
                <td class="optionTitleBoldLeft">
                  <input class="inputBox" id="chargeNumber2" type="text" name="chargeNumber2" value="<c:out value="${param.allocateByChargeNumber2}"/>" size="25">
                </td>
            </tr>
        </c:if>
        <c:if test="${param.allocateByChargeId3 == 'Y'}" >
            <tr>
                <td class="optionTitleBoldRight">
                    ${param.chargeLabel3} :
                </td>
                <td class="optionTitleBoldLeft">
                  <input class="inputBox" id="chargeNumber3" type="text" name="chargeNumber3" value="<c:out value="${param.allocateByChargeNumber3}"/>" size="25">
                </td>
            </tr>
        </c:if>
        <c:if test="${param.allocateByChargeId4 == 'Y'}" >
            <tr>
                <td class="optionTitleBoldRight">
                    ${param.chargeLabel4} :
                </td>
                <td class="optionTitleBoldLeft">
                  <input class="inputBox" id="chargeNumber4" type="text" name="chargeNumber4" value="<c:out value="${param.allocateByChargeNumber4}"/>" size="25">
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

<div class="spacerY">&nbsp;</div>

<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
<!-- Error Messages Ends -->



<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;"></div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>
