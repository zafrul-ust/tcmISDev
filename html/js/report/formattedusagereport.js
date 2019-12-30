function getListSearch()
{

    var listID = document.getElementById("listOption");
    var selecelemet = listID.selectedIndex;
    var listName = listID.options[selecelemet].text;
    listName = listName.replace(/&/gi, "%26");
    listName = listName.replace(/#/gi, "%23");
    listName = listName.replace(/\+/gi, "%2b");
    openWinGeneric("/tcmIS/iai/chemicallistsearch.do?Action=New&listId="+listID.value+"&listName="+listName,"chemicallistsearch","600","400","yes","80","80");
}

function getCasSearch()
{
    openWinGeneric("/tcmIS/iai/chemicalcassearch.do?Action=New","chemicalcassearch","600","400","yes","80","80");
}

function facilityChanged() {
  var facilityO = document.getElementById("facilityId");
  var applicationIdO = document.getElementById("applicationId");
  var selectedFacility = facilityO.value;

  for (var i = applicationIdO.length; i > 0;i--) {
    applicationIdO[i] = null;
  }

  if (facilityO.value == "All")
  {
   setApplication(0,"All","")
  }
  else if (facilityO.value == "My Facilities")
  {
    setApplication(0,"All","")
    setApplication(1,"My Work Areas","My Work Areas")
  }
  else
  {
  showApplicationLinks(selectedFacility);
  }
  applicationIdO.selectedIndex = 0;
}

function showApplicationLinks(selectedFacility)
{
  var applicationIdArray = new Array();
  applicationIdArray = altApplication[selectedFacility];

  var applicationDescArray = new Array();
  applicationDescArray = altApplicationDesc[selectedFacility];

  if(applicationIdArray.length == 0) {
    setApplication(0,"All","")
  }

  for (var i=0; i < applicationIdArray.length; i++) {
    setApplication(i,applicationDescArray[i],applicationIdArray[i])
  }
}

function setApplication(href,text,id) {
  var optionName = new Option(text, id, false, false)
  var applicationIdO = document.getElementById("applicationId");
  applicationIdO[href] = optionName;
}

function addItem(obj,value,text) {
     index = obj.length;
     obj.options[index]=new Option(text,value);
     obj.options[index].selected = true;
}

function removeItem(obj) {
     if(obj.length <= 0)
          return;
     var index = obj.selectedIndex;
     if(index != -1)
          obj.options[index]=null;
}

function transferItem(objFrom,objTo) {
     if(objFrom.length <= 0) {
        alert("There are no item to move");
     }else {
        //make at least one item is selected from table
        var count = 0;
        for (j=0;j<objFrom.length;j++) {
           if(objFrom.options[j].selected){
              count++;
           }
        }
        if (count > 0) {
           for(i=0;i<objFrom.length;i++) {
              if(objFrom.options[i].selected){
                 addItem(objTo,objFrom.options[i].value,objFrom.options[i].text);
                 removeItem(objFrom,i);
                 i--;
              }
           }
        }else {
            alert("Select an item that you want to move");
        }
     }
}

//This function is called by the buttons generated above and is used to move the selected item in the listbox up or down.
function move(objMove,bDir) {
   if(objMove.length <= 0) {
      alert("There are no item to move");
      return;
   }
   var idx = objMove.selectedIndex
   if (idx==-1)
      alert("You must first select the item to reorder.")
   else {
      var nxidx = idx+( bDir? -1 : 1)
      if (nxidx<0) nxidx=objMove.length-1
      if (nxidx>=objMove.length) nxidx=0
      var oldVal = objMove[idx].value
      var oldText = objMove[idx].text
      objMove[idx].value = objMove[nxidx].value
      objMove[idx].text = objMove[nxidx].text
      objMove[nxidx].value = oldVal
      objMove[nxidx].text = oldText
      objMove.selectedIndex = nxidx
   }
}

function generateReportAudit() {
   var groupByOptionList = document.getElementById("groupByOptionList");
   selectAllItems(groupByOptionList);
   var groupByList = document.getElementById("groupByList");
   selectAllItems(groupByList);
}

function selectAllItems(obj) {
   for(i=0;i<obj.length;i++) {
      obj.options[i].selected=true;
   }
}

