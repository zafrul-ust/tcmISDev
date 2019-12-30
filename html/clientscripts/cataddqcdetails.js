var visinfo = new Object();
visinfo['display'] = new Array("", "none");

var submitcount=0;
var updatecount=0;
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
function openWinGeneric(destination,WinName,WinWidth, WinHeight, Resizable )
{
    windowprops = "toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,status=yes,top=200,left=200,width=" + WinWidth + ",height=" + WinHeight+",resizable=" + Resizable;
    preview = window.open(destination, WinName,windowprops);
}


function showExistingItems()
{
    loc = "/tcmIS/hub/existingcatalogitems.do?";
    var userpartnumber  =  document.getElementById("userpartnumber");
    if (userpartnumber.value.length > 0)
    {
        loc = loc + "catPartNo=" + userpartnumber.value;
        var CompanyId  =  document.getElementById("CompanyId");
        loc = loc + "&companyId=" + CompanyId.value;
        var catalogId  =  document.getElementById("catalogId");
        loc = loc + "&catalogId=" + catalogId.value;

        openWinGeneric(loc,"showExistingItemsfor","700","450","yes","260","320")
    }
}


function editItemNotes(numdetrow)
{
   loc = "/tcmIS/supply/edititemnotes.do?itemId=";

   var itemId  =  document.getElementById("item_id");
   if (itemId.value.length > 0)
   {
	   loc = loc + itemId.value;
     try
     {
     var itemnotestable  =  document.getElementById("linedetailtable"+numdetrow+"");
     itemnotestable.className =  "displaynone";

     var itemnotesimgae = document.getElementById("itemnotesimage"+numdetrow+"");
     itemnotesimgae.setAttribute("src",'/images/plus.jpg');

     var itemnotestatus  =  document.getElementById("itemnotestatus"+numdetrow+"");
     itemnotestatus.value = "No";
    }
    catch (ex)
    {
    }
	   openWinGeneric(loc,"editItemNotes","800","600","yes","50","50")
	 }
	 else
	 {

	 }
}
/*
function addItemNotes(numdetrow)
{
    loc = "/tcmIS/purchase/itemnotes?itemID=";
    var itemID  =  document.getElementById("item_id");
    if (itemID.value.length > 0)
    {
        loc = loc + itemID.value;
        loc = loc + "&Action=addnew";
        loc = loc + "&lineID=" + numdetrow;

        openWinGeneric(loc,"ShipToID","200","200","yes","260","320")
    }
}
*/

function getItemNotes(numdetrow)
{
    loc = "/tcmIS/purchase/itemnotes?itemID=";
    var itemID  =  document.getElementById("item_id");

    var itemnotestatus  =  document.getElementById("itemnotestatus"+numdetrow+"");
    if ( (itemID.value.length > 0) && (itemnotestatus.value == "No") )
    {
        var itemnotesimgae = document.getElementById("itemnotesimage"+numdetrow+"");
        itemnotesimgae.setAttribute("src",'/images/minus.jpg');

        loc = loc + itemID.value;
        loc = loc + "&Action=view";
        loc = loc + "&lineID=" + numdetrow;
        itemnotestatus.value = "Yes";
        openWinGeneric(loc,"ShipToID","200","200","yes","200","200")
    }
    else if ( itemnotestatus.value == "Yes" )
    {
        var itemnotestable  =  document.getElementById("linedetailtable"+numdetrow+"");
        itemnotestable.className =  "displaynone";

        var itemnotesimgae = document.getElementById("itemnotesimage"+numdetrow+"");
        itemnotesimgae.setAttribute("src",'/images/plus.jpg');

        var itemnotestatus  =  document.getElementById("itemnotestatus"+numdetrow+"");
        itemnotestatus.value = "No";
    }
}

function showitemtcmBuys(itemId,shiptoloc)
{
    loc = "/tcmIS/purchase/showtcmbuys?Action=viewforpage&itemID=";
    loc = loc + itemId;
    loc = loc + "&shiptoLoc=" + shiptoloc;
    openWinGeneric(loc,"tcmbuyhistory","800","450","yes","50","50")
}

