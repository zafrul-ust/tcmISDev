/*
function hubchanged()
{
	var hubO = document.getElementById("sourceHub");
	var inventoryGroupO = document.getElementById("inventoryGroup");
	var selhub = hubO.value;

	for (var i = inventoryGroupO.length; i > 0;i--)
   {
      inventoryGroupO[i] = null;
   }
	showinvlinks(selhub);
	inventoryGroupO.selectedIndex = 0;
        igchanged();

}  */

function showinvlinks(selectedhub)
{
    var invgrpid = new Array();
    invgrpid = altinvid[selectedhub];

	 var invgrpName = new Array();
    invgrpName = altinvName[selectedhub];

    if(invgrpid.length == 0)
    {
      setCab(0,messagesData.all,"")
    }

    for (var i=0; i < invgrpid.length; i++)
    {
        setCab(i,invgrpName[i],invgrpid[i])
    }
}

function setCab(href,text,id)
{
    var optionName = new Option(text, id, false, false)
    var inventoryGroupO = document.getElementById("inventoryGroup");
	 inventoryGroupO[href] = optionName;
}

function igchanged()
{
	var igO = document.getElementById("inventoryGroup");
	var shipToO = document.getElementById("dock");
	var selig = igO.value;

	for (var i = shipToO.length; i > 0;i--)
    {
      shipToO[i] = null;
    }
    
    if(selig != null && selig.length>0) {
	  for (var i = shipToO.length; i > 0;i--) {
            shipToO[i] = null;
      }
	  showshiptolinks(selig);
    }
    else {
          showshiptolinks("");
    }
    shipToO.selectedIndex = 0;
}

function lookupCustomer() {    
  var loc = "customerlookupsearchmain.do?popup=Y&displayElementId=customerName&valueElementId=customerId";  
  
  if ($v("personnelCompanyId") == 'Radian') 
	  loc = "/tcmIS/distribution/" + loc;
  
  openWinGeneric(loc,"customerlookup","800","500","yes","50","50","20","20","no"); 
}

function clearCustomer()
{
    document.getElementById("customerName").value = "";
    document.getElementById("customerName").className = "inputBox";
    document.getElementById("customerId").value = "";
}

function customerChanged(id, name) {
	document.getElementById("customerName").className = "inputBox";
}

function showshiptolinks(selectedig)
{
    var shiptoid = new Array();
    shiptoid = altigid[selectedig];

    var shiptoName = new Array();
    shiptoName = altigName[selectedig];

    if(shiptoid != null && shiptoid.length > 0) {
      for (var i=0; i < shiptoid.length; i++) {
        setCab2(i,shiptoName[i],shiptoid[i])
      }
    }
    else {
      setCab2(0,messagesData.all,"")
    }
}

function setCab2(href,text,id)
{
    var optionName = new Option(text, id, false, false)
    var shiptoO = document.getElementById("dock");
	 shiptoO[href] = optionName;
}

/*
function openWinGeneric(destination,WinName,WinWidth, WinHeight, Resizable )
{
    windowprops = "toolbar=no,location=no,directories=no,menubar=yes,scrollbars=yes,status=no,top=200,left=200,width=" + WinWidth + ",height=" + WinHeight+",resizable=" + Resizable;
    preview = window.open(destination, WinName,windowprops);
}
*/
function searchCarrierInfo(parentSelectedRow,branchPlant) {
   var inventoryGroup = document.getElementById("inventoryGroup"+parentSelectedRow);
   var tmp = escape(inventoryGroup.value);
   var loc = "/tcmIS/Hub/ShipConfirm?UserAction=BuildCarrierInfoPage&parentSelectedRow="+parentSelectedRow+"&inventoryGroup="+tmp;
   openWinGeneric(loc,"searchCarrierInfo","800","500","yes","80","80")
}

