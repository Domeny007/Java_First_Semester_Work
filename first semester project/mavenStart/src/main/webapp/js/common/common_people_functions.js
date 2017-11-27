var id = '';
var name = '';

var lastChecked = null;

var country = null;

window.onload = initialize;

function initialize() {

    changeDocHeight();

    $("#countryFilter").css("display","none");


}

function changeDocHeight() {
    var height = $('#menuDiv').outerHeight(true) + $("#allForms").outerHeight(true);
    document.documentElement.style.height = height;
}



$(document).on("click", ".checkbox", function() {
    name = '';
    id = '';
    if(lastChecked !== null){
        lastChecked.prop('checked',false);
        $('#addList').text(name);
        lastChecked = null;
        country = null;
    }
    $('.checkbox').each(function(){
        if($(this).is(':checked')){
            lastChecked = $(this);
            name = $(this).prop('name');
            id = $(this).prop('value');
            country = $('#' + id);
            $('#addList').text(name);
            if(country !== null){
                $('#countryName').val(country.val());
            }
            return true;
        }
    });
});

$(document).on("submit", "#searchForm", function() {
    $('#idList').val(id);
    $('#namesList').val(name);

});
