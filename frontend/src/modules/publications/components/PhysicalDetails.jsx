import {useSelector, useDispatch} from "react-redux";
import {useParams} from "react-router-dom";
import {useEffect} from "react";

import * as selectors from '../selectors';
import * as actions from '../actions';
import {BackLink} from '../../common';
import {FormattedMessage} from "react-intl";
import * as catalogSelectors from "../../catalog/selectors.js";
import ImagesCarousel from "../../catalog/components/ImagesCarousel.jsx";


const PhysicalDetails = () =>{

    const {id} = useParams();
    const physical = useSelector(selectors.getPhysical);
    const dispatch = useDispatch();
    const crafts = useSelector(catalogSelectors.getCrafts);
    const categories = useSelector(catalogSelectors.getCategories);

    useEffect(() => {

        if(!Number.isNaN(id)){
            dispatch(actions.findPhysicalById(id));
        }

        return () => dispatch(actions.clearPhysical());
    }, [id, dispatch]);

    if(!physical){
        return null;
    }

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

    const isItActive = (active) =>{
        if(active === true){
            return <FormattedMessage id="project.products.Product.isPublished"/>
        }else{
            return <FormattedMessage id="project.products.Product.isDraft"/>
        }
    }

    return(
        <div>
            <BackLink/>

            <div className="mt-4 mb-4 justify-content-center align-items-center">
                <div className="container d-flex">
                    <div className="card mb-3 mx-auto ">
                        <h2 className="retro card-header">
                            <FormattedMessage id="project.products.ViewProduct.heading"/>
                        </h2>
                        <div className="card-body">

                            <div className="p-3 text-center">
                                <div className="framed-title disabled bold-label">
                                    <FormattedMessage id="project.products.Pattern.generalInfo"/>
                                </div>
                            </div>

                            <div className="row ml-3">
                                <label htmlFor="title" className="col-md-12 col-form-label bold-label">
                                    <FormattedMessage id="project.products.Product.title"/>
                                </label>

                                <div className="col-md-12 col-form-label">
                                    <p>{physical.title}</p>
                                </div>
                            </div>

                            <div className="row ml-3">
                                <label htmlFor="description" className=" col-md-12 col-form-label bold-label">
                                    <FormattedMessage id="project.products.Product.description"/>
                                </label>
                                <div className="col-md-12 col-form-label">
                                    <p>{physical.description}</p>
                                </div>
                            </div>

                            <div className="row ml-3">
                                <div className="col-md-6">
                                    <label htmlFor="price" className="col-form-label bold-label">
                                        <FormattedMessage id="project.products.Product.price"/>
                                    </label>
                                    <div className="col-form-label">
                                        <p>{physical.price} €</p>
                                    </div>

                                </div>

                                <div className="col-md-6">
                                    <label htmlFor="amount" className="col-form-label bold-label">
                                        <FormattedMessage id="project.products.Product.amount"/>
                                    </label>
                                    <div className="col-form-label">
                                        <p>{physical.amount}<FormattedMessage id="project.products.Product.amount.left"/></p>
                                    </div>
                                </div>

                            </div>

                            <div className="row ml-3">
                                <label htmlFor="craft" className="col-md-12 col-form-label bold-label">
                                    <FormattedMessage id="project.catalog.Craft.field"/>
                                </label>
                                <div className="col-md-12 col-form-label">
                                    <p>{craftNameTranslation(catalogSelectors.getCraftName(crafts, physical.craftId))} </p>
                                </div>
                            </div>


                            <div className="row ml-3">
                                <label htmlFor="craft" className="col-md-12 col-form-label bold-label">
                                    <FormattedMessage id="project.catalog.Craft.field"/>
                                </label>
                                <div className="col-md-12 col-form-label">
                                    <p>{subcategoryNameTranslation(physical.subcategoryId, categories)} </p>
                                </div>
                            </div>


                            <div className="row justify-content-center">
                                <label className="col-md-12 col-form-label bold-label">
                                    <FormattedMessage id="project.products.Product.images"/>
                                </label>
                                <div style={{maxWidth: "400px", maxHeight: "100%"}}>
                                    <ImagesCarousel images={physical.imagesUrl}/>
                                </div>

                            </div>



                            <div className="p-3 text-center">
                                <div className="framed-title disabled bold-label">
                                    <FormattedMessage id="project.products.Pattern.detailedInfo"/>
                                </div>
                            </div>

                            <div className="row ml-3">
                                <label htmlFor="color" className="col-md-12 col-form-label bold-label">
                                    <FormattedMessage id="project.products.Product.color"/>
                                </label>
                                <div className="col-md-12 col-form-label">
                                    <p>{physical.color}</p>
                                </div>
                            </div>

                            <div className="row ml-3">
                                <label htmlFor="size" className="col-md-12 col-form-label bold-label">
                                    <FormattedMessage id="project.products.Product.size"/>
                                </label>
                                <div className="col-md-12 col-form-label">
                                    <p>{physical.size}</p>
                                </div>
                            </div>

                            <div className="row ml-3">
                                <label htmlFor="details" className="col-md-12 col-form-label bold-label">
                                    <FormattedMessage id="project.products.Product.details"/>
                                </label>
                                <div className="col-md-12 col-form-label">
                                    <p>{physical.details}</p>
                                </div>
                            </div>

                            <div className="row ml-3">
                                <label htmlFor="active" className="col-md-12 col-form-label bold-label">
                                    <FormattedMessage id="project.products.Product.active"/>
                                </label>
                                <div className="col-md-12 col-form-label">
                                    <p>{isItActive(physical.active)}</p>
                                </div>
                            </div>

                            <div className="text-center mt-3">
                                <a href={`/publications/manage-physical/${physical.id}`} className="btn btn-primary mr-2">
                                    <FormattedMessage id="project.global.buttons.edit"/>
                                </a>
                                <a href={`/publications/delete-physical/${physical.id}`} className="btn btn-danger mr-2">
                                    <FormattedMessage id="project.global.buttons.delete"/>
                                </a>
                            </div>



                        </div>
                    </div>
                </div>
            </div>

        </div>

    )
}

export default PhysicalDetails;