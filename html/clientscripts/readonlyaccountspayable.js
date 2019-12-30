var submitcount=0;
var updatecount=0;
var max = 20 //maximum rows
var color = "#0000ff";
var selected_row = 0;
var selected_rowid = "row1";

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


function setvoucherline(rownum,mainrow)
{

}

function setvouchercreditline(rownum,mainrow)
{

}


function checkreceiptextprice(rownum,mainrow)
{

}

function checkcreditmemoqty(rownum,mainrow)
{

}


function checkCreditCorrectiontotals(name, entered)
{

}

function checkInvocietotals(name, entered)
{

}

function checkInvocieValues(name, entered)
{

}

function actionValue(name)
{
   var actvalue = name;
   window.document.purchaseorder.Action.value = actvalue;

	var result ;
   var finalMsgt;
   var allClear = 0;
   var sameShipto = 0;
   var shipTomsg;

	var testreceipt  =  document.getElementById("vocreceiptid11");
	//alert(testreceipt.value);

   finalMsgt = "Please enter valid values for: \n\n";

	var povalue  =  document.getElementById("po");
	if (povalue.value.length > 0)
	{
	    var validpo  =  document.getElementById("validpo");
	    if (validpo.value == "No")
	    {
	        finalMsgt = finalMsgt + " PO.\n" ;
	        allClear = 1;
	    }
	}

    if (allClear < 1)
    {
        result = true;
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

        updatecount++;
        return result;
    }
    else
    {
        alert(finalMsgt);
        result = false;
        return result;
    }
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
            newdivcurrent_cell=document.createElement("TD");
            newdivcurrent_cell.setAttribute("align",'left');
            newdivcurrent_cell.setAttribute("colSpan","2");
            newdivcurrent_cell.innerHTML = "<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name='reflinechgs' OnClick=refreshAddCharges('"+rownum+"') value=\"Refresh\">";
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

        if ( (itemID.value.length > 0) && (itemType.value == "MA") && (validitem.value == "Yes") && (linestatus.value != "Remove") )
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
                newdivcurrent_cell.innerHTML = "<INPUT TYPE=\"checkbox\" value=\"Yes\" NAME=\"addchargecheck"+divname+rownum+lineadded+"\" onClick=\"setlineUnconfirmed('"+rownum+"')\">";
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
            newdivcurrent_cell.innerHTML = "<INPUT TYPE=\"hidden\" NAME=\"linechange"+rownum+"\" VALUE=\""+setUnconfirmedFlag+"\"><INPUT TYPE=\"hidden\" NAME=\"nooflinesinaddcharge"+rownum+"\" VALUE=\""+lineadded+"\">";
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
        if (rownum%2==0)
        {
            Color ="blue";
        }
        else
        {
            Color ="white";
        }

        var lineNumberc = 0;

        if (allTRs.length == 0)
        {
            lineNumberc = rownum;
        }
        else
        {
            var lineNumber = document.getElementById("row"+allTRs.length+"linenumber");
            lineNumberc = ((lineNumber.value)*1 + 1);
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
                mycurrent_row.id = "row"+rownum;
                mycurrent_row.className = Color;
               // mycurrent_row.attachEvent("onclick", catchevent);
               // mycurrent_row.attachEvent("mouseover", catchoverevent);

                    // creates an element whose tag name is TD
                    mycurrent_cell=document.createElement("TD");
                    mycurrent_cell.id = "cell1"+rownum;
                    mycurrent_cell.attachEvent("onclick", catchevent);
                    mycurrent_cell.attachEvent("mouseover", catchoverevent);
                    mycurrent_cell.style.cursor = "hand";

                    mycurrent_cell.setAttribute("width",'30');
                    mycurrent_cell.innerHTML = lineNumberc+"/0";
                    // appends the cell TD into the row TR
                    mycurrent_row.appendChild(mycurrent_cell);

                    // creates an element whose tag name is TD
                    mycurrent_cell=document.createElement("TD");
                    mycurrent_cell.id = "cell2"+rownum;
                    mycurrent_cell.setAttribute("width",'92');
                    mycurrent_cell.setAttribute("align",'left');
                    name = "lineitemid" + (rownum);
                    mycurrent_cell.innerHTML = "<INPUT TYPE=\"hidden\" NAME=\"itemtype"+rownum+"\" VALUE=\"\"><INPUT TYPE=\"hidden\" NAME=\"validitem"+rownum+"\" VALUE=\"No\"><input type=\"text\" size=\"5\" CLASS=\"INVINVALIDTEXT\" onChange=\"invalidateItem('"+rownum+"')\" name='"+name+"' id='"+name+"'><BUTTON name='button"+name+"' OnClick=getChargeItemDetail('"+rownum+"') type=button><IMG src=\"/images/search_small.gif\" alt=\"Item ID\"></BUTTON>";
                    // appends the cell TD into the row TR
                    mycurrent_row.appendChild(mycurrent_cell);

                    // creates an element whose tag name is TD
                    mycurrent_cell=document.createElement("TD");
                    mycurrent_cell.id = "cell3"+rownum;
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
                    mycurrent_cell.innerHTML = "<input type=\"text\" size=\"2\" CLASS=\"HEADER\" onChange=\"changeTotalPrice('"+rownum+"')\" name='"+name+"' id='"+name+"'>";
                    // appends the cell TD into the row TR
                    mycurrent_row.appendChild(mycurrent_cell);

                    // creates an element whose tag name is TD
                    mycurrent_cell=document.createElement("TD");
                    mycurrent_cell.id = "cell4"+rownum;
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
                    mycurrent_cell.innerHTML = "<input type=\"text\" size=\"4\" CLASS=\"HEADER\" onChange=\"changeTotalPrice('"+rownum+"')\" name='"+name+"' id='"+name+"'>";
                    // appends the cell TD into the row TR
                    mycurrent_row.appendChild(mycurrent_cell);

                    // creates an element whose tag name is TD
                    mycurrent_cell=document.createElement("TD");
                    mycurrent_cell.id = "cell9"+rownum;
                    mycurrent_cell.setAttribute("width",'90');
                    mycurrent_cell.setAttribute("align",'right');
                    mycurrent_cell.innerHTML = "";
                    // appends the cell TD into the row TR
                    mycurrent_row.appendChild(mycurrent_cell);

                    // creates an element whose tag name is TD
                    mycurrent_cell=document.createElement("TD");
                    mycurrent_cell.id = "cell10"+rownum;
                    mycurrent_cell.setAttribute("width",'30');
                    mycurrent_cell.innerHTML = "";
                    // appends the cell TD into the row TR
                    mycurrent_row.appendChild(mycurrent_cell);

                    // creates an element whose tag name is TD
                    mycurrent_cell=document.createElement("TD");
                    mycurrent_cell.id = "cell11"+rownum;
                    mycurrent_cell.setAttribute("width",'30');
                    //mycurrent_cell.innerHTML = "";
                    mycurrent_cell.innerHTML = "<INPUT TYPE=\"hidden\" NAME=\"row"+rownum+"row\" VALUE=\""+rownum+"\"><INPUT TYPE=\"hidden\" NAME=\"ammendmentcomments"+rownum+"\" VALUE=\"\"><INPUT TYPE=\"hidden\" NAME=\"ammendment"+rownum+"\" VALUE=\"0\"><INPUT TYPE=\"hidden\" NAME=\"originallinestatus"+rownum+"\" VALUE=\"Draft\"><INPUT TYPE=\"hidden\" NAME=\"row"+rownum+"linenumber\" VALUE=\""+lineNumberc+"\"><INPUT TYPE=\"hidden\" NAME=\"linestatus"+rownum+"\" VALUE=\"Draft\"><INPUT TYPE=\"hidden\" NAME=\"linetotalprice"+rownum+"\" VALUE=\"0\"><INPUT TYPE=\"hidden\" NAME=\"qtyreceived"+rownum+"\" VALUE=\"0\">";
                    // appends the cell TD into the row TR
                    mycurrent_row.appendChild(mycurrent_cell);

                    // creates an element whose tag name is TD
                    mycurrent_cell=document.createElement("TD");
                    mycurrent_cell.id = "cell12"+rownum;
                    mycurrent_cell.setAttribute("width",'10');
                    mycurrent_cell.innerHTML = "Draft";
                    // appends the cell TD into the row TR
                    mycurrent_row.appendChild(mycurrent_cell);

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
            newdivcurrent_cell=document.createElement("TD");
            newdivcurrent_cell.setAttribute("align",'left');
            newdivcurrent_cell.setAttribute("colSpan","2");
            newdivcurrent_cell.innerHTML = "<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name='reflinechgs' OnClick=refreshAddCharges('"+rownum+"') value=\"Refresh\">";
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

            if ( (itemID.value.length > 0) && (itemType.value == "MA") && (validitem.value == "Yes") && (linestatus.value != "Remove") )
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
                    newdivcurrent_cell.innerHTML = "<INPUT TYPE=\"checkbox\" value=\"Yes\" NAME=\"addchargecheck"+divname+rownum+lineadded+"\" onClick=\"setlineUnconfirmed('"+rownum+"')\">";
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
                newdivcurrent_cell.innerHTML = "<INPUT TYPE=\"hidden\" NAME=\"linechange"+rownum+"\" VALUE=\"Yes\"><INPUT TYPE=\"hidden\" NAME=\"nooflinesinaddcharge"+rownum+"\" VALUE=\""+lineadded+"\">";
            newdivcurrent_row.appendChild(newdivcurrent_cell);
            newdivtablebody.appendChild(newdivcurrent_row);

            mybody.appendChild(newdivtable);

            //alert("Done Add Line Charge");
    }
}

