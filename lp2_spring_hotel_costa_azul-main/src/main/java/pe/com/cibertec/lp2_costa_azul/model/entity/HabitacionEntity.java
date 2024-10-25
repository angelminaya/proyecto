package pe.com.cibertec.lp2_costa_azul.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_habitacion") // Cambiado el nombre de la tabla
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HabitacionEntity { // Cambiado el nombre de la clase
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer habitacionId; // Cambiado el nombre de la variable
	
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@Column(name = "stock", nullable = false)
	private Integer stock;
	
	@Column(name = "precio", nullable = false)
	private Double precio;
	
	@Column(name = "url_imagen", nullable = false)
	private String urlImagen;

}
