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
<tcmis:gridFontSizeCss />
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
<script type="text/javascript" src="/js/common/cabinet/workareareceiptcount.js"></script>
<script type="text/javascript" src="/js/common/grid/iframegridresizemain.js"></script>

<!-- These are for the Grid-->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<%--This is for HTML form integration.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<%--This is for smart rendering.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<%--This is to suppoert loading by JSON.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>    
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%>
<!-- <script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script> -->

<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--This file has our custom sorting and other utility methos for the grid.--%>    
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<!-- This is for the YUI, uncomment if you will use this -->
<%-- <script type="text/javascript" src="/yui/build/yahoo/yahoo.js" ></script>
<script type="text/javascript" src="/yui/build/event/event.js" ></script>
<script type="text/javascript" src="/yui/build/dom/dom.js" ></script>
<script type="text/javascript" src="/yui/build/animation/animation.js" ></script>
<script type="text/javascript" src="/yui/build/container/container.js"></script>
<script type="text/javascript" src="/js/common/waitDialog.js"></script>
<script type="text/javascript" src="/yui/build/dragdrop/dragdrop.js" ></script> --%>

<!--  These are for the autocomplete function -->
<script type="text/javascript" src="/js/jquery/jquery-1.6.4.js"></script> 
<script type="text/javascript" src="/js/jquery/autocomplete.js"></script> 
<link rel="stylesheet" type="text/css" href="/css/autocomplete.css" />  
  
  
<title>
<fmt:message key="label.receiptcount"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
var showErrorMessage = false;
<c:if test="${not empty tcmisError}">
    showErrorMessage = true;
</c:if>

//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData = {alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
            validvalues:"<fmt:message key="label.validvalues"/>",
            submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",   
            ok:"<fmt:message key="label.ok"/>",
            updatesucess:"<fmt:message key="msg.savesuccess"/>",
            verifyemptybin:"<fmt:message key="label.verifyemptybin"/>",
    		positiveInteger:"<fmt:message key="label.positiveinteger"/>",
    		receipt:"<fmt:message key="label.receipt"/>",
    		damaged:"<fmt:message key="label.damaged"/>",
    		expired:"<fmt:message key="label.expired"/>",
    		expiredOnly:"<fmt:message key="label.receiptcantbeexpiredandnewordamaged"/>",
    		qty:"<fmt:message key="label.qty"/>"
			};
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoad();" onunload="opener.parent.closeTransitWin();" >
<tcmis:form action="/workareareceiptcount.do" onsubmit="return submitFrameOnlyOnce();">

<script type="text/javascript">
    <!--
        showUpdateLinks = true;
     //-->
</script>

 <div id="transitDialogWin" class="optionTitleBoldCenter" style="display: none;">
  <br><br><br><fmt:message key="label.pleasewait"/>
 </div>
 
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none; ">
	${tcmisError}
</div>
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
	 <div class="boxhead"><%-- boxhead Begins --%>
		<%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
				Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
				--%>
		<div id="mainUpdateLinks" style="display: none"><%-- mainUpdateLinks Begins --%>
			<span id="updateResultLink">
				<a href="#" onclick="submitUpdate(); return false;"><fmt:message key="label.update"/></a> | 
				<a href="#" onclick="addLine(); return false;"><fmt:message key="label.addline"/></a> |
				<a href="#" onclick="emptyBin(); return false;"><fmt:message key="label.emptybin"/></a> |
                <a href="#" onclick="closeWindow(); return false;"><fmt:message key="label.cancel"/></a>
            </span>
		</div> <%-- mainUpdateLinks Ends --%>
	</div> <%-- boxhead Ends --%>
    <div class="dataContent">
        <div id="cabinetInventoryCountBean" style="width:100%;height:400px;"></div>

        <script type="text/javascript">
            <!--
            var config = [
                {columnId: "permission"},
                {columnId: "receiptIdPermission"},
                {columnId: "receiptId", columnName:'<fmt:message key="label.receipt"/>',  width:15, align:"left", type:'hed', permission:true},
                <c:choose>
                	<c:when test="${param.countType == 'ADVRECEIPT'}">
	            		{columnId: "countQuantity", columnName:'<fmt:message key="label.qty"/>', attachHeader:'<fmt:message key="label.new"/>', type:'hed', width:5 },
	            		{columnId: "receiptDamagedQty", columnName:'#cspan', attachHeader:'<fmt:message key="label.damaged"/>', type:'hed', width:5 },
	            		{columnId: "receiptExpiredQty", columnName:'#cspan', attachHeader:'<fmt:message key="label.expired"/>', type:'hed', width:5 },
                	</c:when>
                	<c:otherwise>
                		{columnId: "countQuantity", columnName:'<fmt:message key="label.qty"/>', type:'hed', width:5 },
                	</c:otherwise>
                </c:choose>
                {columnId: "editableReceipt"}
            ];

            <c:set var="dataCount" value='0'/> 
   			var advReceipt = false;
            <c:if test="${param.countType == 'ADVRECEIPT'}">
       			advReceipt = true;
	       </c:if>

            var jsonMainData = new Array();
            <c:if test="${empty receiptCollection}">
                var jsonMainData = {
                rows:[
                    <c:set var="editableReceipt" value='Y'/>
                    {id: 1,
                     data: ['Y',
                           '${editableReceipt}',
                           '',
                           '',
                           <c:if test="${param.countType == 'ADVRECEIPT'}">
                           '',
                           '',
                           </c:if>
                           '${editableReceipt}',
                      ]}
                    ]};
                <c:set var="dataCount" value="1" />
            </c:if>
            <c:if test="${!empty receiptCollection}">
                var jsonMainData = {
                rows:[<c:forEach var="bean" items="${receiptCollection}" varStatus="status">
                    <c:set var="editableReceipt" value='N'/>
                    <c:if test="${bean.editableReceipt}"> <c:set var="editableReceipt" value='Y'/> </c:if>
                    {id:${status.count},
                     data:['Y',
                           '${editableReceipt}',
                           '${bean.receiptId}',
                           '${bean.countQuantity}',
                           <c:if test="${param.countType == 'ADVRECEIPT'}">
                           		'${bean.receiptDamagedQty}',
                           		'${bean.receiptExpiredQty}',
                           </c:if>
                           '${editableReceipt}',
                      ]}<c:if test="${!status.last}">,</c:if>
                      <c:set var="dataCount" value='${dataCount+1}'/>
                  </c:forEach>]};
              </c:if>

            //-->
        </script>

	</div>
	<div id="footer" class="messageBar">
	</div>
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

<!-- Hidden element start -->
 <div id="hiddenElements" style="display: none;">
 	<input name="action" id="action" type="hidden" value="${param.action}" />
 	<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
    <input name="minHeight" id="minHeight" type="hidden" value="100"/>
    <input name="itemId" id="itemId" value="${param.itemId}" type="hidden"/>
    <input name="countType" id="itemId" value="${param.countType}" type="hidden"/>
     <input name="test" id="test" value="" type="hidden"/>
 </div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->
</tcmis:form>
</body>
</html:html>