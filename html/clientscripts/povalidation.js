
function invalidateSecondarySupplier(selectedRow)
{
   var secondarysupplier  =  document.getElementById("secondarysupplier"+selectedRow+"");
   secondarysupplier.className="INVALIDTEXT";

   setlineUnconfirmed(selectedRow);
}

function invalidateSupplier()
{
    var validsupplier  =  document.getElementById("validsupplier");
    if (validsupplier.value == "Yes")
    {
        validsupplier.value = "No";

        var supplierid  =  document.getElementById("supplierid");
        supplierid.className="INVALIDTEXT";

        var validordertaker  =  document.getElementById("validordertaker");
        validordertaker.value = "No";

        ordertaker = document.getElementById("ordertaker");
        ordertaker.className="INVALIDTEXT";

        var supplierline1  =  document.getElementById("supplierline1");
        supplierline1.innerHTML = "";

        var supplierline2  =  document.getElementById("supplierline2");
        supplierline2.innerHTML = "";

        var supplierline3  =  document.getElementById("supplierline3");
        supplierline3.innerHTML = "";

        var supplierline4  =  document.getElementById("supplierline4");
        supplierline4.innerHTML = "";

        var mfgphoneno  =  document.getElementById("mfgphoneno");
        mfgphoneno.value = "";
    }
}

function invalidateShipTo()
{
    var validshipto  =  document.getElementById("validshipto");
    if (validshipto.value == "Yes")
    {
        validshipto.value = "No";

        var shiptoid  =  document.getElementById("shiptoid");
        shiptoid.className="INVALIDTEXT";

        var validcarrier  =  document.getElementById("validcarrier");
        validcarrier.value = "No";

        var carrierID  =  document.getElementById("carrierID");
        carrierID.className="INVALIDTEXT";

        var shiptocompanyid  =  document.getElementById("shiptocompanyid");
        shiptocompanyid.value = "";

        /*var HubName  =  document.getElementById("HubName");
        HubName.value = "None";*/

        var carrierline1  =  document.getElementById("carrierline1");
        carrierline1.innerHTML = "";

        var carrierline2  =  document.getElementById("carrierline2");
        carrierline2.innerHTML = "";

        var shiptoline1  =  document.getElementById("shiptoline1");
        shiptoline1.innerHTML = "";

        var shiptoline2  =  document.getElementById("shiptoline2");
        shiptoline2.innerHTML = "";

        var shiptoline3  =  document.getElementById("shiptoline3");
        shiptoline3.innerHTML = "";

        var shiptoline4  =  document.getElementById("shiptoline4");
        shiptoline4.innerHTML = "";

        var shiptoline5  =  document.getElementById("shiptoline5");
        shiptoline5.innerHTML = "";

	    var attachedpr  =  document.getElementById("attachedpr");
		 if (attachedpr.value == "N")
		 {
		 var invengrp  =  document.getElementById("invengrp");
	    invengrp.value = "";
	    }

            /*mytable = document.getElementById("linetable");
            var allTRs = mytable.getElementsByTagName("tr");
            var nowofRows = (allTRs.length)*1;

            for(j=0;j<nowofRows;j++)
            {
             var itemtype = document.getElementById("itemtype"+(j+1)+"");
                  if (itemtype.value == "MA")
             {
                     //var validspec  =  document.getElementById("validspec"+(j+1)+"");
                //validspec.value = "No";

                 var itemnotestatus  =  document.getElementById("specstatus"+(j+1)+"");
                 if ( itemnotestatus.value == "Yes" )
                 {
                      var itemnotestable  =  document.getElementById("spectable"+(j+1)+"");
                      itemnotestable.className =  "displaynone";

                      var itemnotesimgae = document.getElementById("specimg"+(j+1)+"");
                      itemnotesimgae.setAttribute("src",'/images/plus.jpg');

                      var itemnotestatus  =  document.getElementById("specstatus"+(j+1)+"");
                      itemnotestatus.value = "No";
                  }

                //var validflowdown  =  document.getElementById("validflowdown"+(j+1)+"");
                //validflowdown.value = "No";

                var flowdownstatus  =  document.getElementById("flowdownstatus"+(j+1)+"");
                if ( flowdownstatus.value == "Yes" )
                {
                   var flownotestable  =  document.getElementById("flowdowntable"+(j+1)+"");
                   flownotestable.className =  "displaynone";

                   var flownotesimgae = document.getElementById("flowdownimg"+(j+1)+"");
                   flownotesimgae.setAttribute("src",'/images/plus.jpg');

                   var flownotestatus  =  document.getElementById("flowdownstatus"+(j+1)+"");
                   flownotestatus.value = "No";
                 }
              }
           }*/
    }
}

