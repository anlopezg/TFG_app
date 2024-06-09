import {FormattedMessage} from "react-intl";
import PropTypes from 'prop-types';

import {PatternLink} from '../../../common/index.js';
import {Link} from "react-router-dom";

const PurchasedPatternsResult= ({patterns}) =>(

    <div className="row">
        {patterns.map(pattern =>(
            <div key={pattern.id} className="col-md-4 mb-3">
                <Link to={`/shopping/pattern-details/${pattern.id}`} className="product-link-light" style={{ textDecoration: 'none', color: 'inherit' }}>
                    <div className="card-deck">
                        <div className="card">
                            <img src={pattern.imagesUrl[0]} className="card-img" alt="Product image"/>
                            <div className="card-body">
                                <h5 className="card-title text-center">
                                    {pattern.title}
                                </h5>
                            </div>
                        </div>
                    </div>
                </Link>
            </div>
        ))}
    </div>

);

PurchasedPatternsResult.propTypes = {
    patterns: PropTypes.array.isRequired
};

export default PurchasedPatternsResult;