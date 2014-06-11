var dom = {
		win: $(window),
		doc: $(document),
		bod: $(document.body)
	},
	events = [
		{
			click: 'touchstart',
			over: 'touchstart',
			out: 'touchend',
			down: 'touchstart',
			move: 'touchmove',
			up: 'touchend'
		},
		{
			click: 'click',
			over: 'mouseover',
			out: 'mouseout',
			down: 'mousedown',
			move: 'mousemove',
			up: 'mouseup'
		}
	]
	[ !!~window.navigator.userAgent.toLowerCase().indexOf('mobile') ? 0 : 1 ];

$.fn.extend({
	// 弹出层
	dialog: function(options){
		options = options || {}
		,options.menus = $(this)
		,options.width = options.width || 800
		,options.height = options.height || 600
		,options.mask = options.mask || true
		,options.close = options.close || true
		,options.type = options.type || 'ajax'
		,options.callback = options.callback || $.noop
		,options.pop = $('<div>').css({
					background: 'white'
					,width: options.width
					,height: options.height
					,zIndex: 99999
					,position: 'fixed'
					,borderRadius: 3
					,boxShadow: '0 0 5px rgba(0, 0, 0, 0.25)'
				});
		
		dom.win.on('resize', function(){
			options.pop.css({
					left: ( dom.bod.width() - options.width ) / 2
					,top: ( dom.bod.height() - options.height ) / 2
				});
		}).trigger('resize');
		
		options.menus.on(events.click, function(){
			
			var me = $(this);
			
			if( options.mask ){
				options.mask = $('<div>').css({
						background: 'rgba(0, 0, 0, .36)'
						,top: 0
						,left: 0
						,right: 0
						,bottom: 0
						,zIndex: 88888
						,position: 'fixed'
					}).appendTo(dom.bod);
			}
			
			if( options.close ){
				options.close = $('<div>').css({
						background: 'dimgray'
						,top: -15
						,right: -15
						,position: 'absolute'
						,lineHeight: '100%'
						,padding: '4px 8px 6px'
						,fontSize: 20
						,cursor: 'pointer'
						,color: '#fff'
						,borderRadius: 20
						,boxShadow: '0 0 3px rgba(0, 0, 0, 0.25)'
					})
						.text('×')
						.on(events.click, function(){
							options.pop.remove();
							options.mask.remove();
						});
			}
			
			if( options.type === 'ajax' ){
			
				$.ajax({
					url: '/1/pages/' + me.attr('data-ajax') + '.html',
					cache: false,
					success: function(html){
						options.pop.html(html).append(options.close).appendTo(dom.bod).hide().fadeIn(function(){ options.callback() });
					}
				});
			}
		});
	}
});

