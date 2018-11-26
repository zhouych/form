/**
 * 描述：给window对象绑定一个基于SpreadJS插件的表单模板类（Fspread）。
 * 作用：定义用于渲染表单的模板。
 * 作者：ZhouYancheng
 */
$(document).ready(function() {
	
	var gkey = function() {
		return Array.prototype.slice.apply(arguments).join('_');
	}
	
	var toArray = function(obj) {
		return $.isArray(obj) ? obj : [ obj ];
	}

	var C = {
		SHEET: 'sheet',
		AREA: 'area',
		CELL: 'cell'
	}
	
	var FSPREAD_DEFAULTS = {
		id: 'fspread',
		workbook: {
			sheetCount: 3
		}
	};

	var winkey = FSPREAD_DEFAULTS.id;
	
	/**
	 * 在指定Spread的页签[sheet]的区域[area]上添加行
	 */
	var addRows = function(workSheet, area) {
		var arearow = area.endrow - area.startrow + 1; //表单区域一条数据填充到Spread上所跨的行数 。
		//第一行[k=0]数据所需要的行本身是存在的，表单滚动区第二行数据开始，填充时需要对Spread进行增行。
		for(var i = 0; l < arearow; i++) {
			//Spread新添加行的索引 = 格式设计时当前表单区域结束行索引 + 1 + 在表单区域跨度中逐一新增行的当前索引 + 当前表单滚动区数据行记录索引
			workSheet.addRows(area.endrow + 1 + i + k , 1);
		}
	}
	
	/**
	 * 计算Spread中字段单元格的行索引
	 */
	var calcFieldCellRowIndex = function(sheetFormArea, fieldCell, itemIndex) {
		//cell所在行索引 = 格式设计时cell所在行索引 + 当前表单滚动区数据行记录索引 + 格式设计时cell所在行索引与cell所在区域起始行索引的差
		return fieldCell.row + itemIndex + (fieldCell.row - sheetFormArea.startrow);
	}

	var getFormKey = function(formarea) {
		formarea = formarea.toLowerCase();
		if(formarea != 'main') {
			formarea += 's';
		}
		return formarea;
	}

	/**
	 * 提供针对Spread模板化相关操作的对象
	 */
	var templatable = {
		xml: {
			get: function() {
				throw new Error('The function "templatable.xml.get" not implements.');
			},
			set: function(template) {
				throw new Error('The function "templatable.xml.set" not implements.');
			}
		},
		json: {
			get: function() {
				return JSON.stringify(this.spread.toJSON());
			},
			set: function(template) {
				this.spread.fromJSON(JSON.parse(template));
				return this;
			}
		}
	};
	
	/**
	 * 获取Spread模板数据或者按指定的模板加载Spread
	 * @param type 必选参数，模板类型。枚举值，在 [ 'xml' | 'json' ] 中根据需要选择一种。
	 * @param template 可选参数，模板数据。传值即为按所传值加载Spread模板数据；不传值（undefined）即为获取Spread模板数据
	 */
	var _template = function(type, template) {
		var isgets = typeof template === 'undefined'
			, command = isgets ? 'get' : 'set';
		return templatable[type][command].apply(this, [ template ]);
	}
	
	var _initTemplateSource = function(source) {
		var sourceIndex = {}, sheet, area, fcell;
		
		//整理Spread的表单模板页签顺序。
		source.sheets.sort(function(a, b) {
			return a.index - b.index;
		});
		
		for(var i = 0; i < source.sheets.length; i++) {
			sheet = source.sheets[i];
			keySheetIndex = SHEET_ + sheet.index;
			
			//将sheet加入到索引对象，便于后续需要时能够快速查找。
			sourceIndex[gkey(C.SHEET, sheet.index)] = sheet;
			sourceIndex[gkey(C.SHEET, sheet.id)] = sheet;
			sourceIndex[gkey(C.SHEET, sheet.name)] = sheet;
			
			//基于Spread的表单模板为行列结构的表格，数据加载有顺序要求，需要整理表单区域顺序。
			sheet.areas.sort(function(a, b) {
				return a.startrow - b.startrow;
			});
			
			for(var j = 0; j < sheet.areas.length; j++) {
				area = sheet.areas[j];
				
				//将area加入到索引对象，便于后续需要时能够快速查找。
				sourceIndex[gkey(C.SHEET, sheet.index, C.AREA, j)] = area;
				sourceIndex[gkey(C.SHEET, sheet.index, C.AREA, area.id)] = area;
				sourceIndex[gkey(C.SHEET, sheet.index, C.AREA, area.formarea)] = area;
				
				//基于Spread的表单模板为行列结构的表格，数据加载有顺序要求，需要整理表单字段顺序。
				area.fieldCells.sort(function(a, b) {
					return a.row - b.row || a.row == b.row && a.col - b.col;
				});
				
				for(var k = 0; k < area.fieldCells.length; k++) {
					fcell = area.fieldCells[k];

					//将fcell加入到索引对象，便于后续需要时能够快速查找。
					sourceIndex[gkey(C.SHEET, sheet.index, C.AREA, area.formarea, C.CELL, k)] = fcell;
					sourceIndex[gkey(C.SHEET, sheet.index, C.AREA, area.formarea, C.CELL, fcell.id)] = fcell;
					sourceIndex[gkey(C.SHEET, sheet.index, C.AREA, area.formarea, C.CELL, fcell.field)] = fcell;
				}
			}
		}
		
		this.templateSourceIndex = sourceIndex;
		this.templateSource = source;
	}
	
	var _render = function() {
		 var mode = this.mode
		 	, source = this.templateSource
			, sheet, area, fcell;
	}
	
	var _findField = function(formarea, fieldvalue) {
		var field = null;
		for(var i = 0; i < this.fields.length; i++) {
			field = this.fields[i];
			if(field.formarea === formarea && field.fieldvalue === fieldvalue) {
				break;
			}
		}
		return field;
	}
	
	var _displayCell = function(item, cell, field) {
		var value = item[field.fieldvalue];
		switch(field.displaytype) {
			case 'textbox':
				cell.value(value);
				break;
			case 'textarea':
				cell.value(value);
				break;
			default:
				console.log('Type not supported. (type=' + field.displaytype + ')');
				break;
		}
	}
	
	var _init = function() {
		var sheets = this.spread.sheets
			, sheet;
		for(var i = 0; i < sheets.length; i++) {
			sheet = sheets[i];
			sheet.setColumnCount(26, GC.Spread.Sheets.SheetArea.viewport);
		}
	}

	/*************************************************************************************************/
	/*********************************** 						   ***********************************/
	/***********************************    Fspread Definition.    ***********************************/
	/*********************************** 						   ***********************************/
	/*************************************************************************************************/

	var Fspread = function(options) {
		var that = this;
		this.options = $.extend(true, {}, FSPREAD_DEFAULTS, options || {});

		winkey = this.options.id;
		
		this.element = document.getElementById(this.options.id);
		this.mode = null; //Spread加载表单的模式，枚举类型[ 'view' | 'add' | 'edit' |  'approval' | 'design' ] 。
		this.templateSource = null; //基于Spread的表单模板结构化数据源。
		this.templateSourceIndex = null; //基于Spread的表单模板结构化数据源的索引对象，用于快速查找数据。
		this.fields = null; //必选，数组类型，表单可使用所用字段的列表。

		//************************** Spread对象实例初始化 **************************
		this.spread = new GC.Spread.Sheets.Workbook(element, this.options.workbook);
		
		_init.apply(this, []);

		return that;
	}
	
	/**
	 * 制作表单模板
	 * @param mode -- 必选，Spread加载表单的模式。字符串枚举值，在 [ 'view' | 'add' | 'edit' |  'approval' | 'design' ] 中根据需要选择一种。
	 * @param source -- 必选，Object对象。绑定到Spread的表单区域、字段等信息。
	 *	source对象的结构如下： 
	 *		id -- 必选，字符串，主键ID。
	 *		formid -- 必选，表单主键ID。
	 *		json -- 可选（[ 'xml' | 'json' ] 中至少一个有值），基于Spread的模板数据JSON字符串。
	 *		xml -- 可选（[ 'xml' | 'json' ] 中至少一个有值），基于Spread的模板数据XML字符串。
	 *		sheets -- 可选（mode=design时，不需要），数组，页签列表。数组元素为Object对象，其结构如下：
	 *			id -- 必选，字符串，主键ID。
	 *			spreadid -- 必选，字符串，外键ID，对应source的id字段。
	 *			index -- 必选，数组，当前页签的排序索引。
	 *			name -- 必选，字符串，页签名称。
	 *			areas -- 必选，数组，表单区域列表。数组元素为Object对象，其结构如下：
	 *				id -- 必选，字符串，主键ID。
	 *				spreadsheetid -- 必选，字符串，外键ID，对应sheets数组元素对象实例的id字段。
	 *				formarea -- 必选，枚举[ 'Main' | 'Detail' | 'Payment' ...]，表单区域。
	 *				startrow -- 必选，整数，区域起始行位置索引。
	 *				startcol -- 必选，整数，区域结束行位置索引。
	 *				endrow -- 必选，整数，区域起始列位置索引。
	 *				endcol -- 必选，整数，区域结束列位置索引。
	 *				rowmax -- 必选，整数，显示最大的行数。
	 *				fieldCells -- 必选，数组，表单字段单元格列表。数组元素为Object对象，其结构如下：
	 *					spreadsheetareaid -- 必选，字符串，外键ID，对应areas数组元素对象实例的id字段。
	 *					row -- 必选，整数，字段单元格所在的行位置索引。
	 *					col -- 必选，整数，字段单元格所在的列位置索引。
	 *					area -- 必选，枚举[ 'Main' | 'Detail' | 'Payment' ...]，表单字段定义所在区域。
	 *					field -- 必选，字符串，字段值。
	 *	fields -- 必选，数组，表单使用的所有字段信息集合。
	 */
	Fspread.prototype.template = function(mode, templateSource, fields) {
		this.mode = mode;
		_initTemplateSource.apply(this, [ templateSource ]);
		this.fields = fields;
		_template.apply(this, [ 'json', options.source.json ]);
		_render.apply(this, []);
		return this;
	}
	
	/**
	 * 加载表单数据
	 * @param form -- 必选，Object对象，表单数据。
	 */
	Fspread.prototype.load = function(form) {
		var source = this.templateSource
			, sheet, workSheet, area, arearow, formarea, fcell, cell, field, items, item, value, rowIndex;
		
		for(var i = 0; i < source.sheets.length; i++) {
			sheet = source.sheets[i];
			workSheet = this.spread.getSheetFromName(sheet.name);
			for(var j = 0; j < sheet.areas.length; j++) {
				area = sheet.areas[i]; //Spread上的表单区域
				formarea = getFormKey(area.formarea); //表单区域。
				items = toArray(form[formarea]); //指定表单区域的表单数据（主表区只有一条数据，将其放入数组当做滚动区看待）。
				for(var k = 0; k < items.length; k++) {
					item = items[k];
					if(k > 0) {
						addRows(workSheet, area);
					}
					for(var m = 0; m < area.fieldCells.length; m++) {
						fcell = area.fieldCells[m];
						if(typeof item[fcell.field] !== 'undefined') {
							rowIndex = calcFieldCellRowIndex(area, fcell, k);
							cell = sheet.getCell(rowIndex, fcell.col);
							field = _findField.apply(this, [ fcell.area, fcell.field ]);
							_displayCell.apply(this, [ item, cell, field ]);
						}
					}
				}
			}
		}
		return this;
	}
	
	Fspread.VERSION  = '1.0';
	
	window[winkey] = Fspread;
	/*************************************************************************************************/
	/*********************************** 						   ***********************************/
	/***********************************  Fspread Definition End.  ***********************************/
	/*********************************** 						   ***********************************/
	/*************************************************************************************************/
});