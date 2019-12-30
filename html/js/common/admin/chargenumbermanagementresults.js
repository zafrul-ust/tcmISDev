var mygrid;
var selectedRowId;
var oneEdit = false;
// Set this to be false if you don't want the grid width to resize based on
// window size.
var resizeGridWithWindow = true;

function callOnChangeFunction() {
}

function callValidateLinefunction(rowId) {
	// Validate the line here
	if (cellValue(rowId, "okDoUpdate") == "true") {
		validateLine(rowId);
	}
}

function update() {
	if (validationforUpdate()) {
		document.genericForm.target = ''; // Form name "genericForm" comes from struts config file
		$('uAction').value = 'update';		// $('formVariableName') is a utility function that does a document.getElementById('variableName')
							// $v('formVariableName') does the same with document.getElementById('variableName').value

        // set start search time
	    var now = new Date();
	    var startSearchTime = parent.document.getElementById("startSearchTime");
	    startSearchTime.value = now.getTime();
        parent.showPleaseWait(); // Show "please wait" while updating

		if (mygrid != null) {
			// This function prepares the grid dat for submitting top the server
			mygrid.parentFormOnSubmit();
		}

		document.genericForm.submit(); // Submit the form
	}
}

// validate the whole grid
function validationforUpdate() {
	var rowsNum = mygrid.getRowsNum();

	// This reflects the rowId we put in the JSON data 
	for ( var rowpos = 0; rowpos < rowsNum; rowpos++) {
		var rowId = mygrid.getRowId(rowpos);
		if (!validateLine(rowId)) 
		{
			//Select the failing line
			mygrid.selectRowById(rowId, null, false, false); 
			return false;
		}
	}

	if(oneEdit)
		return true;
	else
		{
			alert(messagesData.nothingchanged);
			return false;
		}
}

