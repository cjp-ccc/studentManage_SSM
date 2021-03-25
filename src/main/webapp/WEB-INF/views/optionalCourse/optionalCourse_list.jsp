<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <title>可选课程列表</title>
    <link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="../easyui/css/demo.css">
    <script type="text/javascript" src="../easyui/jquery.min.js"></script>
    <script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../easyui/js/validateExtends.js"></script>
    <script type="text/javascript">
        var teacherList = ${teacherListJson};
        $(function() {
            var table;

            //datagrid初始化
            $('#dataList').datagrid({
                title:'可选课程列表',
                iconCls:'icon-more',//图标
                border: true,
                collapsible:false,//是否可折叠的
                fit: true,//自动大小
                method: "post",
                url:"get_list?t="+new Date().getTime(),
                idField:'id',
                singleSelect:false,//是否单选
                pagination:true,//分页控件
                rownumbers:true,//行号
                sortName:'id',
                sortOrder:'DESC',
                remoteSort: false,
                columns: [[
                    {field:'chk',checkbox: true,width:50},
                    {field:'id',title:'ID',width:50, sortable: true},
                    {field:'name',title:'姓名',width:150, sortable: true},
                    {field:'courseDate',title:'课程时间',width:150, sortable: true},
                    {field:'courseLocation',title:'课程地点',width:150, sortable: true},
                    {field:'teacherId',title:'任课老师',width:150, sortable: true,
                        formatter:function(value,index,row){
                            for(var i=0;i<teacherList.length;i++){
                                if(teacherList[i].id == value){
                                    return teacherList[i].name;
                                }
                            }
                            return value;
                        }
                    },
                    // {field:'password',title:'密码',width:150},
                    {field:'selectedNum',title:'已选人数',width:150, sortable: true},
                    {field:'maxNum',title:'最大选课人数',width:200},
                    // {field:'admissionYear',title:'入学时间',width:200},
                ]],
                toolbar: "#toolbar"
            });
            //设置分页控件
            var p = $('#dataList').datagrid('getPager');
            $(p).pagination({
                pageSize: 10,//每页显示的记录条数，默认为10
                pageList: [10,20,30,50,100],//可以设置每页记录条数的列表
                beforePageText: '第',//页数文本框前显示的汉字
                afterPageText: '页    共 {pages} 页',
                displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
            });
            //设置工具类按钮
            $("#add").click(function(){
                table = $("#addTable");
                $("#addDialog").dialog("open");
            });

            //修改
            $("#edit").click(function(){
                table = $("#editTable");
                var selectRows = $("#dataList").datagrid("getSelections");
                if(selectRows.length != 1){
                    $.messager.alert("消息提醒", "请选择一条数据进行操作!", "warning");
                } else{
                    $("#editDialog").dialog("open");
                }
            });
            //删除
            $("#delete").click(function(){
                var selectRows = $("#dataList").datagrid("getSelections");
                var selectLength = selectRows.length;
                if(selectLength == 0){
                    $.messager.alert("消息提醒", "请选择数据进行删除!", "warning");
                } else{
                    var ids = [];
                    $(selectRows).each(function(i, row){
                        ids[i] = row.id;
                    });
                    $.messager.confirm("消息提醒", "确定删除课程信息？", function(r){
                        if(r){
                            $.ajax({
                                type: "post",
                                url: "delete",
                                data: {ids: ids},
                                dataType:'json',
                                success: function(data){
                                    if(data.type == "success"){
                                        $.messager.alert("消息提醒","删除成功!","info");
                                        //刷新表格
                                        $("#dataList").datagrid("reload");
                                        $("#dataList").datagrid("uncheckAll");
                                    } else{
                                        $.messager.alert("消息提醒",data.msg,"warning");
                                        return;
                                    }
                                }
                            });
                        }
                    });
                }
            });

            //选课
            $("#selectCourse").click(function(){
                var selectRows = $("#dataList").datagrid("getSelections");

                if(selectRows.length != 1){
                    $.messager.alert("消息提醒", "请选择一条数据进行操作!", "warning");
                } else{


                    var ids = [];
                    $(selectRows).each(function(i, row){
                        ids[i] = row.id;
                    });

                    $.messager.confirm("消息提醒", "确定选择该课程？", function(r){
                        if(r){
                            $.ajax({
                                type: "post",
                                url: "selectCourse",
                                data: {ids: ids},
                                dataType:'json',
                                success: function(data){
                                    if(data.type == "success"){
                                        $.messager.alert("消息提醒","选课成功!","info");
                                        //刷新表格
                                        $("#dataList").datagrid("reload");
                                        $("#dataList").datagrid("uncheckAll");
                                    } else{
                                        $.messager.alert("消息提醒",data.msg,"warning");
                                        return;
                                    }
                                }
                            });
                        }
                    });
                }
            });

            //设置添加窗口
            $("#addDialog").dialog({
                title: "添加课程",
                width: 450,
                height: 650,
                iconCls: "icon-add",
                modal: true,
                collapsible: false,
                minimizable: false,
                maximizable: false,
                draggable: true,
                closed: true,
                buttons: [
                    {
                        text:'添加',
                        plain: true,
                        iconCls:'icon-add',
                        handler:function(){
                            var validate = $("#addForm").form("validate");
                            if(!validate){
                                $.messager.alert("消息提醒","请检查你输入的数据!","warning");
                                return;
                            } else{
                                var data = $("#addForm").serialize();
                                $.ajax({
                                    type: "post",
                                    url: "add",
                                    data: data,
                                    dataType:'json',
                                    success: function(data){
                                        if(data.type == "success"){
                                            $.messager.alert("消息提醒","添加成功!","info");
                                            //关闭窗口
                                            $("#addDialog").dialog("close");
                                            //清空原表格数据
                                            $("#add_name").textbox('setValue', "");
                                            $("#add_password").textbox('setValue', "");
                                            //重新刷新页面数据
                                            $('#dataList').datagrid("reload");

                                        } else{
                                            $.messager.alert("消息提醒",data.msg,"warning");
                                            return;
                                        }
                                    }
                                });
                            }
                        }
                    },
                ],
                onClose: function(){
                    $("#add_name").textbox('setValue', "");
                    $("#add_password").textbox('setValue', "");
                }
            });

            //编辑课程信息
            $("#editDialog").dialog({
                title: "修改课程信息",
                width: 450,
                height: 650,
                iconCls: "icon-edit",
                modal: true,
                collapsible: false,
                minimizable: false,
                maximizable: false,
                draggable: true,
                closed: true,
                buttons: [
                    {
                        text:'提交',
                        plain: true,
                        iconCls:'icon-edit',
                        handler:function(){
                            var validate = $("#editForm").form("validate");
                            if(!validate){
                                $.messager.alert("消息提醒","请检查你输入的数据!","warning");
                                return;
                            } else{

                                var data = $("#editForm").serialize();

                                $.ajax({
                                    type: "post",
                                    url: "edit",
                                    data: data,
                                    dataType:'json',
                                    success: function(data){
                                        if(data.type == "success"){
                                            $.messager.alert("消息提醒","修改成功!","info");
                                            //关闭窗口
                                            $("#editDialog").dialog("close");

                                            //重新刷新页面数据
                                            $('#dataList').datagrid("reload");
                                            $('#dataList').datagrid("uncheckAll");

                                        } else{
                                            $.messager.alert("消息提醒",data.msg,"warning");
                                            return;
                                        }
                                    }
                                });
                            }
                        }
                    },
                ],
                onBeforeOpen: function(){
                    var selectRow = $("#dataList").datagrid("getSelected");
                    //设置值
                    $("#edit-id").val(selectRow.id);
                    $("#edit_name").textbox('setValue', selectRow.name);
                    $("#edit_teacherId").combobox('setValue', selectRow.teacherId);
                    $("#edit_courseDate").textbox('setValue', selectRow.courseDate);
                    $("#edit_courseLocation").textbox('setValue', selectRow.courseLocation);
                    $("#edit_selectedNum").textbox('setValue', selectRow.selectedNum);
                    $("#edit_maxNum").textbox('setValue', selectRow.maxNum);
                    // $("#edit_admissionTime").textbox('setValue', selectRow.admissionTime);
                }
            });


            //搜索按钮
            $("#search-btn").click(function(){
                $('#dataList').datagrid('reload',{
                    name:$("#search-name").textbox('getValue'),
                    teacherId:$("#search-teacher-id").combobox('getValue')
                });
            });
        });

    </script>
