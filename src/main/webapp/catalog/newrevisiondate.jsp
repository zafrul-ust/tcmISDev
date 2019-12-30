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

<%@ include file="/common/locale.jsp" %>

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

<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
<script type="text/javascript" src="/js/calendar/calendarval.js"></script>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/catalog/newrevisiondate.js"></script>


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

<!--  These are for the autocomplete function -->
<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script> 
<script type="text/javascript" src="/js/jquery/autocomplete.js"></script> 
<link rel="stylesheet" type="text/css" href="/css/autocomplete.css" />  
  
  
<title>
<fmt:message key="label.newrevdate"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--

j$().ready(function() {
	function log(event, data, formatted) {
		if(j$('#msdsAuthorId').val != null && formatted != null) {
			j$('#msdsAuthorId').val(formatted.split("::")[0]);
			j$('#countryAbbrev').val(formatted.split("::")[2]);
			j$('#notes').val(formatted.split("::")[3]);
			$("msdsAuthorDesc").className = "inputBox"; 
		}
	} 
	
	j$("#msdsAuthorDesc").autocomplete("msdsauthorsearch.do",{
			extraParams: {msdsAuthorDesc: function() { return j$("#msdsAuthorDesc").val(); } },
			width: 400,
			max: 100,  // default value is 10
			cacheLength:0, // disable cache here because we put "rownum < max" for better performance. Cache will make data off.
			scrollHeight: 300,
			formatItem: function(data, i, n, value) {
				return  value.split("::")[0]+" "+value.split("::")[1].substring(0,value.length);
			},
			formatResult: function(data, value) {
				return value.split("::")[1];
			}
	});
	
	j$('#msdsAuthorDesc').bind('keyup',(function(e) {
		  var keyCode = (e.keyCode ? e.keyCode : e.which);

		  if( keyCode != 13 && keyCode != 9) // 13 is for Enter; 9 is for Tab
		  	invalidateAuthor();
	}));
	
	j$("#msdsAuthorDesc").result(log).next().click(function() {
		j$(this).prev().search();
	});
}); 

function invalidateAuthor()
{
 var msdsAuthorDesc  =  document.getElementById("msdsAuthorDesc");
 var msdsAuthorId  =  document.getElementById("msdsAuthorId");
 if (msdsAuthorDesc.value.length == 0) {
	 msdsAuthorDesc.className = "inputBox";
 }else {
	 msdsAuthorDesc.className = "inputBox red";
 }
 //set to empty
 msdsAuthorId.value = "";
 }

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={
		alert:"<fmt:message key="label.alert"/>",
		and:"<fmt:message key="label.and"/>",
		validvalues:"<fmt:message key="label.validvalues"/>",
		submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
		revDateRequired:"<fmt:message key="errors.required"><fmt:param><fmt:message key="label.revisiondate"/></fmt:param></fmt:message>",
		authorRequired:"<fmt:message key="errors.required"><fmt:param><fmt:message key="label.msdsauthor"/></fmt:param></fmt:message>"
		};
// -->
</script>
</head>

<body bgcolor="#ffffff" <c:choose><c:when test="${not empty newRevDate}">onload="opener.setNewRevisionDate(document.getElementById('revDate').value, '${param.msdsAuthorId}', '<tcmis:jsReplace value="${param.msdsAuthorDesc}" processMultiLines="true" />', '<tcmis:jsReplace value="${param.countryAbbrev}" processMultiLines="true" />', '<tcmis:jsReplace value="${param.notes}" processMultiLines="true" />');window.close();"</c:when><c:otherwise>onload="getCalendar(document.getElementById('revDate'));"</c:otherwise></c:choose> onunload="parent.opener.stopShowingWait();">
<tcmis:form action="/newrevisiondate.do" onsubmit="return submitOnlyOnce();">
 <div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
 </div>
 <div class="interface" id="mainPage">

<div class="contentArea">

<!-- Search Option Begins -->
<table id="searchMaskTable" width="600" border="0" cellpadding="0" cellspacing="0">
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
   			<td width="25%" class="optionTitleBoldRight"><fmt:message key="label.revisiondate"/>&nbsp;:</td>
			<td width="75%" class="optionTitleLeft" >
				<input class="inputBox pointer" name="revDate" id="revDate" value="<fmt:formatDate value='${newRevDate}' pattern='${dateTimeFormatPattern}'/>" onClick="return getCalendar(document.getElementById('revDate'));" maxlength="10"/>		
			</td>
   		</tr>
      	<tr>
        	<td class="optionTitleBoldRight" width="25%" ><fmt:message key="label.msdsauthor"/>:</td>
        	<td width="75%" class="optionTitleLeft" >
        		<input type="hidden" name="msdsAuthorId" id="msdsAuthorId" value="${msdsAuthor.msdsAuthorId}"/>
        		<input name="msdsAuthorDesc" id="msdsAuthorDesc" type="text" class="inputBox" size="40" maxlength="250" value="${msdsAuthor.msdsAuthorDesc}"/>
        		<input type="hidden" name="countryAbbrev" id="countryAbbrev" value="${msdsAuthor.countryAbbrev}"/>
        		<input type="hidden" name="notes" id="notes" value="${msdsAuthor.notes}"/>
        		
	        	<span id="updateResultLink">
					<a href="#" onclick="createNew(); return false;"><fmt:message key="label.new"/></a>
					<c:if test="${showDupLink}">
						&nbsp;|&nbsp;<a href="#" onclick="sameAsMfg();"><fmt:message key="label.sameasmfg"/></a>
					</c:if>
				</span>
				<c:if test="${showDupLink}">
					<input type="hidden" name="dupDesc" id="dupDesc" type="hidden" value="${dupMsdsAuthor.msdsAuthorDesc}"/> 
					<input type="hidden" name="dupCountry" id="dupCountry" type="hidden" value="${dupMsdsAuthor.countryAbbrev}"/> 
					<input type="hidden" name="dupNotes" id="dupNotes" type="hidden" value="${dupMsdsAuthor.notes}"/>
				</c:if>
        	</td>
      	</tr>
      	<tr>
			<td width="15%" colspan="2">
				<br/>
				<input name="okBtn" type="button" class="inputBtns" value="<fmt:message key="label.ok(done)"/>" id="okBtn" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
		          		  onclick= "doSubmit(true)">
		        <input name="cancelBtn" type="button" class="inputBtns" value="<fmt:message key="label.cancel"/>" id="cancelBtn" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'"
		          		 onclick= "doSubmit(false)"/>
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

<div class="spacerY">&nbsp;</div>

<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
</div>
<!-- Error Messages Ends -->



<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;"></div>
 <input name="uAction" id="uAction" type="hidden" />
 <input name="materialId" id="materialId" type="hidden" value="${param.materialId}"/>
 <input name="mfgId" id="mfgId" type="hidden" value="${param.mfgId}"/>
<%--  <input name="mfgDesc" id="mfgDesc" type="hidden" value="${param.mfgDesc}"/> 
 <input name="mfgCountry" id="mfgCountry" type="hidden" value="${param.mfgCountry}"/> 
 <input name="mfgNotes" id="mfgNotes" type="hidden" value="${param.mfgNotes}"/>  --%>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->
</tcmis:form>
</body>
</html:html>