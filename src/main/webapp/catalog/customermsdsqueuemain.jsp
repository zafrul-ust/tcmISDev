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
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"> </link>

<%@ include file="/common/locale.jsp" %>
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

<link rel="stylesheet" type="text/css" href="/css/clientpages.css"></link>
<style type="text/css">
html {
  height: 100%;
  max-height:100%;
  margin-bottom: 1px;
  overflow: hidden;
}
</style>

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

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
    
<title>
<fmt:message key="customerMsdsQueue"/>
</title>
<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",all:"<fmt:message key="label.all"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
missingSearchText:"<fmt:message key="receiptdocumentviewer.searchmessage"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
errors:"<fmt:message key="label.errors"/>",
pleasewait:"<fmt:message key="label.pleasewait"/>"
};

function validateForm()
{
	  if (isWhitespace($v("searchArgument")) && ($v("status") == 'customerIndexing' || $v("status") == 'globalIndexing' || $v("status") == 'maxcomIndexing')) {
	       alert(messagesData.missingSearchText);
	       return false;
	  }
	  
	return true;
}


function submitSearchForm()
{
 /*Make sure to not set the target of the form to anything other than resultFrame*/
  var now = new Date();
  $("startSearchTime").value = now.getTime();
  if(validateForm()) {
   $('uAction').value = 'search';
   document.genericForm.target='resultFrame';
   showPleaseWait();
   document.genericForm.submit();
  }
}

function generateExcel() 
{
	var flag = validateForm();
	if(flag) 
	{	
	 $('uAction').value = 'createExcel';
	 openWinGenericViewFile('/tcmIS/common/loadingfile.jsp',"_CustomerMsdsQueueExcel","650","600","yes");             
	 document.genericForm.target='_CustomerMsdsQueueExcel';
	 var a = window.setTimeout("document.genericForm.submit();",500);
	}
}

function changeSearchTypeOptions(selectedValue)
{
	if(selectedValue == 'materialId' || selectedValue == 'mfgId')
		$("searchMode").value = "is";
	else
		$("searchMode").value = "contains";
}

function changeStatus() {
    if ($v("status") == 'globalQc' || $v("status") == 'globalIndexing' || $v("status") == 'maxcomIndexing') {
        $("companyId").selectedIndex = 0;
        $("companyId").disabled = true;
    }else {
        $("companyId").disabled = false;
    }
}

// -->
 </script>
</head>

<!--call the loadLayoutWin() to set the sizes of the search section and initiate the layout. 
If you dont want to use the layout set javascript variable useLayout=false;-->
<body bgcolor="#ffffff"  onload="loadLayoutWin('','customerMsdsQueue');changeStatus();" onresize="resizeFrames()">

<div class="interface" id="mainPage" style="">

<!-- Top Navigation- we will not display this section after sometime, when we move to the new menu structure -->
<div class="topNavigation" id="topNavigation">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td width="200">
<img src="/images/tcmtitlegif.gif" align="left">
</td>
<td>
<img src="/images/tcmistcmis32.gif" align="right">
</td>
</tr>
</table>
<%@ include file="/common/clientnavigation.jsp" %>
</div>
<!-- end of Top Navigation -->

<!-- Search Frame Begins -->
<div id="searchFrameDiv">

