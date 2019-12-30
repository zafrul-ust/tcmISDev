var submitcount=0;
var updatecount=0;
var max = 20 //maximum rows
var color = "#0000ff";
var selected_row = 0;
var selected_rowid = "row1";

String.prototype.trim = function()
{
// skip leading and trailing whitespace
// and return everything in between
  return this.replace(/^\s*(\b.*\b|)\s*$/, "$1");
}

function SubmitOnlyOnce()
{
    if (submitcount == 0)
    {
        submitcount++;
        return true;
    }
    else
    {
        alert("This form has already been submitted.  Thanks!");
        return false;
    }
}

function countrychanged(object)
{
   var artist;
   artist = object.options[object.selectedIndex].value;

  var zipplus  =  document.getElementById("zipplus");
  if( artist == 'USA' || artist == 'PRI' || artist == 'MEX'  || artist == 'BRA')
  	zipplus.style.display="";
  else
  {
      zipplus.style.display="none";
      zipplus.value="";
  }

   var selectedIndex = object.selectedIndex;

	for (var i = document.SupplierLike.state_abbrev.length; i > 0;i--)
   {
      document.SupplierLike.state_abbrev.options[i] = null;
   }
	showinvlinks(artist);
	window.document.SupplierLike.state_abbrev.selectedIndex = 0;
}

function showinvlinks(selectedIndex)
{
    var invgrpid = new Array();
    invgrpid = altinvid[selectedIndex];

	 var invgrpName = new Array();
    invgrpName = altinvName[selectedIndex];

    for (var i=0; i < invgrpid.length; i++)
    {
        if (invgrpid[i] != 'PQ')
        {
          setCab(i,invgrpName[i],invgrpid[i]);
        }
        else
        {
          setCab(i,invgrpName[i],'QC');
        }
    }
}

function setCab(href,text,id)
{
    var optionName = new Option(text, id, false, false)
    document.SupplierLike.state_abbrev.options[href] = optionName;
}


function checkaddchgtotals(name, entered)
{
   var actvalue = name.toString();

	var result ;
   var finalMsgt;
   var allClear = 0;
	finalMsgt = "";
	var invoices  =  document.getElementById("invoices");
	var remainingqty = document.getElementById(""+invoices.value+"");

	invoicecreditqty = document.getElementById("invoicecreditqty");
   invcreditunitprice = document.getElementById("invcreditunitprice");

	 if ( !(isSignedFloat(invoicecreditqty.value)) )
    {
        finalMsgt = finalMsgt + " Please Enter Valid Credit Quantity.\n" ;
        allClear = 1;
    }
    else if ( invoicecreditqty.value <= 0 )
    {
        finalMsgt = finalMsgt + " Please Enter Valid Credit Quantity.\n" ;
        allClear = 1;
    }
    else if ( invoicecreditqty.value.trim().length == 0 )
    {
        finalMsgt = finalMsgt + " Please Enter Valid Credit Quantity.\n" ;
        allClear = 1;
    }

	 if ( !(isSignedFloat(invcreditunitprice.value)) )
	 {
        finalMsgt = finalMsgt + " Please Enter Valid Credit Unit Price.\n" ;
        allClear = 1;
    }
    else if ( invcreditunitprice.value > 0 )
    {
        finalMsgt = finalMsgt + " Please Enter Valid Credit Unit Price.\n" ;
        allClear = 1;
    }
    else if ( invcreditunitprice.value.trim().length == 0 )
    {
        finalMsgt = finalMsgt + " Please Enter Valid Credit Unit Price.\n" ;
        allClear = 1;
    }

	if (allClear < 1)
   {
        result = true;
        return result;
    }
    else
    {
        alert(finalMsgt);
        result = false;
        return result;
    }
}

function checkAddStateCode()
{
 var result ;
 var finalMsgt;
 var allClear = 0;
 finalMsgt = "Please enter valid values for\n";

 var c1 = document.getElementById("countryabbrev").value.trim();
 var z1 = document.getElementById("zip").value.trim();
 var z2 = document.getElementById("zipplus").value.trim();
 if( !zipCheck(c1,z1,z2) ) {
    finalMsgt = finalMsgt + "Zip.\n" ;
    allClear = 1;
 }

 var locationdesc = document.getElementById("locationdesc");
 if ( locationdesc.value.trim().length == 0 )
 {
        finalMsgt = finalMsgt + "At least 1 line of address.\n" ;
        allClear = 1;
 }

 var city = document.getElementById("city");
 if ( city.value.trim().length == 0 )
 {
        finalMsgt = finalMsgt + "City.\n" ;
        allClear = 1;
 }

 if (allClear < 1)
 {
    result = true;
    return result;
 }
 else
 {
    alert(finalMsgt);
    result = false;
    return result;
 }
}

function setvoucherline( rownum,mainrow,credit )
   {
   var result;
   var finalMsgt;
   var allClear=0;
   finalMsgt="";

   var invoicechoosen=document.getElementById( "invoicechoosen" + mainrow + "" + rownum + "" );
   var vouchertext=invoicechoosen.value;

if ( invoicechoosen.value.trim().length > 0 )
{
  var voucarray=vouchertext.split( "-" );

  var receiptvoucherId=document.getElementById( "receiptvoucherId" + mainrow + "" + rownum + "" );
  receiptvoucherId.value=voucarray[0];

  var receiptvoucherline=document.getElementById( "receiptvoucherline" + mainrow + "" + rownum + "" );
  /*if (voucarray[0].trim().length == 0 )
           {
      receiptvoucherline.value = "";
           }
           else*/
  {
    receiptvoucherline.value=voucarray[1];
  }

  var receiptinvoicqtyvoc=document.getElementById( "receiptinvoicqtyvoc" + mainrow + "" + rownum + "" );
  var absreceiptinvoicqtyvoc=receiptinvoicqtyvoc.value;


  if ( receiptinvoicqtyvoc.value.trim().length > 0 )
  {
    var receiptvoucherId1=document.getElementById( "receiptvoucherId" + mainrow + "" + rownum + "" );
    var receiptvoucherline1=document.getElementById( "receiptvoucherline" + mainrow + "" + rownum + "" );

    var noofmatchingitem=document.getElementById( "noofmatchingitem" + mainrow + "" );
	var thisLineItemId  = document.getElementById("itemidindetail" + mainrow).value;
    for ( j=0; j < noofmatchingitem.value; j++ )
    {
      var itemvoucherId=document.getElementById( "itemvoucherId" + mainrow + "" + ( j + 1 ) + thisLineItemId + "" );
      var itemvoucherline=document.getElementById( "itemvoucherline" + mainrow + "" + ( j + 1 ) + thisLineItemId + "" );
      var lineitemid=document.getElementById( "lineitemid" + mainrow + "" + ( j + 1 ) + thisLineItemId + "" );

      if ( ( itemvoucherId.value == receiptvoucherId1.value ) && ( itemvoucherline.value == receiptvoucherline1.value ) )
      {
        var iteminvoicqtytovoc=document.getElementById( "iteminvoicqtytovoc" + mainrow + "" + ( j + 1 ) + thisLineItemId + "" );
        var absiteminvoicqtytovoc=iteminvoicqtytovoc.value;
        if ( "Y" == credit || "y" == credit )
        {
          if ( absreceiptinvoicqtyvoc * 1 < absiteminvoicqtytovoc * 1 )
          {
            finalMsgt=finalMsgt + "You Cannot Assign This Invoice for Qty " + receiptinvoicqtyvoc.value + ".\nWhich is More Than " + iteminvoicqtytovoc.value + ", the Qty 										Remaining to Invoice.\n";
            receiptinvoicqtyvoc.value="";
            allClear=1;
          }

        }
        else
        {
          //alert("setvoucherline    " +absreceiptinvoicqtyvoc+"   "+absiteminvoicqtytovoc+"   "+credit);
          if ( (lineitemid.value*1 == 430158) || (lineitemid.value*1 == 500743) )
          {
				if ( absreceiptinvoicqtyvoc > 0 )
          	{
            finalMsgt=finalMsgt + "You Cannot Assign a +Ve Qty For this Invoice.\n";
            receiptinvoicqtyvoc.value="";
            allClear=1;
          	}
          }
          else if ( absreceiptinvoicqtyvoc < 0 )
          {
            finalMsgt=finalMsgt + "You Cannot Assign a -Ve Qty For this Invoice.\n";
            receiptinvoicqtyvoc.value="";
            allClear=1;
          }

          else if ( absreceiptinvoicqtyvoc * 1 > absiteminvoicqtytovoc * 1 )
          {
            finalMsgt=finalMsgt + "You Cannot Assign This Invoice for Qty " + receiptinvoicqtyvoc.value + ".\nWhich is More Than " + iteminvoicqtytovoc.value + ", the Qty Remaining to Invoice.\n";
            receiptinvoicqtyvoc.value="";
            allClear=1;
          }
        }
      }
    }
  }

}

  if ( allClear < 1 )
  {
    result=true;
    return result;
  }
  else
  {
    alert( finalMsgt );
    var receiptinvoicqtyvocextprice=document.getElementById( "receiptinvoicqtyvocextprice" + mainrow + "" + rownum + "" );
    receiptinvoicqtyvocextprice.value="";
    result=false;
    return result;
  }
}


