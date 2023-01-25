package com.nttdata.ct.web.service.datamanage;

import com.nttdata.ct.web.datasession.SessionData;
import net.minidev.json.JSONArray;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateSessionData {

    private ValidateSessionData() {
        throw new IllegalStateException("Utility class");
    }

    private static final String PATTERN_REGEX_VARIABLE = "\\{(\\{[^{}]+})}";
    private static final String PATTERN_REGEX_DDT_VARIABLE = "@\\{(\\{[^{}]+})}";

    public static boolean isParameterFormat(String valor) {
        if (valor.matches("\\{([^{}]+})}")
                || valor.matches("([^{}]+})}")
                || valor.matches("\\{(\\{[^{}]+})")
                || valor.matches("(\\{[^{}]+})")) {
            throw new IllegalArgumentException("Error en el formato {{value}}");
        } else return valor.matches(PATTERN_REGEX_VARIABLE);
    }

    public static boolean isDDtParameterFormat(String valor) {
        return valor.matches(PATTERN_REGEX_DDT_VARIABLE);
    }

    public static String getValueIntoDDTFormat(String valor) {
        Pattern p = Pattern.compile(PATTERN_REGEX_DDT_VARIABLE);
        Matcher m = p.matcher(valor);
        if (m.find())
            valor = (String) m.group().subSequence(3, m.group().length() - 2);
        return valor;
    }

    public static Map<String, Object> findSessionVariables(String valor) {
        Map<String, Object> variableFoundMap = new HashMap<>();
        if (isParameterFormat(valor)) {
            Pattern p = Pattern.compile(PATTERN_REGEX_VARIABLE);
            Matcher m = p.matcher(valor);
            if (m.find()) {
                String variableKey = (String) m.group().subSequence(2, m.group().length() - 2);
                Object valueSessionVariable = SessionData.getVariableCalled(variableKey);

                if (valueSessionVariable instanceof JSONArray) {
                    for (Object valueInJSONArray : (JSONArray) valueSessionVariable) {
                        variableFoundMap.put(variableKey, valueInJSONArray);
                    }
                } else {
                    variableFoundMap.put(variableKey, valueSessionVariable);
                }
            }
        }
        return variableFoundMap;
    }
}