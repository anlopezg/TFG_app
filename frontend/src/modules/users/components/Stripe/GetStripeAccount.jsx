import {FormattedMessage} from "react-intl";
import {useEffect} from "react";
import * as actions from "../../actions.js";
import {useDispatch, useSelector} from "react-redux";
import * as selectors from "../../selectors.js";

const GetStripeAccount = () =>{

    const dispatch = useDispatch();
    const seller = useSelector(selectors.isSeller);
    const stripeAccount = useSelector(selectors.getStripeAccount);
    const accountId = useSelector(selectors.getAccountId);


    const getChargesValue = (charges) =>{
        if(charges === true){
            return <FormattedMessage id="project.users.StripeAccount.charges.true"/>
        }else{
            return <FormattedMessage id="project.users.StripeAccount.charges.false"/>
        }
    }


    return (
        <div>
            {stripeAccount ? (
                <div>
                    <div className="card">

                        <h2 className="retro card-header back-color-pink">
                            <FormattedMessage id="project.users.StripeAccount.title"/>
                        </h2>

                        <div className="card-body">

                            <div className="row justify-content-center">
                                <div className="col-md-4">
                                    <label htmlFor="id" className="col-form-label bold-label">
                                        <FormattedMessage id="project.users.StripeAccount.accountId"/>
                                    </label>
                                </div>
                                <div className="col-md-4 col-form-label">
                                    <p>{stripeAccount.id}</p>
                                </div>
                            </div>

                            <div className="row justify-content-center">
                                <div className="col-md-4">
                                    <label htmlFor="email" className="col-form-label bold-label">
                                        <FormattedMessage id="project.users.StripeAccount.email"/>
                                    </label>
                                </div>
                                <div className="col-md-4 col-form-label">
                                    <p>{stripeAccount.email}</p>
                                </div>
                            </div>

                            <div className="row justify-content-center">
                                <div className="col-md-4">
                                    <label htmlFor="country" className="col-form-label bold-label">
                                        <FormattedMessage id="project.users.StripeAccount.country"/>
                                    </label>
                                </div>
                                <div className="col-md-4 col-form-label">
                                    <p>{stripeAccount.country}</p>
                                </div>
                            </div>

                            <div className="row justify-content-center">
                                <div className="col-md-4">
                                    <label htmlFor="charges" className="col-form-label bold-label">
                                        <FormattedMessage id="project.users.StripeAccount.charges"/>
                                    </label>
                                </div>
                                <div className="col-md-4 col-form-label">
                                    <p>{getChargesValue(stripeAccount.chargesEnabled)}</p>
                                </div>
                            </div>


                        </div>

                    </div>

                </div>
            ):(
                <div>
                    <FormattedMessage id="project.users.StripeAccount.notFound"/>
                </div>
            )}

        </div>



    )
}

export default GetStripeAccount;