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

export const getSubcategoryName = (subcategories, id) => {

    if(!subcategories){
        return '';
    }

    const subcategory = subcategories.find(subcategory => subcategory.id === id);

    if(!subcategory){
        return '';
    }

    return subcategory.subcategoryName;
}