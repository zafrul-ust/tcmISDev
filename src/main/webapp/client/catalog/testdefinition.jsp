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
<script type="text/javascript" src="/js/menu/mm_menueditapi.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

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
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_filter.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>

<script type="text/javascript" src="/js/client/catalog/testdefinition.js"></script>
<script type="text/javascript" src="/js/common/taxcodes.js"></script>

<script language="JavaScript" type="text/javascript">
<!--
windowCloseOnEsc = true;

var testId = new Array(
	<c:if test="${empty testColl}">{text:'<fmt:message key="label.pleasecreatenewtest"/>',value:''}</c:if>
	<c:if test="${!empty testColl}">{text:'<fmt:message key="label.pleaseselect"/>',value:''}</c:if>
	<c:forEach var="testBean" items="${testColl}" varStatus="status">
		,{text:'<tcmis:jsReplace value="${testBean.testId}"/>: <tcmis:jsReplace value="${testBean.shortName}"/>',value:'<tcmis:jsReplace value="${testBean.testId}"/>'}
	</c:forEach>
);

var frequencyType = new Array(
	{text:'<fmt:message key="label.pleaseselect"/>',value:''}
	<c:forEach var="frequencyTypeBean" items="${frequencyTypeColl}" varStatus="status">
		,{text:'<tcmis:jsReplace value="${frequencyTypeBean.frequencyTypeDisplay}"/>',value:'<tcmis:jsReplace value="${frequencyTypeBean.frequencyType}"/>'}
	</c:forEach>
);

var frequencyUnit = new Array(
	{text:'<fmt:message key="label.pleaseselect"/>',value:''}
	<c:forEach var="frequencyUnitBean" items="${frequencyUnitColl}" varStatus="status">
		,{text:'<tcmis:jsReplace value="${frequencyUnitBean.frequencyUnitDisplay}"/>',value:'<tcmis:jsReplace value="${frequencyUnitBean.frequencyUnit}"/>'}
	</c:forEach>
);

var active = new Array(
	{text:'<fmt:message key="label.active"/>',value:'Y'},
    {text:'<fmt:message key="label.inactive"/>',value:'N'}
);

with(milonic=new menuname("editTestMenu")){
    top="offset=2"
        style = contextStyle;
        margin=3        
        aI("text=");
}


drawMenus();


// -->
</script>


<title>
<fmt:message key="label.testdefinition"/> ${param.catalogId}
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
pleaseselect:"<fmt:message key="label.pleaseselect"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
test:"<fmt:message key="label.test"/>",
waitingforinputfrom:"<fmt:message key="label.waitingforinputfrom"/>",
pleasecreatenewtest:"<fmt:message key="label.pleasecreatenewtest"/>",
successTestForSkip:"<fmt:message key="label.successtestforskip"/>",
frequency:"<fmt:message key="label.frequency"/>",        
edittest:"<fmt:message key="label.edittest"/>",
deletetest:"<fmt:message key="label.delete"/>"
};

var lineMap = new Array();
var lineMap3 = new Array();
var rowSpanCols = [8,9,10,11];

var gridConfig = {
	divName:'labTestBean', // the div id to contain the grid.
	beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	beanGrid:'beanGrid',     // the grid's name, as in beanGrid.attachEvent...
	config:'labTestConfig',	     // the column config var name, as in var config = { [ columnId:..,columnName...
	rowSpan:true,			 // this page has rowSpan > 1 or not.
	submitDefault:true,    // the fields in grid are defaulted to be submitted or not.,
	noSmartRender: false,
	selectChild: 1,
	onRowSelect:selectRow,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
	onRightClick:selectRightclick   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
  };


// -->
</script>
</head>

<body bgcolor="#ffffff" onload="myInit();" onunload="if($v('uAction') != 'update'){try{opener.parent.closeTransitWin();}catch(ex){}}" onresize="myResize()">

<tcmis:form action="/testdefinition.do" onsubmit="return submitOnlyOnce();">

