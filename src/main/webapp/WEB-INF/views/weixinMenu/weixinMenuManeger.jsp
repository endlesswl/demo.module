<%@include file="/includes/common/taglibs.jsp"%>
<%@ page language="java"
	import="com.lingcaibao.weixin.maneger.entity.WeixinNewsitem"
	pageEncoding="UTF-8"%>
<rapid:override name="css-js">
	<!-- page specific plugin scripts -->
	<link rel="stylesheet"
		href="${ctx}/jQuery-Validation/css/validationEngine.jquery.css"
		type="text/css" />
	<script src="${ctx}/jQuery-Validation/js/jquery.validationEngine.js"></script>
	<script
		src="${ctx}/jQuery-Validation/js/languages/jquery.validationEngine-zh_CN.js"></script>
	<script src="${ctx}/assets/js/jquery.nestable.min.js"></script>
	<script src="${ctx}/assets/js/bootbox.min.js"></script>
</rapid:override>

<rapid:override name="contentright">
	<!-- 当前所在位置开始-->
	<div class="breadcrumbs" id="breadcrumbs">
		<script type="text/javascript">
			try {
				ace.settings.check('breadcrumbs', 'fixed')
			} catch (e) {
			}
		</script>
		<ul class="breadcrumb">
			<li><i class="ace-icon fa fa-home home-icon"></i><a href="${ctx}/admin/weixinAccount">首页</a>
			</li>
			<li><a href="${ctx}/admin/weixinAccount">账号管理</a></li>
			<li class="active">公众账号<<span class="smaller lighter purple">${weixinAccount.accountName}</span>>管理
			</li>
		</ul>
	</div>
	<!-- 当前所在位置结束-->
	<div class="page-content">
		<div class="page-header">
			<h1>
				<a href="${ctx}/admin/weixinAutoresponse/${accountId}">Menu管理</a> <small>
					<i class="ace-icon fa fa-angle-double-right"></i> Menu设置
				</small>
			</h1>
		</div>
		<!-- /.page-header -->
		<div class="row">
			<div class="col-xs-12">
				<div class="row">
					<div class="col-xs-12 col-sm-3">
						<div class="widget-box">
							<div class="widget-header center">
								<h4 class="widget-title lighter middle dark">menu设置</h4>
								<div class="widget-toolbar">
									<button class="btn btn-xs btn-primary" onclick="publishMenu('${accountId}');">&nbsp;发&nbsp;布&nbsp;</button>
								</div>
							</div>
							<div class="widget-body">
								<div class="widget-main padding-8 dd">
									<c:choose>
										<c:when test="${!empty weixinMenus}">
											<ol class="dd-list">
												<c:forEach var="weixinMenuModel" items="${weixinMenus}"
													varStatus="status">
													<li class="dd-item" data-id="${status.index+1}">
														<div class="dd-handle"><span class=orange>${weixinMenuModel.name}</span>
															<div class="pull-right action-buttons">
																<c:if test="${fn:length(weixinMenuModel.subWeimenus)<5}">
																	<a class="green" href="#"
																		onclick="addSubbuttom('${weixinMenuModel.id}',${fn:length(weixinMenuModel.subWeimenus)})">
																		<i class="ace-icon fa fa-plus bigger-130"></i>
																	</a>
																</c:if>
																<a class="purple" href="#"
																		onclick="addSortNo(${status.index+1},'${weixinMenuModel.id}')">
																		<i class="ace-icon fa fa-arrow-up bigger-130"></i>
																</a>
																<a class="blue" href="#"
																	onclick="editSupButtom('${weixinMenuModel.id}','${weixinMenuModel.name}','${weixinMenuModel.type}','${weixinMenuModel.url}','${weixinMenuModel.key}','${status.index+1}',${fn:length(weixinMenuModel.subWeimenus)})">
																	<i class="ace-icon fa fa-pencil bigger-130"></i>
																</a>
																<!-- <a class="red" href="#">
																		<i class="ace-icon fa fa-trash-o bigger-130"></i>
																	</a> -->
															</div>
														</div> <c:if
															test="${!empty weixinMenuModel.subWeimenus && fn:length(weixinMenus)>0}">
															<ol class="dd-list" style="">
																<c:forEach var="weixinSubMenuModel"
																	items="${weixinMenuModel.subWeimenus}"
																	varStatus="subStatus">
																	<div class="dd-handle">
																		<span class="orange2">${weixinSubMenuModel.name}</span>
																		<div class="pull-right action-buttons">
																			<a class="blue" href="#"
																				onclick="editSubButtom('${weixinMenuModel.id}','${weixinSubMenuModel.id}','${weixinSubMenuModel.name}','${weixinSubMenuModel.type}','${weixinSubMenuModel.url}','${weixinSubMenuModel.key}',${subStatus.index+1})">
																				<i class="ace-icon fa fa-pencil bigger-130"></i>
																			</a> 
																			<a class="purple" href="#" onclick="addSortNo(${subStatus.index+1},'${weixinSubMenuModel.id}')">
																				<i class="ace-icon fa fa-arrow-up bigger-130"></i>
																			</a>
																			<a class="red" href="#"
																				onclick="deleteSubButtom('${weixinSubMenuModel.id}')">
																				<i class="ace-icon fa fa-trash-o bigger-130"></i>
																			</a>
																		</div>
																	</div>
																</c:forEach>
															</ol>
														</c:if>
													</li>
												</c:forEach>
											</ol>
										</c:when>
										<c:otherwise>
											<div class="alert alert-danger center">暂未设置Menu!</div>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
						</div>
					</div>
					<div class="col-sm-8">
						<div class="center">
							<div class="widget-main">
								<p class="alert alert-info">
									自定义菜单最多包括3个一级菜单，每个一级菜单最多包含5个二级菜单。一级菜单最多4个汉字，二级菜单最多7个汉字，多出来的部分将会以“...”代替。
								</p>
							</div>
						</div>
						<div class="space-12"></div>
						<div class="center" id="buttom_edit_form" style="display: none;">
							<form class="form-horizontal" id="weixinMenuForm"
								action="${ctx}/admin/weixinMenu/${accountId}/save" method="post">
								<input id="form_id" name="id" value="0" type="hidden"> <input
									id="form_prentsId" name="prentsId" value="0" type="hidden">
								<input id="form_orders" name="orders" value="1" type="hidden">
								<input name="accountId" value="${accountId}" type="hidden">
								<div class="profile-user-info profile-user-info-striped">
									<div class="profile-info-row">
										<div class="profile-info-name">菜单名称</div>
										<div class="profile-info-value">
											<input type="text" placeholder="一级菜单可输入5个字符，二级菜单可输入12个字符"
												class="validate[required] col-xs-10 col-sm-8" name="name"
												id="buttom_mane" value="">
										</div>
									</div>
									<div class="profile-info-row">
										<div class="profile-info-name" style="vertical-align: top;">
											<select name="type" id="form_type" onchange="typeChange()">
												<option value="">请选择</option>
												<option value="view">链接</option>
												<option value="click">绑定规则</option>
											</select>
										</div>
										<div class="profile-info-value">
											<input class="validate[custom[url]] col-xs-10 col-sm-8"
												placeholder="需以 http:// 开头 ,如：http://www.lingcaibao.cn"
												id="form_url" name="url" value="" type="text"
												style="display: none;">

											<div class="col-xs-10 col-sm-12" id="form_key"
												style="display: none;">
												<div class="control-group col-xs-12 col-sm-11">
													<c:choose>
														<c:when
															test="${newsitemList eq null|| fn:length(newsitemList)==0}">
															 没有可供选择的
															</c:when>
														<c:otherwise>
															<c:forEach var="list" items="${newsitemList}"
																varStatus="status">
																	<div class="radio no-padding-left"
																		style="width: 100%; text-align: left;">
																		<label><input type="radio" class="ace"
																			name="key" id="radio_${list.id}" value="${list.id}">
																			<span class="lbl">${list.keyword}</span> </label>
																	</div>
															</c:forEach>
														</c:otherwise>
													</c:choose>
												</div>
												<!-- 
												<div class="control-group col-xs-12 col-sm-6">
													<c:choose>
														<c:when test="${newsitemList eq null}">
														</c:when>
														<c:otherwise>
															<c:forEach var="list" items="${newsitemList}"
																varStatus="status">
																<c:if test="${status.index%2!=0}">
																	<div class="radio"
																		style="width: 100%; text-align: left;">
																		<label><input type="radio" class="ace"
																			name="key" id="radio_${list.id}" value="${list.id}">
																			<span class="lbl">${list.keyword}</span> </label>
																	</div>
																</c:if>
															</c:forEach>
														</c:otherwise>
													</c:choose>
												</div>-->
											</div>
											<!-- <input class="col-xs-10 col-sm-8" id="form_key" name="key" value=""
													type="text" style="display: none;"> -->
										</div>
									</div>


								</div>
							</form>
							<div class="clearfix form-actions">
								<div class="col-md-12">
									<%-- <c:if test="${empty weixinMenus || fn:length(weixinMenus)<3}">
											<button id="btn-top-submit" class="btn btn-sm btn-info"
												type="button" data-loading-text="正在保存..">
												<i class="ace-icon fa fa-floppy-o"></i> 存为一级菜单
											</button>&nbsp; &nbsp; &nbsp;
										&nbsp; &nbsp; &nbsp;</c:if> --%>
									<button id="btn-submit" class="btn btn-sm btn-info"
										type="button" data-loading-text="正在保存..">
										<i class="ace-icon fa fa-floppy-o"></i> 保存
									</button>
									<%-- &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <a
											href="${ctx}/admin/weixinAutoresponse/${accountId}">
											<button id="btn-back" class="btn btn-sm btn-grey" type="reset">
												<i class="ace-icon fa fa-undo bigger-110"></i> 发布
											</button> 
										</a>--%>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


