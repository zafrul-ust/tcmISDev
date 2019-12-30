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
This looks at what the users preffered font size and which application he is viewing to set the correct CSS. -->
<tcmis:gridFontSizeCss />
<!-- Add any other stylesheets you need for the page here -->

<script type="text/javascript" src="/js/common/formchek.js"></script>
<script type="text/javascript" src="/js/common/commonutil.js"></script>
<%--NEW - grid resize--%>
<script type="text/javascript" src="/js/common/grid/nosearchresize.js"></script>
<script type="text/javascript" src="/js/common/grid/resultiframegridresize.js"></script>
<!-- This handels which key press events are disabeled on this page -->
<script src="/js/common/disableKeys.js"></script>

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
<!-- These are for the Grid-->

<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgrid.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxgridcell.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_form.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_srnd.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/ext/dhtmlxgrid_json.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/excells/dhtmlxgrid_excell_customized.js"></script>
<script type="text/javascript" src="/dhtmlxGrid/codebase/dhtmlxcommon_haas.js"></script>

<title>
Customer Return Results.
</title>

<script language="JavaScript" type="text/javascript">
<!--
//add all the javascript messages here, this for internationalization of client side javascript messages
var messagesData = new Array();
messagesData={alert:"<fmt:message key="label.alert"/>",
                and:"<fmt:message key="label.and"/>",
        recordFound:"<fmt:message key="label.recordFound"/>",
     searchDuration:"<fmt:message key="label.searchDuration"/>",
            minutes:"<fmt:message key="label.minutes"/>",
            seconds:"<fmt:message key="label.seconds"/>",
        validvalues:"<fmt:message key="label.validvalues"/>",
     submitOnlyOnce:"<fmt:message key="label.submitOnlyOnce"/>", 
        qtyRetInteger:"<fmt:message key="error.quantity.integer"/>",
        qtyReturnNotLess:"<fmt:message key="error.customerreturns.qtyreturnednotless"/>",
        createnewbin:"<fmt:message key="label.createnewbin"/>",
        pleaseSelectARow:"<fmt:message key="label.pleaseselectarowforupdate"/>"
        };

function receiveReturns() 
{
	if (validateReceive())
	{
    	/*Set any variables you want to send to the server*/
    	$('uAction').value = 'receiveReturns';
    	parent.showPleaseWait();
    	haasGrid.parentFormOnSubmit(); //prepare grid for data sending
    	/*Submit the form in the result frame*/
   		document.genericForm.submit();
 	}
}

function validateReceive() 
{
   var resultForm = document.genericForm;

  //var mydoc = parent.document.frame[1];
//  var okName = 'resultForm.ok';
 // var qtyName = 'resultForm.quantity';
  //var qtyRetName = 'resultForm.quantityReturned';
  //var dorName = 'resultForm.dor';
  var totalLines = $v("totalLines")
  // alert(" totalLines  "+totalLines+"");
  var okCheckedCount = 0;
  
//  alert( resultFrame.cellValue(1,"replacementMaterial") +":" + resultFrame.cellValue(1,"ok"));
  for (i=1; i <= totalLines; i++) 
  {
//    okCheckbox = eval(okName + i);
//    if (okCheckbox.checked) 
//	alert( resultFrame.cellValue(i,"ok") + resultFrame.cellValue(i,"quantity"));
	if ( "true" == cellValue(i,"ok") || "Y" == cellValue(i,"ok"))
    {
		okCheckedCount++;
      	qtyInputbox = cellValue(i,"quantity")
      	qtyRetInputbox = cellValue(i,"quantityReturned");
<c:if test="${param.returnForCredit ne 'Y'}" >
	if (! cellValue(i,"bin") ) 
  	{
		alert('<fmt:message key="label.pleaseselect"/> : <fmt:message key="label.bin"/>');
    	return false;
  	}
</c:if>
//      dorInputbox = eval(dorName + i);
      	if (!isInteger(qtyRetInputbox.trim(),false)) 
      	{
        	alert(messagesData.qtyRetInteger);
        	return false;
      	}
/*      if (!checkdate(dorInputbox)) 
      {
        alert(messagesData.dorDate);
        return false;
      }   */
      // check to see the Quantity Returned < Quantity
      	if (parseInt(qtyRetInputbox.trim()) > parseInt(qtyInputbox.trim())) 
      	{
        	alert(messagesData.qtyReturnNotLess);
        	return false;
      	}
    }
  }
  if (okCheckedCount > 0)
  {
     return true;
  }
  else
  {
     //alert("Please select..");
     alert(messagesData.pleaseSelectARow);
     return false;
  }
}

