/* ========================================================================
 * 用于构建树形表达式的模态框: zyc-tree-expression-modal.js
 * ========================================================================
 * Author: ZhouYancheng
 * Created at: 2018-10
 * ======================================================================== */

+function ($) {
	'use strict';
		
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
			cancel: { text: 'Cancel', show: true }
		},
		show: true,
		relationOptions: [],
		modalOptions: {
		    backdrop: true,
		    keyboard: true,
		    show: true
		}
	}
	
	TreeExpressionModal.prototype.fillData = function(_data) {
		this.data = $.extend(true, this.data, _data);
	}
	
	TreeExpressionModal.prototype.init = function () {
		var that = this;
		
		var relationHtmls = [], temp;
		for (var i = 0; i < this.options.relationOptions.length; i++) {
			temp = this.options.relationOptions[i];
			relationHtmls.push('<option value="' + temp.value + '">' + temp.text + '</option>');
		}
		
		var modal = 
		'<div class="modal fade bs-example-modal-lg" id="' + this.options.id + '" tabindex="-1" role="dialog" aria-labelledby="' + this.options.id + '_label">' +
			'<div class="modal-dialog modal-lg" role="document">' +
		    	'<div class="modal-content container-fluid">' +
		      		'<div class="modal-header">' +
		        		'<button type="button" class="btn close" data-dismiss="modal" aria-label="Close">' + this.options.button.close.text + '</button>' +
		        		'<h4 class="modal-title" id="' + this.options.id + '_label">' + this.options.title + '</h4>' +
		      		'</div>' +
		      		'<div class="modal-body row">' +
		      			'<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5">' + 
	      					'...' +
		      			'</div>' +
		      			'<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">' + 
		      				'...' +
		      				'<select class="selectpicker">' +
		      					relationHtmls.join('') +
			      			'</select>' +
		      			'</div>' +
		      			'<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5">' + 
	      					'...' +
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
		
		var $relationSelect = $('.selectpicker').selectpicker();
		$relationSelect.on('changed.bs.select', function (e, clickedIndex, isSelected, previousValue) {
			alert(that.data.relation.value + ';' + that.data.relation.text);
			that.fillData({ 
				relation: { 
					value: $relationSelect.val(), 
					text: $('div.filter-option-inner-inner').text() 
				} 
			});
			alert(that.data.relation.value + ';' + that.data.relation.text);
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