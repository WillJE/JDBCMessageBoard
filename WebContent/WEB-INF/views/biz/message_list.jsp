<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%String path = request.getContextPath(); %>
<html>
	<!-- 该页面前端代码系复制粘贴 -->
    <head>
        <meta charset="UTF-8">
        <title>留言板</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
        <script type="text/javascript">        	
            function submitMessageForm(flag) {
                if ('first' == flag) {
                    document.getElementById('page').value = 1;
                } else if ('pre' == flag) {
                    var current = Number(document.getElementById('page').value);
                    if (current > 1) {
                        document.getElementById('page').value = current - 1;
                    }
                } else if ('next' == flag) {
                    var current = Number(document.getElementById('page').value);
                    var last = Number(document.getElementById('last').value);
                    if (current < last) {
                        document.getElementById('page').value = current + 1;
                    }
                } else if ('last' == flag) {
                    var last = Number(document.getElementById('last').value);
                    document.getElementById('page').value = last < 1 ? 1 : last;
                }
                document.getElementById('messageForm').submit();
            }
        </script>
    </head>

    <body>
        <header>
            <div class="container">
                <% if (null != session.getAttribute("user")) {%>
                    <nav>
                        <a href="<%=path %>/my/message/list.do">我的留言</a>
                    </nav>
                    <nav>
                        <a href="<%=path %>/userInfo.do">我的信息</a>
                    </nav>
                <%} else { %>
                    <nav>
                        <a href="${pageContext.request.contextPath}/login.do">登录</a>
                        <a href="<%=path %>/regPrompt.do">注册</a>
                    </nav>
                <% } %>
            </div>
        </header>
        <section class="banner">
            <div class="container">
                <div>
                    <h1>留言板</h1>
                    <p>JSP+Servlet+JDBC留言板案例。该案例系慕课网Java职业路径4--数据库与MyBatis入门实战案例 </p>
                </div>
            </div>
        </section>
        <section class="main">
            <div class="container">
                <c:forEach items="${messages}" var="msg">
                    <div class="alt-item">
                        <div class="alt-head">
                            <div class="alt-info">
                                <span>作者：<a href="">${msg.username}</a></span>
                                <span>时间：<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${msg.createTime}"/></span>
                            </div>
                        </div>
                        <div class="alt-content">
                            <h3>${msg.title}</h3>
                            <p>${msg.content}</p>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </section>
        <section class="page">
            <div class="container">
                <% if (null != request.getSession().getAttribute("user")) {%>
                    <div id="fatie">
                        <a href="<%=path %>/addMessagePrompt.do"><button>点我留言</button></a>
                    </div>
                <%} else { %>
                    <div id="fatie">
                        请<a href="${pageContext.request.contextPath}/login.do"><button>登录</button></a>后留言
                    </div>
                <% } %>


                <div id="pagefy">
                    <ul>
                        <form id="messageForm" action="<%=path %>/message/list.do" method="post">
                            <input type="hidden" id="page" name="page" value="${page}">
                            <input type="hidden" id="last" name="last" value="${last}">
                            <li><a href="javascript:void(0)" onclick="submitMessageForm('first')">首页</a></li>
                            <li><a href="javascript:void(0)" onclick="submitMessageForm('pre')">上一页</a></li>
                            <li><a href="javascript:void(0)">当前第${page}页</a></li>
                            <li><a href="javascript:void(0)" onclick="submitMessageForm('next')">下一页</a></li>
                            <li><a href="javascript:void(0)" onclick="submitMessageForm('last')">尾页</a></li>
                        </form>
                    </ul>
                </div>
            </div>
        </section>
        <footer>
            copy@will
        </footer>
    </body>
</html>