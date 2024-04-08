package es.udc.paproject.backend.model.entities;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

public class CustomizedProductDaoImpl implements CustomizedProductDao {

    @PersistenceContext
    private EntityManager entityManager;

    private String[] getTokens(String keywords) {

        if (keywords == null || keywords.length() == 0) {
            return new String[0];
        } else {
            return keywords.split("\\s");
        }

    }

    /**   ELIMINAR ESTA **/

    @SuppressWarnings("unchecked")
    /*
    @Override
    public Slice<Product> find(Long categoryId, String keywords, int page, int size) {

        String[] tokens = getTokens(keywords);
        String queryString = "SELECT p FROM Product p";

        if (categoryId != null || tokens.length > 0) {
            queryString += " WHERE ";
        }

        if (categoryId != null) {
            queryString += "p.category.id = :categoryId";
        }

        if (tokens.length != 0) {

            if (categoryId != null) {
                queryString += " AND ";
            }

            for (int i = 0; i<tokens.length-1; i++) {
                queryString += "LOWER(p.title) LIKE LOWER(:token" + i + ") AND ";
            }

            queryString += "LOWER(p.title) LIKE LOWER(:token" + (tokens.length-1) + ")";

        }

        queryString += " ORDER BY p.title";

        Query query = entityManager.createQuery(queryString).setFirstResult(page*size).setMaxResults(size+1);

        if (categoryId != null) {
            query.setParameter("categoryId", categoryId);
        }

        if (tokens.length != 0) {
            for (int i = 0; i<tokens.length; i++) {
                query.setParameter("token" + i, '%' + tokens[i] + '%');
            }

        }

        List<Product> products = query.getResultList();
        boolean hasNext = products.size() == (size+1);

        if (hasNext) {
            products.remove(products.size()-1);
        }

        return new SliceImpl<>(products, PageRequest.of(page, size), hasNext);

    }*/


    @Override
    public Slice<Product> find(Long craftId, Long subcategoryId, String keywords, int page, int size){

        String[] tokens = getTokens(keywords);
        String queryString = "SELECT p FROM Product p WHERE p.active= true";

        if (craftId != null || subcategoryId != null || tokens.length > 0) {
            queryString += " AND ";
        }

        if(craftId != null ){
            queryString += " p.craft.id = :craftId ";
        }

        if (subcategoryId != null) {

            if (craftId != null) {
                queryString += " AND ";
            }

            queryString += " p.subcategory.id = :subcategoryId ";
        }

        if (tokens.length != 0) {

            if (subcategoryId != null) {
                queryString += " AND ";
            }

            for (int i = 0; i<tokens.length-1; i++) {
                queryString += "LOWER(p.title) LIKE LOWER(:token" + i + ") AND ";
            }

            queryString += "LOWER(p.title) LIKE LOWER(:token" + (tokens.length-1) + ")";

        }

        queryString += " ORDER BY p.title";

        Query query = entityManager.createQuery(queryString).setFirstResult(page*size).setMaxResults(size+1);

        if(craftId != null){
            query.setParameter("craftId", craftId);
        }

        if (subcategoryId != null) {
            query.setParameter("subcategoryId", subcategoryId);
        }

        if (tokens.length != 0) {
            for (int i = 0; i<tokens.length; i++) {
                query.setParameter("token" + i, '%' + tokens[i] + '%');
            }

        }

        List<Product> products = query.getResultList();
        boolean hasNext = products.size() == (size+1);

        if (hasNext) {
            products.remove(products.size()-1);
        }

        return new SliceImpl<>(products, PageRequest.of(page, size), hasNext);
    }
}