function buildheader()
{
		  // getting reference to my Line Table
        mytable = document.getElementById("linetable");

        // get the reference for the body
        var mybody=document.getElementById("mainpara");

        // creates an element whose tag name is TBODY
        mytablebody = document.createElement("TBODY");
        // creating all cells
        // creates an element whose tag name is TR
            mycurrent_row=document.createElement("TR");

                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TH");
                mycurrent_cell.setAttribute("width",'80');
                mycurrent_cell.innerHTML = "Item";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TH");
                mycurrent_cell.setAttribute("width",'275');
                mycurrent_cell.innerHTML = "Description";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TH");
                mycurrent_cell.setAttribute("width",'100');
                mycurrent_cell.innerHTML = "Packaging";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

					 // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TH");
                mycurrent_cell.setAttribute("width",'40');
                mycurrent_cell.innerHTML = "Qty";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

                // creates an element whose tag name is TD
                /*mycurrent_cell=document.createElement("TH");
                mycurrent_cell.setAttribute("width",'40');
                name = "dateneeded" + (rownum);
                mycurrent_cell.innerHTML = "";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);*/

                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TH");
                mycurrent_cell.setAttribute("width",'100');
                mycurrent_cell.innerHTML = "Ext Price";

                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TH");
                mycurrent_cell.setAttribute("width",'100');
                mycurrent_cell.innerHTML = "Supplier Packaging";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TH");
                mycurrent_cell.setAttribute("width",'40');
                mycurrent_cell.innerHTML = "Supplier Qty";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

                /*// creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TH");
                mycurrent_cell.setAttribute("width",'40');
                mycurrent_cell.innerHTML = "";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);*/

                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TH");
                mycurrent_cell.setAttribute("width",'100');
                mycurrent_cell.innerHTML = "Supplier Ext Price";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

            // appends the row TR into TBODY
            mytablebody.appendChild(mycurrent_row);

        // appends TBODY into TABLE
        mytable.appendChild(mytablebody);

		  mybody.appendChild(mytable);
}


