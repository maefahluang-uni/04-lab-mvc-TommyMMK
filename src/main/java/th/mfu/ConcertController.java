package th.mfu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ConcertController {
    // TODO: create hashmap of concerts for storing data
    private int nextId = 1;
    private HashMap<Integer,Concert> concerts = new HashMap<Integer,Concert>(); 

    //TODO: add initbinder to convert date
    @InitBinder
    public final void initBinderUsuariosFormValidator(final WebDataBinder binder, final Locale locale) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", locale);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
    

    @GetMapping("/concerts")
    public String listConcerts(Model model) {
        model.addAttribute("concerts", concerts.values());
        return "list-concert";
    }

    @GetMapping("/add-concert")
    public String addAConcertForm(Model model) {
        Concert newConcert = new Concert();
        // pass blank employee to a form
        model.addAttribute("concerts", newConcert);
        return "add-concert-form";
    }

    @PostMapping("/concerts")
    public String saveConcert(@ModelAttribute Concert concert) {
        // In a real application, you would save the employee to a database or other storage
        concert.setId(nextId);
        concerts.put(nextId, concert);
        nextId++;
        return "redirect:/concerts";
    }

    @GetMapping("/delete-concert/{id}")
    public String deleteConcert(@PathVariable int id) {
        // pass blank employee to a form
        concerts.remove(id);
        return "redirect:/concerts";
    }

    
    @GetMapping("/delete-concert")
    public String removeAllConcerts() {
        // pass blank employee to a form
        concerts.clear();
        nextId = 1;
        return "redirect:/concerts";
    }

}
