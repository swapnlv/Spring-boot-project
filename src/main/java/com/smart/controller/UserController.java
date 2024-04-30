package com.smart.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;


import org.aspectj.apache.bcel.util.ClassPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.smart.Helper.Message;
import com.smart.config.UserDetailServiceImpl;
import com.smart.model.Contact;
import com.smart.model.User;
import com.smart.repository.ContactRepo;
import com.smart.repository.UserRepo;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserRepo userRepo;
	@Autowired
	ContactRepo contactRepo;
	
	
	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		String userName=principal.getName();
		
		User user=userRepo.getUserByUsername(userName);
		
		model.addAttribute("user", user);
	}
	
	@RequestMapping("/index")
	public String dashboard(Model model, Principal principal) {
		model.addAttribute("title","User Dashboard");
		return "normal/user_dashboard";
	}
	
	@RequestMapping("/add-contact")
	public String addContact(Model model, Principal principal){
		
		model.addAttribute("title", "Add-Contact");
		
		model.addAttribute("contact", new Contact());
		
		return "normal/add-contact";
		
	}
	@RequestMapping("/process-contact")
	public String processContact(@Valid @ModelAttribute("contact") Contact contact, BindingResult result,  Principal principal, @RequestParam("profileImage") MultipartFile file ,Model model, HttpSession session){
		
		
		try{
		String name =principal.getName();
		
		User user= this.userRepo.getUserByUsername(name);
		
//		model.addAttribute("title", "Add-Contact");
		
//		model.addAttribute("contact", new Contact());
		
		if(file.isEmpty()) {
			System.out.println("No File Uploaded");
			throw new Exception("Image has not been selected !!");					
		}else {
			
			if(result.hasErrors()) {
				System.out.println("Error : "+result.toString());
				model.addAttribute(contact);
				return "normal/add-contact";
			}
			
			contact.setImage(file.getOriginalFilename());
			
			File saveDirectory = new ClassPathResource("/static/image/").getFile();
			
			File saveFile = new File(saveDirectory, file.getOriginalFilename());
			
			Path path = Paths.get(saveDirectory.getAbsolutePath() + File.separator + file.getOriginalFilename());

			
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			
			System.out.println(path);
			
		}
		
		contact.setUser(user);		
		user.getContacts().add(contact);
		this.userRepo.save(user);
		
		System.out.println("Data "+ contact);
		
		session.setAttribute("message", new Message("Contact has been Added !!! ","alert-success"));
		}catch(Exception e) {
			e.printStackTrace();
			session.setAttribute("message", new Message("Something went wrong !!"+ e.getMessage(), "alert-danger"));
			
			}

		return "normal/add-contact";
		
	}
	@GetMapping(value="/view-contacts/{page}",produces = "application/json")
	public String showContacts(Model model, Principal principal,@PathVariable("page") int page ) {
		
		model.addAttribute("title", "View-Contacts");
		String name=principal.getName();	
		User user=userRepo.getUserByUsername(name);
		Pageable pageable= PageRequest.of(page, 5);
		
		Page<Contact> contacts=contactRepo.getContactsbyID(user.getU_id(), pageable);
		
		 contacts.forEach(contact -> {
		        String imageName = contact.getImage();
		        String imagePath = "/static/image/" + imageName;
		        File file=null;
				try {
					file = new ClassPathResource(imagePath).getFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        
		        if (file==null) {
		            contact.setImage("default.png"); // Set default image name
		        }
		    });
		 
		model.addAttribute("contacts", contacts);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", contacts.getTotalPages());
		
		
		return "normal/view-contacts";
		
	}
	@GetMapping("/{cId}/contact-detail")
	public String contactDetail(@PathVariable int cId, Model model){
		
		
		
		Contact contact=contactRepo.getById(cId);
		String imageName = contact.getImage();
        String imagePath = "/static/image/" + imageName;
        File file=null;
		try {
			file = new ClassPathResource(imagePath).getFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        if (file==null) {
            contact.setImage("default.png"); // Set default image name
        }
		model.addAttribute("contact",contact);
		return "normal/contact-detail";
		
	}
	@GetMapping("/{cId}/delete-contact")
	public String deleteDetail(@PathVariable int cId, Model model, HttpSession session) {
		
		contactRepo.deleteById(cId);
		Message message=new Message("Contact has been Deleted !!! ","alert-danger");
		session.setAttribute("session", message);
		return "redirect:/user/view-contacts/0";
	}
	@GetMapping("/{cId}/update-contact")
	public String updateeDetail(@PathVariable int cId, Model model) {
		model.addAttribute("title","SmartContactManager - Update");
		model.addAttribute("id",cId);
		Contact contact1=contactRepo.getById(cId);
		model.addAttribute("contact", contact1);
		
		return "normal/update-contact";
	}
	
	@PostMapping("/{cId}/process-update-contact")
	public String processUpdateContact(@PathVariable int cId,  Principal principal, @RequestParam("profileImage") MultipartFile file ,Model model, HttpSession session) {
			try {
			Contact contact=this.contactRepo.getById(cId);	
			System.out.println("Data before"+ contact.toString());
			model.addAttribute(contact);
			
//			model.addAttribute("title", "Add-Contact");
			
//			model.addAttribute("contact", new Contact());
			
			if(file.isEmpty()) {
				File saveDirectory = new ClassPathResource("/static/image/").getFile();
				
				File saveFile = new File(saveDirectory, file.getOriginalFilename());
				
				Path path = Paths.get(saveDirectory.getAbsolutePath() + File.separator + file.getOriginalFilename());

				
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);					
			}else {
					
					
					contact.setImage(file.getOriginalFilename());
					
				}
				
			
				
			String name =principal.getName();
			
			User user= this.userRepo.getUserByUsername(name);
			
			contact.setUser(user);		
			this.contactRepo.save(contact);
			System.out.println("Data "+ contact.toString());
			session.setAttribute("message", new Message("Contact has been Updated !!! ","alert-success"));
			}catch(Exception e) {
							
						}
						
			

			
		
		return "normal/update-contact";
	}


}                          