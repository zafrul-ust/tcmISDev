function addMaterialLine() {
   var maxCount = getMaxCount();
   document.getElementById('quantity_1').disabled=false;
   var table = document.getElementById("materialTable");
   var num = table.rows.length;
   for(ii = 1 ; ii <= num; ii++ ) 
      if( document.getElementById('quantity_'+ ii) != null )
	 document.getElementById('quantity_'+ ii).disabled=false;
	 		
   // the only way to work on both ie and firefox..	 
   var cells = table.rows[1].cells;
   var newcells = new Array();
   var row = table.insertRow(num);

   for( ii = 0 ; ii < cells.length; ii++) {
      var inner = cells[ii].innerHTML;
      inner=inner.replace(/unit_price_1/gi,"unit_price_"+num);
      inner=inner.replace(/vendor_ship_date_1/gi,"vendor_ship_date_"+num);
      inner=inner.replace(/promised_date_1/gi,"promised_date_"+num);
      inner=inner.replace(/po_line_1/gi,"po_line_"+num);
      inner=inner.replace(/quantity_1/gi,"quantity_"+num); 	 
      inner=inner.replace(/item_id_1/gi,"item_id_"+num);
      newcells[ii] = cells[ii].cloneNode(false);
      var cell = row.insertCell(ii);
      cell.innerHTML = inner;
   }
 	 
   document.getElementById('quantity_'+num).value= '0' ;
   var lineCount = 1000 + maxCount; 
   document.getElementById('label_po_line_'+num).innerHTML = lineCount; 
   document.getElementById('po_line_'+num).value = lineCount;
}
 
function checkDate ( strval,strval_1 ) { 
   if( strval == null || strval.value.length == 0 ) {
      alert(messagesData.invalidShipDate);
      return false;
   }
   if( strval_1 == null || strval_1.value.length == 0 ) {
      alert(messagesData.invalidDockDate);
      return false;
   }
   strval = strval.value;
   strval_1 =strval_1.value;
   var ind = strval.indexOf('/');
   var ship_date = parseInt( strval.substring(ind+4,ind+8) )*10000 +
                   parseInt(strval.substring(ind-2,ind))*100 + 
                   parseInt(strval.substr(ind+1,ind+3));
   ind = strval_1.indexOf('/');
   var dock_date = parseInt( strval_1.substring(ind+4,ind+8) )*10000 + 
                   parseInt(strval_1.substring(ind-2,ind))*100 + 
                   parseInt(strval_1.substr(ind+1,ind+3));
   if( dock_date < ship_date ) {
      alert( messagesData.dateError + "["+ strval + "," + strval_1 +"]" );
      return false;
   }
   return true;
}	          
 
function confirmPO() 
{ 
   var orig = document.getElementById("quantity_0").value;
   var msg = messagesData.invalidTotal + orig;
   for(ii=1;ii<document.getElementById("materialTable").rows.length;ii++) {
      if( document.getElementById("quantity_"+ii) != null ) {
	 orig -= document.getElementById("quantity_"+ii).value;
	 var strval = document.getElementById("vendor_ship_date_"+ii);
	 var strval_1 = document.getElementById("promised_date_"+ii);
	 if ( !checkDate(strval, strval_1) ) {
	    return false;
	 }
      }
   }
   if( orig != 0 ) {
      alert(msg);
      return false;       
   }
   if (confirm( messagesData.confirmPO + document.getElementById("radianpo").value + ' ?') == true) {
      document.goConfirmForm.po_action.value = 'CONFIRM';
       try
       {
         //Turns off the main page and shows the transitions page
         var transitPage = document.getElementById("transitPage");
         transitPage.style["display"] = "";

         var mainPage = document.getElementById("mainPage");
         mainPage.style["display"] = "none";
         /*This is for IE, fomr some reason the table background is visible*/
         var resultsMaskTable = document.getElementById("resultsMaskTable");
         resultsMaskTable.style["display"] = "none";
       }
       catch (ex)
       {

       }
      document.goConfirmForm.submit();                       
      return true;
   }
}
 
