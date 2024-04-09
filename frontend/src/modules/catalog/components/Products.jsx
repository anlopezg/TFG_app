import {FormattedMessage} from 'react-intl';
import PropTypes from 'prop-types';

import * as selectors from '../selectors';
import {PatternLink, ProductLink} from '../../common';
import {UserLink} from "../../common/index.js";

const Products = ({products, crafts, categories}) => {

const productsChunks = chunkArray(products, 3);

const craftNameTranslation = (craftName) =>{
    return <FormattedMessage id={`project.catalog.Crafts.${craftName}`}/>
}

const subcategoryNameTranslation = (subcategoryId, categories) =>{

    for (const category of categories) {
        for (const subcategory of category.subcategories) {
            if (subcategory.id === subcategoryId) {
                return (
                    <FormattedMessage
                        id={`project.catalog.Subcategories.${subcategory.subcategoryName}`}
                    />
                );
            }
        }
    }
    return <FormattedMessage id="project.catalog.Subcategories.unknown"/>
}

return(
    <div>
        {productsChunks.map((chunk, index)  =>(
            <div key={index} className="row">

                {chunk.map((product) => (
                    <div key={product.id} className="col-md-4 mb-4">

                        <div className="card-deck">
                            <div className="card">
                                <img src="/src/crochet_default.jpg" className="card-img-top" alt="Product image"/>

                                <div className="card-body">
                                    <h5 className="card-title">
                                        <ProductLink id={product.id} title={product.title}/>
                                    </h5>

                                    <div className="card-text align-items-center d-flex">
                                        <p>{craftNameTranslation(selectors.getCraftName(crafts, product.craftId))} / </p>
                                        <p> {subcategoryNameTranslation(product.subcategoryId, categories)}</p>
                                    </div>
                                    <div className="d-flex">
                                        <i className="fa-solid fa-user-tag m-1"></i>
                                        <UserLink id={product.userId} username={product.username}/>
                                    </div>


                                    <div className="d-flex justify-content-end">
                                        <p className="card-text bold-label">{product.price} €</p>
                                    </div>


                                </div>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        ))}
    </div>
    );
}

Products.propTypes = {
    products: PropTypes.array.isRequired,
    crafts: PropTypes.array.isRequired
};

function chunkArray(array, size) {
    const chunkedArr = [];
    for (let i = 0; i < array.length; i += size) {
        chunkedArr.push(array.slice(i, i + size));
    }
    return chunkedArr;
}

export default Products;