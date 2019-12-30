
function buildDropDown( arr, eleName ) {
   var obj = $(eleName);
   for (var i = obj.length; i >= 0;i--)
     obj[i] = null;
  
   var start = 0;
  	  
   for ( var i=0; i < arr.length; i++) {
	    setOption(start++,arr[i].name,arr[i].id,eleName);
   }
  
  obj.selectedIndex = 0;
}

function setDropdowns() {
 	buildDropDown(facilityAccountSysChargeTypeArr,"facilityId");
 	if($v("myDefaultFacilityId").length > 0)
 		$('facilityId').value = $v("myDefaultFacilityId");
 	$('facilityId').onchange = facilityChanged;
    $('accountSysId').onchange = accountSysIdChanged;	
    $('chargeType').onchange = chargeTypeChanged;	
    facilityChanged();
}

function facilityChanged()
{  
   var facilityId = $("facilityId");
   var arr = null;
   if( facilityId.value != '' ) {
   	   for(i=0;i< facilityAccountSysChargeTypeArr.length;i++)
   	   		if( facilityAccountSysChargeTypeArr[i].id == facilityId.value ) {
   	   			arr = facilityAccountSysChargeTypeArr[i].coll;
   	   			break;
   	   		}
   }

   buildDropDown(arr,"accountSysId");
   accountSysIdChanged();
}

function accountSysIdChanged()
{
   /*var facilityId = $("facilityId");
   var accountSysId = $("accountSysId");
   var arr = null;
   if( facilityId.value != '' && accountSysId.value != '' ) {
   	   for(i=0;i< facilityAccountSysChargeTypeArr.length;i++)
   	   		if( facilityAccountSysChargeTypeArr[i].id == facilityId.value ) {
		   	   for(j=0;j< facilityAccountSysChargeTypeArr[i].coll.length;j++)
	   	   		if( facilityAccountSysChargeTypeArr[i].coll[j].id == accountSysId.value ) {
	   	   			arr = facilityAccountSysChargeTypeArr[i].coll[j].coll;
	   	   			break;
   	   		    }
   	   		 break;
   	   		}
   }
   chargeTypeArray = arr;
   buildDropDown(arr,"chargeType");*/
   acctSysChanged = true;
   chargeTypeChanged();
}

