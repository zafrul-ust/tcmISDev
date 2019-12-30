function addaddchgrtn(rownum)
{
    loc = "/tcmIS/Invoice/newinvoice?Action=addaddchgrtn&suppID=";

    var validpo  =  document.getElementById("validpo");
    if (validpo.value == "No")
    {
        alert("Please Enter a Valid PO.");
    }
    else
    {
        var suppid  =  document.getElementById("supplierid");
	     var supplierrep = suppid.value;
        supplierrep = supplierrep.replace(/&/gi, "%26");
        loc = loc + supplierrep;

		  var invoices  =  document.getElementById("invoices");
        loc = loc + "&invoiceId=" +invoices.value;

	     var po  =  document.getElementById("po");
        loc = loc + "&radianpo=" + po.value;

        var itemID  =  document.getElementById("itemidindetail"+rownum+"");
        loc = loc + "&itemID=" + itemID.value;

        loc = loc + "&selectedrowid=" + selected_rowid;

        openWinGeneric(loc,"addcreditcorrectionline","345","240","yes","40","80")
    }
}

function showallinvlines (rownum)
{
	 loc = "/tcmIS/Invoice/accpayable?Action=showallinvlines";
    var validpo  =  document.getElementById("validpo");
    if (validpo.value == "No")
    {
        alert("Please Enter a Valid PO.");
    }
    else
    {
	     var po  =  document.getElementById("po");
        loc = loc + "&radianPO=" + po.value;

        var itemID  =  document.getElementById("itemidindetail"+rownum+"");
        loc = loc + "&itemID=" + itemID.value;
        loc = loc + "&lineID=" + rownum;

        openWinGeneric(loc,"addinvoiceLine","250","200","yes","40","80")
    }
}

function showunmatchedinv (rownum)
{
	 loc = "/tcmIS/Invoice/accpayable?Action=showunmatchedinv";
    var validpo  =  document.getElementById("validpo");
    if (validpo.value == "No")
    {
        alert("Please Enter a Valid PO.");
    }
    else
    {
	     var po  =  document.getElementById("po");
        loc = loc + "&radianPO=" + po.value;

        var itemID  =  document.getElementById("itemidindetail"+rownum+"");
        loc = loc + "&itemID=" + itemID.value;
        loc = loc + "&lineID=" + rownum;

        openWinGeneric(loc,"addinvoiceLine","250","200","yes","40","80")
    }
}


function showallreceiptlines (rownum)
{
	 loc = "/tcmIS/Invoice/accpayable?Action=showallreceiptlines";
    var validpo  =  document.getElementById("validPo");
    if (validpo.value == "No")
    {
        alert("Please Enter a Valid PO.");
    }
    else
    {
	     var po  =  document.getElementById("po");
        loc = loc + "&radianPO=" + po.value;

        var itemID  =  document.getElementById("itemidindetail"+rownum+"");
        loc = loc + "&itemID=" + itemID.value;
        loc = loc + "&lineID=" + rownum;

        openWinGeneric(loc,"addinvoiceLine","250","200","yes","40","80")
    }
}

function showunmatchedrecipts (rownum)
{
	 loc = "/tcmIS/Invoice/accpayable?Action=showunmatchedrecipts";
    var validpo  =  document.getElementById("validPo");
    if (validpo.value == "No")
    {
        alert("Please Enter a Valid PO.");
    }
    else
    {
	     var po  =  document.getElementById("po");
        loc = loc + "&radianPO=" + po.value;

        var itemID  =  document.getElementById("itemidindetail"+rownum+"");
        loc = loc + "&itemID=" + itemID.value;
        loc = loc + "&lineID=" + rownum;

        openWinGeneric(loc,"addinvoiceLine","250","200","yes","40","80")
    }
}

function updateVerifiedBillingAddressComplete()
{
        opener.document.purchaseorder.VerifiedB.disabled = true;
        window.close();
}