function setvouchercreditline(rownum,mainrow)
{
      var result ;
      var finalMsgt;
      var allClear = 0;
      finalMsgt = "";

      var invoicreditcechoosen  =  document.getElementById("invoicreditcechoosen"+mainrow+""+rownum+"");
      if (invoicreditcechoosen.value.trim().length > 0 )
      {
		var vouchertext = invoicreditcechoosen.value;
		var voucarray = vouchertext.split("-");

		var receiptvoucherId  =  document.getElementById("receiptvoucherId"+mainrow+""+rownum+"");
		receiptvoucherId.value = voucarray[0];

		var receiptvoucherline  =  document.getElementById("receiptvoucherline"+mainrow+""+rownum+"");
		/*if (voucarray[0].trim().length == 0 )
		{
			receiptvoucherline.value = "";
		}
		else*/
		{
			receiptvoucherline.value = voucarray[1];
		}


      var receiptcreditqty  =  document.getElementById("receiptcreditqty"+mainrow+""+rownum+"");
      var absvoccreditQty = Math.abs(receiptcreditqty.value);

         var receiptvoucherId  =  document.getElementById("receiptvoucherId"+mainrow+""+rownum+"");
         var receiptvoucherline  =  document.getElementById("receiptvoucherline"+mainrow+""+rownum+"");

         var noofmatchingitem  =  document.getElementById("noofmatchingitem"+mainrow+"");
     	 var thisLineItemId  = document.getElementById("itemidindetail" + mainrow).value;
         for(j=0;j<noofmatchingitem.value;j++)
         {
             var itemvoucherId  =  document.getElementById("itemvoucherId"+mainrow+""+(j+1) + thisLineItemId +"");
             var itemvoucherline  =  document.getElementById("itemvoucherline"+mainrow+""+(j+1) + thisLineItemId +"");

             if ( (itemvoucherId.value == receiptvoucherId.value) && (itemvoucherline.value == receiptvoucherline.value) )
             {
                 var iteminvoicqtytovoc  =  document.getElementById("iteminvoicqtytovoc"+mainrow+""+(j+1)+ thisLineItemId +"");
                 var absiteminvoicqtytovoc = Math.abs(iteminvoicqtytovoc.value);
                 //alert(absvoccreditQty+"   "+absiteminvoicqtytovoc);
                 if (absvoccreditQty > absiteminvoicqtytovoc)
                 {
                        finalMsgt = finalMsgt + " You Can Not Credit More Than "+iteminvoicqtytovoc.value+", the Qty Credited Against This Invoice.\n" ;
                        receiptcreditqty.value = "";
                        allClear = 1;
                 }
             }
         }

    }

    if (allClear < 1)
    {
      result = true;
      return result;
      }
      else
      {
      alert(finalMsgt);
      result = false;
      return result;
   }

}

