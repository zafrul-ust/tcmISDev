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

function update(entered)
{
   var allClear = 0;
	var finalMsgt = "";

	itemid = document.getElementById("item_id").value;
	if (itemid.trim().length == 0)
	{
	 finalMsgt = finalMsgt + "Haas Item # is Needed to Submit Label Information\n";
	 allClear = 1;
	}

	health=document.getElementById( "health" ).value;
	if ( health.trim().length == 0 )
	{
	  finalMsgt = finalMsgt + "Please provide a valid value for Health.\n";
	  allClear = 1;
	}

	flamability=document.getElementById( "flamability" ).value;
	if ( flamability.trim().length == 0 )
	{
	  finalMsgt = finalMsgt + "Please provide a valid value for Flamability.\n";
	  allClear = 1;
	}

	reactivity=document.getElementById( "reactivity" ).value;
	if ( reactivity.trim().length == 0 )
	{
	  finalMsgt = finalMsgt + "Please provide a valid value for Reactivity.\n";
	  allClear = 1;
	}

	chemicalstorage=document.getElementById( "chemicalstorage" ).value;
	if ( chemicalstorage.trim().length == 0 )
	{
	  finalMsgt = finalMsgt + "Please provide a valid value for Chemical Storage.\n";
	  allClear = 1;
	}

	signalword=document.getElementById( "signalword" ).value;
	if ( signalword.trim().length == 0 )
	{
	  finalMsgt = finalMsgt + "Please provide a valid value for Signal Word.\n";
	  allClear = 1;
	}

	perprotectiveequip=document.getElementById( "perprotectiveequip" ).value;
	if ( perprotectiveequip.trim().length == 0 )
	{
	  finalMsgt = finalMsgt + "Please provide a valid value for PPE.\n";
	  allClear = 1;
	}

	disposablecode=document.getElementById( "disposablecode" ).value;
	if ( disposablecode.trim().length == 0 )
	{
	  finalMsgt = finalMsgt + "Please provide a valid value for Disposable Code.\n";
	  allClear = 1;
	}

	disposablecontainercode=document.getElementById( "disposablecontainercode" ).value;
	if ( disposablecontainercode.trim().length == 0 )
	{
	  finalMsgt = finalMsgt + "Please provide a valid value for Disposable Container Code.\n";
	  allClear = 1;
	}

	obj=document.getElementById( "hazard" )
	if ( obj.length <= 0 )
	{
	  finalMsgt = finalMsgt + "Please Select Hazard Codes.\n";
  	  allClear = 1;
	}

	var noofSelectedoptions=0;
	var len=obj.length;
	for ( i=1; i < len; i++ )
	{
	  if ( obj.options[i].selected )
	  {
		noofSelectedoptions++;
	  }
	}

	if ( noofSelectedoptions > 0 )
	{

	}
	else
	{
	  finalMsgt = finalMsgt + "Please Select Two Valid Hazard Codes.\n";
	  allClear = 1;
	}

	if (allClear < 1)
   {
	window.document.MainForm.Session.value = "UPDATE";

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
		alert(finalMsgt);
		return false;
	}
}

