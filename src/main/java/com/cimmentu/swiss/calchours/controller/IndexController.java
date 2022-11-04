package com.cimmentu.swiss.calchours.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cimmentu.swiss.calchours.model.WorkTypeEnum;
import com.cimmentu.swiss.calchours.model.WorkingDay;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;

@Controller
public class IndexController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model, HttpSession session) {
		List<WorkingDay> workedList = getWorkingHoursFromFile(session);
		model.addAttribute("workTypes",Arrays.asList(WorkTypeEnum.values()));
		model.addAttribute("workedList", workedList);
		model.addAttribute("workingDay",new WorkingDay());
		return "index";
    }
	
	@RequestMapping(value = "/index", method = RequestMethod.POST, params="action=save")
    public String saveWorkingDay(@ModelAttribute WorkingDay workingDay, Model model,HttpSession session) {
		List<WorkingDay> workedList = getWorkingHours(session);
		workedList.add(workingDay);
		session.setAttribute("workedList",workedList);
		model.addAttribute("workTypes",Arrays.asList(WorkTypeEnum.values())); 
		model.addAttribute("workedList", workedList);
		model.addAttribute("workingDay",new WorkingDay());
        return "index";
    }
	
	@RequestMapping(value = "/index", method = RequestMethod.POST, params="action=saveall")
    public String saveAll(@ModelAttribute WorkingDay workingDay, Model model, HttpSession session) {
		List<WorkingDay> workedList = getWorkingHours(session);
		workedList.add(workingDay);
		session.setAttribute("workedList",workedList);
		model.addAttribute("workTypes",Arrays.asList(WorkTypeEnum.values())); 
		model.addAttribute("workedList", workedList);
		model.addAttribute("workingDay",new WorkingDay());
		saveWorkingHours(workedList);
        return "index";
    }
	
	private List<WorkingDay> getWorkingHours(HttpSession session) {
		List<WorkingDay> workedList = new ArrayList<>();
		if(session != null && session.getAttribute("workedList") != null) {
			workedList = (List<WorkingDay>) session.getAttribute("workedList");
		} 
		return workedList;
	}
	
	private void saveWorkingHours(List<WorkingDay> workedList) {
		logger.info(workedList.toString());
		Path filePath = Paths.get("workingHours.json");
		if(!Files.exists(filePath)) {
			try {
				Files.createFile(filePath);
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toString()))) {
			logger.info("starting writing to File Json");
			Gson gson = new Gson();
			String jsonWorkedList = gson.toJson(workedList);
			writer.write(jsonWorkedList);
			writer.flush();
			logger.info(jsonWorkedList);
		} catch (JsonIOException | IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private List<WorkingDay> getWorkingHoursFromFile(HttpSession session) {
		List<WorkingDay> workedList = new ArrayList<>();
		Path filePath = Paths.get("workingHours.json");
		if(Files.exists(filePath)) {
			try (BufferedReader reader = new BufferedReader(new FileReader("workingHours.json"))){
				Gson gson = new Gson();
				workedList = gson.fromJson(reader,new TypeToken<List<WorkingDay>>() {}.getType());
			} catch (JsonIOException | IOException e) {
				e.printStackTrace();
			}
		}
		return workedList;
	}
}
