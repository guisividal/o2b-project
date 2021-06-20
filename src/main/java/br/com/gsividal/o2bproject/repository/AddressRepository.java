package br.com.gsividal.o2bproject.repository;

import br.com.gsividal.o2bproject.model.Address;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends CrudRepository<Address, Long> {

    List<Address> findByCep(String cep);

    Optional<Address> findByStreetNameAndNumber(String streetName, Long number);
}
