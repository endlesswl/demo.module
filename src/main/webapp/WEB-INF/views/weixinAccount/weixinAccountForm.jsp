<%@include file="/includes/common/taglibs.jsp" %>
<%@ page language="java" import="com.lingcaibao.weixin.account.entity.WeixinAccount" pageEncoding="UTF-8" %>
<rapid:override name="contentright">
	<!-- 当前所在位置开始-->
	<div class="breadcrumbs" id="breadcrumbs">
		<script type="text/javascript">
			try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
		</script>
		<ul class="breadcrumb">
			<li>
				<i class="ace-icon fa fa-home home-icon"></i>
				<a href="${ctx}/admin/weixinAccount">首页</a>
			</li>
			<li class="active">账号管理</li>
		</ul><!-- /.breadcrumb -->
		<!--
		<div class="nav-search" id="nav-search">
			<form class="form-search">
				<span class="input-icon">
					<input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off" />
					<i class="ace-icon fa fa-search nav-search-icon"></i>
				</span>
			</form>
		</div> /.nav-search -->
	</div>
	<!-- 当前所在位置结束-->	
	<div class="page-content">
		<div class="page-header">
			<h1>
				账号管理
				<small>
					<i class="ace-icon fa fa-angle-double-right"></i>
					帐号<c:choose><c:when test="${weixinAccount!=null&&weixinAccount.id!=null}">编辑</c:when><c:otherwise>新增</c:otherwise></c:choose>
				</small>
			</h1>
		</div><!-- /.page-header -->
		<div class="row">
			<div class="col-xs-12">
				<!-- PAGE CONTENT BEGINS -->
				<form class="form-horizontal" id="weixinAccountForm" action="${ctx}/admin/weixinAccount/${action}" method="post">
					<input name="id" value="${weixinAccount.id}" type="hidden"/>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1">账号名称</label>
						<div class="col-sm-9">
							<input type="text" id="form-field-1" placeholder="如：零彩宝" class="validate[required] col-xs-10 col-sm-5"   name="accountName" value="${weixinAccount.accountName}"/>
						</div>
					</div>
					<!-- 
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-2">登陆账号</label>
						<div class="col-sm-9">
							<input type="text" id="form-field-2" placeholder="如：service@domain.com" class="validate[required] col-xs-10 col-sm-5"  name="accountUserName" value="${weixinAccount.accountUserName}"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-3">登陆密码</label>
						<div class="col-sm-9">
							<input type="password" id="form-field-3" placeholder="******" class="col-xs-10 col-sm-5" name="accountPassword" value="${weixinAccount.accountPassword}"/>
						</div>
					</div>-->
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-4">APPID</label>
						<div class="col-sm-9">
							<input type="text" id="form-field-4"  class="validate[required] col-xs-10 col-sm-5" name="accountAppid" value="${weixinAccount.accountAppid}"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-5">APPSECRET</label>
						<div class="col-sm-9">
							<input type="text" id="form-field-5"  class="validate[required] col-xs-10 col-sm-5" name="accountAppsecret" value="${weixinAccount.accountAppsecret}"/>
						</div>
					</div>	
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-6">TOKEN</label>
						<div class="col-sm-9">
							<input type="text" id="form-field-6"  class="validate[required] col-xs-10 col-sm-5"  placeholder="微信公众帐号所设置的TOKEN" name="accountToken" value="${weixinAccount.accountToken}"/>
						</div>
					</div>
					<!--
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-7">微信号</label>
						<div class="col-sm-9">
							<input type="text" id="form-field-7"  class="validate[required] col-xs-10 col-sm-5"  name="accountNumber" value="${weixinAccount.accountNumber}"/>
						</div>
					</div> -->
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right">账号类型</label>
						<div class="col-sm-9">
							<div class="clearfix">
								<select class="col-xs-10 col-sm-5" data-placeholder="选择类型" name="accountType">
									<option value="服务号"  <c:if test="${'服务号'==weixinAccount.accountType}">selected="selected"</c:if>>服务号</option>
									<option value="订阅号" <c:if test="${'订阅号'==weixinAccount.accountType}">selected="selected"</c:if>>订阅号</option>
								</select>
							</div>
						</div>
					</div>
					<!--
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-8">服务器地址</label>
						<div class="col-sm-9">
							<input type="text" id="form-field-8"  class="validate[required,custom[url]] col-xs-10 col-sm-5" placeholder="用于自动回复等"  name="accountVerifyUrl" value="${weixinAccount.accountVerifyUrl}"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-9">授权回调域名</label>
						<div class="col-sm-9">
							<input type="text" id="form-field-9"  class="validate[required,custom[url]] col-xs-10 col-sm-5" name="accountOauth2Url" value="${weixinAccount.accountOauth2Url}"/>
						</div>
					</div>
					<c:if test="${weixinAccount!=null&&weixinAccount.id!=null}">
						<div class="form-group">
							<label class="col-sm-3 control-label no-padding-right">状态</label>
							
							<div class="col-sm-9">
								<div class="clearfix">
									<select class="col-xs-10 col-sm-5" data-placeholder="选择类型" name="status" >
										<option  <c:if test="${'待启用' eq weixinAccount.accountType}">selected="selected"</c:if>>待启用</option>
										<option  <c:if test="${'服务中' eq weixinAccount.accountType}">selected="selected"</c:if>>服务中</option>
										<option  <c:if test="${'服务停止' eq weixinAccount.accountType}">selected="selected"</c:if>>服务停止</option>
									</select>
								</div>
							</div> 
						</div>
					</c:if>-->
					<!--
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" >服务周期</label>
						<div class="col-sm-4">		
							<div class="input-group">
								<span class="input-group-addon">
									<i class="fa fa-calendar bigger-110"></i>
								</span>
								<input class="validate[required] form-control" type="text" name="dateRange" id="id-date-range-picker-1" value="${weixinAccount.dateRange}" />
							</div>
						</div>	
					</div>
					
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" >周期</label>
						<div class="col-sm-4">
							<div class="input-daterange input-group">
								<input type="text" class="input-sm form-control" name="start" />
								<span class="input-group-addon">
									<i class="fa fa-exchange"></i>
								</span>

								<input type="text" class="input-sm form-control" name="end" />
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1-1">授权访问域名</label>
						<div class="col-sm-9">
							<div class="clearfix">
								<textarea id="form-field-8" placeholder="多个域名用','隔开" class="validate[required] col-xs-10 col-sm-5" name="allowUrls">${weixinAccount.allowUrls}</textarea>
							</div>
						</div>
					</div>	-->
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1-1">备注</label>
						<div class="col-sm-9">
							<div class="clearfix">
								<textarea id="form-field-8" placeholder="备注" class="col-xs-10 col-sm-5" name="accountDesc">${weixinAccount.accountDesc}</textarea>
							</div>
						</div>
					</div>
				</form>
				<div class="clearfix form-actions">
					<div class="col-md-offset-3 col-md-9">
						<button id="btn-submit" class="btn btn-sm btn-info" type="button" data-loading-text="正在保存..">
							<i class="ace-icon fa fa-floppy-o"></i>
							保存
						</button>
						&nbsp; &nbsp; &nbsp;
						
						&nbsp; &nbsp; &nbsp;
						<a href="${ctx}/admin/weixinAccount">
						<button id="btn-back" class="btn btn-sm btn-grey" type="reset">
							<i class="ace-icon fa fa-undo bigger-110"></i>
							返回
						</button></a>
					</div>
				</div>
			</div>	
			
		</div>
	</div>
