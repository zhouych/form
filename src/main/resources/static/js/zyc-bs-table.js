(function() {
	
	//扩展查询参数
	var _extendQueryParams = function(params, old, extension) {
		return $.extend(true, {}, old(params), extension(params));
	}

    _onLoadSuccess = function (data) {
    	var that = this;
    	if(that.rowTooltipable) {
    		that.$elmt.find('tbody tr[data-uniqueid] td:not(.disable-row-tooltip)').each(function(index) {
	    		$(this).tooltip({
	    			container: 'body',
	    			html: true,
	    			title: '<span class="label label-none">' + (that.rowTooltipTitle || $(this).text()) + '</span>',
	    			placement: 'bottom',
	    			delay: { show: 400, hide: 100 }
	    		})
	    	});
    	}
		
    	that.$elmt.find('.arrcol-item-tooltip').each(function(index) {
    		var $this = $(this)
    			, count = parseInt($this.attr('data-bind-part-count'));
    		
    		if(!isNaN(count)) {
    			var titles = [], tmp;
    			for(var i = 0; i < count; i++) {
    				tmp = $this.attr('data-bind-part-' + i);
    				if(tmp) {
    					titles.push('<span class="label label-none href">' + tmp + '</span>');
    				}
    			}
	    		$(this).tooltip({ html: true, title: titles.join(''), placement: 'bottom' });
    		}
    	});
    }
	
	var bstable = window['bstable'] = {
		//初始化bootstrap-table，即执行$.fn.bootstrapTable函数
		//@param $element: bootstrap-table插件的目标dom的jQuery对象
		//@param options: bootstrap-table插件的参数选项
		//@param queryParamsExtension: 函数，用于扩展bootstrap-table插件的参数选项queryParams属性
		init: function($element, options, queryParamsExtension) {
			options = typeof options == 'object' && options || {}
			var currentOptions = { $elmt: $element };
			$.extend(true, currentOptions, DEFAULTS, options);
			
			if(typeof queryParamsExtension === 'function') {
				currentOptions.queryParams = function(params) {
					return _extendQueryParams(params, DEFAULTS.queryParams, queryParamsExtension);
				}
			}
			
			if(typeof options.onLoadSuccess === 'function') {
				currentOptions.onLoadSuccess = function(data) {
					DEFAULTS.onLoadSuccess.apply(this, [ data ]);
					options.onLoadSuccess.apply(this, [ data ]);
				}
			}
			
			$element.bootstrapTable(currentOptions);
		},
		tooltipArrayColumn: function(enabledLoop, func, value, item, i) {
			if(!enabledLoop) {
				return '';
			}

			var l = $.isArray(value) && value.length || 0
				, isFunction = typeof func === 'function'
				, htmls = [], info, parts, attrHtml
				, template = '<span class="label label-tag hand arrcol-item-tooltip %s" href %s>%s</span>';
			
			for(var k = 0; k < l; k++) {
				if(isFunction) {
					info = func(value[k]);
					if(info) {
						parts = info.title && $.isArray(info.title) ? info.title : [ info.title || info.text ];
						attrHtml = 'data-bind-part-count="' + parts.length + '"';
						for(var m = 0; m < parts.length; m++) {
							attrHtml += ' data-bind-part-' + m + '="' + parts[m] + '"';
						}
						htmls.push(zyc.sprintf(template, info.classes, attrHtml, info.text));
					}
				}
			}
			
			return htmls.length ? htmls.join(' ') : '';
		},
		cellDisableRowTooltip: function(value, row, index, field) {
			return { classes: 'disable-row-tooltip' };
		}
	};

	var DEFAULTS = {
		buttonsToolbar: undefined, //一个jQuery 选择器，指明自定义的 buttons toolbar。例如: #buttons-toolbar, .buttons-toolbar 或 DOM 节点。
		buttonsAlign: 'right', //指定 按钮栏 水平方向的位置。'left' 或 'right'。
	    toolbar: undefined, //一个jQuery 选择器，指明自定义的 toolbar。例如: #toolbar, .toolbar.
	    toolbarAlign: 'left', //指定 toolbar 水平方向的位置。'left' 或 'right'。
		url: undefined,
	    method: 'get',
	    ajax: false,
	  	//得到查询的参数
	    queryParams: function(params) {
	    	return {
	    		pagination: {
		    		pageNumber: (params.offset / params.limit) + 1,
		    		pageRowCount: params.limit,
		    		order: params.sort,
		    		asc: 'asc' === (params.order || '').toLowerCase()
	    		},
	    		condition: {},
				searchText: params.search
	    	};
	    }, 
	    search: true, //是否显示表格搜索
	    searchAlign: 'right', //指定 搜索框 水平方向的位置。'left' 或 'right'。
	    uniqueId: "id", //每一行的唯一标识，一般为主键列
	    idField: 'id', //指定主键列。
	    columns: [[]],
	    striped: true, //是否显示行间隔色
	    cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性
	    pagination: true, //是否显示分页
	    sortable: true,	//是否启用排序
	    sortName: 'id',
	    sortOrder: 'asc', //排序方式
	    silentSort: true, //设置为 false 将在点击分页按钮时，自动记住排序项。仅在 sidePagination设置为 server时生效
	    sidePagination: 'server', //分页方式：client客户端分页，server服务端分页
	    pageNumber: 1, //初始化加载第一页，默认第一页
	    pageSize: 10, //每页的记录行数（*）
	    pageList: [10, 25, 50, 100], //可供选择的每页的行数
	    paginationPreText: '上一页', //指定分页条中上一页按钮的图标或文字
	    paginationNextText: '下一页', //指定分页条中下一页按钮的图标或文字
	    paginationHAlign: 'right', //指定 分页条 在水平方向的位置。'left' 或 'right'。
	    paginationDetailHAlign: 'left', //指定 分页详细信息 在水平方向的位置。'left' 或 'right'。
	    strictSearch: false, //设置为 true启用全匹配搜索，否则为模糊搜索
	    showColumns: true, //是否显示所有的列（选择显示的列）
	    showRefresh: true, //是否显示刷新按钮
	    showFullscreen: true, //是否显示全屏按钮。
	    showPaginationSwitch: false, //是否显示切换分页按钮。
	    minimumCountColumns: 2, //最少允许的列数
	    clickToSelect: true, //是否启用点击选中行
		singleSelect: true, //设置 true 将禁止多选
		//包含一个参数：
		//element: 点击的元素。
		//返回 true 是点击事件会被忽略，返回 false 将会自动选中。该选项只有在 clickToSelect 为 true 时才生效。 
	    ignoreClickToSelectOn: function (element) {
	        return $.inArray(element.tagName, ['A', 'BUTTON']);
	    },
		checkboxHeader: true, //设置 false 将在列头隐藏全选复选框。
		maintainSelected: false, //设置为 true 在点击分页按钮或搜索按钮时，将记住checkbox的选择项
	    showToggle: false, //是否显示详细视图和列表视图的切换按钮
	    cardView: false, //是否显示详细视图
	    detailView: false, //是否显示父子表
	    //自定义行样式 参数为：
	    //row: 行数据
	    //index: 行下标
	    //返回值可以为class或者css 
	    rowStyle: function(row, index) {
	    	return {};
	    },
		//自定义行属性 参数为：
		//row: 行数据
		//index: 行下标
		//返回值可以为class或者css 支持所有自定义属性
	    rowAttributes: function(row, index) {
	    	return {};
	    },
	    //自定义搜索方法来替代内置的搜索功能，它包含一个参数：
	    //text：搜索文字。
		//用法示例：
	    //function customSearch(text) {
	    //    //Search logic here.
	    //    //You must use `this.data` array in order to filter the data. NO use `this.options.data`.
	    //}
	    customSearch: $.noop,
	    //自定义排序方法来替代内置的搜索功能，它包含一个参数：
		//sortName: 排序名。
		//sortOrder: 排序顺序。
		//用法示例：
		//function customSort(sortName, sortOrder) {
	    //	//Sort logic here.
	    //	//You must use `this.data` array in order to sort the data. NO use `this.options.data`.
		//}
	    customSort: $.noop,
	    rowTooltipable: false,
	    rowTooltipTitle: '',
	    onLoadSuccess: _onLoadSuccess,
	    onLoadError: function (status) {
	    },
		onClickRow: function (item, $element) {
	        return false;
	    },
		onDblClickRow: function (item, $element) {
	        return false;
	    }
	};
	
})();
