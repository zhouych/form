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
	}
	
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
    }

	var _syncAjaxGet = function(url) {
		var result = null;
		$.ajax({
		    type: 'get',
		    url: url,
		    data: {},
			async: false,
			dataType: 'json',
		    success: function(response) {
		    	if(response.status === '0') {
		    		result = response.data;
		    	} else {
			    	console.log(url + ": " + response.message);
		    	}
		    },
		    error: function(err) {
		    	var msg = typeof err === 'object' && err && JSON.stringify(err) || error;
		    	console.log(url + ": " + msg);
		    }
		});
		return result;
	}
	
	var _treeRelationships = null
		, _includeOrExclude = null
		, _dataStatus = null
		, _emptyNodeTypes = null;
    
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
    	confirm: function(message) {
    		return confirm(message);
    	},
    	sprintf: _sprintf,
		timestampToDate: function(timestamp) {
			return !timestamp ? null : new Date(timestamp);
		},
		formatTimestamp: function(timestamp) {
			var date = zyc.timestampToDate(timestamp);
			return date && date.format() || '';
		},
    	joinArray: function(array, symbol) {
    		symbol = typeof symbol !== 'udnefined' && symbol != null ? (symbol + '') : '';
    		var result = {}, tmp, valid;
    		for(var i = 0, l = array.length; i < l; i++) {
    			for (var key in array[i]) {
    				tmp = array[i][key];
    				valid = typeof tmp !== 'udnefined' && tmp != null;
					result[key] = (result[key] || '') + (valid ? tmp : '') + (i === l - 1 ? '' : symbol);
				}
    		}
    		return result;
    	},
    	getEmptyNodeTypes: function() {
    		return _emptyNodeTypes || (_emptyNodeTypes = _syncAjaxGet('/api/emptynodetypes'));
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
    		var emptys = zyc.getEmptyNodeTypes();
    		for(var i = 0; i < emptys.length; i++) {
    			if(nodeValue === emptys[i].value) {
    				return true;
    			}
    		}
    		return false;
    	},
    	getAllDataStatus: function() {
    		return _dataStatus || (_dataStatus = _syncAjaxGet('/api/datastatus'));
    	},
		textDataStatus: function(value) {
			var ds = zyc.getAllDataStatus(), tmp;
			for(var i = 0; i < ds.length; i++) {
				tmp = ds[i];
				if(tmp.value === value) {
					return tmp.text;
				}
			}
			return '';
		},
    	getIncludeOrExclude: function() {
    		return _includeOrExclude || (_includeOrExclude = _syncAjaxGet('/api/includeorexclude'));
    	},
    	getTreeRelationships: function() {
    		return _treeRelationships || (_treeRelationships = _syncAjaxGet('/api/tree/relationships'));
    	}
    };
	
})();
