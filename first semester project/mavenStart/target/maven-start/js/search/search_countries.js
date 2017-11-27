$(document).on("keyup", "#searchInput", function() {
    var queryCountry = document.getElementById('searchInput').value;
    queryCountry = queryCountry.trim();
    if(queryCountry !== "") {
        var url = "/searchCountries?country=" + queryCountry;

        $.get(url, function (responseXml) {
            $("#dataDiv").html($(responseXml).find("data").html());
            changeDocHeight();
        });
    }
});
