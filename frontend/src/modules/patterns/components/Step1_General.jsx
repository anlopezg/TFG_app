import React, {useEffect} from 'react';
import {FormattedMessage, FormattedNumber} from "react-intl";
import CraftSelector from "../../catalog/components/CraftSelector.jsx";
import SubcategorySelector from "../../catalog/components/SubcategorySelector.jsx";

const Step1_General = ({ data, onChange, handleImagesChange, handleDeleteImage, previewUrls  }) => {
    const handleChange = (e) => {
        const { name, value } = e.target;
        onChange({ [name]: value });
    };

    useEffect(() => {
        return () => {
            previewUrls.forEach(url => {
                if (url) URL.revokeObjectURL(url);
            });
        };
    }, [previewUrls]);

    return (
        <div>
            <div className="mb-4 container d-flex justify-content-center align-items-center">

                    <div className="card-body ">

                            <div className=" text-center">
                                <div className="framed-title disabled bold-label">
                                    <FormattedMessage id="project.products.Pattern.generalInfo"/>
                                </div>
                            </div>

                            <div className="mb-3 text-center">
                                <div className="my-2">
                                    <FormattedMessage id="project.products.Pattern.generalInfo.message"/>
                                </div>
                                <div className="italic-message small">
                                    <FormattedMessage id="project.products.Pattern.generalInfo.available"/>
                                </div>
                            </div>

                            <div className="form-group row">
                                <label htmlFor="title" className="col-md-12 col-form-label bold-label">
                                    <FormattedMessage id="project.products.Product.title"/>
                                </label>
                                <div className="col-md-12">
                                    <input type="text" id="title" className="form-control"
                                           name="title"
                                           value={data.title}
                                           onChange={handleChange}
                                           required/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>
                            </div>

                            <div className="form-group row">
                                <label htmlFor="description" className="col-md-12 col-form-label bold-label">
                                    <FormattedMessage id="project.products.Product.description"/>
                                </label>
                                <div className="col-md-12">
                                    <textarea id="description" className="form-control" rows="2"
                                              name="description"
                                              value={data.description}
                                              maxLength="500"
                                              onChange={handleChange}
                                              required/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>
                            </div>

                            <div className="form-group row">
                                <label htmlFor="price" className=" col-md-12 col-form-label bold-label">
                                    <FormattedMessage id="project.products.Product.price"/>
                                </label>
                                <div className=" col-md-6 input-group">
                                    <div className="input-group-prepend">
                                        <span className="input-group-text">€</span>
                                    </div>
                                    <input type="number" id="price" className="form-control"
                                           name="price"
                                           value={data.price}
                                           min={0}
                                           onChange={handleChange}
                                           required/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>
                            </div>

                        <br/>
                        <br/>

                            <div className="form-group row">
                                <div className="col-md-6">
                                    <label htmlFor="craftId" className="col-form-label bold-label">
                                        <FormattedMessage id="project.catalog.Craft.field"/>
                                    </label>
                                    <CraftSelector id="craftId" className="form-select"
                                                   name="craftId"
                                                   value={data.craftId} onChange={handleChange}/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>

                                <div className="col-md-6">
                                    <label htmlFor="subcategoryId" className="col-form-label bold-label">
                                        <FormattedMessage id="project.catalog.Category.field"/>
                                    </label>
                                    <SubcategorySelector id="subcategoryId" className="form-select"
                                                         name="subcategoryId"
                                                         value={data.subcategoryId} onChange={handleChange}/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>
                            </div>


                        <br/>
                            <div className="form-group row">
                                <div className="mt-3 mb-3">
                                    <label className="col-md-12 col-form-label bold-label">
                                        <FormattedMessage id="project.products.Product.images"/>
                                    </label>
                                    <div className="italic-message small">
                                        <FormattedMessage id="project.products.Product.uploadImages"/>
                                    </div>
                                </div>
                                <div className="container image-upload-container justify-content-center dashed-border">

                                    {previewUrls.map((url, index) => (
                                        <div key={index} className="row text-center">
                                            <div className="col">
                                                <input
                                                    type="file"
                                                    accept="image/*"
                                                    onChange={(e) => handleImagesChange(e.target.files[0], index)}
                                                    style={{ display: 'none' }}
                                                    id={`file-input-${index}`}
                                                />

                                                <label htmlFor={`file-input-${index}`} className="btn button-light-pink-images">
                                                    {url ? <img src={url} alt="Preview"  />
                                                        :
                                                        <i className="fa-solid fa-plus" style={{color: "#fcfcfc",}}></i>}
                                                </label>
                                            </div>

                                            <div key={index} className="row">
                                                <div className="col">
                                                    {url && (<button type="button" onClick={() => handleDeleteImage(index)} className="btn btn-danger">
                                                        <i className="fa-solid fa-trash"></i></button>)}
                                                </div>
                                            </div>

                                        </div>
                                    ))}
                                </div>
                            </div>



                        </div>
            </div>
        </div>
    );
};

export default Step1_General;
