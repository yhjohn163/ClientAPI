package com.jadan.client.validation;

import com.jadan.client.model.view.ClientPatchRequest;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import static com.jadan.client.utils.CPFUtils.isValidCpf;
import static com.jadan.client.utils.DateUtils.isValidDate;

@Component
public class ClientPatchValidator extends AbstractValidator<ClientPatchRequest> {


    @Override
    protected void apply(ClientPatchRequest request, Validation validation) {
        if(request.getName() != null){
            validation.setInvalid(Strings.isBlank(request.getName())).setError("name", "client.name.blank");
        }

        if(request.getBirthdate() != null) {
            boolean validDate = isValidDate(request.getBirthdate());
            validation.setInvalid(!validDate).setError("birthdate", "client.birthdate.invalid");
        }

        if(request.getCPF() != null) {
            boolean validCpf = isValidCpf(request.getCPF());
            validation.setInvalid(!validCpf).setError("CPF", "client.cpf.invalid");
        }
    }



}
