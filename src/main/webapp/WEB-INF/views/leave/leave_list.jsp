<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>请假信息列表</title>
	<link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../easyui/css/demo.css">
	<script type="text/javascript" src="../easyui/jquery.min.js"></script>
	<script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../easyui/js/validateExtends.js"></script>
	<script type="text/javascript">
        var studentList = ${studentListJson};
        $(function() {
            var table;

            //datagrid初始化
            $('#dataList').datagrid({
                title:'班级列表',
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
                    {field:'studentId',title:'学生姓名',width:200,
                        formatter:function(value,index,row){

                            for(var i=0;i<studentList.length;i++){
                                if(studentList[i].id == value){
                                    return studentList[i].name;
                                }
                            }
                            return value;
                        }
                    },
                    {field:'reason',title:'请假原因',width:400},
                    {field:'status',title:'状态',width:80,
                        formatter: function(value,row,index){
                            switch(row.status){
                                case 0:{
                                    return '等待审核';
                                }
                                case 1:{
                                    return '审核通过';
                                }
                                case -1:{
                                    return '审核不通过';
                                }
                            }
                        }
                    },
                    {field:'remark',title:'批复内容',width:400},
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

            //审核
            $("#check").click(function(){
                table = $("#checkTable");
                var selectRows = $("#dataList").datagrid("getSelections");
                if(selectRows.length != 1){
                    $.messager.alert("消息提醒", "请选择一条数据进行操作!", "warning");
                } else{
                    $("#checkDialog").dialog("open");
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
                    $.messager.confirm("消息提醒", "确定删除该请假信息？", function(r){
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
            $("#addDialog").dialog({
                title: "添加班级",
                width: 450,
                height: 400,
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
                        iconCls:'icon-user_add',
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
                                            $.messager.alert("消息提醒","请假成功!","info");
                                            //关闭窗口
                                            $("#addDialog").dialog("close");
                                            //清空原表格数据
                                            $("#add_name").textbox('setValue', "");
                                            $("#add_remark").textbox('setValue', "");
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
                    $("#add_remark").textbox('setValue', "");
                }
            });

            //编辑班级信息
            $("#editDialog").dialog({
                title: "修改班级信息",
                width: 450,
                height: 400,
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
                    $("#edit_professionId").combobox('setValue', selectRow.professionId);
                }
            });


            //审核请假信息
            $("#checkDialog").dialog({
                title: "审核请假信息",
                width: 450,
                height: 400,
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
                            var validate = $("#checkForm").form("validate");
                            if(!validate){
                                $.messager.alert("消息提醒","请检查你输入的数据!","warning");
                                return;
                            } else{

                                var data = $("#checkForm").serialize();

                                $.ajax({
                                    type: "post",
                                    url: "check",
                                    data: data,
                                    dataType:'json',
                                    success: function(data){
                                        if(data.type == "success"){
                                            $.messager.alert("消息提醒","审核成功!","info");
                                            //关闭窗口
                                            $("#checkDialog").dialog("close");

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
                    $("#check-id").val(selectRow.id);
                    // $("#check_status").textbox('setValue', selectRow.status);
                    // $("#check_remark").combobox('setValue', selectRow.remark);
                }
            });


            //搜索按钮
            $("#search-btn").click(function(){
                $('#dataList').datagrid('reload',{
                    // name:$('#search-name').textbox('getValue'),
                    status:$("#search-leave-id").combobox('getValue')
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
		<c:if test="${userType == 2}">
		<div style="float: left;"><a id="add" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">请假</a></div>
		<div style="float: left;" class="datagrid-btn-separator"></div>
		<div style="float: left;"><a id="edit" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">修改</a></div>
		<div style="float: left;" class="datagrid-btn-separator"></div>
		</c:if>
	<div>
		<c:if test="${userType == 1}">
			<div style="float: left;"><a id="check" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">审核</a></div>
			<div style="float: left;" class="datagrid-btn-separator"></div>
		</c:if>
		<a id="delete" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-some-delete',plain:true">删除</a>

		审核状态：
		<select id="search-leave-id" class="easyui-combobox" style="width: 150px;" >
			<option value="">全部</option>
			<option value="1">审核通过</option>
			<option value="-1">审核不通过</option>
			<option value="0">等待审核</option>
		</select>
		<a id="search-btn" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">搜索</a>
	</div>
</div>






<!-- 添加窗口 -->
<div id="addDialog" style="padding: 10px;">
	<form id="addForm" method="post">
		<table id="addTable" cellpadding="8">
			<tr>
				<td>请假原因:</td>
				<td>
					<textarea id="add_reason" name="reason" style="width: 300px; height: 160px;" class="easyui-textbox" data-options="multiline:true,required:true, missingMessage:'请假原因不能为空'" ></textarea>
				</td>
			</tr>

		</table>
	</form>
</div>


<!-- 修改窗口 -->
<div id="editDialog" style="padding: 10px">
	<form id="editForm" method="post">
		<input type="hidden" name="id" id="edit-id">
		<table id="editTable" border=0 cellpadding="8" >
			<tr>
				<td>请假原因:</td>
				<td>
					<textarea id="edit_reason" name="reason" style="width: 300px; height: 160px;" class="easyui-textbox" data-options="multiline:true,required:true, missingMessage:'请假原因不能为空'" ></textarea>
				</td>
			</tr>

		</table>
	</form>
</div>

<!-- 审核窗口 -->
<div id="checkDialog" style="padding: 10px">
	<form id="checkForm" method="post">
		<input type="hidden" name="id" id="check-id">
		<table id="checkTable" border=0 cellpadding="8" >
			<tr>
				<td style="width:60px">状态:</td>
				<td colspan="3">
					<select id="check_statusList" style="width: 300px; height: 30px;" class="easyui-combobox" name="status" data-options="required:true, missingMessage:'请选择状态'" >
						<option value="1">审核通过</option>
						<option value="-1">审核不通过</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>批复内容:</td>
				<td>
					<textarea id="check_remark" name="remark" style="width: 300px; height: 160px;" class="easyui-textbox" data-options="multiline:true,required:true, missingMessage:'请假原因不能为空'" ></textarea>
				</td>
			</tr>

		</table>
	</form>
</div>


</body>
</html>