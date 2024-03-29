package org.wedding.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.wedding.application.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.wedding.common.response.ApiResponse;
import org.wedding.adapter.in.web.dto.LoginDTO;
import org.wedding.adapter.in.web.dto.SignUpDTO;

@Slf4j
@Tag(name="User API", description = "User API")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    @Operation(summary = "회원가입", description = "회원가입")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<Void>> signUp(@RequestBody @Valid SignUpDTO request) {
        userService.signUp(request);
        ApiResponse<Void> response = ApiResponse.successApiResponse(HttpStatus.CREATED, "회원가입이 완료되었습니다.");
        return new ResponseEntity<>(response, response.status());
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "로그인")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<Void>> login(@RequestBody @Valid LoginDTO request) {
        ApiResponse<Void> response = ApiResponse.successApiResponse(HttpStatus.OK, "로그인 성공했습니다.");
        userService.login(request);
        return new ResponseEntity<>(response, response.status());
    }

}
