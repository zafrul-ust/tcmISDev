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
<script type="text/javascript" src="/js/distribution/speclist.js"></script>
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
<c:choose>
    <c:when test="${param.save == 'Y'}">
     	<fmt:message key="label.specificationlookup"/>
    </c:when>
    <c:otherwise>
     	<fmt:message key="label.addline"/> > <fmt:message key="label.specificationlookup"/>
    </c:otherwise>
   </c:choose>   
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
scratchpad:"<fmt:message key="label.scratchPad"/>",
norow:"<fmt:message key="error.norowselected"/>",
errors:"<fmt:message key="label.errors"/>"};

var config = [
{ columnId:"permission",
  columnName:''
},
{ columnId:"ok",
  columnName:'   ',
  width:3,
  type:"hchstatus",
  sorting:"haasHch",
  align:"center",
  onChange:callEnableDisable,
  submit:true
},
{ columnId:"specLibraryDesc",
  columnName:'<fmt:message key="label.library"/>',
  width:12
},
{ columnId:"specName",
  columnName:'<fmt:message key="label.specification"/>',
  width:12
},
{ columnId:"specVersion",
  columnName:'<fmt:message key="label.revision"/>',
  align:"left",
  width:5
},
{ columnId:"specAmendment",
  columnName:'<fmt:message key="label.amendment"/>',
  align:"left",
  width:6
},
{ columnId:"detail",
  columnName:'<fmt:message key="label.detail"/>',
  tooltip:"Y",
  width:10
},
{
  	columnId:"coc",
  	columnName:'<fmt:message key="catalogspec.label.coc"/>',
  	type:'hchstatus',
    align:'center',
    width:3
},
{
  	columnId:"coa",
  	columnName:'<fmt:message key="catalogspec.label.coa"/>',
  	type:'hchstatus',
    align:'center',
    width:3
},
{
  	columnId:"itemId"
},
{	
  	columnId:"specLibrary"
},
{
  	columnId:"specId"
}
];

with(milonic=new menuname("rightClickMenu")){
    top="offset=2"
    style = contextStyle;
    margin=3
    aI("text=<fmt:message key="label.modifyspecdetail"/>;url=javascript:getDetail();");
    aI("text=<fmt:message key="label.modifyspec"/>;url=javascript:getSpec();");
}

drawMenus();
// -->
</script>
</head>

<body bgcolor="#ffffff"  onload="resultOnLoad();closeAllchildren();" onresize="resizeFrames()" onunload="closeAllchildren();try{opener.closeTransitWin();}catch(ex){}">

<tcmis:form action="/speclist.do" onsubmit="return submitFrameOnlyOnce();" target="resultFrame">
<div class="interface" id="mainPage" style="">

<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/>
    ${tcmISError}
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
  <br/><br/><br/><fmt:message key="label.pleasewait"/>
  <br/><br/><br/><img src="/images/rel_interstitial_loading.gif" align="middle"/>
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
       <span id="updateResultLink">
       	<a href="#" onclick="getSpecList();"><fmt:message key="label.returnselected"/></a> 
       	<span id="addSpecSpan">| <a href="#" onclick="addNewSpec();"><fmt:message key="addspecs.title"/></a></span></span>
     </div> <%-- mainUpdateLinks Ends --%>
    </div> <%-- boxhead Ends --%>

<div class="dataContent">
 <!--  result page section start -->
<div id="catalogItemSpecListBean" style="height:400px;" style="display: none;"></div>

<c:if test="${specListColl != null}">
<script type="text/javascript">
<!--
<c:set var="noSpecifictionChecked" value='N'/>

