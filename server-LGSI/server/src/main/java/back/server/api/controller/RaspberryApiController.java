package back.server.api.controller;

import back.server.api.dto.raspberry.WaterLevelRequestDto;
import back.server.domain.WaterLevel;
import back.server.service.InfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/raspberry")
@Slf4j
public class RaspberryApiController {

    private final InfoService infoService;

    @PostMapping("/save")
    public ResponseEntity save(@RequestBody @Valid WaterLevelRequestDto request) {
        infoService.saveInfo(request);
        return ResponseEntity.ok(200);
    }

    @PostMapping("/get")
    public WaterLevel getValue() {
        return infoService.getInfo(1L);
    }
}
