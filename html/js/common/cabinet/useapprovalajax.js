var workAreaManagementData = new Array();
var totalRows = 0;
var endingRowNumber = 0;
var rowCount = 0;
var headerString = "";
var isItmanagedWorkArea = "";
var useApprovalStatusPermission = "";
var useApprovalPermission = "";
var grilLoadedCount = 0;
var clearGridCount = 0;
var dockLocationId = new Array();
var dockDesc = new Array();
var lastSearchText="";
var lastShowActiveOnly="";
var lastShowOnlyWithLimits="";
var inputChangedCount=0;
var checkImageClicked=false;
var showErrorMessage = false;

var IFrameObj; // our IFrame object
function callToServer(URL) {
//alert(URL);
  if (!document.createElement) {return true};
var IFrameDoc;
  if (!IFrameObj && document.createElement) {
    // create the IFrame and assign a reference to the
    // object to our global variable IFrameObj.
    // this will only happen the first time
    // callToServer() is called
   try {
      var tempIFrame=document.createElement('iframe');
      tempIFrame.setAttribute('id','RSIFrame');
      tempIFrame.setAttribute('name','RSIFrame');
      tempIFrame.setAttribute('src','/blank.html');
      tempIFrame.style.border='0px';
      tempIFrame.style.width='0px';
      tempIFrame.style.height='0px';
      IFrameObj = document.body.appendChild(tempIFrame);

      if (document.frames) {
        // this is for IE5 Mac, because it will only
        // allow access to the document object
        // of the IFrame if we access it through
        // the document.frames array
        IFrameObj = document.frames['RSIFrame'];
      }
    } catch(exception) {
      // This is for IE5 PC, which does not allow dynamic creation
      // and manipulation of an iframe object. Instead, we'll fake
      // it up by creating our own objects.
      iframeHTML='\<iframe id="RSIFrame" src="/blank.html" name="RSIFrame" style="';
      iframeHTML+='border:0px;';
      iframeHTML+='width:0px;';
      iframeHTML+='height:0px;';
      iframeHTML+='"><\/iframe>';
      document.body.innerHTML+=iframeHTML;
      IFrameObj = new Object();
      IFrameObj.document = new Object();
      IFrameObj.document.location = new Object();
      IFrameObj.document.location.iframe = document.getElementById('RSIFrame');
      IFrameObj.document.location.replace = function(location) {
     	this.iframe.src = location;
      }
    }
  }

  if (navigator.userAgent.indexOf('Gecko') !=-1 && !IFrameObj.contentDocument) {
    // we have to give NS6 a fraction of a second
    // to recognize the new IFrame
    setTimeout('callToServer()',10);
    return false;
  }

  if (IFrameObj.contentDocument) {
    // For NS6
    IFrameDoc = IFrameObj.contentDocument;
  } else if (IFrameObj.contentWindow) {
    // For IE5.5 and IE6
    IFrameDoc = IFrameObj.contentWindow.document;
  } else if (IFrameObj.document) {
    // For IE5
    IFrameDoc = IFrameObj.document;
  } else {
    return true;
  }
  IFrameDoc.location.replace(URL);
  setTimeout('window.status="";',1000);
}

/*function makeAllRowsActive()
{
 if (useApprovalPermission == "Yes")
 {
 var makeAllActive  =  document.getElementById("makeAllActive");
 if (makeAllActive.checked)
 {
  for (var i = 0; i < totalRows; i++)
  {
   workAreaManagementData[i].mwApprovalId = "Y";

   var displayValue = mygrid.getUserData((i*1),"display");
   if (displayValue == "text")
   {
    var c  =  document.getElementById("c_"+i+"_7");
    c.innerHTML = "Yes";
   }
   else
   {
   var ok  =  document.getElementById("ok"+i+"");
   ok.checked=true;
   var useApprovalChanged  =  document.getElementById("useApprovalChanged"+i+"");
   useApprovalChanged.value = "Y";
   }
  }
 }
 else
 {
  for (var i = 0; i < totalRows; i++)
  {
   workAreaManagementData[i].mwApprovalId = "";

   var displayValue = mygrid.getUserData((i*1),"display");
   if (displayValue == "text")
   {
    var c  =  document.getElementById("c_"+i+"_7");
    c.innerHTML = "&nbsp;";
   }
   else
   {
   var ok  =  document.getElementById("ok"+i+"");
   ok.checked=false;
   var useApprovalChanged  =  document.getElementById("useApprovalChanged"+i+"");
   useApprovalChanged.value = "";
   }
  }
 }
 }
}*/

