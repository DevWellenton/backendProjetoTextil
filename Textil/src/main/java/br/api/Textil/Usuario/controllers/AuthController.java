package br.api.Textil.Usuario.controllers;

import br.api.Textil.Enum.EnumStatus;
import br.api.Textil.Usuario.TokenRefreshException;
import br.api.Textil.Usuario.models.ERole;
import br.api.Textil.Usuario.models.RefreshToken;
import br.api.Textil.Usuario.models.Role;
import br.api.Textil.Usuario.models.User;
import br.api.Textil.Usuario.payload.request.LoginRequest;
import br.api.Textil.Usuario.payload.request.SignupRequest;
import br.api.Textil.Usuario.payload.request.TokenRefreshRequest;
import br.api.Textil.Usuario.payload.response.JwtResponse;
import br.api.Textil.Usuario.payload.response.MessageResponse;
import br.api.Textil.Usuario.payload.response.TokenRefreshResponse;
import br.api.Textil.Usuario.repository.RefreshTokenService;
import br.api.Textil.Usuario.repository.RoleRepository;
import br.api.Textil.Usuario.repository.UserRepository;
import br.api.Textil.Usuario.security.jwt.JwtUtils;
import br.api.Textil.Usuario.security.services.UserDetailsImpl;
import net.bytebuddy.asm.Advice;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.Character.isUpperCase;
import static org.apache.coyote.http11.Constants.Z;
import static org.apache.coyote.http11.Constants.a;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return ResponseEntity.ok(new JwtResponse(jwt,
                refreshToken.getToken(),
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        String caracterEspecial = "?!@#$%&*<>:;°|'()-_+=§";
        String numero = "1234567890";
        String senha = signUpRequest.getPassword().trim();
        boolean temCaracterEspecial = false;
        boolean temNumero = false;
        boolean temLetraMaiuscula = false;

        for (int i = 0; i < senha.length(); i++) {
            char letra=senha.charAt(i);
            for (int j = 0; j < caracterEspecial.length(); j++) {
                char letracaracterEspecial=caracterEspecial.charAt(j);
                if (letra==letracaracterEspecial){
                    temCaracterEspecial = true;
                    break;
                }
            }
        }

        for (int i = 0; i < senha.length(); i++) {
            char letra=senha.charAt(i);
            for (int j = 0; j < numero.length(); j++) {
                char charNumero=numero.charAt(j);
                if (letra==charNumero){
                    temNumero = true;
                    break;
                }
            }
        }

        for (int i = 0; i < senha.length(); i++) {
            char letra=senha.charAt(i);
            if (isUpperCase(letra)){
                temLetraMaiuscula = true;
                break;
            }
        }

        if (!temCaracterEspecial || !temNumero || !temLetraMaiuscula){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Password deve conter um caracter especial, uma letra maiúscula e um número!"));
        }

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                EnumStatus.Ativo);

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        List<Role> all = roleRepository.findAll();

        if (all.isEmpty()) {
            Role role = new Role();
            role.setName(ERole.ROLE_ADMIN);// 1
            roleRepository.save(role);
            Role role2 = new Role();
            role2.setName(ERole.ROLE_USER);// 2
            roleRepository.save(role2);
            Role role3 = new Role();
            role3.setName(ERole.ROLE_MODERATOR);// 3
            roleRepository.save(role3);
        }


        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }
}
