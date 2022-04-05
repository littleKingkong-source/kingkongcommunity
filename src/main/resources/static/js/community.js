function post() {
    var questionId = $("#questionId").val();
    var content = $("#commentText").val();
    $.ajax({
        type:"POST",
        url:"/comment",
        contentType:"application/json",
        data: JSON.stringify({
            "parent_id":questionId,
            "content":content,
            "type":1
        }),
        success:function (response) {
            alert(response.code);
            if (response.code == 200) {
                $("comment_section").hide();
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
