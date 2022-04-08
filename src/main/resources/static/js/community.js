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
            console.log(data);
            $.each(data.data.reverse(), function (index, comment) {
                var c = $("<div/>", {
                    "class" : "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments subcommentscon2",
                    html:comment.content
                });
                subCommentContainer.prepend(c);
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


function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    alert(content);
    alert(commentId);
    comment2target(commentId,2,content);
}

