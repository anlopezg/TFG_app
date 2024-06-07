import {useSelector, useDispatch} from "react-redux";
import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";

import * as selectors from '../../selectors.js';
import * as catalogSelectors from '../../../catalog/selectors.js';
import * as actions from '../../actions.js';
import {FormattedMessage} from "react-intl";
import ImagesCarousel from "../../../catalog/components/ImagesCarousel.jsx";
import SideBar from "../SideBar.jsx";

import PurchasedPatternDetails_Intro from "./PurchasedPatternDetails_Intro.jsx";
import PurchasedPatternDetails_Materials from "./PurchasedPatternDetails_Materials.jsx";
import PurchasedPatternDetails_Directions from "./PurchasedPatternDetails_Directions.jsx";

const PurchasedPatternDetails = () =>{

    const {id} = useParams();
    const pattern = useSelector(selectors.getPattern);
    const dispatch = useDispatch();
    const crafts = useSelector(catalogSelectors.getCrafts);
    const categories = useSelector(catalogSelectors.getCategories);
    const [currentStep, setCurrentStep] = useState(1);
    const steps = [
        { step: 1, label: 'Introduction' },
        { step: 2, label: 'Materials' },
        { step: 3, label: 'Sections' }
    ];


    useEffect(() => {

        if(!Number.isNaN(id)){
            dispatch(actions.getPurchasedPatternById(id));
        }

        return () => dispatch(actions.clearPattern());
    }, [id, dispatch]);

    if(!pattern){
        return null;
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
                <h2 className="retro text-center">
                    <FormattedMessage id="project.products.ViewPattern.heading" />
                </h2>
            </div>

            <div className="d-flex h-100 justify-content-center">
                <SideBar steps={steps} currentStep={currentStep} onStepClick={handleStepClick} />
                <div className="card pattern-card">
                    <div className="card-body">

                        {currentStep === 1 && <PurchasedPatternDetails_Intro pattern ={pattern} />}
                        {currentStep === 2 && <PurchasedPatternDetails_Materials pattern ={pattern} />}
                        {currentStep === 3 && <PurchasedPatternDetails_Directions pattern ={pattern} />}

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

export default PurchasedPatternDetails;