function printInvoice() {
   var totallines = document.getElementById("totallines").value;
   totallines = totallines*1;
   if(totallines == 0) {
     alert(messagesData.selectOneShipment);
   }
   else {
	     var checked = "no";
	     var tmp = "";
	     var shipmentTmp = "";
	     var shippingId;
	     var invGrp;
	     var materialRequestOrigin;
	     var invoiceBy;

	     for (var p = 0 ; p < totallines ; p ++) {
	        var linecheck = document.getElementById("rowChk"+(p*1)+"");
			if(linecheck.checked) {
				if( checked == "yes" && (document.getElementById("materialRequestOrigin"+(p*1)+"").value == 'Order Entry' ||
                                         document.getElementById("materialRequestOrigin"+(p*1)+"").value == 'Intercompany MR') ) {
				       alert(messagesData.selectOnlyOneShipment);
				       return;
				}
			  	checked="yes";
		        shippingId = document.getElementById("shipmentId"+(p*1)+"");
		        invGrp = document.getElementById("inventoryGroup"+(p*1)+"").value;
		        materialRequestOrigin = document.getElementById("materialRequestOrigin"+(p*1)+"").value;
		        invoiceBy = document.getElementById("invoiceBy"+(p*1)+"").value;

		        if(invoiceBy == 'Order') {
		        	if(tmp == "") {
			            tmp = "?confirmedShipments=" + "" + shippingId.value;
			        }
			        else {
			            tmp = tmp + "," + shippingId.value;
			        }	
		        }
		        
		        if (invoiceBy == 'Shipment') {
		        	if(shipmentTmp == "") {
		        		shipmentTmp = "?confirmedShipments=" + "" + shippingId.value;
			        }
			        else {
			        	shipmentTmp = shipmentTmp + "," + shippingId.value;
			        }
		        }
			}
	     }

	     if(checked == "no") {
	       alert(messagesData.selectOneShipment);
	     }
	     else {
	    	 if (tmp.trim().length > 0 ) {
	    		 if( materialRequestOrigin == 'Order Entry' || materialRequestOrigin == 'Intercompany MR' ) {
		               var personnelId = document.getElementById("personnelId").value;
		               loc = "/HaasReports/report/printdistributioninvoice.do"+tmp+"&personnelId="+personnelId;
			    	   openWinGeneric(loc,"printInvoiceDocument","800","600","yes","50","50","20","20","no");
			    	   //return;
			     }    
	    	 }
	    	 
	    	 if (shipmentTmp.trim().length > 0 ) {  // new invoice link goes here
	   				loc = "/HaasReports/report/printshipmentinvoice.do?shipmentIds="+shipmentTmp;
	   				openWinGeneric(loc,"printCMSInvoiceDocument","800","600","yes","50","50","20","20","no");
	    	 }
       	    return;	    	 
	    }
  }
}

function consolidate() {
   var totallines = document.getElementById("totallines").value;
   totallines = totallines*1;
   if(totallines == 0) {
     alert(messagesData.selectOneShipment);
   }
   else {
	     var checked = "no";
	     var tmp = "";
	     var shippingId;
	     var invGrp;
	     var materialRequestOrigin;
	     
	     for (var p = 0 ; p < totallines ; p ++) {
	        var linecheck = document.getElementById("rowChk"+(p*1)+"");
			if(linecheck.checked) {
				if( checked == "yes" && (document.getElementById("materialRequestOrigin"+(p*1)+"").value == 'Order Entry' ||
                                         document.getElementById("materialRequestOrigin"+(p*1)+"").value == 'Intercompany MR') ) {
				       alert(messagesData.selectOnlyOneShipment);
				       return;
				}
			  	checked="yes";
		        shippingId = document.getElementById("shipmentId"+(p*1)+"");
		        invGrp = document.getElementById("inventoryGroup"+(p*1)+"").value;
		        materialRequestOrigin = document.getElementById("materialRequestOrigin"+(p*1)+"").value;
		        
		        if(tmp == "") {
		            tmp = "?shipmentIds=" + "" + shippingId.value;
		        }
		        else {
		            tmp = tmp + "," + shippingId.value;
		        }
			}
	     }

	     if(checked == "no") {
	       alert(messagesData.selectOneShipment);
	     }
	     else {
	       var loc = "printpackinglist.do" + tmp;
	       
	       if ($v("personnelCompanyId") == 'Radian') 
	    		  loc = "/tcmIS/hub/" + loc;
	       
	       if( materialRequestOrigin == 'Order Entry' || materialRequestOrigin == 'Intercompany MR'  ||
	    		   materialRequestOrigin == 'Customer Consignment' || materialRequestOrigin == 'Cabinet Scan' || materialRequestOrigin == 'Customer PO Stage') {
	    	   loc = "/HaasReports/report/printdeliverydocument.do?documentType=shipmentId&idField="+shippingId.value;
	    	   openWinGeneric(loc,"printDeliveryDocument","800","600","yes","50","50","20","20","no");
	    	   return;
	       }
	       openWinGeneric(loc,"consolidate","800","500","yes","80","80");
	    }
  }
}

