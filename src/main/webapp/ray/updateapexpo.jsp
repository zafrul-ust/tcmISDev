<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="expires" content="-1">
<link rel="shortcut icon" href="/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>
<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<%-- Add any other stylesheets you need for the page here --%>

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handles all the resizing of the page and frames -->
<%--NEW--%>
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
<script type="text/javascript" src="/js/ray/updateapexpo.js"></script>

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
<fmt:message key="label.update"/> <fmt:message key="label.po"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
errors:"<fmt:message key="label.errors"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>"
};

var config = [
  {
/* columnId: to set columIds. this will be the id you want your dynabean to be,
*            this is the same as setting id attribute on an input element.
*  columnName: The correspondent header. If you have columnName defined as '', 
*			 or columnName is not defined, the column becomes hidden.
*            In this case, the width will automatically become 0, cannot be overwritten.
*/
  	columnId:"permission",
  	columnName:''
  },
  {
/* 
** type is used to set column types, default is 'ro'.
** you can define editable columns here. More documentation is availabe on Nimbus
** N:\Tech Center\dhtmlxGrid_Customized_Docs
** ro -read only
** hed -editable sinlge line text
** txt - Multiline edit text (mostly for comments and user input)
** date -hcal
** hcoro -select drop down
** hlink - link
** hch -checkbox
*** For txt type, if the user has no permissions to update the text, change the column type to ro.
*/ 
    columnId:"ok",
    columnName:'<fmt:message key="label.ok"/>',
    type:"hch",
// width is the header width, default 8. or if header is empty, 0.
    width:4
  },
  {
    columnId:"mrLine",
    columnName:'<fmt:message key="label.mrline"/>',
// align value can be 'left', 'center' or 'right', if not specified, using default which is 'left' unless sorting is 'int' => 'right'
    align:'center'
  },
  { 
// for hed type , there are two global variable inputSize and maxInputLength ( see hed doc. )
// size and maxlength are the values in those two array respectively. If not set, 
// default size is width or 8 if width not set.
// default maxlength is 0
   columnId:"poNumber",
   columnName:'<fmt:message key="label.customerpo"/>',
   type:"hed",
   width:12,
   size:15,
   maxlength:30,
// submit this field when grid create inputs, doesn't matter what's the default submit setting is.
// all submit setting fields setting will be set to the submitDefault value in initGridWithConfig 
// unless overwritten by this value.
   submit:true
  },
  {
   columnId:"facilityId",
   columnName:'<fmt:message key="label.facility"/>'
  },
  {
  	columnId:"application",
  	columnName:'<fmt:message key="label.workarea"/>'
  },
  {
   columnId:"facPartNo",
   columnName:'<fmt:message key="label.partnum"/>'
  },
  {
   columnId:"quantity",
   columnName:'<fmt:message key="label.qty"/>',
// sorting method. Mostly we use haasStr but you can overwrite here unless your type is
// hcal,hch,hchstatus or hcoro
   sorting:'int'
  },
  {
  	columnId:"shipToLocationId",
  	columnName:'<fmt:message key="label.shipto"/>'
  },
  {
// has tooltip, true, "true" or "Y", 
// also if your width is >=20, it will have tooltip unless specifically overwritten
   columnId:"itemDesc",
   columnName:'<fmt:message key="label.itemdesc"/>',
   width:16,
   tooltip:"Y"
  },
  {  
   columnId:"packaging",
   columnName:'<fmt:message key="label.packaging"/>',
   width:16,
   tooltip:"Y"
  },
  {  
// no tooltip, false, "false", "N", '', null or do not define[and width<20 ]
  	columnId:"requestLineStatus",
  	columnName:'<fmt:message key="label.status"/>',
	tooltip:"N"
  },
  { 
/* 
** If you do not define a columnName the column becomes hidden.
** Or if you have columnName defined as '', the column becomes hidden.
** Most of the hidden columns are at the end, except permission.
*/
  	columnId:"releaseNumber"
  },
  {
    columnId:"prNumber"
  },
  {
    columnId:"lineItem"
  }
]; 
// -->
</script>
</head>

<body bgcolor="#ffffff"  onload="gridPopupOnLoad();" onresize="resizeFrames()">

<tcmis:form action="/updateapexpo.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="mainPage" style="">

<!-- Error Messages Begins -->
<c:set var="tcmISError" value=''/>
<div id="errorMessagesAreaBody" style="display:none;">
<c:if test="${! empty tcmISErrors }" >
  <c:forEach var="err" items="${tcmISErrors}" varStatus="status">
	<c:set var="tcmISError" value='hasError'/>
    ${err} <br>
  </c:forEach>
</c:if>
</div>
<!-- Error Messages Ends -->

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${ empty tcmISError }">
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
<!-- If you want your results section to span only 50% set this to 50% and your main table will be 100% -->
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
      <div id="mainUpdateLinks" style="display:none"> <%-- mainUpdateLinks Begins --%>
      <span id="updateResultLink" style="display:none"><a href="#" onclick="javascript:updatePO(); return false;"><fmt:message key="label.update"/></a></span>  
     </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

<div class="dataContent">
<!--  result page section start -->
<div id="beanCollDiv" style="height:400px;display: none;" ></div>
<c:if test="${beanColl != null}" >
<script type="text/javascript">
<!--

<%--NEW - storing the data to be displayed in the grid in a JSON.--%>
<c:set var="applicationPermissionAll" value="N"/>
<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty beanColl}" >
var beanData = {
        rows:[
<c:forEach var="bean" items="${beanColl}" varStatus="status">
        <c:if test="${status.index > 0}">,</c:if>
<c:set var="applicationPermission" value="N"/>
<tcmis:applicationPermission indicator="true" userGroupId="GenerateOrders" facilityId="${bean.facilityId}" application="${bean.application}">
	  	<c:set var="applicationPermission" value="Y"/>
	  	<c:set var="applicationPermissionAll" value="Y"/>
</tcmis:applicationPermission>
        /*The row ID needs to start with 1 per their design.*/
        { id:${status.index +1},
         data:[
 '${applicationPermission}',
 false,
 '${bean.prNumber}-${bean.lineItem}',
 '${bean.poNumber}',
 '${bean.facilityId}',
 '<tcmis:jsReplace value="${bean.application}"/>',
 '${bean.facPartNo}',
 '${bean.quantity}',
 '${bean.shipToLocationId}',
 '<tcmis:jsReplace value="${bean.itemDesc}" processMultiLines="true"/>',
 '<tcmis:jsReplace value="${bean.packaging}" processMultiLines="true"/>',
 '${bean.requestLineStatus}',
 '${bean.releaseNumber}',
 '${bean.prNumber}',
 '${bean.lineItem}'
 ]}
    <c:set var="dataCount" value='${dataCount+1}'/>
     </c:forEach>
    ]};
</c:if>
// -->
</script>

<!-- If the collection is empty say no data found -->
<c:if test="${empty beanColl}" >
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
<input name="uAction" id="uAction" value="" type="hidden"/>
<input name="prNumber" id="prNumber" value="${param.prNumber}" type="hidden"/>
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

<c:if test="${applicationPermissionAll == 'Y'}">
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
 //-->
 </script>
</c:if>

</body>
</html>