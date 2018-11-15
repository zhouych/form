(function() {

	'use strict';
	  
	var DATA_BIND_HREF = 'data-bind-href';
	var ATTR_KEYS = [ DATA_BIND_HREF ];

	jQuery.fn.extend({
		bindHref: function(value) {
			return jQuery.fn.attr.apply(this, typeof value === 'undefined' ? [ DATA_BIND_HREF ] : [ DATA_BIND_HREF, value ]);
		}
	});
	
	jQuery.extend({
		bindHref: function(element, value) {
			return jQuery.attr.apply(this, [ element, DATA_BIND_HREF, value ]);
		}
	});
	
	jQuery.fn.extend({
		onUndisabled: function(event, fn) {
			this.off(event).on(event, function(e) {
				if($(this).attr('disabled')) {
					return;
				}
				
				fn.apply(this, [ e ]);
			});
		},
		findInput: function(name) {
			return this.find('input[name="' + name + '"]');
		},
		findSelect: function(name) {
			return this.find('select[name="' + name + '"]');
		}
	});

})();