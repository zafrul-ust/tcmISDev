
String.prototype.trim = function()
{
// skip leading and trailing whitespace
// and return everything in between
  return this.replace(/^\s*(\b.*\b|)\s*$/, "$1");
}

function getAssociatedPrs(numdetrow)
{
    //alert("Get Associated Prs");
    var HubName  =  document.getElementById("HubName");
    if (HubName.value == "None")
    {
        alert("Please Choose a Hub");
        //return false;
    }
    else
    {
        loc = "/tcmIS/supply/Buyorder?HubName="+HubName.value;
        var itemID  =  document.getElementById("lineitemid"+numdetrow+"");
        var radianPO  =  document.getElementById("po");
        var itemnotestatus  =  document.getElementById("associatedprsstatus"+numdetrow+"");
		  var invengrp = document.getElementById( "invengrp" );
        var attachedpr = document.getElementById( "attachedpr" );
        //alert(radianPO.value.length);
        if ( (itemID.value.length > 0)
                && (radianPO.value.length > 0)
                && (itemnotestatus.value == "No") )
        {
            var itemnotesimgae = document.getElementById("associatedprimg"+numdetrow+"");
            itemnotesimgae.setAttribute("src",'/images/minus.jpg');

            loc = loc + "&lineID=" + numdetrow;
            loc = loc + "&itemnum=" + itemID.value;
            loc = loc + "&radianPO=" + radianPO.value;
            loc = loc + "&UserAction=associatedPRs";
	      	var buyerid  =  document.getElementById("buyerid");
            loc = loc + "&buyer=" + buyerid.value;
				loc = loc + "&invengrp=" + invengrp.value;
				loc = loc + "&attachedpr=" + attachedpr.value;

            itemnotestatus.value = "Yes";
            openWinGeneric(loc,"associatedPRs","100","100","no","50","50")
        }
        else if ( itemnotestatus.value == "Yes" )
        {
            var itemnotestable  =  document.getElementById("associatedprtable"+numdetrow+"");
            itemnotestable.className =  "displaynone";

            var itemnotesimgae = document.getElementById("associatedprimg"+numdetrow+"");
            itemnotesimgae.setAttribute("src",'/images/plus.jpg');

            var itemnotestatus  =  document.getElementById("associatedprsstatus"+numdetrow+"");
            itemnotestatus.value = "No";
        }
    }
}


