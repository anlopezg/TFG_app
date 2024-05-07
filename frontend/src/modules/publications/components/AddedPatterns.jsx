import {FormattedMessage} from "react-intl";
import PropTypes from 'prop-types';

import {PatternLink} from '../../common';
import {Link} from "react-router-dom";

const AddedPatterns= ({patterns}) =>(

    <div className="row">
        {patterns.map(pattern =>(
            <div key={pattern.id} className="col-md-4 mb-3">

                <div className="card-deck">
                    <div className="card">
                        <img src={pattern.imagesUrl[0]} className="card-img" alt="Product image"/>
                        <div className="card-body">
                            <h5 className="card-title">
                                <PatternLink id={pattern.id} title={pattern.title}/>
                            </h5>

                            <div className="d-flex justify-content-start mb-2">
                                <span className={`badge ${pattern.active ? 'bg-success' : 'bg-warning'}`}>
                                  <FormattedMessage
                                      id={pattern.active ? 'project.products.Product.published' : 'project.products.Product.draft.label'}
                                  />
                                </span>
                            </div>

                            <div className="d-flex justify-content-end">
                                <p className="card-text bold-label">{pattern.price} €</p>

                            </div>

                            <div className="text-center mt-3">
                                <a href={`/publications/manage-pattern/${pattern.id}`} className="btn btn-primary mx-2">
                                    <FormattedMessage id="project.global.buttons.edit"/>
                                </a>

                                <Link to={`/publications/delete-pattern/${pattern.id}`} className="btn btn-danger mx-2">
                                    <FormattedMessage id="project.global.buttons.delete"/>
                                </Link>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        ))}
    </div>

);

AddedPatterns.propTypes = {
    patterns: PropTypes.array.isRequired
};

export default AddedPatterns;