function invalidatespecsandflow(thisrow)
{
    var itemtype = document.getElementById("itemtype"+thisrow+"");
    //var validspec  =  document.getElementById("validspec"+thisrow+"");
    //validspec.value = "No";

    var itemnotestatus  =  document.getElementById("specstatus"+thisrow+"");
    if ( itemnotestatus.value == "Yes" )
    {
        var itemnotestable  =  document.getElementById("spectable"+thisrow+"");
        itemnotestable.className =  "displaynone";

        var itemnotesimgae = document.getElementById("specimg"+thisrow+"");
        itemnotesimgae.setAttribute("src",
                                    '/images/plus.jpg');

        var itemnotestatus  =  document.getElementById("specstatus"+thisrow+"");
        itemnotestatus.value = "No";
    }

    //var validflowdown  =  document.getElementById("validflowdown"+(j+1)+"");
    //validflowdown.value = "No";

    var flowdownstatus  =  document.getElementById("flowdownstatus"+thisrow+"");
    if ( flowdownstatus.value == "Yes" )
    {
        var flownotestable  =  document.getElementById("flowdowntable"+thisrow+"");
        flownotestable.className =  "displaynone";

        var flownotesimgae = document.getElementById("flowdownimg"+thisrow+"");
        flownotesimgae.setAttribute("src",
                                    '/images/plus.jpg');

        var flownotestatus  =  document.getElementById("flowdownstatus"+thisrow+"");
        flownotestatus.value = "No";
    }

    var itemnotestatus  =  document.getElementById("itemnotestatus"+thisrow+"");
    if ( itemnotestatus.value == "Yes" )
    {
        var linedetailtable  =  document.getElementById("linedetailtable"+thisrow+"");
        linedetailtable.className =  "displaynone";

        var itemnotesimage = document.getElementById("itemnotesimage"+thisrow+"");
        itemnotesimage.setAttribute("src",
                                    '/images/plus.jpg');

        var itemnotestatus1  =  document.getElementById("itemnotestatus"+thisrow+"");
        itemnotestatus1.value = "No";
    }

    var tcmbuystatus  =  document.getElementById("tcmbuystatus"+thisrow+"");
    if ( tcmbuystatus.value == "Yes" )
    {
        var tcmbuytable  =  document.getElementById("tcmbuytable"+thisrow+"");
        tcmbuytable.className =  "displaynone";

        var tcmbuyimg = document.getElementById("tcmbuyimg"+thisrow+"");
        tcmbuyimg.setAttribute("src",
                               '/images/plus.jpg');

        var tcmbuystatus1  =  document.getElementById("tcmbuystatus"+thisrow+"");
        tcmbuystatus1.value = "No";
    }

	 var lookaheadstatus  =  document.getElementById("lookaheadstatus"+thisrow+"");
	 if ( lookaheadstatus.value == "Yes" )
    {
       var lookaheadtable  =  document.getElementById("lookaheadtable"+thisrow+"");
       lookaheadtable.className =  "displaynone";

       var lookaheadimg = document.getElementById("lookaheadimg"+thisrow+"");
       lookaheadimg.setAttribute("src",'/images/plus.jpg');

       var lookaheadstatus1  =  document.getElementById("lookaheadstatus"+thisrow+"");
       lookaheadstatus1.value = "No";
     }

}

function invalidateCarrier()
{
    var validcarrier  =  document.getElementById("validcarrier");
    if (validcarrier.value == "Yes")
    {
        validcarrier.value = "No";

        var carrierID  =  document.getElementById("carrierID");
        carrierID.className="INVALIDTEXT";

        var carrierline1  =  document.getElementById("carrierline1");
        carrierline1.innerHTML = "";

        var carrierline2  =  document.getElementById("carrierline2");
        carrierline2.innerHTML = "";

    }
}