function checkreceiptextprice( rownum,mainrow,credit )
   {
   var result;
   var finalMsgt;
   var allClear=0;
   finalMsgt="";

   var receiptinvoicqtyvoc=document.getElementById( "receiptinvoicqtyvoc" + mainrow + "" + rownum + "" );
   var absreceiptinvoicqtyvoc=receiptinvoicqtyvoc.value;

if ( receiptinvoicqtyvoc.value.trim().length > 0 )
{
  if ( ! ( isSignedFloat( receiptinvoicqtyvoc.value ) ) )
  {
    finalMsgt=finalMsgt + " Please Enter Valid Quantity.\n";
    receiptinvoicqtyvoc.value="";
    allClear=1;
  }

  var receiptqty=document.getElementById( "receiptqty" + mainrow + "" + rownum + "" );
  var absreceiptqty=receiptqty.value;

  if ( "Y" == credit || "y" == credit )
  {
    if ( absreceiptinvoicqtyvoc * 1 < absreceiptqty * 1 )
    {
      finalMsgt=finalMsgt + " You Cannot Assign More Than the Receieved Qty.\n";
      receiptinvoicqtyvoc.value="";
      allClear=1;
    }
  }
  else
  {
   var lineitemid1=document.getElementById( "lineitemid" + mainrow + "" + rownum + "" );
	if ( (lineitemid1.value*1 == 430158) || (lineitemid1.value*1 == 500743) )
   {
		if ( absreceiptinvoicqtyvoc > 0 )
      {
      finalMsgt=finalMsgt + " You Cannot Assign a +Ve Qty.\n";
      receiptinvoicqtyvoc.value="";
      allClear=1;
     }
   }
   else if ( absreceiptinvoicqtyvoc < 0 )
    {
      finalMsgt=finalMsgt + " You Cannot Assign a -Ve Qty.\n";
      receiptinvoicqtyvoc.value="";
      allClear=1;
    }
    else if ( ( absreceiptinvoicqtyvoc * 1 > absreceiptqty * 1 ) )
    {
      finalMsgt=finalMsgt + " You Cannot Assign More Than the Receieved Qty.\n";
      receiptinvoicqtyvoc.value="";
      allClear=1;
    }
  }

  var invoicechoosen=document.getElementById( "invoicechoosen" + mainrow + "" + rownum + "" );
  if ( invoicechoosen.value.trim().length > 0 )
  {
    var receiptvoucherId=document.getElementById( "receiptvoucherId" + mainrow + "" + rownum + "" );
    var receiptvoucherline=document.getElementById( "receiptvoucherline" + mainrow + "" + rownum + "" );

    var noofmatchingitem=document.getElementById( "noofmatchingitem" + mainrow + "" );
	 var thisLineItemId  = document.getElementById("itemidindetail" + mainrow).value;
    for ( j=0; j < noofmatchingitem.value; j++ )
    {
      var itemvoucherId=document.getElementById( "itemvoucherId" + mainrow + "" + ( j + 1 ) + thisLineItemId + "" );
      var itemvoucherline=document.getElementById( "itemvoucherline" + mainrow + "" + ( j + 1 ) + thisLineItemId + "" );
      var lineitemid=document.getElementById( "lineitemid" + mainrow + "" + ( j + 1 ) + thisLineItemId + "" );

      if ( ( itemvoucherId.value == receiptvoucherId.value ) && ( itemvoucherline.value == receiptvoucherline.value ) )
      {
        var iteminvoicqtytovoc=document.getElementById( "iteminvoicqtytovoc" + mainrow + "" + ( j + 1 ) + thisLineItemId + "" );
        var absiteminvoicqtytovoc=iteminvoicqtytovoc.value;
        //alert( "checkreceiptextprice    " + absreceiptinvoicqtyvoc + "   " + absiteminvoicqtytovoc + "   " + credit );
        if ( "Y" == credit || "y" == credit )
        {
          if ( absreceiptinvoicqtyvoc * 1 < absiteminvoicqtytovoc * 1 )
          {
            finalMsgt=finalMsgt + " You Cannot Assign More Than " + iteminvoicqtytovoc.value + ", the Qty Remaining to Invoice.\n";
            receiptinvoicqtyvoc.value="";
            allClear=1;
          }
        }
        else
        {
          if ( (lineitemid.value*1 == 430158) || (lineitemid.value*1 == 500743) )
          {
				if ( absreceiptinvoicqtyvoc > 0 )
          	{
            finalMsgt=finalMsgt + "You Cannot Assign a +Ve Qty For this Invoice.\n";
            receiptinvoicqtyvoc.value="";
            allClear=1;
          	}
          }
          else if ( absreceiptinvoicqtyvoc < 0 )
          {
            finalMsgt=finalMsgt + " You Cannot Assign -Ve Qty to this Invoice.\n";
            receiptinvoicqtyvoc.value="";
            allClear=1;
          }
          else if ( ( absreceiptinvoicqtyvoc * 1 > absiteminvoicqtytovoc * 1 ) )
          {
            finalMsgt=finalMsgt + " You Cannot Assign More Than " + iteminvoicqtytovoc.value + ", the Qty Remaining to Invoice.\n";
            receiptinvoicqtyvoc.value="";
            allClear=1;
          }
        }
      }
    }
  }

}

if ( allClear < 1 )
{
  result=true;

  var receiptunitprice=document.getElementById( "receiptunitprice" + mainrow + "" + rownum + "" );
  var receiptinvoicqtyvocextprice=document.getElementById( "receiptinvoicqtyvocextprice" + mainrow + "" + rownum + "" );

  receiptinvoicqtyvocextprice.value= ( receiptunitprice.value * receiptinvoicqtyvoc.value );

  return result;
}
else
{
  alert( finalMsgt );
  var receiptinvoicqtyvocextprice=document.getElementById( "receiptinvoicqtyvocextprice" + mainrow + "" + rownum + "" );
  receiptinvoicqtyvocextprice.value="";
  result=false;
  return result;
}
}

function checkcreditmemoqty(rownum,mainrow)
{
		var result ;
	   var finalMsgt;
	   var allClear = 0;
	   finalMsgt = "";

		var receiptcreditqty  =  document.getElementById("receiptcreditqty"+mainrow+""+rownum+"");
      if (receiptcreditqty.value.trim().length > 0 )
      {
          var absvoccreditQty = Math.abs(receiptcreditqty.value);
          if ( !( isSignedFloat(receiptcreditqty.value) && (receiptcreditqty.value < 0) ) )
          {
               finalMsgt = finalMsgt + " Please Enter Valid Negative Quantity.\n" ;
               receiptcreditqty.value = "";
               allClear = 1;
          }

          var receiptqty  =  document.getElementById("receiptqty"+mainrow+""+rownum+"");
          var absreceiptqty = Math.abs(receiptqty.value);

          if (absvoccreditQty > absreceiptqty)
          {
          finalMsgt = finalMsgt + " You Cannot Credit More Than the Receipt Qty.\n" ;
          receiptcreditqty.value = "";
          allClear = 1;
          }

      var invoicreditcechoosen  =  document.getElementById("invoicreditcechoosen"+mainrow+""+rownum+"");
      if (invoicreditcechoosen.value.trim().length > 0 )
      {
         var receiptvoucherId  =  document.getElementById("receiptvoucherId"+mainrow+""+rownum+"");
         var receiptvoucherline  =  document.getElementById("receiptvoucherline"+mainrow+""+rownum+"");

         var noofmatchingitem  =  document.getElementById("noofmatchingitem"+mainrow+"");
     	 var thisLineItemId  = document.getElementById("itemidindetail" + mainrow).value;
         for(j=0;j<noofmatchingitem.value;j++)
         {
             var itemvoucherId  =  document.getElementById("itemvoucherId"+mainrow+""+(j+1)+ thisLineItemId +"");
             var itemvoucherline  =  document.getElementById("itemvoucherline"+mainrow+""+(j+1)+ thisLineItemId +"");

             if ( (itemvoucherId.value == receiptvoucherId.value) && (itemvoucherline.value == receiptvoucherline.value) )
             {
                 var iteminvoicqtytovoc  =  document.getElementById("iteminvoicqtytovoc"+mainrow+""+(j+1)+ thisLineItemId +"");
                 var absiteminvoicqtytovoc = Math.abs(iteminvoicqtytovoc.value);
                 //alert(absvoccreditQty+"   "+absiteminvoicqtytovoc);
                 if (absvoccreditQty > absiteminvoicqtytovoc)
                 {
                        finalMsgt = finalMsgt + " You Cannot Credit More Than "+iteminvoicqtytovoc.value+", the Qty Credited Against This Invoice.\n" ;
                        receiptcreditqty.value = "";
                        allClear = 1;
                 }
             }
         }
      }

      }


		if (allClear < 1)
	   {
	        result = true;
	        return result;
	    }
	    else
	    {
	        alert(finalMsgt);
	        result = false;
	        return result;
	    }
}


