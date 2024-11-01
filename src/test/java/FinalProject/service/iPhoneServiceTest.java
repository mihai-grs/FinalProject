package FinalProject.service;

import FinalProject.model.iPhone;
import FinalProject.model.iPhoneCreateDto;
import FinalProject.model.iPhoneReturnDto;
import FinalProject.model.iPhoneUpdateDto;
import FinalProject.repository.iPhoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;


import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class iPhoneServiceTest {

    @Autowired
    private iPhoneService iPhoneService;

    @Autowired
    private iPhoneRepository iPhoneRepository;
    private iPhone testiPhone;

    @BeforeEach
    public void setUp() {
        testiPhone = new iPhone();
        testiPhone.setModelName("iPhone 13");
        testiPhone.setColor("Black");
        testiPhone.setStorage(256);
        testiPhone.setPrice(900.00);
        testiPhone = iPhoneRepository.save(testiPhone);
    }

    @Test
    public void testCreateiPhone() {
        iPhoneCreateDto newiPhoneDto = new iPhoneCreateDto();
        newiPhoneDto.setModelName("iPhone 14");
        newiPhoneDto.setColor("Blue");
        newiPhoneDto.setStorage(512);
        iPhoneReturnDto creatediPhone = iPhoneService.createiPhone(newiPhoneDto);
        assertNotNull(creatediPhone);
        assertEquals("iPhone 14", creatediPhone.getModelName());
        assertEquals("Blue", creatediPhone.getColor());
        assertEquals(512, creatediPhone.getStorage());
    }

    @Test
    public void testGetiPhoneById() {
        iPhoneReturnDto retrievediPhone = iPhoneService.getiPhoneById(testiPhone.getId());
        assertNotNull(retrievediPhone);
        assertEquals(testiPhone.getModelName(), retrievediPhone.getModelName());
        assertEquals(testiPhone.getColor(), retrievediPhone.getColor());
        assertEquals(testiPhone.getStorage(), retrievediPhone.getStorage());
    }

    @Test
    public void testGetAlliPhones() {
        List<iPhoneReturnDto> iphones = iPhoneService.getAlliPhones(PageRequest.of(0, 5));
        assertNotNull(iphones);
    }

    @Test
    public void testUpdateiPhone() {
        iPhoneUpdateDto updateDto = new iPhoneUpdateDto();
        updateDto.setModelName("iPhone 13 Pro");
        updateDto.setColor("Green");
        updateDto.setStorage(512);
        iPhoneReturnDto updatediPhone = iPhoneService.updateiPhone(testiPhone.getId(), updateDto);
        assertNotNull(updatediPhone);
        assertEquals("iPhone 13 Pro", updatediPhone.getModelName());
        assertEquals("Green", updatediPhone.getColor());
        assertEquals(512, updatediPhone.getStorage());
    }

    @Test
    public void testDeleteiPhone() {
        iPhoneService.deleteiPhone(testiPhone.getId());
        assertThrows(RuntimeException.class, () -> iPhoneService.getiPhoneById(testiPhone.getId()));
    }
}