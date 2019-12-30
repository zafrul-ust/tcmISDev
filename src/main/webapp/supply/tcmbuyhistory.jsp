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
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>
<script type="text/javascript" src="/js/supply/tcmbuyhistory.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--NEW - dhtmlX grid files--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>-->
<%--Uncomment the below if your grid has rwospans >1--%>
<!--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
-->
<%--This has the custom cells we built,
    hcal - the internationalized calendar which we use
    hlink - this is for any links you want tp provide in the grid, the URL/function to call
           can be attached based on a event (rowselect etc)
    hed -editable sinlge line text
    hcoro -select drop down
    hch -checkbox
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<%--Custom sorting.
This custom sorting function implements case insensitive sorting.
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
    
<title>
<fmt:message key="label.haastcmbuyhistoryforitem"/>: <c:out value="${param.itemId}"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
errors:"<fmt:message key="label.errors"/>",  
itemInteger:"<fmt:message key="error.item.integer"/>"};

var config = [
{ columnId:"radianPo",
  columnName:'<fmt:message key="label.po"/>',
  width:5
},
{ columnId:"supplierName",
  columnName:'<fmt:message key="label.supplier"/>',
  width:12,
  tooltip:"Y" 
},
{ columnId:"dateConfirmedDiplay",
  columnName:'<fmt:message key="label.orderdate"/>',
  width:8,
  sorting:'int'
},
{ columnId:"dateConfirmed",  
  sorting:'int'
},
{ columnId:"originalDateConfirmedDiplay",
  columnName:'<fmt:message key="label.originalconfirmdate"/>',
  width:8,
  sorting:'int'
},
{ columnId:"originalDateConfirmed",	
  sorting:'int'
},
{ columnId:"quantity",
  columnName:'<fmt:message key="label.qty"/>',  
  width:5,
  sorting:'int'
},
{ columnId:"unitPriceDisplay",
  columnName:'<fmt:message key="label.unitprice"/>',
  sorting:'int'
},
{ columnId:"unitPrice",
  sorting:'int'
},
{ columnId:"shipToLocationId",
  columnName:'<fmt:message key="label.shipto"/>'
},
{ columnId:"carrier",
  columnName:'<fmt:message key="label.carrier"/>',
  width:12,
  tooltip:"Y" 
},
{ columnId:"buyerName",
  columnName:'<fmt:message key="label.buyer"/>',
  tooltip:"Y" 
},
{ columnId:"customerPo",
  columnName:'<fmt:message key="label.customerpo"/>',
  tooltip:"Y" 
},
{ columnId:"supplierContactName",
  columnName:'<fmt:message key="label.ordertakenby"/>',
  tooltip:"Y" 
},
{ columnId:"phone",
  columnName:'<fmt:message key="label.phoneno"/>'
},
{ columnId:"quantityReceived",
  columnName:'<fmt:message key="label.qtyreceived"/>',
  width:6,
  sorting:'int'
},
{ columnId:"firstTimeReceivedDiplay",
  columnName:'<fmt:message key="label.receivedate"/>', 
  sorting:'int'
},
{ columnId:"firstTimeReceived",
  sorting:'int'
}
];
// -->
</script>

<script LANGUAGE="JavaScript" TYPE="text/javascript">
<!--
        with(milonic=new menuname("purchaseorder")){
         top="offset=2"
         style = contextStyle;
         margin=3
         aI("text=<fmt:message key="label.viewpurchaseorder"/>;url=javascript:showPurchOrder();"); 
    }

    drawMenus();
// -->
</script>
</head>

<!--TODO Show ship to location name or address.-->

<body bgcolor="#ffffff"  onload="resultOnLoad();" onresize="resizeFrames()">

<tcmis:form action="/tcmbuyhistory.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="mainPage" style="">

<!--  update permissions here, if it is read-only page you don't need this -->
<%--<c:set var="pickingPermission" value=''/>
<tcmis:facilityPermission indicator="true" userGroupId="Picking" >--%><%--
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
  <c:set var="pickingPermission" value='Yes'/>
 //-->
 </script>
</tcmis:facilityPermission>--%>

<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
 <html:errors/>
</div>
<!-- Error Messages Ends -->

<script type="text/javascript">
<!--

/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null}">
    showErrorMessage = false;
   </c:when>
   <c:otherwise>
    showErrorMessage = true;
   </c:otherwise>
