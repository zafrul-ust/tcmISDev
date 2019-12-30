<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

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
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<link rel="stylesheet" type="text/css" href="/css/haasMenu.css"></link>

<%@ include file="/common/locale.jsp" %>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<%--NEW--%>
<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<!-- For Calendar support
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
 -->
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<title>
  <fmt:message key="title.msdsviewer"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",all:"<fmt:message key="label.all"/>",
showlegend:"<fmt:message key="label.showlegend"/>",errors:"<fmt:message key="label.errors"/>",  
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
pleaseselect:"<fmt:message key="label.pleaseselect"/>",
inputSearchText:"<fmt:message key="label.inputSearchText"/>",
showlegend:"<fmt:message key="label.showlegend"/>",
selectFacility:"<fmt:message key="msg.selectFacility"/>",
showInstructions:"<fmt:message key="label.showinstructions"/>",
hideInstructions:"<fmt:message key="label.hideinstructions"/>",
simpleMsdsSearchValidate1:"<fmt:message key="label.simplemsdssearchvalidate1"/>",
simpleMsdsSearchValidate2:"<fmt:message key="label.simplemsdssearchvalidate2"/>",
simpleMsdsSearchValidate3:"<fmt:message key="label.simplemsdssearchvalidate3"/>",    
itemInteger:"<fmt:message key="error.item.integer"/>"};

var showSearchInstr = true;

function validateForm() {
    if( isWhitespace($v("simpleSearchText"))) {
		alert(messagesData.inputSearchText);
		return false;
	}else {
	    if( $v('facilityId') == 'PleaseSelect' ) {
            alert(messagesData.selectFacility);
            return false;
        }

        if (searchTextContainBracket()) {
            alert(messagesData.simpleMsdsSearchValidate1);
		    return false;
        }
        if (!parenthesisMatch()) {
            alert(messagesData.simpleMsdsSearchValidate2);
		    return false;
        }
        if (startsEndsWithOperator()) {
            alert(messagesData.simpleMsdsSearchValidate3);
		    return false;
        }
    }
    return true;
}

function startsEndsWithOperator() {
    var result = false;
    var tmpSearchText = $v("simpleSearchText");
    var tmpArray = tmpSearchText.toLocaleLowerCase().split(" ");
    //start with operators
    if (tmpArray[0] == "and" || tmpArray[0] == "or" || tmpArray[0] == "not")
        result = true;
    //end with operators
    if (tmpArray[tmpArray.length-1] == "and" || tmpArray[tmpArray.length-1] == "or" || tmpArray[tmpArray.length-1] == "not")
        result = true;
    return result;
}

function parenthesisMatch() {
    var result = false;
    var tmpSearchText = $v("simpleSearchText");
    var leftCount = countParenthesis(tmpSearchText,"(");
    var rightCount = countParenthesis(tmpSearchText,")");
    //no parenthesis
    if (leftCount == 0 && rightCount == 0) {
        result = true;
    }else {
        if (leftCount == rightCount) {
            if ((tmpSearchText.lastIndexOf("(") < tmpSearchText.lastIndexOf(")")) && (tmpSearchText.indexOf("(") < tmpSearchText.indexOf(")")) )
                result = true;
        }
    }
    return result;
}

function countParenthesis(tmpText,parenthesis) {
    var result = 0;
    var tmpIndex = 0;
    do {
        tmpIndex =  tmpText.indexOf(parenthesis,tmpIndex);
        if (tmpIndex > -1) {
            result++;
            tmpIndex = tmpIndex+1;
            //stop if last index is found
            if (tmpIndex == tmpText.length)
                tmpIndex = -1;
        }
    }while (tmpIndex > -1)
    return result;
}

function searchTextContainBracket() {
    var result = false;
    var tmpSearchText = $v("simpleSearchText");
    if (tmpSearchText.indexOf('{') > -1 || tmpSearchText.indexOf('}') > -1)
        result = true;

    return result;
}

function submitSearchForm() {
	if (!validateForm())
		return false;

    // hide search instructions
    $('searchInstructionsDiv').style["display"] = "none";
    showSearchInstr = false;

    $("uAction").value = 'search';
    var now = new Date();
    var startSearchTime = document.getElementById("startSearchTime");
    startSearchTime.value = now.getTime();
    showPleaseWait();
    document.msdsViewerForm.target='resultFrame';
    document.msdsViewerForm.submit(); 
}

