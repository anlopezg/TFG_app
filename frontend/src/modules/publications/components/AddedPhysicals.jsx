import {FormattedMessage} from "react-intl";
import PropTypes from 'prop-types';

import {PhysicalLink} from '../../common';

const AddedPhysicals= ({physicals}) =>(

    <table className="table table-striped table hover">
        <thead>
        <tr>
            <th scope="col">
                <FormattedMessage id="project.products.Product.title"/>
            </th>
        </tr>
        </thead>

        <tbody>
        {physicals.map(physical =>
            <tr key={physical.id}>
                <td><PhysicalLink id={physical.id} title={physical.title}/></td>
            </tr>
        )}
        </tbody>
    </table>

);

AddedPhysicals.propTypes = {
    physicals: PropTypes.array.isRequired
};

export default AddedPhysicals;