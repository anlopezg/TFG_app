import {FormattedMessage} from "react-intl";
import React from "react";

const PurchasedPatternDetails_Materials = ({pattern}) => {

    const renderTools = () => {
        return pattern.tools.map((tool, index) => (
            <li key={index} className="mb-2 d-flex align-items-center">
                <span className="font-bold mr-2">{tool.amount}</span> x {tool.toolName}
            </li>
        ));
    };

    const renderYarns = () => {
        return pattern.yarns.map((yarn, index) => (
            <li key={index} className="mb-2">
                <div className="d-flex align-items-center">
                    <span className="font-bold me-1">{yarn.amount} x </span> {yarn.brand} {yarn.name} <em>
                    <FormattedMessage id="project.products.Pattern.yarns.inColor"/> {yarn.color}</em>
                </div>
                <div className="text-muted ms-4 my-2">
                    <small>
                        <div>{yarn.fiberContent}</div>
                        <div>
                            <FormattedMessage id="project.products.Pattern.yarns.weight"/>
                            : {yarn.weight}
                        </div>
                        <div>
                            <FormattedMessage id="project.products.Pattern.yarns.yardage"/>
                            : {yarn.length} </div>
                    </small>
                </div>
            </li>
        ));
    };

    return(
        <div>
            <div className="mb-4 container d-flex">

                <div className="card-body mx-4">
                    <div className=" text-center">
                        <div className="framed-title disabled bold-label">
                            <FormattedMessage id="project.pattern.step.materials"/>
                        </div>
                    </div>

                    <h4 className="section-title ">
                        <FormattedMessage id="project.products.Pattern.toolsRequired" />
                    </h4>
                    <ul className="list-unstyled border p-3 rounded bg-light ">
                        {renderTools()}
                    </ul>

                    <h4 className="section-title">
                        <FormattedMessage id="project.products.Pattern.yarnsRecommended" />
                    </h4>
                    <ul className="list-unstyled  border p-3 rounded bg-light">
                        {renderYarns()}
                    </ul>

                    <div className="row justify-content-center mt-4">
                        <div className="col-md-6">
                            <h4 className="section-title">
                                <FormattedMessage id="project.global.fields.gauge" />
                            </h4>
                            <p className="text-center border p-3 rounded bg-light">{pattern.gauge}</p>
                        </div>
                        <div className="col-md-6">
                            <h4 className="section-title">
                                <FormattedMessage id="project.global.fields.sizing" />
                            </h4>
                            <p className="text-center border p-3 rounded bg-light">{pattern.sizing}</p>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    )
}

export default PurchasedPatternDetails_Materials;