package FinalProject.service;

import FinalProject.model.iPhone;
import FinalProject.model.iPhoneCreateDto;
import FinalProject.model.iPhoneReturnDto;
import FinalProject.model.iPhoneUpdateDto;
import FinalProject.repository.iPhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class iPhoneService {

    @Autowired
    private iPhoneRepository iphoneRepository;


    public iPhone createiPhone(iPhoneCreateDto iphoneDto) {
        iPhone iphone = new iPhone();
        iphone.setModelName(iphoneDto.getModelName());
        iphone.setStorage(iphoneDto.getStorage());
        iphone.setColor(iphoneDto.getColor());

        return iphoneRepository.save(iphone);
    }

    public iPhoneReturnDto getiPhoneById(Long id) {
        iPhone iphone = iphoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("iPhone can't be found by id: " + id));

        return mapToReturnDto(iphone);
    }


    public List<iPhoneReturnDto> getAlliPhones(Pageable pageable) {
        return iphoneRepository.findAll(pageable)
                .stream()
                .map(this::mapToReturnDto)
                .collect(Collectors.toList());
    }


    public iPhoneReturnDto updateiPhone(Long id, iPhoneUpdateDto iphoneDetails) {

        iPhone iphone = iphoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("iPhone can't be found by id: " + id));
        iphone.setModelName(iphoneDetails.getModelName());
        iphone.setColor(iphoneDetails.getColor());
        iphone.setStorage(iphoneDetails.getStorage());
        iphone.setReleaseDate(iphoneDetails.getReleaseDate());
        iphone.setPrice(iphoneDetails.getPrice());
        iphone.setStock(iphoneDetails.getStock());
        iphone.setImage(iphoneDetails.getImage());
//        LocalDate releaseDate = LocalDate.parse(iphoneDetails.getReleaseDate().toString(), DateTimeFormatter.ISO_LOCAL_DATE);
//        iphone.setReleaseDate(releaseDate);

        iPhone updatediPhone = iphoneRepository.save(iphone);
        return mapToReturnDto(updatediPhone);
    }


    public void deleteiPhone(Long id) {
        iPhone iphone = iphoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("iPhone can't be found by id: " + id));

        iphoneRepository.delete(iphone);
    }


    public iPhoneReturnDto mapToReturnDto(iPhone iphone) {
        iPhoneReturnDto iPhonedto = new iPhoneReturnDto();
        iPhonedto.setId(iphone.getId());
        iPhonedto.setModelName(iphone.getModelName());
        iPhonedto.setColor(iphone.getColor());
        iPhonedto.setStorage(iphone.getStorage());
        iPhonedto.setReleaseDate(iphone.getReleaseDate());
        iPhonedto.setPrice(iphone.getPrice());
        iPhonedto.setStock(iphone.getStock());
        iPhonedto.setImage(iphone.getImage());
        return iPhonedto;
    }

    public iPhoneReturnDto getByModelName(String modelName) {
        iPhone iphone = iphoneRepository.findByModelName(modelName)
                .orElseThrow(() -> new RuntimeException("iPhone can't be found by model name: " + modelName));
        return mapToReturnDto(iphone);
    }

}
