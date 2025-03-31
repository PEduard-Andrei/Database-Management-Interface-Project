/** Clasa pentru controllerul tabelului de facturi (view/delete/add/edit)
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
import proiectBD.exception.DeleteConstraintException;
import proiectBD.service.FacturiService;
import proiectBD.tables.Facturi;

import java.math.BigDecimal;

@Controller
@RequestMapping("/facturi")
public class FacturiController {
    @Autowired
    private FacturiService facturiService;

    @GetMapping("/view")
    public String viewFacturi(Model model) {
        model.addAttribute("facturi", facturiService.getAllFacturi());
        return "facturi/view";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("factura", new Facturi());
        model.addAttribute("comenzi", facturiService.getAllComenzi());
        return "facturi/add";
    }

    @PostMapping("/add")
    public String addFactura(@ModelAttribute Facturi factura, Model model) {
        if (facturiService.existsFacturaForComanda(factura.getIdComanda().getIdComanda())) {
            model.addAttribute("error", "alreadyexists");
            model.addAttribute("factura", factura);
            model.addAttribute("comenzi", facturiService.getAllComenzi());
            return "facturi/add";
        }

        if (facturiService.isComandaSumZero(factura.getIdComanda().getIdComanda())) {
            model.addAttribute("error", "sumzero");
            model.addAttribute("factura", factura);
            model.addAttribute("comenzi", facturiService.getAllComenzi());
            return "facturi/add";
        }

        facturiService.saveFacturi(factura);
        return "redirect:/facturi/view";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        Facturi factura = facturiService.getFacturiById(id);
        BigDecimal total = facturiService.calculateTotalForComanda(factura.getIdComanda().getIdComanda());

        if (total.compareTo(BigDecimal.ZERO) == 0) {
            redirectAttributes.addFlashAttribute("error", "Comanda este goală! Nu se poate edita factura.");
            return "redirect:/facturi/view";
        }

        model.addAttribute("factura", factura);
        model.addAttribute("comenzi", facturiService.getAllComenzi());
        return "facturi/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateFactura(@PathVariable int id, @ModelAttribute Facturi factura, RedirectAttributes redirectAttributes, Model model) {
        try {
            if (facturiService.existsFacturaForComandaExcludingCurrent(factura.getIdComanda().getIdComanda(), id)) {
                model.addAttribute("error", "O factură este deja înregistrată pentru această comandă!");
                model.addAttribute("factura", factura);
                model.addAttribute("comenzi", facturiService.getAllComenzi());
                return "facturi/edit";
            }

            BigDecimal total = facturiService.calculateTotalForComanda(factura.getIdComanda().getIdComanda());
            if (total.compareTo(BigDecimal.ZERO) == 0) {
                redirectAttributes.addFlashAttribute("error", "Comanda este goala, nu se poate edita factura.");
                return "redirect:/facturi/view";
            }

            factura.setIdFactura(id);
            facturiService.saveFacturi(factura);
            return "redirect:/facturi/view";
        } catch (Exception e) {
            model.addAttribute("error", "A apărut o eroare la actualizarea facturii!");
            model.addAttribute("factura", factura);
            model.addAttribute("comenzi", facturiService.getAllComenzi());
            return "facturi/edit";
        }
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteFactura(@PathVariable int id) {
        try {
            facturiService.deleteFacturi(id);
            return ResponseEntity.ok().build();
        } catch (DeleteConstraintException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/calculate-total/{comandaId}")
    @ResponseBody
    public BigDecimal calculateTotal(@PathVariable int comandaId) {
        return facturiService.calculateTotalForComanda(comandaId);
    }
}