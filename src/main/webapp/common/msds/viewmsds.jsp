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

<html:html lang="true" >
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"> </link>

<%@ include file="/common/locale.jsp" %>
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss overflow="Hidden"/>

<link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />

<%-- Add any other stylesheets you need for the page here --%>

<link rel="stylesheet" type="text/css" href="/css/clientpages.css"></link>
<link rel="stylesheet" type="text/css" href="/css/msds.css"></link>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
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

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/js/common/msds/viewmsds.js"></script>


<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>


<title>
<fmt:message key="label.msdsinfo"/>
</title>
<style type="text/css">
td.clickblue {
	font-family: arial;
    font-size: 8pt;
    background-color:  #8a8aff;
    text-align: center;
    cursor: pointer;
}

td.unclickblue {
	font-family: arial;
    font-size: 8pt;
	background-color: #f5f5ff;
	text-align: center;
	cursor: pointer;
}
</style>


<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={
    alert:"<fmt:message key="label.alert"/>",
    and:"<fmt:message key="label.and"/>",
    or:"<fmt:message key="label.or"/>",
    all:"<fmt:message key="label.all"/>",
    validvalues:"<fmt:message key="label.validvalues"/>",
    submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
    itemInteger:"<fmt:message key="error.item.integer"/>",
    errors:"<fmt:message key="label.errors"/>",
    pleasewait:"<fmt:message key="label.pleasewait"/>",
    searchText:"<fmt:message key="label.text"/>",
    ph:"<fmt:message key="label.ph"/>",
    flashpoint:"<fmt:message key="label.flashpoint"/>",
    vaporpressure:"<fmt:message key="label.vaporpressure"/>",
    voc:"<fmt:message key="label.voc"/>",
    solids:"<fmt:message key="label.solids"/>",
    nfpa:"<fmt:message key="label.nfpa"/>",
    hmis:"<fmt:message key="label.hmis"/>",
    health:"<fmt:message key="label.health"/>",
    flammability:"<fmt:message key="label.flammability"/>",
    reactivity:"<fmt:message key="label.reactivity"/>",
    casnumber:"<fmt:message key="label.casnumber"/>",
    documents:"<fmt:message key="label.documents"/>",
    msdsRevisionComparision:"<fmt:message key="label.msdsrevisioncomparision"/>",
    synonym:"<fmt:message key="label.synonym"/>",
    showPropertiesLog:"<fmt:message key="label.showpropertieslog"/>",
    editProperties:"<fmt:message key="label.editproperties"/>",
    submitNewRevision:"<fmt:message key="label.submitnewrevision"/>",
    viewContactLogs:"<fmt:message key="label.viewcontactlogs"/>",
    searchInput:"<fmt:message key="error.searchInput.integer"/>"
};

function showPropertiesLog() {
	openWinGeneric('propertylog.do?materialId='+ $v("materialId")+'&revisionDate='+$v("revisionDate")
			,"showPropertiesLog","950","550",'yes' );
}

function editProperties() {
	var loc = 'editcustomerproperties.do?fromMsdsInfo=Y&materialId='+ $v("materialId")+
		'&revisionDate='+$v("revisionDate")+'&facility='+$v("facility")+'&catpartno='+$v("catpartno") ;

	openWinGeneric(loc,"editProperties","1200","580",'yes' );
}

function setDisplayView() {
	<c:if test="${!empty param.currentView}">
		$("currentView").value = '${param.currentView}';
	</c:if>
}

// -->
 </script>
</head>

<body bgcolor="#ffffff"  onload="setDisplayView();myonload();myResize();" onresize="myResize();">
<tcmis:form action="/viewmsds.do" onsubmit="return submitSearchOnlyOnce();" >
<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<!-- Search Option Begins -->
<table width="100%" height="" border="0" cellpadding="0" cellspacing="0">
<tr>
<td width=15% align="center" valign="center">
	<%@ include file="/common/application/msdsclientlogo.jsp" %>
