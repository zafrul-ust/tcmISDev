function showNotes(fieldid)
{
   var section = 'div' + fieldid;
   var pgphBlock = 'pgph' + fieldid;
   var current = (document.getElementById(section).style.display == 'block') ? 'none' : 'block';
   document.getElementById(section).style.display = current;
   document.getElementById(pgphBlock).innerHTML = (current == 'block') ? '<i>-</i>' : '<i>+</i>';   
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
    for ( var p = 0; p <= totallines; p++ )
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
            var chkname = 'document.' + formname + '.' + headername+p;
            var linecheck = eval(chkname);

            linecheck.checked =result;
            linecheck.value = valueq;
        }
        catch (ex1)
        {

        }
    }
}

function validatePickingForm() {
  document.genericForm.target='';
  if(!isInteger(document.genericForm.prNumber.value.trim(), true)) {
    alert(messagesData.mrNumberInteger);
    return false;
  }
  if(!isInteger(document.genericForm.expireDays.value.trim(), true)) {
    alert(messagesData.expireDaysInteger);
    return false;
  }
  return true;
}

function countChecked() {
  // *note: requires TOTAL_ROWS to be defined prior to the inclusion of this
  var totallines = TOTAL_ROWS;
  // first do rows
  var totalChecked = 0;
  for ( var p = 0; p <= totallines; p++ )
  {
     try
     {
        var checkboxname = 'ok' + p;
        var chkname = 'document.genericForm.' + checkboxname;
        
        var linecheck = eval(chkname);

        if (linecheck.checked==true) {
          totalChecked++;
        }
     }
     catch (ex1)
     {
     }
  }
  return totalChecked;
}

function showpickingpagelegend()
{
  openWinGeneric("/tcmIS/help/pickingpagelegend.html","pickingpagelegend","290","300","no","50","50")
}

function genPicklist(confirmation)
{
   var checked = countChecked();
   if (checked==0) {
      alert(messagesData.pleaseSelect);
      return false;
   } 
   else {
      document.genericForm.action.value = 'generate';
      document.genericForm.submit();
      return true;
   }
}        

function doPrintBolShort()
{
    var testurl3 = "/tcmIS/Hub/PickingQC?session=Active&generate_bols=yes&UserAction=generatebols";
    var paperSize  =  window.document.picking.boldetails.value;
    testurl3 = testurl3 + "&boldetails=" + paperSize ;
    openWinGeneric(testurl3,"Generate_Bols","640","600","yes")
}

function doPrintBolLong()
{
    var testurl3 = "/tcmIS/Hub/PickingQC?session=Active&generate_bols=yes&UserAction=generatebols";
    var paperSize  =  window.document.picking.boldetails.value;
    testurl3 = testurl3 + "&boldetails=" + paperSize ;
    openWinGeneric(testurl3,"Generate_Bols","640","600","yes")
}

function doPrintCons()
{

}

function doPrintbox()
{
    var testurl3 = "/tcmIS/hub/reprintboxlbls?";
    HubNoforlabel = document.getElementById("HubName");
    testurl3 = testurl3 + "HubNoforlabel=" + HubNoforlabel.value ;
    openWinGeneric(testurl3,"Generate_Boxlabels","640","600","yes")
}