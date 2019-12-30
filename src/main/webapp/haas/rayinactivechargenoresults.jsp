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

<c:set var="ugid" value='invalidDirectedCharge'/>
<c:if test="${param.inactiveChargeNumber eq 'Y' }">
	<c:set var="ugid" value='invalidDirectedCharge'/>
</c:if>

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
<c:if test="${param.inactiveChargeNumber ne 'Y'}" >
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
   
   <c:if test="${param.inactiveChargeNumber ne 'Y'}" >
   if( showUpdateLinks ) {
	   toggleContextMenu("addBin");
   }
   </c:if>
   
   //alert("${param.hubName}");
}
var config = [
<c:if test="${param.inactiveChargeNumber eq 'Y'}" >
{ columnId:"permission",
	  columnName:''
	},
	{ columnId:"c1",
	  columnName:'<fmt:message key="label.inactivedate"/>'
	},
	{ columnId:"c2",
	  columnName:'<fmt:message key="label.chargeid"/>'
	},
	{ columnId:"c3",
	  columnName:'<fmt:message key="label.tradingpartner"/>'
	},
		{ columnId:"c4",
		  columnName:'<fmt:message key="label.chargenumber"/>'
		},
		{ columnId:"c5",
		  columnName:'<fmt:message key="label.ponumber"/>'
		},
		{ columnId:"c6",
	 	  columnName:'<fmt:message key="label.poline"/>'
		},
	{ columnId:"c7",
	  columnName:'<fmt:message key="label.radianpo"/>'
	},
	{ columnId:"c8",
		  columnName:'<fmt:message key="label.mrnumber"/>'
	},
	{ columnId:"c81",
		  columnName:'<fmt:message key="label.lineitem"/>'
	},
	{ columnId:"c82",
		  columnName:'<fmt:message key="label.status"/>'
	},
	{ columnId:"c9",
		  columnName:'<fmt:message key="label.facilityid"/>'
	},
	{ columnId:"c10",
		  columnName:'<fmt:message key="label.deliverytype"/>'
	},
	{ columnId:"c11",
		  columnName:'<fmt:message key="label.releasedate"/>'
	}
</c:if>
/*
CHARGE_NUMBER_1                                    VARCHAR2(30)
TRADING_PARTNER                                    VARCHAR2(4)
FACILITY_ID                                        VARCHAR2(30)
ACCOUNT_SYS_ID                                     VARCHAR2(20)
CHARGE_TYPE                                        CHAR(1)
APPLICATION                                        VARCHAR2(30)
BANNED                                             CHAR(1)
PERCENT                                            NUMBER
CHARGE_NUMBER_2                                    VARCHAR2(30)
COMMODITY_TYPE                                     VARCHAR2(10)
CAT_PART_NO                                        VARCHAR2(30)
PART_GROUP_NO                                      NUMBER
*/
<c:if test="${param.inactiveChargeNumber ne 'Y'}" >
{ columnId:"permission",
	  columnName:''
	},
	{ columnId:"c1",
	  columnName:'<fmt:message key="label.chargenumber1"/>'
	},
	{ columnId:"c2",
	  columnName:'<fmt:message key="label.tradingpartner"/>'
	},
	{ columnId:"c3",
	  columnName:'<fmt:message key="label.facilityid"/>'
	},
		{ columnId:"c4",
		  columnName:'<fmt:message key="label.accountsysid"/>'
		},
		{ columnId:"c5",
		  columnName:'<fmt:message key="label.chargetype"/>'
		},
		{ columnId:"c6",
	 	  columnName:'<fmt:message key="label.application"/>'
		},
	{ columnId:"c7",
	  columnName:'<fmt:message key="label.banned"/>'
	},
	{ columnId:"c8",
		  columnName:'<fmt:message key="label.percent"/>'
	},
	{ columnId:"c81",
		  columnName:'<fmt:message key="label.chargenumber2"/>'
	},
/*	{ columnId:"c82",
		  columnName:'<fmt:message key="label.chargenumber1"/> <fmt:message key="transactions.source"/>'
	},
	{ columnId:"c9",
		  columnName:'<fmt:message key="label.chargenumber2"/> <fmt:message key="transactions.source"/>'
	},
	*/
	{ columnId:"c10",
		  columnName:'<fmt:message key="label.commodityType"/>'
	},
	{ columnId:"c11",
		  columnName:'<fmt:message key="label.catpartno"/>'
	},
	{ columnId:"c12",
		  columnName:'<fmt:message key="label.partgroupnumber"/>'
	}
</c:if>
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
	
<%--
		name='addBin<c:out value="${rowcount}"/>' value="+" onclick='showVvHubBins("<c:out value="${receipt.itemId}"/>","<c:out value="${param.hub}"/>",<c:out value="${rowcount}"/>)'>
--%>
	
// -->
</script>
</head>

<body bgcolor="#ffffff" onload="resultOnLoadWithGrid(_gridConfig);">

<tcmis:form action="/rayinactivechargenoresult.do" onsubmit="return submitFrameOnlyOnce();">

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
<c:if test="${empty dataColl}" >
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
<c:if test="${!empty dataColl}" >
  <script type="text/javascript">
  /*This is to keep track of whether to show any update links.
    If the user has any update permisions for any row then we show update links.*/
    // set up bin array .
// I have decided to use facilityPermission for both situations.
	
  <c:set var="dataCount" value='${0}'/>
  /*Storing the data to be displayed in a JSON object array.*/
  var jsonData = new Array();
  <c:if test="${ugid eq 'inactiveChargeNumber' }">
  var jsonData = { 
		  rows:[    
		  <c:forEach items="${dataColl}">
		  	<c:if test="${dataCount > 0}">,</c:if>
		  	<fmt:formatDate var="inactiveDate" value="${dataColl[dataCount][0]}" pattern="${dateFormatPattern}"/>	
		  	<fmt:formatDate var="releaseDate" value="${dataColl[dataCount][12]}" pattern="${dateFormatPattern}"/>	
		   	{ 
		   	id:${dataCount +1},
		   	data:[ 
		   	'Y',
		  '${inactiveDate}',
		  '${dataColl[dataCount][1]}',
		  '${dataColl[dataCount][2]}',
		  '${dataColl[dataCount][3]}',
		  '${dataColl[dataCount][4]}',
		  '${dataColl[dataCount][5]}',
		  '${dataColl[dataCount][6]}',
		  '${dataColl[dataCount][7]}',
		  '${dataColl[dataCount][8]}',
		  '${dataColl[dataCount][9]}',
		  '${dataColl[dataCount][10]}',
		  '${dataColl[dataCount][11]}',
		  '${releaseDate}'
		   	<%--
		  		'Y'
		  		<c:forEach var="obj" items="${dataColl[dataCount]}">
		  		,'${obj}'
		  		</c:forEach>
		  		--%>
		   	]}
		   	<c:set var="dataCount" value='${dataCount+1}'/>
		  </c:forEach>
		  ]};
  </c:if>
  <%-- new option --%>
  <c:if test="${ugid eq 'invalidDirectedCharge' }">
  var jsonData = { 
		  rows:[    
		  <c:forEach items="${dataColl}">
		  	<c:if test="${dataCount > 0}">,</c:if>
		   	{ 
		   	id:${dataCount +1},
		   	data:[ 
		  		'Y'
		  		<c:forEach var="obj" items="${dataColl[dataCount]}">
		  		,'${obj}'
		  		</c:forEach>
		   	]}
		   	<c:set var="dataCount" value='${dataCount+1}'/>
		  </c:forEach>
		  ]};
  </c:if>

  </script>
</c:if>       
<!-- If the collection is empty say no data found -->

<!-- Search results end -->

<!-- Hidden element start -->
<div id="hiddenElements" style="display: none;">
  <input name="totalLines" id="totalLines" value="${dataCount}" type="hidden"/>
  <%--  <tcmis:saveRequestParameter/>Need to store search input options here. This is used to re-do the original search upon updates etc.--%>    
  <input name="uAction" id="uAction" value="" type="hidden"/>
  <input name="inactiveChargeNumber" id="inactiveChargeNumber" type="hidden" value="${param.inactiveChargeNumber}"/>
  
  <!--This sets the start time for time elapesed.
  common for anykind of hub ig drop down.
    <input name="hub" id="hub" type="hidden" value="${param.hub}"/>
  <input name="hubName" id="hubName" type="hidden" value="${param.hubName}"/>
  -->
  <input name="startSearchTime" id="startSearchTime" type="hidden" value="${startSearchTime}"/>
  <input name="endSearchTime" id="endSearchTime" type="hidden" value="${endSearchTime}"/>
  <input name="minHeight" id="minHeight" type="hidden" value="100"/>

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
