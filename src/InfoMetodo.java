import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface InfoMetodo {
	String[] parametros() default {};
	String tipoRetorno() default "";
	String descripcion() default "";
	String[] modificadores() default {};
	boolean esGetter() default false;
	boolean esSetter() default false;
	boolean esConstructor() default false;
	boolean esSobreescrito() default false;
}
