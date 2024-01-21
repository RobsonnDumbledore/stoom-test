package br.com.stoom.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class GenericBatchUpdateService {

    @Autowired
    private EntityManager entityManager;

    /**
     * Executa uma operação de atualização em lote para uma entidade específica.
     *
     * @param sqlQuery A consulta SQL para executar.
     * @param parameters Um mapa contendo os IDs e os status para a consulta.
     * @param entityClass A classe da entidade que está sendo atualizada.
     * @param idParamName O nome do parâmetro da consulta correspondente ao ID da entidade.
     */
    @Transactional
    public <T> void executeBatchUpdate(
            String sqlQuery,
            Map<Long, Boolean> parameters,
            String idParamName,
            Class<T> entityClass
    ) {
        Query query = entityManager.createNativeQuery(sqlQuery, entityClass);

        int i = 0;
        for (Map.Entry<Long, Boolean> entry : parameters.entrySet()) {
            query.setParameter(idParamName, entry.getKey());
            query.setParameter("isActive", entry.getValue());
            query.executeUpdate();

            if (i % 50 == 0) {
                entityManager.flush();
                entityManager.clear();
            }
            i++;
        }
    }
}
