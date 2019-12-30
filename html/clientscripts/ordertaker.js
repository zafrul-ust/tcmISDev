String.prototype.trim = function()
{
// skip leading and trailing whitespace
// and return everything in between
  return this.replace(/^\s*(\b.*\b|)\s*$/, "$1");
}

function checkContactvalues()
{
    var finalMsgt;

    var result ;
    var allClear = 0;


	 var contacttype  =  document.getElementById("contacttype");
	 var nickname  =  document.getElementById("nickname");
	 var firstname  =  document.getElementById("firstname");
	 var lastname  =  document.getElementById("lastname");

	 if (contacttype.value == "Main")
	 {

	 }
	 else
	 {
		if (nickname.value.trim().length == 0 && firstname.value.trim().length == 0 && lastname.value.trim().length == 0)
		{
			allClear = 1;
			finalMsgt = "You Must Enter Atleast One Value for First Name, Last Name or Nick Name.\n\n";
		}
      else if (nickname.value.trim().length == 0 )
		{
			allClear = 1;
			finalMsgt = "Please Enter a Value for Nick Name.\n\n";
		}
	 }
    if (allClear < 1)
    {
        result = true;
        contacttype.disabled = false;
    }
    else
    {
        alert(finalMsgt);
        result = false;
    }

    return result;
}