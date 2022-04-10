/**
 * 提交回复，ajax
 */
function post() {
    var questionId = $("#questionId").val();
    var content = $("#commentText").val();
    comment2target(questionId,1,content);
}


/**
 *  展开二级评论
 */
function collapsecomment(e) {
    let id = e.getAttribute("data-id");
    let comments = $("#comment-" + id);
    comments.toggleClass("in");
    var subCommentContainer = $("#comment-" + id);
    if (subCommentContainer.children().length != 1) {

    } else {
        $.getJSON("/comment/" + id, function (data){
            $.each(data.data.reverse(), function (index, comment) {
                //拼接头像
                var mediaLeftElement = $("<div/>", {
                    "class":"media-left"
                }).append($("<img/>", {
                    "class":"media-object img-rounded",
                    "src":comment.user.avatar_url,
                    "alt":"头像丢失了"
                }));

                var mediaBodyElement = $("<div/>", {
                    "class":"media-body"
                }).append($("<h5/>", {
                    "class":"media-heading",
                    "html":comment.user.name
                })).append($("<div/>", {
                    "html":comment.content
                })).append($("<div/>", {
                    "class":"menu",
                }).append($("<span/>",{
                    "class":"pull-right",
                    "html": moment(comment.gmt_create).format('YYYY-MM-DD')
                })));

                var mediaElement = $("<div/>",{
                    "class":"media"
                }).append(mediaLeftElement).append(mediaBodyElement);

                var commentElement = $("<div/>", {
                    "class" : "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments subcommentscon2",
                }).append(mediaElement);

                subCommentContainer.prepend(commentElement);
            });
        });
    }
}

function comment2target(targetid,type,content) {

    if (!content) {
        alert("不能回复空内容~~~");
        return;
    }
    $.ajax({
        type:"POST",
        url:"/comment",
        contentType:"application/json",
        data: JSON.stringify({
            "parent_id":targetid,
            "content":content,
            "type":type
        }),
        success:function (response) {
            if (response.code == 200) {
                $("comment_section").hide();
                window.location.reload();
            } else {
                if (response.code == 2005) {
                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=Iv1.b8a646b73219c4e2&redirect_uri=http://localhost:8777/callback&state=1")
                        window.localStorage.setItem("closable", true);
                    }
                } else {
                    alert(response.message);
                }
            }
        },
        dataType:"json"
    });
}

/**
 * 二级回复评论
 * @param e
 */
function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    comment2target(commentId,2,content);
}

/**
 * 植入标签值
 */
function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();
    if (previous.indexOf(value) == -1) {
        if (previous) {
            $("#tag").val(previous + ',' + value);
        } else {
            $("#tag").val(value);
        }
    }
}

/**
 * 展示标签页
 */

function selectByTag() {
     $("#selectByTag").show();

    //获取焦点 focus

}


