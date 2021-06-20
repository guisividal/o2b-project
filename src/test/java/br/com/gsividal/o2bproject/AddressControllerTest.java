package br.com.gsividal.o2bproject;

import br.com.gsividal.o2bproject.dto.AddressDTO;
import br.com.gsividal.o2bproject.service.JWTUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddressControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String JWT = JWTUtils.createJWT("1", "O2B", "gsividal", LocalDateTime.now().toInstant(ZoneOffset.UTC).getEpochSecond());
    
    @BeforeEach
    public void init() {
        restTemplate.getRestTemplate().setInterceptors(
                Collections.singletonList((request, body, execution) -> {
                    request.getHeaders()
                            .add("Authorization", "Bearer " + JWT);
                    return execution.execute(request, body);
                }));
    }

    @Test
    public void shouldReturnEmptyOnNotExistingCEP() {
        ResponseEntity<AddressDTO[]> entity = this.restTemplate.getForEntity("http://localhost:" + port + "/addresses?cep=13920-001", AddressDTO[].class);
        assertThat(entity.getStatusCode()).isEqualTo(OK);
        assertThat(entity.getBody()).hasSize(0);
    }

    @Test
    public void shouldCreateAddress() {
        // CREATE
        String cep = "13920-000";

        AddressDTO addressDTO = createAddress(2L, cep);
        ResponseEntity<AddressDTO> entity = this.restTemplate.postForEntity("http://localhost:" + port + "/addresses", addressDTO, AddressDTO.class);

        AddressDTO addressDTO2 = createAddress(3L, cep);
        ResponseEntity<AddressDTO> entity2 = this.restTemplate.postForEntity("http://localhost:" + port + "/addresses", addressDTO2, AddressDTO.class);

        assertThat(entity.getStatusCode()).isEqualTo(CREATED);
        assertThat(entity.getBody()).isNotNull();

        assertThat(entity2.getStatusCode()).isEqualTo(CREATED);
        assertThat(entity2.getBody()).isNotNull();

        // GET
        ResponseEntity<AddressDTO[]> getEntity = this.restTemplate.getForEntity("http://localhost:" + port + "/addresses?cep=" + cep, AddressDTO[].class);
        assertThat(getEntity.getStatusCode()).isEqualTo(OK);
        assertThat(getEntity.getBody()).hasSize(2);
    }

    @Test
    public void shouldDeleteAddress() {
        // CREATE
        AddressDTO addressDTO = createAddress(3L, "13920-000");
        ResponseEntity<AddressDTO> entity = this.restTemplate.postForEntity("http://localhost:" + port + "/addresses", addressDTO, AddressDTO.class);
        assertThat(entity.getBody()).isNotNull();

        // DELETE
        this.restTemplate.delete("http://localhost:" + port + "/addresses/{id}", Map.of("id", entity.getBody().getId()));

        // GET
        ResponseEntity<AddressDTO> getEntity = this.restTemplate.getForEntity("http://localhost:" + port + "/addresses/{id}", AddressDTO.class,
                Map.of("id", entity.getBody().getId()));
        assertThat(getEntity.getStatusCode()).isEqualTo(NOT_FOUND);
    }

    @Test
    public void shouldUpdateAddress() {
        // CREATE
        AddressDTO addressDTO = createAddress(4L, "13920-002");
        ResponseEntity<AddressDTO> entity = this.restTemplate.postForEntity("http://localhost:" + port + "/addresses", addressDTO, AddressDTO.class);
        assertThat(entity.getStatusCode()).isEqualTo(CREATED);
        assertThat(entity.getBody()).isNotNull();

        // CHANGE VALUE
        AddressDTO address = entity.getBody();

        // UPDATE
        this.restTemplate.put("http://localhost:" + port + "/addresses", address, AddressDTO.class);

        // GET
        ResponseEntity<AddressDTO> updatedAddress = this.restTemplate.getForEntity("http://localhost:" + port + "/addresses/{id}",
                AddressDTO.class, Map.of("id", address.getId()));

        assertThat(updatedAddress.getBody()).isNotNull();
    }

    private AddressDTO createAddress(Long id, String cep) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(id);
        addressDTO.setCity("Pedreira");
        addressDTO.setCountry("Brasil");
        addressDTO.setNeighbourhood("Morumbi");
        addressDTO.setNumber(105L);
        addressDTO.setState("SP");
        addressDTO.setCep(cep);
        addressDTO.setStreetName("Rua Louis Pasteur");

        return addressDTO;
    }
}