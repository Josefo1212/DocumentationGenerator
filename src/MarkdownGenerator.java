import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.*;

public class MarkdownGenerator {
    public static void generarMarkdownParaClase(Class<?> clase, String nombreArchivo) {
        StringBuilder md = new StringBuilder();

        // Documentar la clase
        InfoClase infoClase = clase.getAnnotation(InfoClase.class);
        if (infoClase != null) {
            md.append("# " + infoClase.nombre() + "\n\n");
            md.append("**Autor:** " + infoClase.autor() + "\n");
            md.append("**Descripción:** " + infoClase.descripcion() + "\n");
            md.append("**Versión:** " + infoClase.version() + "\n");
            md.append("**Subclase:** " + infoClase.esSubclase() + "\n\n");
        }

        // Documentar atributos
        md.append("## Atributos\n\n");
        md.append("| Nombre | Tipo | Descripción | Modificadores |\n");
        md.append("|--------|------|-------------|--------------|\n");
        for (Field field : clase.getDeclaredFields()) {
            InfoAtributo infoAtributo = field.getAnnotation(InfoAtributo.class);
            if (infoAtributo != null) {
                md.append("| " + field.getName() + " | " + infoAtributo.tipo() + " | " + infoAtributo.descripcion() + " | " + String.join(", ", infoAtributo.modificadores()) + " |\n");
            }
        }
        md.append("\n");

        // Documentar métodos
        md.append("## Métodos\n\n");
        md.append("| Nombre | Parámetros | Retorno | Descripción | Modificadores | Getter/Setter | Constructor | Sobreescrito |\n");
        md.append("|--------|------------|---------|-------------|--------------|--------------|-------------|--------------|\n");
        for (Method method : clase.getDeclaredMethods()) {
            InfoMetodo infoMetodo = method.getAnnotation(InfoMetodo.class);
            if (infoMetodo != null) {
                String getterSetter = (infoMetodo.esGetter() ? "Getter" : "") + (infoMetodo.esSetter() ? "Setter" : "");
                md.append("| " + method.getName() + " | " + String.join(", ", infoMetodo.parametros()) + " | " + infoMetodo.tipoRetorno() + " | " + infoMetodo.descripcion() + " | " + String.join(", ", infoMetodo.modificadores()) + " | " + getterSetter + " | " + (infoMetodo.esConstructor() ? "Sí" : "No") + " | " + (infoMetodo.esSobreescrito() ? "Sí" : "No") + " |\n");
            }
        }
        // Documentar constructores
        for (Constructor<?> constructor : clase.getDeclaredConstructors()) {
            InfoMetodo infoMetodo = constructor.getAnnotation(InfoMetodo.class);
            if (infoMetodo != null) {
                md.append("| " + constructor.getName() + " | " + String.join(", ", infoMetodo.parametros()) + " | " + infoMetodo.tipoRetorno() + " | " + infoMetodo.descripcion() + " | " + String.join(", ", infoMetodo.modificadores()) + " | " + (infoMetodo.esGetter() ? "Getter" : "") + (infoMetodo.esSetter() ? "Setter" : "") + " | " + (infoMetodo.esConstructor() ? "Sí" : "No") + " | " + (infoMetodo.esSobreescrito() ? "Sí" : "No") + " |\n");
            }
        }

        // Escribir archivo
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            writer.write(md.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
