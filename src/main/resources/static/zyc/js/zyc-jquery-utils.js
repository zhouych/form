(function() {
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
	
})();