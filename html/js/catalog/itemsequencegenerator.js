
function generateSequence() {
	if ( ! valid()) {
		alert("Must enter a positive number no greater than 100");
	}
	else {
		var list = document.getElementById("dataContent");

		while (list.hasChildNodes()) {
		    list.removeChild(list.firstChild);
		}
		
		var searchLike = $("searchLike");
		j$.ajax({
			url:"itemsequencegenerator.do",
			cache:false,
			data:{uAction: "generate", howMany:$v("howMany"), searchLike:searchLike.value},
			success: function(data) {
				var itemColl = j$.parseJSON(data);
				$("boxhead").innerHTML = searchLike.options[searchLike.selectedIndex].innerHTML;
				for (i in itemColl) {
					var itemDiv = document.createElement("DIV");
					itemDiv.className = "optionTitleBold";
					itemDiv.style.borderBottom = "1px solid gray";
					itemDiv.style.textAlign = "center";
					itemDiv.style.padding = "5px 0 5px 0";
					itemDiv.innerHTML = itemColl[i];
					list.appendChild(itemDiv);
				}
				j$("#resultGridDiv").show();
				
				resizeResults();
			},
			error: function(xhr, status, error) {
				alert(error);
			}
		});
	}
}

function valid() {
	var valid = false;
	try {
		var howMany = $v("howMany");
		if (howMany <= 100 &&
				howMany > 0) {
			valid = true;
		}
	}
	catch(e) {}
	return valid;
}

function resizeResults() {
	var ht = window.innerHeight
		|| document.documentElement.clientHeight
		|| document.body.clientHeight;
	
	var resultHeight = ht-$("dataContent").offsetTop-15;
	$("dataContent").style.height = resultHeight+"px";
}