function switchpages()
{
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
}

//08-03-02
function additemID(itemidvalue,itemisVerified,itemType)
{
    document.itemidsearch.itemid.value = itemidvalue;
    document.itemidsearch.itemisVerified.value = itemisVerified;
    document.itemidsearch.itemType.value = itemType;
}

function sendItemID(entered)
{
    with (entered)
    {
        eval( testmfgid = "window.document.itemidsearch.itemid")
        if ( (eval(testmfgid.toString())).value.length > 1 )
        {
            opener.document.MainForm.item_id.value = window.document.itemidsearch.itemid.value;
            //opener.document.MainForm.itemisVerified.value = window.document.itemidsearch.itemisVerified.value;
            var itemerified = "N";
	    try
    	    {
                itemerified = window.document.itemidsearch.itemisVerified.value;  //09-25-03
            }
	   catch (ex)
	  {
	       itemerified = "N";
	  }
	  opener.document.MainForm.itemisVerified.value = itemerified;
      var itemType = document.getElementById("itemType");
      if (itemType !=null && itemType.value.length > 1)
      {
       opener.document.MainForm.item_type.value = window.document.itemidsearch.itemType.value;
      }
      }
    }
    window.close();
}

function ShowSearch(name,entered)
{
    with (entered)
    {
        loc = "/tcmIS/Catalog/likeitem?Action=" + name.toString() + "&itemid=";
        loc = loc + window.document.itemidsearch.itemid.value;
	loc = loc +"&itemisVerified=" + window.document.itemidsearch.itemisVerified.value;
    }
    window.location.replace(loc);
}

function togglePage(tagid, title)
{
    for (var i=0; i<cmds.length; i++)
    {
        var tag = document.all(cmds[i]+"_tabchild");

        if (cmds[i] == tagid)
        {
            setVisible(cmds[i], true)
            eval(compForm2  =  "window.document.ComponentForm" + (i+1).toString() + ".material_id" +i.toString() );
            ( curval2 =  (eval(compForm2.toString()).value ) );
            if (curval2.length < 1)
            {
                tag.className = "tab_nomatID_selected"
            }
            else
            {
                tag.className = "tab_selected_tab";
            }

        }
        else
        {
            setVisible(cmds[i], false);

            eval(compForm2  =  "window.document.ComponentForm" + (i+1).toString() + ".material_id" +i.toString() );
            ( curval2 =  (eval(compForm2.toString()).value ) );
            if (curval2.length < 1)
            {
                tag.className = "tab_nomatID"
            }
            else
            {
                tag.className = "tab_tab";
            }

        }
    }
}

function eraseBlank(ref, alternate)
{
    if (ref == null || ref == "")
    {
        if (alternate == null)
            return "";
        else
            return alternate;
    }
    else
        return ref;
}

function setVisible(tagid, yesno)
{
    try
    {
        displaystyle = "display";
        var target = document.all.item(tagid);
        var x = (yesno) ? 0 : 1;
        target.style[displaystyle] = visinfo[displaystyle][x];
    }
    catch (ex)
    {
    }
}

function getitemID(entered)
{
    var total  =  window.document.MainForm.totalComps.value;
    var finalMsgt;
    finalMsgt = "You need valid values for: \n\n";

    var result ;
    var allClear = 0;
    for ( var p = 0 ; p < total ; p ++)
    {
        /*eval(compForm  =  "window.document.ComponentForm" + (p+1).toString() + ".mfg_id" +p.toString() );
        ( curval =  (eval(compForm.toString()).value ) );
        if (curval.length < 1)
        {
            finalMsgt = finalMsgt +   " Manufacturer Id for Component " +(p+1).toString() + "\n" ;
            allClear = 1;
        }*/
        eval(compForm2  =  "window.document.ComponentForm" + (p+1).toString() + ".material_id" +p.toString() );
        ( curval2 =  (eval(compForm2.toString()).value ) );
        if (curval2.length < 1)
        {
            finalMsgt = finalMsgt +   " Material Id for Component " +(p+1).toString() + "\n" ;
            allClear = 1;
        }
    }

    if (allClear < 1)
    {
        result = true;
        loc = "/tcmIS/Catalog/likeitem?Action=findlike&like=itemid&manufacturer=";
        openWinGeneric(loc,"ItemId","800","410","yes")

    }
    else
    {
        alert(finalMsgt);
        result = false;
    }
    return result;
}

