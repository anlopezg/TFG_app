import {useEffect, useState} from 'react';
import {useDispatch, useSelector} from 'react-redux';
import {FormattedMessage} from 'react-intl';
import {Form, useNavigate} from 'react-router-dom';


import {Errors} from '../../common';
import * as actions from '../actions';
import * as userSelector from "../../users/selectors.js";
import CraftSelector from "../../catalog/components/CraftSelector.jsx";
import SubcategorySelector from "../../catalog/components/SubcategorySelector.jsx";
import imageUploader from "./imageUploader.js";
import uploadImages from "../../../backend/cloudinary/uploadImages.js";

const CreatePhysical = () => {

    const dispatch = useDispatch();
    const navigate = useNavigate();

    const user = useSelector(userSelector.getUser);

    const[ craftId, setCraftId] = useState('');
    const[ subcategoryId, setSubcategoryId] = useState('');
    const[ title, setTitle] = useState('');
    const[ description, setDescription] = useState('');
    const[ price , setPrice ] = useState('');
    const[ active, setActive ] = useState(true);
    const[ amount, setAmount ] = useState('');
    const[ size, setSize] = useState('');
    const[ color, setColor] = useState('');
    const[ details, setDetails ] = useState('');

    const { images, previewUrls, handleImagesChange, handleDeleteImage } = imageUploader();

    const [backendErrors, setBackendErrors] = useState(null);
    let form;

    const handleSubmit = event => {
        event.preventDefault();

        let imageResults = [];


        uploadImages(images).then((results) => {

            imageResults = results;
            const urlList = imageResults.map(imageResult => imageResult.url);

            if(form.checkValidity()){
                dispatch(actions.createPhysical(
                    { userId: user.id,
                        craftId: toNumber(craftId),
                        subcategoryId: toNumber(subcategoryId),
                        title: title.trim(),
                        description: description.trim(),
                        price: price,
                        active: active,
                        amount: amount,
                        size: size.trim(),
                        color: color.trim(),
                        details: details.trim(),
                        imagesUrl: urlList
                    },
                    () => navigate('/publications/products'),
                    errors => setBackendErrors(errors)
                ));
            } else {
                setBackendErrors(null);
                form.classList.add('was-validated');
            }
        });

    }

    //Handle images changes
    useEffect(() => {
        return () => {
            previewUrls.forEach(url => {
                if (url) URL.revokeObjectURL(url);
            });
        };
    }, [previewUrls]);

    const handleCheckboxChange = (newValue) => {
        if(newValue==="publish"){
            setActive(true);
        } else{
            setActive(false);
        }
    };

    const toNumber = value => value.length > 0 ? Number(value) : null;

    return (
        <div>
            <Errors errors={backendErrors} onClose={() => setBackendErrors(null)}/>
            <div className="mt-4 mb-4 container d-flex justify-content-center align-items-center">
                <div className="card bg-light mb-3 ">
                    <h2 className="retro card-header">
                        <FormattedMessage id="project.products.CreatePhysical.heading"/>
                    </h2>

                    <div className="card-body ">
                        <form ref={node => form = node}
                              className="needs-validation" noValidate
                              onSubmit={e => handleSubmit(e)}>

                            <div className="text-center">
                                <div className="italic-message">
                                    <FormattedMessage id="project.global.form.introduction"/>
                                </div>
                            </div>

                            <div className="form-group row ">
                                <label htmlFor="title" className="col-md-12 col-form-label bold-label ">
                                    <FormattedMessage id="project.products.Product.title"/>
                                </label>
                                <div className="col-md-12">
                                    <input type="text" id="title" className="form-control"
                                           value={title}
                                           maxLength="60"
                                           onChange={e => setTitle(e.target.value)}
                                           autoFocus
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
                                           value={description}
                                              maxLength="500"
                                           onChange={e => setDescription(e.target.value)}
                                           autoFocus
                                           required/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>
                            </div>

                            <div className="form-group row">
                                <div className="col-md-6">
                                    <label htmlFor="price" className="col-form-label bold-label">
                                        <FormattedMessage id="project.products.Product.price"/>
                                    </label>
                                    <div className="input-group">
                                        <div className="input-group-prepend">
                                            <span className="input-group-text">€</span>
                                        </div>
                                        <input type="number" id="price" className="form-control"
                                               value={price}
                                               min={0}
                                               onChange={e => setPrice(e.target.value)}
                                               autoFocus
                                               required/>
                                        <div className="invalid-feedback">
                                            <FormattedMessage id='project.global.validator.required'/>
                                        </div>
                                    </div>
                                </div>

                                <div className="col-md-6">
                                    <label htmlFor="amount" className="col-form-label bold-label">
                                        <FormattedMessage id="project.products.Product.amount"/>
                                    </label>
                                    <input type="number" id="amount" className="form-control"
                                           value={amount}
                                           min={1}
                                           onChange={e => setAmount(e.target.value)}
                                           autoFocus
                                           required/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>
                            </div>

                            <div className="form-group row">
                                <div className="col-md-6">
                                    <label htmlFor="craftId" className="col-form-label bold-label">
                                        <FormattedMessage id="project.catalog.Craft.field"/>
                                    </label>
                                    <CraftSelector id="craftId" className="form-select"
                                                   value={craftId} onChange={e => setCraftId(e.target.value)}/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>

                                <div className="col-md-6">
                                    <label htmlFor="subcategoryId" className="col-form-label bold-label">
                                        <FormattedMessage id="project.catalog.Category.field"/>
                                    </label>
                                    <SubcategorySelector id="subcategoryId" className="form-select"
                                                         value={subcategoryId} onChange={e => setSubcategoryId(e.target.value)}/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>
                            </div>

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
                                                <div className="col ">
                                                    {url && (
                                                        <button type="button" onClick={() => handleDeleteImage(index)} className="btn btn-danger">
                                                            <i className="fa-solid fa-trash"></i></button>
                                                    )}
                                                </div>
                                            </div>
                                        </div>
                                    ))}
                                </div>

                            </div>

                            <div className="form-group row">
                                <div className="col-md-6">
                                    <label htmlFor="color" className="col-form-label bold-label">
                                        <FormattedMessage id="project.products.Product.color"/>
                                    </label>

                                    <input type="text" id="color" className="form-control"
                                           value={color}
                                           maxLength="60"
                                           onChange={e => setColor(e.target.value)}
                                           autoFocus
                                           required/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>

                                <div className="col-md-6">
                                    <label htmlFor="size" className="col-form-label bold-label">
                                        <FormattedMessage id="project.products.Product.size"/>
                                    </label>
                                    <input type="text" id="size" className="form-control"
                                           value={size}
                                           maxLength="60"
                                           onChange={e => setSize(e.target.value)}
                                           autoFocus
                                           required/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>
                            </div>

                            <div className="form-group row">
                                <label htmlFor="details" className="col-md-12 col-form-label bold-label">
                                    <FormattedMessage id="project.products.Product.details"/>
                                </label>
                                <div className="col-md-12">
                                    <textarea id="details" className="form-control" rows="2"
                                              value={details}
                                              maxLength="500"
                                              onChange={e => setDetails(e.target.value)}
                                              autoFocus/>
                                </div>
                            </div>


                            <div className="form-group row">
                                <p className="col-md-12 col-form-label bold-label">
                                    <FormattedMessage id="project.products.Product.active.message"/>
                                </p>
                                <div className="col-md-12 ml-5 mt-2">
                                    <input type="radio" id="publish" className="form-check-input"
                                           checked={active}
                                           onChange={()=> handleCheckboxChange('publish')}/>
                                    <label htmlFor="publish" className="form-check-label mx-2">
                                        <FormattedMessage id="project.products.Product.publish"/>
                                    </label>
                                </div>

                                <div className="col-md-12 ml-5 mt-2">
                                    <input type="radio" id="draft" className="form-check-input"
                                           checked={!active}
                                           onChange={()=> handleCheckboxChange('draft')}/>
                                    <label htmlFor="draft" className="form-check-label mx-2">
                                        <FormattedMessage id="project.products.Product.draft"/>
                                    </label>
                                </div>

                            </div>

                            <div className="form-group row row justify-content-center">
                                <div className="col-md-6 mt-4">
                                    <button type="submit" className="btn button-light-pink bold-label">
                                        <FormattedMessage id="project.global.buttons.submit"/>
                                    </button>
                                </div>
                            </div>

                        </form>
                    </div>
                </div>
            </div>
        </div>

    );

}

export default CreatePhysical;