function invalidatePo()
{
    //alert(" PO Hi!");
    var validpo  =  document.getElementById("validpo");
    //if (validpo.value == "Yes")
    {
        validpo.value = "No";

        var povalue  =  document.getElementById("po");
        povalue.className="INVALIDTEXT";
        if (povalue.value.length == 0 )
        {
            selectedRow = document.getElementById("searchbpolike");
            selectedRow.disabled=false;
            selectedRow.style.display = "";

            selectedRow = document.getElementById("Newbpo");
            selectedRow.disabled=false;
            selectedRow.style.display = "";

            selectedRow = document.getElementById("bpo");
            selectedRow.readOnly=false;
            var validbpo  =  document.getElementById("validbpo");
            if (validbpo.value == "Yes")
            {
                selectedRow.className="HEADER";
            }
            else if (validbpo.value == "No")
            {
                selectedRow.className="INVALIDTEXT";
            }
            else if (selectedRow.value.length == 0)
            {
                selectedRow.className="INVALIDTEXT";
            }

            selectedRow = document.getElementById("searchsupplierlike");
            selectedRow.disabled=false;
            selectedRow.style.display = "";

            selectedRow = document.getElementById("supplierid");
            selectedRow.readOnly = false;
            var validsupplier  =  document.getElementById("validsupplier");
            if (validsupplier.value == "Yes")
            {
                selectedRow.className="HEADER";
            }
        }
    }

}

function invalidatebPo()
{
    var validbpo  =  document.getElementById("validbpo");
    //if (validbpo.value == "Yes")
    {
        validbpo.value = "No";

        var bpovalue  =  document.getElementById("bpo");
        bpovalue.className="INVALIDTEXT";
        if (bpovalue.value.length == 0 )
        {
            selectedRow = document.getElementById("searchpolike");
            selectedRow.disabled=false;
            selectedRow.style.display = "";

            selectedRow = document.getElementById("Newpo");
            selectedRow.disabled=false;
            selectedRow.style.display = "";

            selectedRow = document.getElementById("po");
            selectedRow.readOnly=false;
            var validpo  =  document.getElementById("validpo");
            if (validpo.value == "Yes")
            {
                selectedRow.className="HEADER";
            }
            else if (validpo.value == "No")
            {
                selectedRow.className="INVALIDTEXT";
            }
            else if (selectedRow.value.length == 0)
            {
                selectedRow.className="INVALIDTEXT";
            }

            selectedRow = document.getElementById("searchsupplierlike");
            selectedRow.disabled=false;
            selectedRow.style.display = "";

            selectedRow = document.getElementById("supplierid");
            selectedRow.readOnly = false;
            var validsupplier  =  document.getElementById("validsupplier");
            if (validsupplier.value == "Yes")
            {
                selectedRow.className="HEADER";
            }

            selectedRow = document.getElementById("bostartdate");
            selectedRow.className="INVISIBLEHEADWHITE";
            selectedRow.readOnly = true;

            selectedRow = document.getElementById("boenddate");
            selectedRow.className="INVISIBLEHEADWHITE";
            selectedRow.readOnly = true;
        }
    }
}

function invalidateOrderTaker()
{
    var validordertaker  =  document.getElementById("validordertaker");
    if (validordertaker.value == "Yes")
    {
        validordertaker.value = "No";

        ordertakerID = document.getElementById("ordertakerID");
        ordertakerID.value = "";

        ordertaker = document.getElementById("ordertaker");
        ordertaker.className="INVALIDTEXT";
    }
}

function invalidateItem(selectedRow)
{
    var validitem  =  document.getElementById("validitem"+selectedRow+"");
    if (validitem.value == "Yes")
    {
        validitem.value = "No";

        var lineitemid  =  document.getElementById("lineitemid"+selectedRow+"");
        lineitemid.className="INVALIDTEXT";

        setlineUnconfirmed(selectedRow);

        /*selectedRowStatus = document.getElementById("linestatus"+selectedRow+"");
        if (selectedRowStatus.value == "Confirmed")
        {
        selectedRowStatus.value = "Unconfirmed";

        selectedRowStatus = document.getElementById("cell12"+selectedRow+"");
        selectedRowStatus.innerHTML = "Unconfirmed";
        }*/
    }
}


function checkForCreditCartConf() {
    var paymentterms  =  document.getElementById("paymentterms");
    var opsEntityId  =  document.getElementById("opsEntityId");
    var allowPOCreditConfirmaiton  =  document.getElementById("allowPOCreditConfirmaiton");
    var confirmButton = document.getElementById("confirm");

    if (opsEntityId.value == "HAASTCMUSA" && paymentterms.value == "Credit Card" && allowPOCreditConfirmaiton.value == "false") {
    	confirmButton.style.display = "none";
    }
    else  {
        confirmButton.style.display = "";

    }
}