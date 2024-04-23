import {useNavigate} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {useState} from "react";

import * as actions from "../actions.js";
import * as selectors from "../selectors.js";
import Errors from "../../common/components/Errors.jsx";
import {FormattedMessage} from "react-intl";
import CraftSelector from "../../catalog/components/CraftSelector.jsx";
import SubcategorySelector from "../../catalog/components/SubcategorySelector.jsx";

const EditPattern = ()=>{

    const dispatch = useDispatch();
    const navigate = useNavigate();
    //const {id} = useParams();
    const pattern= useSelector(selectors.getPattern);

    const [backendErrors, setBackendErrors] = useState(null);
    let form;


    /***********  Pattern attributes  ************/
    const[ craftId, setCraftId] = useState(pattern.craftId);
    const[ subcategoryId, setSubcategoryId] = useState(pattern.subcategoryId);
    const[ title, setTitle] = useState(pattern.title);
    const[ description, setDescription] = useState( pattern.description);
    const[ price , setPrice ] = useState( pattern.price);
    const[ active, setActive ] = useState(pattern.active);
    const[ introduction, setIntroduction ] = useState(pattern.introduction );
    const[ notes, setNotes ] = useState(pattern.notes );
    const[ gauge, setGauge] = useState(pattern.gauge );
    const[ sizing, setSizing ] = useState(pattern.sizing );
    const[ difficultyLevel, setDifficultyLevel ] = useState(pattern.difficultyLevel );
    const[ time, setTime ] = useState(pattern.time);

    const handleSubmit = event =>{
        event.preventDefault();

        if(form.checkValidity()){
            dispatch(actions.editPattern(
                {
                    id: pattern.id,
                    userId: pattern.userId,
                    craftId: craftId,
                    subcategoryId: subcategoryId,
                    title: title.trim(),
                    description: description.trim(),
                    price: price,
                    active: active,
                    introduction: introduction.trim(),
                    notes: notes.trim(),
                    gauge: gauge.trim(),
                    sizing: sizing.trim(),
                    difficultyLevel: difficultyLevel,
                    time: time.trim()
                }, ()=>
                    navigate(`/publications/pattern-details/${pattern.id}`),

                errors => setBackendErrors(errors)
            ));
        } else{
            setBackendErrors(null);
            form.classList.add('was-validated');
        }
    };


    // Active box
    const handleCheckboxChange = (newValue) => {
        if(newValue==="publish"){
            setActive(true);
        } else{
            setActive(false);
        }
    };


    return (
        <div>
            <Errors errors={backendErrors} onClose={() => setBackendErrors(null)}/>
            <div className="mt-4 mb-4 justify-content-center align-items-center">
                <div className="card bg-light mb-3">
                    <h2 className="retro card-header">
                        <FormattedMessage id="project.products.EditPattern.heading"/>
                    </h2>

                    <div className="card-body">
                        <form ref={node => form = node}
                              className="needs-validation" noValidate
                              onSubmit={(e)=> handleSubmit(e)}>

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

                            <div className="form-group row ">
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
                                           step="0.01"
                                           onChange={e => setPrice(e.target.value)}
                                           required/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>
                            </div>

                            <div className="form-group row">
                                <div className="col-md-6">
                                    <label htmlFor="craft" className="col-form-label bold-label">
                                        <FormattedMessage id="project.catalog.Craft.field"/>
                                    </label>
                                    <CraftSelector id="craftId" className="form-select"
                                                   value={craftId} onChange={e => setCraftId(e.target.value)}/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>

                                <div className="col-md-6">
                                    <label htmlFor="subcategory" className="col-form-label bold-label">
                                        <FormattedMessage id="project.catalog.Category.field"/>
                                    </label>
                                    <SubcategorySelector id="subcategoryId" className="form-select"
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
                                <div className="col-md-6">
                                    <label htmlFor="time" className="col-form-label bold-label">
                                        <FormattedMessage id="project.products.Pattern.time"/>
                                    </label>

                                    <input type="text" id="time" className="form-control"
                                           value={time}
                                           maxLength="60"
                                           onChange={e => setTime(e.target.value)}
                                           required/>
                                    <div className="invalid-feedback">
                                        <FormattedMessage id='project.global.validator.required'/>
                                    </div>
                                </div>

                                <div className="col-md-6">
                                    <label htmlFor="difficultyLevel" className="col-form-label bold-label">
                                        <FormattedMessage id="project.products.Pattern.difficultyLevel"/>
                                    </label>
                                    <select id="difficultyLevel" className="form-select" value={difficultyLevel}
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
                                <label htmlFor="active" className="col-md-12 col-form-label bold-label">
                                    <FormattedMessage id="project.products.Product.active.message"/>
                                </label>
                                <div className="col-md-12 ml-5 mt-2">
                                    <input type="radio" id="active" className="form-check-input"
                                           checked={active}
                                           onChange={()=> handleCheckboxChange('publish')}
                                    />
                                    <label htmlFor="publish" className="form-check-label mx-2">
                                        <FormattedMessage id="project.products.Product.publish"/>
                                    </label>
                                </div>

                                <div className="col-md-12 ml-5 mt-2">
                                    <input type="radio" id="draft" className="form-check-input"
                                           checked={!active}
                                           onChange={()=> handleCheckboxChange('draft')}
                                    />
                                    <label htmlFor="draft" className="form-check-label mx-2">
                                        <FormattedMessage id="project.products.Product.draft"/>
                                    </label>
                                </div>
                            </div>

                            <div className="form-group row justify-content-center">
                                <div className="col-md-6 mt-4">
                                    <button type="submit" className="btn button-light-pink bold-label">
                                        <FormattedMessage id="project.global.buttons.save"/>
                                    </button>
                                </div>
                            </div>


                        </form>
                    </div>

                </div>
            </div>
        </div>
    )


}

export default EditPattern;