<div id="transitPage" class="optionTitleBoldCenter" style="display: none;">
  <br><br><br><fmt:message key="label.pleasewait"/>
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
        <td width="15%" class="optionTitleBoldRight" nowrap><fmt:message key="label.part"/>:</td>
        <td width="85%">${param.catPartNo}</td>
        </tr>
        <tr>
        <td width="15%" class="optionTitleBoldRight" nowrap><fmt:message key="label.description"/>:</td>
        <td width="85%">
            ${param.partDesc}
        </td>
        </tr>
        <tr>
        <td width="15%" class="optionTitleBoldRight" nowrap><input type="checkbox"  name="activeTestsOnly" id="activeTestsOnly" value="Y" <c:if test="${param.activeTestsOnly == 'Y'}">checked</c:if>  class="radioBtns" onclick="refreshTests()"/></td>
        <td width="85%">
            <fmt:message key="label.activetestsonly"/>
        </td>
        </tr>
    </table>
</td>
</tr>


<tr>

<td colspan="4" class="optionTitleLeft">
<fieldset>
<table>
<tr>
<td>

<div  id="updateResultLink" class="boxhead">
	
<c:set var="managePermission" value="N"/>
<tcmis:facilityPermission indicator="true" userGroupId="testDefinition" facilityId="${param.facilityId}">
  <c:set var="managePermission" value="Y"/>
</tcmis:facilityPermission>

	<c:if test="${managePermission == 'Y'}">
	  	<a href="#" onclick="addRow();"><fmt:message key="label.addrow"/></a> |
	    <a href="#" onclick="updatePage();"><fmt:message key="label.update"/></a> |
        <a href="#" onclick="applyDefaultTests();"><fmt:message key="label.applydefaulttests"/></a>
	    <span id="editSpan" style="display:none;"> | <a href="#" onclick="editTest();"><fmt:message key="label.edittest"/></a></span>
	</c:if>
    <c:if test="${managePermission == 'N'}">
	  	<fmt:message key="label.labtests"/>
	</c:if>
    </div>
</td>
</tr>
<%-- For First Grid --%>
<tr>
<td>
<div id="labTestBean" style="height:500px;display:;" ></div>
</td>
</tr>

<script language="JavaScript" type="text/javascript">
<!--

<c:set var="dataCount" value='0'/>

<c:if test="${!empty custLabTestColl}">
    var jsonMainData = {
      rows:[
        <c:forEach var="bean" items="${custLabTestColl}" varStatus="status">
                <c:if test="${status.index > 0}">,</c:if>

          <c:set var="testAllowedSkip" value="false"/>
          <c:if test="${status.current.testAllowedSkip == 'Y'}"><c:set var="testAllowedSkip" value="true"/></c:if>
          <fmt:formatDate var="fmtupdatedOn" value="${status.current.updatedOn}" pattern="${dateFormatPattern}"/>
          <c:set var="requiredCustResp" value="false"/>
          <c:if test="${status.current.requireCustomerResponse == 'Y'}"><c:set var="requiredCustResp" value="true"/></c:if>
          <c:set var="freqTypePer" value="${managePermission}"/>
          <c:if test="${status.current.frequencyUnit == 'Receipts'}"><c:set var="freqTypePer" value="N"/></c:if>


          /*The row ID needs to start with 1 per their design.*/
           { id:${status.index +1},
             data:[
                 '${managePermission}','${freqTypePer}','N',
                 '<tcmis:jsReplace value="${bean.testId}"/>',
                 '${bean.active}',
                 '${bean.noSuccessTestReqForSkip}',
                 ${testAllowedSkip},
                 '${bean.testType}',
                 '${bean.frequency}',
                 '${bean.frequencyUnit}',
                 '${bean.frequencyType}',
                 ${requiredCustResp},
                 '<tcmis:jsReplace value="${bean.updatedByName}" />',
                 '${fmtupdatedOn}',
                 '${status.current.updatedOn.time}',
                 ''
            ]}
            <c:set var="dataCount" value='${dataCount+1}'/>
        </c:forEach>
    ]};

        <c:set var="preFreqUnit" value=''/>
		<c:set var="tmpCount" value='0'/>
		<c:forEach var="tmpBean" items="${custLabTestColl}" varStatus="status">
		<c:choose>
			<c:when test="${tmpBean.frequencyUnit != preFreqUnit}">
				lineMap[${status.index}] = 1;
				<c:set var="preFreqUnit" value='${tmpBean.frequencyUnit}'/>
				<c:set var="tmpCount" value="${tmpCount + 1}"/>
				<c:set var="parent" value="${status.index}"/>
			</c:when>
			<c:otherwise>
				lineMap[${parent}]++;
			</c:otherwise>
		</c:choose>
			lineMap3[${status.index}] = ${tmpCount % 2};
		</c:forEach>

