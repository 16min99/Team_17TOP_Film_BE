package com.programmers.film.api.user.controller;

import com.programmers.film.api.auth.dto.ProviderAttribute;
import com.programmers.film.api.config.interceptor.Auth;
import com.programmers.film.api.config.resolver.Provider;
import com.programmers.film.api.config.resolver.UserId;
import com.programmers.film.api.user.dto.request.SignUpRequest;
import com.programmers.film.api.user.dto.response.CheckNicknameResponse;
import com.programmers.film.api.user.dto.response.SignUpResponse;
import com.programmers.film.api.user.service.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("api/v1/users")
@RestController
public class UserController {

    private final UserService userService;

    @Auth
    @GetMapping("/duplicate")
    public ResponseEntity<Boolean> checkUserDuplicate(@UserId final Long userId) {
        return ResponseEntity.ok(userService.checkUser(userId));
    }

    @GetMapping("/{nickname}")
    public ResponseEntity<CheckNicknameResponse> checkNicknameDuplicate(
        @PathVariable final String nickname
    ) {
        CheckNicknameResponse response = userService.checkNickname(nickname);
        return ResponseEntity.ok(response);
    }

    @Auth
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signUp(
        @Valid @RequestBody final SignUpRequest request,
        @Provider final ProviderAttribute provider
    ) {
        SignUpResponse response = userService.signUp(request, provider);
        return ResponseEntity.ok(response);
    }
}