// validate one line here
function validateLine(rowId) {
	var errorMessage = messagesData.validvalues + "\n";
	var count = 0;

	if (cellValue(rowId, "newRow") == "Y" || cellValue(rowId, "updated") == "true")
		{
		var chargeNumber1 = $("chargeNumber1Name" + rowId);
		if (chargeNumber1 != undefined || chargeNumber1 != null) 
			if(chargeNumber1.value.length == 0)
				{
					errorMessage += "\n" + messagesData.chargeNumber1Label;
					count++;
				}
			else if(chargeNumber1.className == "inputBox red")
				{
					errorMessage += "\n" + messagesData.chargeNumber1Label;
					count++;
				}
		var chargeNumber2 = $("chargeNumber2Name" + rowId);
		if (chargeNumber2 != undefined || chargeNumber2 != null) 
			if(chargeNumber2.value.length == 0)
				{
					errorMessage += "\n" + messagesData.chargeNumber2Label;
					count++;
				}
			else if(chargeNumber2.className == "inputBox red")
			{
				errorMessage += "\n" + messagesData.chargeNumber2Label;
				count++;
			}
		var chargeNumber3 =  $("chargeNumber3Name" + rowId);
		if (chargeNumber3 != undefined || chargeNumber3 != null) 
			if(chargeNumber3.value.length == 0)
				{
					errorMessage += "\n" + messagesData.chargeNumber3Label;
					count++;
				}
			else if(chargeNumber3.className == "inputBox red")
			{
				errorMessage += "\n" + messagesData.chargeNumber3Label;
				count++;
			}
		var chargeNumber4 = $("chargeNumber4Name" + rowId);
		if (chargeNumber4 != undefined || chargeNumber4 != null) 
			if(chargeNumber4.value.length == 0)
				{
					errorMessage += "\n" + messagesData.chargeNumber4Label;
					count++;
				}
			else if(chargeNumber4.className == "inputBox red")
			{
				errorMessage += "\n" + messagesData.chargeNumber4Label;
				count++;
			}
		var approvalLevel1Limit = $v("approvalLevel1Limit" + rowId);
		var approvalLevel1LimitExists = false;
		if (approvalLevel1Limit != undefined || approvalLevel1Limit != null) 
			{
			approvalLevel1LimitExists = true;
			if(approvalLevel1Limit.length == 0 || !isInteger(approvalLevel1Limit))
				{
					errorMessage += "\n" + messagesData.approvalLevelLimit + ' 1';
					count++;
				}
			}
		var approvalLevel2Limit = $v("approvalLevel2Limit" + rowId);
		var approvalLevel2LimitExists = false;
		if (approvalLevel2Limit != undefined || approvalLevel2Limit != null) 
			{
			approvalLevel2LimitExists = true;
			if(approvalLevel2Limit.length == 0 || !isInteger(approvalLevel2Limit))
				{
					errorMessage += "\n" + messagesData.approvalLevelLimit + ' 2';
					count++;
				}
			else if(approvalLevel1LimitExists && parseInt(approvalLevel1Limit) > parseInt(approvalLevel2Limit))
				{
					errorMessage += "\n" + messagesData.approvalLimitGreater.replace('{0}', ' 2 ');
					count++;
				}
			}
		var approvalLevel3Limit = $v("approvalLevel3Limit" + rowId);
		var approvalLevel3LimitExists = false;
		if (approvalLevel3Limit != undefined || approvalLevel3Limit != null) 
			{
				approvalLevel3LimitExists = true;
				if(approvalLevel3Limit.length == 0 || !isInteger(approvalLevel3Limit))
					{
						errorMessage += "\n" + messagesData.approvalLevelLimit + ' 3';
						count++;
					}
				else if((approvalLevel1LimitExists && parseInt(approvalLevel1Limit) > parseInt(approvalLevel3Limit)) 
						|| (approvalLevel2LimitExists && parseInt(approvalLevel2Limit) > parseInt(approvalLevel3Limit)))
				{
					errorMessage += "\n" + messagesData.approvalLimitGreater.replace('{0}', ' 3 ');
					count++;
				}
			}
		var approvalLevel4Limit = $v("approvalLevel4Limit" + rowId);
		var approvalLevel4LimitExists = false;
		if (approvalLevel4Limit != undefined || approvalLevel4Limit != null) 
			{
				approvalLevel4LimitExists = true;
				if(approvalLevel4Limit.length == 0 || !isInteger(approvalLevel4Limit))
					{
						errorMessage += "\n" + messagesData.approvalLevelLimit + ' 4';
						count++;
					}
				else if((approvalLevel1LimitExists && parseInt(approvalLevel1Limit) > parseInt(approvalLevel4Limit)) 
						|| (approvalLevel2LimitExists && parseInt(approvalLevel2Limit) > parseInt(approvalLevel4Limit))
						|| (approvalLevel3LimitExists && parseInt(approvalLevel3Limit) > parseInt(approvalLevel4Limit)))
				{
					errorMessage += "\n" + messagesData.approvalLimitGreater.replace('{0}', ' 4 ');
					count++;
				}
			}
		var approvalLevel1Name1 = $v("approvalLevel1Name1" + rowId);
		if (approvalLevel1Name1 != undefined || approvalLevel1Name1 != null) 
			{
				approvalLevel1ApproverId1Exists = true;
				var approvalLevel1ApproverId1 = cellValue(rowId, 'approvalLevel1ApproverId1');
				if(approvalLevel1ApproverId1.length == 0)
					{
						errorMessage += "\n" + messagesData.approverName + ' 1-1';
						count++;
					}	
			}
		var approvalLevel1Name2 = $v("approvalLevel1Name2" + rowId);
		var approvalLevel1ApproverId2Exists = false;
		if (approvalLevel1Name2 != undefined || approvalLevel1Name2 != null) 
			{
				approvalLevel1ApproverId2Exists = true;
				var approvalLevel1ApproverId2 = cellValue(rowId, 'approvalLevel1ApproverId2');
				if(approvalLevel1ApproverId2.length == 0)
				{
					errorMessage += "\n" + messagesData.approverName + ' 1-2';
					count++;
				}
				/*else if(approvalLevel1ApproverId1 != '' && approvalLevel1ApproverId2 == approvalLevel1ApproverId1) //
				{
					errorMessage += "\n" + messagesData.approverNameSame + ' 1-2';
					count++;
				}	*/ 
			}
		var approvalLevel2Name1 = $v("approvalLevel2Name1" + rowId);
		var approvalLevel2ApproverId1Exists = false;
		if (approvalLevel2Name1 != undefined || approvalLevel2Name1 != null) 
			{
				approvalLevel2ApproverId1Exists = true;
				var approvalLevel2ApproverId1 = cellValue(rowId, "approvalLevel2ApproverId1");
				if(approvalLevel2ApproverId1.length == 0)
					{
						errorMessage += "\n" + messagesData.approverName + ' 2-1';
						count++;
					}
				/*else if((approvalLevel1ApproverId1 != '' && approvalLevel2ApproverId1 == approvalLevel1ApproverId1) || (approvalLevel1ApproverId2Exists && approvalLevel1ApproverId2 != '' && approvalLevel2ApproverId1 == approvalLevel1ApproverId2))//
					{
						errorMessage += "\n" + messagesData.approverNameSame + ' 2-1';
						count++;
					}*/	
			}
		var approvalLevel2Name2 = $v("approvalLevel2Name2" + rowId);
		var approvalLevel2ApproverId2Exists = false;
		if (approvalLevel2Name2 != undefined || approvalLevel2Name2 != null) 
			{
				approvalLevel2ApproverId2Exists = true;
				var approvalLevel2ApproverId2 = cellValue(rowId, "approvalLevel2ApproverId2");
				if(approvalLevel2ApproverId2.length == 0)
				{
					errorMessage += "\n" + messagesData.approverName + ' 2-2';
					count++;
				}	
				/*else if(approvalLevel2ApproverId1 != '' && approvalLevel2ApproverId2 == approvalLevel2ApproverId1)//
				{
					errorMessage += "\n" + messagesData.approverNameSame + ' 2-2';
					count++;
				}	*/
			}
		var approvalLevel3Name1 = $v("approvalLevel3Name1" + rowId);
		var approvalLevel3ApproverId1Exists = false;
		if (approvalLevel3Name1 != undefined || approvalLevel3Name1 != null) 
			{
				approvalLevel3ApproverId1Exists = true;
				var approvalLevel3ApproverId1 = cellValue(rowId, "approvalLevel3ApproverId1");
				if(approvalLevel3ApproverId1.length == 0)
				{
					errorMessage += "\n" + messagesData.approverName + ' 3-1';
					count++;
				}
				/*else if((approvalLevel1ApproverId1 != '' && approvalLevel3ApproverId1 == approvalLevel1ApproverId1) || (approvalLevel1ApproverId2Exists && approvalLevel1ApproverId2 != '' && approvalLevel3ApproverId1 == approvalLevel1ApproverId2) 
						|| (approvalLevel2ApproverId1Exists && approvalLevel2ApproverId1 != '' &&  approvalLevel3ApproverId1 == approvalLevel2ApproverId1) || (approvalLevel2ApproverId2Exists && approvalLevel2ApproverId2 != '' && approvalLevel3ApproverId1 == approvalLevel2ApproverId2))//
				{
					errorMessage += "\n" + messagesData.approverNameSame + ' 3-1';
					count++;
				}*/

			}
		var approvalLevel3Name2 = $v("approvalLevel3Name2" + rowId);
		var approvalLevel3ApproverId2Exists = false;
		if (approvalLevel3Name2 != undefined || approvalLevel3Name2 != null)
			{
				approvalLevel3ApproverId2Exists = true;
				var approvalLevel3ApproverId2 = cellValue(rowId, "approvalLevel3ApproverId2");
				if(approvalLevel3ApproverId2.length == 0)
				{
					errorMessage += "\n" + messagesData.approverName + ' 3-2';
					count++;
				}
				/*else if(approvalLevel3ApproverId1 != '' && approvalLevel3ApproverId2 == approvalLevel3ApproverId1)
				{
					errorMessage += "\n" + messagesData.approverNameSame + ' 3-2';
					count++;
				}	*/
				
			}
		var approvalLevel4Name1 = $v("approvalLevel4Name1" + rowId);
		var approvalLevel4ApproverId1Exists = false;
		if (approvalLevel4Name1 != undefined || approvalLevel4Name1 != null) 
			{
				approvalLevel4ApproverId1Exists = true;
				var approvalLevel4ApproverId1 = cellValue(rowId, "approvalLevel4ApproverId1");
				if(approvalLevel4ApproverId1.length == 0)
					{
						errorMessage += "\n" + messagesData.approverName + ' 4-1';
						count++;
					}
				/*else if((approvalLevel1ApproverId1 != '' && approvalLevel4ApproverId1 == approvalLevel1ApproverId1) || (approvalLevel1ApproverId2Exists && approvalLevel1ApproverId2 != '' && approvalLevel4ApproverId1 == approvalLevel1ApproverId2) 
						|| (approvalLevel2ApproverId1Exists && approvalLevel2ApproverId1 != '' && approvalLevel4ApproverId1 == approvalLevel2ApproverId1) || (approvalLevel2ApproverId2Exists && approvalLevel2ApproverId2 != '' && approvalLevel4ApproverId1 == approvalLevel2ApproverId2) 
						|| (approvalLevel3ApproverId1Exists && approvalLevel3ApproverId1 != '' && approvalLevel4ApproverId1 == approvalLevel3ApproverId1) || (approvalLevel3ApproverId2Exists && approvalLevel3ApproverId2 != '' && approvalLevel4ApproverId1 == approvalLevel3ApproverId2))
				{
					errorMessage += "\n" + messagesData.approverNameSame  + ' 4-1';
					count++;
				}	*/
						
			}
		var approvalLevel4Name2 = $v("approvalLevel4Name2" + rowId);
		var approvalLevel4ApproverId2Exists = false;
		if (approvalLevel4Name2 != undefined || approvalLevel4Name2 != null) 
			{
				var approvalLevel4ApproverId2 = cellValue(rowId, "approvalLevel4ApproverId2");
				approvalLevel4ApproverId2Exists = true;
				if(approvalLevel4ApproverId2.length == 0)
					{
						errorMessage += "\n" + messagesData.approverName  + ' 4-2';
						count++;
					}
				/*else if(approvalLevel4ApproverId1 != '' && approvalLevel4ApproverId2 == approvalLevel4ApproverId1)
				{
					errorMessage += "\n" + messagesData.approverNameSame + ' 4-2';
					count++;
				}	*/
			}		
		
		if (count > 0) {
			alert(errorMessage);
			return false;
		}
		else
			{
			oneEdit = true;
			return true; 
			}
	}
	else if(cellValue(rowId, "updated") == 'true')
		oneEdit = true;

	return true;
}

