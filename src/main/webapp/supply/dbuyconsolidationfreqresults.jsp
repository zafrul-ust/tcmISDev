<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tcmis.tld" prefix="tcmis" %>

<html lang="true">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<meta http-equiv="expires" content="-1"/>
<link rel="shortcut icon" href="https://www.tcmis.com/images/buttons/tcmIS.ico"></link>

<%@ include file="/common/locale.jsp" %>

<!--Use this tag to get the correct CSS class.
This looks at what the user's preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<!-- Add any other stylesheets you need for the page here -->

<script type="text/javascript" src="/js/supply/dbuyconsolidationfreq.js"></script>
<script type="text/javascript" src="/js/common/formchek.js"></script>			<%-- Validation support --%>
<script type="text/javascript" src="/js/common/commonutil.js"></script>

<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script> <%-- This handles all the resizing of the page and frames --%>
<script type="text/javascript" src="/js/common/disableKeys.js"></script>		<%-- This disables various key press events --%>

<%-- Right Mouse Click (RMC) Menu support, keep in all pages  --%>
<script type="text/javascript" src="/js/menu/milonic_src.js"></script>
<script type="text/javascript" src="/js/menu/mmenudom.js"></script>
<script type="text/javascript" src="/js/menu/mainmenudata.js"></script>
<script type="text/javascript" src="/js/menu/contextmenu.js"></script>
<%@ include file="/common/rightclickmenudata.jsp" %>

<%-- Form popup Calendar support --%>
<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>

<%-- Grid support --%>
<script type="text/javascript" src="/js/common/standardGridmain.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>    
<%--Uncomment below if you are providing header menu to switch columns on/off--%>
<%--<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_hmenu.js"></script>--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_rowspan.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/rowspan_cell_patch.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
recordFound:"<fmt:message key="label.recordFound"/>",
searchDuration:"<fmt:message key="label.searchDuration"/>",minutes:"<fmt:message key="label.minutes"/>",seconds:"<fmt:message key="label.seconds"/>",
validvalues:"<fmt:message key="label.validvalues"/>",
submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
supplyPath:"<fmt:message key="label.supplypath"/>", 
runTime:"<fmt:message key="label.runtime"/>", 
runDay:"<fmt:message key="label.runday"/>",
inventoryGroup:"<fmt:message key="label.inventorygroup"/>",
needByTolerance:"<fmt:message key="label.needbytolerance"/>",
norowselected:"<fmt:message key="error.norowselected"/>"
};

<c:set var="beanCollection" value="${dBuyConsFreqViewBeanColl}"/>



/*var gridConfig = {
	divName:'dBuyConsFreqBean', // the div id to contain the grid.
	beanData:'jsonMainData',     // the data variable name for jsonparse, as in grid.parse( beanData ,"json" )
	beanGrid:'beanGrid',     // the grid's name, as in beanGrid.attachEvent...
	config:'config',	     // the column config var name, as in var config = { [ columnId:..,columnName...
	rowSpan:false,			 // this page has rowSpan > 1 or not.
	submitDefault:true,    // the fields in grid aalre defaulted to be submitted or not.
	singleClickEdit:true,	
	onRowSelect:doOnRowSelected,   // the onRowSelect event function to be attached, as in beanGrid.attachEvent("onRowSelect",selectRow)
	onRightClick:selectRightclick   // the onRightClick event function to be attached, as in beanGrid.attachEvent("onRightClick",selectRow)
//	onBeforeSorting:_onBeforeSorting
};*/
<tcmis:opsEntityPermission indicator="true" opsEntityId="${param.opsEntityId}"  userGroupId="dBuyConsolidationFreq">
with(milonic=new menuname("newMenu")){
    top="offset=2"
    style = contextStyle;
    margin=3
    	aI("text=<fmt:message key="label.duplicate"/>;url=javascript:dup();");
    	aI("text=<fmt:message key="label.changeneedbytolerance"/>;url=javascript:editNeedByTolerance();");
}
</tcmis:opsEntityPermission>
drawMenus();

