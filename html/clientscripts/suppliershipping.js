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

function showshippingpagelegend()
{
  openWinGeneric("/tcmIS/help/shippingpagelegend.html","shippingpagelegend","290","300","no","50","50")
}

function showShippingaddress (shiptolocid,shiptocompid)
{
    shiptolocid = shiptolocid.replace(/#/gi, "%23");
    shiptolocid = shiptolocid.replace(/&/gi, "%26");

    var testurl3 = "/tcmIS/supplier/suppliershippingaddress?showissuedrcts=Y&";
    testurl3 = testurl3 + "shiptolocid=" + shiptolocid ;
    testurl3 = testurl3 + "&shiptocompid=" + shiptocompid ;

    openWinGeneric(testurl3,"Generate_Bols","400","200","yes")
}

function doPrintrelabel()
{
    var testurl3 = "/tcmIS/supplier/suppliershipping?session=Active&SubUserAction=genpartlabels";
    openWinGeneric(testurl3,"Generate_relabels","640","600","yes")
}

function reprintlabels(entered)
{
    window.document.suppliershipping.UserAction.value = "reprintlabels";
    window.document.suppliershipping.SubUserAction.value = "reprintlabels";

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

function doPrintbox(HubNoforlabel)
{
    var testurl3 = "/tcmIS/hub/reprintboxlbls?";
    testurl3 = testurl3 + "HubNoforlabel=" + HubNoforlabel ;
    openWinGeneric(testurl3,"Generate_Bols","640","600","yes")
}

function boxlabels(entered)
{
    window.document.suppliershipping.UserAction.value = "printpicks";
    window.document.suppliershipping.SubUserAction.value = "PrintBOXLabels";

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

function containerLabels()
{
 var totalRows  =  document.getElementById("totalRows");
 var receiptsToPrint = "";
 var receiptCount = 0;
 for ( var p = 0 ; p < totalRows.value ; p ++)
 {
  var receiptId  =  document.getElementById("receiptId"+(p+1)+"");
  //var row_chk  =  document.getElementById("row_chk"+(p+1)+"");

  if (receiptId.value.length > 0)
  {
   if (receiptCount > 0)
   {
    receiptsToPrint = receiptsToPrint + ",";
   }
	receiptsToPrint = receiptsToPrint + receiptId.value;
	receiptCount++;
  }
 }
 //alert(receiptsToPrint);
 if (receiptsToPrint.length ==0 )
 {
	alert("You can print Container Labels only for confirmed POs and need to check the 'OK' Check Box.");
 }
 else
 {
    var testurl3 = "/tcmIS/Hub/ShowReceivedReceipts?session=Active&generate_labels=yes&showissuedrcts=Yes&UserAction=GenerateLabels";
    testurl3 = testurl3 + "&genLabels=1";
    testurl3 = testurl3 + "&receivedReceipts="+receiptsToPrint;
    openWinGeneric(testurl3,"Generate_labels","640","600","yes");
 }
}


function showrecrecipts()
{
    var testurl3 = "/tcmIS/supplier/ShowReceivedReceipts?session=Active&generate_labels=yes&showissuedrcts=Yes&UserAction=GenerateLabels";
    /*var paperSize  =  "1";
    testurl3 = testurl3 + "&genLabels=" + paperSize ;*/

   /*var testbin;
   eval( testbin =  "window.document.receiving.HubName");
   var cur = null;
   eval( cur = (eval(testbin.toString())).selectedIndex );
   var curval = null;
   ( curval =  (eval(testbin.toString())).options[cur].value );
	testurl3 = testurl3 + "&HubNoforlabel=" + curval ;*/

    openWinGeneric(testurl3,"Generate_labels","640","600","yes")
}

function openWinGeneric(destination,WinName,WinWidth, WinHeight, Resizable )
{
    windowprops = "toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,status=yes,top=200,left=200,width=" + WinWidth + ",height=" + WinHeight+",resizable=" + Resizable;
    preview = window.open(destination, WinName,windowprops);
}


function suppchanged(object)
{
	var artist;
   artist = object.options[object.selectedIndex].value;

   var selectedIndex = object.selectedIndex;

	for (var i = document.suppliershipping.shipto.length; i > 0;i--)
   {
      document.suppliershipping.shipto.options[i] = null;
   }
	showinvlinks(artist);
	window.document.suppliershipping.shipto.selectedIndex = 0;
}

function showinvlinks(selectedIndex)
{
    var invgrpid = new Array();
    invgrpid = altinvid[selectedIndex];

    if (invgrpid == null)
	 {
      setCab(0,"All","")
	 }
	 else
    {
     for (var i=0; i < invgrpid.length; i++)
     {
        setCab(i,invgrpid[i],invgrpid[i])
     }
    }
}

function setCab(href,text,id)
{
    var optionName = new Option(text, id, false, false)
    document.suppliershipping.shipto.options[href] = optionName;
}

function addDays(myDate,days) {
    return new Date(myDate.getTime() + days*24*60*60*1000);
}

function y2k(number) { return (number < 1000) ? number + 1900 : number; }

function daysElapsed(date1,date2) {
    var difference = Date.UTC(y2k(date1.getYear()),date1.getMonth(),date1.getDate(),0,0,0) - Date.UTC(y2k(date2.getYear()),date2.getMonth(),date2.getDate(),0,0,0);
    return difference/1000/60/60/24;
}

function calculateexpdate(linenumber)
{
var testbasisdate = document.getElementById("basis_date"+linenumber+"");
if ( testbasisdate.value.length == 10 )
{
  if ( checkdate(testbasisdate) == true )
  {
    var basisdatestg =testbasisdate.value;
    var basisdate = new Date(basisdatestg.substring(6,10),basisdatestg.substring(0,2)-1,basisdatestg.substring(3,5));

    var shelflifedays = document.getElementById("shelf_life_days"+linenumber+"").value;
    var expdate = new Date(basisdate.getTime() + shelflifedays*24*60*60*1000);

    var d  = expdate.getDate();
    var day = (d < 10) ? '0' + d : d;
    var m = expdate.getMonth() + 1;
    var month = (m < 10) ? '0' + m : m;
    var yy = expdate.getYear();
    var year = (yy < 1000) ? yy + 1900 : yy;

    var expiry_datecell = document.getElementById("expiry_datecell"+linenumber+"");
    expiry_datecell.innerHTML = month  + "/" + day + "/" + year;

    var testexpirydate = document.getElementById("expiry_date"+linenumber+"");
    testexpirydate.value = month  + "/" + day + "/" + year;
  }
}
}

function checkReadOnlyValues (linenumber)
{
 var yes = "yes";
 var no = "no";

 var testcheckbox  =  document.getElementById("row_chk"+linenumber+"");
 if ( testcheckbox.value  == no.toString() )
 {
  testcheckbox.value  = yes.toString();
 }
 else if ( testcheckbox.value  == yes.toString())
 {
   testcheckbox.value  = no.toString();
 }
}

function checkValues(linenumber)
{
    var result ;
    var yes = "yes";
    var no = "no";
    var NONE = "NONE";
    var now = new Date();
    var today = new Date(now.getYear(),now.getMonth(),now.getDate());

            var testdate;
            var testqty;
            var testlot;
            var finalMsgt;
            var allClear = 0;

				var testcheckbox  =  document.getElementById("row_chk"+linenumber+"");
            if ( testcheckbox.value  == no.toString() )
            {
            }

            if ( testcheckbox.value  == yes.toString())
            {
                testcheckbox.value  = no.toString();
                testcheckbox.checked = false;
                updatecount--;
                return;
            }

            finalMsgt = "Please enter valid values for: \n\n";

				var testactualshipdate = document.getElementById("act_suppship_date"+linenumber+"");
            if ( testactualshipdate.value.length == 10 )
            {
                if ( checkdate(testactualshipdate) == false )
                {
                    finalMsgt = finalMsgt +   " Ship Date. \n" ;
                    testactualshipdate.value = "";
                    allClear = 1;
                }
                var shipdatestg =testactualshipdate.value;
                var shipdate = new Date(shipdatestg.substring(6,10),shipdatestg.substring(0,2)-1,shipdatestg.substring(3,5));

                /*var diffdays = (shipdate.getTime() - today.getTime() )/1000/60/60/24;
                if (diffdays > 30)
                {
                  finalMsgt = finalMsgt +   " Cannot Ship More than 30 Days in Advance. \n" ;
                  testactualshipdate.value = "";
                  allClear = 1;
                }*/
            }
            else
            {
                finalMsgt = finalMsgt + " Ship Date.\n" ;
                testactualshipdate.value = "";
                allClear = 1;
            }

            var testdatereceipt = document.getElementById("date_recieved"+linenumber+"");
            if ( testdatereceipt.value.length == 10 )
            {
                if ( checkdate(testdatereceipt) == false )
                {
                    finalMsgt = finalMsgt +   " Date of Receipt. \n" ;
                    testdatereceipt.value = "";
                    allClear = 1;
                }
                var shipdatestg =testdatereceipt.value;
                var shipdate = new Date(shipdatestg.substring(6,10),shipdatestg.substring(0,2)-1,shipdatestg.substring(3,5));

                /*var diffdays = (shipdate.getTime() - today.getTime() )/1000/60/60/24;
                if (diffdays > 30)
                {
                  finalMsgt = finalMsgt +   " Cannot Ship More than 30 Days in Advance. \n" ;
                  testdatereceipt.value = "";
                  allClear = 1;
                }*/
            }
            else
            {
                finalMsgt = finalMsgt + " Date of Receipt.\n" ;
                testdatereceipt.value = "";
                allClear = 1;
            }

            /*var testbasisdate = document.getElementById("basis_date"+linenumber+"");
            if ( testbasisdate.value.length == 10 )
            {
             if ( checkdate(testbasisdate) == false )
             {
              finalMsgt = finalMsgt +   " Basis Date. \n" ;
              testbasisdate.value = "";
              allClear = 1;
             }
             else
             {
              calculateexpdate(linenumber);
             }
            }

				var testexpirydate = document.getElementById("expiry_date"+linenumber+"");
            if (!( testexpirydate.value == "Indefinite" || testexpirydate.value == "indefinite" || testexpirydate.value == "INDEFINITE" ))
            {
                if ( testexpirydate.value.length == 10 )
                {
                    if ( checkdate(testexpirydate) == false )
                    {
                        finalMsgt = finalMsgt +   " Exp Date. \n" ;
                        testexpirydate.value = "";
                        allClear = 1;
                    }

                    var expdatestg =testexpirydate.value;
                    var expdate = new Date(expdatestg.substring(6,10),expdatestg.substring(0,2)-1,expdatestg.substring(3,5));

                    var diffdays = (expdate.getTime() - today.getTime() )/1000/60/60/24;
                    if (diffdays < -2)
                    {
                      finalMsgt = finalMsgt +   " Cannot Ship Expired Material. \n" ;
                      testexpirydate.value = "";
                      allClear = 1;
                    }
                }
                else
                {
                    finalMsgt = finalMsgt + " Exp Date. \n" ;
                    testexpirydate.value = "";
                    allClear = 1;
                }
            }*/

            /*var mfg_lot = document.getElementById("mfg_lot"+linenumber+"");
            if ( mfg_lot.value.length < 1 )
            {
                finalMsgt = finalMsgt + " Mfg Lot #. \n" ;
                allClear = 1;
            }*/

            var qtyoveride = document.getElementById("qtyoveride"+linenumber+"");
            if ( qtyoveride.value == "N" )
            {
          		var qty_recd = document.getElementById("qty_recd"+linenumber+"");
          		var qty_open = document.getElementById("qty_open"+linenumber+"");

	            var v = qty_recd.value;
	            if ( !(isInteger(v)) )
	            {
	                finalMsgt = finalMsgt + " Quantity Shipped. \n";
	                allClear = 1;
	                qty_recd.value = "";
	            }
	            else if (v*1 > (qty_open.value*1))
	            {
						 finalMsgt = finalMsgt + " Quantity Shipped cannot be greater than Quantity Open. \n";
	                allClear = 1;
	                qty_recd.value = "";
	            }
            }

            if (allClear < 1)
            {
                testcheckbox.value  = yes.toString();
                testcheckbox.checked = true;
                result = true;
            }
            else
            {
                testcheckbox.value  = no.toString();
                testcheckbox.checked = false;
                alert(finalMsgt);
                result = false;
            }
            return;

    if (true == result )
    {
        updatecount++;
    }
    return result;
}

function update(entered)
{
    window.document.suppliershipping.UserAction.value = "UPDATE";
    window.document.suppliershipping.SubUserAction.value = "UpdPage";
    window.document.suppliershipping.DuplLineNumber.value = "NA"  ;

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

function search(entered)
{
    window.document.suppliershipping.UserAction.value = "NEW";
    window.document.suppliershipping.SubUserAction.value = "NA";
    window.document.suppliershipping.DuplLineNumber.value = "NA"  ;

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

function duplLine(linenumber)
{

	window.document.suppliershipping.UserAction.value = "UPDATE";
   window.document.suppliershipping.SubUserAction.value = "DuplLine" ;
   window.document.suppliershipping.DuplLineNumber.value = linenumber ;

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

function cancel()
{
    window.close();
}