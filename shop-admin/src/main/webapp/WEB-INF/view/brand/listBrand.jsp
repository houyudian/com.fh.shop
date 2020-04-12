<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2020/3/11
  Time: 21:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>品牌</title>
    <jsp:include page="../common/commons.jsp"></jsp:include>

</head>
<body>

<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    类型列表
                    <button type="button" class="btn btn-info" onclick="toAdd();"><i
                            class="glyphicon glyphicon-ok"></i> 新增
                    </button>
                    <button type="button" class="btn btn-info" onclick="deleteBrandCache();"><i
                            class="glyphicon glyphicon-ok"></i> 清楚缓存
                    </button>
                </div>
                <table id="brandTable" class="table table-striped table-bordered" style="width:100%">
                    <thead>
                    <tr>
                        <th><input type="button" onclick="qx()" value="全选"></th>
                        <th>类型</th>
                        <th>推荐</th>
                        <th>操作</th>
                    </tr>
                    </thead>

                    <tfoot>
                    <tr>
                        <th><input type="button" onclick="pbx()" value="全不选"></th>
                        <th>类型</th>
                        <th>推荐</th>
                        <th>操作</th>
                    </tr>
                    </tfoot>
                </table>
            </div>

        </div>
    </div>
</div>

<script>
    $(function () {
        initBrandList();
    })

    function toAdd() {
        location.href = "/brand/toAdd.jhtml";
    }

    var idList = [];

    function initBindEvent() {
        $("#typeTable").on('click', 'tr', function () {
            var data = typeTable.row(this).data();
            var checkbox = $(this).find("[name=fxk]")[0];
            if (checkbox.checked) {
                checkbox.checked = false;
                $(this).css('background-color', '');
                idList.splice(idList.indexOf(checkbox.valueOf(), 1));
            } else {
                checkbox.checked = true;
                $(this).css('background-color', 'yellow');
                idList.push(checkbox.value())
            }
        });
    }

    var brandTable;

    function initBrandList() {
        brandTable = $("#brandTable").DataTable({
            "language": {"url": "/js/Chinese.json"},
            "searching": false, // 是否允许检索
            "processing": true,
            "lengthMenu": [3, 5, 8, 10],
            "serverSide": true,
            "ordering": false,
            "ajax": {
                "type": "post",
                "url": "/brand/findBrandList.jhtml",
            },
            "columns": [
                {
                    "data": "id",
                    render: function (data, type, row, meta) {
                        return "<input type='checkbox' name='fxk' value='" + data + "'>";
                    }
                },
                {"data": "brandName"},
                {
                    data: 'isReconmend', render: function (data, type, row, meat) {
                        return data == "0" ? "不推荐" : "推荐";
                    }
                },
                {
                    "data": "id", "render": function (data, type, row, meta) {

                        var r = row.isReconmend;
                        var r_text = "";
                        var r_icon = "";
                        var r_color = "";

                        if (r == "0") {
                            r = "1";
                            r_text = "推荐";
                            r_icon = "glyphicon glyphicon-fire";
                            r_color = "btn btn-success";
                        } else {
                            r = "0";
                            r_text = "不推荐";
                            r_icon = "glyphicon glyphicon-thumbs-down";
                            r_color = "btn btn-default";
                        }

                        var buttons = '<button type="button" class="btn btn-danger" onclick="deletes(' + data + ');">删除</button>' +
                            '<button type="button" class="btn btn-warning" onclick="updates(' + data + ');">修改</button>' +
                            '<button type="button" class="' + r_color + '" onclick="updateReconmend(' + data + ',' + r + ')"><i class="' + r_icon + '">' + r_text + '</i></button>';
                        return buttons;
                    },
                },

            ],
            //datatables 自带的回调函数
            "drawCallback": function (settings) {
                //判断List里面是否有值
                if (idList.length > 0) {
                    $("[name='fxk']").each(function () {
                        if (idList.indexOf(this.value) != -1) {
                            this.checked = true;
                            $(this).parent().parent().css("background-color", "yellow");
                        }
                    })
                }
            },
        });
    }

    function seach() {
        brandTable.ajax.reload();
    }

    function updateReconmend(brandId, isReconmend) {
        console.log(
            brandId,isReconmend
        )
        $.ajax({
            url: "/brand/updateReconmend.jhtml",
            type: "post",
            data: {brandId: brandId, isReconmend: isReconmend},
            success: function (result) {
                if (result.code == 200) {
                    seach();
                }
            }
        })
    }
    
    function deleteBrandCache() {
        $.ajax({
            url:"/brand/deleteBrandCache.jhtml",
            type:"get",
            success:function (result) {
                if (result.code==200){
                    alert("ok");
                }
            }
        })
    }
</script>
</body>
</html>
