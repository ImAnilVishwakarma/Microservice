package service.impl;

import exception.BadRequestException;
import exception.ResourceNotFoundException;
import model.dto.EmployeeDto;
import model.entity.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import respository.EmployeeRepository;
import service.EmployeeService;

import java.util.List;
import java.util.Objects;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    final private ModelMapper modelMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }
    
    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {

        Employee entity = modelMapper.map(employeeDto, Employee.class);
        Employee saveEntity = employeeRepository.save(entity);
        return modelMapper.map(saveEntity, EmployeeDto.class);
    }


    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        if(id == null || employeeDto.getId() == null) {
            throw new BadRequestException("Please provide employee id");
        }
            if(!Objects.equals(id, employeeDto.getId())) {
                throw new BadRequestException("Id mismatch");
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
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id:" + id));
      return modelMapper.map(employee, EmployeeDto.class);
}
    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        if(employees.isEmpty()) {
            throw new ResourceNotFoundException("Employee not found");
        }
        return employees.stream().map(emp -> modelMapper.map(emp, EmployeeDto.class)).toList();
    }

    @Override
    public EmployeeDto getEmployeeByEmpCodeAndCompanyName(String empCode, String companyName) {
      Employee employee =  employeeRepository.findByEmpCodeAndCompanyName(empCode, companyName).orElseThrow(() -> new ResourceNotFoundException("Employee not found with empcode :" + empCode + "and companyName: " + companyName));;
        return modelMapper.map(employee, EmployeeDto.class);
    }
}