function changesuppid()
{
var suppid  =  document.getElementById("supplierid");
var suppid11  =  document.getElementById("suppID");

var suppidfromdd  =  document.getElementById("choicesuppid");
suppid.value =suppidfromdd.value;
suppid11.value =suppidfromdd.value;

var voucherbillinglocId  =  document.getElementById("voucherbillinglocId");
voucherbillinglocId.value = "";

var shiptoline1  =  document.getElementById("shiptoline1");
shiptoline1.innerHTML = "";

var shiptoline2  =  document.getElementById("shiptoline2");
shiptoline2.innerHTML = "";

var shiptoline3  =  document.getElementById("shiptoline3");
shiptoline3.innerHTML = "";

var shiptoline4  =  document.getElementById("shiptoline4");
shiptoline4.innerHTML = "";
}

function changeNetInvAmount()
{
	var invAmount = document.getElementById("invamount");
   var refused = document.getElementById("refused");
	var lineTotalPrice = (invAmount.value - refused.value);
	var invNetAmount = document.getElementById("netAmount");
	//alert("------ change net amount: "+lineTotalPrice.toFixed(2));
   invNetAmount.value = (lineTotalPrice.toFixed(2));
}

function searchbillingAddressWithUpdate()
{
    loc = "/tcmIS/Invoice/newinvoice?Action=searchbillingAddress&suppID=";
    var suppid  =  document.getElementById("supplierid");
         if (!suppid.value.trim().length > 0 )
    {
        alert("Please Enter a Valid PO.");
    }
    else
    {
        var supplierrep = suppid.value;
        supplierrep = supplierrep.replace(/&/gi, "%26");
        loc = loc + supplierrep;
        loc = loc + "&selectedrowid=" + selected_rowid;
        loc = loc + "&withUpdate=yes";
        openWinGeneric(loc,"editbillingAddress","640","420","yes","40","80")
    }
}

function sendSupplierWithUpdate(entered)
{
    with (entered)
    {
        eval( testmfgid = "window.document.SupplierLike.supplierid")
        if ( (eval(testmfgid.toString())).value.length > 0 )
        {
            supplierId = document.getElementById("supplierid");
            voucherbillinglocId = opener.document.getElementById("voucherbillinglocId");
            voucherbillinglocId.value = supplierId.value;
            selectedRow = opener.document.getElementById("shiptoline1");
            selectedRow.innerHTML = "<FONT COLOR=\"Maroon\">" + window.document.SupplierLike.supplierdesc.value + "<FONT>" ;
            selectedRow = opener.document.getElementById("shiptoline2");
            selectedRow.innerHTML = "<FONT COLOR=\"Maroon\">" + window.document.SupplierLike.addline1.value + "<FONT>" ;
            if (window.document.SupplierLike.addline2.value.length > 0)
            {
                selectedRow = opener.document.getElementById("shiptoline3");
                selectedRow.innerHTML = "<FONT COLOR=\"Maroon\">" + window.document.SupplierLike.addline2.value + "<FONT>" ;
                selectedRow = opener.document.getElementById("shiptoline4");
                selectedRow.innerHTML = "<FONT COLOR=\"Maroon\">" + window.document.SupplierLike.addline3.value + "<FONT>" ;
            }
            else
            {
                selectedRow = opener.document.getElementById("shiptoline3");
                selectedRow.innerHTML = "<FONT COLOR=\"Maroon\">" + window.document.SupplierLike.addline3.value + "<FONT>" ;
                selectedRow = opener.document.getElementById("shiptoline4");
                selectedRow.innerHTML = "<FONT COLOR=\"Maroon\"><FONT>" ;
            }
            //update voucher
            loc = "/tcmIS/Invoice/newinvoice?Action=billingAddressUpdateVerified&voucherID=";
            var vID = opener.document.getElementById("invoices");
            loc = loc + vID.value + "&voucherBillLocID="+voucherbillinglocId.value;
            window.location = loc;
            //alert("Testing for premature socket close.  Please click OK to continue.");
            //window.close();
            //opener.document.purchaseorder.VerifiedB.disabled = true;
        }
    }
}

