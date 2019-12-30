var submitcount=0;
var shipupdcount=0;
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

function doUnConfPopup()
{
  var testbin;
  eval( testbin =  "window.document.receiving.HubName");
  var cur = null;
  eval( cur = (eval(testbin.toString())).selectedIndex );
  var curval = null;
  ( curval =  (eval(testbin.toString())).options[cur].value );
  if (curval == "All")
  {
    alert("Please Choose a Hub");
    return false;
  }

  var unconfURL = "/tcmIS/Hub/ShowUnconfirmedReceipts?session=Active";
  unconfURL = unconfURL + "&HubNo=" + curval;
  unconfURL = unconfURL + "&customownd=yes";
  unconfURL = unconfURL + "&genLabels=1";
  openWinGeneric(unconfURL,"Generate_customer_returns_labels","640","600","yes")
}

//07-17-02
function sendbin(name,entered)
{
    var itemID  =  window.document.EmptyBins.ItemID.value;
    var lineNum  =  window.document.EmptyBins.LineNum.value;

    var testbin;
    eval( testbin =  "window.document.EmptyBins.EmptyBin");
    var cur = null;
    eval( cur = (eval(testbin.toString())).selectedIndex );
    var curval = null;
    ( curval =  (eval(testbin.toString())).options[cur].value );

    opener.document.receiving.UserAction.value = "AddBin";
    opener.document.receiving.SubUserAction.value = "AddBin";
    opener.document.receiving.AddBin_Item_Id.value = itemID.toString();
    opener.document.receiving.AddBin_Bin_Id.value = curval.toString();
    opener.document.receiving.DuplLineNumber.value = "NA"  ;
    opener.document.receiving.AddBin_Line_No.value = lineNum.toString();

    opener.document.receiving.submit();
    window.close();
}

//07-17-02
function showEmtyBins(itemID,LineNo,Hub)
{
    loc = "/tcmIS/servlet/radian.web.servlets.internal.InternalShowEmptyBins?itemID=";
    loc = loc + itemID;
    loc = loc + "&HubNo=" + Hub;
    loc = loc + "&LineNo=" + LineNo;
    openWinGeneric(loc,"Show_Empty_Bins","300","150","no");
}

function openWinGeneric(destination,WinName,WinWidth, WinHeight, Resizable )
{
    windowprops = "toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,status=no,top=200,left=200,width=" + WinWidth + ",height=" + WinHeight+",resizable=" + Resizable;
    preview = window.open(destination, WinName,windowprops);
}

function reshow(object)
{
    artist = object.options[object.selectedIndex].text;
    var indexselectec = object.options[object.selectedIndex]
        var indexofxompany = object.options[object.selectedIndex].value;
    for (var i = document.receiving.FacName.length;i > 0;i--)
    {
        document.receiving.FacName.options[i] = null;
    }
    reloading = true;
    showlinks(indexofxompany);
    window.document.receiving.FacName.selectedIndex = 0;
}
function showlinks(selectedIndex)
{
    var indextoshow = hubnumbers[selectedIndex];
    var companytoshow = hubnames[indextoshow];
    var nickNameValue = new Array();
    var nickNameValueid = new Array();
    nickNameValue = altName[companytoshow];
    nickNameValueid = altNameid[companytoshow];
    for (var i=0; i < nickNameValue.length; i++)
    {
        opt(i,nickNameValue[i],nickNameValueid[i])
    }
}
function opt(href,text,id)
{
    var optionName = new Option(text, id, false, false)
    document.receiving.FacName.options[href] = optionName;
}

