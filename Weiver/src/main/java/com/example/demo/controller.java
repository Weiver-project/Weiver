package com.example.demo;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


public class controller {

	public static void main(String[] args) {
		
	}

	@RequestMapping(value = "/posting", method = RequestMethod.GET)
	public String main(Model model, HttpSession session) {

		
		return "posting";
	}
}
