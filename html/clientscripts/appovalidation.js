

function invalidatePo()
{
    var validpo  =  document.getElementById("validpo");
	 validpo.value = "No";

	 var povalue  =  document.getElementById("po");
	 povalue.className="INVALIDTEXT";

	 var supplierid  =  document.getElementById("supplierid");
	 supplierid.value = "";

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