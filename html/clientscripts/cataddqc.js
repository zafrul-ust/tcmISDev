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
        alert("This form has already been submitted.\n Please Wait for Results.\n Thanks!");
        return false;
    }
}

function openWinGeneric(destination,WinName,WinWidth, WinHeight, Resizable )
{
    windowprops = "toolbar=no,location=no,directories=no,menubar=no,scrollbars=yes,status=no,top=200,left=200,width=" + WinWidth + ",height=" + WinHeight+",resizable=" + Resizable;
    preview = window.open(destination, WinName,windowprops);
}

function showvalue(object)
{
    artist = object.options[object.selectedIndex].text;
    window.document.companyform.CatDescription.value = artist;
}
function reshow(object)
{
    window.document.companyform.CatDescription.value = "ALL";
    artist = object.options[object.selectedIndex].text;
    var indexselectec = object.options[object.selectedIndex]
        var indexofxompany = object.options[object.selectedIndex].value;
    for (var i = document.companyform.catalogs.length;i > 0;i--)
    {
        document.companyform.catalogs.options[i] = null;
    }
    reloading = true;
    showlinks(indexofxompany);
    window.document.companyform.catalogs.selectedIndex = 0;
}
function showlinks(selectedIndex)
{
    var companytoshow = companynames[selectedIndex];
    var nickNameValue = new Array();
    nickNameValue = altName[companytoshow];
    for (var i=0; i < nickNameValue.length; i++)
    {
        opt(i,nickNameValue[i])
    }
}
function opt(href,text)
{
    if (reloading)
    {
        var optionName = new Option(text, href, false, false)
        document.companyform.catalogs.options[href] = optionName;
    }
    else
        document.write('<OPTION VALUE="',text,'">',text,'<\/OPTION>');
}

function initTabs()
{
    document.all.item("Component1").style["display"] = "";
}


function togglePage(tagid, title)
{
    for (var i=0; i<cmds.length; i++)
    {
        var tag = document.all(cmds[i]+"_tabchild");

        if (cmds[i] == tagid)
        {
            setVisible(cmds[i], true)
            eval(compForm2  =  "window.document.ComponentForm" + (i+1).toString() + ".material_id" +i.toString() );
            ( curval2 =  (eval(compForm2.toString()).value ) );
            if (curval2.length < 1)
            {
                tag.className = "tab_nomatID_selected"
            }
            else
            {
                tag.className = "tab_selected_tab";
            }

        }
        else
        {
            setVisible(cmds[i], false);

            eval(compForm2  =  "window.document.ComponentForm" + (i+1).toString() + ".material_id" +i.toString() );
            ( curval2 =  (eval(compForm2.toString()).value ) );
            if (curval2.length < 1)
            {
                tag.className = "tab_nomatID"
            }
            else
            {
                tag.className = "tab_tab";
            }

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

function getitemID(entered)
{
    loc = window.document.MainForm.item_category.value;
    alert(loc);
}

function resetApplicationTimer()
{
    try
    {
     parent.resetTimer();
    }
    catch(exap){
    }
}