@InfoClase(
	nombre = "ClaseEjemplo",
	autor = "Jose Fereira",
	descripcion = "Profe pongame 20 porfa:).",
	version = "1.0",
	esSubclase = false
)
public class ClaseEjemplo {
	@InfoAtributo(
		tipo = "String",
		descripcion = "Nombre del usuario",
		modificadores = {"private"}
	)
	private String nombre;

	@InfoAtributo(
		tipo = "int",
		descripcion = "Edad del usuario",
		modificadores = {"private"}
	)
	private int edad;

	@InfoMetodo(
		parametros = {"String nombre", "int edad"},
		tipoRetorno = "",
		descripcion = "Constructor de la clase.",
		modificadores = {"public"},
		esConstructor = true
	)
	public ClaseEjemplo(String nombre, int edad) {
		this.nombre = nombre;
		this.edad = edad;
	}

	@InfoMetodo(
		parametros = {},
		tipoRetorno = "String",
		descripcion = "Obtiene el nombre.",
		modificadores = {"public"},
		esGetter = true
	)
	public String getNombre() {
		return nombre;
	}

	@InfoMetodo(
		parametros = {"String nombre"},
		tipoRetorno = "void",
		descripcion = "Establece el nombre.",
		modificadores = {"public"},
		esSetter = true
	)
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@InfoMetodo(
		parametros = {},
		tipoRetorno = "int",
		descripcion = "Obtiene la edad.",
		modificadores = {"public"},
		esGetter = true
	)
	public int getEdad() {
		return edad;
	}

	@InfoMetodo(
		parametros = {"int edad"},
		tipoRetorno = "void",
		descripcion = "Establece la edad.",
		modificadores = {"public"},
		esSetter = true
	)
	public void setEdad(int edad) {
		this.edad = edad;
	}
}
