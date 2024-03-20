import {Link} from 'react-router-dom';
import {FormattedMessage} from "react-intl";
import {useDispatch, useSelector} from "react-redux";

import * as actions from "../actions.js";
import * as selectors from "../selectors.js";
import * as userSelectors from '../../users/selectors.js';

import AddedPhysicals from "./AddedPhysicals.jsx";
import {Pager} from "../../common/index.js";
import {useEffect} from "react";

const ViewAddedPhysicals = () => {

    const physicalSearch = useSelector(selectors.getPhysicalSearch);
    const dispatch = useDispatch();
    const user = useSelector(userSelectors.getUser);

    useEffect(() => {

        dispatch(actions.findAddedPhysicals(user.userName,{page: 0}));

        return () => dispatch(actions.clearAddedPhysicalSearch());

    }, [dispatch]);


    if(!physicalSearch){
        return null;
    }


    return (

        <div className="mt-4 mb-4 container justify-content-center align-items-center">

            <h1 className="p-4">
                <FormattedMessage id="project.products.MyStore.heading"/>
            </h1>
            <ul className="nav nav-tabs nav-justified">
                <li className="nav-item">
                    <Link className="nav-link active nav-tab-item" to="/publications/products">
                        <i className="fa-solid fa-shirt mr-3"></i>

                        <FormattedMessage id="project.products.Product.heading"/>
                    </Link>
                </li>
                <li className="nav-item">
                    <Link className="nav-link nav-tab-item" to="/publications/patterns">
                        <i className="fa-solid fa-file-lines mr-3"></i>
                        <FormattedMessage id="project.products.Pattern.heading"/>
                    </Link>
                </li>
            </ul>

            <div className="back-color-blue card-body pt-5">
                <div className="d-flex justify-content-between align-items-center">
                    <div>
                        <h3 className="pt-3 pl-5">
                            <FormattedMessage id="project.products.AddedPhysical.heading"/>
                        </h3>
                    </div>
                    <div className="col-md-4 mr-5">
                        <Link className="btn button-dark-blue bold-label " to="/publications/create-physical">
                            <FormattedMessage id="project.products.CreatePhysical.heading"/>
                        </Link>
                    </div>
                </div>

                <p className=" bold-label ml-5" style={{ textTransform: 'uppercase' }}>
                    <FormattedMessage id="project.products.TotalPhysicals.title"/> {physicalSearch?.result?.items?.length || 0}
                </p>

                <div className="p-5">
                    {physicalSearch.result.items.length > 0 ? (
                        <div>
                            <AddedPhysicals physicals={physicalSearch.result.items}/>

                            <div className="mt-4">
                                <Pager back={{
                                    enabled: physicalSearch.criteria.page >= 1,
                                    onClick:() => dispatch(actions.previousFindAddedPhysicalsResultPage(physicalSearch.criteria))}}
                                       next={{
                                           enabled: physicalSearch.result.existMoreItems,
                                           onClick: () => dispatch(actions.nextFindAddedPhysicalsResultPage(physicalSearch.criteria))}}
                                /></div>
                        </div>
                    ):(
                        <div className="alert back-color-grey" role="alert">
                            <FormattedMessage id="project.products.Physical.empty"/>
                        </div>
                    )}
                </div>

            </div>







        </div>
    )
}

export default ViewAddedPhysicals;