</c:if>

<%-- creating blank row if no tests assigned to part --%>
<c:if test="${empty custLabTestColl}">
    var jsonMainData = {
      rows:[
           { id:${1},
             data:[
                 '${managePermission}','${managePermission}','Y',
                 '',
                 '',
                 '',
                 '',
                 '',
                 '',
                 '',
                 '',
                 '',
                 '',
                 '',
                 '',
                 'new'
            ]}
    ]};
    <c:set var="dataCount" value='1'/>
</c:if>

var labTestConfig = [
{
  	columnId:"permission"
},
{
  	columnId:"frequencyTypePermission"
},
{
  	columnId:"testIdPermission"
},
{
	columnId:"testId",
	columnName:"<fmt:message key="label.test"/>",
	onChange:callChangeStatus,
	tooltip:"Y",
	width:25,
    permission:true,
    type:'hcoro'
},
{
	columnId:"active",
	columnName:"<fmt:message key="label.status"/>",
    onChange:callChangeStatus,
    width:8,
  	type:'hcoro'
},
{
  	columnId:"noSuccessTestReqForSkip",
  	type:'hed',    // input field
  	width:7, // the width of this cell
  	size:5,  // the size of the input field
    maxlength:5
},
{
  	columnId:"testAllowedSkip",
  	type:'hchstatus',  // checkbox:The value is string "true" if checked
    align:'center',
    width:5
},
{
  	columnId:"testType",
	onChange:callChangeStatus,
  	type:'hed',    // input field
  	width:8, // the width of this cell
  	size:5,  // the size of the input field
    maxlength:10
},
{
  	columnId:"frequency",
	columnName:"<fmt:message key="label.frequency"/>",
    attachHeader:'<fmt:message key="label.amount"/>',
    onChange:frequencyChanged,
    type:'hed',    // input field
  	width:8, // the width of this cell
  	size:5,  // the size of the input field
    maxlength:10
},
{
	columnId:"frequencyUnit",
	columnName:'#cspan',
    attachHeader:'<fmt:message key="label.unit"/>',
    onChange:frequencyUnitChanged,
    width:8,
  	type:'hcoro'
},
{
	columnId:"frequencyType",
	columnName:'#cspan',
    attachHeader:'<fmt:message key="label.type"/>',
    onChange:frequencyTypeChanged,
    width:8,
    permission:true,
    type:'hcoro'
},
{
  	columnId:"requireCustomerResponse",
	columnName:"<fmt:message key="label.requirecustomerresponse"/>",
    onChange:requiredCustomerResponseChange,
    type:'hchstatus',
    align:'center',
    width:5
},
{ 
	columnId:"updatedByName",
	columnName:'<fmt:message key="label.lastUpdatedBy"/>',
	tooltip:"Y",
	width:8
},
{
	columnId:"updatedOn",
	columnName:'<fmt:message key="label.updateddate"/>',
	hiddenSortingColumn:'hiddenUpdatedDateTime',
	sorting:'int',
	width:7
},
{ 
	columnId:"hiddenUpdatedDateTime",
	sorting:'int'
},
{
  	columnId:"status"
}
];

//-->
</script>

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
   <input type="hidden" name="companyId" id="companyId" value="${param.companyId}"/>
   <input type="hidden" name="facilityId" id="facilityId" value="${param.facilityId}"/>
   <input type="hidden" name="catalogId" id="catalogId" value="${param.catalogId}"/>
   <input type="hidden" name="catalogCompanyId" id="catalogCompanyId" value="${param.catalogCompanyId}"/>
   <input type="hidden" name="catPartNo" id="catPartNo" value="${param.catPartNo}"/>
   <input type="hidden" name="partGroupNo" id="partGroupNo" value="${param.partGroupNo}"/>
   <input type="hidden" name="partDesc" id="partDesc" value="${param.partDesc}"/>
 </div>
<!-- Hidden elements end -->

</div> <!-- close of contentArea -->

<!-- Footer message start -->
 <div class="messageBar">&nbsp;</div>
<!-- Footer message end -->

</div> <!-- close of interface -->
<c:if test="${managePermission == 'Y'}">
    <script type="text/javascript">
        <!--
        showRightClick = true;
        //-->
    </script>
</c:if>

</tcmis:form>

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

</body>
</html:html>

