function showErrorMessages()
{
  parent.showErrorMessages();
}

function myOnload()
{
 setResultFrameSize();
 /*Dont show any update links if the user does not have permissions.
 Remove this section if you don't have any links on the main page*/
 if (!showUpdateLinks)
 {
  parent.document.getElementById("updateResultLink").style["display"] = "none";
 }
 else
 {
  parent.document.getElementById("updateResultLink").style["display"] = "";
 }
}

function updateAllRows(checkBoxName)
{
var checkAll = document.getElementById("checkAll");
var totalLines = document.getElementById("totalLines").value;
totalLines = totalLines*1;

var result ;
var valueq;
if (checkAll.checked)
{
  result = true;
  //valueq = "yes";
}
else
{
  result = false;
  //valueq = "no";
}

 for ( var p = 0 ; p < totalLines ; p ++)
 {
	try
	{
	var lineCheck = document.getElementById(""+checkBoxName+""+(p*1)+"");
	lineCheck.checked =result;
	//linecheck.value = valueq;
	}
	catch (ex1)
	{

	}
 }
}

var inputChangedCount=0;
function distributedCountChanged(rowNum)
{
 var usageChanged  =  document.getElementById("usageChanged"+rowNum+"");
 usageChanged.value = "Y";

 var uomDistributedUsage = document.getElementById("uomDistributedUsage"+rowNum+"");
 if (uomDistributedUsage.value.trim().length > 0 && !(isFloat(uomDistributedUsage.value)))
 {
  var errorMessage = messagesData.validValues+ "\n\n";;
  errorMessage = errorMessage + messagesData.distributedUsage + "\n";
  alert(errorMessage);
  uomDistributedUsage.value = "";
 }

 inputChangedCount++;
}

function checkUsageTotals()
{
 var errorMessage = messagesData.validValues+ "\n\n";;
 var warningMessage = messagesData.alert+ "\n\n";;
 var errorCount = 0;
 var warnCount = 0;
 var rowNumber = 0;

var totalMainLines = document.getElementById("totalMainLines").value;
totalMainLines = totalMainLines*1;

 for (var i = 0; i < totalMainLines; i++)
 {
    var mainCatPartNo = distributedCountData[i].catPartNo;
    var mainInventoryGroup = distributedCountData[i].inventoryGroup;
    var mainRowSpan=distributedCountData[i].rowSpan;

    if (mainRowSpan == 1)
    {
     var ok  =  document.getElementById("ok"+(rowNumber)+"");
     var usage = distributedCountData[i].itemcollection[0].usage;
     var uomDistributedUsage = document.getElementById("uomDistributedUsage"+rowNumber+"");
     if ( ok.checked )
     {
      if (Math.round(10000*uomDistributedUsage.value)/10000 > Math.round(10000*usage)/10000)
      {
       errorMessage += messagesData.distributedUsageGreater;
       errorMessage += "\n"+messagesData.inventorygroup+": "+ mainInventoryGroup +" "+messagesData.and+" "+messagesData.partno+": "+mainCatPartNo+" "+messagesData.and + " " + messagesData.item + ": ";
       errorMessage += itemCollection[itemCount].itemId +".\n\n";
       errorCount = 1;
      }
     }
     rowNumber++;
    }
    else
    {
    var itemCollection = distributedCountData[i].itemcollection;

    for(var itemCount=0;itemCount<itemCollection.length;itemCount++){
     if (itemCount > 0 && itemCollection.length > 1)
     {
      rowNumber++;
     }

    var ok  =  document.getElementById("ok"+rowNumber+"");
    if ( ok.checked )
    {
     var usage = itemCollection[itemCount].usage;
     var totalDistributedUsage=0;

     var workAreaCollection = itemCollection[itemCount].workAreaCollection;
     for(var materialCount=0;materialCount<workAreaCollection.length;materialCount++){
     if (materialCount > 0 && workAreaCollection.length > 1)
     {
      rowNumber++;
     }
     try {
      var uomDistributedUsage = document.getElementById("uomDistributedUsage"+rowNumber+"");
      totalDistributedUsage += uomDistributedUsage.value*1;
     }
     catch (ex)
     {
      totalDistributedUsage += workAreaCollection[materialCount].uomDistributedUsage*1;
     }
    }

    if (Math.round(10000*totalDistributedUsage)/10000 > Math.round(10000*usage)/10000)
    {
      errorMessage += messagesData.distributedUsageGreater;
      errorMessage += "\n"+messagesData.inventoryGroup+": "+ mainInventoryGroup +" "+messagesData.and+" "+messagesData.partno+": "+mainCatPartNo+" "+messagesData.and + " " + messagesData.item + ": ";
      errorMessage += itemCollection[itemCount].itemId +".\n\n";
      errorCount = 1;
    }
    else if (Math.round(10000*totalDistributedUsage)/10000 < Math.round(10000*usage)/10000)
    {
      errorMessage += "Sum of Distributed Usage cannot be less than counted usage for";
      errorMessage += "\n"+messagesData.inventoryGroup+": "+ mainInventoryGroup +" "+messagesData.and+" "+messagesData.partno+": "+mainCatPartNo+" "+messagesData.and + " " + messagesData.item + ": ";
      errorMessage += itemCollection[itemCount].itemId +".\n\n";
      errorCount = 1;
    }
    }
    else
    {
     var workAreaCollection = itemCollection[itemCount].workAreaCollection;
     for(var materialCount=0;materialCount<workAreaCollection.length;materialCount++){
     if (materialCount > 0 && workAreaCollection.length > 1)
     {
      rowNumber++;
     }
    }
    }
   }
   rowNumber++;
  }
 }

 if (errorCount >0)
 {
  alert(errorMessage);
  return false;
 }
 else
 {
   return true;
 }
}

function checkInput(rownum)
{
var errorMessage = messagesData.validValues+ "\n\n";;
var warningMessage = messagesData.alert+ "\n\n";;
var errorCount = 0;
var warnCount = 0;
var parentRowId = mygrid.getUserData((rownum*1),"parentRowId");
var mainRowSpan = mygrid.getUserData((parentRowId*1),"mainRowSpan");

var ok = document.getElementById("ok"+parentRowId+"");
inputChangedCount++;
/*
if (ok.checked)
{
 var uomDistributedUsage = document.getElementById("uomDistributedUsage"+rownum+"");
 if (uomDistributedUsage.value.trim().length > 0 && !(isFloat(uomDistributedUsage.value)))
 {
  errorMessage = errorMessage + messagesData.distributedUsage + "\n";
  errorCount = 1;
  uomDistributedUsage.value = "";
 }

  for(j=0;j<mainRowSpan*1;j++)
  {

  }

 if (errorCount >0)
 {
  alert(errorMessage);
 	ok.checked = false;
  return false;
 }
}*/

 return true;
}