function chargeTypeChanged() {
   var facilityId = $("facilityId");
   var accountSysId = $("accountSysId");
   var arr = null;
   if( facilityId.value != '' && accountSysId.value != '' ) {
   	   for(i=0;i< facilityAccountSysChargeTypeArr.length;i++)
   	   		if( facilityAccountSysChargeTypeArr[i].id == facilityId.value ) {
		   	   for(j=0;j< facilityAccountSysChargeTypeArr[i].coll.length;j++)
	   	   		if( facilityAccountSysChargeTypeArr[i].coll[j].id == accountSysId.value ) {
	   				var map = {};
	   	   			for(k=0;k< facilityAccountSysChargeTypeArr[i].coll[j].coll.length;k++) {
	   	   				//if( facilityAccountSysChargeTypeArr[i].coll[j].coll[k].id == chargeType.value ) 
	   	   				{
	   	   					arr = facilityAccountSysChargeTypeArr[i].coll[j].coll[k];
	   	   					var tmp;
	   	   					for(var l = 1; l < 5; l++)
	   	   						{
	   	   							switch(l)
	   	   							{
		   	   							case 1:
		   	   								if(arr.chargeLabel1 != '' && arr.chargeId1 != '')
				   	   							if((map[arr.id + ";" + arr.chargeId1] == null || map[arr.id + ";" + arr.chargeId1] == undefined))
			   	   								{
				   	   								tmp = new Array();
				   	   								tmp[0] = {name:arr.chargeLabel1,
				   	   										id:arr.id + ";" + arr.chargeId1,
				   	   										chargeName:arr.name,
				   	   										chargePos:1,
				   	   										chargeValidation:arr.chargeValidation1,
				   	   										printChargeDesc:arr.printChargeDesc1
				   	   										};
			   	   									map[arr.id + ";" + arr.chargeId1] = tmp;
			   	   								}
				   	   							else
			   	   								{
				   	   								tmp = map[arr.id + ";" + arr.chargeId1]; 
				   	   								tmp[tmp.length] = {name:arr.chargeLabel1,
			   	   										id:arr.id + ";" + arr.chargeId1,
			   	   										chargeName:arr.name,
			   	   										chargePos:1,
			   	   										chargeValidation:arr.chargeValidation1,
			   	   										printChargeDesc:arr.printChargeDesc1
			   	   										};
			   	   								}
		   	   								break;
		   	   							case 2:
			   	   							if(arr.chargeLabel2 != '' && arr.chargeId2 != '')
					   	   						if(map[arr.id + ";" + arr.chargeId2] == null || map[arr.id + ";" + arr.chargeId2] == undefined)
			   	   								{
				   	   								tmp = new Array();
				   	   								tmp[0] = {name:arr.chargeLabel2,
			   	   										id:arr.id + ";" + arr.chargeId2,
			   	   										chargeName:arr.name,
			   	   										chargePos:2,
			   	   										chargeValidation:arr.chargeValidation2,
			   	   										printChargeDesc:arr.printChargeDesc2
			   	   										};
			   	   									map[arr.id + ";" + arr.chargeId2] = tmp;
			   	   								}
				   	   							else
			   	   								{
				   	   								tmp = map[arr.id + ";" + arr.chargeId2]; 
				   	   								tmp[tmp.length] = {name:arr.chargeLabel2,
			   	   										id:arr.id + ";" + arr.chargeId2,
			   	   										chargeName:arr.name,
			   	   										chargePos:2,
			   	   										chargeValidation:arr.chargeValidation2,
			   	   										printChargeDesc:arr.printChargeDesc2
			   	   										};
			   	   								}
		   	   								break;
		   	   							case 3:
			   	   							if(arr.chargeLabel3 != '' && arr.chargeId3 != '')
					   	   						if(map[arr.id + ";" + arr.chargeId3] == null || map[arr.id + ";" + arr.chargeId3] == undefined)
			   	   								{
				   	   								tmp = new Array();
					   	   							tmp[0] = {name:arr.chargeLabel3,
				   	   										id:arr.id + ";" + arr.chargeId3,
				   	   										chargeName:arr.name,
				   	   										chargePos:3,
				   	   										chargeValidation:arr.chargeValidation3,
				   	   										printChargeDesc:arr.printChargeDesc3
				   	   										};
			   	   									map[arr.id + ";" + arr.chargeId3] = tmp;
			   	   								}
				   	   							else
			   	   								{
				   	   								tmp = map[arr.id + ";" + arr.chargeId3]; 
					   	   							tmp[tmp.length] = {name:arr.chargeLabel3,
				   	   										id:arr.id + ";" + arr.chargeId3,
				   	   										chargeName:arr.name,
				   	   										chargePos:3,
				   	   										chargeValidation:arr.chargeValidation3,
				   	   										printChargeDesc:arr.printChargeDesc3
				   	   										};
			   	   								}
		   	   								break;
		   	   							case 4:
			   	   							if(arr.chargeLabel4 != '' && arr.chargeId4 != '')
					   	   						if(map[arr.id + ";" + arr.chargeId4] == null || map[arr.id + ";" + arr.chargeId4] == undefined)
			   	   								{
				   	   								tmp = new Array();
					   	   							tmp[0] = {name:arr.chargeLabel4,
				   	   										id:arr.id + ";" + arr.chargeId4,
				   	   										chargeName:arr.name,
				   	   										chargePos:4,
				   	   										chargeValidation:arr.chargeValidation4,
				   	   										printChargeDesc:arr.printChargeDesc4
				   	   										};
			   	   									map[arr.id + ";" + arr.chargeId4] = tmp;
			   	   								}
				   	   							else
			   	   								{
				   	   								tmp = map[arr.id + ";" + arr.chargeId4]; 
					   	   							tmp[tmp.length] = {name:arr.chargeLabel4,
				   	   										id:arr.id + ";" + arr.chargeId4,
				   	   										chargeName:arr.name,
				   	   										chargePos:4,
				   	   										chargeValidation:arr.chargeValidation4,
				   	   										printChargeDesc:arr.printChargeDesc4
				   	   										};
			   	   								}
		   	   								break;
		   	   							default:
	   	   							}
	   	   						}	
	   	   				}
	   	   			}
	   	   			
  						if(acctSysChanged)
  						{
  							var chargeTypeArr = new Array();
	   	   					for(var key in map)
	   	   						{	
	   	   							if(map[key].length > 0)
	   	   								{ 	
	   	   									var iName = map[key][0].chargeName + ' (';
		   	   								for(var m = 0; m < map[key].length; m++)
		   	   									{
		   	   										iName += map[key][m].name;
		   	   										if(m != map[key].length - 1)
		   	   											iName += ' / ';
		   	   									}
		   	   									
		   	   								iName += ')';
			   	   							chargeTypeArr[chargeTypeArr.length] = {name:iName,
		   	   										id:key
			   	   							}
	   	   								}
	   	   							else
	   	   								chargeTypeArr = chargeTypeArr.concat(map[key]);
	   	   						}
	   	   						buildDropDown(chargeTypeArr,"chargeType");
	   	   						acctSysChanged = false;
	   	   						showLabels( getLabels(map[$v("chargeType")]));
  						}
  						else
  								showLabels(getLabels(map[$v("chargeType")]));


   	   		    }
   	   		 break;
   	   	} //end of for loop
        //clear charge number search fields
       $("chargeNumber1").value = "";
       $("chargeNumber2").value = "";
       $("chargeNumber3").value = "";
       $("chargeNumber4").value = "";
   }
}