_gridConfig.onRowSelect = selectRow;
_gridConfig.onRightClick = selectRow;
_gridConfig.submitDefault = true;

var selectedRowId = null;
function selectRow()
{
// to show menu directly
   rightClick = false;
   rowId0 = arguments[0];
   colId0 = arguments[1];
   ee     = arguments[2];

   selectedRowId = rowId0;
   if( ee != null ) { 
   		if( ee.button != null && ee.button == 2 ) rightClick = true;
   		else if( ee.which == 3  ) rightClick = true;
   }
   
   <c:if test="${param.returnForCredit ne 'Y'}" >
   if( showUpdateLinks ) {
	   toggleContextMenu("addBin");
   }
   </c:if>
   
   //alert("${param.hubName}");
}


var config = [
{ columnId:"permission"
},
{ columnId:"ok",
	columnName:'<fmt:message key="label.ok"/>',
  type:"hchstatus",
  width:4
},
{ columnId:"companyId",
  columnName:'<fmt:message key="label.company"/>',
  width:8
},
{ columnId:"facilityId",
	columnName:'<fmt:message key="label.facility"/>',
  width:8
},
{ columnId:"mrline",
	columnName:'<fmt:message key="label.mrline"/>*',
  width:8
},
{ columnId:"itemId",
	columnName:'<fmt:message key="label.item"/>',
  width:8
},
{ columnId:"catPartNo",
	columnName:'<fmt:message key="label.part"/>',
  width:10
},
{ columnId:"receiptId",
	columnName:'<fmt:message key="label.receiptid"/>*',
  width:8
},
{ columnId:"mfgLot",
	columnName:'<fmt:message key="label.mfglot"/>',
  width:8
},
{ columnId:"quantity",
	columnName:'<fmt:message key="label.qty"/>',
	align:'middle',
  width:5
},
<c:if test="${param.returnForCredit ne 'Y'}" >
{ columnId:"bin",
	columnName:'<fmt:message key="label.returninfo"/>',
//  	columnName:'#cspan',
  	attachHeader:'<fmt:message key="label.bin"/>',
  	type:'hcoro',
  width:10
},
{ columnId:"dor"
  ,columnName:'#cspan',
  attachHeader:'<fmt:message key="receivedreceipts.label.dor"/>',
  type:'hcal', 
  width:8,
  align:'center'
},
{ columnId:"quantityReturned",
	columnName:'#cspan',
	  attachHeader:'<fmt:message key="label.qtyreturned"/>',
	type:'hed',
	align:'middle',
  width:10
},
</c:if>
<c:if test="${param.returnForCredit eq 'Y'}" >
{ columnId:"quantityReturned",
	columnName:'<fmt:message key="label.returninfo"/>',
	attachHeader:'<fmt:message key="label.qtyreturned"/>',
	type:'hed',
  width:10
},
{ columnId:"shipmentId",
	columnName:'#cspan',
	  attachHeader:'<fmt:message key="label.shipmentid"/>',
  width:10
},
{ columnId:"dateShipped",
	columnName:'#cspan',
	  attachHeader:'<fmt:message key="label.dateshipped"/>',
  width:10
},
{ columnId:"issueId",
	columnName:'#cspan',
	  attachHeader:'<fmt:message key="label.issueid"/>',
  width:10
},
{ columnId:"replacementMaterial",
  columnName:'#cspan',
  attachHeader:'<fmt:message key="label.replacementmaterial"/>',
  type:"hch",
  width:8,
  align:'center'
},
</c:if>
{ columnId:"prNumber"
},
{ columnId:"lineItem"
},
{ columnId:"orderQuantity"
}
];

with(milonic=new menuname("addBin")){
	 top="offset=2"
	 style = contextStyle;
	 margin=3

	 aI("text=<fmt:message key="receiving.button.newbin"/>;url=javascript:addBin();");
<tcmis:inventoryGroupPermission indicator="true" userGroupId="addNewBin" facilityId="${param.hubName}">
	 aI("text=" + messagesData.createnewbin + ";url=javascript:createNewBin();" );
</tcmis:inventoryGroupPermission>
	}

	drawMenus();
	
	function getBin(value,text,rowid) {
		   obj = document.getElementById("bin"+rowid);
		   var index = obj.length;
		   obj.options[index]=new Option(text,value);
		   obj.options[index].selected = true; 
	}
	
	function addBin(){
		{
			var loc = "/tcmIS/hub/showhubbin.do?callbackparam="+selectedRowId+"&branchPlant=" + $v('hub') + "&userAction=showBins";
			var winname = null;
			try {
				winname = openWinGeneric(loc, "showVvHubBins", "300", "150", "no", "80", "80");
				children[children.length] = winname;
				} catch (ex) {
//				openWinGeneric(loc, "showVvHubBins", "300", "150", "no", "80", "80");
			}
		}
	}

