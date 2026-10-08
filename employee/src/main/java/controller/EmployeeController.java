package controller;

import exception.MissingParameterException;
import model.dto.EmployeeDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.EmployeeService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/save")
    public ResponseEntity<EmployeeDto> saveEmployee(@RequestBody EmployeeDto employeeDto) {
        EmployeeDto saved = employeeService.saveEmployee(employeeDto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long id,
                                                      @RequestBody EmployeeDto employeeDto) {
        EmployeeDto updated = employeeService.updateEmployee(id, employeeDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>("Employee Delete Successfully", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getSingleEmployee(@PathVariable Long id) {
        EmployeeDto employee = employeeService.getSingleEmployee(id);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("get-by-emp-code-and-company-name")
    public ResponseEntity<EmployeeDto> getEmployeeByEmpCodeAndCompanyName(
            @RequestParam(required = false) String empCode,
            @RequestParam(required = false) String companyName) {

        List<String> missingParameters = new ArrayList<>();

        if (empCode == null || empCode.trim().isEmpty()) {
            missingParameters.add("empCode");
        }

        if (companyName == null || companyName.trim().isEmpty()) {
            missingParameters.add("companyName");
        }

        if (!missingParameters.isEmpty()) {

            String finalMessage = missingParameters.stream()
                    .collect(Collectors.joining(", "));

            throw new MissingParameterException(
                    "Please provide " + finalMessage
            );
        }

        EmployeeDto response =
                employeeService.getEmployeeByEmpCodeAndCompanyName(
                        empCode,
                        companyName
                );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}