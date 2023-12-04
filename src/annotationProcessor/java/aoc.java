import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Set;

@SupportedAnnotationTypes("aoc.framework.DaySolution")
public class SolutionFactoryAnnotationProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(DaySolution.class)) {
            if (element.getKind() == ElementKind.CLASS) {
                TypeElement classElement = (TypeElement) element;
                generateFactory(classElement);
            }
        }
        return true;
    }

    private void generateFactory(TypeElement classElement) {
        // Generate factory code here, e.g., using JavaPoet or plain Java code.
        // Create a factory class that instantiates the annotated classes.
        // Write the generated code to a file using the Filer.
        // For simplicity, we'll just print the generated code.
        String className = classElement.getSimpleName().toString();
        String packageName = processingEnv.getElementUtils().getPackageOf(classElement).getQualifiedName().toString();

        String factoryCode = String.format(
                "package %s;\n\n" +
                        "public class %sFactory {\n\n" +
                        "    public static %s createInstance() {\n" +
                        "        return new %s();\n" +
                        "    }\n" +
                        "}\n",
                packageName, className, className, className);

        // Print the generated code to the console (you should write it to a file).
        System.out.println(factoryCode);
    }

    @Retention(RetentionPolicy.RUNTIME)
    public static @interface DaySolution {
    
        public int day();
    
    }
}
