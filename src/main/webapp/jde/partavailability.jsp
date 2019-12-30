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
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<title>Inventory Search</title>
<script language="JavaScript" type="text/javascript"/>
<!--
var messagesData = {};

function submitRefreshForm() {
    /*
     * Make sure to not set the target of the form to anything other than
     * resultFrame
     */
    
    $("uAction").value = 'refresh';
    //document.displayonly.target = 'resultFrame';
    // for data sending
    document.genericForm.submit();
    showPleaseWait();
      
    return true; 
}   

function loadCachedResults() {
    $("uAction").value = 'cachedResults';
    //document.displayonly.target = 'resultFrame';
    // for data sending
    document.genericForm.submit();
    showPleaseWait();
      
    return true; 
}

function loadFreshResults() {
    $("uAction").value = 'search';
    //document.displayonly.target = 'resultFrame';
    // for data sending
    document.genericForm.submit();
    showPleaseWait();
      
    return true; 
}

//-->
</script>

<c:set var="queryStringSet">
    <c:out value = "${pageContext.request.queryString}" />
</c:set>

<c:if test="${queryStringSet == ''}">
    <c:set var="queryStringSet">
        ${param.queryStringVar}
    </c:set>
</c:if>

</head>
<body onload="loadLayoutWin('','partAvailability');loadCachedResults();" onresize="resizeFrames();">
<div class="interface" id="mainPage" style="">
<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<!-- open contentArea -->
<div class="contentArea">
<tcmis:form action="/partavailabilityresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
<!-- Search Option Begins -->
<table id="searchMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td>
            <div class="roundcont filterContainer" style="width:400px; margin:0 auto;">
                <div class="roundright">
                    <div class="roundtop">
                        <div class="roundtopright">
                            <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" />
                        </div>
                    </div>
                    <div class="roundContent">
                    <%-- Insert all the search option within this div --%>
                    <table width="100%" id="searchTable" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
                                       
                        <tr>
                            <td width="50%" class="optionTitleBoldRight">
                                Part Number Searched: 
                            </td>                        
                            <td width="50%" class="optionTitleBoldLeft">
                                <span id="partNum">${partNum}</span>
                            </td>                                                                        
                        </tr>
                        
                        <tr>
                            <td width="50%" class="optionTitleBoldRight">
                                Last Update Date:
                            </td>                        
                            <td width="50%" class="optionTitleBoldLeft">
                                <span id="updateDate">${updateDate}</span>
                                &nbsp; &nbsp; &nbsp; &nbsp;
                                
                            </td>                                                                                                                                                                
                        </tr>                                                                                                                                                              
                    </table>
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
<!-- Error Messages Begins -->
<!-- Build this section only if there is an error message to display. For the search section, we show the error messages within its frame -->
<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null}">
    <div class="spacerY">&nbsp;
        <div id="searchErrorMessagesArea" class="errorMessages">
            <html:errors />
        </div>
    </div>
</c:if>
<!--Error Messages Ends -->
                
<!-- Hidden Element start -->
<div id="hiddenElements" style="display:none">
    <input type="hidden" name="uAction" id="uAction" value="cachedResults"/>
    <input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
    <input name="partNumber" id="partNumber" type="hidden" value="${param.partNumber}"/>
    <input name="queryStringVar" id="queryStringVar" type="hidden" value="${queryStringSet}"/>
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
                                    <span id="updateResultLink" style="display: none"></span>
                                    <input name="Refresh"  id="Refresh" type="button" value="Refresh" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onMouseout="this.className='inputBtns'" onClick="loadFreshResults();"/>
                                </div>
                            </div>
                            <div class="dataContent"> 
                                <iframe  scrolling="no"  id="resultFrame" name="resultFrame" width="100%" height="400" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
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
