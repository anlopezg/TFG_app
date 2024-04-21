import React, {useState} from "react";
import {FormattedMessage} from "react-intl";
import ProductFilter from "../../catalog/components/ProductFilter.jsx";
import UserFilter from "../../catalog/components/UserFilter.jsx";
import {ViewAddedPatterns, ViewAddedPhysicals} from "../index.js";

const StoreTabs = ({ initialTab }) =>{

    const [activeTab, setActiveTab] = useState(initialTab);

    return (
        <div className="container my-store">

            <div className="tabs my-store">
                <button
                    className={`tablinks my-store ${activeTab === 'physicals' ? 'active' : ''}`}
                    onClick={() => setActiveTab('physicals')}
                >
                    <i className="fa-solid fa-shirt me-3"></i>
                    <FormattedMessage id="project.products.Product.heading"/>
                </button>
                <button
                    className={`tablinks my-store ${activeTab === 'patterns' ? 'active' : ''}`}
                    onClick={() => setActiveTab('patterns')}>
                    <i className="fa-solid fa-file-lines me-3"></i>
                    <FormattedMessage id="project.products.Pattern.heading"/>
                </button>
            </div>

            <div className="tab-content my-store">

                <h1 className="h1-retro p-2 text-center">
                    <FormattedMessage id="project.products.MyStore.heading"/>
                </h1>

                {activeTab === 'physicals' && <ViewAddedPhysicals />}
                {activeTab === 'patterns' && <ViewAddedPatterns />}
            </div>
        </div>
    );
};

export default StoreTabs;