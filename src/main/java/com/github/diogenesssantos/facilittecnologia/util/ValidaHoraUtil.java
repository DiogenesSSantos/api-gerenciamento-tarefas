package com.github.diogenesssantos.facilittecnologia.util;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * @author Dioge
 * classe criada para a regra de negócio.
 */
public class ValidaHoraUtil {

    public static boolean isPassado(LocalDateTime data) {
        return data.isBefore(LocalDateTime.now());
    }

}
