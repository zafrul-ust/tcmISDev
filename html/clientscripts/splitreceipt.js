function checkallowstatus()
{
 var lotstatus =  document.getElementById("selectElementStatus");
 var origlotstatus =  document.getElementById("origlotstatus");
 var qualitycntitem =  document.getElementById("qualitycntitem");
 var originvgrp =  document.getElementById("originvgrp");
 var invengrp =  document.getElementById("invengrp");

 if (qualitycntitem.value == "Y")
 {
	var allowedinvengrp = false;
	var pickstatus = false;

for (var i=0; i < pickablestatus.length; i++)
{
var pickthissts = pickablestatus [i];
if (pickthissts == lotstatus.value)
{
   pickstatus = true;
}
}

if (pickstatus == true)
{
	 //if (origlotstatus.value != lotstatus.value)
	 {
	   for (var i=0; i < allowedinvgrps.length; i++)
	   {
	      var allowedinvgrp = allowedinvgrps[i];
	      if (allowedinvgrp == invengrp.value)
	      {
	         allowedinvengrp = true;
	      }
      }

	 if (!allowedinvengrp)
	 {
	 alert("You don't have permissions to split to a pickable status.")
	 lotstatus.value = origlotstatus.value;
	 invengrp.value = originvgrp.value;
	 return false;
	 }

   }

    //if (originvgrp.value != invengrp.value)
	 {
	   for (var i=0; i < allowedinvgrps.length; i++)
	   {
	      var allowedinvgrp = allowedinvgrps[i];
	      if (allowedinvgrp == invengrp.value)
	      {
	         allowedinvengrp = true;
	      }
      }

	 if (!allowedinvengrp)
	 {
	 alert("You don't have permissions to split to a pickable status for this inventory group.")
	 lotstatus.value = origlotstatus.value;
    invengrp.value = originvgrp.value;
    return false;
	 }

   }
 }
}
return true;
}

//07-14-02
function sendsplitbin(name,entered)
{
    if (checkallowstatus() && CheckSplitQtyValue(name,entered))
    {
    window.document.receiving.UserAction.value = "UPDATE";
    window.document.receiving.SubUserAction.value = "Updatesplit";

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
		return false;
	}
}

//07-14-02
function CheckSplitQtyValue(name ,entered)
{
    var result ;
    var allClear = 0;
    var yes = "yes";
    var no = "no";

    finalMsgt = "Please enter valid values for: \n\n";

    var QtyAvailable  =  document.getElementById("QtyAvailable").value;
    var maxsplitqty  =  document.getElementById("maxsplitqty");
    maxsplitqty.value = QtyAvailable;

    //var maxsplitqty  =  window.document.receiving.maxsplitqty.value;
    var v  =  document.getElementById("splitqty").value;
    //eval( testqty = "window.document.receiving.splitqty")
    //var v = (eval(testqty.toString())).value;
    if ( !(isFloat(v)) )
    {
        finalMsgt = finalMsgt + " Quantity To Split. \n";
        allClear = 1;
        testqty12  =  eval("window.document.receiving.splitqty");
        testqty12.value = "";
    }
    else if ( v*1 < 0 || v*1 == 0)
    {
        finalMsgt = finalMsgt + " Quantity To Split. \n";
        allClear = 1;
        testqty12  =  eval("window.document.receiving.splitqty");
        testqty12.value = "";
    }
    else if ( v*1 > QtyAvailable*1 )
    {
        finalMsgt = finalMsgt + " Quantity To Split <= Quantity On-Hand. \n";
        allClear = 1;
        testqty12  =  eval("window.document.receiving.splitqty");
        testqty12.value = "";
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

function cancel()
{
    window.close();
}