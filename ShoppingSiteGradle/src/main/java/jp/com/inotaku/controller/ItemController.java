package jp.com.inotaku.controller;

import javax.servlet.http.HttpSession;

import jp.com.inotaku.domain.User;
import jp.com.inotaku.service.ItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;

	@RequestMapping(value = "/item",method = RequestMethod.GET)
	public String index(Model model,HttpSession session){
		model.addAttribute("itemList",itemService.getItemList());
		User loginUser = (User)session.getAttribute("user");
		if(loginUser != null){
			model.addAttribute("loginUser",loginUser);
		}
		return "index";
	}
	
	@RequestMapping(value = "/detail",method = RequestMethod.GET)
	public String detail(long itemId, Model model,HttpSession session){
		model.addAttribute("item",itemService.getItemByItemId(itemId));
		User loginUser = (User)session.getAttribute("user");
		if(loginUser != null){
			model.addAttribute("loginUser",loginUser);
		}
		return "detail";
	}
}
