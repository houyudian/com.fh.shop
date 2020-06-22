<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2020/2/25
  Time: 14:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>goods添加SPU</title>
    <jsp:include page="../common/commons.jsp"></jsp:include>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <form class="form-group col-sm-12">

                <div class="form-group col-sm-12">
                    <label class="col-sm-2 control-label">名称</label>
                    <div class="col-sm-3">
                        <input class="form-control" id="proName" placeholder="请输入商品名">

                    </div>
                </div>

                <div class="form-group col-sm-12">
                    <label class="col-sm-2 control-label">主图</label>
                    <div class="col-sm-10">
                        <input type="file" id="mainImg" name="image"/>
                        <input id="imgPath"/>
                    </div>
                </div>
                <div class="form-group col-sm-12">
                    <label class="col-sm-2 control-label">价格</label>
                    <div class="col-sm-3">
                        <input class="form-control" id="price" name="img" placeholder="请输入商品价格"/>
                    </div>
                </div>
                <div class="form-group col-sm-12">
                    <label class="col-sm-2 control-label">库存</label>
                    <div class="col-sm-3">
                        <input class="form-control" id="stock" name="img" placeholder="请输入商品库存"/>
                    </div>
                </div>

                <div class="form-group col-sm-12" id="treeTableDiv">
                    <label class="col-sm-2 control-label">分类</label>
                    <div class="col-sm-10"></div>
                </div>

                <div class="form-group" id="relateInfoDiv"></div>

                <div style="margin-left: 500px;">
                    <button type="button" class="btn btn-success" onclick="addGoods()"><i
                            class="glyphicon glyphicon-plus"></i>提交
                    </button>
                    <button type="reset" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i>重置</button>
                </div>
            </form>

        </div>
    </div>

</div>

<script>
    $(function () {
        init(0);
        initMainImage();
    })
