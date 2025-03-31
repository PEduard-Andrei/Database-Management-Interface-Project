/** Clasa care se ocupa de serviciu pentru gestionarea angajatilor,
 * incluzand operatii de adaugare, stergere si obtinere a acestora.
 * @author Paunita Eduard-Andrei 332AA
 * @version 6 Ianuarie 2025
 */
package proiectBD.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proiectBD.repository.AngajatiRepository;
import proiectBD.tables.Angajati;

import java.util.List;

@Service
public class AngajatiService {
    @Autowired
    private AngajatiRepository angajatiRepository;

    public List<Angajati> getAllAngajati() {
        return angajatiRepository.findAll();
    }

    public Angajati getAngajatById(int id) {
        return angajatiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Angajat not found with id: " + id));
    }

    public Angajati saveAngajat(Angajati angajat) {
        return angajatiRepository.save(angajat);
    }

    public void deleteAngajat(int id) {
        angajatiRepository.deleteById(id);
    }
}