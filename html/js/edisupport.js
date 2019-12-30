var submitcount=0;
var updatecount=0;

function showNotes(fieldid)
{
   var section = 'div_' + fieldid;
   var pgph_block = 'pgph_' + fieldid;
   var current = (document.getElementById(section).style.display == 'block') ? 'none' : 'block';
   document.getElementById(section).style.display = current;
   document.getElementById(pgph_block).innerHTML = (current == 'block') ? '<i>-</i>' : '<i>+</i>';   
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
        alert("This form has already been submitted.\n Please Wait for Results.\n Thanks!");
        return false;
    }
}

function resetSelections()
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
    document.ordstat.submit();
    return true;
}

function checkall(checkbx, formname, checkboxname, headername)
{
    // *note: requires TOTAL_ROWS to be defined prior to the inclusion of this

    var totallines = TOTAL_ROWS;

    var result;
    var valueq;
    if (checkbx.checked)
    {
        result = true;
        valueq = "yes";
    }
    else
    {
        result = false;
        valueq = "no";
    }

    // first do rows
    for ( var p = 1; p <= totallines; p++ )
    {
        try
        {
            var chkname = 'document.' + formname + '.' + checkboxname+p;
            var linecheck = eval(chkname);

            linecheck.checked =result;
            linecheck.value = valueq;
        }
        catch (ex1)
        {

        }
    }

    // next do headers
    for ( p = 1; p <= totallines; p++ )
    {
        try
        {
            var chkname = 'document.' + formname + '.' + headername+"_"+p;
            var linecheck = eval(chkname);

            linecheck.checked =result;
            linecheck.value = valueq;
        }
        catch (ex1)
        {

        }
    }
}

function openAddressChangeRequest(addressChangeRequestId,addressChangeType)
{
  var addressChangeUrl = "dpmsaddress.do?addressChangeRequestId=";
  addressChangeUrl += addressChangeRequestId;
  addressChangeUrl += "&type="+addressChangeType;

  openWinGeneric(addressChangeUrl,"address_ChangeRequest","800","650","yes","50","50");
}

function cancelOrder(docNumWithSuffix)
{
  if (docNumWithSuffix.length > 0)
  {
   var loc = "/tcmIS/haas/dlagasordertrackingresults.do?userAction=cancelOrder&docNumWithSuffix=" +docNumWithSuffix                           
   openWinGeneric(loc,'_CancelOrder','400','200','yes',"50","50","no");
  }
}