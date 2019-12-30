var submitcount=0;
var updatecount=0;
var max = 20 //maximum rows
var color = "#0000ff";
var selected_row = 0;
var selected_rowid = "row1";

function makeCursorBusy()
{
document.body.style.cursor = 'wait';
}

function makeCursorNormal()
{
document.body.style.cursor = 'auto';
}

String.prototype.trim = function()
{
// skip leading and trailing whitespace
// and return everything in between
  return this.replace(/^\s*(\b.*\b|)\s*$/, "$1");
}

function SubmitOnlyOnce()
{
    if (submitcount == 0)
    {
        submitcount++;
        return true;
    }
    else
    {
        alert("This form has already been submitted.  Thanks!");
        return false;
    }
}

function donothing(entered)
{
    return false;
}

function addOptionItem(obj,value,text) {
	  var index = obj.length;
     obj.options[index]=new Option(text,value);

     obj.options[index].selected = true;
}


function actionValue(name, entered)
{
    var actvalue = name.toString();
    window.document.purchaseorder.Action.value = actvalue;

}

function setlineUnconfirmed(selectedRow)
{
}


function changeTotalPrice(selectedRow)
{

}

function changeSuppTotalPrice(selectedRow)
{

}


function changePoTotalPrice()
{

}


function removeAllLines()
{
  var chargeTable = document.getElementById("linetable");

  var allTRs = chargeTable.getElementsByTagName("tr");
  var nowofRows = (allTRs.length)*1;

  for (j = 0; j<nowofRows; j++)
  {
    for (i=0;i<chargeTable.childNodes.length;i++)
    {
      chargeTable.removeChild(chargeTable.childNodes[i]);
    }
     var divname1 = "divrow"+(j+1);
     mydetailtable = document.getElementById(divname1);
     mydetailtable.removeNode(true);
 }
}

function checkflowChecks(numdetrow)
{

}

function checkSpecChecks(numdetrow,specLine)
{

}

function checkflowflowChecks(numdetrow)
{

}

function checkFlowChecks(numdetrow,specLine)
{

}

function checkAddChargeLines(numdetrow)
{
    var nooflinesinaddcharge = document.getElementById("nooflinesinaddcharge"+numdetrow+"");
      for (j = 0; j<nooflinesinaddcharge.value; j++)
      {
         chargelinenumber = document.getElementById("chargelinenumber"+(j+1)+"");

         chargeAncnumber = document.getElementById("chargeAncnumber"+numdetrow+""+chargelinenumber.value+"");

         checkAncValue = chargeAncnumber.value;
         //alert(checkAncValue);
         if (checkAncValue == "Yes")
        {
              addchargecheck = document.getElementById("addchargecheckdivrow"+numdetrow+""+numdetrow+""+(j+1)+"");
              //alert("Here  "+addchargecheck.checked+"");
              addchargecheck.checked = true;
              //addchargecheck.disabled=false;
        }
     }
}

function refreshlinecharges(numdetrow,setUnconfirmedFlag)
{
        //alert("hi!  "+numdetrow+"");
        mytable = document.getElementById("linetable");

        var allTRs = mytable.getElementsByTagName("tr");
        var str = "# of table Rows: " + allTRs.length + "\n";
        var nowofRows = (allTRs.length)*1;
        var rownum = numdetrow;
        var divname =  "divrow"+numdetrow;
        var chargeTable = document.getElementById(divname);

        for (i=0;i<chargeTable.childNodes.length;i++)
       {
           chargeTable.removeChild(chargeTable.childNodes[i]);
       }

       newdivtablebody = document.createElement("TBODY");

         newdivcurrent_row=document.createElement("TR");
         newdivcurrent_row.className = "bluenocur";
         newdivcurrent_cell=document.createElement("TD");
         newdivcurrent_cell.setAttribute("align",'right');
         newdivcurrent_cell.setAttribute("colSpan","2");
         newdivcurrent_cell.innerHTML = "<B>Last Confirmed:</B>";
         newdivcurrent_row.appendChild(newdivcurrent_cell);

         newdivcurrent_cell=document.createElement("TD");
         newdivcurrent_cell.id = "lastconfirmedcell"+divname+rownum;
         newdivcurrent_cell.setAttribute("align",'left');
         newdivcurrent_cell.setAttribute("colSpan","2");
         newdivcurrent_cell.innerHTML = "";
         newdivcurrent_row.appendChild(newdivcurrent_cell);

         newdivtablebody.appendChild(newdivcurrent_row);

        newdivcurrent_row=document.createElement("TR");
        newdivcurrent_cell=document.createElement("TD");
        newdivcurrent_cell.setAttribute("align",'right');
        newdivcurrent_cell.setAttribute("colSpan","2");
        newdivcurrent_cell.innerHTML = "<B>Desc:</B>";
        newdivcurrent_row.appendChild(newdivcurrent_cell);

        newdivcurrent_cell=document.createElement("TD");
        newdivcurrent_cell.id = "itemdesc"+divname+rownum;
        newdivcurrent_cell.setAttribute("align",'left');
        newdivcurrent_cell.setAttribute("colSpan","2");
        newdivcurrent_cell.innerHTML = "";
        newdivcurrent_row.appendChild(newdivcurrent_cell);

        newdivtablebody.appendChild(newdivcurrent_row);

       newdivcurrent_row=document.createElement("TR");
       newdivcurrent_row.id = "secondarysupplierrow"+divname+rownum;
       newdivcurrent_row.className = "bluenocur";

           newdivcurrent_cell=document.createElement("TD");
           newdivcurrent_cell.id = "secondarysuppliertitle"+divname+rownum;
           newdivcurrent_cell.setAttribute("align",'right');
           newdivcurrent_cell.setAttribute("colSpan","2");
           newdivcurrent_cell.innerHTML = "<B>Secondary Supplier:</B>";
           newdivcurrent_row.appendChild(newdivcurrent_cell);

           newdivcurrent_cell=document.createElement("TD");
           newdivcurrent_cell.id = "secondarysuppliercell"+divname+rownum;
           newdivcurrent_cell.setAttribute("align",'left');
           newdivcurrent_cell.setAttribute("colSpan","2");
           newdivcurrent_cell.innerHTML = "";
       newdivcurrent_row.appendChild(newdivcurrent_cell);

      newdivtablebody.appendChild(newdivcurrent_row);

        newdivcurrent_row=document.createElement("TR");
		  newdivcurrent_cell=document.createElement("TD");
        newdivcurrent_cell.setAttribute("align",'left');
        newdivcurrent_cell.setAttribute("colSpan","2");
        newdivcurrent_cell.innerHTML = "";
        newdivcurrent_row.appendChild(newdivcurrent_cell);

		  newdivcurrent_cell=document.createElement("TD");
        newdivcurrent_cell.setAttribute("align",'right');
        newdivcurrent_cell.setAttribute("colSpan","2");
        newdivcurrent_cell.innerHTML = "<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name='showlinehistory' OnClick=showPOLineHistory('"+rownum+"') value=\"Line History\">";
        newdivcurrent_row.appendChild(newdivcurrent_cell);

       newdivtablebody.appendChild(newdivcurrent_row);


        // creating all po line details
            newdivcurrent_row=document.createElement("TR");
            newdivcurrent_row.id = "detailrow1"+divname+rownum;
            newdivcurrent_row.className = "bluenocur";
                newdivcurrent_cell=document.createElement("TH");
                newdivcurrent_cell.id = "titleline1"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'2%');
                newdivcurrent_cell.setAttribute("align",'right');
                newdivcurrent_cell.innerHTML = "<B>Select</B>";
            newdivcurrent_row.appendChild(newdivcurrent_cell);

            newdivcurrent_cell=document.createElement("TH");
                newdivcurrent_cell.id = "titleline2"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'5%');
                newdivcurrent_cell.setAttribute("align",'right');
                newdivcurrent_cell.innerHTML = "<B>Line</B>";
            newdivcurrent_row.appendChild(newdivcurrent_cell);

            newdivcurrent_cell=document.createElement("TH");
                newdivcurrent_cell.id = "titleline3"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'5%');
                newdivcurrent_cell.setAttribute("align",'right');
                newdivcurrent_cell.innerHTML = "<B>Item</B>";
            newdivcurrent_row.appendChild(newdivcurrent_cell);

            newdivcurrent_cell=document.createElement("TH");
                newdivcurrent_cell.id = "titleline4"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'10%');
                newdivcurrent_cell.setAttribute("align",'right');
                newdivcurrent_cell.innerHTML = "<B>Desc</B>";
            newdivcurrent_row.appendChild(newdivcurrent_cell);

        newdivtablebody.appendChild(newdivcurrent_row);

    var lineadded = 0;
    var Color1 ="white";

    for(j=0;j<nowofRows;j++)
    {
        var itemID = document.getElementById("lineitemid"+(j+1)+"");
        var itemType = document.getElementById("itemtype"+(j+1)+"");
        var validitem = document.getElementById("validitem"+(j+1)+"");
        var linestatus = document.getElementById("linestatus"+(j+1)+"");
		  var canassignaddcharge = document.getElementById("canassignaddcharge"+(j+1)+"");

        if ( (itemID.value.length > 0) && (canassignaddcharge.value == "Y") && (validitem.value == "Yes") && (linestatus.value != "Remove") )
        {
            lineadded ++;

            if (lineadded%2==0)
            {
                Color1 ="whitenocur";
            }
            else
            {
                Color1 ="bluenocur";
            }

            newdivcurrent_row=document.createElement("TR");
            newdivcurrent_row.id = "linechargerow"+divname+rownum+j;
            newdivcurrent_row.className = Color1;

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "select"+divname+rownum+j;
                newdivcurrent_cell.setAttribute("width",'2%');
                newdivcurrent_cell.setAttribute("align",'center');
                newdivcurrent_cell.innerHTML = "<INPUT TYPE=\"checkbox\" value=\"Yes\" NAME=\"addchargecheck"+divname+rownum+lineadded+"\" onClick=\"setlineUnconfirmed('"+rownum+"')\" disabled>";
            newdivcurrent_row.appendChild(newdivcurrent_cell);

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "line"+divname+rownum+j;
                newdivcurrent_cell.setAttribute("width",'5%');
                newdivcurrent_cell.setAttribute("align",'center');
                newdivcurrent_cell.innerHTML = (j+1) + "<INPUT TYPE=\"hidden\" NAME=\"chargeAncnumber"+rownum+""+(j+1)+"\" VALUE=\"No\"><INPUT TYPE=\"hidden\" NAME=\"chargelinenumber"+lineadded+"\" VALUE=\""+(j+1)+"\">";
            newdivcurrent_row.appendChild(newdivcurrent_cell);

            newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "line"+divname+rownum+j;
                newdivcurrent_cell.setAttribute("width",'5%');
                newdivcurrent_cell.setAttribute("align",'center');
                newdivcurrent_cell.innerHTML = itemID.value;
            newdivcurrent_row.appendChild(newdivcurrent_cell);

            var para = document.getElementById("itemdescdivrow"+(j+1)+(j+1)+"").cloneNode(true);
            newdivcurrent_row.appendChild(para);

        newdivtablebody.appendChild(newdivcurrent_row);
        }
    }
        newdivcurrent_row=document.createElement("TR");
            newdivcurrent_cell=document.createElement("TD");
            newdivcurrent_cell.setAttribute("align",'left');
            newdivcurrent_cell.setAttribute("colSpan","4");
            newdivcurrent_cell.innerHTML = "<INPUT TYPE=\"hidden\" NAME=\"nooflinesinaddcharge"+rownum+"\" VALUE=\""+lineadded+"\">";
        newdivcurrent_row.appendChild(newdivcurrent_cell);
        newdivtablebody.appendChild(newdivcurrent_row);

        chargeTable.appendChild(newdivtablebody);

	  if (setUnconfirmedFlag == "Yes")
	  {
        setlineUnconfirmed(rownum);
	  }
}