function addBuyOrdersToPO()
{
 document.getElementById("addLineButton").style["display"] = "none";
 document.getElementById("addChargeButton").style["display"] = "none";
 document.getElementById("removeLine").style["display"] = "none";
 mytable = document.getElementById("linetable");
 var allTRs = mytable.getElementsByTagName("tr");
 var nowofRows = (allTRs.length)*1;
 for(j=0;j<nowofRows;j++)
 {
     try
     {
         document.getElementById("editassociatePr00"+(j+1)+"").style["display"] = "none";
     }
     catch (expo)
     {

     }
 }
    
 var validshipto  =  document.getElementById("validshipto");
 var HubName  =  document.getElementById("HubName");
 var shiptoid  =  document.getElementById("shiptoid").value;
 shiptoid = shiptoid.replace(/&/gi, "%26");
 shiptoid = shiptoid.replace(/#/gi, "%23");
 var numofHubs  =  document.getElementById("numofHubs");
 var buyerid  =  document.getElementById("buyerid");
 var invengrp = document.getElementById( "invengrp" );
 var attachedpr = document.getElementById( "attachedpr" );
 var inventoryGroup  =  document.getElementById("invengrp");
 var opsEntityId  =  document.getElementById("opsEntityId");
 var validpo  =  document.getElementById("validpo");
 var validbpo  =  document.getElementById("validbpo");
 var supplierId  =  document.getElementById("supplierid");
 var validsupplier  =  document.getElementById("validsupplier");
 var radianPO  =  document.getElementById("po");
 var currency  =  document.getElementById("currency");
 if ( validpo.value == "No" &&  validbpo.value !="Yes" )
 {
     alert("Please choose a valid PO.");
 }
 else if (inventoryGroup.value.trim().length == 0 || opsEntityId.value.trim().length == 0)
 {
     alert("Please choose a valid Ship To.");
 }
 else if (validsupplier.value == "No" || supplierId.value.trim().length == 0)
 {
     alert("Please Select a Valid Supplier.");
 }
 else if (HubName.value == "None")
 {
     alert("Please Choose a Hub");
 }
 else if ( (shiptoid.length > 0) && (validshipto.value == "No") )
 {
      alert("Please Choose a Valid Ship To.\nOr\nLeave the Input Field Blank.");
 }
 else if ((currency.value == "None" || currency.value.trim().length == 0))
 {
    alert("Please Pick a Currency.");
 }
 else
 {
    loc = "/tcmIS/supply/Buyorder?HubName="+HubName.value;

    if (radianPO.value.length > 0)
    {
        loc = loc + "&radianPO=" + radianPO.value;
        if ( (shiptoid.length > 0) && (validshipto.value == "Yes") )
        {
           loc = loc  + "&shipTo=" + shiptoid;
        }
        else
        {
           loc = loc  + "&shipTo=";
        }
        var shiptocompanyid  =  document.getElementById("shiptocompanyid");
        loc = loc  + "&mainshipTo=" +shiptoid;
        loc = loc  + "&mainshipTocompanyId=" + shiptocompanyid.value;
        loc = loc + "&prHubNum=" + HubName.value;
        loc = loc + "&numofHubs=" + numofHubs.value;
        loc = loc + "&buyer=" + buyerid.value;
        //loc = loc + "&companyID=" + shiptocompanyid.value;
        loc = loc + "&UserAction=addBuyOrdersToPO";
        loc = loc + "&invengrp=" + invengrp.value;
        loc = loc + "&attachedpr=" + attachedpr.value;
        loc = loc + "&currencyId=" + currency.value;

        openWinGeneric(loc,"addBuyOrdersToPO","900","600","yes","40","40")
    }
 }
}

function edditAssociatedPrs(numdetrow)
{
    //alert("Edit Associated Prs");
    document.getElementById("addBuyOrders").style["display"] = "none";
	 var validshipto  =  document.getElementById("validshipto");
	 var HubName  =  document.getElementById("HubName");
	 var shiptoid  =  document.getElementById("shiptoid").value;
    shiptoid = shiptoid.replace(/&/gi, "%26");
    shiptoid = shiptoid.replace(/#/gi, "%23");

    var povalue  =  document.getElementById("po");
	 var validpo  =  document.getElementById("validpo");
	 var numofHubs  =  document.getElementById("numofHubs");
	 var buyerid  =  document.getElementById("buyerid");
	 var shiptocompanyid = document.getElementById("shiptocompanyid");
	 var invengrp = document.getElementById( "invengrp" );
   var attachedpr = document.getElementById( "attachedpr" );
   var itemID  =  document.getElementById("lineitemid"+numdetrow+"");
   var validitem  =  document.getElementById("validitem"+numdetrow+"");
    if (validpo.value == "No")
    {
			alert("Please Choose a Valid PO.");
    }
    else if ( (shiptoid.length > 0) && (validshipto.value == "No") )
    {
	     alert("Please Choose a Valid Ship To.\nOr\nLeave the Input Field Blank.");
	  }
    else if (invengrp.value.trim().length == 0)
    {
	     alert("Please Choose a Valid Inventory Group.");
	  }
    /*else if (HubName.value == "None")
    {
        alert("Please Choose a Hub");
    }*/
    else if (validitem.value == "No" || itemID.value.trim().length==0)
    {
        alert("Please Choose a Valid Item Id");
    }
    else
    {
        loc = "/tcmIS/supply/Buyorder?HubName="+HubName.value;
        var ammendment  =  document.getElementById("ammendment"+numdetrow+"");
        var radianPO  =  document.getElementById("po");

        if (radianPO.value.length > 0)
        {
            if (validitem.value == "Yes")
            {
            loc = loc + "&itemnum=" + itemID.value;
            }
				loc = loc + "&lineID=" + numdetrow;
            loc = loc + "&radianPO=" + radianPO.value;

 				var prshipto  =  document.getElementById("prshipto"+numdetrow+"").value;
            prshipto = prshipto.replace(/&/gi, "%26");
            prshipto = prshipto.replace(/#/gi, "%23");

				if ( prshipto.trim().length > 0 )
				{
            	loc = loc  + "&shipTo=" + prshipto;
				}
				else if ( (shiptoid.length > 0) && (validshipto.value == "Yes") )
				{
					loc = loc  + "&shipTo=" + shiptoid;
				}
				else
				{
					loc = loc  + "&shipTo=";
				}

				var shiptocompanyid  =  document.getElementById("shiptocompanyid");
				loc = loc  + "&mainshipTo=" +shiptoid;
				loc = loc  + "&mainshipTocompanyId=" + shiptocompanyid.value;

            //loc = loc + "&shipTo=" + shiptoid.value;
            loc = loc + "&prHubNum=" + HubName.value;
            loc = loc + "&numofHubs=" + numofHubs.value;
            loc = loc + "&buyer=" + buyerid.value;
            //loc = loc + "&companyID=" + shiptocompanyid.value;
            loc = loc + "&ammendment=" + ammendment.value;
            loc = loc + "&UserAction=editassociatedPRs";
				loc = loc + "&invengrp=" + invengrp.value;
				loc = loc + "&attachedpr=" + attachedpr.value;

            openWinGeneric(loc,"editassociatedPRs","900","600","yes","40","40")
        }

    }
}


function checkEditPrsValues(name, entered,userAction)
{
 if (userAction =="editassociatedPRs")
 {
var noLinesChecked = 0;
var lineNum = name.toString();
//alert("AssociatPRs  "+lineNum+"");
var currentcheckBox = document.getElementById(""+lineNum+"");

var totalRecords = document.getElementById("totalRecords");
var itemID  =  document.getElementById("itemnum");
var lineitemID  =  document.getElementById("itemid"+lineNum+"");

var numofHubs  =  document.getElementById("numofHubs");
var HubName  =  document.getElementById("HubName");
var lineHubName  =  document.getElementById("branchplant"+lineNum+"");
var shipTo  =  document.getElementById("shipTo");

var lineshipTo  =  document.getElementById("facility"+lineNum+"");
var itemfromLine  =  document.getElementById("itemfromLine");
var shipTofromLine  =  document.getElementById("shipTofromLine");
var shipToCompanyfromLine  =  document.getElementById("shipToCompanyfromLine");
var invengrp  =  document.getElementById("invengrp"+lineNum+"");
var selectedCustomerPo  =  document.getElementById("customerPo"+lineNum+"");

if (currentcheckBox.checked)
{
noLinesChecked ++;
}
	if ( itemID.value.trim().length > 0 && (lineitemID.value != itemID.value) )
	{
	  alert("You Cannot Choose a Different Item");
	  currentcheckBox.checked = false;
	  return false;
	}

	/*if ( shipTo.value.trim().length > 0 && (lineshipTo.value != shipTo.value) )
	{
	  alert("You Cannot Choose a Different Ship TO");
	  currentcheckBox.checked = false;
	  return false;
	}
	//alert(HubName.value);
	if ( HubName.value.trim().length > 0 && (HubName.value != "None") && (lineHubName.value != HubName.value) )
	{
	  alert("You Cannot Choose a Different Hub");
	  currentcheckBox.checked = false;
	  return false;
	}*/

	   var allClear = 0;
	   var finalMsgt;
	   finalMsgt = "You Cannot Select PRs with Different: \n\n";

	   for(j=0;j<(totalRecords.value*1);j++)
	   {

	      var lineitemID1  =  "";
	      var lineHubName1 =  "";
	      var lineshipTo1  =  "";
	      var lineinvengrp  =  "";
	      var currentcheckBox1;
	      var currentCustomerPo  =  "";

	      //if ( (j+1) < totalRecords.value*1 )
	      {
	       lineitemID1  =  document.getElementById("itemid"+(j+1)+"");
	       lineHubName1 =  document.getElementById("branchplant"+(j+1)+"");
	       lineshipTo1  =  document.getElementById("facility"+(j+1)+"");
	       currentcheckBox1 = document.getElementById(""+(j+1)+"");
	       companyid = document.getElementById("companyid"+(j+1)+"");
	       lineinvengrp  =  document.getElementById("invengrp"+(j+1)+"");
	       currentCustomerPo =  document.getElementById("customerPo"+(j+1)+"");
	      }

	      if ( (j+1)!=(lineNum*1) && currentcheckBox1.checked)
	      {
				noLinesChecked ++;
	         if (lineitemID.value != lineitemID1.value)
	         {
	            if (allClear == 0)
	            {
	            finalMsgt = finalMsgt + "  Item\n";
	            }
	            allClear += 1;
	         }

				if (lineinvengrp.value != invengrp.value)
	         {
	            if (allClear == 0)
	            {
	            finalMsgt = finalMsgt + "  Inventory Group\n";
	            }
	            allClear += 1;
	         }

	      var companyid2  =  document.getElementById("companyid"+lineNum*1+"");
		   if (lineHubName.value == "2202" || lineHubName1.value == "2202")
		   {
				//alert(lineHubName.value);
				if (currentCustomerPo.value == selectedCustomerPo.value)
				{

				}
				else if ( (companyid2.value.trim() == "SWA") || (companyid.value.trim() == "SWA") )
	         {
	         finalMsgt = finalMsgt + "  Customer PO for Drop Ship\n";
	         allClear += 1;
	         }
		   }
	    }
	  }

	    if (allClear < 1)
	    {
	       if (numofHubs.value != 1)
	       {
	           HubName.value  =  lineHubName.value;
	       }
	       itemfromLine.value = lineitemID.value;
	       shipTofromLine.value = lineshipTo.value;
	       shipToCompanyfromLine.value = companyid.value;
	    }
	    else
	    {
	        alert(finalMsgt);
	        currentcheckBox.checked = false;
	    }

		 var everconfirmed  =  document.getElementById("everconfirmed");
		 if ( noLinesChecked == 0)
		 {
			if (everconfirmed.value.trim() == "Y")
         {
           var lineID  =  document.getElementById("lineID");
           var lineqty = opener.document.getElementById("lineqty" + lineID.value.trim() + "");
           //alert(" lineID  "+lineID.value+"     lineqty   "+lineqty.value+"");
           if ( (lineqty.value*1) > 0 )
           {
             alert("You Cannot disassociate all PRs from the PO with a non-zero qty on the line.");
             currentcheckBox.checked = true;
             return false;
           }
         }

         if (numofHubs.value != 1)
         {
           HubName.value  =  "None";
           itemfromLine.value = "";
           shipTofromLine.value = "";
        }
	   }
}
}

function showLPPranges(companyid,mrnumber,lineitem1,itemid,invengrp)
{
       loc = "/tcmIS/purchase/showtcmbuys?itemID=";
       loc = loc + itemid;
       loc = loc + "&mrnumber=" + mrnumber;
       loc = loc + "&lineitem=" + lineitem1;
       loc = loc + "&invengrp=" + invengrp;
       loc = loc + "&companyID=" + companyid;

       var currencyid = document.getElementById("currency");
       loc = loc + "&currency=" + currencyid.value;

       loc = loc + "&Action=showlpprange";

       openWinGeneric(loc,"showlpprange","650","420","yes","40","40");
}

//this function will encode the string for URL
function URLEncode(s) {
	var str = new String(s);
	str = str.replace(/&/gi, "%26");
   str = str.replace(/#/gi, "%23");
   str = str.replace(/%/gi, "%25");
   str = str.replace(/\+/gi, "%2b");
    return str;
}

function showMrLineAllocationReport(companyId,mrNumber,lineItem,itemid,invengrp)
{
	if ((mrNumber!= null && lineItem != null && mrNumber!= 0) && (companyId.length>0) )
	{        
        companyId = URLEncode(companyId);
        var loc = "/tcmIS/distribution/mrallocationreportmain.do?fromCustomerOrdertracking=Y&companyId="+companyId+"&mrNumber="+mrNumber+"&lineItem="+lineItem+"";
		openWinGeneric(loc,"showMrAllocationReport","800","550","yes","80","80","no");
	}
}