$.extend({
	// 焦点图
	carous: function(options){
		options = options || {};
		
		$.each( options.carous = options.carous || $('.carous'), function(i, carous){
			carous = $(carous)
			,options.move        = carous.find(options.move || '.carous-container > ul')
			,options.dish        = carous.find(options.dish || '.carous-thumb > ul')
			,options.menu        = carous.find(options.menu || '.carous-thumb > ul > li')
			,options.prev        = carous.find(options.prev || '.carous-prev')
			,options.next        = carous.find(options.next || '.carous-next')
			,options.pause       = carous.find(options.pause || '.carous-pause')
			,options.start       = carous.find(options.start || '.carous-start')
			,options.numerator   = carous.find(options.numerator || '.numerator')
			,options.denominator = carous.find(options.denominator || '.denominator')
			,options.active      = carous.attr('data-active') || 'active'
			,options.distance    = carous.attr('data-distance') || options.move.children(':eq(0)').outerWidth(true)
			,options.max         = carous.attr('data-max') || options.move.children().length - 1
			,options.count       = carous.attr('data-count') || 0
			,options.speed       = carous.attr('data-speed') || 'fast'
			,options.time        = carous.attr('data-time')  || 1200
			,options.type        = 'pause'
			,options.view        = [0, ( carous.attr('data-view') || 10 ) - 1]
			,options.effect      = function(index){
				index = index || options.count;
				
				options.menu.removeClass(options.active).eq(index).addClass(options.active);
						
				// 分子
				if( options.numerator.length ){
					options.numerator.text(index + 1);
				}
				
				options.move.stop().animate({
					left: -index * options.distance
				});
				
				var viewMin = options.view[0],
					viewMax = options.view[1],
					viewGap = viewMax - viewMin + 1,
					viewDistance = options.menu.eq(0).outerWidth(true),
					viewMove = 0,
					viewDiff = 0;
				
				if( index >= viewMin && index <= viewMax ){
					return;
				}
				
				if( index > viewMax ){
					viewDiff = index - viewMax;
					viewDistance = -(viewDiff) * viewDistance;
					options.view[0] += viewDiff, options.view[1] += viewDiff;
				
					options.dish.stop().animate({
						left: '+=' + viewDistance
					}, options.speed);
				}
				if( index < viewMin ){
					viewDiff = viewMin - index;
					viewDistance = -(viewDiff) * viewDistance;
					options.view[0] -= viewDiff, options.view[1] -= viewDiff;
				
					options.dish.stop().animate({
						left: '-=' + viewDistance
					}, options.speed);
				}
			}
			,options.interval = undefined
			,options.run = function(){
				if( options.interval === undefined ){
					options.interval = setInterval(function(){
						options.next.trigger(events.click);
					}, options.time);
				}
			}
			,options.clear = function(){
				clearInterval(options.interval);
				options.interval = undefined;
			}
			,options.init = ~function(){
				
				// 分母
				if( options.denominator.length ){
					options.denominator.text(options.max + 1);
				}
				
				// 上一页
				if( options.prev.length ){
					options.prev.on(events.click, function(){
						
						if( options.move.is(':animated') ){
							return;
						}
						
						options.count--;
						if( options.count < 0 ){
							options.count = options.max;
						}
						options.effect(options.count);
						
						if( options.type === 'start' ){
							options.clear(), options.run();
						}
					});
				}
				
				// 下一页
				if( options.next.length ){
					options.next.on(events.click, function(){
						
						if( options.move.is(':animated') ){
							return;
						}
						
						options.count++;
						if( options.count > options.max ){
							options.count = 0;
						}
						options.effect(options.count);
						
						if( options.type === 'start' ){
							options.clear(), options.run();
						}
					});
				}
				
				// 缩略图
				if( options.menu.length ){
					options.menu.on(events.click, function(){
						options.count = options.menu.index(this);
						options.effect(options.count);
						
						if( options.type === 'start' ){
							options.clear(), options.run();
						}
					});
				}
				
				// 暂停
				if( options.pause.length ){
					options.pause.on(events.click, function(){
						options.clear();
						options.type = 'pause';
					});
				}
				
				// 开始
				if( options.start.length ){
					options.start.on(events.click, function(){
						options.run();
						options.type = 'start';
					});
				}
				
				// 悬停
				carous
					.on(events.over, function(){
						if( options.type === 'start' ){
							options.clear();
						}
					})
					.on(events.out, function(){
						if( options.type === 'start' ){
							options.run();
						}
					});
				
				// 初始化
				options.move.width( (options.max + 1) * options.distance ),
				options.dish.width( options.menu.length * options.menu.eq(0).outerWidth(true) ),
				options.effect(0);
			}();
		});
	},
	
	// 选项卡
	tab: function(options){
		options = options || {}
		,options.container = $(options.container || '.tab')
		,options.event = options.event || events.over
		,options.active = options.active || 'active'
		,options.callback = options.callback || $.noop;
		
		$.each(options.container, function(i, container){
			var option = {};
			container = $(container)
			,option.menus = container.find(options.menus || 'dt li')
			,option.contents = container.find(options.contents || 'dd');
			
			if( option.menus.length ){
				option.menus.on(options.event, function(){
					var index = option.menus.index(this);
					option.menus.removeClass(options.active).eq(index).addClass(options.active),
					option.contents.hide().eq(index).show(),
					options.callback( options.container, option.menus, option.contents, index );
				}).eq(0).trigger(options.event);
			}
		});
	},
	
	// 滑动菜单
	expand: function(options){
		options = options || {}
		,options.menus = $(options.menus) || undefined
		,options.contents = $(options.contents) || undefined
		,options.callback = options.callback || $.noop;
		
		if( !options.menus ){
			return;
		}
		
		options.menus.on(events.click, function(){
			var index = options.menus.index(this);
			options.contents.eq( index ).slideToggle(function(){
				options.callback(options.menus, options.contents, index);
			});
		});
	}
});