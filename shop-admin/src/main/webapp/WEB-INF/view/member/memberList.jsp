<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2020/5/5
  Time: 21:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>member展示</title>
    <jsp:include page="../common/commons.jsp"></jsp:include>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-primary">
                <div class="panel-heading">会员列表条件查询</div>
                <form class="form-horizontal" id="userQueryForm">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">会员名</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="name" name="name" placeholder="会员名称。。。">
                        </div>

                        <label class="col-sm-2 control-label">realName</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" id="realName" name="name" placeholder="真实名称。。。">
                        </div>
                        <label class="col-sm-2 control-label">生日</label>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <input type="text" class="form-control" id="minDate" placeholder="起始。。。">
                                <span class="input-group-addon" id="sizing-addon2"><i
                                        class="glyphicon glyphicon-calendar"></i></span>
                                <input type="text" class="form-control" id="maxDate" placeholder="结束。。。">
                            </div>
                        </div>
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
                <div class="panel-heading">列表条件查询</div>

                <table id="memberTable" class="table table-striped table-bordered" style="width:100%">
                    <thead>
                    <tr>
                        <th></th>
                        <th>全选</th>
                        <th>真实姓名</th>
                        <th>名称</th>
                        <th>手机号</th>
                        <th>邮箱</th>
                        <th>生日</th>
                        <th>地区</th>
                        <th>操作</th>
                    </tr>
                    </thead>

                    <tfoot>
                    <tr>
                        <th></th>
                        <th>全选</th>
                        <th>真实姓名</th>
                        <th>名称</th>
                        <th>手机号</th>
                        <th>邮箱</th>
                        <th>生日</th>
                        <th>地区</th>
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
        initMember();
    })
    var memberTable;

    function initMember() {
        memberTable = $("#memberTable").DataTable({
            language: {url: '/js/DataTables/Chinese.json'},
            searching: false,
            processing: true,
            lengthMenu: [5, 10, 15, 20],
            serverSide: true,
            ajax: {url: "/member/findMember.jhtml", type: "get",},
            columns: [
                {className: 'details-control', orderable: false, data: null, defaultContent: ''},
                {
                    data: 'id', render: function (data, type, row, meta) {
                        return '<input type="checkbox" name="ids" value="' + data + '"/>';
                    }
                },
                {data: 'realName'},
                {data: 'name'},
                {data: 'phone'},
                {data: 'mail'},
                {data: 'birthday'},
                {data: 'areaName'},

                {
                    data: 'id', render: function (data, type, row, meta) {
                        return '<button type="button" class="btn btn-danger" onclick="deletes(' + data + ');">删除</button>&nbsp;' +
                            '<button type="button" class="btn btn-warning" onclick="updates(' + data + ');">修改</button>';
                    }
                },
            ],

        })
        $('#memberTable tbody').on('click', 'td.details-control', function () {
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

    function search() {
        var param = {};

        param.name = $("#name").val();
        param.realName = $("#realName").val();
        param.minDate = $("#minDate").val();
        param.maxDate = $("#maxDate").val();
        memberTable.settings()[0].ajax.data = param;
        memberTable.ajax.reload();

    }

</script>
</body>
</html>
