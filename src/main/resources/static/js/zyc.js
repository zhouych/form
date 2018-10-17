(function() {
    
	'use strict';
    
	var zyc = window['zyc'] = {
    	comWebsite: 'www.zyc.com',
    	emptyNodeType: {
    		NONE: { value: 'none', text: '' },
    		ALL: { value: 'all', text: '全部' },
    		OPTIONAL: { value: 'optional', text: '请选择' }
    	},
    	memberRelation: {
    		SELF: { value : 'self', text: '本身（自己）'},
    		PEER: { value : 'peer', text: '同辈（自己及兄弟姐妹）'},
    		CHILDREN: { value : 'children', text: '成员（子女）'},
    		DESCENDANT: { value : 'descendant', text: '成员及后代（子孙后代）'},
    	}
    };
	
})();
