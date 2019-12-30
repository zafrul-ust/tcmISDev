var submitcount=0;
var updatecount=0;
function SubmitOnlyOnce()
{
    if (submitcount == 0)
    {
        submitcount++;
        return true;
    }
    else
    {
        alert("This form has already been submitted.\n Please Wait for Results.\n Thanks!");
        return false;
    }
}

function cancel()
{
   window.close();
}

function getshipTo(name,whichone)
{
    loc = "/tcmIS/hub/genericshipto?shiptoID=";
    var shiptoid  =  document.getElementById(""+name+"");
    loc = loc + shiptoid.value;
    loc = loc + "&whichone=" + whichone;
    openWinGeneric(loc,"ShipToID","50","50","yes","150","120")
}

String.prototype.trim = function()
{
// skip leading and trailing whitespace
// and return everything in between
  return this.replace(/^\s*(\b.*\b|)\s*$/, "$1");
}

function openWinGeneric(destination,WinName,WinWidth, WinHeight,Resizable,topCoor,leftCoor )
{
windowprops = "toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,status=yes,top="+topCoor+",left="+leftCoor+",width="+WinWidth+",height="+WinHeight+",resizable="+ Resizable;
preview = window.open(destination, WinName,windowprops);
}


function sendShipto( entered, which )
{
 with( entered )
 {
  if (which == "from")
  {
     selectedRow=opener.document.getElementById( "hub_location_desc" );
     selectedRow.value=window.document.SupplierLike.shiptodesc.value;

	  selectedRow=opener.document.getElementById( "hub_address_line_1" );
     selectedRow.value=window.document.SupplierLike.addline1.value;

     selectedRow=opener.document.getElementById( "hub_address_line_2" );
     selectedRow.value=window.document.SupplierLike.addline2.value;

     selectedRow=opener.document.getElementById( "hub_city" );
     selectedRow.value=window.document.SupplierLike.city.value;

     selectedRow=opener.document.getElementById( "hub_state_abbrev" );
     selectedRow.value=window.document.SupplierLike.state.value;

     selectedRow=opener.document.getElementById( "hub_zip" );
     selectedRow.value=window.document.SupplierLike.zipcode.value;
  }
  else
  {
     selectedRow=opener.document.getElementById( "ship_to_location_desc" );
     selectedRow.value=window.document.SupplierLike.shiptodesc.value;

	  selectedRow=opener.document.getElementById( "ship_to_address_line_1" );
     selectedRow.value=window.document.SupplierLike.addline1.value;

     selectedRow=opener.document.getElementById( "ship_to_address_line_2" );
     selectedRow.value=window.document.SupplierLike.addline2.value;

     selectedRow=opener.document.getElementById( "ship_to_city" );
     selectedRow.value=window.document.SupplierLike.city.value;

     selectedRow=opener.document.getElementById( "ship_to_state_abbrev" );
     selectedRow.value=window.document.SupplierLike.state.value;

     selectedRow=opener.document.getElementById( "ship_to_zip" );
     selectedRow.value=window.document.SupplierLike.zipcode.value;
  }
 }
 window.close();
}

function addsupplierID( matidvalue,matdesc,addline1,addline2,city,state,zip )
{
     document.SupplierLike.supplierid.value=matidvalue;
     document.SupplierLike.shiptodesc.value=matdesc;
     document.SupplierLike.addline1.value=addline1;
     document.SupplierLike.addline2.value=addline2;
     document.SupplierLike.city.value=city;
     document.SupplierLike.state.value=state;
     document.SupplierLike.zipcode.value=zip;
}