function  originalversion()
{
    var itemnotestable  =  document.getElementById("mainheadertable");
    itemnotestable.className =  "moveup";

    var itemnotestable  =  document.getElementById("orderdetail");
    itemnotestable.className =  "scroll_column75";

}

function  printversion()
{
    var itemnotestable  =  document.getElementById("mainheadertable");
    itemnotestable.className =  "displaynone";

    var itemnotestable  =  document.getElementById("orderdetail");
    itemnotestable.className =  "moveup";

}

function addLineCharge()
{
    //alert("Add Line Charge");
    // getting reference to my Line Table
    mytable = document.getElementById("linetable");

    var allTRs = mytable.getElementsByTagName("tr");
    var str = "# of table Rows: " + allTRs.length + "\n";
    var nowofRows = (allTRs.length)*1;
    var rownum = (allTRs.length)*1 + 1;
    //alert(str);

    if (rownum > 1)
    {
        var Color ="white";
		  var itemheColor = " ";

        if (rownum%2==0)
        {
            Color ="blue";
            itemheColor = "INVISIBLEHEADBLUE";
        }
        else
        {
            Color ="white";
            itemheColor = "INVISIBLEHEADWHITE";
        }


        var lineNumberc = 0;

        if (allTRs.length == 0)
        {
            lineNumberc = rownum;
        }
        else
        {
            var lineNumber = document.getElementById("row"+allTRs.length+"linenumber");
            lineNumberc = (Math.floor((lineNumber.value)*1) + 1);
        }

        Nototallines = document.getElementById("totallines");
        Nototallines.value = rownum;

        // get the reference for the body
            var mybody=document.getElementById("mainpara");

            // creates an element whose tag name is TBODY
            mytablebody = document.createElement("TBODY");
            // creating all cells
            // creates an element whose tag name is TR
                mycurrent_row=document.createElement("TR");
                mycurrent_row.onmouseup=function() {
                	var materials = document.getElementById("materialId"+(this.rowIndex+1)).innerHTML.split(",");
                	var menus = [];
                	for (m in materials) {
                		mrd = materials[m].split(" : ");
                		menu = "text="+materials[m]+";url=javascript:createViewContactLog('"+mrd[0]+"','"+mrd[1]+"');";
                		menus[menus.length] = menu;
                	}
                	if (menus.length > 0) {
	                	replaceMenu('contactLogMenu', menus);
	                	toggleContextMenu('contactLogMenu');
                	}
                };
                mycurrent_row.id = "row"+rownum;
                mycurrent_row.className = Color;
               // mycurrent_row.attachEvent("onclick", catchevent);
               // mycurrent_row.attachEvent("mouseover", catchoverevent);

                    // creates an element whose tag name is TD
                    mycurrent_cell=document.createElement("TD");
                    mycurrent_cell.id = "linenumberammn"+rownum;
                    mycurrent_cell.attachEvent("onclick", catchevent);
                    mycurrent_cell.attachEvent("mouseover", catchoverevent);
                    mycurrent_cell.style.cursor = "hand";

                    mycurrent_cell.setAttribute("width",'30');
                    mycurrent_cell.innerHTML = lineNumberc+"/0";
                    // appends the cell TD into the row TR
                    mycurrent_row.appendChild(mycurrent_cell);
                    
                    // creates an element whose tag name is TD
                    mycurrent_cell=document.createElement("TD");
                    mycurrent_cell.id = "ghscompliantcell"+rownum;
                    mycurrent_cell.setAttribute("width",'40');
                    mycurrent_cell.innerHTML = "";
                    // appends the cell TD into the row TR
                    mycurrent_row.appendChild(mycurrent_cell);

                    // creates an element whose tag name is TD
                    mycurrent_cell=document.createElement("TD");
                    mycurrent_cell.id = "cell2"+rownum;
                    mycurrent_cell.setAttribute("width",'92');
                    mycurrent_cell.setAttribute("align",'left');
                    name = "lineitemid" + (rownum);
                    mycurrent_cell.innerHTML = "<INPUT TYPE=\"hidden\" NAME=\"itemtype"+rownum+"\" VALUE=\"\"><INPUT TYPE=\"hidden\" NAME=\"validitem"+rownum+"\" VALUE=\"No\"><input type=\"text\" size=\"5\" CLASS=\""+itemheColor+"\" onChange=\"invalidateItem('"+rownum+"')\" name='"+name+"' id='"+name+"'><BUTTON name='button"+name+"' STYLE=\"display: none;\" OnClick=getChargeItemDetail('"+rownum+"') type=button><IMG src=\"/images/search_small.gif\" alt=\"Item ID\"></BUTTON>";
                    // appends the cell TD into the row TR
                    mycurrent_row.appendChild(mycurrent_cell);

                    // creates an element whose tag name is TD
                    mycurrent_cell=document.createElement("TD");
                    mycurrent_cell.id = "itemtypecell"+rownum;
                    mycurrent_cell.setAttribute("width",'20');
                    mycurrent_cell.innerHTML = "";
                    // appends the cell TD into the row TR
                    mycurrent_row.appendChild(mycurrent_cell);

                    // creates an element whose tag name is TD
                    mycurrent_cell=document.createElement("TD");
                    mycurrent_cell.id = "cell5"+rownum;
                    mycurrent_cell.setAttribute("width",'40');
                    name = "dateneeded" + (rownum);
                    mycurrent_cell.innerHTML = "";
                    //mycurrent_cell.innerHTML = "<input type=\"text\" size=\"6\" CLASS=\"HEADER\" onChange=\"setlineUnconfirmed('"+rownum+"')\" name='"+name+"' id='"+name+"'><a href=\"javascript: void(0);\" onClick=\"return getCalendar(document.purchaseorder."+name+");\">&diams;</a>";
                    // appends the cell TD into the row TR
                    mycurrent_row.appendChild(mycurrent_cell);

						  // creates an element whose tag name is TD
                    mycurrent_cell=document.createElement("TD");
                    mycurrent_cell.id = "cell12"+rownum;
                    mycurrent_cell.setAttribute("width",'40');
                    name = "projsuppshipdate" + (rownum);
                    mycurrent_cell.innerHTML = "";
                    //mycurrent_cell.innerHTML = "<input type=\"text\" size=\"6\" CLASS=\"HEADER\" onChange=\"setlineUnconfirmed('"+rownum+"')\" name='"+name+"' id='"+name+"'><a href=\"javascript: void(0);\" onClick=\"return getCalendar(document.purchaseorder."+name+");\">&diams;</a>";
                    // appends the cell TD into the row TR
                    mycurrent_row.appendChild(mycurrent_cell);

                    // creates an element whose tag name is TD
                    mycurrent_cell=document.createElement("TD");
                    mycurrent_cell.id = "cell6"+rownum;
                    mycurrent_cell.setAttribute("width",'40');
                    name = "datepromised" + (rownum);
                    mycurrent_cell.innerHTML = "";
                    //mycurrent_cell.innerHTML = "<input type=\"text\" size=\"6\" CLASS=\"HEADER\" onChange=\"setlineUnconfirmed('"+rownum+"')\" name='"+name+"' id='"+name+"'><a href=\"javascript: void(0);\" onClick=\"return getCalendar(document.purchaseorder."+name+");\">&diams;</a>";
                    // appends the cell TD into the row TR
                    mycurrent_row.appendChild(mycurrent_cell);

                    // creates an element whose tag name is TD
                    mycurrent_cell=document.createElement("TD");
                    mycurrent_cell.id = "cell7"+rownum;
                    mycurrent_cell.setAttribute("width",'40');
                    mycurrent_cell.setAttribute("align",'right');
                    name = "lineqty" + (rownum);
                    mycurrent_cell.innerHTML = "<input type=\"text\" size=\"2\" CLASS=\""+itemheColor+"\" onChange=\"changeTotalPrice('"+rownum+"')\" name='"+name+"' id='"+name+"' readonly>";
                    // appends the cell TD into the row TR
                    mycurrent_row.appendChild(mycurrent_cell);

                    // creates an element whose tag name is TD
                    mycurrent_cell=document.createElement("TD");
                    mycurrent_cell.id = "pakgingcell"+rownum;
                    mycurrent_cell.setAttribute("width",'100');
                    mycurrent_cell.innerHTML = "";
                    // appends the cell TD into the row TR
                    mycurrent_row.appendChild(mycurrent_cell);

                    // creates an element whose tag name is TD
                    mycurrent_cell=document.createElement("TD");
                    mycurrent_cell.id = "cell8"+rownum;
                    mycurrent_cell.setAttribute("width",'40');
                    mycurrent_cell.setAttribute("align",'right');
                    name = "lineunitprice" + (rownum);
                    mycurrent_cell.innerHTML = "<input type=\"text\" size=\"4\" CLASS=\""+itemheColor+"\" onChange=\"changeTotalPrice('"+rownum+"')\" name='"+name+"' id='"+name+"' readonly>";
                    // appends the cell TD into the row TR
                    mycurrent_row.appendChild(mycurrent_cell);

                    // creates an element whose tag name is TD
                    mycurrent_cell=document.createElement("TD");
                    mycurrent_cell.id = "extpricecell"+rownum;
                    mycurrent_cell.setAttribute("width",'90');
                    mycurrent_cell.setAttribute("align",'right');
                    mycurrent_cell.innerHTML = "";
                    // appends the cell TD into the row TR
                    mycurrent_row.appendChild(mycurrent_cell);

                    // creates an element whose tag name is TD
                    mycurrent_cell=document.createElement("TD");
                    mycurrent_cell.id = "qtyreceivedcell"+rownum;
                    mycurrent_cell.setAttribute("width",'30');
                    mycurrent_cell.innerHTML = "";
                    // appends the cell TD into the row TR
                    mycurrent_row.appendChild(mycurrent_cell);

						  // creates an element whose tag name is TD
                    mycurrent_cell=document.createElement("TD");
                    mycurrent_cell.id = "qtyvouchered"+rownum;
                    mycurrent_cell.setAttribute("width",'30');
                    mycurrent_cell.innerHTML = "";
                    // appends the cell TD into the row TR
                    mycurrent_row.appendChild(mycurrent_cell);

                    // creates an element whose tag name is TD
                    mycurrent_cell=document.createElement("TD");
                    mycurrent_cell.id = "qtyreturned"+rownum;
                    mycurrent_cell.setAttribute("width",'30');
                    //mycurrent_cell.innerHTML = "";
                    //mycurrent_cell.innerHTML = "<INPUT TYPE=\"hidden\" NAME=\"row"+rownum+"row\" VALUE=\""+rownum+"\"><INPUT TYPE=\"hidden\" NAME=\"ammendmentcomments"+rownum+"\" VALUE=\"\"><INPUT TYPE=\"hidden\" NAME=\"ammendment"+rownum+"\" VALUE=\"0\"><INPUT TYPE=\"hidden\" NAME=\"originallinestatus"+rownum+"\" VALUE=\"Draft\"><INPUT TYPE=\"hidden\" NAME=\"row"+rownum+"linenumber\" VALUE=\""+lineNumberc+"\"><INPUT TYPE=\"hidden\" NAME=\"linestatus"+rownum+"\" VALUE=\"Draft\"><INPUT TYPE=\"hidden\" NAME=\"linetotalprice"+rownum+"\" VALUE=\"0\"><INPUT TYPE=\"hidden\" NAME=\"qtyreceived"+rownum+"\" VALUE=\"0\"><INPUT TYPE=\"hidden\" NAME=\"lineArchived"+rownum+"\" VALUE=\"\"><INPUT TYPE=\"hidden\" NAME=\"canassignaddcharge"+rownum+"\" VALUE=\"N\"><INPUT TYPE=\"hidden\" NAME=\"linechange"+rownum+"\" VALUE=\"Yes\"><INPUT TYPE=\"hidden\" NAME=\"purchasingUnitsPerItem"+rownum+"\" VALUE=\"\">";
		 	           mycurrent_cell.innerHTML = "<INPUT TYPE=\"hidden\" NAME=\"row"+rownum+"row\" VALUE=\""+rownum+"\"><INPUT TYPE=\"hidden\" NAME=\"ammendmentcomments"+rownum+"\" VALUE=\"\"><INPUT TYPE=\"hidden\" NAME=\"ammendment"+rownum+"\" VALUE=\"0\"><INPUT TYPE=\"hidden\" NAME=\"originallinestatus"+rownum+"\" VALUE=\"Draft\"><INPUT TYPE=\"hidden\" NAME=\"row"+rownum+"linenumber\" VALUE=\""+lineNumberc+"\"><INPUT TYPE=\"hidden\" NAME=\"linestatus"+rownum+"\" VALUE=\"Draft\"><INPUT TYPE=\"hidden\" NAME=\"linetotalprice"+rownum+"\" VALUE=\"0\"><INPUT TYPE=\"hidden\" NAME=\"qtyreceived"+rownum+"\" VALUE=\"0\"><INPUT TYPE=\"hidden\" NAME=\"lineArchived"+rownum+"\" VALUE=\"\"><INPUT TYPE=\"hidden\" NAME=\"linechange"+rownum+"\" VALUE=\"Yes\"><INPUT TYPE=\"hidden\" NAME=\"canassignaddcharge"+rownum+"\" VALUE=\"N\"><INPUT TYPE=\"hidden\" NAME=\"purchasingUnitsPerItem"+rownum+"\" VALUE=\"\"><INPUT TYPE=\"hidden\" NAME=\"everConfirmed"+rownum+"\" ID=\"everConfirmed"+rownum+"\" VALUE=\"\">";
						  // appends the cell TD into the row TR
                    mycurrent_row.appendChild(mycurrent_cell);

                    // creates an element whose tag name is TD
                    mycurrent_cell=document.createElement("TD");
                    mycurrent_cell.id = "linestatustext"+rownum;
                    mycurrent_cell.setAttribute("width",'10');
                    mycurrent_cell.innerHTML = "Draft";
                    // appends the cell TD into the row TR
                    mycurrent_row.appendChild(mycurrent_cell);
                    
                    mycurrent_cell=document.createElement("TD");
                    mycurrent_cell.style.display = "none";
                    mycurrent_cell.id = "materialId"+rownum;
                    mycurrent_cell.innerHTML = "";
                    mycurrent_row.appendChild(mycurrent_cell);

						  /*// creates an element whose tag name is TD for ARCHIVED 12-04-02
                    mycurrent_cell=document.createElement("TD");
                    mycurrent_cell.id = "cell11"+rownum;
                    mycurrent_cell.setAttribute("width",'1');
                    name = "lineArchived" + (rownum);
                    //mycurrent_cell.innerHTML = "<input type=\"text\" size=\"4\" CLASS=\"HEADER\" name='"+name+"' id='"+name+"'>";
                    mycurrent_cell.innerHTML = "<INPUT TYPE=\"hidden\" NAME='"+name+"' VALUE=\"\">";
                    // appends the cell TD into the row TR
                    mycurrent_row.appendChild(mycurrent_cell);*/

                // appends the row TR into TBODY
                mytablebody.appendChild(mycurrent_row);

            // appends TBODY into TABLE
            mytable.appendChild(mytablebody);

            var divname =  "divrow"+rownum;

            newdivtable = document.createElement("TABLE");
            newdivtable.setAttribute("width",'100%');
            newdivtable.setAttribute("CELLSPACING",'1');
            newdivtable.setAttribute("CELLPADDING",'3');
            newdivtable.id = divname;
            newdivtable.className =  "displaynone";

            newdivtablebody = document.createElement("TBODY");

            newdivcurrent_row=document.createElement("TR");
            newdivcurrent_row.className = "bluenocur";
            newdivcurrent_cell=document.createElement("TD");
            newdivcurrent_cell.setAttribute("align",'right');
            newdivcurrent_cell.setAttribute("colSpan","2");
            newdivcurrent_cell.innerHTML = "<B>Last Confirmed:</B>";
            newdivcurrent_row.appendChild(newdivcurrent_cell);

            newdivcurrent_cell=document.createElement("TD");
            newdivcurrent_cell.id = "lastconfirmedcell"+divname+rownum;
            newdivcurrent_cell.setAttribute("align",'left');
            newdivcurrent_cell.setAttribute("colSpan","2");
            newdivcurrent_cell.innerHTML = "";
            newdivcurrent_row.appendChild(newdivcurrent_cell);

            newdivtablebody.appendChild(newdivcurrent_row);

            newdivcurrent_row=document.createElement("TR");
            newdivcurrent_cell=document.createElement("TD");
            newdivcurrent_cell.setAttribute("align",'right');
            newdivcurrent_cell.setAttribute("colSpan","2");
            newdivcurrent_cell.innerHTML = "<B>Desc:</B>";
            newdivcurrent_row.appendChild(newdivcurrent_cell);

            newdivcurrent_cell=document.createElement("TD");
            newdivcurrent_cell.id = "itemdesc"+divname+rownum;
            newdivcurrent_cell.setAttribute("align",'left');
            newdivcurrent_cell.setAttribute("colSpan","2");
            newdivcurrent_cell.innerHTML = "";
            newdivcurrent_row.appendChild(newdivcurrent_cell);

            newdivtablebody.appendChild(newdivcurrent_row);

            newdivcurrent_row=document.createElement("TR");
            newdivcurrent_row.id = "secondarysupplierrow"+divname+rownum;
            newdivcurrent_row.className = "bluenocur";

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "secondarysuppliertitle"+divname+rownum;
                newdivcurrent_cell.setAttribute("align",'right');
                newdivcurrent_cell.setAttribute("colSpan","2");
                newdivcurrent_cell.innerHTML = "<B>Secondary Supplier:</B>";
                newdivcurrent_row.appendChild(newdivcurrent_cell);

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "secondarysuppliercell"+divname+rownum;
                newdivcurrent_cell.setAttribute("align",'left');
                newdivcurrent_cell.setAttribute("colSpan","2");
                newdivcurrent_cell.innerHTML = "";
            newdivcurrent_row.appendChild(newdivcurrent_cell);

           newdivtablebody.appendChild(newdivcurrent_row);

            newdivcurrent_row=document.createElement("TR");
            newdivcurrent_cell=document.createElement("TD");
            newdivcurrent_cell.setAttribute("align",'left');
            newdivcurrent_cell.setAttribute("colSpan","2");
            newdivcurrent_cell.innerHTML = "&nbsp;";

            newdivcurrent_row.appendChild(newdivcurrent_cell);

				newdivcurrent_cell=document.createElement("TD");
            newdivcurrent_cell.setAttribute("align",'right');
            newdivcurrent_cell.setAttribute("colSpan","2");
				newdivcurrent_cell.innerHTML = "<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name='showlinehistory' OnClick=showPOLineHistory('"+rownum+"') value=\"Line History\">";
            newdivcurrent_row.appendChild(newdivcurrent_cell);

            newdivtablebody.appendChild(newdivcurrent_row);

            // creating all po line details
                newdivcurrent_row=document.createElement("TR");
                newdivcurrent_row.id = "detailrow1"+divname+rownum;
                newdivcurrent_row.className = "bluenocur";
                    newdivcurrent_cell=document.createElement("TH");
                    newdivcurrent_cell.id = "titleline1"+divname+rownum;
                    newdivcurrent_cell.setAttribute("width",'2%');
                    newdivcurrent_cell.setAttribute("align",'right');
                    newdivcurrent_cell.innerHTML = "<B>Select</B>";
                newdivcurrent_row.appendChild(newdivcurrent_cell);

                newdivcurrent_cell=document.createElement("TH");
                    newdivcurrent_cell.id = "titleline2"+divname+rownum;
                    newdivcurrent_cell.setAttribute("width",'5%');
                    newdivcurrent_cell.setAttribute("align",'right');
                    newdivcurrent_cell.innerHTML = "<B>Line</B>";
                newdivcurrent_row.appendChild(newdivcurrent_cell);

                newdivcurrent_cell=document.createElement("TH");
                    newdivcurrent_cell.id = "titleline3"+divname+rownum;
                    newdivcurrent_cell.setAttribute("width",'5%');
                    newdivcurrent_cell.setAttribute("align",'right');
                    newdivcurrent_cell.innerHTML = "<B>Item</B>";
                newdivcurrent_row.appendChild(newdivcurrent_cell);

                newdivcurrent_cell=document.createElement("TH");
                    newdivcurrent_cell.id = "titleline4"+divname+rownum;
                    newdivcurrent_cell.setAttribute("width",'10%');
                    newdivcurrent_cell.setAttribute("align",'right');
                    newdivcurrent_cell.innerHTML = "<B>Desc</B>";
                newdivcurrent_row.appendChild(newdivcurrent_cell);

            newdivtablebody.appendChild(newdivcurrent_row);

        var lineadded = 0;
        var Color1 ="white";

        for(j=0;j<nowofRows;j++)
        {
            var itemID = document.getElementById("lineitemid"+(j+1)+"");
            var itemType = document.getElementById("itemtype"+(j+1)+"");
            var validitem = document.getElementById("validitem"+(j+1)+"");
            var linestatus = document.getElementById("linestatus"+(j+1)+"");
			   var canassignaddcharge = document.getElementById("canassignaddcharge"+(j+1)+"");

            if ( (itemID.value.length > 0) && (canassignaddcharge.value == "Y") && (validitem.value == "Yes") && (linestatus.value != "Remove") )
            {
                lineadded ++;

                if (lineadded%2==0)
                {
                    Color1 ="whitenocur";
                }
                else
                {
                    Color1 ="bluenocur";
                }

            newdivcurrent_row=document.createElement("TR");
                newdivcurrent_row.id = "linechargerow"+divname+rownum+j;
                newdivcurrent_row.className = Color1;

                    newdivcurrent_cell=document.createElement("TD");
                    newdivcurrent_cell.id = "select"+divname+rownum+j;
                    newdivcurrent_cell.setAttribute("width",'2%');
                    newdivcurrent_cell.setAttribute("align",'center');
                    newdivcurrent_cell.innerHTML = "<INPUT TYPE=\"checkbox\" value=\"Yes\" NAME=\"addchargecheck"+divname+rownum+lineadded+"\" onClick=\"setlineUnconfirmed('"+rownum+"')\" disabled>";
                    newdivcurrent_row.appendChild(newdivcurrent_cell);

                    newdivcurrent_cell=document.createElement("TD");
                    newdivcurrent_cell.id = "line"+divname+rownum+j;
                    newdivcurrent_cell.setAttribute("width",'5%');
                    newdivcurrent_cell.setAttribute("align",'center');
                    newdivcurrent_cell.innerHTML = (j+1) + "<INPUT TYPE=\"hidden\" NAME=\"chargeAncnumber"+rownum+""+(j+1)+"\" VALUE=\"No\"><INPUT TYPE=\"hidden\" NAME=\"chargelinenumber"+lineadded+"\" VALUE=\""+(j+1)+"\">";
                newdivcurrent_row.appendChild(newdivcurrent_cell);

                newdivcurrent_cell=document.createElement("TD");
                    newdivcurrent_cell.id = "line"+divname+rownum+j;
                    newdivcurrent_cell.setAttribute("width",'5%');
                    newdivcurrent_cell.setAttribute("align",'center');
                    newdivcurrent_cell.innerHTML = itemID.value;
                newdivcurrent_row.appendChild(newdivcurrent_cell);

                var para = document.getElementById("itemdescdivrow"+(j+1)+(j+1)+"").cloneNode(true);
                newdivcurrent_row.appendChild(para);

            newdivtablebody.appendChild(newdivcurrent_row);
            }
        }
            newdivtable.appendChild(newdivtablebody);
            //alert(lineadded);
            newdivcurrent_row=document.createElement("TR");
                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.setAttribute("align",'left');
                newdivcurrent_cell.setAttribute("colSpan","4");
                newdivcurrent_cell.innerHTML = "<INPUT TYPE=\"hidden\" NAME=\"nooflinesinaddcharge"+rownum+"\" VALUE=\""+lineadded+"\">";
            newdivcurrent_row.appendChild(newdivcurrent_cell);
            newdivtablebody.appendChild(newdivcurrent_row);

            mybody.appendChild(newdivtable);

            //alert("Done Add Line Charge");
    }
}

