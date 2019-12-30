<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script type="text/javascript" src="/js/common/disableKeys.js"></script>

<!-- This handels the menu style and what happens to the right click on the whole page -->
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>

<script src="/js/calendar/newcalendar.js" language="JavaScript"></script>
<script src="/js/calendar/AnchorPosition.js" language="JavaScript"></script>
<script src="/js/calendar/PopupWindow.js" language="JavaScript"></script>

<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
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
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<script type="text/javascript" src="/js/client/catalog/secondarylabelinformation.js"></script>
<script type="text/javascript" src="/js/common/taxcodes.js"></script>

<script language="JavaScript" type="text/javascript">
<!--
windowCloseOnEsc = true;
var typeId = new Array(
	<c:if test="${empty typeNameColl}">{text:'<fmt:message key="label.pleasecreatenewtype"/>',value:''}</c:if>
	<c:if test="${!empty typeNameColl}">{text:'<fmt:message key="label.pleaseselect"/>',value:''}</c:if>
	<c:forEach var="typeBean" items="${typeNameColl}" varStatus="status">
		,{text:'<tcmis:jsReplace value="${status.current.typeName}"/>',value:'<tcmis:jsReplace value="${status.current.typeId}"/>'}
	</c:forEach>
);

var commentId = new Array();

var altNameId = new Array();
<c:forEach var="vvCountryBean" items="${typeNameColl}" varStatus="status">
  <c:set var="currentType" value='${status.current.typeId}'/>
  <c:set var="commentList" value='${status.current.commentList}'/>

  altNameId["${currentType}"] = new Array(
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

var altName = new Array();
<c:forEach var="vvCountryBean" items="${typeNameColl}" varStatus="status">
  <c:set var="currentType" value='${status.current.typeId}'/>
  <c:set var="commentList" value='${status.current.commentList}'/>

  altName["${currentType}"] = new Array(
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

with(milonic=new menuname("editTypeMenu")){
    top="offset=2"
    style = contextStyle;
    margin=3
    	aI("text=<fmt:message key="label.edittype"/>;url=javascript:editType();");
}

with(milonic=new menuname("editNameMenu")){
    top="offset=2"
    style = contextStyle;
    margin=3
    	aI("text=<fmt:message key="label.editname"/>;url=javascript:editName();");
}

drawMenus();


// -->
</script>


<title>
<fmt:message key="label.secondarylabelinformationformaterialid"/> ${param.materialId}
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
pleaseselect:"<fmt:message key="label.pleaseselect"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
type:"<fmt:message key="label.type"/>",
name:"<fmt:message key="label.name"/>",
waitingforinputfrom:"<fmt:message key="label.waitingforinputfrom"/>",
choosetypeb4editname:"<fmt:message key="label.choosetypeb4editname"/>",
pleasecreatenewtype:"<fmt:message key="label.pleasecreatenewtype"/>",
pleasecreatenewname:"<fmt:message key="label.pleasecreatenewname"/>",
edittype:"<fmt:message key="label.edittype"/>",
editname:"<fmt:message key="label.editname"/>",
maximum200:"<fmt:message key="label.maximum200"/>"
};


// -->
</script>
</head>

<body bgcolor="#ffffff" onload="myInit();" onunload="try{opener.parent.closeTransitWin();}catch(ex){}" onresize="myResize()">

<tcmis:form action="/secondarylabelinformation.do" onsubmit="return submitOnlyOnce();">

<div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
  <br><br><br><fmt:message key="label.pleasewait"/>
</div>
 
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

<div class="interface" id="resultGridDiv">
<div class="contentArea">
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages">
<html:errors/>
<c:if test="${requestScope['org.apache.struts.action.ERROR'] != null || ! empty tcmISErrors or !empty tcmISError}" >
  <html:errors/><br>
  <c:forEach var="err" items="${tcmISErrors}" varStatus="status">
    ${err} <br>
  </c:forEach>
  ${tcmISError}
</c:if>    
</div>
<!-- Error Messages Ends -->
<%--
<c:if test="${customerAddRequestViewBean != null}" >
--%>
<!-- Search results start -->
<!-- If you want your results section to span only 50% set this to 50% and your mail table will be 100% -->
<table id="resultsMaskTable" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
<div class="roundcont contentContainer">
<div class="roundright">
  <div class="roundtop">
    <div class="roundtopright"> <img src="/images/rndBoxes/borderTL.gif" alt="" width="15" height="15" class="corner" style="display: none" /></div>
  </div>
  <div class="roundContent">
    
    <div id="resultDiv" class="dataContent">
    <table id="searchTable" width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">

<tr>
<td colspan="4">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableSearch">
<tr>
<td width="15%" class="optionTitleBoldRight" nowrap><fmt:message key="label.manufacturer"/>:</td>
<td width="85%">${MaterialBean.manufacturer}</td>
</tr>
<tr>
<td width="15%" class="optionTitleBoldRight" nowrap><fmt:message key="label.tradename"/>:</td>
<td width="85%">${MaterialBean.tradeName}</td>
</tr>
</table>
</td>
</tr>


<tr>

<td colspan="4" class="optionTitleLeft">
<fieldset>
<legend>
	<fmt:message key="label.comments"/>
</legend>
<table>
<tr>
<td>
	<div  id="updateResultLink" class="boxhead">
	
<c:set var="managePermission" value="N"/>
<c:set var="adminPermission" value="N"/>
<tcmis:facilityPermission indicator="true" userGroupId="manageSecLblInfo" facilityId="${param.facilityId}">
  <c:set var="managePermission" value="Y"/>
</tcmis:facilityPermission>
<tcmis:facilityPermission indicator="true" userGroupId="secLblInfoAdmin" facilityId="${param.facilityId}">
  <c:set var="managePermission" value="Y"/>
  <c:set var="adminPermission" value="Y"/>
</tcmis:facilityPermission>

	<c:if test="${managePermission == 'Y' || adminPermission == 'Y'}">
	  	<a href="#" onclick="addRow();"><fmt:message key="label.addrow"/></a> |
	    <a href="#" onclick="deleteRow();"><fmt:message key="label.delete"/></a> |
	    <a href="#" onclick="updatePage();"><fmt:message key="label.update"/></a>
	</c:if>
	</div>
</td>
</tr>
<%-- For First Grid --%>
<tr>
<td>
<div id="secondaryLabelDataBean" style="height:200px;display:;" ></div>
</td>
</tr>
<script language="JavaScript" type="text/javascript">
<!--
<c:forEach var="bean" items="${secondaryLabelDataColl}" varStatus="status">
	commentId[${status.index +1}] = buildNameDropdownArray('${bean.typeId}');
</c:forEach>

<c:set var="dataCount" value='0'/>

var jsonMainData = {
  rows:[
<c:forEach var="bean" items="${secondaryLabelDataColl}" varStatus="status">
        <c:if test="${status.index > 0}">,</c:if>


  /*The row ID needs to start with 1 per their design.*/
   { id:${status.index +1},
     data:[
		 '${managePermission}',
		 '${bean.typeId}',
		 '${bean.commentId}',
		 '<tcmis:jsReplace value="${bean.commentAltTxt}" processMultiLines="true"/>'
 ]}
    <c:set var="dataCount" value='${dataCount+1}'/>
</c:forEach>
]};

var secondaryLabelDataConfig = [
  {
  	columnId:"permission"
  },
  {
	columnId:"typeId",
	columnName:"<fmt:message key="label.type"/>",
	onChange:typeChanged,
	width:15,
  	type:"hcoro"
},
{
	columnId:"commentId",
	columnName:"<fmt:message key="label.name"/>",
	onChange:nameChanged,
	width:12,
  	type:"hcoro"
},
{
  	columnId:"commentAltTxt",
	columnName:"<fmt:message key="label.description"/>",
  	type:'txt',   
  	tooltip:"Y",
  	width:40
}
];

//-->
</script>

<%-- For Second Grid --%>
<tr><td>&nbsp;</td></tr>
<tr>
<td>
<div id="iconBean" style="height:200px;width:300px;display: none;" ></div>
</td>
</tr>
<c:if test="${!empty iconColl}" >
<script language="JavaScript" type="text/javascript">
<!--

<c:set var="iconCount" value='0'/>

var jsonIconData = {
  rows:[
<c:forEach var="bean" items="${iconColl}" varStatus="status">
        <c:if test="${status.index > 0}">,</c:if>
        
<c:set var="ok" value="false"/>
<c:if test="${bean.iconInLabelFlag == 'Y'}"><c:set var="ok" value="true"/></c:if>

  /*The row ID needs to start with 1 per their design.*/
   { id:${status.index +1},
     data:[
		 '${managePermission}',
		 ${ok},
		 '${bean.iconName}',
		 '${bean.iconId}',
		 '${bean.iconInLabelFlag}'
 ]}
    <c:set var="iconCount" value='${iconCount+1}'/>
     </c:forEach>
]};

var iconConfig = [
{
  	columnId:"permission"
},
{
  	columnId:"ok",
  	columnName:'<fmt:message key="label.ok"/>',
  	type:'hchstatus',  
    align:'center',
    width:5
},
{
	columnId:"iconName",
	columnName:"<fmt:message key="label.icon"/>",
	width:20
},
{
  	columnId:"iconId"
},
{
  	columnId:"iconInLabelFlag"
}
];

//-->
</script>
</c:if>

</table>
</fieldset>
</td>
</tr>

   </table>
  </div>
  </div>
  <div class="roundbottom">
    <div class="roundbottomright"> <img src="/images/rndBoxes/borderBL.gif" alt="" width="15" height="15" class="corner" style="display: none" /> </div>
  </div>
</div>
</div>
</td></tr>
</table>
<!-- Search results end -->
<%--
</c:if>
--%>
<!-- Hidden element start, the search text is just saved, might not be used. -->
 <div id="hiddenElements" style="display: none;">
   <input type="hidden" name="uAction" id="uAction">
   <input type="hidden" name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" />
   <input type="hidden" name="iconCount" id="iconCount" value="<c:out value="${iconCount}"/>" />
   <input type="hidden" name="materialId" id="materialId" value="${param.materialId}"/>
   <input type="hidden" name="facilityId" id="facilityId" value="${param.facilityId}"/>
   <input type="hidden" name="catalog" id="catalog" value="${param.catalog}"/>
 </div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->

<c:if test="${adminPermission == 'Y'}">
    <script type="text/javascript">
        <!--
        showRightClick = true;
        //-->
    </script>
</c:if>

</tcmis:form>

</body>
</html:html>

