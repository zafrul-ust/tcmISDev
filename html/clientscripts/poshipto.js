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

String.prototype.trim = function()
{
// skip leading and trailing whitespace
// and return everything in between
  return this.replace(/^\s*(\b.*\b|)\s*$/, "$1");
}

function openWinGeneric(destination,WinName,WinWidth, WinHeight,Resizable,topCoor,leftCoor )
{
windowprops = "toolbar=yes,location=no,directories=yes,menubar=yes,scrollbars=yes,status=yes,top="+topCoor+",left="+leftCoor+",width="+WinWidth+",height="+WinHeight+",resizable="+ Resizable;
preview = window.open(destination, WinName,windowprops);
}


function sendinvgrpvalue()
{
     var invengrp = document.getElementById( "invengrpdo" );
     //var hub = document.getElementById( "hub" );
     //var hubname = document.getElementById( "hubname" );

     selectedRow=opener.document.getElementById( "invengrp" );
     selectedRow.value=invengrp.value;

     var opsEntityId = document.getElementById( "opsEntityId" );     
     var selectedOpsEntityId=opener.document.getElementById( "opsEntityId" );
     selectedOpsEntityId.value=opsEntityId.value;

     //selectedRow=opener.document.getElementById( "HubName" );
     //selectedRow.value=hub.value;

     //selectedRow=opener.document.getElementById( "HubFullName" );
     //selectedRow.value=hubname.value;

	  var currencyid = document.getElementById( "currencyid" );
	  var parentcurrency=opener.document.getElementById( "currency" );

	  if ( parentcurrency.value == "None" )
	  {
		 parentcurrency.value = currencyid.value;
	  }

	  var suppid  =  opener.document.getElementById("supplierid");
	  var validsupplier  =  opener.document.getElementById("validsupplier");
	  if (suppid.value.trim().length > 0 && validsupplier.value.trim() == "Yes")
	  {
       opener.getSupplier();
     }
     else
     {
        opener.document.getElementById('save').click();
     }
     window.close();
}

function sendShipto( entered )
{
 with( entered )
 {
  eval( testmfgid="window.document.SupplierLike.supplierid" )
  if ( ( eval( testmfgid.toString() ) ).value.length > 0 )
  {
     selectedRow=opener.document.getElementById( "shiptoid" );
     selectedRow.className="HEADER";
     selectedRow.value=window.document.SupplierLike.supplierid.value;
     selectedRow=opener.document.getElementById( "shiptocompanyid" );
     selectedRow.value=window.document.SupplierLike.shiptocompanyid.value;
     selectedRow=opener.document.getElementById( "shipToFacilityId" );
     selectedRow.value=window.document.SupplierLike.shipToFacilityId.value;
     selectedRow=opener.document.getElementById( "validshipto" );
     selectedRow.value="Yes";
     validcarrier=opener.document.getElementById( "validcarrier" );
     validcarrier.value="No";
     carrierID=opener.document.getElementById( "carrierID" );
     carrierID.className="INVALIDTEXT";
     carrierline1=opener.document.getElementById( "carrierline1" );
     carrierline1.innerHTML="";
     carrierline2=opener.document.getElementById( "carrierline2" );
     carrierline2.innerHTML="";
     selectedRow=opener.document.getElementById( "shiptoline1" );
     selectedRow.innerHTML="<FONT COLOR=\"Maroon\">" + window.document.SupplierLike.shiptodesc.value + "<FONT>";
     selectedRow=opener.document.getElementById( "shiptoline2" );
     selectedRow.innerHTML="<FONT COLOR=\"Maroon\">" + window.document.SupplierLike.addline1.value + "<FONT>";

     selectedRow=opener.document.getElementById( "shiptoline3" );
     selectedRow.innerHTML="<FONT COLOR=\"Maroon\">" + window.document.SupplierLike.addline2.value + "<FONT>";

     if ( window.document.SupplierLike.addline3.value.length > 0 )
     {
       selectedRow=opener.document.getElementById( "shiptoline4" );
       selectedRow.innerHTML="<FONT COLOR=\"Maroon\">" + window.document.SupplierLike.addline3.value + "<FONT>";
       selectedRow=opener.document.getElementById( "shiptoline5" );
       selectedRow.innerHTML="<FONT COLOR=\"Maroon\">" + window.document.SupplierLike.addline4.value + "<FONT>";
     }
     else
     {
       selectedRow=opener.document.getElementById( "shiptoline4" );
       selectedRow.innerHTML="<FONT COLOR=\"Maroon\">" + window.document.SupplierLike.addline4.value + "<FONT>";
       selectedRow=opener.document.getElementById( "shiptoline5" );
       selectedRow.innerHTML="<FONT COLOR=\"Maroon\"><FONT>";
     }

     invengrp =opener.document.getElementById( "invengrp" );
     invengrp.value=window.document.SupplierLike.invengrp.value;
   }
 }
}

function addsupplierID( matidvalue,matdesc,addline1,addline2,addline3,addline4,shiptocompanyid,HubName1,hubFullName,shipToFacilityId )
{
     document.SupplierLike.supplierid.value=matidvalue;
     document.SupplierLike.shiptocompanyid.value=shiptocompanyid;
     document.SupplierLike.shipToFacilityId.value=shipToFacilityId;
     document.SupplierLike.shiptodesc.value=matdesc;
     document.SupplierLike.addline1.value=addline1;
     document.SupplierLike.addline2.value=addline2;
     document.SupplierLike.addline3.value=addline3;
     document.SupplierLike.addline4.value=addline4;
     document.SupplierLike.HubName.value=HubName1;
     document.SupplierLike.hubFullName.value=hubFullName;
}