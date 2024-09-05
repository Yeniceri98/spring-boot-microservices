package com.example.authmicroservice.service;

import com.example.authmicroservice.dto.AuthResponseDto;
import com.example.authmicroservice.dto.LoginRequestDto;
import com.example.authmicroservice.dto.RegisterRequestDto;
import com.example.authmicroservice.dto.UserProfileRequestDto;
import com.example.authmicroservice.exception.PasswordsMismatchException;
import com.example.authmicroservice.feign.UserProfileManager;
import com.example.authmicroservice.model.Auth;
import com.example.authmicroservice.repository.AuthRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTests {

    @Mock
    private AuthRepository authRepository;

    @Mock
    private UserProfileManager userProfileManager;   // Feign Interface

    @InjectMocks
    private AuthService authService;

    private Auth auth;
    private LoginRequestDto loginRequestDto;
    private LoginRequestDto loginRequestDtoFail;
    private RegisterRequestDto registerRequestDto;
    private RegisterRequestDto registerRequestDtoFail;

    @BeforeEach
    public void init() {
        auth = Auth.builder()
                .id(1L)
                .username("user")
                .password("123")
                .email("user@gmail.com")
                .isActive(true)
                .createdAt(LocalDate.now())
                .build();
        loginRequestDto = LoginRequestDto.builder()
                .username("user")
                .password("123")
                .build();
        loginRequestDtoFail = LoginRequestDto.builder()
                .username("user")
                .password("54321")    // Wrong password
                .build();
        registerRequestDto = RegisterRequestDto.builder()
                .username("user")
                .password("123")
                .repassword("123")
                .email("user@gmail.com")
                .build();
        registerRequestDtoFail = RegisterRequestDto.builder()
                .username("user")
                .password("123")
                .repassword("54321")  // Password does not match
                .email("user@gmail.com")
                .build();
    }

    @Test
    public void register_Success() throws PasswordsMismatchException {
        // Arrange
        when(authRepository.save(any(Auth.class))).thenReturn(auth);

        // Act
        AuthResponseDto registeredUser = authService.register(registerRequestDto);

        // Assert
        assertThat(registeredUser).isNotNull();               // AssertJ
        assertEquals(auth.getId(), registeredUser.getId());   // JUnit (Jupiter)
        assertEquals(auth.getUsername(), registeredUser.getUsername());
        assertEquals(auth.getEmail(), registeredUser.getEmail());
        assertEquals(auth.getCreatedAt(), registeredUser.getCreatedAt());

        // Verify
        /*
            - Feign Client kullanılarak başka bir mikroservise çağrı yapıldığında, genellikle sadece verify() kullanarak bu çağrının gerçekleşip gerçekleşmediğini test etmek yeterlidir
            - Daha derinlemesine bir test gerekirse, Feign çağrısını tam anlamıyla simüle edip, yanıtların doğru işlendiği kontrol edilebilir

            times()
            - times(1) ifadesi, ilgili metodun bir kez çağrılmış olması gerektiğini belirtir
            - Eğer metod daha fazla ya da daha az çağrılmışsa, test başarısız olur
        */
        verify(authRepository, times(1)).save(any(Auth.class));
        verify(userProfileManager, times(1)).createUser(any(UserProfileRequestDto.class));
    }

    @Test
    public void register_Fail_PasswordsDoNotMatch() {
        // Act
        assertThrows(PasswordsMismatchException.class, () -> {
            authService.register(registerRequestDtoFail);
        });

        // Verify (Hata durumunda repository.save ve Feign Client çağrılmamış olması lazım)
        verify(authRepository, never()).save(any(Auth.class));
        verify(userProfileManager, never()).createUser(any(UserProfileRequestDto.class));
    }

    @Test
    public void login_Success() {
        // Arrange
        when(authRepository.findByUsername("user")).thenReturn(auth);
        when(authRepository.existsByUsernameAndPassword("user", "123")).thenReturn(true);

        // Act
        Boolean result = authService.login(loginRequestDto);

        // Assert
        assertTrue(result);

        // Verify
        verify(authRepository, times(1)).findByUsername("user");
        verify(authRepository, times(1)).existsByUsernameAndPassword("user", "123");
    }

    @Test
    public void login_Fail_WrongPassword() {
        // Arrange
        when(authRepository.findByUsername("user")).thenReturn(auth);
        when(authRepository.existsByUsernameAndPassword("user", "54321")).thenReturn(false);

        // Act
        Boolean result = authService.login(loginRequestDtoFail);

        // Assert
        assertFalse(result);

        // Verify
        verify(authRepository, times(1)).findByUsername("user");
        verify(authRepository, times(1)).existsByUsernameAndPassword("user", "54321");
    }

    @Test
    public void login_Fail_UserNotFound() {
        // Arrange
        when(authRepository.findByUsername("user")).thenReturn(null);   // User won't be found

        // Act
        Boolean result = authService.login(loginRequestDtoFail);

        // Assert
        assertFalse(result);    // Should be false because no user found

        // Verify
        verify(authRepository, times(1)).findByUsername("user");
        verify(authRepository, never()).existsByUsernameAndPassword(anyString(), anyString());
    }
}
