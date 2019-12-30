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
<fmt:message key="label.hubpermsfor">
	<fmt:param>(${param.fullName})</fmt:param>
</fmt:message>
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

function checkAllAccess() {
var lines = $v('totalLines');
	if( $('chc1').checked ) {
		for(i=1;i<=lines;i++) {
			var cid = 'hubAccess'+i;
			if( ! $(cid).disabled && !$(cid).checked) {
				$(cid).checked = true;
				updateHchStatusA(cid);
			}
		}
	}
	else {
		for(i=1;i<=lines;i++) {
			var cid = 'hubAccess'+i;
			if( !$(cid).disabled && $(cid).checked) {
				$(cid).checked = false;
				updateHchStatusA(cid);
			}
		}
	}
}

function checkAllAdmin() {
	var lines = $v('totalLines');
	if( $('chc2').checked ) {
		for(i=1;i<=lines;i++) {
			var cid = 'adminRole'+i;
			if( ! $(cid).disabled && !$(cid).checked) {
				$(cid).checked = true;
				updateHchStatusA(cid);
			}
		}
	}
	else {
		for(i=1;i<=lines;i++) {
			var cid = 'adminRole'+i;
			if( !$(cid).disabled && $(cid).checked) {
				$(cid).checked = false;
				updateHchStatusA(cid);
			}
		}
	}
}

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
   columnId:"hubAccessPermission"
//   ,columnName:'accessPermission'
  },
  {
   columnId:"adminRolePermission"
//   ,columnName:'adminPermission'
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
    columnId:"hubName",
    columnName:'<fmt:message key="label.hub"/>',
    type:"ro",
    width:20
// width is the header width, default 8. or if header is empty, 0.
  },
  {
    columnId:"hubAccess",
    columnName:'<fmt:message key="label.access"/><br/><input type="checkbox" name="chc1" id="chc1" onclick="checkAllAccess()"/>',
// align value can be 'left', 'center' or 'right', if not specified, using default which is 'left' unless sorting is 'int' => 'right'
    align:'center',
    type:"hchstatus",
    permission:true
  },
  { 
// for hed type , there are two global variable inputSize and maxInputLength ( see hed doc. )
// size and maxlength are the values in those two array respectively. If not set, 
// default size is width or 8 if width not set.
// default maxlength is 0
   columnId:"adminRole",
   columnName:'<fmt:message key="label.admin"/><br/><input type="checkbox" name="chc2" id="chc2" onclick="checkAllAdmin()"/>',
   align:'center',
   type:"hchstatus",
   permission:true
  },
  {
   columnId:"hub"
  },
  {
   columnId:"oldHubAccess"
  },
  {
   columnId:"oldAdminRole"
  },
  {
   columnId:"userAccess"
  },
  {
   columnId:"companyId"
  }
]; 

_gridConfig.submitDefault = true;
_gridConfig.divName = 'UserHubAdmin';

var updateonly = true;
function refreshopener() {
	try {
		if($v("updated")=='Y' && updateonly && opener.refreshme ) {
			opener.refreshme();
		}
	}catch(ex){}
}

// -->
</script>
</head>

<body bgcolor="#ffffff"  onload="popupOnLoadWithGrid();" onresize="resizeFrames()" onunload="refreshopener();">

<tcmis:form action="/hubpermission.do" onsubmit="return submitFrameOnlyOnce();">

<div class="interface" id="mainPage" style="">

<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
  ${tcmISError}<br/>
  <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
  </c:forEach>
</div>
<!-- Error Messages Ends -->

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
      <span id="updateResultLink" style="display:none"><a href="#" onclick="javascript:updateonly=false;_simpleUpdate(); return false;"><fmt:message key="label.update"/></a></span>  
     </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

<div class="dataContent">
<!--  result page section start -->
<div id="UserHubAdmin" style="height:400px;display: none;" ></div>
<c:if test="${beanColl != null}" >
<script type="text/javascript">
<!--

<%--NEW - storing the data to be displayed in the grid in a JSON.--%>
<c:set var="adminCount" value='0'/>
<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty beanColl}" >
var jsonData = {
        rows:[
<c:forEach var="bean" items="${beanColl}" varStatus="status">
        <c:if test="${status.index > 0}">,</c:if>
	<c:set var="accessPermission" value="N"/>       
	<c:set var="adminPermission" value="N"/>

	<c:if test="${status.current.userAccess == 'Admin'}">
		<c:set var="accessPermission" value="Y"/>       
 	<c:set var="adminCount" value='1'/>
	</c:if>
	<c:if test="${status.current.userAccess == 'Grant Admin'}">
		<c:set var="accessPermission" value="Y"/>       
		<c:set var="adminPermission" value="Y"/>
 	<c:set var="adminCount" value='${1}'/>
	</c:if>

	<c:set var="hubAccess" value="false"/>       
	<c:set var="adminRole" value="false"/>
	
	<c:if test="${status.current.hubAccess == 'Y'}">
		<c:set var="hubAccess" value="true"/>       
	</c:if>

	<c:if test="${status.current.adminRole == 'Admin' || status.current.adminRole == 'Grant Admin'}">
		<c:set var="adminRole" value="true"/>       
	</c:if>
	
        /*The row ID needs to start with 1 per their design.*/
        { id:${status.index +1},
         data:[
 'Y',
 '${accessPermission}',
 '${adminPermission}',
 '${bean.hubName}',
  ${hubAccess},
  ${adminRole},
 '${bean.hub}',
 '${bean.hubAccess}',
 '${bean.adminRole}',
 '${bean.userAccess}',
 '${bean.companyId}'
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
<input type="hidden" name="personnelId" id="personnelId" value="${param.personnelId}"/>
<input type="hidden" name="companyId" id="companyId" value="${param.companyId}"/>
<input type="hidden" name="fullName" id="fullName" value="${param.fullName}"/>
<!--This sets the start time for time elapesed.-->
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}">
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}">
<input name="minHeight" id="minHeight" type="hidden" value="100"/>
<input name="updated" id="updated" value="${updated}" type="hidden"/>	

</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

</tcmis:form>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>

<c:if test="${adminCount == '1'}">
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
 //-->
 </script>
</c:if>

</body>
</html>