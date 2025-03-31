/** Clasa care se ocupa de serviciu pentru gestionarea categoriilor de produse,
 * incluzand operatii de adaugare, stergere si obtinere a acestora.
 * @author Paunita Eduard-Andrei 332AA
 * @version 6 Ianuarie 2025
 */

package proiectBD.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proiectBD.repository.Categorii_ProduseRepository;
import proiectBD.tables.Categorii_Produse;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class Categorii_ProduseService {
    @Autowired
    private Categorii_ProduseRepository categorii_produseRepository;

    public List<Categorii_Produse> getAllCategorii_Produse() {
        return categorii_produseRepository.findAll();
    }

    public Categorii_Produse getCategorii_ProduseById(int id) {
        return categorii_produseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria nu a fost gasita cu id: " + id));
    }

    public Categorii_Produse saveCategorii_Produse(Categorii_Produse categorii_produse) throws RuntimeException {
        // Verificare spatii
        String normalizedName = categorii_produse.getDenumire().trim().replaceAll("\\s+", " ");
        categorii_produse.setDenumire(normalizedName);

        // Verifica daca exista categoria in baza de date
        if (categorii_produseRepository.existsByDenumireIgnoreCase(categorii_produse.getDenumire())) {
            throw new RuntimeException("Această categorie există deja în baza de date!");
        }

        if (categorii_produse.getId_categorie() == 0) {
            Set<Integer> existingIds = categorii_produseRepository.findAll().stream()
                    .map(Categorii_Produse::getId_categorie)
                    .collect(Collectors.toSet());

            int nextId = 1;
            while (existingIds.contains(nextId)) {
                nextId++;
            }
            categorii_produse.setId_categorie(nextId);
        }
        return categorii_produseRepository.save(categorii_produse);
    }

    public Categorii_Produse updateCategorii_Produse(int id, Categorii_Produse categorii_produse) throws RuntimeException {
        // Verificare spatii
        String normalizedName = categorii_produse.getDenumire().trim().replaceAll("\\s+", " ");
        categorii_produse.setDenumire(normalizedName);

        // Verifica daca exista categoria in baza de date
        if (categorii_produseRepository.existsByDenumireIgnoreCaseAndIdNot(categorii_produse.getDenumire(), id)) {
            throw new RuntimeException("Această categorie există deja în baza de date!");
        }

        categorii_produse.setId_categorie(id);
        return categorii_produseRepository.save(categorii_produse);
    }

    public void deleteCategorii_Produse(int id) {
        categorii_produseRepository.deleteById(id);
    }
}