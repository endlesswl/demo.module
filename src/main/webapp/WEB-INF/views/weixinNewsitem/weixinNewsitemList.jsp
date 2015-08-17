<%@include file="/includes/common/taglibs.jsp" %>
<%@ page language="java" import="com.lingcaibao.weixin.maneger.entity.WeixinNewsitem" pageEncoding="UTF-8" %>
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
			<li class="active">公众账号<<span class="smaller lighter purple">${weixinAccount.accountName}</span>>管理</li>
		</ul>
	</div>
	<!-- 当前所在位置结束-->	
	<div class="page-content">
		<div class="page-header">
			<h1>
				<a href="${ctx}/admin/weixinAutoresponse/${accountId}">消息管理</a>
				<small>
					<i class="ace-icon fa fa-angle-double-right"></i>
					模版列表
				</small>
				<span class="my-button-group">
					<button onclick="javascript:window.location.href='${ctx}/admin/weixinNewsitem/upload/${accountId}'" class="btn btn-sm  btn-warning"><i class="ace-icon fa fa-cloud-upload"></i> 模版上传</button>
					<button id="btn-query" class="btn btn-sm btn-purple"><i class="ace-icon fa fa-search"></i> 精确查询</button>
					<button onclick="javascript:window.location.href='${ctx}/admin/weixinNewsitem/${accountId}/create'" class="btn btn-sm btn-success"><i class="ace-icon fa fa-plus"></i> 新增</button>
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
									<th class="center" width="40">
									<!--
										<label class="position-relative">
											<input type="checkbox" class="ace" />
											<span class="lbl"></span>
										</label>-->
									</th>
									<th class="center" width="30%"><%=WeixinNewsitem.ALIAS_NAME%></th>
									<th class="center"><%=WeixinNewsitem.ALIAS_TITLE%></th>
									<!--
									<th class="center" width="140"><%=WeixinNewsitem.ALIAS_DESCRIPTION%></th>
									<th class="center"><%=WeixinNewsitem.ALIAS_URL%></th>
									<th class="center"><%=WeixinNewsitem.ALIAS_IMAGEPATH%></th>-->
									<th class="center" width="90">操作</th>
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
											<input type="text" id="s_name" class="form-control" value=""/>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label no-padding-right" for="form-field-2">类型</label>
										<div class="col-sm-8">
											<select class="form-control" data-placeholder="选择类型"  id="s_msgtype">
												<option value="">全部</option>
												<option value="消息">消息</option>
												<option value="事件">事件</option>
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
				"sAjaxSource": "${ctx}/admin/weixinNewsitem/${accountId}/list", //给服务器发请求的url
				fnServerParams: function (aoData) {
				    aoData.push({"name": "s_name", "value": $("#s_name").val()});
				    aoData.push({"name": "s_msgtype", "value": $("#s_msgtype").val()});
				    
				},
				"aoColumns": [
					{"sDefaultContent": '', "sClass": "center"}, //mData 表示发请求时候本列的列明，返回的数据中相同下标名字的数据会填充到这一列
					{"mData": 'name'},
					{"mData": 'title'},
					//{"mData": 'description'},
					//{"mData": 'url'},
					//{"mData": 'imagepath'},
					{"sDefaultContent": '',"sClass": "center"},//sClass 表示给本列加class
				],
				"fnRowCallback": function(nRow, aData, iDisplayIndex) {// 当创建了行，但还未绘制到屏幕上的时候调用，通常用于改变行的class风格 
					$('td:eq(0)', nRow).html(iDisplayIndex + 1);
					$('td:eq(3)', nRow).html("<div class=\"hidden-sm hidden-xs btn-group\"><button class=\"btn btn-xs btn-info\" onclick=\"javascript:window.location.href='${ctx}/admin/weixinNewsitem/${accountId}/update/" + aData.id + "'\"><i class=\"ace-icon fa fa-pencil bigger-120\"></i></button><button class=\"btn btn-xs btn-danger\" onclick=\"javascript:deleteAccount('" + aData.id +"')\"><i class=\"ace-icon fa fa-trash-o bigger-120\"></i></button></div>");
					return nRow;
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
						$.post("${ctx}/admin/weixinNewsitem/delete/" + id, function(r) {
							if (r.success) {
								bootbox.alert("预设消息图文删除成功！");
								$('#sample-table').dataTable().fnDraw(); // 重新加载表格中的数据
								//$("#modal-form").modal("hide");
							}else if (!r.success){
								bootbox.alert(r.msg);
								//$('#sample-table').dataTable().fnDraw(); // 重新加载表格中的数据
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