function addLineItem()
{
        // getting reference to my Line Table
        mytable = document.getElementById("linetable");

        var allTRs = mytable.getElementsByTagName("tr");
        var str = "# of table Rows: " + allTRs.length + "\n";
        var rownum = (allTRs.length)*1;
        //alert(str);

        var lineNumberc = 0;

		  if (allTRs.length == 0)
        {
            lineNumberc = rownum;
				buildheader();
				rownum = rownum + 1;
        }
        else
        {
            //var lineNumber = document.getElementById("row"+allTRs.length+"linenumber");
            //lineNumberc = ((lineNumber.value)*1 + 1);
        }

        var Color ="white";
        if (rownum%2==0)
        {
            Color ="blue";
        }
        else
        {
            Color ="white";
        }

        Nototallines = document.getElementById("totallines");
        Nototallines.value = (rownum-1);

        // get the reference for the body
        var mybody=document.getElementById("mainpara");

        // creates an element whose tag name is TBODY
        mytablebody = document.createElement("TBODY");
        // creating all cells
        // creates an element whose tag name is TR
            mycurrent_row=document.createElement("TR");
            mycurrent_row.id = "row"+rownum;
            mycurrent_row.className = Color;
           // mycurrent_row.attachEvent("onclick", catchevent);
           // mycurrent_row.attachEvent("mouseover", catchoverevent);

                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TD");
                mycurrent_cell.id = "cell1"+rownum;
                mycurrent_cell.attachEvent("onclick", catchevent);
                mycurrent_cell.attachEvent("mouseover", catchoverevent);
                mycurrent_cell.style.cursor = "hand";

                mycurrent_cell.setAttribute("width",'80');
                mycurrent_cell.innerHTML = lineNumberc+"/0";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TD");
                mycurrent_cell.id = "cell2"+rownum;
                mycurrent_cell.setAttribute("width",'275');
                mycurrent_cell.setAttribute("align",'left');
                name = "lineitemid" + (rownum);
                mycurrent_cell.innerHTML = "";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TD");
                mycurrent_cell.id = "cell3"+rownum;
                mycurrent_cell.setAttribute("width",'100');
                mycurrent_cell.innerHTML = "";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

					 // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TD");
                mycurrent_cell.id = "cell4"+rownum;
                mycurrent_cell.setAttribute("width",'40');
                mycurrent_cell.innerHTML = "";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

                // creates an element whose tag name is TD
                /*mycurrent_cell=document.createElement("TD");
                mycurrent_cell.id = "cell5"+rownum;
                mycurrent_cell.setAttribute("width",'40');
                name = "dateneeded" + (rownum);
                mycurrent_cell.innerHTML = "";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);*/

                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TD");
                mycurrent_cell.id = "cell6"+rownum;
                mycurrent_cell.setAttribute("width",'100');
                name = "datepromised" + (rownum);
                mycurrent_cell.innerHTML = "";

                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TD");
                mycurrent_cell.id = "cell7"+rownum;
                mycurrent_cell.setAttribute("width",'100');
                mycurrent_cell.setAttribute("align",'right');
                name = "lineqty" + (rownum);
                mycurrent_cell.innerHTML = "";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TD");
                mycurrent_cell.id = "cell8"+rownum;
                mycurrent_cell.setAttribute("width",'40');
                mycurrent_cell.setAttribute("align",'right');
                name = "lineunitprice" + (rownum);
                mycurrent_cell.innerHTML = "";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

                /*// creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TD");
                mycurrent_cell.id = "cell9"+rownum;
                mycurrent_cell.setAttribute("width",'40');
                mycurrent_cell.setAttribute("align",'right');
                //mycurrent_cell.setAttribute("align",'right');
                mycurrent_cell.innerHTML = "";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);*/

                // creates an element whose tag name is TD
                mycurrent_cell=document.createElement("TD");
                mycurrent_cell.id = "cell10"+rownum;
                mycurrent_cell.setAttribute("width",'100');
                mycurrent_cell.innerHTML = "";
                // appends the cell TD into the row TR
                mycurrent_row.appendChild(mycurrent_cell);

            // appends the row TR into TBODY
            mytablebody.appendChild(mycurrent_row);

        // appends TBODY into TABLE
        mytable.appendChild(mytablebody);

        // creates an element whose tag name is DIV
        //newdiv = document.createElement("DIV");
        var divname =  "divrow"+rownum;
        //newdiv.id = divname;
        //newdiv.className =  "displaynone";

        //newdiv.innerHTML = "<B><U>PO Line Detail: </U></B>";

        newdivtable = document.createElement("TABLE");
        newdivtable.setAttribute("width",'100%');
        newdivtable.setAttribute("CELLSPACING",'1');
        newdivtable.setAttribute("CELLPADDING",'3');
        newdivtable.id = divname;
        newdivtable.className =  "displaynone";

        newdivtablebody = document.createElement("TBODY");
        // creating all po line details

		  newdivcurrent_row=document.createElement("TR");
            newdivcurrent_row.id = "supplierdetail"+divname+rownum;
            newdivcurrent_row.className = "whitenocur";
                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "supplierdetailline"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'10%');
                newdivcurrent_cell.setAttribute("align",'left');
                newdivcurrent_cell.innerHTML = "<B>Item Detail:</B>&nbsp;&nbsp;&nbsp;<input type=\"text\" size=\"12\" MAXLENGTH=\"30\" CLASS=\"INVISIBLEHEADWHITE\" name='itemidindetail"+rownum+"' id='itemidindetail"+rownum+"' readonly> ";
            newdivcurrent_row.appendChild(newdivcurrent_cell);
        newdivtablebody.appendChild(newdivcurrent_row);

            newdivcurrent_row=document.createElement("TR");
            newdivcurrent_row.id = "detailrow1"+divname+rownum;
            newdivcurrent_row.className = "bluenocur";

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "row1detail"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'90%');
                newdivcurrent_cell.setAttribute("align",'left');
                //newdivcurrent_cell.setAttribute("colSpan","4");

                var innHtmlline =  "<B>Invoice - PO Item Matching</B>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ";

                /*<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name='addIdnvoiceLine' OnClick=addinvoiceLine('"+rownum+"') value=\"Add Line\"> ";
                innHtmlline += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name='addCreditCorrection' OnClick=addcreditcorrectionline('"+rownum+"') value=\"Add Credit/Correction\"> ";*/

                newdivcurrent_cell.innerHTML = innHtmlline;
            newdivcurrent_row.appendChild(newdivcurrent_cell);
        newdivtablebody.appendChild(newdivcurrent_row);

