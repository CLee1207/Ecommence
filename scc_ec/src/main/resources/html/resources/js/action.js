jQuery(function($){

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