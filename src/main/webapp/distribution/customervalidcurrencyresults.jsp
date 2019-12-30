
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

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
<script type="text/javascript" src="/js/distribution/customervalidcurrency.js"></script>

<!-- These are for the Grid-->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<%--This is for HTML form integration.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<%--This is for smart rendering.--%>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<%--This is to suppoert loading by JSON.--%>
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
<%--This file has our custom sorting and other utility methos for the grid.--%>    
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
    <fmt:message key="label.customervalidcurrency"/>
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData = {alert:"<fmt:message key="label.alert"/>",and:"<fmt:message key="label.and"/>",
            validvalues:"<fmt:message key="label.validvalues"/>",
            submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>",
            recordFound:"<fmt:message key="label.recordFound"/>",
            searchDuration:"<fmt:message key="label.searchDuration"/>",
            minutes:"<fmt:message key="label.minutes"/>",
            seconds:"<fmt:message key="label.seconds"/>",
            missingcurrency:"<fmt:message key="label.missingcurrency"/>",
            missingremittance:"<fmt:message key="label.missingremittance"/>",
            onlyonedefaultallowed:"<fmt:message key="label.onlyonedefaultallowed"/>",
            mustchooseonedefault:"<fmt:message key="label.mustchooseonedefault"/>",
            ok:"<fmt:message key="label.ok"/>"};

function currencyChanged(rowId,columnId,invval) {
	  var selectedOps = gridCellValue(haasGrid,rowId,columnId);
//pricegroupid	  
	  var inv = $("remittanceId"+rowId);
	  for (var i = inv.length; i > 0; i--) {
	    inv[i] = null;
	  }
	  var selectedIndex = 0 ;

	  var invarr = buildRemittanceId(selectedOps);
	  if(invarr.length == 0) {
	    setOption(0,messagesData.pleaseselect,"", "remittanceId"+rowId)
	  }
	
	  for (var i=0; i < invarr.length; i++) {
	    setOption(i,invarr[i].text,invarr[i].value, "remittanceId"+rowId);
	    if( invarr[i].value == invval ) selectedIndex = i;
	  }
	  inv.selectedIndex = selectedIndex;
}

    
var config = [
  {
  	columnId:"permission"
  },
  {
  	columnId:"currencyIdPermission"
  },
  {
  	columnId:"deleteCurrencyPermission"
  }, 
  {
  	columnId:"currencyId",
  	columnName:'<fmt:message key="label.currency"/>',
  	type:'hcoro',    // dropdown:This dropdown links to an array, deliveryType
  	align:'left',
  	onChange:currencyChanged,
  	width:12
  },
  {
	columnId:"remittanceId",
	columnName:'<fmt:message key="label.remittanceinstructions"/>',
	type:'hcoro',    // dropdown:This dropdown links to an array, deliveryType
	align:'left',
	width:12
  },
  {
  	columnId:"defaultCurrency",
  	columnName:'<fmt:message key="label.default"/>',
  	type:'hchstatus',  // checkbox:The value is string "true" if checked
    align:'center',
    onChange:checkOne,
    width:6
  },
  {
  	columnId:"deleteCurrency",
  	columnName:'<fmt:message key="label.delete"/>',
  	type:'hchstatus',  // checkbox:The value is string "true" if checked
    align:'center',
    width:6
  },
  {
  	columnId:"enteredByName",
  	columnName:'<fmt:message key="label.enteredby"/>',
    width:10
  },
  {
  	columnId:"enteredDate",
  	columnName:'<fmt:message key="label.entereddate"/>',
  	hiddenSortingColumn:'hiddenEnteredDateTime',
    align:'left',
    sorting:'int',
    width:8
  },
  {
    columnId:"hiddenEnteredDateTime"
  }
];

// More complicate form for type:'hcoro'. Build the dropdown using the data from database.
/*
var currencyId = new Array(); 
new Array(
	{
		value:'',text:'<fmt:message key="label.pleaseselect"/>'
	}
<c:forEach var="currencyBean" items="${vvCurrencyBeanCollection}" varStatus="status">
	,{
		value:'${status.current.currencyId}',
		text:'<tcmis:jsReplace value="${status.current.currencyDescription}"/> (${status.current.currencyDisplay})'
	}
</c:forEach>
);
*/

<c:set var="preops" value=""/>
<c:set var="opssep" value=""/>
var opspg = [
	    <c:forEach var="nouse0" items="${beanColl}" varStatus="status">
<%--	    
<c:set var="hasPerm" value="N"/>
<tcmis:opsEntityPermission indicator="true" opsEntityId="${status.current.opsEntityId}"  userGroupId="priceGroupAssignment">
<c:set var="hasPerm" value="Y"/>
</tcmis:opsEntityPermission>
--%>
	    <c:set var="curops" value="${status.current.currencyId}"/>
	    <c:if test="${ preops ne curops}">
	    ${opssep}
        {
          id:   '${status.current.currencyId}',
          name: '<tcmis:jsReplace value="${status.current.currencyDisplay}"/>',
          coll: [ 
        	 	  {  id: '${status.current.remittanceId}',
           	 	     name:'${status.current.remittanceName}'
        		  }
        <c:set var="opssep" value="]},"/>
    	</c:if>	        
	    <c:if test="${ preops eq curops}">
	    	    	 	   ,
	    	    	 	   {
	    	    			id:'${status.current.remittanceId}',
	    	    			name:'<tcmis:jsReplace value="${status.current.remittanceName}"/>'
	    	    		   }
    	</c:if>	        
    	<c:set var="preops" value="${curops}"/>
	    </c:forEach>
	    <c:if test="${!empty preops }">
	    	]}
	    </c:if>
	   ]; 
	   