function billingAddressVerified()
{
    loc = "/tcmIS/Invoice/newinvoice?Action=billingAddressVerified&voucherID=";

    var suppid  =  document.getElementById("supplierid");
         if (!suppid.value.trim().length > 0 )
    {
        alert("Please Enter a Valid PO.");
    }
    else
    {
        var vID = document.getElementById("invoices");
        loc = loc + vID.value;
        windowprops = "toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,status=yes,top=40,left=80,width=100,height=100,resizable=no";
        preview = window.open(loc, "editbillingAddress",windowprops);
        //alert("Testing for premature socket close.  Please click OK to continue.");
        //preview.close();
        //window.document.purchaseorder.VerifiedB.disabled = true;
    }
}

function openWinGeneric(destination,WinName,WinWidth, WinHeight,Resizable,topCoor,leftCoor )
{
    windowprops = "toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,status=yes,top="+topCoor+",left="+leftCoor+",width="+WinWidth+",height="+WinHeight+",resizable="+ Resizable;
	 preview = window.open(destination, WinName.replace('.','a'),windowprops);
}

function ShowSearch(name,entered)
{
 with (entered)
 {
	 loc = "/tcmIS/Invoice/newinvoice?Action=" + name.toString();
	 var suppid  =  document.getElementById("suppID");
	 loc = loc + "&suppID=" + suppid.value;
	 var newBillingLocId  =  document.getElementById("newBillingLocId");
	 loc = loc + "&newbillingId=" + newBillingLocId.value;
 }
  	 window.location.replace(loc);
 }


function editbillingAddress(entered)
{
    loc = "/tcmIS/Invoice/newinvoice?Action=editbillingAddress&suppID=";

    var suppid  =  document.getElementById("supplierid");
    var voucherbillinglocId  =  document.getElementById("voucherbillinglocId");

	 if (!voucherbillinglocId.value.trim().length > 0 )
	 {

	 }
    else if (!suppid.value.trim().length > 0 )
    {
        alert("Please Enter a Valid PO.");
    }
    else
    {
        //var suppid  =  document.getElementById("supplierid");
		  var supplierrep = suppid.value;
        supplierrep = supplierrep.replace(/&/gi, "%26");
        loc = loc + supplierrep;

		  //var po  =  document.getElementById("po");
        //loc = loc + "&radianpo=" + po.value;

		  loc = loc + "&selectedrowid=" + selected_rowid;
		  loc = loc + "&voucherbillinglocId=" + voucherbillinglocId.value;

        openWinGeneric(loc,"editbillingAddress","550","300","yes","40","80")
    }
}

function cancel()
{
	window.close();
}

function searchbillingAddress()
{
    loc = "/tcmIS/Invoice/newinvoice?Action=searchbillingAddress&suppID=";

    var suppid  =  document.getElementById("supplierid");
	 if (!suppid.value.trim().length > 0 )
    {
        alert("Please Enter a Valid PO.");
    }
    else
    {
        //var suppid  =  document.getElementById("supplierid");
		  var supplierrep = suppid.value;
        supplierrep = supplierrep.replace(/&/gi, "%26");
        loc = loc + supplierrep;

		  //var po  =  document.getElementById("po");
        //loc = loc + "&radianpo=" + po.value;

		  loc = loc + "&selectedrowid=" + selected_rowid;

        openWinGeneric(loc,"editbillingAddress","640","420","yes","40","80")
    }
}

