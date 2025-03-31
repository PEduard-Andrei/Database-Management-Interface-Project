/** Clasa care se ocupa de serviciu pentru gestionarea paginii principale,
 * incluzand operatii de adaugare, vizualizare atat a tabelelor, cat si a filtrelor (query-urilor), redirectionare catre
 * alte pagini.
 * @author Paunita Eduard-Andrei 332AA
 * @version 6 Ianuarie 2025
 */
package proiectBD.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import proiectBD.repository.*;
import proiectBD.tables.*;
import java.util.*;

@Service
public class DashboardService {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private FurnizoriRepository furnizoriRepository;

    @Autowired
    private Categorii_ProduseRepository categoriiProduseRepository;

    @Autowired
    private AngajatiRepository angajatiRepository;

    public Map<String, Object> getFilteredData(String filter, Map<String, String> filterParams) {
        return switch (filter) {
            case "orderDetails" -> getOrderDetailsWithProducts(
                    filterParams.get("commandId") != null ? Integer.parseInt(filterParams.get("commandId")) : null
            );
            case "invoiceList" -> getInvoicesWithTotal(
                    filterParams.get("furnizorId") != null ? Integer.parseInt(filterParams.get("furnizorId")) : null
            );
            case "productsCategories" -> getProductsWithCategories(
                    filterParams.get("categoryId") != null ? Integer.parseInt(filterParams.get("categoryId")) : null
            );
            case "deliveriesList" -> getDeliveriesWithDetails(
                    filterParams.get("status") != null ? filterParams.get("status") : null
            );
            case "ordersWithTotal" -> getOrdersWithTotal(
                    filterParams.get("angajatId") != null ? Integer.parseInt(filterParams.get("angajatId")) : null
            );
            case "productsWithStock" -> getProductsWithStock(
                    filterParams.get("minStock") != null ? Integer.parseInt(filterParams.get("minStock")) : null
            );
            case "last30DaysOrders" -> getLast30DaysOrders(
                    filterParams.get("furnizorId") != null ? Integer.parseInt(filterParams.get("furnizorId")) : null
            );
            case "lastMonthInvoices" -> getLastMonthInvoices(
                    filterParams.get("metodaPlata") != null ? filterParams.get("metodaPlata") : null
            );
            case "topBuyingProducts" -> getTopBuyingProducts(
                    filterParams.get("categoryId") != null ? Integer.parseInt(filterParams.get("categoryId")) : null
            );
            case "supplierProducts" -> getSupplierProducts(
                    filterParams.get("furnizorId") != null ? Integer.parseInt(filterParams.get("furnizorId")) : null
            );
            default -> throw new IllegalArgumentException("Unknown filter: " + filter);
        };
    }

    private Map<String, Object> getOrderDetailsWithProducts(Integer commandId) {
        String title = "Detalii comenzi și produse";
        if (commandId != null) {
            title += " pentru comanda numarul " + commandId;
        }

        StringBuilder sql = new StringBuilder("""
            SELECT 
                c.id_comanda as "ID Comandă",
                p.nume_produs as "Produs",
                dc.cantitate as "Cantitate",
                f.nume_furnizor as "Furnizor",
                a.nume || ' ' || a.prenume as "Angajat"
            FROM detalii_comenzi dc
            JOIN comenzi c ON dc.id_comanda = c.id_comanda
            JOIN produse p ON dc.id_produs = p.id_produs
            JOIN furnizori f ON c.id_furnizor = f.id_furnizor
            JOIN angajati a ON c.id_angajat = a.id_angajat
        """);

        if (commandId != null) {
            sql.append(" WHERE c.id_comanda = :commandId");
        }
        sql.append(" ORDER BY c.id_comanda");

        Query query = entityManager.createNativeQuery(sql.toString());
        if (commandId != null) {
            query.setParameter("commandId", commandId);
        }

        List<Object[]> results = query.getResultList();

        return Map.of(
                "title", title,
                "headers", List.of("Comanda", "Produs", "Cantitate", "Furnizor", "Angajat responsabil"),
                "data", results
        );
    }

