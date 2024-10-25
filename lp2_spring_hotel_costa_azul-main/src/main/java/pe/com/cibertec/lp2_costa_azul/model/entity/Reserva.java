package pe.com.cibertec.lp2_costa_azul.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reserva { // Cambiado el nombre de la clase
    private Integer cantidad;
    private Integer productoId; // Puede ser un ID de habitación o servicio
}
