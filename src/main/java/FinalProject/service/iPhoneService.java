package FinalProject.service;

import FinalProject.exception.iPhoneNotFoundException;
import FinalProject.model.iPhone;
import FinalProject.model.iPhoneCreateDto;
import FinalProject.model.iPhoneReturnDto;
import FinalProject.model.iPhoneUpdateDto;
import FinalProject.repository.iPhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class iPhoneService {

    @Autowired
    private iPhoneRepository iphoneRepository;


    public iPhoneReturnDto createiPhone(iPhoneCreateDto iphoneDto) {
        iPhone iphone = new iPhone();
        iphone.setModelName(iphoneDto.getModelName());
        iphone.setStorage(iphoneDto.getStorage());
        iphone.setColor(iphoneDto.getColor());
        iphone.setReleaseDate(iphoneDto.getReleaseDate());
        iphone.setPrice(iphoneDto.getPrice());
        iphone.setStock(iphoneDto.getStock());
        iphone.setImage(iphoneDto.getImage());

        iPhone savediPhone = iphoneRepository.save(iphone);
        return mapToReturnDto(savediPhone);
    }

    public iPhoneReturnDto getiPhoneById(Long id) {
        iPhone iphone = iphoneRepository.findById(id)
                .orElseThrow(() -> new iPhoneNotFoundException(id));

        return mapToReturnDto(iphone);
    }


    public List<iPhoneReturnDto> getAlliPhones(Pageable pageable) {
        return iphoneRepository.findAll(pageable)
                .stream()
                .map(this::mapToReturnDto)
                .collect(Collectors.toList());
    }

    public Page<iPhoneReturnDto> getAlliPhonesPaged(Pageable pageable) {
        Page<iPhone> phonesPage = iphoneRepository.findAll(pageable);
        return phonesPage.map(this::mapToReturnDto);
    }

    public List<iPhoneReturnDto> getAllIPhonesSortedById() {
        List<iPhone> iphones = iphoneRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        return iphones.stream()
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

        iPhone updatediPhone = iphoneRepository.save(iphone);
        return mapToReturnDto(updatediPhone);
    }


    public void deleteiPhone(Long id) {
        iPhone iphone = iphoneRepository.findById(id)
                .orElseThrow(() -> new iPhoneNotFoundException(id));

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

    public boolean existsById(Long id) {
        return iphoneRepository.existsById(id);
    }
    public Long getFirstiPhoneId() {
        return iphoneRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream()
                .findFirst()
                .map(iPhone::getId)
                .orElseThrow(() -> new RuntimeException("No products available."));
    }

    public List<iPhone> searchiPhones(String query) {
        return iphoneRepository.findByModelNameContainingIgnoreCase(query);
    }

}