function addRow()
{
	var rId = new Date();
	var newId = rId.getTime();
	var newData = new Array();
	var cntr = 0;
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'Y'; // Permission
	
	if($v('chargeNumber1Exists') == 'Y')
	{
		newData[cntr++] = '';
		newData[cntr++] = '';
		newData[cntr++] = 'charge_number_1';
	}
	if($v('chargeNumber2Exists') == 'Y')
	{
		newData[cntr++] = '';
		newData[cntr++] = '';
		if($v('chargeNumber1Exists') == 'Y')
			newData[cntr++] = 'charge_number_2';
		else
			newData[cntr++] = 'charge_number_1';
	}
	if($v('chargeNumber3Exists') == 'Y')
	{
		newData[cntr++] = '';
		newData[cntr++] = '';
		if($v('chargeNumber1Exists') == 'Y' && $v('chargeNumber2Exists') == 'Y')
			newData[cntr++] = 'charge_number_3';
		else if($v('chargeNumber1Exists') == 'Y' || $v('chargeNumber2Exists') == 'Y')
			newData[cntr++] = 'charge_number_2';
		else
			newData[cntr++] = 'charge_number_1';
	}
	if($v('chargeNumber4Exists') == 'Y')
	{
		newData[cntr++] = '';
		newData[cntr++] = '';
		if($v('chargeNumber1Exists') == 'Y' && $v('chargeNumber2Exists') == 'Y' &&  $v('chargeNumber3Exists') == 'Y')
			newData[cntr++] = 'charge_number_4';
		else if(($v('chargeNumber1Exists') == 'Y' && $v('chargeNumber2Exists') == 'Y') || ($v('chargeNumber1Exists') == 'Y' && $v('chargeNumber3Exists') == 'Y') || ($v('chargeNumber2Exists') == 'Y' && $v('chargeNumber3Exists') == 'Y'))
			newData[cntr++] = 'charge_number_3';
		else if($v('chargeNumber1Exists') == 'Y' || $v('chargeNumber2Exists') == 'Y' || $v('chargeNumber3Exists') == 'Y')
			newData[cntr++] = 'charge_number_2';
		else
			newData[cntr++] = 'charge_number_1';
	}
	
	newData[cntr++] = true; // active
	var approveByPrice = $('approveByPrice').value;
	if(approveByPrice == 'Y')
	{
		newData[cntr++] = ''; // limit
		newData[cntr++] = ''; //currency
	}
	newData[cntr++] = ''; // start approver name, approver id
	newData[cntr++] = '';
	var noLevel1ChgAcctApprover = $('noLevel1ChgAcctApprover').value;
	if(noLevel1ChgAcctApprover == '2')
	{
		newData[cntr++] = '';
		newData[cntr++] = '';
	
	}
	var noLevel2ChgAcctApprover = $('noLevel2ChgAcctApprover').value;
	if(approveByPrice == 'Y' && noLevel2ChgAcctApprover != '0')
		newData[cntr++] = ''; // limit
	if(noLevel2ChgAcctApprover == '2')
	{
		newData[cntr++] = '';
		newData[cntr++] = '';
		newData[cntr++] = '';
		newData[cntr++] = '';
	}
	else if(noLevel2ChgAcctApprover == '1')
		{
			newData[cntr++] = '';
			newData[cntr++] = '';
		}

	var noLevel3ChgAcctApprover = $('noLevel3ChgAcctApprover').value;
	if(approveByPrice == 'Y' && noLevel3ChgAcctApprover != '0')
		newData[cntr++] = ''; // limit
	if(noLevel3ChgAcctApprover == '2')
	{
		newData[cntr++] = '';
		newData[cntr++] = '';
		newData[cntr++] = '';
		newData[cntr++] = '';
	}
	else if(noLevel3ChgAcctApprover == '1')
		{
			newData[cntr++] = '';
			newData[cntr++] = '';
		}
	var noLevel4ChgAcctApprover = $('noLevel4ChgAcctApprover').value;
	if(approveByPrice == 'Y' && noLevel4ChgAcctApprover != '0')
		newData[cntr++] = ''; // limit
	if(noLevel4ChgAcctApprover == '2')
	{
		newData[cntr++] = '';
		newData[cntr++] = '';
		newData[cntr++] = '';
		newData[cntr++] = '';
	}
	else if(noLevel4ChgAcctApprover == '1')
		{
			newData[cntr++] = '';
			newData[cntr++] = '';
		}

	newData[cntr++] = 'Y'; // oldActive
	newData[cntr++] = 'Y'; // newRow
	newData[cntr++] =  true; // updated
	newData[cntr++] =  $v('accountSysId'); // accountSysId
	newData[cntr++] =  $v('chargeType'); // chargeType
	newData[cntr++] =  $v('chargeId'); // chargeType
	newData[cntr++] =  $v('chargeId1'); // chargeType
	newData[cntr++] =  $v('chargeId2'); // chargeType
	newData[cntr++] =  $v('chargeId3'); // chargeType
	newData[cntr++] =  $v('chargeId4'); // chargeType
	newData[cntr++] = '';
	mygrid.addRow(newId,newData,0);
	mygrid.selectRowById(newId);	
	if($v('chargeNumber1Exists') == 'Y')
		chargeBind(newId,'chargeNumber1Name','chargeNumber1');
	if($v('chargeNumber2Exists') == 'Y')
		chargeBind(newId,'chargeNumber2Name','chargeNumber2');
	if($v('chargeNumber3Exists') == 'Y')
		chargeBind(newId,'chargeNumber3Name','chargeNumber3');
	if($v('chargeNumber4Exists') == 'Y')
		chargeBind(newId,'chargeNumber4Name','chargeNumber4');
	
	apprBind(newId,'approvalLevel1Name1','approvalLevel1ApproverId1');
	if(noLevel1ChgAcctApprover == '2')
		apprBind(newId,'approvalLevel1Name2','approvalLevel1ApproverId2');
	if(noLevel2ChgAcctApprover == '2')
	{
		apprBind(newId,'approvalLevel2Name1','approvalLevel2ApproverId1');
		apprBind(newId,'approvalLevel2Name2','approvalLevel2ApproverId2');
	}
	else if(noLevel2ChgAcctApprover == '1')
		apprBind(newId,'approvalLevel2Name1','approvalLevel2ApproverId1');
	if(noLevel3ChgAcctApprover == '2')
	{
		apprBind(newId,'approvalLevel3Name1','approvalLevel3ApproverId1');
		apprBind(newId,'approvalLevel3Name2','approvalLevel3ApproverId2');
	}
	else if(noLevel3ChgAcctApprover == '1')
		apprBind(newId,'approvalLevel3Name1','approvalLevel3ApproverId1');
	if(noLevel4ChgAcctApprover == '2')
	{
		apprBind(newId,'approvalLevel4Name1','approvalLevel4ApproverId1');
		apprBind(newId,'approvalLevel4Name2','approvalLevel4ApproverId2');
	}
	else if(noLevel4ChgAcctApprover == '1')
		apprBind(newId,'approvalLevel4Name1','approvalLevel4ApproverId1');
	oneEdit = true;
}

