<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

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
<%@ include file="/common/opshubig.jsp" %>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

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
<script type="text/javascript" src="/js/catalog/itemstoragesearchmain.js"></script>


<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>

<title>
<fmt:message key="itemStorageSearch"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages

var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
    and:"<fmt:message key="label.and"/>",
    validvalues:"<fmt:message key="label.validvalues"/>",
    submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
    missingInventoryGroup:"<fmt:message key="label.missinginventorygroup"/>",
    errors:"<fmt:message key="label.errors"/>",
    itemInteger:"<fmt:message key="error.item.integer"/>"};

    defaults.ops.nodefault = true;
    defaults.hub.nodefault = true;

// -->
</script>
</head>

<body bgcolor="#ffffff"  onLoad="loadLayoutWin('','itemStorageSearch');setOps();setDefault();" onunload="closeAllchildren();" onresize="resizeFrames()" >

<div class="interface" id="mainPage" style="">

<!-- Search Frame Begins -->
<div id="searchFrameDiv">
<tcmis:form action="/itemstoragesearchresults.do" onsubmit="return submitSearchOnlyOnce();" target="resultFrame">
<div class="contentArea">

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
    <!-- Search Option Table Start -->
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
        <tr>
             <td width="5%" class="optionTitleBoldRight">
                <fmt:message key="label.operatingentity"/>:
             </td>
             <td width="10%" class="optionTitleLeft">
                <select name="opsEntityId" id="opsEntityId" class="selectBox">
                </select>
                <input type="hidden" name="selectedOpsEntityId" id="selectedOpsEntityId" value=""/>
             </td>
        </tr>

        <tr>
             <td width="5%" class="optionTitleBoldRight">
                <fmt:message key="label.hub"/>:
             </td>
             <td width="10%" class="optionTitleLeft">
                <select name="sourceHub" id="sourceHub" class="selectBox">
                </select>
                <input type="hidden" name="selectedHub" id="selectedHub" value=""/>
                <input type="hidden" name="sourceHubName" id="sourceHubName" value="">
             </td>
        </tr>

        <tr>
            <td width="5%" class="optionTitleBoldRight" nowrap>
                <fmt:message key="label.inventorygroup"/>:
            </td>
            <td width="10%" class="optionTitleBoldLeft">
                <select name="inventoryGroup" id="inventoryGroup" class="selectBox">
                </select>
                <input type="hidden" name="selectedInventoryGroup" id="selectedInventoryGroup" value=""/>
            </td>
        </tr>

        <tr>
            <td width="5%" class="optionTitleBoldRight">
                <fmt:message key="label.search"/>:
            </td>
            <td width="8%" class="optionTitleLeft" nowrap>
                <html:select property="searchWhat" styleId="searchWhat" styleClass="selectBox">
                    <html:option value="i.item_id" key="label.itemid"/>
                    <html:option value="m.material_desc" key="label.materialdesc"/>
                </html:select>

                <html:select property="searchType" styleId="searchType" styleClass="selectBox">
                    <html:option value="is" key="label.is"/>
                    <html:option value="contains" key="label.contains"/>
                    <html:option value="startsWith" key="label.startswith"/>
                    <html:option value="endsWith" key="label.endswith"/>
                </html:select>
                <html:text property="searchText" styleId="searchText" size="20" styleClass="inputBox"/>
            </td>
        </tr>

        <tr>
            <td width="5%" class="optionTitleLeft" colspan="2">
                <input onClick="return submitSearchForm()" type="submit" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.search"/>" name="submitSearch" id="submitSearch">&nbsp;
                <input type="button" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.createexcelfile"/>" name="createExl" id="createExl" onclick="createXSL()">
                <input type="button" class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value="<fmt:message key="label.addusertoemaillist"/>" name="addusertoemaillist" id="addusertoemaillist" onclick="addUserToEmailList()">
            </td>
        </tr>
</table>

 <!-- Search Option Table end -->
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
   <input type="hidden" name="userAction" id="userAction" value="">
   <input name="startSearchTime" id="startSearchTime" type="hidden" value="" />
</div>
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
        <br/><br/><br/><fmt:message key="label.pleasewait"/>
        <br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/>
    </div>
    <!-- Transit Page Ends -->
    <div id="resultGridDiv" style="display: none;">
        <!-- Search results start -->
        <!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
        <table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td>
                    <div class="roundcont contentContainer">
                        <div class="roundright">
                            <div class="roundtop">
                                <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
                            </div>
                            <div class="roundContent">
                                <div class="boxhead">
                                    <div id="mainUpdateLinks" style="display: none">
                                        <div id="updateResultLink" style="display: none">
                                        </div>
                                    </div> <%-- mainUpdateLinks Ends --%>
                                </div> <%-- boxhead Ends --%>

                                <div class="dataContent">
                                    <iframe scrolling="no" id="resultFrame" name="resultFrame" width="100%" height="300" frameborder="0" marginwidth="0" src="/blank.html"></iframe>
                                </div>


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

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a yui pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>
</body>
</html:html>