<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>
<html:html lang="true">
<head>
    <jsp:useBean id="today" class="java.util.Date"/>
    <c:set var="javascriptDate"><fmt:message key="javascript.dateformat"/></c:set>
    
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta http-equiv="expires" content="-1">
    <link rel="shortcut icon" href="/images/buttons/tcmIS.ico">
    </link>
    <%@ include file="/common/locale.jsp" %>

    <!--Use this tag to get the correct CSS class.
        This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
    <tcmis:gridFontSizeCss />
    <link rel="stylesheet" type="text/css" href="/yui/build/container/assets/container.css" />  

    <!-- Add any other stylesheets you need for the page here -->
    <script type="text/javascript" src="/js/common/formchek.js"></script>
    <script type="text/javascript" src="/js/common/commonutil.js"></script>

    <%--NEW - grid resize--%>
    <script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
    
    <!-- This handles which key press events are disabled on this page -->
    <script src="/js/common/disableKeys.js"></script>
    
    <!-- This handles the menu style and what happens to the right click on the whole page -->
    <script type="text/javascript" src="/js/menu/milonic_src.js"></script>
    <script type="text/javascript" src="/js/menu/mmenudom.js"></script>
    <script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
    <script type="text/javascript" src="/js/menu/contextmenu.js"></script>
    
    <%@ include file="/common/rightclickmenudata.jsp" %>

    <!-- Add any other javascript you need for the page here -->
    <script type="text/javascript" src="/js/hub/hubcyclecountresults.js"></script>

    <!-- For Calendar support -->
    <script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
    <script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
    <script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
    
    <!-- These are for the Grid, uncomment if you are going to use the grid -->
    <script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
    <script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
    <script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
    <script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
    <script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
    <script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
    
    <%--Uncomment below if you are providing header menu to switch columns on/off--%>
    <!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
    
    <%--Uncomment the below if your grid has rowspans --%>
    <%--
    <script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
    <script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
    --%>
 
    <%--This is to allow copy and paste. works only in IE--%>
    <script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_selection.js"></script>
    <script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_nxml.js"></script>
    
    <%--This has the custom cells we built, hcal - the internationalized calendar which we use hlink- this is for any links you want tp provide in the grid, the URL/function to call can be attached based on a even (rowselect etc) --%>
    <script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
    
    <%--Custom sorting. This custom sorting function implements case insensitive sorting. --%>
    <script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
        
    <!-- Popup window support -->
    <script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script> 
   
    <script src="/js/jquery/jquery-1.6.4.js"></script>

    <script language="JavaScript" type="text/javascript">
        //add all the javascript messages here, this for internationalization of client side javascript messages
        var messagesData = new Array();
        messagesData= {
            alert: "<fmt:message key="label.alert"/>",
            and: "<fmt:message key="label.and"/>", 
            validvalues: "<fmt:message key="label.validvalues"/>",
            submitOnlyOnce: "<fmt:message key="label.submitOnlyOnce"/>",
            itemInteger: "<fmt:message key="error.item.integer"/>",
            recordFound: "<fmt:message key="label.recordFound"/>",
            searchDuration: "<fmt:message key="label.searchDuration"/>",
            minutes: "<fmt:message key="label.minutes"/>",
            seconds: "<fmt:message key="label.seconds"/>",
            pleasewait: "<fmt:message key="label.pleasewaitmenu"/>",            
            actualCount: "<fmt:message key="label.actualcount"/>",
            nonZeroCount:"<fmt:message key="label.nonnegativenumber"><fmt:param><fmt:message key="label.actualcount"/></fmt:param></fmt:message>",
            quantitycounted: "<fmt:message key="error.quantitycountedpercent"/>",
            doublecheckquantity: "<fmt:message key="label.doublecheckquantity"/>",
            receiptsnotcountedcloseanyway: "<fmt:message key="label.receiptsnotcountedcloseanyway"/>",
            cannotstartnewcount: "<fmt:message key="label.cannotstartnewcount"/>",
            startcountfornextmonth: "<fmt:message key="label.startcountfornextmonth"/>",
            cannotstartnewcountnow: "<fmt:message key="label.cannotstartnewcountnow"/>",
            quantitycounteddifferent: "<fmt:message key="error.quantitycounteddifferent"/>"
           };

        <%-- See webroot/dhtmlxgrid/codebase/dhtmlxcommon_haas.js for option explanations of config & gridconfig --%>
           <c:set var="inventorygroup"><fmt:message key="label.inventorygroup"/></c:set>
           <c:set var="inpurchasing"><fmt:message key="label.inpurchasing"/></c:set>  
           var config = [ 
            {columnId: "permission"},
            {columnId: "room", columnName:'<fmt:message key="label.room"/>', tooltip:true, width:6, submit:true},
            {columnId: "bin", columnName:'<fmt:message key="label.bin"/>', tooltip:true, width:8},
            {columnId: "receiptId", columnName:'<fmt:message key="label.receiptid"/>', width:6, align:"center", submit:true},
            {columnId: "counted", columnName:'<fmt:message key="label.counted"/><br><input type="checkbox" value="" onClick="return checkAll(\'counted\');" name="checkAllCounted" id="checkAllCounted">', type:"hchstatus", width:6, align:"center", onChange:"countedChecked", submit:true},
            {columnId: "expectedQuantity", columnName:'<fmt:message key="label.expectedquantity"/>', sort:"int", width:4, align:"right"},
            {columnId: "actualQuantity", columnName:'<fmt:message key="label.actualquantity"/>', type:"hed", width:6, maxlength:22, size:5, align:"center", onChange:"validateCountedQuantity", submit:true},
            {columnId: "description", columnName:'<fmt:message key="label.description"/>', tooltip:true, width:30},
            {columnId: "countId", submit:true},
            {columnId: "countType", submit:true},            
            {columnId: "countMonth"},
            {columnId: "countDatetime"}
           ];
           
           var gridConfig = {
               divName:'hubCycleCountDisplayViewBean', // the div id to contain the grid.
               beanData:'jsonMainData', // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
               beanGrid:'mygrid', // the grid's name, as in beanGrid.attachEvent...
               config:config, // the column config var name, as in var config = { [ columnId:..,columnName...
               rowSpan:false, // true: this page has rowSpan > 1 or not, disables smartrendering & sorting
               backgroundRender: true,
               submitDefault:false // the fields in grid are defaulted to be submitted or not.               
           };
           
           var month = ['JAN','FEB','MAR','APR','MAY','JUN','JUL','AUG','SEP','OCT','NOV','DEC'];

           function verifyAllRowsCounted() {
        	  var hubId =  document.getElementById("hub").value;
        	  var countId =  document.getElementById("countId").value;
        	          	  
              j$.ajax({
                 type: "POST",
                  url: "/tcmIS/hub/hubcyclecountresults.do",
                 data: { 
                        uAction:'allRowsCounted',
                        hub: hubId,
                        countId: countId 
                       },
                    success: promptUser
                });
           }

           function promptUser(results) {
               if (results != null && results > 0 ) {                   
                   if (confirm(results + " " + messagesData.receiptsnotcountedcloseanyway)) {
                	   cycleCountClose();
                   } else { 
                	   return false;
                   }
               } else if (results != null && results == 0) {
            	   cycleCountClose();
               }
           }
           
           function isValidCreateNewCountId() {        	  
               var hubId =  document.getElementById("hub").value;
               var countMonth =  document.getElementById("countMonth").value;
              
               j$.ajax({
                  type: "POST",
                   url: "/tcmIS/hub/hubcyclecountresults.do",
                  data: { 
                         uAction:'isValidCreateNewCountId',
                         hub: hubId,                         
                         countMonth: countMonth 
                        },
                     success: isValidCreateResponse
                 });
            }

           function isValidCreateResponse(res) {               
        	   var results = jQuery.parseJSON(res);        	    
        	   var countMonth = results.CountMonth;
        	   var createCountId =  results.CreateCountId;
        	    
               if (createCountId == true) {
            	   startCycleCount();
               }  else {
                   if (confirm(messagesData.cannotstartnewcount +  month[countMonth-1] + ". " + messagesData.startcountfornextmonth)) {
                	   isNextMonthCountStarted();   
                   }
               }
           }

           function isNextMonthCountStarted() {             
               var hubId =  document.getElementById("hub").value;
               var countMonth =  document.getElementById("countMonth").value;

               j$.ajax({
                  type: "POST",
                   url: "/tcmIS/hub/hubcyclecountresults.do",
                  data: { 
                         uAction:'isNextMonthCountStarted',
                         hub: hubId,                         
                         countMonth: countMonth 
                        },
                     success: isValidNextMonth
                 });
            }

           function isValidNextMonth(results) {

        	   if (results == "false") {
                   startCycleCount();
               } else {
                   alert(messagesData.cannotstartnewcountnow);
               }
           }
           
      </script>
