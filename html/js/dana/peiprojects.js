function addSavingsLine()
{
 mytable = document.getElementById("savingstable");
 var allTRs = mytable.getElementsByTagName("tr");
// alert(allTRs.length);
 var rownum = (allTRs.length)*1 - 3;
 var savingsCount = document.getElementById("savingsCount");
 savingsCount.value = (rownum*1+1);

  var Color ="white";
  if (rownum%2==0)
  {
      Color ="blue";
  }
  else
  {
      Color ="white";
  }

  // creates an element whose tag name is TBODY
  mytablebody = document.createElement("TBODY");
  // creating all cells
  // creates an element whose tag name is TR
      mycurrent_row=document.createElement("TR");
      mycurrent_row.id = "savingsRowId"+rownum;
      mycurrent_row.className = Color;

          // creates an element whose tag name is TD
          mycurrent_cell=document.createElement("TD");
          mycurrent_cell.id = "linenumberammn"+rownum;
          mycurrent_cell.setAttribute("width",'5%');
          mycurrent_cell.innerHTML = "<INPUT TYPE=\"text\" name=\"peiProjectSavingsBean["+rownum+"].periodName\" ID=\"periodName"+rownum+"\" value=\"\" size=\"6\" maxlength=\"30\" Class=\"DETAILS\"><INPUT TYPE=\"hidden\" name=\"peiProjectSavingsBean["+rownum+"].lineStatus\" ID=\"lineStatus"+rownum+"\" value=\"New\"><INPUT TYPE=\"hidden\" name=\"peiProjectSavingsBean["+rownum+"].periodId\" ID=\"periodId"+rownum+"\" value=\""+((rownum*1)+1)+"\">";
          // appends the cell TD into the row TR
          mycurrent_row.appendChild(mycurrent_cell);

          // creates an element whose tag name is TD
          mycurrent_cell=document.createElement("TD");
          mycurrent_cell.id = "cell2"+rownum;
          mycurrent_cell.setAttribute("width",'5%');
          //mycurrent_cell.setAttribute("align",'left');
          mycurrent_cell.innerHTML = "<INPUT TYPE=\"text\" name=\"peiProjectSavingsBean["+rownum+"].projectedPeriodSavings\" ID=\"projectedPeriodSavings"+rownum+"\" onChange=\"updateProjectedSavingsTotals("+rownum+")\" value=\"\" size=\"6\" maxlength=\"30\" Class=\"DETAILS\">";
          // appends the cell TD into the row TR
          mycurrent_row.appendChild(mycurrent_cell);

          // creates an element whose tag name is TD
          mycurrent_cell=document.createElement("TD");
          mycurrent_cell.id = "itemtypecell"+rownum;
          mycurrent_cell.setAttribute("width",'5%');
          mycurrent_cell.innerHTML = "<INPUT TYPE=\"text\" name=\"peiProjectSavingsBean["+rownum+"].actualPeriodSavings\" ID=\"actualPeriodSavings"+rownum+"\" onChange=\"updateActualSavingsTotals("+rownum+")\" value=\"\" size=\"6\" maxlength=\"30\" Class=\"DETAILS\">";
          // appends the cell TD into the row TR
          mycurrent_row.appendChild(mycurrent_cell);

          /*// creates an element whose tag name is TD
          mycurrent_cell=document.createElement("TD");
          mycurrent_cell.id = "cell5"+rownum;
          mycurrent_cell.setAttribute("width",'5%');
          mycurrent_cell.innerHTML = "<SELECT name=\"peiProjectSavingsBean["+rownum+"].currencyId\" ID=\"currencyId"+rownum+"\" CLASS=\"DETAILS\"><OPTION value=\"USD\" selected>United States Dollars</option><OPTION value=\"MXN\">Mexico Pesos</option><OPTION value=\"CAD\">Canada Dollars</option><OPTION value=\"ILS\">Israel New Shekels</option><OPTION value=\"ARS\">Argentina Pesos</option><OPTION value=\"EUR\">Euros</option><OPTION value=\"GBP\">United Kingdom Pounds</option><OPTION value=\"CNY\">China Yuan Renminbi</option><OPTION value=\"TWD\">Taiwan New Dollars</option><OPTION value=\"SGD\">Singapore Dollars</option><OPTION value=\"KRW\">South Korea Won</option><OPTION value=\"CHF\">Switzerland Francs</option></SELECT>";
          // appends the cell TD into the row TR
          mycurrent_row.appendChild(mycurrent_cell);*/

      // appends the row TR into TBODY
      //mytablebody.appendChild(mycurrent_row);

		if (rownum == 0)
		{
	    var savingsRowHeader = document.getElementById("savingsRowHeader");
	    savingsRowHeader.appendChild(mycurrent_row);
		}
		else
		{
 		 var savingsRowId = document.getElementById("savingsRowId"+((rownum*1)-1)+"");
	    savingsRowId.appendChild(mycurrent_row);
	   }

  // appends TBODY into TABLE
//  mytable.insertBefore(mytablebody,mytable.lastChild.nodeValue );
//  mytable.appendChild(mytablebody);
}


