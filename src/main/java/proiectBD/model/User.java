/** Clasa care reprezinta utilizatorul, cu numele de utilizator si parola.
 * @author Paunita Eduard-Andrei 332AA
 * @version 21 Decembrie 2024
 */
package proiectBD.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String username;
    private String password;
}