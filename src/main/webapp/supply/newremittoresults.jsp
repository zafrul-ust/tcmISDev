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

<!-- For Calendar support 

<script type="text/javascript" src="/js/calendar/newcalendar.js"></script>
<script type="text/javascript" src="/js/calendar/AnchorPosition.js"></script>
<script type="text/javascript" src="/js/calendar/PopupWindow.js"></script>
-->
<!-- Add any other javascript you need for the page here -->
<script type="text/javascript" src="/js/supply/newremittoresults.js"></script>

<!-- These are for the Grid, uncomment if you are going to use the grid -->
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
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
<fmt:message key="newremitto.title"/>
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
sapvendorcode:"<fmt:message key="label.sapvendorcode"/>",
or:"<fmt:message key="label.or"/>",
newCol:"<fmt:message key="label.new"/>",
itemInteger:"<fmt:message key="error.item.integer"/>"};

var okName;
var sapcodeColName;
var statusColName;

<tcmis:facilityPermission indicator="true" userGroupId="newRemitTo">
<c:set var="showUpdateLink" value='Y'/>
</tcmis:facilityPermission>

<c:if test="${showUpdateLink == 'Y'}">
showUpdateLinks = true;
</c:if>


if(!showUpdateLinks)
{
	okName = '';
	sapcodeColName ='';
	statusColName ='';
}
else
{
	okName = '<fmt:message key="label.ok"/>';
	sapcodeColName ='<fmt:message key="label.sapvendorcode"/>';
	statusColName ='<fmt:message key="label.new"/>';
}
var config = [
{ columnId:"permission",
  columnName:''
	  
},              
{ columnId:"supplierName",  
  columnName:'<fmt:message key="label.supplier"/>',
  width:16
},
{ columnId:"address",
  columnName:'<fmt:message key="label.address"/>',
  width:20,
  tooltip:"Y"	  
},
{ columnId:"supplierInvoiceIds",
  columnName:'<fmt:message key="label.invoicereference"/>',
  tooltip:"Y",
  submit:true,
  width:10  		  
},
{ columnId:"requesterName",
  columnName:'<fmt:message key="label.requestor"/>',
  tooltip:"Y",
  width:10
},
{ columnId:"requesterEmail",
  columnName:'<fmt:message key="label.requestoremail"/>',
  tooltip:"Y",
  width:10  		  
},
{ columnId:"requestDate",
  columnName:'<fmt:message key="label.requestdate"/>',
  width:8 		  
},
{ columnId:"comments", 
  tooltip:"Y"
},
{ columnId:"ok",
  columnName:okName,
  type:"hch",
  sorting:"Haashch",
  submit:true,
  width:4
},

{ columnId:"tempSapVendorCode",
  columnName:sapcodeColName,
  width:12  
},
{ columnId:"statusCol",
  columnName:statusColName, 
  type:"hch",
  sorting:"Haashch",
  submit:true,
  width:4
},
{ columnId:"status"      
},
{ columnId:"billingLocationId",
  columnName:'<fmt:message key="label.location"/>',
  submit:true
},
{ columnId:"supplier",
  submit:true
},
{ columnId:"sapVendorCode",  
  submit:true
}
];

function setHlink() {
	 if( typeof( jsonMainData ) == 'undefined' ) return; 
	 	 
	  var rows =jsonMainData.rows;
	  for( var i = 0; i < rows.length; i++ ) 
	 {			
		if(rows[i].data[0]=='Y')
	   {	
	  	rows[i].data[9] ="<input class='inputBox' type='text' id='sapvendorcode" + (i+1) +  "' value='' size='10' readonly/>&nbsp;&nbsp;<input type='button' class='lookupBtn'  onmouseover=\"this.className='lookupBtn lookupBtnOver'\"   onmouseout=\"this.className='lookupBtn'\" name=\"sapvendorSelector"+(i+1)+"\" value=\"...\" align=\"right\" onClick=\"getVendorCode($('sapvendorcode"+(i+1)+"'),"+(i+1)+");\"/>";
	   
	   }
	
	 }	 	 	 
	}
	