function sendSupplier(entered)
{
    with (entered)
    {
        eval( testmfgid = "window.document.SupplierLike.supplierid")
        if ( (eval(testmfgid.toString())).value.length > 0 )
        {
				supplierId = document.getElementById("supplierid");
				voucherbillinglocId = opener.document.getElementById("voucherbillinglocId");
				voucherbillinglocId.value = supplierId.value;

            selectedRow = opener.document.getElementById("shiptoline1");
            selectedRow.innerHTML = "<FONT COLOR=\"Maroon\">" + window.document.SupplierLike.supplierdesc.value + "<FONT>" ;
            selectedRow = opener.document.getElementById("shiptoline2");
            selectedRow.innerHTML = "<FONT COLOR=\"Maroon\">" + window.document.SupplierLike.addline1.value + "<FONT>" ;
            if (window.document.SupplierLike.addline2.value.length > 0)
            {
                selectedRow = opener.document.getElementById("shiptoline3");
                selectedRow.innerHTML = "<FONT COLOR=\"Maroon\">" + window.document.SupplierLike.addline2.value + "<FONT>" ;
                selectedRow = opener.document.getElementById("shiptoline4");
                selectedRow.innerHTML = "<FONT COLOR=\"Maroon\">" + window.document.SupplierLike.addline3.value + "<FONT>" ;
            }
            else
            {
                selectedRow = opener.document.getElementById("shiptoline3");
                selectedRow.innerHTML = "<FONT COLOR=\"Maroon\">" + window.document.SupplierLike.addline3.value + "<FONT>" ;
                selectedRow = opener.document.getElementById("shiptoline4");
                selectedRow.innerHTML = "<FONT COLOR=\"Maroon\"><FONT>" ;
            }
            window.close();
        }
    }
}
function addsupplierID(matidvalue,matdesc,addline1,addline2,addline3)
{
    document.SupplierLike.supplierid.value = matidvalue;
    document.SupplierLike.supplierdesc.value = matdesc;
    document.SupplierLike.addline1.value = addline1;
    document.SupplierLike.addline2.value = addline2;
    document.SupplierLike.addline3.value = addline3;
}



function showinvoicesummary()
{
    loc = "/tcmIS/Invoice/newinvoice?Action=showinvoicesummary&suppID=";

    var validpo  =  document.getElementById("validpo");
    var invoices  =  document.getElementById("invoices");
    //alert(invoices.value);
    if (validpo.value == "No")
    {
        alert("Please Enter a Valid PO.");
    }
 	 else if (invoices.value.trim().length == 0)
    {
        alert("Please Enter a Valid Invoice.");
    }
    else
    {
        var suppid  =  document.getElementById("supplierid");
	     var supplierrep = suppid.value;
        supplierrep = supplierrep.replace(/&/gi, "%26");
        loc = loc + supplierrep;


        loc = loc + "&invoiceId=" +invoices.value;

	     var po  =  document.getElementById("po");
        loc = loc + "&radianpo=" + po.value;

        loc = loc + "&selectedrowid=" + selected_rowid;

        openWinGeneric(loc,"editinvoice","650","600","yes","40","80")
    }
}

function addcreditcorrectionline(rownum)
{
    loc = "/tcmIS/Invoice/newinvoice?Action=addcreditcorrectionline&suppID=";

    var validpo  =  document.getElementById("validpo");
    if (validpo.value == "No")
    {
        alert("Please Enter a Valid PO.");
    }
    else
    {
        var suppid  =  document.getElementById("supplierid");
	     var supplierrep = suppid.value;
        supplierrep = supplierrep.replace(/&/gi, "%26");
        loc = loc + supplierrep;

		  var invoices  =  document.getElementById("invoices");
        loc = loc + "&invoiceId=" +invoices.value;

	     var po  =  document.getElementById("po");
        loc = loc + "&radianpo=" + po.value;

        var itemID  =  document.getElementById("itemidindetail"+rownum+"");
        loc = loc + "&itemID=" + itemID.value;

        loc = loc + "&selectedrowid=" + selected_rowid;

        openWinGeneric(loc,"addcreditcorrectionline","345","240","yes","40","80")
    }
}

