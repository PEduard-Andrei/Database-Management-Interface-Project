/** Clasa care contine detaliile tabelului de livrari din baza de date
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
@Table(name = "Livrari")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Livrari {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Livrare")
    private int idLivrare;

    @ManyToOne
    @JoinColumn(name = "ID_Comanda")
    private Comenzi idComanda;

    @Column(name = "Data")
    private LocalDate data;

    @Column(name = "Status_Livrare")
    private String statusLivrare;
}