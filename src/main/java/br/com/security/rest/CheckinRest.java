package br.com.security.rest;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.security.repository.CheckinRepository;

@RestController
@RequestMapping("/checkins")
public class CheckinRest {

	@Autowired
	private CheckinRepository checkinRepository;

	@GetMapping(value = "/foto/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	public @ResponseBody ResponseEntity<byte[]> foto(@PathVariable("id") Long id) {
		String fotoBase64 = checkinRepository.findFotoByCheckin(id);

		// se encontrou a imagem retorne os bytes dela
		if (!StringUtils.isEmpty(fotoBase64)) {
			byte[] fotoBytes = Base64.decodeBase64(fotoBase64);
			return ResponseEntity.ok(fotoBytes);
		}

		return ResponseEntity.notFound().build();
	}

}
