var myJasonArr = new Array();
var hubId;

function submitSearchForm()
{
	/*Make sure to not set the target of the form to anything other than resultFrame*/
	document.genericForm.target='resultFrame';
	document.getElementById("action").value = 'search'; 
	//set start search time
	var now = new Date();
	var startSearchTime = document.getElementById("startSearchTime");
	startSearchTime.value = now.getTime();
	var flag = validateForm();
	if(flag) {
		showPleaseWait();
		return true;
	}
	else
	{
		return false;
	}
}



function generateExcel() {
	var flag = validateForm();
	if(flag) {
		var action = document.getElementById("action");
		action.value = 'createExcel';
		openWinGenericViewFile('/tcmIS/common/loadingfile.jsp','_nWeekInventoryGenerateExcel','650','600','yes');
		document.genericForm.target='_nWeekInventoryGenerateExcel';
		var a = window.setTimeout("document.genericForm.submit();",500);
	}
}

function validateForm()
{
	// validates the age input field	
	return true;
}


/*
function setPicklist()
{
	setOption(0,messagesData.all,"", "picklistId");	
}

function picklistUpdateAjaxCall()
{

	var hubVal = document.getElementById("hub").value;
	var valueExists = false; 
	if(typeof(hubVal)!='undefined')
	{	
		resetDropDown();

		if(hubVal!="")
		{	  
			hubId = hubVal;			
			for (var i = 0; i < myJasonArr.length; i++) 
			{
				if( myJasonArr[i].id == hubId)
				{
					updatePickListDropDown(myJasonArr[i].arr);
					valueExists = true;
					break;
				} 	  
			}

			if(!valueExists)
			{	   
				callToServer("openpicksmain.do?action=getPickList&hub="+hubVal);
			}

		}		
	}
}


function attachPicklistUpdate()
{
	defaults.hub.callback = picklistUpdateAjaxCall;
}


function resetDropDown()
{
	var dropDown  = $("picklistId");
	for (var i = dropDown.length; i > 0;i--)
	{	  
		dropDown[i] = null;
	}	  
	setPicklist();

}


function updatePickListDropDown(jsonObj)
{
	var tmpJasonArray	
	var dontAddVal = false;
	if(jsonObj.length>0)
	{	 
		if(typeof(tmpJasonArray)!='undefined')
		{	
			for (var i = 0; i < tmpJasonArray.length; i++)
			{	
				if(tmpJasonArray[i].id == hubId)	
				{
					dontAddVal = true;
				}	
			}

			if(!dontAddVal)
			{	
				tmpJasonArray[tmpJasonArray.length]={id:hubId,arr:jsonObj};
				tmpJasonArray.length++;
			}
		}	
		else
		{			
			tmpJasonArray= new Array({id: hubId, arr: jsonObj});

		}

		myJasonArr = tmpJasonArray;
	}

	for (var i = 0; i < jsonObj.length; i++) 
	{	
		setOption(i+1,jsonObj[i].picklistPrintDate,jsonObj[i].picklistId,"picklistId");		  
	}
}*/