function search(entered)
{

    var testbin;
    eval( testbin =  "window.document.receiving.HubName");
    var cur = null;
    eval( cur = (eval(testbin.toString())).selectedIndex );
    var curval = null;
    ( curval =  (eval(testbin.toString())).options[cur].value );
    if (curval == "All")
    {
        alert("Please Choose a Hub");
        return false;

    }

    window.document.receiving.UserAction.value = "Search";
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

function update(entered)
{
    window.document.receiving.UserAction.value = "Update";
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


var defaultEmptyOK = false
    function isEmpty(s)
{
    return ((s == null) || (s.length == 0))
}
function isDigit (c)
{
    return ((c >= "0") && (c <= "9"))
}
function isInteger (s)
{
    var i;
    if (isEmpty(s))
        if (isInteger.arguments.length == 1) return defaultEmptyOK;
        else return (isInteger.arguments[1] == true);
    for (i = 0; i < s.length; i++)
    {
        // Check that current character is number.
        var c = s.charAt(i);
        if (!isDigit(c)) return false;
    }
    return true;
}


function checkreturnvalue(name,entered,delname)
{
    var result ;
    var allClear = 0;
    var yes = "yes";
    var no = "no";
    var NONE = "NONE";

    var custchekbox;
    eval( custchekbox  = "window.document.receiving." + name.toString() );

    if ( ((eval(custchekbox.toString())).value )  == no.toString())
    {
        finalMsgt = "Please enter valid values for: \n\n";

        eval( testqty = "window.document.receiving.qtyreturned" + delname.toString() )
	  eval( mrqty0 = "window.document.receiving.qtyonmr" + delname.toString() )

        mrqty = (eval(mrqty0.toString())).value;

        var v = (eval(testqty.toString())).value;
        if ( v*1 < 0 )
        {
            finalMsgt = finalMsgt + " Quantity Returned. \n";
            allClear = 1;

            testqty12  =  eval("window.document.receiving.qtyreturned" + delname.toString() );
            testqty12.value = "";
        }
	  else if ( v*1 == 0 )
        {
            finalMsgt = finalMsgt + " Quantity Returned. \n";
            allClear = 1;

            testqty12  =  eval("window.document.receiving.qtyreturned" + delname.toString() );
            testqty12.value = "";
        }
        else if ( !(isInteger(v)) )
        {
            finalMsgt = finalMsgt + " Quantity Returned. \n";
            allClear = 1;
            testqty12  =  eval("window.document.receiving.qtyreturned" + delname.toString() );
            testqty12.value = "";
        }

	  if ( v*1 > mrqty*1 )
        {
            finalMsgt = finalMsgt + " Quantity Returned. \n";
            allClear = 1;

            testqty12  =  eval("window.document.receiving.qtyreturned" + delname.toString() );
            testqty12.value = "";
        }


		eval( testdatereceipt = "window.document.receiving.date_recieved" + delname.toString() )
            if ( (eval(testdatereceipt.toString())).value.length == 10 )
            {
                if ( checkdate((eval(testdatereceipt.toString()))) == false )
                {
                    finalMsgt = finalMsgt +   " Date of Receipt. \n" ;
                    allClear = 1;
                }
            }
            else
            {
                finalMsgt = finalMsgt + " Date of Receipt.\n" ;
                allClear = 1;
            }

	      var testbin;
            eval( testbin =  "window.document.receiving.selectElementBin" + delname.toString() );
            var cur = null;
            eval( cur = (eval(testbin.toString())).selectedIndex );
            var curval = null;
            ( curval =  (eval(testbin.toString())).options[cur].value );
            if ( curval  == NONE.toString())
            {
                finalMsgt = finalMsgt +   " BIN. \n" ;
                allClear = 1;
            }




        if (allClear < 1)
        {
            eval((eval(custchekbox.toString())).value  = yes.toString() );;
            eval( (eval(custchekbox.toString())).checked = true );;
            result = true;
        }
        else
        {
            eval((eval(custchekbox.toString())).value  = no.toString() );;
            eval( (eval(custchekbox.toString())).checked = false );;
            alert(finalMsgt);
            result = false;
        }

        if (true == result )
        {
            shipupdcount++;
        }

    }
    else if ( ((eval(custchekbox.toString())).value )  == yes.toString())
    {
        eval((eval(custchekbox.toString())).value  = no.toString() );
        eval( (eval(custchekbox.toString())).checked = false );
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

        if ( strMonth > month )
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