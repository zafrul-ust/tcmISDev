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
<%--
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/client/catalog/chargepoinfo.js"></script>
<script type="text/javascript" src="/js/client/catalog/chargenumberpoinfo.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>	
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<!-- These are for the Grid-->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%>
<%--
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
--%>

<%--This has the custom cells we built,
    hcal - the internationalized calendar which we use
    hlink - this is for any links you want tp provide in the grid, the URL/function to call
           can be attached based on a event (rowselect etc)
    hed -editable sinlge line text
    hcoro -select drop down
    hch -checkbox
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>	
<%--This file has our custom sorting and other utility methos for the grid.--%>    
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
	<fmt:message key="label.chargeinfo"/>
</title>

</head>


<body bgcolor="#ffffff" onload="editOnLoad();" onunload="checkClose();">

<script language="JavaScript" type="text/javascript">
<!--

	var prRulesColl = new Array();
	var facAccountSysPoForDirectColl = new Array();
	var facAccountSysPoForIndirectColl = new Array();
	var chargeNumberForDirectColl = new Array();
	var chargeNumberForIndirectColl = new Array();
    var chargeNumbersData = new Array();

    <c:set var="accountSysDesc" value=''/>
    <c:forEach var="tmpBean" items="${accountSysChargeNumberPoData.prRulesColl}" varStatus="status">
        <c:set var="accountSysDesc" value='${tmpBean.accountSysDesc}'/>
        prRulesColl[${status.index}]={
			chargeType:'${tmpBean.chargeType}',
			poRequired:'${tmpBean.poRequired}',
			prAccountRequired:'${tmpBean.prAccountRequired}',
			chargeDisplay1:'${tmpBean.chargeDisplay1}',
			chargeDisplay2:'${tmpBean.chargeDisplay2}',
			chargeDisplay3:'${tmpBean.chargeDisplay3}',
			chargeDisplay4:'${tmpBean.chargeDisplay4}',
			chargeLabel1:'${tmpBean.chargeLabel1}',
			chargeLabel2:'${tmpBean.chargeLabel2}',
			chargeLabel3:'${tmpBean.chargeLabel3}',
			chargeLabel4:'${tmpBean.chargeLabel4}',
			chargeAllowNull1:'${tmpBean.chargeAllowNull1}',
			chargeAllowNull2:'${tmpBean.chargeAllowNull2}',
			chargeAllowNull3:'${tmpBean.chargeAllowNull3}',
			chargeAllowNull4:'${tmpBean.chargeAllowNull4}',
            unitPriceRequired:'${tmpBean.unitPriceRequired}',
			poSeqRequired:'${tmpBean.poSeqRequired}',
			customerRequisition:'${tmpBean.customerRequisition}',
            directedChargeAllowNull1:'${tmpBean.directedChargeAllowNull1}',
			directedChargeAllowNull2:'${tmpBean.directedChargeAllowNull2}',
			directedChargeAllowNull3:'${tmpBean.directedChargeAllowNull3}',
			directedChargeAllowNull4:'${tmpBean.directedChargeAllowNull4}'
        };
    </c:forEach>

	<c:forEach var="tmpBean" items="${accountSysChargeNumberPoData.facAccountSysPoForDirectColl}" varStatus="status">
		facAccountSysPoForDirectColl[${status.index}]={
			poNumber:'${tmpBean.poNumber}'
		};
	</c:forEach>

	<c:forEach var="tmpBean" items="${accountSysChargeNumberPoData.facAccountSysPoForIndirectColl}" varStatus="status">
		facAccountSysPoForIndirectColl[${status.index}]={
			poNumber:'${tmpBean.poNumber}'
		};
	</c:forEach>

	<c:forEach var="tmpBean" items="${accountSysChargeNumberPoData.chargeNumberForDirectColl}" varStatus="status">
		chargeNumberForDirectColl[${status.index}]={
			chargeNumber1:'${tmpBean.chargeNumber1}',
		    chargeNumber2:'${tmpBean.chargeNumber2}',
			chargeNumber3:'${tmpBean.chargeNumber3}',
			chargeNumber4:'${tmpBean.chargeNumber4}',
			percent:'${tmpBean.percent}'
		};
	</c:forEach>

	<c:forEach var="tmpBean" items="${accountSysChargeNumberPoData.chargeNumberForIndirectColl}" varStatus="status">
		chargeNumberForIndirectColl[${status.index}]={
			chargeNumber1:'${tmpBean.chargeNumber1}',
		    chargeNumber2:'${tmpBean.chargeNumber2}',
			chargeNumber3:'${tmpBean.chargeNumber3}',
			chargeNumber4:'${tmpBean.chargeNumber4}',
			percent:'${tmpBean.percent}'
		};
	</c:forEach>

    <c:set var="dataCount" value='${0}'/>
    <c:forEach var="tmpBean" items="${accountSysChargeNumberPoData.directedChargeForDirectColl}" varStatus="status">
		chargeNumbersData[${dataCount}]={
			accountNumber:'${tmpBean.chargeNumber1}',
		    accountNumber2:'${tmpBean.chargeNumber2}',
			accountNumber3:'${tmpBean.chargeNumber3}',
			accountNumber4:'${tmpBean.chargeNumber4}',
			percentage:'${tmpBean.percent}',
            chargeType:'${tmpBean.chargeType}'
        };
        <c:set var="dataCount" value='${dataCount+1}'/>
    </c:forEach>

	<c:forEach var="tmpBean" items="${accountSysChargeNumberPoData.directedChargeForIndirectColl}" varStatus="status">
		chargeNumbersData[${dataCount}]={
			accountNumber:'${tmpBean.chargeNumber1}',
		    accountNumber2:'${tmpBean.chargeNumber2}',
			accountNumber3:'${tmpBean.chargeNumber3}',
			accountNumber4:'${tmpBean.chargeNumber4}',
			percentage:'${tmpBean.percent}',
            chargeType:'${tmpBean.chargeType}'
		};
        <c:set var="dataCount" value='${dataCount+1}'/>
    </c:forEach>


