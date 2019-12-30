windowCloseOnEsc = true;

function resultOnLoad()
{
	totalLines = document.getElementById("totalLines").value;
	
	/*This dislpays our standard footer message*/
	displayNoSearchSecDuration();
	/*It is important to call this after all the divs have been turned on or off.*/
	setResultSize();
}

