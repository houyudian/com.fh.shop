<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>tree分类展示</title>
    <jsp:include page="../common/commons.jsp"></jsp:include>
</head>
<body>
<table id="example-basic">
    <caption>Basic基本 jQuery treetable Example</caption>
    <thead>
    <tr>
        <th>Tree column</th>
        <th>Additional data</th>
    </tr>
    </thead>
    <tbody>
    <tr data-tt-id="1">
        <td>Node 1:</td>
        <td>helloWorld.</td>
    </tr>
    <tr data-tt-id="1.1" data-tt-parent-id="1">
        <td>Node 1.1:</td>
        <td>hello.</td>
    </tr>
    <tr data-tt-id="1.1.1" data-tt-parent-id="1.1">
        <td>Node 1.1.1:</td>
        <td>呼呼</td>
    </tr>
    <tr data-tt-id="2">
        <td>Node 2:</td>
        <td>Hurray!</td>
    </tr>
    </tbody>
</table>


<div class="container">
    <div class="row">
        <div class="col-md-12" id="treeTableDiv">
            <table id="treeTable" class="table table-bordered">
                <thead>
                <tr>
                    <td>分类名</td>
                    <td>类型名</td>
                    <td>操作</td>
                </tr>
                </thead>
                <tbody>


                </tbody>
            </table>
        </div>
    </div>
</div>

<div id="addTreeTableDiv" style="display: none">
    <input name="id" id="treeTableId"/>
    <form class="form-horizontal">
        <div class="form-group">
            <label class="col-sm-3 control-label">分类名：</label>
            <div class="col-sm-5">
                <input class="form-control" id="addName" name="treeTable" maxlength="15" required>
                <input class="form-control" id="oldAddName" name="oldTreeTable" maxlength="15" required>
            </div>
        </div>

        <div class="form-group">
            <label class="col-sm-3 control-label">上级分类</label>
            <div class="col-sm-5" id="addSelectDiv"></div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">类型</label>
            <div class="col-sm-5" id="addTypeDiv"></div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">关联到子类</label>
            <div class="col-sm-5">
                <input type="checkbox" id="relateCheckbox" checked="true"/>
            </div>
        </div>

    </form>

