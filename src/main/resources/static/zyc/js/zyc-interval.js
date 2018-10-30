/**
 * 描述：给window对象绑定一个间隔类（ZycInterval）。
 * 作用：用于指定时间间隔执行业务的对象，并且支持终止执行。
 * 作者：ZhouYancheng
 */
(function() {

	'use strict';
	
	//window对象没有ZycInterval才需要重新绑定，否则，不需要重新绑定。
	if(!window['ZycInterval']) {
		
		/**
		 * 将间隔类（ZycInterval）绑定到window对象上。
		 * @param context 参数handle/unexecutable/stoppable函数执行的上下文环境。
		 * @param handle 业务处理函数，不支持带参数。
		 * @param unexecutable 用于判断不可间隔执行业务处理的函数，不支持带参数。
		 * @param stoppable 用于判断终止该间隔执行（默认业务处理完毕即终止间隔执行），不支持带参数。
		 */
		window['ZycInterval'] = function (context, handle, unexecutable, stoppable) {
			if(typeof unexecutable !== 'function') {
				throw new Error('The parameter "unexecutable" is not function.');	
			}
			
			var that = this
				, ctxt = context || this
				, fn = typeof handle === 'function' ? handle : null
				, un = unexecutable
				, stop = typeof stoppable === 'function' && stoppable || null
				, timer;
			
			var exec = function() {
				//如果不可执行业务处理及终止的函数，则return，不再进行后续处理。
				if(un.apply(ctxt, [])) {
					//console.log('unexecutable');
					return;	
				}
				
				//如果存在业务处理，则进行业务处理
				if(fn) {
					fn.apply(ctxt, []);
				}
				
				//业务处理完毕，如果可以终止的话，则终止该间隔执行（默认业务处理完毕即终止间隔执行）。
				if(!stop || stop.apply(ctxt, [])) {
					clearInterval(timer);
					//console.log('clearInterval');
				}
			}
			
			/**
			 * 启动间隔
			 * @param milliseconds 间隔时间（单位：毫秒）。
			 */
			this.start = function(milliseconds) {
				var ms = parseInt(milliseconds);
				timer = setInterval(exec, isNaN(ms) ? 200 : ms);
				return that;
			}
			
			return that;
		}
	}
})();