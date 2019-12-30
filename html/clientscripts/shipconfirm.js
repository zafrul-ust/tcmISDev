var submitcount=0;
var shipupdcount=0;
function SubmitOnlyOnce()
{
  document.shipconfirm.target='';
  document.shipconfirm.action='/tcmIS/Hub/ShipConfirm';

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


String.prototype.trim = function()
{
// skip leading and trailing whitespace
// and return everything in between
  return this.replace(/^\s*(\b.*\b|)\s*$/, "$1");
}


function hubchanged(object)
{
	var artist;
   artist = object.options[object.selectedIndex].value;

   var selectedIndex = object.selectedIndex;

	for (var i = document.shipconfirm.invengrp.length; i > 0;i--)
   {
      document.shipconfirm.invengrp.options[i] = null;
   }
	showinvlinks(artist);
	window.document.shipconfirm.invengrp.selectedIndex = 0;
}

function showinvlinks(selectedIndex)
{
    var invgrpid = new Array();
    invgrpid = altinvid[selectedIndex];

	 var invgrpName = new Array();
    invgrpName = altinvName[selectedIndex];

    for (var i=0; i < invgrpid.length; i++)
    {
        setCab(i,invgrpName[i],invgrpid[i])
    }
}

function setCab(href,text,id)
{
    var optionName = new Option(text, id, false, false)
    document.shipconfirm.invengrp.options[href] = optionName;
}

function showshipconfirmlegend()
{
  openWinGeneric("/tcmIS/help/shipconfirmlegend.html","shipconfirmlegend","290","300","no","50","50")
}

function returnToMain()
{
  opener.search();
  opener.document.shipconfirm.submit();
  window.close();
}

function checkall(checkboxname,sartingvalue)
{
var checkall = document.getElementById("chkall");
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
	var linecheck = document.getElementById(""+checkboxname+""+(p+1)+"");
	linecheck.checked =result;
	linecheck.value = valueq;

  var printOk = document.getElementById("printOk"+(p+1)+"");	
	printOk.value = valueq;
  }
	catch (ex1)
	{

	}
}

}

function openWinGeneric(destination,WinName,WinWidth, WinHeight, Resizable )
{
    windowprops = "toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,status=no,top=200,left=200,width=" + WinWidth + ",height=" + WinHeight+",resizable=" + Resizable;
    preview = window.open(destination, WinName,windowprops);
}

function openWinGenericXls(destination,WinName,WinWidth, WinHeight, Resizable )
{
    windowprops = "toolbar=no,location=no,directories=no,menubar=yes,scrollbars=yes,status=no,top=200,left=200,width=" + WinWidth + ",height=" + WinHeight+",resizable=" + Resizable;
    preview = window.open(destination, WinName,windowprops);
}

function printDelDocuments()
{
    openWinGeneric('/tcmIS/common/loadingfile.jsp','_newprintDelDocuments','650','600','yes');
    document.shipconfirm.target='_newprintDelDocuments';
    document.shipconfirm.action='/tcmIS/hub/printlabel.do?labelType=DELIVERYDOCS&printerLocation=PHILAS1';
    var a = window.setTimeout("document.shipconfirm.submit();",500);
}

function printConsolidatedBol()
{
  var totallines = document.getElementById("totallines").value;
  totallines = totallines*1;
  var consolidatedShipmentIdList= "";
  var shipmentIdCount = 0;

  for ( var p = 0 ; p < totallines ; p ++)
  {
	try
	{
	var linecheck = document.getElementById("row_chk"+(p+1)+"");
	var shipmentId = document.getElementById("shipmentId"+(p+1)+"");
	if (linecheck.checked)
	{
	 if (shipmentIdCount > 0)
	 {
     consolidatedShipmentIdList = consolidatedShipmentIdList + "," + shipmentId.value;
	 }
	 else
	 {
     consolidatedShipmentIdList = consolidatedShipmentIdList + shipmentId.value;
	 }
	 shipmentIdCount ++;
	}
	}
	catch (ex1)
	{

	}
   }

   if (shipmentIdCount == 1)
   {
      var testurl3 = "/tcmIS/Hub/ShipConfirm?session=Active&generate_labels=yes&UserAction=generateConsolidatedBol&shipmentIds=";
      testurl3 = testurl3 + consolidatedShipmentIdList;
      openWinGeneric(testurl3,"Generate_consolidatedBol","640","600","yes")
   }else if (shipmentIdCount > 1) {
       alert("Only one shipment id can be print at a time.");
   }else
   {
      alert("Please select a shipment id that you want to print.");
   }
}