var selectedRowId = null;
function selectRow(e, rowId) {
	var selectedRow = document.getElementById("rowId" + rowId + "");

	var selectedRowClass = document.getElementById("colorClass" + rowId + "");
	selectedRow.className = "selected" + selectedRowClass.value + "";

	if (selectedRowId != null && rowId != selectedRowId) {
		var previouslySelectedRow = document.getElementById("rowId" + selectedRowId + "");
		var previouslySelectedRowClass = document.getElementById("colorClass" + selectedRowId + "");
		previouslySelectedRow.className = "" + previouslySelectedRowClass.value + "";
	}  
	selectedRowId = rowId;  

	var isRightMB;
	e = e || window.event;	
	if ("which" in e)  // Gecko (Firefox), WebKit (Safari/Chrome) & Opera
	    isRightMB = e.which == 3; 
	else if ("button" in e)  // IE, Opera 
	    isRightMB = e.button == 2; 
		
	if(isRightMB) {		
		nowShowRightClickMenu(rowId);
	}
} 

function showDetails() {
	loc = "shipmenthistorydetails.do?shipmentId="+document.getElementById("shipmentId" + selectedRowId + "").value +
		  "&companyId="+document.getElementById("companyId" + selectedRowId + "").value+
		  "&inventoryGroup="+document.getElementById("inventoryGroup" + selectedRowId + "").value+
		  "&opsEntityId="+parent.searchFrame.$v("opsEntityId");  
  	openWinGeneric(loc,"customerlookup","800","500","yes","50","50","20","20","no"); 
}

function consolidateBol() {
   var totallines = document.getElementById("totallines").value;
   totallines = totallines*1;
   if(totallines == 0) {
     alert(messagesData.selectashipment);
   }else {
     var checked = "no";
     var tmp = "";
     var shippingId;
     var count = 0;
     for (var p = 0 ; p < totallines ; p ++) {
        var linecheck = document.getElementById("rowChk"+(p*1)+"");
	if(linecheck.checked) {
	  checked="yes";
          count++;
          shippingId = document.getElementById("shipmentId"+(p*1)+"");
          if(tmp == "") {
            tmp = "?shipmentIds=" + "" + shippingId.value;
          }else {
            tmp = tmp + "," + shippingId.value;
          }
	}
     }
     if(checked == "no") {
       alert(messagesData.selectashipment);
     }else if (count > 1) {
       alert(messagesData.oneshipmentcanbeprinted);
     }else {
       var loc = "printconsolidatedbol.do" + tmp;
       
       if ($v("personnelCompanyId") == 'Radian') 
    		  loc = "/tcmIS/hub/" + loc;
       
       openWinGeneric(loc,"printconsolidatebol","800","500","yes","80","80")
    }
  }
}


function checkall(checkboxname)
{
var checkall = document.getElementById("chkAll");
var totallines = document.getElementById("totallines").value;
totallines = totallines*1;

var result ;
var valueq;
if (checkall.checked)
{
  result = true;
  valueq = "yes";
}
else
{
  result = false;
  valueq = "no";
}

for ( var p = 0 ; p < totallines ; p ++)
{
	try
	{
	var linecheck = document.getElementById(""+checkboxname+""+(p*1)+"");
	linecheck.checked =result;
	linecheck.value = valueq;
	}
	catch (ex1)
	{

	}
}

}

function update(entered)
{   
	if (!validateData()){
		return false;
	}		
	document.getElementById('uAction').value = 'update';
   	parent.showPleaseWait();
	document.genericForm.submit();
}


