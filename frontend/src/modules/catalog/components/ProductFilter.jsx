import {useState} from 'react';
import {useDispatch} from 'react-redux';
import {useNavigate} from 'react-router-dom';
import {FormattedMessage} from 'react-intl';


import * as actions from '../actions';
import SubcategorySelector from "./SubcategorySelector.jsx";
import {CraftSelector} from "../index.js";

const ProductFilter = () => {

    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [craftId, setCraftId] = useState('');
    const [subcategoryId, setSubcategoryId] = useState('');
    const [keywords, setKeywords] = useState('');
    const [productType, setProductType] = useState('');

    const handleSubmit = event => {
        event.preventDefault();

        dispatch(actions.findProducts(
            {craftId: toNumber(craftId),
                subcategoryId: toNumber(subcategoryId),
                keywords: keywords.trim(),
                productType: productType.trim(),
                page: 0}));
        navigate('/catalog/find-products-result');
    }

    const toNumber = value => value.length > 0 ? Number(value) : null;

    return (

        <div className="container card back-color-lilac-pink">

            <h4 className="text-left m-3 mb-4 bold text-uppercase font-bold">
                <i className="fa-solid fa-magnifying-glass m-2 "></i>
                <FormattedMessage id="project.catalog.Filter.Products"/>
            </h4>

            <form className="form-wrapper" onSubmit={handleSubmit}>

                <div className="row row-cols-lg-auto mb-3 align-items-end justify-content-center">

                    <div className="col">
                        <div className="form-group">
                            <label htmlFor="craftId" className="italic-message">
                                <FormattedMessage id="project.catalog.Craft.field"/>
                            </label>
                            <CraftSelector id="craftId" className="form-select"
                                           value={craftId} onChange={e => setCraftId(e.target.value)} />
                        </div>
                    </div>

                    <div className="col">
                        <div className="form-group">
                            <label htmlFor="subcategoryId" className="italic-message">
                                <FormattedMessage id="project.catalog.Category.field"/>
                            </label>
                            <SubcategorySelector id="subcategoryId" className="form-select"
                                                 value={subcategoryId} onChange={e => setSubcategoryId(e.target.value)} />
                        </div>
                    </div>

                    <div className="col-md-6 col-lg">
                        <div className="form-group">
                            <label htmlFor="productType" className="italic-message">
                                <FormattedMessage id="project.catalog.Product.type"/>
                            </label>
                            <select id="productType" className="form-select"
                                    onChange={e => setProductType(e.target.value)}
                                    value={productType}>
                                <option value="">
                                    <FormattedMessage id="project.catalog.Selector.all"/>
                                </option>
                                <option value="pattern">
                                    <FormattedMessage id="project.products.Pattern.heading"/>
                                </option>
                                <option value="physical">
                                    <FormattedMessage id="project.products.Physical.heading"/>
                                </option>
                            </select>
                        </div>
                    </div>

                    <div className="col">
                        <div className="form-group">
                            <label htmlFor="keywords" className="italic-message">
                                <FormattedMessage id="project.global.fields.keywords"/>
                            </label>
                            <input id="keywords" type="text" className="form-control"
                                   value={keywords} onChange={e => setKeywords(e.target.value)} />
                        </div>
                    </div>

                    <div className="col-auto">
                        <div className="form-group">
                            <button type="submit" className="btn button-coral">
                                <FormattedMessage id="project.global.buttons.search"/>
                            </button>
                        </div>
                    </div>

                </div>

            </form>

        </div>
    );

}

export default ProductFilter;