/*
function openwin ()
 {

 MSDS_Help = window.open("/tcmIS/help/new/", "MSDS_Help",

 "toolbar=no,location=no,directories=no,status=yes" +

 ",menubar=no,scrollbars=yes,resizable=yes," +

 "width=730,height=520");

 }*/

function openWinGeneric(destination,WinName,WinWidth, WinHeight,Resizable,topCoor,leftCoor )
{
    windowprops = "toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,status=yes,top="+topCoor+",left="+leftCoor+",width="+WinWidth+",height="+WinHeight+",resizable="+ Resizable;
    preview = window.open(destination,WinName,windowprops);
}

function closeUserWin() {
   window.close();
}

function showMsg(){
 alert("");
}

function clearDate(object)
{
   object.value = "";
}

function auditSearch(){
   var tmp = document.getElementById("searchBy");
   if (tmp.value == "None") {
      alert("Select an Approval Role and click on List Requests again.");
      return false;
   }else {
      return true;
   }
}

//approved part number
function auditPartNumber(entered) {
	//part number
   //var testvar = document.getElementById("csm_part_no");
   //if (testvar.value.length < 1) {
   //  alert("Please Enter the Part # Value.");
   //  return false;
   //}
   //unit of sale
   var tmp = document.getElementById("uom");
   if (tmp != null) {
   	if (tmp.value == "None") {
      	alert("Please select a unit of measure.");
      	return false;
   	}
		tmp = document.getElementById("unitOfSaleQtyPerEach");
      if (!isFloat(tmp.value)) {
         alert("Please Enter a unit price (real number only ex. 1.2345)");
         return false;
      }
   }
   return true;
}
//end of part number approve

//approve pricing
function auditPricingData(entered) {
   //baseline
   var tmp = document.getElementById("baseline");
   if (!isFloat(tmp.value)) {
      alert("Please Enter a baseline price (real number only ex. 1.2345)");
      return false;
   }
   tmp= document.getElementById("selling");
   if (!isFloat(tmp.value)) {
      alert("Please Enter a selling price (real number only ex. 1.2345)");
      return false;
   }
   //first make sure user select a supplier
   tmp = document.getElementById("supplierID");
   if (tmp.value.length < 1) {
      alert("Please select a supplier.");
      return false;
   }
   //supplier contact
   tmp = document.getElementById("contactID");
   if (tmp.value.length < 1) {
      alert("Please select a contact name.");
      return false;
   }
   //carrier
   tmp = document.getElementById("carrierID");
   if (tmp.value == "None") {
      alert("Please select a carrier.");
      return false;
   }
   //consignment
   tmp = document.getElementById("consignment");
   if (tmp.value == "None") {
      alert("Please select a consignment.");
      return false;
   }
   //multiple of
   tmp = document.getElementById("multipleOf");
   if (tmp.value.length > 0) {
      if (!isInteger(tmp.value)) {
         alert("Please enter a number for Multiple of (whole number only ex. 1)");
         return false;
      }
   }
   //lead time
   tmp = document.getElementById("leadTime");
   if (tmp.value.length > 0) {
      if (!isInteger(tmp.value)) {
         alert("Please enter a number lead time (whole number only ex. 5)");
         return false;
      }
   }
   //currency
   tmp = document.getElementById("currency");
   if (tmp.value == "None") {
      alert("Please select a currency.");
      return false;
   }
   //contract type
   tmp = document.getElementById("contractType");
   if (tmp.value == "None") {
      alert("Please select a contract type.");
      return false;
   }
   //end date
   tmp = document.getElementById("goodUntil");
   if (tmp.value.length < 1) {
      alert("Please select a good until date.");
      return false;
   }
   //purchasing price
	tmp= document.getElementById("purchasing");
	if (!isFloat(tmp.value)) {
		alert("Please Enter a purchasing price (real number only ex. 1.2345)");
		return false;
	}
   //upto quantity
   tmp = document.getElementById("uptoQuantity");
   if (tmp.value.length > 0) {
      if (!isInteger(tmp.value)) {
         alert("Please enter a number upto quantity (whole number only ex. 3)");
         return false;
      }
   }
   //if user enter uptoquantity I need the purchasing as well
   tmp = document.getElementById("uptoQuantity2");
   if (tmp.value.length > 0) {
      if (!isInteger(tmp.value)) {
         alert("Please enter a number upto quantity (whole number only ex. 3)");
         return false;
      }
		tmp = document.getElementById("purchasing2");
		if (tmp.value.length < 1) {
         alert("Please enter a purchasing price for upto quantity.");
         return false;
		}
   }
   tmp = document.getElementById("uptoQuantity3");
   if (tmp.value.length > 0) {
      if (!isInteger(tmp.value)) {
         alert("Please enter a number upto quantity (whole number only ex. 3)");
         return false;
      }
		tmp = document.getElementById("purchasing3");
		if (tmp.value.length < 1) {
         alert("Please enter a purchasing price for upto quantity.");
         return false;
		}
   }
   tmp = document.getElementById("uptoQuantity4");
   if (tmp.value.length > 0) {
      if (!isInteger(tmp.value)) {
         alert("Please enter a number upto quantity (whole number only ex. 3)");
         return false;
      }
		tmp = document.getElementById("purchasing4");
		if (tmp.value.length < 1) {
         alert("Please enter a purchasing price for upto quantity.");
         return false;
		}
   }
   /*check to see if user enter the baseline and selling price correctly
   var multiplier = document.getElementById("multiplier");
   if (multiplier.value != "-1") {
       var purchasing = document.getElementById("purchasing");
       var selling = document.getElementById("selling");
       if (purchasing.value*multiplier.value != selling.value) {
          alert("Purchasing price does not include tax ("+multiplier.value+").  Please change Purchasing or Selling price.");
          return false;
       }
   }*/

   return true;
}
//end of pricing approve

