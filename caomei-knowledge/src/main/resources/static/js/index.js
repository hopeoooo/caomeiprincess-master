var jq22 = {
    init: function () {
        $("ul.list").attr("class", "hidden");
        checkNum();
    },
    loadMore: function () {
        $('.jq22 .more').html("<p>数据加载中...</p>");
        $.ajax({
            url: "/queryQuestion?pageCode=" + nexPage+"&param="+$("#param").val(),
            success: function (question) {
                for (i = 0; i < question.data.rows.length; i++) {
                    $("#js-home-feed-list").append(getMoreHtml(question.data.rows[i].title ? question.data.rows[i].title : "",
                        question.data.rows[i].qUpdateDate ? question.data.rows[i].qUpdateDate : "",
                        question.data.rows[i].qid ? question.data.rows[i].qid : "",
                        question.data.rows[i].atext ? question.data.rows[i].atext : "",
                        question.data.rows[i].qtext ? question.data.rows[i].qtext : ""));
                }
                pageNum++;
                nexPage++;
                checkNum();
            }
        });
    }
};

function checkNum() {
    if(total<=0){
        $('.jq22 .more').html("<p>没有找到相关内容</p>");
    }else if(total<=(pageNum+1)*pageSize){
        $('.jq22 .more').html("<p>全部加载完毕</p>");
    }else{
        $('.jq22 .more').html("<a href=\"javascript:;\" onclick=\"jq22.loadMore();\">加载更多</a>");
    }

}

//展开 收起
function swithContent(id) {
    var swith = $("#swith" + id);
    if ("1" == swith.attr("swith_data")) {
        $("#content" + id).css("height", "100%");
        swith.attr("swith_data", "2");
        $(swith).text("👆收起");
    } else {
        $("html,body").animate({scrollTop: ($("#content" + id).offset().top - 400)}, 300);//1000是ms,也可以用slow代替
        $("#content" + id).css("height", "200px");
        swith.attr("swith_data", "1");
        $(swith).text("👇展开");
    }
    return false;
}

function getMoreHtml(title,qUpdateDate,qId,aText,qText){
    return "<div class=\"feed-item folding feed-item-hook feed-item-2\">" +
        "<div class=\"feed-item-inner\">" +
        "<div class=\"avatar\">" +
        "<img src=\"/img/caomei.jpg\" class=\"zm-item-img-avatar\">" +
        "</div>" +
        "<div class=\"feed-main\">" +
        "<div class=\"feed-content\" data-za-module=\"AnswerItem\">" +
        "<h2 class=\"feed-title\">" +
        "<a class=\"question_link\" target=\"_blank\" href=\"#\">"+title+"</a>"+
        "</h2>" +
        "<div >"+qText+
        "</div>" +
        "<div class=\"expandable entry-body\">" +
        "<div class=\"zm-item-answer-author-info\">" +
        "<span class=\"author-link\" >"+qUpdateDate+"</span>" +
        "</div>" +
        "<div class=\"zm-item-rich-text expandable js-collapse-body\">" +
        "<div class=\"zh-summary summary clearfix\">" +
        "<div id=\"content"+qId+"\" class=\"question_content\" onclick=\"swithContent("+qId+")\">" +aText+
        "</div>" +
        "<span swith_data=\"1\" id=\"swith"+qId+"\" style=\"color: #006acc\" onclick=\"swithContent("+qId+")\">👇展开</span>" +
        "</div>" +
        "</div>" +
        "</div>" +
        "</div>" +
        "</div>" +
        "</div>" +
        "</div>";
}