function checkCreditCorrectiontotals(name, entered)
{
   var actvalue = name.toString();

	var result ;
   var finalMsgt;
   var allClear = 0;
	finalMsgt = "";
	var invoices  =  document.getElementById("invoices");
	var remainingqty = document.getElementById(""+invoices.value+"");

	invoicecreditqty = document.getElementById("invoicecreditqty");
   invcreditunitprice = document.getElementById("invcreditunitprice");

	 if ( !(isSignedFloat(invoicecreditqty.value)) )
    {
        finalMsgt = finalMsgt + " Please Enter Valid Credit Quantity.\n" ;
        allClear = 1;
    }
    else if ( invoicecreditqty.value.trim().length == 0 )
    {
        finalMsgt = finalMsgt + " Please Enter Valid Credit Quantity.\n" ;
        allClear = 1;
    }

	 if ( !(isFloat(invcreditunitprice.value)) )
	 {
        finalMsgt = finalMsgt + " Please Enter Valid Credit Unit Price.\n" ;
        allClear = 1;
    }
    else if ( invcreditunitprice.value.trim().length == 0 )
    {
        finalMsgt = finalMsgt + " Please Enter Valid Credit Unit Price.\n" ;
        allClear = 1;
    }

	invoicedebitqty = document.getElementById("invoicedebitqty");
   invdebitunitprice = document.getElementById("invdebitunitprice");

	 if ( invoicedebitqty.value.trim().length == 0 )
    {
        finalMsgt = finalMsgt + " Please Enter Valid Debit Quantity.\n" ;
        allClear = 1;
    }
	 else if (invoicedebitqty.value.trim().length > 0) {
	 	if ( !(isFloat(invoicedebitqty.value)) )
    	{
        finalMsgt = finalMsgt + " Please Enter Valid Debit Quantity.\n" ;
        allClear = 1;
    	}

		if ( !(isFloat(invdebitunitprice.value)) )
	 	{
        finalMsgt = finalMsgt + " Please Enter Valid Debit Unit Price.\n" ;
        allClear = 1;
    	}
    }else {
		if ( invdebitunitprice.value.trim().length > 0 ) {
	 		if ( !(isFloat(invdebitunitprice.value)) )
	 		{
	 			finalMsgt = finalMsgt + " Please Enter Valid Debit Quantity.\n" ;
        		finalMsgt = finalMsgt + " Please Enter Valid Debit Unit Price.\n" ;
        		allClear = 1;
    		}else {
				finalMsgt = finalMsgt + " Please Enter Valid Debit Quantity.\n" ;
        		allClear = 1;
    		}
    	}
    }

	 /*var creditTotalPrice = (invoicecreditqty.value*invcreditunitprice.value);
	 var debitTotalPrice = (invoicedebitqty.value*invdebitunitprice.value);

	 var lineTotalPrice = (debitTotalPrice - creditTotalPrice);

	 if ( Math.abs(lineTotalPrice*1) < Math.abs((remainingqty.value*1).toFixed(2)) || lineTotalPrice*1 == (remainingqty.value*1).toFixed(2) )
	 {

	 }
	 else
	 {
		finalMsgt = finalMsgt + "\nYou Cannot Excede the Amount $"+remainingqty.value*1+".\nWhich is the Remainging Amount to be Invoiced on This Invoice.\n";
		allClear = 1;
	 }*/

	if (allClear < 1)
   {
        result = true;
        return result;
    }
    else
    {
        alert(finalMsgt);
        result = false;
        return result;
    }
}

function checkrmatotals(name, entered)
{
   var actvalue = name.toString();

	var result ;
   var finalMsgt;
   var allClear = 0;
	finalMsgt = "";
	var invoices  =  document.getElementById("invoices");
	var remainingqty = document.getElementById(""+invoices.value+"");

	invoicecreditqty = document.getElementById("invoicecreditqty");
   invcreditunitprice = document.getElementById("invcreditunitprice");

	 if ( !(isSignedFloat(invoicecreditqty.value)) )
    {
        finalMsgt = finalMsgt + " Please Enter Valid Credit Quantity.\n" ;
        allClear = 1;
    }
    else if ( invoicecreditqty.value.trim().length == 0 )
    {
        finalMsgt = finalMsgt + " Please Enter Valid Credit Quantity.\n" ;
        allClear = 1;
    }

	 if ( !(isFloat(invcreditunitprice.value)) )
	 {
        finalMsgt = finalMsgt + " Please Enter Valid Credit Unit Price.\n" ;
        allClear = 1;
    }
    else if ( invcreditunitprice.value.trim().length == 0 )
    {
        finalMsgt = finalMsgt + " Please Enter Valid Credit Unit Price.\n" ;
        allClear = 1;
    }

	if (allClear < 1)
   {
        result = true;
        return result;
    }
    else
    {
        alert(finalMsgt);
        result = false;
        return result;
    }
}

function checkInvocietotals(name, entered)
{
   var actvalue = name.toString();

	var result ;
   var finalMsgt;
   var allClear = 0;
	finalMsgt = "";
	var invoices  =  document.getElementById("invoices");
	var remainingqty = document.getElementById(""+invoices.value+"");

	invoiceqty = document.getElementById("invoiceqty");
   invunitprice = document.getElementById("invunitprice");
   itemID = document.getElementById("itemID");

    if ( (itemID.value*1 == 430158) || (itemID.value*1 == 500743) )
  	 {
	   if ( invoiceqty.value > 0 )
		{
	     finalMsgt = finalMsgt + " Please Enter Valid Quantity.Only negative values allowed\n" ;
        allClear = 1;
		}
		else if ( invoiceqty.value.trim().length == 0 )
      {
        finalMsgt = finalMsgt + " Please Enter Valid Quantity.\n" ;
        allClear = 1;
      }
    }
	 else if ( !(isFloat(invoiceqty.value)) )
    {
        finalMsgt = finalMsgt + " Please Enter Valid Quantity.\n" ;
        allClear = 1;
    }
    else if ( invoiceqty.value.trim().length == 0 )
    {
        finalMsgt = finalMsgt + " Please Enter Valid Quantity.\n" ;
        allClear = 1;
    }

	 if ( !(isSignedFloat(invunitprice.value)) )
	 {
        finalMsgt = finalMsgt + " Please Enter Valid Unit Price.\n" ;
        allClear = 1;
    }
    else if ( invunitprice.value.trim().length == 0 )
    {
        finalMsgt = finalMsgt + " Please Enter Valid Unit Price.\n" ;
        allClear = 1;
    }

	 /*var lineTotalPrice = (invoiceqty.value*invunitprice.value).toFixed(2);
	 if ( lineTotalPrice*1 < (remainingqty.value*1).toFixed(2) || lineTotalPrice*1 == (remainingqty.value*1).toFixed(2) )
	 {

	 }
	 else
	 {
		finalMsgt = finalMsgt + "\nYou Cannot Excede the Amount $"+remainingqty.value*1+".\nWhich is the Remainging Amount to be Invoiced on This Invoice.\n";
		allClear = 1;
	 }*/

	if (allClear < 1)
   {
        result = true;
        return result;
    }
    else
    {
        alert(finalMsgt);
        result = false;
        return result;
    }
}

