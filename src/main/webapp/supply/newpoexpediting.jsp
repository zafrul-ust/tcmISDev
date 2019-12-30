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

<!-- For Calendar support -->
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/supply/newpoexpediting.js"></script>
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>
    
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


<%--This has the custom cells we built, hcal - the internationalized calendar which we use
    hlink- this is for any links you want tp provide in the grid, the URL/function to call
    can be attached based on a even (rowselect etc)
--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
<fmt:message key="poexpediting.title"/>
</title>
  
<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
and:"<fmt:message key="label.and"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
itemInteger:"<fmt:message key="error.item.integer"/>",
ok:"<fmt:message key="label.ok"/>",
maximum2000:"<fmt:message key="label.maximum2000"/>",
revisedPromisedDate:"<fmt:message key="label.revisedpromisedate"/>",
norow:"<fmt:message key="error.norowselected"/>",
errors:"<fmt:message key="label.errors"/>"};


// -->
</script>

    <script LANGUAGE="JavaScript" TYPE="text/javascript">
<!--
        with(milonic=new menuname("history")){
         top="offset=2"
         style = contextStyle;
         margin=3
            aI("text=<fmt:message key="label.viewpurchaseorder"/>;url=javascript:showPurchOrder();");
            aI("text=<fmt:message key="label.showhistory"/>;url=javascript:showHistory();");
            aI("text=<fmt:message key="label.showitemhistory"/>;url=javascript:showItemHistory();");
           
    }

    drawMenus();
// -->
    </script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoad();"   onresize="resizeFrames()">

<tcmis:form action="/newpoexpediting.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">
<div class="interface" id="resultsPage">

<!-- You can build your error messages below. But we want to trigger the pop-up from the main page.
So this is just used to feed the pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
  ${tcmISError}<br/>
  <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
  </c:forEach>
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
<!-- Result Frame Begins -->
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;">
<!-- Transit Page Begins -->
<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
  <br><br><br><fmt:message key="label.pleasewait"/>
  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
</div>
<!-- Transit Page Ends -->

<div id="resultGridDiv" style="display: none;">
<!-- results start -->
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
          Please keep the <span></span> on the same line this will avoid extra virtical space.
      --%>
   
      <div id="mainUpdateLinks" style="display:none"> <%-- mainUpdateLinks Begins --%>
       <span id="updateResultLink"><a href="#" onclick="update();"><fmt:message key="label.update"/></a></span>         
     </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

<div class="dataContent">
 <!--  result page section start -->
<div id="newPoExpediteBean" style="width:100%;height:600px;" style="display: none;"></div>

<c:if test="${newPoExpediteViewBeanColl != null}">
<script type="text/javascript">
<!--
<c:set var="showUpdateLink" value='N'/>
<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty newPoExpediteViewBeanColl}" >  
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="poExpediteBean" items="${newPoExpediteViewBeanColl}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>

<fmt:formatDate var="fmtOrderDate" value="${status.current.orderDate}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="fmtNeedDate" value="${status.current.needDate}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="fmtPromisedDate" value="${status.current.promisedDate}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="fmtRevisedPromisedDate" value="${status.current.revisedPromisedDate}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="fmtLastRevised" value="${status.current.lastRevised}" pattern="${dateFormatPattern}"/>
<fmt:formatDate var="fmtVendorShipDate" value="${status.current.vendorShipDate}" pattern="${dateFormatPattern}"/>
<c:set var="orderDatSortable" value="${status.current.orderDate.time}"/>
<c:set var="needDateSortable" value="${status.current.needDate.time}"/>
<c:set var="promisedDateSortable" value="${status.current.promisedDate.time}"/>
<c:set var="revisedPromisedDateSortable" value="${status.current.revisedPromisedDate.time}"/>
<c:set var="lastRevisedSortable" value="${status.current.lastRevised.time}"/>
<c:set var="expediteAgeSortable" value="${status.current.expediteAge}"/>
<c:set var="vendorShipDateSortable" value="${status.current.vendorShipDate.time}"/>

<tcmis:jsReplace var="itemDesc" value="${status.current.itemDesc}" processMultiLines="true" />
<tcmis:jsReplace var="expediteComments" value="${status.current.expediteComments}" processMultiLines="true" />  
<tcmis:jsReplace var="shipToAddress" value="${status.current.shipToAddress}" processMultiLines="true" />
<tcmis:jsReplace var="supplierName" value="${status.current.supplierName}" processMultiLines="true" />
<tcmis:jsReplace var="buyerName" value="${status.current.buyerName}" processMultiLines="false" />
<tcmis:jsReplace var="lastRevisedBy" value="${status.current.lastRevisedBy}" processMultiLines="false" />
<tcmis:jsReplace var="partNumber" value="${status.current.partNumber}" processMultiLines="false" />

