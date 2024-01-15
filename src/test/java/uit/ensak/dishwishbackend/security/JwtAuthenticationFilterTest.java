package uit.ensak.dishwishbackend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import uit.ensak.dishwishbackend.exception.VerificationTokenNotFoundException;
import uit.ensak.dishwishbackend.model.Client;
import uit.ensak.dishwishbackend.model.VerificationToken;
import uit.ensak.dishwishbackend.service.auth.VerificationTokenService;

import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {
    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private VerificationTokenService tokenService;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void doFilterInternal_ValidToken_SuccessfulAuthentication() throws ServletException, IOException, VerificationTokenNotFoundException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        String jwt = "validToken";
        String userEmail = "meryemelhassouni@example.com";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwt);
        when(jwtService.extractUsername(jwt)).thenReturn(userEmail);

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername(userEmail)).thenReturn(userDetails);

        when(jwtService.isTokenValid(jwt, userDetails)).thenReturn(true);
        when(tokenService.getByToken(jwt)).thenReturn(createValidVerificationToken());

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        verify(filterChain, times(1)).doFilter(request, response);
    }

    private VerificationToken createValidVerificationToken() {
        Client client = new Client();

        String token = UUID.randomUUID().toString();

        Calendar expirationTime = Calendar.getInstance();
        expirationTime.add(Calendar.HOUR, 1);

        String code = UUID.randomUUID().toString();

        VerificationToken verificationToken = new VerificationToken(client, token, code);
        verificationToken.setExpirationTime(expirationTime.getTime());

        verificationToken.setRevoked(false);
        verificationToken.setExpired(false);

        return verificationToken;
    }

}
