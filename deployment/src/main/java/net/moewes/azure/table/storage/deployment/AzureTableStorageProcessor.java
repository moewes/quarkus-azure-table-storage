package net.moewes.azure.table.storage.deployment;

import javax.enterprise.context.ApplicationScoped;

import com.microsoft.azure.storage.table.TableServiceEntity;
import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.arc.deployment.GeneratedBeanBuildItem;
import io.quarkus.arc.deployment.GeneratedBeanGizmoAdaptor;
import io.quarkus.arc.deployment.UnremovableBeanBuildItem;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.CombinedIndexBuildItem;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.gizmo.ClassCreator;
import io.quarkus.gizmo.ClassOutput;
import io.quarkus.runtime.util.HashUtil;
import net.moewes.GenericDao;
import net.moewes.TableStorage;
import org.jboss.jandex.ClassInfo;
import org.jboss.jandex.DotName;
import org.jboss.jandex.IndexView;

class AzureTableStorageProcessor {

    private static final String FEATURE = "azure-table-storage";
    
    private static final DotName TABLE_SERVICE_ENTITY = DotName.createSimple(TableServiceEntity.class.getName());

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    AdditionalBeanBuildItem beans() {
        return new AdditionalBeanBuildItem(TableStorage.class);
    }

    @BuildStep
    void findRepositories(CombinedIndexBuildItem indexBuildItem,
                          BuildProducer<GeneratedBeanBuildItem> implementationsProducer,
                          BuildProducer<UnremovableBeanBuildItem> unremovableBeansProducer) {
        IndexView index = indexBuildItem.getIndex();

        ClassOutput classOutput = new GeneratedBeanGizmoAdaptor(implementationsProducer);

        for (ClassInfo classInfo : index.getKnownDirectSubclasses(TABLE_SERVICE_ENTITY)) {

            String entityType = classInfo.name().toString();
            String className = entityType + "ReposityImpl_" + HashUtil.sha1(entityType);

            String signature = String.format("L%s<L%s;>;",
                    GenericDao.class.getName().replace(".", "/"),
                    entityType.replace(".", "/"));

            ClassCreator classCreator = ClassCreator.builder()
                    .classOutput(classOutput)
                    .className(className)
                    .signature(signature)
                    .superClass(GenericDao.class)
                    .build();

            classCreator.addAnnotation(ApplicationScoped.class);

            classCreator.close();
        }
    }
}