function activeChange(i,name)
{
	if(cellValue(i,'newRow') != 'Y')
		{
		if(cellValue(i,'oldActive') != cellValue(i,'active'))
			mygrid.cellById(i, mygrid.getColIndexById("updated")).setValue(true);
		else
			mygrid.cellById(i, mygrid.getColIndexById("updated")).setValue(false);
		}
}

function onRowSelect(rowId, cellId) {
	selectedRowId = rowId;
}

function invalidateRequestor(rowId, el1, el2)
{
 var requestorName  =  document.getElementById(el1 + rowId);
 if (requestorName.value.length == 0) {
   requestorName.className = "inputBox";
 }else {
   requestorName.className = "inputBox red";
 }
 //set to empty
 mygrid.cellById(rowId, mygrid.getColIndexById(el2)).setValue("");
}

function apprBind(rowId, el1, el2)
{
			j$().ready(function() {
				function log(event, data, formatted) {
					mygrid.cellById(rowId, mygrid.getColIndexById(el2)).setValue(formatted.split(":")[0]);
					$(el1 + rowId).className = "inputBox"; 
				} 
				
				j$("#" + el1 + rowId).autocomplete("getpersonneldata.do",{
					extraParams: {activeOnly: function() { return true; },
					  companyId: function() { return j$("#companyId").val(); } },
						width: 200,
						max: 10,  // default value is 10
						cacheLength:0, // disable cache here because we put "rownum < max" for better performance. Cache will make data off.
						scrollHeight: 200,
						formatItem: function(data, i, n, value) {
							return  value.split(":")[1].substring(0,40);
						},
						formatResult: function(data, value) {
							return value.split(":")[1];
						}
				});
				
				j$("#" + el1 + rowId).bind('keyup',(function(e) {
					  var keyCode = (e.keyCode ? e.keyCode : e.which);
		
					  if( keyCode != 13 && keyCode != 9) // 13 is for Enter; 9 is for Tab
					  	invalidateRequestor(rowId, el1, el2);
				}));
				
				
				j$("#" + el1 + rowId).result(log).next().click(function() {
					j$(this).prev().search();
				});
				
			}); 
}