</head>
<body bgcolor="#ffffff" onload="myResultOnLoadWithGrid(gridConfig);">
    <tcmis:form action="/hubcyclecountresults.do" onsubmit="return submitFrameOnlyOnce();">
        <script  language="JavaScript" type="text/javascript">
            <c:set var="countMonthAbrev" value=""/>
            <c:if test= "${!empty HubCycleCountViewBeanCollection}" >
                var jsonMainData = {
                    rows: [
                        <c:forEach var= "p" items= "${HubCycleCountViewBeanCollection}" varStatus= "status" >                            
                            <c:set var="checked" value="false"/>
                            <c:set var="countMonthAbrev" value="${p.countMonth}"/>
                            <c:if test= "${status.index != 0 }" >, </c:if>
                            { id: ${status.index + 1},
                              data: ['Y',
                                 '${p.room}',
                                 '${p.bin}',
                                 '${p.receiptId}',
                                 <c:choose><c:when test="${checked == true}">true</c:when><c:otherwise>false</c:otherwise></c:choose>,                                 
                                 '${p.expectedQuantity}',
                                 '${p.actualQuantity}',
                                 '<tcmis:jsReplace value="${p.itemDesc}" processMultiLines="true"/>',
                                 '${p.countId}',
                                 '${p.countType}',
                                 '${p.countMonth}',                                    
                                 <fmt:formatDate var="dispDate" value="${p.countDatetime}" pattern="${javascriptDate}"/>'${dispDate}'
                                ] 
                            }
                        </c:forEach>
                    ]
                };
            </c:if>
        </script>       
            
        <!-- Check if the user has permissions and needs to see the update links,set the variable you use in javascript to true.
            The default value of showUpdateLinks is false.
        -->
            <script type="text/javascript">
                showUpdateLinks = true;
                noCountId = <c:choose><c:when test="${empty countId}">true</c:when><c:otherwise>false</c:otherwise></c:choose>;
                noResultsFound = <c:choose><c:when test="${empty HubCycleCountViewBeanCollection}">true</c:when><c:otherwise>false</c:otherwise></c:choose>;
            </script>

        
        <!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
            So this is just used to feed the YUI pop-up in the main page.
            Similar divs would have to be built to show any other messages in a pop-up.-->
        <!-- Error Messages Begins -->
        <div id="errorMessagesAreaBody" style="display:none;">
            ${tcmisError}
        </div>
        <script type="text/javascript">
            <!--
            /*YAHOO.namespace("example.aqua");
            YAHOO.util.Event.addListener(window, "load", init);*/
            /*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
            <c:choose> 
                <c:when test= "${empty tcmisError}" >
                    showErrorMessage = false;
                </c:when>
                <c:otherwise>
                    showErrorMessage = true;
                </c:otherwise> 
            </c:choose>//-->
        </script>
        <!-- Error Messages Ends -->        
        <div class="interface" id="resultsPage">
            <div class="backGroundContent">
                <c:if test="${!empty HubCycleCountViewBeanCollection}" >
                    <table width="100%" >
                        <tr>
                            <td nowrap width="5%"  class="optionTitleBoldRight">
                                 <span id="mnthSpan" name="mnthSpan" />
                            </td>
                            <td nowrap width="7%"  class="optionTitleBoldCenter">
                                <fmt:message key="label.countfor" />
                            </td>
                            <td nowrap width="75%" class="optionTitleBoldLeft">
                                <span id="hubNameSpan" name="hubNameSpan" />
                            </td>                                              
                        </tr>
                    </table>
                </c:if>
                <%--NEW - there is no results table anymore--%>
                <div id="HubCycleCountDisplayViewBean" style="width:100%;height:400px;" style="display: none;" >
                </div>
                <!-- If the collection is empty say no data found -->
                <c:if test="${empty HubCycleCountViewBeanCollection}" >
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
                        <tr>
                            <td width="100%">
	                            <c:choose>
	                                <c:when test="${!empty countId}" >
	                                    <fmt:message key="error.noitemsscheduled" />
	                                </c:when>
	                                <c:otherwise>
	                                   <fmt:message key="main.nodatafound" />
	                                </c:otherwise>
	                            </c:choose>
                            </td>
                        </tr>
                    </table>
                </c:if>
                <!-- Hidden element start -->
                <div id="hiddenElements" style="display: none;">
                    <tcmis:setHiddenFields beanName="HubCycleCountInputBean"/>
                    <input type="hidden" id="countId" name="countId" value="${countId}"/>
                    <input type="hidden" id="showUncountedOnly" name="showUncountedOnly" value="${param.showUncountedOnly}"/>
                    <input type="hidden" id="countMonth" name="countMonth" value="${countMonth}"/>
                </div>
                <!-- Hidden elements end -->
            </div>
            <!-- close of backGroundContent -->
        </div>
        <script type="text/javascript">
           var mnt = <c:choose><c:when test="${empty countMonthAbrev}">0</c:when><c:otherwise><c:out value="${countMonthAbrev}"/></c:otherwise></c:choose>; 
           if (mnt != 0 ) {
        	    document.getElementById("mnthSpan").innerHTML = month[mnt-1];
        	    if (parent.hubName != null)
        	        document.getElementById("hubNameSpan").innerHTML = parent.hubName;
           }
        </script> 
        <!-- close of interface -->
    </tcmis:form>
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

