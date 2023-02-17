package com.example.restaurant.manager.controller;

import com.example.restaurant.manager.domain.Manager;
import com.example.restaurant.manager.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/manager")
public class ManagerController {

    @Autowired
    ManagerService managerService;

    @GetMapping(value = "/login")
    public String loginForm(HttpServletRequest request, Model model){
        String redirectUrl = request.getParameter("redirectURL");
        model.addAttribute("url" ,redirectUrl);
        return "manager/loginForm";
    }

    @PostMapping(value = "/login")
    public String login(@RequestParam(name = "email") String email, @RequestParam(name = "password") String password,
                        @RequestParam(name = "redirectURL",defaultValue = "/") String redirectURL,
                        HttpServletRequest request, Model model){
        Map<String, String> errors = new HashMap<>();
        Manager loginmember = managerService.login(email, password);
        if(loginmember==null){
            errors.put("loginError", "아이디, 비밀번호를 확인하세요.");
            model.addAttribute("errors",errors);
            return "/manager/loginForm";
        }

        //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession(true);
        //세션에 로그인 회원 정보 보관
        session.setAttribute("loginMember", loginmember);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        //세션 삭제.
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 세션 제거
        }
        return "redirect:/";
    }

    @GetMapping(value = "/add")
    public String signup(){
        return "manager/addManagerForm";
    }

    @PostMapping(value = "/add")
    public String registerManager(HttpServletRequest request, Model model){
        Map<String, String> errors = new HashMap<>();

        String email = request.getParameter("email");
        String pwd = request.getParameter("password");
        String pwdconfirm = request.getParameter("passwordcf");
        String name = request.getParameter("name");
        String number = request.getParameter("number");
        String restaurant = request.getParameter("restaurant");
        String address = request.getParameter("address");
        System.out.println(email);

        Manager checkMember = managerService.findByEmail(email);

        if(!StringUtils.hasText(email)){
            errors.put("email", "아이디 이메일은 필수입니다.");
        }
        else if(checkMember!=null){
            errors.put("email","이미 존재하는 아이디 이메일입니다.");
        }
        if(!StringUtils.hasText(pwd)){
            errors.put("password", "비밀번호는 필수입니다.");
        }
        if(!pwd.equals(pwdconfirm)){
            errors.put("passwordConfirm","비밀번호가 일치하지 않습니다.");
        }
        if(!StringUtils.hasText(name)){
            errors.put("name", "이름은 필수입니다.");
        }
        if(!StringUtils.hasText(number)){
            errors.put("number", "사업자등록번호는 필수입니다.");
        }
        if(!StringUtils.hasText(restaurant)){
            errors.put("restaurant", "음식점이름은 필수입니다.");
        }
        if(!StringUtils.hasText(address)){
            errors.put("address", "음식점 주소는 필수입니다.");
        }

        if(!errors.isEmpty()){
            model.addAttribute("errors",errors);
            return "manager/addManagerForm";
        }

        Manager member = new Manager(email, pwd, name, number, restaurant, address);
        if(managerService.add(member)<=0){
            return "manager/addManagerForm";
        }

        return "redirect:/manager/login";
    }
}
