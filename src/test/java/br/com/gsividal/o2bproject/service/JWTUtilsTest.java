package br.com.gsividal.o2bproject.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class JWTUtilsTest {

    @Test
    public void createAndDecodeJWT() {

        String jwtId = "SOMEID1234";
        String jwtIssuer = "JWT Demo";
        String jwtSubject = "Andrew";
        int jwtTimeToLive = 800000;

        String jwt = JWTUtils.createJWT(
                jwtId, // claim = jti
                jwtIssuer, // claim = iss
                jwtSubject, // claim = sub
                jwtTimeToLive // used to calculate expiration (claim = exp)
        );

        Claims claims = JWTUtils.decodeJWT(jwt);

        assertEquals(jwtId, claims.getId());
        assertEquals(jwtIssuer, claims.getIssuer());
        assertEquals(jwtSubject, claims.getSubject());

    }

    @Test
    public void decodeShouldFail() {
        String notAJwt = "This is not a JWT";
        Assertions.assertThrows(MalformedJwtException.class, () -> JWTUtils.decodeJWT(notAJwt));
    }

    @Test
    public void createAndDecodeTamperedJWT() {

        String jwtId = "SOMEID1234";
        String jwtIssuer = "JWT Demo";
        String jwtSubject = "Guisividal";
        int jwtTimeToLive = 800000;

        String jwt = JWTUtils.createJWT(
                jwtId, // claim = jti
                jwtIssuer, // claim = iss
                jwtSubject, // claim = sub
                jwtTimeToLive // used to calculate expiration (claim = exp)
        );

        StringBuilder tamperedJwt = new StringBuilder(jwt);
        tamperedJwt.setCharAt(22, 'I');

        Assertions.assertNotEquals(jwt, tamperedJwt.toString());

        Assertions.assertThrows(SignatureException.class, () -> JWTUtils.decodeJWT(tamperedJwt.toString()));


    }

}