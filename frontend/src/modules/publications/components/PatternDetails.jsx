import {useSelector, useDispatch} from "react-redux";
import {useParams} from "react-router-dom";
import {useEffect} from "react";

import * as selectors from '../selectors';
import * as actions from '../actions';
import {BackLink} from '../../common';
import {FormattedMessage} from "react-intl";

const PatternDetails = () =>{

    const {id} = useParams();
    const pattern = useSelector(selectors.getPattern);
    const dispatch = useDispatch();
    const crafts = useSelector(selectors.getCrafts);
    const categories = useSelector(selectors.getCategories);


    useEffect(() => {

        if(!Number.isNaN(id)){
            dispatch(actions.findPatternById(id));
        }

        return () => dispatch(actions.clearPattern());
    }, [id, dispatch]);

    if(!pattern){
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

    const isItActive = (active) =>{
        if(active === true){
            return <FormattedMessage id="project.products.Product.isPublished"/>
        }else{
            return <FormattedMessage id="project.products.Product.isDraft"/>
        }
    }

    return(
        <div>

            <a href={`/publications/patterns/`} className="btn btn-link back-link-blue">
                <i className="fa-solid fa-angle-left mr-2"></i>
                <FormattedMessage id='project.global.buttons.back'/>
            </a>


            <div className="mt-4 mb-4 justify-content-center align-items-center">
                <div className="container d-flex">
                    <div className="card mb-3 mx-auto ">
                        <h2 className="card-header">
                            <FormattedMessage id="project.products.ViewPattern.heading"/>
                        </h2>
                        <div className="card-body">

                            <div className="p-3 text-center">
                                <div className="framed-title disabled bold-label">
                                    <FormattedMessage id="project.products.Pattern.generalInfo"/>
                                </div>
                            </div>

                            <div className="pb-4 text-center">
                                <div className="italic-message small">
                                    <FormattedMessage id="project.products.Pattern.generalInfo.message"/>
                                </div>
                            </div>
                            <div className="row ml-3">
                                <label htmlFor="title" className="col-md-12 col-form-label bold-label">
                                    <FormattedMessage id="project.products.Product.title"/>
                                </label>

                                <div className="col-md-12 col-form-label">
                                    <p>{pattern.title}</p>
                                </div>
                            </div>

                            <div className="row ml-3">
                                    <label htmlFor="description" className=" col-md-12 col-form-label bold-label">
                                        <FormattedMessage id="project.products.Product.description"/>
                                    </label>
                                <div className="col-md-12 col-form-label">
                                    <p>{pattern.description}</p>
                                </div>
                            </div>

                            <div className="row ml-3">

                                <label htmlFor="price" className="col-md-12 col-form-label bold-label">
                                        <FormattedMessage id="project.products.Product.price"/>
                                </label>
                                <div className="col-md-12 col-form-label">
                                    <p>{pattern.price} €</p>
                                </div>
                            </div>

                            <div className="row ml-3">
                                <label htmlFor="craft" className="col-md-12 col-form-label bold-label">
                                        <FormattedMessage id="project.catalog.Craft.field"/>
                                </label>
                                <div className="col-md-12 col-form-label">
                                    <p>{craftNameTranslation(selectors.getCraftName(crafts, pattern.craftId))} </p>
                                </div>
                            </div>


                            <div className="row ml-3">
                                <label htmlFor="craft" className="col-md-12 col-form-label bold-label">
                                        <FormattedMessage id="project.catalog.Craft.field"/>
                                </label>
                                <div className="col-md-12 col-form-label">
                                    <p>{subcategoryNameTranslation(pattern.subcategoryId, categories)} </p>
                                </div>
                            </div>

                            <div className="p-3 text-center">
                                <div className="framed-title disabled bold-label">
                                    <FormattedMessage id="project.products.Pattern.detailedInfo"/>
                                </div>
                            </div>
                            <div className="pb-4 text-center">
                                <div className="italic-message small">
                                    <FormattedMessage id="project.products.Pattern.detailedInfo.message"/>
                                </div>
                            </div>

                            <div className="row ml-3">
                                <label htmlFor="introduction" className="col-md-12 col-form-label bold-label">
                                    <FormattedMessage id="project.products.Pattern.introduction"/>
                                </label>
                                <div className="col-md-12 col-form-label">
                                    <p>{pattern.introduction}</p>
                                </div>
                            </div>

                            <div className="row ml-3">
                                <label htmlFor="notes" className="col-md-12 col-form-label bold-label">
                                    <FormattedMessage id="project.products.Pattern.notes"/>
                                </label>
                                <div className="col-md-12 col-form-label">
                                    <p>{pattern.notes}</p>
                                </div>
                            </div>

                            <div className="row ml-3">
                                <label htmlFor="gauge" className="col-md-12 col-form-label bold-label">
                                    <FormattedMessage id="project.products.Pattern.gauge"/>
                                </label>
                                <div className="col-md-12 col-form-label">
                                    <p>{pattern.gauge}</p>
                                </div>
                            </div>

                            <div className="row ml-3">
                                <label htmlFor="sizing" className="col-md-12 col-form-label bold-label">
                                    <FormattedMessage id="project.products.Pattern.sizing"/>
                                </label>
                                <div className="col-md-12 col-form-label">
                                    <p>{pattern.sizing}</p>
                                </div>
                            </div>

                            <div className="row ml-3">
                                <label htmlFor="time" className="col-md-12 col-form-label bold-label">
                                    <FormattedMessage id="project.products.Pattern.time"/>
                                </label>
                                <div className="col-md-12 col-form-label">
                                    <p>{pattern.time}</p>
                                </div>
                            </div>

                            <div className="row ml-3">
                                <label htmlFor="difficultyLevel" className="col-md-12 col-form-label bold-label">
                                    <FormattedMessage id="project.products.Pattern.difficultyLevel"/>
                                </label>
                                <div className="col-md-12 col-form-label">
                                    <p>{getDifficultyLevel(pattern.difficultyLevel)}</p>
                                </div>
                            </div>

                            <div className="row ml-3">
                                <label htmlFor="active" className="col-md-12 col-form-label bold-label">
                                    <FormattedMessage id="project.products.Product.active"/>
                                </label>
                                <div className="col-md-12 col-form-label">
                                    <p>{isItActive(pattern.active)}</p>
                                </div>
                            </div>


                            <div className="text-center mt-3">
                                <a href={`/publications/patterns/manage/${pattern.id}`} className="btn btn-primary mr-2">
                                    <FormattedMessage id="project.global.buttons.edit"/>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    )
}

export default PatternDetails;