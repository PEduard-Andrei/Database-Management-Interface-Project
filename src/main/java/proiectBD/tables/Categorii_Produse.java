/** Clasa care contine detaliile tabelului de categorii produse din baza de date
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
@Table(name = "categorii_produse")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Categorii_Produse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categorie")
    private int id_categorie;

    @Column(name = "denumire")
    private String denumire;
}