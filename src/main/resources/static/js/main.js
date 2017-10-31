var dataset1 = null;
$(document).ready(function() {
	$.ajax({
		type : 'post',
		url : "/getUserinfo",
		data : {},
		beforeSend : function(XMLHttpRequest) {

		},
		success : function(data, status) {
			dataset1 = data;
			   $('#example').DataTable( {
				        data: dataset1,
				        columns: [
				            { "data":"userid" },
				            { "data": "username" },
				            { "data": "account" },
				            { "data": "address" },
				            { "data": "time" },
							{ "data": "state" }
				        ]
				    } );
		}
	}); 
 
} );