@InfoClase(
	nombre = "ClaseEjemplo",
	autor = "Jose Fereira",
	descripcion = "Profe pongame 20 porfa:).",
	version = "1.0"
)
public class ClaseEjemplo {
	@InfoAtributo(descripcion = "Nombre del usuario")
	private String nombre;

	@InfoAtributo(descripcion = "Edad del usuario")
	private int edad;

	@InfoMetodo(descripcion = "Constructor de la clase.")
	public ClaseEjemplo(String nombre, int edad) {
		this.nombre = nombre;
		this.edad = edad;
	}

	@InfoMetodo(descripcion = "Obtiene el nombre.")
	public String getNombre() {
		return nombre;
	}

	@InfoMetodo(descripcion = "Establece el nombre.")
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@InfoMetodo(descripcion = "Obtiene la edad.")
	public int getEdad() {
		return edad;
	}

	@InfoMetodo(descripcion = "Establece la edad.")
	public void setEdad(int edad) {
		this.edad = edad;
	}
}