</rapid:override> 
<rapid:override name="css-js">
	<link rel="stylesheet" href="${ctx}/jQuery-Validation/css/validationEngine.jquery.css" type="text/css"/>
	<link rel="stylesheet" href="${ctx}/assets/css/datepicker.css" />
	<link rel="stylesheet" href="${ctx}/assets/css/bootstrap-timepicker.css" />
	<link rel="stylesheet" href="${ctx}/assets/css/daterangepicker.css" />
	<link rel="stylesheet" href="${ctx}/assets/css/bootstrap-datetimepicker.css" />
	<link rel="stylesheet" href="${ctx}/assets/css/colorpicker.css" />
	<!-- page specific plugin scripts -->
	<script src="${ctx}/assets/js/date-time/bootstrap-datepicker.min.js"></script>
	<script src="${ctx}/assets/js/date-time/bootstrap-timepicker.min.js"></script>
	<script src="${ctx}/assets/js/date-time/moment.min.js"></script>
	<!-- <script src="${ctx}/assets/js/date-time/moment-with-locales.min.js"></script>-->
	<script src="${ctx}/assets/js/date-time/daterangepicker.min.js"></script>
	<script src="${ctx}/assets/js/date-time/bootstrap-datetimepicker.min.js"></script>
	<script src="${ctx}/assets/js/date-time/bootstrap-datepicker.zh-CN.min.js"></script>
	<script src="${ctx}/jQuery-Validation/js/jquery.validationEngine.js"></script>
	<script src="${ctx}/jQuery-Validation/js/languages/jquery.validationEngine-zh_CN.js"></script>	
</rapid:override> 
<rapid:override name="javaScript">
	<script type="text/javascript">
			jQuery(function($) {
				$('input[name=dateRange]').daterangepicker({
					'applyClass' : 'btn-sm btn-success',
					'cancelClass' : 'btn-sm btn-default',
					locale: {
						applyLabel: '确定',
						cancelLabel: '取消',
					}
				}).prev().on(ace.click_event, function(){
					$(this).next().focus();
				});
				
				$(document).on("click", "#btn-submit", function(e) {
					//alert($("#weixinAccountForm").validationEngine('validate'));
					if ($("#weixinAccountForm").validationEngine('validate')) {
						$("#btn-submit").button("loading");
						$("#weixinAccountForm").submit();
					}
				});
				
			})
	</script>
</rapid:override>
<%@ include file="../base.jsp" %>