<div class="contentArea">
<tcmis:form action="/customermsdsqueueresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
<!-- Search Option Begins -->
<table id="searchMaskTable" width="500" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td>
            <div class="roundcont filterContainer">
                <div class="roundright">
                    <div class="roundtop">
                        <div class="roundtopright"><img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none"/></div>
                    </div>
                    <div class="roundContent">
                     <!-- Insert all the search option within this div -->
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
		<tr>
			<td width="20%" class="optionTitleBoldRight">
				<fmt:message key="label.company"/>:
			</td>
			<td width="80%" class="optionTitleBoldLeft">
				<select name="companyId" id="companyId" class="selectBox" >
					<option value="" ><fmt:message key="label.mycompanies"/></option>
                    <c:forEach var="cbean" items="${myCompanyCollection}" varStatus="status">
  							<option value="${status.current.companyId}" >${status.current.companyName}</option>
    				</c:forEach>
    			</select>
			</td>
            <td width="20%" class="optionTitleBoldRight">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            </td>
            <td width="20%" class="optionTitleBoldRight">
				<fmt:message key="label.status"/>:
			</td>

			<td width="80%" class="optionTitleBoldLeft">
				<select name="status" id="status" class="selectBox" onchange="changeStatus()" >
                    <option value="globalIndexing"><fmt:message key="label.pendingglobalindexing"/></option>
                    <option value="globalQc"><fmt:message key="label.pendingglobalqc"/></option>
				    <option value="customerIndexing"><fmt:message key="label.pendingcustomerindexing"/></option>
				    <option value="customerQc"><fmt:message key="label.pendingcustomerqc"/></option>
                    <option value="maxcomIndexing"><fmt:message key="label.pendingmaxcomindexing"/></option>
                </select>
			</td>
        </tr>
        <tr>
        	<td class="optionTitleBoldRight">
				<fmt:message key="label.assignedto" />:
			</td>
			<td class="optionTitleLeft">
				<select name="assignedTo" id="assignedTo" class="selectBox">
					<option value="-10"><fmt:message key="label.all"/></option>
					<option value="-1">Unassigned</option>
					<c:forEach var="user" items="${catalogUsers}" varStatus="status">
						<option value="${user.personnelId}">${user.personnelName}</option>
					</c:forEach>
				</select>
			</td>
        </tr>
        <tr>
          <!-- search by -->
          <td width="20%" class="optionTitleBoldRight">
            <fmt:message key="label.search"/>:&nbsp;
          </td>
          <td width="80%" colspan="4" class="optionTitleBoldLeft" nowrap>
            <select name="searchField"  class="selectBox" id="searchField" onchange="changeSearchTypeOptions(this.value)">
	            <option value="materialId"><fmt:message key="label.material"/></option>
				<option value="materialDesc"><fmt:message key="label.description"/></option>
				<option value="mfgDesc"><fmt:message key="label.manufacturer"/></option>
                <option value="mfgId"><fmt:message key="label.manufacturerid"/></option>
                <option value="tradeName"><fmt:message key="label.tradename"/></option>
	          </select>
	          &nbsp;
	          <select name="searchMode" class="selectBox" id="searchMode" >
	               <option value="is"> <fmt:message key="label.is"/>  </option>
	               <option value="contains"> <fmt:message key="label.contains"/>  </option>
	               <option value="startsWith"><fmt:message key="label.startswith"/> </option>
	               <option value="endsWith"><fmt:message key="label.endswith" /></option>
	          </select>
	          &nbsp;                                                            
	          <input name="searchArgument" id="searchArgument" type="text" class="inputBox" size="15" onKeyPress="onKeyPress()" value=""/>
         </td>
       </tr>
      
       <tr>  
         <td colspan="2">   
            <!-- search button -->
            <input name="submitSearch" type="button" class="inputBtns" value="<fmt:message key="label.search"/>" id="submitSearch" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "submitSearchForm()"/>
            <input name="createExcel" id="createExcel" type="button"
						value="<fmt:message key="label.createexcelfile"/>" onclick="generateExcel();" class="inputBtns"
						onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" />
          </td>
        </tr>
    </table>
   <!-- End search options -->
                    </div>
                    <div class="roundbottom">
                        <div class="roundbottomright"><img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none"/></div>
                    </div>
                </div>
            </div>
        </td>
    </tr>
</table>
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
<input name="uAction" id="uAction" type="hidden" value="">
<input name="startSearchTime" id="startSearchTime" type="hidden"value="">
</div>
<!-- Hidden elements end -->
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
<!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr> 
    <td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    <div class="boxhead"> <%-- boxhead Begins --%>
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
      --%>
     <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
         <a href="#" onclick="call('updateAssignedTo');"><fmt:message key="label.update"/></a>
     </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

    <div class="dataContent"> 
     <iframe  scrolling="no"  id="resultFrame" name="resultFrame" width="100%" height="" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
   </div>
   <%-- result count and time --%>
   <div id="footer" class="messageBar"></div>
  </div>

  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</td></tr>
</table>
</div>  
</div><!-- Result Frame Ends -->

</div> <!-- close of interface -->
    <!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a yui pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">
</div>

<!-- Input Transit Window Begins -->
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

</body>
</html:html>