function checkInvocieValues(name, entered)
{
   var actvalue = name.toString();

	var result ;
   var finalMsgt;
   var allClear = 0;

   finalMsgt = "Please Enter Valid Values For: \n\n";

	 var voucherbillinglocId  =  document.getElementById("voucherbillinglocId");
    if (!voucherbillinglocId.value.trim().length > 0)
    {
       finalMsgt = finalMsgt + " Valid Voucher Billing Address.\n" ;
    }

    var invoices  =  document.getElementById("invoices");
    if (!invoices.value.trim().length > 0)
    {
       finalMsgt = finalMsgt + " Valid Invoice.\n" ;
    }

    currency = document.getElementById("currency");
    if ( currency.value.trim().length == 0 )
    {
        finalMsgt = finalMsgt + " Valid Currency.\n" ;
        allClear = 1;
    }

	 var invoicedate =  document.getElementById("invoicedate");
    if (invoicedate.value.trim().length == 10 )
    {
        if ( checkdate(invoicedate) == false )
        {
			 finalMsgt = finalMsgt + " Valid Invoice Date in  MM/DD/YYYY Format.\n" ;
	       allClear = 1;
        }
    }
	 else
    {
			 finalMsgt = finalMsgt + " Valid Invoice Date in  MM/DD/YYYY Format.\n" ;
	       allClear = 1;
    }
	 //make sure that invoice date is not greater than today
    var now = new Date();
    var temp = new Date(invoicedate.value.trim());
    if (temp.getTime() > now.getTime()) {
        finalMsgt = finalMsgt + "Invoice Date cannot be greater than today's date.\n";
        allClear = 1;
    }

	var janu1995Day = new Date(1995, 0, 1);
	if (temp.getTime() < janu1995Day.getTime()) {
        finalMsgt = finalMsgt + "Invoice Date cannot be so far in the past.\n";
        allClear = 1;
    }

	var invdatereceived =  document.getElementById("invdatereceived");
	if (invdatereceived.value.trim().length == 10 )
	{
        if ( checkdate(invdatereceived) == false )
        {
			 finalMsgt = finalMsgt + " Valid Date Invoice Received Date in  MM/DD/YYYY Format.\n" ;
	       allClear = 1;
        }
    }
	else
	{
			 finalMsgt = finalMsgt + " Valid Date Invoice Received Date in  MM/DD/YYYY Format.\n" ;
	       allClear = 1;
    }
	//make sure that recieved date is not greater than today
   var temp = new Date(invdatereceived.value.trim());
   if (temp.getTime() > now.getTime()) {
       finalMsgt = finalMsgt + "Invoice Date Received cannot be greater than today's date.\n";
       allClear = 1;
   }

	if (temp.getTime() < janu1995Day.getTime()) {
        finalMsgt = finalMsgt + "Invoice Date Received cannot be so far in the past.\n";
        allClear = 1;
   }

	var invamount = document.getElementById("invamount");
	if (invamount.value.trim().length > 0)
	{
		if ( !(isSignedFloat(invamount.value)) )
   	{
       finalMsgt = finalMsgt + " Valid Invoice Amount.\n" ;
       allClear = 1;
   	}
   }
   else
   {
		 finalMsgt = finalMsgt + " Valid Invoice Amount.\n" ;
       allClear = 1;
   }

    var taxAmount1 = document.getElementById("taxAmount1");
	if (taxAmount1.value.trim().length > 0)
	{
	 if ( !(isSignedFloat(taxAmount1.value)) )
   	 {
       finalMsgt = finalMsgt + " Valid Tax 1 Amount.\n" ;
       allClear = 1;
   	 }

     var taxCurrency1 = document.getElementById("taxCurrency1");
     if ( taxCurrency1.value.trim().length == 0 )
     {
        finalMsgt = finalMsgt + " Valid Tax 1 Currency.\n" ;
        allClear = 1;
     }
    }


    var taxAmount2 = document.getElementById("taxAmount2");
	if (taxAmount2.value.trim().length > 0)
	{
	 if ( !(isSignedFloat(taxAmount2.value)) )
   	 {
       finalMsgt = finalMsgt + " Valid Tax 2 Amount.\n" ;
       allClear = 1;
   	 }

     var taxCurrency2 = document.getElementById("taxCurrency2");
     if ( taxCurrency2.value.trim().length == 0 )
     {
        finalMsgt = finalMsgt + " Valid Tax 2 Currency.\n" ;
        allClear = 1;
     }
    }

    var invstatus  =  document.getElementById("invstatus");
	if (allClear < 1)
   {
    result = true;
    for (var i=0; i < supplierInvoiceIdArray.length; i++)
    {
        if (supplierInvoiceIdArray[i] == invoices.value.trim())
        {
             if (confirm("This Invoice ("+invoices.value.trim()+") has already been entered once.\n\nDo you Want to Proceed"))
	          {

	          }
	          else
	          {
	            result = false;
	            return result;
	          }
        }
    }

			if (invstatus.value == "Cancelled")
			{
	          if (confirm("Cancelation of This Invoice Will Result in Deletion of\nAll Invoice Matches Against This Invoice\n\n\nDo you Want to Proceed?"))
	          {

	          }
	          else
	          {
	            result = false;
	            return result;
	          }
	      }
        return result;
    }
    else
    {
        alert(finalMsgt);
        result = false;
        return result;
    }
}

function actionValue(name)
{
   var actvalue = name;
   window.document.purchaseorder.Action.value = actvalue;

	var result ;
   var finalMsgt;
   var allClear = 0;
   var sameShipto = 0;
   var shipTomsg;

	var testreceipt  =  document.getElementById("vocreceiptid11");
	//alert(testreceipt.value);

   finalMsgt = "Please enter valid values for: \n\n";

	var povalue  =  document.getElementById("po");
	if (povalue.value.length > 0)
	{
	    var validpo  =  document.getElementById("validpo");
	    if (validpo.value == "No")
	    {
	        finalMsgt = finalMsgt + " PO.\n" ;
	        allClear = 1;
	    }
	}

    if (allClear < 1)
    {
        result = true;
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

        updatecount++;
        return result;
    }
    else
    {
        alert(finalMsgt);
        result = false;
        return result;
    }
}

function removeAllLines()
{
  var chargeTable = document.getElementById("linetable");

  var allTRs = chargeTable.getElementsByTagName("tr");
  var nowofRows = (allTRs.length)*1;

  for (j = 0; j<nowofRows; j++)
  {
    for (i=0;i<chargeTable.childNodes.length;i++)
    {
      chargeTable.removeChild(chargeTable.childNodes[i]);
    }
     var divname1 = "divrow"+(j+1);
     mydetailtable = document.getElementById(divname1);
     mydetailtable.removeNode(true);
 }
}

function  originalversion()
{
    var itemnotestable  =  document.getElementById("mainheadertable");
    itemnotestable.className =  "moveup";

    var itemnotestable  =  document.getElementById("orderdetail");
    itemnotestable.className =  "scroll_column75";

}

function  printversion()
{
    var itemnotestable  =  document.getElementById("mainheadertable");
    itemnotestable.className =  "displaynone";

    var itemnotestable  =  document.getElementById("orderdetail");
    itemnotestable.className =  "moveup";

}

