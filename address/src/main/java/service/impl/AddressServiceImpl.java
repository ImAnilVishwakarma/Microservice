package service.impl;

import exception.ResourceNotFoundException;
import model.dto.AddressDto;
import model.dto.AddressRequest;
import model.dto.AddressRequestDto;
import model.entity.Address;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import respository.AddressRepository;
import service.AddressService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AddressServiceImpl implements AddressService {

    Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);

    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    public AddressServiceImpl(AddressRepository addressRepository, ModelMapper modelMapper) {
        this.addressRepository = addressRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<AddressDto> saveAddress(AddressRequest addressRequest) {
        // TODO: Check if employee exists

        List<Address> listTosave = this.saveOrUpdateAddressiequest(addressRequest);
        List<Address> savedAddress = addressRepository.saveAll(listTosave);
        return savedAddress.stream().map(address -> modelMapper.map(address, AddressDto.class)).toList();
    }

    @Override
    public List<AddressDto> updateAddress(AddressRequest addressRequest) {
        // Todo: check if employee exists

        List<Address> addressByEmpId = addressRepository.findAllByEmpld(addressRequest.getEmpld());
        if (addressByEmpId.isEmpty()) {
            log.info("No address found for employee id {}", addressRequest.getEmpld());
            log.info("Creating new address for employee id {}", addressRequest.getEmpld());
        }
        List<Address> listToUpdate = this.saveOrUpdateAddressiequest(addressRequest);
        List<Long> upcomingNonNullIds = listToUpdate.stream().map(Address::getId).filter(Objects::nonNull).toList();
        List<Long> existinglds = addressByEmpId.stream().map(Address::getId).toList();
        List<Long> idsToDelete = existinglds.stream().filter(id -> !upcomingNonNullIds.contains(id)).toList();

        if(!idsToDelete. isEmpty()){
            addressRepository.deleteAllById(idsToDelete);
        }
        List<Address> updatedAddress = addressRepository.saveAll(listToUpdate);
        return updatedAddress.stream().map( address -> modelMapper.map(address, AddressDto.class)).toList();
    }

    @Override
    public AddressDto getSingleAddress(Long id) {
        Address address = addressRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + id));
        return modelMapper.map(address, AddressDto.class);
    }

    @Override
    public List<AddressDto> getAllAddress() {
        List<Address> all = addressRepository.findAll();
        if(all.isEmpty()) {
            throw new ResourceNotFoundException("No address found");
        }
            return all.stream().map( address -> modelMapper.map(address, AddressDto.class)).toList();
        }

    @Override
    public void deleteAddress(Long id) {
        Address address = addressRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + id));
        addressRepository.delete(address);
    }

    private List<Address> saveOrUpdateAddressiequest(AddressRequest addressRequest) {
        List<Address> listTosave = new ArrayList<>();
        for (AddressRequestDto addressRequestDto : addressRequest.getAddressRequestDtoList()) {
            Address address = new Address();
            address.setId(addressRequestDto.getId() != null ? addressRequestDto.getId() : null);
            address.setStreet(addressRequestDto.getStreet());
            address.setCity(addressRequestDto.getCity());
            address.setCountry(addressRequestDto.getCountry());
            address.setPinCode(addressRequestDto.getPinCode());
            address.setAddressType(addressRequestDto.getAddressType());
            address.setEmpld(addressRequest.getEmpld());
            listTosave.add(address);
        }
        return listTosave;
     }
}
