
$("#allForms").ready(changeDocHeight);

var CONSTANT_HEIGHT = 20;

var reviews;

var typeMarkId;

function changeDocHeight() {

    $("#general_inf").height($("#div_short_inf").outerHeight(true));

    var height = $('#menuDiv').outerHeight(true) + $("#allForms").outerHeight(true);
    document.documentElement.style.height = height;

    var markBtn = document.getElementById("setMarkBtn");
    if(markBtn !== null) {
        markBtn.onclick = setMovieMark;
    }

    reviews = document.getElementById("reviews");
    if(reviews !== null){
    reviews.onclick  = setReviewMark;

    typeMarkId = $("#typeMark").val();
    }
}

function setMovieMark() {
    var mark = $("#movieSelect").val();

    var markId = $("#movieId").val();

    var userId = $("#userId").val();

    var url = "/setMovieMark";

    $.ajax({
        type: 'POST',
        url: url,
        data: { "mark" : mark, "markId" : markId, "userId" : userId, "typeMark" : typeMarkId}
    }).done(changeMark(mark));
}

function changeMark(mark) {
    $("#div_mark_wr").remove();
    $("#mark_span").text("Ваша оценка : " + mark);
}

function setReviewMark(event) {
    var target = event.target;

    while (target !== reviews) {
        if(event.target.tagName !== 'SELECT') {
        }
        if (target.tagName === 'INPUT') {
            var parent = target.parentNode;
            var reviewId = $(parent).find("#reviewId").val();
            var writerId = $(parent).find("#writerId").val();
            var reviewMark = $("#reviews").find("#" + reviewId).val();
            var url = "/setReviewMark";

            $.ajax({
                type: 'POST',
                url: url,
                data: { "mark" : reviewMark, "reviewId" : reviewId,  "writerId" : writerId}
            });
            return;
        }
        target = target.parentNode;
    }
}