function doPrintConsolidatedBol (shipmentList)
{
  var testurl3 = "/tcmIS/Hub/ShipConfirm?session=Active&generate_labels=yes&UserAction=generateConsolidatedBol&shipmentIds=";
  testurl3 = testurl3 + shipmentList ;
  openWinGeneric(testurl3,"Generate_consolidatedBol","640","600","yes")
}

function doPrintPackingList (shipmentList)
{
  var testurl3 = "/tcmIS/hub/printpackinglist.do?shipmentIds=";
  testurl3 = testurl3 + shipmentList ;
  openWinGeneric(testurl3,"Generate_packing_list","640","600","yes")
}

function doPrintbol()
{
    var testurl3 = "/tcmIS/Hub/ShipConfirm?session=Active&generate_labels=yes&UserAction=GenerateLabels";
    var paperSize  =  window.document.shipconfirm.boldetails.value;
    testurl3 = testurl3 + "&boldetails=" + paperSize ;
    openWinGeneric(testurl3,"Generate_Bols","640","600","yes")
}

function doPrintbox()
{
	 var testurl3 = "/tcmIS/hub/reprintboxlbls?";

    HubNoforlabel = document.getElementById("HubName");
	 testurl3 = testurl3 + "HubNoforlabel=" + HubNoforlabel ;

    openWinGeneric(testurl3,"Generate_Bols","640","600","yes")
}


