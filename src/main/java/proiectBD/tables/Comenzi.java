/** Clasa care contine detaliile tabelului de comenzi din baza de date
 * @author Paunita Eduard-Andrei 332AA
 * @version 21 Decembrie 2024
 */

package proiectBD.tables;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "comenzi")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comenzi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comanda")
    private int idComanda;

    @ManyToOne
    @JoinColumn(name = "id_furnizor")
    private Furnizori idFurnizor;

    @ManyToOne
    @JoinColumn(name = "id_curier")
    private Curier idCurier;

    @ManyToOne
    @JoinColumn(name = "id_angajat")
    private Angajati idAngajat;

    @Column(name = "data_plasarii")
    private LocalDate dataPlasarii;

    @OneToOne(mappedBy = "idComanda", fetch = FetchType.EAGER)
    private Livrari livrare;
}