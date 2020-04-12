<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2020/2/28
  Time: 13:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>goods展示</title>
    <jsp:include page="../common/commons.jsp"></jsp:include>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-primary">
                <div class="panel-heading">列表条件查询</div>
                <form class="form-horizontal" id="userQueryForm">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">商品名</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="name" name="name" placeholder="商品名称。。。">
                        </div>

                        <label class="col-sm-2 control-label">品牌</label>
                        <div class="col-sm-4" id="brandSelect"></div>
                    </div>

                    <div style="text-align: center;">
                        <button type="button" class="btn btn-primary" onclick="search();"><i
                                class="glyphicon glyphicon-search"></i>查询
                        </button>
                        <button type="reset" class="btn btn-default"><i class="glyphicon glyphicon-refresh"></i>重置
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    spu列表
                    <button type="button" class="btn btn-info" onclick="toAdd();">spu添加</button>
                    <button type="button" class="btn btn-danger" onclick="bathDeletes();">批量删除</button>
                    <button type="button" class="btn btn-danger" onclick="deletePath();">清楚缓存</button>
                </div>
                <table id="goodsTable" class="table table-striped table-bordered" style="width:100%">
                    <thead>
                    <tr>
                        <th></th>
                        <th>全选</th>
                        <th>主图</th>
                        <th>名称</th>
                        <th>价格</th>
                        <th>库存</th>
                        <th>品牌</th>
                        <th>分类名</th>
                        <th>是否热销</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>

                    <tfoot>
                    <tr>
                        <th></th>
                        <th>全选</th>
                        <th>主图</th>
                        <th>名称</th>
                        <th>价格</th>
                        <th>库存</th>
                        <th>品牌</th>
                        <th>分类名</th>
                        <th>是否热销</th>
                        <th>状态</th>
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
        initGoods();
        //initEvent();
        initBrand();
    })
    function toAdd(){
        location.href="/goods/toGoods.jhtml";
    }

    function format(row) {
        console.log(row);
        var htmls = "<div>";
        for (let s of row.goodsList) {
            htmls += "<table class='table table-bordered' style='width:45%;float:left;margin:10px'>" +
                "<tr><td>id:</td><td>" + s.id + "</td></tr>" +
                "<tr><td>主图:</td><td><img src="+s.mainImage+" width='50px' alt='图片未找到'/></td></tr>" +
                "<tr><td>商品名:</td><td>" + s.productName + "</td></tr>" +
                "<tr><td>价格:</td><td>" + s.price + "</td></tr>" +
                "<tr><td>库存:</td><td>" + s.stock + "</td></tr>" +
                "</table>";
        }
        htmls += "</div>";
        return htmls;
        console.log(htmls);
    }

    function initEvent() {
        $("#goodsTable tbody").on('click', 'td.details-control', function () {
            var tr = $(this).closest('tr');
            var row = goodsTable.row(tr);
            if (row.child.isShown()) {
                row.child.hide();
                tr.removeClass('shown');
            } else {
                row.child(format(row.data())).show();
                tr.addClass('shown');
            }
        })
    }

    var goodsTable;

    function initGoods() {
        goodsTable = $("#goodsTable").DataTable({
            language: {url: '/js/DataTables/Chinese.json'},
            searching: false,
            processing: true,
            lengthMenu: [5, 10, 15, 20],
            serverSide: true,
            ajax: {url: "/goods/findGoodsList.jhtml", type: "post",},
            columns: [
                {className: 'details-control', orderable: false, data: null, defaultContent: ''},
                {
                    data: 'goodsCommon.id', render: function (data, type, row, meta) {
                        return '<input type="checkbox" name="ids" value="' + data + '"/>';
                    }
                },
                {
                    data: "goodsCommon.mainImage", "title": "图片", render: function (data, type, row, meta) {
                        return '<img src="' + data + '" alt="图片未找到" width="50px">'
                    }
                },
                {data: 'goodsCommon.name'},
                {data: 'goodsCommon.price'},
                {data: 'goodsCommon.stock'},
                {data: 'goodsCommon.brandName'},
                {data: 'goodsCommon.cateName'},

                {
                    data: 'goodsCommon.isHot', render: function (data, type, row, meat) {
                        return data == "0" ? "非热销" : "热销";
                    }
                },
                {
                    data: 'goodsCommon.status', render: function (data, type, row, meat) {
                        return data == "0" ? "下架" : "上架";
                    }
                },
                {
                    data: 'goodsCommon.id', render: function (data, type, row, meta) {
                        var status = row.goodsCommon.status;
                        var status_text = "";
                        var status_icon = "";
                        var status_color = "";
                        var isHot = row.goodsCommon.isHot;
                        var isHot_text = "";
                        var isHot_icon = "";
                        var isHot_color = "";

                        if (isHot == "0") {
                            isHot = "1";
                            isHot_text = "热销";
                            isHot_icon = "glyphicon glyphicon-fire";
                            isHot_color = "btn btn-success";
                        } else {
                            isHot = "0";
                            isHot_text = "非热销";
                            isHot_icon = "glyphicon glyphicon-thumbs-down";
                            isHot_color = "btn btn-default";
                        }

                        if (status == "0") {
                             status = "1";
                            status_text = "上架";
                            status_icon = "glyphicon glyphicon-arrow-up";
                            status_color = "btn btn-success";
                        } else {
                             status = "0";
                            status_text = "下架";
                            status_icon = "glyphicon glyphicon-arrow-down";
                            status_color = "btn btn-danger";
                        }

                        return '<button type="button" class="btn btn-danger" onclick="deletes(' + data + ');">删除</button>&nbsp;' +
                            '<button type="button" class="btn btn-warning" onclick="updates(' + data + ');">修改</button>' +
                            '<button type="button" class="' + status_color + '" onclick="updateStatus(' + data + ',' + status + ')"><i class="' + status_icon + '">' + status_text + '</i></button>&nbsp;' +
                            '<button type="button" class="' + isHot_color + '" onclick="updateHot(' + data + ',' + isHot + ')"><i class="' + isHot_icon + '">' + isHot_text + '</i></button>';
                    }
                },
            ],
        })


        $('#goodsTable tbody').on('click', 'td.details-control', function () {
            var tr = $(this).closest('tr');
            var row = goodsTable.row(tr);

            if (row.child.isShown()) {
                // This row is already open - close it
                row.child.hide();
                tr.removeClass('shown');
            }
            else {
                // Open this row
                row.child(format(row.data())).show();
                tr.addClass('shown');
            }
        });
    }

    function initBrand() {
        $.ajax({
            type: "post",
            url: "/brand/findBrand.jhtml",
            success: function (result) {
                if (result.code == 200) {
                    var html = '<select class="form-control" id="brandId" name="brandId"><option value="-1">请选择</option>';
                    var brandList = result.data;
                    for (var i = 0; i < brandList.length; i++) {
                        var b = brandList[i];
                        html += '<option value="' + b.id + '">' + b.brandName + '</option>';
                    }
                    html += '</select>';
                    $("#brandSelect").html(html);
                }
            }
        })
    }

    function search() {
        var param = {};

        param.name = $("#name").val();
        param.brandId = $("#brandId").val();
        goodsTable.settings()[0].ajax.data = param;
        goodsTable.ajax.reload();

    }
    function updateStatus(goodsCommonId, status) {
        console.log(goodsCommonId,status);
        $.ajax({
            url: "/goods/updateStatus.jhtml",
            type: "post",
            data: {goodsCommonId: goodsCommonId, status: status},
            dataType:"json",
            success: function (result) {
                if (result.code == 200) {
                    search();
                }
            }
        })
    }

    function updateHot(goodsCommonId, hot) {
        console.log(goodsCommonId,hot);
        $.ajax({
            url: "/goods/updateHot.jhtml",
            type: "post",
            dataType:"json",
            data: {goodsCommonId: goodsCommonId, hot: hot},
            success: function (result) {
                if (result.code == 200) {
                    search();
                }
            }
        })
    }
function deletePath(){
        $.ajax({
            url:"/goods/deleteHotPath.jhtml",
            dataType:"json",
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