function createViewContactLog(materialId, revisionDate) {
	var loc = "/tcmIS/catalog/contactlog.do?uAction=view&materialId="+materialId;
	loc += "&revisionDate=" + URLEncode(revisionDate);
    openWinGeneric(loc,"contactlog","800","700","yes","50","50","20","20","no");
}

function stopShowingWait() {
	// stub to prevent error when contact log closes
}

function addLineItem()
{
        // getting reference to my Line Table
        mytable = document.getElementById("linetable");

        var allTRs = mytable.getElementsByTagName("tr");
        var str = "# of table Rows: " + allTRs.length + "\n";
        var rownum = (allTRs.length)*1 + 1;

        var lineNumberc = 0;

        if (allTRs.length == 0)
        {
            lineNumberc = rownum;
        }
        else
        {
            var lineNumber = document.getElementById("row"+allTRs.length+"linenumber");
            lineNumberc = (Math.floor((lineNumber.value)*1) + 1);
        }

        var Color ="white";
		  var itemheColor = " ";

        if (rownum%2==0)
        {
            Color ="blue";
            itemheColor = "INVISIBLEHEADBLUE";
        }
        else
        {
            Color ="white";
            itemheColor = "INVISIBLEHEADWHITE";
        }

        Nototallines = document.getElementById("totallines");
        Nototallines.value = rownum;

        // get the reference for the body
        var mybody=document.getElementById("mainpara");

        // creates an element whose tag name is TBODY
        mytablebody = document.createElement("TBODY");
        // creating all cells
        // creates an element whose tag name is TR
            mycurrent_row=document.createElement("TR");
            mycurrent_row.onmouseup=function() {
            	var materials = document.getElementById("materialId"+(this.rowIndex+1)).innerHTML.split(",");
            	var menus = [];
            	for (m in materials) {
            		mrd = materials[m].split(" : ");
            		menu = "text="+materials[m]+";url=javascript:createViewContactLog('"+mrd[0]+"','"+mrd[1]+"');";
            		menus[menus.length] = menu;
            	}
            	if (menus.length > 0) {
	            	replaceMenu('contactLogMenu', menus);
	            	toggleContextMenu('contactLogMenu');
            	}
            };
            mycurrent_row.id = "row"+rownum;
            mycurrent_row.className = Color;

                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TD");
                mycurrent_cell.id = "linenumberammn"+rownum;
            	if (mycurrent_cell.attachEvent) {
            		mycurrent_cell.attachEvent("onclick", catchevent);
            		mycurrent_cell.attachEvent("onmouseover", catchoverevent);
            	} else {
            		mycurrent_cell.addEventListener("click", catchevent, false);
            		mycurrent_cell.addEventListener("mouseover", catchoverevent, false);
            	}
                mycurrent_cell.style.cursor = "hand";

                mycurrent_cell.setAttribute("width",'2%');
                mycurrent_cell.innerHTML = lineNumberc+"/0";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);
                
                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TD");
                mycurrent_cell.id = "ghscompliantcell"+rownum;
                mycurrent_cell.setAttribute("width",'5%');
                mycurrent_cell.innerHTML = "";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TD");
                mycurrent_cell.id = "cell2"+rownum;
                mycurrent_cell.setAttribute("width",'5%');
                mycurrent_cell.setAttribute("align",'left');
                name = "lineitemid" + (rownum);
                mycurrent_cell.innerHTML = "<input type=\"text\" size=\"5\" CLASS=\""+itemheColor+"\" name='"+name+"' onChange=\"invalidateItem('"+rownum+"')\" id='"+name+"' readonly><BUTTON name='button"+name+"' STYLE=\"display: none;\" OnClick=getItemDetail('"+rownum+"') type=button><IMG src=\"/images/search_small.gif\" alt=\"Item ID\"></BUTTON>";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TD");
                mycurrent_cell.id = "itemtypecell"+rownum;
                mycurrent_cell.setAttribute("width",'2%');
                mycurrent_cell.innerHTML = "";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TD");
                mycurrent_cell.id = "cell5"+rownum;
                mycurrent_cell.setAttribute("width",'4%');
                name = "dateneeded" + (rownum);
                mycurrent_cell.innerHTML = "<input type=\"text\" size=\"6\" CLASS=\""+itemheColor+"\" name='"+name+"' onChange=\"setlineUnconfirmed('"+rownum+"')\" id='"+name+"'  readonly><a href=\"javascript: void(0);\" ID=\"datelink"+name+"\" onClick=\"return getCalendar(document.purchaseorder."+name+");\" STYLE=\"display: none;\">&diams;</a> ";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

					 // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TD");
                mycurrent_cell.id = "cell12"+rownum;
                mycurrent_cell.setAttribute("width",'6.5%');
                name = "projsuppshipdate" + (rownum);
                mycurrent_cell.innerHTML = "<input type=\"text\" size=\"6\" CLASS=\""+itemheColor+"\" name='"+name+"' MAXLENGTH=\"10\" onChange=\"setlineUnconfirmed('"+rownum+"')\" id='"+name+"'><FONT SIZE=\"4\"><a href=\"javascript: void(0);\" ID=\"datelink"+name+"\" getCalendar(document.purchaseorder."+name+");\" STYLE=\"display: none;\">&diams;</a></FONT>";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TD");
                mycurrent_cell.id = "cell6"+rownum;
                mycurrent_cell.setAttribute("width",'7%');
                name = "datepromised" + (rownum);
                mycurrent_cell.innerHTML = "<input type=\"text\" size=\"6\" CLASS=\""+itemheColor+"\" name='"+name+"' onChange=\"setlineUnconfirmed('"+rownum+"')\" id='"+name+"' readonly><a href=\"javascript: void(0);\" ID=\"datelink"+name+"\" onClick=\"return getCalendar(document.purchaseorder."+name+");\" STYLE=\"display: none;\">&diams;</a>";

                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TD");
                mycurrent_cell.id = "cell7"+rownum;
                mycurrent_cell.setAttribute("width",'4%');
                mycurrent_cell.setAttribute("align",'right');
                name = "lineqty" + (rownum);
                mycurrent_cell.innerHTML = "<input type=\"text\" size=\"2\" CLASS=\""+itemheColor+"\" name='"+name+"' onChange=\"changeTotalPrice('"+rownum+"')\" id='"+name+"' readonly>";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TD");
                mycurrent_cell.id = "pakgingcell"+rownum;
                mycurrent_cell.setAttribute("width",'17%');
                mycurrent_cell.innerHTML = "";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TD");
                mycurrent_cell.id = "cell8"+rownum;
                mycurrent_cell.setAttribute("width",'5%');
                mycurrent_cell.setAttribute("align",'right');
                name = "lineunitprice" + (rownum);
                mycurrent_cell.innerHTML = "<input type=\"text\" size=\"4\" CLASS=\""+itemheColor+"\" name='"+name+"' onChange=\"changeTotalPrice('"+rownum+"')\" id='"+name+"' readonly>";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TD");
                mycurrent_cell.id = "extpricecell"+rownum;
                mycurrent_cell.setAttribute("width",'7%');
                mycurrent_cell.setAttribute("align",'right');
                //mycurrent_cell.setAttribute("align",'right');
                mycurrent_cell.innerHTML = "0";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TD");
                mycurrent_cell.id = "qtyreceivedcell"+rownum;
                mycurrent_cell.setAttribute("width",'3%');
                mycurrent_cell.innerHTML = "";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TD");
                mycurrent_cell.id = "qtyvouchered"+rownum;
                mycurrent_cell.setAttribute("width",'3%');
                mycurrent_cell.innerHTML = "";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

					 // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TD");
                mycurrent_cell.id = "qtyreturned"+rownum;
                mycurrent_cell.setAttribute("width",'3%');
                mycurrent_cell.innerHTML = "";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TD");
                mycurrent_cell.id = "linestatustext"+rownum;
                mycurrent_cell.setAttribute("width",'4%');
                mycurrent_cell.innerHTML = "Draft";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);
                
                mycurrent_cell=document.createElement("TD");
                mycurrent_cell.setAttribute("width",'0');
                mycurrent_cell.style.display = "none";
                mycurrent_cell.id = "materialId"+rownum;
                mycurrent_cell.innerHTML = "";
                mycurrent_row.appendChild(mycurrent_cell);

            // appends the row TR into TBODY
            mytablebody.appendChild(mycurrent_row);

        // appends TBODY into TABLE
        mytable.appendChild(mytablebody);

        // creates an element whose tag name is DIV
        var divname =  "divrow"+rownum;

        newdivtable = document.createElement("TABLE");
        newdivtable.setAttribute("width",'100%');
        newdivtable.setAttribute("CELLSPACING",'1');
        newdivtable.setAttribute("CELLPADDING",'3');
        newdivtable.id = divname;
        newdivtable.className =  "displaynone";

        newdivtablebody = document.createElement("TBODY");
        // creating all po line details

         newdivcurrent_row=document.createElement("TR");
            newdivcurrent_row.id = "lastconfirmedrow"+divname+rownum;
            newdivcurrent_row.className = "whitenocur";

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "lastconfirmedtitle"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'10%');
                newdivcurrent_cell.setAttribute("align",'right');
                newdivcurrent_cell.innerHTML = "<B>Last Confirmed: </B>";
                newdivcurrent_row.appendChild(newdivcurrent_cell);

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "lastconfirmedcell"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'90%');
                newdivcurrent_cell.setAttribute("align",'left');
                newdivcurrent_cell.innerHTML = "";
            newdivcurrent_row.appendChild(newdivcurrent_cell);

        newdivtablebody.appendChild(newdivcurrent_row);

            newdivcurrent_row=document.createElement("TR");
            newdivcurrent_row.id = "detailrow1"+divname+rownum;
            newdivcurrent_row.className = "bluenocur";
                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "titleline1"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'10%');
                newdivcurrent_cell.setAttribute("align",'right');
                newdivcurrent_cell.innerHTML = "<B>Line:</B>";
            newdivcurrent_row.appendChild(newdivcurrent_cell);

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "row1detail"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'90%');
                newdivcurrent_cell.setAttribute("align",'left');

                var innHtmlline =  "<INPUT CLASS=\"INVISIBLEHEADBLUE\" TYPE=\"text\" NAME=\"detaillinenumber"+rownum+"\" ID=\"detaillinenumber"+rownum+"\" value=\""+lineNumberc+"\" size=\"5\" readonly>&nbsp;&nbsp;&nbsp;&nbsp;<B>MPN:</B><input type=\"text\" size=\"12\" CLASS=\"INVISIBLEHEADBLUE\" name='mpn"+rownum+"' id='mpn"+rownum+"' onChange=\"setlineUnconfirmed('"+rownum+"')\" readonly> ";
                innHtmlline =  innHtmlline +"&nbsp;&nbsp;&nbsp;&nbsp;<B>Supplier PN:</B><input type=\"text\" size=\"12\" MAXLENGTH=\"30\" CLASS=\"INVISIBLEHEADBLUE\" name='supplierpn"+rownum+"' id='supplierpn"+rownum+"' onChange=\"setlineUnconfirmed('"+rownum+"')\" readonly> ";
                innHtmlline =  innHtmlline +"&nbsp;&nbsp;&nbsp;&nbsp;<B>DPAS:</B><input type=\"text\" size=\"5\" MAXLENGTH=\"4\" CLASS=\"INVISIBLEHEADBLUE\" name='dpas"+rownum+"' id='dpas"+rownum+"' onChange=\"setlineUnconfirmed('"+rownum+"')\" readonly> ";
                innHtmlline =  innHtmlline +"&nbsp;&nbsp;&nbsp;&nbsp;<B>Shelf Life:</B><input type=\"text\" size=\"2\" CLASS=\"INVISIBLEHEADBLUE\" name='shelflife"+rownum+"' id='shelflife"+rownum+"' onChange=\"setlineUnconfirmed('"+rownum+"')\" readonly>&nbsp;<B>%</B>";
                innHtmlline =  innHtmlline +"&nbsp;&nbsp;<span name='shelflifebasis"+rownum+"' id='shelflifebasis"+rownum+"'></span><br>";
                innHtmlline =  innHtmlline +"&nbsp;&nbsp;&nbsp;&nbsp;<INPUT TYPE=\"button\" style=\"width:90px;\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name='showlinehistory' OnClick=showPOLineHistory('"+rownum+"') value=\"Line History\"> ";
                innHtmlline =  innHtmlline +"&nbsp;&nbsp;&nbsp;&nbsp;<INPUT TYPE=\"hidden\" name='expediteNotes"+rownum+"' id='expediteNotes"+rownum+"' style=\"display:none\" value=\"\"> ";
                innHtmlline =  innHtmlline +"&nbsp;&nbsp;&nbsp;&nbsp;<INPUT TYPE=\"button\" style=\"width:150px;\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name='showexpeditenoteshistory' OnClick=showexpeditenotes('"+rownum+"') value=\"Line Expediting History\"> ";
                innHtmlline =  innHtmlline +"&nbsp;&nbsp;<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name='showitemexpeditenoteshistory' OnClick=showItemexpeditenotes('"+rownum+"') style=\"width:150px;\" value=\"Item Expediting History\"> ";
                innHtmlline =  innHtmlline +"&nbsp;&nbsp;<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name='showsourcinginfo' OnClick=showSoucingInfo('"+rownum+"') style=\"width:150px;\" value=\"View/Edit Sourcing Info\"> ";
                innHtmlline =  innHtmlline +"<input type=\"hidden\" name='slHidden"+rownum+"' id='slHidden"+rownum+"'\">";     

                newdivcurrent_cell.innerHTML = innHtmlline;
            newdivcurrent_row.appendChild(newdivcurrent_cell);
        newdivtablebody.appendChild(newdivcurrent_row);

	newdivcurrent_row=document.createElement("TR");
            newdivcurrent_row.id = "supplierdetail"+divname+rownum;
            newdivcurrent_row.className = "whitenocur";
                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "supplierdetailline"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'10%');
                newdivcurrent_cell.setAttribute("align",'right');
                newdivcurrent_cell.innerHTML = "<B></B>";
            newdivcurrent_row.appendChild(newdivcurrent_cell);

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "supplier1detail"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'90%');
                newdivcurrent_cell.setAttribute("align",'left');

                var innHtmlline =  "<input type=\"hidden\" size=\"5\" CLASS=\"INVISIBLEHEADWHITE\" name='supplierqty"+rownum+"' id='supplierqty"+rownum+"' onChange=\"changeSuppTotalPrice('"+rownum+"')\" readonly> ";
                innHtmlline =  innHtmlline +"<input type=\"hidden\" size=\"20\" MAXLENGTH=\"30\" CLASS=\"INVISIBLEHEADWHITE\" name='supplierpkg"+rownum+"' id='supplierpkg"+rownum+"' onChange=\"setlineUnconfirmed('"+rownum+"')\" readonly> ";
                innHtmlline =  innHtmlline +"<input type=\"hidden\" size=\"5\" CLASS=\"INVISIBLEHEADWHITE\" name='supplierunitprice"+rownum+"' id='supplierunitprice"+rownum+"' onChange=\"changeSuppTotalPrice('"+rownum+"')\" readonly> ";
					      innHtmlline =  innHtmlline +"<input type=\"hidden\" size=\"8\" CLASS=\"INVISIBLEHEADWHITE\" name='supplierextprice"+rownum+"' id='supplierextprice"+rownum+"' readonly> ";
                innHtmlline =  innHtmlline +"<B>Supplier Ship From Location:</B><INPUT TYPE=\"text\" name=\"shipFromLocationId"+rownum+"\" id=\"shipFromLocationId"+rownum+"\" VALUE=\"\" readonly>";
                innHtmlline =  innHtmlline +"&nbsp;&nbsp;&nbsp;&nbsp;<INPUT TYPE=\"hidden\" name='enterSoleSource"+rownum+"' id='enterSoleSource"+rownum+"' style=\"display:none\" value=\"\"> ";

                newdivcurrent_cell.innerHTML = innHtmlline;
            newdivcurrent_row.appendChild(newdivcurrent_cell);
        newdivtablebody.appendChild(newdivcurrent_row);

        newdivcurrent_row=document.createElement("TR");
            newdivcurrent_row.id = "detailrow2"+divname+rownum;
            newdivcurrent_row.className = "bluenocur";

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "titleline2"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'10%');
                newdivcurrent_cell.setAttribute("align",'right');
                newdivcurrent_cell.innerHTML = "<B>Desc: </B>";
                newdivcurrent_row.appendChild(newdivcurrent_cell);

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "itemdesc"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'90%');
                newdivcurrent_cell.setAttribute("align",'left');
                newdivcurrent_cell.innerHTML = "";
            newdivcurrent_row.appendChild(newdivcurrent_cell);

        newdivtablebody.appendChild(newdivcurrent_row);

	         newdivcurrent_row=document.createElement("TR");
            newdivcurrent_row.id = "secondarysupplierrow"+divname+rownum;
            newdivcurrent_row.className = "whitenocur";

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "secondarysuppliertitle"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'10%');
                newdivcurrent_cell.setAttribute("align",'right');
                newdivcurrent_cell.innerHTML = "<B>Secondary Supplier:</B>";
                newdivcurrent_row.appendChild(newdivcurrent_cell);

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "secondarysuppliercell"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'90%');
                newdivcurrent_cell.setAttribute("align",'left');
                newdivcurrent_cell.innerHTML = "";
            newdivcurrent_row.appendChild(newdivcurrent_cell);

        newdivtablebody.appendChild(newdivcurrent_row);

		newdivcurrent_row=document.createElement("TR");
            newdivcurrent_row.id = "detailrow2"+divname+rownum;
            newdivcurrent_row.className = "bluenocur";

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "itemverbytitle"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'10%');
                newdivcurrent_cell.setAttribute("align",'right');
                newdivcurrent_cell.innerHTML = "<B>Verified By:</B>";
                newdivcurrent_row.appendChild(newdivcurrent_cell);

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "itemverifiedby"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'90%');
                newdivcurrent_cell.setAttribute("align",'left');
                newdivcurrent_cell.innerHTML = "";
            newdivcurrent_row.appendChild(newdivcurrent_cell);

        newdivtablebody.appendChild(newdivcurrent_row);

		  newdivcurrent_row=document.createElement("TR");
            newdivcurrent_row.id = "lookahead"+divname+rownum;
            newdivcurrent_row.className = "whitenocur";

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "lookaheadtitle"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'10%');
                newdivcurrent_cell.setAttribute("align",'right');
                newdivcurrent_cell.setAttribute("vAlign",'top');
                newdivcurrent_cell.innerHTML = "<B>Look Ahead: </B> <A HREF=\"javascript:getlookaheads('"+rownum+"')\" STYLE=\"color:#0000ff;cursor:hand\"><IMG ID=\"lookaheadimg"+rownum+"\" src=\"/images/plus.jpg\" alt=\"Specs\"></A>";
                newdivcurrent_row.appendChild(newdivcurrent_cell);

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "lookaheaddetail"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'90%');
                newdivcurrent_cell.setAttribute("align",'left');
                newdivcurrent_cell.innerHTML = "";
            newdivcurrent_row.appendChild(newdivcurrent_cell);
        newdivtablebody.appendChild(newdivcurrent_row);

        newdivcurrent_row=document.createElement("TR");
            newdivcurrent_row.id = "detailrow3"+divname+rownum;
            newdivcurrent_row.className = "bluenocur";

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "titleline3"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'10%');
                newdivcurrent_cell.setAttribute("align",'right');
                newdivcurrent_cell.innerHTML = "<B>Line Notes: </B>";
                newdivcurrent_row.appendChild(newdivcurrent_cell);

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "row3detail"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'90%');
                newdivcurrent_cell.setAttribute("align",'left');
                newdivcurrent_cell.setAttribute("vAlign",'top');
                innnoteHtmlline = "<TEXTAREA CLASS=\"INVISIBLEHEADWHITE\" name=\"linenotes"+divname+rownum+"\" rows=\"5\" cols=\"130\" onChange=\"setlineUnconfirmed('"+rownum+"')\" readonly></TEXTAREA>";
                innnoteHtmlline =  innnoteHtmlline +"&nbsp;&nbsp;&nbsp;&nbsp;";
                newdivcurrent_cell.innerHTML = innnoteHtmlline;

            newdivcurrent_row.appendChild(newdivcurrent_cell);
        newdivtablebody.appendChild(newdivcurrent_row);


				newdivcurrent_row=document.createElement("TR");
            newdivcurrent_row.id = "detailrowdelnotes"+divname+rownum;
            newdivcurrent_row.className = "whitenocur";

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "titleline3"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'10%');
                newdivcurrent_cell.setAttribute("align",'right');
                newdivcurrent_cell.innerHTML = "<B>Delivery Notes: </B>";
                newdivcurrent_row.appendChild(newdivcurrent_cell);

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "row3detail"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'90%');
                newdivcurrent_cell.setAttribute("align",'left');
                newdivcurrent_cell.setAttribute("vAlign",'top');
                innnoteHtmlline = "<TEXTAREA name=\"deliverylinenotes"+divname+rownum+"\" rows=\"2\" cols=\"130\" onChange=\"setlineUnconfirmed('"+rownum+"')\" readonly></TEXTAREA>";
                innnoteHtmlline =  innnoteHtmlline +"&nbsp;&nbsp;&nbsp;&nbsp;";
                newdivcurrent_cell.innerHTML = innnoteHtmlline;

            newdivcurrent_row.appendChild(newdivcurrent_cell);
        newdivtablebody.appendChild(newdivcurrent_row);

            newdivcurrent_row=document.createElement("TR");
            newdivcurrent_row.id = "detailrow4"+divname+rownum;
            newdivcurrent_row.className = "bluenocur";

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "titleline4"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'10%');
                newdivcurrent_cell.setAttribute("align",'right');
                newdivcurrent_cell.setAttribute("vAlign",'top');
                newdivcurrent_cell.innerHTML = "<B>Item Notes: </B> <A HREF=\"javascript:getItemNotes('"+rownum+"')\" STYLE=\"color:#0000ff;cursor:hand\"><IMG ID=\"itemnotesimage"+rownum+"\" src=\"/images/plus.jpg\" alt=\"Item Notes\"></A>";
                newdivcurrent_row.appendChild(newdivcurrent_cell);

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "row4detail"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'90%');
                newdivcurrent_cell.setAttribute("align",'left');
                newdivcurrent_cell.innerHTML = "";
            newdivcurrent_row.appendChild(newdivcurrent_cell);
        newdivtablebody.appendChild(newdivcurrent_row);

