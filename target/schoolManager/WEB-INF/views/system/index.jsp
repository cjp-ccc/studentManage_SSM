<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>学生信息管理系统 管理员后台</title>
    <link rel="shortcut icon" href="favicon.ico"/>
	<link rel="bookmark" href="favicon.ico"/>
    <link rel="stylesheet" type="text/css" href="../easyui/css/default.css" />
    <link rel="stylesheet" type="text/css" href="../easyui/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="../easyui/themes/icon.css" />
    <script type="text/javascript" src="../easyui/jquery.min.js"></script>
    <script type="text/javascript" src="../easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src='../easyui/js/outlook2.js'> </script>
    <script type="text/javascript">
	 var _menus = {"menus":[
                        <c:if test="${userType == 1}">
						{"menuid":"1","icon":"","menuname":"管理员管理",
							"menus":[
									{"menuid":"11","menuname":"管理员列表","icon":"icon-user-teacher","url":"../admin/list"}
								]
						},
                        </c:if>
						{"menuid":"3","icon":"","menuname":"学院信息管理",
							"menus":[
									{"menuid":"31","menuname":"学院列表","icon":"icon-house","url":"../academy/list"},
								]
						},
                        {"menuid":"5","icon":"","menuname":"专业信息管理",
                            "menus":[
                                     {"menuid":"51","menuname":"专业列表","icon":"icon-house","url":"../profession/list"},
                                ]
                        },
						{"menuid":"4","icon":"","menuname":"班级信息管理",
							"menus":[
									{"menuid":"41","menuname":"班级列表","icon":"icon-house","url":"../clazz/list"},
								]
						},
						{"menuid":"2","icon":"","menuname":"学生信息管理",
							"menus":[
									 {"menuid":"21","menuname":"学生列表","icon":"icon-user-student","url":"../student/list"},
                                     <c:if test="${userType == 1}">
                                      {"menuid":"22","menuname":"学生选课列表","icon":"icon-book-open","url":"../selectedCourse/list"},
                                     </c:if>
                                    <c:if test="${userType == 2}">
                                    {"menuid":"23","menuname":"选课信息","icon":"icon-book-open","url":"../sct/list"},
                                    </c:if>
                                    {"menuid":"24","menuname":"选课成绩","icon":"icon-book-open","url":"../grade/list"},
								]
						},
                        {"menuid":"6","icon":"","menuname":"教师信息管理",
                            "menus":[
                                {"menuid":"61","menuname":"教师列表","icon":"icon-user-teacher","url":"../teacher/list"},
                                ]
                        },
                        {"menuid":"7","icon":"","menuname":"课程信息管理",
                            "menus":[
                                {"menuid":"71","menuname":"课程列表","icon":"icon-book-open","url":"../course/list"},
                                {"menuid":"72","menuname":"学生可选课信息列表","icon":"icon-book-open","url":"../optionalCourse/list"},
                                ]
                        },
                        {"menuid":"8","icon":"","menuname":"教师可授课信息管理",
                            "menus":[
                                {"menuid":"81","menuname":"教师可授课信息列表","icon":"icon-book-open","url":"../courseTeacher/list"},
                                ]
                        },
                        {"menuid":"9","icon":"","menuname":"学生签到信息管理",
                            "menus":[
                            {"menuid":"91","menuname":"签到信息列表","icon":"icon-book-open","url":"../attendance/list"},
                            ]
                        },
                        {"menuid":"10","icon":"","menuname":"请假信息管理",
                            "menus":[
                            {"menuid":"101","menuname":"请假信息列表","icon":"icon-book-open","url":"../leave/list"},
                                ]
                        },

				]};


    </script>

</head>
<body class="easyui-layout" style="overflow-y: hidden"  scroll="no">
	<noscript>
		<div style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">
		    <img src="images/noscript.gif" alt='抱歉，请开启脚本支持！' />
		</div>
	</noscript>
    <div region="north" split="true" border="false" style="overflow: hidden; height: 30px;
        background:  #7f99be;
        line-height: 20px;color: #fff; font-family: Verdana, 微软雅黑,黑体">
        <span style="float:right; padding-right:20px;" class="head"><span style="color:red; font-weight:bold;">${user.name}&nbsp;</span>您好&nbsp;&nbsp;&nbsp;<a href="login_out" id="loginOut">安全退出</a></span>
        <span style="padding-left:10px; font-size: 16px; ">【陈剑平】学生信息管理系统</span>
    </div>
    <div region="south" split="true" style="height: 30px; background: #D2E0F2; ">
        <div class="footer">Copyright &copy; SWU By 陈剑平</div>
    </div>
    <div region="west" hide="true" split="true" title="导航菜单" style="width:180px;" id="west">
	<div id="nav" class="easyui-accordion" fit="true" border="false">
		<!--  导航内容 -->
	</div>
	
    </div>


    <%--这里引入 欢迎页面--%>
    <div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden">
        <div id="tabs" class="easyui-tabs"  fit="true" border="false" >
			<jsp:include page="welcome.jsp" />
		</div>
    </div>
	
</body>
</html>