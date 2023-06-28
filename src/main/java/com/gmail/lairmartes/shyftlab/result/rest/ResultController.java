package com.gmail.lairmartes.shyftlab.result.rest;

import com.gmail.lairmartes.shyftlab.result.rest.dto.ResultDTO;
import com.gmail.lairmartes.shyftlab.result.service.ResultService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/intrado/api/results")
@AllArgsConstructor
public class ResultController {

    private final ResultService resultService;

    @PostMapping("/")
    public ResultDTO addResult(@RequestBody ResultDTO newResult) {
        return ResultDTO.fromDomain(resultService.addResult(newResult.toDomain()));
    }

    @GetMapping("/all")
    public List<ResultDTO> listAllResults() {
        return resultService.listAllResults().stream().map(ResultDTO::fromDomain).toList();
    }
}
