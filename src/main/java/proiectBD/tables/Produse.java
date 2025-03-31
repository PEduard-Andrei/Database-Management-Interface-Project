/** Clasa care contine detaliile tabelului de produse din baza de date
 * @author Paunita Eduard-Andrei 332AA
 * @version 21 Decembrie 2024
 */

package proiectBD.tables;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "Produse")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Produse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Produs")
    private int idProdus;

    @ManyToOne
    @JoinColumn(name = "ID_Categorie")
    private Categorii_Produse idCategorie;

    @Column(name = "Nume_Produs")
    private String numeProdus;

    @Column(name = "Descriere")
    private String descriere;

    @Column(name = "Pret")
    private BigDecimal pret;

    @Column(name = "Stoc")
    private int stoc;
}
