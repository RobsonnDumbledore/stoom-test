package br.com.stoom;

import java.util.List;
import java.util.Collection;
import br.com.stoom.repositories.BrandRepository;
import br.com.stoom.repositories.ProductRepository;
import br.com.stoom.repositories.CategoryRepository;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class PostgresCleanUpExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(final ExtensionContext context) {
        final var appContext = SpringExtension.getApplicationContext(context);

        cleanUp(List.of(
                appContext.getBean(ProductRepository.class),
                appContext.getBean(CategoryRepository.class),
                appContext.getBean(BrandRepository.class)
        ));
    }

    private void cleanUp(final Collection<CrudRepository> repositories) {
        repositories.forEach(CrudRepository::deleteAll);
    }
}