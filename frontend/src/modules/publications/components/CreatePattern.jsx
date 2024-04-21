import {useState} from 'react';
import {useDispatch, useSelector} from 'react-redux';
import {FormattedMessage} from 'react-intl';
import {useNavigate} from 'react-router-dom';

import {Errors} from '../../common';
import * as actions from '../actions';
import * as userSelector from "../../users/selectors.js";
import CraftSelector from "../../catalog/components/CraftSelector.jsx";
import SubcategorySelector from "../../catalog/components/SubcategorySelector.jsx";

import uploadImages from "../../../backend/cloudinary/uploadImages.js";
import deleteImages from "../../../backend/cloudinary/deleteImages.js";
import {image} from "@cloudinary/url-gen/qualifiers/source";
import imageUploader from "./imageUploader.js";

const CreatePattern = () => {

    const dispatch = useDispatch();
    const navigate = useNavigate();

    const user = useSelector(userSelector.getUser);

    const[ craftId, setCraftId] = useState('');
    const[ subcategoryId, setSubcategoryId] = useState('');
    const[ title, setTitle] = useState('');
    const[ description, setDescription] = useState('');
    const[ price , setPrice ] = useState('');
    const[ active, setActive ] = useState(true);
    const[ introduction, setIntroduction ] = useState('');
    const[ notes, setNotes ] = useState('');
    const[ gauge, setGauge] = useState('');
    const[ sizing, setSizing ] = useState('');
    const[ difficultyLevel, setDifficultyLevel ] = useState('');

    const[abbreviations, setAbbreviations]= useState('');
    const[specialAbbreviations, setSpecialAbbreviations]= useState('');
    const[tools, setTools] = useState('');

    const[timeValue, setTimeValue]= useState('');
    const[timeUnit, setTimeUnit] = useState('');
    const[time, setTime ] = `${timeValue} ${timeUnit}`;


    const { images, previewUrls, handleImagesChange } = imageUploader();


    const [backendErrors, setBackendErrors] = useState(null);
    let form;


    const handleSubmit = event => {
        event.preventDefault();

        let imageResults = [];


        uploadImages(images).then((results) =>{
            console.log("Resultados de la carga:", results);
            imageResults= results;

            const urlList = imageResults.map(imageResult => imageResult.url);
            console.log("Url List: ", urlList);

            if(form.checkValidity()){
                dispatch(actions.createPattern(
                    {userId: user.id,
                        craftId: toNumber(craftId),
                        subcategoryId: toNumber(subcategoryId),
                        title: title.trim(),
                        description: description.trim(),
                        price: price,
                        active: active,
                        introduction: introduction.trim(),
                        notes: notes.trim(),
                        gauge: gauge.trim(),
                        sizing: sizing.trim(),
                        difficultyLevel: difficultyLevel,
                        time: time.trim(),
                        abbreviations: abbreviations.trim(),
                        specialAbbreviations: specialAbbreviations.trim(),
                        tools: tools.trim(),
                        imagesUrl: urlList
                    },
                    () => navigate('/publications/patterns'),
                    errors => setBackendErrors(errors)
                ));
            } else {
                setBackendErrors(null);
                form.classList.add('was-validated');
            }

    });
    }

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
                        <FormattedMessage id="project.products.CreatePattern.heading"/>
                    </h2>


                    <div>
                        <input type="file" accept="image/*" multiple onChange={handleImagesChange}/>
                    </div>
                    {previewUrls.map((url, index) => (
                        <div key={index}>
                            <img src={url} alt="Preview" style={{ width: "200px", height: "200px" }} />
                        </div>
                    ))}



                    <div className="card-body">
                        <form ref={node => form = node}
                              className="needs-validation" noValidate
                              onSubmit={e => handleSubmit(e)}>

                            <div className="m-3 text-center ">
                                <div className="italic-message">
                                    <FormattedMessage id="project.products.CreatePattern.pattern"/>
                                </div>
                            </div>

                            <div className="p-3 text-center">
                                <div className="framed-title disabled bold-label">
                                    <FormattedMessage id="project.products.Pattern.generalInfo"/>
                                </div>
                            </div>

                            <div className="mb-3 text-center">
                                <div className="italic-message small">
                                    <FormattedMessage id="project.products.Pattern.generalInfo.message"/>
                                </div>
                            </div>

                            <div className="form-group row">
                                <label htmlFor="title" className="col-md-12 col-form-label bold-label">
                                    <FormattedMessage id="project.products.Product.title"/>
                                </label>
                                <div className="col-md-12">
                                    <input type="text" id="title" className="form-control"
                                           value={title}
                                           onChange={e => setTitle(e.target.value)}
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
                                           value={price}
                                           min={0}
                                           onChange={e => setPrice(e.target.value)}
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
                                    <CraftSelector id="craftId" className="custom-select my-1 mr-sm-2"
                                                   value={craftId} onChange={e => setCraftId(e.target.value)}/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>

                                <div className="col-md-6">
                                    <label htmlFor="subcategoryId" className="col-form-label bold-label">
                                        <FormattedMessage id="project.catalog.Category.field"/>
                                    </label>
                                    <SubcategorySelector id="subcategoryId" className="custom-select my-1 mr-sm-2"
                                                         value={subcategoryId} onChange={e => setSubcategoryId(e.target.value)}/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>
                            </div>


                            <div className="mt-5 text-center">
                                <div className="framed-title disabled bold-label">
                                    <FormattedMessage id="project.products.Pattern.detailedInfo"/>
                                </div>
                            </div>
                            <div className="mt-2 mb-3 text-center">
                                <div className="italic-message small">
                                    <FormattedMessage id="project.products.Pattern.detailedInfo.message"/>
                                </div>
                            </div>

                            <div className="form-group row">
                                <label htmlFor="introduction" className="col-md-12 col-form-label bold-label">
                                    <FormattedMessage id="project.products.Pattern.introduction"/>
                                </label>
                                <div className="col-md-12">
                                    <textarea id="introduction" className="form-control" rows="2"
                                              value={introduction}
                                              maxLength="500"
                                              onChange={e => setIntroduction(e.target.value)}
                                              required/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>
                            </div>

                            <div className="form-group row">
                                <label htmlFor="notes" className="col-md-12 col-form-label bold-label">
                                    <FormattedMessage id="project.products.Pattern.notes"/>
                                </label>
                                <div className="col-md-12">
                                    <textarea id="notes" className="form-control" rows="2"
                                              value={notes}
                                              maxLength="500"
                                              onChange={e => setNotes(e.target.value)}
                                    />
                                </div>
                            </div>

                            <div className="form-group row">
                                <div className="col-md-6">
                                    <label htmlFor="time" className=" col-form-label bold-label">
                                        <FormattedMessage id="project.products.Pattern.time"/>
                                    </label>

                                    <div className="row">
                                        <div className="col-md-6">
                                            <input type="number" id="time" className="form-control"
                                                   value={timeValue}
                                                   onChange={e => setTimeValue(e.target.value)}
                                                   required/>
                                            <div className="invalid-feedback">
                                                <FormattedMessage id='project.global.validator.required'/>
                                            </div>
                                        </div>
                                        <div className="col-md-6">
                                            <select id="timeUnit" className="form-control" value={timeUnit}
                                                    onChange={e=> setTimeUnit(e.target.value)} required>
                                                <option value="minutes"><FormattedMessage id="project.products.Pattern.time.minutes"/></option>
                                                <option value="hours"><FormattedMessage id="project.products.Pattern.time.hours"/></option>
                                                <option value="days"><FormattedMessage id="project.products.Pattern.time.days"/></option>
                                                <option value="weeks"><FormattedMessage id="project.products.Pattern.time.weeks"/></option>
                                            </select>
                                        </div>
                                    </div>
                                </div>

                                <div className="col-md-6">
                                    <label htmlFor="difficultyLevel" className="col-form-label bold-label">
                                        <FormattedMessage id="project.products.Pattern.difficultyLevel"/>
                                    </label>
                                    <select id="difficultyLevel" className="form-control" value={difficultyLevel}
                                            onChange={e => setDifficultyLevel(e.target.value)}
                                            required>
                                        <option value="0">
                                            <FormattedMessage id="project.global.fields.level.beginner"/></option>
                                        <option value="1">
                                            <FormattedMessage id="project.global.fields.level.intermediate"/></option>
                                        <option value="2">
                                            <FormattedMessage id="project.global.fields.level.advanced"/></option>
                                    </select>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>
                            </div>


                            <div className="form-group row">
                                <div className="col-md-6">
                                    <label htmlFor="abbreviations" className=" col-form-label bold-label">
                                        <FormattedMessage id="project.products.Pattern.abbreviations"/>
                                    </label>
                                    <select id="abbreviations" className="form-control" value={abbreviations}
                                            onChange={e=> setAbbreviations(e.target.value)} required>
                                        <option value="custom">
                                            <FormattedMessage id="project.products.Pattern.standard.custom"/></option>
                                        <option value="usa">
                                            <FormattedMessage id="project.products.Pattern.standard.usa"/></option>
                                        <option value="spanish">
                                            <FormattedMessage id="project.products.Pattern.standard.spanish"/></option>
                                    </select>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>
                                <div className="col-md-6">
                                    <label htmlFor="specialAbbreviations" className=" col-form-label bold-label">
                                        <FormattedMessage id="project.products.Pattern.special.abbreviations"/>
                                    </label>
                                    <textarea
                                        id="specialAbbreviations" className="form-control" rows="2"
                                        value={specialAbbreviations}
                                        maxLength="200"
                                        onChange={e => setSpecialAbbreviations(e.target.value)}>

                                    </textarea>
                                </div>
                            </div>

                            <div className="mt-5 text-center">
                                <div className="framed-title disabled bold-label">
                                    <FormattedMessage id="project.products.Pattern.materials"/>
                                </div>
                            </div>

                            <div className="row">
                                <div className="col-md-12">
                                    <p className="col-form-label bold-label">
                                        <FormattedMessage id="project.products.Pattern.tools"/>
                                    </p>
                                    <div className="italic-message small">
                                        <FormattedMessage id="project.products.Pattern.tools.message"/>
                                    </div>
                                    <input id="tools" className="form-control"
                                           value={tools}
                                           maxLength="200"
                                           onChange={e=>setTools(e.target.value)}
                                           required
                                        />
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>
                            </div>

                            <div className="form-group row">
                                <div className="col-md-6">
                                    <label htmlFor="gauge" className="col-form-label bold-label">
                                        <FormattedMessage id="project.products.Pattern.gauge"/>
                                    </label>
                                    <textarea id="gauge" className="form-control" rows="2"
                                              value={gauge}
                                              maxLength="200"
                                              onChange={e => setGauge(e.target.value)}
                                              required/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>

                                <div className="col-md-6">
                                    <label htmlFor="sizing" className="col-form-label bold-label">
                                        <FormattedMessage id="project.products.Pattern.sizing"/>
                                    </label>
                                    <textarea id="sizing" className="form-control" rows="2"
                                           value={sizing}
                                           maxLength="200"
                                           onChange={e => setSizing(e.target.value)}
                                              required/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>
                            </div>





                            <div className="form-group row">
                                <p className="col-md-12 col-form-label bold-label">
                                    <FormattedMessage id="project.products.Product.active.message"/>
                                </p>
                                <div className="col-md-12 ml-5 mt-2">
                                    <input id="publish" type="radio" className="form-check-input"
                                           checked={active === true}
                                           onChange={()=> handleCheckboxChange('publish')}/>
                                    <label htmlFor="publish" className="form-check-label">
                                        <FormattedMessage id="project.products.Product.publish"/>
                                    </label>
                                </div>

                                <div className="col-md-12 ml-5 mt-2">
                                    <input type="radio" id="draft" className="form-check-input"
                                           checked={active === false}
                                           onChange={()=> handleCheckboxChange('draft')}/>
                                    <label htmlFor="draft" className="form-check-label">
                                        <FormattedMessage id="project.products.Product.draft"/>
                                    </label>
                                </div>

                            </div>

                            <div className="form-group row justify-content-center">
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

export default CreatePattern;