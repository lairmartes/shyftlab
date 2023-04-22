package com.gmail.lairmartes.shyftlab.result.service;

import com.gmail.lairmartes.shyftlab.result.domain.Result;
import jakarta.validation.Valid;
import lombok.NonNull;

public interface ResultService {
    Result addResult(@NonNull @Valid Result result);
}
