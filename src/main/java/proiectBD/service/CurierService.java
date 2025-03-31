/** Clasa care se ocupa de serviciu pentru gestionarea curierilor,
 * incluzand operatii de adaugare, stergere si obtinere a acestora.
 * @author Paunita Eduard-Andrei 332AA
 * @version 29 Decembrie 2024
 */

package proiectBD.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proiectBD.repository.CurierRepository;
import proiectBD.tables.Curier;

import java.util.List;

@Service
public class CurierService {
    @Autowired
    private CurierRepository curierRepository;

    public List<Curier> getAllCurier() {
        return curierRepository.findAll();
    }

    public Curier getCurierById(int id) {
        return curierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curierul nu a fost gasit cu id: " + id));
    }

    public Curier saveCurier(Curier curier) {
        return curierRepository.save(curier);
    }

    public void deleteCurier(int id) {
        curierRepository.deleteById(id);
    }
}