function validateData() 
{
   var totallines = document.getElementById("totallines").value;
   totallines = totallines*1;
   if(totallines == 0) {
	   alert(messagesData.selectatleastoneshipment);
	   return false;
   }
   else  {
	   var checked = "no";
	   var error = "";
	   for (var p = 0 ; p < totallines ; p ++)  {
		   var linecheck = document.getElementById("rowChk"+(p*1)+"");
		   if(linecheck.checked) {
			   if(document.getElementById("incotermRequired"+(p*1)+"").value == 'Y' 
				   && ( document.getElementById("modeoftransport"+(p*1)+"").value == '' 
						|| document.getElementById("incoterm"+(p*1)+"").value == '') ) 
			   {
				   if (error != "") {
					   error = error + ", ";
				   }
				   error = error + document.getElementById("shipmentId"+(p*1)+"").value;				   				   
			   }
			   checked="yes";			        
		   }
	   }
	   if (error != "") {
		   alert(messagesData.selectmotandincoterms + " - " + error);
		   return false;
	   }
	   if(checked == "no") {
		   alert(messagesData.selectatleastoneshipment);
		   return false;
	   }
   }
   return true;
}


function generateExcel() {
  var action = document.getElementById("uAction");
  action.value = 'createExcel';
  openWinGenericViewFile('/tcmIS/common/loadingfile.jsp',"_ShipmentHistoryGenerateExcel","650","600","yes");             
	document.genericForm.target='_ShipmentHistoryGenerateExcel';
	var a = window.setTimeout("document.genericForm.submit();",500);
}


function search()
{

var now = new Date();
 document.getElementById("startSearchTime").value = now.getTime();

	document.getElementById('uAction').value = 'search';
	var shipid = document.getElementById('shipmentId').value;	
	if( shipid.trim() != '' )  {
		document.getElementById('fromDate').value = '';		
		document.getElementById('toDate').value = '';		
	}
   	parent.showPleaseWait();
//	document.genericForm.submit();
    return true;
}

function addViewShipmentDocs()
{ 
		
 var loc = "showshipmentdocuments.do?showDocuments=Yes"+
           "&shipmentId="+document.getElementById("shipmentId" + selectedRowId + "").value; 
 
 if ($v("personnelCompanyId") == 'Radian') 
	  loc = "/tcmIS/supply/" + loc;
 
 try {
 	parent.children[parent.children.length] = openWinGeneric(loc,"showShipmentDocuments","650","300","no","100","100");
 } catch (ex){
 	openWinGeneric(loc,"showShipmentDocuments","650","300","no","100","100");
 }
}


function showIncotermOptions(selectedMot, index) {
	
	var incotermArray = altIncoterm[selectedMot];
	var incotermDescArray = altIncotermDesc[selectedMot];

	var selectedIncotermIndex = 0;
	var defaultIncoterm = $v("hincoterm"+index);
	
	if(incotermArray != null && incotermArray.length > 0) {
		var count = 0;
        if(incotermArray.length > 1) {
	   	    setOption(count++, messagesData.pleaseselect, "", "incoterm"+index);
		}

		for (var i=0; i < incotermArray.length; i++) {
			setOption(i+count,incotermDescArray[i], incotermArray[i], "incoterm"+index);
			//set default incoterm or selected incoterm
			if (defaultIncoterm != null && incotermArray[i] == defaultIncoterm) 
				selectedIncotermIndex = i+count;
		}
	} else {
		  setOption(0,messagesData.pleaseselect,"", "incoterm"+index);
	}
	
    $("incoterm"+index).selectedIndex = selectedIncotermIndex;
}


function modeOfTransportChanged(index) {
	
	var selectedMot = $v("modeOfTransport"+index);
	var off = $('incoterm'+index);

	// clear incoterm drop down
	var incotermO = document.getElementById("incoterm"+index);

	for (var i = incotermO.length; i >= 0;i--) {
		incotermO[i] = null;
	}
	
	showIncotermOptions(selectedMot, index);
	
}