<c:set var="readonly" value='N'/>
<c:set var="creditHold" value='false'/>
<c:if test="${status.current.creditHold eq 'Y'}">
	<c:set var="creditHold" value='true'/>
</c:if>

<tcmis:inventoryGroupPermission indicator="true" userGroupId="BuyOrder" inventoryGroup="${status.current.inventoryGroup}">
 <c:set var="readonly" value='Y'/>
<c:set var="showUpdateLink" value='Y'/>
</tcmis:inventoryGroupPermission>

/*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},
 data:['${readonly}','${status.current.radianPo}-${status.current.poLine}',
  '${supplierName}','${partNumber}',
  '${status.current.itemId}',  '${itemDesc}','${buyerName}',
  '${status.current.supplyPath}','${fmtOrderDate}',
  '${fmtNeedDate}','${status.current.quantityOpen}',
  '${status.current.mrAlloc}','${status.current.qtyReceived}',   '${status.current.radianPo}-${status.current.poLine}',
  '${status.current.inventoryGroup}', '${fmtVendorShipDate}','${fmtPromisedDate}',
  '',
  '${fmtRevisedPromisedDate}','${expediteComments}',${creditHold},
  '${fmtLastRevised}','${lastRevisedBy}','${status.current.expediteAge} <fmt:message key="label.days"/>' ,
   <c:choose>
    <c:when test="${status.current.daysLate > 0}">
  '${status.current.daysLate} <fmt:message key="label.days"/>' ,
    </c:when>
    <c:otherwise>
     '' ,
    </c:otherwise>
   </c:choose>
  '${status.current.companyId}','${status.current.customerPo}', 
  '${shipToAddress}', '${status.current.carrier}',
  '${status.current.hubName}',
  '${orderDatSortable}','${needDateSortable}','${promisedDateSortable}',
  '${lastRevisedSortable}', '${status.current.radianPo}','${status.current.poLine}',
   '${expediteAgeSortable}','${revisedPromisedDateSortable}','${status.current.daysLate}','${vendorShipDateSortable}', '${fmtRevisedPromisedDate}', '${expediteComments}'
  ]}  
 <c:set var="dataCount" value='${dataCount+1}'/> 
 </c:forEach>
]};
</c:if>
//-->  
</script>
<c:if test="${showUpdateLink == 'Y'}">
    <script type="text/javascript">
        <!--
        showUpdateLinks = true;
        //-->
    </script>
</c:if>
<script type="text/javascript">

var commentType = "txt";
if (!showUpdateLinks)
{  
    commentType = "ro";
}

var config = [
{ columnId:"permission",
 columnName:''
},
{ columnId:"radianPoLine",
  columnName:''
},
{ columnId:"supplierName",
  columnName:''
},
{ columnId:"partNumber",
  columnName:''
},
{ columnId:"itemId",
  columnName:'<fmt:message key="label.itemid"/>',
  width:4
},
{ columnId:"itemDesc",
  columnName:'<fmt:message key="label.itemdescription"/>',
  tooltip:"Y",
  width:15
},
{ columnId:"buyerName",
  columnName:''
},
{ columnId:"supplyPath",
  columnName:''
},
{ columnId:"orderDate",
  columnName:''
},
{ columnId:"needDate",
  columnName:'<fmt:message key="label.needdate"/>',
  sorting:"int",
  width:7,
  hiddenSortingColumn:"hNeedDate"
},
{ columnId:"quantityOpen",
  columnName:''
},
{ columnId:"mrAlloc",
  columnName:''
},
{ columnId:"qtyReceived",
  columnName:''
},
{ columnId:"radianPoDisplay",
  columnName:''
},
{ columnId:"inventoryGroup",
  columnName:''
},
{ columnId:"vendorShipDate",
  columnName:'<fmt:message key="label.promised.ship.date"/>',
  sorting:"int",
  width:7,
  hiddenSortingColumn:"hVendorShipDate"
},
{ columnId:"promisedDate",
  columnName:'<fmt:message key="label.oriprojecteddelivery"/>',
  sorting:"int",
  width:7,
  hiddenSortingColumn:"hPromisedDate"
},
{ columnId:"ok",
  columnName:'<fmt:message key="label.ok"/>',
  type:"hch",
  sorting:"haasHch",
  align:"center",
  width:2,
  submit:true
  
},
{ columnId:"revisedPromisedDate",
  columnName:'<fmt:message key="label.revprojecteddelivery"/>',
  type:"hcal",  
  sorting:"int",        
  width:8,
  hiddenSortingColumn:"hRevisedPromisedDate",
  submit:true
  
},
{ columnId:"comments",
  columnName:"<span title='<fmt:message key="label.maximum2000"/>'> <fmt:message key="label.comments"/></span>"  ,
  width:20,
  type:commentType,
  tooltip:"Y",
  submit:true
},
{ columnId:"creditHold",
	  columnName:"<fmt:message key="label.credithold"/>",
	  width:4,
	  type:"hchstatus",
	  submit:true
},
{ columnId:"lastRevised",
  columnName:'<fmt:message key="label.laastUpdatedDate"/>',
  sorting:"int",
  hiddenSortingColumn:"hLastRevised"
},
{ columnId:"lastRevisedBy",
  columnName:'<fmt:message key="label.lastUpdatedBy"/>'
},
{ columnId:"expediteAge",
  columnName:'<fmt:message key="label.expediteage"/>',
  sorting:"int",
  hiddenSortingColumn:"hExpediteAge"
},
{ columnId:"daysLate",
  columnName:'<fmt:message key="label.daysLate"/>'
},
{ columnId:"companyId",
  columnName:'<fmt:message key="label.company"/>'
},
{ columnId:"customerPo",
  columnName:'<fmt:message key="label.customerpo"/>'
},
{ columnId:"shipToAddress",
  columnName:''
},
{ columnId:"carrier",
  columnName:''
},
{ columnId:"hubName",
  columnName:''
},
{ columnId:"hOrderDate",
  sorting:"int"
},
{ columnId:"hNeedDate",
  sorting:"int"
},
{ columnId:"hPromisedDate",
  sorting:"int"
},
{ columnId:"hLastRevised",
  sorting:"int"
},
{ columnId:"radianPo",
  sorting:"int",
  submit:true
},
{ columnId:"poLine",
  sorting:"int",
  submit:true
},
{ columnId:"hExpediteAge",
  sorting:"int"
},
{ columnId:"hRevisedPromisedDate",
  sorting:"int"
},
{ columnId:"daysLate",
  sorting:"int"
},
{ columnId:"hVendorShipDate",
  sorting:"int"
},
{
 columnId:"oldRevisedDate",
 submit:true	
},
{columnId:"oldComments",
submit:true
	
}
];
// -->
</script>

        <!-- If the collection is empty say no data found -->
        <c:if test="${empty newPoExpediteViewBeanColl}">
            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
                <tr>
                    <td width="100%">
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
</td>
</tr>
</table>
<!-- results end -->
</div>
</div>
<!-- Result Frame Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
<%--Store the search parameters.--%>
<input name="action" id="action" value="" type="hidden"/>
<input name="creditHold" id="creditHold" value="${param.creditHold}" type="hidden"/>
<input name="radianPo" id="radianPo" value="${param.radianPo}" type="hidden"/>
<input name="poLine" id="poLine" value="${param.poLine}" type="hidden"/>
<!--This sets the start time for time elapesed from the action.-->
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}">
<!--This sets the end time for time elapesed from the action.-->
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}">
<input name="minHeight" id="minHeight" type="hidden" value="100">
<input type="hidden" name="blockBefore_revisedPromisedDate" id="blockBefore_revisedPromisedDateDisplay" value=""/>
<input type="hidden" name="blockAfter_revisedPromisedDate" id="blockAfter_revisedPromisedDate" value=""/>
<input type="hidden" name="blockBeforeExclude_revisedPromisedDate" id="blockBeforeExclude_revisedPromisedDate" value=""/>
<input type="hidden" name="blockAfterExclude_revisedPromisedDate" id="blockAfterExclude_revisedPromisedDate" value=""/>
<input type="hidden" name="inDefinite_revisedPromisedDate" id="inDefinite_revisedPromisedDate" value=""/>
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

<!-- If the user has permissions and needs to see the update links,set the variable showUpdateLinks javascript to true.
     The default value of showUpdateLinks is false.
-->

</tcmis:form>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>

</body>
</html:html>