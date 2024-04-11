package es.udc.paproject.backend.model.daos;

import es.udc.paproject.backend.model.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

public class CustomizedUserDaoImpl implements CustomizedUserDao {

    @PersistenceContext
    private EntityManager entityManager;


    private String[] getTokens(String username) {

        if (username == null || username.length() == 0) {
            return new String[0];
        } else {
            return username.split("\\s");
        }

    }
    @SuppressWarnings("unchecked")
    @Override
    public Slice<User> findSellers(String username, int page, int size) {

        String[] tokens = getTokens(username);
        String queryString = "SELECT u FROM User u WHERE u.role = 1 AND ";

        if(tokens.length != 0){

            for(int i=0; i< tokens.length-1; i++){
                queryString += "LOWER(u.username) LIKE LOWER(:token" + i + ") AND ";
            }

            queryString += "LOWER(u.username) LIKE LOWER(:token" + (tokens.length-1) + ")";
        }

        queryString += " ORDER BY u.username";

        Query query = entityManager.createQuery(queryString).setFirstResult(page*size).setMaxResults(size+1);

        if (tokens.length != 0) {
            for (int i = 0; i<tokens.length; i++) {
                query.setParameter("token" + i, '%' + tokens[i] + '%');
            }

        }

        List<User> users = query.getResultList();
        boolean hasNext = users.size() == (size+1);

        if (hasNext) {
            users.remove(users.size()-1);
        }

        return new SliceImpl<>(users, PageRequest.of(page, size), hasNext);
    }
}
