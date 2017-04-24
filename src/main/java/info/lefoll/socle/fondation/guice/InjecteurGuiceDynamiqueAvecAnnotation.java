package info.lefoll.socle.fondation.guice;


import com.google.inject.Binder;
import com.google.inject.BindingAnnotation;
import com.google.inject.multibindings.Multibinder;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Qualifier;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

public class InjecteurGuiceDynamiqueAvecAnnotation {

    public static <T> void listerEtBinderLesTypesAnnotés(Binder binder, Class<T> typeABinder, String nomDePackage) {

        LOGGER.debug(String.format("listerEtBinderLesTypesAnnotés pour le type %s dans le package %s", typeABinder.getSimpleName(), nomDePackage));

        Reflections paramètresRéflections = new Reflections(ClasspathHelper.forPackage(nomDePackage));

        Set<Class<? extends T>> ensembleDeClassesABinder = paramètresRéflections.getSubTypesOf(typeABinder);

        ensembleDeClassesABinder.stream()
                .filter(implémentation -> !Modifier.isAbstract(implémentation.getModifiers()))
                .forEach(implémentation -> bind(binder, typeABinder, implémentation));
    }

    private static <T> void bind(Binder binder, Class<T> typeABinder, Class<? extends T> implémentation) {
        Optional<Annotation> bindingAnnotation = Arrays.stream(implémentation.getAnnotations())
                .filter(annotation -> annotation.annotationType().isAnnotationPresent(Qualifier.class))
                .findFirst();

        bindingAnnotation.ifPresent(annotation -> {
            LOGGER.debug(String.format("bind pour le type %s avec l'annotation %s et l'implémentation %s", typeABinder.getSimpleName(), annotation.annotationType().getSimpleName(), implémentation.getSimpleName()));
            binder.bind(typeABinder).annotatedWith(annotation).to(implémentation);
        });
    }

    private InjecteurGuiceDynamiqueAvecAnnotation() {
    }

    private static Logger LOGGER = LoggerFactory.getLogger(InjecteurGuiceDynamiqueAvecAnnotation.class);
}
