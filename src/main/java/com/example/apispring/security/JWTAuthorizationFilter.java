package com.example.apispring.security;

import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.apispring.utils.CONSTATNS.SECRET_KEY_TOKEN;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer ";

    /**
     * Filtro para solicitar validacion para token
     *
     * @param request peticion del cliente
     * @param response respuesta para el cliente
     * @param chain
     * @throws "ServletException"
     * @throws "IOException"
     */
    @Override
    protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws ServletException, IOException{
        try {
                if (! existeJWTToken(request)){
                SecurityContextHolder.clearContext();
            }
            else {
                Claims claims = validateToken(request);
                if (claims.get("authorities") == null) {
                    SecurityContextHolder.clearContext();
                }
                else {
                    setUpSpringAuthenticatiob(claims);
                }
            }
            chain.doFilter(request,response);
        }
        catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        }
    }


    /**
     * metodo para validar token
     *
     * @param request peticion del cliente
     * @return si el token es valido, retorna los roles del usuario autenticado
     */
    private Claims validateToken(HttpServletRequest request){
        String jwtToken = request.getHeader(HEADER)
                .replace(PREFIX, "");
        return Jwts.parser()
                .setSigningKey(SECRET_KEY_TOKEN.getBytes())
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    /**
     * Funcion para autenticarnos dentro del flujo de spring
     *
     * @param claims roles del usuario autenticado por token
     */
    private void setUpSpringAuthenticatiob(Claims claims){
        @SuppressWarnings("unchecked")
        List<String> authorities = (List) claims.get("authorities");

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                claims.getSubject(),
                null,
                authorities.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()));
        SecurityContextHolder.getContext()
                .setAuthentication(auth);
    }


    /**
     * Verifica que exista la cabecera "Authorization" y cuyo valor comience con "Bearer"
     *
     * @param request peticion del cliente
     * @return boolean que indica si la peticion tenia un token
     */
    private boolean existeJWTToken(HttpServletRequest request) {
        String authenticationHeader = request.getHeader(HEADER);
        return authenticationHeader != null && authenticationHeader.startsWith(PREFIX);
    }
}
