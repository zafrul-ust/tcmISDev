function submitSearchForm() {
	/*
	 * Make sure to not set the target of the form to anything other than
	 * resultFrame
	 */
	var approverName11 = document.getElementById("requestorName");
	document.genericForm.target = 'resultFrame';
	document.getElementById("uAction").value = 'search';
	// set start search time
	var now = new Date();
	var startSearchTime = document.getElementById("startSearchTime");
	startSearchTime.value = now.getTime();
	showPleaseWait();
	return true;

}

var chargeMap;
var facToAcctMap;
function displaySearchArgs() {
	var accts = facToAcctMap[$v('facilityId')];
	 var obj = $("accountSysId");
	 for (var i = obj.length; i > 0;i--) {
	   obj[i] = null;
	 }
	for(var i = 0; i < accts.length; i++)
		{
			setOption(i,accts[i],accts[i], "accountSysId");
		}
	
	showCharge(accts[0],null);
	
}

function showCharge(acct,type)
{
	if(acct == null || acct == undefined)
		acct = $v('accountSysId');
	
	var checkI = chargeMap[acct + '-i'];
	var checkD = chargeMap[acct + '-d'];
	if(checkI != null && checkI != undefined && checkD != null && checkD != undefined)
		{
			$('chargeTypeRow').style.display = "";
			if(type == null || type == undefined)
				{
					type  = 'd';
					$('chargeType').value = 'd';
					$('directCharge').checked = true;
					$('inDirectCharge').checked = false;
				}
			else{
				if($('directCharge').checked == true)
					$('chargeType').value = 'd';
				else
					$('chargeType').value = 'i';
			}
		}
	else if (checkI != null && checkI != undefined)
	{
		$('directCharge').checked = false;
		$('inDirectCharge').checked = true;
		$('chargeType').value = 'i';
		$('chargeTypeRow').style.display = "none";
		if(type == null || type == undefined)
			type  = 'i';
	}
	else
	{
		$('directCharge').checked = true;
		$('inDirectCharge').checked = false;
		$('chargeType').value = 'd';
		$('chargeTypeRow').style.display = "none";
		if(type == null || type == undefined)
			type  = 'd';
	}
	
	var acctId = chargeMap[acct + '-' + type];
	$('chargeId1').value = acctId.rows[4];
	$('chargeId2').value = acctId.rows[5];
	$('chargeId3').value = acctId.rows[6];
	$('chargeId4').value = acctId.rows[7];
	var labelCount = 0;
	var labels = new Array();
	$('chargeRow1').style.display = "none";
	$('chargeRow2').style.display = "none";
	$('chargeNumber1Exists').value = "N";
	$('chargeNumber2Exists').value = "N";
	$('chargeNumber3Exists').value = "N";
	$('chargeNumber4Exists').value = "N";
	$('charge1Display').innerHTML = '';
	$('charge2Display').innerHTML = '';
	$('charge3Display').innerHTML = '';
	$('charge4Display').innerHTML = '';
	$('charge1Content').innerHTML = '';	
	$('charge2Content').innerHTML = '';	
	$('charge3Content').innerHTML = '';	
	$('charge4Content').innerHTML = '';	
	$('chargeLabel1').value = "";
	$('chargeLabel2').value = "";
	$('chargeLabel3').value = "";
	$('chargeLabel4').value = "";

	for(var i = 0;i < acctId.rows.length;i++)
		{
			var cur = acctId.rows[i];
			if (cur.length > 1 && cur.substr(0,1) == 'Y')
				{
					labels[labelCount] = cur.substr(2,cur.length);			
					switch(i)
					{
						case 0:
							$('chargeRow1').style.display = "";
							$('charge1Display').innerHTML = labels[labelCount] + ':';
							$('charge1Content').innerHTML = '<input name="chargeNumber1" id="chargeNumber1" type="text" class="inputBox" size="15">';
							$('chargeNumber1Exists').value = "Y";
							$('chargeLabel1').value = labels[labelCount++];
						  break;
						case 1:
							$('chargeRow1').style.display = "";
							$('charge2Display').innerHTML = labels[labelCount] + ':';
							$('charge2Content').innerHTML = '<input name="chargeNumber2" id="chargeNumber2" type="text" class="inputBox" size="15">';
							$('chargeNumber2Exists').value = "Y";
							$('chargeLabel2').value = labels[labelCount++];
						  break;
						case 2:
							$('chargeRow2').style.display = "";
							$('charge3Display').innerHTML = labels[labelCount] + ':';
							$('charge3Content').innerHTML = '<input name="chargeNumber3" id="chargeNumber3" type="text" class="inputBox" size="15">';
							$('chargeNumber3Exists').value = "Y";
							$('chargeLabel3').value = labels[labelCount++];
							break;
						case 3:
							$('chargeRow2').style.display = "";
							$('charge4Display').innerHTML = labels[labelCount] + ':';
							$('charge4Content').innerHTML = '<input name="chargeNumber4" id="chargeNumber4" type="text" class="inputBox" size="15">';
							$('chargeNumber4Exists').value = "Y";
							$('chargeLabel4').value = labels[labelCount++];
					     break;
						default:
					}
				}
		}
	$('noLevel1ChgAcctApprover').value = acctId.rows[8];
	$('noLevel2ChgAcctApprover').value = acctId.rows[9];
	$('noLevel3ChgAcctApprover').value = acctId.rows[10];
	$('noLevel4ChgAcctApprover').value = acctId.rows[11];
	$('approveByPrice').value = acctId.rows[12];
}


function createXSL() {
	var flag = true;// validateForm();
	if (flag) {
		$('uAction').value = 'createExcel';
		openWinGenericViewFile('/tcmIS/common/loadingfile.jsp', '_ChargeNumberManagementExcel', '650', '600', 'yes');
		document.genericForm.target = '_ChargeNumberManagementExcel';
		var a = window.setTimeout("document.genericForm.submit();", 50);
	}
}