function useApprovalChanged(rowNum)
{
 var useApprovalChanged  =  document.getElementById("useApprovalChanged"+rowNum+"");
 useApprovalChanged.value = "Y";
 checkInput(rowNum);
 /*var ok  =  document.getElementById("ok"+rowNum+"");
 if (!ok.checked)
 {
  ok.checked = true;
 }*/
 inputChangedCount++;
}

function automatedFeedChanged(rowNum)
{
 var automatedFeedChanged  =  document.getElementById("automatedFeedChanged"+rowNum+"");
 automatedFeedChanged.value = "Y";
 inputChangedCount++;
}

function addEvent(obj, evType, fn, useCapture){
  if (obj.addEventListener){
    obj.addEventListener(evType, fn, useCapture);
    //return true;
  } else if (obj.attachEvent){
    var r = obj.attachEvent("on"+evType, fn);
    //return r;
  } else {
    alert("Handler could not be attached");
  }
}

function checkImageClicked()
{
 checkImageClicked = true;
}

useApprovalRowSelected = function()
{
 rowid = mygrid.getSelectedId();
 selectedCell = mygrid.getSelectedCellIndex();
 var displayValue = mygrid.getUserData((rowid*1),"display");
 if (displayValue == "text" && useApprovalPermission == "Yes" )
 {
    var invisibleElements  =  document.getElementById("invisibleElements");
    invisibleElements.innerHTML +="<input type=\"hidden\" name=\"useApprovalStatusViewBean["+rowid+"].rowNumber\" id=\"rowNumber"+rowid+"\" value=\""+rowCount+"\">";
    invisibleElements.innerHTML +="<input type=\"hidden\" name=\"useApprovalStatusViewBean["+rowid+"].facPartNo\" id=\"facPartNo"+rowid+"\" value=\""+workAreaManagementData[rowid].facPartNo+"\">";
    invisibleElements.innerHTML +="<input type=\"hidden\" name=\"useApprovalStatusViewBean["+rowid+"].useApprovalChanged\" id=\"useApprovalChanged"+rowid+"\" value=\"\">";
    invisibleElements.innerHTML +="<input type=\"hidden\" name=\"useApprovalStatusViewBean["+rowid+"].automatedFeedChanged\" id=\"automatedFeedChanged"+rowid+"\" value=\"\">";
	 invisibleElements.innerHTML +="<input type=\"hidden\" name=\"useApprovalStatusViewBean["+rowid+"].companyId\" id=\"companyId"+rowid+"\" value=\""+workAreaManagementData[rowid].companyId+"\">";
	 invisibleElements.innerHTML +="<input type=\"hidden\" name=\"useApprovalStatusViewBean["+rowid+"].catalogCompanyId\" id=\"catalogCompanyId"+rowid+"\" value=\""+workAreaManagementData[rowid].catalogCompanyId+"\">";
   var invisibleColorClass ="";
   if(rowid%2==1){invisibleColorClass =" invisibleHeadWhite ";}
   else {invisibleColorClass =" invisibleHeadBlue ";}

 // workAreaManagementData[rowid]
 var cellText = "";
 for(var index=0;index<mygrid.hdr.rows[0].cells.length;index++){
 var c  =  document.getElementById("c_"+rowid+"_"+index+"");

  switch(index)
  {
   case 7:
     var checked = "";

     if (workAreaManagementData[rowid].mwApprovalId.length > 0 ) {checked="checked";}
     if (workAreaManagementData[rowid].mwApprovalId.length > 0 && checkImageClicked ) {checked="";}
     if (workAreaManagementData[rowid].mwApprovalId.length == 0 && checkImageClicked ) {checked="checked";}
     checkImageClicked = false;
     c.innerHTML = "<input type=\"checkbox\" name=\"useApprovalStatusViewBean["+rowid+"].ok\" ID=\"ok"+rowid+"\" class=\"radioBtns\" value=\""+rowid+"\" "+checked+" onclick=\"checkInput('"+rowid+"')\">";
    break
   case 8:
    c.innerHTML = "<input type=\"text\" class=\"inputBox\" name=\"useApprovalStatusViewBean["+rowid+"].mwLimitQuantityPeriod1\" ID=\"mwLimitQuantityPeriod1"+rowid+"\" value=\""+workAreaManagementData[rowid].mwLimitQuantityPeriod1+"\" size=\"2\" maxlength=\"10\" onchange=\"useApprovalChanged("+rowid+")\">every ";
    if (workAreaManagementData[rowid].mwDaysPeriod1.trim().length ==0)
    {
     c.innerHTML += "<input type=\"text\" class=\"inputBox\" name=\"useApprovalStatusViewBean["+rowid+"].mwDaysPeriod1\" ID=\"mwDaysPeriod1"+rowid+"\" value=\"7\" size=\"2\" maxlength=\"10\" onchange=\"useApprovalChanged("+rowid+")\">days";
    }
    else
    {
     c.innerHTML += "<input type=\"text\" class=\"inputBox\" name=\"useApprovalStatusViewBean["+rowid+"].mwDaysPeriod1\" ID=\"mwDaysPeriod1"+rowid+"\" value=\""+workAreaManagementData[rowid].mwDaysPeriod1+"\" size=\"2\" maxlength=\"10\" onchange=\"useApprovalChanged("+rowid+")\">days";
    }
    break
   case 9:
    c.innerHTML = "<input type=\"text\" class=\"inputBox\" name=\"useApprovalStatusViewBean["+rowid+"].mwLimitQuantityPeriod2\" ID=\"mwLimitQuantityPeriod2"+rowid+"\" value=\""+workAreaManagementData[rowid].mwLimitQuantityPeriod2+"\" size=\"2\" maxlength=\"10\" onchange=\"useApprovalChanged("+rowid+")\">every ";
    if (workAreaManagementData[rowid].mwDaysPeriod2.trim().length ==0)
    {
     c.innerHTML += "<input type=\"text\" class=\"inputBox\" name=\"useApprovalStatusViewBean["+rowid+"].mwDaysPeriod2\" ID=\"mwDaysPeriod2"+rowid+"\" value=\"30\" size=\"2\" maxlength=\"10\" onchange=\"useApprovalChanged("+rowid+")\">days";
    }
    else
    {
     c.innerHTML += "<input type=\"text\" class=\"inputBox\" name=\"useApprovalStatusViewBean["+rowid+"].mwDaysPeriod2\" ID=\"mwDaysPeriod2"+rowid+"\" value=\""+workAreaManagementData[rowid].mwDaysPeriod2+"\" size=\"2\" maxlength=\"10\" onchange=\"useApprovalChanged("+rowid+")\">days";
    }
    break
   case 10:
    obj = document.createElement(_isKHTML?"INPUT":"TEXTAREA");
    obj.maxLength = "15";
    obj.style.width = "100%";
    //obj.style.height ="20px";
    obj.style.height =(c.offsetHeight-(mygrid.multiLine?6:5))+"px";
    //obj.style.border = "2px";
    //obj.style.margin = "0px";
    //obj.style.padding = "0px";
    obj.style.overflow = "auto";
    obj.wrap = "soft";
    obj.style.textAlign = c.align;
    obj.className="inputBox";
    //obj.value = workAreaManagementData[rowid].mwProcessDesc;
    obj.value = c.childNodes[0].innerHTML._dhx_trim().replaceBr();
    obj.name="useApprovalStatusViewBean["+rowid+"].mwProcessDesc";
    obj.id="mwProcessDesc"+rowid+"";
   // obj.onclick= "useApprovalChanged("+rowid+")";
   // obj.attachEvent("onclick", useApprovalChanged("+rowid+"));
    //obj.addEventListener("onclick",useApprovalChanged,true);
    c.innerHTML = "";
    c.appendChild(obj);
    //c.innerHTML = "<input type=\"text\" class=\"inputBox\" name=\"useApprovalStatusViewBean["+rowid+"].mwProcessDesc\" ID=\"mwProcessDesc"+rowid+"\" value=\""+workAreaManagementData[rowid].mwProcessDesc+"\" maxlength=\"15\" onchange=\"useApprovalChanged("+rowid+")\" style=\"overflow:hidden;\">";
    break
   case 11:
    c.innerHTML = "<input type=\"text\" class=\"inputBox\" name=\"useApprovalStatusViewBean["+rowid+"].mwOrderQuantity\" ID=\"mwOrderQuantity"+rowid+"\" value=\""+workAreaManagementData[rowid].mwOrderQuantity+"\" size=\"4\" maxlength=\"10\" onchange=\"useApprovalChanged("+rowid+")\">";
    break
   case 13:
    c.innerHTML = "<input type=\"text\" class=\"inputBox\" name=\"useApprovalStatusViewBean["+rowid+"].mwEstimateOrderQuantityPrd\" ID=\"mwEstimateOrderQuantityPrd"+rowid+"\" value=\""+workAreaManagementData[rowid].mwEstimateOrderQuantityPrd+"\" size=\"4\" maxlength=\"10\" onchange=\"useApprovalChanged("+rowid+")\"> days";
    break
   case 14:
    var cellInnerHTML = "<select name=\"useApprovalStatusViewBean["+rowid+"].mwOrderQuantityRule\" ID=\"mwOrderQuantityRule"+rowid+"\" class=\"selectBox\" onchange=\"useApprovalChanged("+rowid+")\">";
    cellInnerHTML += "<option value=\"\">Please Select</option>";

     try
     {
      for (var i=0; i < orderQuantityRule.length; i++)
      {
       var selected = "";
       if (orderQuantityRule[i] == workAreaManagementData[rowid].mwOrderQuantityRule) {selected = "selected";}
       cellInnerHTML += "<option value=\""+orderQuantityRule[i]+"\" "+selected+">"+orderQuantityRuleDesc[i]+"</option>";
     }
    }
    catch (ex)
    {
    }
    cellInnerHTML += "</select>";
    c.innerHTML = cellInnerHTML;
    break
   case 15:
    c.innerHTML = "<input type=\"text\" class=\"inputBox\" name=\"useApprovalStatusViewBean["+rowid+"].customerDeliverTo\" ID=\"customerDeliverTo"+rowid+"\" value=\""+workAreaManagementData[rowid].customerDeliverTo+"\" size=\"8\" maxlength=\"10\" onchange=\"automatedFeedChanged("+rowid+")\">";
    break
   case 16:
    var cellInnerHTML = "<select name=\"useApprovalStatusViewBean["+rowid+"].dockLocationId\" ID=\"dockLocationId"+rowid+"\" class=\"selectBox\" onchange=\"dockLocationIdChanged("+rowid+")\">";
    if (workAreaManagementData[rowid].dockLocationId.length ==0)
    {
     cellInnerHTML += "<option value=\"\">Please Select</option>";
    }

    try
    {
     for (var i=0; i < dockLocationId.length; i++)
     {
       var selected = "";
       if (dockLocationId[i] == workAreaManagementData[rowid].dockLocationId) {selected = "selected";}
       cellInnerHTML += "<option value=\""+dockLocationId[i]+"\" "+selected+">"+dockDesc[i]+"</option>";
     }
    }
    catch (ex)
    {
    }
    cellInnerHTML += "</select>";
    c.innerHTML = cellInnerHTML;
    break
   case 17:
    c.innerHTML = "<input type=\"text\" class=\"inputBox\" name=\"useApprovalStatusViewBean["+rowid+"].deliveryPointBarcode\" ID=\"deliveryPointBarcode"+rowid+"\" value=\""+workAreaManagementData[rowid].deliveryPointBarcode+"\" size=\"8\" maxlength=\"10\" onchange=\"automatedFeedChanged("+rowid+")\">";
    break
   case 18:
    var cellInnerHTML = "<select name=\"useApprovalStatusViewBean["+rowid+"].dockDeliveryPoint\" ID=\"dockDeliveryPoint"+rowid+"\" class=\"selectBox\" onchange=\"automatedFeedChanged("+rowid+")\">";

    /*cellInnerHTML +="<option value=\"\">Please Select</option>";
    try
    {
     for (var i=0; i < dockLocationId.length; i++)
     {
       var selected = "";
       if (dockLocationId[i] == workAreaManagementData[rowid].dockDeliveryPoint) {selected = "selected";}
       cellInnerHTML += "<option value=\""+dockLocationId[i]+"\" "+selected+">"+dockDesc[i]+"</option>";
     }
    }
    catch (ex)
    {
    }*/
/*  <c:forEach var="deliveryPointObjBean" items="${deliveryPointObjBeanJspCollection}" varStatus="dockDeliveryPointStatus">
  <c:set var="currentDockDeliveryPoint" value='${dockDeliveryPointStatus.current.deliveryPoint}'/>
  <c:choose>
   <c:when test="${currentDockDeliveryPoint == selectedDockDeliveryPoint}">
    <option value="<c:out value="${currentDockDeliveryPoint}"/>" selected><c:out value="${dockDeliveryPointStatus.current.deliveryPointDesc}"/></option>
   </c:when>
   <c:otherwise>
    <option value="<c:out value="${currentDockDeliveryPoint}"/>"><c:out value="${dockDeliveryPointStatus.current.deliveryPointDesc}"/></option>
   </c:otherwise>
  </c:choose>
  </c:forEach>
*/
    cellInnerHTML += "</select>";
    c.innerHTML = cellInnerHTML;
    showDeliveryPoints(workAreaManagementData[rowid].dockLocationId,rowid);
    var dockDeliveryPoint = document.getElementById("dockDeliveryPoint"+rowid+"");
    dockDeliveryPoint.value = workAreaManagementData[rowid].dockDeliveryPoint;
    //dockLocationIdChanged(rowid);
    break
    case 19:
     //c.innerHTML = "";
     c.innerHTML = "<input type=\"hidden\" name=\"useApprovalStatusViewBean["+rowid+"].barcodeRequester\" ID=\"barcodeRequester"+rowid+"\" value=\""+workAreaManagementData[rowid].barcodeRequester+"\" onchange=\"automatedFeedChanged("+rowid+")\">";
     c.innerHTML += "<input type=\"text\" name=\"useApprovalStatusViewBean["+rowid+"].barcodeRequesterName\" ID=\"barcodeRequesterName"+rowid+"\" value=\""+workAreaManagementData[rowid].barcodeRequesterName+"\" size=\"12\" onchange=\"automatedFeedChanged("+rowid+")\" class=\""+invisibleColorClass+"\" readonly>";
     c.innerHTML += "<br><input type=\"button\" name=\"searchpersonnellike\" value=\"...\" OnClick=\"getpersonnelID("+rowid+")\">";
     break
  }
  }
  mygrid.setUserData(rowid*1,"display","input");

  /*var makeAllActive  =  document.getElementById("makeAllActive");
  if (makeAllActive.checked)
  {
    useApprovalChanged(rowid);
  }*/

  /*Attaching event here to give the browser some time to render the element*/
  var mwProcessDesc  =  document.getElementById("mwProcessDesc"+rowid+"");
  if (_isIE)
  {
  mwProcessDesc.attachEvent("onchange",function(){useApprovalChanged(""+rowid+"")});
  }
  else
  {
   mwProcessDesc.addEventListener("change",function(){useApprovalChanged(""+rowid+"")},false);
  }
 }
}

