var visinfo = new Object();
visinfo['display'] = new Array("", "none");

var submitcount=0;
var updatecount=0;
function SubmitOnlyOnce()
{
    if (submitcount == 0)
    {
        submitcount++;
        return true;
    }
    else
    {
        alert("This form has already been submitted.  Thanks!");
        return false;
    }
}
function openWinGeneric(destination,WinName,WinWidth, WinHeight, Resizable )
{
    windowprops = "toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,status=yes,top=200,left=200,width=" + WinWidth + ",height=" + WinHeight+",resizable=" + Resizable;
    preview = window.open(destination, WinName,windowprops);
}

function togglePage(tagid, title)
{
    for (var i=0; i<cmds.length; i++)
    {
        var tag = document.all(cmds[i]+"_tabchild");

        if (cmds[i] == tagid)
        {
            setVisible(cmds[i], true)
            tag.className = "tab_selected_tab";            
        }
        else
        {
            setVisible(cmds[i], false);
		tag.className = "tab_tab";            
        }
    }
}

function eraseBlank(ref, alternate)
{
    if (ref == null || ref == "")
    {
        if (alternate == null)
            return "";
        else
            return alternate;
    }
    else
        return ref;
}

function setVisible(tagid, yesno)
{
    try
    {
        displaystyle = "display";
        var target = document.all.item(tagid);
        var x = (yesno) ? 0 : 1;
        target.style[displaystyle] = visinfo[displaystyle][x];
    }
    catch (ex)
    {
    }
}

function initTabs()
{
    document.all.item("Component1").style["display"] = "";
}

function cancel()
{
    window.close();
}