//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
errors:"<fmt:message key="label.errors"/>",
pleasewait:"<fmt:message key="label.pleasewait"/>",
pleasewaitmenu:"<fmt:message key="label.pleasewaitmenu"/>",
missingpo:"<fmt:message key="label.missingpo"/>",
poline:"<fmt:message key="label.poline"/>",
missingchargenumber:"<fmt:message key="label.missingchargenumber"/>",
missingpercent:"<fmt:message key="label.missingpercent"/>",
invalidpercent:"<fmt:message key="label.invalidpercent"/>",
onlyOneChargeTypeData:"<fmt:message key="label.onlyonechargetypedata"/>",
itemInteger:"<fmt:message key="error.item.integer"/>"};
// -->
 </script>


<tcmis:form action="/catalogmain.do" onsubmit="" target="_self">

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
<div id="searchMaskTable">

<div>
<%-- Right Section 1 --%>
<!-- Initialize Menus -->

<!-- CSS Tabs -->
<div id="newChemTabs">
 <ul id="mainTabList">
 </ul>
</div>
<!-- CSS Tabs End -->

<div id="section1" class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
    <!-- Insert all the search option within this div -->

	 <table class="tableSearch" border="0">
		  <tr>
				<td colspan="2">
                    <c:choose>
                        <c:when test="${param.canEditData == 'Y'}">
                            <a href="#" onclick="submitUpdate(); return false;"><fmt:message key="button.submit"/></a>
                            <span id="removeSpan" style="display: none">
                                | <a href="#" onclick="submitDeleteDirectedCharge(); return false;"><fmt:message key="label.removechargenumbers"/></a>
                            </span>
                            | <a href="#" onclick="cancel(); return false;"><fmt:message key="button.cancel"/></a>
                        </c:when>
                        <c:otherwise>
                            <a href="#" onclick="cancel(); return false;"><fmt:message key="button.cancel"/></a>     
                        </c:otherwise>
                    </c:choose>

                </td>
		  </tr>
		  <tr>
			 <td class="optionTitleBoldRight">
				 <fmt:message key="label.facility"/>:
			 </td>
			 <td class="optionTitleBoldLeft">
				 ${param.facilityName}
			 </td>
		 </tr>
		 <tr>
			 <td class="optionTitleBoldRight">
				 <fmt:message key="label.accountsys"/>:
			 </td>
			 <td class="optionTitleBoldLeft">
				 ${accountSysDesc}
			 </td>
		 </tr>

		 <tr>
			 <table id="chargeNumberTable"  width="100%" border="0" cellpadding="0" cellspacing="0">
				 <tr>
					<td width="100%" class="optionTitleBoldCenter" colspan="2">
						<span id="chargeTypeSpan" name="chargeTypeSpan">
							<c:set var="selectedD" value="checked='checked'"/>
							<c:set var="selectedI" value=''/>
							<c:if test="${accountSysChargeNumberPoData.selectedChargeType == 'i'}">
								<c:set var="selectedD" value=''/>
								<c:set var="selectedI" value="checked='checked'"/>
							</c:if>

							<input type="radio" name="chargeType" id="chargeTypeD" onClick="directedCheck();" value="direct" class="radioBtns" ${selectedD}/><fmt:message key="label.direct"/>
							&nbsp;&nbsp;
							<input type="radio" name="chargeType" id="chargeTypeI" onClick="inDirectedCheck();" value="indirect" class="radioBtns" ${selectedI}/><fmt:message key="label.indirect"/>
						</span>
					</td>
				</tr>
				<tr>
					<td width="100%" class="optionTitleBoldLeft" colspan="2">
						<div id="chargeNumber2ColumnsDivId" style="display: none;" height="180px"></div>
						<div id="chargeNumber3ColumnsDivId" style="display: none;" height="180px"></div>
						<div id="chargeNumber4ColumnsDivId" style="display: none;" height="180px"></div>
						<div id="chargeNumber5ColumnsDivId" style="display: none;" height="180px"></div>
					</td>
				</tr>
		 	</table>
		 </tr>

		 <tr>
			 <table class="tableSearch" border="0">
				 <tr>
					 <td>&nbsp;</td>
				 </tr>
			  </table>
		 </tr>

		<tr>
			<td class="optionTitleBoldRight">
				<span id="poLabelSpan" name="poLabelSpan">
					<b><fmt:message key="label.po"/>:&nbsp;</b>
				</span>
			</td>
			<td class="optionTitleBoldLeft">
				<span id="poComboSpan" name="poComboSpan">
					<select name="poCombo" id="poCombo" class="selectBox">
					</select>
				</span>
				<span id="poInputSpan" name="poInputSpan">
					<input name="poInput" id="poInput" type="text" class="inputBox" value="" size="20"/>
				</span>
				&nbsp;&nbsp;&nbsp;
				<span id="poLineSpan" name="poLineSpan">
					<b><fmt:message key="label.line"/>:&nbsp;</b>
					<input name="poLineInput" id="poLineInput" type="text" class="inputBox" value="" size="20"/>
				</span>
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

