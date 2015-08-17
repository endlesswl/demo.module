<%@include file="/includes/common/taglibs.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<rapid:block name="head">
			<title>零彩宝微信管理后台</title>
		</rapid:block>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
		<!-- bootstrap & fontawesome -->
		<link rel="stylesheet" href="${ctx}/assets/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${ctx}/assets/css/font-awesome.min.css" />
		<link rel="stylesheet" href="${ctx}/css/common/common.css" />
		<!-- page specific plugin styles -->

		<!-- text fonts 
		<link href='http://fonts.useso.com/css?family=Open+Sans:300,400,600&subset=latin,latin-ext' rel='stylesheet'>
		-->
		
		<!-- ace styles -->
		<link rel="stylesheet" href="${ctx}/assets/css/ace.min.css" />

		<!--[if lte IE 9]>
			<link rel="stylesheet" href="assets/css/ace-part2.min.css" />
		<![endif]-->
		<link rel="stylesheet" href="${ctx}/assets/css/ace-skins.min.css" />
		<link rel="stylesheet" href="${ctx}/assets/css/ace-rtl.min.css" />

		<!--[if lte IE 9]>
		  <link rel="stylesheet" href="assets/css/ace-ie.min.css" />
		<![endif]-->

		<!-- inline styles related to this page -->

		<!-- ace settings handler -->
		<script src="${ctx}/assets/js/ace-extra.min.js"></script>
	</head>
	<body class="no-skin">
		<!--头部开始-->
		<div id="navbar" class="navbar navbar-default">
			<script type="text/javascript">
				try{ace.settings.check('navbar' , 'fixed')}catch(e){}
			</script>
			<div class="navbar-container" id="navbar-container">
				<button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler">
					<span class="sr-only">Toggle sidebar</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>

				<div class="navbar-header pull-left">
					<a href="#" class="navbar-brand">
						<small>
							<i class="fa fa-leaf"></i>
							零彩宝
						</small>
					</a>
				</div>

				<div class="navbar-buttons navbar-header pull-right" role="navigation">
					<ul class="nav ace-nav">
						<li class="light-blue">
							<a data-toggle="dropdown" href="#" class="dropdown-toggle">
								<img class="nav-user-photo" src="${ctx}/assets/avatars/user.jpg" alt="Jason's Photo" />
								<span class="user-info">
									<small>欢迎,</small>
									<shiro:user>
										<shiro:principal property="realName"/>
									</shiro:user>
								</span>
								<i class="ace-icon fa fa-caret-down"></i>
							</a>
							<ul class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
								<li>
									<a href="#">
										<i class="ace-icon fa fa-cog"></i>
										设置
									</a>
								</li>
								<li class="divider"></li>
								<li>
									<a href="${ctx}/admin/logout">
										<i class="ace-icon fa fa-power-off"></i>
										退出
									</a>
								</li>
							</ul>
						</li>
					</ul>
				</div>
			</div><!-- /.navbar-container -->
		</div>
		<!--头部结束-->

		<!--container开始--> 
		<div class="main-container" id="main-container">
			<script type="text/javascript">
				try{ace.settings.check('main-container' , 'fixed')}catch(e){}
			</script>
			<!-- 左侧导航开始 -->
			<div id="sidebar" class="sidebar  responsive">
			<rapid:block name="left">
				<script type="text/javascript">
					try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
				</script>
					
				<!-- 快捷按钮开始 -->
				<div class="sidebar-shortcuts" id="sidebar-shortcuts">
					<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
						<button class="btn btn-success">
							<i class="ace-icon fa fa-signal"></i>
						</button>
						<button class="btn btn-info">
							<i class="ace-icon fa fa-pencil"></i>
						</button>
						<button class="btn btn-warning">
							<i class="ace-icon fa fa-users"></i>
						</button>
						<button class="btn btn-danger">
							<i class="ace-icon fa fa-cogs"></i>
						</button>
					</div>
					<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
						<span class="btn btn-success"></span>
						<span class="btn btn-info"></span>
						<span class="btn btn-warning"></span>
						<span class="btn btn-danger"></span>
					</div>
				</div> 
				<!-- 快捷按钮结束 -->
					
				<!-- 导航列表开始 -->
				<ul class="nav nav-list">
					<li class="active">
						<a href="${ctx}/admin/weixinAccount">
							<i class="menu-icon fa fa-tachometer"></i>
							<span class="menu-text">微信帐号管理</span>
						</a>
						<b class="arrow"></b>
					</li>
					<!--
					<li>
						<a href="${ctx}/admin/image">
							<i class="menu-icon fa fa-tachometer"></i>
							<span class="menu-text">图片管理</span>
						</a>
						<b class="arrow"></b>
					</li>
					<li>
						<a href="${ctx}/admin/weixinAccount">
							<i class="menu-icon fa fa-tachometer"></i>
							<span class="menu-text">微信消息管理</span>
						</a>
						<b class="arrow"></b>
					</li>
					<li>
						<a href="${ctx}/admin/weixinAccount">
							<i class="menu-icon fa fa-tachometer"></i>
							<span class="menu-text">微信用户管理</span>
						</a>
						<b class="arrow"></b>
					</li>
					
					<li class="">
						<a href="#" class="dropdown-toggle">
							<i class="menu-icon fa fa-list"></i>
							<span class="menu-text"> Tables </span>
							<b class="arrow fa fa-angle-down"></b>
						</a>
						<b class="arrow"></b>
						<ul class="submenu">
							<li class="">
								<a href="tables.html">
									<i class="menu-icon fa fa-caret-right"></i>
									Simple &amp; Dynamic
								</a>
								<b class="arrow"></b>
							</li>
							<li class="">
								<a href="jqgrid.html">
									<i class="menu-icon fa fa-caret-right"></i>
									jqGrid plugin
								</a>
								<b class="arrow"></b>
							</li>
						</ul>
					</li>-->
				</ul>
				<!-- 导航列表开始 -->
				
				<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
					<i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
				</div>
				<script type="text/javascript">
					try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
				</script>
			
			</rapid:block>
			</div>
			<!--左侧导航结束-->
			
			<!--右侧内容开始-->
			<div class="main-content">
				<rapid:block name="contentright">
					<!-- 当前所在位置开始-->
					<div class="breadcrumbs" id="breadcrumbs">
						<script type="text/javascript">
							try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
						</script>
						<ul class="breadcrumb">
							<li>
								<i class="ace-icon fa fa-home home-icon"></i>
								<a href="#">Home</a>
							</li>
							<li class="active">Dashboard</li>
						</ul><!-- /.breadcrumb -->
						<div class="nav-search" id="nav-search">
							<form class="form-search">
								<span class="input-icon">
									<input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off" />
									<i class="ace-icon fa fa-search nav-search-icon"></i>
								</span>
							</form>
						</div><!-- /.nav-search -->
					</div>
					<!-- 当前所在位置结束-->
					
					<!-- 内容区开始 -->
					<div class="page-content">
						<div class="row">
							<div class="col-xs-12">
							</div>
						</div>	
					</div>
					<!-- 内容区结束 -->
				</rapid:block>
			</div>
			<!-- 底部开始 -->
			<div class="footer">
				<div class="footer-inner">
					<div class="footer-content">
						<span class="bigger-120">
							<span class="blue bolder">零彩宝</span>
							京ICP备11038002号-3  Copyright  &#169;2014 lingcaibao.com All
						</span>

						<!-- 
						<span class="action-buttons">
							<a href="#">
								<i class="ace-icon fa fa-twitter-square light-blue bigger-150"></i>
							</a>

							<a href="#">
								<i class="ace-icon fa fa-facebook-square text-primary bigger-150"></i>
							</a>

							<a href="#">
								<i class="ace-icon fa fa-rss-square orange bigger-150"></i>
							</a>
						</span>-->
					</div>
				</div>
			</div>
			<!-- 底部结束 -->
			<!--右侧内容结束-->
		</div>
	</body>
	<!-- basic scripts -->
	<script type="text/javascript" src='${ctx}/assets/js/jquery.min.js'></script>
	<script type="text/javascript">
		if('ontouchstart' in document.documentElement) document.write("<script src='${ctx}assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
	</script>
	<script src="${ctx}/assets/js/bootstrap.min.js"></script>
	
	<!-- ace scripts -->
	<script src="${ctx}/assets/js/jquery-ui.custom.min.js"></script>
	<script src="${ctx}/assets/js/jquery.ui.touch-punch.min.js"></script>
	<%-- <script src="${ctx}/assets/js/jquery.sparkline.min.js"></script> --%>
	<!-- ace scripts -->
	<script src="${ctx}/assets/js/ace-elements.min.js"></script>
	<script src="${ctx}/assets/js/ace.min.js"></script>		
	<rapid:block name="css-js">
	</rapid:block>
	<rapid:block name="javaScript">
	</rapid:block>
</html>