<%@page contentType="text/html; charset=UTF-8"%>
<!-- BEGIN PAGE CONTENT-->
<div class="row">
	<div class="col-md-12">
		<div class="tabbable-line boxless">
			<ul class="nav nav-tabs">
				<li class="active">
					<a href="#tab_1" data-toggle="tab">基本的</a>
				</li>
				<li>
					<a href="#tab_2" data-toggle="tab">页面集成</a>
				</li>
				<li>
					<a href="#tab_3" data-toggle="tab">可视化效果</a>
				</li>
				<li>
					<a href="#tab_4" data-toggle="tab">动画演示</a>
				</li>
				<li>
					<a href="#tab_5" data-toggle="tab">实时API</a>
				</li>
				<li>
					<a href="#tab_6" data-toggle="tab">定制样式</a>
				</li>
				<li>
					<a href="#tab_7" data-toggle="tab">没有图像</a>
				</li>
				<li>
					<a href="#tab_8" data-toggle="tab">PHP剪裁</a>
				</li>
			</ul>
			<div class="tab-content">
				<div class="tab-pane active" id="tab_1">
					<h4>
						This example demonstrates the default behavior of Jcrop.
					</h4>
					<p>
						Since no event handlers have been attached it only performs the cropping behavior.
					</p>
					<img src="../../assets/global/plugins/jcrop/demos/demo_files/image1.jpg" id="demo1" alt="Jcrop Example" class="img-responsive" />
				</div>
				<div class="tab-pane" id="tab_2">
					<div class="row">
						<div class="col-md-7 col-sm-12 responsive-1024">
							<h4>
								An example with a basic event handler.
							</h4>
							<p>
								Here we've tied several form values together with a simple event handler invocation. The result is that the form values are updated in real-time as the selection is changed using Jcrop's
								<em>onChange</em> handler.
							</p>
							<img src="../../assets/global/plugins/jcrop/demos/demo_files/image2.jpg" id="demo2" alt="Jcrop Example" class="img-responsive" />
						</div>
						<div class="col-md-5 col-sm-12 responsive-1024">
							<h4>
								Cropped Image Details:
							</h4>
							<form id="coords" class="coords form-inline" onsubmit="return false;" action="#">
								<table class="table table-bordered">
									<tr>
										<td>
											<label class="control-label">
												X1
											</label>
										</td>
										<td>
											<input type="text" id="x1" name="x1" class="form-control btn-sm" />
										</td>
									</tr>
									<tr>
										<td>
											<label class="control-label">
												Y1
											</label>
										</td>
										<td>
											<input type="text" id="y1" name="y1" class="form-control btn-sm" />
										</td>
									</tr>
									<tr>
										<td>
											<label class="control-label">
												X2
											</label>
										</td>
										<td>
											<input type="text" id="x2" name="x2" class="form-control btn-sm" />
										</td>
									</tr>
									<tr>
										<td>
											<label class="control-label">
												Y2
											</label>
										</td>
										<td>
											<input type="text" id="y2" name="y2" class="form-control btn-sm" />
										</td>
									</tr>
									<tr>
										<td>
											<label class="control-label">
												Width
											</label>
										</td>
										<td>
											<input type="text" id="w" name="w" class="form-control btn-sm" />
										</td>
									</tr>
									<tr>
										<td>
											<label class="control-label">
												Height
											</label>
										</td>
										<td>
											<input type="text" id="h" name="h" class="form-control btn-sm" />
										</td>
									</tr>
								</table>
							</form>
						</div>
					</div>
				</div>
				<div class="tab-pane" id="tab_3">
					<h4>
						An example implementing a preview pane.
					</h4>
					<p>
						Obviously the most visual demo, the preview pane is accomplished entirely outside of Jcrop with a simple jQuery-flavored callback. This type of interface could be useful for creating a thumbnail or avatar. The onChange event handler is used to update the view in the preview pane.
					</p>
					<div class="row">
						<div class="col-md-6 responsive-1024">
							<img src="../../assets/global/plugins/jcrop/demos/demo_files/image3.jpg" id="demo3" alt="Jcrop Example" />
						</div>
						<div class="col-md-6 responsive-1024">
							<div id="preview-pane">
								<div class="preview-container">
									<img src="../../assets/global/plugins/jcrop/demos/demo_files/image3.jpg" class="jcrop-preview" alt="Preview" />
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="tab-pane" id="tab_4">
					<div class="row">
						<div class="col-md-7 responsive-1024">
							<img src="../../assets/global/plugins/jcrop/demos/demo_files/image5.jpg" id="demo4" alt="Jcrop Example" />
							<div id="shadetxt" style="display: none !important">
								<h4>
									Experimental shader active.
								</h4>
								Jcrop now includes a shading mode that facilitates building better transparent Jcrop instances. The experimental shader is less robust than Jcrop's default shading method and should only be used if you require this functionality.
							</div>
							<div>
								<h4>
									Animation/Transitions.
								</h4>
								Demonstration of animateTo API method and transitions for bgColor and bgOpacity options. Color fading requires inclusion of John Resig's jQuery
								<a href="http://plugins.jquery.com/project/color"> Color Animations </a> plugin. If it is not included, colors will not fade.
							</div>
						</div>
						<div class="col-md-5 responsive-1024">
							<div class="row">
								<div class="col-md-12" id="interface">
									<div class="checkbox-list margin-top-10">
										<label>
											<input type="checkbox" id="fadetog" />
											Enable fading (bgFade: true)
										</label>
										<label>
											<input type="checkbox" id="shadetog" />
											Use experimental shader (shade: true)
										</label>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="tab-pane" id="tab_5">
					<div class="row">
						<div class="col-md-7 responsive-1024">
							<img src="../../assets/global/plugins/jcrop/demos/demo_files/image5.jpg" id="demo5" alt="Jcrop Example" />
						</div>
						<div class="col-md-5 util-btn-margin-bottom-5 responsive-1024">
							<h3>
								Realtime API
							</h3>
							<span class="requiresjcrop">
								<button id="setSelect" class="btn default">
									setSelect
								</button>
								<button id="animateTo" class="btn default">
									animateTo
								</button>
								<button id="release" class="btn default">
									Release
								</button>
								<button id="disable" class="btn default">
									Disable
								</button> </span>
							<button id="enable" class="btn default" style="display: none;">
								Re-Enable
							</button>
							<button id="unhook" class="btn default">
								Destroy!
							</button>
							<button id="rehook" class="btn default" style="display: none;">
								Attach Jcrop
							</button>
							<fieldset class="optdual requiresjcrop">
								<h4>
									Option Toggles
								</h4>
								<div class="checkbox-list">
									<label>
										<input type="checkbox" id="ar_lock" />
										Aspect ratio
									</label>
									<label>
										<input type="checkbox" id="size_lock" />
										minSize/maxSize setting
									</label>
									<label>
										<input type="checkbox" id="can_click" />
										Allow new selections
									</label>
									<label>
										<input type="checkbox" id="can_move" />
										Selection can be moved
									</label>
									<label>
										<input type="checkbox" id="can_size" />
										Resizable selection
									</label>
								</div>
							</fieldset>
							<fieldset class="requiresjcrop">
								<h4>
									Change Image
								</h4>
								<div class="btn-group">
									<button class="btn default" id="img2">
										Pool
									</button>
									<button class="btn default active" id="img1">
										Sago
									</button>
									<button class="btn default" id="img3">
										Sago w/ outerImage
									</button>
								</div>
							</fieldset>
						</div>
					</div>
				</div>
				<div class="tab-pane" id="tab_6">
					<div class="row">
						<div class="col-md-7 responsive-1024">
							<img src="../../assets/global/plugins/jcrop/demos/demo_files/image4.jpg" id="demo6" alt="Jcrop Example" />
						</div>
						<div class="col-md-5 responsive-1024">
							<h4>
								Manipulate classes
							</h4>
							<div class="btn-group" id="buttonbar">
								<button id="radio1" data-setclass="jcrop-light" class="btn active">
									jcrop-light
								</button>
								<button id="radio2" data-setclass="jcrop-dark" class="btn">
									jcrop-dark
								</button>
								<button id="radio3" data-setclass="jcrop-normal" class="btn">
									normal
								</button>
							</div>
							<p>
								<br>
								<b>Example styling tricks.</b> Click the buttons above to change the appearance of Jcrop in real-time.
							</p>
						</div>
					</div>
				</div>
				<div class="tab-pane" id="tab_7">
					<p id="demo7">
						<span style="display: block; padding: .5em 1em;"> This is an example of attaching Jcrop to a target that is not an image. You are now cropping a paragraph tag. </span>
					</p>
					<h4>
						Attaching Jcrop to a non-image element.
					</h4>
					<p>
						This is mostly useful to implement other interfaces, such as
						<code>
							canvas
						</code>
						or over an arbitrary
						<code>
							div
						</code>
						.
					</p>
				</div>
				<div class="tab-pane" id="tab_8">
					<div class="row">
						<div class="col-md-7 responsive-1024">
							<!-- This is the image we're attaching Jcrop to -->
							<img src="../../assets/global/plugins/jcrop/demos/demo_files/image5.jpg" id="demo8" alt="Jcrop Example" />
						</div>
						<div class="col-md-5 responsive-1024">
							<h4>
								An example server-side crop script.
							</h4>
							<p>
								Hidden form values are set when a selection is made. If you press the
								<i>Crop Image</i> button, the form will be submitted and a 150x150 thumbnail will be dumped to the browser. Try it!
							</p>
							<!-- This is the form that our event handler fills -->
							<form action="http://103.248.220.228/ylxxt/image.php" target="_blank" method="post" id="demo8_form">
								<input type="hidden" id="crop_x" name="x" />
								<input type="hidden" id="crop_y" name="y" />
								<input type="hidden" id="crop_w" name="w" />
								<input type="hidden" id="crop_h" name="h" />
								<input type="submit" value="Crop Image" class="btn btn-large green" />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- END PAGE CONTENT-->
<!-- 页面样式和JS -->
<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
<!-- BEGIN PAGE LEVEL STYLES -->
<link href="../../assets/global/plugins/jcrop/css/jquery.Jcrop.min.css" rel="stylesheet"/>
<link href="../../assets/admin/pages/css/image-crop.css" rel="stylesheet"/>
<!-- END PAGE LEVEL STYLES -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script src="../../assets/global/plugins/jcrop/js/jquery.color.js"></script>
<script src="../../assets/global/plugins/jcrop/js/jquery.Jcrop.min.js"></script>
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="../../assets/admin/pages/scripts/form-image-crop.js"></script>
<!-- END PAGE LEVEL SCRIPTS -->
<script src="../../assets/module/scripts/project/notice/image_clip.js"></script>
