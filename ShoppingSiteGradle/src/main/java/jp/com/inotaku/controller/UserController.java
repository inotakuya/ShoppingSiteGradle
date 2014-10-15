package jp.com.inotaku.controller;

import java.util.Set;

import javax.servlet.http.HttpSession;

import jp.com.inotaku.domain.User;
import jp.com.inotaku.domain.User.userEntry;
import jp.com.inotaku.service.UserService;
import jp.com.inotaku.validation.LoginForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String toLoginView(Model model,HttpSession session) {
		model.addAttribute( new User());
		model.addAttribute("loginUser","login");
		session.invalidate();
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String onSubmit(@Validated(LoginForm.class) User user,
			BindingResult bindingResult, Model model, HttpSession session) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("loginUser","login");
			return "login";
		}

		try {
			User loginUser = userService.getUserByUserNameAndPassword(
					user.getUserName(), user.getPassword());
			session.setAttribute("user", loginUser);
			model.addAttribute("loginUser", loginUser);
		} catch (Exception e) {
			return "login";
		}
		return "loginSuccess";
	}

	@RequestMapping(value = "userEntry", method = RequestMethod.GET)
	public String toUserEntryView(Model model,HttpSession session) {
		model.addAttribute(new User());
		session.invalidate();
		return "userEntry";
	}

	@RequestMapping(value = "userEntry", method = RequestMethod.POST)
	public String addUser(@Validated(userEntry.class) User user,
			BindingResult bindingResult, Model model, HttpSession session) {
		if (bindingResult.hasErrors()) {
			return "userEntry";
		}
		userService.addUser(user);
		session.setAttribute("user", user);
		model.addAttribute("loginUser",user);
		return "userEntrySuccess";
	}
	
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(Model model,HttpSession session){
		session.invalidate();
		return "redirect:login";
	}
}
