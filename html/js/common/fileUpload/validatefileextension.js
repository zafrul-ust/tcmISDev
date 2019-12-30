var validUploadFileExtension = ['png', 'jpg', 'ipeg', 'gif', 'bmp', 'txt', 'pdf', 'csv', 'xlsx', 'xls'];
// This is to validate the file type allowed for uploading
function validateFileExtension(elementId) { 
	// Default the element Id to theFile if elementId is not passed
	if(elementId == null) elementId = 'theFile'; 
	
	if(document.getElementById(elementId) != null) { 
		var extension = document.getElementById(elementId).value.trim().split('.').pop().toLowerCase();
		if (extension.length == 0) { 
			return true;
		} else if(validUploadFileExtension.indexOf(extension) > -1) { 
			return true;
		} else { 
			alert(messagesData.filetypenotallowed);
		}
	} else {
	//	alert("Element does not exist.");
		return true; // If theFile doesn't exist, don't validate
	}
	
	return false;
	
}

// This is for older version of IE
// http://stackoverflow.com/questions/2790001/fixing-javascript-array-functions-in-internet-explorer-indexof-foreach-etc
if (!Array.prototype.indexOf)
{
	Array.prototype.indexOf= function(find, i /*opt*/) {
        if (i===undefined) i= 0;
        if (i<0) i+= this.length;
        if (i<0) i= 0;
        for (var n= this.length; i<n; i++)
            if (i in this && this[i]===find)
                return i;
        return -1;
    };
}