var config = [
  {
   columnId:"permission"
  },
  {
     columnId:"okDoUpdatePermission"
  },
  {
  	columnId:"okDoUpdate",
  	columnName:'<fmt:message key="label.ok"/>',
  	width:3,
  	type:'hch'
  },
  {
    columnId:"inventoryGroupNamePermission"
  },
  {
  	columnId:"inventoryGroupName",
  	columnName:'<fmt:message key="label.inventorygroup"/>',
    type:'hcoro',
    width:14
  },
  {
	columnId:"needByTolerance",
	columnName:'<fmt:message key="label.needbytolerance"/>'
  },
  {
     columnId:"supplyPathPermission"
  },
  {
  	columnId:"supplyPath",
  	columnName:'<fmt:message key="label.supplypath"/>',
    width:12,
    type:'hcoro'
  },
  {
     columnId:"runDayPermission"
  },
  {
  	columnId:"runDay",
  	columnName:'<fmt:message key="label.runday"/>',
    width:9,
    type:'hcoro'
  },
  {
     columnId:"runTimePermission"
  },
  {
  	columnId:"runTime",
  	columnName:'<fmt:message key="label.runtime"/>',
    width:12,
    type:'hcoro'
  },
  {
  	columnId:"enteredBy",
  	columnName:'<fmt:message key="label.updatedby"/>',
    tooltip:"Y",
    width:10
  },
  {
  	columnId:"transactionDate",
  	columnName:'<fmt:message key="label.updateddate"/>',
    tooltip:"Y",
    width:12
  },  
  {
	columnId:"opsCompanyId"
  },
  {
  	columnId:"opsEntityId"
  },
  {
  	columnId:"inventoryGroup"
  },
  {
  	columnId:"oldRunTime"
  },
  {
  	columnId:"oldSupplyPath"
  },
  {
  	columnId:"status"
  }
  ];

// -->
</script>

<script type="text/javascript">
<!--
<c:choose>
   <c:when test="${new == 'Y'}">
    done = true;
   </c:when>
   <c:otherwise>
    done = false;
   </c:otherwise>
</c:choose>

//-->
</script>
 
</head>
<%--TODO - Singl click open remarks.--%>
<body bgcolor="#ffffff" onload="onloadset()">
<tcmis:form action="/dbuyconsolidationfreqresults.do" onsubmit="return submitFrameOnlyOnce();">

<tcmis:opsEntityPermission indicator="true" opsEntityId="${param.opsEntityId}"  userGroupId="dBuyConsolidationFreq">
 <script type="text/javascript">
 <!--
  showUpdateLinks = true;
 //-->
 </script>
</tcmis:opsEntityPermission>
<!-- You can build your error messages below. But we want to trigger the YUI pop-up from the main page.
So this is just used to feed the YUI pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
 <!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/>
    ${tcmISError}
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
    </c:forEach>        
</div>

<script type="text/javascript">
   <c:choose>
    <c:when test="${requestScope['org.apache.struts.action.ERROR'] == null && empty tcmISError && empty tcmISErrors }">
     showErrorMessage = false;
    </c:when>
    <c:otherwise>
     showErrorMessage = true;
    </c:otherwise>
   </c:choose>   
    //-->       
</script>
 
<div class="interface" id="resultsPage">
<div class="backGroundContent">
<c:set var="dataCount" value='${0}'/>

<c:if test="${!empty beanCollection}" >
<div id="dBuyConsFreqBean" style="width:100%;height:500px;" style=""></div>
<!-- Search results start -->
<script type="text/javascript">
<!--
/*This is to keep track of whether to show any update links.
If the user has any update permisions for any row then we show update links.*/
//<c:set var="showUpdateLink" value='N'/>

var runTime = new Array(
		  {value:"-1",text:'<fmt:message key="label.pleaseselect"/>'},
		  {value:"0",text:'12am CST (GMT-6)'},{value:"1",text:'1am CST (GMT-6)'},{value:"2",text:'2am CST (GMT-6)'},{value:"3",text:'3am CST (GMT-6)'},
		  {value:"4",text:'4am CST (GMT-6)'},{value:"5",text:'5am CST (GMT-6)'},{value:"6",text:'6am CST (GMT-6)'},{value:"7",text:'7am CST (GMT-6)'},
		  {value:"8",text:'8am CST (GMT-6)'},{value:"9",text:'9am CST (GMT-6)'},{value:"10",text:'10am CST (GMT-6)'},{value:"11",text:'11am CST (GMT-6)'},
		  {value:"12",text:'12pm CST (GMT-6)'},{value:"13",text:'1pm CST (GMT-6)'},{value:"14",text:'2pm CST (GMT-6)'},{value:"15",text:'3pm CST (GMT-6)'},
		  {value:"16",text:'4pm CST (GMT-6)'},{value:"17",text:'5pm CST (GMT-6)'},{value:"18",text:'6pm CST (GMT-6)'},{value:"19",text:'7pm CST (GMT-6)'},
		  {value:"20",text:'8pm CST (GMT-6)'},{value:"21",text:'9pm CST (GMT-6)'},{value:"22",text:'10pm CST (GMT-6)'},{value:"23",text:'11pm CST (GMT-6)'}
		);
		
var runDay = new Array(
		  {value:"-1",text:'<fmt:message key="label.pleaseselect"/>'},
		  {value:"0",text:'<fmt:message key="label.everyday"/>'},
		  {value:"1",text:'Monday'},{value:"2",text:'Tuesday'},{value:"3",text:'Wednesday'},
		  {value:"4",text:'Thursday'},{value:"5",text:'Friday'},{value:"6",text:'Saturday'},{value:"7",text:'Sunday'}
		);



