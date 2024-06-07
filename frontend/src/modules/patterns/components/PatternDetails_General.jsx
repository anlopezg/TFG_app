import {FormattedMessage} from "react-intl";
import React from "react";
import * as catalogSelectors from "../../catalog/selectors.js";
import {useSelector} from "react-redux";
import ImagesCarousel from "../../catalog/components/ImagesCarousel.jsx";

const PatternDetails_General = ({pattern}) =>{

    const crafts = useSelector(catalogSelectors.getCrafts);
    const categories = useSelector(catalogSelectors.getCategories);

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
            <div className="mb-4 container d-flex justify-content-center align-items-center">

                <div className="card-body mx-4">

                    <div className=" text-center">
                        <div className="framed-title disabled bold-label">
                            <FormattedMessage id="project.products.Pattern.generalInfo"/>
                        </div>
                    </div>

                    <div className="row mt-2">
                        <label className="col-md-12 col-form-label bold-label">
                            <FormattedMessage id="project.products.Product.title"/>
                        </label>
                        <div className="col-md-12 col-form-label">
                            <p>{pattern.title}</p>
                        </div>
                    </div>

                    <div className="row ">
                        <label htmlFor="description" className="col-md-12 col-form-label bold-label">
                            <FormattedMessage id="project.products.Product.description"/>
                        </label>
                        <div className="col-md-12 col-form-label">
                            <p>{pattern.description}</p>
                        </div>
                    </div>

                    <div className="row">
                        <label htmlFor="price" className=" col-md-12 col-form-label bold-label">
                            <FormattedMessage id="project.products.Product.price"/>
                        </label>
                        <div className="col-md-12 col-form-label">
                            <p>{pattern.price} €</p>
                        </div>
                    </div>

                    <div className="row">
                        <label htmlFor="craftId" className="col-form-label bold-label">
                            <FormattedMessage id="project.catalog.Craft.field"/>
                        </label>

                        <div className="col-md-6 col-form-label">
                            <p>{craftNameTranslation(catalogSelectors.getCraftName(crafts, pattern.craftId))} </p>
                        </div>


                        <label htmlFor="subcategoryId" className="col-form-label bold-label">
                            <FormattedMessage id="project.catalog.Category.field"/>
                        </label>

                        <div className="col-md-6 col-form-label">
                            <p>{subcategoryNameTranslation(pattern.subcategoryId, categories)} </p>
                        </div>
                    </div>

                    <div className="row">

                        <label className="col-md-12 col-form-label bold-label">
                            <FormattedMessage id="project.products.Product.images"/>
                        </label>

                        <div style={{maxWidth: "400px", maxHeight: "100%"}}>
                            <ImagesCarousel images={pattern.imagesUrl}/>
                        </div>

                    </div>

                </div>
            </div>
        </div>
    )

}

export default PatternDetails_General;