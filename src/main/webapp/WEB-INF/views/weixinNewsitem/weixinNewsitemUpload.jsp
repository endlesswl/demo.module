<%@include file="/includes/common/taglibs.jsp"%>
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
			<li class="active">公众帐号&lt;<span class="smaller lighter purple">${weixinAccount.accountName}</span>&gt;消息管理</li>
		</ul><!-- /.breadcrumb -->
	</div>
	<!-- 当前所在位置结束-->	
	<div class="page-content">
		<div class="page-header">
			<h1>
				<a href ="${ctx}/admin/weixinNewsitem/${accountId}">模版消息管理</a>
				<small>
					<i class="ace-icon fa fa-angle-double-right"></i>
					模版消息上传
				</small>
			</h1>
		</div>
		<!-- /.page-header -->
		<div class="row">
			<div class="col-xs-12">
				<form  class="form-horizontal" id="codeUploadForm" target="actionframe"> 
					<input type="hidden" id="filePath" name="filePath" />
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-field-1">账号名称</label>
						<div class="col-sm-9">
							<input type="text" id="form-field-1" class="col-xs-10 col-sm-5"
								value="${weixinAccount.accountName}"
								readonly="readonly" />
						</div>
					</div>
					<%-- 		
					<label>资源类型：</label> <input name="type" type="text" /><br /> 
					<label>有效开始时间：</label>
					<input name="beginTime" type="text" /> <br /> 
					<label>失效时间：</label> 
					<input name="endTime" type="text" />
					<label>文件：</label> <input type="file" size="60" id="pictureAjax" name="pictureAjax" />  <br />
 --%>
					<!-- <input type="text" id="datepicker" class="form-control" /> -->
					<!-- 	<span class="input-group-addon"> <i class="ace-icon fa fa-calendar"></i></span> <br />  -->
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"
							for="form-field-1">文件</label>
						<div class="col-sm-9">
							<input type="file" size="60" id="pictureAjax" name="pictureAjax" />
						</div>
					</div>
					
					<!-- <button type="submit" class="btn" id="search_btn">submit</button> -->
				</form> 
				<div class="clearfix form-actions">
					<div class="col-md-offset-3 col-md-9">
						<button id="btn-submit" class="btn btn-sm btn-info" type="button" data-loading-text="正在保存..">
							<i class="ace-icon fa fa-floppy-o"></i>
							上传
						</button>
						&nbsp; &nbsp; &nbsp;
						&nbsp; &nbsp; &nbsp;
						<a href="${ctx}/admin/weixinNewsitem/${accountId}">
						<button id="btn-back" class="btn btn-sm btn-grey" type="reset">
							<i class="ace-icon fa fa-undo bigger-110"></i>
							返回
						</button></a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<iframe width="0" height="0" name="actionframe"></iframe>
</rapid:override>

<rapid:override name="css-js">
	<link rel="stylesheet" type="text/css" href="${ctx}/includes/uploadify/uploadify.css">
	<script src="${ctx}/includes/uploadify/jquery.uploadify.min.js" type="text/javascript"></script>
	<script src="${ctx}/assets/js/bootbox.min.js"></script>
	<script type="text/javascript">
		var jsessionid ='<%=session.getId() %>';
		$(function() {
			$(document).on("click", "#btn-submit", function(e) {
				$("#btn-submit").button("loading");
				//$("#codeUploadForm").submit();
				ajaxFileUpload();
			});
			
			$('#pictureAjax').uploadify({
				'swf' : '${ctx}/includes/uploadify/uploadify.swf',
				'uploader' : '${ctx}/admin/weixinNewsitem/upload;jsessionid=' + jsessionid,
				'multi' : false,
				'formData' : {
					'argName' : 'picture'
				},
				'buttonText' : '选择文件',
				'width' : 100,
				'height' : 30,
				'fileTypeDesc' : '支持的格式:',
				'fileTypeExts' : '*.xls',
				'fileSizeLimit' : '1MB',
				'onUploadSuccess' : function(file, data, response) {
					//var obj = jQuery.parseJSON(data);
					bootbox.alert("文件："+file.name+"上传成功！");
					console.log(data);
					$("#filePath").val(data);
				}
			});
		})

		function ajaxFileUpload() {
			console.log($('#codeUploadForm').serialize());
			$.ajax({
	          	type: "POST",
	            url: "${ctx}/admin/weixinNewsitem/upload/${accountId}?" + Math.floor(Math.random() * 100),
				data:$('#codeUploadForm').serialize(),
		        cache: false,
	            success: function (msg) {
	            	console.log(msg);
	            	if(msg.ssuccess){
	            		bootbox.alert(msg.data);
		            }else{
		            	bootbox.alert(msg.data);
			        }
	          	}
	          }); 
		}
	</script>
</rapid:override>
<%@ include file="../base.jsp" %>