function getLabels(arr)
{

	var retArr = new Object;
	if(typeof arr  != 'undefined')
	{
		for(var i = 0; i < arr.length;i++)
			if(retArr.chargeLabel1 == null || retArr.chargeLabel1 == undefined)
				retArr = {chargeLabel1:arr[i].name,
						chargeLabel2:retArr.chargeLabel2,
						chargeLabel3:retArr.chargeLabel3,
						chargeLabel4:retArr.chargeLabel4,
						chargeLabel1Alias:arr[i].chargePos,
						chargeLabel2Alias:retArr.chargeLabel2Alias,
						chargeLabel3Alias:retArr.chargeLabel3Alias,
						chargeLabel4Alias:retArr.chargeLabel4Alias,
						chargeValidation1:arr[i].chargeValidation,
						chargeValidation2:retArr.chargeValidation2,
						chargeValidation3:retArr.chargeValidation3,
						chargeValidation4:retArr.chargeValidation4,
						printChargeDesc1:arr[i].printChargeDesc,
						printChargeDesc2:retArr.printChargeDesc2,
						printChargeDesc3:retArr.printChargeDesc3,
						printChargeDesc4:retArr.printChargeDesc4
						};
			else if(retArr.chargeLabel2 == null || retArr.chargeLabel2 == undefined)
				retArr = {chargeLabel1:retArr.chargeLabel1,
						chargeLabel2:arr[i].name,
						chargeLabel3:retArr.chargeLabel3,
						chargeLabel4:retArr.chargeLabel4,
						chargeLabel1Alias:retArr.chargeLabel1Alias,
						chargeLabel2Alias:arr[i].chargePos,
						chargeLabel3Alias:retArr.chargeLabel3Alias,
						chargeLabel4Alias:retArr.chargeLabel4Alias,
						chargeValidation1:retArr.chargeValidation1,
						chargeValidation2:arr[i].chargeValidation,
						chargeValidation3:retArr.chargeValidation3,
						chargeValidation4:retArr.chargeValidation4,
						printChargeDesc1:retArr.printChargeDesc1,
						printChargeDesc2:arr[i].printChargeDesc,
						printChargeDesc3:retArr.printChargeDesc3,
						printChargeDesc4:retArr.printChargeDesc4
						};
			else if(retArr.chargeLabel3 == null || retArr.chargeLabel3 == undefined)
				retArr =  {chargeLabel2:retArr.chargeLabel2,
						chargeLabel3:arr[i].name,
						chargeLabel1:retArr.chargeLabel1,
						chargeLabel4:retArr.chargeLabel4,
						chargeLabel1Alias:retArr.chargeLabel1Alias,
						chargeLabel2Alias:retArr.chargeLabel2Alias,
						chargeLabel3Alias:arr[i].chargePos,
						chargeLabel4Alias:retArr.chargeLabel4Alias,
						chargeValidation1:retArr.chargeValidation1,
						chargeValidation2:retArr.chargeValidation2,
						chargeValidation3:arr[i].chargeValidation,
						chargeValidation4:retArr.chargeValidation4,
						printChargeDesc1:retArr.printChargeDesc1,
						printChargeDesc2:retArr.printChargeDesc2,
						printChargeDesc3:arr[i].printChargeDesc,
						printChargeDesc4:retArr.printChargeDesc4
						};
			else if(retArr.chargeLabel4 == null || retArr.chargeLabel4 == undefined)
				retArr =  {chargeLabel1:retArr.chargeLabel1,
					chargeLabel4:arr[i].name,
					chargeLabel3:retArr.chargeLabel3,
					chargeLabel2:retArr.chargeLabel2,
					chargeLabel1Alias:retArr.chargeLabel1Alias,
					chargeLabel2Alias:retArr.chargeLabel2Alias,
					chargeLabel3Alias:retArr.chargeLabel3Alias,
					chargeLabel4Alias:arr[i].chargePos,
					chargeValidation1:retArr.chargeValidation1,
					chargeValidation2:retArr.chargeValidation2,
					chargeValidation3:retArr.chargeValidation3,
					chargeValidation4:arr[i].chargeValidation,
					printChargeDesc1:retArr.printChargeDesc1,
					printChargeDesc2:retArr.printChargeDesc2,
					printChargeDesc3:retArr.printChargeDesc3,
					printChargeDesc4:arr[i].printChargeDesc
					};
	}
	return retArr;
}


