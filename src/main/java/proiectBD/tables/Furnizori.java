/** Clasa care contine detaliile tabelului de furnizori din baza de date
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
@Table(name = "Furnizori")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Furnizori {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Furnizor")
    private int idFurnizor;

    @Column(name = "Nume_Furnizor")
    private String numeFurnizor;

    @Column(name = "Adresa")
    private String adresa;

    @Column(name = "Telefon")
    private String telefon;

    @Column(name = "Email")
    private String email;
}
