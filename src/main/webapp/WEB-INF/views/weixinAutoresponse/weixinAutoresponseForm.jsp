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
			<li>
				<a href="${ctx}/admin/weixinAccount">账号管理</a>
			</li>
			<li class="active">公众帐号<<span class="smaller lighter purple">${weixinAccount.accountName}</span>>消息管理</li>
		</ul>
	</div>
	<!-- 当前所在位置结束-->	
	<div class="page-content">
		<div class="page-header">
			<h1>
				<a href ="${ctx}/admin/weixinAutoresponse/${accountId}">消息管理</a>
				<small>
					<i class="ace-icon fa fa-angle-double-right"></i>
					自动回复消息<c:choose><c:when test="${weixinAutoresponse!=null&&weixinAutoresponse.id!=null}">编辑</c:when><c:otherwise>新增</c:otherwise></c:choose>
				</small>
				<span class="my-button-group">
					<button onclick="javascript:window.location.href='${ctx}/admin/weixinNewsitem/${accountId}/create'" class="btn btn-sm btn-success" data-rel="tooltip" title="现只提供图文消息预设。" data-placement="bottom"><i class="ace-icon fa fa-plus"></i> 新增预设消息</button>
				</span>
			</h1>
		</div><!-- /.page-header -->
		<div class="row">
			<div class="col-xs-12">
				<!-- PAGE CONTENT BEGINS -->
				<form class="form-horizontal" id="weixinAccountForm" action="${ctx}/admin/weixinAutoresponse/${accountId}/${action}" method="post">
					<input name="id" value="${weixinAutoresponse.id}" type="hidden"/>
					<input name="accountId" value="${accountId}" type="hidden"/>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1" data-rel="tooltip" title="多个关键词以'_'分开" data-placement="right">关键词</label>
						<div class="col-sm-9">
							<input type="text" id="form-field-1" placeholder=" 如：我要领彩_免费领彩" class="validate[required] col-xs-10 col-sm-5"   name="keyword"  value="${weixinAutoresponse.keyword}" <c:if test="${weixinAutoresponse.keyword=='关注回复'||weixinAutoresponse.keyword=='关键词未匹配回复'}">readonly="readonly"</c:if>  />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right">消息类型 </label>
						<div class="col-sm-9">
							<div class="clearfix">
								<select id="form_msgtype" class="col-xs-10 col-sm-5" name="msgtype" onchange="changeMessageType()">
									<option value="文字消息"  <c:if test="${'文字消息'==weixinAutoresponse.msgtype}">selected="selected"</c:if>>文字消息</option>
									<option value="图文消息" <c:if test="${'图文消息'==weixinAutoresponse.msgtype}">selected="selected"</c:if>>图文消息</option>
								</select>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"  data-rel="tooltip"  data-placement="right">状态 </label>
						<div class="col-sm-9">
							<div class="clearfix">
								<select class="col-xs-10 col-sm-5" name="flag">
									<option value="停用" <c:if test="${'停用'==weixinAutoresponse.flag}">selected="selected"</c:if>>停用</option>
									<option value="启用"  <c:if test="${'启用'==weixinAutoresponse.flag}">selected="selected"</c:if>>启用</option>
								</select>
							</div>
						</div>
					</div>
					<div class="form-group" id="image_text_setting" <c:if test="${'图文消息'!=weixinAutoresponse.msgtype}">style="display: none;"</c:if> >
						<label class="col-sm-3 control-label no-padding-right" data-rel="tooltip" title="绑定单个即为单图文，绑定多个即为多图文。" data-placement="right">绑定模版消息</label>
						<div class="col-sm-9">
							<div class="clearfix">
								<div class="col-xs-12 col-sm-3">
									<c:choose>
										<c:when test="${newsitemList eq null}">
										 没有可供选择的
										</c:when>
										<c:otherwise>
											<c:forEach var="list" items="${newsitemList}" varStatus="status">
												<c:if test="${status.index%2==0}">
													<div class="checkbox" style="width: 100%;">
														<label style="width: 50%;"><input type="checkbox" class="ace"
															name="messageId" value="${list.id}"
															<c:if test="${fn:contains(weixinAutoresponse.messageId,list.id)}">checked="checked"</c:if>>
															<span class="lbl">${list.name}</span>
														</label>
													</div>
												</c:if>
											</c:forEach>
										</c:otherwise>
									</c:choose>
								</div>
								<div class="col-xs-12 col-sm-5">
									<c:choose>
										<c:when test="${newsitemList eq null}">
										</c:when>
										<c:otherwise>
											<c:forEach var="list" items="${newsitemList}" varStatus="status">
												<c:if test="${status.index%2!=0}">
													<div class="checkbox" style="width: 100%;">
														<label style="width: 50%;"><input type="checkbox" class="ace"
															name="messageId" value="${list.id}"
															<c:if test="${fn:contains(weixinAutoresponse.messageId,list.id)}">checked="checked"</c:if>>
															<span class="lbl">${list.name}</span>
														</label>
													</div>
												</c:if>
											</c:forEach>
										</c:otherwise>
									</c:choose>
								</div>
							</div>	
						</div>	
						<%-- <div class="col-sm-9">
							<div class="clearfix">
								<select class="col-xs-10 col-sm-5" name="messageId">
									<option value="0">不绑定</option>
									<c:choose>
										<c:when test="${newsitemList eq null}">
										</c:when>
										<c:otherwise>
											<c:forEach var="list" items="${newsitemList}" varStatus="status">
												<option value="${list.id}" <c:if test="${list.id == weixinAutoresponse.messageId}">selected="selected"</c:if>>${list.name}</option>
											</c:forEach>
										</c:otherwise>
									</c:choose>
								</select>
							</div>
						</div> --%>
					</div>
					<div class="form-group" id="text_rescontent_setting" <c:if test="${'图文消息'==weixinAutoresponse.msgtype||weixinAutoresponse.msgtype==''}">style="display: none;"</c:if>>
						<label class="col-sm-3 control-label no-padding-right" for="form-field-rescontent" data-rel="tooltip" title="只有在消息类型为文字消息时生效，且只能回复纯文本消息。" data-placement="right">文本回复内容</label>
						<div class="col-sm-9">
							<textarea id="form-field-rescontent" placeholder="只有在消息类型为文字消息时生效，且只能回复纯文本消息。" class="col-xs-10 col-sm-5" name="rescontent">${weixinAutoresponse.rescontent}</textarea>
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
						<a href="${ctx}/admin/weixinAutoresponse/${accountId}">
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
	<link rel="stylesheet" href="${ctx}/assets/css/colorpicker.css" />
	<!-- page specific plugin scripts -->
	<script src="${ctx}/jQuery-Validation/js/jquery.validationEngine.js"></script>
	<script src="${ctx}/jQuery-Validation/js/languages/jquery.validationEngine-zh_CN.js"></script>
	<script src="${ctx}/assets/js/bootbox.min.js"></script>	
