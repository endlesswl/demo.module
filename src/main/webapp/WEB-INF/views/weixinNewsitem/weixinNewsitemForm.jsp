<%@include file="/includes/common/taglibs.jsp" %>
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
		</ul><!-- /.breadcrumb -->
	</div>
	<!-- 当前所在位置结束-->	
	<div class="page-content">
		<div class="page-header">
			<h1>
				<a href ="${ctx}/admin/weixinAutoresponse/${accountId}">消息管理</a>
				<small>
					<i class="ace-icon fa fa-angle-double-right"></i>
					模版<c:choose><c:when test="${weixinNewsitem!=null&&weixinNewsitem.id!=null}">编辑</c:when><c:otherwise>新增</c:otherwise></c:choose>
				</small>
			</h1>
		</div><!-- /.page-header -->
		<div class="row">
			<div class="col-xs-12">
				<!-- PAGE CONTENT BEGINS -->
				<form class="form-horizontal" id="weixinAccountForm" action="${ctx}/admin/weixinNewsitem/${accountId}/${action}" method="post">
					<input name="id" value="${weixinNewsitem.id}" type="hidden"/>
					<input name="accountId" value="${accountId}" type="hidden"/>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-1">名称</label>
						<div class="col-sm-9">
							<input type="text" id="form-field-1" class="validate[required] col-xs-10 col-sm-5"   name="name" value="${weixinNewsitem.name}"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-2">消息标题</label>
						<div class="col-sm-9">
							<input type="text" id="form-field-2" class="validate[required] col-xs-10 col-sm-5"   name="title" value="${weixinNewsitem.title}"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right"  data-rel="tooltip"  data-placement="right">状态 </label>
						<div class="col-sm-9">
							<div class="clearfix">
								<select class="col-xs-10 col-sm-5" name="flag">
									<option value="启用"  <c:if test="${'启用'==weixinNewsitem.flag}">selected="selected"</c:if>>启用</option>
									<option value="停用" <c:if test="${'停用'==weixinNewsitem.flag}">selected="selected"</c:if>>停用</option>
								</select>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-3">消息URL</label>
						<div class="col-sm-9">
							<input type="text" id="form-field-3" class="validate[required,custom[url]] col-xs-10 col-sm-5"   name="url" value="${weixinNewsitem.url}"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-imagepath">图片URL</label>
						<div class="col-sm-4">
							<input type="text" id="form-imagepath" class="validate[required,custom[url]] col-xs-11"   name="imagepath" value="${weixinNewsitem.imagepath}"/>
						</div><div class="col-sm-5"> <input id="pictureAjax" type="file"></div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-5">消息内容</label>
						<div class="col-sm-9">
							<textarea id="form-field-5"  class="col-xs-10 col-sm-5" name="description">${weixinNewsitem.description}</textarea>
						</div>
					</div>
					
					<%-- <div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="form-field-6" >备注</label>
						<div class="col-sm-9">
							<textarea id="form-field-6"  class="col-xs-10 col-sm-5" name="note">${weixinNewsitem.note}</textarea>
						</div>
					</div> --%>
				</form>
				<div class="clearfix form-actions">
					<div class="col-md-offset-3 col-md-9">
						<button id="btn-submit" class="btn btn-sm btn-info" type="button" data-loading-text="正在保存..">
							<i class="ace-icon fa fa-floppy-o"></i>
							保存
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
</rapid:override> 
<rapid:override name="css-js">
	<link rel="stylesheet" href="${ctx}/jQuery-Validation/css/validationEngine.jquery.css" type="text/css"/>
		<link rel="stylesheet" href="${ctx}/assets/css/colorpicker.css" />
	<!-- page specific plugin scripts -->
	<script src="${ctx}/jQuery-Validation/js/jquery.validationEngine.js"></script>
	<script src="${ctx}/jQuery-Validation/js/languages/jquery.validationEngine-zh_CN.js"></script>
	<!--  uploader -->
	<link rel="stylesheet" type="text/css" href="${ctx}/includes/uploadify/uploadify.css">
	<script src="${ctx}/includes/uploadify/jquery.uploadify.min.js?ver=<%=(new Random()).nextInt(10000)%>" type="text/javascript"></script>
	
	<script src="${ctx}/assets/js/bootbox.min.js"></script>	
</rapid:override> 
<rapid:override name="javaScript">
	<script type="text/javascript">
		var jsessionid ='<%=session.getId()%>';
		jQuery(function($) {
						
			$(document).on("click", "#btn-submit", function(e) {
				//alert($("#weixinAccountForm").validationEngine('validate'));
				if ($("#weixinAccountForm").validationEngine('validate')) {
					$("#btn-submit").button("loading");
					$("#weixinAccountForm").submit();
				}
			});
			$('[data-rel=tooltip]').tooltip({container:'body'});

			$('#pictureAjax').uploadify({
				'swf' : '${ctx}/includes/uploadify/uploadify.swf',
				'uploader' : '${ctx}/admin/fileupload/uploadImgRU;jsessionid=' + jsessionid,
				'multi' : false,
				'formData' : {
					'argName' : 'picture'
				},
				'buttonText' : '选择文件',
				'width' : 100,
				'height' : 30,
				'fileTypeDesc' : '支持的格式:',
				'fileTypeExts' : '*.jpg;*.png;*.gif;*.bmp',
				'fileSizeLimit' : '2MB',
				'onUploadSuccess' : function(file, data, response) {
					var obj = jQuery.parseJSON(data);
					console.log(data);
					console.log(data.success);
					console.log(data.msg);
				//{"success":true,"msg":"http://image.lingcaibao.com/image/2014/11/2014_11_b4abdd8c4a2b44f28871bff6c5afcb7c.jpg","data":null} 
					if(obj.success){
						$("#form-imagepath").val(obj.msg);
						bootbox.alert("文件上传成功!");
					}else{
						bootbox.alert(obj.msg);
					}
					
					
					// 								if (obj.resultCode == 0) {
					// 									var arr = obj.retultMsg;
					// 									console.log(arr[0].filePath);
					// 									$("#msgimageUrl").val(arr[0].filePath)
					// 									$("#picturePreview").attr("src",
					// 											getRootPath() + arr[0].filePath);
					// 									$("#imageUrl").val(
					// 											getRootPath() + arr[0].filePath);
					// 									$("#img1").attr("src",
					// 											getRootPath() + arr[0].filePath);
					// 									$("#image-show").show();
					// 								} else {
					// 									console.log("上传图片失败！")
					// 								}
				}
			});
			
		})
	</script>
</rapid:override>
<%@ include file="../base.jsp" %>