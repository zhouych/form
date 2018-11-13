/* ========================================================================
 * 用于构建树形表达式的模态框: zyc-express-modal.js
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
	
	var expressionHtml = function(expressionText) {
		var index = expressionText.indexOf('@')
			, operateText = expressionText.substr(0, index)
			, right = operateText.substr(index + 1)
			, relationText = ''
			, memberText = ''
			, tmp;
		
		for(var key in zyc.memberRelation) {
			tmp = zyc.memberRelation[key];
			if(right.indexOf(tmp.text) === 0) {
				relationText = tmp.text;
				memberText = right.substr(tmp.text.length + 1);
				break;
			}
		}
		
		return formatExpressionNodeHtml(operateText, relationText, memberText);
	}
	
	var formatExpressionNodeHtml = function(operateText, relationText, memberText) {
		return '<code class="operate">' + operateText + '</code><code class="relation">@' + relationText + '</code><code class="value">' + memberText + '</code>';
	}

	var ITEM_HTML = '<a href="#" class="list-group-item" data-bind-value="%s" data-bind-text="%s">%s</a>';
	var _generateListGroupItem = function(expression, expressionText, expressionHtml) {
		var that = this
			, $item = $(zyc.sprintf(ITEM_HTML, expression, expressionText, expressionHtml));
		
		$item.off('click').on('click', function(e) {
			var active = $(this).hasClass('active');
			if(!active) {
				that.$modal.find('div.expression-wrap a.list-group-item.active:not([data-bind-value="' + expression + '"])').removeClass('active');
			}
			$(this)[active ? 'removeClass' : 'addClass']('active');
		});
		
		return $item;
	}
	
	var _apendExpression = function(expression, expressionText, expressionHtml) {
		var $item = this.$expression.find('a.list-group-item[data-bind-value="' + expression + '"]');
		if($item.length === 0) {
			$item = _generateListGroupItem.apply(this, [ expression, expressionText, expressionHtml ]).appendTo(this.$expression);
		}
		
		if(!$item.hasClass('active')) {
			$item.trigger('click');
		}
	}
	
	var _apendExpressionDefaultValue = function(expression, expressionText) {
		this.$default.html('');
		_generateListGroupItem.apply(this, [ expression, expressionText, '@默认值(' + expressionText + ')' ]).appendTo(this.$default).trigger('click');
	}

	var _generateExpression = function(values, texts) {
		var	operate = this.$operate.val()
			//, operateText = this.$modal.find('.bootstrap-select.operate div.filter-option-inner-inner').text() || ''
			, operateText = this.$operate.find('option:selected').text()
			, relation = this.$relation.val()
			//, relationText = this.$modal.find('.bootstrap-select.relation div.filter-option-inner-inner').text() || ''
			, relationText = this.$relation.find('option:selected').text();
		
		return {
			value: operate + '@' + relation + '(' + values.join(',') + ');',
			text: operateText + '@' + relationText + '(' + texts.join(',') + ');',
			html: formatExpressionNodeHtml(operateText, relationText, '(' + texts.join(',') + ');')
		};
	}
	
	var _setup = function(e) {
		var nodes = this.$treeSource.treeview('getSelected')
			, values = []
			, texts = [];

		for(var i = 0, l = $.isArray(nodes) ? nodes.length : 0; i < l; i++) {
			values.push(this.options.sourceGetNodeBoundValue(nodes[i]));
			texts.push(this.options.sourceGetNodeBoundText(nodes[i]));
		}
		
		if(values.length === 0) {
			alert('请在左侧数据源中至少选择一个数据节点！');
			return;
		}
		
		var express = _generateExpression.apply(this, [ values, texts ]);
		_apendExpression.apply(this, [ express.value, express.text, express.html ]);
	}
	
	var _setdft = function(e) {
		var nodes = this.$treeSource.treeview('getSelected')
			, node = $.isArray(nodes) && nodes.length === 1 ? nodes[0] : null;
		
		if(!this.options.onCheckExpressionDefaultValue(nodes)) {
			alert('请在左侧数据源中选择一个数据叶子节点作为默认值！');
		}
		
		var value = this.options.sourceGetNodeBoundValue(nodes[0])
			, text = this.options.sourceGetNodeBoundText(nodes[0]);
		_apendExpressionDefaultValue.apply(this, [ value, text ]);
	}
	
	var _remove = function(e) {
		var $items = this.$modal.find('div.expression-wrap a.list-group-item.active');
		if(!$items.length) {
			alert('请从右边结果集中选择待移除的表达式！');
			return;
		}
		$items.remove();
	}
	
	var _removeall = function(e) {
		if(confirm("您确认要移除右侧所有表达式？")) {
			this.$expression.html('');
			if(this.options.enabledExpressionDefaultValue === true) {
				this.$default.html('');
			}
		}
	}
	
	var _hasChanges = function () {
		var old = this.options.expressionResult
			, count = 0
			, existsCount = 0;
		
		this.$expression.find('a.list-group-item').each(function() {
			var val = $(this).attr('data-bind-value');
			for (var i = 0; i < old.length; i++) {
				if(val === old[i].value) {
					existsCount++;
					break;
				}
			}
			count++;
		});
		
		var defaultHasChanges = false;
		if(this.options.enabledExpressionDefaultValue === true) {
			var dft = this.options.expressionDefaultValue
				, val = this.$default.find('a.list-group-item:first').attr('data-bind-value');
			defaultHasChanges = (val || '') !== (dft && dft.value || '');
		}
		
		return old.length !== count || old.length !== existsCount || defaultHasChanges;
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
		if(_hasChanges.apply(this, [])) {
			var expressionResult = [];
			this.$expression.find('a.list-group-item').each(function() {
				expressionResult.push({ value: $(this).attr('data-bind-value'), text: $(this).attr('data-bind-text') });
			});
			
			var defaultValue = null;
			if(this.options.enabledExpressionDefaultValue === true) {
				var $item = this.$default.find('a.list-group-item:first');
				if($item.length) {
					defaultValue = { value: $item.attr('data-bind-value'), text: $item.attr('data-bind-text') };
				}
			}
			
			if(typeof this.options.onSaveExpressionResult === 'function') {
				this.options.onSaveExpressionResult(expressionResult, defaultValue);
			}
			
			this.hide();
			this.options.expressionResult = expressionResult; //跟新历史数据，保存最新的值。
		} else {
			if(confirm('数据没有发生变化，不需要保存。是否关闭窗口？')) {
				this.hide();
			}
		}
	}
	
	// ExpressModal Class Definition
	var ExpressModal = function (container, options) {
		this.options = options;
		this.$container = $(container || document.body);
		this.init();
	}

	ExpressModal.VERSION  = '1.0';
	  
	ExpressModal.DEFAULTS = {
		id: 'tree_expression_modal',
		title: 'Tree Expression Modal Title',
		button: {
			close: { text: '<span aria-hidden="true">&times;</span>', show: true },
			save: { text: 'Save', show: true },
			cancel: { text: 'Cancel', show: true },
			setup: { text: 'Set up', show: true },
			setdft: { text: 'Set default value', show: true },
			remove: { text: 'Remove', show: true },
			removeall: { text: 'Remove all', show: true }
		},
		show: true,
		leftTitle: '数据源',
		middleTitle: '操作',
		rightTitle: '结果集',
		sourceRootOptional: null,
		sourceRootDefault: null,
		sourceLoadingIcon: 'fa fa-hourglass',
		sourceLoadDataUrl: null,
		sourceLoadDataParam: function(node, root) {
			return {};
		},
		sourceGetNodeBoundValue: function(node) {
			return node.attrs.membercode;
		},
		sourceGetNodeBoundText: function(node) {
			return node.attrs.membername;
		},
		operateOptions: [],
		relationOptions: [],
		enabledExpressionDefaultValue: false,
		expressionDefaultValue: null,
		//针对公式默认值的检查。默认逻辑：公式默认值只能选择一个叶子结点
		onCheckExpressionDefaultValue: function(nodes) {
			var node = $.isArray(nodes) && nodes.length === 1 ? nodes[0] : null;
			return node && !node.attrs.haschildren;
		},
		expressionResult: [],
		onSaveExpressionResult: function(result, defaultValue) {
			//code...
		},
		modalOptions: {
		    backdrop: true,
		    keyboard: true,
		    show: true
		}
	}
	
	ExpressModal.prototype.init = function () {
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
		      		  			'<select class="form-control root-optional">' +
		      		  			'</select>' +
			      		  		'<div class="tree-source" />' +
				      		'</div>' +
		      			'</div>' +
		      			'<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3 middle">' +
			      			'<div class="page-header">' +
				      		  	'<h5>' + this.options.middleTitle + '</h5>' +
				      		'</div>' +
			      			'<div class="operate-wrap">' +
		      					'<select class="form-control operate" data-bind-type="operate">' +
		      						buildSelectOptionHtmls(this.options.operateOptions).join('') +
				      			'</select>' +
				      			'<div class="isolate"></div>' +
			      				'<select class="form-control relation" data-bind-type="relation">' +
			      					buildSelectOptionHtmls(this.options.relationOptions).join('') +
				      			'</select>' +
				      			'<div class="isolate-max"></div>' +
				      			'<button type="button" class="btn btn-default setup">' + this.options.button.setup.text + '</button>' +
				      			(this.options.enabledExpressionDefaultValue === true 
				      				? ('<div class="isolate"></div><button type="button" class="btn btn-default setdft">' + this.options.button.setdft.text + '</button>') : ''
				      			) +
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
			      				(this.options.enabledExpressionDefaultValue === true ? '<div class="list-group default"></div>' : '') +
				      			'<div class="isolate-bottom"></div>' +
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
		
		var loadTreeSourceDataFunc = function(node, func, async) {
			var _async = 'undefined' === typeof async ? true : async
				, result = null;
			
			$.ajax({
			    type: 'get',
			    url: that.options.sourceLoadDataUrl,
			    data: that.options.sourceLoadDataParam(node, that.$rootOptional.val()),
				async: _async,
				dataType: 'json',
			    success: function(response) {
			    	if(response.status === '0') {
			    		result = response.data;
			    		if(typeof func === 'function' && func) {
			    			func(result);
			    		}
			    	} else {
				    	alert(response.status + ': ' + response.message);
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

		this.$rootOptional = this.$modal.find('.left .form-control.root-optional');
		
		//绑定root选项的change事件
		this.$rootOptional.off('change').on('change', function(e) {
			//根据选择root加初始化其成员树
			that.$treeSource = that.$modal.find('.left .tree-source').treeview({
				data: loadTreeSourceDataFunc(null, null, false),
				loadingIcon: that.options.sourceLoadingIcon,
				levels: 2,
				lazyLoad: loadTreeSourceDataFunc,
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
		});
		
		var rootOptional = that.options.sourceRootOptional
			, rootDefault = typeof that.options.sourceRootDefault === 'string' && that.options.sourceRootDefault || null;
		
		//sourceRootOptional支持通过url同步加载，也可以传数组，如：[ { value: 'a', text: 'a' } ]
		if(typeof rootOptional === 'string') {
			//url ...
			$.ajax({
			    type: 'get',
			    url: rootOptional,
			    data: {},
				async: false,
				dataType: 'json',
			    success: function(response) {
			    	if(response.status === '0') {
			    		rootOptional = response.data;
			    	} else {
				    	alert(response.message);
			    	}
			    },
			    error: function(err) {
			    	alert('error');
			    }
			});
		} else {
			//如果没有rootOptional，就取默认值（如果有默认值的话）构建rootOptional
			if(!$.isArray(rootOptional) && rootDefault) {
				rootOptional = [ { value: rootDefault, text: rootDefault } ];
			}
		}
		
		//给根节点可选项添加值（往select追加option）
		this.$rootOptional.html(buildSelectOptionHtmls(rootOptional).join(''));
		//根节点可选项有默认值设置其默认值（select设置默认值）
		if(rootDefault) {
			this.$rootOptional.val(rootOptional[0].value);
		}
		//如果选择了一个有意义的根节点，则触发根节点change事件（select的change事件）
		if(!zyc.invalidBusNode(this.$rootOptional.val())) {
			this.$rootOptional.trigger('change');
		}
		//美化根节点可选项select的外观（依赖bootstrap-select插件）
		//this.$rootOptional.selectpicker({ });
		//根节点可选项只有一个，则将其隐藏
		if(rootOptional.length === 1) {
			this.$rootOptional.hide();
			//this.$rootOptional.selectpicker('hide');
		}
		
		this.$operate = this.$modal.find('.middle .form-control.operate');//.selectpicker({ width: '120' });
		this.$relation = this.$modal.find('.middle .form-control.relation');//.selectpicker({ width: '120' });
		this.$setup = this.$modal.find('.middle .btn.setup').off('click').on('click', $.proxy(_setup, this));
		this.$remove = this.$modal.find('.middle .btn.remove').off('click').on('click', $.proxy(_remove, this));
		this.$removeall = this.$modal.find('.middle .btn.removeall').off('click').on('click', $.proxy(_removeall, this));
		
		if(this.options.enabledExpressionDefaultValue === true) {
			this.$setdft = this.$modal.find('.middle .btn.setdft').off('click').on('click', $.proxy(_setdft, this));	
			this.$default = this.$modal.find('.right div.default');
			this.$default.html('');
			var dftv = this.options.expressionDefaultValue;
			if(dftv) {
				_apendExpressionDefaultValue.apply(this, [ dftv.value, dftv.text ]);
			}
		}
		
		this.$expression = this.$modal.find('.right div.expression');
		this.$expression.html('');
		var tmp;
		for(var i = 0; i < this.options.expressionResult.length; i++) {
			tmp = this.options.expressionResult[i];
			_apendExpression.apply(this, [ tmp.value, tmp.text, expressionHtml(tmp.text) ]);	
		}
		
		this.$cancel = this.$modal.find('.modal-footer .btn.cancel').off('click').on('click', $.proxy(_cancel, this));
		this.$save = this.$modal.find('.modal-footer .btn.save').off('click').on('click', $.proxy(_save, this));
		
		for(var key in this.options.button) {
			if(!this.options.button[key].show) {
				this.$modal.find('div.modal-content button.btn.' + key).hide();
			}
		}
		
		var modalOptions = $.extend({}, ExpressModal.DEFAULTS.modalOptions, typeof this.options.modalOptions == 'object' && this.options.modalOptions);
		modalOptions.show = this.options.show;
		this.$modal.modal(modalOptions);
	}
	
	ExpressModal.prototype.show = function(_relatedTarget) {
		this.$modal.modal('show');
	}
	
	ExpressModal.prototype.hide = function(_relatedTarget) {
		this.$modal.modal('hide');
	}
	
	ExpressModal.prototype.destroy = function() {
		this.$container.empty();
		this.$container.removeData(DATA_KEY);
	}
	
	var DATA_KEY = 'bs.expressmodal';

	// ExpressModal Plugin Definition
	function Plugin(option, _relatedTarget) {
		return this.each(function() {
			var $this = $(this)
				//深度拷贝
				, options = $.extend(true, {}, ExpressModal.DEFAULTS, typeof option == 'object' && option)
				//$this.data()不做深度拷贝
				, options = $.extend(options, $this.data())
			 	, data = $this.data(DATA_KEY);

			if (!data) {
				$this.data(DATA_KEY, (data = new ExpressModal(this, options)))
			}
			
			if (typeof option == 'string') {
				data[option](_relatedTarget);
			} else if (options.show) {
				data.show(_relatedTarget);
			}
		});
	}

	var old = $.fn.ExpressModal;
	$.fn.expressmodal = Plugin;
	$.fn.expressmodal.Constructor = ExpressModal;
	
	$.fn.expressmodal.noConflict = function () {
		$.fn.expressmodal = old
		return this
	}
	
}(jQuery);