    private Map<String, Object> getInvoicesWithTotal(Integer furnizorId) {
        String title = "Lista facturilor cu detalii plăți";
        if (furnizorId != null) {
            Furnizori furnizor = furnizoriRepository.findById(furnizorId)
                    .orElseThrow(() -> new RuntimeException("Furnizor not found"));
            title += " pentru furnizorul " + furnizor.getNumeFurnizor();
        }

        StringBuilder sql = new StringBuilder("""
        SELECT 
            c.id_comanda as "Factura Comanda",  -- Now showing the command ID
            f.data as "Data",
            f.suma as "Suma Totală",
            COALESCE(SUM(ef.suma_platita), 0) as "Suma Plătită",
            f.suma - COALESCE(SUM(ef.suma_platita), 0) as "Suma Restantă",
            fr.nume_furnizor as "Furnizor"
        FROM facturi f
        JOIN comenzi c ON f.id_comanda = c.id_comanda
        JOIN furnizori fr ON c.id_furnizor = fr.id_furnizor
        LEFT JOIN evidenta_facturilor ef ON f.id_factura = ef.id_factura
    """);

        if (furnizorId != null) {
            sql.append(" WHERE fr.id_furnizor = :furnizorId");
        }
        sql.append(" GROUP BY c.id_comanda, f.data, f.suma, fr.nume_furnizor ORDER BY f.data DESC");

        Query query = entityManager.createNativeQuery(sql.toString());
        if (furnizorId != null) {
            query.setParameter("furnizorId", furnizorId);
        }

        List<Object[]> results = query.getResultList();

        return Map.of(
                "title", title,
                "headers", List.of("Factura Comanda", "Data", "Suma Totală", "Suma Plătită", "Suma Restantă", "Furnizor"),
                "data", results
        );
    }


    private Map<String, Object> getProductsWithCategories(Integer categoryId) {
        String title = "Produse și categorii";
        if (categoryId != null) {
            Categorii_Produse category = categoriiProduseRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            title += " pentru categoria " + category.getDenumire();
        }

        StringBuilder sql = new StringBuilder("""
            SELECT 
                p.nume_produs as "Produs",
                cp.denumire as "Categorie",
                p.pret as "Preț",
                p.stoc as "Stoc"
            FROM produse p
            JOIN categorii_produse cp ON p.id_categorie = cp.id_categorie
        """);

        if (categoryId != null) {
            sql.append(" WHERE cp.id_categorie = :categoryId");
        }
        sql.append(" ORDER BY cp.denumire, p.nume_produs");

        Query query = entityManager.createNativeQuery(sql.toString());
        if (categoryId != null) {
            query.setParameter("categoryId", categoryId);
        }

        List<Object[]> results = query.getResultList();

        return Map.of(
                "title", title,
                "headers", List.of("Produs", "Categorie", "Preț", "Stoc"),
                "data", results
        );
    }

    private Map<String, Object> getDeliveriesWithDetails(String status) {
        String title = "Lista livrărilor";
        if (status != null && !status.isEmpty()) {
            title += " cu status: " + status;
        }

        StringBuilder sql = new StringBuilder("""
            SELECT 
                l.id_livrare as "ID Livrare",
                l.data as "Data Livrare",
                l.status_livrare as "Status",
                c.id_comanda as "ID Comandă"
            FROM livrari l
            JOIN comenzi c ON l.id_comanda = c.id_comanda
        """);

        if (status != null && !status.isEmpty()) {
            sql.append(" WHERE l.status_livrare = :status");
        }
        sql.append(" ORDER BY l.data DESC");

        Query query = entityManager.createNativeQuery(sql.toString());
        if (status != null && !status.isEmpty()) {
            query.setParameter("status", status);
        }

        List<Object[]> results = query.getResultList();

        return Map.of(
                "title", title,
                "headers", List.of("Livrarea", "Data Livrare", "Status", "Pentru Comanda"),
                "data", results
        );
    }

