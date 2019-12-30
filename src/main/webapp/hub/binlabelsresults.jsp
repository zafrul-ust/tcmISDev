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
<!-- Add any other stylesheets you need for the page here -->

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<%--NEW - grid resize--%>
<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js"></script>

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
<script type="text/javascript" src="/js/hub/binlabelsresults.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/dhtmlxLayout/codebase/dhtmlxlayout.js"></script>
<script type="text/javascript" src="/dhtmlxWindows/codebase/dhtmlxwindows.js"></script>
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
<script type="text/javascript" src="/dhtmlxCombo/codebase/dhtmlxcombo.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_combo.js"></script>

<title>
<fmt:message key="binlabels.title"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData = {alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",
minutes:"<fmt:message key="label.minutes"/>",
seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
norow:"<fmt:message key="error.norowselected"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
required:"<fmt:message key="errors.required"/>",
mustBeAnInteger:"<fmt:message key="errors.integer"/>",
routeOrder:"<fmt:message key="label.routeOrder"/>",
binType:"<fmt:message key="label.bintype"/>"};

var beangrid;

var config = [
{ columnId:"permission",
  columnName:'',
  submit:false
},
{
  columnId:"printPermission"
},
{
  columnId:"statusDisplayPermission"
},
{
 columnId:"roomPermission"
},
{
 columnId:"onSiteDisplayPermission"
},
{ columnId:"ok",
  columnName:'<fmt:message key="label.ok"/>',
  width:3,
  type:"hch",  
  sorting:"haasHch",
  submit:true
		
},
{ columnId:"print",
  columnName:'',
  type:"hch",  
  width:3,
  sorting:"haasHch",
  submit:false
},
{ columnId:"bin",
  columnName:'<fmt:message key="label.bin"/>',
  width:16,
  submit:true
},
{ columnId:"room",
  columnName:'<fmt:message key="label.room"/>',  
  type:"hcoro",
  submit:true,
  sorting:"haasCoro",
  width:20
},
{  columnId:"oldStatus",
  columnName:'', 
  submit:true	
		
},
{ columnId:"branchPlant",
  columnName:'',
  submit:true	
}
,
{ columnId:"statusDisplay",
  columnName:'<fmt:message key="label.active"/>',
  width:5,
  type:"hchstatus",  
  sorting:"haasHch",
  submit:false	
},
{ columnId:"status",
  columnName:'',  
  submit:true  		
}
,
{ columnId:"onSiteDisplay",
  columnName:'<fmt:message key="hubbin.label.onsite"/>',
  width:6,
  type:"hchstatus",  
  sorting:"haasHch"		
},
{ columnId:"onSite",
  columnName:'',
  width:6,
  type:"hchstatus",  
  sorting:"haasHch",
  submit:true		
},
{ columnId:"binType",
	  columnName:'<fmt:message key="label.bintype"/>',
	  width:10,
	  type:"hcoro",
	  submit:true				
},
{ columnId:"routeOrder",
	  columnName:'<fmt:message key="label.routeOrder"/>',
	  width:8,
	  type:"hed",
	  submit:true				
}

];  

<c:set var="hasPerm" value='N'/>
<tcmis:inventoryGroupPermission indicator="true" userGroupId="addNewBin" facilityId="${sourceHubName}">
<c:set var="hasPerm" value='Y'/>
</tcmis:inventoryGroupPermission>


<c:set var="selectedHub" value='${param.branchPlant}'/>

<c:set var="notEmptyArr" value='N'/>

<c:forEach var="hubRoomOvBean" items="${hubRoomOvBeanCollection}" varStatus="status1">

<c:set var="currentHub" value='${status1.current.hub}'/>
<c:choose>
<c:when test="${currentHub == selectedHub}" >
<c:set var="roomCollection" value='${status1.current.roomVar}'/>

var room= new Array({text:'<fmt:message key="label.pleaseselect"/>',value:''}	
		<c:forEach var="bean" items="${roomCollection}" varStatus="status2"> 
		<c:set var="notEmptyArr" value='Y'/>
		,{text:'${status2.current.room}',value:'${status2.current.room}'}
		 
		</c:forEach>
		);
		
var binType = [{text:'<fmt:message key="label.pleaseselect"/>',value:''}
    <c:forEach var="bean" items="${vvBinTypes}" varStatus="status2">
	,{text:'${status2.current}',value:'${status2.current}'}
	</c:forEach>
	];

</c:when>
<c:otherwise>

</c:otherwise>
</c:choose>

	
	</c:forEach>  	


