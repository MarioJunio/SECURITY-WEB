$(function() {

	var tbCheckins = $('.js-tb-checkins');
	var map;
	
	loadMap();
	loadCheckins();
	
	setInterval(() => {
		loadCheckins();
	}, 30000);

	$('.js-btn-force-update').on('click', function(e) {
		e.preventDefault();
		loadCheckins();
	});
	
	function loadCheckins() {

		$.ajax({
			method : 'GET',
			url : '/app/checkins'
		}).done(function(msg) {
			
			// se não há registros não faça nada
			if (msg.length <= 0)
				return;

			tbCheckins.find('tbody>tr').remove();
			map.removeMarkers();

			$.each(msg, function(i, item) {
				
				var tooltipClass = isStatusSuspeitoOrDescricao(item.status) ? 'tooltipped' : '';
				var tooltipDescricao = isStatusSuspeitoOrDescricao(item.status) ? 'data-position="left" data-tooltip="' + item.descricao + '"' : '';
				
				addMarker(item.cliente, item.latitude, item.longitude);
				
				var tr = '<tr>';
				tr += '<td>' + item.empregado + '</td>';
				tr += '<td>' + item.cliente + '</td>';
				tr += '<td>' + item.data + '</td>';
				tr += '<td><span class="status-descricao new badge ' + tooltipClass + ' ' + getStatusColor(item.status) + '" style="float: left; margin-left: 0;" data-badge-caption="" ' + tooltipDescricao + '>' + item.status + '</span></td>';
				tr += '<td>' + '<a class="map-marker" data-lat="' + item.latitude + '" data-long="' + item.longitude + '" href="" onclick="return false;"><i class="fa fa-thumb-tack" aria-hidden="true"></i> Ver no mapa</a>' + '</td>';
				tr += '</tr>';

				tbCheckins.find('tbody').append(tr);
				
				$('.tooltipped').tooltip({delay: 10});

			});
			
			// inicializa a função ao clicar no botão 'Ver no mapa'
			$('.map-marker').on('click', function () { 
				
				var coords = new google.maps.LatLng($(this).attr('data-lat'), $(this).attr('data-long'));
				
				scrollToMap();
				
				map.fitLatLngBounds([coords]);
				map.zoomOut(6);
				
				return false;
			});
			
		}).fail(function(jqXHR, status) {
			console.log(status);
		});

	}
	
	function isStatusSuspeitoOrDescricao(status) {
		return (status == 'SUSPEITO' || status == 'PERIGO');
	}
	
	function getStatusColor(status) {
		return status == 'NORMAL' ? 'green' : (status == 'SUSPEITO' ? 'orange darken-1' : 'red');
	}
	
	function loadMap() {
		
		map = new GMaps({
			div : '#map',
			lat : -18.729985,
			lng : -47.498914,
			zoom : 15
		});

	}
	
	function addMarker(cliente, lat, long) {
		
		map.addMarker({
			lat : lat,
			lng : long,
			title : cliente,
			infoWindow : {
				content : cliente
			}
		});
	}
	
	function scrollToMap() {
		var map = $('#map');
		$('html,body').animate({scrollTop: map.offset().top},'slow');
	}

});