</td>
<td width="58%" valign="center">
	<center>
		<table border="0" cellpadding="2" cellspacing="0" class="selectedblue">
		<tr>
		<td>
			<table border="0" cellspacing="0" cellpadding="0" bgcolor="#ffffff">
				<tr>
				<td>
					<FONT size="4" face="Arial"><b>&nbsp;&nbsp;<span id="tradeNameSpan">${titleInfo.tradeName}</span>&nbsp;&nbsp;</FONT>
				</td>
				</tr>
			</table>
		</td>
		</tr>
		</table>
	</center>
	<center>
		<FONT size="1" face="Arial" COLOR="green"><fmt:message key="label.revisiondate"/>:&nbsp;</FONT>
        <c:set var="localeDisplay" value=""/>
        <c:if test="${titleInfo.localeDisplay != 'English' && !empty titleInfo.localeDisplay}">
            <c:set var="localeDisplay" value=" (${titleInfo.localeDisplay})"/>
        </c:if>
        <FONT COLOR="000000"><span id="revisionDateSpan"><fmt:formatDate value="${titleInfo.revisionDate}" pattern="${dateFormatPattern}"/>${localeDisplay}</span></FONT>
	</center>
</td>

<td align="left" valign="center">
	<a href="#" onClick="contactUs();">
	<img name="imgContactUs" src="${hostUrl}/images/docx2.gif" alt="Please click to report a problem with the current image" border=0></a>
</td>
</tr>

<tr>
<td width="15%">&nbsp;</td>
<td width="58%" align="center" valign="center"><font size="1" face="Arial"><b><fmt:message key="label.manufacturer"/>: <span id="mfgDescSpan">${titleInfo.mfgDesc}</span></b></font>
</td>
</tr>
</table>

<div id="caProp65NoticeDisplayDiv" style="display:none">
    <table width="100%" border="0" cellpadding="1" cellspacing="0">
    <tr>
        <td width="100%" align="center" valign="center">
            <font size="1" face="Arial"><b><fmt:message key="label.caprop65notice"/></b></font>
        </td>
    </tr>
    </table>
</div>

<div id="companyStandardDisplayDiv"  style="display:none">
    <table width="100%" border="0" cellpadding="1" cellspacing="0">
    <tr>
        <td width="100%" align="center" valign="center">
            <font size="1" COLOR="red" face="Arial"><b><fmt:message key="label.revisionnotcompleteforcompany"/></b></font>
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
</div>
<!-- Search Frame Ends -->

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 0px 0px 0px 0px;">
<br/>
<!-- Transit Page Ends -->
<div id="resultGridDiv" style="display:;">
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="1" cellspacing="0">

<tr>
    <td colspan="2">
        <div id="actionSpan" class="boxhead">
            <%-- this is for action options--%>
        </div>
    </td>
</tr>

<tr>
	<td width="200px;" valign="top">
<c:if test="${empty titleInfo}">
	<FONT FACE="Arial"><BR><BR><BR><B><fmt:message key="main.nodatafound"/></B></FONT>
</c:if>
<c:if test="${!empty titleInfo}">
<TABLE CELLSPACING="0" CELLPADDING="1" BORDER="0" class="selectedblue" WIDTH="100%">



