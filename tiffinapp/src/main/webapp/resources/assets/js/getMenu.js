function getMenu(mealId,title,mealType) {
	$.ajax({
        	type : "POST",
            url : 'getMenu',
            dataType: 'json',
            data: "mealId=" + mealId + "&mealType=" + mealType,
            success : function(data) {
                //$('#result').html(data);
                if(data == null) {
                	$('#menuDate').text('Menu not available yet..');
                	$('#menuMainItem').text('');
                    $('#menuSubItem1').text('');
                    $('#menuSubItem2').text('');
                    $('#menuSubItem3').text('');
                    $('#menuSubItem4').text('');
                    $('#myModalLabel').text(title);
                    $("#menuModal").modal('show');
                	return;
                }
                $('#menuDate').text('Menu for :' + data.date.substring(0,12));
              	$('#menuMainItem').text(data.mainItem);
                $('#menuSubItem1').text(data.subItem1);
                $('#menuSubItem2').text(data.subItem2);
                $('#menuSubItem3').text(data.subItem3);
                $('#menuSubItem4').text(data.subItem4);
                $('#myModalLabel').text(title);
                $("#menuModal").modal('show');
            },
            error: function(e){
            	alert('Error: ' + e);
        	}
        });
}