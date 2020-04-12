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
    <title>类型展示type</title>
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
                </div>
                <table id="typeTable" class="table table-striped table-bordered" style="width:100%">
                    <thead>
                    <tr>
                        <th><input type="button" onclick="qx()" value="全选"></th>
                        <th>类型</th>
                        <th>操作</th>
                    </tr>
                    </thead>

                    <tfoot>
                    <tr>
                        <th><input type="button" onclick="pbx()" value="全不选"></th>
                        <th>类型</th>
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
        initTable();
    })


var idList=[];
    function initBindEvent() {
        $("#typeTable").on('click','tr',function () {
           var data= typeTable.row(this).data();
           var checkbox=$(this).find("[name=fxk]")[0];
           if (checkbox.checked){
               checkbox.checked=false;
               $(this).css('background-color','');
            idList.splice(idList.indexOf(checkbox.valueOf(),1));
           }else{
               checkbox.checked=true;
               $(this).css('background-color','yellow');
               idList.push(checkbox.value())
           }
        });
    }


var empTable;
    function initTable() {

        empTable = $('#typeTable').DataTable({
            "language": {"url": "/js/Chinese.json"},
            // 是否允许检索
            "searching": false,
            "processing": true,
            "lengthMenu": [3, 5, 8, 10],
            "serverSide": true,
            "ordering": false,
            "ajax": {
                "url": "/type/findType.jhtml",
                "type": "POST"
            },
            "columns": [
                {
                    "data": "id",
                    render: function (data, type, row, meta) {
                        return "<input type='checkbox' name='fxk' value='" + data + "'>";
                    }
                },
                {"data": "typeName"},
                {
                    "data": "id", "render": function (data, type, row, meta) {
                        var buttons = '<button type="button" class="btn btn-danger" onclick="deletes(' + data + ');">删除</button>' +
                            '<button type="button" class="btn btn-warning" onclick="updates(' + data + ');">修改</button>';
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

    function deletes(id) {
        $.ajax({
            url:"/type/deleteType.jhtml",
            dataType:"json",
            type:"post",
            data:{id:id},
            success:function (result) {
                alert("删除成功");
                empTable.ajax.reload();
            },error:function () {
                alert("初始化删除异常");
            }

        })
    }
    //全选
    function qx() {
        $("[name='fxk']").each(function () {
            this.checked = true;
        });
    }

    //全不选
    function pbx() {
        $("[name='fxk']").each(function () {
            this.checked = false;
        });
    }


    function toAdd(){
        location.href="/type/toAddType.jhtml";
    }

    function updates(id) {
        location.href="/type/toTypeEdit.jhtml?id="+id;
    }

</script>
</body>
</html>