<TR>
<TD>

	<TABLE CELLSPACING="0" CELLPADDING="0" BORDER="0" BGCOLOR="#FFFFFF" WIDTH="100%">

		<c:choose>
			<c:when test="${empty itemMaterialColl}">
		<TR>
			<TD ALIGN="LEFT" class="selectedblue">
				<FONT FACE="Arial" SIZE="2" COLOR="#E6E8FA"><B><fmt:message key="label.msds"/>:&nbsp;${sideViewInfo.msdsNo}<BR></FONT>
				<FONT FACE="Arial" SIZE="1" COLOR="#000000"><fmt:message key="label.revisiondate"/></FONT>
			</TD>
		</TR>
			</c:when>
			<c:otherwise>
		<TR>
			<TD ALIGN="LEFT" class="selectedblue">
				<FONT FACE="Arial" SIZE="2" COLOR="#E6E8FA"><B><fmt:message key="label.msds"/>:&nbsp;${sideViewInfo.msdsNo}</B></FONT>
			</TD>
		</TR>

		<TR>
			<TD ALIGN="CENTER">
				<FONT FACE="Arial" SIZE="1">
					<select id="chosenMsds" name="chosenMsds" class="selectBox" onChange="changeMsds();" style="width:100%;text-overflow:ellipsis;">
					<c:forEach var="msdsBean" items="${itemMaterialColl}" varStatus="status">
                    <option value="${msdsBean.materialId}" <c:if test="${param.materialId == msdsBean.materialId}">selected</c:if>>${msdsBean.materialDesc}</option>
					</c:forEach>
					</select>
				</FONT>
			</TD>
		</TR>

		<TR>
			<TD ALIGN="LEFT" class="selectedblue">
				<FONT FACE="Arial" SIZE="2" COLOR="#E6E8FA"><B><fmt:message key="label.revisiondate"/></B></FONT>
			</TD>
		</TR>
			</c:otherwise>
		</c:choose>

		<TR>
			<TD ALIGN="CENTER">
				<FONT FACE="Arial" SIZE="1">
					<select id="revisionDateAndLocale" name="revisionDateAndLocale" class="selectBox" onChange="revisionDateChanged();">
					<c:forEach var="revisionDateBean" items="${revisionDateColl}" varStatus="status">
					<fmt:formatDate var="fmtRevisionDate" value="${revisionDateBean.revisionDate}" pattern="${dateFormatPattern}"/>
                    <c:set var="localeDisplay" value=""/>
                    <c:if test="${revisionDateBean.localeDisplay != 'English'}">
                        <c:set var="localeDisplay" value="(${revisionDateBean.localeDisplay})"/>
                    </c:if>
                    <option value="${revisionDateBean.revisionDateWithFormat}|${revisionDateBean.localeCode}|${revisionDateBean.revisionDate}"
                            <c:if test="${selectedRevisionDate == revisionDateBean.revisionDateWithFormat && selectedLocaleCode == revisionDateBean.localeCode}">selected</c:if>>
                            ${fmtRevisionDate}${localeDisplay}</option>
					</c:forEach>
					</select>
				</FONT>
			</TD>
		</TR>
	</TABLE>

</TD>
</TR>
</TABLE>


<span id="upperSideViewSpan" style="display:none">
    <table border="0" width="150px" cellpadding="5">
        <tr><td id="msdsTd" class="clickblue" align="center" onClick="doTitle('msdsTd');if($v('currentView') == 'msds') return false;else{$('currentView').value='msds';changeView();}">
            <FONT FACE="Arial" SIZE="2"><B><fmt:message key="label.msds"/></B></FONT>
        </td></tr>
        <tr><td id="compositionTd" class="unclickblue" align="center" onClick="doTitle('compositionTd');if($v('currentView') == 'composition') return false;else{$('currentView').value='composition';changeView();}">
            <FONT FACE="Arial" SIZE="2"><B><fmt:message key="label.composition"/></B></FONT>
        </td></tr>
        <tr><td id="propertiesTd" class="unclickblue" align="center" onClick="doTitle('propertiesTd');if($v('currentView') == 'properties') return false;else{$('currentView').value='properties';changeView();}">
            <FONT FACE="Arial" SIZE="2"><B><fmt:message key="label.properties"/></B></FONT>
        </td></tr>
        <tr <c:if test="${hideMsdsViewerActionOptions == 'Y'}">style="display:none" </c:if>>
        <td id="listsTd" class="unclickblue" align="center" onClick="doTitle('listsTd');if($v('currentView') == 'lists') return false;else{$('currentView').value='lists';changeView();}">
            <FONT FACE="Arial" SIZE="2"><B><fmt:message key="label.lists"/></B></FONT>
        </td></tr>
        <tr><td id="shippingTd" class="unclickblue" align="center" onClick="doTitle('shippingTd');if($v('currentView') == 'shipping') return false;else{$('currentView').value='shipping';changeView();}">
            <FONT FACE="Arial" SIZE="2"><B><fmt:message key="label.shipping"/></B></FONT>
        </td></tr>
    </table>
