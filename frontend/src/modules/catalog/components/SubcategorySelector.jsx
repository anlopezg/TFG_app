import {useSelector} from "react-redux";

import * as selectors from '../selectors.js';
import {FormattedMessage} from "react-intl";
import PropTypes from "prop-types";

const SubcategorySelector = (selectProps) =>{

    const categories = useSelector(selectors.getCategories);

    return (
        <select {...selectProps}>
            <option value=""><FormattedMessage id="project.catalog.Selector.all"/></option>
            {categories && categories.map(category => (

                <optgroup key={category.id} label={category.categoryName}>

                    {category.subcategories && category.subcategories.length > 0 && category.subcategories.map(subcategory => (

                        <option key={subcategory.id} value={subcategory.id}>

                            <FormattedMessage id={`project.catalog.Subcategories.${subcategory.subcategoryName}`} />
                        </option>
                    ))}
                </optgroup>
            ))}
        </select>
    );
}

SubcategorySelector.propTypes = {
    selectProps: PropTypes.object
};

export default SubcategorySelector;