var waitSomeTimeCout = 0;
function waitSomeTime(numberOfRows)
{
 waitSomeTimeCout++;
 if (waitSomeTimeCout ==1)
 {
  setTimeout('nextActualSetOfRows('+numberOfRows+')',10);
 }
}

function nextActualSetOfRows (numberOfRows)
{
 buildRows(endingRowNumber+1,(endingRowNumber+1+numberOfRows));
 //mygrid.selectRow(endingRowNumber,false,false);
 stopWaitDialog();
}

function nextSetOfRows(numberOfRows)
{
 //alert("nextSetOfRows "+endingRowNumber+" "+numberOfRows+" "+(totalRows-1)+" ");
 if (endingRowNumber+1 >= (totalRows-1))
 {
   return false;
 }
 else
 {
  showWaitDialog();
  waitSomeTimeCout = 0;
  waitSomeTime(numberOfRows);
  //alert("nextSetOfRows "+endingRowNumber+" "+(totalRows-1)+" ");
  return true;
 }

}

function buildRows(fromRowNumber,toRowNumber)
{
 if (toRowNumber > (totalRows-1))
 {
   toRowNumber = (totalRows-1);
 }

 //alert("fromRowNumber  "+fromRowNumber+"  toRowNumber  "+toRowNumber+"");
 for (var i = fromRowNumber; i <= toRowNumber; i++)
 {
   try
   {
    var className ="";
    if(i%2==1){className =" even ";}
    else {className =" uneven ";}

    //var invisibleElements  =  document.getElementById("invisibleElements");
    /*var invobj = document.createElement("input");
	  invobj.type="hidden"
    invobj.id= "useApprovalStatusViewBean["+rowCount+"].facPartNo";
    invobj.value = workAreaManagementData[i].facPartNo;
    invisibleElements.appendChild(invobj);
    */
    /*invisibleElements.innerHTML +="<input type=\"hidden\" name=\"useApprovalStatusViewBean["+rowCount+"].rowNumber\" id=\"rowNumber"+rowCount+"\" value=\""+rowCount+"\">";
    invisibleElements.innerHTML +="<input type=\"hidden\" name=\"useApprovalStatusViewBean["+rowCount+"].facPartNo\" id=\"facPartNo"+rowCount+"\" value=\""+workAreaManagementData[i].facPartNo+"\">";
    invisibleElements.innerHTML +="<input type=\"hidden\" name=\"useApprovalStatusViewBean["+rowCount+"].useApprovalChanged\" id=\"useApprovalChanged"+rowCount+"\" value=\"\">";
    invisibleElements.innerHTML +="<input type=\"hidden\" name=\"useApprovalStatusViewBean["+rowCount+"].automatedFeedChanged\" id=\"automatedFeedChanged"+rowCount+"\" value=\"\">";
*/
    addUseApprovalRow(rowCount,workAreaManagementData[i],-1,className);
    rowCount++;
   }
   catch(exception) {
     alert("Exception adding rownumber "+i+"");
   }
 }
 endingRowNumber = toRowNumber;
 var footer  =  document.getElementById("footer");
 footer.innerHTML= totalRows + " Records found.";
 if (totalRows > 0)
 {
  footer.innerHTML += " Showing 1-"+(endingRowNumber+1)+" rows.";
 }
}


