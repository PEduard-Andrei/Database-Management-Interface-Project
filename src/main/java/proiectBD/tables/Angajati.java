/** Clasa care contine detaliile tabelului de angajati din baza de date
 * @author Paunita Eduard-Andrei 332AA
 * @version 21 Decembrie 2024
 */

package proiectBD.tables;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Angajati")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Angajati {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Angajat")
    private int idAngajat;

    @Column(name = "Nume")
    private String nume;

    @Column(name = "Prenume")
    private String prenume;

    @Column(name = "Functie")
    private String functie;
}
