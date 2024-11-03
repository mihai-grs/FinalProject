package FinalProject.controller;


import FinalProject.model.iPhone;
import FinalProject.model.iPhoneCreateDto;
import FinalProject.model.iPhoneUpdateDto;
import FinalProject.repository.iPhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import FinalProject.service.iPhoneService;
import FinalProject.model.iPhoneReturnDto;

import java.util.List;


@Controller
public class iPhoneMvcController {

    @Autowired
    private iPhoneService iPhoneService;


    @GetMapping("/iphones/view")
    public String getAlliPhones(Model model,@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
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

    @GetMapping("/iphones/create")
    @PreAuthorize("hasRole('ADMIN')")
    public String showCreatePhoneForm(Model model) {
        model.addAttribute("iphone", new iPhoneCreateDto());
        return "create";
    }

    @PostMapping("/iphones/create")
    @PreAuthorize("hasRole('ADMIN')")
    public String createPhone(@ModelAttribute iPhoneCreateDto iphoneCreateDto) {
        iPhoneService.createiPhone(iphoneCreateDto);
        return "redirect:/iphones/view";
    }

    @GetMapping("/iphones/detail/{id}")
    public String getiPhoneDetails(@PathVariable Long id, Model model) {
        iPhoneReturnDto iphone = iPhoneService.getiPhoneById(id);
        if (iphone != null) {
            model.addAttribute("iphone", iphone);
            Long nextId = id +1;
            if (!iPhoneService.existsById(nextId)) {
                nextId = iPhoneService.getFirstiPhoneId();
            }
            model.addAttribute("nextId", nextId);
            return "iphone-details";
        } else {
            return "redirect:/iphones/view";
        }
    }

    @GetMapping("/search")
    public String search(@RequestParam("query") String query, Model model) {
        List<iPhone> results = iPhoneService.searchiPhones(query);
        model.addAttribute("iphoneList", results);
        model.addAttribute("totalItems", results.size());
        model.addAttribute("currentPage", 0);
        model.addAttribute("totalPages", 1);

        return "iphone";
    }

}