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

String.prototype.trim = function()
{
// skip leading and trailing whitespace
// and return everything in between
  return this.replace(/^\s*(\b.*\b|)\s*$/, "$1");
}

function openWinGeneric(destination,WinName,WinWidth, WinHeight, Resizable )
{
    windowprops = "toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,status=no,top=200,left=200,width=" + WinWidth + ",height=" + WinHeight+",resizable=" + Resizable;
    preview = window.open(destination, WinName,windowprops);
}


function countrychanged(object)
{
	var artist;
   artist = object.options[object.selectedIndex].value;

   var selectedIndex = object.selectedIndex;

	for (var i = document.newsupplier.state_abbrev.length; i > 0;i--)
   {
      document.newsupplier.state_abbrev.options[i] = null;
   }
	showinvlinks(artist);
	window.document.newsupplier.state_abbrev.selectedIndex = 0;
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
    document.newsupplier.state_abbrev.options[href] = optionName;
}

function checkvalues()
{
    var finalMsgt;
    finalMsgt = "Please enter valid values for: \n\n";

    var result ;
    var allClear = 0;

     var supplier_name =  document.getElementById("supplier_name");
     if (supplier_name.value.trim().length == 0)
     {
     finalMsgt = finalMsgt + " Supplier Name.\n" ;
     allClear = 1;
     }

     /*var country_abbrev =  document.getElementById("country_abbrev");
     if (country_abbrev.value.trim().length == 0)
     {
     finalMsgt = finalMsgt + " country_abbrev.\n" ;
     allClear = 1;
     }*/
     var state_abbrev =  document.getElementById("state_abbrev");
     if (state_abbrev.value.trim().length == 0)
     {
     finalMsgt = finalMsgt + " state_abbrev.\n" ;
     allClear = 1;
     }

     var address_line_1 =  document.getElementById("address_line_1");
     if (address_line_1.value.trim().length == 0)
     {
     finalMsgt = finalMsgt + " Address Line.\n" ;
     allClear = 1;
     }

     var city =  document.getElementById("city");
     if (city.value.trim().length == 0)
     {
     finalMsgt = finalMsgt + " City.\n" ;
     allClear = 1;
     }

     var zip =  document.getElementById("zip");
     if (zip.value.trim().length == 0)
     {
     finalMsgt = finalMsgt + " Zip.\n" ;
     allClear = 1;
     }

     var supplier_main_phone =  document.getElementById("supplier_main_phone");
     if (supplier_main_phone.value.trim().length == 0)
     {
     finalMsgt = finalMsgt + " Supplier Main Phone.\n" ;
     allClear = 1;
     }

	  var default_payment_terms =  document.getElementById("default_payment_terms");
     if (default_payment_terms.value.trim().length == 0)
     {
     finalMsgt = finalMsgt + " Default Payment Terms.\n" ;
     allClear = 1;
     }

     var federal_tax_id =  document.getElementById("federal_tax_id");
	  var country_abbrev =  document.getElementById("country_abbrev");
     if (country_abbrev.value.trim() == "USA" && federal_tax_id.value.trim().length == 0)
     {
     finalMsgt = finalMsgt + " Federal Tax Id.\n" ;
     allClear = 1;
     }

     var qualification_level =  document.getElementById("qualification_level");
     if (qualification_level.value.trim().length == 0)
     {
     finalMsgt = finalMsgt + " Qualification Level.\n" ;
     allClear = 1;
     }
     var type_of_purchase =  document.getElementById("type_of_purchase");
     if (type_of_purchase.value.trim().length == 0)
     {
     finalMsgt = finalMsgt + " Type of Purchase.\n" ;
     allClear = 1;
     }

    if (allClear < 1)
    {
        result = true;
    }
    else
    {
        alert(finalMsgt);
        result = false;
    }
    return result;
}

function reject(entered)
{
var rejectreasons = document.getElementById("rejectreasons");
var rejectcomment = prompt("Enter Comments for Rejection:" ,"");
rejectreasons.value = rejectcomment;

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
    window.document.newsupplier.UserAction.value = "UPDATE";
    window.document.newsupplier.SubUserAction.value = "reject";
    return true;
}

function newrequest(entered)
{
	var checkresult ;
	checkresult =checkvalues();

	if (checkresult)
	{
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
    window.document.newsupplier.UserAction.value = "UPDATE";
    window.document.newsupplier.SubUserAction.value = "newrequest";
    return true;
	 }
    else
    {
    	return false;
    }
}

function backsearch(entered)
{
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
    window.document.newsupplier.UserAction.value = "BackSearch";
    window.document.newsupplier.SubUserAction.value = "BackSearch";
    return true;
}

function updpayterms(entered)
{
	var checkresult ;
	checkresult =checkvalues();

	if (checkresult)
	{
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
    window.document.newsupplier.UserAction.value = "UPDATE";
    window.document.newsupplier.SubUserAction.value = "updpayterms";
    return true;
   }
   else
   {
	 return false;
   }
}

function approve(entered)
{
	var checkresult ;
	checkresult =checkvalues();

	var e_supplier_id =  document.getElementById("e_supplier_id");
   if (e_supplier_id.value.trim().length == 0)
   {
     alert("Please enter valid values for: \n\nE Supplier Id.\n");
	  return false;
   }

	if (checkresult)
	{
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
    window.document.newsupplier.UserAction.value = "UPDATE";
    window.document.newsupplier.SubUserAction.value = "approve";
    return true;
    }
    else
    {
    	return false;
    }
}

function update(entered)
{
	var checkresult ;
	checkresult =checkvalues();

	if (checkresult)
	{
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
    window.document.newsupplier.UserAction.value = "UPDATE";
    window.document.newsupplier.SubUserAction.value = "save";
    return true;
	 }
    else
    {
    	return false;
    }
}

function search(entered)
{
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
    window.document.newsupplier.UserAction.value = "Search";
    window.document.newsupplier.SubUserAction.value = "NA";
    return true;
}

function StartNew(entered)
{
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
    window.document.newsupplier.UserAction.value = "New";
    window.document.newsupplier.SubUserAction.value = "STARTNEW";
    return true;
}