<c:if test="${notEmptyArr eq 'N'}">
var room= new Array({text:'',value:''});
</c:if>
	 
// -->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoad();">
<%-- 
Nawaz----${sourceHubName}-----nawaz
--%>
<tcmis:form action="/binlabelsresults.do" onsubmit="return submitFrameOnlyOnce();">
<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/>
    ${tcmISError}
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
    </c:forEach>
    <c:if test="${param.maxData == fn:length(binlabelsBeanCollection)}">
     <fmt:message key="label.maxdata">
      <fmt:param value="${param.maxData}"/>
     </fmt:message>
    </c:if>
</div>

<script type="text/javascript">
   <c:choose>
    <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISErrors }">
    <c:choose>
      <c:when test="${param.maxData == fn:length(binlabelsBeanCollection)}">
	    showErrorMessage = true;
	    parent.messagesData.errors = "<fmt:message key="label.noticewindowtitle"/>";
	  </c:when>
	  <c:otherwise>
	    showErrorMessage = false;
	  </c:otherwise>
	</c:choose>
    </c:when>
    <c:otherwise>
     showErrorMessage = true;
    </c:otherwise>
   </c:choose>
    //-->
</script>

<!-- Error Messages Ends -->
<div class="interface" id="resultsPage">
<div class="backGroundContent">

<div id="binlabelsViewBean" style="width:100%;height:400px;" style="display: none;"></div>

<c:if test="${binlabelsBeanCollection != null}">


<script type="text/javascript">
<!--
<c:set var="dataCount" value='${0}'/>



<c:if test="${!empty binlabelsBeanCollection}" >  
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="binLabelCol" items="${binlabelsBeanCollection}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>

<tcmis:jsReplace var="bin" value="${status.current.bin}" processMultiLines="bin" />


/*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},

	<c:if test="${status.current.status eq 'I'}">'class':"grid_black",</c:if>
 data:[
  '${hasPerm}','Y',
  '${hasPerm}','${hasPerm}','N','',
  '',
  '${bin}',
  '${status.current.room}','${status.current.status}','${status.current.branchPlant}',
  <c:choose>
  <c:when test="${status.current.status eq 'A'}" >  
   true,
  </c:when>
  <c:otherwise>
   false,   
  </c:otherwise>
 </c:choose>
 '',
 <c:choose>
 <c:when test="${status.current.onSite eq 'Y'}" >  
  true,
 </c:when>
 <c:otherwise>
  false,   
 </c:otherwise>
</c:choose> 
'${status.current.onSite}',
'${status.current.binType}',
'${status.current.routeOrder}'  
  ]}  
 <c:set var="dataCount" value='${dataCount+1}'/> 
 </c:forEach>
]};
</c:if>
//-->   
</script>

        <!-- If the collection is empty say no data found -->
        <c:if test="${empty binlabelsBeanCollection}">
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

    <!-- Hidden element start --> 
    <div id="hiddenElements" style="display: none;">    			
		<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden"/>
        <input name="action" id="action" value="" type="hidden"/>     
        <input name="minHeight" id="minHeight" type="hidden" value="100"/>        
        <input name="searchField" id="searchField" type="hidden" value="${param.searchField}"/>
        <input name="room" id="room" type="hidden" value="${param.room}"/>        
        <input name="searchMode" id="searchMode" type="hidden" value="${param.searchMode}"/>
        <input name="searchArgument" id="searchArgument" type="hidden" value="${param.searchArgument}"/>
        <input name="hub" id="hub" type="hidden" value="${param.hub}"/>
        <input type="hidden" name="branchPlant"  id="branchPlant" value="${param.hub}"/>
        <input type="hidden" name="sourceHubName"  id="sourceHubName" value="${sourceHubName}"/>
        <input type="hidden" name="submitSearch"  id="submitSearch" value=""/>
        <input type="hidden" name="showActiveOnly"  id="showActiveOnly" value="${param.showActiveOnly}"/>
        <input type="hidden" name="pageid"  id="pageid" value="${param.pageid }"/>
        <input type="hidden" name="personnelCompanyId"  id="personnelCompanyId" value="${personnelBean.companyId}"/>  
        <input type="hidden" name="paperSize" id="paperSize" value="31"/>
		<input name="maxData" id="maxData" type="hidden" value="2000"/>
    </div>
    <!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->

<c:if test="${hasPerm == 'Y'}">
    <script type="text/javascript">
        <!--
        showUpdateLinks = true;
        //-->
    </script>
</c:if>

</tcmis:form>
</body>
</html:html>