newdivcurrent_row=document.createElement("TR");
            newdivcurrent_row.id = "detailrow123"+divname+rownum;
            newdivcurrent_row.className = "whitenocur";
                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "row1detail23"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'90%');
                newdivcurrent_cell.setAttribute("align",'left');

                var innHtmlline2 =  "<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name='showallInvLines"+rownum+"' OnClick=showallinvlines('"+rownum+"') value=\"Show All\">";
					 innHtmlline2 +=  "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name='showunMatchedInv"+rownum+"' OnClick=showunmatchedinv('"+rownum+"') value=\"Show Only Open Invoices\"> ";
                newdivcurrent_cell.innerHTML = innHtmlline2;
            newdivcurrent_row.appendChild(newdivcurrent_cell);
        newdivtablebody.appendChild(newdivcurrent_row);

	newdivcurrent_row=document.createElement("TR");
            newdivcurrent_row.id = "supplierdetail"+divname+rownum;
            newdivcurrent_row.className = "whitenocur";
                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "itemmatching"+rownum;
                newdivcurrent_cell.setAttribute("width",'10%');
                newdivcurrent_cell.setAttribute("align",'left');
                newdivcurrent_cell.innerHTML = "Table";
            newdivcurrent_row.appendChild(newdivcurrent_cell);
        newdivtablebody.appendChild(newdivcurrent_row);