newdivcurrent_row=document.createElement("TR");
            newdivcurrent_row.id = "detailrow4"+divname+rownum;
            newdivcurrent_row.className = "whitenocur";

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "titleline4"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'10%');
                newdivcurrent_cell.setAttribute("align",'right');
                newdivcurrent_cell.setAttribute("vAlign",'top');
                newdivcurrent_cell.innerHTML = "<B>Part Notes: </B> <A HREF=\"javascript:getpartNotes('"+rownum+"')\" STYLE=\"color:#0000ff;cursor:hand\"><IMG ID=\"partnotesimage"+rownum+"\" src=\"/images/plus.jpg\" alt=\"Part Notes\"></A>";
                newdivcurrent_row.appendChild(newdivcurrent_cell);

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "partnotes"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'90%');
                newdivcurrent_cell.setAttribute("align",'left');
                newdivcurrent_cell.innerHTML = "";
            newdivcurrent_row.appendChild(newdivcurrent_cell);
        newdivtablebody.appendChild(newdivcurrent_row);

        newdivcurrent_row=document.createElement("TR");
            newdivcurrent_row.id = "detailrow5"+divname+rownum;
            newdivcurrent_row.className = "bluenocur";

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "titleline5"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'10%');
                newdivcurrent_cell.setAttribute("align",'right');
                newdivcurrent_cell.setAttribute("vAlign",'top');
                newdivcurrent_cell.innerHTML = "<B>Associated PRs: </B> <A HREF=\"javascript:getAssociatedPrs('"+rownum+"')\" STYLE=\"color:#0000ff;cursor:hand\"><IMG ID=\"associatedprimg"+rownum+"\" src=\"/images/plus.jpg\" alt=\"Associated PRS\"></A><BR><INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name='editassociatePr00"+rownum+"' OnClick=edditAssociatedPrs('"+rownum+"') value=\"Edit\" STYLE=\"display: none;\">";
                newdivcurrent_row.appendChild(newdivcurrent_cell);

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "row5detail"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'90%');
                newdivcurrent_cell.setAttribute("align",'left');
                newdivcurrent_cell.innerHTML = "";
            newdivcurrent_row.appendChild(newdivcurrent_cell);
        newdivtablebody.appendChild(newdivcurrent_row);

        newdivcurrent_row=document.createElement("TR");
            newdivcurrent_row.id = "detailrow6"+divname+rownum;
            newdivcurrent_row.className = "whitenocur";

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "titleline6"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'10%');
                newdivcurrent_cell.setAttribute("align",'right');
                newdivcurrent_cell.setAttribute("vAlign",'top');
                newdivcurrent_cell.innerHTML = "<B>Specs: </B> <A HREF=\"javascript:getspecs('"+rownum+"')\" STYLE=\"color:#0000ff;cursor:hand\"><IMG ID=\"specimg"+rownum+"\" src=\"/images/plus.jpg\" alt=\"Specs\"></A><BR><A HREF=\"javascript:showlegend()\" STYLE=\"color:#0000ff;cursor:hand\">Legend</A>";
                newdivcurrent_row.appendChild(newdivcurrent_cell);

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "row6detail"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'90%');
                newdivcurrent_cell.setAttribute("align",'left');
                newdivcurrent_cell.innerHTML = "";
            newdivcurrent_row.appendChild(newdivcurrent_cell);
        newdivtablebody.appendChild(newdivcurrent_row);

        newdivcurrent_row=document.createElement("TR");
            newdivcurrent_row.id = "detailrow7"+divname+rownum;
            newdivcurrent_row.className = "bluenocur";

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "titleline7"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'10%');
                newdivcurrent_cell.setAttribute("align",'right');
                newdivcurrent_cell.setAttribute("vAlign",'top');
                newdivcurrent_cell.innerHTML = "<B>Flowdowns: </B> <A HREF=\"javascript:getflowdowns('"+rownum+"')\" STYLE=\"color:#0000ff;cursor:hand\"><IMG ID=\"flowdownimg"+rownum+"\" src=\"/images/plus.jpg\" alt=\"Flow Down\"></A>";
                newdivcurrent_row.appendChild(newdivcurrent_cell);

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "row7detail"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'90%');
                newdivcurrent_cell.setAttribute("align",'left');
                newdivcurrent_cell.innerHTML = "";
            newdivcurrent_row.appendChild(newdivcurrent_cell);
        newdivtablebody.appendChild(newdivcurrent_row);

        newdivcurrent_row=document.createElement("TR");
            newdivcurrent_row.id = "detailrowmsds"+divname+rownum;
            newdivcurrent_row.className = "whitenocur";

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "titlelinemsds"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'10%');
                newdivcurrent_cell.setAttribute("align",'right');
                newdivcurrent_cell.setAttribute("vAlign",'top');
                newdivcurrent_cell.innerHTML = "<B>MSDS: </B> <A HREF=\"javascript:getmsdsrevdate('"+rownum+"')\" STYLE=\"color:#0000ff;cursor:hand\"><IMG ID=\"msdsimg"+rownum+"\" src=\"/images/plus.jpg\" alt=\"Specs\"></A>";
                newdivcurrent_row.appendChild(newdivcurrent_cell);

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "rowmsdsdetail"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'90%');
                newdivcurrent_cell.setAttribute("align",'left');
                newdivcurrent_cell.innerHTML = "";
            newdivcurrent_row.appendChild(newdivcurrent_cell);
        newdivtablebody.appendChild(newdivcurrent_row);

        newdivcurrent_row=document.createElement("TR");
            newdivcurrent_row.id = "detailrow8"+divname+rownum;
            newdivcurrent_row.className = "bluenocur";

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "titleline8"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'10%');
                newdivcurrent_cell.setAttribute("align",'right');
                newdivcurrent_cell.setAttribute("vAlign",'top');
                newdivcurrent_cell.innerHTML = "<B>TCM Buys: </B> <A HREF=\"javascript:gettcmBuys('"+rownum+"')\" STYLE=\"color:#0000ff;cursor:hand\"><IMG ID=\"tcmbuyimg"+rownum+"\" src=\"/images/plus.jpg\" alt=\"TCM Buys\"></A>";
                newdivcurrent_row.appendChild(newdivcurrent_cell);

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "row8detail"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'90%');
                newdivcurrent_cell.setAttribute("align",'left');
                newdivcurrent_cell.innerHTML = "";
            newdivcurrent_row.appendChild(newdivcurrent_cell);
        newdivtablebody.appendChild(newdivcurrent_row);

        newdivcurrent_row=document.createElement("TR");
            newdivcurrent_row.id = "detailrow9"+divname+rownum;
            newdivcurrent_row.className = "whitenocur";

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "titleline9"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'10%');
                newdivcurrent_cell.setAttribute("align",'right');
                newdivcurrent_cell.setAttribute("vAlign",'top');
                newdivcurrent_cell.innerHTML = "<B>Client Buys: </B> <A HREF=\"javascript:getclientBuys('"+rownum+"')\" STYLE=\"color:#0000ff;cursor:hand\"><IMG ID=\"clientbuys"+rownum+"\" src=\"/images/plus.jpg\" alt=\"Client Buys\"></A>";
                newdivcurrent_row.appendChild(newdivcurrent_cell);

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "row9detail"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'90%');
                newdivcurrent_cell.setAttribute("align",'left');
                newdivcurrent_cell.innerHTML = "";
            newdivcurrent_row.appendChild(newdivcurrent_cell);
        newdivtablebody.appendChild(newdivcurrent_row);

        newdivcurrent_row=document.createElement("TR");
            newdivcurrent_row.id = "detailrow10"+divname+rownum;
            newdivcurrent_row.className = "bluenocur";

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "titleline10"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'10%');
                newdivcurrent_cell.setAttribute("align",'right');
                newdivcurrent_cell.innerHTML = "";
                newdivcurrent_row.appendChild(newdivcurrent_cell);

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "row10detail"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'90%');
                newdivcurrent_cell.setAttribute("align",'left');

                var invisibleElements = "<INPUT TYPE=\"hidden\" NAME=\"linestatus"+rownum+"\" VALUE=\"Draft\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"row"+rownum+"linenumber\" VALUE=\""+lineNumberc+"\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"row"+rownum+"row\" VALUE=\""+rownum+"\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"itemnotestatus"+rownum+"\" VALUE=\"No\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"partnotestatus"+rownum+"\" VALUE=\"No\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"linechange"+rownum+"\" VALUE=\"Yes\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"originallinestatus"+rownum+"\" VALUE=\"Draft\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"ammendment"+rownum+"\" VALUE=\"0\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"ammendmentcomments"+rownum+"\" VALUE=\"\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"itemtype"+rownum+"\" VALUE=\"\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"associatedprsstatus"+rownum+"\" VALUE=\"No\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"nofassociatedprs"+rownum+"\" VALUE=\"0\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"totalofassociatedprs"+rownum+"\" VALUE=\"0\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"specstatus"+rownum+"\" VALUE=\"No\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"msdsstatus"+rownum+"\" VALUE=\"No\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"validspec"+rownum+"\" VALUE=\"No\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"flowdownstatus"+rownum+"\" VALUE=\"No\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"validflowdown"+rownum+"\" VALUE=\"No\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"tcmbuystatus"+rownum+"\" VALUE=\"No\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"clientbuystatus"+rownum+"\" VALUE=\"No\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"validitem"+rownum+"\" VALUE=\"No\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"qtyreceived"+rownum+"\" VALUE=\"0\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"linetotalprice"+rownum+"\" VALUE=\"0\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"prshipto"+rownum+"\" VALUE=\"\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"noofspecs"+divname+""+rownum+"\" VALUE=\"0\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"noofflowdowns"+divname+""+rownum+"\" VALUE=\"0\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"nooflinesinaddcharge"+rownum+"\" VALUE=\"0\">";
				invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"nooflookaheads"+rownum+"\" VALUE=\"0\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"lookaheadstatus"+rownum+"\" VALUE=\"No\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"validlookahead"+rownum+"\" VALUE=\"No\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"lineArchived"+rownum+"\" VALUE=\"\">";
				invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"canassignaddcharge"+rownum+"\" VALUE=\"N\">";
				invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"purchasingUnitsPerItem"+rownum+"\" VALUE=\"\">";
  				invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"supplierSalesOrderNo"+rownum+"\" VALUE=\"\">";
				invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"everConfirmed"+rownum+"\" ID=\"everConfirmed"+rownum+"\" VALUE=\"\">";
				invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"dataTransferStatusClosed" + rownum + "\" VALUE=\"0\">";
				
                newdivcurrent_cell.innerHTML = invisibleElements;
            newdivcurrent_row.appendChild(newdivcurrent_cell);
        newdivtablebody.appendChild(newdivcurrent_row);


        newdivtable.appendChild(newdivtablebody);

        mybody.appendChild(newdivtable);
}