    private Map<String, Object> getOrdersWithTotal(Integer angajatId) {
        String title = "Comenzi cu valoare totală";
        if (angajatId != null) {
            Angajati angajat = angajatiRepository.findById(angajatId)
                    .orElseThrow(() -> new RuntimeException("Angajat not found"));
            title += " pentru angajatul " + angajat.getNume() + " " + angajat.getPrenume();
        }

        StringBuilder sql = new StringBuilder("""
            SELECT 
                c.id_comanda as "ID Comandă",
                SUM(p.pret * dc.cantitate) as "Valoare Totală",
                a.nume || ' ' || a.prenume as "Angajat"
            FROM comenzi c
            JOIN detalii_comenzi dc ON c.id_comanda = dc.id_comanda
            JOIN produse p ON dc.id_produs = p.id_produs
            JOIN angajati a ON c.id_angajat = a.id_angajat
        """);

        if (angajatId != null) {
            sql.append(" WHERE a.id_angajat = :angajatId");
        }
        sql.append(" GROUP BY c.id_comanda, a.nume, a.prenume ORDER BY c.id_comanda");

        Query query = entityManager.createNativeQuery(sql.toString());
        if (angajatId != null) {
            query.setParameter("angajatId", angajatId);
        }

        List<Object[]> results = query.getResultList();

        return Map.of(
                "title", title,
                "headers", List.of("Comanda numarul", "Valoare Totală", "Angajat responsabil"),
                "data", results
        );
    }

    private Map<String, Object> getProductsWithStock(Integer minStock) {
        String title = "Produse cu stoc disponibil";
        if (minStock != null) {
            title += String.format(" (minim %d bucăți)", minStock);
        }

        StringBuilder sql = new StringBuilder("""
            SELECT 
                p.nume_produs as "Produs",
                p.stoc as "Stoc Disponibil",
                p.pret as "Preț",
                cp.denumire as "Categorie"
            FROM produse p
            JOIN categorii_produse cp ON p.id_categorie = cp.id_categorie
            WHERE p.stoc > 0
        """);

        if (minStock != null) {
            sql.append(" AND p.stoc >= :minStock");
        }
        sql.append(" ORDER BY p.stoc DESC");

        Query query = entityManager.createNativeQuery(sql.toString());
        if (minStock != null) {
            query.setParameter("minStock", minStock);
        }

        List<Object[]> results = query.getResultList();

        return Map.of(
                "title", title,
                "headers", List.of("Produs", "Stoc Disponibil", "Preț", "Categorie"),
                "data", results
        );
    }

    private Map<String, Object> getLast30DaysOrders(Integer furnizorId) {
        String title = "Comenzi ultimele 30 zile";
        if (furnizorId != null) {
            Furnizori furnizor = furnizoriRepository.findById(furnizorId)
                    .orElseThrow(() -> new RuntimeException("Furnizor not found"));
            title += " pentru furnizorul " + furnizor.getNumeFurnizor();
        }

        StringBuilder sql = new StringBuilder("""
            SELECT 
                f.nume_furnizor as "Furnizor",
                COUNT(c.id_comanda) as "Număr Comenzi",
                SUM(fa.suma) as "Valoare Totală"
            FROM comenzi c
            JOIN furnizori f ON c.id_furnizor = f.id_furnizor
            JOIN facturi fa ON c.id_comanda = fa.id_comanda
            WHERE c.data_plasarii >= CURRENT_DATE - INTERVAL '30 days'
        """);

        if (furnizorId != null) {
            sql.append(" AND f.id_furnizor = :furnizorId");
        }
        sql.append(" GROUP BY f.nume_furnizor ORDER BY COUNT(c.id_comanda) DESC");

        Query query = entityManager.createNativeQuery(sql.toString());
        if (furnizorId != null) {
            query.setParameter("furnizorId", furnizorId);
        }

        List<Object[]> results = query.getResultList();

        return Map.of(
                "title", title,
                "headers", List.of("Furnizor", "Număr Comenzi", "Valoare Totală"),
                "data", results
        );
    }