//search for supplier session
function searchSupplierWin()
{
   var supplierID = document.getElementById("supplierID");
   var companyID = document.getElementById("companyID");
   var loc = "/tcmIS/"+companyID.value+"/sourcing?Session=searchSupplierWin&searchText="+supplierID.value;
   openWinGeneric(loc,"searchSupplierWin","710","330","yes","240","280");
}

function auditSupplierSearch()
{
   var searchText = document.getElementById("supplierT");
   if (searchText.value.length < 1) {
      alert("Please enter something for Search Text.")
      return false;
   }else {
      return true;
   }
}

function getSupplierID(supplierID,supplierName,phone,paymentTerm)
{
   opener.document.catAddDetail.supplierID.value = supplierID;
   opener.document.catAddDetail.supplierName.value = supplierName+" ("+phone+")";
   //opener.document.catAddDetail.paymentTermT.value = paymentTerm;
   closeUserWin();
}
//end of supplier session

//change payment terms session
function paymentTermWin()
{
	var companyID = document.getElementById("companyID");
   var loc = "/tcmIS/"+companyID.value+"/sourcing?Session=paymentTermWin";
   openWinGeneric(loc,"paymentTermWin","410","330","yes","240","280");
}

function applyPaymentTerm(object) {
	//make sure user select a payment term
	var tmp = document.getElementById("paymentTerm");
	if (tmp.value == "None") {
 		alert("Select a payment term.");
 		return false;
 	}else {
      object.value = tmp.value;
      closeUserWin();
   }
}

function auditLoginPassword() {
   //make sure user type in login and password
   var loginID = document.getElementById("loginID");
   if (loginID.value.length < 1) {
      alert("Please enter an authorized login ID");
      return false;
   }
   var password = document.getElementById("password");
   if (password.value.length < 1) {
      alert("Please enter a password");
      return false;
   }
   return true;
}
//end of payment terms session

//search for carrier session
function searchCarrierWin()
{
   var requestID = document.getElementById("requestID");
   var companyID = document.getElementById("companyID");
   var loc = "/tcmIS/"+companyID.value+"/sourcing?Session=searchCarrierWin&requestID="+requestID.value;
   openWinGeneric(loc,"searchCarrierWin","710","330","yes","240","280");
}

function auditCarrierSearch()
{
   /*
   var searchText = document.getElementById("carrierT");
   if (searchText.value.length < 1) {
      alert("Please enter something for Search Text.")
      return false;
   }else {
      return true;
   }*/
   return true;
}

function getCarrierID(carrierID,carrierName,freightOnBoardID,freightOnBoardName)
{
   opener.document.catAddDetail.carrierID.value = carrierID;
   opener.document.catAddDetail.carrierName.value = carrierName;
   opener.document.catAddDetail.freightOnBoardID.value = freightOnBoardID;
   opener.document.catAddDetail.freightOnBoard.value = freightOnBoardName;
   closeUserWin();
}
//end of carrier session

//supplier contact session
function contactNameWin()
{
   var supplierID = document.getElementById("supplierID");
   //got to select a supplier first
   if (supplierID.value.length < 1) {
      alert("Please select a supplier first.")
      return false;
   }
   //open search window
   var companyID = document.getElementById("companyID");
   var loc = "/tcmIS/"+companyID.value+"/sourcing?Session=contactNameWin&supplierID="+supplierID.value;
   openWinGeneric(loc,"contactNameWin","410","370","yes","240","280");
}

function lastNameChecked() {
   var lastName = document.getElementById("lastR");
   if (lastName.checked) {
       window.document.contactNameWin.firstR.checked = false;
   }else {
       window.document.contactNameWin.firstR.checked = true;
   }
   window.document.contactNameWin.searchBy.value = "LASTNAME";
}

function firstNameChecked() {
   var firstName = document.getElementById("firstR");
   if (firstName.checked) {
       window.document.contactNameWin.lastR.checked = false;
   }else {
       window.document.contactNameWin.lastR.checked = true;
   }
   window.document.contactNameWin.searchBy.value = "FIRSTNAME";
}

function getContactID(contactID,contactName,phone)
{
   opener.document.catAddDetail.contactID.value = contactID;
   opener.document.catAddDetail.contactName.value = contactName+" ("+phone+")";
   closeUserWin();
}

function auditContactSearch()
{
   window.document.contactNameWin.Session.value = "contactNameWin";
   /*search text
   var searchText = document.getElementById("searchText");
   if (searchText.value.length < 1) {
      alert("Please enter something for Search Text.")
      return false;
   }else {
      return true;
   }*/
   return true;
}

function newContactWin()
{
   window.document.contactNameWin.Session.value = "newContactWin";
   return true;
}

function auditNewContact()
{
   var tmp = document.getElementById("lastNameT");
   if (tmp.value.length < 1) {
      alert("Please enter contact last name.")
      return false;
   }else {
      return true;
   }
   var tmp = document.getElementById("firstNameT");
   if (tmp.value.length < 1) {
      alert("Please enter contact first name.")
      return false;
   }else {
      return true;
   }
}
//end of contact session
