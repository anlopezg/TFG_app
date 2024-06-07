import {FormattedMessage} from "react-intl";
import React from "react";

const PatternDetails_Introduction = ({pattern}) => {

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

    return(
        <div>
            <div className="mb-4 container d-flex justify-content-center align-items-center">

                <div className="card-body mx-4">

                    <div className="text-center">
                        <div className="framed-title disabled bold-label">
                            <FormattedMessage id="project.products.Pattern.detailedInfo"/>
                        </div>
                    </div>

                    <div className="row">
                        <label htmlFor="introduction" className="col-md-12 col-form-label bold-label">
                            <FormattedMessage id="project.products.Pattern.introduction"/>
                        </label>

                        <div className="col-md-12 col-form-label">
                            <p>{pattern.introduction}</p>
                        </div>
                    </div>

                    <div className="row">
                        <label htmlFor="notes" className="col-md-12 col-form-label bold-label">
                            <FormattedMessage id="project.products.Pattern.notes"/>
                        </label>

                        <div className="col-md-12 col-form-label">
                            <p>{pattern.notes}</p>
                        </div>
                    </div>

                    <div className="row">
                        <label htmlFor="time" className=" col-form-label bold-label">
                            <FormattedMessage id="project.products.Pattern.time"/>
                        </label>

                        <div className="col-md-12 col-form-label">
                            <p>{pattern.time}</p>
                        </div>
                    </div>
                    <div className="row">
                        <label htmlFor="difficultyLevel" className="col-form-label bold-label">
                            <FormattedMessage id="project.products.Pattern.difficultyLevel"/>
                        </label>

                        <div className="col-md-12 col-form-label">
                            <p>{getDifficultyLevel(pattern.difficultyLevel)}</p>
                        </div>

                    </div>

                    <div className="row">
                        <label htmlFor="abbreviations" className=" col-form-label bold-label">
                            <FormattedMessage id="project.products.Pattern.abbreviations"/>
                        </label>
                        <div className="col-md-12 col-form-label">
                            <p>{pattern.abbreviations}</p>
                        </div>

                    </div>

                    <div className="row">
                        <label htmlFor="specialAbbreviations" className=" col-form-label bold-label">
                            <FormattedMessage id="project.products.Pattern.special.abbreviations"/>
                        </label>
                        <div className="col-md-12 col-form-label">
                            <p>{pattern.specialAbbreviations}</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default PatternDetails_Introduction;