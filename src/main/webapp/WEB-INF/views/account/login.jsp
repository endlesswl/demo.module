<%@include file="/includes/common/taglibs.jsp"%>
<%@ page language="java" import="com.lingcaibao.weixin.user.entity.*" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title>零彩宝微信管理后台登录</title>

		<meta name="description" content="User login page" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

		<!-- bootstrap & fontawesome -->
		<link rel="stylesheet" href="${ctx}/assets/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${ctx}/assets/css/font-awesome.min.css" />
		<!-- text fonts 
		<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400,300" />
		-->
		<!-- ace styles -->
		<link rel="stylesheet" href="${ctx}/assets/css/ace.min.css" />

		<!--[if lte IE 9]>
			<link rel="stylesheet" href="${ctx}/assets/css/ace-part2.min.css" />
		<![endif]-->
		<link rel="stylesheet" href="${ctx}/assets/css/ace-rtl.min.css" />

		<!--[if lte IE 9]>
		  <link rel="stylesheet" href="${ctx}/assets/css/ace-ie.min.css" />
		<![endif]-->

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

		<!--[if lt IE 9]>
		<script src="assets/js/html5shiv.js"></script>
		<script src="assets/js/respond.min.js"></script>
		<![endif]-->
		<script type="text/javascript" src='${ctx}/jQuery-Validation/js/jquery-1.8.2.min.js'></script>
		
		<link rel="stylesheet" href="${ctx}/jQuery-Validation/css/validationEngine.jquery.css" type="text/css"/>
		
		<!-- page specific plugin scripts -->
		<script src="${ctx}/jQuery-Validation/js/languages/jquery.validationEngine-zh_CN.js"></script>
		<script src="${ctx}/jQuery-Validation/js/jquery.validationEngine.js"></script>
		<script type="text/javascript">
			function ajaxValidationCallback(status, form, json, options){
				console.log(status)
				console.log(form)
				console.log(json)
				console.log(options)
				if (console) 
				if (status === true) {
					alert("the form is valid!");
					// uncomment these lines to submit the form to form.action
					// form.validationEngine('detach');
					// form.submit();
					// or you may use AJAX again to submit the data
				}
			}
			$(function($) {
				 $("#login_form").validationEngine({
					 	maxErrorsPerField: 1 ,//这个参数是，设置为 1 时，就不会出现 3 个提示，而只会出现 1 个提示。
					 	//showOneMessage:true,//该参数的功能是，在提交验证时，只会显示第一个输入框的错误，后面的都不会显示，和 maxErrorsPerField 结合使用，超级赞！
					 	addSuccessCssClassToField: "success" ,//当验证通过或不通过时，给元素增加的样式。通过增加样式来让元素更醒目的提示，也是不错的体验。
					 	addFailureCssClassToField: "failure" ,
					 	autoHidePrompt: true ,//自动隐藏提示信息，以及设置延时多久自动隐藏
					    autoHideDelay: 5000 ,//
					    autoPositionUpdate: true ,//自动调整提示信息。使用后，当窗口大小变化时，会自动调整提示信息的位置，对于不是固定布局的页面很有用。
					   // addPromptClass: "" ,//给提示信息的元素增加样式。
					   // promptPosition:"",//提示位置
					   // ajaxFormValidation: true,
					   // onAjaxFormComplete: ajaxValidationCallback,//ajax提交form
						'custom_error_messages': {
							"#username":{
									'required': {
										'message': "请先输入您的用户名"
									}
						//			,
						//			'custom[phone]':{
						//				'message': "无效的手机号"
						//			}
								},
							'#password':{
									'required':{
											'message': "请输入您的密码"
									 }
							}
						}
					});
			})
	</script>		
	</head>

	<body class="login-layout">
		<div class="main-container">
			<div class="main-content">
				<div class="row">
					<div class="col-sm-10 col-sm-offset-1">
						<div class="login-container">
							<div class="center">
								<h1>
									<i class="ace-icon fa fa-leaf green"></i>
									<span class="red">LCB</span>
									<span class="white" id="id-text2">Weixin Application</span>
								</h1>
								<h4 class="blue" id="id-company-text">&copy; <a href="http://www.lingcaibao.com">LCB</a></h4>
							</div>

							<div class="space-6"></div>

							<div class="position-relative">
								<div id="login-box" class="login-box visible widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header blue lighter bigger">
												<i class="ace-icon fa fa-coffee green"></i>
													登录LCB微信管理平台
											</h4>

											<div class="space-6"></div>

											<form  id="login_form" action="login" method="post">
												<fieldset>
													<label style="color:#ee0101">
														<c:if test="${not empty username}">
															用户名或密码不正确
														</c:if>
													</label>
												
												
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" class="validate[required] form-control" 
															name="username" id="username" value="${username}"placeholder="用户名" data-prompt-position="inline"/>
															<i class="ace-icon fa fa-user">
															</i>
														</span>
													</label>
													
													
													
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="validate[required] form-control"
															 name="password" id="password" placeholder="密码"  data-prompt-position="inline"/>
															<i class="ace-icon fa fa-lock"></i>
														</span>
													</label>

													<div class="space"></div>

													<div class="clearfix">
														<!--
														<label class="inline">
															<input type="checkbox" class="ace" checked="checked" />
															<span class="lbl"> 记住账号</span>
														</label>-->

														<button type="submit" id="login_btn" class="width-35 pull-right btn btn-sm btn-primary"
														data-loading-text="登录中..">
															<i class="ace-icon fa fa-key"></i>
															<span class="bigger-110">登录</span>
														</button>
													</div>

													<div class="space-4"></div>
												</fieldset>
											</form>

