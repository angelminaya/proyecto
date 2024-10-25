package pe.com.cibertec.lp2_costa_azul.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "detalle_reserva") // Cambiado para reflejar el contexto de reservas
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetalleReservaEntity { // Cambiado el nombre de la clase

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detalle_id")
    private Integer detalleId;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "fk_producto", nullable = false)
    private ProductoEntity productoEntity;

    @ManyToOne
    @JoinColumn(name = "fk_reserva", nullable = false) // Cambiado para reflejar el contexto de reservas
    private ReservaEntity reservaEntity; // Cambiado el tipo a ReservaEntity
}