with(milonic=new menuname("reject")){
         top="offset=2"
         style = contextStyle;
         margin=3
         aI("text=<fmt:message key="label.reject"/>;url=javascript:changeStatusRejected();"); 
    }

drawMenus();
// -->

</script>
</head>

<body bgcolor="#ffffff" onload="setHlink();resultOnLoad();">

<tcmis:form action="/newremittoresults.do" onsubmit="return submitFrameOnlyOnce();">

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
<!--


/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
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

<div id="supplierBillingLocViewBean" style="width:100%;height:400px;" style="display: none;"></div>

<c:if test="${supplierBillingLocViewBeanColl != null}">
<script type="text/javascript">
<!--


<tcmis:facilityPermission indicator="true" userGroupId="newRemitTo">
<c:set var="showUpdateLink" value='Y'/>
</tcmis:facilityPermission>


<c:set var="dataCount" value='${0}'/>
<c:if test="${!empty supplierBillingLocViewBeanColl}">
var jsonMainData = new Array();
var jsonMainData = {
rows:[
<c:forEach var="supplierBillingLoc" items="${supplierBillingLocViewBeanColl}" varStatus="status">
<c:if test="${status.index > 0}">,</c:if>

<tcmis:jsReplace var="addressLine1" value="${status.current.addressLine1}"  processMultiLines="true"/>
<tcmis:jsReplace var="addressLine2" value="${status.current.addressLine2}"  processMultiLines="true"/>
<tcmis:jsReplace var="addressLine3" value="${status.current.addressLine3}"  processMultiLines="true"/>
<tcmis:jsReplace var="city" value="${status.current.city}"  processMultiLines="true"/>
<tcmis:jsReplace var="stateAbbrev" value="${status.current.stateAbbrev}"  processMultiLines="true"/>
<tcmis:jsReplace var="countryAbbrev" value="${status.current.countryAbbrev}"  processMultiLines="true"/>
<tcmis:jsReplace var="zip" value="${status.current.zip}"  processMultiLines="true"/>
<tcmis:jsReplace var="comments" value="${status.current.comments}"  processMultiLines="true"/>
<tcmis:jsReplace var="supplierName" value="${status.current.supplierName}"  processMultiLines="true"/>
<tcmis:jsReplace var="requester" value="${status.current.requester}"  processMultiLines="false"/>
<tcmis:jsReplace var="requesterEmail" value="${status.current.requesterEmail}"  processMultiLines="false"/>
<tcmis:jsReplace var="requesterName" value="${status.current.requesterName}"  processMultiLines="true"/>
<fmt:formatDate var="fmtRequestDate" value="${status.current.requestDate}" pattern="${dateFormatPattern}"/>
 /*The row ID needs to start with 1 per their design.*/
{ id:${status.index +1},
 data:['${showUpdateLink}',
       '${supplierName}',
       '${addressLine1} ${addressLine2} ${addressLine3} ${city} ${stateAbbrev} ${zip} ${countryAbbrev}','${status.current.supplierInvoiceIds}',
       '${requesterName}','${requesterEmail}','${fmtRequestDate}',
       '${comments}','','','','${status.current.status}','${status.current.billingLocationId}','${status.current.supplier}','','${billingLocationId}']}
 <c:set var="dataCount" value='${dataCount+1}'/>
 </c:forEach>
]};
</c:if>
//-->
</script>

<!-- If the collection is empty say no data found -->

<c:if test="${empty supplierBillingLocViewBeanColl}">
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
<input name="totalLines" id="totalLines" value="${dataCount}" type="hidden">
<input name="rowIndex" id="rowIndex" value="" type="hidden">
<input name="minHeight" id="minHeight" type="hidden" value="100">
<input name="action" id="action" type="hidden" value="">
<input name="searchField" id="searchField" type="hidden" value="${param.searchField}">
<input name="searchMode" id="searchMode" type="hidden" value="${param.searchMode}">
<input name="searchArgument" id="searchArgument" type="hidden" value="${param.searchArgument}">  
<!-- Store all search criteria in hidden elements, need this to requery the database after updates -->


 </div>
<!-- Hidden elements end -->

</div>
<!-- close of backGroundContent -->
</div>
<!-- close of interface -->

</tcmis:form>
</body>
</html:html>