function init() 
{
	this.cfg = new YAHOO.util.Config(this);
	if (this.isSecure)
	{
 	this.imageRoot = YAHOO.widget.Module.IMG_ROOT_SSL;
	}

/*Yui pop-ups need to be initialized onLoad to make them work correctly.
If they are not initialized onLoad they tend to act weird*/
	showErrorMessagesWin = new YAHOO.widget.Panel("errorMessagesArea", { width:"300px", height:"300px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:true, visible:false,
	draggable:true, modal:false } );
	showErrorMessagesWin.render();
}

function myMainBodyOnLoad()
{
    //set start search time
    var now = new Date();
    var startSearchTime = document.getElementById("startSearchTime");
    startSearchTime.value = now.getTime();
    //showPleaseWait();
}

/*The reason this is here because you might want a different width/proprties for the pop-up div depending on the page*/
function showErrorMessages()
{
  var resulterrorMessages = window.frames["resultFrame"].document.getElementById("errorMessagesAreaBody");
  var errorMessagesAreaBody = document.getElementById("errorMessagesAreaBody");
  errorMessagesAreaBody.innerHTML = resulterrorMessages.innerHTML;

  showErrorMessagesWin.show();

  var errorMessagesArea = document.getElementById("errorMessagesArea");
  errorMessagesArea.style.display="";
}

var returnSelectedRadianPo=null;
var valueElementId=null;
var displayElementId=null;

function onclickViewPurchaseOrderLink()
{ 
  	try 
  	{
  		var openervalueElementId = opener.document.getElementById(valueElementId);
  		openervalueElementId.value = returnSelectedRadianPo;
  		var openerdisplayElementId = opener.document.getElementById(displayElementId);
  		openerdisplayElementId.className = "inputBox";
  		openerdisplayElementId.value = returnSelectedRadianPo;
  		//reset valiable
  		returnSelectedRadianPo = null;
  		valueElementId=null;
  		displayElementId=null;
  	} 
  	catch( ex ) 
  	{ 
  	//alert("caught exception in try block 1;");
  	}
  	/* try 
  	{
  		if( window.opener.personnelChanged ) 
  		{
  			window.opener.personnelChanged(returnSelectedUserId);
  		}
  	} 
  	catch(exx) { alert("caught exception in try block 2;"); } */
     var selectedRadianPo = window.frames["resultFrame"].document.getElementById("selectedRadianPo");
  	alert(" selectedRadianPo.value = [" +  selectedRadianPo.value + "]; ");
    doViewPurchaseOrder( selectedRadianPo.value );
  	
 	window.close();
}

function doViewPurchaseOrder(radianPo)
{
    loc = "/tcmIS/supply/purchaseorder.do?po=";
    loc = loc + radianPo;
    loc = loc + "&Action=searchlike&subUserAction=po";
    window.open(loc);
    
    //openWinGeneric(loc,"purchaseorder", "800", "450", "yes", "50", "50")
} 
