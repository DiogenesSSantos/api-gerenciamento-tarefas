package com.github.diogenesssantos.facilittecnologia.util;

import com.github.diogenesssantos.facilittecnologia.exception.CampoInvalidoException;

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

    public static void futuroOuThrows(LocalDateTime dataLimite) {
        if (!dataLimite.isAfter(LocalDateTime.now())) throw new CampoInvalidoException(
                "O campo dataLimite deve ser em um periodo no tempo futuro.", "dataLimite");
    }

}
