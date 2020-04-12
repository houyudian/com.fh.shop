<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2020/2/9
  Time: 9:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="/js/jquery-3.3.1.min.js"></script>
    <script src="/js/vue.min.js"></script>
</head>
<body>

<div id="info"></div>
<div id="infoVue">
    <select>
        <option value="-1">===请选择===</option>
        <option v-for="book in books" :value="book.id">
            {{book.bookName}}
        </option>

    </select>
</div>
<div id="box">
    <span>{{msg}}</span>
</div>

<div id="app">
    <input v-model="msg"/><span>{{msg}}</span>
</div>
<div id="book">
    <ul>
        <li v-for="a in names">
            {{a}}
        </li>
    </ul>
    <ul>
        <li v-for="(x,y) in student">
            {{x}}={{y}}
        </li>
    </ul>
    <table>
        <tr>
            <td>名称</td>
            <td>年龄</td>
            <td>性别</td>
        </tr>
        <tr v-for="s in students">
            <td>{{s.stuName}}</td>
            <td>{{s.age}}</td>
            <td>{{s.sex}}</td>
        </tr>
    </table>
</div>

<div id="stu">
    名称：<input v-model="stuName"/><br/>
    年龄：<input v-model="age"/><br/>
    性别：<input v-model="sex"/><br/>

    <input type="button" value="增加学生" v-on:click="addUser()"/>
    <input type="button" value="批量删除" @click="bathDelete()"/>
    <input type="button" value="更新" @click="update()"/>

    <table v-show="students.length>0">
        <tr>
            <td>名称</td>
            <td>年龄</td>
            <td>性别</td>

        </tr>
        <tr v-for="(s,index) in students">
            <td>{{s.stuName}}</td>
            <td>{{s.age}}</td>
            <td>{{s.sex}}</td>
            <td>
                <input type="button" value="删除" @click="deleteStu(index);"/>
                <input type="button" value="编辑" @click="updateStu(index);"/>
            </td>

        </tr>
    </table>
    <h1 v-show="students.length==0">数据空!!</h1>
</div>


<script>
    $(function () {
        init();
    })

    function init() {
        var books = [{id: 1, bookName: "huahua"}, {id: 2, bookName: "三国"}, {id: 3, bookName: "小毛驴"}]
        var resultHtml = '<select><option>==请选择==</optin>';
        for (var i = 0; i < books.length; i++) {
            var book = books[i];
            resultHtml += '<option value=' + book.id + '>' + book.bookName + '</option>';
        }
        resultHtml += '</select>';
        $("#info").html(resultHtml);
    }

    new Vue({
        el: "#infoVue",
        data: {
            books: [{id: 1, bookName: "小小"}, {id: 2, bookName: "红楼"}, {id: 3, bookName: "开卡"}]
        }
    })
    new Vue({
        el: "#box",
        data: {
            msg: "飞虎欢迎您",
        }
    })
    new Vue({

        el: "#app",
        data: {
            msg: "hello,world"
        }
    })


    new Vue({
        el: "#book",
        data: {
            names: ["张", "李", "王"],
            student: {stuName: "花花", age: 12, sex: "女"},
            students: [{stuName: "花花", age: 12, sex: "女"}, {stuName: "xml", age: 12, sex: "女"}, {
                stuName: "小毛驴",
                age: 12,
                sex: "女"
            }]
        }
    })


    new Vue({
        el: "#stu",
        data: {
            stuName: "",
            age: "",
            sex: "",
            stuIndex:-1,
            students: []
        },
        methods: {
            addUser() {
                var user = {};
                user.stuName = this.stuName;
                user.age = this.age;
                user.sex = this.sex;
                this.students.push(user);
                this.stuName="";
                this.age="";
                this.sex="";
            },
            deleteStu(i){
                this.students.splice(i,1);
            },
            bathDelete(){
                this.students=[]
            },
            updateStu(i){
                var stu=this.students[i];
            this.stuName=stu.stuName;
            this.sex=stu.sex;
            this.age=stu.age;
            this.stuIndex=i;
            },
            update(){
this.students[this.stuIndex].stuName=this.stuName;
this.students[this.stuIndex].sex=this.sex;
this.students[this.stuIndex].age=this.age;
this.stuName="";
this.age="";
this.sex="";
            }
        }
    })

</script>


</body>
</html>
