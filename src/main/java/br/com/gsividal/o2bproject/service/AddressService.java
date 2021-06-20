package br.com.gsividal.o2bproject.service;

import br.com.gsividal.o2bproject.model.Address;
import br.com.gsividal.o2bproject.repository.AddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public Address createAddress(Address address) {
        // todo: check uniqueness's
        address.setId(null);
        return addressRepository.save(address);
    }


    public Optional<Address> findAddressById(Long id) {
        log.info("Searching address with id: {}", id);
        return addressRepository.findById(id);
    }

    public List<Address> findAddressByCep(String cep) {
        log.info("Searching address with cep: {}", cep);
        return addressRepository.findByCep(cep);
    }

    public Optional<Address> updateAddress(Address addressUpdated) {
        log.info("Editing address with id: {}", addressUpdated.getId());

        return addressRepository.findById(addressUpdated.getId())
                .map(address -> {
                    address.setStreetName(addressUpdated.getStreetName());
                    address.setNumber(addressUpdated.getNumber());
                    address.setNeighbourhood(addressUpdated.getNeighbourhood());
                    address.setCity(addressUpdated.getCity());
                    address.setState(addressUpdated.getState());
                    address.setCountry(addressUpdated.getCountry());
                    address.setCep(addressUpdated.getCep());

                    return address;
                })
                .map(addressRepository::save);
    }

    public Optional<Address> deleteAddress(Long id) {
        log.info("Deleting address with id: {}", id);
        return addressRepository.findById(id)
                .map(addressFound -> {
                    addressRepository.deleteById(id);
                    return addressFound;
                });
    }
}