</div>

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
<input type="hidden" name="uAction" id="uAction" value=""/>
<input type="hidden" name="sourcePage" id="sourcePage" value="${param.sourcePage}"/>
<input type="hidden" name="poNumberForDirect" id="poNumberForDirect" value="${accountSysChargeNumberPoData.poNumberForDirect}"/>
<input type="hidden" name="poNumberForIndirect" id="poNumberForIndirect" value="${accountSysChargeNumberPoData.poNumberForIndirect}"/>
<input type="hidden" name="poLineForDirect" id="poLineForDirect" value="${accountSysChargeNumberPoData.poLineForDirect}"/>
<input type="hidden" name="poLineForIndirect" id="poLineForIndirect" value="${accountSysChargeNumberPoData.poLineForIndirect}"/>
<input type="hidden" name="allowBothChargeType" id="allowBothChargeType" value="${accountSysChargeNumberPoData.allowBothChargeType}"/>
<input type="hidden" name="facLocAppDirectedChargeAllowNull" id="facLocAppDirectedChargeAllowNull" value="${accountSysChargeNumberPoData.facLocAppDirectedChargeAllowNull}"/>
<input type="hidden" name="canEditData" id="canEditData" value="${param.canEditData}"/>
</div>
<!-- Hidden elements end -->
</tcmis:form>
</div>
<!-- close of contentArea -->

</div> <!-- close of interface -->

</body>
</html:html>