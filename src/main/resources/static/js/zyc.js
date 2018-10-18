(function() {
    
	'use strict';
    
	var zyc = window['zyc'] = {
    	comWebsite: 'www.zyc.com',
    	emptyNodeType: {
    		NONE: { value: 'none', text: '' },
    		ALL: { value: 'all', text: '全部' },
    		OPTIONAL: { value: 'optional', text: '请选择' }
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