function savePO()
{
   var orig = document.getElementById("quantity_0").value;
   var msg = messagesData.invalidTotal + orig;

   for(ii=1;ii<document.getElementById("materialTable").rows.length;ii++) {
      if( document.getElementById("quantity_"+ii) != null ) {
         orig -= document.getElementById("quantity_"+ii).value;
	 var strval = document.getElementById("vendor_ship_date_"+ii);
	 var strval_1 = document.getElementById("promised_date_"+ii);
	 if ( !checkDate(strval, strval_1) ) {
	    return;
	 }
      }
   }		
   if( orig != 0 ) {
      alert(msg);
      return false;
   }

   try
   {
     //Turns off the main page and shows the transitions page
     var transitPage = document.getElementById("transitPage");
     transitPage.style["display"] = "";

     var mainPage = document.getElementById("mainPage");
     mainPage.style["display"] = "none";
     /*This is for IE, fomr some reason the table background is visible*/
     var resultsMaskTable = document.getElementById("resultsMaskTable");
     resultsMaskTable.style["display"] = "none";
   }
   catch (ex)
   {

   }
   document.goConfirmForm.po_action.value = 'SAVE';
   document.goConfirmForm.submit();
}
 
function problemPO()
{
   try
   {
     //Turns off the main page and shows the transitions page
     var transitPage = document.getElementById("transitPage");
     transitPage.style["display"] = "";

     var mainPage = document.getElementById("mainPage");
     mainPage.style["display"] = "none";
     /*This is for IE, fomr some reason the table background is visible*/
     var resultsMaskTable = document.getElementById("resultsMaskTable");
     resultsMaskTable.style["display"] = "none";
   }
   catch (ex)
   {

   }

   document.goConfirmForm.po_action.value = 'PROBLEM';
   document.goConfirmForm.submit();
}
 
function rejectPO()
{
  var radianpo  =  document.getElementById("radianpo");
   if ( confirm(formatMessage(messagesData.rejectpomsg, radianpo.value) ) == true) 
   {
    try
    {
     //Turns off the main page and shows the transitions page
     var transitPage = document.getElementById("transitPage");
     transitPage.style["display"] = "";

     var mainPage = document.getElementById("mainPage");
     mainPage.style["display"] = "none";
     /*This is for IE, fomr some reason the table background is visible*/
     var resultsMaskTable = document.getElementById("resultsMaskTable");
     resultsMaskTable.style["display"] = "none";
    }
    catch (ex)
    {

    }       
      document.goConfirmForm.po_action.value = 'REJECT';
      document.goConfirmForm.submit();
   }             
}

  
var curId = null;
var curLine = 0;
function addFirstLine() {
  if( curLine == 0 )
     addNewLine();
}
  