</rapid:override> 
<rapid:override name="javaScript">
	<script type="text/javascript">
			jQuery(function($) {
							
				$(document).on("click", "#btn-submit", function(e) {
					if ($("#weixinAccountForm").validationEngine('validate')) {
						var msg_js_type = $("#form_msgtype").val();
						if (msg_js_type == '图文消息') {
							var s_size = $("#image_text_setting input[type='checkbox']:checked").size();
							if(s_size>10||s_size<1){
								bootbox.alert("绑定模板数量必须是1至10个！");
								return;
							}
						}else{
							var res_c=$("#form-field-rescontent").val();
							if(res_c==''){
								bootbox.alert("回复文本内容不能为空！");
								return;
							}
						}
						$("#btn-submit").button("loading");
						$("#weixinAccountForm").submit();
					}
				});
				$('[data-rel=tooltip]').tooltip({container:'body'});
			})
			
			function changeMessageType(){
				var msg_js_type = $("#form_msgtype").val();
					if (msg_js_type == '图文消息') {
						$("#text_rescontent_setting").hide();
						$("#image_text_setting").show();
					}else{
						$("#text_rescontent_setting").show();
						$("#image_text_setting").hide();
					}
				}
	</script>
</rapid:override>
<%@ include file="../base.jsp" %>