// it's kind of misleading, it's expecting hub id not hub name.	
	function createNewBin()
	{
	  var newbinURL = "/tcmIS/Hub/AddNewBin?";
	  newbinURL = newbinURL + "HubName=" + '${param.hub}';
	  openWinGeneric(newbinURL,"add_newbin","400","200","Yes")
	}
	
	function doUnConfPopup()
	{
<c:if test="${ param.uAction eq 'receiveReturns' }">
	  var unconfURL = "/tcmIS/Hub/ShowUnconfirmedReceipts?session=Active";
	  unconfURL = unconfURL + "&HubNo=" + '${param.hub}'
	  unconfURL = unconfURL + "&customownd=yes";
	  unconfURL = unconfURL + "&genLabels=1";
	  openWinGeneric(unconfURL,"Generate_customer_returns_labels","640","600","yes")
</c:if>
	}
	
<%--
		name='addBin<c:out value="${rowcount}"/>' value="+" onclick='showVvHubBins("<c:out value="${receipt.itemId}"/>","<c:out value="${param.hub}"/>",<c:out value="${rowcount}"/>)'>
--%>
	
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(_gridConfig);doUnConfPopup();">

<tcmis:form action="/customerreturnsresults.do" onsubmit="return submitFrameOnlyOnce();">

<!-- You can build your error messages below. But we want to trigger the pop-up from the main page.
So this is just used to feed the pop-up in the main page.
Similar divs would have to be built to show any other messages in a pop-up.-->
<!-- Error Messages Begins -->
<div id="errorMessagesAreaBody" style="display:none;">
  <c:if test="${! empty tcmISError}">
  	${tcmISError}<br/>
  </c:if>
  <c:forEach items="${tcmISErrors}" varStatus="status">
  	${status.current}<br/>
  </c:forEach>
<c:if test="${! empty custOwnedReceiptColl}">
  <fmt:message key="receiving.successmessage"/>
  Receipts:<br/>
  <c:forEach items="${custOwnedReceiptColl}" var="ownedReceipt">
     <c:out value="${ownedReceipt}"/><br/>
  </c:forEach>
</c:if>
<c:if test="${! empty custOwnedErrorColl}">
  Errors:<br/>
  <c:forEach items="${custOwnedErrorColl}" var="ownedError">
     <c:out value="${ownedError}"/><br/>
  </c:forEach>
</c:if>
</div>

<script type="text/javascript">
/*Check if there is any error messages to show and set the variable you use in javascript to true or false.*/
	showErrorMessage = false;
<c:if test="${! empty tcmISError || ! empty custOwnedReceiptColl || ! empty custOwnedErrorColl}"> 
    showErrorMessage = true;
 </c:if>

</script>
<!-- Error Messages Ends -->

<div class="interface" id="resultsPage">

<div class="backGroundContent">    
<c:if test="${empty returnReceiptColl}" >
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableNoData" id="resultsPageTable">
   <tr>
    <td width="100%">
      <fmt:message key="main.nodatafound"/>
    </td>
   </tr>
  </table>
</c:if>
<!--Give the div name that holds the grid the same name as your viewbean or dynabean you want for updates-->
<div id="beanData" style="width:100%;height:600px;" style="display: none;"></div>
<!-- Search results start -->
<c:if test="${!empty returnReceiptColl}" >
  <script type="text/javascript">
  /*This is to keep track of whether to show any update links.
    If the user has any update permisions for any row then we show update links.*/
    // set up bin array .
<c:set var="ugid" value='customerReturns'/>
<c:if test="${param.returnForCredit eq 'Y' }">
	<c:set var="ugid" value='returnForCredit'/>