</head>
<body>
<!-- 数据列表 -->
<table id="dataList" cellspacing="0" cellpadding="0">

</table>
<!-- 工具栏 -->
<div id="toolbar">
    <c:if test="${userType == 1}">
        <div style="float: left;"><a id="add" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加</a></div>
        <div style="float: left;" class="datagrid-btn-separator"></div>
        <div style="float: left;"><a id="edit" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">修改</a></div>
        <div style="float: left;" class="datagrid-btn-separator"></div>
    </c:if>

    <div>
        <c:if test="${userType == 1}">
            <a id="delete" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-some-delete',plain:true">删除</a>
        </c:if>
        <c:if test="${userType == 2}">
            <a id="selectCourse" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">选课</a>
        </c:if>

        课程名：<input id="search-name" class="easyui-textbox" />
        任课老师：
        <select id="search-teacher-id" class="easyui-combobox" style="width: 150px;">
            <option value="">全部</option>
            <c:forEach items="${ teacherList}" var="teacher">
                <option value="${teacher.id }">${teacher.name }</option>
            </c:forEach>
        </select>
        <a id="search-btn" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">搜索</a>
    </div>
</div>

<!-- 添加窗口 -->
<div id="addDialog" style="padding: 10px;">

    <form id="addForm" method="post">
        <table id="addTable2" cellpadding="8">
            <tr >
                <td>课程姓名:</td>
                <td>
                    <input id="add_name"  class="easyui-textbox" style="width: 200px; height: 30px;" type="text" name="name" data-options="required:true, missingMessage:'请填写课程姓名'"  />
                </td>
            </tr>
            <%--<tr >--%>
                <%--<td>登录密码:</td>--%>
                <%--<td>--%>
                    <%--<input id="add_password"  class="easyui-textbox" style="width: 200px; height: 30px;" type="password" name="password" data-options="required:true, missingMessage:'请填写登录密码'"  />--%>
                <%--</td>--%>
            <%--</tr>--%>
            <tr >
                <td>任课老师:</td>
                <td>
                    <select id="add_teacherId"  class="easyui-combobox" style="width: 200px;" name="teacherId" data-options="required:true, missingMessage:'请选择任课老师'">
                        <c:forEach items="${ teacherList}" var="teacher">
                            <option value="${teacher.id }">${teacher.name }</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr >
                <td>课程时间:</td>
                <td>
                    <%--<select id="add_sex"  class="easyui-combobox" style="width: 200px;" name="sex" data-options="required:true, missingMessage:'请选择课程性别'">--%>
                        <%--<option value="男">男</option>--%>
                        <%--<option value="女">女</option>--%>
                    <%--</select>--%>
                        <input id="add_courseDate"  class="easyui-textbox" style="width: 200px; height: 30px;" type="text" name="courseDate" data-options="required:true, missingMessage:'请填写课程时间'"  />
                </td>
            </tr>
            <tr>
                <td>上课地点:</td>
                <td>
                    <input id="add_courseLocation"  class="easyui-textbox" style="width: 200px; height: 30px;" type="text" name="courseLocation" data-options="required:true, missingMessage:'请填写上课地点'"  />
                </td>
            </tr>
            <tr>
                <td>已选课人数:</td>
                <td>
                    <input id="add_selectedNum"  class="easyui-textbox" style="width: 200px; height: 30px;" type="text" name="selectedNum" data-options="required:true, missingMessage:'请填写已选课人数'"  />
                </td>
            </tr>
            <tr>
                <td>最大选课人数:</td>
                <td>
                    <input id="add_maxNum"  class="easyui-textbox" style="width: 200px; height: 30px;" type="text" name="maxNum" data-options="required:true, missingMessage:'请填写最大选课人数'"  />
                </td>
            </tr>
            <%--<tr>--%>
                <%--<td>入学时间:</td>--%>
                <%--<td><input type="text" style="width: 200px; height: 30px;" class="easyui-datebox" id="add_admissionTime" name="admissionTime"> </td>--%>
            <%--</tr>--%>
        </table>
    </form>