function addNewLine(){
   var maxCount = getMaxCount();
   var div = document.getElementById("addItemDiv");
   // it doesn't seem the dom works.... Gee...
   var taildiv   = document.getElementById("taildiv").innerHTML;
   var myArray = new Array();
   var tableOffset = document.getElementById("oriAddChargeTable");
   var offset = 0;
   if( tableOffset != null )
      offset = tableOffset.rows.length-1;

   for( ii=0; ii< curLine; ii++ ) {
      var lineArray = new Array();
      myArray[ii] = lineArray;
      var tline = offset + ii;
      lineArray[0] = document.getElementById('addItemID_'+tline).value;
      lineArray[1] = document.getElementById('addDescription_'+tline).value;
      lineArray[2] = document.getElementById('addQuantity_'+tline).value;
      lineArray[3] = document.getElementById('addUnitPrice_'+tline).value;
      lineArray[4] = document.getElementById('AddLineNum_'+tline).value;
   }
   var inner = // merge into the form above.. "<form name='AddLineForm' id='AddLineForm' action='addchargeline.do'>" +
       "<input name='addItemID' id='addItemID' type='hidden' />" +
       "<input name='addDescription' id='addDescription' type='hidden' />" +
       "<table width='100%' border='0' cellpadding='0' cellspacing='0' class='tableResults'>" +
       "<tr><th>"+ messagesData.label1 +"</th>" +
       "<th>"+ messagesData.label2 +"</th>" +
       "<th>"+ messagesData.label3 +"</th>" +
       "<th>"+ messagesData.label4 +"</th>" +
       "<th>"+ messagesData.label5 +"</th></tr>";
   for( line = 0 ; line <= curLine; line++ ) { 
      var tline = offset + line;
      if( (line%2)== 0 )
	 inner += "<tr class='alt'>";
      else
	 inner += "<tr class=''>";
      inner += "<td><input type='text' class='inputBox' size='8' name='addItemID_"+tline+"' ";
      inner += "id='addItemID_" + tline + "' value='0' readonly='readonly'/>";
      inner += '<input type="button" name="getAddCharges_' + tline + '" id="getAddCharges_' + tline + '" class="lookupBtn" onmouseover="this.className=' + "'lookupBtn lookupBtnOver'" + '"onmouseout="this.className=' + "'lookupBtn'" + '" onclick="searchAddCharges(' + tline + ')"/></td>';
      inner += "<td><input type='text' class='inputBox' size='20' name='addDescription_" + tline + "' ";
      inner += "id='addDescription_"+ tline +"' value='' readonly='readonly'/></td>";
      inner += "<td><input type='text' class='inputBox' size='3' name='addQuantity_" + tline +"' ";
      inner += "id='addQuantity_" + tline + "' value='1'/></td>";
      inner += "<td><input type='text' class='inputBox' size='5' name='addUnitPrice_" + tline + "' ";
      inner += "id='addUnitPrice_" + tline + "' value='0'/>";
      inner += "<input type='hidden' name='AddLineNum_"+ tline + "' id='AddLineNum_" + tline + "' value='0'/>";
      inner += "</td>";
      inner += '<td align="center">'; 
      inner += '<input type="button" name="removeLine_' + tline + '" id="removeLine_' + tline + '" class="smallBtns" onmouseover="this.className=' + "'smallBtns smallBtnsOver'" + '"onmouseout="this.className=' + "'smallBtns'" + '" onclick="removeLine(' + tline + ')"/></td>';         
      inner += "</tr>";
   }
   inner += "</table><div name='taildiv' id='taildiv'>" + taildiv + "</div>";// merged into above form</form>";
   div.innerHTML = inner;
   for( ii=0; ii< curLine; ii++ ) {
      var lineArray = myArray[ii];
      var tline = offset + ii;
      document.getElementById('addItemID_'+tline).value = lineArray[0] ;
      document.getElementById('addDescription_'+tline).value = lineArray[1] ;
      document.getElementById('addQuantity_'+tline).value = lineArray[2] ;
      document.getElementById('addUnitPrice_'+tline).value = lineArray[3] ;
      document.getElementById('AddLineNum_'+tline).value = lineArray[4] ;
      document.getElementById('removeLine_'+tline).value = lineArray[4] ;
   }
   var lineCount = 1000+ maxCount; 
   document.getElementById('AddLineNum_'+ (curLine+offset) ).value = lineCount;
   document.getElementById('removeLine_'+ (curLine+offset) ).value = lineCount;
   curLine++;
}
  