// we can just use the fix array too.
var rowids = new Array();
var currencyIdArr = new Array();
var remittanceId = new Array();
var currencyId = new Array();

currencyIdArr[currencyIdArr.length] = {value:'',text:'<fmt:message key="label.pleaseselect"/>'};
for( i=0;i < opspg.length; i++) { // opshubig
	currencyIdArr[currencyIdArr.length] = {text:opspg[i].name,value:opspg[i].id}
}

var pgdefaults = {
		   ops: {id:'',name:'<fmt:message key="label.myentities"/>',nodefault:true},
	   	   pg: {id:'',name:'<fmt:message key="label.pleaseselect"/>'}
	   }

function buildRemittanceId(opsid,priceid) {
//	var opsid = $v('opsEntityId');
	var newInvArray = new Array();
	newInvArray[newInvArray.length] = {text:pgdefaults.pg.name,value:pgdefaults.pg.id};
	for( i=0;i < opspg.length; i++) {
		if( opspg[i].id == opsid ) {
			var hubcoll = opspg[i].coll;
			for( j = 0;j< hubcoll.length;j++ ){
				newInvArray[newInvArray.length] = {text:hubcoll[j].name,value:hubcoll[j].id};
			}	
		}
	}
	return newInvArray;
}

<c:forEach var="bean" items="${customerOpsEntityCurrencyColl}" varStatus="status">
	rowids[${status.index +1}] = ${status.index +1};
	currencyId[${status.index +1}] = currencyIdArr;
	remittanceId[${status.index +1}] = buildRemittanceId('${bean.currencyId}','${bean.remittanceId}');
</c:forEach>

            
// -->
</script>

</head>

<body bgcolor="#ffffff" onload="resultOnLoad()">
<tcmis:form action="/customervalidcurrencyresults.do" onsubmit="return submitFrameOnlyOnce();">
<div id="errorMessagesAreaBody" style="display:none;">
    <html:errors/>
    ${tcmISError}
    <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
    </c:forEach>        
</div>

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
<div class="interface" id="resultsPage">
<div class="backGroundContent">

<div id="currencyBean" style="width:100%;height:600px;" style="display: none;"></div>

<script type="text/javascript">
<!--
<c:set var="showUpdateLink" value='N'/>
<tcmis:opsEntityPermission indicator="true" userGroupId="CustomerDetailUpdate"> 
	<c:set var="showUpdateLink" value='Y'/>
</tcmis:opsEntityPermission>
<tcmis:opsEntityPermission indicator="true" userGroupId="CustomerDetailAdmin">   
   	<c:set var="showUpdateLink" value='Y'/>
</tcmis:opsEntityPermission>

<c:set var="dataCount" value='${1}'/>
<c:if test="${!empty customerOpsEntityCurrencyColl}" >  
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="currencyBean" items="${customerOpsEntityCurrencyColl}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>

// convert the value of a column to checked 
<c:set var="defaultCurrency" value="false"/>
<c:if test="${status.current.defaultCurrency == 'Y'}"><c:set var="defaultCurrency" value="true"/></c:if>
 
/* set page permission here */
<c:set var="readonly" value='N'/>
<tcmis:opsEntityPermission indicator="true" userGroupId="CustomerDetailUpdate"> 
 	<c:set var="readonly" value='Y'/>
</tcmis:opsEntityPermission>
<tcmis:opsEntityPermission indicator="true" userGroupId="CustomerDetailAdmin">   
   	<c:set var="readonly" value='Y'/>
</tcmis:opsEntityPermission>

<fmt:formatDate var="fmtEnteredDate" value="${status.current.enteredDate}" pattern="${dateFormatPattern}"/>

/*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},
 data:['${readonly}','N','${readonly}',
  '${status.current.currencyId}',
  '${status.current.remittanceId}',
  ${defaultCurrency}, ${false},
  '<tcmis:jsReplace value="${status.current.enteredByName}"/>',
  '${fmtEnteredDate}','${status.current.enteredDate.time}'
  ]}  
 <c:set var="dataCount" value='${dataCount+1}'/> 
 </c:forEach>
]};
</c:if>
//-->  
</script>

<!-- If the collection is empty say no data found -->
<c:if test="${empty customerOpsEntityCurrencyColl}">
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
    <tr>
       <td width="100%">
          <fmt:message key="main.nodatafound"/>
       </td>
    </tr>
  </table>
</c:if>
        
<!-- Search results end -->

<!-- Hidden element start --> 
<div id="hiddenElements" style="display: none;">    			
	<input name="totalLines" id="totalLines" value="<c:out value="${dataCount}"/>" type="hidden">
    <input name="uAction" id="uAction" value="" type="hidden">    
    <input name="personnelName" id="personnelName" type="hidden" value="${personnelBean.lastName}, ${personnelBean.firstName}"> 
    <input name="toDate" id="toDate" type="hidden" value='<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>'>   
    <input name="minHeight" id="minHeight" type="hidden" value="108">
      
  <!-- Put all the Search Criteria here for re-search after update-->
    <input name="opsEntityId" id="opsEntityId" type="hidden" value="${param.opsEntityId}">
    <input name="customerId" id="customerId" type="hidden" value="${param.customerId}"> 
</div>
<!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->

<c:if test="${showUpdateLink == 'Y'}">
    <script type="text/javascript">
        <!--
        showUpdateLinks = true;
        //-->
    </script>
</c:if>

</tcmis:form>
</body>
</html:html>