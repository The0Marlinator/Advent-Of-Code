package aoc;

import aoc.framework.solution.AOCSolution;
import aoc.framework.solution.Solution;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.*;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@AutoService(Processor.class)
public class DaySolutionProcessor extends AbstractProcessor {

    boolean generated = false;

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(Solution.class.getName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        if (generated) {
            return true;
        }
        List<String> classNames = new ArrayList<>();
        for (var element : roundEnv.getElementsAnnotatedWith(Solution.class)) {
            if (element instanceof TypeElement typeElement) {
                classNames.add(typeElement.getQualifiedName().toString());
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Found annotated class: " + typeElement.getQualifiedName());
            }
        }

        // Generate the Java file
        generateDaySolutionFactory(classNames);
        generated = true;
        return true;
    }

    private void generateDaySolutionFactory(List<String> classNames) {
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("createDaySolutions")
                .returns(ParameterizedTypeName.get(ClassName.get(List.class),
                        WildcardTypeName.subtypeOf(AOCSolution.class)))
                .addModifiers(javax.lang.model.element.Modifier.PUBLIC, javax.lang.model.element.Modifier.STATIC);


        methodBuilder.addStatement("$T result = new $T<>()",
                ParameterizedTypeName.get(ClassName.get(List.class),
                        TypeName.get(AOCSolution.class)),
                ArrayList.class);

        for (String className : classNames) {
            methodBuilder.addStatement("result.add(new $T(false))", ClassName.bestGuess(className));
        }

        methodBuilder.addStatement("return result");

        TypeSpec factoryClass = TypeSpec.classBuilder("DaySolutionFactory")
                .addMethod(methodBuilder.build())
                .addModifiers(javax.lang.model.element.Modifier.PUBLIC)
                .build();

        JavaFile javaFile = JavaFile.builder("aoc.factory", factoryClass).build();

        try {
            JavaFileObject file = processingEnv.getFiler().createSourceFile("aoc.factory.DaySolutionFactory");
            try (Writer writer = file.openWriter()) {
                javaFile.writeTo(writer);
            }
        } catch (IOException e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Failed to generate factory: " + e.getMessage());
        }
    }

}
