<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>

<html:html lang="true">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="expires" content="-1"/>
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss overflow="notHidden"/>
<%-- Add any other stylesheets you need for the page here --%>
<!-- Add any other stylesheets you need for the page here -->
<link rel="stylesheet" type="text/css" href="/css/tabs.css"/>  

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

<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
<script type="text/javascript" src="/js/calendar/calendarval.js"></script>


<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/common/noSearchStandardGridmain.js"></script>


<!-- These are for the Grid-->
	<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>


<script src="/js/jquery/jquery-1.6.4.js"></script>


<title><fmt:message key="label.mfrnotificationcategories" /></title>

<script language="JavaScript" type="text/javascript">
<!--

var messagesData={}

var sampleRay = [
<c:forEach var="category" items="${categories}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>
"<c:out value="${category.mfrReqCategoryDesc}"/>"
</c:forEach>
];

function mapArray(arr, mappingFn) {
	var newArr = [];
	for (i in arr) {
		newArr[i] = mappingFn(arr[i]);
	}
	return newArr;
}

function returnCategories() {
	var checkedBoxes = j$(":checked").get();
	opener.categoriesChanged(
			mapArray(checkedBoxes, function(element) { return element.value; }),
			mapArray(checkedBoxes, function(element) { return element.name; }));
	window.close();
}

function cancel() {
	window.close();
}

//-->
</script>
</head>
<body>
<tcmis:form action="/mfrnotificationmgmtmain.do" onsubmit="return submitFrameOnlyOnce();">

<!-- Search Option Begins -->
<div class="roundcont filterContainer">
 <div class="roundright">
   <div class="roundtop">
     <div class="roundtopright"> <img src="/images/rndBoxes/borderTL_filter.gif" alt="" width="15" height="10" class="corner_filter" style="display: none" /></div>
   </div>
   <div class="roundContent">
     <div class="boxhead"> <%-- boxhead Begins --%>
     <%-- Place all update links in divs that can be turned on or off depending on the search criteria that is selected
          Do not remove mainUpdateLinks div, even if you don't have any links. You can remove content inside the div but not the div itself.
          Please keep the <span></span> on the same line this will avoid extra virtical space. 
      --%>
      <div id="mainUpdateLinks"> <%-- mainUpdateLinks Begins --%>
        <span id="returnLink"><a href="#" onclick="returnCategories(); return false;"><fmt:message key="label.return"/></a></span> | 
        <span id="cancelLink"><a href="#" onclick="cancel(); return false;"><fmt:message key="label.cancel"/></a></span>
     </div> <%-- mainUpdateLinks Ends --%>
     </div>
     <div class="dataContent"> 
	    <table style="width:96%;">
	    	<tr>
	   		<c:forEach var="category" items="${categories}" varStatus="status">
	    	<%--<c:if test="${status.index gt 0 && status.index % 3 eq 0}">--%>
	    	<c:if test="${status.index % 3 eq 0}">
	    	</tr>
	    	<tr>
	    	</c:if>
	    	<td style="width:33%" nowrap>
	    		<label for="category${status.count}">
	    			<input type="checkbox" id="category${status.count}" name="${category.mfrReqCategoryDesc}" ${category.selected?'checked':''} value="${category.mfrReqCategoryId}"/>
	    			<c:out value="${category.mfrReqCategoryDesc}"/>
	    		</label>
	    	</td>
	    	<c:set var="catId" value="category${category.mfrReqCategoryId}"/>
	    	</c:forEach>
	    	</tr>
	     </table>
     </div>
   </div>
   <div class="roundbottom">
     <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
   </div>
 </div>
</div>
<!-- Search Option Ends -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
	<input name="uAction" id="uAction" type="hidden" value=""/>
	<input name="mfrNotIndex" id="mfrNotIndex" type="hidden" value="0"/>
</div>
<!-- Hidden elements end -->

</tcmis:form>
</body>
</html:html>