function search(entered)
{
    window.document.MainForm.Session.value = "NEW";

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

function openWinGeneric(destination,WinName,WinWidth, WinHeight,Resizable,topCoor,leftCoor )
{
windowprops = "toolbar=no,location=no,directories=no,menubar=no,scrollbars=no,status=no,top="+topCoor+",left="+leftCoor+",width="+WinWidth+",height="+WinHeight+",resizable="+Resizable;
preview = window.open(destination, WinName,windowprops);
}

String.prototype.trim = function()
{
// skip leading and trailing whitespace
// and return everything in between
  return this.replace(/^\s*(\b.*\b|)\s*$/, "$1");
}

function checkForTwo(obj)
{
	if(obj.length <= 0)
          return;
   var noofSelectedoptions = 0;
   var len = obj.length;
   for(i=0;i<len;i++)
   {
      if (obj.options[i].selected)
      {
			noofSelectedoptions ++;

			/*if (noofSelectedoptions > 1)
			{
				obj.options[i].selected = false;
			}*/
      }
   }

   if (noofSelectedoptions > 2)
   {
		alert("You Can Only Select Two Options.");

		for(i=0;i<len;i++)
	   {
	      if (obj.options[i].selected)
	      {
	         if (noofSelectedoptions > 1)
	         {
	            obj.options[i].selected = false;
	         }
	      }
	   }
   }

   //alert(noofSelectedoptions);
}

function CheckValues(entered)
{
   var allClear = 0;
	var finalMsgt = "";

	catpartno=document.getElementById( "haas_item_no" ).value;
	if ( catpartno.trim().length == 0 )
	{
	  finalMsgt = finalMsgt + "Haas Item # is Needed to Submit Label Information.\n";
	  allClear = 1;
	}

	health=document.getElementById( "health" ).value;
	if ( health.trim().length == 0 )
	{
	  finalMsgt = finalMsgt + "Please provide a valid value for Health.\n";
	  allClear = 1;
	}

	flamability=document.getElementById( "flamability" ).value;
	if ( flamability.trim().length == 0 )
	{
	  finalMsgt = finalMsgt + "Please provide a valid value for Flamability.\n";
	  allClear = 1;
	}

	reactivity=document.getElementById( "reactivity" ).value;
	if ( reactivity.trim().length == 0 )
	{
	  finalMsgt = finalMsgt + "Please provide a valid value for Reactivity.\n";
	  allClear = 1;
	}

	chemicalstorage=document.getElementById( "chemicalstorage" ).value;
	if ( chemicalstorage.trim().length == 0 )
	{
	  finalMsgt = finalMsgt + "Please provide a valid value for Chemical Storage.\n";
	  allClear = 1;
	}

	signalword=document.getElementById( "signalword" ).value;
	if ( signalword.trim().length == 0 )
	{
	  finalMsgt = finalMsgt + "Please provide a valid value for Signal Word.\n";
	  allClear = 1;
	}

	perprotectiveequip=document.getElementById( "perprotectiveequip" ).value;
	if ( perprotectiveequip.trim().length == 0 )
	{
	  finalMsgt = finalMsgt + "Please provide a valid value for PPE.\n";
	  allClear = 1;
	}

	disposablecode=document.getElementById( "disposablecode" ).value;
	if ( disposablecode.trim().length == 0 )
	{
	  finalMsgt = finalMsgt + "Please provide a valid value for Disposable Code.\n";
	  allClear = 1;
	}

	disposablecontainercode=document.getElementById( "disposablecontainercode" ).value;
	if ( disposablecontainercode.trim().length == 0 )
	{
	  finalMsgt = finalMsgt + "Please provide a valid value for Disposable Container Code.\n";
	  allClear = 1;
	}

	obj=document.getElementById( "hazard" )
	if ( obj.length <= 0 )
	{
	  finalMsgt = finalMsgt + "Please Select Hazard Codes.\n";
  	  allClear = 1;
	}

	var noofSelectedoptions=0;
	var len=obj.length;
	for ( i=1; i < len; i++ )
	{
	  if ( obj.options[i].selected )
	  {
		noofSelectedoptions++;
	  }
	}

	if ( noofSelectedoptions > 0 )
	{

	}
	else
	{
	  finalMsgt = finalMsgt + "Please Select Two Valid Hazard Codes.\n";
	  allClear = 1;
	}


	if (allClear < 1)
   {
		return true;
	}
	else
	{
		alert(finalMsgt);
		return false;
	}
}

function removeAllHazards()
{
	removeAllOptionItem(document.getElementById("hazard"));
}

function getHazardvalues()
{
	//removeAllOptionItem(document.getElementById("hazard"));

	var health  =  document.getElementById("health").value;
	var flamability  =  document.getElementById("flamability").value;
	var reactivity  =  document.getElementById("reactivity").value;

	if (health.trim().length > 0 && flamability.trim().length > 0 && reactivity.trim().length > 0)
	{
    loc = "/tcmIS/seagate/labelinfo?Session=gethazardvalues";
    loc = loc + "&health=" + health;
    loc = loc + "&flamability=" + flamability;
    loc = loc + "&reactivity=" + reactivity;

    openWinGeneric(loc,"getHazardvalues","100","100","no","50","50")
   }
   else
   {
		alert("Please Select Health, Flamability and Reactivity Values.");
   }
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

function cancel()
{
    window.close();
}