function showLabels(arr) {
	    	if(arr.chargeLabel1 != null && arr.chargeLabel1 != undefined && arr.chargeLabel1.length > 0) {
	    		$("label1Span").style["display"] = "";
	    		$("label1Span").innerHTML = arr.chargeLabel1;
	    		$("chargeNumber1").style["display"] = "";
	    		$("chargeNumber1Label").value = arr.chargeLabel1;
	     		$("chargeLabel1").value = arr.chargeLabel1;
	     		$("chargeLabel1Alias").value = arr.chargeLabel1Alias;
	     		$("chargeValidation1").value = arr.chargeValidation1;
	     		$("printChargeDesc1").value = arr.printChargeDesc1;
	    	}
	    	else {
	    		$("label1Span").style["display"] = "none";
	    		$("label1Span").innerHTML = "";
	    		$("chargeNumber1").style["display"] = "none";
	      		$("chargeNumber1Label").value = '';
	    		$("chargeLabel1").value = '';   		
	    		$("chargeLabel1Alias").value = '';
	      		$("chargeValidation1").value = '';
	      		$("printChargeDesc1").value = '';
	    	}
	    	if(arr.chargeLabel2 != null && arr.chargeLabel2 != undefined && arr.chargeLabel2.length > 0) {
	    		$("label2Span").style["display"] = "";
	    		$("label2Span").innerHTML = arr.chargeLabel2;
	    		$("chargeNumber2").style["display"] = "";
	      		$("chargeNumber2Label").value = arr.chargeLabel2;
	      		$("chargeLabel2").value = arr.chargeLabel2;
	      		$("chargeLabel2Alias").value = arr.chargeLabel2Alias;
	      		$("chargeValidation2").value = arr.chargeValidation2;
	      		$("printChargeDesc2").value = arr.printChargeDesc2;
	    	}
	    	else {
	    		$("label2Span").style["display"] = "none";
	    		$("label2Span").innerHTML = "";
	    		$("chargeNumber2").style["display"] = "none";
	      		$("chargeNumber2Label").value = '';
	    		$("chargeLabel2").value = '';
	    		$("chargeLabel2Alias").value = '';
	    		$("chargeValidation2").value = '';
	    		$("printChargeDesc2").value = '';
	    	}
	    	if(arr.chargeLabel3 != null && arr.chargeLabel3 != undefined && arr.chargeLabel3.length > 0) {
	    		$("label3Span").style["display"] = "";
	    		$("label3Span").innerHTML = arr.chargeLabel3;
	    		$("chargeNumber3").style["display"] = "";
	      		$("chargeNumber3Label").value = arr.chargeLabel3;
	     		$("chargeLabel3").value = arr.chargeLabel3;
	      		$("chargeLabel3Alias").value = arr.chargeLabel3Alias;
	      		$("chargeValidation3").value = arr.chargeValidation3;
	      		$("printChargeDesc3").value = arr.printChargeDesc3;
	    	}
	    	else {
	    		$("label3Span").style["display"] = "none";
	    		$("label3Span").innerHTML = "";
	    		$("chargeNumber3").style["display"] = "none";
	      		$("chargeNumber3Label").value = '';
	    		$("chargeLabel3").value = '';
	    		$("chargeLabel3Alias").value = '';
	    		$("chargeValidation3").value = '';
	    		$("printChargeDesc3").value = '';
	    	}
	    	if(arr.chargeLabel4 != null && arr.chargeLabel4 != undefined && arr.chargeLabel4.length > 0) {
	    		$("label4Span").style["display"] = "";
	    		$("label4Span").innerHTML = arr.chargeLabel4;
	    		$("chargeNumber4").style["display"] = "";
	      		$("chargeNumber4Label").value = arr.chargeLabel4;
	      		$("chargeLabel4").value = arr.chargeLabel4;
	      		$("chargeLabel4Alias").value = arr.chargeLabel4Alias;
	      		$("chargeValidation4").value = arr.chargeValidation4;
	      		$("printChargeDesc4").value = arr.printChargeDesc4;
	    	}
	    	else {
	    		$("label4Span").style["display"] = "none";
	    		$("label4Span").innerHTML = "";
	    		$("chargeNumber4").style["display"] = "none";
	      		$("chargeNumber4Label").value = '';
	    		$("chargeLabel4").value = '';
	     		$("chargeLabel4Alias").value = '';
	     		$("chargeValidation4").value = '';
	     		$("printChargeDesc4").value = '';
	    	}
}

function submitSearchForm()
{
 /*Make sure to not set the target of the form to anything other than resultFrame*/
  var isValidSearchForm = true; //validateSearchCriteriaInput();
  var now = new Date();
  document.getElementById("startSearchTime").value = now.getTime();

  if(isValidSearchForm) {
   $("uAction").value = 'search';
   document.genericForm.target='resultFrame';
   showPleaseWait();
   return true;
  }
  else
  {
    return false;
  }
}

function createXSL(){
  var flag = true;//validateForm();
  if(flag) {
	$('uAction').value = 'createExcel';
    openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_ChargeNumberSetUpExcel','650','600','yes');
    document.genericForm.target='_ChargeNumberSetUpExcel';
    var a = window.setTimeout("document.genericForm.submit();",50);
  }
}