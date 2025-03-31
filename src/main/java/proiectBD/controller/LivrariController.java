/** Clasa pentru controllerul tabelului de livrari (view/delete/add/edit)
 * @author Paunita Eduard-Andrei 332AA
 * @version 6 Ianuarie 2025
 */
package proiectBD.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import proiectBD.service.LivrariService;
import proiectBD.tables.Livrari;

@Controller
@RequestMapping("/livrari")
public class LivrariController {
    @Autowired
    private LivrariService livrariService;

    @GetMapping("/view")
    public String viewLivrari(Model model) {
        model.addAttribute("livrari", livrariService.getAllLivrari());
        return "livrari/view";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("livrare", new Livrari());
        model.addAttribute("comenzi", livrariService.getAllComenzi());
        return "livrari/add";
    }

    @PostMapping("/add")
    public String addLivrare(@ModelAttribute Livrari livrare, RedirectAttributes redirectAttributes, Model model) {
        ResponseEntity<?> response = livrariService.saveLivrari(livrare);

        if (!response.getStatusCode().is2xxSuccessful()) {
            redirectAttributes.addFlashAttribute("error", response.getBody().toString());
            return "redirect:/livrari/add";
        }

        return "redirect:/livrari/view";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Livrari livrare = livrariService.getLivrariById(id);
        if ("Livrata".equals(livrare.getStatusLivrare())) {
            model.addAttribute("error", "Nu se poate edita o livrare finalizată!");
            model.addAttribute("livrare", livrare);
            model.addAttribute("comenzi", livrariService.getAllComenzi());
            return "livrari/edit";
        }
        model.addAttribute("livrare", livrare);
        model.addAttribute("comenzi", livrariService.getAllComenzi());
        return "livrari/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateLivrare(@PathVariable int id, @ModelAttribute Livrari livrare, Model model) {
        try {
            Livrari existingLivrare = livrariService.getLivrariById(id);
            if ("Livrata".equals(existingLivrare.getStatusLivrare())) {
                model.addAttribute("error", "Nu se poate edita o livrare finalizată!");
                model.addAttribute("livrare", livrare);
                model.addAttribute("comenzi", livrariService.getAllComenzi());
                return "livrari/edit";
            }

            // Validare daca exista alta livrare inregistrata deja
            if (livrariService.existsLivrareForComandaExcludingCurrent(livrare.getIdComanda().getIdComanda(), id)) {
                model.addAttribute("error", "Există deja o livrare pentru această comandă!");
                model.addAttribute("livrare", livrare);
                model.addAttribute("comenzi", livrariService.getAllComenzi());
                return "livrari/edit";
            }

            livrare.setIdLivrare(id);
            livrariService.saveLivrari(livrare);
            return "redirect:/livrari/view";
        } catch (Exception e) {
            model.addAttribute("error", "A apărut o eroare la actualizarea livrării!");
            model.addAttribute("livrare", livrare);
            model.addAttribute("comenzi", livrariService.getAllComenzi());
            return "livrari/edit";
        }
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteLivrare(@PathVariable int id) {
        Livrari livrare = livrariService.getLivrariById(id);
        if ("Livrata".equals(livrare.getStatusLivrare())) {
            return ResponseEntity.badRequest().build();
        }
        livrariService.deleteLivrari(id);
        return ResponseEntity.ok().build();
    }
}