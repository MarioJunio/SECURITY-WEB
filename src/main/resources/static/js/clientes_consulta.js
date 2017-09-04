$(function() {

	// inicializa os elementos da view
	var searchBarSelect = $('#search-bar-select');
	var tableConsulta = $('#consulta');
	var fieldConsulta = $('#field-search');
	var fieldConsultaHidden = $('#field-consulta-hidden');
	var searchField = $('#search-field');
	var searchRadio = $('#search-radio');

	// busca todas as colunas da tabela
	var tableHeaders = tableConsulta.find('thead>tr>th');

	// limpa o select
	searchBarSelect.empty();

	// preenche o select de opções para filtragem
	tableHeaders.each(function() {

		// ignora colunas em branco, por exemplo de checkbox e de edição
		if (!!$(this).attr('data'))
			searchBarSelect.append($("<option " + (fieldConsultaHidden.val() == $(this).attr('data') ? 'selected' : '') + ">")
					.attr('value', $(this).attr('data')).text($(this).text()));
	});

	searchBarSelect.on('change', function() {
		fieldConsulta.val('');
		toggleSearchField(searchBarSelect.val());
	});

	toggleSearchField(searchBarSelect.val());

	marcarStatus();

	// ---------------------------------------------------------------------------------------------------------------
	// Funções utilitárias
	// ---------------------------------------------------------------------------------------------------------------
	function toggleSearchField(val) {

		if (searchBarSelect.val() == '<domain>.ativo') {

			searchField.fadeOut('fast', function() {
				searchRadio.fadeIn('fast');
			});

		} else {
			searchRadio.fadeOut('fast', function() {
				searchField.fadeIn('fast');
			});
		}

	}

	/**
	 * Marca o status caso nenhuma esteja selecionado
	 */
	function marcarStatus() {

		var radioStatus = $('input[name="status"]:checked');

		if (!!!radioStatus.length)
			$('#both').attr('checked', 'checked');
	}

});