$(function() {

	var cloneCbAtivo = $('#clone-cb-ativo');
	var rbPF = $('#pf');
	var rbPJ = $('#pj');

	var tmpCPF = $('#cpf');
	var tmpCNPJ = $('#cnpj');

	// mascara dos campos da tela de cadastro
	$('#cpf').mask('000.000.000-00');
	$('#cnpj').mask('00.000.000/0000-00');
	$('#telefone1').mask('(00) 00000-0000');
	$('#telefone2').mask('(00) 00000-0000');
	$('#cep').mask('00000-000');

	// habilita ou desabilita o cpf/cnpj de acordo com o tipo da pessoa
	rbPF.on('change', function() {

		$('#cnpj').prop('disabled', 'disabled');
		$('#cnpj').val('');
		$('#cnpj').removeClass('valid').removeClass('invalid');

		if (rbPF.prop('checked') == true) {
			$('#cpf').removeAttr('disabled');
		}

		$('#cpf').focus();

	});

	rbPJ.on('change', function() {

		$('#cpf').prop('disabled', 'disabled');
		$('#cpf').val('');
		$('#cpf').removeClass('valid').removeClass('invalid');

		if (rbPJ.prop('checked') == true)
			$('#cnpj').removeAttr('disabled');

		$('#cnpj').focus();
	});

	// ao alterar o campo da tela, aplicaca o valor no campo a ser persistido
	// que está invisível
	cloneCbAtivo.on('change', function() {
		$('#cb-ativo').prop('value', cloneCbAtivo.prop('checked'));
	});

	// evento disparado ao marcar o checkbox alterar senha
	$('#cb-change-passwd').on('change', function() {
		$('#passwd1').fadeToggle('fast');
		$('#passwd2').fadeToggle('fast');
	});

	if (tmpCPF.val() && !!tmpCPF.val().trim()) {
		tmpCPF.removeAttr('disabled');
	}

	if (tmpCNPJ.val() && !!tmpCNPJ.val().trim()) {
		tmpCNPJ.removeAttr('disabled');
	}

	setTimeout(function() {
		var cbAtivo = $('#cb-ativo');

		if (cbAtivo.val() == 'true')
			cloneCbAtivo.attr('checked', 'checked');
		else
			cloneCbAtivo.removeAttr('checked');

	}, 0);

});