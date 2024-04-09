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

            <h4 className="text-left m-3 bold text-uppercase font-bold">
                <i className="fa-solid fa-magnifying-glass m-2 "></i>
                <FormattedMessage id="project.app.Home.filter"/>

            </h4>

            <form className="form-wrapper " onSubmit={e => handleSubmit(e)}>

                <div className="form-row d-flex ml-4 mb-2">

                    <div className="form-group col-md-2 ">
                        <label htmlFor="craftId" className="italic-message">
                            <FormattedMessage id="project.catalog.Craft.field"/>
                        </label>

                        <CraftSelector id="craftId" className="custom-select"
                                          value={craftId} onChange={e => setCraftId(e.target.value)}/>
                    </div>

                    <div className="form-group col-md-2">
                        <label htmlFor="subcategoryId" className="italic-message">
                            <FormattedMessage id="project.catalog.Category.field"/>
                        </label>
                        <SubcategorySelector id="subcategoryId" className="custom-select"
                                             value={subcategoryId} onChange={e => setSubcategoryId(e.target.value)}/>
                    </div>

                    <div className="form-group col-md-2">
                        <label htmlFor="subcategoryId" className="italic-message">
                            <FormattedMessage id="project.catalog.Product.type"/>
                        </label>
                        <select id="productType" className="custom-select"
                                onChange={e=> setProductType(e.target.value)}
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


                    <div className="form-group col-md-4">

                        <label htmlFor="keywords" className="italic-message">
                            <FormattedMessage id="project.global.fields.keywords"/>
                        </label>
                        <input id="keywords" type="text" className="form-control "
                               value={keywords} onChange={e => setKeywords(e.target.value)}/>
                    </div>

                    <div className="form-group col-md-2 align-self-end">
                        <button type="submit" className="btn button-coral">
                            <FormattedMessage id='project.global.buttons.search'/>
                        </button>
                    </div>

                </div>


            </form>
        </div>
    );

}

export default ProductFilter;