</div>
<script>
    $("#example-basic").treetable({expandable: true, initialState: 'expanded'});
    var treeTableHtml;
    var addTreeTableHtml;
    $(function () {
        treeTableHtml = $("#treeTableDiv").html();
        addTreeTableHtml = $("#addTreeTableDiv").html();
        initTreeTable();
    })
    let data = [];
    let resultArr = [];

    function initTreeTable() {
        $.ajax({
            type: 'post',
            url: "/treeTable/fingAllTreeTable.jhtml",
            success: function (result) {
                data = result.data;
                resultArr = [];
                list(data, resultArr, 0, 0);
                buildTreeTable(resultArr);//zu
            }, error: function () {
                alert("初始化treeTeale异常");
            }
        })

    }

    function list(data, resultArr, id, level) {
        let childs = getChilds(data, id);
        for (let c of childs) {
            c.level = level;
            resultArr.push(c);
            list(data, resultArr, c.id, c.level + 1);
        }
    }

    function getChilds(data, id) {
        let childs = [];
        for (let a of data) {
            if (a.pid == id) {
                childs.push(a);
            }
        }
        return childs;
    }

    function buildTreeTable(data) {
        $("#treeTableDiv").html(treeTableHtml);
        let html = '<tr data-tt-id="0"><td>根目录&nbsp;&nbsp;<i class="glyphicon glyphicon-plus"><input type="button" value="增加" class="btn btn-link" onclick="add()"/></td><td></td><td>🌸🙃</td></tr>';

        for (let a of data) {
            html += '<tr data-tt-id=' + a.id + ' data-tt-parent-id=' + a.pid + '>' +
                '<td>' + a.treeTable + '&nbsp;&nbsp;<i class="glyphicon glyphicon-plus">' +
                '</i><input type="button" value="增加" class="btn btn-link" onclick="add(this)"/></td>' +
                '<td>' + a.typeName + '</td>' +
                '<td><button type="button" class="btn btn-danger" onclick="deletes(this)">删除<span class="glyphicon glyphicon-remove"></span></button>' +
                '<button type="button" class="btn btn-warning" onclick="updates(this)">修改<span class="glyphicon glyphicon-user"></span></button></td>' +
                '</tr>';
        }
        $("#treeTable tbody").html(html);
        $("#treeTable").treetable({expandable: true, initialState: 'expanded'});
    }

    function updates(obj) {
        var id = $(obj).parents("tr").data("tt-id");
        buildsSelect(resultArr, id);
        buildTypeRadioList();

        $.ajax({
            type: 'post',
            url: "/treeTable/findById.jhtml",
            data: {id: id},
            success: function (result) {
                if (result.code == 200) {
                    var d = result.data;
                    $("#treeTableId").val(d.id);
                    $("#addName").val(d.treeTable);
                    $("#oldAddName").val(d.treeTable);
                    $("#addTreeTable").val(d.pid);
                    $("input[name=typeIds]").each(function () {
                        if (d.typeId == this.value) {
                            this.checked = true;
                        }
                    })

                    var updateTreeTableDlg = bootbox.dialog({
                        message: $("#addTreeTableDiv form"),
                        title: "增加TreeTable",
                        size: 'large',
                        buttons: {
                            cancel: {label: "取消"},
                            confirm: {
                                label: '<span class="glyphicon glyphicon-ok"></span>确认',
                                className: 'btn-danger',
                                callback: function (result) {
                                    var param = {};
                                    param["treeTable.id"] = id;
                                    param["treeTable.treeTable"] = $("#addName", updateTreeTableDlg).val();
                                    param["treeTable.oldTreeTable"] = $("#oldAddName", updateTreeTableDlg).val();
                                    param["treeTable.pid"] = $("#addTreeTable", updateTreeTableDlg).val();
                                    param["treeTable.typeId"] = $("input[name=typeIds]:checked", updateTreeTableDlg).val();
                                    param["treeTable.typeName"] = $("input[name=typeIds]:checked", updateTreeTableDlg).attr("type-name");

                                    param.relateFlag = $("#relateCheckbox", updateTreeTableDlg).prop("checked")?1:0;
                                    var resultArr=[];
                                    getTree(data, resultArr, id)
                                   /* var idArr=[];
                                    for (let r of resultArr){idArr.push(r.id)}*/

                                   let idArr=resultArr.map(x=>x.id);
                                   param.ids=idArr.length==0?'':idArr.join(",");
                                   console.log(param)
                                   console.log(idArr)
                                    $.ajax({
                                        type: 'post',
                                        url: "/treeTable/updateTreeTable.jhtml",
                                        data: param,
                                        success: function (result) {
                                            if (result.code == 200) {
                                                alert("修改成功");
                                                initTreeTable();
                                            }
                                        }

                                    })
                                }
                            }
                        }
                    })
                    $("#addTreeTableDiv").html(addTreeTableHtml);

                }
            }
        })

    }

    function add(obj) {
        var id = $(obj).parents("tr").data("tt-id");
        console.log(id);
        buildsSelect(resultArr, id);
        buildTypeRadioList();
        var addHtml = '';
        var addTreeTableDlg = bootbox.dialog({
            message: $("#addTreeTableDiv form"),
            title: "增加TreeTable",
            size: 'large',
            buttons: {
                cancel: {label: "取消"},
                confirm: {
                    label: '<span class="glyphicon glyphicon-ok"></span>确认',
                    className: 'btn-danger',
                    callback: function (result) {

                        var param = {};
                        param.treeTable = $("#addName", addTreeTableDlg).val();
                        param.pid = $("#addTreeTable", addTreeTableDlg).val();
                        param.typeId = $("input[name=typeIds]:checked", addTreeTableDlg).val();
                        param.typeName = $("input[name=typeIds]:checked", addTreeTableDlg).attr("type-name");
                        $.ajax({
                            type: 'post',
                            url: "/treeTable/addTreeTable.jhtml",
                            data: param,
                            success: function (result) {
                                if (result.code == 200) {
                                    initTreeTable();
                                }
                            }
                        })

                    }
                }
            }
        })
        $("#addTreeTableDiv").html(addTreeTableHtml);

    }

    function buildsSelect(data, id) {
        console.log("修改："+id)
        var html = '<select id="addTreeTable"><option value="-1">==请选者==</option><option value="0">茛目录</option>';
        for (let d of data) {
            html += "<option value='" + d.id + "'>" + buildIndent(d.level) + d.treeTable + "</option>";
        }
        html += "</select>";
        /*document.getElementById("").innerHTML=html;*/
        $("#addSelectDiv").html(html);
        $("#addTreeTable").val(id);
    }

    function buildIndent(level) {
        var res = '';
        for (var i = 0; i < level; i++) {
            res += '&nbsp;&nbsp;';
        }
        return res;
    }

    function buildTypeRadioList() {
        $.ajax({
            type: 'post',
            url: "/type/findAllType.jhtml",
            async: false,
            success: function (result) {
                if (result.code == 200) {
                    var data = result.data;
                    var html = '<input type="radio" value="-1" type-name="" name="typeIds" checked/>无';
                    for (let d of data) {
                        html += '&nbsp;&nbsp;<input type="radio" type-name="' + d.typeName + '" name="typeIds" value="' + d.id + '"/>' + d.typeName;
                    }
                    $("#addTypeDiv").html(html);
                }
            }
        })
    }


    function deletes(obj) {
        bootbox.confirm({
            title: "删除分类", size: 'small',
            message: "确定删除",
            button: {label: '<span class="glyphicon glyphicon-remove"></span>取消', className: 'btn-danger',},
            callback: function (result) {
                if (result) {
                    var id = $(obj).parents("tr").data("tt-id");
                    var resultArr = [];
                    getTree(data, resultArr, id);
                    var ids = [];
                    for (let a of resultArr) {
                        ids.push(a.id);
                    }
                    ids.push(id);
                    $.ajax({
                        type: 'post',
                        url: "/treeTable/deleteTreeTable.jhtml",
                        data: {ids: ids},
                        success: function (result) {
                            alert("删除成功");
                            initTreeTable();
                        }, error: function () {
                            alert("删除异常");
                        }
                    })
                }
            }
        })

    }

    function getTree(data, resultArr, id) {
        for (let a of data) {
            if (id == a.pid) {
                resultArr.push(a);
                getTree(data, resultArr, a.id);
            }
        }
    }
</script>
</body>
</html>
