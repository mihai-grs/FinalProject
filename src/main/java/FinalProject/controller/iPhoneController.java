package FinalProject.controller;

import FinalProject.model.iPhoneCreateDto;
import FinalProject.model.iPhoneReturnDto;
import FinalProject.model.iPhoneUpdateDto;
import FinalProject.service.iPhoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class iPhoneController {

    private final iPhoneService iphoneService;


    @GetMapping("/iphones")
    public List<iPhoneReturnDto> getAlliPhones(Pageable pageable) {
        return iphoneService.getAlliPhones(pageable);
    }


    @GetMapping("/iphones/byName")
    public iPhoneReturnDto getByModelName(@RequestParam String modelName) {
        return iphoneService.getByModelName(modelName);
    }


    @GetMapping("/iphones/{id}")
    public iPhoneReturnDto getiPhoneById(@PathVariable Long id) {
        return iphoneService.getiPhoneById(id);
    }


    @PostMapping("/iphones")
    public ResponseEntity<iPhoneReturnDto> createiPhone(@RequestBody @Validated iPhoneCreateDto iphoneCreateDto) {
        iPhoneReturnDto creatediPhone = iphoneService.mapToReturnDto(iphoneService.createiPhone(iphoneCreateDto));
        return new ResponseEntity<>(creatediPhone, HttpStatus.CREATED);
    }


    @PutMapping("/iphones/{id}")
    public ResponseEntity<iPhoneReturnDto> updateiPhone(@PathVariable Long id,
                                                        @RequestBody @Validated iPhoneUpdateDto iphoneUpdateDto) {
        iPhoneReturnDto updatediPhone = iphoneService.updateiPhone(id, iphoneUpdateDto);
        return ResponseEntity.ok(updatediPhone);
    }


    @DeleteMapping("/iphones/{id}")
    public ResponseEntity<Void> deleteiPhone(@PathVariable Long id) {
        iphoneService.deleteiPhone(id);
        return ResponseEntity.noContent().build();
    }
}
