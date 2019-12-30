<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis"%>

<html:html lang="true">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:fontSizeCss />
<%@ include file="/common/opshubig.jsp"%> 

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
<script type="text/javascript" src="/js/common/searchiframeresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp"%>

<!-- For Calendar support -->
<%-- 
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
--%>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--<script src="/js/dhtmlxGrid/dhtmlXCommon.js"></script>
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

<title><fmt:message key="forcebuy.title" /></title>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/hub/forcebuy.js"></script>
<script type="text/javascript" src="/js/common/opshubig.js"></script>

<script language="JavaScript" type="text/javascript">
<!--
var menuskin = "skin1"; // skin0, or skin1
var display_url = 0; // Show URLs in status bar?
// -->
</script>

<script LANGUAGE="JavaScript" TYPE="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",itemid:"<fmt:message key="label.itemid"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>"};

defaults.ops.nodefault = true;
defaults.hub.nodefault = true;
defaults.inv.nodefault = true;
		
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="setSearchFrameSize();setOps();">

<tcmis:form action="forcebuyresults.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">

	<div class="interface" id="searchMainPage">

	<div class="contentArea"><!-- Search Option Begins -->
	
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
				<%-- row 1 --%>							
				<tr>
					<td width="15%" class="optionTitleBoldRight" nowrap><fmt:message key="label.operatingentity" />:&nbsp;</td>
					<td width="40%" class="optionTitleBoldLeft"><select name="opsEntityId" id="opsEntityId" class="selectBox"></select>
					</td>
					<td>&nbsp;</td>
				</tr>
				
				<%-- row 2 --%>							
				<tr>
					<td width="15%" class="optionTitleBoldRight" nowrap><fmt:message key="label.hub" />:&nbsp;</td>
					<td width="40%" class="optionTitleBoldLeft"><select name="hub" id="hub" class="selectBox"
						onchange="hubChanged();">
					</select></td>
					<td>&nbsp;</td>
				</tr>
				
				<%-- row 3 --%>
				<tr>
					<td width="20%" class="optionTitleBoldRight" nowrap><fmt:message key="label.inventorygroup" />:&nbsp;</td>
					<td width="40%" class="optionTitleBoldLeft"><select name="inventoryGroup" id="inventoryGroup"
						class="selectBox">
					</select></td>
					<td>&nbsp;</td>
				</tr>

				<%-- row 4 --%>
				<tr>
					<td width="15%" class="optionTitleBoldRight"><fmt:message key="label.search" />:&nbsp;</td>
					<td width="55%" class="optionTitleLeft" nowrap><html:select property="searchField"
						styleId="searchField" styleClass="selectBox">
						<html:option value="itemId">
							<fmt:message key="label.itemid" />
						</html:option>
                        <html:option value="itemDesc">
							<fmt:message key="label.itemdesc" />
						</html:option>
                        <html:option value="catPartNo">
							<fmt:message key="label.partno" />
						</html:option>

                    </html:select> <html:select property="searchMode" styleId="searchMode" styleClass="selectBox">
						<html:option value="is">
							<fmt:message key="label.is" />
						</html:option>
						<html:option value="contains">
							<fmt:message key="label.contains" />
						</html:option>
						<html:option value="startsWith">
							<fmt:message key="label.startswith" />
						</html:option>
						<html:option value="endsWith">
							<fmt:message key="label.endswith" />
						</html:option>
					</html:select> <input class="inputBox" type="text" name="searchArgument" id="searchArgument" size="15" value=""
						onkeypress="return onKeyPress()"></td>
					<td width="30%" class="optionTitleBoldLeft" nowrap>&nbsp;&nbsp;<input type="checkbox"
						name="showMinMaxOnly" id="showMinMaxOnly" value="Yes" /> <fmt:message key="label.showminmaxonly" /></td>
				</tr>
				
				<%-- row 5 --%>
				<tr>
					<td colspan ="3" class="optionTitleLeft">					
					<input type="submit" name="submitSearch" id="submitSearch"
						value="<fmt:message key="label.search"/>" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'"
						onmouseout="this.className='inputBtns'" onclick="return submitSearchForm();" />
					<input name="buttonCreateExcel" id="buttonCreateExcel" type="button"
						class="inputBtns" value="<fmt:message key="label.createexcelfile"/>"
						onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'"
						onclick="generateExcel();">
					</td>
				</tr>
			</table>
			<!-- End search options --></div>
			<div class="roundbottom">
			<div class="roundbottomright"><img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
			</div>
			</div>
			</div>
			</td>
		</tr>
	</table>
	<!-- Search Option Ends --> <!-- Error Messages Begins --> <!-- Build this section only if there is an error message to display.
     For the search section, we show the error messages within its frame
--> <c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
		<div class="spacerY">&nbsp;
		<div id="errorMessagesArea" class="errorMessages"><html:errors /></div>
		</div>
	</c:if> <!-- Error Messages Ends --></div>
	<!-- close of contentArea --> <!-- Hidden element start -->
	<div id="hiddenElements" style="display: none;"><input name="uAction" id="uAction" value="search" type="hidden" />
	<input type="hidden" name="searchTypeText" id="searchTypeText" value="" /> <input name="startSearchTime"
		id="startSearchTime" type="hidden" value="">
 </div>
	<!-- Hidden elements end --></div>
	<!-- close of interface -->

</tcmis:form>
</body>
</html:html>
