/** Clasa care contine cheia primara a tabelului de detalii comenzi, formata din 2 chei straine.
 * @author Paunita Eduard-Andrei 332AA
 * @version 21 Decembrie 2024
 */

package proiectBD.tables.Detalii_Comenzi;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Detalii_ComenziId implements Serializable {
    @Column(name = "ID_Comanda")
    private Integer idComanda;

    @Column(name = "ID_Produs")
    private Integer idProdus;
}