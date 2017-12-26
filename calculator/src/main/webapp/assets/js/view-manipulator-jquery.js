

"use strict";

$(function() {
	
	function clearTypedText() {
		document.getElementById("input-to-validate").value = "";
	}
	
	//Не дает формам отправлятся по enter
	$("form").keydown(function(event){
		if(event.keyCode == 13) {
			event.preventDefault();
			return false;
		}
	});
	
	$.validator.addMethod(
	        "regex",
	        function(value, element, regexp) {
	            var re = new RegExp(regexp);
	            return this.optional(element) || re.test(value);
	        }
	);
	
	$("#calc-form").validate({
		rules: {
			digit: { required: true, regex: "^-?[0-9]+.?[0-9]*$" },
			mathfunction: { required: true, regex: "^[+-\*\/=]$" }
		},
		
		messages: {
			digit: { required: "The number is required.", regex: "It is not a number!" },
			mathfunction: { required: "The operation is required.", regex: "Operations must be: +, -, *, /." }
		}, 
		
		submitHandler: function(form) {
		    $("#calc-form").submit();
		    clearTypedText();
		}
	});
});