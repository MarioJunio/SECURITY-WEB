$(function() {
	$('#message-bar').addClass('message-bar-active');

	$('#mb-close').bind('click', function() {

		$('#message-bar').slideUp('slow', function() {
			$('#message-bar').remove();
		});
	});

	$('.box-login').hover(function() {
		$('.img-logo').addClass('img-logo-hover');
	}, function() {
		$('.img-logo').removeClass('img-logo-hover');
	});

});