function addrmaline(rownum)
{
    loc = "/tcmIS/Invoice/newinvoice?Action=addrmaline&suppID=";

    var validpo  =  document.getElementById("validpo");
    if (validpo.value == "No")
    {
        alert("Please Enter a Valid PO.");
    }
    else
    {
        var suppid  =  document.getElementById("supplierid");
	     var supplierrep = suppid.value;
        supplierrep = supplierrep.replace(/&/gi, "%26");
        loc = loc + supplierrep;

		  var invoices  =  document.getElementById("invoices");
        loc = loc + "&invoiceId=" +invoices.value;

	     var po  =  document.getElementById("po");
        loc = loc + "&radianpo=" + po.value;

        var itemID  =  document.getElementById("itemidindetail"+rownum+"");
        loc = loc + "&itemID=" + itemID.value;

        loc = loc + "&selectedrowid=" + selected_rowid;

        openWinGeneric(loc,"addcreditcorrectionline","345","240","yes","40","80")
    }
}

function undoInvocieLine(rownum,mainrow)
{
    loc = "/tcmIS/Invoice/newinvoice?Action=undoInvocieLine&suppID=";

    var validpo  =  document.getElementById("validpo");
    if (validpo.value == "No")
    {
        alert("Please Enter a Valid PO.");
    }
    else
    {
        var suppid  =  document.getElementById("supplierid");
	     var supplierrep = suppid.value;
        supplierrep = supplierrep.replace(/&/gi, "%26");
        loc = loc + supplierrep;

		  var invoices  =  document.getElementById("invoices");
        loc = loc + "&invoiceId=" +invoices.value;

	     var po  =  document.getElementById("po");
        loc = loc + "&radianpo=" + po.value;

        var itemID  =  document.getElementById("itemidindetail"+mainrow+"");
        loc = loc + "&itemID=" + itemID.value;

		  var itemtype  =  document.getElementById("itemtype"+mainrow+"");
		  var noofmatchingitem  =  document.getElementById("noofmatchingitem"+mainrow+"");
		  if (itemtype.value == "y" && noofmatchingitem.value == 1)
		  {
				selected_rowid = "row1";
				selectedrowidinform = document.getElementById("selectedrowid");
			   selectedrowidinform.value = "row1";
		  }

        loc = loc + "&itemID=" + itemID.value;

        var itemvoucherId  =  document.getElementById("itemvoucherId"+mainrow+""+rownum+"");
        loc = loc + "&itemvoucherId=" + itemvoucherId.value;

	     var itemvoucherline  =  document.getElementById("itemvoucherline"+mainrow+""+rownum+"");
        loc = loc + "&itemvoucherline=" + itemvoucherline.value;

		  loc = loc + "&selectedrowid=" + selected_rowid;

		  if (confirm("This Will Remove all Matches Against This Invoice Line.\n\n\nDo You Want to Proceed?"))
		  {
	     openWinGeneric(loc,"undoInvocieLine","250","200","yes","40","80")
	     }
    }
}


function undoReceiptInvocieLine(rownum,mainrow)
{
    loc = "/tcmIS/Invoice/newinvoice?Action=undoReceiptInvocieLine&suppID=";

    var validpo  =  document.getElementById("validpo");
    if (validpo.value == "No")
    {
        alert("Please Enter a Valid PO.");
    }
    else
    {
        var suppid  =  document.getElementById("supplierid");
	     var supplierrep = suppid.value;
        supplierrep = supplierrep.replace(/&/gi, "%26");
        loc = loc + supplierrep;

		  var invoices  =  document.getElementById("invoices");
        loc = loc + "&invoiceId=" +invoices.value;

	     var po  =  document.getElementById("po");
        loc = loc + "&radianpo=" + po.value;

        var itemID  =  document.getElementById("itemidindetail"+mainrow+"");
        loc = loc + "&itemID=" + itemID.value;

        var receiptvoucherId  =  document.getElementById("receiptvoucherId"+mainrow+""+rownum+"");
        loc = loc + "&receiptvoucherId=" + receiptvoucherId.value;

		  var receiptvoucherline  =  document.getElementById("receiptvoucherline"+mainrow+""+rownum+"");
        loc = loc + "&receiptvoucherline=" + receiptvoucherline.value;

		  var receiptid  =  document.getElementById("vocreceiptid"+mainrow+""+rownum+"");
        loc = loc + "&receiptid=" + receiptid.value;

		  loc = loc + "&selectedrowid=" + selected_rowid;

		  if (confirm("This Will Remove This Match Against This Invoice Line.\n\n\nDo You Want to Proceed?"))
		  {
        openWinGeneric(loc,"undoReceiptInvocieLine","250","200","yes","40","80")
        }
    }
}