function update(entered)
{
    window.document.shipconfirm.UserAction.value = "Update";
    document.shipconfirm.target='';
    document.shipconfirm.action='/tcmIS/Hub/ShipConfirm';
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
    window.document.shipconfirm.UserAction.value = "Search";
    document.shipconfirm.target='';
    document.shipconfirm.action='/tcmIS/Hub/ShipConfirm';

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

function generatexls(entered)
{
    var testurl3 = "/tcmIS/Hub/ShipConfirm?session=Active&UserAction=generatexls";
    openWinGenericXls(testurl3,"Generate_Excel_File","640","600","yes")
}

function boxlabels(entered)
{
    window.document.shipconfirm.UserAction.value = "PrintBOXLabels";
    document.shipconfirm.target='';
    document.shipconfirm.action='/tcmIS/Hub/ShipConfirm';

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

function bolshort(entered)
{
    window.document.shipconfirm.UserAction.value = "PrintBOL";
    document.shipconfirm.target='';
    document.shipconfirm.action='/tcmIS/Hub/ShipConfirm';

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


function generatConsolidatedBol(entered)
{
    window.document.shipconfirm.UserAction.value = "consolidatedBol";
    document.shipconfirm.target='';
    document.shipconfirm.action='/tcmIS/Hub/ShipConfirm';

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

function generatConsolidatedShipmentIdBol(entered)
{
    window.document.shipconfirm.UserAction.value = "consolidatedShipmentIdBol";
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


function bollong(entered)
{
    window.document.shipconfirm.UserAction.value = "PrintBOLDetail";
    document.shipconfirm.target='';
    document.shipconfirm.action='/tcmIS/Hub/ShipConfirm';
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

function checkBoxOnChangeValue(name)
{
    var result ;
    var allClear = 0;
    var yes = "yes";
    var no = "no";

    var shipchekbox;
    eval( shipchekbox  =  "window.document.shipconfirm." + name.toString() );

    if ( ((eval(shipchekbox.toString())).value )  == no.toString())
    {
            eval((eval(shipchekbox.toString())).value  = yes.toString() );;
            eval( (eval(shipchekbox.toString())).checked = true );;
            result = true;
    }
    /*else if ( ((eval(shipchekbox.toString())).value )  == yes.toString())
    {
        eval((eval(shipchekbox.toString())).value  = no.toString() );
        eval( (eval(shipchekbox.toString())).checked = false );
        shipupdcount--;
        result = true;

    }*/
    return result;
}

function CheckValue(name ,entered,delname,rowNum)
{
    var result ;
    var allClear = 0;
    var yes = "yes";
    var no = "no";

    var shipchekbox;
    eval( shipchekbox  =  "window.document.shipconfirm." + name.toString() );

    if ( ((eval(shipchekbox.toString())).value )  == no.toString())
    {
        finalMsgt = "Please enter valid values for: \n\n";

        eval( deliverydate = "window.document.shipconfirm.datedelivered" )
        //eval( deliverydate = "window.document.shipconfirm." + delname.toString() )

        var expDate = eval(deliverydate.toString()).value;

        if ( (eval(deliverydate.toString())).value.length == 10 )
        {
            if ( checkdate((eval(deliverydate.toString()))) == false )
            {
                finalMsgt = finalMsgt +   " Delivery Date. \n" ;
                allClear = 1;
            }
        }
        else
        {
            finalMsgt = finalMsgt + " Delivery Date. \n" ;
            allClear = 1;
        }

        if (allClear < 1)
        {
            eval((eval(shipchekbox.toString())).value  = yes.toString() );;
            eval( (eval(shipchekbox.toString())).checked = true );;
            result = true;

            var printOk = document.getElementById("printOk"+rowNum+"");
	          printOk.value = "Yes";
        }
        else
        {
            eval((eval(shipchekbox.toString())).value  = no.toString() );;
            eval( (eval(shipchekbox.toString())).checked = false );;
            alert(finalMsgt);
            result = false;
        }

        if (true == result )
        {
            shipupdcount++;
        }

    }
    else if ( ((eval(shipchekbox.toString())).value )  == yes.toString())
    {
        eval((eval(shipchekbox.toString())).value  = no.toString() );
        eval( (eval(shipchekbox.toString())).checked = false );
        var printOk = document.getElementById("printOk"+rowNum+"");
	      printOk.value = "No";

        shipupdcount--;
        result = true;
    }

    return result;
}

function checkdate(objName)
{
    var datefield = objName;
    if (chkdate(objName) == false)
    {
        return false;
    }
    else
    {
        return true;
    }
}
function chkdate(objName)
{
    var strDatestyle = "US";
    var strDate;
    var strDateArray;
    var strDay;
    var strMonth;
    var strYear;
    var intday;
    var intMonth;
    var intYear;
    var booFound = false;
    var datefield = objName;
    var strSeparatorArray = new Array("-"," ","/",".");
    var intElementNr;
    var err = 0;
    var strMonthArray = new Array(12);
    strMonthArray[0] = "Jan";
    strMonthArray[1] = "Feb";
    strMonthArray[2] = "Mar";
    strMonthArray[3] = "Apr";
    strMonthArray[4] = "May";
    strMonthArray[5] = "Jun";
    strMonthArray[6] = "Jul";
    strMonthArray[7] = "Aug";
    strMonthArray[8] = "Sep";
    strMonthArray[9] = "Oct";
    strMonthArray[10] = "Nov";
    strMonthArray[11] = "Dec";
    strDate = datefield.value;
    if (strDate.length < 1)
    {
        return true;
    }
    for (intElementNr = 0; intElementNr < strSeparatorArray.length; intElementNr++)
    {
        if (strDate.indexOf(strSeparatorArray[intElementNr]) != -1)
        {
            strDateArray = strDate.split(strSeparatorArray[intElementNr]);
            if (strDateArray.length != 3)
            {
                err = 1;
                return false;
            }
            else
            {
                strDay = strDateArray[0];
                strMonth = strDateArray[1];
                strYear = strDateArray[2];
            }
            booFound = true;
        }
    }
    if (booFound == false)
    {
        if (strDate.length>5)
        {
            strDay = strDate.substr(0, 2);
            strMonth = strDate.substr(2, 2);
            strYear = strDate.substr(4);
        }
    }
    if (strYear.length == 2)
    {
        strYear = '20' + strYear;
    }
    if (strDatestyle == "US")
    {
        strTemp = strDay;
        strDay = strMonth;
        strMonth = strTemp;
    }
    intday = parseInt(strDay, 10);
    if (isNaN(intday))
    {
        err = 2;
        return false;
    }
    intMonth = parseInt(strMonth, 10);
    if (isNaN(intMonth))
    {
        for (i = 0;i<12;i++)
        {
            if (strMonth.toUpperCase() == strMonthArray[i].toUpperCase())
            {
                intMonth = i+1;
                strMonth = strMonthArray[i];
                i = 12;
            }
        }
        if (isNaN(intMonth))
        {
            err = 3;
            return false;
        }
    }
    intYear = parseInt(strYear, 10);
    if (isNaN(intYear))
    {
        err = 4;
        return false;
    }
    if (intMonth>12 || intMonth<1)
    {
        err = 5;
        return false;
    }
    if ((intMonth == 1
         || intMonth == 3
         || intMonth == 5
         || intMonth == 7
         || intMonth == 8
         || intMonth == 10
         || intMonth == 12) && (intday > 31 || intday < 1))
    {
        err = 6;
        return false;
    }
    if ((intMonth == 4
         || intMonth == 6
         || intMonth == 9
         || intMonth == 11) && (intday > 30 || intday < 1))
    {
        err = 7;
        return false;
    }
    if (intMonth == 2)
    {
        if (intday < 1)
        {
            err = 8;
            return false;
        }
        if (LeapYear(intYear) == true)
        {
            if (intday > 29)
            {
                err = 9;
                return false;
            }
        }
        else
        {
            if (intday > 28)
            {
                err = 10;
                return false;
            }
        }
    }
    var now = new Date();
    var year  = now.getYear()
    if ( year < 2000)
    {
        year = year + 1900;
    }
    var day  = now.getDate();
    var month  = now.getMonth() + 1;
    if (  strYear > year  )
    {
        return false;
    }
    if ( strYear == year )
    {
        if ( strMonth > month+1 )
        {
            return false;
        }
        if ( strMonth == month )
        {
            if ( strDay > day )
            {
                return false;
            }
        }
    }
    return true;
}
function LeapYear(intYear)
{
    if (intYear % 100 == 0)
    {
        if (intYear % 400 == 0)
        {
            return true;
        }
    }
    else
    {
        if ((intYear % 4) == 0)
        {
            return true;
        }
    }
    return false;
}
function checkexpirydate(objName)
{
    var datefield1 = objName;
    if (chkexpdate(objName) == false)
    {
        return false;
    }
    else
    {
        return true;
    }
}
function chkexpdate(objName)
{
    var strDatestyle1 = "US";
    var strDate1;
    var strDateArray1;
    var strDay1;
    var strMonth1;
    var strYear1;
    var intday1;
    var intMonth1;
    var intYear1;
    var booFound1 = false;
    var datefield1 = objName;
    var strSeparatorArray1 = new Array("-"," ","/",".");
    var intElementNr1;
    var err1 = 0;
    var strMonthArray1 = new Array(12);
    strMonthArray1[0] = "Jan";
    strMonthArray1[1] = "Feb";
    strMonthArray1[2] = "Mar";
    strMonthArray1[3] = "Apr";
    strMonthArray1[4] = "May";
    strMonthArray1[5] = "Jun";
    strMonthArray1[6] = "Jul";
    strMonthArray1[7] = "Aug";
    strMonthArray1[8] = "Sep";
    strMonthArray1[9] = "Oct";
    strMonthArray1[10] = "Nov";
    strMonthArray1[11] = "Dec";
    strDate1 = datefield1.value;
    if (strDate1.length < 1)
    {
        return true;
    }
    for (intElementNr1 = 0; intElementNr1 < strSeparatorArray1.length; intElementNr1++)
    {
        if (strDate1.indexOf(strSeparatorArray1[intElementNr1]) != -1)
        {
            strDateArray1 = strDate1.split(strSeparatorArray1[intElementNr1]);
            if (strDateArray1.length != 3)
            {
                err1 = 1;
                return false;
            }
            else
            {
                strDay1 = strDateArray1[0];
                strMonth1 = strDateArray1[1];
                strYear1 = strDateArray1[2];
            }
            booFound1 = true;
        }
    }
    if (booFound1 == false)
    {
        if (strDate1.length>5)
        {
            strDay1 = strDate1.substr(0, 2);
            strMonth1 = strDate1.substr(2, 2);
            strYear1 = strDate1.substr(4);
        }
    }
    if (strYear1.length == 2)
    {
        strYear1 = '20' + strYear1;
    }
    if (strDatestyle1 == "US")
    {
        strTemp1 = strDay1;
        strDay1 = strMonth1;
        strMonth1 = strTemp1;
    }
    intday1 = parseInt(strDay1, 10);
    if (isNaN(intday1))
    {
        err = 2;
        return false;
    }
    intMonth1 = parseInt(strMonth1, 10);
    if (isNaN(intMonth1))
    {
        for (i = 0;i<12;i++)
        {
            if (strMonth1.toUpperCase() == strMonthArray1[i].toUpperCase())
            {
                intMonth1 = i+1;
                strMonth1 = strMonthArray1[i];
                i = 12;
            }
        }
        if (isNaN(intMonth1))
        {
            err1 = 3;
            return false;
        }
    }
    intYear1 = parseInt(strYear1, 10);
    if (isNaN(intYear1))
    {
        err1 = 4;
        return false;
    }
    if (intMonth1>12 || intMonth1<1)
    {
        err1 = 5;
        return false;
    }
    if ((intMonth1 == 1
         || intMonth1 == 3
         || intMonth1 == 5
         || intMonth1 == 7
         || intMonth1 == 8
         || intMonth1 == 10
         || intMonth1 == 12) && (intday1 > 31 || intday1 < 1))
    {
        err1 = 6;
        return false;
    }
    if ((intMonth1 == 4
         || intMonth1 == 6
         || intMonth1 == 9
         || intMonth1 == 11) && (intday1 > 30 || intday1 < 1))
    {
        err1 = 7;
        return false;
    }
    if (intMonth1 == 2)
    {
        if (intday1 < 1)
        {
            err1 = 8;
            return false;
        }
        if (LeapYear(intYear1) == true)
        {
            if (intday1 > 29)
            {
                err1 = 9;
                return false;
            }
        }
        else
        {
            if (intday1 > 28)
            {
                err1 = 10;
                return false;
            }
        }
    }
}

function addTrackingNo(branchPlant) {
    var tmpCount = document.getElementById("totallines");
    var rowCount = parseInt(tmpCount.value);
    if (isNaN(parseInt(rowCount))) {
        rowCount = 0;
    }
    var test = "";
    for (i = 1; i <= rowCount; i++) {
         var tmp = document.getElementById("row_chk"+i);
         if (tmp.value == "yes") {
             test += i+"%2C";
         }
    }
    if (test.length > 0) {
        alert("Please uncheck all the checkboxes.");
        return false;
    }
    var loc = "/tcmIS/Hub/ShipConfirm?UserAction=AddTrackingNoPage&rowCheckedIndex="+test+"&branchPlant="+branchPlant;
    openWinGeneric(loc,"addTrackingNo","800","400","yes","80","80")
}


function shipconfirmshipment() {
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

function createShipment() {
    window.document.shipconfirm.UserAction.value = "CreateShipment";
    document.shipconfirm.target='';
    document.shipconfirm.action='/tcmIS/Hub/ShipConfirm';
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

function addToShipment() {
    window.document.shipconfirm.UserAction.value = "AddToShipment";
    document.shipconfirm.target='';
    document.shipconfirm.action='/tcmIS/Hub/ShipConfirm';
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

function removeFromShipment() {
    window.document.shipconfirm.UserAction.value = "RemoveFromShipment";
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

function printConsolPL(branchPlant) {
    /*var tmpCount = document.getElementById("totallines");
    var rowCount = parseInt(tmpCount.value);
    if (isNaN(parseInt(rowCount))) {
        rowCount = 0;
    }
    var test = "";
    for (i = 1; i <= rowCount; i++) {
         var tmp = document.getElementById("row_chk"+i);
         if (tmp.value == "yes") {
             test += i+"%2C";
         }
    }
    if (test.length > 0) {
        alert("Please uncheck all the checkboxes.");
        return false;
    }*/
    var loc = "/tcmIS/Hub/ShipConfirm?UserAction=PrintConsolPL&branchPlant="+branchPlant;
    openWinGeneric(loc,"printConsolPL","800","400","yes","80","80")

    /*window.document.shipconfirm.UserAction.value = "PrintConsolPL";
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
    return true;*/
}

function consolidate() {
   var totallines = document.getElementById("totallines").value;
   totallines = totallines*1;
   if(totallines == 0) {
     alert("You must select at least one shipment to print.");
   }
   else {
     var checked = "no";
     var tmp = "";
     var shippingId;
     for (var p = 0 ; p < totallines ; p ++) {
        var linecheck = document.getElementById("row_chk"+(p*1+1)+"");
	if(linecheck.checked) {
	  checked="yes";
          shippingId = document.getElementById("shipmentId"+(p*1+1)+"");
          if(tmp == "") {
            tmp = "?shipmentIds=" + "" + shippingId.value;
          }
          else {
            tmp = tmp + "," + shippingId.value;
          }
	}
     }
     if(checked == "no") {
       alert("You must select at least one shipment to print.");
     }
     else {
       var loc = "/tcmIS/hub/printpackinglist.do" + tmp;
       openWinGeneric(loc,"consolidate","800","500","yes","80","80")
    }
  }
}

function auditAddTrackingNo() {
   //alert("audit add tracking number.");
   return true;
}

function searchCarrierInfo(parentSelectedRow) {
   var inventoryGroup = document.getElementById("inventoryGroup"+parentSelectedRow);
   var tmp = escape(inventoryGroup.value);
   var loc = "/tcmIS/Hub/ShipConfirm?UserAction=BuildCarrierInfoPage&parentSelectedRow="+parentSelectedRow+"&inventoryGroup="+tmp;
   openWinGeneric(loc,"searchCarrierInfo","800","500","yes","80","80")
}

function auditSearchCarrierInfo() {

}

function processCarrierInfo() {
   //var selectedRow = document.getElementById("selectedRow");
	var selectedRow = window.document.searchCarrierInfo.selectedRow.value;
	if (selectedRow == null) {
      alert("Please select a Carrier Code.");
      return false;
   }
	var carrierCode = document.getElementById("carrierCode"+selectedRow);
   var carrierMethod = document.getElementById("carrierMethod"+selectedRow);
	if (carrierCode == null || carrierCode.value.length == 0) {
      alert("Please select a Carrier Code.");
      return false;
   }
   var accountOwner = document.getElementById("accountOwner"+selectedRow);
   var accountNumber = document.getElementById("accountNumber"+selectedRow);
   var parentSelectedRow = document.getElementById("parentSelectedRow").value;
	//set parent values
   var carrierCodeFld = "opener.document.forms[0].carrierCode" + parentSelectedRow;
   x_fld = eval(carrierCodeFld);
   x_fld.value = carrierCode.value;
   var acctOwnerFld = "opener.document.forms[0].accountOwner" + parentSelectedRow;
   x_fld = eval(acctOwnerFld);
   x_fld.value = accountOwner.value;
   var acctNumFld = "opener.document.forms[0].accountNumber" + parentSelectedRow;
   x_fld = eval(acctNumFld);
   x_fld.value = accountNumber.value;

   //close window
   cancel();
}

function carrierCodeClicked(selectedRow) {
   var tmpCarrierCode = document.getElementById("carrierCode"+selectedRow);
   var tmpCarrierMethod = document.getElementById("carrierMethod"+selectedRow);
   window.document.searchCarrierInfo.carrierCode.value = tmpCarrierCode.value;
   window.document.searchCarrierInfo.carrierMethod.value = "(" + tmpCarrierMethod.value + ")";
   window.document.searchCarrierInfo.selectedRow.value = selectedRow;
}

function cancel()
{
    window.close();
}
