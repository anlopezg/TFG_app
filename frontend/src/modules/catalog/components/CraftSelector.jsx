import {useSelector} from "react-redux";

import * as selectors from '../selectors.js';
import {FormattedMessage} from "react-intl";
import PropTypes from "prop-types";

const CraftSelector = (selectProps) =>{

    const crafts = useSelector(selectors.getCrafts);

    return(
        <select {...selectProps}>
            <option value="" disabled><FormattedMessage id="project.global.selectors.default"/></option>

            {crafts && crafts.map(craft =>
                <option key={craft.id} value={craft.id} >
                    <FormattedMessage id={`project.catalog.Crafts.${craft.craftName}`} />
                </option>
                    )}
        </select>
    );
}

CraftSelector.propTypes = {
    selectProps: PropTypes.object
};

export default CraftSelector;