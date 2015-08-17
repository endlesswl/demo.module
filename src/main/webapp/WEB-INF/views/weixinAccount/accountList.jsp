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
		</ul>
	</div>
	<!-- 当前所在位置结束-->	
	<div class="page-content">
		<div class="page-header">
			<h1>
				账号管理
				<small>
					<i class="ace-icon fa fa-angle-double-right"></i>
					帐号列表
				</small>
				<span class="my-button-group">
								<button id="btn-query" class="btn btn-sm btn-purple"><i class="ace-icon fa fa-search"></i> 精确查询</button>
								<button onclick="javascript:window.location.href='${ctx}/admin/weixinAccount/create'" class="btn btn-sm btn-success"><i class="ace-icon fa fa-plus"></i> 新增</button>
				</span>
			</h1>
		</div><!-- /.page-header -->
		<div class="row">
			<div class="col-xs-12">
				<div class="row">
					<div class="col-xs-12">
						<table id="sample-table" class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th class="center" width="40px;">
									<!--
										<label class="position-relative">
											<input type="checkbox" class="ace" />
											<span class="lbl"></span>
										</label>-->
									</th>
									<th data-rel="tooltip" title="自动回复设置" data-placement="bottom"><%=WeixinAccount.ALIAS_ACCOUNT_NAME%></th>
									<!--
									<th><%=WeixinAccount.ALIAS_ACCOUNT_USER_NAME%></th>
									<th><%=WeixinAccount.ALIAS_ACCOUNT_PASSWORD%></th>
									<th><%=WeixinAccount.ALIAS_ACCOUNT_APPID%></th>
									<th><%=WeixinAccount.ALIAS_ACCOUNT_APPSECRET%></th>
									<th><%=WeixinAccount.ALIAS_ACCOUNT_TOKEN%></th>
									<th><%=WeixinAccount.ALIAS_ACCOUNT_NUMBER%></th>-->
									<th class="center" width="120px;"><%=WeixinAccount.ALIAS_ACCOUNT_TYPE%></th>
									<!--
									<th><%=WeixinAccount.ALIAS_ACCOUNT_DESC%></th>
									<th><%=WeixinAccount.ALIAS_ACCOUNT_VERIFY_URL%></th>
									<th><%=WeixinAccount.ALIAS_ACCOUNT_OAUTH2_URL%></th>
									<th><%=WeixinAccount.ALIAS_STATUS%></th>-->
									<th width="150px;">操作</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>	
				</div>
			</div>
		</div>
	</div>
	<div id="search-form" class="modal" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="form-horizontal">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="blue bigger"><i class="ace-icon fa fa-search"></i>查询</h4>
					</div>
					<form id="searchAccountForm">
						<div class="modal-body">
							<div class="row">
								<div class="col-xs-12">
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-1">名称</label>
										<div class="col-sm-8">
											<input type="text" id="s_accountName" class="form-control" />
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-2">类型</label>
										<div class="col-sm-8">
											<select class="form-control" data-placeholder="选择类型"  id="s_accountType">
												<option value="">全部</option>
												<option value="服务号">服务号</option>
												<option value="订阅号">订阅号</option>
											</select>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-2"> 状态 </label>
										<div class="col-sm-8">
											<select id="status" class="form-control" id="s_status">
												<option value="">全部</option>
												<option value="待启用">待启用</option>
												<option value="服务中">服务中</option>
												<option value="服务停止">服务停止</option>
											</select>
											
										</div>
									</div>
								</div>
							</div>
						</div>	
					</form>
					<div class="modal-footer">
						<button id="executeSearch" class="btn btn-sm btn-purple">
							<i class="ace-icon fa fa-search"></i> 查询
						</button>
						<button class="btn btn-sm" data-dismiss="modal">
							<i class="ace-icon fa fa-times"></i> 取消
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</rapid:override> 
<rapid:override name="css-js">
	<!-- page specific plugin scripts -->
	<script src="${ctx}/assets/js/jquery.dataTables.min.js"></script>
	<script src="${ctx}/assets/js/jquery.dataTables.bootstrap.js"></script>
	<script src="${ctx}/assets/js/bootbox.min.js"></script>
