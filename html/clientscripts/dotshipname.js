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


String.prototype.trim = function()
{
// skip leading and trailing whitespace
// and return everything in between
  return this.replace(/^\s*(\b.*\b|)\s*$/, "$1");
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

function getpropershipingname(CompNum)
{
        loc = "/tcmIS/hub/propershpname?Action=findlike";
        loc = loc + "&CompNum=" + CompNum;
        openWinGeneric(loc,"ProperShipping_Name","720","410","yes")
}

function namesearch(entered)
{
    window.document.pshipnames.Action.value = "Search";
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
    return true;
}

function sendpropshpnamevalue()
{
   var propershipingname = document.getElementById( "propershipingname" );
   var compnumbn = document.getElementById("CompNum");

	if (propershipingname.value.trim().length > 0)
	{

   var opnshpname  = opener.document.getElementById("ground_shipping_name"+compnumbn.value+"");
   opnshpname.value = propershipingname.value;

   window.close();
   }
   else
   {
		alert("Please select a Proper Shipping Name");
   }
}

function addshipingname( shpname,hazclass,indtnumb,pkgroup,hazmat_id,shpnamecount,symbol )
{
   document.pshipnames.shpname.value=shpname;
   document.pshipnames.hazardclass.value=hazclass;
   document.pshipnames.intdnumber.value=indtnumb;
   document.pshipnames.pkggroup.value=pkgroup;
   document.pshipnames.hazmat_id.value=hazmat_id;
   document.pshipnames.shpnamecount.value=shpnamecount;
   document.pshipnames.symbol.value=symbol;
}

function sendshipingname()
{
  var compnumbn = document.getElementById("CompNum");

  var shpname = document.getElementById("shpname");
  if ( shpname.value.length > 0 )
  {
	var opnhazard_class  = opener.document.getElementById("hazard_class"+compnumbn.value+"");
	opnhazard_class.value = document.pshipnames.hazardclass.value;

	var opnpacking_group  = opener.document.getElementById("packing_group"+compnumbn.value+"");
	opnpacking_group.value = document.pshipnames.pkggroup.value;

	var opnun_number = opener.document.getElementById("un_number"+compnumbn.value+"");
	opnun_number.value = document.pshipnames.intdnumber.value;

	var opnhazmat_id = opener.document.getElementById("hazmat_id"+compnumbn.value+"");
	opnhazmat_id.value = document.pshipnames.hazmat_id.value;

	var opnsymbol = opener.document.getElementById("symbol"+compnumbn.value+"");
	opnsymbol.value = document.pshipnames.symbol.value;

	var opntechnical_name  = opener.document.getElementById("technical_name"+compnumbn.value+"");
	var symbol = document.getElementById("symbol");
	if (symbol.value == "G" || symbol.value == "A G" || symbol.value == "D G" || symbol.value == "G I")
	{
		opntechnical_name.className = "Inwhitel";
		opntechnical_name.readOnly = false;
	}
	opntechnical_name.value = "";

	var shpnamecount = document.getElementById("shpnamecount");
	var hazmat_id = document.getElementById("hazmat_id");
	/*if (shpnamecount.value*1 > 1)
	{
	  var opnshpname  = opener.document.getElementById("ground_shipping_name"+compnumbn.value+"");
     opnshpname.value = "";
	}
	else
	{
		var opnshpname  = opener.document.getElementById("ground_shipping_name"+compnumbn.value+"");
   	opnshpname.value = shpname.value;

		window.close();
	}*/

	loc = "/tcmIS/hub/propershpname?Action=showmultiplenames";
   loc = loc + "&hazmat_id=" + hazmat_id.value;
   loc = loc + "&CompNum=" + compnumbn.value;
  	window.location.replace(loc);
  }
}

function togglePage(tagid, title)
{
    for (var i=0; i<cmds.length; i++)
    {
        var tag = document.all(cmds[i]+"_tabchild");

        if (cmds[i] == tagid)
        {
            setVisible(cmds[i], true)
            tag.className = "tab_selected_tab";
        }
        else
        {
            setVisible(cmds[i], false);
            tag.className = "tab_tab";
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

function nonregulatedvalues(idennumber)
{
var nonreg = document.getElementById("nonreg"+idennumber+"");
if (nonreg.checked)
{
var grdshipname = document.getElementById("ground_shipping_name"+idennumber+"");
grdshipname.value = "";

var hazard_class = document.getElementById("hazard_class"+idennumber+"");
hazard_class.value = "0";

var un_number = document.getElementById("un_number"+idennumber+"");
//un_number.value = "NONREG";
un_number.value = "";

var packing_group = document.getElementById("packing_group"+idennumber+"");
packing_group.value = "";

var air_hazard = document.getElementById("air_hazard"+idennumber+"");
air_hazard.checked = false;

var water_hazard = document.getElementById("water_hazard"+idennumber+"");
water_hazard.checked = false;

var ground_hazard = document.getElementById("ground_hazard"+idennumber+"");
ground_hazard.checked = false;
}
}

function uncehknonreg (idennumber)
{
var nonreg = document.getElementById("nonreg"+idennumber+"");
nonreg.checked = false;
}

function groundhazchecked (idennumber)
{
var ground_hazard = document.getElementById("ground_hazard"+idennumber+"");
if (ground_hazard.checked)
{
var air_hazard = document.getElementById("air_hazard"+idennumber+"");
air_hazard.checked = true;

var water_hazard = document.getElementById("water_hazard"+idennumber+"");
water_hazard.checked = true;

var nonreg = document.getElementById("nonreg"+idennumber+"");
nonreg.checked = false;
}
else
{
var air_hazard = document.getElementById("air_hazard"+idennumber+"");
air_hazard.checked = false;

var water_hazard = document.getElementById("water_hazard"+idennumber+"");
water_hazard.checked = false;
}
}

function checkrqweight (idennumber)
{
var weight_lb = document.getElementById("weight_lb"+idennumber+"");
var reportable_quantity_lb = document.getElementById("reportable_quantity_lb"+idennumber+"");

if (reportable_quantity_lb.value*1 > weight_lb.value*1)
{
	var pkg_gt_rq = document.getElementById("pkg_gt_rq"+idennumber+"");
	pkg_gt_rq.checked = true;
}
}

function actionValue(name, entered)
{
    var actvalue = name.toString();
    window.document.MainForm.Action.value = actvalue;
    var total  =  window.document.MainForm.totalComps.value;
    var finalMsgt;
    finalMsgt = "Please enter valid values for: \n\n";
    var allClear = 0;
    var result ;

    if (actvalue == "Update")
    {
        for ( var p = 0 ; p < total ; p ++)
        {
            var grdshipname = document.getElementById("ground_shipping_name"+(p+1).toString()+""+p.toString()+"");
            var materialid = document.getElementById("material_id"+(p+1).toString()+""+p.toString()+"");
				var hazard_class = document.getElementById("hazard_class"+(p+1).toString()+""+p.toString()+"");
            var packing_group = document.getElementById("packing_group"+(p+1).toString()+""+p.toString()+"");
            var un_number  =  document.getElementById("un_number"+ (p+1).toString() + ""+p.toString()+"");
				//var nonreg = document.getElementById("nonreg"+ (p+1).toString() + ""+p.toString()+"");
				var orm_d = document.getElementById("orm_d"+ (p+1).toString() + ""+p.toString()+"");

				//if (!nonreg.checked)
				{
					if (grdshipname.value.length < 1)
	            {
	                finalMsgt = finalMsgt +   " Proper Shipping Name for Material Id "+materialid.value+" \n" ;
	                allClear = 1;
	            }
            }

				/*if (!orm_d.checked)
				{
					if (hazard_class.value.length < 1)
	            {
	                finalMsgt = finalMsgt +   " Hazard Class for Material Id "+materialid.value+" \n" ;
	                allClear = 1;
	            }
            }*/

            /*if ( (un_number.value.length < 1) )
            {
                finalMsgt = finalMsgt +   " UN # or NA # for Material Id "+materialid.value+" \n" ;
                allClear = 1;
            }*/

        }
    }

    if (allClear < 1)
    {
       result = true;
    }
    else
    {
      alert(finalMsgt);
      result = false;
    }

if ( result && (actvalue == "Update") )
{
for ( var p = 0 ; p < total ; p ++)
{
/*part_id  =  document.getElementById("part_id"+ (p+1).toString() + ""+p.toString()+"");
mainFormpart_id  =  document.getElementById("part_id"+p.toString()+"");
mainFormpart_id.value = part_id.value;
material_id  =  document.getElementById("material_id"+ (p+1).toString() + ""+p.toString()+"");
mainFormmaterial_id  =  document.getElementById("material_id"+p.toString()+"");
mainFormmaterial_id.value = material_id.value;
item_id  =  document.getElementById("item_id"+ (p+1).toString() + ""+p.toString()+"");
mainFormitem_id  =  document.getElementById("item_id"+p.toString()+"");
mainFormitem_id.value = item_id.value;*/
pkg_gt_rq  =  document.getElementById("pkg_gt_rq"+ (p+1).toString() + ""+p.toString()+"");
mainFormpkg_gt_rq  =  document.getElementById("pkg_gt_rq"+p.toString()+"");
//mainFormpkg_gt_rq.value = pkg_gt_rq.value;
if (pkg_gt_rq.checked)
{
mainFormpkg_gt_rq.value = "Y";
}
else
{
mainFormpkg_gt_rq.value = "N";
}

/*test_pkg_gt_rq  =  document.getElementById("test_pkg_gt_rq"+ (p+1).toString() + ""+p.toString()+"");
mainFormtest_pkg_gt_rq  =  document.getElementById("test_pkg_gt_rq"+p.toString()+"");
mainFormtest_pkg_gt_rq.value = test_pkg_gt_rq.value;*/
bulk_pkg_marine_pollutant  =  document.getElementById("bulk_pkg_marine_pollutant"+ (p+1).toString() + ""+p.toString()+"");
mainFormbulk_pkg_marine_pollutant  =  document.getElementById("bulk_pkg_marine_pollutant"+p.toString()+"");
//mainFormbulk_pkg_marine_pollutant.value = bulk_pkg_marine_pollutant.value;
if (bulk_pkg_marine_pollutant.checked)
{
mainFormbulk_pkg_marine_pollutant.value = "Y";
}
else
{
mainFormbulk_pkg_marine_pollutant.value = "N";
}

orm_d  =  document.getElementById("orm_d"+ (p+1).toString() + ""+p.toString()+"");
mainFormorm_d  =  document.getElementById("orm_d"+p.toString()+"");
if (orm_d.checked)
{
mainFormorm_d.value = "Y";
}
else
{
mainFormorm_d.value = "N";
}
/*weight_lb  =  document.getElementById("weight_lb"+ (p+1).toString() + ""+p.toString()+"");
mainFormweight_lb  =  document.getElementById("weight_lb"+p.toString()+"");
mainFormweight_lb.value = weight_lb.value;
material_desc  =  document.getElementById("material_desc"+ (p+1).toString() + ""+p.toString()+"");
mainFormmaterial_desc  =  document.getElementById("material_desc"+p.toString()+"");
mainFormmaterial_desc.value = material_desc.value;*/
hazard_class  =  document.getElementById("hazard_class"+ (p+1).toString() + ""+p.toString()+"");
mainFormhazard_class  =  document.getElementById("hazard_class"+p.toString()+"");
mainFormhazard_class.value = hazard_class.value;
subsidiary_hazard_class  =  document.getElementById("subsidiary_hazard_class"+ (p+1).toString() + ""+p.toString()+"");
mainFormsubsidiary_hazard_class  =  document.getElementById("subsidiary_hazard_class"+p.toString()+"");
mainFormsubsidiary_hazard_class.value = subsidiary_hazard_class.value;

packing_group  =  document.getElementById("packing_group"+ (p+1).toString() + ""+p.toString()+"");
mainFormpacking_group  =  document.getElementById("packing_group"+p.toString()+"");
mainFormpacking_group.value = packing_group.value;

un_number  =  document.getElementById("un_number"+ (p+1).toString() + ""+p.toString()+"");
mainFormun_number  =  document.getElementById("un_number"+p.toString()+"");
mainFormun_number.value = un_number.value;
ground_shipping_name  =  document.getElementById("ground_shipping_name"+ (p+1).toString() + ""+p.toString()+"");
mainFormground_shipping_name  =  document.getElementById("ground_shipping_name"+p.toString()+"");
mainFormground_shipping_name.value = ground_shipping_name.value;
/*dry_ice  =  document.getElementById("dry_ice"+ (p+1).toString() + ""+p.toString()+"");
mainFormdry_ice  =  document.getElementById("dry_ice"+p.toString()+"");
mainFormdry_ice.value = dry_ice.value;*/
erg  =  document.getElementById("erg"+ (p+1).toString() + ""+p.toString()+"");
mainFormerg  =  document.getElementById("erg"+p.toString()+"");
mainFormerg.value = erg.value;
/*passenger_air_limit  =  document.getElementById("passenger_air_limit"+ (p+1).toString() + ""+p.toString()+"");
mainFormpassenger_air_limit  =  document.getElementById("passenger_air_limit"+p.toString()+"");
mainFormpassenger_air_limit.value = passenger_air_limit.value;
passenger_air_unit  =  document.getElementById("passenger_air_unit"+ (p+1).toString() + ""+p.toString()+"");
mainFormpassenger_air_unit  =  document.getElementById("passenger_air_unit"+p.toString()+"");
mainFormpassenger_air_unit.value = passenger_air_unit.value;
cargo_air_limit  =  document.getElementById("cargo_air_limit"+ (p+1).toString() + ""+p.toString()+"");
mainFormcargo_air_limit  =  document.getElementById("cargo_air_limit"+p.toString()+"");
mainFormcargo_air_limit.value = cargo_air_limit.value;
cargo_air_unit  =  document.getElementById("cargo_air_unit"+ (p+1).toString() + ""+p.toString()+"");
mainFormcargo_air_unit  =  document.getElementById("cargo_air_unit"+p.toString()+"");
mainFormcargo_air_unit.value = cargo_air_unit.value;*/
marine_pollutant  =  document.getElementById("marine_pollutant"+ (p+1).toString() + ""+p.toString()+"");
mainFormmarine_pollutant  =  document.getElementById("marine_pollutant"+p.toString()+"");
//mainFormmarine_pollutant.value = marine_pollutant.value;
if (marine_pollutant.checked)
{
mainFormmarine_pollutant.value = "Y";
}
else
{
mainFormmarine_pollutant.value = "N";
}

reportable_quantity_lb  =  document.getElementById("reportable_quantity_lb"+ (p+1).toString() + ""+p.toString()+"");
mainFormreportable_quantity_lb  =  document.getElementById("reportable_quantity_lb"+p.toString()+"");
mainFormreportable_quantity_lb.value = reportable_quantity_lb.value;

/*ground_hazard  =  document.getElementById("ground_hazard"+ (p+1).toString() + ""+p.toString()+"");
mainFormground_hazard  =  document.getElementById("ground_hazard"+p.toString()+"");
mainFormground_hazard.value = ground_hazard.value;
if (ground_hazard.checked)
{
mainFormground_hazard.value = "Y";
}
else
{
mainFormground_hazard.value = "N";
}

air_hazard  =  document.getElementById("air_hazard"+ (p+1).toString() + ""+p.toString()+"");
mainFormair_hazard  =  document.getElementById("air_hazard"+p.toString()+"");
mainFormair_hazard.value = air_hazard.value;
if (air_hazard.checked)
{
mainFormair_hazard.value = "Y";
}
else
{
mainFormair_hazard.value = "N";
}

water_hazard  =  document.getElementById("water_hazard"+ (p+1).toString() + ""+p.toString()+"");
mainFormwater_hazard  =  document.getElementById("water_hazard"+p.toString()+"");
mainFormwater_hazard.value = water_hazard.value;
if (water_hazard.checked)
{
mainFormwater_hazard.value = "Y";
}
else
{
mainFormwater_hazard.value = "N";
}
*/

shipping_info_remarks  =  document.getElementById("shipping_info_remarks"+ (p+1).toString() + ""+p.toString()+"");
mainFormshipping_info_remarks  =  document.getElementById("shipping_info_remarks"+p.toString()+"");
mainFormshipping_info_remarks.value = shipping_info_remarks.value;
/*packaging  =  document.getElementById("packaging"+ (p+1).toString() + ""+p.toString()+"");
mainFormpackaging  =  document.getElementById("packaging"+p.toString()+"");
mainFormpackaging.value = packaging.value;*/

technical_name  =  document.getElementById("technical_name"+ (p+1).toString() + ""+p.toString()+"");
maintechnical_name  =  document.getElementById("technical_name"+p.toString()+"");
maintechnical_name.value = technical_name.value;

hazmat_id  =  document.getElementById("hazmat_id"+ (p+1).toString() + ""+p.toString()+"");
mainhazmat_id  =  document.getElementById("hazmat_id"+p.toString()+"");
mainhazmat_id.value = hazmat_id.value;

symbol  =  document.getElementById("symbol"+ (p+1).toString() + ""+p.toString()+"");
mainsymbol  =  document.getElementById("symbol"+p.toString()+"");
mainsymbol.value = symbol.value;
}
}

return result;
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

function ShowMSDS(CompNum)
{
 var loc;
 var materialid = document.getElementById("material_id"+CompNum+"");
 loc = "https://www.tcmis.com/tcmIS/all/ViewMsds?showspec=N&act=msds&id="+materialid.value+"&cl=Internal";

 openWinGeneric(loc,"MSDS","800","600","yes")
}


function getpropershipname(CompNum)
{

}

function showshipname()
{
   var item_id  =  document.getElementById("item_id");

	loc = "/tcmIS/hub/propershpname?Action=findpropershpname";
   loc = loc + "&item_id=" + item_id.value;
   openWinGeneric(loc,"getProperShipping_Name","50","50","yes")

    /*for ( var p = 0 ; p < total ; p ++)
    {
            var grdshipname = document.getElementById("ground_shipping_name"+(p+1).toString()+""+p.toString()+"");
            var hazard_class = document.getElementById("hazard_class"+(p+1).toString()+""+p.toString()+"");
            var packing_group = document.getElementById("packing_group"+(p+1).toString()+""+p.toString()+"");
            var un_number  =  document.getElementById("un_number"+ (p+1).toString() + ""+p.toString()+"");

            finalshipname = finalshipname + grdshipname.value + " " ;
            finalshipname = finalshipname + hazard_class.value + "," ;
            if (un_number.value.length > 0)
            {
            finalshipname = finalshipname + un_number.value + "," ;
            }
            finalshipname = finalshipname + packing_group.value + "<BR>" ;
   }

var finalshipnamecell  =  document.getElementById("finalshipname");
finalshipnamecell.innerHTML =finalshipname;
*/
}

function initTabs()
{
    document.all.item("Component1").style["display"] = "";
}

function cancel()
{
    window.close();
}