var submitcount=0;
var shipupdcount=0;
function submitheform()
{
    if (submitcount == 0)
    {
        submitcount++;
        document.puchoutiproc.submit();
        return true;
    }
    else
    {
        alert("This form has already been submitted.\nThanks.");
        return false;
    }
}
