<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <title>学生选课成绩列表</title>
    <link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="../easyui/css/demo.css">
    <script type="text/javascript" src="../easyui/jquery.min.js"></script>
    <script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../easyui/js/validateExtends.js"></script>
    <script type="text/javascript">
        var optionalCourseList = ${ optionalCourseListJson};
        var studentList = ${ studentListJson};
        var clazzList = ${clazzListJson};
        $(function() {
            var table;

            //datagrid初始化
            $('#dataList').datagrid({
                title:'选课成绩信息列表',
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
                    {field:'sn',title:'学生学号',width:150, sortable: true,
                        formatter:function(value,index,row){
                            // var sn = 'sn';
                            var s = index.student;
                            // var name = s.name;
                            // alert("学生姓名是"+s["name"]);

                            // 这里参数顺序应该是 value row index
                            for(var i in s){
                                if(i === 'sn'){
                                    // alert("属性是"+i);
                                    // alert("属性值是"+s[i]);
                                    return s[i];
                                }

                            }
                            // if(index.student[sn]){
                            //     return index.student[sn];
                            // } else {
                            //     return value;
                            // }
                            // return s.sn;

                        }
                    },
                    {field:'studentName',title:'学生姓名',width:150, sortable: true,
                        formatter:function(value,index,row){
                            var s = index.student;


                            // 这里参数顺序应该是 value row index
                            for(var i in s){
                                if(i === 'name'){
                                    return s[i];
                                }

                            }
                            return value;
                        }
                    },
                    {field:'clazzId',title:'班级',width:150, sortable: true,
                    formatter:function(value,index,row){
                        var s = index.student;
                        // 这里参数顺序应该是 value row index
                        for(var i in s){
                            if(i === 'clazzId'){
                                var clazzId =  s[i];
                            }
                        }
                        for(var i=0;i<clazzList.length;i++){
                            if(clazzList[i].id == clazzId){
                                return clazzList[i].name;
                            }
                        }
                        return value;
                    }
                    },
                    {field:'CourseName',title:'课程名称',width:150, sortable: true,
                        formatter:function(value,index,row){
                            var s = index.optionalCourse;


                            // 这里参数顺序应该是 value row index
                            for(var i in s){
                                if(i === 'name'){
                                    return s[i];
                                }

                            }
                            return value;
                        }
                    },
                    {field:'score',title:'分数',width:150, sortable: true,
                        //可以省略
                        formatter:function(value,index,row){
                            return index.score;
                        }
                    },
                    {field:'gpa',title:'绩点',width:150, sortable: true,
                        formatter:function(value,index,row){
                            return index.gpa;
                        }
                    },

                    // {field:'student',title:'班级',width:150, sortable: true,
                    //     formatter:function(value,index,row){
                    //         // alert(value.toString());
                    //         return row.student.clazzId;
                    //     }
                    // },
                    // {field:'student',title:'学生姓名',width:150, sortable: true,
                    //     formatter:function(value,index,row){
                    //         // alert(value.toString());
                    //         return student.text;
                    //     }
                    // },
                    // {field:'student',title:'学生姓名',width:150, sortable: true,
                    //     // formatter:function(value,index,row){
                    //     //     // alert(value.toString());
                    //     //     return value.sn;
                    //     // }
                    // },
                    // {field:'studentId',title:'学生',width:150, sortable: true,
                        // formatter:function(value,index,row){
                        //     // alert(studentId);
                        //     for(var i=0;i<studentList.length;i++){
                        //         if(studentList[i].id == value){
                        //             return studentList[i].name;
                        //         }
                        //     }
                        //     return value;
                        // }
                    // },
                    // {field:'optionalCourseId',title:'课程',width:150, sortable: true,
                    //     formatter:function(value,index,row){
                    //         for(var i=0;i<optionalCourseList.length;i++){
                    //             if(optionalCourseList[i].id == value){
                    //                 return optionalCourseList[i].name;
                    //             }
                    //         }
                    //         return value;
                    //     }
                    // },
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
            // $("#add").click(function(){
            //     table = $("#addTable");
            //     $("#addDialog").dialog("open");
            // });

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
                    $.messager.confirm("消息提醒", "确定删除成绩信息？", function(r){
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

            //设置添加窗口
            // $("#addDialog").dialog({
            //     title: "添加选课",
            //     width: 450,
            //     height: 650,
            //     iconCls: "icon-add",
            //     modal: true,
            //     collapsible: false,
            //     minimizable: false,
            //     maximizable: false,
            //     draggable: true,
            //     closed: true,
            //     buttons: [
            //         {
            //             text:'添加',
            //             plain: true,
            //             iconCls:'icon-add',
            //             handler:function(){
            //                 var validate = $("#addForm").form("validate");
            //                 if(!validate){
            //                     $.messager.alert("消息提醒","请检查你输入的数据!","warning");
            //                     return;
            //                 } else{
            //                     var data = $("#addForm").serialize();
            //                     $.ajax({
            //                         type: "post",
            //                         url: "add",
            //                         data: data,
            //                         dataType:'json',
            //                         success: function(data){
            //                             if(data.type == "success"){
            //                                 $.messager.alert("消息提醒","添加成功!","info");
            //                                 //关闭窗口
            //                                 $("#addDialog").dialog("close");
            //                                 //清空原表格数据
            //                                 // $("#add_name").textbox('setValue', "");
            //                                 // $("#add_password").textbox('setValue', "");
            //                                 //重新刷新页面数据
            //                                 $('#dataList').datagrid("reload");
            //
            //                             } else{
            //                                 $.messager.alert("消息提醒",data.msg,"warning");
            //                                 return;
            //                             }
            //                         }
            //                     });
            //                 }
            //             }
            //         },
            //     ],
            //     // onClose: function(){
            //     //     $("#add_name").textbox('setValue', "");
            //     //     $("#add_password").textbox('setValue', "");
            //     // }
            // });

            //编辑学生信息
            $("#editDialog").dialog({
                title: "修改学生信息",
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
                    // $("#edit_name").textbox('setValue', selectRow.name);
                    $("#edit_studentId").combobox('setValue', selectRow.studentId);
                    $("#edit_optionalCourseId").combobox('setValue', selectRow.optionalCourseId);
                    // $("#edit_sex").combobox('setValue', selectRow.sex);
                    // $("#edit_password").textbox('setValue', selectRow.password);
                    // $("#edit_mobile").textbox('setValue', selectRow.mobile);
                    // $("#edit_admissionTime").textbox('setValue', selectRow.admissionTime);
                }
            });


            // 搜索按钮
            $("#search-btn").click(function(){
                $('#dataList').datagrid('reload',{
                    // name:$("#search-name").textbox('getValue'),
                    studentId:$("#search-student-id").combobox('getValue'),
                    optionalCourseId:$("#search-optionalCourse-id").combobox('getValue')
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
        <%--<div style="float: left;"><a id="add" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加</a></div>--%>
        <%--<div style="float: left;" class="datagrid-btn-separator"></div>--%>
        <div style="float: left;"><a id="edit" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">修改</a></div>
        <div style="float: left;" class="datagrid-btn-separator"></div>
    </c:if>

    <div>
        <c:if test="${userType == 1}">
            <a id="delete" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-some-delete',plain:true">删除</a>


        <%--学生名：<input id="search-name" class="easyui-textbox" />--%>
        学生：
        <select id="search-student-id" class="easyui-combobox" style="width: 150px;">
            <option value="">全部</option>
            <c:forEach items="${ studentList}" var="student">
                <option value="${student.id }">${student.name }</option>
            </c:forEach>
        </select>
        课程：
        <select id="search-optionalCourse-id" class="easyui-combobox" style="width: 150px;">
            <option value="">全部</option>
            <c:forEach items="${ optionalCourseList}" var="optionalCourse">
                <option value="${optionalCourse.id }">${optionalCourse.name }</option>
            </c:forEach>
        </select>
        <a id="search-btn" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">搜索</a>
        </c:if>
    </div>
</div>

<!-- 添加窗口 -->
<%--<div id="addDialog" style="padding: 10px;">--%>

    <%--<form id="addForm" method="post">--%>
        <%--<table id="addTable2" cellpadding="8">--%>
            <%--&lt;%&ndash;<tr >&ndash;%&gt;--%>
                <%--&lt;%&ndash;<td>学生姓名:</td>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<td>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<input id="add_name"  class="easyui-textbox" style="width: 200px; height: 30px;" type="text" name="name" data-options="required:true, missingMessage:'请填写学生姓名'"  />&ndash;%&gt;--%>
                <%--&lt;%&ndash;</td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;</tr>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<tr >&ndash;%&gt;--%>
                <%--&lt;%&ndash;<td>登录密码:</td>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<td>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<input id="add_password"  class="easyui-textbox" style="width: 200px; height: 30px;" type="password" name="password" data-options="required:true, missingMessage:'请填写登录密码'"  />&ndash;%&gt;--%>
                <%--&lt;%&ndash;</td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;</tr>&ndash;%&gt;--%>
            <%--<tr >--%>
                <%--<td>学生:</td>--%>
                <%--<td>--%>
                    <%--<select id="add_studentId"  class="easyui-combobox" style="width: 200px;" name="studentId" data-options="required:true, missingMessage:'请选择老师'">--%>
                        <%--<c:forEach items="${ studentList}" var="student">--%>
                            <%--<option value="${student.id }">${student.name }</option>--%>
                        <%--</c:forEach>--%>
                    <%--</select>--%>
                <%--</td>--%>
            <%--</tr>--%>
                <%--<tr >--%>
                    <%--<td>课程:</td>--%>
                    <%--<td>--%>
                        <%--<select id="add_optionalCourseId"  class="easyui-combobox" style="width: 200px;" name="optionalCourseId" data-options="required:true, missingMessage:'请选择老师'">--%>
                            <%--<c:forEach items="${ optionalCourseList}" var="optionalCourse">--%>
                                <%--<option value="${optionalCourse.id }">${optionalCourse.name }</option>--%>
                            <%--</c:forEach>--%>
                        <%--</select>--%>
                    <%--</td>--%>
                <%--</tr>--%>
            <%--&lt;%&ndash;<tr >&ndash;%&gt;--%>
                <%--&lt;%&ndash;<td>学生性别:</td>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<td>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<select id="add_sex"  class="easyui-combobox" style="width: 200px;" name="sex" data-options="required:true, missingMessage:'请选择学生性别'">&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<option value="男">男</option>&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<option value="女">女</option>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;</select>&ndash;%&gt;--%>
                <%--&lt;%&ndash;</td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;</tr>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<tr>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<td>手机号:</td>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<td><input id="add_mobile" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="mobile" data-options="multiline:true"  /></td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;</tr>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<tr>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<td>入学时间:</td>&ndash;%&gt;--%>
                <%--&lt;%&ndash;<td><input type="text" style="width: 200px; height: 30px;" class="easyui-datebox" id="add_admissionTime" name="admissionTime"> </td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;</tr>&ndash;%&gt;--%>
        <%--</table>--%>
    <%--</form>--%>
<%--</div>--%>


<!-- 修改窗口 -->
<div id="editDialog" style="padding: 10px">
    <form id="editForm" method="post">
        <input type="hidden" name="id" id="edit-id">
        <table id="editTable2" cellpadding="8">、

            <tr >
                <td>分数:</td>
                <td>
                    <input id="edit_score"  class="easyui-textbox" style="width: 200px; height: 30px;" type="text" name="score" data-options="required:true, missingMessage:'请填写分数'"  />
                </td>
            </tr>
            <tr >
                <td>绩点:</td>
                <td>
                    <input id="edit_gpa"  class="easyui-textbox" style="width: 200px; height: 30px;" type="text" name="gpa" data-options="required:true, missingMessage:'请填写绩点'"  />
                </td>
            </tr>
            <%--<tr >--%>
                <%--<td>学生:</td>--%>
                <%--<td>--%>
                    <%--<select id="edit_teacherId"  class="easyui-combobox" style="width: 200px;" name="studentId" data-options="required:true, missingMessage:'请选择学生'">--%>
                        <%--<c:forEach items="${ studentList}" var="student">--%>
                            <%--<option value="${student.id }">${student.name }</option>--%>
                        <%--</c:forEach>--%>
                    <%--</select>--%>
                <%--</td>--%>
            <%--</tr>--%>
            <%--<tr >--%>
                <%--<td>课程:</td>--%>
                <%--<td>--%>
                    <%--<select id="edit_optionalCourseId"  class="easyui-combobox" style="width: 200px;" name="optionalCourseId" data-options="required:true, missingMessage:'请选择课程'">--%>
                        <%--<c:forEach items="${ optionalCourseList}" var="optionalCourse">--%>
                            <%--<option value="${optionalCourse.id }">${optionalCourse.name }</option>--%>
                        <%--</c:forEach>--%>
                    <%--</select>--%>
                <%--</td>--%>
            <%--</tr>--%>
            <%----%>
            <%--<tr >--%>
                <%--<td>学生姓名:</td>--%>
                <%--<td>--%>
                    <%--<input id="edit_name"  class="easyui-textbox" style="width: 200px; height: 30px;" type="text" name="name" data-options="required:true, missingMessage:'请填写学生姓名'"  />--%>
                <%--</td>--%>
            <%--</tr>--%>
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
                        <%--<c:forEach items="${ clazzList}" var="clazz">--%>
                            <%--<option value="${clazz.id }">${clazz.name }</option>--%>
                        <%--</c:forEach>--%>
                    <%--</select>--%>
                <%--</td>--%>
            <%--</tr>--%>
            <%--<tr >--%>
                <%--<td>学生性别:</td>--%>
                <%--<td>--%>
                    <%--<select id="edit_sex"  class="easyui-combobox" style="width: 200px;" name="sex" data-options="required:true, missingMessage:'请选择学生性别'">--%>
                        <%--<option value="男">男</option>--%>
                        <%--<option value="女">女</option>--%>
                    <%--</select>--%>
                <%--</td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td>手机号:</td>--%>
                <%--<td><input id="edit_mobile" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="mobile" data-options="multiline:true"  /></td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td>入学时间:</td>--%>
                <%--<td><input type="text" style="width: 200px; height: 30px;" class="easyui-datebox" id="edit_admissionTime" name="admissionTime"> </td>--%>
            <%--</tr>--%>
        </table>
    </form>
</div>
</body>
</html>