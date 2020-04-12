<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2020/2/5
  Time: 18:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>类型添加type</title>
    <jsp:include page="../common/commons.jsp"></jsp:include>

</head>
<body>
<div class="container">
    <div class="row">
        <form>
            <div class="col-sm-10">
                <div class="panel panel-success">
                    <div class="panel-heading">
                        <input type="hidden" name="id">
                        类型添加
                        <input class="form-control" placeholder="请输入类型名" id="typeName"/>
                    </div>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="panel panel-success">
                    <div class="panel-heading">品牌展示</div>
                    <table class="table table-bordered" id="brandTable">
                        <tbody></tbody>
                    </table>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="panel panel-success">
                    <div class="panel-heading">规格展示</div>
                    <table class="table table-bordered" id="speacTable">
                        <tbody></tbody>
                    </table>
                </div>
            </div>
            <div class="col-sm-12">
                <div class="panel panel-success">
                    <div class="panel-heading">属性添加
                        <button type="button" class="btn-primary" onclick="addAttr()">
                            <i class="glyphicon glyphicon-plus"></i>添加属性值
                        </button>
                    </div>
                    <table class="table table-bordered" id="attrTable">
                        <thead>
                        <tr>
                            <td>属性名</td>
                            <td>属性值</td>
                            <td>操作</td>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td><input name="attrName" class="form-control"/></td>
                            <td><input name="attrValue" class="form-control"/></td>
                            <td></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div style="margin-left: 500px;">
                <button type="button" class="btn btn-success" onclick="submitForm()"><i
                        class="glyphicon glyphicon-plus"></i>提交
                </button>
                <button type="reset" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i>重置</button>
            </div>
        </form>

    </div>
</div>
<textarea id="trDiv" style="display: none">

<tr>
             <td><input class="form-control" name="attrName"></td>
             <td><input class="form-control" name="attrValue"></td>
             <td><button type="button" class="btn-link" onclick="deleteAttr(this)">删除</button></td>
    </tr>
</textarea>
<script>

    function addAttr() {
        $("#attrTable tbody").append($("#trDiv").val());
    }

    function deleteAttr(obj) {
        $(obj).parents("tr").remove();
    }

    $.ajax({
        url: "/brand/findBrand.jhtml",
        dataType: "json",
        type: "post",
        success: function (result) {
            if (result.code == 200) {
                var total = result.data.length;
                var size = 6;
                var trCount = total % size == 0 ? total / size : Math.ceil(total / size);
                var htmls = '';
                for (var i = 0; i < trCount; i++) {
                    htmls += '<tr>';
                    var index = size * i;
                    for (var j = 0; j < size; j++) {
                        var brandInfo = result.data[index + j];
                        if (brandInfo) {
                            htmls += '<td><input type="checkbox" value=' + brandInfo.id + ' name="brandIds"/>' + brandInfo.brandName + '</td>'
                        } else {
                            htmls += '<td></td>';
                        }
                    }
                    htmls += '</tr>';
                    $("#brandTable tbody").html(htmls);
                }
            }

        }, error: function () {
            alert("初始化品牌异常");
        }
    })
    $.ajax({
        url: "/speac/findSpeac.jhtml",
        dataType: "json",
        type: "post",
        success: function (result) {
            //console.log(result);
            var total = result.data.length;
            var size = 2;
            var trCount = total / size == 0 ? total / size : Math.ceil(total / size);
            var htmls = ''

            for (var i = 0; i < trCount; i++) {
                htmls += '<tr>';
                var index = size * i;
                for (var j = 0; j < size; j++) {
                    var speac = result.data[index + j];
                    if (speac) {
                        htmls += '<td><input type="checkbox" value=' + speac.id + ' name="speacIds"/>' + speac.speacName + '</td>';
                    } else {
                        htmls += '<td></td>';
                    }
                }
                htmls += '</tr>';
                $("#speacTable tbody").html(htmls);

            }
        }, error: function () {
            alert("初始化规格异常");
        }
    })

    function submitForm() {
        var typeName = $("#typeName").val();
        var speacIdArr = [];
        var brandIdArr = [];
        var attrNameArr = [];
        var attrValueArr = [];
        $("input[name=speacIds]:checked").each(function () {
            speacIdArr.push(this.value);
        })
        $("input[name=brandIds]:checked").each(function () {
            brandIdArr.push(this.value);
        })

        $("input[name=attrName]").each(function () {
            attrNameArr.push(this.value);
        })
        $("input[name=attrValue]").each(function () {
            attrValueArr.push(this.value);
        })

        var param = {};
        param.typeName = typeName;
        param.speacIds = speacIdArr.join(",");
        param.brandIds = brandIdArr.join(",");
        param.attrNames = attrNameArr.join(",");
        param.attrValues = attrValueArr.join(";");
        console.log(param);

        $.ajax({
            url:"/type/addType.jhtml",
            type:"post",
            dataType:"json",
            data:param,
            success:function (result) {
                if (result.code==200){
                    alert("添加成功");
                location.href="/type/toType.jhtml";
                }
            },error:function () {
                alert("初始化异常");
            }

        })

    }
</script>
</body>
</html>
