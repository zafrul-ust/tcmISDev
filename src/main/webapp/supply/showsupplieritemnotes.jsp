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
<!-- Add any other stylesheets you need for the page here -->

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

<!-- For Calendar support for column type hcal-->
<!--
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
-->

<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>
<script type="text/javascript" src="/js/supply/showsupplieritemnotes.js"></script>

<!-- These are for the Grid-->
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
<fmt:message key="supplieritemnotes.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
recordFound:"<fmt:message key="label.recordFound"/>",searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
errors:"<fmt:message key="label.errors"/>",itemInteger:"<fmt:message key="error.item.integer"/>"};
// -->

var gridConfig = {
		divName:'supItemNotesBean', // the div id to contain the grid.
		beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
		beanGrid:'beanGrid',     // the grid's name, as in beanGrid.attachEvent...
		config:'config',	     // the column config var name, as in var config = { [ columnId:..,columnName...
		rowSpan:false,			 // this page has rowSpan > 1 or not.
		submitDefault:true,    // the fields in grid are defaulted to be submitted or not.
		onRowSelect:doOnRowSelected,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
	    onRightClick:selectRightclick,   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
        onBeforeSorting:doOnBeforeSelect // the onBeforeSorting event function to be attached, as in beanGrid.attachEvent("onBeforeSorting",selectRow));
	};

with(milonic=new menuname("rightClickMenu")){
    top="offset=2"
    style = contextStyle;
    margin=3
    	aI("text=<fmt:message key="label.addNewComment"/>;url=javascript:addNewComment();");
}

drawMenus();

var config = [
{
  columnId:"permission"
},  
{ columnId:"supplierName",
  columnName:'<fmt:message key="label.supplier"/>',
  tooltip:true,
  width:15
},
{
  columnId:"opsEntityName",
  columnName:'<fmt:message key="label.operatingentity"/>',
  width:10
},
{
  columnId:"opsEntityId"
},
{
  columnId:"transactionDate",
  columnName:'<fmt:message key="label.date"/>',
   hiddenSortingColumn:'hiddenTransactionDateTime',
  sorting:'int'
},
{ 
  columnId:"hiddenTransactionDateTime",
  sorting:'int'
},
{ columnId:"enteredByName",
  columnName:'<fmt:message key="label.enteredby"/>',
  sorting:"haasStr",
  tooltip:true,
  width:10
},
{
  columnId:"comments",
  columnName:'<fmt:message key="label.comments"/>',  
  tooltip:true,
  width:20
},
{ 
  columnId:"itemId",
  columnName:'<fmt:message key="label.itemid"/>',
  width:6
},
{ 
  columnId:"itemDesc",
  columnName:'<fmt:message key="label.itemdesc"/>',
  tooltip:true,
  width:20
},
{ 
  columnId:"supplier"
}
,
{ 
  columnId:"opsEntityId"
},
{ 
  columnId:"recordNo"
}
];

//-->
</script>
</head>

<body bgcolor="#ffffff" onload="popupOnLoadWithGrid(gridConfig);checkUpdatePermission()" onresize="resizeFrames()">
<tcmis:form action="/showsupplieritemnotes.do" onsubmit="return submitFrameOnlyOnce();">

<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/>
    ${tcmISError}
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
    </c:forEach>        
</div>

<tcmis:opsEntityPermission indicator="true" userGroupId="supplierItemNotes" opsEntityId="${param.opsEntityId}">
    <script type="text/javascript">
        <!--
        showUpdateLinks = true;
        //-->
    </script>
 </tcmis:opsEntityPermission> 

<script type="text/javascript">
   <c:choose>
    <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISErrors }">
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
<div id="resultFrameDiv" style="margin: 4px 4px 0px 4px;" >
<!-- Transit Page Begins -->
	<div id="transitPage" style="display: none;" class="optionTitleBoldCenter">
	  <br><br><br><fmt:message key="label.pleasewait"/>
	  <br><br><br><img src="/images/rel_interstitial_loading.gif" align="middle">
	</div>
