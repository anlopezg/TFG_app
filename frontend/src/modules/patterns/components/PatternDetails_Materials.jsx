import {FormattedMessage} from "react-intl";
import React from "react";

const PatternDetails_Materials = ({pattern}) => {


    return(
        <div>
            <div className="mb-4 container d-flex justify-content-center align-items-center">

                <div className="card-body mx-4">

                    <div className=" text-center">
                        <div className="framed-title disabled bold-label">
                            <FormattedMessage id="project.products.Pattern.materials"/>
                        </div>
                    </div>

                    <div className="row mt-4">
                        <div className="col-md-12">
                            <p className="col-form-label bold-label">
                                <FormattedMessage id="project.products.Pattern.tools"/>
                            </p>
                            <ul className="mx-3 mt-2">
                                {pattern.tools.map((t, index) =>(
                                    <div key={index} className="d-flex justify-content-between align-items-center mb-2">
                                        <li style={{ listStyleType: 'decimal' }}>
                                            {t.toolName} x {t.amount}
                                        </li>
                                    </div>
                                ))}
                            </ul>
                        </div>
                    </div>

                    <div className="row">
                        <div className="col-md-12">
                            <p className="col-form-label bold-label">
                                <FormattedMessage id="project.products.Pattern.yarns"/>
                            </p>
                        </div>

                        <ul style={{ marginLeft: '20px' }}>
                            {pattern.yarns.map((y, index) => (
                                <div key={index} className="d-flex justify-content-between align-items-center mb-2">
                                    <li style={{ listStyleType: 'decimal' }}>
                                        {y.brand} - {y.name} x {y.amount} . {y.content} - {y.weight} - {y.yardage}
                                    </li>
                                </div>))}
                        </ul>
                    </div>

                    <div className="row">
                        <label htmlFor="gauge" className="col-form-label bold-label">
                            <FormattedMessage id="project.products.Pattern.gauge"/>
                        </label>
                        <div className="col-md-12 col-form-label">
                            <p>{pattern.gauge}</p>
                        </div>
                    </div>

                    <div className="row">
                        <label htmlFor="sizing" className="col-form-label bold-label">
                            <FormattedMessage id="project.products.Pattern.sizing"/>
                        </label>
                        <div className="col-md-12 col-form-label">
                            <p>{pattern.sizing}</p>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    )
}

export default PatternDetails_Materials;