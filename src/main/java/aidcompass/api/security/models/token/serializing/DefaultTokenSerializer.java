//package aidcompass.api.security.models.token.serializing;
//
//import aidcompass.api.security.models.token.models.Token;
//import aidcompass.api.security.core.interfaces.TokenSerializer;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import lombok.Data;
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.security.Key;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//@Data
//@RequiredArgsConstructor
//public class DefaultTokenSerializer implements TokenSerializer {
//
//    private final String SECRET;
//
//    private final Logger logger = LoggerFactory.getLogger(DefaultTokenSerializer.class);
//
//
//    @Override
//    public String serialize(Token token) {
//
//        logger.info("Serializing token");
//
//        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
//
//        Map<String, Object> header = new HashMap<>();
//        header.put("alg", signatureAlgorithm.getValue());
//        header.put("typ", "JWT");
//
//        Claims claims = Jwts.claims()
//                .setId(token.getId().toString())
//                .setSubject(token.getSubject())
//                .setIssuedAt(Date.from(token.getIssuedAt()))
//                .setExpiration(Date.from(token.getExpiresAt()));
//        claims.put("authorities", token.getAuthorities());
//
//        Key key = Keys.hmacShaKeyFor(SECRET.getBytes());
//
//        logger.info("Serializing token ended");
//
//        return Jwts
//                .builder()
//                .setHeader(header)
//                .setClaims(claims)
//                .signWith(key, signatureAlgorithm)
//                .compact();
//    }
//
//}
