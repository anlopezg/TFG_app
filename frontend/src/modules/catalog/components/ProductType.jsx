import {FormattedMessage} from "react-intl";

const ProductType = ({productType}) =>{

    const productLabel = () => {
        switch (productType) {
            case 'Pattern':
                return (
                    <label className="product-type-label">
                        <FormattedMessage id="project.product.ProductType.Pattern"/>

                    </label>
                );
            case 'Physical':
                return (
                    <label className="product-type-label">
                        <FormattedMessage id="project.product.ProductType.Physical"/>

                    </label>
                );
            default:
                return null;
        }
    };

    return(

        <div>
            {productLabel()}
        </div>
    );
}

export default ProductType;