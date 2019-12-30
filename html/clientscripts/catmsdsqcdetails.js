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
function additemID(itemidvalue)
{
    document.itemidsearch.itemid.value = itemidvalue;
}

function sendItemID(entered)
{
    with (entered)
    {
        eval( testmfgid = "window.document.itemidsearch.itemid")
        if ( (eval(testmfgid.toString())).value.length > 1 )
        {
            opener.document.MainForm.item_id.value = window.document.itemidsearch.itemid.value;
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
            eval(compForm2  =  "window.document.ComponentForm" + (i+1).toString() + ".materialid" +i.toString() );
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

            eval(compForm2  =  "window.document.ComponentForm" + (i+1).toString() + ".materialid" +i.toString() );
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
        eval(compForm  =  "window.document.ComponentForm" + (p+1).toString() + ".mfgid" +p.toString() );
        ( curval =  (eval(compForm.toString()).value ) );
        if (curval.length < 1)
        {
            finalMsgt = finalMsgt +   " Manufacturer Id for Component " +(p+1).toString() + "\n" ;
            allClear = 1;
        }
        eval(compForm2  =  "window.document.ComponentForm" + (p+1).toString() + ".materialid" +p.toString() );
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
        openWinGeneric(loc,"ItemId","800","420","yes")

    }
    else
    {
        alert(finalMsgt);
        result = false;
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
    if (actvalue == "Process")
    {
        var result ;
        var allClear = 0;
        for ( var p = 0 ; p < total ; p ++)
        {
            eval(compForm  =  "window.document.ComponentForm" + (p+1).toString() + ".mfgid" +p.toString() );
            ( curval =  (eval(compForm.toString()).value ) );
            if (curval.length < 1)
            {
                finalMsgt = finalMsgt +   " Manufacturer Id for Component " +(p+1).toString() + "\n" ;
                allClear = 1;
            }
            eval(compForm2  =  "window.document.ComponentForm" + (p+1).toString() + ".materialid" +p.toString() );
            ( curval2 =  (eval(compForm2.toString()).value ) );
            if (curval2.length < 1)
            {
                finalMsgt = finalMsgt +   " Material Id for Component " +(p+1).toString() + "\n" ;
                allClear = 1;
            }

	      	  /*eval(compForm2  =  "window.document.ComponentForm" + (p+1).toString() + ".revdate" +p.toString() );
            ( curval2 =  (eval(compForm2.toString()).value ) );
            if (curval2.length < 1)
            {
                finalMsgt = finalMsgt +   " Revision Date for Component " +(p+1).toString() + "\n" ;
                allClear = 1;
            }

		        eval(compForm2  =  "window.document.ComponentForm" + (p+1).toString() + ".content" +p.toString() );
            ( curval2 =  (eval(compForm2.toString()).value ) );
            if (curval2.length < 1)
            {
                finalMsgt = finalMsgt +   " MSDS File URL (Content) for Component " +(p+1).toString() + "\n" ;
                allClear = 1;
            }*/

	    eval(flashpointO  =  "window.document.ComponentForm" + (p+1).toString() + ".flashpoint" +p.toString() );
            flashpoint =  (eval(flashpointO.toString()).value);

				eval(flashpointunitsO =  "window.document.ComponentForm" + (p+1).toString() + ".flashpointunits" +p.toString() );
            flashpointunits =  (eval(flashpointunitsO.toString()).value);

            if ( isSignedFloat(flashpoint) && (flashpointunits.length < 1) )
            {
                finalMsgt = finalMsgt +   " Flash Point Unit for Component " +(p+1).toString() + "\n" ;
                allClear = 1;
            }
            /*else if ( !(isSignedFloat(flashpoint)) && (flashpointunits == "F") )
            {
				    finalMsgt = finalMsgt +   " Flash Point Unit for Component " +(p+1).toString() + "\n" ;
                allClear = 1;
            }*/
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
        eval(compForm  =  "window.document.ComponentForm" + (p+1).toString() + ".mfgid" +p.toString() );
        ( curval =  (eval(compForm.toString()).value ) );
        mfgidmain  =  eval("window.document.MainForm.mfgid" + p.toString() );
        mfgidmain.value = curval;

	     eval(compForm  =  "window.document.ComponentForm" + (p+1).toString() + ".mfgurl" +p.toString() );
        ( curval =  (eval(compForm.toString()).value ) );
        mfgurlmain  =  eval("window.document.MainForm.mfgurl" + p.toString() );
        mfgurlmain.value = curval;

	     eval(compForm  =  "window.document.ComponentForm" + (p+1).toString() + ".mfgcontact" +p.toString() );
        ( curval =  (eval(compForm.toString()).value ) );
        mfgcontactmain  =  eval("window.document.MainForm.mfgcontact" + p.toString() );
        mfgcontactmain.value = curval;

	     eval(compForm  =  "window.document.ComponentForm" + (p+1).toString() + ".mfgphone" +p.toString() );
        ( curval =  (eval(compForm.toString()).value ) );
        mfgcontactmain  =  eval("window.document.MainForm.mfgphone" + p.toString() );
        mfgcontactmain.value = curval;

        eval(compForm1  =  "window.document.ComponentForm" + (p+1).toString() + ".manufacturer" +p.toString() );
        ( curval1 =  (eval(compForm1.toString()).value ) );
        mainFormvalue1  =  eval("window.document.MainForm.manufacturer" + p.toString() );
        mainFormvalue1.value = curval1;

        eval(compForm2  =  "window.document.ComponentForm" + (p+1).toString() + ".materialid" +p.toString() );
        ( curval2 =  (eval(compForm2.toString()).value ) );
        mainFormvalue2  =  eval("window.document.MainForm.materialid" + p.toString() );
        mainFormvalue2.value = curval2;

        eval(compForm3  =  "window.document.ComponentForm" + (p+1).toString() + ".materialdesc" +p.toString() );
        ( curval3 =  (eval(compForm3.toString()).value ) );
        mainFormvalue3  =  eval("window.document.MainForm.materialdesc" + p.toString() );
        mainFormvalue3.value = curval3;

        eval(tradename  =  "window.document.ComponentForm" + (p+1).toString() + ".tradename" +p.toString() );
        ( tradenameV =  (eval(tradename.toString()).value ) );
        mainFormvalueMatC  =  eval("window.document.MainForm.tradename" + p.toString() );
        mainFormvalueMatC.value = tradenameV;

        /*eval(compForm3  =  "window.document.ComponentForm" + (p+1).toString() + ".landdot" +p.toString() );
        ( curval3 =  (eval(compForm3.toString()).value ) );
        mainFormvalue3  =  eval("window.document.MainForm.landdot" + p.toString() );
        mainFormvalue3.value = curval3;

        eval(compForm3  =  "window.document.ComponentForm" + (p+1).toString() + ".airdot" +p.toString() );
        ( curval3 =  (eval(compForm3.toString()).value ) );
        mainFormvalue3  =  eval("window.document.MainForm.airdot" + p.toString() );
        mainFormvalue3.value = curval3;

        eval(compForm3  =  "window.document.ComponentForm" + (p+1).toString() + ".waterdot" +p.toString() );
        ( curval3 =  (eval(compForm3.toString()).value ) );
        mainFormvalue3  =  eval("window.document.MainForm.waterdot" + p.toString() );
        mainFormvalue3.value = curval3;

        eval(compForm31  =  "window.document.ComponentForm" + (p+1).toString() + ".erg" +p.toString() );
        ( curval31 =  (eval(compForm31.toString()).value ) );
        if ( !(isInteger(curval31)) ){ curval31 = "";}
        mainFormvalue31  =  eval("window.document.MainForm.erg" + p.toString() );
        mainFormvalue31.value = curval31;*/

	     eval(compForm1111  =  "window.document.ComponentForm" + (p+1).toString() + ".Comments" +p.toString() );
        ( curval111 =  (eval(compForm1111.toString()).value ) );
        mainFormvalue1111  =  eval("window.document.MainForm.Comments" + p.toString() );
        mainFormvalue1111.value = curval111;


        eval(compForm4  =  "window.document.ComponentForm" + (p+1).toString() + ".revdate" +p.toString() );
        ( curval4 =  (eval(compForm4.toString()).value ) );
        mainFormvalue4  =  eval("window.document.MainForm.revdate" + p.toString() );
        mainFormvalue4.value = curval4;

        eval(compForm41  =  "window.document.ComponentForm" + (p+1).toString() + ".content" +p.toString() );
        ( curval41 =  (eval(compForm41.toString()).value ) );
        mainFormvalue41  =  eval("window.document.MainForm.content" + p.toString() );
        mainFormvalue41.value = curval41;

        eval(compForm5  =  "window.document.ComponentForm" + (p+1).toString() + ".physicalstate" +p.toString() );
        ( curval5 =  (eval(compForm5.toString()).value ) );
        mainFormvalue5  =  eval("window.document.MainForm.physicalstate" + p.toString() );
        mainFormvalue5.value = curval5;

        eval(compForm6  =  "window.document.ComponentForm" + (p+1).toString() + ".nfpah" +p.toString() );
        ( curval6 =  (eval(compForm6.toString()).value ) );
        mainFormvalue6  =  eval("window.document.MainForm.nfpah" + p.toString() );
        mainFormvalue6.value = curval6;

        eval(compForm7  =  "window.document.ComponentForm" + (p+1).toString() + ".nfpar" +p.toString() );
        ( curval7 =  (eval(compForm7.toString()).value ) );
        mainFormvalue7  =  eval("window.document.MainForm.nfpar" + p.toString() );
        mainFormvalue7.value = curval7;

        eval(compForm8  =  "window.document.ComponentForm" + (p+1).toString() + ".nfpaf" +p.toString() );
        ( curval8 =  (eval(compForm8.toString()).value ) );
        mainFormvalue8  =  eval("window.document.MainForm.nfpaf" + p.toString() );
        mainFormvalue8.value = curval8;

        eval(compForm9  =  "window.document.ComponentForm" + (p+1).toString() + ".nfpas" +p.toString() );
        ( curval9 =  (eval(compForm9.toString()).value ) );
        mainFormvalue9  =  eval("window.document.MainForm.nfpas" + p.toString() );
        mainFormvalue9.value = curval9;

        eval(compForm11  =  "window.document.ComponentForm" + (p+1).toString() + ".hmish" +p.toString() );
        ( curval11 =  (eval(compForm11.toString()).value ) );
        mainFormvalue11  =  eval("window.document.MainForm.hmish" + p.toString() );
        mainFormvalue11.value = curval11;

        eval(compForm12  =  "window.document.ComponentForm" + (p+1).toString() + ".hmisr" +p.toString() );
        ( curval12 =  (eval(compForm12.toString()).value ) );
        mainFormvalue12  =  eval("window.document.MainForm.hmisr" + p.toString() );
        mainFormvalue12.value = curval12;

        eval(compForm13  =  "window.document.ComponentForm" + (p+1).toString() + ".hmisf" +p.toString() );
        ( curval13 =  (eval(compForm13.toString()).value ) );
        mainFormvalue13  =  eval("window.document.MainForm.hmisf" + p.toString() );
        mainFormvalue13.value = curval13;

        eval(compForm14  =  "window.document.ComponentForm" + (p+1).toString() + ".hmiss" +p.toString() );
        ( curval14 =  (eval(compForm14.toString()).value ) );
        mainFormvalue14  =  eval("window.document.MainForm.hmiss" + p.toString() );
        mainFormvalue14.value = curval14;

        eval(compForm15  =  "window.document.ComponentForm" + (p+1).toString() + ".spegravity" +p.toString() );
        ( curval15 =  (eval(compForm15.toString()).value ) );
        if ( !(isSignedFloat(curval15)) ){ curval15 = "";}
        mainFormvalue15  =  eval("window.document.MainForm.spegravity" + p.toString() );
        mainFormvalue15.value = curval15;

        eval(compForm111  =  "window.document.ComponentForm" + (p+1).toString() + ".density" +p.toString() );
        ( curval11 =  (eval(compForm111.toString()).value ) );
        if ( !(isSignedFloat(curval11)) ){ curval11 = "";}
        mainFormvalue111  =  eval("window.document.MainForm.density" + p.toString() );
        mainFormvalue111.value = curval11;

        eval(compForm111  =  "window.document.ComponentForm" + (p+1).toString() + ".denunits" +p.toString() );
        ( curval11 =  (eval(compForm111.toString()).value ) );
        mainFormvalue111  =  eval("window.document.MainForm.denunits" + p.toString() );
        mainFormvalue111.value = curval11;

        eval(compForm111  =  "window.document.ComponentForm" + (p+1).toString() + ".voc" +p.toString() );
        ( curval11 =  (eval(compForm111.toString()).value ) );
        if ( !(isSignedFloat(curval11)) ){ curval11 = "";}
        mainFormvalue111  =  eval("window.document.MainForm.voc" + p.toString() );
        mainFormvalue111.value = curval11;

        eval(compForm111  =  "window.document.ComponentForm" + (p+1).toString() + ".voclower" +p.toString() );
        ( curval11 =  (eval(compForm111.toString()).value ) );
        if ( !(isSignedFloat(curval11)) ){ curval11 = "";}
        mainFormvalue111  =  eval("window.document.MainForm.voclower" + p.toString() );
        mainFormvalue111.value = curval11;

        eval(compForm111  =  "window.document.ComponentForm" + (p+1).toString() + ".vocupper" +p.toString() );
        ( curval11 =  (eval(compForm111.toString()).value ) );
        if ( !(isSignedFloat(curval11)) ){ curval11 = "";}
        mainFormvalue111  =  eval("window.document.MainForm.vocupper" + p.toString() );
        mainFormvalue111.value = curval11;

        eval(compForm111  =  "window.document.ComponentForm" + (p+1).toString() + ".vocunits" +p.toString() );
        ( curval11 =  (eval(compForm111.toString()).value ) );
        mainFormvalue111  =  eval("window.document.MainForm.vocunits" + p.toString() );
        mainFormvalue111.value = curval11;

        eval(compForm111  =  "window.document.ComponentForm" + (p+1).toString() + ".percsolids" +p.toString() );
        ( curval11 =  (eval(compForm111.toString()).value ) );
        if ( !(isSignedFloat(curval11)) ){ curval11 = "";}
        mainFormvalue111  =  eval("window.document.MainForm.percsolids" + p.toString() );
        mainFormvalue111.value = curval11;

        eval(compForm111  =  "window.document.ComponentForm" + (p+1).toString() + ".percsolidslower" +p.toString() );
        ( curval11 =  (eval(compForm111.toString()).value ) );
        if ( !(isSignedFloat(curval11)) ){ curval11 = "";}
        mainFormvalue111  =  eval("window.document.MainForm.percsolidslower" + p.toString() );
        mainFormvalue111.value = curval11;

        eval(compForm111  =  "window.document.ComponentForm" + (p+1).toString() + ".percsolidsupper" +p.toString() );
        ( curval11 =  (eval(compForm111.toString()).value ) );
        if ( !(isSignedFloat(curval11)) ){ curval11 = "";}
        mainFormvalue111  =  eval("window.document.MainForm.percsolidsupper" + p.toString() );
        mainFormvalue111.value = curval11;

        eval(compForm1111  =  "window.document.ComponentForm" + (p+1).toString() + ".percsolidsunit" +p.toString() );
        ( curval111 =  (eval(compForm1111.toString()).value ) );
        mainFormvalue1111  =  eval("window.document.MainForm.percsolidsunit" + p.toString() );
        mainFormvalue1111.value = curval111;

        eval(compFormSam  =  "window.document.ComponentForm" + (p+1).toString() + ".flashpoint" +p.toString() );
        ( curvaSam =  (eval(compFormSam.toString()).value ) );
        mainFormvalueSam  =  eval("window.document.MainForm.flashpoint" + p.toString() );
        mainFormvalueSam.value = curvaSam;

        eval(compFormSam1  =  "window.document.ComponentForm" + (p+1).toString() + ".flashpointunits" +p.toString() );
        ( curvaSam1 =  (eval(compFormSam1.toString()).value ) );
        mainFormvalueSam1  =  eval("window.document.MainForm.flashpointunits" + p.toString() );
        mainFormvalueSam1.value = curvaSam1;

        eval(compFormSam1  =  "window.document.ComponentForm" + (p+1).toString() + ".msdsremark" +p.toString() );
        ( curvaSam1 =  (eval(compFormSam1.toString()).value ) );
        mainFormvalueSam1  =  eval("window.document.MainForm.msdsremark" + p.toString() );
        mainFormvalueSam1.value = curvaSam1;

		  eval(compFormSam1  =  "window.document.ComponentForm" + (p+1).toString() + ".altdatasource" +p.toString() );
        ( curvaSam1 =  (eval(compFormSam1.toString()).value ) );
        mainFormvalueSam1  =  eval("window.document.MainForm.altdatasource" + p.toString() );
        mainFormvalueSam1.value = curvaSam1;

	  eval( compFormSam="window.document.ComponentForm" + ( p + 1 ).toString() + ".vappressdet" + p.toString() );
	  ( curvaSam= ( eval( compFormSam.toString() ).value ) );
	  mainFormvalueSam=eval( "window.document.MainForm.vappressdet" + p.toString() );
	  mainFormvalueSam.value=curvaSam;

	  eval( compFormSam="window.document.ComponentForm" + ( p + 1 ).toString() + ".vappress" + p.toString() );
	  ( curvaSam= ( eval( compFormSam.toString() ).value ) );
	  mainFormvalueSam=eval( "window.document.MainForm.vappress" + p.toString() );
	  mainFormvalueSam.value=curvaSam;

	  eval( compFormSam="window.document.ComponentForm" + ( p + 1 ).toString() + ".vappressunit" + p.toString() );
	  ( curvaSam= ( eval( compFormSam.toString() ).value ) );
	  mainFormvalueSam=eval( "window.document.MainForm.vappressunit" + p.toString() );
	  mainFormvalueSam.value=curvaSam;

	  eval( compFormSam="window.document.ComponentForm" + ( p + 1 ).toString() + ".vappresstemp" + p.toString() );
	  ( curvaSam= ( eval( compFormSam.toString() ).value ) );
	  mainFormvalueSam=eval( "window.document.MainForm.vappresstemp" + p.toString() );
	  mainFormvalueSam.value=curvaSam;

	  eval( compFormSam="window.document.ComponentForm" + ( p + 1 ).toString() + ".vappresstempunt" + p.toString() );
	  ( curvaSam= ( eval( compFormSam.toString() ).value ) );
	  mainFormvalueSam=eval( "window.document.MainForm.vappresstempunt" + p.toString() );
	  mainFormvalueSam.value=curvaSam;

	  eval( compFormSam="window.document.ComponentForm" + ( p + 1 ).toString() + ".vapor_pressure_source" + p.toString() );
	  ( curvaSam= ( eval( compFormSam.toString() ).value ) );
	  mainFormvalueSam=eval( "window.document.MainForm.vapor_pressure_source" + p.toString() );
	  mainFormvalueSam.value=curvaSam;

	  eval( compFormSam="window.document.ComponentForm" + ( p + 1 ).toString() + ".vapor_pressure_upper" + p.toString() );
	  ( curvaSam= ( eval( compFormSam.toString() ).value ) );
	  mainFormvalueSam=eval( "window.document.MainForm.vapor_pressure_upper" + p.toString() );
	  mainFormvalueSam.value=curvaSam;

	  eval( compFormSam="window.document.ComponentForm" + ( p + 1 ).toString() + ".vapor_pressure_lower" + p.toString() );
	  ( curvaSam= ( eval( compFormSam.toString() ).value ) );
	  mainFormvalueSam=eval( "window.document.MainForm.vapor_pressure_lower" + p.toString() );
	  mainFormvalueSam.value=curvaSam;


	eval( compFormSam="window.document.ComponentForm" + ( p + 1 ).toString() + ".voc_source" + p.toString() );
	( curvaSam= ( eval( compFormSam.toString() ).value ) );
	mainFormvalueSam=eval( "window.document.MainForm.voc_source" + p.toString() );
	mainFormvalueSam.value=curvaSam;

eval( compFormSam="window.document.ComponentForm" + ( p + 1 ).toString() + ".voc_less_h2o_exempt" + p.toString() );
( curvaSam= ( eval( compFormSam.toString() ).value ) );
mainFormvalueSam=eval( "window.document.MainForm.voc_less_h2o_exempt" + p.toString() );
mainFormvalueSam.value=curvaSam;
eval( compFormSam="window.document.ComponentForm" + ( p + 1 ).toString() + ".voc_less_h2o_exempt_unit" + p.toString() );
( curvaSam= ( eval( compFormSam.toString() ).value ) );
mainFormvalueSam=eval( "window.document.MainForm.voc_less_h2o_exempt_unit" + p.toString() );
mainFormvalueSam.value=curvaSam;
eval( compFormSam="window.document.ComponentForm" + ( p + 1 ).toString() + ".voc_less_h2o_exempt_source" + p.toString() );
( curvaSam= ( eval( compFormSam.toString() ).value ) );
mainFormvalueSam=eval( "window.document.MainForm.voc_less_h2o_exempt_source" + p.toString() );
mainFormvalueSam.value=curvaSam;
eval( compFormSam="window.document.ComponentForm" + ( p + 1 ).toString() + ".voc_less_h2o_exempt_lower" + p.toString() );
( curvaSam= ( eval( compFormSam.toString() ).value ) );
mainFormvalueSam=eval( "window.document.MainForm.voc_less_h2o_exempt_lower" + p.toString() );
mainFormvalueSam.value=curvaSam;
eval( compFormSam="window.document.ComponentForm" + ( p + 1 ).toString() + ".voc_less_h2o_exempt_upper" + p.toString() );
( curvaSam= ( eval( compFormSam.toString() ).value ) );
mainFormvalueSam=eval( "window.document.MainForm.voc_less_h2o_exempt_upper" + p.toString() );
mainFormvalueSam.value=curvaSam;


eval( compFormSam="window.document.ComponentForm" + ( p + 1 ).toString() + ".voc_lb_per_solid_lb" + p.toString() );
( curvaSam= ( eval( compFormSam.toString() ).value ) );
mainFormvalueSam=eval( "window.document.MainForm.voc_lb_per_solid_lb" + p.toString() );
mainFormvalueSam.value=curvaSam;

eval( compFormSam="window.document.ComponentForm" + ( p + 1 ).toString() + ".voc_lb_per_solid_lb_source" + p.toString() );
( curvaSam= ( eval( compFormSam.toString() ).value ) );
mainFormvalueSam=eval( "window.document.MainForm.voc_lb_per_solid_lb_source" + p.toString() );
mainFormvalueSam.value=curvaSam;
eval( compFormSam="window.document.ComponentForm" + ( p + 1 ).toString() + ".voc_lb_per_solid_lb_lower" + p.toString() );
( curvaSam= ( eval( compFormSam.toString() ).value ) );
mainFormvalueSam=eval( "window.document.MainForm.voc_lb_per_solid_lb_lower" + p.toString() );
mainFormvalueSam.value=curvaSam;
eval( compFormSam="window.document.ComponentForm" + ( p + 1 ).toString() + ".voc_lb_per_solid_lb_upper" + p.toString() );
( curvaSam= ( eval( compFormSam.toString() ).value ) );
mainFormvalueSam=eval( "window.document.MainForm.voc_lb_per_solid_lb_upper" + p.toString() );
mainFormvalueSam.value=curvaSam;

eval( compFormSam="window.document.ComponentForm" + ( p + 1 ).toString() + ".solids_source" + p.toString() );
( curvaSam= ( eval( compFormSam.toString() ).value ) );
mainFormvalueSam=eval( "window.document.MainForm.solids_source" + p.toString() );
mainFormvalueSam.value=curvaSam;

        for ( var c = 0 ; c < 50 ; c ++)
        {

            eval(casnumcomp3  =  "window.document.ComponentForm" + (p+1).toString() + ".percent" +c.toString() +p.toString() );
            ( curvacasnumcomp3 =  (eval(casnumcomp3.toString()).value ) );
            if ( !(isSignedFloat(curvacasnumcomp3)) ){ curvacasnumcomp3 = "";}
            mainFormvaluecasnum3  =  eval("window.document.MainForm.percent" +c.toString() + p.toString() );
            mainFormvaluecasnum3.value = curvacasnumcomp3

            eval(casnumcomp6  =  "window.document.ComponentForm" + (p+1).toString() + ".percent_lower" +c.toString() +p.toString() );
            ( curvacasnumcomp6 =  (eval(casnumcomp6.toString()).value ) );
            if ( !(isSignedFloat(curvacasnumcomp6)) ){ curvacasnumcomp6 = "";}
            mainFormvaluecasnum6  =  eval("window.document.MainForm.percent_lower" +c.toString() + p.toString() );
            mainFormvaluecasnum6.value = curvacasnumcomp6;

            eval(casnumcomp7  =  "window.document.ComponentForm" + (p+1).toString() + ".percent_upper" +c.toString() +p.toString() );
            ( curvacasnumcomp7 =  (eval(casnumcomp7.toString()).value ) );
            if ( !(isSignedFloat(curvacasnumcomp7)) ){ curvacasnumcomp7 = "";}
            mainFormvaluecasnum7  =  eval("window.document.MainForm.percent_upper" +c.toString() + p.toString() );
            mainFormvaluecasnum7.value = curvacasnumcomp7;

/*            eval(casnumcomp8  =  "window.document.ComponentForm" + (p+1).toString() + ".hazardous" +c.toString() +p.toString() );
            ( curvacasnumcomp8 =  (eval(casnumcomp8.toString()).value ) );
            mainFormvaluecasnum8  =  eval("window.document.MainForm.hazardous" +c.toString() + p.toString() );
            mainFormvaluecasnum8.value = curvacasnumcomp8;

           if (eval(casnumcomp8.toString()).checked)
           {
             mainFormvaluecasnum8.value = 'Y';
           }
           else
           {
            mainFormvaluecasnum8.value = '';
           }*/

            eval(casnumcomp9  =  "window.document.ComponentForm" + (p+1).toString() + ".cas_number" +c.toString() +p.toString() );
            ( curvacasnumcomp9 =  (eval(casnumcomp9.toString()).value ) );
            mainFormvaluecasnum9  =  eval("window.document.MainForm.cas_number" +c.toString() + p.toString() );
            mainFormvaluecasnum9.value = curvacasnumcomp9;

/*            eval(casnumcomp10  =  "window.document.ComponentForm" + (p+1).toString() + ".trade_secret" +c.toString() +p.toString() );
            ( curvacasnumcomp10 =  (eval(casnumcomp10.toString()).value ) );
            mainFormvaluecasnum10  =  eval("window.document.MainForm.trade_secret" +c.toString() + p.toString() );
            mainFormvaluecasnum10.value = curvacasnumcomp10;

            if (eval(casnumcomp10.toString()).checked)
            {
              mainFormvaluecasnum10.value = 'Y';
            }
            else
            {
             mainFormvaluecasnum10.value = '';
            }*/
            //alert(curvacasnumcomp10);

            eval(casnumcomp11  =  "window.document.ComponentForm" + (p+1).toString() + ".remark" +c.toString() +p.toString() );
            ( curvacasnumcomp11 =  (eval(casnumcomp11.toString()).value ) );
            mainFormvaluecasnum11  =  eval("window.document.MainForm.remark" +c.toString() + p.toString() );
            mainFormvaluecasnum11.value = curvacasnumcomp11;

            eval(casnumcomp12  =  "window.document.ComponentForm" + (p+1).toString() + ".msds_chemical_name" +c.toString() +p.toString() );
            ( curvacasnumcomp12 =  (eval(casnumcomp12.toString()).value ) );
            mainFormvaluecasnum12  =  eval("window.document.MainForm.msds_chemical_name" +c.toString() + p.toString() );
            mainFormvaluecasnum12.value = curvacasnumcomp12;

        }

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

		  mfgid = mfgid.replace(/#/gi, "%23");
        mfgid = mfgid.replace(/&/gi, "%26");

        loc = loc + mfgid;
        loc = loc + "&CompNum=" + CompNum;
        loc = loc + "&Company=" + window.document.MainForm.CompanyId.value;
        openWinGeneric(loc,"MfgID","600","420","yes")
    }
}

function getmaterialID(entered,CompNum)
{
    with (entered)
    {
        loc = "/tcmIS/Catalog/likematerial?Action=findlike&like=materialid&tradename=";
        eval(compForm2  =  "window.document.ComponentForm" + (CompNum+1).toString() + ".materialdesc" +CompNum.toString() );
        ( matdesc =  (eval(compForm2.toString()).value ) );

		  matdesc = matdesc.replace(/#/gi, "%23");
		  matdesc = matdesc.replace(/&/gi, "%26");

        loc = loc + matdesc;
        loc = loc + "&MfgID="
        eval(compForm3  =  "window.document.ComponentForm" + (CompNum+1).toString() + ".mfgid" +CompNum.toString() );
        ( mfgid =  (eval(compForm3.toString()).value ) );
	  if (mfgid .length > 0)
	  {
        loc = loc + mfgid;
        loc = loc + "&CompNum=" + CompNum;
        loc = loc + "&Company=" + window.document.MainForm.CompanyId.value;
        openWinGeneric(loc,"MaterialId","600","420","yes")
	  }
	  else
	  {
		alert("There is no Manufacturer Associated to choose Material");
	  }
    }
}

function ShowOriginal(entered)
{
    with (entered)
    {
        loc = "/tcmIS/Catalog/catqcoriginal?Action=Original&request_id=";
        loc = loc + window.document.MainForm.request_id.value;
        loc = loc + "&Company=" + window.document.MainForm.CompanyId.value;
        openWinGeneric(loc,"Original","650","450","yes")
    }
}

function getrevdate(entered,CompNum)
{
    with (entered)
    {
        loc = "/tcmIS/Catalog/likerevdate?Action=findlike&materialid=";
        eval(compForm2  =  "window.document.ComponentForm" + (CompNum+1).toString() + ".materialid" +CompNum.toString() );
        ( matdesc =  (eval(compForm2.toString()).value ) );

	  if (matdesc.length > 0)
	  {
        loc = loc + matdesc;
        loc = loc + "&CompNum=" + CompNum;
        loc = loc + "&Company=" + window.document.MainForm.CompanyId.value;
        openWinGeneric(loc,"MaterialId","300","200","no")
	  }
        else
	  {
		alert("There is no Material Associated to choose Revision Date");
	  }
    }
}


function getCasNum(entered)
{
    with (entered)
    {
        loc = "/tcmIS/Catalog/likecasnum?Action=Nothing&request_id=";
        loc = loc + window.document.MainForm.request_id.value;
        loc = loc + "&Company=" + window.document.MainForm.CompanyId.value;
        openWinGeneric(loc,"CasNum","600","420","yes")
    }
}

function ShowMSDS(entered, CompNum)
{
    var loc
        with (entered)
    {
        eval(compForm2  =  "window.document.ComponentForm" + (CompNum+1).toString() + ".materialid" +CompNum.toString() );
        ( matid =  (eval(compForm2.toString()).value ) );
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
else if (company == "MILLER")
        {
            loc = "/tcmIS/miller/ViewMsds?cl=Miller&showspec=N&act=msds&id="
                loc = loc + matid;
        }
else if (company == "FEC")
        {
            loc = "/tcmIS/fec/ViewMsds?cl=fec&showspec=N&act=msds&id="
                loc = loc + matid;
        }
else if (company == "GM")
        {
            loc = "/tcmIS/gm/ViewMsds?cl=gm&showspec=N&act=msds&id="
                loc = loc + matid;
        }
else if (company == "BOEING")
        {
            loc = "/tcmIS/ula/ViewMsds?cl=Boeing&showspec=N&act=msds&id="
                loc = loc + matid;
        }
	  else
	  {
		loc = "/tcmIS/ray/ViewMsds?cl=Ray&showspec=N&act=msds&id="
            loc = loc + matid;

	  }
    }
    openWinGeneric(loc,"MSDS","800","650","yes")
}


function Showfile(entered, CompNum)
{
    var loc
        with (entered)
    {
        eval(compForm2  =  "window.document.ComponentForm" + (CompNum+1).toString() + ".content" +CompNum.toString() );
        ( content =  (eval(compForm2.toString()).value ) );
        loc = content;
    }
    if (loc.length > 1)
    {
    openWinGeneric(loc,"VIEW_FILE","800","650","yes")
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