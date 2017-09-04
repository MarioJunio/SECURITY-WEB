$(function() {

	var fieldSearch = $('#search');
	var tbClientes = $('#tb-clientes');
	var cbGerarAll = $('.js-cb-gerar-all');
	var loading = $('.js-loading');
	var fabGerar = $('.js-btn-gerar');
	var oldLength = -1;
	var checkeds = [];

	fieldSearch.keyup(function() {

		var nomeTyped = fieldSearch.val();

		if (nomeTyped.length == 0 && oldLength == 0)
			return;

		if (nomeTyped.length == 0 || nomeTyped.length > 2)
			buscarClientesAjax(nomeTyped);

		oldLength = nomeTyped;

	});

	// busca todos os clientes ao carregar a página
	buscarClientesAjax('');

	// marca ou desmarca todos os checkbox
	$('.js-cb-gerar-all').on('change', function() {

		if ($(this).is(':checked')) {
			$('.js-cb-gerar').prop('checked', 'checked');
		} else {
			$('.js-cb-gerar').prop('checked', '');
		}

		$('.js-cb-gerar').each(function(i, item) {
			appendOrRemoveChecked($(this).attr('data-id'), $(this).is(':checked'));
		});

		toggleFabGerar();

	});

	// quando é clicado no botão gerar QRCode
	$('.js-btn-gerar').on('click', function() {

		var tmpIds = '';

		$('.js-cb-gerar:checked').each(function(i, item) {
			tmpIds += $(item).attr('data') + ',';
		});

		$('.js-hidden-records').val(tmpIds.substring(0, tmpIds.length - 1).trim());
		$('.js-form-gerar').submit();

	});

	function buscarClientesAjax(nome) {

		loading.fadeToggle('fast');

		$.ajax({
			method : 'GET',
			url : '/qrcode/clientes/buscar',
			data : {
				nome : nome.trim()
			}

		}).done(
				function(msg) {

					var allAlreadyChecked = true;

					loading.fadeToggle('fast');

					$('#tb-clientes>tbody>tr').remove();

					$.each(msg, function(i, item) {

						var checkedAttr = '';

						if (isAlreadyChecked(item.id))
							checkedAttr = 'checked="checked"';
						else
							allAlreadyChecked = false;

						// console.log(checkeds);
						// console.log(item.id + ': ' +item.nome + ': ' +
						// checkeds.indexOf(item.id));

						var tr = '<tr>';
						tr += '<td style="width: 5%;">' + '<input data-id="' + item.id + '" data="' + item.id + ':' + item.nome
								+ '" id="' + item.id + '_cb" type="checkbox" class="filled-in js-cb-gerar" id="filled-in-box" '
								+ checkedAttr + '/> <label for="' + item.id + '_cb"></label></td>';
						tr += '<td style="width: 35%;">' + item.nome + '</td>';
						tr += '<td style="width: 25%;">' + item.telefone1 + '</td>';
						tr += '<td style="width: 25%;">' + (item.cpf ? item.cpf : item.cnpj) + '</td>';
						tr += '<td style="text-align: right;">';
						tr += '<span class="new badge ' + (item.ativo ? 'green' : 'red') + '" data-badge-caption="">'
								+ (item.ativo ? 'ATIVO' : 'INATIVO') + '</span></td>';
						tr += '</tr>';

						tbClientes.find('tbody').append(tr);

					});

					$('.js-cb-gerar').on('change', function() {

						var id = $(this).attr('data-id');
						var isChecked = $(this).is(':checked');

						appendOrRemoveChecked(id, isChecked);
						toggleFabGerar();

						// marca ou desmarca o checkbox todos, de acordo com o
						// número de checkbox marcados
						if ($('.js-cb-gerar').length <= $('.js-cb-gerar:checked').length)
							$('.js-cb-gerar-all').prop('checked', 'checked');
						else
							$('.js-cb-gerar-all').prop('checked', '');

					});
					
					// desmarca o checkbox todos selecionados, caso não estejam ao realizar uma nova busca
					if (!allAlreadyChecked)
						cbGerarAll.prop('checked', '');

				}).fail(function(jqXHR, status) {
			loading.fadeToggle('fast');
			console.log(status);
		});
	}

	function isAlreadyChecked(id) {
		return checkeds.indexOf(id) >= 0;
	}

	function appendOrRemoveChecked(id, isChecked) {

		// obtem o indice do elemento no array
		var index = checkeds.indexOf(parseInt(id));

		// se o checkbox foi marcado, então o elemento será
		// adicionado ao array, caso contrário removido
		if (isChecked) {

			// se o array contem o elemento, o indice será >= 0,
			// caso contrário não contem, então será necessário
			// adiciona-lo
			if (index < 0)
				checkeds.push(parseInt(id));

		} else {

			if (index >= 0)
				checkeds.splice(parseInt(index), 1);
		}
	}

	function toggleFabGerar() {

		var cbChecked = $('.js-cb-gerar:checked');

		if (cbChecked.length > 0) {

			cbChecked.each(function() {

				if (!fabGerar.hasClass('scale-in'))
					fabGerar.addClass('scale-in');
			});

		} else
			fabGerar.removeClass('scale-in');

	}

});