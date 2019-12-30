function showlegend()
{
	openWinGeneric("/tcmIS/help/pospeclegend.html","pospecslegend","250","325","no","50","50")
}

function getSecondarySupplier(rowNumber)
{
    //loc = "/tcmIS/purchase/posupplier?suppID=";
    loc = "/tcmIS/distribution/entitysuppliersearchmain.do?secondarySupplier=Y&valueElementId=supplierid&statusFlag=true&displayElementId=supplierName&autoSearch=y";
	var suppid  =  document.getElementById("secondarysupplier"+rowNumber+"");
    var invengrp  =  document.getElementById("invengrp");
    var opsEntityId  =  document.getElementById("opsEntityId");

    var supplierrep = suppid.value;
    supplierrep = supplierrep.replace(/&/gi, "%26");
    supplierrep = supplierrep.replace(/#/gi, "%23");

    //loc = loc + supplierrep;
    loc = loc + "&supplierName="+supplierrep.value;
    loc = loc + "&secondarySupplier=Y";
    loc = loc + "&rowNumber="+rowNumber;
    loc = loc + "&inventoryGroup="+invengrp.value;
    loc = loc + "&opsEntityId=" + opsEntityId.value;
    openWinGeneric(loc,"SupplierID","800","600","yes","50","50");
}

function showItemexpeditenotes(rowNumber)
{
   //loc = "https://www.tcmis.com/cgi-bin/purch/buy_history.cgi?item=";
   var itemID  =  document.getElementById("lineitemid"+rowNumber+"");

    if ( (itemID.value.length > 0 && itemID.value*1 >0) )
    {
        var po  =  document.getElementById("po");
        var lineNumber = document.getElementById("row"+rowNumber+"linenumber");
        loc = "/tcmIS/supply/polineexpeditehistory.do?itemId="+itemID.value+"&action=search";
        openWinGeneric(loc,"showItemexpeditenotesddd","900","400","yes","120","140")
    }
    else
    {

    }
}

function showSoucingInfo(rowNumber)
{
	var itemID  =  document.getElementById("lineitemid"+rowNumber+"");
	
	if ( (itemID.value.length > 0 && itemID.value*1 >0) ) {
	
//		var suppid  =  document.getElementById("secondarysupplier"+rowNumber+"");
    	var inventoryGroup  =  document.getElementById("invengrp");
    	var opsEntityId  =  document.getElementById("opsEntityId");

    	var suppid  =  document.getElementById("supplierid");
    	var supplierName = document.getElementById("supplierline1").innerText;
    	supplierName = supplierName.replace(/&/gi, "%26");
    	supplierName = supplierName.replace(/#/gi, "%23");
		
		var hub  =  document.getElementById("HubName");
		loc = "/tcmIS/distribution/editpricelist.do?uAction=search&searchField=itemId|number&searchMode=is&searchArgument=" + itemID.value;
		loc = loc + "&supplier=" + suppid.value;
		loc = loc + "&supplierName=" + supplierName;
		loc = loc + "&inventoryGroup=" + inventoryGroup.value;
		loc = loc + "&hub=" + hub.value;
	    loc = loc + "&opsEntityId=" + opsEntityId.value;
	    openWinGeneric(loc, "showSourcingInfo", "1024", "600", "yes", "50", "50");
    }

	
}

function showexpeditenotes(rowNumber)
{
   //loc = "https://www.tcmis.com/cgi-bin/purch/buy_history.cgi?item=";   
   var itemID  =  document.getElementById("lineitemid"+rowNumber+"");

    if ( (itemID.value.length > 0 && itemID.value*1 >0) )
    {
        var po  =  document.getElementById("po");
        var lineNumber = document.getElementById("row"+rowNumber+"linenumber");
        loc = "/tcmIS/supply/polineexpeditehistory.do?radianPo="+po.value+"&poLine="+lineNumber.value*1000+"&action=search";
        openWinGeneric(loc,"showexpedintingnotes","900","400","yes","120","140")
    }
    else
    {

    }
}

function enterExpediteNotes(rowNumber)
{
  //https://www.tcmis.com/cgi-bin/purch/expedite.cgi?po=768345&rec=X&not_rec=X&bp=2202&item_id=502372&line=1000
    //loc = "https://www.tcmis.com/cgi-bin/purch/expedite.cgi?";
    var po  =  document.getElementById("po");
 	var validpo  =  document.getElementById("validpo");
    var HubName  =  document.getElementById("HubName");
    var lineNumber = document.getElementById("row"+rowNumber+"linenumber");
    var itemID  =  document.getElementById("lineitemid"+rowNumber+"");

    if ( validpo.value != "No" && (po.value.length > 0) && (itemID.value.length > 0 && itemID.value*1 >0) && (HubName.value.length > 0 && HubName.value*1 >0))
    {
        loc="/tcmIS/supply/newpoexpediting.do?action=searchnewpoexpedite&radianPo="+po.value+"&poLine="+lineNumber.value*1000+"";
        openWinGeneric(loc,"enterExpediteNotesin"+po.value.replace('.','a')+"","900","400","yes","100","100")
    }
}

function enterPoExpediteNotes()
{
  //https://www.tcmis.com/cgi-bin/purch/expedite.cgi?po=768345&rec=X&not_rec=X&bp=2202&item_id=502372&line=1000
    //loc = "https://www.tcmis.com/cgi-bin/purch/expedite.cgi?";
    var po  =  document.getElementById("po");
 	var validpo  =  document.getElementById("validpo");
    var HubName  =  document.getElementById("HubName");
    //var lineNumber = document.getElementById("row"+rowNumber+"linenumber");
    //var itemID  =  document.getElementById("lineitemid"+rowNumber+"");

    if ( validpo.value != "No" && (po.value.length > 0) && (HubName.value.length > 0 && HubName.value*1 >0))
    {
        loc="/tcmIS/supply/newpoexpediting.do?action=searchnewpoexpedite&radianPo="+po.value+"";
        openWinGeneric(loc,"enterExpediteNotesin"+po.value.replace('.','a')+"","900","400","yes","100","100")
    }
}


function showemailnotes()
{
    var povalue  =  document.getElementById("po");
	 var validpo  =  document.getElementById("validpo");
	 var bpovalue  =  document.getElementById("bpo");
	 var validbpo  =  document.getElementById("validbpo");

   /* if ( (povalue.value.trim().length > 0) && (validpo.value == "No") )
    {
		alert("Please Choose a Valid PO.");
    }
	 else if ( (bpovalue.value.trim().length > 0) && (validbpo.value == "No") )
    {
		alert("Please Choose a Valid BO.");
    }
	 else*/
    {
	    if (povalue.value.trim().length > 0)
	    {
			  loc = "/tcmIS/purchase/ponotes?radianPO=";
	  	     loc = loc + povalue.value;
	    	  loc = loc + "&Action=emailview&subUserAction=po";
	        openWinGeneric(loc,"showEmailnotes","100","100","yes","50","50")
	    }
	    else if (bpovalue.value.trim().length > 0)
	    {
			  loc = "/tcmIS/purchase/ponotes?radianbPO=";
	  	     loc = loc + bpovalue.value;
	    	  loc = loc + "&Action=emailview&subUserAction=bpo";
	    	  alert(loc);
	        openWinGeneric(loc,"showEmailnotes","100","100","yes","50","50")
	    }
    }
}

function showPonotes()
{
    var povalue  =  document.getElementById("po");
	 var validpo  =  document.getElementById("validpo");
	 var bpovalue  =  document.getElementById("bpo");
	 var validbpo  =  document.getElementById("validbpo");

/*    if ( (povalue.value.trim().length > 0) && (validpo.value == "No") )
    {
		alert("Please Choose a Valid PO.");
    }
	 else if ( (bpovalue.value.trim().length > 0) && (validbpo.value == "No") )
    {
		alert("Please Choose a Valid BO.");
    }
	 else*/
    {
	    if (povalue.value.trim().length > 0)
	    {
			  loc = "/tcmIS/purchase/ponotes?radianPO=";
	  	     loc = loc + povalue.value;
	    	  loc = loc + "&Action=view&subUserAction=po";
	        openWinGeneric(loc,"showPonotes","100","100","no","50","50")
	    }
	    else if (bpovalue.value.trim().length > 0)
	    {
			  loc = "/tcmIS/purchase/ponotes?radianbPO=";
	  	     loc = loc + bpovalue.value;
	    	  loc = loc + "&Action=view&subUserAction=bpo";
	        openWinGeneric(loc,"showPonotes","100","100","no","50","50")
	    }
    }
}

function gettcmBuysmore(numdetrow)
{
    loc = "/tcmIS/purchase/showtcmbuys?itemID=";

    var itemID  =  document.getElementById("lineitemid"+numdetrow+"");
    var itemnotestatus  =  document.getElementById("tcmbuystatus"+numdetrow+"");
    var opsEntityId  =  document.getElementById("opsEntityId");

        var itemnotesimgae = document.getElementById("tcmbuyimg"+numdetrow+"");
        itemnotesimgae.setAttribute("src",'/images/minus.jpg');

        loc = loc + itemID.value;
        loc = loc + "&Action=view";
        loc = loc + "&lineID=" + numdetrow;
        loc = loc + "&opsEntityId=" + opsEntityId.value;

          var shiptoid  =  document.getElementById("shiptoid");
  		  loc = loc  + "&shiptoLoc=" + shiptoid.value;

        itemnotestatus.value = "Yes";
        openWinGeneric(loc,"tcmbuyhistory","100","100","no","50","50")
}

function getsuggestedSupplier(partnum,requestid,catalogid)
{
    loc = "/tcmIS/purchase/posupplier?Action=suggestedsupp";
    loc = loc + "&catpartno=" + partnum + "&requestid=" +requestid+ "&catalogid=" +catalogid;
    openWinGeneric(loc,"suggestedsupplier","450","200","yes","200","200")
}

function showSupplierContacts(suppliername)
{
   suppliername = suppliername.replace(/#/gi, "%23");
   suppliername = suppliername.replace(/&/gi, "%26");

	loc = "/tcmIS/purchase/poordertaker?Action=showallcontacts&suppID=" + suppliername;
	openWinGeneric(loc,"shoSupplierContacts","100","100","yes","340","380")
}

function openWinGeneric(destination,WinName,WinWidth, WinHeight,Resizable,topCoor,leftCoor )
{
    windowprops = "toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,status=yes,top="+topCoor+",left="+leftCoor+",width="+WinWidth+",height="+WinHeight+",resizable="+ Resizable;
    preview = window.open(destination, WinName,windowprops);
}

function changeCalanderInfo(entered,rowname)
{
setlineUnconfirmed(rowname);
return getCalendar(entered);
}

function getCannedComments(rowname)
{
	 loc = "/tcmIS/purchase/showcannedcomments?lineID=" + rowname;
    openWinGeneric(loc,"SupplierID","100","100","no","50","50")
}

function showschedulde(mrnumber,lineitem)
{
    loc = "/tcmIS/purchase/showtcmbuys?Action=showschedulde&itemID=";
    loc = loc + mrnumber;
    loc = loc + "&lineID="+lineitem;

    openWinGeneric(loc,"tcmbuyhistory","400","460","yes","80","80")
}

function printpo()
{
    var validpo  =  document.getElementById("validpo");
    var po  =  document.getElementById("po");
    var HubName  =  document.getElementById("HubName");   
    var bpovalue  =  document.getElementById("bpo");
    var validbpo  =  document.getElementById("validbpo");
    
    var supplierCountryAbbrev =  document.getElementById("supplierCountryAbbrev").value;
    var opsEntityId =  document.getElementById("opsEntityId").value;

    if ( (po.value.trim().length > 0) && (validpo.value == "No") )
    {
	   alert(messagesData.validpo);
    }
    else if (po.value.trim().length > 0)
    {
		 var tmp = "";
		 while (true) {
			tmp = prompt(messagesData.chooseprintpo,tmp);
			if (tmp == null) {	//user hit cancel
				break;
			}
			//determine whether the user enter the correct value
			if (tmp == "0" || tmp == "1" || tmp == "2" || tmp == "3" || tmp == "4") {
				break;
			}else {
				alert(messagesData.chooseprintpo);
			}
		 }	//end of while

         var tmpLocale = "";
         if (HubName.value == "2403" || HubName.value == "2470")
         {
         while (true) {
			tmpLocale = prompt("Język wydruku: proszę wybrać wydruk '1' po polsku '2' po angielsku",tmpLocale);
			if (tmpLocale == null) {	//user hit cancel
				break;
			}
			//determine whether the user enter the correct value
			if (tmpLocale == "1" || tmpLocale == "2") {
                if (tmpLocale == "1") {
				tmpLocale = "pl_PL";
			}else{
				tmpLocale = "";
			}                
                break;
			}else {
				alert("Język wydruku: proszę wybrać wydruk '1' po polsku '2' po angielsku");
			}
		 }	//end of while
         }


         if ( HubName.value == "2186" || HubName.value == "2208" || HubName.value == "2188"
              || HubName.value == "2189" || HubName.value == "2190" || HubName.value == "2402" || HubName.value == "2400")
         {
         while (true) {
			tmpLocale = prompt("Please pick the PO Print Language:'1' Deutsch '2' English",tmpLocale);
			if (tmpLocale == null) {	//user hit cancel
				break;
			}
			//determine whether the user enter the correct value
			if (tmpLocale == "1" || tmpLocale == "2") {
                if (tmpLocale == "1") {
				tmpLocale = "de_DE";
			}else{
				tmpLocale = "";
			}
                break;
			}else {
				alert("Please pick the PO Print Language:'1' Deutsch '2' English");
			}
		 }
         }

        if ( HubName.value == "2453" )
         {
         while (true) {
			tmpLocale = prompt("S'il vous plaît prendre le PO Imprimer Langue: '1' Français '2' Anglais",tmpLocale);
			if (tmpLocale == null) {	//user hit cancel
				break;
			}
			//determine whether the user enter the correct value
			if (tmpLocale == "1" || tmpLocale == "2") {
                if (tmpLocale == "1") {
				tmpLocale = "fr_FR";
			}else{
				tmpLocale = "";
			}
                break;
			}else {
				alert("S'il vous plaît prendre le PO Imprimer Langue: '1' Français '2' Anglais");
			}
		 }
         }
        
         if (tmp != null)
		 {
			if (tmp == "0")
		   {
				if (actionValue("printdraft",this))
				{
					//alert("Print PO");
				}
				else
				{
					//alert("Do Not Print PO");
					return;
				}
		   }
			if(supplierCountryAbbrev == 'CHN' && opsEntityId =='AVICHAAS')
			{
			   tmpLocale="zh_CN";
			   //loc= "/HaasReports/report/printporeport.do?po="
			}
						
	 	 	   loc = "/tcmIS/supply/faxpo?Session=7&po=";
			
							
	    	loc = loc + po.value;
	    	//12-11-02 past info to server: 1 - New Order, 2 - Confirming Order and 3 - Admended Order
	    	loc = loc + "&HeaderNote="+tmp+"&localeCode="+tmpLocale;
	    	
	    	openWinGeneric(loc,"printpo","700","600","yes","200","200")
		 }
		 //openWinGeneric(loc,"SupplierID","700","600","yes","50","50")
    }
    else if ( (bpovalue.value.trim().length > 0) && (validbpo.value != "No") )
    {
		 var tmp = "";
		 while (true) {
			tmp = prompt(messagesData.chooseprintpo,tmp);
			if (tmp == null) {	//user hit cancel
				break;
			}
			//determine whether the user enter the correct value
			if (tmp == "1" || tmp == "2" || tmp == "3") {
				break;
			}else {
				alert(messagesData.chooseprintpo);
			}
		 }	//end of while

		if (tmp != null) {
			loc = "/tcmIS/supply/faxpo?Session=7&bpo=";
			//loc= "/HaasReports/report/printporeport.do?po="
				
	      loc = loc + bpovalue.value;
	      //12-11-02 past info to server: 1 - New Order, 2 - Confirming Order and 3 - Admended Order
	      loc = loc + "&HeaderNote="+tmp;
	      openWinGeneric(loc,"printbpo","700","600","yes","200","200")
		}	//end of if
    }
}


function getSupplier(entered)
{
    document.getElementById("addBuyOrders").style["display"] = "none";
    var validpo  =  document.getElementById("validpo");
	var po  =  document.getElementById("po");
	var validbpo  =  document.getElementById("validbpo");
    var inventoryGroup  =  document.getElementById("invengrp");
    var opsEntityId  =  document.getElementById("opsEntityId");    
     //if ( (po.value.trim().length > 0) && (validpo.value == "No") )
	if ( validpo.value == "No" &&  validbpo.value !="Yes" )
    {
		alert("Please choose a valid PO.");
    }
    else if (inventoryGroup.value.trim().length == 0 || opsEntityId.value.trim().length == 0)
    {
		alert("Please choose a valid Ship To.");
    }
    else
    {
    //loc = "/tcmIS/purchase/posupplier?suppID=";

    loc = "/tcmIS/distribution/entitysuppliersearchmain.do?valueElementId=supplierid&statusFlag=true&displayElementId=supplierName&autoSearch=y";

	var suppid  =  document.getElementById("supplierid");
    var supplierrep = suppid.value;
    supplierrep = supplierrep.replace(/&/gi, "%26");
    supplierrep = supplierrep.replace(/#/gi, "%23");

    //loc = loc + supplierrep;
    loc = loc + "&supplier=" + supplierrep;
    loc = loc + "&supplierName=" + supplierrep;
    loc = loc + "&inventoryGroup=" + inventoryGroup.value;
    loc = loc + "&opsEntityId=" + opsEntityId.value;
    //loc = loc + suppid.value;
    //loc = loc + "&item=" + itemID;
    openWinGeneric(loc,"SupplierID","800","600","yes","50","50")
}
}


function getItemDetail(rowname)
{
	 var validshipto  =  document.getElementById("validshipto");
	 var HubName  =  document.getElementById("HubName");
	 var shiptoid  =  document.getElementById("shiptoid");
	 var shiptocompanyid  =  document.getElementById("shiptocompanyid");
    var povalue  =  document.getElementById("po");
	 var validpo  =  document.getElementById("validpo");
	 var numofHubs  =  document.getElementById("numofHubs");

	 var bpovalue  =  document.getElementById("bpo");
	 var validbpo  =  document.getElementById("validbpo");
	 var invengrp  =  document.getElementById("invengrp");

    //if ( (povalue.value.trim().length > 0) && (validpo.value == "No") )
    if ( validpo.value == "No" &&  validbpo.value !="Yes" )
    {
			alert("Please Choose a Valid PO.");
    }
    else if (validshipto.value == "No")
    {
	     alert("Please Choose a Valid Ship To.");
	 }
	 else if (invengrp.value.trim().length == 0)
    {
	     alert("Please Choose a Valid Inventory Group.");
	 }
	 else
	 {
    loc = "/tcmIS/purchase/poitem?lineID=";
    loc = loc + rowname;
    var itemID  =  document.getElementById("lineitemid"+rowname+"");
    loc = loc + "&itemID=" + itemID.value;
    loc = loc + "&Company=" + shiptocompanyid.value;
    loc = loc + "&shiptoid=" + shiptoid.value;
	 loc = loc + "&invengrp=" + invengrp.value;

	 if ( (bpovalue.value.trim().length > 0) && (validbpo.value == "Yes") )
	 {
		loc = loc + "&validbpo=yes";
	 }
    //loc = loc + "&item=" + itemID;
    openWinGeneric(loc,"lineID","100","100","no","50","50")
    }
}

function getChargeItemDetail(rowname)
{
	 var validshipto  =  document.getElementById("validshipto");
	 var HubName  =  document.getElementById("HubName");
	 var shiptoid  =  document.getElementById("shiptoid");
	 var shiptocompanyid  =  document.getElementById("shiptocompanyid");
    var povalue  =  document.getElementById("po");
	 var validpo  =  document.getElementById("validpo");
	 var numofHubs  =  document.getElementById("numofHubs");

	 var bpovalue  =  document.getElementById("bpo");
	 var validbpo  =  document.getElementById("validbpo");
    var invengrp  =  document.getElementById("invengrp");

    //if ( (povalue.value.trim().length > 0) && (validpo.value == "No") )
    if ( validpo.value == "No" &&  validbpo.value !="Yes" )
    {
			alert("Please Choose a Valid PO.");
    }
    else if (validshipto.value == "No")
    {
	     alert("Please Choose a Valid Ship To.");
	 }
	 else if (invengrp.value.trim().length == 0)
    {
	     alert("Please Choose a Valid Inventory Group.");
	 }
    /*else if (HubName.value == "None")
    {
        alert("Please Choose a Hub");
    }*/
	 else
	 {
    loc = "/tcmIS/purchase/poitem?lineID=";
    loc = loc + rowname;
    var itemID  =  document.getElementById("lineitemid"+rowname+"");
    loc = loc + "&itemID=" + itemID.value;
    loc = loc + "&subUserAction=lineCharge";
	 loc = loc + "&invengrp=" + invengrp.value;

	 if ( (bpovalue.value.trim().length > 0) && (validbpo.value == "Yes") )
	 {
		loc = loc + "&validbpo=yes";
	 }
    //loc = loc + "&item=" + itemID;
    openWinGeneric(loc,"lineID","100","100","yes","105","100")
    }
}

function getPo(entered)
{
    loc = "/tcmIS/supply/purchorder?po=";
    //loc = "/tcmIS/purchase/popo?radianPO=";

    var po  =  document.getElementById("po");
    if (po.value.trim().length > 0)
    {
	    if ( isFloat(po.value.trim()) )
	    {
	      loc = loc + po.value.trim();
        try
        {
          parent.setTabName(window.frameElement.id,"Purchase Order "+po.value.trim()+"");
        }
        catch(ex)
        {
         //alert("Here can not find parent 408");
        }        
      }
	    else
	    {
	      loc = loc + "";
	    }
	 }
	 else
    {
	   loc = loc + "";
    }

    loc = loc + "&Action=searchlike&subUserAction=po";
    //loc = loc + "&Action=po&subUserAction=po";

    var HubName  =  document.getElementById("HubName");
    if (HubName.value != "None")
    {
        loc = loc + "&HubName="+ HubName.value;
    }

	 makeCursorBusy();
	 document.forms[0].action = loc;
	 document.location= loc;
    //openWinGeneric(loc,"radianPO","50","50","yes")
}

function refreshAddCharges(rowname)
{
    var allClear = 0;

    var povalue  =  document.getElementById("po");
    if (povalue.value.length > 0)
    {
        var validpo  =  document.getElementById("validpo");
        if (validpo.value == "No")
        {
            allClear = 1;
        }
    }

    var bpovalue  =  document.getElementById("bpo");
    if (bpovalue.value.length > 0)
    {
        var validbpo  =  document.getElementById("validbpo");
        if (validbpo.value == "No")
        {
            allClear = 1;
        }
    }

    if (!(povalue.value.length > 0) && !(bpovalue.value.length > 0) )
    {
        allClear = 1;
    }

    if (allClear < 1)
    {
        //setlineUnconfirmed(rowname);

        loc = "/tcmIS/purchase/popo?radianPO=";
        var po  =  document.getElementById("po");
        loc = loc + po.value;
        loc = loc + "&Action=refreshAddCharges";
        loc = loc + "&lineID=" + rowname;

        var ammendment = document.getElementById("ammendment"+rowname+"");
        loc = loc + "&ammendment=" + ammendment.value;
		  loc = loc  + "&radianBPOadd=" + bpovalue.value;
		  //alert(ammendment.value);
		  makeCursorBusy();
        openWinGeneric(loc,"refrdCharges","100","100","no","50","50")

    }
    else
    {
        refreshlinecharges(rowname,'Yes');

    }
}

function getnewPo(entered)
{
    var validbpo  =  document.getElementById("validbpo");
    if (validbpo.value == "No")
    {
        loc = "/tcmIS/supply/purchorder?po=";
        //loc = "/tcmIS/purchase/popo?radianPO=";

        var po  =  document.getElementById("po");
        loc = loc + po.value;
        loc = loc + "&Action=searchlike&subUserAction=newpo";
        //loc = loc + "&Action=po&subUserAction=po";

        var HubName  =  document.getElementById("HubName");
        if (HubName.value != "None")
        {
            loc = loc + "&HubName="+ HubName.value;
        }
		  makeCursorBusy();
        document.location=loc;
    }
    else
    {
        loc = "/tcmIS/purchase/popo?newpobpo=";
        var bpo  =  document.getElementById("bpo");
        loc = loc + bpo.value;
        loc = loc + "&Action=new&subUserAction=po";
		  makeCursorBusy();
        openWinGeneric(loc,"newradianPO","100","100","no","50","50")
    }
}

function recalPo (poNumber,message,subuserAction,hubName)
{
    loc = "/tcmIS/purchase/popo?radianPO=";

	 if (isFloat(poNumber))
    {
	    loc = loc + poNumber;
	 }
	 else
	 {
		 loc = loc + "";
	 }
    loc = loc + "&Action=recalpo&subUserAction=" + subuserAction;
    loc = loc + "&updateMsg="+ message;
    loc = loc + "&HubName="+ hubName;

	makeCursorBusy();
    try{
     parent.resetTimer();
   }
   catch (ex)
   {
   }
   openWinGeneric(loc,"radianPO"+poNumber.replace('.','a')+"","100","100","yes","50","50")
}

function recalnewPo (subuserAction)
{
    loc = "/tcmIS/purchase/popo?";
    loc = loc + "Action=new&subUserAction=" + subuserAction;

	 makeCursorBusy();
    openWinGeneric(loc,"radianPO","100","100","no","50","50")
}

function getbpo(entered)
{
    loc = "/tcmIS/supply/purchorder?bpo=";
    //loc = "/tcmIS/purchase/popo?radianPO=";

    var po  =  document.getElementById("bpo");

	 if (po.value.trim().length > 0)
    {
	    if ( isFloat(po.value.trim()) )
	    {
	    loc = loc + po.value.trim();
	    }
	    else
	    {
	      loc = loc + "";
	    }
	 }
	 else
    {
		loc = loc + "";
    }
    loc = loc + "&Action=searchlike&subUserAction=bpo";
    //loc = loc + "&Action=po&subUserAction=bpo";

	 makeCursorBusy();
    document.location=loc;
    //openWinGeneric(loc,"radianBPO","50","50","yes")
}

function recalbPo(poNumber)
{
    loc = "/tcmIS/purchase/popo?radianPO=";

    if (isFloat(poNumber))
    {
	    loc = loc + poNumber;
	 }
	 else
	 {
		loc = loc + "";
	 }
    loc = loc + "&Action=po&subUserAction=bpo";

	 makeCursorBusy();
    openWinGeneric(loc,"radianBPO","100","100","yes","50","50")
}

function getnewbpo(entered)
{

    loc = "/tcmIS/supply/purchorder?bpo=";
    //loc = "/tcmIS/purchase/popo?radianPO=";

    var po  =  document.getElementById("bpo");
    loc = loc + po.value;
    loc = loc + "&Action=searchlike&subUserAction=newbpo";
    //loc = loc + "&Action=po&subUserAction=bpo";

	 makeCursorBusy();
    document.location=loc;

    /*loc = "/tcmIS/purchase/popo?radianPO=";

    var po  =  document.getElementById("bpo");
    loc = loc + po.value;
    loc = loc + "&Action=new&subUserAction=bpo";

    openWinGeneric(loc,"newradianBPO","100","100","no","50","50")*/
}


function enterSoleSourcePriceQuote(rowNumber)
{
    //https://www.tcmis.com/cgi-bin/purch/ss_enter.cgi?777777-1000=Y
    loc = "https://www.tcmis.com/cgi-bin/purch/ss_enter.cgi?";
	 var po  =  document.getElementById("po");
	 var validpo  =  document.getElementById("validpo");

    var lineNumber = document.getElementById("row"+rowNumber+"linenumber");

    if ( validpo.value != "No" && po.value.length > 0)
    {
        loc = loc + po.value+ "-"+(lineNumber.value*1000)+"=Y";
        openWinGeneric(loc,"enterSoleSourcePriceQuotein","300","300","yes","300","300")
    }
    else
    {

    }
}

function showPOLineHistory(rowNumber)
{
    loc = "/tcmIS/purchase/polinehistory?radianPO1=0";
	 var po  =  document.getElementById("po");
    var bpo  =  document.getElementById("bpo");



    var lineNumber = document.getElementById("row"+rowNumber+"linenumber");
    var ammendment = document.getElementById("ammendment"+rowNumber+"");

    if ( ( (po.value.length > 0)
          || (bpo.value.length > 0) ) && (ammendment.value > 0) )
    {
        loc = loc + "&poline=" + lineNumber.value;
        loc = loc + "&ammendment=" + ammendment.value;

        if ( po.value.length > 0)
        {
            loc = loc + "&Action=view&subUserAction=po";
            loc = loc + "&radianPO="+ po.value;
        }
        else if (bpo.value.length > 0)
        {
            loc = loc + "&Action=view&subUserAction=bpo";
            loc = loc + "&radianPO=" + bpo.value;
        }

        openWinGeneric(loc,"showPOLineHistory","50","50","yes","120","140")
    }
    else
    {

    }
}

function getcarrier(entered)
{
    document.getElementById("addBuyOrders").style["display"] = "none";
    var validshipto  =  document.getElementById("validshipto");
    var HubName  =  document.getElementById("HubName");
    var invengrp  =  document.getElementById("invengrp");

    if ( (validshipto.value == "Yes") && (invengrp.value.trim().length >0) )
    {
        loc = "/tcmIS/purchase/pocarrier?carrierID=";

        var carrierID  =  document.getElementById("carrierID");
        loc = loc + carrierID.value;

        var invengrp  =  document.getElementById("invengrp");
	     loc = loc + "&inventoryGroup=" + invengrp.value;

        /*var shiptoid  =  document.getElementById("shiptoid").value;
		  shiptoid = shiptoid.replace(/#/gi, "%23");
        shiptoid = shiptoid.replace(/&/gi, "%26");
        loc = loc + "&HubName=" + HubName.value;
        loc = loc + "&shiptoid=" + shiptoid;*/
        openWinGeneric(loc,"carrierID","50","50","yes","130","160")
    }
    else if ( validshipto.value != "Yes" )
    {
        alert("Please Select a Valid Ship To.");
    }
    else if ( invengrp.value.trim().length == 0 )
    {
        alert("Please Select a Valid Inventory Group.");
    }

}

function getorderTaker(entered)
{
    document.getElementById("addBuyOrders").style["display"] = "none";
    loc = "/tcmIS/purchase/poordertaker?suppID=";

    var validsupplier  =  document.getElementById("validsupplier");
    if (validsupplier.value == "No")
    {
        alert("Please Select a Valid Supplier.");
    }
    else
    {
	     var suppid  =  document.getElementById("supplierid");
		  var supplierrep = suppid.value;
	     supplierrep = supplierrep.replace(/&/gi, "%26");
		  supplierrep = supplierrep.replace(/#/gi, "%23");

		  loc = loc + supplierrep;
        //loc = loc + suppid.value;

        var ordertaker  =  document.getElementById("ordertaker");
        loc = loc +"&Ordertaker=" + ordertaker.value;

        openWinGeneric(loc,"SupplierID","50","50","yes","140","180")
    }
}

function getshipTo(entered)
{
	document.getElementById("addBuyOrders").style["display"] = "none";
    var validpo  =  document.getElementById("validpo");
    var po  =  document.getElementById("po");
    var validbpo  =  document.getElementById("validbpo");
    var invengrp  =  document.getElementById("invengrp");

	 //if ( (po.value.trim().length > 0) && (validpo.value == "No") )
	 if ( validpo.value == "No" &&  validbpo.value !="Yes" )
	 {
			alert("Please Choose a Valid PO.");
    }
    else
    {
    loc = "/tcmIS/purchase/poshipto?shiptoID=";

    var shiptoid  =  document.getElementById("shiptoid").value;
    shiptoid = shiptoid.replace(/#/gi, "%23");
    shiptoid = shiptoid.replace(/&/gi, "%26");

    loc = loc + shiptoid;
	  loc = loc + "&invengrp=" + invengrp.value;

    var shiptocompanyid  =  document.getElementById("shiptocompanyid").value;
    loc = loc + "&shiptocompanyid=" + shiptocompanyid;
      
   /*var suppid  =  document.getElementById("supplierid");
    var supplierrep = suppid.value;
    supplierrep = supplierrep.replace(/&/gi, "%26");
    supplierrep = supplierrep.replace(/#/gi, "%23");
    loc = loc + "&supplierId="+ supplierrep;*/

    //loc = loc + "&item=" + itemID;
    openWinGeneric(loc,"ShipToID","50","50","yes","150","120")
    }
}


function getpartNotes(numdetrow)
{
    loc = "/tcmIS/purchase/itemnotes?itemID=";

    var itemID  =  document.getElementById("lineitemid"+numdetrow+"");
    var partnotestatus  =  document.getElementById("partnotestatus"+numdetrow+"");
    var invengrp  =  document.getElementById("invengrp");

    if ( (itemID.value.length > 0 && itemID.value*1 >0) && (partnotestatus.value == "No") && (invengrp.value.length > 0)  )
    {
        var partnotesimage = document.getElementById("partnotesimage"+numdetrow+"");
        partnotesimage.setAttribute("src",'/images/minus.jpg');

        loc = loc + itemID.value;
        loc = loc + "&Action=partnotes";
        loc = loc + "&lineID=" + numdetrow;
        loc = loc + "&invengrp=" + invengrp.value;

        partnotestatus.value = "Yes";
        openWinGeneric(loc,"ShipToID","100","100","no","50","50")
    }
    else if ( partnotestatus.value == "Yes" )
    {
        var itemnotestable  =  document.getElementById("partdetailtable"+numdetrow+"");
        itemnotestable.className =  "displaynone";

        var partnotesimage = document.getElementById("partnotesimage"+numdetrow+"");
        partnotesimage.setAttribute("src",'/images/plus.jpg');

        var partnotestatus  =  document.getElementById("partnotestatus"+numdetrow+"");
        partnotestatus.value = "No";
    }
}


function getItemNotes(numdetrow)
{
    /*var spanElm = document.getElementById("row5detaildivrow11");
    var insidehtml ="<TABLE BORDER=\"0\" width=\"100%\" CELLPADDING=\"1\" CELLSPACING=\"1\"> ";
    insidehtml +="<TR>";
    insidehtml +="<TH WIDTH=\"5%\"><B>Item Id</B></FONT></TH>";
    insidehtml +="<TH WIDTH=\"45%\"><B>Item Desc</B></FONT></TH>";
    insidehtml +="<TH WIDTH=\"25%\"><B>Packaging</B></FONT></TH>";
    insidehtml +="</TR></TABLE>";
    spanElm.innerHTML =insidehtml;*/

    //alert("Show Item NOtes!");
    loc = "/tcmIS/purchase/itemnotes?itemID=";


    var itemID  =  document.getElementById("lineitemid"+numdetrow+"");

    var itemnotestatus  =  document.getElementById("itemnotestatus"+numdetrow+"");
    //alert(itemnotestatus.value);

    if ( (itemID.value.length > 0 && itemID.value*1 >0) && (itemnotestatus.value == "No") )
    {
        var itemnotesimgae = document.getElementById("itemnotesimage"+numdetrow+"");
        itemnotesimgae.setAttribute("src",'/images/minus.jpg');

        loc = loc + itemID.value;
        loc = loc + "&Action=view";
        loc = loc + "&lineID=" + numdetrow;
        itemnotestatus.value = "Yes";
        openWinGeneric(loc,"ShipToID","100","100","no","50","50")
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

function addItemNotes(numdetrow)
{
    //alert("Add Item NOtes!");
    //loc = "/tcmIS/purchase/itemnotes?itemID=";
    loc = "/tcmIS/supply/edititemnotes.do?itemId=";
    var itemID  =  document.getElementById("lineitemid"+numdetrow+"");
    if (itemID.value.length > 0 && itemID.value*1 >0)
    {
        loc = loc + itemID.value;
        //loc = loc + "&Action=addnew";
        //loc = loc + "&lineID=" + numdetrow;

        var itemnotestable  =  document.getElementById("linedetailtable"+numdetrow+"");
        itemnotestable.className =  "displaynone";

        var itemnotesimgae = document.getElementById("itemnotesimage"+numdetrow+"");
        itemnotesimgae.setAttribute("src",'/images/plus.jpg');

        var itemnotestatus  =  document.getElementById("itemnotestatus"+numdetrow+"");
        itemnotestatus.value = "No";

        openWinGeneric(loc,"editItemNotes","800","600","yes","160","150")
    }
}

function getflowdowns(numdetrow)
{
    //alert("Get Associated Prs");
    var HubName  =  document.getElementById("HubName");
    var validShiptoid  =  document.getElementById("validshipto");
    var validpo =  document.getElementById("validpo");

    if (validpo.value == "No")
    {
        alert("Please Enter a Valid PO.");
    }
    else if (HubName.value == "None")
    {
        alert("Please Choose a Hub.");
        //return false;
    }
    else if (validShiptoid.value == "No")
    {
        alert("Please Choose a Valid Ship To.");
    }
    else
    {
        var HubFullName =  document.getElementById("HubFullName");
        loc = "/tcmIS/purchase/showflowdowns?HubName="+HubFullName.value ;
        var itemID  =  document.getElementById("lineitemid"+numdetrow+"");

        var ammendmentno =  document.getElementById("ammendment"+numdetrow+"");

        var flowdownstatus  =  document.getElementById("flowdownstatus"+numdetrow+"");
        var validflowdown  =  document.getElementById("validflowdown"+numdetrow+"");

        //alert(radianPO.value.length);
        if ( (itemID.value.length > 0 && itemID.value*1 >0) && (flowdownstatus.value == "No") )
        {
            var itemnotesimgae = document.getElementById("flowdownimg"+numdetrow+"");
            itemnotesimgae.setAttribute("src",'/images/minus.jpg');

				if (validflowdown.value == "Yes")
				{
				  var flowdowntable  =  document.getElementById("flowdowntable"+numdetrow+"");
              flowdowntable.className =  "";

              flowdownstatus.value = "Yes";
				}
				else
				{
            loc = loc + "&lineID=" + numdetrow;
            loc = loc + "&itemID=" + itemID.value;
            loc = loc + "&ammendMent=" + ammendmentno.value;

            loc = loc + "&Action=view";

            loc = loc  + "&poLine=" + numdetrow;

            var po  =  document.getElementById("po");
            loc = loc  + "&radianPO=" + po.value;

            var bpo  =  document.getElementById("bpo");
            loc = loc  + "&radianBPO=" + bpo.value;

            var shiptoid  =  document.getElementById("shiptoid");
            var prshipto  =  document.getElementById("prshipto"+numdetrow+"");
            var shiptocompanyid  =  document.getElementById("shiptocompanyid");

				/*if ( prshipto.value.trim().length > 0 )
				{
            	loc = loc  + "&shiptTo=" + prshipto.value;
				}
				else*/
				{
					loc = loc  + "&shiptTo=" + shiptoid.value;
				}
				loc = loc  + "&shiptocompanyid=" + shiptocompanyid.value;

            flowdownstatus.value = "Yes";
            openWinGeneric(loc,"flowdowntable","100","100","no","50","50")
            }
        }
        else if ( flowdownstatus.value == "Yes" )
        {
            var itemnotestable  =  document.getElementById("flowdowntable"+numdetrow+"");
            itemnotestable.className =  "displaynone";

            var itemnotesimgae = document.getElementById("flowdownimg"+numdetrow+"");
            itemnotesimgae.setAttribute("src",'/images/plus.jpg');

            var itemnotestatus  =  document.getElementById("flowdownstatus"+numdetrow+"");
            itemnotestatus.value = "No";
        }
    }

}

function getspecs(numdetrow)
{
    //alert("Get Associated Prs");
    var HubName  =  document.getElementById("HubName");
    var validShiptoid  =  document.getElementById("validshipto");
    var validpo =  document.getElementById("validpo");
	 var invengrp  =  document.getElementById("invengrp");

    if (validpo.value == "No")
    {
        alert("Please Enter a Valid PO.");
    }
    else if (HubName.value == "None")
    {
        alert("Please Choose a Hub");
        //return false;
    }
    else if (validShiptoid.value == "No")
    {
        alert("Please Choose a Valid Ship To");
    }
    else
    {
        var HubFullName =  document.getElementById("HubFullName");
        loc = "/tcmIS/purchase/showspecs?HubName="+HubFullName.value ;

        var itemID  =  document.getElementById("lineitemid"+numdetrow+"");

        var specstatus  =  document.getElementById("specstatus"+numdetrow+"");

        var ammendmentno =  document.getElementById("ammendment"+numdetrow+"");

		  var validspec  =  document.getElementById("validspec"+numdetrow+"");

        //alert(radianPO.value.length);
        if ( (itemID.value.length > 0 && itemID.value*1 >0) && (specstatus.value == "No") )
        {
            var itemnotesimgae = document.getElementById("specimg"+numdetrow+"");
            itemnotesimgae.setAttribute("src",'/images/minus.jpg');

				if (validspec.value == "Yes")
				{
				var spectable  =  document.getElementById("spectable"+numdetrow+"");
            spectable.className =  "";

            specstatus.value = "Yes";
				}
				else
				{
            loc = loc + "&lineID=" + numdetrow;
            loc = loc + "&itemID=" + itemID.value;
            loc = loc + "&ammendMent=" + ammendmentno.value;
            loc = loc + "&Action=view";

            loc = loc  + "&poLine=" + numdetrow;

            var po  =  document.getElementById("po");
            loc = loc  + "&radianPO=" + po.value;

            var bpo  =  document.getElementById("bpo");
            loc = loc  + "&radianBPO=" + bpo.value;

            var shiptoid  =  document.getElementById("shiptoid");
				var prshipto  =  document.getElementById("prshipto"+numdetrow+"");
				var shiptocompanyid  =  document.getElementById("shiptocompanyid");
				/*if ( prshipto.value.trim().length > 0 )
				{
            	loc = loc  + "&shiptTo=" + prshipto.value;
				}
				else*/
				{
					loc = loc  + "&shiptTo=" + shiptoid.value;
				}
				loc = loc  + "&shiptocompanyid=" + shiptocompanyid.value;
				loc = loc  + "&invengrp=" + invengrp.value;

            specstatus.value = "Yes";
            openWinGeneric(loc,"spectable","100","100","no","50","50")
            }
        }
        else if ( specstatus.value == "Yes" )
        {
            var itemnotestable  =  document.getElementById("spectable"+numdetrow+"");
            itemnotestable.className =  "displaynone";

            var itemnotesimgae = document.getElementById("specimg"+numdetrow+"");
            itemnotesimgae.setAttribute("src",'/images/plus.jpg');

            var itemnotestatus  =  document.getElementById("specstatus"+numdetrow+"");
            itemnotestatus.value = "No";
        }
    }
}

function getmsdsrevdate(numdetrow)
{
    //alert("Get Associated Prs");
    var HubName  =  document.getElementById("HubName");
    var validShiptoid  =  document.getElementById("validshipto");
    var validpo =  document.getElementById("validpo");
    var po  =  document.getElementById("po");
    var bpo  =  document.getElementById("bpo");
    var validbpo  =  document.getElementById("validbpo");

	 var validitem  =  document.getElementById("validitem"+numdetrow+"");
    if ( (po.value.length >0) && (validpo.value == "No") )
    {
        alert("Please Enter a Valid PO.");
    }
    else if (validShiptoid.value == "No")
    {
        alert("Please Choose a Valid Ship To");
    }
	 else if (validitem.value == "No")
    {
        alert("Please Choose a Valid Item Id");
    }
    else
    {
        var HubFullName =  document.getElementById("HubFullName");
        loc = "/tcmIS/purchase/showmsdsrevdate?HubName="+HubFullName.value ;

        var itemID  =  document.getElementById("lineitemid"+numdetrow+"");
        var msdsstatus  =  document.getElementById("msdsstatus"+numdetrow+"");

        //alert(radianPO.value.length);
        if ( (itemID.value.length > 0 && itemID.value*1 >0) && (msdsstatus.value == "No") )
        {
            var itemnotesimgae = document.getElementById("msdsimg"+numdetrow+"");
            itemnotesimgae.setAttribute("src",'/images/minus.jpg');

				var ammendmentno =  document.getElementById("ammendment"+numdetrow+"");
				loc = loc + "&ammendMent=" + ammendmentno.value;
            loc = loc + "&lineID=" + numdetrow;
            loc = loc + "&itemID=" + itemID.value;
            //loc = loc + "&Action=view";

		     if ( po.value.length > 0)
           {
               loc = loc + "&Action=view&subUserAction=po";
               loc = loc + "&radianPO="+ po.value;
           }
           else if (bpo.value.length > 0)
           {
               loc = loc + "&Action=view&subUserAction=bpo";
               loc = loc + "&radianBPO=" + bpo.value;
           }

            msdsstatus.value = "Yes";
            openWinGeneric(loc,"msdstable","100","100","no","50","50")

        }
        else if ( msdsstatus.value == "Yes" )
        {
            var itemnotestable  =  document.getElementById("msdstable"+numdetrow+"");
            itemnotestable.className =  "displaynone";

            var itemnotesimgae = document.getElementById("msdsimg"+numdetrow+"");
            itemnotesimgae.setAttribute("src",'/images/plus.jpg');

            var itemnotestatus  =  document.getElementById("msdsstatus"+numdetrow+"");
            itemnotestatus.value = "No";
        }
    }
}


function gettcmBuys(numdetrow)
{
    //alert("Get tcm Buys");
    loc = "/tcmIS/purchase/showtcmbuys?itemID=";

    var itemID  =  document.getElementById("lineitemid"+numdetrow+"");
    var opsEntityId  =  document.getElementById("opsEntityId");
    var itemnotestatus  =  document.getElementById("tcmbuystatus"+numdetrow+"");
    //alert(itemnotestatus.value);

    if ( (itemID.value.length > 0 && itemID.value*1 >0) && (itemnotestatus.value == "No") )
    {
        var itemnotesimgae = document.getElementById("tcmbuyimg"+numdetrow+"");
        itemnotesimgae.setAttribute("src",'/images/minus.jpg');

        loc = loc + itemID.value;
        loc = loc + "&Action=view";
        loc = loc + "&lineID=" + numdetrow;
        loc = loc + "&opsEntityId=" + opsEntityId.value;

          var shiptoid  =  document.getElementById("shiptoid");
  		  loc = loc  + "&shiptoLoc=" + shiptoid.value;

        itemnotestatus.value = "Yes";
        openWinGeneric(loc,"tcmbuyhistory","100","100","no","50","50")
    }
    else if ( itemnotestatus.value == "Yes" )
    {
        var itemnotestable  =  document.getElementById("tcmbuytable"+numdetrow+"");
        itemnotestable.className =  "displaynone";

        var itemnotesimgae = document.getElementById("tcmbuyimg"+numdetrow+"");
        itemnotesimgae.setAttribute("src",'/images/plus.jpg');

        var itemnotestatus  =  document.getElementById("tcmbuystatus"+numdetrow+"");
        itemnotestatus.value = "No";
    }
}

function getclientBuys(numdetrow)
{
    //alert("Get Client Buys");
    loc = "/tcmIS/purchase/showclientbuys?itemID=";

    var itemID  =  document.getElementById("lineitemid"+numdetrow+"");

    var itemnotestatus  =  document.getElementById("clientbuystatus"+numdetrow+"");
    //alert(itemnotestatus.value);

    if ( (itemID.value.length > 0 && itemID.value*1 >0) && (itemnotestatus.value == "No") )
    {
        var itemnotesimgae = document.getElementById("clientbuys"+numdetrow+"");
        itemnotesimgae.setAttribute("src",'/images/minus.jpg');

        loc = loc + itemID.value;
        loc = loc + "&Action=view";
        loc = loc + "&lineID=" + numdetrow;
        itemnotestatus.value = "Yes";
        openWinGeneric(loc,"clientbuyhistory","100","100","no","50","50")
    }
    else if ( itemnotestatus.value == "Yes" )
    {
        var itemnotestable  =  document.getElementById("clientbuytable"+numdetrow+"");
        itemnotestable.className =  "displaynone";

        var itemnotesimgae = document.getElementById("clientbuys"+numdetrow+"");
        itemnotesimgae.setAttribute("src",'/images/plus.jpg');

        var itemnotestatus  =  document.getElementById("clientbuystatus"+numdetrow+"");
        itemnotestatus.value = "No";
    }
}

function getlookaheads(numdetrow)
{
    //alert("Get Associated Prs");
    var HubName  =  document.getElementById("HubName");
    var validShiptoid  =  document.getElementById("validshipto");
    var validpo =  document.getElementById("validpo");
	 var po  =  document.getElementById("po");


    if (po.value.trim().length > 0 && validpo.value == "Yes")
    {
        var HubFullName =  document.getElementById("HubFullName");
        loc = "/tcmIS/purchase/showflowdowns?HubName="+HubFullName.value ;
        var itemID  =  document.getElementById("lineitemid"+numdetrow+"");

        var ammendmentno =  document.getElementById("ammendment"+numdetrow+"");

        var lookaheadstatus  =  document.getElementById("lookaheadstatus"+numdetrow+"");
		  var validlookahead  =  document.getElementById("validlookahead"+numdetrow+"");

        //alert(radianPO.value.length);
        if ( (itemID.value.length > 0 && itemID.value*1 >0) && (lookaheadstatus.value == "No") )
        {
            var lookaheadimg = document.getElementById("lookaheadimg"+numdetrow+"");
            lookaheadimg.setAttribute("src",'/images/minus.jpg');

				if (validlookahead.value == "Yes")
				{
				  var lookaheadtable  =  document.getElementById("lookaheadtable"+numdetrow+"");
              lookaheadtable.className =  "";

              lookaheadstatus.value = "Yes";
				}
				else
				{
            loc = loc + "&lineID=" + numdetrow;
            loc = loc + "&itemID=" + itemID.value;
            loc = loc + "&ammendMent=" + ammendmentno.value;
            loc = loc + "&Action=lookaheadview";
            loc = loc  + "&poLine=" + numdetrow;

            var po  =  document.getElementById("po");
            loc = loc  + "&radianPO=" + po.value;

            var bpo  =  document.getElementById("bpo");
            loc = loc  + "&radianBPO=" + bpo.value;

            var shiptoid  =  document.getElementById("shiptoid");
            var prshipto  =  document.getElementById("prshipto"+numdetrow+"");
            var shiptocompanyid  =  document.getElementById("shiptocompanyid");

				/*if ( prshipto.value.trim().length > 0 )
				{
            	loc = loc  + "&shiptTo=" + prshipto.value;
				}
				else*/
				{
					loc = loc  + "&shiptTo=" + shiptoid.value;
				}
				loc = loc  + "&shiptocompanyid=" + shiptocompanyid.value;

            lookaheadstatus.value = "Yes";
            openWinGeneric(loc,"flowdowntable","200","200","yes","200","200")
            }
        }
        else if ( lookaheadstatus.value == "Yes" )
        {
            var lookaheadtable  =  document.getElementById("lookaheadtable"+numdetrow+"");
            lookaheadtable.className =  "displaynone";

            var lookaheadimg = document.getElementById("lookaheadimg"+numdetrow+"");
            lookaheadimg.setAttribute("src",'/images/plus.jpg');

            var lookaheadstatus  =  document.getElementById("lookaheadstatus"+numdetrow+"");
            lookaheadstatus.value = "No";
        }
    }
}

function getCorrectPaymentTerms(rowNumber,inventoryGroup,returnSelectedId)
{
 var loc = "/tcmIS/purchase/posupplier?session=Active&Action=sendPaymentTerms&SearchString=";
 loc = loc + returnSelectedId;
 loc = loc + "&userAction=";
  loc = loc + "&rowNumber=";
 loc = loc + rowNumber;
 loc = loc + "&inventoryGroup=";
 loc = loc + inventoryGroup;
 var supplierrep = returnSelectedId;
 supplierrep = supplierrep.replace(/&/gi, "%26");
 loc = loc + "&suppID=" + supplierrep;

 openWinGeneric(loc,"get_Correct_PaymentTerms","100","100","no","50","50")
}