function addDocumentLine()
{
 mytable = document.getElementById("documentstable");
 var allTRs = mytable.getElementsByTagName("tr");
 //alert(allTRs.length);
 var rownum = (allTRs.length)*1 - 1;
 var documentCount = document.getElementById("documentCount");
 documentCount.value = (rownum*1+1);

  var Color ="white";
  if (rownum%2==0)
  {
      Color ="blue";
  }
  else
  {
      Color ="white";
  }

  // creates an element whose tag name is TBODY
  mytablebody = document.createElement("TBODY");
  // creating all cells
  // creates an element whose tag name is TR
      mycurrent_row=document.createElement("TR");
      mycurrent_row.id = "documentRowId"+rownum;
      mycurrent_row.className = Color;

          // creates an element whose tag name is TD
          mycurrent_cell=document.createElement("TD");
          mycurrent_cell.id = "documentUrl"+rownum;
          mycurrent_cell.setAttribute("width",'5%');
          mycurrent_cell.innerHTML = "";
          // appends the cell TD into the row TR
          mycurrent_row.appendChild(mycurrent_cell);

          // creates an element whose tag name is TD
          mycurrent_cell=document.createElement("TD");
          mycurrent_cell.id = "documentDelete"+rownum;
          mycurrent_cell.setAttribute("width",'5%');
          //mycurrent_cell.setAttribute("align",'left');
          mycurrent_cell.innerHTML = "";
          // appends the cell TD into the row TR
          mycurrent_row.appendChild(mycurrent_cell);

       // appends the row TR into TBODY
      mytablebody.appendChild(mycurrent_row);
      mytable.appendChild(mytablebody);
}

function transferMultipleItems(objFrom,objTo) {
     if(objFrom.length <= 0)
          return;

     for(i=0;i<objFrom.length;i++) {
          if(objFrom.options[i].selected){
               addItem(objTo,objFrom.options[i].value,objFrom.options[i].text);
               removeItem(objFrom,i);
               i--;
          }
     }
}

function addItem(obj,value,text) {
     index = obj.length;
     obj.options[index]=new Option(text,value);
     //obj.options[index].selected = true;
}

function selectAllOptionItem(obj) {
     if(obj.length <= 0)
          return;
     var len = obj.length;
     for(i=0;i<len;i++)
     {
      obj.options[i].selected = true;
     }
}

function removeItem(obj) {
     if(obj.length <= 0)
          return;
     var index = obj.selectedIndex;
     if(index != -1)
          obj.options[index]=null;
}

function showCurrencyChangeMessage()
{
	alert("Please make sure the savings entered below match the currency you picked.");
}

function showProjectDetails(projectId)
{
loc = "showpeiproject.do?projectId=" + projectId;
//openWinGeneric(loc,"showProjectDetails"+projectId+"");
window.open(loc,"showProjectDetails"+projectId+"");
}

function addProjectDocument()
{
projectId = document.getElementById("projectId");
companyId = document.getElementById("companyId");
loc = "showaddprojectdocument.do?projectId=" + projectId.value +"&companyId="+companyId.value;

openWinGeneric(loc,"addprojectdocument11","650","250","yes");
}

function getpersonnelID()
{
 loc = "searchpersonnel.do?displayElementId=customerContactManager&valueElementId=customerContactId";
 openWinGeneric(loc,"SupplierID","650","455","yes","50","50")
}

function getProjectManager()
{
 loc = "searchpersonnel.do?displayElementId=projectManager&valueElementId=projectManagerId";
 openWinGeneric(loc,"SupplierID","650","455","yes","50","50")
}

function printPdf()
{
 projectId = document.getElementById("projectId");
 loc = "showpeiproject.do?submitPrint=Yes&printProjectIdList=" + projectId.value;

 openWinGeneric(loc,"printPdffile","800","600","yes","50","50")
}

function startNewProject()
{
	window.open("showpeiproject.do?submitNew=New Project");
}

