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
<tcmis:gridFontSizeCss overflow="notHidden"/>
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
<script type="text/javascript" src="/js/client/catalog/editname.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<%--NEW - dhtmlX grid files--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>
    
<title>
<fmt:message key="label.editname"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
selectedaddress:"<fmt:message key="label.selectedaddress"/>",
errors:"<fmt:message key="label.errors"/>",  
waitingforinputfrom:"<fmt:message key="label.waitingforinputfrom"/>",
newshiptoaddress:"<fmt:message key="newshiptoaddress.title"/>",
name:"<fmt:message key="label.name"/>",
description:"<fmt:message key="label.description"/>",
maximum200:"<fmt:message key="label.maximum200"/>",
itemInteger:"<fmt:message key="error.item.integer"/>"};
// -->
</script>
    <script LANGUAGE="JavaScript" TYPE="text/javascript">
<!--

var config = [
{
  	columnId:"permission"
},
{
  	columnId:"commentIdPermission"
},
{
    columnId:"commentId",
    columnName:'<fmt:message key="label.name"/>',
    tooltip:true,
    type:'hed',    // input field
  	align:'left',
  	width:12, // the width of this cell
  	size:11,  // the size of the input field
    maxlength:50 // the maxlength of the input field 
},
{
    columnId:"commentTxt",
    columnName:'<fmt:message key="label.description"/>',
  	type:'txt',   
  	tooltip:"Y",
  	width:40
},
{
  	columnId:"originalCommentTxt"
},
{
  	columnId:"status"
}
];

if('${updated}' =='Y') {
	var typeIdfromPopup = new Array(
		<c:forEach var="typeBean" items="${typeNameColl}" varStatus="status">
			<c:if test="${status.index > 0}">,</c:if>
			{text:'<tcmis:jsReplace value="${status.current.typeName}"/>',value:'<tcmis:jsReplace value="${status.current.typeId}"/>'}
		</c:forEach>
	);
	
	altNameIdfromPopup = new Array();
	<c:forEach var="vvCountryBean" items="${typeNameColl}" varStatus="status">
	  <c:set var="currentType" value='${status.current.typeId}'/>
	  <c:set var="commentList" value='${status.current.commentList}'/>
	
	  altNameIdfromPopup["${currentType}"] = new Array(
	  <c:forEach var="commentBean" items="${commentList}" varStatus="status1">
	 <c:choose>
	   <c:when test="${status1.index > 0}">
	        ,'<tcmis:jsReplace value="${status1.current.commentId}"/>'
	   </c:when>
	   <c:otherwise>
		   '<tcmis:jsReplace value="${status1.current.commentId}"/>'
	   </c:otherwise>
	  </c:choose>
	  </c:forEach>
	  );
	</c:forEach>
	
	altNamefromPopup = new Array();
	<c:forEach var="vvCountryBean" items="${typeNameColl}" varStatus="status">
	  <c:set var="currentType" value='${status.current.typeId}'/>
	  <c:set var="commentList" value='${status.current.commentList}'/>
	
	altNamefromPopup["${currentType}"] = new Array(
	  <c:forEach var="commentBean" items="${commentList}" varStatus="status2">
	 <c:choose>
	   <c:when test="${status2.index > 0}">
	        ,'<tcmis:jsReplace value="${status2.current.commentTxt}" processMultiLines="true"/>'
	   </c:when>
	   <c:otherwise>
		   '<tcmis:jsReplace value="${status2.current.commentTxt}" processMultiLines="true"/>'
	   </c:otherwise>
	  </c:choose>
	  </c:forEach>
	  );
	</c:forEach>
	
  opener.rebuildAllArray(typeIdfromPopup,altNameIdfromPopup,altNamefromPopup);

  for(var i=0;i<opener.beanGrid.getRowsNum();i++){
	commentRowid = opener.beanGrid.getRowId(i);

	opener.typeChanged(commentRowid,'typeId','Y');
  } 
}


// -->
    </script>
</head>

<body bgcolor="#ffffff"  onload="resultOnLoad();" onunload="try{opener.closeTransitWin();}catch(ex){}" onresize="resizeFrames()">

<tcmis:form action="/editname.do" onsubmit="return submitFrameOnlyOnce();">

<c:set var="adminPermission" value="N"/>
<tcmis:facilityPermission indicator="true" userGroupId="secLblInfoAdmin" facilityId="${param.facilityId}">
  <c:set var="adminPermission" value="Y"/>
</tcmis:facilityPermission>

<div class="interface" id="mainPage" style="">

<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
 <html:errors/>
 <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
 </c:forEach> 
</div>
<!-- Error Messages Ends -->

<script type="text/javascript">
<!--
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
<c:choose>
   <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISErrors}">
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

<div id="transitDailogWin" class="errorMessages" style="display:none;left:20%;top:20%;z-index:5;">
</div>

<div id="transitDailogWinBody" class="errorMessages" style="display: none;">
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


<div id="resultGridDiv" style="display:;">
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
      --%>
      <div id="mainUpdateLinks"> 
<c:if test="${adminPermission == 'Y'}" >
         <span id="deleteSpan" style="display:none;"><a href="#" onclick="deleteLine();"><fmt:message key="label.delete"/></a> |</span>
         <a href="#" onclick="addLine();"><fmt:message key="label.add"/></a> |
         <a href="#" onclick="update();"><fmt:message key="label.update"/></a>
</c:if>         
      </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

<div class="dataContent">
 <!--  result page section start -->
<div id="commentBean" style="height:400px;display: ;" ></div>

<%--NEW - storing the data to be displayed in the grid in a JSON.--%>
<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty commentTxtColl.commentList}" >
    <script type="text/javascript">
    <!--
        var jsonMainData = new Array();
        var jsonMainData = {
        rows:[
        <c:forEach var="commentBean" items="${commentTxtColl.commentList}" varStatus="status">
   
        <c:if test="${status.index > 0}">,</c:if>
        { id:${status.index +1},
         data:[
         '${adminPermission}','N',
         '${commentBean.commentId}','<tcmis:jsReplace value="${commentBean.commentTxt}" processMultiLines="true"/>','<tcmis:jsReplace value="${commentBean.commentTxt}" processMultiLines="true"/>','']}

        <c:set var="dataCount" value='${dataCount+1}'/>
         </c:forEach>
        ]};
    // -->
    </script>
</c:if>
<!-- Search results end -->
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
<input name="totalLines" id="totalLines" value="1" type="hidden"/>
<input name="uAction" id="uAction" value="${param.uAction}" type="hidden"/>
<input name="facilityId" id="facilityId" value="${param.facilityId}" type="hidden"/>
<input name="typeId" id="typeId" value="${param.typeId}" type="hidden"/>
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