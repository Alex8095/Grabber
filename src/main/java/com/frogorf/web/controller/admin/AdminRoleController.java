package com.frogorf.web.controller.admin;

import com.frogorf.security.domain.Role;
import com.frogorf.security.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

/**
 * Created by Alex on 06.11.14.
 */
@SessionAttributes(types = {Role.class})
@Controller
@RequestMapping("/admin")
public class AdminRoleController {
    private static final Logger logger = LoggerFactory.getLogger(AdminRoleController.class);

    @Autowired
    private RoleService roleService;

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    //    @PreAuthorize("hasRole('CTRL_USER_LIST_GET')")
    @RequestMapping(value = {"/role/list", "/role"}, method = RequestMethod.GET)
    public String listRoles(Model model) {
        return "admin/role/list";
    }

    @RequestMapping("/role/{id}")
    public String detail(@PathVariable("id") int id, Model model) {
        Role role = roleService.getRole(id);
        model.addAttribute("role", role);
        return "admin/role/detail";
    }

    @RequestMapping(value = "/role/new", method = RequestMethod.GET)
    public String initCreationForm(Model model) {
        Role role = new Role();
        model.addAttribute(role);
        return "admin/role/edit";
    }

    //    @PreAuthorize("hasRole('CTRL_USER_ADD_POST')")
    @RequestMapping(value = "/role/new", method = RequestMethod.POST)
    public String addUser(@ModelAttribute Role role, BindingResult result, SessionStatus status) {
        logger.info("IN: Role/add-POST");
        if (result.hasErrors()) {
            return "admin/role/edit";
        } else {
            roleService.saveRole(role);
            status.setComplete();
            return "redirect:/admin/role/" + role.getId();
        }
    }

    @RequestMapping(value = "/role/{id}/edit", method = RequestMethod.GET)
    public String editUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("role", roleService.getRole(id));
        return "admin/role/edit";
    }


    @RequestMapping(value = "/role/{id}/edit", method = RequestMethod.POST)
    public String processUpdateCatalogNoteForm(Role role, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            return "admin/role/edit";
        } else {
            roleService.saveRole(role);
            status.setComplete();
            return "redirect:/admin/role/" + role.getId();
        }
    }

    @RequestMapping(value = "/role/{id}/delete")
    public String deleteUser(@PathVariable("id") int id, Model model) {
        roleService.deleteRole(id);
        return "redirect:/admin/role/list";
    }
}