<!-- Transit Page Ends -->
<div id="resultGridDiv" style="display:none">
<!-- results start -->
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
          <div id="mainUpdateLinks" style="display: none"> <%-- mainUpdateLinks Begins --%>
              <span id="addItemSpan" style="display: none"><a href="#" onclick="addNewComment('NewSupplierOK');"><fmt:message key="label.addNewComment"/></a></span>
       </div> <%-- mainUpdateLinks Ends --%>
    
     </div> <%-- boxhead Ends --%>

  <div class="dataContent">
	<div id="supItemNotesBean" ></div>
	<c:set var="dataCount" value='${0}'/>
	<c:if test="${supItemNotesCol != null}">
	<script type="text/javascript">
		<c:if test="${!empty supItemNotesCol}">
		var jsonMainData = new Array();
		var jsonMainData = {
		rows:[
		<c:forEach var="supItemNotesBean" items="${supItemNotesCol}" varStatus="status">
		<c:if test="${status.index > 0}">,</c:if>
		<c:set var="readonly" value='N'/>
        <tcmis:opsEntityPermission indicator="true" userGroupId="supplierItemNotes" opsEntityId="${supItemNotesBean.opsEntityId}">
            <c:set var="readonly" value='Y'/>
        </tcmis:opsEntityPermission>
		/* Get time for hidden date column. This is for sorting purpose. */
		<c:set var="transactionDateTime" value="${status.current.transactionDate.time}"/>
		
		{id:${status.index +1},
		 data:['${readonly}',
		  '<tcmis:jsReplace value="${supItemNotesBean.supplierName}" />',
		  '${supItemNotesBean.operatingEntityName}','${supItemNotesBean.opsEntityId}',
		  '<fmt:formatDate value="${supItemNotesBean.transactionDate}" pattern="${dateFormatPattern}"/>', 
		  '${transactionDateTime}',
		  '<tcmis:jsReplace value="${supItemNotesBean.enteredByName}" />',
		  '<tcmis:jsReplace value="${supItemNotesBean.comments}" processMultiLines="true"/>',
		  '${supItemNotesBean.itemId}',
		  '<tcmis:jsReplace value="${supItemNotesBean.itemDesc}" processMultiLines="true"/>',
		  '${supItemNotesBean.supplier}',
		  '${supItemNotesBean.opsEntityId}',
		  '${supItemNotesBean.recordNo}']}
		  
		 <c:set var="dataCount" value='${dataCount+1}'/> 
		 </c:forEach>
		]};
		</c:if>
	</script>
	</c:if>
	<!-- If the collection is empty say no data found -->
	<c:if test="${empty supItemNotesCol}" >
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
	 <tr>
	  <td width="100%">
	   <fmt:message key="main.nodatafound"/>
	  </td>
	 </tr>
	</table>
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
<!-- results end -->
</div>
</div>
<!-- Result Frame Ends -->

<!-- Hidden element start -->
  <div id="hiddenElements" style="display: none;">
		<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden"/>
		<input type="hidden" name="uAction" id="uAction" value="${param.uAction}"/>
		<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}"/>
        <input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>
        <input name="minHeight" id="minHeight" type="hidden" value="100"/>
		<input name="opsEntityId" id="opsEntityId" value="${param.opsEntityId}" type="hidden"/>
		<input name="itemId" id="itemId" value="${param.itemId}" type="hidden"/>
		<input name="itemDesc" id="itemDesc" value="<c:out value="${param.itemDesc}"/>" type="hidden"/>
		<input name="searchField" id="searchField" value="${param.searchField}" type="hidden"/>
		<input name="searchMode" id="searchMode" value="${param.searchMode}" type="hidden"/>
		<input name="searchArgument" id="searchArgument" value="${param.searchArgument}" type="hidden"/>
		<input name="supplier" id="supplier" value="${param.supplier}" type="hidden"/>
		<input name="supplierName" id="supplierName" value="<c:out value="${param.supplierName}"/>" type="hidden"/>
  </div>

</tcmis:form>
</body>
</html:html>