/* ========================================================================
 * 用于构建树形表达式的模态框: zyc-tree-expression-modal.js
 * ========================================================================
 * Author: ZhouYancheng
 * Created at: 2018-10
 * ======================================================================== */

+function ($) {
	'use strict';
	
	//@options: Example: [{ value: '', text: '' }]
	var buildSelectOptionHtmls = function(options) {
		var htmls = [];
		for(var i = 0; i < options.length; i++) {
			htmls.push('<option value="' + options[i].value + '">' + options[i].text + '</option>');
		}
		return htmls;
	}
	
	var _apendExpression = function(expression, expressionText, expressionHtml) {
		var that = this;
		var exists = false; //用于判重，如果已经存在，不需要再添加了。
		that.$expression.find('a.list-group-item').each(function() {
			if($(this).attr('data-bind-value') === expression) {
				exists = true;
				return false;
			}
		});
		
		if(!exists) {
			var $item = $('<a href="#" class="list-group-item" data-bind-value="' + expression + '" data-bind-text="' + expressionText + '">' + expressionHtml + '</a>');
			$item.off('click').on('click', function(e) {
				var active = $(this).hasClass('active');
				if(!active) {
					that.$expression.find('a.list-group-item.active:not([data-bind-value="' + expression + '"])').removeClass('active');
				}
				$(this)[active ? 'removeClass' : 'addClass']('active');
			});
			that.$expression.append($item);	
		}
	}
	
	var _setup = function(e) {
		var nodes = this.$treeSource.treeview('getSelected')
			, memberValues = []
			, memberTexts = [];
		
		if($.isArray(nodes)) {
			for(var i = 0; i < nodes.length; i++) {
				memberValues.push(this.options.sourceGetNodeBoundValue(nodes[i]));
				memberTexts.push(this.options.sourceGetNodeBoundText(nodes[i]));
			}
		}
		
		if(!memberValues.length) {
			alert('请在左侧数据源中至少选择一个数据节点！');
			return;
		}
		
		var	operate = this.$operate.val()
			, operateText = this.$modal.find('.bootstrap-select.operate div.filter-option-inner-inner').text() || ''
			, relation = this.$relation.val()
			, relationText = this.$modal.find('.bootstrap-select.relation div.filter-option-inner-inner').text() || ''
			, expression = operate + '@' + relation + '(' + memberValues.join(',') + ');'
			, expressionText = operateText + '@' + relationText + '(' + memberTexts.join(',') + ');'
			, expressionHtml = '<code class="operate">' + operateText + '</code><code class="relation">@' + relationText + '</code><code class="value">(' + memberTexts.join(',') + ');</code>';
		
		_apendExpression.apply(this, [expression, expressionText, expressionHtml]);
	}
	
	var _remove = function(e) {
		var $items = this.$expression.find('a.list-group-item.active');
		if(!$items.length) {
			alert('请从右边结果集中选择待移除的表达式！');
			return;
		}
		$items.remove();
	}
	
	var _removeall = function(e) {
		if(confirm("您确认要移除右侧所有表达式？")) {
			this.$expression.html('');
		}
	}
	
	var _hasChanges = function () {
		var old = this.options.expressionResult
			, count = 0
			, existsCount = 0;
		this.$expression.find('a.list-group-item').each(function() {
			var val = $(this).attr('data-bind-value');
			for (var i = 0; i < old.length; i++) {
				if(val === old.value) {
					existsCount++;
					break;
				}
			}
			count++;
		});
		
		return old.length !== count || old.length !== existsCount;
	} 
	
	var _cancel = function(e) {
		if(_hasChanges.apply(this, [])) {
			if(confirm("数据发生了更改，是否取消这些更改？")) {
				this.hide();
			}
		} else {
			this.hide();
		}
	}
	
	var _save = function(e) {
		if(!_hasChanges.apply(this, [])) {
			if(confirm('数据没有发生变化，不需要保存。是否关闭窗口？')) {
				this.hide();
			}
		} else {
			this.hide();
			
			var expressionResult = [];
			this.$expression.find('a.list-group-item').each(function() {
				expressionResult.push({ value: $(this).attr('data-bind-value'), text: $(this).attr('data-bind-text') });
			});
			this.options.onSaveExpressionResult(expressionResult);
		}
	}
		
	// TreeExpressionModal Class Definition
	var TreeExpressionModal = function (container, options) {
		this.options = options;
		this.$container = $(container || document.body);
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
		sourceLoadingIcon: 'fa fa-hourglass',
		sourceLoadDataUrl: null,
		sourceGetParentIdForLoading: function(node) {
			return node.attrs.id;
		},
		sourceGetNodeBoundValue: function(node) {
			return node.attrs.membercode;
		},
		sourceGetNodeBoundText: function(node) {
			return node.attrs.membername;
		},
		operateOptions: [],
		relationOptions: [],
		expressionResult: [],
		onSaveExpressionResult: function(expressionResult) {
			//code...
		},
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
			      			'<div class="tree-source-wrap">' +
			      		  		'<div class="tree-source" />' +
				      		'</div>' +
		      			'</div>' +
		      			'<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3 middle">' +
			      			'<div class="page-header">' +
				      		  	'<h5>' + this.options.middleTitle + '</h5>' +
				      		'</div>' +
			      			'<div class="operate-wrap">' +
		      					'<select class="selectpicker operate" data-bind-type="operate">' +
		      						buildSelectOptionHtmls(this.options.operateOptions).join('') +
				      			'</select>' +
				      			'<div class="isolate"></div>' +
			      				'<select class="selectpicker relation" data-bind-type="relation">' +
			      					buildSelectOptionHtmls(this.options.relationOptions).join('') +
				      			'</select>' +
				      			'<div class="isolate-max"></div>' +
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
			      			'<div class="expression-wrap">' +
				      		  	'<div class="list-group expression">' +
				      		  	'</div>' +
				      		'</div>' +
		      			'</div>' +
		      		'</div>' +
		      		'<div class="modal-footer">' +
		        		'<button type="button" class="btn btn-default cancel">' + this.options.button.cancel.text + '</button>' +
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
			    url: that.options.sourceLoadDataUrl,
			    data: { 
			    	parentId: node && that.options.sourceGetParentIdForLoading(node) || '' 
			    },
				async: _async,
				dataType: 'json',
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
		
		this.$treeSource = this.$modal.find('.left .tree-source').treeview({
			data: _loadData(null, null, false),
			loadingIcon: that.options.sourceLoadingIcon,
			levels: 2,
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

		this.$operate = this.$modal.find('.middle .selectpicker.operate').selectpicker({ width: '120' });
		this.$relation = this.$modal.find('.middle .selectpicker.relation').selectpicker({ width: '120' });
		this.$setup = this.$modal.find('.middle .btn.setup').off('click').on('click', $.proxy(_setup, this));
		this.$remove = this.$modal.find('.middle .btn.remove').off('click').on('click', $.proxy(_remove, this));
		this.$removeall = this.$modal.find('.middle .btn.removeall').off('click').on('click', $.proxy(_removeall, this));
		
		this.$expression = this.$modal.find('.right div.expression');
		this.$expression.html('');
		
		this.$cancel = this.$modal.find('.modal-footer .btn.cancel').off('click').on('click', $.proxy(_cancel, this));
		this.$save = this.$modal.find('.modal-footer .btn.save').off('click').on('click', $.proxy(_save, this));
		
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
	
	TreeExpressionModal.prototype.hide = function(_relatedTarget) {
		this.$modal.modal('hide');
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