</span>

    <FONT FACE="Times New Roman" SIZE="3">
    <span id="lowerSideViewSpan">
    <CENTER><B><fmt:message key="label.nfpa"/></B>
    <c:choose>
        <c:when test="${empty sideViewInfo.flammability && empty sideViewInfo.health && empty sideViewInfo.reactivity && empty sideViewInfo.specificHazard}">
            <BR><B><fmt:message key="label.notlisted"/></B><BR><BR>
        </c:when>
        <c:otherwise>
            <div class="nfpadiamond">
                                     <div class="fire">
                                        ${sideViewInfo.flammability}
                                     </div>
                                     <div class="health">
                                        ${sideViewInfo.health}
                                     </div>
                                     <div class="reactivity">
                                        ${sideViewInfo.reactivity}
                                     </div>
                                     <div class="special" style='font-size: 20px;'>
                                        ${sideViewInfo.specificHazard}
                                     </div>
                                  </div>
        </c:otherwise>
    </c:choose>
    <BR>
    <B><fmt:message key="label.hmis"/></B>

    <BR>
    <c:choose>
        <c:when test="${ empty sideViewInfo.hmisHealth && empty sideViewInfo.hmisFlammability && empty sideViewInfo.hmisReactivity && empty sideViewInfo.ppe}">
            <BR><B><fmt:message key="label.notlisted"/></B><BR>
        </c:when>
        <c:otherwise>
            <div class="hmis">
                                     <div class="health">
                                        ${sideViewInfo.hmisHealth}
                                     </div>
                                     <div class="fire">
                                        ${sideViewInfo.hmisFlammability}
                                     </div>
                                     <div class="reactivity">
                                        ${sideViewInfo.hmisReactivity}
                                     </div>
                                     <div class="personalprotection">
                                        ${sideViewInfo.personalProtection}
                                     </div>
           </div>
        </c:otherwise>
    </c:choose>
    <BR>

    </CENTER>
    </span>
    </FONT>

<span id="openMSDSSpan"><A HREF="#" ID="printlinkie" onClick="openMSDS()" STYLE="color:#0000ff;cursor:hand;"><U><fmt:message key="label.openmsds"/></U></A>
</span>


</c:if>
	</td>
    <td height="100%" valign="top">
<!-- Transit Page Begins -->
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
    	<table border="0" width="100%"><tr><td>
	    	<iframe  scrolling="auto"  id="resultFrame" name="resultFrame" width="100%" height="100%" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
	    	</td></tr>
    	</table>
	</td>
  </tr>
</table>
</div>
</div><!-- Result Frame Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
    <input name="uAction" id="uAction" type="hidden" value="">
    <input name="currentView" id="currentView" type="hidden" value="msds">
    <input name="materialId" id="materialId" type="hidden" value="<tcmis:jsReplace value="${param.materialId}" />">
    <input name="itemId" id="itemId" type="hidden" value="<tcmis:jsReplace value="${param.itemId}" />">
    <input name="facility" id="facility" type="hidden" value="<tcmis:jsReplace value="${param.facility}" />">
    <input name="catpartno" id="catpartno" type="hidden" value="<tcmis:jsReplace value="${param.catpartno}" />">
    <input name="localeCode" id="localeCode" type="hidden" value="${selectedLocaleCode}">
    <input name="revisionDate" id="revisionDate" type="hidden" value="${selectedRevisionDate}">
    <input name="revisionDateTimeStamp" id="revisionDateTimeStamp" type="hidden" value="${selectedRevisionDateTimestamp}">
    <input name="module" id="module" type="hidden" value="${module}">
    <input name="msdsUrl" id="msdsUrl" type="hidden" value="${titleInfo.content}">
    <input type="hidden" name="companyId" id="companyId" value="${personnelBean.companyId}"/>
    <input name="searchHeight" id="searchHeight" type="hidden" value="">
    <input name="notice" id="notice" type="hidden" value="${notice}">
    <input name="revisionMeetsCompanyStandard" id="revisionMeetsCompanyStandard" type="hidden" value="${revisionMeetsCompanyStandard}">
    <input name="showMsdsProperties" id="showMsdsProperties" type="hidden" value="${showMsdsProperties}">
    <input name="editMsdsProperties" id="editMsdsProperties" type="hidden" value="${editMsdsProperties}">
    <input name="hideMsdsViewerActionOptions" id="hideMsdsViewerActionOptions" type="hidden" value="${hideMsdsViewerActionOptions}">
</div>
<!-- Hidden elements end -->

    <!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a yui pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">
</div>
</tcmis:form>
</body>
</html:html>