addUseApprovalRow = function(new_id,jsonWorkAreaManagementData,ind,className){
 var r = _addUseApprovalRow(new_id,jsonWorkAreaManagementData,ind,className);
 if(typeof(mygrid.onRowAdded)=='function'){
 mygrid.onRowAdded(new_id);
}
 mygrid.setSizes();
 return r;
}

_addUseApprovalRow = function(new_id,jsonWorkAreaManagementData,ind,className){
 if(ind<0)ind=mygrid.obj.rows.length;
 //alert(ind);
 //alert("_addCatalogFullRow");
 mygrid.math_off=true;
 mygrid.math_req=false;

if((arguments.length<3)||(ind===window.undefined))
 ind = mygrid.rowsCol.length
 else{
 if(ind>mygrid.rowsCol.length)
 ind = mygrid.rowsCol.length;
}

// if(typeof(text)!='object')
// text = text.split(mygrid.delim)

 if((!mygrid.dynScroll || mygrid.dynScroll=='false' || ind<mygrid.obj.rows.length)&&((ind)||(ind==0)))
{
 if(_isKHTML)
 if(ind==mygrid.obj.rows.length){
 var r=document.createElement("TR");
 mygrid.obj.appendChild(r);
}
 else
{
 var r=document.createElement("TR");
 mygrid.obj.rows[ind].parentNode.insertBefore(r,mygrid.obj.rows[ind]);
}
 else
 if(ind==mygrid.obj.rows.length)
 var r=mygrid.obj.insertRow(ind);
 else
 var r=mygrid.obj.insertRow(ind);
}
 if(mygrid.multiLine != true)
 mygrid.obj.className+=" row60px";

 r.idd = new_id;
 r.grid = this;
 r.className+=" "+className+" ";

 mygrid.setUserData(new_id,"display","text");

 _addUseApprovalCell(0,0,jsonWorkAreaManagementData.catalogId,r);
 _addUseApprovalCell(1,1,jsonWorkAreaManagementData.facPartNo,r);
 _addUseApprovalCell(2,2,jsonWorkAreaManagementData.partDescription,r);  //jsonCatalogData.partDescription;
 _addUseApprovalCell(3,3,jsonWorkAreaManagementData.approvalStatus,r);  //jsonCatalogData.stockingMethod;
/*if (jsonWorkAreaManagementData.limitQuantityPeriod1.length > 0)
_addUseApprovalCell(4,4,""+jsonWorkAreaManagementData.limitQuantityPeriod1+" every "+jsonWorkAreaManagementData.daysPeriod1+" days",r);
else
_addUseApprovalCell(4,4,"",r);
*/
 _addUseApprovalCell(4,4,jsonWorkAreaManagementData.limitQuantityPeriod1Display,r);
/* if (jsonWorkAreaManagementData.limitQuantityPeriod2.length > 0)
_addUseApprovalCell(5,5,""+jsonWorkAreaManagementData.limitQuantityPeriod2+" every "+jsonWorkAreaManagementData.daysPeriod2+" days",r);
else
_addUseApprovalCell(5,5,"",r);*/
 _addUseApprovalCell(5,5,jsonWorkAreaManagementData.limitQuantityPeriod2Display,r);

 _addUseApprovalCell(6,6,jsonWorkAreaManagementData.processDesc.replaceBr(),r);  //jsonCatalogData.storageTemp;
 /*if (jsonWorkAreaManagementData.mwApprovalId.length > 0)
 {
  _addUseApprovalCell(7,7,"<img src=\"/images/item_chk1.gif\" border=0 alt=\"Active\" align=\"\">",r);  //Active
 }
 else
 {
  _addUseApprovalCell(7,7,"<img src=\"/images/item_chk0.gif\" border=0 alt=\"In Active\" align=\"\">",r);  //In Active
 }*/
 _addUseApprovalCell(7,7,jsonWorkAreaManagementData.active,r);
 /*if (jsonWorkAreaManagementData.mwLimitQuantityPeriod1.length > 0)
_addUseApprovalCell(8,8,""+jsonWorkAreaManagementData.mwLimitQuantityPeriod1+" every "+jsonWorkAreaManagementData.mwDaysPeriod1+" days",r);
else
_addUseApprovalCell(8,8,"",r);

 if (jsonWorkAreaManagementData.mwLimitQuantityPeriod2.length > 0)
_addUseApprovalCell(9,9,""+jsonWorkAreaManagementData.mwLimitQuantityPeriod2+" every "+jsonWorkAreaManagementData.mwDaysPeriod2+" days",r);
else
_addUseApprovalCell(9,9,"",r);
*/
 _addUseApprovalCell(8,8,jsonWorkAreaManagementData.mwLimitQuantityPeriod1Display,r);
 _addUseApprovalCell(9,9,jsonWorkAreaManagementData.mwLimitQuantityPeriod2Display,r);

_addUseApprovalCell(10,10,jsonWorkAreaManagementData.mwProcessDesc.replaceBr(),r);
_addUseApprovalCell(11,11,jsonWorkAreaManagementData.mwOrderQuantity,r);
_addUseApprovalCell(12,12,jsonWorkAreaManagementData.packaging,r);
_addUseApprovalCell(13,13,jsonWorkAreaManagementData.mwEstimateOrderQuantityPrdDisplay,r);
_addUseApprovalCell(14,14,jsonWorkAreaManagementData.mwOrderQuantityRule,r);
_addUseApprovalCell(15,15,jsonWorkAreaManagementData.customerDeliverTo,r);
_addUseApprovalCell(16,16,jsonWorkAreaManagementData.dockLocationId,r);
_addUseApprovalCell(17,17,jsonWorkAreaManagementData.deliveryPointBarcode,r);
_addUseApprovalCell(18,18,jsonWorkAreaManagementData.dockDeliveryPoint,r);
_addUseApprovalCell(19,19,jsonWorkAreaManagementData.barcodeRequesterName,r);
 mygrid.rowsAr[new_id] = r;
 mygrid.rowsCol._dhx_insertAt(ind,r);
 mygrid.doOnRowAdded(r);

 mygrid.math_off=false;
 if((mygrid.math_req)&&(!mygrid._parsing_)){
 for(var i=0;i<mygrid.hdr.rows[0].cells.length;i++)
 mygrid._checkSCL(r.childNodes[i]);
}

 return r;
}

