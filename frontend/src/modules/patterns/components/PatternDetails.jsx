import {useSelector, useDispatch} from "react-redux";
import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";

import * as selectors from '../selectors.js';
import * as catalogSelectors from '../../catalog/selectors.js';
import * as actions from '../actions.js';
import {FormattedMessage} from "react-intl";
import ImagesCarousel from "../../catalog/components/ImagesCarousel.jsx";
import SideBar from "./SideBar.jsx";
import PatternDetails_General from "./PatternDetails_General.jsx";
import PatternDetails_Introduction from "./PatternDetails_Introduction.jsx";
import PatternDetails_Directions from "./PatternDetails_Directions.jsx";
import PatternDetails_Materials from "./PatternDetails_Materials.jsx";

const PatternDetails = () =>{

    const {id} = useParams();
    const pattern = useSelector(selectors.getPattern);
    const dispatch = useDispatch();
    const crafts = useSelector(catalogSelectors.getCrafts);
    const categories = useSelector(catalogSelectors.getCategories);
    const [currentStep, setCurrentStep] = useState(1);
    const steps = [
        { step: 1, label: 'General' },
        { step: 2, label: 'Introduction' },
        { step: 3, label: 'Materials' },
        { step: 4, label: 'Sections' }
    ];


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

    const handleNext = (e) => {
        e.preventDefault();
        setCurrentStep(prevStep => prevStep + 1);
    };

    const handlePrev = (e) => {
        e.preventDefault();
        setCurrentStep(prevStep => prevStep - 1);
    };

    const handleStepClick = (step) => setCurrentStep(step);

    return(
        <div>

            <a href={`/publications/patterns/`} className="btn btn-link back-link-blue">
                <i className="fa-solid fa-angle-left mr-2"></i>
                <FormattedMessage id='project.global.buttons.back'/>
            </a>

            <div className="mt-2 mb-3">
                <h2 className="retro">
                    <FormattedMessage id="project.products.ViewPattern.heading" />
                </h2>
            </div>

            <div className="d-flex h-100 justify-content-center">
                <SideBar steps={steps} currentStep={currentStep} onStepClick={handleStepClick} />
                <div className="card pattern-card">
                    <div className="card-body">

                        {currentStep === 1 && <PatternDetails_General pattern ={pattern} />}
                        {currentStep === 2 && <PatternDetails_Introduction pattern ={pattern} />}
                        {currentStep === 3 && <PatternDetails_Materials pattern ={pattern} />}
                        {currentStep === 4 && <PatternDetails_Directions pattern ={pattern} />}

                        <div className="navigation-buttons mt-3 mx-4">

                            {currentStep > 1 && <button className="button-light-blue" onClick={handlePrev}>
                                <i className="fa-solid fa-angle-left mx-1"></i>
                                <FormattedMessage id="project.global.buttons.previous" />
                            </button>}

                            {currentStep < steps.length && <button className="button-light-blue next" onClick={handleNext}>
                                <FormattedMessage id="project.global.buttons.next" />
                                <i className="fa-solid fa-angle-right mx-1"></i></button>}
                        </div>
                    </div>
                </div>
            </div>
        </div>

    )
}

export default PatternDetails;