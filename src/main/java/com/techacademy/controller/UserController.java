package com.techacademy.controller;

import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.techacademy.entity.User;
import com.techacademy.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {
    private final UserService service;
    public UserController(UserService service) {
        this.service = service;
    }

    /**一覧画面表示*/
    @GetMapping("/list")
    public String getList(Model model) {
        model.addAttribute("userlist", service.getUserList());
        return "user/list";
    }
    /**User登録画面を表示*/
    @GetMapping("/register")
    public String getRegister(@ModelAttribute User user) {
        return "user/register";
    }
    /**登録処理*/
    @PostMapping("/register")
    public String postRegister(@Validated User user, BindingResult res, Model model) {
        if(res.hasErrors()) {
            return getRegister(user);
        }
        service.saveUser(user);
        return "redirect:/user/list";
    }
    /**User更新画面*/
    @GetMapping("/update/{id}/")
    public String getUser(@PathVariable(name = "id", required = false) Integer id, @ModelAttribute User user, Model model) {
        if(id != null) {
            model.addAttribute("user", service.getUser(id));
        }else {
            model.addAttribute("user", user);
        }
        return "user/update";
    }
    /** User更新処理*/
    @PostMapping("/update/{id}/")
    public String postUser(@Validated @ModelAttribute User user, BindingResult res, Model model) {
        if(res.hasErrors()) {
            return getUser(null, user, model);
        }
        //User登録
        service.saveUser(user);
        return "redirect:/user/list";
    }
    /**User削除処理*/
    @PostMapping(path="list", params="deleteRun")
    public String deleteRun(@RequestParam(name="idck")Set<Integer>idck, Model model) {
        service.deleteUser(idck);
        return "redirect:/user/list";
    }

}