function onKeySearch(e,doFunction) {
  var keyCode;
  if(window.event){
    	keyCode = window.event.keyCode;     //IE
  } else {
	    try {
	      keyCode = e.which;     //firefox
	    } catch (ex){
	      //return false;
	    }
  }
 
  if (keyCode==13) {
    doFunction();
  }
}

function generateExcel() 
{
	if (!validateForm()) 
		return false;

	 $("uAction").value = 'createExcel';
	 openWinGenericViewFile('/tcmIS/common/loadingfile.jsp',"MSDSExcel","650","600","yes");             
	 document.msdsViewerForm.target='MSDSExcel';
	 var a = window.setTimeout("document.msdsViewerForm.submit();",500);
}

function searchInstructionsClicked() {
    if (showSearchInstr) {
        $('searchInstructionsDiv').style["display"] = "none";
        showSearchInstr = false;
    }else {
        $('searchInstructionsDiv').style["display"] = "";
        showSearchInstr = true;
    }
}


// -->
</script>
</head>
<%-- displayOnly needs to match the pageData in application.jsp if the page will be on the menu --%>
<body bgcolor="#ffffff" onload="loadLayoutWin('','newMsds');" onresize="resizeFrames()">

<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">

    <%-- standalone add our image --%>
    <c:if test="${param.standalone == 'y'}">
        <c:set var="module"><tcmis:module /></c:set>
        <div class="header">
            <div class="alignLeft">
                <c:if test="${module == 'merck'}">
                    <img src="/images/logo/merck.png" height="43">
		            <img src="/images/logo/logo_ltblue.gif" width="174" height="43">
                </c:if>
                <c:if test="${module != 'merck'}">
                    <img src="/images/logo/logo_HASS.gif" width="44" height="43">
                    <img src="/images/logo/logo.gif" width="174" height="43">
                </c:if>
            </div>
        </div>
        <div class="menus">
            <div class="menuContainer">&nbsp;</div>
        </div>
    </c:if>

<%--NEW - removed the search frame and copied the search section here--%>
<div class="contentArea">
<tcmis:form action="/msdsviewerresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
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
    <%--  <table width="700" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch"> --%>
    <table width="600" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
    <%-- Row 1 --%>
    <c:set var="module">< tcmis:module/></c:set>
    <c:set var="companyId" value=""/>
    <tr>
            <td width="10%" class="optionTitleBoldLeft"nowrap>&nbsp;
                <fmt:message key="label.facility"/>:
                &nbsp;
                <c:set var="selectedFacilityId" value="${personnelBean.myDefaultFacilityId}"/>
                <select name="facilityId" id="facilityId" class="selectBox" >
                    <c:if test='${module == "lmco"}'>
                	    <option value="PleaseSelect"><fmt:message key="label.selectafacility"/></option>
                	</c:if>
                	<c:if test='${module != "lmco"}'>
                        <option value=""><fmt:message key="label.allfacilities"/></option>
                    </c:if>
                	<c:forEach var="facBean" items="${facilityWorkareaColl}" varStatus="status">
                        <c:if test="${status.index == 0}">
                            <c:set var="companyId" value="${facBean.companyId}"/>    
                        </c:if>
                        <option value="${facBean.facilityId}"><tcmis:jsReplace value="${facBean.facilityName}"/></option>
                	</c:forEach>
                	<c:if test='${module == "lmco"}'>
                	    <option value=""><fmt:message key="label.allfacilities"/></option>
                	</c:if>
                </select>

            </td>

            <td width="30%" class="optionTitleBoldLeft">
                &nbsp;
            </td>
            <!-- search by -->
            <td width="10%" >
	            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	        </td>
	        <td width="10%" id="simpleSearchTd1" class="optionTitleBoldRight" nowrap>
	            <fmt:message key="label.search"/>:&nbsp;
	        </td>
	        <td width="50%" id="simpleSearchTd2" class="optionTitleBoldLeft" nowrap>
		        &nbsp;         
	            <input name="simpleSearchText" id="simpleSearchText" type="text" class="inputBox" onkeypress="return onKeySearch(event,submitSearchForm);" size="45" value="">&nbsp;
	        </td>
	        <td width="10%">
	            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="/images/imageViewer/help.png" onmouseover=style.cursor="hand" onclick="searchInstructionsClicked();">
	        </td>
        </tr>

       <tr>  
         <td colspan="7">   
            <!-- search button -->
            &nbsp;
            <input name="submitSearch" id="submitSearch" type="button" class="inputBtns" value="<fmt:message key="label.search"/>" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" onclick= "submitSearchForm(); return false;">
            <input name="createExcel" id="createExcel" type="button"
				   value="<fmt:message key="label.createexcelfile"/>" onclick="generateExcel();" class="inputBtns"
				   onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" />
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
  <input type="text" name="uAction" id="uAction" value=""/>
  <input type="text" name="hideOrShowDiv" id="hideOrShowDiv" value="hide"/>
  <input name="companyId" id="companyId" type="hidden" value="${companyId}" />
  <input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
 <!-- needed if this page will show on application.do -->
 <!-- To get the correct height value to insert, insert showSearchHeight = true; anywhere in the JavaScript array section in main.jsp.  -->
  <input name="searchHeight" id="searchHeight" type="hidden" value="214"/>
