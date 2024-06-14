import React from 'react';
import ImagesCarousel from "../../../catalog/components/ImagesCarousel.jsx";
import {FormattedMessage} from "react-intl";
import Abbreviations from "../Abbreviations.jsx";
import ISO6391 from 'iso-639-1';

const PurchasedPatternDetails_Intro = ({ pattern }) => {

    const languageName = ISO6391.getName(pattern.language) || "Unknown Language";

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

    const renderAbbreviations = () => {
        if (pattern.abbreviations === 'None') return null;
        return <Abbreviations craft={pattern.craftId} standard={pattern.abbreviations}/>;
    }


    return (
        <div className="container text-center justify-content-center mt-3">
            <h3 className="display-5">{pattern.title}</h3>
            <p className="lead">{pattern.description}</p>
            <p className="text-muted"><em>By {pattern.username}</em></p>

            <div className="d-flex justify-content-center mb-4">
                <div id="patternCarousel" className="carousel slide mx-auto" data-ride="carousel">
                    <ImagesCarousel images={pattern.imagesUrl} />
                </div>
            </div>

            <div className="row justify-content-center mb-4">
                <div className="col-md-3">
                    <div className="p-2 border rounded bg-light">
                        <i className="fa-solid fa-graduation-cap mx-2"></i>
                        {getDifficultyLevel(pattern.difficultyLevel)}
                    </div>
                </div>
                <div className="col-md-3">
                    <div className="p-2 border rounded bg-light">
                        <i className="fa-regular fa-clock mx-2"></i>
                        {pattern.time}
                    </div>
                </div>
                <div className="col-md-3">
                    <div className="p-2 border rounded bg-light">
                        <i className="fa-solid fa-language mx-2"></i>
                        {languageName}
                    </div>
                </div>
            </div>

            <hr/>
            <div className="text-left">

                <h4 className="section-title">
                    <FormattedMessage id="project.global.fields.introduction"/>
                </h4>
                <p>{pattern.introduction}</p>


                <h4 className="section-title">
                    <FormattedMessage id="project.global.fields.notes"/>
                </h4>
                <div className="border pt-2 rounded bg-light">
                    <p>{pattern.notes}</p>
                </div>

                <div className="row mt-4">
                    <div className="col-md-6">
                        <h4 className="section-title">
                            <FormattedMessage id="project.global.fields.abbreviations" />
                        </h4>
                        {renderAbbreviations()}
                    </div>
                    <div className="col-md-6">
                        <h4 className="section-title">
                            <FormattedMessage id="project.products.Pattern.special.abbreviations" />
                        </h4>
                        <p>{pattern.specialAbbreviations}</p>
                    </div>
                </div>


            </div>

        </div>
    );
};

export default PurchasedPatternDetails_Intro;
