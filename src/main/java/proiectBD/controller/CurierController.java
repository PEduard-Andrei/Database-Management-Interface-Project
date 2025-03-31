/** Clasa pentru controllerul tabelului de curieri (view/delete/add/edit)
 * @author Paunita Eduard-Andrei 332AA
 * @version 29 Decembrie 2024
 */
package proiectBD.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import proiectBD.service.CurierService;
import proiectBD.tables.Curier;

@Controller
@RequestMapping("/curier")
public class CurierController {
    @Autowired
    private CurierService curierService;

    @GetMapping("/view")
    public String viewCurier(Model model) {
        model.addAttribute("curieri", curierService.getAllCurier());
        return "curier/view";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("curier", new Curier());
        return "curier/add";
    }

    @PostMapping("/add")
    public String addCurier(@ModelAttribute Curier curier) {
        curierService.saveCurier(curier);
        return "redirect:/curier/view";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        model.addAttribute("curier", curierService.getCurierById(id));
        return "curier/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateCurier(@PathVariable int id, @ModelAttribute Curier curier) {
        curier.setIdCurier(id);
        curierService.saveCurier(curier);
        return "redirect:/curier/view";
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteCurier(@PathVariable int id) {
        curierService.deleteCurier(id);
        return ResponseEntity.ok().build();
    }
}