package service;

import model.dto.AddressDto;
import model.dto.AddressRequest;

import java.util.List;

public interface AddressService {

    AddressDto saveAddress(AddressRequest addressRequest);
    AddressDto updateAddress (AddressRequest addressRequest);
    AddressDto getSingleAddress(Long id);
    List<AddressDto> getAllAddress();
    void deleteAddress(Long id);


}