function initMainImage(){
    var s = {
        language: 'zh',
        uploadUrl: '/file/uploadImg.jhtml',
         showUpload:false,
         showRemove:false,
        dropZoneEnabled: false,//是否显示拖拽区域
        allowedFileExtensions: ['gif', 'jpeg', 'png', 'jpg']
    };
    $("#mainImg").fileinput(s).on("fileuploaded", function (r, event, previewiId, index) {
        var result = event.response;
console.log(result)
        if (result.code == 200) {
            $("#imgPath").val(result.data);
        }
    })
}

    function addGoods() {
        var param = {};
        param["goodsCommon.name"] = $("#proName").val();
        param["goodsCommon.mainImage"] = $("#imgPath").val();
        param["goodsCommon.price"] = $("#price").val();
        param["goodsCommon.stock"] = $("#stock").val();
        param["goodsCommon.brandId"] = $("#brandSelect").val();
        param["goodsCommon.brandName"] = $("#brandSelect option:selected").data("brand-name");
        param["goodsCommon.cate1"] = $($("select[name=cateSelect]")[0]).val();
        param["goodsCommon.cate2"] = $($("select[name=cateSelect]")[1]).val();
        param["goodsCommon.cate3"] = $($("select[name=cateSelect]")[2]).val();
        param["goodsCommon.cateName"] = "|" + $($("select[name=cateSelect]")[0]).find("option:selected").data("cate-name") + "|->|" +
            $($("select[name=cateSelect]")[1]).find("option:selected").data("cate-name") + "|->|" +
            $($("select[name=cateSelect]")[2]).find("option:selected").data("cate-name") + "|";
        var priceArr = [];
        $("input[name=skuPrice]").each(function () {
            priceArr.push(this.value);
        })
        param.prices = priceArr.length > 0 ? priceArr.join(",") : "";
        var stockArr = [];
        $("input[name=skuStock]").each(function () {
            stockArr.push(this.value);
        })
        param.stocks = stockArr.length > 0 ? stockArr.join(",") : "";
        var specValueArr = [];
        $("input[name=specValueInfo]").each(function () {
            specValueArr.push(this.value);
        })
        param.specValueInfos = specValueArr.length > 0 ? specValueArr.join(";") : "";


        var attrInfoArr = [];
        $("select[name=attrSelect]").each(function () {
            if ($(this).val() > 0) {
                var optionInfo = $(this).find("option:selected");
                var attrInfo = optionInfo.data("attr-name-id") + ":" + optionInfo.data("attr-name") + "," + $(this).val() + ":" + optionInfo.data("attr-value-name")
                attrInfoArr.push(attrInfo);
            }
        })
        param["goodsCommon.attrInfo"] = attrInfoArr.length > 0 ? attrInfoArr.join(";") : "";
        var resultArr = [];
        for (let s of specArr) {
            var specValueArr = [];
            var specValueList = [];
            //specValueArr.push(s.id+":"+s.speacName)
            $("input[name=specIds_" + s.id + "]:checked").each(function () {
                specValueArr.push($(this).val() + ":" + $(this).data("specvalue-info"))
            })
            if (specValueArr.length > 0) {
                specValueList.push(s.id + ":" + s.speacName);
                for (let v of specValueArr) {
                    specValueList.push(v);
                }
            }
            if (specValueList.length > 0) {
                resultArr.push(specValueList.join(","));
            }
        }
        param["goodsCommon.specInfo"] = resultArr.length > 0 ? resultArr.join(";") : "";

        var goodsImg = [];
        $("input[name=imgPath]").each(function () {
            var colorId = $(this).data("color-id");
            var imgPath = this.value.substring(1);
            goodsImg.push(colorId + "|" + imgPath);

        })
        param.goodsImage = goodsImg.length > 0 ? goodsImg.join(";") : "";

        console.log(param);

        $.ajax({
            type: "post",
            dataType: 'json',
            url: "/goods/addGoods.jhtml",
            data: param,
            success: function (result) {
                if (result.code == 200) {
                    alert("添加成功")
                    console.log(result.data)
                    location.href = "/goods/toList.jhtml";
                }
            }


        })

    }

    function init(id, obj) {
        if (obj) {
            $(obj).parent().nextAll().remove();
        }
        $.ajax({
            url: "/treeTable/findTreeTable.jhtml",
            type: 'post',
            data: {id: id},
            dataType: "json",
            success: function (result) {
                var data = result.data;
                if (data.length == 0) {
                    var typeId = $(obj).find("option:selected").data("type-id");
                    initTypeRelate(typeId);
                    return;
                }
                if (data.length > 0) {
                    var html = '<div class="col-sm-3"><select onchange="init(this.value,this)" name="cateSelect" class="form-control"><option value="-1">请选择</option>';
                    for (let t of data) {
                        html += '<option data-cate-name="' + t.treeTable + '" data-type-id="' + t.typeId + '" value="' + t.id + '">' + t.treeTable + '</option>';
                    }
                    html += '<select></div>';
                    $("#treeTableDiv").append(html);
                }
            }
        })
    }

    function initTypeRelate(typeId) {
        if (!typeId || typeId == -1) {
            bootbox.alert({
                message: '<span class="glyphicon-exclamation-sign">未绑定类型，请绑定</span>',
                size: 'small',
                title: "提示信息",
            })
        }
        $.ajax({
            type: 'post',
            url: "/type/findTypeRelate.jhtml",
            data: {typeId: typeId},
            dataType: "json",
            success: function (result) {
                if (result.code == 200) {
                    $("#relateInfoDiv").html("");
                    var data = result.data;
                    var speacVoList = data.speacVoList;
                    var brandList = data.brandList;
                    var attrVoList = data.attrVoList;

                    initBrandList(brandList);
                    initAttrList(attrVoList);
                    initSpeacList(speacVoList);
                }
            }
        })

    }

    function initBrandList(brandList) {
        var html = '<div class="form-group col-sm-12"><label class="col-sm-2 control-label">品牌</label>' +
            '<div class="col-sm-3"><select class="form-control" id="brandSelect"><option value="-1" data-brand-name="">请选择</option>';
        for (let b of brandList) {
            html += '<option  data-brand-name="' + b.brandName + '" value="' + b.id + '">' + b.brandName + '</option>';
        }
        html += '<div></select><div>';
        $("#relateInfoDiv").append(html);
    }

    function initAttrList(attrVoList) {

        var html = '<div class="form-group col-sm-12"><label class="col-sm-2 control-label">属性</label>';
        for (let a of attrVoList) {
            var item = '<div form-group class="col-sm-3">';
            item += '<div class="input-group"><span class="input-group-addon" id="basic-addon1">' + a.attr.attrName + '</span>';
            item += '<select class="form-control" name="attrSelect"><option value="-1">请选择</option>';
            var attrValueArr = a.attrValue
            for (let v of attrValueArr) {
                item += '<option  data-attr-name="' + a.attr.attrName + '" data-attr-name-id="' + a.attr.id + '" data-attr-value-name="' + v.attrValue + '" value="' + v.id + '">' + v.attrValue + '</option>';
            }
            item += '</select></div></div>';
            html += item;
        }
        html += '</div>';
        $("#relateInfoDiv").append(html);

    }

    var specArr = [];

    function initSpeacList(speacVoList) {
        for (let s of speacVoList) {
            specArr.push(s.speac);
            var html = '<div class="form-group">';
            html += '<label class="col-sm-2 control-label">' + s.speac.speacName + '</label>';
            var speacValueArr = s.speacValueList;
            for (let v of speacValueArr) {
                html += '&nbsp;&nbsp;<input type="checkbox" data-specvalue-info="' + v.name + '" name="specIds_' + s.speac.id + '" onclick="buildTable()" value="' + v.id + '">' + v.name;
            }
            html += '</div>';
            $("#relateInfoDiv").append(html);
        }
    }

    function spuStock() {
        var stocks = 0;
        $("input[name='skuStock']").each(function () {
            stocks += parseInt(this.value.length == 0 ? 0 : this.value)
            console.log(stocks)
        })
        $("#stock").val(stocks);
    }

    function spuPrice() {
        var priceArr = [];
        $("[name=skuPrice]").each(function () {
            priceArr.push(parseInt(this.value == 0 ? 0 : this.value))
        })
        priceArr.sort((x, y) => x - y);
        $("#price").val(priceArr[0]);
    }

    function buildTable() {
        $("#specTableDiv").remove();
        $("div[name=imgDiv]").remove();

        var res = [];
        var headerArr = [];
        for (let s of specArr) {
            var specId = s.id;
            var tempArr = [];
            $("input[name='specIds_" + specId + "']:checked").each(function () {
                var specValueId = $(this).val();
                var specValueInfo = $(this).data("specvalue-info");
                tempArr.push(specValueId + ":" + specValueInfo);

            })
            if (tempArr.length > 0) {
                res.push(tempArr);

                headerArr.push(s.speacName)
            }
        }
        if (headerArr.length > 1 && headerArr[0] == "颜色") {
            var colorArr = res[0];
            console.log(colorArr)
            var trArr = getData(res);

            var table = '<div class="row" id="specTableDiv"><div class="col-md-10 col-md-offset-2"><table class="table table-striped table-bordered">';
            var header = '<tr>';
            for (let h of headerArr) {
                header += '<th>' + h + '</th>';
            }
            header += '<th>价格</th><th>库存</th>';
            header += '</tr>';
            var body = '<tbody>';
            for (let tr of trArr) {
                body += '<tr>';
                var tdArr = tr.split(",");
                for (let td of tdArr) {
                    var tdA = td.split(":")[1];
                    body += '<td>' + tdA + '</td>';
                }
                body += '<td><input type="hidden" name="specValueInfo" value="' + tr + '"/><input class="form-control" placeholder="价格" style="width:120px" onkeydown="spuPrice()" name="skuPrice"></td>' +
                    '<td><input class="form-control" name="skuStock" placeholder="库存" onkeydown="spuStock()" style="width:120px"></td>';
                body += '</tr>';
            }
            body += '</tbody>';
            table += header;
            table += body;
            table += '</table></div></div>';

        }
        $("#relateInfoDiv").append(table);
        buildUploadImage(colorArr);
    }

    function buildUploadImage(colorArr) {
        for (let color of colorArr) {

            var html = '<div class="row" name="imgDiv"><div class="col-md-10 col-md-offset-2">';
            var colorId = color.split(":")[0];
            var colorName = color.split(":")[1];
            html += '<div class="panel panel-default">' +
                '<div class="panel-heading">' + colorName + '</div>' +
                '<input type="file" id="mainImg_' + colorId + '" data-color-id="' + colorId + '" name="image" multiple/>' +
                '<input type="text" id="imgPath_' + colorId + '" data-color-id="' + colorId + '" name="imgPath"/>' +
                '</div></div></div>';
            $("#relateInfoDiv").append(html)
            var s = {
                language: 'zh',
                uploadUrl: '/file/uploadImg.jhtml',
                /* showUpload:false,
                 showRemove:false,*/
                dropZoneEnabled: false,//是否显示拖拽区域
                allowedFileExtensions: ['gif', 'jpeg', 'png', 'jpg']
            };
            $("#mainImg_" + colorId).fileinput(s).on("fileuploaded", function (r, event, previewiId, index) {
                console.log($(this).data("color-id"))
                var colorId = $(this).data("color-id");
                var result = event.response;
                if (result.code == 200) {
                    var imgPath = $("#imgPath_" + colorId).val();
                    $("#imgPath_" + colorId).val(imgPath + "," + result.data);

                }
            })
        }
    }

    function getData(arr) {
        if (arr.length > 1) {
            var res = [];
            var base = arr[0];
            arr.splice(0, 1);
            var next = getData(arr);
            for (var i = 0; i < base.length; i++) {
                for (let j = 0; j < next.length; j++) {
                    res.push(base[i] + "," + next[j]);
                }
            }
            return res;
        } else {
            return arr[0];
        }
    }
</script>
</body>
</html>