var supplyPath = new Array(
         {value:"-1",text:'<fmt:message key="label.pleaseselect"/>'},
		<c:forEach var="rBean" items="${vvSupplyPathBeanCollection}" varStatus="status1">
		   <c:set var="jspLabel" value=""/>
		   <c:if test="${fn:length(status1.current.jspLabel) > 0}"><c:set var="jspLabel">${status1.current.jspLabel}</c:set></c:if>
		  <c:if test="${status1.index > 0}">,</c:if>
		  {value:"${status1.current.supplyPath}",text:'<fmt:message key="${jspLabel}"/>'}
		</c:forEach>
);	

<c:set var="igCount" value="0"/>
var inventoryGroupName = 
new Array(
<c:forEach var="nouse0" items="${personnelBean.opsHubIgOvBeanCollection}" varStatus="status">
<c:if  test="${ status.current.opsEntityId == param.opsEntityId }"> 
	<c:choose>
		<c:when test="${param.hub == ''}">
		<c:forEach var="nouse1" items="${status.current.hubIgCollection}" varStatus="status1">
		  <c:forEach var="nouse2" items="${status1.current.inventoryGroupCollection}" varStatus="status2">
			<tcmis:opsEntityPermission indicator="true" opsEntityId="${status.current.opsEntityId}"  userGroupId="dBuyConsolidationFreq">
			<c:if test="${ igCount !=0 }">,</c:if>
			{
			value:'${status2.current.inventoryGroup}',
			text:'<tcmis:jsReplace value="${status2.current.inventoryGroupName}"/>'
		   }
		   <c:set var="igCount" value="${igCount+1}"/>
	  		</tcmis:opsEntityPermission>
		 </c:forEach>
		</c:forEach>
		</c:when>
		<c:otherwise>
		 <c:forEach var="nouse1" items="${status.current.hubIgCollection}" varStatus="status1">
		 <c:if  test="${ status1.current.hub == param.hub }">
		  <c:forEach var="nouse2" items="${status1.current.inventoryGroupCollection}" varStatus="status2">
			<tcmis:opsEntityPermission indicator="true" opsEntityId="${status.current.opsEntityId}"  userGroupId="dBuyConsolidationFreq">
			<c:if test="${ igCount !=0 }">,</c:if>
			{
			value:'${status2.current.inventoryGroup}',
			text:'<tcmis:jsReplace value="${status2.current.inventoryGroupName}"/>'
		   }
		   <c:set var="igCount" value="${igCount+1}"/>
	  		</tcmis:opsEntityPermission>
		 </c:forEach>
		 </c:if>
		</c:forEach>
		</c:otherwise>
	</c:choose>
 </c:if>
 
</c:forEach>
);	

/*Storing the data to be displayed in a JSON object array.*/


var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="bean" items="${beanCollection}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>

<c:set var="runDayPerm" value='N'/>
<c:set var="readonly" value='N'/>
<tcmis:opsEntityPermission indicator="true" opsEntityId="${status.current.opsEntityId}"  userGroupId="dBuyConsolidationFreq">
 <c:set var="hasPerm" value="Y"/>
 <c:if test="${bean.runDay == null}">
	<c:set var="runDayPerm" value='Y'/>
</c:if>
</tcmis:opsEntityPermission>

<fmt:formatDate var="fmtTransactionDate" value="${bean.transactionDate}" pattern="MM/dd/yyyy kk:mm:ss"/>
{
 id:${status.index +1},
 data:[
  '${hasPerm}',
  '${hasPerm}',
  '',
  '${readonly}',
  '${bean.inventoryGroupName}',
  '${bean.igdNeedByTolerance}',
  '${hasPerm}',
  '${bean.supplyPath}',
  '${runDayPerm}',
  '${bean.runDay}',
  '${hasPerm}',
  '${bean.runTime}',
  '${bean.enteredBy}',
  '${fmtTransactionDate}',
  '${bean.opsCompanyId}',
  '${bean.opsEntityId}',
  '${bean.inventoryGroup}',
  '${bean.runTime}', 
  '${bean.supplyPath}',        
  '${bean.status}'
  ]
}
 <c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
 
]};
//-->
</script>

</c:if>

<c:if test="${empty beanCollection}" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
 <tr>
  <td width="100%">
   <fmt:message key="main.nodatafound"/>
  </td>
 </tr>
</table>
</c:if>

<div id="hiddenElements" style="display: none;">
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
<input name="uAction" id="uAction" value="" type="hidden"/>
<input name="opsEntityId" id="opsEntityId" value="${param.opsEntityId}" type="hidden"/>
<input name="hub" id="hub" value="${param.hub}" type="hidden"/>
<input name="inventoryGroup" id="inventoryGroup" value="${param.inventoryGroup}" type="hidden"/>
</div>

</div>
</div> <!-- close of interface -->

</tcmis:form>

</body>
</html>