package es.udc.paproject.backend.rest.dtos;

import es.udc.paproject.backend.model.entities.Favorite;

public class FavoriteDto {

    private Long userId;
    private Long productId;
    private Boolean liked;

    public FavoriteDto(){}

    public FavoriteDto( Long userId, Long productId, Boolean liked){
        this.userId= userId;
        this.productId= productId;
        this.liked=liked;
    }

    public final static FavoriteDto toFavoriteDto(Favorite favorite){
        return new FavoriteDto(favorite.getUser().getId(), favorite.getProduct().getId(), favorite.getLiked());
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Boolean getLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }
}
