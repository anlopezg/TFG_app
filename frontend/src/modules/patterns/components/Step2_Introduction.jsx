import React, { useState } from 'react';
import {FormattedMessage} from "react-intl";

const Step2_Introduction = ({ data, onChange }) => {

    const handleChange = (e) => {
        const { name, value } = e.target;
        onChange({ [name]: value });
    };

    const handleTimeChange = (e) => {
        const { name, value } = e.target;
        const newTimeData = {
            ...data,
            [name]: value,
            time: `${data.timeValue} ${data.timeUnit}`
        };
        if (name === 'timeValue') {
            newTimeData.timeValue = value;
        } else if (name === 'timeUnit') {
            newTimeData.timeUnit = value;
        }
        onChange(newTimeData);
    };

    return (
        <div>
            <div className="container d-flex justify-content-center align-items-center">

                    <div className="card-body">
                        <div className="text-center">
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
                                              name="introduction"
                                              value={data.introduction}
                                              maxLength="500"
                                              onChange={handleChange}
                                              required/>
                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.required'/>
                                </div>
                            </div>
                            <p className="small muted italic-message">
                                <FormattedMessage id="project.products.Pattern.introduction.desc"/>
                            </p>
                        </div>

                        <div className="form-group row">
                            <label htmlFor="notes" className="col-md-12 col-form-label bold-label">
                                <FormattedMessage id="project.products.Pattern.notes"/>
                            </label>
                            <div className="col-md-12">
                                    <textarea id="notes" className="form-control" rows="2"
                                              name="notes"
                                              value={data.notes}
                                              maxLength="500"
                                              onChange={handleChange}
                                    />
                            </div>
                            <p className="small muted  italic-message">
                                <FormattedMessage id="project.products.Pattern.notes.desc"/>
                            </p>
                        </div>

                        <br/>

                        <div className="form-group row">
                            <div className="col-md-6">
                                <label htmlFor="time" className=" col-form-label bold-label">
                                    <FormattedMessage id="project.products.Pattern.time"/>
                                </label>

                                <div className="row">
                                    <div className="col-md-6">
                                        <input type="number" id="time" className="form-control"
                                               name="timeValue"
                                               value={data.timeValue || ''}
                                               onChange={handleTimeChange}
                                               required/>
                                        <div className="invalid-feedback">
                                            <FormattedMessage id='project.global.validator.required'/>
                                        </div>
                                    </div>
                                    <div className="col-md-6">
                                        <select id="timeUnit" className="form-control form-select"
                                                name="timeUnit"
                                                value={data.timeUnit || 'minutes'}
                                                onChange={handleTimeChange} required>
                                            <option value="minutes"><FormattedMessage id="project.products.Pattern.time.minutes"/></option>
                                            <option value="hours"><FormattedMessage id="project.products.Pattern.time.hours"/></option>
                                            <option value="days"><FormattedMessage id="project.products.Pattern.time.days"/></option>
                                            <option value="weeks"><FormattedMessage id="project.products.Pattern.time.weeks"/></option>
                                        </select>
                                    </div>
                                </div>
                                <p className="small muted italic-message">
                                    <FormattedMessage id="project.products.Pattern.time.desc"/>
                                </p>
                            </div>

                            <div className="col-md-6">
                                <label htmlFor="difficultyLevel" className="col-form-label bold-label">
                                    <FormattedMessage id="project.products.Pattern.difficultyLevel"/>
                                </label>
                                <select id="difficultyLevel" className="form-select"  name="difficultyLevel" value={data.difficultyLevel}
                                        onChange={handleChange}
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


                        <br/>

                        <div className="form-group row">
                            <div className="col-md-6">
                                <label htmlFor="abbreviations" className=" col-form-label bold-label">
                                    <FormattedMessage id="project.products.Pattern.abbreviations"/>
                                </label>
                                <select id="abbreviations" className="form-select" name="abbreviations" value={data.abbreviations}
                                        onChange={handleChange} required>
                                    <option value="ES">
                                        <FormattedMessage id="project.products.Pattern.standard.spanish"/></option>
                                    <option value="CUSTOM">
                                        <FormattedMessage id="project.products.Pattern.standard.custom"/></option>
                                    <option value="US">
                                        <FormattedMessage id="project.products.Pattern.standard.usa"/></option>

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
                                    name="specialAbbreviations"
                                    value={data.specialAbbreviations}
                                    maxLength="200"
                                    onChange={handleChange}>

                                    </textarea>
                            </div>
                        </div>
                    </div>
            </div>

        </div>
    );
};

export default Step2_Introduction;