function buildheader()
{
		  // getting reference to my Line Table
        mytable = document.getElementById("linetable");

        // get the reference for the body
        var mybody=document.getElementById("mainpara");

        // creates an element whose tag name is TBODY
        mytablebody = document.createElement("TBODY");
        // creating all cells
        // creates an element whose tag name is TR
            mycurrent_row=document.createElement("TR");

                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TH");
                mycurrent_cell.setAttribute("width",'80');
                mycurrent_cell.innerHTML = "Item";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TH");
                mycurrent_cell.setAttribute("width",'275');
                mycurrent_cell.innerHTML = "Description";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TH");
                mycurrent_cell.setAttribute("width",'100');
                mycurrent_cell.innerHTML = "UOM";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

					 // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TH");
                mycurrent_cell.setAttribute("width",'40');
                mycurrent_cell.innerHTML = "Qty";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

                // creates an element whose tag name is TD
                /*mycurrent_cell=document.createElement("TH");
                mycurrent_cell.setAttribute("width",'40');
                name = "dateneeded" + (rownum);
                mycurrent_cell.innerHTML = "";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);*/

                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TH");
                mycurrent_cell.setAttribute("width",'100');
                mycurrent_cell.innerHTML = "Ext Price";

                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TH");
                mycurrent_cell.setAttribute("width",'100');
                mycurrent_cell.innerHTML = "Supplier Packaging";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TH");
                mycurrent_cell.setAttribute("width",'40');
                mycurrent_cell.innerHTML = "Supplier Qty";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

                /*// creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TH");
                mycurrent_cell.setAttribute("width",'40');
                mycurrent_cell.innerHTML = "";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);*/

                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TH");
                mycurrent_cell.setAttribute("width",'100');
                mycurrent_cell.innerHTML = "Supplier Ext Price";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

            // appends the row TR into TBODY
            mytablebody.appendChild(mycurrent_row);

        // appends TBODY into TABLE
        mytable.appendChild(mytablebody);

		  mybody.appendChild(mytable);
}


function addLineItem()
{
        // getting reference to my Line Table
        mytable = document.getElementById("linetable");

        var allTRs = mytable.getElementsByTagName("tr");
        var str = "# of table Rows: " + allTRs.length + "\n";
        var rownum = (allTRs.length)*1;
        //alert(str);

        var lineNumberc = 0;

		  if (allTRs.length == 0)
        {
            lineNumberc = rownum;
				buildheader();
				rownum = rownum + 1;
        }
        else
        {
            //var lineNumber = document.getElementById("row"+allTRs.length+"linenumber");
            //lineNumberc = ((lineNumber.value)*1 + 1);
        }

        var Color ="white";
        if (rownum%2==0)
        {
            Color ="blue";
        }
        else
        {
            Color ="white";
        }

        Nototallines = document.getElementById("totallines");
        Nototallines.value = (rownum-1);

        // get the reference for the body
        var mybody=document.getElementById("mainpara");

        // creates an element whose tag name is TBODY
        mytablebody = document.createElement("TBODY");
        // creating all cells
        // creates an element whose tag name is TR
            mycurrent_row=document.createElement("TR");
            mycurrent_row.id = "row"+rownum;
            mycurrent_row.className = Color;
           // mycurrent_row.attachEvent("onclick", catchevent);
           // mycurrent_row.attachEvent("mouseover", catchoverevent);

                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TD");
                mycurrent_cell.id = "cell1"+rownum;
                mycurrent_cell.attachEvent("onclick", catchevent);
                mycurrent_cell.attachEvent("mouseover", catchoverevent);
                mycurrent_cell.style.cursor = "hand";

                mycurrent_cell.setAttribute("width",'80');
                mycurrent_cell.innerHTML = lineNumberc+"/0";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TD");
                mycurrent_cell.id = "cell2"+rownum;
                mycurrent_cell.setAttribute("width",'275');
                mycurrent_cell.setAttribute("align",'left');
                name = "lineitemid" + (rownum);
                mycurrent_cell.innerHTML = "";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TD");
                mycurrent_cell.id = "cell3"+rownum;
                mycurrent_cell.setAttribute("width",'100');
                mycurrent_cell.innerHTML = "";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

					 // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TD");
                mycurrent_cell.id = "cell4"+rownum;
                mycurrent_cell.setAttribute("width",'40');
                mycurrent_cell.innerHTML = "";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

                // creates an element whose tag name is TD
                /*mycurrent_cell=document.createElement("TD");
                mycurrent_cell.id = "cell5"+rownum;
                mycurrent_cell.setAttribute("width",'40');
                name = "dateneeded" + (rownum);
                mycurrent_cell.innerHTML = "";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);*/

                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TD");
                mycurrent_cell.id = "cell6"+rownum;
                mycurrent_cell.setAttribute("width",'100');
                name = "datepromised" + (rownum);
                mycurrent_cell.innerHTML = "";

                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TD");
                mycurrent_cell.id = "cell7"+rownum;
                mycurrent_cell.setAttribute("width",'100');
                name = "lineqty" + (rownum);
                mycurrent_cell.innerHTML = "";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TD");
                mycurrent_cell.id = "cell8"+rownum;
                mycurrent_cell.setAttribute("width",'40');
                //mycurrent_cell.setAttribute("align",'right');
                name = "lineunitprice" + (rownum);
                mycurrent_cell.innerHTML = "";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

                /*// creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TD");
                mycurrent_cell.id = "cell9"+rownum;
                mycurrent_cell.setAttribute("width",'40');
                mycurrent_cell.setAttribute("align",'right');
                //mycurrent_cell.setAttribute("align",'right');
                mycurrent_cell.innerHTML = "";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);*/

                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TD");
                mycurrent_cell.id = "cell10"+rownum;
                mycurrent_cell.setAttribute("width",'100');
                mycurrent_cell.innerHTML = "";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

            // appends the row TR into TBODY
            mytablebody.appendChild(mycurrent_row);

        // appends TBODY into TABLE
        mytable.appendChild(mytablebody);

        // creates an element whose tag name is DIV
        //newdiv = document.createElement("DIV");
        var divname =  "divrow"+rownum;
        //newdiv.id = divname;
        //newdiv.className =  "displaynone";

        //newdiv.innerHTML = "<B><U>PO Line Detail: </U></B>";

        newdivtable = document.createElement("TABLE");
        newdivtable.setAttribute("width",'100%');
        newdivtable.setAttribute("CELLSPACING",'1');
        newdivtable.setAttribute("CELLPADDING",'3');
        newdivtable.id = divname;
        newdivtable.className =  "displaynone";

        newdivtablebody = document.createElement("TBODY");
        // creating all po line details

		  newdivcurrent_row=document.createElement("TR");
            newdivcurrent_row.id = "supplierdetail"+divname+rownum;
            newdivcurrent_row.className = "whitenocur";
                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "supplierdetailline"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'10%');
                newdivcurrent_cell.setAttribute("align",'left');
                newdivcurrent_cell.innerHTML = "<B>Item Detail:</B>&nbsp;&nbsp;&nbsp;<input type=\"text\" size=\"12\" MAXLENGTH=\"30\" CLASS=\"INVISIBLEHEADWHITE\" name='itemidindetail"+rownum+"' id='itemidindetail"+rownum+"' readonly> ";
            newdivcurrent_row.appendChild(newdivcurrent_cell);
        newdivtablebody.appendChild(newdivcurrent_row);

            newdivcurrent_row=document.createElement("TR");
            newdivcurrent_row.id = "detailrow1"+divname+rownum;
            newdivcurrent_row.className = "bluenocur";

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "row1detail"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'90%');
                newdivcurrent_cell.setAttribute("align",'left');
                //newdivcurrent_cell.setAttribute("colSpan","4");

                //var innHtmlline =  rownum+"&nbsp;&nbsp;&nbsp;&nbsp;<B>MPN:</B><input type=\"text\" size=\"12\" MAXLENGTH=\"30\" CLASS=\"HEADER\" name='mpn"+rownum+"' id='mpn"+rownum+"'> ";
                var innHtmlline =  "<B>Invoice - PO Item Matching</B>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name='addIdnvoiceLine' OnClick=addinvoiceLine('"+rownum+"') value=\"Add Line\"> ";
                innHtmlline += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name='addCreditCorrection' OnClick=addcreditcorrectionline('"+rownum+"') value=\"Add Correction\"> ";
                innHtmlline += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name='addmaterialrma"+rownum+"' OnClick=addrmaline('"+rownum+"') value=\"Add RMA (Credit)\"> ";
                innHtmlline += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name='addadchrgreturn"+rownum+"' OnClick=addaddchgrtn('"+rownum+"') value=\"Additional Charge (RMA Credit)\"> ";
                newdivcurrent_cell.innerHTML = innHtmlline;
            newdivcurrent_row.appendChild(newdivcurrent_cell);
        newdivtablebody.appendChild(newdivcurrent_row);

