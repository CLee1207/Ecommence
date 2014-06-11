jQuery(function($){

/* U-Editor */
if( window.UE ){
	UE.getEditor('editor');
}

/* Datepicker */
if( $.fn.datepicker ){
	$.datepicker.regional['zh-cn'] = {
		closeText: '关闭',
		prevText: '&#x3c;上月',
		nextText: '下月&#x3e;',
		currentText: '今天',
		monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
		monthNamesShort: ['一', '二', '三', '四', '五', '六', '七', '八', '九', '十', '十一', '十二'],
		dayNames: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
		dayNamesShort: ['周日', '周一', '周二', '周三', '周四', '周五', '周六'],
		dayNamesMin: ['日', '一', '二', '三', '四', '五', '六'],
		weekHeader: '周',
		dateFormat: 'yy-mm-dd',
		firstDay: 1,
		isRTL:! 1,
		showMonthAfterYear: !0,
		yearSuffix: '年'
	}
	$.datepicker.setDefaults( $.datepicker.regional['zh-cn'] );
	$('[datepicker=true]').datepicker({
		showOtherMonths: true,
		selectOtherMonths: true
	});
}

/* 全站 - tab */
$.tab(),
$.tab({
	container: '.detail-main',
	menus: 'dt li',
	event: 'click'
}),
$.tab({
	container: '.magnifier',
	menus: '.magnifier-menu li',
	event: 'click',
	callback: function( container, menus, contents, index ){
		container.find('.magnifier-view img').attr('src', menus.eq(index).attr('data-url'));
	}
}),
$.tab({
	container: '.publish-catalogue',
	menus: 'dt li',
	event: 'click'
});

/* 全站 - expand */
$.expand({
	menus: '.commodity-list > dd > span > i',
	contents: '.commodity-list > dd > ul',
	callback: function( menus, contents, index ){
		var menu = menus.eq(index),
			content = contents.eq(index);
		
		content.is(':hidden') ?
			menu.removeClass('icon-minus').addClass('icon-plus'):
			menu.removeClass('icon-plus').addClass('icon-minus');
	}
}),
$.expand({
	menus: '.search-pack',
	contents: '.search-select > dd',
	callback: function( menus, contents, index ){
		var menu = menus.eq(index),
			content = contents.eq(index);
		
		content.is(':hidden') ?
			menu.find('i').removeClass('icon-minus').addClass('icon-plus'):
			menu.find('i').removeClass('icon-plus').addClass('icon-minus');
	}
});

/* 首页 - 焦点图 */
if( $('.carous').length ){
	$.carous();
}


});