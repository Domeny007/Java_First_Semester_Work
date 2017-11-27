var state = false;

function changeSession() {
    if(state) {
        var url = "/saveAddMovie";
        var name = $("input[name ='movie_name']").val();
        var photo = $("input[name ='movie_photo']").val();
        var subscription = $("textarea[name ='movie_subscription']").val();
        var budget = $("input[name ='movie_budget']").val();
        var money = $("input[name ='movie_money']").val();
        var year = $("input[name ='movie_year']").val();

        $.post(url,
                {"movie_name": name,
                 "movie_photo" : photo,
                 "movie_subscription": subscription,
                 "movie_budget" : budget,
                 "movie_money": money,
                 "movie_year" : year});
    }
}

window.onbeforeunload  = changeSession;

$(document).ready(function() {
    $(".button").each(clickFunc);
});


function clickFunc() {
    $(this).click(function () {
        state = true;
    })

}