</div>
<!-- Hidden elements end -->

<!-- Error Messages Ends -->
</tcmis:form>
</div> <!-- close of contentArea -->

</div>
<!-- Search Frame Ends -->

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">

<%--NEw -Transit Page Starts --%>
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br/><br/><br/><fmt:message key="label.pleasewait"/>
  <br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/>
</div>
<!-- Transit Page Ends -->

<%-- search instructions --%>
<div id="searchInstructionsDiv" class="optionTitleBoldLeft">
   <br/><fmt:message key="label.instructions"/>:
   <c:if test='${module == "lmco"}'>
       <br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="label.simplemsdssearchinstruction1lmco"/>
       <br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="label.simplemsdssearchinstruction2"/>
       <c:if test="${showCASNumber == 'N'}">
            <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- <fmt:message key="label.simplemsdssearchinstruction3lmco"/>
       </c:if>
       <c:if test="${showCASNumber == 'Y'}">
            <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- <fmt:message key="label.simplemsdssearchinstruction3lmcocas"/>
       </c:if>
   </c:if>
   <c:if test='${module != "lmco"}'>
       <br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="label.simplemsdssearchinstruction1"/>
       <br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="label.simplemsdssearchinstruction2"/>
       <c:if test="${showCASNumber == 'N'}">
            <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- <fmt:message key="label.simplemsdssearchinstruction3"/>
       </c:if>
       <c:if test="${showCASNumber == 'Y'}">
            <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- <fmt:message key="label.simplemsdssearchinstruction3cas"/>
       </c:if>
   </c:if>
   <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- <fmt:message key="label.simplemsdssearchinstruction4"/>
   <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="label.simplemsdssearchinstruction5"/>
   <br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="label.simplemsdssearchinstruction6"/>
   <br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="label.simplemsdssearchinstruction7"/>
   <br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="label.simplemsdssearchinstruction8"/>    
</div>
<!-- search instructions End -->

<div id="resultGridDiv" style="display: none;">
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% or 800 pixels. Make sure to set resizeGridWithWindow to false. -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">    
    <div class="boxhead"> 
    <%-- boxhead Begins --%>
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
          Please keep the <span></span> on the same line this will avoid extra virtical space.
      --%>     
		<%-- Use this case if you only have one update link to minimize extra line.  Notice this uses "div" instead of "span" --%>
	  <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
      </div> <%-- mainUpdateLinks Ends --%>
     <%-- END OF OR --%>
	</div> <%-- boxhead Ends --%>

    <div class="dataContent">
     <iframe scrolling="no" id="resultFrame" name="resultFrame" width="100%" height="300" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
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

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">
</div>

<%-- show legend Begins --%>
<div id="showLegendArea" style="display: none;overflow: auto;">
  <table width=100% class="tableResults" border="0" cellpadding="0" cellspacing="0">
    <tr><td width="100px" class="yellow legendText">&nbsp;</td><td class="legendText"><fmt:message key="label.pendingccauth"/></td></tr>
    <tr><td width="100px" class="red legendText">&nbsp;</td><td class="legendText"><fmt:message key="label.qualityhold"/></td></tr>
  </table>
</div>
<%-- show legend Ends --%>
</body>
</html:html>