newdivcurrent_row=document.createElement("TR");
            newdivcurrent_row.id = "detailrow123"+divname+rownum;
            newdivcurrent_row.className = "whitenocur";
                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "row1detail23"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'90%');
                newdivcurrent_cell.setAttribute("align",'left');

                var innHtmlline2 =  "<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name='showallInvLines"+rownum+"' OnClick=showallinvlines('"+rownum+"') value=\"Show All\">";
					 innHtmlline2 +=  "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name='showunMatchedInv"+rownum+"' OnClick=showunmatchedinv('"+rownum+"') value=\"Show Only Open Invoices\"> ";
                newdivcurrent_cell.innerHTML = innHtmlline2;
            newdivcurrent_row.appendChild(newdivcurrent_cell);
        newdivtablebody.appendChild(newdivcurrent_row);

	newdivcurrent_row=document.createElement("TR");
            newdivcurrent_row.id = "supplierdetail"+divname+rownum;
            newdivcurrent_row.className = "whitenocur";
                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "itemmatching"+rownum;
                newdivcurrent_cell.setAttribute("width",'10%');
                newdivcurrent_cell.setAttribute("align",'left');
                newdivcurrent_cell.innerHTML = "Table";
            newdivcurrent_row.appendChild(newdivcurrent_cell);
        newdivtablebody.appendChild(newdivcurrent_row);


newdivcurrent_row=document.createElement("TR");
            newdivcurrent_row.id = "supplierdetail"+divname+rownum;
            newdivcurrent_row.className = "whitenocur";
                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "itemmatching"+rownum;
                newdivcurrent_cell.setAttribute("width",'10%');
                newdivcurrent_cell.setAttribute("align",'left');
                newdivcurrent_cell.innerHTML = "&nbsp;";
            newdivcurrent_row.appendChild(newdivcurrent_cell);
        newdivtablebody.appendChild(newdivcurrent_row);

        newdivcurrent_row=document.createElement("TR");
            newdivcurrent_row.id = "detailrow2"+divname+rownum;
            newdivcurrent_row.className = "bluenocur";

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "titleline2"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'10%');
                newdivcurrent_cell.setAttribute("align",'left');
					 var innHtmlline1 =  "<B>Invoice - Receipt Matching</B>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name='shwallreceiptMatching"+rownum+"' OnClick=showallreceiptlines('"+rownum+"') value=\"Show All\">";
					 innHtmlline1 +=  "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name='showunMatchedReceipts"+rownum+"' OnClick=showunmatchedrecipts('"+rownum+"') value=\"Show Only Receipts to Match\"> ";

                //newdivcurrent_cell.innerHTML = "<B>Invoice - Receipt Matching</B>";
                newdivcurrent_cell.innerHTML = innHtmlline1;
                newdivcurrent_row.appendChild(newdivcurrent_cell);

        newdivtablebody.appendChild(newdivcurrent_row);

        /*newdivcurrent_row=document.createElement("TR");
            newdivcurrent_row.id = "detailrow3"+divname+rownum;
            newdivcurrent_row.className = "whitenocur";

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "supplierconv"+rownum;
                newdivcurrent_cell.setAttribute("width",'10%');
                newdivcurrent_cell.setAttribute("align",'left');
                newdivcurrent_cell.innerHTML = "<B>Supplier Conversion:</B>";
                newdivcurrent_row.appendChild(newdivcurrent_cell);
        newdivtablebody.appendChild(newdivcurrent_row);*/

            newdivcurrent_row=document.createElement("TR");
            newdivcurrent_row.id = "detailrow4"+divname+rownum;
            newdivcurrent_row.className = "whitenocur";

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "receiptmatching"+rownum;
                newdivcurrent_cell.setAttribute("width",'10%');
                newdivcurrent_cell.setAttribute("align",'left');
                newdivcurrent_cell.setAttribute("vAlign",'top');
                newdivcurrent_cell.innerHTML = "Table";
                newdivcurrent_row.appendChild(newdivcurrent_cell);

        newdivtablebody.appendChild(newdivcurrent_row);


        newdivcurrent_row=document.createElement("TR");
            newdivcurrent_row.id = "detailrow10"+divname+rownum;
            newdivcurrent_row.className = "whitenocur";

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "row10detail"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'90%');
                newdivcurrent_cell.setAttribute("align",'left');

                var invisibleElements = "<INPUT TYPE=\"hidden\" NAME=\"linestatus"+rownum+"\" VALUE=\"Draft\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"row"+rownum+"linenumber\" VALUE=\""+lineNumberc+"\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"row"+rownum+"row\" VALUE=\""+rownum+"\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"itemnotestatus"+rownum+"\" VALUE=\"No\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"linechange"+rownum+"\" VALUE=\"Yes\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"originallinestatus"+rownum+"\" VALUE=\"Draft\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"ammendment"+rownum+"\" VALUE=\"0\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"ammendmentcomments"+rownum+"\" VALUE=\"\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"itemtype"+rownum+"\" VALUE=\"\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"associatedprsstatus"+rownum+"\" VALUE=\"No\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"nofassociatedprs"+rownum+"\" VALUE=\"0\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"specstatus"+rownum+"\" VALUE=\"No\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"msdsstatus"+rownum+"\" VALUE=\"No\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"validspec"+rownum+"\" VALUE=\"No\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"flowdownstatus"+rownum+"\" VALUE=\"No\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"validflowdown"+rownum+"\" VALUE=\"No\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"tcmbuystatus"+rownum+"\" VALUE=\"No\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"clientbuystatus"+rownum+"\" VALUE=\"No\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"validitem"+rownum+"\" VALUE=\"No\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"qtyreceived"+rownum+"\" VALUE=\"0\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"linetotalprice"+rownum+"\" VALUE=\"0\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"polineunitprice"+rownum+"\" VALUE=\"0\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"prshipto"+rownum+"\" VALUE=\"\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"noofmatchingitem"+rownum+"\" VALUE=\"0\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"noofreceiptmatching"+rownum+"\" VALUE=\"0\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"nooflinesinaddcharge"+rownum+"\" VALUE=\"0\">";

                //invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"itemnotestatus"+divname+""+rownum+"\" VALUE=\"No\">";
                newdivcurrent_cell.innerHTML = invisibleElements;
            newdivcurrent_row.appendChild(newdivcurrent_cell);
        newdivtablebody.appendChild(newdivcurrent_row);


        newdivtable.appendChild(newdivtablebody);

        mybody.appendChild(newdivtable);
}


