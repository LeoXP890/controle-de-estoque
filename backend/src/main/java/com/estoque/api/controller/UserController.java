package com.estoque.api.controller;

import com.estoque.api.dto.UserResponse;
import com.estoque.api.model.User;
import com.estoque.api.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> listarTodos() {
        List<User> users = userService.listarTodos();
        List<UserResponse> responses = users.stream()
                .map(user -> new UserResponse(user.getId(), user.getUsername(), user.getRole()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> obterPerfil() {
        User user = userService.obterUsuarioLogado();
        UserResponse response = new UserResponse(user.getId(), user.getUsername(), user.getRole());
        return ResponseEntity.ok(response);
    }
}