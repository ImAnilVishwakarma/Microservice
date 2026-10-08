package model.dto;


import java.util.List;

public class AddressRequest {

    private Long empld;
    private List<AddressRequestDto> addressRequestDtoList;

    public Long getEmpld() {
        return empld;
    }

    public void setEmpld(Long empld) {
        this.empld = empld;
    }

    public List<AddressRequestDto> getAddressRequestDtoList() {
        return addressRequestDtoList;
    }

    public void setAddressRequestDtoList(List<AddressRequestDto> addressRequestDtoList) {
        this.addressRequestDtoList = addressRequestDtoList;
    }
}