</c:choose>   
//-->
</script>

<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">

<%--NEw -Transit Page Starts --%>
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->

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
    <div class="boxhead"> <%-- boxhead Begins --%>
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
          Since this page is read-only I don't need to hide the mainUpdateLinks div, I am showing the link Close to all.
      --%>      
      <div id="mainUpdateLinks" style=""> <%-- mainUpdateLinks Begins --%>
       <%--<a href="#" onclick="call('printGrid'); return false;"><fmt:message key="label.print"/></a>--%>
       <span id="closeLink"><a href="#" onclick="window.close()"><fmt:message key="label.close"/></a></span>
      <%--
      <span id="updateResultLink" style="display: none">
        <tcmis:permission indicator="true" userGroupId="transactions">
         <a href="#" onclick="submitUpdate(); return false;"><fmt:message key="label.update"/></a>
        </tcmis:permission>
      </span> --%>
     </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

<div class="dataContent">
 <!--  result page section start -->
<div id="poViewBean" style="height:400px;display: none;" ></div>
<c:if test="${poViewBeanCollection != null}" >

<%--NEW - storing the data to be displayed in the grid in a JSON.--%>
<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty poViewBeanCollection}" >
    <script type="text/javascript">
    <!--
        var jsonMainData = new Array();
        var jsonMainData = {
        rows:[
        <c:forEach var="poView" items="${poViewBeanCollection}" varStatus="status">
        <fmt:formatDate var="fmtDateConfirmed" value="${status.current.dateConfirmed}" pattern="${dateFormatPattern}"/>
        <fmt:formatDate var="fmtOriginalDateConfirmed" value="${status.current.originalDateConfirmed}" pattern="${dateFormatPattern}"/>
        <fmt:formatDate var="fmtFirstTimeReceived" value="${status.current.firstTimeReceived}" pattern="${dateFormatPattern}"/>
        <c:set var="dateConfirmedSortable" value="${status.current.dateConfirmed.time}"/>
        <c:set var="originalDateConfirmedSortable" value="${status.current.originalDateConfirmed.time}"/>
        <c:set var="firstTimeReceivedSortable" value="${status.current.firstTimeReceived.time}"/>
        
        <c:choose>
   			<c:when test="${status.current.unitPrice eq null}">
     			<c:set var="currencyId" value='' />
   			</c:when>
   			<c:otherwise>
     			<c:set var="currencyId" value='${status.current.currencyId}' />
   			</c:otherwise>
		</c:choose>
		<fmt:formatNumber var="unitPrice" value="${status.current.unitPrice}"  pattern="${unitpricecurrencyformat}"></fmt:formatNumber>
        
        <c:if test="${status.index > 0}">,</c:if>                
        /*The row ID needs to start with 1 per their design.*/
        { id:${status.index +1},
         data:[
         '${status.current.radianPo}',
         '<tcmis:jsReplace value="${status.current.supplierName}"/>','${fmtDateConfirmed}',
         '${dateConfirmedSortable}','${fmtOriginalDateConfirmed}','${originalDateConfirmedSortable}',
         '${status.current.quantity}','${currencyId} ${unitPrice}','${status.current.unitPrice}',
         '${status.current.shipToLocationId}',
         '<tcmis:jsReplace value="${status.current.carrier}" />','<tcmis:jsReplace value="${status.current.buyerName}"/>',
         '<tcmis:jsReplace value="${status.current.customerPo}" />',
         '<tcmis:jsReplace value="${status.current.supplierContactName}"/>','${status.current.phone}',
         '${status.current.quantityReceived}','${fmtFirstTimeReceived}','${firstTimeReceivedSortable}']}
        <c:set var="dataCount" value='${dataCount+1}'/>
         </c:forEach>
        ]}; 
    // -->
    </script>
</c:if>

<!-- If the collection is empty say no data found -->
<c:if test="${empty poViewBeanCollection}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td>
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
</table>
</c:if>
<!-- Search results end -->
</c:if>
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

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
<input name="action" id="action" value="" type="hidden"/>
<input name="itemId" id="itemId" type="hidden" value="${param.itemId}">
<input name="supplier" id="supplier" type="hidden" value="${param.supplier}">

<!--This sets the start time for time elapesed.-->
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}">
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}">    
<input name="minHeight" id="minHeight" type="hidden" value="100">

</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>
</body>
</html:html>