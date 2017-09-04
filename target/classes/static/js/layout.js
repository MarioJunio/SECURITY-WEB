$(function() {

	var searchBar = $('.bar');
	var mainBar = $('#main-bar');
	var fabConsultaDelete = $('#fab-delete');
	var fieldSelectedRecords = $('#confirm-records');
	var recordsStorage = [];

	// inicializa o menu de navegação
	$('.button-collapse').sideNav({

		menuWidth : 300,
		edge : 'left',
		closeOnClick : true,
		draggable : true,
		onOpen : function(el) {
		},
		onClose : function(el) {
		},
	});

	// initialize all modals
	$('.modal').modal({
		dismissible : true, // Modal can be dismissed by clicking outside of the
		// modal
		opacity : .5, // Opacity of modal background
		inDuration : 300, // Transition in duration
		outDuration : 200, // Transition out duration
		startingTop : '4%', // Starting top style attribute
		endingTop : '10%', // Ending top style attribute
		ready : function(modal, trigger) { // Callback for Modal open. Modal

			var map = new GMaps({
				div : '#map',
				lat : -18.7024,
				lng : -47.4882,
				zoom : 17
			});

			map.addMarker({
				lat : -18.7024,
				lng : -47.4882,
				title : 'Lima',
				click : function(e) {
				},

				infoWindow : {
					content : '<p>Conteudo</p>'
				}
			});
			
		},
		complete : function() {
		} // Callback for Modal close
	});

	// ao clicar no botão menu, a função é chamada apenas uma vez
	$('.button-collapse').click(function(e) {
		e.stopImmediatePropagation();
	});

	// inicializa o select do Materializecss
	$('select').material_select();

	$('#btn-search').on('click', function() {
		toggleSearchBar();
		return false;
	});

	$('.search-bar-btn-exit').on('click', function() {
		toggleSearchBar();
		return false;
	});

	$(document).keyup(function(e) {

		if (!searchBar.length)
			return;

		if (e.keyCode == 27)
			toggleSearchBar();
	});

	// evento disparado na tela de consulta ao selecionar algum checkbox para
	// exclusão
	$('.cb-delete').on('change', function() {
		consultaBehaviors();

		var isChecked = $(this).is(':checked');
		var recordID = $(this).attr('data');

		var index = recordsStorage.indexOf(recordID);

		// se o elemento já está no array
		if (index >= 0) {

			// remove o elemento, se o checkbox foi desmarcado
			if (!isChecked)
				recordsStorage.splice(index, 1);
		} else
			recordsStorage.push(recordID);

	});

	fabConsultaDelete.on('click', function() {
		fieldSelectedRecords.val('');
		fieldSelectedRecords.val(recordsStorage.join());
	});

	$('.widget-counter').hover(function() {
		$(this).find('.widget-body').addClass('widget-body-hover');
	}, function() {
		$(this).find('.widget-body').removeClass('widget-body-hover');
	});

	$('.count').each(function() {

		$(this).prop('Counter', 0).animate({
			Counter : $(this).text()
		}, {
			duration : 2000,
			easing : 'swing',
			step : function(now) {
				$(this).text(Math.ceil(now));
			}
		});
	});

	function toggleSearchBar() {
		if (searchBar.hasClass('scale-in')) {
			searchBar.toggleClass('invisible');
			searchBar.toggleClass('scale-out');
			searchBar.toggleClass('scale-in');
			mainBar.fadeToggle('fast');
		} else {
			mainBar.fadeToggle('fast', function() {
				searchBar.toggleClass('invisible');
				searchBar.toggleClass('scale-out');
				searchBar.toggleClass('scale-in');
			});
		}

	}

	function consultaBehaviors() {

		var cbChecked = $('.cb-delete:checked');

		if (cbChecked.length > 0) {

			cbChecked.each(function() {

				if (!fabConsultaDelete.hasClass('scale-in'))
					fabConsultaDelete.addClass('scale-in');
			});

		} else
			fabConsultaDelete.removeClass('scale-in');
	}

});