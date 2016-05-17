package info.lefoll.socle.fondation.guice;

import com.google.inject.Binder;
import com.google.inject.multibindings.Multibinder;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Modifier;
import java.util.Set;

public class InjecteurGuiceDynamique {

    public static <T> void listerEtBinderLesTypes(Binder binder, Class<T> typeABinder, String nomDePackage) {

        LOGGER.debug("listerEtBinderLesTypes pour le type %s dans le package %s", typeABinder.getSimpleName(), nomDePackage);

        Multibinder<T> guiceMultibinder = Multibinder.newSetBinder(binder, typeABinder);

        Reflections paramètresRéflections = new Reflections(ClasspathHelper.forPackage("info.lefoll.socle"),
                ClasspathHelper.forPackage(nomDePackage));

        Set<Class<? extends T>> ensembleDeClassesABinder = paramètresRéflections.getSubTypesOf(typeABinder);

        ensembleDeClassesABinder.stream()
                .filter(implémentation -> !Modifier.isAbstract(implémentation.getModifiers()))
                .forEach(implémentation -> guiceMultibinder.addBinding().to(implémentation));
    }

    private InjecteurGuiceDynamique() {
    }

    private static Logger LOGGER = LoggerFactory.getLogger(InjecteurGuiceDynamique.class);
}