function chargeBind(rowId, el1, el2)
{
			j$().ready(function() {
				function log(event, data, formatted) {
					var d = formatted.split(":");
					var chargeNumber1Exists = $v('chargeNumber1Exists');
					var chargeNumber2Exists = $v('chargeNumber2Exists');
					var chargeNumber3Exists = $v('chargeNumber3Exists');
					var chargeNumber4Exists = $v('chargeNumber4Exists');
					switch(d[0])
					{
					case 'charge_number_1':
						if(chargeNumber1Exists == 'Y' && d[0] == cellValue(rowId,'chargeNumber1IdAlias'))
							mygrid.cellById(rowId, mygrid.getColIndexById('chargeNumber1')).setValue(d[1]);
						if(chargeNumber2Exists == 'Y' && d[0] == cellValue(rowId,'chargeNumber2IdAlias'))
							mygrid.cellById(rowId, mygrid.getColIndexById('chargeNumber2')).setValue(d[1]);
						if(chargeNumber3Exists == 'Y' && d[0] == cellValue(rowId,'chargeNumber3IdAlias'))
							mygrid.cellById(rowId, mygrid.getColIndexById('chargeNumber3')).setValue(d[1]); 
						if(chargeNumber4Exists == 'Y' && d[0] == cellValue(rowId,'chargeNumber4IdAlias'))
							mygrid.cellById(rowId, mygrid.getColIndexById('chargeNumber4')).setValue(d[1]);
						break;
					case 'charge_number_2':
						if(chargeNumber1Exists == 'Y' && d[0] == cellValue(rowId,'chargeNumber1IdAlias'))
							mygrid.cellById(rowId, mygrid.getColIndexById('chargeNumber1')).setValue(d[1]);
						if(chargeNumber2Exists == 'Y' && d[0] == cellValue(rowId,'chargeNumber2IdAlias'))
							mygrid.cellById(rowId, mygrid.getColIndexById('chargeNumber2')).setValue(d[1]);
						if(chargeNumber3Exists == 'Y' && d[0] == cellValue(rowId,'chargeNumber3IdAlias'))
							mygrid.cellById(rowId, mygrid.getColIndexById('chargeNumber3')).setValue(d[1]); 
						if(chargeNumber4Exists == 'Y' && d[0] == cellValue(rowId,'chargeNumber4IdAlias'))
							mygrid.cellById(rowId, mygrid.getColIndexById('chargeNumber4')).setValue(d[1]);
						break;
					case 'charge_number_3':
						if(chargeNumber1Exists == 'Y' && d[0] == cellValue(rowId,'chargeNumber1IdAlias'))
							mygrid.cellById(rowId, mygrid.getColIndexById('chargeNumber1')).setValue(d[1]);
						if(chargeNumber2Exists == 'Y' && d[0] == cellValue(rowId,'chargeNumber2IdAlias'))
							mygrid.cellById(rowId, mygrid.getColIndexById('chargeNumber2')).setValue(d[1]);
						if(chargeNumber3Exists == 'Y' && d[0] == cellValue(rowId,'chargeNumber3IdAlias'))
							mygrid.cellById(rowId, mygrid.getColIndexById('chargeNumber3')).setValue(d[1]); 
						if(chargeNumber4Exists == 'Y' && d[0] == cellValue(rowId,'chargeNumber4IdAlias'))
							mygrid.cellById(rowId, mygrid.getColIndexById('chargeNumber4')).setValue(d[1]);
						break;
					case 'charge_number_4':
						if(chargeNumber1Exists == 'Y' && d[0] == cellValue(rowId,'chargeNumber1IdAlias'))
							mygrid.cellById(rowId, mygrid.getColIndexById('chargeNumber1')).setValue(d[1]);
						if(chargeNumber2Exists == 'Y' && d[0] == cellValue(rowId,'chargeNumber2IdAlias'))
							mygrid.cellById(rowId, mygrid.getColIndexById('chargeNumber2')).setValue(d[1]);
						if(chargeNumber3Exists == 'Y' && d[0] == cellValue(rowId,'chargeNumber3IdAlias'))
							mygrid.cellById(rowId, mygrid.getColIndexById('chargeNumber3')).setValue(d[1]); 
						if(chargeNumber4Exists == 'Y' && d[0] == cellValue(rowId,'chargeNumber4IdAlias'))
							mygrid.cellById(rowId, mygrid.getColIndexById('chargeNumber4')).setValue(d[1]);
						break;
					default:
						mygrid.cellById(rowId, mygrid.getColIndexById(d[0])).setValue(d[1]);
					}
					$(el1 + rowId).className = "inputBox"; 
				} 
				
				j$("#" + el1 + rowId).autocomplete("chargenumbermanagementresults.do?uAction=chrgBind",{
						extraParams: {chargeNumName: function() {
							switch(el1)
							{
							case 'chargeNumber1Name':
								return cellValue(rowId,'chargeNumber1IdAlias');
								break;
							case 'chargeNumber2Name':
								return cellValue(rowId,'chargeNumber2IdAlias');
								break;
							case 'chargeNumber3Name':
								return cellValue(rowId,'chargeNumber3IdAlias');
								break;
							case 'chargeNumber4Name':
								return cellValue(rowId,'chargeNumber4IdAlias');
								break;
							default:
								return '';
							}
						},
						chargeAssociations: function() {			
							var chargeAssociations = "";
							var chargeNumber1Exists = $v('chargeNumber1Exists');
							var chargeNumber2Exists = $v('chargeNumber2Exists');
							var chargeNumber3Exists = $v('chargeNumber3Exists');
							var chargeNumber4Exists = $v('chargeNumber4Exists');
							var chargeNumber1Name = $('chargeNumber1Name' + rowId);
							var chargeNumber2Name = $('chargeNumber2Name' + rowId);
							var chargeNumber3Name = $('chargeNumber3Name' + rowId);
							var chargeNumber4Name = $('chargeNumber4Name' + rowId);

							switch(el1)
							{
							case 'chargeNumber1Name':
								if(chargeNumber2Exists == 'Y' && chargeNumber2Name.value != '' && chargeNumber2Name.className != "inputBox red")
									chargeAssociations += cellValue(rowId,'chargeNumber2IdAlias') + ":" + cellValue(rowId,'chargeNumber2') + ","; 
								if(chargeNumber3Exists == 'Y' && chargeNumber3Name.value != '' && chargeNumber3Name.className != "inputBox red")
									chargeAssociations += cellValue(rowId,'chargeNumber3IdAlias') + ":" + cellValue(rowId,'chargeNumber3') + ","; 
								if(chargeNumber4Exists == 'Y' && chargeNumber4Name.value != '' && chargeNumber4Name.className != "inputBox red")
									chargeAssociations += cellValue(rowId,'chargeNumber4IdAlias') + ":" + cellValue(rowId,'chargeNumber4') + ","; 	
								return chargeAssociations;
								break;
							case 'chargeNumber2Name':
								if(chargeNumber1Exists == 'Y' && chargeNumber1Name.value != '' && chargeNumber1Name.className != "inputBox red")
									chargeAssociations += cellValue(rowId,'chargeNumber1IdAlias') + ":" + cellValue(rowId,'chargeNumber1') + ","; 
								if(chargeNumber3Exists == 'Y' && chargeNumber3Name.value != '' && chargeNumber3Name.className != "inputBox red")
									chargeAssociations += cellValue(rowId,'chargeNumber3IdAlias') + ":" + cellValue(rowId,'chargeNumber3') + ","; 
								if(chargeNumber4Exists == 'Y' && chargeNumber4Name.value != '' && chargeNumber4Name.className != "inputBox red")
									chargeAssociations += cellValue(rowId,'chargeNumber4IdAlias') + ":" + cellValue(rowId,'chargeNumber4') + ","; 	
								return chargeAssociations;
								break;
							case 'chargeNumber3Name':
								if(chargeNumber1Exists == 'Y' && chargeNumber1Name.value != '' && chargeNumber1Name.className != "inputBox red")
									chargeAssociations += cellValue(rowId,'chargeNumber1IdAlias') + ":" + cellValue(rowId,'chargeNumber1') + ","; 
								if(chargeNumber2Exists == 'Y' && chargeNumber2Name.value != '' && chargeNumber2Name.className != "inputBox red")
									chargeAssociations += cellValue(rowId,'chargeNumber2IdAlias') + ":" + cellValue(rowId,'chargeNumber2') + ","; 
								if(chargeNumber4Exists == 'Y' && chargeNumber4Name.value != '' && chargeNumber4Name.className != "inputBox red")
									chargeAssociations += cellValue(rowId,'chargeNumber4IdAlias') + ":" + cellValue(rowId,'chargeNumber4') + ","; 	
								return chargeAssociations;
								break;
							case 'chargeNumber4Name':
								if(chargeNumber1Exists == 'Y' && chargeNumber1Name.value != '' && chargeNumber1Name.className != "inputBox red")
									chargeAssociations += cellValue(rowId,'chargeNumber1IdAlias') + ":" + cellValue(rowId,'chargeNumber1') + ","; 
								if(chargeNumber2Exists == 'Y' && chargeNumber2Name.value != '' && chargeNumber2Name.className != "inputBox red")
									chargeAssociations += cellValue(rowId,'chargeNumber2IdAlias') + ":" + cellValue(rowId,'chargeNumber2') + ","; 
								if(chargeNumber3Exists == 'Y' && chargeNumber3Name.value != '' && chargeNumber3Name.className != "inputBox red")
									chargeAssociations += cellValue(rowId,'chargeNumber3IdAlias') + ":" + cellValue(rowId,'chargeNumber3') + ","; 	
								return chargeAssociations;
								break;
							default:
								return '';
							}
						},
						chargeNumVal: function() {return cellValue(rowId,el1);},
				  		chargeId: function() { 
				  			switch(el1)
							{
							case 'chargeNumber1Name':
								return cellValue(rowId,'chargeId1');
								break;
							case 'chargeNumber2Name':
								return cellValue(rowId,'chargeId2');
								break;
							case 'chargeNumber3Name':
								return cellValue(rowId,'chargeId3');
								break;
							case 'chargeNumber4Name':	
								return cellValue(rowId,'chargeId4');
								break;
							default:
								return '';
							}
							},
				  		accountSysId: function() { return $v('accountSysId');},
				  		chargeNumberPostionActual: function() { 
				  			switch(el1)
							{
							case 'chargeNumber1Name':
								return '1';
								break;
							case 'chargeNumber2Name':
								return '2';
								break;
							case 'chargeNumber3Name':
								return '3';
								break;
							case 'chargeNumber4Name':	
								return '4';
								break;
							default:
								return '';
							}
				  			},
				  		chargeNumberPostionActualAssociations: function() { 
				  			var chargeNumberPostionActualAssociations = "";
							var chargeNumber1Exists = $v('chargeNumber1Exists');
							var chargeNumber2Exists = $v('chargeNumber2Exists');
							var chargeNumber3Exists = $v('chargeNumber3Exists');
							var chargeNumber4Exists = $v('chargeNumber4Exists');
							var chargeNumber1Name = $('chargeNumber1Name' + rowId);
							var chargeNumber2Name = $('chargeNumber2Name' + rowId);
							var chargeNumber3Name = $('chargeNumber3Name' + rowId);
							var chargeNumber4Name = $('chargeNumber4Name' + rowId);

							switch(el1)
							{
							case 'chargeNumber1Name':
								if(chargeNumber2Exists == 'Y' && chargeNumber2Name.value != '' && chargeNumber2Name.className != "inputBox red")
									chargeNumberPostionActualAssociations += "2,"; 
								if(chargeNumber3Exists == 'Y' && chargeNumber3Name.value != '' && chargeNumber3Name.className != "inputBox red")
									chargeNumberPostionActualAssociations += "3,"; 
								if(chargeNumber4Exists == 'Y' && chargeNumber4Name.value != '' && chargeNumber4Name.className != "inputBox red")
									chargeNumberPostionActualAssociations += "4,"; 	
								return chargeNumberPostionActualAssociations;
								break;
							case 'chargeNumber2Name':
								if(chargeNumber1Exists == 'Y' && chargeNumber1Name.value != '' && chargeNumber1Name.className != "inputBox red")
									chargeNumberPostionActualAssociations += "1,"; 
								if(chargeNumber3Exists == 'Y' && chargeNumber3Name.value != '' && chargeNumber3Name.className != "inputBox red")
									chargeNumberPostionActualAssociations += "3,"; 
								if(chargeNumber4Exists == 'Y' && chargeNumber4Name.value != '' && chargeNumber4Name.className != "inputBox red")
									chargeNumberPostionActualAssociations += "4,"; 	
								return chargeNumberPostionActualAssociations;
								break;
							case 'chargeNumber3Name':
								if(chargeNumber1Exists == 'Y' && chargeNumber1Name.value != '' && chargeNumber1Name.className != "inputBox red")
									chargeNumberPostionActualAssociations += "1,"; 
								if(chargeNumber2Exists == 'Y' && chargeNumber2Name.value != '' && chargeNumber2Name.className != "inputBox red")
									chargeNumberPostionActualAssociations += + "2,"; 
								if(chargeNumber4Exists == 'Y' && chargeNumber4Name.value != '' && chargeNumber4Name.className != "inputBox red")
									chargeNumberPostionActualAssociations += "4,"; 	
								return chargeNumberPostionActualAssociations;
								break;
							case 'chargeNumber4Name':
								if(chargeNumber1Exists == 'Y' && chargeNumber1Name.value != '' && chargeNumber1Name.className != "inputBox red")
									chargeNumberPostionActualAssociations += "1,"; 
								if(chargeNumber2Exists == 'Y' && chargeNumber2Name.value != '' && chargeNumber2Name.className != "inputBox red")
									chargeNumberPostionActualAssociations += "2,"; 
								if(chargeNumber3Exists == 'Y' && chargeNumber3Name.value != '' && chargeNumber3Name.className != "inputBox red")
									chargeAssociations += "3,"; 	
								return chargeNumberPostionActualAssociations;
								break;
							default:
								return '';
							}
				  			
				  		}
						},
						width: 200,
						max: 10,  // default value is 10
						cacheLength:0, // disable cache here because we put "rownum < max" for better performance. Cache will make data off.
						scrollHeight: 200,
						formatItem: function(data, i, n, value) {
							return  value.split(":")[1].substring(0,40);
						},
						formatResult: function(data, value) {
							return value.split(":")[1];
						}
				});
				
				j$("#" + el1 + rowId).bind('keyup',(function(e) {
					  var keyCode = (e.keyCode ? e.keyCode : e.which);
		
					  if( keyCode != 13 && keyCode != 9) // 13 is for Enter; 9 is for Tab
					  	invalidateRequestor(rowId, el1, el2);
				}));
				
				
				j$("#" + el1 + rowId).result(log).next().click(function() {
					j$(this).prev().search();
				});
				
			}); 
}

