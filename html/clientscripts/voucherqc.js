var submitcount=0;
var shipupdcount=0;
function SubmitOnlyOnce()
{
    if (submitcount == 0)
    {
        submitcount++;
        /*try
        {
         var target = document.all.item("TRANSITPAGE");
         target.style["display"] = "";
         var target1 = document.all.item("MAINPAGE");
         target1.style["display"] = "none";
        }
        catch (ex)
        {
        }*/

        return true;
    }
    else
    {
        alert("This form has already been submitted.\n Please Wait for Results.\n Thanks!");
        return false;
    }
}

function removeQcButton()
{
  try
  {
   var invstatus =  document.getElementById("invstatus");
   if (invstatus.value != "Approved")
   {
    var voucherQcButton =  document.getElementById("voucherQcButton");
    voucherQcButton.style["display"] = "none";
   }
  }
   catch (ex)
   {
   }
   return true;
}

function updateActionToQC()
{
 var Action =  document.getElementById("Action");
 Action.value = "qcVoucher";
}