$(function () {

    var cloneCbAtivo = $('#clone-cb-ativo');

    // mascara dos campos da tela de cadastro
    $('#telefone1').mask('(00) 00000-0000');
    $('#telefone2').mask('(00) 00000-0000');
    $('#cep').mask('00000-000');

    // ao alterar o campo da tela, aplicaca o valor no campo a ser persistido que está invisível
    cloneCbAtivo.on('change', function () {

        console.log('alterou: ' + cloneCbAtivo.prop('checked'));
        $('#cb-ativo').prop('value', cloneCbAtivo.prop('checked'));
    });

});