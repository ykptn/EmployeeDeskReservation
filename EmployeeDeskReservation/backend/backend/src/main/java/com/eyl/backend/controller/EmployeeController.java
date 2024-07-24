package com.eyl.backend.controller;

import com.eyl.backend.LoginMessage;
import com.eyl.backend.LoginMessage;
import com.eyl.backend.dto.EmployeeDTO;
import com.eyl.backend.dto.LoginDTO;
import com.eyl.backend.dto.PasswordDTO;
import com.eyl.backend.entity.Employee;
import com.eyl.backend.repository.EmployeeRepository;
import com.eyl.backend.service.EmailService;
import com.eyl.backend.service.serviceImplementations.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService userService;
    private final EmailService emailService;
    private final EmployeeRepository employeeRepository;

    @PostMapping("/register")
    public ResponseEntity<EmployeeDTO> createUser(@RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO createdUser = userService.createUser(employeeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<EmployeeDTO> getUserById(@PathVariable Long userId) {
        EmployeeDTO employeeDTO = userService.getUserById(userId);
        return ResponseEntity.ok(employeeDTO);
    }

    @GetMapping("/employee-list")
    public ResponseEntity<List<EmployeeDTO>> getAllUsers() {
        List<EmployeeDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<EmployeeDTO> updateUser(@PathVariable Long userId, @RequestBody EmployeeDTO updatedUser) {
        EmployeeDTO userDTO = userService.updateUser(userId, updatedUser);
        return ResponseEntity.ok(userDTO);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO logindto){
        LoginMessage loginMesage =userService.login(logindto);
        if (loginMesage.getStatus()) {
            return ResponseEntity.ok(loginMesage); // Login success
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginMesage); // Login failed
        }
    }

    @PostMapping("/changePassword/{employeeId}")
    public ResponseEntity<Void> changePassword(@PathVariable Long employeeId, @RequestBody PasswordDTO passwordDTO) {
        userService.changePassword(employeeId, passwordDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestBody LoginDTO loginDTO) {
        String email = loginDTO.getEmail();
        EmployeeDTO employee = userService.findByEmail(email);
        if (employee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("E-posta bulunamadı");
        }

        // Generate a random password
        String newPassword = generateRandomPassword();
        String encryptedPassword = userService.encodePassword(newPassword);

        // Update the password in the database
        userService.updatePassword(employee.getEmpId(), encryptedPassword);

        // Send email with the new password
        emailService.sendEmail(email, "Yeni Şifre", "Yeni şifreniz: " + newPassword);

        return ResponseEntity.ok("Yeni şifre e-posta adresinize gönderildi.");
    }

    private String generateRandomPassword() {
        String symbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+[]{}|;:,.<>?";
        Random random = new SecureRandom();
        StringBuilder password = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            password.append(symbols.charAt(random.nextInt(symbols.length())));
        }
        return password.toString();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchEmployees(@RequestParam("query") String query) {
        return ResponseEntity.ok(userService.searchEmployees(query));
    }
}
