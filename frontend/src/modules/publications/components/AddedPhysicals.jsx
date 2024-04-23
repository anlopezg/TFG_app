import {FormattedMessage} from "react-intl";
import PropTypes from 'prop-types';

import {PhysicalLink} from '../../common';

const AddedPhysicals= ({physicals}) =>(

    <div className="row">
        {physicals.map(physical =>(
            <div key={physical.id} className="col-md-4 mb-3">

                <div className="card-deck">
                    <div className="card">
                        <img src={physical.imagesUrl[0]} className="card-img" alt="Product image"/>
                        <div className="card-body">
                            <h5 className="card-title">
                                <PhysicalLink id={physical.id} title={physical.title}/>
                            </h5>
                            <div className="d-flex justify-content-between">
                                <p className="card-text"><small className="text-muted">{physical.amount}
                                    <FormattedMessage id="project.products.Product.amount.left"/>
                                </small></p>
                                <p className="card-text bold-label">{physical.price} €</p>

                            </div>

                            <div className="text-center mt-3">
                                <a href={`/publications/manage-physical/${physical.id}`} className="btn btn-primary mx-2">
                                    <FormattedMessage id="project.global.buttons.edit"/>
                                </a>
                                <a href={`/publications/delete-physical/${physical.id}`} className="btn btn-danger mx-2">
                                    <FormattedMessage id="project.global.buttons.delete"/>
                                </a>
                            </div>


                        </div>
                    </div>
                </div>
            </div>
        ))}
    </div>

);

AddedPhysicals.propTypes = {
    physicals: PropTypes.array.isRequired
};

export default AddedPhysicals;