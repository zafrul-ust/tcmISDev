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
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="expires" content="-1"/>
<link rel="shortcut-icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>
<%-- For Internationalization, copies data from calendarval.js --%>
<%@ include file="/common/locale.jsp" %>
<tcmis:gridFontSizeCss />
<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles which key press events are disabled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>
<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>

<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<script src="/js/jquery/jquery-1.6.4.js"></script>
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/js/catalog/mfrnotificationmgmtmain.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<script type="text/javascript" src="/js/jquery/autocomplete.js"></script> 
<link rel="stylesheet" type="text/css" href="/css/autocomplete.css" />  

<title><fmt:message key="mfrNotificationMgmt"/></title>
<script language="JavaScript" type="text/javascript"/>
<!--
var windowCloseOnEsc = true;
var children = [];

var messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
searchInput:"<fmt:message key="label.inputSearchText"/>",
errors:"<fmt:message key="label.errors"/>", 
itemInteger:"<fmt:message key="error.item.integer"/>",
inputsearchtext:"<fmt:message key="label.inputSearchText"/>"};

<fmt:message var="materialid" key="label.materialid"/>
<fmt:message var="materialdesc" key="label.materialdesc"/>
<fmt:message var="manufacturerid" key="label.manufacturerid"/>
<fmt:message var="manufacturerdesc" key="label.manufacturerdescription"/>
<fmt:message var="acquiredmfrid" key="label.acquiredmfrid"/>
<fmt:message var="acquiredmfrdesc" key="label.acquiredmfrdesc"/>
<fmt:message var="itemid" key="label.itemid"/>
<fmt:message var="itemdesc" key="label.itemdescription"/>
<fmt:message var="notificationid" key="label.notificationrequest"/>
<fmt:message var="internalcomments" key="label.internalcomments"/>
var searchArray = [
    { id:'materialId', text:'<tcmis:jsReplace value="${materialid}"/>'},
    { id:'materialDesc', text:'<tcmis:jsReplace value="${materialdesc}"/>'},
    { id:'mfrId', text:'<tcmis:jsReplace value="${manufacturerid}"/>'},
    { id:'mfrDesc', text:'<tcmis:jsReplace value="${manufacturerdesc}"/>'},
    { id:'acquiredMfrId', text:'<tcmis:jsReplace value="${acquiredmfrid}"/>'},
    { id:'acquiredMfrDesc', text:'<tcmis:jsReplace value="${acquiredmfrdesc}"/>'},
    { id:'itemId', text:'<tcmis:jsReplace value="${itemid}"/>'},
    { id:'itemDesc', text:'<tcmis:jsReplace value="${itemdesc}"/>'},
    { id:'notificationId', text:'<tcmis:jsReplace value="${notificationid}"/>'},
    { id:'internalComments', text:'<tcmis:jsReplace value="${internalcomments}"/>'}
];

