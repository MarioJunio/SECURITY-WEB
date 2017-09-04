$(function() {

	var clientesArray = [];
	var clienteSelected = undefined;

	// busca todos os clientes assincronamente
	getClientesAjax();

	$('.modal').modal();
	
	// submete o formulario
	$('.js-gerar-relatorio').on('click', function (e) {
		e.preventDefault();
		
		if (!$('.js-cliente-id').val()) {
			$('.js-modal-content').text('Selecione um cliente valido para Gerar o Relatório!');
			$('.js-modal-title').text('Atenção');
			$('#modal1').modal('open');
			return false;
		}
		
		$('.js-form-relatorio').submit();
	});
	
	$('.js-autocomplete-cliente').keyup(function() {

		if ($(this).val() != clienteSelected) {
			$('.js-confirm-fields').addClass('invisible');
			$('.js-cliente-id').val('');
			$('.js-cliente-nome').val('');
			$('.js-confirm1').val('');
			$('.js-confirm2').val('');
		}

	});

	// inicializa os campos do tipo datepicker
	$('.datepicker').pickadate(
			{
				monthsFull : [ 'Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro',
						'Outubro', 'Novembro', 'Dezembro' ],
				monthsShort : [ 'Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez' ],
				weekdaysFull : [ 'Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta', 'Sabádo' ],
				weekdaysShort : [ 'Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab' ],
				weekdaysLetter : [ 'D', 'S', 'T', 'Q', 'Q', 'S', 'S' ],
				today : 'Hoje',
				clear : 'Limpar',
				close : 'OK',
				labelMonthNext : 'Próximo mês',
				labelMonthPrev : 'Mês anterior',
				labelMonthSelect : 'Selecione um mês',
				labelYearSelect : 'Selecione um ano',
				selectMonths : true,
				selectYears : 15,
				closeOnSelect : true,
				format : 'dd/mm/yyyy'
			});

	// Busca todos os clientes de forma assincrona
	function getClientesAjax() {

		$.ajax({

			method : 'GET',
			url : '/ajax/clientes/buscar',
			data : {
				nome : ''
			}

		}).done(function(msg) {

			// limpa o array de clientes atual
			clientesArray = [];

			var tmpData = {};

			$.each(msg, function(i, item) {
				tmpData[item.id + ' - ' + item.nome] = null;

				clientesArray[item.id] = {
					'nome' : item.nome,
					'cpf' : item.cpf,
					'cnpj' : item.cnpj,
					'celular' : item.telefone1
				};
			});

			initAutocomplete(tmpData);

		}).fail(function(xhr, status) {
			console.log(status);
		});

	}

	function initAutocomplete(data) {

		$('input.autocomplete').autocomplete({
			data : data,
			limit : 20, // The max amount of results that can be shown at once.
			// Default: Infinity.
			onAutocomplete : function(val) {

				var id = parseInt(val.substring(0, val.indexOf('-')).trim());
				var confirm = clientesArray[id];

				$('.js-cliente-id').val(id);
				$('.js-cliente-nome').val(confirm.nome);
				$('.js-confirm1').val(confirm.cpf ? confirm.cpf : confirm.cnpj);
				$('.js-confirm2').val(confirm.celular);

				$('.js-confirm-fields').removeClass('invisible');

				clienteSelected = val;

			},
			minLength : 1
		// The minimum length of the input for the
		// autocomplete
		// to start. Default: 1.
		});

	}
});