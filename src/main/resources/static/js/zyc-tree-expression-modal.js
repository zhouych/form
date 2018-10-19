/* ========================================================================
 * 用于构建树形表达式的模态框: zyc-tree-expression-modal.js
 * ========================================================================
 * Author: ZhouYancheng
 * Created at: 2018-10
 * ======================================================================== */

+function ($) {
	'use strict';
	
	//TreeExpressionModal data
	var expressionStructure = {
		operate: {
			value: '',
			text: ''
		},
		relation: {
			value: '',
			text: ''
		}
	};

	//fill TreeExpressionModal data
	var fillExpressionStructure = function(structure) {
		expressionStructure = $.extend(true, expressionStructure, structure);
	}
	
	//get TreeExpressionModal data
	var getExpressionStructure = function() {
		return expressionStructure;
	}
	
	//@options: Example: [{ value: '', text: '' }]
	var buildSelectOptionHtmls = function(options) {
		var htmls = [];
		for(var i = 0; i < options.length; i++) {
			htmls.push('<option value="' + options[i].value + '">' + options[i].text + '</option>');
		}
		return htmls;
	}
		
	// TreeExpressionModal Class Definition
	var TreeExpressionModal = function (container, options) {
		this.options = options;
		this.$container = $(container || document.body);
		this.data = {
			relation: {
				value: '',
				text: ''
			}
		};
		this.init();
	}

	TreeExpressionModal.VERSION  = '1.0';
	  
	TreeExpressionModal.DEFAULTS = {
		id: 'tree_expression_modal',
		title: 'Tree Expression Modal Title',
		button: {
			close: { text: '<span aria-hidden="true">&times;</span>', show: true },
			save: { text: 'Save', show: true },
			cancel: { text: 'Cancel', show: true },
			setup: { text: 'Set up', show: true },
			remove: { text: 'Remove', show: true },
			removeall: { text: 'Remove all', show: true }
		},
		show: true,
		leftTitle: '数据源',
		middleTitle: '操作',
		rightTitle: '结果集',
		leftSourceLoadingIcon: 'fa fa-hourglass',
		leftSourceLoadDataUrl: null,
		leftSourceGetLoadParentId: function(node) {
			alert('参数选项leftSourceGetLazyLoadParentId函数未定义。');
		}
		operateOptions: [],
		relationOptions: [],
		modalOptions: {
		    backdrop: true,
		    keyboard: true,
		    show: true
		}
	}
	
	TreeExpressionModal.prototype.init = function () {
		var that = this, temp;
		
		var modal = 
		'<div class="modal fade bs-example-modal-lg zyc-tem" id="' + this.options.id + '" tabindex="-1" role="dialog" aria-labelledby="' + this.options.id + '_label">' +
			'<div class="modal-dialog modal-lg" role="document">' +
		    	'<div class="modal-content container-fluid">' +
		      		'<div class="modal-header">' +
		        		'<button type="button" class="btn close" data-dismiss="modal" aria-label="Close">' + this.options.button.close.text + '</button>' +
		        		'<h4 class="modal-title" id="' + this.options.id + '_label">' + this.options.title + '</h4>' +
		      		'</div>' +
		      		'<div class="modal-body row">' +
		      			'<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5 left">' + 
			      			'<div class="page-header">' +
				      		  	'<h5>' + this.options.leftTitle + '</h5>' +
				      		'</div>' +
			      			'<div>' +
			      		  		'<div class="tree-source" />' +
				      		'</div>' +
		      			'</div>' +
		      			'<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3 middle">' +
			      			'<div class="page-header">' +
				      		  	'<h5>' + this.options.middleTitle + '</h5>' +
				      		'</div>' +
			      			'<div>' +
		      					'<select class="selectpicker operate" data-bind-type="operate">' +
		      						buildSelectOptionHtmls(this.options.operateOptions).join('') +
				      			'</select>' +
				      			'<div class="isolate"></div>' +
			      				'<select class="selectpicker relation" data-bind-type="relation">' +
			      					buildSelectOptionHtmls(this.options.relationOptions).join('') +
				      			'</select>' +
				      			'<div class="isolate"></div>' +
				      			'<button type="button" class="btn btn-default setup">' + this.options.button.setup.text + '</button>' + 
				      			'<div class="isolate"></div>' +
				      			'<button type="button" class="btn btn-default remove">' + this.options.button.remove.text + '</button>' + 
				      			'<div class="isolate"></div>' +
				      			'<button type="button" class="btn btn-default removeall">' + this.options.button.removeall.text + '</button>' + 
				      		'</div>' +
		      			'</div>' +
		      			'<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4 right">' + 
			      			'<div class="page-header">' +
				      		  	'<h5>' + this.options.rightTitle + '</h5>' +
				      		'</div>' +
			      			'<div>' +
				      		  	'<div class="list-group result">' +
				      		  		'<a href="#" class="list-group-item">Cras justo odio</a>' +
				      		  		'<a href="#" class="list-group-item">Dapibus ac facilisis in</a>' +
				      		  		'<a href="#" class="list-group-item">Morbi leo risus</a>' +
				      		  		'<a href="#" class="list-group-item">Dapibus ac facilisis in</a>' +
				      		  		'<a href="#" class="list-group-item">Morbi leo risus</a>' +
				      		  		'<a href="#" class="list-group-item">Dapibus ac facilisis inDapibus ac facilisis inDapibus ac facilisis in</a>' +
				      		  		'<a href="#" class="list-group-item">Morbi leo risus</a>' +
				      		  	'</div>' +
				      		'</div>' +
		      			'</div>' +
		      		'</div>' +
		      		'<div class="modal-footer">' +
		        		'<button type="button" class="btn btn-default cancel" data-dismiss="modal">' + this.options.button.cancel.text + '</button>' +
		        		'<button type="button" class="btn btn-primary save">' + this.options.button.save.text + '</button>' +
		      		'</div>' +
		    	'</div>' +
			'</div>' +
		'</div>';
		
		this.$modal = $(modal).appendTo(this.$container);
		
		var _loadData = function(node, func, async) {
			var _async = 'undefined' === typeof async ? true : async
				, result = null;
			
			$.ajax({
			    type: 'get',
			    url: that.options.leftSourceLoadDataUrl,
			    data: { 
			    	parentId: node && hat.options.leftSourceGetLoadParentId(node) || '' 
			    },
				async: _async,
			    success: function(response) {
			    	if(response.status === '0') {
			    		result = response.data;
			    		if(typeof func === 'function' && func) {
			    			func(result);
			    		}
			    	} else {
				    	alert(response.status + ': ' + (response.message || ''));
			    	}
			    },
			    error: function(err) {
			    	alert('error');
			    }
			});
			
			if(!_async) {
				return result;
			}
		}
		
		var $treeSource = $('.left .tree-source').treeview({
			data: _loadData(null, null, false),
			loadingIcon: that.options.leftSourceLoadingIcon,
			lazyLoad: _loadData,
			showCheckbox: false, 
			emptyIcon: 'glyphicon', 
			onNodeExpanded: function(ctx, node) {
				//code ...
			},
			onNodeSelected: function(ctx, node) {
				//code ...
			},
			onNodeUnselected: function(ctx, node) {
				//code ...
			}
		});  
		
		$('.selectpicker.operate, .selectpicker.relation').selectpicker({
			width: 'auto'
		}).on('changed.bs.select', function (e, clickedIndex, isSelected, previousValue) {
			var $target = $(e.target || e)
				, type = $target.attr('data-bind-type')
				, value = $target.val() || ''
				, struct = {};
			
			struct[type] = {
				value: value, 
				text: value ? $('.bootstrap-select.' + type + ' div.filter-option-inner-inner').text() : '' 
			};
				
			fillExpressionStructure(struct);
		});
		
		for(var key in this.options.button) {
			if(!this.options.button[key].show) {
				this.$modal.find('div.modal-content button.btn.' + key).hide();
			}
		}

		var modalOptions = $.extend({}, TreeExpressionModal.DEFAULTS.modalOptions, typeof this.options.modalOptions == 'object' && this.options.modalOptions);
		modalOptions.show = this.options.show;
		this.$modal.modal(modalOptions);
	}
	
	TreeExpressionModal.prototype.show = function(_relatedTarget) {
		this.$modal.modal('show');
	}

	// TreeExpressionModal Plugin Definition
	function Plugin(option, _relatedTarget) {
		return this.each(function() {
			var $this = $(this)
				//深度拷贝
				, options = $.extend(true, {}, TreeExpressionModal.DEFAULTS, typeof option == 'object' && option)
				//$this.data()不做深度拷贝
				, options = $.extend(options, $this.data())
				, dataKey = 'bs.TreeExpressionModal.' + options.id
			 	, data = $this.data(dataKey + options.id);

			if (!data) {
				$this.data(dataKey, (data = new TreeExpressionModal(this, options)))
			}
			
			if (typeof option == 'string') {
				data[option](_relatedTarget);
			} else if (options.show) {
				data.show(_relatedTarget);
			}
		});
	}

	var old = $.fn.TreeExpressionModal;
	$.fn.treeexpressionmodal = Plugin;
	$.fn.treeexpressionmodal.Constructor = TreeExpressionModal;
	
	$.fn.treeexpressionmodal.noConflict = function () {
		$.fn.treeexpressionmodal = old
		return this
	}
	
}(jQuery);