function printprojectlist()
{
var rowCount = document.getElementById("rowCount");
var printProjectIdList = "";
var checkedCount = 0;

for(j=0;j<rowCount.value*1;j++)
{
  var projectId = document.getElementById("projectId"+j+"");
  var printPdf = document.getElementById("printPdf"+j+"");
  //alert("projectId "+projectId.value+" printPdf "+printPdf.checked+"");

  if (printPdf.checked)
  {
   if (checkedCount >0)
   {
    printProjectIdList += ",";
   }
   printProjectIdList +=projectId.value;
   checkedCount++
  }
}

loc = "peiprojectlist.do?submitPrint=Yes&printProjectIdList="+printProjectIdList;
openWinGeneric(loc,"showprintprojectlist","800","600","yes")
}

function doexcelpopup()
{
 excelfileurl = "/tcmIS/common/viewexcel.jsp";

 openWinGenericExcel(excelfileurl,"show_excel_report_file","610","600","yes");
}

function checkForNumber(elementToCheck)
{
 var elementToCheck  =  document.getElementById(""+elementToCheck+"");
 if ( !(isSignedFloat(elementToCheck.value)) )
 {
  alert("Please enter a valid number");
  elementToCheck.value = "";
  return;
 }
}

function updateProjectedSavingsTotals(rownumber)
{
var projectedPeriodSavings  =  document.getElementById("projectedPeriodSavings"+rownumber+"");
if ( !(isSignedFloat(projectedPeriodSavings.value)) )
{
 alert("Please enter a valid number");
 projectedPeriodSavings.value = "";
 return;
}

var savingsCount  =  document.getElementById("savingsCount");
var totalProjectedPeriodSavings = 0;

for(j=0;j<savingsCount.value*1;j++)
{
  var projectedPeriodSavings = document.getElementById("projectedPeriodSavings"+j+"");

  if (projectedPeriodSavings.value.trim().length > 0)
  {
	 totalProjectedPeriodSavings += projectedPeriodSavings.value*1;
  }
}

var totalProjectedPeriodSavingsCell  =  document.getElementById("totalProjectedPeriodSavings");
totalProjectedPeriodSavingsCell.innerHTML = totalProjectedPeriodSavings;
}

function updateActualSavingsTotals(rownumber)
{
var actualPeriodSavings  =  document.getElementById("actualPeriodSavings"+rownumber+"");
if ( !(isSignedFloat(actualPeriodSavings.value)) )
{
 alert("Please enter a valid number");
 actualPeriodSavings.value = "";
 return;
}

var savingsCount  =  document.getElementById("savingsCount");
var totalActualPeriodSavings = 0;

for(j=0;j<savingsCount.value*1;j++)
{
  var actualPeriodSavings = document.getElementById("actualPeriodSavings"+j+"");

  if (actualPeriodSavings.value.trim().length > 0)
  {
         totalActualPeriodSavings += actualPeriodSavings.value*1;
  }
}

var totalActualPeriodSavingsCell  =  document.getElementById("totalActualPeriodSavings");
totalActualPeriodSavingsCell.innerHTML =totalActualPeriodSavings;
}

function search()
{
    return true;
}

function newProject()
{
    return true;
}

function saveAsNewProject()
{
	 selectAllOptionItem(document.getElementById("keywordsCollectionSelect"));
    return true;
}

function updateProject()
{
	 selectAllOptionItem(document.getElementById("keywordsCollectionSelect"));
    return true;
}

function projectCompanychanged()
{
 var originalCompanyId = document.getElementById("originalCompanyId");
 var companyId = document.getElementById("companyId");

 if (originalCompanyId.value.trim().length > 0)
 {
  var submitUpdate = document.getElementById("submitUpdate");
  if (originalCompanyId.value == companyId.value)
  {
    submitUpdate.style["display"] = "";
  }
  else
  {
    submitUpdate.style["display"] = "none";
  }
 }
 companychanged();
}

function companychanged()
{
	var companyId = document.getElementById("companyId");

	var facilityId = document.getElementById("facilityId");
	var selectedCompanyId = companyId.value;

	for (var i = facilityId.length; i > 0;i--)
   {
      facilityId[i] = null;
   }

   if (companyId.value == "All")
   {
    setOption(0,"All","")
   }
   else
   {
    showFacilities(selectedCompanyId);
   }
	facilityId.selectedIndex = 0;
}

function showFacilities(selectedCompanyId)
{
    var facilityId = new Array();
    facilityId = facilityIdArray[selectedCompanyId];

	 var facilityName = new Array();
    facilityName = facilityNameArray[selectedCompanyId];

    if(facilityId.length == 0)
    {
      setOption(0,"All","")
    }

    for (var i=0; i < facilityId.length; i++)
    {
        setOption(i,facilityName[i],facilityId[i])
    }
}

function setOption(href,text,id)
{
    var optionName = new Option(text, id, false, false)
    var facilityId = document.getElementById("facilityId");
	 facilityId[href] = optionName;
}

function cancel()
{
   closeWindow();
}