//-->
</script>
</head>
<body onload="loadLayoutWin('','mfrNotificationMgmt');" onunload="closeAllchildren();" onresize="resizeFrames()" >
<div class="interface" id="mainPage" style="">
<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<!-- open contentArea -->
<div class="contentArea">
<tcmis:form action="/mfrnotificationmgmtresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<div class="roundcont filterContainer">
				<div class="roundright">
					<div class="roundtop">
						<div class="roundtopright">
							<img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" />
						</div>
					</div>
					<div class="roundContent">
					<%-- Insert all the search option within this div --%>
					<!-- Search Option Table Start -->
					<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">

					<tr>
					 <td width="5%" class="optionTitleBoldRight">
						<fmt:message key="label.requestor"/>:
					 </td>
					 <td width="40%" class="optionTitleLeft">
					 	<input type="text" name="requesterName" id="requesterName" class="inputBox" value=""/>
						<input type="hidden" name="requester" id="requester" value=""/>
						<label for="activeOnly" nowrap>
							<input name="activeOnly" id="activeOnly" onclick="" type="checkbox" checked class="radioBtns" value="Y" />
	         				<fmt:message key="label.activeOnly"/>
          				</label>
					 </td>
					 <td width="40%" colspan="2" class="optionTitleLeft">
					 	<div class="optionTitleBold" style="text-align:center"><fmt:message key="label.status"/></div>
					 </td>
					</tr>

					<tr>
					 <td width="5%" class="optionTitleBoldRight" nowrap>
						<fmt:message key="label.approver"/>:
					 </td>
					 <td width="40%" class="optionTitleLeft">
						<select name="assignedTo" id="assignedTo" class="selectBox">
							<option value=""><fmt:message key="label.all"/></option>
							<option value="-1"><c:out value="Unassigned"/></option>
						<c:forEach var="approver" items="${chemicalApprovers}" varStatus="status">
							<option value="${approver.personnelId}"><tcmis:jsReplace value="${approver.lastName}, ${approver.firstName}"/></option>
						</c:forEach>
						</select>
				     </td>
					 <td width="20%" nowrap>
						<label for="draftStatus">
							<input type="checkbox" id="draftStatus" name="draftStatus" class="radioBtns" value="Draft" checked="checked"/><fmt:message key="label.draft"/>
						</label>
					 </td>
					 <td width="20%" nowrap>
						<label for="pendingApprovalStatus">
							<input type="checkbox" id="pendingApprovalStatus" name="pendingApprovalStatus" class="radioBtns" value="Pending Approval"/><fmt:message key="label.pendingapproval"/>
						</label>
					 </td>
					</tr>

					<tr>
					 <td width="5%" class="optionTitleBoldRight">
						<fmt:message key="label.search"/>:
					 </td>
					 <td width="40%" class="optionTitleBoldLeft">
					 	<select name="searchWhat" id="searchWhat"  onchange="changeSearchTypeOptions(this.value);" class="selectBox">
						 </select>
						 <html:select property="searchType" styleId="searchType" styleClass="selectBox">
								<html:option value="is" ><fmt:message key="label.is"/></html:option>
								<html:option value="contains"><fmt:message key="label.contains"/></html:option>
								<html:option value="startsWith" key="label.startswith"/>
								<html:option value="endsWith" key="label.endswith"/>
						 </html:select>
						 <html:text property="searchText" styleId="search" size="20" styleClass="inputBox"/>
					 </td>
					 <td width="20%" nowrap>
						<label for="approvedStatus">
							<input type="checkbox" id="approvedStatus" name="approvedStatus" class="radioBtns" value="Approved"/><fmt:message key="label.approved"/>
						</label>
						<input type="text" id="approvedWindow" name="approvedWindow" value="30" size="3"/>
						<fmt:message key="label.days" />
					 </td>
					 <td width="20%" nowrap>
						<label for="rejectedStatus">
							<input type="checkbox" id="rejectedStatus" name="rejectedStatus" class="radioBtns" value="Rejected"/><fmt:message key="label.rejected"/>
						</label>
					 </td>
					</tr>
					<tr>
					 <td width="5%" class="optionTitleBoldRight">
						<fmt:message key="label.category"/>:
					 </td>
					 <td width="80%" class="optionTitleBoldLeft" colspan="3">
						<input type="text" name="selectedCategoriesDisp" id="selectedCategoriesDisp" class="inputBox" readonly size="140"/>&nbsp;
						<input type="hidden" name="selectedCategories" id="selectedCategories" />
						<input type="button" name="categoryLookup" id="categoryLookup" value="..." class="lookupBtn" onmouseover="this.className='lookupBtn lookupBtnOver'" onmouseout="this.className='lookupBtn'"  onclick="lookupCategories();" />
						<input type="button" name="clearCategoriesBtn" id="clearCategoriesBtn" value="<fmt:message key="label.clear" />" class="smallBtns" onmouseover="this.className='smallBtns smallBtnsOver'" onmouseout="this.className='smallBtns'" onclick="clearCategories();"/>
					 </td>
					</tr>
					
					<tr>
					 <td width="85%" class="optionTitleLeft" colspan="4">
						<input onclick="return submitSearchForm()" type="submit" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.search"/>" name="search" id="search"/>&nbsp;
						<input type="button" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.createexcelfile"/>" name="createExl" id="createExl" onclick="createXSL()"/>&nbsp;
						<input type="button" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.createnewrequest"/>" name="createNewRequest" id="createNewRequest"/>&nbsp;
						<input type="button" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.addusertoemaillist"/>" name="addUserToEmailListBtn" id="addUserToEmailListBtn" onclick="addUserToEmailList()"/>
					 </td>
					</tr>
					</table>
					<!-- Search Option Table end -->
					</div>
					<div class="roundbottom">
						<div class="roundbottomright">
							<img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
						</div>
					</div>
				</div>
			</div>
		</td>
	</tr>
</table>
<!-- Search Option Ends -->
<!-- Error Messages Begins --><!-- Build this section only if there is an error message to display. For the search section, we show the error messages within its frame --><c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">	<div class="spacerY">&nbsp;		<div id="searchErrorMessagesArea" class="errorMessages">			<html:errors />		</div>	</div></c:if><!-- Error Messages Ends --><!-- Hidden Element start -->
<div id="hiddenElements" style="display:none">
	<input type="hidden" name="uAction" id="uAction" value=""/>
	<input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
	<input name="searchHeight" id="searchHeight" type="hidden" value="400"/>
</div>
</tcmis:form>
</div>
<!-- close of contentArea -->
</div>
<!-- Search Frame Ends -->
<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
<!-- Transit Page Begins -->
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
	<br><br><br><fmt:message key="label.pleasewait"/>
	<br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->
<div id="resultGridDiv" style="display: none;">
<!-- Search results start -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<div class="roundcont contentContainer">
				<div class="roundright">
					<div class="roundtop">
						<div class="roundtopright">
							<img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
						</div>
					</div>
					<div class="roundContent">
						<%-- boxhead Begins --%>
						<div class="boxhead">
							<%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
								Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself. --%>
							<div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
								<a href="#" onclick="call('updateQcAssignees')"><fmt:message key="label.update"/></a>
							</div>
						</div>
						<div class="dataContent"> 
							<iframe  scrolling="no"  id="resultFrame" name="resultFrame" width="100%" height="" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
						</div>
						<%-- result count and time --%>
						<div id="footer" class="messageBar"></div>
					</div>
					<div class="roundbottom">
						<div class="roundbottomright">
							<img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" />
						</div>
					</div>
				</div>
			</div>
		</td>
	</tr>
</table>
<!-- Search results end -->
</div>
</div>
<!-- Result Frame Ends -->
</div>
<!-- close of interface -->
<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a yui pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display:none;z-index:5;">
	<div id="errorMessagesAreaTitle" class="hd"><fmt:message key="label.errors"/></div>
	<div id="errorMessagesAreaBody" class="bd">
		<html:errors/>
	</div>
</div>
</body>
</html:html>