function addinvoiceLine(rownum)
{
    loc = "/tcmIS/Invoice/newinvoice?Action=addinvoiceLine&suppID=";

    var validpo  =  document.getElementById("validpo");
    if (validpo.value == "No")
    {
        alert("Please Enter a Valid PO.");
    }
    else
    {
        var suppid  =  document.getElementById("supplierid");
	     var supplierrep = suppid.value;
        supplierrep = supplierrep.replace(/&/gi, "%26");
        loc = loc + supplierrep;

		  var invoices  =  document.getElementById("invoices");
        loc = loc + "&invoiceId=" +invoices.value;

	     var po  =  document.getElementById("po");
        loc = loc + "&radianpo=" + po.value;

        var itemID  =  document.getElementById("itemidindetail"+rownum+"");
        loc = loc + "&itemID=" + itemID.value;

		  var polineunitprice  =  document.getElementById("polineunitprice"+rownum+"");
		  //alert(polineunitprice.value);
		  loc = loc + "&polineunitprice=" + polineunitprice.value;

			var noofmatchingitem  =  document.getElementById("noofmatchingitem"+rownum+"");
			var totalitemqty = 0;
			var totalitemqtytovoc = 0;
		
       	 var thisLineItemId  = document.getElementById("itemidindetail" + rownum).value;
         for(j=0;j<noofmatchingitem.value;j++)
         {

             var iteminvoicqty  =  document.getElementById("iteminvoicqty"+rownum+""+(j+1) + thisLineItemId +"");
             var iteminvoicqtytovoc  =  document.getElementById("iteminvoicqtytovoc"+rownum+""+(j+1) + thisLineItemId +"");

				 totalitemqty = totalitemqty + iteminvoicqty.value;
				 totalitemqtytovoc = totalitemqtytovoc + iteminvoicqtytovoc.value;
         }
         //alert (""+totalitemqty+"    "+totalitemqtytovoc+"");
         if (totalitemqty == totalitemqtytovoc)
         {
				loc = loc + "&notvouchered=yes";
         }

        loc = loc + "&selectedrowid=" + selected_rowid;

        openWinGeneric(loc,"addinvoiceLine","250","200","yes","40","80")
    }
}

function getnewinvoice(entered)
{
    loc = "/tcmIS/Invoice/newinvoice?Action=New&suppID=";

    var validpo  =  document.getElementById("validpo");
    if (validpo.value == "No")
    {
        alert("Please Enter a Valid PO.");
    }
    else
    {
        var suppid  =  document.getElementById("supplierid");
		  var supplierrep = suppid.value;
        supplierrep = supplierrep.replace(/&/gi, "%26");
        loc = loc + supplierrep;

		  var po  =  document.getElementById("po");
        loc = loc + "&radianpo=" + po.value;

        loc = loc + "&selectedrowid=" + selected_rowid;

		  var haascarrier  =  document.getElementById("haascarrier");
        loc = loc + "&haascarrier=" + haascarrier.value;

        var carriersuppid  =  document.getElementById("carriersuppid");
        loc = loc + "&carriersuppid=" + carriersuppid.value;

        openWinGeneric(loc,"getnewinvoice","700","500","yes","40","80")
    }
}

function editinvoice(entered)
{
    loc = "/tcmIS/Invoice/newinvoice?Action=editinvoice&suppID=";

    var validpo  =  document.getElementById("validpo");
	 var invoices  =  document.getElementById("invoices");
    if (validpo.value == "No")
    {
        alert("Please Enter a Valid PO.");
    }
 	 else if (invoices.value.trim().length == 0)
    {
        alert("Please Enter a Valid Invoice.");
    }
    else
    {
        var suppid  =  document.getElementById("supplierid");
	     var supplierrep = suppid.value;
        supplierrep = supplierrep.replace(/&/gi, "%26");
        loc = loc + supplierrep;

		  var invoices  =  document.getElementById("invoices");
        loc = loc + "&invoiceId=" +invoices.value;

	     var po  =  document.getElementById("po");
        loc = loc + "&radianpo=" + po.value;

        loc = loc + "&selectedrowid=" + selected_rowid;

        openWinGeneric(loc,"editinvoice"+invoices.value+"","700","500","yes","40","80")
    }
}

