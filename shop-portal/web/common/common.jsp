<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2020/3/22
  Time: 18:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<script>
    $.ajaxSetup({
        complete:
            function (XMLHttpRequest, textStatus) {


                    var header = XMLHttpRequest.getResponseHeader("自定义信息");
                if (header && header == "timeout") {
                    location.href = "/";
                }

                var result= XMLHttpRequest.responseJSON;
                if (result.code==-1){
                    bootbox.alert({
                        message:"<span class='glyhicon glyhicon-exclamation'>系统错误，联系管理员</span>",
                        size:"small",
                        title:"提示信息",
                    })
                }

            }
    })
</script>

</body>
</html>
