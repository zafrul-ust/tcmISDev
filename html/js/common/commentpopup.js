(function($G, $) {
	'use strict';
	$G.commentPopup = $G.commentPopup || {
		submitComment: function() {
			var commentBox = $("#comment");
			var text = commentBox.val();
			if (text.length == 0) {
				msg = commentBox.siblings("label").html();
				alert(msg);
			}
			else {
				var textLimit = $("#limit").val();
				if (text.length > textLimit) {
					text = text.substring(0, textLimit);
				}
				$G.popupService.closePopup({"comment": text});
			}
		}
	};
	
	$(function() {
		$("#cancel").on("click", function() {
			window.close();
		});
		$("#submit").on("click", function() {
			$G.commentPopup.submitComment();
		});
		$("#comment").on("keydown", function(event) {
			if ($(this).val().length >= $("#limit").val()) {
				var key = event.which || event.keyCode || event.charCode;
				if ( ! (key == 8 || key == 46 || (key >= 37 && key <= 40) || (key >= 16 && key <= 18))) {
					event.preventDefault();
				}
			}
		});
	});
	
}(this, jQuery));