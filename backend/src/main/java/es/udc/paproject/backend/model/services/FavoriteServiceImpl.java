package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.daos.FavoriteDao;
import es.udc.paproject.backend.model.entities.Favorite;
import es.udc.paproject.backend.model.entities.Product;
import es.udc.paproject.backend.model.entities.User;
import es.udc.paproject.backend.model.exceptions.DuplicateInstanceException;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.OwnerOfProductException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavoriteServiceImpl implements FavoriteService{

    @Autowired
    private FavoriteDao favoriteDao;

    @Autowired
    private PermissionChecker permissionChecker;

    @Autowired
    private PublicationService publicationService;


    @Override
    public Favorite markAsFavoriteProduct(Long userId, Long productId) throws InstanceNotFoundException, DuplicateInstanceException, OwnerOfProductException {

        User user = permissionChecker.checkUser(userId);
        Product product = publicationService.findProductById(productId);

        //Check if the product is already mark as favorite
        Optional<Favorite> alreadyFav = favoriteDao.findByUserAndProduct(user, product);

        if(alreadyFav.isPresent()){
            throw new DuplicateInstanceException("project.entities.Favorite", alreadyFav.get());
        }

        Favorite favorite = new Favorite(user, product, true);

        favoriteDao.save(favorite);
        return favorite;
    }

    @Override
    public List<Product> getFavoriteProducts(Long userId) throws InstanceNotFoundException {

        User user = permissionChecker.checkUser(userId);

        List<Favorite> favorites = favoriteDao.findByUserAndLiked(user, true);

        return favorites.stream().map(Favorite::getProduct).collect(Collectors.toList());
    }


    @Override
    public Optional<Favorite> findFavoriteByUserAndProduct(Long userId, Long productId) throws InstanceNotFoundException{

        User user = permissionChecker.checkUser(userId);

        Product product = publicationService.findProductById(productId);

        Optional<Favorite> favorite = favoriteDao.findByUserAndProduct(user, product);

        if(!favorite.isPresent()){
            throw new InstanceNotFoundException("project.entities.favorite", favorite);
        }

        return Optional.of(favorite.get());
    }

    @Override
    public Favorite findFavoriteById(Long favoriteId) throws InstanceNotFoundException{

        Optional<Favorite> favorite = favoriteDao.findById(favoriteId);

        if(!favorite.isPresent()){
            throw new InstanceNotFoundException("project.entities.favorite", favorite);
        }

        return favorite.get();
    }


    @Override
    public void removeFavoriteProduct(Long userId, Long productId) throws InstanceNotFoundException {

        User user = permissionChecker.checkUser(userId);

        Product product = publicationService.findProductById(productId);

        Optional<Favorite> favorite = favoriteDao.findByUserAndProduct(user, product);

        if(favorite.isEmpty()){
            throw new InstanceNotFoundException("project.entities.favorite", product.getId());
        }

        favoriteDao.deleteById(favorite.get().getId());
    }
}
