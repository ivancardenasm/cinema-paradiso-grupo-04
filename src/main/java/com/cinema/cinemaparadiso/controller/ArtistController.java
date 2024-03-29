package com.cinema.cinemaparadiso.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cinema.cinemaparadiso.model.Artist;
import com.cinema.cinemaparadiso.model.Project;
import com.cinema.cinemaparadiso.model.Role;
import com.cinema.cinemaparadiso.model.User;
import com.cinema.cinemaparadiso.service.ArtistService;
import com.cinema.cinemaparadiso.service.UserService;
import com.cinema.cinemaparadiso.service.exceptions.UserUniqueException;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/artists")
@Slf4j
public class ArtistController {

	@Autowired
	private ArtistService artistService;

	@Autowired
	private UserService userService;

	@GetMapping("/list")
	public String list(Model model) {
		List<Role> roles = Arrays.asList(Role.values());
		Artist artistsFiltered = new Artist();
		Iterable<Artist> artists = artistService.list();
		
		model.addAttribute("artists", artists);
		model.addAttribute("artistsPro", artistService.listProArtist());
		model.addAttribute("artistsNoPro", artistService.listNoProArtist());
		model.addAttribute("artistsDisabled", artistService.listDisabledArtist());
		model.addAttribute("roles", roles);
		model.addAttribute("artistsFiltered", artistsFiltered);
		
		log.info("Listing Artists..." + artists.toString());
		return "artists/listArtist";
	}

	@GetMapping("/desactivarArtist/{artistId}")
	public String desactivarArtist(@PathVariable("artistId") Integer artistId,Model model) {
           model.addAttribute("artistId",artistId);
		return "desactivar/desactivarArtist";
	}
	
	@PostMapping("/list")
	public String list(@ModelAttribute("artistsFiltered") Artist artistsFiltered,Model model) {
		List<Role> roles = Arrays.asList(Role.values());
		List<Artist> artists = artistService.list();
		
		model.addAttribute("artists", artists);
		model.addAttribute("artistsFiltered", artistsFiltered);

		List<Artist> artistasProFiltrados = artists.stream()
				.filter(a -> a.getPro() &&		
							a.getUser().getUsername().toLowerCase().contains(artistsFiltered.getUser().getUsername().toLowerCase()) && 
							(!roles.contains(artistsFiltered.getRole()) || a.getRole().equals(artistsFiltered.getRole()))
				).collect(Collectors.toList());
		
		List<Artist> artistasNoProFiltrados = artists.stream()
				.filter(a -> !a.getPro() &&
							a.getUser().getUsername().toLowerCase().contains(artistsFiltered.getUser().getUsername().toLowerCase()) && 
							(!roles.contains(artistsFiltered.getRole()) || a.getRole().equals(artistsFiltered.getRole()))
				).collect(Collectors.toList());
		
		
		model.addAttribute("artistsPro", artistasProFiltrados);
		model.addAttribute("artistsNoPro", artistasNoProFiltrados);
		model.addAttribute("artistsDisabled", artistService.listDisabledArtist());
		model.addAttribute("roles", roles);
		
		log.info("Listando artists..." + artistsFiltered.toString());
		return "artists/listArtist";
	}
	

	@GetMapping(value = { "/show/{artistId}" })
	public String showArtist(@PathVariable("artistId") int artistId, Model model) {
		Artist artist = artistService.findArtistById(artistId);
		Boolean showButton = artistService.isActualArtist(artistId) || userService.isAdmin();
		List<Project> myProjects = artistService.findMyProjects(artistId);
		Integer projectsLeft = artistService.leftProjects(artistId);
		Boolean disabled = !artistService.findMyUser(artistId).isEnabled();
		model.addAttribute("projectsLeft",projectsLeft);
		model.addAttribute("myProjects",myProjects);
		model.addAttribute("artistUsername", artist.getUser().getUsername());
		model.addAttribute("artistId", artistId);
		model.addAttribute("artist", artist);
		model.addAttribute("showButton",showButton);
		model.addAttribute("userDisabled",disabled);
		model.addAttribute("isAdmin",userService.isAdmin());
		return "artists/showArtist";
	}	

	@GetMapping("/create")
	public String initFormCreateArtist(Model model) {
		Artist artist = new Artist();
		User user = new User();
		List<Role> role = Arrays.asList(Role.values());
		
		model.addAttribute("user",user);
		model.addAttribute("artist", artist);
		model.addAttribute("roles", role);

		return "artists/createOrUpdateArtistForm";
	}

	@PostMapping("/create")
	public String createArtist(@Valid Artist artist, BindingResult result, Model model) throws UserUniqueException{
		List<Role> role = Arrays.asList(Role.values());
		model.addAttribute("roles", role);
		if(!result.hasErrors()) {
			//Unique artist exception
			try{
				
				this.artistService.createArtist(artist);
			}
			catch(UserUniqueException ex) {
				result.rejectValue("user.username", "unique", "Este usuario ya existe, pruebe con otro");
				return "artists/createOrUpdateArtistForm";
			}
			log.info("Artist Created Successfully");
		} else {
			return "artists/createOrUpdateArtistForm";
		}
		return "redirect:/login";
	}

	@GetMapping("/update/{artistId}")
	public String initFormUpdateArtist(Model model, @PathVariable("artistId") Integer artistId) {
		if(!artistService.isActualArtist(artistId) && !userService.isAdmin()) {
			return "error/error-403";
		}
		Artist artist = artistService.findArtistById(artistId);
		List<Role> roles = Arrays.asList(Role.values());
		model.addAttribute("artistId", artistId);
		model.addAttribute("artist", artist);
		model.addAttribute("roles",roles);
		return "artists/updateArtist";
	}


	@PostMapping("/update/{artistId}")
	public String updateArtist(@ModelAttribute("artist") @Valid Artist artist, BindingResult result,Model model, @PathVariable("artistId") Integer artistId) {
		artist.setId(artistId);
		if(!artistService.isActualArtist(artistId) && !userService.isAdmin()) {
			return "error/error-403";
		}
		List<Role> roles = Arrays.asList(Role.values());
		model.addAttribute("roles",roles);
		if(!result.hasErrors()) {
			artistService.editArtist(artist);
			return "redirect:/artists/show/{artistId}";
		}else {
			return "artists/updateArtist";
		}
	}
	@GetMapping("/delete/{artistId}")
	public String deleteArtist(@PathVariable("artistId") Integer artistId) {
		if(!artistService.isActualArtist(artistId) && !userService.isAdmin()) {
			return "error/error-403";
		}
		try {
			artistService.deleteArtist(artistId);
			log.info("Artist Deleted Successfully");
			if(userService.isAdmin())
				return "redirect:/artists/show/"+artistId;
			SecurityContextHolder.clearContext();
		} catch (Exception e) {
			log.error("Error Deleting Artist", e);
		}
		return "redirect:/";
	}
	@GetMapping("/activate/{artistId}")
	public String activateArtist(@PathVariable("artistId") Integer artistId) {
		if(!userService.isAdmin()) {
			return "error/error-403";
		}
		try {
			artistService.activateArtist(artistId);
			if(!userService.isAdmin())
				SecurityContextHolder.clearContext();
			log.info("Artist Activated Successfully");
		} catch (Exception e) {
			log.error("Error Activating Artist", e);
		}
		return "redirect:/";
	}
}