//cab be used to initialized grid for result frame onload.
function myResultOnLoadWithGrid(gridConfig)
{
 try{
 if (!showUpdateLinks) /*Dont show any update links if the user does not have permissions*/
 {
  parent.document.getElementById("updateResultLink").style["display"] = "none";
 }
 else
 {
  parent.document.getElementById("updateResultLink").style["display"] = "";
 }
 }
 catch(ex)
 {}

 initGridWithGlobal(gridConfig);

 /*This dislpays our standard footer message*/
 displayGridSearchDuration();
 var t = $('totalLines');
 if(t.value == 0)
	 t.value = 1;
 /*It is important to call this after all the divs have been turned on or off.*/
 setResultFrameSize();

}

function onRightClick(row)
{
	if(showUpdateLinks)
	{
		selectedRowId = row;
		if(cellValue(row,'newRow') == 'Y')
			toggleContextMenu('rightClickMenuRemove');
		else
			toggleContextMenu('rightClickMenuUpdateRemove');
	}
}

function removeLine()
{
	if(cellValue(selectedRowId,'newRow') == 'Y')
		mygrid.deleteRow(selectedRowId);
	else
		{
		    var url = "chargenumbermanagementresults.do?uAction=delete&callBack=chargeNumberRemoveLine&acctSysApproverId=" + cellValue(selectedRowId,'acctSysApproverId');
			callToServer(url);
		}
}