</rapid:override> 
<rapid:override name="javaScript">
	<script type="text/javascript">
		jQuery(function($) {
			var oTable1 = 
			$('#sample-table').dataTable( {
				"oLanguage": {//下面是一些汉语翻译
					"sSearch": "搜索:",
					"sLengthMenu": "每页显示 _MENU_ 条记录",
					"sZeroRecords": "没有检索到数据",
					"sInfo": "显示 _START_ 至 _END_ 条 &nbsp;&nbsp;共 _TOTAL_ 条",
				//	"sInfoFiltered": "(筛选自 _MAX_ 条数据)",
				//	"sInfoEmtpy": "没有数据",
				//	"sProcessing": "正在加载数据...",
				//	"sProcessing": "<img src='{{rootUrl}}global/img/ajaxLoader/loader01.gif' />", //这里是给服务器发请求后到等待时间显示的 加载gif
					"oPaginate":
					{
						"sFirst": "首页",
						"sPrevious": "前一页",
						"sNext": "后一页",
						"sLast": "末页"
					}
				},
				"bAutoWidth": false,
				"bProcessing": false, // 禁用正在处理提示(由于太丑)
				"bServerSide": true, // 启用服务端模式
				"sServerMethod": "POST", // 解决参数乱码问题
				"bSort":false,
				"bFilter":false,
				"sAjaxSource": "${ctx}/admin/weixinAccount/list", //给服务器发请求的url
				fnServerParams: function (aoData) {
				    aoData.push({"name": "s_accountName", "value": $("#s_accountName").val()});
				    aoData.push({"name": "s_accountType", "value": $("#s_accountType").val()});
				    aoData.push({"name": "s_status", "value": $("#s_status").val()});
				},
				"aoColumns": [
					{"sDefaultContent": '', "sClass": "center"}, //mData 表示发请求时候本列的列明，返回的数据中相同下标名字的数据会填充到这一列
					{"mData": 'accountName'},
					//{"mData": 'accountUserName'},
					//{"mData": 'accountPassword'},
					//{"mData": 'accountAppid'},
					//{"mData": 'accountAppsecret'},
					//{"mData": 'accountToken'},
					//{"mData": 'accountNumber'},
					{"mData": 'accountType', "sClass": "center"},
					//{"mData": 'accountVerifyUrl'},
					//{"mData": 'accountOauth2Url'},
					//{"mData": 'status'},
					{"sDefaultContent": '',"sClass": "center"},//sClass 表示给本列加class
				],
				"fnRowCallback": function(nRow, aData, iDisplayIndex) {// 当创建了行，但还未绘制到屏幕上的时候调用，通常用于改变行的class风格 
					$('td:eq(0)', nRow).html(iDisplayIndex + 1);
					$('td:eq(3)', nRow).html("<div class=\"hidden-sm hidden-xs btn-group\"><button class=\"btn btn-xs btn-light\" onclick=\"javascript:window.location.href='${ctx}/admin/weixinMenu/" + aData.id +"'\"  data-rel=\"tooltip\" title=\"菜单管理\" data-placement=\"bottom\"><i class=\"ace-icon fa fa-list bigger-120\"></i></button><button class=\"btn btn-xs \" onclick=\"javascript:window.location.href='${ctx}/admin/weixinAutoresponse/" + aData.id +"'\"  data-rel=\"tooltip\" title=\"消息管理\" data-placement=\"bottom\"><i class=\"ace-icon fa  fa-comments-o  bigger-120\"></i></button><button class=\"btn btn-xs btn-primary\" onclick=\"javascript:window.location.href='${ctx}/admin/weixinAccount/update/" + aData.id + "'\" data-rel=\"tooltip\" title=\"帐号编辑\" data-placement=\"bottom\"><i class=\"ace-icon fa fa-pencil bigger-120\"></i></button><button class=\"btn btn-xs btn-danger\" onclick=\"javascript:deleteAccount('" + aData.id +"')\" data-rel=\"tooltip\" title=\"账号删除\" data-placement=\"bottom\"><i class=\"ace-icon fa fa-trash-o bigger-120\"></i></button></div>"); 	 
					//$('td:eq(5)', nRow).html("<div class=\"hidden-sm hidden-xs btn-group\"><button class=\"btn btn-xs btn-light\" onclick=\"javascript:window.location.href='${ctx}/admin/weixinAutoresponse/" + aData.id +"'\"  data-rel=\"tooltip\" title=\"菜单管理\" data-placement=\"bottom\"><i class=\"ace-icon fa fa-list bigger-120\"></i></button><button class=\"btn btn-xs \" onclick=\"javascript:window.location.href='${ctx}/admin/weixinAutoresponse/" + aData.id +"'\"  data-rel=\"tooltip\" title=\"消息管理\" data-placement=\"bottom\"><i class=\"ace-icon fa  fa-comments-o  bigger-120\"></i></button><button class=\"btn btn-xs btn-info\" onclick=\"javascript:window.location.href='${ctx}/admin/weixinAccount/update/" + aData.id +"'\"  data-rel=\"tooltip\" title=\"粉丝管理\" data-placement=\"bottom\"><i class=\"ace-icon fa fa-users bigger-120\"></i></button><button class=\"btn btn-xs btn-primary\" onclick=\"javascript:window.location.href='${ctx}/admin/weixinAccount/update/" + aData.id + "'\" data-rel=\"tooltip\" title=\"帐号编辑\" data-placement=\"bottom\"><i class=\"ace-icon fa fa-pencil bigger-120\"></i></button><button class=\"btn btn-xs btn-danger\" onclick=\"javascript:deleteAccount('" + aData.id +"')\" data-rel=\"tooltip\" title=\"账号删除\" data-placement=\"bottom\"><i class=\"ace-icon fa fa-trash-o bigger-120\"></i></button></div>"); 	 
					return nRow;
				},
				"fnInitComplete": function(oSettings, json) {
					$('[data-rel=tooltip]').tooltip({container:'body'});
				},
				"fnDrawCallback": function(oSettings) {
					$('[data-rel=tooltip]').tooltip({container:'body'});
				}
				
				
			} );
			
			$(document).on('click', '#btn-query' , function(){
				$("#search-form div.modal-header h4").html("<i class='ace-icon fa fa-search'></i> 精确查询");
				$("#search-form").modal("show");
			});
		
			$(document).on("click", "#executeSearch", function(e) { //执行查询
				$('#sample-table').dataTable().fnPageChange(0);
				$("#search-form").modal("hide");
			});
		
			$(document).on('click', 'th input:checkbox' , function(){
				var that = this;
				$(this).closest('table').find('tr > td:first-child input:checkbox')
				.each(function(){
					this.checked = that.checked;
					$(this).closest('tr').toggleClass('selected');
				});
			});
			
			$('[data-rel=tooltip]').tooltip({container:'body'});
			
		})
		
		function deleteAccount(id) { // 动态绑定所有删除按钮的click事件
			bootbox.confirm({
				message: "您确定要删除此记录吗？",
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
						$.post("${ctx}/admin/weixinAccount/delete/" + id, function(r) {
							if (r.success) {
								bootbox.alert("用户删除成功！");
								$('#sample-table').dataTable().fnDraw(); // 重新加载表格中的数据
								//$("#modal-form").modal("hide");
							}
						});
					}
				}
			});
		}
	</script>
</rapid:override>
<%@ include file="../base.jsp" %>