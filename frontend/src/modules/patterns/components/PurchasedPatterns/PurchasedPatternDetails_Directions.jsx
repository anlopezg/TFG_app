import React, {useEffect, useState} from 'react';
import { Accordion, Card, Button } from 'react-bootstrap';
import { FormattedMessage } from 'react-intl';
import ImagesCarousel from "../../../catalog/components/ImagesCarousel.jsx";

const PurchasedPatternDetails_Directions = ({ pattern }) => {
    const [checkedSteps, setCheckedSteps] = useState({});
    const [sortedSections, setSortedSections] = useState([]);


    // Sort the sections based on their sectionOrder and steps based on their stepOrder
    useEffect(() => {
        if (pattern.sections) {
            const sortedSections = [...pattern.sections].map(section => {
                const sortedSteps = [...section.steps].sort((a, b) => a.stepOrder - b.stepOrder);
                return { ...section, steps: sortedSteps };
            }).sort((a, b) => a.sectionOrder - b.sectionOrder);
            setSortedSections(sortedSections);
        }
    }, [pattern.sections]);

    const handleCheckboxChange = (sectionIndex, stepIndex) => {
        setCheckedSteps(prevState => ({
            ...prevState,
            [`${sectionIndex}-${stepIndex}`]: !prevState[`${sectionIndex}-${stepIndex}`]
        }));
    };


    const renderSections = () => {


        return sortedSections.map((section, sectionIndex) => (
                <Accordion.Item eventKey={`${sectionIndex}`} key={sectionIndex}>
                    <Accordion.Header>
                        <p className="bold-label">
                            <FormattedMessage id="project.pattern.instructions.section.part" /> {sectionIndex + 1}: {section.title}
                        </p>
                    </Accordion.Header>
                    <Accordion.Body>
                        <p>{section.description}</p>

                        {section.imagesUrl && section.imagesUrl.length > 0 && (
                            <div className="mb-3">
                                <ImagesCarousel images={section.imagesUrl.map(img => img.imageUrl)} />
                            </div>
                        )}

                        {section.steps.map((step, stepIndex) => (
                            <div key={stepIndex} className="mb-3">
                                <div className="d-flex align-items-center">
                                    <input
                                        type="checkbox"
                                        checked={checkedSteps[`${sectionIndex}-${stepIndex}`] || false}
                                        onChange={() => handleCheckboxChange(sectionIndex, stepIndex)}
                                        className="me-2"
                                    />
                                    <div>
                                        <strong>{`${step.rowNumber}`}</strong> {step.instructions}
                                    </div>
                                </div>
                            </div>
                        ))}
                    </Accordion.Body>
                </Accordion.Item>
        ));
    };

    return (
        <div>
            <div className="text-center mb-3">
                <div className="framed-title disabled bold-label">
                    <FormattedMessage id="project.products.Pattern.instructions" />
                </div>
            </div>

            <Accordion defaultActiveKey="0">
                {renderSections()}
            </Accordion>
        </div>

    );
};

export default PurchasedPatternDetails_Directions;
