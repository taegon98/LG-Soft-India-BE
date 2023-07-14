package back.server.api.controller;

import back.server.api.dto.data.DataRequestDto;
import back.server.api.dto.data.RaspRequestDto;
import back.server.api.dto.data.DataResponseDto;
import back.server.domain.DataSet;
import back.server.domain.Member;
import back.server.exception.MethodArgumentNotValidException;
import back.server.repository.MemberRepository;
import back.server.service.InfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/data")
@Slf4j
public class DataApiController {

    private final MemberRepository memberRepository;
    private final InfoService infoService;

    @PostMapping("/save")
    public ResponseEntity saveData(@RequestBody @Valid RaspRequestDto request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException("Field Error");
        }

        infoService.saveInfo(request);
        return ResponseEntity.ok(200);
    }

    @GetMapping("/get")
    public ResponseEntity<DataResponseDto> getData(@RequestBody @Valid DataRequestDto request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException("Field Error");
        }

        Member member = memberRepository.findByEmail(request.getEmail()).get();
        DataSet data = member.getCity().getDataSet();

        DataResponseDto response = DataResponseDto.builder()
                .now(LocalDateTime.now())
                .waterLevel(Double.parseDouble(data.getWaterLevel()))
                .temperature(Double.parseDouble(data.getTemperature()))
                .ph(Double.parseDouble(data.getPh()))
                .turbidity(Double.parseDouble(data.getTurbidity()))
                .build();

        return ResponseEntity.ok(response);
    }
}
