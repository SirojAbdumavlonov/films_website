package com.example.backend_prj.controller;

import com.example.backend_prj.entity.*;
import com.example.backend_prj.exception.UserAlreadyExistException;
import com.example.backend_prj.repository.ExpirationTokenRepository;
import com.example.backend_prj.service.SavedMoviesServiceImpl;
import com.example.backend_prj.utils.JsonFile;
import com.example.backend_prj.service.FilmServiceImpl;
import com.example.backend_prj.service.UserServiceImpl;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.Binding;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;


@Controller
public class FilmController {

    @Autowired
    private final FilmServiceImpl filmService;

    @Autowired
    private final UserServiceImpl userService;

    @Autowired
    private final ExpirationTokenRepository expirationTokenRepository;

    @Autowired
    private final SavedMoviesServiceImpl savedMoviesService;

    List<Film> films;
    List<Film> newFilms;

    public FilmController(FilmServiceImpl filmService, UserServiceImpl userService, ExpirationTokenRepository expirationTokenRepository, SavedMoviesServiceImpl savedMoviesService) {
        this.filmService = filmService;
        this.userService = userService;
        this.expirationTokenRepository = expirationTokenRepository;
        this.savedMoviesService = savedMoviesService;
    }


    @GetMapping("/")
    public String mainPage(Model model) throws Exception {
        films = filmService.returnAllMovies();
        newFilms = filmService.findAllNewFilms();

        model.addAttribute("films",films);
        model.addAttribute("newfilms",newFilms);
        model.addAttribute("savedMovie", new SavedMovie());


        int id = JsonFile.readFromJSON(JsonFile.JSONFILENAME);
        ExpirationToken expirationToken = expirationTokenRepository.findByUserId(id);
        if(expirationToken == null){
            model.addAttribute("loggedUser",null);
             return "mainPage";
        }



        Calendar calendar = Calendar.getInstance();
        System.out.println(expirationToken.getExpirationTime().getTime() - calendar.getTime().getTime());

        if(expirationToken.getExpirationTime().getTime() - calendar.getTime().getTime() <= 0){
            System.out.println(expirationToken.getExpirationTime().getTime() - calendar.getTime().getTime());
            expirationTokenRepository.tokenLoggedToZero(id);
            JsonFile.noOneLogged(JsonFile.JSONFILENAME);
            model.addAttribute("loggedUser",null);
             return "mainPage";
        }
        System.out.println("Logged user = " + userService.findById(id).get().getFullname());
        model.addAttribute("loggedUser", userService.findById(id));
        model.addAttribute("allSavedMovies", savedMoviesService.savedMoviesOfUser(id));
        expirationTokenRepository.tokenLoggedToOne(id);
        JsonFile.writeTo(id,JsonFile.JSONFILENAME);
        return "mainPage";
    }
    @GetMapping("/add_movie")
    public String addFilmPage(Model model){

        model.addAttribute("film",new Film());
        return "add_film";
    }
    @PostMapping("/add_mov")
    public String createMovie(@RequestParam("file") MultipartFile file,
                              @ModelAttribute Film film){
        film = filmService.saveFilm(film.getFilm_grade(),film.getFilm_info(),
                film.getFilm_link(), film.getFilm_name(), film.getRelease(),
                file.getOriginalFilename(), film.getLikes(), film.getDislikes());
        if(film != null) {
            try {
                File saveFile = new ClassPathResource("static/img").getFile();
                Path uploadDir = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(),uploadDir, StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("finished");
        return "redirect:/";
    }
    @GetMapping("/movie_page")
    public String filmPage(){
        return "one_movie";
    }
    @GetMapping("/{id}/movie")
    public String moviePage(@PathVariable("id") int id, Model model){
        model.addAttribute("film",filmService.showFilm(id));
        return "one_movie";
    }
    @PostMapping("/likeordis")
    public String likeAndDislike(){
        return null;
    }

    ///registration
    @GetMapping("/reg")
    public String registering(Model model)
    {
        model.addAttribute("user",new User());
        return "registration";
    }
    @PostMapping("/usereg")
    public String userReg(@ModelAttribute User user, Model model, BindingResult bindingResult){

        if(userService.findUserByEmail(user.getEmail()).isPresent()){
            model.addAttribute("userHas","User with this email already exists");
            JsonFile.noOneLogged(JsonFile.JSONFILENAME);
            throw new UserAlreadyExistException("User with this email has already been registered");
        }

        userService.saveUser(user, new ExpirationToken(user));
        model.addAttribute("exUser",new ExpirationToken(user));
        JsonFile.writeTo(user.getUserId(),JsonFile.JSONFILENAME);


        return "redirect:/";
    }
    ///end registration

    ///logging in
    @GetMapping("/logging")
    public String loggingPage(){
        return "login";
    }
    @PostMapping("/userlog")
    public String loggingIn(@ModelAttribute User user){
        try {
            Optional<User> existIfUser = userService.findByUserByEmailAndPassword(user.getEmail(), user.getPassword());
            if (existIfUser.isEmpty()) {
                JsonFile.noOneLogged(JsonFile.JSONFILENAME);
                return "redirect:/logging";
            }
            userService.deleteAndCreateNewTokenWhenLoggingIn(userService.findTokenId(existIfUser.get().getUserId()),new ExpirationToken(existIfUser.get()));
            expirationTokenRepository.tokenLoggedToOne(existIfUser.get().getUserId());
            JsonFile.writeTo(existIfUser.get().getUserId(), JsonFile.JSONFILENAME);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "redirect:/";
    }
    ///end logging in ///

    ///////
    @PostMapping("/film/cart")
    public String savingFilmToUser(@RequestParam int userId, @RequestParam int movieId, HttpSession session){

        if(savedMoviesService.findingMovieOfUser(userId, movieId) != null){
            savedMoviesService.removeFromCart(userId, movieId);
            session.setAttribute("removed","removed from saved movies list");
            return "redirect:/";
        }
        savedMoviesService.saveMovieToCart(userId,movieId);
        session.setAttribute("added","added to saved movie list");
        return "redirect:/";
    }
    /////////
    @GetMapping("/user/u/{id}")
    public String userPage(@PathVariable("id") int id, Model model){
        model.addAttribute("userSavedMovies", savedMoviesService.allMoviesOfOneUser(id));
        model.addAttribute("loggedUser", userService.findById(id));

        if(!expirationTokenRepository.findByUserId(id).isLogged()){
            return "redirect:/";
        }

        return "user_page";
    }

    @PostMapping("/user/logout")
    public String loggingOut(@RequestParam int userId){
        userService.deleteExpirationToken(userId);
        JsonFile.noOneLogged(JsonFile.JSONFILENAME);
        return "redirect:/";
    }
}
