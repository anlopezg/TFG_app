import {FormattedMessage} from "react-intl";
import PropTypes from 'prop-types';

import {PatternLink} from '../../common';

const AddedPatterns= ({patterns}) =>(

    <table className="table table-striped table hover">
        <thead>
            <tr>
                <th scope="col">
                    <FormattedMessage id="project.products.Product.title"/>
                </th>
            </tr>
        </thead>

        <tbody>
            {patterns.map(pattern =>
            <tr key={pattern.id}>
                <td><PatternLink id={pattern.id} title={pattern.title}/></td>
            </tr>
            )}
        </tbody>
    </table>

);

AddedPatterns.propTypes = {
    patterns: PropTypes.array.isRequired
};

export default AddedPatterns;