function catchoverevent()
{
eventSrcID=(event.srcElement)?event.srcElement.id:'undefined';
eventtype=event.type;
status=eventSrcID+' has received a '+eventtype+' event.';
parentrow = event.srcElement.parentNode;
}

function catchevent()
{
    eventSrcID=(event.srcElement)?event.srcElement.id:'undefined';
    eventtype=event.type;
    //status=eventSrcID+' has received a '+eventtype+' event.';

    parentrow = event.srcElement.parentNode;

    var str = "Parent Row is " + parentrow.id + "\n";
    //alert(str);

    var url = /\d/;
    var result = selected_rowid.match(url);
    var numberline = result[0];

    selectedItemRow = document.getElementById(""+selected_rowid+"row");
    parentItemRow = document.getElementById(""+parentrow.id+"row");

    selectedRowStatus = document.getElementById("linestatus"+selectedItemRow.value+"");
    parentRowStatus = document.getElementById("linestatus"+parentItemRow.value+"");

    //alert(selectedRowStatus.value);
    //alert(parentRowStatus.value);

    var Color ="white";
    if ( ( (selectedItemRow.value)*1 )%2==0)
    {
        Color ="#E6E8FA";
    }
    else
    {
        Color ="#FFFFFF";
    }

    if (selectedRowStatus.value != "Remove")
    {
    selectedRow = document.getElementById(selected_rowid);
    selectedRow.style.backgroundColor = Color;
    }

    if (parentRowStatus.value != "Remove")
    {
    parentrow.style.backgroundColor = "#8a8aff";
    }

    //parentrow.style.display = "none"

    var divrowid = "div"+selected_rowid;
    var target1 = document.getElementById(divrowid);
    target1.style.display = "none";

    if (parentRowStatus.value != "Remove")
    {
    var divrowid = "div"+parentrow.id;
    var target1 = document.getElementById(divrowid);
    target1.style.display = "block";
    }

    selected_rowid = parentrow.id;
}