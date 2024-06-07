import React, { useState, useEffect } from 'react';
import { FormattedMessage } from 'react-intl';
import uploadImages from "../../../backend/cloudinary/uploadImages.js"; // Ajusta la ruta correcta
import deleteImages from "../../../backend/cloudinary/deleteImages.js"; // Ajusta la ruta correcta

const Step4_Directions = ({ data, onChange, handleSectionImagesChange, handleDeleteSectionImage, sectionPreviewUrls, setSectionPreviewUrls }) => {
    const [section, setSection] = useState({ title: '', description: '', sectionImages: [], steps: [] });
    const [step, setStep] = useState({ row_number: '', instruction: '' });

    const [active, setActive] = useState(true);
    const [errors, setErrors] = useState({ section: false, step: false });

    const handleCheckboxChange = (newValue) => {
        setActive(newValue === "publish");
    };

    const handleAddStep = () => {
        if (step.row_number.trim() !== '' && step.instruction.trim() !== '') {
            setSection(prev => ({
                ...prev,
                steps: [...prev.steps, step]
            }));
            setStep({ row_number: '', instruction: '' });
            setErrors(prev => ({ ...prev, step: false }));
        } else {
            setErrors(prev => ({ ...prev, step: true }));
        }
    };

    const handleRemoveStep = (index) => {
        setSection(prev => ({
            ...prev,
            steps: prev.steps.filter((_, i) => i !== index)
        }));
    };

    const handleStepChange = (e) => {
        const { name, value } = e.target;
        setStep(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const handleAddSection = async () => {
        if (section.title.trim() !== '' && section.description.trim() !== '' && section.steps.length > 0) {
            const sectionCount = data.sections.length + 1;

            // Filtrar las imágenes válidas antes de subir
            const validImages = sectionPreviewUrls.filter(url => url !== null && url !== undefined);

            if (validImages.length > 0) {
                try {
                    const imageResults = await uploadImages(validImages);
                    const uploadedImageUrls = imageResults.map(result => result.url);

                    const newSection = {
                        ...section,
                        title: `PART ${sectionCount}. ${section.title}`,
                        sectionImages: uploadedImageUrls
                    };
                    onChange({ sections: [...data.sections, newSection] });
                    setSection({ title: '', description: '', sectionImages: [], steps: [] });
                    setErrors(prev => ({ ...prev, section: false }));

                    // Limpiar las imágenes de la vista previa
                    handleClearPreviewImages();
                } catch (error) {
                    console.error('Error uploading images:', error);
                }
            } else {
                const newSection = {
                    ...section,
                    title: `PART ${sectionCount}. ${section.title}`,
                    sectionImages: []
                };
                onChange({ sections: [...data.sections, newSection] });
                setSection({ title: '', description: '', sectionImages: [], steps: [] });
                setErrors(prev => ({ ...prev, section: false }));
            }
        } else {
            setErrors(prev => ({ ...prev, section: true }));
        }
    };

    const handleRemoveSection = async (index) => {
        const sectionToRemove = data.sections[index];
        if (sectionToRemove.sectionImages.length > 0) {
            await deleteImages(sectionToRemove.sectionImages);
        }
        onChange({ sections: data.sections.filter((_, i) => i !== index) });
    };

    const handleSectionChange = (e) => {
        const { name, value } = e.target;
        setSection(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const handleClearPreviewImages = () => {
        setSectionPreviewUrls([null, null, null, null, null]);
    };

    const handleImageUpload = (file, index) => {
        if (file) {
            handleSectionImagesChange(file, index);
        }
    };

    useEffect(() => {
        return () => {
            sectionPreviewUrls.forEach(url => {
                if (url) URL.revokeObjectURL(url);
            });
        };
    }, [sectionPreviewUrls]);

    return (
        <div>
            <div>
                <div className="mb-4 container d-flex justify-content-center align-items-center">
                    <div className="card-body">
                        <div className="text-center">
                            <div className="framed-title disabled bold-label">
                                <FormattedMessage id="project.products.Pattern.instructions" />
                            </div>
                        </div>

                        <div className="row mt-3">
                            <h3 className="col-md-12 retro">
                                <FormattedMessage id="project.pattern.instructions.section" />
                            </h3>

                            <div className="mx-5">
                                {data.sections.map((sec, index) => (
                                    <div key={index} className="mb-4">
                                        <h5 className="bold-label">{sec.title}</h5>
                                        <p>{sec.description}</p>
                                        <div>
                                            {sec.sectionImages.map((imageUrl, imgIndex) => (
                                                <img key={imgIndex} src={imageUrl} alt={`Section ${index} Image ${imgIndex}`} width="100" />
                                            ))}
                                        </div>
                                        <div className="mx-4">
                                            <div className="bold-label text-uppercase"><FormattedMessage id="project.pattern.instructions.step.short" /></div>
                                            <ul>
                                                {sec.steps.map((st, idx) => (
                                                    <li style={{ listStyleType: 'none' }} key={idx}>{idx + 1}. Row {st.row_number}: {st.instruction}</li>
                                                ))}
                                            </ul>
                                        </div>
                                        <button onClick={() => handleRemoveSection(index)} className="btn btn-danger btn-sm">
                                            <i className="fa-solid fa-minus mx-2"></i>
                                            <FormattedMessage id="project.pattern.instructions.section.remove" />
                                        </button>
                                    </div>
                                ))}
                            </div>
                        </div>

                        <div className="small text-muted italic-message">
                            <FormattedMessage id="project.pattern.instructions.section.msg" />
                        </div>

                        <br />
                        <div className="card materials-card mt-0">
                            <div className="card-body">
                                <div className="form-group">
                                    <div className="col-md-12">
                                        <label htmlFor="sectionTitle" className="bold-label">
                                            <FormattedMessage id="project.pattern.instructions.section.title" />
                                        </label>
                                        <input
                                            type="text"
                                            id="sectionTitle"
                                            name="title"
                                            className="form-control"
                                            value={section.title}
                                            onChange={handleSectionChange}
                                        />
                                    </div>
                                    <div className="col-md-12">
                                        <label htmlFor="sectionDesc" className="mt-2">
                                            <FormattedMessage id="project.pattern.instructions.section.descr" />
                                        </label>
                                        <textarea
                                            id="sectionDesc"
                                            name="description"
                                            className="form-control"
                                            value={section.description}
                                            onChange={handleSectionChange}
                                        />
                                    </div>

                                    <div className="container image-upload-container justify-content-center dashed-border">
                                        {sectionPreviewUrls.slice(0, 5).map((url, index) => (
                                            <div key={index} className="row text-center">
                                                <div className="col">
                                                    <input
                                                        type="file"
                                                        accept="image/*"
                                                        onChange={(e) => handleImageUpload(e.target.files[0], index)}
                                                        style={{ display: 'none' }}
                                                        id={`file-input-${index}`}
                                                    />
                                                    <label htmlFor={`file-input-${index}`} className="btn button-light-pink-images">
                                                        {url ? <img src={url} alt="Preview" />
                                                            :
                                                            <i className="fa-solid fa-plus" style={{ color: "#fcfcfc", }}></i>}
                                                    </label>
                                                </div>
                                                <div key={index} className="row">
                                                    <div className="col">
                                                        {url && (<button type="button" onClick={() => handleDeleteSectionImage(index)} className="btn btn-danger">
                                                            <i className="fa-solid fa-trash"></i></button>)}
                                                    </div>
                                                </div>
                                            </div>
                                        ))}
                                    </div>
                                </div>
                                <br />

                                <div>
                                    <h4 className="col-md-12 retro">
                                        <FormattedMessage id="project.pattern.instructions.step" />
                                    </h4>

                                    <ul className="mt-2">
                                        {section.steps.map((s, index) => (
                                            <li key={index} className="d-flex justify-content-between align-items-center">
                                                {index + 1}. {s.row_number}: {s.instruction}
                                                <button onClick={() => handleRemoveStep(index)} className="btn btn-danger btn-sm" style={{ borderRadius: '20px' }}>
                                                    <i className="fa-solid fa-minus"></i>
                                                </button>
                                            </li>
                                        ))}
                                    </ul>

                                    <div className="small text-muted italic-message">
                                        <FormattedMessage id="project.pattern.instructions.step.msg" />
                                    </div>

                                    <div className="card materials-card">
                                        <div className="card-body">
                                            <label htmlFor="stepRowNumber">
                                                <FormattedMessage id="project.pattern.instructions.step.row" />
                                            </label>
                                            <input
                                                type="text"
                                                id="stepRowNumber"
                                                name="row_number"
                                                className="form-control"
                                                value={step.row_number}
                                                onChange={handleStepChange}
                                                placeholder={"Row #"}
                                            />
                                            <label htmlFor="stepInstruction">
                                                <FormattedMessage id="project.pattern.instructions.step.instruction" />
                                            </label>
                                            <textarea
                                                id="stepInstruction"
                                                name="instruction"
                                                className="form-control"
                                                value={step.instruction}
                                                onChange={handleStepChange}
                                            />
                                            {errors.step && <p className="text-danger mt-2">
                                                <FormattedMessage id="project.pattern.instructions.step.error" /></p>}
                                            <button onClick={handleAddStep} className="btn btn-primary mt-2">
                                                <FormattedMessage id="project.pattern.instructions.step.add" />
                                            </button>
                                        </div>
                                    </div>
                                </div>
                                <button onClick={handleAddSection} className="btn button-light-pink mt-3 bold-label">
                                    <FormattedMessage id="project.pattern.instructions.section.add" />
                                </button>
                                {errors.section && <p className="text-danger mt-2">
                                    <FormattedMessage id="project.pattern.instructions.sections.error" /></p>}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Step4_Directions;


