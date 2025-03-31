/** Clasa care contine detaliile tabelului de facturi din baza de date
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
import java.time.LocalDate;

@Entity
@Table(name = "Facturi")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Facturi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Factura")
    private int idFactura;

    @ManyToOne
    @JoinColumn(name = "ID_Comanda")
    private Comenzi idComanda;

    @Column(name = "Metoda_Plata")
    private String metodaPlata;

    @Column(name = "Data")
    private LocalDate data;

    @Column(name = "Suma")
    private BigDecimal suma;
}