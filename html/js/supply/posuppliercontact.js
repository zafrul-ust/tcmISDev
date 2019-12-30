var beangrid;
/*Set this to be false if you don't want the grid width to resize based on window size.
* You will also need to set the resultsMaskTable width appropriately in the JSP.*/
var resizeGridWithWindow = true;
windowCloseOnEsc = true;

var returnSelectedValue=null;
var returnSelectedId=null;
var returnSelectedPhone=null;
var returnSelectedFax=null;
var returnSelectedEmail=null;
var valueElementId=$v('valueElementId');
var displayElementId=$v('displayElementId');

/*This function is called onload from the page*/
function myResultOnload()
{
	/*try
 {
 if (!showUpdateLinks) Dont show any update links if the user does not have permissions
 {
  document.getElementById("updateResultLink").style["display"] = "none";
 }
 else
 {
  document.getElementById("updateResultLink").style["display"] = "";
 }
 }
 catch(ex)
 {}*/

/*If there is data to be shown initialize the grid*/
 var totalLines = document.getElementById("totalLines").value;
 if (totalLines > 0)
 {
  document.getElementById("poSupplierContact").style["display"] = "";
  initializeGrid();  
 }
 else
 {
   document.getElementById("poSupplierContact").style["display"] = "none";
 }

 /*if (valueElementId.length>0 && displayElementId.length>0 )
 {
  document.getElementById("selectedRow").innerHTML="";
 }*/

/*This displays our standard footer message*/
displayNoSearchSecDuration();

 /*It is important to call this after all the divs have been turned on or off.
 * This sets all sizes to be a good fit on the screen.*/
 setResultSize();

 valueElementId=$v('valueElementId');
 displayElementId=$v('displayElementId');
 
}
 
 
function initializeGrid(){
	 beangrid = new dhtmlXGridObject('poSupplierContact');

	 initGridWithConfig(beangrid,config,false,true);
	 if( typeof( jsonMainData ) != 'undefined' ) {
	 beangrid.parse(jsonMainData,"json");

	 }
	 beangrid.attachEvent("onRowSelect",selectRow);
	 beangrid.attachEvent("onRightClick",selectRightclick);
	}


function selectRow(rowId,cellInd){
    
	  if (valueElementId.length>0 && displayElementId.length>0 )
	  {
	  var contactid = beangrid.cellById(beangrid.getSelectedRowId(), beangrid.getColIndexById("contactId")).getValue();
	  var contactname = beangrid.cellById(beangrid.getSelectedRowId(), beangrid.getColIndexById("lastName")).getValue();
	  var selectedUser = parent.document.getElementById("selectedRow");	
	  
	  selectedUser.innerHTML = '| <a href="#" onclick="selectedRow(); return false;">'+messagesData.selectedRowMsg+' : '+contactname+'</a>';
	  
	  //set variable to main
	  parent.returnSelectedValue = contactname;
	  parent.returnSelectedId    = contactid; 
	 
     }
}	  


function selectRightclick(rowId,cellInd){
	beangrid.selectRowById(rowId,null,false,false);	
	
	if(showUpdateLinks)
	toggleContextMenu("suppplierContact");
}


function selectedRow()
{
	if ($("fromSupplierPriceList") != null && $v("fromSupplierPriceList") =='Y' && window.opener.setSupplierContact != null)
    {
		   window.opener.setSupplierContact($v("callbackparam"), parent.returnSelectedId, parent.returnSelectedValue);
	} else if ($("fromSupplierPriceList") != null && $v("fromSupplierPriceList") =='N' && window.opener.orderTakerChanged == 'Y')
    {
		 if (parent.returnSelectedValue == "") 
			 parent.returnSelectedValue = beangrid.cellById(beangrid.getSelectedRowId(), beangrid.getColIndexById("nickname")).getValue();
		 parent.returnSelectedPhone = beangrid.cellById(beangrid.getSelectedRowId(), beangrid.getColIndexById("phone")).getValue();
		 parent.returnSelectedFax = beangrid.cellById(beangrid.getSelectedRowId(), beangrid.getColIndexById("fax")).getValue();
		 parent.returnSelectedEmail = beangrid.cellById(beangrid.getSelectedRowId(), beangrid.getColIndexById("email")).getValue();
		 window.opener.setOrderTakerChanged(parent.returnSelectedId, parent.returnSelectedValue, parent.returnSelectedPhone, 
				 parent.returnSelectedFax, parent.returnSelectedEmail);
    } else {
		  try {
			  var openervalueElementId = opener.document.getElementById(valueElementId);
			  openervalueElementId.value = returnSelectedId;
			  var openerdisplayElementId = opener.document.getElementById(displayElementId);
			  openerdisplayElementId.className = "inputBox";
			  openerdisplayElementId.value = returnSelectedValue;
		  } catch( ex ) {}		  
		  try {
			  if( window.opener.contactChanged ) {
			  	window.opener.contactChanged(returnSelectedId);	  	
			  }
		  } catch(exx) {}
     }
  window.close();
  //reset valiable
  returnSelectedValue = null;
  returnSelectedId=null;
  valueElementId=null;
  displayElementId=null;
}




function addNewSupplierContact()
{
   var supplier= $v("supplier");
   var fromSupplierPriceList= $v("fromSupplierPriceList");
   var loc = "/tcmIS/supply/newposuppliercontact.do?action=showSupplierContact&actionType=new&supplier="+supplier+
             "&fromSupplierPriceList="+fromSupplierPriceList;
   openWinGeneric(loc, "newPoSupplierContact", "800", "250", "yes", "50", "50");	
}



function editSupplierContact()
{
	var supplier= cellValue(beangrid.getSelectedRowId(),"supplier");   
	var contactId =  cellValue(beangrid.getSelectedRowId(),"contactId"); 
	var loc = "/tcmIS/supply/newposuppliercontact.do?action=showSupplierContactData&actionType=edit&supplier="+supplier+"&contactId="+contactId;
    openWinGeneric(loc, "newPoSupplierContact", "800", "250", "yes", "50", "50");
}


function refreshPage( supplier)
{		
			
	document.getElementById("supplier").value=supplier;
	document.genericForm.target='';
	var action = document.getElementById("action");
	action.value="search";
	var now = new Date();
    var startSearchTime = document.getElementById("startSearchTime");
	startSearchTime.value = now.getTime();
	document.genericForm.submit();
	
}