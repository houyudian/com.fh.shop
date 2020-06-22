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
    <title>类型修改type</title>
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
<textarea id="attrDiv" style="display: none">

                        <tr>
                            <td><input type="checkbox" name="attrIds" value="##attrIds##"></td>
                            <td><input class="form-control" name="attrName" value="##attrName##"/></td>
                            <td>##attrValue##<input type="hidden" name="attrValue" value="##attrValue##"/></td>
                            <td><button type="button" class="btn btn-link"
                                        onclick="toEditAttr('##attrIds##')">编辑</button></td>
                        </tr>
</textarea>
<textarea id="trDiv" style="display: none">
<tr>
             <td><input class="form-control" name="attrName"></td>
             <td><input class="form-control" name="attrValue"></td>
             <td><button type="button" class="btn-link" onclick="deleteAttr(this)">删除</button></td>
    </tr>
</textarea>
<script>
    $(function () {
        initBrand();
        initTypeData();
        initSpeac();
    })

    function addAttr() {
        $("#attrTable tbody").append($("#trDiv").val());
    }

    function deleteAttr(obj) {
        $(obj).parents("tr").remove();
    }

    function initBrand() {
        $.ajax({
            url: "/brand/findBrand.jhtml",
            type: "post",
            dataType: "json",
            success: function (result) {
                var total = result.data.length;
                var size = 6;
                var trCount = total / size == 0 ? total / size : Math.ceil(total / size);
                var htmls = '';
                for (var i = 0; i < trCount; i++) {
                    htmls += '<tr>';

                    var index = size * i;
                    for (var j = 0; j < size; j++) {
                        var brand = result.data[index + j];
                        if (brand) {
                            htmls += '<td><input type="checkbox" value=' + brand.id + ' name="brandIds"/>' + brand.brandName + '</td>';
                        } else {
                            htmls += '<td></td>';
                        }
                    }
                    htmls += '</tr>';
                    $("#brandTable tbody").html(htmls);
                }
            }, error: function () {
                alert("初始化品牌异常");
            }
        })
    }

    function initSpeac() {
        $.ajax({
            url: "/speac/findSpeac.jhtml",
            type: "post",
            dataType: "json",
            success: function (result) {
                var total = result.data.length;
                var size = 2;
                var trCount = total / size == 0 ? total / size : Math.ceil(total / size);
                var htmls = '';
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
    }

    function selectCheckbox(obj, brandArr) {
        for (var i = 0; i < brandArr.length; i++) {
            if (brandArr[i] == obj.value) {
                obj.checked = true;
            }
        }
    }

    function initTypeData() {
        var id =${param.id};
        $.ajax({
            type: "post",
            url: "/type/getByTypeId.jhtml",
            data: {id: id},
            success: function (result) {
                if (result.code == 200) {
                    console.log(result);
                    var data = result.data;
                    var typeName = data.type.typeName;
                    var speacArr = data.speacList;
                    var brandArr = data.brandList;
                    var attrArr = data.attrList;
                    console.log(brandArr);
                    $("#typeName").val(typeName);
                    $("input[name=speacIds]").each(function () {
                        selectCheckbox(this, speacArr);
                    })
                    $("input[name=brandIds]").each(function () {
                        selectCheckbox(this, brandArr);
                    })

                    var attrDivHtml = $("#attrDiv").val();
                    for (var i = 0; i < attrArr.length; i++) {
                        var attr = attrArr[i];
                        var result = attrDivHtml.replace(/##attrIds##/g, attr.id).replace(/##attrName##/g, attr.attrName).replace(/##attrValue##/g, attr.attrValue);
                        $("#attrTable tbody").append(result);

                    }

                }
            }
        })
    }

    function submitForm() {
        var typeId = '${param.id}';
        var typeName = $('#typeName').val();
        var speacIdArr = [];
        var brandIdArr = [];
        var attrNameArr = [];
        var attrValueArr = [];
        $('input[name=speacIds]:checked').each(function () {
            speacIdArr.push(this.value);
        })
        $('input[name=brandIds]:checked').each(function () {
            brandIdArr.push(this.value);
        })
        $('input[name=attrName]').each(function () {
            var checkboxArr = $().parents('tr').find('input[name=attrIds]')
            if (!checkboxArr[0] || !checkboxArr[0].checked) {
                attrNameArr.push(this.value);
            }
        })
        $('input[name=attrValue]').each(function () {
            var checkboxArr = $().parents('tr').find('input[name=attrIds]');
            if (!checkboxArr[0] || !checkboxArr[0].checked) {
                attrValueArr.push(this.value);
            }
        })
        var param = {};
        param.typeId=typeId;
        param.typeName=typeName;
        param.speacIds=speacIdArr.join(',');
        param.brandIds=brandIdArr.join(',');
        param.attrNames=attrNameArr.join(',');
        param.attrValues=attrValueArr.join(';');
$.ajax({
    url:"/type/updateType.jhtml",
    type:"post",
    dataType:"json",
    data:param,
    success:function (result) {
        if (result.code==200){
            alert("修改成功");
            location.href='/type/toType.jhtml';
        }
    },error:function () {
        alert('初始化修改异常');
    }
})

    }

</script>
</body>
</html>
