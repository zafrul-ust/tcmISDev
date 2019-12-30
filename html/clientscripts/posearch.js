var submitcount=0;
var updatecount=0;
function SubmitOnlyOnce()
{
    if (submitcount == 0)
    {
        submitcount++;
        try
        {
         var target = document.all.item("TRANSITPAGE");
         target.style["display"] = "";
         var target1 = document.all.item("MAINPAGE");
         target1.style["display"] = "none";
        }
        catch (ex)
        {
        }

        return true;
    }
    else
    {
        alert("This form has already been submitted.\n Please Wait for Results.\n Thanks!");
        return false;
    }
}

String.prototype.trim = function()
{
// skip leading and trailing whitespace
// and return everything in between
  return this.replace(/^\s*(\b.*\b|)\s*$/, "$1");
}

function cancel()
{
opener.makeCursorNormal();
window.close();
}

function sendSupplier(entered)
{
 with (entered)
 {
 eval( testmfgid = "window.document.SupplierLike.supplierid")
 if ( (eval(testmfgid.toString())).value.length > 0 )
{
selectedRow = opener.document.getElementById("supplierid");
selectedRow.className = "HEADER";
selectedRow.value = window.document.SupplierLike.supplierid.value;
selectedRow = opener.document.getElementById("validsupplier");
selectedRow.value = "Yes";
selectedRow = opener.document.getElementById("supplierline1");
selectedRow.innerHTML = "<FONT COLOR=\"Maroon\">" + window.document.SupplierLike.supplierdesc.value + "<FONT>" ;
selectedRow = opener.document.getElementById("supplierline2");
selectedRow.innerHTML = "<FONT COLOR=\"Maroon\">" + window.document.SupplierLike.addline1.value + "<FONT>" ;
selectedRow = opener.document.getElementById("supplierline3");
selectedRow.innerHTML = "<FONT COLOR=\"Maroon\">" + window.document.SupplierLike.addline2.value + "<FONT>" ;
selectedRow = opener.document.getElementById("supplierline4");
selectedRow.innerHTML = "<FONT COLOR=\"Maroon\">" + window.document.SupplierLike.addline3.value + "<FONT>" ;
window.close();
 }
 }
}

function addsupplierID(matidvalue)
{
document.SupplierLike.supplierid.value = matidvalue;
}

function hubChanged()
{
var hubO = document.getElementById("HubName");
var inventoryGroupO = document.getElementById("inventoryGroup");
var selhub = hubO.value;
	for (var i = inventoryGroupO.length; i > 0;i--)
   {
      inventoryGroupO[i] = null;
   }

   if (selhub != "None")
   {
	 showinvlinks(selhub);
	}
	else
   {
    setCab(0,"All","")
   }
	inventoryGroupO.selectedIndex = 0;
}

function showinvlinks(selectedhub)
{
    var invgrpid = new Array();
    invgrpid = altinvid[selectedhub];

	 var invgrpName = new Array();
    invgrpName = altinvName[selectedhub];

    if(invgrpid.length == 0)
    {
      setCab(0,"All","")
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