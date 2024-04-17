import {FormattedMessage} from "react-intl";

const ProductType = ({productType}) =>{

    const productLabel = () => {
        switch (productType) {
            case 'pattern':
                return (
                    <label className="product-type-label btn">
                        <FormattedMessage id="project.product.ProductType.Pattern"/>
                        <i className="fa-regular fa-file-lines ml-2"></i>
                    </label>
                );
            case 'physical':
                return (
                    <label className="product-type-label btn">
                        <FormattedMessage id="project.product.ProductType.Physical"/>
                        <i className="fa-solid fa-shirt ml-2"></i>
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