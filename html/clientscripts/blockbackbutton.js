function blockCtrlN()
{
    Key = window.event.keyCode;
    if (Key != 0)
    {
      //alert("Key pressed! ASCII-value: " + Key);
      if (Key == 78 && ctrlpressed)
     {
		alert("Please do not use Ctrl+N to open a new window.\n\nTo open another window please click on the 'e' icon on your desktop.");
      ctrlpressed = false;
     }
    }

    if (event.ctrlLeft)
    {
      //alert("Left CTRL Pressed");
      ctrlpressed = true;
    }
    else
    {
      if (event.ctrlKey)
      {
        //alert("Right CTRL Pressed");
        ctrlpressed = true;
      }
      else
      {
	     ctrlpressed = false;
      }
    }

    document.body.focus();
}
