<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html:html lang="true">

<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"/>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<!-- CSS for YUI -->
<%--
<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />
--%>

<!-- Add any other stylesheets you need for the page here -->
<link rel="stylesheet" type="text/css" href="/css/tabs.css"/>                

<%--
<link rel="stylesheet" type="text/css" href="/css/dhtmlXGridHaas.css"></link>
--%>

<%@ include file="/common/locale.jsp" %>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/client/catalog/catalogaddeditnewmsds.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>   

<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script> 
<script type="text/javascript" src="/js/jquery/autocomplete.js"></script>
<link rel="stylesheet" type="text/css" href="/css/autocomplete.css" />  

<title>
	<fmt:message key="label.editmaterialinfo"/>
</title>

</head>


<body bgcolor="#ffffff" onLoad="editOnLoad('${param.uAction}');closeAllchildren();" onunload="checkDeleteLine();closeAllchildren();closeThisWindow();" onresize="resizeWindowSizes();">

<script language="JavaScript" type="text/javascript">
<!--

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
errors:"<fmt:message key="label.errors"/>",
genericError:"<fmt:message key="generic.error"/>",
submit:"<fmt:message key="button.submit"/>",
save:"<fmt:message key="button.save"/>",
lmMsds:"<fmt:message key="label.lmmsds"/>",
materialDescription:"<fmt:message key="label.materialdescription"/>",
manufacturer:"<fmt:message key="label.manufacturer"/>",
mfgTradeName:"<fmt:message key="label.manufacturertradename"/>",
size:"<fmt:message key="label.size"/>",
unit:"<fmt:message key="label.unit"/>", 
pleasewait:"<fmt:message key="label.pleasewait"/>",
itemInteger:"<fmt:message key="error.item.integer"/>",
invalidReplaceMsds:"<fmt:message key="error.invalidreplacemsds"/>"};


j$().ready(function() {
	
	j$("#manufacturer").autocomplete("manufacturersearchmain.do?loginNeeded=N&uAction=autoCompleteSearch",{
			width: 600,
			max: 60,  // default value is 10
			cacheLength:0, // disable cache here because we put "rownum < max" for better performance. Cache will make data off.
			scrollHeight: 150,
			formatItem: function(data, i, n, value) {
				return  value.split(":")[0]+" : "+value.split(":")[1].substring(0,240);
			},
			formatResult: function(data, value) {
				return value.split(":")[1];
			}
	});
/*	
	j$('#manufacturer').bind('keyup',(function(e) {
		  var keyCode = (e.keyCode ? e.keyCode : e.which);

		  if( keyCode != 13 && keyCode != 9) // 13 is for Enter; 9 is for Tab
		  	invalidateManufacturerInput();
	}));
	*/
	
	j$("#manufacturer").result(mfgLog).next().click(function() {
		j$(this).prev().search();
	});
	
	function mfgLog(event, data, formatted) {
		$("manufacturer").className = "inputBox"; 
		$('mfgId').value = formatted.split(":")[0];
	} 

});
/*
function invalidateManufacturerInput()
{
 var manufacturer  =  document.getElementById("manufacturer");
 var mfgId  =  document.getElementById("mfgId");
 if (manufacturer.value.length == 0) {
   manufacturer.className = "inputBox";
 }else {
   manufacturer.className = "inputBox red";
 }
 //set to empty
 mfgId.value = "";
}   */			 
// -->
 </script>


<tcmis:form action="/catalogaddmsdsrequest.do" onsubmit="" target="_self">

<div class="interface" id="mainPage" style="">

<%-- transition --%>
<div id="transitDailogWin" class="errorMessages" style="display: none;overflow: auto;">
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

	 <%-- message --%>
<div id="messageDailogWin" class="errorMessages" style="display: none;overflow: auto;">
	<table width="100%" border="0" cellpadding="2" cellspacing="1">
		<tr>
			<td align="center" width="100%">
				<textarea cols="70" rows="5" class="inputBox" readonly="true" name="messageText" id="messageText">${tcmISError}</textarea>
			</td>
		</tr>
		<tr>
			<td align="center" width="100%">
			<input name="closeMessageB"  id="closeMessageB"  type="button" value="<fmt:message key="label.ok"/>" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onClick="closeMessageWin();"/>
			</td>
		</tr>
	</table>
</div>

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${empty tcmISErrors and empty tcmISError}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>
//-->
</script>
<!-- Error Messages Ends -->

