package com.nrapendra.account.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class AccountException extends RuntimeException  {

    public AccountException(ErrorMessages errorMessages){
        super(errorMessages.name());
    }
}
