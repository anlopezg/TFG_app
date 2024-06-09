import React from 'react';
import { FormattedMessage } from 'react-intl';
import ISO6391 from 'iso-639-1';

const AdditionalFeatures = ({ product }) => {

    const languageName = ISO6391.getName(product.language) || "Unknown Language";


    if (!product) return null;

    const getDifficultyLevel = (difficultyLevel) =>{
        switch (difficultyLevel){
            case 0:
                return <FormattedMessage id="project.global.fields.level.beginner"/>;
            case 1:
                return <FormattedMessage id="project.global.fields.level.intermediate"/>;
            case 2:
                return <FormattedMessage id="project.global.fields.level.advanced"/>;
        }
    }


    return (
        <div className=" mt-4 text-center">
            <h2 className="retro my-3">
                <FormattedMessage id="project.catalog.Additional.features"/>
            </h2>
            <hr/>


            {product.productType === 'Pattern' ? (
                <div className="row justify-content-center">
                    <div className="col-md-4">
                        <p className="text-muted small text-uppercase">
                            <FormattedMessage id="project.products.Pattern.difficultyLevel" />
                        </p>
                        <div className="border p-2 rounded bg-light">
                            <i className="fa-solid fa-graduation-cap mx-2"></i>
                            {getDifficultyLevel(product.difficultyLevel)}
                        </div>
                    </div>
                    <div className="col-md-4">
                        <p className="text-muted small text-uppercase">
                            <FormattedMessage id="project.products.Pattern.time"  />
                        </p>
                        <div className="border p-2 rounded bg-light">
                            <i className="fa-regular fa-clock mx-2"></i>
                            {product.time}
                        </div>
                    </div>
                    <div className="col-md-4">
                        <p className="text-muted small text-uppercase">
                            <FormattedMessage id="project.global.fields.language" />
                        </p>
                        <div className="border p-2 rounded bg-light">
                            <i className="fa-solid fa-language mx-2"></i>
                            {languageName}
                        </div>
                    </div>
                </div>



            ) : product.productType === 'Physical' ? (
                <div className="row justify-content-center">
                    <div className="col-md-4">
                        <p className="text-muted small text-uppercase">
                            <FormattedMessage id="project.products.Product.color" />
                        </p>
                        <div className="border p-2 rounded bg-light">
                            <i className="fa-solid fa-palette mx-2"></i>
                            {product.color}
                        </div>
                    </div>
                    <div className="col-md-4">
                        <p className="text-muted small text-uppercase">
                            <FormattedMessage id="project.products.Product.details" />
                        </p>
                        <div className="border p-2 rounded bg-light">
                            <i className="fa-solid fa-circle-info mx-2"></i>
                            {product.details}
                        </div>
                    </div>
                    <div className="col-md-4">
                        <p className="text-muted small text-uppercase">
                            <FormattedMessage id="project.products.Product.size"/>
                        </p>
                        <div className="border p-2 rounded bg-light">
                            <i className="fa-solid fa-swatchbook mx-2"></i>
                            {product.size}
                        </div>
                    </div>
                </div>
            ) : null}
        </div>
    );
};

export default AdditionalFeatures;
