package es.udc.paproject.backend.rest.dtos;

import es.udc.paproject.backend.model.entities.Favorite;

import java.util.Optional;

public class FavoriteDto {

    private Long userId;
    private Long productId;
    private Boolean isLiked;

    public FavoriteDto(){}

    public FavoriteDto( Long userId, Long productId, Boolean isLiked){
        this.userId= userId;
        this.productId= productId;
        this.isLiked = isLiked;
    }

    public final static FavoriteDto toOptionalFavoriteDto(Optional<Favorite> favorite){
        return new FavoriteDto(favorite.get().getUser().getId(), favorite.get().getProduct().getId(), favorite.get().getLiked());
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

    public Boolean getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(Boolean isLiked) {
        this.isLiked = isLiked;
    }
}
