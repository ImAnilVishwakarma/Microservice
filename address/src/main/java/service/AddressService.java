package service;

import model.dto.AddressDto;
import model.dto.AddressRequest;

import java.util.List;

public interface AddressService {

    List<AddressDto> saveAddress(AddressRequest addressRequest);
    List<AddressDto> updateAddress (AddressRequest addressRequest);
    AddressDto getSingleAddress(Long id);
    List<AddressDto> getAllAddress(Long empld);
    void deleteAddress(Long id);
    List<AddressDto> getAddressByEmpld(Long empld);
    List<AddressDto> getAllAddress();
}
