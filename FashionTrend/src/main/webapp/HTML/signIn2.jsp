	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
<title>디옷토 :: 로그인</title>
<meta charset="utf-8" />
<!-- <link type="text/css" href="../CSS/join.css" rel="stylesheet" /> -->
<link rel="stylesheet" href="../CSS/join.css">
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
<style>
.policy_wrap {font-size:12px;}
.policy_top {border-bottom:1px solid #dcdcdc;}
.policy_top > p {line-height:18px;}
.policy_wrap div.list_wrap {padding:30px 0; overflow:hidden;}
.policy_wrap div.list_wrap ul {display:inline; float:left; width:440px; padding-left:2px; color:#424242;}
.policy_wrap div.list_wrap ul li {height:22px;}
.policy_wrap div.list_wrap ul li span {display:inline-block;}
.policy_wrap div.list_wrap ul li span:first-child {font-weight:bold;}
.policy_wrap div.list_wrap ul li span.tit {width:60px;}
.policy_wrap > p {line-height:18px;}
.policy_wrap > p.tit1 {margin-top:30px; color:#333333; font-weight:bold;}
.policy_wrap > p.tit2 {margin-top:10px; font-weight:bold; color:#666666;}
.policy_wrap > p.txt1 {margin-bottom:20px; color:#666666;}
</style>
</head>
<body >

<div class="header_wrap">
	<div class="header">
		<h1><a href="index.jsp"><img src="../images/member/logo.png" alt=디옷토리" width="150px" /></a> <img src="../images/member/member_tit_join.png" alt="회원가입" /></h1>
		<div>
			<span><a href="/">홈</a></span>

		</div>
	</div>
</div>
<div class="login_wrap">
	<div class="title"><img src="../images/member/login_tit.gif" alt="" /></div>
	<div class="login_inwrap">
		<div class="login_input_wrap">
			<form action="/login_before.php" method="post" name="fm" style="margin:0px" onsubmit="return check_login();">
			<input type="hidden" name="process" value="ok">
			<input type="hidden" name="url" value="">
				<div class="input"><span class="tit"><img src="../images/member/join_id.gif" alt="아이디" /></span><span class="data"><input type="text" name="id" value="" tabindex="1" /><span></div>
				<div class="input"><span class="tit"><img src="../images/member/join_pw.gif" alt="비밀번호" /></span><span class="data"><input type="password" name="passwd" value="" tabindex="2" /><span></div>
				<div class="keep"><input type="checkbox" name="rememberID" id="rememberID" value="Y"  /> <span><label>ID 저장</label></span></div>
				<div class="btn"><input type="image" alt="로그인" src="../images/member/btn_befor_login.gif" /></div>
			</form>
		</div>
			<span><a href="signIn.jsp">회원가입</a></span>
		</div>
						
				<div class="login_ad" id="login_banner1" style="display:block" style=""><a href="/banner.php?t=banner&amp;no=27&amp;url=http%3A%2F%2Fwww.fashionn.com" target="_top"><img src="http://www.fashionn.com/files/banner/20131203_903651.gif" border="0" alt="" /></a></div>
			</div><!-- // login_inwrap -->
	<div class="login_btm">
		<div>Copyright ⓒ 디옷토리 All rights reserved.</div>
		<div>
			<span><a href="/member/policy1.php" target="_blank" title="새창">이용약관</a></span>
			<span><a href="/member/policy2.php" target="_blank" title="새창">개인정보취급방침</a></span>
			<span><a href="#">이메일 무단수집거부</a></span>
		</div>
	</div>
</div>
</body>
</html>

<script type="text/javascript">
	function check_login() {
		with(document.fm) {
			if(!id.value) {
				alert("아이디를 입력 바랍니다.");
				id.focus();
				return false;
			}

			if(!passwd.value) {
				alert("비밀번호를 입력 바랍니다.");
				passwd.focus();
				return false;
			}

			return true;
		}
	}
</script>