newdivcurrent_row=document.createElement("TR");
            newdivcurrent_row.id = "supplierdetail"+divname+rownum;
            newdivcurrent_row.className = "whitenocur";
                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "itemmatching"+rownum;
                newdivcurrent_cell.setAttribute("width",'10%');
                newdivcurrent_cell.setAttribute("align",'left');
                newdivcurrent_cell.innerHTML = "&nbsp;";
            newdivcurrent_row.appendChild(newdivcurrent_cell);
        newdivtablebody.appendChild(newdivcurrent_row);

        newdivcurrent_row=document.createElement("TR");
            newdivcurrent_row.id = "detailrow2"+divname+rownum;
            newdivcurrent_row.className = "bluenocur";

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "titleline2"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'10%');
                newdivcurrent_cell.setAttribute("align",'left');

					 var innHtmlline1 =  "<B>Invoice - Receipt Matching</B>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name='shwallreceiptMatching"+rownum+"' OnClick=showallreceiptlines('"+rownum+"') value=\"Show All\">";
					 innHtmlline1 +=  "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<INPUT TYPE=\"button\" CLASS=\"SUBMIT\" onmouseover=\"className='SUBMITHOVER'\" onMouseout=\"className='SUBMIT'\" name='showunMatchedReceipts"+rownum+"' OnClick=showunmatchedrecipts('"+rownum+"') value=\"Show Only Receipts to Match\"> ";

					 //newdivcurrent_cell.innerHTML = "<B>Invoice - Receipt Matching</B>";
                newdivcurrent_cell.innerHTML = innHtmlline1;
                newdivcurrent_row.appendChild(newdivcurrent_cell);

        newdivtablebody.appendChild(newdivcurrent_row);

        newdivcurrent_row=document.createElement("TR");
            newdivcurrent_row.id = "detailrow3"+divname+rownum;
            newdivcurrent_row.className = "whitenocur";

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "supplierconv"+rownum;
                newdivcurrent_cell.setAttribute("width",'10%');
                newdivcurrent_cell.setAttribute("align",'left');
                newdivcurrent_cell.innerHTML = "<B>Supplier Conversion:</B>";
                newdivcurrent_row.appendChild(newdivcurrent_cell);
        newdivtablebody.appendChild(newdivcurrent_row);

            newdivcurrent_row=document.createElement("TR");
            newdivcurrent_row.id = "detailrow4"+divname+rownum;
            newdivcurrent_row.className = "whitenocur";

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "receiptmatching"+rownum;
                newdivcurrent_cell.setAttribute("width",'10%');
                newdivcurrent_cell.setAttribute("align",'left');
                newdivcurrent_cell.setAttribute("vAlign",'top');
                newdivcurrent_cell.innerHTML = "Table";
                newdivcurrent_row.appendChild(newdivcurrent_cell);

        newdivtablebody.appendChild(newdivcurrent_row);


        newdivcurrent_row=document.createElement("TR");
            newdivcurrent_row.id = "detailrow10"+divname+rownum;
            newdivcurrent_row.className = "whitenocur";

                newdivcurrent_cell=document.createElement("TD");
                newdivcurrent_cell.id = "row10detail"+divname+rownum;
                newdivcurrent_cell.setAttribute("width",'90%');
                newdivcurrent_cell.setAttribute("align",'left');

                var invisibleElements = "<INPUT TYPE=\"hidden\" NAME=\"linestatus"+rownum+"\" VALUE=\"Draft\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"row"+rownum+"linenumber\" VALUE=\""+lineNumberc+"\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"row"+rownum+"row\" VALUE=\""+rownum+"\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"itemnotestatus"+rownum+"\" VALUE=\"No\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"linechange"+rownum+"\" VALUE=\"Yes\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"originallinestatus"+rownum+"\" VALUE=\"Draft\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"ammendment"+rownum+"\" VALUE=\"0\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"ammendmentcomments"+rownum+"\" VALUE=\"\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"itemtype"+rownum+"\" VALUE=\"\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"associatedprsstatus"+rownum+"\" VALUE=\"No\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"nofassociatedprs"+rownum+"\" VALUE=\"0\">";
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
		    invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"polineunitprice"+rownum+"\" VALUE=\"0\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"prshipto"+rownum+"\" VALUE=\"\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"noofmatchingitem"+rownum+"\" VALUE=\"0\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"noofreceiptmatching"+rownum+"\" VALUE=\"0\">";
                invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"nooflinesinaddcharge"+rownum+"\" VALUE=\"0\">";

					 invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"editinvoicedata\" VALUE=\"\">";
					 invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"addIdnvoiceLine\" VALUE=\"\">";
					 invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"addmaterialrma"+rownum+"\" VALUE=\"\">";
					 invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"addadchrgreturn"+rownum+"\" VALUE=\"\">";

                //invisibleElements += "<INPUT TYPE=\"hidden\" NAME=\"itemnotestatus"+divname+""+rownum+"\" VALUE=\"No\">";
                newdivcurrent_cell.innerHTML = invisibleElements;
            newdivcurrent_row.appendChild(newdivcurrent_cell);
        newdivtablebody.appendChild(newdivcurrent_row);


        newdivtable.appendChild(newdivtablebody);

        mybody.appendChild(newdivtable);
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

    selectedrowidinform = document.getElementById("selectedrowid");
	 selectedrowidinform.value = parentrow.id;

}

