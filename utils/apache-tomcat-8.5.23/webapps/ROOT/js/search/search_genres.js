$(document).on("keyup", "#searchInput", function() {
    var queryGenre = document.getElementById('searchInput').value;

    queryGenre = queryGenre.trim();
    if(queryGenre !== "") {
        var url = "/searchGenres?genre=" + queryGenre;

        $.get(url, function (responseXml) {
            $("#dataDiv").html($(responseXml).find("data").html());
            changeDocHeight();
        });
    }
});

