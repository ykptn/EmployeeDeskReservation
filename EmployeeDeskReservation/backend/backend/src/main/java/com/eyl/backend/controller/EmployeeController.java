package com.eyl.backend.controller;

import com.eyl.backend.LoginMessage;
import com.eyl.backend.LoginMessage;
import com.eyl.backend.dto.EmployeeDTO;
import com.eyl.backend.dto.LoginDTO;
import com.eyl.backend.dto.PasswordDTO;
import com.eyl.backend.service.serviceImplementations.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService userService;

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
}
