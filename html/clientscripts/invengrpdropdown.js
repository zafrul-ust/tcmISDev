
function hubchanged(object)
{
	var artist;
   artist = object.options[object.selectedIndex].value;

   var selectedIndex = object.selectedIndex;

	for (var i = document.receiving.invengrp.length; i > 0;i--)
   {
      document.receiving.invengrp.options[i] = null;
   }

  if (artist != "All")
  {
  showinvlinks(artist);
	window.document.receiving.invengrp.selectedIndex = 0;
	var isAutoPutHub = autoPutHub[artist];
	if(isAutoPutHub)
		$('genbutton1').style.display = "none";
	else
		$('genbutton1').style.display = "";
  }
  else
  {
    setCab(0,"All","All");
    $('genbutton1').style.display = "none";
  }
}

function showinvlinks(selectedIndex)
{
    var invgrpid = new Array();
    invgrpid = altinvid[selectedIndex];

	 var invgrpName = new Array();
    invgrpName = altinvName[selectedIndex];

    for (var i=0; i < invgrpid.length; i++)
    {
        setCab(i,invgrpName[i],invgrpid[i])
    }
}

function setCab(href,text,id)
{
    var optionName = new Option(text, id, false, false)
    document.receiving.invengrp.options[href] = optionName;
}