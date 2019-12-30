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
<%--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/catalog/newmanufacturer.js"></script>


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
<fmt:message key="label.catalogaddqcnewmanufacturer"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
function doCallback() {
	opener.manufacturerChanged('${mfg.mfgId}', '${mfg.mfgDesc}','${mfg.mfgShortName}', '${mfg.phone}', '${mfg.email}', '${mfg.contact}', '<tcmis:jsReplace value="${mfg.notes}" processMultiLines="true" />', '${mfg.mfgUrl}' );
	window.close();
}
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
pinputmdesc:"<fmt:message key="label.pinputmdesc"/>"
};
// -->
</script>
</head>

<body bgcolor="#ffffff" <c:if test="${not empty mfg}">onload="doCallback();"</c:if> onunload="parent.opener.stopShowingWait();">

<tcmis:form action="/manufacturersearchresults.do" onsubmit="return window.onunload=null && submitOnlyOnce();">

 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
 </div>
 <div class="interface" id="mainPage">

<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="570" border="0" cellpadding="0" cellspacing="0">
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
        	<td class="optionTitleBoldRight" width="25%" ><fmt:message key="label.manufacturerid"/>:</td>
        	<td width="75%" >${mfgId}<input name="mfgId" id="mfgId" type="hidden" value="${mfgId}"/></td>
      	</tr>
      	<tr>
        	<td class="optionTitleBoldRight" width="25%" ><fmt:message key="label.manufacturerdescription"/>:</td>
        	<td width="75%" ><input name="mfgDesc" id="mfgDesc" type="text" class="inputBox" value="${param.mfgDesc}" size="50" maxlength="60"></td>
      	</tr>
        <tr>
        	<td class="optionTitleBoldRight" width="25%" ><fmt:message key="label.shortname"/>:</td>
        	<td width="75%" ><input name="mfgShortName" id="mfgShortName" type="text" class="inputBox" value="${param.mfgShortName}" size="50" maxlength="60"></td>
      	</tr>
        <tr>
        	<td class="optionTitleBoldRight" width="25%" ><fmt:message key="label.manufacturerurl"/>:</td>
        	<td width="75%" ><input name="mfgUrl" id="mfgUrl" type="text" class="inputBox" value="${mfgUrl}" size="60" maxlength="200"/></td>
      	</tr>
      	<tr>
        	<td class="optionTitleBoldRight" width="25%" ><fmt:message key="label.contact"/>:</td>
        	<td width="75%" ><input name="contact" id="contact" type="text" class="inputBox" value="${contact}" size="40" maxlength="50"></td>
      	</tr>
      	<tr>
        	<td class="optionTitleBoldRight" width="25%" ><fmt:message key="label.email"/>:</td>
        	<td width="75%" ><input name="email" id="email" type="text" class="inputBox" value="${email}" size="40" maxlength="100"></td>
      	</tr>
      	<tr>
        	<td class="optionTitleBoldRight" width="25%" ><fmt:message key="label.phonenumber"/>:</td>
        	<td width="75%" ><input name="phone" id="phone" type="text" class="inputBox" value="${phone}" size="20" maxlength="40"></td>
      	</tr>
      	<tr>
	        <td class="optionTitleBoldRight"><fmt:message key="label.country"/>:</td>
	        <td >
	         <select name="countryAbbrev" id="countryAbbrev" class="selectBox">
	         	<option value="" selected></option>
	             <c:forEach var="bean" items="${vvCountryBeanCollection}" varStatus="status">
	                <option value="${bean.countryAbbrev}">${bean.country}</option>
     	             </c:forEach>
	          </select>
	       </td>
      	</tr>
      	<tr>
        	<td class="optionTitleBoldRight" width="25%" ><fmt:message key="label.notes"/>:</td>
        	<td width="75%" ><textarea name="notes" id="notes" type="text" class="inputBox" value="${notes}" rows="3" cols="60" ></textarea></td>
      	</tr>
    	<tr>
      		<td width="25%" class="optionTitleBoldRight">
				<input type="submit" class="inputBtns" name="submitNew" id="submitNew" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.add"/>" 
				   		onclick="if(document.getElementById('mfgDesc').value.trim()==''){alert(messagesData.pinputmdesc);return false;}return true;" />
      		</td>
      		<td width="75%"></td>
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

<div class="spacerY">&nbsp;</div>

<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
<!-- Error Messages Ends -->



<!-- Hidden element start -->
	 <div id="hiddenElements" style="display: none;"></div>
	 <input name="uAction" id="userAction" type="hidden" value="refresh"/>
 
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->

</tcmis:form>
</body>
</html:html>