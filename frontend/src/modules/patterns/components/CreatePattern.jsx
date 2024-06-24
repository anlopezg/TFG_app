import { useState } from 'react';
import { useDispatch } from 'react-redux';
import { FormattedMessage } from 'react-intl';
import { useNavigate } from 'react-router-dom';

import { Errors } from '../../common/index.js';
import * as actions from '../actions.js';

import uploadImages from "../../../backend/cloudinary/uploadImages.js";
import SideBar from "./SideBar.jsx";
import Step1_General from "./Step1_General.jsx";
import Step2_Introduction from "./Step2_Introduction.jsx";
import Step3_Materials from "./Step3_Materials.jsx";
import Step4_Directions from "./Step4_Directions.jsx";
import imageUploader from "../../publications/components/imageUploader.js";
import sectionImageUploader from "./sectionImageUploader.js";

const CreatePattern = () => {

    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [currentStep, setCurrentStep] = useState(1);
    const [backendErrors, setBackendErrors] = useState(null);

    const { images, previewUrls, handleImagesChange, handleDeleteImage } = imageUploader();
    const { sectionImagesData, sectionPreviewUrls, handleSectionImagesChange, handleDeleteSectionImage, setSectionPreviewUrls } = sectionImageUploader();

    let form;

    const steps = [
        { step: 1, label: 'General' },
        { step: 2, label: 'Introduction' },
        { step: 3, label: 'Materials' },
        { step: 4, label: 'Sections' }
    ];

    const [patternData, setPatternData] = useState({
        userId: '',
        craftId: '',
        subcategoryId: '',
        title: '',
        description: '',
        price: '',
        active: true,
        introduction: '',
        notes: '',
        gauge: '',
        sizing: '',
        difficultyLevel: '',
        timeValue: '',
        timeUnit: 'minutes',
        abbreviations: '',
        specialAbbreviations: '',
        tools: [],
        imagesUrl: [],
        yarns: [],
        sections: []
    });

    const handleDataChange = (newData) => {
        setPatternData(prevData => ({ ...prevData, ...newData }));
    };

    const handleNext = (e) => {
        e.preventDefault();
        setCurrentStep(prevStep => prevStep + 1);
    };

    const handlePrev = (e) => {
        e.preventDefault();
        setCurrentStep(prevStep => prevStep - 1);
    };

    const handleStepClick = (step) => setCurrentStep(step);

    const handleSubmit = event => {
        event.preventDefault();

        let imageResults = [];

        const finalPatternData = {
            ...patternData,
            time: `${patternData.timeValue} ${patternData.timeUnit}`
        };

        try {
            // Subir imágenes generales
            uploadImages(images).then(results => {
                imageResults = results;
                finalPatternData.imagesUrl = imageResults.map(imageResult => imageResult.url);

                if (form.checkValidity()) {
                    dispatch(actions.createPattern(
                        finalPatternData,
                        () => navigate('/publications/patterns'),
                        errors => setBackendErrors(errors)
                    ));
                } else {
                    setBackendErrors(null);
                    form.classList.add('was-validated');
                }
            }).catch(error => {
                console.error("Error uploading pattern images: ", error);
            });
        } catch (error) {
            console.error("Error uploading pattern images: ", error);
        }
    };

    return (
        <div>
            <Errors errors={backendErrors} onClose={() => setBackendErrors(null)} />

            <div className="mt-2 mb-3">
                <h2 className="retro">
                    <FormattedMessage id="project.products.CreatePattern.heading" />
                </h2>
            </div>

            <div className="d-flex flex-column flex-lg-row h-100 justify-content-center">
                <SideBar steps={steps} currentStep={currentStep} onStepClick={handleStepClick} />

                <div className="card pattern-card flex-grow-1">
                    <div className="card-body">
                        <div className="form-content p-3 flex-grow-1">
                            <form ref={node => form = node}
                                  className="needs-validation" noValidate
                                  onSubmit={e => handleSubmit(e)}>

                                {currentStep === 1 && <Step1_General data={patternData} onChange={handleDataChange}
                                                                     handleImagesChange={handleImagesChange}
                                                                     handleDeleteImage={handleDeleteImage}
                                                                     previewUrls={previewUrls} />}
                                {currentStep === 2 && <Step2_Introduction data={patternData} onChange={handleDataChange} />}
                                {currentStep === 3 && <Step3_Materials data={patternData} onChange={handleDataChange} />}
                                {currentStep === 4 && <Step4_Directions data={patternData} onChange={handleDataChange}
                                                                        handleSectionImagesChange={handleSectionImagesChange}
                                                                        handleDeleteSectionImage={handleDeleteSectionImage}
                                                                        sectionPreviewUrls={sectionPreviewUrls}
                                                                        setSectionPreviewUrls={setSectionPreviewUrls} />}
                            </form>

                            <div className="navigation-buttons mt-3 mx-4">

                                {currentStep > 1 && <button className="button-light-blue" onClick={handlePrev}>
                                    <i className="fa-solid fa-angle-left mx-1"></i>
                                    <FormattedMessage id="project.global.buttons.previous" />
                                </button>}

                                {currentStep < steps.length && <button className="button-light-blue next" onClick={handleNext}>
                                    <FormattedMessage id="project.global.buttons.next" />
                                    <i className="fa-solid fa-angle-right mx-1"></i></button>}

                                {currentStep === steps.length && <button onClick={handleSubmit} className="button-coral next">
                                    <FormattedMessage id="project.global.buttons.submit" /></button>}
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>

    );

}

export default CreatePattern;