function removeline(entered)
{
    mytable = document.getElementById("linetable");
    var allTRs = mytable.getElementsByTagName("tr");
    var nowofRows = (allTRs.length)*1;

    if (nowofRows > 0)
    {

    //alert(selected_rowid);
    selectedItemRow = document.getElementById(""+selected_rowid+"row");
    //alert(selectedItemRow.value);
    selectedRowStatus = document.getElementById("linestatus"+selectedItemRow.value+"");

    selectedOriginalRowStatus = document.getElementById("originallinestatus"+selectedItemRow.value+"");

    var removedraftline = 0;
    if (selectedRowStatus.value == "Confirmed")
    {
	alert("You Cannot Delete a Confirmeded Line");
    }
    /*else if (selectedRowStatus.value == "Draft")
    {
      var chargeTable = document.getElementById("linetable");
      var allTRs = chargeTable.getElementsByTagName("tr");
      var nowofRows = (allTRs.length)*1;

      for (j = 0; j<nowofRows; j++)
      {
        var selectedRowtoTest = "row" + (j+1);
        //alert(""+selectedRowtoTest+"   "+selected_rowid+"");
        if (selectedRowtoTest == selected_rowid)
        {
            selectedRowStatus.value = "Remove";
            removedraftline = j;
            selectedRow = document.getElementById(selected_rowid);
            selectedRow.style.backgroundColor = "#3b3b3b";
            selectedRow.style.display = "none";
            selectedRow.className = "displaynone";

            selectedRow = document.getElementById("divrow"+selectedItemRow.value+"");
            selectedRow.style.display = "none";
            selectedRow.className = "displaynone";

            selecteditemRowStatus = document.getElementById("cell12"+selectedItemRow.value+"");
            selecteditemRowStatus.innerHTML = "Remove";

        }

        if ( (removedraftline > 0) && (j>removedraftline))
        {
            selectedRowStatus = document.getElementById("linestatus"+(j+1)+"");

            var Color ="white";
            if ( j%2==0 )
            {
                Color ="#E6E8FA";
            }
            else
            {
                Color ="#FFFFFF";
            }

            if (selectedRowStatus.value != "Remove")
            {
            selectedRow = document.getElementById("row"+(j+1)+"");
            selectedRow.style.backgroundColor = Color;
            }
        }
     }
    }*/
    else if (selectedRowStatus.value != "Remove")
    {
    selectedRowStatus.value = "Remove";

    selectedRow = document.getElementById(selected_rowid);
    selectedRow.style.backgroundColor = "#3b3b3b";

    selectedRow = document.getElementById("divrow"+selectedItemRow.value+"");
    selectedRow.style.display = "none";
    selectedRow.className = "displaynone";

    selecteditemRowStatus = document.getElementById("cell12"+selectedItemRow.value+"");
    selecteditemRowStatus.innerHTML = "Remove";
    }
  }
}

function unremoveline(entered)
{
    mytable = document.getElementById("linetable");
    var allTRs = mytable.getElementsByTagName("tr");
    var nowofRows = (allTRs.length)*1;

    if (nowofRows > 0)
    {
    //alert(selected_rowid);
    selectedItemRow = document.getElementById(""+selected_rowid+"row");
    //alert(selectedItemRow.value);
    selectedRowStatus = document.getElementById("linestatus"+selectedItemRow.value+"");

    var removedraftline = 0;
    if (selectedRowStatus.value == "Remove")
    {
    selectedRowStatus.value = "Unconfirmed";

    /*var Color ="white";
    if ( ((selectedItemRow.value)*1)%2==0 )
    {
        Color ="#E6E8FA";
    }
    else
    {
        Color ="#FFFFFF";
    }*/

    selectedRow = document.getElementById(selected_rowid);
    selectedRow.style.backgroundColor = "#8a8aff";

    selectedRow = document.getElementById("divrow"+selectedItemRow.value+"");
    selectedRow.style.display = "block";

    selecteditemRowStatus = document.getElementById("cell12"+selectedItemRow.value+"");
    selecteditemRowStatus.innerHTML = "Unconfirmed";
    }
  }
}