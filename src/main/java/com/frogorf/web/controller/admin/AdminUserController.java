package com.frogorf.web.controller.admin;

import com.frogorf.security.domain.User;
import com.frogorf.security.service.UserService;
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
@SessionAttributes(types = {User.class})
@Controller
@RequestMapping("/admin")
public class AdminUserController {
    private static final Logger logger = LoggerFactory.getLogger(AdminUserController.class);

    @Autowired
    private UserService userService;

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    //    @PreAuthorize("hasRole('CTRL_USER_LIST_GET')")
    @RequestMapping(value = {"/user/list", "/user"}, method = RequestMethod.GET)
    public String listUsers(Model model) {
        return "admin/user/list";
    }

    @RequestMapping("/user/{id}")
    public String detail(@PathVariable("id") int id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "admin/user/detail";
    }

    @RequestMapping(value = "/user/new", method = RequestMethod.GET)
    public String initCreationForm(Model model) {
        User user = new User();
        model.addAttribute(user);
        return "admin/user/edit";
    }

    //    @PreAuthorize("hasRole('CTRL_USER_ADD_POST')")
    @RequestMapping(value = "/user/new", method = RequestMethod.POST)
    public String addUser(@ModelAttribute User user, BindingResult result, SessionStatus status) {
        logger.info("IN: User/add-POST");
        if (result.hasErrors()) {
            return "admin/user/edit";
        } else {
            userService.saveUser(user);
            status.setComplete();
            return "redirect:/admin/user/" + user.getId();
        }
    }

    @RequestMapping(value = "/user/{id}/edit", method = RequestMethod.GET)
    public String editUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "admin/user/edit";
    }


    @RequestMapping(value = "/user/{id}/edit", method = RequestMethod.POST)
    public String processUpdateCatalogNoteForm(User user, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            return "admin/user/edit";
        } else {
            userService.saveUser(user);
            status.setComplete();
            return "redirect:/admin/user/" + user.getId();
        }
    }

    @RequestMapping(value = "/user/{id}/delete")
    public String deleteUser(@PathVariable("id") int id, Model model) {
        userService.deleteUser(id);
        return "redirect:/admin/user/list";
    }
}