function removeLine(removeRow){
   //            <th><fmt:message key="label.remove"/></th>
   //           <td align="center">
   //           <input type='button' class="inputBtns" onmouseover="this.className='inputBtns inputBtnsOver'" onmouseout="this.className='inputBtns'" value='<c:out value="${addcharges.poLine}"/>' onclick='removeLine("<c:out value="${addcharges.poLine}"/>");' <logic:present name="ConfirmedBean"><fmt:message key="label.disabled"/></logic:present> >         
   //         </td>      
   var tableOffset = document.getElementById("oriAddChargeTable");
   var offset = 0;
   if( tableOffset != null )
      offset = tableOffset.rows.length-1;
   var div = document.getElementById("addItemDiv");
   // it doesn't seem the dom works.... Gee...
   var taildiv   = document.getElementById("taildiv").innerHTML;
   var myArray = new Array();
	
   for( ii=0; ii< curLine; ii++ ) {
      var lineArray = new Array();
      myArray[ii] = lineArray;
      var tline = offset + ii;
      lineArray[0] = document.getElementById('addItemID_'+tline).value;
      lineArray[1] = document.getElementById('addDescription_'+tline).value;
      lineArray[2] = document.getElementById('addQuantity_'+tline).value;
      lineArray[3] = document.getElementById('addUnitPrice_'+tline).value;
      lineArray[4] = document.getElementById('AddLineNum_'+tline).value;
   }
   var inner = // merge into the form above.. "<form name='AddLineForm' id='AddLineForm' action='addchargeline.do'>" +
  	  "<input name='addItemID' id='addItemID' type='hidden' />"+
  	  "<input name='addDescription' id='addDescription' type='hidden' />"+
	  "<table width='100%' border='0' cellpadding='0' cellspacing='0' class='tableResults'>" +
  	  "<tr><th>"+ messagesData.label1 +"</th>" +
  	  "<th>"+ messagesData.label2 +"</th>" +
	  "<th>"+ messagesData.label3 +"</th>" +
	  "<th>"+ messagesData.label4 +"</th>" +
          "<th>"+ messagesData.label5 +"</th></tr>";
   for( line = 0 ; line < curLine -1 ; line++ ) { 
      var tline = line + offset;
      if( (line%2)== 0 )
	 inner += "<tr class='alt'>";
      else
	 inner += "<tr class=''>";
      inner += "<td><input type='text' class='inputBox' size='8' name='addItemID_"+tline+"' ";
      inner += "id='addItemID_" + tline + "' value='0' readonly='readonly'/>";
      inner += '<input type="button" name="getAddCharges_' + tline + '" id="getAddCharges_' + tline + '" class="lookupBtn" onmouseover="this.className=' + "'lookupBtn lookupBtnOver'" + '"onmouseout="this.className=' + "'lookupBtn'" + '" onclick="searchAddCharges(' + tline + ')"/></td>';
      inner += "<td><input type='text' class='inputBox' size='20' name='addDescription_" + tline + "' ";
      inner += "id='addDescription_"+ tline +"' value='' readonly='readonly'/></td>";
      inner += "<td><input type='text' class='inputBox' size='3' name='addQuantity_" + tline +"' ";
      inner += "id='addQuantity_" + tline + "' value='1'/></td>";
      inner += "<td><input type='text' class='inputBox' size='5' name='addUnitPrice_" + tline + "' ";
      inner += "id='addUnitPrice_" + tline + "' value='0'/>";
      inner += "<input type='hidden' name='AddLineNum_"+ tline + "' id='AddLineNum_" + tline + "' value='0'/>";
      inner += "</td>";
      inner += '<td align="center">'; 
      inner += '<input type="button" name="removeLine_' + tline + '" id="removeLine_' + tline + '" class="smallBtns" onmouseover="this.className=' + "'smallBtns smallBtnsOver'" + '"onmouseout="this.className=' + "'smallBtns'" + '" onclick="removeLine(' + tline + ')"/></td>';
      inner += "</tr>";
   }
   inner += "</table><div name='taildiv' id='taildiv'>" + taildiv + "</div>";// merged into above form</form>";
   div.innerHTML = inner;
   for( ii=0; ii< curLine -1 ; ii++ ) {
      var lineArray = myArray[ii];
      if( ii >= removeRow ) 
  	 lineArray = myArray[ii+1];
      var tline = offset + ii;
      document.getElementById('addItemID_'+tline).value = lineArray[0] ;
      document.getElementById('addDescription_'+tline).value = lineArray[1] ;
      document.getElementById('addQuantity_'+tline).value = lineArray[2] ;
      document.getElementById('addUnitPrice_'+tline).value = lineArray[3] ;
      document.getElementById('AddLineNum_'+tline).value = myArray[ii][4] ;
      document.getElementById('removeLine_'+tline).value = myArray[ii][4] ;
   }
   curLine--;
}

