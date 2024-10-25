package pe.com.cibertec.lp2_costa_azul.model.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_reserva") // Cambiado el nombre de la tabla
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservaEntity { // Cambiado el nombre de la clase
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reserva_id", nullable = false) // Cambiado el nombre de la columna
	private Integer reservaId; // Cambiado el nombre de la variable
	
	@Column(name = "fecha_reserva") // Cambiado el nombre de la columna
	private LocalDate fechaReserva; // Cambiado el nombre de la variable
	
	@ManyToOne
	@JoinColumn(name = "fk_usuario", nullable = false)
	private UsuarioEntity usuarioEntity;
	
	@OneToMany(mappedBy = "reservaEntity", cascade = CascadeType.ALL) // Cambiado el nombre del mapeo
	private List<DetalleReservaEntity> detalleReserva; // Cambiado el nombre de la lista
}
