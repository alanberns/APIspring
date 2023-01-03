package com.example.apispring.service;

import com.example.apispring.dto.request.UserRequestDto;
import com.example.apispring.dto.response.UserResponseDto;
import com.example.apispring.entity.User;
import com.example.apispring.exception.NotFoundExceptionHandler;
import com.example.apispring.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.apispring.utils.CONSTATNS.SECRET_KEY_TOKEN;

@Service
public class SessionSerive implements ISessionService{

    private final UserRepository userRepository;

    public SessionSerive(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @Override
    public UserResponseDto login (UserRequestDto user){
        String username = user.getUsername();
        Optional<User> u = userRepository.findByUsernameAndPassword(username,user.getPassword());
        if (! u.isPresent()){
            throw new NotFoundExceptionHandler("usuario o contraseña incorrecta");
        }

        User usuario = u.get();
        List<String> roles = usuario.getRoles()
                .stream()
                .map(rol -> rol.getRol().getText())
                .collect(Collectors.toList());
        String token = getJWTToken(username,roles);

        return new UserResponseDto(username,token);
    }

    /**
     * Genera un token para un usuario especifico, valido por 30 minutos
     *
     * @param username username para login
     * @param roles lista de roles del usuario
     * @return String
     */
    private String getJWTToken(String username, List<String> roles){
        List<GrantedAuthority> grantedAuthorities = roles
                .stream()
                .map(AuthorityUtils::commaSeparatedStringToAuthorityList)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        LocalDateTime expired = LocalDateTime.now()
                .minusMinutes(30);
        Date expiredTime = Date.from(expired.atZone(ZoneId.systemDefault())
                .toInstant());

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities
                                .stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList())
                )
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expiredTime)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY_TOKEN.getBytes())
                .compact();

        return "Bearer " + token;
    }

    /**
     * Decodifica un token para poder obtener los componentes que contiene el mismo
     * @param token tokenJWT
     * @return Claims
     */
    private static Claims decodeJWT (String token){
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY_TOKEN.getBytes())
                .parseClaimsJwt(token)
                .getBody();
        return claims;
    }

    /**
     * Permite obtener el username segun el token indicado
     * @param token tokenJWT
     * @return String username
     */
    public static String getUsername(String token){
        Claims claims = decodeJWT(token);
        return claims.get("sub", String.class);
    }
}
