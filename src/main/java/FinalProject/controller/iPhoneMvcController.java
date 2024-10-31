package FinalProject.controller;

import FinalProject.model.iPhoneUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import FinalProject.service.iPhoneService;
import FinalProject.model.iPhoneReturnDto;

@Controller
public class iPhoneMvcController {
    @Autowired
    private iPhoneService iPhoneService;
    @GetMapping("/iphones/view")
    public String getAlliPhones(Model model, @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<iPhoneReturnDto> page = iPhoneService.getAlliPhonesPaged(pageable);
        model.addAttribute("iphoneList", page.getContent());
        model.addAttribute("currentPage", page.getNumber());
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        return "iphone";
    }

    @GetMapping("/iphones/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String showTheUpdateiPhone(@PathVariable Long id, Model model) {
        iPhoneReturnDto iphone = iPhoneService.getiPhoneById(id);
        model.addAttribute("iphone", iphone);
        return "update";
    }


    @PostMapping("/iphones/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updatePhone(@PathVariable Long id, @ModelAttribute iPhoneUpdateDto iphoneUpdateDto) {
        iPhoneService.updateiPhone(id, iphoneUpdateDto);
        return "redirect:/iphones/view";
    }

    @PostMapping("/iphones/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteiPhone(@PathVariable Long id) {
        iPhoneService.deleteiPhone(id);
        return "redirect:/iphones/view";
    }
}