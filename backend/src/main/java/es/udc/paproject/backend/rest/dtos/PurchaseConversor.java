package es.udc.paproject.backend.rest.dtos;

import es.udc.paproject.backend.model.entities.ProductImages;
import es.udc.paproject.backend.model.entities.Purchase;
import es.udc.paproject.backend.model.entities.PurchaseItem;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PurchaseConversor {

    private PurchaseConversor(){}

    public final static List<PurchaseSummaryDto> toPurchaseSummaryDtos(List<Purchase> orders) {
        return orders.stream().map(o -> toPurchaseSummaryDto(o)).collect(Collectors.toList());
    }

    public final static PurchaseDto toPurchaseDto(Purchase purchase) {

        List<PurchaseItemDto> items = purchase.getItems().stream().map(i -> toPurchaseItemDto(i)).collect(Collectors.toList());

        items.sort(Comparator.comparing(PurchaseItemDto::getProductName));

        return new PurchaseDto(purchase.getId(), items, toMillis(purchase.getDate()), purchase.getTotalPrice(),
                purchase.getPostalAddress(), purchase.getLocality(), purchase.getRegion(), purchase.getCountry(), purchase.getPostalCode());

    }

    private final static PurchaseSummaryDto toPurchaseSummaryDto(Purchase purchase) {

        return new PurchaseSummaryDto(purchase.getId(), toMillis(purchase.getDate()));

    }

    private final static PurchaseItemDto toPurchaseItemDto(PurchaseItem item) {

        Set<ProductImages> images = item.getProduct().getImages();
        String mainImageUrl  = images.iterator().next().getImageUrl();

        return new PurchaseItemDto(item.getId(), item.getProduct().getId(), item.getProduct().getTitle(), item.getProductPrice(),
                item.getQuantity(), mainImageUrl);

    }

    public final static long toMillis(LocalDateTime date) {
        return date.truncatedTo(ChronoUnit.MINUTES).atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli();
    }
}
