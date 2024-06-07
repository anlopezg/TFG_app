import React, { useState } from 'react';
import {FormattedMessage} from "react-intl";

const Step3_Materials = ({ data, onChange }) => {

    const initialYarnState = { brand: '', name: '', color: '', amount: '', content: '', weight: '', yardage: '' };
    const initialToolState = { toolName: '', amount: '' };

    const [yarn, setYarn] = useState(initialYarnState);
    const [tool, setTool] = useState(initialToolState);
    const [errors, setErrors] = useState({ yarn: false, tool: false });


    /* YARNS MANAGEMENT */
    const handleChangeYarn = (e) => {
        const { name, value } = e.target;
        setYarn((prevState) => ({
            ...prevState,
            [name]: value
        }));
    };

    const isYarnValid = () => {
        return Object.values(yarn).every(value => value.trim() !== '');
    };

    const handleAddYarn = () => {
        if (isYarnValid()) {
            onChange({ yarns: [...data.yarns, yarn] });
            setYarn(initialYarnState);
            setErrors(prev => ({ ...prev, yarn: false }));
        } else {
            setErrors(prev => ({ ...prev, yarn: true }));
        }
    };

    const handleRemoveYarn = (index) => {
        const updatedYarns = data.yarns.filter((_, i) => i !== index);
        onChange({ yarns: updatedYarns });
    };



    /* TOOLS MANAGEMENT */
    const handleChangeTool = (e) => {
        const { name, value } = e.target;
        setTool((prevState) => ({
            ...prevState,
            [name]: value
        }));
    };

    const isToolValid = () => {
        return Object.values(tool).every(value => value.trim() !== '');
    };

    const handleAddTool = () => {
        if (isToolValid()) {
            onChange({ tools: [...data.tools, tool] });
            setTool(initialToolState);
            setErrors(prev => ({ ...prev, tool: false }));
        } else {
            setErrors(prev => ({ ...prev, tool: true }));
        }
    };

    const handleRemoveTool = (index) => {
        const updatedTools = data.tools.filter((_, i) => i !== index);
        onChange({ tools: updatedTools });
    };


    return (
        <div>

            <div className="container d-flex justify-content-center align-items-center">
                    <div className="card-body">
                        <div className=" text-center">
                            <div className="framed-title disabled bold-label">
                                <FormattedMessage id="project.products.Pattern.materials"/>
                            </div>
                        </div>


                        {/* Tool Inputs */}
                        <div className="row mt-4">
                            <div className="col-md-12">
                                <p className="col-form-label bold-label">
                                    <FormattedMessage id="project.products.Pattern.tools"/>
                                </p>
                            </div>
                        </div>

                        <ul className="mx-3 mt-2">
                            {data.tools.map((t, index) => (
                                <div key={index} className="d-flex justify-content-between align-items-center mb-2">
                                    <li style={{ listStyleType: 'decimal' }}>
                                        {t.toolName} x {t.amount}
                                    </li>
                                    <button
                                        onClick={() => handleRemoveTool(index)}
                                        className="btn btn-danger btn-sm"
                                        style={{ marginLeft: '10px' }}
                                    >
                                        <i className="fa-solid fa-minus mx-2"></i>
                                        <FormattedMessage id="project.products.Pattern.removeTool"/>
                                    </button>
                                </div>
                            ))}
                        </ul>

                        <div className="italic-message small mb-3">
                            <FormattedMessage id="project.products.Pattern.tools.message"/>
                        </div>

                        <div className="card materials-card">
                            <div className="card-body ">


                                <div className="row">
                                    <div className="col-md-6">
                                        <label htmlFor="amount">
                                            <FormattedMessage id="project.products.Pattern.toolName"/>
                                        </label>
                                        <input
                                            id="toolName"
                                            className="form-control"
                                            value={tool.toolName}
                                            name="toolName"
                                            maxLength="200"
                                            onChange={handleChangeTool}
                                        />
                                    </div>

                                    <div className="col-md-2">
                                        <label htmlFor="amount">
                                            <FormattedMessage id="project.products.Pattern.toolQuantity"/>
                                        </label>
                                        <input
                                            id="amount"
                                            className="form-control"
                                            type="number"
                                            value={tool.amount}
                                            name="amount"
                                            maxLength="200"
                                            onChange={handleChangeTool}
                                        />
                                    </div>

                                    <div className="col-md-4 d-flex align-items-end">
                                        <button onClick={handleAddTool} className="button-light-pink">
                                            <i className="fa-solid fa-plus mx-2"></i>
                                            <FormattedMessage id="project.products.Pattern.addTool"/>
                                        </button>
                                    </div>
                                </div>
                                {errors.tool && <p className="text-danger"><FormattedMessage id="project.products.Pattern.tool.error"/></p>}
                            </div>
                        </div>


                        <br/>


                        {/* Yarns Inputs */}
                        <div className="row">
                            <div className="col-md-12">
                                <p className="col-form-label bold-label">
                                    <FormattedMessage id="project.products.Pattern.yarns"/>
                                </p>

                                <ul style={{ marginLeft: '20px' }}>
                                    {data.yarns.map((y, index) => (
                                        <div key={index} className="d-flex justify-content-between align-items-center mb-2">
                                            <li style={{ listStyleType: 'decimal' }}>
                                                {y.brand} - {y.name} x {y.amount} . {y.content} - {y.weight} - {y.yardage}
                                            </li>
                                            <button
                                                onClick={() => handleRemoveYarn(index)}
                                                className="btn btn-danger btn-sm"
                                                style={{ marginLeft: '10px' }}
                                            >
                                                <i className="fa-solid fa-minus mx-2"></i>
                                                <FormattedMessage id="project.products.Pattern.removeYarn"/>
                                            </button>
                                        </div>
                                    ))}
                                </ul>


                            </div>
                        </div>

                        <div className="italic-message small mb-3">
                            <FormattedMessage id="project.products.Pattern.yarns.message"/>
                        </div>

                        <div className="card materials-card">
                            <div className="card-body">
                                <div className="row">
                                    <div className="col-md-6">
                                        <label htmlFor="brand">
                                            <FormattedMessage id="project.products.Pattern.yarns.brand"/>
                                        </label>
                                        <input
                                            id="brand"
                                            className="form-control"
                                            value={yarn.brand}
                                            name="brand"
                                            maxLength="200"
                                            onChange={handleChangeYarn}
                                        />
                                    </div>
                                    <div className="col-md-6">
                                        <label htmlFor="name">
                                            <FormattedMessage id="project.products.Pattern.yarns.name"/>
                                        </label>
                                        <input
                                            id="name"
                                            className="form-control"
                                            value={yarn.name}
                                            name="name"
                                            maxLength="200"
                                            onChange={handleChangeYarn}
                                        />
                                    </div>
                                    <div className="col-md-6">
                                        <label htmlFor="color">
                                            <FormattedMessage id="project.products.Pattern.yarns.color"/>
                                        </label>
                                        <input
                                            id="color"
                                            className="form-control"
                                            value={yarn.color}
                                            name="color"
                                            maxLength="200"
                                            onChange={handleChangeYarn}
                                        />
                                    </div>
                                    <div className="col-md-6">
                                        <label htmlFor="amount">
                                            <FormattedMessage id="project.products.Pattern.yarns.amount"/>
                                        </label>
                                        <input
                                            id="amount"
                                            className="form-control"
                                            value={yarn.amount}
                                            name="amount"
                                            maxLength="200"
                                            onChange={handleChangeYarn}
                                        />
                                    </div>
                                    <div className="col-md-6">
                                        <label htmlFor="content">
                                            <FormattedMessage id="project.products.Pattern.yarns.content"/>
                                        </label>
                                        <input
                                            id="content"
                                            className="form-control"
                                            value={yarn.content}
                                            name="content"
                                            maxLength="200"
                                            onChange={handleChangeYarn}
                                        />
                                    </div>
                                    <div className="col-md-6">
                                        <label htmlFor="weight">
                                            <FormattedMessage id="project.products.Pattern.yarns.weight"/>
                                        </label>
                                        <input
                                            id="weight"
                                            className="form-control"
                                            value={yarn.weight}
                                            name="weight"
                                            maxLength="200"
                                            onChange={handleChangeYarn}
                                        />
                                    </div>
                                    <div className="col-md-6">
                                        <label htmlFor="yardage">
                                            <FormattedMessage id="project.products.Pattern.yarns.yardage"/>
                                        </label>
                                        <input
                                            id="yardage"
                                            className="form-control"
                                            value={yarn.yardage}
                                            name="yardage"
                                            maxLength="200"
                                            onChange={handleChangeYarn}
                                        />
                                    </div>

                                    <div className="col-md-4 mt-3 d-flex align-items-center">
                                        <button onClick={handleAddYarn} className="button-light-pink">
                                            <FormattedMessage id="project.products.Pattern.yarns.add"/>
                                        </button>
                                    </div>
                            </div>
                                {errors.yarn && <p className="text-danger"><FormattedMessage id="project.products.Pattern.yarn.error"/></p>}
                        </div>
                        </div>

                        <br/>
                        <div className="form-group row">
                            <div className="col-md-6">
                                <label htmlFor="gauge" className="col-form-label bold-label">
                                    <FormattedMessage id="project.products.Pattern.gauge"/>
                                </label>

                                <div className="italic-message small">
                                    <FormattedMessage id="project.products.Pattern.gauge.msg"/>
                                </div>
                                <textarea id="gauge" className="form-control" rows="2"
                                          value={data.gauge}
                                          name="gauge"
                                          maxLength="200"
                                          onChange={(e) => onChange({ [e.target.name]: e.target.value })}
                                          required/>
                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.required'/>
                                </div>
                            </div>

                            <div className="col-md-6">
                                <label htmlFor="sizing" className="col-form-label bold-label">
                                    <FormattedMessage id="project.products.Pattern.sizing"/>
                                </label>
                                <div className="italic-message small">
                                    <FormattedMessage id="project.products.Pattern.sizing.msg"/>
                                </div>
                                <textarea id="sizing" className="form-control" rows="2"
                                          name="sizing"
                                          value={data.sizing}
                                          maxLength="200"
                                          onChange={(e) => onChange({ [e.target.name]: e.target.value })}
                                          required/>
                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.required'/>
                                </div>
                            </div>
                        </div>

                        <br/>
                    </div>
            </div>
        </div>
    );
};

export default Step3_Materials;