</c:if>
	var bin = new Array();
    var binArr = null;
    <c:forEach var="receipt" items="${returnReceiptColl}" varStatus="status">
		binArr = new Array(
				{text:'<fmt:message key="label.none"/>',value:''}
		<c:forEach var="binBean" items="${receipt.receiptItemPriorBinViewBeanCollection}" varStatus="status2">
				,{text:'${binBean.bin}',value:'${binBean.bin}'}
		</c:forEach>
		);
    	bin[${status.index +1}] = binArr;
    </c:forEach>    	
	
  <c:set var="dataCount" value='${0}'/>
  /*Storing the data to be displayed in a JSON object array.*/
  <c:set var="today"><tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/></c:set>
  var jsonData = new Array();
  var jsonData = {
  rows:[
  <c:forEach var="receipt" items="${returnReceiptColl}" varStatus="status">
    <c:if test="${status.index > 0}">,</c:if>
    <fmt:formatDate var="dateShipped" value="${status.current.shipConfirmDate}" pattern="${dateFormatPattern}"/>
 	<c:set var="rowperm" value='N'/>
	<tcmis:inventoryGroupPermission indicator="true" userGroupId="${ugid}" companyId="${personnelBean.companyId}" facilityId="All" inventoryGroup="${receipt.inventoryGroup}">
	 <c:set var="showUpdateLink" value='Y'/>
	 <c:set var="rowperm" value='Y'/>
	</tcmis:inventoryGroupPermission>
    {    id:${status.index +1},
        data:['${rowperm}',
              false,
              '${receipt.companyId}',
              '${receipt.facilityId}',
              '${receipt.prNumber}-${receipt.lineItem}',
              '${receipt.itemId}',
              '${receipt.catPartNo}',
              '${receipt.receiptId}',
              '<tcmis:jsReplace value="${receipt.mfgLot}" processMultiLines="true"/>',
              '${receipt.quantity}',
              <c:if test="${param.returnForCredit ne 'Y'}" >
			    '${receipt.bin}',
                '${today}',
              </c:if>
              '',
              <c:if test="${param.returnForCredit eq 'Y'}" >
                '${receipt.shipmentId}',
                '${dateShipped}',
                '${receipt.issueId}',
                "",
              </c:if>              
              '${receipt.prNumber}',
              '${receipt.lineItem}',
              '${receipt.orderQuantity}'
              ]}
    <c:set var="dataCount" value='${dataCount+1}'/>
  </c:forEach>
  ]};
  </script>
</c:if>       
<!-- If the collection is empty say no data found -->

<!-- Search results end -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
  <input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
  <%--  <tcmis:saveRequestParameter/>Need to store search input options here. This is used to re-do the original search upon updates etc.--%>    
  <input name="uAction" id="uAction" value="" type="hidden"/>
  <input name="inventoryGroup" id="dodaacType" type="hidden" value="${param.inventoryGroup} "/>
  <input name="receiptId" id="receiptId" type="hidden" value="${param.receiptId}"/>
  <input name="hub" id="hub" type="hidden" value="${param.hub}"/>
  <input name="mrNumber" id="mrNumber" type="hidden" value="${param.mrNumber}"/>
  <input name="returnForCredit" id="returnForCredit" type="hidden" value="${param.returnForCredit}"/>
  <input name="catPartNo" id="catPartNo" type="hidden" value="${param.catPartNo}"/>
  <input name="facilityId" id="facilityId" type="hidden" value="${param.facilityId}"/>
  <input name="itemId" id="itemId" type="hidden" value="${param.itemId}"/>
  <input name="sortBy" id="sortBy" type="hidden" value="${param.sortBy}"/>
  
  <!--This sets the start time for time elapesed.-->
  <input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}"/>
  <input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>
  <input name="minHeight" id="minHeight" type="hidden" value="100"/>
  					<input type="hidden" name="blockBefore_dor" id="blockBefore_dor" value=""/>
					<input type="hidden" name="blockAfter_dor" id="blockAfter_dor" value=""/>
					<input type="hidden" name="blockBeforeExclude_dor" id="blockBeforeExclude_dor" value=""/>
					<input type="hidden" name="blockAfterExclude_dor" id="blockAfterExclude_dor" value="<tcmis:getDateTag numberOfDaysFromToday="0" datePattern="${dateFormatPattern}"/>"/>
					<input type="hidden" name="inDefinite_dor" id="inDefinite_dor" value=""/>
  
  <!-- Popup Calendar input options for hcal column Type in the grid-->
  <!--
  <input type="hidden" name="blockBefore_columnId" id="blockBefore_columnId" value=""/>
  <input type="hidden" name="blockAfter_columnId" id="blockAfter_columnId" value=""/>
  <input type="hidden" name="blockBeforeExclude_columnId" id="blockBeforeExclude_columnId" value=""/>
  <input type="hidden" name="blockAfterExclude_columnId" id="blockAfterExclude_columnId" value=""/>
  <input type="hidden" name="inDefinite_columnId" id="inDefinite_columnId" value=""/>
  -->

  <%--This is the minimum height of the result section you want to display--%>
  <input name="minHeight" id="minHeight" type="hidden" value="0">
</div>
<!-- Hidden elements end -->

 <%-- result count and time --%>
 <div id="footer" class="messageBar"></div>

</div> <!-- backGroundContent -->

</div> <!-- close of interface -->

<!-- If the user has permissions and needs to see the update links,set the variable showUpdateLinks javascript to true.
     The default value of showUpdateLinks is false.
-->
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