function addOptionItem(obj,value,text) {
     obj.options[obj.length]=new Option(text,value);
}

function removeAllOptionItem(obj) {
     if(obj.length <= 0)
          return;
     var len = obj.length;
     for(i=0;i<len;i++)
          obj.options[0]=null;
}


function catchoverevent()
{
eventSrcID=(event.srcElement)?event.srcElement.id:'undefined';
eventtype=event.type;
status=eventSrcID+' has received a '+eventtype+' event.';
parentrow = event.srcElement.parentNode;
}

function catchevent()
{
    eventSrcID=(event.srcElement)?event.srcElement.id:'undefined';
    eventtype=event.type;
    //status=eventSrcID+' has received a '+eventtype+' event.';

    parentrow = event.srcElement.parentNode;

    var str = "Parent Row is " + parentrow.id + "\n";
    //alert(str);

    var url = /\d/;
    var result = selected_rowid.match(url);
    var numberline = result[0];

    selectedItemRow = document.getElementById(""+selected_rowid+"row");
    parentItemRow = document.getElementById(""+parentrow.id+"row");

    selectedRowStatus = document.getElementById("linestatus"+selectedItemRow.value+"");
    parentRowStatus = document.getElementById("linestatus"+parentItemRow.value+"");

    //alert(selectedRowStatus.value);
    //alert(parentRowStatus.value);

    var Color ="white";
    if ( ( (selectedItemRow.value)*1 )%2==0)
    {
        Color ="#E6E8FA";
    }
    else
    {
        Color ="#FFFFFF";
    }

    if (selectedRowStatus.value != "Remove")
    {
    selectedRow = document.getElementById(selected_rowid);
    selectedRow.style.backgroundColor = Color;
    }

    if (parentRowStatus.value != "Remove")
    {
    parentrow.style.backgroundColor = "#8a8aff";
    }

    //parentrow.style.display = "none"

    var divrowid = "div"+selected_rowid;
    var target1 = document.getElementById(divrowid);
    target1.style.display = "none";

    if (parentRowStatus.value != "Remove")
    {
    var divrowid = "div"+parentrow.id;
    var target1 = document.getElementById(divrowid);
    target1.style.display = "block";
    }

    selected_rowid = parentrow.id;

    selectedrowidinform = document.getElementById("selectedrowid");
	 selectedrowidinform.value = parentrow.id;

}

function removeline(entered)
{
    mytable = document.getElementById("linetable");
    var allTRs = mytable.getElementsByTagName("tr");
    var nowofRows = (allTRs.length)*1;

    if (nowofRows > 0)
    {

    //alert(selected_rowid);
    selectedItemRow = document.getElementById(""+selected_rowid+"row");
    //alert(selectedItemRow.value);
    selectedRowStatus = document.getElementById("linestatus"+selectedItemRow.value+"");

    selectedOriginalRowStatus = document.getElementById("originallinestatus"+selectedItemRow.value+"");

    var removedraftline = 0;
    if (selectedRowStatus.value == "Confirmed")
    {
	alert("You Cannot Delete a Confirmeded Line");
    }
    /*else if (selectedRowStatus.value == "Draft")
    {
      var chargeTable = document.getElementById("linetable");
      var allTRs = chargeTable.getElementsByTagName("tr");
      var nowofRows = (allTRs.length)*1;

      for (j = 0; j<nowofRows; j++)
      {
        var selectedRowtoTest = "row" + (j+1);
        //alert(""+selectedRowtoTest+"   "+selected_rowid+"");
        if (selectedRowtoTest == selected_rowid)
        {
            selectedRowStatus.value = "Remove";
            removedraftline = j;
            selectedRow = document.getElementById(selected_rowid);
            selectedRow.style.backgroundColor = "#3b3b3b";
            selectedRow.style.display = "none";
            selectedRow.className = "displaynone";

            selectedRow = document.getElementById("divrow"+selectedItemRow.value+"");
            selectedRow.style.display = "none";
            selectedRow.className = "displaynone";

            selecteditemRowStatus = document.getElementById("cell12"+selectedItemRow.value+"");
            selecteditemRowStatus.innerHTML = "Remove";

        }

        if ( (removedraftline > 0) && (j>removedraftline))
        {
            selectedRowStatus = document.getElementById("linestatus"+(j+1)+"");

            var Color ="white";
            if ( j%2==0 )
            {
                Color ="#E6E8FA";
            }
            else
            {
                Color ="#FFFFFF";
            }

            if (selectedRowStatus.value != "Remove")
            {
            selectedRow = document.getElementById("row"+(j+1)+"");
            selectedRow.style.backgroundColor = Color;
            }
        }
     }
    }*/
    else if (selectedRowStatus.value != "Remove")
    {
    selectedRowStatus.value = "Remove";

    selectedRow = document.getElementById(selected_rowid);
    selectedRow.style.backgroundColor = "#3b3b3b";

    selectedRow = document.getElementById("divrow"+selectedItemRow.value+"");
    selectedRow.style.display = "none";
    selectedRow.className = "displaynone";

    selecteditemRowStatus = document.getElementById("cell12"+selectedItemRow.value+"");
    selecteditemRowStatus.innerHTML = "Remove";
    }
  }
}

function unremoveline(entered)
{
    mytable = document.getElementById("linetable");
    var allTRs = mytable.getElementsByTagName("tr");
    var nowofRows = (allTRs.length)*1;

    if (nowofRows > 0)
    {
    //alert(selected_rowid);
    selectedItemRow = document.getElementById(""+selected_rowid+"row");
    //alert(selectedItemRow.value);
    selectedRowStatus = document.getElementById("linestatus"+selectedItemRow.value+"");

    var removedraftline = 0;
    if (selectedRowStatus.value == "Remove")
    {
    selectedRowStatus.value = "Unconfirmed";

    /*var Color ="white";
    if ( ((selectedItemRow.value)*1)%2==0 )
    {
        Color ="#E6E8FA";
    }
    else
    {
        Color ="#FFFFFF";
    }*/

    selectedRow = document.getElementById(selected_rowid);
    selectedRow.style.backgroundColor = "#8a8aff";

    selectedRow = document.getElementById("divrow"+selectedItemRow.value+"");
    selectedRow.style.display = "block";

    selecteditemRowStatus = document.getElementById("cell12"+selectedItemRow.value+"");
    selecteditemRowStatus.innerHTML = "Unconfirmed";
    }
  }
}

function vRev()
{
	var reason = '';
	do
	{
		reason = prompt('Please enter a reason for reversal','');
	}
	while(reason == '')
	if(reason == null || reason == undefined)
		return false;
	document.getElementById("reverseNote").value = reason; 
	document.getElementById("Action").value = 'reversevoucher'; 
}