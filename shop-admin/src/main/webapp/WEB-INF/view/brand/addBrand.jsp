<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2020/3/11
  Time: 21:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <jsp:include page="../common/commons.jsp"></jsp:include>

</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <form class="form-horizontal">
                <div class="form-group">
                    <label class="col-sm-2 control-label">品牌名</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="brandName">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">主图</label>
                    <div class="col-sm-6">
                        <input type="file" id="img" name="logo">
                        <input type="text" id="logo">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">排序</label>
                    <div class="col-sm-6">
                        <input type="text" class="form-control" id="sort">
                    </div>
                </div>
                <div style="text-align: center;">
                    <button type="button" class="btn btn-primary" onclick="add();">
                        <i class="glyphicon glyphicon-search"></i>添加
                    </button>
                    <button type="reset" class="btn btn-default"><i class="glyphicon glyphicon-refresh"></i>重置
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>
<script>

    function add() {
        var param = {};
        param.brandName = $("#brandName").val();
        param.sort = $("#sort").val();
        $.ajax({
            url: "/brand/add.jhtml",
            data: param,
            type: "post",
            success: function (result) {
                location.href = "/brand/toList.jhtml";
            }
        })
    }
</script>
</body>
</html>
