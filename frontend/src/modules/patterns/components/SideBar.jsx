import React, {useState} from 'react';
import {FormattedMessage} from "react-intl";

const Sidebar = ({ steps, currentStep, onStepClick }) => {

    return (

        <div className="pattern-side-bar d-none d-lg-block">
            <div className=" d-flex flex-column flex-shrink-0 p-3" >
                <a href="/" className="d-flex align-items-center mb-3 mb-md-0 me-md-auto link-dark text-decoration-none">
                    <svg className="bi me-2" width="40" height="32"></svg>
                    <span className="fs-4">
                        <FormattedMessage id="project.pattern.step.title"/>
                    </span>
                </a>
                <hr />
                <ul className="nav nav-pills flex-column mb-auto">
                    {steps.map(step => (
                        <li className="nav-item" key={step.step}>
                            <a
                                href="#"
                                className={`nav-link ${currentStep === step.step ? 'active' : 'link-dark'}`}
                                aria-current={currentStep === step.step ? 'page' : undefined}
                                onClick={() => onStepClick(step.step)}
                            >
                                <svg className="bi me-2" width="16" height="16"></svg>
                                <FormattedMessage id={`project.pattern.step.${step.label.toLowerCase()}`} defaultMessage={step.label} />
                            </a>
                        </li>
                    ))}
                </ul>
            </div>
        </div>

    );
};

export default Sidebar;
