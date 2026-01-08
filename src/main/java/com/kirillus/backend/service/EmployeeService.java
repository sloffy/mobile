package com.kirillus.backend.service;

import com.kirillus.backend.dto.EmployeeDTO;
import com.kirillus.backend.entity.Employee;
import com.kirillus.backend.entity.EmployeePhoto;
import com.kirillus.backend.repository.EmployeeRepository;
import com.kirillus.backend.repository.EmployeePhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeePhotoRepository employeePhotoRepository;

    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public EmployeeDTO getEmployeeById(Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Сотрудник не найден"));
        return toDTO(employee);
    }

    @Transactional
    public EmployeeDTO createEmployee(EmployeeDTO dto) {
        if (employeeRepository.findByEmployeeNumber(dto.getEmployeeNumber()).isPresent()) {
            throw new RuntimeException("Сотрудник с таким табельным номером уже существует");
        }

        Employee employee = new Employee();
        employee.setFullName(dto.getFullName());
        employee.setPosition(dto.getPosition());
        employee.setEmployeeNumber(dto.getEmployeeNumber());

        employee = employeeRepository.save(employee);
        return toDTO(employee);
    }

    @Transactional
    public EmployeeDTO updateEmployee(Integer id, EmployeeDTO dto) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Сотрудник не найден"));

        // Проверка уникальности табельного номера
        employeeRepository.findByEmployeeNumber(dto.getEmployeeNumber())
                .ifPresent(emp -> {
                    if (!emp.getId().equals(id)) {
                        throw new RuntimeException("Сотрудник с таким табельным номером уже существует");
                    }
                });

        employee.setFullName(dto.getFullName());
        employee.setPosition(dto.getPosition());
        employee.setEmployeeNumber(dto.getEmployeeNumber());

        employee = employeeRepository.save(employee);
        return toDTO(employee);
    }

    @Transactional
    public void deleteEmployee(Integer id) {
        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("Сотрудник не найден");
        }
        employeeRepository.deleteById(id);
    }

    @Transactional
    public void uploadPhoto(Integer employeeId, MultipartFile file) throws IOException {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Сотрудник не найден"));

        EmployeePhoto photo = employeePhotoRepository.findByEmployeeId(employeeId).orElse(new EmployeePhoto());
        photo.setFilename(file.getOriginalFilename());
        photo.setMimeType(file.getContentType());
        photo.setEmployee(employee);

        employeePhotoRepository.save(photo);
    }

    private EmployeeDTO toDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setFullName(employee.getFullName());
        dto.setPosition(employee.getPosition());
        dto.setEmployeeNumber(employee.getEmployeeNumber());
        dto.setCreatedAt(employee.getCreatedAt());

        if (employee.getPhoto() != null) {
            dto.setPhotoFilename(employee.getPhoto().getFilename());
        }

        return dto;
    }
}

