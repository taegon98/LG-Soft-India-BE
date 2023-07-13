package back.server.api.controller;

import back.server.api.dto.raspberry.DataRequestDto;
import back.server.exception.MethodArgumentNotValidException;
import back.server.service.InfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/data")
@Slf4j
public class RaspberryApiController {

    private final InfoService infoService;

    @PostMapping("/save")
    public ResponseEntity save(@RequestBody @Valid DataRequestDto request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException("Field Error");
        }

        infoService.saveInfo(request);
        return ResponseEntity.ok(200);
    }
}
