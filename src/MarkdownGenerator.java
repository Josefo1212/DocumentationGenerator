import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MarkdownGenerator {
    public static void generarMarkdownParaClase(Class<?> clase, String nombreArchivo) {
        StringBuilder md = new StringBuilder();
        String rutaFuente = "src/" + clase.getSimpleName() + ".java";
        List<String> lineasFuente = leerLineasFuente(rutaFuente);

        InfoClase infoClase = clase.getAnnotation(InfoClase.class);
        List<PropiedadDoc> propiedades = extraerPropiedades(clase, lineasFuente, rutaFuente);
        List<MetodoDoc> metodos = extraerMetodos(clase, lineasFuente, rutaFuente);

        md.append("### File\n");
        md.append(rutaFuente).append("\n\n");

        md.append("### Class\n");
        md.append("\n");
        md.append("| Field | Value |\n");
        md.append("| --- | --- |\n");
        md.append("| Name | ").append(escapeMarkdownCell(valorClase(infoClase, clase.getSimpleName(), CampoClase.NOMBRE))).append(" |\n");
        md.append("| Author | ").append(escapeMarkdownCell(valorClase(infoClase, "No definido", CampoClase.AUTOR))).append(" |\n");
        md.append("| Description | ").append(escapeMarkdownCell(valorClase(infoClase, "No definida", CampoClase.DESCRIPCION))).append(" |\n");
        md.append("| Version | ").append(escapeMarkdownCell(valorClase(infoClase, "No definida", CampoClase.VERSION))).append(" |\n");
        md.append("| Is subclass | ").append(esSubclase(clase)).append(" |\n\n");

        md.append("---\n");

        md.append("### Index\n");
        md.append("\n");
        md.append("### Properties\n");
        md.append("\n");
        md.append("| Name |\n");
        md.append("| --- |\n");
        for (PropiedadDoc propiedad : propiedades) {
            md.append("| ").append(escapeMarkdownCell(propiedad.nombre)).append(" |\n");
        }
        md.append("---\n");

        md.append("### Methods\n");
        md.append("\n");
        md.append("| Name |\n");
        md.append("| --- |\n");
        for (MetodoDoc metodo : metodos) {
            md.append("| ").append(escapeMarkdownCell(metodo.nombre)).append(" |\n");
        }
        md.append("---\n");

        md.append("### Properties\n");
        md.append("\n");
        md.append("| Name | Type | Description | Modifiers | Defined in |\n");
        md.append("| --- | --- | --- | --- | --- |\n");
        for (PropiedadDoc propiedad : propiedades) {
            md.append("| ")
              .append(escapeMarkdownCell(propiedad.nombre))
              .append(" | ")
              .append(escapeMarkdownCell(propiedad.tipo))
              .append(" | ")
              .append(escapeMarkdownCell(propiedad.descripcion))
              .append(" | ")
              .append(escapeMarkdownCell(propiedad.modificadores))
              .append(" | ")
              .append(escapeMarkdownCell(propiedad.definicion))
              .append(" |\n");
        }
        md.append("---\n");
        md.append("### Methods\n");
        md.append("\n");
        md.append("| Name | Parameters | Returns | Description | Modifiers | Getter | Setter | Constructor | Overridden | Defined in |\n");
        md.append("| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |\n");
        for (MetodoDoc metodo : metodos) {
            md.append("| ")
              .append(escapeMarkdownCell(metodo.nombre))
              .append(" | ")
              .append(escapeMarkdownCell(metodo.parametros))
              .append(" | ")
              .append(escapeMarkdownCell(metodo.retorno))
              .append(" | ")
              .append(escapeMarkdownCell(metodo.descripcion))
              .append(" | ")
              .append(escapeMarkdownCell(metodo.modificadores))
              .append(" | ")
              .append(metodo.esGetter)
              .append(" | ")
              .append(metodo.esSetter)
              .append(" | ")
              .append(metodo.esConstructor)
              .append(" | ")
              .append(metodo.esSobreescrito)
              .append(" | ")
              .append(escapeMarkdownCell(metodo.definicion))
              .append(" |\n");
        }
        md.append("---\n");

        md.append("### Property Details\n");
        for (PropiedadDoc propiedad : propiedades) {
            md.append("\n");
            md.append("---\n");
            md.append("### ").append(escapeMarkdownCell(propiedad.nombre)).append("\n");
            md.append("| Field | Value |\n");
            md.append("| --- | --- |\n");
            md.append("| Declaration | ").append(escapeMarkdownCell(propiedad.nombre + ": " + propiedad.tipo)).append(" |\n");
            md.append("| Type | ").append(escapeMarkdownCell(propiedad.tipo)).append(" |\n");
            md.append("| Description | ").append(escapeMarkdownCell(propiedad.descripcion)).append(" |\n");
            md.append("| Modifiers | ").append(escapeMarkdownCell(propiedad.modificadores)).append(" |\n");
            md.append("| Decorators | ").append(escapeMarkdownCell(propiedad.decoradores)).append(" |\n");
            md.append("| Defined in | ").append(escapeMarkdownCell(propiedad.definicion)).append(" |\n\n");
        }

        md.append("---\n");

        md.append("### Method Details\n");
        for (MetodoDoc metodo : metodos) {
            md.append("\n");
            md.append("---\n");
            md.append("### ").append(escapeMarkdownCell(metodo.nombre)).append("\n");
            md.append("| Field | Value |\n");
            md.append("| --- | --- |\n");
            md.append("| Signature | ").append(escapeMarkdownCell(metodo.firma)).append(" |\n");
            md.append("| Parameters | ").append(escapeMarkdownCell(metodo.parametros)).append(" |\n");
            md.append("| Returns | ").append(escapeMarkdownCell(metodo.retorno)).append(" |\n");
            md.append("| Description | ").append(escapeMarkdownCell(metodo.descripcion)).append(" |\n");
            md.append("| Modifiers | ").append(escapeMarkdownCell(metodo.modificadores)).append(" |\n");
            md.append("| Getter | ").append(metodo.esGetter).append(" |\n");
            md.append("| Setter | ").append(metodo.esSetter).append(" |\n");
            md.append("| Constructor | ").append(metodo.esConstructor).append(" |\n");
            md.append("| Overridden | ").append(metodo.esSobreescrito).append(" |\n");
            md.append("| Decorators | ").append(escapeMarkdownCell(metodo.decoradores)).append(" |\n");
            md.append("| Defined in | ").append(escapeMarkdownCell(metodo.definicion)).append(" |\n\n");
        }

        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            writer.write(md.toString());
        } catch (IOException e) {
            System.err.println("No se pudo escribir el archivo markdown: " + e.getMessage());
        }
    }

    private static List<String> leerLineasFuente(String rutaFuente) {
        try {
            return Files.readAllLines(Path.of(rutaFuente));
        } catch (IOException e) {
            return List.of();
        }
    }

    private static List<PropiedadDoc> extraerPropiedades(Class<?> clase, List<String> lineasFuente, String rutaFuente) {
        List<PropiedadDoc> propiedades = new ArrayList<>();
        for (Field field : clase.getDeclaredFields()) {
            InfoAtributo info = field.getAnnotation(InfoAtributo.class);
            int linea = buscarLineaCampo(lineasFuente, field.getName());
            String descripcion = (info != null && !info.descripcion().isBlank()) ? info.descripcion() : "Sin descripcion";
            propiedades.add(new PropiedadDoc(
                field.getName(),
                nombreTipo(field.getType()),
                descripcion,
                textoModificadores(field.getModifiers()),
                construirDecoradores(field.getAnnotations()),
                construirDefinicion(rutaFuente, linea)
            ));
        }
        propiedades.sort(Comparator.comparing(p -> p.nombre));
        return propiedades;
    }

    private static List<MetodoDoc> extraerMetodos(Class<?> clase, List<String> lineasFuente, String rutaFuente) {
        List<MetodoDoc> metodos = new ArrayList<>();

        for (Constructor<?> constructor : clase.getDeclaredConstructors()) {
            InfoMetodo info = constructor.getAnnotation(InfoMetodo.class);
            String nombre = clase.getSimpleName();
            String parametros = firmaParametros(constructor);
            int linea = buscarLineaMetodo(lineasFuente, nombre);
            metodos.add(new MetodoDoc(
                nombre,
                firmaMetodo(nombre, parametros),
                parametros,
                "void",
                (info != null && !info.descripcion().isBlank()) ? info.descripcion() : "Sin descripcion",
                textoModificadores(constructor.getModifiers()),
                construirDecoradores(constructor.getAnnotations()),
                false,
                false,
                true,
                false,
                construirDefinicion(rutaFuente, linea)
            ));
        }

        for (Method method : clase.getDeclaredMethods()) {
            InfoMetodo info = method.getAnnotation(InfoMetodo.class);
            String parametros = firmaParametros(method);
            int linea = buscarLineaMetodo(lineasFuente, method.getName());
            metodos.add(new MetodoDoc(
                method.getName(),
                firmaMetodo(method.getName(), parametros),
                parametros,
                nombreTipo(method.getReturnType()),
                (info != null && !info.descripcion().isBlank()) ? info.descripcion() : "Sin descripcion",
                textoModificadores(method.getModifiers()),
                construirDecoradores(method.getAnnotations()),
                esGetter(method),
                esSetter(method),
                false,
                esSobreescrito(clase, method),
                construirDefinicion(rutaFuente, linea)
            ));
        }

        metodos.sort(Comparator.comparing(m -> m.nombre));
        return metodos;
    }

    private static String construirDecoradores(Annotation[] annotations) {
        List<String> decoradores = new ArrayList<>();
        for (Annotation annotation : annotations) {
            decoradores.add(annotation.toString().replace("@" + annotation.annotationType().getName(), "@" + annotation.annotationType().getSimpleName()));
        }
        return decoradores.isEmpty() ? "-" : String.join("<br>", decoradores);
    }

    private static String textoModificadores(int modificadores) {
        String texto = Modifier.toString(modificadores);
        return texto.isBlank() ? "package-private" : texto;
    }

    private static String firmaParametros(Executable executable) {
        Parameter[] parameters = executable.getParameters();
        if (parameters.length == 0) {
            return "-";
        }

        List<String> partes = new ArrayList<>();
        for (Parameter parameter : parameters) {
            partes.add(nombreTipo(parameter.getType()) + " " + parameter.getName());
        }
        return String.join(", ", partes);
    }

    private static String firmaMetodo(String nombre, String parametros) {
        if ("-".equals(parametros)) {
            return nombre + "()";
        }
        return nombre + "(" + parametros + ")";
    }

    private static String nombreTipo(Class<?> tipo) {
        if (tipo.isArray()) {
            return nombreTipo(tipo.getComponentType()) + "[]";
        }
        return tipo.getSimpleName();
    }

    private static boolean esSubclase(Class<?> clase) {
        Class<?> superClase = clase.getSuperclass();
        return superClase != null && !Object.class.equals(superClase);
    }

    private static boolean esGetter(Method method) {
        if (method.getParameterCount() != 0 || void.class.equals(method.getReturnType())) {
            return false;
        }
        String nombre = method.getName();
        return nombre.startsWith("get") || nombre.startsWith("is");
    }

    private static boolean esSetter(Method method) {
        return method.getName().startsWith("set")
            && method.getParameterCount() == 1
            && void.class.equals(method.getReturnType());
    }

    private static boolean esSobreescrito(Class<?> clase, Method method) {
        if (method.isAnnotationPresent(Override.class)) {
            return true;
        }

        Class<?> superClase = clase.getSuperclass();
        while (superClase != null) {
            try {
                superClase.getDeclaredMethod(method.getName(), method.getParameterTypes());
                return true;
            } catch (NoSuchMethodException ignored) {
                superClase = superClase.getSuperclass();
            }
        }

        for (Class<?> iface : clase.getInterfaces()) {
            try {
                iface.getMethod(method.getName(), method.getParameterTypes());
                return true;
            } catch (NoSuchMethodException ignored) {
                // No hay coincidencia en esta interfaz.
            }
        }
        return false;
    }

    private static String valorClase(InfoClase infoClase, String fallback, CampoClase campo) {
        if (infoClase == null) {
            return fallback;
        }
        return switch (campo) {
            case NOMBRE -> infoClase.nombre();
            case AUTOR -> infoClase.autor();
            case DESCRIPCION -> infoClase.descripcion();
            case VERSION -> infoClase.version();
        };
    }

    private static String construirDefinicion(String rutaFuente, int linea) {
        return linea > 0 ? rutaFuente + ":" + linea : rutaFuente;
    }

    private static String escapeMarkdownCell(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("|", "\\|").replace("\r", " ").replace("\n", "<br>");
    }

    private static int buscarLineaCampo(List<String> lineasFuente, String nombreCampo) {
        for (int i = 0; i < lineasFuente.size(); i++) {
            String linea = lineasFuente.get(i);
            if (linea.contains(nombreCampo) && linea.contains(";") && !linea.contains("(")) {
                return i + 1;
            }
        }
        return -1;
    }

    private static int buscarLineaMetodo(List<String> lineasFuente, String nombreMetodo) {
        for (int i = 0; i < lineasFuente.size(); i++) {
            String linea = lineasFuente.get(i);
            if (linea.contains(nombreMetodo + "(") && linea.contains("{")) {
                return i + 1;
            }
        }
        return -1;
    }

    private enum CampoClase {
        NOMBRE,
        AUTOR,
        DESCRIPCION,
        VERSION
    }

    private record PropiedadDoc(
        String nombre,
        String tipo,
        String descripcion,
        String modificadores,
        String decoradores,
        String definicion
    ) {}

    private record MetodoDoc(
        String nombre,
        String firma,
        String parametros,
        String retorno,
        String descripcion,
        String modificadores,
        String decoradores,
        boolean esGetter,
        boolean esSetter,
        boolean esConstructor,
        boolean esSobreescrito,
        String definicion
    ) {}
}
