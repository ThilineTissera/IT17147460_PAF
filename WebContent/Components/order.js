$(document).ready(function(){
	$("#alertSuccess").hide();
	$("#alertError").hide();
}); 

// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {
	
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	
	// Form validation-------------------
	var status = validateOrderForm();
	if(status != true)
		{
		$("#alertError").text(status);
		$("#alertError").show();
		return;
		}
	
// If valid------------------------
	
	//$("#formOrder").submit
	var type = ($("#hidOrderIDSave").val() == "") ? "POST" : "PUT";

	$.ajax({
		url : "orderAPI",
		type : type,
		data : $("#formOrder").serialize(),
		dataType : "text",
		complete : function(response, status) 
		{
			onOrderSaveCompelet(response.responseText, status);
		}
	});
});
	
	function onOrderSaveCompelet(response, status) {
		if (status == "success") 
		{
			var resultSet = JSON.parse(response);
			
			if (resultSet.status.trim() == "success") 
			{
				$("#alertSuccess").text("Successfully saved.");
				$("#alertSuccess").show();
				
				$("#divOrderGrid").html(resultSet.data);
				
			} else if (resultSet.status.trim() == "error") {
				
				$("#alertError").text(resultSet.data);
				$("#alertError").show();
			}
		} else if (status == "error") {
			$("#alertError").text("Error while saving.");
			$("#alertError").show();
		} else {
			$("#alertError").text("Unknown error while saving..");
			$("#alertError").show();
		}
		$("#hidOrderIDSave").val("");
		$("#formOrder")[0].reset();
	}
	



//UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event) 
		{
			$("#hidOrderIDSave").val($(this).closest("tr").find('#hidOrderIDUpdate').val());
			$("#orderName").val($(this).closest("tr").find('td:eq(0)').text());
			$("#orderQuantity").val($(this).closest("tr").find('td:eq(1)').text());
			$("#orderUnit").val($(this).closest("tr").find('td:eq(2)').text());
			$("#orderDate").val($(this).closest("tr").find('td:eq(3)').text());
		});


//remove
$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "orderAPI",
		type : "DELETE",
		data : "orderId=" + $(this).data("orderid"),
		dataType : "text",
		complete : function(response, status) 
		{
			onOrderDeleteComplete(response.responseText, status);
		}
	});
});


function onOrderDeleteComplete(response, status) {
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
		
		if (resultSet.status.trim() == "success") 
		{
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			
			$("#divOrderGrid").html(resultSet.data);
		
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}

//CLIENTMODEL=========================================================================
function validateOrderForm() {

	if ($("#orderName").val().trim() == "") {
		return "Insert Order Name.";
	}

	if ($("#orderQuantity").val().trim() == "") {
		return "Insert Order Address.";
	}

	if ($("#orderUnit").val().trim() == "") {
		return "Insert contact.";
	}

	if ($("#orderDate").val().trim() == "") {
		return "Insert date.";
	}


	
	return true;
}


