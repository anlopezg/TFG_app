import React, { useState } from 'react';
import ProductFilter from './ProductFilter';
import UserFilter from './UserFilter';
import {Form} from "react-router-dom";
import {FormattedMessage} from "react-intl";

const FilterTab = () => {
    const [activeTab, setActiveTab] = useState('products');

    return (
        <div className="container filters">

            <div className="tabs">
                <button
                    className={`tablinks ${activeTab === 'products' ? 'active' : ''}`}
                    onClick={() => setActiveTab('products')}
                >
                    <FormattedMessage id="project.catalog.Search.ByProduct"/>
                </button>
                <button
                    className={`tablinks ${activeTab === 'users' ? 'active' : ''}`}
                    onClick={() => setActiveTab('users')}
                >
                    <FormattedMessage id="project.catalog.Search.ByUser"/>
                </button>
            </div>

            <div className="tab-content">
                {activeTab === 'products' && <ProductFilter />}
                {activeTab === 'users' && <UserFilter />}
            </div>
        </div>
    );
};

export default FilterTab;