</div>


<!-- 修改窗口 -->
<div id="editDialog" style="padding: 10px">
    <form id="editForm" method="post">
        <input type="hidden" name="id" id="edit-id">
        <table id="editTable2" cellpadding="8">
            <tr >
                <td>课程名:</td>
                <td>
                    <input id="edit_name"  class="easyui-textbox" style="width: 200px; height: 30px;" type="text" name="name" data-options="required:true, missingMessage:'请填写课程姓名'"  />
                </td>
            </tr>
            <tr >
                <td>任课老师:</td>
                <td>
                    <select id="edit_teacherId"  class="easyui-combobox" style="width: 200px;" name="teacherId" data-options="required:true, missingMessage:'请选择任课老师'">
                        <c:forEach items="${ teacherList}" var="teacher">
                            <option value="${teacher.id }">${teacher.name }</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <%--<tr >--%>
                <%--<td>登录密码:</td>--%>
                <%--<td>--%>
                    <%--<input id="edit_password"  class="easyui-textbox" style="width: 200px; height: 30px;" type="password" name="password" data-options="required:true, missingMessage:'请填写登录密码'"  />--%>
                <%--</td>--%>
            <%--</tr>--%>
            <%--<tr >--%>
                <%--<td>所属班级:</td>--%>
                <%--<td>--%>
                    <%--<select id="edit_clazzId"  class="easyui-combobox" style="width: 200px;" name="clazzId" data-options="required:true, missingMessage:'请选择所属班级'">--%>
                        <%--<c:forEach items="${ teacherList}" var="teacher">--%>
                            <%--<option value="${teacher.id }">${teacher.name }</option>--%>
                        <%--</c:forEach>--%>
                    <%--</select>--%>
                <%--</td>--%>
            <%--</tr>--%>
            <tr >
                <td>课程时间:</td>
                <td>
                    <%--<select id="add_sex"  class="easyui-combobox" style="width: 200px;" name="sex" data-options="required:true, missingMessage:'请选择课程性别'">--%>
                    <%--<option value="男">男</option>--%>
                    <%--<option value="女">女</option>--%>
                    <%--</select>--%>
                    <input id="edit_courseDate"  class="easyui-textbox" style="width: 200px; height: 30px;" type="text" name="courseDate" data-options="required:true, missingMessage:'请填写课程时间'"  />
                </td>
            </tr>
            <tr>
                <td>上课地点:</td>
                <td><input id="edit_courseLocation" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="courseLocation" data-options="multiline:true"  /></td>
            </tr>
            <tr>
                <td>已选课人数:</td>
                <td><input id="edit_selectedNum" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="selectedNum" data-options="multiline:true"  /></td>
            </tr>
            <tr>
                <td>最大选课人数:</td>
                <td><input id="edit_maxNum" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="maxNum" data-options="multiline:true"  /></td>
            </tr>
            <%--<tr>--%>
                <%--<td>入学时间:</td>--%>
                <%--<td><input type="text" style="width: 200px; height: 30px;" class="easyui-datebox" id="edit_admissionTime" name="admissionTime"> </td>--%>
            <%--</tr>--%>
        </table>
    </form>
</div>
<!-- 提交表单处理iframe框架 -->
<iframe id="photo_target" name="photo_target" onload="uploaded(this)"></iframe>
</body>
</html>