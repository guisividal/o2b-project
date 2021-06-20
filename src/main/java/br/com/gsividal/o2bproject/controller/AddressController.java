package br.com.gsividal.o2bproject.controller;

import br.com.gsividal.o2bproject.dto.AddressDTO;
import br.com.gsividal.o2bproject.model.Address;
import br.com.gsividal.o2bproject.service.AddressService;
import br.com.gsividal.o2bproject.service.JWTUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static br.com.gsividal.o2bproject.service.JWTUtils.extractJWT;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    // CREATE
    @PostMapping
    public ResponseEntity<AddressDTO> createAddress(@Valid @RequestBody AddressDTO addressDTO, @RequestHeader("Authorization") String authorization) {
        JWTUtils.decodeJWT(extractJWT(authorization));
        Address address = addressService.createAddress(convert(addressDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(convert(address));
    }

    // FIND BY CEP
    @GetMapping
    public ResponseEntity<?> readByCep(@RequestParam("cep") String cep, @RequestHeader("Authorization") String authorization) {
        JWTUtils.decodeJWT(extractJWT(authorization));
        List<Address> addresses = addressService.findAddressByCep(cep);
        return ResponseEntity.status(HttpStatus.OK).body(convert(addresses));
    }

    // FIND
    @GetMapping("/{id}")
    public ResponseEntity<?> readAddress(@PathVariable("id") Long id, @RequestHeader("Authorization") String authorization) {
        JWTUtils.decodeJWT(extractJWT(authorization));
        return addressService.findAddressById(id)
                .map(address -> ResponseEntity.status(HttpStatus.OK).body(convert(address)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // UPDATE
    @PutMapping
    public ResponseEntity<AddressDTO> updateAddress(@Valid @RequestBody AddressDTO addressDTO, @RequestHeader("Authorization") String authorization) {
        JWTUtils.decodeJWT(extractJWT(authorization));
        return addressService.updateAddress(convert(addressDTO))
                .map(address -> ResponseEntity.status(HttpStatus.OK).body(convert(address)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<AddressDTO> deleteAddress(@PathVariable(value = "id") Long id, @RequestHeader("Authorization") String authorization) {
        JWTUtils.decodeJWT(extractJWT(authorization));
        return addressService.deleteAddress(id)
                .map(address -> ResponseEntity.status(HttpStatus.OK).body(convert(address)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    private List<AddressDTO> convert(List<Address> addresses) {
        List<AddressDTO> dtos = new ArrayList<>();
        for (Address address : addresses) {
            dtos.add(convert(address));
        }
        return dtos;
    }

    private AddressDTO convert(Address address) {
        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setId(address.getId());
        addressDTO.setCity(address.getCity());
        addressDTO.setState(address.getState());
        addressDTO.setNumber(address.getNumber());
        addressDTO.setCountry(address.getCountry());
        addressDTO.setCep(address.getCep());
        addressDTO.setStreetName(address.getStreetName());
        addressDTO.setNeighbourhood(address.getNeighbourhood());

        return addressDTO;
    }

    private Address convert(AddressDTO addressDTO) {
        Address address = new Address();

        address.setId(addressDTO.getId());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setNumber(addressDTO.getNumber());
        address.setCountry(addressDTO.getCountry());
        address.setCep(addressDTO.getCep());
        address.setStreetName(addressDTO.getStreetName());
        address.setNeighbourhood(addressDTO.getNeighbourhood());

        return address;
    }
}