</rapid:override>
<rapid:override name="javaScript">
	<script type="text/javascript">
		var allowEditType =true;//是否允许编辑绑定关系
		jQuery(function($) {
			$(document).on("click", "#btn-submit", function(e) {
				//alert($("#weixinAccountForm").validationEngine('validate'));
				if ($("#weixinMenuForm").validationEngine('validate')) {//基础验证
					var vpv=$("#form_prentsId").val();
					if(vpv==0){//是否为一级菜单
						if(allowEditType){//没有子菜单进行编辑验证
							//console.log("---form_type-----"+$("#form_type").val()+"--------")
							if ($("#form_type").val() == 'view') {
								var formUrl = $("#form_url").val();
								//console.log("--------"+formUrl+"--------")
								if(formUrl==''){
									bootbox.alert("请输入菜单url！");
									return;
								}
								$("input:radio[name='key']").attr("checked",false);
							} else if ($("#form_type").val() == 'click') {
								var formKey = $("input:radio[name='key']:checked").val();
								//console.log("--------"+formKey+"--------")
								if(formKey==''||formKey==undefined){
									bootbox.alert("请选择菜单模版！");
									return;
								}
								$("#form_url").val("");
							}else{
								if(formKey==''||formKey==undefined){
									bootbox.alert("请选择菜单类型！");
									return;
								}
								$("#form_key").val("");
								$("#form_url").val("");
							}
						}else{
							$("#form_type").val("");
							$("#form_key").val("");
							$("#form_url").val("");
						}
					}else{
						if ($("#form_type").val() == 'view') {
							var formUrl = $("#form_url").val();
							if(formUrl==''){
								bootbox.alert("请输入菜单url！");
								return;
							}
							$("input:radio[name='key']").attr("checked",false);
						} else if ($("#form_type").val() == 'click') {
							var formKey = $("input:radio[name='key']:checked").val();
							//console.log("--------"+formKey+"--------")
							if(formKey==''||formKey==undefined){
								bootbox.alert("请选择菜单模版！");
								return;
							}
							$("#form_url").val("");
						}else{
							bootbox.alert("请选择菜单类型！");
						}
					}
					
					$("#btn-top-submit").button("loading");
					$("#weixinMenuForm").submit();
				}
			});

			
			
			$('[data-rel=tooltip]').tooltip({
				container : 'body'
			});
			
		})
		
		//编辑一级菜单
		function editSupButtom(id,name,type,url,key,orders,subLength){
			allowEditType=true;
			$('#buttom_edit_form').show();
			$('#form_id').val(id);
			$('#buttom_mane').val(name);
			$("#form_prentsId").val(0);
			$("#form_orders").val(orders);
			if(subLength>0){
				allowEditType=false;
				$("#form_type").val("");
				$("#form_type").attr("disabled","disabled");
				typeChange();
			}else{
				$("#form_type").removeAttr("disabled");
				$("#form_type").val(type);
				$("#form_url").val(url);
				type_key_select(key);
				typeChange();
			}
		}

		//编辑二级菜单
		function editSubButtom(pid,id,name,type,url,key,orders){
			$("#form_type").removeAttr("disabled");
			allowEditType=true;
			$('#buttom_edit_form').show();
			$('#form_id').val(id);
			$('#buttom_mane').val(name);
			$("#form_type").val(type);
			$("#form_prentsId").val(pid);
			$("#form_orders").val(orders);
			type_key_select(key);
			$("#form_url").val(url);
			typeChange();
		}

		//删除二级菜单
		function deleteSubButtom(id){
			bootbox.confirm({
				message: "您确定要删除此菜单吗？",
				buttons: {
					confirm: {
						label: "<i class='ace-icon fa fa-trash-o bigger-110'></i> 确定",
						className: "btn-danger btn-sm",
					},
					cancel : {
						label: "<i class='ace-icon fa fa-times bigger-110'></i> 取消",
						className: "btn-sm",
					}
				},
				callback: function(result) {
					if (result) {
						window.location.href="${ctx}/admin/weixinMenu/${accountId}/delete/" + id; 
					}
				}
			});
		}
		
		//新增二级菜单
		function addSubbuttom(p_id,orders){
			if(orders==0){
				bootbox.alert("当一级菜单添加二级菜单后，一级菜单的绑定关系将自动失效，请确认后继续操作！");
			}
			$('#buttom_edit_form').show();
			orders= orders+1;
			$('#form_id').val(0);
			$('#buttom_mane').val("");
			$("#form_type").val("");
			$("#form_prentsId").val(p_id);
			$("#form_orders").val(orders);
			$("#form_key").val("");
			$("#form_url").val("");
			typeChange();
		}

		//绑定规则选中
		function type_key_select(t_key){
			if(t_key==''){
				$("input:radio[name='key']").attr("checked",false);
			}else{
				$("#radio_"+t_key).click();
			}
		}

		//menu发布
		function publishMenu(accountid){
			bootbox.confirm({
				message: "您确定发布菜单吗？",
				buttons: {
					confirm: {
						label: "<i class='ace-icon fa fa-trash-o bigger-110'></i> 确定",
						className: "btn-danger btn-sm",
					},
					cancel : {
						label: "<i class='ace-icon fa fa-times bigger-110'></i> 取消",
						className: "btn-sm",
					}
				},
				callback: function(result) {
					if (result) {
						$.post("${ctx}/admin/weixinMenu/publishMenuToWeixin/" + accountid, function(r) {
							if (r.success) {
								bootbox.alert(r.msg);
							}else{
								bootbox.alert(r.msg);
							}
							console.log("--------"+r.data+"--------")
						});
					}
				}
			});
		}

		//菜单类型切换
		function typeChange() {
			if ($("#form_type").val() == 'view') {
				$("#form_url").show();
				$("#form_key").hide();
			} else if ($("#form_type").val() == 'click') {
				$("#form_key").show();
				$("#form_url").hide();
			}else{
				$("#form_key").hide();
				$("#form_url").hide();
			}
		}

		//排序
		function addSortNo(sortNo,id){
			if(sortNo==1){
				bootbox.alert("该菜单已经是第一了哦！");
			}else{
				window.location.href="${ctx}/admin/weixinMenu/${accountId}/addSortNo/" + id; 
			}
		}
	</script>
</rapid:override>
<%@ include file="../base.jsp"%>