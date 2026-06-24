package com.example.geovision.security;

import java.text.Normalizer;

public final class AuthorityUtil {

    private AuthorityUtil() {
    }

    public static String toRoleAuthority(String nombreRol) {
        return "ROLE_" + normalizar(nombreRol);
    }

    public static String toModuloAuthority(String nombreModulo) {
        return "MODULO_" + normalizar(nombreModulo);
    }

    private static String normalizar(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
                .toUpperCase()
                .replaceAll("[^A-Z0-9]+", "_")
                .replaceAll("^_+|_+$", "");
    }
}
