/** Clasa pentru controllerul paginii principale (dashboardul, interactiunea cu tabelele si functiile acestora
 * + querry-urile/filtrele)
 * @author Paunita Eduard-Andrei 332AA
 * @version 6 Ianuarie 2025
 */
package proiectBD.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import proiectBD.model.DashboardSection;
import proiectBD.service.*;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

@Controller
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private FurnizoriService furnizoriService;

    @Autowired
    private Categorii_ProduseService categorii_produseService;

    @Autowired
    private AngajatiService angajatiService;

    @GetMapping("/dashboard")
    public String dashboard(
            @RequestParam(required = false) String filter,
            @RequestParam(required = false) Integer commandId,
            @RequestParam(required = false) Integer furnizorId,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Integer angajatId,
            @RequestParam(required = false) Integer minStock,
            @RequestParam(required = false) String metodaPlata,
            Model model
    ) {
        model.addAttribute("sections", getSections());

        if (filter != null) {
            Map<String, String> filterParams = new HashMap<>();
            if (commandId != null) filterParams.put("commandId", commandId.toString());
            if (furnizorId != null) filterParams.put("furnizorId", furnizorId.toString());
            if (categoryId != null) filterParams.put("categoryId", categoryId.toString());
            if (status != null) filterParams.put("status", status);
            if (angajatId != null) filterParams.put("angajatId", angajatId.toString());
            if (minStock != null) filterParams.put("minStock", minStock.toString());
            if (metodaPlata != null) filterParams.put("metodaPlata", metodaPlata);

            Map<String, Object> filterData = dashboardService.getFilteredData(filter, filterParams);

            model.addAttribute("filterTitle", filterData.get("title"));
            model.addAttribute("headers", filterData.get("headers"));
            model.addAttribute("results", filterData.get("data"));
            model.addAttribute("currentFilter", filter);

            switch (filter) {
                case "orderDetails" -> {
                    model.addAttribute("showCommandIdFilter", true);
                    model.addAttribute("commandId", commandId);
                }
                case "invoiceList" -> {
                    model.addAttribute("showFurnizorFilter", true);
                    model.addAttribute("furnizorId", furnizorId);
                    model.addAttribute("furnizori", furnizoriService.getAllFurnizori());
                }
                case "productsCategories" -> {
                    model.addAttribute("showCategoryFilter", true);
                    model.addAttribute("categoryId", categoryId);
                    model.addAttribute("categories", categorii_produseService.getAllCategorii_Produse());
                }
                case "deliveriesList" -> {
                    model.addAttribute("showStatusFilter", true);
                    model.addAttribute("status", status);
                    model.addAttribute("statusOptions", List.of("In procesare", "In tranzit", "Livrata", "Returnata"));
                }
                case "ordersWithTotal" -> {
                    model.addAttribute("showAngajatFilter", true);
                    model.addAttribute("angajatId", angajatId);
                    model.addAttribute("angajati", angajatiService.getAllAngajati());
                }
                case "productsWithStock" -> {
                    model.addAttribute("showMinStockFilter", true);
                    model.addAttribute("minStock", minStock);
                }
                case "lastMonthInvoices" -> {
                    model.addAttribute("showMetodaPlataFilter", true);
                    model.addAttribute("metodaPlata", metodaPlata);
                    model.addAttribute("metodePlata", List.of("Card", "Cash", "Transfer bancar"));
                }
                case "topBuyingProducts" -> {
                    model.addAttribute("showCategoryFilter", true);
                    model.addAttribute("categoryId", categoryId);
                    model.addAttribute("categories", categorii_produseService.getAllCategorii_Produse());
                }
                case "supplierProducts", "last30DaysOrders" -> {
                    model.addAttribute("showFurnizorFilter", true);
                    model.addAttribute("furnizorId", furnizorId);
                    model.addAttribute("furnizori", furnizoriService.getAllFurnizori());
                }
            }

            return "dashboard-filtered";
        }

        return "dashboard";
    }

    private List<DashboardSection> getSections() {
        return List.of(
                new DashboardSection("Categorii Produse", "/categorii_produse/view", "/categorii_produse/add"),
                new DashboardSection("Furnizori", "/furnizori/view", "/furnizori/add"),
                new DashboardSection("Produse", "/produse/view", "/produse/add"),
                new DashboardSection("Angajati", "/angajati/view", "/angajati/add"),
                new DashboardSection("Curieri", "/curier/view", "/curier/add"),
                new DashboardSection("Comenzi", "/comenzi/view", "/comenzi/add"),
                new DashboardSection("Detalii Comenzi", "/detalii_comenzi/view", "/detalii_comenzi/add"),
                new DashboardSection("Livrari", "/livrari/view", "/livrari/add"),
                new DashboardSection("Facturi", "/facturi/view", "/facturi/add"),
                new DashboardSection("Evidenta Facturilor", "/evidenta-facturilor/view", "/evidenta-facturilor/add")
        );
    }
}