function showMotOptions(index) {
	
	var modeOfTransportArray = altModeOfTransport;
	
	var selectedMotIndex = 0;
	var defaultModeOfTransport = $v("hmodeOfTransport"+index);
	if(modeOfTransportArray != null ) {
		var count = 0;
		if(modeOfTransportArray.length > 1) {
	   	    setOption(count++, messagesData.pleaseselect, "", "modeOfTransport"+index);
		}
		for (var i=0; i < modeOfTransportArray.length; i++) {
			setOption(i+count,modeOfTransportArray[i], modeOfTransportArray[i], "modeOfTransport"+index);
			//set default modeOfTransport or selected modeOfTransport
			if (defaultModeOfTransport != null && modeOfTransportArray[i] == defaultModeOfTransport) 
				selectedMotIndex = i+count;
		}
		
		if(modeOfTransportArray.length <= 1){
			// hide mot drop down 
			var off = $('modeOfTransport'+index);
			off.style['display'] = 'none';
		}
	}
	
    $("modeOfTransport"+index).selectedIndex = selectedMotIndex;
    modeOfTransportChanged(index);
}

function nowShowRightClickMenu(rowNum) {
	var invoiceAtShipping = document.getElementById('invoiceAtShipping'+rowNum).value;
	var proFormaRequired = document.getElementById('proFormaRequired'+rowNum).value;
	
	var menuCount = mm_returnMenuItemCount('showMenu');
	
	// showMenu was the last menu referenced
	mm_deleteItemByText(messagesData.printinvoice);
	mm_deleteItemByText(messagesData.printproforma);
	
	if (invoiceAtShipping == 'Y') {	
		menuCount++;
		mm_insertItem("showMenu",menuCount,"text="+messagesData.printinvoice+";url=javascript:printSelectedInvoice();");
	} 
	
	if (proFormaRequired == 'Y') {	
		menuCount++;
		mm_insertItem("showMenu",menuCount,"text="+messagesData.printproforma+";url=javascript:printSelectedProForma();");
	}

    toggleContextMenu('showMenu');
}

function printSelectedInvoice() {
	var tmp = "";
	var shippingId = document.getElementById("shipmentId"+selectedRowId+"").value;
	var materialRequestOrigin = document.getElementById("materialRequestOrigin"+selectedRowId+"").value;
	var invoiceAtShipping = document.getElementById("invoiceAtShipping"+selectedRowId+"").value;
	var invoiceBy = document.getElementById("invoiceBy"+selectedRowId+"").value;
	
	if (invoiceAtShipping != 'Y')
		return;
	
	if(tmp == "") {
	    tmp = "?confirmedShipments=" + "" + shippingId;
	}
	
	var personnelId = document.getElementById("personnelId").value;
	if(invoiceBy == 'Order' && (materialRequestOrigin == 'Order Entry' || materialRequestOrigin == 'Intercompany MR') ) {       
       var loc = "/HaasReports/report/printdistributioninvoice.do"+tmp+"&personnelId="+personnelId;
	   openWinGeneric(loc,"printInvoiceDocument","800","600","yes","50","50","20","20","no");
	   return;
	} else if (invoiceBy == 'Shipment') { //new link goes here
		var loc = "/HaasReports/report/printshipmentinvoice.do?shipmentIds="+shippingId;
		openWinGeneric(loc,"printCMSInvoiceDocument","800","600","yes","50","50","20","20","no");
		return;
	}	
}

function printSelectedProForma() {
	var shipmentId = document.getElementById("shipmentId"+selectedRowId+"").value;

	var loc = "/HaasReports/report/printshipmentproformainvoice.do?shipmentIds="+shipmentId;
	/*var loc = "/HaasReports/report/printConfigurableReport.do?pr_shipment_id="+shipmentId+
		"&rpt_ReportBeanId=BangalorePackingListRptDef";*/
	openWinGeneric(loc,"printProForma","800","600","yes","50","50","20","20","no");
}

var shipmentIdCol = 1;
function showPickingDocuments() {
	var shipmentId = $("rowId"+selectedRowId).cells[shipmentIdCol].innerHTML;
	
	var loc = "pickingunitdocuments.do?shipmentId="+shipmentId;
    openWinGeneric(loc,"Picking_Unit_Documents","800","400","yes")
}
