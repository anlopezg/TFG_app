package es.udc.paproject.backend.rest.dtos;

import es.udc.paproject.backend.model.entities.Physical;
import es.udc.paproject.backend.model.entities.ProductImages;
import es.udc.paproject.backend.model.entities.ShoppingCart;
import es.udc.paproject.backend.model.entities.ShoppingCartItem;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ShoppingCartConversor {

    private ShoppingCartConversor() {
    }

    public static ShoppingCartDto toShoppingCartDto(ShoppingCart cart) {

        List<ShoppingCartItemDto> items =
                cart.getItems().stream().map(i -> toShoppingCartItemDto(i)).collect(Collectors.toList());

        items.sort(Comparator.comparing(ShoppingCartItemDto::getProductName));

        return new ShoppingCartDto(cart.getId(), items, cart.getTotalQuantity(), cart.getTotalPrice());

    }

    private static ShoppingCartItemDto toShoppingCartItemDto(ShoppingCartItem item) {

        Set<ProductImages> images = item.getProduct().getImages();
        String mainImageUrl  = images.iterator().next().getImageUrl();


        return new ShoppingCartItemDto(item.getProduct().getId(), item.getProduct().getTitle(),
                item.getProduct().getSubcategory().getId(), item.getProduct().getPrice(), item.getQuantity(),
                mainImageUrl, item.getProduct().getProductType());

    }

}
