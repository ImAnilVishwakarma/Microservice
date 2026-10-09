package controller;

import model.dto.AddressDto;
import model.dto.AddressRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.AddressService;
import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;

    }

    @PostMapping("/save")
    public ResponseEntity<List<AddressDto>> saveAddress(@RequestBody AddressRequest addressDto) {
        List<AddressDto> response = addressService.saveAddress(addressDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<List<AddressDto>> updateAddress(@RequestBody AddressRequest addressDto) {
        List<AddressDto> response = addressService.updateAddress(addressDto) ;
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/all-address")
                public ResponseEntity<List<AddressDto>> getAllAddress() {
        List<AddressDto> response = addressService.getAllAddress();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{addressId}")
       public ResponseEntity<AddressDto> getAddressByld(@PathVariable Long addressId) {
        AddressDto response = addressService.getSingleAddress(addressId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
        @DeleteMapping("/delete/{addressId}")
        public ResponseEntity<String> deleteAddress(@PathVariable Long addressId) {
            addressService.deleteAddress(addressId);
            return new ResponseEntity<>("Address deleted successfully", HttpStatus.OK);
        }
}