    private Map<String, Object> getLastMonthInvoices(String metodaPlata) {
        String title = "Facturi luna curentă";
        if (metodaPlata != null && !metodaPlata.isEmpty()) {
            title += " cu metoda de plată: " + metodaPlata;
        }

        StringBuilder sql = new StringBuilder("""
        SELECT 
            c.id_comanda as "Factura Comanda",
            f.data as "Data",
            f.suma as "Suma Totală",
            COALESCE(SUM(ef.suma_platita), 0) as "Suma Plătită",
            f.suma - COALESCE(SUM(ef.suma_platita), 0) as "Suma Restantă",
            fr.nume_furnizor as "Furnizor",
            f.metoda_plata as "Metoda Plată"
        FROM facturi f
        JOIN comenzi c ON f.id_comanda = c.id_comanda
        JOIN furnizori fr ON c.id_furnizor = fr.id_furnizor
        LEFT JOIN evidenta_facturilor ef ON f.id_factura = ef.id_factura
        WHERE EXTRACT(MONTH FROM f.data) = EXTRACT(MONTH FROM CURRENT_DATE)
    """);

        if (metodaPlata != null && !metodaPlata.isEmpty()) {
            sql.append(" AND f.metoda_plata = :metodaPlata");
        }
        sql.append(" GROUP BY c.id_comanda, f.data, f.suma, fr.nume_furnizor, f.metoda_plata ORDER BY f.data DESC");

        Query query = entityManager.createNativeQuery(sql.toString());
        if (metodaPlata != null && !metodaPlata.isEmpty()) {
            query.setParameter("metodaPlata", metodaPlata);
        }

        List<Object[]> results = query.getResultList();

        return Map.of(
                "title", title,
                "headers", List.of("Factura Comanda", "Data", "Suma Totală", "Suma Plătită", "Suma Restantă", "Furnizor", "Metoda Plată"),
                "data", results
        );
    }

    private Map<String, Object> getTopBuyingProducts(Integer categoryId) {
        String title = "Top produse cumpărate ultimele 3 luni";
        if (categoryId != null) {
            Categorii_Produse category = categoriiProduseRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            title += " pentru categoria " + category.getDenumire();
        }

        StringBuilder sql = new StringBuilder("""
            SELECT 
                p.nume_produs as "Produs",
                SUM(dc.cantitate) as "Cantitate Cumpărată",
                cp.denumire as "Categorie",
                f.nume_furnizor as "Furnizor",
                SUM(p.pret * dc.cantitate) as "Valoare Totală"
            FROM detalii_comenzi dc
            JOIN produse p ON dc.id_produs = p.id_produs
            JOIN comenzi c ON dc.id_comanda = c.id_comanda
            JOIN categorii_produse cp ON p.id_categorie = cp.id_categorie
            JOIN furnizori f ON c.id_furnizor = f.id_furnizor
            WHERE c.data_plasarii >= CURRENT_DATE - INTERVAL '90 days'
        """);

        if (categoryId != null) {
            sql.append(" AND cp.id_categorie = :categoryId");
        }
        sql.append(" GROUP BY p.nume_produs, cp.denumire, f.nume_furnizor ORDER BY SUM(dc.cantitate) DESC LIMIT 10");

        Query query = entityManager.createNativeQuery(sql.toString());
        if (categoryId != null) {
            query.setParameter("categoryId", categoryId);
        }

        List<Object[]> results = query.getResultList();

        return Map.of(
                "title", title,
                "headers", List.of("Produs", "Cantitate Cumpărată", "Categorie", "Furnizor", "Valoare Totală"),
                "data", results
        );
    }

    private Map<String, Object> getSupplierProducts(Integer furnizorId) {
        String title = "Sumar produse per furnizor";
        if (furnizorId != null) {
            Furnizori furnizor = furnizoriRepository.findById(furnizorId)
                    .orElseThrow(() -> new RuntimeException("Furnizor not found"));
            title += " pentru furnizorul " + furnizor.getNumeFurnizor();
        }

        StringBuilder sql = new StringBuilder("""
            SELECT 
                f.nume_furnizor as "Furnizor",
                COUNT(DISTINCT p.id_produs) as "Număr Produse",
                SUM(p.stoc) as "Stoc Total",
                ROUND(AVG(p.pret)::numeric, 2) as "Preț Mediu"
            FROM furnizori f
            JOIN comenzi c ON f.id_furnizor = c.id_furnizor
            JOIN detalii_comenzi dc ON c.id_comanda = dc.id_comanda
            JOIN produse p ON dc.id_produs = p.id_produs
        """);

        if (furnizorId != null) {
            sql.append(" WHERE f.id_furnizor = :furnizorId");
        }
        sql.append(" GROUP BY f.nume_furnizor ORDER BY SUM(p.stoc) DESC");

        Query query = entityManager.createNativeQuery(sql.toString());
        if (furnizorId != null) {
            query.setParameter("furnizorId", furnizorId);
        }

        List<Object[]> results = query.getResultList();

        return Map.of(
                "title", title,
                "headers", List.of("Furnizor", "Număr Produse", "Stoc Total", "Preț Mediu"),
                "data", results
        );
    }
}