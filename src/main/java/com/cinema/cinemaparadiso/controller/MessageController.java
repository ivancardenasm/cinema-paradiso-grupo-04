package com.cinema.cinemaparadiso.controller;

import java.time.Instant;
import java.util.Date;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.cinema.cinemaparadiso.model.Message;
import com.cinema.cinemaparadiso.service.MessageService;
import com.cinema.cinemaparadiso.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/messages")
@Slf4j
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;


    @GetMapping("/listSend")
    public String listSend(Model model){
		String username = ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Iterable<Message> messages = messageService.findByEmisorUsername(username);
        model.addAttribute("messages", messages);
        model.addAttribute("tipo", "send");
        model.addAttribute("urlMsg", "");
        log.info("Listing Messages..."+messages.toString());
        return "messages/listMessage";
    }

    @GetMapping("/check")
    @ResponseBody
    public String check(Model model, @RequestParam String username){
    	String res="false";
		String usernameReal = ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		if(usernameReal.equals(username)) {
			if(messageService.checkMessage(username))
				res="true";
		}
		return res;
    }

    @GetMapping("/listReceived")
    public String listReceived(Model model){
		String username = ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Iterable<Message> messages = messageService.findByReceptorUsername(username);
        model.addAttribute("messages", messages);
        model.addAttribute("tipo", "received");
        model.addAttribute("urlMsg", "");
        log.info("Listing Messages..."+messages.toString());
        return "messages/listMessage";
    }

    @GetMapping("/show/{messageId}")
    public String show(Model model, @PathVariable("messageId") Integer messageId){
    	try {
	        Message message = messageService.findById(messageId);
	        
	        String usernameActual = userService.getPrincipal().getUsername();
	        
	        Boolean isRequest = message.getIsRequest()!=null && message.getIsRequest()!=0 && message.getReceptor().getUsername().equals(usernameActual);
	        Boolean isArtist = this.userService.findArtistByUserUsername(message.getEmisor().getUsername()).isPresent();
	        Boolean isProducer = this.userService.findProducerByUserUsername(message.getEmisor().getUsername()).isPresent();
	        Boolean isWriter =this.userService.findWriterByUserUsername(message.getReceptor().getUsername()).isPresent();
	        Boolean messageForMe = message.getEmisor().getUsername().equals(usernameActual) || message.getReceptor().getUsername().equals(usernameActual);
	        if(!messageForMe) {
	        	return "error/error-403";
	        }
	        if(!message.isSeen() && usernameActual.equals(message.getReceptor().getUsername()))
	        	messageService.checkSeen(messageId);
	        model.addAttribute("isRequest",isRequest);
	        model.addAttribute("isWriter",isWriter);
	        model.addAttribute("isArtist",isArtist);
	        model.addAttribute("isProducer",isProducer);
	        model.addAttribute("message", message);
	        log.info("Showing Message..."+message.toString());
    	}catch (NoSuchElementException e) {
	        log.error("Error Showing Message..."+messageId.toString());
		}
        return "messages/showMessage";
    }
    
    @Transactional
    @GetMapping("/show/{messageId}/acceptRequestStory")
    public String showAcceptStory(Model model, @PathVariable("messageId") Integer messageId){
        try {
            messageService.acceptRequestStory(messageId);
        }catch (NoSuchElementException e) {
            log.error("Error Showing Message..."+messageId.toString());
        }
        return "redirect:/messages/listReceived";
    }

    @GetMapping("/show/{messageId}/acceptRequestArtist")
    public String showAcceptArtist(Model model, @PathVariable("messageId") Integer messageId){
        try {
            messageService.acceptRequestArtist(messageId);
        }catch (NoSuchElementException e) {
            log.error("Error Showing Message..."+messageId.toString());
        }
        return "redirect:/messages/listReceived";
    }
    @GetMapping("/show/{messageId}/acceptRequestProducer")
    public String showAcceptProducer(Model model, @PathVariable("messageId") Integer messageId){
    	try {
	        messageService.acceptRequestProducer(messageId);
    	}catch (NoSuchElementException e) {
	        log.error("Error Showing Message..."+messageId.toString());
		}
        return "redirect:/messages/listReceived";
    }
    @Transactional
    @GetMapping("/show/{messageId}/rejectRequestStory")
    public String showRejectStory(Model model, @PathVariable("messageId") Integer messageId){
         try {
             messageService.rejectRequestStory(messageId);
         }catch (NoSuchElementException e) {
             log.error("Error Showing Message..."+messageId.toString());
         }
         return "redirect:/messages/listReceived";
     }

    @Transactional 
    @GetMapping("/show/{messageId}/rejectRequestArtist")
    public String showRejectArtist(Model model, @PathVariable("messageId") Integer messageId){
        try {
            messageService.rejectRequestArtist(messageId);
        }catch (NoSuchElementException e) {
            log.error("Error Showing Message..."+messageId.toString());
        }
        return "redirect:/messages/listReceived";
    }
    
    @GetMapping("/show/{messageId}/rejectRequestProducer")
    public String showRejectProducer(Model model, @PathVariable("messageId") Integer messageId){
    	try {
	        messageService.rejectRequestProducer(messageId);
    	}catch (NoSuchElementException e) {
	        log.error("Error Showing Message..."+messageId.toString());
		}
        return "redirect:/messages/listReceived";
    }

    @GetMapping("/create/{userName}")
    public String initFormCreateMessage(Model model, @PathVariable("userName") String userName){
    	try {
    		Message message = new Message();
            model.addAttribute("message", message);
            model.addAttribute("userName", userName);
    		model.addAttribute("Estado", "Exito");
    	}catch (IllegalArgumentException e) {
    		model.addAttribute("Estado", "Error al iniciar la entidad");
		}
        log.info("Initializing Messages to..."+userName);
        return "messages/createMessageForm";
    }

    @PostMapping("/create/{userName}")
    public String create(@PathVariable("userName") String userName, @Validated @ModelAttribute("message") Message message, BindingResult result, Model model){
		if(!result.hasErrors()) {
    		String emisor_username = SecurityContextHolder.getContext().getAuthentication().getName();
    		message.setEmisor(userService.getUserByUsername(emisor_username));
    		message.setReceptor(userService.getUserByUsername(userName));
    		message.setMessageDate(Date.from(Instant.now()));
    		messageService.create(message);
    		model.addAttribute("Estado", "Exito");
            return "redirect:/messages/listSend";
    	}else {
    		model.addAttribute("Estado", "Error, entidad incorrecta");
    		model.addAttribute(message);
            return "messages/createMessageForm";
		}
    }

    @GetMapping("/delete/{messageId}")
    public String initDelete(Model model, @PathVariable("messageId") Integer messageId){

    	try {
    		Message message = messageService.findById(messageId);
    		String username = userService.getPrincipal().getUsername();
    		if(message.getEmisor().getUsername().equals(username) || message.getReceptor().getUsername().equals(username)) {
        		messageService.delete(message);
        		model.addAttribute("Estado", "Exito");
                log.info("Deleting Messages..."+message.toString());
    		}else {
    			model.addAttribute("Error", "No tienes relacion con esta entidad");
    			return "error/error-403";
    		}

    	}catch (NoSuchElementException e) {
    		model.addAttribute("Estado", "Error, identificador incorrecto");
            log.error("Error Deleting Message..."+messageId);
		}
        return "redirect:/messages/listReceived";
    }
}