function chargeNumberRemoveLine(callbackError)
{
	if(callbackError == undefined)
		mygrid.deleteRow(selectedRowId);
	else 
		alert(messagesData.updateError);
}

function updateLineAppovers()
{
	var rowId = selectedRowId;
	var oldRowPostion = mygrid.getRowIndex(rowId);
	var newData = new Array();
	var cntr = 0;
	newData[cntr++] = 'N'; // Permission
	newData[cntr++] = 'N'; // Permission
	newData[cntr++] = 'N'; // Permission
	newData[cntr++] = 'N'; // Permission
	newData[cntr++] = 'N'; // Permission
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'Y'; // Permission
	
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'Y'; // Permission
	newData[cntr++] = 'Y'; // Permission
	
	
	if($v('chargeNumber1Exists') == 'Y')
	{
		newData[cntr++] = cellValue(rowId,'chargeNumber1Name');
		newData[cntr++] = cellValue(rowId,'chargeNumber1');
		newData[cntr++] = 'charge_number_1';
	}
	if($v('chargeNumber2Exists') == 'Y')
	{
		newData[cntr++] = cellValue(rowId,'chargeNumber2Name');
		newData[cntr++] = cellValue(rowId,'chargeNumber2');
		if($v('chargeNumber1Exists') == 'Y')
			newData[cntr++] = 'charge_number_2';
		else
			newData[cntr++] = 'charge_number_1';
	}
	if($v('chargeNumber3Exists') == 'Y')
	{
		newData[cntr++] = cellValue(rowId,'chargeNumber3Name');
		newData[cntr++] = cellValue(rowId,'chargeNumber3');
		if($v('chargeNumber1Exists') == 'Y' && $v('chargeNumber2Exists') == 'Y')
			newData[cntr++] = 'charge_number_3';
		else if($v('chargeNumber1Exists') == 'Y' || $v('chargeNumber2Exists') == 'Y')
			newData[cntr++] = 'charge_number_2';
		else
			newData[cntr++] = 'charge_number_1';
	}
	if($v('chargeNumber4Exists') == 'Y')
	{
		newData[cntr++] = cellValue(rowId,'chargeNumber4Name');
		newData[cntr++] = cellValue(rowId,'chargeNumber4');
		if($v('chargeNumber1Exists') == 'Y' && $v('chargeNumber2Exists') == 'Y' &&  $v('chargeNumber3Exists') == 'Y')
			newData[cntr++] = 'charge_number_4';
		else if(($v('chargeNumber1Exists') == 'Y' && $v('chargeNumber2Exists') == 'Y') || ($v('chargeNumber1Exists') == 'Y' && $v('chargeNumber3Exists') == 'Y') || ($v('chargeNumber2Exists') == 'Y' && $v('chargeNumber3Exists') == 'Y'))
			newData[cntr++] = 'charge_number_3';
		else if($v('chargeNumber1Exists') == 'Y' || $v('chargeNumber2Exists') == 'Y' || $v('chargeNumber3Exists') == 'Y')
			newData[cntr++] = 'charge_number_2';
		else
			newData[cntr++] = 'charge_number_1';
	}
	var active = cellValue(rowId,'active');
	if(active == 'true')
		newData[cntr++] = true; // active
	else
		newData[cntr++] = false; // active
	
	var approveByPrice = $('approveByPrice').value;
	
	// start approver name, approver id
	if(approveByPrice == 'Y')
	{
		var approvalLevel1Limit = cellValue(rowId,'approvalLevel1Limit');
		if(approvalLevel1Limit != '&nbsp;')
			newData[cntr++] = approvalLevel1Limit;
		else
			newData[cntr++] = '';
		newData[cntr++] = cellValue(rowId,'approvalLimitCurrencyId'); //currency
	}
	var noLevel1ChgAcctApprover = $('noLevel1ChgAcctApprover').value;
	if(noLevel1ChgAcctApprover == '2')
	{
		var approvalLevel1Name1 = cellValue(rowId,'approvalLevel1Name1');
		if(approvalLevel1Name1 != '&nbsp;')
			newData[cntr++] = approvalLevel1Name1;
		else
			newData[cntr++] = '';
		var approvalLevel1Name2 = cellValue(rowId,'approvalLevel1Name2');
		if(approvalLevel1Name2 != '&nbsp;')
			newData[cntr++] = approvalLevel1Name2;
		else
			newData[cntr++] = '';
		newData[cntr++] = cellValue(rowId,'approvalLevel1ApproverId1');
		newData[cntr++] = cellValue(rowId,'approvalLevel1ApproverId2');
	}
	else
	{
		var approvalLevel1Name1 = cellValue(rowId,'approvalLevel1Name1');
		if(approvalLevel1Name1 != '&nbsp;')
			newData[cntr++] = approvalLevel1Name1;
		else
			newData[cntr++] = '';
		newData[cntr++] = cellValue(rowId,'approvalLevel1ApproverId1');
	}
	
	var noLevel2ChgAcctApprover = $('noLevel2ChgAcctApprover').value;
	if(approveByPrice == 'Y' && noLevel2ChgAcctApprover != '0')
		{
			var approvalLevel2Limit = cellValue(rowId,'approvalLevel2Limit');
			if(approvalLevel2Limit != '&nbsp;')
				newData[cntr++] = approvalLevel2Limit;
			else
				newData[cntr++] = '';
		}
	if(noLevel2ChgAcctApprover == '2')
	{
		var approvalLevel2Name1 = cellValue(rowId,'approvalLevel2Name1');
		if(approvalLevel2Name1 != '&nbsp;')
			newData[cntr++] = approvalLevel2Name1;
		else
			newData[cntr++] = '';
		var approvalLevel2Name2 = cellValue(rowId,'approvalLevel2Name2');
		if(approvalLevel2Name2 != '&nbsp;')
			newData[cntr++] = approvalLevel2Name2;
		else
			newData[cntr++] = '';
		newData[cntr++] = cellValue(rowId,'approvalLevel2ApproverId1');
		newData[cntr++] = cellValue(rowId,'approvalLevel2ApproverId2');
	}
	else if(noLevel2ChgAcctApprover == '1')
	{
		var approvalLevel2Name1 = cellValue(rowId,'approvalLevel2Name1');
		if(approvalLevel2Name1 != '&nbsp;')
			newData[cntr++] = approvalLevel2Name1;
		else
			newData[cntr++] = '';
		newData[cntr++] = cellValue(rowId,'approvalLevel2ApproverId1');
	}

	var noLevel3ChgAcctApprover = $('noLevel3ChgAcctApprover').value;
	if(approveByPrice == 'Y' && noLevel3ChgAcctApprover != '0')
		{
			var approvalLevel3Limit = cellValue(rowId,'approvalLevel3Limit');
			if(approvalLevel3Limit != '&nbsp;')
				newData[cntr++] = approvalLevel3Limit;
			else
				newData[cntr++] = '';
		}
	if(noLevel3ChgAcctApprover == '2')
	{
		var approvalLevel3Name1 = cellValue(rowId,'approvalLevel3Name1');
		if(approvalLevel3Name1 != '&nbsp;')
			newData[cntr++] = approvalLevel3Name1;
		else
			newData[cntr++] = '';
		var approvalLevel3Name2 = cellValue(rowId,'approvalLevel3Name2');
		if(approvalLevel3Name2 != '&nbsp;')
			newData[cntr++] = approvalLevel3Name2;
		else
			newData[cntr++] = '';
		newData[cntr++] = cellValue(rowId,'approvalLevel3ApproverId1');
		newData[cntr++] = cellValue(rowId,'approvalLevel3ApproverId2');
	}
	else if(noLevel3ChgAcctApprover == '1')
	{
		var approvalLevel3Name1 = cellValue(rowId,'approvalLevel3Name1');
		if(approvalLevel3Name1 != '&nbsp;')
			newData[cntr++] = approvalLevel3Name1;
		else
			newData[cntr++] = '';
		newData[cntr++] = cellValue(rowId,'approvalLevel3ApproverId1');
	}
	
	var noLevel4ChgAcctApprover = $('noLevel4ChgAcctApprover').value;
	if(approveByPrice == 'Y' && noLevel4ChgAcctApprover != '0')
	{
		var approvalLevel4Limit = cellValue(rowId,'approvalLevel4Limit');
		if(approvalLevel4Limit != '&nbsp;')
			newData[cntr++] = approvalLevel4Limit;
		else
			newData[cntr++] = '';
	}
	if(noLevel4ChgAcctApprover == '2')
	{
		var approvalLevel4Name1 = cellValue(rowId,'approvalLevel4Name1');
		if(approvalLevel4Name1 != '&nbsp;')
			newData[cntr++] = approvalLevel4Name1;
		else
			newData[cntr++] = '';
		var approvalLevel4Name2 = cellValue(rowId,'approvalLevel4Name2');
		if(approvalLevel4Name2 != '&nbsp;')
			newData[cntr++] = approvalLevel4Name2;
		else
			newData[cntr++] = '';
		newData[cntr++] = cellValue(rowId,'approvalLevel4ApproverId1');
		newData[cntr++] = cellValue(rowId,'approvalLevel4ApproverId2');
	}
	else if(noLevel4ChgAcctApprover == '1')
	{
		var approvalLevel4Name1 = cellValue(rowId,'approvalLevel4Name1');
		if(approvalLevel4Name1 != '&nbsp;')
			newData[cntr++] = approvalLevel4Name1;
		else
			newData[cntr++] = '';
		newData[cntr++] = cellValue(rowId,'approvalLevel4ApproverId1');
	}

	newData[cntr++] = cellValue(rowId,'oldActive'); // oldActive
	newData[cntr++] = 'N'; // newRow
	newData[cntr++] =  true; // updated
	newData[cntr++] =  $v('accountSysId'); // accountSysId
	newData[cntr++] =  $v('chargeType'); // chargeType
	newData[cntr++] =  $v('chargeId'); // chargeType
	newData[cntr++] =  $v('chargeId1'); // chargeType
	newData[cntr++] =  $v('chargeId2'); // chargeType
	newData[cntr++] =  $v('chargeId3'); // chargeType
	newData[cntr++] =  $v('chargeId4'); // chargeType
	newData[cntr++] = cellValue(rowId,'acctSysApproverId');
	
	mygrid.deleteRow(rowId);
	mygrid.addRow(rowId, newData, oldRowPostion);
	mygrid.selectRowById(rowId);	
	
	apprBind(rowId,'approvalLevel1Name1','approvalLevel1ApproverId1');
	if(noLevel1ChgAcctApprover == '2')
		apprBind(rowId,'approvalLevel1Name2','approvalLevel1ApproverId2');
	if(noLevel2ChgAcctApprover == '2')
	{
		apprBind(rowId,'approvalLevel2Name1','approvalLevel2ApproverId1');
		apprBind(rowId,'approvalLevel2Name2','approvalLevel2ApproverId2');
	}
	else if(noLevel2ChgAcctApprover == '1')
		apprBind(rowId,'approvalLevel2Name1','approvalLevel2ApproverId1');
	if(noLevel3ChgAcctApprover == '2')
	{
		apprBind(rowId,'approvalLevel3Name1','approvalLevel3ApproverId1');
		apprBind(rowId,'approvalLevel3Name2','approvalLevel3ApproverId2');
	}
	else if(noLevel3ChgAcctApprover == '1')
		apprBind(rowId,'approvalLevel3Name1','approvalLevel3ApproverId1');
	if(noLevel4ChgAcctApprover == '2')
	{
		apprBind(rowId,'approvalLevel4Name1','approvalLevel4ApproverId1');
		apprBind(rowId,'approvalLevel4Name2','approvalLevel4ApproverId2');
	}
	else if(noLevel4ChgAcctApprover == '1')
		apprBind(rowId,'approvalLevel4Name1','approvalLevel4ApproverId1');
	oneEdit = true;
}

