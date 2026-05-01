package com.github.diogenesssantos.facilittecnologia.util;

import java.time.Instant;

/**
 * @author Dioge
 * classe criada para a regra de negócio.
 */
public class ValidaHoraUtil {

    public static boolean isPassado(Instant data) {
        return data.isBefore(Instant.now());
    }

}