function resetinvoicestatus()
{
    loc = "/tcmIS/invoice/editInvoiceTerms?Action=Update&suppID=";

    var validpo  =  document.getElementById("validpo");
	 var invoices  =  document.getElementById("invoices");
    if (validpo.value == "No")
    {
        alert("Please Enter a Valid PO.");
    }
 	 else if (invoices.value.trim().length == 0)
    {
        alert("Please Enter a Valid Invoice.");
    }
    else
    {
        var suppid  =  document.getElementById("supplierid");
	     var supplierrep = suppid.value;
        supplierrep = supplierrep.replace(/&/gi, "%26");
        loc = loc + supplierrep;

		  var invoices  =  document.getElementById("invoices");
        loc = loc + "&vouchNumber=" +invoices.value;

	     var po  =  document.getElementById("po");
        loc = loc + "&radianpo=" + po.value;
        loc = loc + "&invstatus=In Progress";
        loc = loc + "&selectedrowid=" + selected_rowid;

        openWinGeneric(loc,"editinvoice"+invoices.value+"","200","200","yes","40","80")
    }
}


function qcvoucherstatus()
{
    loc = "/tcmIS/invoice/editInvoiceTerms?Action=qcVoucher&suppID=";

    var validpo  =  document.getElementById("validpo");
	 var invoices  =  document.getElementById("invoices");
    if (validpo.value == "No")
    {
        alert("Please Enter a Valid PO.");
    }
 	 else if (invoices.value.trim().length == 0)
    {
        alert("Please Enter a Valid Invoice.");
    }
    else
    {
        var suppid  =  document.getElementById("supplierid");
	     var supplierrep = suppid.value;
        supplierrep = supplierrep.replace(/&/gi, "%26");
        loc = loc + supplierrep;

		  var invoices  =  document.getElementById("invoices");
        loc = loc + "&vouchNumber=" +invoices.value;

	     var po  =  document.getElementById("po");
        loc = loc + "&radianpo=" + po.value;
        loc = loc + "&selectedrowid=" + selected_rowid;

        openWinGeneric(loc,"qcinvoice"+invoices.value+"","200","200","yes","40","80")
    }
}


/*function editbillingAddress(entered)
{
    loc = "/tcmIS/Invoice/newinvoice?Action=editbillingAddress&suppID=";

    var suppid  =  document.getElementById("supplierid");
    if (!suppid.value.trim().length > 0 )
    {
        alert("Please Enter a Valid PO.");
    }
    else
    {
        //var suppid  =  document.getElementById("supplierid");
		  var supplierrep = suppid.value;
        supplierrep = supplierrep.replace(/&/gi, "%26");
        loc = loc + supplierrep;

		  //var po  =  document.getElementById("po");
        //loc = loc + "&radianpo=" + po.value;

		  loc = loc + "&selectedrowid=" + selected_rowid;

        openWinGeneric(loc,"editbillingAddress","550","300","yes","40","80")
    }
}*/

function getInvoiceData(entered)
{
    loc = "/tcmIS/Invoice/newinvoice?Action=getInvoiceData&suppID=";
    var validpo  =  document.getElementById("validpo");
    if (validpo.value == "No")
    {
        alert("Please Enter a Valid PO.");
    }
    else
    {
        var suppid  =  document.getElementById("supplierid");
	     var supplierrep = suppid.value;
        supplierrep = supplierrep.replace(/&/gi, "%26");
        loc = loc + supplierrep;

        var po  =  document.getElementById("po");
        loc = loc + "&radianpo=" + po.value;

		  var invoices  =  document.getElementById("invoices");
        loc = loc + "&invoiceId=" +invoices.value;

        loc = loc + "&selectedrowid=" + selected_rowid;

        openWinGeneric(loc,"getInvoiceData","50","50","yes","40","80")
    }
    document.getElementById('supplierInvoiceId').value = invoices.options[invoices.selectedIndex].text;
    document.getElementById('messagerow').innerHTML = "";
}


