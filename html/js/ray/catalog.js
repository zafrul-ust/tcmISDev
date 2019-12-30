var selectedrow=null;

function doexcelpopup()
{
 excelfileurl = "/tcmIS/common/viewexcel.jsp";
 openWinGenericExcel(excelfileurl,"show_excel_report_file","610","600","yes");
}

function printLabels() {
  openWinGeneric('/tcmIS/common/loadingpleasewait.jsp','_newPrintLabels','650','600','yes');
  document.genericForm.target='_newPrintLabels';
  document.genericForm.action='label.do?action=printLabels';
  document.genericForm.submit();
  //var a = window.setTimeout("document.genericForm.submit();",500);
}

function generateExcel() {
  openWinGeneric('/tcmIS/common/loadingpleasewait.jsp','_newgenerateExcel','650','600','yes');
  document.genericForm.target='_newgenerateExcel';
  document.genericForm.action='catalog.do?createExcel=createExcel';
  document.genericForm.submit();
  //var a = window.setTimeout("document.genericForm.submit();",500);
}

function checkLabelQuantity(rowNumber)
{
 var labelQty = document.getElementById("labelQty"+rowNumber+"");

 if ( !(isInteger(labelQty.value)) )
 {
  alert(messagesData.validquantity);
  labelQty.value = "";
  return false;
 }
}

function checkAllCheckBoxes(checkBoxName)
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

function doprintpopup(printurl)
{
 openWinGeneric(printurl,"show_print_file","800","500","yes");
}

function catchevent(rowid)
{
    var selectedRow = document.getElementById("rowId"+rowid+"");
	 var selectedRowClass = document.getElementById("colorClass"+rowid+"");
	 selectedRow.className = "selected"+selectedRowClass.value+"";

	 try
	 {
      var grandChildRowsSize = document.getElementById("grandChildRowsSize"+rowid+"0");
      for(k=1;k<grandChildRowsSize.value;k++)
      {
       //alert("grandchildRowId"+rowid+"0"+(k)+"");
	    var grnadChildRowId = document.getElementById("grnadChildRowId"+rowid+"0"+(k)+"");
	    grnadChildRowId.className = "selected"+selectedRowClass.value+"";
       }
    }
    catch (ex)
    {

    }

	 var childRowsSize = document.getElementById("childRowsSize"+rowid+"");
    for(j=1;j<childRowsSize.value;j++)
    {
	   //alert("childRowId"+rowid+""+(j)+"");
	   var selectedchildRow = document.getElementById("childRowId"+rowid+""+(j)+"");
	   selectedchildRow.className = "selected"+selectedRowClass.value+"";

	   var grandChildRowsSize = document.getElementById("grandChildRowsSize"+rowid+""+(j)+"");
      for(k=1;k<grandChildRowsSize.value;k++)
      {
      //alert("grandchildRowId"+rowid+""+(j)+""+(k)+"");
	   var grnadChildRowId = document.getElementById("grnadChildRowId"+rowid+""+(j)+""+(k)+"");
	   grnadChildRowId.className = "selected"+selectedRowClass.value+"";

      }
    }

	 if (selectedrow != null && rowid != selectedrow)
	 {
		var previouslySelectedRow = document.getElementById("rowId"+selectedrow+"");
	   var previouslySelectedRowClass = document.getElementById("colorClass"+selectedrow+"");
	   previouslySelectedRow.className = ""+previouslySelectedRowClass.value+"";

      try
	   {
       var previouslygrandChildRowsSize = document.getElementById("grandChildRowsSize"+selectedrow+"0");
       for(k=1;k<previouslygrandChildRowsSize.value;k++)
       {
        //alert("grandchildRowId"+rowid+"0"+(k)+"");
	     var previouslygrnadChildRowId = document.getElementById("grnadChildRowId"+selectedrow+"0"+(k)+"");
	     previouslygrnadChildRowId.className = ""+previouslySelectedRowClass.value+"";
        }
      }
      catch (ex)
      {

      }

		//alert(previouslySelectedRowClass.value);
      var previouslychildRowsSize = document.getElementById("childRowsSize"+selectedrow+"");
      for(j=1;j<previouslychildRowsSize.value;j++)
      {
	    //alert("childRowId"+selectedrow+""+(j)+"");
	    var previouslyselectedchildRow = document.getElementById("childRowId"+selectedrow+""+(j)+"");
	    previouslyselectedchildRow.className = ""+previouslySelectedRowClass.value+"";

	    var previouslygrandChildRowsSize = document.getElementById("grandChildRowsSize"+selectedrow+""+(j)+"");
       for(k=1;k<previouslygrandChildRowsSize.value;k++)
       {
       //alert("grandchildRowId"+selectedrow+""+(j)+""+(k)+"");
	    var previouslygrnadChildRowId = document.getElementById("grnadChildRowId"+selectedrow+""+(j)+""+(k)+"");
	    previouslygrnadChildRowId.className = ""+previouslySelectedRowClass.value+"";
       }
     }
	 }
	 selectedrow =rowid;
	 //showRightClickMenu();
}

function facilityChanged() {
  var facilityO = document.getElementById("facilityId");
  var applicationIdO = document.getElementById("applicationId");
  var selectedFacility = facilityO.value;

  for (var i = applicationIdO.length; i > 0;i--) {
    applicationIdO[i] = null;
  }

  showApplicationLinks(selectedFacility);

  applicationIdO.selectedIndex = 0;
}

function showApplicationLinks(selectedFacility)
{
  var applicationIdArray = new Array();
  applicationIdArray = altApplication[selectedFacility];

  var applicationDescArray = new Array();
  applicationDescArray = altApplicationDesc[selectedFacility];

  setApplication(0,messagesData.pleaseselect,"All")
  var j = 0;
  for (var i=0; i < applicationIdArray.length; i++) {
   j++;
    setApplication(j,applicationDescArray[i],applicationIdArray[i])
  }
}

function setApplication(href,text,id) {
  var optionName = new Option(text, id, false, false)
  var applicationIdO = document.getElementById("applicationId");
  applicationIdO[href] = optionName;
}