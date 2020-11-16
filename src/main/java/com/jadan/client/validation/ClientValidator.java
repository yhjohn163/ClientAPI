package com.jadan.client.validation;

import com.jadan.client.model.view.ClientRequest;
import org.springframework.stereotype.Component;

import static com.jadan.client.utils.CPFUtils.isValidCpf;
import static com.jadan.client.utils.DateUtils.isValidDate;

@Component
public class ClientValidator extends AbstractValidator<ClientRequest> {

    @Override
    protected void apply(ClientRequest request, Validation validation) {
        String birthdate = request.getBirthdate();

        boolean validDate = isValidDate(birthdate);
        validation.setInvalid(!validDate).setError("birthdate", "client.birthdate.invalid");

        boolean validCpf = isValidCpf(request.getCPF());
        validation.setInvalid(!validCpf).setError("CPF", "client.cpf.invalid");
    }
}
