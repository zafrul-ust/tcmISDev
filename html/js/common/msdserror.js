String.prototype.trim = function()
{
// skip leading and trailing whitespace
// and return everything in between
  return this.replace(/^\s*(\b.*\b|)\s*$/, "$1");
}

function checkName()
{
   var name  =  document.getElementById("name");
   var phone  =  document.getElementById("phone");

	 if (name.value.trim() == 0 )
	 {
	  alert("Please provide a Name and Phone Number to facilitate communication if need be.\n\nThanks.");
	  return false;
	 }
   else if (phone.value.trim() == 0 )
   {
    alert("Please provide a Name and Phone Number to facilitate communication if need be.\n\nThanks.");
    return false;
   }
	 else
   {
    return true;
   }
}