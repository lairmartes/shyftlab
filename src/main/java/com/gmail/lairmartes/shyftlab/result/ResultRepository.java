package com.gmail.lairmartes.shyftlab.result;

import com.gmail.lairmartes.shyftlab.result.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository extends JpaRepository<Result, Long> {
}