<!-- 											<div class="social-or-login center"> -->
<!-- 												<span class="bigger-110">Or Login Using</span> -->
<!-- 											</div> -->

<!-- 											<div class="space-6"></div> -->

<!-- 											<div class="social-login center"> -->
<!-- 												<a class="btn btn-primary"> -->
<!-- 													<i class="ace-icon fa fa-facebook"></i> -->
<!-- 												</a> -->

<!-- 												<a class="btn btn-info"> -->
<!-- 													<i class="ace-icon fa fa-twitter"></i> -->
<!-- 												</a> -->

<!-- 												<a class="btn btn-danger"> -->
<!-- 													<i class="ace-icon fa fa-google-plus"></i> -->
<!-- 												</a> -->
<!-- 											</div> -->
										</div><!-- /.widget-main -->

										<!-- 
										<div class="toolbar clearfix">
											<div>
												<a href="#" data-target="#forgot-box" class="forgot-password-link">
													<i class="ace-icon fa fa-arrow-left"></i>
													忘记密码
												</a>
											</div>

											<div>
												<a href="#" data-target="#signup-box" class="user-signup-link">
													注册账号
													<i class="ace-icon fa fa-arrow-right"></i>
												</a>
											</div>
										</div>-->
									</div><!-- /.widget-body -->
								</div><!-- /.login-box -->

								<!-- 
								<div id="forgot-box" class="forgot-box widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header red lighter bigger">
												<i class="ace-icon fa fa-key"></i>
												Retrieve Password
											</h4>

											<div class="space-6"></div>
											<p>
												Enter your email and to receive instructions
											</p>

											<form>
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="email" class="form-control" placeholder="Email" />
															<i class="ace-icon fa fa-envelope"></i>
														</span>
													</label>

													<div class="clearfix">
														<button type="button" class="width-35 pull-right btn btn-sm btn-danger">
															<i class="ace-icon fa fa-lightbulb-o"></i>
															<span class="bigger-110">Send Me!</span>
														</button>
													</div>
												</fieldset>
											</form>
										</div>

										<div class="toolbar center">
											<a href="#" data-target="#login-box" class="back-to-login-link">
												Back to login
												<i class="ace-icon fa fa-arrow-right"></i>
											</a>
										</div>
									</div>
								</div> -->
							</div><!-- /.position-relative -->

							<div class="navbar-fixed-top align-right">
								<br />
								&nbsp;
								<a id="btn-login-dark" href="#">Dark</a>
								&nbsp;
								<span class="blue">/</span>
								&nbsp;
								<a id="btn-login-blur" href="#">Blue</a>
								&nbsp;
								<span class="blue">/</span>
								&nbsp;
								<a id="btn-login-light" href="#">Light</a>
								&nbsp; &nbsp; &nbsp;
							</div>
						</div>
					</div><!-- /.col -->
				</div><!-- /.row -->
			</div><!-- /.main-content -->
		</div><!-- /.main-container -->

		<!-- basic scripts -->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>

		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			jQuery(function($) {
			 $(document).on('click', '.toolbar a[data-target]', function(e) {
				e.preventDefault();
				var target = $(this).data('target');
				$('.widget-box.visible').removeClass('visible');//hide others
				$(target).addClass('visible');//show target
			 });
			});
			
			
			
			//you don't need this, just used for changing background
			jQuery(function($) {
			 $('#btn-login-dark').on('click', function(e) {
				$('body').attr('class', 'login-layout');
				$('#id-text2').attr('class', 'white');
				$('#id-company-text').attr('class', 'blue');
				
				e.preventDefault();
			 });
			 $('#btn-login-light').on('click', function(e) {
				$('body').attr('class', 'login-layout light-login');
				$('#id-text2').attr('class', 'grey');
				$('#id-company-text').attr('class', 'blue');
				
				e.preventDefault();
			 });
			 $('#btn-login-blur').on('click', function(e) {
				$('body').attr('class', 'login-layout blur-login');
				$('#id-text2').attr('class', 'white');
				$('#id-company-text').attr('class', 'light-blue');
				
				e.preventDefault();
			 });
			 
			});
		</script>
	</body>
</html>
