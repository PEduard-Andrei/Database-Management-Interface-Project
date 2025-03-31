/** Clasa care contine detaliile tabelului de evidenta facturilor din baza de date
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
@Table(name = "Evidenta_Facturilor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Evidenta_Facturilor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Evidenta")
    private int idEvidenta;

    @ManyToOne
    @JoinColumn(name = "ID_Factura")
    private Facturi idFactura;

    @Column(name = "Data")
    private LocalDate data;

    @Column(name = "Suma_Platita")
    private BigDecimal sumaPlatita;
}
