package com.jadan.client.utils;

import br.com.caelum.stella.format.CPFFormatter;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.Validator;

public class CPFUtils {

    public static String removeFormat(String cpf) {
        CPFFormatter formatter = new CPFFormatter();
        if(formatter.isFormatted(cpf)){
            return formatter.unformat(cpf);
        }
        return cpf;
    }


    public static boolean isValidCpf(String cpf) {
        try {
            Validator<String> cpfValidator = new CPFValidator();
            cpfValidator.assertValid(cpf);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

}

