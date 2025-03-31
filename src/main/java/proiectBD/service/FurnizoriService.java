/** Clasa care se ocupa de serviciu pentru gestionarea furnizorilor,
 * incluzand operatii de adaugare, stergere si obtinere a acestora.
 * @author Paunita Eduard-Andrei 332AA
 * @version 6 Ianuarie 2025
 */

package proiectBD.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proiectBD.repository.FurnizoriRepository;
import proiectBD.tables.Furnizori;

import java.util.List;

@Service
public class FurnizoriService {
    @Autowired
    private FurnizoriRepository furnizoriRepository;

    public List<Furnizori> getAllFurnizori() {
        return furnizoriRepository.findAll();
    }

    public Furnizori getFurnizoriById(int id) {
        return furnizoriRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Furnizorul nu a fost gasit cu id: " + id));
    }

    public Furnizori saveFurnizor(Furnizori furnizor) throws RuntimeException {
        // Verificare spatii
        String normalizedName = furnizor.getNumeFurnizor().trim().replaceAll("\\s+", " ");
        furnizor.setNumeFurnizor(normalizedName);

        if (furnizor.getIdFurnizor() == 0) {
            // Verificare daca furnizorul este deja inregistrat
            if (furnizoriRepository.existsByNumeFurnizorIgnoreCase(furnizor.getNumeFurnizor())) {
                throw new RuntimeException("Acest furnizor este deja înregistrat în baza de date!");
            }
        } else {
            // La updatare verifica daca este deja inregistrat
            if (furnizoriRepository.existsByNumeFurnizorIgnoreCaseAndIdNot(
                    furnizor.getNumeFurnizor(),
                    furnizor.getIdFurnizor())) {
                throw new RuntimeException("Acest furnizor este deja înregistrat în baza de date!");
            }
        }
        return furnizoriRepository.save(furnizor);
    }

    public void deleteFurnizor(int id) {
        furnizoriRepository.deleteById(id);
    }
}