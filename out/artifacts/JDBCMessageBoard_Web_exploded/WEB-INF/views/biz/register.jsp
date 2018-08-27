<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	String path = request.getContextPath();					//项目名 
%>
<html>
	<head>
		<title>注册页面</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
		<style type="text/css">
			.login {
				height: auto;
				width: 450px;
			}
		</style>
		<script type="text/javascript">
            function changeImg() {            
				return true;
            }

            function getCookie(cookie_name) {
                var allCookies = document.cookie;
                var cookie_pos = allCookies.indexOf(cookie_name);   //如果找到了索引，就代表cookie存在
                if (cookie_pos != -1) {
                    cookie_pos += cookie_name.length + 1;
                    var cookie_end = allCookies.indexOf(";", cookie_pos);
                    if (cookie_end == -1) {
                        cookie_end = allCookies.length;
                    }
                    return unescape(allCookies.substring(cookie_pos, cookie_end));
                }
                return null;
            }
		</script>
	</head>
	<body>
		<div class="login">
			<div class="header">
				<h1>					
					<a href="<%=path %>/login.do" style="color: #000;">登录</a>
					<a href="<%=path %>/regPrompt.do" style="color: #2C689B;">注册</a>
				</h1>
				<button></button>
			</div>
			<form action="<%=path %>/reg.do" method="post">
				<div class="name">
					<input type="text" id="name" name="username" placeholder="请输入用户名">					
					<p></p>
				</div>				
				<div class="pwd">
					<input type="password" id="pwd" name="password" placeholder="6-16位密码，区分大小写，不能用空格">
					<p></p>
				</div>				
				<div class="name">
					<input type="text"  name="realName" placeholder="请输入你的姓名">					
					<p></p>
				</div>
				<div class="name">
					<input type="text"  name="birthday" placeholder="请输入你的生日(格式:yyyy-MM-dd)">					
					<p></p>
				</div>		
				<div class="name">
					<input type="text"  name="phone" placeholder="请输入你的手机号码">					
					<p></p>
				</div>
				<div class="name">
					<input type="text"  name="address" placeholder="请输入你的住址">					
					<p></p>
				</div>
				<div class="btn-red">
					<input  type="submit" value="注册" id="login-btn">
				</div>
			</form>
		</div>
	</body>
</html>