_addUseApprovalCell = function(index,columnIndex,cellText,row){
 var c = row.insertCell(index);
  //if(mygrid._enbCid)
  c.id="c_"+row.idd+"_"+index;
  c._cellIndex = index;
 //c.id = columnIndex;
 c.align = mygrid.cellAlign[columnIndex];
 c.style.verticalAlign = mygrid.cellVAlign[columnIndex];
 c.bgColor = mygrid.columnColor[columnIndex] || "";

 mygrid.editStop();
 /*if (cellText.length <1)
 {
  cellText = "&nbsp;";
 }*/

   eval("mygrid.editor = new eXcell_"+mygrid.cellType[columnIndex]+"(c)");
   mygrid.editor.setValue(cellText)
   mygrid.editor = mygrid.editor.destructor();
}

function sortPartNumber()
{
 alert("sortPartNumber");
 mygrid.setSortImgPos(0,false);
}

function buildFirstSetRows()
{
  var resultsMaskTable  =  document.getElementById("resultsMaskTable");
  resultsMaskTable.style["display"] = "";

  /*var errorMessagesArea  =  document.getElementById("errorMessagesArea");
  errorMessagesArea.style["display"] = "none";*/

  /*var gridbox  =  document.getElementById("gridbox");
  alert(gridbox.offsetHeight);
  gridOffsetHeight = gridbox.offsetHeight;
  gridbox.height= ""+gridOffsetHeight+"px";*/

  if (grilLoadedCount == 0)
  {
   mygrid = new dhtmlXGridObject('gridbox');
   mygrid.setImagePath("/images/dhtmlxGrid/");
   mygrid.setHeader(headerString);
   //number of rows 20
   mygrid.setInitWidthsP("4.7,4.7,9.7,5.5,5.5,5.5,9,5.5,7.5,7.5,10,5.5,9,6,5.5,6.5,5.5,7,7,7");
   mygrid.setColAlign("left,left,left,left,left,left,left,center,left,left,left,left,left,left,left,left,left,left,left,left");
   mygrid.setColVAlign("top,top,top,top,top,top,top,top,top,top,top,top,top,top,top,top,top,top,top,top");
   mygrid.setColTypes("mro,mro,mro,mro,mro,mro,mro,mro,mro,mro,mro,mro,mro,mro,mro,mro,mro,mro,mro,mro");
   //mygrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro");
   mygrid.setColSorting("na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na,na");
   //mygrid.setOnColumnSort(sortPartNumber);
   mygrid.enableTooltips("true,true,true,true,true,true,true,false,false,false,false,false,true,false,false,false,false,false,false,false");
   mygrid.setColumnHdrColor("#7777ff,#7777ff,#7777ff,#a6c6bd,#a6c6bd,#a6c6bd,#a6c6bd,#b6ff6c,#b6ff6c,#b6ff6c,#b6ff6c,#b6ff6c,#b6ff6c,#b6ff6c,#b6ff6c,#CCCC99,#CCCC99,#CCCC99,#CCCC99,#CCCC99");
   if (isItmanagedWorkArea == "YES")
   {
    mygrid.setColumnColor(",,,#dfdfdf,#dfdfdf,#dfdfdf,#dfdfdf,,,,,,,,,,,,,");
   }
   else
   {
    mygrid.setColumnColor(",,,,,,,#dfdfdf,#dfdfdf,#dfdfdf,#dfdfdf,#dfdfdf,#dfdfdf,#dfdfdf,#dfdfdf,,,,,");
   }
   mygrid.enableBuffering(24);
   bodyOffsetHeight = window.document.body.offsetHeight;
   mygrid.enableAutoHeigth(true,(bodyOffsetHeight-382));
   //mygrid.enableAutoHeigth(true,(gridbox.offsetHeight));
   mygrid.enableKeyboardSupport(true);
  //mygrid.setOnEnterPressedHandler(returnFalse);
  mygrid.enableMultiline(false);
  //mygrid.enableLightMouseNavigation(true);
  mygrid.enableCellIds(true);
  //mygrid.enableEditEvents(true,false,false);
  //mygrid.setOnColumnSort(sortSelectedColumn);
  mygrid.setOnRowSelectHandler(useApprovalRowSelected);
  //mygrid.setOnRowDblClickedHandler(rowDblClicked);
  //mygrid.setOnRowSelectHandler(onRowSelect);
  //mygrid.setOnRightClickHandler(rightMouseClicked);
  //mygrid.setNoHeader(false);
  //mygrid.setMultiselect(true);
  mygrid.enableRightClick(false);
  mygrid.init(false);
  mygrid.entBox.onselectstart = function(){return true};
  }
  else
  {
    var invisibleElements  =  document.getElementById("invisibleElements");
    invisibleElements.innerHTML = "";

    /*var makeAllActive  =  document.getElementById("makeAllActive");
    makeAllActive.checked= false;*/

    if (isItmanagedWorkArea == "YES")
   {
    mygrid.setColumnColor(",,,#dfdfdf,#dfdfdf,#dfdfdf,#dfdfdf,,,,,,,,,,,,,");
   }
   else
   {
    mygrid.setColumnColor(",,,,,,,#dfdfdf,#dfdfdf,#dfdfdf,#dfdfdf,#dfdfdf,#dfdfdf,#dfdfdf,#dfdfdf,,,,,");
   }
  }
  try
  {
  mygrid.clearAll();
  }
  catch (ex)
  {
   alert("Exception in clearAll 661");
  }
  buildRows(0,24);

  grilLoadedCount ++;

  if (useApprovalPermission == "Yes")
  {
  var managedWorkAreaUpdates  =  document.getElementById("managedWorkAreaUpdates");
  managedWorkAreaUpdates.style["display"] = "inline";

  /*var submitUpdateButton  =  document.getElementById("submitUpdateButton");
  submitUpdateButton.style["display"] = "";

  var submitUpdateAddAll  =  document.getElementById("submitUpdateAddAll");
  submitUpdateAddAll.style["display"] = "";

  var submitDeleteAll  =  document.getElementById("submitDeleteAll");
  submitDeleteAll.style["display"] = "";*/

  var managedWorkAreaLink = document.getElementById("managedWorkAreaLink");
  var managedWorkArea  =  document.getElementById("managedWorkArea");
  if (isItmanagedWorkArea == "YES")
  {
    managedWorkArea.value = "Y";
    managedWorkAreaLink.innerHTML="Turn Override Off";
  }
  else
  {
    managedWorkArea.value = "N";
    managedWorkAreaLink.innerHTML="Turn Override On";
  }
  }
  else
  {
   var managedWorkAreaUpdates  =  document.getElementById("managedWorkAreaUpdates");
   managedWorkAreaUpdates.style["display"] = "none";

   /*var submitUpdateButton  =  document.getElementById("submitUpdateButton");
   submitUpdateButton.style["display"] = "none";

   var submitUpdateAddAll  =  document.getElementById("submitUpdateAddAll");
   submitUpdateAddAll.style["display"] = "none";

   var submitDeleteAll  =  document.getElementById("submitDeleteAll");
   submitDeleteAll.style["display"] = "none";*/
  }

  /*var gridtopLinks  =  document.getElementById("gridtopLinks");
  gridtopLinks.style["display"] = "";*/

  var gridbox = document.getElementById("gridbox");

  var parentGridBox = document.getElementById("parentGridBox");
  parentGridBox.style.height = gridbox.offsetHeight;

  stopWaitDialog();
  fixProgressBar();
  setTimeout('showErrorMessages()',100);
}

function showErrorMessages()
{
 if (showErrorMessage)
 {
  showErrorMessagesWin = new YAHOO.widget.Panel("errorMessagesArea", { width:"400px", fixedcenter: true, constraintoviewport: true, underlay:"none", close:true, visible:true, draggable:true, modal:true } );
  showErrorMessagesWin.render();
  showErrorMessagesWin.show();

  var errorMessagesArea = document.getElementById("errorMessagesArea");
  errorMessagesArea.style.display="";
 }
}