function isNetWtRequired(sizeUnit) {
    var result = false;
    for (var i = 0; i < netWtRequired.length; i++) {
        if (sizeUnit == netWtRequired[i]) {
            result = true;
            break;
        }
    }
    return result;
}

function actionValue(name, entered)
{
    var actvalue = name.toString();
    window.document.MainForm.Action.value = actvalue;
    var total  =  window.document.MainForm.totalComps.value;
    var finalMsgt;
    finalMsgt = "Please enter valid values for: \n\n";
    var itemisVerified =  document.getElementById("itemisVerified");


	 var caseQty  =  document.getElementById("caseQty");
	 if ( !(isInteger(caseQty.value)) )
	 {
		caseQty.value = "";
	 }

    if ( (actvalue == "Process") && (itemisVerified.value !='Y') )
    {
        var result ;
        var allClear = 0;
        for ( var p = 0 ; p < total ; p ++)
        {
            /*eval(compForm  =  "window.document.ComponentForm" + (p+1).toString() + ".mfg_id" +p.toString() );
            ( curval =  (eval(compForm.toString()).value ) );
            if (curval.length < 1)
            {
                finalMsgt = finalMsgt +   " Manufacturer Id for Component " +(p+1).toString() + "\n" ;
                allClear = 1;
            }*/
            eval(compForm2  =  "window.document.ComponentForm" + (p+1).toString() + ".material_id" +p.toString() );
            ( curval2 =  (eval(compForm2.toString()).value ) );
            if (curval2.length < 1)
            {
                finalMsgt = finalMsgt +   " Material Id for Component " +(p+1).toString() + "\n" ;
                allClear = 1;
            }

			eval(compForm2  =  "window.document.ComponentForm" + (p+1).toString() + ".shelf_life_basis" +p.toString() );
            ( curval2 =  (eval(compForm2.toString()).value ) );
            if (curval2.length < 1)
            {
                finalMsgt = finalMsgt +   " Shelf Life Basis for Component " +(p+1).toString() + "\n" ;
                allClear = 1;
            }

				/*eval(compForm2  =  "window.document.ComponentForm" + (p+1).toString() + ".mfg_storage_temp" +p.toString() );
            ( curval2 =  (eval(compForm2.toString()).value ) );
            if (curval2 == 'None')
            {
                finalMsgt = finalMsgt +   " Mfg Storage Temp for Component " +(p+1).toString() + "\n" ;
                allClear = 1;
            }*/

            eval(compForm2  =  "window.document.ComponentForm" + (p+1).toString() + ".size_units" +p.toString() );
            ( curval2 =  (eval(compForm2.toString()).value ) );
            var compSizeUnit = curval2;
            if (curval2.length == 0 )
            {
                finalMsgt = finalMsgt +   " Size Unit for Component " +(p+1).toString() + "\n" ;
                allClear = 1;
            }

				eval(compForm2  =  "window.document.ComponentForm" + (p+1).toString() + ".pkg_style" +p.toString() );
            ( curval2 =  (eval(compForm2.toString()).value ) );
            if (curval2.length == 0)
            {
                finalMsgt = finalMsgt +   " Pkg Style for Component " +(p+1).toString() + "\n" ;
                allClear = 1;
            }

				eval(compForm2  =  "window.document.ComponentForm" + (p+1).toString() + ".noofcomponents" +p.toString() );
            ( curval2 =  (eval(compForm2.toString()).value ) );
            if (curval2.length == 0)
            {
                finalMsgt = finalMsgt +   " # Comp for Component " +(p+1).toString() + "\n" ;
                allClear = 1;
            }


            if (isNetWtRequired(compSizeUnit)) {
                eval(compForm2  =  "window.document.ComponentForm" + (p+1).toString() + ".net_wt" +p.toString() );
                ( curval2 =  (eval(compForm2.toString()).value ) );
                if (curval2.length < 1 || curval2*1 <= 0)
                {
                    finalMsgt = finalMsgt +   " Net Wt for Component " +(p+1).toString() + "\n" ;
                    allClear = 1;
                }

                eval(compForm2  =  "window.document.ComponentForm" + (p+1).toString() + ".net_weight_unit" +p.toString() );
                ( curval2 =  (eval(compForm2.toString()).value ) );
                if (curval2 == 0)
                {
                    finalMsgt = finalMsgt +   " Net Wt Unit for Component " +(p+1).toString() + "\n" ;
                    allClear = 1;
                }
            }

            eval(compForm2  =  "window.document.ComponentForm" + (p+1).toString() + ".itemverified" +p.toString() );
            ( curval2 =  (eval(compForm2.toString()).value ) );
            if (curval2 == 'N')
            {
                finalMsgt = finalMsgt +   " Item Verified" +(p+1).toString() + "\n" ;
                allClear = 1;
            }

        }
        if (window.document.MainForm.item_id.value.length < 1 || window.document.MainForm.item_id.value*1 == 442554 )
        {
            finalMsgt = finalMsgt +   " Item Id \n" ;
            allClear = 1;
        }
        if (allClear < 1)
        {
            result = true;
        }
        else
        {
            alert(finalMsgt);
            result = false;
			   return result;
        }

    }
    for ( var p = 0 ; p < total ; p ++)
    {
        eval(compForm  =  "window.document.ComponentForm" + (p+1).toString() + ".mfg_id" +p.toString() );
        ( curval =  (eval(compForm.toString()).value ) );
        mfgidmain  =  eval("window.document.MainForm.mfg_id" + p.toString() );
        mfgidmain.value = curval;

        eval(compForm1  =  "window.document.ComponentForm" + (p+1).toString() + ".manufacturer" +p.toString() );
        ( curval1 =  (eval(compForm1.toString()).value ) );
        mainFormvalue1  =  eval("window.document.MainForm.manufacturer" + p.toString() );
        mainFormvalue1.value = curval1;

        eval(compForm2  =  "window.document.ComponentForm" + (p+1).toString() + ".material_id" +p.toString() );
        ( curval2 =  (eval(compForm2.toString()).value ) );
        mainFormvalue2  =  eval("window.document.MainForm.material_id" + p.toString() );
        mainFormvalue2.value = curval2;

        eval(compForm3  =  "window.document.ComponentForm" + (p+1).toString() + ".material_desc" +p.toString() );
        ( curval3 =  (eval(compForm3.toString()).value ) );
        mainFormvalue3  =  eval("window.document.MainForm.material_desc" + p.toString() );
        mainFormvalue3.value = curval3;

        eval(compForm4  =  "window.document.ComponentForm" + (p+1).toString() + ".grade" +p.toString() );
        ( curval4 =  (eval(compForm4.toString()).value ) );
        mainFormvalue4  =  eval("window.document.MainForm.grade" + p.toString() );
        mainFormvalue4.value = curval4;

        eval(compForm5  =  "window.document.ComponentForm" + (p+1).toString() + ".mfg_shelf_life" +p.toString() );
        ( curval5 =  (eval(compForm5.toString()).value ) );
        if ( !(isSignedFloat(curval5)) ){ curval5 = "";}
        mainFormvalue5  =  eval("window.document.MainForm.mfg_shelf_life" + p.toString() );
        mainFormvalue5.value = curval5;

        /*eval(compForm6  =  "window.document.ComponentForm" + (p+1).toString() + ".mfg_storage_temp" +p.toString() );
        ( curval6 =  (eval(compForm6.toString()).value ) );
        mainFormvalue6  =  eval("window.document.MainForm.mfg_storage_temp" + p.toString() );
        mainFormvalue6.value = curval6;*/

        eval(compForm7  =  "window.document.ComponentForm" + (p+1).toString() + ".shelf_life_basis" +p.toString() );
        ( curval7 =  (eval(compForm7.toString()).value ) );
        mainFormvalue7  =  eval("window.document.MainForm.shelf_life_basis" + p.toString() );
        mainFormvalue7.value = curval7;

        eval(compForm8  =  "window.document.ComponentForm" + (p+1).toString() + ".noofcomponents" +p.toString() );
        ( curval8 =  (eval(compForm8.toString()).value ) );
        if ( !(isSignedFloat(curval8)) ){ curval8 = "";}
        mainFormvalue8  =  eval("window.document.MainForm.noofcomponents" + p.toString() );
        mainFormvalue8.value = curval8;

        eval(compForm9  =  "window.document.ComponentForm" + (p+1).toString() + ".part_size" +p.toString() );
        ( curval9 =  (eval(compForm9.toString()).value ) );
        if ( !(isSignedFloat(curval9)) ){ curval9 = "";}
        mainFormvalue9  =  eval("window.document.MainForm.part_size" + p.toString() );
        mainFormvalue9.value = curval9;

        eval(compForm11  =  "window.document.ComponentForm" + (p+1).toString() + ".size_units" +p.toString() );
        ( curval11 =  (eval(compForm11.toString()).value ) );
        mainFormvalue11  =  eval("window.document.MainForm.size_units" + p.toString() );
        mainFormvalue11.value = curval11;

        eval(compForm12  =  "window.document.ComponentForm" + (p+1).toString() + ".pkg_style" +p.toString() );
        ( curval12 =  (eval(compForm12.toString()).value ) );
        mainFormvalue12  =  eval("window.document.MainForm.pkg_style" + p.toString() );
        mainFormvalue12.value = curval12;

        eval(compForm13  =  "window.document.ComponentForm" + (p+1).toString() + ".dimension" +p.toString() );
        ( curval13 =  (eval(compForm13.toString()).value ) );
        mainFormvalue13  =  eval("window.document.MainForm.dimension" + p.toString() );
        mainFormvalue13.value = curval13;

        eval(compForm14  =  "window.document.ComponentForm" + (p+1).toString() + ".net_wt" +p.toString() );
        ( curval14 =  (eval(compForm14.toString()).value ) );
        if ( !(isSignedFloat(curval14)) ){ curval14 = "";}
        mainFormvalue14  =  eval("window.document.MainForm.net_wt" + p.toString() );
        mainFormvalue14.value = curval14;

        eval(compForm15  =  "window.document.ComponentForm" + (p+1).toString() + ".net_weight_unit" +p.toString() );
        ( curval15 =  (eval(compForm15.toString()).value ) );
        mainFormvalue15  =  eval("window.document.MainForm.net_weight_unit" + p.toString() );
        mainFormvalue15.value = curval15;

        eval(compForm111  =  "window.document.ComponentForm" + (p+1).toString() + ".mfg_part_no" +p.toString() );
        ( curval11 =  (eval(compForm111.toString()).value ) );
        mainFormvalue111  =  eval("window.document.MainForm.mfg_part_no" + p.toString() );
        mainFormvalue111.value = curval11;

        eval(compForm1111  =  "window.document.ComponentForm" + (p+1).toString() + ".Comments" +p.toString() );
        ( curval111 =  (eval(compForm1111.toString()).value ) );
        mainFormvalue1111  =  eval("window.document.MainForm.Comments" + p.toString() );
        mainFormvalue1111.value = curval111;

        //eval(compFormSam  =  "window.document.ComponentForm" + (p+1).toString() + ".sample_size" +p.toString() );
        //( curvaSam =  (eval(compFormSam.toString()).value ) );
        //mainFormvalueSam  =  eval("window.document.MainForm.sample_size" + p.toString() );
        //mainFormvalueSam.value = curvaSam;

        //eval(compFormMatC  =  "window.document.ComponentForm" + (p+1).toString() + ".material_category" +p.toString() );
        //( curvaMatC =  (eval(compFormMatC.toString()).value ) );
        //mainFormvalueMatC  =  eval("window.document.MainForm.material_category" + p.toString() );
        //mainFormvalueMatC.value = curvaMatC;

        eval(tradename  =  "window.document.ComponentForm" + (p+1).toString() + ".trade_name" +p.toString() );
        ( tradenameV =  (eval(tradename.toString()).value ) );
        mainFormvalueMatC  =  eval("window.document.MainForm.trade_name" + p.toString() );
        mainFormvalueMatC.value = tradenameV;

	  //eval(compForm1111  =  "window.document.ComponentForm" + (p+1).toString() + ".sizevaries" +p.toString() );
        //( curval111 =  (eval(compForm1111.toString()).value ) );
        //mainFormvalue1111  =  eval("window.document.MainForm.sizevaries" + p.toString() );
        //mainFormvalue1111.value = curval111;

	     eval(compForm1111  =  "window.document.ComponentForm" + (p+1).toString() + ".itemverified" +p.toString() );
        ( curval111 =  (eval(compForm1111.toString()).value ) );
        mainFormvalue1111  =  eval("window.document.MainForm.itemverified" + p.toString() );
        mainFormvalue1111.value = curval111;

		  eval(compmintemp  =  "window.document.ComponentForm" + (p+1).toString() + ".mintemp" +p.toString() );
        ( compmintempval1 =  (eval(compmintemp.toString()).value ) );
        mainFormmintemp  =  eval("window.document.MainForm.mintemp" + p.toString() );
        mainFormmintemp.value = compmintempval1;

	     eval(compmaxtemp  =  "window.document.ComponentForm" + (p+1).toString() + ".maxtemp" +p.toString() );
        ( compmaxtempval =  (eval(compmaxtemp.toString()).value ) );
        mainFormcompmaxtemp  =  eval("window.document.MainForm.maxtemp" + p.toString() );
        mainFormcompmaxtemp.value = compmaxtempval;

 		  eval(comptempunit  =  "window.document.ComponentForm" + (p+1).toString() + ".tempunit" +p.toString() );
        ( comptempunitval =  (eval(comptempunit.toString()).value ) );
        mainFormcomptempunit  =  eval("window.document.MainForm.tempunit" + p.toString() );
        mainFormcomptempunit.value = comptempunitval;

        eval(complabelColor  =  "window.document.ComponentForm" + (p+1).toString() + ".labelColor" +p.toString() );
        ( complabelColorval =  (eval(complabelColor.toString()).value ) );
        mainFormcomplabelColor  =  eval("window.document.MainForm.labelColor" + p.toString() );
        mainFormcomplabelColor.value = complabelColorval;
    }
    document.body.style.cursor = 'wait';
    return true;
}
function update(entered)
{
    window.document.MainForm.Action.value = "UPDATE";
    return true;
}
function search(entered)
{
    window.document.MainForm.Action.value = "SAVE";
    return true;
}

