import {FormattedMessage} from "react-intl";

const getModuleState = state => state.catalog;

/*********************** CRAFTS, CATEGORIES ***********************/
export const getCategories = state =>
    getModuleState(state).categories;

export const getCrafts = state =>
    getModuleState(state).crafts;

export const getCraftName = (crafts, id) => {

    if(!crafts){
        return '';}

    const craft = crafts.find(craft => craft.id === id);

    if(!craft){
        return '';}

    return craft.craftName;
}

export const getCategoryNameBySubcategoryId = (categories, subcategoryId) => {
    if (!categories || !subcategoryId) {
        return '';
    }

    for (const category of categories) {
        for (const subcategory of category.subcategories) {
            if (subcategory.id === subcategoryId) {
                return category.categoryName;
            }
        }
    }

    return 'unknown';
};

export const getSubcategoryName = (categories, subcategoryId) => {
    if (!categories || !subcategoryId) {
        return '';
    }

    for (const category of categories) {
        for (const subcategory of category.subcategories) {
            if (subcategory.id === subcategoryId) {
                return subcategory.subcategoryName;
            }
        }
    }

    return 'unknown';
};

export const getProductSearch = state =>
    getModuleState(state).productSearch;

export const getProduct = state =>
    getModuleState(state).product;

export const getUserSearch = state =>
    getModuleState(state).userSearch;