<!-- start of contentArea -->
<div class="contentArea">

<!-- Search Option Begins -->
<%--Change the width to what you want your page to span.--%>
<div id="searchMaskTable"  style="width:1010px;height:393px;">
	<table class="tableSearch" border="1">
		<tr>
		    <td><a href="#" onclick="submitUpdate(); return false;"><fmt:message key="button.submit"/></a>
					&nbsp;|&nbsp;<a href="#" onclick="cancel(); return false;"><fmt:message key="label.cancel"/></a>
			</td>
		</tr>

        <tr>
            <td>
            <table class="tableSearchDetail" border="0">
                <c:set var="hmrbSpanDisplay" value='display: none'/>
                <c:if test="${hasHmrbTab == 'Y'}">
                    <c:set var="hmrbSpanDisplay" value=''/>
                </c:if>

                <tr>
                     <td class="optionTitleBoldLeft">
                        <fmt:message key="label.msds"/>:
                        <input type="text" class="inputBox" name="customerMsdsNumber" id="customerMsdsNumber" value="${catAddHeaderViewBean.catalogAddItemColl[0].customerMsdsNumber}" size="20" maxLength="40"/>
                        <c:if test="${showReplacesMsds == 'Y'}">
                            &nbsp;&nbsp;&nbsp;&nbsp;
                            <fmt:message key="label.replacesmsds"/>:
                            <input type="text" class="inputBox" name="replacesMsds" id="replacesMsds" value="${catAddHeaderViewBean.catalogAddItemColl[0].replacesMsds}" size="20" maxLength="40"/>
                        </c:if>
                     </td>
                </tr>

                 <tr style="${hmrbSpanDisplay}">
                     <td class="optionTitleBoldLeft" colspan="2">
                        <%--
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <c:set var="tmpVal" value='${catAddHeaderViewBean.catalogAddItemColl[0].aerosolContainer}'/>
                        <c:set var="tmpValChecked" value=''/>
                        <c:if test="${tmpVal == 'Y'}">
                            <c:set var="tmpValChecked" value='checked="checked"'/>
                        </c:if>
                        <input type="checkbox" name="aerosolContainer" id="aerosolContainer" value="Y" class="radio" ${tmpValChecked}><fmt:message key="label.aerosol"/>
                        --%>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <c:set var="tmpVal" value='${catAddHeaderViewBean.catalogAddItemColl[0].nanomaterial}'/>
                        <c:set var="tmpValChecked" value=''/>
                        <c:if test="${tmpVal == 'Y'}">
                            <c:set var="tmpValChecked" value='checked="checked"'/>
                        </c:if>
                        <input title='<fmt:message key="label.containsnanomaterialdefinition"/>' type="checkbox" name="nanomaterial" id="nanomaterial" value="Y" class="radio" ${tmpValChecked}><fmt:message key="label.containsnanomaterial"/>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <c:set var="tmpVal" value='${catAddHeaderViewBean.catalogAddItemColl[0].radioactive}'/>
                        <c:set var="tmpValChecked" value=''/>
                        <c:if test="${tmpVal == 'Y'}">
                            <c:set var="tmpValChecked" value='checked="checked"'/>
                        </c:if>
                        <input type="checkbox" name="radioactive" id="radioactive" value="Y" class="radio" ${tmpValChecked}><fmt:message key="label.radioactive"/>
                     </td>
                 </tr>

                <tr>
                    <td class="optionTitleBoldLeft" colspan="2" nowrap>
                        <fmt:message key="label.materialdescription"/> - <fmt:message key="label.240charsmax"/>:* (<fmt:message key="label.includeallinformation"/>)
                    </td>
                </tr>
                <tr>
                    <td class="optionTitleBoldLeft" colspan="2">
                         <input type="text" class="inputBox" name="materialDesc" id="materialDesc" value="${catAddHeaderViewBean.catalogAddItemColl[0].materialDesc}" size="110" maxLength="240"/>
                    </td>
                </tr>

                <tr><td class="optionTitleBoldLeft" colspan="2"><fmt:message key="label.manufacturer"/> - <fmt:message key="label.240charsmax"/>:*</td></tr>
                 <tr>
                    <td class="optionTitleBoldLeft" colspan="2">
              			<input class="inputBox" type="text" name="manufacturer" id="manufacturer" value="${catAddHeaderViewBean.catalogAddItemColl[0].manufacturer}" size="110" maxlength="240" /> 
 		 				<input name="mfgId" id="mfgId" type="hidden" value="${catAddHeaderViewBean.catalogAddItemColl[0].mfgId}">
              <%--
                        <input class="inputBox" type="hidden" name="mfgId" id="mfgId" value="${catAddHeaderViewBean.catalogAddItemColl[0].mfgId}" >
                        <input class="inputBox" type="text" name="manufacturer" id="manufacturer" value="${catAddHeaderViewBean.catalogAddItemColl[0].manufacturer}" size="110" maxlength="240">
                        <input type="button" class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'" name="selectedManufacturer" id="selectedManufacturer" value="..." align="right" onClick="lookupManufacturer();"/>
                     --%>
                    </td>
                </tr>
                <tr>
                    <td class="optionTitleBoldLeft" colspan="2">
                      <fmt:message key="label.manufacturertradename"/> - <fmt:message key="label.100charsmax"/>:* (<fmt:message key="label.frommsds"/>)<br>
                  </td>
                </tr>
                <tr>
                    <td class="optionTitleBoldLeft" colspan="2">
                        <input class="inputBox" type="text" name="mfgTradeName" id="mfgTradeName" value="${catAddHeaderViewBean.catalogAddItemColl[0].mfgTradeName}" size="110" maxLength="100"/>
                    </td>
                </tr>
                <%--
                <tr>
                    <td class="optionTitleBoldLeft" colspan="2">
                      <fmt:message key="label.size"/>:*
                      <input class="inputBox" type="text" name="partSize" id="partSize" value="${catAddHeaderViewBean.catalogAddItemColl[0].partSize}" size="3" maxLength="10"/>
                      &nbsp;&nbsp;&nbsp;&nbsp;
                      <fmt:message key="label.unit"/>:*
                      <select class="selectBox" name="sizeUnit" id="sizeUnit">
                            <c:set var="selectedSizeUnit" value='${catAddHeaderViewBean.catalogAddItemColl[0].sizeUnit}'/>
                            <c:set var="sizeUnitSizeColl" value='${catAddHeaderViewBean.shippingWeightUnitColl}'/>
                            <bean:size id="sizeUnitSize" name="sizeUnitSizeColl"/>
                            <c:if test="${sizeUnitSize > 1}">
                                <option value="" selected><fmt:message key="label.selectOne"/></option>
                            </c:if>
                            <c:forEach var="vvSizeUnitBean" items="${catAddHeaderViewBean.shippingWeightUnitColl}" varStatus="status1">
                                <c:set var="currentSizeUnit" value='${status1.current.sizeUnit}'/>
                                <c:choose>
                                    <c:when test="${currentSizeUnit == selectedSizeUnit}">
                                        <option value="<c:out value="${status1.current.sizeUnit}"/>" selected><c:out value="${status1.current.sizeUnit}" escapeXml="false"/></option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="<c:out value="${status1.current.sizeUnit}"/>"><c:out value="${status1.current.sizeUnit}" escapeXml="false"/></option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                --%>
            </table>
            </td>
        </tr>
    </table>

</div>
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
<input type="hidden" name="uAction" id="uAction" value="${param.uAction}"/>
<input type="hidden" name="totalLines" id="totalLines" value="${dataCount}" />
<input type="hidden" name="personnelId" id="personnelId" value='${personnelBean.personnelId}'/>
<input type="hidden" name="requestId" id="requestId" value='${catAddHeaderViewBean.requestId}'/>
<input type="hidden" name="hasHmrbTab" id="hasHmrbTab" value='${hasHmrbTab}'/>
<input type="hidden" name="lineItem" id="lineItem" value='${catAddHeaderViewBean.catalogAddItemColl[0].lineItem}'/>
<input type="hidden" name="partId" id="partId" value='${catAddHeaderViewBean.catalogAddItemColl[0].partId}'/>
<input type="hidden" name="calledFrom" id="calledFrom" value='${param.calledFrom}'/>  
<input type="hidden" id="replacesMsdsValidator" value='${catAddHeaderViewBean.catalogAddItemColl[0].replacesMsds}'/>
   
</div>
<!-- Hidden elements end -->
</tcmis:form>
</div>
<!-- close of contentArea -->

</div> <!-- close of interface -->

</body>
</html:html>