function getmfgID(entered,CompNum)
{
    with (entered)
    {
        loc = "/tcmIS/Catalog/likemfgid?Action=findlike&like=mfgid&manufacturer=";
        eval(compForm2  =  "window.document.ComponentForm" + (CompNum+1).toString() + ".manufacturer" +CompNum.toString() );
        ( mfgid =  (eval(compForm2.toString()).value ) );
        loc = loc + mfgid;
        loc = loc + "&CompNum=" + CompNum;
        loc = loc + "&Company=" + window.document.MainForm.CompanyId.value;
        openWinGeneric(loc,"MfgID","600","400","yes")
    }
}

function getmaterialID(entered,CompNum)
{
    with (entered)
    {
        loc = "/tcmIS/Catalog/likematerial?Action=findlike&like=materialid&trade_name=";
        eval(compForm2  =  "window.document.ComponentForm" + (CompNum+1).toString() + ".material_desc" +CompNum.toString() );
        ( matdesc =  (eval(compForm2.toString()).value ) );
        loc = loc + matdesc;
        loc = loc + "&MfgID="
            eval(compForm3  =  "window.document.ComponentForm" + (CompNum+1).toString() + ".mfg_id" +CompNum.toString() );
        ( mfgid =  (eval(compForm3.toString()).value ) );
        loc = loc + mfgid;
        loc = loc + "&CompNum=" + CompNum;
        loc = loc + "&Company=" + window.document.MainForm.CompanyId.value;
        openWinGeneric(loc,"MaterialId","600","400","yes")
    }
}

