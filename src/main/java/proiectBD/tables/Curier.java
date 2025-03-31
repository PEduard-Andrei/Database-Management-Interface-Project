/** Clasa care contine detaliile tabelului de curieri din baza de date
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
@Table(name = "Curier")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Curier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Curier")
    private int idCurier;

    @Column(name = "Nume_Curier")
    private String numeCurier;

    @Column(name = "Prenume_Curier")
    private String prenumeCurier;

    @Column(name = "Firma")
    private String firma;
}