function getChargeItemDetail(rowname)
{
	 var validpo  =  document.getElementById("validpo");

    var mytable = document.getElementById("linetable");
    var allTRs = mytable.getElementsByTagName("tr");

	 if (allTRs.length > 0)
    {
	    if ( validpo.value == "No" )
	    {
	         alert("Please Enter a Valid PO.");
	    }
	    else
	    {
	    loc = "/tcmIS/Invoice/invitem?lineID=";
	    loc = loc + "&itemID=";
	    loc = loc + "&subUserAction=lineCharge";

	    var po  =  document.getElementById("po");
	    loc = loc +"&po="+ po.value;

		 loc = loc + "&selectedrowid=" + selected_rowid;

	    openWinGeneric(loc,"getChargeItemDetail","50","50","yes","10","20")
	    }
	 }
	 else
	 {
		alert("Please Enter a Valid PO.");
	 }
}

function getPo()
{
    loc = "/tcmIS/Invoice/AccountsPayable?po=";
    //loc = "/tcmIS/purchase/popo?radianPO=";

    var po  =  document.getElementById("po");

	 if ( isFloat(po.value.trim()) )
    {
    loc = loc + po.value;
    loc = loc + "&Action=searchlike&subUserAction=po";
    //loc = loc + "&Action=po&subUserAction=po";

    document.location=loc;
    //openWinGeneric(loc,"radianPO","50","50","yes")
    }
    else
    {
			alert("Please Enter a Valid PO Number. It should be a Whole Number.");
    }
}


function donothing(entered)
{
    loc = "/tcmIS/Invoice/AccountsPayable?po=";
    var po  =  document.getElementById("po");

    if (po.value.trim().length > 0 && isFloat(po.value.trim()) )
    {
    loc = loc + po.value;
    loc = loc + "&Action=searchlike&subUserAction=po";
    document.location=loc;
    }
    else
    {
			alert("Please Enter a Valid PO Number. It should be a Whole Number.");
    }
    return false;
}

function getPoInvoice(voucherId)
{
    loc = "/tcmIS/Invoice/AccountsPayable?po=";
    var po  =  document.getElementById("po");

	 if ( isFloat(po.value.trim()) )
    {
    loc = loc + po.value;
    loc = loc + "&Action=searchlike&subUserAction=po";
    loc = loc + "&selectedInvoiceId="+voucherId+"";
    //loc = loc + "&Action=po&subUserAction=po";

    document.location=loc;
    //openWinGeneric(loc,"radianPO","50","50","yes")
    }
    else
    {
			alert("Please Enter a Valid PO Number. It should be a Whole Number.");
    }
}

function recalPo (poNumber,message,subuserAction,hubName,selectedRowhere,selectedInvoiceId)
{
    try
    {
     parent.resetTimer();
    }
    catch(exap){
    //alert("Here recalPo");
    }

    loc = "/tcmIS/Invoice/accpayable?radianPO=";
    loc = loc + poNumber;

	 if (isFloat(poNumber))
    {
    loc = loc + "&Action=recalpo&subUserAction=" + subuserAction;
    loc = loc + "&updateMsg="+ message;
    loc = loc + "&HubName="+ hubName;
    loc = loc + "&selectedrowid=" + selectedRowhere;
    loc = loc + "&selectedInvoiceId=" + selectedInvoiceId;

    openWinGeneric(loc,"apRadianPO"+poNumber+""+selectedInvoiceId+"","50","50","yes","50","50")
    }
    else
    {
			alert("Please Enter a Valid PO Number. It should be a Whole Number.");
    }
}