function ShowOriginal(entered)
{
    with (entered)
    {
        loc = "/tcmIS/Catalog/catqcoriginal?Action=Original&request_id=";
        loc += window.document.MainForm.request_id.value;
        loc += "&Company=" + window.document.MainForm.CompanyId.value;
        loc += "&line_item=" + window.document.MainForm.line_item.value;
        openWinGeneric(loc,"Original","650","450","yes")
    }
}

function ShowMSDS(entered, CompNum)
{
    var loc
        with (entered)
    {
        eval(compForm2  =  "window.document.ComponentForm" + (CompNum+1).toString() + ".material_id" +CompNum.toString() );
        ( matid =  (eval(compForm2.toString()).value ) );
	  if (matid .length > 1)
	  {
        company = window.document.MainForm.CompanyId.value;
			if (company == "SEAGATE")
        {
            loc = "/tcmIS/seagate/ViewMsds?cl=Seagate&showspec=N&act=msds&id="
                loc = loc + matid;
        }
        else if (company == "RAYTHEON")
        {
            loc = "/tcmIS/ray/ViewMsds?cl=Ray&showspec=N&act=msds&id="
                loc = loc + matid;
        }
        else if (company == "SWA")
        {
            loc = "/tcmIS/swa/ViewMsds?cl=SWA&showspec=N&act=msds&id="
                loc = loc + matid;
        }
        else if (company == "DRS")
        {
            loc = "/tcmIS/drs/ViewMsds?cl=DRS&showspec=N&act=msds&id="
                loc = loc + matid;
        }
        else if (company == "LOCKHEED")
        {
            loc = "/tcmIS/lmco/ViewMsds?cl=Lockheed&showspec=N&act=msds&id="
                loc = loc + matid;
        }
	  else if (company == "BAE")
        {
            loc = "/tcmIS/bae/ViewMsds?cl=BAE&showspec=N&act=msds&id="
                loc = loc + matid;
        }
	  else if (company == "UTC")
        {
            loc = "/tcmIS/utc/ViewMsds?cl=UTC&showspec=N&act=msds&id="
                loc = loc + matid;
        }
	 else if (company == "SD")
        {
            loc = "/tcmIS/sd/ViewMsds?cl=SD&showspec=N&act=msds&id="
                loc = loc + matid;
        }
	  else
	  {
		loc = "/tcmIS/ray/ViewMsds?cl=Ray&showspec=N&act=msds&id="
            loc = loc + matid;

	  }
		 openWinGeneric(loc,"MSDS","800","650","yes")
	  }
	  else
	  {
		alert("There is no Material Id Associated to view MSDS");
	  }
    }

}

function CheckValues(entered)
{
    var testvar;
    var finalMsg;
    var allClear = 0;
    finalMsg = "";testvar =window.document.MainForm.csm_part_no.value.length;
    if (testvar < 1)
    {
        finalMsg = finalMsg + "Please Enter the CSM Part # Value.";
        allClear = allClear + 1;
    }
    if (allClear < 1)
    {
        return true;
    }
    else
    {
        alert(finalMsg);
        return false;
    }
}

function initTabs()
{
    document.all.item("Component1").style["display"] = "";
    resetApplicationTimer();
}

function cancel()
{
    window.close();
}

function resetApplicationTimer()
{
    try
    {
     parent.resetTimer();
    }
    catch(exap){
    }
}