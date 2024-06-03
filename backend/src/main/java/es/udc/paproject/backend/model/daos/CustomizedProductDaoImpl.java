package es.udc.paproject.backend.model.daos;

import es.udc.paproject.backend.model.entities.Product;
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


    @Override
    public Slice<Product> find(Long craftId, Long subcategoryId, String keywords, Class<?> productType, int page, int size){

        String[] tokens = getTokens(keywords);
        String queryString = "SELECT p FROM Product p WHERE p.active= true";

        if (craftId != null || subcategoryId != null || tokens.length > 0 || productType != null) {
            queryString += " AND ";
        }

        // Craft
        if(craftId != null ){
            queryString += " p.craft.id = :craftId ";
        }


        //Subcategory
        if (subcategoryId != null) {

            if (craftId != null) {
                queryString += " AND ";
            }

            queryString += " p.subcategory.id = :subcategoryId ";
        }


        //Keywords
        if (tokens.length != 0) {

            if (subcategoryId != null) {
                queryString += " AND ";
            }

            for (int i = 0; i<tokens.length-1; i++) {
                queryString += "LOWER(p.title) LIKE LOWER(:token" + i + ") AND ";
            }

            queryString += "LOWER(p.title) LIKE LOWER(:token" + (tokens.length-1) + ")";

        }

        //Product type
        if (productType != null) {
            if (subcategoryId != null || craftId != null || tokens.length != 0) {
                queryString += " AND ";
            }
            queryString += " TYPE(p) = :productType";
        }

        //Order by title
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

        if (productType != null) {
            query.setParameter("productType", productType);
        }


        List<Product> products = query.getResultList();
        boolean hasNext = products.size() == (size+1);

        if (hasNext) {
            products.remove(products.size()-1);
        }

        return new SliceImpl<>(products, PageRequest.of(page, size), hasNext);
    }
}
