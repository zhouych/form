(function() {
    
	'use strict';
	
	Date.prototype.format = function (format) {
		var value = format || 'yyyy-MM-dd hh:mm:ss';

	    var o = {
	        "M+": this.getMonth() + 1, // month
	        "d+": this.getDate(), // day
	        "h+": this.getHours(), // hour
	        "m+": this.getMinutes(), // minute
	        "s+": this.getSeconds(), // second
	        "q+": Math.floor((this.getMonth() + 3) / 3), // quarter
	        "S" : this.getMilliseconds() // millisecond
	    };

	    if (/(y+)/.test(value)) {
	    	value = value.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    }

	    for (var k in o) {
	        if (new RegExp("(" + k + ")").test(value)) {
	        	value = value.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
	        }
	    }
	    
	    return value;
	};
	
    // it only does '%s', and return '' when arguments are undefined
    var _sprintf = function (str) {
        var args = arguments,
            flag = true,
            i = 1;

        str = str.replace(/%s/g, function () {
            var arg = args[i++];

            if (typeof arg === 'undefined') {
                flag = false;
                return '';
            }
            return arg;
        });
        return flag ? str : '';
    };
    
	var zyc = window['zyc'] = {
    	comWebsite: 'www.zyc.com',
    	getUrl: function(relativeOriginPath) {
    		return window.location.origin + relativeOriginPath;
    	},
    	redirect: function(relativeOriginPath) {
    		window.location = window.location.origin + relativeOriginPath;
    	},
    	alert: function(message, level) {
    		var defaultMessage
    			, msg = message;
    		
    		if(!msg) {
        		if(level === 'error') {
        			defaultMessage = '操作失败，请稍后再试或者联系管理员！';
        		}
    		}
    		
    		alert(msg || defaultMessage);
    	},
    	sprintf: _sprintf,
		timestampToDate: function(timestamp) {
			return !timestamp ? null : new Date(timestamp);
		},
		formatTimestamp: function(timestamp) {
			var date = zyc.timestampToDate(timestamp);
			return date && date.format() || '';
		},
		textDataStatus: function(value) {
			var tmp;
			for(var key in zyc.dataStatus) {
				tmp = zyc.dataStatus[key];
				if(tmp.value === value) {
					return tmp.text;
				}
			}
			return '';
		},
		dataStatus: {
			ENABLED: { value: 'enabled', text: '已启用' },
			DISABLED: { value: 'disabled', text: '已禁用' },
			LOCKED: { value: 'locked', text: '已锁住' },
			DELETED: { value: 'deleted', text: '已删除' }
		},
    	emptyNodeType: {
    		NONE: { value: 'none', text: '------' },
    		ALL: { value: 'all', text: '全部' },
    		OPTIONAL: { value: 'optional', text: '请选择' }
    	},
    	/**
    	 * 判断指定节点值是否指向的是无效的业务节点
    	 * @param nodeValue 待判断节点的节点值
    	 * @returns 布尔值：true-无效节点；false-有效节点。
    	 */
    	invalidBusNode: function(nodeValue) {
    		if(typeof nodeValue !== 'string') {
    			return true;
    		}
    		for(var key in zyc.emptyNodeType) {
    			if(nodeValue === zyc.emptyNodeType[key].value) {
    				return true;
    			}
    		}
    		return false;
    	},
    	inexOperate: {
    		include: { value: 'include', text: '包含' },
    		exclude: { value: 'exclude', text: '排除' }
    	},
    	memberRelation: {
    		SELF: { value: 'self', text: '本身（自己）'},
    		PEER: { value: 'peer', text: '同辈（兄弟姐妹）'},
    		CHILDREN: { value: 'children', text: '成员（子女）'},
    		DESCENDANT: { value: 'descendant', text: '后代（子孙后代）'},
    	},
    	entryBeans: function(entryBeanMap) {
    		var beans = [], temp;
    		for(var key in entryBeanMap) {
    			temp = entryBeanMap[key];
    			beans.push({ value: temp.value, text: temp.text });
    		}
    		return beans;
    	}
    };
	
})();