var searchLine; 
function setIdValue()  {
	document.getElementById('addItemID_'+searchLine).value = document.getElementById('addItemID').value;
	var comfirmlink = document.getElementById("comfirmlink");
	if (comfirmlink) {
		comfirmlink.style["display"] = "none";
	}

	var savelink = document.getElementById("savelink");
	if (savelink) {
		savelink.style["display"] = "none";
	}
}
  
function setDescriptionValue() 
{
   document.getElementById('addDescription_'+searchLine).value = document.getElementById('addDescription').value;
}
  
function searchAddCharges(line) 
{  
   searchLine = line;
    openWinGeneric('/tcmIS/common/loadingpleasewait.jsp','_searchAddCharges','380','400','yes',"50","50");
    document.SearchItemForm.target='_searchAddCharges';
    var a = window.setTimeout("document.SearchItemForm.submit();",50);
}
  
function addChargeLine()
{
   for( ii = 0; ii < curLine; ii ++ ) {

      if (document.getElementById('addItemID_'+ ii ).value != '0' &&
          document.getElementById('addItemID_'+ ii ).value.length > 0)
      {
         if  (document.getElementById('addUnitPrice_'+ii).value != '0' &&
              document.getElementById('addUnitPrice_'+ii).value.length > 0)
         {      
             document.AddLineForm.submit();
         } 
         else
         {
             alert(messagesData.invalidPrice);
         }
      }  
      else    
      {  
         alert(messagesData.invalidId);
      }
   }
   /*
    if (document.AddLineForm.addItemID.value != '0' &&
         document.AddLineForm.addItemID.value.length > 0)
     {
        if  (document.AddLineForm.addUnitPrice.value != '0' &&
             document.AddLineForm.addUnitPrice.value.length > 0)
        {      
           document.AddLineForm.submit();
        } 
        else
        {
           alert('Invalid Price!');
        }
     } 
     else    
     {  
        alert('Invalid Item ID');
     }
    */
}
 
/* original removeLine action 
  function removeLine(lineNum) {
    if (confirm('Remove line ' + lineNum + ' ?')) {
      document.goAddtlChargesForm.line.value = lineNum;
      document.goAddtlChargesForm.submit();
    }
  }
*/

function getMaxCount() {
   var maxCount = 0;
   var table = document.getElementById("materialTable");
   var num = table.rows.length;
   for(ii = 1 ; ii <= num; ii++ ) 
      if( document.getElementById('po_line_'+ ii) != null ) {
         var intval = parseInt( document.getElementById('po_line_'+ ii).value );
         if( intval > maxCount )
	    maxCount = intval;
      }

   var tableOffset = document.getElementById("oriAddChargeTable");
   var offset = 0;
   if( tableOffset != null )
      offset = tableOffset.rows.length-1;

   var totalAddCharge = offset + curLine; 
   for( ii=0; ii< totalAddCharge; ii++ ) {
      if( document.getElementById('AddLineNum_'+ii) != null ) { 
	 var intval = parseInt( document.getElementById('AddLineNum_'+ii).value ); 
	 if( intval > maxCount )
	    maxCount = intval;
	 }
      }
   return maxCount;
} 

function printpo(headerNote)
{
    var po  =  document.getElementById("po");
    //http://ws1.tcmis.com/tcmIS/supply/faxpo?Session=7&po=975970&HeaderNote=0
    if (po.value.trim().length > 0)
    {
	 loc = "/tcmIS/supplier/faxpo?Session=7&po=";
	 loc = loc + po.value;
	 loc = loc + "&HeaderNote="+headerNote+"";
	 openWinGeneric(loc,"printpo","700","600","yes","200","200")		
    }
}