package service.impl;

import client.AddressClient;
import exception.BadRequestException;
import exception.ResourceNotFoundException;
import model.dto.AddressDto;
import model.dto.EmployeeDto;
import model.entity.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import respository.EmployeeRepository;
import service.EmployeeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final AddressClient addressClient;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper, AddressClient addressClient) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
        this.addressClient = addressClient;
    }

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {

        Employee entity = modelMapper.map(employeeDto, Employee.class);
        Employee saveEntity = employeeRepository.save(entity);
        return modelMapper.map(saveEntity, EmployeeDto.class);
    }


    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        if (id == null || employeeDto.getId() == null) {
            throw new BadRequestException("Please provide employee id", HttpStatus.SERVICE_UNAVAILABLE);
        }
        if (!Objects.equals(id, employeeDto.getId())) {
            throw new BadRequestException("Id mismatch", HttpStatus.SERVICE_UNAVAILABLE);
        }
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id:" + id));
        Employee entity = modelMapper.map(employeeDto, Employee.class);
        Employee updatedEmployee = employeeRepository.save(entity);
        return modelMapper.map(updatedEmployee, EmployeeDto.class);
    }

    @Override
    public void deleteEmployee(long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id:" + id));
        employeeRepository.delete(employee);
    }


    @Override
    public EmployeeDto getSingleEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Employee not found with id: " + id));

        EmployeeDto dto = modelMapper.map(employee, EmployeeDto.class);

        try {
            List<AddressDto> addresses =
                    addressClient.getAddressByEmpld(employee.getId());

            dto.setAddressDtoList(addresses);

        } catch (Exception e) {
            log.error("Failed to fetch addresses for employee id: {}",
                    employee.getId(), e);
        }

        return dto;
    }


    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        if (employees.isEmpty()) {
            throw new ResourceNotFoundException("Employee not found");
        }
        List<EmployeeDto> employeeDtoList = employees.stream().map(emp -> modelMapper.map(emp, EmployeeDto.class)).toList();
        List<EmployeeDto> response = new ArrayList<>();
        for (EmployeeDto employee : employeeDtoList) {
            List<AddressDto> addresses = new ArrayList<>();
            try {
                addresses = addressClient.getAddressByEmpld(employee.getId());
                employee.setAddressDtoList(addresses);
            } catch (Exception e) {
                log.error("Address not found with employee id: {}", employee.getId());
            }
            response.add(employee);
        }
        return response;
    }


    @Override
    public EmployeeDto getEmployeeByEmpCodeAndCompanyName(String empCode, String companyName) {
      Employee employee =  employeeRepository.findByEmpCodeAndCompanyName(empCode, companyName).orElseThrow(() -> new ResourceNotFoundException("Employee not found with empcode :" + empCode + "and companyName: " + companyName));;
        return modelMapper.map(employee, EmployeeDto.class);
    }
}