<c:set var="dataCount" value='${0}'/>
var jsonMainData = new Array();
var jsonMainData = { 
rows:[    
<c:forEach var="specBean" items="${specListColl}" varStatus="status">
<%-- <c:if test="${specBean.specId != 'No Specification'}" > --%>
<c:if test="${dataCount > 0}">,</c:if>
<c:set var="coc" value="false"/>
<c:set var="coa" value="false"/>
<c:set var="okVal" value="false"/>

<tcmis:jsReplace var="specName" value="${specBean.specName}"  processMultiLines="false"/>
<tcmis:jsReplace var="specId" value="${specBean.specId}"  processMultiLines="true"/>
<tcmis:jsReplace var="detail" value="${specBean.detail}"  processMultiLines="true"/>
<tcmis:jsReplace var="specLibraryDesc" value="${specBean.specLibraryDesc}"  processMultiLines="true"/>
<tcmis:jsReplace var="specLibrary" value="${specBean.specLibrary}"  processMultiLines="false"/>
<c:if test="${specBean.coc == 'Y' || (param.save != 'Y' && specId == 'Std Mfg Cert')}"><c:set var="coc" value="true"/></c:if>
<c:if test="${specBean.coa == 'Y'}"><c:set var="coa" value="true"/></c:if>

<c:if test="${(specBean.coa == 'Y') or (specBean.coc == 'Y') }"><c:set var="okVal" value="true"/></c:if>
<c:if test="${param.save != 'Y' && specId == 'Std Mfg Cert'}"><c:set var="okVal" value="true"/></c:if>

<c:if test="${specBean.specForPart == 'Y' && specId == 'No Specification'}">
 <c:set var="okVal" value="true"/>
 <c:set var="noSpecifictionChecked" value='Y'/>
</c:if>


 /*The row ID needs to start with 1 per their design.*/
{ id:${dataCount +1},
 data:[ 
<%--  
<c:choose>
  <c:when test="${specBean.specId == 'No Specification'}" >
    'N',
  </c:when>
  <c:otherwise>
    'Y',
  </c:otherwise>
</c:choose>    --%>
'Y',${okVal},'${specLibraryDesc}','${specName}','${specBean.specVersion}','${specBean.specAmendment}',  
'${detail}',${coc},${coa},'${status.current.itemId}','${specLibrary}','${specId}' ]}
 <c:set var="dataCount" value='${dataCount+1}'/>
<%-- </c:if>  --%>
 </c:forEach>
]};
//-->
</script>
</c:if>

<c:if test="${noSpecifictionChecked == 'Y'}">
    <script type="text/javascript">
        <!--
        noSpecifictionChecked = true;
        //-->
    </script>
</c:if>

<!-- If the collection is empty say no data found -->

<c:if test="${empty specListColl}">
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
    <tr>
    <td width="100%">
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr> 
 </table> 
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
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
<input name="uAction" id="uAction" value="${param.uAction}" type="hidden"/>
<!--This sets the start time for time elapesed.-->
<input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}"/>
<input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>
<input name="save" id="save" type="hidden" value="${param.save}"/>
<input name="itemId" id="itemId" type="hidden" value="${param.itemId}"/>
<input name="specListConcat" id="specListConcat" type="text" value="${param.specListConcat}"/>
<input name="specNameConcat" id="specNameConcat" type="text" value="${param.specNameConcat}"/>
<input name="specVersionConcat" id="specVersionConcat" type="text" value="${param.specVersionConcat}"/>
<input name="specAmendmentConcat" id="specAmendmentConcat" type="text" value="${param.specAmendmentConcat}"/>
<input name="specDetailConcat" id="specDetailConcat" type="text" value="${param.specDetailConcat}"/>
<input name="specLibraryDescConcat" id="specLibraryDescConcat" type="text" value="${param.specLibraryDescConcat}"/>
<input name="specLibraryConcat" id="specLibraryConcat" type="text" value="${param.specLibraryConcat}"/>
<input name="specCocConcat" id="specCocConcat" type="text" value="${param.specCocConcat}"/>
<input name="specCoaConcat" id="specCoaConcat" type="text" value="${param.specCoaConcat}"/>
<input name="minHeight" id="minHeight" type="hidden" value="100"/>
</div>
<!-- Hidden elements end -->

</div> <!-- close of interface -->

<c:if test="${showUpdateLink == 'Y'}">
    <script type="text/javascript">
        <!--
        showUpdateLinks = true;
        //-->
    </script>
</c:if>
</tcmis:form>

<!-- You can build your error messages below. Similar divs would have to be built to show any other messages in a inline pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesArea" class="errorMessages" style="display: none;overflow: auto;">

</div>

</body>
</html:html>