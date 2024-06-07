import {FormattedMessage} from "react-intl";
import React from "react";
import ImagesCarousel from "../../catalog/components/ImagesCarousel.jsx";

const PatternDetails_Directions = ({pattern}) => {

    return(
        <div>
            <div className="mb-4 container d-flex justify-content-center align-items-center">

                <div className="card-body mx-4">

                    <div className="text-center">
                        <div className="framed-title disabled bold-label">
                            <FormattedMessage id="project.products.Pattern.instructions" />
                        </div>
                    </div>

                    <div className="row mt-3">
                        <h3 className="col-md-12 retro">
                            <FormattedMessage id="project.pattern.instructions.section" />
                        </h3>

                        <div>
                            {pattern.sections.map((sec, index) => (
                                <div key={index} className="mb-4">
                                    <h5 className="bold-label">{sec.title}</h5>
                                    <p>{sec.description}</p>
                                    <div>
                                        <div style={{maxWidth: "400px", maxHeight: "100%"}}>
                                            <ImagesCarousel images={sec.imagesUrl}/>
                                        </div>
                                    </div>
                                    <div className="mx-4">
                                        <div className="bold-label text-uppercase"><FormattedMessage id="project.pattern.instructions.step.short" /></div>
                                        <ul>
                                            {sec.steps.map((st, idx) => (
                                                <li style={{ listStyleType: 'none' }} key={idx}>{idx + 1}. Row {st.rowNumber}: {st.instructions}</li>
                                            ))}
                                        </ul>
                                    </div>
                                </div>
                            ))}
                        </div>
                    </div>
                </div>

            </div>
        </div>
    )
}

export default PatternDetails_Directions;