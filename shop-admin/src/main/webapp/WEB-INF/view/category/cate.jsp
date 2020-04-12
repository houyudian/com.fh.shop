<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2020/3/14
  Time: 13:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>category分类展示</title>
    <jsp:include page="../common/commons.jsp"></jsp:include>

</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-12" id="categoryTableDiv">
            <table id="categoryTable" class="table table-bordered">
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


<div id="addCategoryDiv" style="display: none">
    <input name="id" id="categoryId"/>
    <form class="form-horizontal">
        <div class="form-group">
            <label class="col-sm-3 control-label">分类名：</label>
            <div class="col-sm-5">
                <input class="form-control" id="cateName" name="categoryName" maxlength="15" required>
                <input class="form-control" id="oldCateName" name="oldCategoryName" maxlength="15" required>
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
    $("#example-basic").treetable({expandable: true, initialState: 'expanded'})
    var categoryTableHtml;
    var addCategoryTableHtml;
    $(function () {
        categoryTableHtml=$("#categoryTableDiv").html();
        addCategoryTableHtml=$("#addCategoryDiv").html();
        initCategory();
    })
    let data = [];
    let resultArr = [];

    function initCategory() {
        $.ajax({
            type: 'post',
            url: "/category/findAllCategory.jhtml",
            success: function (result) {
                data = result.data;
                resultArr = [];
                cateList(data, resultArr, 0, 0);
                buildCate(resultArr);//zu
            }, error: function () {
                alert("初始化treeTeale异常");
            }
        })
    }

    function cateList(data, resultArr, id, level) {
        let childs = getChild(data, id);
        for (let c of childs) {
            c.level = level;
            resultArr.push(c);
            cateList(data, resultArr, c.id, c.level + 1);
        }
    }

    function getChild(data, id) {
        let childs = [];
        for (let ca of data) {
            if (ca.pid == id) {
                childs.push(ca);
            }
        }
        return childs;
    }

    function buildCate(data) {
        $("#categoryTableDiv").html(categoryTableHtml);
        let html = '<tr data-tt-id="0"><td>根目录&nbsp;&nbsp;<i class="glyphicon glyphicon-plus"><input type="button" value="增加" class="btn btn-link" onclick="add()"/></td><td></td><td>🌸🙃</td></tr>';

        for (let a of data) {
            html += '<tr data-tt-id=' + a.id + ' data-tt-parent-id=' + a.pid + '>' +
                '<td>' + a.categoryName + '&nbsp;&nbsp;<i class="glyphicon glyphicon-plus">' +
                '</i><input type="button" value="增加" class="btn btn-link" onclick="add(this)"/></td>' +
                '<td>' + a.typeName + '</td>' +
                '<td><button type="button" class="btn btn-danger" onclick="deletes(this)">删除<span class="glyphicon glyphicon-remove"></span></button>' +
                '<button type="button" class="btn btn-warning" onclick="updates(this)">修改<span class="glyphicon glyphicon-user"></span></button></td>' +
                '</tr>';
        }
        $("#categoryTable tbody").html(html);
        $("#categoryTable").treetable({expandable: true, initialState: 'expanded'});
    }
    function add(obj) {
        var id = $(obj).parents("tr").data("tt-id");
        buildsSelect(resultArr, id);
        buildTypeRadioList();
        var addHtml = '';
        var addTreeTableDlg = bootbox.dialog({
            message: $("#addCategoryDiv form"),
            title: "增加TreeTable",
            size: 'large',
            buttons: {
                cancel: {label: "取消"},
                confirm: {
                    label: '<span class="glyphicon glyphicon-ok"></span>确认',
                    className: 'btn-danger',
                    callback: function (result) {

                        var param = {};
                        param.categoryName = $("#cateName", addTreeTableDlg).val();
                        param.pid = $("#addCategory", addTreeTableDlg).val();
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
        $("#addCategoryDiv").html(addCategoryTableHtml);

    }
    function updates(obj) {
        var id = $(obj).parents("tr").data("tt-id");
        console.log("修改父类id："+id)
        buildsSelect(resultArr, id);
        buildTypeRadioList();
        $.ajax({
            type: 'post',
            url: "/category/findById.jhtml",
            data: {id: id},
            success: function (result) {
                if (result.code == 200) {
                    var d = result.data;
                    console.log(d)
                    $("#categoryId").val(d.id);
                    $("#cateName").val(d.categoryName);
                    $("#oldCateName").val(d.categoryName);
                    $("#addCategory").val(d.pid);
                    $("input[name=typeIds]").each(function () {
                        if (d.typeId == this.value) {
                            this.checked = true;
                        }
                    })

                    var updateTreeTableDlg = bootbox.dialog({
                        message: $("#addCategoryDiv form"),
                        title: "增加cate",
                        size: 'large',
                        buttons: {
                            cancel: {label: "取消"},
                            confirm: {
                                label: '<span class="glyphicon glyphicon-ok"></span>确认',
                                className: 'btn-danger',
                                callback: function (result) {
                                    var param = {};
                                    param["category.id"] = id;
                                    param["category.categoryName"] = $("#cateName", updateTreeTableDlg).val();
                                    param["category.oldCategoryName"] = $("#oldCateName", updateTreeTableDlg).val();
                                    param["category.pid"] = $("#addCategory", updateTreeTableDlg).val();
                                    param["category.typeId"] = $("input[name=typeIds]:checked", updateTreeTableDlg).val();
                                    param["category.typeName"] = $("input[name=typeIds]:checked", updateTreeTableDlg).attr("type-name");

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
                                        url: "/category/updateCategory.jhtml",
                                        data: param,
                                        success: function (result) {
                                            if (result.code == 200) {
                                                alert("修改成功");
                                                initCategory();
                                            }
                                        }

                                    })
                                }
                            }
                        }
                    })
                    $("#addCategoryDiv").html(addCategoryTableHtml);
                }
            }
        })
    }
    function buildsSelect(data,id) {
        console.log(data,id)
        var html = '<select id="addCategory"><option value="-1">==请选者==</option><option value="0">茛目录</option>';
        for (let d of data) {
            html += "<option value='" + d.id + "'>" + buildIndent(d.level) + d.categoryName + "</option>";
        }
        html += "</select>";
        /*document.getElementById("").innerHTML=html;*/
        $("#addSelectDiv").html(html);
        $("#addCategory").val(id);
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
                        url: "/category/deleteCategory.jhtml",
                        data: {ids: ids},
                        success: function (result) {
                            alert("删除成功");
                            initCategory();
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
